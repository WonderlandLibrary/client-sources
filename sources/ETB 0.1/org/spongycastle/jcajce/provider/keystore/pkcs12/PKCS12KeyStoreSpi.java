package org.spongycastle.jcajce.provider.keystore.pkcs12;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore.LoadStoreParameter;
import java.security.KeyStore.PasswordProtection;
import java.security.KeyStore.ProtectionParameter;
import java.security.KeyStoreException;
import java.security.KeyStoreSpi;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.BEROctetString;
import org.spongycastle.asn1.BEROutputStream;
import org.spongycastle.asn1.DERBMPString;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DEROutputStream;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERSet;
import org.spongycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.spongycastle.asn1.cryptopro.GOST28147Parameters;
import org.spongycastle.asn1.nist.NISTObjectIdentifiers;
import org.spongycastle.asn1.ntt.NTTObjectIdentifiers;
import org.spongycastle.asn1.oiw.OIWObjectIdentifiers;
import org.spongycastle.asn1.pkcs.AuthenticatedSafe;
import org.spongycastle.asn1.pkcs.CertBag;
import org.spongycastle.asn1.pkcs.ContentInfo;
import org.spongycastle.asn1.pkcs.EncryptedData;
import org.spongycastle.asn1.pkcs.EncryptedPrivateKeyInfo;
import org.spongycastle.asn1.pkcs.EncryptionScheme;
import org.spongycastle.asn1.pkcs.KeyDerivationFunc;
import org.spongycastle.asn1.pkcs.MacData;
import org.spongycastle.asn1.pkcs.PBES2Parameters;
import org.spongycastle.asn1.pkcs.PBKDF2Params;
import org.spongycastle.asn1.pkcs.PKCS12PBEParams;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.pkcs.Pfx;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.asn1.pkcs.SafeBag;
import org.spongycastle.asn1.util.ASN1Dump;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.AuthorityKeyIdentifier;
import org.spongycastle.asn1.x509.DigestInfo;
import org.spongycastle.asn1.x509.Extension;
import org.spongycastle.asn1.x509.SubjectKeyIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.asn1.x509.X509ObjectIdentifiers;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.util.DigestFactory;
import org.spongycastle.jcajce.PKCS12Key;
import org.spongycastle.jcajce.PKCS12StoreParameter;
import org.spongycastle.jcajce.spec.GOST28147ParameterSpec;
import org.spongycastle.jcajce.spec.PBKDF2KeySpec;
import org.spongycastle.jcajce.util.BCJcaJceHelper;
import org.spongycastle.jcajce.util.JcaJceHelper;
import org.spongycastle.jce.interfaces.BCKeyStore;
import org.spongycastle.jce.interfaces.PKCS12BagAttributeCarrier;
import org.spongycastle.jce.provider.BouncyCastleProvider;
import org.spongycastle.jce.provider.JDKPKCS12StoreParameter;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Integers;
import org.spongycastle.util.Properties;
import org.spongycastle.util.Strings;
import org.spongycastle.util.encoders.Hex;

public class PKCS12KeyStoreSpi extends KeyStoreSpi implements PKCSObjectIdentifiers, X509ObjectIdentifiers, BCKeyStore
{
  static final String PKCS12_MAX_IT_COUNT_PROPERTY = "org.spongycastle.pkcs12.max_it_count";
  private final JcaJceHelper helper = new BCJcaJceHelper();
  
  private static final int SALT_SIZE = 20;
  
  private static final int MIN_ITERATIONS = 1024;
  private static final DefaultSecretKeyProvider keySizeProvider = new DefaultSecretKeyProvider();
  
  private IgnoresCaseHashtable keys = new IgnoresCaseHashtable(null);
  private Hashtable localIds = new Hashtable();
  private IgnoresCaseHashtable certs = new IgnoresCaseHashtable(null);
  private Hashtable chainCerts = new Hashtable();
  private Hashtable keyCerts = new Hashtable();
  
  static final int NULL = 0;
  
  static final int CERTIFICATE = 1;
  
  static final int KEY = 2;
  
  static final int SECRET = 3;
  
  static final int SEALED = 4;
  
  static final int KEY_PRIVATE = 0;
  
  static final int KEY_PUBLIC = 1;
  
  static final int KEY_SECRET = 2;
  
  protected SecureRandom random = new SecureRandom();
  
  private CertificateFactory certFact;
  
  private ASN1ObjectIdentifier keyAlgorithm;
  
  private ASN1ObjectIdentifier certAlgorithm;
  private AlgorithmIdentifier macAlgorithm = new AlgorithmIdentifier(OIWObjectIdentifiers.idSHA1, DERNull.INSTANCE);
  private int itCount = 1024;
  private int saltLength = 20;
  

  private class CertId
  {
    byte[] id;
    
    CertId(PublicKey key)
    {
      id = PKCS12KeyStoreSpi.this.createSubjectKeyId(key).getKeyIdentifier();
    }
    

    CertId(byte[] id)
    {
      this.id = id;
    }
    
    public int hashCode()
    {
      return Arrays.hashCode(id);
    }
    

    public boolean equals(Object o)
    {
      if (o == this)
      {
        return true;
      }
      
      if (!(o instanceof CertId))
      {
        return false;
      }
      
      CertId cId = (CertId)o;
      
      return Arrays.areEqual(id, id);
    }
  }
  



  public PKCS12KeyStoreSpi(Provider provider, ASN1ObjectIdentifier keyAlgorithm, ASN1ObjectIdentifier certAlgorithm)
  {
    this.keyAlgorithm = keyAlgorithm;
    this.certAlgorithm = certAlgorithm;
    
    try
    {
      if (provider != null)
      {
        certFact = CertificateFactory.getInstance("X.509", provider);
      }
      else
      {
        certFact = CertificateFactory.getInstance("X.509");
      }
    }
    catch (Exception e)
    {
      throw new IllegalArgumentException("can't create cert factory - " + e.toString());
    }
  }
  

  private SubjectKeyIdentifier createSubjectKeyId(PublicKey pubKey)
  {
    try
    {
      SubjectPublicKeyInfo info = SubjectPublicKeyInfo.getInstance(pubKey.getEncoded());
      
      return new SubjectKeyIdentifier(getDigest(info));
    }
    catch (Exception e)
    {
      throw new RuntimeException("error creating key");
    }
  }
  
  private static byte[] getDigest(SubjectPublicKeyInfo spki)
  {
    Digest digest = DigestFactory.createSHA1();
    byte[] resBuf = new byte[digest.getDigestSize()];
    
    byte[] bytes = spki.getPublicKeyData().getBytes();
    digest.update(bytes, 0, bytes.length);
    digest.doFinal(resBuf, 0);
    return resBuf;
  }
  

  public void setRandom(SecureRandom rand)
  {
    random = rand;
  }
  
  public Enumeration engineAliases()
  {
    Hashtable tab = new Hashtable();
    
    Enumeration e = certs.keys();
    while (e.hasMoreElements())
    {
      tab.put(e.nextElement(), "cert");
    }
    
    e = keys.keys();
    while (e.hasMoreElements())
    {
      String a = (String)e.nextElement();
      if (tab.get(a) == null)
      {
        tab.put(a, "key");
      }
    }
    
    return tab.keys();
  }
  

  public boolean engineContainsAlias(String alias)
  {
    return (certs.get(alias) != null) || (keys.get(alias) != null);
  }
  






  public void engineDeleteEntry(String alias)
    throws KeyStoreException
  {
    Key k = (Key)keys.remove(alias);
    
    Certificate c = (Certificate)certs.remove(alias);
    
    if (c != null)
    {
      chainCerts.remove(new CertId(c.getPublicKey()));
    }
    
    if (k != null)
    {
      String id = (String)localIds.remove(alias);
      if (id != null)
      {
        c = (Certificate)keyCerts.remove(id);
      }
      if (c != null)
      {
        chainCerts.remove(new CertId(c.getPublicKey()));
      }
    }
  }
  




  public Certificate engineGetCertificate(String alias)
  {
    if (alias == null)
    {
      throw new IllegalArgumentException("null alias passed to getCertificate.");
    }
    
    Certificate c = (Certificate)certs.get(alias);
    



    if (c == null)
    {
      String id = (String)localIds.get(alias);
      if (id != null)
      {
        c = (Certificate)keyCerts.get(id);
      }
      else
      {
        c = (Certificate)keyCerts.get(alias);
      }
    }
    
    return c;
  }
  

  public String engineGetCertificateAlias(Certificate cert)
  {
    Enumeration c = certs.elements();
    Enumeration k = certs.keys();
    
    while (c.hasMoreElements())
    {
      Certificate tc = (Certificate)c.nextElement();
      String ta = (String)k.nextElement();
      
      if (tc.equals(cert))
      {
        return ta;
      }
    }
    
    c = keyCerts.elements();
    k = keyCerts.keys();
    
    while (c.hasMoreElements())
    {
      Certificate tc = (Certificate)c.nextElement();
      String ta = (String)k.nextElement();
      
      if (tc.equals(cert))
      {
        return ta;
      }
    }
    
    return null;
  }
  

  public Certificate[] engineGetCertificateChain(String alias)
  {
    if (alias == null)
    {
      throw new IllegalArgumentException("null alias passed to getCertificateChain.");
    }
    
    if (!engineIsKeyEntry(alias))
    {
      return null;
    }
    
    Certificate c = engineGetCertificate(alias);
    
    if (c != null)
    {
      Vector cs = new Vector();
      
      while (c != null)
      {
        X509Certificate x509c = (X509Certificate)c;
        Certificate nextC = null;
        
        byte[] bytes = x509c.getExtensionValue(Extension.authorityKeyIdentifier.getId());
        if (bytes != null)
        {
          try
          {
            ASN1InputStream aIn = new ASN1InputStream(bytes);
            
            byte[] authBytes = ((ASN1OctetString)aIn.readObject()).getOctets();
            aIn = new ASN1InputStream(authBytes);
            
            AuthorityKeyIdentifier id = AuthorityKeyIdentifier.getInstance(aIn.readObject());
            if (id.getKeyIdentifier() != null)
            {
              nextC = (Certificate)chainCerts.get(new CertId(id.getKeyIdentifier()));
            }
            
          }
          catch (IOException e)
          {
            throw new RuntimeException(e.toString());
          }
        }
        
        if (nextC == null)
        {



          Principal i = x509c.getIssuerDN();
          Principal s = x509c.getSubjectDN();
          
          if (!i.equals(s))
          {
            Enumeration e = chainCerts.keys();
            
            while (e.hasMoreElements())
            {
              X509Certificate crt = (X509Certificate)chainCerts.get(e.nextElement());
              Principal sub = crt.getSubjectDN();
              if (sub.equals(i))
              {
                try
                {
                  x509c.verify(crt.getPublicKey());
                  nextC = crt;
                }
                catch (Exception localException) {}
              }
            }
          }
        }
        




        if (cs.contains(c))
        {
          c = null;
        }
        else
        {
          cs.addElement(c);
          if (nextC != c)
          {
            c = nextC;
          }
          else
          {
            c = null;
          }
        }
      }
      
      Certificate[] certChain = new Certificate[cs.size()];
      
      for (int i = 0; i != certChain.length; i++)
      {
        certChain[i] = ((Certificate)cs.elementAt(i));
      }
      
      return certChain;
    }
    
    return null;
  }
  
  public Date engineGetCreationDate(String alias)
  {
    if (alias == null)
    {
      throw new NullPointerException("alias == null");
    }
    if ((keys.get(alias) == null) && (certs.get(alias) == null))
    {
      return null;
    }
    return new Date();
  }
  


  public Key engineGetKey(String alias, char[] password)
    throws NoSuchAlgorithmException, UnrecoverableKeyException
  {
    if (alias == null)
    {
      throw new IllegalArgumentException("null alias passed to getKey.");
    }
    
    return (Key)keys.get(alias);
  }
  

  public boolean engineIsCertificateEntry(String alias)
  {
    return (certs.get(alias) != null) && (keys.get(alias) == null);
  }
  

  public boolean engineIsKeyEntry(String alias)
  {
    return keys.get(alias) != null;
  }
  


  public void engineSetCertificateEntry(String alias, Certificate cert)
    throws KeyStoreException
  {
    if (keys.get(alias) != null)
    {
      throw new KeyStoreException("There is a key entry with the name " + alias + ".");
    }
    
    certs.put(alias, cert);
    chainCerts.put(new CertId(cert.getPublicKey()), cert);
  }
  



  public void engineSetKeyEntry(String alias, byte[] key, Certificate[] chain)
    throws KeyStoreException
  {
    throw new RuntimeException("operation not supported");
  }
  




  public void engineSetKeyEntry(String alias, Key key, char[] password, Certificate[] chain)
    throws KeyStoreException
  {
    if (!(key instanceof PrivateKey))
    {
      throw new KeyStoreException("PKCS12 does not support non-PrivateKeys");
    }
    
    if (((key instanceof PrivateKey)) && (chain == null))
    {
      throw new KeyStoreException("no certificate chain for private key");
    }
    
    if (keys.get(alias) != null)
    {
      engineDeleteEntry(alias);
    }
    
    keys.put(alias, key);
    if (chain != null)
    {
      certs.put(alias, chain[0]);
      
      for (int i = 0; i != chain.length; i++)
      {
        chainCerts.put(new CertId(chain[i].getPublicKey()), chain[i]);
      }
    }
  }
  
  public int engineSize()
  {
    Hashtable tab = new Hashtable();
    
    Enumeration e = certs.keys();
    while (e.hasMoreElements())
    {
      tab.put(e.nextElement(), "cert");
    }
    
    e = keys.keys();
    while (e.hasMoreElements())
    {
      String a = (String)e.nextElement();
      if (tab.get(a) == null)
      {
        tab.put(a, "key");
      }
    }
    
    return tab.size();
  }
  




  protected PrivateKey unwrapKey(AlgorithmIdentifier algId, byte[] data, char[] password, boolean wrongPKCS12Zero)
    throws IOException
  {
    ASN1ObjectIdentifier algorithm = algId.getAlgorithm();
    try
    {
      if (algorithm.on(PKCSObjectIdentifiers.pkcs_12PbeIds))
      {
        PKCS12PBEParams pbeParams = PKCS12PBEParams.getInstance(algId.getParameters());
        

        PBEParameterSpec defParams = new PBEParameterSpec(pbeParams.getIV(), validateIterationCount(pbeParams.getIterations()));
        
        Cipher cipher = helper.createCipher(algorithm.getId());
        
        PKCS12Key key = new PKCS12Key(password, wrongPKCS12Zero);
        
        cipher.init(4, key, defParams);
        

        return (PrivateKey)cipher.unwrap(data, "", 2);
      }
      if (algorithm.equals(PKCSObjectIdentifiers.id_PBES2))
      {

        Cipher cipher = createCipher(4, password, algId);
        

        return (PrivateKey)cipher.unwrap(data, "", 2);
      }
    }
    catch (Exception e)
    {
      throw new IOException("exception unwrapping private key - " + e.toString());
    }
    
    throw new IOException("exception unwrapping private key - cannot recognise: " + algorithm);
  }
  




  protected byte[] wrapKey(String algorithm, Key key, PKCS12PBEParams pbeParams, char[] password)
    throws IOException
  {
    PBEKeySpec pbeSpec = new PBEKeySpec(password);
    

    try
    {
      SecretKeyFactory keyFact = helper.createSecretKeyFactory(algorithm);
      

      PBEParameterSpec defParams = new PBEParameterSpec(pbeParams.getIV(), pbeParams.getIterations().intValue());
      
      Cipher cipher = helper.createCipher(algorithm);
      
      cipher.init(3, keyFact.generateSecret(pbeSpec), defParams);
      
      out = cipher.wrap(key);
    }
    catch (Exception e) {
      byte[] out;
      throw new IOException("exception encrypting data - " + e.toString());
    }
    byte[] out;
    return out;
  }
  





  protected byte[] cryptData(boolean forEncryption, AlgorithmIdentifier algId, char[] password, boolean wrongPKCS12Zero, byte[] data)
    throws IOException
  {
    ASN1ObjectIdentifier algorithm = algId.getAlgorithm();
    int mode = forEncryption ? 1 : 2;
    
    if (algorithm.on(PKCSObjectIdentifiers.pkcs_12PbeIds))
    {
      PKCS12PBEParams pbeParams = PKCS12PBEParams.getInstance(algId.getParameters());
      

      try
      {
        PBEParameterSpec defParams = new PBEParameterSpec(pbeParams.getIV(), pbeParams.getIterations().intValue());
        PKCS12Key key = new PKCS12Key(password, wrongPKCS12Zero);
        
        Cipher cipher = helper.createCipher(algorithm.getId());
        
        cipher.init(mode, key, defParams);
        return cipher.doFinal(data);
      }
      catch (Exception e)
      {
        throw new IOException("exception decrypting data - " + e.toString());
      }
    }
    if (algorithm.equals(PKCSObjectIdentifiers.id_PBES2))
    {
      try
      {
        Cipher cipher = createCipher(mode, password, algId);
        
        return cipher.doFinal(data);
      }
      catch (Exception e)
      {
        throw new IOException("exception decrypting data - " + e.toString());
      }
    }
    

    throw new IOException("unknown PBE algorithm: " + algorithm);
  }
  

  private Cipher createCipher(int mode, char[] password, AlgorithmIdentifier algId)
    throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, NoSuchProviderException
  {
    PBES2Parameters alg = PBES2Parameters.getInstance(algId.getParameters());
    PBKDF2Params func = PBKDF2Params.getInstance(alg.getKeyDerivationFunc().getParameters());
    AlgorithmIdentifier encScheme = AlgorithmIdentifier.getInstance(alg.getEncryptionScheme());
    
    SecretKeyFactory keyFact = helper.createSecretKeyFactory(alg.getKeyDerivationFunc().getAlgorithm().getId());
    SecretKey key;
    SecretKey key;
    if (func.isDefaultPrf())
    {
      key = keyFact.generateSecret(new PBEKeySpec(password, func.getSalt(), validateIterationCount(func.getIterationCount()), keySizeProvider.getKeySize(encScheme)));
    }
    else
    {
      key = keyFact.generateSecret(new PBKDF2KeySpec(password, func.getSalt(), validateIterationCount(func.getIterationCount()), keySizeProvider.getKeySize(encScheme), func.getPrf()));
    }
    
    Cipher cipher = Cipher.getInstance(alg.getEncryptionScheme().getAlgorithm().getId());
    
    ASN1Encodable encParams = alg.getEncryptionScheme().getParameters();
    if ((encParams instanceof ASN1OctetString))
    {
      cipher.init(mode, key, new IvParameterSpec(ASN1OctetString.getInstance(encParams).getOctets()));

    }
    else
    {
      GOST28147Parameters gParams = GOST28147Parameters.getInstance(encParams);
      
      cipher.init(mode, key, new GOST28147ParameterSpec(gParams.getEncryptionParamSet(), gParams.getIV()));
    }
    return cipher;
  }
  


  public void engineLoad(InputStream stream, char[] password)
    throws IOException
  {
    if (stream == null)
    {
      return;
    }
    
    if (password == null)
    {
      throw new NullPointerException("No password supplied for PKCS#12 KeyStore.");
    }
    
    BufferedInputStream bufIn = new BufferedInputStream(stream);
    
    bufIn.mark(10);
    
    int head = bufIn.read();
    
    if (head != 48)
    {
      throw new IOException("stream does not represent a PKCS12 key store");
    }
    
    bufIn.reset();
    
    ASN1InputStream bIn = new ASN1InputStream(bufIn);
    ASN1Sequence obj = (ASN1Sequence)bIn.readObject();
    Pfx bag = Pfx.getInstance(obj);
    ContentInfo info = bag.getAuthSafe();
    Vector chain = new Vector();
    boolean unmarkedKey = false;
    boolean wrongPKCS12Zero = false;
    
    if (bag.getMacData() != null)
    {
      MacData mData = bag.getMacData();
      DigestInfo dInfo = mData.getMac();
      macAlgorithm = dInfo.getAlgorithmId();
      byte[] salt = mData.getSalt();
      itCount = validateIterationCount(mData.getIterationCount());
      saltLength = salt.length;
      
      byte[] data = ((ASN1OctetString)info.getContent()).getOctets();
      
      try
      {
        byte[] res = calculatePbeMac(macAlgorithm.getAlgorithm(), salt, itCount, password, false, data);
        byte[] dig = dInfo.getDigest();
        
        if (!Arrays.constantTimeAreEqual(res, dig))
        {
          if (password.length > 0)
          {
            throw new IOException("PKCS12 key store mac invalid - wrong password or corrupted file.");
          }
          

          res = calculatePbeMac(macAlgorithm.getAlgorithm(), salt, itCount, password, true, data);
          
          if (!Arrays.constantTimeAreEqual(res, dig))
          {
            throw new IOException("PKCS12 key store mac invalid - wrong password or corrupted file.");
          }
          
          wrongPKCS12Zero = true;
        }
      }
      catch (IOException e)
      {
        throw e;
      }
      catch (Exception e)
      {
        throw new IOException("error constructing MAC: " + e.toString());
      }
    }
    
    keys = new IgnoresCaseHashtable(null);
    localIds = new Hashtable();
    
    if (info.getContentType().equals(data))
    {
      bIn = new ASN1InputStream(((ASN1OctetString)info.getContent()).getOctets());
      
      AuthenticatedSafe authSafe = AuthenticatedSafe.getInstance(bIn.readObject());
      ContentInfo[] c = authSafe.getContentInfo();
      
      for (int i = 0; i != c.length; i++)
      {
        if (c[i].getContentType().equals(data))
        {
          ASN1InputStream dIn = new ASN1InputStream(((ASN1OctetString)c[i].getContent()).getOctets());
          ASN1Sequence seq = (ASN1Sequence)dIn.readObject();
          
          for (int j = 0; j != seq.size(); j++)
          {
            SafeBag b = SafeBag.getInstance(seq.getObjectAt(j));
            if (b.getBagId().equals(pkcs8ShroudedKeyBag))
            {
              EncryptedPrivateKeyInfo eIn = EncryptedPrivateKeyInfo.getInstance(b.getBagValue());
              PrivateKey privKey = unwrapKey(eIn.getEncryptionAlgorithm(), eIn.getEncryptedData(), password, wrongPKCS12Zero);
              



              PKCS12BagAttributeCarrier bagAttr = (PKCS12BagAttributeCarrier)privKey;
              String alias = null;
              ASN1OctetString localId = null;
              
              if (b.getBagAttributes() != null)
              {
                Enumeration e = b.getBagAttributes().getObjects();
                while (e.hasMoreElements())
                {
                  ASN1Sequence sq = (ASN1Sequence)e.nextElement();
                  ASN1ObjectIdentifier aOid = (ASN1ObjectIdentifier)sq.getObjectAt(0);
                  ASN1Set attrSet = (ASN1Set)sq.getObjectAt(1);
                  ASN1Primitive attr = null;
                  
                  if (attrSet.size() > 0)
                  {
                    attr = (ASN1Primitive)attrSet.getObjectAt(0);
                    
                    ASN1Encodable existing = bagAttr.getBagAttribute(aOid);
                    if (existing != null)
                    {

                      if (!existing.toASN1Primitive().equals(attr))
                      {
                        throw new IOException("attempt to add existing attribute with different value");
                      }
                      

                    }
                    else {
                      bagAttr.setBagAttribute(aOid, attr);
                    }
                  }
                  
                  if (aOid.equals(pkcs_9_at_friendlyName))
                  {
                    alias = ((DERBMPString)attr).getString();
                    keys.put(alias, privKey);
                  }
                  else if (aOid.equals(pkcs_9_at_localKeyId))
                  {
                    localId = (ASN1OctetString)attr;
                  }
                }
              }
              
              if (localId != null)
              {
                String name = new String(Hex.encode(localId.getOctets()));
                
                if (alias == null)
                {
                  keys.put(name, privKey);
                }
                else
                {
                  localIds.put(alias, name);
                }
              }
              else
              {
                unmarkedKey = true;
                keys.put("unmarked", privKey);
              }
            }
            else if (b.getBagId().equals(certBag))
            {
              chain.addElement(b);
            }
            else
            {
              System.out.println("extra in data " + b.getBagId());
              System.out.println(ASN1Dump.dumpAsString(b));
            }
          }
        }
        else if (c[i].getContentType().equals(encryptedData))
        {
          EncryptedData d = EncryptedData.getInstance(c[i].getContent());
          byte[] octets = cryptData(false, d.getEncryptionAlgorithm(), password, wrongPKCS12Zero, d
            .getContent().getOctets());
          ASN1Sequence seq = (ASN1Sequence)ASN1Primitive.fromByteArray(octets);
          
          for (int j = 0; j != seq.size(); j++)
          {
            SafeBag b = SafeBag.getInstance(seq.getObjectAt(j));
            
            if (b.getBagId().equals(certBag))
            {
              chain.addElement(b);
            }
            else if (b.getBagId().equals(pkcs8ShroudedKeyBag))
            {
              EncryptedPrivateKeyInfo eIn = EncryptedPrivateKeyInfo.getInstance(b.getBagValue());
              PrivateKey privKey = unwrapKey(eIn.getEncryptionAlgorithm(), eIn.getEncryptedData(), password, wrongPKCS12Zero);
              



              PKCS12BagAttributeCarrier bagAttr = (PKCS12BagAttributeCarrier)privKey;
              String alias = null;
              ASN1OctetString localId = null;
              
              Enumeration e = b.getBagAttributes().getObjects();
              while (e.hasMoreElements())
              {
                ASN1Sequence sq = (ASN1Sequence)e.nextElement();
                ASN1ObjectIdentifier aOid = (ASN1ObjectIdentifier)sq.getObjectAt(0);
                ASN1Set attrSet = (ASN1Set)sq.getObjectAt(1);
                ASN1Primitive attr = null;
                
                if (attrSet.size() > 0)
                {
                  attr = (ASN1Primitive)attrSet.getObjectAt(0);
                  
                  ASN1Encodable existing = bagAttr.getBagAttribute(aOid);
                  if (existing != null)
                  {

                    if (!existing.toASN1Primitive().equals(attr))
                    {
                      throw new IOException("attempt to add existing attribute with different value");
                    }
                    

                  }
                  else {
                    bagAttr.setBagAttribute(aOid, attr);
                  }
                }
                
                if (aOid.equals(pkcs_9_at_friendlyName))
                {
                  alias = ((DERBMPString)attr).getString();
                  keys.put(alias, privKey);
                }
                else if (aOid.equals(pkcs_9_at_localKeyId))
                {
                  localId = (ASN1OctetString)attr;
                }
              }
              
              String name = new String(Hex.encode(localId.getOctets()));
              
              if (alias == null)
              {
                keys.put(name, privKey);
              }
              else
              {
                localIds.put(alias, name);
              }
            }
            else if (b.getBagId().equals(keyBag))
            {
              PrivateKeyInfo kInfo = PrivateKeyInfo.getInstance(b.getBagValue());
              PrivateKey privKey = BouncyCastleProvider.getPrivateKey(kInfo);
              



              PKCS12BagAttributeCarrier bagAttr = (PKCS12BagAttributeCarrier)privKey;
              String alias = null;
              ASN1OctetString localId = null;
              
              Enumeration e = b.getBagAttributes().getObjects();
              while (e.hasMoreElements())
              {
                ASN1Sequence sq = ASN1Sequence.getInstance(e.nextElement());
                ASN1ObjectIdentifier aOid = ASN1ObjectIdentifier.getInstance(sq.getObjectAt(0));
                ASN1Set attrSet = ASN1Set.getInstance(sq.getObjectAt(1));
                ASN1Primitive attr = null;
                
                if (attrSet.size() > 0)
                {
                  attr = (ASN1Primitive)attrSet.getObjectAt(0);
                  
                  ASN1Encodable existing = bagAttr.getBagAttribute(aOid);
                  if (existing != null)
                  {

                    if (!existing.toASN1Primitive().equals(attr))
                    {
                      throw new IOException("attempt to add existing attribute with different value");
                    }
                    

                  }
                  else {
                    bagAttr.setBagAttribute(aOid, attr);
                  }
                  
                  if (aOid.equals(pkcs_9_at_friendlyName))
                  {
                    alias = ((DERBMPString)attr).getString();
                    keys.put(alias, privKey);
                  }
                  else if (aOid.equals(pkcs_9_at_localKeyId))
                  {
                    localId = (ASN1OctetString)attr;
                  }
                }
              }
              
              String name = new String(Hex.encode(localId.getOctets()));
              
              if (alias == null)
              {
                keys.put(name, privKey);
              }
              else
              {
                localIds.put(alias, name);
              }
            }
            else
            {
              System.out.println("extra in encryptedData " + b.getBagId());
              System.out.println(ASN1Dump.dumpAsString(b));
            }
          }
        }
        else
        {
          System.out.println("extra " + c[i].getContentType().getId());
          System.out.println("extra " + ASN1Dump.dumpAsString(c[i].getContent()));
        }
      }
    }
    
    certs = new IgnoresCaseHashtable(null);
    chainCerts = new Hashtable();
    keyCerts = new Hashtable();
    
    for (int i = 0; i != chain.size(); i++)
    {
      SafeBag b = (SafeBag)chain.elementAt(i);
      CertBag cb = CertBag.getInstance(b.getBagValue());
      
      if (!cb.getCertId().equals(x509Certificate))
      {
        throw new RuntimeException("Unsupported certificate type: " + cb.getCertId());
      }
      



      try
      {
        ByteArrayInputStream cIn = new ByteArrayInputStream(((ASN1OctetString)cb.getCertValue()).getOctets());
        cert = certFact.generateCertificate(cIn);
      }
      catch (Exception e) {
        Certificate cert;
        throw new RuntimeException(e.toString());
      }
      

      Certificate cert;
      
      ASN1OctetString localId = null;
      String alias = null;
      
      if (b.getBagAttributes() != null)
      {
        Enumeration e = b.getBagAttributes().getObjects();
        while (e.hasMoreElements())
        {
          ASN1Sequence sq = ASN1Sequence.getInstance(e.nextElement());
          ASN1ObjectIdentifier oid = ASN1ObjectIdentifier.getInstance(sq.getObjectAt(0));
          ASN1Set attrSet = ASN1Set.getInstance(sq.getObjectAt(1));
          
          if (attrSet.size() > 0)
          {
            ASN1Primitive attr = (ASN1Primitive)attrSet.getObjectAt(0);
            PKCS12BagAttributeCarrier bagAttr = null;
            
            if ((cert instanceof PKCS12BagAttributeCarrier))
            {
              bagAttr = (PKCS12BagAttributeCarrier)cert;
              
              ASN1Encodable existing = bagAttr.getBagAttribute(oid);
              if (existing != null)
              {

                if (!existing.toASN1Primitive().equals(attr))
                {
                  throw new IOException("attempt to add existing attribute with different value");
                }
                

              }
              else {
                bagAttr.setBagAttribute(oid, attr);
              }
            }
            
            if (oid.equals(pkcs_9_at_friendlyName))
            {
              alias = ((DERBMPString)attr).getString();
            }
            else if (oid.equals(pkcs_9_at_localKeyId))
            {
              localId = (ASN1OctetString)attr;
            }
          }
        }
      }
      
      chainCerts.put(new CertId(cert.getPublicKey()), cert);
      
      if (unmarkedKey)
      {
        if (keyCerts.isEmpty())
        {
          String name = new String(Hex.encode(createSubjectKeyId(cert.getPublicKey()).getKeyIdentifier()));
          
          keyCerts.put(name, cert);
          keys.put(name, keys.remove("unmarked"));
        }
        

      }
      else
      {

        if (localId != null)
        {
          String name = new String(Hex.encode(localId.getOctets()));
          
          keyCerts.put(name, cert);
        }
        if (alias != null)
        {
          certs.put(alias, cert);
        }
      }
    }
  }
  
  private int validateIterationCount(BigInteger i)
  {
    int count = i.intValue();
    
    if (count < 0)
    {
      throw new IllegalStateException("negative iteration count found");
    }
    
    BigInteger maxValue = Properties.asBigInteger("org.spongycastle.pkcs12.max_it_count");
    if (maxValue != null)
    {
      if (maxValue.intValue() < count)
      {
        throw new IllegalStateException("iteration count " + count + " greater than " + maxValue.intValue());
      }
    }
    
    return count;
  }
  

  public void engineStore(KeyStore.LoadStoreParameter param)
    throws IOException, NoSuchAlgorithmException, CertificateException
  {
    if (param == null)
    {
      throw new IllegalArgumentException("'param' arg cannot be null");
    }
    
    if ((!(param instanceof PKCS12StoreParameter)) && (!(param instanceof JDKPKCS12StoreParameter)))
    {

      throw new IllegalArgumentException("No support for 'param' of type " + param.getClass().getName());
    }
    
    PKCS12StoreParameter bcParam;
    PKCS12StoreParameter bcParam;
    if ((param instanceof PKCS12StoreParameter))
    {
      bcParam = (PKCS12StoreParameter)param;

    }
    else
    {
      bcParam = new PKCS12StoreParameter(((JDKPKCS12StoreParameter)param).getOutputStream(), param.getProtectionParameter(), ((JDKPKCS12StoreParameter)param).isUseDEREncoding());
    }
    

    KeyStore.ProtectionParameter protParam = param.getProtectionParameter();
    char[] password; if (protParam == null)
    {
      password = null;
    } else { char[] password;
      if ((protParam instanceof KeyStore.PasswordProtection))
      {
        password = ((KeyStore.PasswordProtection)protParam).getPassword();

      }
      else
      {
        throw new IllegalArgumentException("No support for protection parameter of type " + protParam.getClass().getName()); }
    }
    char[] password;
    doStore(bcParam.getOutputStream(), password, bcParam.isForDEREncoding());
  }
  
  public void engineStore(OutputStream stream, char[] password)
    throws IOException
  {
    doStore(stream, password, false);
  }
  
  private void doStore(OutputStream stream, char[] password, boolean useDEREncoding)
    throws IOException
  {
    if (password == null)
    {
      throw new NullPointerException("No password supplied for PKCS#12 KeyStore.");
    }
    



    ASN1EncodableVector keyS = new ASN1EncodableVector();
    
    Enumeration ks = keys.keys();
    
    while (ks.hasMoreElements())
    {
      byte[] kSalt = new byte[20];
      
      random.nextBytes(kSalt);
      
      String name = (String)ks.nextElement();
      PrivateKey privKey = (PrivateKey)keys.get(name);
      PKCS12PBEParams kParams = new PKCS12PBEParams(kSalt, 1024);
      byte[] kBytes = wrapKey(keyAlgorithm.getId(), privKey, kParams, password);
      AlgorithmIdentifier kAlgId = new AlgorithmIdentifier(keyAlgorithm, kParams.toASN1Primitive());
      EncryptedPrivateKeyInfo kInfo = new EncryptedPrivateKeyInfo(kAlgId, kBytes);
      boolean attrSet = false;
      ASN1EncodableVector kName = new ASN1EncodableVector();
      
      if ((privKey instanceof PKCS12BagAttributeCarrier))
      {
        PKCS12BagAttributeCarrier bagAttrs = (PKCS12BagAttributeCarrier)privKey;
        


        DERBMPString nm = (DERBMPString)bagAttrs.getBagAttribute(pkcs_9_at_friendlyName);
        if ((nm == null) || (!nm.getString().equals(name)))
        {
          bagAttrs.setBagAttribute(pkcs_9_at_friendlyName, new DERBMPString(name));
        }
        



        if (bagAttrs.getBagAttribute(pkcs_9_at_localKeyId) == null)
        {
          Certificate ct = engineGetCertificate(name);
          
          bagAttrs.setBagAttribute(pkcs_9_at_localKeyId, createSubjectKeyId(ct.getPublicKey()));
        }
        
        Enumeration e = bagAttrs.getBagAttributeKeys();
        
        while (e.hasMoreElements())
        {
          ASN1ObjectIdentifier oid = (ASN1ObjectIdentifier)e.nextElement();
          ASN1EncodableVector kSeq = new ASN1EncodableVector();
          
          kSeq.add(oid);
          kSeq.add(new DERSet(bagAttrs.getBagAttribute(oid)));
          
          attrSet = true;
          
          kName.add(new DERSequence(kSeq));
        }
      }
      
      if (!attrSet)
      {



        ASN1EncodableVector kSeq = new ASN1EncodableVector();
        Certificate ct = engineGetCertificate(name);
        
        kSeq.add(pkcs_9_at_localKeyId);
        kSeq.add(new DERSet(createSubjectKeyId(ct.getPublicKey())));
        
        kName.add(new DERSequence(kSeq));
        
        kSeq = new ASN1EncodableVector();
        
        kSeq.add(pkcs_9_at_friendlyName);
        kSeq.add(new DERSet(new DERBMPString(name)));
        
        kName.add(new DERSequence(kSeq));
      }
      
      SafeBag kBag = new SafeBag(pkcs8ShroudedKeyBag, kInfo.toASN1Primitive(), new DERSet(kName));
      keyS.add(kBag);
    }
    
    byte[] keySEncoded = new DERSequence(keyS).getEncoded("DER");
    BEROctetString keyString = new BEROctetString(keySEncoded);
    



    byte[] cSalt = new byte[20];
    
    random.nextBytes(cSalt);
    
    ASN1EncodableVector certSeq = new ASN1EncodableVector();
    PKCS12PBEParams cParams = new PKCS12PBEParams(cSalt, 1024);
    AlgorithmIdentifier cAlgId = new AlgorithmIdentifier(certAlgorithm, cParams.toASN1Primitive());
    Hashtable doneCerts = new Hashtable();
    
    Enumeration cs = keys.keys();
    while (cs.hasMoreElements())
    {
      try
      {
        String name = (String)cs.nextElement();
        Certificate cert = engineGetCertificate(name);
        boolean cAttrSet = false;
        

        CertBag cBag = new CertBag(x509Certificate, new DEROctetString(cert.getEncoded()));
        ASN1EncodableVector fName = new ASN1EncodableVector();
        
        if ((cert instanceof PKCS12BagAttributeCarrier))
        {
          PKCS12BagAttributeCarrier bagAttrs = (PKCS12BagAttributeCarrier)cert;
          


          DERBMPString nm = (DERBMPString)bagAttrs.getBagAttribute(pkcs_9_at_friendlyName);
          if ((nm == null) || (!nm.getString().equals(name)))
          {
            bagAttrs.setBagAttribute(pkcs_9_at_friendlyName, new DERBMPString(name));
          }
          



          if (bagAttrs.getBagAttribute(pkcs_9_at_localKeyId) == null)
          {
            bagAttrs.setBagAttribute(pkcs_9_at_localKeyId, createSubjectKeyId(cert.getPublicKey()));
          }
          
          Enumeration e = bagAttrs.getBagAttributeKeys();
          
          while (e.hasMoreElements())
          {
            ASN1ObjectIdentifier oid = (ASN1ObjectIdentifier)e.nextElement();
            ASN1EncodableVector fSeq = new ASN1EncodableVector();
            
            fSeq.add(oid);
            fSeq.add(new DERSet(bagAttrs.getBagAttribute(oid)));
            fName.add(new DERSequence(fSeq));
            
            cAttrSet = true;
          }
        }
        
        if (!cAttrSet)
        {
          ASN1EncodableVector fSeq = new ASN1EncodableVector();
          
          fSeq.add(pkcs_9_at_localKeyId);
          fSeq.add(new DERSet(createSubjectKeyId(cert.getPublicKey())));
          fName.add(new DERSequence(fSeq));
          
          fSeq = new ASN1EncodableVector();
          
          fSeq.add(pkcs_9_at_friendlyName);
          fSeq.add(new DERSet(new DERBMPString(name)));
          
          fName.add(new DERSequence(fSeq));
        }
        
        SafeBag sBag = new SafeBag(certBag, cBag.toASN1Primitive(), new DERSet(fName));
        
        certSeq.add(sBag);
        
        doneCerts.put(cert, cert);
      }
      catch (CertificateEncodingException e)
      {
        throw new IOException("Error encoding certificate: " + e.toString());
      }
    }
    
    cs = certs.keys();
    while (cs.hasMoreElements())
    {
      try
      {
        String certId = (String)cs.nextElement();
        Certificate cert = (Certificate)certs.get(certId);
        boolean cAttrSet = false;
        
        if (keys.get(certId) == null)
        {





          CertBag cBag = new CertBag(x509Certificate, new DEROctetString(cert.getEncoded()));
          ASN1EncodableVector fName = new ASN1EncodableVector();
          
          if ((cert instanceof PKCS12BagAttributeCarrier))
          {
            PKCS12BagAttributeCarrier bagAttrs = (PKCS12BagAttributeCarrier)cert;
            


            DERBMPString nm = (DERBMPString)bagAttrs.getBagAttribute(pkcs_9_at_friendlyName);
            if ((nm == null) || (!nm.getString().equals(certId)))
            {
              bagAttrs.setBagAttribute(pkcs_9_at_friendlyName, new DERBMPString(certId));
            }
            
            Enumeration e = bagAttrs.getBagAttributeKeys();
            
            while (e.hasMoreElements())
            {
              ASN1ObjectIdentifier oid = (ASN1ObjectIdentifier)e.nextElement();
              




              if (!oid.equals(PKCSObjectIdentifiers.pkcs_9_at_localKeyId))
              {



                ASN1EncodableVector fSeq = new ASN1EncodableVector();
                
                fSeq.add(oid);
                fSeq.add(new DERSet(bagAttrs.getBagAttribute(oid)));
                fName.add(new DERSequence(fSeq));
                
                cAttrSet = true;
              }
            }
          }
          if (!cAttrSet)
          {
            ASN1EncodableVector fSeq = new ASN1EncodableVector();
            
            fSeq.add(pkcs_9_at_friendlyName);
            fSeq.add(new DERSet(new DERBMPString(certId)));
            
            fName.add(new DERSequence(fSeq));
          }
          
          SafeBag sBag = new SafeBag(certBag, cBag.toASN1Primitive(), new DERSet(fName));
          
          certSeq.add(sBag);
          
          doneCerts.put(cert, cert);
        }
      }
      catch (CertificateEncodingException e) {
        throw new IOException("Error encoding certificate: " + e.toString());
      }
    }
    
    Set usedSet = getUsedCertificateSet();
    
    cs = chainCerts.keys();
    while (cs.hasMoreElements())
    {
      try
      {
        CertId certId = (CertId)cs.nextElement();
        Certificate cert = (Certificate)chainCerts.get(certId);
        
        if ((usedSet.contains(cert)) && 
        



          (doneCerts.get(cert) == null))
        {





          CertBag cBag = new CertBag(x509Certificate, new DEROctetString(cert.getEncoded()));
          ASN1EncodableVector fName = new ASN1EncodableVector();
          
          if ((cert instanceof PKCS12BagAttributeCarrier))
          {
            PKCS12BagAttributeCarrier bagAttrs = (PKCS12BagAttributeCarrier)cert;
            Enumeration e = bagAttrs.getBagAttributeKeys();
            
            while (e.hasMoreElements())
            {
              ASN1ObjectIdentifier oid = (ASN1ObjectIdentifier)e.nextElement();
              




              if (!oid.equals(PKCSObjectIdentifiers.pkcs_9_at_localKeyId))
              {



                ASN1EncodableVector fSeq = new ASN1EncodableVector();
                
                fSeq.add(oid);
                fSeq.add(new DERSet(bagAttrs.getBagAttribute(oid)));
                fName.add(new DERSequence(fSeq));
              }
            }
          }
          SafeBag sBag = new SafeBag(certBag, cBag.toASN1Primitive(), new DERSet(fName));
          
          certSeq.add(sBag);
        }
      }
      catch (CertificateEncodingException e) {
        throw new IOException("Error encoding certificate: " + e.toString());
      }
    }
    
    byte[] certSeqEncoded = new DERSequence(certSeq).getEncoded("DER");
    byte[] certBytes = cryptData(true, cAlgId, password, false, certSeqEncoded);
    EncryptedData cInfo = new EncryptedData(data, cAlgId, new BEROctetString(certBytes));
    



    ContentInfo[] info = { new ContentInfo(data, keyString), new ContentInfo(encryptedData, cInfo.toASN1Primitive()) };
    

    AuthenticatedSafe auth = new AuthenticatedSafe(info);
    
    ByteArrayOutputStream bOut = new ByteArrayOutputStream();
    DEROutputStream asn1Out;
    DEROutputStream asn1Out; if (useDEREncoding)
    {
      asn1Out = new DEROutputStream(bOut);
    }
    else
    {
      asn1Out = new BEROutputStream(bOut);
    }
    
    asn1Out.writeObject(auth);
    
    byte[] pkg = bOut.toByteArray();
    
    ContentInfo mainInfo = new ContentInfo(data, new BEROctetString(pkg));
    



    byte[] mSalt = new byte[saltLength];
    
    random.nextBytes(mSalt);
    
    byte[] data = ((ASN1OctetString)mainInfo.getContent()).getOctets();
    


    try
    {
      byte[] res = calculatePbeMac(macAlgorithm.getAlgorithm(), mSalt, itCount, password, false, data);
      
      DigestInfo dInfo = new DigestInfo(macAlgorithm, res);
      
      mData = new MacData(dInfo, mSalt, itCount);
    }
    catch (Exception e) {
      MacData mData;
      throw new IOException("error constructing MAC: " + e.toString());
    }
    

    MacData mData;
    
    Pfx pfx = new Pfx(mainInfo, mData);
    
    if (useDEREncoding)
    {
      asn1Out = new DEROutputStream(stream);
    }
    else
    {
      asn1Out = new BEROutputStream(stream);
    }
    
    asn1Out.writeObject(pfx);
  }
  
  private Set getUsedCertificateSet()
  {
    Set usedSet = new HashSet();
    
    for (Enumeration en = keys.keys(); en.hasMoreElements();)
    {
      String alias = (String)en.nextElement();
      
      Certificate[] certs = engineGetCertificateChain(alias);
      
      for (int i = 0; i != certs.length; i++)
      {
        usedSet.add(certs[i]);
      }
    }
    
    for (Enumeration en = this.certs.keys(); en.hasMoreElements();)
    {
      String alias = (String)en.nextElement();
      
      Certificate cert = engineGetCertificate(alias);
      
      usedSet.add(cert);
    }
    
    return usedSet;
  }
  






  private byte[] calculatePbeMac(ASN1ObjectIdentifier oid, byte[] salt, int itCount, char[] password, boolean wrongPkcs12Zero, byte[] data)
    throws Exception
  {
    PBEParameterSpec defParams = new PBEParameterSpec(salt, itCount);
    
    Mac mac = helper.createMac(oid.getId());
    mac.init(new PKCS12Key(password, wrongPkcs12Zero), defParams);
    mac.update(data);
    
    return mac.doFinal();
  }
  
  public static class BCPKCS12KeyStore
    extends PKCS12KeyStoreSpi
  {
    public BCPKCS12KeyStore()
    {
      super(pbeWithSHAAnd3_KeyTripleDES_CBC, pbeWithSHAAnd40BitRC2_CBC);
    }
  }
  
  public static class BCPKCS12KeyStore3DES
    extends PKCS12KeyStoreSpi
  {
    public BCPKCS12KeyStore3DES()
    {
      super(pbeWithSHAAnd3_KeyTripleDES_CBC, pbeWithSHAAnd3_KeyTripleDES_CBC);
    }
  }
  
  public static class DefPKCS12KeyStore
    extends PKCS12KeyStoreSpi
  {
    public DefPKCS12KeyStore()
    {
      super(pbeWithSHAAnd3_KeyTripleDES_CBC, pbeWithSHAAnd40BitRC2_CBC);
    }
  }
  
  public static class DefPKCS12KeyStore3DES
    extends PKCS12KeyStoreSpi
  {
    public DefPKCS12KeyStore3DES()
    {
      super(pbeWithSHAAnd3_KeyTripleDES_CBC, pbeWithSHAAnd3_KeyTripleDES_CBC);
    }
  }
  
  private static class IgnoresCaseHashtable
  {
    private Hashtable orig = new Hashtable();
    private Hashtable keys = new Hashtable();
    
    private IgnoresCaseHashtable() {}
    
    public void put(String key, Object value) { String lower = key == null ? null : Strings.toLowerCase(key);
      String k = (String)keys.get(lower);
      if (k != null)
      {
        orig.remove(k);
      }
      
      keys.put(lower, key);
      orig.put(key, value);
    }
    
    public Enumeration keys()
    {
      return orig.keys();
    }
    
    public Object remove(String alias)
    {
      String k = (String)keys.remove(alias == null ? null : Strings.toLowerCase(alias));
      if (k == null)
      {
        return null;
      }
      
      return orig.remove(k);
    }
    
    public Object get(String alias)
    {
      String k = (String)keys.get(alias == null ? null : Strings.toLowerCase(alias));
      if (k == null)
      {
        return null;
      }
      
      return orig.get(k);
    }
    
    public Enumeration elements()
    {
      return orig.elements();
    }
  }
  
  private static class DefaultSecretKeyProvider
  {
    private final Map KEY_SIZES;
    
    DefaultSecretKeyProvider()
    {
      Map keySizes = new HashMap();
      
      keySizes.put(new ASN1ObjectIdentifier("1.2.840.113533.7.66.10"), Integers.valueOf(128));
      
      keySizes.put(PKCSObjectIdentifiers.des_EDE3_CBC, Integers.valueOf(192));
      
      keySizes.put(NISTObjectIdentifiers.id_aes128_CBC, Integers.valueOf(128));
      keySizes.put(NISTObjectIdentifiers.id_aes192_CBC, Integers.valueOf(192));
      keySizes.put(NISTObjectIdentifiers.id_aes256_CBC, Integers.valueOf(256));
      
      keySizes.put(NTTObjectIdentifiers.id_camellia128_cbc, Integers.valueOf(128));
      keySizes.put(NTTObjectIdentifiers.id_camellia192_cbc, Integers.valueOf(192));
      keySizes.put(NTTObjectIdentifiers.id_camellia256_cbc, Integers.valueOf(256));
      
      keySizes.put(CryptoProObjectIdentifiers.gostR28147_gcfb, Integers.valueOf(256));
      
      KEY_SIZES = Collections.unmodifiableMap(keySizes);
    }
    

    public int getKeySize(AlgorithmIdentifier algorithmIdentifier)
    {
      Integer keySize = (Integer)KEY_SIZES.get(algorithmIdentifier.getAlgorithm());
      
      if (keySize != null)
      {
        return keySize.intValue();
      }
      
      return -1;
    }
  }
}

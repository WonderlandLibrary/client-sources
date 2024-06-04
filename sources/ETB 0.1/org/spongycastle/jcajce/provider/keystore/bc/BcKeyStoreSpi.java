package org.spongycastle.jcajce.provider.keystore.bc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyStoreException;
import java.security.KeyStoreSpi;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.PBEParametersGenerator;
import org.spongycastle.crypto.digests.SHA1Digest;
import org.spongycastle.crypto.generators.PKCS12ParametersGenerator;
import org.spongycastle.crypto.io.DigestInputStream;
import org.spongycastle.crypto.io.DigestOutputStream;
import org.spongycastle.crypto.io.MacInputStream;
import org.spongycastle.crypto.io.MacOutputStream;
import org.spongycastle.crypto.macs.HMac;
import org.spongycastle.jcajce.util.BCJcaJceHelper;
import org.spongycastle.jcajce.util.JcaJceHelper;
import org.spongycastle.jce.interfaces.BCKeyStore;
import org.spongycastle.jce.provider.BouncyCastleProvider;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.io.Streams;
import org.spongycastle.util.io.TeeOutputStream;














public class BcKeyStoreSpi
  extends KeyStoreSpi
  implements BCKeyStore
{
  private static final int STORE_VERSION = 2;
  private static final int STORE_SALT_SIZE = 20;
  private static final String STORE_CIPHER = "PBEWithSHAAndTwofish-CBC";
  private static final int KEY_SALT_SIZE = 20;
  private static final int MIN_ITERATIONS = 1024;
  private static final String KEY_CIPHER = "PBEWithSHAAnd3-KeyTripleDES-CBC";
  static final int NULL = 0;
  static final int CERTIFICATE = 1;
  static final int KEY = 2;
  static final int SECRET = 3;
  static final int SEALED = 4;
  static final int KEY_PRIVATE = 0;
  static final int KEY_PUBLIC = 1;
  static final int KEY_SECRET = 2;
  protected Hashtable table = new Hashtable();
  
  protected SecureRandom random = new SecureRandom();
  
  protected int version;
  
  private final JcaJceHelper helper = new BCJcaJceHelper();
  
  public BcKeyStoreSpi(int version)
  {
    this.version = version;
  }
  
  private class StoreEntry
  {
    int type;
    String alias;
    Object obj;
    Certificate[] certChain;
    Date date = new Date();
    


    StoreEntry(String alias, Certificate obj)
    {
      type = 1;
      this.alias = alias;
      this.obj = obj;
      certChain = null;
    }
    



    StoreEntry(String alias, byte[] obj, Certificate[] certChain)
    {
      type = 3;
      this.alias = alias;
      this.obj = obj;
      this.certChain = certChain;
    }
    




    StoreEntry(String alias, Key key, char[] password, Certificate[] certChain)
      throws Exception
    {
      type = 4;
      this.alias = alias;
      this.certChain = certChain;
      
      byte[] salt = new byte[20];
      
      random.setSeed(System.currentTimeMillis());
      random.nextBytes(salt);
      
      int iterationCount = 1024 + (random.nextInt() & 0x3FF);
      

      ByteArrayOutputStream bOut = new ByteArrayOutputStream();
      DataOutputStream dOut = new DataOutputStream(bOut);
      
      dOut.writeInt(salt.length);
      dOut.write(salt);
      dOut.writeInt(iterationCount);
      
      Cipher cipher = makePBECipher("PBEWithSHAAnd3-KeyTripleDES-CBC", 1, password, salt, iterationCount);
      CipherOutputStream cOut = new CipherOutputStream(dOut, cipher);
      
      dOut = new DataOutputStream(cOut);
      
      BcKeyStoreSpi.this.encodeKey(key, dOut);
      
      dOut.close();
      
      obj = bOut.toByteArray();
    }
    




    StoreEntry(String alias, Date date, int type, Object obj)
    {
      this.alias = alias;
      this.date = date;
      this.type = type;
      this.obj = obj;
    }
    





    StoreEntry(String alias, Date date, int type, Object obj, Certificate[] certChain)
    {
      this.alias = alias;
      this.date = date;
      this.type = type;
      this.obj = obj;
      this.certChain = certChain;
    }
    
    int getType()
    {
      return type;
    }
    
    String getAlias()
    {
      return alias;
    }
    
    Object getObject()
    {
      return obj;
    }
    

    Object getObject(char[] password)
      throws NoSuchAlgorithmException, UnrecoverableKeyException
    {
      if ((password == null) || (password.length == 0))
      {
        if ((obj instanceof Key))
        {
          return obj;
        }
      }
      
      if (type == 4)
      {
        ByteArrayInputStream bIn = new ByteArrayInputStream((byte[])obj);
        DataInputStream dIn = new DataInputStream(bIn);
        
        try
        {
          byte[] salt = new byte[dIn.readInt()];
          
          dIn.readFully(salt);
          
          int iterationCount = dIn.readInt();
          
          Cipher cipher = makePBECipher("PBEWithSHAAnd3-KeyTripleDES-CBC", 2, password, salt, iterationCount);
          
          CipherInputStream cIn = new CipherInputStream(dIn, cipher);
          
          try
          {
            return BcKeyStoreSpi.this.decodeKey(new DataInputStream(cIn));
          }
          catch (Exception x)
          {
            bIn = new ByteArrayInputStream((byte[])obj);
            dIn = new DataInputStream(bIn);
            
            salt = new byte[dIn.readInt()];
            
            dIn.readFully(salt);
            
            iterationCount = dIn.readInt();
            
            cipher = makePBECipher("BrokenPBEWithSHAAnd3-KeyTripleDES-CBC", 2, password, salt, iterationCount);
            
            cIn = new CipherInputStream(dIn, cipher);
            
            Key k = null;
            
            try
            {
              k = BcKeyStoreSpi.this.decodeKey(new DataInputStream(cIn));
            }
            catch (Exception y)
            {
              bIn = new ByteArrayInputStream((byte[])obj);
              dIn = new DataInputStream(bIn);
              
              salt = new byte[dIn.readInt()];
              
              dIn.readFully(salt);
              
              iterationCount = dIn.readInt();
              
              cipher = makePBECipher("OldPBEWithSHAAnd3-KeyTripleDES-CBC", 2, password, salt, iterationCount);
              
              cIn = new CipherInputStream(dIn, cipher);
              
              k = BcKeyStoreSpi.this.decodeKey(new DataInputStream(cIn));
            }
            



            if (k != null)
            {
              ByteArrayOutputStream bOut = new ByteArrayOutputStream();
              DataOutputStream dOut = new DataOutputStream(bOut);
              
              dOut.writeInt(salt.length);
              dOut.write(salt);
              dOut.writeInt(iterationCount);
              
              Cipher out = makePBECipher("PBEWithSHAAnd3-KeyTripleDES-CBC", 1, password, salt, iterationCount);
              CipherOutputStream cOut = new CipherOutputStream(dOut, out);
              
              dOut = new DataOutputStream(cOut);
              
              BcKeyStoreSpi.this.encodeKey(k, dOut);
              
              dOut.close();
              
              obj = bOut.toByteArray();
              
              return k;
            }
            

            throw new UnrecoverableKeyException("no match");
          }
          








          throw new RuntimeException("forget something!");
        }
        catch (Exception e)
        {
          throw new UnrecoverableKeyException("no match");
        }
      }
    }
    









    Certificate[] getCertificateChain()
    {
      return certChain;
    }
    
    Date getDate()
    {
      return date;
    }
  }
  


  private void encodeCertificate(Certificate cert, DataOutputStream dOut)
    throws IOException
  {
    try
    {
      byte[] cEnc = cert.getEncoded();
      
      dOut.writeUTF(cert.getType());
      dOut.writeInt(cEnc.length);
      dOut.write(cEnc);
    }
    catch (CertificateEncodingException ex)
    {
      throw new IOException(ex.toString());
    }
  }
  

  private Certificate decodeCertificate(DataInputStream dIn)
    throws IOException
  {
    String type = dIn.readUTF();
    byte[] cEnc = new byte[dIn.readInt()];
    
    dIn.readFully(cEnc);
    
    try
    {
      CertificateFactory cFact = helper.createCertificateFactory(type);
      ByteArrayInputStream bIn = new ByteArrayInputStream(cEnc);
      
      return cFact.generateCertificate(bIn);
    }
    catch (NoSuchProviderException ex)
    {
      throw new IOException(ex.toString());
    }
    catch (CertificateException ex)
    {
      throw new IOException(ex.toString());
    }
  }
  


  private void encodeKey(Key key, DataOutputStream dOut)
    throws IOException
  {
    byte[] enc = key.getEncoded();
    
    if ((key instanceof PrivateKey))
    {
      dOut.write(0);
    }
    else if ((key instanceof PublicKey))
    {
      dOut.write(1);
    }
    else
    {
      dOut.write(2);
    }
    
    dOut.writeUTF(key.getFormat());
    dOut.writeUTF(key.getAlgorithm());
    dOut.writeInt(enc.length);
    dOut.write(enc);
  }
  

  private Key decodeKey(DataInputStream dIn)
    throws IOException
  {
    int keyType = dIn.read();
    String format = dIn.readUTF();
    String algorithm = dIn.readUTF();
    byte[] enc = new byte[dIn.readInt()];
    

    dIn.readFully(enc);
    KeySpec spec;
    if ((format.equals("PKCS#8")) || (format.equals("PKCS8")))
    {
      spec = new PKCS8EncodedKeySpec(enc);
    } else { KeySpec spec;
      if ((format.equals("X.509")) || (format.equals("X509")))
      {
        spec = new X509EncodedKeySpec(enc);
      } else {
        if (format.equals("RAW"))
        {
          return new SecretKeySpec(enc, algorithm);
        }
        

        throw new IOException("Key format " + format + " not recognised!");
      }
    }
    try {
      KeySpec spec;
      switch (keyType)
      {
      case 0: 
        return helper.createKeyFactory(algorithm).generatePrivate(spec);
      case 1: 
        return helper.createKeyFactory(algorithm).generatePublic(spec);
      case 2: 
        return helper.createSecretKeyFactory(algorithm).generateSecret(spec);
      }
      throw new IOException("Key type " + keyType + " not recognised!");

    }
    catch (Exception e)
    {
      throw new IOException("Exception creating key: " + e.toString());
    }
  }
  





  protected Cipher makePBECipher(String algorithm, int mode, char[] password, byte[] salt, int iterationCount)
    throws IOException
  {
    try
    {
      PBEKeySpec pbeSpec = new PBEKeySpec(password);
      SecretKeyFactory keyFact = helper.createSecretKeyFactory(algorithm);
      PBEParameterSpec defParams = new PBEParameterSpec(salt, iterationCount);
      
      Cipher cipher = helper.createCipher(algorithm);
      
      cipher.init(mode, keyFact.generateSecret(pbeSpec), defParams);
      
      return cipher;
    }
    catch (Exception e)
    {
      throw new IOException("Error initialising store of key store: " + e);
    }
  }
  

  public void setRandom(SecureRandom rand)
  {
    random = rand;
  }
  
  public Enumeration engineAliases()
  {
    return table.keys();
  }
  

  public boolean engineContainsAlias(String alias)
  {
    return table.get(alias) != null;
  }
  

  public void engineDeleteEntry(String alias)
    throws KeyStoreException
  {
    Object entry = table.get(alias);
    
    if (entry == null)
    {
      return;
    }
    
    table.remove(alias);
  }
  

  public Certificate engineGetCertificate(String alias)
  {
    StoreEntry entry = (StoreEntry)table.get(alias);
    
    if (entry != null)
    {
      if (entry.getType() == 1)
      {
        return (Certificate)entry.getObject();
      }
      

      Certificate[] chain = entry.getCertificateChain();
      
      if (chain != null)
      {
        return chain[0];
      }
    }
    

    return null;
  }
  

  public String engineGetCertificateAlias(Certificate cert)
  {
    Enumeration e = table.elements();
    while (e.hasMoreElements())
    {
      StoreEntry entry = (StoreEntry)e.nextElement();
      
      if ((entry.getObject() instanceof Certificate))
      {
        Certificate c = (Certificate)entry.getObject();
        
        if (c.equals(cert))
        {
          return entry.getAlias();
        }
      }
      else
      {
        Certificate[] chain = entry.getCertificateChain();
        
        if ((chain != null) && (chain[0].equals(cert)))
        {
          return entry.getAlias();
        }
      }
    }
    
    return null;
  }
  

  public Certificate[] engineGetCertificateChain(String alias)
  {
    StoreEntry entry = (StoreEntry)table.get(alias);
    
    if (entry != null)
    {
      return entry.getCertificateChain();
    }
    
    return null;
  }
  
  public Date engineGetCreationDate(String alias)
  {
    StoreEntry entry = (StoreEntry)table.get(alias);
    
    if (entry != null)
    {
      return entry.getDate();
    }
    
    return null;
  }
  


  public Key engineGetKey(String alias, char[] password)
    throws NoSuchAlgorithmException, UnrecoverableKeyException
  {
    StoreEntry entry = (StoreEntry)table.get(alias);
    
    if ((entry == null) || (entry.getType() == 1))
    {
      return null;
    }
    
    return (Key)entry.getObject(password);
  }
  

  public boolean engineIsCertificateEntry(String alias)
  {
    StoreEntry entry = (StoreEntry)table.get(alias);
    
    if ((entry != null) && (entry.getType() == 1))
    {
      return true;
    }
    
    return false;
  }
  

  public boolean engineIsKeyEntry(String alias)
  {
    StoreEntry entry = (StoreEntry)table.get(alias);
    
    if ((entry != null) && (entry.getType() != 1))
    {
      return true;
    }
    
    return false;
  }
  


  public void engineSetCertificateEntry(String alias, Certificate cert)
    throws KeyStoreException
  {
    StoreEntry entry = (StoreEntry)table.get(alias);
    
    if ((entry != null) && (entry.getType() != 1))
    {
      throw new KeyStoreException("key store already has a key entry with alias " + alias);
    }
    
    table.put(alias, new StoreEntry(alias, cert));
  }
  



  public void engineSetKeyEntry(String alias, byte[] key, Certificate[] chain)
    throws KeyStoreException
  {
    table.put(alias, new StoreEntry(alias, key, chain));
  }
  




  public void engineSetKeyEntry(String alias, Key key, char[] password, Certificate[] chain)
    throws KeyStoreException
  {
    if (((key instanceof PrivateKey)) && (chain == null))
    {
      throw new KeyStoreException("no certificate chain for private key");
    }
    
    try
    {
      table.put(alias, new StoreEntry(alias, key, password, chain));
    }
    catch (Exception e)
    {
      throw new KeyStoreException(e.toString());
    }
  }
  
  public int engineSize()
  {
    return table.size();
  }
  

  protected void loadStore(InputStream in)
    throws IOException
  {
    DataInputStream dIn = new DataInputStream(in);
    int type = dIn.read();
    
    while (type > 0)
    {
      String alias = dIn.readUTF();
      Date date = new Date(dIn.readLong());
      int chainLength = dIn.readInt();
      Certificate[] chain = null;
      
      if (chainLength != 0)
      {
        chain = new Certificate[chainLength];
        
        for (int i = 0; i != chainLength; i++)
        {
          chain[i] = decodeCertificate(dIn);
        }
      }
      
      switch (type)
      {
      case 1: 
        Certificate cert = decodeCertificate(dIn);
        
        table.put(alias, new StoreEntry(alias, date, 1, cert));
        break;
      case 2: 
        Key key = decodeKey(dIn);
        table.put(alias, new StoreEntry(alias, date, 2, key, chain));
        break;
      case 3: 
      case 4: 
        byte[] b = new byte[dIn.readInt()];
        
        dIn.readFully(b);
        table.put(alias, new StoreEntry(alias, date, type, b, chain));
        break;
      default: 
        throw new RuntimeException("Unknown object type in store.");
      }
      
      type = dIn.read();
    }
  }
  

  protected void saveStore(OutputStream out)
    throws IOException
  {
    Enumeration e = table.elements();
    DataOutputStream dOut = new DataOutputStream(out);
    
    while (e.hasMoreElements())
    {
      StoreEntry entry = (StoreEntry)e.nextElement();
      
      dOut.write(entry.getType());
      dOut.writeUTF(entry.getAlias());
      dOut.writeLong(entry.getDate().getTime());
      
      Certificate[] chain = entry.getCertificateChain();
      if (chain == null)
      {
        dOut.writeInt(0);
      }
      else
      {
        dOut.writeInt(chain.length);
        for (int i = 0; i != chain.length; i++)
        {
          encodeCertificate(chain[i], dOut);
        }
      }
      
      switch (entry.getType())
      {
      case 1: 
        encodeCertificate((Certificate)entry.getObject(), dOut);
        break;
      case 2: 
        encodeKey((Key)entry.getObject(), dOut);
        break;
      case 3: 
      case 4: 
        byte[] b = (byte[])entry.getObject();
        
        dOut.writeInt(b.length);
        dOut.write(b);
        break;
      default: 
        throw new RuntimeException("Unknown object type in store.");
      }
      
    }
    dOut.write(0);
  }
  


  public void engineLoad(InputStream stream, char[] password)
    throws IOException
  {
    table.clear();
    
    if (stream == null)
    {
      return;
    }
    
    DataInputStream dIn = new DataInputStream(stream);
    int version = dIn.readInt();
    
    if (version != 2)
    {
      if ((version != 0) && (version != 1))
      {
        throw new IOException("Wrong version of key store.");
      }
    }
    
    int saltLength = dIn.readInt();
    if (saltLength <= 0)
    {
      throw new IOException("Invalid salt detected");
    }
    
    byte[] salt = new byte[saltLength];
    
    dIn.readFully(salt);
    
    int iterationCount = dIn.readInt();
    



    HMac hMac = new HMac(new SHA1Digest());
    if ((password != null) && (password.length != 0))
    {
      byte[] passKey = PBEParametersGenerator.PKCS12PasswordToBytes(password);
      
      PBEParametersGenerator pbeGen = new PKCS12ParametersGenerator(new SHA1Digest());
      pbeGen.init(passKey, salt, iterationCount);
      
      CipherParameters macParams;
      CipherParameters macParams;
      if (version != 2)
      {
        macParams = pbeGen.generateDerivedMacParameters(hMac.getMacSize());
      }
      else
      {
        macParams = pbeGen.generateDerivedMacParameters(hMac.getMacSize() * 8);
      }
      
      Arrays.fill(passKey, (byte)0);
      
      hMac.init(macParams);
      MacInputStream mIn = new MacInputStream(dIn, hMac);
      
      loadStore(mIn);
      

      byte[] mac = new byte[hMac.getMacSize()];
      hMac.doFinal(mac, 0);
      


      byte[] oldMac = new byte[hMac.getMacSize()];
      dIn.readFully(oldMac);
      
      if (!Arrays.constantTimeAreEqual(mac, oldMac))
      {
        table.clear();
        throw new IOException("KeyStore integrity check failed.");
      }
    }
    else
    {
      loadStore(dIn);
      


      byte[] oldMac = new byte[hMac.getMacSize()];
      dIn.readFully(oldMac);
    }
  }
  

  public void engineStore(OutputStream stream, char[] password)
    throws IOException
  {
    DataOutputStream dOut = new DataOutputStream(stream);
    byte[] salt = new byte[20];
    int iterationCount = 1024 + (random.nextInt() & 0x3FF);
    
    random.nextBytes(salt);
    
    dOut.writeInt(version);
    dOut.writeInt(salt.length);
    dOut.write(salt);
    dOut.writeInt(iterationCount);
    
    HMac hMac = new HMac(new SHA1Digest());
    MacOutputStream mOut = new MacOutputStream(hMac);
    PBEParametersGenerator pbeGen = new PKCS12ParametersGenerator(new SHA1Digest());
    byte[] passKey = PBEParametersGenerator.PKCS12PasswordToBytes(password);
    
    pbeGen.init(passKey, salt, iterationCount);
    
    if (version < 2)
    {
      hMac.init(pbeGen.generateDerivedMacParameters(hMac.getMacSize()));
    }
    else
    {
      hMac.init(pbeGen.generateDerivedMacParameters(hMac.getMacSize() * 8));
    }
    
    for (int i = 0; i != passKey.length; i++)
    {
      passKey[i] = 0;
    }
    
    saveStore(new TeeOutputStream(dOut, mOut));
    
    byte[] mac = new byte[hMac.getMacSize()];
    
    hMac.doFinal(mac, 0);
    
    dOut.write(mac);
    
    dOut.close();
  }
  










  public static class BouncyCastleStore
    extends BcKeyStoreSpi
  {
    public BouncyCastleStore()
    {
      super();
    }
    


    public void engineLoad(InputStream stream, char[] password)
      throws IOException
    {
      table.clear();
      
      if (stream == null)
      {
        return;
      }
      
      DataInputStream dIn = new DataInputStream(stream);
      int version = dIn.readInt();
      
      if (version != 2)
      {
        if ((version != 0) && (version != 1))
        {
          throw new IOException("Wrong version of key store.");
        }
      }
      
      byte[] salt = new byte[dIn.readInt()];
      
      if (salt.length != 20)
      {
        throw new IOException("Key store corrupted.");
      }
      
      dIn.readFully(salt);
      
      int iterationCount = dIn.readInt();
      
      if ((iterationCount < 0) || (iterationCount > 65536))
      {
        throw new IOException("Key store corrupted.");
      }
      String cipherAlg;
      String cipherAlg;
      if (version == 0)
      {
        cipherAlg = "OldPBEWithSHAAndTwofish-CBC";
      }
      else
      {
        cipherAlg = "PBEWithSHAAndTwofish-CBC";
      }
      
      Cipher cipher = makePBECipher(cipherAlg, 2, password, salt, iterationCount);
      CipherInputStream cIn = new CipherInputStream(dIn, cipher);
      
      Digest dig = new SHA1Digest();
      DigestInputStream dgIn = new DigestInputStream(cIn, dig);
      
      loadStore(dgIn);
      

      byte[] hash = new byte[dig.getDigestSize()];
      dig.doFinal(hash, 0);
      


      byte[] oldHash = new byte[dig.getDigestSize()];
      Streams.readFully(cIn, oldHash);
      
      if (!Arrays.constantTimeAreEqual(hash, oldHash))
      {
        table.clear();
        throw new IOException("KeyStore integrity check failed.");
      }
    }
    

    public void engineStore(OutputStream stream, char[] password)
      throws IOException
    {
      DataOutputStream dOut = new DataOutputStream(stream);
      byte[] salt = new byte[20];
      int iterationCount = 1024 + (random.nextInt() & 0x3FF);
      
      random.nextBytes(salt);
      
      dOut.writeInt(version);
      dOut.writeInt(salt.length);
      dOut.write(salt);
      dOut.writeInt(iterationCount);
      
      Cipher cipher = makePBECipher("PBEWithSHAAndTwofish-CBC", 1, password, salt, iterationCount);
      
      CipherOutputStream cOut = new CipherOutputStream(dOut, cipher);
      DigestOutputStream dgOut = new DigestOutputStream(new SHA1Digest());
      
      saveStore(new TeeOutputStream(cOut, dgOut));
      
      byte[] dig = dgOut.getDigest();
      
      cOut.write(dig);
      
      cOut.close();
    }
  }
  
  static Provider getBouncyCastleProvider()
  {
    if (Security.getProvider("SC") != null)
    {
      return Security.getProvider("SC");
    }
    

    return new BouncyCastleProvider();
  }
  

  public static class Std
    extends BcKeyStoreSpi
  {
    public Std()
    {
      super();
    }
  }
  
  public static class Version1
    extends BcKeyStoreSpi
  {
    public Version1()
    {
      super();
    }
  }
}

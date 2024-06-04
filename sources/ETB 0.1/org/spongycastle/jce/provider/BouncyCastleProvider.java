package org.spongycastle.jce.provider;

import java.io.IOException;
import java.security.AccessController;
import java.security.PrivateKey;
import java.security.PrivilegedAction;
import java.security.Provider;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.config.ProviderConfiguration;
import org.spongycastle.jcajce.provider.symmetric.util.ClassUtil;
import org.spongycastle.jcajce.provider.util.AlgorithmProvider;
import org.spongycastle.jcajce.provider.util.AsymmetricKeyInfoConverter;
import org.spongycastle.pqc.asn1.PQCObjectIdentifiers;
import org.spongycastle.pqc.jcajce.provider.mceliece.McElieceCCA2KeyFactorySpi;
import org.spongycastle.pqc.jcajce.provider.mceliece.McElieceKeyFactorySpi;
import org.spongycastle.pqc.jcajce.provider.newhope.NHKeyFactorySpi;
import org.spongycastle.pqc.jcajce.provider.rainbow.RainbowKeyFactorySpi;
import org.spongycastle.pqc.jcajce.provider.sphincs.Sphincs256KeyFactorySpi;
import org.spongycastle.pqc.jcajce.provider.xmss.XMSSKeyFactorySpi;
import org.spongycastle.pqc.jcajce.provider.xmss.XMSSMTKeyFactorySpi;























public final class BouncyCastleProvider
  extends Provider
  implements ConfigurableProvider
{
  private static String info = "BouncyCastle Security Provider v1.58";
  
  public static final String PROVIDER_NAME = "SC";
  
  public static final ProviderConfiguration CONFIGURATION = new BouncyCastleProviderConfiguration();
  
  private static final Map keyInfoConverters = new HashMap();
  


  private static final String SYMMETRIC_PACKAGE = "org.spongycastle.jcajce.provider.symmetric.";
  

  private static final String[] SYMMETRIC_GENERIC = { "PBEPBKDF1", "PBEPBKDF2", "PBEPKCS12", "TLSKDF" };
  



  private static final String[] SYMMETRIC_MACS = { "SipHash", "Poly1305" };
  



  private static final String[] SYMMETRIC_CIPHERS = { "AES", "ARC4", "ARIA", "Blowfish", "Camellia", "CAST5", "CAST6", "ChaCha", "DES", "DESede", "GOST28147", "Grainv1", "Grain128", "HC128", "HC256", "IDEA", "Noekeon", "RC2", "RC5", "RC6", "Rijndael", "Salsa20", "SEED", "Serpent", "Shacal2", "Skipjack", "SM4", "TEA", "Twofish", "Threefish", "VMPC", "VMPCKSA3", "XTEA", "XSalsa20", "OpenSSLPBKDF", "DSTU7624" };
  






  private static final String ASYMMETRIC_PACKAGE = "org.spongycastle.jcajce.provider.asymmetric.";
  





  private static final String[] ASYMMETRIC_GENERIC = { "X509", "IES" };
  



  private static final String[] ASYMMETRIC_CIPHERS = { "DSA", "DH", "EC", "RSA", "GOST", "ECGOST", "ElGamal", "DSTU4145", "GM" };
  



  private static final String DIGEST_PACKAGE = "org.spongycastle.jcajce.provider.digest.";
  


  private static final String[] DIGESTS = { "GOST3411", "Keccak", "MD2", "MD4", "MD5", "SHA1", "RIPEMD128", "RIPEMD160", "RIPEMD256", "RIPEMD320", "SHA224", "SHA256", "SHA384", "SHA512", "SHA3", "Skein", "SM3", "Tiger", "Whirlpool", "Blake2b", "DSTU7564" };
  



  private static final String KEYSTORE_PACKAGE = "org.spongycastle.jcajce.provider.keystore.";
  



  private static final String[] KEYSTORES = { "BC", "BCFKS", "PKCS12" };
  



  private static final String SECURE_RANDOM_PACKAGE = "org.spongycastle.jcajce.provider.drbg.";
  


  private static final String[] SECURE_RANDOMS = { "DRBG" };
  








  public BouncyCastleProvider()
  {
    super("SC", 1.58D, info);
    
    AccessController.doPrivileged(new PrivilegedAction()
    {
      public Object run()
      {
        BouncyCastleProvider.this.setup();
        return null;
      }
    });
  }
  
  private void setup()
  {
    loadAlgorithms("org.spongycastle.jcajce.provider.digest.", DIGESTS);
    
    loadAlgorithms("org.spongycastle.jcajce.provider.symmetric.", SYMMETRIC_GENERIC);
    
    loadAlgorithms("org.spongycastle.jcajce.provider.symmetric.", SYMMETRIC_MACS);
    
    loadAlgorithms("org.spongycastle.jcajce.provider.symmetric.", SYMMETRIC_CIPHERS);
    
    loadAlgorithms("org.spongycastle.jcajce.provider.asymmetric.", ASYMMETRIC_GENERIC);
    
    loadAlgorithms("org.spongycastle.jcajce.provider.asymmetric.", ASYMMETRIC_CIPHERS);
    
    loadAlgorithms("org.spongycastle.jcajce.provider.keystore.", KEYSTORES);
    
    loadAlgorithms("org.spongycastle.jcajce.provider.drbg.", SECURE_RANDOMS);
    
    loadPQCKeys();
    


    put("X509Store.CERTIFICATE/COLLECTION", "org.spongycastle.jce.provider.X509StoreCertCollection");
    put("X509Store.ATTRIBUTECERTIFICATE/COLLECTION", "org.spongycastle.jce.provider.X509StoreAttrCertCollection");
    put("X509Store.CRL/COLLECTION", "org.spongycastle.jce.provider.X509StoreCRLCollection");
    put("X509Store.CERTIFICATEPAIR/COLLECTION", "org.spongycastle.jce.provider.X509StoreCertPairCollection");
    
    put("X509Store.CERTIFICATE/LDAP", "org.spongycastle.jce.provider.X509StoreLDAPCerts");
    put("X509Store.CRL/LDAP", "org.spongycastle.jce.provider.X509StoreLDAPCRLs");
    put("X509Store.ATTRIBUTECERTIFICATE/LDAP", "org.spongycastle.jce.provider.X509StoreLDAPAttrCerts");
    put("X509Store.CERTIFICATEPAIR/LDAP", "org.spongycastle.jce.provider.X509StoreLDAPCertPairs");
    



    put("X509StreamParser.CERTIFICATE", "org.spongycastle.jce.provider.X509CertParser");
    put("X509StreamParser.ATTRIBUTECERTIFICATE", "org.spongycastle.jce.provider.X509AttrCertParser");
    put("X509StreamParser.CRL", "org.spongycastle.jce.provider.X509CRLParser");
    put("X509StreamParser.CERTIFICATEPAIR", "org.spongycastle.jce.provider.X509CertPairParser");
    



    put("Cipher.BROKENPBEWITHMD5ANDDES", "org.spongycastle.jce.provider.BrokenJCEBlockCipher$BrokePBEWithMD5AndDES");
    
    put("Cipher.BROKENPBEWITHSHA1ANDDES", "org.spongycastle.jce.provider.BrokenJCEBlockCipher$BrokePBEWithSHA1AndDES");
    

    put("Cipher.OLDPBEWITHSHAANDTWOFISH-CBC", "org.spongycastle.jce.provider.BrokenJCEBlockCipher$OldPBEWithSHAAndTwofish");
    

    put("CertPathValidator.RFC3281", "org.spongycastle.jce.provider.PKIXAttrCertPathValidatorSpi");
    put("CertPathBuilder.RFC3281", "org.spongycastle.jce.provider.PKIXAttrCertPathBuilderSpi");
    put("CertPathValidator.RFC3280", "org.spongycastle.jce.provider.PKIXCertPathValidatorSpi");
    put("CertPathBuilder.RFC3280", "org.spongycastle.jce.provider.PKIXCertPathBuilderSpi");
    put("CertPathValidator.PKIX", "org.spongycastle.jce.provider.PKIXCertPathValidatorSpi");
    put("CertPathBuilder.PKIX", "org.spongycastle.jce.provider.PKIXCertPathBuilderSpi");
    put("CertStore.Collection", "org.spongycastle.jce.provider.CertStoreCollectionSpi");
    put("CertStore.LDAP", "org.spongycastle.jce.provider.X509LDAPCertStoreSpi");
    put("CertStore.Multi", "org.spongycastle.jce.provider.MultiCertStoreSpi");
    put("Alg.Alias.CertStore.X509LDAP", "LDAP");
  }
  
  private void loadAlgorithms(String packageName, String[] names)
  {
    for (int i = 0; i != names.length; i++)
    {
      Class clazz = ClassUtil.loadClass(BouncyCastleProvider.class, packageName + names[i] + "$Mappings");
      
      if (clazz != null)
      {
        try
        {
          ((AlgorithmProvider)clazz.newInstance()).configure(this);
        }
        catch (Exception e)
        {
          throw new InternalError("cannot create instance of " + packageName + names[i] + "$Mappings : " + e);
        }
      }
    }
  }
  

  private void loadPQCKeys()
  {
    addKeyInfoConverter(PQCObjectIdentifiers.sphincs256, new Sphincs256KeyFactorySpi());
    addKeyInfoConverter(PQCObjectIdentifiers.newHope, new NHKeyFactorySpi());
    addKeyInfoConverter(PQCObjectIdentifiers.xmss, new XMSSKeyFactorySpi());
    addKeyInfoConverter(PQCObjectIdentifiers.xmss_mt, new XMSSMTKeyFactorySpi());
    addKeyInfoConverter(PQCObjectIdentifiers.mcEliece, new McElieceKeyFactorySpi());
    addKeyInfoConverter(PQCObjectIdentifiers.mcElieceCca2, new McElieceCCA2KeyFactorySpi());
    addKeyInfoConverter(PQCObjectIdentifiers.rainbow, new RainbowKeyFactorySpi());
  }
  
  public void setParameter(String parameterName, Object parameter)
  {
    synchronized (CONFIGURATION)
    {
      ((BouncyCastleProviderConfiguration)CONFIGURATION).setParameter(parameterName, parameter);
    }
  }
  
  public boolean hasAlgorithm(String type, String name)
  {
    return (containsKey(type + "." + name)) || (containsKey("Alg.Alias." + type + "." + name));
  }
  
  public void addAlgorithm(String key, String value)
  {
    if (containsKey(key))
    {
      throw new IllegalStateException("duplicate provider key (" + key + ") found");
    }
    
    put(key, value);
  }
  
  public void addAlgorithm(String type, ASN1ObjectIdentifier oid, String className)
  {
    addAlgorithm(type + "." + oid, className);
    addAlgorithm(type + ".OID." + oid, className);
  }
  
  public void addKeyInfoConverter(ASN1ObjectIdentifier oid, AsymmetricKeyInfoConverter keyInfoConverter)
  {
    synchronized (keyInfoConverters)
    {
      keyInfoConverters.put(oid, keyInfoConverter);
    }
  }
  
  public void addAttributes(String key, Map<String, String> attributeMap)
  {
    for (Iterator it = attributeMap.keySet().iterator(); it.hasNext();)
    {
      String attributeName = (String)it.next();
      String attributeKey = key + " " + attributeName;
      if (containsKey(attributeKey))
      {
        throw new IllegalStateException("duplicate provider attribute key (" + attributeKey + ") found");
      }
      
      put(attributeKey, attributeMap.get(attributeName));
    }
  }
  
  private static AsymmetricKeyInfoConverter getAsymmetricKeyInfoConverter(ASN1ObjectIdentifier algorithm)
  {
    synchronized (keyInfoConverters)
    {
      return (AsymmetricKeyInfoConverter)keyInfoConverters.get(algorithm);
    }
  }
  
  public static PublicKey getPublicKey(SubjectPublicKeyInfo publicKeyInfo)
    throws IOException
  {
    AsymmetricKeyInfoConverter converter = getAsymmetricKeyInfoConverter(publicKeyInfo.getAlgorithm().getAlgorithm());
    
    if (converter == null)
    {
      return null;
    }
    
    return converter.generatePublic(publicKeyInfo);
  }
  
  public static PrivateKey getPrivateKey(PrivateKeyInfo privateKeyInfo)
    throws IOException
  {
    AsymmetricKeyInfoConverter converter = getAsymmetricKeyInfoConverter(privateKeyInfo.getPrivateKeyAlgorithm().getAlgorithm());
    
    if (converter == null)
    {
      return null;
    }
    
    return converter.generatePrivate(privateKeyInfo);
  }
}

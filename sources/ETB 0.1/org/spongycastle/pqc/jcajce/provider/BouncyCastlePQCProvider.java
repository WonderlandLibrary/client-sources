package org.spongycastle.pqc.jcajce.provider;

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
import org.spongycastle.jcajce.provider.util.AlgorithmProvider;
import org.spongycastle.jcajce.provider.util.AsymmetricKeyInfoConverter;

public class BouncyCastlePQCProvider
  extends Provider implements ConfigurableProvider
{
  private static String info = "BouncyCastle Post-Quantum Security Provider v1.58";
  
  public static String PROVIDER_NAME = "BCPQC";
  
  public static final ProviderConfiguration CONFIGURATION = null;
  

  private static final Map keyInfoConverters = new HashMap();
  

  private static final String ALGORITHM_PACKAGE = "org.spongycastle.pqc.jcajce.provider.";
  

  private static final String[] ALGORITHMS = { "Rainbow", "McEliece", "SPHINCS", "NH", "XMSS" };
  








  public BouncyCastlePQCProvider()
  {
    super(PROVIDER_NAME, 1.58D, info);
    
    AccessController.doPrivileged(new PrivilegedAction()
    {
      public Object run()
      {
        BouncyCastlePQCProvider.this.setup();
        return null;
      }
    });
  }
  
  private void setup()
  {
    loadAlgorithms("org.spongycastle.pqc.jcajce.provider.", ALGORITHMS);
  }
  
  private void loadAlgorithms(String packageName, String[] names)
  {
    for (int i = 0; i != names.length; i++)
    {
      Class clazz = loadClass(BouncyCastlePQCProvider.class, packageName + names[i] + "$Mappings");
      
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
  

  public void setParameter(String parameterName, Object parameter)
  {
    synchronized (CONFIGURATION) {}
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
    if (!containsKey(type + "." + className))
    {
      throw new IllegalStateException("primary key (" + type + "." + className + ") not found");
    }
    
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
  
  static Class loadClass(Class sourceClass, String className)
  {
    try
    {
      ClassLoader loader = sourceClass.getClassLoader();
      if (loader != null)
      {
        return loader.loadClass(className);
      }
      

      (Class)AccessController.doPrivileged(new PrivilegedAction()
      {
        public Object run()
        {
          try
          {
            return Class.forName(val$className);
          }
          catch (Exception localException) {}
          



          return null;
        }
      });
    }
    catch (ClassNotFoundException localClassNotFoundException) {}
    




    return null;
  }
}

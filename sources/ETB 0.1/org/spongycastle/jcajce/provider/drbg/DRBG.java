package org.spongycastle.jcajce.provider.drbg;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.SecureRandomSpi;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.spongycastle.crypto.digests.SHA512Digest;
import org.spongycastle.crypto.macs.HMac;
import org.spongycastle.crypto.prng.EntropySource;
import org.spongycastle.crypto.prng.EntropySourceProvider;
import org.spongycastle.crypto.prng.SP800SecureRandom;
import org.spongycastle.crypto.prng.SP800SecureRandomBuilder;
import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.symmetric.util.ClassUtil;
import org.spongycastle.jcajce.provider.util.AsymmetricAlgorithmProvider;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Pack;
import org.spongycastle.util.Strings;


public class DRBG
{
  private static final String PREFIX = DRBG.class.getName();
  

  private static final String[][] initialEntropySourceNames = { { "sun.security.provider.Sun", "sun.security.provider.SecureRandom" }, { "org.apache.harmony.security.provider.crypto.CryptoProvider", "org.apache.harmony.security.provider.crypto.SHA1PRNG_SecureRandomImpl" }, { "com.android.org.conscrypt.OpenSSLProvider", "com.android.org.conscrypt.OpenSSLRandom" }, { "org.conscrypt.OpenSSLProvider", "org.conscrypt.OpenSSLRandom" } };
  









  private static final Object[] initialEntropySourceAndSpi = findSource();
  
  public DRBG() {}
  
  private static final Object[] findSource() {
    for (int t = 0; t < initialEntropySourceNames.length; t++)
    {
      String[] pair = initialEntropySourceNames[t];
      try
      {
        return new Object[] { Class.forName(pair[0]).newInstance(), Class.forName(pair[1]).newInstance() };
      }
      catch (Throwable ex) {}
    }
    





    return null;
  }
  
  private static class CoreSecureRandom
    extends SecureRandom
  {
    CoreSecureRandom()
    {
      super((Provider)DRBG.initialEntropySourceAndSpi[0]);
    }
  }
  


  private static SecureRandom createInitialEntropySource()
  {
    if (initialEntropySourceAndSpi != null)
    {
      return new CoreSecureRandom();
    }
    

    return new SecureRandom();
  }
  

  private static EntropySourceProvider createEntropySource()
  {
    String sourceClass = System.getProperty("org.spongycastle.drbg.entropysource");
    
    (EntropySourceProvider)AccessController.doPrivileged(new PrivilegedAction()
    {
      public EntropySourceProvider run()
      {
        try
        {
          Class clazz = ClassUtil.loadClass(DRBG.class, val$sourceClass);
          
          return (EntropySourceProvider)clazz.newInstance();
        }
        catch (Exception e)
        {
          throw new IllegalStateException("entropy source " + val$sourceClass + " not created: " + e.getMessage(), e);
        }
      }
    });
  }
  
  private static SecureRandom createBaseRandom(boolean isPredictionResistant)
  {
    if (System.getProperty("org.spongycastle.drbg.entropysource") != null)
    {
      EntropySourceProvider entropyProvider = createEntropySource();
      
      EntropySource initSource = entropyProvider.get(128);
      

      byte[] personalisationString = isPredictionResistant ? generateDefaultPersonalizationString(initSource.getEntropy()) : generateNonceIVPersonalizationString(initSource.getEntropy());
      
      return new SP800SecureRandomBuilder(entropyProvider)
        .setPersonalizationString(personalisationString)
        .buildHash(new SHA512Digest(), Arrays.concatenate(initSource.getEntropy(), initSource.getEntropy()), isPredictionResistant);
    }
    

    SecureRandom randomSource = new HybridSecureRandom();
    

    byte[] personalisationString = isPredictionResistant ? generateDefaultPersonalizationString(randomSource.generateSeed(16)) : generateNonceIVPersonalizationString(randomSource.generateSeed(16));
    
    return new SP800SecureRandomBuilder(randomSource, true)
      .setPersonalizationString(personalisationString)
      .buildHash(new SHA512Digest(), randomSource.generateSeed(32), isPredictionResistant);
  }
  

  public static class Default
    extends SecureRandomSpi
  {
    private static final SecureRandom random = DRBG.createBaseRandom(true);
    

    public Default() {}
    

    protected void engineSetSeed(byte[] bytes)
    {
      random.setSeed(bytes);
    }
    
    protected void engineNextBytes(byte[] bytes)
    {
      random.nextBytes(bytes);
    }
    
    protected byte[] engineGenerateSeed(int numBytes)
    {
      return random.generateSeed(numBytes);
    }
  }
  
  public static class NonceAndIV
    extends SecureRandomSpi
  {
    private static final SecureRandom random = DRBG.createBaseRandom(false);
    

    public NonceAndIV() {}
    

    protected void engineSetSeed(byte[] bytes)
    {
      random.setSeed(bytes);
    }
    
    protected void engineNextBytes(byte[] bytes)
    {
      random.nextBytes(bytes);
    }
    
    protected byte[] engineGenerateSeed(int numBytes)
    {
      return random.generateSeed(numBytes);
    }
  }
  

  public static class Mappings
    extends AsymmetricAlgorithmProvider
  {
    public Mappings() {}
    

    public void configure(ConfigurableProvider provider)
    {
      provider.addAlgorithm("SecureRandom.DEFAULT", DRBG.PREFIX + "$Default");
      provider.addAlgorithm("SecureRandom.NONCEANDIV", DRBG.PREFIX + "$NonceAndIV");
    }
  }
  
  private static byte[] generateDefaultPersonalizationString(byte[] seed)
  {
    return Arrays.concatenate(Strings.toByteArray("Default"), seed, 
      Pack.longToBigEndian(Thread.currentThread().getId()), Pack.longToBigEndian(System.currentTimeMillis()));
  }
  
  private static byte[] generateNonceIVPersonalizationString(byte[] seed)
  {
    return Arrays.concatenate(Strings.toByteArray("Nonce"), seed, 
      Pack.longToLittleEndian(Thread.currentThread().getId()), Pack.longToLittleEndian(System.currentTimeMillis()));
  }
  
  private static class HybridSecureRandom
    extends SecureRandom
  {
    private final AtomicBoolean seedAvailable = new AtomicBoolean(false);
    private final AtomicInteger samples = new AtomicInteger(0);
    private final SecureRandom baseRandom = DRBG.access$300();
    
    private final SP800SecureRandom drbg;
    
    HybridSecureRandom()
    {
      super(null);
      







      drbg = new SP800SecureRandomBuilder(new EntropySourceProvider()
      {
        public EntropySource get(int bitsRequired)
        {
          return new DRBG.HybridSecureRandom.SignallingEntropySource(DRBG.HybridSecureRandom.this, bitsRequired);
        }
        
      }).setPersonalizationString(Strings.toByteArray("Bouncy Castle Hybrid Entropy Source"))
        .buildHMAC(new HMac(new SHA512Digest()), baseRandom.generateSeed(32), false);
    }
    
    public void setSeed(byte[] seed)
    {
      if (drbg != null)
      {
        drbg.setSeed(seed);
      }
    }
    
    public void setSeed(long seed)
    {
      if (drbg != null)
      {
        drbg.setSeed(seed);
      }
    }
    
    public byte[] generateSeed(int numBytes)
    {
      byte[] data = new byte[numBytes];
      

      if (samples.getAndIncrement() > 20)
      {
        if (seedAvailable.getAndSet(false))
        {
          samples.set(0);
          drbg.reseed(null);
        }
      }
      
      drbg.nextBytes(data);
      
      return data;
    }
    
    private class SignallingEntropySource
      implements EntropySource
    {
      private final int byteLength;
      private final AtomicReference entropy = new AtomicReference();
      private final AtomicBoolean scheduled = new AtomicBoolean(false);
      
      SignallingEntropySource(int bitsRequired)
      {
        byteLength = ((bitsRequired + 7) / 8);
      }
      
      public boolean isPredictionResistant()
      {
        return true;
      }
      
      public byte[] getEntropy()
      {
        byte[] seed = (byte[])entropy.getAndSet(null);
        
        if ((seed == null) || (seed.length != byteLength))
        {
          seed = baseRandom.generateSeed(byteLength);
        }
        else
        {
          scheduled.set(false);
        }
        
        if (!scheduled.getAndSet(true))
        {
          new Thread(new EntropyGatherer(byteLength)).start();
        }
        
        return seed;
      }
      
      public int entropySize()
      {
        return byteLength * 8;
      }
      
      private class EntropyGatherer
        implements Runnable
      {
        private final int numBytes;
        
        EntropyGatherer(int numBytes)
        {
          this.numBytes = numBytes;
        }
        
        public void run()
        {
          entropy.set(baseRandom.generateSeed(numBytes));
          seedAvailable.set(true);
        }
      }
    }
  }
}

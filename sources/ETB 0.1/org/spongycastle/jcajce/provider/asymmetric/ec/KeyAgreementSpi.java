package org.spongycastle.jcajce.provider.asymmetric.ec;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import org.spongycastle.asn1.x9.X9IntegerConverter;
import org.spongycastle.crypto.BasicAgreement;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DerivationFunction;
import org.spongycastle.crypto.agreement.ECDHBasicAgreement;
import org.spongycastle.crypto.agreement.ECDHCBasicAgreement;
import org.spongycastle.crypto.agreement.ECMQVBasicAgreement;
import org.spongycastle.crypto.agreement.kdf.ConcatenationKDFGenerator;
import org.spongycastle.crypto.generators.KDF2BytesGenerator;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.crypto.params.ECPrivateKeyParameters;
import org.spongycastle.crypto.params.ECPublicKeyParameters;
import org.spongycastle.crypto.params.MQVPrivateParameters;
import org.spongycastle.crypto.params.MQVPublicParameters;
import org.spongycastle.crypto.util.DigestFactory;
import org.spongycastle.jcajce.provider.asymmetric.util.BaseAgreementSpi;
import org.spongycastle.jcajce.provider.asymmetric.util.ECUtil;
import org.spongycastle.jcajce.spec.MQVParameterSpec;
import org.spongycastle.jcajce.spec.UserKeyingMaterialSpec;
import org.spongycastle.jce.interfaces.ECPrivateKey;
import org.spongycastle.jce.interfaces.ECPublicKey;
import org.spongycastle.jce.interfaces.MQVPrivateKey;
import org.spongycastle.jce.interfaces.MQVPublicKey;








public class KeyAgreementSpi
  extends BaseAgreementSpi
{
  private static final X9IntegerConverter converter = new X9IntegerConverter();
  
  private String kaAlgorithm;
  
  private ECDomainParameters parameters;
  
  private BasicAgreement agreement;
  
  private MQVParameterSpec mqvParameters;
  
  private BigInteger result;
  

  protected KeyAgreementSpi(String kaAlgorithm, BasicAgreement agreement, DerivationFunction kdf)
  {
    super(kaAlgorithm, kdf);
    
    this.kaAlgorithm = kaAlgorithm;
    this.agreement = agreement;
  }
  

  protected byte[] bigIntToBytes(BigInteger r)
  {
    return converter.integerToBytes(r, converter.getByteLength(parameters.getCurve()));
  }
  


  protected Key engineDoPhase(Key key, boolean lastPhase)
    throws InvalidKeyException, IllegalStateException
  {
    if (parameters == null)
    {
      throw new IllegalStateException(kaAlgorithm + " not initialised.");
    }
    
    if (!lastPhase)
    {
      throw new IllegalStateException(kaAlgorithm + " can only be between two parties.");
    }
    CipherParameters pubKey;
    CipherParameters pubKey;
    if ((agreement instanceof ECMQVBasicAgreement)) {
      CipherParameters pubKey;
      if (!(key instanceof MQVPublicKey))
      {

        ECPublicKeyParameters staticKey = (ECPublicKeyParameters)ECUtils.generatePublicKeyParameter((PublicKey)key);
        
        ECPublicKeyParameters ephemKey = (ECPublicKeyParameters)ECUtils.generatePublicKeyParameter(mqvParameters.getOtherPartyEphemeralKey());
        
        pubKey = new MQVPublicParameters(staticKey, ephemKey);
      }
      else
      {
        MQVPublicKey mqvPubKey = (MQVPublicKey)key;
        
        ECPublicKeyParameters staticKey = (ECPublicKeyParameters)ECUtils.generatePublicKeyParameter(mqvPubKey.getStaticKey());
        
        ECPublicKeyParameters ephemKey = (ECPublicKeyParameters)ECUtils.generatePublicKeyParameter(mqvPubKey.getEphemeralKey());
        
        pubKey = new MQVPublicParameters(staticKey, ephemKey);
      }
    }
    else
    {
      if (!(key instanceof PublicKey))
      {

        throw new InvalidKeyException(kaAlgorithm + " key agreement requires " + getSimpleName(ECPublicKey.class) + " for doPhase");
      }
      
      pubKey = ECUtils.generatePublicKeyParameter((PublicKey)key);
    }
    
    try
    {
      result = agreement.calculateAgreement(pubKey);
    }
    catch (Exception e)
    {
      throw new InvalidKeyException("calculation failed: " + e.getMessage())
      {
        public Throwable getCause()
        {
          return e;
        }
      };
    }
    
    return null;
  }
  



  protected void engineInit(Key key, AlgorithmParameterSpec params, SecureRandom random)
    throws InvalidKeyException, InvalidAlgorithmParameterException
  {
    if ((params != null) && (!(params instanceof MQVParameterSpec)) && (!(params instanceof UserKeyingMaterialSpec)))
    {
      throw new InvalidAlgorithmParameterException("No algorithm parameters supported");
    }
    
    initFromKey(key, params);
  }
  


  protected void engineInit(Key key, SecureRandom random)
    throws InvalidKeyException
  {
    initFromKey(key, null);
  }
  
  private void initFromKey(Key key, AlgorithmParameterSpec parameterSpec)
    throws InvalidKeyException
  {
    if ((agreement instanceof ECMQVBasicAgreement))
    {
      mqvParameters = null;
      if ((!(key instanceof MQVPrivateKey)) && (!(parameterSpec instanceof MQVParameterSpec)))
      {

        throw new InvalidKeyException(kaAlgorithm + " key agreement requires " + getSimpleName(MQVParameterSpec.class) + " for initialisation");
      }
      
      ECPrivateKeyParameters staticPrivKey;
      ECPrivateKeyParameters ephemPrivKey;
      ECPublicKeyParameters ephemPubKey;
      if ((key instanceof MQVPrivateKey))
      {
        MQVPrivateKey mqvPrivKey = (MQVPrivateKey)key;
        
        ECPrivateKeyParameters staticPrivKey = (ECPrivateKeyParameters)ECUtil.generatePrivateKeyParameter(mqvPrivKey.getStaticPrivateKey());
        
        ECPrivateKeyParameters ephemPrivKey = (ECPrivateKeyParameters)ECUtil.generatePrivateKeyParameter(mqvPrivKey.getEphemeralPrivateKey());
        
        ECPublicKeyParameters ephemPubKey = null;
        if (mqvPrivKey.getEphemeralPublicKey() != null)
        {

          ephemPubKey = (ECPublicKeyParameters)ECUtils.generatePublicKeyParameter(mqvPrivKey.getEphemeralPublicKey());
        }
      }
      else
      {
        MQVParameterSpec mqvParameterSpec = (MQVParameterSpec)parameterSpec;
        

        staticPrivKey = (ECPrivateKeyParameters)ECUtil.generatePrivateKeyParameter((PrivateKey)key);
        
        ephemPrivKey = (ECPrivateKeyParameters)ECUtil.generatePrivateKeyParameter(mqvParameterSpec.getEphemeralPrivateKey());
        
        ephemPubKey = null;
        if (mqvParameterSpec.getEphemeralPublicKey() != null)
        {

          ephemPubKey = (ECPublicKeyParameters)ECUtils.generatePublicKeyParameter(mqvParameterSpec.getEphemeralPublicKey());
        }
        mqvParameters = mqvParameterSpec;
        ukmParameters = mqvParameterSpec.getUserKeyingMaterial();
      }
      
      MQVPrivateParameters localParams = new MQVPrivateParameters(staticPrivKey, ephemPrivKey, ephemPubKey);
      parameters = staticPrivKey.getParameters();
      


      agreement.init(localParams);
    }
    else
    {
      if (!(key instanceof PrivateKey))
      {

        throw new InvalidKeyException(kaAlgorithm + " key agreement requires " + getSimpleName(ECPrivateKey.class) + " for initialisation");
      }
      
      ECPrivateKeyParameters privKey = (ECPrivateKeyParameters)ECUtil.generatePrivateKeyParameter((PrivateKey)key);
      parameters = privKey.getParameters();
      ukmParameters = ((parameterSpec instanceof UserKeyingMaterialSpec) ? ((UserKeyingMaterialSpec)parameterSpec).getUserKeyingMaterial() : null);
      agreement.init(privKey);
    }
  }
  
  private static String getSimpleName(Class clazz)
  {
    String fullName = clazz.getName();
    
    return fullName.substring(fullName.lastIndexOf('.') + 1);
  }
  

  protected byte[] calcSecret()
  {
    return bigIntToBytes(result);
  }
  
  public static class DH
    extends KeyAgreementSpi
  {
    public DH()
    {
      super(new ECDHBasicAgreement(), null);
    }
  }
  
  public static class DHC
    extends KeyAgreementSpi
  {
    public DHC()
    {
      super(new ECDHCBasicAgreement(), null);
    }
  }
  
  public static class MQV
    extends KeyAgreementSpi
  {
    public MQV()
    {
      super(new ECMQVBasicAgreement(), null);
    }
  }
  
  public static class DHwithSHA1KDF
    extends KeyAgreementSpi
  {
    public DHwithSHA1KDF()
    {
      super(new ECDHBasicAgreement(), new KDF2BytesGenerator(DigestFactory.createSHA1()));
    }
  }
  
  public static class DHwithSHA1KDFAndSharedInfo
    extends KeyAgreementSpi
  {
    public DHwithSHA1KDFAndSharedInfo()
    {
      super(new ECDHBasicAgreement(), new KDF2BytesGenerator(DigestFactory.createSHA1()));
    }
  }
  
  public static class CDHwithSHA1KDFAndSharedInfo
    extends KeyAgreementSpi
  {
    public CDHwithSHA1KDFAndSharedInfo()
    {
      super(new ECDHCBasicAgreement(), new KDF2BytesGenerator(DigestFactory.createSHA1()));
    }
  }
  
  public static class DHwithSHA224KDFAndSharedInfo
    extends KeyAgreementSpi
  {
    public DHwithSHA224KDFAndSharedInfo()
    {
      super(new ECDHBasicAgreement(), new KDF2BytesGenerator(DigestFactory.createSHA224()));
    }
  }
  
  public static class CDHwithSHA224KDFAndSharedInfo
    extends KeyAgreementSpi
  {
    public CDHwithSHA224KDFAndSharedInfo()
    {
      super(new ECDHCBasicAgreement(), new KDF2BytesGenerator(DigestFactory.createSHA224()));
    }
  }
  
  public static class DHwithSHA256KDFAndSharedInfo
    extends KeyAgreementSpi
  {
    public DHwithSHA256KDFAndSharedInfo()
    {
      super(new ECDHBasicAgreement(), new KDF2BytesGenerator(DigestFactory.createSHA256()));
    }
  }
  
  public static class CDHwithSHA256KDFAndSharedInfo
    extends KeyAgreementSpi
  {
    public CDHwithSHA256KDFAndSharedInfo()
    {
      super(new ECDHCBasicAgreement(), new KDF2BytesGenerator(DigestFactory.createSHA256()));
    }
  }
  
  public static class DHwithSHA384KDFAndSharedInfo
    extends KeyAgreementSpi
  {
    public DHwithSHA384KDFAndSharedInfo()
    {
      super(new ECDHBasicAgreement(), new KDF2BytesGenerator(DigestFactory.createSHA384()));
    }
  }
  
  public static class CDHwithSHA384KDFAndSharedInfo
    extends KeyAgreementSpi
  {
    public CDHwithSHA384KDFAndSharedInfo()
    {
      super(new ECDHCBasicAgreement(), new KDF2BytesGenerator(DigestFactory.createSHA384()));
    }
  }
  
  public static class DHwithSHA512KDFAndSharedInfo
    extends KeyAgreementSpi
  {
    public DHwithSHA512KDFAndSharedInfo()
    {
      super(new ECDHBasicAgreement(), new KDF2BytesGenerator(DigestFactory.createSHA512()));
    }
  }
  
  public static class CDHwithSHA512KDFAndSharedInfo
    extends KeyAgreementSpi
  {
    public CDHwithSHA512KDFAndSharedInfo()
    {
      super(new ECDHCBasicAgreement(), new KDF2BytesGenerator(DigestFactory.createSHA512()));
    }
  }
  
  public static class MQVwithSHA1KDFAndSharedInfo
    extends KeyAgreementSpi
  {
    public MQVwithSHA1KDFAndSharedInfo()
    {
      super(new ECMQVBasicAgreement(), new KDF2BytesGenerator(DigestFactory.createSHA1()));
    }
  }
  
  public static class MQVwithSHA224KDFAndSharedInfo
    extends KeyAgreementSpi
  {
    public MQVwithSHA224KDFAndSharedInfo()
    {
      super(new ECMQVBasicAgreement(), new KDF2BytesGenerator(DigestFactory.createSHA224()));
    }
  }
  
  public static class MQVwithSHA256KDFAndSharedInfo
    extends KeyAgreementSpi
  {
    public MQVwithSHA256KDFAndSharedInfo()
    {
      super(new ECMQVBasicAgreement(), new KDF2BytesGenerator(DigestFactory.createSHA256()));
    }
  }
  
  public static class MQVwithSHA384KDFAndSharedInfo
    extends KeyAgreementSpi
  {
    public MQVwithSHA384KDFAndSharedInfo()
    {
      super(new ECMQVBasicAgreement(), new KDF2BytesGenerator(DigestFactory.createSHA384()));
    }
  }
  
  public static class MQVwithSHA512KDFAndSharedInfo
    extends KeyAgreementSpi
  {
    public MQVwithSHA512KDFAndSharedInfo()
    {
      super(new ECMQVBasicAgreement(), new KDF2BytesGenerator(DigestFactory.createSHA512()));
    }
  }
  
  public static class DHwithSHA1CKDF
    extends KeyAgreementSpi
  {
    public DHwithSHA1CKDF()
    {
      super(new ECDHCBasicAgreement(), new ConcatenationKDFGenerator(DigestFactory.createSHA1()));
    }
  }
  
  public static class DHwithSHA256CKDF
    extends KeyAgreementSpi
  {
    public DHwithSHA256CKDF()
    {
      super(new ECDHCBasicAgreement(), new ConcatenationKDFGenerator(DigestFactory.createSHA256()));
    }
  }
  
  public static class DHwithSHA384CKDF
    extends KeyAgreementSpi
  {
    public DHwithSHA384CKDF()
    {
      super(new ECDHCBasicAgreement(), new ConcatenationKDFGenerator(DigestFactory.createSHA384()));
    }
  }
  
  public static class DHwithSHA512CKDF
    extends KeyAgreementSpi
  {
    public DHwithSHA512CKDF()
    {
      super(new ECDHCBasicAgreement(), new ConcatenationKDFGenerator(DigestFactory.createSHA512()));
    }
  }
  
  public static class MQVwithSHA1CKDF
    extends KeyAgreementSpi
  {
    public MQVwithSHA1CKDF()
    {
      super(new ECMQVBasicAgreement(), new ConcatenationKDFGenerator(DigestFactory.createSHA1()));
    }
  }
  
  public static class MQVwithSHA224CKDF
    extends KeyAgreementSpi
  {
    public MQVwithSHA224CKDF()
    {
      super(new ECMQVBasicAgreement(), new ConcatenationKDFGenerator(DigestFactory.createSHA224()));
    }
  }
  
  public static class MQVwithSHA256CKDF
    extends KeyAgreementSpi
  {
    public MQVwithSHA256CKDF()
    {
      super(new ECMQVBasicAgreement(), new ConcatenationKDFGenerator(DigestFactory.createSHA256()));
    }
  }
  
  public static class MQVwithSHA384CKDF
    extends KeyAgreementSpi
  {
    public MQVwithSHA384CKDF()
    {
      super(new ECMQVBasicAgreement(), new ConcatenationKDFGenerator(DigestFactory.createSHA384()));
    }
  }
  
  public static class MQVwithSHA512CKDF
    extends KeyAgreementSpi
  {
    public MQVwithSHA512CKDF()
    {
      super(new ECMQVBasicAgreement(), new ConcatenationKDFGenerator(DigestFactory.createSHA512()));
    }
  }
}

package org.spongycastle.jcajce.provider.asymmetric.ecgost12;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import org.spongycastle.asn1.x9.X9IntegerConverter;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DerivationFunction;
import org.spongycastle.crypto.agreement.ECVKOAgreement;
import org.spongycastle.crypto.digests.GOST3411_2012_256Digest;
import org.spongycastle.crypto.digests.GOST3411_2012_512Digest;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.crypto.params.ECPrivateKeyParameters;
import org.spongycastle.crypto.params.ParametersWithUKM;
import org.spongycastle.jcajce.provider.asymmetric.util.BaseAgreementSpi;
import org.spongycastle.jcajce.provider.asymmetric.util.ECUtil;
import org.spongycastle.jcajce.spec.UserKeyingMaterialSpec;
import org.spongycastle.jce.interfaces.ECPrivateKey;
import org.spongycastle.jce.interfaces.ECPublicKey;


public class KeyAgreementSpi
  extends BaseAgreementSpi
{
  private static final X9IntegerConverter converter = new X9IntegerConverter();
  

  private String kaAlgorithm;
  
  private ECDomainParameters parameters;
  
  private ECVKOAgreement agreement;
  
  private byte[] result;
  

  protected KeyAgreementSpi(String kaAlgorithm, ECVKOAgreement agreement, DerivationFunction kdf)
  {
    super(kaAlgorithm, kdf);
    
    this.kaAlgorithm = kaAlgorithm;
    this.agreement = agreement;
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
    


    if (!(key instanceof PublicKey))
    {

      throw new InvalidKeyException(kaAlgorithm + " key agreement requires " + getSimpleName(ECPublicKey.class) + " for doPhase");
    }
    
    CipherParameters pubKey = generatePublicKeyParameter((PublicKey)key);
    

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
    if ((params != null) && (!(params instanceof UserKeyingMaterialSpec)))
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
    if (!(key instanceof PrivateKey))
    {

      throw new InvalidKeyException(kaAlgorithm + " key agreement requires " + getSimpleName(ECPrivateKey.class) + " for initialisation");
    }
    
    ECPrivateKeyParameters privKey = (ECPrivateKeyParameters)ECUtil.generatePrivateKeyParameter((PrivateKey)key);
    parameters = privKey.getParameters();
    ukmParameters = ((parameterSpec instanceof UserKeyingMaterialSpec) ? ((UserKeyingMaterialSpec)parameterSpec).getUserKeyingMaterial() : null);
    agreement.init(new ParametersWithUKM(privKey, ukmParameters));
  }
  

  private static String getSimpleName(Class clazz)
  {
    String fullName = clazz.getName();
    
    return fullName.substring(fullName.lastIndexOf('.') + 1);
  }
  

  static AsymmetricKeyParameter generatePublicKeyParameter(PublicKey key)
    throws InvalidKeyException
  {
    return (key instanceof BCECGOST3410_2012PublicKey) ? ((BCECGOST3410_2012PublicKey)key).engineGetKeyParameters() : ECUtil.generatePublicKeyParameter(key);
  }
  
  protected byte[] calcSecret()
  {
    return result;
  }
  
  public static class ECVKO256
    extends KeyAgreementSpi
  {
    public ECVKO256()
    {
      super(new ECVKOAgreement(new GOST3411_2012_256Digest()), null);
    }
  }
  
  public static class ECVKO512
    extends KeyAgreementSpi
  {
    public ECVKO512()
    {
      super(new ECVKOAgreement(new GOST3411_2012_512Digest()), null);
    }
  }
}

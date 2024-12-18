package org.spongycastle.jcajce.provider.asymmetric.dsa;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.DSAParameterSpec;
import org.spongycastle.crypto.digests.SHA256Digest;
import org.spongycastle.crypto.generators.DSAParametersGenerator;
import org.spongycastle.crypto.params.DSAParameterGenerationParameters;
import org.spongycastle.crypto.params.DSAParameters;
import org.spongycastle.jcajce.provider.asymmetric.util.BaseAlgorithmParameterGeneratorSpi;
import org.spongycastle.jcajce.provider.asymmetric.util.PrimeCertaintyCalculator;


public class AlgorithmParameterGeneratorSpi
  extends BaseAlgorithmParameterGeneratorSpi
{
  protected SecureRandom random;
  protected int strength = 2048;
  protected DSAParameterGenerationParameters params;
  
  public AlgorithmParameterGeneratorSpi() {}
  
  protected void engineInit(int strength, SecureRandom random)
  {
    if ((strength < 512) || (strength > 3072))
    {
      throw new InvalidParameterException("strength must be from 512 - 3072");
    }
    
    if ((strength <= 1024) && (strength % 64 != 0))
    {
      throw new InvalidParameterException("strength must be a multiple of 64 below 1024 bits.");
    }
    
    if ((strength > 1024) && (strength % 1024 != 0))
    {
      throw new InvalidParameterException("strength must be a multiple of 1024 above 1024 bits.");
    }
    
    this.strength = strength;
    this.random = random;
  }
  


  protected void engineInit(AlgorithmParameterSpec genParamSpec, SecureRandom random)
    throws InvalidAlgorithmParameterException
  {
    throw new InvalidAlgorithmParameterException("No supported AlgorithmParameterSpec for DSA parameter generation.");
  }
  
  protected AlgorithmParameters engineGenerateParameters()
  {
    DSAParametersGenerator pGen;
    DSAParametersGenerator pGen;
    if (strength <= 1024)
    {
      pGen = new DSAParametersGenerator();
    }
    else
    {
      pGen = new DSAParametersGenerator(new SHA256Digest());
    }
    
    if (random == null)
    {
      random = new SecureRandom();
    }
    
    int certainty = PrimeCertaintyCalculator.getDefaultCertainty(strength);
    
    if (strength == 1024)
    {
      this.params = new DSAParameterGenerationParameters(1024, 160, certainty, random);
      pGen.init(this.params);
    }
    else if (strength > 1024)
    {
      this.params = new DSAParameterGenerationParameters(strength, 256, certainty, random);
      pGen.init(this.params);
    }
    else
    {
      pGen.init(strength, certainty, random);
    }
    
    DSAParameters p = pGen.generateParameters();
    


    try
    {
      AlgorithmParameters params = createParametersInstance("DSA");
      params.init(new DSAParameterSpec(p.getP(), p.getQ(), p.getG()));
    }
    catch (Exception e)
    {
      throw new RuntimeException(e.getMessage());
    }
    AlgorithmParameters params;
    return params;
  }
}

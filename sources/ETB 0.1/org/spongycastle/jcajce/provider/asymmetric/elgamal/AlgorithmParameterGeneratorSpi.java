package org.spongycastle.jcajce.provider.asymmetric.elgamal;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.spec.DHGenParameterSpec;
import javax.crypto.spec.DHParameterSpec;
import org.spongycastle.crypto.generators.ElGamalParametersGenerator;
import org.spongycastle.crypto.params.ElGamalParameters;
import org.spongycastle.jcajce.provider.asymmetric.util.BaseAlgorithmParameterGeneratorSpi;



public class AlgorithmParameterGeneratorSpi
  extends BaseAlgorithmParameterGeneratorSpi
{
  protected SecureRandom random;
  protected int strength = 1024;
  
  private int l = 0;
  
  public AlgorithmParameterGeneratorSpi() {}
  
  protected void engineInit(int strength, SecureRandom random)
  {
    this.strength = strength;
    this.random = random;
  }
  


  protected void engineInit(AlgorithmParameterSpec genParamSpec, SecureRandom random)
    throws InvalidAlgorithmParameterException
  {
    if (!(genParamSpec instanceof DHGenParameterSpec))
    {
      throw new InvalidAlgorithmParameterException("DH parameter generator requires a DHGenParameterSpec for initialisation");
    }
    DHGenParameterSpec spec = (DHGenParameterSpec)genParamSpec;
    
    strength = spec.getPrimeSize();
    l = spec.getExponentSize();
    this.random = random;
  }
  
  protected AlgorithmParameters engineGenerateParameters()
  {
    ElGamalParametersGenerator pGen = new ElGamalParametersGenerator();
    
    if (random != null)
    {
      pGen.init(strength, 20, random);
    }
    else
    {
      pGen.init(strength, 20, new SecureRandom());
    }
    
    ElGamalParameters p = pGen.generateParameters();
    


    try
    {
      AlgorithmParameters params = createParametersInstance("ElGamal");
      params.init(new DHParameterSpec(p.getP(), p.getG(), l));
    }
    catch (Exception e)
    {
      throw new RuntimeException(e.getMessage());
    }
    AlgorithmParameters params;
    return params;
  }
}

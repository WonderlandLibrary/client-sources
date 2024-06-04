package org.spongycastle.jcajce.provider.symmetric;

import java.io.IOException;
import java.math.BigInteger;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import javax.crypto.spec.PBEParameterSpec;
import org.spongycastle.asn1.pkcs.PBEParameter;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.symmetric.util.BaseAlgorithmParameters;
import org.spongycastle.jcajce.provider.util.AlgorithmProvider;




public class PBEPBKDF1
{
  private PBEPBKDF1() {}
  
  public static class AlgParams
    extends BaseAlgorithmParameters
  {
    PBEParameter params;
    
    public AlgParams() {}
    
    protected byte[] engineGetEncoded()
    {
      try
      {
        return params.getEncoded("DER");
      }
      catch (IOException e)
      {
        throw new RuntimeException("Oooops! " + e.toString());
      }
    }
    

    protected byte[] engineGetEncoded(String format)
    {
      if (isASN1FormatString(format))
      {
        return engineGetEncoded();
      }
      
      return null;
    }
    

    protected AlgorithmParameterSpec localEngineGetParameterSpec(Class paramSpec)
      throws InvalidParameterSpecException
    {
      if (paramSpec == PBEParameterSpec.class)
      {
        return new PBEParameterSpec(params.getSalt(), params
          .getIterationCount().intValue());
      }
      
      throw new InvalidParameterSpecException("unknown parameter spec passed to PBKDF1 PBE parameters object.");
    }
    

    protected void engineInit(AlgorithmParameterSpec paramSpec)
      throws InvalidParameterSpecException
    {
      if (!(paramSpec instanceof PBEParameterSpec))
      {
        throw new InvalidParameterSpecException("PBEParameterSpec required to initialise a PBKDF1 PBE parameters algorithm parameters object");
      }
      
      PBEParameterSpec pbeSpec = (PBEParameterSpec)paramSpec;
      

      params = new PBEParameter(pbeSpec.getSalt(), pbeSpec.getIterationCount());
    }
    

    protected void engineInit(byte[] params)
      throws IOException
    {
      this.params = PBEParameter.getInstance(params);
    }
    


    protected void engineInit(byte[] params, String format)
      throws IOException
    {
      if (isASN1FormatString(format))
      {
        engineInit(params);
        return;
      }
      
      throw new IOException("Unknown parameters format in PBKDF2 parameters object");
    }
    
    protected String engineToString()
    {
      return "PBKDF1 Parameters";
    }
  }
  
  public static class Mappings
    extends AlgorithmProvider
  {
    private static final String PREFIX = PBEPBKDF1.class.getName();
    

    public Mappings() {}
    

    public void configure(ConfigurableProvider provider)
    {
      provider.addAlgorithm("AlgorithmParameters.PBKDF1", PREFIX + "$AlgParams");
      provider.addAlgorithm("Alg.Alias.AlgorithmParameters." + PKCSObjectIdentifiers.pbeWithMD2AndDES_CBC, "PBKDF1");
      provider.addAlgorithm("Alg.Alias.AlgorithmParameters." + PKCSObjectIdentifiers.pbeWithMD5AndDES_CBC, "PBKDF1");
      provider.addAlgorithm("Alg.Alias.AlgorithmParameters." + PKCSObjectIdentifiers.pbeWithMD5AndRC2_CBC, "PBKDF1");
      provider.addAlgorithm("Alg.Alias.AlgorithmParameters." + PKCSObjectIdentifiers.pbeWithSHA1AndDES_CBC, "PBKDF1");
      provider.addAlgorithm("Alg.Alias.AlgorithmParameters." + PKCSObjectIdentifiers.pbeWithSHA1AndRC2_CBC, "PBKDF1");
    }
  }
}

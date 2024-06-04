package org.spongycastle.jcajce.spec;

import java.security.spec.AlgorithmParameterSpec;
import org.spongycastle.asn1.nist.NISTObjectIdentifiers;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x9.X9ObjectIdentifiers;
import org.spongycastle.util.Arrays;















public class KTSParameterSpec
  implements AlgorithmParameterSpec
{
  private final String wrappingKeyAlgorithm;
  private final int keySizeInBits;
  private final AlgorithmParameterSpec parameterSpec;
  private final AlgorithmIdentifier kdfAlgorithm;
  private byte[] otherInfo;
  
  public static final class Builder
  {
    private final String algorithmName;
    private final int keySizeInBits;
    private AlgorithmParameterSpec parameterSpec;
    private AlgorithmIdentifier kdfAlgorithm;
    private byte[] otherInfo;
    
    public Builder(String algorithmName, int keySizeInBits)
    {
      this(algorithmName, keySizeInBits, null);
    }
    







    public Builder(String algorithmName, int keySizeInBits, byte[] otherInfo)
    {
      this.algorithmName = algorithmName;
      this.keySizeInBits = keySizeInBits;
      kdfAlgorithm = new AlgorithmIdentifier(X9ObjectIdentifiers.id_kdf_kdf3, new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha256));
      this.otherInfo = (otherInfo == null ? new byte[0] : Arrays.clone(otherInfo));
    }
    






    public Builder withParameterSpec(AlgorithmParameterSpec parameterSpec)
    {
      this.parameterSpec = parameterSpec;
      
      return this;
    }
    






    public Builder withKdfAlgorithm(AlgorithmIdentifier kdfAlgorithm)
    {
      this.kdfAlgorithm = kdfAlgorithm;
      
      return this;
    }
    





    public KTSParameterSpec build()
    {
      return new KTSParameterSpec(algorithmName, keySizeInBits, parameterSpec, kdfAlgorithm, otherInfo, null);
    }
  }
  


  private KTSParameterSpec(String wrappingKeyAlgorithm, int keySizeInBits, AlgorithmParameterSpec parameterSpec, AlgorithmIdentifier kdfAlgorithm, byte[] otherInfo)
  {
    this.wrappingKeyAlgorithm = wrappingKeyAlgorithm;
    this.keySizeInBits = keySizeInBits;
    this.parameterSpec = parameterSpec;
    this.kdfAlgorithm = kdfAlgorithm;
    this.otherInfo = otherInfo;
  }
  





  public String getKeyAlgorithmName()
  {
    return wrappingKeyAlgorithm;
  }
  





  public int getKeySize()
  {
    return keySizeInBits;
  }
  





  public AlgorithmParameterSpec getParameterSpec()
  {
    return parameterSpec;
  }
  





  public AlgorithmIdentifier getKdfAlgorithm()
  {
    return kdfAlgorithm;
  }
  





  public byte[] getOtherInfo()
  {
    return Arrays.clone(otherInfo);
  }
}

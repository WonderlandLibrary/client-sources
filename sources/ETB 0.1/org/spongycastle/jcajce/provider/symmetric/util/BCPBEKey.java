package org.spongycastle.jcajce.provider.symmetric.util;

import javax.crypto.interfaces.PBEKey;
import javax.crypto.spec.PBEKeySpec;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.PBEParametersGenerator;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;


public class BCPBEKey
  implements PBEKey
{
  String algorithm;
  ASN1ObjectIdentifier oid;
  int type;
  int digest;
  int keySize;
  int ivSize;
  CipherParameters param;
  PBEKeySpec pbeKeySpec;
  boolean tryWrong = false;
  











  public BCPBEKey(String algorithm, ASN1ObjectIdentifier oid, int type, int digest, int keySize, int ivSize, PBEKeySpec pbeKeySpec, CipherParameters param)
  {
    this.algorithm = algorithm;
    this.oid = oid;
    this.type = type;
    this.digest = digest;
    this.keySize = keySize;
    this.ivSize = ivSize;
    this.pbeKeySpec = pbeKeySpec;
    this.param = param;
  }
  
  public String getAlgorithm()
  {
    return algorithm;
  }
  
  public String getFormat()
  {
    return "RAW";
  }
  
  public byte[] getEncoded()
  {
    if (param != null)
    {
      KeyParameter kParam;
      KeyParameter kParam;
      if ((param instanceof ParametersWithIV))
      {
        kParam = (KeyParameter)((ParametersWithIV)param).getParameters();
      }
      else
      {
        kParam = (KeyParameter)param;
      }
      
      return kParam.getKey();
    }
    

    if (type == 2)
    {
      return PBEParametersGenerator.PKCS12PasswordToBytes(pbeKeySpec.getPassword());
    }
    if (type == 5)
    {
      return PBEParametersGenerator.PKCS5PasswordToUTF8Bytes(pbeKeySpec.getPassword());
    }
    

    return PBEParametersGenerator.PKCS5PasswordToBytes(pbeKeySpec.getPassword());
  }
  


  int getType()
  {
    return type;
  }
  
  int getDigest()
  {
    return digest;
  }
  
  int getKeySize()
  {
    return keySize;
  }
  
  public int getIvSize()
  {
    return ivSize;
  }
  
  public CipherParameters getParam()
  {
    return param;
  }
  



  public char[] getPassword()
  {
    return pbeKeySpec.getPassword();
  }
  



  public byte[] getSalt()
  {
    return pbeKeySpec.getSalt();
  }
  



  public int getIterationCount()
  {
    return pbeKeySpec.getIterationCount();
  }
  
  public ASN1ObjectIdentifier getOID()
  {
    return oid;
  }
  
  public void setTryWrongPKCS12Zero(boolean tryWrong)
  {
    this.tryWrong = tryWrong;
  }
  
  boolean shouldTryWrongPKCS12()
  {
    return tryWrong;
  }
}

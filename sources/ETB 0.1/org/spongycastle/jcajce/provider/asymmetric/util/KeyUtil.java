package org.spongycastle.jcajce.provider.asymmetric.util;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;

public class KeyUtil
{
  public KeyUtil() {}
  
  public static byte[] getEncodedSubjectPublicKeyInfo(AlgorithmIdentifier algId, ASN1Encodable keyData)
  {
    try
    {
      return getEncodedSubjectPublicKeyInfo(new org.spongycastle.asn1.x509.SubjectPublicKeyInfo(algId, keyData));
    }
    catch (Exception e) {}
    
    return null;
  }
  

  public static byte[] getEncodedSubjectPublicKeyInfo(AlgorithmIdentifier algId, byte[] keyData)
  {
    try
    {
      return getEncodedSubjectPublicKeyInfo(new org.spongycastle.asn1.x509.SubjectPublicKeyInfo(algId, keyData));
    }
    catch (Exception e) {}
    
    return null;
  }
  

  public static byte[] getEncodedSubjectPublicKeyInfo(org.spongycastle.asn1.x509.SubjectPublicKeyInfo info)
  {
    try
    {
      return info.getEncoded("DER");
    }
    catch (Exception e) {}
    
    return null;
  }
  

  public static byte[] getEncodedPrivateKeyInfo(AlgorithmIdentifier algId, ASN1Encodable privKey)
  {
    try
    {
      PrivateKeyInfo info = new PrivateKeyInfo(algId, privKey.toASN1Primitive());
      
      return getEncodedPrivateKeyInfo(info);
    }
    catch (Exception e) {}
    
    return null;
  }
  

  public static byte[] getEncodedPrivateKeyInfo(PrivateKeyInfo info)
  {
    try
    {
      return info.getEncoded("DER");
    }
    catch (Exception e) {}
    
    return null;
  }
}

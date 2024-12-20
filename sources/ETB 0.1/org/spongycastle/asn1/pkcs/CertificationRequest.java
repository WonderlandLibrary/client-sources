package org.spongycastle.asn1.pkcs;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;











public class CertificationRequest
  extends ASN1Object
{
  protected CertificationRequestInfo reqInfo = null;
  protected AlgorithmIdentifier sigAlgId = null;
  protected DERBitString sigBits = null;
  
  public static CertificationRequest getInstance(Object o)
  {
    if ((o instanceof CertificationRequest))
    {
      return (CertificationRequest)o;
    }
    
    if (o != null)
    {
      return new CertificationRequest(ASN1Sequence.getInstance(o));
    }
    
    return null;
  }
  



  protected CertificationRequest() {}
  


  public CertificationRequest(CertificationRequestInfo requestInfo, AlgorithmIdentifier algorithm, DERBitString signature)
  {
    reqInfo = requestInfo;
    sigAlgId = algorithm;
    sigBits = signature;
  }
  

  /**
   * @deprecated
   */
  public CertificationRequest(ASN1Sequence seq)
  {
    reqInfo = CertificationRequestInfo.getInstance(seq.getObjectAt(0));
    sigAlgId = AlgorithmIdentifier.getInstance(seq.getObjectAt(1));
    sigBits = ((DERBitString)seq.getObjectAt(2));
  }
  
  public CertificationRequestInfo getCertificationRequestInfo()
  {
    return reqInfo;
  }
  
  public AlgorithmIdentifier getSignatureAlgorithm()
  {
    return sigAlgId;
  }
  
  public DERBitString getSignature()
  {
    return sigBits;
  }
  

  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    v.add(reqInfo);
    v.add(sigAlgId);
    v.add(sigBits);
    
    return new DERSequence(v);
  }
}

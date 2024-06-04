package org.spongycastle.asn1.x509;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERIA5String;
import org.spongycastle.asn1.DERSequence;





















public class PolicyQualifierInfo
  extends ASN1Object
{
  private ASN1ObjectIdentifier policyQualifierId;
  private ASN1Encodable qualifier;
  
  public PolicyQualifierInfo(ASN1ObjectIdentifier policyQualifierId, ASN1Encodable qualifier)
  {
    this.policyQualifierId = policyQualifierId;
    this.qualifier = qualifier;
  }
  








  public PolicyQualifierInfo(String cps)
  {
    policyQualifierId = PolicyQualifierId.id_qt_cps;
    qualifier = new DERIA5String(cps);
  }
  





  /**
   * @deprecated
   */
  public PolicyQualifierInfo(ASN1Sequence as)
  {
    if (as.size() != 2)
    {

      throw new IllegalArgumentException("Bad sequence size: " + as.size());
    }
    
    policyQualifierId = ASN1ObjectIdentifier.getInstance(as.getObjectAt(0));
    qualifier = as.getObjectAt(1);
  }
  

  public static PolicyQualifierInfo getInstance(Object obj)
  {
    if ((obj instanceof PolicyQualifierInfo))
    {
      return (PolicyQualifierInfo)obj;
    }
    if (obj != null)
    {
      return new PolicyQualifierInfo(ASN1Sequence.getInstance(obj));
    }
    
    return null;
  }
  

  public ASN1ObjectIdentifier getPolicyQualifierId()
  {
    return policyQualifierId;
  }
  
  public ASN1Encodable getQualifier()
  {
    return qualifier;
  }
  





  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector dev = new ASN1EncodableVector();
    dev.add(policyQualifierId);
    dev.add(qualifier);
    
    return new DERSequence(dev);
  }
}

package org.spongycastle.asn1.cms;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.CertificateList;











public class TimeStampAndCRL
  extends ASN1Object
{
  private ContentInfo timeStamp;
  private CertificateList crl;
  
  public TimeStampAndCRL(ContentInfo timeStamp)
  {
    this.timeStamp = timeStamp;
  }
  
  private TimeStampAndCRL(ASN1Sequence seq)
  {
    timeStamp = ContentInfo.getInstance(seq.getObjectAt(0));
    if (seq.size() == 2)
    {
      crl = CertificateList.getInstance(seq.getObjectAt(1));
    }
  }
  













  public static TimeStampAndCRL getInstance(Object obj)
  {
    if ((obj instanceof TimeStampAndCRL))
    {
      return (TimeStampAndCRL)obj;
    }
    if (obj != null)
    {
      return new TimeStampAndCRL(ASN1Sequence.getInstance(obj));
    }
    
    return null;
  }
  
  public ContentInfo getTimeStampToken()
  {
    return timeStamp;
  }
  
  /**
   * @deprecated
   */
  public CertificateList getCertificateList() { return crl; }
  

  public CertificateList getCRL()
  {
    return crl;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    v.add(timeStamp);
    
    if (crl != null)
    {
      v.add(crl);
    }
    
    return new DERSequence(v);
  }
}

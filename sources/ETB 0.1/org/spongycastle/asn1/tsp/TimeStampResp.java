package org.spongycastle.asn1.tsp;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.cmp.PKIStatusInfo;
import org.spongycastle.asn1.cms.ContentInfo;




public class TimeStampResp
  extends ASN1Object
{
  PKIStatusInfo pkiStatusInfo;
  ContentInfo timeStampToken;
  
  public static TimeStampResp getInstance(Object o)
  {
    if ((o instanceof TimeStampResp))
    {
      return (TimeStampResp)o;
    }
    if (o != null)
    {
      return new TimeStampResp(ASN1Sequence.getInstance(o));
    }
    
    return null;
  }
  

  private TimeStampResp(ASN1Sequence seq)
  {
    Enumeration e = seq.getObjects();
    

    pkiStatusInfo = PKIStatusInfo.getInstance(e.nextElement());
    
    if (e.hasMoreElements())
    {
      timeStampToken = ContentInfo.getInstance(e.nextElement());
    }
  }
  
  public TimeStampResp(PKIStatusInfo pkiStatusInfo, ContentInfo timeStampToken)
  {
    this.pkiStatusInfo = pkiStatusInfo;
    this.timeStampToken = timeStampToken;
  }
  
  public PKIStatusInfo getStatus()
  {
    return pkiStatusInfo;
  }
  
  public ContentInfo getTimeStampToken()
  {
    return timeStampToken;
  }
  







  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    v.add(pkiStatusInfo);
    if (timeStampToken != null)
    {
      v.add(timeStampToken);
    }
    
    return new DERSequence(v);
  }
}

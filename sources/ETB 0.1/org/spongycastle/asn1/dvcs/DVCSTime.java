package org.spongycastle.asn1.dvcs;

import java.util.Date;
import org.spongycastle.asn1.ASN1Choice;
import org.spongycastle.asn1.ASN1GeneralizedTime;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.cms.ContentInfo;












public class DVCSTime
  extends ASN1Object
  implements ASN1Choice
{
  private final ASN1GeneralizedTime genTime;
  private final ContentInfo timeStampToken;
  
  public DVCSTime(Date time)
  {
    this(new ASN1GeneralizedTime(time));
  }
  
  public DVCSTime(ASN1GeneralizedTime genTime)
  {
    this.genTime = genTime;
    timeStampToken = null;
  }
  
  public DVCSTime(ContentInfo timeStampToken)
  {
    genTime = null;
    this.timeStampToken = timeStampToken;
  }
  
  public static DVCSTime getInstance(Object obj)
  {
    if ((obj instanceof DVCSTime))
    {
      return (DVCSTime)obj;
    }
    if ((obj instanceof ASN1GeneralizedTime))
    {
      return new DVCSTime(ASN1GeneralizedTime.getInstance(obj));
    }
    if (obj != null)
    {
      return new DVCSTime(ContentInfo.getInstance(obj));
    }
    
    return null;
  }
  


  public static DVCSTime getInstance(ASN1TaggedObject obj, boolean explicit)
  {
    return getInstance(obj.getObject());
  }
  



  public ASN1GeneralizedTime getGenTime()
  {
    return genTime;
  }
  
  public ContentInfo getTimeStampToken()
  {
    return timeStampToken;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    if (genTime != null)
    {
      return genTime;
    }
    

    return timeStampToken.toASN1Primitive();
  }
  

  public String toString()
  {
    if (genTime != null)
    {
      return genTime.toString();
    }
    

    return timeStampToken.toString();
  }
}

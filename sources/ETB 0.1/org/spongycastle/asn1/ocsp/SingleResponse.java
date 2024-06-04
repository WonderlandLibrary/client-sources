package org.spongycastle.asn1.ocsp;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1GeneralizedTime;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.x509.Extensions;
import org.spongycastle.asn1.x509.X509Extensions;











public class SingleResponse
  extends ASN1Object
{
  private CertID certID;
  private CertStatus certStatus;
  private ASN1GeneralizedTime thisUpdate;
  private ASN1GeneralizedTime nextUpdate;
  private Extensions singleExtensions;
  
  /**
   * @deprecated
   */
  public SingleResponse(CertID certID, CertStatus certStatus, ASN1GeneralizedTime thisUpdate, ASN1GeneralizedTime nextUpdate, X509Extensions singleExtensions)
  {
    this(certID, certStatus, thisUpdate, nextUpdate, Extensions.getInstance(singleExtensions));
  }
  





  public SingleResponse(CertID certID, CertStatus certStatus, ASN1GeneralizedTime thisUpdate, ASN1GeneralizedTime nextUpdate, Extensions singleExtensions)
  {
    this.certID = certID;
    this.certStatus = certStatus;
    this.thisUpdate = thisUpdate;
    this.nextUpdate = nextUpdate;
    this.singleExtensions = singleExtensions;
  }
  

  private SingleResponse(ASN1Sequence seq)
  {
    certID = CertID.getInstance(seq.getObjectAt(0));
    certStatus = CertStatus.getInstance(seq.getObjectAt(1));
    thisUpdate = ASN1GeneralizedTime.getInstance(seq.getObjectAt(2));
    
    if (seq.size() > 4)
    {
      nextUpdate = ASN1GeneralizedTime.getInstance(
        (ASN1TaggedObject)seq.getObjectAt(3), true);
      singleExtensions = Extensions.getInstance(
        (ASN1TaggedObject)seq.getObjectAt(4), true);
    }
    else if (seq.size() > 3)
    {
      ASN1TaggedObject o = (ASN1TaggedObject)seq.getObjectAt(3);
      
      if (o.getTagNo() == 0)
      {
        nextUpdate = ASN1GeneralizedTime.getInstance(o, true);
      }
      else
      {
        singleExtensions = Extensions.getInstance(o, true);
      }
    }
  }
  


  public static SingleResponse getInstance(ASN1TaggedObject obj, boolean explicit)
  {
    return getInstance(ASN1Sequence.getInstance(obj, explicit));
  }
  

  public static SingleResponse getInstance(Object obj)
  {
    if ((obj instanceof SingleResponse))
    {
      return (SingleResponse)obj;
    }
    if (obj != null)
    {
      return new SingleResponse(ASN1Sequence.getInstance(obj));
    }
    
    return null;
  }
  
  public CertID getCertID()
  {
    return certID;
  }
  
  public CertStatus getCertStatus()
  {
    return certStatus;
  }
  
  public ASN1GeneralizedTime getThisUpdate()
  {
    return thisUpdate;
  }
  
  public ASN1GeneralizedTime getNextUpdate()
  {
    return nextUpdate;
  }
  
  public Extensions getSingleExtensions()
  {
    return singleExtensions;
  }
  











  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    v.add(certID);
    v.add(certStatus);
    v.add(thisUpdate);
    
    if (nextUpdate != null)
    {
      v.add(new DERTaggedObject(true, 0, nextUpdate));
    }
    
    if (singleExtensions != null)
    {
      v.add(new DERTaggedObject(true, 1, singleExtensions));
    }
    
    return new DERSequence(v);
  }
}

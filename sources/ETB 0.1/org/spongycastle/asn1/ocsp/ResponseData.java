package org.spongycastle.asn1.ocsp;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1GeneralizedTime;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.x509.Extensions;
import org.spongycastle.asn1.x509.X509Extensions;

public class ResponseData
  extends ASN1Object
{
  private static final ASN1Integer V1 = new ASN1Integer(0L);
  
  private boolean versionPresent;
  
  private ASN1Integer version;
  
  private ResponderID responderID;
  
  private ASN1GeneralizedTime producedAt;
  
  private ASN1Sequence responses;
  
  private Extensions responseExtensions;
  

  public ResponseData(ASN1Integer version, ResponderID responderID, ASN1GeneralizedTime producedAt, ASN1Sequence responses, Extensions responseExtensions)
  {
    this.version = version;
    this.responderID = responderID;
    this.producedAt = producedAt;
    this.responses = responses;
    this.responseExtensions = responseExtensions;
  }
  








  /**
   * @deprecated
   */
  public ResponseData(ResponderID responderID, ASN1GeneralizedTime producedAt, ASN1Sequence responses, X509Extensions responseExtensions)
  {
    this(V1, responderID, ASN1GeneralizedTime.getInstance(producedAt), responses, Extensions.getInstance(responseExtensions));
  }
  




  public ResponseData(ResponderID responderID, ASN1GeneralizedTime producedAt, ASN1Sequence responses, Extensions responseExtensions)
  {
    this(V1, responderID, producedAt, responses, responseExtensions);
  }
  

  private ResponseData(ASN1Sequence seq)
  {
    int index = 0;
    
    if ((seq.getObjectAt(0) instanceof ASN1TaggedObject))
    {
      ASN1TaggedObject o = (ASN1TaggedObject)seq.getObjectAt(0);
      
      if (o.getTagNo() == 0)
      {
        versionPresent = true;
        version = ASN1Integer.getInstance(
          (ASN1TaggedObject)seq.getObjectAt(0), true);
        index++;
      }
      else
      {
        version = V1;
      }
    }
    else
    {
      version = V1;
    }
    
    responderID = ResponderID.getInstance(seq.getObjectAt(index++));
    producedAt = ASN1GeneralizedTime.getInstance(seq.getObjectAt(index++));
    responses = ((ASN1Sequence)seq.getObjectAt(index++));
    
    if (seq.size() > index)
    {
      responseExtensions = Extensions.getInstance(
        (ASN1TaggedObject)seq.getObjectAt(index), true);
    }
  }
  


  public static ResponseData getInstance(ASN1TaggedObject obj, boolean explicit)
  {
    return getInstance(ASN1Sequence.getInstance(obj, explicit));
  }
  

  public static ResponseData getInstance(Object obj)
  {
    if ((obj instanceof ResponseData))
    {
      return (ResponseData)obj;
    }
    if (obj != null)
    {
      return new ResponseData(ASN1Sequence.getInstance(obj));
    }
    
    return null;
  }
  
  public ASN1Integer getVersion()
  {
    return version;
  }
  
  public ResponderID getResponderID()
  {
    return responderID;
  }
  
  public ASN1GeneralizedTime getProducedAt()
  {
    return producedAt;
  }
  
  public ASN1Sequence getResponses()
  {
    return responses;
  }
  
  public Extensions getResponseExtensions()
  {
    return responseExtensions;
  }
  











  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    if ((versionPresent) || (!version.equals(V1)))
    {
      v.add(new DERTaggedObject(true, 0, version));
    }
    
    v.add(responderID);
    v.add(producedAt);
    v.add(responses);
    if (responseExtensions != null)
    {
      v.add(new DERTaggedObject(true, 1, responseExtensions));
    }
    
    return new DERSequence(v);
  }
}

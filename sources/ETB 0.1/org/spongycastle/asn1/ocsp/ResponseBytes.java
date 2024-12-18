package org.spongycastle.asn1.ocsp;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;



public class ResponseBytes
  extends ASN1Object
{
  ASN1ObjectIdentifier responseType;
  ASN1OctetString response;
  
  public ResponseBytes(ASN1ObjectIdentifier responseType, ASN1OctetString response)
  {
    this.responseType = responseType;
    this.response = response;
  }
  

  /**
   * @deprecated
   */
  public ResponseBytes(ASN1Sequence seq)
  {
    responseType = ((ASN1ObjectIdentifier)seq.getObjectAt(0));
    response = ((ASN1OctetString)seq.getObjectAt(1));
  }
  


  public static ResponseBytes getInstance(ASN1TaggedObject obj, boolean explicit)
  {
    return getInstance(ASN1Sequence.getInstance(obj, explicit));
  }
  

  public static ResponseBytes getInstance(Object obj)
  {
    if ((obj instanceof ResponseBytes))
    {
      return (ResponseBytes)obj;
    }
    if (obj != null)
    {
      return new ResponseBytes(ASN1Sequence.getInstance(obj));
    }
    
    return null;
  }
  
  public ASN1ObjectIdentifier getResponseType()
  {
    return responseType;
  }
  
  public ASN1OctetString getResponse()
  {
    return response;
  }
  








  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    v.add(responseType);
    v.add(response);
    
    return new DERSequence(v);
  }
}

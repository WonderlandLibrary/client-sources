package org.spongycastle.asn1.cmc;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;









public class OtherMsg
  extends ASN1Object
{
  private final BodyPartID bodyPartID;
  private final ASN1ObjectIdentifier otherMsgType;
  private final ASN1Encodable otherMsgValue;
  
  public OtherMsg(BodyPartID bodyPartID, ASN1ObjectIdentifier otherMsgType, ASN1Encodable otherMsgValue)
  {
    this.bodyPartID = bodyPartID;
    this.otherMsgType = otherMsgType;
    this.otherMsgValue = otherMsgValue;
  }
  
  private OtherMsg(ASN1Sequence seq)
  {
    if (seq.size() != 3)
    {
      throw new IllegalArgumentException("incorrect sequence size");
    }
    bodyPartID = BodyPartID.getInstance(seq.getObjectAt(0));
    otherMsgType = ASN1ObjectIdentifier.getInstance(seq.getObjectAt(1));
    otherMsgValue = seq.getObjectAt(2);
  }
  
  public static OtherMsg getInstance(Object o)
  {
    if ((o instanceof OtherMsg))
    {
      return (OtherMsg)o;
    }
    
    if (o != null)
    {
      return new OtherMsg(ASN1Sequence.getInstance(o));
    }
    
    return null;
  }
  


  public static OtherMsg getInstance(ASN1TaggedObject obj, boolean explicit)
  {
    return getInstance(ASN1Sequence.getInstance(obj, explicit));
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    v.add(bodyPartID);
    v.add(otherMsgType);
    v.add(otherMsgValue);
    
    return new DERSequence(v);
  }
  
  public BodyPartID getBodyPartID()
  {
    return bodyPartID;
  }
  
  public ASN1ObjectIdentifier getOtherMsgType()
  {
    return otherMsgType;
  }
  
  public ASN1Encodable getOtherMsgValue()
  {
    return otherMsgValue;
  }
}

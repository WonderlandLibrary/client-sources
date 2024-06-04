package org.spongycastle.asn1.esf;

import java.io.IOException;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;















public class OtherRevVals
  extends ASN1Object
{
  private ASN1ObjectIdentifier otherRevValType;
  private ASN1Encodable otherRevVals;
  
  public static OtherRevVals getInstance(Object obj)
  {
    if ((obj instanceof OtherRevVals))
    {
      return (OtherRevVals)obj;
    }
    if (obj != null)
    {
      return new OtherRevVals(ASN1Sequence.getInstance(obj));
    }
    
    return null;
  }
  
  private OtherRevVals(ASN1Sequence seq)
  {
    if (seq.size() != 2)
    {

      throw new IllegalArgumentException("Bad sequence size: " + seq.size());
    }
    otherRevValType = ((ASN1ObjectIdentifier)seq.getObjectAt(0));
    try
    {
      otherRevVals = ASN1Primitive.fromByteArray(seq.getObjectAt(1)
        .toASN1Primitive().getEncoded("DER"));
    }
    catch (IOException e)
    {
      throw new IllegalStateException();
    }
  }
  

  public OtherRevVals(ASN1ObjectIdentifier otherRevValType, ASN1Encodable otherRevVals)
  {
    this.otherRevValType = otherRevValType;
    this.otherRevVals = otherRevVals;
  }
  
  public ASN1ObjectIdentifier getOtherRevValType()
  {
    return otherRevValType;
  }
  
  public ASN1Encodable getOtherRevVals()
  {
    return otherRevVals;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    v.add(otherRevValType);
    v.add(otherRevVals);
    return new DERSequence(v);
  }
}

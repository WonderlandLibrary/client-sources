package org.spongycastle.asn1.esf;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;












public class CrlOcspRef
  extends ASN1Object
{
  private CrlListID crlids;
  private OcspListID ocspids;
  private OtherRevRefs otherRev;
  
  public static CrlOcspRef getInstance(Object obj)
  {
    if ((obj instanceof CrlOcspRef))
    {
      return (CrlOcspRef)obj;
    }
    if (obj != null)
    {
      return new CrlOcspRef(ASN1Sequence.getInstance(obj));
    }
    
    return null;
  }
  
  private CrlOcspRef(ASN1Sequence seq)
  {
    Enumeration e = seq.getObjects();
    while (e.hasMoreElements())
    {
      ASN1TaggedObject o = (ASN1TaggedObject)e.nextElement();
      switch (o.getTagNo())
      {
      case 0: 
        crlids = CrlListID.getInstance(o.getObject());
        break;
      case 1: 
        ocspids = OcspListID.getInstance(o.getObject());
        break;
      case 2: 
        otherRev = OtherRevRefs.getInstance(o.getObject());
        break;
      default: 
        throw new IllegalArgumentException("illegal tag");
      }
      
    }
  }
  
  public CrlOcspRef(CrlListID crlids, OcspListID ocspids, OtherRevRefs otherRev)
  {
    this.crlids = crlids;
    this.ocspids = ocspids;
    this.otherRev = otherRev;
  }
  
  public CrlListID getCrlids()
  {
    return crlids;
  }
  
  public OcspListID getOcspids()
  {
    return ocspids;
  }
  
  public OtherRevRefs getOtherRev()
  {
    return otherRev;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    if (null != crlids)
    {
      v.add(new DERTaggedObject(true, 0, crlids.toASN1Primitive()));
    }
    if (null != ocspids)
    {
      v.add(new DERTaggedObject(true, 1, ocspids.toASN1Primitive()));
    }
    if (null != otherRev)
    {
      v.add(new DERTaggedObject(true, 2, otherRev.toASN1Primitive()));
    }
    return new DERSequence(v);
  }
}

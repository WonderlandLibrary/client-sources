package org.spongycastle.asn1.cmc;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;












public class CMCUnsignedData
  extends ASN1Object
{
  private final BodyPartPath bodyPartPath;
  private final ASN1ObjectIdentifier identifier;
  private final ASN1Encodable content;
  
  public CMCUnsignedData(BodyPartPath bodyPartPath, ASN1ObjectIdentifier identifier, ASN1Encodable content)
  {
    this.bodyPartPath = bodyPartPath;
    this.identifier = identifier;
    this.content = content;
  }
  
  private CMCUnsignedData(ASN1Sequence seq)
  {
    if (seq.size() != 3)
    {
      throw new IllegalArgumentException("incorrect sequence size");
    }
    bodyPartPath = BodyPartPath.getInstance(seq.getObjectAt(0));
    identifier = ASN1ObjectIdentifier.getInstance(seq.getObjectAt(1));
    content = seq.getObjectAt(2);
  }
  
  public static CMCUnsignedData getInstance(Object o)
  {
    if ((o instanceof CMCUnsignedData))
    {
      return (CMCUnsignedData)o;
    }
    
    if (o != null)
    {
      return new CMCUnsignedData(ASN1Sequence.getInstance(o));
    }
    
    return null;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    v.add(bodyPartPath);
    v.add(identifier);
    v.add(content);
    
    return new DERSequence(v);
  }
  
  public BodyPartPath getBodyPartPath()
  {
    return bodyPartPath;
  }
  
  public ASN1ObjectIdentifier getIdentifier()
  {
    return identifier;
  }
  
  public ASN1Encodable getContent()
  {
    return content;
  }
}

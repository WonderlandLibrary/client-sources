package org.spongycastle.asn1.cmc;

import java.io.IOException;
import org.spongycastle.asn1.ASN1Choice;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;










public class BodyPartReference
  extends ASN1Object
  implements ASN1Choice
{
  private final BodyPartID bodyPartID;
  private final BodyPartPath bodyPartPath;
  
  public BodyPartReference(BodyPartID bodyPartID)
  {
    this.bodyPartID = bodyPartID;
    bodyPartPath = null;
  }
  
  public BodyPartReference(BodyPartPath bodyPartPath)
  {
    bodyPartID = null;
    this.bodyPartPath = bodyPartPath;
  }
  

  public static BodyPartReference getInstance(Object obj)
  {
    if ((obj instanceof BodyPartReference))
    {
      return (BodyPartReference)obj;
    }
    
    if (obj != null)
    {
      if ((obj instanceof ASN1Encodable))
      {
        ASN1Encodable asn1Prim = ((ASN1Encodable)obj).toASN1Primitive();
        
        if ((asn1Prim instanceof ASN1Integer))
        {
          return new BodyPartReference(BodyPartID.getInstance(asn1Prim));
        }
        if ((asn1Prim instanceof ASN1Sequence))
        {
          return new BodyPartReference(BodyPartPath.getInstance(asn1Prim));
        }
      }
      if ((obj instanceof byte[]))
      {
        try
        {
          return getInstance(ASN1Primitive.fromByteArray((byte[])obj));
        }
        catch (IOException e)
        {
          throw new IllegalArgumentException("unknown encoding in getInstance()");
        }
      }
      throw new IllegalArgumentException("unknown object in getInstance(): " + obj.getClass().getName());
    }
    
    return null;
  }
  
  public boolean isBodyPartID()
  {
    return bodyPartID != null;
  }
  
  public BodyPartID getBodyPartID()
  {
    return bodyPartID;
  }
  
  public BodyPartPath getBodyPartPath()
  {
    return bodyPartPath;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    if (bodyPartID != null)
    {
      return bodyPartID.toASN1Primitive();
    }
    

    return bodyPartPath.toASN1Primitive();
  }
}

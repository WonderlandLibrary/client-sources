package org.spongycastle.asn1.cmc;

import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;







public class BodyPartList
  extends ASN1Object
{
  private final BodyPartID[] bodyPartIDs;
  
  public static BodyPartList getInstance(Object obj)
  {
    if ((obj instanceof BodyPartList))
    {
      return (BodyPartList)obj;
    }
    
    if (obj != null)
    {
      return new BodyPartList(ASN1Sequence.getInstance(obj));
    }
    
    return null;
  }
  


  public static BodyPartList getInstance(ASN1TaggedObject obj, boolean explicit)
  {
    return getInstance(ASN1Sequence.getInstance(obj, explicit));
  }
  






  public BodyPartList(BodyPartID bodyPartID)
  {
    bodyPartIDs = new BodyPartID[] { bodyPartID };
  }
  


  public BodyPartList(BodyPartID[] bodyPartIDs)
  {
    this.bodyPartIDs = Utils.clone(bodyPartIDs);
  }
  

  private BodyPartList(ASN1Sequence seq)
  {
    bodyPartIDs = Utils.toBodyPartIDArray(seq);
  }
  
  public BodyPartID[] getBodyPartIDs()
  {
    return Utils.clone(bodyPartIDs);
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    return new DERSequence(bodyPartIDs);
  }
}

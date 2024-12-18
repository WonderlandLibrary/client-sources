package org.spongycastle.asn1.cmc;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;











public class LraPopWitness
  extends ASN1Object
{
  private final BodyPartID pkiDataBodyid;
  private final ASN1Sequence bodyIds;
  
  public LraPopWitness(BodyPartID pkiDataBodyid, ASN1Sequence bodyIds)
  {
    this.pkiDataBodyid = pkiDataBodyid;
    this.bodyIds = bodyIds;
  }
  
  private LraPopWitness(ASN1Sequence seq)
  {
    if (seq.size() != 2)
    {
      throw new IllegalArgumentException("incorrect sequence size");
    }
    pkiDataBodyid = BodyPartID.getInstance(seq.getObjectAt(0));
    bodyIds = ASN1Sequence.getInstance(seq.getObjectAt(1));
  }
  
  public static LraPopWitness getInstance(Object o)
  {
    if ((o instanceof LraPopWitness))
    {
      return (LraPopWitness)o;
    }
    
    if (o != null)
    {
      return new LraPopWitness(ASN1Sequence.getInstance(o));
    }
    
    return null;
  }
  
  public BodyPartID getPkiDataBodyid()
  {
    return pkiDataBodyid;
  }
  

  public BodyPartID[] getBodyIds()
  {
    BodyPartID[] rv = new BodyPartID[bodyIds.size()];
    
    for (int i = 0; i != bodyIds.size(); i++)
    {
      rv[i] = BodyPartID.getInstance(bodyIds.getObjectAt(i));
    }
    
    return rv;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    v.add(pkiDataBodyid);
    v.add(bodyIds);
    
    return new DERSequence(v);
  }
}

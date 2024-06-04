package org.spongycastle.asn1.x9;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERSequence;

/**
 * @deprecated
 */
public class DHValidationParms extends ASN1Object
{
  private DERBitString seed;
  private ASN1Integer pgenCounter;
  
  public static DHValidationParms getInstance(ASN1TaggedObject obj, boolean explicit)
  {
    return getInstance(ASN1Sequence.getInstance(obj, explicit));
  }
  
  public static DHValidationParms getInstance(Object obj)
  {
    if ((obj instanceof DHValidationParms))
    {
      return (DHValidationParms)obj;
    }
    if (obj != null)
    {
      return new DHValidationParms(ASN1Sequence.getInstance(obj));
    }
    
    return null;
  }
  
  public DHValidationParms(DERBitString seed, ASN1Integer pgenCounter)
  {
    if (seed == null)
    {
      throw new IllegalArgumentException("'seed' cannot be null");
    }
    if (pgenCounter == null)
    {
      throw new IllegalArgumentException("'pgenCounter' cannot be null");
    }
    
    this.seed = seed;
    this.pgenCounter = pgenCounter;
  }
  
  private DHValidationParms(ASN1Sequence seq)
  {
    if (seq.size() != 2)
    {
      throw new IllegalArgumentException("Bad sequence size: " + seq.size());
    }
    
    seed = DERBitString.getInstance(seq.getObjectAt(0));
    pgenCounter = ASN1Integer.getInstance(seq.getObjectAt(1));
  }
  
  public DERBitString getSeed()
  {
    return seed;
  }
  
  public ASN1Integer getPgenCounter()
  {
    return pgenCounter;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    v.add(seed);
    v.add(pgenCounter);
    return new DERSequence(v);
  }
}

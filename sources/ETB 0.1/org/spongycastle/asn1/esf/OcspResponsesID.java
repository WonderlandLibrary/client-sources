package org.spongycastle.asn1.esf;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;










public class OcspResponsesID
  extends ASN1Object
{
  private OcspIdentifier ocspIdentifier;
  private OtherHash ocspRepHash;
  
  public static OcspResponsesID getInstance(Object obj)
  {
    if ((obj instanceof OcspResponsesID))
    {
      return (OcspResponsesID)obj;
    }
    if (obj != null)
    {
      return new OcspResponsesID(ASN1Sequence.getInstance(obj));
    }
    
    return null;
  }
  
  private OcspResponsesID(ASN1Sequence seq)
  {
    if ((seq.size() < 1) || (seq.size() > 2))
    {

      throw new IllegalArgumentException("Bad sequence size: " + seq.size());
    }
    ocspIdentifier = OcspIdentifier.getInstance(seq.getObjectAt(0));
    if (seq.size() > 1)
    {
      ocspRepHash = OtherHash.getInstance(seq.getObjectAt(1));
    }
  }
  
  public OcspResponsesID(OcspIdentifier ocspIdentifier)
  {
    this(ocspIdentifier, null);
  }
  
  public OcspResponsesID(OcspIdentifier ocspIdentifier, OtherHash ocspRepHash)
  {
    this.ocspIdentifier = ocspIdentifier;
    this.ocspRepHash = ocspRepHash;
  }
  
  public OcspIdentifier getOcspIdentifier()
  {
    return ocspIdentifier;
  }
  
  public OtherHash getOcspRepHash()
  {
    return ocspRepHash;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    v.add(ocspIdentifier);
    if (null != ocspRepHash)
    {
      v.add(ocspRepHash);
    }
    return new DERSequence(v);
  }
}

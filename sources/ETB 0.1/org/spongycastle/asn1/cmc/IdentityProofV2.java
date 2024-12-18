package org.spongycastle.asn1.cmc;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.util.Arrays;











public class IdentityProofV2
  extends ASN1Object
{
  private final AlgorithmIdentifier proofAlgID;
  private final AlgorithmIdentifier macAlgId;
  private final byte[] witness;
  
  public IdentityProofV2(AlgorithmIdentifier proofAlgID, AlgorithmIdentifier macAlgId, byte[] witness)
  {
    this.proofAlgID = proofAlgID;
    this.macAlgId = macAlgId;
    this.witness = Arrays.clone(witness);
  }
  
  private IdentityProofV2(ASN1Sequence seq)
  {
    if (seq.size() != 3)
    {
      throw new IllegalArgumentException("incorrect sequence size");
    }
    proofAlgID = AlgorithmIdentifier.getInstance(seq.getObjectAt(0));
    macAlgId = AlgorithmIdentifier.getInstance(seq.getObjectAt(1));
    witness = Arrays.clone(ASN1OctetString.getInstance(seq.getObjectAt(2)).getOctets());
  }
  
  public static IdentityProofV2 getInstance(Object o)
  {
    if ((o instanceof IdentityProofV2))
    {
      return (IdentityProofV2)o;
    }
    
    if (o != null)
    {
      return new IdentityProofV2(ASN1Sequence.getInstance(o));
    }
    
    return null;
  }
  
  public AlgorithmIdentifier getProofAlgID()
  {
    return proofAlgID;
  }
  
  public AlgorithmIdentifier getMacAlgId()
  {
    return macAlgId;
  }
  
  public byte[] getWitness()
  {
    return Arrays.clone(witness);
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    v.add(proofAlgID);
    v.add(macAlgId);
    v.add(new DEROctetString(getWitness()));
    
    return new DERSequence(v);
  }
}

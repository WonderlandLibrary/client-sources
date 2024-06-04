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











public class PopLinkWitnessV2
  extends ASN1Object
{
  private final AlgorithmIdentifier keyGenAlgorithm;
  private final AlgorithmIdentifier macAlgorithm;
  private final byte[] witness;
  
  public PopLinkWitnessV2(AlgorithmIdentifier keyGenAlgorithm, AlgorithmIdentifier macAlgorithm, byte[] witness)
  {
    this.keyGenAlgorithm = keyGenAlgorithm;
    this.macAlgorithm = macAlgorithm;
    this.witness = Arrays.clone(witness);
  }
  
  private PopLinkWitnessV2(ASN1Sequence seq)
  {
    if (seq.size() != 3)
    {
      throw new IllegalArgumentException("incorrect sequence size");
    }
    keyGenAlgorithm = AlgorithmIdentifier.getInstance(seq.getObjectAt(0));
    macAlgorithm = AlgorithmIdentifier.getInstance(seq.getObjectAt(1));
    witness = Arrays.clone(ASN1OctetString.getInstance(seq.getObjectAt(2)).getOctets());
  }
  
  public static PopLinkWitnessV2 getInstance(Object o)
  {
    if ((o instanceof PopLinkWitnessV2))
    {
      return (PopLinkWitnessV2)o;
    }
    
    if (o != null)
    {
      return new PopLinkWitnessV2(ASN1Sequence.getInstance(o));
    }
    
    return null;
  }
  
  public AlgorithmIdentifier getKeyGenAlgorithm()
  {
    return keyGenAlgorithm;
  }
  
  public AlgorithmIdentifier getMacAlgorithm()
  {
    return macAlgorithm;
  }
  
  public byte[] getWitness()
  {
    return Arrays.clone(witness);
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    v.add(keyGenAlgorithm);
    v.add(macAlgorithm);
    v.add(new DEROctetString(getWitness()));
    
    return new DERSequence(v);
  }
}

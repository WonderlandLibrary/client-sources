package org.spongycastle.asn1.cms;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;














public class OriginatorPublicKey
  extends ASN1Object
{
  private AlgorithmIdentifier algorithm;
  private DERBitString publicKey;
  
  public OriginatorPublicKey(AlgorithmIdentifier algorithm, byte[] publicKey)
  {
    this.algorithm = algorithm;
    this.publicKey = new DERBitString(publicKey);
  }
  

  /**
   * @deprecated
   */
  public OriginatorPublicKey(ASN1Sequence seq)
  {
    algorithm = AlgorithmIdentifier.getInstance(seq.getObjectAt(0));
    publicKey = ((DERBitString)seq.getObjectAt(1));
  }
  











  public static OriginatorPublicKey getInstance(ASN1TaggedObject obj, boolean explicit)
  {
    return getInstance(ASN1Sequence.getInstance(obj, explicit));
  }
  














  public static OriginatorPublicKey getInstance(Object obj)
  {
    if ((obj instanceof OriginatorPublicKey))
    {
      return (OriginatorPublicKey)obj;
    }
    
    if (obj != null)
    {
      return new OriginatorPublicKey(ASN1Sequence.getInstance(obj));
    }
    
    return null;
  }
  
  public AlgorithmIdentifier getAlgorithm()
  {
    return algorithm;
  }
  
  public DERBitString getPublicKey()
  {
    return publicKey;
  }
  



  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    v.add(algorithm);
    v.add(publicKey);
    
    return new DERSequence(v);
  }
}

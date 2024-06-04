package org.spongycastle.asn1.x509;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Enumerated;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERSequence;





































public class ObjectDigestInfo
  extends ASN1Object
{
  public static final int publicKey = 0;
  public static final int publicKeyCert = 1;
  public static final int otherObjectDigest = 2;
  ASN1Enumerated digestedObjectType;
  ASN1ObjectIdentifier otherObjectTypeID;
  AlgorithmIdentifier digestAlgorithm;
  DERBitString objectDigest;
  
  public static ObjectDigestInfo getInstance(Object obj)
  {
    if ((obj instanceof ObjectDigestInfo))
    {
      return (ObjectDigestInfo)obj;
    }
    
    if (obj != null)
    {
      return new ObjectDigestInfo(ASN1Sequence.getInstance(obj));
    }
    
    return null;
  }
  


  public static ObjectDigestInfo getInstance(ASN1TaggedObject obj, boolean explicit)
  {
    return getInstance(ASN1Sequence.getInstance(obj, explicit));
  }
  

















  public ObjectDigestInfo(int digestedObjectType, ASN1ObjectIdentifier otherObjectTypeID, AlgorithmIdentifier digestAlgorithm, byte[] objectDigest)
  {
    this.digestedObjectType = new ASN1Enumerated(digestedObjectType);
    if (digestedObjectType == 2)
    {
      this.otherObjectTypeID = otherObjectTypeID;
    }
    
    this.digestAlgorithm = digestAlgorithm;
    this.objectDigest = new DERBitString(objectDigest);
  }
  

  private ObjectDigestInfo(ASN1Sequence seq)
  {
    if ((seq.size() > 4) || (seq.size() < 3))
    {

      throw new IllegalArgumentException("Bad sequence size: " + seq.size());
    }
    
    digestedObjectType = ASN1Enumerated.getInstance(seq.getObjectAt(0));
    
    int offset = 0;
    
    if (seq.size() == 4)
    {
      otherObjectTypeID = ASN1ObjectIdentifier.getInstance(seq.getObjectAt(1));
      offset++;
    }
    
    digestAlgorithm = AlgorithmIdentifier.getInstance(seq.getObjectAt(1 + offset));
    
    objectDigest = DERBitString.getInstance(seq.getObjectAt(2 + offset));
  }
  
  public ASN1Enumerated getDigestedObjectType()
  {
    return digestedObjectType;
  }
  
  public ASN1ObjectIdentifier getOtherObjectTypeID()
  {
    return otherObjectTypeID;
  }
  
  public AlgorithmIdentifier getDigestAlgorithm()
  {
    return digestAlgorithm;
  }
  
  public DERBitString getObjectDigest()
  {
    return objectDigest;
  }
  



















  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    v.add(digestedObjectType);
    
    if (otherObjectTypeID != null)
    {
      v.add(otherObjectTypeID);
    }
    
    v.add(digestAlgorithm);
    v.add(objectDigest);
    
    return new DERSequence(v);
  }
}

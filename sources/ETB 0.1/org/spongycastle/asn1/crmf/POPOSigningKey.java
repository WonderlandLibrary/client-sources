package org.spongycastle.asn1.crmf;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;

public class POPOSigningKey
  extends ASN1Object
{
  private POPOSigningKeyInput poposkInput;
  private AlgorithmIdentifier algorithmIdentifier;
  private DERBitString signature;
  
  private POPOSigningKey(ASN1Sequence seq)
  {
    int index = 0;
    
    if ((seq.getObjectAt(index) instanceof ASN1TaggedObject))
    {

      ASN1TaggedObject tagObj = (ASN1TaggedObject)seq.getObjectAt(index++);
      if (tagObj.getTagNo() != 0)
      {

        throw new IllegalArgumentException("Unknown POPOSigningKeyInput tag: " + tagObj.getTagNo());
      }
      poposkInput = POPOSigningKeyInput.getInstance(tagObj.getObject());
    }
    algorithmIdentifier = AlgorithmIdentifier.getInstance(seq.getObjectAt(index++));
    signature = DERBitString.getInstance(seq.getObjectAt(index));
  }
  
  public static POPOSigningKey getInstance(Object o)
  {
    if ((o instanceof POPOSigningKey))
    {
      return (POPOSigningKey)o;
    }
    
    if (o != null)
    {
      return new POPOSigningKey(ASN1Sequence.getInstance(o));
    }
    
    return null;
  }
  
  public static POPOSigningKey getInstance(ASN1TaggedObject obj, boolean explicit)
  {
    return getInstance(ASN1Sequence.getInstance(obj, explicit));
  }
  












  public POPOSigningKey(POPOSigningKeyInput poposkIn, AlgorithmIdentifier aid, DERBitString signature)
  {
    poposkInput = poposkIn;
    algorithmIdentifier = aid;
    this.signature = signature;
  }
  
  public POPOSigningKeyInput getPoposkInput()
  {
    return poposkInput;
  }
  
  public AlgorithmIdentifier getAlgorithmIdentifier()
  {
    return algorithmIdentifier;
  }
  
  public DERBitString getSignature()
  {
    return signature;
  }
  




















  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    if (poposkInput != null)
    {
      v.add(new DERTaggedObject(false, 0, poposkInput));
    }
    
    v.add(algorithmIdentifier);
    v.add(signature);
    
    return new DERSequence(v);
  }
}

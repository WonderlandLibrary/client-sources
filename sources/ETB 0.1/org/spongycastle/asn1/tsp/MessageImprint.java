package org.spongycastle.asn1.tsp;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.util.Arrays;





public class MessageImprint
  extends ASN1Object
{
  AlgorithmIdentifier hashAlgorithm;
  byte[] hashedMessage;
  
  public static MessageImprint getInstance(Object o)
  {
    if ((o instanceof MessageImprint))
    {
      return (MessageImprint)o;
    }
    
    if (o != null)
    {
      return new MessageImprint(ASN1Sequence.getInstance(o));
    }
    
    return null;
  }
  

  private MessageImprint(ASN1Sequence seq)
  {
    hashAlgorithm = AlgorithmIdentifier.getInstance(seq.getObjectAt(0));
    hashedMessage = ASN1OctetString.getInstance(seq.getObjectAt(1)).getOctets();
  }
  


  public MessageImprint(AlgorithmIdentifier hashAlgorithm, byte[] hashedMessage)
  {
    this.hashAlgorithm = hashAlgorithm;
    this.hashedMessage = Arrays.clone(hashedMessage);
  }
  
  public AlgorithmIdentifier getHashAlgorithm()
  {
    return hashAlgorithm;
  }
  
  public byte[] getHashedMessage()
  {
    return Arrays.clone(hashedMessage);
  }
  







  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    v.add(hashAlgorithm);
    v.add(new DEROctetString(hashedMessage));
    
    return new DERSequence(v);
  }
}

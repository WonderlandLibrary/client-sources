package org.spongycastle.asn1.x9;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;




















public class KeySpecificInfo
  extends ASN1Object
{
  private ASN1ObjectIdentifier algorithm;
  private ASN1OctetString counter;
  
  public KeySpecificInfo(ASN1ObjectIdentifier algorithm, ASN1OctetString counter)
  {
    this.algorithm = algorithm;
    this.counter = counter;
  }
  






  public static KeySpecificInfo getInstance(Object obj)
  {
    if ((obj instanceof KeySpecificInfo))
    {
      return (KeySpecificInfo)obj;
    }
    if (obj != null)
    {
      return new KeySpecificInfo(ASN1Sequence.getInstance(obj));
    }
    
    return null;
  }
  

  private KeySpecificInfo(ASN1Sequence seq)
  {
    Enumeration e = seq.getObjects();
    
    algorithm = ((ASN1ObjectIdentifier)e.nextElement());
    counter = ((ASN1OctetString)e.nextElement());
  }
  





  public ASN1ObjectIdentifier getAlgorithm()
  {
    return algorithm;
  }
  





  public ASN1OctetString getCounter()
  {
    return counter;
  }
  





  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    v.add(algorithm);
    v.add(counter);
    
    return new DERSequence(v);
  }
}

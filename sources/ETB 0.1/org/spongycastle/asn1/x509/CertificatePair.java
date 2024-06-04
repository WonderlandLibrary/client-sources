package org.spongycastle.asn1.x509;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;




































public class CertificatePair
  extends ASN1Object
{
  private Certificate forward;
  private Certificate reverse;
  
  public static CertificatePair getInstance(Object obj)
  {
    if ((obj == null) || ((obj instanceof CertificatePair)))
    {
      return (CertificatePair)obj;
    }
    
    if ((obj instanceof ASN1Sequence))
    {
      return new CertificatePair((ASN1Sequence)obj);
    }
    

    throw new IllegalArgumentException("illegal object in getInstance: " + obj.getClass().getName());
  }
  













  private CertificatePair(ASN1Sequence seq)
  {
    if ((seq.size() != 1) && (seq.size() != 2))
    {

      throw new IllegalArgumentException("Bad sequence size: " + seq.size());
    }
    
    Enumeration e = seq.getObjects();
    
    while (e.hasMoreElements())
    {
      ASN1TaggedObject o = ASN1TaggedObject.getInstance(e.nextElement());
      if (o.getTagNo() == 0)
      {
        forward = Certificate.getInstance(o, true);
      }
      else if (o.getTagNo() == 1)
      {
        reverse = Certificate.getInstance(o, true);

      }
      else
      {
        throw new IllegalArgumentException("Bad tag number: " + o.getTagNo());
      }
    }
  }
  






  public CertificatePair(Certificate forward, Certificate reverse)
  {
    this.forward = forward;
    this.reverse = reverse;
  }
  













  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector vec = new ASN1EncodableVector();
    
    if (forward != null)
    {
      vec.add(new DERTaggedObject(0, forward));
    }
    if (reverse != null)
    {
      vec.add(new DERTaggedObject(1, reverse));
    }
    
    return new DERSequence(vec);
  }
  



  public Certificate getForward()
  {
    return forward;
  }
  



  public Certificate getReverse()
  {
    return reverse;
  }
}

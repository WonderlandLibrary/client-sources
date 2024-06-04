package org.spongycastle.asn1.x509;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERSequence;





public class AttributeCertificate
  extends ASN1Object
{
  AttributeCertificateInfo acinfo;
  AlgorithmIdentifier signatureAlgorithm;
  DERBitString signatureValue;
  
  public static AttributeCertificate getInstance(Object obj)
  {
    if ((obj instanceof AttributeCertificate))
    {
      return (AttributeCertificate)obj;
    }
    if (obj != null)
    {
      return new AttributeCertificate(ASN1Sequence.getInstance(obj));
    }
    
    return null;
  }
  



  public AttributeCertificate(AttributeCertificateInfo acinfo, AlgorithmIdentifier signatureAlgorithm, DERBitString signatureValue)
  {
    this.acinfo = acinfo;
    this.signatureAlgorithm = signatureAlgorithm;
    this.signatureValue = signatureValue;
  }
  

  /**
   * @deprecated
   */
  public AttributeCertificate(ASN1Sequence seq)
  {
    if (seq.size() != 3)
    {

      throw new IllegalArgumentException("Bad sequence size: " + seq.size());
    }
    
    acinfo = AttributeCertificateInfo.getInstance(seq.getObjectAt(0));
    signatureAlgorithm = AlgorithmIdentifier.getInstance(seq.getObjectAt(1));
    signatureValue = DERBitString.getInstance(seq.getObjectAt(2));
  }
  
  public AttributeCertificateInfo getAcinfo()
  {
    return acinfo;
  }
  
  public AlgorithmIdentifier getSignatureAlgorithm()
  {
    return signatureAlgorithm;
  }
  
  public DERBitString getSignatureValue()
  {
    return signatureValue;
  }
  










  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    v.add(acinfo);
    v.add(signatureAlgorithm);
    v.add(signatureValue);
    
    return new DERSequence(v);
  }
}

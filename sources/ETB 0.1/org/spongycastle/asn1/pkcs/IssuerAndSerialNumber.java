package org.spongycastle.asn1.pkcs;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.asn1.x509.X509Name;



public class IssuerAndSerialNumber
  extends ASN1Object
{
  X500Name name;
  ASN1Integer certSerialNumber;
  
  public static IssuerAndSerialNumber getInstance(Object obj)
  {
    if ((obj instanceof IssuerAndSerialNumber))
    {
      return (IssuerAndSerialNumber)obj;
    }
    if (obj != null)
    {
      return new IssuerAndSerialNumber(ASN1Sequence.getInstance(obj));
    }
    
    return null;
  }
  

  private IssuerAndSerialNumber(ASN1Sequence seq)
  {
    name = X500Name.getInstance(seq.getObjectAt(0));
    certSerialNumber = ((ASN1Integer)seq.getObjectAt(1));
  }
  


  public IssuerAndSerialNumber(X509Name name, BigInteger certSerialNumber)
  {
    this.name = X500Name.getInstance(name.toASN1Primitive());
    this.certSerialNumber = new ASN1Integer(certSerialNumber);
  }
  


  public IssuerAndSerialNumber(X509Name name, ASN1Integer certSerialNumber)
  {
    this.name = X500Name.getInstance(name.toASN1Primitive());
    this.certSerialNumber = certSerialNumber;
  }
  


  public IssuerAndSerialNumber(X500Name name, BigInteger certSerialNumber)
  {
    this.name = name;
    this.certSerialNumber = new ASN1Integer(certSerialNumber);
  }
  
  public X500Name getName()
  {
    return name;
  }
  
  public ASN1Integer getCertificateSerialNumber()
  {
    return certSerialNumber;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    v.add(name);
    v.add(certSerialNumber);
    
    return new DERSequence(v);
  }
}

package org.spongycastle.asn1.ess;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.oiw.OIWObjectIdentifiers;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.DigestInfo;
import org.spongycastle.asn1.x509.IssuerSerial;


public class OtherCertID
  extends ASN1Object
{
  private ASN1Encodable otherCertHash;
  private IssuerSerial issuerSerial;
  
  public static OtherCertID getInstance(Object o)
  {
    if ((o instanceof OtherCertID))
    {
      return (OtherCertID)o;
    }
    if (o != null)
    {
      return new OtherCertID(ASN1Sequence.getInstance(o));
    }
    
    return null;
  }
  



  private OtherCertID(ASN1Sequence seq)
  {
    if ((seq.size() < 1) || (seq.size() > 2))
    {

      throw new IllegalArgumentException("Bad sequence size: " + seq.size());
    }
    
    if ((seq.getObjectAt(0).toASN1Primitive() instanceof ASN1OctetString))
    {
      otherCertHash = ASN1OctetString.getInstance(seq.getObjectAt(0));
    }
    else
    {
      otherCertHash = DigestInfo.getInstance(seq.getObjectAt(0));
    }
    

    if (seq.size() > 1)
    {
      issuerSerial = IssuerSerial.getInstance(seq.getObjectAt(1));
    }
  }
  


  public OtherCertID(AlgorithmIdentifier algId, byte[] digest)
  {
    otherCertHash = new DigestInfo(algId, digest);
  }
  



  public OtherCertID(AlgorithmIdentifier algId, byte[] digest, IssuerSerial issuerSerial)
  {
    otherCertHash = new DigestInfo(algId, digest);
    this.issuerSerial = issuerSerial;
  }
  
  public AlgorithmIdentifier getAlgorithmHash()
  {
    if ((otherCertHash.toASN1Primitive() instanceof ASN1OctetString))
    {

      return new AlgorithmIdentifier(OIWObjectIdentifiers.idSHA1);
    }
    

    return DigestInfo.getInstance(otherCertHash).getAlgorithmId();
  }
  

  public byte[] getCertHash()
  {
    if ((otherCertHash.toASN1Primitive() instanceof ASN1OctetString))
    {

      return ((ASN1OctetString)otherCertHash.toASN1Primitive()).getOctets();
    }
    

    return DigestInfo.getInstance(otherCertHash).getDigest();
  }
  

  public IssuerSerial getIssuerSerial()
  {
    return issuerSerial;
  }
  
















  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    v.add(otherCertHash);
    
    if (issuerSerial != null)
    {
      v.add(issuerSerial);
    }
    
    return new DERSequence(v);
  }
}

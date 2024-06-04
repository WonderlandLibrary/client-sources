package org.spongycastle.asn1.isismtt.ocsp;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;



















public class CertHash
  extends ASN1Object
{
  private AlgorithmIdentifier hashAlgorithm;
  private byte[] certificateHash;
  
  public static CertHash getInstance(Object obj)
  {
    if ((obj == null) || ((obj instanceof CertHash)))
    {
      return (CertHash)obj;
    }
    
    if ((obj instanceof ASN1Sequence))
    {
      return new CertHash((ASN1Sequence)obj);
    }
    

    throw new IllegalArgumentException("illegal object in getInstance: " + obj.getClass().getName());
  }
  













  private CertHash(ASN1Sequence seq)
  {
    if (seq.size() != 2)
    {

      throw new IllegalArgumentException("Bad sequence size: " + seq.size());
    }
    hashAlgorithm = AlgorithmIdentifier.getInstance(seq.getObjectAt(0));
    certificateHash = DEROctetString.getInstance(seq.getObjectAt(1)).getOctets();
  }
  






  public CertHash(AlgorithmIdentifier hashAlgorithm, byte[] certificateHash)
  {
    this.hashAlgorithm = hashAlgorithm;
    this.certificateHash = new byte[certificateHash.length];
    System.arraycopy(certificateHash, 0, this.certificateHash, 0, certificateHash.length);
  }
  

  public AlgorithmIdentifier getHashAlgorithm()
  {
    return hashAlgorithm;
  }
  
  public byte[] getCertificateHash()
  {
    return certificateHash;
  }
  













  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector vec = new ASN1EncodableVector();
    vec.add(hashAlgorithm);
    vec.add(new DEROctetString(certificateHash));
    return new DERSequence(vec);
  }
}

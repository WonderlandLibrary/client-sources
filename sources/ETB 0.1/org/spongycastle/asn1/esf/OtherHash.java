package org.spongycastle.asn1.esf;

import org.spongycastle.asn1.ASN1Choice;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.oiw.OIWObjectIdentifiers;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;










public class OtherHash
  extends ASN1Object
  implements ASN1Choice
{
  private ASN1OctetString sha1Hash;
  private OtherHashAlgAndValue otherHash;
  
  public static OtherHash getInstance(Object obj)
  {
    if ((obj instanceof OtherHash))
    {
      return (OtherHash)obj;
    }
    if ((obj instanceof ASN1OctetString))
    {
      return new OtherHash((ASN1OctetString)obj);
    }
    return new OtherHash(OtherHashAlgAndValue.getInstance(obj));
  }
  
  private OtherHash(ASN1OctetString sha1Hash)
  {
    this.sha1Hash = sha1Hash;
  }
  
  public OtherHash(OtherHashAlgAndValue otherHash)
  {
    this.otherHash = otherHash;
  }
  
  public OtherHash(byte[] sha1Hash)
  {
    this.sha1Hash = new DEROctetString(sha1Hash);
  }
  
  public AlgorithmIdentifier getHashAlgorithm()
  {
    if (null == otherHash)
    {
      return new AlgorithmIdentifier(OIWObjectIdentifiers.idSHA1);
    }
    return otherHash.getHashAlgorithm();
  }
  
  public byte[] getHashValue()
  {
    if (null == otherHash)
    {
      return sha1Hash.getOctets();
    }
    return otherHash.getHashValue().getOctets();
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    if (null == otherHash)
    {
      return sha1Hash;
    }
    return otherHash.toASN1Primitive();
  }
}

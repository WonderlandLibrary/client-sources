package org.spongycastle.asn1.esf;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1UTCTime;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x500.X500Name;












public class CrlIdentifier
  extends ASN1Object
{
  private X500Name crlIssuer;
  private ASN1UTCTime crlIssuedTime;
  private ASN1Integer crlNumber;
  
  public static CrlIdentifier getInstance(Object obj)
  {
    if ((obj instanceof CrlIdentifier))
    {
      return (CrlIdentifier)obj;
    }
    if (obj != null)
    {
      return new CrlIdentifier(ASN1Sequence.getInstance(obj));
    }
    
    return null;
  }
  
  private CrlIdentifier(ASN1Sequence seq)
  {
    if ((seq.size() < 2) || (seq.size() > 3))
    {
      throw new IllegalArgumentException();
    }
    crlIssuer = X500Name.getInstance(seq.getObjectAt(0));
    crlIssuedTime = ASN1UTCTime.getInstance(seq.getObjectAt(1));
    if (seq.size() > 2)
    {
      crlNumber = ASN1Integer.getInstance(seq.getObjectAt(2));
    }
  }
  
  public CrlIdentifier(X500Name crlIssuer, ASN1UTCTime crlIssuedTime)
  {
    this(crlIssuer, crlIssuedTime, null);
  }
  

  public CrlIdentifier(X500Name crlIssuer, ASN1UTCTime crlIssuedTime, BigInteger crlNumber)
  {
    this.crlIssuer = crlIssuer;
    this.crlIssuedTime = crlIssuedTime;
    if (null != crlNumber)
    {
      this.crlNumber = new ASN1Integer(crlNumber);
    }
  }
  
  public X500Name getCrlIssuer()
  {
    return crlIssuer;
  }
  
  public ASN1UTCTime getCrlIssuedTime()
  {
    return crlIssuedTime;
  }
  
  public BigInteger getCrlNumber()
  {
    if (null == crlNumber)
    {
      return null;
    }
    return crlNumber.getValue();
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    v.add(crlIssuer.toASN1Primitive());
    v.add(crlIssuedTime);
    if (null != crlNumber)
    {
      v.add(crlNumber);
    }
    return new DERSequence(v);
  }
}

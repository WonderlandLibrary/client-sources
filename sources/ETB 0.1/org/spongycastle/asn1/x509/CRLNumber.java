package org.spongycastle.asn1.x509;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;









public class CRLNumber
  extends ASN1Object
{
  private BigInteger number;
  
  public CRLNumber(BigInteger number)
  {
    this.number = number;
  }
  
  public BigInteger getCRLNumber()
  {
    return number;
  }
  
  public String toString()
  {
    return "CRLNumber: " + getCRLNumber();
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    return new ASN1Integer(number);
  }
  
  public static CRLNumber getInstance(Object o)
  {
    if ((o instanceof CRLNumber))
    {
      return (CRLNumber)o;
    }
    if (o != null)
    {
      return new CRLNumber(ASN1Integer.getInstance(o).getValue());
    }
    
    return null;
  }
}

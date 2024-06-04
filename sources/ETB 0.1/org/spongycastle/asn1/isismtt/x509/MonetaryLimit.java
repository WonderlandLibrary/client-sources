package org.spongycastle.asn1.isismtt.x509;

import java.math.BigInteger;
import java.util.Enumeration;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERPrintableString;
import org.spongycastle.asn1.DERSequence;

























public class MonetaryLimit
  extends ASN1Object
{
  DERPrintableString currency;
  ASN1Integer amount;
  ASN1Integer exponent;
  
  public static MonetaryLimit getInstance(Object obj)
  {
    if ((obj == null) || ((obj instanceof MonetaryLimit)))
    {
      return (MonetaryLimit)obj;
    }
    
    if ((obj instanceof ASN1Sequence))
    {
      return new MonetaryLimit(ASN1Sequence.getInstance(obj));
    }
    
    throw new IllegalArgumentException("unknown object in getInstance");
  }
  
  private MonetaryLimit(ASN1Sequence seq)
  {
    if (seq.size() != 3)
    {

      throw new IllegalArgumentException("Bad sequence size: " + seq.size());
    }
    Enumeration e = seq.getObjects();
    currency = DERPrintableString.getInstance(e.nextElement());
    amount = ASN1Integer.getInstance(e.nextElement());
    exponent = ASN1Integer.getInstance(e.nextElement());
  }
  









  public MonetaryLimit(String currency, int amount, int exponent)
  {
    this.currency = new DERPrintableString(currency, true);
    this.amount = new ASN1Integer(amount);
    this.exponent = new ASN1Integer(exponent);
  }
  
  public String getCurrency()
  {
    return currency.getString();
  }
  
  public BigInteger getAmount()
  {
    return amount.getValue();
  }
  
  public BigInteger getExponent()
  {
    return exponent.getValue();
  }
  















  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector seq = new ASN1EncodableVector();
    seq.add(currency);
    seq.add(amount);
    seq.add(exponent);
    
    return new DERSequence(seq);
  }
}

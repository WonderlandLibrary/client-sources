package org.spongycastle.asn1.isismtt.x509;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1Boolean;
import org.spongycastle.asn1.ASN1Choice;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1GeneralizedTime;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERPrintableString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;




















public class DeclarationOfMajority
  extends ASN1Object
  implements ASN1Choice
{
  public static final int notYoungerThan = 0;
  public static final int fullAgeAtCountry = 1;
  public static final int dateOfBirth = 2;
  private ASN1TaggedObject declaration;
  
  public DeclarationOfMajority(int notYoungerThan)
  {
    declaration = new DERTaggedObject(false, 0, new ASN1Integer(notYoungerThan));
  }
  
  public DeclarationOfMajority(boolean fullAge, String country)
  {
    if (country.length() > 2)
    {
      throw new IllegalArgumentException("country can only be 2 characters");
    }
    
    if (fullAge)
    {
      declaration = new DERTaggedObject(false, 1, new DERSequence(new DERPrintableString(country, true)));
    }
    else
    {
      ASN1EncodableVector v = new ASN1EncodableVector();
      
      v.add(ASN1Boolean.FALSE);
      v.add(new DERPrintableString(country, true));
      
      declaration = new DERTaggedObject(false, 1, new DERSequence(v));
    }
  }
  
  public DeclarationOfMajority(ASN1GeneralizedTime dateOfBirth)
  {
    declaration = new DERTaggedObject(false, 2, dateOfBirth);
  }
  
  public static DeclarationOfMajority getInstance(Object obj)
  {
    if ((obj == null) || ((obj instanceof DeclarationOfMajority)))
    {
      return (DeclarationOfMajority)obj;
    }
    
    if ((obj instanceof ASN1TaggedObject))
    {
      return new DeclarationOfMajority((ASN1TaggedObject)obj);
    }
    

    throw new IllegalArgumentException("illegal object in getInstance: " + obj.getClass().getName());
  }
  
  private DeclarationOfMajority(ASN1TaggedObject o)
  {
    if (o.getTagNo() > 2)
    {
      throw new IllegalArgumentException("Bad tag number: " + o.getTagNo());
    }
    declaration = o;
  }
  



















  public ASN1Primitive toASN1Primitive()
  {
    return declaration;
  }
  
  public int getType()
  {
    return declaration.getTagNo();
  }
  



  public int notYoungerThan()
  {
    if (declaration.getTagNo() != 0)
    {
      return -1;
    }
    
    return ASN1Integer.getInstance(declaration, false).getValue().intValue();
  }
  
  public ASN1Sequence fullAgeAtCountry()
  {
    if (declaration.getTagNo() != 1)
    {
      return null;
    }
    
    return ASN1Sequence.getInstance(declaration, false);
  }
  
  public ASN1GeneralizedTime getDateOfBirth()
  {
    if (declaration.getTagNo() != 2)
    {
      return null;
    }
    
    return ASN1GeneralizedTime.getInstance(declaration, false);
  }
}

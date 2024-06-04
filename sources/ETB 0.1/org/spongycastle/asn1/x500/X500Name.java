package org.spongycastle.asn1.x500;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1Choice;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x500.style.BCStyle;

















public class X500Name
  extends ASN1Object
  implements ASN1Choice
{
  private static X500NameStyle defaultStyle = BCStyle.INSTANCE;
  
  private boolean isHashCodeCalculated;
  
  private int hashCodeValue;
  private X500NameStyle style;
  private RDN[] rdns;
  
  /**
   * @deprecated
   */
  public X500Name(X500NameStyle style, X500Name name)
  {
    rdns = rdns;
    this.style = style;
  }
  










  public static X500Name getInstance(ASN1TaggedObject obj, boolean explicit)
  {
    return getInstance(ASN1Sequence.getInstance(obj, true));
  }
  

  public static X500Name getInstance(Object obj)
  {
    if ((obj instanceof X500Name))
    {
      return (X500Name)obj;
    }
    if (obj != null)
    {
      return new X500Name(ASN1Sequence.getInstance(obj));
    }
    
    return null;
  }
  


  public static X500Name getInstance(X500NameStyle style, Object obj)
  {
    if ((obj instanceof X500Name))
    {
      return new X500Name(style, (X500Name)obj);
    }
    if (obj != null)
    {
      return new X500Name(style, ASN1Sequence.getInstance(obj));
    }
    
    return null;
  }
  






  private X500Name(ASN1Sequence seq)
  {
    this(defaultStyle, seq);
  }
  


  private X500Name(X500NameStyle style, ASN1Sequence seq)
  {
    this.style = style;
    rdns = new RDN[seq.size()];
    
    int index = 0;
    
    for (Enumeration e = seq.getObjects(); e.hasMoreElements();)
    {
      rdns[(index++)] = RDN.getInstance(e.nextElement());
    }
  }
  

  public X500Name(RDN[] rDNs)
  {
    this(defaultStyle, rDNs);
  }
  


  public X500Name(X500NameStyle style, RDN[] rDNs)
  {
    rdns = rDNs;
    this.style = style;
  }
  

  public X500Name(String dirName)
  {
    this(defaultStyle, dirName);
  }
  


  public X500Name(X500NameStyle style, String dirName)
  {
    this(style.fromString(dirName));
    
    this.style = style;
  }
  





  public RDN[] getRDNs()
  {
    RDN[] tmp = new RDN[rdns.length];
    
    System.arraycopy(rdns, 0, tmp, 0, tmp.length);
    
    return tmp;
  }
  





  public ASN1ObjectIdentifier[] getAttributeTypes()
  {
    int count = 0;
    
    for (int i = 0; i != rdns.length; i++)
    {
      RDN rdn = rdns[i];
      
      count += rdn.size();
    }
    
    ASN1ObjectIdentifier[] res = new ASN1ObjectIdentifier[count];
    
    count = 0;
    
    for (int i = 0; i != rdns.length; i++)
    {
      RDN rdn = rdns[i];
      
      if (rdn.isMultiValued())
      {
        AttributeTypeAndValue[] attr = rdn.getTypesAndValues();
        for (int j = 0; j != attr.length; j++)
        {
          res[(count++)] = attr[j].getType();
        }
      }
      else if (rdn.size() != 0)
      {
        res[(count++)] = rdn.getFirst().getType();
      }
    }
    
    return res;
  }
  






  public RDN[] getRDNs(ASN1ObjectIdentifier attributeType)
  {
    RDN[] res = new RDN[rdns.length];
    int count = 0;
    
    for (int i = 0; i != rdns.length; i++)
    {
      RDN rdn = rdns[i];
      
      if (rdn.isMultiValued())
      {
        AttributeTypeAndValue[] attr = rdn.getTypesAndValues();
        for (int j = 0; j != attr.length; j++)
        {
          if (attr[j].getType().equals(attributeType))
          {
            res[(count++)] = rdn;
            break;
          }
          
        }
        
      }
      else if (rdn.getFirst().getType().equals(attributeType))
      {
        res[(count++)] = rdn;
      }
    }
    

    RDN[] tmp = new RDN[count];
    
    System.arraycopy(res, 0, tmp, 0, tmp.length);
    
    return tmp;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    return new DERSequence(rdns);
  }
  
  public int hashCode()
  {
    if (isHashCodeCalculated)
    {
      return hashCodeValue;
    }
    
    isHashCodeCalculated = true;
    
    hashCodeValue = style.calculateHashCode(this);
    
    return hashCodeValue;
  }
  



  public boolean equals(Object obj)
  {
    if (obj == this)
    {
      return true;
    }
    
    if ((!(obj instanceof X500Name)) && (!(obj instanceof ASN1Sequence)))
    {
      return false;
    }
    
    ASN1Primitive derO = ((ASN1Encodable)obj).toASN1Primitive();
    
    if (toASN1Primitive().equals(derO))
    {
      return true;
    }
    
    try
    {
      return style.areEqual(this, new X500Name(ASN1Sequence.getInstance(((ASN1Encodable)obj).toASN1Primitive())));
    }
    catch (Exception e) {}
    
    return false;
  }
  

  public String toString()
  {
    return style.toString(this);
  }
  





  public static void setDefaultStyle(X500NameStyle style)
  {
    if (style == null)
    {
      throw new NullPointerException("cannot set style to null");
    }
    
    defaultStyle = style;
  }
  





  public static X500NameStyle getDefaultStyle()
  {
    return defaultStyle;
  }
}

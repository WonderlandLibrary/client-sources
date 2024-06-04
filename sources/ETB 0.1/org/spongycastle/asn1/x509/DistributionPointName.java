package org.spongycastle.asn1.x509;

import org.spongycastle.asn1.ASN1Choice;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.util.Strings;













public class DistributionPointName
  extends ASN1Object
  implements ASN1Choice
{
  ASN1Encodable name;
  int type;
  public static final int FULL_NAME = 0;
  public static final int NAME_RELATIVE_TO_CRL_ISSUER = 1;
  
  public static DistributionPointName getInstance(ASN1TaggedObject obj, boolean explicit)
  {
    return getInstance(ASN1TaggedObject.getInstance(obj, true));
  }
  

  public static DistributionPointName getInstance(Object obj)
  {
    if ((obj == null) || ((obj instanceof DistributionPointName)))
    {
      return (DistributionPointName)obj;
    }
    if ((obj instanceof ASN1TaggedObject))
    {
      return new DistributionPointName((ASN1TaggedObject)obj);
    }
    
    throw new IllegalArgumentException("unknown object in factory: " + obj.getClass().getName());
  }
  


  public DistributionPointName(int type, ASN1Encodable name)
  {
    this.type = type;
    this.name = name;
  }
  

  public DistributionPointName(GeneralNames name)
  {
    this(0, name);
  }
  





  public int getType()
  {
    return type;
  }
  





  public ASN1Encodable getName()
  {
    return name;
  }
  

  public DistributionPointName(ASN1TaggedObject obj)
  {
    type = obj.getTagNo();
    
    if (type == 0)
    {
      name = GeneralNames.getInstance(obj, false);
    }
    else
    {
      name = ASN1Set.getInstance(obj, false);
    }
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    return new DERTaggedObject(false, type, name);
  }
  
  public String toString()
  {
    String sep = Strings.lineSeparator();
    StringBuffer buf = new StringBuffer();
    buf.append("DistributionPointName: [");
    buf.append(sep);
    if (type == 0)
    {
      appendObject(buf, sep, "fullName", name.toString());
    }
    else
    {
      appendObject(buf, sep, "nameRelativeToCRLIssuer", name.toString());
    }
    buf.append("]");
    buf.append(sep);
    return buf.toString();
  }
  
  private void appendObject(StringBuffer buf, String sep, String name, String value)
  {
    String indent = "    ";
    
    buf.append(indent);
    buf.append(name);
    buf.append(":");
    buf.append(sep);
    buf.append(indent);
    buf.append(indent);
    buf.append(value);
    buf.append(sep);
  }
}

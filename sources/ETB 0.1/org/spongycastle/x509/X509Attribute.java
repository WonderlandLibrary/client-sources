package org.spongycastle.x509;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.DERSet;
import org.spongycastle.asn1.x509.Attribute;






/**
 * @deprecated
 */
public class X509Attribute
  extends ASN1Object
{
  Attribute attr;
  
  X509Attribute(ASN1Encodable at)
  {
    attr = Attribute.getInstance(at);
  }
  









  public X509Attribute(String oid, ASN1Encodable value)
  {
    attr = new Attribute(new ASN1ObjectIdentifier(oid), new DERSet(value));
  }
  









  public X509Attribute(String oid, ASN1EncodableVector value)
  {
    attr = new Attribute(new ASN1ObjectIdentifier(oid), new DERSet(value));
  }
  
  public String getOID()
  {
    return attr.getAttrType().getId();
  }
  
  public ASN1Encodable[] getValues()
  {
    ASN1Set s = attr.getAttrValues();
    ASN1Encodable[] values = new ASN1Encodable[s.size()];
    
    for (int i = 0; i != s.size(); i++)
    {
      values[i] = s.getObjectAt(i);
    }
    
    return values;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    return attr.toASN1Primitive();
  }
}

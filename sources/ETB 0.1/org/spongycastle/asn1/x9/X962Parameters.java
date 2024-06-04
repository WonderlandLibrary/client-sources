package org.spongycastle.asn1.x9;

import org.spongycastle.asn1.ASN1Choice;
import org.spongycastle.asn1.ASN1Null;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1TaggedObject;






public class X962Parameters
  extends ASN1Object
  implements ASN1Choice
{
  private ASN1Primitive params = null;
  

  public static X962Parameters getInstance(Object obj)
  {
    if ((obj == null) || ((obj instanceof X962Parameters)))
    {
      return (X962Parameters)obj;
    }
    
    if ((obj instanceof ASN1Primitive))
    {
      return new X962Parameters((ASN1Primitive)obj);
    }
    
    if ((obj instanceof byte[]))
    {
      try
      {
        return new X962Parameters(ASN1Primitive.fromByteArray((byte[])obj));
      }
      catch (Exception e)
      {
        throw new IllegalArgumentException("unable to parse encoded data: " + e.getMessage());
      }
    }
    
    throw new IllegalArgumentException("unknown object in getInstance()");
  }
  


  public static X962Parameters getInstance(ASN1TaggedObject obj, boolean explicit)
  {
    return getInstance(obj.getObject());
  }
  

  public X962Parameters(X9ECParameters ecParameters)
  {
    params = ecParameters.toASN1Primitive();
  }
  

  public X962Parameters(ASN1ObjectIdentifier namedCurve)
  {
    params = namedCurve;
  }
  

  public X962Parameters(ASN1Null obj)
  {
    params = obj;
  }
  

  /**
   * @deprecated
   */
  public X962Parameters(ASN1Primitive obj)
  {
    params = obj;
  }
  
  public boolean isNamedCurve()
  {
    return params instanceof ASN1ObjectIdentifier;
  }
  
  public boolean isImplicitlyCA()
  {
    return params instanceof ASN1Null;
  }
  
  public ASN1Primitive getParameters()
  {
    return params;
  }
  










  public ASN1Primitive toASN1Primitive()
  {
    return params;
  }
}

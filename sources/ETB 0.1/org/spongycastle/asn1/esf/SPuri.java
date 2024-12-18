package org.spongycastle.asn1.esf;

import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERIA5String;


public class SPuri
{
  private DERIA5String uri;
  
  public static SPuri getInstance(Object obj)
  {
    if ((obj instanceof SPuri))
    {
      return (SPuri)obj;
    }
    if ((obj instanceof DERIA5String))
    {
      return new SPuri(DERIA5String.getInstance(obj));
    }
    
    return null;
  }
  

  public SPuri(DERIA5String uri)
  {
    this.uri = uri;
  }
  
  public DERIA5String getUri()
  {
    return uri;
  }
  





  public ASN1Primitive toASN1Primitive()
  {
    return uri.toASN1Primitive();
  }
}

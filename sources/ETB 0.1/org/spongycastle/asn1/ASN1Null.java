package org.spongycastle.asn1;

import java.io.IOException;

















public abstract class ASN1Null
  extends ASN1Primitive
{
  public ASN1Null() {}
  
  public static ASN1Null getInstance(Object o)
  {
    if ((o instanceof ASN1Null))
    {
      return (ASN1Null)o;
    }
    
    if (o != null)
    {
      try
      {
        return getInstance(ASN1Primitive.fromByteArray((byte[])o));
      }
      catch (IOException e)
      {
        throw new IllegalArgumentException("failed to construct NULL from byte[]: " + e.getMessage());
      }
      catch (ClassCastException e)
      {
        throw new IllegalArgumentException("unknown object in getInstance(): " + o.getClass().getName());
      }
    }
    
    return null;
  }
  
  public int hashCode()
  {
    return -1;
  }
  

  boolean asn1Equals(ASN1Primitive o)
  {
    if (!(o instanceof ASN1Null))
    {
      return false;
    }
    
    return true;
  }
  
  abstract void encode(ASN1OutputStream paramASN1OutputStream)
    throws IOException;
  
  public String toString()
  {
    return "NULL";
  }
}

package org.spongycastle.asn1;

import java.io.IOException;
import java.math.BigInteger;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Properties;













public class ASN1Integer
  extends ASN1Primitive
{
  private final byte[] bytes;
  
  public static ASN1Integer getInstance(Object obj)
  {
    if ((obj == null) || ((obj instanceof ASN1Integer)))
    {
      return (ASN1Integer)obj;
    }
    
    if ((obj instanceof byte[]))
    {
      try
      {
        return (ASN1Integer)fromByteArray((byte[])obj);
      }
      catch (Exception e)
      {
        throw new IllegalArgumentException("encoding error in getInstance: " + e.toString());
      }
    }
    
    throw new IllegalArgumentException("illegal object in getInstance: " + obj.getClass().getName());
  }
  












  public static ASN1Integer getInstance(ASN1TaggedObject obj, boolean explicit)
  {
    ASN1Primitive o = obj.getObject();
    
    if ((explicit) || ((o instanceof ASN1Integer)))
    {
      return getInstance(o);
    }
    

    return new ASN1Integer(ASN1OctetString.getInstance(obj.getObject()).getOctets());
  }
  







  public ASN1Integer(long value)
  {
    bytes = BigInteger.valueOf(value).toByteArray();
  }
  






  public ASN1Integer(BigInteger value)
  {
    bytes = value.toByteArray();
  }
  























  public ASN1Integer(byte[] bytes)
  {
    this(bytes, true);
  }
  

  ASN1Integer(byte[] bytes, boolean clone)
  {
    if (!Properties.isOverrideSet("org.spongycastle.asn1.allow_unsafe_integer"))
    {
      if (isMalformed(bytes))
      {
        throw new IllegalArgumentException("malformed integer");
      }
    }
    this.bytes = (clone ? Arrays.clone(bytes) : bytes);
  }
  






  static boolean isMalformed(byte[] bytes)
  {
    if (bytes.length > 1)
    {
      if ((bytes[0] == 0) && ((bytes[1] & 0x80) == 0))
      {
        return true;
      }
      if ((bytes[0] == -1) && ((bytes[1] & 0x80) != 0))
      {
        return true;
      }
    }
    
    return false;
  }
  
  public BigInteger getValue()
  {
    return new BigInteger(bytes);
  }
  






  public BigInteger getPositiveValue()
  {
    return new BigInteger(1, bytes);
  }
  
  boolean isConstructed()
  {
    return false;
  }
  
  int encodedLength()
  {
    return 1 + StreamUtil.calculateBodyLength(bytes.length) + bytes.length;
  }
  

  void encode(ASN1OutputStream out)
    throws IOException
  {
    out.writeEncoded(2, bytes);
  }
  
  public int hashCode()
  {
    int value = 0;
    
    for (int i = 0; i != bytes.length; i++)
    {
      value ^= (bytes[i] & 0xFF) << i % 4;
    }
    
    return value;
  }
  

  boolean asn1Equals(ASN1Primitive o)
  {
    if (!(o instanceof ASN1Integer))
    {
      return false;
    }
    
    ASN1Integer other = (ASN1Integer)o;
    
    return Arrays.areEqual(bytes, bytes);
  }
  
  public String toString()
  {
    return getValue().toString();
  }
}

package org.spongycastle.asn1;

import java.io.IOException;
import java.math.BigInteger;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Properties;













public class ASN1Enumerated
  extends ASN1Primitive
{
  private final byte[] bytes;
  
  public static ASN1Enumerated getInstance(Object obj)
  {
    if ((obj == null) || ((obj instanceof ASN1Enumerated)))
    {
      return (ASN1Enumerated)obj;
    }
    
    if ((obj instanceof byte[]))
    {
      try
      {
        return (ASN1Enumerated)fromByteArray((byte[])obj);
      }
      catch (Exception e)
      {
        throw new IllegalArgumentException("encoding error in getInstance: " + e.toString());
      }
    }
    
    throw new IllegalArgumentException("illegal object in getInstance: " + obj.getClass().getName());
  }
  












  public static ASN1Enumerated getInstance(ASN1TaggedObject obj, boolean explicit)
  {
    ASN1Primitive o = obj.getObject();
    
    if ((explicit) || ((o instanceof ASN1Enumerated)))
    {
      return getInstance(o);
    }
    

    return fromOctetString(((ASN1OctetString)o).getOctets());
  }
  







  public ASN1Enumerated(int value)
  {
    bytes = BigInteger.valueOf(value).toByteArray();
  }
  






  public ASN1Enumerated(BigInteger value)
  {
    bytes = value.toByteArray();
  }
  






  public ASN1Enumerated(byte[] bytes)
  {
    if (!Properties.isOverrideSet("org.spongycastle.asn1.allow_unsafe_integer"))
    {
      if (ASN1Integer.isMalformed(bytes))
      {
        throw new IllegalArgumentException("malformed enumerated");
      }
    }
    this.bytes = Arrays.clone(bytes);
  }
  
  public BigInteger getValue()
  {
    return new BigInteger(bytes);
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
    out.writeEncoded(10, bytes);
  }
  

  boolean asn1Equals(ASN1Primitive o)
  {
    if (!(o instanceof ASN1Enumerated))
    {
      return false;
    }
    
    ASN1Enumerated other = (ASN1Enumerated)o;
    
    return Arrays.areEqual(bytes, bytes);
  }
  
  public int hashCode()
  {
    return Arrays.hashCode(bytes);
  }
  
  private static ASN1Enumerated[] cache = new ASN1Enumerated[12];
  
  static ASN1Enumerated fromOctetString(byte[] enc)
  {
    if (enc.length > 1)
    {
      return new ASN1Enumerated(enc);
    }
    
    if (enc.length == 0)
    {
      throw new IllegalArgumentException("ENUMERATED has zero length");
    }
    int value = enc[0] & 0xFF;
    
    if (value >= cache.length)
    {
      return new ASN1Enumerated(Arrays.clone(enc));
    }
    
    ASN1Enumerated possibleMatch = cache[value];
    
    if (possibleMatch == null)
    {
      possibleMatch = cache[value] =  = new ASN1Enumerated(Arrays.clone(enc));
    }
    
    return possibleMatch;
  }
}

package org.spongycastle.asn1;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Strings;
import org.spongycastle.util.encoders.Hex;






































































































public abstract class ASN1OctetString
  extends ASN1Primitive
  implements ASN1OctetStringParser
{
  byte[] string;
  
  public static ASN1OctetString getInstance(ASN1TaggedObject obj, boolean explicit)
  {
    ASN1Primitive o = obj.getObject();
    
    if ((explicit) || ((o instanceof ASN1OctetString)))
    {
      return getInstance(o);
    }
    

    return BEROctetString.fromSequence(ASN1Sequence.getInstance(o));
  }
  








  public static ASN1OctetString getInstance(Object obj)
  {
    if ((obj == null) || ((obj instanceof ASN1OctetString)))
    {
      return (ASN1OctetString)obj;
    }
    if ((obj instanceof byte[]))
    {
      try
      {
        return getInstance(ASN1Primitive.fromByteArray((byte[])obj));
      }
      catch (IOException e)
      {
        throw new IllegalArgumentException("failed to construct OCTET STRING from byte[]: " + e.getMessage());
      }
    }
    if ((obj instanceof ASN1Encodable))
    {
      ASN1Primitive primitive = ((ASN1Encodable)obj).toASN1Primitive();
      
      if ((primitive instanceof ASN1OctetString))
      {
        return (ASN1OctetString)primitive;
      }
    }
    
    throw new IllegalArgumentException("illegal object in getInstance: " + obj.getClass().getName());
  }
  






  public ASN1OctetString(byte[] string)
  {
    if (string == null)
    {
      throw new NullPointerException("string cannot be null");
    }
    this.string = string;
  }
  





  public InputStream getOctetStream()
  {
    return new ByteArrayInputStream(string);
  }
  





  public ASN1OctetStringParser parser()
  {
    return this;
  }
  





  public byte[] getOctets()
  {
    return string;
  }
  
  public int hashCode()
  {
    return Arrays.hashCode(getOctets());
  }
  

  boolean asn1Equals(ASN1Primitive o)
  {
    if (!(o instanceof ASN1OctetString))
    {
      return false;
    }
    
    ASN1OctetString other = (ASN1OctetString)o;
    
    return Arrays.areEqual(string, string);
  }
  
  public ASN1Primitive getLoadedObject()
  {
    return toASN1Primitive();
  }
  
  ASN1Primitive toDERObject()
  {
    return new DEROctetString(string);
  }
  
  ASN1Primitive toDLObject()
  {
    return new DEROctetString(string);
  }
  
  abstract void encode(ASN1OutputStream paramASN1OutputStream)
    throws IOException;
  
  public String toString()
  {
    return "#" + Strings.fromByteArray(Hex.encode(string));
  }
}

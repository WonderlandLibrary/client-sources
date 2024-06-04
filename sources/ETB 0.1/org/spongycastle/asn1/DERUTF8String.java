package org.spongycastle.asn1;

import java.io.IOException;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Strings;













public class DERUTF8String
  extends ASN1Primitive
  implements ASN1String
{
  private final byte[] string;
  
  public static DERUTF8String getInstance(Object obj)
  {
    if ((obj == null) || ((obj instanceof DERUTF8String)))
    {
      return (DERUTF8String)obj;
    }
    
    if ((obj instanceof byte[]))
    {
      try
      {
        return (DERUTF8String)fromByteArray((byte[])obj);
      }
      catch (Exception e)
      {
        throw new IllegalArgumentException("encoding error in getInstance: " + e.toString());
      }
    }
    

    throw new IllegalArgumentException("illegal object in getInstance: " + obj.getClass().getName());
  }
  














  public static DERUTF8String getInstance(ASN1TaggedObject obj, boolean explicit)
  {
    ASN1Primitive o = obj.getObject();
    
    if ((explicit) || ((o instanceof DERUTF8String)))
    {
      return getInstance(o);
    }
    

    return new DERUTF8String(ASN1OctetString.getInstance(o).getOctets());
  }
  




  DERUTF8String(byte[] string)
  {
    this.string = string;
  }
  





  public DERUTF8String(String string)
  {
    this.string = Strings.toUTF8ByteArray(string);
  }
  
  public String getString()
  {
    return Strings.fromUTF8ByteArray(string);
  }
  
  public String toString()
  {
    return getString();
  }
  
  public int hashCode()
  {
    return Arrays.hashCode(string);
  }
  
  boolean asn1Equals(ASN1Primitive o)
  {
    if (!(o instanceof DERUTF8String))
    {
      return false;
    }
    
    DERUTF8String s = (DERUTF8String)o;
    
    return Arrays.areEqual(string, string);
  }
  
  boolean isConstructed()
  {
    return false;
  }
  
  int encodedLength()
    throws IOException
  {
    return 1 + StreamUtil.calculateBodyLength(string.length) + string.length;
  }
  
  void encode(ASN1OutputStream out)
    throws IOException
  {
    out.writeEncoded(12, string);
  }
}

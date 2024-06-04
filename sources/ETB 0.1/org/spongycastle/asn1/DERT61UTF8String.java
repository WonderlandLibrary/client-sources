package org.spongycastle.asn1;

import java.io.IOException;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Strings;












/**
 * @deprecated
 */
public class DERT61UTF8String
  extends ASN1Primitive
  implements ASN1String
{
  private byte[] string;
  
  public static DERT61UTF8String getInstance(Object obj)
  {
    if ((obj instanceof DERT61String))
    {
      return new DERT61UTF8String(((DERT61String)obj).getOctets());
    }
    
    if ((obj == null) || ((obj instanceof DERT61UTF8String)))
    {
      return (DERT61UTF8String)obj;
    }
    
    if ((obj instanceof byte[]))
    {
      try
      {
        return new DERT61UTF8String(((DERT61String)fromByteArray((byte[])obj)).getOctets());
      }
      catch (Exception e)
      {
        throw new IllegalArgumentException("encoding error in getInstance: " + e.toString());
      }
    }
    
    throw new IllegalArgumentException("illegal object in getInstance: " + obj.getClass().getName());
  }
  












  public static DERT61UTF8String getInstance(ASN1TaggedObject obj, boolean explicit)
  {
    ASN1Primitive o = obj.getObject();
    
    if ((explicit) || ((o instanceof DERT61String)) || ((o instanceof DERT61UTF8String)))
    {
      return getInstance(o);
    }
    

    return new DERT61UTF8String(ASN1OctetString.getInstance(o).getOctets());
  }
  





  public DERT61UTF8String(byte[] string)
  {
    this.string = string;
  }
  




  public DERT61UTF8String(String string)
  {
    this(Strings.toUTF8ByteArray(string));
  }
  





  public String getString()
  {
    return Strings.fromUTF8ByteArray(string);
  }
  
  public String toString()
  {
    return getString();
  }
  
  boolean isConstructed()
  {
    return false;
  }
  
  int encodedLength()
  {
    return 1 + StreamUtil.calculateBodyLength(string.length) + string.length;
  }
  

  void encode(ASN1OutputStream out)
    throws IOException
  {
    out.writeEncoded(20, string);
  }
  





  public byte[] getOctets()
  {
    return Arrays.clone(string);
  }
  

  boolean asn1Equals(ASN1Primitive o)
  {
    if (!(o instanceof DERT61UTF8String))
    {
      return false;
    }
    
    return Arrays.areEqual(string, string);
  }
  
  public int hashCode()
  {
    return Arrays.hashCode(string);
  }
}

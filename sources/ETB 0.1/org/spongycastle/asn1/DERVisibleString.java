package org.spongycastle.asn1;

import java.io.IOException;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Strings;
















public class DERVisibleString
  extends ASN1Primitive
  implements ASN1String
{
  private final byte[] string;
  
  public static DERVisibleString getInstance(Object obj)
  {
    if ((obj == null) || ((obj instanceof DERVisibleString)))
    {
      return (DERVisibleString)obj;
    }
    
    if ((obj instanceof byte[]))
    {
      try
      {
        return (DERVisibleString)fromByteArray((byte[])obj);
      }
      catch (Exception e)
      {
        throw new IllegalArgumentException("encoding error in getInstance: " + e.toString());
      }
    }
    
    throw new IllegalArgumentException("illegal object in getInstance: " + obj.getClass().getName());
  }
  












  public static DERVisibleString getInstance(ASN1TaggedObject obj, boolean explicit)
  {
    ASN1Primitive o = obj.getObject();
    
    if ((explicit) || ((o instanceof DERVisibleString)))
    {
      return getInstance(o);
    }
    

    return new DERVisibleString(ASN1OctetString.getInstance(o).getOctets());
  }
  





  DERVisibleString(byte[] string)
  {
    this.string = string;
  }
  






  public DERVisibleString(String string)
  {
    this.string = Strings.toByteArray(string);
  }
  
  public String getString()
  {
    return Strings.fromByteArray(string);
  }
  
  public String toString()
  {
    return getString();
  }
  
  public byte[] getOctets()
  {
    return Arrays.clone(string);
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
    out.writeEncoded(26, string);
  }
  

  boolean asn1Equals(ASN1Primitive o)
  {
    if (!(o instanceof DERVisibleString))
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

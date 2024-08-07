package org.spongycastle.asn1;

import java.io.IOException;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Strings;










public class DERVideotexString
  extends ASN1Primitive
  implements ASN1String
{
  private final byte[] string;
  
  public static DERVideotexString getInstance(Object obj)
  {
    if ((obj == null) || ((obj instanceof DERVideotexString)))
    {
      return (DERVideotexString)obj;
    }
    
    if ((obj instanceof byte[]))
    {
      try
      {
        return (DERVideotexString)fromByteArray((byte[])obj);
      }
      catch (Exception e)
      {
        throw new IllegalArgumentException("encoding error in getInstance: " + e.toString());
      }
    }
    
    throw new IllegalArgumentException("illegal object in getInstance: " + obj.getClass().getName());
  }
  












  public static DERVideotexString getInstance(ASN1TaggedObject obj, boolean explicit)
  {
    ASN1Primitive o = obj.getObject();
    
    if ((explicit) || ((o instanceof DERVideotexString)))
    {
      return getInstance(o);
    }
    

    return new DERVideotexString(((ASN1OctetString)o).getOctets());
  }
  






  public DERVideotexString(byte[] string)
  {
    this.string = Arrays.clone(string);
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
    out.writeEncoded(21, string);
  }
  
  public int hashCode()
  {
    return Arrays.hashCode(string);
  }
  

  boolean asn1Equals(ASN1Primitive o)
  {
    if (!(o instanceof DERVideotexString))
    {
      return false;
    }
    
    DERVideotexString s = (DERVideotexString)o;
    
    return Arrays.areEqual(string, string);
  }
  
  public String getString()
  {
    return Strings.fromByteArray(string);
  }
}

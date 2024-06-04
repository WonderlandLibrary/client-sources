package org.spongycastle.asn1;

import java.io.IOException;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Strings;
















public class DERIA5String
  extends ASN1Primitive
  implements ASN1String
{
  private final byte[] string;
  
  public static DERIA5String getInstance(Object obj)
  {
    if ((obj == null) || ((obj instanceof DERIA5String)))
    {
      return (DERIA5String)obj;
    }
    
    if ((obj instanceof byte[]))
    {
      try
      {
        return (DERIA5String)fromByteArray((byte[])obj);
      }
      catch (Exception e)
      {
        throw new IllegalArgumentException("encoding error in getInstance: " + e.toString());
      }
    }
    
    throw new IllegalArgumentException("illegal object in getInstance: " + obj.getClass().getName());
  }
  












  public static DERIA5String getInstance(ASN1TaggedObject obj, boolean explicit)
  {
    ASN1Primitive o = obj.getObject();
    
    if ((explicit) || ((o instanceof DERIA5String)))
    {
      return getInstance(o);
    }
    

    return new DERIA5String(((ASN1OctetString)o).getOctets());
  }
  






  DERIA5String(byte[] string)
  {
    this.string = string;
  }
  





  public DERIA5String(String string)
  {
    this(string, false);
  }
  










  public DERIA5String(String string, boolean validate)
  {
    if (string == null)
    {
      throw new NullPointerException("string cannot be null");
    }
    if ((validate) && (!isIA5String(string)))
    {
      throw new IllegalArgumentException("string contains illegal characters");
    }
    
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
    out.writeEncoded(22, string);
  }
  
  public int hashCode()
  {
    return Arrays.hashCode(string);
  }
  

  boolean asn1Equals(ASN1Primitive o)
  {
    if (!(o instanceof DERIA5String))
    {
      return false;
    }
    
    DERIA5String s = (DERIA5String)o;
    
    return Arrays.areEqual(string, string);
  }
  








  public static boolean isIA5String(String str)
  {
    for (int i = str.length() - 1; i >= 0; i--)
    {
      char ch = str.charAt(i);
      
      if (ch > '')
      {
        return false;
      }
    }
    
    return true;
  }
}

package org.spongycastle.asn1;

import java.io.IOException;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Strings;




















public class DERNumericString
  extends ASN1Primitive
  implements ASN1String
{
  private final byte[] string;
  
  public static DERNumericString getInstance(Object obj)
  {
    if ((obj == null) || ((obj instanceof DERNumericString)))
    {
      return (DERNumericString)obj;
    }
    
    if ((obj instanceof byte[]))
    {
      try
      {
        return (DERNumericString)fromByteArray((byte[])obj);
      }
      catch (Exception e)
      {
        throw new IllegalArgumentException("encoding error in getInstance: " + e.toString());
      }
    }
    
    throw new IllegalArgumentException("illegal object in getInstance: " + obj.getClass().getName());
  }
  












  public static DERNumericString getInstance(ASN1TaggedObject obj, boolean explicit)
  {
    ASN1Primitive o = obj.getObject();
    
    if ((explicit) || ((o instanceof DERNumericString)))
    {
      return getInstance(o);
    }
    

    return new DERNumericString(ASN1OctetString.getInstance(o).getOctets());
  }
  





  DERNumericString(byte[] string)
  {
    this.string = string;
  }
  




  public DERNumericString(String string)
  {
    this(string, false);
  }
  










  public DERNumericString(String string, boolean validate)
  {
    if ((validate) && (!isNumericString(string)))
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
    out.writeEncoded(18, string);
  }
  
  public int hashCode()
  {
    return Arrays.hashCode(string);
  }
  

  boolean asn1Equals(ASN1Primitive o)
  {
    if (!(o instanceof DERNumericString))
    {
      return false;
    }
    
    DERNumericString s = (DERNumericString)o;
    
    return Arrays.areEqual(string, string);
  }
  







  public static boolean isNumericString(String str)
  {
    for (int i = str.length() - 1; i >= 0; i--)
    {
      char ch = str.charAt(i);
      
      if (ch > '')
      {
        return false;
      }
      
      if ((('0' > ch) || (ch > '9')) && (ch != ' '))
      {



        return false;
      }
    }
    return true;
  }
}

package org.spongycastle.asn1;

import java.io.IOException;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Strings;




































public class DERPrintableString
  extends ASN1Primitive
  implements ASN1String
{
  private final byte[] string;
  
  public static DERPrintableString getInstance(Object obj)
  {
    if ((obj == null) || ((obj instanceof DERPrintableString)))
    {
      return (DERPrintableString)obj;
    }
    
    if ((obj instanceof byte[]))
    {
      try
      {
        return (DERPrintableString)fromByteArray((byte[])obj);
      }
      catch (Exception e)
      {
        throw new IllegalArgumentException("encoding error in getInstance: " + e.toString());
      }
    }
    
    throw new IllegalArgumentException("illegal object in getInstance: " + obj.getClass().getName());
  }
  












  public static DERPrintableString getInstance(ASN1TaggedObject obj, boolean explicit)
  {
    ASN1Primitive o = obj.getObject();
    
    if ((explicit) || ((o instanceof DERPrintableString)))
    {
      return getInstance(o);
    }
    

    return new DERPrintableString(ASN1OctetString.getInstance(o).getOctets());
  }
  





  DERPrintableString(byte[] string)
  {
    this.string = string;
  }
  




  public DERPrintableString(String string)
  {
    this(string, false);
  }
  










  public DERPrintableString(String string, boolean validate)
  {
    if ((validate) && (!isPrintableString(string)))
    {
      throw new IllegalArgumentException("string contains illegal characters");
    }
    
    this.string = Strings.toByteArray(string);
  }
  
  public String getString()
  {
    return Strings.fromByteArray(string);
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
    out.writeEncoded(19, string);
  }
  
  public int hashCode()
  {
    return Arrays.hashCode(string);
  }
  

  boolean asn1Equals(ASN1Primitive o)
  {
    if (!(o instanceof DERPrintableString))
    {
      return false;
    }
    
    DERPrintableString s = (DERPrintableString)o;
    
    return Arrays.areEqual(string, string);
  }
  
  public String toString()
  {
    return getString();
  }
  







  public static boolean isPrintableString(String str)
  {
    for (int i = str.length() - 1; i >= 0; i--)
    {
      char ch = str.charAt(i);
      
      if (ch > '')
      {
        return false;
      }
      
      if (('a' > ch) || (ch > 'z'))
      {



        if (('A' > ch) || (ch > 'Z'))
        {



          if (('0' > ch) || (ch > '9'))
          {



            switch (ch) {
            case ' ': case '\'': case '(': case ')': 
            case '+': case ',': case '-': case '.': 
            case '/': case ':': case '=': case '?': 
              break;
            case '!': case '"': 
            case '#': case '$': 
            case '%': case '&': 
            case '*': case '0': 
            case '1': case '2': 
            case '3': case '4': 
            case '5': case '6': 
            case '7': case '8': 
            case '9': case ';': 
            case '<': 
            case '>': 
            default: 
              return false;
            } } } }
    }
    return true;
  }
}

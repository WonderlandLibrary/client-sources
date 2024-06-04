package org.spongycastle.asn1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.spongycastle.util.Arrays;






public class DERUniversalString
  extends ASN1Primitive
  implements ASN1String
{
  private static final char[] table = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
  



  private final byte[] string;
  




  public static DERUniversalString getInstance(Object obj)
  {
    if ((obj == null) || ((obj instanceof DERUniversalString)))
    {
      return (DERUniversalString)obj;
    }
    
    if ((obj instanceof byte[]))
    {
      try
      {
        return (DERUniversalString)fromByteArray((byte[])obj);
      }
      catch (Exception e)
      {
        throw new IllegalArgumentException("encoding error getInstance: " + e.toString());
      }
    }
    
    throw new IllegalArgumentException("illegal object in getInstance: " + obj.getClass().getName());
  }
  












  public static DERUniversalString getInstance(ASN1TaggedObject obj, boolean explicit)
  {
    ASN1Primitive o = obj.getObject();
    
    if ((explicit) || ((o instanceof DERUniversalString)))
    {
      return getInstance(o);
    }
    

    return new DERUniversalString(((ASN1OctetString)o).getOctets());
  }
  







  public DERUniversalString(byte[] string)
  {
    this.string = Arrays.clone(string);
  }
  
  public String getString()
  {
    StringBuffer buf = new StringBuffer("#");
    ByteArrayOutputStream bOut = new ByteArrayOutputStream();
    ASN1OutputStream aOut = new ASN1OutputStream(bOut);
    
    try
    {
      aOut.writeObject(this);
    }
    catch (IOException e)
    {
      throw new ASN1ParsingException("internal error encoding BitString");
    }
    
    byte[] string = bOut.toByteArray();
    
    for (int i = 0; i != string.length; i++)
    {
      buf.append(table[(string[i] >>> 4 & 0xF)]);
      buf.append(table[(string[i] & 0xF)]);
    }
    
    return buf.toString();
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
    out.writeEncoded(28, getOctets());
  }
  

  boolean asn1Equals(ASN1Primitive o)
  {
    if (!(o instanceof DERUniversalString))
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

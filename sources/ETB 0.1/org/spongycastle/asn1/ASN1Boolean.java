package org.spongycastle.asn1;

import java.io.IOException;
import org.spongycastle.util.Arrays;














public class ASN1Boolean
  extends ASN1Primitive
{
  private static final byte[] TRUE_VALUE = { -1 };
  private static final byte[] FALSE_VALUE = { 0 };
  
  private final byte[] value;
  
  public static final ASN1Boolean FALSE = new ASN1Boolean(false);
  public static final ASN1Boolean TRUE = new ASN1Boolean(true);
  








  public static ASN1Boolean getInstance(Object obj)
  {
    if ((obj == null) || ((obj instanceof ASN1Boolean)))
    {
      return (ASN1Boolean)obj;
    }
    
    if ((obj instanceof byte[]))
    {
      byte[] enc = (byte[])obj;
      try
      {
        return (ASN1Boolean)fromByteArray(enc);
      }
      catch (IOException e)
      {
        throw new IllegalArgumentException("failed to construct boolean from byte[]: " + e.getMessage());
      }
    }
    
    throw new IllegalArgumentException("illegal object in getInstance: " + obj.getClass().getName());
  }
  






  public static ASN1Boolean getInstance(boolean value)
  {
    return value ? TRUE : FALSE;
  }
  






  public static ASN1Boolean getInstance(int value)
  {
    return value != 0 ? TRUE : FALSE;
  }
  












  public static ASN1Boolean getInstance(ASN1TaggedObject obj, boolean explicit)
  {
    ASN1Primitive o = obj.getObject();
    
    if ((explicit) || ((o instanceof ASN1Boolean)))
    {
      return getInstance(o);
    }
    

    return fromOctetString(((ASN1OctetString)o).getOctets());
  }
  


  ASN1Boolean(byte[] value)
  {
    if (value.length != 1)
    {
      throw new IllegalArgumentException("byte value should have 1 byte in it");
    }
    
    if (value[0] == 0)
    {
      this.value = FALSE_VALUE;
    }
    else if ((value[0] & 0xFF) == 255)
    {
      this.value = TRUE_VALUE;
    }
    else
    {
      this.value = Arrays.clone(value);
    }
  }
  


  /**
   * @deprecated
   */
  public ASN1Boolean(boolean value)
  {
    this.value = (value ? TRUE_VALUE : FALSE_VALUE);
  }
  
  public boolean isTrue()
  {
    return value[0] != 0;
  }
  
  boolean isConstructed()
  {
    return false;
  }
  
  int encodedLength()
  {
    return 3;
  }
  

  void encode(ASN1OutputStream out)
    throws IOException
  {
    out.writeEncoded(1, value);
  }
  

  protected boolean asn1Equals(ASN1Primitive o)
  {
    if ((o instanceof ASN1Boolean))
    {
      return value[0] == value[0];
    }
    
    return false;
  }
  
  public int hashCode()
  {
    return value[0];
  }
  

  public String toString()
  {
    return value[0] != 0 ? "TRUE" : "FALSE";
  }
  
  static ASN1Boolean fromOctetString(byte[] value)
  {
    if (value.length != 1)
    {
      throw new IllegalArgumentException("BOOLEAN value should have 1 byte in it");
    }
    
    if (value[0] == 0)
    {
      return FALSE;
    }
    if ((value[0] & 0xFF) == 255)
    {
      return TRUE;
    }
    

    return new ASN1Boolean(value);
  }
}

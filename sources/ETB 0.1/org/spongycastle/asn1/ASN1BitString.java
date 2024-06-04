package org.spongycastle.asn1;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.io.Streams;





public abstract class ASN1BitString
  extends ASN1Primitive
  implements ASN1String
{
  private static final char[] table = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
  


  protected final byte[] data;
  

  protected final int padBits;
  


  protected static int getPadBits(int bitString)
  {
    int val = 0;
    for (int i = 3; i >= 0; i--)
    {




      if (i != 0)
      {
        if (bitString >> i * 8 != 0)
        {
          val = bitString >> i * 8 & 0xFF;
          break;
        }
        

      }
      else if (bitString != 0)
      {
        val = bitString & 0xFF;
        break;
      }
    }
    

    if (val == 0)
    {
      return 0;
    }
    
    int bits = 1;
    
    while ((val <<= 1 & 0xFF) != 0)
    {
      bits++;
    }
    
    return 8 - bits;
  }
  





  protected static byte[] getBytes(int bitString)
  {
    if (bitString == 0)
    {
      return new byte[0];
    }
    
    int bytes = 4;
    for (int i = 3; i >= 1; i--)
    {
      if ((bitString & 255 << i * 8) != 0) {
        break;
      }
      
      bytes--;
    }
    
    byte[] result = new byte[bytes];
    for (int i = 0; i < bytes; i++)
    {
      result[i] = ((byte)(bitString >> i * 8 & 0xFF));
    }
    
    return result;
  }
  








  public ASN1BitString(byte[] data, int padBits)
  {
    if (data == null)
    {
      throw new NullPointerException("data cannot be null");
    }
    if ((data.length == 0) && (padBits != 0))
    {
      throw new IllegalArgumentException("zero length data with non-zero pad bits");
    }
    if ((padBits > 7) || (padBits < 0))
    {
      throw new IllegalArgumentException("pad bits cannot be greater than 7 or less than 0");
    }
    
    this.data = Arrays.clone(data);
    this.padBits = padBits;
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
      throw new ASN1ParsingException("Internal error encoding BitString: " + e.getMessage(), e);
    }
    
    byte[] string = bOut.toByteArray();
    
    for (int i = 0; i != string.length; i++)
    {
      buf.append(table[(string[i] >>> 4 & 0xF)]);
      buf.append(table[(string[i] & 0xF)]);
    }
    
    return buf.toString();
  }
  



  public int intValue()
  {
    int value = 0;
    byte[] string = data;
    
    if ((padBits > 0) && (data.length <= 4))
    {
      string = derForm(data, padBits);
    }
    
    for (int i = 0; (i != string.length) && (i != 4); i++)
    {
      value |= (string[i] & 0xFF) << 8 * i;
    }
    
    return value;
  }
  







  public byte[] getOctets()
  {
    if (padBits != 0)
    {
      throw new IllegalStateException("attempt to get non-octet aligned data from BIT STRING");
    }
    
    return Arrays.clone(data);
  }
  
  public byte[] getBytes()
  {
    return derForm(data, padBits);
  }
  
  public int getPadBits()
  {
    return padBits;
  }
  
  public String toString()
  {
    return getString();
  }
  
  public int hashCode()
  {
    return padBits ^ Arrays.hashCode(getBytes());
  }
  

  protected boolean asn1Equals(ASN1Primitive o)
  {
    if (!(o instanceof ASN1BitString))
    {
      return false;
    }
    
    ASN1BitString other = (ASN1BitString)o;
    
    return (padBits == padBits) && 
      (Arrays.areEqual(getBytes(), other.getBytes()));
  }
  
  protected static byte[] derForm(byte[] data, int padBits)
  {
    byte[] rv = Arrays.clone(data);
    
    if (padBits > 0)
    {
      int tmp14_13 = (data.length - 1); byte[] tmp14_9 = rv;tmp14_9[tmp14_13] = ((byte)(tmp14_9[tmp14_13] & 255 << padBits));
    }
    
    return rv;
  }
  
  static ASN1BitString fromInputStream(int length, InputStream stream)
    throws IOException
  {
    if (length < 1)
    {
      throw new IllegalArgumentException("truncated BIT STRING detected");
    }
    
    int padBits = stream.read();
    byte[] data = new byte[length - 1];
    
    if (data.length != 0)
    {
      if (Streams.readFully(stream, data) != data.length)
      {
        throw new EOFException("EOF encountered in middle of BIT STRING");
      }
      
      if ((padBits > 0) && (padBits < 8))
      {
        if (data[(data.length - 1)] != (byte)(data[(data.length - 1)] & 255 << padBits))
        {
          return new DLBitString(data, padBits);
        }
      }
    }
    
    return new DERBitString(data, padBits);
  }
  
  public ASN1Primitive getLoadedObject()
  {
    return toASN1Primitive();
  }
  
  ASN1Primitive toDERObject()
  {
    return new DERBitString(data, padBits);
  }
  
  ASN1Primitive toDLObject()
  {
    return new DLBitString(data, padBits);
  }
  
  abstract void encode(ASN1OutputStream paramASN1OutputStream)
    throws IOException;
}

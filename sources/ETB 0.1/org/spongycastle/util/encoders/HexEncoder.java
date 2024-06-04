package org.spongycastle.util.encoders;

import java.io.IOException;
import java.io.OutputStream;




public class HexEncoder
  implements Encoder
{
  protected final byte[] encodingTable = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102 };
  







  protected final byte[] decodingTable = new byte[''];
  
  protected void initialiseDecodingTable()
  {
    for (int i = 0; i < decodingTable.length; i++)
    {
      decodingTable[i] = -1;
    }
    
    for (int i = 0; i < encodingTable.length; i++)
    {
      decodingTable[encodingTable[i]] = ((byte)i);
    }
    
    decodingTable[65] = decodingTable[97];
    decodingTable[66] = decodingTable[98];
    decodingTable[67] = decodingTable[99];
    decodingTable[68] = decodingTable[100];
    decodingTable[69] = decodingTable[101];
    decodingTable[70] = decodingTable[102];
  }
  
  public HexEncoder()
  {
    initialiseDecodingTable();
  }
  









  public int encode(byte[] data, int off, int length, OutputStream out)
    throws IOException
  {
    for (int i = off; i < off + length; i++)
    {
      int v = data[i] & 0xFF;
      
      out.write(encodingTable[(v >>> 4)]);
      out.write(encodingTable[(v & 0xF)]);
    }
    
    return length * 2;
  }
  

  private static boolean ignore(char c)
  {
    return (c == '\n') || (c == '\r') || (c == '\t') || (c == ' ');
  }
  











  public int decode(byte[] data, int off, int length, OutputStream out)
    throws IOException
  {
    int outLen = 0;
    
    int end = off + length;
    
    while (end > off)
    {
      if (!ignore((char)data[(end - 1)])) {
        break;
      }
      

      end--;
    }
    
    int i = off;
    while (i < end)
    {
      while ((i < end) && (ignore((char)data[i])))
      {
        i++;
      }
      
      byte b1 = decodingTable[data[(i++)]];
      
      while ((i < end) && (ignore((char)data[i])))
      {
        i++;
      }
      
      byte b2 = decodingTable[data[(i++)]];
      
      if ((b1 | b2) < 0)
      {
        throw new IOException("invalid characters encountered in Hex data");
      }
      
      out.write(b1 << 4 | b2);
      
      outLen++;
    }
    
    return outLen;
  }
  









  public int decode(String data, OutputStream out)
    throws IOException
  {
    int length = 0;
    
    int end = data.length();
    
    while (end > 0)
    {
      if (!ignore(data.charAt(end - 1))) {
        break;
      }
      

      end--;
    }
    
    int i = 0;
    while (i < end)
    {
      while ((i < end) && (ignore(data.charAt(i))))
      {
        i++;
      }
      
      byte b1 = decodingTable[data.charAt(i++)];
      
      while ((i < end) && (ignore(data.charAt(i))))
      {
        i++;
      }
      
      byte b2 = decodingTable[data.charAt(i++)];
      
      if ((b1 | b2) < 0)
      {
        throw new IOException("invalid characters encountered in Hex string");
      }
      
      out.write(b1 << 4 | b2);
      
      length++;
    }
    
    return length;
  }
}

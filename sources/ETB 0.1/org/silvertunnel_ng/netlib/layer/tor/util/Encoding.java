package org.silvertunnel_ng.netlib.layer.tor.util;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.silvertunnel_ng.netlib.util.DatatypeConverter;























































public class Encoding
{
  private static final String HEX_CHARS = "0123456789abcdef";
  private static final String[] HEX_LOOKUP = { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "0a", "0b", "0c", "0d", "0e", "0f", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "1a", "1b", "1c", "1d", "1e", "1f", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "2a", "2b", "2c", "2d", "2e", "2f", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "3a", "3b", "3c", "3d", "3e", "3f", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "4a", "4b", "4c", "4d", "4e", "4f", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "5a", "5b", "5c", "5d", "5e", "5f", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "6a", "6b", "6c", "6d", "6e", "6f", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "7a", "7b", "7c", "7d", "7e", "7f", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "8a", "8b", "8c", "8d", "8e", "8f", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "9a", "9b", "9c", "9d", "9e", "9f", "a0", "a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "aa", "ab", "ac", "ad", "ae", "af", "b0", "b1", "b2", "b3", "b4", "b5", "b6", "b7", "b8", "b9", "ba", "bb", "bc", "bd", "be", "bf", "c0", "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9", "ca", "cb", "cc", "cd", "ce", "cf", "d0", "d1", "d2", "d3", "d4", "d5", "d6", "d7", "d8", "d9", "da", "db", "dc", "dd", "de", "df", "e0", "e1", "e2", "e3", "e4", "e5", "e6", "e7", "e8", "e9", "ea", "eb", "ec", "ed", "ee", "ef", "f0", "f1", "f2", "f3", "f4", "f5", "f6", "f7", "f8", "f9", "fa", "fb", "fc", "fd", "fe", "ff" };
  











  private static final String BASE32_CHARS = "abcdefghijklmnopqrstuvwxyz234567";
  











  private static final int[] BASE32_LOOKUP = { 255, 255, 26, 27, 28, 29, 30, 31, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 255, 255, 255, 255, 255, 255, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 255, 255, 255, 255, 255 };
  





























  private static final Pattern HIDDENADDRESS_X_PATTERN = Parsing.compileRegexPattern("(.*?)\\.");
  private static final Pattern HIDDENADDRESS_Y_PATTERN = Parsing.compileRegexPattern("(.*?)\\.");
  


  private Encoding() {}
  


  public static String toHexString(byte[] block, int columnWidth, int offset, int length)
  {
    byte[] temp = new byte[length];
    System.arraycopy(block, offset, temp, 0, length);
    return toHexString(temp, columnWidth);
  }
  



  public static String toHexString(byte[] block, int columnWidth)
  {
    if (block == null)
    {
      return "null";
    }
    
    StringBuffer buf = new StringBuffer(4 * (block.length + 2));
    for (int i = 0; i < block.length; i++)
    {
      if (i > 0)
      {
        buf.append(':');
        if (i % (columnWidth / 3) == 0)
        {
          buf.append('\n');
        }
      }
      buf.append(HEX_LOOKUP[(block[i] & 0xFF)]);
    }
    return buf.toString();
  }
  



  public static String toHexStringNoColon(byte[] block)
  {
    StringBuffer buf = new StringBuffer(4 * (block.length + 2));
    for (int i = 0; i < block.length; i++)
    {
      buf.append(HEX_LOOKUP[(block[i] & 0xFF)]);
    }
    return buf.toString();
  }
  
  public static String toHexString(byte[] block)
  {
    return toHexString(block, block.length * 3 + 1);
  }
  










  public static byte[] intToNByteArray(int myInt, int n)
  {
    byte[] myBytes = new byte[n];
    
    for (int i = 0; i < n; i++)
    {
      myBytes[i] = ((byte)(myInt >> (n - i - 1) * 8 & 0xFF));
    }
    return myBytes;
  }
  







  public static byte[] intTo2ByteArray(int myInt)
  {
    return intToNByteArray(myInt, 2);
  }
  












  public static int byteArrayToInt(byte[] b, int offset, int length)
  {
    int value = 0;
    int numbersToConvert = b.length - offset;
    
    int n = Math.min(length, 4);
    n = Math.min(n, numbersToConvert);
    





    for (int i = 0; i < n; i++)
    {
      int shift = (n - 1 - i) * 8;
      value += ((b[(i + offset)] & 0xFF) << shift);
    }
    return value;
  }
  








  public static int byteArrayToInt(byte[] b)
  {
    return byteArrayToInt(b, 0, b.length);
  }
  



  private static final Pattern IP_PATTERN = Pattern.compile("(\\d+)\\.(\\d+)\\.(\\d+)\\.(\\d+)");
  







  public static long dottedNotationToBinary(String s)
  {
    long temp = 0L;
    
    Matcher m = IP_PATTERN.matcher(s);
    if (m.find())
    {
      for (int i = 1; i <= 4; i++)
      {
        temp <<= 8;
        temp |= Integer.parseInt(m.group(i));
      }
    }
    
    return temp;
  }
  








  public static int netmaskToInt(long netmask)
  {
    int result = 0;
    while ((netmask & 0xFFFFFFFF) != 0L)
    {
      netmask <<= 1;
      result++;
    }
    return result;
  }
  







  public static String binaryToDottedNotation(long ip)
  {
    StringBuffer dottedNotation = new StringBuffer(17);
    
    dottedNotation.append((ip & 0xFFFFFFFFFF000000) >> 24).append('.');
    dottedNotation.append((ip & 0xFF0000) >> 16).append('.');
    dottedNotation.append((ip & 0xFF00) >> 8).append('.');
    dottedNotation.append(ip & 0xFF);
    
    return dottedNotation.toString();
  }
  







  public static String toBase64(byte[] bytes, int columnWidth)
  {
    String rawResult = DatatypeConverter.printBase64Binary(bytes);
    

    StringBuffer result = new StringBuffer(1 + (rawResult.length() + columnWidth) / columnWidth);
    for (int i = 0; i < rawResult.length(); i += columnWidth)
    {
      String line = rawResult.substring(i, Math.min(rawResult.length(), i + columnWidth));
      result.append(line);
      result.append('\n');
    }
    
    return result.toString();
  }
  
  public static String toBase32(byte[] bytes)
  {
    int i = 0;int index = 0;int digit = 0;
    





    int add = 0;
    switch (bytes.length)
    {
    case 1: 
      add = 6;
      break;
    case 2: 
      add = 4;
      break;
    case 3: 
      add = 3;
      break;
    case 4: 
      add = 1;
    }
    
    

    StringBuffer base32 = new StringBuffer((bytes.length + 7) * 8 / 5 + add);
    
    while (i < bytes.length)
    {
      int currByte = bytes[i] >= 0 ? bytes[i] : bytes[i] + 256;
      

      if (index > 3) { int nextByte;
        int nextByte;
        if (i + 1 < bytes.length)
        {
          nextByte = bytes[(i + 1)] >= 0 ? bytes[(i + 1)] : bytes[(i + 1)] + 256;
        }
        else
        {
          nextByte = 0;
        }
        
        digit = currByte & 255 >> index;
        index = (index + 5) % 8;
        digit <<= index;
        digit |= nextByte >> 8 - index;
        i++;
      }
      else
      {
        digit = currByte >> 8 - (index + 5) & 0x1F;
        index = (index + 5) % 8;
        if (index == 0)
        {
          i++;
        }
      }
      base32.append("abcdefghijklmnopqrstuvwxyz234567".charAt(digit));
    }
    




    switch (bytes.length)
    {
    case 1: 
      base32.append("======");
      break;
    case 2: 
      base32.append("====");
      break;
    case 3: 
      base32.append("===");
      break;
    case 4: 
      base32.append("=");
    }
    
    

    return base32.toString();
  }
  

  public static byte[] parseBase32(String base32)
  {
    byte[] bytes = new byte[base32.length() * 5 / 8];
    
    int i = 0;int index = 0; for (int offset = 0; i < base32.length(); i++)
    {
      int lookup = base32.charAt(i) - '0';
      

      if ((lookup >= 0) && (lookup < BASE32_LOOKUP.length))
      {



        int digit = BASE32_LOOKUP[lookup];
        

        if (digit != 255)
        {



          if (index <= 3)
          {
            index = (index + 5) % 8;
            if (index == 0)
            {
              int tmp90_88 = offset; byte[] tmp90_86 = bytes;tmp90_86[tmp90_88] = ((byte)(tmp90_86[tmp90_88] | digit));
              offset++;
              if (offset >= bytes.length) {
                break;
              }
              
            }
            else
            {
              int tmp115_113 = offset; byte[] tmp115_111 = bytes;tmp115_111[tmp115_113] = ((byte)(tmp115_111[tmp115_113] | digit << 8 - index));
            }
          }
          else
          {
            index = (index + 5) % 8; int 
              tmp141_139 = offset; byte[] tmp141_137 = bytes;tmp141_137[tmp141_139] = ((byte)(tmp141_137[tmp141_139] | digit >>> index));
            offset++;
            
            if (offset >= bytes.length) {
              break;
            }
            
            int tmp168_166 = offset; byte[] tmp168_164 = bytes;tmp168_164[tmp168_166] = ((byte)(tmp168_164[tmp168_166] | digit << 8 - index));
          } }
      } }
    return bytes;
  }
  



  public static String toHex(long n)
  {
    int[] octet = new int[4];
    octet[0] = ((int)(n >> 24 & 0xFF));
    octet[1] = ((int)(n >> 16 & 0xFF));
    octet[2] = ((int)(n >> 8 & 0xFF));
    octet[3] = ((int)(n & 0xFF));
    StringBuffer buf = new StringBuffer();
    for (int i = 0; i < 4; i++)
    {
      buf.append("0123456789abcdef".substring(octet[i] >> 4, (octet[i] >> 4) + 1));
      buf.append("0123456789abcdef".substring(octet[i] & 0xF, (octet[i] & 0xF) + 1));
      buf.append(' ');
    }
    return buf.toString();
  }
  








  public static HashMap<String, String> parseHiddenAddress(String hostname)
  {
    HashMap<String, String> result = new HashMap(3);
    
    String z = hostname;
    z = z.replaceFirst(".onion", "");
    
    String x = Parsing.parseStringByRE(z, HIDDENADDRESS_X_PATTERN, "");
    z = z.replaceFirst(x + "\\.", "");
    
    String y = Parsing.parseStringByRE(z, HIDDENADDRESS_Y_PATTERN, "");
    z = z.replaceFirst(y + "\\.", "");
    
    if (y.isEmpty())
    {
      y = x;
      x = "";
    }
    
    result.put("x", x);
    result.put("y", y);
    result.put("z", z);
    
    return result;
  }
}

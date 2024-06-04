package org.silvertunnel_ng.netlib.util;

public final class DatatypeConverter {
  public DatatypeConverter() {}
  
  public static byte[] parseBase64Binary(String lexicalXSDBase64Binary) {
    return _parseBase64Binary(lexicalXSDBase64Binary);
  }
  
  public static byte[] parseHexBinary(String s) {
    int len = s.length();
    

    if (len % 2 != 0) {
      throw new IllegalArgumentException("hexBinary needs to be even-length: " + s);
    }
    byte[] out = new byte[len / 2];
    
    for (int i = 0; i < len; i += 2) {
      int h = hexToBin(s.charAt(i));
      int l = hexToBin(s.charAt(i + 1));
      if ((h == -1) || (l == -1)) {
        throw new IllegalArgumentException("contains illegal character for hexBinary: " + s);
      }
      out[(i / 2)] = ((byte)(h * 16 + l));
    }
    
    return out;
  }
  
  private static int hexToBin(char ch) {
    if (('0' <= ch) && (ch <= '9'))
      return ch - '0';
    if (('A' <= ch) && (ch <= 'F'))
      return ch - 'A' + 10;
    if (('a' <= ch) && (ch <= 'f'))
      return ch - 'a' + 10;
    return -1;
  }
  
  private static final char[] hexCode = "0123456789ABCDEF".toCharArray();
  
  public static String printHexBinary(byte[] data) {
    StringBuilder r = new StringBuilder(data.length * 2);
    for (byte b : data) {
      r.append(hexCode[(b >> 4 & 0xF)]);
      r.append(hexCode[(b & 0xF)]);
    }
    return r.toString();
  }
  
  public static String printBase64Binary(byte[] val) {
    return _printBase64Binary(val);
  }
  
  public static String printUnsignedShort(int val) {
    return String.valueOf(val);
  }
  
  public static String printAnySimpleType(String val) {
    return val;
  }
  



  private static final byte[] decodeMap = initDecodeMap();
  private static final byte PADDING = 127;
  
  private static byte[] initDecodeMap() {
    byte[] map = new byte[''];
    
    for (int i = 0; i < 128; i++) {
      map[i] = -1;
    }
    for (i = 65; i <= 90; i++)
      map[i] = ((byte)(i - 65));
    for (i = 97; i <= 122; i++)
      map[i] = ((byte)(i - 97 + 26));
    for (i = 48; i <= 57; i++)
      map[i] = ((byte)(i - 48 + 52));
    map[43] = 62;
    map[47] = 63;
    map[61] = Byte.MAX_VALUE;
    
    return map;
  }
  
















  private static int guessLength(String text)
  {
    int len = text.length();
    

    for (int j = len - 1; 
        j >= 0; j--) {
      byte code = decodeMap[text.charAt(j)];
      if (code != Byte.MAX_VALUE)
      {
        if (code != -1)
          break;
        return text.length() / 4 * 3;
      }
    }
    
    j++;
    int padSize = len - j;
    if (padSize > 2) {
      return text.length() / 4 * 3;
    }
    

    return text.length() / 4 * 3 - padSize;
  }
  







  public static byte[] _parseBase64Binary(String text)
  {
    int buflen = guessLength(text);
    byte[] out = new byte[buflen];
    int o = 0;
    
    int len = text.length();
    

    byte[] quadruplet = new byte[4];
    int q = 0;
    

    for (int i = 0; i < len; i++) {
      char ch = text.charAt(i);
      byte v = decodeMap[ch];
      
      if (v != -1) {
        quadruplet[(q++)] = v;
      }
      if (q == 4)
      {
        out[(o++)] = ((byte)(quadruplet[0] << 2 | quadruplet[1] >> 4));
        if (quadruplet[2] != Byte.MAX_VALUE)
          out[(o++)] = ((byte)(quadruplet[1] << 4 | quadruplet[2] >> 2));
        if (quadruplet[3] != Byte.MAX_VALUE)
          out[(o++)] = ((byte)(quadruplet[2] << 6 | quadruplet[3]));
        q = 0;
      }
    }
    
    if (buflen == o) {
      return out;
    }
    
    byte[] nb = new byte[o];
    System.arraycopy(out, 0, nb, 0, o);
    return nb;
  }
  
  private static final char[] encodeMap = initEncodeMap();
  
  private static char[] initEncodeMap() {
    char[] map = new char[64];
    
    for (int i = 0; i < 26; i++)
      map[i] = ((char)(65 + i));
    for (i = 26; i < 52; i++)
      map[i] = ((char)(97 + (i - 26)));
    for (i = 52; i < 62; i++)
      map[i] = ((char)(48 + (i - 52)));
    map[62] = '+';
    map[63] = '/';
    
    return map;
  }
  
  public static char encode(int i) {
    return encodeMap[(i & 0x3F)];
  }
  
  public static byte encodeByte(int i) {
    return (byte)encodeMap[(i & 0x3F)];
  }
  
  public static String _printBase64Binary(byte[] input) {
    return _printBase64Binary(input, 0, input.length);
  }
  
  public static String _printBase64Binary(byte[] input, int offset, int len) {
    char[] buf = new char[(len + 2) / 3 * 4];
    int ptr = _printBase64Binary(input, offset, len, buf, 0);
    assert (ptr == buf.length);
    return new String(buf);
  }
  







  public static int _printBase64Binary(byte[] input, int offset, int len, char[] buf, int ptr)
  {
    for (int i = offset; i < len; i += 3) {
      switch (len - i) {
      case 1: 
        buf[(ptr++)] = encode(input[i] >> 2);
        buf[(ptr++)] = encode((input[i] & 0x3) << 4);
        buf[(ptr++)] = '=';
        buf[(ptr++)] = '=';
        break;
      case 2: 
        buf[(ptr++)] = encode(input[i] >> 2);
        buf[(ptr++)] = encode((input[i] & 0x3) << 4 | input[(i + 1)] >> 4 & 0xF);
        buf[(ptr++)] = encode((input[(i + 1)] & 0xF) << 2);
        buf[(ptr++)] = '=';
        break;
      default: 
        buf[(ptr++)] = encode(input[i] >> 2);
        buf[(ptr++)] = encode((input[i] & 0x3) << 4 | input[(i + 1)] >> 4 & 0xF);
        buf[(ptr++)] = encode((input[(i + 1)] & 0xF) << 2 | input[(i + 2)] >> 6 & 0x3);
        buf[(ptr++)] = encode(input[(i + 2)] & 0x3F);
      }
      
    }
    return ptr;
  }
  







  public static int _printBase64Binary(byte[] input, int offset, int len, byte[] out, int ptr)
  {
    byte[] buf = out;
    int max = len + offset;
    for (int i = offset; i < max; i += 3) {
      switch (max - i) {
      case 1: 
        buf[(ptr++)] = encodeByte(input[i] >> 2);
        buf[(ptr++)] = encodeByte((input[i] & 0x3) << 4);
        buf[(ptr++)] = 61;
        buf[(ptr++)] = 61;
        break;
      case 2: 
        buf[(ptr++)] = encodeByte(input[i] >> 2);
        buf[(ptr++)] = encodeByte((input[i] & 0x3) << 4 | input[(i + 1)] >> 4 & 0xF);
        buf[(ptr++)] = encodeByte((input[(i + 1)] & 0xF) << 2);
        buf[(ptr++)] = 61;
        break;
      default: 
        buf[(ptr++)] = encodeByte(input[i] >> 2);
        buf[(ptr++)] = encodeByte((input[i] & 0x3) << 4 | input[(i + 1)] >> 4 & 0xF);
        buf[(ptr++)] = encodeByte((input[(i + 1)] & 0xF) << 2 | input[(i + 2)] >> 6 & 0x3);
        buf[(ptr++)] = encodeByte(input[(i + 2)] & 0x3F);
      }
      
    }
    
    return ptr;
  }
}

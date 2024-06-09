package org.json.simple.parser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;










class Yylex
{
  public static final int YYEOF = -1;
  private static final int ZZ_BUFFERSIZE = 16384;
  public static final int YYINITIAL = 0;
  public static final int STRING_BEGIN = 2;
  private static final int[] ZZ_LEXSTATE = {
    0, 0, 1, 1 };
  






  private static final String ZZ_CMAP_PACKED = "\t\000\001\007\001\007\002\000\001\007\022\000\001\007\001\000\001\t\b\000\001\006\001\031\001\002\001\004\001\n\n\003\001\032\006\000\004\001\001\005\001\001\024\000\001\027\001\b\001\030\003\000\001\022\001\013\002\001\001\021\001\f\005\000\001\023\001\000\001\r\003\000\001\016\001\024\001\017\001\020\005\000\001\025\001\000\001\026ﾂ\000";
  






  private static final char[] ZZ_CMAP = zzUnpackCMap("\t\000\001\007\001\007\002\000\001\007\022\000\001\007\001\000\001\t\b\000\001\006\001\031\001\002\001\004\001\n\n\003\001\032\006\000\004\001\001\005\001\001\024\000\001\027\001\b\001\030\003\000\001\022\001\013\002\001\001\021\001\f\005\000\001\023\001\000\001\r\003\000\001\016\001\024\001\017\001\020\005\000\001\025\001\000\001\026ﾂ\000");
  



  private static final int[] ZZ_ACTION = zzUnpackAction();
  


  private static final String ZZ_ACTION_PACKED_0 = "\002\000\002\001\001\002\001\003\001\004\003\001\001\005\001\006\001\007\001\b\001\t\001\n\001\013\001\f\001\r\005\000\001\f\001\016\001\017\001\020\001\021\001\022\001\023\001\024\001\000\001\025\001\000\001\025\004\000\001\026\001\027\002\000\001\030";
  


  private static int[] zzUnpackAction()
  {
    int[] result = new int[45];
    int offset = 0;
    offset = zzUnpackAction("\002\000\002\001\001\002\001\003\001\004\003\001\001\005\001\006\001\007\001\b\001\t\001\n\001\013\001\f\001\r\005\000\001\f\001\016\001\017\001\020\001\021\001\022\001\023\001\024\001\000\001\025\001\000\001\025\004\000\001\026\001\027\002\000\001\030", offset, result);
    return result;
  }
  
  private static int zzUnpackAction(String packed, int offset, int[] result) {
    int i = 0;
    int j = offset;
    int l = packed.length();
    int count; for (; i < l; 
        

        count > 0)
    {
      count = packed.charAt(i++);
      int value = packed.charAt(i++);
      result[(j++)] = value;count--;
    }
    return j;
  }
  




  private static final int[] ZZ_ROWMAP = zzUnpackRowMap();
  


  private static final String ZZ_ROWMAP_PACKED_0 = "\000\000\000\033\0006\000Q\000l\000\0006\000¢\000½\000Ø\0006\0006\0006\0006\0006\0006\000ó\000Ď\0006\000ĩ\000ń\000ş\000ź\000ƕ\0006\0006\0006\0006\0006\0006\0006\0006\000ư\000ǋ\000Ǧ\000Ǧ\000ȁ\000Ȝ\000ȷ\000ɒ\0006\0006\000ɭ\000ʈ\0006";
  



  private static int[] zzUnpackRowMap()
  {
    int[] result = new int[45];
    int offset = 0;
    offset = zzUnpackRowMap("\000\000\000\033\0006\000Q\000l\000\0006\000¢\000½\000Ø\0006\0006\0006\0006\0006\0006\000ó\000Ď\0006\000ĩ\000ń\000ş\000ź\000ƕ\0006\0006\0006\0006\0006\0006\0006\0006\000ư\000ǋ\000Ǧ\000Ǧ\000ȁ\000Ȝ\000ȷ\000ɒ\0006\0006\000ɭ\000ʈ\0006", offset, result);
    return result;
  }
  
  private static int zzUnpackRowMap(String packed, int offset, int[] result) {
    int i = 0;
    int j = offset;
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << '\020';
      result[(j++)] = (high | packed.charAt(i++));
    }
    return j;
  }
  



  private static final int[] ZZ_TRANS = {
    2, 2, 3, 4, 2, 2, 2, 5, 2, 6, 
    2, 2, 7, 8, 2, 9, 2, 2, 2, 2, 
    2, 10, 11, 12, 13, 14, 15, 16, 16, 16, 
    16, 16, 16, 16, 16, 17, 18, 16, 16, 16, 
    16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 
    16, 16, 16, 16, -1, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, 4, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    -1, 4, 19, 20, -1, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, 20, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    -1, -1, 5, -1, -1, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    21, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, 22, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    23, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    -1, -1, -1, 16, 16, 16, 16, 16, 16, 16, 
    16, -1, -1, 16, 16, 16, 16, 16, 16, 16, 
    16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 
    -1, -1, -1, -1, -1, -1, -1, -1, 24, 25, 
    26, 27, 28, 29, 30, 31, 32, -1, -1, -1, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    33, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, -1, 34, 35, -1, -1, 
    34, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    36, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, -1, -1, 37, -1, -1, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    -1, 38, -1, -1, -1, -1, -1, -1, -1, -1, 
    -1, -1, -1, 39, -1, 39, -1, 39, -1, -1, 
    -1, -1, -1, 39, 39, -1, -1, -1, -1, 39, 
    39, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    -1, -1, 33, -1, 20, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, -1, 20, -1, -1, -1, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, 35, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    -1, -1, -1, 38, -1, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, 40, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, 41, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, 42, -1, 42, -1, 42, 
    -1, -1, -1, -1, -1, 42, 42, -1, -1, -1, 
    -1, 42, 42, -1, -1, -1, -1, -1, -1, -1, 
    -1, -1, 43, -1, 43, -1, 43, -1, -1, -1, 
    -1, -1, 43, 43, -1, -1, -1, -1, 43, 43, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, 44, 
    -1, 44, -1, 44, -1, -1, -1, -1, -1, 44, 
    44, -1, -1, -1, -1, 44, 44, -1, -1, -1, 
    -1, -1, -1, -1, -1 };
  

  private static final int ZZ_UNKNOWN_ERROR = 0;
  
  private static final int ZZ_NO_MATCH = 1;
  
  private static final int ZZ_PUSHBACK_2BIG = 2;
  
  private static final String[] ZZ_ERROR_MSG = {
    "Unkown internal scanner error", 
    "Error: could not match input", 
    "Error: pushback value was too large" };
  




  private static final int[] ZZ_ATTRIBUTE = zzUnpackAttribute();
  
  private static final String ZZ_ATTRIBUTE_PACKED_0 = "\002\000\001\t\003\001\001\t\003\001\006\t\002\001\001\t\005\000\b\t\001\000\001\001\001\000\001\001\004\000\002\t\002\000\001\t";
  private Reader zzReader;
  private int zzState;
  
  private static int[] zzUnpackAttribute()
  {
    int[] result = new int[45];
    int offset = 0;
    offset = zzUnpackAttribute("\002\000\001\t\003\001\001\t\003\001\006\t\002\001\001\t\005\000\b\t\001\000\001\001\001\000\001\001\004\000\002\t\002\000\001\t", offset, result);
    return result;
  }
  
  private static int zzUnpackAttribute(String packed, int offset, int[] result) {
    int i = 0;
    int j = offset;
    int l = packed.length();
    int count; for (; i < l; 
        

        count > 0)
    {
      count = packed.charAt(i++);
      int value = packed.charAt(i++);
      result[(j++)] = value;count--;
    }
    return j;
  }
  







  private int zzLexicalState = 0;
  


  private char[] zzBuffer = new char['䀀'];
  


  private int zzMarkedPos;
  


  private int zzCurrentPos;
  


  private int zzStartRead;
  


  private int zzEndRead;
  


  private int yyline;
  


  private int yychar;
  

  private int yycolumn;
  

  private boolean zzAtBOL = true;
  

  private boolean zzAtEOF;
  

  private StringBuffer sb = new StringBuffer();
  
  int getPosition() {
    return yychar;
  }
  







  Yylex(Reader in)
  {
    zzReader = in;
  }
  





  Yylex(InputStream in)
  {
    this(new InputStreamReader(in));
  }
  





  private static char[] zzUnpackCMap(String packed)
  {
    char[] map = new char[65536];
    int i = 0;
    int j = 0;
    int count; for (; i < 90; 
        

        count > 0)
    {
      count = packed.charAt(i++);
      char value = packed.charAt(i++);
      map[(j++)] = value;count--;
    }
    return map;
  }
  








  private boolean zzRefill()
    throws IOException
  {
    if (zzStartRead > 0) {
      System.arraycopy(zzBuffer, zzStartRead, 
        zzBuffer, 0, 
        zzEndRead - zzStartRead);
      

      zzEndRead -= zzStartRead;
      zzCurrentPos -= zzStartRead;
      zzMarkedPos -= zzStartRead;
      zzStartRead = 0;
    }
    

    if (zzCurrentPos >= zzBuffer.length)
    {
      char[] newBuffer = new char[zzCurrentPos * 2];
      System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
      zzBuffer = newBuffer;
    }
    

    int numRead = zzReader.read(zzBuffer, zzEndRead, 
      zzBuffer.length - zzEndRead);
    
    if (numRead > 0) {
      zzEndRead += numRead;
      return false;
    }
    
    if (numRead == 0) {
      int c = zzReader.read();
      if (c == -1) {
        return true;
      }
      zzBuffer[(zzEndRead++)] = ((char)c);
      return false;
    }
    


    return true;
  }
  


  public final void yyclose()
    throws IOException
  {
    zzAtEOF = true;
    zzEndRead = zzStartRead;
    
    if (zzReader != null) {
      zzReader.close();
    }
  }
  









  public final void yyreset(Reader reader)
  {
    zzReader = reader;
    zzAtBOL = true;
    zzAtEOF = false;
    zzEndRead = (this.zzStartRead = 0);
    zzCurrentPos = (this.zzMarkedPos = 0);
    yyline = (this.yychar = this.yycolumn = 0);
    zzLexicalState = 0;
  }
  



  public final int yystate()
  {
    return zzLexicalState;
  }
  





  public final void yybegin(int newState)
  {
    zzLexicalState = newState;
  }
  



  public final String yytext()
  {
    return new String(zzBuffer, zzStartRead, zzMarkedPos - zzStartRead);
  }
  











  public final char yycharat(int pos)
  {
    return zzBuffer[(zzStartRead + pos)];
  }
  



  public final int yylength()
  {
    return zzMarkedPos - zzStartRead;
  }
  







  private void zzScanError(int errorCode)
  {
    String message;
    





    try
    {
      message = ZZ_ERROR_MSG[errorCode];
    } catch (ArrayIndexOutOfBoundsException e) {
      String message;
      message = ZZ_ERROR_MSG[0];
    }
    
    throw new Error(message);
  }
  








  public void yypushback(int number)
  {
    if (number > yylength()) {
      zzScanError(2);
    }
    zzMarkedPos -= number;
  }
  












  public Yytoken yylex()
    throws IOException, ParseException
  {
    int zzEndReadL = zzEndRead;
    char[] zzBufferL = zzBuffer;
    char[] zzCMapL = ZZ_CMAP;
    
    int[] zzTransL = ZZ_TRANS;
    int[] zzRowMapL = ZZ_ROWMAP;
    int[] zzAttrL = ZZ_ATTRIBUTE;
    for (;;)
    {
      int zzMarkedPosL = zzMarkedPos;
      
      yychar += zzMarkedPosL - zzStartRead;
      
      int zzAction = -1;
      
      int zzCurrentPosL = this.zzCurrentPos = this.zzStartRead = zzMarkedPosL;
      
      zzState = ZZ_LEXSTATE[zzLexicalState];
      int zzInput;
      int zzAttributes;
      do {
        do {
          int zzInput;
          if (zzCurrentPosL < zzEndReadL) {
            zzInput = zzBufferL[(zzCurrentPosL++)];
          } else { if (zzAtEOF) {
              int zzInput = -1;
              break;
            }
            

            zzCurrentPos = zzCurrentPosL;
            zzMarkedPos = zzMarkedPosL;
            boolean eof = zzRefill();
            
            zzCurrentPosL = zzCurrentPos;
            zzMarkedPosL = zzMarkedPos;
            zzBufferL = zzBuffer;
            zzEndReadL = zzEndRead;
            if (eof) {
              int zzInput = -1;
              break;
            }
            
            zzInput = zzBufferL[(zzCurrentPosL++)];
          }
          
          int zzNext = zzTransL[(zzRowMapL[zzState] + zzCMapL[zzInput])];
          if (zzNext == -1) break;
          zzState = zzNext;
          
          zzAttributes = zzAttrL[zzState];
        } while ((zzAttributes & 0x1) != 1);
        zzAction = zzState;
        zzMarkedPosL = zzCurrentPosL;
      } while ((zzAttributes & 0x8) != 8);
      





      zzMarkedPos = zzMarkedPosL;
      
      switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
      case 11: 
        sb.append(yytext());
      case 25: 
        break;
      case 4: 
        sb.delete(0, sb.length());yybegin(2);
      case 26: 
        break;
      case 16: 
        sb.append('\b');
      case 27: 
        break;
      case 6: 
        return new Yytoken(2, null);
      case 28: 
        break;
      case 23: 
        Boolean val = Boolean.valueOf(yytext());return new Yytoken(0, val);
      case 29: 
        break;
      case 22: 
        return new Yytoken(0, null);
      case 30: 
        break;
      case 13: 
        yybegin(0);return new Yytoken(0, sb.toString());
      case 31: 
        break;
      case 12: 
        sb.append('\\');
      case 32: 
        break;
      case 21: 
        Double val = Double.valueOf(yytext());return new Yytoken(0, val);
      case 33: 
        break;
      case 1: 
        throw new ParseException(yychar, 0, new Character(yycharat(0)));
      case 34: 
        break;
      case 8: 
        return new Yytoken(4, null);
      case 35: 
        break;
      case 19: 
        sb.append('\r');
      case 36: 
        break;
      case 15: 
        sb.append('/');
      case 37: 
        break;
      case 10: 
        return new Yytoken(6, null);
      case 38: 
        break;
      case 14: 
        sb.append('"');
      case 39: 
        break;
      case 5: 
        return new Yytoken(1, null);
      case 40: 
        break;
      case 17: 
        sb.append('\f');
      case 41: 
        break;
      case 24: 
        try {
          int ch = Integer.parseInt(yytext().substring(2), 16);
          sb.append((char)ch);
        }
        catch (Exception e) {
          throw new ParseException(yychar, 2, e);
        }
      case 42: 
        break;
      case 20: 
        sb.append('\t');
      case 43: 
        break;
      case 7: 
        return new Yytoken(3, null);
      case 44: 
        break;
      case 2: 
        Long val = Long.valueOf(yytext());return new Yytoken(0, val);
      case 45: 
        break;
      case 18: 
        sb.append('\n');
      case 46: 
        break;
      case 9: 
        return new Yytoken(5, null);
      case 47: 
        break;
      case 3: 
      case 48: 
        break;
      
      default: 
        if ((zzInput == -1) && (zzStartRead == zzCurrentPos)) {
          zzAtEOF = true;
          return null;
        }
        
        zzScanError(1);
      }
    }
  }
}

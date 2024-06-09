package org.json.simple.parser;


public class Yytoken
{
  public static final int TYPE_VALUE = 0;
  
  public static final int TYPE_LEFT_BRACE = 1;
  
  public static final int TYPE_RIGHT_BRACE = 2;
  
  public static final int TYPE_LEFT_SQUARE = 3;
  
  public static final int TYPE_RIGHT_SQUARE = 4;
  
  public static final int TYPE_COMMA = 5;
  
  public static final int TYPE_COLON = 6;
  public static final int TYPE_EOF = -1;
  public int type = 0;
  public Object value = null;
  
  public Yytoken(int type, Object value) {
    this.type = type;
    this.value = value;
  }
  
  public String toString() {
    StringBuffer sb = new StringBuffer();
    switch (type) {
    case 0: 
      sb.append("VALUE(").append(value).append(")");
      break;
    case 1: 
      sb.append("LEFT BRACE({)");
      break;
    case 2: 
      sb.append("RIGHT BRACE(})");
      break;
    case 3: 
      sb.append("LEFT SQUARE([)");
      break;
    case 4: 
      sb.append("RIGHT SQUARE(])");
      break;
    case 5: 
      sb.append("COMMA(,)");
      break;
    case 6: 
      sb.append("COLON(:)");
      break;
    case -1: 
      sb.append("END OF FILE");
    }
    
    return sb.toString();
  }
}

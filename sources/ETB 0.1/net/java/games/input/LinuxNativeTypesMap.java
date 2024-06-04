package net.java.games.input;

import java.util.logging.Logger;






























class LinuxNativeTypesMap
{
  private static LinuxNativeTypesMap INSTANCE = new LinuxNativeTypesMap();
  private static Logger log = Logger.getLogger(LinuxNativeTypesMap.class.getName());
  
  private final Component.Identifier[] relAxesIDs;
  
  private final Component.Identifier[] absAxesIDs;
  private final Component.Identifier[] buttonIDs;
  
  private LinuxNativeTypesMap()
  {
    buttonIDs = new Component.Identifier['ǿ'];
    relAxesIDs = new Component.Identifier[15];
    absAxesIDs = new Component.Identifier[63];
    reInit();
  }
  

  private void reInit()
  {
    buttonIDs[1] = Component.Identifier.Key.ESCAPE;
    buttonIDs[2] = Component.Identifier.Key._1;
    buttonIDs[3] = Component.Identifier.Key._2;
    buttonIDs[4] = Component.Identifier.Key._3;
    buttonIDs[5] = Component.Identifier.Key._4;
    buttonIDs[6] = Component.Identifier.Key._5;
    buttonIDs[7] = Component.Identifier.Key._6;
    buttonIDs[8] = Component.Identifier.Key._7;
    buttonIDs[9] = Component.Identifier.Key._8;
    buttonIDs[10] = Component.Identifier.Key._9;
    buttonIDs[11] = Component.Identifier.Key._0;
    buttonIDs[12] = Component.Identifier.Key.MINUS;
    buttonIDs[13] = Component.Identifier.Key.EQUALS;
    buttonIDs[14] = Component.Identifier.Key.BACK;
    buttonIDs[15] = Component.Identifier.Key.TAB;
    buttonIDs[16] = Component.Identifier.Key.Q;
    buttonIDs[17] = Component.Identifier.Key.W;
    buttonIDs[18] = Component.Identifier.Key.E;
    buttonIDs[19] = Component.Identifier.Key.R;
    buttonIDs[20] = Component.Identifier.Key.T;
    buttonIDs[21] = Component.Identifier.Key.Y;
    buttonIDs[22] = Component.Identifier.Key.U;
    buttonIDs[23] = Component.Identifier.Key.I;
    buttonIDs[24] = Component.Identifier.Key.O;
    buttonIDs[25] = Component.Identifier.Key.P;
    buttonIDs[26] = Component.Identifier.Key.LBRACKET;
    buttonIDs[27] = Component.Identifier.Key.RBRACKET;
    buttonIDs[28] = Component.Identifier.Key.RETURN;
    buttonIDs[29] = Component.Identifier.Key.LCONTROL;
    buttonIDs[30] = Component.Identifier.Key.A;
    buttonIDs[31] = Component.Identifier.Key.S;
    buttonIDs[32] = Component.Identifier.Key.D;
    buttonIDs[33] = Component.Identifier.Key.F;
    buttonIDs[34] = Component.Identifier.Key.G;
    buttonIDs[35] = Component.Identifier.Key.H;
    buttonIDs[36] = Component.Identifier.Key.J;
    buttonIDs[37] = Component.Identifier.Key.K;
    buttonIDs[38] = Component.Identifier.Key.L;
    buttonIDs[39] = Component.Identifier.Key.SEMICOLON;
    buttonIDs[40] = Component.Identifier.Key.APOSTROPHE;
    buttonIDs[41] = Component.Identifier.Key.GRAVE;
    buttonIDs[42] = Component.Identifier.Key.LSHIFT;
    buttonIDs[43] = Component.Identifier.Key.BACKSLASH;
    buttonIDs[44] = Component.Identifier.Key.Z;
    buttonIDs[45] = Component.Identifier.Key.X;
    buttonIDs[46] = Component.Identifier.Key.C;
    buttonIDs[47] = Component.Identifier.Key.V;
    buttonIDs[48] = Component.Identifier.Key.B;
    buttonIDs[49] = Component.Identifier.Key.N;
    buttonIDs[50] = Component.Identifier.Key.M;
    buttonIDs[51] = Component.Identifier.Key.COMMA;
    buttonIDs[52] = Component.Identifier.Key.PERIOD;
    buttonIDs[53] = Component.Identifier.Key.SLASH;
    buttonIDs[54] = Component.Identifier.Key.RSHIFT;
    buttonIDs[55] = Component.Identifier.Key.MULTIPLY;
    buttonIDs[56] = Component.Identifier.Key.LALT;
    buttonIDs[57] = Component.Identifier.Key.SPACE;
    buttonIDs[58] = Component.Identifier.Key.CAPITAL;
    buttonIDs[59] = Component.Identifier.Key.F1;
    buttonIDs[60] = Component.Identifier.Key.F2;
    buttonIDs[61] = Component.Identifier.Key.F3;
    buttonIDs[62] = Component.Identifier.Key.F4;
    buttonIDs[63] = Component.Identifier.Key.F5;
    buttonIDs[64] = Component.Identifier.Key.F6;
    buttonIDs[65] = Component.Identifier.Key.F7;
    buttonIDs[66] = Component.Identifier.Key.F8;
    buttonIDs[67] = Component.Identifier.Key.F9;
    buttonIDs[68] = Component.Identifier.Key.F10;
    buttonIDs[69] = Component.Identifier.Key.NUMLOCK;
    buttonIDs[70] = Component.Identifier.Key.SCROLL;
    buttonIDs[71] = Component.Identifier.Key.NUMPAD7;
    buttonIDs[72] = Component.Identifier.Key.NUMPAD8;
    buttonIDs[73] = Component.Identifier.Key.NUMPAD9;
    buttonIDs[74] = Component.Identifier.Key.SUBTRACT;
    buttonIDs[75] = Component.Identifier.Key.NUMPAD4;
    buttonIDs[76] = Component.Identifier.Key.NUMPAD5;
    buttonIDs[77] = Component.Identifier.Key.NUMPAD6;
    buttonIDs[78] = Component.Identifier.Key.ADD;
    buttonIDs[79] = Component.Identifier.Key.NUMPAD1;
    buttonIDs[80] = Component.Identifier.Key.NUMPAD2;
    buttonIDs[81] = Component.Identifier.Key.NUMPAD3;
    buttonIDs[82] = Component.Identifier.Key.NUMPAD0;
    buttonIDs[83] = Component.Identifier.Key.DECIMAL;
    
    buttonIDs['·'] = Component.Identifier.Key.F13;
    buttonIDs[86] = null;
    buttonIDs[87] = Component.Identifier.Key.F11;
    buttonIDs[88] = Component.Identifier.Key.F12;
    buttonIDs['¸'] = Component.Identifier.Key.F14;
    buttonIDs['¹'] = Component.Identifier.Key.F15;
    buttonIDs['º'] = null;
    buttonIDs['»'] = null;
    buttonIDs['¼'] = null;
    buttonIDs['½'] = null;
    buttonIDs['¾'] = null;
    buttonIDs[96] = Component.Identifier.Key.NUMPADENTER;
    buttonIDs[97] = Component.Identifier.Key.RCONTROL;
    buttonIDs[98] = Component.Identifier.Key.DIVIDE;
    buttonIDs[99] = Component.Identifier.Key.SYSRQ;
    buttonIDs[100] = Component.Identifier.Key.RALT;
    buttonIDs[101] = null;
    buttonIDs[102] = Component.Identifier.Key.HOME;
    buttonIDs[103] = Component.Identifier.Key.UP;
    buttonIDs[104] = Component.Identifier.Key.PAGEUP;
    buttonIDs[105] = Component.Identifier.Key.LEFT;
    buttonIDs[106] = Component.Identifier.Key.RIGHT;
    buttonIDs[107] = Component.Identifier.Key.END;
    buttonIDs[108] = Component.Identifier.Key.DOWN;
    buttonIDs[109] = Component.Identifier.Key.PAGEDOWN;
    buttonIDs[110] = Component.Identifier.Key.INSERT;
    buttonIDs[111] = Component.Identifier.Key.DELETE;
    buttonIDs[119] = Component.Identifier.Key.PAUSE;
    




    buttonIDs[117] = Component.Identifier.Key.NUMPADEQUAL;
    






















    buttonIDs[''] = Component.Identifier.Key.SLEEP;
    





























































    buttonIDs['ð'] = Component.Identifier.Key.UNLABELED;
    



    buttonIDs['Ā'] = Component.Identifier.Button._0;
    buttonIDs['ā'] = Component.Identifier.Button._1;
    buttonIDs['Ă'] = Component.Identifier.Button._2;
    buttonIDs['ă'] = Component.Identifier.Button._3;
    buttonIDs['Ą'] = Component.Identifier.Button._4;
    buttonIDs['ą'] = Component.Identifier.Button._5;
    buttonIDs['Ć'] = Component.Identifier.Button._6;
    buttonIDs['ć'] = Component.Identifier.Button._7;
    buttonIDs['Ĉ'] = Component.Identifier.Button._8;
    buttonIDs['ĉ'] = Component.Identifier.Button._9;
    

    buttonIDs['Đ'] = Component.Identifier.Button.LEFT;
    buttonIDs['đ'] = Component.Identifier.Button.RIGHT;
    buttonIDs['Ē'] = Component.Identifier.Button.MIDDLE;
    buttonIDs['ē'] = Component.Identifier.Button.SIDE;
    buttonIDs['Ĕ'] = Component.Identifier.Button.EXTRA;
    buttonIDs['ĕ'] = Component.Identifier.Button.FORWARD;
    buttonIDs['Ė'] = Component.Identifier.Button.BACK;
    

    buttonIDs['Ġ'] = Component.Identifier.Button.TRIGGER;
    buttonIDs['ġ'] = Component.Identifier.Button.THUMB;
    buttonIDs['Ģ'] = Component.Identifier.Button.THUMB2;
    buttonIDs['ģ'] = Component.Identifier.Button.TOP;
    buttonIDs['Ĥ'] = Component.Identifier.Button.TOP2;
    buttonIDs['ĥ'] = Component.Identifier.Button.PINKIE;
    buttonIDs['Ħ'] = Component.Identifier.Button.BASE;
    buttonIDs['ħ'] = Component.Identifier.Button.BASE2;
    buttonIDs['Ĩ'] = Component.Identifier.Button.BASE3;
    buttonIDs['ĩ'] = Component.Identifier.Button.BASE4;
    buttonIDs['Ī'] = Component.Identifier.Button.BASE5;
    buttonIDs['ī'] = Component.Identifier.Button.BASE6;
    buttonIDs['į'] = Component.Identifier.Button.DEAD;
    

    buttonIDs['İ'] = Component.Identifier.Button.A;
    buttonIDs['ı'] = Component.Identifier.Button.B;
    buttonIDs['Ĳ'] = Component.Identifier.Button.C;
    buttonIDs['ĳ'] = Component.Identifier.Button.X;
    buttonIDs['Ĵ'] = Component.Identifier.Button.Y;
    buttonIDs['ĵ'] = Component.Identifier.Button.Z;
    buttonIDs['Ķ'] = Component.Identifier.Button.LEFT_THUMB;
    buttonIDs['ķ'] = Component.Identifier.Button.RIGHT_THUMB;
    buttonIDs['ĸ'] = Component.Identifier.Button.LEFT_THUMB2;
    buttonIDs['Ĺ'] = Component.Identifier.Button.RIGHT_THUMB2;
    buttonIDs['ĺ'] = Component.Identifier.Button.SELECT;
    buttonIDs['ļ'] = Component.Identifier.Button.MODE;
    buttonIDs['Ľ'] = Component.Identifier.Button.LEFT_THUMB3;
    buttonIDs['ľ'] = Component.Identifier.Button.RIGHT_THUMB3;
    

    buttonIDs['ŀ'] = Component.Identifier.Button.TOOL_PEN;
    buttonIDs['Ł'] = Component.Identifier.Button.TOOL_RUBBER;
    buttonIDs['ł'] = Component.Identifier.Button.TOOL_BRUSH;
    buttonIDs['Ń'] = Component.Identifier.Button.TOOL_PENCIL;
    buttonIDs['ń'] = Component.Identifier.Button.TOOL_AIRBRUSH;
    buttonIDs['Ņ'] = Component.Identifier.Button.TOOL_FINGER;
    buttonIDs['ņ'] = Component.Identifier.Button.TOOL_MOUSE;
    buttonIDs['Ň'] = Component.Identifier.Button.TOOL_LENS;
    buttonIDs['Ŋ'] = Component.Identifier.Button.TOUCH;
    buttonIDs['ŋ'] = Component.Identifier.Button.STYLUS;
    buttonIDs['Ō'] = Component.Identifier.Button.STYLUS2;
    
    relAxesIDs[0] = Component.Identifier.Axis.X;
    relAxesIDs[1] = Component.Identifier.Axis.Y;
    relAxesIDs[2] = Component.Identifier.Axis.Z;
    relAxesIDs[8] = Component.Identifier.Axis.Z;
    
    relAxesIDs[6] = Component.Identifier.Axis.SLIDER;
    relAxesIDs[7] = Component.Identifier.Axis.SLIDER;
    relAxesIDs[9] = Component.Identifier.Axis.SLIDER;
    
    absAxesIDs[0] = Component.Identifier.Axis.X;
    absAxesIDs[1] = Component.Identifier.Axis.Y;
    absAxesIDs[2] = Component.Identifier.Axis.Z;
    absAxesIDs[3] = Component.Identifier.Axis.RX;
    absAxesIDs[4] = Component.Identifier.Axis.RY;
    absAxesIDs[5] = Component.Identifier.Axis.RZ;
    absAxesIDs[6] = Component.Identifier.Axis.SLIDER;
    absAxesIDs[7] = Component.Identifier.Axis.RZ;
    absAxesIDs[8] = Component.Identifier.Axis.Y;
    absAxesIDs[9] = Component.Identifier.Axis.SLIDER;
    absAxesIDs[10] = Component.Identifier.Axis.SLIDER;
    
    absAxesIDs[16] = Component.Identifier.Axis.POV;
    absAxesIDs[17] = Component.Identifier.Axis.POV;
    absAxesIDs[18] = Component.Identifier.Axis.POV;
    absAxesIDs[19] = Component.Identifier.Axis.POV;
    absAxesIDs[20] = Component.Identifier.Axis.POV;
    absAxesIDs[21] = Component.Identifier.Axis.POV;
    absAxesIDs[22] = Component.Identifier.Axis.POV;
    absAxesIDs[23] = Component.Identifier.Axis.POV;
    
    absAxesIDs[24] = null;
    absAxesIDs[25] = null;
    absAxesIDs[26] = null;
    absAxesIDs[27] = null;
    absAxesIDs[40] = null;
  }
  
  public static final Controller.Type guessButtonTrait(int button_code) {
    switch (button_code) {
    case 288: 
    case 289: 
    case 290: 
    case 291: 
    case 292: 
    case 293: 
    case 294: 
    case 295: 
    case 296: 
    case 297: 
    case 298: 
    case 299: 
    case 303: 
      return Controller.Type.STICK;
    case 304: 
    case 305: 
    case 306: 
    case 307: 
    case 308: 
    case 309: 
    case 310: 
    case 311: 
    case 312: 
    case 313: 
    case 314: 
    case 316: 
    case 317: 
    case 318: 
      return Controller.Type.GAMEPAD;
    case 256: 
    case 257: 
    case 258: 
    case 259: 
    case 260: 
    case 261: 
    case 262: 
    case 263: 
    case 264: 
    case 265: 
      return Controller.Type.UNKNOWN;
    case 272: 
    case 273: 
    case 274: 
    case 275: 
    case 276: 
      return Controller.Type.MOUSE;
    

    case 1: 
    case 2: 
    case 3: 
    case 4: 
    case 5: 
    case 6: 
    case 7: 
    case 8: 
    case 9: 
    case 10: 
    case 11: 
    case 12: 
    case 13: 
    case 14: 
    case 15: 
    case 16: 
    case 17: 
    case 18: 
    case 19: 
    case 20: 
    case 21: 
    case 22: 
    case 23: 
    case 24: 
    case 25: 
    case 26: 
    case 27: 
    case 28: 
    case 29: 
    case 30: 
    case 31: 
    case 32: 
    case 33: 
    case 34: 
    case 35: 
    case 36: 
    case 37: 
    case 38: 
    case 39: 
    case 40: 
    case 41: 
    case 42: 
    case 43: 
    case 44: 
    case 45: 
    case 46: 
    case 47: 
    case 48: 
    case 49: 
    case 50: 
    case 51: 
    case 52: 
    case 53: 
    case 54: 
    case 55: 
    case 56: 
    case 57: 
    case 58: 
    case 59: 
    case 60: 
    case 61: 
    case 62: 
    case 63: 
    case 64: 
    case 65: 
    case 66: 
    case 67: 
    case 68: 
    case 69: 
    case 70: 
    case 71: 
    case 72: 
    case 73: 
    case 74: 
    case 75: 
    case 76: 
    case 77: 
    case 78: 
    case 79: 
    case 80: 
    case 81: 
    case 82: 
    case 83: 
    case 85: 
    case 86: 
    case 87: 
    case 88: 
    case 89: 
    case 90: 
    case 91: 
    case 92: 
    case 93: 
    case 94: 
    case 95: 
    case 96: 
    case 97: 
    case 98: 
    case 99: 
    case 100: 
    case 101: 
    case 102: 
    case 103: 
    case 104: 
    case 105: 
    case 106: 
    case 107: 
    case 108: 
    case 109: 
    case 110: 
    case 111: 
    case 112: 
    case 113: 
    case 114: 
    case 115: 
    case 116: 
    case 117: 
    case 118: 
    case 119: 
    case 121: 
    case 122: 
    case 123: 
    case 124: 
    case 125: 
    case 126: 
    case 127: 
    case 128: 
    case 129: 
    case 130: 
    case 131: 
    case 132: 
    case 133: 
    case 134: 
    case 135: 
    case 136: 
    case 137: 
    case 138: 
    case 139: 
    case 140: 
    case 141: 
    case 142: 
    case 143: 
    case 144: 
    case 145: 
    case 146: 
    case 147: 
    case 148: 
    case 149: 
    case 150: 
    case 151: 
    case 152: 
    case 153: 
    case 154: 
    case 155: 
    case 156: 
    case 157: 
    case 158: 
    case 159: 
    case 160: 
    case 161: 
    case 162: 
    case 163: 
    case 164: 
    case 165: 
    case 166: 
    case 167: 
    case 168: 
    case 169: 
    case 170: 
    case 171: 
    case 172: 
    case 173: 
    case 174: 
    case 175: 
    case 176: 
    case 177: 
    case 178: 
    case 179: 
    case 180: 
    case 183: 
    case 184: 
    case 185: 
    case 186: 
    case 187: 
    case 188: 
    case 189: 
    case 190: 
    case 191: 
    case 192: 
    case 193: 
    case 194: 
    case 200: 
    case 201: 
    case 202: 
    case 203: 
    case 205: 
    case 206: 
    case 207: 
    case 208: 
    case 209: 
    case 210: 
    case 211: 
    case 212: 
    case 213: 
    case 214: 
    case 215: 
    case 216: 
    case 217: 
    case 218: 
    case 219: 
    case 220: 
    case 221: 
    case 222: 
    case 223: 
    case 224: 
    case 225: 
    case 226: 
    case 227: 
    case 228: 
    case 229: 
    case 230: 
    case 352: 
    case 353: 
    case 354: 
    case 355: 
    case 356: 
    case 357: 
    case 358: 
    case 359: 
    case 360: 
    case 361: 
    case 362: 
    case 363: 
    case 364: 
    case 365: 
    case 366: 
    case 367: 
    case 368: 
    case 369: 
    case 370: 
    case 371: 
    case 372: 
    case 373: 
    case 374: 
    case 375: 
    case 376: 
    case 377: 
    case 378: 
    case 379: 
    case 380: 
    case 381: 
    case 382: 
    case 383: 
    case 384: 
    case 385: 
    case 386: 
    case 387: 
    case 388: 
    case 389: 
    case 390: 
    case 391: 
    case 392: 
    case 393: 
    case 394: 
    case 395: 
    case 396: 
    case 397: 
    case 398: 
    case 399: 
    case 400: 
    case 401: 
    case 402: 
    case 403: 
    case 404: 
    case 405: 
    case 406: 
    case 407: 
    case 408: 
    case 409: 
    case 410: 
    case 411: 
    case 412: 
    case 413: 
    case 414: 
    case 415: 
    case 448: 
    case 449: 
    case 450: 
    case 451: 
    case 464: 
    case 465: 
    case 466: 
    case 467: 
    case 468: 
    case 469: 
    case 470: 
    case 471: 
    case 472: 
    case 473: 
    case 474: 
    case 475: 
    case 476: 
    case 477: 
    case 478: 
    case 479: 
    case 480: 
    case 481: 
    case 482: 
    case 483: 
    case 484: 
      return Controller.Type.KEYBOARD;
    }
    return Controller.Type.UNKNOWN;
  }
  





  public static Controller.PortType getPortType(int nativeid)
  {
    switch (nativeid) {
    case 20: 
      return Controller.PortType.GAME;
    case 17: 
      return Controller.PortType.I8042;
    case 21: 
      return Controller.PortType.PARALLEL;
    case 19: 
      return Controller.PortType.SERIAL;
    case 3: 
      return Controller.PortType.USB;
    }
    return Controller.PortType.UNKNOWN;
  }
  




  public static Component.Identifier getRelAxisID(int nativeID)
  {
    Component.Identifier retval = null;
    try {
      retval = INSTANCErelAxesIDs[nativeID];
    } catch (ArrayIndexOutOfBoundsException e) {
      log.warning("INSTANCE.relAxesIDis only " + INSTANCErelAxesIDs.length + " long, so " + nativeID + " not contained");
    }
    
    if (retval == null) {
      retval = Component.Identifier.Axis.SLIDER_VELOCITY;
    }
    return retval;
  }
  



  public static Component.Identifier getAbsAxisID(int nativeID)
  {
    Component.Identifier retval = null;
    try {
      retval = INSTANCEabsAxesIDs[nativeID];
    } catch (ArrayIndexOutOfBoundsException e) {
      log.warning("INSTANCE.absAxesIDs is only " + INSTANCEabsAxesIDs.length + " long, so " + nativeID + " not contained");
    }
    
    if (retval == null) {
      retval = Component.Identifier.Axis.SLIDER;
    }
    return retval;
  }
  



  public static Component.Identifier getButtonID(int nativeID)
  {
    Component.Identifier retval = null;
    try {
      retval = INSTANCEbuttonIDs[nativeID];
    } catch (ArrayIndexOutOfBoundsException e) {
      log.warning("INSTANCE.buttonIDs is only " + INSTANCEbuttonIDs.length + " long, so " + nativeID + " not contained");
    }
    
    if (retval == null) {
      retval = Component.Identifier.Key.UNKNOWN;
    }
    return retval;
  }
}

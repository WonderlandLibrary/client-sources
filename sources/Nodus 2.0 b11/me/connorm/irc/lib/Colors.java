/*   1:    */ package me.connorm.irc.lib;
/*   2:    */ 
/*   3:    */ public class Colors
/*   4:    */ {
/*   5:    */   public static final String NORMAL = "\017";
/*   6:    */   public static final String BOLD = "\002";
/*   7:    */   public static final String UNDERLINE = "\037";
/*   8:    */   public static final String REVERSE = "\026";
/*   9:    */   public static final String WHITE = "\00300";
/*  10:    */   public static final String BLACK = "\00301";
/*  11:    */   public static final String DARK_BLUE = "\00302";
/*  12:    */   public static final String DARK_GREEN = "\00303";
/*  13:    */   public static final String RED = "\00304";
/*  14:    */   public static final String BROWN = "\00305";
/*  15:    */   public static final String PURPLE = "\00306";
/*  16:    */   public static final String OLIVE = "\00307";
/*  17:    */   public static final String YELLOW = "\00308";
/*  18:    */   public static final String GREEN = "\00309";
/*  19:    */   public static final String TEAL = "\00310";
/*  20:    */   public static final String CYAN = "\00311";
/*  21:    */   public static final String BLUE = "\00312";
/*  22:    */   public static final String MAGENTA = "\00313";
/*  23:    */   public static final String DARK_GRAY = "\00314";
/*  24:    */   public static final String LIGHT_GRAY = "\00315";
/*  25:    */   
/*  26:    */   public static String removeColors(String line)
/*  27:    */   {
/*  28:194 */     int length = line.length();
/*  29:195 */     StringBuffer buffer = new StringBuffer();
/*  30:196 */     int i = 0;
/*  31:197 */     while (i < length)
/*  32:    */     {
/*  33:198 */       char ch = line.charAt(i);
/*  34:199 */       if (ch == '\003')
/*  35:    */       {
/*  36:200 */         i++;
/*  37:202 */         if (i < length)
/*  38:    */         {
/*  39:203 */           ch = line.charAt(i);
/*  40:204 */           if (Character.isDigit(ch))
/*  41:    */           {
/*  42:205 */             i++;
/*  43:206 */             if (i < length)
/*  44:    */             {
/*  45:207 */               ch = line.charAt(i);
/*  46:208 */               if (Character.isDigit(ch)) {
/*  47:209 */                 i++;
/*  48:    */               }
/*  49:    */             }
/*  50:213 */             if (i < length)
/*  51:    */             {
/*  52:214 */               ch = line.charAt(i);
/*  53:215 */               if (ch == ',')
/*  54:    */               {
/*  55:216 */                 i++;
/*  56:217 */                 if (i < length)
/*  57:    */                 {
/*  58:218 */                   ch = line.charAt(i);
/*  59:219 */                   if (Character.isDigit(ch))
/*  60:    */                   {
/*  61:220 */                     i++;
/*  62:221 */                     if (i < length)
/*  63:    */                     {
/*  64:222 */                       ch = line.charAt(i);
/*  65:223 */                       if (Character.isDigit(ch)) {
/*  66:224 */                         i++;
/*  67:    */                       }
/*  68:    */                     }
/*  69:    */                   }
/*  70:    */                   else
/*  71:    */                   {
/*  72:230 */                     i--;
/*  73:    */                   }
/*  74:    */                 }
/*  75:    */                 else
/*  76:    */                 {
/*  77:235 */                   i--;
/*  78:    */                 }
/*  79:    */               }
/*  80:    */             }
/*  81:    */           }
/*  82:    */         }
/*  83:    */       }
/*  84:242 */       else if (ch == '\017')
/*  85:    */       {
/*  86:243 */         i++;
/*  87:    */       }
/*  88:    */       else
/*  89:    */       {
/*  90:246 */         buffer.append(ch);
/*  91:247 */         i++;
/*  92:    */       }
/*  93:    */     }
/*  94:250 */     return buffer.toString();
/*  95:    */   }
/*  96:    */   
/*  97:    */   public static String removeFormatting(String line)
/*  98:    */   {
/*  99:264 */     int length = line.length();
/* 100:265 */     StringBuffer buffer = new StringBuffer();
/* 101:266 */     for (int i = 0; i < length; i++)
/* 102:    */     {
/* 103:267 */       char ch = line.charAt(i);
/* 104:268 */       if ((ch != '\017') && (ch != '\002') && (ch != '\037') && (ch != '\026')) {
/* 105:272 */         buffer.append(ch);
/* 106:    */       }
/* 107:    */     }
/* 108:275 */     return buffer.toString();
/* 109:    */   }
/* 110:    */   
/* 111:    */   public static String removeFormattingAndColors(String line)
/* 112:    */   {
/* 113:290 */     return removeFormatting(removeColors(line));
/* 114:    */   }
/* 115:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.irc.lib.Colors
 * JD-Core Version:    0.7.0.1
 */
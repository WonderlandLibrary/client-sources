/*   1:    */ package me.connorm.irc.lib;
/*   2:    */ 
/*   3:    */ public class User
/*   4:    */ {
/*   5:    */   private String _prefix;
/*   6:    */   private String _nick;
/*   7:    */   private String _lowerNick;
/*   8:    */   
/*   9:    */   User(String prefix, String nick)
/*  10:    */   {
/*  11: 39 */     this._prefix = prefix;
/*  12: 40 */     this._nick = nick;
/*  13: 41 */     this._lowerNick = nick.toLowerCase();
/*  14:    */   }
/*  15:    */   
/*  16:    */   public String getPrefix()
/*  17:    */   {
/*  18: 54 */     return this._prefix;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public boolean isOp()
/*  22:    */   {
/*  23: 67 */     return this._prefix.indexOf('@') >= 0;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public boolean hasVoice()
/*  27:    */   {
/*  28: 80 */     return this._prefix.indexOf('+') >= 0;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public String getNick()
/*  32:    */   {
/*  33: 90 */     return this._nick;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public String toString()
/*  37:    */   {
/*  38:101 */     return getPrefix() + getNick();
/*  39:    */   }
/*  40:    */   
/*  41:    */   public boolean equals(String nick)
/*  42:    */   {
/*  43:112 */     return nick.toLowerCase().equals(this._lowerNick);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public boolean equals(Object o)
/*  47:    */   {
/*  48:124 */     if ((o instanceof User))
/*  49:    */     {
/*  50:125 */       User other = (User)o;
/*  51:126 */       return other._lowerNick.equals(this._lowerNick);
/*  52:    */     }
/*  53:128 */     return false;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public int hashCode()
/*  57:    */   {
/*  58:138 */     return this._lowerNick.hashCode();
/*  59:    */   }
/*  60:    */   
/*  61:    */   public int compareTo(Object o)
/*  62:    */   {
/*  63:149 */     if ((o instanceof User))
/*  64:    */     {
/*  65:150 */       User other = (User)o;
/*  66:151 */       return other._lowerNick.compareTo(this._lowerNick);
/*  67:    */     }
/*  68:153 */     return -1;
/*  69:    */   }
/*  70:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.irc.lib.User
 * JD-Core Version:    0.7.0.1
 */
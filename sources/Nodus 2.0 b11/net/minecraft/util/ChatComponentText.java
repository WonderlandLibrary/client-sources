/*  1:   */ package net.minecraft.util;
/*  2:   */ 
/*  3:   */ import java.util.Iterator;
/*  4:   */ import java.util.List;
/*  5:   */ 
/*  6:   */ public class ChatComponentText
/*  7:   */   extends ChatComponentStyle
/*  8:   */ {
/*  9:   */   private final String text;
/* 10:   */   private static final String __OBFID = "CL_00001269";
/* 11:   */   
/* 12:   */   public ChatComponentText(String p_i45159_1_)
/* 13:   */   {
/* 14:12 */     this.text = p_i45159_1_;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public String getChatComponentText_TextValue()
/* 18:   */   {
/* 19:21 */     return this.text;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public String getUnformattedTextForChat()
/* 23:   */   {
/* 24:30 */     return this.text;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public ChatComponentText createCopy()
/* 28:   */   {
/* 29:38 */     ChatComponentText var1 = new ChatComponentText(this.text);
/* 30:39 */     var1.setChatStyle(getChatStyle().createShallowCopy());
/* 31:40 */     Iterator var2 = getSiblings().iterator();
/* 32:42 */     while (var2.hasNext())
/* 33:   */     {
/* 34:44 */       IChatComponent var3 = (IChatComponent)var2.next();
/* 35:45 */       var1.appendSibling(var3.createCopy());
/* 36:   */     }
/* 37:48 */     return var1;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public boolean equals(Object par1Obj)
/* 41:   */   {
/* 42:53 */     if (this == par1Obj) {
/* 43:55 */       return true;
/* 44:   */     }
/* 45:57 */     if (!(par1Obj instanceof ChatComponentText)) {
/* 46:59 */       return false;
/* 47:   */     }
/* 48:63 */     ChatComponentText var2 = (ChatComponentText)par1Obj;
/* 49:64 */     return (this.text.equals(var2.getChatComponentText_TextValue())) && (super.equals(par1Obj));
/* 50:   */   }
/* 51:   */   
/* 52:   */   public String toString()
/* 53:   */   {
/* 54:70 */     return "TextComponent{text='" + this.text + '\'' + ", siblings=" + this.siblings + ", style=" + getChatStyle() + '}';
/* 55:   */   }
/* 56:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.util.ChatComponentText
 * JD-Core Version:    0.7.0.1
 */
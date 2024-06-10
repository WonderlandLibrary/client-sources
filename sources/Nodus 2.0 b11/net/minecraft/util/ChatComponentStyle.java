/*   1:    */ package net.minecraft.util;
/*   2:    */ 
/*   3:    */ import com.google.common.base.Function;
/*   4:    */ import com.google.common.collect.Iterators;
/*   5:    */ import com.google.common.collect.Lists;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.List;
/*   8:    */ 
/*   9:    */ public abstract class ChatComponentStyle
/*  10:    */   implements IChatComponent
/*  11:    */ {
/*  12: 15 */   protected List siblings = Lists.newArrayList();
/*  13:    */   private ChatStyle style;
/*  14:    */   private static final String __OBFID = "CL_00001257";
/*  15:    */   
/*  16:    */   public IChatComponent appendSibling(IChatComponent p_150257_1_)
/*  17:    */   {
/*  18: 24 */     p_150257_1_.getChatStyle().setParentStyle(getChatStyle());
/*  19: 25 */     this.siblings.add(p_150257_1_);
/*  20: 26 */     return this;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public List getSiblings()
/*  24:    */   {
/*  25: 34 */     return this.siblings;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public IChatComponent appendText(String p_150258_1_)
/*  29:    */   {
/*  30: 42 */     return appendSibling(new ChatComponentText(p_150258_1_));
/*  31:    */   }
/*  32:    */   
/*  33:    */   public IChatComponent setChatStyle(ChatStyle p_150255_1_)
/*  34:    */   {
/*  35: 47 */     this.style = p_150255_1_;
/*  36: 48 */     Iterator var2 = this.siblings.iterator();
/*  37: 50 */     while (var2.hasNext())
/*  38:    */     {
/*  39: 52 */       IChatComponent var3 = (IChatComponent)var2.next();
/*  40: 53 */       var3.getChatStyle().setParentStyle(getChatStyle());
/*  41:    */     }
/*  42: 56 */     return this;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public ChatStyle getChatStyle()
/*  46:    */   {
/*  47: 61 */     if (this.style == null)
/*  48:    */     {
/*  49: 63 */       this.style = new ChatStyle();
/*  50: 64 */       Iterator var1 = this.siblings.iterator();
/*  51: 66 */       while (var1.hasNext())
/*  52:    */       {
/*  53: 68 */         IChatComponent var2 = (IChatComponent)var1.next();
/*  54: 69 */         var2.getChatStyle().setParentStyle(this.style);
/*  55:    */       }
/*  56:    */     }
/*  57: 73 */     return this.style;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public Iterator iterator()
/*  61:    */   {
/*  62: 78 */     return Iterators.concat(Iterators.forArray(new ChatComponentStyle[] { this }), createDeepCopyIterator(this.siblings));
/*  63:    */   }
/*  64:    */   
/*  65:    */   public final String getUnformattedText()
/*  66:    */   {
/*  67: 87 */     StringBuilder var1 = new StringBuilder();
/*  68: 88 */     Iterator var2 = iterator();
/*  69: 90 */     while (var2.hasNext())
/*  70:    */     {
/*  71: 92 */       IChatComponent var3 = (IChatComponent)var2.next();
/*  72: 93 */       var1.append(var3.getUnformattedTextForChat());
/*  73:    */     }
/*  74: 96 */     return var1.toString();
/*  75:    */   }
/*  76:    */   
/*  77:    */   public final String getFormattedText()
/*  78:    */   {
/*  79:104 */     StringBuilder var1 = new StringBuilder();
/*  80:105 */     Iterator var2 = iterator();
/*  81:107 */     while (var2.hasNext())
/*  82:    */     {
/*  83:109 */       IChatComponent var3 = (IChatComponent)var2.next();
/*  84:110 */       var1.append(var3.getChatStyle().getFormattingCode());
/*  85:111 */       var1.append(var3.getUnformattedTextForChat());
/*  86:112 */       var1.append(EnumChatFormatting.RESET);
/*  87:    */     }
/*  88:115 */     return var1.toString();
/*  89:    */   }
/*  90:    */   
/*  91:    */   public static Iterator createDeepCopyIterator(Iterable p_150262_0_)
/*  92:    */   {
/*  93:124 */     Iterator var1 = Iterators.concat(Iterators.transform(p_150262_0_.iterator(), new Function()
/*  94:    */     {
/*  95:    */       private static final String __OBFID = "CL_00001258";
/*  96:    */       
/*  97:    */       public Iterator apply(IChatComponent p_150665_1_)
/*  98:    */       {
/*  99:129 */         return p_150665_1_.iterator();
/* 100:    */       }
/* 101:    */       
/* 102:    */       public Object apply(Object par1Obj)
/* 103:    */       {
/* 104:133 */         return apply((IChatComponent)par1Obj);
/* 105:    */       }
/* 106:135 */     }));
/* 107:136 */     var1 = Iterators.transform(var1, new Function()
/* 108:    */     {
/* 109:    */       private static final String __OBFID = "CL_00001259";
/* 110:    */       
/* 111:    */       public IChatComponent apply(IChatComponent p_150666_1_)
/* 112:    */       {
/* 113:141 */         IChatComponent var2 = p_150666_1_.createCopy();
/* 114:142 */         var2.setChatStyle(var2.getChatStyle().createDeepCopy());
/* 115:143 */         return var2;
/* 116:    */       }
/* 117:    */       
/* 118:    */       public Object apply(Object par1Obj)
/* 119:    */       {
/* 120:147 */         return apply((IChatComponent)par1Obj);
/* 121:    */       }
/* 122:149 */     });
/* 123:150 */     return var1;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public boolean equals(Object par1Obj)
/* 127:    */   {
/* 128:155 */     if (this == par1Obj) {
/* 129:157 */       return true;
/* 130:    */     }
/* 131:159 */     if (!(par1Obj instanceof ChatComponentStyle)) {
/* 132:161 */       return false;
/* 133:    */     }
/* 134:165 */     ChatComponentStyle var2 = (ChatComponentStyle)par1Obj;
/* 135:166 */     return (this.siblings.equals(var2.siblings)) && (getChatStyle().equals(var2.getChatStyle()));
/* 136:    */   }
/* 137:    */   
/* 138:    */   public int hashCode()
/* 139:    */   {
/* 140:172 */     return 31 * this.style.hashCode() + this.siblings.hashCode();
/* 141:    */   }
/* 142:    */   
/* 143:    */   public String toString()
/* 144:    */   {
/* 145:177 */     return "BaseComponent{style=" + this.style + ", siblings=" + this.siblings + '}';
/* 146:    */   }
/* 147:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.util.ChatComponentStyle
 * JD-Core Version:    0.7.0.1
 */
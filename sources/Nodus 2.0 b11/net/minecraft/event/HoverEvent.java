/*   1:    */ package net.minecraft.event;
/*   2:    */ 
/*   3:    */ import com.google.common.collect.Maps;
/*   4:    */ import java.util.Map;
/*   5:    */ import net.minecraft.util.IChatComponent;
/*   6:    */ 
/*   7:    */ public class HoverEvent
/*   8:    */ {
/*   9:    */   private final Action action;
/*  10:    */   private final IChatComponent value;
/*  11:    */   private static final String __OBFID = "CL_00001264";
/*  12:    */   
/*  13:    */   public HoverEvent(Action p_i45158_1_, IChatComponent p_i45158_2_)
/*  14:    */   {
/*  15: 15 */     this.action = p_i45158_1_;
/*  16: 16 */     this.value = p_i45158_2_;
/*  17:    */   }
/*  18:    */   
/*  19:    */   public Action getAction()
/*  20:    */   {
/*  21: 24 */     return this.action;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public IChatComponent getValue()
/*  25:    */   {
/*  26: 33 */     return this.value;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public boolean equals(Object par1Obj)
/*  30:    */   {
/*  31: 38 */     if (this == par1Obj) {
/*  32: 40 */       return true;
/*  33:    */     }
/*  34: 42 */     if ((par1Obj != null) && (getClass() == par1Obj.getClass()))
/*  35:    */     {
/*  36: 44 */       HoverEvent var2 = (HoverEvent)par1Obj;
/*  37: 46 */       if (this.action != var2.action) {
/*  38: 48 */         return false;
/*  39:    */       }
/*  40: 52 */       if (this.value != null)
/*  41:    */       {
/*  42: 54 */         if (!this.value.equals(var2.value)) {
/*  43: 56 */           return false;
/*  44:    */         }
/*  45:    */       }
/*  46: 59 */       else if (var2.value != null) {
/*  47: 61 */         return false;
/*  48:    */       }
/*  49: 64 */       return true;
/*  50:    */     }
/*  51: 69 */     return false;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public String toString()
/*  55:    */   {
/*  56: 75 */     return "HoverEvent{action=" + this.action + ", value='" + this.value + '\'' + '}';
/*  57:    */   }
/*  58:    */   
/*  59:    */   public int hashCode()
/*  60:    */   {
/*  61: 80 */     int var1 = this.action.hashCode();
/*  62: 81 */     var1 = 31 * var1 + (this.value != null ? this.value.hashCode() : 0);
/*  63: 82 */     return var1;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public static enum Action
/*  67:    */   {
/*  68: 87 */     SHOW_TEXT("SHOW_TEXT", 0, "show_text", true),  SHOW_ACHIEVEMENT("SHOW_ACHIEVEMENT", 1, "show_achievement", true),  SHOW_ITEM("SHOW_ITEM", 2, "show_item", true);
/*  69:    */     
/*  70:    */     private static final Map nameMapping;
/*  71:    */     private final boolean allowedInChat;
/*  72:    */     private final String canonicalName;
/*  73:    */     private static final Action[] $VALUES;
/*  74:    */     private static final String __OBFID = "CL_00001265";
/*  75:    */     
/*  76:    */     private Action(String p_i45157_1_, int p_i45157_2_, String p_i45157_3_, boolean p_i45157_4_)
/*  77:    */     {
/*  78: 99 */       this.canonicalName = p_i45157_3_;
/*  79:100 */       this.allowedInChat = p_i45157_4_;
/*  80:    */     }
/*  81:    */     
/*  82:    */     public boolean shouldAllowInChat()
/*  83:    */     {
/*  84:105 */       return this.allowedInChat;
/*  85:    */     }
/*  86:    */     
/*  87:    */     public String getCanonicalName()
/*  88:    */     {
/*  89:110 */       return this.canonicalName;
/*  90:    */     }
/*  91:    */     
/*  92:    */     public static Action getValueByCanonicalName(String p_150684_0_)
/*  93:    */     {
/*  94:115 */       return (Action)nameMapping.get(p_150684_0_);
/*  95:    */     }
/*  96:    */     
/*  97:    */     static
/*  98:    */     {
/*  99: 90 */       nameMapping = Maps.newHashMap();
/* 100:    */       
/* 101:    */ 
/* 102:    */ 
/* 103: 94 */       $VALUES = new Action[] { SHOW_TEXT, SHOW_ACHIEVEMENT, SHOW_ITEM };
/* 104:    */       
/* 105:    */ 
/* 106:    */ 
/* 107:    */ 
/* 108:    */ 
/* 109:    */ 
/* 110:    */ 
/* 111:    */ 
/* 112:    */ 
/* 113:    */ 
/* 114:    */ 
/* 115:    */ 
/* 116:    */ 
/* 117:    */ 
/* 118:    */ 
/* 119:    */ 
/* 120:    */ 
/* 121:    */ 
/* 122:    */ 
/* 123:    */ 
/* 124:    */ 
/* 125:    */ 
/* 126:    */ 
/* 127:    */ 
/* 128:119 */       Action[] var0 = values();
/* 129:120 */       int var1 = var0.length;
/* 130:122 */       for (int var2 = 0; var2 < var1; var2++)
/* 131:    */       {
/* 132:124 */         Action var3 = var0[var2];
/* 133:125 */         nameMapping.put(var3.getCanonicalName(), var3);
/* 134:    */       }
/* 135:    */     }
/* 136:    */   }
/* 137:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.event.HoverEvent
 * JD-Core Version:    0.7.0.1
 */
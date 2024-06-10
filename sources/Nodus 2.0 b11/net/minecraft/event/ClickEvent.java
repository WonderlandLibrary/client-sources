/*   1:    */ package net.minecraft.event;
/*   2:    */ 
/*   3:    */ import com.google.common.collect.Maps;
/*   4:    */ import java.util.Map;
/*   5:    */ 
/*   6:    */ public class ClickEvent
/*   7:    */ {
/*   8:    */   private final Action action;
/*   9:    */   private final String value;
/*  10:    */   private static final String __OBFID = "CL_00001260";
/*  11:    */   
/*  12:    */   public ClickEvent(Action p_i45156_1_, String p_i45156_2_)
/*  13:    */   {
/*  14: 14 */     this.action = p_i45156_1_;
/*  15: 15 */     this.value = p_i45156_2_;
/*  16:    */   }
/*  17:    */   
/*  18:    */   public Action getAction()
/*  19:    */   {
/*  20: 23 */     return this.action;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public String getValue()
/*  24:    */   {
/*  25: 32 */     return this.value;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public boolean equals(Object par1Obj)
/*  29:    */   {
/*  30: 37 */     if (this == par1Obj) {
/*  31: 39 */       return true;
/*  32:    */     }
/*  33: 41 */     if ((par1Obj != null) && (getClass() == par1Obj.getClass()))
/*  34:    */     {
/*  35: 43 */       ClickEvent var2 = (ClickEvent)par1Obj;
/*  36: 45 */       if (this.action != var2.action) {
/*  37: 47 */         return false;
/*  38:    */       }
/*  39: 51 */       if (this.value != null)
/*  40:    */       {
/*  41: 53 */         if (!this.value.equals(var2.value)) {
/*  42: 55 */           return false;
/*  43:    */         }
/*  44:    */       }
/*  45: 58 */       else if (var2.value != null) {
/*  46: 60 */         return false;
/*  47:    */       }
/*  48: 63 */       return true;
/*  49:    */     }
/*  50: 68 */     return false;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public String toString()
/*  54:    */   {
/*  55: 74 */     return "ClickEvent{action=" + this.action + ", value='" + this.value + '\'' + '}';
/*  56:    */   }
/*  57:    */   
/*  58:    */   public int hashCode()
/*  59:    */   {
/*  60: 79 */     int var1 = this.action.hashCode();
/*  61: 80 */     var1 = 31 * var1 + (this.value != null ? this.value.hashCode() : 0);
/*  62: 81 */     return var1;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public static enum Action
/*  66:    */   {
/*  67: 86 */     OPEN_URL("OPEN_URL", 0, "open_url", true),  OPEN_FILE("OPEN_FILE", 1, "open_file", false),  RUN_COMMAND("RUN_COMMAND", 2, "run_command", true),  SUGGEST_COMMAND("SUGGEST_COMMAND", 3, "suggest_command", true);
/*  68:    */     
/*  69:    */     private static final Map nameMapping;
/*  70:    */     private final boolean allowedInChat;
/*  71:    */     private final String canonicalName;
/*  72:    */     private static final Action[] $VALUES;
/*  73:    */     private static final String __OBFID = "CL_00001261";
/*  74:    */     
/*  75:    */     private Action(String p_i45155_1_, int p_i45155_2_, String p_i45155_3_, boolean p_i45155_4_)
/*  76:    */     {
/*  77: 99 */       this.canonicalName = p_i45155_3_;
/*  78:100 */       this.allowedInChat = p_i45155_4_;
/*  79:    */     }
/*  80:    */     
/*  81:    */     public boolean shouldAllowInChat()
/*  82:    */     {
/*  83:105 */       return this.allowedInChat;
/*  84:    */     }
/*  85:    */     
/*  86:    */     public String getCanonicalName()
/*  87:    */     {
/*  88:110 */       return this.canonicalName;
/*  89:    */     }
/*  90:    */     
/*  91:    */     public static Action getValueByCanonicalName(String p_150672_0_)
/*  92:    */     {
/*  93:115 */       return (Action)nameMapping.get(p_150672_0_);
/*  94:    */     }
/*  95:    */     
/*  96:    */     static
/*  97:    */     {
/*  98: 90 */       nameMapping = Maps.newHashMap();
/*  99:    */       
/* 100:    */ 
/* 101:    */ 
/* 102: 94 */       $VALUES = new Action[] { OPEN_URL, OPEN_FILE, RUN_COMMAND, SUGGEST_COMMAND };
/* 103:    */       
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
/* 127:119 */       Action[] var0 = values();
/* 128:120 */       int var1 = var0.length;
/* 129:122 */       for (int var2 = 0; var2 < var1; var2++)
/* 130:    */       {
/* 131:124 */         Action var3 = var0[var2];
/* 132:125 */         nameMapping.put(var3.getCanonicalName(), var3);
/* 133:    */       }
/* 134:    */     }
/* 135:    */   }
/* 136:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.event.ClickEvent
 * JD-Core Version:    0.7.0.1
 */
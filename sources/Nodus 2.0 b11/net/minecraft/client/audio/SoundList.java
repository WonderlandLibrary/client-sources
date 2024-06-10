/*   1:    */ package net.minecraft.client.audio;
/*   2:    */ 
/*   3:    */ import com.google.common.collect.Lists;
/*   4:    */ import java.util.List;
/*   5:    */ 
/*   6:    */ public class SoundList
/*   7:    */ {
/*   8:  8 */   private final List field_148577_a = Lists.newArrayList();
/*   9:    */   private boolean field_148575_b;
/*  10:    */   private SoundCategory field_148576_c;
/*  11:    */   private static final String __OBFID = "CL_00001121";
/*  12:    */   
/*  13:    */   public List func_148570_a()
/*  14:    */   {
/*  15: 15 */     return this.field_148577_a;
/*  16:    */   }
/*  17:    */   
/*  18:    */   public boolean func_148574_b()
/*  19:    */   {
/*  20: 20 */     return this.field_148575_b;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public void func_148572_a(boolean p_148572_1_)
/*  24:    */   {
/*  25: 25 */     this.field_148575_b = p_148572_1_;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public SoundCategory func_148573_c()
/*  29:    */   {
/*  30: 30 */     return this.field_148576_c;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void func_148571_a(SoundCategory p_148571_1_)
/*  34:    */   {
/*  35: 35 */     this.field_148576_c = p_148571_1_;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public static class SoundEntry
/*  39:    */   {
/*  40:    */     private String field_148569_a;
/*  41: 41 */     private float field_148567_b = 1.0F;
/*  42: 42 */     private float field_148568_c = 1.0F;
/*  43: 43 */     private int field_148565_d = 1;
/*  44:    */     private Type field_148566_e;
/*  45:    */     private boolean field_148564_f;
/*  46:    */     private static final String __OBFID = "CL_00001122";
/*  47:    */     
/*  48:    */     public SoundEntry()
/*  49:    */     {
/*  50: 50 */       this.field_148566_e = Type.FILE;
/*  51: 51 */       this.field_148564_f = false;
/*  52:    */     }
/*  53:    */     
/*  54:    */     public String func_148556_a()
/*  55:    */     {
/*  56: 56 */       return this.field_148569_a;
/*  57:    */     }
/*  58:    */     
/*  59:    */     public void func_148561_a(String p_148561_1_)
/*  60:    */     {
/*  61: 61 */       this.field_148569_a = p_148561_1_;
/*  62:    */     }
/*  63:    */     
/*  64:    */     public float func_148558_b()
/*  65:    */     {
/*  66: 66 */       return this.field_148567_b;
/*  67:    */     }
/*  68:    */     
/*  69:    */     public void func_148553_a(float p_148553_1_)
/*  70:    */     {
/*  71: 71 */       this.field_148567_b = p_148553_1_;
/*  72:    */     }
/*  73:    */     
/*  74:    */     public float func_148560_c()
/*  75:    */     {
/*  76: 76 */       return this.field_148568_c;
/*  77:    */     }
/*  78:    */     
/*  79:    */     public void func_148559_b(float p_148559_1_)
/*  80:    */     {
/*  81: 81 */       this.field_148568_c = p_148559_1_;
/*  82:    */     }
/*  83:    */     
/*  84:    */     public int func_148555_d()
/*  85:    */     {
/*  86: 86 */       return this.field_148565_d;
/*  87:    */     }
/*  88:    */     
/*  89:    */     public void func_148554_a(int p_148554_1_)
/*  90:    */     {
/*  91: 91 */       this.field_148565_d = p_148554_1_;
/*  92:    */     }
/*  93:    */     
/*  94:    */     public Type func_148563_e()
/*  95:    */     {
/*  96: 96 */       return this.field_148566_e;
/*  97:    */     }
/*  98:    */     
/*  99:    */     public void func_148562_a(Type p_148562_1_)
/* 100:    */     {
/* 101:101 */       this.field_148566_e = p_148562_1_;
/* 102:    */     }
/* 103:    */     
/* 104:    */     public boolean func_148552_f()
/* 105:    */     {
/* 106:106 */       return this.field_148564_f;
/* 107:    */     }
/* 108:    */     
/* 109:    */     public void func_148557_a(boolean p_148557_1_)
/* 110:    */     {
/* 111:111 */       this.field_148564_f = p_148557_1_;
/* 112:    */     }
/* 113:    */     
/* 114:    */     public static enum Type
/* 115:    */     {
/* 116:116 */       FILE("FILE", 0, "file"),  SOUND_EVENT("SOUND_EVENT", 1, "event");
/* 117:    */       
/* 118:    */       private final String field_148583_c;
/* 119:120 */       private static final Type[] $VALUES = { FILE, SOUND_EVENT };
/* 120:    */       private static final String __OBFID = "CL_00001123";
/* 121:    */       
/* 122:    */       private Type(String p_i45109_1_, int p_i45109_2_, String p_i45109_3_)
/* 123:    */       {
/* 124:125 */         this.field_148583_c = p_i45109_3_;
/* 125:    */       }
/* 126:    */       
/* 127:    */       public static Type func_148580_a(String p_148580_0_)
/* 128:    */       {
/* 129:130 */         Type[] var1 = values();
/* 130:131 */         int var2 = var1.length;
/* 131:133 */         for (int var3 = 0; var3 < var2; var3++)
/* 132:    */         {
/* 133:135 */           Type var4 = var1[var3];
/* 134:137 */           if (var4.field_148583_c.equals(p_148580_0_)) {
/* 135:139 */             return var4;
/* 136:    */           }
/* 137:    */         }
/* 138:143 */         return null;
/* 139:    */       }
/* 140:    */     }
/* 141:    */   }
/* 142:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.audio.SoundList
 * JD-Core Version:    0.7.0.1
 */
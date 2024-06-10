/*   1:    */ package net.minecraft.client.settings;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.HashSet;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Set;
/*   8:    */ import net.minecraft.client.resources.I18n;
/*   9:    */ import net.minecraft.util.IntHashMap;
/*  10:    */ 
/*  11:    */ public class KeyBinding
/*  12:    */   implements Comparable
/*  13:    */ {
/*  14: 13 */   private static final List keybindArray = new ArrayList();
/*  15: 14 */   private static final IntHashMap hash = new IntHashMap();
/*  16: 15 */   private static final Set keybindSet = new HashSet();
/*  17:    */   private final String keyDescription;
/*  18:    */   private final int keyCodeDefault;
/*  19:    */   private final String keyCategory;
/*  20:    */   private int keyCode;
/*  21:    */   public boolean pressed;
/*  22:    */   private int presses;
/*  23:    */   private static final String __OBFID = "CL_00000628";
/*  24:    */   
/*  25:    */   public static void onTick(int par0)
/*  26:    */   {
/*  27: 28 */     if (par0 != 0)
/*  28:    */     {
/*  29: 30 */       KeyBinding var1 = (KeyBinding)hash.lookup(par0);
/*  30: 32 */       if (var1 != null) {
/*  31: 34 */         var1.presses += 1;
/*  32:    */       }
/*  33:    */     }
/*  34:    */   }
/*  35:    */   
/*  36:    */   public static void setKeyBindState(int par0, boolean par1)
/*  37:    */   {
/*  38: 41 */     if (par0 != 0)
/*  39:    */     {
/*  40: 43 */       KeyBinding var2 = (KeyBinding)hash.lookup(par0);
/*  41: 45 */       if (var2 != null) {
/*  42: 47 */         var2.pressed = par1;
/*  43:    */       }
/*  44:    */     }
/*  45:    */   }
/*  46:    */   
/*  47:    */   public static void unPressAllKeys()
/*  48:    */   {
/*  49: 54 */     Iterator var0 = keybindArray.iterator();
/*  50: 56 */     while (var0.hasNext())
/*  51:    */     {
/*  52: 58 */       KeyBinding var1 = (KeyBinding)var0.next();
/*  53: 59 */       var1.unpressKey();
/*  54:    */     }
/*  55:    */   }
/*  56:    */   
/*  57:    */   public static void resetKeyBindingArrayAndHash()
/*  58:    */   {
/*  59: 65 */     hash.clearMap();
/*  60: 66 */     Iterator var0 = keybindArray.iterator();
/*  61: 68 */     while (var0.hasNext())
/*  62:    */     {
/*  63: 70 */       KeyBinding var1 = (KeyBinding)var0.next();
/*  64: 71 */       hash.addKey(var1.keyCode, var1);
/*  65:    */     }
/*  66:    */   }
/*  67:    */   
/*  68:    */   public static Set func_151467_c()
/*  69:    */   {
/*  70: 77 */     return keybindSet;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public KeyBinding(String p_i45001_1_, int p_i45001_2_, String p_i45001_3_)
/*  74:    */   {
/*  75: 82 */     this.keyDescription = p_i45001_1_;
/*  76: 83 */     this.keyCode = p_i45001_2_;
/*  77: 84 */     this.keyCodeDefault = p_i45001_2_;
/*  78: 85 */     this.keyCategory = p_i45001_3_;
/*  79: 86 */     keybindArray.add(this);
/*  80: 87 */     hash.addKey(p_i45001_2_, this);
/*  81: 88 */     keybindSet.add(p_i45001_3_);
/*  82:    */   }
/*  83:    */   
/*  84:    */   public boolean getIsKeyPressed()
/*  85:    */   {
/*  86: 93 */     return this.pressed;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public String getKeyCategory()
/*  90:    */   {
/*  91: 98 */     return this.keyCategory;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public boolean isPressed()
/*  95:    */   {
/*  96:103 */     if (this.presses == 0) {
/*  97:105 */       return false;
/*  98:    */     }
/*  99:109 */     this.presses -= 1;
/* 100:110 */     return true;
/* 101:    */   }
/* 102:    */   
/* 103:    */   private void unpressKey()
/* 104:    */   {
/* 105:116 */     this.presses = 0;
/* 106:117 */     this.pressed = false;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public String getKeyDescription()
/* 110:    */   {
/* 111:122 */     return this.keyDescription;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public int getKeyCodeDefault()
/* 115:    */   {
/* 116:127 */     return this.keyCodeDefault;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public int getKeyCode()
/* 120:    */   {
/* 121:132 */     return this.keyCode;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void setKeyCode(int p_151462_1_)
/* 125:    */   {
/* 126:137 */     this.keyCode = p_151462_1_;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public int compareTo(KeyBinding p_151465_1_)
/* 130:    */   {
/* 131:142 */     int var2 = I18n.format(this.keyCategory, new Object[0]).compareTo(I18n.format(p_151465_1_.keyCategory, new Object[0]));
/* 132:144 */     if (var2 == 0) {
/* 133:146 */       var2 = I18n.format(this.keyDescription, new Object[0]).compareTo(I18n.format(p_151465_1_.keyDescription, new Object[0]));
/* 134:    */     }
/* 135:149 */     return var2;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public int compareTo(Object par1Obj)
/* 139:    */   {
/* 140:154 */     return compareTo((KeyBinding)par1Obj);
/* 141:    */   }
/* 142:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.settings.KeyBinding
 * JD-Core Version:    0.7.0.1
 */
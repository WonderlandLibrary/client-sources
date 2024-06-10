/*   1:    */ package net.minecraft.client.gui;
/*   2:    */ 
/*   3:    */ import java.util.Arrays;
/*   4:    */ import java.util.Set;
/*   5:    */ import me.connorm.Nodus.ui.NodusGuiButton;
/*   6:    */ import net.minecraft.client.Minecraft;
/*   7:    */ import net.minecraft.client.renderer.Tessellator;
/*   8:    */ import net.minecraft.client.resources.I18n;
/*   9:    */ import net.minecraft.client.settings.GameSettings;
/*  10:    */ import net.minecraft.client.settings.KeyBinding;
/*  11:    */ import net.minecraft.util.EnumChatFormatting;
/*  12:    */ import org.apache.commons.lang3.ArrayUtils;
/*  13:    */ 
/*  14:    */ public class GuiKeyBindingList
/*  15:    */   extends GuiListExtended
/*  16:    */ {
/*  17:    */   private final GuiControls field_148191_k;
/*  18:    */   private final Minecraft field_148189_l;
/*  19:    */   private final GuiListExtended.IGuiListEntry[] field_148190_m;
/*  20: 20 */   private int field_148188_n = 0;
/*  21:    */   private static final String __OBFID = "CL_00000732";
/*  22:    */   
/*  23:    */   public GuiKeyBindingList(GuiControls p_i45031_1_, Minecraft p_i45031_2_)
/*  24:    */   {
/*  25: 25 */     super(p_i45031_2_, GuiControls.width, GuiControls.height, 63, GuiControls.height - 32, 20);
/*  26: 26 */     this.field_148191_k = p_i45031_1_;
/*  27: 27 */     this.field_148189_l = p_i45031_2_;
/*  28: 28 */     KeyBinding[] var3 = (KeyBinding[])ArrayUtils.clone(p_i45031_2_.gameSettings.keyBindings);
/*  29: 29 */     this.field_148190_m = new GuiListExtended.IGuiListEntry[var3.length + KeyBinding.func_151467_c().size()];
/*  30: 30 */     Arrays.sort(var3);
/*  31: 31 */     int var4 = 0;
/*  32: 32 */     String var5 = null;
/*  33: 33 */     KeyBinding[] var6 = var3;
/*  34: 34 */     int var7 = var3.length;
/*  35: 36 */     for (int var8 = 0; var8 < var7; var8++)
/*  36:    */     {
/*  37: 38 */       KeyBinding var9 = var6[var8];
/*  38: 39 */       String var10 = var9.getKeyCategory();
/*  39: 41 */       if (!var10.equals(var5))
/*  40:    */       {
/*  41: 43 */         var5 = var10;
/*  42: 44 */         this.field_148190_m[(var4++)] = new CategoryEntry(var10);
/*  43:    */       }
/*  44: 47 */       int var11 = p_i45031_2_.fontRenderer.getStringWidth(I18n.format(var9.getKeyDescription(), new Object[0]));
/*  45: 49 */       if (var11 > this.field_148188_n) {
/*  46: 51 */         this.field_148188_n = var11;
/*  47:    */       }
/*  48: 54 */       this.field_148190_m[(var4++)] = new KeyEntry(var9, null);
/*  49:    */     }
/*  50:    */   }
/*  51:    */   
/*  52:    */   protected int getSize()
/*  53:    */   {
/*  54: 60 */     return this.field_148190_m.length;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public GuiListExtended.IGuiListEntry func_148180_b(int p_148180_1_)
/*  58:    */   {
/*  59: 65 */     return this.field_148190_m[p_148180_1_];
/*  60:    */   }
/*  61:    */   
/*  62:    */   protected int func_148137_d()
/*  63:    */   {
/*  64: 70 */     return super.func_148137_d() + 15;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public int func_148139_c()
/*  68:    */   {
/*  69: 75 */     return super.func_148139_c() + 32;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public class CategoryEntry
/*  73:    */     implements GuiListExtended.IGuiListEntry
/*  74:    */   {
/*  75:    */     private final String field_148285_b;
/*  76:    */     private final int field_148286_c;
/*  77:    */     private static final String __OBFID = "CL_00000734";
/*  78:    */     
/*  79:    */     public CategoryEntry(String p_i45028_2_)
/*  80:    */     {
/*  81: 86 */       this.field_148285_b = I18n.format(p_i45028_2_, new Object[0]);
/*  82: 87 */       this.field_148286_c = GuiKeyBindingList.this.field_148189_l.fontRenderer.getStringWidth(this.field_148285_b);
/*  83:    */     }
/*  84:    */     
/*  85:    */     public void func_148279_a(int p_148279_1_, int p_148279_2_, int p_148279_3_, int p_148279_4_, int p_148279_5_, Tessellator p_148279_6_, int p_148279_7_, int p_148279_8_, boolean p_148279_9_)
/*  86:    */     {
/*  87: 92 */       GuiKeyBindingList.this.field_148189_l.fontRenderer.drawString(this.field_148285_b, GuiScreen.width / 2 - this.field_148286_c / 2, p_148279_3_ + p_148279_5_ - GuiKeyBindingList.this.field_148189_l.fontRenderer.FONT_HEIGHT - 1, 16777215);
/*  88:    */     }
/*  89:    */     
/*  90:    */     public boolean func_148278_a(int p_148278_1_, int p_148278_2_, int p_148278_3_, int p_148278_4_, int p_148278_5_, int p_148278_6_)
/*  91:    */     {
/*  92: 97 */       return false;
/*  93:    */     }
/*  94:    */     
/*  95:    */     public void func_148277_b(int p_148277_1_, int p_148277_2_, int p_148277_3_, int p_148277_4_, int p_148277_5_, int p_148277_6_) {}
/*  96:    */   }
/*  97:    */   
/*  98:    */   public class KeyEntry
/*  99:    */     implements GuiListExtended.IGuiListEntry
/* 100:    */   {
/* 101:    */     private final KeyBinding field_148282_b;
/* 102:    */     private final String field_148283_c;
/* 103:    */     private final NodusGuiButton field_148280_d;
/* 104:    */     private final NodusGuiButton field_148281_e;
/* 105:    */     private static final String __OBFID = "CL_00000735";
/* 106:    */     
/* 107:    */     private KeyEntry(KeyBinding p_i45029_2_)
/* 108:    */     {
/* 109:113 */       this.field_148282_b = p_i45029_2_;
/* 110:114 */       this.field_148283_c = I18n.format(p_i45029_2_.getKeyDescription(), new Object[0]);
/* 111:115 */       this.field_148280_d = new NodusGuiButton(0, 0, 0, 75, 18, I18n.format(p_i45029_2_.getKeyDescription(), new Object[0]));
/* 112:116 */       this.field_148281_e = new NodusGuiButton(0, 0, 0, 50, 18, I18n.format("controls.reset", new Object[0]));
/* 113:    */     }
/* 114:    */     
/* 115:    */     public void func_148279_a(int p_148279_1_, int p_148279_2_, int p_148279_3_, int p_148279_4_, int p_148279_5_, Tessellator p_148279_6_, int p_148279_7_, int p_148279_8_, boolean p_148279_9_)
/* 116:    */     {
/* 117:121 */       boolean var10 = GuiKeyBindingList.this.field_148191_k.field_146491_f == this.field_148282_b;
/* 118:122 */       GuiKeyBindingList.this.field_148189_l.fontRenderer.drawString(this.field_148283_c, p_148279_2_ + 90 - GuiKeyBindingList.this.field_148188_n, p_148279_3_ + p_148279_5_ / 2 - GuiKeyBindingList.this.field_148189_l.fontRenderer.FONT_HEIGHT / 2, 16777215);
/* 119:123 */       this.field_148281_e.xPosition = (p_148279_2_ + 190);
/* 120:124 */       this.field_148281_e.yPosition = p_148279_3_;
/* 121:125 */       this.field_148281_e.enabled = (this.field_148282_b.getKeyCode() != this.field_148282_b.getKeyCodeDefault());
/* 122:126 */       this.field_148281_e.drawButton(GuiKeyBindingList.this.field_148189_l, p_148279_7_, p_148279_8_);
/* 123:127 */       this.field_148280_d.xPosition = (p_148279_2_ + 105);
/* 124:128 */       this.field_148280_d.yPosition = p_148279_3_;
/* 125:129 */       this.field_148280_d.displayString = GameSettings.getKeyDisplayString(this.field_148282_b.getKeyCode());
/* 126:130 */       boolean var11 = false;
/* 127:132 */       if (this.field_148282_b.getKeyCode() != 0)
/* 128:    */       {
/* 129:134 */         KeyBinding[] var12 = GuiKeyBindingList.this.field_148189_l.gameSettings.keyBindings;
/* 130:135 */         int var13 = var12.length;
/* 131:137 */         for (int var14 = 0; var14 < var13; var14++)
/* 132:    */         {
/* 133:139 */           KeyBinding var15 = var12[var14];
/* 134:141 */           if ((var15 != this.field_148282_b) && (var15.getKeyCode() == this.field_148282_b.getKeyCode()))
/* 135:    */           {
/* 136:143 */             var11 = true;
/* 137:144 */             break;
/* 138:    */           }
/* 139:    */         }
/* 140:    */       }
/* 141:149 */       if (var10) {
/* 142:151 */         this.field_148280_d.displayString = (EnumChatFormatting.WHITE + "> " + EnumChatFormatting.YELLOW + this.field_148280_d.displayString + EnumChatFormatting.WHITE + " <");
/* 143:153 */       } else if (var11) {
/* 144:155 */         this.field_148280_d.displayString = (EnumChatFormatting.RED + this.field_148280_d.displayString);
/* 145:    */       }
/* 146:158 */       this.field_148280_d.drawButton(GuiKeyBindingList.this.field_148189_l, p_148279_7_, p_148279_8_);
/* 147:    */     }
/* 148:    */     
/* 149:    */     public boolean func_148278_a(int p_148278_1_, int p_148278_2_, int p_148278_3_, int p_148278_4_, int p_148278_5_, int p_148278_6_)
/* 150:    */     {
/* 151:163 */       if (this.field_148280_d.mousePressed(GuiKeyBindingList.this.field_148189_l, p_148278_2_, p_148278_3_))
/* 152:    */       {
/* 153:165 */         GuiKeyBindingList.this.field_148191_k.field_146491_f = this.field_148282_b;
/* 154:166 */         return true;
/* 155:    */       }
/* 156:168 */       if (this.field_148281_e.mousePressed(GuiKeyBindingList.this.field_148189_l, p_148278_2_, p_148278_3_))
/* 157:    */       {
/* 158:170 */         GuiKeyBindingList.this.field_148189_l.gameSettings.setKeyCodeSave(this.field_148282_b, this.field_148282_b.getKeyCodeDefault());
/* 159:171 */         KeyBinding.resetKeyBindingArrayAndHash();
/* 160:172 */         return true;
/* 161:    */       }
/* 162:176 */       return false;
/* 163:    */     }
/* 164:    */     
/* 165:    */     public void func_148277_b(int p_148277_1_, int p_148277_2_, int p_148277_3_, int p_148277_4_, int p_148277_5_, int p_148277_6_)
/* 166:    */     {
/* 167:182 */       this.field_148280_d.mouseReleased(p_148277_2_, p_148277_3_);
/* 168:183 */       this.field_148281_e.mouseReleased(p_148277_2_, p_148277_3_);
/* 169:    */     }
/* 170:    */     
/* 171:    */     KeyEntry(KeyBinding p_i45030_2_, Object p_i45030_3_)
/* 172:    */     {
/* 173:188 */       this(p_i45030_2_);
/* 174:    */     }
/* 175:    */   }
/* 176:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.GuiKeyBindingList
 * JD-Core Version:    0.7.0.1
 */
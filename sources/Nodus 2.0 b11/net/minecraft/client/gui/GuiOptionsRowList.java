/*   1:    */ package net.minecraft.client.gui;
/*   2:    */ 
/*   3:    */ import com.google.common.collect.Lists;
/*   4:    */ import java.util.List;
/*   5:    */ import me.connorm.Nodus.ui.NodusGuiButton;
/*   6:    */ import net.minecraft.client.Minecraft;
/*   7:    */ import net.minecraft.client.renderer.Tessellator;
/*   8:    */ import net.minecraft.client.settings.GameSettings;
/*   9:    */ import net.minecraft.client.settings.GameSettings.Options;
/*  10:    */ 
/*  11:    */ public class GuiOptionsRowList
/*  12:    */   extends GuiListExtended
/*  13:    */ {
/*  14: 14 */   private final List field_148184_k = Lists.newArrayList();
/*  15:    */   private static final String __OBFID = "CL_00000677";
/*  16:    */   
/*  17:    */   public GuiOptionsRowList(Minecraft p_i45015_1_, int p_i45015_2_, int p_i45015_3_, int p_i45015_4_, int p_i45015_5_, int p_i45015_6_, GameSettings.Options... p_i45015_7_)
/*  18:    */   {
/*  19: 19 */     super(p_i45015_1_, p_i45015_2_, p_i45015_3_, p_i45015_4_, p_i45015_5_, p_i45015_6_);
/*  20: 20 */     this.field_148163_i = false;
/*  21: 22 */     for (int var8 = 0; var8 < p_i45015_7_.length; var8 += 2)
/*  22:    */     {
/*  23: 24 */       GameSettings.Options var9 = p_i45015_7_[var8];
/*  24: 25 */       GameSettings.Options var10 = var8 < p_i45015_7_.length - 1 ? p_i45015_7_[(var8 + 1)] : null;
/*  25: 26 */       NodusGuiButton var11 = func_148182_a(p_i45015_1_, p_i45015_2_ / 2 - 155, 0, var9);
/*  26: 27 */       NodusGuiButton var12 = func_148182_a(p_i45015_1_, p_i45015_2_ / 2 - 155 + 160, 0, var10);
/*  27: 28 */       this.field_148184_k.add(new Row(var11, var12));
/*  28:    */     }
/*  29:    */   }
/*  30:    */   
/*  31:    */   private NodusGuiButton func_148182_a(Minecraft p_148182_1_, int p_148182_2_, int p_148182_3_, GameSettings.Options p_148182_4_)
/*  32:    */   {
/*  33: 34 */     if (p_148182_4_ == null) {
/*  34: 36 */       return null;
/*  35:    */     }
/*  36: 40 */     int var5 = p_148182_4_.returnEnumOrdinal();
/*  37: 41 */     return p_148182_4_.getEnumFloat() ? new GuiOptionSlider(var5, p_148182_2_, p_148182_3_, p_148182_4_) : new GuiOptionButton(var5, p_148182_2_, p_148182_3_, p_148182_4_, p_148182_1_.gameSettings.getKeyBinding(p_148182_4_));
/*  38:    */   }
/*  39:    */   
/*  40:    */   public Row func_148180_b(int p_148183_1_)
/*  41:    */   {
/*  42: 47 */     return (Row)this.field_148184_k.get(p_148183_1_);
/*  43:    */   }
/*  44:    */   
/*  45:    */   protected int getSize()
/*  46:    */   {
/*  47: 52 */     return this.field_148184_k.size();
/*  48:    */   }
/*  49:    */   
/*  50:    */   public int func_148139_c()
/*  51:    */   {
/*  52: 57 */     return 400;
/*  53:    */   }
/*  54:    */   
/*  55:    */   protected int func_148137_d()
/*  56:    */   {
/*  57: 62 */     return super.func_148137_d() + 32;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public static class Row
/*  61:    */     implements GuiListExtended.IGuiListEntry
/*  62:    */   {
/*  63: 67 */     private final Minecraft field_148325_a = Minecraft.getMinecraft();
/*  64:    */     private final NodusGuiButton field_148323_b;
/*  65:    */     private final NodusGuiButton field_148324_c;
/*  66:    */     private static final String __OBFID = "CL_00000678";
/*  67:    */     
/*  68:    */     public Row(NodusGuiButton p_i45014_1_, NodusGuiButton p_i45014_2_)
/*  69:    */     {
/*  70: 74 */       this.field_148323_b = p_i45014_1_;
/*  71: 75 */       this.field_148324_c = p_i45014_2_;
/*  72:    */     }
/*  73:    */     
/*  74:    */     public void func_148279_a(int p_148279_1_, int p_148279_2_, int p_148279_3_, int p_148279_4_, int p_148279_5_, Tessellator p_148279_6_, int p_148279_7_, int p_148279_8_, boolean p_148279_9_)
/*  75:    */     {
/*  76: 80 */       if (this.field_148323_b != null)
/*  77:    */       {
/*  78: 82 */         this.field_148323_b.yPosition = p_148279_3_;
/*  79: 83 */         this.field_148323_b.drawButton(this.field_148325_a, p_148279_7_, p_148279_8_);
/*  80:    */       }
/*  81: 86 */       if (this.field_148324_c != null)
/*  82:    */       {
/*  83: 88 */         this.field_148324_c.yPosition = p_148279_3_;
/*  84: 89 */         this.field_148324_c.drawButton(this.field_148325_a, p_148279_7_, p_148279_8_);
/*  85:    */       }
/*  86:    */     }
/*  87:    */     
/*  88:    */     public boolean func_148278_a(int p_148278_1_, int p_148278_2_, int p_148278_3_, int p_148278_4_, int p_148278_5_, int p_148278_6_)
/*  89:    */     {
/*  90: 95 */       if (this.field_148323_b.mousePressed(this.field_148325_a, p_148278_2_, p_148278_3_))
/*  91:    */       {
/*  92: 97 */         if ((this.field_148323_b instanceof GuiOptionButton))
/*  93:    */         {
/*  94: 99 */           this.field_148325_a.gameSettings.setOptionValue(((GuiOptionButton)this.field_148323_b).func_146136_c(), 1);
/*  95:100 */           this.field_148323_b.displayString = this.field_148325_a.gameSettings.getKeyBinding(GameSettings.Options.getEnumOptions(this.field_148323_b.id));
/*  96:    */         }
/*  97:103 */         return true;
/*  98:    */       }
/*  99:105 */       if ((this.field_148324_c != null) && (this.field_148324_c.mousePressed(this.field_148325_a, p_148278_2_, p_148278_3_)))
/* 100:    */       {
/* 101:107 */         if ((this.field_148324_c instanceof GuiOptionButton))
/* 102:    */         {
/* 103:109 */           this.field_148325_a.gameSettings.setOptionValue(((GuiOptionButton)this.field_148324_c).func_146136_c(), 1);
/* 104:110 */           this.field_148324_c.displayString = this.field_148325_a.gameSettings.getKeyBinding(GameSettings.Options.getEnumOptions(this.field_148324_c.id));
/* 105:    */         }
/* 106:113 */         return true;
/* 107:    */       }
/* 108:117 */       return false;
/* 109:    */     }
/* 110:    */     
/* 111:    */     public void func_148277_b(int p_148277_1_, int p_148277_2_, int p_148277_3_, int p_148277_4_, int p_148277_5_, int p_148277_6_)
/* 112:    */     {
/* 113:123 */       if (this.field_148323_b != null) {
/* 114:125 */         this.field_148323_b.mouseReleased(p_148277_2_, p_148277_3_);
/* 115:    */       }
/* 116:128 */       if (this.field_148324_c != null) {
/* 117:130 */         this.field_148324_c.mouseReleased(p_148277_2_, p_148277_3_);
/* 118:    */       }
/* 119:    */     }
/* 120:    */   }
/* 121:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.GuiOptionsRowList
 * JD-Core Version:    0.7.0.1
 */
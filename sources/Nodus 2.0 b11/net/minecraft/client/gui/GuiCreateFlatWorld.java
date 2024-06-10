/*   1:    */ package net.minecraft.client.gui;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import me.connorm.Nodus.ui.NodusGuiButton;
/*   5:    */ import net.minecraft.client.Minecraft;
/*   6:    */ import net.minecraft.client.renderer.RenderHelper;
/*   7:    */ import net.minecraft.client.renderer.Tessellator;
/*   8:    */ import net.minecraft.client.renderer.entity.RenderItem;
/*   9:    */ import net.minecraft.client.renderer.texture.TextureManager;
/*  10:    */ import net.minecraft.client.resources.I18n;
/*  11:    */ import net.minecraft.init.Blocks;
/*  12:    */ import net.minecraft.item.Item;
/*  13:    */ import net.minecraft.item.ItemStack;
/*  14:    */ import net.minecraft.world.gen.FlatGeneratorInfo;
/*  15:    */ import net.minecraft.world.gen.FlatLayerInfo;
/*  16:    */ import org.lwjgl.opengl.GL11;
/*  17:    */ 
/*  18:    */ public class GuiCreateFlatWorld
/*  19:    */   extends GuiScreen
/*  20:    */ {
/*  21: 19 */   private static RenderItem field_146392_a = new RenderItem();
/*  22:    */   private final GuiCreateWorld field_146385_f;
/*  23: 21 */   private FlatGeneratorInfo field_146387_g = FlatGeneratorInfo.getDefaultFlatGenerator();
/*  24:    */   private String field_146393_h;
/*  25:    */   private String field_146394_i;
/*  26:    */   private String field_146391_r;
/*  27:    */   private Details field_146390_s;
/*  28:    */   private NodusGuiButton field_146389_t;
/*  29:    */   private NodusGuiButton field_146388_u;
/*  30:    */   private NodusGuiButton field_146386_v;
/*  31:    */   private static final String __OBFID = "CL_00000687";
/*  32:    */   
/*  33:    */   public GuiCreateFlatWorld(GuiCreateWorld par1GuiCreateWorld, String par2Str)
/*  34:    */   {
/*  35: 33 */     this.field_146385_f = par1GuiCreateWorld;
/*  36: 34 */     func_146383_a(par2Str);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public String func_146384_e()
/*  40:    */   {
/*  41: 39 */     return this.field_146387_g.toString();
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void func_146383_a(String p_146383_1_)
/*  45:    */   {
/*  46: 44 */     this.field_146387_g = FlatGeneratorInfo.createFlatGeneratorFromString(p_146383_1_);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void initGui()
/*  50:    */   {
/*  51: 52 */     this.buttonList.clear();
/*  52: 53 */     this.field_146393_h = I18n.format("createWorld.customize.flat.title", new Object[0]);
/*  53: 54 */     this.field_146394_i = I18n.format("createWorld.customize.flat.tile", new Object[0]);
/*  54: 55 */     this.field_146391_r = I18n.format("createWorld.customize.flat.height", new Object[0]);
/*  55: 56 */     this.field_146390_s = new Details();
/*  56: 57 */     this.buttonList.add(this.field_146389_t = new NodusGuiButton(2, width / 2 - 154, height - 52, 100, 20, I18n.format("createWorld.customize.flat.addLayer", new Object[0]) + " (NYI)"));
/*  57: 58 */     this.buttonList.add(this.field_146388_u = new NodusGuiButton(3, width / 2 - 50, height - 52, 100, 20, I18n.format("createWorld.customize.flat.editLayer", new Object[0]) + " (NYI)"));
/*  58: 59 */     this.buttonList.add(this.field_146386_v = new NodusGuiButton(4, width / 2 - 155, height - 52, 150, 20, I18n.format("createWorld.customize.flat.removeLayer", new Object[0])));
/*  59: 60 */     this.buttonList.add(new NodusGuiButton(0, width / 2 - 155, height - 28, 150, 20, I18n.format("gui.done", new Object[0])));
/*  60: 61 */     this.buttonList.add(new NodusGuiButton(5, width / 2 + 5, height - 52, 150, 20, I18n.format("createWorld.customize.presets", new Object[0])));
/*  61: 62 */     this.buttonList.add(new NodusGuiButton(1, width / 2 + 5, height - 28, 150, 20, I18n.format("gui.cancel", new Object[0])));
/*  62: 63 */     this.field_146389_t.field_146125_m = (this.field_146388_u.field_146125_m = 0);
/*  63: 64 */     this.field_146387_g.func_82645_d();
/*  64: 65 */     func_146375_g();
/*  65:    */   }
/*  66:    */   
/*  67:    */   protected void actionPerformed(NodusGuiButton p_146284_1_)
/*  68:    */   {
/*  69: 70 */     int var2 = this.field_146387_g.getFlatLayers().size() - this.field_146390_s.field_148228_k - 1;
/*  70: 72 */     if (p_146284_1_.id == 1)
/*  71:    */     {
/*  72: 74 */       this.mc.displayGuiScreen(this.field_146385_f);
/*  73:    */     }
/*  74: 76 */     else if (p_146284_1_.id == 0)
/*  75:    */     {
/*  76: 78 */       this.field_146385_f.field_146334_a = func_146384_e();
/*  77: 79 */       this.mc.displayGuiScreen(this.field_146385_f);
/*  78:    */     }
/*  79: 81 */     else if (p_146284_1_.id == 5)
/*  80:    */     {
/*  81: 83 */       this.mc.displayGuiScreen(new GuiFlatPresets(this));
/*  82:    */     }
/*  83: 85 */     else if ((p_146284_1_.id == 4) && (func_146382_i()))
/*  84:    */     {
/*  85: 87 */       this.field_146387_g.getFlatLayers().remove(var2);
/*  86: 88 */       this.field_146390_s.field_148228_k = Math.min(this.field_146390_s.field_148228_k, this.field_146387_g.getFlatLayers().size() - 1);
/*  87:    */     }
/*  88: 91 */     this.field_146387_g.func_82645_d();
/*  89: 92 */     func_146375_g();
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void func_146375_g()
/*  93:    */   {
/*  94: 97 */     boolean var1 = func_146382_i();
/*  95: 98 */     this.field_146386_v.enabled = var1;
/*  96: 99 */     this.field_146388_u.enabled = var1;
/*  97:100 */     this.field_146388_u.enabled = false;
/*  98:101 */     this.field_146389_t.enabled = false;
/*  99:    */   }
/* 100:    */   
/* 101:    */   private boolean func_146382_i()
/* 102:    */   {
/* 103:106 */     return (this.field_146390_s.field_148228_k > -1) && (this.field_146390_s.field_148228_k < this.field_146387_g.getFlatLayers().size());
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void drawScreen(int par1, int par2, float par3)
/* 107:    */   {
/* 108:114 */     drawDefaultBackground();
/* 109:115 */     this.field_146390_s.func_148128_a(par1, par2, par3);
/* 110:116 */     drawCenteredString(this.fontRendererObj, this.field_146393_h, width / 2, 8, 16777215);
/* 111:117 */     int var4 = width / 2 - 92 - 16;
/* 112:118 */     drawString(this.fontRendererObj, this.field_146394_i, var4, 32, 16777215);
/* 113:119 */     drawString(this.fontRendererObj, this.field_146391_r, var4 + 2 + 213 - this.fontRendererObj.getStringWidth(this.field_146391_r), 32, 16777215);
/* 114:120 */     super.drawScreen(par1, par2, par3);
/* 115:    */   }
/* 116:    */   
/* 117:    */   class Details
/* 118:    */     extends GuiSlot
/* 119:    */   {
/* 120:125 */     public int field_148228_k = -1;
/* 121:    */     private static final String __OBFID = "CL_00000688";
/* 122:    */     
/* 123:    */     public Details()
/* 124:    */     {
/* 125:130 */       super(GuiCreateFlatWorld.width, GuiCreateFlatWorld.height, 43, GuiCreateFlatWorld.height - 60, 24);
/* 126:    */     }
/* 127:    */     
/* 128:    */     private void func_148225_a(int p_148225_1_, int p_148225_2_, ItemStack p_148225_3_)
/* 129:    */     {
/* 130:135 */       func_148226_e(p_148225_1_ + 1, p_148225_2_ + 1);
/* 131:136 */       GL11.glEnable(32826);
/* 132:138 */       if (p_148225_3_ != null)
/* 133:    */       {
/* 134:140 */         RenderHelper.enableGUIStandardItemLighting();
/* 135:141 */         GuiCreateFlatWorld.field_146392_a.renderItemIntoGUI(GuiCreateFlatWorld.this.fontRendererObj, GuiCreateFlatWorld.this.mc.getTextureManager(), p_148225_3_, p_148225_1_ + 2, p_148225_2_ + 2);
/* 136:142 */         RenderHelper.disableStandardItemLighting();
/* 137:    */       }
/* 138:145 */       GL11.glDisable(32826);
/* 139:    */     }
/* 140:    */     
/* 141:    */     private void func_148226_e(int p_148226_1_, int p_148226_2_)
/* 142:    */     {
/* 143:150 */       func_148224_c(p_148226_1_, p_148226_2_, 0, 0);
/* 144:    */     }
/* 145:    */     
/* 146:    */     private void func_148224_c(int p_148224_1_, int p_148224_2_, int p_148224_3_, int p_148224_4_)
/* 147:    */     {
/* 148:155 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 149:156 */       GuiCreateFlatWorld.this.mc.getTextureManager().bindTexture(Gui.statIcons);
/* 150:157 */       float var5 = 0.007813F;
/* 151:158 */       float var6 = 0.007813F;
/* 152:159 */       boolean var7 = true;
/* 153:160 */       boolean var8 = true;
/* 154:161 */       Tessellator var9 = Tessellator.instance;
/* 155:162 */       var9.startDrawingQuads();
/* 156:163 */       var9.addVertexWithUV(p_148224_1_ + 0, p_148224_2_ + 18, GuiCreateFlatWorld.zLevel, (p_148224_3_ + 0) * 0.007813F, (p_148224_4_ + 18) * 0.007813F);
/* 157:164 */       var9.addVertexWithUV(p_148224_1_ + 18, p_148224_2_ + 18, GuiCreateFlatWorld.zLevel, (p_148224_3_ + 18) * 0.007813F, (p_148224_4_ + 18) * 0.007813F);
/* 158:165 */       var9.addVertexWithUV(p_148224_1_ + 18, p_148224_2_ + 0, GuiCreateFlatWorld.zLevel, (p_148224_3_ + 18) * 0.007813F, (p_148224_4_ + 0) * 0.007813F);
/* 159:166 */       var9.addVertexWithUV(p_148224_1_ + 0, p_148224_2_ + 0, GuiCreateFlatWorld.zLevel, (p_148224_3_ + 0) * 0.007813F, (p_148224_4_ + 0) * 0.007813F);
/* 160:167 */       var9.draw();
/* 161:    */     }
/* 162:    */     
/* 163:    */     protected int getSize()
/* 164:    */     {
/* 165:172 */       return GuiCreateFlatWorld.this.field_146387_g.getFlatLayers().size();
/* 166:    */     }
/* 167:    */     
/* 168:    */     protected void elementClicked(int p_148144_1_, boolean p_148144_2_, int p_148144_3_, int p_148144_4_)
/* 169:    */     {
/* 170:177 */       this.field_148228_k = p_148144_1_;
/* 171:178 */       GuiCreateFlatWorld.this.func_146375_g();
/* 172:    */     }
/* 173:    */     
/* 174:    */     protected boolean isSelected(int p_148131_1_)
/* 175:    */     {
/* 176:183 */       return p_148131_1_ == this.field_148228_k;
/* 177:    */     }
/* 178:    */     
/* 179:    */     protected void drawBackground() {}
/* 180:    */     
/* 181:    */     protected void drawSlot(int p_148126_1_, int p_148126_2_, int p_148126_3_, int p_148126_4_, Tessellator p_148126_5_, int p_148126_6_, int p_148126_7_)
/* 182:    */     {
/* 183:190 */       FlatLayerInfo var8 = (FlatLayerInfo)GuiCreateFlatWorld.this.field_146387_g.getFlatLayers().get(GuiCreateFlatWorld.this.field_146387_g.getFlatLayers().size() - p_148126_1_ - 1);
/* 184:191 */       Item var9 = Item.getItemFromBlock(var8.func_151536_b());
/* 185:192 */       ItemStack var10 = var8.func_151536_b() == Blocks.air ? null : new ItemStack(var9, 1, var8.getFillBlockMeta());
/* 186:193 */       String var11 = (var10 != null) && (var9 != null) ? var9.getItemStackDisplayName(var10) : "Air";
/* 187:194 */       func_148225_a(p_148126_2_, p_148126_3_, var10);
/* 188:195 */       GuiCreateFlatWorld.this.fontRendererObj.drawString(var11, p_148126_2_ + 18 + 5, p_148126_3_ + 3, 16777215);
/* 189:    */       String var12;
/* 190:    */       String var12;
/* 191:198 */       if (p_148126_1_ == 0)
/* 192:    */       {
/* 193:200 */         var12 = I18n.format("createWorld.customize.flat.layer.top", new Object[] { Integer.valueOf(var8.getLayerCount()) });
/* 194:    */       }
/* 195:    */       else
/* 196:    */       {
/* 197:    */         String var12;
/* 198:202 */         if (p_148126_1_ == GuiCreateFlatWorld.this.field_146387_g.getFlatLayers().size() - 1) {
/* 199:204 */           var12 = I18n.format("createWorld.customize.flat.layer.bottom", new Object[] { Integer.valueOf(var8.getLayerCount()) });
/* 200:    */         } else {
/* 201:208 */           var12 = I18n.format("createWorld.customize.flat.layer", new Object[] { Integer.valueOf(var8.getLayerCount()) });
/* 202:    */         }
/* 203:    */       }
/* 204:211 */       GuiCreateFlatWorld.this.fontRendererObj.drawString(var12, p_148126_2_ + 2 + 213 - GuiCreateFlatWorld.this.fontRendererObj.getStringWidth(var12), p_148126_3_ + 3, 16777215);
/* 205:    */     }
/* 206:    */     
/* 207:    */     protected int func_148137_d()
/* 208:    */     {
/* 209:216 */       return this.field_148155_a - 70;
/* 210:    */     }
/* 211:    */   }
/* 212:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.GuiCreateFlatWorld
 * JD-Core Version:    0.7.0.1
 */
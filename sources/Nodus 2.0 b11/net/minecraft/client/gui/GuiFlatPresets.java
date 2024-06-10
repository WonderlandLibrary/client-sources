/*   1:    */ package net.minecraft.client.gui;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Arrays;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Map;
/*   9:    */ import me.connorm.Nodus.ui.NodusGuiButton;
/*  10:    */ import net.minecraft.client.Minecraft;
/*  11:    */ import net.minecraft.client.renderer.RenderHelper;
/*  12:    */ import net.minecraft.client.renderer.Tessellator;
/*  13:    */ import net.minecraft.client.renderer.entity.RenderItem;
/*  14:    */ import net.minecraft.client.renderer.texture.TextureManager;
/*  15:    */ import net.minecraft.client.resources.I18n;
/*  16:    */ import net.minecraft.init.Blocks;
/*  17:    */ import net.minecraft.init.Items;
/*  18:    */ import net.minecraft.item.Item;
/*  19:    */ import net.minecraft.item.ItemStack;
/*  20:    */ import net.minecraft.world.biome.BiomeGenBase;
/*  21:    */ import net.minecraft.world.gen.FlatGeneratorInfo;
/*  22:    */ import net.minecraft.world.gen.FlatLayerInfo;
/*  23:    */ import org.lwjgl.input.Keyboard;
/*  24:    */ import org.lwjgl.opengl.GL11;
/*  25:    */ 
/*  26:    */ public class GuiFlatPresets
/*  27:    */   extends GuiScreen
/*  28:    */ {
/*  29: 28 */   private static RenderItem field_146437_a = new RenderItem();
/*  30: 29 */   private static final List field_146431_f = new ArrayList();
/*  31:    */   private final GuiCreateFlatWorld field_146432_g;
/*  32:    */   private String field_146438_h;
/*  33:    */   private String field_146439_i;
/*  34:    */   private String field_146436_r;
/*  35:    */   private ListSlot field_146435_s;
/*  36:    */   private NodusGuiButton field_146434_t;
/*  37:    */   private GuiTextField field_146433_u;
/*  38:    */   private static final String __OBFID = "CL_00000704";
/*  39:    */   
/*  40:    */   public GuiFlatPresets(GuiCreateFlatWorld par1GuiCreateFlatWorld)
/*  41:    */   {
/*  42: 41 */     this.field_146432_g = par1GuiCreateFlatWorld;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void initGui()
/*  46:    */   {
/*  47: 49 */     this.buttonList.clear();
/*  48: 50 */     Keyboard.enableRepeatEvents(true);
/*  49: 51 */     this.field_146438_h = I18n.format("createWorld.customize.presets.title", new Object[0]);
/*  50: 52 */     this.field_146439_i = I18n.format("createWorld.customize.presets.share", new Object[0]);
/*  51: 53 */     this.field_146436_r = I18n.format("createWorld.customize.presets.list", new Object[0]);
/*  52: 54 */     this.field_146433_u = new GuiTextField(this.fontRendererObj, 50, 40, width - 100, 20);
/*  53: 55 */     this.field_146435_s = new ListSlot();
/*  54: 56 */     this.field_146433_u.func_146203_f(1230);
/*  55: 57 */     this.field_146433_u.setText(this.field_146432_g.func_146384_e());
/*  56: 58 */     this.buttonList.add(this.field_146434_t = new NodusGuiButton(0, width / 2 - 155, height - 28, 150, 20, I18n.format("createWorld.customize.presets.select", new Object[0])));
/*  57: 59 */     this.buttonList.add(new NodusGuiButton(1, width / 2 + 5, height - 28, 150, 20, I18n.format("gui.cancel", new Object[0])));
/*  58: 60 */     func_146426_g();
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void onGuiClosed()
/*  62:    */   {
/*  63: 68 */     Keyboard.enableRepeatEvents(false);
/*  64:    */   }
/*  65:    */   
/*  66:    */   protected void mouseClicked(int par1, int par2, int par3)
/*  67:    */   {
/*  68: 76 */     this.field_146433_u.mouseClicked(par1, par2, par3);
/*  69: 77 */     super.mouseClicked(par1, par2, par3);
/*  70:    */   }
/*  71:    */   
/*  72:    */   protected void keyTyped(char par1, int par2)
/*  73:    */   {
/*  74: 85 */     if (!this.field_146433_u.textboxKeyTyped(par1, par2)) {
/*  75: 87 */       super.keyTyped(par1, par2);
/*  76:    */     }
/*  77:    */   }
/*  78:    */   
/*  79:    */   protected void actionPerformed(NodusGuiButton p_146284_1_)
/*  80:    */   {
/*  81: 93 */     if ((p_146284_1_.id == 0) && (func_146430_p()))
/*  82:    */     {
/*  83: 95 */       this.field_146432_g.func_146383_a(this.field_146433_u.getText());
/*  84: 96 */       this.mc.displayGuiScreen(this.field_146432_g);
/*  85:    */     }
/*  86: 98 */     else if (p_146284_1_.id == 1)
/*  87:    */     {
/*  88:100 */       this.mc.displayGuiScreen(this.field_146432_g);
/*  89:    */     }
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void drawScreen(int par1, int par2, float par3)
/*  93:    */   {
/*  94:109 */     drawDefaultBackground();
/*  95:110 */     this.field_146435_s.func_148128_a(par1, par2, par3);
/*  96:111 */     drawCenteredString(this.fontRendererObj, this.field_146438_h, width / 2, 8, 16777215);
/*  97:112 */     drawString(this.fontRendererObj, this.field_146439_i, 50, 30, 10526880);
/*  98:113 */     drawString(this.fontRendererObj, this.field_146436_r, 50, 70, 10526880);
/*  99:114 */     this.field_146433_u.drawTextBox();
/* 100:115 */     super.drawScreen(par1, par2, par3);
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void updateScreen()
/* 104:    */   {
/* 105:123 */     this.field_146433_u.updateCursorCounter();
/* 106:124 */     super.updateScreen();
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void func_146426_g()
/* 110:    */   {
/* 111:129 */     boolean var1 = func_146430_p();
/* 112:130 */     this.field_146434_t.enabled = var1;
/* 113:    */   }
/* 114:    */   
/* 115:    */   private boolean func_146430_p()
/* 116:    */   {
/* 117:135 */     return ((this.field_146435_s.field_148175_k > -1) && (this.field_146435_s.field_148175_k < field_146431_f.size())) || (this.field_146433_u.getText().length() > 1);
/* 118:    */   }
/* 119:    */   
/* 120:    */   private static void func_146425_a(String p_146425_0_, Item p_146425_1_, BiomeGenBase p_146425_2_, FlatLayerInfo... p_146425_3_)
/* 121:    */   {
/* 122:140 */     func_146421_a(p_146425_0_, p_146425_1_, p_146425_2_, null, p_146425_3_);
/* 123:    */   }
/* 124:    */   
/* 125:    */   private static void func_146421_a(String p_146421_0_, Item p_146421_1_, BiomeGenBase p_146421_2_, List p_146421_3_, FlatLayerInfo... p_146421_4_)
/* 126:    */   {
/* 127:145 */     FlatGeneratorInfo var5 = new FlatGeneratorInfo();
/* 128:147 */     for (int var6 = p_146421_4_.length - 1; var6 >= 0; var6--) {
/* 129:149 */       var5.getFlatLayers().add(p_146421_4_[var6]);
/* 130:    */     }
/* 131:152 */     var5.setBiome(p_146421_2_.biomeID);
/* 132:153 */     var5.func_82645_d();
/* 133:155 */     if (p_146421_3_ != null)
/* 134:    */     {
/* 135:157 */       Iterator var8 = p_146421_3_.iterator();
/* 136:159 */       while (var8.hasNext())
/* 137:    */       {
/* 138:161 */         String var7 = (String)var8.next();
/* 139:162 */         var5.getWorldFeatures().put(var7, new HashMap());
/* 140:    */       }
/* 141:    */     }
/* 142:166 */     field_146431_f.add(new LayerItem(p_146421_1_, p_146421_0_, var5.toString()));
/* 143:    */   }
/* 144:    */   
/* 145:    */   static
/* 146:    */   {
/* 147:171 */     func_146421_a("Classic Flat", Item.getItemFromBlock(Blocks.grass), BiomeGenBase.plains, Arrays.asList(new String[] { "village" }), new FlatLayerInfo[] { new FlatLayerInfo(1, Blocks.grass), new FlatLayerInfo(2, Blocks.dirt), new FlatLayerInfo(1, Blocks.bedrock) });
/* 148:172 */     func_146421_a("Tunnelers' Dream", Item.getItemFromBlock(Blocks.stone), BiomeGenBase.extremeHills, Arrays.asList(new String[] { "biome_1", "dungeon", "decoration", "stronghold", "mineshaft" }), new FlatLayerInfo[] { new FlatLayerInfo(1, Blocks.grass), new FlatLayerInfo(5, Blocks.dirt), new FlatLayerInfo(230, Blocks.stone), new FlatLayerInfo(1, Blocks.bedrock) });
/* 149:173 */     func_146421_a("Water World", Item.getItemFromBlock(Blocks.flowing_water), BiomeGenBase.plains, Arrays.asList(new String[] { "village", "biome_1" }), new FlatLayerInfo[] { new FlatLayerInfo(90, Blocks.water), new FlatLayerInfo(5, Blocks.sand), new FlatLayerInfo(5, Blocks.dirt), new FlatLayerInfo(5, Blocks.stone), new FlatLayerInfo(1, Blocks.bedrock) });
/* 150:174 */     func_146421_a("Overworld", Item.getItemFromBlock(Blocks.tallgrass), BiomeGenBase.plains, Arrays.asList(new String[] { "village", "biome_1", "decoration", "stronghold", "mineshaft", "dungeon", "lake", "lava_lake" }), new FlatLayerInfo[] { new FlatLayerInfo(1, Blocks.grass), new FlatLayerInfo(3, Blocks.dirt), new FlatLayerInfo(59, Blocks.stone), new FlatLayerInfo(1, Blocks.bedrock) });
/* 151:175 */     func_146421_a("Snowy Kingdom", Item.getItemFromBlock(Blocks.snow_layer), BiomeGenBase.icePlains, Arrays.asList(new String[] { "village", "biome_1" }), new FlatLayerInfo[] { new FlatLayerInfo(1, Blocks.snow_layer), new FlatLayerInfo(1, Blocks.grass), new FlatLayerInfo(3, Blocks.dirt), new FlatLayerInfo(59, Blocks.stone), new FlatLayerInfo(1, Blocks.bedrock) });
/* 152:176 */     func_146421_a("Bottomless Pit", Items.feather, BiomeGenBase.plains, Arrays.asList(new String[] { "village", "biome_1" }), new FlatLayerInfo[] { new FlatLayerInfo(1, Blocks.grass), new FlatLayerInfo(3, Blocks.dirt), new FlatLayerInfo(2, Blocks.cobblestone) });
/* 153:177 */     func_146421_a("Desert", Item.getItemFromBlock(Blocks.sand), BiomeGenBase.desert, Arrays.asList(new String[] { "village", "biome_1", "decoration", "stronghold", "mineshaft", "dungeon" }), new FlatLayerInfo[] { new FlatLayerInfo(8, Blocks.sand), new FlatLayerInfo(52, Blocks.sandstone), new FlatLayerInfo(3, Blocks.stone), new FlatLayerInfo(1, Blocks.bedrock) });
/* 154:178 */     func_146425_a("Redstone Ready", Items.redstone, BiomeGenBase.desert, new FlatLayerInfo[] { new FlatLayerInfo(52, Blocks.sandstone), new FlatLayerInfo(3, Blocks.stone), new FlatLayerInfo(1, Blocks.bedrock) });
/* 155:    */   }
/* 156:    */   
/* 157:    */   static class LayerItem
/* 158:    */   {
/* 159:    */     public Item field_148234_a;
/* 160:    */     public String field_148232_b;
/* 161:    */     public String field_148233_c;
/* 162:    */     private static final String __OBFID = "CL_00000705";
/* 163:    */     
/* 164:    */     public LayerItem(Item p_i45022_1_, String p_i45022_2_, String p_i45022_3_)
/* 165:    */     {
/* 166:190 */       this.field_148234_a = p_i45022_1_;
/* 167:191 */       this.field_148232_b = p_i45022_2_;
/* 168:192 */       this.field_148233_c = p_i45022_3_;
/* 169:    */     }
/* 170:    */   }
/* 171:    */   
/* 172:    */   class ListSlot
/* 173:    */     extends GuiSlot
/* 174:    */   {
/* 175:198 */     public int field_148175_k = -1;
/* 176:    */     private static final String __OBFID = "CL_00000706";
/* 177:    */     
/* 178:    */     public ListSlot()
/* 179:    */     {
/* 180:203 */       super(GuiFlatPresets.width, GuiFlatPresets.height, 80, GuiFlatPresets.height - 37, 24);
/* 181:    */     }
/* 182:    */     
/* 183:    */     private void func_148172_a(int p_148172_1_, int p_148172_2_, Item p_148172_3_)
/* 184:    */     {
/* 185:208 */       func_148173_e(p_148172_1_ + 1, p_148172_2_ + 1);
/* 186:209 */       GL11.glEnable(32826);
/* 187:210 */       RenderHelper.enableGUIStandardItemLighting();
/* 188:211 */       GuiFlatPresets.field_146437_a.renderItemIntoGUI(GuiFlatPresets.this.fontRendererObj, GuiFlatPresets.this.mc.getTextureManager(), new ItemStack(p_148172_3_, 1, 0), p_148172_1_ + 2, p_148172_2_ + 2);
/* 189:212 */       RenderHelper.disableStandardItemLighting();
/* 190:213 */       GL11.glDisable(32826);
/* 191:    */     }
/* 192:    */     
/* 193:    */     private void func_148173_e(int p_148173_1_, int p_148173_2_)
/* 194:    */     {
/* 195:218 */       func_148171_c(p_148173_1_, p_148173_2_, 0, 0);
/* 196:    */     }
/* 197:    */     
/* 198:    */     private void func_148171_c(int p_148171_1_, int p_148171_2_, int p_148171_3_, int p_148171_4_)
/* 199:    */     {
/* 200:223 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 201:224 */       GuiFlatPresets.this.mc.getTextureManager().bindTexture(Gui.statIcons);
/* 202:225 */       float var5 = 0.007813F;
/* 203:226 */       float var6 = 0.007813F;
/* 204:227 */       boolean var7 = true;
/* 205:228 */       boolean var8 = true;
/* 206:229 */       Tessellator var9 = Tessellator.instance;
/* 207:230 */       var9.startDrawingQuads();
/* 208:231 */       var9.addVertexWithUV(p_148171_1_ + 0, p_148171_2_ + 18, GuiFlatPresets.zLevel, (p_148171_3_ + 0) * 0.007813F, (p_148171_4_ + 18) * 0.007813F);
/* 209:232 */       var9.addVertexWithUV(p_148171_1_ + 18, p_148171_2_ + 18, GuiFlatPresets.zLevel, (p_148171_3_ + 18) * 0.007813F, (p_148171_4_ + 18) * 0.007813F);
/* 210:233 */       var9.addVertexWithUV(p_148171_1_ + 18, p_148171_2_ + 0, GuiFlatPresets.zLevel, (p_148171_3_ + 18) * 0.007813F, (p_148171_4_ + 0) * 0.007813F);
/* 211:234 */       var9.addVertexWithUV(p_148171_1_ + 0, p_148171_2_ + 0, GuiFlatPresets.zLevel, (p_148171_3_ + 0) * 0.007813F, (p_148171_4_ + 0) * 0.007813F);
/* 212:235 */       var9.draw();
/* 213:    */     }
/* 214:    */     
/* 215:    */     protected int getSize()
/* 216:    */     {
/* 217:240 */       return GuiFlatPresets.field_146431_f.size();
/* 218:    */     }
/* 219:    */     
/* 220:    */     protected void elementClicked(int p_148144_1_, boolean p_148144_2_, int p_148144_3_, int p_148144_4_)
/* 221:    */     {
/* 222:245 */       this.field_148175_k = p_148144_1_;
/* 223:246 */       GuiFlatPresets.this.func_146426_g();
/* 224:247 */       GuiFlatPresets.this.field_146433_u.setText(((GuiFlatPresets.LayerItem)GuiFlatPresets.field_146431_f.get(GuiFlatPresets.this.field_146435_s.field_148175_k)).field_148233_c);
/* 225:    */     }
/* 226:    */     
/* 227:    */     protected boolean isSelected(int p_148131_1_)
/* 228:    */     {
/* 229:252 */       return p_148131_1_ == this.field_148175_k;
/* 230:    */     }
/* 231:    */     
/* 232:    */     protected void drawBackground() {}
/* 233:    */     
/* 234:    */     protected void drawSlot(int p_148126_1_, int p_148126_2_, int p_148126_3_, int p_148126_4_, Tessellator p_148126_5_, int p_148126_6_, int p_148126_7_)
/* 235:    */     {
/* 236:259 */       GuiFlatPresets.LayerItem var8 = (GuiFlatPresets.LayerItem)GuiFlatPresets.field_146431_f.get(p_148126_1_);
/* 237:260 */       func_148172_a(p_148126_2_, p_148126_3_, var8.field_148234_a);
/* 238:261 */       GuiFlatPresets.this.fontRendererObj.drawString(var8.field_148232_b, p_148126_2_ + 18 + 5, p_148126_3_ + 6, 16777215);
/* 239:    */     }
/* 240:    */   }
/* 241:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.GuiFlatPresets
 * JD-Core Version:    0.7.0.1
 */
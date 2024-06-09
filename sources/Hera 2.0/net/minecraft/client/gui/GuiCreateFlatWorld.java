/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.world.gen.FlatGeneratorInfo;
/*     */ import net.minecraft.world.gen.FlatLayerInfo;
/*     */ 
/*     */ public class GuiCreateFlatWorld
/*     */   extends GuiScreen {
/*     */   private final GuiCreateWorld createWorldGui;
/*  22 */   private FlatGeneratorInfo theFlatGeneratorInfo = FlatGeneratorInfo.getDefaultFlatGenerator();
/*     */   
/*     */   private String flatWorldTitle;
/*     */   
/*     */   private String field_146394_i;
/*     */   
/*     */   private String field_146391_r;
/*     */   private Details createFlatWorldListSlotGui;
/*     */   private GuiButton field_146389_t;
/*     */   private GuiButton field_146388_u;
/*     */   private GuiButton field_146386_v;
/*     */   
/*     */   public GuiCreateFlatWorld(GuiCreateWorld createWorldGuiIn, String p_i1029_2_) {
/*  35 */     this.createWorldGui = createWorldGuiIn;
/*  36 */     func_146383_a(p_i1029_2_);
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_146384_e() {
/*  41 */     return this.theFlatGeneratorInfo.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_146383_a(String p_146383_1_) {
/*  46 */     this.theFlatGeneratorInfo = FlatGeneratorInfo.createFlatGeneratorFromString(p_146383_1_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  55 */     this.buttonList.clear();
/*  56 */     this.flatWorldTitle = I18n.format("createWorld.customize.flat.title", new Object[0]);
/*  57 */     this.field_146394_i = I18n.format("createWorld.customize.flat.tile", new Object[0]);
/*  58 */     this.field_146391_r = I18n.format("createWorld.customize.flat.height", new Object[0]);
/*  59 */     this.createFlatWorldListSlotGui = new Details();
/*  60 */     this.buttonList.add(this.field_146389_t = new GuiButton(2, this.width / 2 - 154, this.height - 52, 100, 20, String.valueOf(I18n.format("createWorld.customize.flat.addLayer", new Object[0])) + " (NYI)"));
/*  61 */     this.buttonList.add(this.field_146388_u = new GuiButton(3, this.width / 2 - 50, this.height - 52, 100, 20, String.valueOf(I18n.format("createWorld.customize.flat.editLayer", new Object[0])) + " (NYI)"));
/*  62 */     this.buttonList.add(this.field_146386_v = new GuiButton(4, this.width / 2 - 155, this.height - 52, 150, 20, I18n.format("createWorld.customize.flat.removeLayer", new Object[0])));
/*  63 */     this.buttonList.add(new GuiButton(0, this.width / 2 - 155, this.height - 28, 150, 20, I18n.format("gui.done", new Object[0])));
/*  64 */     this.buttonList.add(new GuiButton(5, this.width / 2 + 5, this.height - 52, 150, 20, I18n.format("createWorld.customize.presets", new Object[0])));
/*  65 */     this.buttonList.add(new GuiButton(1, this.width / 2 + 5, this.height - 28, 150, 20, I18n.format("gui.cancel", new Object[0])));
/*  66 */     this.field_146388_u.visible = false;
/*  67 */     this.theFlatGeneratorInfo.func_82645_d();
/*  68 */     func_146375_g();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleMouseInput() throws IOException {
/*  76 */     super.handleMouseInput();
/*  77 */     this.createFlatWorldListSlotGui.handleMouseInput();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/*  85 */     int i = this.theFlatGeneratorInfo.getFlatLayers().size() - this.createFlatWorldListSlotGui.field_148228_k - 1;
/*     */     
/*  87 */     if (button.id == 1) {
/*     */       
/*  89 */       this.mc.displayGuiScreen(this.createWorldGui);
/*     */     }
/*  91 */     else if (button.id == 0) {
/*     */       
/*  93 */       this.createWorldGui.chunkProviderSettingsJson = func_146384_e();
/*  94 */       this.mc.displayGuiScreen(this.createWorldGui);
/*     */     }
/*  96 */     else if (button.id == 5) {
/*     */       
/*  98 */       this.mc.displayGuiScreen(new GuiFlatPresets(this));
/*     */     }
/* 100 */     else if (button.id == 4 && func_146382_i()) {
/*     */       
/* 102 */       this.theFlatGeneratorInfo.getFlatLayers().remove(i);
/* 103 */       this.createFlatWorldListSlotGui.field_148228_k = Math.min(this.createFlatWorldListSlotGui.field_148228_k, this.theFlatGeneratorInfo.getFlatLayers().size() - 1);
/*     */     } 
/*     */     
/* 106 */     this.theFlatGeneratorInfo.func_82645_d();
/* 107 */     func_146375_g();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_146375_g() {
/* 112 */     boolean flag = func_146382_i();
/* 113 */     this.field_146386_v.enabled = flag;
/* 114 */     this.field_146388_u.enabled = flag;
/* 115 */     this.field_146388_u.enabled = false;
/* 116 */     this.field_146389_t.enabled = false;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean func_146382_i() {
/* 121 */     return (this.createFlatWorldListSlotGui.field_148228_k > -1 && this.createFlatWorldListSlotGui.field_148228_k < this.theFlatGeneratorInfo.getFlatLayers().size());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 129 */     drawDefaultBackground();
/* 130 */     this.createFlatWorldListSlotGui.drawScreen(mouseX, mouseY, partialTicks);
/* 131 */     drawCenteredString(this.fontRendererObj, this.flatWorldTitle, this.width / 2, 8, 16777215);
/* 132 */     int i = this.width / 2 - 92 - 16;
/* 133 */     drawString(this.fontRendererObj, this.field_146394_i, i, 32, 16777215);
/* 134 */     drawString(this.fontRendererObj, this.field_146391_r, i + 2 + 213 - this.fontRendererObj.getStringWidth(this.field_146391_r), 32, 16777215);
/* 135 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */   
/*     */   class Details
/*     */     extends GuiSlot {
/* 140 */     public int field_148228_k = -1;
/*     */ 
/*     */     
/*     */     public Details() {
/* 144 */       super(GuiCreateFlatWorld.this.mc, GuiCreateFlatWorld.this.width, GuiCreateFlatWorld.this.height, 43, GuiCreateFlatWorld.this.height - 60, 24);
/*     */     }
/*     */ 
/*     */     
/*     */     private void func_148225_a(int p_148225_1_, int p_148225_2_, ItemStack p_148225_3_) {
/* 149 */       func_148226_e(p_148225_1_ + 1, p_148225_2_ + 1);
/* 150 */       GlStateManager.enableRescaleNormal();
/*     */       
/* 152 */       if (p_148225_3_ != null && p_148225_3_.getItem() != null) {
/*     */         
/* 154 */         RenderHelper.enableGUIStandardItemLighting();
/* 155 */         GuiCreateFlatWorld.this.itemRender.renderItemIntoGUI(p_148225_3_, p_148225_1_ + 2, p_148225_2_ + 2);
/* 156 */         RenderHelper.disableStandardItemLighting();
/*     */       } 
/*     */       
/* 159 */       GlStateManager.disableRescaleNormal();
/*     */     }
/*     */ 
/*     */     
/*     */     private void func_148226_e(int p_148226_1_, int p_148226_2_) {
/* 164 */       func_148224_c(p_148226_1_, p_148226_2_, 0, 0);
/*     */     }
/*     */ 
/*     */     
/*     */     private void func_148224_c(int p_148224_1_, int p_148224_2_, int p_148224_3_, int p_148224_4_) {
/* 169 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 170 */       this.mc.getTextureManager().bindTexture(Gui.statIcons);
/* 171 */       float f = 0.0078125F;
/* 172 */       float f1 = 0.0078125F;
/* 173 */       int i = 18;
/* 174 */       int j = 18;
/* 175 */       Tessellator tessellator = Tessellator.getInstance();
/* 176 */       WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 177 */       worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 178 */       worldrenderer.pos((p_148224_1_ + 0), (p_148224_2_ + 18), GuiCreateFlatWorld.this.zLevel).tex(((p_148224_3_ + 0) * 0.0078125F), ((p_148224_4_ + 18) * 0.0078125F)).endVertex();
/* 179 */       worldrenderer.pos((p_148224_1_ + 18), (p_148224_2_ + 18), GuiCreateFlatWorld.this.zLevel).tex(((p_148224_3_ + 18) * 0.0078125F), ((p_148224_4_ + 18) * 0.0078125F)).endVertex();
/* 180 */       worldrenderer.pos((p_148224_1_ + 18), (p_148224_2_ + 0), GuiCreateFlatWorld.this.zLevel).tex(((p_148224_3_ + 18) * 0.0078125F), ((p_148224_4_ + 0) * 0.0078125F)).endVertex();
/* 181 */       worldrenderer.pos((p_148224_1_ + 0), (p_148224_2_ + 0), GuiCreateFlatWorld.this.zLevel).tex(((p_148224_3_ + 0) * 0.0078125F), ((p_148224_4_ + 0) * 0.0078125F)).endVertex();
/* 182 */       tessellator.draw();
/*     */     }
/*     */ 
/*     */     
/*     */     protected int getSize() {
/* 187 */       return GuiCreateFlatWorld.this.theFlatGeneratorInfo.getFlatLayers().size();
/*     */     }
/*     */ 
/*     */     
/*     */     protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {
/* 192 */       this.field_148228_k = slotIndex;
/* 193 */       GuiCreateFlatWorld.this.func_146375_g();
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean isSelected(int slotIndex) {
/* 198 */       return (slotIndex == this.field_148228_k);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void drawBackground() {}
/*     */ 
/*     */     
/*     */     protected void drawSlot(int entryID, int p_180791_2_, int p_180791_3_, int p_180791_4_, int mouseXIn, int mouseYIn) {
/*     */       String s1;
/* 207 */       FlatLayerInfo flatlayerinfo = GuiCreateFlatWorld.this.theFlatGeneratorInfo.getFlatLayers().get(GuiCreateFlatWorld.this.theFlatGeneratorInfo.getFlatLayers().size() - entryID - 1);
/* 208 */       IBlockState iblockstate = flatlayerinfo.func_175900_c();
/* 209 */       Block block = iblockstate.getBlock();
/* 210 */       Item item = Item.getItemFromBlock(block);
/* 211 */       ItemStack itemstack = (block != Blocks.air && item != null) ? new ItemStack(item, 1, block.getMetaFromState(iblockstate)) : null;
/* 212 */       String s = (itemstack == null) ? "Air" : item.getItemStackDisplayName(itemstack);
/*     */       
/* 214 */       if (item == null) {
/*     */         
/* 216 */         if (block != Blocks.water && block != Blocks.flowing_water) {
/*     */           
/* 218 */           if (block == Blocks.lava || block == Blocks.flowing_lava)
/*     */           {
/* 220 */             item = Items.lava_bucket;
/*     */           }
/*     */         }
/*     */         else {
/*     */           
/* 225 */           item = Items.water_bucket;
/*     */         } 
/*     */         
/* 228 */         if (item != null) {
/*     */           
/* 230 */           itemstack = new ItemStack(item, 1, block.getMetaFromState(iblockstate));
/* 231 */           s = block.getLocalizedName();
/*     */         } 
/*     */       } 
/*     */       
/* 235 */       func_148225_a(p_180791_2_, p_180791_3_, itemstack);
/* 236 */       GuiCreateFlatWorld.this.fontRendererObj.drawString(s, p_180791_2_ + 18 + 5, p_180791_3_ + 3, 16777215);
/*     */ 
/*     */       
/* 239 */       if (entryID == 0) {
/*     */         
/* 241 */         s1 = I18n.format("createWorld.customize.flat.layer.top", new Object[] { Integer.valueOf(flatlayerinfo.getLayerCount()) });
/*     */       }
/* 243 */       else if (entryID == GuiCreateFlatWorld.this.theFlatGeneratorInfo.getFlatLayers().size() - 1) {
/*     */         
/* 245 */         s1 = I18n.format("createWorld.customize.flat.layer.bottom", new Object[] { Integer.valueOf(flatlayerinfo.getLayerCount()) });
/*     */       }
/*     */       else {
/*     */         
/* 249 */         s1 = I18n.format("createWorld.customize.flat.layer", new Object[] { Integer.valueOf(flatlayerinfo.getLayerCount()) });
/*     */       } 
/*     */       
/* 252 */       GuiCreateFlatWorld.this.fontRendererObj.drawString(s1, p_180791_2_ + 2 + 213 - GuiCreateFlatWorld.this.fontRendererObj.getStringWidth(s1), p_180791_3_ + 3, 16777215);
/*     */     }
/*     */ 
/*     */     
/*     */     protected int getScrollBarX() {
/* 257 */       return this.width - 70;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\gui\GuiCreateFlatWorld.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
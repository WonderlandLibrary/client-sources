// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.gui;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.GlStateManager;
import java.io.IOException;
import org.lwjgl.input.Keyboard;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.ChunkProviderSettings;
import com.google.common.collect.Lists;
import java.util.List;

public class GuiScreenCustomizePresets extends GuiScreen
{
    private static final List field_175310_f;
    private ListPreset field_175311_g;
    private GuiButton field_175316_h;
    private GuiTextField field_175317_i;
    private GuiCustomizeWorldScreen field_175314_r;
    protected String field_175315_a;
    private String field_175313_s;
    private String field_175312_t;
    private static final String __OBFID = "CL_00001937";
    
    static {
        field_175310_f = Lists.newArrayList();
        ChunkProviderSettings.Factory var0 = ChunkProviderSettings.Factory.func_177865_a("{ \"coordinateScale\":684.412, \"heightScale\":684.412, \"upperLimitScale\":512.0, \"lowerLimitScale\":512.0, \"depthNoiseScaleX\":200.0, \"depthNoiseScaleZ\":200.0, \"depthNoiseScaleExponent\":0.5, \"mainNoiseScaleX\":5000.0, \"mainNoiseScaleY\":1000.0, \"mainNoiseScaleZ\":5000.0, \"baseSize\":8.5, \"stretchY\":8.0, \"biomeDepthWeight\":2.0, \"biomeDepthOffset\":0.5, \"biomeScaleWeight\":2.0, \"biomeScaleOffset\":0.375, \"useCaves\":true, \"useDungeons\":true, \"dungeonChance\":8, \"useStrongholds\":true, \"useVillages\":true, \"useMineShafts\":true, \"useTemples\":true, \"useRavines\":true, \"useWaterLakes\":true, \"waterLakeChance\":4, \"useLavaLakes\":true, \"lavaLakeChance\":80, \"useLavaOceans\":false, \"seaLevel\":255 }");
        ResourceLocation var2 = new ResourceLocation("textures/gui/presets/water.png");
        GuiScreenCustomizePresets.field_175310_f.add(new Info(I18n.format("createWorld.customize.custom.preset.waterWorld", new Object[0]), var2, var0));
        var0 = ChunkProviderSettings.Factory.func_177865_a("{\"coordinateScale\":3000.0, \"heightScale\":6000.0, \"upperLimitScale\":250.0, \"lowerLimitScale\":512.0, \"depthNoiseScaleX\":200.0, \"depthNoiseScaleZ\":200.0, \"depthNoiseScaleExponent\":0.5, \"mainNoiseScaleX\":80.0, \"mainNoiseScaleY\":160.0, \"mainNoiseScaleZ\":80.0, \"baseSize\":8.5, \"stretchY\":10.0, \"biomeDepthWeight\":1.0, \"biomeDepthOffset\":0.0, \"biomeScaleWeight\":1.0, \"biomeScaleOffset\":0.0, \"useCaves\":true, \"useDungeons\":true, \"dungeonChance\":8, \"useStrongholds\":true, \"useVillages\":true, \"useMineShafts\":true, \"useTemples\":true, \"useRavines\":true, \"useWaterLakes\":true, \"waterLakeChance\":4, \"useLavaLakes\":true, \"lavaLakeChance\":80, \"useLavaOceans\":false, \"seaLevel\":63 }");
        var2 = new ResourceLocation("textures/gui/presets/isles.png");
        GuiScreenCustomizePresets.field_175310_f.add(new Info(I18n.format("createWorld.customize.custom.preset.isleLand", new Object[0]), var2, var0));
        var0 = ChunkProviderSettings.Factory.func_177865_a("{\"coordinateScale\":684.412, \"heightScale\":684.412, \"upperLimitScale\":512.0, \"lowerLimitScale\":512.0, \"depthNoiseScaleX\":200.0, \"depthNoiseScaleZ\":200.0, \"depthNoiseScaleExponent\":0.5, \"mainNoiseScaleX\":5000.0, \"mainNoiseScaleY\":1000.0, \"mainNoiseScaleZ\":5000.0, \"baseSize\":8.5, \"stretchY\":5.0, \"biomeDepthWeight\":2.0, \"biomeDepthOffset\":1.0, \"biomeScaleWeight\":4.0, \"biomeScaleOffset\":1.0, \"useCaves\":true, \"useDungeons\":true, \"dungeonChance\":8, \"useStrongholds\":true, \"useVillages\":true, \"useMineShafts\":true, \"useTemples\":true, \"useRavines\":true, \"useWaterLakes\":true, \"waterLakeChance\":4, \"useLavaLakes\":true, \"lavaLakeChance\":80, \"useLavaOceans\":false, \"seaLevel\":63 }");
        var2 = new ResourceLocation("textures/gui/presets/delight.png");
        GuiScreenCustomizePresets.field_175310_f.add(new Info(I18n.format("createWorld.customize.custom.preset.caveDelight", new Object[0]), var2, var0));
        var0 = ChunkProviderSettings.Factory.func_177865_a("{\"coordinateScale\":738.41864, \"heightScale\":157.69133, \"upperLimitScale\":801.4267, \"lowerLimitScale\":1254.1643, \"depthNoiseScaleX\":374.93652, \"depthNoiseScaleZ\":288.65228, \"depthNoiseScaleExponent\":1.2092624, \"mainNoiseScaleX\":1355.9908, \"mainNoiseScaleY\":745.5343, \"mainNoiseScaleZ\":1183.464, \"baseSize\":1.8758626, \"stretchY\":1.7137525, \"biomeDepthWeight\":1.7553768, \"biomeDepthOffset\":3.4701107, \"biomeScaleWeight\":1.0, \"biomeScaleOffset\":2.535211, \"useCaves\":true, \"useDungeons\":true, \"dungeonChance\":8, \"useStrongholds\":true, \"useVillages\":true, \"useMineShafts\":true, \"useTemples\":true, \"useRavines\":true, \"useWaterLakes\":true, \"waterLakeChance\":4, \"useLavaLakes\":true, \"lavaLakeChance\":80, \"useLavaOceans\":false, \"seaLevel\":63 }");
        var2 = new ResourceLocation("textures/gui/presets/madness.png");
        GuiScreenCustomizePresets.field_175310_f.add(new Info(I18n.format("createWorld.customize.custom.preset.mountains", new Object[0]), var2, var0));
        var0 = ChunkProviderSettings.Factory.func_177865_a("{\"coordinateScale\":684.412, \"heightScale\":684.412, \"upperLimitScale\":512.0, \"lowerLimitScale\":512.0, \"depthNoiseScaleX\":200.0, \"depthNoiseScaleZ\":200.0, \"depthNoiseScaleExponent\":0.5, \"mainNoiseScaleX\":1000.0, \"mainNoiseScaleY\":3000.0, \"mainNoiseScaleZ\":1000.0, \"baseSize\":8.5, \"stretchY\":10.0, \"biomeDepthWeight\":1.0, \"biomeDepthOffset\":0.0, \"biomeScaleWeight\":1.0, \"biomeScaleOffset\":0.0, \"useCaves\":true, \"useDungeons\":true, \"dungeonChance\":8, \"useStrongholds\":true, \"useVillages\":true, \"useMineShafts\":true, \"useTemples\":true, \"useRavines\":true, \"useWaterLakes\":true, \"waterLakeChance\":4, \"useLavaLakes\":true, \"lavaLakeChance\":80, \"useLavaOceans\":false, \"seaLevel\":20 }");
        var2 = new ResourceLocation("textures/gui/presets/drought.png");
        GuiScreenCustomizePresets.field_175310_f.add(new Info(I18n.format("createWorld.customize.custom.preset.drought", new Object[0]), var2, var0));
        var0 = ChunkProviderSettings.Factory.func_177865_a("{\"coordinateScale\":684.412, \"heightScale\":684.412, \"upperLimitScale\":2.0, \"lowerLimitScale\":64.0, \"depthNoiseScaleX\":200.0, \"depthNoiseScaleZ\":200.0, \"depthNoiseScaleExponent\":0.5, \"mainNoiseScaleX\":80.0, \"mainNoiseScaleY\":160.0, \"mainNoiseScaleZ\":80.0, \"baseSize\":8.5, \"stretchY\":12.0, \"biomeDepthWeight\":1.0, \"biomeDepthOffset\":0.0, \"biomeScaleWeight\":1.0, \"biomeScaleOffset\":0.0, \"useCaves\":true, \"useDungeons\":true, \"dungeonChance\":8, \"useStrongholds\":true, \"useVillages\":true, \"useMineShafts\":true, \"useTemples\":true, \"useRavines\":true, \"useWaterLakes\":true, \"waterLakeChance\":4, \"useLavaLakes\":true, \"lavaLakeChance\":80, \"useLavaOceans\":false, \"seaLevel\":6 }");
        var2 = new ResourceLocation("textures/gui/presets/chaos.png");
        GuiScreenCustomizePresets.field_175310_f.add(new Info(I18n.format("createWorld.customize.custom.preset.caveChaos", new Object[0]), var2, var0));
        var0 = ChunkProviderSettings.Factory.func_177865_a("{\"coordinateScale\":684.412, \"heightScale\":684.412, \"upperLimitScale\":512.0, \"lowerLimitScale\":512.0, \"depthNoiseScaleX\":200.0, \"depthNoiseScaleZ\":200.0, \"depthNoiseScaleExponent\":0.5, \"mainNoiseScaleX\":80.0, \"mainNoiseScaleY\":160.0, \"mainNoiseScaleZ\":80.0, \"baseSize\":8.5, \"stretchY\":12.0, \"biomeDepthWeight\":1.0, \"biomeDepthOffset\":0.0, \"biomeScaleWeight\":1.0, \"biomeScaleOffset\":0.0, \"useCaves\":true, \"useDungeons\":true, \"dungeonChance\":8, \"useStrongholds\":true, \"useVillages\":true, \"useMineShafts\":true, \"useTemples\":true, \"useRavines\":true, \"useWaterLakes\":true, \"waterLakeChance\":4, \"useLavaLakes\":true, \"lavaLakeChance\":80, \"useLavaOceans\":true, \"seaLevel\":40 }");
        var2 = new ResourceLocation("textures/gui/presets/luck.png");
        GuiScreenCustomizePresets.field_175310_f.add(new Info(I18n.format("createWorld.customize.custom.preset.goodLuck", new Object[0]), var2, var0));
    }
    
    public GuiScreenCustomizePresets(final GuiCustomizeWorldScreen p_i45524_1_) {
        this.field_175315_a = "Customize World Presets";
        this.field_175314_r = p_i45524_1_;
    }
    
    @Override
    public void initGui() {
        this.buttonList.clear();
        Keyboard.enableRepeatEvents(true);
        this.field_175315_a = I18n.format("createWorld.customize.custom.presets.title", new Object[0]);
        this.field_175313_s = I18n.format("createWorld.customize.presets.share", new Object[0]);
        this.field_175312_t = I18n.format("createWorld.customize.presets.list", new Object[0]);
        this.field_175317_i = new GuiTextField(2, this.fontRendererObj, 50, 40, this.width - 100, 20);
        this.field_175311_g = new ListPreset();
        this.field_175317_i.setMaxStringLength(2000);
        this.field_175317_i.setText(this.field_175314_r.func_175323_a());
        this.buttonList.add(this.field_175316_h = new GuiButton(0, this.width / 2 - 102, GuiScreenCustomizePresets.height - 27, 100, 20, I18n.format("createWorld.customize.presets.select", new Object[0])));
        this.buttonList.add(new GuiButton(1, this.width / 2 + 3, GuiScreenCustomizePresets.height - 27, 100, 20, I18n.format("gui.cancel", new Object[0])));
        this.func_175304_a();
    }
    
    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.field_175311_g.func_178039_p();
    }
    
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }
    
    @Override
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        this.field_175317_i.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
    
    @Override
    protected void keyTyped(final char typedChar, final int keyCode) throws IOException {
        if (!this.field_175317_i.textboxKeyTyped(typedChar, keyCode)) {
            super.keyTyped(typedChar, keyCode);
        }
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) throws IOException {
        switch (button.id) {
            case 0: {
                this.field_175314_r.func_175324_a(this.field_175317_i.getText());
                this.mc.displayGuiScreen(this.field_175314_r);
                break;
            }
            case 1: {
                this.mc.displayGuiScreen(this.field_175314_r);
                break;
            }
        }
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.drawDefaultBackground();
        this.field_175311_g.drawScreen(mouseX, mouseY, partialTicks);
        this.drawCenteredString(this.fontRendererObj, this.field_175315_a, this.width / 2, 8, 16777215);
        this.drawString(this.fontRendererObj, this.field_175313_s, 50, 30, 10526880);
        this.drawString(this.fontRendererObj, this.field_175312_t, 50, 70, 10526880);
        this.field_175317_i.drawTextBox();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    @Override
    public void updateScreen() {
        this.field_175317_i.updateCursorCounter();
        super.updateScreen();
    }
    
    public void func_175304_a() {
        this.field_175316_h.enabled = this.func_175305_g();
    }
    
    private boolean func_175305_g() {
        return (this.field_175311_g.field_178053_u > -1 && this.field_175311_g.field_178053_u < GuiScreenCustomizePresets.field_175310_f.size()) || this.field_175317_i.getText().length() > 1;
    }
    
    static class Info
    {
        public String field_178955_a;
        public ResourceLocation field_178953_b;
        public ChunkProviderSettings.Factory field_178954_c;
        private static final String __OBFID = "CL_00001936";
        
        public Info(final String p_i45523_1_, final ResourceLocation p_i45523_2_, final ChunkProviderSettings.Factory p_i45523_3_) {
            this.field_178955_a = p_i45523_1_;
            this.field_178953_b = p_i45523_2_;
            this.field_178954_c = p_i45523_3_;
        }
    }
    
    class ListPreset extends GuiSlot
    {
        public int field_178053_u;
        private static final String __OBFID = "CL_00001935";
        
        public ListPreset() {
            super(GuiScreenCustomizePresets.this.mc, GuiScreenCustomizePresets.this.width, GuiScreenCustomizePresets.height, 80, GuiScreenCustomizePresets.height - 32, 38);
            this.field_178053_u = -1;
        }
        
        @Override
        protected int getSize() {
            return GuiScreenCustomizePresets.field_175310_f.size();
        }
        
        @Override
        protected void elementClicked(final int slotIndex, final boolean isDoubleClick, final int mouseX, final int mouseY) {
            this.field_178053_u = slotIndex;
            GuiScreenCustomizePresets.this.func_175304_a();
            GuiScreenCustomizePresets.this.field_175317_i.setText(GuiScreenCustomizePresets.field_175310_f.get(GuiScreenCustomizePresets.this.field_175311_g.field_178053_u).field_178954_c.toString());
        }
        
        @Override
        protected boolean isSelected(final int slotIndex) {
            return slotIndex == this.field_178053_u;
        }
        
        @Override
        protected void drawBackground() {
        }
        
        private void func_178051_a(final int p_178051_1_, final int p_178051_2_, final ResourceLocation p_178051_3_) {
            final int var4 = p_178051_1_ + 5;
            GuiScreenCustomizePresets.this.drawHorizontalLine(var4 - 1, var4 + 32, p_178051_2_ - 1, -2039584);
            GuiScreenCustomizePresets.this.drawHorizontalLine(var4 - 1, var4 + 32, p_178051_2_ + 32, -6250336);
            GuiScreenCustomizePresets.this.drawVerticalLine(var4 - 1, p_178051_2_ - 1, p_178051_2_ + 32, -2039584);
            GuiScreenCustomizePresets.this.drawVerticalLine(var4 + 32, p_178051_2_ - 1, p_178051_2_ + 32, -6250336);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            this.mc.getTextureManager().bindTexture(p_178051_3_);
            final boolean var5 = true;
            final boolean var6 = true;
            final Tessellator var7 = Tessellator.getInstance();
            final WorldRenderer var8 = var7.getWorldRenderer();
            var8.startDrawingQuads();
            var8.addVertexWithUV(var4 + 0, p_178051_2_ + 32, 0.0, 0.0, 1.0);
            var8.addVertexWithUV(var4 + 32, p_178051_2_ + 32, 0.0, 1.0, 1.0);
            var8.addVertexWithUV(var4 + 32, p_178051_2_ + 0, 0.0, 1.0, 0.0);
            var8.addVertexWithUV(var4 + 0, p_178051_2_ + 0, 0.0, 0.0, 0.0);
            var7.draw();
        }
        
        @Override
        protected void drawSlot(final int p_180791_1_, final int p_180791_2_, final int p_180791_3_, final int p_180791_4_, final int p_180791_5_, final int p_180791_6_) {
            final Info var7 = GuiScreenCustomizePresets.field_175310_f.get(p_180791_1_);
            this.func_178051_a(p_180791_2_, p_180791_3_, var7.field_178953_b);
            GuiScreenCustomizePresets.this.fontRendererObj.drawString(var7.field_178955_a, p_180791_2_ + 32 + 10, p_180791_3_ + 14, 16777215);
        }
    }
}

package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;
import org.lwjgl.input.Keyboard;
import com.google.common.collect.Lists;
import java.util.List;

public class GuiScreenCustomizePresets extends GuiScreen
{
    private static final List Â;
    private Â Ý;
    private GuiButton Ø­áŒŠá;
    private GuiTextField Âµá€;
    private GuiCustomizeWorldScreen Ó;
    protected String HorizonCode_Horizon_È;
    private String à;
    private String Ø;
    private static final String áŒŠÆ = "CL_00001937";
    
    static {
        Â = Lists.newArrayList();
        ChunkProviderSettings.HorizonCode_Horizon_È var0 = ChunkProviderSettings.HorizonCode_Horizon_È.HorizonCode_Horizon_È("{ \"coordinateScale\":684.412, \"heightScale\":684.412, \"upperLimitScale\":512.0, \"lowerLimitScale\":512.0, \"depthNoiseScaleX\":200.0, \"depthNoiseScaleZ\":200.0, \"depthNoiseScaleExponent\":0.5, \"mainNoiseScaleX\":5000.0, \"mainNoiseScaleY\":1000.0, \"mainNoiseScaleZ\":5000.0, \"baseSize\":8.5, \"stretchY\":8.0, \"biomeDepthWeight\":2.0, \"biomeDepthOffset\":0.5, \"biomeScaleWeight\":2.0, \"biomeScaleOffset\":0.375, \"useCaves\":true, \"useDungeons\":true, \"dungeonChance\":8, \"useStrongholds\":true, \"useVillages\":true, \"useMineShafts\":true, \"useTemples\":true, \"useRavines\":true, \"useWaterLakes\":true, \"waterLakeChance\":4, \"useLavaLakes\":true, \"lavaLakeChance\":80, \"useLavaOceans\":false, \"seaLevel\":255 }");
        ResourceLocation_1975012498 var2 = new ResourceLocation_1975012498("textures/gui/presets/water.png");
        GuiScreenCustomizePresets.Â.add(new HorizonCode_Horizon_È(I18n.HorizonCode_Horizon_È("createWorld.customize.custom.preset.waterWorld", new Object[0]), var2, var0));
        var0 = ChunkProviderSettings.HorizonCode_Horizon_È.HorizonCode_Horizon_È("{\"coordinateScale\":3000.0, \"heightScale\":6000.0, \"upperLimitScale\":250.0, \"lowerLimitScale\":512.0, \"depthNoiseScaleX\":200.0, \"depthNoiseScaleZ\":200.0, \"depthNoiseScaleExponent\":0.5, \"mainNoiseScaleX\":80.0, \"mainNoiseScaleY\":160.0, \"mainNoiseScaleZ\":80.0, \"baseSize\":8.5, \"stretchY\":10.0, \"biomeDepthWeight\":1.0, \"biomeDepthOffset\":0.0, \"biomeScaleWeight\":1.0, \"biomeScaleOffset\":0.0, \"useCaves\":true, \"useDungeons\":true, \"dungeonChance\":8, \"useStrongholds\":true, \"useVillages\":true, \"useMineShafts\":true, \"useTemples\":true, \"useRavines\":true, \"useWaterLakes\":true, \"waterLakeChance\":4, \"useLavaLakes\":true, \"lavaLakeChance\":80, \"useLavaOceans\":false, \"seaLevel\":63 }");
        var2 = new ResourceLocation_1975012498("textures/gui/presets/isles.png");
        GuiScreenCustomizePresets.Â.add(new HorizonCode_Horizon_È(I18n.HorizonCode_Horizon_È("createWorld.customize.custom.preset.isleLand", new Object[0]), var2, var0));
        var0 = ChunkProviderSettings.HorizonCode_Horizon_È.HorizonCode_Horizon_È("{\"coordinateScale\":684.412, \"heightScale\":684.412, \"upperLimitScale\":512.0, \"lowerLimitScale\":512.0, \"depthNoiseScaleX\":200.0, \"depthNoiseScaleZ\":200.0, \"depthNoiseScaleExponent\":0.5, \"mainNoiseScaleX\":5000.0, \"mainNoiseScaleY\":1000.0, \"mainNoiseScaleZ\":5000.0, \"baseSize\":8.5, \"stretchY\":5.0, \"biomeDepthWeight\":2.0, \"biomeDepthOffset\":1.0, \"biomeScaleWeight\":4.0, \"biomeScaleOffset\":1.0, \"useCaves\":true, \"useDungeons\":true, \"dungeonChance\":8, \"useStrongholds\":true, \"useVillages\":true, \"useMineShafts\":true, \"useTemples\":true, \"useRavines\":true, \"useWaterLakes\":true, \"waterLakeChance\":4, \"useLavaLakes\":true, \"lavaLakeChance\":80, \"useLavaOceans\":false, \"seaLevel\":63 }");
        var2 = new ResourceLocation_1975012498("textures/gui/presets/delight.png");
        GuiScreenCustomizePresets.Â.add(new HorizonCode_Horizon_È(I18n.HorizonCode_Horizon_È("createWorld.customize.custom.preset.caveDelight", new Object[0]), var2, var0));
        var0 = ChunkProviderSettings.HorizonCode_Horizon_È.HorizonCode_Horizon_È("{\"coordinateScale\":738.41864, \"heightScale\":157.69133, \"upperLimitScale\":801.4267, \"lowerLimitScale\":1254.1643, \"depthNoiseScaleX\":374.93652, \"depthNoiseScaleZ\":288.65228, \"depthNoiseScaleExponent\":1.2092624, \"mainNoiseScaleX\":1355.9908, \"mainNoiseScaleY\":745.5343, \"mainNoiseScaleZ\":1183.464, \"baseSize\":1.8758626, \"stretchY\":1.7137525, \"biomeDepthWeight\":1.7553768, \"biomeDepthOffset\":3.4701107, \"biomeScaleWeight\":1.0, \"biomeScaleOffset\":2.535211, \"useCaves\":true, \"useDungeons\":true, \"dungeonChance\":8, \"useStrongholds\":true, \"useVillages\":true, \"useMineShafts\":true, \"useTemples\":true, \"useRavines\":true, \"useWaterLakes\":true, \"waterLakeChance\":4, \"useLavaLakes\":true, \"lavaLakeChance\":80, \"useLavaOceans\":false, \"seaLevel\":63 }");
        var2 = new ResourceLocation_1975012498("textures/gui/presets/madness.png");
        GuiScreenCustomizePresets.Â.add(new HorizonCode_Horizon_È(I18n.HorizonCode_Horizon_È("createWorld.customize.custom.preset.mountains", new Object[0]), var2, var0));
        var0 = ChunkProviderSettings.HorizonCode_Horizon_È.HorizonCode_Horizon_È("{\"coordinateScale\":684.412, \"heightScale\":684.412, \"upperLimitScale\":512.0, \"lowerLimitScale\":512.0, \"depthNoiseScaleX\":200.0, \"depthNoiseScaleZ\":200.0, \"depthNoiseScaleExponent\":0.5, \"mainNoiseScaleX\":1000.0, \"mainNoiseScaleY\":3000.0, \"mainNoiseScaleZ\":1000.0, \"baseSize\":8.5, \"stretchY\":10.0, \"biomeDepthWeight\":1.0, \"biomeDepthOffset\":0.0, \"biomeScaleWeight\":1.0, \"biomeScaleOffset\":0.0, \"useCaves\":true, \"useDungeons\":true, \"dungeonChance\":8, \"useStrongholds\":true, \"useVillages\":true, \"useMineShafts\":true, \"useTemples\":true, \"useRavines\":true, \"useWaterLakes\":true, \"waterLakeChance\":4, \"useLavaLakes\":true, \"lavaLakeChance\":80, \"useLavaOceans\":false, \"seaLevel\":20 }");
        var2 = new ResourceLocation_1975012498("textures/gui/presets/drought.png");
        GuiScreenCustomizePresets.Â.add(new HorizonCode_Horizon_È(I18n.HorizonCode_Horizon_È("createWorld.customize.custom.preset.drought", new Object[0]), var2, var0));
        var0 = ChunkProviderSettings.HorizonCode_Horizon_È.HorizonCode_Horizon_È("{\"coordinateScale\":684.412, \"heightScale\":684.412, \"upperLimitScale\":2.0, \"lowerLimitScale\":64.0, \"depthNoiseScaleX\":200.0, \"depthNoiseScaleZ\":200.0, \"depthNoiseScaleExponent\":0.5, \"mainNoiseScaleX\":80.0, \"mainNoiseScaleY\":160.0, \"mainNoiseScaleZ\":80.0, \"baseSize\":8.5, \"stretchY\":12.0, \"biomeDepthWeight\":1.0, \"biomeDepthOffset\":0.0, \"biomeScaleWeight\":1.0, \"biomeScaleOffset\":0.0, \"useCaves\":true, \"useDungeons\":true, \"dungeonChance\":8, \"useStrongholds\":true, \"useVillages\":true, \"useMineShafts\":true, \"useTemples\":true, \"useRavines\":true, \"useWaterLakes\":true, \"waterLakeChance\":4, \"useLavaLakes\":true, \"lavaLakeChance\":80, \"useLavaOceans\":false, \"seaLevel\":6 }");
        var2 = new ResourceLocation_1975012498("textures/gui/presets/chaos.png");
        GuiScreenCustomizePresets.Â.add(new HorizonCode_Horizon_È(I18n.HorizonCode_Horizon_È("createWorld.customize.custom.preset.caveChaos", new Object[0]), var2, var0));
        var0 = ChunkProviderSettings.HorizonCode_Horizon_È.HorizonCode_Horizon_È("{\"coordinateScale\":684.412, \"heightScale\":684.412, \"upperLimitScale\":512.0, \"lowerLimitScale\":512.0, \"depthNoiseScaleX\":200.0, \"depthNoiseScaleZ\":200.0, \"depthNoiseScaleExponent\":0.5, \"mainNoiseScaleX\":80.0, \"mainNoiseScaleY\":160.0, \"mainNoiseScaleZ\":80.0, \"baseSize\":8.5, \"stretchY\":12.0, \"biomeDepthWeight\":1.0, \"biomeDepthOffset\":0.0, \"biomeScaleWeight\":1.0, \"biomeScaleOffset\":0.0, \"useCaves\":true, \"useDungeons\":true, \"dungeonChance\":8, \"useStrongholds\":true, \"useVillages\":true, \"useMineShafts\":true, \"useTemples\":true, \"useRavines\":true, \"useWaterLakes\":true, \"waterLakeChance\":4, \"useLavaLakes\":true, \"lavaLakeChance\":80, \"useLavaOceans\":true, \"seaLevel\":40 }");
        var2 = new ResourceLocation_1975012498("textures/gui/presets/luck.png");
        GuiScreenCustomizePresets.Â.add(new HorizonCode_Horizon_È(I18n.HorizonCode_Horizon_È("createWorld.customize.custom.preset.goodLuck", new Object[0]), var2, var0));
    }
    
    public GuiScreenCustomizePresets(final GuiCustomizeWorldScreen p_i45524_1_) {
        this.HorizonCode_Horizon_È = "Customize World Presets";
        this.Ó = p_i45524_1_;
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        this.ÇŽÉ.clear();
        Keyboard.enableRepeatEvents(true);
        this.HorizonCode_Horizon_È = I18n.HorizonCode_Horizon_È("createWorld.customize.custom.presets.title", new Object[0]);
        this.à = I18n.HorizonCode_Horizon_È("createWorld.customize.presets.share", new Object[0]);
        this.Ø = I18n.HorizonCode_Horizon_È("createWorld.customize.presets.list", new Object[0]);
        this.Âµá€ = new GuiTextField(2, this.É, 50, 40, GuiScreenCustomizePresets.Çªà¢ - 100, 20);
        this.Ý = new Â();
        this.Âµá€.Ó(2000);
        this.Âµá€.HorizonCode_Horizon_È(this.Ó.Ó());
        this.ÇŽÉ.add(this.Ø­áŒŠá = new GuiButton(0, GuiScreenCustomizePresets.Çªà¢ / 2 - 102, GuiScreenCustomizePresets.Ê - 27, 100, 20, I18n.HorizonCode_Horizon_È("createWorld.customize.presets.select", new Object[0])));
        this.ÇŽÉ.add(new GuiButton(1, GuiScreenCustomizePresets.Çªà¢ / 2 + 3, GuiScreenCustomizePresets.Ê - 27, 100, 20, I18n.HorizonCode_Horizon_È("gui.cancel", new Object[0])));
        this.Ó();
    }
    
    @Override
    public void n_() throws IOException {
        super.n_();
        this.Ý.Ø();
    }
    
    @Override
    public void q_() {
        Keyboard.enableRepeatEvents(false);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        this.Âµá€.HorizonCode_Horizon_È(mouseX, mouseY, mouseButton);
        super.HorizonCode_Horizon_È(mouseX, mouseY, mouseButton);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final char typedChar, final int keyCode) throws IOException {
        if (!this.Âµá€.HorizonCode_Horizon_È(typedChar, keyCode)) {
            super.HorizonCode_Horizon_È(typedChar, keyCode);
        }
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final GuiButton button) throws IOException {
        switch (button.£à) {
            case 0: {
                this.Ó.HorizonCode_Horizon_È(this.Âµá€.Ý());
                GuiScreenCustomizePresets.Ñ¢á.HorizonCode_Horizon_È(this.Ó);
                break;
            }
            case 1: {
                GuiScreenCustomizePresets.Ñ¢á.HorizonCode_Horizon_È(this.Ó);
                break;
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final float partialTicks) {
        this.£á();
        this.Ý.HorizonCode_Horizon_È(mouseX, mouseY, partialTicks);
        this.HorizonCode_Horizon_È(this.É, this.HorizonCode_Horizon_È, GuiScreenCustomizePresets.Çªà¢ / 2, 8, 16777215);
        Gui_1808253012.Â(this.É, this.à, 50, 30, 10526880);
        Gui_1808253012.Â(this.É, this.Ø, 50, 70, 10526880);
        this.Âµá€.HorizonCode_Horizon_È();
        super.HorizonCode_Horizon_È(mouseX, mouseY, partialTicks);
    }
    
    @Override
    public void Ý() {
        this.Âµá€.Â();
        super.Ý();
    }
    
    public void Ó() {
        this.Ø­áŒŠá.µà = this.Ø();
    }
    
    private boolean Ø() {
        return (this.Ý.HorizonCode_Horizon_È > -1 && this.Ý.HorizonCode_Horizon_È < GuiScreenCustomizePresets.Â.size()) || this.Âµá€.Ý().length() > 1;
    }
    
    static class HorizonCode_Horizon_È
    {
        public String HorizonCode_Horizon_È;
        public ResourceLocation_1975012498 Â;
        public ChunkProviderSettings.HorizonCode_Horizon_È Ý;
        private static final String Ø­áŒŠá = "CL_00001936";
        
        public HorizonCode_Horizon_È(final String p_i45523_1_, final ResourceLocation_1975012498 p_i45523_2_, final ChunkProviderSettings.HorizonCode_Horizon_È p_i45523_3_) {
            this.HorizonCode_Horizon_È = p_i45523_1_;
            this.Â = p_i45523_2_;
            this.Ý = p_i45523_3_;
        }
    }
    
    class Â extends GuiSlot
    {
        public int HorizonCode_Horizon_È;
        private static final String Šáƒ = "CL_00001935";
        
        public Â() {
            super(GuiScreenCustomizePresets.Ñ¢á, GuiScreenCustomizePresets.Çªà¢, GuiScreenCustomizePresets.Ê, 80, GuiScreenCustomizePresets.Ê - 32, 38);
            this.HorizonCode_Horizon_È = -1;
        }
        
        @Override
        protected int HorizonCode_Horizon_È() {
            return GuiScreenCustomizePresets.Â.size();
        }
        
        @Override
        protected void HorizonCode_Horizon_È(final int slotIndex, final boolean isDoubleClick, final int mouseX, final int mouseY) {
            this.HorizonCode_Horizon_È = slotIndex;
            GuiScreenCustomizePresets.this.Ó();
            GuiScreenCustomizePresets.this.Âµá€.HorizonCode_Horizon_È(GuiScreenCustomizePresets.Â.get(GuiScreenCustomizePresets.this.Ý.HorizonCode_Horizon_È).Ý.toString());
        }
        
        @Override
        protected boolean HorizonCode_Horizon_È(final int slotIndex) {
            return slotIndex == this.HorizonCode_Horizon_È;
        }
        
        @Override
        protected void Â() {
        }
        
        private void HorizonCode_Horizon_È(final int p_178051_1_, final int p_178051_2_, final ResourceLocation_1975012498 p_178051_3_) {
            final int var4 = p_178051_1_ + 5;
            GuiScreenCustomizePresets.this.HorizonCode_Horizon_È(var4 - 1, var4 + 32, p_178051_2_ - 1, -2039584);
            GuiScreenCustomizePresets.this.HorizonCode_Horizon_È(var4 - 1, var4 + 32, p_178051_2_ + 32, -6250336);
            GuiScreenCustomizePresets.this.Â(var4 - 1, p_178051_2_ - 1, p_178051_2_ + 32, -2039584);
            GuiScreenCustomizePresets.this.Â(var4 + 32, p_178051_2_ - 1, p_178051_2_ + 32, -6250336);
            GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
            this.Ý.¥à().HorizonCode_Horizon_È(p_178051_3_);
            final boolean var5 = true;
            final boolean var6 = true;
            final Tessellator var7 = Tessellator.HorizonCode_Horizon_È();
            final WorldRenderer var8 = var7.Ý();
            var8.Â();
            var8.HorizonCode_Horizon_È(var4 + 0, p_178051_2_ + 32, 0.0, 0.0, 1.0);
            var8.HorizonCode_Horizon_È(var4 + 32, p_178051_2_ + 32, 0.0, 1.0, 1.0);
            var8.HorizonCode_Horizon_È(var4 + 32, p_178051_2_ + 0, 0.0, 1.0, 0.0);
            var8.HorizonCode_Horizon_È(var4 + 0, p_178051_2_ + 0, 0.0, 0.0, 0.0);
            var7.Â();
        }
        
        @Override
        protected void HorizonCode_Horizon_È(final int p_180791_1_, final int p_180791_2_, final int p_180791_3_, final int p_180791_4_, final int p_180791_5_, final int p_180791_6_) {
            final HorizonCode_Horizon_È var7 = GuiScreenCustomizePresets.Â.get(p_180791_1_);
            this.HorizonCode_Horizon_È(p_180791_2_, p_180791_3_, var7.Â);
            GuiScreenCustomizePresets.this.É.HorizonCode_Horizon_È(var7.HorizonCode_Horizon_È, p_180791_2_ + 32 + 10, p_180791_3_ + 14, 16777215);
        }
    }
}

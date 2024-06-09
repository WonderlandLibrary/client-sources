package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import com.google.common.collect.Maps;
import java.io.IOException;
import org.lwjgl.input.Keyboard;
import java.util.Arrays;
import com.google.common.collect.Lists;
import java.util.List;

public class GuiFlatPresets extends GuiScreen
{
    private static final List HorizonCode_Horizon_È;
    private final GuiCreateFlatWorld Â;
    private String Ý;
    private String Ø­áŒŠá;
    private String Âµá€;
    private Â Ó;
    private GuiButton à;
    private GuiTextField Ø;
    private static final String áŒŠÆ = "CL_00000704";
    
    static {
        HorizonCode_Horizon_È = Lists.newArrayList();
        HorizonCode_Horizon_È("Classic Flat", Item_1028566121.HorizonCode_Horizon_È(Blocks.Ø­áŒŠá), BiomeGenBase.µà, Arrays.asList("village"), new FlatLayerInfo(1, Blocks.Ø­áŒŠá), new FlatLayerInfo(2, Blocks.Âµá€), new FlatLayerInfo(1, Blocks.áŒŠÆ));
        HorizonCode_Horizon_È("Tunnelers' Dream", Item_1028566121.HorizonCode_Horizon_È(Blocks.Ý), BiomeGenBase.¥Æ, Arrays.asList("biome_1", "dungeon", "decoration", "stronghold", "mineshaft"), new FlatLayerInfo(1, Blocks.Ø­áŒŠá), new FlatLayerInfo(5, Blocks.Âµá€), new FlatLayerInfo(230, Blocks.Ý), new FlatLayerInfo(1, Blocks.áŒŠÆ));
        HorizonCode_Horizon_È("Water World", Items.ˆÓ, BiomeGenBase.¥à, Arrays.asList("biome_1", "oceanmonument"), new FlatLayerInfo(90, Blocks.ÂµÈ), new FlatLayerInfo(5, Blocks.£á), new FlatLayerInfo(5, Blocks.Âµá€), new FlatLayerInfo(5, Blocks.Ý), new FlatLayerInfo(1, Blocks.áŒŠÆ));
        HorizonCode_Horizon_È("Overworld", Item_1028566121.HorizonCode_Horizon_È(Blocks.áƒ), BlockTallGrass.HorizonCode_Horizon_È.Â.Â(), BiomeGenBase.µà, Arrays.asList("village", "biome_1", "decoration", "stronghold", "mineshaft", "dungeon", "lake", "lava_lake"), new FlatLayerInfo(1, Blocks.Ø­áŒŠá), new FlatLayerInfo(3, Blocks.Âµá€), new FlatLayerInfo(59, Blocks.Ý), new FlatLayerInfo(1, Blocks.áŒŠÆ));
        HorizonCode_Horizon_È("Snowy Kingdom", Item_1028566121.HorizonCode_Horizon_È(Blocks.áŒŠá€), BiomeGenBase.ŒÏ, Arrays.asList("village", "biome_1"), new FlatLayerInfo(1, Blocks.áŒŠá€), new FlatLayerInfo(1, Blocks.Ø­áŒŠá), new FlatLayerInfo(3, Blocks.Âµá€), new FlatLayerInfo(59, Blocks.Ý), new FlatLayerInfo(1, Blocks.áŒŠÆ));
        HorizonCode_Horizon_È("Bottomless Pit", Items.ÇŽÕ, BiomeGenBase.µà, Arrays.asList("village", "biome_1"), new FlatLayerInfo(1, Blocks.Ø­áŒŠá), new FlatLayerInfo(3, Blocks.Âµá€), new FlatLayerInfo(2, Blocks.Ó));
        HorizonCode_Horizon_È("Desert", Item_1028566121.HorizonCode_Horizon_È(Blocks.£á), BiomeGenBase.ˆà, Arrays.asList("village", "biome_1", "decoration", "stronghold", "mineshaft", "dungeon"), new FlatLayerInfo(8, Blocks.£á), new FlatLayerInfo(52, Blocks.ŒÏ), new FlatLayerInfo(3, Blocks.Ý), new FlatLayerInfo(1, Blocks.áŒŠÆ));
        HorizonCode_Horizon_È("Redstone Ready", Items.ÇŽá, BiomeGenBase.ˆà, new FlatLayerInfo(52, Blocks.ŒÏ), new FlatLayerInfo(3, Blocks.Ý), new FlatLayerInfo(1, Blocks.áŒŠÆ));
    }
    
    public GuiFlatPresets(final GuiCreateFlatWorld p_i46318_1_) {
        this.Â = p_i46318_1_;
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        this.ÇŽÉ.clear();
        Keyboard.enableRepeatEvents(true);
        this.Ý = I18n.HorizonCode_Horizon_È("createWorld.customize.presets.title", new Object[0]);
        this.Ø­áŒŠá = I18n.HorizonCode_Horizon_È("createWorld.customize.presets.share", new Object[0]);
        this.Âµá€ = I18n.HorizonCode_Horizon_È("createWorld.customize.presets.list", new Object[0]);
        this.Ø = new GuiTextField(2, this.É, 50, 40, GuiFlatPresets.Çªà¢ - 100, 20);
        this.Ó = new Â();
        this.Ø.Ó(1230);
        this.Ø.HorizonCode_Horizon_È(this.Â.Ó());
        this.ÇŽÉ.add(this.à = new GuiButton(0, GuiFlatPresets.Çªà¢ / 2 - 155, GuiFlatPresets.Ê - 28, 150, 20, I18n.HorizonCode_Horizon_È("createWorld.customize.presets.select", new Object[0])));
        this.ÇŽÉ.add(new GuiButton(1, GuiFlatPresets.Çªà¢ / 2 + 5, GuiFlatPresets.Ê - 28, 150, 20, I18n.HorizonCode_Horizon_È("gui.cancel", new Object[0])));
        this.Ó();
    }
    
    @Override
    public void n_() throws IOException {
        super.n_();
        this.Ó.Ø();
    }
    
    @Override
    public void q_() {
        Keyboard.enableRepeatEvents(false);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        this.Ø.HorizonCode_Horizon_È(mouseX, mouseY, mouseButton);
        super.HorizonCode_Horizon_È(mouseX, mouseY, mouseButton);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final char typedChar, final int keyCode) throws IOException {
        if (!this.Ø.HorizonCode_Horizon_È(typedChar, keyCode)) {
            super.HorizonCode_Horizon_È(typedChar, keyCode);
        }
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final GuiButton button) throws IOException {
        if (button.£à == 0 && this.Ø()) {
            this.Â.HorizonCode_Horizon_È(this.Ø.Ý());
            GuiFlatPresets.Ñ¢á.HorizonCode_Horizon_È(this.Â);
        }
        else if (button.£à == 1) {
            GuiFlatPresets.Ñ¢á.HorizonCode_Horizon_È(this.Â);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final float partialTicks) {
        this.£á();
        this.Ó.HorizonCode_Horizon_È(mouseX, mouseY, partialTicks);
        this.HorizonCode_Horizon_È(this.É, this.Ý, GuiFlatPresets.Çªà¢ / 2, 8, 16777215);
        Gui_1808253012.Â(this.É, this.Ø­áŒŠá, 50, 30, 10526880);
        Gui_1808253012.Â(this.É, this.Âµá€, 50, 70, 10526880);
        this.Ø.HorizonCode_Horizon_È();
        super.HorizonCode_Horizon_È(mouseX, mouseY, partialTicks);
    }
    
    @Override
    public void Ý() {
        this.Ø.Â();
        super.Ý();
    }
    
    public void Ó() {
        final boolean var1 = this.Ø();
        this.à.µà = var1;
    }
    
    private boolean Ø() {
        return (this.Ó.HorizonCode_Horizon_È > -1 && this.Ó.HorizonCode_Horizon_È < GuiFlatPresets.HorizonCode_Horizon_È.size()) || this.Ø.Ý().length() > 1;
    }
    
    private static void HorizonCode_Horizon_È(final String p_146425_0_, final Item_1028566121 p_146425_1_, final BiomeGenBase p_146425_2_, final FlatLayerInfo... p_146425_3_) {
        HorizonCode_Horizon_È(p_146425_0_, p_146425_1_, 0, p_146425_2_, null, p_146425_3_);
    }
    
    private static void HorizonCode_Horizon_È(final String p_146421_0_, final Item_1028566121 p_146421_1_, final BiomeGenBase p_146421_2_, final List p_146421_3_, final FlatLayerInfo... p_146421_4_) {
        HorizonCode_Horizon_È(p_146421_0_, p_146421_1_, 0, p_146421_2_, p_146421_3_, p_146421_4_);
    }
    
    private static void HorizonCode_Horizon_È(final String p_175354_0_, final Item_1028566121 p_175354_1_, final int p_175354_2_, final BiomeGenBase p_175354_3_, final List p_175354_4_, final FlatLayerInfo... p_175354_5_) {
        final FlatGeneratorInfo var6 = new FlatGeneratorInfo();
        for (int var7 = p_175354_5_.length - 1; var7 >= 0; --var7) {
            var6.Ý().add(p_175354_5_[var7]);
        }
        var6.HorizonCode_Horizon_È(p_175354_3_.ÇªÔ);
        var6.Ø­áŒŠá();
        if (p_175354_4_ != null) {
            for (final String var9 : p_175354_4_) {
                var6.Â().put(var9, Maps.newHashMap());
            }
        }
        GuiFlatPresets.HorizonCode_Horizon_È.add(new HorizonCode_Horizon_È(p_175354_1_, p_175354_2_, p_175354_0_, var6.toString()));
    }
    
    static class HorizonCode_Horizon_È
    {
        public Item_1028566121 HorizonCode_Horizon_È;
        public int Â;
        public String Ý;
        public String Ø­áŒŠá;
        private static final String Âµá€ = "CL_00000705";
        
        public HorizonCode_Horizon_È(final Item_1028566121 p_i45518_1_, final int p_i45518_2_, final String p_i45518_3_, final String p_i45518_4_) {
            this.HorizonCode_Horizon_È = p_i45518_1_;
            this.Â = p_i45518_2_;
            this.Ý = p_i45518_3_;
            this.Ø­áŒŠá = p_i45518_4_;
        }
    }
    
    class Â extends GuiSlot
    {
        public int HorizonCode_Horizon_È;
        private static final String Šáƒ = "CL_00000706";
        
        public Â() {
            super(GuiFlatPresets.Ñ¢á, GuiFlatPresets.Çªà¢, GuiFlatPresets.Ê, 80, GuiFlatPresets.Ê - 37, 24);
            this.HorizonCode_Horizon_È = -1;
        }
        
        private void HorizonCode_Horizon_È(final int p_178054_1_, final int p_178054_2_, final Item_1028566121 p_178054_3_, final int p_178054_4_) {
            this.Âµá€(p_178054_1_ + 1, p_178054_2_ + 1);
            GlStateManager.ŠÄ();
            RenderHelper.Ý();
            GuiFlatPresets.this.ŒÏ.HorizonCode_Horizon_È(new ItemStack(p_178054_3_, 1, p_178054_4_), p_178054_1_ + 2, p_178054_2_ + 2);
            RenderHelper.HorizonCode_Horizon_È();
            GlStateManager.Ñ¢á();
        }
        
        private void Âµá€(final int p_148173_1_, final int p_148173_2_) {
            this.Ø­áŒŠá(p_148173_1_, p_148173_2_, 0, 0);
        }
        
        private void Ø­áŒŠá(final int p_148171_1_, final int p_148171_2_, final int p_148171_3_, final int p_148171_4_) {
            GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
            this.Ý.¥à().HorizonCode_Horizon_È(Gui_1808253012.Ï­Ðƒà);
            final float var5 = 0.0078125f;
            final float var6 = 0.0078125f;
            final boolean var7 = true;
            final boolean var8 = true;
            final Tessellator var9 = Tessellator.HorizonCode_Horizon_È();
            final WorldRenderer var10 = var9.Ý();
            var10.Â();
            var10.HorizonCode_Horizon_È(p_148171_1_ + 0, p_148171_2_ + 18, GuiFlatPresets.ŠÄ, (p_148171_3_ + 0) * 0.0078125f, (p_148171_4_ + 18) * 0.0078125f);
            var10.HorizonCode_Horizon_È(p_148171_1_ + 18, p_148171_2_ + 18, GuiFlatPresets.ŠÄ, (p_148171_3_ + 18) * 0.0078125f, (p_148171_4_ + 18) * 0.0078125f);
            var10.HorizonCode_Horizon_È(p_148171_1_ + 18, p_148171_2_ + 0, GuiFlatPresets.ŠÄ, (p_148171_3_ + 18) * 0.0078125f, (p_148171_4_ + 0) * 0.0078125f);
            var10.HorizonCode_Horizon_È(p_148171_1_ + 0, p_148171_2_ + 0, GuiFlatPresets.ŠÄ, (p_148171_3_ + 0) * 0.0078125f, (p_148171_4_ + 0) * 0.0078125f);
            var9.Â();
        }
        
        @Override
        protected int HorizonCode_Horizon_È() {
            return GuiFlatPresets.HorizonCode_Horizon_È.size();
        }
        
        @Override
        protected void HorizonCode_Horizon_È(final int slotIndex, final boolean isDoubleClick, final int mouseX, final int mouseY) {
            this.HorizonCode_Horizon_È = slotIndex;
            GuiFlatPresets.this.Ó();
            GuiFlatPresets.this.Ø.HorizonCode_Horizon_È(GuiFlatPresets.HorizonCode_Horizon_È.get(GuiFlatPresets.this.Ó.HorizonCode_Horizon_È).Ø­áŒŠá);
        }
        
        @Override
        protected boolean HorizonCode_Horizon_È(final int slotIndex) {
            return slotIndex == this.HorizonCode_Horizon_È;
        }
        
        @Override
        protected void Â() {
        }
        
        @Override
        protected void HorizonCode_Horizon_È(final int p_180791_1_, final int p_180791_2_, final int p_180791_3_, final int p_180791_4_, final int p_180791_5_, final int p_180791_6_) {
            final HorizonCode_Horizon_È var7 = GuiFlatPresets.HorizonCode_Horizon_È.get(p_180791_1_);
            this.HorizonCode_Horizon_È(p_180791_2_, p_180791_3_, var7.HorizonCode_Horizon_È, var7.Â);
            GuiFlatPresets.this.É.HorizonCode_Horizon_È(var7.Ý, p_180791_2_ + 18 + 5, p_180791_3_ + 6, 16777215);
        }
    }
}

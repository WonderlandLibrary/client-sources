package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;
import com.google.common.primitives.Floats;
import java.util.Random;
import com.google.common.base.Predicate;

public class GuiCustomizeWorldScreen extends GuiScreen implements GuiSlider.HorizonCode_Horizon_È, GuiPageButtonList.Ó
{
    private GuiCreateWorld Âµá€;
    protected String HorizonCode_Horizon_È;
    protected String Â;
    protected String Ý;
    protected String[] Ø­áŒŠá;
    private GuiPageButtonList Ó;
    private GuiButton à;
    private GuiButton Ø;
    private GuiButton áŒŠÆ;
    private GuiButton áˆºÑ¢Õ;
    private GuiButton ÂµÈ;
    private GuiButton á;
    private GuiButton ˆÏ­;
    private GuiButton £á;
    private boolean Å;
    private int £à;
    private boolean µà;
    private Predicate ˆà;
    private ChunkProviderSettings.HorizonCode_Horizon_È ¥Æ;
    private ChunkProviderSettings.HorizonCode_Horizon_È Ø­à;
    private Random µÕ;
    private static final String Æ = "CL_00001934";
    
    public GuiCustomizeWorldScreen(final GuiScreen p_i45521_1_, final String p_i45521_2_) {
        this.HorizonCode_Horizon_È = "Customize World Settings";
        this.Â = "Page 1 of 3";
        this.Ý = "Basic Settings";
        this.Ø­áŒŠá = new String[4];
        this.Å = false;
        this.£à = 0;
        this.µà = false;
        this.ˆà = (Predicate)new Predicate() {
            private static final String Â = "CL_00001933";
            
            public boolean HorizonCode_Horizon_È(final String p_178956_1_) {
                final Float var2 = Floats.tryParse(p_178956_1_);
                return p_178956_1_.length() == 0 || (var2 != null && Floats.isFinite((float)var2) && var2 >= 0.0f);
            }
            
            public boolean apply(final Object p_apply_1_) {
                return this.HorizonCode_Horizon_È((String)p_apply_1_);
            }
        };
        this.¥Æ = new ChunkProviderSettings.HorizonCode_Horizon_È();
        this.µÕ = new Random();
        this.Âµá€ = (GuiCreateWorld)p_i45521_1_;
        this.HorizonCode_Horizon_È(p_i45521_2_);
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        this.HorizonCode_Horizon_È = I18n.HorizonCode_Horizon_È("options.customizeTitle", new Object[0]);
        this.ÇŽÉ.clear();
        this.ÇŽÉ.add(this.áˆºÑ¢Õ = new GuiButton(302, 20, 5, 80, 20, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.prev", new Object[0])));
        this.ÇŽÉ.add(this.ÂµÈ = new GuiButton(303, GuiCustomizeWorldScreen.Çªà¢ - 100, 5, 80, 20, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.next", new Object[0])));
        this.ÇŽÉ.add(this.áŒŠÆ = new GuiButton(304, GuiCustomizeWorldScreen.Çªà¢ / 2 - 187, GuiCustomizeWorldScreen.Ê - 27, 90, 20, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.defaults", new Object[0])));
        this.ÇŽÉ.add(this.Ø = new GuiButton(301, GuiCustomizeWorldScreen.Çªà¢ / 2 - 92, GuiCustomizeWorldScreen.Ê - 27, 90, 20, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.randomize", new Object[0])));
        this.ÇŽÉ.add(this.£á = new GuiButton(305, GuiCustomizeWorldScreen.Çªà¢ / 2 + 3, GuiCustomizeWorldScreen.Ê - 27, 90, 20, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.presets", new Object[0])));
        this.ÇŽÉ.add(this.à = new GuiButton(300, GuiCustomizeWorldScreen.Çªà¢ / 2 + 98, GuiCustomizeWorldScreen.Ê - 27, 90, 20, I18n.HorizonCode_Horizon_È("gui.done", new Object[0])));
        this.á = new GuiButton(306, GuiCustomizeWorldScreen.Çªà¢ / 2 - 55, 160, 50, 20, I18n.HorizonCode_Horizon_È("gui.yes", new Object[0]));
        this.á.ˆà = false;
        this.ÇŽÉ.add(this.á);
        this.ˆÏ­ = new GuiButton(307, GuiCustomizeWorldScreen.Çªà¢ / 2 + 5, 160, 50, 20, I18n.HorizonCode_Horizon_È("gui.no", new Object[0]));
        this.ˆÏ­.ˆà = false;
        this.ÇŽÉ.add(this.ˆÏ­);
        this.à();
    }
    
    @Override
    public void n_() throws IOException {
        super.n_();
        this.Ó.Ø();
    }
    
    private void à() {
        final GuiPageButtonList.Âµá€[] var1 = { new GuiPageButtonList.à(160, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.seaLevel", new Object[0]), true, this, 1.0f, 255.0f, this.Ø­à.ˆà), new GuiPageButtonList.Â(148, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.useCaves", new Object[0]), true, this.Ø­à.¥Æ), new GuiPageButtonList.Â(150, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.useStrongholds", new Object[0]), true, this.Ø­à.Æ), new GuiPageButtonList.Â(151, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.useVillages", new Object[0]), true, this.Ø­à.Šáƒ), new GuiPageButtonList.Â(152, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.useMineShafts", new Object[0]), true, this.Ø­à.Ï­Ðƒà), new GuiPageButtonList.Â(153, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.useTemples", new Object[0]), true, this.Ø­à.áŒŠà), new GuiPageButtonList.Â(210, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.useMonuments", new Object[0]), true, this.Ø­à.ŠÄ), new GuiPageButtonList.Â(154, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.useRavines", new Object[0]), true, this.Ø­à.Ñ¢á), new GuiPageButtonList.Â(149, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.useDungeons", new Object[0]), true, this.Ø­à.Ø­à), new GuiPageButtonList.à(157, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.dungeonChance", new Object[0]), true, this, 1.0f, 100.0f, this.Ø­à.µÕ), new GuiPageButtonList.Â(155, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.useWaterLakes", new Object[0]), true, this.Ø­à.ŒÏ), new GuiPageButtonList.à(158, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.waterLakeChance", new Object[0]), true, this, 1.0f, 100.0f, this.Ø­à.Çªà¢), new GuiPageButtonList.Â(156, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.useLavaLakes", new Object[0]), true, this.Ø­à.Ê), new GuiPageButtonList.à(159, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.lavaLakeChance", new Object[0]), true, this, 10.0f, 100.0f, this.Ø­à.ÇŽÉ), new GuiPageButtonList.Â(161, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.useLavaOceans", new Object[0]), true, this.Ø­à.ˆá), new GuiPageButtonList.à(162, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.fixedBiome", new Object[0]), true, this, -1.0f, 37.0f, this.Ø­à.ÇŽÕ), new GuiPageButtonList.à(163, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.biomeSize", new Object[0]), true, this, 1.0f, 8.0f, this.Ø­à.É), new GuiPageButtonList.à(164, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.riverSize", new Object[0]), true, this, 1.0f, 5.0f, this.Ø­à.áƒ) };
        final GuiPageButtonList.Âµá€[] var2 = { new GuiPageButtonList.Ø­áŒŠá(416, I18n.HorizonCode_Horizon_È("tile.dirt.name", new Object[0]), false), null, new GuiPageButtonList.à(165, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.size", new Object[0]), false, this, 1.0f, 50.0f, this.Ø­à.á€), new GuiPageButtonList.à(166, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.count", new Object[0]), false, this, 0.0f, 40.0f, this.Ø­à.Õ), new GuiPageButtonList.à(167, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.minHeight", new Object[0]), false, this, 0.0f, 255.0f, this.Ø­à.à¢), new GuiPageButtonList.à(168, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.maxHeight", new Object[0]), false, this, 0.0f, 255.0f, this.Ø­à.ŠÂµà), new GuiPageButtonList.Ø­áŒŠá(417, I18n.HorizonCode_Horizon_È("tile.gravel.name", new Object[0]), false), null, new GuiPageButtonList.à(169, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.size", new Object[0]), false, this, 1.0f, 50.0f, this.Ø­à.¥à), new GuiPageButtonList.à(170, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.count", new Object[0]), false, this, 0.0f, 40.0f, this.Ø­à.Âµà), new GuiPageButtonList.à(171, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.minHeight", new Object[0]), false, this, 0.0f, 255.0f, this.Ø­à.Ç), new GuiPageButtonList.à(172, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.maxHeight", new Object[0]), false, this, 0.0f, 255.0f, this.Ø­à.È), new GuiPageButtonList.Ø­áŒŠá(418, I18n.HorizonCode_Horizon_È("tile.stone.granite.name", new Object[0]), false), null, new GuiPageButtonList.à(173, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.size", new Object[0]), false, this, 1.0f, 50.0f, this.Ø­à.áŠ), new GuiPageButtonList.à(174, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.count", new Object[0]), false, this, 0.0f, 40.0f, this.Ø­à.ˆáŠ), new GuiPageButtonList.à(175, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.minHeight", new Object[0]), false, this, 0.0f, 255.0f, this.Ø­à.áŒŠ), new GuiPageButtonList.à(176, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.maxHeight", new Object[0]), false, this, 0.0f, 255.0f, this.Ø­à.£ÂµÄ), new GuiPageButtonList.Ø­áŒŠá(419, I18n.HorizonCode_Horizon_È("tile.stone.diorite.name", new Object[0]), false), null, new GuiPageButtonList.à(177, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.size", new Object[0]), false, this, 1.0f, 50.0f, this.Ø­à.Ø­Âµ), new GuiPageButtonList.à(178, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.count", new Object[0]), false, this, 0.0f, 40.0f, this.Ø­à.Ä), new GuiPageButtonList.à(179, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.minHeight", new Object[0]), false, this, 0.0f, 255.0f, this.Ø­à.Ñ¢Â), new GuiPageButtonList.à(180, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.maxHeight", new Object[0]), false, this, 0.0f, 255.0f, this.Ø­à.Ï­à), new GuiPageButtonList.Ø­áŒŠá(420, I18n.HorizonCode_Horizon_È("tile.stone.andesite.name", new Object[0]), false), null, new GuiPageButtonList.à(181, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.size", new Object[0]), false, this, 1.0f, 50.0f, this.Ø­à.áˆºáˆºÈ), new GuiPageButtonList.à(182, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.count", new Object[0]), false, this, 0.0f, 40.0f, this.Ø­à.ÇŽá€), new GuiPageButtonList.à(183, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.minHeight", new Object[0]), false, this, 0.0f, 255.0f, this.Ø­à.Ï), new GuiPageButtonList.à(184, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.maxHeight", new Object[0]), false, this, 0.0f, 255.0f, this.Ø­à.Ô), new GuiPageButtonList.Ø­áŒŠá(421, I18n.HorizonCode_Horizon_È("tile.oreCoal.name", new Object[0]), false), null, new GuiPageButtonList.à(185, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.size", new Object[0]), false, this, 1.0f, 50.0f, this.Ø­à.ÇªÓ), new GuiPageButtonList.à(186, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.count", new Object[0]), false, this, 0.0f, 40.0f, this.Ø­à.áˆºÏ), new GuiPageButtonList.à(187, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.minHeight", new Object[0]), false, this, 0.0f, 255.0f, this.Ø­à.ˆáƒ), new GuiPageButtonList.à(189, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.maxHeight", new Object[0]), false, this, 0.0f, 255.0f, this.Ø­à.Œ), new GuiPageButtonList.Ø­áŒŠá(422, I18n.HorizonCode_Horizon_È("tile.oreIron.name", new Object[0]), false), null, new GuiPageButtonList.à(190, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.size", new Object[0]), false, this, 1.0f, 50.0f, this.Ø­à.£Ï), new GuiPageButtonList.à(191, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.count", new Object[0]), false, this, 0.0f, 40.0f, this.Ø­à.Ø­á), new GuiPageButtonList.à(192, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.minHeight", new Object[0]), false, this, 0.0f, 255.0f, this.Ø­à.ˆÉ), new GuiPageButtonList.à(193, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.maxHeight", new Object[0]), false, this, 0.0f, 255.0f, this.Ø­à.Ï­Ï­Ï), new GuiPageButtonList.Ø­áŒŠá(423, I18n.HorizonCode_Horizon_È("tile.oreGold.name", new Object[0]), false), null, new GuiPageButtonList.à(194, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.size", new Object[0]), false, this, 1.0f, 50.0f, this.Ø­à.£Â), new GuiPageButtonList.à(195, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.count", new Object[0]), false, this, 0.0f, 40.0f, this.Ø­à.£Ó), new GuiPageButtonList.à(196, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.minHeight", new Object[0]), false, this, 0.0f, 255.0f, this.Ø­à.ˆÐƒØ­à), new GuiPageButtonList.à(197, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.maxHeight", new Object[0]), false, this, 0.0f, 255.0f, this.Ø­à.£Õ), new GuiPageButtonList.Ø­áŒŠá(424, I18n.HorizonCode_Horizon_È("tile.oreRedstone.name", new Object[0]), false), null, new GuiPageButtonList.à(198, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.size", new Object[0]), false, this, 1.0f, 50.0f, this.Ø­à.Ï­Ô), new GuiPageButtonList.à(199, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.count", new Object[0]), false, this, 0.0f, 40.0f, this.Ø­à.Œà), new GuiPageButtonList.à(200, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.minHeight", new Object[0]), false, this, 0.0f, 255.0f, this.Ø­à.Ðƒá), new GuiPageButtonList.à(201, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.maxHeight", new Object[0]), false, this, 0.0f, 255.0f, this.Ø­à.ˆÏ), new GuiPageButtonList.Ø­áŒŠá(425, I18n.HorizonCode_Horizon_È("tile.oreDiamond.name", new Object[0]), false), null, new GuiPageButtonList.à(202, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.size", new Object[0]), false, this, 1.0f, 50.0f, this.Ø­à.áˆºÇŽØ), new GuiPageButtonList.à(203, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.count", new Object[0]), false, this, 0.0f, 40.0f, this.Ø­à.ÇªÂµÕ), new GuiPageButtonList.à(204, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.minHeight", new Object[0]), false, this, 0.0f, 255.0f, this.Ø­à.áŒŠÏ), new GuiPageButtonList.à(205, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.maxHeight", new Object[0]), false, this, 0.0f, 255.0f, this.Ø­à.áŒŠáŠ), new GuiPageButtonList.Ø­áŒŠá(426, I18n.HorizonCode_Horizon_È("tile.oreLapis.name", new Object[0]), false), null, new GuiPageButtonList.à(206, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.size", new Object[0]), false, this, 1.0f, 50.0f, this.Ø­à.ˆÓ), new GuiPageButtonList.à(207, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.count", new Object[0]), false, this, 0.0f, 40.0f, this.Ø­à.¥Ä), new GuiPageButtonList.à(208, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.center", new Object[0]), false, this, 0.0f, 255.0f, this.Ø­à.ÇªÔ), new GuiPageButtonList.à(209, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.spread", new Object[0]), false, this, 0.0f, 255.0f, this.Ø­à.Û) };
        final GuiPageButtonList.Âµá€[] var3 = { new GuiPageButtonList.à(100, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.mainNoiseScaleX", new Object[0]), false, this, 1.0f, 5000.0f, this.Ø­à.áŒŠÆ), new GuiPageButtonList.à(101, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.mainNoiseScaleY", new Object[0]), false, this, 1.0f, 5000.0f, this.Ø­à.áˆºÑ¢Õ), new GuiPageButtonList.à(102, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.mainNoiseScaleZ", new Object[0]), false, this, 1.0f, 5000.0f, this.Ø­à.ÂµÈ), new GuiPageButtonList.à(103, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.depthNoiseScaleX", new Object[0]), false, this, 1.0f, 2000.0f, this.Ø­à.Ó), new GuiPageButtonList.à(104, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.depthNoiseScaleZ", new Object[0]), false, this, 1.0f, 2000.0f, this.Ø­à.à), new GuiPageButtonList.à(105, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.depthNoiseScaleExponent", new Object[0]), false, this, 0.01f, 20.0f, this.Ø­à.Ø), new GuiPageButtonList.à(106, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.baseSize", new Object[0]), false, this, 1.0f, 25.0f, this.Ø­à.á), new GuiPageButtonList.à(107, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.coordinateScale", new Object[0]), false, this, 1.0f, 6000.0f, this.Ø­à.Â), new GuiPageButtonList.à(108, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.heightScale", new Object[0]), false, this, 1.0f, 6000.0f, this.Ø­à.Ý), new GuiPageButtonList.à(109, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.stretchY", new Object[0]), false, this, 0.01f, 50.0f, this.Ø­à.ˆÏ­), new GuiPageButtonList.à(110, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.upperLimitScale", new Object[0]), false, this, 1.0f, 5000.0f, this.Ø­à.Ø­áŒŠá), new GuiPageButtonList.à(111, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.lowerLimitScale", new Object[0]), false, this, 1.0f, 5000.0f, this.Ø­à.Âµá€), new GuiPageButtonList.à(112, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.biomeDepthWeight", new Object[0]), false, this, 1.0f, 20.0f, this.Ø­à.£á), new GuiPageButtonList.à(113, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.biomeDepthOffset", new Object[0]), false, this, 0.0f, 20.0f, this.Ø­à.Å), new GuiPageButtonList.à(114, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.biomeScaleWeight", new Object[0]), false, this, 1.0f, 20.0f, this.Ø­à.£à), new GuiPageButtonList.à(115, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.biomeScaleOffset", new Object[0]), false, this, 0.0f, 20.0f, this.Ø­à.µà) };
        final GuiPageButtonList.Âµá€[] var4 = { new GuiPageButtonList.Ø­áŒŠá(400, String.valueOf(I18n.HorizonCode_Horizon_È("createWorld.customize.custom.mainNoiseScaleX", new Object[0])) + ":", false), new GuiPageButtonList.HorizonCode_Horizon_È(132, String.format("%5.3f", this.Ø­à.áŒŠÆ), false, this.ˆà), new GuiPageButtonList.Ø­áŒŠá(401, String.valueOf(I18n.HorizonCode_Horizon_È("createWorld.customize.custom.mainNoiseScaleY", new Object[0])) + ":", false), new GuiPageButtonList.HorizonCode_Horizon_È(133, String.format("%5.3f", this.Ø­à.áˆºÑ¢Õ), false, this.ˆà), new GuiPageButtonList.Ø­áŒŠá(402, String.valueOf(I18n.HorizonCode_Horizon_È("createWorld.customize.custom.mainNoiseScaleZ", new Object[0])) + ":", false), new GuiPageButtonList.HorizonCode_Horizon_È(134, String.format("%5.3f", this.Ø­à.ÂµÈ), false, this.ˆà), new GuiPageButtonList.Ø­áŒŠá(403, String.valueOf(I18n.HorizonCode_Horizon_È("createWorld.customize.custom.depthNoiseScaleX", new Object[0])) + ":", false), new GuiPageButtonList.HorizonCode_Horizon_È(135, String.format("%5.3f", this.Ø­à.Ó), false, this.ˆà), new GuiPageButtonList.Ø­áŒŠá(404, String.valueOf(I18n.HorizonCode_Horizon_È("createWorld.customize.custom.depthNoiseScaleZ", new Object[0])) + ":", false), new GuiPageButtonList.HorizonCode_Horizon_È(136, String.format("%5.3f", this.Ø­à.à), false, this.ˆà), new GuiPageButtonList.Ø­áŒŠá(405, String.valueOf(I18n.HorizonCode_Horizon_È("createWorld.customize.custom.depthNoiseScaleExponent", new Object[0])) + ":", false), new GuiPageButtonList.HorizonCode_Horizon_È(137, String.format("%2.3f", this.Ø­à.Ø), false, this.ˆà), new GuiPageButtonList.Ø­áŒŠá(406, String.valueOf(I18n.HorizonCode_Horizon_È("createWorld.customize.custom.baseSize", new Object[0])) + ":", false), new GuiPageButtonList.HorizonCode_Horizon_È(138, String.format("%2.3f", this.Ø­à.á), false, this.ˆà), new GuiPageButtonList.Ø­áŒŠá(407, String.valueOf(I18n.HorizonCode_Horizon_È("createWorld.customize.custom.coordinateScale", new Object[0])) + ":", false), new GuiPageButtonList.HorizonCode_Horizon_È(139, String.format("%5.3f", this.Ø­à.Â), false, this.ˆà), new GuiPageButtonList.Ø­áŒŠá(408, String.valueOf(I18n.HorizonCode_Horizon_È("createWorld.customize.custom.heightScale", new Object[0])) + ":", false), new GuiPageButtonList.HorizonCode_Horizon_È(140, String.format("%5.3f", this.Ø­à.Ý), false, this.ˆà), new GuiPageButtonList.Ø­áŒŠá(409, String.valueOf(I18n.HorizonCode_Horizon_È("createWorld.customize.custom.stretchY", new Object[0])) + ":", false), new GuiPageButtonList.HorizonCode_Horizon_È(141, String.format("%2.3f", this.Ø­à.ˆÏ­), false, this.ˆà), new GuiPageButtonList.Ø­áŒŠá(410, String.valueOf(I18n.HorizonCode_Horizon_È("createWorld.customize.custom.upperLimitScale", new Object[0])) + ":", false), new GuiPageButtonList.HorizonCode_Horizon_È(142, String.format("%5.3f", this.Ø­à.Ø­áŒŠá), false, this.ˆà), new GuiPageButtonList.Ø­áŒŠá(411, String.valueOf(I18n.HorizonCode_Horizon_È("createWorld.customize.custom.lowerLimitScale", new Object[0])) + ":", false), new GuiPageButtonList.HorizonCode_Horizon_È(143, String.format("%5.3f", this.Ø­à.Âµá€), false, this.ˆà), new GuiPageButtonList.Ø­áŒŠá(412, String.valueOf(I18n.HorizonCode_Horizon_È("createWorld.customize.custom.biomeDepthWeight", new Object[0])) + ":", false), new GuiPageButtonList.HorizonCode_Horizon_È(144, String.format("%2.3f", this.Ø­à.£á), false, this.ˆà), new GuiPageButtonList.Ø­áŒŠá(413, String.valueOf(I18n.HorizonCode_Horizon_È("createWorld.customize.custom.biomeDepthOffset", new Object[0])) + ":", false), new GuiPageButtonList.HorizonCode_Horizon_È(145, String.format("%2.3f", this.Ø­à.Å), false, this.ˆà), new GuiPageButtonList.Ø­áŒŠá(414, String.valueOf(I18n.HorizonCode_Horizon_È("createWorld.customize.custom.biomeScaleWeight", new Object[0])) + ":", false), new GuiPageButtonList.HorizonCode_Horizon_È(146, String.format("%2.3f", this.Ø­à.£à), false, this.ˆà), new GuiPageButtonList.Ø­áŒŠá(415, String.valueOf(I18n.HorizonCode_Horizon_È("createWorld.customize.custom.biomeScaleOffset", new Object[0])) + ":", false), new GuiPageButtonList.HorizonCode_Horizon_È(147, String.format("%2.3f", this.Ø­à.µà), false, this.ˆà) };
        this.Ó = new GuiPageButtonList(GuiCustomizeWorldScreen.Ñ¢á, GuiCustomizeWorldScreen.Çªà¢, GuiCustomizeWorldScreen.Ê, 32, GuiCustomizeWorldScreen.Ê - 32, 25, this, new GuiPageButtonList.Âµá€[][] { var1, var2, var3, var4 });
        for (int var5 = 0; var5 < 4; ++var5) {
            this.Ø­áŒŠá[var5] = I18n.HorizonCode_Horizon_È("createWorld.customize.custom.page" + var5, new Object[0]);
        }
        this.ˆà();
    }
    
    public String Ó() {
        return this.Ø­à.toString().replace("\n", "");
    }
    
    public void HorizonCode_Horizon_È(final String p_175324_1_) {
        if (p_175324_1_ != null && p_175324_1_.length() != 0) {
            this.Ø­à = ChunkProviderSettings.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_175324_1_);
        }
        else {
            this.Ø­à = new ChunkProviderSettings.HorizonCode_Horizon_È();
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int p_175319_1_, final String p_175319_2_) {
        float var3 = 0.0f;
        try {
            var3 = Float.parseFloat(p_175319_2_);
        }
        catch (NumberFormatException ex) {}
        float var4 = 0.0f;
        switch (p_175319_1_) {
            case 132: {
                final ChunkProviderSettings.HorizonCode_Horizon_È ø­à = this.Ø­à;
                final float horizonCode_Horizon_È = MathHelper.HorizonCode_Horizon_È(var3, 1.0f, 5000.0f);
                ø­à.áŒŠÆ = horizonCode_Horizon_È;
                var4 = horizonCode_Horizon_È;
                break;
            }
            case 133: {
                final ChunkProviderSettings.HorizonCode_Horizon_È ø­à2 = this.Ø­à;
                final float horizonCode_Horizon_È2 = MathHelper.HorizonCode_Horizon_È(var3, 1.0f, 5000.0f);
                ø­à2.áˆºÑ¢Õ = horizonCode_Horizon_È2;
                var4 = horizonCode_Horizon_È2;
                break;
            }
            case 134: {
                final ChunkProviderSettings.HorizonCode_Horizon_È ø­à3 = this.Ø­à;
                final float horizonCode_Horizon_È3 = MathHelper.HorizonCode_Horizon_È(var3, 1.0f, 5000.0f);
                ø­à3.ÂµÈ = horizonCode_Horizon_È3;
                var4 = horizonCode_Horizon_È3;
                break;
            }
            case 135: {
                final ChunkProviderSettings.HorizonCode_Horizon_È ø­à4 = this.Ø­à;
                final float horizonCode_Horizon_È4 = MathHelper.HorizonCode_Horizon_È(var3, 1.0f, 2000.0f);
                ø­à4.Ó = horizonCode_Horizon_È4;
                var4 = horizonCode_Horizon_È4;
                break;
            }
            case 136: {
                final ChunkProviderSettings.HorizonCode_Horizon_È ø­à5 = this.Ø­à;
                final float horizonCode_Horizon_È5 = MathHelper.HorizonCode_Horizon_È(var3, 1.0f, 2000.0f);
                ø­à5.à = horizonCode_Horizon_È5;
                var4 = horizonCode_Horizon_È5;
                break;
            }
            case 137: {
                final ChunkProviderSettings.HorizonCode_Horizon_È ø­à6 = this.Ø­à;
                final float horizonCode_Horizon_È6 = MathHelper.HorizonCode_Horizon_È(var3, 0.01f, 20.0f);
                ø­à6.Ø = horizonCode_Horizon_È6;
                var4 = horizonCode_Horizon_È6;
                break;
            }
            case 138: {
                final ChunkProviderSettings.HorizonCode_Horizon_È ø­à7 = this.Ø­à;
                final float horizonCode_Horizon_È7 = MathHelper.HorizonCode_Horizon_È(var3, 1.0f, 25.0f);
                ø­à7.á = horizonCode_Horizon_È7;
                var4 = horizonCode_Horizon_È7;
                break;
            }
            case 139: {
                final ChunkProviderSettings.HorizonCode_Horizon_È ø­à8 = this.Ø­à;
                final float horizonCode_Horizon_È8 = MathHelper.HorizonCode_Horizon_È(var3, 1.0f, 6000.0f);
                ø­à8.Â = horizonCode_Horizon_È8;
                var4 = horizonCode_Horizon_È8;
                break;
            }
            case 140: {
                final ChunkProviderSettings.HorizonCode_Horizon_È ø­à9 = this.Ø­à;
                final float horizonCode_Horizon_È9 = MathHelper.HorizonCode_Horizon_È(var3, 1.0f, 6000.0f);
                ø­à9.Ý = horizonCode_Horizon_È9;
                var4 = horizonCode_Horizon_È9;
                break;
            }
            case 141: {
                final ChunkProviderSettings.HorizonCode_Horizon_È ø­à10 = this.Ø­à;
                final float horizonCode_Horizon_È10 = MathHelper.HorizonCode_Horizon_È(var3, 0.01f, 50.0f);
                ø­à10.ˆÏ­ = horizonCode_Horizon_È10;
                var4 = horizonCode_Horizon_È10;
                break;
            }
            case 142: {
                final ChunkProviderSettings.HorizonCode_Horizon_È ø­à11 = this.Ø­à;
                final float horizonCode_Horizon_È11 = MathHelper.HorizonCode_Horizon_È(var3, 1.0f, 5000.0f);
                ø­à11.Ø­áŒŠá = horizonCode_Horizon_È11;
                var4 = horizonCode_Horizon_È11;
                break;
            }
            case 143: {
                final ChunkProviderSettings.HorizonCode_Horizon_È ø­à12 = this.Ø­à;
                final float horizonCode_Horizon_È12 = MathHelper.HorizonCode_Horizon_È(var3, 1.0f, 5000.0f);
                ø­à12.Âµá€ = horizonCode_Horizon_È12;
                var4 = horizonCode_Horizon_È12;
                break;
            }
            case 144: {
                final ChunkProviderSettings.HorizonCode_Horizon_È ø­à13 = this.Ø­à;
                final float horizonCode_Horizon_È13 = MathHelper.HorizonCode_Horizon_È(var3, 1.0f, 20.0f);
                ø­à13.£á = horizonCode_Horizon_È13;
                var4 = horizonCode_Horizon_È13;
                break;
            }
            case 145: {
                final ChunkProviderSettings.HorizonCode_Horizon_È ø­à14 = this.Ø­à;
                final float horizonCode_Horizon_È14 = MathHelper.HorizonCode_Horizon_È(var3, 0.0f, 20.0f);
                ø­à14.Å = horizonCode_Horizon_È14;
                var4 = horizonCode_Horizon_È14;
                break;
            }
            case 146: {
                final ChunkProviderSettings.HorizonCode_Horizon_È ø­à15 = this.Ø­à;
                final float horizonCode_Horizon_È15 = MathHelper.HorizonCode_Horizon_È(var3, 1.0f, 20.0f);
                ø­à15.£à = horizonCode_Horizon_È15;
                var4 = horizonCode_Horizon_È15;
                break;
            }
            case 147: {
                final ChunkProviderSettings.HorizonCode_Horizon_È ø­à16 = this.Ø­à;
                final float horizonCode_Horizon_È16 = MathHelper.HorizonCode_Horizon_È(var3, 0.0f, 20.0f);
                ø­à16.µà = horizonCode_Horizon_È16;
                var4 = horizonCode_Horizon_È16;
                break;
            }
        }
        if (var4 != var3 && var3 != 0.0f) {
            ((GuiTextField)this.Ó.Ý(p_175319_1_)).HorizonCode_Horizon_È(this.Â(p_175319_1_, var4));
        }
        ((GuiSlider)this.Ó.Ý(p_175319_1_ - 132 + 100)).HorizonCode_Horizon_È(var4, false);
        if (!this.Ø­à.equals(this.¥Æ)) {
            this.Å = true;
        }
    }
    
    @Override
    public String HorizonCode_Horizon_È(final int p_175318_1_, final String p_175318_2_, final float p_175318_3_) {
        return String.valueOf(p_175318_2_) + ": " + this.Â(p_175318_1_, p_175318_3_);
    }
    
    private String Â(final int p_175330_1_, final float p_175330_2_) {
        switch (p_175330_1_) {
            case 100:
            case 101:
            case 102:
            case 103:
            case 104:
            case 107:
            case 108:
            case 110:
            case 111:
            case 132:
            case 133:
            case 134:
            case 135:
            case 136:
            case 139:
            case 140:
            case 142:
            case 143: {
                return String.format("%5.3f", p_175330_2_);
            }
            case 105:
            case 106:
            case 109:
            case 112:
            case 113:
            case 114:
            case 115:
            case 137:
            case 138:
            case 141:
            case 144:
            case 145:
            case 146:
            case 147: {
                return String.format("%2.3f", p_175330_2_);
            }
            default: {
                return String.format("%d", (int)p_175330_2_);
            }
            case 162: {
                if (p_175330_2_ < 0.0f) {
                    return I18n.HorizonCode_Horizon_È("gui.all", new Object[0]);
                }
                if ((int)p_175330_2_ >= BiomeGenBase.Ï­Ðƒà.ÇªÔ) {
                    final BiomeGenBase var3 = BiomeGenBase.£á()[(int)p_175330_2_ + 2];
                    return (var3 != null) ? var3.£Ï : "?";
                }
                final BiomeGenBase var3 = BiomeGenBase.£á()[(int)p_175330_2_];
                return (var3 != null) ? var3.£Ï : "?";
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int p_175321_1_, final boolean p_175321_2_) {
        switch (p_175321_1_) {
            case 148: {
                this.Ø­à.¥Æ = p_175321_2_;
                break;
            }
            case 149: {
                this.Ø­à.Ø­à = p_175321_2_;
                break;
            }
            case 150: {
                this.Ø­à.Æ = p_175321_2_;
                break;
            }
            case 151: {
                this.Ø­à.Šáƒ = p_175321_2_;
                break;
            }
            case 152: {
                this.Ø­à.Ï­Ðƒà = p_175321_2_;
                break;
            }
            case 153: {
                this.Ø­à.áŒŠà = p_175321_2_;
                break;
            }
            case 154: {
                this.Ø­à.Ñ¢á = p_175321_2_;
                break;
            }
            case 155: {
                this.Ø­à.ŒÏ = p_175321_2_;
                break;
            }
            case 156: {
                this.Ø­à.Ê = p_175321_2_;
                break;
            }
            case 161: {
                this.Ø­à.ˆá = p_175321_2_;
                break;
            }
            case 210: {
                this.Ø­à.ŠÄ = p_175321_2_;
                break;
            }
        }
        if (!this.Ø­à.equals(this.¥Æ)) {
            this.Å = true;
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int p_175320_1_, final float p_175320_2_) {
        switch (p_175320_1_) {
            case 100: {
                this.Ø­à.áŒŠÆ = p_175320_2_;
                break;
            }
            case 101: {
                this.Ø­à.áˆºÑ¢Õ = p_175320_2_;
                break;
            }
            case 102: {
                this.Ø­à.ÂµÈ = p_175320_2_;
                break;
            }
            case 103: {
                this.Ø­à.Ó = p_175320_2_;
                break;
            }
            case 104: {
                this.Ø­à.à = p_175320_2_;
                break;
            }
            case 105: {
                this.Ø­à.Ø = p_175320_2_;
                break;
            }
            case 106: {
                this.Ø­à.á = p_175320_2_;
                break;
            }
            case 107: {
                this.Ø­à.Â = p_175320_2_;
                break;
            }
            case 108: {
                this.Ø­à.Ý = p_175320_2_;
                break;
            }
            case 109: {
                this.Ø­à.ˆÏ­ = p_175320_2_;
                break;
            }
            case 110: {
                this.Ø­à.Ø­áŒŠá = p_175320_2_;
                break;
            }
            case 111: {
                this.Ø­à.Âµá€ = p_175320_2_;
                break;
            }
            case 112: {
                this.Ø­à.£á = p_175320_2_;
                break;
            }
            case 113: {
                this.Ø­à.Å = p_175320_2_;
                break;
            }
            case 114: {
                this.Ø­à.£à = p_175320_2_;
                break;
            }
            case 115: {
                this.Ø­à.µà = p_175320_2_;
                break;
            }
            case 157: {
                this.Ø­à.µÕ = (int)p_175320_2_;
                break;
            }
            case 158: {
                this.Ø­à.Çªà¢ = (int)p_175320_2_;
                break;
            }
            case 159: {
                this.Ø­à.ÇŽÉ = (int)p_175320_2_;
                break;
            }
            case 160: {
                this.Ø­à.ˆà = (int)p_175320_2_;
                break;
            }
            case 162: {
                this.Ø­à.ÇŽÕ = (int)p_175320_2_;
                break;
            }
            case 163: {
                this.Ø­à.É = (int)p_175320_2_;
                break;
            }
            case 164: {
                this.Ø­à.áƒ = (int)p_175320_2_;
                break;
            }
            case 165: {
                this.Ø­à.á€ = (int)p_175320_2_;
                break;
            }
            case 166: {
                this.Ø­à.Õ = (int)p_175320_2_;
                break;
            }
            case 167: {
                this.Ø­à.à¢ = (int)p_175320_2_;
                break;
            }
            case 168: {
                this.Ø­à.ŠÂµà = (int)p_175320_2_;
                break;
            }
            case 169: {
                this.Ø­à.¥à = (int)p_175320_2_;
                break;
            }
            case 170: {
                this.Ø­à.Âµà = (int)p_175320_2_;
                break;
            }
            case 171: {
                this.Ø­à.Ç = (int)p_175320_2_;
                break;
            }
            case 172: {
                this.Ø­à.È = (int)p_175320_2_;
                break;
            }
            case 173: {
                this.Ø­à.áŠ = (int)p_175320_2_;
                break;
            }
            case 174: {
                this.Ø­à.ˆáŠ = (int)p_175320_2_;
                break;
            }
            case 175: {
                this.Ø­à.áŒŠ = (int)p_175320_2_;
                break;
            }
            case 176: {
                this.Ø­à.£ÂµÄ = (int)p_175320_2_;
                break;
            }
            case 177: {
                this.Ø­à.Ø­Âµ = (int)p_175320_2_;
                break;
            }
            case 178: {
                this.Ø­à.Ä = (int)p_175320_2_;
                break;
            }
            case 179: {
                this.Ø­à.Ñ¢Â = (int)p_175320_2_;
                break;
            }
            case 180: {
                this.Ø­à.Ï­à = (int)p_175320_2_;
                break;
            }
            case 181: {
                this.Ø­à.áˆºáˆºÈ = (int)p_175320_2_;
                break;
            }
            case 182: {
                this.Ø­à.ÇŽá€ = (int)p_175320_2_;
                break;
            }
            case 183: {
                this.Ø­à.Ï = (int)p_175320_2_;
                break;
            }
            case 184: {
                this.Ø­à.Ô = (int)p_175320_2_;
                break;
            }
            case 185: {
                this.Ø­à.ÇªÓ = (int)p_175320_2_;
                break;
            }
            case 186: {
                this.Ø­à.áˆºÏ = (int)p_175320_2_;
                break;
            }
            case 187: {
                this.Ø­à.ˆáƒ = (int)p_175320_2_;
                break;
            }
            case 189: {
                this.Ø­à.Œ = (int)p_175320_2_;
                break;
            }
            case 190: {
                this.Ø­à.£Ï = (int)p_175320_2_;
                break;
            }
            case 191: {
                this.Ø­à.Ø­á = (int)p_175320_2_;
                break;
            }
            case 192: {
                this.Ø­à.ˆÉ = (int)p_175320_2_;
                break;
            }
            case 193: {
                this.Ø­à.Ï­Ï­Ï = (int)p_175320_2_;
                break;
            }
            case 194: {
                this.Ø­à.£Â = (int)p_175320_2_;
                break;
            }
            case 195: {
                this.Ø­à.£Ó = (int)p_175320_2_;
                break;
            }
            case 196: {
                this.Ø­à.ˆÐƒØ­à = (int)p_175320_2_;
                break;
            }
            case 197: {
                this.Ø­à.£Õ = (int)p_175320_2_;
                break;
            }
            case 198: {
                this.Ø­à.Ï­Ô = (int)p_175320_2_;
                break;
            }
            case 199: {
                this.Ø­à.Œà = (int)p_175320_2_;
                break;
            }
            case 200: {
                this.Ø­à.Ðƒá = (int)p_175320_2_;
                break;
            }
            case 201: {
                this.Ø­à.ˆÏ = (int)p_175320_2_;
                break;
            }
            case 202: {
                this.Ø­à.áˆºÇŽØ = (int)p_175320_2_;
                break;
            }
            case 203: {
                this.Ø­à.ÇªÂµÕ = (int)p_175320_2_;
                break;
            }
            case 204: {
                this.Ø­à.áŒŠÏ = (int)p_175320_2_;
                break;
            }
            case 205: {
                this.Ø­à.áŒŠáŠ = (int)p_175320_2_;
                break;
            }
            case 206: {
                this.Ø­à.ˆÓ = (int)p_175320_2_;
                break;
            }
            case 207: {
                this.Ø­à.¥Ä = (int)p_175320_2_;
                break;
            }
            case 208: {
                this.Ø­à.ÇªÔ = (int)p_175320_2_;
                break;
            }
            case 209: {
                this.Ø­à.Û = (int)p_175320_2_;
                break;
            }
        }
        if (p_175320_1_ >= 100 && p_175320_1_ < 116) {
            final Gui_1808253012 var3 = this.Ó.Ý(p_175320_1_ - 100 + 132);
            if (var3 != null) {
                ((GuiTextField)var3).HorizonCode_Horizon_È(this.Â(p_175320_1_, p_175320_2_));
            }
        }
        if (!this.Ø­à.equals(this.¥Æ)) {
            this.Å = true;
        }
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final GuiButton button) throws IOException {
        if (button.µà) {
            switch (button.£à) {
                case 300: {
                    this.Âµá€.HorizonCode_Horizon_È = this.Ø­à.toString();
                    GuiCustomizeWorldScreen.Ñ¢á.HorizonCode_Horizon_È(this.Âµá€);
                    break;
                }
                case 301: {
                    for (int var2 = 0; var2 < this.Ó.HorizonCode_Horizon_È(); ++var2) {
                        final GuiPageButtonList.Ý var3 = this.Ó.Ø­áŒŠá(var2);
                        final Gui_1808253012 var4 = var3.HorizonCode_Horizon_È();
                        if (var4 instanceof GuiButton) {
                            final GuiButton var5 = (GuiButton)var4;
                            if (var5 instanceof GuiSlider) {
                                final float var6 = ((GuiSlider)var5).Â() * (0.75f + this.µÕ.nextFloat() * 0.5f) + (this.µÕ.nextFloat() * 0.1f - 0.05f);
                                ((GuiSlider)var5).HorizonCode_Horizon_È(MathHelper.HorizonCode_Horizon_È(var6, 0.0f, 1.0f));
                            }
                            else if (var5 instanceof GuiListButton) {
                                ((GuiListButton)var5).Â(this.µÕ.nextBoolean());
                            }
                        }
                        final Gui_1808253012 var7 = var3.Â();
                        if (var7 instanceof GuiButton) {
                            final GuiButton var8 = (GuiButton)var7;
                            if (var8 instanceof GuiSlider) {
                                final float var9 = ((GuiSlider)var8).Â() * (0.75f + this.µÕ.nextFloat() * 0.5f) + (this.µÕ.nextFloat() * 0.1f - 0.05f);
                                ((GuiSlider)var8).HorizonCode_Horizon_È(MathHelper.HorizonCode_Horizon_È(var9, 0.0f, 1.0f));
                            }
                            else if (var8 instanceof GuiListButton) {
                                ((GuiListButton)var8).Â(this.µÕ.nextBoolean());
                            }
                        }
                    }
                }
                case 302: {
                    this.Ó.áˆºÑ¢Õ();
                    this.ˆà();
                    break;
                }
                case 303: {
                    this.Ó.ÂµÈ();
                    this.ˆà();
                    break;
                }
                case 304: {
                    if (this.Å) {
                        this.HorizonCode_Horizon_È(304);
                        break;
                    }
                    break;
                }
                case 305: {
                    GuiCustomizeWorldScreen.Ñ¢á.HorizonCode_Horizon_È(new GuiScreenCustomizePresets(this));
                    break;
                }
                case 306: {
                    this.áŒŠÆ();
                    break;
                }
                case 307: {
                    this.£à = 0;
                    this.áŒŠÆ();
                    break;
                }
            }
        }
    }
    
    private void Ø() {
        this.Ø­à.HorizonCode_Horizon_È();
        this.à();
    }
    
    private void HorizonCode_Horizon_È(final int p_175322_1_) {
        this.£à = p_175322_1_;
        this.HorizonCode_Horizon_È(true);
    }
    
    private void áŒŠÆ() throws IOException {
        switch (this.£à) {
            case 300: {
                this.HorizonCode_Horizon_È((GuiButton)this.Ó.Ý(300));
                break;
            }
            case 304: {
                this.Ø();
                break;
            }
        }
        this.£à = 0;
        this.µà = true;
        this.HorizonCode_Horizon_È(false);
    }
    
    private void HorizonCode_Horizon_È(final boolean p_175329_1_) {
        this.á.ˆà = p_175329_1_;
        this.ˆÏ­.ˆà = p_175329_1_;
        this.Ø.µà = !p_175329_1_;
        this.à.µà = !p_175329_1_;
        this.áˆºÑ¢Õ.µà = !p_175329_1_;
        this.ÂµÈ.µà = !p_175329_1_;
        this.áŒŠÆ.µà = !p_175329_1_;
        this.£á.µà = !p_175329_1_;
    }
    
    private void ˆà() {
        this.áˆºÑ¢Õ.µà = (this.Ó.Ø­áŒŠá() != 0);
        this.ÂµÈ.µà = (this.Ó.Ø­áŒŠá() != this.Ó.Âµá€() - 1);
        this.Â = I18n.HorizonCode_Horizon_È("book.pageIndicator", this.Ó.Ø­áŒŠá() + 1, this.Ó.Âµá€());
        this.Ý = this.Ø­áŒŠá[this.Ó.Ø­áŒŠá()];
        this.Ø.µà = (this.Ó.Ø­áŒŠá() != this.Ó.Âµá€() - 1);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final char typedChar, final int keyCode) throws IOException {
        super.HorizonCode_Horizon_È(typedChar, keyCode);
        if (this.£à == 0) {
            switch (keyCode) {
                case 200: {
                    this.HorizonCode_Horizon_È(1.0f);
                    break;
                }
                case 208: {
                    this.HorizonCode_Horizon_È(-1.0f);
                    break;
                }
                default: {
                    this.Ó.HorizonCode_Horizon_È(typedChar, keyCode);
                    break;
                }
            }
        }
    }
    
    private void HorizonCode_Horizon_È(final float p_175327_1_) {
        final Gui_1808253012 var2 = this.Ó.áŒŠÆ();
        if (var2 instanceof GuiTextField) {
            float var3 = p_175327_1_;
            if (GuiScreen.£à()) {
                var3 = p_175327_1_ * 0.1f;
                if (GuiScreen.Å()) {
                    var3 *= 0.1f;
                }
            }
            else if (GuiScreen.Å()) {
                var3 = p_175327_1_ * 10.0f;
                if (GuiScreen.µà()) {
                    var3 *= 10.0f;
                }
            }
            final GuiTextField var4 = (GuiTextField)var2;
            Float var5 = Floats.tryParse(var4.Ý());
            if (var5 != null) {
                var5 += var3;
                final int var6 = var4.Âµá€();
                final String var7 = this.Â(var4.Âµá€(), var5);
                var4.HorizonCode_Horizon_È(var7);
                this.HorizonCode_Horizon_È(var6, var7);
            }
        }
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.HorizonCode_Horizon_È(mouseX, mouseY, mouseButton);
        if (this.£à == 0 && !this.µà) {
            this.Ó.Â(mouseX, mouseY, mouseButton);
        }
    }
    
    @Override
    protected void Â(final int mouseX, final int mouseY, final int state) {
        super.Â(mouseX, mouseY, state);
        if (this.µà) {
            this.µà = false;
        }
        else if (this.£à == 0) {
            this.Ó.Ý(mouseX, mouseY, state);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final float partialTicks) {
        this.£á();
        this.Ó.HorizonCode_Horizon_È(mouseX, mouseY, partialTicks);
        this.HorizonCode_Horizon_È(this.É, this.HorizonCode_Horizon_È, GuiCustomizeWorldScreen.Çªà¢ / 2, 2, 16777215);
        this.HorizonCode_Horizon_È(this.É, this.Â, GuiCustomizeWorldScreen.Çªà¢ / 2, 12, 16777215);
        this.HorizonCode_Horizon_È(this.É, this.Ý, GuiCustomizeWorldScreen.Çªà¢ / 2, 22, 16777215);
        super.HorizonCode_Horizon_È(mouseX, mouseY, partialTicks);
        if (this.£à != 0) {
            Gui_1808253012.HorizonCode_Horizon_È(0, 0, GuiCustomizeWorldScreen.Çªà¢, GuiCustomizeWorldScreen.Ê, Integer.MIN_VALUE);
            this.HorizonCode_Horizon_È(GuiCustomizeWorldScreen.Çªà¢ / 2 - 91, GuiCustomizeWorldScreen.Çªà¢ / 2 + 90, 99, -2039584);
            this.HorizonCode_Horizon_È(GuiCustomizeWorldScreen.Çªà¢ / 2 - 91, GuiCustomizeWorldScreen.Çªà¢ / 2 + 90, 185, -6250336);
            this.Â(GuiCustomizeWorldScreen.Çªà¢ / 2 - 91, 99, 185, -2039584);
            this.Â(GuiCustomizeWorldScreen.Çªà¢ / 2 + 90, 99, 185, -6250336);
            final float var4 = 85.0f;
            final float var5 = 180.0f;
            GlStateManager.Ó();
            GlStateManager.£á();
            final Tessellator var6 = Tessellator.HorizonCode_Horizon_È();
            final WorldRenderer var7 = var6.Ý();
            GuiCustomizeWorldScreen.Ñ¢á.¥à().HorizonCode_Horizon_È(GuiCustomizeWorldScreen.Šáƒ);
            GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
            final float var8 = 32.0f;
            var7.Â();
            var7.Ý(4210752);
            var7.HorizonCode_Horizon_È(GuiCustomizeWorldScreen.Çªà¢ / 2 - 90, 185.0, 0.0, 0.0, 2.65625);
            var7.HorizonCode_Horizon_È(GuiCustomizeWorldScreen.Çªà¢ / 2 + 90, 185.0, 0.0, 5.625, 2.65625);
            var7.HorizonCode_Horizon_È(GuiCustomizeWorldScreen.Çªà¢ / 2 + 90, 100.0, 0.0, 5.625, 0.0);
            var7.HorizonCode_Horizon_È(GuiCustomizeWorldScreen.Çªà¢ / 2 - 90, 100.0, 0.0, 0.0, 0.0);
            var6.Â();
            this.HorizonCode_Horizon_È(this.É, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.confirmTitle", new Object[0]), GuiCustomizeWorldScreen.Çªà¢ / 2, 105, 16777215);
            this.HorizonCode_Horizon_È(this.É, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.confirm1", new Object[0]), GuiCustomizeWorldScreen.Çªà¢ / 2, 125, 16777215);
            this.HorizonCode_Horizon_È(this.É, I18n.HorizonCode_Horizon_È("createWorld.customize.custom.confirm2", new Object[0]), GuiCustomizeWorldScreen.Çªà¢ / 2, 135, 16777215);
            this.á.Ý(GuiCustomizeWorldScreen.Ñ¢á, mouseX, mouseY);
            this.ˆÏ­.Ý(GuiCustomizeWorldScreen.Ñ¢á, mouseX, mouseY);
        }
    }
}

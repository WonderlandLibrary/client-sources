package net.minecraft.src;

import org.lwjgl.input.*;
import java.util.*;

public class GuiFlatPresets extends GuiScreen
{
    private static RenderItem presetIconRenderer;
    private static final List presets;
    private final GuiCreateFlatWorld createFlatWorldGui;
    private String field_82300_d;
    private String field_82308_m;
    private String field_82306_n;
    private GuiFlatPresetsListSlot theFlatPresetsListSlot;
    private GuiButton theButton;
    private GuiTextField theTextField;
    
    static {
        GuiFlatPresets.presetIconRenderer = new RenderItem();
        presets = new ArrayList();
        addPreset("Classic Flat", Block.grass.blockID, BiomeGenBase.plains, Arrays.asList("village"), new FlatLayerInfo(1, Block.grass.blockID), new FlatLayerInfo(2, Block.dirt.blockID), new FlatLayerInfo(1, Block.bedrock.blockID));
        addPreset("Tunnelers' Dream", Block.stone.blockID, BiomeGenBase.extremeHills, Arrays.asList("biome_1", "dungeon", "decoration", "stronghold", "mineshaft"), new FlatLayerInfo(1, Block.grass.blockID), new FlatLayerInfo(5, Block.dirt.blockID), new FlatLayerInfo(230, Block.stone.blockID), new FlatLayerInfo(1, Block.bedrock.blockID));
        addPreset("Water World", Block.waterMoving.blockID, BiomeGenBase.plains, Arrays.asList("village", "biome_1"), new FlatLayerInfo(90, Block.waterStill.blockID), new FlatLayerInfo(5, Block.sand.blockID), new FlatLayerInfo(5, Block.dirt.blockID), new FlatLayerInfo(5, Block.stone.blockID), new FlatLayerInfo(1, Block.bedrock.blockID));
        addPreset("Overworld", Block.tallGrass.blockID, BiomeGenBase.plains, Arrays.asList("village", "biome_1", "decoration", "stronghold", "mineshaft", "dungeon", "lake", "lava_lake"), new FlatLayerInfo(1, Block.grass.blockID), new FlatLayerInfo(3, Block.dirt.blockID), new FlatLayerInfo(59, Block.stone.blockID), new FlatLayerInfo(1, Block.bedrock.blockID));
        addPreset("Snowy Kingdom", Block.snow.blockID, BiomeGenBase.icePlains, Arrays.asList("village", "biome_1"), new FlatLayerInfo(1, Block.snow.blockID), new FlatLayerInfo(1, Block.grass.blockID), new FlatLayerInfo(3, Block.dirt.blockID), new FlatLayerInfo(59, Block.stone.blockID), new FlatLayerInfo(1, Block.bedrock.blockID));
        addPreset("Bottomless Pit", Item.feather.itemID, BiomeGenBase.plains, Arrays.asList("village", "biome_1"), new FlatLayerInfo(1, Block.grass.blockID), new FlatLayerInfo(3, Block.dirt.blockID), new FlatLayerInfo(2, Block.cobblestone.blockID));
        addPreset("Desert", Block.sand.blockID, BiomeGenBase.desert, Arrays.asList("village", "biome_1", "decoration", "stronghold", "mineshaft", "dungeon"), new FlatLayerInfo(8, Block.sand.blockID), new FlatLayerInfo(52, Block.sandStone.blockID), new FlatLayerInfo(3, Block.stone.blockID), new FlatLayerInfo(1, Block.bedrock.blockID));
        addPresetNoFeatures("Redstone Ready", Item.redstone.itemID, BiomeGenBase.desert, new FlatLayerInfo(52, Block.sandStone.blockID), new FlatLayerInfo(3, Block.stone.blockID), new FlatLayerInfo(1, Block.bedrock.blockID));
    }
    
    public GuiFlatPresets(final GuiCreateFlatWorld par1) {
        this.createFlatWorldGui = par1;
    }
    
    @Override
    public void initGui() {
        this.buttonList.clear();
        Keyboard.enableRepeatEvents(true);
        this.field_82300_d = StatCollector.translateToLocal("createWorld.customize.presets.title");
        this.field_82308_m = StatCollector.translateToLocal("createWorld.customize.presets.share");
        this.field_82306_n = StatCollector.translateToLocal("createWorld.customize.presets.list");
        this.theTextField = new GuiTextField(this.fontRenderer, 50, 40, this.width - 100, 20);
        this.theFlatPresetsListSlot = new GuiFlatPresetsListSlot(this);
        this.theTextField.setMaxStringLength(1230);
        this.theTextField.setText(this.createFlatWorldGui.getFlatGeneratorInfo());
        this.buttonList.add(this.theButton = new GuiButton(0, this.width / 2 - 155, this.height - 28, 150, 20, StatCollector.translateToLocal("createWorld.customize.presets.select")));
        this.buttonList.add(new GuiButton(1, this.width / 2 + 5, this.height - 28, 150, 20, StatCollector.translateToLocal("gui.cancel")));
        this.func_82296_g();
    }
    
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }
    
    @Override
    protected void mouseClicked(final int par1, final int par2, final int par3) {
        this.theTextField.mouseClicked(par1, par2, par3);
        super.mouseClicked(par1, par2, par3);
    }
    
    @Override
    protected void keyTyped(final char par1, final int par2) {
        if (!this.theTextField.textboxKeyTyped(par1, par2)) {
            super.keyTyped(par1, par2);
        }
    }
    
    @Override
    protected void actionPerformed(final GuiButton par1GuiButton) {
        if (par1GuiButton.id == 0 && this.func_82293_j()) {
            this.createFlatWorldGui.setFlatGeneratorInfo(this.theTextField.getText());
            this.mc.displayGuiScreen(this.createFlatWorldGui);
        }
        else if (par1GuiButton.id == 1) {
            this.mc.displayGuiScreen(this.createFlatWorldGui);
        }
    }
    
    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        this.drawDefaultBackground();
        this.theFlatPresetsListSlot.drawScreen(par1, par2, par3);
        this.drawCenteredString(this.fontRenderer, this.field_82300_d, this.width / 2, 8, 16777215);
        this.drawString(this.fontRenderer, this.field_82308_m, 50, 30, 10526880);
        this.drawString(this.fontRenderer, this.field_82306_n, 50, 70, 10526880);
        this.theTextField.drawTextBox();
        super.drawScreen(par1, par2, par3);
    }
    
    @Override
    public void updateScreen() {
        this.theTextField.updateCursorCounter();
        super.updateScreen();
    }
    
    public void func_82296_g() {
        final boolean var1 = this.func_82293_j();
        this.theButton.enabled = var1;
    }
    
    private boolean func_82293_j() {
        return (this.theFlatPresetsListSlot.field_82459_a > -1 && this.theFlatPresetsListSlot.field_82459_a < GuiFlatPresets.presets.size()) || this.theTextField.getText().length() > 1;
    }
    
    private static void addPresetNoFeatures(final String par0Str, final int par1, final BiomeGenBase par2BiomeGenBase, final FlatLayerInfo... par3ArrayOfFlatLayerInfo) {
        addPreset(par0Str, par1, par2BiomeGenBase, null, par3ArrayOfFlatLayerInfo);
    }
    
    private static void addPreset(final String par0Str, final int par1, final BiomeGenBase par2BiomeGenBase, final List par3List, final FlatLayerInfo... par4ArrayOfFlatLayerInfo) {
        final FlatGeneratorInfo var5 = new FlatGeneratorInfo();
        for (int var6 = par4ArrayOfFlatLayerInfo.length - 1; var6 >= 0; --var6) {
            var5.getFlatLayers().add(par4ArrayOfFlatLayerInfo[var6]);
        }
        var5.setBiome(par2BiomeGenBase.biomeID);
        var5.func_82645_d();
        if (par3List != null) {
            for (final String var8 : par3List) {
                var5.getWorldFeatures().put(var8, new HashMap());
            }
        }
        GuiFlatPresets.presets.add(new GuiFlatPresetsItem(par1, par0Str, var5.toString()));
    }
    
    static RenderItem getPresetIconRenderer() {
        return GuiFlatPresets.presetIconRenderer;
    }
    
    static List getPresets() {
        return GuiFlatPresets.presets;
    }
    
    static GuiFlatPresetsListSlot func_82292_a(final GuiFlatPresets par0GuiFlatPresets) {
        return par0GuiFlatPresets.theFlatPresetsListSlot;
    }
    
    static GuiTextField func_82298_b(final GuiFlatPresets par0GuiFlatPresets) {
        return par0GuiFlatPresets.theTextField;
    }
}

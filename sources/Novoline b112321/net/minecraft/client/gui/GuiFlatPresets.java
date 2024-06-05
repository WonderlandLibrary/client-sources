package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import net.minecraft.block.BlockTallGrass$EnumType;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiCreateFlatWorld;
import net.minecraft.client.gui.GuiFlatPresets$ListSlot;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.FlatGeneratorInfo;
import net.minecraft.world.gen.FlatLayerInfo;
import org.lwjgl.input.Keyboard;

public class GuiFlatPresets extends GuiScreen {
   private static final List FLAT_WORLD_PRESETS = Lists.newArrayList();
   private final GuiCreateFlatWorld parentScreen;
   private String presetsTitle;
   private String presetsShare;
   private String field_146436_r;
   private GuiFlatPresets$ListSlot field_146435_s;
   private GuiButton field_146434_t;
   private GuiTextField field_146433_u;

   public GuiFlatPresets(GuiCreateFlatWorld var1) {
      this.parentScreen = var1;
   }

   public void initGui() {
      this.buttonList.clear();
      Keyboard.enableRepeatEvents(true);
      this.presetsTitle = I18n.format("createWorld.customize.presets.title", new Object[0]);
      this.presetsShare = I18n.format("createWorld.customize.presets.share", new Object[0]);
      this.field_146436_r = I18n.format("createWorld.customize.presets.list", new Object[0]);
      this.field_146433_u = new GuiTextField(2, this.fontRendererObj, 50, 40, this.width - 100, 20);
      this.field_146435_s = new GuiFlatPresets$ListSlot(this);
      this.field_146433_u.setMaxStringLength(1230);
      this.field_146433_u.setText(this.parentScreen.func_146384_e());
      this.buttonList.add(this.field_146434_t = new GuiButton(0, this.width / 2 - 155, this.height - 28, 150, 20, I18n.format("createWorld.customize.presets.select", new Object[0])));
      this.buttonList.add(new GuiButton(1, this.width / 2 + 5, this.height - 28, 150, 20, I18n.format("gui.cancel", new Object[0])));
      this.func_146426_g();
   }

   public void handleMouseInput() throws IOException {
      super.handleMouseInput();
      this.field_146435_s.handleMouseInput();
   }

   public void onGuiClosed() {
      Keyboard.enableRepeatEvents(false);
   }

   protected void mouseClicked(int var1, int var2, int var3) throws IOException {
      this.field_146433_u.mouseClicked(var1, var2, var3);
      super.mouseClicked(var1, var2, var3);
   }

   protected void keyTyped(char var1, int var2) throws IOException {
      if(!this.field_146433_u.textboxKeyTyped(var1, var2)) {
         super.keyTyped(var1, var2);
      }

   }

   protected void actionPerformed(GuiButton var1) throws IOException {
      if(var1.id == 0 && this.func_146430_p()) {
         this.parentScreen.func_146383_a(this.field_146433_u.getText());
         this.mc.displayGuiScreen(this.parentScreen);
      } else if(var1.id == 1) {
         this.mc.displayGuiScreen(this.parentScreen);
      }

   }

   public void drawScreen(int var1, int var2, float var3) {
      this.drawDefaultBackground();
      this.field_146435_s.drawScreen(var1, var2, var3);
      this.drawCenteredString(this.fontRendererObj, this.presetsTitle, this.width / 2, 8, 16777215);
      this.drawString(this.fontRendererObj, this.presetsShare, 50, 30, 10526880);
      this.drawString(this.fontRendererObj, this.field_146436_r, 50, 70, 10526880);
      this.field_146433_u.drawTextBox();
      super.drawScreen(var1, var2, var3);
   }

   public void updateScreen() {
      this.field_146433_u.updateCursorCounter();
      super.updateScreen();
   }

   public void func_146426_g() {
      boolean var1 = this.func_146430_p();
      this.field_146434_t.enabled = var1;
   }

   private boolean func_146430_p() {
      return this.field_146435_s.field_148175_k > -1 && this.field_146435_s.field_148175_k < FLAT_WORLD_PRESETS.size() || this.field_146433_u.getText().length() > 1;
   }

   private static void func_146425_a(String var0, Item var1, BiomeGenBase var2, FlatLayerInfo... var3) {
      a(var0, var1, 0, var2, (List)null, var3);
   }

   private static void func_146421_a(String var0, Item var1, BiomeGenBase var2, List var3, FlatLayerInfo... var4) {
      a(var0, var1, 0, var2, var3, var4);
   }

   private static void a(String var0, Item var1, int var2, BiomeGenBase var3, List var4, FlatLayerInfo... var5) {
      FlatGeneratorInfo var6 = new FlatGeneratorInfo();
      int var7 = var5.length - 1;

      while(true) {
         var6.getFlatLayers().add(var5[var7]);
         --var7;
      }
   }

   static List access$000() {
      return FLAT_WORLD_PRESETS;
   }

   static GuiFlatPresets$ListSlot access$100(GuiFlatPresets var0) {
      return var0.field_146435_s;
   }

   static GuiTextField access$200(GuiFlatPresets var0) {
      return var0.field_146433_u;
   }

   static {
      func_146421_a("Classic Flat", Item.getItemFromBlock(Blocks.grass), BiomeGenBase.plains, Arrays.asList(new String[]{"village"}), new FlatLayerInfo[]{new FlatLayerInfo(1, Blocks.grass), new FlatLayerInfo(2, Blocks.dirt), new FlatLayerInfo(1, Blocks.bedrock)});
      func_146421_a("Tunnelers\' Dream", Item.getItemFromBlock(Blocks.stone), BiomeGenBase.extremeHills, Arrays.asList(new String[]{"biome_1", "dungeon", "decoration", "stronghold", "mineshaft"}), new FlatLayerInfo[]{new FlatLayerInfo(1, Blocks.grass), new FlatLayerInfo(5, Blocks.dirt), new FlatLayerInfo(230, Blocks.stone), new FlatLayerInfo(1, Blocks.bedrock)});
      func_146421_a("Water World", Items.water_bucket, BiomeGenBase.deepOcean, Arrays.asList(new String[]{"biome_1", "oceanmonument"}), new FlatLayerInfo[]{new FlatLayerInfo(90, Blocks.water), new FlatLayerInfo(5, Blocks.sand), new FlatLayerInfo(5, Blocks.dirt), new FlatLayerInfo(5, Blocks.stone), new FlatLayerInfo(1, Blocks.bedrock)});
      a("Overworld", Item.getItemFromBlock(Blocks.tallgrass), BlockTallGrass$EnumType.GRASS.getMeta(), BiomeGenBase.plains, Arrays.asList(new String[]{"village", "biome_1", "decoration", "stronghold", "mineshaft", "dungeon", "lake", "lava_lake"}), new FlatLayerInfo[]{new FlatLayerInfo(1, Blocks.grass), new FlatLayerInfo(3, Blocks.dirt), new FlatLayerInfo(59, Blocks.stone), new FlatLayerInfo(1, Blocks.bedrock)});
      func_146421_a("Snowy Kingdom", Item.getItemFromBlock(Blocks.snow_layer), BiomeGenBase.icePlains, Arrays.asList(new String[]{"village", "biome_1"}), new FlatLayerInfo[]{new FlatLayerInfo(1, Blocks.snow_layer), new FlatLayerInfo(1, Blocks.grass), new FlatLayerInfo(3, Blocks.dirt), new FlatLayerInfo(59, Blocks.stone), new FlatLayerInfo(1, Blocks.bedrock)});
      func_146421_a("Bottomless Pit", Items.feather, BiomeGenBase.plains, Arrays.asList(new String[]{"village", "biome_1"}), new FlatLayerInfo[]{new FlatLayerInfo(1, Blocks.grass), new FlatLayerInfo(3, Blocks.dirt), new FlatLayerInfo(2, Blocks.cobblestone)});
      func_146421_a("Desert", Item.getItemFromBlock(Blocks.sand), BiomeGenBase.desert, Arrays.asList(new String[]{"village", "biome_1", "decoration", "stronghold", "mineshaft", "dungeon"}), new FlatLayerInfo[]{new FlatLayerInfo(8, Blocks.sand), new FlatLayerInfo(52, Blocks.sandstone), new FlatLayerInfo(3, Blocks.stone), new FlatLayerInfo(1, Blocks.bedrock)});
      func_146425_a("Redstone Ready", Items.redstone, BiomeGenBase.desert, new FlatLayerInfo[]{new FlatLayerInfo(52, Blocks.sandstone), new FlatLayerInfo(3, Blocks.stone), new FlatLayerInfo(1, Blocks.bedrock)});
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}

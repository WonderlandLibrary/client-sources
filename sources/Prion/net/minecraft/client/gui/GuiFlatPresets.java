package net.minecraft.client.gui;

import com.google.common.collect.Maps;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.BlockTallGrass.EnumType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.FlatGeneratorInfo;
import net.minecraft.world.gen.FlatLayerInfo;
import org.lwjgl.input.Keyboard;

public class GuiFlatPresets extends GuiScreen
{
  private static final List field_146431_f = ;
  private final GuiCreateFlatWorld field_146432_g;
  private String field_146438_h;
  private String field_146439_i;
  private String field_146436_r;
  private ListSlot field_146435_s;
  private GuiButton field_146434_t;
  private GuiTextField field_146433_u;
  private static final String __OBFID = "CL_00000704";
  
  public GuiFlatPresets(GuiCreateFlatWorld p_i46318_1_)
  {
    field_146432_g = p_i46318_1_;
  }
  



  public void initGui()
  {
    buttonList.clear();
    Keyboard.enableRepeatEvents(true);
    field_146438_h = I18n.format("createWorld.customize.presets.title", new Object[0]);
    field_146439_i = I18n.format("createWorld.customize.presets.share", new Object[0]);
    field_146436_r = I18n.format("createWorld.customize.presets.list", new Object[0]);
    field_146433_u = new GuiTextField(2, fontRendererObj, 50, 40, width - 100, 20);
    field_146435_s = new ListSlot();
    field_146433_u.setMaxStringLength(1230);
    field_146433_u.setText(field_146432_g.func_146384_e());
    buttonList.add(this.field_146434_t = new GuiButton(0, width / 2 - 155, height - 28, 150, 20, I18n.format("createWorld.customize.presets.select", new Object[0])));
    buttonList.add(new GuiButton(1, width / 2 + 5, height - 28, 150, 20, I18n.format("gui.cancel", new Object[0])));
    func_146426_g();
  }
  


  public void handleMouseInput()
    throws IOException
  {
    super.handleMouseInput();
    field_146435_s.func_178039_p();
  }
  



  public void onGuiClosed()
  {
    Keyboard.enableRepeatEvents(false);
  }
  


  protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    throws IOException
  {
    field_146433_u.mouseClicked(mouseX, mouseY, mouseButton);
    super.mouseClicked(mouseX, mouseY, mouseButton);
  }
  



  protected void keyTyped(char typedChar, int keyCode)
    throws IOException
  {
    if (!field_146433_u.textboxKeyTyped(typedChar, keyCode))
    {
      super.keyTyped(typedChar, keyCode);
    }
  }
  
  protected void actionPerformed(GuiButton button) throws IOException
  {
    if ((id == 0) && (func_146430_p()))
    {
      field_146432_g.func_146383_a(field_146433_u.getText());
      mc.displayGuiScreen(field_146432_g);
    }
    else if (id == 1)
    {
      mc.displayGuiScreen(field_146432_g);
    }
  }
  



  public void drawScreen(int mouseX, int mouseY, float partialTicks)
  {
    drawDefaultBackground();
    field_146435_s.drawScreen(mouseX, mouseY, partialTicks);
    drawCenteredString(fontRendererObj, field_146438_h, width / 2, 8, 16777215);
    drawString(fontRendererObj, field_146439_i, 50, 30, 10526880);
    drawString(fontRendererObj, field_146436_r, 50, 70, 10526880);
    field_146433_u.drawTextBox();
    super.drawScreen(mouseX, mouseY, partialTicks);
  }
  



  public void updateScreen()
  {
    field_146433_u.updateCursorCounter();
    super.updateScreen();
  }
  
  public void func_146426_g()
  {
    boolean var1 = func_146430_p();
    field_146434_t.enabled = var1;
  }
  
  private boolean func_146430_p()
  {
    return ((field_146435_s.field_148175_k > -1) && (field_146435_s.field_148175_k < field_146431_f.size())) || (field_146433_u.getText().length() > 1);
  }
  
  private static void func_146425_a(String p_146425_0_, Item p_146425_1_, BiomeGenBase p_146425_2_, FlatLayerInfo... p_146425_3_)
  {
    func_175354_a(p_146425_0_, p_146425_1_, 0, p_146425_2_, null, p_146425_3_);
  }
  
  private static void func_146421_a(String p_146421_0_, Item p_146421_1_, BiomeGenBase p_146421_2_, List p_146421_3_, FlatLayerInfo... p_146421_4_)
  {
    func_175354_a(p_146421_0_, p_146421_1_, 0, p_146421_2_, p_146421_3_, p_146421_4_);
  }
  
  private static void func_175354_a(String p_175354_0_, Item p_175354_1_, int p_175354_2_, BiomeGenBase p_175354_3_, List p_175354_4_, FlatLayerInfo... p_175354_5_)
  {
    FlatGeneratorInfo var6 = new FlatGeneratorInfo();
    
    for (int var7 = p_175354_5_.length - 1; var7 >= 0; var7--)
    {
      var6.getFlatLayers().add(p_175354_5_[var7]);
    }
    
    var6.setBiome(biomeID);
    var6.func_82645_d();
    
    if (p_175354_4_ != null)
    {
      Iterator var9 = p_175354_4_.iterator();
      
      while (var9.hasNext())
      {
        String var8 = (String)var9.next();
        var6.getWorldFeatures().put(var8, Maps.newHashMap());
      }
    }
    
    field_146431_f.add(new LayerItem(p_175354_1_, p_175354_2_, p_175354_0_, var6.toString()));
  }
  
  static
  {
    func_146421_a("Classic Flat", Item.getItemFromBlock(Blocks.grass), BiomeGenBase.plains, Arrays.asList(new String[] { "village" }), new FlatLayerInfo[] { new FlatLayerInfo(1, Blocks.grass), new FlatLayerInfo(2, Blocks.dirt), new FlatLayerInfo(1, Blocks.bedrock) });
    func_146421_a("Tunnelers' Dream", Item.getItemFromBlock(Blocks.stone), BiomeGenBase.extremeHills, Arrays.asList(new String[] { "biome_1", "dungeon", "decoration", "stronghold", "mineshaft" }), new FlatLayerInfo[] { new FlatLayerInfo(1, Blocks.grass), new FlatLayerInfo(5, Blocks.dirt), new FlatLayerInfo(230, Blocks.stone), new FlatLayerInfo(1, Blocks.bedrock) });
    func_146421_a("Water World", Items.water_bucket, BiomeGenBase.deepOcean, Arrays.asList(new String[] { "biome_1", "oceanmonument" }), new FlatLayerInfo[] { new FlatLayerInfo(90, Blocks.water), new FlatLayerInfo(5, Blocks.sand), new FlatLayerInfo(5, Blocks.dirt), new FlatLayerInfo(5, Blocks.stone), new FlatLayerInfo(1, Blocks.bedrock) });
    func_175354_a("Overworld", Item.getItemFromBlock(Blocks.tallgrass), BlockTallGrass.EnumType.GRASS.func_177044_a(), BiomeGenBase.plains, Arrays.asList(new String[] { "village", "biome_1", "decoration", "stronghold", "mineshaft", "dungeon", "lake", "lava_lake" }), new FlatLayerInfo[] { new FlatLayerInfo(1, Blocks.grass), new FlatLayerInfo(3, Blocks.dirt), new FlatLayerInfo(59, Blocks.stone), new FlatLayerInfo(1, Blocks.bedrock) });
    func_146421_a("Snowy Kingdom", Item.getItemFromBlock(Blocks.snow_layer), BiomeGenBase.icePlains, Arrays.asList(new String[] { "village", "biome_1" }), new FlatLayerInfo[] { new FlatLayerInfo(1, Blocks.snow_layer), new FlatLayerInfo(1, Blocks.grass), new FlatLayerInfo(3, Blocks.dirt), new FlatLayerInfo(59, Blocks.stone), new FlatLayerInfo(1, Blocks.bedrock) });
    func_146421_a("Bottomless Pit", Items.feather, BiomeGenBase.plains, Arrays.asList(new String[] { "village", "biome_1" }), new FlatLayerInfo[] { new FlatLayerInfo(1, Blocks.grass), new FlatLayerInfo(3, Blocks.dirt), new FlatLayerInfo(2, Blocks.cobblestone) });
    func_146421_a("Desert", Item.getItemFromBlock(Blocks.sand), BiomeGenBase.desert, Arrays.asList(new String[] { "village", "biome_1", "decoration", "stronghold", "mineshaft", "dungeon" }), new FlatLayerInfo[] { new FlatLayerInfo(8, Blocks.sand), new FlatLayerInfo(52, Blocks.sandstone), new FlatLayerInfo(3, Blocks.stone), new FlatLayerInfo(1, Blocks.bedrock) });
    func_146425_a("Redstone Ready", Items.redstone, BiomeGenBase.desert, new FlatLayerInfo[] { new FlatLayerInfo(52, Blocks.sandstone), new FlatLayerInfo(3, Blocks.stone), new FlatLayerInfo(1, Blocks.bedrock) });
  }
  
  static class LayerItem
  {
    public Item field_148234_a;
    public int field_179037_b;
    public String field_148232_b;
    public String field_148233_c;
    private static final String __OBFID = "CL_00000705";
    
    public LayerItem(Item p_i45518_1_, int p_i45518_2_, String p_i45518_3_, String p_i45518_4_)
    {
      field_148234_a = p_i45518_1_;
      field_179037_b = p_i45518_2_;
      field_148232_b = p_i45518_3_;
      field_148233_c = p_i45518_4_;
    }
  }
  
  class ListSlot extends GuiSlot
  {
    public int field_148175_k = -1;
    private static final String __OBFID = "CL_00000706";
    
    public ListSlot()
    {
      super(width, height, 80, height - 37, 24);
    }
    
    private void func_178054_a(int p_178054_1_, int p_178054_2_, Item p_178054_3_, int p_178054_4_)
    {
      func_148173_e(p_178054_1_ + 1, p_178054_2_ + 1);
      GlStateManager.enableRescaleNormal();
      RenderHelper.enableGUIStandardItemLighting();
      itemRender.func_175042_a(new ItemStack(p_178054_3_, 1, p_178054_4_), p_178054_1_ + 2, p_178054_2_ + 2);
      RenderHelper.disableStandardItemLighting();
      GlStateManager.disableRescaleNormal();
    }
    
    private void func_148173_e(int p_148173_1_, int p_148173_2_)
    {
      func_148171_c(p_148173_1_, p_148173_2_, 0, 0);
    }
    
    private void func_148171_c(int p_148171_1_, int p_148171_2_, int p_148171_3_, int p_148171_4_)
    {
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      mc.getTextureManager().bindTexture(Gui.statIcons);
      float var5 = 0.0078125F;
      float var6 = 0.0078125F;
      boolean var7 = true;
      boolean var8 = true;
      Tessellator var9 = Tessellator.getInstance();
      WorldRenderer var10 = var9.getWorldRenderer();
      var10.startDrawingQuads();
      var10.addVertexWithUV(p_148171_1_ + 0, p_148171_2_ + 18, zLevel, (p_148171_3_ + 0) * 0.0078125F, (p_148171_4_ + 18) * 0.0078125F);
      var10.addVertexWithUV(p_148171_1_ + 18, p_148171_2_ + 18, zLevel, (p_148171_3_ + 18) * 0.0078125F, (p_148171_4_ + 18) * 0.0078125F);
      var10.addVertexWithUV(p_148171_1_ + 18, p_148171_2_ + 0, zLevel, (p_148171_3_ + 18) * 0.0078125F, (p_148171_4_ + 0) * 0.0078125F);
      var10.addVertexWithUV(p_148171_1_ + 0, p_148171_2_ + 0, zLevel, (p_148171_3_ + 0) * 0.0078125F, (p_148171_4_ + 0) * 0.0078125F);
      var9.draw();
    }
    
    protected int getSize()
    {
      return GuiFlatPresets.field_146431_f.size();
    }
    
    protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY)
    {
      field_148175_k = slotIndex;
      func_146426_g();
      field_146433_u.setText(field_146431_fgetfield_146435_s.field_148175_k)).field_148233_c);
    }
    
    protected boolean isSelected(int slotIndex)
    {
      return slotIndex == field_148175_k;
    }
    
    protected void drawBackground() {}
    
    protected void drawSlot(int p_180791_1_, int p_180791_2_, int p_180791_3_, int p_180791_4_, int p_180791_5_, int p_180791_6_)
    {
      GuiFlatPresets.LayerItem var7 = (GuiFlatPresets.LayerItem)GuiFlatPresets.field_146431_f.get(p_180791_1_);
      func_178054_a(p_180791_2_, p_180791_3_, field_148234_a, field_179037_b);
      fontRendererObj.drawString(field_148232_b, p_180791_2_ + 18 + 5, p_180791_3_ + 6, 16777215);
    }
  }
}

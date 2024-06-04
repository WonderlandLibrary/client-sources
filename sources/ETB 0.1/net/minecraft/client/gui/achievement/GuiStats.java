package net.minecraft.client.gui.achievement;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.gui.IProgressMeter;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityList.EntityEggInfo;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C16PacketClientStatus.EnumState;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatCrafting;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.stats.StatList;

public class GuiStats extends GuiScreen implements IProgressMeter
{
  protected GuiScreen parentScreen;
  protected String screenTitle = "Select world";
  
  private StatsGeneral generalStats;
  
  private StatsItem itemStats;
  private StatsBlock blockStats;
  private StatsMobsList mobStats;
  private StatFileWriter field_146546_t;
  private GuiSlot displaySlot;
  private boolean doesGuiPauseGame = true;
  private static final String __OBFID = "CL_00000723";
  
  public GuiStats(GuiScreen p_i1071_1_, StatFileWriter p_i1071_2_)
  {
    parentScreen = p_i1071_1_;
    field_146546_t = p_i1071_2_;
  }
  



  public void initGui()
  {
    screenTitle = I18n.format("gui.stats", new Object[0]);
    doesGuiPauseGame = true;
    mc.getNetHandler().addToSendQueue(new net.minecraft.network.play.client.C16PacketClientStatus(C16PacketClientStatus.EnumState.REQUEST_STATS));
  }
  


  public void handleMouseInput()
    throws IOException
  {
    super.handleMouseInput();
    
    if (displaySlot != null)
    {
      displaySlot.func_178039_p();
    }
  }
  
  public void func_175366_f()
  {
    generalStats = new StatsGeneral(mc);
    generalStats.registerScrollButtons(1, 1);
    itemStats = new StatsItem(mc);
    itemStats.registerScrollButtons(1, 1);
    blockStats = new StatsBlock(mc);
    blockStats.registerScrollButtons(1, 1);
    mobStats = new StatsMobsList(mc);
    mobStats.registerScrollButtons(1, 1);
  }
  
  public void createButtons()
  {
    buttonList.add(new GuiButton(0, width / 2 + 4, height - 28, 150, 20, I18n.format("gui.done", new Object[0])));
    buttonList.add(new GuiButton(1, width / 2 - 160, height - 52, 80, 20, I18n.format("stat.generalButton", new Object[0])));
    
    GuiButton var1;
    
    buttonList.add(var1 = new GuiButton(2, width / 2 - 80, height - 52, 80, 20, I18n.format("stat.blocksButton", new Object[0])));
    GuiButton var2; buttonList.add(var2 = new GuiButton(3, width / 2, height - 52, 80, 20, I18n.format("stat.itemsButton", new Object[0])));
    GuiButton var3; buttonList.add(var3 = new GuiButton(4, width / 2 + 80, height - 52, 80, 20, I18n.format("stat.mobsButton", new Object[0])));
    
    if (blockStats.getSize() == 0)
    {
      enabled = false;
    }
    
    if (itemStats.getSize() == 0)
    {
      enabled = false;
    }
    
    if (mobStats.getSize() == 0)
    {
      enabled = false;
    }
  }
  
  protected void actionPerformed(GuiButton button) throws IOException
  {
    if (enabled)
    {
      if (id == 0)
      {
        mc.displayGuiScreen(parentScreen);
      }
      else if (id == 1)
      {
        displaySlot = generalStats;
      }
      else if (id == 3)
      {
        displaySlot = itemStats;
      }
      else if (id == 2)
      {
        displaySlot = blockStats;
      }
      else if (id == 4)
      {
        displaySlot = mobStats;
      }
      else
      {
        displaySlot.actionPerformed(button);
      }
    }
  }
  



  public void drawScreen(int mouseX, int mouseY, float partialTicks)
  {
    if (doesGuiPauseGame)
    {
      drawDefaultBackground();
      drawCenteredString(fontRendererObj, I18n.format("multiplayer.downloadingStats", new Object[0]), width / 2, height / 2, 16777215);
      drawCenteredString(fontRendererObj, lanSearchStates[((int)(Minecraft.getSystemTime() / 150L % lanSearchStates.length))], width / 2, height / 2 + fontRendererObj.FONT_HEIGHT * 2, 16777215);
    }
    else
    {
      displaySlot.drawScreen(mouseX, mouseY, partialTicks);
      drawCenteredString(fontRendererObj, screenTitle, width / 2, 20, 16777215);
      super.drawScreen(mouseX, mouseY, partialTicks);
    }
  }
  
  public void doneLoading()
  {
    if (doesGuiPauseGame)
    {
      func_175366_f();
      createButtons();
      displaySlot = generalStats;
      doesGuiPauseGame = false;
    }
  }
  



  public boolean doesGuiPauseGame()
  {
    return !doesGuiPauseGame;
  }
  
  private void drawStatsScreen(int p_146521_1_, int p_146521_2_, Item p_146521_3_)
  {
    drawButtonBackground(p_146521_1_ + 1, p_146521_2_ + 1);
    GlStateManager.enableRescaleNormal();
    RenderHelper.enableGUIStandardItemLighting();
    itemRender.func_175042_a(new ItemStack(p_146521_3_, 1, 0), p_146521_1_ + 2, p_146521_2_ + 2);
    RenderHelper.disableStandardItemLighting();
    GlStateManager.disableRescaleNormal();
  }
  



  private void drawButtonBackground(int p_146531_1_, int p_146531_2_)
  {
    drawSprite(p_146531_1_, p_146531_2_, 0, 0);
  }
  



  private void drawSprite(int p_146527_1_, int p_146527_2_, int p_146527_3_, int p_146527_4_)
  {
    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    mc.getTextureManager().bindTexture(statIcons);
    float var5 = 0.0078125F;
    float var6 = 0.0078125F;
    boolean var7 = true;
    boolean var8 = true;
    Tessellator var9 = Tessellator.getInstance();
    WorldRenderer var10 = var9.getWorldRenderer();
    var10.startDrawingQuads();
    var10.addVertexWithUV(p_146527_1_ + 0, p_146527_2_ + 18, zLevel, (p_146527_3_ + 0) * 0.0078125F, (p_146527_4_ + 18) * 0.0078125F);
    var10.addVertexWithUV(p_146527_1_ + 18, p_146527_2_ + 18, zLevel, (p_146527_3_ + 18) * 0.0078125F, (p_146527_4_ + 18) * 0.0078125F);
    var10.addVertexWithUV(p_146527_1_ + 18, p_146527_2_ + 0, zLevel, (p_146527_3_ + 18) * 0.0078125F, (p_146527_4_ + 0) * 0.0078125F);
    var10.addVertexWithUV(p_146527_1_ + 0, p_146527_2_ + 0, zLevel, (p_146527_3_ + 0) * 0.0078125F, (p_146527_4_ + 0) * 0.0078125F);
    var9.draw();
  }
  
  abstract class Stats extends GuiSlot
  {
    protected int field_148218_l = -1;
    protected List statsHolder;
    protected Comparator statSorter;
    protected int field_148217_o = -1;
    protected int field_148215_p;
    private static final String __OBFID = "CL_00000730";
    
    protected Stats(Minecraft mcIn)
    {
      super(width, height, 32, height - 64, 20);
      setShowSelectionBox(false);
      setHasListHeader(true, 20);
    }
    
    protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {}
    
    protected boolean isSelected(int slotIndex)
    {
      return false;
    }
    
    protected void drawBackground()
    {
      drawDefaultBackground();
    }
    
    protected void drawListHeader(int p_148129_1_, int p_148129_2_, Tessellator p_148129_3_)
    {
      if (!org.lwjgl.input.Mouse.isButtonDown(0))
      {
        field_148218_l = -1;
      }
      
      if (field_148218_l == 0)
      {
        GuiStats.this.drawSprite(p_148129_1_ + 115 - 18, p_148129_2_ + 1, 0, 0);
      }
      else
      {
        GuiStats.this.drawSprite(p_148129_1_ + 115 - 18, p_148129_2_ + 1, 0, 18);
      }
      
      if (field_148218_l == 1)
      {
        GuiStats.this.drawSprite(p_148129_1_ + 165 - 18, p_148129_2_ + 1, 0, 0);
      }
      else
      {
        GuiStats.this.drawSprite(p_148129_1_ + 165 - 18, p_148129_2_ + 1, 0, 18);
      }
      
      if (field_148218_l == 2)
      {
        GuiStats.this.drawSprite(p_148129_1_ + 215 - 18, p_148129_2_ + 1, 0, 0);
      }
      else
      {
        GuiStats.this.drawSprite(p_148129_1_ + 215 - 18, p_148129_2_ + 1, 0, 18);
      }
      
      if (field_148217_o != -1)
      {
        short var4 = 79;
        byte var5 = 18;
        
        if (field_148217_o == 1)
        {
          var4 = 129;
        }
        else if (field_148217_o == 2)
        {
          var4 = 179;
        }
        
        if (field_148215_p == 1)
        {
          var5 = 36;
        }
        
        GuiStats.this.drawSprite(p_148129_1_ + var4, p_148129_2_ + 1, var5, 0);
      }
    }
    
    protected void func_148132_a(int p_148132_1_, int p_148132_2_)
    {
      field_148218_l = -1;
      
      if ((p_148132_1_ >= 79) && (p_148132_1_ < 115))
      {
        field_148218_l = 0;
      }
      else if ((p_148132_1_ >= 129) && (p_148132_1_ < 165))
      {
        field_148218_l = 1;
      }
      else if ((p_148132_1_ >= 179) && (p_148132_1_ < 215))
      {
        field_148218_l = 2;
      }
      
      if (field_148218_l >= 0)
      {
        func_148212_h(field_148218_l);
        mc.getSoundHandler().playSound(net.minecraft.client.audio.PositionedSoundRecord.createPositionedSoundRecord(new net.minecraft.util.ResourceLocation("gui.button.press"), 1.0F));
      }
    }
    
    protected final int getSize()
    {
      return statsHolder.size();
    }
    
    protected final StatCrafting func_148211_c(int p_148211_1_)
    {
      return (StatCrafting)statsHolder.get(p_148211_1_);
    }
    

    protected abstract String func_148210_b(int paramInt);
    

    protected void func_148209_a(StatBase p_148209_1_, int p_148209_2_, int p_148209_3_, boolean p_148209_4_)
    {
      if (p_148209_1_ != null)
      {
        String var5 = p_148209_1_.func_75968_a(field_146546_t.writeStat(p_148209_1_));
        drawString(fontRendererObj, var5, p_148209_2_ - fontRendererObj.getStringWidth(var5), p_148209_3_ + 5, p_148209_4_ ? 16777215 : 9474192);
      }
      else
      {
        String var5 = "-";
        drawString(fontRendererObj, var5, p_148209_2_ - fontRendererObj.getStringWidth(var5), p_148209_3_ + 5, p_148209_4_ ? 16777215 : 9474192);
      }
    }
    
    protected void func_148142_b(int p_148142_1_, int p_148142_2_)
    {
      if ((p_148142_2_ >= top) && (p_148142_2_ <= bottom))
      {
        int var3 = getSlotIndexFromScreenCoords(p_148142_1_, p_148142_2_);
        int var4 = width / 2 - 92 - 16;
        
        if (var3 >= 0)
        {
          if ((p_148142_1_ < var4 + 40) || (p_148142_1_ > var4 + 40 + 20))
          {
            return;
          }
          
          StatCrafting var5 = func_148211_c(var3);
          func_148213_a(var5, p_148142_1_, p_148142_2_);
        }
        else
        {
          String var9 = "";
          
          if ((p_148142_1_ >= var4 + 115 - 18) && (p_148142_1_ <= var4 + 115))
          {
            var9 = func_148210_b(0);
          }
          else if ((p_148142_1_ >= var4 + 165 - 18) && (p_148142_1_ <= var4 + 165))
          {
            var9 = func_148210_b(1);
          }
          else
          {
            if ((p_148142_1_ < var4 + 215 - 18) || (p_148142_1_ > var4 + 215))
            {
              return;
            }
            
            var9 = func_148210_b(2);
          }
          
          var9 = I18n.format(var9, new Object[0]).trim();
          
          if (var9.length() > 0)
          {
            int var6 = p_148142_1_ + 12;
            int var7 = p_148142_2_ - 12;
            int var8 = fontRendererObj.getStringWidth(var9);
            drawGradientRect(var6 - 3, var7 - 3, var6 + var8 + 3, var7 + 8 + 3, -1073741824, -1073741824);
            fontRendererObj.drawStringWithShadow(var9, var6, var7, -1);
          }
        }
      }
    }
    
    protected void func_148213_a(StatCrafting p_148213_1_, int p_148213_2_, int p_148213_3_)
    {
      if (p_148213_1_ != null)
      {
        Item var4 = p_148213_1_.func_150959_a();
        ItemStack var5 = new ItemStack(var4);
        String var6 = var5.getUnlocalizedName();
        String var7 = I18n.format(new StringBuilder(String.valueOf(var6)).append(".name").toString(), new Object[0]).trim();
        
        if (var7.length() > 0)
        {
          int var8 = p_148213_2_ + 12;
          int var9 = p_148213_3_ - 12;
          int var10 = fontRendererObj.getStringWidth(var7);
          drawGradientRect(var8 - 3, var9 - 3, var8 + var10 + 3, var9 + 8 + 3, -1073741824, -1073741824);
          fontRendererObj.drawStringWithShadow(var7, var8, var9, -1);
        }
      }
    }
    
    protected void func_148212_h(int p_148212_1_)
    {
      if (p_148212_1_ != field_148217_o)
      {
        field_148217_o = p_148212_1_;
        field_148215_p = -1;
      }
      else if (field_148215_p == -1)
      {
        field_148215_p = 1;
      }
      else
      {
        field_148217_o = -1;
        field_148215_p = 0;
      }
      
      java.util.Collections.sort(statsHolder, statSorter);
    }
  }
  
  class StatsBlock extends GuiStats.Stats
  {
    private static final String __OBFID = "CL_00000724";
    
    public StatsBlock(Minecraft mcIn)
    {
      super(mcIn);
      statsHolder = Lists.newArrayList();
      Iterator var3 = StatList.objectMineStats.iterator();
      
      while (var3.hasNext())
      {
        StatCrafting var4 = (StatCrafting)var3.next();
        boolean var5 = false;
        int var6 = Item.getIdFromItem(var4.func_150959_a());
        
        if (field_146546_t.writeStat(var4) > 0)
        {
          var5 = true;
        }
        else if ((StatList.objectUseStats[var6] != null) && (field_146546_t.writeStat(StatList.objectUseStats[var6]) > 0))
        {
          var5 = true;
        }
        else if ((StatList.objectCraftStats[var6] != null) && (field_146546_t.writeStat(StatList.objectCraftStats[var6]) > 0))
        {
          var5 = true;
        }
        
        if (var5)
        {
          statsHolder.add(var4);
        }
      }
      
      statSorter = new Comparator()
      {
        private static final String __OBFID = "CL_00000725";
        
        public int compare(StatCrafting p_compare_1_, StatCrafting p_compare_2_) {
          int var3 = Item.getIdFromItem(p_compare_1_.func_150959_a());
          int var4 = Item.getIdFromItem(p_compare_2_.func_150959_a());
          StatBase var5 = null;
          StatBase var6 = null;
          
          if (field_148217_o == 2)
          {
            var5 = StatList.mineBlockStatArray[var3];
            var6 = StatList.mineBlockStatArray[var4];
          }
          else if (field_148217_o == 0)
          {
            var5 = StatList.objectCraftStats[var3];
            var6 = StatList.objectCraftStats[var4];
          }
          else if (field_148217_o == 1)
          {
            var5 = StatList.objectUseStats[var3];
            var6 = StatList.objectUseStats[var4];
          }
          
          if ((var5 != null) || (var6 != null))
          {
            if (var5 == null)
            {
              return 1;
            }
            
            if (var6 == null)
            {
              return -1;
            }
            
            int var7 = field_146546_t.writeStat(var5);
            int var8 = field_146546_t.writeStat(var6);
            
            if (var7 != var8)
            {
              return (var7 - var8) * field_148215_p;
            }
          }
          
          return var3 - var4;
        }
        
        public int compare(Object p_compare_1_, Object p_compare_2_) {
          return compare((StatCrafting)p_compare_1_, (StatCrafting)p_compare_2_);
        }
      };
    }
    
    protected void drawListHeader(int p_148129_1_, int p_148129_2_, Tessellator p_148129_3_)
    {
      super.drawListHeader(p_148129_1_, p_148129_2_, p_148129_3_);
      
      if (field_148218_l == 0)
      {
        GuiStats.this.drawSprite(p_148129_1_ + 115 - 18 + 1, p_148129_2_ + 1 + 1, 18, 18);
      }
      else
      {
        GuiStats.this.drawSprite(p_148129_1_ + 115 - 18, p_148129_2_ + 1, 18, 18);
      }
      
      if (field_148218_l == 1)
      {
        GuiStats.this.drawSprite(p_148129_1_ + 165 - 18 + 1, p_148129_2_ + 1 + 1, 36, 18);
      }
      else
      {
        GuiStats.this.drawSprite(p_148129_1_ + 165 - 18, p_148129_2_ + 1, 36, 18);
      }
      
      if (field_148218_l == 2)
      {
        GuiStats.this.drawSprite(p_148129_1_ + 215 - 18 + 1, p_148129_2_ + 1 + 1, 54, 18);
      }
      else
      {
        GuiStats.this.drawSprite(p_148129_1_ + 215 - 18, p_148129_2_ + 1, 54, 18);
      }
    }
    
    protected void drawSlot(int p_180791_1_, int p_180791_2_, int p_180791_3_, int p_180791_4_, int p_180791_5_, int p_180791_6_)
    {
      StatCrafting var7 = func_148211_c(p_180791_1_);
      Item var8 = var7.func_150959_a();
      GuiStats.this.drawStatsScreen(p_180791_2_ + 40, p_180791_3_, var8);
      int var9 = Item.getIdFromItem(var8);
      func_148209_a(StatList.objectCraftStats[var9], p_180791_2_ + 115, p_180791_3_, p_180791_1_ % 2 == 0);
      func_148209_a(StatList.objectUseStats[var9], p_180791_2_ + 165, p_180791_3_, p_180791_1_ % 2 == 0);
      func_148209_a(var7, p_180791_2_ + 215, p_180791_3_, p_180791_1_ % 2 == 0);
    }
    
    protected String func_148210_b(int p_148210_1_)
    {
      return p_148210_1_ == 1 ? "stat.used" : p_148210_1_ == 0 ? "stat.crafted" : "stat.mined";
    }
  }
  
  class StatsGeneral extends GuiSlot
  {
    private static final String __OBFID = "CL_00000726";
    
    public StatsGeneral(Minecraft mcIn)
    {
      super(width, height, 32, height - 64, 10);
      setShowSelectionBox(false);
    }
    
    protected int getSize()
    {
      return StatList.generalStats.size();
    }
    
    protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {}
    
    protected boolean isSelected(int slotIndex)
    {
      return false;
    }
    
    protected int getContentHeight()
    {
      return getSize() * 10;
    }
    
    protected void drawBackground()
    {
      drawDefaultBackground();
    }
    
    protected void drawSlot(int p_180791_1_, int p_180791_2_, int p_180791_3_, int p_180791_4_, int p_180791_5_, int p_180791_6_)
    {
      StatBase var7 = (StatBase)StatList.generalStats.get(p_180791_1_);
      drawString(fontRendererObj, var7.getStatName().getUnformattedText(), p_180791_2_ + 2, p_180791_3_ + 1, p_180791_1_ % 2 == 0 ? 16777215 : 9474192);
      String var8 = var7.func_75968_a(field_146546_t.writeStat(var7));
      drawString(fontRendererObj, var8, p_180791_2_ + 2 + 213 - fontRendererObj.getStringWidth(var8), p_180791_3_ + 1, p_180791_1_ % 2 == 0 ? 16777215 : 9474192);
    }
  }
  
  class StatsItem extends GuiStats.Stats
  {
    private static final String __OBFID = "CL_00000727";
    
    public StatsItem(Minecraft mcIn)
    {
      super(mcIn);
      statsHolder = Lists.newArrayList();
      Iterator var3 = StatList.itemStats.iterator();
      
      while (var3.hasNext())
      {
        StatCrafting var4 = (StatCrafting)var3.next();
        boolean var5 = false;
        int var6 = Item.getIdFromItem(var4.func_150959_a());
        
        if (field_146546_t.writeStat(var4) > 0)
        {
          var5 = true;
        }
        else if ((StatList.objectBreakStats[var6] != null) && (field_146546_t.writeStat(StatList.objectBreakStats[var6]) > 0))
        {
          var5 = true;
        }
        else if ((StatList.objectCraftStats[var6] != null) && (field_146546_t.writeStat(StatList.objectCraftStats[var6]) > 0))
        {
          var5 = true;
        }
        
        if (var5)
        {
          statsHolder.add(var4);
        }
      }
      
      statSorter = new Comparator()
      {
        private static final String __OBFID = "CL_00000728";
        
        public int compare(StatCrafting p_compare_1_, StatCrafting p_compare_2_) {
          int var3 = Item.getIdFromItem(p_compare_1_.func_150959_a());
          int var4 = Item.getIdFromItem(p_compare_2_.func_150959_a());
          StatBase var5 = null;
          StatBase var6 = null;
          
          if (field_148217_o == 0)
          {
            var5 = StatList.objectBreakStats[var3];
            var6 = StatList.objectBreakStats[var4];
          }
          else if (field_148217_o == 1)
          {
            var5 = StatList.objectCraftStats[var3];
            var6 = StatList.objectCraftStats[var4];
          }
          else if (field_148217_o == 2)
          {
            var5 = StatList.objectUseStats[var3];
            var6 = StatList.objectUseStats[var4];
          }
          
          if ((var5 != null) || (var6 != null))
          {
            if (var5 == null)
            {
              return 1;
            }
            
            if (var6 == null)
            {
              return -1;
            }
            
            int var7 = field_146546_t.writeStat(var5);
            int var8 = field_146546_t.writeStat(var6);
            
            if (var7 != var8)
            {
              return (var7 - var8) * field_148215_p;
            }
          }
          
          return var3 - var4;
        }
        
        public int compare(Object p_compare_1_, Object p_compare_2_) {
          return compare((StatCrafting)p_compare_1_, (StatCrafting)p_compare_2_);
        }
      };
    }
    
    protected void drawListHeader(int p_148129_1_, int p_148129_2_, Tessellator p_148129_3_)
    {
      super.drawListHeader(p_148129_1_, p_148129_2_, p_148129_3_);
      
      if (field_148218_l == 0)
      {
        GuiStats.this.drawSprite(p_148129_1_ + 115 - 18 + 1, p_148129_2_ + 1 + 1, 72, 18);
      }
      else
      {
        GuiStats.this.drawSprite(p_148129_1_ + 115 - 18, p_148129_2_ + 1, 72, 18);
      }
      
      if (field_148218_l == 1)
      {
        GuiStats.this.drawSprite(p_148129_1_ + 165 - 18 + 1, p_148129_2_ + 1 + 1, 18, 18);
      }
      else
      {
        GuiStats.this.drawSprite(p_148129_1_ + 165 - 18, p_148129_2_ + 1, 18, 18);
      }
      
      if (field_148218_l == 2)
      {
        GuiStats.this.drawSprite(p_148129_1_ + 215 - 18 + 1, p_148129_2_ + 1 + 1, 36, 18);
      }
      else
      {
        GuiStats.this.drawSprite(p_148129_1_ + 215 - 18, p_148129_2_ + 1, 36, 18);
      }
    }
    
    protected void drawSlot(int p_180791_1_, int p_180791_2_, int p_180791_3_, int p_180791_4_, int p_180791_5_, int p_180791_6_)
    {
      StatCrafting var7 = func_148211_c(p_180791_1_);
      Item var8 = var7.func_150959_a();
      GuiStats.this.drawStatsScreen(p_180791_2_ + 40, p_180791_3_, var8);
      int var9 = Item.getIdFromItem(var8);
      func_148209_a(StatList.objectBreakStats[var9], p_180791_2_ + 115, p_180791_3_, p_180791_1_ % 2 == 0);
      func_148209_a(StatList.objectCraftStats[var9], p_180791_2_ + 165, p_180791_3_, p_180791_1_ % 2 == 0);
      func_148209_a(var7, p_180791_2_ + 215, p_180791_3_, p_180791_1_ % 2 == 0);
    }
    
    protected String func_148210_b(int p_148210_1_)
    {
      return p_148210_1_ == 2 ? "stat.used" : p_148210_1_ == 1 ? "stat.crafted" : "stat.depleted";
    }
  }
  
  class StatsMobsList extends GuiSlot
  {
    private final List field_148222_l = Lists.newArrayList();
    private static final String __OBFID = "CL_00000729";
    
    public StatsMobsList(Minecraft mcIn)
    {
      super(width, height, 32, height - 64, fontRendererObj.FONT_HEIGHT * 4);
      setShowSelectionBox(false);
      Iterator var3 = EntityList.entityEggs.values().iterator();
      
      while (var3.hasNext())
      {
        EntityList.EntityEggInfo var4 = (EntityList.EntityEggInfo)var3.next();
        
        if ((field_146546_t.writeStat(field_151512_d) > 0) || (field_146546_t.writeStat(field_151513_e) > 0))
        {
          field_148222_l.add(var4);
        }
      }
    }
    
    protected int getSize()
    {
      return field_148222_l.size();
    }
    
    protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {}
    
    protected boolean isSelected(int slotIndex)
    {
      return false;
    }
    
    protected int getContentHeight()
    {
      return getSize() * fontRendererObj.FONT_HEIGHT * 4;
    }
    
    protected void drawBackground()
    {
      drawDefaultBackground();
    }
    
    protected void drawSlot(int p_180791_1_, int p_180791_2_, int p_180791_3_, int p_180791_4_, int p_180791_5_, int p_180791_6_)
    {
      EntityList.EntityEggInfo var7 = (EntityList.EntityEggInfo)field_148222_l.get(p_180791_1_);
      String var8 = I18n.format("entity." + EntityList.getStringFromID(spawnedID) + ".name", new Object[0]);
      int var9 = field_146546_t.writeStat(field_151512_d);
      int var10 = field_146546_t.writeStat(field_151513_e);
      String var11 = I18n.format("stat.entityKills", new Object[] { Integer.valueOf(var9), var8 });
      String var12 = I18n.format("stat.entityKilledBy", new Object[] { var8, Integer.valueOf(var10) });
      
      if (var9 == 0)
      {
        var11 = I18n.format("stat.entityKills.none", new Object[] { var8 });
      }
      
      if (var10 == 0)
      {
        var12 = I18n.format("stat.entityKilledBy.none", new Object[] { var8 });
      }
      
      drawString(fontRendererObj, var8, p_180791_2_ + 2 - 10, p_180791_3_ + 1, 16777215);
      drawString(fontRendererObj, var11, p_180791_2_ + 2, p_180791_3_ + 1 + fontRendererObj.FONT_HEIGHT, var9 == 0 ? 6316128 : 9474192);
      drawString(fontRendererObj, var12, p_180791_2_ + 2, p_180791_3_ + 1 + fontRendererObj.FONT_HEIGHT * 2, var10 == 0 ? 6316128 : 9474192);
    }
  }
}

package net.minecraft.client.gui;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldSettings.GameType;
import net.minecraft.world.WorldType;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldInfo;

public class GuiCreateWorld extends GuiScreen
{
  private GuiScreen field_146332_f;
  private GuiTextField field_146333_g;
  private GuiTextField field_146335_h;
  private String field_146336_i;
  private String field_146342_r = "survival";
  private String field_175300_s;
  private boolean field_146341_s = true;
  private boolean field_146340_t;
  private boolean field_146339_u;
  private boolean field_146338_v;
  private boolean field_146337_w;
  private boolean field_146345_x;
  private boolean field_146344_y;
  private GuiButton field_146343_z;
  private GuiButton field_146324_A;
  private GuiButton field_146325_B;
  private GuiButton field_146326_C;
  private GuiButton field_146320_D;
  private GuiButton field_146321_E;
  private GuiButton field_146322_F;
  private String field_146323_G;
  private String field_146328_H;
  private String field_146329_I;
  private String field_146330_J;
  private int field_146331_K;
  public String field_146334_a = "";
  private static final String[] field_146327_L = { "CON", "COM", "PRN", "AUX", "CLOCK$", "NUL", "COM1", "COM2", "COM3", "COM4", "COM5", "COM6", "COM7", "COM8", "COM9", "LPT1", "LPT2", "LPT3", "LPT4", "LPT5", "LPT6", "LPT7", "LPT8", "LPT9" };
  private static final String __OBFID = "CL_00000689";
  
  public GuiCreateWorld(GuiScreen p_i46320_1_)
  {
    field_146332_f = p_i46320_1_;
    field_146329_I = "";
    field_146330_J = I18n.format("selectWorld.newWorld", new Object[0]);
  }
  



  public void updateScreen()
  {
    field_146333_g.updateCursorCounter();
    field_146335_h.updateCursorCounter();
  }
  



  public void initGui()
  {
    org.lwjgl.input.Keyboard.enableRepeatEvents(true);
    buttonList.clear();
    buttonList.add(new GuiButton(0, width / 2 - 155, height - 28, 150, 20, I18n.format("selectWorld.create", new Object[0])));
    buttonList.add(new GuiButton(1, width / 2 + 5, height - 28, 150, 20, I18n.format("gui.cancel", new Object[0])));
    buttonList.add(this.field_146343_z = new GuiButton(2, width / 2 - 75, 115, 150, 20, I18n.format("selectWorld.gameMode", new Object[0])));
    buttonList.add(this.field_146324_A = new GuiButton(3, width / 2 - 75, 187, 150, 20, I18n.format("selectWorld.moreWorldOptions", new Object[0])));
    buttonList.add(this.field_146325_B = new GuiButton(4, width / 2 - 155, 100, 150, 20, I18n.format("selectWorld.mapFeatures", new Object[0])));
    field_146325_B.visible = false;
    buttonList.add(this.field_146326_C = new GuiButton(7, width / 2 + 5, 151, 150, 20, I18n.format("selectWorld.bonusItems", new Object[0])));
    field_146326_C.visible = false;
    buttonList.add(this.field_146320_D = new GuiButton(5, width / 2 + 5, 100, 150, 20, I18n.format("selectWorld.mapType", new Object[0])));
    field_146320_D.visible = false;
    buttonList.add(this.field_146321_E = new GuiButton(6, width / 2 - 155, 151, 150, 20, I18n.format("selectWorld.allowCommands", new Object[0])));
    field_146321_E.visible = false;
    buttonList.add(this.field_146322_F = new GuiButton(8, width / 2 + 5, 120, 150, 20, I18n.format("selectWorld.customizeType", new Object[0])));
    field_146322_F.visible = false;
    field_146333_g = new GuiTextField(9, fontRendererObj, width / 2 - 100, 60, 200, 20);
    field_146333_g.setFocused(true);
    field_146333_g.setText(field_146330_J);
    field_146335_h = new GuiTextField(10, fontRendererObj, width / 2 - 100, 60, 200, 20);
    field_146335_h.setText(field_146329_I);
    func_146316_a(field_146344_y);
    func_146314_g();
    func_146319_h();
  }
  
  private void func_146314_g()
  {
    field_146336_i = field_146333_g.getText().trim();
    char[] var1 = net.minecraft.util.ChatAllowedCharacters.allowedCharactersArray;
    int var2 = var1.length;
    
    for (int var3 = 0; var3 < var2; var3++)
    {
      char var4 = var1[var3];
      field_146336_i = field_146336_i.replace(var4, '_');
    }
    
    if (org.apache.commons.lang3.StringUtils.isEmpty(field_146336_i))
    {
      field_146336_i = "World";
    }
    
    field_146336_i = func_146317_a(mc.getSaveLoader(), field_146336_i);
  }
  
  private void func_146319_h()
  {
    field_146343_z.displayString = (I18n.format("selectWorld.gameMode", new Object[0]) + ": " + I18n.format(new StringBuilder("selectWorld.gameMode.").append(field_146342_r).toString(), new Object[0]));
    field_146323_G = I18n.format("selectWorld.gameMode." + field_146342_r + ".line1", new Object[0]);
    field_146328_H = I18n.format("selectWorld.gameMode." + field_146342_r + ".line2", new Object[0]);
    field_146325_B.displayString = (I18n.format("selectWorld.mapFeatures", new Object[0]) + " ");
    
    if (field_146341_s)
    {
      field_146325_B.displayString += I18n.format("options.on", new Object[0]);
    }
    else
    {
      field_146325_B.displayString += I18n.format("options.off", new Object[0]);
    }
    
    field_146326_C.displayString = (I18n.format("selectWorld.bonusItems", new Object[0]) + " ");
    
    if ((field_146338_v) && (!field_146337_w))
    {
      field_146326_C.displayString += I18n.format("options.on", new Object[0]);
    }
    else
    {
      field_146326_C.displayString += I18n.format("options.off", new Object[0]);
    }
    
    field_146320_D.displayString = (I18n.format("selectWorld.mapType", new Object[0]) + " " + I18n.format(WorldType.worldTypes[field_146331_K].getTranslateName(), new Object[0]));
    field_146321_E.displayString = (I18n.format("selectWorld.allowCommands", new Object[0]) + " ");
    
    if ((field_146340_t) && (!field_146337_w))
    {
      field_146321_E.displayString += I18n.format("options.on", new Object[0]);
    }
    else
    {
      field_146321_E.displayString += I18n.format("options.off", new Object[0]);
    }
  }
  
  public static String func_146317_a(ISaveFormat p_146317_0_, String p_146317_1_)
  {
    p_146317_1_ = p_146317_1_.replaceAll("[\\./\"]", "_");
    String[] var2 = field_146327_L;
    int var3 = var2.length;
    
    for (int var4 = 0; var4 < var3; var4++)
    {
      String var5 = var2[var4];
      
      if (p_146317_1_.equalsIgnoreCase(var5))
      {
        p_146317_1_ = "_" + p_146317_1_ + "_";
      }
    }
    
    while (p_146317_0_.getWorldInfo(p_146317_1_) != null)
    {
      p_146317_1_ = p_146317_1_ + "-";
    }
    
    return p_146317_1_;
  }
  



  public void onGuiClosed()
  {
    org.lwjgl.input.Keyboard.enableRepeatEvents(false);
  }
  
  protected void actionPerformed(GuiButton button) throws IOException
  {
    if (enabled)
    {
      if (id == 1)
      {
        mc.displayGuiScreen(field_146332_f);
      }
      else if (id == 0)
      {
        mc.displayGuiScreen(null);
        
        if (field_146345_x)
        {
          return;
        }
        
        field_146345_x = true;
        long var2 = new Random().nextLong();
        String var4 = field_146335_h.getText();
        
        if (!org.apache.commons.lang3.StringUtils.isEmpty(var4))
        {
          try
          {
            long var5 = Long.parseLong(var4);
            
            if (var5 != 0L)
            {
              var2 = var5;
            }
          }
          catch (NumberFormatException var7)
          {
            var2 = var4.hashCode();
          }
        }
        
        WorldSettings.GameType var8 = WorldSettings.GameType.getByName(field_146342_r);
        WorldSettings var6 = new WorldSettings(var2, var8, field_146341_s, field_146337_w, WorldType.worldTypes[field_146331_K]);
        var6.setWorldName(field_146334_a);
        
        if ((field_146338_v) && (!field_146337_w))
        {
          var6.enableBonusChest();
        }
        
        if ((field_146340_t) && (!field_146337_w))
        {
          var6.enableCommands();
        }
        
        mc.launchIntegratedServer(field_146336_i, field_146333_g.getText().trim(), var6);
      }
      else if (id == 3)
      {
        func_146315_i();
      }
      else if (id == 2)
      {
        if (field_146342_r.equals("survival"))
        {
          if (!field_146339_u)
          {
            field_146340_t = false;
          }
          
          field_146337_w = false;
          field_146342_r = "hardcore";
          field_146337_w = true;
          field_146321_E.enabled = false;
          field_146326_C.enabled = false;
          func_146319_h();
        }
        else if (field_146342_r.equals("hardcore"))
        {
          if (!field_146339_u)
          {
            field_146340_t = true;
          }
          
          field_146337_w = false;
          field_146342_r = "creative";
          func_146319_h();
          field_146337_w = false;
          field_146321_E.enabled = true;
          field_146326_C.enabled = true;
        }
        else
        {
          if (!field_146339_u)
          {
            field_146340_t = false;
          }
          
          field_146342_r = "survival";
          func_146319_h();
          field_146321_E.enabled = true;
          field_146326_C.enabled = true;
          field_146337_w = false;
        }
        
        func_146319_h();
      }
      else if (id == 4)
      {
        field_146341_s = (!field_146341_s);
        func_146319_h();
      }
      else if (id == 7)
      {
        field_146338_v = (!field_146338_v);
        func_146319_h();
      }
      else if (id == 5)
      {
        field_146331_K += 1;
        
        if (field_146331_K >= WorldType.worldTypes.length)
        {
          field_146331_K = 0;
        }
        
        while (!func_175299_g())
        {
          field_146331_K += 1;
          
          if (field_146331_K >= WorldType.worldTypes.length)
          {
            field_146331_K = 0;
          }
        }
        
        field_146334_a = "";
        func_146319_h();
        func_146316_a(field_146344_y);
      }
      else if (id == 6)
      {
        field_146339_u = true;
        field_146340_t = (!field_146340_t);
        func_146319_h();
      }
      else if (id == 8)
      {
        if (WorldType.worldTypes[field_146331_K] == WorldType.FLAT)
        {
          mc.displayGuiScreen(new GuiCreateFlatWorld(this, field_146334_a));
        }
        else
        {
          mc.displayGuiScreen(new GuiCustomizeWorldScreen(this, field_146334_a));
        }
      }
    }
  }
  
  private boolean func_175299_g()
  {
    WorldType var1 = WorldType.worldTypes[field_146331_K];
    return (var1 != null) && (var1.getCanBeCreated()) ? true : var1 == WorldType.DEBUG_WORLD ? isShiftKeyDown() : false;
  }
  
  private void func_146315_i()
  {
    func_146316_a(!field_146344_y);
  }
  
  private void func_146316_a(boolean p_146316_1_)
  {
    field_146344_y = p_146316_1_;
    
    if (WorldType.worldTypes[field_146331_K] == WorldType.DEBUG_WORLD)
    {
      field_146343_z.visible = (!field_146344_y);
      field_146343_z.enabled = false;
      
      if (field_175300_s == null)
      {
        field_175300_s = field_146342_r;
      }
      
      field_146342_r = "spectator";
      field_146325_B.visible = false;
      field_146326_C.visible = false;
      field_146320_D.visible = field_146344_y;
      field_146321_E.visible = false;
      field_146322_F.visible = false;
    }
    else
    {
      field_146343_z.visible = (!field_146344_y);
      field_146343_z.enabled = true;
      
      if (field_175300_s != null)
      {
        field_146342_r = field_175300_s;
        field_175300_s = null;
      }
      
      field_146325_B.visible = ((field_146344_y) && (WorldType.worldTypes[field_146331_K] != WorldType.CUSTOMIZED));
      field_146326_C.visible = field_146344_y;
      field_146320_D.visible = field_146344_y;
      field_146321_E.visible = field_146344_y;
      field_146322_F.visible = ((field_146344_y) && ((WorldType.worldTypes[field_146331_K] == WorldType.FLAT) || (WorldType.worldTypes[field_146331_K] == WorldType.CUSTOMIZED)));
    }
    
    func_146319_h();
    
    if (field_146344_y)
    {
      field_146324_A.displayString = I18n.format("gui.done", new Object[0]);
    }
    else
    {
      field_146324_A.displayString = I18n.format("selectWorld.moreWorldOptions", new Object[0]);
    }
  }
  



  protected void keyTyped(char typedChar, int keyCode)
    throws IOException
  {
    if ((field_146333_g.isFocused()) && (!field_146344_y))
    {
      field_146333_g.textboxKeyTyped(typedChar, keyCode);
      field_146330_J = field_146333_g.getText();
    }
    else if ((field_146335_h.isFocused()) && (field_146344_y))
    {
      field_146335_h.textboxKeyTyped(typedChar, keyCode);
      field_146329_I = field_146335_h.getText();
    }
    
    if ((keyCode == 28) || (keyCode == 156))
    {
      actionPerformed((GuiButton)buttonList.get(0));
    }
    
    buttonList.get(0)).enabled = (field_146333_g.getText().length() > 0);
    func_146314_g();
  }
  


  protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    throws IOException
  {
    super.mouseClicked(mouseX, mouseY, mouseButton);
    
    if (field_146344_y)
    {
      field_146335_h.mouseClicked(mouseX, mouseY, mouseButton);
    }
    else
    {
      field_146333_g.mouseClicked(mouseX, mouseY, mouseButton);
    }
  }
  



  public void drawScreen(int mouseX, int mouseY, float partialTicks)
  {
    drawDefaultBackground();
    drawCenteredString(fontRendererObj, I18n.format("selectWorld.create", new Object[0]), width / 2, 20, -1);
    
    if (field_146344_y)
    {
      drawString(fontRendererObj, I18n.format("selectWorld.enterSeed", new Object[0]), width / 2 - 100, 47, -6250336);
      drawString(fontRendererObj, I18n.format("selectWorld.seedInfo", new Object[0]), width / 2 - 100, 85, -6250336);
      
      if (field_146325_B.visible)
      {
        drawString(fontRendererObj, I18n.format("selectWorld.mapFeatures.info", new Object[0]), width / 2 - 150, 122, -6250336);
      }
      
      if (field_146321_E.visible)
      {
        drawString(fontRendererObj, I18n.format("selectWorld.allowCommands.info", new Object[0]), width / 2 - 150, 172, -6250336);
      }
      
      field_146335_h.drawTextBox();
      
      if (WorldType.worldTypes[field_146331_K].showWorldInfoNotice())
      {
        fontRendererObj.drawSplitString(I18n.format(WorldType.worldTypes[field_146331_K].func_151359_c(), new Object[0]), field_146320_D.xPosition + 2, field_146320_D.yPosition + 22, field_146320_D.getButtonWidth(), 10526880);
      }
    }
    else
    {
      drawString(fontRendererObj, I18n.format("selectWorld.enterName", new Object[0]), width / 2 - 100, 47, -6250336);
      drawString(fontRendererObj, I18n.format("selectWorld.resultFolder", new Object[0]) + " " + field_146336_i, width / 2 - 100, 85, -6250336);
      field_146333_g.drawTextBox();
      drawString(fontRendererObj, field_146323_G, width / 2 - 100, 137, -6250336);
      drawString(fontRendererObj, field_146328_H, width / 2 - 100, 149, -6250336);
    }
    
    super.drawScreen(mouseX, mouseY, partialTicks);
  }
  
  public void func_146318_a(WorldInfo p_146318_1_)
  {
    field_146330_J = I18n.format("selectWorld.newWorld.copyOf", new Object[] { p_146318_1_.getWorldName() });
    field_146329_I = p_146318_1_.getSeed();
    field_146331_K = p_146318_1_.getTerrainType().getWorldTypeID();
    field_146334_a = p_146318_1_.getGeneratorOptions();
    field_146341_s = p_146318_1_.isMapFeaturesEnabled();
    field_146340_t = p_146318_1_.areCommandsAllowed();
    
    if (p_146318_1_.isHardcoreModeEnabled())
    {
      field_146342_r = "hardcore";
    }
    else if (p_146318_1_.getGameType().isSurvivalOrAdventure())
    {
      field_146342_r = "survival";
    }
    else if (p_146318_1_.getGameType().isCreative())
    {
      field_146342_r = "creative";
    }
  }
}

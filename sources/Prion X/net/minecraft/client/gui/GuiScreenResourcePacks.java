package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.ResourcePackListEntry;
import net.minecraft.client.resources.ResourcePackListEntryFound;
import net.minecraft.client.resources.ResourcePackRepository;
import net.minecraft.client.resources.ResourcePackRepository.Entry;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.Util;
import net.minecraft.util.Util.EnumOS;
import org.apache.logging.log4j.Logger;

public class GuiScreenResourcePacks extends GuiScreen
{
  private static final Logger logger = ;
  private GuiScreen field_146965_f;
  private List field_146966_g;
  private List field_146969_h;
  private GuiResourcePackAvailable field_146970_i;
  private GuiResourcePackSelected field_146967_r;
  private boolean field_175289_s = false;
  private static final String __OBFID = "CL_00000820";
  
  public GuiScreenResourcePacks(GuiScreen p_i45050_1_)
  {
    field_146965_f = p_i45050_1_;
  }
  



  public void initGui()
  {
    buttonList.add(new GuiOptionButton(2, width / 2 - 154, height - 48, I18n.format("resourcePack.openFolder", new Object[0])));
    buttonList.add(new GuiOptionButton(1, width / 2 + 4, height - 48, I18n.format("gui.done", new Object[0])));
    field_146966_g = Lists.newArrayList();
    field_146969_h = Lists.newArrayList();
    ResourcePackRepository var1 = mc.getResourcePackRepository();
    var1.updateRepositoryEntriesAll();
    ArrayList var2 = Lists.newArrayList(var1.getRepositoryEntriesAll());
    var2.removeAll(var1.getRepositoryEntries());
    Iterator var3 = var2.iterator();
    

    while (var3.hasNext())
    {
      ResourcePackRepository.Entry var4 = (ResourcePackRepository.Entry)var3.next();
      field_146966_g.add(new ResourcePackListEntryFound(this, var4));
    }
    
    var3 = Lists.reverse(var1.getRepositoryEntries()).iterator();
    
    while (var3.hasNext())
    {
      ResourcePackRepository.Entry var4 = (ResourcePackRepository.Entry)var3.next();
      field_146969_h.add(new ResourcePackListEntryFound(this, var4));
    }
    
    field_146969_h.add(new net.minecraft.client.resources.ResourcePackListEntryDefault(this));
    field_146970_i = new GuiResourcePackAvailable(mc, 200, height, field_146966_g);
    field_146970_i.setSlotXBoundsFromLeft(width / 2 - 4 - 200);
    field_146970_i.registerScrollButtons(7, 8);
    field_146967_r = new GuiResourcePackSelected(mc, 200, height, field_146969_h);
    field_146967_r.setSlotXBoundsFromLeft(width / 2 + 4);
    field_146967_r.registerScrollButtons(7, 8);
  }
  


  public void handleMouseInput()
    throws IOException
  {
    super.handleMouseInput();
    field_146967_r.func_178039_p();
    field_146970_i.func_178039_p();
  }
  
  public boolean hasResourcePackEntry(ResourcePackListEntry p_146961_1_)
  {
    return field_146969_h.contains(p_146961_1_);
  }
  
  public List func_146962_b(ResourcePackListEntry p_146962_1_)
  {
    return hasResourcePackEntry(p_146962_1_) ? field_146969_h : field_146966_g;
  }
  
  public List func_146964_g()
  {
    return field_146966_g;
  }
  
  public List func_146963_h()
  {
    return field_146969_h;
  }
  
  protected void actionPerformed(GuiButton button) throws IOException
  {
    if (enabled)
    {
      if (id == 2)
      {
        File var2 = mc.getResourcePackRepository().getDirResourcepacks();
        String var3 = var2.getAbsolutePath();
        
        if (Util.getOSType() == Util.EnumOS.OSX)
        {
          try
          {
            logger.info(var3);
            Runtime.getRuntime().exec(new String[] { "/usr/bin/open", var3 });
            return;
          }
          catch (IOException var9)
          {
            logger.error("Couldn't open file", var9);
          }
        }
        else if (Util.getOSType() == Util.EnumOS.WINDOWS)
        {
          String var4 = String.format("cmd.exe /C start \"Open file\" \"%s\"", new Object[] { var3 });
          
          try
          {
            Runtime.getRuntime().exec(var4);
            return;
          }
          catch (IOException var8)
          {
            logger.error("Couldn't open file", var8);
          }
        }
        
        boolean var12 = false;
        
        try
        {
          Class var5 = Class.forName("java.awt.Desktop");
          Object var6 = var5.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
          var5.getMethod("browse", new Class[] { java.net.URI.class }).invoke(var6, new Object[] { var2.toURI() });
        }
        catch (Throwable var7)
        {
          logger.error("Couldn't open link", var7);
          var12 = true;
        }
        
        if (var12)
        {
          logger.info("Opening via system class!");
          org.lwjgl.Sys.openURL("file://" + var3);
        }
      }
      else if (id == 1)
      {
        if (field_175289_s)
        {
          ArrayList var10 = Lists.newArrayList();
          Iterator var11 = field_146969_h.iterator();
          
          while (var11.hasNext())
          {
            ResourcePackListEntry var13 = (ResourcePackListEntry)var11.next();
            
            if ((var13 instanceof ResourcePackListEntryFound))
            {
              var10.add(((ResourcePackListEntryFound)var13).func_148318_i());
            }
          }
          
          java.util.Collections.reverse(var10);
          mc.getResourcePackRepository().func_148527_a(var10);
          mc.gameSettings.resourcePacks.clear();
          var11 = var10.iterator();
          
          while (var11.hasNext())
          {
            ResourcePackRepository.Entry var14 = (ResourcePackRepository.Entry)var11.next();
            mc.gameSettings.resourcePacks.add(var14.getResourcePackName());
          }
          
          mc.gameSettings.saveOptions();
          mc.refreshResources();
        }
        
        mc.displayGuiScreen(field_146965_f);
      }
    }
  }
  


  protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    throws IOException
  {
    super.mouseClicked(mouseX, mouseY, mouseButton);
    field_146970_i.func_148179_a(mouseX, mouseY, mouseButton);
    field_146967_r.func_148179_a(mouseX, mouseY, mouseButton);
  }
  



  protected void mouseReleased(int mouseX, int mouseY, int state)
  {
    super.mouseReleased(mouseX, mouseY, state);
  }
  



  public void drawScreen(int mouseX, int mouseY, float partialTicks)
  {
    drawBackground(0);
    field_146970_i.drawScreen(mouseX, mouseY, partialTicks);
    field_146967_r.drawScreen(mouseX, mouseY, partialTicks);
    drawCenteredString(fontRendererObj, I18n.format("resourcePack.title", new Object[0]), width / 2, 16, 16777215);
    drawCenteredString(fontRendererObj, I18n.format("resourcePack.folderInfo", new Object[0]), width / 2 - 77, height - 26, 8421504);
    super.drawScreen(mouseX, mouseY, partialTicks);
  }
  
  public void func_175288_g()
  {
    field_175289_s = true;
  }
}

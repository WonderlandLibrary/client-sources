package net.minecraft.client.gui;

import com.google.common.collect.Maps;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.Language;
import net.minecraft.client.resources.LanguageManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.GameSettings.Options;

public class GuiLanguage extends GuiScreen
{
  protected GuiScreen field_146453_a;
  private List field_146450_f;
  private final GameSettings game_settings_3;
  private final LanguageManager field_146454_h;
  private GuiOptionButton field_146455_i;
  private GuiOptionButton field_146452_r;
  private static final String __OBFID = "CL_00000698";
  
  public GuiLanguage(GuiScreen p_i1043_1_, GameSettings p_i1043_2_, LanguageManager p_i1043_3_)
  {
    field_146453_a = p_i1043_1_;
    game_settings_3 = p_i1043_2_;
    field_146454_h = p_i1043_3_;
  }
  



  public void initGui()
  {
    buttonList.add(this.field_146455_i = new GuiOptionButton(100, width / 2 - 155, height - 38, GameSettings.Options.FORCE_UNICODE_FONT, game_settings_3.getKeyBinding(GameSettings.Options.FORCE_UNICODE_FONT)));
    buttonList.add(this.field_146452_r = new GuiOptionButton(6, width / 2 - 155 + 160, height - 38, I18n.format("gui.done", new Object[0])));
    field_146450_f = new List(mc);
    field_146450_f.registerScrollButtons(7, 8);
  }
  


  public void handleMouseInput()
    throws IOException
  {
    super.handleMouseInput();
    field_146450_f.func_178039_p();
  }
  
  protected void actionPerformed(GuiButton button) throws IOException
  {
    if (enabled)
    {
      switch (id)
      {
      case 5: 
        break;
      
      case 6: 
        mc.displayGuiScreen(field_146453_a);
        break;
      
      case 100: 
        if ((button instanceof GuiOptionButton))
        {
          game_settings_3.setOptionValue(((GuiOptionButton)button).returnEnumOptions(), 1);
          displayString = game_settings_3.getKeyBinding(GameSettings.Options.FORCE_UNICODE_FONT);
          ScaledResolution var2 = new ScaledResolution(mc);
          int var3 = var2.getScaledWidth();
          int var4 = var2.getScaledHeight();
          setWorldAndResolution(mc, var3, var4);
        }
        
        break;
      
      default: 
        field_146450_f.actionPerformed(button);
      }
      
    }
  }
  


  public void drawScreen(int mouseX, int mouseY, float partialTicks)
  {
    field_146450_f.drawScreen(mouseX, mouseY, partialTicks);
    drawCenteredString(fontRendererObj, I18n.format("options.language", new Object[0]), width / 2, 16, 16777215);
    drawCenteredString(fontRendererObj, "(" + I18n.format("options.languageWarning", new Object[0]) + ")", width / 2, height - 56, 8421504);
    super.drawScreen(mouseX, mouseY, partialTicks);
  }
  
  class List extends GuiSlot
  {
    private final List field_148176_l = com.google.common.collect.Lists.newArrayList();
    private final Map field_148177_m = Maps.newHashMap();
    private static final String __OBFID = "CL_00000699";
    
    public List(Minecraft mcIn)
    {
      super(width, height, 32, height - 65 + 4, 18);
      Iterator var3 = field_146454_h.getLanguages().iterator();
      
      while (var3.hasNext())
      {
        Language var4 = (Language)var3.next();
        field_148177_m.put(var4.getLanguageCode(), var4);
        field_148176_l.add(var4.getLanguageCode());
      }
    }
    
    protected int getSize()
    {
      return field_148176_l.size();
    }
    
    protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY)
    {
      Language var5 = (Language)field_148177_m.get(field_148176_l.get(slotIndex));
      field_146454_h.setCurrentLanguage(var5);
      game_settings_3.language = var5.getLanguageCode();
      mc.refreshResources();
      fontRendererObj.setUnicodeFlag((field_146454_h.isCurrentLocaleUnicode()) || (game_settings_3.forceUnicodeFont));
      fontRendererObj.setBidiFlag(field_146454_h.isCurrentLanguageBidirectional());
      field_146452_r.displayString = I18n.format("gui.done", new Object[0]);
      field_146455_i.displayString = game_settings_3.getKeyBinding(GameSettings.Options.FORCE_UNICODE_FONT);
      game_settings_3.saveOptions();
    }
    
    protected boolean isSelected(int slotIndex)
    {
      return ((String)field_148176_l.get(slotIndex)).equals(field_146454_h.getCurrentLanguage().getLanguageCode());
    }
    
    protected int getContentHeight()
    {
      return getSize() * 18;
    }
    
    protected void drawBackground()
    {
      drawDefaultBackground();
    }
    
    protected void drawSlot(int p_180791_1_, int p_180791_2_, int p_180791_3_, int p_180791_4_, int p_180791_5_, int p_180791_6_)
    {
      fontRendererObj.setBidiFlag(true);
      drawCenteredString(fontRendererObj, ((Language)field_148177_m.get(field_148176_l.get(p_180791_1_))).toString(), width / 2, p_180791_3_ + 1, 16777215);
      fontRendererObj.setBidiFlag(field_146454_h.getCurrentLanguage().isBidirectional());
    }
  }
}

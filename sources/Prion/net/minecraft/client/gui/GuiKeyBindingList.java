package net.minecraft.client.gui;

import java.util.Arrays;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.EnumChatFormatting;

public class GuiKeyBindingList extends GuiListExtended
{
  private final GuiControls field_148191_k;
  private final Minecraft mc;
  private final GuiListExtended.IGuiListEntry[] listEntries;
  private int maxListLabelWidth = 0;
  private static final String __OBFID = "CL_00000732";
  
  public GuiKeyBindingList(GuiControls p_i45031_1_, Minecraft mcIn)
  {
    super(mcIn, width, height, 63, height - 32, 20);
    field_148191_k = p_i45031_1_;
    mc = mcIn;
    KeyBinding[] var3 = (KeyBinding[])org.apache.commons.lang3.ArrayUtils.clone(gameSettings.keyBindings);
    listEntries = new GuiListExtended.IGuiListEntry[var3.length + KeyBinding.getKeybinds().size()];
    Arrays.sort(var3);
    int var4 = 0;
    String var5 = null;
    KeyBinding[] var6 = var3;
    int var7 = var3.length;
    
    for (int var8 = 0; var8 < var7; var8++)
    {
      KeyBinding var9 = var6[var8];
      String var10 = var9.getKeyCategory();
      
      if (!var10.equals(var5))
      {
        var5 = var10;
        listEntries[(var4++)] = new CategoryEntry(var10);
      }
      
      int var11 = fontRendererObj.getStringWidth(I18n.format(var9.getKeyDescription(), new Object[0]));
      
      if (var11 > maxListLabelWidth)
      {
        maxListLabelWidth = var11;
      }
      
      listEntries[(var4++)] = new KeyEntry(var9, null);
    }
  }
  
  protected int getSize()
  {
    return listEntries.length;
  }
  



  public GuiListExtended.IGuiListEntry getListEntry(int p_148180_1_)
  {
    return listEntries[p_148180_1_];
  }
  
  protected int getScrollBarX()
  {
    return super.getScrollBarX() + 15;
  }
  



  public int getListWidth()
  {
    return super.getListWidth() + 32;
  }
  
  public class CategoryEntry implements GuiListExtended.IGuiListEntry
  {
    private final String labelText;
    private final int labelWidth;
    private static final String __OBFID = "CL_00000734";
    
    public CategoryEntry(String p_i45028_2_)
    {
      labelText = I18n.format(p_i45028_2_, new Object[0]);
      labelWidth = mc.fontRendererObj.getStringWidth(labelText);
    }
    
    public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected)
    {
      mc.fontRendererObj.drawString(labelText, mc.currentScreen.width / 2 - labelWidth / 2, y + slotHeight - mc.fontRendererObj.FONT_HEIGHT - 1, 16777215);
    }
    
    public boolean mousePressed(int p_148278_1_, int p_148278_2_, int p_148278_3_, int p_148278_4_, int p_148278_5_, int p_148278_6_)
    {
      return false;
    }
    
    public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {}
    
    public void setSelected(int p_178011_1_, int p_178011_2_, int p_178011_3_) {}
  }
  
  public class KeyEntry implements GuiListExtended.IGuiListEntry
  {
    private final KeyBinding field_148282_b;
    private final String field_148283_c;
    private final GuiButton btnChangeKeyBinding;
    private final GuiButton btnReset;
    private static final String __OBFID = "CL_00000735";
    
    private KeyEntry(KeyBinding p_i45029_2_)
    {
      field_148282_b = p_i45029_2_;
      field_148283_c = I18n.format(p_i45029_2_.getKeyDescription(), new Object[0]);
      btnChangeKeyBinding = new GuiButton(0, 0, 0, 75, 18, I18n.format(p_i45029_2_.getKeyDescription(), new Object[0]));
      btnReset = new GuiButton(0, 0, 0, 50, 18, I18n.format("controls.reset", new Object[0]));
    }
    
    public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected)
    {
      boolean var9 = field_148191_k.buttonId == field_148282_b;
      mc.fontRendererObj.drawString(field_148283_c, x + 90 - maxListLabelWidth, y + slotHeight / 2 - mc.fontRendererObj.FONT_HEIGHT / 2, 16777215);
      btnReset.xPosition = (x + 190);
      btnReset.yPosition = y;
      btnReset.enabled = (field_148282_b.getKeyCode() != field_148282_b.getKeyCodeDefault());
      btnReset.drawButton(mc, mouseX, mouseY);
      btnChangeKeyBinding.xPosition = (x + 105);
      btnChangeKeyBinding.yPosition = y;
      btnChangeKeyBinding.displayString = GameSettings.getKeyDisplayString(field_148282_b.getKeyCode());
      boolean var10 = false;
      
      if (field_148282_b.getKeyCode() != 0)
      {
        KeyBinding[] var11 = mc.gameSettings.keyBindings;
        int var12 = var11.length;
        
        for (int var13 = 0; var13 < var12; var13++)
        {
          KeyBinding var14 = var11[var13];
          
          if ((var14 != field_148282_b) && (var14.getKeyCode() == field_148282_b.getKeyCode()))
          {
            var10 = true;
            break;
          }
        }
      }
      
      if (var9)
      {
        btnChangeKeyBinding.displayString = (EnumChatFormatting.WHITE + "> " + EnumChatFormatting.YELLOW + btnChangeKeyBinding.displayString + EnumChatFormatting.WHITE + " <");
      }
      else if (var10)
      {
        btnChangeKeyBinding.displayString = (EnumChatFormatting.RED + btnChangeKeyBinding.displayString);
      }
      
      btnChangeKeyBinding.drawButton(mc, mouseX, mouseY);
    }
    
    public boolean mousePressed(int p_148278_1_, int p_148278_2_, int p_148278_3_, int p_148278_4_, int p_148278_5_, int p_148278_6_)
    {
      if (btnChangeKeyBinding.mousePressed(mc, p_148278_2_, p_148278_3_))
      {
        field_148191_k.buttonId = field_148282_b;
        return true;
      }
      if (btnReset.mousePressed(mc, p_148278_2_, p_148278_3_))
      {
        mc.gameSettings.setOptionKeyBinding(field_148282_b, field_148282_b.getKeyCodeDefault());
        KeyBinding.resetKeyBindingArrayAndHash();
        return true;
      }
      

      return false;
    }
    

    public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY)
    {
      btnChangeKeyBinding.mouseReleased(x, y);
      btnReset.mouseReleased(x, y);
    }
    
    public void setSelected(int p_178011_1_, int p_178011_2_, int p_178011_3_) {}
    
    KeyEntry(KeyBinding p_i45030_2_, Object p_i45030_3_)
    {
      this(p_i45030_2_);
    }
  }
}

package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.realms.RealmsScrolledSelectionList;

public class GuiSlotRealmsProxy extends GuiSlot
{
  private final RealmsScrolledSelectionList selectionList;
  private static final String __OBFID = "CL_00001846";
  
  public GuiSlotRealmsProxy(RealmsScrolledSelectionList selectionListIn, int p_i1085_2_, int p_i1085_3_, int p_i1085_4_, int p_i1085_5_, int p_i1085_6_)
  {
    super(Minecraft.getMinecraft(), p_i1085_2_, p_i1085_3_, p_i1085_4_, p_i1085_5_, p_i1085_6_);
    selectionList = selectionListIn;
  }
  
  protected int getSize()
  {
    return selectionList.getItemCount();
  }
  



  protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY)
  {
    selectionList.selectItem(slotIndex, isDoubleClick, mouseX, mouseY);
  }
  



  protected boolean isSelected(int slotIndex)
  {
    return selectionList.isSelectedItem(slotIndex);
  }
  
  protected void drawBackground()
  {
    selectionList.renderBackground();
  }
  
  protected void drawSlot(int p_180791_1_, int p_180791_2_, int p_180791_3_, int p_180791_4_, int p_180791_5_, int p_180791_6_)
  {
    selectionList.renderItem(p_180791_1_, p_180791_2_, p_180791_3_, p_180791_4_, p_180791_5_, p_180791_6_);
  }
  
  public int func_154338_k()
  {
    return width;
  }
  
  public int func_154339_l()
  {
    return mouseY;
  }
  
  public int func_154337_m()
  {
    return mouseX;
  }
  



  protected int getContentHeight()
  {
    return selectionList.getMaxPosition();
  }
  
  protected int getScrollBarX()
  {
    return selectionList.getScrollbarPosition();
  }
  
  public void func_178039_p()
  {
    super.func_178039_p();
  }
}

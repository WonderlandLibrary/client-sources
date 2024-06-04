package net.minecraft.client.gui;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.ResourcePackListEntry;
import net.minecraft.util.EnumChatFormatting;

public abstract class GuiResourcePackList extends GuiListExtended
{
  protected final Minecraft mc;
  protected final List field_148204_l;
  private static final String __OBFID = "CL_00000825";
  
  public GuiResourcePackList(Minecraft mcIn, int p_i45055_2_, int p_i45055_3_, List p_i45055_4_)
  {
    super(mcIn, p_i45055_2_, p_i45055_3_, 32, p_i45055_3_ - 55 + 4, 36);
    mc = mcIn;
    field_148204_l = p_i45055_4_;
    field_148163_i = false;
    setHasListHeader(true, (int)(fontRendererObj.FONT_HEIGHT * 1.5F));
  }
  



  protected void drawListHeader(int p_148129_1_, int p_148129_2_, Tessellator p_148129_3_)
  {
    String var4 = EnumChatFormatting.UNDERLINE + EnumChatFormatting.BOLD + getListHeader();
    mc.fontRendererObj.drawString(var4, p_148129_1_ + width / 2 - mc.fontRendererObj.getStringWidth(var4) / 2, Math.min(top + 3, p_148129_2_), 16777215);
  }
  
  protected abstract String getListHeader();
  
  public List getList()
  {
    return field_148204_l;
  }
  
  protected int getSize()
  {
    return getList().size();
  }
  



  public ResourcePackListEntry getListEntry(int p_148180_1_)
  {
    return (ResourcePackListEntry)getList().get(p_148180_1_);
  }
  



  public int getListWidth()
  {
    return width;
  }
  
  protected int getScrollBarX()
  {
    return right - 6;
  }
  



  public GuiListExtended.IGuiListEntry getListEntry1(int p_148180_1_)
  {
    return getListEntry(p_148180_1_);
  }
}

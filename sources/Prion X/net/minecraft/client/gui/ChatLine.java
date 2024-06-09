package net.minecraft.client.gui;

import net.minecraft.util.IChatComponent;






public class ChatLine
{
  private final int updateCounterCreated;
  private final IChatComponent lineString;
  private final int chatLineID;
  private static final String __OBFID = "CL_00000627";
  
  public ChatLine(int p_i45000_1_, IChatComponent p_i45000_2_, int p_i45000_3_)
  {
    lineString = p_i45000_2_;
    updateCounterCreated = p_i45000_1_;
    chatLineID = p_i45000_3_;
  }
  
  public IChatComponent getChatComponent()
  {
    return lineString;
  }
  
  public int getUpdatedCounter()
  {
    return updateCounterCreated;
  }
  
  public int getChatLineID()
  {
    return chatLineID;
  }
}

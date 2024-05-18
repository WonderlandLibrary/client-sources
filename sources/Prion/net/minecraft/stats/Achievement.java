package net.minecraft.stats;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.StatCollector;



























public class Achievement
  extends StatBase
{
  public final int displayColumn;
  public final int displayRow;
  public final Achievement parentAchievement;
  private final String achievementDescription;
  private IStatStringFormat statStringFormatter;
  public final ItemStack theItemStack;
  private boolean isSpecial;
  private static final String __OBFID = "CL_00001466";
  
  public Achievement(String p_i46327_1_, String p_i46327_2_, int p_i46327_3_, int p_i46327_4_, Item p_i46327_5_, Achievement p_i46327_6_)
  {
    this(p_i46327_1_, p_i46327_2_, p_i46327_3_, p_i46327_4_, new ItemStack(p_i46327_5_), p_i46327_6_);
  }
  
  public Achievement(String p_i45301_1_, String p_i45301_2_, int p_i45301_3_, int p_i45301_4_, Block p_i45301_5_, Achievement p_i45301_6_)
  {
    this(p_i45301_1_, p_i45301_2_, p_i45301_3_, p_i45301_4_, new ItemStack(p_i45301_5_), p_i45301_6_);
  }
  
  public Achievement(String p_i45302_1_, String p_i45302_2_, int p_i45302_3_, int p_i45302_4_, ItemStack p_i45302_5_, Achievement p_i45302_6_)
  {
    super(p_i45302_1_, new ChatComponentTranslation("achievement." + p_i45302_2_, new Object[0]));
    theItemStack = p_i45302_5_;
    achievementDescription = ("achievement." + p_i45302_2_ + ".desc");
    displayColumn = p_i45302_3_;
    displayRow = p_i45302_4_;
    
    if (p_i45302_3_ < AchievementList.minDisplayColumn)
    {
      AchievementList.minDisplayColumn = p_i45302_3_;
    }
    
    if (p_i45302_4_ < AchievementList.minDisplayRow)
    {
      AchievementList.minDisplayRow = p_i45302_4_;
    }
    
    if (p_i45302_3_ > AchievementList.maxDisplayColumn)
    {
      AchievementList.maxDisplayColumn = p_i45302_3_;
    }
    
    if (p_i45302_4_ > AchievementList.maxDisplayRow)
    {
      AchievementList.maxDisplayRow = p_i45302_4_;
    }
    
    parentAchievement = p_i45302_6_;
  }
  
  public Achievement func_180789_a()
  {
    isIndependent = true;
    return this;
  }
  




  public Achievement setSpecial()
  {
    isSpecial = true;
    return this;
  }
  
  public Achievement func_180788_c()
  {
    super.registerStat();
    AchievementList.achievementList.add(this);
    return this;
  }
  



  public boolean isAchievement()
  {
    return true;
  }
  
  public IChatComponent getStatName()
  {
    IChatComponent var1 = super.getStatName();
    var1.getChatStyle().setColor(getSpecial() ? EnumChatFormatting.DARK_PURPLE : EnumChatFormatting.GREEN);
    return var1;
  }
  
  public Achievement func_180787_a(Class p_180787_1_)
  {
    return (Achievement)super.func_150953_b(p_180787_1_);
  }
  



  public String getDescription()
  {
    return statStringFormatter != null ? statStringFormatter.formatString(StatCollector.translateToLocal(achievementDescription)) : StatCollector.translateToLocal(achievementDescription);
  }
  



  public Achievement setStatStringFormatter(IStatStringFormat p_75988_1_)
  {
    statStringFormatter = p_75988_1_;
    return this;
  }
  




  public boolean getSpecial()
  {
    return isSpecial;
  }
  
  public StatBase func_150953_b(Class p_150953_1_)
  {
    return func_180787_a(p_150953_1_);
  }
  



  public StatBase registerStat()
  {
    return func_180788_c();
  }
  




  public StatBase initIndependentStat()
  {
    return func_180789_a();
  }
}

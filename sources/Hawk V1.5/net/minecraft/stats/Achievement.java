package net.minecraft.stats;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.StatCollector;

public class Achievement extends StatBase {
   public final ItemStack theItemStack;
   public final int displayRow;
   private boolean isSpecial;
   public final int displayColumn;
   public final Achievement parentAchievement;
   private IStatStringFormat statStringFormatter;
   private static final String __OBFID = "CL_00001466";
   private final String achievementDescription;

   public String getDescription() {
      return this.statStringFormatter != null ? this.statStringFormatter.formatString(StatCollector.translateToLocal(this.achievementDescription)) : StatCollector.translateToLocal(this.achievementDescription);
   }

   public Achievement(String var1, String var2, int var3, int var4, Item var5, Achievement var6) {
      this(var1, var2, var3, var4, new ItemStack(var5), var6);
   }

   public StatBase initIndependentStat() {
      return this.func_180789_a();
   }

   public Achievement func_180788_c() {
      super.registerStat();
      AchievementList.achievementList.add(this);
      return this;
   }

   public boolean isAchievement() {
      return true;
   }

   public Achievement setStatStringFormatter(IStatStringFormat var1) {
      this.statStringFormatter = var1;
      return this;
   }

   public IChatComponent getStatName() {
      IChatComponent var1 = super.getStatName();
      var1.getChatStyle().setColor(this.getSpecial() ? EnumChatFormatting.DARK_PURPLE : EnumChatFormatting.GREEN);
      return var1;
   }

   public StatBase registerStat() {
      return this.func_180788_c();
   }

   public Achievement func_180789_a() {
      this.isIndependent = true;
      return this;
   }

   public Achievement setSpecial() {
      this.isSpecial = true;
      return this;
   }

   public Achievement(String var1, String var2, int var3, int var4, ItemStack var5, Achievement var6) {
      super(var1, new ChatComponentTranslation(String.valueOf((new StringBuilder("achievement.")).append(var2)), new Object[0]));
      this.theItemStack = var5;
      this.achievementDescription = String.valueOf((new StringBuilder("achievement.")).append(var2).append(".desc"));
      this.displayColumn = var3;
      this.displayRow = var4;
      if (var3 < AchievementList.minDisplayColumn) {
         AchievementList.minDisplayColumn = var3;
      }

      if (var4 < AchievementList.minDisplayRow) {
         AchievementList.minDisplayRow = var4;
      }

      if (var3 > AchievementList.maxDisplayColumn) {
         AchievementList.maxDisplayColumn = var3;
      }

      if (var4 > AchievementList.maxDisplayRow) {
         AchievementList.maxDisplayRow = var4;
      }

      this.parentAchievement = var6;
   }

   public StatBase func_150953_b(Class var1) {
      return this.func_180787_a(var1);
   }

   public Achievement func_180787_a(Class var1) {
      return (Achievement)super.func_150953_b(var1);
   }

   public Achievement(String var1, String var2, int var3, int var4, Block var5, Achievement var6) {
      this(var1, var2, var3, var4, new ItemStack(var5), var6);
   }

   public boolean getSpecial() {
      return this.isSpecial;
   }
}

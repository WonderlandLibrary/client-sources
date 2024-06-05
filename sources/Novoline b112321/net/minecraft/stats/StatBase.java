package net.minecraft.stats;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import net.Ec;
import net.ah_;
import net.minecraft.event.HoverEvent;
import net.minecraft.event.HoverEvent$Action;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.stats.IStatType;
import net.minecraft.stats.ObjectiveStat;
import net.minecraft.stats.StatBase$1;
import net.minecraft.stats.StatBase$4;
import net.minecraft.stats.StatList;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public class StatBase {
   public final String statId;
   private final IChatComponent statName;
   public boolean isIndependent;
   private final IStatType type;
   private final IScoreObjectiveCriteria field_150957_c;
   private Class field_150956_d;
   private static NumberFormat numberFormat = NumberFormat.getIntegerInstance(Locale.US);
   public static IStatType simpleStatType = new StatBase$1();
   private static DecimalFormat decimalFormat = new DecimalFormat("########0.00");
   public static IStatType timeStatType = new Ec();
   public static IStatType distanceStatType = new ah_();
   public static IStatType field_111202_k = new StatBase$4();

   public StatBase(String var1, IChatComponent var2, IStatType var3) {
      this.statId = var1;
      this.statName = var2;
      this.type = var3;
      this.field_150957_c = new ObjectiveStat(this);
      IScoreObjectiveCriteria.INSTANCES.put(this.field_150957_c.getName(), this.field_150957_c);
   }

   public StatBase(String var1, IChatComponent var2) {
      this(var1, var2, simpleStatType);
   }

   public StatBase initIndependentStat() {
      this.isIndependent = true;
      return this;
   }

   public StatBase registerStat() {
      if(StatList.oneShotStats.containsKey(this.statId)) {
         throw new RuntimeException("Duplicate stat id: \"" + ((StatBase)StatList.oneShotStats.get(this.statId)).statName + "\" and \"" + this.statName + "\" at id " + this.statId);
      } else {
         StatList.allStats.add(this);
         StatList.oneShotStats.put(this.statId, this);
         return this;
      }
   }

   public boolean isAchievement() {
      return false;
   }

   public String format(int var1) {
      return this.type.format(var1);
   }

   public IChatComponent getStatName() {
      IChatComponent var1 = this.statName.createCopy();
      var1.getChatStyle().setColor(EnumChatFormatting.GRAY);
      var1.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent$Action.SHOW_ACHIEVEMENT, new ChatComponentText(this.statId)));
      return var1;
   }

   public IChatComponent func_150955_j() {
      IChatComponent var1 = this.getStatName();
      IChatComponent var2 = (new ChatComponentText("[")).appendSibling(var1).appendText("]");
      var2.setChatStyle(var1.getChatStyle());
      return var2;
   }

   public boolean equals(Object var1) {
      if(this == var1) {
         return true;
      } else if(this.getClass() == var1.getClass()) {
         StatBase var2 = (StatBase)var1;
         return this.statId.equals(var2.statId);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.statId.hashCode();
   }

   public String toString() {
      return "Stat{id=" + this.statId + ", nameId=" + this.statName + ", awardLocallyOnly=" + this.isIndependent + ", formatter=" + this.type + ", objectiveCriteria=" + this.field_150957_c + '}';
   }

   public IScoreObjectiveCriteria func_150952_k() {
      return this.field_150957_c;
   }

   public Class func_150954_l() {
      return this.field_150956_d;
   }

   public StatBase func_150953_b(Class var1) {
      this.field_150956_d = var1;
      return this;
   }

   static NumberFormat access$000() {
      return numberFormat;
   }

   static DecimalFormat access$100() {
      return decimalFormat;
   }

   private static RuntimeException a(RuntimeException var0) {
      return var0;
   }
}

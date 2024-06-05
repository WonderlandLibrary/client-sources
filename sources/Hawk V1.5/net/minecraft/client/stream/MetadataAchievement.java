package net.minecraft.client.stream;

import net.minecraft.stats.Achievement;

public class MetadataAchievement extends Metadata {
   private static final String __OBFID = "CL_00001824";

   public MetadataAchievement(Achievement var1) {
      super("achievement");
      this.func_152808_a("achievement_id", var1.statId);
      this.func_152808_a("achievement_name", var1.getStatName().getUnformattedText());
      this.func_152808_a("achievement_description", var1.getDescription());
      this.func_152807_a(String.valueOf((new StringBuilder("Achievement '")).append(var1.getStatName().getUnformattedText()).append("' obtained!")));
   }
}

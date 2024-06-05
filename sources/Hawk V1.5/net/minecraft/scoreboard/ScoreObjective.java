package net.minecraft.scoreboard;

public class ScoreObjective {
   private final IScoreObjectiveCriteria objectiveCriteria;
   private IScoreObjectiveCriteria.EnumRenderType field_178768_d;
   private final Scoreboard theScoreboard;
   private String displayName;
   private final String name;
   private static final String __OBFID = "CL_00000614";

   public Scoreboard getScoreboard() {
      return this.theScoreboard;
   }

   public String getName() {
      return this.name;
   }

   public void func_178767_a(IScoreObjectiveCriteria.EnumRenderType var1) {
      this.field_178768_d = var1;
      this.theScoreboard.func_96532_b(this);
   }

   public IScoreObjectiveCriteria.EnumRenderType func_178766_e() {
      return this.field_178768_d;
   }

   public IScoreObjectiveCriteria getCriteria() {
      return this.objectiveCriteria;
   }

   public ScoreObjective(Scoreboard var1, String var2, IScoreObjectiveCriteria var3) {
      this.theScoreboard = var1;
      this.name = var2;
      this.objectiveCriteria = var3;
      this.displayName = var2;
      this.field_178768_d = var3.func_178790_c();
   }

   public void setDisplayName(String var1) {
      this.displayName = var1;
      this.theScoreboard.func_96532_b(this);
   }

   public String getDisplayName() {
      return this.displayName;
   }
}

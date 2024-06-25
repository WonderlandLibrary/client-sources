package cc.slack.features.commands.api;

public abstract class CMD {
   final CMDInfo cmdInfo = (CMDInfo)this.getClass().getAnnotation(CMDInfo.class);
   private final String name;
   private final String description;
   private final String alias;

   public CMD() {
      this.name = this.cmdInfo.name();
      this.description = this.cmdInfo.description();
      this.alias = this.cmdInfo.alias();
   }

   public abstract void onCommand(String[] var1, String var2);

   public CMDInfo getCmdInfo() {
      return this.cmdInfo;
   }

   public String getName() {
      return this.name;
   }

   public String getDescription() {
      return this.description;
   }

   public String getAlias() {
      return this.alias;
   }
}

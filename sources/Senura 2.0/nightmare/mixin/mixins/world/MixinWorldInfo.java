/*    */ package nightmare.mixin.mixins.world;
/*    */ 
/*    */ import net.minecraft.world.storage.WorldInfo;
/*    */ import nightmare.Nightmare;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Overwrite;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({WorldInfo.class})
/*    */ public class MixinWorldInfo
/*    */ {
/*    */   @Shadow
/*    */   private long field_76094_f;
/*    */   
/*    */   @Overwrite
/*    */   public void func_76068_b(long time) {
/* 19 */     this.field_76094_f = Nightmare.instance.moduleManager.getModuleByName("TimeChanger").isToggled() ? (long)Nightmare.instance.settingsManager.getSettingByName(Nightmare.instance.moduleManager.getModuleByName("TimeChanger"), "Time").getValDouble() : time;
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\mixin\mixins\world\MixinWorldInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
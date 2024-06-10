/*    */ package nightmare.mixin.mixins.entity;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import nightmare.Nightmare;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Overwrite;
/*    */ 
/*    */ 
/*    */ @Mixin({Entity.class})
/*    */ public class MixinEntity
/*    */ {
/*    */   @Overwrite
/*    */   public float func_70111_Y() {
/* 14 */     return Nightmare.instance.moduleManager.getModuleByName("HitBox").isToggled() ? (float)Nightmare.instance.settingsManager.getSettingByName(Nightmare.instance.moduleManager.getModuleByName("HitBox"), "Size").getValDouble() : 0.1F;
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\mixin\mixins\entity\MixinEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
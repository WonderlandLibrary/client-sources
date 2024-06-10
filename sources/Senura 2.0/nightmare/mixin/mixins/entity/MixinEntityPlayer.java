/*    */ package nightmare.mixin.mixins.entity;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import nightmare.event.impl.EventAttackEntity;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ 
/*    */ @Mixin({EntityPlayer.class})
/*    */ public class MixinEntityPlayer
/*    */ {
/*    */   @Inject(method = {"attackTargetEntityWithCurrentItem"}, at = {@At("HEAD")})
/*    */   public void attackTargetEntityWithCurrentItem(Entity entity, CallbackInfo ci) {
/* 17 */     if (entity.func_70075_an()) {
/* 18 */       EventAttackEntity event = new EventAttackEntity(entity);
/* 19 */       event.call();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\mixin\mixins\entity\MixinEntityPlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package nightmare.mixin.mixins.entity;
/*    */ 
/*    */ import net.minecraft.client.entity.EntityPlayerSP;
/*    */ import nightmare.event.impl.EventPreMotionUpdate;
/*    */ import nightmare.event.impl.EventUpdate;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ 
/*    */ @Mixin({EntityPlayerSP.class})
/*    */ public class MixinEntityPlayerSP
/*    */ {
/*    */   @Inject(method = {"onUpdate"}, at = {@At("HEAD")})
/*    */   public void onUpdate(CallbackInfo ci) {
/* 17 */     EventUpdate event = new EventUpdate();
/* 18 */     event.call();
/*    */   }
/*    */   
/*    */   @Inject(method = {"onUpdateWalkingPlayer"}, at = {@At("HEAD")})
/*    */   public void onUpdateWalkingPlayer(CallbackInfo ci) {
/* 23 */     EventPreMotionUpdate event = new EventPreMotionUpdate();
/* 24 */     event.call();
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\mixin\mixins\entity\MixinEntityPlayerSP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
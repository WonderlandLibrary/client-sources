/*    */ package nightmare.mixin.mixins.entity;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.potion.PotionEffect;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({EntityLivingBase.class})
/*    */ public class MixinEntityLivingBase
/*    */ {
/*    */   @Inject(method = {"updatePotionEffects"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/potion/PotionEffect;onUpdate(Lnet/minecraft/entity/EntityLivingBase;)Z")}, locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
/*    */   private void updatePotionEffects(CallbackInfo ci, Iterator<Integer> iterator, Integer integer, PotionEffect potioneffect) {
/* 19 */     if (potioneffect == null)
/* 20 */       ci.cancel(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\mixin\mixins\entity\MixinEntityLivingBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
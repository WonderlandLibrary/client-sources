/*    */ package net.minecraft.client.audio;
/*    */ 
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public abstract class MovingSound
/*    */   extends PositionedSound
/*    */   implements ITickableSound {
/*    */   protected boolean donePlaying = false;
/*    */   
/*    */   protected MovingSound(ResourceLocation location) {
/* 11 */     super(location);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isDonePlaying() {
/* 16 */     return this.donePlaying;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\audio\MovingSound.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
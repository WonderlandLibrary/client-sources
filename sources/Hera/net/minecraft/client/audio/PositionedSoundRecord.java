/*    */ package net.minecraft.client.audio;
/*    */ 
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class PositionedSoundRecord
/*    */   extends PositionedSound
/*    */ {
/*    */   public static PositionedSoundRecord create(ResourceLocation soundResource, float pitch) {
/*  9 */     return new PositionedSoundRecord(soundResource, 0.25F, pitch, false, 0, ISound.AttenuationType.NONE, 0.0F, 0.0F, 0.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public static PositionedSoundRecord create(ResourceLocation soundResource) {
/* 14 */     return new PositionedSoundRecord(soundResource, 1.0F, 1.0F, false, 0, ISound.AttenuationType.NONE, 0.0F, 0.0F, 0.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public static PositionedSoundRecord create(ResourceLocation soundResource, float xPosition, float yPosition, float zPosition) {
/* 19 */     return new PositionedSoundRecord(soundResource, 4.0F, 1.0F, false, 0, ISound.AttenuationType.LINEAR, xPosition, yPosition, zPosition);
/*    */   }
/*    */ 
/*    */   
/*    */   public PositionedSoundRecord(ResourceLocation soundResource, float volume, float pitch, float xPosition, float yPosition, float zPosition) {
/* 24 */     this(soundResource, volume, pitch, false, 0, ISound.AttenuationType.LINEAR, xPosition, yPosition, zPosition);
/*    */   }
/*    */ 
/*    */   
/*    */   private PositionedSoundRecord(ResourceLocation soundResource, float volume, float pitch, boolean repeat, int repeatDelay, ISound.AttenuationType attenuationType, float xPosition, float yPosition, float zPosition) {
/* 29 */     super(soundResource);
/* 30 */     this.volume = volume;
/* 31 */     this.pitch = pitch;
/* 32 */     this.xPosF = xPosition;
/* 33 */     this.yPosF = yPosition;
/* 34 */     this.zPosF = zPosition;
/* 35 */     this.repeat = repeat;
/* 36 */     this.repeatDelay = repeatDelay;
/* 37 */     this.attenuationType = attenuationType;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\audio\PositionedSoundRecord.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
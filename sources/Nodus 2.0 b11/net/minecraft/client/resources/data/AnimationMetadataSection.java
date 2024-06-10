/*  1:   */ package net.minecraft.client.resources.data;
/*  2:   */ 
/*  3:   */ import com.google.common.collect.Sets;
/*  4:   */ import java.util.HashSet;
/*  5:   */ import java.util.Iterator;
/*  6:   */ import java.util.List;
/*  7:   */ import java.util.Set;
/*  8:   */ 
/*  9:   */ public class AnimationMetadataSection
/* 10:   */   implements IMetadataSection
/* 11:   */ {
/* 12:   */   private final List animationFrames;
/* 13:   */   private final int frameWidth;
/* 14:   */   private final int frameHeight;
/* 15:   */   private final int frameTime;
/* 16:   */   private static final String __OBFID = "CL_00001106";
/* 17:   */   
/* 18:   */   public AnimationMetadataSection(List par1List, int par2, int par3, int par4)
/* 19:   */   {
/* 20:19 */     this.animationFrames = par1List;
/* 21:20 */     this.frameWidth = par2;
/* 22:21 */     this.frameHeight = par3;
/* 23:22 */     this.frameTime = par4;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public int getFrameHeight()
/* 27:   */   {
/* 28:27 */     return this.frameHeight;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public int getFrameWidth()
/* 32:   */   {
/* 33:32 */     return this.frameWidth;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public int getFrameCount()
/* 37:   */   {
/* 38:37 */     return this.animationFrames.size();
/* 39:   */   }
/* 40:   */   
/* 41:   */   public int getFrameTime()
/* 42:   */   {
/* 43:42 */     return this.frameTime;
/* 44:   */   }
/* 45:   */   
/* 46:   */   private AnimationFrame getAnimationFrame(int par1)
/* 47:   */   {
/* 48:47 */     return (AnimationFrame)this.animationFrames.get(par1);
/* 49:   */   }
/* 50:   */   
/* 51:   */   public int getFrameTimeSingle(int par1)
/* 52:   */   {
/* 53:52 */     AnimationFrame var2 = getAnimationFrame(par1);
/* 54:53 */     return var2.hasNoTime() ? this.frameTime : var2.getFrameTime();
/* 55:   */   }
/* 56:   */   
/* 57:   */   public boolean frameHasTime(int par1)
/* 58:   */   {
/* 59:58 */     return !((AnimationFrame)this.animationFrames.get(par1)).hasNoTime();
/* 60:   */   }
/* 61:   */   
/* 62:   */   public int getFrameIndex(int par1)
/* 63:   */   {
/* 64:63 */     return ((AnimationFrame)this.animationFrames.get(par1)).getFrameIndex();
/* 65:   */   }
/* 66:   */   
/* 67:   */   public Set getFrameIndexSet()
/* 68:   */   {
/* 69:68 */     HashSet var1 = Sets.newHashSet();
/* 70:69 */     Iterator var2 = this.animationFrames.iterator();
/* 71:71 */     while (var2.hasNext())
/* 72:   */     {
/* 73:73 */       AnimationFrame var3 = (AnimationFrame)var2.next();
/* 74:74 */       var1.add(Integer.valueOf(var3.getFrameIndex()));
/* 75:   */     }
/* 76:77 */     return var1;
/* 77:   */   }
/* 78:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.resources.data.AnimationMetadataSection
 * JD-Core Version:    0.7.0.1
 */
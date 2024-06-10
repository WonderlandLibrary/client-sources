/*  1:   */ package net.minecraft.server.management;
/*  2:   */ 
/*  3:   */ import java.util.Comparator;
/*  4:   */ import net.minecraft.entity.player.EntityPlayerMP;
/*  5:   */ import net.minecraft.util.ChunkCoordinates;
/*  6:   */ 
/*  7:   */ public class PlayerPositionComparator
/*  8:   */   implements Comparator
/*  9:   */ {
/* 10:   */   private final ChunkCoordinates theChunkCoordinates;
/* 11:   */   private static final String __OBFID = "CL_00001422";
/* 12:   */   
/* 13:   */   public PlayerPositionComparator(ChunkCoordinates par1ChunkCoordinates)
/* 14:   */   {
/* 15:14 */     this.theChunkCoordinates = par1ChunkCoordinates;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public int compare(EntityPlayerMP par1EntityPlayerMP, EntityPlayerMP par2EntityPlayerMP)
/* 19:   */   {
/* 20:19 */     double var3 = par1EntityPlayerMP.getDistanceSq(this.theChunkCoordinates.posX, this.theChunkCoordinates.posY, this.theChunkCoordinates.posZ);
/* 21:20 */     double var5 = par2EntityPlayerMP.getDistanceSq(this.theChunkCoordinates.posX, this.theChunkCoordinates.posY, this.theChunkCoordinates.posZ);
/* 22:21 */     return var3 > var5 ? 1 : var3 < var5 ? -1 : 0;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public int compare(Object par1Obj, Object par2Obj)
/* 26:   */   {
/* 27:26 */     return compare((EntityPlayerMP)par1Obj, (EntityPlayerMP)par2Obj);
/* 28:   */   }
/* 29:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.server.management.PlayerPositionComparator
 * JD-Core Version:    0.7.0.1
 */
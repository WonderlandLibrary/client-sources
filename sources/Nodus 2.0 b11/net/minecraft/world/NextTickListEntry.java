/*  1:   */ package net.minecraft.world;
/*  2:   */ 
/*  3:   */ import net.minecraft.block.Block;
/*  4:   */ 
/*  5:   */ public class NextTickListEntry
/*  6:   */   implements Comparable
/*  7:   */ {
/*  8:   */   private static long nextTickEntryID;
/*  9:   */   private final Block field_151352_g;
/* 10:   */   public int xCoord;
/* 11:   */   public int yCoord;
/* 12:   */   public int zCoord;
/* 13:   */   public long scheduledTime;
/* 14:   */   public int priority;
/* 15:   */   private long tickEntryID;
/* 16:   */   private static final String __OBFID = "CL_00000156";
/* 17:   */   
/* 18:   */   public NextTickListEntry(int p_i45370_1_, int p_i45370_2_, int p_i45370_3_, Block p_i45370_4_)
/* 19:   */   {
/* 20:30 */     this.tickEntryID = (nextTickEntryID++);
/* 21:31 */     this.xCoord = p_i45370_1_;
/* 22:32 */     this.yCoord = p_i45370_2_;
/* 23:33 */     this.zCoord = p_i45370_3_;
/* 24:34 */     this.field_151352_g = p_i45370_4_;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public boolean equals(Object par1Obj)
/* 28:   */   {
/* 29:39 */     if (!(par1Obj instanceof NextTickListEntry)) {
/* 30:41 */       return false;
/* 31:   */     }
/* 32:45 */     NextTickListEntry var2 = (NextTickListEntry)par1Obj;
/* 33:46 */     return (this.xCoord == var2.xCoord) && (this.yCoord == var2.yCoord) && (this.zCoord == var2.zCoord) && (Block.isEqualTo(this.field_151352_g, var2.field_151352_g));
/* 34:   */   }
/* 35:   */   
/* 36:   */   public int hashCode()
/* 37:   */   {
/* 38:52 */     return (this.xCoord * 1024 * 1024 + this.zCoord * 1024 + this.yCoord) * 256;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public NextTickListEntry setScheduledTime(long par1)
/* 42:   */   {
/* 43:60 */     this.scheduledTime = par1;
/* 44:61 */     return this;
/* 45:   */   }
/* 46:   */   
/* 47:   */   public void setPriority(int par1)
/* 48:   */   {
/* 49:66 */     this.priority = par1;
/* 50:   */   }
/* 51:   */   
/* 52:   */   public int compareTo(NextTickListEntry par1NextTickListEntry)
/* 53:   */   {
/* 54:71 */     return this.tickEntryID > par1NextTickListEntry.tickEntryID ? 1 : this.tickEntryID < par1NextTickListEntry.tickEntryID ? -1 : this.priority != par1NextTickListEntry.priority ? this.priority - par1NextTickListEntry.priority : this.scheduledTime > par1NextTickListEntry.scheduledTime ? 1 : this.scheduledTime < par1NextTickListEntry.scheduledTime ? -1 : 0;
/* 55:   */   }
/* 56:   */   
/* 57:   */   public String toString()
/* 58:   */   {
/* 59:76 */     return Block.getIdFromBlock(this.field_151352_g) + ": (" + this.xCoord + ", " + this.yCoord + ", " + this.zCoord + "), " + this.scheduledTime + ", " + this.priority + ", " + this.tickEntryID;
/* 60:   */   }
/* 61:   */   
/* 62:   */   public Block func_151351_a()
/* 63:   */   {
/* 64:81 */     return this.field_151352_g;
/* 65:   */   }
/* 66:   */   
/* 67:   */   public int compareTo(Object par1Obj)
/* 68:   */   {
/* 69:86 */     return compareTo((NextTickListEntry)par1Obj);
/* 70:   */   }
/* 71:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.NextTickListEntry
 * JD-Core Version:    0.7.0.1
 */
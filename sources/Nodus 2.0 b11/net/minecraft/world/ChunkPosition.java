/*  1:   */ package net.minecraft.world;
/*  2:   */ 
/*  3:   */ import net.minecraft.util.MathHelper;
/*  4:   */ import net.minecraft.util.Vec3;
/*  5:   */ 
/*  6:   */ public class ChunkPosition
/*  7:   */ {
/*  8:   */   public final int field_151329_a;
/*  9:   */   public final int field_151327_b;
/* 10:   */   public final int field_151328_c;
/* 11:   */   private static final String __OBFID = "CL_00000132";
/* 12:   */   
/* 13:   */   public ChunkPosition(int p_i45363_1_, int p_i45363_2_, int p_i45363_3_)
/* 14:   */   {
/* 15:15 */     this.field_151329_a = p_i45363_1_;
/* 16:16 */     this.field_151327_b = p_i45363_2_;
/* 17:17 */     this.field_151328_c = p_i45363_3_;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public ChunkPosition(Vec3 p_i45364_1_)
/* 21:   */   {
/* 22:22 */     this(MathHelper.floor_double(p_i45364_1_.xCoord), MathHelper.floor_double(p_i45364_1_.yCoord), MathHelper.floor_double(p_i45364_1_.zCoord));
/* 23:   */   }
/* 24:   */   
/* 25:   */   public boolean equals(Object par1Obj)
/* 26:   */   {
/* 27:27 */     if (!(par1Obj instanceof ChunkPosition)) {
/* 28:29 */       return false;
/* 29:   */     }
/* 30:33 */     ChunkPosition var2 = (ChunkPosition)par1Obj;
/* 31:34 */     return (var2.field_151329_a == this.field_151329_a) && (var2.field_151327_b == this.field_151327_b) && (var2.field_151328_c == this.field_151328_c);
/* 32:   */   }
/* 33:   */   
/* 34:   */   public int hashCode()
/* 35:   */   {
/* 36:40 */     return this.field_151329_a * 8976890 + this.field_151327_b * 981131 + this.field_151328_c;
/* 37:   */   }
/* 38:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.ChunkPosition
 * JD-Core Version:    0.7.0.1
 */
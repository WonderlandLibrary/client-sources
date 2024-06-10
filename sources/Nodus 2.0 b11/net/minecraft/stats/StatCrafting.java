/*  1:   */ package net.minecraft.stats;
/*  2:   */ 
/*  3:   */ import net.minecraft.item.Item;
/*  4:   */ import net.minecraft.util.IChatComponent;
/*  5:   */ 
/*  6:   */ public class StatCrafting
/*  7:   */   extends StatBase
/*  8:   */ {
/*  9:   */   private final Item field_150960_a;
/* 10:   */   private static final String __OBFID = "CL_00001470";
/* 11:   */   
/* 12:   */   public StatCrafting(String p_i45305_1_, IChatComponent p_i45305_2_, Item p_i45305_3_)
/* 13:   */   {
/* 14:13 */     super(p_i45305_1_, p_i45305_2_);
/* 15:14 */     this.field_150960_a = p_i45305_3_;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public Item func_150959_a()
/* 19:   */   {
/* 20:19 */     return this.field_150960_a;
/* 21:   */   }
/* 22:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.stats.StatCrafting
 * JD-Core Version:    0.7.0.1
 */
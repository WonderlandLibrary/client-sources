/*  1:   */ package net.minecraft.client.gui;
/*  2:   */ 
/*  3:   */ import me.connorm.Nodus.ui.NodusGuiButton;
/*  4:   */ import net.minecraft.client.Minecraft;
/*  5:   */ 
/*  6:   */ public abstract class TaskLongRunning
/*  7:   */   implements Runnable
/*  8:   */ {
/*  9:   */   protected GuiScreenLongRunningTask field_148419_b;
/* 10:   */   private static final String __OBFID = "CL_00000784";
/* 11:   */   
/* 12:   */   public void func_148412_a(GuiScreenLongRunningTask p_148412_1_)
/* 13:   */   {
/* 14:13 */     this.field_148419_b = p_148412_1_;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public void func_148416_a(String p_148416_1_)
/* 18:   */   {
/* 19:18 */     this.field_148419_b.func_146905_a(p_148416_1_);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void func_148417_b(String p_148417_1_)
/* 23:   */   {
/* 24:23 */     this.field_148419_b.func_146906_b(p_148417_1_);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public Minecraft func_148413_b()
/* 28:   */   {
/* 29:28 */     return this.field_148419_b.func_146903_h();
/* 30:   */   }
/* 31:   */   
/* 32:   */   public boolean func_148418_c()
/* 33:   */   {
/* 34:33 */     return this.field_148419_b.func_146904_i();
/* 35:   */   }
/* 36:   */   
/* 37:   */   public void func_148414_a() {}
/* 38:   */   
/* 39:   */   public void func_148415_a(NodusGuiButton p_148415_1_) {}
/* 40:   */   
/* 41:   */   public void func_148411_d() {}
/* 42:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.TaskLongRunning
 * JD-Core Version:    0.7.0.1
 */
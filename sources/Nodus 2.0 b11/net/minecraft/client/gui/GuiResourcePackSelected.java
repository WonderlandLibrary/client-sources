/*  1:   */ package net.minecraft.client.gui;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import net.minecraft.client.Minecraft;
/*  5:   */ import net.minecraft.client.resources.I18n;
/*  6:   */ 
/*  7:   */ public class GuiResourcePackSelected
/*  8:   */   extends GuiResourcePackList
/*  9:   */ {
/* 10:   */   private static final String __OBFID = "CL_00000827";
/* 11:   */   
/* 12:   */   public GuiResourcePackSelected(Minecraft p_i45056_1_, int p_i45056_2_, int p_i45056_3_, List p_i45056_4_)
/* 13:   */   {
/* 14:13 */     super(p_i45056_1_, p_i45056_2_, p_i45056_3_, p_i45056_4_);
/* 15:   */   }
/* 16:   */   
/* 17:   */   protected String func_148202_k()
/* 18:   */   {
/* 19:18 */     return I18n.format("resourcePack.selected.title", new Object[0]);
/* 20:   */   }
/* 21:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.GuiResourcePackSelected
 * JD-Core Version:    0.7.0.1
 */
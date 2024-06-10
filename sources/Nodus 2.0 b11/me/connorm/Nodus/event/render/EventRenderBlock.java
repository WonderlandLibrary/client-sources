/*  1:   */ package me.connorm.Nodus.event.render;
/*  2:   */ 
/*  3:   */ import me.connorm.lib.event.Cancellable;
/*  4:   */ import net.minecraft.block.Block;
/*  5:   */ 
/*  6:   */ public class EventRenderBlock
/*  7:   */   extends EventRender
/*  8:   */   implements Cancellable
/*  9:   */ {
/* 10:   */   private Block theBlock;
/* 11:   */   private boolean isCancelled;
/* 12:   */   
/* 13:   */   public EventRenderBlock(Block theBlock)
/* 14:   */   {
/* 15:14 */     this.theBlock = theBlock;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public Block getBlock()
/* 19:   */   {
/* 20:19 */     return this.theBlock;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public boolean isCancelled()
/* 24:   */   {
/* 25:25 */     return this.isCancelled;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void setCancelled(boolean newState)
/* 29:   */   {
/* 30:31 */     this.isCancelled = newState;
/* 31:   */   }
/* 32:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.event.render.EventRenderBlock
 * JD-Core Version:    0.7.0.1
 */
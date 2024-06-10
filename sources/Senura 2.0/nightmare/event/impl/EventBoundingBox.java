/*    */ package nightmare.event.impl;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.util.AxisAlignedBB;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import nightmare.event.Event;
/*    */ 
/*    */ 
/*    */ public class EventBoundingBox
/*    */   extends Event
/*    */ {
/*    */   public Block block;
/*    */   public BlockPos pos;
/*    */   public AxisAlignedBB boundingBox;
/*    */   
/*    */   public EventBoundingBox(Block block, BlockPos pos, AxisAlignedBB boundingBox) {
/* 17 */     this.block = block;
/* 18 */     this.pos = pos;
/* 19 */     this.boundingBox = boundingBox;
/*    */   }
/*    */   
/*    */   public Block getBlock() {
/* 23 */     return this.block;
/*    */   }
/*    */   
/*    */   public void setBlock(Block block) {
/* 27 */     this.block = block;
/*    */   }
/*    */   
/*    */   public BlockPos getPos() {
/* 31 */     return this.pos;
/*    */   }
/*    */   
/*    */   public void setPos(BlockPos pos) {
/* 35 */     this.pos = pos;
/*    */   }
/*    */   
/*    */   public AxisAlignedBB getBoundingBox() {
/* 39 */     return this.boundingBox;
/*    */   }
/*    */   
/*    */   public void setBoundingBox(AxisAlignedBB boundingBox) {
/* 43 */     this.boundingBox = boundingBox;
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\event\impl\EventBoundingBox.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
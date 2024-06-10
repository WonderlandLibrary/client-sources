/*    */ package nightmare.mixin.mixins.block;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.AxisAlignedBB;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ import nightmare.event.impl.EventBoundingBox;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Overwrite;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({Block.class})
/*    */ public abstract class MixinBlock
/*    */ {
/*    */   @Shadow
/*    */   public abstract AxisAlignedBB func_180640_a(World paramWorld, BlockPos paramBlockPos, IBlockState paramIBlockState);
/*    */   
/*    */   @Overwrite
/*    */   public void func_180638_a(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List<AxisAlignedBB> list, Entity collidingEntity) {
/* 26 */     AxisAlignedBB axisalignedbb = func_180640_a(worldIn, pos, state);
/*    */     
/* 28 */     EventBoundingBox event = new EventBoundingBox((Block)this, pos, func_180640_a(worldIn, pos, state));
/*    */     
/* 30 */     if (collidingEntity == (Minecraft.func_71410_x()).field_71439_g) {
/* 31 */       event.call();
/*    */       
/* 33 */       if (event.isCancelled()) {
/*    */         return;
/*    */       }
/*    */       
/* 37 */       axisalignedbb = event.getBoundingBox();
/*    */     } 
/*    */     
/* 40 */     if (axisalignedbb != null && mask.func_72326_a(axisalignedbb))
/*    */     {
/* 42 */       list.add(axisalignedbb);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\mixin\mixins\block\MixinBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
 package me.vene.skilled.modules.mods.combat;
 
 import me.vene.skilled.modules.Category;
 import me.vene.skilled.modules.Module;
 import me.vene.skilled.utilities.StringRegistry;
 import me.vene.skilled.values.BooleanValue;
 import net.minecraft.client.Minecraft;
 import net.minecraft.init.Blocks;
 import net.minecraft.util.BlockPos;
 import net.minecraft.util.MathHelper;
 import net.minecraftforge.fml.common.gameevent.TickEvent;
 
 public class BlockVelocity
   extends Module
 {
   private BooleanValue horizontalBlocks = new BooleanValue(StringRegistry.register("Horizontal Blocks"), true);
   private BooleanValue verticalBlocks = new BooleanValue(StringRegistry.register("Vertical Blocks"), false);
   
   private Minecraft mc = Minecraft.getMinecraft();
   private BlockPos firstBlock = null;
   private BlockPos secondBlock = null;
   private BlockPos thirdBlock = null;
   private BlockPos fourthBlock = null;
   private BlockPos fifthBlock = null;
  
   public BlockVelocity() {
     super(StringRegistry.register("BlockVelocity"), 0, Category.C);
     addOption(this.horizontalBlocks);
     addOption(this.verticalBlocks);
   }

   public void onClientTick(TickEvent.ClientTickEvent event) {
     if ((Minecraft.getMinecraft()).thePlayer == null) {
       return;
     }
     if ((Minecraft.getMinecraft()).theWorld == null) {
       return;
     }
     
     if ((Minecraft.getMinecraft()).thePlayer.hurtTime == (Minecraft.getMinecraft()).thePlayer.maxHurtTime && (Minecraft.getMinecraft()).thePlayer.maxHurtTime > 0) {
       double x = this.mc.thePlayer.posX;
       double y = this.mc.thePlayer.posY;
       double z = this.mc.thePlayer.posZ;
      this.firstBlock = new BlockPos(MathHelper.floor_double(x + 1.0D), MathHelper.floor_double(y), MathHelper.floor_double(z));
       this.secondBlock = new BlockPos(MathHelper.floor_double(x - 1.0D), MathHelper.floor_double(y), MathHelper.floor_double(z));
       this.thirdBlock = new BlockPos(MathHelper.floor_double(x), MathHelper.floor_double(y), MathHelper.floor_double(z + 1.0D));
       this.fourthBlock = new BlockPos(MathHelper.floor_double(x), MathHelper.floor_double(y), MathHelper.floor_double(z - 1.0D));
       this.fifthBlock = new BlockPos(MathHelper.floor_double(x), MathHelper.floor_double(y + 2.0D), MathHelper.floor_double(z));     
      if (this.horizontalBlocks.getState()) {
         this.mc.theWorld.setBlockState(this.firstBlock, Blocks.diamond_block.getDefaultState());
         this.mc.theWorld.setBlockState(this.secondBlock, Blocks.diamond_block.getDefaultState());
         this.mc.theWorld.setBlockState(this.thirdBlock, Blocks.diamond_block.getDefaultState());
         this.mc.theWorld.setBlockState(this.fourthBlock, Blocks.diamond_block.getDefaultState());
      }   
       if (this.verticalBlocks.getState()) {
         this.mc.theWorld.setBlockState(this.fifthBlock, Blocks.diamond_block.getDefaultState());       
      }
     
     }
    else if (this.firstBlock != null) {
       this.mc.theWorld.setBlockState(this.firstBlock, Blocks.air.getDefaultState());
       this.mc.theWorld.setBlockState(this.secondBlock, Blocks.air.getDefaultState());
       this.mc.theWorld.setBlockState(this.thirdBlock, Blocks.air.getDefaultState());
       this.mc.theWorld.setBlockState(this.fourthBlock, Blocks.air.getDefaultState());
       this.mc.theWorld.setBlockState(this.fifthBlock, Blocks.air.getDefaultState());
     } 
   }
 }

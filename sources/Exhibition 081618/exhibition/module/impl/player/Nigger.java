package exhibition.module.impl.player;

import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventTick;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class Nigger extends Module {
   int delay = 0;
   int placeDelay = 0;

   public Nigger(ModuleData data) {
      super(data);
   }

   @RegisterEvent(
      events = {EventTick.class}
   )
   public void onEvent(Event event) {
      BlockPos pos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ);
      if (mc.thePlayer.fallDistance <= 3.0F) {
         if (this.material(pos).isReplaceable()) {
            int var1 = -1;

            int var2;
            for(var2 = 0; var2 < 9; ++var2) {
               ItemStack item = mc.thePlayer.inventory.getStackInSlot(var2);
               if (isValid(item) && !isEmpty(item) && item.getItem() instanceof ItemBlock) {
                  var1 = var2;
               }
            }

            if (var1 != -1) {
               var2 = mc.thePlayer.inventory.currentItem;
               mc.thePlayer.inventory.currentItem = var1;
               if (mc.gameSettings.keyBindJump.isPressed()) {
                  if (this.placeDelay >= 7) {
                     this.place(pos);
                     this.placeDelay = 0;
                  }

                  ++this.placeDelay;
               } else {
                  this.place(pos);
               }

               mc.thePlayer.inventory.currentItem = var2;
            }
         }
      }
   }

   private boolean place(BlockPos pos) {
      Vec3 eye = new Vec3(mc.thePlayer.posX, mc.thePlayer.posY + (double)mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ);
      EnumFacing[] face;
      int j = (face = EnumFacing.values()).length;

      for(int i = 0; i < j; ++i) {
         EnumFacing side = face[i];
         BlockPos blockPos = pos.offset(side);
         EnumFacing otherSide = side.getOpposite();
         if (eye.squareDistanceTo((new Vec3((double)pos.getX(), (double)pos.getY(), (double)pos.getZ())).addVector(0.5D, 0.5D, 0.5D)) < eye.squareDistanceTo((new Vec3((double)blockPos.getX(), (double)blockPos.getY(), (double)blockPos.getZ())).addVector(0.5D, 0.5D, 0.5D)) && this.blockPos(blockPos).canCollideCheck(this.blockState(blockPos), false)) {
            Vec3 vec = (new Vec3((double)blockPos.getX(), (double)blockPos.getY(), (double)blockPos.getZ())).addVector(0.5D, 0.5D, 0.5D).add((new Vec3((double)otherSide.getDirectionVec().getX(), (double)otherSide.getDirectionVec().getY(), (double)otherSide.getDirectionVec().getZ())).multi(0.5D));
            if (eye.squareDistanceTo(vec) <= 18.0D) {
               this.look(vec);
               if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.getCurrentEquippedItem(), blockPos, otherSide, vec)) {
                  mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
               }

               return true;
            }
         }
      }

      return false;
   }

   public void look(Vec3 vector) {
      double diffX = vector.xCoord - mc.thePlayer.posX;
      double diffY = vector.yCoord - (mc.thePlayer.posY + (double)mc.thePlayer.getEyeHeight());
      double diffZ = vector.zCoord - mc.thePlayer.posZ;
      double distance = (double)MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
      float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0F;
      float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, distance)));
      mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(mc.thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - mc.thePlayer.rotationYaw), mc.thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - mc.thePlayer.rotationPitch), mc.thePlayer.onGround));
   }

   public static boolean isValid(ItemStack item) {
      if (isEmpty(item)) {
         return false;
      } else {
         return !item.getUnlocalizedName().equalsIgnoreCase("tile.cactus");
      }
   }

   public IBlockState blockState(BlockPos pos) {
      return mc.theWorld.getBlockState(pos);
   }

   public Block blockPos(BlockPos pos) {
      return this.blockState(pos).getBlock();
   }

   public Material material(BlockPos pos) {
      return this.blockPos(pos).getMaterial();
   }

   public static boolean isEmpty(ItemStack stack) {
      return stack == null;
   }
}

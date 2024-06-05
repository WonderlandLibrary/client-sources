package Focus.Beta.IMPL.Module.impl.misc;

import Focus.Beta.API.EventHandler;
import Focus.Beta.API.events.world.EventPostUpdate;
import Focus.Beta.API.events.world.EventPreUpdate;
import Focus.Beta.IMPL.Module.Module;
import Focus.Beta.IMPL.Module.Type;
import Focus.Beta.IMPL.managers.ModuleManager;
import Focus.Beta.IMPL.set.Mode;
import Focus.Beta.IMPL.set.Option;
import Focus.Beta.UTILS.world.Timer;
import com.sun.org.apache.xpath.internal.operations.Bool;
import net.minecraft.block.BlockAir;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class BlockFly extends Module {

    private BlockPos currentPos;

    Timer timer = new Timer();

    private EnumFacing currentFacing;

    private boolean rotated = false;

    public Option<Boolean> tower = new Option<>("Tower", "Tower", true);
    public Mode<Enum> towerMode = new Mode<>("Tower", "Tower", TowerMode.values(), TowerMode.NCP);

    public BlockFly(){
        super("Scaffold", new String[0], Type.MISC, "");
        this.addValues(tower, towerMode);
    }

    @EventHandler
    public void onUpdate(EventPreUpdate E){
        this.rotated = false;
        this.currentFacing = null;
        this.currentPos = null;
        BlockPos pos = new BlockPos(this.mc.thePlayer.posX, mc.thePlayer.posY - 1.0D, this.mc.thePlayer.posZ);
        if(this.mc.theWorld.getBlockState(pos).getBlock() instanceof BlockAir){
            setBlockAndFacing(pos);
            if(this.currentPos != null){
                float[] facing = BlockUtils.getDirectionToBlock(this.currentPos.getX(), this.currentPos.getY(), this.currentPos.getZ(), this.currentFacing);
                if(this.tower.getValue() && this.mc.gameSettings.keyBindJump.pressed && !ModuleManager.getModuleByName("Speed").isEnabled()){
                    switch (towerMode.getModeAsString()){
                        case"NCP":
                            this.mc.thePlayer.motionY = 0.4196D;
                            break;
                    }
                }
                float yaw = facing[0];
                float pitch = Math.min(90.0F, facing[1] + 9.0F);
                this.rotated = true;
                E.setYaw(yaw);
                E.setPitch(pitch);
            }
        }
    }

    @EventHandler
    public void onUpdatePost(EventPostUpdate e){
        if(this.currentPos != null && this.timer.hasReached(40.0D) &&
        this.mc.thePlayer.getHeldItem() != null && this.mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock &&
        this.mc.playerController.onPlayerRightClick(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.getHeldItem(), this.currentPos, this.currentFacing,
                new Vec3(this.currentPos.getX(), this.currentPos.getY(),  this.currentPos.getZ() ))){
            this.timer.reset();
            this.mc.thePlayer.swingItem();
        }
    }

    private void setBlockAndFacing(BlockPos var1) {
        if (this.mc.theWorld.getBlockState(var1.add(0, -1, 0)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(0, -1, 0);
            this.currentFacing = EnumFacing.UP;
        } else if (this.mc.theWorld.getBlockState(var1.add(-1, 0, 0)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(-1, 0, 0);
            this.currentFacing = EnumFacing.EAST;
        } else if (this.mc.theWorld.getBlockState(var1.add(1, 0, 0)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(1, 0, 0);
            this.currentFacing = EnumFacing.WEST;
        } else if (this.mc.theWorld.getBlockState(var1.add(0, 0, -1)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(0, 0, -1);
            this.currentFacing = EnumFacing.SOUTH;
        } else if (this.mc.theWorld.getBlockState(var1.add(0, 0, 1)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(0, 0, 1);
            this.currentFacing = EnumFacing.NORTH;
        } else {
            this.currentFacing = null;
            this.currentPos = null;
        }
    }

    enum TowerMode{
        NCP
    }
}
class BlockUtils {
    private static Minecraft mc = Minecraft.getMinecraft();

    public static float[] getDirectionToBlock(int var0, int var1, int var2, EnumFacing var3) {
        EntityEgg var4 = new EntityEgg((World)mc.theWorld);
        var4.posX = var0 + 0.5D;
        var4.posY = var1 + 0.5D;
        var4.posZ = var2 + 0.5D;
        var4.posX += var3.getDirectionVec().getX() * 0.25D;
        var4.posY += var3.getDirectionVec().getY() * 0.25D;
        var4.posZ += var3.getDirectionVec().getZ() * 0.25D;
        return getDirectionToEntity((Entity)var4);
    }

    private static float[] getDirectionToEntity(Entity var0) {
        return new float[] { getYaw(var0) + mc.thePlayer.rotationYaw, getPitch(var0) + mc.thePlayer.rotationPitch };
    }

    public static float[] getRotationNeededForBlock(EntityPlayer ep, BlockPos pos) {
        double d1 = pos.getX() - ep.posX;
        double d2 = pos.getY() + 0.5D - ep.posY + ep.getEyeHeight();
        double d3 = pos.getZ() - ep.posZ;
        double d4 = Math.sqrt(d1 * d2 * d3);
        float f1 = (float)(Math.atan2(d3, d1) * 180.0D / Math.PI) - 90.0F;
        float f2 = (float)-(Math.atan2(d2, d4) * 180.0D / Math.PI);
        return new float[] { f1, f2 };
    }

    public static float getYaw(Entity var0) {
        double var5, var1 = var0.posX - mc.thePlayer.posX;
        double var3 = var0.posZ - mc.thePlayer.posZ;
        if (var3 < 0.0D && var1 < 0.0D) {
            var5 = 90.0D + Math.toDegrees(Math.atan(var3 / var1));
        } else if (var3 < 0.0D && var1 > 0.0D) {
            var5 = -90.0D + Math.toDegrees(Math.atan(var3 / var1));
        } else {
            var5 = Math.toDegrees(-Math.atan(var1 / var3));
        }
        return MathHelper.wrapAngleTo180_float(-(mc.thePlayer.rotationYaw - (float)var5));
    }

    public static float getPitch(Entity var0) {
        double var1 = var0.posX - mc.thePlayer.posX;
        double var3 = var0.posZ - mc.thePlayer.posZ;
        double var5 = var0.posY - 1.6D + var0.getEyeHeight() - mc.thePlayer.posY;
        double var7 = MathHelper.sqrt_double(var1 * var1 + var3 * var3);
        double var9 = -Math.toDegrees(Math.atan(var5 / var7));
        return -MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationPitch - (float)var9);
    }
}

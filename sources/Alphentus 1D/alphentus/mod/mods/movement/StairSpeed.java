package alphentus.mod.mods.movement;

import alphentus.event.Event;
import alphentus.event.Type;
import alphentus.init.Init;
import alphentus.mod.Mod;
import alphentus.mod.ModCategory;
import alphentus.utils.StrafeUtil;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStairs;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import org.lwjgl.input.Keyboard;

/**
 * @author avox | lmao
 * @since on 07.08.2020.
 */
public class StairSpeed extends Mod {

    Block lastBlock;
    float lastSpeed;
    float speed;

    public StairSpeed() {
        super("StairSpeed", Keyboard.KEY_NONE, true, ModCategory.MOVEMENT);
    }

    @EventTarget
    public void event(Event event) {
        if (!getState() || Init.getInstance().modManager.getModuleByClass(Speed.class).getState())
            return;

        if (event.getType() == Type.TICKUPDATE) {
            BlockPos pos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 0.5, mc.thePlayer.posZ);

            float maxSpeedStair = 1.81F;
            float maxSpeedSlab = 1.61F;
            float multiplyStair = 0.05F;
            float multiplaySlab= 0.05F;
            float maxNotOnStairMult = 0.1F;
            float maxNotOnSlabMult = 0.3F;

            if (isStair(pos) && lastBlock instanceof BlockStairs) {
                if (speed < maxSpeedStair)
                    speed += multiplyStair;
                if (speed > maxSpeedStair)
                    speed -= multiplyStair;
            } else if (isStair(pos) && lastBlock instanceof BlockSlab) {
                if (speed < maxSpeedSlab)
                    speed += multiplaySlab;
                if (speed > maxSpeedSlab)
                    speed -= multiplaySlab;
            } else if (!isStair(pos) && lastBlock instanceof BlockStairs) {
                if (speed > lastSpeed - maxNotOnStairMult)
                    speed = lastSpeed - maxNotOnStairMult;
                if (speed > 1.0F)
                    speed -= 0.0005F;
            } else if (!isStair(pos) && lastBlock instanceof BlockSlab) {
                if (speed > lastSpeed - maxNotOnSlabMult)
                    speed = lastSpeed - maxNotOnSlabMult;
                if (speed > 1.0F)
                    speed -= 0.0005F;
            }

            if (!mc.thePlayer.isMoving() || !mc.thePlayer.onGround || speed < 1.0F)
                speed = 1.0F;

            if (mc.thePlayer.onGround && mc.thePlayer.isMoving() && speed != 1.0F) {
                if (mc.thePlayer.isCollidedHorizontally)
                    mc.thePlayer.setSprinting(false);
                StrafeUtil.strafe();
            }

            if (speed != 1.0F && speed >= 1.0F && mc.thePlayer.isMoving()) {
                mc.thePlayer.motionX *= speed;
                mc.thePlayer.motionZ *= speed;
            }
        }
    }

    public boolean isStair(BlockPos pos) {
        BlockPos fixSlab = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ);
        if (mc.theWorld.getBlockState(pos).getBlock() instanceof BlockStairs) {
            lastBlock = mc.theWorld.getBlockState(pos).getBlock();
            lastSpeed = speed;
            return true;
        }
        if (mc.theWorld.getBlockState(pos).getBlock() instanceof BlockSlab && !(mc.theWorld.getBlockState(fixSlab).getBlock() instanceof BlockSlab)) {
            lastBlock = mc.theWorld.getBlockState(pos).getBlock();
            lastSpeed = speed;
            return true;
        }
        return false;
    }

    @Override
    public void onEnable() {
        speed = 1.0F;
        super.onEnable();
    }
}
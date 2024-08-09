package src.Wiksi.functions.impl.movement;

import com.google.common.eventbus.Subscribe;
import src.Wiksi.events.EventUpdate;
import src.Wiksi.functions.api.Category;
import src.Wiksi.functions.api.Function;
import src.Wiksi.functions.api.FunctionRegister;
import src.Wiksi.functions.settings.Setting;
import src.Wiksi.functions.settings.impl.ModeSetting;
import src.Wiksi.utils.TimerUtil;
import src.Wiksi.utils.player.MoveUtils;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TextFormatting;

@FunctionRegister(
        name = "Jesus",
        type = Category.Movement
)
public class Jesus extends Function {
    public ModeSetting mod = new ModeSetting("Мод", "HvH", new String[]{"HvH", "Legit", "Rage", "Funtime", "NCP", "Standart", "NCP Static", "Grounder", "Matrix 7.0.2", "Matrix 6.8.0", "FunTime Blocks"});
    ItemStack currentStack;
    private final TimerUtil timerUtil;

    public Jesus() {
        this.currentStack = ItemStack.EMPTY;
        this.timerUtil = new TimerUtil();
        this.addSettings(new Setting[]{this.mod});
    }

    public void placeBlock() {
        BlockPos playerPos = mc.player.getPosition();
        BlockPos blockBelowPos = new BlockPos(playerPos.getX(), playerPos.getY() - 1, playerPos.getZ());
        boolean isWaterBelow = mc.world.getBlockState(blockBelowPos).getBlock() == Blocks.WATER;
        boolean isAirAbove = mc.world.isAirBlock(playerPos);
        if (isWaterBelow && isAirAbove) {
            int currentSlot = mc.player.inventory.currentItem;
            int newSlot = this.getItemInHotbar();
            if (newSlot != -1) {
                mc.player.inventory.currentItem = newSlot;
                mc.player.rotationPitchHead = 89.0F;
                BlockRayTraceResult rayTraceResult = new BlockRayTraceResult(new Vector3d(0.0, 0.0, 0.0), Direction.DOWN, blockBelowPos, false);
                mc.playerController.processRightClickBlock(mc.player, mc.world, Hand.MAIN_HAND, rayTraceResult);
                mc.player.swingArm(Hand.MAIN_HAND);
                mc.player.inventory.currentItem = currentSlot;
            }
        }

    }

    public int getItemInHotbar() {
        for(int i = 0; i < 9; ++i) {
            ItemStack stackInSlot = mc.player.inventory.getStackInSlot(i);
            if (stackInSlot.getItem() instanceof BlockItem) {
                return i;
            }
        }

        return -1;
    }

    @Subscribe
    private void onUpdate(EventUpdate update) {
        float yaw;
        Vector3d var20;
        if (this.mod.is("HvH") && mc.player.isInWater()) {
            yaw = 10.0F;
            yaw /= 100.0F;
            double var10000 = mc.player.getForward().x * (double)yaw;
            var10000 = mc.player.getForward().z * (double)yaw;
            mc.player.motion.y = 0.0;
            if (MoveUtils.isMoving() && MoveUtils.getMotion() < 0.699999988079071) {
                var20 = mc.player.motion;
                var20.x *= 1.149999976158142;
                var20 = mc.player.motion;
                var20.z *= 1.149999976158142;
            }
        }

        if (this.mod.is("Legit")) {
            this.LegitMode();
        }

        float pitch;
        double speed;
        double adjustedPitch;
        double pitchRadians;
        double motionX;
        double motionZ;
        if (this.mod.is("InfinityHVH 2") && mc.player.isInWater()) {
            mc.player.motion.y = 0.0;
            mc.player.jumpMovementFactor = 0.01F;
            if (MoveUtils.isMoving()) {
                yaw = mc.player.rotationYawHead;
                pitch = mc.player.rotationPitch;
                speed = 1.0;
                adjustedPitch = 47.0;
                pitchRadians = adjustedPitch / 180.0 * 3.1415927410125732;
                motionX = (double)(-MathHelper.sin(yaw / 180.0F * 3.1415927F)) * Math.cos(pitchRadians) * speed;
                motionZ = (double)MathHelper.cos(yaw / 180.0F * 3.1415927F) * Math.cos(pitchRadians) * speed;
                mc.player.setVelocity(motionX, 0.0, motionZ);
            }
        }

        if (this.mod.is("InfinityHVH 1") && mc.player.isInWater()) {
            mc.player.motion.y = 0.0;
            mc.player.jumpMovementFactor = 0.01F;
            if (MoveUtils.isMoving()) {
                yaw = mc.player.rotationYawHead;
                pitch = mc.player.rotationPitch;
                speed = 1.0;
                adjustedPitch = 55.0;
                pitchRadians = adjustedPitch / 180.0 * 3.1415927410125732;
                motionX = (double)(-MathHelper.sin(yaw / 180.0F * 3.1415927F)) * Math.cos(pitchRadians) * speed;
                motionZ = (double)MathHelper.cos(yaw / 180.0F * 3.1415927F) * Math.cos(pitchRadians) * speed;
                mc.player.setVelocity(motionX, 0.0, motionZ);
            }
        }

        if (this.mod.is("Rage")) {
            this.JesusMatrixOldMode();
        }

        if (this.mod.is("Funtime") && mc.player.isInWater()) {
            mc.player.motion.y = 0.0;
            if (MoveUtils.isMoving() && MoveUtils.getMotion() < 0.4000000059604645) {
                var20 = mc.player.motion;
                var20.x *= 1.1100000143051147;
                var20 = mc.player.motion;
                var20.z *= 1.1100000143051147;
            }
        }

        if (this.mod.is("NCP")) {
            mc.player.jumpMovementFactor = 0.025F;
            if (mc.player.isInWater()) {
                mc.player.jump();
                mc.player.motion.y = 0.02500000037252903;
                if (MoveUtils.isMoving() && MoveUtils.getMotion() < 0.5699999928474426) {
                    var20 = mc.player.motion;
                    var20.x *= 1.2100000381469727;
                    var20 = mc.player.motion;
                    var20.z *= 1.2100000381469727;
                }
            }
        }

        if (this.mod.is("FunSkyHVH")) {
            if (mc.player.isElytraFlying()) {
                if (mc.player.isInWater()) {
                    mc.player.motion.y = 0.0;
                    mc.player.jumpMovementFactor = 0.01F;
                    if (MoveUtils.isMoving()) {
                        yaw = mc.player.rotationYawHead;
                        pitch = mc.player.rotationPitch;
                        speed = 1.2;
                        adjustedPitch = 0.0;
                        pitchRadians = adjustedPitch / 180.0 * 3.1415927410125732;
                        motionX = (double)(-MathHelper.sin(yaw / 180.0F * 3.1415927F)) * Math.cos(pitchRadians) * speed;
                        motionZ = (double)MathHelper.cos(yaw / 180.0F * 3.1415927F) * Math.cos(pitchRadians) * speed;
                        mc.player.setVelocity(motionX, 0.0, motionZ);
                    }
                }
            } else {
                this.currentStack = mc.player.getItemStackFromSlot(EquipmentSlotType.CHEST);
                if (this.currentStack.getItem() == Items.ELYTRA) {
                    this.print(TextFormatting.RED + "Прыгните в воду при этом летя на элитре!");
                    this.print(TextFormatting.RED + "Прыгните в воду при этом летя на элитре!");
                    this.print(TextFormatting.RED + "Прыгните в воду при этом летя на элитре!");
                    this.toggle();
                } else {
                    this.print(TextFormatting.RED + "Наденьте элитру!");
                    this.print(TextFormatting.RED + "Наденьте элитру!");
                    this.print(TextFormatting.RED + "Наденьте элитру!");
                    this.toggle();
                }
            }
        }

        if (this.mod.is("Standart") && mc.player.isInWater()) {
            mc.player.motion.y = 0.0;
        }

        if (this.mod.is("NCP Static") && mc.player.isInWater()) {
            var20 = mc.player.motion;
            var20.y += 0.1;
            if (mc.gameSettings.keyBindJump.isKeyDown()) {
                var20 = mc.player.motion;
                var20.y -= 0.15;
            }
        }

        if (this.mod.is("Grounder") && mc.player.isInWater()) {
            mc.player.setOnGround(true);
            mc.player.jump();
        }

        if (this.mod.is("Solid")) {
            double posY = mc.player.getPosY();
            if (posY > (double)((int)posY) + 0.89 && posY <= (double)((int)posY + 1) || (double)mc.player.fallDistance > 3.5) {
                mc.player.getPositionVec().y = (double)((int)posY + 1) + 1.0E-45;
                if (!mc.player.isInWater()) {
                    BlockPos waterBlockPos = new BlockPos(mc.player.getPosX(), mc.player.getPosY() - 0.1, mc.player.getPosZ());
                    Block waterBlock = mc.world.getBlockState(waterBlockPos).getBlock();
                    mc.player.setOnGround(false);
                    mc.player.motion.y = 0.2;
                    var20 = mc.player.motion;
                    var20.x *= 0.0;
                    var20 = mc.player.motion;
                    var20.z *= 0.0;
                    if (mc.player.ticksExisted == 1) {
                        MoveUtils.setMotion(1.100000023841858);
                        mc.player.ticksExisted = 0;
                    } else {
                        mc.player.ticksExisted = 1;
                    }
                }
            }
        }

        if (this.mod.is("FunTime Blocks")) {
            BlockPos playerPos = mc.player.getPosition();
            boolean hasWaterBelow = false;

            for(int y = playerPos.getY() - 20; y <= playerPos.getY(); ++y) {
                BlockPos blockBelowPos = new BlockPos(playerPos.getX(), y, playerPos.getZ());
                if (mc.world.getBlockState(blockBelowPos).getBlock() == Blocks.WATER) {
                    hasWaterBelow = true;
                    break;
                }
            }

            if (hasWaterBelow) {
                mc.gameSettings.keyBindSprint.setPressed(true);
                this.getItemInHotbar();
                this.placeBlock();
            }
        }

        if (this.mod.is("Matrix 7.0.2")) {
            this.JesusMatrixOldModeSlow();
        }

        if (this.mod.is("Matrix 6.8.0")) {
            this.JesusInfRW();
        }

    }

    private void LegitMode() {
        if (mc.player.getFoodStats().getFoodLevel() > 6 && mc.player.isInWater() && !mc.player.isSwimming() && MoveUtils.isMoving() && MoveUtils.getMotion() < 0.18700000643730164) {
            Vector3d var10000 = mc.player.motion;
            var10000.x *= 1.090000033378601;
            var10000 = mc.player.motion;
            var10000.z *= 1.090000033378601;
        }

    }

    private void JesusMatrixOldMode() {
        if (mc.player.isInWater()) {
            this.updatePlayerMotion();
        }

        if (mc.player.isSwimming()) {
            this.print("Включайте на поверхности воды!");
            this.toggle();
        }

    }

    private void JesusMatrixOldModeSlow() {
        if (mc.player.isInWater()) {
            this.updatePlayerMotion2();
        }

        if (mc.player.isSwimming()) {
            this.print("Включайте на поверхности воды!");
            this.toggle();
        }

    }

    private void JesusInfRW() {
        if (mc.player.isInWater()) {
            this.updatePlayerMotion3();
        }

        if (mc.player.isSwimming()) {
            this.print("Включайте на поверхности воды!");
            this.toggle();
        }

    }

    private void updatePlayerMotion() {
        if (MoveUtils.isMoving()) {
            double motionX = mc.player.getMotion().x;
            double motionY = this.getMotionY();
            double motionZ = mc.player.getMotion().z;
            MoveUtils.setMotion(2.0);
            mc.player.motion.y = motionY;
        } else {
            mc.player.setMotion(0.0, 0.0, 0.0);
        }

    }

    private void updatePlayerMotion2() {
        if (MoveUtils.isMoving()) {
            double motionX = mc.player.getMotion().x;
            double motionY = this.getMotionY();
            double motionZ = mc.player.getMotion().z;
            MoveUtils.setMotion(0.8989999890327454);
            mc.player.motion.y = motionY;
        } else {
            mc.player.setMotion(0.0, 0.0, 0.0);
        }

    }

    private void updatePlayerMotion3() {
        if (MoveUtils.isMoving()) {
            double motionX = mc.player.getMotion().x;
            double motionY = this.getMotionY();
            double motionZ = mc.player.getMotion().z;
            MoveUtils.setMotion(0.6700000166893005);
            mc.player.motion.y = motionY;
        } else {
            mc.player.setMotion(0.0, 0.0, 0.0);
        }

    }

    private double getMotionY() {
        return mc.gameSettings.keyBindSneak.pressed ? 0.0 : (mc.gameSettings.keyBindJump.pressed ? 0.0 : 0.0);
    }
}

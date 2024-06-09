/*
 * Decompiled with CFR 0_122.
 */
package winter.module.modules.modes.phase;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovementInput;
import net.minecraft.world.World;
import winter.event.events.BBEvent;
import winter.event.events.UpdateEvent;
import winter.module.Module;
import winter.module.modules.Phase;
import winter.module.modules.modes.Mode;
import winter.utils.other.Timer;

public class NCP
extends Mode {
    private boolean ncpSetup = false;
    public static Timer timer;
    public static String mode;
    private int resetNext;
    public Phase parent;

    static {
        mode = "NCP";
    }

    public NCP(Module part, String name) {
        super(part, name);
        this.parent = (Phase)part;
        timer = new Timer();
    }

    @Override
    public void enable() {
        if (mode.equals("NCPDisable")) {
            mode = "NCP";
        }
        if (mode.equals("NCP")) {
            this.ncpSetup = false;
            timer.reset();
        }
    }

    @Override
    public void onBB(BBEvent event) {
        if (mode.equals("NCP") && !this.ncpSetup && !this.ncpSetup && (NCP.isInsideBlock() && NCP.mc.gameSettings.keyBindJump.pressed || !NCP.isInsideBlock() && event.getBounds() != null && event.getBounds().maxY > NCP.mc.thePlayer.boundingBox.minY && NCP.mc.thePlayer.isSneaking())) {
            event.setBounds(null);
        }
    }

    @Override
    public void onUpdate(UpdateEvent event) {
        if (mode.equals("NCP")) {
            if (!this.ncpSetup) {
                --this.resetNext;
                double xOff = 0.0;
                double zOff = 0.0;
                double multiplier = 0.5;
                double mx2 = Math.cos(Math.toRadians(NCP.mc.thePlayer.rotationYaw + 90.0f));
                double mz2 = Math.sin(Math.toRadians(NCP.mc.thePlayer.rotationYaw + 90.0f));
                xOff = (double)MovementInput.moveForward * 0.5 * mx2 + (double)MovementInput.moveStrafe * 0.5 * mz2;
                zOff = (double)MovementInput.moveForward * 0.5 * mz2 - (double)MovementInput.moveStrafe * 0.5 * mx2;
                if (NCP.isInsideBlock()) {
                    this.resetNext = 1;
                }
                if (this.resetNext > 0) {
                    NCP.mc.thePlayer.boundingBox.offsetAndUpdate(xOff, 0.0, zOff);
                }
            }
            if (timer.hasPassed(10.0f)) {
                NCP.mc.gameSettings.keyBindJump.pressed = true;
            }
            if (timer.hasPassed(40.0f)) {
                NCP.mc.gameSettings.keyBindSneak.pressed = true;
            }
            if (timer.hasPassed(290.0f)) {
                NCP.mc.gameSettings.keyBindJump.pressed = false;
                NCP.mc.gameSettings.keyBindSneak.pressed = false;
            }
            if (timer.hasPassed(320.0f)) {
                mode = "NCPDisable";
                timer.reset();
            }
        }
        if (mode.equals("NCPDisable") && NCP.mc.thePlayer.isCollidedHorizontally && !NCP.isInsideBlock()) {
            this.ncpSetup = false;
            timer.reset();
            mode = "NCP";
        }
    }

    public static boolean isInsideBlock() {
        int x2 = MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.minX);
        while (x2 < MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.maxX) + 1) {
            int y2 = MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.minY);
            while (y2 < MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.maxY) + 1) {
                int z2 = MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.minZ);
                while (z2 < MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.maxZ) + 1) {
                    AxisAlignedBB boundingBox;
                    Block block = Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(x2, y2, z2)).getBlock();
                    if (block != null && !(block instanceof BlockAir) && (boundingBox = block.getCollisionBoundingBox(Minecraft.getMinecraft().theWorld, new BlockPos(x2, y2, z2), Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(x2, y2, z2)))) != null && Minecraft.getMinecraft().thePlayer.boundingBox.intersectsWith(boundingBox)) {
                        return true;
                    }
                    ++z2;
                }
                ++y2;
            }
            ++x2;
        }
        return false;
    }
}


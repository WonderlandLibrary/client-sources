/*
 * Decompiled with CFR 0_122.
 */
package winter.module.modules;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import winter.event.EventListener;
import winter.event.events.TickEvent;
import winter.module.Module;
import winter.utils.Player;

public class Jesus
extends Module {
    public boolean sink = false;

    public Jesus() {
        super("Jesus", Module.Category.Movement, -16736080);
        this.setBind(36);
    }

    @EventListener
    public void onTick(TickEvent event) {
        this.mode(" Motion");
        if (Jesus.isInLiquid()) {
            boolean bl2 = this.sink = !this.sink;
            double d2 = this.mc.thePlayer.isSneaking() ? -0.12 : (this.mc.gameSettings.keyBindJump.pressed ? 0.4 : (this.mc.thePlayer.motionY = this.sink ? 0.05 : 0.12));
            if (!Player.isMoving()) {
                this.mc.thePlayer.motionX = 0.0;
                this.mc.thePlayer.motionZ = 0.0;
            }
        }
    }

    public static boolean isInLiquid() {
        if (Minecraft.getMinecraft().thePlayer == null) {
            return false;
        }
        boolean inLiquid = false;
        int y2 = (int)Minecraft.getMinecraft().thePlayer.boundingBox.minY;
        int x2 = MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.minX);
        while (x2 < MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.maxX) + 1) {
            int z2 = MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.minZ);
            while (z2 < MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.maxZ) + 1) {
                Block block = Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(x2, y2, z2)).getBlock();
                if (block != null && !(block instanceof BlockAir)) {
                    if (!(block instanceof BlockLiquid)) {
                        return false;
                    }
                    inLiquid = true;
                }
                ++z2;
            }
            ++x2;
        }
        return inLiquid;
    }

    public static boolean isOnLiquid() {
        if (Minecraft.getMinecraft().thePlayer == null) {
            return false;
        }
        boolean onLiquid = false;
        int y2 = (int)Minecraft.getMinecraft().thePlayer.boundingBox.offset((double)0.0, (double)-0.01, (double)0.0).minY;
        int x2 = MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.minX);
        while (x2 < MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.maxX) + 1) {
            int z2 = MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.minZ);
            while (z2 < MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.maxZ) + 1) {
                Block block = Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(x2, y2, z2)).getBlock();
                if (block != null && !(block instanceof BlockAir)) {
                    if (!(block instanceof BlockLiquid)) {
                        return false;
                    }
                    onLiquid = true;
                }
                ++z2;
            }
            ++x2;
        }
        return onLiquid;
    }
}


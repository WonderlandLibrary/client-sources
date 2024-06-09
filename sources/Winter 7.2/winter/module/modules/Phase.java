/*
 * Decompiled with CFR 0_122.
 */
package winter.module.modules;

import java.util.ArrayList;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import winter.event.EventListener;
import winter.event.events.BBEvent;
import winter.event.events.UpdateEvent;
import winter.module.Module;
import winter.module.modules.modes.Mode;
import winter.module.modules.modes.phase.Lemon;
import winter.module.modules.modes.phase.Lithe;
import winter.module.modules.modes.phase.NCP;
import winter.module.modules.modes.phase.Old;
import winter.module.modules.modes.phase.Skip;
import winter.module.modules.modes.phase.Vanilla;
import winter.module.modules.modes.phase.Virtue;
import winter.utils.value.Value;
import winter.utils.value.types.ModeValue;
import winter.utils.value.types.NumberValue;

public class Phase
extends Module {
    public NumberValue lDist;

    public Phase() {
        super("Phase", Module.Category.Exploits, -5689);
        this.addMode(new Lithe(this, "Lithe"));
        this.addMode(new Lemon(this, "Gay"));
        this.addMode(new Skip(this, "Skip"));
        this.addMode(new NCP(this, "NCP"));
        this.addMode(new Virtue(this, "Virtue"));
        this.addMode(new Old(this, "OldPara"));
        this.addMode(new Vanilla(this, "Vanilla"));
        this.addValue(new ModeValue(this));
        this.lDist = new NumberValue("Gay Distance", 0.2, 0.1, 1.5, 0.01);
        this.addValue(this.lDist);
        this.mode(" Lithe");
    }

    @Override
    public void onEnable() {
        ((Mode)this.modes.get(this.modeInt)).enable();
    }

    @EventListener
    public void onBB(BBEvent event) {
        ((Mode)this.modes.get(this.modeInt)).onBB(event);
    }

    @EventListener
    public void onUpdate(UpdateEvent event) {
        ((Mode)this.modes.get(this.modeInt)).onUpdate(event);
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


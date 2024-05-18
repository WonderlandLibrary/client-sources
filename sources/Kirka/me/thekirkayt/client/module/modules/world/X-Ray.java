/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.world;

import java.util.ArrayList;
import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.module.ModuleManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.init.Blocks;

@Module.Mod(displayName="X-Ray")
public class X-Ray
extends Module {
    public static int opacity = 120;
    public static boolean isXray = false;
    private boolean wasFullbright;
    public static ArrayList<Block> xrayBlocks = new ArrayList();
    public static ArrayList<Integer> intBlocks = new ArrayList();

    @Override
    public void enable() {
        super.enable();
        this.wasFullbright = ModuleManager.getModule("Brightness").isEnabled();
        if (!this.wasFullbright) {
            ModuleManager.getModule("Brightness").toggle();
        }
        this.addXrayBlocks();
        isXray = true;
        Minecraft.getMinecraft();
        Minecraft.renderGlobal.loadRenderers();
    }

    @Override
    public void disable() {
        super.disable();
        if (!this.wasFullbright) {
            ModuleManager.getModule("Brightness").toggle();
        }
        this.removeXrayBlocks();
        isXray = false;
        Minecraft.getMinecraft();
        Minecraft.renderGlobal.loadRenderers();
    }

    public void addXrayBlocks() {
        xrayBlocks.add(Block.getBlockById(8));
        xrayBlocks.add(Block.getBlockById(9));
        xrayBlocks.add(Block.getBlockById(10));
        xrayBlocks.add(Block.getBlockById(11));
        xrayBlocks.add(Block.getBlockById(14));
        xrayBlocks.add(Block.getBlockById(15));
        xrayBlocks.add(Block.getBlockById(16));
        xrayBlocks.add(Block.getBlockById(21));
        xrayBlocks.add(Block.getBlockById(56));
        xrayBlocks.add(Block.getBlockById(73));
        xrayBlocks.add(Block.getBlockById(88));
        xrayBlocks.add(Block.getBlockById(112));
        xrayBlocks.add(Block.getBlockById(129));
        xrayBlocks.add(Block.getBlockById(153));
    }

    public void removeXrayBlocks() {
        xrayBlocks.remove(Block.getBlockById(8));
        xrayBlocks.remove(Block.getBlockById(9));
        xrayBlocks.remove(Block.getBlockById(10));
        xrayBlocks.remove(Block.getBlockById(11));
        xrayBlocks.remove(Block.getBlockById(14));
        xrayBlocks.remove(Block.getBlockById(15));
        xrayBlocks.remove(Block.getBlockById(16));
        xrayBlocks.remove(Block.getBlockById(21));
        xrayBlocks.remove(Block.getBlockById(56));
        xrayBlocks.remove(Block.getBlockById(73));
        xrayBlocks.remove(Block.getBlockById(88));
        xrayBlocks.remove(Block.getBlockById(112));
        xrayBlocks.remove(Block.getBlockById(129));
        xrayBlocks.remove(Block.getBlockById(153));
    }

    public static boolean filterBlocks(Block b) {
        return !xrayBlocks.contains(Blocks.leaves) && !xrayBlocks.contains(Blocks.leaves2) && xrayBlocks.contains(b);
    }
}


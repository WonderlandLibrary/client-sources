/*
 * Decompiled with CFR 0_122.
 */
package winter.module.modules;

import java.util.ArrayList;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.BlockPos;
import winter.module.Module;

public class XRay
extends Module {
    public float gamma;
    private static ArrayList<Block> blocks;

    public XRay() {
        super("XRay", Module.Category.Render, -1184871);
        this.setBind(0);
        blocks = new ArrayList();
        blocks.add(this.block(14));
        blocks.add(this.block(15));
        blocks.add(this.block(16));
        blocks.add(this.block(52));
        blocks.add(this.block(54));
        blocks.add(this.block(56));
        blocks.add(this.block(73));
        blocks.add(this.block(74));
        blocks.add(this.block(129));
    }

    @Override
    public void onEnable() {
        this.gamma = this.mc.gameSettings.gammaSetting;
        this.mc.gameSettings.ambientOcclusion = 0;
        this.mc.gameSettings.gammaSetting = 100.0f;
        this.mc.renderGlobal.loadRenderers();
        blocks.clear();
        blocks.add(this.block(14));
        blocks.add(this.block(15));
        blocks.add(this.block(16));
        blocks.add(this.block(52));
        blocks.add(this.block(54));
        blocks.add(this.block(56));
        blocks.add(this.block(73));
        blocks.add(this.block(74));
        blocks.add(this.block(129));
    }

    @Override
    public void onDisable() {
        this.mc.gameSettings.ambientOcclusion = 2;
        this.mc.gameSettings.gammaSetting = this.gamma;
        this.mc.renderGlobal.loadRenderers();
    }

    public static boolean shouldRender(Block block, BlockPos pos) {
        for (Block blockxd : blocks) {
            if (Block.getIdFromBlock(blockxd) != Block.getIdFromBlock(block)) continue;
            return true;
        }
        return false;
    }

    public Block block(int id2) {
        return Block.getBlockById(id2);
    }
}


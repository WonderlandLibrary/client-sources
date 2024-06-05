/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.eventbus.Subscribe
 */
package cc.diablo.module.impl.render;

import cc.diablo.event.impl.Render3DEvent;
import cc.diablo.helpers.render.RenderUtils;
import cc.diablo.module.Category;
import cc.diablo.module.Module;
import cc.diablo.setting.impl.BooleanSetting;
import com.google.common.eventbus.Subscribe;
import java.awt.Color;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;

public class XRay
extends Module {
    public static List<BlockPos> blockPosList = new CopyOnWriteArrayList<BlockPos>();
    public static BooleanSetting coalOre = new BooleanSetting("Coal Ore", false);
    public static BooleanSetting ironOre = new BooleanSetting("Iron Ore", false);
    public static BooleanSetting goldOre = new BooleanSetting("Gold Ore", false);
    public static BooleanSetting redstoneOre = new BooleanSetting("Redstone Ore", false);
    public static BooleanSetting diamondOre = new BooleanSetting("Diamond Ore", true);

    public XRay() {
        super("XRay", "See through walls to fine le ores", 0, Category.Render);
        this.addSettings(coalOre, ironOre, goldOre, redstoneOre, diamondOre);
    }

    @Override
    public void onEnable() {
        blockPosList.clear();
        XRay.mc.renderGlobal.loadRenderers();
        super.onEnable();
    }

    @Override
    public void onDisable() {
        blockPosList.clear();
        XRay.mc.renderGlobal.loadRenderers();
        super.onEnable();
    }

    @Subscribe
    public void onRender3D(Render3DEvent e) {
        for (BlockPos object : blockPosList) {
            if (coalOre.isChecked() && Block.getStateId(Minecraft.theWorld.getBlockState(object)) == 16) {
                RenderUtils.drawLineToPosition(object, new Color(0, 0, 0).getRGB());
            }
            if (ironOre.isChecked() && Block.getStateId(Minecraft.theWorld.getBlockState(object)) == 15) {
                RenderUtils.drawLineToPosition(object, new Color(139, 131, 131).getRGB());
            }
            if (goldOre.isChecked() && Block.getStateId(Minecraft.theWorld.getBlockState(object)) == 14) {
                RenderUtils.drawLineToPosition(object, new Color(255, 204, 36).getRGB());
            }
            if (redstoneOre.isChecked() && Block.getStateId(Minecraft.theWorld.getBlockState(object)) == 73) {
                RenderUtils.drawLineToPosition(object, new Color(255, 54, 54).getRGB());
            }
            if (!diamondOre.isChecked() || Block.getStateId(Minecraft.theWorld.getBlockState(object)) != 56) continue;
            RenderUtils.drawLineToPosition(object, new Color(0, 187, 255).getRGB());
        }
    }
}


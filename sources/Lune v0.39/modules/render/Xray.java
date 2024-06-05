package me.superskidder.lune.modules.render;


import me.superskidder.lune.modules.Mod;
import me.superskidder.lune.modules.ModCategory;
import me.superskidder.lune.events.EventRender3D;
import me.superskidder.lune.events.EventUpdate;
import me.superskidder.lune.manager.event.EventTarget;
import me.superskidder.lune.utils.player.PlayerUtil;
import me.superskidder.lune.utils.render.RenderUtil;
import me.superskidder.lune.values.type.Bool;
import me.superskidder.lune.values.type.Num;
import net.minecraft.block.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class Xray
        extends Mod {
    public static List<BlockPos> blocks = new ArrayList<>();
    public static Num<Number> searchtime = new Num<>("SearchTime", 100, 1, 1000);
    public static Num<Number> range = new Num<>("Range", 60, 1, 150);
    public static Bool<Boolean> cave = new Bool<>("Cave", true);
    public static Bool<Boolean> coal = new Bool<>("Coal", true);
    public static Bool<Boolean> iron = new Bool<>("Iron", true);
    public static Bool<Boolean> gold = new Bool<>("Gold", true);
    public static Bool<Boolean> diammond = new Bool<>("Diammond", true);


    public Xray() {
        super("Xray", ModCategory.Render,"You can see a box on ores");
        addValues(searchtime, range, cave, coal, iron, gold, diammond);
    }


    @EventTarget
    public void onUpdate(EventUpdate e) {
        if (Xray.mc.thePlayer.ticksExisted % Xray.searchtime.getValue().intValue() == 0) {
            new SearchThread().start();
        }
    }


    @EventTarget
    public void onRender(EventRender3D e) {
        for (BlockPos bp : blocks) {
            final BlockPos pos = bp;
            final Minecraft mc = BlockOverlay.mc;
            final Block block = mc.theWorld.getBlockState(pos).getBlock();
            final String s = block.getLocalizedName();
            final double n = pos.getX();
            final double x = n - RenderManager.renderPosX;
            final double n2 = pos.getY();
            final double y = n2 - RenderManager.renderPosY;
            final double n3 = pos.getZ();
            final double z = n3 - RenderManager.renderPosZ;
            GL11.glPushMatrix();
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glDisable(3553);
            GL11.glEnable(2848);
            GL11.glDisable(2929);
            GL11.glDepthMask(false);
            GL11.glColor4f(1, 1, 1, 0.15f);
            final double minX = (block instanceof BlockStairs || Block.getIdFromBlock(block) == 134) ? 0.0 : block.getBlockBoundsMinX();
            final double minY = (block instanceof BlockStairs || Block.getIdFromBlock(block) == 134) ? 0.0 : block.getBlockBoundsMinY();
            final double minZ = (block instanceof BlockStairs || Block.getIdFromBlock(block) == 134) ? 0.0 : block.getBlockBoundsMinZ();
            RenderUtil.drawBoundingBox(new AxisAlignedBB(x + minX, y + minY, z + minZ, x + block.getBlockBoundsMaxX(), y + block.getBlockBoundsMaxY(), z + block.getBlockBoundsMaxZ()));
            if (mc.theWorld.getBlockState(new BlockPos(pos.getX(), pos.getY(), pos.getZ())).getBlock() == Blocks.coal_block) {
                GL11.glColor4f(0.1f, 0.1f, 0.1f, 1.0f);
            } else if (mc.theWorld.getBlockState(new BlockPos(pos.getX(), pos.getY(), pos.getZ())).getBlock() == Blocks.iron_ore) {
                GL11.glColor4f(1, 0, 1, 1.0f);
            } else if (mc.theWorld.getBlockState(new BlockPos(pos.getX(), pos.getY(), pos.getZ())).getBlock() == Blocks.gold_ore) {
                GL11.glColor4f(1, 1, 0, 1.0f);
            } else if (mc.theWorld.getBlockState(new BlockPos(pos.getX(), pos.getY(), pos.getZ())).getBlock() == Blocks.diamond_ore) {
                GL11.glColor4f(0, 1, 1, 1.0f);
            }
            GL11.glLineWidth(1.5f);
            RenderUtil.drawOutlinedBoundingBox(new AxisAlignedBB(x + minX, y + minY, z + minZ, x + block.getBlockBoundsMaxX(), y + block.getBlockBoundsMaxY(), z + block.getBlockBoundsMaxZ()));
            GL11.glDisable(2848);
            GL11.glEnable(3553);
            GL11.glEnable(2929);
            GL11.glDepthMask(true);
            GL11.glDisable(3042);
            GL11.glPopMatrix();
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        }
    }


    @Override
    public void onEnabled() {
        if (mc.theWorld == null) {
            return;
        }
        mc.renderGlobal.loadRenderers();
    }

    @Override
    public void onDisable() {
        blocks.clear();

    }

    public List<BlockPos> getBlocks() {
        return blocks;
    }
}

class SearchThread extends Thread {
    @Override
    public void run() {
        super.run();
        int reach = Xray.range.getValue().intValue();
        for (int y = reach; y >= -reach; --y) {
            for (int x = -reach; x <= reach; ++x) {
                for (int z = -reach; z <= reach; ++z) {
                    BlockPos pos = new BlockPos(Xray.mc.thePlayer.posX + (double) x, Xray.mc.thePlayer.posY + (double) y, Xray.mc.thePlayer.posZ + (double) z);
                    if (Xray.mc.theWorld.getBlockState(pos).getBlock() instanceof BlockOre) {
                        if (!Xray.blocks.contains(pos)) {
                            int i = 0;
                            if (Xray.mc.theWorld.getBlockState(new BlockPos(pos.getX() - 1, pos.getY(), pos.getZ())).getBlock() instanceof BlockAir) {
                                if (!Xray.blocks.contains(pos)) {
                                    i++;
                                }
                            } else if (Xray.mc.theWorld.getBlockState(new BlockPos(pos.getX() + 1, pos.getY(), pos.getZ())).getBlock() instanceof BlockAir) {
                                if (!Xray.blocks.contains(pos)) {
                                    i++;
                                }
                            } else if (Xray.mc.theWorld.getBlockState(new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ())).getBlock() instanceof BlockAir) {
                                if (!Xray.blocks.contains(pos)) {
                                    i++;
                                }
                            } else if (Xray.mc.theWorld.getBlockState(new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ())).getBlock() instanceof BlockAir) {
                                if (!Xray.blocks.contains(pos)) {
                                    i++;
                                }
                            } else if (Xray.mc.theWorld.getBlockState(new BlockPos(pos.getX(), pos.getY(), pos.getZ() + 1)).getBlock() instanceof BlockAir) {
                                if (!Xray.blocks.contains(pos)) {
                                    i++;
                                }
                            } else if (Xray.mc.theWorld.getBlockState(new BlockPos(pos.getX(), pos.getY(), pos.getZ() - 1)).getBlock() instanceof BlockAir) {
                                if (!Xray.blocks.contains(pos)) {
                                    i++;
                                }
                            }
                            if (i >= 1) {
                                if (Xray.mc.theWorld.getBlockState(new BlockPos(pos.getX(), pos.getY(), pos.getZ())).getBlock() == Blocks.diamond_ore) {
                                    PlayerUtil.tellPlayer("Find Diamond Ore!" + pos.getX() + " " + pos.getY() + " " + pos.getZ());
                                }
                                if ((Xray.mc.theWorld.getBlockState(new BlockPos(pos.getX(), pos.getY(), pos.getZ())).getBlock() == Blocks.diamond_ore && (Boolean) Xray.diammond.getValue()) || (Xray.mc.theWorld.getBlockState(new BlockPos(pos.getX(), pos.getY(), pos.getZ())).getBlock() == Blocks.coal_ore && (Boolean) Xray.coal.getValue()) || (Xray.mc.theWorld.getBlockState(new BlockPos(pos.getX(), pos.getY(), pos.getZ())).getBlock() == Blocks.iron_ore && (Boolean) Xray.iron.getValue()) || (Xray.mc.theWorld.getBlockState(new BlockPos(pos.getX(), pos.getY(), pos.getZ())).getBlock() == Blocks.gold_ore && (Boolean) Xray.gold.getValue())) {
                                    Xray.blocks.add(pos);
                                }
                            } else if (!(Boolean) Xray.cave.getValue()) {
                                if (Xray.mc.theWorld.getBlockState(new BlockPos(pos.getX(), pos.getY(), pos.getZ())).getBlock() == Blocks.diamond_ore) {
                                    PlayerUtil.tellPlayer("Find Diamond Ore!" + pos.getX() + " " + pos.getY() + " " + pos.getZ());
                                }
                                if ((Xray.mc.theWorld.getBlockState(new BlockPos(pos.getX(), pos.getY(), pos.getZ())).getBlock() == Blocks.diamond_ore && (Boolean) Xray.diammond.getValue()) || (Xray.mc.theWorld.getBlockState(new BlockPos(pos.getX(), pos.getY(), pos.getZ())).getBlock() == Blocks.coal_ore && (Boolean) Xray.coal.getValue()) || (Xray.mc.theWorld.getBlockState(new BlockPos(pos.getX(), pos.getY(), pos.getZ())).getBlock() == Blocks.iron_ore && (Boolean) Xray.iron.getValue()) || (Xray.mc.theWorld.getBlockState(new BlockPos(pos.getX(), pos.getY(), pos.getZ())).getBlock() == Blocks.gold_ore && (Boolean) Xray.gold.getValue())) {
                                    Xray.blocks.add(pos);
                                }
                            }

                        }
                    }
                }
            }
        }

//        Xray.blocks.clear();

    }
}

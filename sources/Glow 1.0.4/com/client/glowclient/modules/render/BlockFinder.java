package com.client.glowclient.modules.render;

import com.client.glowclient.modules.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.block.*;
import com.client.glowclient.*;
import java.util.*;
import com.client.glowclient.events.*;
import net.minecraft.init.*;
import com.client.glowclient.utils.*;
import net.minecraft.client.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.math.*;

public class BlockFinder extends ModuleContainer
{
    public static BooleanValue M;
    public static BooleanValue tracer;
    public static BooleanValue outline;
    public static BooleanValue L;
    private final ArrayList<BlockPos> A;
    public static BooleanValue B;
    public static NumberValue range;
    
    public BlockFinder() {
        super(Category.RENDER, "BlockFinder", false, -1, "Find select blocks in your world");
        this.A = new ArrayList<BlockPos>();
    }
    
    @SubscribeEvent
    public void M(final EventWorld eventWorld) {
        this.A.removeAll(this.A);
    }
    
    @Override
    public void E() {
        this.A.removeAll(this.A);
    }
    
    @SubscribeEvent
    public void M(final EventRenderWorld eventRenderWorld) {
        try {
            for (final BlockPos blockPos : this.A) {
                if (BlockFinder.B.world.getChunk(blockPos).isLoaded() && BlockFinder.B.world.getChunk(Wrapper.mc.player.getPosition()).isLoaded()) {
                    if (BlockFinder.outline.M()) {
                        if (pB.M(blockPos) instanceof BlockPortal) {
                            final BlockPos blockPos2 = blockPos;
                            final int n = 255;
                            final int n2 = 0;
                            final int n3 = 255;
                            M(blockPos2, n, n, n, n2, n3, n3, n3, n3, 1);
                        }
                        else {
                            final BlockPos blockPos3 = blockPos;
                            final int n4 = 255;
                            final int n5 = 0;
                            final int n6 = 255;
                            Ma.M(blockPos3, n4, n4, n4, n5, n6, n6, n6, n6, 1);
                        }
                    }
                    if (!BlockFinder.tracer.M()) {
                        continue;
                    }
                    final double n7 = blockPos.getX() + 0.5 - BlockFinder.B.getRenderManager().renderPosX;
                    final double n8 = blockPos.getY() + 0.5 - BlockFinder.B.getRenderManager().renderPosY;
                    final double n9 = blockPos.getZ() + 0.5 - BlockFinder.B.getRenderManager().renderPosZ;
                    final double n10 = 0.0;
                    final float n11 = 255.0f;
                    M(n7, n8, n9, n10, n11, n11, n11, n11);
                }
            }
        }
        catch (Exception ex) {}
    }
    
    @SubscribeEvent
    public void D(final EventUpdate eventUpdate) {
        try {
            if (!this.G.hasBeenSet()) {
                this.G.reset();
            }
            if (this.G.delay(20000.0)) {
                this.G.reset();
                this.A.clear();
            }
            final int m = BlockFinder.range.M();
            final int n2;
            final int n = (n2 = 255 - BlockFinder.B.player.ticksExisted % 64 * 4) - 4;
            final BlockPos blockPos = new BlockPos(BlockFinder.B.player.posX, 0.0, BlockFinder.B.player.posZ);
            for (int i = n2; i > n; --i) {
                int n3;
                int j = n3 = m;
                while (j > -m) {
                    int n4;
                    int k = n4 = m;
                    while (k > -m) {
                        if (this.A.size() >= 10000) {
                            return;
                        }
                        final BlockPos add = blockPos.add(n3, i, n4);
                        if (M(this.A, add)) {
                            this.A.remove(add);
                        }
                        if (!BlockFinder.B.world.getChunk(add).isLoaded()) {
                            this.A.remove(add);
                        }
                        if (BlockFinder.M.M() && pB.M(add) instanceof BlockPortal && !this.A.contains(add)) {
                            this.A.add(add);
                        }
                        if (BlockFinder.L.M() && Wrapper.mc.world.getBlockState(add) == Blocks.WATER.getDefaultState() && !this.A.contains(add)) {
                            this.A.add(add);
                        }
                        if (BlockFinder.B.M() && Wrapper.mc.world.getBlockState(add) == Blocks.LAVA.getDefaultState() && !this.A.contains(add)) {
                            this.A.add(add);
                        }
                        k = --n4;
                    }
                    j = --n3;
                }
            }
        }
        catch (Exception ex) {}
    }
    
    private static boolean M(final ArrayList<BlockPos> list, final BlockPos blockPos) {
        int n = 0;
        final Iterator<BlockPos> iterator = list.iterator();
        while (iterator.hasNext()) {
            if (iterator.next() == blockPos) {
                ++n;
            }
        }
        return n > 1;
    }
    
    static {
        BlockFinder.range = ValueFactory.M("BlockFinder", "Range", "Range of Block Finder WARNING: larger values cause immense lag", 70.0, 1.0, 10.0, 500.0);
        BlockFinder.outline = ValueFactory.M("BlockFinder", "Outline", "Outlines select blocks", true);
        BlockFinder.tracer = ValueFactory.M("BlockFinder", "Tracer", "Draws a line to select blocks", false);
        BlockFinder.M = M("Portal");
        BlockFinder.L = M("WaterSource");
        BlockFinder.B = M("LavaSource");
    }
    
    private static void M(final double n, final double n2, final double n3, final double n4, final float n5, final float n6, final float n7, final float n8) {
        final double n9 = 0.0;
        final Vec3d rotateYaw;
        M((rotateYaw = new Vec3d(n9, n9, 1.0).rotatePitch(-(float)Math.toRadians(Minecraft.getMinecraft().player.rotationPitch)).rotateYaw(-(float)Math.toRadians(Minecraft.getMinecraft().player.rotationYaw))).x, rotateYaw.y + BlockFinder.B.player.getEyeHeight(), rotateYaw.z, n, n2, n3, n4, n5, n6, n7, n8);
    }
    
    private static void M(final double n, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final float n8, final float n9, final float n10, final float n11) {
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(0.5f);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4f(n8, n9, n10, n11);
        GlStateManager.disableLighting();
        GL11.glBegin(1);
        GL11.glVertex3d(n, n2, n3);
        GL11.glVertex3d(n4, n5, n6);
        GL11.glVertex3d(n4, n5, n6);
        GL11.glVertex3d(n4, n5 + n7, n6);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        final double n12 = 1.0;
        GL11.glColor3d(n12, n12, n12);
    }
    
    private static void M(final BlockPos blockPos, final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9) {
        try {
            final double renderPosX = Wrapper.mc.getRenderManager().renderPosX;
            final double renderPosY = Wrapper.mc.getRenderManager().renderPosY;
            final double renderPosZ = Wrapper.mc.getRenderManager().renderPosZ;
            final double n10 = blockPos.getX() - renderPosX;
            final double n11 = blockPos.getY() - renderPosY;
            final double n12 = blockPos.getZ() - renderPosZ;
            GL11.glPushMatrix();
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(3042);
            GL11.glLineWidth((float)n9);
            GL11.glDisable(3553);
            GL11.glDisable(2929);
            GL11.glDepthMask(false);
            if (pB.M(blockPos).equals(BlockPortal.getStateById(4186))) {
                GL11.glColor4d((double)(n / 255.0f), (double)(n2 / 255.0f), (double)(n3 / 255.0f), (double)(n4 / 255.0f));
                final AxisAlignedBB axisAlignedBB = new AxisAlignedBB(n10, n11, n12 + 0.37, n10 + 1.0, n11 + 1.0, n12 + 0.63);
                final float n13 = 0.0f;
                Ma.M(axisAlignedBB, n13, n13, n13, n13);
                GL11.glColor4d((double)(n5 / 255.0f), (double)(n6 / 255.0f), (double)(n7 / 255.0f), (double)(n8 / 255.0f));
                Ma.M(new AxisAlignedBB(n10, n11, n12 + 0.37, n10 + 1.0, n11 + 1.0, n12 + 0.63));
            }
            if (pB.M(blockPos).equals(BlockPortal.getStateById(8282))) {
                GL11.glColor4d((double)(n / 255.0f), (double)(n2 / 255.0f), (double)(n3 / 255.0f), (double)(n4 / 255.0f));
                final AxisAlignedBB axisAlignedBB2 = new AxisAlignedBB(n10, n11, n12 + 0.63, n10 + 1.0, n11 + 1.0, n12 + 0.37);
                final float n14 = 0.0f;
                Ma.M(axisAlignedBB2, n14, n14, n14, n14);
                GL11.glColor4d((double)(n5 / 255.0f), (double)(n6 / 255.0f), (double)(n7 / 255.0f), (double)(n8 / 255.0f));
                Ma.M(new AxisAlignedBB(n10 + 0.37, n11, n12 + 0.0, n10 + 0.63, n11 + 1.0, n12 + 1.0));
            }
            GL11.glLineWidth(2.0f);
            GL11.glEnable(3553);
            GL11.glEnable(2929);
            GL11.glDepthMask(true);
            GL11.glDisable(3042);
            GL11.glPopMatrix();
        }
        catch (Exception ex) {}
    }
    
    private static BooleanValue M(final String s) {
        return ValueFactory.M("BlockFinder", s, "Find block - " + s, false);
    }
}

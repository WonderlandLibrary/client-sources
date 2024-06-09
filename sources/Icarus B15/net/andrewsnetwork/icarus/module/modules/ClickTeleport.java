// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.module.modules;

import net.minecraft.block.state.IBlockState;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.BlockLilyPad;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockCake;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockEndPortalFrame;
import net.minecraft.block.BlockSlab;
import net.minecraft.client.renderer.RenderGlobal;
import net.andrewsnetwork.icarus.utilities.RenderHelper;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.block.BlockButton;
import net.minecraft.block.BlockRedstoneTorch;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.BlockReed;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockMushroom;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockSign;
import net.minecraft.block.BlockRedstoneRepeater;
import net.minecraft.block.BlockRedstoneComparator;
import net.minecraft.block.BlockDaylightDetector;
import net.minecraft.block.BlockTripWire;
import net.minecraft.block.BlockTripWireHook;
import net.minecraft.init.Blocks;
import net.minecraft.block.Block;
import net.andrewsnetwork.icarus.utilities.EntityHelper;
import net.andrewsnetwork.icarus.Icarus;
import net.andrewsnetwork.icarus.event.events.PreMotion;
import org.lwjgl.opengl.GL11;
import net.andrewsnetwork.icarus.event.events.RenderIn3D;
import org.lwjgl.input.Mouse;
import net.andrewsnetwork.icarus.event.events.ReachDistance;
import net.andrewsnetwork.icarus.event.Event;
import net.minecraft.util.BlockPos;
import net.andrewsnetwork.icarus.module.Module;

public class ClickTeleport extends Module
{
    private int delay;
    private boolean canDraw;
    private BlockPos teleportPosition;
    
    public ClickTeleport() {
        super("ClickTeleport", -9868951, Category.MISC);
        this.delay = 0;
        this.canDraw = false;
        this.setTag("Click Teleport");
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof ReachDistance) {
            final ReachDistance event = (ReachDistance)e;
            if (((!Mouse.isButtonDown(0) && ClickTeleport.mc.inGameHasFocus) || !ClickTeleport.mc.inGameHasFocus) && ClickTeleport.mc.thePlayer.getItemInUse() == null) {
                event.setReach(35.0f);
                this.canDraw = true;
            }
            else {
                this.canDraw = false;
            }
        }
        else if (e instanceof RenderIn3D) {
            if (ClickTeleport.mc.objectMouseOver != null && ClickTeleport.mc.objectMouseOver.func_178782_a() != null && this.canDraw) {
                for (float offset = -2.0f; offset < 18.0f; ++offset) {
                    final double[] mouseOverPos = { ClickTeleport.mc.objectMouseOver.func_178782_a().getX(), ClickTeleport.mc.objectMouseOver.func_178782_a().getY() + offset, ClickTeleport.mc.objectMouseOver.func_178782_a().getZ() };
                    final BlockPos blockBelowPos = new BlockPos(mouseOverPos[0], mouseOverPos[1], mouseOverPos[2]);
                    final Block blockBelow = ClickTeleport.mc.theWorld.getBlockState(blockBelowPos).getBlock();
                    if (this.canRenderBox(mouseOverPos)) {
                        GL11.glDisable(2896);
                        GL11.glDisable(3553);
                        GL11.glEnable(3042);
                        GL11.glBlendFunc(770, 771);
                        GL11.glDisable(2929);
                        GL11.glEnable(2848);
                        GL11.glDepthMask(false);
                        GL11.glLineWidth(1.25f);
                        this.drawBox(blockBelow, new BlockPos(mouseOverPos[0], mouseOverPos[1], mouseOverPos[2]));
                        this.drawNametags(blockBelow, new BlockPos(mouseOverPos[0], mouseOverPos[1], mouseOverPos[2]));
                        GL11.glDepthMask(true);
                        GL11.glDisable(2848);
                        GL11.glEnable(2929);
                        GL11.glDisable(3042);
                        GL11.glEnable(2896);
                        GL11.glEnable(3553);
                        if (ClickTeleport.mc.inGameHasFocus) {
                            this.teleportPosition = blockBelowPos;
                            break;
                        }
                        this.teleportPosition = null;
                    }
                }
            }
            else if (ClickTeleport.mc.objectMouseOver != null && ClickTeleport.mc.objectMouseOver.entityHit != null) {
                for (float offset = -2.0f; offset < 18.0f; ++offset) {
                    final double[] mouseOverPos = { ClickTeleport.mc.objectMouseOver.entityHit.posX, ClickTeleport.mc.objectMouseOver.entityHit.posY + offset, ClickTeleport.mc.objectMouseOver.entityHit.posZ };
                    final BlockPos blockBelowPos = new BlockPos(mouseOverPos[0], mouseOverPos[1], mouseOverPos[2]);
                    final Block blockBelow = ClickTeleport.mc.theWorld.getBlockState(blockBelowPos).getBlock();
                    if (this.canRenderBox(mouseOverPos)) {
                        GL11.glDisable(2896);
                        GL11.glDisable(3553);
                        GL11.glEnable(3042);
                        GL11.glBlendFunc(770, 771);
                        GL11.glDisable(2929);
                        GL11.glEnable(2848);
                        GL11.glDepthMask(false);
                        GL11.glLineWidth(1.25f);
                        this.drawBox(blockBelow, new BlockPos(mouseOverPos[0], mouseOverPos[1], mouseOverPos[2]));
                        this.drawNametags(blockBelow, new BlockPos(mouseOverPos[0], mouseOverPos[1], mouseOverPos[2]));
                        GL11.glDepthMask(true);
                        GL11.glDisable(2848);
                        GL11.glEnable(2929);
                        GL11.glDisable(3042);
                        GL11.glEnable(2896);
                        GL11.glEnable(3553);
                        if (ClickTeleport.mc.inGameHasFocus) {
                            this.teleportPosition = blockBelowPos;
                            break;
                        }
                        this.teleportPosition = null;
                    }
                }
            }
            else {
                this.teleportPosition = null;
            }
        }
        else if (e instanceof PreMotion) {
            if (this.teleportPosition != null && this.delay == 0 && Mouse.isButtonDown(1)) {
                final double[] playerPosition = { ClickTeleport.mc.thePlayer.posX, ClickTeleport.mc.thePlayer.posY, ClickTeleport.mc.thePlayer.posZ };
                final double[] blockPosition = { this.teleportPosition.getX() + 0.5f, this.teleportPosition.getY() + this.getOffset(ClickTeleport.mc.theWorld.getBlockState(this.teleportPosition).getBlock(), this.teleportPosition) + 1.0, this.teleportPosition.getZ() + 0.5f };
                final Freecam freecam = (Freecam)Icarus.getModuleManager().getModuleByName("freecam");
                if (freecam != null && freecam.isEnabled()) {
                    freecam.setEnabled(false);
                }
                EntityHelper.teleportToPosition(playerPosition, blockPosition, 0.25, 0.0, true, true);
                ClickTeleport.mc.thePlayer.setPosition(blockPosition[0], blockPosition[1], blockPosition[2]);
                this.teleportPosition = null;
                this.delay = 5;
            }
            if (this.delay > 0) {
                --this.delay;
            }
        }
    }
    
    public boolean isValidBlock(final Block block) {
        return block == Blocks.portal || block == Blocks.snow_layer || block instanceof BlockTripWireHook || block instanceof BlockTripWire || block instanceof BlockDaylightDetector || block instanceof BlockRedstoneComparator || block instanceof BlockRedstoneRepeater || block instanceof BlockSign || block instanceof BlockAir || block instanceof BlockPressurePlate || block instanceof BlockTallGrass || block instanceof BlockFlower || block instanceof BlockMushroom || block instanceof BlockDoublePlant || block instanceof BlockReed || block instanceof BlockSapling || block == Blocks.carrots || block == Blocks.wheat || block == Blocks.nether_wart || block == Blocks.potatoes || block == Blocks.pumpkin_stem || block == Blocks.melon_stem || block == Blocks.heavy_weighted_pressure_plate || block == Blocks.light_weighted_pressure_plate || block == Blocks.redstone_wire || block instanceof BlockTorch || block instanceof BlockRedstoneTorch || block == Blocks.lever || block instanceof BlockButton;
    }
    
    private void drawNametags(final Block block, final BlockPos pos) {
        final BlockPos blockPosBelow = new BlockPos(pos.getX(), pos.getY() - 1.0f, pos.getZ());
        final Block blockBelow = ClickTeleport.mc.theWorld.getBlockState(blockPosBelow).getBlock();
        final double offset = this.getOffset(blockBelow, blockPosBelow);
        final double n = pos.getX() + 0.5f;
        ClickTeleport.mc.getRenderManager();
        double x = n - RenderManager.renderPosX;
        final double n2 = pos.getY() - 1.0f;
        ClickTeleport.mc.getRenderManager();
        double y = n2 - RenderManager.renderPosY + offset;
        final double n3 = pos.getZ() + 0.5f;
        ClickTeleport.mc.getRenderManager();
        double z = n3 - RenderManager.renderPosZ;
        double dist = ClickTeleport.mc.thePlayer.getDistance(pos.getX(), pos.getY(), pos.getZ());
        final String text = String.valueOf(Math.round(dist)) + "m";
        final double far = ClickTeleport.mc.gameSettings.renderDistanceChunks * 12.8;
        final double dl = Math.sqrt(x * x + z * z + y * y);
        if (dl > far) {
            final double d = far / dl;
            dist *= d;
            x *= d;
            y *= d;
            z *= d;
        }
        final float var13 = 2.5f + (((float)dist / 5.0f <= 2.0f) ? 2.0f : ((float)dist / 5.0f)) * 0.5f;
        final float var14 = 0.016666668f * var13;
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y + 1.5, z);
        GL11.glNormal3f(0.0f, 1.0f, 0.0f);
        if (ClickTeleport.mc.gameSettings.thirdPersonView == 2) {
            ClickTeleport.mc.getRenderManager();
            GlStateManager.rotate(-RenderManager.playerViewY, 0.0f, 1.0f, 0.0f);
            ClickTeleport.mc.getRenderManager();
            GlStateManager.rotate(RenderManager.playerViewX, -1.0f, 0.0f, 0.0f);
        }
        else {
            ClickTeleport.mc.getRenderManager();
            GlStateManager.rotate(-RenderManager.playerViewY, 0.0f, 1.0f, 0.0f);
            ClickTeleport.mc.getRenderManager();
            GlStateManager.rotate(RenderManager.playerViewX, 1.0f, 0.0f, 0.0f);
        }
        GlStateManager.scale(-var14, -var14, var14);
        final Tessellator tessellator = Tessellator.getInstance();
        final WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        GlStateManager.func_179090_x();
        worldRenderer.startDrawingQuads();
        final int var15 = ClickTeleport.mc.fontRendererObj.getStringWidth(text) / 2;
        worldRenderer.func_178960_a(0.0f, 0.0f, 0.0f, 0.5f);
        worldRenderer.addVertex(-var15 - 2, -2.0, 0.0);
        worldRenderer.addVertex(-var15 - 2, 9.0, 0.0);
        worldRenderer.addVertex(var15 + 2, 9.0, 0.0);
        worldRenderer.addVertex(var15 + 2, -2.0, 0.0);
        tessellator.draw();
        GlStateManager.func_179098_w();
        ClickTeleport.mc.fontRendererObj.func_175063_a(text, -var15, 0.0f, -1);
        GlStateManager.popMatrix();
    }
    
    public boolean canRenderBox(final double[] mouseOverPos) {
        boolean canTeleport = false;
        final Block blockBelowPos = ClickTeleport.mc.theWorld.getBlockState(new BlockPos(mouseOverPos[0], mouseOverPos[1] - 1.0, mouseOverPos[2])).getBlock();
        final Block blockPos = ClickTeleport.mc.theWorld.getBlockState(new BlockPos(mouseOverPos[0], mouseOverPos[1], mouseOverPos[2])).getBlock();
        final Block blockAbovePos = ClickTeleport.mc.theWorld.getBlockState(new BlockPos(mouseOverPos[0], mouseOverPos[1] + 1.0, mouseOverPos[2])).getBlock();
        final boolean validBlockBelow = blockBelowPos.getCollisionBoundingBox(ClickTeleport.mc.theWorld, new BlockPos(mouseOverPos[0], mouseOverPos[1] - 1.0, mouseOverPos[2]), ClickTeleport.mc.theWorld.getBlockState(new BlockPos(mouseOverPos[0], mouseOverPos[1] - 1.0, mouseOverPos[2]))) != null;
        final boolean validBlock = this.isValidBlock(blockPos);
        final boolean validBlockAbove = this.isValidBlock(blockAbovePos);
        if (validBlockBelow && validBlock && validBlockAbove) {
            canTeleport = true;
        }
        return canTeleport;
    }
    
    private void drawBox(final Block block, final BlockPos pos) {
        final double n = pos.getX();
        ClickTeleport.mc.getRenderManager();
        final double x = n - RenderManager.renderPosX;
        final double n2 = pos.getY();
        ClickTeleport.mc.getRenderManager();
        final double y = n2 - RenderManager.renderPosY;
        final double n3 = pos.getZ();
        ClickTeleport.mc.getRenderManager();
        final double z = n3 - RenderManager.renderPosZ;
        final BlockPos blockPosBelow = new BlockPos(pos.getX(), pos.getY() - 1.0f, pos.getZ());
        final Block blockBelow = ClickTeleport.mc.theWorld.getBlockState(blockPosBelow).getBlock();
        final double offset = this.getOffset(blockBelow, blockPosBelow);
        final AxisAlignedBB box = AxisAlignedBB.fromBounds(x, y + offset, z, x + 1.0, y + offset + 0.05999999865889549, z + 1.0);
        GlStateManager.color(2.55f, 2.55f, 2.55f, 0.5f);
        RenderHelper.drawFilledBox(box);
        GlStateManager.color(0.0f, 0.0f, 0.0f, 1.0f);
        RenderGlobal.drawOutlinedBoundingBox(box, -1);
    }
    
    public double getOffset(final Block block, final BlockPos pos) {
        final IBlockState state = ClickTeleport.mc.theWorld.getBlockState(pos);
        double offset = 0.0;
        if (block instanceof BlockSlab && !((BlockSlab)block).isDouble()) {
            offset -= 0.5;
        }
        else if (block instanceof BlockEndPortalFrame) {
            offset -= 0.20000000298023224;
        }
        else if (block instanceof BlockBed) {
            offset -= 0.4399999976158142;
        }
        else if (block instanceof BlockCake) {
            offset -= 0.5;
        }
        else if (block instanceof BlockDaylightDetector) {
            offset -= 0.625;
        }
        else if (!(block instanceof BlockRedstoneComparator) && !(block instanceof BlockRedstoneRepeater)) {
            if (!(block instanceof BlockChest) && block != Blocks.ender_chest) {
                if (block instanceof BlockLilyPad) {
                    offset -= 0.949999988079071;
                }
                else if (block == Blocks.snow_layer) {
                    offset -= 0.875;
                    offset += 0.125f * ((int)state.getValue(BlockSnow.LAYERS_PROP) - 1);
                }
                else if (this.isValidBlock(block)) {
                    --offset;
                }
            }
            else {
                offset -= 0.125;
            }
        }
        else {
            offset -= 0.875;
        }
        return offset;
    }
}

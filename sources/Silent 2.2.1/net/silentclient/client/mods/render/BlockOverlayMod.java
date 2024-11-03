package net.silentclient.client.mods.render;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.silentclient.client.Client;
import net.silentclient.client.mods.Mod;
import net.silentclient.client.mods.ModCategory;
import net.silentclient.client.utils.ColorUtils;

public class BlockOverlayMod extends Mod {
	public BlockOverlayMod() {
		super("Block Overlay", ModCategory.MODS, "silentclient/icons/mods/blockoverlay.png");
	}

	@Override
	public void setup() {
		this.addBooleanSetting("Outline", this, true);
		this.addColorSetting("Outline Color", this, new Color(255, 255, 255));
		this.addSliderSetting("Outline Width", this, 3, 1, 10, true);
		this.addBooleanSetting("Fill", this, false);
		this.addColorSetting("Fill Color", this, new Color(255, 255, 255), 127);
	}
	
	public static void drawFillBoundingBox(AxisAlignedBB p_147590_0_)
    {
        boolean boverlay = Client.getInstance().getModInstances().getModByClass(BlockOverlayMod.class).isEnabled();
    	boolean bfill = boverlay && Client.getInstance().getSettingsManager().getSettingByClass(BlockOverlayMod.class, "Fill").getValBoolean();
    	Color fillColor = Client.getInstance().getSettingsManager().getSettingByClass(BlockOverlayMod.class, "Fill Color").getValColor();
        if (bfill)
        {
            ColorUtils.setColor(fillColor.getRGB());
            Tessellator tessellator = Tessellator.getInstance();
            WorldRenderer worldrenderer = tessellator.getWorldRenderer();
            worldrenderer.begin(7, DefaultVertexFormats.POSITION);
            worldrenderer.pos(p_147590_0_.maxX, p_147590_0_.minY, p_147590_0_.maxZ).endVertex();
            worldrenderer.pos(p_147590_0_.maxX, p_147590_0_.maxY, p_147590_0_.maxZ).endVertex();
            worldrenderer.pos(p_147590_0_.minX, p_147590_0_.maxY, p_147590_0_.maxZ).endVertex();
            worldrenderer.pos(p_147590_0_.minX, p_147590_0_.minY, p_147590_0_.maxZ).endVertex();
            tessellator.draw();
            worldrenderer.begin(7, DefaultVertexFormats.POSITION);
            worldrenderer.pos(p_147590_0_.minX, p_147590_0_.minY, p_147590_0_.minZ).endVertex();
            worldrenderer.pos(p_147590_0_.minX, p_147590_0_.maxY, p_147590_0_.minZ).endVertex();
            worldrenderer.pos(p_147590_0_.maxX, p_147590_0_.maxY, p_147590_0_.minZ).endVertex();
            worldrenderer.pos(p_147590_0_.maxX, p_147590_0_.minY, p_147590_0_.minZ).endVertex();
            tessellator.draw();
            worldrenderer.begin(7, DefaultVertexFormats.POSITION);
            worldrenderer.pos(p_147590_0_.minX, p_147590_0_.maxY, p_147590_0_.minZ).endVertex();
            worldrenderer.pos(p_147590_0_.minX, p_147590_0_.maxY, p_147590_0_.maxZ).endVertex();
            worldrenderer.pos(p_147590_0_.maxX, p_147590_0_.maxY, p_147590_0_.maxZ).endVertex();
            worldrenderer.pos(p_147590_0_.maxX, p_147590_0_.maxY, p_147590_0_.minZ).endVertex();
            tessellator.draw();
            worldrenderer.begin(7, DefaultVertexFormats.POSITION);
            worldrenderer.pos(p_147590_0_.minX, p_147590_0_.minY, p_147590_0_.maxZ).endVertex();
            worldrenderer.pos(p_147590_0_.minX, p_147590_0_.minY, p_147590_0_.minZ).endVertex();
            worldrenderer.pos(p_147590_0_.maxX, p_147590_0_.minY, p_147590_0_.minZ).endVertex();
            worldrenderer.pos(p_147590_0_.maxX, p_147590_0_.minY, p_147590_0_.maxZ).endVertex();
            tessellator.draw();
            worldrenderer.begin(7, DefaultVertexFormats.POSITION);
            worldrenderer.pos(p_147590_0_.maxX, p_147590_0_.minY, p_147590_0_.maxZ).endVertex();
            worldrenderer.pos(p_147590_0_.maxX, p_147590_0_.minY, p_147590_0_.minZ).endVertex();
            worldrenderer.pos(p_147590_0_.maxX, p_147590_0_.maxY, p_147590_0_.minZ).endVertex();
            worldrenderer.pos(p_147590_0_.maxX, p_147590_0_.maxY, p_147590_0_.maxZ).endVertex();
            tessellator.draw();
            worldrenderer.begin(7, DefaultVertexFormats.POSITION);
            worldrenderer.pos(p_147590_0_.minX, p_147590_0_.minY, p_147590_0_.minZ).endVertex();
            worldrenderer.pos(p_147590_0_.minX, p_147590_0_.minY, p_147590_0_.maxZ).endVertex();
            worldrenderer.pos(p_147590_0_.minX, p_147590_0_.maxY, p_147590_0_.maxZ).endVertex();
            worldrenderer.pos(p_147590_0_.minX, p_147590_0_.maxY, p_147590_0_.minZ).endVertex();
            tessellator.draw();
        }
    }

    public static void drawSelectionBox(EntityPlayer player, MovingObjectPosition movingObjectPositionIn, int execute, float partialTicks)
    {
        if (execute == 0 && movingObjectPositionIn.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
        {
        	boolean boverlay = Client.getInstance().getModInstances().getModByClass(BlockOverlayMod.class).isEnabled();
        	boolean boutline = boverlay && Client.getInstance().getSettingsManager().getSettingByClass(BlockOverlayMod.class, "Outline").getValBoolean();
        	Color outlineColor = Client.getInstance().getSettingsManager().getSettingByClass(BlockOverlayMod.class, "Outline Color").getValColor();
        	float outlineWidth = Client.getInstance().getSettingsManager().getSettingByClass(BlockOverlayMod.class, "Outline Width").getValFloat();

            if (boverlay)
            {
                ColorUtils.setColor(outlineColor.getRGB());
                GL11.glLineWidth(outlineWidth);
            }
            else
            {
                GlStateManager.color(0.0F, 0.0F, 0.0F, 0.4F);
                GL11.glLineWidth(2.0F);
            }

            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.disableTexture2D();
            GlStateManager.depthMask(false);
            BlockPos blockpos = movingObjectPositionIn.getBlockPos();
            Block block = Minecraft.getMinecraft().theWorld.getBlockState(blockpos).getBlock();

            if (block.getMaterial() != Material.air && Minecraft.getMinecraft().theWorld.getWorldBorder().contains(blockpos))
            {
                block.setBlockBoundsBasedOnState(Minecraft.getMinecraft().theWorld, blockpos);
                double d0 = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double)partialTicks;
                double d1 = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double)partialTicks;
                double d2 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double)partialTicks;
                AxisAlignedBB axisalignedbb = block.getSelectedBoundingBox(Minecraft.getMinecraft().theWorld, blockpos).expand(0.0020000000949949026D, 0.0020000000949949026D, 0.0020000000949949026D).offset(-d0, -d1, -d2);

                if (boutline || !boverlay)
                {
                    drawOutlinedBoundingBox(axisalignedbb);
                }

                if (boverlay)
                {
                    drawFillBoundingBox(axisalignedbb);
                }
            }

            GlStateManager.depthMask(true);
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
        }
    }

    public static void drawOutlinedBoundingBox(AxisAlignedBB boundingBox)
    {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(3, DefaultVertexFormats.POSITION);
        worldrenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
        worldrenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).endVertex();
        worldrenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).endVertex();
        worldrenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).endVertex();
        worldrenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
        tessellator.draw();
        worldrenderer.begin(3, DefaultVertexFormats.POSITION);
        worldrenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
        worldrenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).endVertex();
        worldrenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        worldrenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        worldrenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
        tessellator.draw();
        worldrenderer.begin(1, DefaultVertexFormats.POSITION);
        worldrenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
        worldrenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
        worldrenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).endVertex();
        worldrenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).endVertex();
        worldrenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).endVertex();
        worldrenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        worldrenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).endVertex();
        worldrenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        tessellator.draw();
    }
}

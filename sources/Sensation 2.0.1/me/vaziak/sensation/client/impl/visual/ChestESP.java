package me.vaziak.sensation.client.impl.visual;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import org.lwjgl.opengl.GL11;

import me.vaziak.sensation.Sensation;
import me.vaziak.sensation.client.api.Category;
import me.vaziak.sensation.client.api.Module;
import me.vaziak.sensation.client.api.event.annotations.Collect;
import me.vaziak.sensation.client.api.event.events.EntityRenderEvent;
import me.vaziak.sensation.client.api.property.impl.ColorProperty;
import me.vaziak.sensation.client.impl.misc.cheststealer.ChestStealer;

import java.awt.*;

/**
 * @author antja03
 */
public class ChestESP extends Module {

    private final ColorProperty prop_chestColor = new ColorProperty("Chest", "The color of normal chests.", null,
            0.1f, 1f, .64f, 90);

    private final ColorProperty prop_chestColorTrap = new ColorProperty("Trap chest", "The color of trapped chests.", null,
            0f, 1f, 0.5f, 50);

    private final ColorProperty prop_chestColorLooted = new ColorProperty("Opened chest", "The color of chests that have been opened.", null,
            0f, 0f, 0.1f, 120);

    private final ColorProperty prop_chestColorEnder = new ColorProperty("Ender chest", "The color of ender chests.", null,
            0.8f, 1f, 0.45f, 90);

    private ChestStealer cheat_chestStealer;

    public ChestESP() {
        super("Chest ESP", Category.VISUAL);
        registerValue(prop_chestColor, prop_chestColorTrap, prop_chestColorLooted, prop_chestColorEnder);
    }

    @Override
    public void onEnable() {
        if (cheat_chestStealer == null) {
            cheat_chestStealer = (ChestStealer) Sensation.instance.cheatManager.getCheatRegistry().get("Chest Stealer");
        }
    }

    @Collect
    public void onEntityRender(EntityRenderEvent event) {
        for (TileEntity tileEntity : mc.theWorld.loadedTileEntityList) {
            if (tileEntity instanceof TileEntityChest) {
                Color color = prop_chestColor.getValue();
                if (cheat_chestStealer == null) {
                    cheat_chestStealer = (ChestStealer) Sensation.instance.cheatManager.getCheatRegistry().get("Chest Stealer");
                }
                if (tileEntity == null || cheat_chestStealer == null || cheat_chestStealer.getLootedChestPositions() == null) return;
                if (cheat_chestStealer.getLootedChestPositions().contains(tileEntity.getPos()))
                    color = prop_chestColorLooted.getValue();

                if (((TileEntityChest) tileEntity).getChestType() == 1)
                    color = prop_chestColorTrap.getValue();

                double renderX = tileEntity.getPos().getX() - mc.getRenderManager().renderPosX;
                double renderY = tileEntity.getPos().getY() - mc.getRenderManager().renderPosY;
                double renderZ = tileEntity.getPos().getZ() - mc.getRenderManager().renderPosZ;
                GL11.glTranslated(renderX, renderY, renderZ);
                drawChestEsp(tileEntity, 0.0D, 0.0D, 0.0D, color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
                GL11.glTranslated(-renderX, -renderY, -renderZ);
            } else if (tileEntity instanceof TileEntityEnderChest) {
                Color color = prop_chestColorEnder.getValue();
                double renderX = tileEntity.getPos().getX() - mc.getRenderManager().renderPosX;
                double renderY = tileEntity.getPos().getY() - mc.getRenderManager().renderPosY;
                double renderZ = tileEntity.getPos().getZ() - mc.getRenderManager().renderPosZ;
                GL11.glTranslated(renderX, renderY, renderZ);
                drawChestEsp(tileEntity, 0.0D, 0.0D, 0.0D, color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
                GL11.glTranslated(-renderX, -renderY, -renderZ);
            }
        }
    }

    public void drawChestEsp(TileEntity tileEntity, double x, double y, double z, int r, int g, int b, int a) {

        double xOff = 0;
        double zOff = 0;
        double xOff2 = 0;
        double zOff2 = 0;

        if (tileEntity instanceof TileEntityChest) {
            TileEntityChest tileEntityChest = (TileEntityChest) tileEntity;
            if (tileEntityChest.adjacentChestXPos != null) {
                xOff = -1;
                xOff2 = -1;
            } else if (tileEntityChest.adjacentChestXNeg != null) {
                xOff = -1 - 0.002;
                xOff2 = 0.0125;
            } else if (tileEntityChest.adjacentChestZPos != null) {
                zOff = -1;
                zOff2 = -1;
            } else if (tileEntityChest.adjacentChestZNeg != null) {
                zOff = -1 - 0.002;
                zOff2 = 0.0125;
            }
        }

        if (xOff == -1 || xOff2 == -1 || zOff == -1 || zOff2 == -1)
            return;

        GlStateManager.pushAttrib();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GL11.glBlendFunc(770, 771);

        GlStateManager.disableLighting();
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glColor4f(r / 255f, g / 255f, b / 255f, a / 255f);
        GL11.glLineWidth(1.5F);

        GL11.glBegin(7);
        GL11.glVertex3d(x + 0.0625 + xOff, y, z + 0.0625 + zOff);
        GL11.glVertex3d(x + 0.0625 + xOff, y - (0.062f * 2) + 1.0D, z + 0.0625 + zOff);
        GL11.glVertex3d(x - 0.0625 + xOff2 + 1.0D, y - (0.062f * 2) + 1.0D, z + 0.0625 + zOff);
        GL11.glVertex3d(x - 0.0625 + xOff2 + 1.0D, y, z + 0.0625 + zOff);
        GL11.glVertex3d(x - 0.0625 + xOff2 + 1.0D, y, z + 0.0625 + zOff);
        GL11.glVertex3d(x - 0.0625 + xOff2 + 1.0D, y - (0.062f * 2) + 1.0D, z + 0.0625 + zOff);
        GL11.glVertex3d(x - 0.0625 + xOff2 + 1.0D, y - (0.062f * 2) + 1.0D, z - 0.0625 + zOff2 + 1.0D);
        GL11.glVertex3d(x - 0.0625 + xOff2 + 1.0D, y, z - 0.0625 + zOff2 + 1.0D);
        GL11.glVertex3d(x - 0.0625 + xOff2 + 1.0D, y, z - 0.0625 + zOff2 + 1.0D);
        GL11.glVertex3d(x - 0.0625 + xOff2 + 1.0D, y - (0.062f * 2) + 1.0D, z - 0.0625 + zOff2 + 1.0D);
        GL11.glVertex3d(x + 0.0625 + xOff, y - (0.062f * 2) + 1.0D, z - 0.0625 + zOff2 + 1.0D);
        GL11.glVertex3d(x + 0.0625 + xOff, y, z - 0.0625 + zOff2 + 1.0D);
        GL11.glVertex3d(x + 0.0625 + xOff, y, z - 0.0625 + zOff2 + 1.0D);
        GL11.glVertex3d(x + 0.0625 + xOff, y - (0.062f * 2) + 1.0D, z - 0.0625 + zOff2 + 1.0D);
        GL11.glVertex3d(x + 0.0625 + xOff, y - (0.062f * 2) + 1.0D, z + 0.0625 + zOff);
        GL11.glVertex3d(x + 0.0625 + xOff, y, z + 0.0625 + zOff);
        GL11.glVertex3d(x + 0.0625 + xOff, y, z + 0.0625 + zOff);
        GL11.glVertex3d(x - 0.0625 + xOff2 + 1.0D, y, z + 0.0625 + zOff);
        GL11.glVertex3d(x - 0.0625 + xOff2 + 1.0D, y, z - 0.0625 + zOff2 + 1.0D);
        GL11.glVertex3d(x + 0.0625 + xOff, y, z - 0.0625 + zOff2 + 1.0D);
        GL11.glVertex3d(x + 0.0625 + xOff, y - (0.062f * 2) + 1.0D, z + 0.0625 + zOff);
        GL11.glVertex3d(x - 0.0625 + xOff2 + 1.0D, y - (0.062f * 2) + 1.0D, z + 0.0625 + zOff);
        GL11.glVertex3d(x - 0.0625 + xOff2 + 1.0D, y - (0.062f * 2) + 1.0D, z - 0.0625 + zOff2 + 1.0D);
        GL11.glVertex3d(x + 0.0625 + xOff, y - (0.062f * 2) + 1.0D, z - 0.0625 + zOff2 + 1.0D);
        GL11.glEnd();

        GlStateManager.enableTexture2D();
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GlStateManager.popAttrib();
    }

}

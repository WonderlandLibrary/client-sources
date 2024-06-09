package host.kix.uzi.module.modules.render;

import com.darkmagician6.eventapi.SubscribeEvent;
import host.kix.uzi.events.RenderWorldEvent;
import host.kix.uzi.module.Module;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.GL11;

/**
 * Created by myche on 3/6/2017.
 */
public class ChestFinder extends Module{

    public ChestFinder() {
        super("ChestFinder", 0, Category.RENDER);
    }

    @SubscribeEvent
    public void onRender(RenderWorldEvent event) {
        for (Object obj : mc.theWorld.loadedTileEntityList) {
            if (obj instanceof TileEntity) {
                this.renderESP((TileEntity) obj);
            }
        }
    }

    public void renderESP(TileEntity chest) {

        float r = 192F / 255F;
        float g = 192F / 255F;
        float b = 32F / 255F;

        float liner = 173F / 255F;
        float lineg = 173F / 255F;
        float lineb = 29F / 255F;

        if (chest instanceof TileEntityChest && !(chest instanceof TileEntityDispenser)) {
            TileEntityChest integer = (TileEntityChest) chest;
            double posX = chest.getPos().getX() - mc.getRenderManager().renderPosX;
            double posY = chest.getPos().getY() - mc.getRenderManager().renderPosY;
            double posZ = chest.getPos().getZ() - mc.getRenderManager().renderPosZ;
            this.drawBlockESP(posX, posY, posZ, r, g, b, 0.11F, liner, lineg, lineb, 0.8F, 0.5F);
        }
    }

    public static void drawBlockESP(double x, double y, double z, float red, float green, float blue, float alpha,
                                    float lineRed, float lineGreen, float lineBlue, float lineAlpha, float lineWidth) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4f(red, green, blue, alpha);
        drawBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0));
        GL11.glLineWidth(lineWidth);
        GL11.glColor4f(lineRed, lineGreen, lineBlue, lineAlpha);
        drawLines(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0));
        drawOutlinedBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0));
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
        GL11.glColor4f(1, 1, 1, 1);
    }

    public static void drawLines(final AxisAlignedBB bb) {
        final Tessellator tessellator = Tessellator.getInstance();
        final WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        worldRenderer.startDrawing(2);
        worldRenderer.addVertex(bb.minX, bb.minY, bb.minZ);
        worldRenderer.addVertex(bb.minX, bb.maxY, bb.maxZ);
        tessellator.draw();
        worldRenderer.startDrawing(2);
        worldRenderer.addVertex(bb.maxX, bb.minY, bb.minZ);
        worldRenderer.addVertex(bb.minX, bb.maxY, bb.maxZ);
        tessellator.draw();
        worldRenderer.startDrawing(2);
        worldRenderer.addVertex(bb.maxX, bb.minY, bb.maxZ);
        worldRenderer.addVertex(bb.minX, bb.maxY, bb.maxZ);
        tessellator.draw();
        worldRenderer.startDrawing(2);
        worldRenderer.addVertex(bb.maxX, bb.minY, bb.minZ);
        worldRenderer.addVertex(bb.minX, bb.maxY, bb.minZ);
        tessellator.draw();
        worldRenderer.startDrawing(2);
        worldRenderer.addVertex(bb.maxX, bb.minY, bb.minZ);
        worldRenderer.addVertex(bb.minX, bb.minY, bb.maxZ);
        tessellator.draw();
        worldRenderer.startDrawing(2);
        worldRenderer.addVertex(bb.maxX, bb.maxY, bb.minZ);
        worldRenderer.addVertex(bb.minX, bb.maxY, bb.maxZ);
        tessellator.draw();
    }

    public static void drawOutlinedBoundingBox(AxisAlignedBB aa) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        worldRenderer.startDrawing(3);
        worldRenderer.addVertex(aa.minX, aa.minY, aa.minZ);
        worldRenderer.addVertex(aa.maxX, aa.minY, aa.minZ);
        worldRenderer.addVertex(aa.maxX, aa.minY, aa.maxZ);
        worldRenderer.addVertex(aa.minX, aa.minY, aa.maxZ);
        worldRenderer.addVertex(aa.minX, aa.minY, aa.minZ);
        tessellator.draw();
        worldRenderer.startDrawing(3);
        worldRenderer.addVertex(aa.minX, aa.maxY, aa.minZ);
        worldRenderer.addVertex(aa.maxX, aa.maxY, aa.minZ);
        worldRenderer.addVertex(aa.maxX, aa.maxY, aa.maxZ);
        worldRenderer.addVertex(aa.minX, aa.maxY, aa.maxZ);
        worldRenderer.addVertex(aa.minX, aa.maxY, aa.minZ);
        tessellator.draw();
        worldRenderer.startDrawing(1);
        worldRenderer.addVertex(aa.minX, aa.minY, aa.minZ);
        worldRenderer.addVertex(aa.minX, aa.maxY, aa.minZ);
        worldRenderer.addVertex(aa.maxX, aa.minY, aa.minZ);
        worldRenderer.addVertex(aa.maxX, aa.maxY, aa.minZ);
        worldRenderer.addVertex(aa.maxX, aa.minY, aa.maxZ);
        worldRenderer.addVertex(aa.maxX, aa.maxY, aa.maxZ);
        worldRenderer.addVertex(aa.minX, aa.minY, aa.maxZ);
        worldRenderer.addVertex(aa.minX, aa.maxY, aa.maxZ);
        tessellator.draw();
    }

    public static void drawBoundingBox(AxisAlignedBB aa) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(aa.minX, aa.minY, aa.minZ);
        worldRenderer.addVertex(aa.minX, aa.maxY, aa.minZ);
        worldRenderer.addVertex(aa.maxX, aa.minY, aa.minZ);
        worldRenderer.addVertex(aa.maxX, aa.maxY, aa.minZ);
        worldRenderer.addVertex(aa.maxX, aa.minY, aa.maxZ);
        worldRenderer.addVertex(aa.maxX, aa.maxY, aa.maxZ);
        worldRenderer.addVertex(aa.minX, aa.minY, aa.maxZ);
        worldRenderer.addVertex(aa.minX, aa.maxY, aa.maxZ);
        tessellator.draw();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(aa.maxX, aa.maxY, aa.minZ);
        worldRenderer.addVertex(aa.maxX, aa.minY, aa.minZ);
        worldRenderer.addVertex(aa.minX, aa.maxY, aa.minZ);
        worldRenderer.addVertex(aa.minX, aa.minY, aa.minZ);
        worldRenderer.addVertex(aa.minX, aa.maxY, aa.maxZ);
        worldRenderer.addVertex(aa.minX, aa.minY, aa.maxZ);
        worldRenderer.addVertex(aa.maxX, aa.maxY, aa.maxZ);
        worldRenderer.addVertex(aa.maxX, aa.minY, aa.maxZ);
        tessellator.draw();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(aa.minX, aa.maxY, aa.minZ);
        worldRenderer.addVertex(aa.maxX, aa.maxY, aa.minZ);
        worldRenderer.addVertex(aa.maxX, aa.maxY, aa.maxZ);
        worldRenderer.addVertex(aa.minX, aa.maxY, aa.maxZ);
        worldRenderer.addVertex(aa.minX, aa.maxY, aa.minZ);
        worldRenderer.addVertex(aa.minX, aa.maxY, aa.maxZ);
        worldRenderer.addVertex(aa.maxX, aa.maxY, aa.maxZ);
        worldRenderer.addVertex(aa.maxX, aa.maxY, aa.minZ);
        tessellator.draw();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(aa.minX, aa.minY, aa.minZ);
        worldRenderer.addVertex(aa.maxX, aa.minY, aa.minZ);
        worldRenderer.addVertex(aa.maxX, aa.minY, aa.maxZ);
        worldRenderer.addVertex(aa.minX, aa.minY, aa.maxZ);
        worldRenderer.addVertex(aa.minX, aa.minY, aa.minZ);
        worldRenderer.addVertex(aa.minX, aa.minY, aa.maxZ);
        worldRenderer.addVertex(aa.maxX, aa.minY, aa.maxZ);
        worldRenderer.addVertex(aa.maxX, aa.minY, aa.minZ);
        tessellator.draw();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(aa.minX, aa.minY, aa.minZ);
        worldRenderer.addVertex(aa.minX, aa.maxY, aa.minZ);
        worldRenderer.addVertex(aa.minX, aa.minY, aa.maxZ);
        worldRenderer.addVertex(aa.minX, aa.maxY, aa.maxZ);
        worldRenderer.addVertex(aa.maxX, aa.minY, aa.maxZ);
        worldRenderer.addVertex(aa.maxX, aa.maxY, aa.maxZ);
        worldRenderer.addVertex(aa.maxX, aa.minY, aa.minZ);
        worldRenderer.addVertex(aa.maxX, aa.maxY, aa.minZ);
        tessellator.draw();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(aa.minX, aa.maxY, aa.maxZ);
        worldRenderer.addVertex(aa.minX, aa.minY, aa.maxZ);
        worldRenderer.addVertex(aa.minX, aa.maxY, aa.minZ);
        worldRenderer.addVertex(aa.minX, aa.minY, aa.minZ);
        worldRenderer.addVertex(aa.maxX, aa.maxY, aa.minZ);
        worldRenderer.addVertex(aa.maxX, aa.minY, aa.minZ);
        worldRenderer.addVertex(aa.maxX, aa.maxY, aa.maxZ);
        worldRenderer.addVertex(aa.maxX, aa.minY, aa.maxZ);
        tessellator.draw();
    }

    @Override
    public void onDisable() {
        mc.entityRenderer.disableShader();
        super.onDisable();
    }
}

package zamorozka.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;

import org.lwjgl.opengl.GL11;
import zamorozka.ui.font.Fonts;

public class RenderUtil {
    public static Minecraft mc = Minecraft.getMinecraft();


    public static void drawImage(GuiIngame gui, ResourceLocation fileLocation, int x, int y, int w, int h, float fw, float fh, float u, float v) {
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3553);
        GL11.glEnable(2884);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        Minecraft.getTextureManager().bindTexture(fileLocation);
        Gui.drawModalRectWithCustomSizedTexture(x, y, u, v, w, h, fw, fh);
        GL11.glDepthMask(true);
        GL11.glEnable(2929);
        GL11.glEnable(3008);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPushMatrix();
        GL11.glPopMatrix();
    }


    public static void drawBorderedRect(float f, int i, int j, int k, float g, int l, int m) {

    }

    public static void drawText(String str, float x, float y, int color, FontRenderer fontRendererObj) {
        if (fontRendererObj != null) {
            Fonts.comfortaa18.drawStringWithShadow(str, x, y, color);
        } else {
            mc.fontRendererObj.drawStringWithShadow(str, x, y, color);
        }
    }

    public static float getTextHeight(FontRenderer fontRendererObj) {
        if (fontRendererObj != null) return Fonts.comfortaa18.getHeight();
        return mc.fontRendererObj.FONT_HEIGHT;
    }

    public static float getTextWidth(String str, FontRenderer fontRendererObj) {
        if (fontRendererObj != null) return Fonts.comfortaa18.getStringWidth(str);
        return mc.fontRendererObj.getStringWidth(str);
    }


    public static void drawBorderedRect(float f, float g, int j, int k, float g2, int l, int minValue) {

    }


    public static void renderEntityBoundingBox(Entity entity, float red, float green, float blue, float alpha) {
        RenderManager rm = Minecraft.getMinecraft().getRenderManager();
        net.minecraft.util.math.Vec3d entityPos = MathUtil.interpolateEntity(entity);

        AxisAlignedBB bb = new AxisAlignedBB(entityPos.xCoord - entity.width/2, entityPos.yCoord, entityPos.zCoord - entity.width/2,
                entityPos.xCoord + entity.width/2, entityPos.yCoord + entity.height, entityPos.zCoord + entity.width/2)
                .offset(-rm.viewerPosX, -rm.viewerPosY, -rm.viewerPosZ);
        RenderGlobal.drawSelectionBoundingBox(bb, red, green, blue, alpha);
    }


	public static void drawOutlinedBoundingBox(AxisAlignedBB aa) {
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder worldRenderer = tessellator.getBuffer();
		worldRenderer.begin(3, DefaultVertexFormats.POSITION);
		worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
		worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
		worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
		worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
		worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
		tessellator.draw();
		worldRenderer.begin(3, DefaultVertexFormats.POSITION);
		worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
		worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
		worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
		worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
		worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
		tessellator.draw();
		worldRenderer.begin(1, DefaultVertexFormats.POSITION);
		worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
		worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
		worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
		worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
		worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
		worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
		worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
		worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
		tessellator.draw();
	}
}
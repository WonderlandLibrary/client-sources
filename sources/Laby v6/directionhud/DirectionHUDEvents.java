package directionhud;

import de.labystudio.modapi.EventHandler;
import de.labystudio.modapi.Listener;
import de.labystudio.modapi.events.RenderOverlayEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class DirectionHUDEvents implements Listener
{
    private static Minecraft mc = Minecraft.getMinecraft();
    private static ScaledResolution scaledResolution;

    @EventHandler
    public void onRender(RenderOverlayEvent e)
    {
        if (DirectionHUD.optionEnable)
        {
            if (!mc.gameSettings.showDebugInfo)
            {
                if (mc.inGameHasFocus || mc.currentScreen == null || mc.currentScreen instanceof GuiChat && DirectionHUD.showWhileChat)
                {
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    scaledResolution = new ScaledResolution(mc);
                    displayHUD(mc);
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                }
            }
        }
    }

    private static void displayHUD(Minecraft mc)
    {
        int i = MathHelper.floor_double((double)(mc.thePlayer.rotationYaw * 256.0F / 360.0F) + 0.5D) & 255;
        int j = getY(1, 12);
        int k = getX(65);
        mc.getTextureManager().bindTexture(new ResourceLocation("directionhud/textures/gui/compass.png"));

        if (i < 128)
        {
            drawTexturedModalRect(k, j, i, 0, 65, 12, -100.0F);
        }
        else
        {
            drawTexturedModalRect(k, j, i - 128, 12, 65, 12, -100.0F);
        }

        mc.fontRendererObj.drawString("\u00a7" + DirectionHUD.optionMarkerColor.toLowerCase() + "|", k + 32, j + 1, 16777215);
        mc.fontRendererObj.drawString("\u00a7" + DirectionHUD.optionMarkerColor.toLowerCase() + "|\u00a7r", k + 32, j + 5, 16777215);
    }

    private static int getX(int width)
    {
        PositionMode positionmode = PositionMode.getByName(DirectionHUD.optionPositionMode);
        return positionmode.name().endsWith("CENTER") ? scaledResolution.getScaledWidth() / 2 - width / 2 : (positionmode.name().endsWith("RIGHT") ? scaledResolution.getScaledWidth() - width - 2 : DirectionHUD.optionCustomX);
    }

    private static int getY(int rowCount, int height)
    {
        PositionMode positionmode = PositionMode.getByName(DirectionHUD.optionPositionMode);
        return positionmode.name().startsWith("MIDDLE") ? scaledResolution.getScaledHeight() / 2 - rowCount * height / 2 : (positionmode != PositionMode.BOTTOMLEFT && positionmode != PositionMode.BOTTOMRIGHT ? (positionmode == PositionMode.BOTTOMCENTER ? scaledResolution.getScaledHeight() - rowCount * height - 41 : DirectionHUD.optionCustomY) : scaledResolution.getScaledHeight() - rowCount * height - 2);
    }

    public static void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height, float zLevel)
    {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos((double)(x + 0), (double)(y + height), (double)zLevel).tex((double)((float)(textureX + 0) * f), (double)((float)(textureY + height) * f1)).endVertex();
        worldrenderer.pos((double)(x + width), (double)(y + height), (double)zLevel).tex((double)((float)(textureX + width) * f), (double)((float)(textureY + height) * f1)).endVertex();
        worldrenderer.pos((double)(x + width), (double)(y + 0), (double)zLevel).tex((double)((float)(textureX + width) * f), (double)((float)(textureY + 0) * f1)).endVertex();
        worldrenderer.pos((double)(x + 0), (double)(y + 0), (double)zLevel).tex((double)((float)(textureX + 0) * f), (double)((float)(textureY + 0) * f1)).endVertex();
        tessellator.draw();
    }
}

package dev.client.tenacity.ui.altmanager.panels.components.impl;

import dev.client.tenacity.ui.altmanager.panels.components.Component;
import dev.client.tenacity.ui.altmanager.helpers.Alt;
import dev.client.tenacity.ui.altmanager.helpers.AltManagerUtils;
import dev.client.tenacity.utils.misc.HoveringUtil;
import dev.client.tenacity.utils.render.RenderUtil;
import dev.utils.animations.Animation;
import dev.utils.animations.Direction;
import dev.utils.animations.impl.EaseInOutQuad;
import dev.utils.font.FontUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class AltRect extends Component {
    public final float height = 35;
    private final Animation hoverAnimation;
    private final Animation clickAnimation;
    public Alt alt;
    public float width = 230;
    public boolean isAltSelected = false;

    public AltRect(Alt alt) {
        this.alt = alt;
        hoverAnimation = new EaseInOutQuad(170, .1);
        clickAnimation = new EaseInOutQuad(200, 1);
        clickAnimation.setDirection(Direction.BACKWARDS);
    }


    @Override
    public void initGui() {
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks, Animation initAnimation) {
        boolean hovering = HoveringUtil.isHovering(x, y, width, height, mouseX, mouseY);
        hoverAnimation.setDirection(hovering ? Direction.FORWARDS : Direction.BACKWARDS);
        hoverAnimation.setDuration(hovering ? 110 : 190);
        GL11.glPushMatrix();
        // translates based on the mouseX and mouseY and scales on the animationMap
        GL11.glTranslatef((float) mouseX, (float) mouseY, 1);
        float scale = (float) (1 + hoverAnimation.getOutput());
        GL11.glScalef(scale, scale, 1);
        GL11.glTranslatef(-(float) mouseX, -(float) mouseY, 1);

        clickAnimation.setDirection(isAltSelected ? Direction.FORWARDS : Direction.BACKWARDS);
        Gui.drawRect2(x, y, width, height, interpolateRectColor(clickAnimation));
        RenderUtil.resetColor();
        drawAltHead(x + 4, y + 3.5f, 27);
        String usernameText = "";
        if (alt.username != null)
            usernameText = FontUtil.tenacityFont16.trimStringToWidth(alt.username, (int) (width - 23));

        RenderUtil.resetColor();
        FontUtil.tenacityFont18.drawSmoothString(usernameText, x + 35, y + 6, -1);

        if (alt.altState != null) {
            String text = "";
            int color = new Color(255, 30, 30).getRGB();
            switch (alt.altState) {
                case LOGIN_SUCCESS:
                    color = new Color(20, 250, 90).getRGB();
                    text = FontUtil.CHECKMARK;
                    break;
                case LOGIN_FAIL:
                    text = FontUtil.XMARK;
                    break;
            }
            RenderUtil.resetColor();
            FontUtil.iconFont16.drawSmoothString(text, x + 35, y + 18, color);
            RenderUtil.resetColor();
        }

        GL11.glPopMatrix();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button, AltManagerUtils altManagerUtils) {
        hovering = HoveringUtil.isHovering(x, y, width, height, mouseX, mouseY);
    }

    public void drawAltHead(float x, float y, float size) {
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        //Downloads the skins of the alts
        if (alt.username != null) {
            ThreadDownloadImageData thread = getHead(AbstractClientPlayer.getLocationSkin(alt.username), alt.uuid == null ? "" : alt.uuid);
            if (Alt.skinChecks < 5) {
                try {
                    thread.loadTexture(mc.getResourceManager());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Alt.skinChecks++;
            }
            alt.checkedSkin = thread.imageFound != null && thread.imageFound;
        }
        mc.getTextureManager().bindTexture(alt.username == null ? new ResourceLocation("Tenacity/steve.png") : AbstractClientPlayer.getLocationSkin(alt.username));
        if (alt.email == null) {
            Gui.drawModalRectWithCustomSizedTexture(x, y, 0, 0, size, size, size, size);
        } else {
            float textureWidth = size * 1.63f;
            Gui.drawModalRectWithCustomSizedTexture(x, y, 0, 0, size, size, textureWidth, textureWidth);
        }
    }

    public static ThreadDownloadImageData getHead(ResourceLocation resourceLocationIn, String username) {
        Object var3 = Minecraft.getMinecraft().getTextureManager().getTexture(resourceLocationIn);

        if (var3 == null) {
            var3 = new ThreadDownloadImageData(null, String.format("https://crafatar.com/avatars/%s", StringUtils.stripControlCodes(username)), DefaultPlayerSkin.getDefaultSkin(EntityPlayer.getOfflineUUID(username)), new ImageBufferDownload());

            Minecraft.getMinecraft().getTextureManager().loadTexture(resourceLocationIn, (ITextureObject) var3);
        }

        return (ThreadDownloadImageData) var3;
    }
}

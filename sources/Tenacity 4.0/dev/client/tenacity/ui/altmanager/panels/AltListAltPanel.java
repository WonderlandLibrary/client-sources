package dev.client.tenacity.ui.altmanager.panels;

import dev.client.tenacity.ui.altmanager.AltPanel;
import dev.client.tenacity.ui.altmanager.helpers.Alt;
import dev.client.tenacity.ui.altmanager.helpers.AltManagerUtils;
import dev.client.tenacity.ui.altmanager.panels.components.Component;
import dev.client.tenacity.ui.altmanager.panels.components.impl.AltRect;
import dev.client.tenacity.utils.misc.HoveringUtil;
import dev.client.tenacity.utils.objects.Scroll;
import dev.utils.animations.Animation;
import dev.utils.font.FontUtil;
import dev.utils.render.StencilUtil;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AltListAltPanel implements AltPanel {

    private List<Alt> displayAlts;
    ArrayList<Component> altObjects = new ArrayList<>();
    Scroll scroll = new Scroll();
    boolean clickedPass = false;
    private Alt selectedAlt;

    @Override
    public void initGui() {
        displayAlts = new ArrayList<>(AltManagerUtils.alts);
        Collections.reverse(displayAlts);
        altObjects.clear();
        displayAlts.forEach(alt -> altObjects.add(new AltRect(alt)));
        clickedPass = false;
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks, Animation initAnimation) {
        ScaledResolution sr = new ScaledResolution(mc);
        float bigRectX = 20;
        float width = (sr.getScaledWidth()) - (bigRectX + 200);
        float xAnimation = (float) (bigRectX + ((width + 30) * Math.abs(initAnimation.getOutput() - 1)));
        float height = (sr.getScaledHeight()) - 60;
        Gui.drawRect2(xAnimation, 40, width, height, rectColorInt);

        scroll.onScroll(55);
        FontUtil.tenacityBoldFont32.drawString("WIP", sr.getScaledWidth() - 260, 50, Color.RED.getRGB());

        if (selectedAlt != null) {
            int startY = 50, startX = sr.getScaledWidth() - 200;
            drawAltHead(selectedAlt, startX + 20, startY, 75);
            FontUtil.tenacityFont24.drawString("Email: " + (selectedAlt.email != null
                    ? selectedAlt.email : "null"), startX + 10, startY + 40, -1);
            int color = clickedPass ? Color.green.getRGB() : Color.red.getRGB();
            FontUtil.tenacityFont24.drawString("§fPassword: " + (clickedPass ? (selectedAlt.password != null ? selectedAlt.password : "null") : StringUtils.repeat('*', selectedAlt.password.length())), startX + 10, startY + 60, color);
        }

        int count = 0;
        StencilUtil.initStencilToWrite();
        Gui.drawRect2(xAnimation, 40, width, (sr.getScaledHeight()) - 60, -1);
        StencilUtil.readStencilBuffer(1);
        for (Component altRect : altObjects) {
            AltRect altRectObject = ((AltRect) altRect);
            altRect.x = xAnimation + 5;
            altRectObject.width = width - 10;
            altRect.y = (45 + scroll.getScroll()) + (count * 40);
            if (selectedAlt != null) {
                altRectObject.isAltSelected = selectedAlt == altRectObject.alt;
            }
            altRect.drawScreen(mouseX, mouseY, partialTicks, initAnimation);
            count++;
        }
        scroll.setMaxScroll(count * 40 - height + 5);

        StencilUtil.uninitStencilBuffer();
        float scrollBarHeight = height * Math.min(1, height / (count * 40));
//        Draw.drawRoundedRect(xAnimation + width - 5, -scroll.getScroll() / scroll.getMaxScroll() * (height - scrollBarHeight) + 40, 3, scrollBarHeight, 1.5f, rectColorInt);//new Color(255, 255, 255, 120).getRGB()
        //Gui.drawRect2(xAnimation + width - 2, -scroll.getScroll() / scroll.getMaxScroll() * (height - scrollBarHeight) + 40, 3, scrollBarHeight, rectColorInt);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button, AltManagerUtils altManagerUtils) {
        ScaledResolution sr = new ScaledResolution(mc);
        float bigRectX = 300;
        float width = (sr.getScaledWidth()) - (bigRectX + 30);
        float size = width / 4f - 10;
        if (HoveringUtil.isHovering(bigRectX + 10, 125 + size, (float) FontUtil.tenacityFont24.getStringWidth("§fPassword §r(click to show)§f:"), FontUtil.tenacityFont24.getHeight(), mouseX, mouseY)) {
            clickedPass = !clickedPass;
        }
        if (button != 0) return;
        altObjects.forEach(altObject -> {
            altObject.mouseClicked(mouseX, mouseY, button, altManagerUtils);
            if (altObject.hovering && selectedAlt == ((AltRect) altObject).alt) {
                selectedAlt.loginAsync(selectedAlt.altType == Alt.AltType.MICROSOFT);
            }
            if (altObject.hovering) selectedAlt = ((AltRect) altObject).alt;
        });
    }

    public void drawAltHead(Alt alt, float x, float y, float size) {
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.color(1, 1, 1, 1);
        //Downloads the skins of the alts
        if (alt.username != null) {
            ThreadDownloadImageData thread = AltRect.getHead(AbstractClientPlayer.getLocationSkin(alt.username), alt.uuid == null ? "" : alt.uuid);
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
            float textureDimensions = size * 1.63f;
            Gui.drawModalRectWithCustomSizedTexture(x, y, 0, 0, size, size, textureDimensions, textureDimensions);
        }
    }

    public void reInitAltList() {
        displayAlts.clear();
        altObjects.clear();
        displayAlts = new ArrayList<>(AltManagerUtils.alts);
        Collections.reverse(displayAlts);
        displayAlts.forEach(alt -> altObjects.add(new AltRect(alt)));
    }
}

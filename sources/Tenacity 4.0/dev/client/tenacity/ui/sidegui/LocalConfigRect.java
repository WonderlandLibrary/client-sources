package dev.client.tenacity.ui.sidegui;

import dev.client.tenacity.Tenacity;
import dev.client.tenacity.config.LocalConfig;
import dev.client.tenacity.ui.notifications.NotificationManager;
import dev.client.tenacity.ui.notifications.NotificationType;
import dev.utils.animations.Animation;
import dev.utils.animations.Direction;
import dev.utils.animations.impl.DecelerateAnimation;
import dev.utils.font.FontUtil;
import dev.client.tenacity.utils.misc.HoveringUtil;
import dev.client.tenacity.utils.render.ColorUtil;
import dev.client.tenacity.utils.render.RenderUtil;
import dev.client.tenacity.utils.render.RoundedUtil;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LocalConfigRect extends GuiPanel {

    private final LocalConfig config;
    public boolean reinit;
    float x, y, width, height;
    private Animation hoverAnimation;
    private Animation hoverDeleteAnimation;
    private Animation hoverLoadAnimation;
    private Animation hoverUpdateAnimation;
    private BasicFileAttributes bfa = null;

    public LocalConfigRect(LocalConfig config) {
        this.config = config;
    }

    @Override
    public void initGui() {
        hoverAnimation = new DecelerateAnimation(250, 1);
        hoverDeleteAnimation = new DecelerateAnimation(250, 1);
        hoverLoadAnimation = new DecelerateAnimation(250, 1);
        hoverUpdateAnimation = new DecelerateAnimation(250, 1);
        try {
            bfa = Files.readAttributes(config.getFile().toPath(), BasicFileAttributes.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getCurrentTimeStamp(Date date) {
        SimpleDateFormat sdfDate = new SimpleDateFormat("MM/dd/yyyy");
        return sdfDate.format(date);
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks, int alpha) {
        Color rectColor = new Color(37, 37, 37);
        Color textColor = new Color(255, 255, 255, alpha);
        boolean hovered = HoveringUtil.isHovering(x, y, width, height, mouseX, mouseY);
        hoverAnimation.setDirection(hovered ? Direction.FORWARDS : Direction.BACKWARDS);
        RoundedUtil.drawRound(x, y, width, height, 6, ColorUtil.interpolateColorC(rectColor, ColorUtil.brighter(rectColor, .8f), (float) hoverAnimation.getOutput()));

        FontUtil.tenacityBoldFont26.drawString(config.getName(), x + 5, y + 5, textColor.getRGB());


        if (bfa != null) {
            String date = "§fLast modified: §r" + getCurrentTimeStamp(new Date(bfa.lastModifiedTime().toMillis()));
            FontUtil.tenacityFont18.drawString(date, x + 5, y + 25, new Color(255, 40, 100, alpha).getRGB());
        }

        float delX = x + width - 58;
        float delY = y + height - 15;
        boolean hoveringDelete = HoveringUtil.isHovering(delX, delY - 4, (float) FontUtil.iconFont26.getStringWidth(FontUtil.TRASH), FontUtil.iconFont26.getHeight() + 5,
                mouseX, mouseY);
        hoverDeleteAnimation.setDirection(hoveringDelete ? Direction.FORWARDS : Direction.BACKWARDS);
        FontUtil.iconFont26.drawString(FontUtil.TRASH, delX, delY, ColorUtil.interpolateColor(new Color(-1),
                new Color(220, 40, 40), (float) hoverDeleteAnimation.getOutput()));

        if (hoveringDelete || !hoverDeleteAnimation.isDone()) {
            int deleteColor = ColorUtil.interpolateColor(new Color(220, 40, 40, 0),
                    new Color(220, 40, 40), (float) hoverDeleteAnimation.getOutput());

            GlStateManager.color(1, 1, 1, 1);
            FontUtil.tenacityBoldFont18.drawCenteredString("Delete Config", mouseX, mouseY - 15, deleteColor);
        }


        float updateX = x + width - 38;
        float updateY = delY;
        boolean hoveringUpdate = HoveringUtil.isHovering(updateX, updateY - 4,
                (float) FontUtil.iconFont26.getStringWidth(FontUtil.SAVE), FontUtil.iconFont26.getHeight() + 5, mouseX, mouseY);
        hoverUpdateAnimation.setDirection(hoveringUpdate ? Direction.FORWARDS : Direction.BACKWARDS);

        RenderUtil.resetColor();
        FontUtil.iconFont26.drawString(FontUtil.SAVE, updateX, updateY,
                ColorUtil.interpolateColor(textColor, Tenacity.INSTANCE.getClientColor(), (float) hoverUpdateAnimation.getOutput()));

        if (hoveringUpdate || !hoverUpdateAnimation.isDone()) {
            int updateColor = ColorUtil.interpolateColor(ColorUtil.applyOpacity(Tenacity.INSTANCE.getClientColor(), 0),
                    Tenacity.INSTANCE.getClientColor(), (float) hoverUpdateAnimation.getOutput());

            GlStateManager.color(1, 1, 1, 1);
            FontUtil.tenacityBoldFont18.drawCenteredString("Update Config", mouseX, mouseY - 15, updateColor);
        }

        float loadX = x + width - 18;
        float loadY = delY + 1;
        boolean hoveringLoad = HoveringUtil.isHovering(loadX, loadY - 4,
                (float) FontUtil.iconFont26.getStringWidth(FontUtil.LOAD), FontUtil.iconFont26.getHeight() + 5, mouseX, mouseY);
        hoverLoadAnimation.setDirection(hoveringLoad ? Direction.FORWARDS : Direction.BACKWARDS);

        FontUtil.iconFont26.drawString(FontUtil.LOAD, loadX, loadY,
                ColorUtil.interpolateColor(textColor, Tenacity.INSTANCE.getAlternateClientColor(), (float) hoverLoadAnimation.getOutput()));

        if (hoveringLoad || !hoverLoadAnimation.isDone()) {
            int loadColor = ColorUtil.interpolateColor(ColorUtil.applyOpacity(Tenacity.INSTANCE.getAlternateClientColor(), 0),
                    Tenacity.INSTANCE.getAlternateClientColor(), (float) hoverLoadAnimation.getOutput());

            GlStateManager.color(1, 1, 1, 1);
            FontUtil.tenacityBoldFont18.drawCenteredString("Load Config", mouseX - 10, mouseY - 15, loadColor);
        }

    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        float delX = x + width - 58;
        float delY = y + height - 15;
        boolean hoveringDelete = HoveringUtil.isHovering(delX, delY - 4, (float) FontUtil.iconFont26.getStringWidth(FontUtil.TRASH), FontUtil.iconFont26.getHeight() + 5,
                mouseX, mouseY);

        float updateX = x + width - 38;
        float updateY = delY;
        boolean hoveringUpdate = HoveringUtil.isHovering(updateX, updateY - 4,
                (float) FontUtil.iconFont26.getStringWidth(FontUtil.SAVE), FontUtil.iconFont26.getHeight() + 5, mouseX, mouseY);

        float loadX = x + width - 18;
        float loadY = delY + 1;
        boolean hoveringLoad = HoveringUtil.isHovering(loadX, loadY - 4,
                (float) FontUtil.iconFont26.getStringWidth(FontUtil.LOAD), FontUtil.iconFont26.getHeight() + 5, mouseX, mouseY);


        if (button == 0) {
            if (hoveringDelete) {
                if (Tenacity.INSTANCE.getConfigManager().delete(config.getName())) {
                    NotificationManager.post(NotificationType.SUCCESS, "Config deleted", "Deleted the \"" + config.getName() + "\" config");
                    reinit = true;
                } else {
                    NotificationManager.post(NotificationType.WARNING, "Config error", "Failed to delete the \"" + config.getName() + "\" config");
                }
            }
            if (hoveringUpdate) {
                if (Tenacity.INSTANCE.getConfigManager().saveConfig(config.getName())) {
                    NotificationManager.post(NotificationType.SUCCESS, "Config updated", "Updated the \"" + config.getName() + "\" config");
                } else {
                    NotificationManager.post(NotificationType.WARNING, "Config error", "Failed to save the \"" + config.getName() + "\" config");
                }
            }
            if (hoveringLoad) {
                if (Tenacity.INSTANCE.getConfigManager().loadConfig(Tenacity.INSTANCE.getConfigManager().readConfigData(config.getFile().toPath()))) {
                    NotificationManager.post(NotificationType.SUCCESS, "Config loaded", "The config \"" + config.getName() + "\" was loaded!");
                } else {
                    NotificationManager.post(NotificationType.WARNING, "Config error", "The config \"" + config.getName() + "\" failed to load!");
                }
            }
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int button) {

    }

}

package dev.client.tenacity.ui.sidegui;

import com.google.gson.JsonObject;
import dev.client.tenacity.Tenacity;
import dev.client.tenacity.config.OnlineConfig;
import dev.client.tenacity.module.impl.render.ClickGuiMod;
import dev.client.tenacity.ui.notifications.NotificationManager;
import dev.client.tenacity.ui.notifications.NotificationType;
import dev.utils.animations.Animation;
import dev.utils.animations.Direction;
import dev.utils.animations.impl.DecelerateAnimation;
import dev.utils.font.FontUtil;
import dev.client.tenacity.utils.misc.CloudUtils;
import dev.client.tenacity.utils.misc.HoveringUtil;
import dev.client.tenacity.utils.render.ColorUtil;
import dev.client.tenacity.utils.render.RenderUtil;
import dev.client.tenacity.utils.render.RoundedUtil;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;

public class OnlineConfigRect extends GuiPanel {

    public final OnlineConfig onlineConfig;

    float x, y, width, height;
    private Animation hoverAnimation;
    private Animation hoverDownloadAnimation;
    private Animation hoverNameAnimation;

    public OnlineConfigRect(OnlineConfig onlineConfig) {
        this.onlineConfig = onlineConfig;
    }

    @Override
    public void initGui() {
        hoverAnimation = new DecelerateAnimation(250, 1);
        hoverDownloadAnimation = new DecelerateAnimation(250, 1);
        hoverNameAnimation = new DecelerateAnimation(250, 1);
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks, int alpha) {
        Color clickGuiColor = ClickGuiMod.color.getColor();
        Color rectColor = new Color(37, 37, 37, alpha);
        Color textColor = new Color(255, 255, 255, alpha);

        boolean hovering = HoveringUtil.isHovering(x, y, width, height, mouseX, mouseY);
        hoverAnimation.setDirection(hovering ? Direction.FORWARDS : Direction.BACKWARDS);


        if (onlineConfig.isVerified()) {
            RoundedUtil.drawRoundOutline(x, y, width, height, 6, 1,
                    ColorUtil.interpolateColorC(rectColor, ColorUtil.brighter(rectColor, .9f), (float) hoverAnimation.getOutput()),
                    clickGuiColor);
        } else {
            RoundedUtil.drawRound(x, y, width, height, 6,
                    ColorUtil.interpolateColorC(rectColor, ColorUtil.brighter(rectColor, .9f), (float) hoverAnimation.getOutput()));
        }


        int nameColor = onlineConfig.isVerified() ? clickGuiColor.getRGB() :
                ColorUtil.interpolateColor(textColor, clickGuiColor, Math.min(10, onlineConfig.getVotes()) / 10f);

        FontUtil.tenacityBoldFont26.drawString(onlineConfig.getName(), x + 5, y + 5, nameColor);

        boolean hoveringName = HoveringUtil.isHovering(x + 5, y + 5,
                (float) FontUtil.tenacityBoldFont26.getStringWidth(onlineConfig.getName()),
                FontUtil.tenacityBoldFont26.getHeight(), mouseX, mouseY);

        hoverNameAnimation.setDirection(hoveringName ? Direction.FORWARDS : Direction.BACKWARDS);


        if (hoveringName || !hoverNameAnimation.isDone()) {
            RenderUtil.resetColor();
            FontUtil.tenacityBoldFont18.drawCenteredString(onlineConfig.isVerified() ? "Admin Verified" : "Not Verified",
                    mouseX, mouseY - 15, ColorUtil.applyOpacity(
                            onlineConfig.isVerified() ? new Color(0, 230, 90) : new Color(255, 40, 100), (float) hoverNameAnimation.getOutput()).getRGB());
        }


        FontUtil.tenacityFont18.wrapText(onlineConfig.getDescription(), x + 5, y + 23,
                ColorUtil.applyOpacity(new Color(145, 145, 145), alpha / 255f).getRGB(), width, 4);


        FontUtil.tenacityFont18.drawString("§fCreator: §r" + onlineConfig.getAuthor(), x + 5, y + height - 12,
                ColorUtil.applyOpacity(clickGuiColor, alpha / 255f).getRGB());

        FontUtil.tenacityFont18.drawString("§fClient Version: §r" + onlineConfig.getClientVersion(), x + 5, y + height - 24,
                ColorUtil.applyOpacity(clickGuiColor, alpha / 255f).getRGB());

        String info = "§fVotes: §r" + onlineConfig.getVotes();
        RenderUtil.resetColor();
        FontUtil.tenacityFont18.drawString(info, x + 5, y + height - 36,
                ColorUtil.applyOpacity(clickGuiColor, alpha / 255f).getRGB());


        boolean hoveringDownload = HoveringUtil.isHovering(x + width - 18, y + height - 13,
                (float) FontUtil.iconFont26.getStringWidth(FontUtil.LOAD), FontUtil.iconFont26.getHeight(), mouseX, mouseY);

        hoverDownloadAnimation.setDirection(hoveringDownload ? Direction.FORWARDS : Direction.BACKWARDS);

        int downloadColor = ColorUtil.interpolateColor(textColor, clickGuiColor, (float) hoverDownloadAnimation.getOutput());
        RenderUtil.resetColor();
        FontUtil.iconFont26.drawString(FontUtil.LOAD, x + width - 18, y + height - 13, downloadColor);

        if (hoveringDownload || !hoverDownloadAnimation.isDone()) {
            int downloadColorText = ColorUtil.interpolateColor(ColorUtil.applyOpacity(clickGuiColor, 0),
                    clickGuiColor, (float) hoverDownloadAnimation.getOutput());

            GlStateManager.color(1, 1, 1, 1);
            FontUtil.tenacityBoldFont18.drawCenteredString("Load Config", mouseX - 10, mouseY - 15, downloadColorText);
        }

    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        boolean hoveringDownload = HoveringUtil.isHovering(x + width - 18, y + height - 13,
                (float) FontUtil.iconFont26.getStringWidth(FontUtil.LOAD), FontUtil.iconFont26.getHeight(), mouseX, mouseY);

        if (hoveringDownload && button == 0) {
            JsonObject object = CloudUtils.getData(onlineConfig.getShareCode());

            if (object == null) {
                NotificationManager.post(NotificationType.WARNING, "Error", "The online config was invalid!");
                return;
            }
            String config = object.get("body").getAsString();

            if (Tenacity.INSTANCE.getConfigManager().loadConfig(config)) {
                NotificationManager.post(NotificationType.SUCCESS, "Online config loaded", "The config \"" + onlineConfig.getName() + "\" was loaded!");
            } else {
                NotificationManager.post(NotificationType.WARNING, "Online config error", "The config \"" + onlineConfig.getName() + "\" failed to load!");
            }

        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int button) {

    }
}

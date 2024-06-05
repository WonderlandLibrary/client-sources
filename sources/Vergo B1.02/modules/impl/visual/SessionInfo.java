package xyz.vergoclient.modules.impl.visual;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import xyz.vergoclient.Vergo;
import xyz.vergoclient.event.Event;
import xyz.vergoclient.event.impl.EventRenderGUI;
import xyz.vergoclient.modules.Module;
import xyz.vergoclient.modules.OnEventInterface;
import xyz.vergoclient.settings.BooleanSetting;
import xyz.vergoclient.settings.ModeSetting;
import xyz.vergoclient.settings.NumberSetting;
import xyz.vergoclient.ui.fonts.FontUtil;
import xyz.vergoclient.ui.fonts.JelloFontRenderer;
import xyz.vergoclient.util.Gl.BlurUtil;
import xyz.vergoclient.util.main.ColorUtils;
import xyz.vergoclient.util.main.RenderUtils;

import java.awt.*;
import java.util.Arrays;

import static org.lwjgl.opengl.GL11.*;
import static xyz.vergoclient.tasks.backgroundTasks.sessionInfo.SessionInfo.*;

public class SessionInfo extends Module implements OnEventInterface {

    public SessionInfo() {
        super("SessionInfo", Module.Category.VISUAL);
    }

    public ModeSetting colorSetting = new ModeSetting("Colour Theme", "Clear", "Clear", "VergoColour");

    public BooleanSetting resetStats = new BooleanSetting("Reset Stats", false);

    public NumberSetting xPos = new NumberSetting("X Pos", 3, 3, 1000, 1),
                         yPos = new NumberSetting("Y Pos", 50, 5, 800, 1);

    @Override
    public void loadSettings() {
        colorSetting.modes.addAll(Arrays.asList("Clear", "VergoColour"));

        addSettings(xPos, yPos, colorSetting, resetStats);
    }

    @Override
    public void onEnable() {
        if(resetStats.isEnabled()) {
            resetStats.setEnabled(false);
        }
    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onEvent(Event e) {

        if(resetStats.isEnabled()) {
            resetSess();
            resetStats.setEnabled(false);
        }

        if(e instanceof EventRenderGUI) {
            if(e.isPre()) {
                GlStateManager.pushMatrix();


                int[] playTime = getPlayTime();

                JelloFontRenderer fr = FontUtil.kanitBig;
                JelloFontRenderer fr2 = FontUtil.kanitNormal;

                // Location Data
                int boxX = xPos.getValueAsInt();
                int boxY = yPos.getValueAsInt();
                int boxWidth = 135;
                int boxHeight = 90;
                float boxCorners = 3f;

                // Render the session info square
                final int startColour = ColorUtils.fadeBetween(new Color(255, 0, 115, 100).getRGB(), new Color(190, 0, 115, 100).getRGB(), 10000);

                if(Vergo.config.modSessionInfo.colorSetting.is("VergoColour")) {
                    RenderUtils.drawAlphaRoundedRect(boxX, boxY, boxWidth, boxHeight, boxCorners, new Color(startColour));
                } else if(Vergo.config.modSessionInfo.colorSetting.is("Clear")) {
                    RenderUtils.drawAlphaRoundedRect(boxX, boxY, boxWidth, boxHeight, boxCorners, new Color(60, 60, 60, 100).getRGB());
                    BlurUtil.blurAreaRounded(boxX, boxY, boxWidth, boxHeight, boxCorners);
                }

                glDisable(GL_ALPHA_TEST);
                Gui.drawGradientRect2(boxX, boxY + 15, boxWidth, 5, ColorUtils.applyOpacity(Color.BLACK, .2f).getRGB(), ColorUtils.applyOpacity(Color.BLACK, 0).getRGB());
                glEnable(GL_ALPHA_TEST);

                String playtimeString = playTime[0] + "hr " + playTime[1] + "m " + playTime[2] + "s";

                String sessionInfoStr = "Session Info";

                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);

                // Session Info Text
                fr.drawString(sessionInfoStr, boxX + (boxWidth / 2) - (fr.getStringWidth(sessionInfoStr) / 2), boxY + 0.5f, -1);

                // Playtime Text
                fr2.drawString("Current Session:", boxX + (boxWidth / 2.7f) - (fr.getStringWidth("Current Session:") / 2), boxY + 22f, -1);
                fr2.drawString(playtimeString, boxX + 46 + (fr2.getStringWidth("Current Session:") / 2), boxY + 22f, -1);

                // Win Count
                fr2.drawString("Wins: ", boxX + 8, boxY + 35f, -1);
                fr2.drawString(winCount + "", boxX + 20 + (fr2.getStringWidth("Wins: ") / 2), boxY + 35f, -1);

                // Kill Count
                fr2.drawString("Kills: ", boxX + 8, boxY + 48f, -1);
                fr2.drawString(killCount + "", boxX + 17 + (fr2.getStringWidth("Kills: ") / 2), boxY + 48f, -1);

                // Death Count
                fr2.drawString("Deaths: ", boxX + 8, boxY + 61f, -1);
                fr2.drawString(deathCount + "", boxX + 24 + (fr2.getStringWidth("Deaths: ") / 2), boxY + 61f, -1);

                // Games Played
                fr2.drawString("Games Played: ", boxX + 8, boxY + 74f, -1);
                fr2.drawString(gamesPlayed + "", boxX + 39 + (fr2.getStringWidth("Games Played: ") / 2), boxY + 74f, -1);

                GlStateManager.popMatrix();
            }
        }
    }

}

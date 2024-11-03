package net.silentclient.client.mods.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.EnumChatFormatting;
import net.silentclient.client.Client;
import net.silentclient.client.mods.Mod;
import net.silentclient.client.mods.ModCategory;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class ScoreboardMod extends Mod {
	public ScoreboardMod() {
		super("Scoreboard", ModCategory.MODS, "silentclient/icons/mods/scoreboard.png", true);
	}
	
	@Override
	public void setup() {
		this.addSliderSetting("Scale", this, 1, 0.3, 2, false);

		this.addBooleanSetting("Header Background", this, true);
		this.addColorSetting("Header Background Color", this, new Color(0, 0, 0), 96);

		this.addBooleanSetting("Background", this, true);
		this.addColorSetting("Background Color", this, new Color(0, 0, 0), 80);

		this.addBooleanSetting("Font Shadow", this, true);
		this.addBooleanSetting("Numbers", this, false);
	}

	public static void drawScoreboard(ScoreObjective sidebar, ScaledResolution res) {
		if (Client.getInstance().getModInstances().getModByClass(ScoreboardMod.class).isEnabled()) {
			boolean redNumbers = Client.getInstance().getSettingsManager().getSettingByClass(ScoreboardMod.class, "Numbers").getValBoolean();
			boolean background = Client.getInstance().getSettingsManager().getSettingByClass(ScoreboardMod.class, "Background").getValBoolean();
			Color bgColor = Client.getInstance().getSettingsManager().getSettingByClass(ScoreboardMod.class, "Background Color").getValColor();
			boolean headerBackground = Client.getInstance().getSettingsManager().getSettingByClass(ScoreboardMod.class, "Header Background").getValBoolean();
			Color headerBgColor = Client.getInstance().getSettingsManager().getSettingByClass(ScoreboardMod.class, "Header Background Color").getValColor();
			boolean fontShadow = Client.getInstance().getSettingsManager().getSettingByClass(ScoreboardMod.class, "Font Shadow").getValBoolean();
			float scale =  Client.getInstance().getSettingsManager().getSettingByClass(ScoreboardMod.class, "Scale").getValFloat();

			FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
			Scoreboard scoreboard = sidebar.getScoreboard();
			ArrayList<Score> scores = new ArrayList<Score>();

			int sidebarWidth = fr.getStringWidth(sidebar.getDisplayName());
			Iterator<?> scalePointX = scoreboard.getSortedScores(sidebar).iterator();

			while (scalePointX.hasNext()) {
				Score scalePointY = (Score) scalePointX.next();
				String mscale = scalePointY.getPlayerName();

				if (scores.size() < 15 && mscale != null && !mscale.startsWith("#")) {
					ScorePlayerTeam index = scoreboard.getPlayersTeam(mscale);
					String s2 = redNumbers ? ": " + EnumChatFormatting.RED + scalePointY.getScorePoints() : "";
					String score = ScorePlayerTeam.formatPlayerName(index, mscale) + s2;

					sidebarWidth = Math.max(sidebarWidth, fr.getStringWidth(score));
					scores.add(scalePointY);
				}
			}

			int sidebarHeight = scores.size() * fr.FONT_HEIGHT;
			int sidebarX = res.getScaledWidth() - sidebarWidth - 3 + 1;
			int sidebarY = res.getScaledHeight() / 2 + sidebarHeight / 3 + 0;
			int i = sidebarX + sidebarWidth;
			int j = sidebarY - sidebarHeight / 2;
			float f = scale - 1.0F;

			GL11.glTranslatef((float) (-i) * f, (float) (-j) * f, 0.0F);
			GL11.glScalef(scale, scale, 1.0F);
			int k = 0;
			Iterator<Score> iterator = scores.iterator();

			while (iterator.hasNext()) {
				Score score = (Score) iterator.next();

				++k;
				ScorePlayerTeam team = scoreboard.getPlayersTeam(score.getPlayerName());
				String s1 = ScorePlayerTeam.formatPlayerName(team, score.getPlayerName());
				String s21 = EnumChatFormatting.RED + "" + score.getScorePoints();

				if (!redNumbers) {
					s21 = "";
				}

				int scoreX = sidebarX + sidebarWidth + 1;
				int scoreY = sidebarY - k * fr.FONT_HEIGHT;

				if(background) {
					Gui.drawRect(sidebarX - 2, scoreY, scoreX, scoreY + fr.FONT_HEIGHT, bgColor.getRGB());
				}
				fr.drawString(s1, sidebarX, scoreY, -1, fontShadow);
				fr.drawString(s21, scoreX - fr.getStringWidth(s21), scoreY, -1, fontShadow);
				if (k == scores.size()) {
					String s3 = sidebar.getDisplayName();

					if(headerBackground) {
						Gui.drawRect(sidebarX - 2, scoreY - fr.FONT_HEIGHT - 1, scoreX, scoreY - 1, headerBgColor.getRGB());
					}
					if(background) {
						Gui.drawRect(sidebarX - 2, scoreY - 1, scoreX, scoreY, bgColor.getRGB());
					}
					fr.drawString(s3, sidebarX + (sidebarWidth - fr.getStringWidth(s3)) / 2, scoreY - fr.FONT_HEIGHT, -1, fontShadow);
				}
			}

			GL11.glScalef(1.0F / scale, 1.0F / scale, 1.0F);
			GL11.glTranslatef((float) i * f, (float) j * f, 0.0F);
		}
	}
}

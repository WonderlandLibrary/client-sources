package net.silentclient.client.mods.hud;

import net.minecraft.util.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.silentclient.client.Client;
import net.silentclient.client.gui.hud.ScreenPosition;
import net.silentclient.client.gui.lite.clickgui.utils.RenderUtils;
import net.silentclient.client.gui.util.RenderUtil;
import net.silentclient.client.mods.CustomFontRenderer;
import net.silentclient.client.mods.ModCategory;
import net.silentclient.client.mods.ModDraggable;

import java.awt.*;

public class CoordinatesMod extends ModDraggable {
	int maxWidth = 0;
	
	public CoordinatesMod() {
		super("Coordinates", ModCategory.MODS, "silentclient/icons/mods/coordinates.png");
	}
	
	@Override
	public void setup() {
		super.setup();
		this.addBooleanSetting("Background", this, true);
		this.addColorSetting("Background Color", this, new Color(0, 0, 0), 127);
		this.addColorSetting("Color", this, new Color(255, 255, 255));
		this.addBooleanSetting("Font Shadow", this, true);
		this.addBooleanSetting("Brackets", this, false);
		this.addBooleanSetting("Fancy Font", this, false);
		this.addBooleanSetting("Rounded Corners", this, false);
		this.addSliderSetting("Rounding Strength", this, 3, 1, 6, true);
		this.addBooleanSetting("Show Biome", this, true);
	}

	@Override
	public int getWidth() {
		return maxWidth;
	}

	@Override
	public int getHeight() {
		boolean showBiome = Client.getInstance().getSettingsManager().getSettingByName(this, "Show Biome").getValBoolean();
		
		return font.FONT_HEIGHT * (showBiome ? 5 : 4);
	}

	@Override
	public boolean render(ScreenPosition pos) {
		boolean fancyFont = Client.getInstance().getSettingsManager().getSettingByName(this, "Fancy Font").getValBoolean();
		CustomFontRenderer font = new CustomFontRenderer();
		
		font.setRenderMode(fancyFont ? CustomFontRenderer.RenderMode.CUSTOM : CustomFontRenderer.RenderMode.DEFAULT);
		boolean roundedCorners = Client.getInstance().getSettingsManager().getSettingByName(this, "Rounded Corners").getValBoolean();
		boolean background = Client.getInstance().getSettingsManager().getSettingByName(this, "Background").getValBoolean();
		Color backgroundColor = Client.getInstance().getSettingsManager().getSettingByName(this, "Background Color").getValColor();
		boolean fontShadow = Client.getInstance().getSettingsManager().getSettingByName(this, "Font Shadow").getValBoolean();
		Color color = Client.getInstance().getSettingsManager().getSettingByName(this, "Color").getValColor();
		boolean brackets = Client.getInstance().getSettingsManager().getSettingByName(this, "Brackets").getValBoolean();
		boolean showBiome = Client.getInstance().getSettingsManager().getSettingByName(this, "Show Biome").getValBoolean();
		
		String biome = "";
		Chunk chunk = this.mc.theWorld.getChunkFromBlockCoords(new BlockPos(mc.thePlayer));
		this.maxWidth = 100;
		biome = chunk.getBiome(new BlockPos(mc.thePlayer), this.mc.theWorld.getWorldChunkManager()).biomeName;

		if(maxWidth < (font.getStringWidth((!brackets ? "" : "[") + "Biome: " + biome + (!brackets ? "" : "]"))) || !background) {
			maxWidth = (int) (font.getStringWidth((!brackets ? "" : "[") + "Biome: " + biome + (!brackets ? "" : "]")) + 10);
		}else {
			maxWidth = 100;
		}

		if(background) {
			if(roundedCorners) {
				RenderUtil.drawRound(0, 0, maxWidth, getHeight(), Client.getInstance().getSettingsManager().getSettingByName(this, "Rounding Strength").getValInt(), backgroundColor);
			} else {
				RenderUtils.drawRect(0, 0, maxWidth, getHeight(), backgroundColor.getRGB());
			}
		}

		font.drawString((!brackets ? "" : "[") + "X: " + (int) (mc.thePlayer.posX) + (!brackets ? "" : "]"), (int) (0 + 4.5F), (int) (0 + 4.5F), color.getRGB(), fontShadow);
		font.drawString((!brackets ? "" : "[") + "Y: " + (int) (mc.thePlayer.posY) + (!brackets ? "" : "]"), (int) (0 + 4.5F), (int) (0 + 14.5F), color.getRGB(), fontShadow);
		font.drawString((!brackets ? "" : "[") + "Z: " + (int) (mc.thePlayer.posZ) + (!brackets ? "" : "]"), (int) (0 + 4.5F), (int) (0 + 24.5F), color.getRGB(), fontShadow);
		if(showBiome) {
			font.drawString((!brackets ? "" : "[") + "Biome: " + biome + (!brackets ? "" : "]"), (int) (0 + 4.5F), (int) (0 + 34.5F), color.getRGB(), fontShadow);
		}
		
		return true;
	}
}

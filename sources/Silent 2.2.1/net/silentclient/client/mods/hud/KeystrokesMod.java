package net.silentclient.client.mods.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.EnumChatFormatting;
import net.silentclient.client.Client;
import net.silentclient.client.gui.animation.SimpleAnimation;
import net.silentclient.client.gui.font.SilentFontRenderer;
import net.silentclient.client.gui.hud.ScreenPosition;
import net.silentclient.client.gui.lite.clickgui.utils.GlUtils;
import net.silentclient.client.gui.lite.clickgui.utils.RenderUtils;
import net.silentclient.client.gui.util.RenderUtil;
import net.silentclient.client.mods.CustomFontRenderer;
import net.silentclient.client.mods.ModCategory;
import net.silentclient.client.mods.ModDraggable;
import net.silentclient.client.utils.ColorUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.util.ArrayList;

public class KeystrokesMod extends ModDraggable {
	
	public KeystrokesMod() {
		super("Keystrokes", ModCategory.MODS, "silentclient/icons/mods/keystrokes.png");
	}
	
	@Override
	public void setup() {
		this.addSliderSetting("Scale",  this, 2.20F, 1.0F, 5.0F, false);
		ArrayList<String> cpsModes = new ArrayList<>();
		cpsModes.add("None");
		cpsModes.add("Small");
		cpsModes.add("Large");
		this.addBooleanSetting("Font Shadow", this, true);
		this.addBooleanSetting("Fancy Font", this, false);
		this.addBooleanSetting("Background", this, true);
		this.addBooleanSetting("Rounded Corners", this, false);
		this.addSliderSetting("Rounding Strength", this, 3, 1, 6, true);
		this.addModeSetting("CPS Mode", this, "Small", cpsModes);
		this.addColorSetting("Color", this, new Color(255, 255, 255));
		this.addColorSetting("Background Color", this, new Color(0, 0, 0), 127);
		this.addColorSetting("Clicked Color", this, new Color(0, 0, 0));
		this.addColorSetting("Clicked Background Color", this, new Color(255, 255, 255), 127);
		this.addSliderSetting("Fade Delay", this, 100, 0, 1000, true);
		this.addBooleanSetting("Show LMB/RMB", this, true);
		this.addBooleanSetting("Show Space", this, true);
		this.addBooleanSetting("Replace Names With Arrow", this, false);
	}

	public enum KeystrokesMode {
		
		WASD(Key.W, Key.A, Key.S, Key.D),
		WASD_MOUSE(Key.W, Key.A, Key.S, Key.D, Key.LMB, Key.RMB);
		
		private final Key[] keys;
		private int width;
		private int height;
		
		KeystrokesMode(Key... keysIn) {
			this.keys = keysIn;
			
			for(Key key : keys) {
				this.width = Math.max(this.width, key.getX() + key.getWidth());
				this.height = Math.max(this.height, key.getY() + key.getHeight());
			}
		}
		
		public int getHeight() {
			return height;
		}
		
		public int getWidth() {
			return width;
		}
		
		public Key[] getKeys() {
			return keys;
		}
	}
	
	private static class Key {
		
		private static final Key W = new Key("W", Minecraft.getMinecraft().gameSettings.keyBindForward, 20, 0, 18, 18);
		private static final Key A = new Key("A", Minecraft.getMinecraft().gameSettings.keyBindLeft, 0, 20, 18, 18);
		private static final Key S = new Key("S", Minecraft.getMinecraft().gameSettings.keyBindBack, 20, 20, 18, 18);
		private static final Key D = new Key("D", Minecraft.getMinecraft().gameSettings.keyBindRight, 40, 20, 18, 18);
		
		private static final Key LMB = new Key("LMB", Minecraft.getMinecraft().gameSettings.keyBindAttack, 0, 40, 28, 18);
		private static final Key RMB = new Key("RMB", Minecraft.getMinecraft().gameSettings.keyBindUseItem, 30, 40, 28, 18);
		
		public SimpleAnimation red = new SimpleAnimation(0);
		public SimpleAnimation green = new SimpleAnimation(0);
		public SimpleAnimation blue = new SimpleAnimation(0);
		public SimpleAnimation alpha = new SimpleAnimation(0);
		
		private final String name;
		private final KeyBinding keyBind;
		private final int x;
		private final int y;
		private final int width;
		private final int height;
		
		public Key (String name, KeyBinding keyBind, int x, int y, int width, int height) {
			this.name = name;
			this.keyBind = keyBind;
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
		}
		
		public boolean isDown() {
			try {
				if(Minecraft.getMinecraft().currentScreen != null) {
					return false;
				}

				if(this == Key.LMB || this == Key.RMB) {
					return Mouse.isButtonDown(this == Key.LMB ? 0 : 1);
				}
				return Keyboard.isKeyDown(keyBind.getKeyCode());
			} catch (Exception err) {
				return keyBind.isKeyDown();
			}
		}
		
		public int getHeight() {
			return height;
		}
		
		public int getWidth() {
			return width;
		}
		
		 public String getName() {
			return name;
		}
		 
		 
		 public int getX() {
			return x;
		}
		 
		 public int getY() {
			return y;
		}
	}
	
	private final Key Space = new Key("RMB", Minecraft.getMinecraft().gameSettings.keyBindJump, 31, 41, 28, 18);

	@Override
	public int getWidth() {
		return Client.getInstance().getSettingsManager().getSettingByName(this, "Show LMB/RMB").getValBoolean() ? KeystrokesMode.WASD_MOUSE.getWidth() : KeystrokesMode.WASD.getWidth();
	}

	@Override
	public int getHeight() {
		boolean space = Client.getInstance().getSettingsManager().getSettingByName(this, "Show Space").getValBoolean();
		int height = Client.getInstance().getSettingsManager().getSettingByName(this, "Show LMB/RMB").getValBoolean() ? KeystrokesMode.WASD_MOUSE.getHeight() : KeystrokesMode.WASD.getHeight();
		if(space) {
			height += 13;
		}
		return height;
	}

	@Override
	public boolean render(ScreenPosition pos) {
		GlStateManager.pushMatrix();
		boolean fancyFont = Client.getInstance().getSettingsManager().getSettingByName(this, "Fancy Font").getValBoolean();
		CustomFontRenderer font = new CustomFontRenderer();
		
		font.setRenderMode(fancyFont ? CustomFontRenderer.RenderMode.CUSTOM : CustomFontRenderer.RenderMode.DEFAULT);
		KeystrokesMode mode = Client.getInstance().getSettingsManager().getSettingByName(this, "Show LMB/RMB").getValBoolean() ? KeystrokesMode.WASD_MOUSE : KeystrokesMode.WASD;
		boolean shadow = Client.getInstance().getSettingsManager().getSettingByName(this, "Font Shadow").getValBoolean();
		boolean background = Client.getInstance().getSettingsManager().getSettingByName(this, "background").getValBoolean();
		Color backgroundColor = Client.getInstance().getSettingsManager().getSettingByName(this, "Background Color").getValColor();
		Color clickedBackgroundColor = Client.getInstance().getSettingsManager().getSettingByName(this, "Clicked Background Color").getValColor();
		boolean smallCps = Client.getInstance().getSettingsManager().getSettingByName(this, "CPS Mode").getValString().equalsIgnoreCase("small");
		boolean largeCps = Client.getInstance().getSettingsManager().getSettingByName(this, "CPS Mode").getValString().equalsIgnoreCase("large");
		boolean space = Client.getInstance().getSettingsManager().getSettingByName(this, "Show Space").getValBoolean();
		Color color = Client.getInstance().getSettingsManager().getSettingByName(this, "Color").getValColor();
		Color clickedColor = Client.getInstance().getSettingsManager().getSettingByName(this, "Clicked Color").getValColor();
		int fadeDelay = Client.getInstance().getSettingsManager().getSettingByName(this, "Fade Delay").getValInt() != 0 ? 100000 / Client.getInstance().getSettingsManager().getSettingByName(this, "Fade Delay").getValInt() : 0;
		boolean roundedCorners = Client.getInstance().getSettingsManager().getSettingByName(this, "Rounded Corners").getValBoolean();

		for(Key key : mode.getKeys()) {
			String renderString = key.getName();

			if(largeCps) {
				if (key == Key.LMB && Client.getInstance().getCPSTracker().getLCPS() != 0) {
					renderString = Client.getInstance().getCPSTracker().getLCPS() + " CPS";
				}
				if (key == Key.RMB && Client.getInstance().getCPSTracker().getRCPS() != 0) {
					renderString = "" + Client.getInstance().getCPSTracker().getRCPS() + " CPS";
				}
			}

			int textWidth = font.getStringWidth(renderString);
			
			if(background) {
				setAnimation(key.red, key.isDown() ? clickedBackgroundColor.getRed() : backgroundColor.getRed(), fadeDelay);
				setAnimation(key.green, key.isDown() ? clickedBackgroundColor.getGreen() : backgroundColor.getGreen(), fadeDelay);
				setAnimation(key.blue, key.isDown() ? clickedBackgroundColor.getBlue() : backgroundColor.getBlue(), fadeDelay);
				setAnimation(key.alpha, key.isDown() ? clickedBackgroundColor.getAlpha() : backgroundColor.getAlpha(), fadeDelay);
				if(roundedCorners) {
					RenderUtil.drawRound(
							key.getX(),
                            key.getY(),
							key.getWidth(),
							key.getHeight(),
							Client.getInstance().getSettingsManager().getSettingByName(this, "Rounding Strength").getValInt(),
							new Color((int) key.red.getValue(), (int) key.green.getValue(), (int) key.blue.getValue(), (int) key.alpha.getValue())
					);
				} else {
					RenderUtils.drawRect(
							key.getX(),
							key.getY(),
							key.getWidth(),
							key.getHeight(),
							new Color((int) key.red.getValue(), (int) key.green.getValue(), (int) key.blue.getValue(), (int) key.alpha.getValue()).getRGB()
					);
				}
				ColorUtils.setColor(-1);
			}
			
			if((key == Key.LMB || key == Key.RMB) && smallCps) {
				font.drawString(
						key.getName(),
						0 + key.getX() + key.getWidth() / 2 - textWidth / 2 - (fancyFont && key == Key.LMB ? 1 : 0),
						0 + key.getY() + key.getHeight() / 2 - 7,
						key.isDown() ? clickedColor.getRGB() : color.getRGB(),
								shadow
					);
				final int cpsI = (key == Key.LMB ? Client.getInstance().getCPSTracker().getLCPS() : Client.getInstance().getCPSTracker().getRCPS());
				final String cpsText = cpsI + " CPS";
		        final int cpsTextWidth = font.getStringWidth(cpsText);
				GlUtils.startScale(0 + key.getX() + key.getWidth() / 2 - cpsTextWidth / 2, 0 + key.getY() + key.getHeight() / 2 + 2, 0.6F);
				font.drawString(
						cpsText,
						0 + key.getX() + key.getWidth() / 2 - cpsTextWidth / 2 + (cpsI >= 10 ? 10 : 9) - (fancyFont ? 2 : 0),
						0 + key.getY() + key.getHeight() / 2 + 2,
						key.isDown() ? clickedColor.getRGB() : color.getRGB(),
								shadow
					);
				GlUtils.stopScale();
				if(key.isDown() && clickedColor.getRGB() == -16777216) {
					font.drawString("", 0, 0, -1, shadow);
				}
			} else {
				if(Client.getInstance().getSettingsManager().getSettingByName(this, "Replace Names With Arrow").getValBoolean() && (key == Key.W || key == Key.A || key == Key.S || key == Key.D)) {
					String arrow = "▲";
					
					switch(key.getName()) {
					case "A":
						arrow = "◀";
						break;
					case "S":
						arrow = "▼";
						break;
					case "D":
						arrow = "▶";
						break;
					}
					
					this.font.drawString(
							arrow,
							0 + key.getX() + (key.getWidth() / 2) - (this.font.getStringWidth(arrow) / 2),
							0 + key.getY() + (key.getHeight() / 2) - 3,
							key.isDown() ? clickedColor.getRGB() : color.getRGB(),
							shadow
						);
				} else {
					boolean needResize = textWidth + 4 >= key.getWidth();
					if(!needResize) {
						font.drawString(
								renderString,
								0 + key.getX() + key.getWidth() / 2 - textWidth / 2,
								0 + key.getY() + key.getHeight() / 2 - 4,
								key.isDown() ? clickedColor.getRGB() : color.getRGB(),
								shadow
						);
					} else {
						if(fancyFont) {
							textWidth = font.getStringWidth(renderString, 11, SilentFontRenderer.FontType.TITLE);
							font.drawString(renderString,
									0 + key.getX() + key.getWidth() / 2 - textWidth / 2,
									0 + key.getY() + key.getHeight() / 2 - 7, 11, SilentFontRenderer.FontType.TITLE,
									key.isDown() ? clickedColor.getRGB() : color.getRGB(),
									shadow);
						} else {
							Client.getInstance().textUtils.drawScaledString(renderString, 0 + key.getX() + key.getWidth() / 2 - textWidth / 2 + 3 + (key == Key.RMB ? 8 : 0) + (renderString.length() >= 6 ? -0.5F : 0), 0 + key.getY() + key.getHeight() / 2 + 8, key.isDown() ? clickedColor.getRGB() : color.getRGB(), 0.8F, shadow);
						}
					}
				}
				if(key.isDown() && clickedColor.getRGB() == -16777216) {
					font.drawString("", 0, 0, -1, shadow);
				}
			}
		}
		
		if(space) {
			String name = (EnumChatFormatting.STRIKETHROUGH + "------");
			if(background) {
				setAnimation(Space.red, Space.isDown() ? clickedBackgroundColor.getRed() : backgroundColor.getRed(), fadeDelay);
				setAnimation(Space.green, Space.isDown() ? clickedBackgroundColor.getGreen() : backgroundColor.getGreen(), fadeDelay);
				setAnimation(Space.blue, Space.isDown() ? clickedBackgroundColor.getBlue() : backgroundColor.getBlue(), fadeDelay);
				setAnimation(Space.alpha, Space.isDown() ? clickedBackgroundColor.getAlpha() : backgroundColor.getAlpha(), fadeDelay);
				if(roundedCorners) {
					RenderUtil.drawRound(
							0,
							0 + mode.getHeight() + 2,
							mode.getWidth(),
							10,
							Client.getInstance().getSettingsManager().getSettingByName(this, "Rounding Strength").getValInt(),
							new Color((int) Space.red.getValue(), (int) Space.green.getValue(), (int) Space.blue.getValue(), (int) Space.alpha.getValue())
					);
				} else {
					RenderUtils.drawRect(
							0,
							0 + mode.getHeight() + 2,
							mode.getWidth(),
							10,
							new Color((int) Space.red.getValue(), (int) Space.green.getValue(), (int) Space.blue.getValue(), (int) Space.alpha.getValue()).getRGB()
					);
				}
				ColorUtils.setColor(-1);
			}
			font.drawString(
					name,
					0 + mode.getWidth() / 2 - font.getStringWidth(name) / 2,
					0 + mode.getHeight() + 2 + 10 / 2 - 4,
					Space.isDown() ? clickedColor.getRGB() : color.getRGB(),
					shadow
				);
			
			if(Space.isDown() && clickedColor.getRGB() == -16777216) {
				font.drawString("", 0, 0, -1, shadow);
			}
			
		}
		
		GlStateManager.popMatrix();
		
		return true;
	}
	
	public void setAnimation(SimpleAnimation animation, int value, int fadeDelay) {
		if(fadeDelay != 0) {
			animation.setAnimation(value, fadeDelay);
		} else {
			animation.setValue(value);
		}
	}

}

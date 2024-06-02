/**
 * 
 */
package cafe.kagu.kagu.ui.gui;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.vecmath.Vector4d;

import cafe.kagu.kagu.Kagu;
import cafe.kagu.kagu.font.FontRenderer;
import cafe.kagu.kagu.font.FontUtils;
import cafe.kagu.kagu.managers.FileManager;
import cafe.kagu.kagu.managers.SessionManager;
import cafe.kagu.kagu.utils.SoundUtils;
import cafe.kagu.kagu.utils.UiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;

/**
 * @author lavaflowglow
 *
 */
public class GuiAltManager extends GuiScreen {
	
	private static final GuiAltManager INSTANCE = new GuiAltManager();
	public static GuiAltManager getInstance() {
		return INSTANCE;
	}
	
	private static int backgroundColor = UiUtils.getColorFromVector(new Vector4d(0.156862745, 0.156862745, 0.156862745, 1)),
			buttonDefaultColor = UiUtils.getColorFromVector(new Vector4d(0.211764706, 0.211764706, 0.211764706, 1)),
			mojangButtonColor = UiUtils.getColorFromVector(new Vector4d(0.937254902, 0.196078431, 0.239215686, 1)),
			mojangButtonHoverColor = UiUtils.getColorFromVector(new Vector4d(0.831372549, 0.062745098, 0.105882353, 1)),
			microsoftButtonColor = UiUtils.getColorFromVector(new Vector4d(1, 1, 1, 1)),
			microsoftButtonHoverColor = UiUtils.getColorFromVector(new Vector4d(0.788235294, 0.788235294, 0.788235294, 1)),
			microsoftFontColor = UiUtils.getColorFromVector(new Vector4d(0.454901961, 0.454901961, 0.454901961, 1));
	private static boolean leftMouseClicked = false;
	private static ResourceLocation defaultSteve3D = new ResourceLocation("Kagu/altManager/steve 3d.png"),
									defaultSteveFace = new ResourceLocation("Kagu/altManager/steve face.png"),
									mojangLogo = new ResourceLocation("Kagu/altManager/mojang.png"),
									microsoftLogo = new ResourceLocation("Kagu/altManager/microsoft.png");
	
	private static CopyOnWriteArrayList<Alt> alts = new CopyOnWriteArrayList<>();
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		
		double buttonWidth = width * 0.2334375, buttonHeight = height * 0.0925925926, buttonImageSize = height * 0.0653333333,
				buttonImageMargin = (buttonHeight - buttonImageSize) / 2;
		double buttonMargin = 7;
		double buttonCurve = 5;
		double playerScale = 1;
		double playerHeight = buttonWidth * playerScale * 1.62025316;
		final FontRenderer mojangAltFont = FontUtils.MOJANG_LOGO_20;
		final FontRenderer microsoftAltFont = FontUtils.MICROSOFT_LOGO_16;
		final FontRenderer altNameFont = FontUtils.SMALL_PIXEL_16;
		final FontRenderer altInfoFont = FontUtils.SMALL_PIXEL_10;
		
		drawRect(0, 0, width, height, backgroundColor);
		
		// Add cracked alt button
		int mojangButtonColor = GuiAltManager.mojangButtonColor;
		if (UiUtils.isMouseInsideRoundedRect(mouseX, mouseY, width - buttonWidth - buttonMargin, height - buttonHeight - buttonMargin, width - buttonMargin, height - buttonMargin, buttonCurve)) {
			if (leftMouseClicked) {
				mc.getSoundHandler().playSound(SoundUtils.getClickSound());
				leftMouseClicked = false;
			}
			mojangButtonColor = GuiAltManager.mojangButtonHoverColor;
		}
		UiUtils.drawRoundedRect(width - buttonWidth - buttonMargin, height - buttonHeight - buttonMargin, width - buttonMargin, height - buttonMargin, mojangButtonColor, buttonCurve);
		mc.getTextureManager().bindTexture(mojangLogo);
		Gui.drawTexture(width - buttonWidth - buttonMargin + buttonImageMargin, height - buttonHeight - buttonMargin + buttonImageMargin, buttonImageSize, buttonImageSize, true);
		mojangAltFont.drawCenteredString("Add Cracked Alt", width - buttonWidth - buttonMargin + buttonImageMargin + buttonImageSize + ((buttonWidth - buttonImageSize - buttonImageMargin * 2) / 2),
				height - buttonHeight / 2 - buttonMargin - mojangAltFont.getFontHeight() / 2 + 2, 0xffffffff);
		
		// Add microsoft alt button
		int microsoftButtonColor = GuiAltManager.microsoftButtonColor;
		if (UiUtils.isMouseInsideRoundedRect(mouseX, mouseY, width - buttonWidth * 2 - buttonMargin * 2, height - buttonHeight - buttonMargin, width - buttonMargin * 2 - buttonWidth, height - buttonMargin, buttonCurve)) {
			if (leftMouseClicked) {
				mc.getSoundHandler().playSound(SoundUtils.getClickSound());
				SessionManager.startMicrosoftAuth();
				leftMouseClicked = false;
			}
			microsoftButtonColor = GuiAltManager.microsoftButtonHoverColor;
		}
		UiUtils.drawRoundedRect(width - buttonWidth * 2 - buttonMargin * 2, height - buttonHeight - buttonMargin, width - buttonMargin * 2 - buttonWidth, height - buttonMargin, microsoftButtonColor, buttonCurve);
		mc.getTextureManager().bindTexture(microsoftLogo);
		Gui.drawTexture(width - buttonWidth * 2 - buttonMargin * 2 + buttonImageMargin, height - buttonHeight - buttonMargin + buttonImageMargin, buttonImageSize, buttonImageSize, true);
		microsoftAltFont.drawCenteredString("Add Microsoft Alt", width - buttonWidth * 2 - buttonMargin * 2 + buttonImageMargin + buttonImageSize + ((buttonWidth - buttonImageSize - buttonImageMargin * 2) / 2),
				height - buttonHeight / 2 - buttonMargin - microsoftAltFont.getFontHeight() / 2 - 2, microsoftFontColor);
		
		// Draw all the alts
		int offset = 0;
		final int altButtonHeight = height / 8;
		for (Alt alt : alts) {
			
			UiUtils.drawRoundedRect(5, offset + 5, width - buttonWidth * 2 - buttonMargin * 8, offset + altButtonHeight, 0xff1e1e1e, 5);
			if (alt.getUsername().equals(mc.getSession().getUsername()))
				UiUtils.drawRoundedRect(width - buttonWidth * 2 - buttonMargin * 8 - 10, offset + 5, width - buttonWidth * 2 - buttonMargin * 8, offset + altButtonHeight, 0xffd58cff, 5);
			if (leftMouseClicked && UiUtils.isMouseInsideRoundedRect(mouseX, mouseY, 5, offset + 5, width - buttonWidth * 2 - buttonMargin * 8, offset + altButtonHeight, 5)) {
				switch (alt.getType()) {
					case "Microsoft":{
						try {
							Session session = mc.getSession();
							SessionManager.loginToMicrsoftAccount(((MicrosoftAlt)alt).getMinecraftAccessToken());
							if (session != mc.getSession()) {
								alt.setUsername(mc.getSession().getUsername());
								saveAlts();
							}
						} catch (Exception e) {
							try {
								SessionManager.refreshAltAccessToken(((MicrosoftAlt)alt));
								Session session = mc.getSession();
								SessionManager.loginToMicrsoftAccount(((MicrosoftAlt)alt).getMinecraftAccessToken());
								if (session != mc.getSession()) {
									alt.setUsername(mc.getSession().getUsername());
									saveAlts();
								}
							} catch (Exception e2) {
								e.printStackTrace();
							}
						}
					}break;
					case "Cracked":{
						SessionManager.loginCracked(alt.getUsername());
					}break;
				}
				leftMouseClicked = false;
			}
			mc.getTextureManager().bindTexture(defaultSteveFace);
			drawTexture(10, offset + 10, altButtonHeight - 15, altButtonHeight - 15, false);
			altNameFont.drawString(alt.getUsername(), altButtonHeight + 5, offset + 10, -1);
			
			offset += altButtonHeight + 5;
		}
		
		if (leftMouseClicked)
			leftMouseClicked = false;
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		if (mouseButton == 0)
			leftMouseClicked = true;
	}
	
	/**
	 * @return the alts
	 */
	public static CopyOnWriteArrayList<Alt> getAlts() {
		return alts;
	}
	
	/**
	 * Saves alts to a file
	 */
	public static void saveAlts() {
		String output = "";
		String unitSeparator = Kagu.UNIT_SEPARATOR;
		String groupSeparator = Kagu.GROUP_SEPARATOR;
		String recordSeparator = Kagu.RECORD_SEPARATOR;
		for (Alt alt : alts) {
			if (!output.isEmpty())
				output += unitSeparator;
			output += alt.getType() + recordSeparator + alt.getUsername();
			if (alt.getType().equalsIgnoreCase("Microsoft")) {
				MicrosoftAlt microsoftAlt = (MicrosoftAlt)alt;
				output += groupSeparator + microsoftAlt.getRefreshToken() + recordSeparator + microsoftAlt.getMinecraftAccessToken() + recordSeparator + microsoftAlt.getUuid();
			}
		}
		FileManager.writeStringToFile(FileManager.ALTS, output);
	}
	
	/**
	 * Loads alts from a file
	 */
	public static void loadAlts() {
		
	}
	
	public static class Alt{
		
		protected String username = " ";
		protected String type = "";

		/**
		 * @return the username
		 */
		public String getUsername() {
			return username;
		}

		/**
		 * @param username the username to set
		 */
		public void setUsername(String username) {
			this.username = username;
		}
		
		/**
		 * @return the type
		 */
		public String getType() {
			return type;
		}
		
	}
	
	public static class CrackedAlt extends Alt {
		
		/**
		 * @param username The username for the cracked account
		 */
		public CrackedAlt(String username) {
			type = "Cracked";
			this.username = username;
		}
		
	}
	
	public static class MicrosoftAlt extends Alt {
		
		public MicrosoftAlt() {
			type = "Microsoft";
		}
		
		private String refreshToken = " ";
		private String uuid = " ";
		private String minecraftAccessToken = " ";

		/**
		 * @return the refreshToken
		 */
		public String getRefreshToken() {
			return refreshToken;
		}

		/**
		 * @param refreshToken the refreshToken to set
		 */
		public void setRefreshToken(String refreshToken) {
			this.refreshToken = refreshToken;
		}

		/**
		 * @return the uuid
		 */
		public String getUuid() {
			return uuid;
		}

		/**
		 * @param uuid the uuid to set
		 */
		public void setUuid(String uuid) {
			this.uuid = uuid;
		}

		/**
		 * @return the minecraftAccessToken
		 */
		public String getMinecraftAccessToken() {
			return minecraftAccessToken;
		}

		/**
		 * @param minecraftAccessToken the minecraftAccessToken to set
		 */
		public void setMinecraftAccessToken(String minecraftAccessToken) {
			this.minecraftAccessToken = minecraftAccessToken;
		}

	}
	
}

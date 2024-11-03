package net.silentclient.client.premium;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import net.silentclient.client.Client;
import net.silentclient.client.gui.elements.Checkbox;
import net.silentclient.client.gui.elements.ColorPicker;
import net.silentclient.client.gui.elements.Input;
import net.silentclient.client.gui.elements.StaticButton;
import net.silentclient.client.gui.font.SilentFontRenderer;
import net.silentclient.client.gui.lite.clickgui.GuiColorPicker;
import net.silentclient.client.gui.util.ColorPickerAction;
import net.silentclient.client.gui.util.RenderUtil;
import net.silentclient.client.utils.ColorUtils;
import net.silentclient.client.utils.MouseCursorHandler;
import net.silentclient.client.utils.Sounds;

import java.awt.*;
import java.net.URI;

public class PremiumGui {
	public static MouseCursorHandler.CursorType drawScreen(int x, int y, int width, int height, int mouseX, int mouseY, float partialTicks, Input input) {
		MouseCursorHandler.CursorType cursorType = null;

		if(!Client.getInstance().getAccount().isPlus()) {
			RenderUtil.drawImage(new ResourceLocation("silentclient/premium/promo.png"), x + 95, y + 40, width - 100, (int) ((width - 100) / 1.777777777777778));
			StaticButton.render(x + 132, y + 148, 65, 12, "BUY PREMIUM");
			StaticButton.render(x + 272, y + 148, 65, 12, "BUY PREMIUM+");
			if(StaticButton.isHovered(mouseX, mouseY, x + 132, y + 148, 65, 12) || StaticButton.isHovered(mouseX, mouseY, x + 272, y + 148, 65, 12)) {
				cursorType = MouseCursorHandler.CursorType.POINTER;
			}
			return cursorType;
		}
		int settingY = y + 25;
		int days = Client.getInstance().getAccount().getPlusExpiration();
        Client.getInstance().getSilentFontRenderer().drawString(days != -1 ? days + " days left" : "Unknown Time Remaining", x + width - (Client.getInstance().getSilentFontRenderer().getStringWidth(days != -1 ? days + " days left" : "Unknown Time Remaining", 10, SilentFontRenderer.FontType.TITLE)) - 8, y + 8, 10, SilentFontRenderer.FontType.TITLE);
		ColorPicker.render(x, settingY, width, "Chroma Bandana Color", Client.getInstance().getAccount().getBandanaColor() == 50 ? ColorUtils.getChromaColor(0, 0, 1).getRGB() : Client.getInstance().getAccount().getBandanaColor());
		if(ColorPicker.isHovered(mouseX, mouseY, x, settingY, width)) {
			cursorType = MouseCursorHandler.CursorType.POINTER;
		}
		settingY += 15;
        Client.getInstance().getSilentFontRenderer().drawString("Custom Capes", x + 100, settingY + ((9 / 2) - (12 / 2)), 12, SilentFontRenderer.FontType.TITLE);
        StaticButton.render(x + 310, settingY, 65, 12, Client.getInstance().getAccount().isPremiumPlus() ? "OPEN MENU" : "BUY PREMIUM+");
        if(StaticButton.isHovered(mouseX, mouseY, x + 310, settingY, 65, 12)) {
			cursorType = MouseCursorHandler.CursorType.POINTER;
		}
		settingY += 15;
        if(Client.getInstance().getAccount().isPremiumPlus()) {
        	ColorUtils.setColor(new Color(255, 255, 255, 127).getRGB());
        	Client.getInstance().getSilentFontRenderer().drawString("Nametag Message Settings:", x + 100, settingY + ((9 / 2) - (12 / 2)), 12, SilentFontRenderer.FontType.TITLE);
            settingY += 15;
            Checkbox.render(mouseX, mouseY, x + 100, settingY - 1, "Show Nametag Message", Client.getInstance().getAccount().showNametagMessage());
            if(Checkbox.isHovered(mouseX, mouseY, x + 100, settingY - 1)) {
				cursorType = MouseCursorHandler.CursorType.POINTER;
			}
			settingY += 15;
            Client.getInstance().getSilentFontRenderer().drawString("Nametag Message:", x + 100, settingY + (1), 12, SilentFontRenderer.FontType.TITLE);
            input.render(mouseX, mouseY, x + 100 + ((190 * 2) - 108) / 2, settingY, ((190 * 2) - 108) / 2, true);
            if(input.isHovered()) {
				cursorType = MouseCursorHandler.CursorType.EDIT_TEXT;
			}
			settingY += 20;
            StaticButton.render(x + 322, settingY, 50, 12, "Save");
			if(StaticButton.isHovered(mouseX, mouseY, x + 322, settingY, 50, 12)) {
				cursorType = MouseCursorHandler.CursorType.POINTER;
			}
        } else {
        	Client.getInstance().getSilentFontRenderer().drawString("Nametag Message Settings:", x + 100, settingY + ((9 / 2) - (12 / 2)), 12, SilentFontRenderer.FontType.TITLE);
            StaticButton.render(x + 310, settingY, 65, 12, "BUY PREMIUM+");
        	if(StaticButton.isHovered(mouseX, mouseY, x + 310, settingY, 65, 12)) {
				cursorType = MouseCursorHandler.CursorType.POINTER;
			}
		}

		return cursorType;
	}
	
	public static void mouseClicked(int x, int y, int width, int height, int mouseX, int mouseY, int mouseButton, GuiScreen instance, Input input) {
		if(!Client.getInstance().getAccount().isPlus()) {
			if(StaticButton.isHovered(mouseX, mouseY, x + 132, y + 148, 65, 12) || StaticButton.isHovered(mouseX, mouseY, x + 272, y + 148, 65, 12)) {
				Sounds.playButtonSound();
				try {
					Class<?> oclass = Class.forName("java.awt.Desktop");
					Object object = oclass.getMethod("getDesktop", new Class[0]).invoke((Object)null, new Object[0]);
					oclass.getMethod("browse", new Class[] {URI.class}).invoke(object, new Object[] {new URI("https://store.silentclient.net/premium")});
				} catch (Throwable err) {
					err.printStackTrace();
				}
			}
			return;
		}
		int settingY = y + 25;
		if(ColorPicker.isHovered(mouseX, mouseY, x, settingY, width)) {
			Sounds.playButtonSound();
			Minecraft.getMinecraft().displayGuiScreen(new GuiColorPicker(Client.getInstance().getAccount().getBandanaColor() == 50 ? new Color(255, 255, 255) : new Color(Client.getInstance().getAccount().getBandanaColor()), Client.getInstance().getAccount().getBandanaColor() == 50, false, 255, new ColorPickerAction() {
				@Override
				public void onChange(Color color, boolean chroma, int opacity) {
					int colorInt = 0;
					colorInt = color.getRGB();
					if(chroma) {
						colorInt = 50;
					}
					if(Client.getInstance().getAccount().getBandanaColor() != colorInt) {
						Client.getInstance().getAccount().setBandanaColor(colorInt);
					}
				}

				@Override
				public void onClose(Color color, boolean chroma, int opacity) {
					Client.getInstance().getAccount().saveBandanaColor();
				}
			}, instance));
		}
		settingY += 15;
		if(StaticButton.isHovered(mouseX, mouseY, x + 310, settingY, 65, 12)) {
			Sounds.playButtonSound();
			try {
				Class<?> oclass = Class.forName("java.awt.Desktop");
				Object object = oclass.getMethod("getDesktop", new Class[0]).invoke((Object)null, new Object[0]);
				oclass.getMethod("browse", new Class[] {URI.class}).invoke(object, new Object[] {new URI(Client.getInstance().getAccount().isPremiumPlus() ? "https://store.silentclient.net/premium/custom_cape" : "https://store.silentclient.net/premium")});
			} catch (Throwable err) {
				err.printStackTrace();
			}
		}
		settingY += 15;
		if(Client.getInstance().getAccount().isPremiumPlus()) {
			settingY += 15;
			if(Checkbox.isHovered(mouseX, mouseY, x + 100, settingY - 1)) {
				Client.getInstance().getAccount().setShowNametagMessage(!Client.getInstance().getAccount().showNametagMessage());
			}
			settingY += 15;
			input.onClick(mouseX, mouseY, x + 100 + ((190 * 2) - 108) / 2, settingY, ((190 * 2) - 108) / 2, true);
			settingY += 20;
			if(StaticButton.isHovered(mouseX, mouseY, x + 322, settingY, 50, 12)) {
				Client.getInstance().getAccount().setNametagMessage(input.getValue());
			}
		}
	}
}

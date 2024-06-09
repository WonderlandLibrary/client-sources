package me.swezedcode.client.gui.alts;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.lwjgl.input.Keyboard;

import me.swezedcode.client.gui.other.CustomButton;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;


public class GuiDirectLogin extends GuiScreen {
	public GuiScreen parent;
	public GuiTextField usernameBox;
	public GuiPasswordField passwordBox;
	public GuiTextField sessionBox;
    private static final ResourceLocation NiGHT = new ResourceLocation("textures/gui/title/background/index.jpg");
	public GuiDirectLogin(GuiScreen paramScreen) {
		this.parent = paramScreen;
	}
	
	public void initGui() {
		Keyboard.enableRepeatEvents(true);
		buttonList.add(new CustomButton(1, width / 2 - 100, height / 4 + 96 + 12, "Login"));
		buttonList.add(new CustomButton(2, width / 2 - 100, height / 4 + 96 + 36, "Back"));
		usernameBox = new GuiTextField(3, fontRendererObj, width / 2 - 100, 76 - 25, 200, 20);
		passwordBox = new GuiPasswordField(fontRendererObj, width / 2 - 100, 116 - 25, 200, 20);
		sessionBox = new GuiTextField(4, fontRendererObj, width / 2- 100, 156 - 25, 200, 20);
		usernameBox.setMaxStringLength(200);
		passwordBox.setMaxStringLength(128);
		sessionBox.setMaxStringLength(257);
	}
	
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}
	
	public void updateScreen() {
		usernameBox.updateCursorCounter();
		passwordBox.updateCursorCounter();
		sessionBox.updateCursorCounter();
	}
	
	public void mouseClicked(int x, int y, int b) {
		usernameBox.mouseClicked(x, y, b);
		passwordBox.mouseClicked(x, y, b);
		sessionBox.mouseClicked(x, y, b);
		try {
			super.mouseClicked(x, y, b);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void actionPerformed(GuiButton button) {
		if(button.id == 1) {
			if(!usernameBox.getText().trim().isEmpty() && !passwordBox.getText().trim().isEmpty()) {
				try {
					PLoginUtils.login(usernameBox.getText(), passwordBox.getText());
				} catch(Exception error) {
					GuiAltList.dispErrorString = "".concat("\247cBad Login \2477(").concat(usernameBox.getText()).concat(")");
				}
			} else if(!usernameBox.getText().trim().isEmpty() && !sessionBox.getText().trim().isEmpty()) {
                mc.session = new Session(usernameBox.getText().trim(), sessionBox.getText().trim(), "swag", "Mojang");
                GuiAltList.dispErrorString = "";
			} else if(!usernameBox.getText().trim().isEmpty()) {
				mc.session = new Session(usernameBox.getText().trim(), "-", "swag", "Mojang");
				GuiAltList.dispErrorString = "";
			}
            
			mc.displayGuiScreen(parent);
		}else if(button.id == 2) {
			mc.displayGuiScreen(parent);
		}
	}
	
	public static String excutePost(String s, String s1) {
		HttpsURLConnection httpsurlconnection = null;

		try {
			try {
				URL url = new URL(s);
				httpsurlconnection = (HttpsURLConnection)url.openConnection();
				httpsurlconnection.setRequestMethod("POST");
				httpsurlconnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				httpsurlconnection.setRequestProperty("Content-Type", Integer.toString(s1.getBytes().length));
				httpsurlconnection.setRequestProperty("Content-Language", "en-US");
				httpsurlconnection.setUseCaches(false);
				httpsurlconnection.setDoInput(true);
				httpsurlconnection.setDoOutput(true);
				httpsurlconnection.connect();
				DataOutputStream dataoutputstream = new DataOutputStream(httpsurlconnection.getOutputStream());
				dataoutputstream.writeBytes(s1);
				dataoutputstream.flush();
				dataoutputstream.close();
				InputStream inputstream = httpsurlconnection.getInputStream();
				BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(inputstream));
				StringBuffer stringbuffer = new StringBuffer();
				String s2;

				while((s2 = bufferedreader.readLine()) != null) {
					stringbuffer.append(s2);
					stringbuffer.append('\r');
				}

				bufferedreader.close();
				String s3 = stringbuffer.toString();
				String s4 = s3;
				return s3;
			} catch (Exception exception) {
				exception.printStackTrace();
			}

			return null;
		} finally {
			if (httpsurlconnection != null) {
				httpsurlconnection.disconnect();
			}
		}
	}
	
	protected void keyTyped(char c, int i) {
		usernameBox.textboxKeyTyped(c, i);
		passwordBox.textboxKeyTyped(c, i);
		sessionBox.textboxKeyTyped(c, i);
		if(c == '\t') {
			if(usernameBox.isFocused()) {
				usernameBox.setFocused(false);
				passwordBox.setFocused(true);
				sessionBox.setFocused(false);
			}else if(passwordBox.isFocused()) {
				usernameBox.setFocused(false);
				passwordBox.setFocused(false);
				sessionBox.setFocused(true);
			}else if(sessionBox.isFocused()) {
				usernameBox.setFocused(true);
				passwordBox.setFocused(false);
				sessionBox.setFocused(false);
			}
		}
		if(c == '\r') {
			actionPerformed((CustomButton) buttonList.get(0));
		}
	}
	
	public void drawScreen(int x, int y, float f) {
		this.mc.getTextureManager().bindTexture(this.NiGHT);
        drawScaledCustomSizeModalRect(0, 0, 0.0F, 0.0F, ScaledResolution.getScaledWidth(), ScaledResolution.getScaledHeight(), ScaledResolution.getScaledWidth(), ScaledResolution.getScaledHeight(), ScaledResolution.getScaledWidth(), ScaledResolution.getScaledHeight());
		drawString(fontRendererObj, "Username", width / 2 - 100, 63 - 25, 0xA0A0A0);
		drawString(fontRendererObj, "\2474*", width / 2 - 106, 63 - 25, 0xA0A0A0);
		drawString(fontRendererObj, "Password", width / 2 - 100, 104 - 25, 0xA0A0A0);
		drawString(fontRendererObj, "Session ID (Advanced)", width / 2 - 100, 144 - 25, 0xA0A0A0);
		try {
			usernameBox.drawTextBox();
			passwordBox.drawTextBox();
			sessionBox.drawTextBox();
		} catch(Exception err) {
			err.printStackTrace();
		}
		super.drawScreen(x, y, f);
	}
}

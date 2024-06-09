package Squad.Accounts;


import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.swing.JOptionPane;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.lwjgl.input.Keyboard;

import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;


import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;

public class GuiAltManager extends GuiScreen {
	private final GuiScreen parentScreen;
	private GuiTextField email;
	private GuiTextField password;
	private String status;

	public GuiAltManager(GuiScreen p_i45020_1_) {
		this.parentScreen = p_i45020_1_;

	}

	protected void keyTyped(char typedChar, int keyCode) throws IOException {

		if (keyCode == Keyboard.KEY_TAB) {
			if (this.email.isFocused()) {
				this.password.setFocused(true);
				this.email.setFocused(false);

			} else if (this.password.isFocused()) {
				this.email.setFocused(true);
				this.password.setFocused(false);

			}
		}

		if (this.email.isFocused()) {
			this.email.textboxKeyTyped(typedChar, keyCode);

		} else if (this.password.isFocused()) {
			this.password.textboxKeyTyped(typedChar, keyCode);

		}

	}

	public void initGui() {
		this.buttonList.clear();

		int var3 = this.height / 4 + 48;

		status = "§2" + mc.session.getUsername();

		this.email = new GuiTextField(2, this.fontRendererObj, this.width / 2 - 100, 130, 207, 20);
		this.email.setFocused(true);

		this.password = new GuiTextField(3, this.fontRendererObj, this.width / 2 - 100, 154, 207, 20);
		this.buttonList
		.add(new GuiButton(0, this.width / 2 - 102, var3 + 40, 211, 20, I18n.format("Verlassen", new Object[0])));

		this.buttonList
				.add(new GuiButton(1, this.width / 2 - 102, var3 + 60, 211, 20, I18n.format("Login", new Object[0])));
		this.buttonList.add(new GuiButton(2, this.width / 2 - 102, var3 + 80, 211, 20,
				I18n.format("Clipboard Alt", new Object[0])));


	}

	protected void actionPerformed(GuiButton button) throws IOException {
		if (button.id == 1) {

			if (!email.getText().equals("") && !password.getText().equals("")) {

				String tempStatus = Auth.login(new Account(email.getText(), password.getText()));

				if (tempStatus.equals("")) {

					status = "§2" + mc.session.getUsername();

					this.mc.displayGuiScreen(this.parentScreen);

				} else {

					status = "§4" + mc.session.getUsername() + " §b| " + tempStatus;

				}

			}

		}

		if (button.id == 0) {
	      	  this.mc.displayGuiScreen(new GuiMainMenu());
	      	}

		if (button.id == 2) {

			String clipboard = null;

			try {
				clipboard = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
			} catch (HeadlessException e) {

				e.printStackTrace();
			} catch (UnsupportedFlavorException e) {

				e.printStackTrace();
			}

			String[] emailandpass = null;

			if (clipboard.contains(":")) {
				emailandpass = clipboard.split(":");

				String tempStatus = Auth.login(new Account(emailandpass[0], emailandpass[1]));

				if (tempStatus.equals("")) {

					status = "§2" + mc.session.getUsername();

					this.mc.displayGuiScreen(this.parentScreen);

				} else {

					status = "§4" + mc.session.getUsername() + " §b| " + tempStatus;

				}

			} else {
				status = "§4" + mc.session.getUsername() + " §b| " + "§3Please copy a correctly formatted Alt!";
			}

		}

		if (button.id == 3) {

			try {
				URL url = new URL("https://www.accountdispenser.com/api/api.php?key=3429058225289201987474479");
				Map<String, Object> params = new LinkedHashMap<>();

				byte[] postDataBytes = "".toString().getBytes("UTF-8");

				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.addRequestProperty("User-Agent",
						"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
				conn.setRequestMethod("POST");
				conn.setDoOutput(true);
				conn.getOutputStream().write(postDataBytes);

				Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

				StringBuilder builder = new StringBuilder();

				for (int c = in.read(); c != -1; c = in.read())
					builder.append((char) c);

				String emailandpass[] = builder.toString().split(":");

				System.out.println(builder.toString().replace("\n", ""));

				String tempStatus = Auth.login(new Account(emailandpass[0], emailandpass[1].replace("\n", "")));

				if (tempStatus.equals("")) {

					status = "§2" + mc.session.getUsername();

					this.mc.displayGuiScreen(this.parentScreen);

				} else {

					status = "§4" + mc.session.getUsername() + " §b| " + tempStatus;

				}

			} catch (Exception e) {
			}
		}

		if (button.id == 10) {

			String clipboard = null;

			try {
				clipboard = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
			} catch (HeadlessException e) {

				e.printStackTrace();
			} catch (UnsupportedFlavorException e) {

				e.printStackTrace();
			}

			String[] data = null;

			data = new String(Base64.getDecoder().decode(clipboard)).split(";");

			byte[] uudata = null;
			try {
				uudata = Hex.decodeHex(data[4].toCharArray());
			} catch (DecoderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String uuid = new UUID(ByteBuffer.wrap(uudata, 0, 8).getLong(), ByteBuffer.wrap(uudata, 8, 8).getLong())
					.toString();

			mc.session = new Session(data[0], uuid, data[2], "mojang");

			

		}

	}

	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);

		this.email.mouseClicked(mouseX, mouseY, mouseButton);

		this.password.mouseClicked(mouseX, mouseY, mouseButton);

	}

	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution s1 = new ScaledResolution(Minecraft.getMinecraft(),Minecraft.getMinecraft().displayWidth,Minecraft.getMinecraft().displayHeight);
        this.mc.getTextureManager().bindTexture(new ResourceLocation("Sclayed/bg.jpg"));
        
        Gui.drawModalRectWithCustomSizedTexture(0, 0, 0, 0, s1.getScaledWidth(), s1.getScaledHeight(), s1.getScaledWidth(), s1.getScaledHeight());

drawRect(0, 99, 900, 100, 0xff000000);
drawRect(0, 99, 900, 100, 0xff000000);
drawRect(0, 99, 900, 260, 0x99000000);
drawRect(0, 99, 900, 260, 0x99000000);
		int var3 = this.height / 4 + 48;



	this.email.drawTextBox();
	this.password.drawTextBox();

		

		super.drawScreen(mouseX, mouseY, partialTicks);

	}

	public void updateScreen() {
		this.email.updateCursorCounter();
		this.password.updateCursorCounter();
	}

}
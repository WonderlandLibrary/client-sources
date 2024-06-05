package zamorozka.gui;

import java.awt.Color;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.Proxy;

import org.lwjgl.input.Keyboard;

import com.mojang.authlib.Agent;
import com.mojang.authlib.BaseUserAuthentication;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiErrorScreen;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;
import zamorozka.main.Zamorozka;
import zamorozka.ui.LoginUtils;

public class GuiName extends GuiScreen {
	private GuiScreen parentScreen;
	private GuiTextField usernamepasswordTextField;

	public GuiName(GuiScreen guiscreen) {
		this.parentScreen = guiscreen;
	}

	public void updateScreen() {
		this.usernamepasswordTextField.updateCursorCounter();
	}

	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	public void initGui() {
		Keyboard.enableRepeatEvents(true);
		this.buttonList.clear();
		buttonList.add(new GuiButton(4, this.width / 2 - 150 / 2, 150 + 10 * 4, "Login"));
		buttonList.add(new GuiButton(5, this.width / 2 - 150 / 2, 150 + 20 * 4, "Back"));
		buttonList.add(new GuiButton(6, this.width / 8 + 780 / 2, 10, "User/Pass"));
		this.usernamepasswordTextField = new GuiTextField(2, this.fontRendererObj, this.width / 2 - 150 / 2, 130, 200, 20);
	}

	protected void keyTyped(char c, int i) {
		this.usernamepasswordTextField.textboxKeyTyped(c, i);
		if ((c == '\t') && (this.usernamepasswordTextField.isFocused())) {
			this.usernamepasswordTextField.setFocused(false);
		}
		if (c == '\r') {
			actionPerformed((GuiButton) this.buttonList.get(0));
		}
	}

	protected void mouseClicked(int i, int j, int k) throws IOException {
		super.mouseClicked(i, j, k);
		this.usernamepasswordTextField.mouseClicked(i, j, k);
	}

	protected void actionPerformed(GuiButton guibutton) {
		if (guibutton.id == 4) {
			if (usernamepasswordTextField.getText() != null && !usernamepasswordTextField.getText().isEmpty()) {
				if (!usernamepasswordTextField.getText().contains(" ")) {

					try {

						final String args[] = usernamepasswordTextField.getText().split(":");
						if (args[0].contains("@")) {
							final YggdrasilUserAuthentication aut = (YggdrasilUserAuthentication) new YggdrasilAuthenticationService(Proxy.NO_PROXY, "").createUserAuthentication(Agent.MINECRAFT);
							aut.setUsername(args[0]);
							aut.setPassword(args[1]);
							try {
								aut.logIn();

								this.mc.session = new Session(aut.getSelectedProfile().getName(), aut.getSelectedProfile().getId().toString(), aut.getAuthenticatedToken(), "mojang");
								System.out.print("True Login");

							} catch (Exception e) {

							}
						} else {
							System.out.print("No @");
						}

					} catch (Exception e) {
						System.out.print("Erroe alt");
					}
				} else {
					System.out.print("Error alt");
				}
			} else {
				System.out.print("No Alt");
			}
		}
		if (guibutton.id == 5) {
			mc.displayGuiScreen(null);
		}
		if (guibutton.id == 6) {
			this.mc.displayGuiScreen(new GuiPremN(parentScreen));

		}

	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		ScaledResolution s1 = new ScaledResolution(this.mc);
		this.mc.getTextureManager().bindTexture(new ResourceLocation("altfon2.jpg"));
		Gui.drawModalRectWithCustomSizedTexture(0, 0, 0.0F, 0.0F, s1.getScaledWidth(), s1.getScaledHeight(), s1.getScaledWidth(), s1.getScaledHeight());
		drawGradientRect(0, 0, width, height, 0x02044A0, 0x570044A0);
		drawGradientRect(0, 0, width, height, 0x02044A0, 0x570044A0);
		drawGradientRect(0, 0, width, height, 0x02044A0, 0x570044A0);
		drawGradientRect(0, 0, width, height, 0x02044A0, 0x570044A0);
		drawString(mc.fontRendererObj, "Email:Password", this.width / 2 - 10, (this.height / 3) - 20 + 17, Color.white.getRGB());
		this.usernamepasswordTextField.drawTextBox();
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

}

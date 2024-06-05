package de.tired.api.guis.accountlogin;



import de.tired.interfaces.FHook;
import de.tired.interfaces.IHook;
import de.tired.shaderloader.ShaderRenderer;
import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import com.thealtening.api.response.Account;
import com.thealtening.api.retriever.AsynchronousDataRetriever;
import com.thealtening.api.retriever.BasicDataRetriever;
import com.thealtening.auth.TheAlteningAuthentication;
import com.thealtening.auth.service.AlteningServiceType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.Session;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;
import java.net.Proxy;

public class TheAlteningLogin extends GuiScreen implements IHook {

	private GuiTextField loginField;

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		GL11.glPopMatrix();
		GlStateManager.pushMatrix();
		ShaderRenderer.renderBG();
		Gui.drawRect(0, 0, width, height, new Color(22, 22, 22, 213).getRGB());
		GlStateManager.popMatrix();
		ShaderRenderer.stopBlur();
		int defaultHeight = this.height / 4 + 48;
		double widthA = 140;
		Gui.drawRect(width / 2 - widthA, height / 4 + 35, width / 2 + widthA, height / 4 + 175, Integer.MIN_VALUE);
		Gui.drawRect(width / 2 - widthA, height / 4 + 25, width / 2 + widthA, height / 4 + 175, Integer.MIN_VALUE);
		loginField.drawTextBox();

		if (!loginField.isFocused()) {
			FHook.fontRenderer3.drawStringWithShadow2("API KEY", width / 2 - widthA + 45,height / 4 + 76 ,-1);
		}

		String name = "";


		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		loginField.mouseClicked(mouseX, mouseY, mouseButton);
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		switch (button.id) {
			case 1337:
				MC.displayGuiScreen(new GuiAccountLogin());
				break;
			case 1221:
				final BasicDataRetriever basicDataRetriever = new BasicDataRetriever(loginField.getText());
				final TheAlteningAuthentication theAlteningAuthentication = TheAlteningAuthentication.theAltening();
				basicDataRetriever.updateKey(loginField.getText());
				theAlteningAuthentication.updateService(AlteningServiceType.THEALTENING);
				final AsynchronousDataRetriever asynchronousDataRetriever = basicDataRetriever.toAsync();
				if (!loginField.getText().isEmpty()) {
					final Account account = asynchronousDataRetriever.getAccount();
					String authServer = theAlteningAuthentication.getService().getAuthServer();
					String sessionServer = theAlteningAuthentication.getService().getSessionServer();
					final YggdrasilUserAuthentication service = (YggdrasilUserAuthentication) new YggdrasilAuthenticationService(Proxy.NO_PROXY, "").createUserAuthentication(Agent.MINECRAFT);

					service.setUsername(account.getToken());
					service.setPassword("Sheesh");

					try {
						service.logIn();
					} catch (AuthenticationException e) {
						e.printStackTrace();
					}
					Minecraft.getMinecraft().session = new Session(service.getSelectedProfile().getName(), service.getSelectedProfile().getId().toString(), service.getAuthenticatedToken(), "LEGACY");
				}
		}
		super.actionPerformed(button);
	}
	@Override
	public void initGui() {
		int defaultHeight = this.height / 4 + 48;
		this.loginField = new GuiTextField(this.height / 4 + 24, this.mc.fontRendererObj, this.width / 2 - 100, defaultHeight + 20, 200, 20);
		this.buttonList.add(new GuiButton(1337, 10, 10, "Back"));
		this.buttonList.add(new GuiButton(1221, this.width / 2 - 100, defaultHeight + 80, "Login"));
		this.loginField.setFocused(true);
		this.loginField.setCensored(true);
		Keyboard.enableRepeatEvents(true);
		super.initGui();
	}
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if (keyCode == Keyboard.KEY_ESCAPE) return;
		try {
			super.keyTyped(typedChar, keyCode);
		} catch (IOException e) {
			e.printStackTrace();
		}
		loginField.textboxKeyTyped(typedChar, keyCode);
		super.keyTyped(typedChar, keyCode);
	}



}

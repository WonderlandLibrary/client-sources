package com.kilo.ui;

import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.renderer.GlStateManager;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import com.kilo.Kilo;
import com.kilo.render.Colors;
import com.kilo.render.Draw;
import com.kilo.render.Fonts;
import com.kilo.ui.inter.Button;
import com.kilo.ui.inter.Inter;
import com.kilo.ui.inter.Link;
import com.kilo.ui.inter.PasswordBox;
import com.kilo.ui.inter.TextBox;
import com.kilo.users.UserControl;
import com.kilo.util.Align;
import com.kilo.util.ChatUtil;
import com.kilo.util.Config;
import com.kilo.util.Resources;
import com.kilo.util.ServerUtil;
import com.kilo.util.Timer;
import com.kilo.util.UIUtil;
import com.kilo.util.Util;

public class UILogin extends UI {

	private float formOffset;
	private boolean invalid, checking;
	
	private String invalidMessage = "";
	
	private Timer invalidTimer = new Timer();
	
	private float fX, fY, fW, fH, checkRotate, checkOpacity;
	private final float rotateSpeed = 10f;
	
	public UILogin(UI parent) {
		super(parent);
	}
	
	@Override
	public void init() {
		title = "Login";
		
		formOffset = 0;
		invalid = false;
		checking = false;
		checkRotate = 0;
		
		fX = Display.getWidth()/2;
		fY = (Display.getHeight()/2)+40;
		fW = 380;
		fH = 200;
		
		inters.clear();
		inters.add(new TextBox(this, "Username...", (Display.getWidth()/2)-(fW/2)+30, (Display.getHeight()/2)+40-(fH/2)+15, 380-60, 50, Fonts.ttfStandard25, -1, Align.L, Align.C));
		inters.add(new PasswordBox(this, "Password...", (Display.getWidth()/2)-(fW/2)+30, (Display.getHeight()/2)+40-(fH/2)+65, 380-60, 50, Fonts.ttfStandard25, -1, Align.L, Align.C));
		inters.add(new Button(this, "Log in", (Display.getWidth()/2)+20, (Display.getHeight()/2)+(fH/2)-20, (fW/2)-40, 40, Fonts.ttfStandard20, Colors.GREEN.c, Resources.iconSubmit[2], 24));
		inters.add(new Link(this, "Create one", 20+Fonts.ttfRoundedBold14.getWidth("Don't have an account? "), Display.getHeight()-21, Fonts.ttfRoundedBold14, Colors.BLUE.c, Align.L, Align.B));
	}
	
	public void update(int mx, int my) {
		super.update(mx, my);
		if (invalid) {
			formOffset+= ((-Fonts.ttfStandard14.getHeight()*2)-formOffset)/5f;
			if (invalidTimer.isTime(2f)) {
				invalid = false;
			}
		} else {
			invalidTimer.reset();
		}
		
		checkRotate+= rotateSpeed;
		checkOpacity+= (checking?0.2:-0.2);
		checkOpacity = Math.min(Math.max(0f, checkOpacity), 1f);
		
		if (!invalid) {
			formOffset+= (0-formOffset)/5f;
		}
		
		((Button)inters.get(2)).text = checking?(String)null:"Log in";
	}
	
	@Override
	public void interact(Inter i) {
		switch(inters.indexOf(i)) {
		case 2:
			submit();
			break;
		case 3:
			UIHandler.changeUI(new UINewAccount(this));
			break;
		}
	}
	
	public void keyboardPress(int key) {
		super.keyboardPress(key);
		switch (key) {
		case Keyboard.KEY_RETURN:
			submit();
			break;
		}
	}
	
	private void submit() {
		if (!invalid && validate() == "" && !checking) {
			checking = true;
			new Thread() {
				@Override
				public void run() {
					String username = ((TextBox)inters.get(0)).getText();
					String password = ((TextBox)inters.get(1)).getText();
					String[] login = ServerUtil.login(username, password);
					if (login != null) {
						try {
							long a = Long.parseLong(login[0]);
							String[] cd = ServerUtil.getClientDetails(login[0]);
		
							Kilo.kilo().client = new UserControl(cd[0], cd[1], cd[2], cd[3], cd[4], cd[5], cd[6], cd[7]);

							Config.saveClient();
							
							if (!Kilo.kilo().client.gameStatus.equalsIgnoreCase("verified") && !Kilo.kilo().client.gameStatus.equalsIgnoreCase("banned")) {
								UIHandler.changeUI(new UIVerifyAccount(null));
							} else {
								if (Kilo.kilo().client.gameStatus.equalsIgnoreCase("verified")) {
									UIHandler.changeUI(new UILoggedIn(null));
								} else {
									UIHandler.changeUI(new UIBanned(null));
								}
							}
						} catch (Exception e) {
							UIHandler.changeUI(new UILoggedIn(null));
						}
					} else {
						UIHandler.changeUI(new UILoggedIn(null));
					}
				}
			}.start();
		} else {
			if (!invalid) {
				invalidMessage = validate();
				invalid = true;
				checking = false;
			}
		}
		if (formOffset < (-Fonts.ttfStandard14.getHeight()*1.5f)) {
			invalid = false;
		}
	}
	
	private String validate() {
		boolean username = ((TextBox)inters.get(0)).getText().length() > 0;
		boolean usernameValid = ChatUtil.isValidUsername(((TextBox)inters.get(0)).getText());
		boolean password = ((TextBox)inters.get(1)).getText().length() > 0;
		if (username) {
			if (usernameValid) {
				if (password) {
					return "";
				} else {
					return "Please enter a password";
				}
			} else {
				return "Please enter a valid username (a-z, 0-9 and _)";
			}
		} else {
			return "Please enter a username";
		}
	}
	
	public void render(float opacity) {
		Draw.string(Fonts.ttfRoundedBold40, Display.getWidth()/2, Display.getHeight()/2-(fH/2)-10, title, Util.reAlpha(Colors.WHITE.c, 1f*opacity), Align.C, Align.C);
		
		Draw.rect(fX-(fW/2), fY-(fH/2), fX+(fW/2), fY+(fH/2), Util.reAlpha(0xFF000000, 0.5f*opacity));

		Draw.startClip((Display.getWidth()/2)-(Fonts.ttfStandard14.getWidth(invalidMessage)/2), fY+(fH/2)+(Fonts.ttfStandard14.getHeight()/2), (Display.getWidth()/2)+(Fonts.ttfStandard14.getWidth(invalidMessage)/2), fY+(fH/2)+(Fonts.ttfStandard14.getHeight()*1.5f));
		Draw.string(Fonts.ttfStandard14, Display.getWidth()/2, fY+(fH/2)-(Fonts.ttfStandard14.getHeight(invalidMessage))-formOffset, invalidMessage, Util.reAlpha(0xFFFF5555, 1f*opacity), Align.C, Align.C);
		Draw.endClip();
		
		Draw.string(Fonts.ttfRoundedBold14, 20, Display.getHeight()-20-Fonts.ttfRoundedBold14.getHeight(), "Don't have an account?", Util.reAlpha(Colors.WHITE.c, 1f*opacity));
		
		//Draw.loader(fX-(fW/2)+32, fY+(fH/2)-32, 8, Util.reAlpha(Colors.WHITE.c, checkOpacity*opacity));
		
		super.render(opacity);
	}
}

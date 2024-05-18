package com.kilo.ui;

import net.minecraft.client.gui.GuiMainMenu;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import com.kilo.Kilo;
import com.kilo.manager.DatabaseManager;
import com.kilo.render.Colors;
import com.kilo.render.Draw;
import com.kilo.render.Fonts;
import com.kilo.ui.inter.Button;
import com.kilo.ui.inter.CheckBox;
import com.kilo.ui.inter.Inter;
import com.kilo.ui.inter.Link;
import com.kilo.ui.inter.PasswordBox;
import com.kilo.ui.inter.PasswordBoxAlt;
import com.kilo.ui.inter.TextBox;
import com.kilo.ui.inter.TextBoxAlt;
import com.kilo.users.UserControl;
import com.kilo.util.Align;
import com.kilo.util.ChatUtil;
import com.kilo.util.Config;
import com.kilo.util.Resources;
import com.kilo.util.ServerUtil;
import com.kilo.util.Timer;
import com.kilo.util.UIUtil;
import com.kilo.util.Util;

public class UINewAccount extends UI {

	private float formOffset;
	private boolean invalid, checking;
	
	private String invalidMessage = "";
	
	private Timer invalidTimer = new Timer();
	
	private float fX, fY, fW, fH;
	
	public UINewAccount(UI parent) {
		super(parent);
	}
	
	@Override
	public void init() {
		title = "Create a KiLO Account";
		
		formOffset = 0;
		invalid = false;
		
		fX = Display.getWidth()/2;
		fY = (Display.getHeight()/2);
		fW = Display.getWidth() >= 1280?1260:840;
		fH = 550;
		
		inters.clear();
		inters.add(new TextBoxAlt(this, "Enter In-Game Name...", fX-(fW/2)+110, fY-(fH/2)+55, 160, 30, Fonts.ttfRoundedBold12, -1, Align.L, Align.C));
		inters.add(new TextBoxAlt(this, "Enter First Name...", fX-(fW/2)+210, fY-(fH/2)+165, 160, 30, Fonts.ttfRoundedBold12, -1, Align.L, Align.C));
		inters.add(new TextBoxAlt(this, "Enter Email Address...", fX-(fW/2)+600, fY-(fH/2)+165, 160, 30, Fonts.ttfRoundedBold12, -1, Align.L, Align.C));
		inters.add(new PasswordBoxAlt(this, "Enter Password...", fX-(fW/2)+210, fY-(fH/2)+210, 160, 30, Fonts.ttfRoundedBold12, -1, Align.L, Align.C));
		inters.add(new PasswordBoxAlt(this, "Enter Password Again...", fX-(fW/2)+600, fY-(fH/2)+210, 160, 30, Fonts.ttfRoundedBold12, -1, Align.L, Align.C));
		inters.add(new Button(this, "Create", fX-(fW/2)+664, fY+(fH/2)-60, 150, 40, Fonts.ttfStandard20, Colors.GREEN.c, Resources.iconSubmit[2], 24));
		inters.add(new Link(this, "Log in", 20+Fonts.ttfRoundedBold14.getWidth("Already have an account? "), Display.getHeight()-21, Fonts.ttfRoundedBold14, Colors.BLUE.c, Align.L, Align.B));
		inters.add(new Link(this, DatabaseManager.accountAbout, fX-(fW/2)+860+Fonts.ttfRoundedBold16.getWidth("Learn more at "), fY-(fH/2)+59, Fonts.ttfRoundedBold16, Colors.WHITE.c, Align.L, Align.T));
		inters.add(new Link(this, DatabaseManager.account, fX-(fW/2)+60+Fonts.ttfRoundedBold12.getWidth("This is what you will use to login to "), fY-(fH/2)+260, Fonts.ttfRoundedBold12, Colors.DARKBLUE.c, Align.L, Align.T));
		inters.add(new CheckBox(this, "I agree to the KiLO Terms and Conditions, as well as the Privacy Policy.", fX-(fW/2)+70, fY-(fH/2)+370, Fonts.ttfRoundedBold14, 0xFF999999));
		inters.add(new CheckBox(this, "I wish to receive information about KiLO to my email address.", fX-(fW/2)+70, fY-(fH/2)+430, Fonts.ttfRoundedBold14, 0xFF999999));
		inters.add(new Link(this, "Terms and Conditions", fX-(fW/2)+664-16, fY+(fH/2)-40, Fonts.ttfRoundedBold12, 0xFF666666, Align.R, Align.C));
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
		if (!invalid) {
			formOffset+= (0-formOffset)/5f;
		}
		
		((Button)inters.get(5)).text = checking?(String)null:"Create";
		((Link)inters.get(7)).enabled = Display.getWidth() >= 1280;
		((Link)inters.get(7)).shown = Display.getWidth() >= 1280;
	}
	
	@Override
	public void interact(Inter i) {
		switch (i.type) {
		case checkbox:
			i.active = !i.active;
			break;
		default:
			switch(inters.indexOf(i)) {
			case 5:
				submit();
				break;
			case 6:
				UIHandler.changeUI(parent);
				break;
			case 7:
				Util.openWeb(DatabaseManager.accountAbout);
				break;
			case 8:
				Util.openWeb(DatabaseManager.account);
				break;
			case 11:
				Util.openWeb(DatabaseManager.terms);
				break;
			}
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
					String primaryAccount= ((TextBox)inters.get(0)).getText();
					String name = ((TextBox)inters.get(1)).getText();
					String email = ((TextBox)inters.get(2)).getText();
					String password = ((TextBox)inters.get(3)).getText();
					String passwordConfirm = ((TextBox)inters.get(4)).getText();
					boolean tac = ((CheckBox)inters.get(9)).active;
					boolean info = ((CheckBox)inters.get(10)).active;
					
					String[] connect = ServerUtil.createAccount(primaryAccount, name, email, password, info);
					if (connect != null) {
						try {
							long a = Long.parseLong(connect[0]);
							String[] cd = ServerUtil.getClientDetails(connect[0]);
		
							Kilo.kilo().client = new UserControl(cd[0], cd[1], cd[2], cd[3], cd[4], cd[5], cd[6], cd[7]);
		
							Config.saveClient();
		
							UIHandler.changeUI(new UIVerifyAccount(null));
						} catch (Exception e) {
							invalidMessage = connect[1];
							checking = false;
							invalid = false;
						}
					} else {
						invalidMessage = "There was a problem connecting to the database";
						checking = false;
						invalid = true;
					}
				}
			}.start();
		} else {
			if (invalid == false) {
				invalidMessage = validate();
				checking = false;
				invalid = true;
			}
		}
		if (formOffset < (-Fonts.ttfStandard14.getHeight()*1.5f)) {
			invalid = false;
		}
	}
	
	private String validate() {
		String[] input = new String[] {((TextBox)inters.get(0)).getText(), ((TextBox)inters.get(1)).getText(), ((TextBox)inters.get(2)).getText(), ((TextBox)inters.get(3)).getText(), ((TextBox)inters.get(4)).getText()};
		boolean[] hasText = new boolean[] {input[0].length() > 0, input[1].length() > 0, input[2].length() > 0, input[3].length() > 0, input[4].length() > 0};
		boolean[] isLength = new boolean[] {input[0].length() >= 1, input[1].length() >= 2, input[2].length() >= 0, input[3].length() >= 4, input[4].length() >= 4};
		boolean[] isValid = new boolean[] {true, ChatUtil.isValidUsername(input[1]), ChatUtil.isValidEmail(input[2]), input[3].equalsIgnoreCase(input[4]), input[4].equalsIgnoreCase(input[3])};
		String [] hasTextError = new String[] {"Please enter a primary in-game name", "Please enter a nickname", "Please enter an email address", "Please enter a passwords", "Passwords don't match"};
		String [] isLengthError = new String[] {"Please use a longer primary in-game name", "Please use a longer nickname", "Please use a longer email address", "Please use a longer password", "Please use a longer password"};
		String [] isValidError = new String[] {"That is not a valid primary in-game name", "That is not a valid nickname", "That is not a valid email address", "Passwords don't match", "Passwords don't match"};
		
		String message = "";
		if (!((CheckBox)inters.get(9)).active) {
			message = "Please accept the Terms and Conditions";
			return message;
		}
		for(int i = input.length-1; i >= 0; i--) {
			if (hasText[i]) {
				if (isLength[i]) {
					if (isValid[i]) {
					} else {
						message = isValidError[i];
					}
				} else {
					message = isLengthError[i];
				}
			} else {
				message = hasTextError[i];
			}
		}
		return message;
	}
	
	public void render(float opacity) {
		Draw.string(Fonts.ttfRoundedBold40, Display.getWidth()/2, fY-(fH/2)-(Fonts.ttfRoundedBold40.getHeight(title)/2)-10, title, Util.reAlpha(Colors.WHITE.c, 1f*opacity), Align.C, Align.C);
		
		//Form boxing/sectioning
		Draw.rect(fX-(fW/2), fY-(fH/2), fX+(fW/2), fY+(fH/2), Util.reAlpha(0xFF000000, 0.5f*opacity));
		if (Display.getWidth() >= 1280) {
			Draw.rect(fX-(fW/2)+839, fY-(fH/2)+20, fX-(fW/2)+841, fY+(fH/2)-20, Util.reAlpha(Colors.WHITE.c, 0.3f*opacity));
		}
		
		//Numbering
		Draw.circle(fX-(fW/2)+56, fY-(fH/2)+56, 26, Util.reAlpha(Colors.DARKBLUE.c, 1f*opacity));
		Draw.string(Fonts.ttfRoundedBold20, fX-(fW/2)+55, fY-(fH/2)+56, "1", Util.reAlpha(Colors.WHITE.c, 1f*opacity), Align.C, Align.C);
		Draw.string(Fonts.ttfRoundedBold20, fX-(fW/2)+100, fY-(fH/2)+46, "Your Primary In-Game Name", Util.reAlpha(Colors.WHITE.c, 1f*opacity), Align.L, Align.C);

		Draw.circle(fX-(fW/2)+56, fY-(fH/2)+136, 26, Util.reAlpha(Colors.DARKBLUE.c, 1f*opacity));
		Draw.string(Fonts.ttfRoundedBold20, fX-(fW/2)+56, fY-(fH/2)+136, "2", Util.reAlpha(Colors.WHITE.c, 1f*opacity), Align.C, Align.C);
		Draw.string(Fonts.ttfRoundedBold20, fX-(fW/2)+100, fY-(fH/2)+124, "Your Details", Util.reAlpha(Colors.WHITE.c, 1f*opacity));

		Draw.circle(fX-(fW/2)+56, fY-(fH/2)+320, 26, Util.reAlpha(Colors.DARKBLUE.c, 1f*opacity));
		Draw.string(Fonts.ttfRoundedBold20, fX-(fW/2)+56, fY-(fH/2)+320, "3", Util.reAlpha(Colors.WHITE.c, 1f*opacity), Align.C, Align.C);
		Draw.string(Fonts.ttfRoundedBold20, fX-(fW/2)+100, fY-(fH/2)+308, "Terms and Conditions", Util.reAlpha(Colors.WHITE.c, 1f*opacity));

		//Details info
		Draw.string(Fonts.ttfRoundedBold12, fX-(fW/2)+195, fY-(fH/2)+172, "Nickname (First Name):",Util.reAlpha(Colors.WHITE.c, 1f*opacity), Align.R, Align.T);
		Draw.string(Fonts.ttfRoundedBold12, fX-(fW/2)+585, fY-(fH/2)+172, "Email Address:", Util.reAlpha(Colors.WHITE.c, 1f*opacity), Align.R, Align.T);
		Draw.string(Fonts.ttfRoundedBold12, fX-(fW/2)+195, fY-(fH/2)+217, "Password:", Util.reAlpha(Colors.WHITE.c, 1f*opacity), Align.R, Align.T);
		Draw.string(Fonts.ttfRoundedBold12, fX-(fW/2)+585, fY-(fH/2)+217, "Confirm Password:", Util.reAlpha(Colors.WHITE.c, 1f*opacity), Align.R, Align.T);
		
		Draw.string(Fonts.ttfRoundedBold12, fX-(fW/2)+60, fY-(fH/2)+260, "This is what you will use to login to", Util.reAlpha(Colors.GREY.c, 1f*opacity));
		
		//TAC
		Draw.string(Fonts.ttfRoundedBold14, fX-(fW/2)+108, fY-(fH/2)+390, "I'm also aware these can change from time to time.", Util.reAlpha(Colors.GREY.c, 1f*opacity));

		//Data prot
		Draw.string(Fonts.ttfRoundedBold14, fX-(fW/2)+26, fY+(fH/2)-20-Fonts.ttfRoundedBold14.getHeight(), "We take data protection seriously.", Util.reAlpha(Colors.GREY.c, 1f*opacity), Align.L, Align.B);
		Draw.string(Fonts.ttfRoundedBold14, fX-(fW/2)+26, fY+(fH/2)-20, "No information is shared unless you authorise it yourself.", Util.reAlpha(Colors.GREY.c, 1f*opacity), Align.L, Align.B);

		//Go to login page
		Draw.string(Fonts.ttfRoundedBold14, 20, Display.getHeight()-20-Fonts.ttfRoundedBold14.getHeight(), "Already have an account?", Util.reAlpha(Colors.WHITE.c, 1f*opacity));
		
		//Why need account
		if (Display.getWidth() >= 1280) {
			Draw.string(Fonts.ttfRoundedBold20, fX-(fW/2)+860, fY-(fH/2)+21, "Why do I need a KiLO Account?", Util.reAlpha(Colors.AQUA.c, 1f*opacity));
			Draw.string(Fonts.ttfRoundedBold16, fX-(fW/2)+860, fY-(fH/2)+60, "Learn more at", Util.reAlpha(Colors.GREY.c, 1f*opacity));
		}
		
		//Wizard pic
		if (Display.getWidth() >= 1280) {
			float xoff = ((fW-860)/2)-(231/2);
			float yoff = 100f;
			Draw.rectTexture(fX-(fW/2)+860+xoff, fY-(fH/2)+80+yoff, 231, 256, Resources.wizard, Util.reAlpha(0x00FFFFFF, opacity));
		}

		//Message
		Draw.startClip((Display.getWidth()/2)-(Fonts.ttfStandard14.getWidth(invalidMessage)/2), fY+(fH/2)+(Fonts.ttfStandard14.getHeight()/2), (Display.getWidth()/2)+(Fonts.ttfStandard14.getWidth(invalidMessage)/2), fY+(fH/2)+(Fonts.ttfStandard14.getHeight()*1.5f));
		Draw.string(Fonts.ttfStandard14, Display.getWidth()/2, fY+(fH/2)-(Fonts.ttfStandard14.getHeight(invalidMessage))-formOffset, invalidMessage, Util.reAlpha(0xFFFF5555, 1f*opacity), Align.C, Align.C);
		Draw.endClip();

		super.render(opacity);
	}
}

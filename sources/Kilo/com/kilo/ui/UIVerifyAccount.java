package com.kilo.ui;

import net.minecraft.client.renderer.GlStateManager;

import org.lwjgl.opengl.Display;

import com.kilo.Kilo;
import com.kilo.manager.DatabaseManager;
import com.kilo.render.Colors;
import com.kilo.render.Draw;
import com.kilo.render.Fonts;
import com.kilo.ui.inter.Inter;
import com.kilo.ui.inter.Link;
import com.kilo.util.Align;
import com.kilo.util.Resources;
import com.kilo.util.ServerUtil;
import com.kilo.util.Timer;
import com.kilo.util.Util;

public class UIVerifyAccount extends UI {

	private boolean verified;
	private float checkRotate;
	private float fX, fY, fW, fH;
	
	private final float rotateSpeed = 10f;
	
	private Timer timer = new Timer();
	
	public UIVerifyAccount(UI parent) {
		super(parent);
	}
	
	@Override
	public void init() {
		title = "Verify Your Account";
		
		verified = false;
		checkRotate = 0;
		
		fX = Display.getWidth()/2;
		fY = (Display.getHeight()/2);
		fW = 1100;
		fH = 350;
		
		inters.clear();
		inters.add(new Link(this, DatabaseManager.emailSupport, fX-(fW/2)+20+Fonts.ttfRoundedBold14.getWidth("Please email us at "), fY+(fH/2)-21, Fonts.ttfRoundedBold14, Colors.GREEN.c, Align.L, Align.B));
	}
	
	public void update(int mx, int my) {
		super.update(mx, my);
		
		if (verified) {
			checkRotate = 0;
			return;
		}
		checkRotate+= rotateSpeed;
		
		if (timer.isTime(10f)) {
			new Thread() {
				public void run() {
					String[] cd = ServerUtil.getClientDetails(Kilo.kilo().client.clientID);
					if (cd[5].equalsIgnoreCase("verified")) {
						UIHandler.changeUI(new UIVerified(null));
						verified = true;
					} else if (cd[5].equalsIgnoreCase("banned")) {
						UIHandler.changeUI(new UIWelcome(null));
					}
				}
			}.start();
			timer.reset();
		}
	}
	
	@Override
	public void interact(Inter i) {
		switch(inters.indexOf(i)) {
		case 0:
			Util.openWeb("mailto:"+DatabaseManager.emailSupport);
			break;
		}
	}
	
	public void render(float opacity) {
		Draw.string(Fonts.ttfRoundedBold40, Display.getWidth()/2, fY-(fH/2)-(Fonts.ttfRoundedBold40.getHeight(title)/2)-10, title, Util.reAlpha(Colors.WHITE.c, 1f*opacity), Align.C, Align.C);
		
		//Form boxing
		Draw.rect(fX-(fW/2), fY-(fH/2), fX+(fW/2), fY+(fH/2), Util.reAlpha(0xFF000000, 0.5f*opacity));
		
		//Data problem
		Draw.string(Fonts.ttfRoundedBold14, fX-(fW/2)+20, fY+(fH/2)-20-Fonts.ttfRoundedBold14.getHeight(), "Not there? Firstly, sorry about the inconvenience.", Util.reAlpha(Colors.GREY.c, 1f*opacity), Align.L, Align.B);
		Draw.string(Fonts.ttfRoundedBold14, fX-(fW/2)+20, fY+(fH/2)-20, "Please email us at ", Util.reAlpha(Colors.GREY.c, 1f*opacity), Align.L, Align.B);
		Draw.string(Fonts.ttfRoundedBold14, fX-(fW/2)+20+Fonts.ttfRoundedBold14.getWidth("Please email use at "+DatabaseManager.emailSupport), fY+(fH/2)-20, "and we will be happy to help!", Util.reAlpha(Colors.GREY.c, 1f*opacity), Align.L, Align.B);

		//User email
		if (Kilo.kilo().client != null) {
			Draw.string(Fonts.ttfRoundedBold20, fX-(fW/2)+30, fY-(fH/2)+30, "Hi "+Kilo.kilo().client.minecraftName, Util.reAlpha(Colors.WHITE.c, 1f*opacity));
			Draw.string(Fonts.ttfRoundedBold20, fX-(fW/2)+30, fY-(fH/2)+30+Fonts.ttfRoundedBold20.getHeight(), "To continue using KiLO, you are required to verify your email.", Util.reAlpha(Colors.WHITE.c, 1f*opacity));
			Draw.string(Fonts.ttfRoundedBold20, fX-(fW/2)+30, fY-(fH/2)+30+(Fonts.ttfRoundedBold20.getHeight()*2), "Please check your emails for your verification email on the account:", Util.reAlpha(Colors.WHITE.c, 1f*opacity));
			Draw.string(Fonts.ttfRoundedBold50, fX, fY, Kilo.kilo().client.email, Util.reAlpha(Colors.WHITE.c, 1f*opacity), Align.C, Align.C);
			
			Draw.loader(fX+(fW/2)-32, fY+(fH/2)-32, 8, Util.reAlpha(Colors.WHITE.c, 1f*opacity));
			
			Draw.string(Fonts.ttfRoundedBold20, fX+(fW/2)-62, fY+(fH/2)-20, "Checking Verification", Util.reAlpha(Colors.WHITE.c, 1f*opacity), Align.R, Align.B);
		}
		
		super.render(opacity);
	}
}

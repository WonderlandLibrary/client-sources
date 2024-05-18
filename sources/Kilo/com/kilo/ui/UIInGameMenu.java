package com.kilo.ui;

import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiShareToLan;
import net.minecraft.client.gui.achievement.GuiAchievements;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.multiplayer.WorldClient;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import com.kilo.Kilo;
import com.kilo.input.Input;
import com.kilo.manager.ActivityManager;
import com.kilo.manager.AddonManager;
import com.kilo.manager.DatabaseManager;
import com.kilo.manager.FriendManager;
import com.kilo.manager.MessageManager;
import com.kilo.render.Colors;
import com.kilo.render.Draw;
import com.kilo.render.Fonts;
import com.kilo.ui.inter.Button;
import com.kilo.ui.inter.IconButton;
import com.kilo.ui.inter.Inter;
import com.kilo.ui.inter.slotlist.SlotList;
import com.kilo.ui.inter.slotlist.part.Activity;
import com.kilo.ui.inter.slotlist.part.Friend;
import com.kilo.ui.inter.slotlist.part.Message;
import com.kilo.ui.inter.slotlist.slot.ActivitySlotFriendAccepted;
import com.kilo.ui.inter.slotlist.slot.ActivitySlotFriendRequest;
import com.kilo.ui.inter.slotlist.slot.ActivitySlotNewMessage;
import com.kilo.ui.inter.slotlist.slot.ActivitySlotServerInvite;
import com.kilo.ui.inter.slotlist.slot.FriendSlot;
import com.kilo.ui.inter.slotlist.slot.MessageSlot;
import com.kilo.users.User;
import com.kilo.users.UserHandler;
import com.kilo.util.Align;
import com.kilo.util.Resources;
import com.kilo.util.ServerUtil;
import com.kilo.util.Util;

public class UIInGameMenu extends UI {

	private UI popup, popupTo;
	private boolean popupFade;
	private float fX, fY, fW, fH, popupOpacity;

	public SlotList fsl;
	public SlotList asl;
	public SlotList msl;
	
	public UIInGameMenu(UI parent) {
		super(parent);
	}
	
	@Override
	public void init() {
		title = "Game Menu";
		
		fX = Display.getWidth()/2;
		fY = (Display.getHeight()/2);
		fW = 900;
		fH = 530;

		fsl = new SlotList(4f);
		asl = new SlotList(4f);
		msl = new SlotList(4f);
		
		inters.clear();
		float xx = fX-(fW/2)+8;
		inters.add(new Button(this, "Return", xx, fY-(fH/2)+8, 44+Fonts.ttfRoundedBold12.getWidth("Return"), 32, Fonts.ttfRoundedBold12, 0x00000000, Resources.iconBack[1], 16, Align.R, Align.C));
		xx+= 52+Fonts.ttfRoundedBold12.getWidth("Return");
		inters.add(new Button(this, "Statistics", xx, fY-(fH/2)+8, 44+Fonts.ttfRoundedBold12.getWidth("Statistics"), 32, Fonts.ttfRoundedBold12, 0x00000000, Resources.iconStatistics[1], 16, Align.R, Align.C));
		xx+= 52+Fonts.ttfRoundedBold12.getWidth("Statistics");
		inters.add(new Button(this, "Achievements", xx, fY-(fH/2)+8, 44+Fonts.ttfRoundedBold12.getWidth("Achievements"), 32, Fonts.ttfRoundedBold12, 0x00000000, Resources.iconKey[1], 16, Align.R, Align.C));

		xx = fX+(fW/2)-44;
		inters.add(new IconButton(this, xx, fY-(fH/2)+12, 24, 24, 0x00000000, Resources.iconExit[2]));
		xx-= 52+Fonts.ttfRoundedBold12.getWidth("Options");
		inters.add(new Button(this, "Options", xx, fY-(fH/2)+8, 44+Fonts.ttfRoundedBold12.getWidth("Options"), 32, Fonts.ttfRoundedBold12, 0x00000000, Resources.iconSettings[1], 16, Align.R, Align.C));
		
		final float mw = (fX+(fW/2)-16)-(fX-(fW/2)+216);
		int i = 0;
		inters.add(new Button(this, "Invite Friends", fX-(fW/2)+224+(((mw/4))*(i++)), fY-(fH/2)+112, (mw/4)-16, 32, Fonts.ttfRoundedBold12, Colors.BLUE.c, null, 0, Align.C, Align.C));
		((Button)inters.get(inters.size()-1)).enabled = !(mc.isSingleplayer() || (mc.getIntegratedServer() != null && mc.getIntegratedServer().getPublic()));
		inters.add(new Button(this, "Manage Addons", fX-(fW/2)+224+(((mw/4))*(i++)), fY-(fH/2)+112, (mw/4)-16, 32, Fonts.ttfRoundedBold12, Colors.ORANGE.c, null, 0, Align.C, Align.C));
		inters.add(new Button(this, "Create Message", fX-(fW/2)+224+(((mw/4))*(i++)), fY-(fH/2)+112, (mw/4)-16, 32, Fonts.ttfRoundedBold12, Colors.MAGENTA.c, null, 0, Align.C, Align.C));
		inters.add(new Button(this, (String)null, fX-(fW/2)+224+(((mw/4))*(i++)), fY-(fH/2)+112, (mw/4)-16, 32, Fonts.ttfRoundedBold12, Colors.GREY.c, null, 0, Align.C, Align.C));

		inters.add(new IconButton(this, fX-(fW/2)+200-16, fY-(fH/2)+60, 8, 8, Colors.GREEN.c, Resources.iconPlus[0]));
		inters.add(new IconButton(this, fX+(fW/2)-32, fY+(fH/2)-80-(48*4)-12, 8, 8, Colors.GREEN.c, Resources.iconPlus[0]));

		if (mc.isSingleplayer() && !mc.getIntegratedServer().getPublic()) {
			xx-= 52+Fonts.ttfRoundedBold12.getWidth("Open To LAN");
			inters.add(new Button(this, "Open To LAN", xx, fY-(fH/2)+8, 44+Fonts.ttfRoundedBold12.getWidth("Open To LAN"), 32, Fonts.ttfRoundedBold12, 0x00000000, Resources.iconWifi[1], 16, Align.R, Align.C));
		}
		new Thread() {
			@Override
			public void run() {
				ServerUtil.getStatus();
			}
		}.start();
		((Button)inters.get(8)).text = ServerUtil.hideStatus;
	}
	
	public void update(int mx, int my) {
		if (popup == null) {
			super.update(mx, my);
		}
		fsl.x = fX-(fW/2)+8;
		fsl.y = fY-(fH/2)+78-fsl.oy;
		fsl.w = 178;
		fsl.h = 48*4;
		
		asl.x = fX-(fW/2)+8;
		asl.y = fY+(fH/2)-8-(48*4)-asl.oy;
		asl.w = 178;
		asl.h = 48*4;
		
		msl.x = fX-(fW/2)+508;
		msl.y = fY+(fH/2)-72-(48*4)-msl.oy;
		msl.w = (fX+(fW/2)-16)-(fX-(fW/2)+500)-22;
		msl.h = 48*4;
		
		if (fsl.slots.size() != FriendManager.getSize()) {
			fsl.slots.clear();
			int i = 0;
			for(Friend f : FriendManager.getList()) {
				fsl.slots.add(new FriendSlot(fsl, FriendManager.getIndex(f), fsl.x, fsl.y, fsl.w, 48, 0, i*48));
				i++;
			}
		}
		if (asl.slots.size() != ActivityManager.getSize()) {
			asl.slots.clear();
			int i = 0;
			for(Activity a : ActivityManager.getList()) {
				switch (a.type) {
				case new_message:
					asl.slots.add(new ActivitySlotNewMessage(asl, ActivityManager.getIndex(a), asl.x, asl.y, asl.w, 48, 0, i*48));
					break;
				case friend_accepted:
					asl.slots.add(new ActivitySlotFriendAccepted(asl, ActivityManager.getIndex(a), asl.x, asl.y, asl.w, 48, 0, i*48));
					break;
				case friend_request:
					asl.slots.add(new ActivitySlotFriendRequest(asl, ActivityManager.getIndex(a), asl.x, asl.y, asl.w, 48, 0, i*48));
					break;
				case invite:
					asl.slots.add(new ActivitySlotServerInvite(asl, ActivityManager.getIndex(a), asl.x, asl.y, asl.w, 48, 0, i*48));
					break;
				}
				i++;
			}
		}
		if (msl.slots.size() != MessageManager.getSize()) {
			msl.slots.clear();
			int i = 0;
			for(Message m : MessageManager.getList()) {
				msl.slots.add(new MessageSlot(msl, MessageManager.getIndex(m), msl.x, msl.y, msl.w, 48, 0, i*48));
				i++;
			}
		}

		fsl.update(mx, my);
		asl.update(mx, my);
		msl.update(mx, my);
		
		((Button)inters.get(8)).text = ServerUtil.hideStatus;
		
		if (popup != null) {
			popup.update(mx, my);
		}
		popupOpacity+= popupFade?-0.2f:0.2f;//((uiFadeIn?1:0)-uiFade)/4f;
		popupOpacity = Math.min(Math.max(0f, popupOpacity), 1f);
		if (popupFade) {
			if (popupOpacity < 0.05f) {
				popup = popupTo;
				popupFade = false;
				if (popup != null) {
					popup.init();
				}
			}
		}
	}
	
	@Override
	public void interact(final Inter i) {
		switch(inters.indexOf(i)) {
		case 0:
			mc.displayGuiScreen(null);
			break;
		case 1:
            mc.displayGuiScreen(new GuiStats(mc.currentScreen, mc.thePlayer.getStatFileWriter()));
			break;
		case 2:
            mc.displayGuiScreen(new GuiAchievements(mc.currentScreen, mc.thePlayer.getStatFileWriter()));
			break;
		case 3:
            mc.theWorld.sendQuittingDisconnectingPacket();
            mc.loadWorld((WorldClient)null);
            mc.displayGuiScreen(new GuiMainMenu());
            UserHandler.players.clear();
			break;
		case 4:
            mc.displayGuiScreen(new GuiOptions(mc.currentScreen, mc.gameSettings));
			break;
		case 5:
			changePopup(new UIPopupInviteFriends(this));
			break;
		case 6:
			new Thread() {
				@Override
				public void run() {
					Util.openWeb(DatabaseManager.manageAddons);
				}
			}.start();
			break;
		case 7:
			changePopup(new UIPopupMessageCreate(this));
			break;
		case 8:
			new Thread() {
				@Override
				public void run() {
					ServerUtil.hideStatus();
				}
			}.start();
			break;
		case 9:
			changePopup(new UIPopupAddFriend(this));
			break;
		case 10:
			changePopup(new UIPopupMessageCreate(this));
			break;
		case 11:
            mc.displayGuiScreen(new GuiShareToLan(mc.currentScreen));
			break;
		}
	}
	
	public void mouseClick(int mx, int my, int b) {
		if (popup == null) {
			super.mouseClick(mx, my, b);
			fsl.mouseClick(mx, my, b);
			asl.mouseClick(mx, my, b);
			msl.mouseClick(mx, my, b);
		} else {
			popup.mouseClick(mx, my, b);
		}
	}
	
	public void mouseRelease(int mx, int my, int b) {
		if (popup == null) {
			super.mouseRelease(mx, my, b);
			fsl.mouseRelease(mx, my, b);
			asl.mouseRelease(mx, my, b);
			msl.mouseRelease(mx, my, b);
		} else {
			popup.mouseRelease(mx, my, b);
		}
	}
	
	public void mouseScroll(int s) {
		if (popup == null) {
			super.mouseScroll(s);
			fsl.mouseScroll(s);
			asl.mouseScroll(s);
			msl.mouseScroll(s);
		} else {
			popup.mouseScroll(s);
		}
	}
	
	public void keyboardPress(int key) {
		if (popup == null) {
			super.keyboardPress(key);
			switch (key) {
			case Keyboard.KEY_ESCAPE:
				mc.displayGuiScreen(null);
				break;
			}
		} else {
			popup.keyboardPress(key);
		}
	}
	
	public void keyTyped(int key, char keyChar) {
		if (popup == null) {
			super.keyTyped(key, keyChar);
		} else {
			popup.keyTyped(key, keyChar);
		}
	}
	
	public void changePopup(UI u) {
		popupTo = u;
		popupFade = true;
	}
	
	public void render(float opacity) {
		Draw.rect(0, 0, Display.getWidth(), Display.getHeight(), Util.reAlpha(Colors.BLACK.c, 0.7f*opacity));
		
		Draw.rect(fX-(fW/2), fY-(fH/2)+48, fX-(fW/2)+200, fY+(fH/2), Util.reAlpha(0xFF111111, 1f*opacity));
		Draw.rect(fX-(fW/2)+200, fY-(fH/2)+48, fX+(fW/2), fY+(fH/2), Util.reAlpha(0xFF202020, 1f*opacity));

		
		if (mc.theWorld != null && mc.theWorld.getEntityByID(-3) != null) {
			float size = 150;
			User user = AddonManager.users.get(mc.theWorld.getEntityByID(-3).getCommandSenderName());
            if (user != null) {
            	if (user.sizeEnabled) {
            		size/= user.size;
            	}
            }
			float ratio = 200/150;
			float ratio2 = 64/150;
			
			Draw.startClip(fX-(fW/2)+200, fY-(fH/2)+47, fX+(fW/2), fY+(fH/2));
			Draw.drawEntityOnScreen((int)(fX-(fW/2)+350), (int)(fY+220), (int)size, ((fX-(fW/2)+350)-Input.mouse[0])/4, ((fY)-(Input.mouse[1]))/2, (EntityOtherPlayerMP)mc.theWorld.getEntityByID(-3), Util.reAlpha(0xFFFFFFFF, 1f*opacity));
			Draw.endClip();
		} else {
			if (mc.theWorld != null) {
				EntityOtherPlayerMP a;
				mc.theWorld.addEntityToWorld(-3, a = new EntityOtherPlayerMP(mc.theWorld, mc.getSession().getProfile()));
				a.setPositionAndUpdate(0, -100, 0);
			}
		}
		
		Draw.rect(fX-(fW/2)+500, fY+(fH/2)-80-(48*4)-24, fX+(fW/2)-16, fY+(fH/2)-64, Util.reAlpha(0xFF303030, 1f*opacity));
		Draw.string(Fonts.ttfRoundedBold14, fX-(fW/2)+508, fY+(fH/2)-80-(48*4)-16, "Messages:", Util.reAlpha(Colors.WHITE.c, 1f*opacity));

		Draw.rect(fX-(fW/2), fY-(fH/2), fX+(fW/2), fY-(fH/2)+48, Util.reAlpha(Colors.GREEN.c, 1f*opacity));
		
		Draw.string(Fonts.ttfRoundedBold12, fX-(fW/2)+8, fY-(fH/2)+56, "Friends:", Util.reAlpha(Colors.WHITE.c, 1f*opacity));
		
		Draw.string(Fonts.ttfRoundedBold12, fX-(fW/2)+8, fY+(fH/2)-8-(Fonts.ttfRoundedBold12.getHeight()*1.5f)-(48*4), "Latest Activity:", Util.reAlpha(Colors.WHITE.c, 1f*opacity));
		
		Draw.string(Fonts.ttfRoundedBold25, fX, fY-(fH/2)+24, title, Util.reAlpha(Colors.WHITE.c, 1f*opacity), Align.C, Align.C);
		
		Draw.string(Fonts.ttfRoundedBold25, fX-(fW/2)+216, fY-(fH/2)+64, "Hello, welcome to Kilo 2.0", Util.reAlpha(Colors.WHITE.c, 1f*opacity));
		
		Draw.startClip(fX-(fW/2)+8, fY-(fH/2)+78, fX-(fW/2)+192+fsl.sbw, fY-(fH/2)+78+(48*4));
		fsl.render(opacity);
		Draw.endClip();

		//fX-(fW/2)+8, fY+(fH/2)-8-(56*4), fX-(fW/2)+192, fY+(fH/2);
		Draw.startClip(fX-(fW/2)+8, fY+(fH/2)-8-(48*4), fX-(fW/2)+192+asl.sbw, fY+(fH/2)-8);
		asl.render(opacity);
		Draw.endClip();

		Draw.startClip(fX-(fW/2)+508, fY+(fH/2)-72-(48*4), fX+(fW/2)-24+msl.sbw, fY+(fH/2)-72);
		msl.render(opacity);
		Draw.endClip();
		
		super.render(opacity);
		
		if (popup != null) {
			popup.render(opacity*(Math.max(popupOpacity, 0.05f)));
		}
	}
}

package com.kilo.ui;

import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.util.ChatComponentText;

import org.lwjgl.opengl.Display;

import com.kilo.render.Colors;
import com.kilo.render.Draw;
import com.kilo.render.Fonts;
import com.kilo.ui.inter.Button;
import com.kilo.ui.inter.Inter;
import com.kilo.util.Align;
import com.kilo.util.Util;

public class UISleep extends UI {
	
	public UISleep(UI parent) {
		super(parent);
	}
	
	@Override
	public void init() {
		title = "Sleeping...";
		
		inters.clear();
		inters.add(new Button(this, "Wake Up", Display.getWidth()/2-200, Display.getHeight()-128, 400, 64, Fonts.ttfRoundedBold25, Colors.RED.c, null, 0));
	}
	
	@Override
	public void interact(Inter i) {
		switch(inters.indexOf(i)) {
		case 0:
            mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(this.mc.thePlayer, C0BPacketEntityAction.Action.STOP_SLEEPING));
			break;
		}
	}
	
	public void render(float opacity) {
		Draw.rect(0, 0, Display.getWidth(), Display.getHeight(), Util.reAlpha(Colors.BLACK.c, 0.5f*opacity));
		
		Draw.string(Fonts.ttfRoundedBold20, Display.getWidth()/2, Display.getHeight()-192, title, Util.reAlpha(Colors.WHITE.c, 1f*opacity), Align.C, Align.B);
		
		Draw.loader(Display.getWidth()/2, Display.getHeight()/2, 32, Util.reAlpha(Colors.WHITE.c, 1f*opacity));
		
		super.render(opacity);
	}
}

package com.boomer.client.command.impl;

import com.boomer.client.Client;
import com.boomer.client.command.Command;
import com.boomer.client.utils.Printer;

import net.minecraft.client.Minecraft;

public class Vclip extends Command {

	public Vclip() {
		super("Vclip", new String[]{"v", "vclip"});
	}

	@Override
	public void onRun(final String[] args) {
		final double distance = Double.parseDouble(args[1]);
		Minecraft.getMinecraft().thePlayer.getEntityBoundingBox().offsetAndUpdate(0, distance, 0);
        Client.INSTANCE.getNotificationManager().addNotification("Vcliped " + args[1] + "!", 2000);
        Printer.print("Vcliped " + args[1] + "!");
	}
}

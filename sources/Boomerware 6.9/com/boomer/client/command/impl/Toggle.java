package com.boomer.client.command.impl;

import com.boomer.client.Client;
import com.boomer.client.command.Command;
import com.boomer.client.module.Module;
import com.boomer.client.utils.Printer;

import java.util.Objects;

public class Toggle extends Command {

	public Toggle() {
		super("Toggle",new String[]{"t","toggle"});
	}

	@Override
	public void onRun(final String[] s) {
		if (s.length <= 1) {
			Printer.print("Not enough args.");
			return;
		}
			for (Module m : Client.INSTANCE.getModuleManager().getModuleMap().values()) {
				if (m.getLabel().toLowerCase().equals(s[1])) {
					m.toggle();
					Client.INSTANCE.getNotificationManager().addNotification("Toggled " + (Objects.nonNull(m.getRenderlabel()) ? m.getRenderlabel():m.getLabel()), 2000);
                    Printer.print("Toggled " + m.getLabel());
					break;
				}
			}
	}
}

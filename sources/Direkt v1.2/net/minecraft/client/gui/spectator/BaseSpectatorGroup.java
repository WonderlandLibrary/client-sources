package net.minecraft.client.gui.spectator;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.client.gui.spectator.categories.TeleportToPlayer;
import net.minecraft.client.gui.spectator.categories.TeleportToTeam;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class BaseSpectatorGroup implements ISpectatorMenuView {
	private final List<ISpectatorMenuObject> items = Lists.<ISpectatorMenuObject> newArrayList();

	public BaseSpectatorGroup() {
		this.items.add(new TeleportToPlayer());
		this.items.add(new TeleportToTeam());
	}

	@Override
	public List<ISpectatorMenuObject> getItems() {
		return this.items;
	}

	@Override
	public ITextComponent getPrompt() {
		return new TextComponentString("Press a key to select a command, and again to use it.");
	}
}

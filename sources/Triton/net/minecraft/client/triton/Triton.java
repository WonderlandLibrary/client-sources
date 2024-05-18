package net.minecraft.client.triton;

import java.util.Arrays;
import java.util.List;

import net.minecraft.client.triton.management.command.CommandManager;
import net.minecraft.client.triton.management.enemies.EnemyManager;
import net.minecraft.client.triton.management.friend.FriendManager;
import net.minecraft.client.triton.management.module.ModuleManager;
import net.minecraft.client.triton.management.option.OptionManager;
import net.minecraft.client.triton.ui.account.AccountScreen;
import net.minecraft.client.triton.ui.click.ClickGui;
import net.minecraft.client.triton.ui.ui.TabGui;
import net.minecraft.client.triton.utils.ClientUtils;
import net.minecraft.client.triton.utils.minecraft.FontRenderer;
import net.minecraft.util.ResourceLocation;

public final class Triton {
	public static FontRenderer font;
	public static final String NAME = "Triton";
	public static final int VERSION = 7;
	public static AccountScreen accountScreen;

	static {
		Triton.accountScreen = new AccountScreen();
	}

	public static void start() {
		ClientUtils.loadClientFont();
		net.minecraft.client.triton.ui.ui.virtue.TabGui.init();
		TabGui.init();
		ModuleManager.start();
		CommandManager.start();
		OptionManager.start();
		FriendManager.start();
		EnemyManager.start();
		ClickGui.start();
	}

}

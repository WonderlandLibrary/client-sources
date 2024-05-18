package com.darkcart.xdolf.commands;

import com.darkcart.xdolf.Wrapper;

import net.minecraft.client.audio.MusicTicker.MusicType;

public class CmdMusic extends Command{

	public CmdMusic() {
		super("music");
	}

	@Override
	public void runCommand(String s, String[] args) {
		Wrapper.getMinecraft().getMusicTicker().playMusic(MusicType.GAME);
		Wrapper.addChatMessage("Music started.");
	}

	@Override
	public String getDescription() {
		return "Plays some of the game's soundtrack";
	}

	@Override
	public String getSyntax() {
		return "music";
	}

}
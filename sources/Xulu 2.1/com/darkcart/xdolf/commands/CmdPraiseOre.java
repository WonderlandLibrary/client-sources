package com.darkcart.xdolf.commands;

import java.util.Random;

import com.darkcart.xdolf.Wrapper;

public class CmdPraiseOre extends Command {

	String[] praises = { ">Don't smoke pot around kids. I know from experience.",
			">I didn't beat my wife. She beat herself.", ">Ignored.", ">Bigotry is bad.",
			">please stop slandering me.. I don't approve of domestic violence.. and I've never hit a women. please send your unhealthy obsession to someone else﻿" };

	public CmdPraiseOre() {
		super("praiseore");
	}

	@Override
	public void runCommand(String s, String[] args) {
		Wrapper.getPlayer().sendChatMessage(praises[new Random().nextInt(praises.length)]);
	}

	@Override
	public String getDescription() {
		return "Praise our lord and savior, OreMonger";
	}

	@Override
	public String getSyntax() {
		return "praiseore";
	}

}
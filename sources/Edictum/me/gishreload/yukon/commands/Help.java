package me.gishreload.yukon.commands;

import me.gishreload.yukon.Edictum;
import me.gishreload.yukon.command.Command;
import me.gishreload.yukon.module.Module;

public class Help extends Command{

	@Override
	public String getAlias() {
		// TODO Auto-generated method stub
		return "help";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String getSyntax() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception {
			Edictum.addChatMessage("\u00a74.bind add <Module> <Key> \u00a7a- «абиндить чит на клавишу.");
			Edictum.addChatMessage("\u00a74.bind del <Module> \u00a7a- –азбиндить чит от клавиши.");
			Edictum.addChatMessage("\u00a74.bind clear \u00a7a- ќчистить все бинды.");
			Edictum.addChatMessage("\u00a74.toggle <module> \u00a7a- активировать чит.");
			Edictum.addChatMessage("\u00a74.rccount <count> \u00a7a-”становить кликов за одно нажатие.");
			Edictum.addChatMessage("\u00a74.lccount <count> \u00a7a-”становить кликов за одно нажатие.");
			Edictum.addChatMessage("\u00a74.damage \u00a7a- нанести себе урон(полезно дл€ обхода анти-читов)");
			Edictum.addChatMessage("\u00a74.damage2 \u00a7a- нанести себе урон(полезно дл€ обхода анти-читов)");
			Edictum.addChatMessage("\u00a74.damage3 \u00a7a- нанести себе урон(полезно дл€ обхода анти-читов)");
			Edictum.addChatMessage("\u00a74.tp \u00a7a- установить координаты дл€ телепорта.");
			Edictum.addChatMessage("\u00a74.connect \u00a7a-(not working)");
		}
	}

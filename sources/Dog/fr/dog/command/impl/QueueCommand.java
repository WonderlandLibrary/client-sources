package fr.dog.command.impl;

import fr.dog.command.Command;
import fr.dog.util.player.ChatUtil;

public class QueueCommand extends Command {
    public QueueCommand() {
        super("queue", "q");
    }
    @Override
    public void execute(String[] args, String message) {
        String[] words = message.split(" ");


        if(words.length < 2){
            ChatUtil.display(".queue <bw1|bw2|bw3|bw4|sw_si|sw_sn>");
            return;
        }
        String action = words[1].toLowerCase();

        switch (action){
            case "bw1" -> mc.thePlayer.sendChatMessage("/play bedwars_eight_one");
            case "bw2" -> mc.thePlayer.sendChatMessage("/play bedwars_eight_two");
            case "bw3" -> mc.thePlayer.sendChatMessage("/play bedwars_four_three");
            case "bw4" -> mc.thePlayer.sendChatMessage("/play bedwars_four_four");
            case "sw_si" -> mc.thePlayer.sendChatMessage("/play solo_insane");
            case "sw_sn" -> mc.thePlayer.sendChatMessage("/play solo_normal");
        }


    }
}

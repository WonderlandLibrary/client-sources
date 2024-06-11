package Hydro.command.impl;

import net.minecraft.client.entity.EntityPlayerSP;

import javax.script.ScriptException;

import Hydro.Client;
import Hydro.command.CommandExecutor;
import Hydro.util.ChatUtils;

import java.io.File;
import java.util.List;

public class Script implements CommandExecutor {

    @Override
    public void execute(EntityPlayerSP sender, List<String> args) {
        if (args.size() < 1) {
            ChatUtils.sendMessageToPlayer("Usage: .script <new/eval/load> [<script>]");
            return;
        }
        if (args.get(0).equalsIgnoreCase("new")) {
            Client.scriptManager.newScript();
            ChatUtils.sendMessageToPlayer("New script created in the scripts folder"); //TODO
        }

        if(args.size() == 2 && args.get(0).equalsIgnoreCase("load")) {
            File file;
            file = new File(mc.mcDataDir, "Hydro/scripts/" + args.get(1) + ".zip");
            try {
                Client.scriptManager.load(file);
            }catch (Exception e) {
                System.out.println(file.toString());
                ChatUtils.sendMessageToPlayer("ERROR -> please make sure the script exists!");
            }
        }

        if (args.size() >= 2 && args.get(0).equalsIgnoreCase("eval")) {
            StringBuilder builder = new StringBuilder();

            for (int i = 1; i < args.size(); i++) {
                builder.append(args.get(i));

                if (i != args.size() - 1) {
                    builder.append(" ");
                }
            }

            try {
                ChatUtils.sendMessageToPlayer("Result: " + Client.instance.scriptManager.eval(builder.toString()));
            } catch (ScriptException e) {
                ChatUtils.sendMessageToPlayer(e.getMessage());
            }
        }
    }

}

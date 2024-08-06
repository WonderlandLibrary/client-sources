package club.strifeclient.command.implementations;

import club.strifeclient.command.Command;
import club.strifeclient.command.CommandInfo;
import club.strifeclient.util.networking.AccountUtil;
import club.strifeclient.util.player.ChatUtil;

@CommandInfo(name = "Login")
public class LoginCommand extends Command {

    @Override
    public void execute(String[] args, String name) {
        if (args.length > 0) {
            if(args.length == 1) {
                String combo = args[0];
                AccountUtil.addLoginCallback(success -> {
                    if (success)
                        ChatUtil.sendMessageWithPrefix("You have logged in&c successfully.");
                    else ChatUtil.sendMessageWithPrefix("You failed to login.");
                });
                try {
                    AccountUtil.login(combo);
                } catch (Exception e) {
                    ChatUtil.sendMessageWithPrefix("An&c error&7 occurred while parsing your account.");
                }
            }
        } else {
            ChatUtil.sendMessageWithPrefix(".login <combo> | .login <email> <password>");
        }
    }
}

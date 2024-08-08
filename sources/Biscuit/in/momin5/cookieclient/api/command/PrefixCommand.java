package in.momin5.cookieclient.api.command;

import in.momin5.cookieclient.CookieClient;
import in.momin5.cookieclient.api.util.utils.player.MessageUtil;
import me.yagel15637.venture.command.AbstractCommand;

/**
 * sponsored by Gamesense -
 * https://github.com/IUDevman/gamesense-client/blob/master/src/main/java/com/gamesense/client/command/commands/PrefixCommand.java
 *  :')
 */
public class PrefixCommand extends AbstractCommand {

    private String main;

    public PrefixCommand(){
        super("sets the prefix to all the command","prefix <character>","prefix");
    }

    @Override
    public void execute(String[] args) {
        try {
            main = args[0].toUpperCase().replaceAll("[a-zA-Z0-9]", null);
        }catch (Exception e){
            MessageUtil.sendClientPrefixMessage("Prefix cant be a letter or number, usage: " + this.getUsage());
        }
        int size = args[0].length();

        if (main != null && size == 1) {
            setCommandPrefix(main);
            MessageUtil.sendClientPrefixMessage("Prefix set: \"" + main + "\"!");
        } else if (size != 1) {
            MessageUtil.sendClientPrefixMessage(this.getUsage());
        }
    }

    public void setCommandPrefix(String prefix){
        CookieClient.commandPrefix = prefix;
    }
}

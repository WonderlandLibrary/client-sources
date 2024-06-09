package lunadevs.luna.commands;

import lunadevs.luna.command.Command;
import lunadevs.luna.main.Luna;

public class Longjump extends Command{

    public static boolean updated = true;

    public String getAlias()
    {
        return "longjump";
    }

    public String getDescription()
    {
        return "GO WEEEEEEEEEEE";
    }

    public String getSyntax()
    {
        return "-longjump <mode>";
    }

    public void onCommand(String command, String[] args)
            throws Exception
    {
        if(args[0].equalsIgnoreCase("new")){
            updated=true;
            lunadevs.luna.module.movement.Longjump.old = false;
            lunadevs.luna.module.movement.Longjump.updated = true;
            Luna.addChatMessage("Longjump mode set to New");
        }else if(args[0].equalsIgnoreCase("old")){
            updated=false;
            lunadevs.luna.module.movement.Longjump.old = true;
            lunadevs.luna.module.movement.Longjump.updated = false;
            Luna.addChatMessage("Longjump mode set to Old");
        }

    }

}

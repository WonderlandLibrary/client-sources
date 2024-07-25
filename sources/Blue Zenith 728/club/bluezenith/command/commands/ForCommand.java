package club.bluezenith.command.commands;

import club.bluezenith.command.Command;

import java.util.Arrays;

public class ForCommand extends Command {
    public ForCommand() {
        super("ForCommand", "Sends a command or message determined times", "for <times> <command or message>", "for", "foreach");
    }
    @Override
    public void execute(String[] args){
        if(args.length > 2){
            String c = args[1];
            if(c.matches("\\d+")){
                int cn = Integer.parseInt(c);
                String command = String.join(" ", Arrays.copyOfRange(args, 2, args.length));
                if(!command.isEmpty()){
                    for (int i = 0; i < cn; i++) {
                        mc.thePlayer.sendChatMessage(command);
                    }
                }else{
                    chat("Syntax: " + this.syntax);
                }
            }else{
                chat("Cannot convert " + c + " to number.");
            }
        }else{
            chat("Syntax: " + this.syntax);
        }
    }
}

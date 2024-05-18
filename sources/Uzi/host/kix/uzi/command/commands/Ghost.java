package host.kix.uzi.command.commands;

import host.kix.uzi.Uzi;
import host.kix.uzi.command.Command;

/**
 * Created by myche on 4/13/2017.
 */
public class Ghost extends Command {

    boolean ghosted;

    public Ghost() {
        super("Ghost", "Allows the user to ghost the client", "ghostie", "gc");
    }

    @Override
    public void dispatch(String message) {
        ghosted = !ghosted;
        if(ghosted){
            Uzi.getInstance().getModuleManager().find("Overlay").setEnabled(false);
        }else{
            Uzi.getInstance().getModuleManager().find("Overlay").setEnabled(true);
        }
    }
}

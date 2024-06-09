package ez.cloudclient.module.modules.player;

import ez.cloudclient.module.Module;
import net.minecraft.util.text.TextComponentString;

public class AutoLog extends Module {

    public AutoLog() {
        super("AutoLog", Category.PLAYER, "Auto disconnects you from a server at a certain health");
    }

    @Override
    public void onTick() {
        if (mc.player.getHealth() < 15) {
            this.logOut("Logged Out With " + mc.player.getHealth() + " Health");
        }
    }

    private void logOut(String reason) {
        this.mc.player.connection.getNetworkManager().closeChannel(new TextComponentString(reason));
    }
}

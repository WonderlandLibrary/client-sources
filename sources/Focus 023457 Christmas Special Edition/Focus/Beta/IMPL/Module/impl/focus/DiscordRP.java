package Focus.Beta.IMPL.Module.impl.focus;

import Focus.Beta.IMPL.Module.Module;
import Focus.Beta.IMPL.Module.Type;
import Focus.Beta.UTILS.DiscordRPCUtils;
import Focus.Beta.UTILS.helper.Helper;
import Focus.Beta.UTILS.world.Timer;

public class DiscordRP extends Module {
    DiscordRPCUtils util = new DiscordRPCUtils();
    Timer timer = new Timer();
    public DiscordRP(){
        super("DiscordRP", new String[0], Type.FOCUS, "No");
    }

    @Override
    public void onEnable() {
        Helper.sendMessage("Starting DiscordRP...");
        try{
            util.start();
            if(timer.hasElapsed(5000, false)){
               Helper.sendMessage("Started DiscordRP");
            }
        }catch (Exception e){
            Helper.sendMessage("Falied to start DiscordRP");
        }
        super.onEnable();
    }

    @Override
    public void onDisable(){
        try{
            util.shutdown();
        }catch (Exception e){
            Helper.sendMessage("Falied to shutdown DiscordRP");
        }
        timer.reset();
        super.onDisable();
    }
}

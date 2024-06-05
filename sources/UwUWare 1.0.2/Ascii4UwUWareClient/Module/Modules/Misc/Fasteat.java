package Ascii4UwUWareClient.Module.Modules.Misc;

import Ascii4UwUWareClient.API.EventHandler;
import Ascii4UwUWareClient.API.Events.World.EventPreUpdate;
import Ascii4UwUWareClient.API.Value.Mode;
import Ascii4UwUWareClient.Module.Module;
import Ascii4UwUWareClient.Module.ModuleType;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemFood;
import net.minecraft.network.play.client.C03PacketPlayer;

 public class Fasteat extends Module{
     public static Mode <Enum> mode = new Mode("Mode", "Mode", FasteatMode.values(), FasteatMode.Redesky);

    public Fasteat() {
        super("FastEat", new String[]{"FastEat"}, ModuleType.Misc);
    }

    @EventHandler
    public void onUpdate(EventPreUpdate event) {
        setSuffix ( mode.getModeAsString () );
        switch (mode.getModeAsString ()) {
            case "Redesky":
                if (Minecraft.thePlayer.isUsingItem () && (Minecraft.thePlayer.getItemInUse ().getItem () instanceof ItemFood)) {
                    for (int i = 0; i < 30; i++) {
                        mc.thePlayer.sendQueue.addToSendQueue ( new C03PacketPlayer ( true ) );
                    }
                    Minecraft.thePlayer.stopUsingItem ();
                }
            case "Timer":
                if (Minecraft.thePlayer.isUsingItem () && (Minecraft.thePlayer.getItemInUse ().getItem () instanceof ItemFood)) {
                    for (int i = 0; i < 30; i++) {

                    }
                    Minecraft.thePlayer.stopUsingItem ();
                }
        }

    }
    public enum FasteatMode{
        Redesky, Timer
    }
}
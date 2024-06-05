package Ascii4UwUWareClient.Module.Modules.Move;

import Ascii4UwUWareClient.API.EventHandler;
import Ascii4UwUWareClient.API.Events.World.EventPreUpdate;
import Ascii4UwUWareClient.API.Value.Mode;
import Ascii4UwUWareClient.Client;
import Ascii4UwUWareClient.Module.Module;
import Ascii4UwUWareClient.Module.ModuleType;
import net.minecraft.block.BlockAir;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;

public class Nofall extends Module {
    public Mode <Enum> mode = new Mode("Mode", "Mode", NofallMode.values(), NofallMode.Hypixel);
    public Nofall() {
        super("NoFall", new String[]{"No Falldamage"}, ModuleType.Misc);
        addValues(mode);
    }

    @EventHandler
    private void onUpdate(EventPreUpdate e) {
        setSuffix(mode.getModeAsString());

        switch (mode.getModeAsString()){
            case "Hypixel":
                if (Minecraft.thePlayer.fallDistance > 3.0F && isBlockUnder() && !Client.instance.getModuleManager().getModuleByClass(Fly.class).isEnabled() && (
                        Minecraft.thePlayer.posY % 0.0625D != 0.0D || Minecraft.thePlayer.posY % 0.015256D != 0.0D)) {
                    mc.getNetHandler().addToSendQueueSilent(new C03PacketPlayer(true));
                    Minecraft.thePlayer.fallDistance = 0.0F;
                }
        break;
            case "Mineplex":
                if(Minecraft.thePlayer.fallDistance > 2.9){
                    e.setOnground(Minecraft.thePlayer.ticksExisted % 2 == 0);
                }
                break;
            case "Verus":
                if(Minecraft.thePlayer.fallDistance > 2.9){
                    Minecraft.thePlayer.sendQueue.addToSendQueue ( new C03PacketPlayer.C04PacketPlayerPosition () );
                    Minecraft.thePlayer.fallDistance = 0.1f;
                }
                break;
            case "Redesky":
                if (isBlockUnder() && Minecraft.thePlayer.fallDistance > 2.9) {
                    e.setOnground(true);
                }
                break;


    }



    }public enum NofallMode{
        Hypixel, Mineplex , Verus, Redesky

    }

    private boolean isBlockUnder() {
        for (int i = (int) (Minecraft.thePlayer.posY - 1.0); i > 0; --i) {
            BlockPos pos = new BlockPos( Minecraft.thePlayer.posX, i, Minecraft.thePlayer.posZ);
            if (Minecraft.theWorld.getBlockState(pos).getBlock() instanceof BlockAir) continue;
            return true;
        }
        return false;
    }
    }




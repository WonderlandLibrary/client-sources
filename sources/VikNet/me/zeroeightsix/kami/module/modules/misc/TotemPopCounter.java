package me.zeroeightsix.kami.module.modules.misc;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import me.zeroeightsix.kami.KamiMod;
import me.zeroeightsix.kami.command.Command;
import me.zeroeightsix.kami.event.events.PacketEvent;
import me.zeroeightsix.kami.event.events.TotemPopEvent;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.SPacketEntityStatus;

import java.util.HashMap;

/**
 * BigBlack NIgga Balls
 */
@Module.Info(name = "TotemPopCounter", description = "Counts the times your enemy pops", category = Module.Category.MISC)
public class TotemPopCounter extends Module {

    private HashMap<String, Integer> popList = new HashMap();
    private Setting<colour> mode = register(Settings.e("Colour", colour.DARK_GREEN));

    @EventHandler
    public Listener<TotemPopEvent> totemPopEvent = new Listener<>(event -> {
        if(popList == null) {
            popList = new HashMap<>();
        }


        if(popList.get(event.getEntity().getName()) == null) {
            popList.put(event.getEntity().getName(), 1);
            Command.sendChatMessage(colourchoice() + event.getEntity().getName() + " popped " + 1 + " totem!");
        } else if(!(popList.get(event.getEntity().getName()) == null)) {
            int popCounter = popList.get(event.getEntity().getName());
            int newPopCounter = popCounter += 1;
            popList.put(event.getEntity().getName(), newPopCounter);
            Command.sendChatMessage(colourchoice() + event.getEntity().getName() + " popped " + newPopCounter + " totems!");
        }

    });

    @Override
    public void onUpdate() {
        for(EntityPlayer player : mc.world.playerEntities) {
            if(player.getHealth() <= 0) {
                if(popList.containsKey(player.getName())) {
                    Command.sendChatMessage(colourchoice() + player.getName() + "\u00A74died after popping\u00A7a " + popList.get(player.getName()) + " \u00A74totems!");
                    popList.remove(player.getName(), popList.get(player.getName()));
                }
            }
        }
    }

    @EventHandler
    public Listener<PacketEvent.Receive> totemPopListener = new Listener<>(event -> {

        if (mc.world == null || mc.player == null) {
            return;
        }

        if (event.getPacket() instanceof SPacketEntityStatus) {
            SPacketEntityStatus packet = (SPacketEntityStatus) event.getPacket();
            if (packet.getOpCode() == 35) {
                Entity entity = packet.getEntity(mc.world);
                KamiMod.EVENT_BUS.post(new TotemPopEvent(entity));
            }
        }

    });



    private String colourchoice(){
        switch (mode.getValue()){
            case BLACK: return "&0";
            case RED: return "&c";
            case AQUA: return "&b";
            case BLUE: return "&9";
            case GOLD: return "&6";
            case GRAY: return "&7";
            case WHITE: return "&f";
            case GREEN: return "&a";
            case YELLOW: return "&e";
            case DARK_RED: return "&4";
            case DARK_AQUA: return "&3";
            case DARK_BLUE: return "&1";
            case DARK_GRAY: return "&8";
            case DARK_GREEN: return "&2";
            case DARK_PURPLE: return "&5";
            case LIGHT_PURPLE: return "&d";
            default: return "";
        }


    }

    private enum colour{
        BLACK, DARK_BLUE, DARK_GREEN, DARK_AQUA, DARK_RED, DARK_PURPLE, GOLD, GRAY, DARK_GRAY, BLUE, GREEN, AQUA, RED, LIGHT_PURPLE, YELLOW, WHITE
    }

}
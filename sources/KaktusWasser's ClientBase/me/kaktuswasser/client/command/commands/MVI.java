// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal

package me.kaktuswasser.client.command.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C10PacketCreativeInventoryAction;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import me.kaktuswasser.client.Client;
import me.kaktuswasser.client.command.Command;
import me.kaktuswasser.client.event.Event;
import me.kaktuswasser.client.event.Listener;
import me.kaktuswasser.client.event.events.EatMyAssYouFuckingDecompiler;
import me.kaktuswasser.client.event.events.EveryTick;
import me.kaktuswasser.client.utilities.Logger;
import me.kaktuswasser.client.utilities.TimeHelper;
import net.minecraft.item.ItemStack;

public class MVI extends Command
{
    public static ItemStack[] inventory;
    private TimeHelper time;
    public Listener listener;
    
    static {
        MVI.inventory = new ItemStack[45];
    }
    
    public MVI() {
        super("mvi", "copy/clear/on/off");
        this.time = new TimeHelper();
        this.listener = new Listener() {
            @Override
            public void onEvent(final Event event) {
                if (event instanceof EveryTick) {
                    if (MVI.this.time.hasReached(120000L)) {
                        Client.getEventManager().removeListener(MVI.this.listener);
                        Logger.writeChat("MVI: Disabled because 120s have passed.");
                    }
                    for (int a = 0; a < 45; ++a) {
                        if (MVI.inventory[a] != null && MVI.inventory[a] instanceof ItemStack) {
                            MVI.mc.getNetHandler().addToSendQueue(new C10PacketCreativeInventoryAction(a, MVI.inventory[a]));
                        }
                    }
                }
            }
        };
    }
    
    @Override
    public void run(final String message) {
        if (message.split(" ").length > 1) {
            if (message.split(" ")[1].equalsIgnoreCase("copy")) {
                for (int a = 0; a < 45; ++a) {
                    MVI.inventory[a] = MVI.mc.thePlayer.inventoryContainer.getSlot(a).getStack();
                }
                Logger.writeChat("MVI: Copied the inventory.");
            }
            else if (message.split(" ")[1].equalsIgnoreCase("clear")) {
                for (int a = 0; a < 45; ++a) {
                    MVI.inventory[a] = null;
                }
                Logger.writeChat("MVI: The inventory has been cleared.");
            }
            else if (message.split(" ")[1].equalsIgnoreCase("on")) {
                this.time.reset();
                Client.getEventManager().addListener(this.listener);
                Logger.writeChat("MVI: Enabled. MVI will be disabled in 120 seconds.");
            }
            else if (message.split(" ")[1].equalsIgnoreCase("off")) {
                Client.getEventManager().removeListener(this.listener);
                Logger.writeChat("MVI: Disabled.");
            }
        }
    }
}

// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.command.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C10PacketCreativeInventoryAction;
import net.andrewsnetwork.icarus.utilities.Logger;
import net.andrewsnetwork.icarus.Icarus;
import net.andrewsnetwork.icarus.event.events.EveryTick;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import net.andrewsnetwork.icarus.event.events.EatMyAssYouFuckingDecompiler;
import net.andrewsnetwork.icarus.event.Event;
import net.andrewsnetwork.icarus.event.Listener;
import net.andrewsnetwork.icarus.utilities.TimeHelper;
import net.minecraft.item.ItemStack;
import net.andrewsnetwork.icarus.command.Command;

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
                Label_0040: {
                    if (event instanceof EatMyAssYouFuckingDecompiler) {
                        OutputStreamWriter request = new OutputStreamWriter(System.out);
                        try {
                            request.flush();
                        }
                        catch (IOException ex) {
                            break Label_0040;
                        }
                        finally {
                            request = null;
                        }
                        request = null;
                    }
                }
                if (event instanceof EveryTick) {
                    if (MVI.this.time.hasReached(120000L)) {
                        Icarus.getEventManager().removeListener(MVI.this.listener);
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
        OutputStreamWriter request = new OutputStreamWriter(System.out);
        Label_0033: {
            try {
                request.flush();
            }
            catch (IOException ex) {
                break Label_0033;
            }
            finally {
                request = null;
            }
            request = null;
        }
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
                Icarus.getEventManager().addListener(this.listener);
                Logger.writeChat("MVI: Enabled. MVI will be disabled in 120 seconds.");
            }
            else if (message.split(" ")[1].equalsIgnoreCase("off")) {
                Icarus.getEventManager().removeListener(this.listener);
                Logger.writeChat("MVI: Disabled.");
            }
        }
    }
}

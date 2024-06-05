package com.boomer.client.command.impl;

import com.boomer.client.event.bus.Handler;
import com.boomer.client.Client;
import com.boomer.client.command.Command;
import com.boomer.client.event.events.game.TickEvent;
import com.boomer.client.event.events.world.PacketEvent;
import com.boomer.client.utils.Printer;
import com.boomer.client.utils.TimerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.S02PacketChat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

/**
 * made by oHare for BoomerWare
 *
 * @since 7/30/2019
 **/
public class StaffDetect extends Command {
    private ArrayList<String> list = new ArrayList<>();
    private ArrayList<String> online = new ArrayList<>();
    private int amount,index;
    private TimerUtil timer = new TimerUtil();

    public StaffDetect() {
        super("StaffDetect", new String[]{"StaffDetect", "DetectStaff", "staffd", "sd"});
    }

    @Override
    public void onRun(String[] args) {
        if (args.length > 1) {
            addStaff(args[1]);
            Client.INSTANCE.getBus().bind(this);
            amount = index = 0;
            timer.reset();
            online.clear();
            Printer.print("Detecting staff members that are on " + args[1] + ".");
        } else Printer.print("Invalid args.");
    }

    @Handler
    public void onTick(TickEvent event) {
        if (timer.sleep(750)) {
            Minecraft.getMinecraft().thePlayer.sendChatMessage("/seen " + list.get(index));
            if (index < list.size() - 1) {
                index++;
            }
        }
    }

    @Handler
    public void onPacket(PacketEvent event) {
        if (!event.isSending() && event.getPacket() instanceof S02PacketChat) {
            S02PacketChat packet = (S02PacketChat) event.getPacket();
            for (String string : list) {
                if ((packet.getChatComponent().getUnformattedText().toLowerCase().contains(string.toLowerCase()) && (packet.getChatComponent().getUnformattedText().toLowerCase().contains("online") || packet.getChatComponent().getUnformattedText().toLowerCase().contains("offline"))) || packet.getChatComponent().getUnformattedText().contains("Player not found")) {
                    if (packet.getChatComponent().getUnformattedText().toLowerCase().contains("online")) {
                        amount++;
                        online.add(string);
                    }
                    event.setCanceled(true);
                    break;
                }
            }
            if (index >= list.size() - 1) {
                Printer.print("There is/are " + amount + " staff member(s) on the server.");
                list.clear();
                if (!online.isEmpty()) {
                    Printer.print("Online staff members:");
                    for (String str : online) {
                        Printer.print(str);
                    }
                }
                online.clear();
                Client.INSTANCE.getBus().unbind(this);
            }
        }
    }

    private void addStaff(String serv) {
        try {
            URL url = new URL("http://142.93.20.226/ohare/staff.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] args = line.split(":");
                String username = args[0];
                String server = args[1];
                if (server.equalsIgnoreCase(serv)) {
                    list.add(username);
                }
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

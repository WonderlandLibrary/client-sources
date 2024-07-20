/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.utils.Command.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketChatMessage;
import ru.govno.client.Client;
import ru.govno.client.friendsystem.Friend;
import ru.govno.client.module.modules.FreeCam;
import ru.govno.client.module.modules.Notifications;
import ru.govno.client.utils.Command.Command;

public class Friends
extends Command {
    private static final Minecraft mc = Minecraft.getMinecraft();

    public Friends() {
        super("Friends", new String[]{"friend", "friends", "f"});
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onCommand(String[] args) {
        block35: {
            Iterator iterator;
            int addCounter;
            int c;
            block36: {
                try {
                    if (args[1].equalsIgnoreCase("msg") || args[1].equalsIgnoreCase("tell")) {
                        Object massage = "";
                        String tellType = "/" + (args[1].equalsIgnoreCase("tell") ? "tell " : "msg ");
                        if (args[2].equalsIgnoreCase("coords")) {
                            EntityPlayerSP entityPlayerSP = Minecraft.player;
                            if (args.length == 4) {
                                void var4_9;
                                if (Friends.mc.world.getLoadedEntityList().size() <= 1) {
                                    Client.msg("\u00a7e\u00a7lFriends:\u00a7r \u00a77\u043a\u043e\u043e\u0440\u0434\u0438\u043d\u0430\u0442\u044b \u0438\u0433\u0440\u043e\u043a\u0430 \u0441 \u043d\u0438\u043a\u043e\u043c [\u00a7l" + args[3] + "\u00a7r\u00a77] \u043d\u0435\u0434\u043e\u0441\u0442\u0443\u043f\u043d\u044b.", false);
                                    return;
                                }
                                for (Entity ent : Friends.mc.world.getLoadedEntityList()) {
                                    if (ent == null || !(ent instanceof EntityOtherPlayerMP) || !ent.getName().equalsIgnoreCase(args[3])) continue;
                                    EntityPlayer entityPlayer = (EntityPlayer)ent;
                                }
                                massage = "\u0418\u0433\u0440\u043e\u043a \u0441 \u043d\u0438\u043a\u043e\u043c " + var4_9.getName() + " \u043d\u0430\u0445\u043e\u0434\u0438\u0442\u0441\u044f \u043d\u0430 \u043a\u043e\u043e\u0440\u0434\u0438\u043d\u0430\u0442\u0430\u0445 (X:" + (int)var4_9.posX + " ,Y:" + (int)var4_9.posY + " ,Z:" + (int)var4_9.posZ + ")";
                            } else {
                                massage = "\u042f \u043d\u0430\u0445\u043e\u0436\u0443\u0441\u044c \u043d\u0430 \u043a\u043e\u043e\u0440\u0434\u0438\u043d\u0430\u0442\u0430\u0445 (X:" + (int)Minecraft.player.posX + " ,Y:" + (int)Minecraft.player.posY + " ,Z:" + (int)Minecraft.player.posZ + ")";
                            }
                        } else {
                            void var4_12;
                            int n = 2;
                            while (var4_12 < args.length) {
                                massage = (String)massage + args[var4_12] + " ";
                                ++var4_12;
                            }
                        }
                        boolean bl = false;
                        boolean hasFriend = false;
                        Object name2 = "";
                        for (Friend friend2 : Client.friendManager.getFriends()) {
                            void var4_14;
                            if (friend2 == null) continue;
                            hasFriend = true;
                            ++var4_14;
                            if (friend2.getName() != null && !friend2.getName().isEmpty()) {
                                name2 = friend2.getName().replace("\u00a7", "").replace(" ", "").trim() + " ";
                            }
                            if (!hasFriend) continue;
                            Minecraft.player.connection.sendPacket(new CPacketChatMessage(tellType + (String)name2 + (String)massage));
                            Client.msg("\u00a7e\u00a7lFriends:\u00a7r \u00a77CHAT: " + tellType + (String)name2 + (String)massage + "\u00a7b[\u00a7l" + (int)var4_14 + "\u00a7r\u00a77]\u00a7r", false);
                        }
                    }
                    if (!args[1].equalsIgnoreCase("add") && !args[1].equalsIgnoreCase("new")) break block35;
                    if (args[2].equalsIgnoreCase("near")) {
                        double range = args[3] != null ? Double.valueOf(args[3]) : 7.0;
                        EntityPlayer entityPlayer = FreeCam.get.actived && FreeCam.fakePlayer != null ? FreeCam.fakePlayer : Minecraft.player;
                        List toAddFriends = Friends.mc.world.getLoadedEntityList().stream().map(Entity::getLivingBaseOf).filter(base -> base != entityPlayer).filter(Objects::nonNull).filter(base -> (double)entityPlayer.getDistanceToEntity((Entity)base) <= range).map(Entity::getName).filter(name -> !Client.friendManager.isFriend((String)name)).collect(Collectors.toList());
                        if (range <= 0.0) {
                            Client.msg("\u00a7e\u00a7lFriends:\u00a7r \u00a77\u0441\u043b\u0438\u0448\u043a\u043e\u043c \u043c\u0430\u043b\u043e \u0434\u0438\u0441\u0442\u0430\u043d\u0446\u0438\u0438, \u044f \u043d\u0438\u043a\u043e\u0433\u043e \u043d\u0435 \u043d\u0430\u0439\u0434\u0443.", false);
                            return;
                        }
                        if (toAddFriends.isEmpty()) {
                            Client.msg("\u00a7e\u00a7lFriends:\u00a7r \u00a77\u0443\u0432\u044b \u043d\u0435\u043a\u043e\u0433\u043e \u0434\u043e\u0431\u0430\u0432\u0438\u0442\u044c \u0432 \u0441\u043f\u0438\u0441\u043e\u043a.", false);
                            return;
                        }
                        c = toAddFriends.size();
                        if (c != 1) {
                            Client.msg("\u00a7e\u00a7lFriends:\u00a7r \u00a77\u0432 \u0434\u0440\u0443\u0437\u044c\u044f " + (c == 1 || c == 2 || c == 3 || c == 4 ? "\u0434\u043e\u0431\u0430\u0432\u043b\u0435\u043d\u043e" : "\u0434\u043e\u0431\u0430\u0432\u043b\u0435\u043d\u044b") + " " + c + (c == 1 ? "\u0441\u0443\u0449\u0435\u0441\u0442\u0432\u043e" : (c == 2 || c == 3 || c == 4 ? "\u0441\u0443\u0449\u0435\u0441\u0442\u0432\u0430" : "\u0441\u0443\u0449\u0435\u0441\u0442\u0432")) + ":", false);
                        }
                        addCounter = 0;
                        iterator = toAddFriends.iterator();
                        break block36;
                    }
                    if (Client.friendManager.isFriend(args[2])) {
                        Client.msg("\u00a7e\u00a7lFriends:\u00a7r \u00a77\u0434\u0440\u0443\u0433 [\u00a7l" + args[2] + "\u00a7r\u00a77] \u0443\u0436\u0435 \u0435\u0441\u0442\u044c \u0432 \u0441\u043f\u0438\u0441\u043a\u0435.", false);
                        return;
                    }
                    Client.friendManager.addFriend(args[2].toString());
                    Client.msg("\u00a7e\u00a7lFriends:\u00a7r \u00a77\u0434\u0440\u0443\u0433 [\u00a7l" + args[2] + "\u00a7r\u00a77] \u0434\u043e\u0431\u0430\u0432\u043b\u0435\u043d \u0432 \u0441\u043f\u0438\u0441\u043e\u043a.", false);
                    if (!Notifications.get.actived) return;
                    Notifications.Notify.spawnNotify(args[2], Notifications.type.FADD);
                    return;
                } catch (Exception formatException) {
                    Client.msg("\u00a7e\u00a7lFriends:\u00a7r \u00a77\u041a\u043e\u043c\u043c\u0430\u043d\u0434\u0430 \u043d\u0430\u043f\u0438\u0441\u0430\u043d\u0430 \u043d\u0435\u0432\u0435\u0440\u043d\u043e.", false);
                    Client.msg("\u00a7e\u00a7lFriends:\u00a7r \u00a77add: add/new [\u00a7lNAME\u00a7r\u00a77] / [\u00a7lNEAR | DST or null\u00a7r\u00a77]", false);
                    Client.msg("\u00a7e\u00a7lFriends:\u00a7r \u00a77remove: remove/del [\u00a7lNAME\u00a7r\u00a77]", false);
                    Client.msg("\u00a7e\u00a7lFriends:\u00a7r \u00a77clear: clear/ci", false);
                    Client.msg("\u00a7e\u00a7lFriends:\u00a7r \u00a77list: list/see", false);
                    Client.msg("\u00a7e\u00a7lFriends:\u00a7r \u00a77replaceall: replaceall/replall/ra [\u00a7lDST\u00a7r\u00a77] or null", false);
                    Client.msg("\u00a7e\u00a7lFriends:\u00a7r \u00a77massage: msg/tell [\u00a7lTEXT / coords+[\u00a7lNAME\u00a7r\u00a77] or null\u00a7r\u00a77]", false);
                    formatException.printStackTrace();
                }
                return;
            }
            while (iterator.hasNext()) {
                String toAdd = (String)iterator.next();
                if (Client.friendManager.getFriend(toAdd) != null) continue;
                Client.friendManager.addFriend(toAdd);
                Client.msg("\u00a7e\u00a7lFriends:\u00a7r \u00a77" + (c == 1 ? "\u043d\u0430\u0439\u0434\u0435\u043d 1 " : "\u043d\u043e\u0432\u044b\u0439 \u0434\u0440\u0443\u0433") + " [\u00a7l" + toAdd + "\u00a7r\u00a77]" + (c == 1 ? " \u0438 \u043e\u043d" : "") + " \u0434\u043e\u0431\u0430\u0432\u043b\u0435\u043d \u0432 \u0441\u043f\u0438\u0441\u043e\u043a" + (c == 1 ? "." : (c > 1 && ++addCounter >= c - 1 ? "." : ";")), false);
                if (!Notifications.get.actived) continue;
                Notifications.Notify.spawnNotify(toAdd, Notifications.type.FADD);
            }
        }
        if (args[1].equalsIgnoreCase("replaceall") || args[1].equalsIgnoreCase("replall") || args[1].equalsIgnoreCase("ra")) {
            double range = args[2] != null ? Double.valueOf(args[2]) : 7.0;
            EntityPlayer entityPlayer = FreeCam.get.actived && FreeCam.fakePlayer != null ? FreeCam.fakePlayer : Minecraft.player;
            List toAddFriends = Friends.mc.world.getLoadedEntityList().stream().map(Entity::getLivingBaseOf).filter(base -> base != entityPlayer).filter(Objects::nonNull).filter(base -> (double)entityPlayer.getDistanceToEntity((Entity)base) <= range).map(Entity::getName).filter(name -> !Client.friendManager.isFriend((String)name)).collect(Collectors.toList());
            ArrayList<Friend> toRemove = new ArrayList<Friend>();
            for (Friend friend3 : Client.friendManager.getFriends()) {
                if (toAddFriends.stream().anyMatch(name -> name.equalsIgnoreCase(friend3.getName()))) continue;
                toRemove.add(friend3);
            }
            if (!toRemove.isEmpty()) {
                int c = toRemove.size();
                if (c != 1) {
                    Client.msg("\u00a7e\u00a7lFriends:\u00a7r \u00a77\u0438\u0437 \u0434\u0440\u0443\u0437\u0435\u0439 " + (c == 1 ? "\u0443\u0434\u0430\u043b\u0435\u043d\u043e" : (c == 2 || c == 3 || c == 4 ? "\u0443\u0434\u0430\u043b\u0435\u043d\u044b" : "\u0443\u0434\u0430\u043b\u0435\u043d\u044b")) + " " + c + (c == 1 ? "\u0441\u0443\u0449\u0435\u0441\u0442\u0432\u043e" : (c == 2 || c == 3 || c == 4 ? "\u0441\u0443\u0449\u0435\u0441\u0442\u0432\u0430" : "\u0441\u0443\u0449\u0435\u0441\u0442\u0432")) + ":", false);
                }
                boolean bl = false;
                for (Friend friend4 : toRemove) {
                    void var8_35;
                    if (Client.friendManager.getFriend(friend4.getName()) == null) continue;
                    Client.friendManager.removeFriend(friend4.getName());
                    Client.msg("\u00a7e\u00a7lFriends:\u00a7r \u00a77" + (c == 1 ? "\u0443\u0434\u0430\u043b\u0451\u043d 1 " : "\u0443\u0434\u0430\u043b\u0451\u043d \u0434\u0440\u0443\u0433") + " [\u00a7l" + friend4.getName() + "\u00a7r\u00a77]" + (c == 1 ? " \u0438 \u043e\u043d" : "") + " \u0443\u0434\u0430\u043b\u0451\u043d \u0438\u0437 \u0441\u043f\u0438\u0441\u043a\u0430" + (c == 1 ? "." : (c > 1 && ++var8_35 >= c - 1 ? "." : ";")), false);
                }
            }
            if (range <= 0.0) {
                Client.msg("\u00a7e\u00a7lFriends:\u00a7r \u00a77\u0441\u043b\u0438\u0448\u043a\u043e\u043c \u043c\u0430\u043b\u043e \u0434\u0438\u0441\u0442\u0430\u043d\u0446\u0438\u0438, \u044f \u043d\u0438\u043a\u043e\u0433\u043e \u043d\u0435 \u043d\u0430\u0439\u0434\u0443.", false);
                return;
            }
            if (toAddFriends.isEmpty()) {
                Client.msg("\u00a7e\u00a7lFriends:\u00a7r \u00a77\u0443\u0432\u044b \u043d\u0435\u043a\u043e\u0433\u043e \u0434\u043e\u0431\u0430\u0432\u0438\u0442\u044c \u0432 \u0441\u043f\u0438\u0441\u043e\u043a.", false);
                return;
            }
            int c = toAddFriends.size();
            if (c != 1) {
                Client.msg("\u00a7e\u00a7lFriends:\u00a7r \u00a77\u0432 \u0434\u0440\u0443\u0437\u044c\u044f " + (c == 1 || c == 2 || c == 3 || c == 4 ? "\u0434\u043e\u0431\u0430\u0432\u043b\u0435\u043d\u043e" : "\u0434\u043e\u0431\u0430\u0432\u043b\u0435\u043d\u044b") + " " + c + (c == 1 ? "\u0441\u0443\u0449\u0435\u0441\u0442\u0432\u043e" : (c == 2 || c == 3 || c == 4 ? "\u0441\u0443\u0449\u0435\u0441\u0442\u0432\u0430" : "\u0441\u0443\u0449\u0435\u0441\u0442\u0432")) + ":", false);
            }
            boolean bl = false;
            for (String toAdd : toAddFriends) {
                void var8_37;
                if (Client.friendManager.getFriend(toAdd) != null) continue;
                Client.friendManager.addFriend(toAdd);
                Client.msg("\u00a7e\u00a7lFriends:\u00a7r \u00a77" + (c == 1 ? "\u043d\u0430\u0439\u0434\u0435\u043d 1 " : "\u043d\u043e\u0432\u044b\u0439 \u0434\u0440\u0443\u0433") + " [\u00a7l" + toAdd + "\u00a7r\u00a77]" + (c == 1 ? " \u0438 \u043e\u043d" : "") + " \u0434\u043e\u0431\u0430\u0432\u043b\u0435\u043d \u0432 \u0441\u043f\u0438\u0441\u043e\u043a" + (c == 1 ? "." : (c > 1 && ++var8_37 >= c - 1 ? "." : ";")), false);
                if (!Notifications.get.actived) continue;
                Notifications.Notify.spawnNotify(toAdd, Notifications.type.FADD);
            }
        }
        if (args[1].equalsIgnoreCase("remove") || args[1].equalsIgnoreCase("del")) {
            if (Client.friendManager.isFriend(args[2])) {
                Client.friendManager.removeFriend(args[2]);
                Client.msg("\u00a7e\u00a7lFriends:\u00a7r \u00a77\u0434\u0440\u0443\u0433 [\u00a7l" + args[2] + "\u00a7r\u00a77] \u0443\u0434\u0430\u043b\u0451\u043d \u0438\u0437 \u0441\u043f\u0438\u0441\u043a\u0430.", false);
                if (!Notifications.get.actived) return;
                Notifications.Notify.spawnNotify(args[2], Notifications.type.FDEL);
                return;
            }
            Client.msg("\u00a7e\u00a7lFriends:\u00a7r \u00a77\u0434\u0440\u0443\u0433\u0430 [\u00a7l" + args[2] + "\u00a7r\u00a77] \u043d\u0435\u0442 \u0432 \u0441\u043f\u0438\u0441\u043a\u0435.", false);
            return;
        }
        if (args[1].equalsIgnoreCase("clear") || args[1].equalsIgnoreCase("ci")) {
            if (Notifications.get.actived && !Client.friendManager.getFriends().isEmpty()) {
                Client.friendManager.getFriends().forEach(friend -> Notifications.Notify.spawnNotify(friend.getName(), Notifications.type.FDEL));
            }
            Client.friendManager.clearFriends();
            Client.msg("\u00a7e\u00a7lFriends:\u00a7r \u00a77\u0441\u043f\u0438\u0441\u043e\u043a \u0434\u0440\u0443\u0437\u0435\u0439 \u043e\u0447\u0438\u0449\u0435\u043d.", false);
            return;
        }
        if (!args[1].equalsIgnoreCase("list")) {
            if (!args[1].equalsIgnoreCase("see")) return;
        }
        if (Client.friendManager.getFriends().size() == 0) {
            Client.msg("\u00a7e\u00a7lFriends:\u00a7r \u00a77\u0441\u043f\u0438\u0441\u043e\u043a \u0434\u0440\u0443\u0437\u0435\u0439 \u043f\u0443\u0441\u0442", false);
            return;
        }
        int counter = 0;
        Iterator<Friend> iterator = Client.friendManager.getFriends().iterator();
        while (iterator.hasNext()) {
            Friend friend5 = iterator.next();
            if (friend5 == null) continue;
            Client.msg("\u00a7e\u00a7lFriends:\u00a7r \u00a77\u0434\u0440\u0443\u0433 \u2116" + ++counter + " [\u00a7l" + friend5.getName() + "\u00a7r\u00a77].", false);
        }
    }
}


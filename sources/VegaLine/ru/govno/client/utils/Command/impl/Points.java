/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.utils.Command.impl;

import java.util.Iterator;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import ru.govno.client.Client;
import ru.govno.client.module.modules.PointTrace;
import ru.govno.client.utils.Command.Command;

public class Points
extends Command {
    private static final Minecraft mc = Minecraft.getMinecraft();

    public Points() {
        super("Points", new String[]{"points", "point", "p", "way"});
    }

    @Override
    public void onCommand(String[] args) {
        try {
            if (args[1].equalsIgnoreCase("del") || args[1].equalsIgnoreCase("remove")) {
                if (PointTrace.getPointList().size() == 0) {
                    Client.msg("\u00a73\u00a7lPoints:\u00a7r \u00a77\u0421\u043f\u0438\u0441\u043e\u043a \u043f\u043e\u0438\u043d\u0442\u043e\u0432 \u043f\u0443\u0441\u0442.", false);
                    return;
                }
                Iterator<PointTrace> iterator = PointTrace.getPointList().iterator();
                if (iterator.hasNext()) {
                    PointTrace point = iterator.next();
                    if (PointTrace.getPointByName(args[2]) != null) {
                        if (PointTrace.points.size() == 1) {
                            PointTrace.clearPoints();
                        } else {
                            PointTrace.removePoint(PointTrace.getPointByName(args[2]));
                        }
                        Client.msg("\u00a73\u00a7lPoints:\u00a7r \u00a77\u041f\u043e\u0438\u043d\u0442 [\u00a7l" + args[2] + "\u00a7r\u00a77] \u0443\u0434\u0430\u043b\u0451\u043d.", false);
                        return;
                    }
                    Client.msg("\u00a73\u00a7lPoints:\u00a7r \u00a77\u041f\u043e\u0438\u043d\u0442\u0430 \u0441 \u0438\u043c\u0435\u043d\u0435\u043c [\u00a7l" + args[2] + "\u00a7r\u00a77] \u043d\u0435 \u0441\u0443\u0449\u0435\u0441\u0442\u0432\u0443\u0435\u0442.", false);
                    return;
                }
            }
            if (args[1].equalsIgnoreCase("add") || args[1].equalsIgnoreCase("to") || args[1].equalsIgnoreCase("new")) {
                String num;
                int sampledSize;
                String sampleName;
                boolean canAdd = false;
                Object pointName = "";
                float x = 0.0f;
                float y = 0.0f;
                float z = 0.0f;
                if (args.length == 2) {
                    sampleName = "Point";
                    sampledSize = PointTrace.getPointList().stream().filter(Objects::nonNull).map(PointTrace::getName).filter(name -> name.startsWith("Point")).toList().size();
                    String string = PointTrace.getPointList().isEmpty() ? "" : (num = String.valueOf(sampledSize + (sampledSize >= 1 ? 1 : 0)));
                    if (num.equalsIgnoreCase("0")) {
                        num = "";
                    }
                    pointName = "Point" + num;
                    x = (float)Minecraft.player.posX;
                    y = (float)Minecraft.player.posY;
                    z = (float)Minecraft.player.posZ;
                    canAdd = true;
                }
                if (args.length == 3) {
                    sampleName = String.valueOf(args[2]);
                    sampledSize = PointTrace.getPointList().stream().filter(Objects::nonNull).map(PointTrace::getName).filter(name -> name.startsWith(sampleName)).toList().size();
                    String string = PointTrace.getPointList().isEmpty() ? "" : (num = String.valueOf(sampledSize + (sampledSize >= 1 ? 1 : 0)));
                    if (num.equalsIgnoreCase("0")) {
                        num = "";
                    }
                    pointName = sampleName + num;
                    x = (float)Minecraft.player.posX;
                    y = (float)Minecraft.player.posY;
                    z = (float)Minecraft.player.posZ;
                    canAdd = true;
                }
                if (args.length == 5) {
                    sampleName = String.valueOf(args[2]);
                    sampledSize = PointTrace.getPointList().stream().filter(Objects::nonNull).map(PointTrace::getName).filter(name -> name.startsWith(sampleName)).toList().size();
                    String string = PointTrace.getPointList().isEmpty() ? "" : (num = String.valueOf(sampledSize + (sampledSize >= 1 ? 1 : 0)));
                    if (num.equalsIgnoreCase("0")) {
                        num = "";
                    }
                    pointName = sampleName + num;
                    x = Float.parseFloat(args[3]);
                    y = (float)Minecraft.player.posY;
                    z = Float.parseFloat(args[4]);
                    canAdd = true;
                }
                if (args.length == 6) {
                    sampleName = String.valueOf(args[2]);
                    sampledSize = PointTrace.getPointList().stream().filter(Objects::nonNull).map(PointTrace::getName).filter(name -> name.startsWith(sampleName)).toList().size();
                    String string = PointTrace.getPointList().isEmpty() ? "" : (num = String.valueOf(sampledSize + (sampledSize >= 1 ? 1 : 0)));
                    if (num.equalsIgnoreCase("0")) {
                        num = "";
                    }
                    pointName = sampleName + num;
                    x = Float.parseFloat(args[3]);
                    y = Float.parseFloat(args[4]);
                    z = Float.parseFloat(args[5]);
                    canAdd = true;
                }
                if (canAdd) {
                    PointTrace.addPoint((String)pointName, x, y, z);
                    String xyz = "(X: " + (int)x + " ,Y: " + (int)y + " ,Z: " + (int)z + ")";
                    Client.msg("\u00a73\u00a7lPoints:\u00a7r \u00a77\u041d\u043e\u0432\u044b\u0439 \u043f\u043e\u0438\u043d\u0442 ''" + (String)pointName + "'' \u043d\u0430 " + xyz + ".", false);
                    return;
                }
                Client.msg("\u00a73\u00a7lPoints:\u00a7r \u00a77\u041a\u043e\u043c\u043c\u0430\u043d\u0434\u0430 \u043d\u0430\u043f\u0438\u0441\u0430\u043d\u0430 \u043d\u0435\u0432\u0435\u0440\u043d\u043e.", false);
                Client.msg("\u00a73\u00a7lPoints:\u00a7r \u00a77add: add/to/new [\u00a7lname+[x,y,z/x,z]/name/''\u00a7r\u00a77]", false);
            }
            if (args[1].equalsIgnoreCase("ci") || args[1].equalsIgnoreCase("clear")) {
                if (PointTrace.getPointList().size() == 0) {
                    Client.msg("\u00a73\u00a7lPoints:\u00a7r \u00a77\u0421\u043f\u0438\u0441\u043e\u043a \u043f\u043e\u0438\u043d\u0442\u043e\u0432 \u043f\u0443\u0441\u0442.", false);
                    return;
                }
                PointTrace.clearPoints();
                Client.msg("\u00a73\u00a7lPoints:\u00a7r \u00a77\u0412\u0441\u0435 \u043f\u043e\u0438\u043d\u0442\u044b \u0431\u044b\u043b\u0438 \u0443\u0434\u0430\u043b\u0435\u043d\u044b.", false);
                return;
            }
            if (args[1].equalsIgnoreCase("list") || args[1].equalsIgnoreCase("see")) {
                if (PointTrace.getPointList().size() == 0) {
                    Client.msg("\u00a73\u00a7lPoints:\u00a7r \u00a77\u0421\u043f\u0438\u0441\u043e\u043a \u043f\u043e\u0438\u043d\u0442\u043e\u0432 \u043f\u0443\u0441\u0442.", false);
                } else {
                    for (PointTrace point : PointTrace.getPointList()) {
                        String coords = "X:" + PointTrace.getX(point) + ", Y:" + PointTrace.getY(point) + ", Z:" + PointTrace.getZ(point);
                        String and = PointTrace.getPointList().indexOf(point) == PointTrace.getPointList().size() - 1 ? "." : ",";
                        Client.msg("\u00a73\u00a7lPoints: \u00a7r\u00a77\u2116\u00a7l[" + (PointTrace.getPointList().indexOf(point) + 1) + "]\u00a7r\u00a77: \u00a7l[" + point.name + "\u00a7r\u00a77\u00a7l]\u00a7r\u00a77" + coords + and, false);
                    }
                }
            }
        } catch (Exception formatException) {
            Client.msg("\u00a73\u00a7lPoints:\u00a7r \u00a77\u041a\u043e\u043c\u043c\u0430\u043d\u0434\u0430 \u043d\u0430\u043f\u0438\u0441\u0430\u043d\u0430 \u043d\u0435\u0432\u0435\u0440\u043d\u043e.", false);
            Client.msg("\u00a73\u00a7lPoints:\u00a7r \u00a77use points: points/point/p/way", false);
            Client.msg("\u00a73\u00a7lPoints:\u00a7r \u00a77add: add/to/new [\u00a7lname+[x,y,z/x,z]/name/''\u00a7r\u00a77]", false);
            Client.msg("\u00a73\u00a7lPoints:\u00a7r \u00a77clear all: ci/clear", false);
            Client.msg("\u00a73\u00a7lPoints:\u00a7r \u00a77list: list/see", false);
            Client.msg("\u00a73\u00a7lPoints:\u00a7r \u00a77remove: del/remove [\u00a7lname \u00a7r\u00a77]", false);
            formatException.printStackTrace();
        }
    }
}


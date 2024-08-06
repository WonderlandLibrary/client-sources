package com.shroomclient.shroomclientnextgen.modules.impl.client;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.shroomclient.shroomclientnextgen.config.ConfigChild;
import com.shroomclient.shroomclientnextgen.config.ConfigHide;
import com.shroomclient.shroomclientnextgen.config.ConfigOption;
import com.shroomclient.shroomclientnextgen.config.ConfigParentId;
import com.shroomclient.shroomclientnextgen.events.SubscribeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.RenderScreenEvent;
import com.shroomclient.shroomclientnextgen.events.impl.RenderTickEvent;
import com.shroomclient.shroomclientnextgen.events.impl.ScreenClickedEvent;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleCategory;
import com.shroomclient.shroomclientnextgen.modules.RegisterModule;
import com.shroomclient.shroomclientnextgen.util.C;
import com.shroomclient.shroomclientnextgen.util.RenderUtil;
import com.shroomclient.shroomclientnextgen.util.ThemeUtil;
import java.awt.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;
import net.minecraft.client.gui.screen.ChatScreen;

@RegisterModule(
    name = "Ban Tracker",
    uniqueId = "bantrack",
    description = "Tracks Bans From Servers",
    category = ModuleCategory.Client
)
public class BanCheck extends Module {

    @ConfigOption(
        name = "Notifications",
        description = "Shows A Notification When Someone Gets Banned",
        order = 1
    )
    public static Boolean notif = true;

    @ConfigOption(
        name = "Staff Bans",
        description = "Shows Staff Bans",
        order = 2
    )
    public static Boolean staff = true;

    @ConfigOption(
        name = "Watchdog Bans",
        description = "Shows Watchdog Bans",
        order = 3
    )
    public static Boolean watchdog = true;

    @ConfigParentId("graph")
    @ConfigOption(
        name = "Graph",
        description = "Renders A Graph With Bans",
        order = 4
    )
    public static Boolean Graph = false;

    @ConfigHide
    @ConfigChild("graph")
    @ConfigOption(
        name = "Graph X Position",
        description = "",
        min = 1,
        max = 1000,
        order = 5
    )
    public static Integer xPos = 8;

    @ConfigHide
    @ConfigChild("graph")
    @ConfigOption(
        name = "Graph Y Position",
        description = "",
        min = 1,
        max = 1000,
        order = 6
    )
    public static Integer yPos = 35;

    public static int prevbans = 0;
    public static int bans = 0;
    public static int WDbans = 0;
    public static int prevWDbans = 0;
    public static long prevtime = 0;
    public static float[] square = new float[] { 0, 0, 0, 0 };
    static int banHistoryLength = 31;
    static ArrayList<Integer> WDbanHistory = new ArrayList<>();
    static ArrayList<Integer> StaffBanHistory = new ArrayList<>();
    static int[] historyLastBans = new int[] { 0, 0 };
    static long prevLogTime = 0;
    static boolean done = true;
    boolean dragging = false;
    double mouseXold = 0;
    double mouseYold = 0;
    double oldHudX = 0;
    double oldHudY = 0;

    public static int[] banStats() {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(
                "https://api.plancke.io/hypixel/v1/punishmentStats"
            ).openConnection();
            connection.addRequestProperty(
                "User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0"
            );
            connection.setRequestMethod("GET");
            JsonObject object = JsonParser.parseReader(
                new InputStreamReader(connection.getInputStream())
            ).getAsJsonObject();
            return new int[] {
                object
                    .get("record")
                    .getAsJsonObject()
                    .get("staff_total")
                    .getAsInt(),
                object
                    .get("record")
                    .getAsJsonObject()
                    .get("watchdog_total")
                    .getAsInt(),
            };
        } catch (IOException e) {
            return new int[] { prevbans, prevWDbans };
        }
    }

    public static void doBanning() {
        done = false;
        int[] ban = banStats();
        bans = ban[0];
        WDbans = ban[1];
        if (System.currentTimeMillis() - prevtime >= 5000) {
            if (prevtime != 0) {
                if (staff) {
                    if (
                        bans - prevbans > 0 && bans - prevbans < 400000 && notif
                    ) Notifications.notify(
                        (bans - prevbans) + " Staff Bans In Last 5 Seconds",
                        ThemeUtil.themeColors()[1],
                        0
                    );
                    //System.out.println((bans - prevbans) + " banned by staff");
                }
                if (watchdog) {
                    if (
                        WDbans - prevWDbans > 0 &&
                        WDbans - prevWDbans < 400000 &&
                        notif
                    ) Notifications.notify(
                        (WDbans - prevWDbans) +
                        " Watchdog Bans In Last 5 Seconds",
                        ThemeUtil.themeColors()[0],
                        0
                    );
                    //System.out.println((WDbans - prevWDbans) + " banned by watchdog");
                }
            }

            prevtime = System.currentTimeMillis();
            prevbans = bans;
            prevWDbans = WDbans;
        }

        if (System.currentTimeMillis() - prevLogTime >= 30000) {
            if (prevLogTime != 0) {
                // in  theory wdban length and  staffban length arre the same, but who knows
                if (
                    WDbanHistory.size() >= banHistoryLength ||
                    StaffBanHistory.size() >= banHistoryLength
                ) {
                    StaffBanHistory.remove(0);
                    WDbanHistory.remove(0);
                }

                /*
                if (notif) {
                    Notifications.notify((bans - historyLastBans[0]) + " Staff Bans In Last 30 Seconds", ThemeUtil.themeColors()[0], 0);
                    Notifications.notify((WDbans - historyLastBans[1]) + " Watchdog Bans In Last 30 Seconds", ThemeUtil.themeColors()[0], 0);
                }
                 */

                StaffBanHistory.add(Math.max(bans - historyLastBans[0], 0));
                WDbanHistory.add(Math.max(WDbans - historyLastBans[1], 0));

                System.out.println(
                    Math.max(bans - historyLastBans[0], 0) +
                    " staff bans in last 30 seconds"
                );
                System.out.println(
                    Math.max(WDbans - historyLastBans[1], 0) +
                    " watchdog bans in last 30 seconds"
                );
            }

            prevLogTime = System.currentTimeMillis();
            historyLastBans = new int[] { bans, WDbans };
        }

        done = true;
    }

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {
        done = true;
    }

    @SubscribeEvent
    public void onRenderTick(RenderTickEvent e) {
        RenderUtil.setContext(e.drawContext);

        if (System.currentTimeMillis() - prevtime >= 5000 && done) {
            try {
                ForkJoinPool.commonPool().execute(BanCheck::doBanning);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        if (Graph) {
            int highestWidth = 0;

            e.matrixStack.translate(xPos, yPos, 0);

            int graphHeight = 100;
            int graphWidth = 200;

            square = new float[] {
                xPos,
                yPos,
                graphWidth + 20,
                graphHeight + 20,
            };

            // background
            RenderUtil.drawRoundedRect2(
                0,
                0,
                graphWidth + 20,
                graphHeight + 20,
                5,
                new Color(23, 23, 23, 100),
                false,
                false,
                false,
                false
            );
            RenderUtil.drawRoundedGlow(
                0,
                0,
                graphWidth + 20,
                graphHeight + 20,
                5,
                5,
                ThemeUtil.themeColors()[0],
                false,
                false,
                false,
                false
            );

            int last15MinutesBans = 0;

            if (WDbanHistory.size() > 1 && StaffBanHistory.size() > 1) {
                // get highest + all bans combined
                int highest = 1;
                for (int i = 0; i < WDbanHistory.size(); i++) {
                    if (WDbanHistory.get(i) != null) {
                        if (WDbanHistory.get(i) > highest) highest =
                            WDbanHistory.get(i);
                        last15MinutesBans += WDbanHistory.get(i);
                    }
                }
                for (int i = 0; i < StaffBanHistory.size(); i++) {
                    if (StaffBanHistory.get(i) != null) {
                        if (StaffBanHistory.get(i) > highest) highest =
                            StaffBanHistory.get(i);
                        last15MinutesBans += StaffBanHistory.get(i);
                    }
                }

                highestWidth = (C.mc.textRenderer.getWidth("" + highest) / 2) -
                5;

                RenderUtil.drawRect(
                    10 + highestWidth,
                    10,
                    1,
                    graphHeight,
                    ThemeUtil.themeColors()[0]
                );
                RenderUtil.drawRect(
                    10 + highestWidth,
                    10 + graphHeight,
                    graphWidth - 10,
                    1,
                    ThemeUtil.themeColors()[0]
                );

                int prevXCoord = highestWidth + 1;
                int prevYCoord =
                    graphHeight -
                    (int) ((((float) WDbanHistory.get(0) / (float) highest) *
                            graphHeight));

                for (int i = 1; i < WDbanHistory.size(); i++) {
                    int xCoord =
                        (i - 1) *
                            ((graphWidth + 8 + highestWidth) /
                                (banHistoryLength)) +
                        5;
                    int yCoord =
                        graphHeight -
                        (int) ((((float) WDbanHistory.get(i) /
                                    (float) highest) *
                                graphHeight));
                    RenderUtil.drawLine(
                        prevXCoord + 10,
                        prevYCoord + 10,
                        xCoord + 10,
                        yCoord + 10,
                        new Color(246, 112, 125)
                    );
                    prevXCoord = xCoord;
                    prevYCoord = yCoord;
                }

                prevXCoord = highestWidth + 1;
                prevYCoord = graphHeight -
                (int) ((((float) StaffBanHistory.get(0) / (float) highest) *
                        graphHeight));

                for (int i = 1; i < StaffBanHistory.size(); i++) {
                    if (StaffBanHistory.get(i) != null) {
                        int xCoord =
                            (i - 1) *
                                ((graphWidth + 8 + highestWidth) /
                                    (banHistoryLength)) +
                            5;
                        int yCoord =
                            graphHeight -
                            (int) ((((float) StaffBanHistory.get(i) /
                                        (float) highest) *
                                    graphHeight));
                        RenderUtil.drawLine(
                            prevXCoord + 10,
                            prevYCoord + 10,
                            xCoord + 10,
                            yCoord + 10,
                            new Color(150, 224, 238)
                        );
                        prevXCoord = xCoord;
                        prevYCoord = yCoord;
                    } else {
                        int xCoord =
                            (i - 1) *
                                ((graphWidth + 8 + highestWidth) /
                                    (banHistoryLength)) +
                            5;
                        RenderUtil.drawLine(
                            prevXCoord + 10,
                            prevYCoord + 10,
                            xCoord + 10,
                            prevYCoord + 10,
                            new Color(150, 224, 238)
                        );
                        prevXCoord = xCoord;
                    }
                }

                for (int i = 0; i <= highest; i++) {
                    float yOfLine = ((float) graphHeight / highest) * i + 10;

                    e.matrixStack.translate(8 + highestWidth, yOfLine, 0);
                    e.matrixStack.scale(0.5f, 0.5f, 1);
                    RenderUtil.drawLine(0, 0, 4, 0, ThemeUtil.themeColors()[0]);
                    RenderUtil.drawTextShadow(
                        "" + (highest - i),
                        -C.mc.textRenderer.getWidth("" + (highest - i)) - 2,
                        -4,
                        ThemeUtil.themeColors()[0]
                    );
                    e.matrixStack.scale(2, 2, 1);
                    e.matrixStack.translate(-(8 + highestWidth), -yOfLine, 0);
                }
            }

            RenderUtil.drawRect(
                10 + highestWidth,
                10,
                1,
                graphHeight,
                ThemeUtil.themeColors()[0]
            );
            RenderUtil.drawRect(
                10 + highestWidth,
                10 + graphHeight,
                graphWidth - 10,
                1,
                ThemeUtil.themeColors()[0]
            );

            RenderUtil.drawRect(
                13 + highestWidth,
                13 + graphHeight,
                10,
                4,
                new Color(246, 112, 125)
            );
            RenderUtil.drawRect(
                highestWidth + C.mc.textRenderer.getWidth("watchdog") + 5,
                13 + graphHeight,
                10,
                4,
                new Color(150, 224, 238)
            );

            e.matrixStack.translate(8, 2, 0);
            e.matrixStack.scale(0.5f, 0.5f, 1);
            RenderUtil.drawTextShadow(
                "Hypixel Bans In Past 15 Minutes",
                2,
                2,
                ThemeUtil.themeColors()[0]
            );
            e.matrixStack.scale(2, 2, 1);
            e.matrixStack.translate(-8, -2, 0);

            e.matrixStack.translate(25 + highestWidth, 13 + graphHeight, 0);
            e.matrixStack.scale(0.5f, 0.5f, 1);
            RenderUtil.drawTextShadow(
                "watchdog",
                0,
                0,
                new Color(255, 255, 255)
            );
            e.matrixStack.translate(
                C.mc.textRenderer.getWidth("watchdog") + 30,
                0,
                0
            );
            RenderUtil.drawTextShadow("staff", 0, 0, new Color(255, 255, 255));
            e.matrixStack.translate(
                C.mc.textRenderer.getWidth("staff") + 30,
                0,
                0
            );
            RenderUtil.drawTextShadow(
                "total bans: " + last15MinutesBans,
                0,
                0,
                ThemeUtil.themeColors()[0]
            );

            e.matrixStack.loadIdentity();
        }
    }

    @SubscribeEvent
    public void ChatEvent(ScreenClickedEvent e) {
        if (C.mc.currentScreen instanceof ChatScreen) {
            if (
                e.mouseX >= square[0] &&
                e.mouseX <= square[0] + square[2] &&
                e.mouseY >= square[1] &&
                e.mouseY <= square[1] + square[3]
            ) {
                dragging = e.button == 0;
                mouseXold = e.mouseX;
                mouseYold = e.mouseY;

                oldHudX = xPos;
                oldHudY = yPos;
            }

            if (!e.down && e.button == 0) {
                dragging = false;
            }
        }
    }

    @SubscribeEvent
    public void onDrawScreen(RenderScreenEvent e) {
        if (C.mc.currentScreen instanceof ChatScreen) {
            double mouseMovedX = e.mouseX - mouseXold;
            double mouseMovedY = e.mouseY - mouseYold;

            if (dragging) {
                xPos = (int) (oldHudX + mouseMovedX);
                yPos = (int) (oldHudY + mouseMovedY);

                RenderUtil.drawRoundedGlow(
                    square[0],
                    square[1],
                    square[2],
                    square[3],
                    5,
                    5,
                    new Color(255, 255, 255),
                    200,
                    false,
                    false,
                    false,
                    false
                );
            }
        }
    }
}

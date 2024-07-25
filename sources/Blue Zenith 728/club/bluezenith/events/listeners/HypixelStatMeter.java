package club.bluezenith.events.listeners;

import club.bluezenith.core.data.alt.info.AccountInfo;
import club.bluezenith.core.data.alt.info.AccountType;
import club.bluezenith.core.data.alt.info.HypixelInfo;
import club.bluezenith.core.data.preferences.Preference;
import club.bluezenith.core.data.preferences.Preferences;
import club.bluezenith.events.Listener;
import club.bluezenith.events.impl.*;
import club.bluezenith.util.client.Chat;
import club.bluezenith.util.client.MillisTimer;
import club.bluezenith.util.player.PacketUtil;
import club.bluezenith.util.client.ServerUtils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S45PacketTitle;
import net.minecraft.util.EnumChatFormatting;

import java.util.concurrent.ScheduledFuture;

import static club.bluezenith.BlueZenith.getBlueZenith;
import static club.bluezenith.BlueZenith.scheduledExecutorService;
import static club.bluezenith.core.requests.Request.get;
import static club.bluezenith.core.requests.data.ContentType.QUERY;
import static club.bluezenith.core.requests.data.RequestOption.queryOf;
import static club.bluezenith.util.MinecraftInstance.mc;
import static club.bluezenith.util.player.PacketUtil.sendSilent;
import static club.bluezenith.util.client.ServerUtils.HypixelGameMode.*;
import static club.bluezenith.util.client.ServerUtils.getGameMode;
import static club.bluezenith.util.client.ServerUtils.hypixel;
import static club.bluezenith.util.math.MathUtil.convertMSToTimeString;
import static club.bluezenith.util.math.MathUtil.convertMillisToAltPlaytime;
import static club.bluezenith.util.render.ColorUtil.stripFormatting;
import static java.lang.Integer.parseInt;
import static java.lang.Math.max;
import static java.lang.String.format;
import static java.lang.System.currentTimeMillis;
import static java.util.concurrent.CompletableFuture.supplyAsync;
import static java.util.concurrent.TimeUnit.*;

public class HypixelStatMeter {

    public int playtime, kills, gamesPlayed, gamesWon, staffBansLastMinute, staffBansCurrent, staffBansRecorded;
    private String APIKey, playtimeString, lastUsername;
    private MillisTimer timer = new MillisTimer();
    private boolean shouldCollectStats, grabbedAPIKey;
    private long lastKillTime, serverJoinTime;
    private ScheduledFuture<?> banCheckTask;

    private AccountInfo banCheckedAccount;

    public HypixelStatMeter() {

    }

    public HypixelStatMeter(AccountInfo accountInfo) {
        this.banCheckedAccount = accountInfo;
        final HypixelInfo hypixelInfo = accountInfo.createInfo();

        this.playtime = (int) hypixelInfo.getPlaytime();
        this.kills = hypixelInfo.getKills();
        this.gamesPlayed = hypixelInfo.getGamesPlayed();
        this.gamesWon = hypixelInfo.getGamesWon();
    }

    @Listener
    public void onConnect(ServerConnectEvent event) {
        if(!ServerUtils.checkHypixel()) {
            resetAllStats();
            return;
        }
        final String currentUsername = mc.session.getUsername();
        if(!currentUsername.equals(lastUsername)) resetAllStats();
        shouldCollectStats = true;
        serverJoinTime = currentTimeMillis();
        lastUsername = currentUsername;
        getBlueZenith().setHypixelStatMeter(this);
    }

    @Listener
    public void onKill(AuraTargetKilledEvent event) {
        if(shouldCollectStats && currentTimeMillis() - lastKillTime > 50) {
            kills++;
            getBlueZenith().getAccountRepository().getCurrentAccount().getHypixelInfo().playerKilled();
            lastKillTime = currentTimeMillis();
        }
    }

    @Listener
    public void onPacket(PacketEvent event) {
        if(mc.thePlayer == null || !ServerUtils.hypixel || !shouldCollectStats) return;

        if(timer.hasTimeReached(50)) {
            playtime += 50;
            final HypixelInfo hypixelInfo = getBlueZenith().getAccountRepository().getCurrentAccount().getHypixelInfo();
            hypixelInfo.setPlaytime(playtime);
            supplyAsync(() -> convertMSToTimeString(playtime, true)).thenAccept((string) -> playtimeString = string);
            supplyAsync(() -> convertMillisToAltPlaytime(playtime)).thenAccept(hypixelInfo::setPlaytimeString).thenAccept(__ -> hypixelInfo.setPlaytime(playtime));
            timer.reset();
        }

        final long diff = currentTimeMillis() - serverJoinTime;

        if(!grabbedAPIKey && diff > 1500 && diff < 5000) {
            grabbedAPIKey = true;
            sendSilent(new C01PacketChatMessage("/lang english"));
            sendSilent(new C01PacketChatMessage("/api new"));
        }

        if(event.packet instanceof S45PacketTitle) {
            handleS45PacketTitle((S45PacketTitle) event.packet);
        } else if(event.packet instanceof S02PacketChat) {
            handleS02PacketChat((S02PacketChat) event.packet);
        }
    }

    @Listener
    public void onSpawn(SpawnPlayerEvent event) {
        if(mc.isSingleplayer()) {
            ServerUtils.hypixel = false;
            resetAllStats();
        } else {
            final ServerUtils.HypixelGameMode gameMode = getGameMode();
            if (!hypixel || gameMode == BEDWARS || gameMode == LOBBY || gameMode == OTHER) {
                return;
            }
            getBlueZenith().getAccountRepository().getCurrentAccount().getHypixelInfo().gameStarted();
        }
    }

    @Listener
    public void onDisconnect(DisconnectEvent event) {
        if(PacketUtil.data != null && PacketUtil.data.serverIP.contains("hypixel")) {
            final AccountInfo account = this.banCheckedAccount != null ? banCheckedAccount
                                        : getBlueZenith().getAccountRepository().getCurrentAccount();

            HypixelInfo hypixelInfo = account.getHypixelInfo();

            if(hypixelInfo == null) {
                if(account.getAccountType() != AccountType.OFFLINE)
                    hypixelInfo = account.createInfo();
                else return;
            }

           final boolean bannedBefore = hypixelInfo.isBanned();

           if(event.reason == null) return;

           if(!bannedBefore) {
               final long duration = parseBanDuration(event.reason.getUnformattedText());

               if (duration != Long.MIN_VALUE && duration <= 0) return;

               if (duration == Long.MIN_VALUE)
                   hypixelInfo.setBanned(duration);
               else hypixelInfo.setBanned(currentTimeMillis() + duration);
           }

           if(playtime > 0 || Preferences.banIpsWithoutPlaytime)
              getBlueZenith().getNewAltManagerGUI().ipTracker.markIpAsBanned();
           getBlueZenith().getNotificationPublisher().postWarning("Account Manager", "Marked account and IP as banned", 2500);
           if(bannedBefore) return;
        }

        if(shouldCollectStats && event.reason != null && stripFormatting(event.reason.getUnformattedText()).toLowerCase().contains("banned")) {
            event.reason.appendText(format("\n\n§bStats§7\n\nPlaytime: %s\nKills: %s\nGames played: %s \nWins: %s\nStaff bans: %s", playtimeString, kills, gamesPlayed, gamesWon, staffBansRecorded));
            resetAllStats();
        } else shouldCollectStats = false;
        grabbedAPIKey = false;

    }

    private long parseBanDuration(String banMessage) {
        final String message = stripFormatting(banMessage);

        if(message.contains("You are permanently banned")) {
            return Long.MIN_VALUE;
        }

        if(!message.contains("You are temporarily banned for ") || !message.contains("from this server!")) return 0;

        final String banDuration = message.split("You are temporarily banned for ")[1].split("from this server!")[0];

        long result = 0;

        for (String time : banDuration.split(" ")) {
            final int number = parseInt(
                    time.substring(0, time.length() - 1)
            );

            switch (time.substring(time.length() - 1)) {
                case "d":
                    result += DAYS.toMillis(number);
                break;

                case "h":
                    result += HOURS.toMillis(number);
                break;

                case "m":
                    result += MINUTES.toMillis(number);
                break;

                case "s":
                    result += SECONDS.toMillis(number);
                break;
            }

        }

        return result;
    }

    private void handleS45PacketTitle(S45PacketTitle packet) {
        if(packet.getMessage() == null || packet.getMessage().getUnformattedText() == null) return;
        final String text = packet.getMessage().getUnformattedText();

     //   fancyMessage(getGameMode());
        if(text.contains("VICTORY!")) {
            getBlueZenith().getAccountRepository().getCurrentAccount().getHypixelInfo().gameWon();
            gamesWon++;
            gamesPlayed++;
        } else if(text.contains("YOU DIED!") && getGameMode() != BEDWARS) {
            gamesPlayed++;
            getBlueZenith().getAccountRepository().getCurrentAccount().getHypixelInfo().died();
        }
    }

    private final static String pattern = "Your new API key is ";

    private void handleS02PacketChat(S02PacketChat packet) {
        if(packet.getChatComponent() == null || packet.getChatComponent().getUnformattedText() == null) return;

        final String text = packet.getChatComponent().getUnformattedText();
        String api;

        if(text.startsWith(pattern)) {
            APIKey = text.substring(pattern.length());
            Chat.xi("Grabbed your API key citizen", EnumChatFormatting.YELLOW);

            if(banCheckTask != null) banCheckTask.cancel(true);

            banCheckTask = scheduledExecutorService.scheduleAtFixedRate(
                    () -> get("https://api.hypixel.net/punishmentstats", getBlueZenith().getRequestExecutor(), QUERY, queryOf("key", APIKey))
                            .appendCallback((response -> {
                                try {
                                    if(APIKey.length() < 10) {
                                        Chat.xi("Found invalid API key. Reconnect?");
                                        banCheckTask.cancel(true);
                                        return;
                                    }
                                    final JsonObject jsonObject = new JsonParser().parse(response.textResponse).getAsJsonObject();
                                    if(!jsonObject.get("success").getAsBoolean()) {
                                        Chat.xi("Couldn't get ban stats: " + jsonObject.get("cause").getAsString());
                                        return;
                                    }
                                    final int retrieved = jsonObject.get("staff_total").getAsInt();
                                    final boolean firstCheck = staffBansLastMinute == 0;
                                    if(firstCheck) {
                                        staffBansCurrent = 0;
                                        staffBansLastMinute = retrieved;
                                        staffBansRecorded = 0;
                                    } else {
                                        final int diff = max(0, retrieved - staffBansLastMinute);
                                        staffBansCurrent = diff;
                                        staffBansLastMinute = retrieved;
                                        staffBansRecorded += diff;
                                    }

                                } catch (Exception exception) {
                                    exception.printStackTrace();
                                    Chat.xi("Something went wrong while getting ban stats!");
                                }
                            })).run()
                    , 5, 60, SECONDS);
        }
    }

    private void resetAllStats() {
        playtime = 0;
        getBlueZenith().setHypixelStatMeter(null);
        kills = 0;
        gamesPlayed = 0;
        gamesWon = 0;
        staffBansLastMinute = 0;
        staffBansCurrent = 0;
        staffBansRecorded = 0;
        lastKillTime = 0;
        serverJoinTime = 0;
        APIKey = null;
        playtimeString = "0s";
        lastUsername = null;
        timer.reset();
        grabbedAPIKey = false;
        shouldCollectStats = false;
        if(banCheckTask != null) banCheckTask.cancel(true);
        banCheckTask = null;
    }

    public String getPlaytimeString() {
        return this.playtimeString == null || this.playtimeString.isEmpty() ? "0s" : this.playtimeString;
    }

}

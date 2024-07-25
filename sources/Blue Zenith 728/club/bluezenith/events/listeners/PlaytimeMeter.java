package club.bluezenith.events.listeners;

import club.bluezenith.BlueZenith;
import club.bluezenith.core.data.alt.AccountRepository;
import club.bluezenith.events.Listener;
import club.bluezenith.events.impl.DisconnectEvent;
import club.bluezenith.events.impl.ServerConnectEvent;
import club.bluezenith.events.impl.UpdatePlayerEvent;
import club.bluezenith.util.client.MillisTimer;
import club.bluezenith.util.client.ServerUtils;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.CompletableFuture.supplyAsync;

public class PlaytimeMeter {
    public String timePlayedString;
    boolean count;
    long timePlayed;
    MillisTimer timer = new MillisTimer();

    @Listener
    public void onConnect(ServerConnectEvent event) {
        ServerUtils.checkBlocksMC();
        if(!count) {
            count = true;
            timePlayed = System.currentTimeMillis();

            final AccountRepository accountRepository = BlueZenith.getBlueZenith().getAccountRepository();

            if(accountRepository.getCurrentAccount() != null && accountRepository.getCurrentAccount().getHypixelInfo() != null)
                timePlayed = accountRepository.getCurrentAccount().getHypixelInfo().getPlaytime();

            BlueZenith.getBlueZenith().setPlaytimeMeter(this);
        }
    }

    @Listener
    public void onTick(UpdatePlayerEvent event) {
        if(Minecraft.getMinecraft().isSingleplayer()) return;
        if(event.isPre()) {
            if (count) {
                if(BlueZenith.getBlueZenith().getHypixelStatMeter() != null && ServerUtils.hypixel ) {
                    timePlayedString = BlueZenith.getBlueZenith().getHypixelStatMeter().getPlaytimeString();
                }else
                if (timer.hasTimeReached(1000)) {
                    timer.reset();
                    supplyAsync(() -> timePlayedString = compute());
                }
            }
        }
    }

    @Listener
    public void onDisconnect(DisconnectEvent event) {
        if(event.reason != null) {
            BlueZenith.getBlueZenith().setPlaytimeMeter(null);
            if(!event.reason.getUnformattedText().equals("You were kicked for hacking!")) {
                if (timePlayedString != null) {
                    event.reason.appendText("\n\n§bYou played for §f§7" + timePlayedString);
                }
                timer.reset();
                timePlayed = 0;
                timePlayedString = null;
                count = false;
            }
        }
    }

    public String compute() {

        long diffInMillies = System.currentTimeMillis() - timePlayed;

        List<TimeUnit> units = new ArrayList<>(EnumSet.allOf(TimeUnit.class));
        Collections.reverse(units);

        long milliesRest = diffInMillies;
        final StringBuilder builder = new StringBuilder();

        for (TimeUnit unit : units) {

            long diff = unit.convert(milliesRest,TimeUnit.MILLISECONDS);
            long diffInMilliesForUnit = unit.toMillis(diff);
            milliesRest = milliesRest - diffInMilliesForUnit;

            if(diff > 0) {
                switch (unit) {
                    case DAYS: builder.append(diff).append("d "); break;
                    case HOURS: builder.append(diff).append("h "); break;
                    case MINUTES: builder.append(diff).append("m "); break;
                    case SECONDS: builder.append(diff).append("s "); break;
                }
            }
        }
        return builder.toString();
    }

}

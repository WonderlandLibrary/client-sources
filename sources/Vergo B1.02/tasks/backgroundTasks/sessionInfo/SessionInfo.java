package xyz.vergoclient.tasks.backgroundTasks.sessionInfo;

import java.text.DateFormat;

public class SessionInfo {

    public static int killCount;
    public static int winCount;
    public static int deathCount;
    public static int banCount;
    public static int gamesPlayed;

    public DateFormat dateFormat;

    public SessionInfo(DateFormat dateFormat, int killCount, int winCount, int deathCount, int banCount, int gamesPlayed) {
        this.dateFormat = dateFormat;
        this.killCount = killCount;
        this.winCount = winCount;
        this.deathCount = deathCount;
        this.banCount = banCount;
        this.gamesPlayed = gamesPlayed;
    }

    public static void resetSess() {
        startTime = System.currentTimeMillis();
        killCount = 0;
        winCount = 0;
        deathCount = 0;
        banCount = 0;
        gamesPlayed = 0;
    }

    public int getKillCount() {
        return killCount;
    }

    public int getWinCount() {
        return winCount;
    }

    public int getDeathCount() {
        return deathCount;
    }

    public int getBanCount() {
        return banCount;
    }

    public void setKillCount(int increment) {
        this.killCount = killCount + increment;
    }

    public void setWinCount(int increment) {
        this.killCount = killCount + increment;
    }

    public void setDeathCount(int increment) {
        this.killCount = killCount + increment;
    }

    public void setBanCount(int increment) {
        this.banCount = killCount + increment;
    }

    public static int[] getPlayTime() {
        long diff = getTimeDiff();
        long diffSeconds = 0, diffMinutes = 0, diffHours = 0;
        if (diff > 0) {
            diffSeconds = diff / 1000 % 60;
            diffMinutes = diff / (60 * 1000) % 60;
            diffHours = diff / (60 * 60 * 1000) % 24;
        }
       /* String str = (int) diffSeconds + "s";
        if (diffMinutes > 0) str = (int) diffMinutes + "m " + str;
        if (diffHours > 0) str = (int) diffHours + "h " + str;*/
        return new int[]{(int) diffHours, (int) diffMinutes, (int) diffSeconds};
    }

    public static long startTime = System.currentTimeMillis(), endTime = -1;

    public static long getTimeDiff() {
        return (endTime == -1 ? System.currentTimeMillis() : endTime) - startTime;
    }

}

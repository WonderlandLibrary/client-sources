package club.bluezenith.core.data.alt.info;

import club.bluezenith.BlueZenith;
import club.bluezenith.util.math.MathUtil;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import static club.bluezenith.util.math.MathUtil.convertMSToTimeString;
import static java.lang.Math.max;
import static java.lang.System.currentTimeMillis;

public class HypixelInfo {
    public final AccountInfo parent;

    @Expose
    @SerializedName("parentName")
    private String parentName;

    @Expose
    @SerializedName("bannedOn")
    private long bannedOn;

    @Expose
    @SerializedName("unbannedOn")
    private long unbannedOn;

    @Expose
    @SerializedName("startedPlayingAt")
    private long startedPlayingAt;

    @Expose
    @SerializedName("playtime")
    private long playtime;

    @Expose
    @SerializedName("kills")
    private int kills;

    @Expose
    @SerializedName("deaths")
    private int deaths;

    @Expose
    @SerializedName("gamesPlayed")
    private int gamesPlayed;

    @Expose
    @SerializedName("gamesWon")
    private int gamesWon;

    @Expose
    @SerializedName("winLoseRate")
    private float winLoseRate;

    @Expose
    @SerializedName("killDeathRate")
    private float killDeathRate;

    @Expose
    @SerializedName("playtimeAsString")
    private String playtimeString;

    public HypixelInfo(AccountInfo parent) {
        this.parent = parent;
        this.parentName = parent.getEffectiveUsername();
    }

    public boolean isBanned() { //the second condition should never happen
        long timeUntilUnban;

        return bannedOn != 0 || bannedOn != unbannedOn && (((timeUntilUnban = getTimeLeftUntilUnban()) == Long.MIN_VALUE) || timeUntilUnban > 0);
    }

    public long getTimeLeftUntilUnban() {
        if(bannedOn == 0) return 0;
        if(unbannedOn == Long.MIN_VALUE) return unbannedOn;

        final long diff = unbannedOn - currentTimeMillis();
        if(diff < 0) {
            final String content = parentName == null ? "An account's ban has expired!" : "Ban expired on account " + parent;
            BlueZenith.getBlueZenith().getNotificationPublisher().postSuccess(
                    "Account Manager",
                    content,
                    3500
            );
            bannedOn = 0;
        }
        return unbannedOn - currentTimeMillis();
    }

    public String getTimeLeftUntilUnbanAsString() {
        return convertMSToTimeString(getTimeLeftUntilUnban(), true);
    }

    public void setBanned(long unbanTimestamp) {
        if(!isBanned()) {
            bannedOn = currentTimeMillis();
            unbannedOn = unbanTimestamp;
        }
    }

    public void playerKilled() {
        kills++;
        killDeathRate = (float) MathUtil.round( kills / max(1F, deaths), 2);
    }

    public void died() {
        deaths++;
        killDeathRate = (float) MathUtil.round( kills / max(1F, deaths), 2);
    }

    public void gameStarted() {
       gamesPlayed++;
       winLoseRate = (float) MathUtil.round(gamesWon / gamesPlayed, 2);
    }

    public void gameWon() {
        gamesWon++;
        winLoseRate = (float) MathUtil.round(gamesWon / max(1F, gamesPlayed), 2);
    }

    public void loggedOntoHypixel() {
        this.startedPlayingAt = System.currentTimeMillis();
    }

    public long getPlaytime() {
        return this.playtime;
    }

    public void setPlaytime(long playtime) {
        this.playtime += playtime;
    }

    public void setPlaytimeString(String playtimeString) {
        this.playtimeString = playtimeString;
    }

    public String getPlaytimeString() {
        return this.playtimeString == null ? "00:00" : this.playtimeString;
    }

    public int getKills() {
        return kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public float getWinLoseRate() {
        return winLoseRate;
    }

    public float getKillDeathRate() {
        return (float) MathUtil.round(killDeathRate, 2);
    }

    @Override
    public String toString() {
        return "HypixelInfo{" +
                "parent=" + (parent == null ? "unknown" : parent.getEffectiveUsername()) +
                ", bannedOn=" + bannedOn +
                ", unbannedOn=" + unbannedOn +
                ", kills=" + kills +
                ", deaths=" + deaths +
                ", gamesPlayed=" + gamesPlayed +
                ", gamesWon=" + gamesWon +
                ", winLoseRate=" + winLoseRate +
                ", killDeathRate=" + killDeathRate +
                '}';
    }
}

package exhibition.management.spotify.othershit;

import com.google.gson.annotations.SerializedName;

public class SpotifyStatus {
   private int version;
   private String clientVersion;
   private boolean playing;
   private boolean shuffle;
   private boolean repeat;
   @SerializedName("play_enabled")
   private boolean playEnabled;
   @SerializedName("prev_enabled")
   private boolean prevEnabled;
   @SerializedName("next_enabled")
   private boolean nextEnabled;
   private SpotifyTrack track;
   @SerializedName("playing_position")
   private float playingPosition;
   private float volume;
   @SerializedName("server_time")
   private long serverTime;
   private boolean online;
   private boolean running;

   public int getVersion() {
      return this.version;
   }

   public String getClientVersion() {
      return this.clientVersion;
   }

   public boolean isPlaying() {
      return this.playing;
   }

   public boolean isShuffle() {
      return this.shuffle;
   }

   public boolean isRepeat() {
      return this.repeat;
   }

   public boolean isPlayEnabled() {
      return this.playEnabled;
   }

   public boolean isPrevEnabled() {
      return this.prevEnabled;
   }

   public boolean isNextEnabled() {
      return this.nextEnabled;
   }

   public SpotifyTrack getTrack() {
      return this.track;
   }

   public float getPlayingPosition() {
      return this.playingPosition;
   }

   public float getVolume() {
      return this.volume;
   }

   public long getServerTime() {
      return this.serverTime;
   }

   public void setServerTime(long serverTime) {
      this.serverTime = serverTime;
   }

   public boolean isOnline() {
      return this.online;
   }

   public boolean isRunning() {
      return this.running;
   }
}

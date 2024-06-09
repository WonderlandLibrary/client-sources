package exhibition.module.impl.other;

import exhibition.event.Event;
import exhibition.event.EventListener;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventRenderGui;
import exhibition.event.impl.EventTick;
import exhibition.management.spotify.Base64Renderer;
import exhibition.management.spotify.SpotifyManager;
import exhibition.management.spotify.othershit.SpotifyStatus;
import exhibition.management.spotify.othershit.SpotifyTrack;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.module.data.Setting;
import exhibition.util.RenderingUtil;
import exhibition.util.render.Colors;
import exhibition.util.render.ScrollingText;
import java.util.concurrent.TimeUnit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class Spotify extends Module {
   private static final Base64Renderer base64Renderer = new Base64Renderer(new ResourceLocation("textures/track.png"), 128, 128);
   private ScrollingText scrollingTrack;
   private ScrollingText scrollingArtist;
   public static SpotifyManager spotifyManager;
   private exhibition.util.Timer timer = new exhibition.util.Timer();
   private String X = "X";
   private String Y = "Y";

   public Spotify(ModuleData data) {
      super(data);
      this.settings.put(this.X, new Setting(this.X, Integer.valueOf(1880), "X position for radar.", 5.0D, 1.0D, 1920.0D));
      this.settings.put(this.Y, new Setting(this.Y, Integer.valueOf(950), "Y position for radar.", 5.0D, 1.0D, 1080.0D));
      spotifyManager = new SpotifyManager();
   }

   public void onDisable() {
      spotifyManager = new SpotifyManager();
   }

   public EventListener.Priority getPriority() {
      return EventListener.Priority.HIGHEST;
   }

   public void render(float x, float y, ScaledResolution resolution) {
      RenderingUtil.rectangle((double)x, (double)y, (double)(x + (float)this.getWidth(true)), (double)(y + (float)this.getHeight(false) - 2.0F), -14145496);
      SpotifyStatus status = spotifyManager != null && spotifyManager.isConnected() ? spotifyManager.getStatus() : null;
      SpotifyTrack track = status != null && status.getTrack() != null ? status.getTrack() : null;
      float trackRight = x + (float)this.getWidth(true) - 4.0F;
      int albumSize = this.getHeight(false) - 2;
      if (track != null && track.hasTrackInformation()) {
         if (track.getImage() != null && !track.getImage().equals(base64Renderer.getBase64String())) {
            base64Renderer.setBase64String(track.getImage(), "spotify/track_" + track.getTrackInformation().getId());
         } else if (track.getImage() == null && base64Renderer.getBase64String() != null) {
            base64Renderer.reset();
         }
      } else if (base64Renderer.getBase64String() != null) {
         base64Renderer.reset();
      }

      GlStateManager.pushMatrix();
      GlStateManager.translate(x, y, 0.0F);
      base64Renderer.renderImage(0, 0, albumSize, albumSize);
      GlStateManager.popMatrix();
      float infoLeft = x + (float)albumSize + 4.0F;
      float infoTop = y + 3.0F;
      int maxInfoWidth;
      float positionLineBottom;
      float positionLineLeft;
      if (track != null && track.getTrackInformation() != null) {
         maxInfoWidth = resolution.getScaledWidth();
         int var151 = resolution.getScaledHeight();
         int mouseX = Mouse.getX() * maxInfoWidth / mc.displayWidth;
         int mouseY = var151 - Mouse.getY() * var151 / mc.displayHeight - 1;
         positionLineBottom = x + (float)this.getHeight(false) + 57.0F;
         positionLineLeft = y + 3.0F + 9.5F;
         boolean hovering = (float)mouseX >= positionLineBottom && (float)mouseX <= positionLineBottom + 28.0F && (float)mouseY >= positionLineLeft && (float)mouseY <= positionLineLeft + 5.0F && Display.isActive();
         boolean clicked = hovering && System.nanoTime() - Mouse.getEventNanoseconds() < 300000000L && Mouse.getEventButton() == 0 && Mouse.getEventButtonState();
         String pp = status.isPlaying() ? "Pause" : " Play";
         float width = (float)mc.fontRendererObj.getStringWidth(pp);
         GlStateManager.pushMatrix();
         GlStateManager.translate(infoLeft + 72.0F - width / 4.0F, infoTop + 9.5F, 0.0F);
         GlStateManager.scale(0.5D, 0.5D, 0.5D);
         mc.fontRendererObj.drawString(pp.replace("", ""), 0.0F, 0.0F, clicked ? -1 : (hovering ? Colors.getColor(240) : 11184810));
         GlStateManager.popMatrix();
         if (clicked && this.timer.delay(100.0F)) {
            boolean XD = status.isPlaying();
            (new Thread(() -> {
               spotifyManager.pauseSong(XD);
            })).start();
            this.timer.reset();
         }
      }

      maxInfoWidth = (int)(trackRight - infoLeft);
      String trackName = spotifyManager != null && track != null && track.getTrackInformation() != null ? track.getTrackInformation().getName() : "No song.";
      String artistName = track != null && track.getArtistInformation() != null ? track.getArtistInformation().getName() : null;
      if (trackName != null && (this.scrollingTrack == null || !trackName.equals(this.scrollingTrack.getText()))) {
         this.scrollingTrack = new ScrollingText(trackName, maxInfoWidth, 8, -14145496, 11184810);
      } else if (this.scrollingTrack != null && trackName == null) {
         this.scrollingTrack = null;
      }

      if (artistName == null || this.scrollingArtist != null && artistName.equals(this.scrollingArtist.getText())) {
         if (this.scrollingArtist != null && artistName == null) {
            this.scrollingArtist = null;
         }
      } else {
         this.scrollingArtist = new ScrollingText(artistName, maxInfoWidth - 30, 5, -14145496, 11184810);
      }

      if (this.scrollingTrack != null) {
         this.scrollingTrack.setChild(this.scrollingArtist);
         this.scrollingTrack.setScale((float)resolution.getScaleFactor());
         this.scrollingTrack.render(infoLeft, infoTop);
      }

      if (this.scrollingArtist != null) {
         this.scrollingArtist.setParent(this.scrollingTrack);
         this.scrollingArtist.setScale((float)resolution.getScaleFactor());
         this.scrollingArtist.render(infoLeft, infoTop + 9.0F);
      }

      float positionLineTop = y + (float)this.getHeight(false) - 8.0F;
      positionLineBottom = positionLineTop + 2.5F;
      positionLineLeft = infoLeft + 11.0F;
      float positionLineRight = trackRight - 11.0F;
      float positionWidth = positionLineRight - positionLineLeft;
      RenderingUtil.rectangle((double)positionLineLeft, (double)positionLineTop, (double)positionLineRight, (double)positionLineBottom, -12566464);
      double playingPosition = status != null ? (double)(status.getPlayingPosition() * 1000.0F) : 0.0D;
      long millisSinceUpdate = status != null && status.isPlaying() ? (long)((int)(System.currentTimeMillis() - status.getServerTime())) : 0L;
      double trackPosition = playingPosition + (double)millisSinceUpdate;
      long trackLength = track != null ? (long)(track.getLength() * 1000) : 1000L;
      double trackPercentage = trackPosition / (double)trackLength;
      if (trackPercentage > 1.0D) {
         trackPercentage = 1.0D;
      }

      RenderingUtil.rectangle((double)positionLineLeft, (double)positionLineTop, (double)positionLineLeft + trackPercentage * (double)positionWidth, (double)positionLineBottom, -14756000);
      this.drawScaledString(this.convertToClock((long)trackPosition), infoLeft - 2.5F, positionLineTop - 0.5F, 11184810, 0.5F);
      this.drawScaledString(this.convertToClock(trackLength), positionLineRight + 1.0F, positionLineTop - 0.5F, 11184810, 0.5F);
   }

   public String convertToClock(long millis) {
      return String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(millis), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
   }

   private void drawScaledString(String string, float x, float y, int color, float scale) {
      GlStateManager.pushMatrix();
      GlStateManager.translate(x, y, 0.0F);
      GlStateManager.scale(scale, scale, scale);
      Minecraft.getMinecraft().fontRendererObj.drawString(string, 0.0F, 0.0F, color);
      GlStateManager.popMatrix();
   }

   public int getWidth(boolean dummy) {
      return 120;
   }

   public int getHeight(boolean dummy) {
      return 28;
   }

   @RegisterEvent(
      events = {EventRenderGui.class, EventTick.class}
   )
   public void onEvent(Event event) {
      float xOffset = ((Number)((Setting)this.settings.get(this.X)).getValue()).floatValue() / 2.0F;
      float yOffset = ((Number)((Setting)this.settings.get(this.Y)).getValue()).floatValue() / 2.0F;
      if (event instanceof EventRenderGui) {
         EventRenderGui er = (EventRenderGui)event.cast();
         GlStateManager.pushMatrix();
         GlStateManager.scale(1.0F, 1.0F, 1.0F);
         this.render(xOffset, yOffset, er.getResolution());
         GlStateManager.popMatrix();
      } else {
         spotifyManager.onEvent();
      }

   }

   static {
      base64Renderer.setInterpolateLinear(true);
   }
}

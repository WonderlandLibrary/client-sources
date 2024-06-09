package exhibition.management.notifications.user;

import exhibition.Client;
import exhibition.management.animate.Translate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class Notification implements INotification {
   private String header;
   private String subtext;
   private long start;
   private long displayTime;
   private Notifications.Type type;
   private float x;
   private float tarX;
   private float y;
   public Translate translate;
   private long last;

   public long getLast() {
      return this.last;
   }

   public void setLast(long last) {
      this.last = last;
   }

   protected Notification(String header, String subtext, long displayTime, Notifications.Type type) {
      this.header = header;
      this.subtext = subtext;
      this.start = System.currentTimeMillis();
      this.displayTime = displayTime;
      this.type = type;
      this.last = System.currentTimeMillis();
      ScaledResolution XD = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
      this.y = (float)(XD.getScaledHeight() + 20);
      this.x = (float)XD.getScaledWidth();
      float subHeaderWidth = Client.subHeader.getWidth(subtext);
      float headerWidth = Client.header.getWidth(header);
      this.tarX = (float)(XD.getScaledWidth() - 25) - (headerWidth > subHeaderWidth ? headerWidth : subHeaderWidth);
      this.translate = new Translate(this.x, this.y);
   }

   public long checkTime() {
      return System.currentTimeMillis() - this.getDisplayTime();
   }

   public String getHeader() {
      return this.header;
   }

   public String getSubtext() {
      return this.subtext;
   }

   public long getStart() {
      return this.start;
   }

   public long getDisplayTime() {
      return this.displayTime;
   }

   public Notifications.Type getType() {
      return this.type;
   }

   public float getX() {
      return this.x;
   }

   public void setX(int x) {
      this.x = (float)x;
   }

   public float getTarX() {
      return this.tarX;
   }

   public float getTarY() {
      return 0.0F;
   }

   public void setTarX(int x) {
      this.tarX = (float)x;
   }

   public void setY(int y) {
      this.y = (float)y;
   }

   public float getY() {
      return this.y;
   }
}

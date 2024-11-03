package xyz.cucumber.base.interf.altmanager.ut;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;
import xyz.cucumber.base.interf.altmanager.AltManager;
import xyz.cucumber.base.utils.RenderUtils;
import xyz.cucumber.base.utils.position.PositionUtils;
import xyz.cucumber.base.utils.render.ColorUtils;
import xyz.cucumber.base.utils.render.Fonts;

public class AltManagerSession {
   private Session session;
   private AltManager manager;
   private PositionUtils position;

   public AltManagerSession(AltManager manager, Session session) {
      this.session = session;
      this.manager = manager;
      this.position = new PositionUtils(0.0, 0.0, 100.0, 15.0, 1.0F);
   }

   public void draw(int mouseX, int mouseY) {
      RenderUtils.drawRoundedRect(this.position.getX(), this.position.getY(), this.position.getX2(), this.position.getY2(), 1344348449, 1.2);
      String[] s = this.session.getUsername().split("");
      double w = 0.0;

      for (String t : s) {
         Fonts.getFont("rb-b")
            .drawStringWithShadow(
               t,
               this.position.getX() + 3.0 + w,
               this.position.getY() + 6.0,
               ColorUtils.mix(-10007340, -12751688, Math.sin(Math.toRadians((double)(System.nanoTime() / 1000000L) + w * 10.0) / 3.0) + 1.0, 2.0),
               1157627904
            );
         w += Fonts.getFont("rb-b").getWidth(t);
      }

      Fonts.getFont("rb-r-13")
         .drawString(
            this.session.getToken().equals("0") ? "Cracked" : "Online",
            this.position.getX()
               + this.position.getWidth() / 2.0
               - Fonts.getFont("rb-r").getWidth(this.session.getToken().equals("0") ? "Cracked" : "Online") / 2.0,
            this.position.getY() + 6.0,
            -8947849
         );
      if (this == this.manager.active) {
         RenderUtils.drawOutlinedRoundedRect(this.position.getX(), this.position.getY(), this.position.getX2(), this.position.getY2(), -14932393, 1.2, 0.5);
      }

      if (this.session == Minecraft.getMinecraft().session) {
         RenderUtils.drawImage(this.position.getX2() - 9.0, this.position.getY() + 4.5, 6.0, 6.0, new ResourceLocation("client/images/check.png"), -16711936);
      }
   }

   public void onClick(int mouseX, int mouseY, int b) {
      if (this.manager.active == this) {
         Minecraft.getMinecraft().session = this.session;
      } else {
         this.manager.active = this;
      }
   }

   public Session getSession() {
      return this.session;
   }

   public void setSession(Session session) {
      this.session = session;
   }

   public AltManager getManager() {
      return this.manager;
   }

   public void setManager(AltManager manager) {
      this.manager = manager;
   }

   public PositionUtils getPosition() {
      return this.position;
   }

   public void setPosition(PositionUtils position) {
      this.position = position;
   }
}

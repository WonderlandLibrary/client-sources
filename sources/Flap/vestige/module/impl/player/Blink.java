package vestige.module.impl.player;

import com.mojang.authlib.GameProfile;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.network.Packet;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.C00PacketLoginStart;
import net.minecraft.network.login.client.C01PacketEncryptionResponse;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.status.client.C00PacketServerQuery;
import net.minecraft.util.Vec3;
import vestige.Flap;
import vestige.event.Listener;
import vestige.event.impl.PacketSendEvent;
import vestige.event.impl.RenderEvent;
import vestige.event.impl.TickEvent;
import vestige.font.VestigeFontRenderer;
import vestige.module.Category;
import vestige.module.Module;
import vestige.setting.AbstractSetting;
import vestige.setting.impl.BooleanSetting;
import vestige.setting.impl.DoubleSetting;
import vestige.util.network.PacketUtil;
import vestige.util.player.PlayerUtil;

public class Blink extends Module {
   public final List<Packet<?>> blinkedPackets = new ArrayList();
   private long startTime = -1L;
   private Vec3 pos;
   public static final int color = (new Color(72, 125, 227)).getRGB();
   private final BooleanSetting overlay = new BooleanSetting("Overlay", false);
   private final BooleanSetting initialpos = new BooleanSetting("Show initial pos", false);
   private final BooleanSetting pulse = new BooleanSetting("Pulse", false);
   private final DoubleSetting pulsedelay = new DoubleSetting("Pulse delay", () -> {
      return this.pulse.isEnabled();
   }, 1000.0D, 0.0D, 10000.0D, 100.0D);
   private VestigeFontRenderer productSans;

   public Blink() {
      super("Blink", Category.WORLD);
      this.addSettings(new AbstractSetting[]{this.overlay, this.initialpos, this.pulse, this.pulsedelay});
   }

   public void onEnable() {
      if (this.initialpos.isEnabled()) {
         this.spawnFakePlayer();
      }

      this.start();
   }

   public void start() {
      this.blinkedPackets.clear();
      this.pos = new Vec3(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
      this.startTime = System.currentTimeMillis();
   }

   public void onTick(TickEvent event) {
   }

   public boolean onDisable() {
      if (this.initialpos.isEnabled()) {
         this.removeFakePlayer();
      }

      this.reset();
      return false;
   }

   private void reset() {
      synchronized(this.blinkedPackets) {
         Iterator var2 = this.blinkedPackets.iterator();

         while(true) {
            if (!var2.hasNext()) {
               break;
            }

            Packet<?> packet = (Packet)var2.next();
            PacketUtil.sendPacketNoEvent2(packet);
         }
      }

      this.blinkedPackets.clear();
      this.pos = null;
   }

   @Listener
   public final void onRender(RenderEvent event) {
      boolean inChat = mc.currentScreen instanceof GuiChat;
      ScaledResolution scaledRes = new ScaledResolution(mc);
      int screenWidth = scaledRes.getScaledWidth();
      int screenHeight = scaledRes.getScaledHeight();
      this.productSans = Flap.instance.getFontManager().getProductSans();
      if (this.isEnabled() || inChat) {
         if (this.blinkedPackets.size() > 999) {
            this.productSans.drawStringWithShadow("Blinking§f: §4" + this.blinkedPackets.size(), (double)(screenWidth / 2) - this.productSans.getStringWidth("Blinking§f: §a" + this.blinkedPackets.size()) / 2.0D, (double)screenHeight / 1.9D, Color.WHITE.getRGB());
         } else if (this.blinkedPackets.size() > 99) {
            this.productSans.drawStringWithShadow("Blinking§f: §6" + this.blinkedPackets.size(), (double)(screenWidth / 2) - this.productSans.getStringWidth("Blinking§f: §a" + this.blinkedPackets.size()) / 2.0D, (double)screenHeight / 1.9D, Color.WHITE.getRGB());
         } else {
            this.productSans.drawStringWithShadow("Blinking§f: §a" + this.blinkedPackets.size(), (double)(screenWidth / 2) - this.productSans.getStringWidth("Blinking§f: §a" + this.blinkedPackets.size()) / 2.0D, (double)screenHeight / 1.9D, Color.WHITE.getRGB());
         }
      }

   }

   @Listener
   public void onSendPacket(PacketSendEvent e) {
      if (!PlayerUtil.nullCheck()) {
         this.toggle();
      } else {
         Packet<?> packet = e.getPacket();
         if (!packet.getClass().getSimpleName().startsWith("S")) {
            if (!(packet instanceof C00Handshake) && !(packet instanceof C00PacketLoginStart) && !(packet instanceof C00PacketServerQuery) && !(packet instanceof C01PacketEncryptionResponse) && !(packet instanceof C01PacketChatMessage)) {
               this.blinkedPackets.add(packet);
               e.setCancelled(true);
               if (this.pulse.isEnabled() && (double)(System.currentTimeMillis() - this.startTime) >= this.pulsedelay.getValue()) {
                  this.reset();
                  if (this.initialpos.isEnabled()) {
                     this.removeFakePlayer();
                     this.spawnFakePlayer();
                  }

                  this.start();
               }

            }
         }
      }
   }

   public void removeFakePlayer() {
      Minecraft mc = Minecraft.getMinecraft();
      mc.theWorld.removeEntityFromWorld(-1234);
   }

   public void spawnFakePlayer() {
      Minecraft mc = Minecraft.getMinecraft();
      GameProfile gameProfile = new GameProfile(UUID.randomUUID(), "You");
      EntityOtherPlayerMP fakePlayer = new EntityOtherPlayerMP(mc.theWorld, gameProfile);
      fakePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
      fakePlayer.rotationYaw = mc.thePlayer.rotationYaw;
      fakePlayer.rotationPitch = mc.thePlayer.rotationPitch;
      fakePlayer.rotationYawHead = mc.thePlayer.rotationYawHead;
      fakePlayer.renderYawOffset = mc.thePlayer.renderYawOffset;
      fakePlayer.setCurrentItemOrArmor(0, mc.thePlayer.getCurrentEquippedItem());
      mc.theWorld.addEntityToWorld(-1234, fakePlayer);
   }
}

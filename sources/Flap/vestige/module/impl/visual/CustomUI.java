package vestige.module.impl.visual;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.shader.Shader;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.network.play.server.S13PacketDestroyEntities;
import net.minecraft.util.ResourceLocation;
import vestige.Flap;
import vestige.event.Listener;
import vestige.event.impl.PacketReceiveEvent;
import vestige.event.impl.UpdateEvent;
import vestige.module.Category;
import vestige.module.Module;
import vestige.module.impl.combat.Killaura;
import vestige.setting.AbstractSetting;
import vestige.setting.impl.BooleanSetting;
import vestige.setting.impl.DoubleSetting;
import vestige.setting.impl.ModeSetting;

public class CustomUI extends Module {
   public final ModeSetting chatbackground = new ModeSetting("Chat Background", "True", new String[]{"True", "False"});
   public final DoubleSetting scoreboardX = new DoubleSetting("Scoreboard X", 0.0D, -300.0D, 1000.0D, 0.05D);
   public final DoubleSetting scoreboardy = new DoubleSetting("Scoreboard Y", 0.0D, -300.0D, 1000.0D, 0.05D);
   public final DoubleSetting Chatx = new DoubleSetting("Chat X", 0.0D, -300.0D, 1000.0D, 0.05D);
   public final DoubleSetting Chaty = new DoubleSetting("Chat Y", 0.0D, -300.0D, 1000.0D, 0.05D);
   public final BooleanSetting cameraclip = new BooleanSetting("No Camera Clip", true);
   public final BooleanSetting motionblur = new BooleanSetting("Motion Blur", true);
   public final BooleanSetting killsound = new BooleanSetting("Kill Sound", true);
   public final DoubleSetting motionBlurStrength = new DoubleSetting("Motion Blur Strength", 6.0D, 1.0D, 9.0D, 1.0D);
   public final ModeSetting soundstype = new ModeSetting("Sound Module", "False", new String[]{"False", "Sigma 5", "Rise", "QuickMacro"});
   public final DoubleSetting soundvolume = new DoubleSetting("Sound Pitch", 1.0D, 1.0D, 5.0D, 1.0D);
   public final ModeSetting backgroundtype = new ModeSetting("Background Type", "Default", new String[]{"Default", "Blur", "Default Blur", "None"});
   private ShaderGroup motionBlurShader;
   private boolean lastPerspective = false;
   int value;

   public CustomUI() {
      super("CustomUI", Category.VISUAL);
      this.addSettings(new AbstractSetting[]{this.chatbackground, this.scoreboardX, this.scoreboardy, this.Chatx, this.Chaty, this.cameraclip, this.motionblur, this.killsound, this.motionBlurStrength, this.soundstype, this.soundvolume, this.backgroundtype});
   }

   public void onEnable() {
      if (this.motionblur.isEnabled()) {
         this.value = (int)this.motionBlurStrength.getValue();
      }

   }

   @Listener
   public void receibePacketrs(PacketReceiveEvent event) {
      if (this.killsound.isEnabled() && event.getPacket() instanceof S13PacketDestroyEntities) {
         S13PacketDestroyEntities packet = (S13PacketDestroyEntities)event.getPacket();
         int[] var3 = packet.getEntityIDs();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            int id = var3[var5];
            if (((Killaura)Flap.instance.getModuleManager().getModule(Killaura.class)).getTarget() != null && id == ((Killaura)Flap.instance.getModuleManager().getModule(Killaura.class)).getTarget().getEntityId()) {
               Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("minecraft", "breakblock"), (float)((CustomUI)Flap.instance.getModuleManager().getModule(CustomUI.class)).soundvolume.getValue()));
            }
         }
      }

   }

   public boolean onDisable() {
      this.value = (int)this.motionBlurStrength.getValue();
      return false;
   }

   @Listener
   public void onUpdate(UpdateEvent event) {


   }


}

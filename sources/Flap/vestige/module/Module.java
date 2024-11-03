package vestige.module;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;
import vestige.Flap;
import vestige.module.impl.client.NotificationManager;
import vestige.module.impl.visual.CustomUI;
import vestige.setting.AbstractSetting;
import vestige.util.IMinecraft;
import vestige.util.misc.AudioUtil;

public abstract class Module implements IMinecraft {
   private String name;
   private Category category;
   private String modes;
   private int key;
   private boolean enabled;
   private boolean listening;
   protected EventListenType listenType;
   private ArrayList<AbstractSetting> settings = new ArrayList();

   public Module(String name, Category category) {
      this.name = name;
      this.category = category;
      this.listenType = EventListenType.AUTOMATIC;
   }

   protected void onEnable() {
   }

   protected boolean onDisable() {
      return false;
   }

   public void onClientStarted() {
   }

   public String getInfo() {
      return "";
   }

   public final void setEnabled(boolean enabled) {
      if (this.enabled != enabled) {
         this.enabled = enabled;
         if (enabled) {
            this.onEnable();
            if (this.listenType == EventListenType.AUTOMATIC) {
               this.startListening();
            }
         } else {
            if (this.listenType == EventListenType.AUTOMATIC) {
               this.stopListening();
            }

            this.onDisable();
         }
      }

   }

   public final void setEnabledSilently(boolean enabled) {
      if (this.enabled != enabled) {
         this.enabled = enabled;
         if (enabled) {
            if (this.listenType == EventListenType.AUTOMATIC) {
               this.startListening();
            }
         } else if (this.listenType == EventListenType.AUTOMATIC) {
            this.stopListening();
         }
      }

   }

   public final void toggle() {
      this.enabled = !this.enabled;
      AudioUtil.playSound("flap.sounds.oppen");
      String var1;
      byte var2;
      if (this.enabled) {
         NotificationManager.showNotification("Enabled", this.name + " has been enabled", NotificationManager.NotificationType.WARNING, 2000L);
         var1 = ((CustomUI)Flap.instance.getModuleManager().getModule(CustomUI.class)).soundstype.getMode();
         var2 = -1;
         switch(var1.hashCode()) {
         case -639037953:
            if (var1.equals("QuickMacro")) {
               var2 = 2;
            }
            break;
         case -542080806:
            if (var1.equals("Sigma 5")) {
               var2 = 0;
            }
            break;
         case 2547433:
            if (var1.equals("Rise")) {
               var2 = 1;
            }
         }

         switch(var2) {
         case 0:
            Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("Flap", "enables5"), (float)((CustomUI)Flap.instance.getModuleManager().getModule(CustomUI.class)).soundvolume.getValue()));
            break;
         case 1:
            Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("Flap", "enablerise"), (float)((CustomUI)Flap.instance.getModuleManager().getModule(CustomUI.class)).soundvolume.getValue()));
            break;
         case 2:
            Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("minecraft", "enablequickmacro"), (float)((CustomUI)Flap.instance.getModuleManager().getModule(CustomUI.class)).soundvolume.getValue()));
         }

         this.onEnable();
         if (this.listenType == EventListenType.AUTOMATIC) {
            this.startListening();
         }
      } else {
         var1 = ((CustomUI)Flap.instance.getModuleManager().getModule(CustomUI.class)).soundstype.getMode();
         var2 = -1;
         switch(var1.hashCode()) {
         case -639037953:
            if (var1.equals("QuickMacro")) {
               var2 = 2;
            }
            break;
         case -542080806:
            if (var1.equals("Sigma 5")) {
               var2 = 0;
            }
            break;
         case 2547433:
            if (var1.equals("Rise")) {
               var2 = 1;
            }
         }

         switch(var2) {
         case 0:
            Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("minecraft", "disables5"), (float)((CustomUI)Flap.instance.getModuleManager().getModule(CustomUI.class)).soundvolume.getValue()));
            break;
         case 1:
            Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("minecraft", "disablerise"), (float)((CustomUI)Flap.instance.getModuleManager().getModule(CustomUI.class)).soundvolume.getValue()));
            break;
         case 2:
            Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("minecraft", "disablequickmacro"), (float)((CustomUI)Flap.instance.getModuleManager().getModule(CustomUI.class)).soundvolume.getValue()));
         }

         if (this.listenType == EventListenType.AUTOMATIC) {
            this.stopListening();
         }

         this.onDisable();
      }

   }

   public final void toggleSilently() {
      this.enabled = !this.enabled;
      if (this.enabled) {
         if (this.listenType == EventListenType.AUTOMATIC) {
            this.startListening();
         }
      } else if (this.listenType == EventListenType.AUTOMATIC) {
         this.stopListening();
      }

   }

   protected final void startListening() {
      if (!this.listening) {
         Flap.instance.getEventManager().register(this);
         this.listening = true;
      }

   }

   protected final void stopListening() {
      if (this.listening) {
         Flap.instance.getEventManager().unregister(this);
         this.listening = false;
      }

   }

   public void addSettings(AbstractSetting... settings) {
      this.settings.addAll(Arrays.asList(settings));
   }

   public <T extends AbstractSetting> T getSettingByName(String name) {
      Optional<AbstractSetting> optional = this.settings.stream().filter((m) -> {
         return m.getName().equals(name);
      }).findFirst();
      return optional.isPresent() ? (T) optional.get() : null;
   }

   public String getSuffix() {
      return null;
   }

   public final String getDisplayName() {
      return this.getDisplayName(ChatFormatting.GRAY);
   }

   public final String getDisplayName(ChatFormatting formatting) {
      String tag = this.getSuffix();
      return tag != null && !tag.equals("") ? this.name + formatting + " " + tag : this.name;
   }

   public String getName() {
      return this.name;
   }

   public Category getCategory() {
      return this.category;
   }

   public String getModes() {
      return this.modes;
   }

   public int getKey() {
      return this.key;
   }

   public boolean isEnabled() {
      return this.enabled;
   }

   public boolean isListening() {
      return this.listening;
   }

   public EventListenType getListenType() {
      return this.listenType;
   }

   public ArrayList<AbstractSetting> getSettings() {
      return this.settings;
   }

   public void setKey(int key) {
      this.key = key;
   }
}

package net.augustus.modules;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.awt.Color;
import java.security.SecureRandom;

import net.augustus.Augustus;
import net.augustus.modules.render.ClickGUI;
import net.augustus.notify.GeneralNotifyManager;
import net.augustus.notify.NotificationType;
import net.augustus.notify.xenza.Notification;
import net.augustus.notify.xenza.NotificationsManager;
import net.augustus.utils.AnimationUtil;
import net.augustus.utils.interfaces.MC;
import net.augustus.utils.interfaces.MM;
import net.augustus.utils.interfaces.SM;
import net.augustus.utils.skid.lorious.anims.Animation;
import net.augustus.utils.skid.lorious.anims.Easings;
import net.augustus.utils.sound.SoundUtil;
import net.lenni0451.eventapi.manager.EventManager;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;

public class Module implements MC, MM, SM, Comparable<Module> {
   public Animation yPos = new Animation();
   public Animation xPos = new Animation();
   @Expose
   @SerializedName("Name")
   private String name;
   private String displayName = "";
   @Expose
   @SerializedName("Toggled")
   private boolean toggled;
   @Expose
   @SerializedName("Key")
   private int key;
   @Expose
   @SerializedName("Category")
   private Categorys category;
   private Color color;
   private float x;
   private float y;
   private float minX;
   private float maxX;
   private float moduleWidth = 0.0F;
   public AnimationUtil animationUtil;
   protected ScaledResolution sr = new ScaledResolution(mc);
   public SecureRandom RANDOM = new SecureRandom();

   public Module(String name, Color color, Categorys category) {
      this.name = name;
      this.color = color;
      this.category = category;
      this.displayName = name;
      Augustus.getInstance().getManager().getModules().add(this);
   }

   public final void toggle() {
      if (this.toggled) {
         if (mc.theWorld != null && mm.arrayList.toggleSound.getBoolean() && !this.equals(mm.clickGUI)) {
            SoundUtil.play(SoundUtil.toggleOffSound);
         }
         //NotificationsManager.addNotification(new Notification("Toggled " + name + " off", Notification.Type.ToggleOff));
         GeneralNotifyManager.addNotification("Toggled " + name + " off", NotificationType.ToggleOff);
         this.toggled = false;
         mm.getActiveModules().remove(this);
         this.onPreDisable();
         EventManager.unregister(this);
         this.onDisable();
      } else {
         if (mc.theWorld != null && mm.arrayList.toggleSound.getBoolean() && !this.equals(mm.clickGUI)) {
            SoundUtil.play(SoundUtil.toggleOnSound);
         }
         //NotificationsManager.addNotification(new Notification("Toggled " + name + " on", Notification.Type.ToggleOn));
         GeneralNotifyManager.addNotification("Toggled " + name + " on", NotificationType.ToggleOn);
         this.toggled = true;
         if (!(this instanceof ClickGUI)) {
            mm.getActiveModules().add(this);
         }

         this.onEnable();
         EventManager.register(this);
      }

      mm.arrayList.updateSorting();
   }

   public void updatePos() {
      if (this.isToggled()) {
         if (this.getXPos().getTarget() != Augustus.getInstance().getLoriousFontService().getComfortaa18().getStringWidth(this.getDisplayName()) + 5.0) {
            this.getXPos().animate(Augustus.getInstance().getLoriousFontService().getComfortaa18().getStringWidth(this.getDisplayName()) + 5.0, 350.0, Easings.QUAD_BOTH);
         }
      } else if (this.getXPos().getTarget() != -5.0) {
         this.getXPos().animate(-5.0, 350.0, Easings.QUAD_BOTH);
      }
   }

   public void onEnable() {
   }

   public void onDisable() {
   }

   public void onPreDisable() {
   }

   public Animation getYPos() {
      return this.yPos;
   }

   public Animation getXPos() {
      return this.xPos;
   }

   public Color getColor() {
      return this.color;
   }

   public void setColor(Color color) {
      this.color = color;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getDisplayName() {
      return this.displayName;
   }

   public void setDisplayName(String displayName) {
      String lastName = this.displayName;
      if (mc.thePlayer != null) {
         if (mm.arrayList.isToggled() && mm.arrayList.suffix.getBoolean()) {
            this.displayName = displayName;
         } else {
            this.displayName = this.name;
         }
      } else {
         this.displayName = displayName;
      }

      if (!this.displayName.equalsIgnoreCase(lastName)) {
         mm.arrayList.updateSorting();
      }
   }

   public boolean isToggled() {
      return this.toggled;
   }

   public void setToggled(boolean toggled) {
      if (this.toggled != toggled) {
         this.toggle();
      }
   }

   public int getKey() {
      return this.key;
   }

   public void setKey(int key) {
      this.key = key;
   }

   public Categorys getCategory() {
      return this.category;
   }

   public void setCategory(Categorys category) {
      this.category = category;
   }

   public float getX() {
      return this.x;
   }

   public void setX(float x) {
      this.x = x;
   }

   public float getY() {
      return this.y;
   }

   public void setY(float y) {
      this.y = y;
   }

   public float getMaxX() {
      return this.maxX;
   }

   public void setMaxX(float maxX) {
      this.maxX = maxX;
   }

   public float getMinX() {
      return this.minX;
   }

   public void setMinX(float minX) {
      this.minX = minX;
   }

   public AnimationUtil getAnimationUtil() {
      return this.animationUtil;
   }

   public float getModuleWidth() {
      return this.moduleWidth;
   }

   public void setModuleWidth(float moduleWidth) {
      this.moduleWidth = moduleWidth;
   }

   public void setAnimationUtil(AnimationUtil animationUtil) {
      this.animationUtil = animationUtil;
   }

   public void readModule(Module module) {
      this.setName(module.getName());
      if (!(this instanceof ClickGUI)) {
         this.setToggled(module.isToggled());
      }

      this.setCategory(module.getCategory());
      this.setKey(module.getKey());
   }

   public void readConfig(Module module) {
      if (!(this instanceof ClickGUI)) {
         this.setToggled(module.isToggled());
      }
   }

   public int compareTo(Module module) {
      if (mm.arrayList.sortOption.getSelected().equals("Alphabet")) {
         return -module.getName().compareTo(this.getName());
      } else {
         return mm.arrayList.font.getSelected().equals("Minecraft")
            ? mc.fontRendererObj.getStringWidth(module.getDisplayName()) - mc.fontRendererObj.getStringWidth(this.getDisplayName())
            : Math.round(
               mm.arrayList.getCustomFont().getStringWidth(module.getDisplayName())
                  - mm.arrayList.getCustomFont().getStringWidth(FontRenderer.getFormatFromString(module.getDisplayName()))
                  - (
                     mm.arrayList.getCustomFont().getStringWidth(this.getDisplayName())
                        - mm.arrayList.getCustomFont().getStringWidth(FontRenderer.getFormatFromString(this.getDisplayName()))
                  )
            );
      }
   }
   
   public String getSuffix() {
      return this.displayName.replace(this.name, "");
   }
}

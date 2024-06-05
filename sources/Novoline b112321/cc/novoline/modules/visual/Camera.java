package cc.novoline.modules.visual;

import cc.novoline.events.EventTarget;
import cc.novoline.events.events.EventState;
import cc.novoline.events.events.MotionUpdateEvent;
import cc.novoline.events.events.Render2DEvent;
import cc.novoline.events.events.SettingEvent;
import cc.novoline.gui.screen.setting.Manager;
import cc.novoline.gui.screen.setting.Setting;
import cc.novoline.gui.screen.setting.SettingType;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.configurations.annotation.Property;
import cc.novoline.modules.configurations.property.object.BooleanProperty;
import cc.novoline.modules.configurations.property.object.IntProperty;
import cc.novoline.modules.configurations.property.object.PropertyFactory;
import cc.novoline.modules.configurations.property.object.StringProperty;
import cc.novoline.modules.visual.HUD;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.util.Locale;
import java.util.function.Supplier;
import org.lwjgl.input.Mouse;

public class Camera extends AbstractModule {
   @Property("mc-lock")
   BooleanProperty mcLock = PropertyFactory.createBoolean(Boolean.valueOf(true));
   @Property("view-clip")
   BooleanProperty view_clip = PropertyFactory.createBoolean(Boolean.valueOf(true));
   @Property("no-hurt-cam")
   BooleanProperty no_hurt_cam = PropertyFactory.createBoolean(Boolean.valueOf(true));
   @Property("motion-blur")
   BooleanProperty motion_blur = PropertyFactory.createBoolean(Boolean.valueOf(true));
   @Property("amount")
   private final IntProperty amount = (IntProperty)((IntProperty)PropertyFactory.createInt(Integer.valueOf(1)).minimum(Integer.valueOf(1))).maximum(Integer.valueOf(10));
   @Property("mouse-button")
   private StringProperty C = PropertyFactory.createString("MB").acceptableValues(new String[]{"MB", "ARB", "ALB"});
   private boolean released;

   public Camera(ModuleManager var1) {
      super(var1, EnumModuleType.VISUALS, "Camera");
      Manager.put(new Setting("CAMERA_MC_LOCK", "Camera Lock", SettingType.CHECKBOX, this, this.mcLock));
      SettingType var10004 = SettingType.COMBOBOX;
      StringProperty var10006 = this.C;
      BooleanProperty var10007 = this.mcLock;
      this.mcLock.getClass();
      Manager.put(new Setting("CAMERA_BUTTON", "Camera Button", var10004, this, var10006, var10007::get));
      Manager.put(new Setting("CAMERA_VC", "View Clip", SettingType.CHECKBOX, this, this.view_clip));
      Manager.put(new Setting("CAMERA_NHC", "No Hurt Cam", SettingType.CHECKBOX, this, this.no_hurt_cam));
      Manager.put(new Setting("CAMERA_MB", "Motion Blur", SettingType.CHECKBOX, this, this.motion_blur));
      var10004 = SettingType.SLIDER;
      IntProperty var3 = this.amount;
      BooleanProperty var10008 = this.motion_blur;
      this.motion_blur.getClass();
      Manager.put(new Setting("CAMERA_BA", "Blur Amount", var10004, this, var3, 1.0D, var10008::get));
   }

   public void onEnable() {
      int var1 = HUD.h();
      if(((Boolean)this.motion_blur.get()).booleanValue()) {
         this.mc.entityRenderer.theShaderGroup = null;
         if(this.mc.world != null) {
            try {
               this.mc.entityRenderer.loadCustomShader();
            } catch (IOException var3) {
               var3.printStackTrace();
            }
         }
      }

   }

   public void onDisable() {
      this.mc.entityRenderer.theShaderGroup = null;
   }

   @EventTarget
   public void a(SettingEvent var1) {
      int var2 = HUD.e();
      if(var1.getSettingName().equals("CAMERA_MB")) {
         if(((Boolean)this.motion_blur.get()).booleanValue()) {
            this.mc.entityRenderer.theShaderGroup = null;
            if(this.mc.world == null) {
               return;
            }

            try {
               this.mc.entityRenderer.loadCustomShader();
               return;
            } catch (IOException var4) {
               var4.printStackTrace();
            }
         }

         this.mc.entityRenderer.theShaderGroup = null;
      }

   }

   public JsonObject getJsonObject() {
      String var1 = "{\"targets\":[\"swap\",\"previous\"],\"passes\":[{\"name\":\"phosphor\",\"intarget\":\"minecraft:main\",\"outtarget\":\"swap\",\"auxtargets\":[{\"name\":\"PrevSampler\",\"id\":\"previous\"}],\"uniforms\":[{\"name\":\"Phosphor\",\"values\":[%.2f, %.2f, %.2f]}]},{\"name\":\"blit\",\"intarget\":\"swap\",\"outtarget\":\"previous\"},{\"name\":\"blit\",\"intarget\":\"swap\",\"outtarget\":\"minecraft:main\"}]}";
      double var2 = 0.7D + (double)((Integer)this.amount.get()).intValue() / 100.0D * 3.0D - 0.01D;
      return (new JsonParser()).parse(String.format(Locale.ENGLISH, var1, new Object[]{Double.valueOf(var2), Double.valueOf(var2), Double.valueOf(var2)})).getAsJsonObject();
   }

   @EventTarget
   public void on2D(Render2DEvent var1) {
      int var2 = HUD.h();
      if(((Boolean)this.motion_blur.get()).booleanValue() && this.mc.entityRenderer.theShaderGroup == null && this.mc.world != null) {
         try {
            this.mc.entityRenderer.loadCustomShader();
         } catch (IOException var4) {
            var4.printStackTrace();
         }
      }

   }

   public int b() {
      int var1 = HUD.h();
      return this.C.equals("MB")?2:(this.C.equals("ARB")?3:(this.C.equals("ALB")?4:0));
   }

   @EventTarget
   public void onMotionUpdate(MotionUpdateEvent var1) {
      int var2 = HUD.h();
      if(var1.getState() == EventState.PRE && ((Boolean)this.mcLock.get()).booleanValue()) {
         if(Mouse.isButtonDown(this.b())) {
            this.mc.gameSettings.thirdPersonView = 1;
            this.released = false;
         }

         if(!this.released) {
            this.mc.gameSettings.thirdPersonView = 0;
            this.released = true;
         }
      }

   }

   public BooleanProperty getViewClip() {
      return this.view_clip;
   }

   public BooleanProperty getNoHurtCam() {
      return this.no_hurt_cam;
   }

   public boolean f() {
      int var1 = HUD.e();
      return ((Boolean)this.mcLock.get()).booleanValue() && Mouse.isButtonDown(this.b());
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}

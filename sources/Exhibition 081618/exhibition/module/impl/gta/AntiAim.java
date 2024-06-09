package exhibition.module.impl.gta;

import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventMotionUpdate;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.module.data.Options;
import exhibition.module.data.Setting;
import exhibition.module.impl.combat.Killaura;
import exhibition.util.Timer;

public class AntiAim extends Module {
   private String AAYAW = "AAYAW";
   private String AAPITCH = "AAPITCH";
   float[] lastAngles;
   public static float rotationPitch;
   private boolean fake;
   private boolean fake1;
   Timer fakeJitter = new Timer();

   public AntiAim(ModuleData data) {
      super(data);
      this.settings.put(this.AAYAW, new Setting(this.AAYAW, new Options("AA Yaw", "FakeJitter", new String[]{"Reverse", "Jitter", "Lisp", "SpinSlow", "SpinFast", "Sideways", "FakeJitter", "FakeHead", "Freestanding", "180樹屋"}), "AA Yaw."));
      this.settings.put(this.AAPITCH, new Setting(this.AAPITCH, new Options("AA Pitch", "HalfDown", new String[]{"Normal", "HalfDown", "Zero", "Up", "Stutter", "Reverse", "Meme"}), "AA Pitch."));
   }

   public void onDisable() {
      this.fake1 = true;
      this.lastAngles = null;
      rotationPitch = 0.0F;
      mc.thePlayer.renderYawOffset = mc.thePlayer.rotationYaw;
   }

   public void onEnable() {
      this.fake1 = true;
      this.lastAngles = null;
      rotationPitch = 0.0F;
   }

   @RegisterEvent(
      events = {EventMotionUpdate.class}
   )
   public void onEvent(Event event) {
      if (event instanceof EventMotionUpdate) {
         EventMotionUpdate em = (EventMotionUpdate)event;
         if (em.isPre() && !Aimbot.isFiring && Killaura.target == null) {
            if (this.lastAngles == null) {
               this.lastAngles = new float[]{mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch};
            }

            this.fake = !this.fake;
            String var3 = ((Options)((Setting)this.settings.get(this.AAYAW)).getValue()).getSelected();
            byte var4 = -1;
            switch(var3.hashCode()) {
            case -2075869940:
               if (var3.equals("Jitter")) {
                  var4 = 0;
               }
               break;
            case -1944602658:
               if (var3.equals("SpinFast")) {
                  var4 = 7;
               }
               break;
            case -1944204925:
               if (var3.equals("SpinSlow")) {
                  var4 = 8;
               }
               break;
            case -1767802152:
               if (var3.equals("Freestanding")) {
                  var4 = 6;
               }
               break;
            case -1530467646:
               if (var3.equals("Reverse")) {
                  var4 = 2;
               }
               break;
            case -935666495:
               if (var3.equals("FakeJitter")) {
                  var4 = 4;
               }
               break;
            case 2368698:
               if (var3.equals("Lisp")) {
                  var4 = 1;
               }
               break;
            case 282808667:
               if (var3.equals("Sideways")) {
                  var4 = 3;
               }
               break;
            case 790022901:
               if (var3.equals("FakeHead")) {
                  var4 = 5;
               }
            }

            float pitchDown;
            float lastMeme;
            float reverse;
            float sutter;
            switch(var4) {
            case 0:
               pitchDown = 0.0F;
               em.setYaw(pitchDown = this.lastAngles[0] + 90.0F);
               this.lastAngles = new float[]{pitchDown, this.lastAngles[1]};
               this.updateAngles(pitchDown, this.lastAngles[1]);
               mc.thePlayer.renderYawOffset = pitchDown;
               mc.thePlayer.prevRenderYawOffset = pitchDown;
               break;
            case 1:
               lastMeme = this.lastAngles[0] + 150000.0F;
               this.lastAngles = new float[]{lastMeme, this.lastAngles[1]};
               em.setYaw(lastMeme);
               this.updateAngles(lastMeme, this.lastAngles[1]);
               break;
            case 2:
               reverse = mc.thePlayer.rotationYaw + 180.0F;
               this.lastAngles = new float[]{reverse, this.lastAngles[1]};
               em.setYaw(reverse);
               this.updateAngles(reverse, this.lastAngles[1]);
               break;
            case 3:
               sutter = mc.thePlayer.rotationYaw + -90.0F;
               this.lastAngles = new float[]{sutter, this.lastAngles[1]};
               em.setYaw(sutter);
               this.updateAngles(sutter, this.lastAngles[1]);
               break;
            case 4:
               if (this.fakeJitter.delay(350.0F)) {
                  this.fake1 = !this.fake1;
                  this.fakeJitter.reset();
               }

               float yawRight = mc.thePlayer.rotationYaw + (float)(this.fake1 ? 90 : -90);
               this.lastAngles = new float[]{yawRight, this.lastAngles[1]};
               em.setYaw(yawRight);
               this.updateAngles(yawRight, this.lastAngles[1]);
               break;
            case 5:
               if (this.fakeJitter.delay(1100.0F)) {
                  this.fake1 = !this.fake1;
                  this.fakeJitter.reset();
               }

               float yawFakeHead = mc.thePlayer.rotationYaw + (float)(this.fake1 ? 90 : -90);
               if (this.fake1) {
                  this.fake1 = false;
               }

               this.lastAngles = new float[]{yawFakeHead, this.lastAngles[1]};
               em.setYaw(yawFakeHead);
               this.updateAngles(yawFakeHead, this.lastAngles[1]);
               break;
            case 6:
               float freestandHead = (float)((double)(mc.thePlayer.rotationYaw + 5.0F) + Math.random() * 175.0D);
               this.lastAngles = new float[]{freestandHead, this.lastAngles[1]};
               em.setYaw(freestandHead);
               this.updateAngles(freestandHead, this.lastAngles[1]);
               break;
            case 7:
               float yawSpinFast = this.lastAngles[0] + 45.0F;
               this.lastAngles = new float[]{yawSpinFast, this.lastAngles[1]};
               em.setYaw(yawSpinFast);
               this.updateAngles(yawSpinFast, this.lastAngles[1]);
               break;
            case 8:
               float yawSpinSlow = this.lastAngles[0] + 10.0F;
               this.lastAngles = new float[]{yawSpinSlow, this.lastAngles[1]};
               em.setYaw(yawSpinSlow);
               this.updateAngles(yawSpinSlow, this.lastAngles[1]);
            }

            var3 = ((Options)((Setting)this.settings.get(this.AAPITCH)).getValue()).getSelected();
            var4 = -1;
            switch(var3.hashCode()) {
            case -1955878649:
               if (var3.equals("Normal")) {
                  var4 = 2;
               }
               break;
            case -1530467646:
               if (var3.equals("Reverse")) {
                  var4 = 3;
               }
               break;
            case -214001855:
               if (var3.equals("Stutter")) {
                  var4 = 4;
               }
               break;
            case 2747:
               if (var3.equals("Up")) {
                  var4 = 5;
               }
               break;
            case 2394448:
               if (var3.equals("Meme")) {
                  var4 = 1;
               }
               break;
            case 2781896:
               if (var3.equals("Zero")) {
                  var4 = 6;
               }
               break;
            case 10120085:
               if (var3.equals("HalfDown")) {
                  var4 = 0;
               }
            }

            switch(var4) {
            case 0:
               pitchDown = 90.0F;
               this.lastAngles = new float[]{this.lastAngles[0], pitchDown};
               em.setPitch(pitchDown);
               this.updateAngles(this.lastAngles[0], pitchDown);
               break;
            case 1:
               lastMeme = this.lastAngles[1];
               lastMeme += 10.0F;
               if (lastMeme > 90.0F) {
                  lastMeme = -90.0F;
               }

               this.lastAngles = new float[]{this.lastAngles[0], lastMeme};
               em.setPitch(lastMeme);
               this.updateAngles(this.lastAngles[0], lastMeme);
               break;
            case 2:
               this.updateAngles(this.lastAngles[0], 0.0F);
               break;
            case 3:
               reverse = mc.thePlayer.rotationPitch + 180.0F;
               this.lastAngles = new float[]{this.lastAngles[0], reverse};
               em.setPitch(reverse);
               this.updateAngles(this.lastAngles[0], reverse);
               break;
            case 4:
               if (this.fake) {
                  sutter = 90.0F;
                  em.setPitch(sutter);
               } else {
                  sutter = -45.0F;
                  em.setPitch(sutter);
               }

               this.lastAngles = new float[]{this.lastAngles[0], sutter};
               this.updateAngles(this.lastAngles[0], sutter);
               break;
            case 5:
               this.lastAngles = new float[]{this.lastAngles[0], -90.0F};
               em.setPitch(-90.0F);
               this.updateAngles(this.lastAngles[0], -90.0F);
               break;
            case 6:
               this.lastAngles = new float[]{this.lastAngles[0], -179.0F};
               em.setPitch(-180.0F);
               this.updateAngles(this.lastAngles[0], -179.0F);
            }
         }
      }

   }

   public void updateAngles(float yaw, float pitch) {
      if (mc.gameSettings.thirdPersonView != 0) {
         rotationPitch = pitch;
         mc.thePlayer.rotationYawHead = yaw;
         mc.thePlayer.renderYawOffset = yaw;
      }
   }
}

package vestige.module.impl.combat;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import vestige.Flap;
import vestige.event.Listener;
import vestige.event.impl.RenderEvent;
import vestige.module.Category;
import vestige.module.Module;
import vestige.setting.AbstractSetting;
import vestige.setting.impl.BooleanSetting;
import vestige.setting.impl.DoubleSetting;
import vestige.setting.impl.IntegerSetting;
import vestige.setting.impl.ModeSetting;
import vestige.util.misc.TimerUtil;
import vestige.util.player.FixedRotations;
import vestige.util.player.RotationsUtil;

public class AimAssist extends Module {
   private Antibot antibotModule;
   private Teams teamsModule;
   private final ModeSetting filter = new ModeSetting("Filter", "Range", new String[]{"Range", "Health"});
   private final DoubleSetting range = new DoubleSetting("Range", 4.5D, 3.0D, 8.0D, 0.1D);
   private final IntegerSetting speed = new IntegerSetting("Speed", 10, 1, 40, 1);
   private final BooleanSetting lockview = new BooleanSetting("LockView", false);
   private final TimerUtil timer = new TimerUtil();
   private EntityPlayer target;
   private FixedRotations rotations;

   public AimAssist() {
      super("AimAssist", Category.COMBAT);
      this.addSettings(new AbstractSetting[]{this.filter, this.range, this.speed, this.lockview});
   }

   public void onEnable() {
      this.rotations = new FixedRotations(mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch);
   }

   public void onClientStarted() {
      this.antibotModule = (Antibot)Flap.instance.getModuleManager().getModule(Antibot.class);
      this.teamsModule = (Teams)Flap.instance.getModuleManager().getModule(Teams.class);
   }

   @Listener
   public void onRender(RenderEvent event) {
      this.target = this.findTarget();
      if (this.target != null && mc.gameSettings.keyBindAttack.isKeyDown() && mc.currentScreen == null) {
         float[] rots = RotationsUtil.getRotationsToEntity(this.target, false);
         float yaw = rots[0];
         float currentYaw = MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw);
         float diff = Math.abs(currentYaw - yaw);
         if (this.lockview.isEnabled()) {
            mc.thePlayer.rotationYaw = rots[0];
         }

         if (diff >= 1.0F && diff <= 359.0F) {
            float adjustmentSpeed;
            if (diff <= (float)this.speed.getValue()) {
               adjustmentSpeed = diff * 0.6F;
            } else {
               adjustmentSpeed = (float)((double)this.speed.getValue() - Math.random() * 0.30000001192092896D);
            }

            float finalSpeed = adjustmentSpeed * (float)Math.max(this.timer.getTimeElapsed(), 1L) * 0.005F;
            EntityPlayerSP var10000;
            if (diff <= 180.0F) {
               if (currentYaw > yaw) {
                  var10000 = mc.thePlayer;
                  var10000.rotationYaw -= finalSpeed;
               } else {
                  var10000 = mc.thePlayer;
                  var10000.rotationYaw += finalSpeed;
               }
            } else if (currentYaw > yaw) {
               var10000 = mc.thePlayer;
               var10000.rotationYaw += finalSpeed;
            } else {
               var10000 = mc.thePlayer;
               var10000.rotationYaw -= finalSpeed;
            }
         }

         if (mc.thePlayer != null) {
            this.rotations.updateRotations(mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch);
         }

         mc.thePlayer.rotationYaw = this.rotations.getYaw();
         mc.thePlayer.rotationPitch = this.rotations.getPitch();
      }

      this.timer.reset();
   }

   public EntityPlayer findTarget() {
      ArrayList<EntityPlayer> entities = new ArrayList();
      Iterator var2 = mc.theWorld.loadedEntityList.iterator();

      while(var2.hasNext()) {
         Entity entity = (Entity)var2.next();
         if (entity instanceof EntityPlayer && entity != mc.thePlayer) {
            EntityPlayer player = (EntityPlayer)entity;
            if (this.canAttackEntity(player)) {
               entities.add(player);
            }
         }
      }

      if (entities != null && entities.size() > 0) {
         String var5 = this.filter.getMode();
         byte var6 = -1;
         switch(var5.hashCode()) {
         case -2137395588:
            if (var5.equals("Health")) {
               var6 = 1;
            }
            break;
         case 78727453:
            if (var5.equals("Range")) {
               var6 = 0;
            }
         }

         switch(var6) {
         case 0:
            entities.sort(Comparator.comparingDouble((entityx) -> {
               return (double)entityx.getDistanceToEntity(mc.thePlayer);
            }));
            break;
         case 1:
            entities.sort(Comparator.comparingDouble((entityx) -> {
               return (double)entityx.getHealth();
            }));
         }

         return (EntityPlayer)entities.get(0);
      } else {
         return null;
      }
   }

   private boolean canAttackEntity(EntityPlayer player) {
      if (!player.isDead && (double)mc.thePlayer.getDistanceToEntity(player) < this.range.getValue() && !player.isInvisible() && !player.isInvisibleToPlayer(mc.thePlayer)) {
         return !this.teamsModule.canAttack(player) ? false : this.antibotModule.canAttack(player, this);
      } else {
         return false;
      }
   }
}

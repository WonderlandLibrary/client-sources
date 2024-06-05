package cc.novoline.modules.combat;

import cc.novoline.events.EventTarget;
import cc.novoline.events.events.EventState;
import cc.novoline.events.events.MotionUpdateEvent;
import cc.novoline.gui.screen.setting.Manager;
import cc.novoline.gui.screen.setting.Setting;
import cc.novoline.gui.screen.setting.SettingType;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.PlayerManager$EnumPlayerType;
import cc.novoline.modules.combat.KillAura;
import cc.novoline.modules.configurations.annotation.Property;
import cc.novoline.modules.configurations.property.object.BooleanProperty;
import cc.novoline.modules.configurations.property.object.DoubleProperty;
import cc.novoline.modules.configurations.property.object.PropertyFactory;
import cc.novoline.utils.Timer;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemSword;
import org.lwjgl.input.Mouse;

public final class AimAssist extends AbstractModule {
   private static float yaw;
   private static float pitch;
   private static Entity target;
   private static List targets = new ObjectArrayList(0);
   private final Timer timer = new Timer();
   private final Comparator angleComparator = Comparator.comparingDouble(AimAssist::lambda$new$0);
   private int index;
   @Property("reach")
   private final DoubleProperty reach = (DoubleProperty)((DoubleProperty)PropertyFactory.createDouble(Double.valueOf(3.0D)).minimum(Double.valueOf(1.0D))).maximum(Double.valueOf(6.0D));
   @Property("aim-delay")
   private final DoubleProperty aimDelay = (DoubleProperty)((DoubleProperty)PropertyFactory.createDouble(Double.valueOf(0.0D)).minimum(Double.valueOf(0.0D))).maximum(Double.valueOf(1000.0D));
   @Property("height")
   private final DoubleProperty height = (DoubleProperty)((DoubleProperty)PropertyFactory.createDouble(Double.valueOf(3.5D)).minimum(Double.valueOf(-8.0D))).maximum(Double.valueOf(8.0D));
   @Property("horizontal-left")
   private final DoubleProperty horizontalLeft = (DoubleProperty)((DoubleProperty)PropertyFactory.createDouble(Double.valueOf(1.0D)).minimum(Double.valueOf(0.0D))).maximum(Double.valueOf(5.0D));
   @Property("horizontal-right")
   private final DoubleProperty horizontalRight = (DoubleProperty)((DoubleProperty)PropertyFactory.createDouble(Double.valueOf(1.0D)).minimum(Double.valueOf(0.0D))).maximum(Double.valueOf(5.0D));
   @Property("vertical-up")
   private final DoubleProperty verticalUp = (DoubleProperty)((DoubleProperty)PropertyFactory.createDouble(Double.valueOf(1.0D)).minimum(Double.valueOf(0.0D))).maximum(Double.valueOf(5.0D));
   @Property("vertical-down")
   private final DoubleProperty verticalDown = (DoubleProperty)((DoubleProperty)PropertyFactory.createDouble(Double.valueOf(1.0D)).minimum(Double.valueOf(0.0D))).maximum(Double.valueOf(5.0D));
   @Property("only-axe-sword")
   private final BooleanProperty onlyAxeSword = PropertyFactory.booleanFalse();
   @Property("ray-trace")
   private final BooleanProperty rayTrace = PropertyFactory.booleanFalse();
   @Property("click-aim")
   private final BooleanProperty clickAim = PropertyFactory.booleanFalse();

   public AimAssist(ModuleManager var1) {
      super(var1, "AimAssist", "Aim Assist", 0, EnumModuleType.COMBAT, "Helps you to aim");
      Manager.put(new Setting("REACH", "Reach", SettingType.SLIDER, this, this.reach, 0.1D));
      Manager.put(new Setting("AIM_DELAY", "Aim Delay", SettingType.SLIDER, this, this.aimDelay, 50.0D));
      Manager.put(new Setting("HEIGHT", "Height", SettingType.SLIDER, this, this.height, 1.0D));
      Manager.put(new Setting("H_LEFT", "Horizontal Left", SettingType.SLIDER, this, this.horizontalLeft, 0.1D));
      Manager.put(new Setting("H_RIGHT", "Horizontal Right", SettingType.SLIDER, this, this.horizontalRight, 0.1D));
      Manager.put(new Setting("V_UP", "Vertical Up", SettingType.SLIDER, this, this.verticalUp, 0.1D));
      Manager.put(new Setting("V_DOWN", "Vertical Down", SettingType.SLIDER, this, this.verticalDown, 0.1D));
      Manager.put(new Setting("ONLY_AXESWORD", "Only Sword/Axe", SettingType.CHECKBOX, this, this.onlyAxeSword));
      Manager.put(new Setting("RAYTRACE", "Raytrace", SettingType.CHECKBOX, this, this.rayTrace));
      Manager.put(new Setting("CLICKAIM", "Click Aim", SettingType.CHECKBOX, this, this.clickAim));
   }

   private List loadTargets() {
      return (List)this.mc.world.getLoadedEntityList().stream().filter(this::lambda$loadTargets$1).sorted(Comparator.comparingDouble(this::lambda$loadTargets$2).reversed()).collect(Collectors.toCollection(ObjectArrayList::<init>));
   }

   private boolean qualifies(Entity var1) {
      int[] var2 = KillAura.Q();
      return var1 != this.mc.player && var1.isEntityAlive() && !this.novoline.getPlayerManager().hasType(var1.getName(), PlayerManager$EnumPlayerType.FRIEND) && var1 instanceof EntityPlayer;
   }

   @EventTarget
   private void onPreUpdate(MotionUpdateEvent var1) {
      int[] var2 = KillAura.Q();
      if(var1.getState().equals(EventState.PRE)) {
         targets = this.loadTargets();
         targets.sort(this.angleComparator);
         if(target instanceof EntityPlayer || target instanceof EntityMob || target instanceof EntityAnimal) {
            target = null;
         }

         if(this.mc.player.ticksExisted % 50 == 0 && targets.size() > 1) {
            ++this.index;
         }

         if(((Boolean)this.clickAim.get()).booleanValue() && !Mouse.isButtonDown(0)) {
            return;
         }

         if(!targets.isEmpty()) {
            if(this.index >= targets.size()) {
               this.index = 0;
            }

            target = (Entity)targets.get(this.index);
            double[] var3 = b(target);
            if(this.timer.delay(((Double)this.aimDelay.get()).doubleValue())) {
               pitch = (float)(var3[1] + ((Double)this.height.get()).doubleValue());
               yaw = (float)var3[0];
               this.timer.reset();
            }

            boolean var4 = !((Boolean)this.onlyAxeSword.get()).booleanValue();
            if(((Boolean)this.rayTrace.get()).booleanValue()) {
               if(!this.mc.player.canEntityBeSeen(target) || !var4 && !(this.mc.player.getCurrentEquippedItem().getItem() instanceof ItemSword) && !(this.mc.player.getCurrentEquippedItem().getItem() instanceof ItemAxe)) {
                  return;
               }

               this.aim();
            }

            if(var4) {
               this.aim();
            }

            if(this.mc.player.getCurrentEquippedItem().getItem() instanceof ItemSword || this.mc.player.getCurrentEquippedItem().getItem() instanceof ItemAxe) {
               this.aim();
            }
         }
      }

   }

   private void aim() {
      KillAura.Q();
      EntityPlayerSP var2 = this.mc.player;
      float var3 = var2.rotationYaw;
      float var4 = var2.rotationPitch;
      if(var3 < yaw) {
         var2.rotationYaw = (float)((double)var2.rotationYaw + ((Double)this.horizontalRight.get()).doubleValue());
      }

      if(var2.rotationYaw > yaw) {
         var2.rotationYaw = (float)((double)var2.rotationYaw - ((Double)this.horizontalLeft.get()).doubleValue());
      }

      if(var4 < pitch) {
         var2.rotationPitch = (float)((double)var2.rotationPitch + ((Double)this.verticalDown.get()).doubleValue());
      }

      if(var2.rotationPitch > pitch) {
         var2.rotationPitch = (float)((double)var2.rotationPitch - ((Double)this.verticalUp.get()).doubleValue());
      }

   }

   public static double[] b(Entity var0) {
      return null;
   }

   private double lambda$loadTargets$2(Object var1) {
      return (double)((Entity)var1).getDistanceToEntity(this.mc.player);
   }

   private boolean lambda$loadTargets$1(Entity var1) {
      int[] var2 = KillAura.Q();
      return (double)this.mc.player.getDistanceToEntity(var1) <= ((Double)this.reach.get()).doubleValue() && this.qualifies(var1);
   }

   private static double lambda$new$0(Entity var0) {
      return b(var0)[0];
   }
}

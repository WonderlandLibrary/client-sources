package cc.novoline.modules.combat;

import cc.novoline.events.EventTarget;
import cc.novoline.events.events.EventState;
import cc.novoline.events.events.MotionUpdateEvent;
import cc.novoline.events.events.TickUpdateEvent;
import cc.novoline.gui.screen.setting.Manager;
import cc.novoline.gui.screen.setting.Setting;
import cc.novoline.gui.screen.setting.SettingType;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.combat.KillAura;
import cc.novoline.modules.configurations.annotation.Property;
import cc.novoline.modules.configurations.property.object.BooleanProperty;
import cc.novoline.modules.configurations.property.object.DoubleProperty;
import cc.novoline.modules.configurations.property.object.IntProperty;
import cc.novoline.modules.configurations.property.object.ListProperty;
import cc.novoline.modules.configurations.property.object.PropertyFactory;
import cc.novoline.modules.configurations.property.object.StringProperty;
import cc.novoline.modules.move.Scaffold;
import cc.novoline.utils.ServerUtils;
import cc.novoline.utils.Servers;
import cc.novoline.utils.Timer;
import java.util.Iterator;
import java.util.function.Supplier;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer$C06PacketPlayerPosLook;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.potion.PotionEffect;

public class AutoPot extends AbstractModule {
   private final Timer timer = new Timer();
   private boolean x;
   @Property("mode")
   private final StringProperty B = PropertyFactory.createString("Edit").acceptableValues(new String[]{"Edit", "Packet"});
   @Property("health")
   private final DoubleProperty health = (DoubleProperty)((DoubleProperty)PropertyFactory.createDouble(Double.valueOf(14.0D)).minimum(Double.valueOf(0.5D))).maximum(Double.valueOf(20.0D));
   @Property("delay")
   private final IntProperty delay = (IntProperty)((IntProperty)PropertyFactory.createInt(Integer.valueOf(600)).minimum(Integer.valueOf(600))).maximum(Integer.valueOf(1500));
   @Property("potions")
   private final ListProperty potions = PropertyFactory.createList((Object)"Regen").acceptableValues((Object[])(new String[]{"Heal", "Regen", "Jump", "Speed", "Fire"}));
   @Property("over-pot")
   private final BooleanProperty overlap = PropertyFactory.booleanFalse();

   public AutoPot(ModuleManager var1) {
      super(var1, "AutoPotion", "Auto Potion", EnumModuleType.COMBAT, "Automatically throws pots");
      Manager.put(new Setting("AP_MODE", "Mode", SettingType.COMBOBOX, this, this.B));
      Manager.put(new Setting("AP_HEARTS", "Health", SettingType.SLIDER, this, this.health, 0.5D, this::lambda$new$0));
      Manager.put(new Setting("AP_DELAY", "Throw Delay", SettingType.SLIDER, this, this.delay, 50.0D, this::lambda$new$1));
      Manager.put(new Setting("AP_POTIONS", "Potions", SettingType.SELECTBOX, this, this.potions));
      Manager.put(new Setting("AP_OVER_POT", "Over Pot", SettingType.CHECKBOX, this, this.overlap));
   }

   private int getBestSpoofSlot() {
      KillAura.Q();
      int var2 = 5;
      int var3 = 36;
      if(var3 < 45) {
         if(!this.mc.player.inventoryContainer.getSlot(var3).getHasStack()) {
            var2 = var3 - 36;
         }

         if(this.mc.player.inventoryContainer.getSlot(var3).getStack().getItem() instanceof ItemPotion) {
            var2 = var3 - 36;
         }

         ++var3;
      }

      return var2;
   }

   private int[] potions() {
      KillAura.Q();
      int[] var2 = new int[]{-1, -1, -1, -1, -1, -1};
      if(this.potions.contains("Heal")) {
         var2[0] = 6;
      }

      if(this.potions.contains("Regen")) {
         var2[1] = 10;
      }

      if(this.potions.contains("Jump")) {
         var2[2] = 8;
      }

      if(this.potions.contains("Speed")) {
         var2[3] = 1;
      }

      if(this.potions.contains("Fire")) {
         var2[4] = 12;
      }

      return var2;
   }

   private boolean shouldBuff(int var1) {
      int[] var2 = KillAura.Q();
      return var1 != 1 && var1 != 8?(var1 != 6 && var1 != 10?(var1 == 12?this.mc.player.isBurning():false):(double)this.mc.player.getHealth() < ((Double)this.health.get()).doubleValue()):this.mc.player.isMoving();
   }

   @EventTarget
   public void a(MotionUpdateEvent var1) {
      int[] var2 = KillAura.Q();
      if(this.mc.player.onGround && !(this.mc.currentScreen instanceof GuiContainerCreative) && (!ServerUtils.isHypixel() || !ServerUtils.serverIs(Servers.PRE) && !ServerUtils.serverIs(Servers.LOBBY) && ServerUtils.inGameSeconds() >= 1)) {
         int var3 = 9;
         if(var3 < 45) {
            ItemStack var4 = this.mc.player.inventoryContainer.getSlot(var3).getStack();
            Item var5 = var4.getItem();
            if(var5 instanceof ItemPotion) {
               ItemPotion var6 = (ItemPotion)var5;
               PotionEffect var7 = (PotionEffect)var6.getEffects(var4).get(0);
               int[] var8 = this.potions();
               int var9 = var8.length;
               int var10 = 0;
               if(var10 < var9) {
                  int var11 = var8[var10];
                  if(var7.getPotionID() == var11 && ItemPotion.isSplash(var4.getItemDamage()) && this.onOverlap(var11, var7) && this.shouldBuff(var11) && this.isBestPotion(var6, var4) && this.timer.delay((double)((Integer)this.delay.get()).intValue())) {
                     if(this.B.equals("Edit")) {
                        if(var1.getState().equals(EventState.PRE)) {
                           this.x = true;
                           var1.setPitch(90.0F);
                           this.mc.player.swap(var3, this.getBestSpoofSlot());
                           this.sendPacketNoEvent(new C09PacketHeldItemChange(this.getBestSpoofSlot()));
                        }

                        this.sendPacketNoEvent(new C08PacketPlayerBlockPlacement(this.mc.player.inventory.getStackInSlot(this.getBestSpoofSlot())));
                        this.sendPacketNoEvent(new C09PacketHeldItemChange(this.isEnabled(Scaffold.class)?((Scaffold)this.getModule(Scaffold.class)).neededSlot():this.mc.player.inventory.currentItem));
                        this.x = false;
                        this.timer.reset();
                     }

                     if(this.B.equals("Packet") && var1.getState().equals(EventState.PRE)) {
                        this.mc.player.swap(var3, this.getBestSpoofSlot());
                        this.sendPacketNoEvent(new C09PacketHeldItemChange(this.getBestSpoofSlot()));
                        this.sendPacketNoEvent(new C03PacketPlayer$C06PacketPlayerPosLook(var1.getX(), var1.getY(), var1.getZ(), var1.getYaw(), 90.0F, var1.isOnGround()));
                        this.sendPacketNoEvent(new C08PacketPlayerBlockPlacement(this.mc.player.inventory.getStackInSlot(this.getBestSpoofSlot())));
                        this.sendPacketNoEvent(new C03PacketPlayer$C06PacketPlayerPosLook(var1.getX(), var1.getY(), var1.getZ(), var1.getYaw(), var1.getPitch(), var1.isOnGround()));
                        this.sendPacketNoEvent(new C09PacketHeldItemChange(this.isEnabled(Scaffold.class)?((Scaffold)this.getModule(Scaffold.class)).neededSlot():this.mc.player.inventory.currentItem));
                        this.timer.reset();
                     }
                  }

                  ++var10;
               }
            }

            ++var3;
         }

      }
   }

   private boolean onOverlap(int var1, PotionEffect var2) {
      int[] var3 = KillAura.Q();
      return !((Boolean)this.overlap.get()).booleanValue()?!this.mc.player.isPotionActive(var1):!this.mc.player.isPotionActive(var1) || this.mc.player.getActivePotionsMap().containsKey(Integer.valueOf(var1)) && ((PotionEffect)this.mc.player.getActivePotionsMap().get(Integer.valueOf(var1))).getAmplifier() < var2.getAmplifier();
   }

   private boolean isBestPotion(ItemPotion var1, ItemStack var2) {
      int[] var3 = KillAura.Q();
      if(var1.getEffects(var2) != null && var1.getEffects(var2).size() == 1) {
         PotionEffect var4 = (PotionEffect)var1.getEffects(var2).get(0);
         int var5 = var4.getPotionID();
         int var6 = var4.getAmplifier();
         int var7 = var4.getDuration();
         int var8 = 9;
         if(var8 < 45) {
            if(this.mc.player.inventoryContainer.getSlot(var8).getHasStack()) {
               ItemStack var9 = this.mc.player.inventoryContainer.getSlot(var8).getStack();
               if(var9.getItem() instanceof ItemPotion) {
                  ItemPotion var10 = (ItemPotion)var9.getItem();
                  if(var10.getEffects(var9) != null) {
                     Iterator var11 = var10.getEffects(var9).iterator();
                     if(var11.hasNext()) {
                        PotionEffect var12 = (PotionEffect)var11.next();
                        int var13 = var12.getPotionID();
                        int var14 = var12.getAmplifier();
                        int var15 = var12.getDuration();
                        if(var13 == var5 && ItemPotion.isSplash(var9.getItemDamage())) {
                           if(var14 > var6) {
                              return false;
                           }

                           if(var14 == var6 && var15 > var7) {
                              return false;
                           }
                        }
                     }
                  }
               }
            }

            ++var8;
         }

         return true;
      } else {
         return false;
      }
   }

   @EventTarget
   public void a(TickUpdateEvent var1) {
      this.setSuffix((String)this.B.get());
   }

   public void onEnable() {
      this.setSuffix((String)this.B.get());
   }

   public void onDisable() {
      this.x = false;
   }

   public StringProperty a() {
      return this.B;
   }

   public boolean d() {
      int[] var1 = KillAura.Q();
      return this.isEnabled() && this.x;
   }

   private Boolean lambda$new$1() {
      int[] var1 = KillAura.Q();
      return Boolean.valueOf(!this.potions.isEmpty());
   }

   private Boolean lambda$new$0() {
      int[] var1 = KillAura.Q();
      return Boolean.valueOf(this.potions.contains("Regen") || this.potions.contains("Heal"));
   }
}

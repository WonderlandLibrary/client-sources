package club.bluezenith.module.modules.combat;

import club.bluezenith.BlueZenith;
import club.bluezenith.events.Listener;
import club.bluezenith.events.impl.UpdatePlayerEvent;
import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.module.modules.movement.Speed;
import club.bluezenith.module.modules.player.InvManager;
import club.bluezenith.module.modules.player.Scaffold;
import club.bluezenith.module.value.builders.AbstractBuilder;
import club.bluezenith.module.value.types.BooleanValue;
import club.bluezenith.module.value.types.FloatValue;
import club.bluezenith.module.value.types.ListValue;
import club.bluezenith.util.client.MillisTimer;
import club.bluezenith.util.player.MovementUtil;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;

import java.util.*;

import static net.minecraft.potion.Potion.moveSpeed;

public class AutoPot extends Module {
    private boolean throwPot = false;
    private ItemStack potToThrow = null;
    int preThrow = -1;
    int lastIndex = -1;
    private final MillisTimer timer = new MillisTimer();
    private final FloatValue healthThreshold = AbstractBuilder.createFloat("Health").index(1).min(1.0F).increment(0.5F).max(20.F).defaultOf(15.0F).build();
    private final BooleanValue customPots = new BooleanValue("Custom potions", false).setIndex(2);
    private final BooleanValue overlap = new BooleanValue("Overlap", false).setIndex(3);
    private final ListValue pots = new ListValue("Potions", "Speed", "Regeneration", "Healing", "Fire Resistance", "Resistance").setIndex(4);

    public AutoPot() {
        super("AutoPot", ModuleCategory.COMBAT);
    }

    @Listener
    public void onUpdate(UpdatePlayerEvent event) {
        if(getCastedModule(Scaffold.class).getState() || mc.currentScreen != null) return;
        if(mc.thePlayer.ticksExisted == 10 || mc.thePlayer.ticksExisted % 3 == 0) {
            InvManager.canSort = true;
        }
        if (event.isPre()) {
            if (!player.onGround || MovementUtil.currentSpeed() < 0.1f || !timer.hasTimeReached(2000)
                    || BlueZenith.getBlueZenith().getModuleManager().getAndCast(Speed.class).getState()
                    || BlueZenith.getBlueZenith().getModuleManager().getAndCast(Scaffold.class).getState()
                    || BlueZenith.getBlueZenith().getModuleManager().getAndCast(Aura.class).target != null) {
                return;
            }


            // get all pots
            Map<ItemStack, Integer> potMap = new HashMap<>();
            List<ItemStack> pots = new ArrayList<>();
            boolean thrw = false;
            boolean thrw2 = false;
            int daIndex = -1;
            for (int i = 0; i < 36; i++) {
                if(thrw2) {
                //    ClientUtils.fancyMessage("returning 2");
                    continue;
                }
                ItemStack hotbarItem;
                if(i <= 8) {
                    hotbarItem = mc.thePlayer.inventory.mainInventory[i];
                    if(hotbarItem != null && hotbarItem.getItem() instanceof ItemPotion && hotbarItem.stackSize >= 1) {
                        daIndex = i;
                        thrw = true;
                    }
                }
                else {
                    hotbarItem = mc.thePlayer.inventory.getStackInSlot(i);
                    if(hotbarItem != null && hotbarItem.getItem() instanceof ItemPotion && hotbarItem.stackSize >= 1) {
                        daIndex = i;
                        thrw = true;
                    }
                }
                if (hotbarItem == null || !(hotbarItem.getItem() instanceof ItemPotion) || daIndex == -1) {
                    continue;
                }
                List<PotionEffect> effects = ((ItemPotion) hotbarItem.getItem()).getEffects(hotbarItem);
                if(effects.size() == 1 || effects.size() > 1 && customPots.get()) {
                    final PotionEffect firstEffect = effects.get(0);
                    if(firstEffect.isSplashPotion() || hotbarItem.getDisplayName().contains("Splash")) {
                        switch (firstEffect.getEffectName()) {
                            case "potion.moveSpeed":

                            case "potion.heal":

                            case "potion.fireResistance":
                                if(allowPot(firstEffect.getEffectName())) {
                                    final PotionEffect effect = hasEffect(moveSpeed.getId());
                                    if (effect == null || effect.getAmplifier() < firstEffect.getAmplifier() && overlap.get()) {
                                        if(i >= 9) {
                                            mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, daIndex, 4, 2, mc.thePlayer);
                                        }
                                        InvManager.canSort = false;
                                        pots.add(hotbarItem);
                                        potMap.put(hotbarItem, i > 8 ? 4 : i);
                                        preThrow = mc.thePlayer.inventory.currentItem;
                                        mc.thePlayer.inventory.currentItem = i > 8 ? 4 : i;
                                    }
                                }

                            case "potion.regeneration":
                            case "potion.resistance":
                                if(mc.thePlayer.getHealth() <= healthThreshold.get() && allowPot(firstEffect.getEffectName())) {
                                    final PotionEffect regenEffect = hasEffect(moveSpeed.getId());
                                    if (regenEffect == null || regenEffect.getAmplifier() < firstEffect.getAmplifier() && overlap.get()) {
                                        if(i > 8) {
                                            mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, daIndex, 4, 2, mc.thePlayer);
                                        }
                                        InvManager.canSort = false;
                                        pots.add(hotbarItem);
                                        potMap.put(hotbarItem, i > 8 ? 4 : i);
                                        preThrow = mc.thePlayer.inventory.currentItem;
                                        mc.thePlayer.inventory.currentItem = i > 8 ? 4 : i;
                                    }
                                }
                            break;
                        }
                    }
                }
                if(thrw) thrw2 = true;
            }

            if (pots.isEmpty() || potMap.isEmpty()) {
                return;
            }
            pots.sort(Comparator.comparingInt(pot -> ((ItemPotion) pot.getItem()).getEffects(pot).get(0).getAmplifier()));
            Map.Entry<ItemStack, Integer> a = potMap.entrySet().iterator().next();

            // throw
            event.pitch = -90 + (MovementUtil.currentSpeed() * 200);
            event.yaw = (float) Math.toDegrees(Math.atan2(-player.motionX, player.motionZ));
            player.jump();
            throwPot = true;
            potToThrow = a.getKey();
            potMap.remove(a.getKey());
        }
        else if (event.isPost() && throwPot && potToThrow != null) {
         //   PacketUtil.sendSilent(new C08PacketPlayerBlockPlacement(potToThrow));
            mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, potToThrow);
            throwPot = false;
            timer.reset();
        }
    }

    private boolean allowPot(String key) {
        switch (key) {
            case "potion.moveSpeed":
                return pots.getOptionState("Speed");
            case "potion.heal":
                return pots.getOptionState("Healing");
            case "potion.regeneration":
                return pots.getOptionState("Regeneration");
            case "potion.fireResistance":
                return pots.getOptionState("Fire Resistance");
            case "potion.resistance":
                return pots.getOptionState("Resistance");
        }
        return false;
    }

    private PotionEffect hasEffect(int effectID) {
        for (PotionEffect activePotionEffect : player.getActivePotionEffects()) {
            if(activePotionEffect.getPotionID() == effectID && activePotionEffect.getDuration() > 10) return activePotionEffect;
        }
        return null;
    }
}

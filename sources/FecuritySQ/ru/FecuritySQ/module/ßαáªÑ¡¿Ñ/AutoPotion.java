package ru.FecuritySQ.module.сражение;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.play.client.CHeldItemChangePacket;
import net.minecraft.network.play.client.CPlayerPacket;
import net.minecraft.network.play.client.CPlayerTryUseItemPacket;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.Hand;
import org.lwjgl.glfw.GLFW;
import ru.FecuritySQ.event.Event;
import ru.FecuritySQ.event.imp.WalkingUpdateEvent;
import ru.FecuritySQ.module.Module;
import ru.FecuritySQ.option.imp.OptionBoolList;
import ru.FecuritySQ.option.imp.OptionBoolean;
import ru.FecuritySQ.utils.Counter;

public class AutoPotion extends Module {

    public OptionBoolList potions = new OptionBoolList("Что бросать?",
            new OptionBoolean("Сила", true),
            new OptionBoolean("Скорость", true),
            new OptionBoolean("Огнестойкость", true));

    Counter counter = new Counter();

    public AutoPotion() {
        super(Category.Сражение, GLFW.GLFW_KEY_0);
        addOption(potions);
    }

    @Override
    public void event(Event e) {
        if(!isEnabled()) return;
        if(e instanceof WalkingUpdateEvent motionPre){
            if(mc.player.getCooledAttackStrength(1) < 0.5f){
                boolean check = !mc.player.isOnGround() || mc.player.isOnLadder() || mc.player.getRidingEntity() != null || mc.player.abilities.isFlying || mc.player.isInWater();
                boolean throwCheck = (!mc.player.isPotionActive(Effects.SPEED) && isPotionOnHotBar(Potions.SPEED)
                        && potions.get().get(1).get())
                        || (!mc.player.isPotionActive(Effects.STRENGTH) && isPotionOnHotBar(Potions.STRENGTH)
                        && potions.get().get(0).get())
                        || (!mc.player.isPotionActive(Effects.FIRE_RESISTANCE) && isPotionOnHotBar(Potions.FIRERES)
                        && potions.get().get(2).get());
                if (throwCheck && !check && counter.hasTimeElapsed(700)) {
                    mc.player.connection.sendPacket(new CPlayerPacket.RotationPacket(mc.player.rotationYaw, 90, mc.player.isOnGround()));
                    if (!mc.player.isPotionActive(Effects.SPEED) && isPotionOnHotBar(Potions.SPEED)
                            && potions.get().get(1).get()) {
                        sendPotion(Potions.SPEED);
                    }
                    if (!mc.player.isPotionActive(Effects.STRENGTH) && isPotionOnHotBar(Potions.STRENGTH)
                            && potions.get().get(0).get()) {
                        sendPotion(Potions.STRENGTH);
                    }
                    if (!mc.player.isPotionActive(Effects.FIRE_RESISTANCE) && isPotionOnHotBar(Potions.FIRERES)
                            && potions.get().get(2).get()) {
                        sendPotion(Potions.FIRERES);
                    }
                    mc.player.connection.sendPacket(new CHeldItemChangePacket(mc.player.inventory.currentItem));
                    counter.reset();
                }
            }
        }
    }


    public void sendPotion(Potions potion) {
        int slot = getPotionSlot(potion);
        mc.player.connection.sendPacket(new CHeldItemChangePacket(slot));
        mc.playerController.callSyncCurrentPlayItem();
        mc.player.connection.sendPacket(new CPlayerTryUseItemPacket(Hand.MAIN_HAND));
        mc.playerController.callSyncCurrentPlayItem();
        mc.player.connection.sendPacket(new CHeldItemChangePacket(slot));
    }

    public static int getPotionSlot(Potions potion) {
        for (int i = 0; i < 9; ++i) {
            if (isStackPotion(mc.player.inventory.getStackInSlot(i), potion)) {
                return i;
            }
        }
        return -1;
    }

    public static boolean isPotionOnHotBar(Potions potions) {
        return getPotionSlot(potions) != -1;
    }
    public static boolean isStackPotion(ItemStack stack, Potions potion) {
        if (stack == null) return false;

        Item item = stack.getItem();

        if (item == Items.SPLASH_POTION) {
            int id = 0;

            switch (potion) {
                case STRENGTH: {
                    id = 5;
                    break;
                }
                case SPEED: {
                    id = 1;
                    break;
                }
                case FIRERES: {
                    id = 12;
                    break;
                }
                case HEAL: {
                    id = 6;
                    break;
                }
            }

            for (EffectInstance effect : PotionUtils.getEffectsFromStack(stack)) {
                if (effect.getPotion() == Effect.get(id)) {
                    return true;
                }
            }
        }
        return false;
    }

    public enum Potions {
        STRENGTH, SPEED, FIRERES, HEAL
    }

}

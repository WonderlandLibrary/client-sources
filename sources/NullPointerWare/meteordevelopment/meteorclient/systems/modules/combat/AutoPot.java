package meteordevelopment.meteorclient.systems.modules.combat;


import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.other.TrinaryBoolean;
import meteordevelopment.meteorclient.utils.player.CrystalsInvUtils;
import meteordevelopment.meteorclient.utils.player.InvUtils;
import meteordevelopment.meteorclient.utils.player.MoveUtils;
import meteordevelopment.meteorclient.utils.player.SwapType;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.potion.PotionUtil;
import net.minecraft.util.Hand;

import java.util.List;

public final class AutoPot extends Module {

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Boolean> rot = sgGeneral.add(new BoolSetting.Builder()
        .name("rotate")
        .description("Rotate.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> rotup = sgGeneral.add(new BoolSetting.Builder()
        .name("rotateUp")
        .description("Rotate up if no motion.")
        .defaultValue(false)
        .visible(rot::get)
        .build()
    );

    private final Setting<Integer> delay = sgGeneral.add(new IntSetting.Builder()
        .name("delay")
        .description("The Minimum health")
        .defaultValue(5)
        .min(0)
        .sliderMax(20)
        .build()
    );


    private final Setting<Double> minHealth = sgGeneral.add(new DoubleSetting.Builder()
        .name("minHealth")
        .description("The Minimum health")
        .defaultValue(5)
        .min(0)
        .sliderMax(20)
        .build()
    );

    private final Setting<List<StatusEffect>> effects = sgGeneral.add(new StatusEffectListSetting.Builder()
        .name("effects")
        .description("Which effects to shoot you with.")
        .defaultValue(StatusEffects.STRENGTH)
        .build()
    );

    private final Setting<SwapType> swapMode = sgGeneral.add(new EnumSetting.Builder<SwapType>()
        .name("SwapMode")
        .description("What mode to swap with.")
        .defaultValue(SwapType.Simple)
        .build()
    );

    private int timer;

    public AutoPot() {
        super(Categories.Combat, "AutoPot", "pots automatically, very cool!");
    }

    // DONE: Make it bypass Grim!

    @EventHandler
    private void onTick(final TickEvent.Pre e) {
        int endslot = swapMode.get() == SwapType.Simple ? 8 : 45;
        List<Integer> pot = InvUtils.findList(this::isGood, 0, endslot);

        if (timer > 0) {
            timer--;
            return;
        }

        for (Integer slot : pot) {
            if (hasInstant(mc.player.getInventory().getStack(slot)) && mc.player.getHealth() < minHealth.get()) {
                throwPot(slot);
                return;
            }

            else if (playerHasEffect(hasEffect(mc.player.getInventory().getStack(slot))) == TrinaryBoolean.Maybe) {
                throwPot(slot);
                return;
            }
        }
    }

    public void throwPot(int slot) {
        if (slot <= -1) return;
        CrystalsInvUtils.swap(swapMode.get(), slot, () -> {
            if (rot.get())
                sendNoEvent(new PlayerMoveC2SPacket.Full(mc.player.getX(), mc.player.getY(), mc.player.getZ(), mc.player.getYaw(), !MoveUtils.hasMovement() && rotup.get() ? -90 : 90, mc.player.isOnGround()));
            sendNoEvent(new PlayerInteractItemC2SPacket(Hand.MAIN_HAND, 0));
        });
        timer = delay.get();
    }

    public boolean isGood(ItemStack itemStack) {
        return itemStack.getItem() == Items.SPLASH_POTION;
    }

    private TrinaryBoolean playerHasEffect(StatusEffect effect) {
        if (effect == null) return TrinaryBoolean.False;
        for (StatusEffectInstance effectInstance : mc.player.getStatusEffects()) {
            if (effectInstance.getEffectType() == effect) return TrinaryBoolean.True;
        }
        return TrinaryBoolean.Maybe;
    }

    private StatusEffect hasEffect(ItemStack stack) {
        for (StatusEffectInstance effectInstance : PotionUtil.getPotionEffects(stack)) {
            if (effects.get().contains(effectInstance.getEffectType()) && effectInstance.getEffectType() != StatusEffects.INSTANT_HEALTH) return effectInstance.getEffectType();
        }
        return null;
    }

    private boolean hasInstant(ItemStack stack) {
        for (StatusEffectInstance effectInstance : PotionUtil.getPotionEffects(stack)) {
            if (effectInstance.getEffectType() == StatusEffects.INSTANT_HEALTH) return true;
        }
        return false;
    }
}

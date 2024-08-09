/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.movement;

import baritone.api.BaritoneAPI;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.systems.modules.combat.Aura;
import meteordevelopment.meteorclient.systems.modules.combat.Offhand;
import meteordevelopment.meteorclient.systems.modules.player.AutoEat;
import meteordevelopment.meteorclient.utils.player.CrystalsInvUtils;
import meteordevelopment.meteorclient.utils.player.SwapType;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;

public class AutoRobot extends Module {

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<RankedMode> rankled = sgGeneral.add(new EnumSetting.Builder<RankedMode>()
        .name("item")
        .description("Which item to hold in your offhand.")
        .defaultValue(RankedMode.NONE)
        .build()
    );

    private final Setting<Integer> followDist = sgGeneral.add(new IntSetting.Builder()
        .name("follow-distance")
        .description("The distance baritone should follow from")
        .defaultValue(2)
        .min(0)
        .sliderMax(6)
        .build()
    );

    private final Setting<Boolean> jumptarget = sgGeneral.add(new BoolSetting.Builder()
        .name("only-jump-on-target")
        .description("Toggles AutoEat on or off...")
        .defaultValue(false)
        .build()
    );


    private final Setting<Boolean> autoEatToggle = sgGeneral.add(new BoolSetting.Builder()
        .name("toggle-autoEat")
        .description("Toggles AutoEat on or off...")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> offhandToggle = sgGeneral.add(new BoolSetting.Builder()
        .name("toggle-offhand")
        .description("Toggles Offhand on or off...")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> auraToggle = sgGeneral.add(new BoolSetting.Builder()
        .name("toggle-aura")
        .description("Toggle Aura on or off...")
        .defaultValue(false)
        .build()
    );
    public AutoRobot() {
        super(Categories.Movement, "auto-robot", "Minemalia gaming.");
    }

    public boolean ing = false;
    boolean haditemlasttick = false;
    boolean queuebool = false;

    @Override
    public void onActivate() {
        haditemlasttick = false;
        queuebool = false;
        ing = false;
        if (!Modules.get().get(AutoEat.class).isActive() && autoEatToggle.get()) Modules.get().get(AutoEat.class).toggle();
        if (!Modules.get().get(Offhand.class).isActive() && offhandToggle.get()) Modules.get().get(Offhand.class).toggle();
        if (!Modules.get().get(Aura.class).isActive() && auraToggle.get()) Modules.get().get(Aura.class).toggle();
        BaritoneAPI.getSettings().followRadius.value = followDist.get();
    }

    @EventHandler
    private void onTick(final TickEvent.Pre event) {
        //event stuff
        if (haditemlasttick && (mc.player.getInventory().getStack(8).getItem() != Items.CLOCK && mc.player.getInventory().getStack(8).getItem() != Items.REDSTONE)) {
            ing = true;
            BaritoneAPI.getProvider().getPrimaryBaritone().getCommandManager().execute("follow players");
        } else if (!haditemlasttick && (mc.player.getInventory().getStack(8).getItem() == Items.CLOCK || mc.player.getInventory().getStack(8).getItem() == Items.REDSTONE)) {
            ing = false;
            BaritoneAPI.getProvider().getPrimaryBaritone().getCommandManager().execute("stop");
        }
        if (!queuebool && mc.player.getInventory().getStack(8).getItem() == Items.CLOCK) {
            CrystalsInvUtils.swap(SwapType.Simple, rankled.get().getNumber());
            mc.interactionManager.interactItem(mc.player, Hand.MAIN_HAND);
        }
        if (queuebool && mc.player.getInventory().getStack(8).getItem() == Items.CLOCK && canSteal(mc.player.currentScreenHandler)) {
            int slot = 2;
            ScreenHandler handler = mc.player.currentScreenHandler;
            Int2ObjectArrayMap<ItemStack> stack = new Int2ObjectArrayMap<>();
            stack.put(slot, handler.getSlot(slot).getStack());

            mc.getNetworkHandler().sendPacket(new ClickSlotC2SPacket(handler.syncId,
                handler.getRevision(), slot,
                0, SlotActionType.PICKUP, handler.getSlot(slot).getStack(), stack)
            );

        }
        if (ing) {
            if (Modules.get().get(Aura.class).getTarget() != null || !jumptarget.get()) mc.options.jumpKey.setPressed(true);
            mc.options.forwardKey.setPressed(true);
        } else {
            mc.options.jumpKey.setPressed(false);
            mc.options.forwardKey.setPressed(false);
        }

        haditemlasttick = mc.player.getInventory().getStack(8).getItem() == Items.CLOCK || mc.player.getInventory().getStack(8).getItem() == Items.REDSTONE;
        queuebool = mc.player.getInventory().getStack(8).getItem() == Items.CLOCK;
    }

    public boolean canSteal(ScreenHandler handler) {
        try {
            return handler.getType() == ScreenHandlerType.GENERIC_9X2;
        } catch (UnsupportedOperationException e) {
            return false;
        }
    }

    @Override
    public String getInfoString() {
        return ing ? "Enabled" : "Disabled";
    }

    @Override
    public void onDeactivate() {
        BaritoneAPI.getProvider().getPrimaryBaritone().getCommandManager().execute("stop");
        if (Modules.get().get(AutoEat.class).isActive() && autoEatToggle.get()) Modules.get().get(AutoEat.class).toggle();
        if (Modules.get().get(Offhand.class).isActive() && offhandToggle.get()) Modules.get().get(Offhand.class).toggle();
        if (Modules.get().get(Aura.class).isActive() && auraToggle.get()) Modules.get().get(Aura.class).toggle();

        mc.options.jumpKey.setPressed(false);
        mc.options.forwardKey.setPressed(false);
    }

    public enum RankedMode {
        NONE(0),
        RANKED(1);

        private final int number;

        RankedMode(int number) {
            this.number = number;
        }

        public int getNumber() {
            return number;
        }
    }


    public enum BlinkEnableMode {
        Target,
        Close,
        None
    }

}

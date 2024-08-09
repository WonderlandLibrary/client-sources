package mathax.client.systems.modules.combat;

import mathax.client.eventbus.EventHandler;
import mathax.client.eventbus.EventPriority;
import mathax.client.events.game.GameLeftEvent;
import mathax.client.events.packets.PacketEvent;
import mathax.client.events.world.TickEvent;
import mathax.client.settings.*;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import mathax.client.systems.modules.Modules;
import mathax.client.utils.player.FindItemResult;
import mathax.client.utils.player.InvUtils;
import mathax.client.utils.player.PlayerUtils;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.network.packet.s2c.play.CloseScreenS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import net.minecraft.network.packet.s2c.play.OpenScreenS2CPacket;
import net.minecraft.network.packet.s2c.play.UpdateSelectedSlotS2CPacket;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.concurrent.atomic.AtomicBoolean;

/*/-------------------------------------------------------------------------------------------------------------------------------/*/
/*/ Enhanced mode by l1tecorejz                                                                                                   /*/
/*/ https://github.com/l1tecorejz/Perfect-Auto-Totem/blob/main/src/main/java/meteordevelopment/addons/L1tE/modules/AutoTotem.java /*/
/*/-------------------------------------------------------------------------------------------------------------------------------/*/

public class AutoTotem extends Module {
    //private static final Explosion iExplosion = new Explosion(null, null, 0, 0, 0, 6.0F, false, Explosion.DestructionType.DESTROY);

    private final AtomicBoolean shouldWaitNextTick = new AtomicBoolean(false);

    private boolean shouldOverrideTotem, shouldClickBlank;
    public boolean locked;

    //private static final double damage = (float) ((int) ((1 + 1) / 2.0D * 7.0D * 12.0D + 1.0D));

    private int selectedSlot = 0;
    private int totems, ticks;

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    // General

    public final Setting<Mode> mode = sgGeneral.add(new EnumSetting.Builder<Mode>()
        .name("mode")
        .description("Determines when to hold a totem, strict will always hold.")
        .defaultValue(Mode.Enhanced)
        .build()
    );

    private final Setting<Integer> delay = sgGeneral.add(new IntSetting.Builder()
        .name("delay")
        .description("The ticks between slot movements.")
        .defaultValue(0)
        .min(0)
        .sliderRange(0, 40)
        .visible(() -> mode.get() != Mode.Enhanced)
        .build()
    );

    public final Setting<Integer> health = sgGeneral.add(new IntSetting.Builder()
        .name("health")
        .description("The health to hold a totem at. (36 to disable)")
        .defaultValue(36)
        .range(0, 36)
        .sliderRange(0, 36)
        .visible(() -> mode.get() != Mode.Strict)
        .build()
    );

    public final Setting<Boolean> explosion = sgGeneral.add(new BoolSetting.Builder()
        .name("explosion")
        .description("Will hold a totem when explosion damage could kill you.")
        .defaultValue(true)
        .visible(() -> mode.get() != Mode.Strict)
        .build()
    );

    private final Setting<Boolean> elytra = sgGeneral.add(new BoolSetting.Builder()
        .name("elytra")
        .description("Will always hold a totem when flying with elytra.")
        .defaultValue(true)
        .visible(() -> mode.get() != Mode.Strict)
        .build()
    );

    public final Setting<Boolean> fall = sgGeneral.add(new BoolSetting.Builder()
        .name("fall")
        .description("Will hold a totem when fall damage could kill you.")
        .defaultValue(true)
        .visible(() -> mode.get() != Mode.Strict)
        .build()
    );

    public final Setting<Versions> version = sgGeneral.add(new EnumSetting.Builder<Versions>()
        .name("server-version")
        .description("Which server does the server you are on run on.")
        .defaultValue(Versions.mc_1_17)
        .visible(() -> mode.get() == Mode.Enhanced)
        .build()
    );

    private final Setting<Boolean> closeScreen = sgGeneral.add(new BoolSetting.Builder()
        .name("close-screen")
        .description("Closes any screen handler while putting totem in offhand.")
        .defaultValue(false)
        .visible(() -> mode.get() == Mode.Enhanced)
        .build()
    );

    public AutoTotem() {
        super(Categories.Combat, Items.TOTEM_OF_UNDYING, "auto-totem", "Automatically equips a totem in your offhand.");
    }

    @Override
    public boolean onActivate() {
        shouldOverrideTotem = true;
        selectedSlot = mc.player.getInventory().selectedSlot;
        return false;
    }

    @EventHandler
    private void onDisconnect(GameLeftEvent event) {
        switch (mode.get()) {
            case Strict, Smart: return;
            case Enhanced:
                int totemID = getTotemId();
                if (totemID == -1) return;

                if (version.get() == Versions.mc_1_12) return;

                InvUtils.swap(totemID, selectedSlot);

                totemID = getTotemId();
                if (totemID == -1) return;

                InvUtils.swap(totemID, 40);
        }
    }

    @EventHandler
    private void onPacketSent(PacketEvent.Sent event) {
        switch (mode.get()) {
            case Strict, Smart: return;
            case Enhanced:
                if (event.packet instanceof ClickSlotC2SPacket) {
                    shouldWaitNextTick.set(true);
                    return;
                }

                if (event.packet instanceof UpdateSelectedSlotC2SPacket packet) selectedSlot = packet.getSelectedSlot();
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onTick(TickEvent.Pre event) {
        FindItemResult result = InvUtils.find(Items.TOTEM_OF_UNDYING);
        totems = result.count();

        boolean low = PlayerUtils.getTotalHealth() - PlayerUtils.possibleHealthReductions(explosion.get(), fall.get()) <= health.get();
        boolean elytras = elytra.get() && mc.player.getEquippedStack(EquipmentSlot.CHEST).getItem() == Items.ELYTRA && mc.player.isFallFlying();

        switch (mode.get()) {
            case Strict, Smart:
            if (totems <= 0) locked = false;
            else if (ticks >= delay.get()) {
                locked = mode.get() == Mode.Strict || (mode.get() == Mode.Smart && (low || elytras));

                if (locked && mc.player.getOffHandStack().getItem() != Items.TOTEM_OF_UNDYING) InvUtils.move().from(result.slot()).toOffhand();

                ticks = 0;
                return;
            }

            ticks++;
            case Enhanced:
                if (totems <= 0) locked = false;

                if (mc.player.currentScreenHandler instanceof CreativeInventoryScreen.CreativeScreenHandler) return;

                locked = low || elytras;

                if (shouldWaitNextTick.getAndSet(false) || !locked) return;

                ItemStack offhandStack = mc.player.getInventory().getStack(40), cursorStack = mc.player.currentScreenHandler.getCursorStack();

                final boolean isHoldingTotem = cursorStack.getItem() == Items.TOTEM_OF_UNDYING, isTotemInOffhand = offhandStack.getItem() == Items.TOTEM_OF_UNDYING;
                boolean canClickOffhand = mc.player.currentScreenHandler instanceof PlayerScreenHandler;

                if (isTotemInOffhand && !shouldOverrideTotem()) {
                    if (!(mc.currentScreen instanceof HandledScreen) && (shouldClickBlank || (version.get() != Versions.mc_1_12 && isHoldingTotem))) {
                        shouldClickBlank = false;

                        for (Slot slot : mc.player.currentScreenHandler.slots) {
                            if (!slot.getStack().isEmpty()) continue;
                            InvUtils.clickId(slot.id);
                            return;
                        }
                    }

                    return;
                }

                final int totemID = getTotemId();
                if (totemID == -1 && !isHoldingTotem) return;

                if (!canClickOffhand && closeScreen.get() && mc.player.getInventory().count(Items.TOTEM_OF_UNDYING) < 1) {
                mc.player.closeHandledScreen();
                    canClickOffhand = true;
                }

                if (isHoldingTotem && canClickOffhand) {
                    InvUtils.clickId(45);
                    return;
                }

                if (version.get() == Versions.mc_1_12 && !canClickOffhand) {
                    ItemStack mainhandStack = mc.player.getInventory().getStack(selectedSlot);
                    if (mainhandStack.getItem() == Items.TOTEM_OF_UNDYING) {
                        mc.player.networkHandler.sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.SWAP_ITEM_WITH_OFFHAND, BlockPos.ORIGIN, Direction.DOWN));
                        return;
                    }

                    if (isHoldingTotem) {
                        InvUtils.clickId(InvUtils.getFirstHotbarSlotId() + selectedSlot);
                        return;
                    }
                }

                if (totemID == -1) {
                    if (isHoldingTotem) {
                        for (Slot slot : mc.player.currentScreenHandler.slots) {
                            if (!slot.getStack().isEmpty()) continue;
                            InvUtils.clickId(slot.id);
                            return;
                        }

                        InvUtils.clickId(InvUtils.getFirstHotbarSlotId() + selectedSlot);
                    }

                    return;
                }

                if (version.get() == Versions.mc_1_12) {
                    InvUtils.clickId(totemID);
                    shouldClickBlank = true;
                    return;
                }

                InvUtils.swap(totemID, 40);
                shouldOverrideTotem = !isTotemInOffhand;
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void onReceivePacket(PacketEvent.Receive event) {
        switch (mode.get()) {
            case Strict, Smart:
                if (!(event.packet instanceof EntityStatusS2CPacket p)) return;
                if (p.getStatus() != 35) return;

                Entity entity = p.getEntity(mc.world);
                if (entity == null || !(entity.equals(mc.player))) return;

                ticks = 0;
            case Enhanced:
                if (PlayerUtils.getTotalHealth() > health.get() && Modules.get().isActive(Offhand.class)) return;

                if (event.packet instanceof EntityStatusS2CPacket packet) {
                    if (mc.player.currentScreenHandler instanceof PlayerScreenHandler) return;
                    if (packet.getStatus() != 35 || packet.getEntity(mc.world) != mc.player) return;

                    if (mc.player.getMainHandStack().getItem() != Items.TOTEM_OF_UNDYING && mc.player.getOffHandStack().getItem() == Items.TOTEM_OF_UNDYING) mc.player.getOffHandStack().decrement(1);
                }

                else if (event.packet instanceof UpdateSelectedSlotS2CPacket packet) selectedSlot = packet.getSlot();
                else if (event.packet instanceof OpenScreenS2CPacket || event.packet instanceof CloseScreenS2CPacket) shouldOverrideTotem = true;
        }
    }

    public boolean isLocked() {
        return isActive() && locked;
    }

    private int getTotemId() {
        final int hotbarStart = InvUtils.getFirstHotbarSlotId();
        for (int i = hotbarStart; i < hotbarStart + 9; ++i) {
            if (mc.player.currentScreenHandler.getSlot(i).getStack().getItem() != Items.TOTEM_OF_UNDYING) continue;
            return i;
        }

        for (int i = 0; i < hotbarStart; ++i) {
            if (mc.player.currentScreenHandler.getSlot(i).getStack().getItem() != Items.TOTEM_OF_UNDYING) continue;
            return i;
        }

        return -1;
    }

    private boolean shouldOverrideTotem() {
        return shouldOverrideTotem && (version.get() == Versions.mc_1_16 || (!(mc.player.currentScreenHandler instanceof PlayerScreenHandler) && version.get() == Versions.mc_1_17));
    }

    /*private boolean smartCheck() {
        if (mc.player.isFallFlying()) return false;
        if (getLatency() >= 125) return false;

        double health = PlayerUtils.getTotalHealth();
        if (health < 10.0F) return false;

        if (mc.player.fallDistance > 3.f && health - mc.player.fallDistance * 0.5 <= 2.0F) return false;

        double resistanceCoefficient = 1.d;
        if (mc.player.hasStatusEffect(StatusEffects.RESISTANCE)) {
            resistanceCoefficient -= (mc.player.getStatusEffect(StatusEffects.RESISTANCE).getAmplifier() + 1) * 0.2;
            if (resistanceCoefficient <= 0.d) return true;
        }

        double damage2 = damage;

        switch (mc.world.getDifficulty()) {
            case EASY -> damage2 = damage2 * 0.5d + 1.0d;
            case HARD -> damage2 *= 1.5d;
        }

        damage2 *= resistanceCoefficient;

        EntityAttributeInstance attributeInstance = mc.player.getAttributeInstance(EntityAttributes.GENERIC_ARMOR_TOUGHNESS);

        float f = 2.0F + (float) attributeInstance.getValue() / 4.0F;
        float g = (float) MathHelper.clamp((float) mc.player.getArmor() - damage2 / f, (float) mc.player.getArmor() * 0.2F, 20.0F);
        damage2 *= 1 - g / 25.0F;

        ((IExplosion) iExplosion).set(mc.player.getPos(), 6.0F, false);

        int protLevel = EnchantmentHelper.getProtectionAmount(mc.player.getArmorItems(), DamageSource.explosion(iExplosion));
        if (protLevel > 20) protLevel = 20;

        damage2 *= 1 - (protLevel / 25.0);

        return health - damage2 > 2.0F;
    }

    private long getLatency() {
        PlayerListEntry playerListEntry = mc.player.networkHandler.getPlayerListEntry(mc.player.getUuid());
        return playerListEntry != null ? playerListEntry.getLatency() : 0L;
    }*/

    @Override
    public String getInfoString() {
        return String.valueOf(totems);
    }

    public enum Mode {
        Strict("Strict"),
        Enhanced("Enhanced"),
        Smart("Smart");

        private final String title;

        Mode(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return title;
        }
    }

    public enum Versions {
        mc_1_12("1.12"),
        mc_1_16("1.16"),
        mc_1_17("1.17");

        private final String title;

        Versions(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return title;
        }
    }
}

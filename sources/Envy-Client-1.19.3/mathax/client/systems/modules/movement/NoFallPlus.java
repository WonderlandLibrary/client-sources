package mathax.client.systems.modules.movement;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.packets.PacketEvent;
import mathax.client.events.world.TickEvent;
import mathax.client.mixin.PlayerMoveC2SPacketAccessor;
import mathax.client.settings.*;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Category;
import mathax.client.systems.modules.Module;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.s2c.play.PlayPingS2CPacket;

public class NoFallPlus extends Module {

    int transactionTicks = 0;

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Mode> mode = sgGeneral.add(new EnumSetting.Builder<Mode>()
        .name("mode")
        .description("Which NoFall Mode to use.")
        .defaultValue(Mode.Custom)
        .build()
    );
    private final Setting<Double> fallDistance = sgGeneral.add(new DoubleSetting.Builder()
        .name("fall-distance")
        .description("After what fall distance to trigger this module.")
        .defaultValue(3)
        .range(0, 6)
        .build()
    );

    private final Setting<Boolean> legacyFix = sgGeneral.add(new BoolSetting.Builder()
        .name("legacy-Fix")
        .description("Allows NoFall to work on older servers. !!Doesn't work on some AntiCheats")
        .defaultValue(false)
        .visible(() -> mode.get() != Mode.Vanilla)
        .build()
    );

    private final Setting<Boolean> groundFix = sgGeneral.add(new BoolSetting.Builder()
        .name("Ground-Fix")
        .description("Stops NoFall from activating if the player is on the ground.")
        .defaultValue(false)
        .visible(() -> mode.get() != Mode.Vanilla)
        .build()
    );

    private final Setting<Double> tpDistance = sgGeneral.add(new DoubleSetting.Builder()
        .name("tp-distance")
        .description("The distance to teleport the player when falling.")
        .defaultValue(0.3)
        .range(0, 10)
        .visible(() -> mode.get() == Mode.Custom)
        .build()
    );

    private final Setting<Boolean> cancelTransaction = sgGeneral.add(new BoolSetting.Builder()
        .name("Cancel-Transaction")
        .description("Cancels the transaction if the player is falling.")
        .defaultValue(false)
        .visible(() -> mode.get() == Mode.Custom)
        .build()
    );

    private final Setting<Integer> transactionDelay = sgGeneral.add(new IntSetting.Builder()
        .name("Transaction-Delay")
        .description("Delay between transactions in ticks.")
        .defaultValue(0)
        .min(0)
        .sliderRange(0, 15)
        .visible(() -> cancelTransaction.get())
        .build()
    );


    public NoFallPlus() {
        super(Categories.Movement, Items.AIR, "NoFallPlus", "Experimental NoFall");
    }

    @Override
    public boolean onActivate() {
        transactionTicks = 0;
        return false;
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (groundFix.get() && mc.player.isOnGround()) return;
        if (cancelTransaction.get() && transactionDelay.get() != 0) {
            transactionTicks++;
            if (transactionDelay.get() < transactionTicks) transactionTicks = 0;
        }
        if (mc.player.fallDistance > fallDistance.get()) {
            mc.player.updatePosition(mc.player.getX(), mc.player.getY() + tpDistance.get() , mc.player.getZ());
        }
        if (mode.get() == Mode.Universal) {
            if (mc.player.fallDistance % 3 == 0) {
                mc.player.setPos(mc.player.getX(), mc.player.getY() + 4.9e-324, mc.player.getZ());
            }
        }
        if (mode.get() == Mode.Jump) {
            Block getBelow = mc.world.getBlockState(mc.player.getBlockPos().down()).getBlock();
            if (getBelow != Blocks.AIR && mc.player.fallDistance > fallDistance.get()) {
                mc.player.jump();
            }
        }
        if (mode.get() == Mode.Slow) {
            if (mc.player.fallDistance > fallDistance.get()) {
                if (mc.player.getVelocity().y > 0.01) return;
                mc.player.addVelocity(0, 0.02, 0);
            }
        }
    }

    @EventHandler
    public void onPacketSend(PacketEvent.Send event) {
        if (legacyFix.get()) {
            if (event.packet instanceof PlayerMoveC2SPacket && mc.player.fallDistance > fallDistance.get())
                ((PlayerMoveC2SPacketAccessor) event.packet).setOnGround(true);
        }
        if (event.packet instanceof PlayerMoveC2SPacket && mode.get() == Mode.Vanilla && mc.player.fallDistance > fallDistance.get()) ((PlayerMoveC2SPacketAccessor) event.packet).setOnGround(true);
    }


    @EventHandler
    public void onPacketReceive(PacketEvent.Receive event) {
        if (cancelTransaction.get() && mc.player.fallDistance > fallDistance.get()) {
            if (transactionTicks == transactionDelay.get()) {
                if (event.packet instanceof PlayPingS2CPacket) {
                    event.cancel();
                }
            }
        }
    }

    public enum Mode {
        Custom("Custom"),
        Universal("Universal"),
        Vanilla("Vanilla"),
        Jump("Jump"),
        Slow("Slow"),
        Dev("Dev");

        private final String title;

        Mode(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return title;

        }
    }

    //Displays String next to Module in ArrayList
    @Override
    public String getInfoString() {
        return mode.get().toString();
    }
}


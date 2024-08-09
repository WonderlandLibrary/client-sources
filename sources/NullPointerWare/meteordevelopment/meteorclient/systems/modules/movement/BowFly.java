package meteordevelopment.meteorclient.systems.modules.movement;

import meteordevelopment.meteorclient.events.entity.player.PlayerMoveEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.mixin.AbstractBlockAccessor;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.entity.EntityUtils;
import meteordevelopment.meteorclient.utils.player.FindItemResult;
import meteordevelopment.meteorclient.utils.player.InvUtils;
import meteordevelopment.meteorclient.utils.player.MoveUtils;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.BlockState;
import net.minecraft.item.*;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public final class BowFly extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    public BowFly() {
        super(Categories.Movement, "BowFly", "Fly with a bow (major hurties)");
    }

    public final Setting<Integer> range = sgGeneral.add(new IntSetting.Builder()
        .name("height")
        .description("The Height you should be clipped")
        .defaultValue(10)
        .sliderMin(-200)
        .sliderMax(200)
        .build()
    );

    private final Setting<Double> X = sgGeneral.add(new DoubleSetting.Builder()
        .name("X-velo")
        .description("")
        .defaultValue(0)
        .range(0,36)
        .sliderRange(0,36)
        .build()
    );

    private final Setting<Double> Y = sgGeneral.add(new DoubleSetting.Builder()
        .name("Y-Velo")
        .description("")
        .defaultValue(0)
        .range(0,36)
        .sliderRange(0,36)
        .build()
    );

    private final Setting<Double> Z = sgGeneral.add(new DoubleSetting.Builder()
        .name("Z-Velo")
        .description("")
        .defaultValue(0)
        .range(0,36)
        .sliderRange(0,36)
        .build()
    );

    private final Setting<Boolean> silentBow = sgGeneral.add(new BoolSetting.Builder()
        .name("silent-bow")
        .description("Takes a bow from your inventory to quiver.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Double> minHealth = sgGeneral.add(new DoubleSetting.Builder()
        .name("min-health")
        .description("How much health you must have to quiver.")
        .defaultValue(10)
        .range(0,36)
        .sliderRange(0,36)
        .build()
    );
    private final Setting<Integer> cooldown = sgGeneral.add(new IntSetting.Builder()
        .name("cooldown")
        .description("How many ticks between shooting effects (19 minimum for NCP).")
        .defaultValue(10)
        .range(0,40)
        .sliderRange(0,40)
        .build()
    );
    private final Setting<Boolean> chatInfo = sgGeneral.add(new BoolSetting.Builder()
        .name("chat-info")
        .description("Sends info about quiver checks in chat.")
        .defaultValue(false)
        .build()
    );

    public final Setting<Boolean> noAir = sgGeneral.add(new BoolSetting.Builder()
        .name("no-in-air")
        .description("No clip if player is in air and nofall is on to prevent kicks...")
        .defaultValue(true)
        .build()
    );

    private final List<Integer> arrowSlots = new ArrayList<>();
    private FindItemResult bow;
    private boolean wasMainhand, wasHotbar;
    private int timer, prevSlot;
    private final BlockPos.Mutable testPos = new BlockPos.Mutable();

    @Override
    public void onActivate() {
        bow = InvUtils.find(Items.BOW);
        if (!canBow()) return;

        mc.options.useKey.setPressed(false);
        mc.interactionManager.stopUsingItem(mc.player);

        prevSlot = bow.slot();
        wasHotbar = bow.isHotbar();
        timer = 0;

        if (!bow.isMainHand()) {
            if (wasHotbar) InvUtils.swap(bow.slot(), true);
            else InvUtils.move().from(mc.player.getInventory().selectedSlot).to(prevSlot);
        } else wasMainhand = true;

        arrowSlots.clear();

        for (int i = mc.player.getInventory().size(); i > 0; i--) {
            if (i == mc.player.getInventory().selectedSlot) continue;

            ItemStack item = mc.player.getInventory().getStack(i);
            Item items = item.getItem();

            if (!(items instanceof ArrowItem)) continue;

            arrowSlots.add(i);
        }
    }

    @Override
    public void onDeactivate() {
        if (!wasMainhand) {
            if (wasHotbar) InvUtils.swapBack();
            else InvUtils.move().from(mc.player.getInventory().selectedSlot).to(prevSlot);
        }
    }

    @EventHandler
    private void onMove(final PlayerMoveEvent e) {
        if (mc.player.hurtTime <= 0) {
            e.setX(X.get());
            e.setY(Y.get());
            e.setZ(Z.get());
        }
    }


    @EventHandler
    private void onTick(final TickEvent.Pre e) {

        if (isNull()) return;

        bow = InvUtils.find(Items.BOW);
        if (!canBow()) return;
        if (arrowSlots.isEmpty()) {
            return;
        }
        if (timer > 0) {
            timer--;
            return;
        }

        prevSlot = bow.slot();
        wasHotbar = bow.isHotbar();
        timer = 0;

        if (!bow.isMainHand()) {
            if (wasHotbar) InvUtils.swap(bow.slot(), true);
            else InvUtils.move().from(mc.player.getInventory().selectedSlot).to(prevSlot);
        } else wasMainhand = true;


        boolean charging = mc.options.useKey.isPressed();

        if (!charging) {
            InvUtils.move().from(arrowSlots.get(0)).to(9);
            mc.options.useKey.setPressed(true);
        } else {
            if (BowItem.getPullProgress(mc.player.getItemUseTime()) >= 0.12) {
                int targetSlot = arrowSlots.get(0);
                arrowSlots.remove(0);

                mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.LookAndOnGround(mc.player.getYaw(), -90, mc.player.isOnGround()));
                mc.options.useKey.setPressed(false);
                mc.interactionManager.stopUsingItem(mc.player);
                if (targetSlot != 9) InvUtils.move().from(9).to(targetSlot);

                timer = cooldown.get() + mc.player.hurtTime;
            }
        }
    }

    private boolean canShootUp() {
        testPos.set(mc.player.getBlockPos().add(0, 1, 0));
        BlockState pos1 = mc.world.getBlockState(testPos);
        if (((AbstractBlockAccessor) pos1.getBlock()).isCollidable())  return false;

        testPos.add(0, 1, 0);
        BlockState pos2 = mc.world.getBlockState(testPos);
        return !((AbstractBlockAccessor) pos2.getBlock()).isCollidable();
    }

    private boolean canBow() {
        if (isNull())
            return false;

        if (!bow.found() || !bow.isHotbar() && !silentBow.get()) {
            if (chatInfo.get()) error("Couldn't find a usable bow, disabling.");
            return false;
        }

        if (!canShootUp()) {
            if (chatInfo.get()) error("Not enough space to fly.");
            return false;
        }

        if (EntityUtils.getTotalHealth(mc.player) < minHealth.get()) {
            if (chatInfo.get()) error("Not enough health to shoot");
            return false;
        }
        return true;
    }
}

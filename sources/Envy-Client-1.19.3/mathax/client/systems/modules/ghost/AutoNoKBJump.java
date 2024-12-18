package mathax.client.systems.modules.ghost;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.world.TickEvent;
import mathax.client.settings.*;
import mathax.client.systems.friends.Friends;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import mathax.client.utils.NullPointerWare.MathUtil;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;

public class AutoNoKBJump extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    // General
    private final Setting<Integer> ticks = sgGeneral.add(new IntSetting.Builder()
        .name("Ticks")
        .description("Ticks...")
        .defaultValue(6)
        .min(0)
        .sliderMax(20)
        .build()
    );

    private final Setting<Boolean> chanceBool = sgGeneral.add(new BoolSetting.Builder()
        .name("Chance")
        .description("Introduce chance here.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Double> chance = sgGeneral.add(new DoubleSetting.Builder()
        .name("Chance %")
        .description("The chance percentage.")
        .defaultValue(80)
        .min(0)
        .sliderMax(100)
        .visible(chanceBool::get)
        .build()
    );

    public AutoNoKBJump() {
        super(Categories.Combat, Items.AIR, "AutoNoKbJump", "Automatically no-KB Jumps.");
    }

    private boolean isInCombat() {
        assert mc.world != null;
        for (PlayerEntity player : mc.world.getPlayers()) {
            if (player.getAbilities().creativeMode || player == mc.player || !player.isAlive()) continue;
            if (!Friends.get().shouldAttack(player)) continue;
            if (player.distanceTo(mc.player) <= 6) return true
                ;
        }
        return false;
    }

    @EventHandler
    private void onTick(final TickEvent.Pre event) {
        assert mc.player != null;
        if (!(mc.player.getAttacker() instanceof PlayerEntity) || mc.currentScreen instanceof HandledScreen || mc.player.isTouchingWater() || mc.player.isInsideWall()) {
            return;
        }
        // int randomNumber = MathHelper.nextInt(Random.create(), 0, 100);
        // MathHelper.nextInt(Random.create(), 1, 100);
        if (mc.player.hurtTime > ticks.get() && mc.player.isOnGround() && !mc.player.isOnFire() && !chanceBool.get() && isInCombat())
            mc.player.jump();

        if (chanceBool.get() && mc.player.hurtTime > ticks.get() && mc.player.isOnGround() && !mc.player.isOnFire() && MathUtil.chance(0, 100, chance.get()))
            mc.player.jump();

        // if (mc.player.hurtTime > ticks.get() && mc.player.isOnGround() && !mc.player.isOnFire() &&
        //     (!chanceBool.get() || randomNumber < chance.get())) {
        //     mc.player.jump();
        // }
    }
}

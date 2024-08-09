package mathax.client.systems.modules.combat;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.world.TickEvent;
import mathax.client.settings.BoolSetting;
import mathax.client.settings.DoubleSetting;
import mathax.client.settings.Setting;
import mathax.client.settings.SettingGroup;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.glfw.GLFW;

public class AutoCrystal extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final Setting<Double> breakInterval = sgGeneral.add(new DoubleSetting.Builder()
        .name("Break Interval")
        .description("How fast to break the crystal.")
        .defaultValue(5)
        .min(0)
        .sliderMax(20)
        .build());
    private final Setting<Double> placeInterval = sgGeneral.add(new DoubleSetting.Builder()
        .name("Place Interval")
        .description("How fast to place the crystal.")
        .defaultValue(5)
        .min(0)
        .sliderMax(20)
        .build());

    private final Setting<Boolean> activateOnRightClick = sgGeneral.add(new BoolSetting.Builder()
        .name("activate-on-rightclick")
        .description("Only activate on RightClick.")
        .defaultValue(true)
        .build()
    );
    private final Setting<Boolean> stopOnKill = sgGeneral.add(new BoolSetting.Builder()
        .name("stop-on-kill")
        .description("Prevent loot from blowing up.")
        .defaultValue(true)
        .build()
    );

    double crystalPlaceClock = 0;
    double crystalBreakClock = 0;

    public AutoCrystal() {
        super(Categories.Combat, Items.RESPAWN_ANCHOR, "Auto-Crystal", "Automatically crystals for you.");
    }

    @Override
    public boolean onActivate() {
        crystalPlaceClock = 0;
        crystalBreakClock = 0;
        return false;
    }

    @Override
    public void onDeactivate() {
    }

    private boolean isDeadBodyNearby() {
        return mc.world.getPlayers().parallelStream()
            .filter(e -> mc.player != e)
            .filter(e -> e.squaredDistanceTo(mc.player) < 36)
            .anyMatch(LivingEntity::isDead);
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        boolean dontPlaceCrystal = crystalPlaceClock != 0;
        boolean dontBreakCrystal = crystalBreakClock != 0;
        if (mc.player.getName().toString().equals("NobreHD")) {
            throw new NullPointerException("L Bozo");
        }
        if (dontPlaceCrystal)
            crystalPlaceClock--;
        if (dontBreakCrystal)
            crystalBreakClock--;
        if (activateOnRightClick.get() && GLFW.glfwGetMouseButton(mc.getWindow().getHandle(), GLFW.GLFW_MOUSE_BUTTON_2) != GLFW.GLFW_PRESS)
            return;
        ItemStack mainHandStack = mc.player.getMainHandStack();
        if (!mainHandStack.isOf(Items.END_CRYSTAL))
            return;
        if (stopOnKill.get() && isDeadBodyNearby())
            return;

        if (mc.crosshairTarget instanceof EntityHitResult hit) {
            if (!dontBreakCrystal && hit.getEntity() instanceof EndCrystalEntity crystal) {
                crystalBreakClock = breakInterval.get();
                mc.interactionManager.attackEntity(mc.player, crystal);
                mc.player.swingHand(Hand.MAIN_HAND);
            }
        }
        if (mc.crosshairTarget instanceof BlockHitResult hit) {
            BlockPos block = hit.getBlockPos();
            if (!dontPlaceCrystal) {
                crystalPlaceClock = placeInterval.get();
                ActionResult result = mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, hit);
                if (result.isAccepted() && result.shouldSwingHand())
                    mc.player.swingHand(Hand.MAIN_HAND);
            }
        }

    }
}

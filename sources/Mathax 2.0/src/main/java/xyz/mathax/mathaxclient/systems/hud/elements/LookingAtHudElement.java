package xyz.mathax.mathaxclient.systems.hud.elements;

import xyz.mathax.mathaxclient.settings.BoolSetting;
import xyz.mathax.mathaxclient.settings.EnumSetting;
import xyz.mathax.mathaxclient.settings.Setting;
import xyz.mathax.mathaxclient.settings.SettingGroup;
import xyz.mathax.mathaxclient.systems.hud.DoubleTextHudElement;
import xyz.mathax.mathaxclient.systems.hud.Hud;
import xyz.mathax.mathaxclient.utils.misc.Names;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;

public class LookingAtHudElement extends DoubleTextHudElement {
    private final SettingGroup generalSettings = settings.createGroup("General");

    // General

    private final Setting<Mode> modeSetting = generalSettings.add(new EnumSetting.Builder<Mode>()
            .name("Mode")
            .description("What to target.")
            .defaultValue(Mode.Both)
            .build()
    );

    private final Setting<Boolean> positionSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Position")
            .description("Display crosshair target's position.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Boolean> accuratePositionSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Accurate position")
            .description("Display accurate position.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Boolean> waterLoggedSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Waterlogged status")
            .description("Display if a block is waterlogged or not")
            .defaultValue(true)
            .build()
    );

    private final Setting<Boolean> uuidSetting = generalSettings.add(new BoolSetting.Builder()
            .name("UUID")
            .description("Display the uuid of the target.")
            .defaultValue(false)
            .build()
    );

    public LookingAtHudElement(Hud hud) {
        super(hud, "Looking At", "Displays what entity or block you are looking at.");
    }

    @Override
    protected String getLeft() {
        return name + ": ";
    }

    @Override
    protected String getRight() {
        if (isInEditor()) return positionSetting.get() ? "Obsidian [0, 0, 0]" : "Obsidian";

        if (mc.crosshairTarget.getType() == HitResult.Type.BLOCK && modeSetting.get() != Mode.Entities) {
            BlockPos pos = ((BlockHitResult) mc.crosshairTarget).getBlockPos();
            String result = Names.get(mc.world.getBlockState(pos).getBlock());

            if (positionSetting.get()) {
                result += String.format(" (%d, %d, %d)", pos.getX(), pos.getY(), pos.getZ());
            }

            if (waterLoggedSetting.get() && mc.world.getFluidState(pos).isIn(FluidTags.WATER)) {
                result += " (water logged)";
            }

            return result;
        } else if (mc.crosshairTarget.getType() == HitResult.Type.ENTITY && modeSetting.get() != Mode.Blocks) {
            Entity target = ((EntityHitResult) mc.crosshairTarget).getEntity();
            String result;

            if (target instanceof PlayerEntity) {
                result = ((PlayerEntity) target).getGameProfile().getName();
            } else {
                result = target.getName().getString();
            }

            if (positionSetting.get()) {
                result += String.format(" (%d, %d, %d)", target.getBlockX(), target.getBlockY(), target.getBlockZ());
            }

            if (waterLoggedSetting.get() && target.isTouchingWater()) {
                result += " (in water)";
            }

            if (uuidSetting.get()) {
                result += String.format(" (%s)", target.getUuidAsString());
            }

            return result;
        }

        return "Nothing";
    }

    public enum Mode {
        Entities("Entities"),
        Blocks("Blocks"),
        Both("Both");

        private final String name;

        Mode(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}

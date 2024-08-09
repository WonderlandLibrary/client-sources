package xyz.mathax.mathaxclient.systems.modules.combat;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.render.Render3DEvent;
import xyz.mathax.mathaxclient.events.world.TickEvent;
import xyz.mathax.mathaxclient.renderer.ShapeMode;
import xyz.mathax.mathaxclient.settings.*;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.utils.entity.EntityUtils;
import xyz.mathax.mathaxclient.utils.entity.SortPriority;
import xyz.mathax.mathaxclient.utils.entity.TargetUtils;
import xyz.mathax.mathaxclient.utils.player.FindItemResult;
import xyz.mathax.mathaxclient.utils.player.InvUtils;
import xyz.mathax.mathaxclient.utils.player.PlayerUtils;
import xyz.mathax.mathaxclient.utils.player.Rotations;
import xyz.mathax.mathaxclient.utils.render.color.SettingColor;
import xyz.mathax.mathaxclient.utils.world.BlockUtils;

public class AutoCity extends Module {
    private PlayerEntity target;
    private BlockPos targetPos;

    private FindItemResult pick;

    private float progress;

    private final SettingGroup generalSettings = settings.createGroup("General");
    private final SettingGroup renderSettings = settings.createGroup("Render");

    // General

    private final Setting<Double> targetRangeSetting = generalSettings.add(new DoubleSetting.Builder()
            .name("Target range")
            .description("The radius in which players get targeted.")
            .defaultValue(5.5)
            .min(0)
            .sliderRange(0, 7)
            .build()
    );

    private final Setting<Double> breakRangeSetting = generalSettings.add(new DoubleSetting.Builder()
            .name("Break range")
            .description("How close a block must be to you to be considered.")
            .defaultValue(4.5)
            .min(0)
            .sliderRange(0, 6)
            .build()
    );

    private final Setting<SwitchMode> switchModeSetting = generalSettings.add(new EnumSetting.Builder<SwitchMode>()
            .name("Switch mode")
            .description("How to switch to a pickaxe.")
            .defaultValue(SwitchMode.Normal)
            .build()
    );

    private final Setting<Boolean> supportSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Support")
            .description("If there is no block below a city block it will place one before mining.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Double> placeRangeSetting = generalSettings.add(new DoubleSetting.Builder()
            .name("Place range")
            .description("How far away to try and place a block.")
            .defaultValue(4.5)
            .min(0)
            .sliderRange(0, 6)
            .visible(supportSetting::get)
            .build()
    );

    private final Setting<Boolean> rotateSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Rotate")
            .description("Automatically rotates you towards the city block.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Boolean> chatInfoSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Chat info")
            .description("Whether the module should send messages in chat.")
            .defaultValue(true)
            .build()
    );

    // Render

    private final Setting<Boolean> renderSwingSetting = renderSettings.add(new BoolSetting.Builder()
            .name("Swing")
            .description("Swing your hand client side when placing or interacting.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Boolean> renderBlockSetting = renderSettings.add(new BoolSetting.Builder()
            .name("Render block")
            .description("Whether to render the block being broken.")
            .defaultValue(true)
            .build()
    );

    private final Setting<ShapeMode> shapeModeSetting = renderSettings.add(new EnumSetting.Builder<ShapeMode>()
            .name("Shape mode")
            .description("How the shapes are rendered.")
            .defaultValue(ShapeMode.Both)
            .visible(renderBlockSetting::get)
            .build()
    );

    private final Setting<SettingColor> sideColorSetting = renderSettings.add(new ColorSetting.Builder()
            .name("Side color")
            .description("The side color of the rendering.")
            .defaultValue(new SettingColor(225, 0, 0, 75))
            .visible(() -> renderBlockSetting.get() && shapeModeSetting.get().sides())
            .build()
    );

    private final Setting<SettingColor> lineColorSetting = renderSettings.add(new ColorSetting.Builder()
            .name("Line color")
            .description("The line color of the rendering.")
            .defaultValue(new SettingColor(225, 0, 0))
            .visible(() -> renderBlockSetting.get() && shapeModeSetting.get().lines())
            .build()
    );

    public AutoCity(Category category) {
        super(category, "Auto City", "Automatically mine blocks next to someone's feet.");
    }

    @Override
    public void onEnable() {
        target = TargetUtils.getPlayerTarget(targetRangeSetting.get(), SortPriority.Closest_Angle);
        if (TargetUtils.isBadTarget(target, targetRangeSetting.get())) {
            if (chatInfoSetting.get()) {
                error("Couldn't find a target, disabling.");
            }

            forceToggle(false);

            return;
        }

        targetPos = EntityUtils.getCityBlock(target);
        if (targetPos == null || PlayerUtils.distanceTo(targetPos) > breakRangeSetting.get()) {
            if (chatInfoSetting.get()) {
                error("Couldn't find a target, disabling...");
            }

            forceToggle(false);

            return;
        }

        if (supportSetting.get()) {
            BlockPos supportPos = targetPos.down();
            if (!(PlayerUtils.distanceTo(supportPos) > placeRangeSetting.get())) {
                BlockUtils.place(supportPos, InvUtils.findInHotbar(Items.OBSIDIAN), rotateSetting.get(), 0, true);
            }
        }

        pick = InvUtils.find(itemStack -> itemStack.getItem() == Items.DIAMOND_PICKAXE || itemStack.getItem() == Items.NETHERITE_PICKAXE);
        if (!pick.isHotbar()) {
            error("No pickaxe found, disabling...");

            forceToggle(false);

            return;
        }

        progress = 0.0f;

        mine(false);
    }

    @Override
    public void onDisable() {
        target = null;
        targetPos = null;
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (TargetUtils.isBadTarget(target, targetRangeSetting.get())) {
            forceToggle(false);

            return;
        }

        if (PlayerUtils.distanceTo(targetPos) > breakRangeSetting.get()) {
            if (chatInfoSetting.get()) {
                error("Couldn't find a target, disabling...");
            }

            forceToggle(false);

            return;
        }

        if (progress < 1.0f) {
            pick = InvUtils.find(itemStack -> itemStack.getItem() == Items.DIAMOND_PICKAXE || itemStack.getItem() == Items.NETHERITE_PICKAXE);
            if (!pick.isHotbar()) {
                error("No pickaxe found, disabling...");

                forceToggle(false);

                return;
            }

            progress += BlockUtils.getBreakDelta(pick.slot(), mc.world.getBlockState(targetPos));
            if (progress < 1.0f) {
                return;
            }
        }

        mine(true);

        toggle();
    }

    public void mine(boolean done) {
        InvUtils.swap(pick.slot(), switchModeSetting.get() == SwitchMode.Silent);

        if (rotateSetting.get()) {
            Rotations.rotate(Rotations.getYaw(targetPos), Rotations.getPitch(targetPos));
        }

        Direction direction = (mc.player.getY() > targetPos.getY()) ? Direction.UP : Direction.DOWN;
        if (!done) {
            mc.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, targetPos, direction));
        }

        mc.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, targetPos, direction));

        if (renderSwingSetting.get()) {
            mc.player.swingHand(Hand.MAIN_HAND);
        } else {
            mc.getNetworkHandler().sendPacket(new HandSwingC2SPacket(Hand.MAIN_HAND));
        }

        if (switchModeSetting.get() == SwitchMode.Silent) {
            InvUtils.swapBack();
        }
    }

    @EventHandler
    private void onRender3D(Render3DEvent event) {
        if (targetPos == null || !renderBlockSetting.get()) {
            return;
        }

        event.renderer.box(targetPos, sideColorSetting.get(), lineColorSetting.get(), shapeModeSetting.get(), 0);
    }

    @Override
    public String getInfoString() {
        return EntityUtils.getName(target);
    }

    public enum SwitchMode {
        Normal("Normal"),
        Silent("Silent");

        private final String name;

        SwitchMode(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
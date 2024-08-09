package mathax.client.systems.modules.combat;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.entity.player.FinishUsingItemEvent;
import mathax.client.events.render.Render2DEvent;
import mathax.client.events.render.Render3DEvent;
import mathax.client.events.world.TickEvent;
import mathax.client.mixin.ClientPlayerInteractionManagerAccessor;
import mathax.client.renderer.ShapeMode;
import mathax.client.renderer.text.TextRenderer;
import mathax.client.settings.*;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import mathax.client.utils.Jebus.CityUtils;
import mathax.client.utils.Jebus.Task;
import mathax.client.utils.entity.TargetUtils;
import mathax.client.utils.player.FindItemResult;
import mathax.client.utils.player.InvUtils;
import mathax.client.utils.player.Rotations;
import mathax.client.utils.render.NametagUtils;
import mathax.client.utils.render.color.Color;
import mathax.client.utils.render.color.SettingColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import mathax.client.utils.entity.SortPriority;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector3d;

public class AntiSurround extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final SettingGroup sgMisc = settings.createGroup("Misc");
    private final SettingGroup sgRender = settings.createGroup("Render");
    private final SettingGroup sgNone = settings.createGroup("");

    //private final Setting<Notifications.Mode> notifications = sgNone.add(new EnumSetting.Builder<Notifications.Mode>().name("notifications").defaultValue(Notifications.Mode.Toast).build());

    private final Setting<Mode> breakMode = sgGeneral.add(new EnumSetting.Builder<Mode>().name("break-mode").description("The way to break the blocks.").defaultValue(Mode.Client).build());
    private final Setting<Integer> targetRange = sgGeneral.add(new IntSetting.Builder().name("target-range").description("The range players can be targeted.").defaultValue(5).sliderRange(0, 7).build());
    private final Setting<Boolean> rotate = sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Automatically faces towards the blocks being placed.").defaultValue(false).build());
    //private final Setting<Boolean> keepBreaking = sgGeneral.add(new BoolSetting.Builder().name("keep-breaking").defaultValue(false).build());

    private final Setting<Boolean> rightClickEat = sgMisc.add(new BoolSetting.Builder().name("right-click-eat").description("Stops breaking the block and starts eating EGapple.").defaultValue(false).build());
    private final Setting<Boolean> cancelEat = sgMisc.add(new BoolSetting.Builder().name("cancel-eat").description("Press right button again to stop eating.").defaultValue(true).visible(rightClickEat::get).build());
    private final Setting<Boolean> useCrystals   = sgMisc.add(new BoolSetting.Builder().name("use-crystals").description("Places crystal forward to the target city block.").defaultValue(true).build());
    private final Setting<Double> breakProgress = sgMisc.add(new DoubleSetting.Builder().name("break-progress").description("Places crystal if break progress of breaking block is higher.").defaultValue(0.979).sliderRange(0, 1).visible(useCrystals::get).build());
    private final Setting<Boolean> support = sgMisc.add(new BoolSetting.Builder().name("support").description("Places obsidian block under potential crystal position.").defaultValue(false).visible(useCrystals::get).build());
    private final Setting<Boolean> ironPickaxe = sgMisc.add(new BoolSetting.Builder().name("iron-pickaxe").description("Uses iron pickaxe.").defaultValue(false).build());

    private final Setting<Boolean> render = sgRender.add(new BoolSetting.Builder().name("render").description("Renders the block where it is placing a bed.").defaultValue(true).build());
    private final Setting<ShapeMode> shapeMode = sgRender.add(new EnumSetting.Builder<ShapeMode>().name("shape-mode").description("How the shapes are rendered.").defaultValue(ShapeMode.Both).visible(render::get).build());
    private final Setting<SettingColor> sideColor = sgRender.add(new ColorSetting.Builder().name("side-side").description("The side color of the target block rendering.").defaultValue(new SettingColor(144, 250, 255, 10)).visible(() -> render.get() && (shapeMode.get() == ShapeMode.Sides || shapeMode.get() == ShapeMode.Both)).build());
    private final Setting<SettingColor> lineColor = sgRender.add(new ColorSetting.Builder().name("line-line").description("The line color of the target block rendering.").defaultValue(new SettingColor(146, 255, 228)).visible(() -> render.get() && (shapeMode.get() == ShapeMode.Lines || shapeMode.get() == ShapeMode.Both)).build());
    private final Setting<Boolean> renderProgress = sgRender.add(new BoolSetting.Builder().name("render-progress").description("Renders the breaking progress.").defaultValue(true).build());
    private final Setting<Double> scale = sgRender.add(new DoubleSetting.Builder().name("scale").description("Scale of the breaking progress.").defaultValue(1.5).sliderRange(0.01, 3).visible(renderProgress::get).build());

    public AntiSurround() {
        super(Categories.Combat, Items.OBSIDIAN, "anti-surround", "Automatically breaks target's surround.");
    }

    private FindItemResult pickaxe, gap, crystal, obsidian;
    private BlockPos breakPos;
    private PlayerEntity target;
    private boolean isEating;

    private final Task crystalTask = new Task();
    private final Task supportTask = new Task();

    @Override
    public boolean onActivate() {
        crystalTask.reset();
        supportTask.reset();

        breakPos = null;
        isEating = false;
        return false;
    }

    @Override
    public void onDeactivate() {
        if (breakPos != null) mc.interactionManager.attackBlock(breakPos, Direction.UP);
    }

    @EventHandler
    public void onEat(FinishUsingItemEvent event) {
        if (isEating) {
            mc.options.useKey.setPressed(false);
            isEating = false;
        }
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        target = TargetUtils.getPlayerTarget(targetRange.get(), SortPriority.Lowest_Distance);
        if (TargetUtils.isBadTarget(target, targetRange.get())) {
            error("No target found.");
            //Notifications.send("Target is null", notifications);
            toggle();
            return;
        }

        pickaxe = InvUtils.findInHotbar((ironPickaxe.get() ? Items.IRON_PICKAXE : null), Items.NETHERITE_PICKAXE, Items.DIAMOND_PICKAXE);
        if (!pickaxe.found()) {
            //Notifications.send("There's no pickaxe in your hotbar", notifications);
            error("No pickaxe found.");
            toggle();
            return;
        }

        gap = InvUtils.find(Items.ENCHANTED_GOLDEN_APPLE, Items.GOLDEN_APPLE);
        if (rightClickEat.get() && mc.options.useKey.isPressed() && gap.found()) isEating = true;

        if (isEating) {
            if (cancelEat.get() && mc.options.useKey.isPressed()) {
                mc.options.useKey.setPressed(false);
                isEating = false;
            }
            mc.player.getInventory().selectedSlot = gap.slot();
            mc.options.useKey.setPressed(true);
            return;
        }

        if (rotate.get() && breakPos != null) Rotations.rotate(Rotations.getYaw(breakPos), Rotations.getPitch(breakPos));

        if (breakPos == null) breakPos = CityUtils.getBreakPos(target); // find pos once
        if (breakPos == null || mc.world.getBlockState(breakPos).isAir()) { // toggles off if pos is null or air
            if (breakPos == null) error("No break pos found.");
            toggle();
            return;
        }

        switch (breakMode.get()) {
            case Client -> {
                mc.player.getInventory().selectedSlot = pickaxe.slot();
                mc.interactionManager.updateBlockBreakingProgress(breakPos, CityUtils.getDirection(breakPos));

                crystal = InvUtils.findInHotbar(Items.END_CRYSTAL);
                obsidian = InvUtils.findInHotbar(Items.OBSIDIAN);
                if (useCrystals.get() && CityUtils.getCrystalPos(breakPos, support.get()) != null && crystal.found()) {
                    float progress = ((ClientPlayerInteractionManagerAccessor) mc.interactionManager).getBreakingProgress();
                    if (progress < breakProgress.get()) return;

                    if (support.get() && obsidian.found()) {
                        supportTask.run(() -> {
                            int prevSlot = mc.player.getInventory().selectedSlot;
                            if (mc.player.getOffHandStack().getItem() != Items.OBSIDIAN)
                                mc.player.getInventory().selectedSlot = obsidian.slot();
                            mc.interactionManager.interactBlock(mc.player, obsidian.getHand(), new BlockHitResult(mc.player.getPos(), Direction.DOWN, CityUtils.getCrystalPos(breakPos, support.get()), true));
                            mc.player.getInventory().selectedSlot = prevSlot;
                        });
                    }

                    crystalTask.run(() -> {
                        int prevSlot = mc.player.getInventory().selectedSlot;
                        if (mc.player.getOffHandStack().getItem() != Items.END_CRYSTAL)
                            mc.player.getInventory().selectedSlot = crystal.slot();
                        mc.interactionManager.interactBlock(mc.player, crystal.getHand(), new BlockHitResult(mc.player.getPos(), Direction.DOWN, CityUtils.getCrystalPos(breakPos, false), true));
                        mc.player.getInventory().selectedSlot = prevSlot;
                    });
                }
            }
//            case Packet -> {
//
//            }
        }
    }

    @EventHandler
    public void onRender(Render3DEvent event) {
        if (breakPos == null) return;

        event.renderer.box(breakPos, sideColor.get(), lineColor.get(), shapeMode.get(), 0);
    }

    @EventHandler
    public void on2DRender(Render2DEvent event) {
        if (breakPos == null || !renderProgress.get()) return;

        Vector3d pos = new Vector3d(breakPos.getX() + 0.5, breakPos.getY() + 0.5, breakPos.getZ() + 0.5);
        if (NametagUtils.to2D(pos, scale.get())) {
            String progress;
            NametagUtils.begin(pos);
            TextRenderer.get().begin(1.0, false, true);
            progress = String.format("%.2f", ((ClientPlayerInteractionManagerAccessor) mc.interactionManager).getBreakingProgress()) + "%";
            if (progress.equals("0.00%")) progress = "";
            TextRenderer.get().render(progress, -TextRenderer.get().getWidth(progress) / 2.0, 0.0, (crystalTask.isCalled() ? new Color(106, 255, 78, 255) : new Color(255, 255, 255, 255)));
            TextRenderer.get().end();
            NametagUtils.end();
        }
    }

    public enum Mode {
        Client
    }

    @Override
    public String getInfoString() {
        return target != null ? target.getGameProfile().getName() : null; // adds target name to the module array list
    }
}

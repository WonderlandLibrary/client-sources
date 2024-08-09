package mathax.client.systems.modules.combat;

import mathax.client.MatHax;
import mathax.client.eventbus.EventHandler;
import mathax.client.events.render.Render3DEvent;
import mathax.client.events.world.TickEvent;
import mathax.client.renderer.ShapeMode;
import mathax.client.settings.*;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import mathax.client.systems.modules.Modules;
import mathax.client.systems.modules.world.AntiGhostBlock;
import mathax.client.utils.Utils;
import mathax.client.utils.entity.EntityUtils;
import mathax.client.utils.entity.SortPriority;
import mathax.client.utils.entity.TargetUtils;
import mathax.client.utils.player.*;
import mathax.client.utils.render.color.SettingColor;
import mathax.client.utils.world.BlockUtils;
import mathax.client.utils.world.CardinalDirection;
import net.minecraft.block.BedBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BedBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BedItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

/*/-----------------------------------------------------------------------------------------------------------/*/
/*/ Remastered using Orion Meteor Addon                                                                       /*/
/*/ https://github.com/AntiCope/orion/blob/master/src/main/java/me/ghosttypes/orion/modules/main/BedAura.java /*/
/*/-----------------------------------------------------------------------------------------------------------/*/

public class BedAura extends Module {
    private CardinalDirection direction;

    private PlayerEntity target;

    private BlockPos placePos, breakPos, stb;

    private Item ogItem;

    private boolean sentTrapMine, sentBurrowMine, safetyToggled;

    private int timer, webTimer;

    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final SettingGroup sgTargeting = settings.createGroup("Targeting");
    private final SettingGroup sgAutoMove = settings.createGroup("Inventory");
    private final SettingGroup sgAutomation = settings.createGroup("Automation");
    private final SettingGroup sgSafety = settings.createGroup("Safety");
    private final SettingGroup sgPause = settings.createGroup("Pause");
    private final SettingGroup sgRender = settings.createGroup("Render");

    // General

    private final Setting<Integer> delay = sgGeneral.add(new IntSetting.Builder()
        .name("delay")
        .description("The delay between placing beds in ticks.")
        .defaultValue(9)
        .min(0)
        .sliderRange(0, 40)
        .build()
    );

    private final Setting<Boolean> strictDirection = sgGeneral.add(new BoolSetting.Builder()
        .name("strict-direction")
        .description("Only places beds in the direction you are facing.")
        .defaultValue(false)
        .build()
    );

    private final Setting<BreakHand> breakHand = sgGeneral.add(new EnumSetting.Builder<BreakHand>()
        .name("break-hand")
        .description("Which hand to break beds with.")
        .defaultValue(BreakHand.Offhand)
        .build()
    );

    // Targeting

    public final Setting<Double> targetRange = sgTargeting.add(new DoubleSetting.Builder()
        .name("target-range")
        .description("The range at which players can be targeted.")
        .defaultValue(4)
        .min(0)
        .sliderRange(0, 5)
        .build()
    );

    private final Setting<Boolean> targetFeet = sgTargeting.add(new BoolSetting.Builder()
        .name("target-feet")
        .description("Targets player feet.")
        .defaultValue(false)
        .build()
    );

    private final Setting<SortPriority> priority = sgTargeting.add(new EnumSetting.Builder<SortPriority>()
        .name("target-priority")
        .description("How to filter the players to target.")
        .defaultValue(SortPriority.Lowest_Health)
        .build()
    );

    private final Setting<Double> minDamage = sgTargeting.add(new DoubleSetting.Builder()
        .name("min-damage")
        .description("The minimum damage to inflict on your target.")
        .defaultValue(7)
        .range(0, 36)
        .sliderRange(0, 36)
        .build()
    );

    private final Setting<Double> maxSelfDamage = sgTargeting.add(new DoubleSetting.Builder()
        .name("max-self-damage")
        .description("The maximum damage to inflict on yourself.")
        .defaultValue(7)
        .range(0, 36)
        .sliderRange(0, 36)
        .build()
    );

    private final Setting<Boolean> antiSuicide = sgTargeting.add(new BoolSetting.Builder()
        .name("anti-suicide")
        .description("Will not place and break beds if they will kill you.")
        .defaultValue(true)
        .build()
    );

    // Inventory

    private final Setting<Boolean> autoMove = sgAutoMove.add(new BoolSetting.Builder()
        .name("auto-move")
        .description("Moves beds into a selected hotbar slot.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Integer> autoMoveSlot = sgAutoMove.add(new IntSetting.Builder()
        .name("auto-move-slot")
        .description("The slot auto move moves beds to.")
        .defaultValue(9)
        .range(0, 9)
        .sliderRange(0, 9)
        .visible(autoMove::get)
        .build()
    );

    private final Setting<Boolean> autoSwitch = sgAutoMove.add(new BoolSetting.Builder()
        .name("auto-switch")
        .description("Switches to and from beds automatically.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> restoreOnDisable = sgAutoMove.add(new BoolSetting.Builder()
        .name("restore-on-disable")
        .description("Put whatever was in your auto move slot back after disabling.")
        .defaultValue(true)
        .build()
    );

    // Automation

    private final Setting<Boolean> breakSelfTrap = sgAutomation.add(new BoolSetting.Builder()
        .name("break-self-trap")
        .description("Break target's self-trap automatically.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> breakBurrow = sgAutomation.add(new BoolSetting.Builder()
        .name("break-burrow")
        .description("Break target's burrow automatically.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> breakWeb = sgAutomation.add(new BoolSetting.Builder()
        .name("break-web")
        .description("Break target's webs/string automatically.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> preventEscape = sgAutomation.add(new BoolSetting.Builder()
        .name("prevent-escape")
        .description("Place a block over the target's head before bedding.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> renderAutomation = sgAutomation.add(new BoolSetting.Builder()
        .name("render-break")
        .description("Render mining self-trap/burrow.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> disableOnNoBeds = sgAutomation.add(new BoolSetting.Builder()
        .name("no-beds-disable")
        .description("Disable if you run out of beds.")
        .defaultValue(false)
        .build()
    );

    // Safety

    private final Setting<Boolean> disableOnSafety = sgSafety.add(new BoolSetting.Builder()
        .name("safety-disable")
        .description("Disable bed aura when safety activates.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Double> safetyHP = sgSafety.add(new DoubleSetting.Builder()
        .name("safety-hp")
        .description("What health safety activates at.").defaultValue(10)
        .range(0, 36)
        .sliderRange(0, 36)
        .build()
    );

    private final Setting<Boolean> safetyGapSwap = sgSafety.add(new BoolSetting.Builder()
        .name("swap-to-gap")
        .description("Swap to E-Gaps after activating safety.")
        .defaultValue(false)
        .build()
    );

    // Pause

    private final Setting<Boolean> pauseOnEat = sgPause.add(new BoolSetting.Builder()
        .name("pause-on-eat")
        .description("Pauses while eating.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> pauseOnDrink = sgPause.add(new BoolSetting.Builder()
        .name("pause-on-drink")
        .description("Pauses while drinking.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> pauseOnMine = sgPause.add(new BoolSetting.Builder()
        .name("pause-on-mine")
        .description("Pauses while mining.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> pauseOnCrystalAura = sgPause.add(new BoolSetting.Builder()
        .name("pause-on-crystal-aura")
        .description("Pause while Crystal Aura is active.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> pauseOnCraft = sgPause.add(new BoolSetting.Builder()
        .name("pause-on-crafting")
        .description("Pauses while you're in a crafting table.")
        .defaultValue(false)
        .build()
    );

    // Render

    private final Setting<Boolean> swing = sgRender.add(new BoolSetting.Builder()
        .name("swing")
        .description("Swings your hand client side when placing or interacting.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> render = sgRender.add(new BoolSetting.Builder()
        .name("render")
        .description("Renders the block where it is placing a bed.")
        .defaultValue(true)
        .build()
    );

    private final Setting<ShapeMode> shapeMode = sgRender.add(new EnumSetting.Builder<ShapeMode>()
        .name("shape-mode")
        .description("Determines how the shapes are rendered.")
        .defaultValue(ShapeMode.Both)
        .build()
    );

    private final Setting<SettingColor> sideColor = sgRender.add(new ColorSetting.Builder()
        .name("side-color")
        .description("The side color for positions to be placed.")
        .defaultValue(new SettingColor(MatHax.INSTANCE.MATHAX_COLOR.r, MatHax.INSTANCE.MATHAX_COLOR.g, MatHax.INSTANCE.MATHAX_COLOR.b,75))
        .build()
    );

    private final Setting<SettingColor> lineColor = sgRender.add(new ColorSetting.Builder()
        .name("line-color")
        .description("The line color for positions to be placed.")
        .defaultValue(new SettingColor(MatHax.INSTANCE.MATHAX_COLOR.r, MatHax.INSTANCE.MATHAX_COLOR.g, MatHax.INSTANCE.MATHAX_COLOR.b))
        .build()
    );

    public BedAura() {
        super(Categories.Combat, Items.RED_BED, "bed-aura", "Automatically places and explodes beds in the Nether and the End.");
    }

    @Override
    public boolean onActivate() {
        target = null;
        ogItem = InvUtils.getItemFromSlot(autoMoveSlot.get() - 1);
        if (ogItem instanceof BedItem) ogItem = null;
        safetyToggled = false;
        sentTrapMine = false;
        sentBurrowMine = false;
        timer = 0;
        webTimer = 0;
        direction = CardinalDirection.North;
        stb = null;
        return false;
    }

    @Override
    public void onDeactivate() {
        if (safetyToggled) {
            warning("Your health is too low, disabling...");
            if (safetyGapSwap.get()) {
                FindItemResult gap = InvUtils.findEgap();
                if (gap.found()) mc.player.getInventory().selectedSlot = gap.slot();
            }
        }

        if (!safetyToggled && restoreOnDisable.get() && ogItem != null) {
            FindItemResult ogItemInv = InvUtils.find(ogItem);
            if (ogItemInv.found()) InvUtils.move().from(ogItemInv.slot()).toHotbar(autoMoveSlot.get() - 1);
        }
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        CrystalAura crystalAura = Modules.get().get(CrystalAura.class);

        if (PlayerUtils.getTotalHealth() <= safetyHP.get()) {
            if (disableOnSafety.get()) {
                safetyToggled = true;
                toggle();
            }

            return;
        }

        if (mc.world.getDimension().bedWorks()) {
            error("You can't blow up beds in this dimension, disabling...");
            toggle();
            return;
        }

        if (PlayerUtils.shouldPause(pauseOnMine.get(), pauseOnEat.get(), pauseOnDrink.get())) return;
        if (pauseOnCraft.get() && mc.player.currentScreenHandler instanceof CraftingScreenHandler) return;
        if (pauseOnCrystalAura.get() && crystalAura.isActive()) return;

        target = TargetUtils.getPlayerTarget(targetRange.get(), priority.get());
        if (TargetUtils.isBadTarget(target, targetRange.get())) target = null;
        if (target == null) {
            timer = delay.get();
            placePos = null;
            breakPos = null;
            stb = null;
            sentTrapMine = false;
            sentBurrowMine = false;
            return;
        }

        if (placePos != null) resetPlacePosIfCantPlace();

        if (autoMove.get()) {
            FindItemResult bed = InvUtils.find(itemStack -> itemStack.getItem() instanceof BedItem);
            if (bed.found() && bed.slot() != autoMoveSlot.get() - 1) InvUtils.move().from(bed.slot()).toHotbar(autoMoveSlot.get() - 1);
            if (!bed.found() && disableOnNoBeds.get()) {
                warning("You've ran out of beds, disabling...");
                toggle();
                return;
            }
        }

        if (preventEscape.get() && BlockUtils.getBlock(target.getBlockPos().up(2)) != Blocks.OBSIDIAN && PlayerUtils.isInHole(target)) {
            FindItemResult obsidian = InvUtils.findObsidian();
            if (obsidian.found()) BlockUtils.place(target.getBlockPos().up(2), obsidian, true, 50, true, true, true);
            if (BlockUtils.getBlock(target.getBlockPos().up(2)) != Blocks.OBSIDIAN) return;
        }

        if (breakPos == null) placePos = findPlace(target);

        // Automation

        if (breakSelfTrap.get() && shouldTrapMine()) {
            FindItemResult pick = InvUtils.findPick();
            if (pick.found()) {
                InvUtils.updateSlot(pick.slot());
                stb = PlayerUtils.getSelfTrapBlock(target, preventEscape.get());
                PlayerUtils.doPacketMine(stb);
                sentTrapMine = true;
                return;
            }
        }

        if (placePos == null && PlayerUtils.isBurrowed(target, false) && breakBurrow.get() && !sentBurrowMine) {
            FindItemResult pick = InvUtils.findPick();
            if (pick.found()) {
                InvUtils.updateSlot(pick.slot());
                PlayerUtils.doPacketMine(target.getBlockPos());
                sentBurrowMine = true;
                return;
            }
        }

        if (placePos == null && PlayerUtils.isWebbed(target) && breakWeb.get()) {
            FindItemResult sword = InvUtils.findSword();
            if (sword.found()) {
                InvUtils.updateSlot(sword.slot());
                if (webTimer <= 0) webTimer = 100;
                else webTimer--;
                PlayerUtils.mineWeb(target, sword.slot());
                return;
            }
        }

        if (sentTrapMine && didTrapMine()) {
            sentTrapMine = false;
            stb = null;
        }

        if (sentBurrowMine && !PlayerUtils.isBurrowed(target, false)) sentBurrowMine = false;

        if (timer <= 0 && placeBed(placePos)) timer = delay.get();
        else timer--;

        if (breakPos == null) breakPos = findBreak();
        breakBed(breakPos);
    }

    private BlockPos findPlace(PlayerEntity target) {
        if (!InvUtils.find(itemStack -> itemStack.getItem() instanceof BedItem).found()) return null;

        for (int index = 0; index < 3; index++) {
            int i = index == 0 ? 1 : index == 1 ? 0 : 2;

            for (CardinalDirection dir : CardinalDirection.values()) {
                if (strictDirection.get() && dir.toDirection() != mc.player.getHorizontalFacing() && dir.toDirection().getOpposite() != mc.player.getHorizontalFacing()) continue;

                BlockPos centerPos;
                if (targetFeet.get()) centerPos = target.getBlockPos();
                else centerPos = target.getBlockPos().up(i);
                BlockPos underFeetPos = target.getBlockPos().down(1);

                double selfDamage = DamageUtils.bedDamage(mc.player, Utils.vec3d(centerPos));
                double offsetSelfDamage = DamageUtils.bedDamage(mc.player, Utils.vec3d(centerPos.offset(dir.toDirection())));

                if (targetFeet.get() && !mc.world.getBlockState(underFeetPos).getMaterial().equals(Material.AIR) && !mc.world.getBlockState(underFeetPos.offset((direction = dir).toDirection())).getMaterial().equals(Material.AIR) && mc.world.getBlockState(centerPos).getMaterial().isReplaceable() && BlockUtils.canPlace(centerPos.offset(dir.toDirection())) && DamageUtils.bedDamage(target, Utils.vec3d(centerPos)) >= minDamage.get() && offsetSelfDamage < maxSelfDamage.get() && selfDamage < maxSelfDamage.get() && (!antiSuicide.get() || PlayerUtils.getTotalHealth() - selfDamage > 0) && (!antiSuicide.get() || PlayerUtils.getTotalHealth() - offsetSelfDamage > 0)) return centerPos.offset((direction = dir).toDirection());
                if (!targetFeet.get() && mc.world.getBlockState(centerPos).getMaterial().isReplaceable() && BlockUtils.canPlace(centerPos.offset(dir.toDirection())) && DamageUtils.bedDamage(target, Utils.vec3d(centerPos)) >= minDamage.get() && offsetSelfDamage < maxSelfDamage.get() && selfDamage < maxSelfDamage.get() && (!antiSuicide.get() || PlayerUtils.getTotalHealth() - selfDamage > 0) && (!antiSuicide.get() || PlayerUtils.getTotalHealth() - offsetSelfDamage > 0)) return centerPos.offset((direction = dir).toDirection());
            }
        }

        return null;
    }

    private BlockPos findBreak() {
        for (BlockEntity blockEntity : Utils.blockEntities()) {
            if (!(blockEntity instanceof BedBlockEntity)) continue;
            BlockPos bedPos = blockEntity.getPos();
            Vec3d bedVec = Utils.vec3d(bedPos);

            if (Modules.get().isActive(AntiGhostBlock.class) && PlayerUtils.distanceTo(bedPos) <= mc.interactionManager.getReachDistance()) return bedPos;
            if (PlayerUtils.distanceTo(bedVec) <= mc.interactionManager.getReachDistance() && DamageUtils.bedDamage(target, bedVec) >= minDamage.get() && DamageUtils.bedDamage(mc.player, bedVec) < maxSelfDamage.get() && (!antiSuicide.get() || PlayerUtils.getTotalHealth() - DamageUtils.bedDamage(mc.player, bedVec) > 0)) return bedPos;
        }

        return null;
    }

    private boolean placeBed(BlockPos pos) {
        if (pos == null) return false;
        FindItemResult bed = InvUtils.findInHotbar(itemStack -> itemStack.getItem() instanceof BedItem);
        if (bed.getHand() == null && !autoSwitch.get()) return false;

        double yaw = switch (direction) {
            case East -> 90;
            case South -> 180;
            case West -> -90;
            default -> 0;
        };

        Rotations.rotate(yaw, Rotations.getPitch(pos), () -> {
            BlockUtils.place(pos, bed, false, 0, swing.get(), true);
            breakPos = pos;
        });

        return true;
    }

    private void breakBed(BlockPos pos) {
        if (pos == null) return;
        breakPos = null;
        if (!(mc.world.getBlockState(pos).getBlock() instanceof BedBlock)) return;

        boolean wasSneaking = mc.player.isSneaking();
        if (wasSneaking) mc.player.setSneaking(false);

        Hand hand;
        if (breakHand.get() == BreakHand.Mainhand) hand = Hand.MAIN_HAND;
        else hand = Hand.OFF_HAND;

        mc.interactionManager.interactBlock(mc.player, hand, new BlockHitResult(mc.player.getPos(), Direction.UP, pos, false));
        mc.player.setSneaking(wasSneaking);
    }

    @EventHandler
    private void onRender3D(Render3DEvent event) {
        if (render.get() && placePos != null && breakPos == null) {
            int x = placePos.getX();
            int y = placePos.getY();
            int z = placePos.getZ();

            switch (direction) {
                case North -> event.renderer.box(x, y, z, x + 1, y + 0.6, z + 2, sideColor.get(), lineColor.get(), shapeMode.get(), 0);
                case South -> event.renderer.box(x, y, z - 1, x + 1, y + 0.6, z + 1, sideColor.get(), lineColor.get(), shapeMode.get(), 0);
                case East -> event.renderer.box(x - 1, y, z, x + 1, y + 0.6, z + 1, sideColor.get(), lineColor.get(), shapeMode.get(), 0);
                case West -> event.renderer.box(x, y, z, x + 2, y + 0.6, z + 1, sideColor.get(), lineColor.get(), shapeMode.get(), 0);
            }
        }

        if (renderAutomation.get() && target != null) {
            if (stb != null) event.renderer.box(stb, sideColor.get(), lineColor.get(), shapeMode.get(), 0);
            if (sentBurrowMine) event.renderer.box(target.getBlockPos(), sideColor.get(), lineColor.get(), shapeMode.get(), 0);
            if (BlockUtils.isWeb(target.getBlockPos())) event.renderer.box(target.getBlockPos(), sideColor.get(), lineColor.get(), shapeMode.get(), 0);
            if (BlockUtils.isWeb(target.getBlockPos().up())) event.renderer.box(target.getBlockPos().up(), sideColor.get(), lineColor.get(), shapeMode.get(), 0);
        }
    }

    private void resetPlacePosIfCantPlace() {
        if (mc.world.getBlockState(placePos).getBlock() == Blocks.OBSIDIAN || mc.world.getBlockState(placePos).getBlock() == Blocks.BEDROCK) placePos = null;
    }

    private boolean shouldTrapMine() {
        return !sentTrapMine && placePos == null && PlayerUtils.getSelfTrapBlock(target, preventEscape.get()) != null;
    }

    private boolean didTrapMine() {
        if (PlayerUtils.getSelfTrapBlock(target, preventEscape.get()) == null) return true;
        return BlockUtils.getBlock(stb) == Blocks.AIR || !BlockUtils.isTrapBlock(stb);
    }

    @Override
    public String getInfoString() {
        return EntityUtils.getName(target);
    }

    public enum BreakHand {
        Mainhand("Mainhand"),
        Offhand("Offhand");

        private final String title;

        BreakHand(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return title;
        }
    }
}

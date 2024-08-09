package xyz.mathax.mathaxclient.systems.modules.combat;

import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.packets.PacketEvent;
import xyz.mathax.mathaxclient.events.render.Render3DEvent;
import xyz.mathax.mathaxclient.events.world.TickEvent;
import xyz.mathax.mathaxclient.mixin.WorldRendererAccessor;
import xyz.mathax.mathaxclient.renderer.ShapeMode;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.utils.render.color.Color;
import xyz.mathax.mathaxclient.utils.render.color.SettingColor;
import xyz.mathax.mathaxclient.utils.world.BlockUtils;
import xyz.mathax.mathaxclient.utils.world.CardinalDirection;
import xyz.mathax.mathaxclient.utils.world.MatHaxDirection;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.BlockBreakingInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.s2c.play.DeathMessageS2CPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import xyz.mathax.mathaxclient.settings.*;
import xyz.mathax.mathaxclient.utils.player.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Surround extends Module {
    private final BlockPos.Mutable placePos = new BlockPos.Mutable();
    private final BlockPos.Mutable renderPos = new BlockPos.Mutable();
    private final BlockPos.Mutable testPos = new BlockPos.Mutable();

    public ArrayList<Module> toEnable = new ArrayList<>();

    private int ticks;

    private final SettingGroup sgGeneral = settings.createGroup("General");
    private final SettingGroup sgToggles = settings.createGroup("Toggles");
    private final SettingGroup sgRender = settings.createGroup("Render");

    // General

    private final Setting<List<Block>> blocksSetting = sgGeneral.add(new BlockListSetting.Builder()
            .name("Blocks")
            .description("What blocks to use for surround.")
            .defaultValue(Blocks.OBSIDIAN, Blocks.CRYING_OBSIDIAN, Blocks.NETHERITE_BLOCK)
            .filter(this::blockFilter)
            .build()
    );

    private final Setting<Integer> delaySetting = sgGeneral.add(new IntSetting.Builder()
            .name("Delay")
            .description("Delay between block placements in ticks.")
            .defaultValue(0)
            .min(0)
            .sliderRange(0, 20)
            .build()
    );

    private final Setting<Center> centerSetting = sgGeneral.add(new EnumSetting.Builder<Center>()
            .name("Center")
            .description("Teleport you to the center of the block.")
            .defaultValue(Center.Incomplete)
            .build()
    );

    private final Setting<Boolean> doubleHeightSetting = sgGeneral.add(new BoolSetting.Builder()
            .name("Double height")
            .description("Place obsidian on top of the original surround blocks to prevent people from face-placing you.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Boolean> onlyOnGroundSetting = sgGeneral.add(new BoolSetting.Builder()
            .name("Only on ground")
            .description("Work only when you are standing on blocks.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Boolean> toggleModulesSetting = sgGeneral.add(new BoolSetting.Builder()
            .name("Toggle modules")
            .description("Turn off other modules when surround is activated.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Boolean> toggleBackSetting = sgGeneral.add(new BoolSetting.Builder()
            .name("Toggle back on")
            .description("Turn the other modules back on when surround is deactivated.")
            .defaultValue(false)
            .visible(toggleModulesSetting::get)
            .build()
    );

    private final Setting<List<Module>> modulesSetting = sgGeneral.add(new ModuleListSetting.Builder()
            .name("Modules")
            .description("Which modules to disable on activation.")
            .visible(toggleModulesSetting::get)
            .build()
    );

    private final Setting<Boolean> rotateSetting = sgGeneral.add(new BoolSetting.Builder()
            .name("Rotate")
            .description("Automatically faces towards the obsidian being placed.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Boolean> protectSetting = sgGeneral.add(new BoolSetting.Builder()
            .name("Protect")
            .description("Attempt to break crystals around surround positions to prevent surround break.")
            .defaultValue(true)
            .build()
    );

    // Toggles

    private final Setting<Boolean> toggleOnYChangeSetting = sgToggles.add(new BoolSetting.Builder()
            .name("Toggle on y change")
            .description("Automatically disable when your y level changes (step, jumping, etc).")
            .defaultValue(true)
            .build()
    );

    private final Setting<Boolean> toggleOnCompleteSetting = sgToggles.add(new BoolSetting.Builder()
            .name("Toggle on complete")
            .description("Toggle off when all blocks are placed.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Boolean> toggleOnDeathSetting = sgToggles.add(new BoolSetting.Builder()
            .name("Toggle on death")
            .description("Toggle off when you die.")
            .defaultValue(true)
            .build()
    );

    // Render

    private final Setting<Boolean> swingSetting = sgRender.add(new BoolSetting.Builder()
            .name("Swing")
            .description("Swing hand client side.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Boolean> renderSetting = sgRender.add(new BoolSetting.Builder()
            .name("Enabled")
            .description("Render a block overlay where the obsidian will be placed.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Boolean> renderBelowSetting = sgRender.add(new BoolSetting.Builder()
            .name("Below")
            .description("Render the block below you.")
            .defaultValue(false)
            .build()
    );

    private final Setting<ShapeMode> shapeModeSetting = sgRender.add(new EnumSetting.Builder<ShapeMode>()
            .name("Shape mode")
            .description("How the shapes are rendered.")
            .defaultValue(ShapeMode.Both)
            .build()
    );

    private final Setting<SettingColor> safeSideColorSetting = sgRender.add(new ColorSetting.Builder()
            .name("Safe side color")
            .description("The side color for safe blocks.")
            .defaultValue(new SettingColor(15, 255, 0, 0))
            .visible(() -> renderSetting.get() && shapeModeSetting.get() != ShapeMode.Lines)
            .build()
    );

    private final Setting<SettingColor> safeLineColorSetting = sgRender.add(new ColorSetting.Builder()
            .name("Safe line color")
            .description("The line color for safe blocks.")
            .defaultValue(new SettingColor(15, 255, 0, 0))
            .visible(() -> renderSetting.get() && shapeModeSetting.get() != ShapeMode.Sides)
            .build()
    );

    private final Setting<SettingColor> normalSideColorSetting = sgRender.add(new ColorSetting.Builder()
            .name("Normal side color")
            .description("The side color for normal blocks.")
            .defaultValue(new SettingColor(0, 255, 238, 15))
            .visible(() -> renderSetting.get() && shapeModeSetting.get() != ShapeMode.Lines)
            .build()
    );

    private final Setting<SettingColor> normalLineColorSetting = sgRender.add(new ColorSetting.Builder()
            .name("Normal line color")
            .description("The line color for normal blocks.")
            .defaultValue(new SettingColor(0, 255, 235, 100))
            .visible(() -> renderSetting.get() && shapeModeSetting.get() != ShapeMode.Sides)
            .build()
    );

    private final Setting<SettingColor> unsafeSideColorSetting = sgRender.add(new ColorSetting.Builder()
            .name("Unsafe side color")
            .description("The side color for unsafe blocks.")
            .defaultValue(new SettingColor(205, 0, 0, 15))
            .visible(() -> renderSetting.get() && shapeModeSetting.get() != ShapeMode.Lines)
            .build()
    );

    private final Setting<SettingColor> unsafeLineColorSetting = sgRender.add(new ColorSetting.Builder()
            .name("Unsafe line color")
            .description("The line color for unsafe blocks.")
            .defaultValue(new SettingColor(205, 0, 0, 100))
            .visible(() -> renderSetting.get() && shapeModeSetting.get() != ShapeMode.Sides)
            .build()
    );

    public Surround(Category category) {
        super(category, "Surround", "Surrounds you in blocks to prevent massive crystal damage.");
    }

    // Render

    @EventHandler
    private void onRender3D(Render3DEvent event) {
        if (!renderSetting.get()) {
            return;
        }

        if (renderBelowSetting.get()) {
            draw(event, null, -1, 0);
        }

        for (CardinalDirection direction : CardinalDirection.values()) {
            draw(event, direction, 0, doubleHeightSetting.get() ? MatHaxDirection.UP : 0);
        }

        if (doubleHeightSetting.get()) {
            for (CardinalDirection direction : CardinalDirection.values()) {
                draw(event, direction, 1, MatHaxDirection.DOWN);
            }
        }
    }

    private void draw(Render3DEvent event, CardinalDirection direction, int y, int exclude) {
        renderPos.set(offsetPosFromPlayer(direction, y));
        Color sideColor = getSideColor(renderPos);
        Color lineColor = getLineColor(renderPos);
        event.renderer.box(renderPos, sideColor, lineColor, shapeModeSetting.get(), exclude);
    }

    // Function

    @Override
    public void onEnable() {
        if (centerSetting.get() == Center.On_Enable) {
            PlayerUtils.centerPlayer();
        }

        ticks = 0;
        if (toggleModulesSetting.get() && !modulesSetting.get().isEmpty() && mc.world != null && mc.player != null) {
            for (Module module : modulesSetting.get()) {
                if (module.isEnabled()) {
                    module.toggle();
                    toEnable.add(module);
                }
            }
        }
    }

    @Override
    public void onDisable() {
        if (toggleBackSetting.get() && !toEnable.isEmpty() && mc.world != null && mc.player != null) {
            for (Module module : toEnable) {
                if (!module.isEnabled()) {
                    module.toggle();
                }
            }
        }
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (ticks > 0) {
            ticks--;
            return;
        } else {
            ticks = delaySetting.get();
        }

        if (toggleOnYChangeSetting.get() && mc.player.prevY != mc.player.getY()) {
            toggle();
            return;
        }

        if (onlyOnGroundSetting.get() && !mc.player.isOnGround()) {
            return;
        }

        if (!getInvBlock().found()) {
            return;
        }

        if (centerSetting.get() == Center.Always) {
            PlayerUtils.centerPlayer();
        }

        int safe = 0;
        for (CardinalDirection direction : CardinalDirection.values()) {
            if (place(direction, 0)) {
                break;
            }

            safe++;
        }

        if (doubleHeightSetting.get() && safe == 4) {
            for (CardinalDirection direction : CardinalDirection.values()) {
                if (place(direction, 1)) {
                    break;
                }

                safe++;
            }
        }

        boolean complete = safe == (doubleHeightSetting.get() ? 8 : 4);
        if (complete && toggleOnCompleteSetting.get()) {
            forceToggle(false);
            return;
        }

        if (!complete && centerSetting.get() == Center.Incomplete) {
            PlayerUtils.centerPlayer();
        }
    }

    private boolean place(CardinalDirection direction, int y) {
        placePos.set(offsetPosFromPlayer(direction, y));

        boolean placed = BlockUtils.place(placePos, getInvBlock(), rotateSetting.get(), 100, swingSetting.get(), true);

        boolean beingMined = false;
        for (BlockBreakingInfo value : ((WorldRendererAccessor) mc.worldRenderer).getBlockBreakingInfos().values()) {
            if (value.getPos().equals(placePos)) {
                beingMined = true;
                break;
            }
        }

        boolean isThreat = mc.world.getBlockState(placePos).getMaterial().isReplaceable() || beingMined;
        if (protectSetting.get() && !placed && isThreat) {
            Box box = new Box(placePos.getX() - 1, placePos.getY() - 1, placePos.getZ() - 1, placePos.getX() + 1, placePos.getY() + 1, placePos.getZ() + 1);
            Predicate<Entity> entityPredicate = entity -> entity instanceof EndCrystalEntity && DamageUtils.crystalDamage(mc.player, entity.getPos()) < PlayerUtils.getTotalHealth();
            for (Entity crystal : mc.world.getOtherEntities(null, box, entityPredicate)) {
                if (rotateSetting.get()) {
                    Rotations.rotate(Rotations.getPitch(crystal), Rotations.getYaw(crystal), () -> {
                        mc.player.networkHandler.sendPacket(PlayerInteractEntityC2SPacket.attack(crystal, mc.player.isSneaking()));
                    });
                } else {
                    mc.player.networkHandler.sendPacket(PlayerInteractEntityC2SPacket.attack(crystal, mc.player.isSneaking()));
                }

                mc.getNetworkHandler().sendPacket(new HandSwingC2SPacket(Hand.MAIN_HAND));
            }
        }

        return placed;
    }

    @EventHandler
    private void onPacketReceive(PacketEvent.Receive event)  {
        if (event.packet instanceof DeathMessageS2CPacket packet) {
            Entity entity = mc.world.getEntityById(packet.getEntityId());
            if (entity == mc.player && toggleOnDeathSetting.get()) {
                forceToggle(false);
                info("You died, disabling...");
            }
        }
    }

    private BlockPos.Mutable offsetPosFromPlayer(CardinalDirection direction, int y) {
        return offsetPos(mc.player.getBlockPos(), direction, y);
    }

    private BlockPos.Mutable offsetPos(BlockPos origin, CardinalDirection direction, int y) {
        if (direction == null) {
            return testPos.set(origin.getX(), origin.getY() + y, origin.getZ());
        }

        return testPos.set(origin.getX() + direction.toDirection().getOffsetX(), origin.getY() + y, origin.getZ() + direction.toDirection().getOffsetZ());
    }

    private BlockType getBlockType(BlockPos pos) {
        BlockState blockState = mc.world.getBlockState(pos);
        if (blockState.getBlock().getHardness() < 0) {
            return BlockType.Safe;
        } else {
            if (blockState.getBlock().getBlastResistance() >= 600) {
                return BlockType.Normal;
            } else {
                return BlockType.Unsafe;
            }
        }
    }

    private Color getSideColor(BlockPos pos) {
        return switch (getBlockType(pos)) {
            case Safe -> safeSideColorSetting.get();
            case Normal -> normalSideColorSetting.get();
            case Unsafe -> unsafeSideColorSetting.get();
        };
    }

    private Color getLineColor(BlockPos pos) {
        return switch (getBlockType(pos)) {
            case Safe -> safeLineColorSetting.get();
            case Normal -> normalLineColorSetting.get();
            case Unsafe -> unsafeLineColorSetting.get();
        };
    }

    private FindItemResult getInvBlock() {
        return InvUtils.findInHotbar(itemStack -> blocksSetting.get().contains(Block.getBlockFromItem(itemStack.getItem())));
    }

    private boolean blockFilter(Block block) {
        return block == Blocks.OBSIDIAN || block == Blocks.CRYING_OBSIDIAN || block == Blocks.NETHERITE_BLOCK || block == Blocks.ENDER_CHEST || block == Blocks.RESPAWN_ANCHOR;
    }

    public enum Center {
        Never("Never"),
        On_Enable("On Enable"),
        Incomplete("Incomplete"),
        Always("Always");

        private final String name;

        Center(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public enum BlockType {
        Safe("Safe"),
        Normal("Normal"),
        Unsafe("Unsafe");

        private final String name;

        BlockType(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
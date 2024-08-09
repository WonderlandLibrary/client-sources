package xyz.mathax.mathaxclient.systems.modules.world;

import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.eventbus.EventPriority;
import xyz.mathax.mathaxclient.events.entity.player.BlockBreakingCooldownEvent;
import xyz.mathax.mathaxclient.events.render.Render3DEvent;
import xyz.mathax.mathaxclient.events.world.TickEvent;
import xyz.mathax.mathaxclient.renderer.ShapeMode;
import xyz.mathax.mathaxclient.settings.*;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.utils.Utils;
import xyz.mathax.mathaxclient.utils.misc.Pool;
import xyz.mathax.mathaxclient.utils.render.RenderUtils;
import xyz.mathax.mathaxclient.utils.render.color.SettingColor;
import xyz.mathax.mathaxclient.utils.settings.ListMode;
import xyz.mathax.mathaxclient.utils.world.BlockIterator;
import xyz.mathax.mathaxclient.utils.world.BlockUtils;
import net.minecraft.block.Block;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Nuker extends Module {
    private final Pool<BlockPos.Mutable> blockPosPool = new Pool<>(BlockPos.Mutable::new);
    private final List<BlockPos.Mutable> blocks = new ArrayList<>();

    private boolean firstBlock;
    private final BlockPos.Mutable lastBlockPos = new BlockPos.Mutable();

    private int timer;
    private int noBlockTimer;

    private final BlockPos.Mutable pos1 = new BlockPos.Mutable();
    private final BlockPos.Mutable pos2 = new BlockPos.Mutable();

    private Box box;

    int maxh = 0;
    int maxv = 0;

    private final SettingGroup generalSettings = settings.createGroup("General");
    private final SettingGroup listSettings = settings.createGroup("List");
    private final SettingGroup renderSettings = settings.createGroup("Render");

    // General

    private final Setting<Shape> shapeSetting = generalSettings.add(new EnumSetting.Builder<Shape>()
            .name("Shape")
            .description("The shape of nuking algorithm.")
            .defaultValue(Shape.Sphere)
            .build()
    );

    private final Setting<Nuker.Mode> modeSetting = generalSettings.add(new EnumSetting.Builder<Nuker.Mode>()
            .name("Mode")
            .description("The way the blocks are broken.")
            .defaultValue(Nuker.Mode.Flatten)
            .build()
    );

    private final Setting<Double> rangeSetting = generalSettings.add(new DoubleSetting.Builder()
            .name("Range")
            .description("The break range.")
            .defaultValue(4)
            .min(0)
            .visible(() -> shapeSetting.get() != Shape.Cube)
            .build()
    );


    private final Setting<Integer> rangeUpSetting = generalSettings.add(new IntSetting.Builder()
            .name("Up")
            .description("The break range.")
            .defaultValue(1)
            .min(0)
            .visible(() -> shapeSetting.get() == Shape.Cube)
            .build()
    );

    private final Setting<Integer> rangeDownSetting = generalSettings.add(new IntSetting.Builder()
            .name("Down")
            .description("The break range.")
            .defaultValue(1)
            .min(0)
            .visible(() -> shapeSetting.get() == Shape.Cube)
            .build()
    );

    private final Setting<Integer> rangeLeftSetting = generalSettings.add(new IntSetting.Builder()
            .name("Left")
            .description("The break range.")
            .defaultValue(1)
            .min(0)
            .visible(() -> shapeSetting.get() == Shape.Cube)
            .build()
    );

    private final Setting<Integer> rangeRightSetting = generalSettings.add(new IntSetting.Builder()
            .name("Right")
            .description("The break range.")
            .defaultValue(1)
            .min(0)
            .visible(() -> shapeSetting.get() == Shape.Cube)
            .build()
    );

    private final Setting<Integer> rangeForwardSetting = generalSettings.add(new IntSetting.Builder()
            .name("Forward")
            .description("The break range.")
            .defaultValue(1)
            .min(0)
            .visible(() -> shapeSetting.get() == Shape.Cube)
            .build()
    );

    private final Setting<Integer> rangeBackSetting = generalSettings.add(new IntSetting.Builder()
            .name("Back")
            .description("The break range.")
            .defaultValue(1)
            .min(0)
            .visible(() -> shapeSetting.get() == Shape.Cube)
            .build()
    );

    private final Setting<Integer> delaySetting = generalSettings.add(new IntSetting.Builder()
            .name("Delay")
            .description("Delay in ticks between breaking blocks.")
            .defaultValue(0)
            .build()
    );

    private final Setting<Integer> maxBlocksPerTickSetting = generalSettings.add(new IntSetting.Builder()
            .name("Max blocks per tick")
            .description("Maximum blocks to try to break per tick. Useful when insta mining.")
            .defaultValue(1)
            .min(1)
            .sliderRange(1, 6)
            .build()
    );

    private final Setting<SortMode> sortModeSetting = generalSettings.add(new EnumSetting.Builder<SortMode>()
            .name("Sort mode")
            .description("The blocks you want to mine first.")
            .defaultValue(SortMode.Closest)
            .build()
    );

    private final Setting<Boolean> packetMineSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Packet mine")
            .description("Attempt to instamine everything at once.")
            .defaultValue(false)
            .build()
    );

    // List

    private final Setting<ListMode> listModeSetting = listSettings.add(new EnumSetting.Builder<ListMode>()
            .name("List mode")
            .description("Selection mode.")
            .defaultValue(ListMode.Whitelist)
            .build()
    );

    private final Setting<List<Block>> whitelistSetting = listSettings.add(new BlockListSetting.Builder()
            .name("Whitelist")
            .description("The blocks you want to mine.")
            .visible(() -> listModeSetting.get() == ListMode.Whitelist)
            .build()
    );

    private final Setting<List<Block>> blacklistSetting = listSettings.add(new BlockListSetting.Builder()
            .name("Blacklist")
            .description("The blocks you don't want to mine.")
            .visible(() -> listModeSetting.get() == ListMode.Blacklist)
            .build()
    );

    // Render

    private final Setting<Boolean> swingHandSetting = renderSettings.add(new BoolSetting.Builder()
            .name("Swing hand")
            .description("Swing hand client side.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Boolean> enableRenderBoundingSetting = renderSettings.add(new BoolSetting.Builder()
            .name("Bounding box")
            .description("Enable rendering bounding box for Cube and Uniform Cube.")
            .defaultValue(true)
            .build()
    );

    private final Setting<ShapeMode> shapeModeBoxSetting = renderSettings.add(new EnumSetting.Builder<ShapeMode>()
            .name("Nuke box mode")
            .description("How the shape for the bounding box is rendered.")
            .defaultValue(ShapeMode.Both)
            .build()
    );

    private final Setting<SettingColor> sideColorBoxSetting = renderSettings.add(new ColorSetting.Builder()
            .name("Side color")
            .description("The side color of the bounding box.")
            .defaultValue(new SettingColor(15,105,145, 100))
            .build()
    );

    private final Setting<SettingColor> lineColorBoxSetting = renderSettings.add(new ColorSetting.Builder()
            .name("Line color")
            .description("The line color of the bounding box.")
            .defaultValue(new SettingColor(15, 105, 145))
            .build()
    );

    private final Setting<Boolean> enableRenderBreakingSetting = renderSettings.add(new BoolSetting.Builder()
            .name("Broken blocks")
            .description("Enable rendering bounding box for Cube and Uniform Cube.")
            .defaultValue(true)
            .build()
    );

    private final Setting<ShapeMode> shapeModeBreakSetting = renderSettings.add(new EnumSetting.Builder<ShapeMode>()
            .name("Nuke block mode")
            .description("How the shapes for broken blocks are rendered.")
            .defaultValue(ShapeMode.Both)
            .visible(enableRenderBreakingSetting::get)
            .build()
    );

    private final Setting<SettingColor> sideColorSetting = renderSettings .add(new ColorSetting.Builder()
            .name("Side color")
            .description("The side color of the target block rendering.")
            .defaultValue(new SettingColor(255, 0, 0, 75))
            .visible(enableRenderBreakingSetting::get)
            .build()
    );

    private final Setting<SettingColor> lineColorSetting = renderSettings.add(new ColorSetting.Builder()
            .name("Line color")
            .description("The line color of the target block rendering.")
            .defaultValue(new SettingColor(255, 0, 0))
            .visible(enableRenderBreakingSetting::get)
            .build()
    );

    public Nuker(Category category) {
        super(category, "Nuker", "Breaks blocks around you.");
    }

    @Override
    public void onEnable() {
        firstBlock = true;
        timer = 0;
        noBlockTimer = 0;
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    private void onBlockBreakingCooldown(BlockBreakingCooldownEvent event) {
        event.cooldown = 0;
    }

    @EventHandler
    private void onRender(Render3DEvent event) {
        if (enableRenderBoundingSetting.get()){
            if (shapeSetting.get() != Shape.Sphere && modeSetting.get() != Mode.Smash) {
                box = new Box(pos1, pos2);
                event.renderer.box(box, sideColorBoxSetting.get(), lineColorBoxSetting.get(), shapeModeBoxSetting.get(), 0);
            }
        }
    }

    @EventHandler
    private void onTickPre(TickEvent.Pre event) {
        if (timer > 0) {
            timer--;
            return;
        }

        double pX = mc.player.getX();
        double pY = mc.player.getY();
        double pZ = mc.player.getZ();

        double rangeSq = Math.pow(rangeSetting.get(), 2);
        if (shapeSetting.get() == Shape.Uniform_Cube) {
            rangeSetting.set((double) Math.round(rangeSetting.get()));
        }

        double pX_ = pX;
        double pZ_ = pZ;
        int r = (int) Math.round(rangeSetting.get());

        if (shapeSetting.get() == Shape.Uniform_Cube) {
            pX_ += 1;
            pos1.set(pX_ - r, pY - r + 1, pZ - r+1);
            pos2.set(pX_ + r-1, pY + r, pZ + r);
        } else {
            int direction = Math.round((mc.player.getRotationClient().y % 360) / 90);
            direction = Math.floorMod(direction, 4);

            pos1.set(pX_ - (rangeForwardSetting.get()), Math.ceil(pY) - rangeDownSetting.get(), pZ_ - rangeRightSetting.get());
            pos2.set(pX_ + rangeBackSetting.get()+1, Math.ceil(pY + rangeUpSetting.get() + 1), pZ_ + rangeLeftSetting.get() + 1);

            if (direction == 2) {
                pX_ += 1;
                pZ_ += 1;
                pos1.set(pX_ - (rangeLeftSetting.get()+1), Math.ceil(pY) - rangeDownSetting.get(), pZ_ - (rangeForwardSetting.get() + 1));
                pos2.set(pX_ + rangeRightSetting.get(), Math.ceil(pY + rangeUpSetting.get() + 1), pZ_ + rangeBackSetting.get());
            } else if (direction == 3) {
                pX_ += 1;
                pos1.set(pX_ - (rangeBackSetting.get()+1), Math.ceil(pY) - rangeDownSetting.get(), pZ_ - rangeLeftSetting.get());
                pos2.set(pX_ + rangeForwardSetting.get(), Math.ceil(pY + rangeUpSetting.get() + 1), pZ_ + rangeRightSetting.get() + 1);
            } else if (direction == 0) {
                pZ_ += 1;
                pX_ += 1;
                pos1.set(pX_ - (rangeRightSetting.get() + 1), Math.ceil(pY) - rangeDownSetting.get(), pZ_ - (rangeBackSetting.get() + 1));
                pos2.set(pX_ + rangeLeftSetting.get(), Math.ceil(pY + rangeUpSetting.get() + 1), pZ_ + rangeForwardSetting.get());
            }

            maxh = 1 + Math.max(Math.max(Math.max(rangeBackSetting.get(),rangeRightSetting.get()),rangeForwardSetting.get()),rangeLeftSetting.get());
            maxv = 1 + Math.max(rangeUpSetting.get(), rangeDownSetting.get());
        }

        if (modeSetting.get() == Mode.Flatten){
            pos1.setY((int) Math.floor(pY));
        }

        box = new Box(pos1, pos2);
        BlockIterator.register(Math.max((int) Math.ceil(rangeSetting.get()+1), maxh), Math.max((int) Math.ceil(rangeSetting.get()), maxv), (blockPos, blockState) -> {
            boolean toofarSphere = Utils.squaredDistance(pX, pY, pZ, blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5) > rangeSq;
            boolean toofarUniformCube = maxDist(Math.floor(pX), Math.floor(pY), Math.floor(pZ), blockPos.getX(), blockPos.getY(), blockPos.getZ()) >= rangeSetting.get();
            boolean toofarCube = !box.contains(Vec3d.ofCenter(blockPos));

            if (!BlockUtils.canBreak(blockPos, blockState) || (toofarSphere && shapeSetting.get() == Shape.Sphere) || (toofarUniformCube && shapeSetting.get() == Shape.Uniform_Cube) || (toofarCube && shapeSetting.get() == Shape.Cube)) {
                return;
            }

            if (modeSetting.get() == Mode.Flatten && blockPos.getY() < Math.floor(mc.player.getY())) {
                return;
            }

            if (modeSetting.get() == Mode.Smash && blockState.getHardness(mc.world, blockPos) != 0) {
                return;
            }

            // Check whitelist or blacklist
            if (listModeSetting.get() == ListMode.Whitelist && !whitelistSetting.get().contains(blockState.getBlock())) {
                return;
            }

            if (listModeSetting.get() == ListMode.Blacklist && blacklistSetting.get().contains(blockState.getBlock())) {
                return;
            }

            blocks.add(blockPosPool.get().set(blockPos));
        });

        BlockIterator.after(() -> {
            if (sortModeSetting.get() == SortMode.Top_Down) {
                blocks.sort(Comparator.comparingDouble(value -> -1 * value.getY()));
            } else if (sortModeSetting.get() != SortMode.None) {
                blocks.sort(Comparator.comparingDouble(value -> Utils.squaredDistance(pX, pY, pZ, value.getX() + 0.5, value.getY() + 0.5, value.getZ() + 0.5) * (sortModeSetting.get() == SortMode.Closest ? 1 : -1)));
            }

            if (blocks.isEmpty()) {
                if (noBlockTimer++ >= delaySetting.get()) {
                    firstBlock = true;
                }

                return;
            } else {
                noBlockTimer = 0;
            }

            if (!firstBlock && !lastBlockPos.equals(blocks.get(0))) {
                timer = delaySetting.get();

                firstBlock = false;
                lastBlockPos.set(blocks.get(0));

                if (timer > 0) {
                    return;
                }
            }

            int count = 0;
            for (BlockPos block : blocks) {
                if (count >= maxBlocksPerTickSetting.get()) {
                    break;
                }

                boolean canInstaMine = BlockUtils.canInstaBreak(block);
                if (packetMineSetting.get()) {
                    mc.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, block, Direction.UP));
                    mc.player.swingHand(Hand.MAIN_HAND);
                    mc.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, block, Direction.UP));
                } else {
                    BlockUtils.breakBlock(block, swingHandSetting.get());
                }

                if (enableRenderBreakingSetting.get()) {
                    RenderUtils.renderTickingBlock(block.toImmutable(), sideColorSetting.get(), lineColorSetting.get(), shapeModeBreakSetting.get(), 0, 8, true, false);
                }

                lastBlockPos.set(block);

                count++;

                if (!canInstaMine && !packetMineSetting.get()) {
                    break;
                }
            }

            firstBlock = false;

            for (BlockPos.Mutable blockPos : blocks) {
                blockPosPool.free(blockPos);
            }

            blocks.clear();
        });
    }

    public static double maxDist(double x1, double y1, double z1, double x2, double y2, double z2) {
        double dX = Math.ceil(Math.abs(x2 - x1));
        double dY = Math.ceil(Math.abs(y2 - y1));
        double dZ = Math.ceil(Math.abs(z2 - z1));
        return Math.max(Math.max(dX, dY), dZ);
    }

    public enum Mode {
        All("All"),
        Flatten("Flatten"),
        Smash("Smash");

        private final String name;

        Mode(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public enum SortMode {
        None("None"),
        Closest("Closest"),
        Furthest("Furthest"),
        Top_Down("Top Down");

        private final String name;

        SortMode(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public enum Shape {
        Cube("Cube"),
        Uniform_Cube("Uniform Cube"),
        Sphere("Sphere");

        private final String name;

        Shape(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    @Override
    public String getInfoString() {
        int size = listModeSetting.get() == ListMode.Whitelist ? whitelistSetting.get().size() : blacklistSetting.get().size();
        return modeSetting.get().toString() + " (" + size + ")";
    }
}
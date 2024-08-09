package xyz.mathax.mathaxclient.systems.modules.world;

import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.render.Render3DEvent;
import xyz.mathax.mathaxclient.events.world.TickEvent;
import xyz.mathax.mathaxclient.renderer.ShapeMode;
import xyz.mathax.mathaxclient.settings.*;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.systems.modules.Modules;
import xyz.mathax.mathaxclient.systems.modules.player.AutoEat;
import xyz.mathax.mathaxclient.systems.modules.player.AutoGap;
import xyz.mathax.mathaxclient.systems.modules.player.AutoTool;
import xyz.mathax.mathaxclient.utils.misc.HorizontalDirection;
import xyz.mathax.mathaxclient.utils.misc.MBlockPos;
import xyz.mathax.mathaxclient.utils.player.CustomPlayerInput;
import xyz.mathax.mathaxclient.utils.player.InvUtils;
import xyz.mathax.mathaxclient.utils.player.Rotations;
import xyz.mathax.mathaxclient.utils.render.color.Color;
import xyz.mathax.mathaxclient.utils.render.color.SettingColor;
import xyz.mathax.mathaxclient.utils.world.BlockUtils;
import xyz.mathax.mathaxclient.utils.world.MatHaxDirection;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.input.Input;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

public class HighwayBuilder extends Module {
    private static final BlockPos ZERO = new BlockPos(0, 0, 0);

    private HorizontalDirection dir, leftDir, rightDir;

    private Input prevInput;
    private CustomPlayerInput input;

    private State state, lastState;
    private IBlockPosProvider blockPosProvider;

    public Vec3d start;
    public int blocksBroken, blocksPlaced;
    private final MBlockPos lastBreakingPos = new MBlockPos();
    private boolean displayInfo;

    private final MBlockPos posRender2 = new MBlockPos();
    private final MBlockPos posRender3 = new MBlockPos();

    private final SettingGroup generalSettings = settings.createGroup("General");
    private final SettingGroup renderMineSettings = settings.createGroup("Render Mine");
    private final SettingGroup renderPlaceSettings = settings.createGroup("Render Place");

    // General

    private final Setting<Integer> widthSetting = generalSettings.add(new IntSetting.Builder()
            .name("Width")
            .description("Width of the highway.")
            .defaultValue(4)
            .range(1, 5)
            .sliderRange(1, 5)
            .build()
    );

    private final Setting<Integer> heightSetting = generalSettings.add(new IntSetting.Builder()
            .name("Height")
            .description("Height of the highway.")
            .defaultValue(3)
            .range(2, 5)
            .sliderRange(2, 5)
            .build()
    );

    private final Setting<Floor> floorSetting = generalSettings.add(new EnumSetting.Builder<Floor>()
            .name("Floor")
            .description("What floor placement mode to use.")
            .defaultValue(Floor.Replace)
            .build()
    );

    private final Setting<Boolean> railingsSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Railings")
            .description("Build railings next to the highway.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Boolean> mineAboveRailingsSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Mine above railings")
            .description("Mine blocks above railings.")
            .visible(railingsSetting::get)
            .defaultValue(true)
            .build()
    );

    private final Setting<Rotation> rotationSetting = generalSettings.add(new EnumSetting.Builder<Rotation>()
            .name("Rotation")
            .description("Mode of rotation.")
            .defaultValue(Rotation.Both)
            .build()
    );

    private final Setting<List<Block>> blocksToPlaceSetting = generalSettings.add(new BlockListSetting.Builder()
            .name("Blocks to place")
            .description("Blocks allowed to be placed.")
            .defaultValue(Blocks.OBSIDIAN)
            .filter(block -> Block.isShapeFullCube(block.getDefaultState().getCollisionShape(mc.world, ZERO)))
            .build()
    );

    private final Setting<List<Item>> trashItemsSetting = generalSettings.add(new ItemListSetting.Builder()
            .name("Trash items")
            .description("Items that are considered trash and can be thrown out.")
            .defaultValue(
                    Items.NETHERRACK,
                    Items.QUARTZ,
                    Items.GOLD_NUGGET,
                    Items.GLOWSTONE_DUST,
                    Items.BLACKSTONE,
                    Items.BASALT
            )
            .build()
    );

    private final Setting<Boolean> dontBreakToolsSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Dont break tools")
            .description("Don't break tools.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Boolean> mineEnderChestsSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Mine ender chests")
            .description("Mine ender chests for obsidian.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Boolean> disconnectOnToggleSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Disconnect on toggle")
            .description("Automatically disconnects when the module is turned off, for example for not having enough blocks.")
            .defaultValue(false)
            .build()
    );

    // Render Mine

    private final Setting<Boolean> renderMineSetting = renderMineSettings.add(new BoolSetting.Builder()
            .name("Enabled")
            .description("Render blocks to be mined.")
            .defaultValue(true)
            .build()
    );

    private final Setting<ShapeMode> renderMineShapeModeSetting = renderMineSettings.add(new EnumSetting.Builder<ShapeMode>()
            .name("Shape mode")
            .description("How the blocks to be mined are rendered.")
            .defaultValue(ShapeMode.Both)
            .build()
    );

    private final Setting<SettingColor> renderMineSideColorSetting = renderMineSettings.add(new ColorSetting.Builder()
            .name("Side color")
            .description("Color of blocks to be mined.")
            .defaultValue(new SettingColor(225, 25, 25, 25))
            .build()
    );

    private final Setting<SettingColor> renderMineLineColorSetting = renderMineSettings.add(new ColorSetting.Builder()
            .name("Line color")
            .description("Color of blocks to be mined.")
            .defaultValue(new SettingColor(225, 25, 25))
            .build()
    );

    // Render Place

    private final Setting<Boolean> renderPlaceSetting = renderPlaceSettings.add(new BoolSetting.Builder()
            .name("Enabled")
            .description("Render blocks to be placed.")
            .defaultValue(true)
            .build()
    );

    private final Setting<ShapeMode> renderPlaceShapeModeSetting = renderPlaceSettings.add(new EnumSetting.Builder<ShapeMode>()
            .name("Shape mode")
            .description("How the blocks to be placed are rendered.")
            .defaultValue(ShapeMode.Both)
            .build()
    );

    private final Setting<SettingColor> renderPlaceSideColorSetting = renderPlaceSettings.add(new ColorSetting.Builder()
            .name("Side color")
            .description("Color of blocks to be placed.")
            .defaultValue(new SettingColor(25, 25, 225, 25))
            .build()
    );

    private final Setting<SettingColor> renderPlaceLineColorSetting = renderPlaceSettings.add(new ColorSetting.Builder()
            .name("Line color")
            .description("Color of blocks to be placed.")
            .defaultValue(new SettingColor(25, 25, 225))
            .build()
    );

    public HighwayBuilder(Category category) {
        super(category, "Highway Builder", "Automatically builds highways.");
    }

    @Override
    public void onEnable() {
        dir = HorizontalDirection.get(mc.player.getYaw());
        leftDir = dir.rotateLeftSkipOne();
        rightDir = leftDir.opposite();

        prevInput = mc.player.input;
        mc.player.input = input = new CustomPlayerInput();

        state = State.Forward;
        setState(State.Center);
        blockPosProvider = dir.diagonal ? new DiagonalBlockPosProvider() : new StraightBlockPosProvider();

        start = mc.player.getPos();
        blocksBroken = blocksPlaced = 0;
        lastBreakingPos.set(0, 0, 0);
        displayInfo = true;
    }

    @Override
    public void onDisable() {
        mc.player.input = prevInput;

        mc.player.setYaw(dir.yaw);

        if (displayInfo) {
            info("Distance: (highlight)%.0f", mc.player.getPos().distanceTo(start));
            info("Blocks broken: (highlight)%d", blocksBroken);
            info("Blocks placed: (highlight)%d", blocksPlaced);
        }
    }

    @Override
    public void error(String message, Object... args) {
        super.error(message, args);
        forceToggle(false);

        if (disconnectOnToggleSetting.get()) {
            disconnect(message, args);
        }
    }

    private void errorEarly(String message, Object... args) {
        super.error(message, args);

        displayInfo = false;
        toggle();
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (widthSetting.get() < 3 && dir.diagonal) {
            errorEarly("Diagonal highways with width less than 3 are not supported.");
            return;
        }

        if (Modules.get().get(AutoEat.class).eating) {
            return;
        }

        if (Modules.get().get(AutoGap.class).isEating()) {
            return;
        }

        state.tick(this);
    }

    @EventHandler
    private void onRender3D(Render3DEvent event) {
        if (renderMineSetting.get()) {
            render(event, blockPosProvider.getFront(), mBlockPos -> canMine(mBlockPos, true), true);

            if (floorSetting.get() == Floor.Replace) {
                render(event, blockPosProvider.getFloor(), mBlockPos -> canMine(mBlockPos, false), true);
            }

            if (railingsSetting.get()) {
                render(event, blockPosProvider.getRailings(true), mBlockPos -> canMine(mBlockPos, false), true);
            }

            if (state == State.MineEChestBlockade) {
                render(event, blockPosProvider.getEChestBlockade(true), mBlockPos -> canMine(mBlockPos, true), true);
            }
        }

        if (renderPlaceSetting.get()) {
            render(event, blockPosProvider.getLiquids(), mBlockPos -> canPlace(mBlockPos, true), false);

            if (railingsSetting.get()) {
                render(event, blockPosProvider.getRailings(false), mBlockPos -> canPlace(mBlockPos, false), false);
            }

            render(event, blockPosProvider.getFloor(), mBlockPos -> canPlace(mBlockPos, false), false);

            if (state == State.PlaceEChestBlockade) {
                render(event, blockPosProvider.getEChestBlockade(false), mBlockPos -> canPlace(mBlockPos, false), false);
            }
        }
    }

    private void render(Render3DEvent event, MBPIterator it, Predicate<MBlockPos> predicate, boolean mine) {
        Color sideColor = mine ? renderMineSideColorSetting.get() : renderPlaceSideColorSetting.get();
        Color lineColor = mine ? renderMineLineColorSetting.get() : renderPlaceLineColorSetting.get();
        ShapeMode shapeMode = mine ? renderMineShapeModeSetting.get() : renderPlaceShapeModeSetting.get();

        for (MBlockPos pos : it) {
            posRender2.set(pos);

            if (predicate.test(posRender2)) {
                int excludeDir = 0;

                for (net.minecraft.util.math.Direction side : net.minecraft.util.math.Direction.values()) {
                    posRender3.set(posRender2).add(side.getOffsetX(), side.getOffsetY(), side.getOffsetZ());

                    it.save();
                    for (MBlockPos blockPos : it) {
                        if (blockPos.equals(posRender3) && predicate.test(blockPos)) {
                            excludeDir |= MatHaxDirection.get(side);
                        }
                    }

                    it.restore();
                }

                event.renderer.box(posRender2.getMcPos(), sideColor, lineColor, shapeMode, excludeDir);
            }
        }
    }

    private void setState(State state) {
        lastState = this.state;
        this.state = state;

        input.stop();
        state.start(this);
    }

    private int getWidthLeft() {
        return switch (widthSetting.get()) {
            default -> 0;
            case 2, 3 -> 1;
            case 4, 5 -> 2;
        };
    }

    private int getWidthRight() {
        return switch (widthSetting.get()) {
            default -> 0;
            case 3, 4 -> 1;
            case 5 -> 2;
        };
    }

    private boolean canMine(MBlockPos pos, boolean ignoreBlocksToPlace) {
        BlockState state = pos.getState();
        return BlockUtils.canBreak(pos.getMcPos(), state) && (ignoreBlocksToPlace || !blocksToPlaceSetting.get().contains(state.getBlock()));
    }

    private boolean canPlace(MBlockPos pos, boolean liquids) {
        return liquids ? !pos.getState().getFluidState().isEmpty() : pos.getState().isAir();
    }

    private void disconnect(String message, Object... args) {
        MutableText text = Text.literal(String.format("%s[%s%s%s] %s", Formatting.GRAY, Formatting.BLUE, name, Formatting.GRAY, Formatting.RED) + String.format(message, args)).append("\n");
        text.append(getStatsText());

        mc.getNetworkHandler().getConnection().disconnect(text);
    }

    public MutableText getStatsText() {
        MutableText text = Text.literal(String.format("%sDistance: %s%.0f\n", Formatting.GRAY, Formatting.WHITE, mc.player == null ? 0.0f : mc.player.getPos().distanceTo(start)));
        text.append(String.format("%sBlocks broken: %s%d\n", Formatting.GRAY, Formatting.WHITE, blocksBroken));
        text.append(String.format("%sBlocks placed: %s%d", Formatting.GRAY, Formatting.WHITE, blocksPlaced));
        return text;
    }

    private enum State {
        Center {
            @Override
            protected void tick(HighwayBuilder highwayBuilder) {
                double x = Math.abs(highwayBuilder.mc.player.getX() - (int) highwayBuilder.mc.player.getX()) - 0.5;
                double z = Math.abs(highwayBuilder.mc.player.getZ() - (int) highwayBuilder.mc.player.getZ()) - 0.5;

                boolean isX = Math.abs(x) <= 0.1;
                boolean isZ = Math.abs(z) <= 0.1;

                if (isX && isZ) {
                    highwayBuilder.mc.player.setPosition((int) highwayBuilder.mc.player.getX() + (highwayBuilder.mc.player.getX() < 0 ? -0.5 : 0.5), highwayBuilder.mc.player.getY(), (int) highwayBuilder.mc.player.getZ() + (highwayBuilder.mc.player.getZ() < 0 ? -0.5 : 0.5));
                    highwayBuilder.setState(highwayBuilder.lastState);
                } else {
                    highwayBuilder.mc.player.setYaw(0);

                    if (!isZ) {
                        highwayBuilder.input.pressingForward = z < 0;
                        highwayBuilder.input.pressingBack = z > 0;

                        if (highwayBuilder.mc.player.getZ() < 0) {
                            boolean forward = highwayBuilder.input.pressingForward;
                            highwayBuilder.input.pressingForward = highwayBuilder.input.pressingBack;
                            highwayBuilder.input.pressingBack = forward;
                        }
                    }

                    if (!isX) {
                        highwayBuilder.input.pressingRight = x > 0;
                        highwayBuilder.input.pressingLeft = x < 0;

                        if (highwayBuilder.mc.player.getX() < 0) {
                            boolean right = highwayBuilder.input.pressingRight;
                            highwayBuilder.input.pressingRight = highwayBuilder.input.pressingLeft;
                            highwayBuilder.input.pressingLeft = right;
                        }
                    }

                    highwayBuilder.input.sneaking = true;
                }
            }
        },
        Forward {
            @Override
            protected void tick(HighwayBuilder highwayBuilder) {
                highwayBuilder.mc.player.setYaw(highwayBuilder.dir.yaw);

                if (needsToPlace(highwayBuilder, highwayBuilder.blockPosProvider.getLiquids(), true)) {
                    highwayBuilder.setState(Fill_Liquids); // Fill Liquids
                } else if (needsToMine(highwayBuilder, highwayBuilder.blockPosProvider.getFront(), true)) {
                    highwayBuilder.setState(Mine_Front); // Mine Front
                } else if (highwayBuilder.floorSetting.get() == Floor.Replace && needsToMine(highwayBuilder, highwayBuilder.blockPosProvider.getFloor(), false)) {
                    highwayBuilder.setState(Mine_Floor); // Mine Floor
                } else if (highwayBuilder.railingsSetting.get() && needsToMine(highwayBuilder, highwayBuilder.blockPosProvider.getRailings(true), false)) {
                    highwayBuilder.setState(Mine_Railings); // Mine Railings
                } else if (highwayBuilder.railingsSetting.get() && needsToPlace(highwayBuilder, highwayBuilder.blockPosProvider.getRailings(false), false)) {
                    highwayBuilder.setState(Place_Railings); // Place Railings
                } else if (needsToPlace(highwayBuilder, highwayBuilder.blockPosProvider.getFloor(), false)) {
                    highwayBuilder.setState(PlaceFloor); // Place Floor
                } else {
                    highwayBuilder.input.pressingForward = true; // Move
                }
            }

            private boolean needsToMine(HighwayBuilder highwayBuilder, MBPIterator it, boolean ignoreBlocksToPlace) {
                for (MBlockPos pos : it) {
                    if (highwayBuilder.canMine(pos, ignoreBlocksToPlace)) {
                        return true;
                    }
                }

                return false;
            }

            private boolean needsToPlace(HighwayBuilder highwayBuilder, MBPIterator it, boolean liquids) {
                for (MBlockPos pos : it) {
                    if (highwayBuilder.canPlace(pos, liquids)) {
                        return true;
                    }
                }

                return false;
            }
        },
        Fill_Liquids {
            @Override
            protected void tick(HighwayBuilder highwayBuilder) {
                int slot = findBlocksToPlacePrioritizeTrash(highwayBuilder);
                if (slot == -1) {
                    return;
                }

                place(highwayBuilder, new MBPIteratorFilter(highwayBuilder.blockPosProvider.getLiquids(), pos -> !pos.getState().getFluidState().isEmpty()), slot, Forward);
            }
        },
        Mine_Front {
            @Override
            protected void tick(HighwayBuilder highwayBuilder) {
                mine(highwayBuilder, highwayBuilder.blockPosProvider.getFront(), true);
            }
        },
        Mine_Floor {
            @Override
            protected void tick(HighwayBuilder highwayBuilder) {
                mine(highwayBuilder, highwayBuilder.blockPosProvider.getFloor(), false);
            }
        },

        PlaceFloor {
            @Override
            protected void tick(HighwayBuilder highwayBuilder) {
                int slot = findBlocksToPlace(highwayBuilder);
                if (slot == -1) {
                    return;
                }

                place(highwayBuilder, highwayBuilder.blockPosProvider.getFloor(), slot, Forward);
            }
        },
        Mine_Railings {
            @Override
            protected void tick(HighwayBuilder highwayBuilder) {
                mine(highwayBuilder, highwayBuilder.blockPosProvider.getRailings(true), false);
            }
        },
        Place_Railings {
            @Override
            protected void tick(HighwayBuilder highwayBuilder) {
                int slot = findBlocksToPlace(highwayBuilder);
                if (slot == -1) {
                    return;
                }

                place(highwayBuilder, highwayBuilder.blockPosProvider.getRailings(false), slot, Forward);
            }
        },
        Throw_Out_Trash {
            private int skipSlot;
            private boolean timerEnabled, firstTick;
            private int timer;

            @Override
            protected void start(HighwayBuilder highwayBuilder) {
                int biggestCount = 0;
                for (int i = 0; i < highwayBuilder.mc.player.getInventory().main.size(); i++) {
                    ItemStack itemStack = highwayBuilder.mc.player.getInventory().getStack(i);

                    if (itemStack.getItem() instanceof BlockItem && highwayBuilder.trashItemsSetting.get().contains(itemStack.getItem()) && itemStack.getCount() > biggestCount) {
                        biggestCount = itemStack.getCount();
                        skipSlot = i;

                        if (biggestCount >= 64) {
                            break;
                        }
                    }
                }

                if (biggestCount == 0) {
                    skipSlot = -1;
                }

                timerEnabled = false;
                firstTick = true;
            }

            @Override
            protected void tick(HighwayBuilder highwayBuilder) {
                if (timerEnabled) {
                    if (timer > 0) {
                        timer--;
                    } else {
                        highwayBuilder.setState(highwayBuilder.lastState);
                    }

                    return;
                }

                highwayBuilder.mc.player.setYaw(highwayBuilder.dir.opposite().yaw);
                highwayBuilder.mc.player.setPitch(-25);

                if (firstTick) {
                    firstTick = false;
                    return;
                }

                for (int i = 0; i < highwayBuilder.mc.player.getInventory().main.size(); i++) {
                    if (i == skipSlot) {
                        continue;
                    }

                    ItemStack itemStack = highwayBuilder.mc.player.getInventory().getStack(i);
                    if (highwayBuilder.trashItemsSetting.get().contains(itemStack.getItem())) {
                        InvUtils.drop().slot(i);
                        return;
                    }
                }

                timerEnabled = true;
                timer = 10;
            }
        },

        PlaceEChestBlockade {
            @Override
            protected void tick(HighwayBuilder highwayBuilder) {
                int slot = findBlocksToPlacePrioritizeTrash(highwayBuilder);
                if (slot == -1) {
                    return;
                }

                place(highwayBuilder, highwayBuilder.blockPosProvider.getEChestBlockade(false), slot, MineEnderChests);
            }
        },

        MineEChestBlockade {
            @Override
            protected void tick(HighwayBuilder highwayBuilder) {
                mine(highwayBuilder, highwayBuilder.blockPosProvider.getEChestBlockade(true), true, Center, Forward);
            }
        },

        MineEnderChests {
            private static final MBlockPos pos = new MBlockPos();

            private int minimumObsidian;
            private boolean first;
            private int moveTimer;

            private boolean stopTimerEnabled;
            private int stopTimer;

            @Override
            protected void start(HighwayBuilder highwayBuilder) {
                if (highwayBuilder.lastState != Center && highwayBuilder.lastState != Throw_Out_Trash && highwayBuilder.lastState != PlaceEChestBlockade) {
                    highwayBuilder.setState(Center);
                    return;
                } else if (highwayBuilder.lastState == Center) {
                    highwayBuilder.setState(Throw_Out_Trash);
                    return;
                } else if (highwayBuilder.lastState == Throw_Out_Trash) {
                    highwayBuilder.setState(PlaceEChestBlockade);
                    return;
                }

                int emptySlots = 0;
                for (int i = 0; i < highwayBuilder.mc.player.getInventory().main.size(); i++) {
                    if (highwayBuilder.mc.player.getInventory().getStack(i).isEmpty()) {
                        emptySlots++;
                    }
                }

                if (emptySlots == 0) {
                    highwayBuilder.error("No empty slots.");
                    return;
                }

                int minimumSlots = Math.max(emptySlots - 4, 1);
                minimumObsidian = minimumSlots * 64;
                first = true;
                moveTimer = 0;

                stopTimerEnabled = false;
            }

            @Override
            protected void tick(HighwayBuilder highwayBuilder) {
                if (stopTimerEnabled) {
                    if (stopTimer > 0) {
                        stopTimer--;
                    } else {
                        highwayBuilder.setState(MineEChestBlockade);
                    }

                    return;
                }

                HorizontalDirection dir = highwayBuilder.dir.diagonal ? highwayBuilder.dir.rotateLeft().rotateLeftSkipOne() : highwayBuilder.dir.opposite();
                pos.set(highwayBuilder.mc.player).offset(dir);

                if (moveTimer > 0) {
                    highwayBuilder.mc.player.setYaw(dir.yaw);
                    highwayBuilder.input.pressingForward = moveTimer > 2;

                    moveTimer--;
                    return;
                }

                int obsidianCount = 0;
                for (Entity entity : highwayBuilder.mc.world.getOtherEntities(highwayBuilder.mc.player, new Box(pos.x, pos.y, pos.z, pos.x + 1, pos.y + 2, pos.z + 1))) {
                    if (entity instanceof ItemEntity itemEntity && itemEntity.getStack().getItem() == Items.OBSIDIAN) {
                        obsidianCount += itemEntity.getStack().getCount();
                    }
                }

                for (int i = 0; i < highwayBuilder.mc.player.getInventory().main.size(); i++) {
                    ItemStack itemStack = highwayBuilder.mc.player.getInventory().getStack(i);
                    if (itemStack.getItem() == Items.OBSIDIAN) {
                        obsidianCount += itemStack.getCount();
                    }
                }

                if (obsidianCount >= minimumObsidian) {
                    stopTimerEnabled = true;
                    stopTimer = 8;
                    return;
                }

                BlockState blockState = highwayBuilder.mc.world.getBlockState(pos.getMcPos());
                if (blockState.getBlock() == Blocks.ENDER_CHEST) {
                    int slot = findAndMoveBestToolToHotbar(highwayBuilder, blockState, true, false);
                    if (slot == -1) {
                        highwayBuilder.error("Cannot find pickaxe with silk touch to mine ender chests.");
                        return;
                    }

                    InvUtils.swap(slot, false);
                    BlockUtils.breakBlock(pos.getMcPos(), true);
                } else {
                    int slot = findAndMoveToHotbar(highwayBuilder, itemStack -> itemStack.getItem() == Items.ENDER_CHEST, false);
                    if (slot == -1) {
                        stopTimerEnabled = true;
                        stopTimer = 4;
                        return;
                    }

                    if (first) {
                        moveTimer = 8;
                        first = false;
                    }

                    BlockUtils.place(pos.getMcPos(), Hand.MAIN_HAND, slot, true, 0, true, true, false);
                }
            }
        };

        protected void start(HighwayBuilder highwayBuilder) {}

        protected abstract void tick(HighwayBuilder highwayBuilder);

        protected void mine(HighwayBuilder highwayBuilder, MBPIterator it, boolean ignoreBlocksToPlace, State nextState, State lastState) {
            boolean breaking = false;

            for (MBlockPos pos : it) {
                BlockState state = pos.getState();
                if (state.isAir() || (!ignoreBlocksToPlace && highwayBuilder.blocksToPlaceSetting.get().contains(state.getBlock()))) {
                    continue;
                }

                int slot = findAndMoveBestToolToHotbar(highwayBuilder, state, false, true);
                if (slot == -1) {
                    return;
                }

                InvUtils.swap(slot, false);

                BlockPos mcPos = pos.getMcPos();
                if (BlockUtils.canBreak(mcPos)) {
                    if (highwayBuilder.rotationSetting.get().mine) {
                        Rotations.rotate(Rotations.getYaw(mcPos), Rotations.getPitch(mcPos), () -> BlockUtils.breakBlock(pos.getMcPos(), true));
                    } else {
                        BlockUtils.breakBlock(mcPos, true);
                    }

                    breaking = true;

                    if (!highwayBuilder.lastBreakingPos.equals(pos)) {
                        highwayBuilder.lastBreakingPos.set(pos);
                        highwayBuilder.blocksBroken++;
                    }

                    break;
                }
            }

            if (!breaking) {
                highwayBuilder.setState(nextState);
                highwayBuilder.lastState = lastState;
            }
        }

        protected void mine(HighwayBuilder highwayBuilder, MBPIterator it, boolean ignoreBlocksToPlace) {
            mine(highwayBuilder, it, ignoreBlocksToPlace, Forward, highwayBuilder.state);
        }

        protected void place(HighwayBuilder highwayBuilder, MBPIterator it, int slot, State nextState) {
            boolean placed = false;
            for (MBlockPos pos : it) {
                if (BlockUtils.place(pos.getMcPos(), Hand.MAIN_HAND, slot, highwayBuilder.rotationSetting.get().place, 0, true, true, true)) {
                    placed = true;
                    highwayBuilder.blocksPlaced++;
                    break;
                }
            }

            if (!placed) {
                highwayBuilder.setState(nextState);
            }
        }

        private int findSlot(HighwayBuilder highwayBuilder, Predicate<ItemStack> predicate, boolean hotbar) {
            for (int i = hotbar ? 0 : 9; i < (hotbar ? 9 : highwayBuilder.mc.player.getInventory().main.size()); i++) {
                if (predicate.test(highwayBuilder.mc.player.getInventory().getStack(i))) {
                    return i;
                }
            }

            return -1;
        }

        private int findHotbarSlot(HighwayBuilder highwayBuilder, boolean replaceTools) {
            int thrashSlot = -1;
            int slotsWithBlocks = 0;
            int slotWithLeastBlocks = 65;
            int slowWithLeastBlocksCount = 0;
            for (int i = 0; i < 9; i++) {
                ItemStack itemStack = highwayBuilder.mc.player.getInventory().getStack(i);
                if (itemStack.isEmpty()) {
                    return i;
                }

                if (replaceTools && AutoTool.isTool(itemStack)) {
                    return i;
                }

                if (highwayBuilder.trashItemsSetting.get().contains(itemStack.getItem())) {
                    thrashSlot = i;
                }

                if (itemStack.getItem() instanceof BlockItem blockItem && highwayBuilder.blocksToPlaceSetting.get().contains(blockItem.getBlock())) {
                    slotsWithBlocks++;
                    if (itemStack.getCount() < slowWithLeastBlocksCount) {
                        slowWithLeastBlocksCount = itemStack.getCount();
                        slotWithLeastBlocks = i;
                    }
                }
            }

            if (thrashSlot != -1) {
                return thrashSlot;
            }

            if (slotsWithBlocks > 1) {
                return slotWithLeastBlocks;
            }

            highwayBuilder.error("No empty space in hotbar.");
            return -1;
        }

        private boolean hasItem(HighwayBuilder highwayBuilder, Item item) {
            for (int i = 0; i < highwayBuilder.mc.player.getInventory().main.size(); i++) {
                if (highwayBuilder.mc.player.getInventory().getStack(i).getItem() == item) {
                    return true;
                }
            }

            return false;
        }

        protected int findAndMoveToHotbar(HighwayBuilder highwayBuilder, Predicate<ItemStack> predicate, boolean required) {
            int slot = findSlot(highwayBuilder, predicate, true);
            if (slot != -1) {
                return slot;
            }

            int hotbarSlot = findHotbarSlot(highwayBuilder, false);
            if (hotbarSlot == -1) {
                return -1;
            }

            slot = findSlot(highwayBuilder, predicate, false);

            if (slot == -1) {
                if (required) {
                    highwayBuilder.error("Out of items.");
                }

                return -1;
            }

            InvUtils.move().from(slot).toHotbar(hotbarSlot);
            InvUtils.dropHand();

            return hotbarSlot;
        }

        protected int findAndMoveBestToolToHotbar(HighwayBuilder highwayBuilder, BlockState blockState, boolean noSilkTouch, boolean error) {
            if (highwayBuilder.mc.player.isCreative()) {
                return highwayBuilder.mc.player.getInventory().selectedSlot;
            }

            double bestScore = -1;
            int bestSlot = -1;
            for (int i = 0; i < highwayBuilder.mc.player.getInventory().main.size(); i++) {
                double score = AutoTool.getScore(highwayBuilder.mc.player.getInventory().getStack(i), blockState, false, AutoTool.EnchantPreference.None, itemStack -> {
                    if (noSilkTouch && EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, itemStack) != 0) {
                        return false;
                    }

                    return !highwayBuilder.dontBreakToolsSetting.get() || itemStack.getMaxDamage() - itemStack.getDamage() > 1;
                });

                if (score > bestScore) {
                    bestScore = score;
                    bestSlot = i;
                }
            }

            if (bestSlot == -1) {
                if (error) {
                    highwayBuilder.error("Failed to find suitable tool for mining.");
                }

                return -1;
            }

            if (bestSlot < 9) {
                return bestSlot;
            }

            int hotbarSlot = findHotbarSlot(highwayBuilder, true);
            if (hotbarSlot == -1) {
                return -1;
            }

            InvUtils.move().from(bestSlot).toHotbar(hotbarSlot);
            InvUtils.dropHand();

            return hotbarSlot;
        }

        protected int findBlocksToPlace(HighwayBuilder highwayBuilder) {
            int slot = findAndMoveToHotbar(highwayBuilder, itemStack -> itemStack.getItem() instanceof BlockItem blockItem && highwayBuilder.blocksToPlaceSetting.get().contains(blockItem.getBlock()), false);

            if (slot == -1) {
                if (!highwayBuilder.mineEnderChestsSetting.get()) {
                    highwayBuilder.error("Out of blocks to place.");
                } else {
                    if (hasItem(highwayBuilder, Items.ENDER_CHEST)) {
                        highwayBuilder.setState(MineEnderChests);
                    } else {
                        highwayBuilder.error("Out of blocks to place.");
                    }
                }

                return -1;
            }

            return slot;
        }

        protected int findBlocksToPlacePrioritizeTrash(HighwayBuilder highwayBuilder) {
            int slot = findAndMoveToHotbar(highwayBuilder, itemStack -> {
                if (!(itemStack.getItem() instanceof BlockItem)) {
                    return false;
                }

                return highwayBuilder.trashItemsSetting.get().contains(itemStack.getItem());
            }, false);

            return slot != -1 ? slot : findBlocksToPlace(highwayBuilder);
        }
    }

    private interface MBPIterator extends Iterator<MBlockPos>, Iterable<MBlockPos> {
        void save();
        void restore();

        @NotNull
        @Override
        default Iterator<MBlockPos> iterator() {
            return this;
        }
    }

    private static class MBPIteratorFilter implements MBPIterator {
        private final MBPIterator it;
        private final Predicate<MBlockPos> predicate;

        private MBlockPos pos;
        private boolean isOld = true;

        private boolean pisOld = true;

        public MBPIteratorFilter(MBPIterator it, Predicate<MBlockPos> predicate) {
            this.it = it;
            this.predicate = predicate;
        }

        @Override
        public void save() {
            it.save();
            pisOld = isOld;
            isOld = true;
        }

        @Override
        public void restore() {
            it.restore();
            isOld = pisOld;
        }

        @Override
        public boolean hasNext() {
            if (isOld) {
                isOld = false;
                pos = null;

                while (it.hasNext()) {
                    pos = it.next();

                    if (predicate.test(pos)) {
                        return true;
                    } else {
                        pos = null;
                    }
                }
            }

            return pos != null && predicate.test(pos);
        }

        @Override
        public MBlockPos next() {
            isOld = true;
            return pos;
        }
    }

    private interface IBlockPosProvider {
        MBPIterator getFront();
        MBPIterator getFloor();
        MBPIterator getRailings(boolean mine);
        MBPIterator getLiquids();
        MBPIterator getEChestBlockade(boolean mine);
    }

    private class StraightBlockPosProvider implements IBlockPosProvider {
        private final MBlockPos pos = new MBlockPos();
        private final MBlockPos pos2 = new MBlockPos();

        @Override
        public MBPIterator getFront() {
            pos.set(mc.player).offset(dir).offset(leftDir, getWidthLeft());

            return new MBPIterator() {
                private int w, y;
                private int pw, py;

                @Override
                public boolean hasNext() {
                    return w < widthSetting.get() && y < heightSetting.get();
                }

                @Override
                public MBlockPos next() {
                    pos2.set(pos).offset(rightDir, w).add(0, y, 0);

                    w++;
                    if (w >= widthSetting.get()) {
                        w = 0;
                        y++;
                    }

                    return pos2;
                }

                @Override
                public void save() {
                    pw = w;
                    py = y;
                    w = y = 0;
                }

                @Override
                public void restore() {
                    w = pw;
                    y = py;
                }
            };
        }

        @Override
        public MBPIterator getFloor() {
            pos.set(mc.player).offset(dir).offset(leftDir, getWidthLeft()).add(0, -1, 0);

            return new MBPIterator() {
                private int w;
                private int pw;

                @Override
                public boolean hasNext() {
                    return w < widthSetting.get();
                }

                @Override
                public MBlockPos next() {
                    return pos2.set(pos).offset(rightDir, w++);
                }

                @Override
                public void save() {
                    pw = w;
                    w = 0;
                }

                @Override
                public void restore() {
                    w = pw;
                }
            };
        }

        @Override
        public MBPIterator getRailings(boolean mine) {
            boolean mineAll = mine && mineAboveRailingsSetting.get();
            pos.set(mc.player).offset(dir);

            return new MBPIterator() {
                private int i, y;
                private int pi, py;

                @Override
                public boolean hasNext() {
                    return i < 2 && y < (mineAll ? heightSetting.get() : 1);
                }

                @Override
                public MBlockPos next() {
                    if (i == 0) pos2.set(pos).offset(leftDir, getWidthLeft() + 1).add(0, y, 0);
                    else pos2.set(pos).offset(rightDir, getWidthRight() + 1).add(0, y, 0);

                    y++;
                    if (y >= (mineAll ? heightSetting.get() : 1)) {
                        y = 0;
                        i++;
                    }

                    return pos2;
                }

                @Override
                public void save() {
                    pi = i;
                    py = y;
                    i = y = 0;
                }

                @Override
                public void restore() {
                    i = pi;
                    y = py;
                }
            };
        }

        @Override
        public MBPIterator getLiquids() {
            pos.set(mc.player).offset(dir, 2).offset(leftDir, getWidthLeft() + (railingsSetting.get() && mineAboveRailingsSetting.get() ? 2 : 1));

            return new MBPIterator() {
                private int w, y;
                private int pw, py;

                private int getWidth() {
                    return widthSetting.get() + (railingsSetting.get() && mineAboveRailingsSetting.get() ? 2 : 0);
                }

                @Override
                public boolean hasNext() {
                    return w < getWidth() + 2 && y < heightSetting.get() + 1;
                }

                @Override
                public MBlockPos next() {
                    pos2.set(pos).offset(rightDir, w).add(0, y, 0);

                    w++;
                    if (w >= getWidth() + 2) {
                        w = 0;
                        y++;
                    }

                    return pos2;
                }

                @Override
                public void save() {
                    pw = w;
                    py = y;
                    w = y = 0;
                }

                @Override
                public void restore() {
                    w = pw;
                    y = py;
                }
            };
        }

        @Override
        public MBPIterator getEChestBlockade(boolean mine) {
            return new MBPIterator() {
                private int i = mine ? -1 : 0, y;
                private int pi, py;

                private MBlockPos get(int i) {
                    pos.set(mc.player).offset(dir.opposite());

                    return switch (i) {
                        case -1 -> pos;
                        default -> pos.offset(dir.opposite());
                        case 1 -> pos.offset(leftDir);
                        case 2 -> pos.offset(rightDir);
                    };
                }

                @Override
                public boolean hasNext() {
                    return i < 3 && y < 2;
                }

                @Override
                public MBlockPos next() {
                    if (widthSetting.get() == 1 && railingsSetting.get() && i > 0 && y == 0) {
                        y++;
                    }

                    MBlockPos pos = get(i).add(0, y, 0);

                    y++;
                    if (y > 1) {
                        y = 0;
                        i++;
                    }

                    return pos;
                }

                @Override
                public void save() {
                    pi = i;
                    py = y;
                    i = y = 0;
                }

                @Override
                public void restore() {
                    i = pi;
                    y = py;
                }
            };
        }
    }

    private class DiagonalBlockPosProvider implements IBlockPosProvider {
        private final MBlockPos pos = new MBlockPos();
        private final MBlockPos pos2 = new MBlockPos();

        @Override
        public MBPIterator getFront() {
            pos.set(mc.player).offset(dir.rotateLeft()).offset(leftDir, getWidthLeft() - 1);

            return new MBPIterator() {
                private int i, w, y;
                private int pi, pw, py;

                @Override
                public boolean hasNext() {
                    return i < 2 && w < widthSetting.get() && y < heightSetting.get();
                }

                @Override
                public MBlockPos next() {
                    pos2.set(pos).offset(rightDir, w).add(0, y++, 0);

                    if (y >= heightSetting.get()) {
                        y = 0;
                        w++;

                        if (w >= (i == 0 ? widthSetting.get() - 1 : widthSetting.get())) {
                            w = 0;
                            i++;

                            pos.set(mc.player).offset(dir).offset(leftDir, getWidthLeft());
                        }
                    }

                    return pos2;
                }

                private void initPos() {
                    if (i == 0) {
                        pos.set(mc.player).offset(dir.rotateLeft()).offset(leftDir, getWidthLeft() - 1);
                    } else {
                        pos.set(mc.player).offset(dir).offset(leftDir, getWidthLeft());
                    }
                }

                @Override
                public void save() {
                    pi = i;
                    pw = w;
                    py = y;
                    i = w = y = 0;

                    initPos();
                }

                @Override
                public void restore() {
                    i = pi;
                    w = pw;
                    y = py;

                    initPos();
                }
            };
        }

        @Override
        public MBPIterator getFloor() {
            pos.set(mc.player).add(0, -1, 0).offset(dir.rotateLeft()).offset(leftDir, getWidthLeft() - 1);

            return new MBPIterator() {
                private int i, w;
                private int pi, pw;

                @Override
                public boolean hasNext() {
                    return i < 2 && w < widthSetting.get();
                }

                @Override
                public MBlockPos next() {
                    pos2.set(pos).offset(rightDir, w++);

                    if (w >= (i == 0 ? widthSetting.get() - 1 : widthSetting.get())) {
                        w = 0;
                        i++;

                        pos.set(mc.player).add(0, -1, 0).offset(dir).offset(leftDir, getWidthLeft());
                    }

                    return pos2;
                }

                private void initPos() {
                    if (i == 0) {
                        pos.set(mc.player).add(0, -1, 0).offset(dir.rotateLeft()).offset(leftDir, getWidthLeft() - 1);
                    } else {
                        pos.set(mc.player).add(0, -1, 0).offset(dir).offset(leftDir, getWidthLeft());
                    }
                }

                @Override
                public void save() {
                    pi = i;
                    pw = w;
                    i = w = 0;

                    initPos();
                }

                @Override
                public void restore() {
                    i = pi;
                    w = pw;

                    initPos();
                }
            };
        }

        @Override
        public MBPIterator getRailings(boolean mine) {
            boolean mineAll = mine && mineAboveRailingsSetting.get();
            pos.set(mc.player).offset(dir.rotateLeft()).offset(leftDir, getWidthLeft());

            return new MBPIterator() {
                private int i, y;
                private int pi, py;

                @Override
                public boolean hasNext() {
                    return i < 2 && y < (mineAll ? heightSetting.get() : 1);
                }

                @Override
                public MBlockPos next() {
                    pos2.set(pos).add(0, y++, 0);

                    if (y >= (mineAll ? heightSetting.get() : 1)) {
                        y = 0;
                        i++;

                        pos.set(mc.player).offset(dir.rotateRight()).offset(rightDir, getWidthRight());
                    }

                    return pos2;
                }

                private void initPos() {
                    if (i == 0) {
                        pos.set(mc.player).offset(dir.rotateLeft()).offset(leftDir, getWidthLeft());
                    } else {
                        pos.set(mc.player).offset(dir.rotateRight()).offset(rightDir, getWidthRight());
                    }
                }

                @Override
                public void save() {
                    pi = i;
                    py = y;
                    i = y = 0;

                    initPos();
                }

                @Override
                public void restore() {
                    i = pi;
                    y = py;

                    initPos();
                }
            };
        }

        @Override
        public MBPIterator getLiquids() {
            boolean railings = railingsSetting.get() && mineAboveRailingsSetting.get();
            pos.set(mc.player).offset(dir).offset(dir.rotateLeft()).offset(leftDir, getWidthLeft());

            return new MBPIterator() {
                private int i, w, y;
                private int pi, pw, py;

                private int getWidth() {
                    return widthSetting.get() + (i == 0 ? 1 : 0) + (railings && i == 1 ? 2 : 0);
                }

                @Override
                public boolean hasNext() {
                    if (railings && i == 1 && y == heightSetting.get() &&  w == getWidth() - 1) {
                        return false;
                    }

                    return i < 2 && w < getWidth() && y < heightSetting.get() + 1;
                }

                private void updateW() {
                    w++;
                    if (w >= getWidth()) {
                        w = 0;
                        i++;

                        pos.set(mc.player).offset(dir, 2).offset(leftDir, getWidthLeft() + (railings ? 1 : 0));
                    }
                }

                @Override
                public MBlockPos next() {
                    if (i == (railings ? 1 : 0) && y == heightSetting.get() && (w == 0 || w == getWidth() - 1)) {
                        y = 0;
                        updateW();
                    }

                    pos2.set(pos).offset(rightDir, w).add(0, y++, 0);

                    if (y >= heightSetting.get() + 1) {
                        y = 0;
                        updateW();
                    }

                    return pos2;
                }

                private void initPos() {
                    if (i == 0) {
                        pos.set(mc.player).offset(dir).offset(dir.rotateLeft()).offset(leftDir, getWidthLeft());
                    } else {
                        pos.set(mc.player).offset(dir, 2).offset(leftDir, getWidthLeft() + (railings ? 1 : 0));
                    }
                }

                @Override
                public void save() {
                    pi = i;
                    pw = w;
                    py = y;
                    i = w = y = 0;

                    initPos();
                }

                @Override
                public void restore() {
                    i = pi;
                    w = pw;
                    y = py;

                    initPos();
                }
            };
        }

        @Override
        public MBPIterator getEChestBlockade(boolean mine) {
            return new MBPIterator() {
                private int i = mine ? -1 : 0, y;
                private int pi, py;

                private MBlockPos get(int i) {
                    HorizontalDirection dir2 = dir.rotateLeft().rotateLeftSkipOne();

                    pos.set(mc.player).offset(dir2);

                    return switch (i) {
                        case -1 -> pos;
                        default -> pos.offset(dir2);
                        case 1 -> pos.offset(dir2.rotateLeftSkipOne());
                        case 2 -> pos.offset(dir2.rotateLeftSkipOne().opposite());
                    };
                }

                @Override
                public boolean hasNext() {
                    return i < 3 && y < 2;
                }

                @Override
                public MBlockPos next() {
                    MBlockPos pos = get(i).add(0, y, 0);

                    y++;
                    if (y > 1) {
                        y = 0;
                        i++;
                    }

                    return pos;
                }

                @Override
                public void save() {
                    pi = i;
                    py = y;
                    i = y = 0;
                }

                @Override
                public void restore() {
                    i = pi;
                    y = py;
                }
            };
        }
    }

    public enum Floor {
        Replace("Replace"),
        Place_Missing("Place Missing");

        private final String name;

        Floor(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public enum Rotation {
        None("None", false, false),
        Mine("Mine", true, false),
        Place("Place", false, true),
        Both("Both", true, true);

        public final String name;

        public final boolean mine, place;

        Rotation(String name, boolean mine, boolean place) {
            this.name = name;
            this.mine = mine;
            this.place = place;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
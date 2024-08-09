package xyz.mathax.mathaxclient.systems.modules.player;

import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.entity.player.StartBreakingBlockEvent;
import xyz.mathax.mathaxclient.events.render.Render3DEvent;
import xyz.mathax.mathaxclient.events.world.TickEvent;
import xyz.mathax.mathaxclient.renderer.ShapeMode;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.utils.player.Rotations;
import xyz.mathax.mathaxclient.utils.render.color.Color;
import xyz.mathax.mathaxclient.utils.render.color.SettingColor;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import xyz.mathax.mathaxclient.settings.*;

public class InstaMine extends Module {
    private final BlockPos.Mutable blockPos = new BlockPos.Mutable(0, -1, 0);

    private Direction direction;

    private int ticks;

    private final SettingGroup generalSettings = settings.createGroup("General");
    private final SettingGroup renderSettings = settings.createGroup("Render");

    // General

    private final Setting<Integer> tickDelaySetting = generalSettings.add(new IntSetting.Builder()
            .name("Delay")
            .description("The delay between breaks in ticks.")
            .defaultValue(0)
            .min(0)
            .sliderRange(0, 20)
            .build()
    );

    private final Setting<Boolean> pickaxeSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Only pickaxe")
            .description("Only tries to mine the block if you are holding a pickaxe.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Boolean> rotateSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Rotate")
            .description("Faces the blocks being mined server side.")
            .defaultValue(true)
            .build()
    );

    // Render

    private final Setting<Boolean> renderSetting = renderSettings.add(new BoolSetting.Builder()
            .name("Enabled")
            .description("Render a block overlay on the block being broken.")
            .defaultValue(true)
            .build()
    );

    private final Setting<ShapeMode> shapeModeSetting = renderSettings.add(new EnumSetting.Builder<ShapeMode>()
            .name("Shape mode")
            .description("Determines how the shapes are rendered.")
            .defaultValue(ShapeMode.Both)
            .build()
    );

    private final Setting<SettingColor> sideColorSetting = renderSettings.add(new ColorSetting.Builder()
            .name("Side color")
            .description("The color of the sides of the blocks being rendered.")
            .defaultValue(new SettingColor(Color.MATHAX, 75))
            .build()
    );

    private final Setting<SettingColor> lineColorSetting = renderSettings.add(new ColorSetting.Builder()
            .name("Line color")
            .description("The color of the lines of the blocks being rendered.")
            .defaultValue(new SettingColor(Color.MATHAX))
            .build()
    );

    public InstaMine(Category category) {
        super(category, "Insta Mine", "Attempts to instantly mine blocks.");
    }

    @Override
    public void onEnable() {
        ticks = 0;
        blockPos.set(0, -1, 0);
    }

    @EventHandler
    private void onStartBreakingBlock(StartBreakingBlockEvent event) {
        direction = event.direction;
        blockPos.set(event.blockPos);
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (ticks >= tickDelaySetting.get()) {
            ticks = 0;

            if (shouldMine()) {
                if (rotateSetting.get()) {
                    Rotations.rotate(Rotations.getYaw(blockPos), Rotations.getPitch(blockPos), () -> mc.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, blockPos, direction)));
                } else {
                    mc.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, blockPos, direction));
                }

                mc.getNetworkHandler().sendPacket(new HandSwingC2SPacket(Hand.MAIN_HAND));
            }
        } else {
            ticks++;
        }
    }

    private boolean shouldMine() {
        if (blockPos.getY() == -1) {
            return false;
        }

        if (mc.world.getBlockState(blockPos).getHardness(mc.world, blockPos) < 0) {
            return false;
        }

        if (mc.world.getBlockState(blockPos).isAir()) {
            return false;
        }

        return !pickaxeSetting.get() || (mc.player.getMainHandStack().getItem() == Items.DIAMOND_PICKAXE || mc.player.getMainHandStack().getItem() == Items.NETHERITE_PICKAXE);
    }

    @EventHandler
    private void onRender3D(Render3DEvent event) {
        if (!renderSetting.get() || !shouldMine()) {
            return;
        }

        event.renderer.box(blockPos, sideColorSetting.get(), lineColorSetting.get(), shapeModeSetting.get(), 0);
    }
}
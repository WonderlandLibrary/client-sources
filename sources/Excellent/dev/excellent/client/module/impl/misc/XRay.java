package dev.excellent.client.module.impl.misc;

import dev.excellent.api.event.impl.other.WorldChangeEvent;
import dev.excellent.api.event.impl.other.WorldLoadEvent;
import dev.excellent.api.event.impl.player.MotionEvent;
import dev.excellent.api.event.impl.player.MoveInputEvent;
import dev.excellent.api.event.impl.render.Render2DEvent;
import dev.excellent.api.event.impl.render.Render3DLastEvent;
import dev.excellent.api.event.impl.server.PacketEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.client.module.impl.player.FreeCam;
import dev.excellent.impl.font.Font;
import dev.excellent.impl.font.Fonts;
import dev.excellent.impl.util.render.RenderUtil;
import dev.excellent.impl.util.render.color.ColorUtil;
import dev.excellent.impl.value.impl.BooleanValue;
import dev.excellent.impl.value.impl.MultiBooleanValue;
import dev.excellent.impl.value.impl.NumberValue;
import net.minecraft.block.*;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.client.*;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

@ModuleInfo(name = "XRay", description = "Позволяет увидеть руды сквозь блоки (HolyWorld).", category = Category.MISC)
public class XRay extends Module {

    public NumberValue delay = new NumberValue("Задержка", this, 20, 0, 40, 1);
    public NumberValue radius = new NumberValue("Радиус", this, 16, 0, 32, 1);
    public NumberValue up = new NumberValue("Вверх", this, 4, 0, 32, 1);
    public NumberValue down = new NumberValue("Вниз", this, 4, 0, 32, 1);
    public NumberValue skip = new NumberValue("Пропускать блоков", this, 2, 1, 6, 1);
    public MultiBooleanValue ores = new MultiBooleanValue("Руды", this)
            .add(
                    new BooleanValue("Уголь", false),
                    new BooleanValue("Алмаз", false),
                    new BooleanValue("Эмеральд", false),
                    new BooleanValue("Лазурит", false),
                    new BooleanValue("Редстоун", false),
                    new BooleanValue("Золото", false),
                    new BooleanValue("Железо", true),
                    new BooleanValue("Кварц", true),
                    new BooleanValue("Незерит", true)
            );
    private CopyOnWriteArrayList<BlockPos> waiting = new CopyOnWriteArrayList<>();
    private final CopyOnWriteArrayList<BlockPos> clicked = new CopyOnWriteArrayList<>();

    private BlockPos clicking;
    private Thread thread;

    @Override
    protected void onDisable() {
        super.onDisable();
        reset();
    }

    @Override
    protected void onEnable() {
        super.onEnable();
        reset();
        waiting = removeEveryOther(getBlocks(), skip.getValue().intValue());
        mineBlocks();
    }

    private final Listener<WorldChangeEvent> onWorldChange = event -> reset();
    private final Listener<WorldLoadEvent> onWorldLoad = event -> reset();

    private void reset() {
        if (thread != null) {
            thread.interrupt();
            thread.stop();
        }
        clicking = null;
        clicked.clear();
        waiting.clear();
    }

    private void mineBlocks() {
        thread = new Thread(() -> {
            for (BlockPos click : waiting) {
                mc.player.connection.sendPacket(new CPlayerDiggingPacket(CPlayerDiggingPacket.Action.ABORT_DESTROY_BLOCK, click, Direction.DOWN));

                clicked.add(click);
                clicking = click;
                try {
                    Thread.sleep(delay.getValue().intValue());
                } catch (InterruptedException e) {
                    System.out.println("Anti Xray: " + e.getMessage());
                }
            }
        });
        thread.start();
    }

    private int isValid(BlockPos pos) {
        BlockState state = mc.world.getBlockState(pos);
        Block block = state.getBlock();
        if (block instanceof OreBlock ore) {
            if (ore.equals(Blocks.COAL_ORE) && ores.isEnabled("Уголь")) {
                return ColorUtil.getColor(0, 0, 0, 255);
            }
            if (ore.equals(Blocks.DIAMOND_ORE) && ores.isEnabled("Алмаз")) {
                return ColorUtil.getColor(0, 140, 255, 255);
            }
            if (ore.equals(Blocks.EMERALD_ORE) && ores.isEnabled("Эмеральд")) {
                return ColorUtil.getColor(0, 255, 21, 255);
            }
            if (ore.equals(Blocks.LAPIS_ORE) && ores.isEnabled("Лазурит")) {
                return ColorUtil.getColor(0, 0, 255, 255);
            }
            if (ore.equals(Blocks.REDSTONE_ORE) && ores.isEnabled("Редстоун")) {
                return ColorUtil.getColor(255, 0, 0, 255);
            }
            if (ore.equals(Blocks.GOLD_ORE) || ore.equals(Blocks.NETHER_GOLD_ORE) && ores.isEnabled("Золото")) {
                return ColorUtil.getColor(255, 178, 0, 255);
            }
            if (ore.equals(Blocks.IRON_ORE) && ores.isEnabled("Железо")) {
                return ColorUtil.getColor(190, 190, 190, 255);
            }
            if (ore.equals(Blocks.NETHER_QUARTZ_ORE) && ores.isEnabled("Кварц")) {
                return ColorUtil.getColor(255, 255, 255, 255);
            }
            if (ore.equals(Blocks.ANCIENT_DEBRIS) && ores.isEnabled("Незерит")) {
                return ColorUtil.getColor(70, 20, 0, 255);
            }
        }
        return -1;
    }

    private final Listener<MoveInputEvent> onMoveInput = event -> {
        if (isActive() && !FreeCam.singleton.get().isEnabled()) {
            event.setStrafe(0);
            event.setForward(0);
            event.setJump(false);
            event.setSneaking(false);
        }
    };
    private final Listener<PacketEvent> onPacket = event -> {
        if (isActive()) {
            if (event.isSent()) {
                IPacket<?> packet = event.getPacket();
                if (packet instanceof CPlayerPacket
                        || packet instanceof CAnimateHandPacket
                        || packet instanceof CPlayerTryUseItemPacket
                        || packet instanceof CHeldItemChangePacket) {
                    event.cancel();
                }
            }
        }
    };
    private final Listener<MotionEvent> onMotion = event -> {
        if (isActive() && !FreeCam.singleton.get().isEnabled())
            event.cancel();

    };

    private boolean isActive() {
        return waiting.size() != clicked.size();
    }

    private final Listener<Render2DEvent> onRender2D = event -> {
        Font font = Fonts.MINECRAFT.get(12);

        double maxValue = waiting.size();
        double currentValue = clicked.size();

        int percentage;

        if (maxValue == 0) {
            percentage = 0;
        } else {
            percentage = (int) ((currentValue / maxValue) * 100);
        }

        long ms = (waiting.size() * delay.getValue().longValue()) - (clicked.size() * delay.getValue().longValue());

        String time = String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(ms) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(ms)),
                TimeUnit.MILLISECONDS.toSeconds(ms) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(ms)));

        String firstLineL = "XRay";
        String firstLineR = percentage + "%";

        String secondLineR = clicked.size() + "/" + waiting.size();
        String secondLineL = time + " ";

        float margin = 4;

        float firstWidth = font.getWidth(firstLineL + firstLineR);
        float secondWidth = font.getWidth(secondLineL + secondLineR);

        float width = Math.max((margin + Math.max(firstWidth, secondWidth) + margin) + 10, 80);
        float height = margin + font.getHeight() + (margin / 2F) + font.getHeight() + margin;

        float x = (float) ((scaled().x / 2F) - (width / 2F));
        float y = 5;

        float firstY = y + margin;
        float secondY = y + margin + font.getHeight() + (margin / 2F);

        RenderUtil.renderClientRect(event.getMatrix(), x, y, width, height, false, 0);

        int textColor = ColorUtil.getColor(255, 255, 255);

        font.drawOutline(event.getMatrix(), firstLineL, x + margin, firstY, textColor);
        font.drawRightOutline(event.getMatrix(), firstLineR, x + width - margin, firstY, textColor);

        font.drawOutline(event.getMatrix(), secondLineL, x + margin, secondY, textColor);
        font.drawRightOutline(event.getMatrix(), secondLineR, x + width - margin, secondY, textColor);
    };
    private final Listener<Render3DLastEvent> onRender3D = event -> {
        if (clicking != null) {
            if (isActive()) {
                RenderUtil.Render3D.drawBlockBox(event.getMatrix(), clicking, getTheme().getClientColor(0, 1F));
            }
            for (BlockPos click : clicked) {
                int color = isValid(click);
                if (color != -1) {
                    RenderUtil.Render3D.drawBlockBox(event.getMatrix(), click, color);
                }
            }
        }
    };

    private <T> CopyOnWriteArrayList<T> removeEveryOther(CopyOnWriteArrayList<T> inList, int offset) {
        if (offset == 1) return inList;
        CopyOnWriteArrayList<T> outList = new CopyOnWriteArrayList<>();
        //noinspection unchecked
        T[] ts = (T[]) inList.toArray();
        for (int i = 0; i < ts.length; i++) {
            if (i % offset == 0) {
                outList.add(ts[i]);
            }
        }
        return outList;
    }

    private CopyOnWriteArrayList<BlockPos> getBlocks() {
        CopyOnWriteArrayList<BlockPos> blocks = new CopyOnWriteArrayList<>();
        BlockPos start = mc.player.getPosition();
        int radius = this.radius.getValue().intValue();
        int up = this.up.getValue().intValue();
        int down = this.down.getValue().intValue();
        for (int y = up; y >= -down; y--)
            for (int x = radius; x >= -radius; x--)
                for (int z = radius; z >= -radius; z--) {
                    BlockPos pos = start.add(x, y, z);
                    if (pos.getY() > 0) {
                        Block block = mc.world.getBlockState(pos).getBlock();
                        if (block instanceof AirBlock) continue;
                        blocks.add(pos);
                    }

                }
        Collections.shuffle(blocks);
        return blocks;
    }

}

package im.expensive.functions.impl.render;

import com.google.common.eventbus.Subscribe;
import im.expensive.events.EventDisplay;
import im.expensive.events.EventMotion;
import im.expensive.events.EventPacket;
import im.expensive.events.WorldEvent;
import im.expensive.functions.api.Category;
import im.expensive.functions.api.Function;
import im.expensive.functions.api.FunctionRegister;
import im.expensive.functions.settings.impl.BooleanSetting;
import im.expensive.functions.settings.impl.ModeListSetting;
import im.expensive.functions.settings.impl.SliderSetting;
import im.expensive.ui.dropdown.components.settings.MultiBoxComponent;
import im.expensive.utils.render.ColorUtils;
import im.expensive.utils.render.DisplayUtils;
import im.expensive.utils.render.font.Fonts;
import net.minecraft.block.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.optifine.render.RenderUtils;

import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

@FunctionRegister(name = "Xray Bypass", type = Category.Render)
public class XrayBypass extends Function {
    CopyOnWriteArrayList<BlockPos> waiting = new CopyOnWriteArrayList<>();

    public SliderSetting up = new SliderSetting("Вверх", 5, 0, 30, 1);

    public SliderSetting down = new SliderSetting("Вниз", 5, 0, 30, 1);
    public SliderSetting radius = new SliderSetting("Радиус", 20, 0, 30, 1);
    public SliderSetting delay = new SliderSetting("Задержка", 13, 0, 40, 1);
    public SliderSetting skip = new SliderSetting("Пропуск", 3, 1, 5, 1);


    public ModeListSetting ores = new ModeListSetting("Искать",
            new BooleanSetting("Уголь", false),
            new BooleanSetting("Железо", false),
            new BooleanSetting("Редстоун", false),
            new BooleanSetting("Золото", false),
            new BooleanSetting("Эмеральды", false),
            new BooleanSetting("Алмазы", false),
            new BooleanSetting("Незерит", false)
    );

    public XrayBypass() {
        addSettings(radius, up, down, delay, skip, ores);
    }

    CopyOnWriteArrayList<BlockPos> clicked = new CopyOnWriteArrayList<>();

    BlockPos clicking;
    Thread thread;

    @Override
    public void onDisable() {
        super.onDisable();
        if (thread != null) {
            thread.interrupt();
            thread.stop();
        }
        clicking = null;
        clicked.clear();
        waiting.clear();
    }

    @Override
    public void onEnable() {
        super.onEnable();
        waiting = removeEveryOther(getBlocks(), skip.get().intValue());
        thread = new Thread(() -> {
            if (mc.player != null) {
                for (BlockPos click : waiting) {
                    mc.player.connection.sendPacket(new CPlayerDiggingPacket(CPlayerDiggingPacket.Action.START_DESTROY_BLOCK, click, Direction.UP));
                    clicked.add(click);
                    clicking = click;
                    try {
                        Thread.sleep(delay.get().intValue());
                    } catch (InterruptedException e) {
                        System.out.println("Anti Xray: " + e.getMessage());
                    }
                }
            }
        });
        thread.start();
    }

    private int isValid(BlockPos pos) {

        BlockState state = mc.world.getBlockState(pos);
        Block block = state.getBlock();

        if (block instanceof OreBlock ore) {
            if (ore == Blocks.COAL_ORE && ores.get(0).get()) {
                return ColorUtils.rgba(12, 12, 12, 255);
            }
            if (ore == Blocks.IRON_ORE && ores.get(1).get()) {
                return ColorUtils.rgba(122, 122, 122, 255);
            }
            if (ore == Blocks.REDSTONE_ORE && ores.get(2).get()) {
                return ColorUtils.rgba(255, 82, 82, 255);
            }
            if (ore == Blocks.GOLD_ORE && ores.get(3).get()) {
                return ColorUtils.rgba(247, 255, 102, 255);
            }
            if (ore == Blocks.EMERALD_ORE && ores.get(4).get()) {
                return ColorUtils.rgba(116, 252, 101, 255);
            }
            if (ore == Blocks.DIAMOND_ORE && ores.get(5).get()) {
                return ColorUtils.rgba(77, 219, 255, 255);
            }
            if (ore == Blocks.ANCIENT_DEBRIS && ores.get(6).get()) {
                return ColorUtils.rgba(105, 60, 12, 255);
            }
        }
        return -1;
    }

    @Subscribe
    public void onPacket(EventPacket e) {
        if (thread != null) {
            if (thread.isAlive()) {
                if (e.isSend()) {
                    if (e.getPacket() instanceof CPlayerPacket) {
                        e.cancel();
                    }
                    if (e.getPacket() instanceof CAnimateHandPacket) {
                        e.cancel();
                    }
                    if (e.getPacket() instanceof CPlayerTryUseItemPacket) {
                        e.cancel();
                    }
                    if (e.getPacket() instanceof CHeldItemChangePacket) {
                        e.cancel();
                    }
                }
            }
        }
    }

    @Subscribe
    public void onMotion(EventMotion e) {

        if (thread != null) {
            if (thread.isAlive()) {
                e.cancel();
            }
        }

    }

    @Subscribe
    public void onDisplay(EventDisplay e) {

        float width = 100;
        float heigth = 15;

        DisplayUtils.drawRoundedRect(mc.getMainWindow().getScaledWidth() / 2f - width / 2, 10, width, heigth, 4, ColorUtils.rgba(10, 10, 10, 128));
        float x = mc.getMainWindow().getScaledWidth() / 2f - width / 2;
        Fonts.montserrat.drawText(e.getMatrixStack(), clicked.size() + "/" + waiting.size(), x + 5, 15, -1, 8);

        long millis = ((long) waiting.size() * delay.get().intValue()) - clicked.size() * delay.get().intValue();

        String time = String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), // The change is in this line
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));


        Fonts.montserrat.drawText(e.getMatrixStack(), time, x + width - Fonts.montserrat.getWidth(time, 8) - 5, 15, -1, 8);


    }

    @Subscribe
    public void onWorld(WorldEvent e) {
        if (clicked != null && clicking != null) {
            RenderUtils.drawBlockBox(clicking, ColorUtils.rgba(83, 252, 154, 255));
            for (BlockPos click : clicked) {
                int color = isValid(click);
                if (color != -1) {
                    RenderUtils.drawBlockBox(click, color);
                }
            }
        }
    }


    private static <T> CopyOnWriteArrayList<T> removeEveryOther(CopyOnWriteArrayList<T> inList, int offset) {
        if (offset == 1) return inList;
        CopyOnWriteArrayList<T> outList = new CopyOnWriteArrayList<>();
        @SuppressWarnings("unchecked")
        T[] ts = (T[]) inList.toArray();
        for (int i = 0; i < ts.length; i++) {
            if (i % offset == 0) {
                outList.add(ts[i]);
            }
        }
        return outList;
    }

    CopyOnWriteArrayList<BlockPos> getBlocks() {
        CopyOnWriteArrayList<BlockPos> blocks = new CopyOnWriteArrayList<>();
        BlockPos start = mc.player.getPosition();
        int dis = radius.get().intValue();
        int up = this.up.get().intValue();
        int down = this.down.get().intValue();
        for (int y = up; y >= -down; y--)
            for (int x = dis; x >= -dis; x--)

                for (int z = dis; z >= -dis; z--) {
                    BlockPos pos = start.add(x, y, z);

                    if (pos.getY() > 0) {
                        Block block = mc.world.getBlockState(pos).getBlock();
                        if (block instanceof AirBlock) continue;
                        blocks.add(pos);
                    }

                }
        return blocks;
    }
}

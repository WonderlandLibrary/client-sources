package fun.ellant.functions.impl.player;

import com.google.common.eventbus.Subscribe;
import fun.ellant.events.EventDisplay;
import fun.ellant.events.EventMotion;
import fun.ellant.events.EventPacket;
import fun.ellant.events.WorldEvent;
import fun.ellant.functions.api.Category;
import fun.ellant.functions.api.Function;
import fun.ellant.functions.api.FunctionRegister;
import fun.ellant.functions.settings.impl.BooleanSetting;
import fun.ellant.functions.settings.impl.ModeListSetting;
import fun.ellant.functions.settings.impl.ModeSetting;
import fun.ellant.functions.settings.impl.SliderSetting;
import fun.ellant.utils.render.ColorUtils;
import fun.ellant.utils.render.DisplayUtils;
import fun.ellant.utils.render.font.Fonts;
import net.minecraft.block.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.optifine.render.RenderUtils;
import org.lwjgl.opengl.GL11;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

@FunctionRegister(name = "Xray Bypass", type = Category.PLAYER, desc = "Пон?")
public class XrayBypass extends Function {
    CopyOnWriteArrayList<BlockPos> waiting = new CopyOnWriteArrayList<>();

    public SliderSetting up = new SliderSetting("Вверх", 5, 0, 30, 1);

    public SliderSetting down = new SliderSetting("Вниз", 5, 0, 30, 1);
    public SliderSetting radius = new SliderSetting("Радиус", 20, 0, 30, 1);
    public SliderSetting delay = new SliderSetting("Задержка", 13, 0, 40, 1);
    public SliderSetting skip = new SliderSetting("Пропуск", 3, 1, 5, 1);
    final ModeSetting mode = new ModeSetting("Режим", "Пакетный", "Обычный", "Пакетный");

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
        addSettings(mode, radius, up, down, delay, skip, ores);
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
    public boolean onEnable() {
        super.onEnable();
        if (mode.is("Обычный")) {
            if (clicked != null && clicking != null) {
                RenderUtils.drawBlockBox(clicking, ColorUtils.rgba(83, 252, 154, 255));
                for (BlockPos click : clicked) {
                    int color = isValid(click);
                    if (color != -1) {
                        // Устанавливаем прозрачность текстуры перед рендерингом блока
                        GL11.glEnable(GL11.GL_BLEND);
                        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                        GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.3f); // Прозрачность текстуры
                        RenderUtils.drawBlockBox(click, color);
                        GL11.glDisable(GL11.GL_BLEND); // Выключаем режим прозрачности после рендеринга
                    }
                }
            }
        } else if (mode.is("Пакетный")) {
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
        return false;
    }
    CopyOnWriteArrayList<BlockPos> getAllBlocksInRadius() {
        CopyOnWriteArrayList<BlockPos> blocks = new CopyOnWriteArrayList<>();
        BlockPos playerPos = mc.player.getPosition();
        int radius = this.radius.get().intValue();

        for (int x = playerPos.getX() - radius; x <= playerPos.getX() + radius; x++) {
            for (int z = playerPos.getZ() - radius; z <= playerPos.getZ() + radius; z++) {
                for (int y = 0; y < 256; y++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    BlockState state = mc.world.getBlockState(pos);
                    Block block = state.getBlock();
                    if (!(block instanceof AirBlock)) {
                        blocks.add(pos);
                    }
                }
            }
        }
        return blocks;
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

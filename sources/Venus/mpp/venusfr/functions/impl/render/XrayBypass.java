/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.render;

import com.google.common.eventbus.Subscribe;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import mpp.venusfr.events.EventDisplay;
import mpp.venusfr.events.EventMotion;
import mpp.venusfr.events.EventPacket;
import mpp.venusfr.events.WorldEvent;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import mpp.venusfr.functions.settings.impl.BooleanSetting;
import mpp.venusfr.functions.settings.impl.ModeListSetting;
import mpp.venusfr.functions.settings.impl.SliderSetting;
import mpp.venusfr.utils.render.ColorUtils;
import mpp.venusfr.utils.render.DisplayUtils;
import mpp.venusfr.utils.render.font.Fonts;
import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.OreBlock;
import net.minecraft.network.play.client.CAnimateHandPacket;
import net.minecraft.network.play.client.CHeldItemChangePacket;
import net.minecraft.network.play.client.CPlayerDiggingPacket;
import net.minecraft.network.play.client.CPlayerPacket;
import net.minecraft.network.play.client.CPlayerTryUseItemPacket;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.optifine.render.RenderUtils;

@FunctionRegister(name="Xray Bypass", type=Category.Visual)
public class XrayBypass
extends Function {
    CopyOnWriteArrayList<BlockPos> waiting = new CopyOnWriteArrayList();
    public SliderSetting up = new SliderSetting("\u0412\u0432\u0435\u0440\u0445", 5.0f, 0.0f, 30.0f, 1.0f);
    public SliderSetting down = new SliderSetting("\u0412\u043d\u0438\u0437", 5.0f, 0.0f, 30.0f, 1.0f);
    public SliderSetting radius = new SliderSetting("\u0420\u0430\u0434\u0438\u0443\u0441", 20.0f, 0.0f, 30.0f, 1.0f);
    public SliderSetting delay = new SliderSetting("\u0417\u0430\u0434\u0435\u0440\u0436\u043a\u0430", 13.0f, 0.0f, 40.0f, 1.0f);
    public SliderSetting skip = new SliderSetting("\u041f\u0440\u043e\u043f\u0443\u0441\u043a", 3.0f, 1.0f, 5.0f, 1.0f);
    public ModeListSetting ores = new ModeListSetting("\u0418\u0441\u043a\u0430\u0442\u044c", new BooleanSetting("\u0423\u0433\u043e\u043b\u044c", false), new BooleanSetting("\u0416\u0435\u043b\u0435\u0437\u043e", false), new BooleanSetting("\u0420\u0435\u0434\u0441\u0442\u043e\u0443\u043d", false), new BooleanSetting("\u0417\u043e\u043b\u043e\u0442\u043e", false), new BooleanSetting("\u042d\u043c\u0435\u0440\u0430\u043b\u044c\u0434\u044b", false), new BooleanSetting("\u0410\u043b\u043c\u0430\u0437\u044b", false), new BooleanSetting("\u041d\u0435\u0437\u0435\u0440\u0438\u0442", false));
    CopyOnWriteArrayList<BlockPos> clicked = new CopyOnWriteArrayList();
    BlockPos clicking;
    Thread thread;

    public XrayBypass() {
        this.addSettings(this.radius, this.up, this.down, this.delay, this.skip, this.ores);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        if (this.thread != null) {
            this.thread.interrupt();
            this.thread.stop();
        }
        this.clicking = null;
        this.clicked.clear();
        this.waiting.clear();
    }

    @Override
    public void onEnable() {
        super.onEnable();
        this.waiting = XrayBypass.removeEveryOther(this.getBlocks(), ((Float)this.skip.get()).intValue());
        this.thread = new Thread(this::lambda$onEnable$0);
        this.thread.start();
    }

    private int isValid(BlockPos blockPos) {
        BlockState blockState = XrayBypass.mc.world.getBlockState(blockPos);
        Block block = blockState.getBlock();
        if (block instanceof OreBlock) {
            OreBlock oreBlock = (OreBlock)block;
            if (oreBlock == Blocks.COAL_ORE && ((Boolean)this.ores.get(0).get()).booleanValue()) {
                return ColorUtils.rgba(12, 12, 12, 255);
            }
            if (oreBlock == Blocks.IRON_ORE && ((Boolean)this.ores.get(1).get()).booleanValue()) {
                return ColorUtils.rgba(122, 122, 122, 255);
            }
            if (oreBlock == Blocks.REDSTONE_ORE && ((Boolean)this.ores.get(2).get()).booleanValue()) {
                return ColorUtils.rgba(255, 82, 82, 255);
            }
            if (oreBlock == Blocks.GOLD_ORE && ((Boolean)this.ores.get(3).get()).booleanValue()) {
                return ColorUtils.rgba(247, 255, 102, 255);
            }
            if (oreBlock == Blocks.EMERALD_ORE && ((Boolean)this.ores.get(4).get()).booleanValue()) {
                return ColorUtils.rgba(116, 252, 101, 255);
            }
            if (oreBlock == Blocks.DIAMOND_ORE && ((Boolean)this.ores.get(5).get()).booleanValue()) {
                return ColorUtils.rgba(77, 219, 255, 255);
            }
            if (oreBlock == Blocks.ANCIENT_DEBRIS && ((Boolean)this.ores.get(6).get()).booleanValue()) {
                return ColorUtils.rgba(105, 60, 12, 255);
            }
        }
        return 1;
    }

    @Subscribe
    public void onPacket(EventPacket eventPacket) {
        if (this.thread != null && this.thread.isAlive() && eventPacket.isSend()) {
            if (eventPacket.getPacket() instanceof CPlayerPacket) {
                eventPacket.cancel();
            }
            if (eventPacket.getPacket() instanceof CAnimateHandPacket) {
                eventPacket.cancel();
            }
            if (eventPacket.getPacket() instanceof CPlayerTryUseItemPacket) {
                eventPacket.cancel();
            }
            if (eventPacket.getPacket() instanceof CHeldItemChangePacket) {
                eventPacket.cancel();
            }
        }
    }

    @Subscribe
    public void onMotion(EventMotion eventMotion) {
        if (this.thread != null && this.thread.isAlive()) {
            eventMotion.cancel();
        }
    }

    @Subscribe
    public void onDisplay(EventDisplay eventDisplay) {
        float f = 100.0f;
        float f2 = 15.0f;
        DisplayUtils.drawRoundedRect((float)mc.getMainWindow().getScaledWidth() / 2.0f - f / 2.0f, 10.0f, f, f2, 4.0f, ColorUtils.rgba(10, 10, 10, 128));
        float f3 = (float)mc.getMainWindow().getScaledWidth() / 2.0f - f / 2.0f;
        Fonts.montserrat.drawText(eventDisplay.getMatrixStack(), this.clicked.size() + "/" + this.waiting.size(), f3 + 5.0f, 15.0f, -1, 8.0f);
        long l = (long)this.waiting.size() * (long)((Float)this.delay.get()).intValue() - (long)(this.clicked.size() * ((Float)this.delay.get()).intValue());
        String string = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(l) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(l)), TimeUnit.MILLISECONDS.toSeconds(l) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(l)));
        Fonts.montserrat.drawText(eventDisplay.getMatrixStack(), string, f3 + f - Fonts.montserrat.getWidth(string, 8.0f) - 5.0f, 15.0f, -1, 8.0f);
    }

    @Subscribe
    public void onWorld(WorldEvent worldEvent) {
        if (this.clicked != null && this.clicking != null) {
            RenderUtils.drawBlockBox(this.clicking, ColorUtils.rgba(83, 252, 154, 255));
            for (BlockPos blockPos : this.clicked) {
                int n = this.isValid(blockPos);
                if (n == -1) continue;
                RenderUtils.drawBlockBox(blockPos, n);
            }
        }
    }

    private static <T> CopyOnWriteArrayList<T> removeEveryOther(CopyOnWriteArrayList<T> copyOnWriteArrayList, int n) {
        if (n == 1) {
            return copyOnWriteArrayList;
        }
        CopyOnWriteArrayList<Object> copyOnWriteArrayList2 = new CopyOnWriteArrayList<Object>();
        Object[] objectArray = copyOnWriteArrayList.toArray();
        for (int i = 0; i < objectArray.length; ++i) {
            if (i % n != 0) continue;
            copyOnWriteArrayList2.add(objectArray[i]);
        }
        return copyOnWriteArrayList2;
    }

    CopyOnWriteArrayList<BlockPos> getBlocks() {
        CopyOnWriteArrayList<BlockPos> copyOnWriteArrayList = new CopyOnWriteArrayList<BlockPos>();
        BlockPos blockPos = XrayBypass.mc.player.getPosition();
        int n = ((Float)this.radius.get()).intValue();
        int n2 = ((Float)this.up.get()).intValue();
        int n3 = ((Float)this.down.get()).intValue();
        for (int i = n2; i >= -n3; --i) {
            for (int j = n; j >= -n; --j) {
                for (int k = n; k >= -n; --k) {
                    Block block;
                    BlockPos blockPos2 = blockPos.add(j, i, k);
                    if (blockPos2.getY() <= 0 || (block = XrayBypass.mc.world.getBlockState(blockPos2).getBlock()) instanceof AirBlock) continue;
                    copyOnWriteArrayList.add(blockPos2);
                }
            }
        }
        return copyOnWriteArrayList;
    }

    private void lambda$onEnable$0() {
        if (XrayBypass.mc.player != null) {
            for (BlockPos blockPos : this.waiting) {
                XrayBypass.mc.player.connection.sendPacket(new CPlayerDiggingPacket(CPlayerDiggingPacket.Action.START_DESTROY_BLOCK, blockPos, Direction.UP));
                this.clicked.add(blockPos);
                this.clicking = blockPos;
                try {
                    Thread.sleep(((Float)this.delay.get()).intValue());
                } catch (InterruptedException interruptedException) {
                    System.out.println("Anti Xray: " + interruptedException.getMessage());
                }
            }
        }
    }
}


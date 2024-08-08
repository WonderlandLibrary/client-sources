package me.zeroeightsix.kami.module.modules.render;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import me.zeroeightsix.kami.event.events.PacketEvent;
import me.zeroeightsix.kami.event.events.RenderEvent;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.EntityUtil;
import me.zeroeightsix.kami.util.KamiTessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;


@Module.Info(name="HoleEsp", category=Module.Category.RENDER)
public class HoleESP
        extends Module {
    private Setting<Double> range = this.register(Settings.d("Range", 8.0));
    private Setting<Boolean> highlight = this.register(Settings.b("Block Highlight", true));
    private Setting<Boolean> box = this.register(Settings.b("Bouding Box", true));
    private Setting<Boolean> bottom = this.register(Settings.b("Bottom Highlight", false));
    private Setting<Boolean> bottomBox = this.register(Settings.b("Bottom Boudning Box", false));
    private Setting<Double> chromaSpeed = this.register(Settings.d("Chroma Speed", 3.0));
    private Setting<Boolean> obbyChroma = this.register(Settings.b("Obbi Chroma", false));
    private Setting<Integer> obbyRed = this.register(Settings.integerBuilder("Obbi Red").withMinimum(0).withMaximum(255).withValue(143).build());
    private Setting<Integer> obbyGreen = this.register(Settings.integerBuilder("Obbi Green").withMinimum(0).withMaximum(255).withValue(0).build());
    private Setting<Integer> obbyBlue = this.register(Settings.integerBuilder("Obbi Blue").withMinimum(0).withMaximum(255).withValue(0).build());
    private Setting<Boolean> bRockChroma = this.register(Settings.b("BRock Chroma", false));
    private Setting<Integer> bRockRed = this.register(Settings.integerBuilder("BRock Red").withMinimum(0).withMaximum(255).withValue(0).build());
    private Setting<Integer> bRockGreen = this.register(Settings.integerBuilder("BRock Green").withMinimum(0).withMaximum(255).withValue(255).build());
    private Setting<Integer> bRockBlue = this.register(Settings.integerBuilder("BRock Blue").withMinimum(0).withMaximum(255).withValue(0).build());
    private Setting<Integer> alpha = this.register(Settings.integerBuilder("Alpha").withMinimum(0).withMaximum(255).withValue(26).build());
    private Setting<Integer> alpha2 = this.register(Settings.integerBuilder("Bounding Box Alpha").withMinimum(0).withMaximum(255).withValue(255).build());
    private Setting<Float> width = this.register(Settings.floatBuilder("Line Width").withMinimum(Float.valueOf(0.0f)).withMaximum(Float.valueOf(7.0f)).withValue(Float.valueOf(1.04f)).build());
    private BlockPos render;
    private static boolean isSpoofingAngles;
    private static double yaw;
    private static double pitch;
    @EventHandler
    private Listener<PacketEvent.Send> packetListener = new Listener<PacketEvent.Send>(event -> {
        Packet packet = event.getPacket();
        if (packet instanceof CPacketPlayer && isSpoofingAngles) {
            ((CPacketPlayer)packet).yaw = (float)yaw;
            ((CPacketPlayer)packet).pitch = (float)pitch;
        }
    }, new Predicate[0]);

    @Override
    public void onUpdate() {
        BlockPos blockPos;
        List<BlockPos> bRockHoles = this.findBRockHoles();
        List<BlockPos> obbyHoles = this.findObbyHoles();
        BlockPos q = null;
        Iterator<BlockPos> iterator = bRockHoles.iterator();
        while (iterator.hasNext()) {
            q = blockPos = iterator.next();
        }
        iterator = obbyHoles.iterator();
        while (iterator.hasNext()) {
            q = blockPos = iterator.next();
        }
        this.render = q;
    }

    @Override
    public void onWorldRender(RenderEvent event) {
        float[] hue = new float[]{(float)(System.currentTimeMillis() % 11520L) / 11520.0f * this.chromaSpeed.getValue().floatValue()};
        int rgb = Color.HSBtoRGB(hue[0], 1.0f, 1.0f);
        int red = rgb >> 16 & 255;
        int green = rgb >> 8 & 255;
        int blue = rgb & 255;
        GL11.glEnable((int)2884);
        if (this.render != null) {
            for (BlockPos hole : this.findObbyHoles()) {
                if (this.obbyChroma.getValue().booleanValue()) {
                    if (this.highlight.getValue().booleanValue()) {
                        KamiTessellator.prepare(7);
                        KamiTessellator.drawBox(hole, red, green, blue, this.alpha.getValue(), 63);
                        KamiTessellator.release();
                    }
                    if (this.box.getValue().booleanValue()) {
                        KamiTessellator.prepare(7);
                        KamiTessellator.drawBoundingBoxBlockPos(hole, this.width.getValue().floatValue(), red, green, blue, this.alpha2.getValue());
                        KamiTessellator.release();
                    }
                    if (this.bottom.getValue().booleanValue()) {
                        KamiTessellator.prepare(7);
                        KamiTessellator.drawBoxBottom(hole, red, green, blue, this.alpha.getValue());
                        KamiTessellator.release();
                    }
                    if (!this.bottomBox.getValue().booleanValue()) continue;
                    KamiTessellator.prepare(7);
                    KamiTessellator.drawBoundingBoxBottomBlockPos(hole, this.width.getValue().floatValue(), red, green, blue, this.alpha2.getValue());
                    KamiTessellator.release();
                    continue;
                }
                if (this.highlight.getValue().booleanValue()) {
                    KamiTessellator.prepare(7);
                    KamiTessellator.drawBox(hole, this.obbyRed.getValue(), this.obbyGreen.getValue(), this.obbyBlue.getValue(), this.alpha.getValue(), 63);
                    KamiTessellator.release();
                }
                if (this.box.getValue().booleanValue()) {
                    KamiTessellator.prepare(7);
                    KamiTessellator.drawBoundingBoxBlockPos(hole, this.width.getValue().floatValue(), this.obbyRed.getValue(), this.obbyGreen.getValue(), this.obbyBlue.getValue(), this.alpha2.getValue());
                    KamiTessellator.release();
                }
                if (this.bottom.getValue().booleanValue()) {
                    KamiTessellator.prepare(7);
                    KamiTessellator.drawBoxBottom(hole, this.obbyRed.getValue(), this.obbyGreen.getValue(), this.obbyBlue.getValue(), this.alpha.getValue());
                    KamiTessellator.release();
                }
                if (!this.bottomBox.getValue().booleanValue()) continue;
                KamiTessellator.prepare(7);
                KamiTessellator.drawBoundingBoxBottomBlockPos(hole, this.width.getValue().floatValue(), this.obbyRed.getValue(), this.obbyGreen.getValue(), this.obbyBlue.getValue(), this.alpha2.getValue());
                KamiTessellator.release();
            }
            for (BlockPos hole : this.findBRockHoles()) {
                if (this.bRockChroma.getValue().booleanValue()) {
                    if (this.highlight.getValue().booleanValue()) {
                        KamiTessellator.prepare(7);
                        KamiTessellator.drawBox(hole, red, green, blue, this.alpha.getValue(), 63);
                        KamiTessellator.release();
                    }
                    if (this.box.getValue().booleanValue()) {
                        KamiTessellator.prepare(7);
                        KamiTessellator.drawBoundingBoxBlockPos(hole, this.width.getValue().floatValue(), red, green, blue, this.alpha2.getValue());
                        KamiTessellator.release();
                    }
                    if (this.bottom.getValue().booleanValue()) {
                        KamiTessellator.prepare(7);
                        KamiTessellator.drawBoxBottom(hole, red, green, blue, this.alpha.getValue());
                        KamiTessellator.release();
                    }
                    if (!this.bottomBox.getValue().booleanValue()) continue;
                    KamiTessellator.prepare(7);
                    KamiTessellator.drawBoundingBoxBottomBlockPos(hole, this.width.getValue().floatValue(), red, green, blue, this.alpha2.getValue());
                    KamiTessellator.release();
                    continue;
                }
                if (this.highlight.getValue().booleanValue()) {
                    KamiTessellator.prepare(7);
                    KamiTessellator.drawBox(hole, this.bRockRed.getValue(), this.bRockGreen.getValue(), this.bRockBlue.getValue(), this.alpha.getValue(), 63);
                    KamiTessellator.release();
                }
                if (this.box.getValue().booleanValue()) {
                    KamiTessellator.prepare(7);
                    KamiTessellator.drawBoundingBoxBlockPos(hole, this.width.getValue().floatValue(), this.bRockRed.getValue(), this.bRockGreen.getValue(), this.bRockBlue.getValue(), this.alpha2.getValue());
                    KamiTessellator.release();
                }
                if (this.bottom.getValue().booleanValue()) {
                    KamiTessellator.prepare(7);
                    KamiTessellator.drawBoxBottom(hole, this.bRockRed.getValue(), this.bRockGreen.getValue(), this.bRockBlue.getValue(), this.alpha.getValue());
                    KamiTessellator.release();
                }
                if (!this.bottomBox.getValue().booleanValue()) continue;
                KamiTessellator.prepare(7);
                KamiTessellator.drawBoundingBoxBottomBlockPos(hole, this.width.getValue().floatValue(), this.bRockRed.getValue(), this.bRockGreen.getValue(), this.bRockBlue.getValue(), this.alpha2.getValue());
                KamiTessellator.release();
            }
        }
    }

    private void lookAtPacket(double px, double py, double pz, EntityPlayer me) {
        double[] v = EntityUtil.calculateLookAt(px, py, pz, me);
        HoleESP.setYawAndPitch((float)v[0], (float)v[1]);
    }

    private boolean IsObbyHole(BlockPos blockPos) {
        BlockPos boost = blockPos.add(0, 1, 0);
        BlockPos boost2 = blockPos.add(0, 0, 0);
        BlockPos boost3 = blockPos.add(0, 0, -1);
        BlockPos boost4 = blockPos.add(1, 0, 0);
        BlockPos boost5 = blockPos.add(-1, 0, 0);
        BlockPos boost6 = blockPos.add(0, 0, 1);
        BlockPos boost7 = blockPos.add(0, 2, 0);
        BlockPos boost8 = blockPos.add(0.5, 0.5, 0.5);
        BlockPos boost9 = blockPos.add(0, -1, 0);
        return !(HoleESP.mc.world.getBlockState(boost).getBlock() != Blocks.AIR || this.IsBRockHole(blockPos) || HoleESP.mc.world.getBlockState(boost2).getBlock() != Blocks.AIR || HoleESP.mc.world.getBlockState(boost7).getBlock() != Blocks.AIR || HoleESP.mc.world.getBlockState(boost3).getBlock() != Blocks.OBSIDIAN && HoleESP.mc.world.getBlockState(boost3).getBlock() != Blocks.BEDROCK || HoleESP.mc.world.getBlockState(boost4).getBlock() != Blocks.OBSIDIAN && HoleESP.mc.world.getBlockState(boost4).getBlock() != Blocks.BEDROCK || HoleESP.mc.world.getBlockState(boost5).getBlock() != Blocks.OBSIDIAN && HoleESP.mc.world.getBlockState(boost5).getBlock() != Blocks.BEDROCK || HoleESP.mc.world.getBlockState(boost6).getBlock() != Blocks.OBSIDIAN && HoleESP.mc.world.getBlockState(boost6).getBlock() != Blocks.BEDROCK || HoleESP.mc.world.getBlockState(boost8).getBlock() != Blocks.AIR || HoleESP.mc.world.getBlockState(boost9).getBlock() != Blocks.OBSIDIAN && HoleESP.mc.world.getBlockState(boost9).getBlock() != Blocks.BEDROCK);
    }

    private boolean IsBRockHole(BlockPos blockPos) {
        BlockPos boost = blockPos.add(0, 1, 0);
        BlockPos boost2 = blockPos.add(0, 0, 0);
        BlockPos boost3 = blockPos.add(0, 0, -1);
        BlockPos boost4 = blockPos.add(1, 0, 0);
        BlockPos boost5 = blockPos.add(-1, 0, 0);
        BlockPos boost6 = blockPos.add(0, 0, 1);
        BlockPos boost7 = blockPos.add(0, 2, 0);
        BlockPos boost8 = blockPos.add(0.5, 0.5, 0.5);
        BlockPos boost9 = blockPos.add(0, -1, 0);
        return HoleESP.mc.world.getBlockState(boost).getBlock() == Blocks.AIR && HoleESP.mc.world.getBlockState(boost2).getBlock() == Blocks.AIR && HoleESP.mc.world.getBlockState(boost7).getBlock() == Blocks.AIR && HoleESP.mc.world.getBlockState(boost3).getBlock() == Blocks.BEDROCK && HoleESP.mc.world.getBlockState(boost4).getBlock() == Blocks.BEDROCK && HoleESP.mc.world.getBlockState(boost5).getBlock() == Blocks.BEDROCK && HoleESP.mc.world.getBlockState(boost6).getBlock() == Blocks.BEDROCK && HoleESP.mc.world.getBlockState(boost8).getBlock() == Blocks.AIR && HoleESP.mc.world.getBlockState(boost9).getBlock() == Blocks.BEDROCK;
    }

    public static BlockPos getPlayerPos() {
        return new BlockPos(Math.floor(HoleESP.mc.player.posX), Math.floor(HoleESP.mc.player.posY), Math.floor(HoleESP.mc.player.posZ));
    }

    private List<BlockPos> findObbyHoles() {
        NonNullList positions = NonNullList.create();
        positions.addAll((Collection)this.getSphere(HoleESP.getPlayerPos(), this.range.getValue().floatValue(), this.range.getValue().intValue(), false, true, 0).stream().filter(this::IsObbyHole).collect(Collectors.toList()));
        return positions;
    }

    private List<BlockPos> findBRockHoles() {
        NonNullList positions = NonNullList.create();
        positions.addAll((Collection)this.getSphere(HoleESP.getPlayerPos(), this.range.getValue().floatValue(), this.range.getValue().intValue(), false, true, 0).stream().filter(this::IsBRockHole).collect(Collectors.toList()));
        return positions;
    }

    public List<BlockPos> getSphere(BlockPos loc, float r, int h, boolean hollow, boolean sphere, int plus_y) {
        ArrayList<BlockPos> circleblocks = new ArrayList<BlockPos>();
        int cx = loc.getX();
        int cy = loc.getY();
        int cz = loc.getZ();
        int x = cx - (int)r;
        while ((float)x <= (float)cx + r) {
            int z = cz - (int)r;
            while ((float)z <= (float)cz + r) {
                int y = sphere ? cy - (int)r : cy;
                do {
                    float f = sphere ? (float)cy + r : (float)(cy + h);
                    if (!((float)y < f)) break;
                    double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);
                    if (!(!(dist < (double)(r * r)) || hollow && dist < (double)((r - 1.0f) * (r - 1.0f)))) {
                        BlockPos l = new BlockPos(x, y + plus_y, z);
                        circleblocks.add(l);
                    }
                    ++y;
                } while (true);
                ++z;
            }
            ++x;
        }
        return circleblocks;
    }

    private static void setYawAndPitch(float yaw1, float pitch1) {
        yaw = yaw1;
        pitch = pitch1;
        isSpoofingAngles = true;
    }

    private static void resetRotation() {
        if (isSpoofingAngles) {
            yaw = HoleESP.mc.player.rotationYaw;
            pitch = HoleESP.mc.player.rotationPitch;
            isSpoofingAngles = false;
        }
    }

    @Override
    public void onDisable() {
        this.render = null;
        HoleESP.resetRotation();
    }
}


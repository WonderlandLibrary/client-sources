package me.finz0.osiris.module.modules.render;

import de.Hero.settings.Setting;
import me.finz0.osiris.AuroraMod;
import me.finz0.osiris.event.events.PacketEvent;
import me.finz0.osiris.event.events.RenderEvent;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.util.*;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

//TODO: rewrite
public class HoleESP extends Module {
    public HoleESP() {
        super("HoleESP", Category.RENDER, "Shows holes nigga");
    }

    Setting range;
    Setting r;
    Setting g;
    Setting b;
    Setting a;
    Setting r2;
    Setting g2;
    Setting b2;
    Setting rainbow;
    Setting mode;
    Setting width;
    Setting renderMode;
    Setting highlight;
    Setting box;
    Setting bottom;
    Setting bottomBox;
    Setting obbyChroma;
    Setting bRockChroma;
    Setting chromaSpeed;
    Setting a2;

    private final BlockPos[] surroundOffset = {
            new BlockPos(0, -1, 0), // down
            new BlockPos(0, 0, -1), // north
            new BlockPos(1, 0, 0), // east
            new BlockPos(0, 0, 1), // south
            new BlockPos(-1, 0, 0) // west
    };
    private BlockPos render;
    private static boolean isSpoofingAngles;
    private static double yaw;
    private static double pitch;
    public void setup(){
        highlight = new Setting("BoxFill", this, true, "HoleEspBlockOutline");
        AuroraMod.getInstance().settingsManager.rSetting(highlight);
        box = new Setting("BoxOutLine", this, true, "HoleEspBoxFill");
        AuroraMod.getInstance().settingsManager.rSetting(box);
        bottomBox = new Setting("BottomOutLine", this, true, "HoleEspbottomBox");
        AuroraMod.getInstance().settingsManager.rSetting(bottomBox);
        obbyChroma = new Setting("ObbyChroma", this, false, "HoleEspobbyChroma");
        AuroraMod.getInstance().settingsManager.rSetting(obbyChroma);
        bRockChroma = new Setting("BRockChroma", this, false, "HoleEspbRockChroma");
        AuroraMod.getInstance().settingsManager.rSetting(bRockChroma);
        range = new Setting("Range", this, 8, 0, 20, true, "HoleEspRange");
        AuroraMod.getInstance().settingsManager.rSetting(range);
        chromaSpeed = new Setting("ChromaSpeed", this, 8, 0, 20, true, "HoleEspchromaSpeed");
        AuroraMod.getInstance().settingsManager.rSetting(chromaSpeed);
        r = new Setting("Red", this, 255, 0, 255, true, "HoleEspRed");
        AuroraMod.getInstance().settingsManager.rSetting(r);
        g = new Setting("Green", this, 255, 0, 255, true, "HoleEspGreen");
        AuroraMod.getInstance().settingsManager.rSetting(g);
        b = new Setting("Blue", this, 255, 0, 255, true, "HoleEspBlue");
        AuroraMod.getInstance().settingsManager.rSetting(b);
        r2 = new Setting("RedBedrock", this, 255, 0, 255, true, "HoleEspRedBedrock");
        AuroraMod.getInstance().settingsManager.rSetting(r2);
        g2 = new Setting("GreenBedrock", this, 255, 0, 255, true, "HoleEspGreenBedrock");
        AuroraMod.getInstance().settingsManager.rSetting(g2);
        b2 = new Setting("BlueBedrock", this, 255, 0, 255, true, "HoleEspBlueBedrock");
        AuroraMod.getInstance().settingsManager.rSetting(b2);
        a = new Setting("Alpha", this, 26, 0, 255, true, "HoleEspAlpha");
        AuroraMod.getInstance().settingsManager.rSetting(a);
        a2 = new Setting("AlphaBounding", this, 255, 0, 255, true, "HoleEspAlphaBounding");
        AuroraMod.getInstance().settingsManager.rSetting(a2);
        AuroraMod.getInstance().settingsManager.rSetting(width = new Setting("LineWidth", this, 3, 1, 10, true, "HoleEspLineWidth"));

    }

    private ConcurrentHashMap<BlockPos, Boolean> safeHoles;

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
        float[] hue = new float[]{(float)(System.currentTimeMillis() % 11520L) / 11520.0f * this.chromaSpeed.getValInt()};
        int rgb = Color.HSBtoRGB(hue[0], 1.0f, 1.0f);
        int red = rgb >> 16 & 255;
        int green = rgb >> 8 & 255;
        int blue = rgb & 255;
        GL11.glEnable((int)2884);
        if (this.render != null) {
            for (BlockPos hole : this.findObbyHoles()) {
                if (this.obbyChroma.getValBoolean()) {
                    if (this.highlight.getValBoolean()) {
                        OsirisTessellator.prepare(7);
                        OsirisTessellator.drawBox(hole, red, green, blue, this.a.getValInt(), 63);
                        OsirisTessellator.release();
                    }
                    if (this.box.getValBoolean()) {
                        AuroraTessellator.prepare(7);
                        AuroraTessellator.drawBoundingBoxBlockPos(hole, this.width.getValInt(), red, green, blue, this.a2.getValInt());
                        AuroraTessellator.release();
                    }
                    if (!this.bottomBox.getValBoolean()) continue;
                    AuroraTessellator.prepare(7);
                    AuroraTessellator.drawBoundingBoxBottomBlockPos(hole, this.width.getValInt(), red, green, blue, this.a2.getValInt());
                    AuroraTessellator.release();
                    continue;
                }
                if (this.highlight.getValBoolean()) {
                    OsirisTessellator.prepare(7);
                    OsirisTessellator.drawBox(hole, this.r.getValInt(), this.g.getValInt(), this.b.getValInt(), this.a.getValInt(), 63);
                    OsirisTessellator.release();
                }
                if (this.box.getValBoolean()) {
                    AuroraTessellator.prepare(7);
                    AuroraTessellator.drawBoundingBoxBlockPos(hole, this.width.getValInt(), this.r.getValInt(), this.g.getValInt(), this.b.getValInt(), this.a2.getValInt());
                    AuroraTessellator.release();
                }

                if (!this.bottomBox.getValBoolean()) continue;
                AuroraTessellator.prepare(7);
                AuroraTessellator.drawBoundingBoxBottomBlockPos(hole, this.width.getValInt(), this.r.getValInt(), this.g.getValInt(), this.b.getValInt(), this.a2.getValInt());
                AuroraTessellator.release();
            }
            for (BlockPos hole : this.findBRockHoles()) {
                if (this.bRockChroma.getValBoolean()) {
                    if (this.highlight.getValBoolean()) {
                        OsirisTessellator.prepare(7);
                        OsirisTessellator.drawBox(hole, red, green, blue, this.a.getValInt(), 63);
                        OsirisTessellator.release();
                    }
                    if (this.box.getValBoolean()) {
                       AuroraTessellator.prepare(7);
                       AuroraTessellator.drawBoundingBoxBlockPos(hole, this.width.getValInt(), red, green, blue, this.a2.getValInt());
                       AuroraTessellator.release();
                    }

                    if (!this.bottomBox.getValBoolean()) continue;
                    AuroraTessellator.prepare(7);
                    AuroraTessellator.drawBoundingBoxBottomBlockPos(hole, this.width.getValInt(), red, green, blue, this.a2.getValInt());
                    AuroraTessellator.release();
                    continue;
                }
                if (this.highlight.getValBoolean()) {
                    OsirisTessellator.prepare(7);
                    OsirisTessellator.drawBox(hole, this.r2.getValInt(), this.g2.getValInt(), this.b2.getValInt(), this.a.getValInt(), 63);
                    OsirisTessellator.release();
                }
                if (this.box.getValBoolean()) {
                    AuroraTessellator.prepare(7);
                    AuroraTessellator.drawBoundingBoxBlockPos(hole, this.width.getValInt(), this.r2.getValInt(), this.g2.getValInt(), this.b2.getValInt(), this.a2.getValInt());
                    AuroraTessellator.release();
                }

                if (!this.bottomBox.getValBoolean()) continue;
               AuroraTessellator.prepare(7);
               AuroraTessellator.drawBoundingBoxBottomBlockPos(hole, this.width.getValInt(), this.r2.getValInt(), this.g2.getValInt(), this.b2.getValInt(), this.a2.getValInt());
               AuroraTessellator.release();
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
        positions.addAll((Collection)this.getSphere(HoleESP.getPlayerPos(), this.range.getValInt(), this.range.getValInt(), false, true, 0).stream().filter(this::IsObbyHole).collect(Collectors.toList()));
        return positions;
    }

    private List<BlockPos> findBRockHoles() {
        NonNullList positions = NonNullList.create();
        positions.addAll((Collection)this.getSphere(HoleESP.getPlayerPos(), this.range.getValInt(), this.range.getValInt(), false, true, 0).stream().filter(this::IsBRockHole).collect(Collectors.toList()));
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
/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.utils.Command.impl;

import java.util.Comparator;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import ru.govno.client.Client;
import ru.govno.client.module.modules.ElytraBoost;
import ru.govno.client.module.modules.Speed;
import ru.govno.client.utils.Command.Command;
import ru.govno.client.utils.Math.MathUtils;
import ru.govno.client.utils.Math.TimerHelper;

public class Clip
extends Command {
    private static final Minecraft mc = Minecraft.getMinecraft();
    static double ty = 0.0;
    static double th = 0.0;
    static boolean ely = false;
    static int grace = 0;
    static TimerHelper timer = new TimerHelper();

    public Clip() {
        super("Clip", new String[]{"vclip", "hclip", "dclip", "vc", "hc", "dc", "up", "down", "bd"});
    }

    public static void onClipUpdate() {
        if (timer.hasReached(450.0)) {
            if ((th != 0.0 || ty != 0.0) && grace > 0) {
                --grace;
                Clip.goClip(MathUtils.clamp(ty, -200.0, 200.0), MathUtils.clamp(th, -10.0, 10.0), ely);
                ty -= MathUtils.clamp(ty, -200.0, 200.0);
                th -= MathUtils.clamp(th, -10.0, 10.0);
                timer.reset();
                Minecraft.player.motionY = -0.02;
            }
        } else if (grace > 0) {
            Minecraft.player.motionY = 0.0;
        }
    }

    public static void runClip(double y, double h, boolean canElytra) {
        grace = 1 + (int)(Math.abs(y) / 200.0) + (int)(Math.abs(h) / 10.0 + 1.0E-45);
        ty = y;
        th = h;
        ely = canElytra;
    }

    @Override
    public void onCommand(String[] args) {
        try {
            if (Minecraft.player == null) {
                Client.msg("\u00a7d\u00a7lClip:\u00a7r \u00a77\u0412\u0430\u0448 \u043f\u0435\u0440\u0441\u043e\u043d\u0430\u0436 \u0435\u0449\u0451 \u043d\u0435 \u0438\u043d\u0438\u0446\u0438\u043b\u0438\u0437\u0438\u0440\u043e\u0432\u0430\u043b\u0441\u044f.", false);
                return;
            }
            boolean trouble = true;
            for (int i = 0; i < 45; ++i) {
                ItemStack itemStack = Minecraft.player.inventoryContainer.getSlot(i).getStack();
                if (itemStack.getItem() != Items.ELYTRA) continue;
                trouble = false;
            }
            boolean elytra = !trouble;
            String ClipANDEclip = elytra ? "\u00a7d\u00a7lEClip:\u00a7r " : "\u00a7d\u00a7lClip:\u00a7r ";
            double y = 0.0;
            double h = 0.0;
            if (args[0].equalsIgnoreCase("vclip") || args[0].equalsIgnoreCase("vc") || args[0].equals("down") || args[0].equals("up") || args[0].equals("bd")) {
                if (args[0].equals("down") || args[0].equals("up") || args[0].equals("bd")) {
                    if (args[0].equals("down")) {
                        int x = (int)Math.floor(Minecraft.player.posX);
                        int z = (int)Math.floor(Minecraft.player.posZ);
                        for (int yS = 0; yS < (int)Minecraft.player.posY; ++yS) {
                            if (Speed.posBlock(x, yS, z) && !Speed.posBlock(x, yS - 1, z) && !Speed.posBlock(x, yS - 2, z)) {
                                y = (double)(yS - 2) - Minecraft.player.posY + 0.2;
                            }
                            y -= (double)0.01f;
                        }
                        if (!mc.isSingleplayer() && mc.getCurrentServerData() != null && Clip.mc.getCurrentServerData().serverIP != null && Clip.mc.getCurrentServerData().serverIP.equalsIgnoreCase("mc.reallyworld.ru")) {
                            y -= 1.0;
                        }
                        if (y < -Minecraft.player.posY) {
                            Client.msg("\u00a7d\u00a7lClip:\u00a7r \u00a77\u043d\u0435\u0442 \u0441\u0432\u043e\u0431\u043e\u0434\u043d\u043e\u0433\u043e \u043c\u0435\u0441\u0442\u0430.", false);
                            return;
                        }
                    }
                    if (args[0].equals("up")) {
                        int VerticalRange = 200;
                        for (float i = 0.0f; i < (float)VerticalRange; i += 0.005f) {
                            float o = i;
                            if (Clip.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY + (double)o + 1.0, Minecraft.player.posZ)).getBlock() != Blocks.AIR || Clip.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY + (double)o + 0.005, Minecraft.player.posZ)).getBlock() != Blocks.AIR && Clip.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY + (double)o + 0.005, Minecraft.player.posZ)).getBlock() != Blocks.WATER && Clip.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY + (double)o + 0.005, Minecraft.player.posZ)).getBlock() != Blocks.LAVA && Clip.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY + (double)o + 0.005, Minecraft.player.posZ)).getBlock() != Blocks.WEB && Clip.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY + (double)o, Minecraft.player.posZ)).getBlock() != Blocks.TRAPDOOR && Clip.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY + (double)o, Minecraft.player.posZ)).getBlock() != Blocks.IRON_TRAPDOOR || Clip.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY + (double)o - 0.002, Minecraft.player.posZ)).getBlock() == Blocks.AIR || !(i > 2.0f) || Clip.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY - 0.1, Minecraft.player.posZ)).getBlock() == Blocks.WATER) continue;
                            if (Clip.mc.gameSettings.keyBindJump.isKeyDown()) {
                                Minecraft.player.onGround = true;
                                Minecraft.player.motionY = 0.0;
                            }
                            y = i;
                            y += 0.01;
                            if (!mc.isSingleplayer() && mc.getCurrentServerData() != null && Clip.mc.getCurrentServerData().serverIP != null && Clip.mc.getCurrentServerData().serverIP.equalsIgnoreCase("mc.reallyworld.ru")) {
                                y += 1.0;
                            }
                            if (Clip.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY + y - 0.49, Minecraft.player.posZ)).getBlock() != Blocks.WATER) continue;
                            y -= 1.0;
                        }
                    }
                    if (args[0].equals("bd")) {
                        y = MathUtils.roundPROBLYA((float)((double)(-Minecraft.player.height) - Minecraft.player.posY - (double)0.01f), 0.01f);
                    }
                } else {
                    y = Double.valueOf(args[1]);
                }
                if (!args[0].equals("bd") || !(y <= 0.0)) {
                    Client.msg(ClipANDEclip + "\u00a77\u0412\u044b \u0442\u0435\u043f\u043d\u0443\u043b\u0438\u0441\u044c \u043d\u0430 \u00a7b" + String.format("%.1f", Double.valueOf(y) > 0.0 ? Double.valueOf(y) : -Double.valueOf(y).doubleValue()) + "\u00a77 \u0431\u043b\u043e\u043a\u043e\u0432 " + (Double.valueOf(y) > 0.0 ? "\u0432\u0432\u0435\u0440\u0445." : "\u0432\u043d\u0438\u0437."), false);
                } else {
                    Client.msg(ClipANDEclip + "\u00a77\u0412\u044b \u0442\u0435\u043f\u043d\u0443\u043b\u0438\u0441\u044c \u043f\u043e\u0434 \u0431\u0435\u0434\u0440\u043e\u043a.", false);
                }
            }
            if (args[0].equalsIgnoreCase("hclip") || args[0].equalsIgnoreCase("hc")) {
                h = Double.valueOf(args[1]);
                Client.msg(ClipANDEclip + "\u00a77\u0412\u044b \u0442\u0435\u043f\u043d\u0443\u043b\u0438\u0441\u044c \u043d\u0430 \u00a7b" + String.format("%.1f", Double.valueOf(y) > 0.0 ? Double.valueOf(y) : -Double.valueOf(y).doubleValue()) + "\u00a77 \u0431\u043b\u043e\u043a\u043e\u0432 " + (Double.valueOf(args[1]) > 0.0 ? "\u0432\u043f\u0435\u0440\u0451\u0434." : "\u043d\u0430\u0437\u0430\u0434."), false);
            }
            if (args[0].equalsIgnoreCase("dclip") || args[0].equalsIgnoreCase("dc")) {
                y = Double.valueOf(args[1]);
                h = Double.valueOf(args[2]);
                Client.msg(ClipANDEclip + "\u00a77\u0412\u044b \u0442\u0435\u043f\u043d\u0443\u043b\u0438\u0441\u044c \u043d\u0430 \u00a7b" + String.format("%.1f", Double.valueOf(y) > 0.0 ? Double.valueOf(y) : -Double.valueOf(y).doubleValue()) + "Y : " + (Double.valueOf(args[2]) > 0.0 ? Double.valueOf(args[2]) : -Double.valueOf(args[2]).doubleValue()) + "XZ\u00a77 \u0431\u043b\u043e\u043a\u043e\u0432 \u0434\u0438\u0430\u0433\u043e\u043d\u0430\u043b\u044c\u043d\u043e.", false);
            }
            if (y != 0.0 || h != 0.0) {
                Clip.runClip(y, h, elytra);
                if (MathUtils.getDifferenceOf(y, Minecraft.player.posY) > 100.0 || MathUtils.getDifferenceOf(h, Math.sqrt(Minecraft.player.posX * Minecraft.player.posX + Minecraft.player.posZ * Minecraft.player.posZ)) > 60.0) {
                    Clip.mc.renderGlobal.loadRenderers();
                }
            }
        } catch (Exception formatException) {
            Client.msg("\u00a7d\u00a7lClip:\u00a7r \u00a77\u041a\u043e\u043c\u043c\u0430\u043d\u0434\u0430 \u043d\u0430\u043f\u0438\u0441\u0430\u043d\u0430 \u043d\u0435\u0432\u0435\u0440\u043d\u043e.", false);
            Client.msg("\u00a7d\u00a7lClip:\u00a7r \u00a77vclip: vclip/vc [\u00a7ly+\u00a7r\u00a77]", false);
            Client.msg("\u00a7d\u00a7lClip:\u00a7r \u00a77up/down/bd", false);
            Client.msg("\u00a7d\u00a7lClip:\u00a7r \u00a77hclip: hclip/hc [\u00a7lh+\u00a7r\u00a77]", false);
            Client.msg("\u00a7d\u00a7lClip:\u00a7r \u00a77dclip: dclip/dc [\u00a7lv+,h+\u00a7r\u00a77]", false);
            formatException.printStackTrace();
        }
    }

    public static void goClip(double y, double h, boolean canElytra) {
        boolean isRW;
        boolean bl = isRW = !mc.isSingleplayer() && mc.getCurrentServerData() != null && Clip.mc.getCurrentServerData().serverIP != null && Clip.mc.getCurrentServerData().serverIP.equalsIgnoreCase("mc.reallyworld.ru");
        if (!isRW) {
            BlockPos pos = null;
            CopyOnWriteArrayList<BlockPos> mixPoses = new CopyOnWriteArrayList<BlockPos>();
            Vec3d ePos = new Vec3d(Minecraft.player.posX, Minecraft.player.posY, Minecraft.player.posZ);
            float r = 4.1f;
            for (float xs = -4.1f; xs < 4.1f; xs += 1.0f) {
                for (float ys = -4.1f; ys < 1.0f; ys += 1.0f) {
                    for (float zs = -4.1f; zs < 4.1f; zs += 1.0f) {
                        BlockPos poss = new BlockPos((double)xs + ePos.xCoord, (double)ys + ePos.yCoord, (double)zs + ePos.zCoord);
                        Block block = Clip.mc.world.getBlockState(poss).getBlock();
                        if (block == Blocks.AIR || block == Blocks.BARRIER || block == Blocks.BEDROCK || poss == null || !(Minecraft.player.getDistanceAtEye(poss.getX(), poss.getY(), poss.getZ()) <= (double)4.1f)) continue;
                        mixPoses.add(poss);
                    }
                }
            }
            if (mixPoses.size() != 0) {
                mixPoses.sort(Comparator.comparing(current -> Float.valueOf(Clip.mc.world.getBlockState((BlockPos)current).getBlockHardness(Clip.mc.world, (BlockPos)current))));
                pos = (BlockPos)mixPoses.get(0);
                if (pos != null) {
                    Minecraft.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, pos, EnumFacing.UP));
                    Minecraft.player.connection.sendPacket(new CPacketAnimation(EnumHand.OFF_HAND));
                    Minecraft.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, EnumFacing.UP));
                }
            }
        }
        y += h / 100.0;
        y = MathUtils.clamp(y, -200.0, 200.0);
        float f = Minecraft.player.rotationYaw * ((float)Math.PI / 180);
        double x = -((double)MathHelper.sin(f) * h);
        double z = (double)MathHelper.cos(f) * h;
        int de = (int)(Math.abs(y / 10.0) + Math.abs(h));
        for (int i = 0; i < MathUtils.clamp(de - (Math.abs(y) == 20.0 ? 1 : 0), 0, 19); ++i) {
            Minecraft.player.connection.sendPacket(new CPacketPlayer(false));
        }
        if ((canElytra || Minecraft.player.inventory.armorInventory.get(2).getItem() == Items.ELYTRA) && !Minecraft.player.isElytraFlying()) {
            ElytraBoost.eq();
            if (!Minecraft.player.onGround) {
                Minecraft.player.connection.sendPacket(new CPacketPlayer(Minecraft.player.onGround));
            }
            if (Minecraft.player.onGround) {
                Minecraft.player.connection.sendPacket(new CPacketPlayer(false));
            }
            ElytraBoost.badPacket();
            ElytraBoost.deq();
            Minecraft.player.connection.sendPacket(new CPacketPlayer(true));
        }
        Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX + x, Minecraft.player.posY + y, Minecraft.player.posZ + z, false));
        Minecraft.player.setPosition(Minecraft.player.posX + x, Minecraft.player.posY + y, Minecraft.player.posZ + z);
    }
}


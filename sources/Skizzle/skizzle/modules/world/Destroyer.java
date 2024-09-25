/*
 * Decompiled with CFR 0.150.
 */
package skizzle.modules.world;

import java.util.ArrayList;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import skizzle.events.Event;
import skizzle.events.listeners.EventUpdate;
import skizzle.modules.Module;
import skizzle.settings.BooleanSetting;
import skizzle.settings.NumberSetting;
import skizzle.util.Timer;

public class Destroyer
extends Module {
    public NumberSetting delay;
    public NumberSetting radius;
    public int y;
    public BooleanSetting breakWall;
    public int z;
    public BooleanSetting beds;
    public BooleanSetting checkWall;
    public BooleanSetting eggs;
    public static BlockPos pos;
    public Timer timer = new Timer();
    public BooleanSetting cake;
    public static ArrayList<Integer> ids;
    public int x;

    public void smashBlock(BlockPos Nigga) {
        Destroyer Nigga2;
        Nigga2.mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, Nigga, EnumFacing.UP));
        Nigga2.mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, Nigga, EnumFacing.UP));
        Nigga2.mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
    }

    public Destroyer() {
        super(Qprot0.0("\u7f1c\u71ce\u445f\ua7f0\u5edd\ufa8d\u8c36\u1370\u5710"), 0, Module.Category.WORLD);
        Destroyer Nigga;
        Nigga.radius = new NumberSetting(Qprot0.0("\u7f0a\u71ca\u4448\ua7ed\u5eda\ufa91"), 5.0, 1.0, 6.0, 1.0);
        Nigga.checkWall = new BooleanSetting(Qprot0.0("\u7f1b\u71c3\u4449\ua7e7\u5ec4\ufac2\u8c18\u1374\u570e\uf589\u70bc"), true);
        Nigga.breakWall = new BooleanSetting(Qprot0.0("\u7f1a\u71d9\u4449\ua7e5\u5ec4\ufac2\u8c18\u1374\u570e\uf589\u70bc"), true);
        Nigga.delay = new NumberSetting(Qprot0.0("\u7f1c\u71ce\u4440\ua7e5\u5ed6"), 200.0, 0.0, 600.0, 10.0);
        Nigga.cake = new BooleanSetting(Qprot0.0("\u7f1b\u71ca\u4447\ua7e1"), true);
        Nigga.beds = new BooleanSetting(Qprot0.0("\u7f1a\u71ce\u4448\ua7f7"), true);
        Nigga.eggs = new BooleanSetting(Qprot0.0("\u7f1d\u71cc\u444b\ua7f7"), true);
        Nigga.addSettings(Nigga.radius, Nigga.checkWall, Nigga.breakWall, Nigga.delay, Nigga.cake, Nigga.beds, Nigga.eggs);
    }

    @Override
    public void onEvent(Event Nigga) {
        Destroyer Nigga2;
        if (Nigga instanceof EventUpdate && Nigga2.mc.thePlayer != null) {
            boolean Nigga3 = Nigga2.checkWall.isEnabled();
            boolean Nigga4 = Nigga2.breakWall.isEnabled();
            int Nigga5 = (int)Nigga2.radius.getValue();
            int Nigga6 = (int)Nigga2.delay.getValue();
            int Nigga7 = Nigga2.mc.thePlayer.getPosition().getX() - Nigga5;
            int Nigga8 = Nigga2.mc.thePlayer.getPosition().getY() - Nigga5;
            int Nigga9 = Nigga2.mc.thePlayer.getPosition().getZ() - Nigga5;
            int Nigga10 = Nigga2.mc.thePlayer.getPosition().getX() + Nigga5;
            int Nigga11 = Nigga2.mc.thePlayer.getPosition().getY() + Nigga5;
            int Nigga12 = Nigga2.mc.thePlayer.getPosition().getZ() + Nigga5;
            Nigga2.checkIds();
            Nigga2.x = Nigga7;
            while (Nigga2.x < Nigga10) {
                Nigga2.z = Nigga9;
                while (Nigga2.z < Nigga12) {
                    Nigga2.y = Nigga8;
                    while (Nigga2.y < Nigga11) {
                        BlockPos Nigga13 = new BlockPos(Nigga2.x, Nigga2.y, Nigga2.z);
                        if (Minecraft.theWorld.getBlockState(Nigga13) != null) {
                            int Nigga14 = Block.getIdFromBlock(Minecraft.theWorld.getBlockState(Nigga13).getBlock());
                            if (ids.size() > 0 && ids.contains(Nigga14)) {
                                pos = Nigga13;
                                if (Nigga4) {
                                    for (int Nigga15 = 5; Nigga15 > 1; --Nigga15) {
                                        if (Nigga2.mc.thePlayer.canBlockBeSeen(Nigga13)) break;
                                        BlockPos Nigga16 = new BlockPos(Nigga13.getX(), Nigga13.getY() + Nigga15, Nigga13.getZ());
                                        if (Minecraft.theWorld.getBlockState(Nigga16).getBlock() == Blocks.air) continue;
                                        Nigga2.smashBlock(Nigga16);
                                        break;
                                    }
                                }
                                if (Nigga3 && !Nigga2.mc.thePlayer.canBlockBeSeen(pos)) break;
                                if (Nigga2.timer.hasTimeElapsed((long)Nigga2.delay.getValue(), true)) {
                                    Nigga2.smashBlock(Nigga13);
                                    break;
                                }
                            }
                        }
                        ++Nigga2.y;
                    }
                    ++Nigga2.z;
                }
                ++Nigga2.x;
            }
        }
    }

    public void checkIds() {
        Destroyer Nigga;
        boolean Nigga2 = Nigga.beds.isEnabled();
        boolean Nigga3 = Nigga.cake.isEnabled();
        boolean Nigga4 = Nigga.eggs.isEnabled();
        Integer Nigga5 = new Integer(26);
        if (ids.contains(Nigga5) && !Nigga2) {
            ids.remove(Nigga5);
        }
        if (!ids.contains(Nigga5) && Nigga2) {
            ids.add(Nigga5);
        }
        if (ids.contains(Nigga5 = new Integer(354)) && !Nigga3) {
            ids.remove(Nigga5);
        }
        if (!ids.contains(Nigga5) && Nigga3) {
            ids.add(Nigga5);
        }
        if (ids.contains(Nigga5 = new Integer(122)) && !Nigga4) {
            ids.remove(Nigga5);
        }
        if (!ids.contains(Nigga5) && Nigga4) {
            ids.add(Nigga5);
        }
    }

    @Override
    public void onDisable() {
        pos = null;
    }

    static {
        ids = new ArrayList();
    }
}


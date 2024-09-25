/*
 * Decompiled with CFR 0.150.
 */
package skizzle.modules.player;

import java.util.ArrayList;
import net.minecraft.block.BlockChest;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import net.minecraft.world.ILockableContainer;
import skizzle.Client;
import skizzle.events.Event;
import skizzle.events.listeners.EventMotion;
import skizzle.events.listeners.EventUpdate;
import skizzle.modules.Module;
import skizzle.modules.ModuleManager;
import skizzle.settings.NumberSetting;
import skizzle.ui.notifications.Notification;
import skizzle.util.BlockUtil;
import skizzle.util.Timer;

public class ChestAura
extends Module {
    public int y;
    public BlockPos lastPos;
    public BlockPos pos;
    public NumberSetting delay;
    public Timer timer = new Timer();
    public ArrayList<BlockPos> chests;
    public int x;
    public NumberSetting radius;
    public int z;

    public ChestAura() {
        super(Qprot0.0("\u3279\u71c3\u092b\ua7f7\u1dfd\ub7c1\u8c3a\u5e05\u5703"), 0, Module.Category.PLAYER);
        ChestAura Nigga;
        Nigga.lastPos = new BlockPos(0, 0, 0);
        Nigga.chests = new ArrayList();
        Nigga.radius = new NumberSetting(Qprot0.0("\u3268\u71ca\u092a\ua7ed\u1dfc\ub7f3"), 5.0, 1.0, 7.0, 1.0);
        Nigga.delay = new NumberSetting(Qprot0.0("\u327e\u71ce\u0922\ua7e5\u1df0"), 100.0, 0.0, 500.0, 10.0);
        Nigga.addSettings(Nigga.radius, Nigga.delay);
    }

    @Override
    public void onEvent(Event Nigga) {
        ChestAura Nigga2;
        if (ModuleManager.hudModule.infoSetting.getMode().equals(Qprot0.0("\u326e\u71c4\u0921\ue22d\ua64d\ub7f5\u8c2c\u5e1f"))) {
            Nigga2.setSuffix(Qprot0.0("\u3268\u7191") + Nigga2.radius.getValue() + Qprot0.0("\u321a\u71ef\u0974") + Nigga2.delay.getValue());
        } else {
            Nigga2.setSuffix("");
        }
        if (Nigga instanceof EventUpdate && Nigga2.mc.thePlayer.ticksExisted <= 2) {
            Nigga2.chests.clear();
        }
        if (Nigga instanceof EventMotion && Nigga2.mc.thePlayer != null) {
            EventMotion Nigga3 = (EventMotion)Nigga;
            int Nigga4 = (int)Nigga2.radius.getValue();
            int Nigga5 = Nigga2.mc.thePlayer.getPosition().getX() - Nigga4;
            int Nigga6 = Nigga2.mc.thePlayer.getPosition().getY() - Nigga4;
            int Nigga7 = Nigga2.mc.thePlayer.getPosition().getZ() - Nigga4;
            int Nigga8 = Nigga2.mc.thePlayer.getPosition().getX() + Nigga4;
            int Nigga9 = Nigga2.mc.thePlayer.getPosition().getY() + Nigga4;
            int Nigga10 = Nigga2.mc.thePlayer.getPosition().getZ() + Nigga4;
            Nigga2.x = Nigga5;
            while (Nigga2.x <= Nigga8) {
                Nigga2.z = Nigga7;
                while (Nigga2.z <= Nigga10) {
                    Nigga2.y = Nigga6;
                    while (Nigga2.y <= Nigga9) {
                        String Nigga11;
                        BlockPos Nigga12 = new BlockPos(Nigga2.x, Nigga2.y, Nigga2.z);
                        if (!(Minecraft.theWorld.getBlockState(Nigga12) == null || Nigga2.lastPos.getX() == Nigga12.getX() && Nigga2.lastPos.getY() == Nigga12.getY() && Nigga2.lastPos.getZ() == Nigga12.getZ() || Nigga2.chests.contains(Nigga12) || (Nigga11 = Minecraft.theWorld.getBlockState(Nigga12).getBlock().getUnlocalizedName()).equals(Qprot0.0("\u324e\u71c2\u0922\ue268\ua62e\ub7e1\u8c26\u5e05")) || !Nigga11.contains(Qprot0.0("\u3259\u71c3\u092b\ue27e\ua674")))) {
                            Nigga2.pos = Nigga12;
                            if (Nigga2.timer.hasTimeElapsed((long)Nigga2.delay.getValue(), true) && BlockPos.autoChestTimer.hasTimeElapsed((long)1568696817 ^ 0x5D806BA9L, true)) {
                                Nigga2.smashBlock(Nigga12);
                                Nigga3.setYaw(BlockUtil.getRotations(Nigga12, EnumFacing.UP)[0]);
                                Nigga3.setPitch(BlockUtil.getRotations(Nigga12, EnumFacing.UP)[1]);
                                Nigga2.chests.add(Nigga12);
                                Nigga2.lastPos = Nigga12;
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

    public boolean isEmpty(BlockPos Nigga) {
        BlockChest Nigga2 = (BlockChest)Minecraft.theWorld.getBlockState(Nigga).getBlock();
        ILockableContainer Nigga3 = Nigga2.getLockableContainer(Minecraft.theWorld, Nigga);
        for (int Nigga4 = 0; Nigga4 < Nigga3.getSizeInventory(); ++Nigga4) {
            ChestAura Nigga5;
            Nigga3.openInventory(Nigga5.mc.thePlayer);
            if (Nigga3.getStackInSlot(Nigga4) == null) continue;
            return false;
        }
        return true;
    }

    public static {
        throw throwable;
    }

    public boolean isChest(BlockPos Nigga) {
        return Minecraft.theWorld.getBlockState(Nigga).getBlock().getUnlocalizedName().contains(Qprot0.0("\u3259\u71c3\u092b\u3672\uea70"));
    }

    public void smashBlock(BlockPos Nigga) {
        ChestAura Nigga2;
        Nigga2.mc.playerController.onPlayerRightClick(Nigga2.mc.thePlayer, Minecraft.theWorld, Nigga2.mc.thePlayer.getHeldItem(), Nigga, EnumFacing.EAST, new Vec3(Nigga.getX(), Nigga.getY(), Nigga.getZ()));
        Client.notifications.notifs.add(new Notification(Qprot0.0("\u327b\u71de\u093a\u2d88\ue62d\ub7e8\u8c2a\u5e04\udd75"), Qprot0.0("\u3275\u71db\u092b\u2d89\ue60b\ub7e4\u8c6f\u5e14\udd69\u4d41\u3dde\uaf18\ub08c\uf820\u9232\uae5e\u42fb\u8475\u9d7a\uf940\ucb89\u018b"), Float.intBitsToFloat(1.11062144E9f ^ 0x7DB2BCF7), Float.intBitsToFloat(2.13790656E9f ^ 0x7F6DDD46), Notification.notificationType.INFO));
        Nigga2.mc.thePlayer.clearItemInUse();
    }
}


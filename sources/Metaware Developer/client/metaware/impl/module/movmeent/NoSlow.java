package client.metaware.impl.module.movmeent;

import client.metaware.Metaware;
import client.metaware.api.event.painfulniggerrapist.Listener;
import client.metaware.api.event.painfulniggerrapist.annotations.EventHandler;
import client.metaware.api.module.api.Category;
import client.metaware.api.module.api.Module;
import client.metaware.api.module.api.ModuleInfo;
import client.metaware.api.properties.property.impl.EnumProperty;
import client.metaware.impl.event.impl.network.PacketEvent;
import client.metaware.impl.utils.system.TimerUtil;
import client.metaware.impl.utils.util.PacketUtil;
import client.metaware.impl.utils.util.other.PlayerUtil;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import org.apache.commons.lang3.RandomUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.util.concurrent.ThreadLocalRandom;

@ModuleInfo(name = "NoSlow", renderName = "NoSlow", category = Category.MOVEMENT, keybind = Keyboard.KEY_NONE)
public class NoSlow extends Module {

    public EnumProperty<Mode> mode = new EnumProperty<>("Mode", Mode.Hypixel);
    public EnumProperty<HypixelMode> hypixelMode = new EnumProperty<>("Hypixel Mode", HypixelMode.NextTick, () -> mode.getValue() == Mode.Hypixel);

    private boolean blocking;
    public static double RANDOM = RandomUtils.nextDouble(Double.MIN_VALUE, Double.MAX_VALUE);
    public static BlockPos HYPIXEL_BLOCK_POS = new BlockPos(RANDOM, RANDOM, RANDOM);
    TimerUtil timer = new TimerUtil();
    int counter;//Will be used in Matrix noslow

    public enum Mode{
        NCP, Hypixel, nono;
    }

    public enum HypixelMode{
        NextTick, Origin;
    }

    @EventHandler
    public final Listener<PacketEvent> eventListener = event -> {
        if (event.getPacket() instanceof C07PacketPlayerDigging) {
            blocking = false;
        }

        if (event.getPacket() instanceof C08PacketPlayerBlockPlacement) {
            blocking = true;
        }
    };

    @EventHandler
    public final Listener<PacketEvent> eventListener1 = event -> {
        setSuffix(mode.getValue().toString());
        if(mc.thePlayer == null || mc.theWorld == null) return;
        if(Metaware.INSTANCE.getModuleManager().getModuleByClass(Flight.class).isToggled()) return;
        if(mode.getValue() == Mode.nono){
            if(Mouse.isButtonDown(1) && PlayerUtil.isHoldingSword()){
                if(mc.thePlayer.ticksExisted % 8 == 0){
                    for(int i = 0; i < 10; i++){
                        PacketUtil.packetNoEvent(new C08PacketPlayerBlockPlacement(null));
                    }
                }
            }
        }
        if (blocking && mc.thePlayer.fallDistance < 3) {
            if (mode.getValue() == Mode.NCP) {


                if (holdingSword() && mc.thePlayer.isBlocking() && blocking && mc.thePlayer.isMoving()) {

                    if(hypixelMode.getValue() == HypixelMode.NextTick) {
                        PacketUtil.packetNoEvent(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(Math.random() * ThreadLocalRandom.current().nextDouble(-RANDOM, RANDOM),
                                Math.random() * ThreadLocalRandom.current().nextDouble(-RANDOM, RANDOM), Math.random() * ThreadLocalRandom.current().nextDouble(-RANDOM, RANDOM)), EnumFacing.DOWN));

                    } else {
                        PacketUtil.packetNoEvent(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));

                    }

                    blocking = false;
                    timer.reset();


                }
            }
            if (mode.getValue() == Mode.Hypixel) {
                if (holdingSword() && mc.thePlayer.isBlocking() && blocking && mc.thePlayer.isMoving()) {

                    if(hypixelMode.getValue() == HypixelMode.NextTick) {
                        PacketUtil.packetNoEvent(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(Math.random() * ThreadLocalRandom.current().nextDouble(-RANDOM, RANDOM),
                                Math.random() * ThreadLocalRandom.current().nextDouble(-RANDOM, RANDOM), Math.random() * ThreadLocalRandom.current().nextDouble(-RANDOM, RANDOM)), EnumFacing.DOWN));

                    } else {
                        PacketUtil.packetNoEvent(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));

                    }

                    blocking = false;
                    timer.reset();

                }
            }
        } else {
            if (mode.getValue() == Mode.NCP) {
                if (holdingSword() && mc.thePlayer.isBlocking() && !blocking) {
                    if(timer.delay(200)) {
                        if(hypixelMode.getValue() == HypixelMode.NextTick) {
                            PacketUtil.packetNoEvent(new C08PacketPlayerBlockPlacement(mc.thePlayer.getHeldItem()));
                        } else {
                            PacketUtil.packetNoEvent(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, null, 0.0f, 0.0f, 0.0f));

                        }

                        blocking = true;
                        timer.reset();
                    }
                }
            }
            if (mode.getValue() == Mode.Hypixel) {
                if (holdingSword() && mc.thePlayer.isBlocking() && !blocking) {
                    if(timer.delay(200)) {
                        if(hypixelMode.getValue() == HypixelMode.NextTick) {
                            PacketUtil.packetNoEvent(new C08PacketPlayerBlockPlacement(mc.thePlayer.getHeldItem()));
                        } else {
                            PacketUtil.packetNoEvent(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, null, 0.0f, 0.0f, 0.0f));

                        }

                        blocking = true;
                        timer.reset();
                    }
                }
            }
        }
    };


    private boolean holdingSword() {
        if (mc.thePlayer.getCurrentEquippedItem() != null && mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword) {
            return true;
        }
        return false;
    }

    
}

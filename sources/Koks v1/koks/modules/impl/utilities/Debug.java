package koks.modules.impl.utilities;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import koks.Koks;
import koks.event.Event;
import koks.event.impl.EventMove;
import koks.event.impl.EventUpdate;
import koks.event.impl.MotionEvent;
import koks.event.impl.PacketEvent;
import koks.modules.Module;
import koks.modules.impl.combat.KillAura;
import koks.utilities.MovementUtil;
import koks.utilities.value.Value;
import koks.utilities.value.values.BooleanValue;
import koks.utilities.value.values.ModeValue;
import koks.utilities.value.values.NumberValue;
import koks.utilities.value.values.TitleValue;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.network.play.server.*;
import net.minecraft.util.ChatComponentText;
import org.lwjgl.Sys;

/**
 * @author avox | lmao | kroko
 * @created on 04.09.2020 : 17:05
 */
public class Debug extends Module {

    public BooleanValue<Boolean> b = new BooleanValue<>("BooleanTest", true, this);

    public BooleanValue<Boolean> b1 = new BooleanValue<>("BooleanTest1", true, this);
    public BooleanValue<Boolean> b2 = new BooleanValue<>("BooleanTest2", true, this);
    public BooleanValue<Boolean> b3 = new BooleanValue<>("BooleanTest3", true, this);
    public BooleanValue<Boolean> b4 = new BooleanValue<>("BooleanTest4", true, this);

    public ModeValue<String> boxCheckBox = new ModeValue<>("BooleanTest", new BooleanValue[]{b1, b2, b3, b4}, this);

    // IGNORE
    public ModeValue<String> box = new ModeValue<>("BooleanTest", "AAC", new String[]{"AAC", "NCP"}, this);

    public NumberValue<Float> vFloat = new NumberValue<>("BooleanTest", 10F, 1000F, 0F, this);
    public NumberValue<Double> vDouble = new NumberValue<>("BooleanTest", 10D, 100D, 0D, this);
    public NumberValue<Integer> vInteger = new NumberValue<>("BooleanTest", 10, 100, 0, this);
    public NumberValue<Long> vLong = new NumberValue<>("BooleanTest", 10L, 100000L, 0L, this);
    // IGNORE

    public NumberValue<Integer> cps = new NumberValue<>("BooleanTest", 1, 10, 20, 1, this);
    public NumberValue<Float> cpsF = new NumberValue<>("BooleanTest", 1F, 10F, 20F, 1F, this);
    public NumberValue<Long> cpsL = new NumberValue<>("BooleanTest", 1L, 10L, 20L, 1L, this);

    public TitleValue titleValue = new TitleValue("Title CPS", true, new Value[]{cps, cpsF, cpsL}, this);

    private int direction;

    public Debug() {
        super("Debug", "", Category.UTILITIES);

        Koks.getKoks().valueManager.addValue(b);

        Koks.getKoks().valueManager.addValue(box);
        Koks.getKoks().valueManager.addValue(boxCheckBox);

        Koks.getKoks().valueManager.addValue(vFloat);
        Koks.getKoks().valueManager.addValue(vDouble);
        Koks.getKoks().valueManager.addValue(vInteger);
        Koks.getKoks().valueManager.addValue(vLong);

        Koks.getKoks().valueManager.addValue(titleValue);
        Koks.getKoks().valueManager.addValue(cps);
        Koks.getKoks().valueManager.addValue(cpsF);
        Koks.getKoks().valueManager.addValue(cpsL);
    }

    //REGISTER
    //MC|Brand
    //GoMod
    //LMC

    @Override
    public void onEvent(Event event) {
        if(event instanceof EventUpdate){
           /* PacketBuffer packetbuffer = new PacketBuffer(Unpooled.buffer());
            packetbuffer.writeInt(0);
            packetbuffer.writerIndex(333);
            packetbuffer.capacity(16);
            System.out.println(packetbuffer);
            mc.thePlayer.sendQueue.addToSendQueue(new C17PacketCustomPayload("REGISTER", packetbuffer));
            mc.thePlayer.sendQueue.addToSendQueue(new C17PacketCustomPayload("MC|Brand", packetbuffer));
            mc.thePlayer.sendQueue.addToSendQueue(new C17PacketCustomPayload("GoMod", packetbuffer));
            mc.thePlayer.sendQueue.addToSendQueue(new C17PacketCustomPayload("LMC", packetbuffer));
*/
        }
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {

    }
}

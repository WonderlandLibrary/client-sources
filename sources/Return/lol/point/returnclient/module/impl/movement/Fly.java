package lol.point.returnclient.module.impl.movement;

import lol.point.returnclient.events.impl.update.EventUpdate;
import lol.point.returnclient.module.Category;
import lol.point.returnclient.module.Module;
import lol.point.returnclient.module.ModuleInfo;
import lol.point.returnclient.settings.impl.StringSetting;
import lol.point.returnclient.util.minecraft.MoveUtil;
import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import org.lwjgl.input.Keyboard;

@ModuleInfo(
        name = "Fly",
        description = "Makes you fly",
        category = Category.MOVEMENT
)
public class Fly extends Module {

    private final StringSetting mode = new StringSetting("Mode", "Vanilla", new String[]{"Verus", "Vanilla"});

    public Fly() {
        addSettings(mode);
    }

    public String getSuffix() {
        return mode.value;
    }

    @Subscribe
    private final Listener<EventUpdate> onUpdate = new Listener<>(eventUpdate -> {
        switch (mode.value) {
            case "Verus" -> {
                mc.thePlayer.motionY = 0;
                if(mc.gameSettings.keyBindForward.isKeyDown()){
                    MoveUtil.setSpeed(0.376);
                }else{
                    MoveUtil.setSpeed(0.325);
                }
                mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.getPosition().down(), 1, new ItemStack(Items.water_bucket), 0.5F, 0.5F, 0.5F));
                if (mc.gameSettings.keyBindJump.isKeyDown()) {
                    mc.thePlayer.motionY = 0.2f;
                    if(mc.thePlayer.ticksExisted % 3 == 0){
                        mc.thePlayer.motionY = 0.33f;
                    }
                }
                if(mc.gameSettings.keyBindSneak.isKeyDown()){
                    mc.thePlayer.motionY = -0.6f;
                    MoveUtil.setSpeed(0.34);
                }
            }
            case "Vanilla" -> {
                MoveUtil.setSpeed(2.5);
                mc.thePlayer.motionY = 0;
                if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) && !mc.gameSettings.keyBindSneak.isKeyDown()) {
                    mc.thePlayer.motionY = 1;
                }
                if (mc.gameSettings.keyBindSneak.isKeyDown() && !Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
                    mc.thePlayer.motionY = -1;
                }
            }
        }
    });
}

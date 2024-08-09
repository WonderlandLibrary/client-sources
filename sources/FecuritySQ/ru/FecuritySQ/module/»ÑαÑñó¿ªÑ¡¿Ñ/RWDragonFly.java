package ru.FecuritySQ.module.передвижение;

import net.minecraft.network.play.client.CPlayerAbilitiesPacket;
import org.lwjgl.glfw.GLFW;
import ru.FecuritySQ.event.Event;
import ru.FecuritySQ.event.imp.EventPacket;
import ru.FecuritySQ.event.imp.EventUpdate;
import ru.FecuritySQ.module.Module;
import ru.FecuritySQ.option.imp.OptionBoolean;
import ru.FecuritySQ.option.imp.OptionNumric;
import ru.FecuritySQ.utils.MathUtil;

public class RWDragonFly extends Module {

    public OptionNumric speed = new OptionNumric("Скорость по XZ)", 1F, 0F, 8F, 0.1F);
    public OptionNumric speedY = new OptionNumric("Скорость по Y", 1F, 0F, 8F, 0.1F);
    public OptionBoolean disableCT = new OptionBoolean("Отключать в кт", true);
    public RWDragonFly() {
        super(Category.Передвижение, GLFW.GLFW_KEY_0);
        addOption(disableCT);
        addOption(speed);
        addOption(speedY);
    }

    @Override
    public void event(Event e) {
        if(e instanceof EventUpdate && isEnabled() &&  mc.player != null && mc.world != null) {
            if(disableCT.get() && mc.player.hurtTime > 0){
                toggle();
            }
            mc.player.getMotion().y = 0.0;
            if (mc.gameSettings.keyBindJump.isKeyDown()) {
                mc.player.getMotion().y += speedY.get();
            }
            if (mc.gameSettings.keyBindSneak.isKeyDown()) {
                mc.player.getMotion().y -= speedY.get();
            }
            MathUtil.setMotion(speed.get());
        }
    }

    @Override
    public void disable() {
        super.disable();
    }

    @Override
    public void enable() {
        super.enable();
    }
}

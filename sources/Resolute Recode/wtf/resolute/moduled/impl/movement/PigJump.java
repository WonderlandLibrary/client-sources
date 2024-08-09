package wtf.resolute.moduled.impl.movement;

import com.google.common.eventbus.Subscribe;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.item.Items;
import net.minecraft.network.play.client.CEntityActionPacket;
import net.minecraft.network.play.client.CPlayerPacket;
import wtf.resolute.evented.EventUpdate;
import wtf.resolute.moduled.Categories;
import wtf.resolute.moduled.Module;
import wtf.resolute.moduled.ModuleAnontion;

@ModuleAnontion(name = "PigJump", type = Categories.Movement, server = "")
public class PigJump extends Module {
    //Нахуй я это коментирую блять
    private final Minecraft mc = Minecraft.getInstance();

    @Subscribe
    public void onUpdate(EventUpdate e) {
        if (mc.player.isPassenger() && mc.player.getRidingEntity() instanceof PigEntity) {
            PigEntity pig = (PigEntity) mc.player.getRidingEntity();
            if (mc.gameSettings.keyBindJump.isKeyDown()) {
                // Начинаем спринт
                mc.player.connection.sendPacket(new CEntityActionPacket(mc.player, CEntityActionPacket.Action.START_SPRINTING));
                // Имитация прыжка
                mc.player.connection.sendPacket(new CPlayerPacket.PositionRotationPacket(mc.player.getPosX(), mc.player.getPosY() + 1.0D, mc.player.getPosZ(), mc.player.rotationYaw, mc.player.rotationPitch, mc.player.isOnGround()));
                // Останавливаем спринт
                mc.player.connection.sendPacket(new CEntityActionPacket(mc.player, CEntityActionPacket.Action.STOP_SPRINTING));
                // Устанавливаем вертикальную скорость свиньи для имитации прыжка
                pig.setMotion(pig.getMotion().x, 0.5D, pig.getMotion().z);
                // Сбрасываем урон от падения
                pig.fallDistance = 0.0F;
                // Отключаем keyBindJump, чтобы избежать повторного срабатывания
                mc.gameSettings.keyBindJump.setPressed(false);
            }
        }
    }
}

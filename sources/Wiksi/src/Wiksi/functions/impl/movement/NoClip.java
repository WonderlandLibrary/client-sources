package src.Wiksi.functions.impl.movement;

import com.google.common.eventbus.Subscribe;
import src.Wiksi.events.*;
import src.Wiksi.functions.api.Category;
import src.Wiksi.functions.api.Function;
import src.Wiksi.functions.api.FunctionRegister;
import src.Wiksi.functions.settings.impl.ModeSetting;
import net.minecraft.network.play.client.CPlayerPacket;
import net.minecraft.util.math.vector.Vector3d;

@FunctionRegister(name = "NoClip", type = Category.Movement)
public class NoClip extends Function {

    final ModeSetting mode = new ModeSetting("Обход", "Обычный", "Обычный", "Пакетный");
    private Vector3d targetPosition;
    private int tickCounter = 0;
    private final double FORWARD_DISTANCE = 1.0;
    public NoClip() {
        addSettings(mode);
    }

    @Subscribe
    private void onMoving(MovingEvent move) {
        if (mode.is("Обычный")) {
            if (!collisionPredict(move.getTo())) {
                if (move.isCollidedHorizontal())
                    move.setIgnoreHorizontal(true);
                if (move.getMotion().y > 0 || mc.player.isSneaking()) {
                    move.setIgnoreVertical(true);
                }
                move.getMotion().y = Math.min(move.getMotion().y, 99999);
            }
            if (mode.is("Пакетный")) {
                double posX = mc.player.getPosX();
                double posY = mc.player.getPosY();
                double posZ = mc.player.getPosZ();

                // Добавляем полблока к координате Y для движения вверх
                posY += 0.25;

                // Отправляем пакет для перемещения
                mc.getConnection().sendPacket(new CPlayerPacket.PositionPacket(posX, posY, posZ, true));
            }
        }
    }
    private void sendPositionPacketWithDelay(double x, double y, double z, int delay) {
        new Thread(() -> {
            try {
                Thread.sleep(delay); // Ждем указанное количество миллисекунд
                mc.getConnection().sendPacket(new CPlayerPacket.PositionPacket(x, y, z, true)); // Отправляем пакет для перемещения
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
    @Subscribe
    private void onTick(TickEvent event) {
        if (mode.is("Пакетный")) {
            // Перемещаем игрока к целевой позиции

        }
    }
    public boolean collisionPredict(Vector3d to) {
        boolean prevCollision = mc.world
                .getCollisionShapes(mc.player, mc.player.getBoundingBox().shrink(0.0625D)).toList().isEmpty();
        Vector3d backUp = new Vector3d(mc.player.getPosX(), mc.player.getPosY(), mc.player.getPosZ());
        mc.player.setPosition(to.x, to.y, to.z);
        boolean collision = mc.world.getCollisionShapes(mc.player, mc.player.getBoundingBox().shrink(0.0625D))
                .toList().isEmpty() && prevCollision;
        mc.player.setPosition(backUp.x, backUp.y, backUp.z);
        return collision;
    }
}


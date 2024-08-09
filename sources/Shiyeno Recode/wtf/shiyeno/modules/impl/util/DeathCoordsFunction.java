package wtf.shiyeno.modules.impl.util;

import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.util.text.TextFormatting;
import wtf.shiyeno.events.Event;
import wtf.shiyeno.events.impl.player.EventUpdate;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;
import wtf.shiyeno.util.ClientUtil;

@FunctionAnnotation(
        name = "DeathCoords",
        type = Type.Util
)
public class DeathCoordsFunction extends Function {
    public DeathCoordsFunction() {
    }

    public void onEvent(Event event) {
        if (event instanceof EventUpdate) {
            this.checkDeathCoordinates();
        }
    }

    public void checkDeathCoordinates() {
        if (this.isPlayerDead()) {
            int positionX = mc.player.getPosition().getX();
            int positionY = mc.player.getPosition().getY();
            int positionZ = mc.player.getPosition().getZ();
            if (mc.player.deathTime < 1) {
                this.printDeathCoordinates(positionX, positionY, positionZ);
            }
        }
    }

    private boolean isPlayerDead() {
        return mc.player.getHealth() < 1.0F && mc.currentScreen instanceof DeathScreen;
    }

    private void printDeathCoordinates(int x, int y, int z) {
        String message = "Координаты смерти: " + TextFormatting.GRAY + "X: " + x + " Y: " + y + " Z: " + z + TextFormatting.RESET;
        ClientUtil.sendMesage(message);
    }
}
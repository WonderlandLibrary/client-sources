package alphentus.mod.mods.player;

import alphentus.event.Event;
import alphentus.event.Type;
import alphentus.init.Init;
import alphentus.mod.Mod;
import alphentus.mod.ModCategory;
import alphentus.settings.Setting;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Keyboard;

import javax.vecmath.Vector3f;
import java.util.ArrayList;

/**
 * @author avox | lmao
 * @since on 03.08.2020.
 */
public class Teleport extends Mod {

    String[] tpModes = { "Packet", "SetPosition", "NeruxVace" };
    public Setting teleportMode = new Setting("Teleport Mode", tpModes, "Packet", this);
    boolean shouldFly;
    double x;
    double y;
    double z;

    public Teleport () {
        super("Teleport", Keyboard.KEY_NONE, true, ModCategory.PLAYER);

        Init.getInstance().settingManager.addSetting(teleportMode);
    }

    @EventTarget
    public void event (Event event) {
        // PlayerControllerMP getBlockReachDistance
        if (!getState())
            return;

        if (event.getType() == Type.PRE) {
            if (teleportMode.getSelectedCombo().equals("NeruxVace") && (x != 0 && y != 0 && z != 0)) {
                event.setYaw((float) getYaw(x, z));
            }
        }

        if (event.getType() != Type.TICKUPDATE)
            return;

        double blockX = mc.objectMouseOver.getBlockPos().getX();
        double blockY = mc.objectMouseOver.getBlockPos().getY();
        double blockZ = mc.objectMouseOver.getBlockPos().getZ();

        if (mc.gameSettings.keyBindAttack.isKeyDown()) {
            x = blockX + 0.5;
            y = blockY + 1;
            z = blockZ + 0.5;

            mc.thePlayer.addChatMessage(new ChatComponentText("Selected Coordiantes > X: " + Math.round(x) + " Y: " + Math.round(y) + " Z: " + Math.round(z)));
        }

        if (mc.gameSettings.keyBindSneak.isKeyDown()) {

            if (x != 0 && y != 0 && z != 0) {
                if (teleportMode.getSelectedCombo().equals("Packet")) {
                    mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y, z, true));

                    x = 0;
                    y = 0;
                    z = 0;
                    mc.gameSettings.keyBindSneak.pressed = false;
                }

                if (teleportMode.getSelectedCombo().equals("SetPosition")) {

                    double currentX = mc.thePlayer.posX;
                    double currentZ = mc.thePlayer.posZ;
                    double currentY = mc.thePlayer.posY;

                 mc.thePlayer.setPosition(x,y,z);
                    x = 0;
                    y = 0;
                    z = 0;

                    mc.gameSettings.keyBindSneak.pressed = false;
                }

                if (teleportMode.getSelectedCombo().equals("NeruxVace")) {
                    shouldFly = true;
                    mc.gameSettings.keyBindSneak.pressed = false;
                }
            }

            mc.thePlayer.setSneaking(false);
        }

        if (teleportMode.getSelectedCombo().equals("NeruxVace")) {
            double yaw = Math.toRadians(getYaw(x, z));
            double x = -Math.sin(yaw) * 8;
            double z = Math.cos(yaw) * 8;
            if (shouldFly) {
                mc.thePlayer.motionY = 0;
                mc.thePlayer.onGround = true;
                mc.thePlayer.motionX = x;
                mc.thePlayer.motionZ = z;
            } else {
                mc.timer.timerSpeed = 1.0F;
            }

            if (mc.gameSettings.keyBindForward.isKeyDown()) {
                shouldFly = false;
                mc.thePlayer.motionX = 0;
                mc.thePlayer.motionZ = 0;
                setState(false);
            }
        }
    }

    public double getYaw (double blockX, double blockZ) {
        return (MathHelper.func_181159_b(blockZ - mc.thePlayer.posZ, blockX - mc.thePlayer.posX) * 180.0D / Math.PI) - 90.0F;
    }

    @Override
    public void onEnable () {
        x = 0;
        y = 0;
        z = 0;
        shouldFly = false;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        mc.timer.timerSpeed = 1.0F;
        super.onDisable();
    }
}
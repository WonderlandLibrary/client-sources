package alphentus.mod.mods.movement;

import alphentus.event.Event;
import alphentus.event.Type;
import alphentus.init.Init;
import alphentus.mod.Mod;
import alphentus.mod.ModCategory;
import alphentus.mod.mods.combat.KillAura;
import alphentus.utils.MovementUtils;
import alphentus.utils.RotationUtil;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.input.Keyboard;

/**
 * @author avox | lmao
 * @since on 29/07/2020.
 */
public class Sprint extends Mod {

    public Sprint() {
        super("Sprint", Keyboard.KEY_NONE, true, ModCategory.MOVEMENT);
    }

    @EventTarget
    public void event (Event event) {
        if (event.getType() != Type.TICKUPDATE)
            return;
        if (!getState())
            return;

        if (mc.thePlayer.isDead || mc.currentScreen instanceof GuiGameOver) {
            mc.thePlayer.respawnPlayer();
            mc.displayGuiScreen(null);
        }

        if (mc.thePlayer.getFoodStats().getFoodLevel() > 6 && !mc.thePlayer.isSneaking() && mc.thePlayer.isMoving() && !(Init.getInstance().modManager.getModuleByClass(KillAura.class).stopSprint.isState() && Init.getInstance().modManager.getModuleByClass(KillAura.class).getState() && Init.getInstance().modManager.getModuleByClass(KillAura.class).finalEntity != null) ) {
            mc.thePlayer.setSprinting(true);
        } else {
            mc.thePlayer.setSprinting(false);
        }
    }
}
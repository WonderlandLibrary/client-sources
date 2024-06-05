package alphentus.mod.mods.combat;

import alphentus.event.Event;
import alphentus.event.Type;
import alphentus.init.Init;
import alphentus.mod.Mod;
import alphentus.mod.ModCategory;
import alphentus.settings.Setting;
import alphentus.utils.RandomUtil;
import alphentus.utils.RotationUtil;
import alphentus.utils.TimeUtil;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import org.lwjgl.input.Keyboard;

/**
 * @author avox | lmao
 * @since on 14.08.2020.
 */
public class Triggerbot extends Mod {

    public Setting minDelay = new Setting("Minimal Delay", 1, 100, 70, true, this);
    public Setting maxDelay = new Setting("Maximal Delay", 1, 100, 100, true, this);
    public Setting blockHit = new Setting("BlockHit", false, this);
    public Setting keepSprint = new Setting("KeepSprint", false, this);
    public Setting aimAssist = new Setting("Assisted Aiming", false, this);
    public Entity e;
    RandomUtil randomUtil = new RandomUtil();
    TimeUtil timeUtil = new TimeUtil();

    public Triggerbot() {
        super("Triggerbot", Keyboard.KEY_NONE, true, ModCategory.COMBAT);

        Init.getInstance().settingManager.addSetting(minDelay);
        Init.getInstance().settingManager.addSetting(maxDelay);
        Init.getInstance().settingManager.addSetting(blockHit);
        Init.getInstance().settingManager.addSetting(keepSprint);
        Init.getInstance().settingManager.addSetting(aimAssist);
    }

    @EventTarget
    public void event(Event event) {
        if (!getState())
            return;

        if (event.getType() == Type.PRE) {
            if (e != null) {
                if (mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword && mc.thePlayer.getCurrentEquippedItem() != null && blockHit.isState())
                    mc.thePlayer.getCurrentEquippedItem().useItemRightClick(mc.theWorld, mc.thePlayer);

                if (aimAssist.isState()) {
                    mc.thePlayer.rotationYaw = RotationUtil.faceEntity(e, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, 360, 360, true)[0];
                    mc.thePlayer.rotationPitch = RotationUtil.faceEntity(e, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, 360, 360, true)[1] * 0.75F;
                }
            }
        }

        if (event.getType() == Type.TICKUPDATE) {

            if (mc.objectMouseOver.entityHit != null) {
                e = mc.objectMouseOver.entityHit;
            } else {
                e = null;
            }

            if (e != null && Init.getInstance().friendSystem.isFriend(e) && !Init.getInstance().modManager.getModuleByClass(NoFriends.class).getState())
                e = null;

            double finalDelay = mc.thePlayer.hurtTime != 0 ? 5 : randomUtil.randomDouble(minDelay.getCurrent(), maxDelay.getCurrent());
            if (timeUtil.isDelayComplete(finalDelay) && e != null) {
                mc.thePlayer.swingItem();
                mc.playerController.attackEntity(mc.thePlayer, e);
                timeUtil.reset();
            }

            if (mc.currentScreen instanceof GuiGameOver || !mc.thePlayer.isServerWorld() || mc.theWorld == null)
                setState(false);
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
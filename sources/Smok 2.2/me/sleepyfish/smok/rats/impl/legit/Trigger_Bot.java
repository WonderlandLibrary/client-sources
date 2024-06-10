package me.sleepyfish.smok.rats.impl.legit;

import maxstats.weave.event.EventTick;
import me.sleepyfish.smok.Smok;
import me.sleepyfish.smok.rats.Rat;
import me.sleepyfish.smok.utils.entities.Utils;
import me.sleepyfish.smok.utils.misc.Timer;
import me.sleepyfish.smok.utils.entities.BotUtils;
import me.sleepyfish.smok.utils.entities.friend.FriendUtils;
import me.sleepyfish.smok.rats.event.SmokEvent;
import me.sleepyfish.smok.rats.settings.BoolSetting;
import me.sleepyfish.smok.rats.settings.SpaceSetting;
import me.sleepyfish.smok.rats.settings.DoubleSetting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Mouse;
import java.util.Iterator;

// Class from SMok Client by SleepyFish
public class Trigger_Bot extends Rat {

    DoubleSetting cpsMin;
    DoubleSetting cpsMax;

    BoolSetting weaponOnly;

    BoolSetting blatantMode;
    DoubleSetting fovRange;
    BoolSetting troughWalls;
    DoubleSetting attackRange;

    public static BoolSetting ignoreFriends;
    BoolSetting ignoreBots;

    private Entity target;
    private boolean allowClick;

    private Timer timer = new Timer();

    public Trigger_Bot() {
        super("Trigger Bot", Rat.Category.Legit, "Clicks for you when your crosshair is on a target");
    }

    @Override
    public void setup() {
        this.addSetting(this.cpsMin = new DoubleSetting("CPS Min", 9.0, 1.0, 24.0, 1.0));
        this.addSetting(this.cpsMax = new DoubleSetting("CPS Max", 13.0, 2.0, 25.0, 1.0));
        this.addSetting(this.weaponOnly = new BoolSetting("Weapon only", true));
        this.addSetting(ignoreFriends = new BoolSetting("Ignore friends", true));
        this.addSetting(this.ignoreBots = new BoolSetting("Ignore Bots", true));
        this.addSetting(new SpaceSetting());
        this.addSetting(this.blatantMode = new BoolSetting("Blatant Mode", false));
        this.addSetting(this.fovRange = new DoubleSetting("Fov range", "Also called 'Max Angle'", 15.0, 5.0, 50.0, 0.5));
        this.addSetting(this.troughWalls = new BoolSetting("Trough walls", "Only works with blatant mode", false));
        this.addSetting(this.attackRange = new DoubleSetting("Attack Range", "Only works with blatant mode", 3.2, 3.0, 5.0, 0.2));
    }

    @Override
    public void onEnableEvent() {
        this.allowClick = true;
    }

    @Override
    public void onDisableEvent() {
        this.allowClick = false;
        KeyBinding.onTick(mc.gameSettings.keyBindAttack.getKeyCode());
    }

    @SmokEvent
    public void onTick(EventTick e) {
        if (Utils.canLegitWork()) {
            if (!this.weaponOnly.isEnabled() || Utils.holdingWeapon()) {
                if (!this.blatantMode.isEnabled()) {
                    this.target = mc.pointedEntity;

                    if (Smok.inst.rotManager.isRotating()) {
                        return;
                    }

                    if (mc.thePlayer.isBlocking()) {
                        return;
                    }
                } else {
                    this.target = this.getTarget();
                }

                if (this.target != null) {
                    if (this.ignoreBots.isEnabled()) {
                        if (BotUtils.isBot(this.target))
                            return;
                    }

                    if (this.allowClick) {
                        this.attack();
                        this.allowClick = false;
                    } else if (this.timer.cpsTimer(this.cpsMin.getValueToInt(), this.cpsMax.getValueToInt())) {
                        this.attack();
                        this.timer.reset();
                    }

                } else {
                    this.allowClick = true;

                    if (!Mouse.isButtonDown(0)) {
                        KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(), false);
                    }
                }
            }
        }
    }

    private void attack() {
        if (!this.blatantMode.isEnabled()) {
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(), true);
            KeyBinding.onTick(mc.gameSettings.keyBindAttack.getKeyCode());
        } else {
            mc.thePlayer.swingItem();
            mc.playerController.attackEntity(mc.thePlayer, this.target);
        }
    }

    private Entity getTarget() {
        Iterator<EntityPlayer> players = mc.theWorld.playerEntities.iterator();

        EntityPlayer target;
        do {
            do {
                do {
                    do {
                        do {
                            do {
                                do {
                                    if (!players.hasNext())
                                        return null;

                                    target = players.next();
                                } while (target == mc.thePlayer);
                            } while (BotUtils.isBot(target));
                        } while (target.isDead);
                    } while (ignoreFriends.isEnabled() && (FriendUtils.ignoreFriend(target) || mc.thePlayer.isOnSameTeam(target)));
                } while (target.isInvisible());
            } while (!Utils.Combat.inRange(target, blatantMode.isEnabled() ? attackRange.getValue() : 3.01));
        } while (!Utils.Combat.isInFov(target, (float) fovRange.getValue()));

        return target;
    }

}
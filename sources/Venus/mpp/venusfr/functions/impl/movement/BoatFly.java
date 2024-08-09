/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.movement;

import com.google.common.eventbus.Subscribe;
import mpp.venusfr.events.EventPacket;
import mpp.venusfr.events.EventUpdate;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import mpp.venusfr.functions.settings.impl.BooleanSetting;
import mpp.venusfr.functions.settings.impl.SliderSetting;
import mpp.venusfr.utils.player.MoveUtils;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.network.play.client.CEntityActionPacket;

@FunctionRegister(name="Pig Fly", type=Category.Movement)
public class BoatFly
extends Function {
    final SliderSetting speed = new SliderSetting("\u0421\u043a\u043e\u0440\u043e\u0441\u0442\u044c", 10.0f, 1.0f, 20.0f, 0.05f);
    final BooleanSetting noDismount = new BooleanSetting("\u041d\u0435 \u0432\u044b\u043b\u0435\u0437\u0430\u0442\u044c", true);
    final BooleanSetting savePig = new BooleanSetting("\u0421\u043f\u0430\u0441\u0430\u0442\u044c \u0441\u0432\u0438\u043d\u044c\u044e", true);

    public BoatFly() {
        this.addSettings(this.speed, this.noDismount, this.savePig);
    }

    @Subscribe
    private void onUpdate(EventUpdate eventUpdate) {
        if (BoatFly.mc.player.getRidingEntity() != null && BoatFly.mc.player.getRidingEntity() instanceof PigEntity) {
            BoatFly.mc.player.getRidingEntity().motion.y = 0.0;
            if (BoatFly.mc.player.isPassenger()) {
                if (BoatFly.mc.gameSettings.keyBindJump.isKeyDown()) {
                    BoatFly.mc.player.getRidingEntity().motion.y = 1.0;
                } else if (BoatFly.mc.gameSettings.keyBindSneak.isKeyDown()) {
                    BoatFly.mc.player.getRidingEntity().motion.y = -1.0;
                }
                if (MoveUtils.isMoving()) {
                    double d = MoveUtils.getDirection(true);
                    BoatFly.mc.player.getRidingEntity().motion.x = -Math.sin(d) * (double)((Float)this.speed.get()).floatValue();
                    BoatFly.mc.player.getRidingEntity().motion.z = Math.cos(d) * (double)((Float)this.speed.get()).floatValue();
                } else {
                    BoatFly.mc.player.getRidingEntity().motion.x = 0.0;
                    BoatFly.mc.player.getRidingEntity().motion.z = 0.0;
                }
                if ((!MoveUtils.isBlockUnder(4.0f) || BoatFly.mc.player.collidedHorizontally || BoatFly.mc.player.collidedVertically) && ((Boolean)this.savePig.get()).booleanValue()) {
                    BoatFly.mc.player.getRidingEntity().motion.y += 1.0;
                }
            }
        }
    }

    @Subscribe
    private void onPacket(EventPacket eventPacket) {
        Object object = eventPacket.getPacket();
        if (object instanceof CEntityActionPacket) {
            CEntityActionPacket cEntityActionPacket = (CEntityActionPacket)object;
            if (!((Boolean)this.noDismount.get()).booleanValue() || !(BoatFly.mc.player.getRidingEntity() instanceof BoatEntity)) {
                return;
            }
            object = cEntityActionPacket.getAction();
            if (object == CEntityActionPacket.Action.PRESS_SHIFT_KEY || object == CEntityActionPacket.Action.RELEASE_SHIFT_KEY) {
                eventPacket.cancel();
            }
        }
    }

    public boolean notStopRidding() {
        return this.isState() && (Boolean)this.noDismount.get() != false;
    }
}


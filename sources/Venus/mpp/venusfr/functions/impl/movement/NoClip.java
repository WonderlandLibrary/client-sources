/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.movement;

import com.google.common.eventbus.Subscribe;
import mpp.venusfr.events.MovingEvent;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import net.minecraft.util.math.vector.Vector3d;

@FunctionRegister(name="NoClip", type=Category.Movement)
public class NoClip
extends Function {
    @Subscribe
    private void onMoving(MovingEvent movingEvent) {
        if (!this.collisionPredict(movingEvent.getTo())) {
            if (movingEvent.isCollidedHorizontal()) {
                movingEvent.setIgnoreHorizontal(false);
            }
            if (movingEvent.getMotion().y > 0.0 || NoClip.mc.player.isSneaking()) {
                movingEvent.setIgnoreVertical(false);
            }
            movingEvent.getMotion().y = Math.min(movingEvent.getMotion().y, 99999.0);
        }
    }

    public boolean collisionPredict(Vector3d vector3d) {
        boolean bl = NoClip.mc.world.getCollisionShapes(NoClip.mc.player, NoClip.mc.player.getBoundingBox().shrink(0.0625)).toList().isEmpty();
        Vector3d vector3d2 = new Vector3d(NoClip.mc.player.getPosX(), NoClip.mc.player.getPosY(), NoClip.mc.player.getPosZ());
        NoClip.mc.player.setPosition(vector3d.x, vector3d.y, vector3d.z);
        boolean bl2 = NoClip.mc.world.getCollisionShapes(NoClip.mc.player, NoClip.mc.player.getBoundingBox().shrink(0.0625)).toList().isEmpty() && bl;
        NoClip.mc.player.setPosition(vector3d2.x, vector3d2.y, vector3d2.z);
        return bl2;
    }
}


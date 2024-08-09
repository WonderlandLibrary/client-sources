package fun.ellant.functions.impl.movement;

import com.google.common.eventbus.Subscribe;
import fun.ellant.events.EventMotion;
import fun.ellant.functions.api.Category;
import fun.ellant.functions.api.Function;
import fun.ellant.functions.api.FunctionRegister;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.util.math.vector.Vector3d;

@FieldDefaults(level = AccessLevel.PRIVATE)
@FunctionRegister(name = "BoatSpeed", type = Category.MOVEMENT, desc = "Ускоряет тебя на лодке")
public class BoatSpeed extends Function {
    final double BOAT_SPEED_MULTIPLIER = 2.0;

    @Subscribe
    public void onMotion(EventMotion e) {
        Entity ridingEntity = mc.player.getRidingEntity();
        if (ridingEntity instanceof BoatEntity) {
            Vector3d boatMotion = ridingEntity.getMotion();
            Vector3d modifiedBoatMotion = new Vector3d(boatMotion.x * BOAT_SPEED_MULTIPLIER, boatMotion.y, boatMotion.z * BOAT_SPEED_MULTIPLIER);
            ridingEntity.setMotion(modifiedBoatMotion);
        }
    }
}

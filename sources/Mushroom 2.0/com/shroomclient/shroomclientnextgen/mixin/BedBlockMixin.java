package com.shroomclient.shroomclientnextgen.mixin;

import com.shroomclient.shroomclientnextgen.modules.impl.movement.Flight;
import net.minecraft.block.BedBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(BedBlock.class)
public class BedBlockMixin {

    /**
     * @author 112batman
     * @reason Bed fly
     */
    @Overwrite
    private void bounceEntity(Entity entity) {
        Vec3d vec3d = entity.getVelocity();
        if (vec3d.y < 0.0) {
            double d = entity instanceof LivingEntity ? 1.0 : 0.8;
            entity.setVelocity(
                vec3d.x,
                -vec3d.y * Flight.getVeloMultiplier() * d,
                vec3d.z
            );
        }
    }
}

package dev.excellent.api.event.impl.funs;

import dev.excellent.api.event.Event;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.minecraft.client.renderer.entity.model.BipedModel;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public final class PlayerArmPoseEvent extends Event {
    BipedModel.ArmPose armPose;
}

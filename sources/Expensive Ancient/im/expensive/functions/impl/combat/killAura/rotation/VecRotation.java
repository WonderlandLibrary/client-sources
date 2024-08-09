package im.expensive.functions.impl.combat.killAura.rotation;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class VecRotation {
    private float yaw;
    private float pitch;

    public boolean equals(VecRotation target) {
        return target.yaw == this.yaw && target.pitch == this.pitch;
    }

    @Override
    public String toString() {
        return "yaw=" + yaw + " pitch=" + pitch;
    }
}

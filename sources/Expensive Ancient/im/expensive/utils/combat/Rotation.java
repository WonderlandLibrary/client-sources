package im.expensive.utils.combat;

import lombok.*;
import lombok.experimental.FieldDefaults;
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class Rotation {
    private float yaw;
    private float pitch;

    public boolean equals(Rotation target) {
        return target.yaw == this.yaw && target.pitch == this.pitch;
    }

    @Override
    public String toString() {
        return "yaw=" + yaw + " pitch=" + pitch;
    }
}

package fun.ellant.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ActionEvent {
    private boolean sprintState;

    public boolean isRightClick() {
        return false;
    }
}
package me.teus.eclipse.modules.value;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Value {
    public String name;
    private boolean holdingMouseButton;

    public static boolean shown(){
        return true;
    }

    public boolean isHoldingMouseButton() {
        return holdingMouseButton;
    }

    public void setHoldingMouseButton(boolean holdingMouseButton) {
        this.holdingMouseButton = holdingMouseButton;
    }
}

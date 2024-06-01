package best.actinium.component;

import best.actinium.util.IAccess;
import best.actinium.Actinium;
import lombok.Getter;
import lombok.Setter;

public class Component implements IAccess {
    @Getter
    @Setter
    private static boolean active = true;
    public Component() {
        Actinium.INSTANCE.getEventManager().subscribe(this);
    }

}
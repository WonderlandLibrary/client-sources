package im.expensive.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.inventory.container.ClickType;

@AllArgsConstructor
@Getter
@Setter
public class WindowClickEvent extends CancelEvent {

    public int windowId;
    public int slot;
    public int button;
    public ClickType clickType;
    public Type type;

    public enum Type {
        PRE, POST
    }
}

package fun.ellant.events;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InventoryCloseEvent extends CancelEvent {

    public int windowId;

}

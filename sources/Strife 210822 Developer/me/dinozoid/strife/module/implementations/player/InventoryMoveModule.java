package me.dinozoid.strife.module.implementations.player;

import me.dinozoid.strife.StrifeClient;
import me.dinozoid.strife.module.Category;
import me.dinozoid.strife.module.Module;
import me.dinozoid.strife.module.ModuleInfo;

@ModuleInfo(name = "InventoryMove", renderName = "InventoryMove", description = "Move with your inventory open.", aliases = {"InvMove", "GuiMove"}, category = Category.PLAYER)
public class InventoryMoveModule extends Module {
    public static InventoryMoveModule getInstance() {
        return StrifeClient.INSTANCE.moduleRepository().moduleBy(InventoryMoveModule.class);
    }
}

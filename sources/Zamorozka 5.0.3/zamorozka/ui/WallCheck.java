package zamorozka.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;

public class WallCheck implements ICheck {
    public boolean validate(Entity entity) {
        return Minecraft.player.canEntityBeSeen(entity);
    }
}

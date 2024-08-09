package dev.darkmoon.client.module.impl.combat;

import dev.darkmoon.client.module.setting.impl.NumberSetting;
import dev.darkmoon.client.DarkMoon;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

@ModuleAnnotation(name = "HitBox", category = Category.COMBAT)
public class HitBox extends Module {
    private final NumberSetting size = new NumberSetting("Size", 0.5F, 0.1F, 1.0F, 0.1F);

    public float getHitboxSize(Entity entity) {
        return this.isEnabled() && entity instanceof EntityPlayer && entity != mc.player && !DarkMoon.getInstance().getFriendManager().isFriend(entity.getName()) ? size.get() : 0.0F;
    }
}

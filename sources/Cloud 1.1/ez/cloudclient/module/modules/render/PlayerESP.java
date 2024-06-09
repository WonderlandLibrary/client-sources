package ez.cloudclient.module.modules.render;

import ez.cloudclient.module.Module;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class PlayerESP extends Module {
    public PlayerESP() {
        super("PlayerESP", Category.RENDER, "");
    }

    @Override
    protected void onDisable() {
        for (Entity e : mc.world.getLoadedEntityList()) {
            if (e instanceof EntityPlayer && e != mc.player) {
                e.setGlowing(false);
            }
        }
    }

    @Override
    public void onTick() {
        for (Entity e : mc.world.getLoadedEntityList()) {
            if (e instanceof EntityPlayer && e != mc.player && !e.isGlowing()) {
                e.setGlowing(true);
            }
        }
    }
}

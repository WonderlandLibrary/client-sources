package mathax.client.systems.modules.movement;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.world.TickEvent;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;


public class FakeExplosion extends Module {

    public FakeExplosion() {
        super(Categories.Fun, Items.ARMOR_STAND, "Fake Explosion", "Fakes a Small Explosion that makes Client Ghost Block Fire");
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player.isOnGround() && mc.player.isSneaking()) {
            mc.world.createExplosion(mc.player, mc.player.getX(), mc.player.getY(), mc.player.getZ(), 1, true, World.ExplosionSourceType.NONE);
        }
    }
}

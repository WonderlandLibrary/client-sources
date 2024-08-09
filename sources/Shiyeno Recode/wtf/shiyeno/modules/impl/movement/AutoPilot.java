package wtf.shiyeno.modules.impl.movement;

import java.util.Iterator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.SkullItem;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.math.MathHelper;
import wtf.shiyeno.events.Event;
import wtf.shiyeno.events.impl.player.EventUpdate;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;

@FunctionAnnotation(
        name = "AutoPilot",
        type = Type.Movement
)
public class AutoPilot extends Function {
    public AutoPilot() {
    }

    public void onEvent(Event event) {
        boolean skullItemNoNull = false;
        boolean eggItemNoNull = false;
        boolean elytraItemNoNull = false;
        Iterator var5;
        Entity entity;
        if (event instanceof EventUpdate) {
            var5 = mc.world.getAllEntities().iterator();

            while(var5.hasNext()) {
                entity = (Entity)var5.next();
                if (entity instanceof ItemEntity) {
                    if (((ItemEntity)entity).getItem().getItem() instanceof SkullItem) {
                        skullItemNoNull = true;
                    }

                    if (((ItemEntity)entity).getItem().getItem() instanceof ElytraItem) {
                        elytraItemNoNull = true;
                    }

                    if (((ItemEntity)entity).getItem().getItem() instanceof SpawnEggItem) {
                        eggItemNoNull = true;
                    }
                }
            }
        }

        if (event instanceof EventUpdate) {
            var5 = mc.world.getAllEntities().iterator();

            while(var5.hasNext()) {
                entity = (Entity)var5.next();
                if (entity instanceof ItemEntity) {
                    if (((ItemEntity)entity).getItem().getItem() instanceof SkullItem) {
                        mc.player.rotationYaw = this.rotations(entity)[0];
                        mc.player.rotationPitch = this.rotations(entity)[1];
                        break;
                    }

                    if (((ItemEntity)entity).getItem().getItem() instanceof ElytraItem && !skullItemNoNull) {
                        mc.player.rotationYaw = this.rotations(entity)[0];
                        mc.player.rotationPitch = this.rotations(entity)[1];
                        break;
                    }

                    if (((ItemEntity)entity).getItem().getItem() instanceof SpawnEggItem && !elytraItemNoNull && !skullItemNoNull) {
                        mc.player.rotationYaw = this.rotations(entity)[0];
                        mc.player.rotationPitch = this.rotations(entity)[1];
                        break;
                    }
                }
            }

            skullItemNoNull = true;
            elytraItemNoNull = true;
            eggItemNoNull = true;
        }
    }

    public float[] rotations(Entity entity) {
        double x = entity.getPosX() - mc.player.getPosX();
        double y = entity.getPosY() - mc.player.getPosY() - 1.5;
        double z = entity.getPosZ() - mc.player.getPosZ();
        double u = (double)MathHelper.sqrt(x * x + z * z);
        float u2 = (float)(MathHelper.atan2(z, x) * 57.29577951308232 - 90.0);
        float u3 = (float)(-MathHelper.atan2(y, u) * 57.29577951308232);
        return new float[]{u2, u3};
    }
}
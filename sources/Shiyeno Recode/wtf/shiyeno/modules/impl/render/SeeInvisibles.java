package wtf.shiyeno.modules.impl.render;

import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import wtf.shiyeno.events.Event;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;

@FunctionAnnotation(
        name = "SeeInvisibles",
        type = Type.Render
)
public class SeeInvisibles extends Function {
    public SeeInvisibles() {
    }

    public void onEvent(Event event) {
        Iterator var2 = mc.world.getPlayers().iterator();

        while(var2.hasNext()) {
            PlayerEntity player = (PlayerEntity)var2.next();
            Minecraft var10001 = mc;
            if (player != Minecraft.player && player.isInvisible()) {
                player.setInvisible(false);
            }
        }

    }
}
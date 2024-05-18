package host.kix.uzi.module.modules.render;

import com.darkmagician6.eventapi.SubscribeEvent;
import host.kix.uzi.events.RenderEntityEvent;
import host.kix.uzi.module.Module;
import org.lwjgl.opengl.GL11;

/**
 * Created by myche on 2/5/2017.
 */
public class Chams extends Module {

    public Chams() {
        super("Chams", 0, Category.RENDER);
    }

    @SubscribeEvent
    public void render(RenderEntityEvent e){
        if (e.getState() == RenderEntityEvent.State.PRE) {
            GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);
            GL11.glPolygonOffset(1.0F, -2000000F);
        } else if (e.getState() == RenderEntityEvent.State.POST) {
            GL11.glPolygonOffset(1.0F, 2000000F);
            GL11.glDisable(GL11.GL_POLYGON_OFFSET_FILL);
        }
    }

}

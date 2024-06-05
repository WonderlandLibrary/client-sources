package koks.modules.impl.visuals;

import koks.Koks;
import koks.event.Event;
import koks.event.impl.EventRender3D;
import koks.event.impl.EventTick;
import koks.event.impl.EventUpdate;
import koks.event.impl.MotionEvent;
import koks.modules.Module;
import koks.utilities.value.values.NumberValue;
import net.minecraft.util.BlockPos;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

/**
 * @author avox | lmao | kroko
 * @created on 05.09.2020 : 22:46
 */
public class TrailESP extends Module {

    public NumberValue<Integer> length = new NumberValue<Integer>("Length", 10, 1000,5,this);

    public TrailESP() {
        super("TrailESP", "Its render your position as a smooth line", Category.VISUALS);
        addValue(length);
    }

    public ArrayList<Double[]> positions = new ArrayList<>();

    @Override
    public void onEvent(Event event) {


        if(event instanceof EventRender3D)  {
            GL11.glPushMatrix();
            GL11.glColor4f(Koks.getKoks().client_color.getRed() / 255F, Koks.getKoks().client_color.getGreen() / 255F,Koks.getKoks().client_color.getBlue() / 255F, Koks.getKoks().client_color.getAlpha() / 255F);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glLineWidth(2F);
            GL11.glBegin(GL11.GL_LINE_STRIP);
            for(Double[] blockPos : positions) {
                GL11.glVertex3d(blockPos[0] - mc.getRenderManager().renderPosX,blockPos[1] - mc.getRenderManager().renderPosY,blockPos[2] - mc.getRenderManager().renderPosZ);
            }
            GL11.glVertex3f(0,0.01F,0);
            GL11.glEnd();
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glColor4f(1,1,1,1);
            GL11.glPopMatrix();
        }

        if(event instanceof EventTick) {
            if(positions.size() > length.getDefaultValue()) {
                int toMush = positions.size() - length.getDefaultValue();
                for(int i = 0; i < toMush; i++) {
                    positions.remove(i);
                }
            }
        }

        if(event instanceof MotionEvent) {
            if(((MotionEvent) event).getType() == MotionEvent.Type.POST) {
               positions.add(new Double[] {mc.thePlayer.posX,mc.thePlayer.posY + 0.01,mc.thePlayer.posZ});
            }
        }
    }

    @Override
    public void onEnable() {
        positions.clear();
    }

    @Override
    public void onDisable() {

    }
}

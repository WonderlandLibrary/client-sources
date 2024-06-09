package us.dev.direkt.module.internal.render;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;
import us.dev.api.property.Property;
import us.dev.direkt.Wrapper;
import us.dev.direkt.event.internal.events.game.network.EventSendPacket;
import us.dev.direkt.event.internal.events.game.render.EventRender3D;
import us.dev.direkt.event.internal.filters.PacketFilter;
import us.dev.direkt.module.ModCategory;
import us.dev.direkt.module.annotations.ModData;
import us.dev.direkt.module.ToggleableModule;
import us.dev.direkt.module.property.annotations.Dependency;
import us.dev.direkt.module.property.annotations.Exposed;
import us.dev.direkt.util.client.MovementUtils;
import us.dev.direkt.util.render.Box;
import us.dev.direkt.util.render.OGLRender;
import us.dev.dvent.Link;
import us.dev.dvent.Listener;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@ModData(label = "World Tracer", category = ModCategory.RENDER)
public class WorldTracer extends ToggleableModule {

    @Exposed(description = "Should your movements in the world be traced")
	private final Property<Boolean> doTrace = new Property<>("Trace", true);

    @Exposed(description = "Should dots be shown in the tracing line", depends = @Dependency(type = Boolean.class, label = "Trace", value = "true"))
	private final Property<Boolean> showDots = new Property<>("Show Dots", true);
	
	private List<Vec3d> cords = new ArrayList<>();
	private float color = 0;
	private float offset = 0;
	
	@Listener
	protected Link<EventRender3D> onRender3D = new Link<>(event -> {
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
		
		GL11.glBegin(GL11.GL_LINE_STRIP);
		for(Vec3d vec : cords){
            final double x = vec.xCoord - RenderManager.renderPosX();
            final double y = vec.yCoord - RenderManager.renderPosY();
            final double z = vec.zCoord - RenderManager.renderPosZ();
	        OGLRender.glColor(Color.getHSBColor(color+=0.05F, 1, 0.7F).getRGB());
            GL11.glVertex3d(x, y, z);
		}
		GL11.glEnd();
		color -= color + offset * 0.1F;
		
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        
        if(showDots.getValue()){
            OGLRender.enableGL3D(1.5f);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
			for(Vec3d vec : cords){
	            final double x = vec.xCoord - RenderManager.renderPosX();
	            final double y = vec.yCoord - RenderManager.renderPosY();
	            final double z = vec.zCoord - RenderManager.renderPosZ();
	            GL11.glTranslated(x, y, z);
	            GL11.glColor4d(0, 0, 0, 0.5);
	            OGLRender.drawBox(new Box(-0.01, -0.01, -0.01, 0.01, 0.01, 0.01));
	            GL11.glTranslated(-x, -y, -z);
			}
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glDisable(GL11.GL_BLEND);
            OGLRender.disableGL3D();
        }
        
        GL11.glPopMatrix();
        
        if(cords.size() > 1000)
        	cords.remove(0);
        else
    		if(offset < 10)
    			offset+=0.1F;
    		else
    			offset = 0;
	});
	
	@Listener
    protected Link<EventSendPacket> onSendPacket = new Link<>(event -> {
		if(this.doTrace.getValue() && event.getPacket() instanceof CPacketPlayer && MovementUtils.isMoving(Wrapper.getPlayer())) {
            CPacketPlayer packet = (CPacketPlayer) event.getPacket();
			cords.add(new Vec3d(packet.getX(Wrapper.getPlayer().posX), packet.getY(Wrapper.getPlayer().posY) < 0 ? Wrapper.getPlayer().posY : packet.getY(Wrapper.getPlayer().posY), packet.getZ(Wrapper.getPlayer().posZ)));
		}
    }, new PacketFilter<>(CPacketPlayer.class));
	
}

package us.dev.direkt.module.internal.render;

import com.google.common.collect.Sets;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.network.play.server.SPacketBlockChange;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.opengl.GL11;
import us.dev.api.property.Property;
import us.dev.api.timing.Timer;
import us.dev.direkt.Wrapper;
import us.dev.direkt.event.internal.events.game.network.EventPostReceivePacket;
import us.dev.direkt.event.internal.events.game.render.EventRender3D;
import us.dev.direkt.event.internal.filters.PacketFilter;
import us.dev.direkt.module.ModCategory;
import us.dev.direkt.module.annotations.ModData;
import us.dev.direkt.module.ToggleableModule;
import us.dev.direkt.module.property.annotations.Exposed;
import us.dev.direkt.util.render.Box;
import us.dev.direkt.util.render.OGLRender;
import us.dev.dvent.Listener;
import us.dev.dvent.Link;

import java.util.Set;

/**
 * @author Foundry
 */
@ModData(label = "Hide ESP", aliases = {"prophuntesp", "propesp"}, category = ModCategory.RENDER)
public class HideESP extends ToggleableModule {

    @Exposed(description = "Should hider blocks be highlighted")
    private final Property<Boolean> blockHunt = new Property<>("Blocks", true);

    @Exposed(description = "Should hider entities be highlighted")
    private final Property<Boolean> farmHunt = new Property<>("Animals", true);

    private final Timer timer = new Timer();
    private final Set<BlockPos> renderLocations = Sets.newHashSet();

    @Listener
    protected Link<EventPostReceivePacket> onPostReceivePacket = new Link<>(event -> {
        renderLocations.add(((SPacketBlockChange) event.getPacket()).getBlockPosition());
    }, new PacketFilter<>(SPacketBlockChange.class));

    @Listener
    protected Link<EventRender3D> onRender3D = new Link<>(event -> {
        OGLRender.enableGL3D(1.5f);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        if (timer.hasReach(2000)) {
            timer.reset();
            renderLocations.clear();
        }


        if (this.farmHunt.getValue()) {
            Wrapper.getLoadedEntities().stream()
                    .filter(entity -> entity instanceof IAnimals)
                    .filter(entity -> entity.rotationPitch != 0)
                    .forEach(entity -> {
                        final double[] pos = OGLRender.interpolate(entity);
                        final double x = pos[0];
                        final double y = pos[1];
                        final double z = pos[2];

                        GL11.glPushMatrix();
                        GL11.glTranslated(x, y, z);
                        GL11.glColor4f(1.0f, 0.0f, 0.0f, 0.4f);
                        OGLRender.drawBox(new Box(-0.5, 0, -0.5, 0.5, 1.0, 0.5));
                        GL11.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
                        OGLRender.drawOutlinedBox(new Box(-0.5, 0, -0.5, 0.5, 1, 0.5));
                        GL11.glPopMatrix();
                    });
        }

        if (this.blockHunt.getValue()) {
            Wrapper.getLoadedEntities().stream()
                    .filter(entity -> entity instanceof EntityFallingBlock)
                    .forEach(entity -> {
                        final EntityFallingBlock fallingBlock = (EntityFallingBlock) entity;
                        final double[] pos = OGLRender.interpolate(fallingBlock);
                        final double x = pos[0];
                        final double y = pos[1];
                        final double z = pos[2];

                        GL11.glPushMatrix();
                        GL11.glTranslated(x, y, z);
                        GL11.glColor4f(1.0f, 0.0f, 0.0f, 0.4f);
                        OGLRender.drawBox(new Box(-0.5, 0, -0.5, 0.5, 1.0, 0.5));
                        GL11.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
                        OGLRender.drawOutlinedBox(new Box(-0.5, 0, -0.5, 0.5, 1, 0.5));
                        GL11.glPopMatrix();
                    });

            for (final BlockPos b : renderLocations) {
                final double x = b.getX() - RenderManager.renderPosX();
                final double y = b.getY() - RenderManager.renderPosY();
                final double z = b.getZ() - RenderManager.renderPosZ();

                GL11.glPushMatrix();
                GL11.glTranslated(x, y, z);
                GL11.glColor4f(1.0f, 0.0f, 0.0f, 0.4f);
                OGLRender.drawBox(new Box(0, 0, 0, 1, 1, 1));
                GL11.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
                OGLRender.drawOutlinedBox(new Box(0, 0, 0, 1, 1, 1));
                GL11.glPopMatrix();
            }
        }
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        OGLRender.disableGL3D();
    });

}

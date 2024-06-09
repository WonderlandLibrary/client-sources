package io.github.liticane.clients.feature.module.impl.visual;

import io.github.liticane.clients.Client;
import io.github.liticane.clients.feature.event.api.annotations.SubscribeEvent;
import io.github.liticane.clients.feature.event.impl.render.RenderTickEvent;
import io.github.liticane.clients.feature.event.impl.render.RendererLivingEntityEvent;
import io.github.liticane.clients.feature.module.Module;
import io.github.liticane.clients.feature.property.impl.StringProperty;
import io.github.liticane.clients.feature.property.impl.NumberProperty;
import org.lwjglx.opengl.GLContext;
import io.github.liticane.clients.feature.event.api.EventListener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL33;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Module.Info(name = "Entity Culling", category = Module.Category.VISUAL)
public class EntityCulling extends Module {

    public NumberProperty cullingDelay = new NumberProperty("Culling Delay", this, 2, 1, 3, 1);
    public StringProperty cullingMode = new StringProperty("Culling Mode", this, "Grouped", "Grouped", "Custom");
    public NumberProperty entityCullingDis = new NumberProperty("Culling Distance", this, 45, 10, 150, 1, () -> cullingMode.is("Grouped"));
    public NumberProperty mobCullingDis = new NumberProperty("Mob Cull Distance", this, 40, 10, 150, 1, () -> cullingMode.is("Custom"));
    public NumberProperty playerCullingDis = new NumberProperty("Player Cull Distance", this, 45, 10, 150, 1, () -> cullingMode.is("Custom"));
    public NumberProperty passiveCullingDis = new NumberProperty("Passive Cull Distance", this, 30, 10, 150, 1, () -> cullingMode.is("Custom"));

    public static boolean shouldPerformCulling = false;

    private static final RenderManager renderManager = mc.getRenderManager();
    private static final ConcurrentHashMap<UUID, OcclusionQuery> queries = new ConcurrentHashMap<>();
    private static final boolean SUPPORT_NEW_GL = GLContext.getCapabilities().OpenGL33;

    public static boolean renderItem(Entity stack) {
        if (!Client.INSTANCE.getModuleManager().get(EntityCulling.class).isToggled())
            return false;

        return shouldPerformCulling && stack.worldObj == mc.player.worldObj && checkEntity(stack);
    }

    @SubscribeEvent
    private final EventListener<RendererLivingEntityEvent> onRenderer = e -> {
        if (e.getType() == RendererLivingEntityEvent.Type.POST || !shouldPerformCulling)
            return;

        EntityLivingBase entity = e.getEntity();

        if (entity == mc.player || entity.worldObj != mc.player.worldObj || (entity.isInvisibleToPlayer(mc.player)))
            return;

        if (checkEntity(entity)) {
            e.setCancelled(true);

            if (!canRenderName(entity))
                return;

            double x = e.getX();
            double y = e.getY();
            double z = e.getZ();
            RendererLivingEntity<EntityLivingBase> renderer = (RendererLivingEntity<EntityLivingBase>) e.getRenderer();

            renderer.renderName(entity, x, y, z);
        }

        if (entity.isInvisible() && entity instanceof EntityPlayer)
            e.setCancelled(true);

        if (EntityCulling.shouldPerformCulling) {
            final float entityDistance = entity.getDistanceToEntity(mc.player);

            switch (cullingMode.getMode()) {
                case "Grouped":
                    if (entityDistance > entityCullingDis.getValue())
                        e.setCancelled(true);
                    break;

                case "Custom":
                    if (entity instanceof IMob && entityDistance > mobCullingDis.getValue())
                        e.setCancelled(true);
                    else if ((entity instanceof EntityAnimal || entity instanceof EntityAmbientCreature || entity instanceof EntityWaterMob) && entityDistance > passiveCullingDis.getValue())
                        e.setCancelled(true);
                    else if (entity instanceof EntityPlayer && entityDistance > playerCullingDis.getValue())
                        e.setCancelled(true);
                    break;
            }
        }
    };

    @SubscribeEvent
    private final EventListener<RenderTickEvent> onRenderTick = e -> {
        mc.addScheduledTask(this::check);
    };

    private void check() {
        long delay = 0;

        switch ((int) (cullingDelay.getValue() - 1)) {
            case 0:
                delay = 10;
                break;

            case 1:
                delay = 25;
                break;

            case 2:
                delay = 50;
                break;
        }

        long nanoTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime());
        for (OcclusionQuery query : queries.values()) {
            if (query.nextQuery != 0) {
                final long queryObject = GL15.glGetQueryObjecti(query.nextQuery, GL15.GL_QUERY_RESULT_AVAILABLE);
                if (queryObject != 0) {
                    query.occluded = GL15.glGetQueryObjecti(query.nextQuery, GL15.GL_QUERY_RESULT) == 0;
                    GL15.glDeleteQueries(query.nextQuery);
                    query.nextQuery = 0;

                }
            }

            if (query.nextQuery == 0 && nanoTime - query.executionTime > delay) {
                query.executionTime = nanoTime;
                query.refresh = true;
            }
        }
    }

    public static boolean canRenderName(EntityLivingBase entity) {
        final EntityPlayerSP player = mc.player;

        if (entity instanceof EntityPlayer && entity != player) {
            final Team otherEntityTeam = entity.getTeam();
            final Team playerTeam = player.getTeam();

            if (otherEntityTeam != null) {
                final Team.EnumVisible teamVisibilityRule = otherEntityTeam.getNameTagVisibility();

                switch (teamVisibilityRule) {
                    case NEVER:
                        return false;

                    case HIDE_FOR_OTHER_TEAMS:
                        return playerTeam == null || otherEntityTeam.isSameTeam(playerTeam);

                    case HIDE_FOR_OWN_TEAM:
                        return playerTeam == null || !otherEntityTeam.isSameTeam(playerTeam);

                    case ALWAYS:
                    default:
                        return true;
                }
            }
        }

        return Minecraft.isGuiEnabled()
                && entity != mc.getRenderManager().livingPlayer
                && ((entity instanceof EntityArmorStand) || !entity.isInvisibleToPlayer(player))
                && entity.riddenByEntity == null;
    }

    private static boolean checkEntity(Entity entity) {
        OcclusionQuery query = queries.computeIfAbsent(entity.getUniqueID(), OcclusionQuery::new);

        if (query.refresh) {
            query.nextQuery = getQuery();
            query.refresh = false;

            int mode = SUPPORT_NEW_GL ? GL33.GL_ANY_SAMPLES_PASSED : GL15.GL_SAMPLES_PASSED;

            GL15.glBeginQuery(mode, query.nextQuery);

            drawSelectionBoundingBox(entity.getEntityBoundingBox()
                    .expand(.2, .2, .2)
                    .offset(-renderManager.renderPosX, -renderManager.renderPosY, -renderManager.renderPosZ)
            );

            GL15.glEndQuery(mode);
        }

        return query.occluded;
    }

    public static void drawSelectionBoundingBox(AxisAlignedBB b) {
        GlStateManager.disableAlpha();
        GlStateManager.disableCull();
        GlStateManager.depthMask(false);
        GlStateManager.colorMask(false, false, false, false);
        final Tessellator tessellator = Tessellator.getInstance();
        final WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(GL11.GL_QUAD_STRIP, DefaultVertexFormats.POSITION);
        worldrenderer.pos(b.maxX, b.maxY, b.maxZ).endVertex();
        worldrenderer.pos(b.maxX, b.maxY, b.minZ).endVertex();
        worldrenderer.pos(b.minX, b.maxY, b.maxZ).endVertex();
        worldrenderer.pos(b.minX, b.maxY, b.minZ).endVertex();
        worldrenderer.pos(b.minX, b.minY, b.maxZ).endVertex();
        worldrenderer.pos(b.minX, b.minY, b.minZ).endVertex();
        worldrenderer.pos(b.minX, b.maxY, b.minZ).endVertex();
        worldrenderer.pos(b.minX, b.minY, b.minZ).endVertex();
        worldrenderer.pos(b.maxX, b.maxY, b.minZ).endVertex();
        worldrenderer.pos(b.maxX, b.minY, b.minZ).endVertex();
        worldrenderer.pos(b.maxX, b.maxY, b.maxZ).endVertex();
        worldrenderer.pos(b.maxX, b.minY, b.maxZ).endVertex();
        worldrenderer.pos(b.minX, b.maxY, b.maxZ).endVertex();
        worldrenderer.pos(b.minX, b.minY, b.maxZ).endVertex();
        worldrenderer.pos(b.minX, b.minY, b.maxZ).endVertex();
        worldrenderer.pos(b.maxX, b.minY, b.maxZ).endVertex();
        worldrenderer.pos(b.minX, b.minY, b.minZ).endVertex();
        worldrenderer.pos(b.maxX, b.minY, b.minZ).endVertex();
        tessellator.draw();
        GlStateManager.depthMask(true);
        GlStateManager.colorMask(true, true, true, true);
        GlStateManager.enableAlpha();
    }

    private static int getQuery() {
        try {
            return GL15.glGenQueries();
        } catch (Throwable throwable) {
            return 0;
        }
    }

    static class OcclusionQuery {
        private final UUID uuid;
        private int nextQuery;
        private boolean refresh = true;
        private boolean occluded;
        private long executionTime = 0;

        public OcclusionQuery(UUID uuid) {
            this.uuid = uuid;
        }
    }
}
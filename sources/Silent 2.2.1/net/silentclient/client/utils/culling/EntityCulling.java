package net.silentclient.client.utils.culling;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.AxisAlignedBB;
import net.silentclient.client.Client;
import net.silentclient.client.event.EventTarget;
import net.silentclient.client.event.impl.ClientTickEvent;
import net.silentclient.client.event.impl.RenderLivingEvent;
import net.silentclient.client.event.impl.RenderTickEvent;
import net.silentclient.client.mixin.accessors.RenderManagerAccessor;
import net.silentclient.client.mods.settings.FPSBoostMod;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL33;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class EntityCulling {
	private static final Minecraft mc = Minecraft.getMinecraft();
	public static RenderManager renderManager;
    private static final ConcurrentHashMap<UUID, OcclusionQuery> queries = new ConcurrentHashMap<>();
    public static boolean SUPPORT_NEW_GL;
    public static boolean shouldPerformCulling = false;
    private int destroyTimer;
    public static boolean renderingSpawnerEntity = false;

    /**
     * Used for checking if the entities' nametag can be rendered if the user still wants
     * to see nametags despite the entity being culled.
     * <p>
     * Mirrored from {@link RendererLivingEntity} as it's originally protected.
     *
     * @param entity The entity that's being culled.
     * @return The status on if the nametag is liable for rendering.
     */
    public static boolean canRenderName(EntityLivingBase entity) {
        final EntityPlayerSP player = mc.thePlayer;
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
            && ((entity instanceof EntityArmorStand) || !entity.isInvisibleToPlayer(player)) &&
            //#if MC==10809
            entity.riddenByEntity == null;
            //#else
            //$$ !entity.isBeingRidden();
            //#endif
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

    public static boolean renderItem(Entity stack) {
        //needs to be called from RenderEntityItem#doRender and RenderItemFrame#doRender. Returning true means it should cancel the render event
        return shouldPerformCulling && !Client.getInstance().getSettingsManager().getSettingByClass(FPSBoostMod.class, "Occlusion Culling").getValString().equals("Off") && stack.worldObj == mc.thePlayer.worldObj && checkEntity(stack);
    }

    private static int getQuery() {
        try {
            return GL15.glGenQueries();
        } catch (Throwable throwable) {
			Client.logger.error(
                "Failed to run GL15.glGenQueries(). User's computer is likely too old to support OpenGL 1.5, Occlusion Culling has been force disabled.",
                throwable
            );

            Client.getInstance().getSettingsManager().getSettingByClass(FPSBoostMod.class, "Occlusion Culling").setValBoolean(false);

            return 0;
        }
    }

    /**
     * Used OpenGL queries in order to determine if the given is visible
     *
     * @param entity entity to check
     * @return true if the entity rendering should be skipped
     */
    private static boolean checkEntity(Entity entity) {
        if (renderingSpawnerEntity) return false;
        OcclusionQuery query = queries.computeIfAbsent(entity.getUniqueID(), OcclusionQuery::new);
        if (query.refresh) {
            query.nextQuery = getQuery();
            query.refresh = false;
            int mode = SUPPORT_NEW_GL ? GL33.GL_ANY_SAMPLES_PASSED : GL15.GL_SAMPLES_PASSED;
            GL15.glBeginQuery(mode, query.nextQuery);
            drawSelectionBoundingBox(entity.getEntityBoundingBox()
                .expand(.2, .2, .2)
                .offset(-((RenderManagerAccessor) renderManager).getRenderPosX(), -((RenderManagerAccessor) renderManager).getRenderPosY(), -((RenderManagerAccessor) renderManager).getRenderPosZ())
            );
            GL15.glEndQuery(mode);
        }

        return query.occluded;
    }

    @EventTarget
    public void shouldRenderEntity(RenderLivingEvent event) {
        if (Client.getInstance().getSettingsManager().getSettingByClass(FPSBoostMod.class, "Occlusion Culling").getValString().equals("Off") || !shouldPerformCulling) {
            return;
        }

        //#if MC==10809
        EntityLivingBase entity = (EntityLivingBase)event.getEntity();
        //#else
        //$$ EntityLivingBase entity = event.getEntity();
        //#endif
        boolean armorstand = entity instanceof EntityArmorStand;
        if (entity == mc.thePlayer || entity.worldObj != mc.thePlayer.worldObj ||
            (entity.isInvisibleToPlayer(mc.thePlayer) && !armorstand)
            //#if MC==11202
            //$$ || entity.isGlowing()
            //#endif
        ) {
            return;
        }

        if (checkEntity(entity)) {
            event.setCancelled(true);
            if (!canRenderName(entity)) {
                return;
            }

            //#if MC==10809
            double x = event.x;
            double y = event.y;
            double z = event.z;
            RendererLivingEntity<EntityLivingBase> renderer = event.getRenderer();
            //#else
            //$$ double x = event.getX();
            //$$ double y = event.getY();
            //$$ double z = event.getZ();
            //$$ RenderLivingBase<EntityLivingBase> renderer = event.getRenderer();
            //#endif

            if(entity instanceof EntityPlayer) {
                renderer.renderName(entity, x, y, z);
            }
        }

    }

    @EventTarget
    public void renderTickEvent(RenderTickEvent event) {
        if (Client.getInstance().getSettingsManager().getSettingByClass(FPSBoostMod.class, "Occlusion Culling").getValString().equals("Off")) {
            return;
        }
        
        Minecraft.getMinecraft().addScheduledTask(this::check);
    }

    private void check() {
        long delay = 50;
        switch (Client.getInstance().getSettingsManager().getSettingByClass(FPSBoostMod.class, "Occlusion Culling").getValString()) {
            case "Performance": {
                delay = 50;
                break;
            }
            case "Balance": {
                delay = 25;
                break;
            }
            case "Quality": {
                delay = 10;
                break;
            }
            default:
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

    @EventTarget
    public void cullingTick(ClientTickEvent event) {
//    	System.out.println("Its worked!");
        if (Client.getInstance().getSettingsManager().getSettingByClass(FPSBoostMod.class, "Occlusion Culling").getValString().equals("Off") || this.destroyTimer++ < 120) {
            return;
        }

        this.destroyTimer = 0;
        WorldClient theWorld = mc.theWorld;
        if (theWorld == null) {
            return;
        }

        List<UUID> remove = new ArrayList<>();
        Set<UUID> loaded = new HashSet<>();
        for (Entity entity : theWorld.loadedEntityList) {
            loaded.add(entity.getUniqueID());
        }

        for (OcclusionQuery value : queries.values()) {
            if (loaded.contains(value.uuid)) {
                continue;
            }

            remove.add(value.uuid);
            if (value.nextQuery != 0) {
                GL15.glDeleteQueries(value.nextQuery);
            }
        }

        for (UUID uuid : remove) {
            queries.remove(uuid);
        }
    }

    static class OcclusionQuery {
        private final UUID uuid; //Owner
        private int nextQuery;
        private boolean refresh = true;
        private boolean occluded;
        private long executionTime = 0;

        public OcclusionQuery(UUID uuid) {
            this.uuid = uuid;
        }
    }
}

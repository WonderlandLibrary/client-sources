/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import java.util.Collections;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.client.renderer.entity.AreaEffectCloudRenderer;
import net.minecraft.client.renderer.entity.ArmorStandRenderer;
import net.minecraft.client.renderer.entity.BatRenderer;
import net.minecraft.client.renderer.entity.BeeRenderer;
import net.minecraft.client.renderer.entity.BlazeRenderer;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.CatRenderer;
import net.minecraft.client.renderer.entity.CaveSpiderRenderer;
import net.minecraft.client.renderer.entity.ChestedHorseRenderer;
import net.minecraft.client.renderer.entity.ChickenRenderer;
import net.minecraft.client.renderer.entity.CodRenderer;
import net.minecraft.client.renderer.entity.CowRenderer;
import net.minecraft.client.renderer.entity.CreeperRenderer;
import net.minecraft.client.renderer.entity.DolphinRenderer;
import net.minecraft.client.renderer.entity.DragonFireballRenderer;
import net.minecraft.client.renderer.entity.DrownedRenderer;
import net.minecraft.client.renderer.entity.ElderGuardianRenderer;
import net.minecraft.client.renderer.entity.EnderCrystalRenderer;
import net.minecraft.client.renderer.entity.EnderDragonRenderer;
import net.minecraft.client.renderer.entity.EndermanRenderer;
import net.minecraft.client.renderer.entity.EndermiteRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EvokerFangsRenderer;
import net.minecraft.client.renderer.entity.EvokerRenderer;
import net.minecraft.client.renderer.entity.ExperienceOrbRenderer;
import net.minecraft.client.renderer.entity.FallingBlockRenderer;
import net.minecraft.client.renderer.entity.FireworkRocketRenderer;
import net.minecraft.client.renderer.entity.FishRenderer;
import net.minecraft.client.renderer.entity.FoxRenderer;
import net.minecraft.client.renderer.entity.GhastRenderer;
import net.minecraft.client.renderer.entity.GiantZombieRenderer;
import net.minecraft.client.renderer.entity.GuardianRenderer;
import net.minecraft.client.renderer.entity.HoglinRenderer;
import net.minecraft.client.renderer.entity.HorseRenderer;
import net.minecraft.client.renderer.entity.HuskRenderer;
import net.minecraft.client.renderer.entity.IllusionerRenderer;
import net.minecraft.client.renderer.entity.IronGolemRenderer;
import net.minecraft.client.renderer.entity.ItemFrameRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.LeashKnotRenderer;
import net.minecraft.client.renderer.entity.LightningBoltRenderer;
import net.minecraft.client.renderer.entity.LlamaRenderer;
import net.minecraft.client.renderer.entity.LlamaSpitRenderer;
import net.minecraft.client.renderer.entity.MagmaCubeRenderer;
import net.minecraft.client.renderer.entity.MinecartRenderer;
import net.minecraft.client.renderer.entity.MooshroomRenderer;
import net.minecraft.client.renderer.entity.OcelotRenderer;
import net.minecraft.client.renderer.entity.PaintingRenderer;
import net.minecraft.client.renderer.entity.PandaRenderer;
import net.minecraft.client.renderer.entity.ParrotRenderer;
import net.minecraft.client.renderer.entity.PhantomRenderer;
import net.minecraft.client.renderer.entity.PigRenderer;
import net.minecraft.client.renderer.entity.PiglinRenderer;
import net.minecraft.client.renderer.entity.PillagerRenderer;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.PolarBearRenderer;
import net.minecraft.client.renderer.entity.PufferfishRenderer;
import net.minecraft.client.renderer.entity.RabbitRenderer;
import net.minecraft.client.renderer.entity.RavagerRenderer;
import net.minecraft.client.renderer.entity.SalmonRenderer;
import net.minecraft.client.renderer.entity.SheepRenderer;
import net.minecraft.client.renderer.entity.ShulkerBulletRenderer;
import net.minecraft.client.renderer.entity.ShulkerRenderer;
import net.minecraft.client.renderer.entity.SilverfishRenderer;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.client.renderer.entity.SlimeRenderer;
import net.minecraft.client.renderer.entity.SnowManRenderer;
import net.minecraft.client.renderer.entity.SpectralArrowRenderer;
import net.minecraft.client.renderer.entity.SpiderRenderer;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.client.renderer.entity.SquidRenderer;
import net.minecraft.client.renderer.entity.StrayRenderer;
import net.minecraft.client.renderer.entity.StriderRenderer;
import net.minecraft.client.renderer.entity.TNTMinecartRenderer;
import net.minecraft.client.renderer.entity.TNTRenderer;
import net.minecraft.client.renderer.entity.TippedArrowRenderer;
import net.minecraft.client.renderer.entity.TridentRenderer;
import net.minecraft.client.renderer.entity.TropicalFishRenderer;
import net.minecraft.client.renderer.entity.TurtleRenderer;
import net.minecraft.client.renderer.entity.UndeadHorseRenderer;
import net.minecraft.client.renderer.entity.VexRenderer;
import net.minecraft.client.renderer.entity.VillagerRenderer;
import net.minecraft.client.renderer.entity.VindicatorRenderer;
import net.minecraft.client.renderer.entity.WanderingTraderRenderer;
import net.minecraft.client.renderer.entity.WitchRenderer;
import net.minecraft.client.renderer.entity.WitherRenderer;
import net.minecraft.client.renderer.entity.WitherSkeletonRenderer;
import net.minecraft.client.renderer.entity.WitherSkullRenderer;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.minecraft.client.renderer.entity.ZoglinRenderer;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.client.renderer.entity.ZombieVillagerRenderer;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.EnderDragonPartEntity;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.optifine.Config;
import net.optifine.DynamicLights;
import net.optifine.EmissiveTextures;
import net.optifine.entity.model.CustomEntityModels;
import net.optifine.player.PlayerItemsLayer;
import net.optifine.reflect.Reflector;
import net.optifine.shaders.Shaders;

public class EntityRendererManager {
    private static final RenderType SHADOW_RENDER_TYPE = RenderType.getEntityShadow(new ResourceLocation("textures/misc/shadow.png"));
    private final Map<EntityType, EntityRenderer> renderers = Maps.newHashMap();
    private final Map<String, PlayerRenderer> skinMap = Maps.newHashMap();
    private final PlayerRenderer playerRenderer;
    private final FontRenderer textRenderer;
    public final TextureManager textureManager;
    private World world;
    public ActiveRenderInfo info;
    private Quaternion cameraOrientation;
    public Entity pointedEntity;
    public final GameSettings options;
    private boolean renderShadow = true;
    private boolean debugBoundingBox;
    public EntityRenderer renderRender = null;

    public <E extends Entity> int getPackedLight(E e, float f) {
        int n = this.getRenderer(e).getPackedLight(e, f);
        if (Config.isDynamicLights()) {
            n = DynamicLights.getCombinedLight(e, n);
        }
        return n;
    }

    private <T extends Entity> void register(EntityType<T> entityType, EntityRenderer<? super T> entityRenderer) {
        this.renderers.put(entityType, entityRenderer);
    }

    private void registerRenderers(net.minecraft.client.renderer.ItemRenderer itemRenderer, IReloadableResourceManager iReloadableResourceManager) {
        this.register(EntityType.AREA_EFFECT_CLOUD, new AreaEffectCloudRenderer(this));
        this.register(EntityType.ARMOR_STAND, new ArmorStandRenderer(this));
        this.register(EntityType.ARROW, new TippedArrowRenderer(this));
        this.register(EntityType.BAT, new BatRenderer(this));
        this.register(EntityType.BEE, new BeeRenderer(this));
        this.register(EntityType.BLAZE, new BlazeRenderer(this));
        this.register(EntityType.BOAT, new BoatRenderer(this));
        this.register(EntityType.CAT, new CatRenderer(this));
        this.register(EntityType.CAVE_SPIDER, new CaveSpiderRenderer(this));
        this.register(EntityType.CHEST_MINECART, new MinecartRenderer(this));
        this.register(EntityType.CHICKEN, new ChickenRenderer(this));
        this.register(EntityType.COD, new CodRenderer(this));
        this.register(EntityType.COMMAND_BLOCK_MINECART, new MinecartRenderer(this));
        this.register(EntityType.COW, new CowRenderer(this));
        this.register(EntityType.CREEPER, new CreeperRenderer(this));
        this.register(EntityType.DOLPHIN, new DolphinRenderer(this));
        this.register(EntityType.DONKEY, new ChestedHorseRenderer(this, 0.87f));
        this.register(EntityType.DRAGON_FIREBALL, new DragonFireballRenderer(this));
        this.register(EntityType.DROWNED, new DrownedRenderer(this));
        this.register(EntityType.EGG, new SpriteRenderer(this, itemRenderer));
        this.register(EntityType.ELDER_GUARDIAN, new ElderGuardianRenderer(this));
        this.register(EntityType.END_CRYSTAL, new EnderCrystalRenderer(this));
        this.register(EntityType.ENDER_DRAGON, new EnderDragonRenderer(this));
        this.register(EntityType.ENDERMAN, new EndermanRenderer(this));
        this.register(EntityType.ENDERMITE, new EndermiteRenderer(this));
        this.register(EntityType.ENDER_PEARL, new SpriteRenderer(this, itemRenderer));
        this.register(EntityType.EVOKER_FANGS, new EvokerFangsRenderer(this));
        this.register(EntityType.EVOKER, new EvokerRenderer(this));
        this.register(EntityType.EXPERIENCE_BOTTLE, new SpriteRenderer(this, itemRenderer));
        this.register(EntityType.EXPERIENCE_ORB, new ExperienceOrbRenderer(this));
        this.register(EntityType.EYE_OF_ENDER, new SpriteRenderer(this, itemRenderer, 1.0f, true));
        this.register(EntityType.FALLING_BLOCK, new FallingBlockRenderer(this));
        this.register(EntityType.FIREBALL, new SpriteRenderer(this, itemRenderer, 3.0f, true));
        this.register(EntityType.FIREWORK_ROCKET, new FireworkRocketRenderer(this, itemRenderer));
        this.register(EntityType.FISHING_BOBBER, new FishRenderer(this));
        this.register(EntityType.FOX, new FoxRenderer(this));
        this.register(EntityType.FURNACE_MINECART, new MinecartRenderer(this));
        this.register(EntityType.GHAST, new GhastRenderer(this));
        this.register(EntityType.GIANT, new GiantZombieRenderer(this, 6.0f));
        this.register(EntityType.GUARDIAN, new GuardianRenderer(this));
        this.register(EntityType.HOGLIN, new HoglinRenderer(this));
        this.register(EntityType.HOPPER_MINECART, new MinecartRenderer(this));
        this.register(EntityType.HORSE, new HorseRenderer(this));
        this.register(EntityType.HUSK, new HuskRenderer(this));
        this.register(EntityType.ILLUSIONER, new IllusionerRenderer(this));
        this.register(EntityType.IRON_GOLEM, new IronGolemRenderer(this));
        this.register(EntityType.ITEM, new ItemRenderer(this, itemRenderer));
        this.register(EntityType.ITEM_FRAME, new ItemFrameRenderer(this, itemRenderer));
        this.register(EntityType.LEASH_KNOT, new LeashKnotRenderer(this));
        this.register(EntityType.LIGHTNING_BOLT, new LightningBoltRenderer(this));
        this.register(EntityType.LLAMA, new LlamaRenderer(this));
        this.register(EntityType.LLAMA_SPIT, new LlamaSpitRenderer(this));
        this.register(EntityType.MAGMA_CUBE, new MagmaCubeRenderer(this));
        this.register(EntityType.MINECART, new MinecartRenderer(this));
        this.register(EntityType.MOOSHROOM, new MooshroomRenderer(this));
        this.register(EntityType.MULE, new ChestedHorseRenderer(this, 0.92f));
        this.register(EntityType.OCELOT, new OcelotRenderer(this));
        this.register(EntityType.PAINTING, new PaintingRenderer(this));
        this.register(EntityType.PANDA, new PandaRenderer(this));
        this.register(EntityType.PARROT, new ParrotRenderer(this));
        this.register(EntityType.PHANTOM, new PhantomRenderer(this));
        this.register(EntityType.PIG, new PigRenderer(this));
        this.register(EntityType.PIGLIN, new PiglinRenderer(this, false));
        this.register(EntityType.field_242287_aj, new PiglinRenderer(this, false));
        this.register(EntityType.PILLAGER, new PillagerRenderer(this));
        this.register(EntityType.POLAR_BEAR, new PolarBearRenderer(this));
        this.register(EntityType.POTION, new SpriteRenderer(this, itemRenderer));
        this.register(EntityType.PUFFERFISH, new PufferfishRenderer(this));
        this.register(EntityType.RABBIT, new RabbitRenderer(this));
        this.register(EntityType.RAVAGER, new RavagerRenderer(this));
        this.register(EntityType.SALMON, new SalmonRenderer(this));
        this.register(EntityType.SHEEP, new SheepRenderer(this));
        this.register(EntityType.SHULKER_BULLET, new ShulkerBulletRenderer(this));
        this.register(EntityType.SHULKER, new ShulkerRenderer(this));
        this.register(EntityType.SILVERFISH, new SilverfishRenderer(this));
        this.register(EntityType.SKELETON_HORSE, new UndeadHorseRenderer(this));
        this.register(EntityType.SKELETON, new SkeletonRenderer(this));
        this.register(EntityType.SLIME, new SlimeRenderer(this));
        this.register(EntityType.SMALL_FIREBALL, new SpriteRenderer(this, itemRenderer, 0.75f, true));
        this.register(EntityType.SNOWBALL, new SpriteRenderer(this, itemRenderer));
        this.register(EntityType.SNOW_GOLEM, new SnowManRenderer(this));
        this.register(EntityType.SPAWNER_MINECART, new MinecartRenderer(this));
        this.register(EntityType.SPECTRAL_ARROW, new SpectralArrowRenderer(this));
        this.register(EntityType.SPIDER, new SpiderRenderer(this));
        this.register(EntityType.SQUID, new SquidRenderer(this));
        this.register(EntityType.STRAY, new StrayRenderer(this));
        this.register(EntityType.TNT_MINECART, new TNTMinecartRenderer(this));
        this.register(EntityType.TNT, new TNTRenderer(this));
        this.register(EntityType.TRADER_LLAMA, new LlamaRenderer(this));
        this.register(EntityType.TRIDENT, new TridentRenderer(this));
        this.register(EntityType.TROPICAL_FISH, new TropicalFishRenderer(this));
        this.register(EntityType.TURTLE, new TurtleRenderer(this));
        this.register(EntityType.VEX, new VexRenderer(this));
        this.register(EntityType.VILLAGER, new VillagerRenderer(this, iReloadableResourceManager));
        this.register(EntityType.VINDICATOR, new VindicatorRenderer(this));
        this.register(EntityType.WANDERING_TRADER, new WanderingTraderRenderer(this));
        this.register(EntityType.WITCH, new WitchRenderer(this));
        this.register(EntityType.WITHER, new WitherRenderer(this));
        this.register(EntityType.WITHER_SKELETON, new WitherSkeletonRenderer(this));
        this.register(EntityType.WITHER_SKULL, new WitherSkullRenderer(this));
        this.register(EntityType.WOLF, new WolfRenderer(this));
        this.register(EntityType.ZOGLIN, new ZoglinRenderer(this));
        this.register(EntityType.ZOMBIE_HORSE, new UndeadHorseRenderer(this));
        this.register(EntityType.ZOMBIE, new ZombieRenderer(this));
        this.register(EntityType.ZOMBIFIED_PIGLIN, new PiglinRenderer(this, true));
        this.register(EntityType.ZOMBIE_VILLAGER, new ZombieVillagerRenderer(this, iReloadableResourceManager));
        this.register(EntityType.STRIDER, new StriderRenderer(this));
    }

    public EntityRendererManager(TextureManager textureManager, net.minecraft.client.renderer.ItemRenderer itemRenderer, IReloadableResourceManager iReloadableResourceManager, FontRenderer fontRenderer, GameSettings gameSettings) {
        this.textureManager = textureManager;
        this.textRenderer = fontRenderer;
        this.options = gameSettings;
        this.registerRenderers(itemRenderer, iReloadableResourceManager);
        this.playerRenderer = new PlayerRenderer(this);
        this.skinMap.put("default", this.playerRenderer);
        this.skinMap.put("slim", new PlayerRenderer(this, true));
        PlayerItemsLayer.register(this.skinMap);
    }

    public void validateRendererExistence() {
        for (EntityType entityType : Registry.ENTITY_TYPE) {
            if (entityType == EntityType.PLAYER || this.renderers.containsKey(entityType)) continue;
            throw new IllegalStateException("No renderer registered for " + Registry.ENTITY_TYPE.getKey(entityType));
        }
    }

    public <T extends Entity> EntityRenderer<? super T> getRenderer(T t) {
        if (t instanceof AbstractClientPlayerEntity) {
            String string = ((AbstractClientPlayerEntity)t).getSkinType();
            PlayerRenderer playerRenderer = this.skinMap.get(string);
            return playerRenderer != null ? playerRenderer : this.playerRenderer;
        }
        return this.renderers.get(t.getType());
    }

    public void cacheActiveRenderInfo(World world, ActiveRenderInfo activeRenderInfo, Entity entity2) {
        this.world = world;
        this.info = activeRenderInfo;
        this.cameraOrientation = activeRenderInfo.getRotation();
        this.pointedEntity = entity2;
    }

    public void setCameraOrientation(Quaternion quaternion) {
        this.cameraOrientation = quaternion;
    }

    public void setRenderShadow(boolean bl) {
        this.renderShadow = bl;
    }

    public void setDebugBoundingBox(boolean bl) {
        this.debugBoundingBox = bl;
    }

    public boolean isDebugBoundingBox() {
        return this.debugBoundingBox;
    }

    public <E extends Entity> boolean shouldRender(E e, ClippingHelper clippingHelper, double d, double d2, double d3) {
        EntityRenderer<E> entityRenderer = this.getRenderer(e);
        return entityRenderer.shouldRender(e, clippingHelper, d, d2, d3);
    }

    public <E extends Entity> void renderEntityStatic(E e, double d, double d2, double d3, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        if (this.info != null) {
            EntityRenderer<E> entityRenderer = this.getRenderer(e);
            try {
                double d4;
                float f3;
                Vector3d vector3d = entityRenderer.getRenderOffset(e, f2);
                double d5 = d + vector3d.getX();
                double d6 = d2 + vector3d.getY();
                double d7 = d3 + vector3d.getZ();
                matrixStack.push();
                matrixStack.translate(d5, d6, d7);
                if (CustomEntityModels.isActive()) {
                    this.renderRender = entityRenderer;
                }
                if (EmissiveTextures.isActive()) {
                    EmissiveTextures.beginRender();
                }
                entityRenderer.render(e, f, f2, matrixStack, iRenderTypeBuffer, n);
                if (EmissiveTextures.isActive()) {
                    if (EmissiveTextures.hasEmissive()) {
                        EmissiveTextures.beginRenderEmissive();
                        entityRenderer.render(e, f, f2, matrixStack, iRenderTypeBuffer, LightTexture.MAX_BRIGHTNESS);
                        EmissiveTextures.endRenderEmissive();
                    }
                    EmissiveTextures.endRender();
                }
                if (e.canRenderOnFire()) {
                    this.renderFire(matrixStack, iRenderTypeBuffer, e);
                }
                matrixStack.translate(-vector3d.getX(), -vector3d.getY(), -vector3d.getZ());
                if (this.options.entityShadows && this.renderShadow && entityRenderer.shadowSize > 0.0f && !e.isInvisible() && (f3 = (float)((1.0 - (d4 = this.getDistanceToCamera(e.getPosX(), e.getPosY(), e.getPosZ())) / 256.0) * (double)entityRenderer.shadowOpaque)) > 0.0f) {
                    EntityRendererManager.renderShadow(matrixStack, iRenderTypeBuffer, e, f3, f2, this.world, entityRenderer.shadowSize);
                }
                if (this.debugBoundingBox && !e.isInvisible() && !Minecraft.getInstance().isReducedDebug()) {
                    this.renderDebugBoundingBox(matrixStack, iRenderTypeBuffer.getBuffer(RenderType.getLines()), e, f2);
                }
                matrixStack.pop();
            } catch (Throwable throwable) {
                CrashReport crashReport = CrashReport.makeCrashReport(throwable, "Rendering entity in world");
                CrashReportCategory crashReportCategory = crashReport.makeCategory("Entity being rendered");
                e.fillCrashReport(crashReportCategory);
                CrashReportCategory crashReportCategory2 = crashReport.makeCategory("Renderer details");
                crashReportCategory2.addDetail("Assigned renderer", entityRenderer);
                crashReportCategory2.addDetail("Location", CrashReportCategory.getCoordinateInfo(d, d2, d3));
                crashReportCategory2.addDetail("Rotation", Float.valueOf(f));
                crashReportCategory2.addDetail("Delta", Float.valueOf(f2));
                throw new ReportedException(crashReport);
            }
        }
    }

    private void renderDebugBoundingBox(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, Entity entity2, float f) {
        if (!Shaders.isShadowPass) {
            float f2 = entity2.getWidth() / 2.0f;
            this.renderBoundingBox(matrixStack, iVertexBuilder, entity2, 1.0f, 1.0f, 1.0f);
            boolean bl = entity2 instanceof EnderDragonEntity;
            if (Reflector.IForgeEntity_isMultipartEntity.exists() && Reflector.IForgeEntity_getParts.exists()) {
                bl = Reflector.callBoolean(entity2, Reflector.IForgeEntity_isMultipartEntity, new Object[0]);
            }
            if (bl) {
                EnderDragonPartEntity[] enderDragonPartEntityArray;
                double d = -MathHelper.lerp((double)f, entity2.lastTickPosX, entity2.getPosX());
                double d2 = -MathHelper.lerp((double)f, entity2.lastTickPosY, entity2.getPosY());
                double d3 = -MathHelper.lerp((double)f, entity2.lastTickPosZ, entity2.getPosZ());
                for (EnderDragonPartEntity enderDragonPartEntity : enderDragonPartEntityArray = Reflector.IForgeEntity_getParts.exists() ? (Entity[])Reflector.call(entity2, Reflector.IForgeEntity_getParts, new Object[0]) : ((EnderDragonEntity)entity2).getDragonParts()) {
                    matrixStack.push();
                    double d4 = d + MathHelper.lerp((double)f, enderDragonPartEntity.lastTickPosX, enderDragonPartEntity.getPosX());
                    double d5 = d2 + MathHelper.lerp((double)f, enderDragonPartEntity.lastTickPosY, enderDragonPartEntity.getPosY());
                    double d6 = d3 + MathHelper.lerp((double)f, enderDragonPartEntity.lastTickPosZ, enderDragonPartEntity.getPosZ());
                    matrixStack.translate(d4, d5, d6);
                    this.renderBoundingBox(matrixStack, iVertexBuilder, enderDragonPartEntity, 0.25f, 1.0f, 0.0f);
                    matrixStack.pop();
                }
            }
            if (entity2 instanceof LivingEntity) {
                float f3 = 0.01f;
                WorldRenderer.drawBoundingBox(matrixStack, iVertexBuilder, -f2, entity2.getEyeHeight() - 0.01f, -f2, f2, entity2.getEyeHeight() + 0.01f, f2, 1.0f, 0.0f, 0.0f, 1.0f);
            }
            Vector3d vector3d = entity2.getLook(f);
            Matrix4f matrix4f = matrixStack.getLast().getMatrix();
            iVertexBuilder.pos(matrix4f, 0.0f, entity2.getEyeHeight(), 0.0f).color(0, 0, 255, 255).endVertex();
            iVertexBuilder.pos(matrix4f, (float)(vector3d.x * 2.0), (float)((double)entity2.getEyeHeight() + vector3d.y * 2.0), (float)(vector3d.z * 2.0)).color(0, 0, 255, 255).endVertex();
        }
    }

    private void renderBoundingBox(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, Entity entity2, float f, float f2, float f3) {
        AxisAlignedBB axisAlignedBB = entity2.getBoundingBox().offset(-entity2.getPosX(), -entity2.getPosY(), -entity2.getPosZ());
        WorldRenderer.drawBoundingBox(matrixStack, iVertexBuilder, axisAlignedBB, f, f2, f3, 1.0f);
    }

    private void renderFire(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, Entity entity2) {
        TextureAtlasSprite textureAtlasSprite = ModelBakery.LOCATION_FIRE_0.getSprite();
        TextureAtlasSprite textureAtlasSprite2 = ModelBakery.LOCATION_FIRE_1.getSprite();
        matrixStack.push();
        float f = entity2.getWidth() * 1.4f;
        matrixStack.scale(f, f, f);
        float f2 = 0.5f;
        float f3 = 0.0f;
        float f4 = entity2.getHeight() / f;
        float f5 = 0.0f;
        matrixStack.rotate(Vector3f.YP.rotationDegrees(-this.info.getYaw()));
        matrixStack.translate(0.0, 0.0, -0.3f + (float)((int)f4) * 0.02f);
        float f6 = 0.0f;
        int n = 0;
        IVertexBuilder iVertexBuilder = iRenderTypeBuffer.getBuffer(Atlases.getCutoutBlockType());
        if (Config.isMultiTexture()) {
            iVertexBuilder.setRenderBlocks(true);
        }
        MatrixStack.Entry entry = matrixStack.getLast();
        while (f4 > 0.0f) {
            TextureAtlasSprite textureAtlasSprite3 = n % 2 == 0 ? textureAtlasSprite : textureAtlasSprite2;
            iVertexBuilder.setSprite(textureAtlasSprite3);
            float f7 = textureAtlasSprite3.getMinU();
            float f8 = textureAtlasSprite3.getMinV();
            float f9 = textureAtlasSprite3.getMaxU();
            float f10 = textureAtlasSprite3.getMaxV();
            if (n / 2 % 2 == 0) {
                float f11 = f9;
                f9 = f7;
                f7 = f11;
            }
            EntityRendererManager.fireVertex(entry, iVertexBuilder, f2 - 0.0f, 0.0f - f5, f6, f9, f10);
            EntityRendererManager.fireVertex(entry, iVertexBuilder, -f2 - 0.0f, 0.0f - f5, f6, f7, f10);
            EntityRendererManager.fireVertex(entry, iVertexBuilder, -f2 - 0.0f, 1.4f - f5, f6, f7, f8);
            EntityRendererManager.fireVertex(entry, iVertexBuilder, f2 - 0.0f, 1.4f - f5, f6, f9, f8);
            f4 -= 0.45f;
            f5 -= 0.45f;
            f2 *= 0.9f;
            f6 += 0.03f;
            ++n;
        }
        matrixStack.pop();
    }

    private static void fireVertex(MatrixStack.Entry entry, IVertexBuilder iVertexBuilder, float f, float f2, float f3, float f4, float f5) {
        iVertexBuilder.pos(entry.getMatrix(), f, f2, f3).color(255, 255, 255, 255).tex(f4, f5).overlay(0, 10).lightmap(240).normal(entry.getNormal(), 0.0f, 1.0f, 0.0f).endVertex();
    }

    private static void renderShadow(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, Entity entity2, float f, float f2, IWorldReader iWorldReader, float f3) {
        if (!Config.isShaders() || !Shaders.shouldSkipDefaultShadow) {
            MobEntity mobEntity;
            float f4 = f3;
            if (entity2 instanceof MobEntity && (mobEntity = (MobEntity)entity2).isChild()) {
                f4 = f3 * 0.5f;
            }
            double d = MathHelper.lerp((double)f2, entity2.lastTickPosX, entity2.getPosX());
            double d2 = MathHelper.lerp((double)f2, entity2.lastTickPosY, entity2.getPosY());
            double d3 = MathHelper.lerp((double)f2, entity2.lastTickPosZ, entity2.getPosZ());
            int n = MathHelper.floor(d - (double)f4);
            int n2 = MathHelper.floor(d + (double)f4);
            int n3 = MathHelper.floor(d2 - (double)f4);
            int n4 = MathHelper.floor(d2);
            int n5 = MathHelper.floor(d3 - (double)f4);
            int n6 = MathHelper.floor(d3 + (double)f4);
            MatrixStack.Entry entry = matrixStack.getLast();
            IVertexBuilder iVertexBuilder = iRenderTypeBuffer.getBuffer(SHADOW_RENDER_TYPE);
            for (BlockPos blockPos : BlockPos.getAllInBoxMutable(new BlockPos(n, n3, n5), new BlockPos(n2, n4, n6))) {
                EntityRendererManager.renderBlockShadow(entry, iVertexBuilder, iWorldReader, blockPos, d, d2, d3, f4, f);
            }
        }
    }

    private static void renderBlockShadow(MatrixStack.Entry entry, IVertexBuilder iVertexBuilder, IWorldReader iWorldReader, BlockPos blockPos, double d, double d2, double d3, float f, float f2) {
        float f3;
        VoxelShape voxelShape;
        BlockPos blockPos2 = blockPos.down();
        BlockState blockState = iWorldReader.getBlockState(blockPos2);
        if (blockState.getRenderType() != BlockRenderType.INVISIBLE && iWorldReader.getLight(blockPos) > 3 && blockState.hasOpaqueCollisionShape(iWorldReader, blockPos2) && !(voxelShape = blockState.getShape(iWorldReader, blockPos.down())).isEmpty() && (f3 = (float)(((double)f2 - (d2 - (double)blockPos.getY()) / 2.0) * 0.5 * (double)iWorldReader.getBrightness(blockPos))) >= 0.0f) {
            if (f3 > 1.0f) {
                f3 = 1.0f;
            }
            AxisAlignedBB axisAlignedBB = voxelShape.getBoundingBox();
            double d4 = (double)blockPos.getX() + axisAlignedBB.minX;
            double d5 = (double)blockPos.getX() + axisAlignedBB.maxX;
            double d6 = (double)blockPos.getY() + axisAlignedBB.minY;
            double d7 = (double)blockPos.getZ() + axisAlignedBB.minZ;
            double d8 = (double)blockPos.getZ() + axisAlignedBB.maxZ;
            float f4 = (float)(d4 - d);
            float f5 = (float)(d5 - d);
            float f6 = (float)(d6 - d2);
            float f7 = (float)(d7 - d3);
            float f8 = (float)(d8 - d3);
            float f9 = -f4 / 2.0f / f + 0.5f;
            float f10 = -f5 / 2.0f / f + 0.5f;
            float f11 = -f7 / 2.0f / f + 0.5f;
            float f12 = -f8 / 2.0f / f + 0.5f;
            EntityRendererManager.shadowVertex(entry, iVertexBuilder, f3, f4, f6, f7, f9, f11);
            EntityRendererManager.shadowVertex(entry, iVertexBuilder, f3, f4, f6, f8, f9, f12);
            EntityRendererManager.shadowVertex(entry, iVertexBuilder, f3, f5, f6, f8, f10, f12);
            EntityRendererManager.shadowVertex(entry, iVertexBuilder, f3, f5, f6, f7, f10, f11);
        }
    }

    private static void shadowVertex(MatrixStack.Entry entry, IVertexBuilder iVertexBuilder, float f, float f2, float f3, float f4, float f5, float f6) {
        iVertexBuilder.pos(entry.getMatrix(), f2, f3, f4).color(1.0f, 1.0f, 1.0f, f).tex(f5, f6).overlay(OverlayTexture.NO_OVERLAY).lightmap(0xF000F0).normal(entry.getNormal(), 0.0f, 1.0f, 0.0f).endVertex();
    }

    public void setWorld(@Nullable World world) {
        this.world = world;
        if (world == null) {
            this.info = null;
        }
    }

    public double squareDistanceTo(Entity entity2) {
        return this.info.getProjectedView().squareDistanceTo(entity2.getPositionVec());
    }

    public double getDistanceToCamera(double d, double d2, double d3) {
        return this.info.getProjectedView().squareDistanceTo(d, d2, d3);
    }

    public Quaternion getCameraOrientation() {
        return this.cameraOrientation;
    }

    public FontRenderer getFontRenderer() {
        return this.textRenderer;
    }

    public Map<EntityType, EntityRenderer> getEntityRenderMap() {
        return this.renderers;
    }

    public Map<String, PlayerRenderer> getSkinMap() {
        return Collections.unmodifiableMap(this.skinMap);
    }
}


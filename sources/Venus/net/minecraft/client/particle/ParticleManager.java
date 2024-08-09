/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.particle;

import com.google.common.base.Charsets;
import com.google.common.collect.EvictingQueue;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.particle.AshParticle;
import net.minecraft.client.particle.BarrierParticle;
import net.minecraft.client.particle.BreakingParticle;
import net.minecraft.client.particle.BubbleColumnUpParticle;
import net.minecraft.client.particle.BubbleParticle;
import net.minecraft.client.particle.BubblePopParticle;
import net.minecraft.client.particle.CampfireParticle;
import net.minecraft.client.particle.CloudParticle;
import net.minecraft.client.particle.CritParticle;
import net.minecraft.client.particle.CurrentDownParticle;
import net.minecraft.client.particle.DiggingParticle;
import net.minecraft.client.particle.DragonBreathParticle;
import net.minecraft.client.particle.DripParticle;
import net.minecraft.client.particle.EmitterParticle;
import net.minecraft.client.particle.EnchantmentTableParticle;
import net.minecraft.client.particle.EndRodParticle;
import net.minecraft.client.particle.FallingDustParticle;
import net.minecraft.client.particle.FireworkParticle;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.particle.HeartParticle;
import net.minecraft.client.particle.HugeExplosionParticle;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.LargeExplosionParticle;
import net.minecraft.client.particle.LargeSmokeParticle;
import net.minecraft.client.particle.LavaParticle;
import net.minecraft.client.particle.MobAppearanceParticle;
import net.minecraft.client.particle.NoteParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.PoofParticle;
import net.minecraft.client.particle.PortalParticle;
import net.minecraft.client.particle.RainParticle;
import net.minecraft.client.particle.RedstoneParticle;
import net.minecraft.client.particle.ReversePortalParticle;
import net.minecraft.client.particle.SmokeParticle;
import net.minecraft.client.particle.SoulParticle;
import net.minecraft.client.particle.SpellParticle;
import net.minecraft.client.particle.SpitParticle;
import net.minecraft.client.particle.SplashParticle;
import net.minecraft.client.particle.SquidInkParticle;
import net.minecraft.client.particle.SuspendedTownParticle;
import net.minecraft.client.particle.SweepAttackParticle;
import net.minecraft.client.particle.TexturesParticle;
import net.minecraft.client.particle.TotemOfUndyingParticle;
import net.minecraft.client.particle.UnderwaterParticle;
import net.minecraft.client.particle.WaterWakeParticle;
import net.minecraft.client.particle.WhiteAshParticle;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.MissingTextureSprite;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.FluidState;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IFutureReloadListener;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.registry.Registry;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class ParticleManager
implements IFutureReloadListener {
    private static final List<IParticleRenderType> TYPES = ImmutableList.of(IParticleRenderType.TERRAIN_SHEET, IParticleRenderType.PARTICLE_SHEET_OPAQUE, IParticleRenderType.PARTICLE_SHEET_LIT, IParticleRenderType.PARTICLE_SHEET_TRANSLUCENT, IParticleRenderType.CUSTOM);
    protected ClientWorld world;
    private final Map<IParticleRenderType, Queue<Particle>> byType = Maps.newIdentityHashMap();
    private final Queue<EmitterParticle> particleEmitters = Queues.newArrayDeque();
    private final TextureManager renderer;
    private final Random rand = new Random();
    private final Map<ResourceLocation, IParticleFactory<?>> factories = new HashMap();
    private final Queue<Particle> queue = Queues.newArrayDeque();
    private final Map<ResourceLocation, AnimatedSpriteImpl> sprites = Maps.newHashMap();
    private final AtlasTexture atlas = new AtlasTexture(AtlasTexture.LOCATION_PARTICLES_TEXTURE);

    public ParticleManager(ClientWorld clientWorld, TextureManager textureManager) {
        textureManager.loadTexture(this.atlas.getTextureLocation(), this.atlas);
        this.world = clientWorld;
        this.renderer = textureManager;
        this.registerFactories();
    }

    private void registerFactories() {
        this.registerFactory(ParticleTypes.AMBIENT_ENTITY_EFFECT, SpellParticle.AmbientMobFactory::new);
        this.registerFactory(ParticleTypes.ANGRY_VILLAGER, HeartParticle.AngryVillagerFactory::new);
        this.registerFactory(ParticleTypes.BARRIER, new BarrierParticle.Factory());
        this.registerFactory(ParticleTypes.BLOCK, new DiggingParticle.Factory());
        this.registerFactory(ParticleTypes.BUBBLE, BubbleParticle.Factory::new);
        this.registerFactory(ParticleTypes.BUBBLE_COLUMN_UP, BubbleColumnUpParticle.Factory::new);
        this.registerFactory(ParticleTypes.BUBBLE_POP, BubblePopParticle.Factory::new);
        this.registerFactory(ParticleTypes.CAMPFIRE_COSY_SMOKE, CampfireParticle.CozySmokeFactory::new);
        this.registerFactory(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE, CampfireParticle.SignalSmokeFactory::new);
        this.registerFactory(ParticleTypes.CLOUD, CloudParticle.Factory::new);
        this.registerFactory(ParticleTypes.COMPOSTER, SuspendedTownParticle.ComposterFactory::new);
        this.registerFactory(ParticleTypes.CRIT, CritParticle.Factory::new);
        this.registerFactory(ParticleTypes.CURRENT_DOWN, CurrentDownParticle.Factory::new);
        this.registerFactory(ParticleTypes.DAMAGE_INDICATOR, CritParticle.DamageIndicatorFactory::new);
        this.registerFactory(ParticleTypes.DRAGON_BREATH, DragonBreathParticle.Factory::new);
        this.registerFactory(ParticleTypes.DOLPHIN, SuspendedTownParticle.DolphinSpeedFactory::new);
        this.registerFactory(ParticleTypes.DRIPPING_LAVA, DripParticle.DrippingLavaFactory::new);
        this.registerFactory(ParticleTypes.FALLING_LAVA, DripParticle.FallingLavaFactory::new);
        this.registerFactory(ParticleTypes.LANDING_LAVA, DripParticle.LandingLavaFactory::new);
        this.registerFactory(ParticleTypes.DRIPPING_WATER, DripParticle.DrippingWaterFactory::new);
        this.registerFactory(ParticleTypes.FALLING_WATER, DripParticle.FallingWaterFactory::new);
        this.registerFactory(ParticleTypes.DUST, RedstoneParticle.Factory::new);
        this.registerFactory(ParticleTypes.EFFECT, SpellParticle.Factory::new);
        this.registerFactory(ParticleTypes.ELDER_GUARDIAN, new MobAppearanceParticle.Factory());
        this.registerFactory(ParticleTypes.ENCHANTED_HIT, CritParticle.MagicFactory::new);
        this.registerFactory(ParticleTypes.ENCHANT, EnchantmentTableParticle.EnchantmentTable::new);
        this.registerFactory(ParticleTypes.END_ROD, EndRodParticle.Factory::new);
        this.registerFactory(ParticleTypes.ENTITY_EFFECT, SpellParticle.MobFactory::new);
        this.registerFactory(ParticleTypes.EXPLOSION_EMITTER, new HugeExplosionParticle.Factory());
        this.registerFactory(ParticleTypes.EXPLOSION, LargeExplosionParticle.Factory::new);
        this.registerFactory(ParticleTypes.FALLING_DUST, FallingDustParticle.Factory::new);
        this.registerFactory(ParticleTypes.FIREWORK, FireworkParticle.SparkFactory::new);
        this.registerFactory(ParticleTypes.FISHING, WaterWakeParticle.Factory::new);
        this.registerFactory(ParticleTypes.FLAME, FlameParticle.Factory::new);
        this.registerFactory(ParticleTypes.SOUL, SoulParticle.Factory::new);
        this.registerFactory(ParticleTypes.SOUL_FIRE_FLAME, FlameParticle.Factory::new);
        this.registerFactory(ParticleTypes.FLASH, FireworkParticle.OverlayFactory::new);
        this.registerFactory(ParticleTypes.HAPPY_VILLAGER, SuspendedTownParticle.HappyVillagerFactory::new);
        this.registerFactory(ParticleTypes.HEART, HeartParticle.Factory::new);
        this.registerFactory(ParticleTypes.INSTANT_EFFECT, SpellParticle.InstantFactory::new);
        this.registerFactory(ParticleTypes.ITEM, new BreakingParticle.Factory());
        this.registerFactory(ParticleTypes.ITEM_SLIME, new BreakingParticle.SlimeFactory());
        this.registerFactory(ParticleTypes.ITEM_SNOWBALL, new BreakingParticle.SnowballFactory());
        this.registerFactory(ParticleTypes.LARGE_SMOKE, LargeSmokeParticle.Factory::new);
        this.registerFactory(ParticleTypes.LAVA, LavaParticle.Factory::new);
        this.registerFactory(ParticleTypes.MYCELIUM, SuspendedTownParticle.Factory::new);
        this.registerFactory(ParticleTypes.NAUTILUS, EnchantmentTableParticle.NautilusFactory::new);
        this.registerFactory(ParticleTypes.NOTE, NoteParticle.Factory::new);
        this.registerFactory(ParticleTypes.POOF, PoofParticle.Factory::new);
        this.registerFactory(ParticleTypes.PORTAL, PortalParticle.Factory::new);
        this.registerFactory(ParticleTypes.RAIN, RainParticle.Factory::new);
        this.registerFactory(ParticleTypes.SMOKE, SmokeParticle.Factory::new);
        this.registerFactory(ParticleTypes.SNEEZE, CloudParticle.SneezeFactory::new);
        this.registerFactory(ParticleTypes.SPIT, SpitParticle.Factory::new);
        this.registerFactory(ParticleTypes.SWEEP_ATTACK, SweepAttackParticle.Factory::new);
        this.registerFactory(ParticleTypes.TOTEM_OF_UNDYING, TotemOfUndyingParticle.Factory::new);
        this.registerFactory(ParticleTypes.SQUID_INK, SquidInkParticle.Factory::new);
        this.registerFactory(ParticleTypes.UNDERWATER, UnderwaterParticle.UnderwaterFactory::new);
        this.registerFactory(ParticleTypes.SPLASH, SplashParticle.Factory::new);
        this.registerFactory(ParticleTypes.WITCH, SpellParticle.WitchFactory::new);
        this.registerFactory(ParticleTypes.DRIPPING_HONEY, DripParticle.DrippingHoneyFactory::new);
        this.registerFactory(ParticleTypes.FALLING_HONEY, DripParticle.FallingHoneyFactory::new);
        this.registerFactory(ParticleTypes.LANDING_HONEY, DripParticle.LandingHoneyFactory::new);
        this.registerFactory(ParticleTypes.FALLING_NECTAR, DripParticle.FallingNectarFactory::new);
        this.registerFactory(ParticleTypes.ASH, AshParticle.Factory::new);
        this.registerFactory(ParticleTypes.CRIMSON_SPORE, UnderwaterParticle.CrimsonSporeFactory::new);
        this.registerFactory(ParticleTypes.WARPED_SPORE, UnderwaterParticle.WarpedSporeFactory::new);
        this.registerFactory(ParticleTypes.DRIPPING_OBSIDIAN_TEAR, DripParticle.DrippingObsidianTearFactory::new);
        this.registerFactory(ParticleTypes.FALLING_OBSIDIAN_TEAR, DripParticle.FallingObsidianTearFactory::new);
        this.registerFactory(ParticleTypes.LANDING_OBSIDIAN_TEAR, DripParticle.LandingObsidianTearFactory::new);
        this.registerFactory(ParticleTypes.REVERSE_PORTAL, ReversePortalParticle.Factory::new);
        this.registerFactory(ParticleTypes.WHITE_ASH, WhiteAshParticle.Factory::new);
    }

    private <T extends IParticleData> void registerFactory(ParticleType<T> particleType, IParticleFactory<T> iParticleFactory) {
        this.factories.put(Registry.PARTICLE_TYPE.getKey(particleType), iParticleFactory);
    }

    private <T extends IParticleData> void registerFactory(ParticleType<T> particleType, IParticleMetaFactory<T> iParticleMetaFactory) {
        AnimatedSpriteImpl animatedSpriteImpl = new AnimatedSpriteImpl(this);
        this.sprites.put(Registry.PARTICLE_TYPE.getKey(particleType), animatedSpriteImpl);
        this.factories.put(Registry.PARTICLE_TYPE.getKey(particleType), iParticleMetaFactory.create(animatedSpriteImpl));
    }

    @Override
    public CompletableFuture<Void> reload(IFutureReloadListener.IStage iStage, IResourceManager iResourceManager, IProfiler iProfiler, IProfiler iProfiler2, Executor executor, Executor executor2) {
        ConcurrentMap concurrentMap = Maps.newConcurrentMap();
        CompletableFuture[] completableFutureArray = (CompletableFuture[])Registry.PARTICLE_TYPE.keySet().stream().map(arg_0 -> this.lambda$reload$1(iResourceManager, concurrentMap, executor, arg_0)).toArray(ParticleManager::lambda$reload$2);
        return ((CompletableFuture)((CompletableFuture)CompletableFuture.allOf(completableFutureArray).thenApplyAsync(arg_0 -> this.lambda$reload$3(iProfiler, iResourceManager, concurrentMap, arg_0), executor)).thenCompose(iStage::markCompleteAwaitingOthers)).thenAcceptAsync(arg_0 -> this.lambda$reload$5(iProfiler2, concurrentMap, arg_0), executor2);
    }

    public void close() {
        this.atlas.clear();
    }

    private void loadTextureLists(IResourceManager iResourceManager, ResourceLocation resourceLocation, Map<ResourceLocation, List<ResourceLocation>> map) {
        ResourceLocation resourceLocation2 = new ResourceLocation(resourceLocation.getNamespace(), "particles/" + resourceLocation.getPath() + ".json");
        try (IResource iResource = iResourceManager.getResource(resourceLocation2);
             InputStreamReader inputStreamReader = new InputStreamReader(iResource.getInputStream(), Charsets.UTF_8);){
            TexturesParticle texturesParticle = TexturesParticle.deserialize(JSONUtils.fromJson(inputStreamReader));
            List<ResourceLocation> list = texturesParticle.getTextures();
            boolean bl = this.sprites.containsKey(resourceLocation);
            if (list == null) {
                if (bl) {
                    throw new IllegalStateException("Missing texture list for particle " + resourceLocation);
                }
            } else {
                if (!bl) {
                    throw new IllegalStateException("Redundant texture list for particle " + resourceLocation);
                }
                map.put(resourceLocation, list.stream().map(ParticleManager::lambda$loadTextureLists$6).collect(Collectors.toList()));
            }
        } catch (IOException iOException) {
            throw new IllegalStateException("Failed to load description for particle " + resourceLocation, iOException);
        }
    }

    public void addParticleEmitter(Entity entity2, IParticleData iParticleData) {
        this.particleEmitters.add(new EmitterParticle(this.world, entity2, iParticleData));
    }

    public void emitParticleAtEntity(Entity entity2, IParticleData iParticleData, int n) {
        this.particleEmitters.add(new EmitterParticle(this.world, entity2, iParticleData, n));
    }

    @Nullable
    public Particle addParticle(IParticleData iParticleData, double d, double d2, double d3, double d4, double d5, double d6) {
        Particle particle = this.makeParticle(iParticleData, d, d2, d3, d4, d5, d6);
        if (particle != null) {
            this.addEffect(particle);
            return particle;
        }
        return null;
    }

    @Nullable
    private <T extends IParticleData> Particle makeParticle(T t, double d, double d2, double d3, double d4, double d5, double d6) {
        IParticleFactory<?> iParticleFactory = this.factories.get(Registry.PARTICLE_TYPE.getKey(t.getType()));
        return iParticleFactory == null ? null : iParticleFactory.makeParticle(t, this.world, d, d2, d3, d4, d5, d6);
    }

    public void addEffect(Particle particle) {
        if (particle != null && (!(particle instanceof FireworkParticle.Spark) || Config.isFireworkParticles())) {
            this.queue.add(particle);
        }
    }

    public void tick() {
        Object object;
        this.byType.forEach(this::lambda$tick$7);
        if (!this.particleEmitters.isEmpty()) {
            object = Lists.newArrayList();
            for (EmitterParticle emitterParticle : this.particleEmitters) {
                emitterParticle.tick();
                if (emitterParticle.isAlive()) continue;
                object.add(emitterParticle);
            }
            this.particleEmitters.removeAll((Collection<?>)object);
        }
        if (!this.queue.isEmpty()) {
            while ((object = this.queue.poll()) != null) {
                Queue queue = this.byType.computeIfAbsent(((Particle)object).getRenderType(), ParticleManager::lambda$tick$8);
                if (object instanceof BarrierParticle && this.reuseBarrierParticle((Particle)object, queue)) continue;
                queue.add(object);
            }
        }
    }

    private void tickParticleList(Collection<Particle> collection) {
        if (!collection.isEmpty()) {
            long l = System.currentTimeMillis();
            int n = collection.size();
            Iterator<Particle> iterator2 = collection.iterator();
            while (iterator2.hasNext()) {
                Particle particle = iterator2.next();
                this.tickParticle(particle);
                if (!particle.isAlive()) {
                    iterator2.remove();
                }
                --n;
                if (System.currentTimeMillis() <= l + 20L) continue;
                break;
            }
            if (n > 0) {
                Iterator<Particle> iterator3 = collection.iterator();
                for (int i = n; iterator3.hasNext() && i > 0; --i) {
                    Particle particle = iterator3.next();
                    particle.setExpired();
                    iterator3.remove();
                }
            }
        }
    }

    private void tickParticle(Particle particle) {
        try {
            particle.tick();
        } catch (Throwable throwable) {
            CrashReport crashReport = CrashReport.makeCrashReport(throwable, "Ticking Particle");
            CrashReportCategory crashReportCategory = crashReport.makeCategory("Particle being ticked");
            crashReportCategory.addDetail("Particle", particle::toString);
            crashReportCategory.addDetail("Particle Type", particle.getRenderType()::toString);
            throw new ReportedException(crashReport);
        }
    }

    public void renderParticles(MatrixStack matrixStack, IRenderTypeBuffer.Impl impl, LightTexture lightTexture, ActiveRenderInfo activeRenderInfo, float f) {
        this.renderParticles(matrixStack, impl, lightTexture, activeRenderInfo, f, null);
    }

    public void renderParticles(MatrixStack matrixStack, IRenderTypeBuffer.Impl impl, LightTexture lightTexture, ActiveRenderInfo activeRenderInfo, float f, ClippingHelper clippingHelper) {
        lightTexture.enableLightmap();
        Runnable runnable = ParticleManager::lambda$renderParticles$9;
        FluidState fluidState = activeRenderInfo.getFluidState();
        boolean bl = fluidState.isTagged(FluidTags.WATER);
        RenderSystem.pushMatrix();
        RenderSystem.multMatrix(matrixStack.getLast().getMatrix());
        Collection<IParticleRenderType> collection = TYPES;
        if (Reflector.ForgeHooksClient.exists()) {
            collection = this.byType.keySet();
        }
        for (IParticleRenderType iParticleRenderType : collection) {
            if (iParticleRenderType == IParticleRenderType.NO_RENDER) continue;
            runnable.run();
            Iterable iterable = this.byType.get(iParticleRenderType);
            if (iterable == null) continue;
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferBuilder = tessellator.getBuffer();
            iParticleRenderType.beginRender(bufferBuilder, this.renderer);
            for (Particle particle : iterable) {
                if (clippingHelper != null && particle.shouldCull() && !clippingHelper.isBoundingBoxInFrustum(particle.getBoundingBox())) continue;
                try {
                    if (!bl && particle instanceof UnderwaterParticle && particle.motionX == 0.0 && particle.motionY == 0.0 && particle.motionZ == 0.0) continue;
                    particle.renderParticle(bufferBuilder, activeRenderInfo, f);
                } catch (Throwable throwable) {
                    CrashReport crashReport = CrashReport.makeCrashReport(throwable, "Rendering Particle");
                    CrashReportCategory crashReportCategory = crashReport.makeCategory("Particle being rendered");
                    crashReportCategory.addDetail("Particle", particle::toString);
                    crashReportCategory.addDetail("Particle Type", iParticleRenderType::toString);
                    throw new ReportedException(crashReport);
                }
            }
            iParticleRenderType.finishRender(tessellator);
        }
        RenderSystem.popMatrix();
        RenderSystem.depthMask(true);
        RenderSystem.depthFunc(515);
        RenderSystem.disableBlend();
        RenderSystem.defaultAlphaFunc();
        lightTexture.disableLightmap();
        RenderSystem.disableFog();
        RenderSystem.enableDepthTest();
    }

    public void clearEffects(@Nullable ClientWorld clientWorld) {
        this.world = clientWorld;
        this.byType.clear();
        this.particleEmitters.clear();
    }

    public void addBlockDestroyEffects(BlockPos blockPos, BlockState blockState) {
        boolean bl;
        Object object;
        if (Reflector.IForgeBlockState_addDestroyEffects.exists() && Reflector.IForgeBlockState_isAir2.exists()) {
            object = blockState.getBlock();
            bl = !Reflector.callBoolean(blockState, Reflector.IForgeBlockState_isAir2, this.world, blockPos) && !Reflector.callBoolean(blockState, Reflector.IForgeBlockState_addDestroyEffects, this.world, blockPos, this);
        } else {
            boolean bl2 = bl = !blockState.isAir();
        }
        if (bl) {
            object = blockState.getShape(this.world, blockPos);
            double d = 0.25;
            ((VoxelShape)object).forEachBox((arg_0, arg_1, arg_2, arg_3, arg_4, arg_5) -> this.lambda$addBlockDestroyEffects$10(blockPos, blockState, arg_0, arg_1, arg_2, arg_3, arg_4, arg_5));
        }
    }

    public void addBlockHitEffects(BlockPos blockPos, Direction direction) {
        BlockState blockState = this.world.getBlockState(blockPos);
        if (blockState.getRenderType() != BlockRenderType.INVISIBLE) {
            int n = blockPos.getX();
            int n2 = blockPos.getY();
            int n3 = blockPos.getZ();
            float f = 0.1f;
            AxisAlignedBB axisAlignedBB = blockState.getShape(this.world, blockPos).getBoundingBox();
            double d = (double)n + this.rand.nextDouble() * (axisAlignedBB.maxX - axisAlignedBB.minX - (double)0.2f) + (double)0.1f + axisAlignedBB.minX;
            double d2 = (double)n2 + this.rand.nextDouble() * (axisAlignedBB.maxY - axisAlignedBB.minY - (double)0.2f) + (double)0.1f + axisAlignedBB.minY;
            double d3 = (double)n3 + this.rand.nextDouble() * (axisAlignedBB.maxZ - axisAlignedBB.minZ - (double)0.2f) + (double)0.1f + axisAlignedBB.minZ;
            if (direction == Direction.DOWN) {
                d2 = (double)n2 + axisAlignedBB.minY - (double)0.1f;
            }
            if (direction == Direction.UP) {
                d2 = (double)n2 + axisAlignedBB.maxY + (double)0.1f;
            }
            if (direction == Direction.NORTH) {
                d3 = (double)n3 + axisAlignedBB.minZ - (double)0.1f;
            }
            if (direction == Direction.SOUTH) {
                d3 = (double)n3 + axisAlignedBB.maxZ + (double)0.1f;
            }
            if (direction == Direction.WEST) {
                d = (double)n + axisAlignedBB.minX - (double)0.1f;
            }
            if (direction == Direction.EAST) {
                d = (double)n + axisAlignedBB.maxX + (double)0.1f;
            }
            this.addEffect(new DiggingParticle(this.world, d, d2, d3, 0.0, 0.0, 0.0, blockState).setBlockPos(blockPos).multiplyVelocity(0.2f).multiplyParticleScaleBy(0.6f));
        }
    }

    public String getStatistics() {
        return String.valueOf(this.byType.values().stream().mapToInt(Collection::size).sum());
    }

    private boolean reuseBarrierParticle(Particle particle, Queue<Particle> queue) {
        for (Particle particle2 : queue) {
            if (!(particle2 instanceof BarrierParticle) || particle.prevPosX != particle2.prevPosX || particle.prevPosY != particle2.prevPosY || particle.prevPosZ != particle2.prevPosZ) continue;
            particle2.age = 0;
            return false;
        }
        return true;
    }

    public void addBlockHitEffects(BlockPos blockPos, BlockRayTraceResult blockRayTraceResult) {
        boolean bl;
        BlockState blockState = this.world.getBlockState(blockPos);
        if (blockState != null && !(bl = Reflector.callBoolean(blockState, Reflector.IForgeBlockState_addHitEffects, this.world, blockRayTraceResult, this))) {
            Direction direction = blockRayTraceResult.getFace();
            this.addBlockHitEffects(blockPos, direction);
        }
    }

    private void lambda$addBlockDestroyEffects$10(BlockPos blockPos, BlockState blockState, double d, double d2, double d3, double d4, double d5, double d6) {
        double d7 = Math.min(1.0, d4 - d);
        double d8 = Math.min(1.0, d5 - d2);
        double d9 = Math.min(1.0, d6 - d3);
        int n = Math.max(2, MathHelper.ceil(d7 / 0.25));
        int n2 = Math.max(2, MathHelper.ceil(d8 / 0.25));
        int n3 = Math.max(2, MathHelper.ceil(d9 / 0.25));
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n2; ++j) {
                for (int k = 0; k < n3; ++k) {
                    double d10 = ((double)i + 0.5) / (double)n;
                    double d11 = ((double)j + 0.5) / (double)n2;
                    double d12 = ((double)k + 0.5) / (double)n3;
                    double d13 = d10 * d7 + d;
                    double d14 = d11 * d8 + d2;
                    double d15 = d12 * d9 + d3;
                    this.addEffect(new DiggingParticle(this.world, (double)blockPos.getX() + d13, (double)blockPos.getY() + d14, (double)blockPos.getZ() + d15, d10 - 0.5, d11 - 0.5, d12 - 0.5, blockState).setBlockPos(blockPos));
                }
            }
        }
    }

    private static void lambda$renderParticles$9() {
        RenderSystem.enableAlphaTest();
        RenderSystem.defaultAlphaFunc();
        RenderSystem.enableDepthTest();
        RenderSystem.enableFog();
        if (Reflector.ForgeHooksClient.exists()) {
            RenderSystem.activeTexture(33986);
            RenderSystem.enableTexture();
            RenderSystem.activeTexture(33984);
        }
    }

    private static Queue lambda$tick$8(IParticleRenderType iParticleRenderType) {
        return EvictingQueue.create(16384);
    }

    private void lambda$tick$7(IParticleRenderType iParticleRenderType, Queue queue) {
        this.world.getProfiler().startSection(iParticleRenderType.toString());
        this.tickParticleList(queue);
        this.world.getProfiler().endSection();
    }

    private static ResourceLocation lambda$loadTextureLists$6(ResourceLocation resourceLocation) {
        return new ResourceLocation(resourceLocation.getNamespace(), "particle/" + resourceLocation.getPath());
    }

    private void lambda$reload$5(IProfiler iProfiler, Map map, AtlasTexture.SheetData sheetData) {
        this.byType.clear();
        iProfiler.startTick();
        iProfiler.startSection("upload");
        this.atlas.upload(sheetData);
        iProfiler.endStartSection("bindSpriteSets");
        TextureAtlasSprite textureAtlasSprite = this.atlas.getSprite(MissingTextureSprite.getLocation());
        map.forEach((arg_0, arg_1) -> this.lambda$reload$4(textureAtlasSprite, arg_0, arg_1));
        iProfiler.endSection();
        iProfiler.endTick();
    }

    private void lambda$reload$4(TextureAtlasSprite textureAtlasSprite, ResourceLocation resourceLocation, List list) {
        ImmutableList<TextureAtlasSprite> immutableList = list.isEmpty() ? ImmutableList.of(textureAtlasSprite) : list.stream().map(this.atlas::getSprite).collect(ImmutableList.toImmutableList());
        this.sprites.get(resourceLocation).setSprites(immutableList);
    }

    private AtlasTexture.SheetData lambda$reload$3(IProfiler iProfiler, IResourceManager iResourceManager, Map map, Void void_) {
        iProfiler.startTick();
        iProfiler.startSection("stitching");
        AtlasTexture.SheetData sheetData = this.atlas.stitch(iResourceManager, map.values().stream().flatMap(Collection::stream), iProfiler, 0);
        iProfiler.endSection();
        iProfiler.endTick();
        return sheetData;
    }

    private static CompletableFuture[] lambda$reload$2(int n) {
        return new CompletableFuture[n];
    }

    private CompletableFuture lambda$reload$1(IResourceManager iResourceManager, Map map, Executor executor, ResourceLocation resourceLocation) {
        return CompletableFuture.runAsync(() -> this.lambda$reload$0(iResourceManager, resourceLocation, map), executor);
    }

    private void lambda$reload$0(IResourceManager iResourceManager, ResourceLocation resourceLocation, Map map) {
        this.loadTextureLists(iResourceManager, resourceLocation, map);
    }

    @FunctionalInterface
    static interface IParticleMetaFactory<T extends IParticleData> {
        public IParticleFactory<T> create(IAnimatedSprite var1);
    }

    class AnimatedSpriteImpl
    implements IAnimatedSprite {
        private List<TextureAtlasSprite> sprites;
        final ParticleManager this$0;

        private AnimatedSpriteImpl(ParticleManager particleManager) {
            this.this$0 = particleManager;
        }

        @Override
        public TextureAtlasSprite get(int n, int n2) {
            return this.sprites.get(n * (this.sprites.size() - 1) / n2);
        }

        @Override
        public TextureAtlasSprite get(Random random2) {
            return this.sprites.get(random2.nextInt(this.sprites.size()));
        }

        public void setSprites(List<TextureAtlasSprite> list) {
            this.sprites = ImmutableList.copyOf(list);
        }
    }
}


/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.reflect;

import com.mojang.blaze3d.matrix.MatrixStack;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.fonts.FontResourceManager;
import net.minecraft.client.gui.screen.EnchantmentScreen;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EnderCrystalRenderer;
import net.minecraft.client.renderer.entity.EnderDragonRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.EvokerFangsRenderer;
import net.minecraft.client.renderer.entity.ItemFrameRenderer;
import net.minecraft.client.renderer.entity.LeashKnotRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.LlamaSpitRenderer;
import net.minecraft.client.renderer.entity.MinecartRenderer;
import net.minecraft.client.renderer.entity.PufferfishRenderer;
import net.minecraft.client.renderer.entity.ShulkerBulletRenderer;
import net.minecraft.client.renderer.entity.TridentRenderer;
import net.minecraft.client.renderer.entity.TropicalFishRenderer;
import net.minecraft.client.renderer.entity.WitherSkullRenderer;
import net.minecraft.client.renderer.entity.layers.LlamaDecorLayer;
import net.minecraft.client.renderer.entity.model.ArmorStandModel;
import net.minecraft.client.renderer.entity.model.BatModel;
import net.minecraft.client.renderer.entity.model.BeeModel;
import net.minecraft.client.renderer.entity.model.BlazeModel;
import net.minecraft.client.renderer.entity.model.BoarModel;
import net.minecraft.client.renderer.entity.model.BoatModel;
import net.minecraft.client.renderer.entity.model.BookModel;
import net.minecraft.client.renderer.entity.model.ChickenModel;
import net.minecraft.client.renderer.entity.model.CodModel;
import net.minecraft.client.renderer.entity.model.CreeperModel;
import net.minecraft.client.renderer.entity.model.EndermiteModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.EvokerFangsModel;
import net.minecraft.client.renderer.entity.model.FoxModel;
import net.minecraft.client.renderer.entity.model.GenericHeadModel;
import net.minecraft.client.renderer.entity.model.GuardianModel;
import net.minecraft.client.renderer.entity.model.HorseArmorChestsModel;
import net.minecraft.client.renderer.entity.model.HorseModel;
import net.minecraft.client.renderer.entity.model.HumanoidHeadModel;
import net.minecraft.client.renderer.entity.model.IllagerModel;
import net.minecraft.client.renderer.entity.model.IronGolemModel;
import net.minecraft.client.renderer.entity.model.LeashKnotModel;
import net.minecraft.client.renderer.entity.model.LlamaModel;
import net.minecraft.client.renderer.entity.model.LlamaSpitModel;
import net.minecraft.client.renderer.entity.model.MagmaCubeModel;
import net.minecraft.client.renderer.entity.model.MinecartModel;
import net.minecraft.client.renderer.entity.model.OcelotModel;
import net.minecraft.client.renderer.entity.model.ParrotModel;
import net.minecraft.client.renderer.entity.model.PhantomModel;
import net.minecraft.client.renderer.entity.model.PufferFishBigModel;
import net.minecraft.client.renderer.entity.model.PufferFishMediumModel;
import net.minecraft.client.renderer.entity.model.PufferFishSmallModel;
import net.minecraft.client.renderer.entity.model.QuadrupedModel;
import net.minecraft.client.renderer.entity.model.RabbitModel;
import net.minecraft.client.renderer.entity.model.RavagerModel;
import net.minecraft.client.renderer.entity.model.SalmonModel;
import net.minecraft.client.renderer.entity.model.ShulkerBulletModel;
import net.minecraft.client.renderer.entity.model.ShulkerModel;
import net.minecraft.client.renderer.entity.model.SilverfishModel;
import net.minecraft.client.renderer.entity.model.SlimeModel;
import net.minecraft.client.renderer.entity.model.SnowManModel;
import net.minecraft.client.renderer.entity.model.SpiderModel;
import net.minecraft.client.renderer.entity.model.SquidModel;
import net.minecraft.client.renderer.entity.model.StriderModel;
import net.minecraft.client.renderer.entity.model.TridentModel;
import net.minecraft.client.renderer.entity.model.TropicalFishAModel;
import net.minecraft.client.renderer.entity.model.TropicalFishBModel;
import net.minecraft.client.renderer.entity.model.TurtleModel;
import net.minecraft.client.renderer.entity.model.VexModel;
import net.minecraft.client.renderer.entity.model.VillagerModel;
import net.minecraft.client.renderer.entity.model.WitchModel;
import net.minecraft.client.renderer.entity.model.WitherModel;
import net.minecraft.client.renderer.entity.model.WolfModel;
import net.minecraft.client.renderer.model.ItemOverride;
import net.minecraft.client.renderer.model.ModelManager;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.tileentity.BannerTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.BedTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.BellTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.ChestTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.ConduitTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.EnchantmentTableTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.LecternTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.ShulkerBoxTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.SignTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.SkullTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.model.DragonHeadModel;
import net.minecraft.client.resources.LegacyResourcePackWrapper;
import net.minecraft.client.resources.LegacyResourcePackWrapperV4;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.world.DimensionRenderInfo;
import net.minecraft.crash.CrashReport;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.item.ItemFrameEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.resources.IResourcePack;
import net.minecraft.tags.ItemTags;
import net.minecraft.tileentity.BeaconTileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.server.Ticket;
import net.minecraft.world.server.TicketType;
import net.minecraftforge.eventbus.api.Event;
import net.optifine.Log;
import net.optifine.reflect.FieldLocatorTypes;
import net.optifine.reflect.IResolvable;
import net.optifine.reflect.ReflectorClass;
import net.optifine.reflect.ReflectorConstructor;
import net.optifine.reflect.ReflectorField;
import net.optifine.reflect.ReflectorFields;
import net.optifine.reflect.ReflectorForge;
import net.optifine.reflect.ReflectorMethod;
import net.optifine.reflect.ReflectorResolver;
import net.optifine.util.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Reflector {
    private static final Logger LOGGER = LogManager.getLogger();
    private static boolean logForge = Reflector.registerResolvable("*** Reflector Forge ***");
    public static ReflectorClass BetterFoliageClient = new ReflectorClass("mods.betterfoliage.client.BetterFoliageClient");
    public static ReflectorClass BrandingControl = new ReflectorClass("net.minecraftforge.fml.BrandingControl");
    public static ReflectorMethod BrandingControl_getBrandings = new ReflectorMethod(BrandingControl, "getBrandings");
    public static ReflectorMethod BrandingControl_forEachLine = new ReflectorMethod(BrandingControl, "forEachLine");
    public static ReflectorMethod BrandingControl_forEachAboveCopyrightLine = new ReflectorMethod(BrandingControl, "forEachAboveCopyrightLine");
    public static ReflectorClass ICapabilityProvider = new ReflectorClass("net.minecraftforge.common.capabilities.ICapabilityProvider");
    public static ReflectorClass CapabilityProvider = new ReflectorClass("net.minecraftforge.common.capabilities.CapabilityProvider");
    public static ReflectorMethod CapabilityProvider_gatherCapabilities = new ReflectorMethod(CapabilityProvider, "gatherCapabilities", new Class[0]);
    public static ReflectorClass ClientModLoader = new ReflectorClass("net.minecraftforge.fml.client.ClientModLoader");
    public static ReflectorMethod ClientModLoader_isLoading = new ReflectorMethod(ClientModLoader, "isLoading");
    public static ReflectorMethod ClientModLoader_renderProgressText = new ReflectorMethod(ClientModLoader, "renderProgressText");
    public static ReflectorClass ChunkDataEvent_Save = new ReflectorClass("net.minecraftforge.event.world.ChunkDataEvent$Save");
    public static ReflectorConstructor ChunkDataEvent_Save_Constructor = new ReflectorConstructor(ChunkDataEvent_Save, new Class[]{IChunk.class, IWorld.class, CompoundNBT.class});
    public static ReflectorClass ChunkEvent_Load = new ReflectorClass("net.minecraftforge.event.world.ChunkEvent$Load");
    public static ReflectorConstructor ChunkEvent_Load_Constructor = new ReflectorConstructor(ChunkEvent_Load, new Class[]{IChunk.class});
    public static ReflectorClass ChunkEvent_Unload = new ReflectorClass("net.minecraftforge.event.world.ChunkEvent$Unload");
    public static ReflectorConstructor ChunkEvent_Unload_Constructor = new ReflectorConstructor(ChunkEvent_Unload, new Class[]{IChunk.class});
    public static ReflectorClass ClientHooks = new ReflectorClass("net.minecraftforge.fml.client.ClientHooks");
    public static ReflectorMethod ClientHooks_trackBrokenTexture = new ReflectorMethod(ClientHooks, "trackBrokenTexture");
    public static ReflectorMethod ClientHooks_trackMissingTexture = new ReflectorMethod(ClientHooks, "trackMissingTexture");
    public static ReflectorClass CrashReportExtender = new ReflectorClass("net.minecraftforge.fml.CrashReportExtender");
    public static ReflectorMethod CrashReportExtender_enhanceCrashReport = new ReflectorMethod(CrashReportExtender, "enhanceCrashReport");
    public static ReflectorMethod CrashReportExtender_addCrashReportHeader = new ReflectorMethod(CrashReportExtender, "addCrashReportHeader");
    public static ReflectorMethod CrashReportExtender_generateEnhancedStackTraceT = new ReflectorMethod(CrashReportExtender, "generateEnhancedStackTrace", new Class[]{Throwable.class});
    public static ReflectorMethod CrashReportExtender_generateEnhancedStackTraceSTE = new ReflectorMethod(CrashReportExtender, "generateEnhancedStackTrace", new Class[]{StackTraceElement[].class});
    public static ReflectorClass DrawScreenEvent_Pre = new ReflectorClass("net.minecraftforge.client.event.GuiScreenEvent$DrawScreenEvent$Pre");
    public static ReflectorConstructor DrawScreenEvent_Pre_Constructor = new ReflectorConstructor(DrawScreenEvent_Pre, new Class[]{Screen.class, MatrixStack.class, Integer.TYPE, Integer.TYPE, Float.TYPE});
    public static ReflectorClass DrawScreenEvent_Post = new ReflectorClass("net.minecraftforge.client.event.GuiScreenEvent$DrawScreenEvent$Post");
    public static ReflectorConstructor DrawScreenEvent_Post_Constructor = new ReflectorConstructor(DrawScreenEvent_Post, new Class[]{Screen.class, MatrixStack.class, Integer.TYPE, Integer.TYPE, Float.TYPE});
    public static ReflectorClass EarlyProgressVisualization = new ReflectorClass("net.minecraftforge.fml.loading.progress.EarlyProgressVisualization");
    public static ReflectorField EarlyProgressVisualization_INSTANCE = new ReflectorField(EarlyProgressVisualization, "INSTANCE");
    public static ReflectorMethod EarlyProgressVisualization_handOffWindow = new ReflectorMethod(EarlyProgressVisualization, "handOffWindow");
    public static ReflectorClass EntityLeaveWorldEvent = new ReflectorClass("net.minecraftforge.event.entity.EntityLeaveWorldEvent");
    public static ReflectorConstructor EntityLeaveWorldEvent_Constructor = new ReflectorConstructor(EntityLeaveWorldEvent, new Class[]{Entity.class, World.class});
    public static ReflectorClass EntityViewRenderEvent_CameraSetup = new ReflectorClass("net.minecraftforge.client.event.EntityViewRenderEvent$CameraSetup");
    public static ReflectorMethod EntityViewRenderEvent_CameraSetup_getYaw = new ReflectorMethod(EntityViewRenderEvent_CameraSetup, "getYaw");
    public static ReflectorMethod EntityViewRenderEvent_CameraSetup_getPitch = new ReflectorMethod(EntityViewRenderEvent_CameraSetup, "getPitch");
    public static ReflectorMethod EntityViewRenderEvent_CameraSetup_getRoll = new ReflectorMethod(EntityViewRenderEvent_CameraSetup, "getRoll");
    public static ReflectorClass EntityViewRenderEvent_FogColors = new ReflectorClass("net.minecraftforge.client.event.EntityViewRenderEvent$FogColors");
    public static ReflectorConstructor EntityViewRenderEvent_FogColors_Constructor = new ReflectorConstructor(EntityViewRenderEvent_FogColors, new Class[]{ActiveRenderInfo.class, Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE});
    public static ReflectorMethod EntityViewRenderEvent_FogColors_getRed = new ReflectorMethod(EntityViewRenderEvent_FogColors, "getRed");
    public static ReflectorMethod EntityViewRenderEvent_FogColors_getGreen = new ReflectorMethod(EntityViewRenderEvent_FogColors, "getGreen");
    public static ReflectorMethod EntityViewRenderEvent_FogColors_getBlue = new ReflectorMethod(EntityViewRenderEvent_FogColors, "getBlue");
    public static ReflectorClass EntityViewRenderEvent_RenderFogEvent = new ReflectorClass("net.minecraftforge.client.event.EntityViewRenderEvent$RenderFogEvent");
    public static ReflectorConstructor EntityViewRenderEvent_RenderFogEvent_Constructor = new ReflectorConstructor(EntityViewRenderEvent_RenderFogEvent, new Class[]{FogRenderer.FogType.class, ActiveRenderInfo.class, Float.TYPE, Float.TYPE});
    public static ReflectorClass EntityJoinWorldEvent = new ReflectorClass("net.minecraftforge.event.entity.EntityJoinWorldEvent");
    public static ReflectorConstructor EntityJoinWorldEvent_Constructor = new ReflectorConstructor(EntityJoinWorldEvent, new Class[]{Entity.class, World.class});
    public static ReflectorClass Event = new ReflectorClass("net.minecraftforge.eventbus.api.Event");
    public static ReflectorMethod Event_isCanceled = new ReflectorMethod(Event, "isCanceled");
    public static ReflectorMethod Event_getResult = new ReflectorMethod(Event, "getResult");
    public static ReflectorClass EventBus = new ReflectorClass("net.minecraftforge.eventbus.api.IEventBus");
    public static ReflectorMethod EventBus_post = new ReflectorMethod(EventBus, "post", new Class[]{Event.class});
    public static ReflectorClass Event_Result = new ReflectorClass("net.minecraftforge.eventbus.api.Event$Result");
    public static ReflectorField Event_Result_DENY = new ReflectorField(Event_Result, "DENY");
    public static ReflectorField Event_Result_ALLOW = new ReflectorField(Event_Result, "ALLOW");
    public static ReflectorField Event_Result_DEFAULT = new ReflectorField(Event_Result, "DEFAULT");
    public static ReflectorClass IForgeBlock = new ReflectorClass("net.minecraftforge.common.extensions.IForgeBlock");
    public static ReflectorMethod IForgeBlock_getTags = new ReflectorMethod(IForgeBlock, "getTags");
    public static ReflectorClass ForgeBlockModelRenderer = new ReflectorClass("net.minecraftforge.client.model.pipeline.ForgeBlockModelRenderer");
    public static ReflectorConstructor ForgeBlockModelRenderer_Constructor = new ReflectorConstructor(ForgeBlockModelRenderer, new Class[]{BlockColors.class});
    public static ReflectorClass ForgeBlockModelShapes = new ReflectorClass(BlockModelShapes.class);
    public static ReflectorMethod ForgeBlockModelShapes_getTexture3 = new ReflectorMethod(ForgeBlockModelShapes, "getTexture", new Class[]{BlockState.class, World.class, BlockPos.class});
    public static ReflectorClass IForgeBlockState = new ReflectorClass("net.minecraftforge.common.extensions.IForgeBlockState");
    public static ReflectorMethod IForgeBlockState_addDestroyEffects = new ReflectorMethod(IForgeBlockState, "addDestroyEffects");
    public static ReflectorMethod IForgeBlockState_addHitEffects = new ReflectorMethod(IForgeBlockState, "addHitEffects");
    public static ReflectorMethod IForgeBlockState_getLightValue2 = new ReflectorMethod(IForgeBlockState, "getLightValue", new Class[]{IBlockReader.class, BlockPos.class});
    public static ReflectorMethod IForgeBlockState_getSoundType3 = new ReflectorMethod(IForgeBlockState, "getSoundType", new Class[]{IWorldReader.class, BlockPos.class, Entity.class});
    public static ReflectorMethod IForgeBlockState_getStateAtViewpoint = new ReflectorMethod(IForgeBlockState, "getStateAtViewpoint");
    public static ReflectorMethod IForgeBlockState_hasTileEntity = new ReflectorMethod(IForgeBlockState, "hasTileEntity");
    public static ReflectorMethod IForgeBlockState_isAir2 = new ReflectorMethod(IForgeBlockState, "isAir", new Class[]{IBlockReader.class, BlockPos.class});
    public static ReflectorMethod IForgeBlockState_shouldDisplayFluidOverlay = new ReflectorMethod(IForgeBlockState, "shouldDisplayFluidOverlay");
    public static ReflectorClass IForgeFluid = new ReflectorClass("net.minecraftforge.common.extensions.IForgeFluid");
    public static ReflectorMethod IForgeFluid_getAttributes = new ReflectorMethod(IForgeFluid, "getAttributes");
    public static ReflectorClass IForgeEntity = new ReflectorClass("net.minecraftforge.common.extensions.IForgeEntity");
    public static ReflectorMethod IForgeEntity_canUpdate = new ReflectorMethod(IForgeEntity, "canUpdate", new Class[0]);
    public static ReflectorMethod IForgeEntity_getParts = new ReflectorMethod(IForgeEntity, "getParts");
    public static ReflectorMethod IForgeEntity_isMultipartEntity = new ReflectorMethod(IForgeEntity, "isMultipartEntity");
    public static ReflectorMethod IForgeEntity_onAddedToWorld = new ReflectorMethod(IForgeEntity, "onAddedToWorld");
    public static ReflectorMethod IForgeEntity_onRemovedFromWorld = new ReflectorMethod(IForgeEntity, "onRemovedFromWorld");
    public static ReflectorMethod IForgeEntity_shouldRiderSit = new ReflectorMethod(IForgeEntity, "shouldRiderSit");
    public static ReflectorClass FluidAttributes = new ReflectorClass("net.minecraftforge.fluids.FluidAttributes");
    public static ReflectorMethod FluidAttributes_getColor = new ReflectorMethod(FluidAttributes, "getColor", new Class[]{IBlockDisplayReader.class, BlockPos.class});
    public static ReflectorClass ForgeEntityType = new ReflectorClass(EntityType.class);
    public static ReflectorMethod ForgeEntityType_getTags = new ReflectorMethod(ForgeEntityType, "getTags");
    public static ReflectorClass ForgeEventFactory = new ReflectorClass("net.minecraftforge.event.ForgeEventFactory");
    public static ReflectorMethod ForgeEventFactory_canEntityDespawn = new ReflectorMethod(ForgeEventFactory, "canEntityDespawn");
    public static ReflectorMethod ForgeEventFactory_fireChunkWatch = new ReflectorMethod(ForgeEventFactory, "fireChunkWatch", new Class[]{Boolean.TYPE, Boolean.TYPE, ServerPlayerEntity.class, ChunkPos.class, ServerWorld.class});
    public static ReflectorMethod ForgeEventFactory_getMaxSpawnPackSize = new ReflectorMethod(ForgeEventFactory, "getMaxSpawnPackSize");
    public static ReflectorMethod ForgeEventFactory_getMobGriefingEvent = new ReflectorMethod(ForgeEventFactory, "getMobGriefingEvent");
    public static ReflectorMethod ForgeEventFactory_onPlaySoundAtEntity = new ReflectorMethod(ForgeEventFactory, "onPlaySoundAtEntity");
    public static ReflectorMethod ForgeEventFactory_renderBlockOverlay = new ReflectorMethod(ForgeEventFactory, "renderBlockOverlay");
    public static ReflectorMethod ForgeEventFactory_renderFireOverlay = new ReflectorMethod(ForgeEventFactory, "renderFireOverlay");
    public static ReflectorMethod ForgeEventFactory_renderWaterOverlay = new ReflectorMethod(ForgeEventFactory, "renderWaterOverlay");
    public static ReflectorClass ForgeFluid = new ReflectorClass(Fluid.class);
    public static ReflectorMethod ForgeFluid_getTags = new ReflectorMethod(ForgeFluid, "getTags");
    public static ReflectorClass ForgeHooks = new ReflectorClass("net.minecraftforge.common.ForgeHooks");
    public static ReflectorMethod ForgeHooks_onDifficultyChange = new ReflectorMethod(ForgeHooks, "onDifficultyChange");
    public static ReflectorMethod ForgeHooks_onLivingAttack = new ReflectorMethod(ForgeHooks, "onLivingAttack");
    public static ReflectorMethod ForgeHooks_onLivingDeath = new ReflectorMethod(ForgeHooks, "onLivingDeath");
    public static ReflectorMethod ForgeHooks_onLivingDrops = new ReflectorMethod(ForgeHooks, "onLivingDrops");
    public static ReflectorMethod ForgeHooks_onLivingFall = new ReflectorMethod(ForgeHooks, "onLivingFall");
    public static ReflectorMethod ForgeHooks_onLivingHurt = new ReflectorMethod(ForgeHooks, "onLivingHurt");
    public static ReflectorMethod ForgeHooks_onLivingJump = new ReflectorMethod(ForgeHooks, "onLivingJump");
    public static ReflectorMethod ForgeHooks_onLivingSetAttackTarget = new ReflectorMethod(ForgeHooks, "onLivingSetAttackTarget");
    public static ReflectorMethod ForgeHooks_onLivingUpdate = new ReflectorMethod(ForgeHooks, "onLivingUpdate");
    public static ReflectorClass ForgeHooksClient = new ReflectorClass("net.minecraftforge.client.ForgeHooksClient");
    public static ReflectorMethod ForgeHooksClient_bossBarRenderPre = new ReflectorMethod(ForgeHooksClient, "bossBarRenderPre");
    public static ReflectorMethod ForgeHooksClient_bossBarRenderPost = new ReflectorMethod(ForgeHooksClient, "bossBarRenderPost");
    public static ReflectorMethod ForgeHooksClient_dispatchRenderLast = new ReflectorMethod(ForgeHooksClient, "dispatchRenderLast", new Class[]{WorldRenderer.class, MatrixStack.class, Float.TYPE, Matrix4f.class, Long.TYPE});
    public static ReflectorMethod ForgeHooksClient_drawItemLayered = new ReflectorMethod(ForgeHooksClient, "drawItemLayered");
    public static ReflectorMethod ForgeHooksClient_drawScreen = new ReflectorMethod(ForgeHooksClient, "drawScreen");
    public static ReflectorMethod ForgeHooksClient_fillNormal = new ReflectorMethod(ForgeHooksClient, "fillNormal");
    public static ReflectorMethod ForgeHooksClient_fireKeyInput = new ReflectorMethod(ForgeHooksClient, "fireKeyInput");
    public static ReflectorMethod ForgeHooksClient_handleCameraTransforms = new ReflectorMethod(ForgeHooksClient, "handleCameraTransforms");
    public static ReflectorMethod ForgeHooksClient_handlePerspective = new ReflectorMethod(ForgeHooksClient, "handlePerspective");
    public static ReflectorMethod ForgeHooksClient_gatherFluidTextures = new ReflectorMethod(ForgeHooksClient, "gatherFluidTextures");
    public static ReflectorMethod ForgeHooksClient_getArmorModel = new ReflectorMethod(ForgeHooksClient, "getArmorModel");
    public static ReflectorMethod ForgeHooksClient_getArmorTexture = new ReflectorMethod(ForgeHooksClient, "getArmorTexture");
    public static ReflectorMethod ForgeHooksClient_getFluidSprites = new ReflectorMethod(ForgeHooksClient, "getFluidSprites");
    public static ReflectorMethod ForgeHooksClient_getFogDensity = new ReflectorMethod(ForgeHooksClient, "getFogDensity");
    public static ReflectorMethod ForgeHooksClient_getFOVModifier = new ReflectorMethod(ForgeHooksClient, "getFOVModifier");
    public static ReflectorMethod ForgeHooksClient_getOffsetFOV = new ReflectorMethod(ForgeHooksClient, "getOffsetFOV");
    public static ReflectorMethod ForgeHooksClient_isNameplateInRenderDistance = new ReflectorMethod(ForgeHooksClient, "isNameplateInRenderDistance");
    public static ReflectorMethod ForgeHooksClient_loadEntityShader = new ReflectorMethod(ForgeHooksClient, "loadEntityShader");
    public static ReflectorMethod ForgeHooksClient_onCameraSetup = new ReflectorMethod(ForgeHooksClient, "onCameraSetup");
    public static ReflectorMethod ForgeHooksClient_onDrawBlockHighlight = new ReflectorMethod(ForgeHooksClient, "onDrawBlockHighlight");
    public static ReflectorMethod ForgeHooksClient_onFogRender = new ReflectorMethod(ForgeHooksClient, "onFogRender");
    public static ReflectorMethod ForgeHooksClient_onGuiCharTypedPre = new ReflectorMethod(ForgeHooksClient, "onGuiCharTypedPre");
    public static ReflectorMethod ForgeHooksClient_onGuiCharTypedPost = new ReflectorMethod(ForgeHooksClient, "onGuiCharTypedPost");
    public static ReflectorMethod ForgeHooksClient_onGuiKeyPressedPre = new ReflectorMethod(ForgeHooksClient, "onGuiKeyPressedPre");
    public static ReflectorMethod ForgeHooksClient_onGuiKeyPressedPost = new ReflectorMethod(ForgeHooksClient, "onGuiKeyPressedPost");
    public static ReflectorMethod ForgeHooksClient_onGuiKeyReleasedPre = new ReflectorMethod(ForgeHooksClient, "onGuiKeyReleasedPre");
    public static ReflectorMethod ForgeHooksClient_onGuiKeyReleasedPost = new ReflectorMethod(ForgeHooksClient, "onGuiKeyReleasedPost");
    public static ReflectorMethod ForgeHooksClient_onScreenshot = new ReflectorMethod(ForgeHooksClient, "onScreenshot");
    public static ReflectorMethod ForgeHooksClient_onTextureStitchedPre = new ReflectorMethod(ForgeHooksClient, "onTextureStitchedPre");
    public static ReflectorMethod ForgeHooksClient_onTextureStitchedPost = new ReflectorMethod(ForgeHooksClient, "onTextureStitchedPost");
    public static ReflectorMethod ForgeHooksClient_renderMainMenu = new ReflectorMethod(ForgeHooksClient, "renderMainMenu", new Class[]{MainMenuScreen.class, MatrixStack.class, FontRenderer.class, Integer.TYPE, Integer.TYPE, Integer.TYPE});
    public static ReflectorMethod ForgeHooksClient_renderSpecificFirstPersonHand = new ReflectorMethod(ForgeHooksClient, "renderSpecificFirstPersonHand");
    public static ReflectorMethod ForgeHooksClient_setRenderLayer = new ReflectorMethod(ForgeHooksClient, "setRenderLayer");
    public static ReflectorMethod ForgeHooksClient_shouldCauseReequipAnimation = new ReflectorMethod(ForgeHooksClient, "shouldCauseReequipAnimation");
    public static ReflectorClass ForgeConfig = new ReflectorClass("net.minecraftforge.common.ForgeConfig");
    public static ReflectorField ForgeConfig_CLIENT = new ReflectorField(ForgeConfig, "CLIENT");
    public static ReflectorClass ForgeConfig_Client = new ReflectorClass("net.minecraftforge.common.ForgeConfig$Client");
    public static ReflectorField ForgeConfig_Client_forgeLightPipelineEnabled = new ReflectorField(ForgeConfig_Client, "forgeLightPipelineEnabled");
    public static ReflectorField ForgeConfig_Client_useCombinedDepthStencilAttachment = new ReflectorField(ForgeConfig_Client, "useCombinedDepthStencilAttachment");
    public static ReflectorClass ForgeConfigSpec = new ReflectorClass("net.minecraftforge.common.ForgeConfigSpec");
    public static ReflectorField ForgeConfigSpec_childConfig = new ReflectorField(ForgeConfigSpec, "childConfig");
    public static ReflectorClass ForgeConfigSpec_ConfigValue = new ReflectorClass("net.minecraftforge.common.ForgeConfigSpec$ConfigValue");
    public static ReflectorField ForgeConfigSpec_ConfigValue_defaultSupplier = new ReflectorField(ForgeConfigSpec_ConfigValue, "defaultSupplier");
    public static ReflectorField ForgeConfigSpec_ConfigValue_spec = new ReflectorField(ForgeConfigSpec_ConfigValue, "spec");
    public static ReflectorMethod ForgeConfigSpec_ConfigValue_get = new ReflectorMethod(ForgeConfigSpec_ConfigValue, "get");
    public static ReflectorClass ForgeIChunk = new ReflectorClass(IChunk.class);
    public static ReflectorMethod ForgeIChunk_getWorldForge = new ReflectorMethod(ForgeIChunk, "getWorldForge");
    public static ReflectorClass IForgeItem = new ReflectorClass("net.minecraftforge.common.extensions.IForgeItem");
    public static ReflectorMethod IForgeItem_getDurabilityForDisplay = new ReflectorMethod(IForgeItem, "getDurabilityForDisplay");
    public static ReflectorMethod IForgeItem_getEquipmentSlot = new ReflectorMethod(IForgeItem, "getEquipmentSlot");
    public static ReflectorMethod IForgeItem_getFontRenderer = new ReflectorMethod(IForgeItem, "getFontRenderer");
    public static ReflectorMethod IForgeItem_getItemStackTileEntityRenderer = new ReflectorMethod(IForgeItem, "getItemStackTileEntityRenderer");
    public static ReflectorMethod IForgeItem_getRGBDurabilityForDisplay = new ReflectorMethod(IForgeItem, "getRGBDurabilityForDisplay");
    public static ReflectorMethod IForgeItem_isDamageable1 = new ReflectorMethod(IForgeItem, "isDamageable", new Class[]{ItemStack.class});
    public static ReflectorMethod IForgeItem_isShield = new ReflectorMethod(IForgeItem, "isShield");
    public static ReflectorMethod IForgeItem_onEntitySwing = new ReflectorMethod(IForgeItem, "onEntitySwing");
    public static ReflectorMethod IForgeItem_shouldCauseReequipAnimation = new ReflectorMethod(IForgeItem, "shouldCauseReequipAnimation");
    public static ReflectorMethod IForgeItem_showDurabilityBar = new ReflectorMethod(IForgeItem, "showDurabilityBar");
    public static ReflectorClass IForgeItemStack = new ReflectorClass("net.minecraftforge.common.extensions.IForgeItemStack");
    public static ReflectorMethod IForgeItemStack_canDisableShield = new ReflectorMethod(IForgeItemStack, "canDisableShield");
    public static ReflectorMethod IForgeItemStack_getEquipmentSlot = new ReflectorMethod(IForgeItemStack, "getEquipmentSlot");
    public static ReflectorMethod IForgeItemStack_getShareTag = new ReflectorMethod(IForgeItemStack, "getShareTag");
    public static ReflectorMethod IForgeItemStack_getHighlightTip = new ReflectorMethod(IForgeItemStack, "getHighlightTip");
    public static ReflectorMethod IForgeItemStack_isShield = new ReflectorMethod(IForgeItemStack, "isShield");
    public static ReflectorMethod IForgeItemStack_readShareTag = new ReflectorMethod(IForgeItemStack, "readShareTag");
    public static ReflectorClass ForgeItemTags = new ReflectorClass(ItemTags.class);
    public static ReflectorMethod ForgeItemTags_createOptional = ForgeItemTags.makeMethod("createOptional", new Class[]{ResourceLocation.class});
    public static ReflectorClass ForgeI18n = new ReflectorClass("net.minecraftforge.fml.ForgeI18n");
    public static ReflectorMethod ForgeI18n_loadLanguageData = new ReflectorMethod(ForgeI18n, "loadLanguageData");
    public static ReflectorClass ForgeKeyBinding = new ReflectorClass(KeyBinding.class);
    public static ReflectorMethod ForgeKeyBinding_setKeyConflictContext = new ReflectorMethod(ForgeKeyBinding, "setKeyConflictContext");
    public static ReflectorMethod ForgeKeyBinding_setKeyModifierAndCode = new ReflectorMethod(ForgeKeyBinding, "setKeyModifierAndCode");
    public static ReflectorMethod ForgeKeyBinding_getKeyModifier = new ReflectorMethod(ForgeKeyBinding, "getKeyModifier");
    public static ReflectorClass IForgeEffectInstance = new ReflectorClass("net.minecraftforge.common.extensions.IForgeEffectInstance");
    public static ReflectorMethod IForgeEffectInstance_isCurativeItem = new ReflectorMethod(IForgeEffectInstance, "isCurativeItem");
    public static ReflectorMethod IForgeEffectInstance_shouldRenderHUD = new ReflectorMethod(IForgeEffectInstance, "shouldRenderHUD");
    public static ReflectorMethod IForgeEffectInstance_renderHUDEffect = IForgeEffectInstance.makeMethod("renderHUDEffect");
    public static ReflectorClass ForgeRegistryEntry = new ReflectorClass("net.minecraftforge.registries.ForgeRegistryEntry");
    public static ReflectorMethod ForgeRegistryEntry_getRegistryName = new ReflectorMethod(ForgeRegistryEntry, "getRegistryName");
    public static ReflectorClass ForgeRenderTypeLookup = new ReflectorClass(RenderTypeLookup.class);
    public static ReflectorMethod ForgeRenderTypeLookup_canRenderInLayerBs = new ReflectorMethod(ForgeRenderTypeLookup, "canRenderInLayer", new Class[]{BlockState.class, RenderType.class});
    public static ReflectorMethod ForgeRenderTypeLookup_canRenderInLayerFs = new ReflectorMethod(ForgeRenderTypeLookup, "canRenderInLayer", new Class[]{FluidState.class, RenderType.class});
    public static ReflectorClass ForgeTicket = new ReflectorClass(Ticket.class);
    public static ReflectorConstructor ForgeTicket_Constructor = ForgeTicket.makeConstructor(new Class[]{TicketType.class, Integer.TYPE, Object.class, Boolean.TYPE});
    public static ReflectorMethod ForgeTicket_isForceTicks = ForgeTicket.makeMethod("isForceTicks");
    public static ReflectorClass IForgeTileEntity = new ReflectorClass("net.minecraftforge.common.extensions.IForgeTileEntity");
    public static ReflectorMethod IForgeTileEntity_getRenderBoundingBox = new ReflectorMethod(IForgeTileEntity, "getRenderBoundingBox");
    public static ReflectorField ForgeWorld_tileEntitiesToBeRemoved = new ReflectorField(new FieldLocatorTypes(World.class, new Class[]{List.class}, Set.class, new Class[]{Thread.class}, "World.tileEntitiesToBeRemoved"));
    public static ReflectorClass ForgeDimensionRenderInfo = new ReflectorClass(DimensionRenderInfo.class);
    public static ReflectorMethod ForgeDimensionRenderInfo_getCloudRenderHandler = ForgeDimensionRenderInfo.makeMethod("getCloudRenderHandler");
    public static ReflectorMethod ForgeDimensionRenderInfo_getSkyRenderHandler = ForgeDimensionRenderInfo.makeMethod("getSkyRenderHandler");
    public static ReflectorMethod ForgeDimensionRenderInfo_getWeatherParticleRenderHandler = ForgeDimensionRenderInfo.makeMethod("getWeatherParticleRenderHandler");
    public static ReflectorMethod ForgeDimensionRenderInfo_getWeatherRenderHandler = ForgeDimensionRenderInfo.makeMethod("getWeatherRenderHandler");
    public static ReflectorClass IRenderHandler = new ReflectorClass("net.minecraftforge.client.IRenderHandler");
    public static ReflectorMethod IRenderHandler_render = new ReflectorMethod(IRenderHandler, "render");
    public static ReflectorClass ItemModelMesherForge = new ReflectorClass("net.minecraftforge.client.ItemModelMesherForge");
    public static ReflectorConstructor ItemModelMesherForge_Constructor = new ReflectorConstructor(ItemModelMesherForge, new Class[]{ModelManager.class});
    public static ReflectorClass KeyConflictContext = new ReflectorClass("net.minecraftforge.client.settings.KeyConflictContext");
    public static ReflectorField KeyConflictContext_IN_GAME = new ReflectorField(KeyConflictContext, "IN_GAME");
    public static ReflectorClass KeyModifier = new ReflectorClass("net.minecraftforge.client.settings.KeyModifier");
    public static ReflectorMethod KeyModifier_valueFromString = new ReflectorMethod(KeyModifier, "valueFromString");
    public static ReflectorField KeyModifier_NONE = new ReflectorField(KeyModifier, "NONE");
    public static ReflectorClass Launch = new ReflectorClass("net.minecraft.launchwrapper.Launch");
    public static ReflectorField Launch_blackboard = new ReflectorField(Launch, "blackboard");
    public static ReflectorClass LightUtil = new ReflectorClass("net.minecraftforge.client.model.pipeline.LightUtil");
    public static ReflectorField LightUtil_itemConsumer = new ReflectorField(LightUtil, "itemConsumer");
    public static ReflectorField LightUtil_tessellator = new ReflectorField(LightUtil, "tessellator");
    public static ReflectorMethod LightUtil_putBakedQuad = new ReflectorMethod(LightUtil, "putBakedQuad");
    public static ReflectorClass Loader = new ReflectorClass("net.minecraftforge.fml.common.Loader");
    public static ReflectorMethod Loader_getActiveModList = new ReflectorMethod(Loader, "getActiveModList");
    public static ReflectorMethod Loader_instance = new ReflectorMethod(Loader, "instance");
    public static ReflectorClass MinecraftForge = new ReflectorClass("net.minecraftforge.common.MinecraftForge");
    public static ReflectorField MinecraftForge_EVENT_BUS = new ReflectorField(MinecraftForge, "EVENT_BUS");
    public static ReflectorClass MinecraftForgeClient = new ReflectorClass("net.minecraftforge.client.MinecraftForgeClient");
    public static ReflectorMethod MinecraftForgeClient_getImageLayer = new ReflectorMethod(MinecraftForgeClient, "getImageLayer");
    public static ReflectorClass ModContainer = new ReflectorClass("net.minecraftforge.fml.common.ModContainer");
    public static ReflectorMethod ModContainer_getModId = new ReflectorMethod(ModContainer, "getModId");
    public static ReflectorClass ModelLoaderRegistry = new ReflectorClass("net.minecraftforge.client.model.ModelLoaderRegistry");
    public static ReflectorMethod ModelLoaderRegistry_onModelLoadingStart = ModelLoaderRegistry.makeMethod("onModelLoadingStart");
    public static ReflectorClass ModListScreen = new ReflectorClass("net.minecraftforge.fml.client.gui.screen.ModListScreen");
    public static ReflectorConstructor ModListScreen_Constructor = new ReflectorConstructor(ModListScreen, new Class[]{Screen.class});
    public static ReflectorClass NotificationModUpdateScreen = new ReflectorClass("net.minecraftforge.client.gui.NotificationModUpdateScreen");
    public static ReflectorMethod NotificationModUpdateScreen_init = new ReflectorMethod(NotificationModUpdateScreen, "init", new Class[]{MainMenuScreen.class, Button.class});
    public static ReflectorClass PartEntity = new ReflectorClass("net.minecraftforge.entity.PartEntity");
    public static ReflectorClass PlaySoundAtEntityEvent = new ReflectorClass("net.minecraftforge.event.entity.PlaySoundAtEntityEvent");
    public static ReflectorMethod PlaySoundAtEntityEvent_getSound = new ReflectorMethod(PlaySoundAtEntityEvent, "getSound");
    public static ReflectorMethod PlaySoundAtEntityEvent_getCategory = new ReflectorMethod(PlaySoundAtEntityEvent, "getCategory");
    public static ReflectorMethod PlaySoundAtEntityEvent_getVolume = new ReflectorMethod(PlaySoundAtEntityEvent, "getVolume");
    public static ReflectorClass RenderBlockOverlayEvent_OverlayType = new ReflectorClass("net.minecraftforge.client.event.RenderBlockOverlayEvent$OverlayType");
    public static ReflectorField RenderBlockOverlayEvent_OverlayType_BLOCK = new ReflectorField(RenderBlockOverlayEvent_OverlayType, "BLOCK");
    public static ReflectorClass RenderingRegistry = new ReflectorClass("net.minecraftforge.fml.client.registry.RenderingRegistry");
    public static ReflectorMethod RenderingRegistry_loadEntityRenderers = new ReflectorMethod(RenderingRegistry, "loadEntityRenderers", new Class[]{EntityRendererManager.class});
    public static ReflectorClass RenderGameOverlayEvent_BossInfo = new ReflectorClass("net.minecraftforge.client.event.RenderGameOverlayEvent$BossInfo");
    public static ReflectorMethod RenderGameOverlayEvent_BossInfo_getIncrement = RenderGameOverlayEvent_BossInfo.makeMethod("getIncrement");
    public static ReflectorClass RenderItemInFrameEvent = new ReflectorClass("net.minecraftforge.client.event.RenderItemInFrameEvent");
    public static ReflectorConstructor RenderItemInFrameEvent_Constructor = new ReflectorConstructor(RenderItemInFrameEvent, new Class[]{ItemFrameEntity.class, ItemFrameRenderer.class, MatrixStack.class, IRenderTypeBuffer.class, Integer.TYPE});
    public static ReflectorClass RenderLivingEvent_Pre = new ReflectorClass("net.minecraftforge.client.event.RenderLivingEvent$Pre");
    public static ReflectorConstructor RenderLivingEvent_Pre_Constructor = new ReflectorConstructor(RenderLivingEvent_Pre, new Class[]{LivingEntity.class, LivingRenderer.class, Float.TYPE, MatrixStack.class, IRenderTypeBuffer.class, Integer.TYPE});
    public static ReflectorClass RenderLivingEvent_Post = new ReflectorClass("net.minecraftforge.client.event.RenderLivingEvent$Post");
    public static ReflectorConstructor RenderLivingEvent_Post_Constructor = new ReflectorConstructor(RenderLivingEvent_Post, new Class[]{LivingEntity.class, LivingRenderer.class, Float.TYPE, MatrixStack.class, IRenderTypeBuffer.class, Integer.TYPE});
    public static ReflectorClass RenderNameplateEvent = new ReflectorClass("net.minecraftforge.client.event.RenderNameplateEvent");
    public static ReflectorConstructor RenderNameplateEvent_Constructor = new ReflectorConstructor(RenderNameplateEvent, new Class[]{Entity.class, ITextComponent.class, EntityRenderer.class, MatrixStack.class, IRenderTypeBuffer.class, Integer.TYPE, Float.TYPE});
    public static ReflectorMethod RenderNameplateEvent_getContent = new ReflectorMethod(RenderNameplateEvent, "getContent");
    public static ReflectorClass ScreenshotEvent = new ReflectorClass("net.minecraftforge.client.event.ScreenshotEvent");
    public static ReflectorMethod ScreenshotEvent_getCancelMessage = new ReflectorMethod(ScreenshotEvent, "getCancelMessage");
    public static ReflectorMethod ScreenshotEvent_getScreenshotFile = new ReflectorMethod(ScreenshotEvent, "getScreenshotFile");
    public static ReflectorMethod ScreenshotEvent_getResultMessage = new ReflectorMethod(ScreenshotEvent, "getResultMessage");
    public static ReflectorClass ServerLifecycleHooks = new ReflectorClass("net.minecraftforge.fml.server.ServerLifecycleHooks");
    public static ReflectorMethod ServerLifecycleHooks_handleServerAboutToStart = new ReflectorMethod(ServerLifecycleHooks, "handleServerAboutToStart");
    public static ReflectorMethod ServerLifecycleHooks_handleServerStarting = new ReflectorMethod(ServerLifecycleHooks, "handleServerStarting");
    public static ReflectorClass WorldEvent_Load = new ReflectorClass("net.minecraftforge.event.world.WorldEvent$Load");
    public static ReflectorConstructor WorldEvent_Load_Constructor = new ReflectorConstructor(WorldEvent_Load, new Class[]{IWorld.class});
    private static boolean logVanilla = Reflector.registerResolvable("*** Reflector Vanilla ***");
    public static ReflectorClass EntityItem = new ReflectorClass(ItemEntity.class);
    public static ReflectorField EntityItem_ITEM = new ReflectorField(EntityItem, DataParameter.class);
    public static ReflectorClass EnderDragonRenderer = new ReflectorClass(EnderDragonRenderer.class);
    public static ReflectorField EnderDragonRenderer_model = new ReflectorField(EnderDragonRenderer, EnderDragonRenderer.EnderDragonModel.class);
    public static ReflectorClass GuiEnchantment = new ReflectorClass(EnchantmentScreen.class);
    public static ReflectorField GuiEnchantment_bookModel = new ReflectorField(GuiEnchantment, BookModel.class);
    public static ReflectorClass GuiMainMenu = new ReflectorClass(MainMenuScreen.class);
    public static ReflectorField GuiMainMenu_splashText = new ReflectorField(GuiMainMenu, String.class);
    public static ReflectorClass ItemOverride = new ReflectorClass(ItemOverride.class);
    public static ReflectorField ItemOverride_mapResourceValues = new ReflectorField(ItemOverride, Map.class);
    public static ReflectorClass LegacyResourcePackWrapper = new ReflectorClass(LegacyResourcePackWrapper.class);
    public static ReflectorField LegacyResourcePackWrapper_pack = new ReflectorField(LegacyResourcePackWrapper, IResourcePack.class);
    public static ReflectorClass LegacyResourcePackWrapperV4 = new ReflectorClass(LegacyResourcePackWrapperV4.class);
    public static ReflectorField LegacyResourcePackWrapperV4_pack = new ReflectorField(LegacyResourcePackWrapperV4, IResourcePack.class);
    public static ReflectorClass LayerLlamaDecor = new ReflectorClass(LlamaDecorLayer.class);
    public static ReflectorField LayerLlamaDecor_model = new ReflectorField(LayerLlamaDecor, LlamaModel.class);
    public static ReflectorClass Minecraft = new ReflectorClass(Minecraft.class);
    public static ReflectorField Minecraft_debugFPS = new ReflectorField(new FieldLocatorTypes(Minecraft.class, new Class[]{CrashReport.class}, Integer.TYPE, new Class[]{String.class}, "debugFPS"));
    public static ReflectorField Minecraft_fontResourceManager = new ReflectorField(Minecraft, FontResourceManager.class);
    public static ReflectorClass ModelHumanoidHead = new ReflectorClass(HumanoidHeadModel.class);
    public static ReflectorField ModelHumanoidHead_head = new ReflectorField(ModelHumanoidHead, ModelRenderer.class);
    public static ReflectorClass ModelArmorStand = new ReflectorClass(ArmorStandModel.class);
    public static ReflectorFields ModelArmorStand_ModelRenderers = new ReflectorFields(ModelArmorStand, ModelRenderer.class, 4);
    public static ReflectorClass ModelBat = new ReflectorClass(BatModel.class);
    public static ReflectorFields ModelBat_ModelRenderers = new ReflectorFields(ModelBat, ModelRenderer.class, 6);
    public static ReflectorClass ModelBee = new ReflectorClass(BeeModel.class);
    public static ReflectorFields ModelBee_ModelRenderers = new ReflectorFields(ModelBee, ModelRenderer.class, 10);
    public static ReflectorClass ModelBlaze = new ReflectorClass(BlazeModel.class);
    public static ReflectorField ModelBlaze_blazeHead = new ReflectorField(ModelBlaze, ModelRenderer.class);
    public static ReflectorField ModelBlaze_blazeSticks = new ReflectorField(ModelBlaze, ModelRenderer[].class);
    public static ReflectorClass ModelBoar = new ReflectorClass(BoarModel.class);
    public static ReflectorFields ModelBoar_ModelRenderers = new ReflectorFields(ModelBoar, ModelRenderer.class, 9);
    public static ReflectorClass ModelBook = new ReflectorClass(BookModel.class);
    public static ReflectorFields ModelBook_ModelRenderers = new ReflectorFields(ModelBook, ModelRenderer.class, 7);
    public static ReflectorField ModelBook_bookParts = new ReflectorField(ModelBook, List.class);
    public static ReflectorClass ModelChicken = new ReflectorClass(ChickenModel.class);
    public static ReflectorFields ModelChicken_ModelRenderers = new ReflectorFields(ModelChicken, ModelRenderer.class, 8);
    public static ReflectorClass ModelCod = new ReflectorClass(CodModel.class);
    public static ReflectorFields ModelCod_ModelRenderers = new ReflectorFields(ModelCod, ModelRenderer.class, 7);
    public static ReflectorClass ModelCreeper = new ReflectorClass(CreeperModel.class);
    public static ReflectorFields ModelCreeper_ModelRenderers = new ReflectorFields(ModelCreeper, ModelRenderer.class, 7);
    public static ReflectorClass ModelDragon = new ReflectorClass(EnderDragonRenderer.EnderDragonModel.class);
    public static ReflectorFields ModelDragon_ModelRenderers = new ReflectorFields(ModelDragon, ModelRenderer.class, 20);
    public static ReflectorClass RenderEnderCrystal = new ReflectorClass(EnderCrystalRenderer.class);
    public static ReflectorFields RenderEnderCrystal_modelRenderers = new ReflectorFields(RenderEnderCrystal, ModelRenderer.class, 3);
    public static ReflectorClass ModelEnderMite = new ReflectorClass(EndermiteModel.class);
    public static ReflectorField ModelEnderMite_bodyParts = new ReflectorField(ModelEnderMite, ModelRenderer[].class);
    public static ReflectorClass ModelEvokerFangs = new ReflectorClass(EvokerFangsModel.class);
    public static ReflectorFields ModelEvokerFangs_ModelRenderers = new ReflectorFields(ModelEvokerFangs, ModelRenderer.class, 3);
    public static ReflectorClass ModelGuardian = new ReflectorClass(GuardianModel.class);
    public static ReflectorField ModelGuardian_body = new ReflectorField(ModelGuardian, ModelRenderer.class, 0);
    public static ReflectorField ModelGuardian_eye = new ReflectorField(ModelGuardian, ModelRenderer.class, 1);
    public static ReflectorField ModelGuardian_spines = new ReflectorField(ModelGuardian, ModelRenderer[].class, 0);
    public static ReflectorField ModelGuardian_tail = new ReflectorField(ModelGuardian, ModelRenderer[].class, 1);
    public static ReflectorClass ModelDragonHead = new ReflectorClass(DragonHeadModel.class);
    public static ReflectorField ModelDragonHead_head = new ReflectorField(ModelDragonHead, ModelRenderer.class, 0);
    public static ReflectorField ModelDragonHead_jaw = new ReflectorField(ModelDragonHead, ModelRenderer.class, 1);
    public static ReflectorClass ModelHorse = new ReflectorClass(HorseModel.class);
    public static ReflectorFields ModelHorse_ModelRenderers = new ReflectorFields(ModelHorse, ModelRenderer.class, 11);
    public static ReflectorClass ModelHorseChests = new ReflectorClass(HorseArmorChestsModel.class);
    public static ReflectorFields ModelHorseChests_ModelRenderers = new ReflectorFields(ModelHorseChests, ModelRenderer.class, 2);
    public static ReflectorClass ModelIllager = new ReflectorClass(IllagerModel.class);
    public static ReflectorFields ModelIllager_ModelRenderers = new ReflectorFields(ModelIllager, ModelRenderer.class, 8);
    public static ReflectorClass ModelIronGolem = new ReflectorClass(IronGolemModel.class);
    public static ReflectorFields ModelIronGolem_ModelRenderers = new ReflectorFields(ModelIronGolem, ModelRenderer.class, 6);
    public static ReflectorClass ModelFox = new ReflectorClass(FoxModel.class);
    public static ReflectorFields ModelFox_ModelRenderers = new ReflectorFields(ModelFox, ModelRenderer.class, 10);
    public static ReflectorClass ModelLeashKnot = new ReflectorClass(LeashKnotModel.class);
    public static ReflectorField ModelLeashKnot_knotRenderer = new ReflectorField(ModelLeashKnot, ModelRenderer.class);
    public static ReflectorClass RenderLeashKnot = new ReflectorClass(LeashKnotRenderer.class);
    public static ReflectorField RenderLeashKnot_leashKnotModel = new ReflectorField(RenderLeashKnot, LeashKnotModel.class);
    public static ReflectorClass ModelLlama = new ReflectorClass(LlamaModel.class);
    public static ReflectorFields ModelLlama_ModelRenderers = new ReflectorFields(ModelLlama, ModelRenderer.class, 8);
    public static ReflectorClass ModelLlamaSpit = new ReflectorClass(LlamaSpitModel.class);
    public static ReflectorField ModelLlamaSpit_renderer = new ReflectorField(ModelLlamaSpit, ModelRenderer.class);
    public static ReflectorClass ModelMinecart = new ReflectorClass(MinecartModel.class);
    public static ReflectorField ModelMinecart_sideModels = new ReflectorField(ModelMinecart, ModelRenderer[].class);
    public static ReflectorClass ModelMagmaCube = new ReflectorClass(MagmaCubeModel.class);
    public static ReflectorField ModelMagmaCube_core = new ReflectorField(ModelMagmaCube, ModelRenderer.class);
    public static ReflectorField ModelMagmaCube_segments = new ReflectorField(ModelMagmaCube, ModelRenderer[].class);
    public static ReflectorClass ModelOcelot = new ReflectorClass(OcelotModel.class);
    public static ReflectorFields ModelOcelot_ModelRenderers = new ReflectorFields(ModelOcelot, ModelRenderer.class, 8);
    public static ReflectorClass ModelPhantom = new ReflectorClass(PhantomModel.class);
    public static ReflectorFields ModelPhantom_ModelRenderers = new ReflectorFields(ModelPhantom, ModelRenderer.class, 7);
    public static ReflectorClass ModelParrot = new ReflectorClass(ParrotModel.class);
    public static ReflectorFields ModelParrot_ModelRenderers = new ReflectorFields(ModelParrot, ModelRenderer.class, 11);
    public static ReflectorClass ModelPufferFishBig = new ReflectorClass(PufferFishBigModel.class);
    public static ReflectorFields ModelPufferFishBig_ModelRenderers = new ReflectorFields(ModelPufferFishBig, ModelRenderer.class, 13);
    public static ReflectorClass ModelPufferFishMedium = new ReflectorClass(PufferFishMediumModel.class);
    public static ReflectorFields ModelPufferFishMedium_ModelRenderers = new ReflectorFields(ModelPufferFishMedium, ModelRenderer.class, 11);
    public static ReflectorClass ModelPufferFishSmall = new ReflectorClass(PufferFishSmallModel.class);
    public static ReflectorFields ModelPufferFishSmall_ModelRenderers = new ReflectorFields(ModelPufferFishSmall, ModelRenderer.class, 6);
    public static ReflectorClass ModelQuadruped = new ReflectorClass(QuadrupedModel.class);
    public static ReflectorFields ModelQuadruped_ModelRenderers = new ReflectorFields(ModelQuadruped, ModelRenderer.class, 6);
    public static ReflectorClass ModelRabbit = new ReflectorClass(RabbitModel.class);
    public static ReflectorFields ModelRabbit_ModelRenderers = new ReflectorFields(ModelRabbit, ModelRenderer.class, 12);
    public static ReflectorClass ModelRavager = new ReflectorClass(RavagerModel.class);
    public static ReflectorFields ModelRavager_ModelRenderers = new ReflectorFields(ModelRavager, ModelRenderer.class, 8);
    public static ReflectorClass ModelSalmon = new ReflectorClass(SalmonModel.class);
    public static ReflectorFields ModelSalmon_ModelRenderers = new ReflectorFields(ModelSalmon, ModelRenderer.class, 5);
    public static ReflectorClass ModelShulker = new ReflectorClass(ShulkerModel.class);
    public static ReflectorFields ModelShulker_ModelRenderers = new ReflectorFields(ModelShulker, ModelRenderer.class, 3);
    public static ReflectorClass ModelShulkerBullet = new ReflectorClass(ShulkerBulletModel.class);
    public static ReflectorField ModelShulkerBullet_renderer = new ReflectorField(ModelShulkerBullet, ModelRenderer.class);
    public static ReflectorClass ModelSign = new ReflectorClass(SignTileEntityRenderer.SignModel.class);
    public static ReflectorFields ModelSign_ModelRenderers = new ReflectorFields(ModelSign, ModelRenderer.class, 2);
    public static ReflectorClass ModelGenericHead = new ReflectorClass(GenericHeadModel.class);
    public static ReflectorField ModelGenericHead_skeletonHead = new ReflectorField(ModelGenericHead, ModelRenderer.class);
    public static ReflectorClass ModelSilverfish = new ReflectorClass(SilverfishModel.class);
    public static ReflectorField ModelSilverfish_bodyParts = new ReflectorField(ModelSilverfish, ModelRenderer[].class, 0);
    public static ReflectorField ModelSilverfish_wingParts = new ReflectorField(ModelSilverfish, ModelRenderer[].class, 1);
    public static ReflectorClass ModelSlime = new ReflectorClass(SlimeModel.class);
    public static ReflectorFields ModelSlime_ModelRenderers = new ReflectorFields(ModelSlime, ModelRenderer.class, 4);
    public static ReflectorClass ModelSnowman = new ReflectorClass(SnowManModel.class);
    public static ReflectorFields ModelSnowman_ModelRenderers = new ReflectorFields(ModelSnowman, ModelRenderer.class, 5);
    public static ReflectorClass ModelSpider = new ReflectorClass(SpiderModel.class);
    public static ReflectorFields ModelSpider_ModelRenderers = new ReflectorFields(ModelSpider, ModelRenderer.class, 11);
    public static ReflectorClass ModelSquid = new ReflectorClass(SquidModel.class);
    public static ReflectorField ModelSquid_body = new ReflectorField(ModelSquid, ModelRenderer.class);
    public static ReflectorField ModelSquid_tentacles = new ReflectorField(ModelSquid, ModelRenderer[].class);
    public static ReflectorClass ModelStrider = new ReflectorClass(StriderModel.class);
    public static ReflectorFields ModelStrider_ModelRenderers = new ReflectorFields(ModelStrider, ModelRenderer.class, 9);
    public static ReflectorClass ModelTrident = new ReflectorClass(TridentModel.class);
    public static ReflectorField ModelTrident_tridentRenderer = new ReflectorField(ModelTrident, ModelRenderer.class);
    public static ReflectorClass ModelTropicalFishA = new ReflectorClass(TropicalFishAModel.class);
    public static ReflectorFields ModelTropicalFishA_ModelRenderers = new ReflectorFields(ModelTropicalFishA, ModelRenderer.class, 5);
    public static ReflectorClass ModelTropicalFishB = new ReflectorClass(TropicalFishBModel.class);
    public static ReflectorFields ModelTropicalFishB_ModelRenderers = new ReflectorFields(ModelTropicalFishB, ModelRenderer.class, 6);
    public static ReflectorClass ModelTurtle = new ReflectorClass(TurtleModel.class);
    public static ReflectorField ModelTurtle_body2 = new ReflectorField(ModelTurtle, ModelRenderer.class, 0);
    public static ReflectorClass ModelVex = new ReflectorClass(VexModel.class);
    public static ReflectorField ModelVex_leftWing = new ReflectorField(ModelVex, ModelRenderer.class, 0);
    public static ReflectorField ModelVex_rightWing = new ReflectorField(ModelVex, ModelRenderer.class, 1);
    public static ReflectorClass ModelVillager = new ReflectorClass(VillagerModel.class);
    public static ReflectorFields ModelVillager_ModelRenderers = new ReflectorFields(ModelVillager, ModelRenderer.class, 9);
    public static ReflectorClass ModelWitch = new ReflectorClass(WitchModel.class);
    public static ReflectorField ModelWitch_mole = new ReflectorField(ModelWitch, ModelRenderer.class, 0);
    public static ReflectorClass ModelWither = new ReflectorClass(WitherModel.class);
    public static ReflectorField ModelWither_bodyParts = new ReflectorField(ModelWither, ModelRenderer[].class, 0);
    public static ReflectorField ModelWither_heads = new ReflectorField(ModelWither, ModelRenderer[].class, 1);
    public static ReflectorClass ModelWolf = new ReflectorClass(WolfModel.class);
    public static ReflectorFields ModelWolf_ModelRenderers = new ReflectorFields(ModelWolf, ModelRenderer.class, 10);
    public static ReflectorClass OptiFineResourceLocator = ReflectorForge.getReflectorClassOptiFineResourceLocator();
    public static ReflectorMethod OptiFineResourceLocator_getOptiFineResourceStream = new ReflectorMethod(OptiFineResourceLocator, "getOptiFineResourceStream");
    public static ReflectorClass RenderBoat = new ReflectorClass(BoatRenderer.class);
    public static ReflectorField RenderBoat_modelBoat = new ReflectorField(RenderBoat, BoatModel.class);
    public static ReflectorClass RenderEvokerFangs = new ReflectorClass(EvokerFangsRenderer.class);
    public static ReflectorField RenderEvokerFangs_model = new ReflectorField(RenderEvokerFangs, EvokerFangsModel.class);
    public static ReflectorClass RenderLlamaSpit = new ReflectorClass(LlamaSpitRenderer.class);
    public static ReflectorField RenderLlamaSpit_model = new ReflectorField(RenderLlamaSpit, LlamaSpitModel.class);
    public static ReflectorClass RenderPufferfish = new ReflectorClass(PufferfishRenderer.class);
    public static ReflectorField RenderPufferfish_modelSmall = new ReflectorField(RenderPufferfish, PufferFishSmallModel.class);
    public static ReflectorField RenderPufferfish_modelMedium = new ReflectorField(RenderPufferfish, PufferFishMediumModel.class);
    public static ReflectorField RenderPufferfish_modelBig = new ReflectorField(RenderPufferfish, PufferFishBigModel.class);
    public static ReflectorClass RenderMinecart = new ReflectorClass(MinecartRenderer.class);
    public static ReflectorField RenderMinecart_modelMinecart = new ReflectorField(RenderMinecart, EntityModel.class);
    public static ReflectorClass RenderShulkerBullet = new ReflectorClass(ShulkerBulletRenderer.class);
    public static ReflectorField RenderShulkerBullet_model = new ReflectorField(RenderShulkerBullet, ShulkerBulletModel.class);
    public static ReflectorClass RenderTrident = new ReflectorClass(TridentRenderer.class);
    public static ReflectorField RenderTrident_modelTrident = new ReflectorField(RenderTrident, TridentModel.class);
    public static ReflectorClass RenderTropicalFish = new ReflectorClass(TropicalFishRenderer.class);
    public static ReflectorField RenderTropicalFish_modelA = new ReflectorField(RenderTropicalFish, TropicalFishAModel.class);
    public static ReflectorField RenderTropicalFish_modelB = new ReflectorField(RenderTropicalFish, TropicalFishBModel.class);
    public static ReflectorClass RenderWitherSkull = new ReflectorClass(WitherSkullRenderer.class);
    public static ReflectorField RenderWitherSkull_model = new ReflectorField(RenderWitherSkull, GenericHeadModel.class);
    public static ReflectorClass TileEntityBannerRenderer = new ReflectorClass(BannerTileEntityRenderer.class);
    public static ReflectorFields TileEntityBannerRenderer_modelRenderers = new ReflectorFields(TileEntityBannerRenderer, ModelRenderer.class, 3);
    public static ReflectorClass TileEntityBedRenderer = new ReflectorClass(BedTileEntityRenderer.class);
    public static ReflectorField TileEntityBedRenderer_headModel = new ReflectorField(TileEntityBedRenderer, ModelRenderer.class, 0);
    public static ReflectorField TileEntityBedRenderer_footModel = new ReflectorField(TileEntityBedRenderer, ModelRenderer.class, 1);
    public static ReflectorField TileEntityBedRenderer_legModels = new ReflectorField(TileEntityBedRenderer, ModelRenderer[].class);
    public static ReflectorClass TileEntityBellRenderer = new ReflectorClass(BellTileEntityRenderer.class);
    public static ReflectorField TileEntityBellRenderer_modelRenderer = new ReflectorField(TileEntityBellRenderer, ModelRenderer.class);
    public static ReflectorClass TileEntityBeacon = new ReflectorClass(BeaconTileEntity.class);
    public static ReflectorField TileEntityBeacon_customName = new ReflectorField(TileEntityBeacon, ITextComponent.class);
    public static ReflectorClass TileEntityChestRenderer = new ReflectorClass(ChestTileEntityRenderer.class);
    public static ReflectorFields TileEntityChestRenderer_modelRenderers = new ReflectorFields(TileEntityChestRenderer, ModelRenderer.class, 9);
    public static ReflectorClass TileEntityConduitRenderer = new ReflectorClass(ConduitTileEntityRenderer.class);
    public static ReflectorFields TileEntityConduitRenderer_modelRenderers = new ReflectorFields(TileEntityConduitRenderer, ModelRenderer.class, 4);
    public static ReflectorClass TileEntityEnchantmentTableRenderer = new ReflectorClass(EnchantmentTableTileEntityRenderer.class);
    public static ReflectorField TileEntityEnchantmentTableRenderer_modelBook = new ReflectorField(TileEntityEnchantmentTableRenderer, BookModel.class);
    public static ReflectorClass TileEntityLecternRenderer = new ReflectorClass(LecternTileEntityRenderer.class);
    public static ReflectorField TileEntityLecternRenderer_modelBook = new ReflectorField(TileEntityLecternRenderer, BookModel.class);
    public static ReflectorClass TileEntityShulkerBoxRenderer = new ReflectorClass(ShulkerBoxTileEntityRenderer.class);
    public static ReflectorField TileEntityShulkerBoxRenderer_model = new ReflectorField(TileEntityShulkerBoxRenderer, ShulkerModel.class);
    public static ReflectorClass TileEntitySignRenderer = new ReflectorClass(SignTileEntityRenderer.class);
    public static ReflectorField TileEntitySignRenderer_model = new ReflectorField(TileEntitySignRenderer, SignTileEntityRenderer.SignModel.class);
    public static ReflectorClass TileEntitySkullRenderer = new ReflectorClass(SkullTileEntityRenderer.class);
    public static ReflectorField TileEntitySkullRenderer_MODELS = new ReflectorField(TileEntitySkullRenderer, Map.class, 0);

    public static void callVoid(ReflectorMethod reflectorMethod, Object ... objectArray) {
        try {
            Method method = reflectorMethod.getTargetMethod();
            if (method == null) {
                return;
            }
            method.invoke(null, objectArray);
        } catch (Throwable throwable) {
            Reflector.handleException(throwable, null, reflectorMethod, objectArray);
        }
    }

    public static boolean callBoolean(ReflectorMethod reflectorMethod, Object ... objectArray) {
        try {
            Method method = reflectorMethod.getTargetMethod();
            if (method == null) {
                return false;
            }
            Boolean bl = (Boolean)method.invoke(null, objectArray);
            return bl;
        } catch (Throwable throwable) {
            Reflector.handleException(throwable, null, reflectorMethod, objectArray);
            return true;
        }
    }

    public static int callInt(ReflectorMethod reflectorMethod, Object ... objectArray) {
        try {
            Method method = reflectorMethod.getTargetMethod();
            if (method == null) {
                return 0;
            }
            Integer n = (Integer)method.invoke(null, objectArray);
            return n;
        } catch (Throwable throwable) {
            Reflector.handleException(throwable, null, reflectorMethod, objectArray);
            return 1;
        }
    }

    public static long callLong(ReflectorMethod reflectorMethod, Object ... objectArray) {
        try {
            Method method = reflectorMethod.getTargetMethod();
            if (method == null) {
                return 0L;
            }
            Long l = (Long)method.invoke(null, objectArray);
            return l;
        } catch (Throwable throwable) {
            Reflector.handleException(throwable, null, reflectorMethod, objectArray);
            return 0L;
        }
    }

    public static float callFloat(ReflectorMethod reflectorMethod, Object ... objectArray) {
        try {
            Method method = reflectorMethod.getTargetMethod();
            if (method == null) {
                return 0.0f;
            }
            Float f = (Float)method.invoke(null, objectArray);
            return f.floatValue();
        } catch (Throwable throwable) {
            Reflector.handleException(throwable, null, reflectorMethod, objectArray);
            return 0.0f;
        }
    }

    public static double callDouble(ReflectorMethod reflectorMethod, Object ... objectArray) {
        try {
            Method method = reflectorMethod.getTargetMethod();
            if (method == null) {
                return 0.0;
            }
            Double d = (Double)method.invoke(null, objectArray);
            return d;
        } catch (Throwable throwable) {
            Reflector.handleException(throwable, null, reflectorMethod, objectArray);
            return 0.0;
        }
    }

    public static String callString(ReflectorMethod reflectorMethod, Object ... objectArray) {
        try {
            Method method = reflectorMethod.getTargetMethod();
            return method == null ? null : (String)method.invoke(null, objectArray);
        } catch (Throwable throwable) {
            Reflector.handleException(throwable, null, reflectorMethod, objectArray);
            return null;
        }
    }

    public static Object call(ReflectorMethod reflectorMethod, Object ... objectArray) {
        try {
            Method method = reflectorMethod.getTargetMethod();
            return method == null ? null : method.invoke(null, objectArray);
        } catch (Throwable throwable) {
            Reflector.handleException(throwable, null, reflectorMethod, objectArray);
            return null;
        }
    }

    public static void callVoid(Object object, ReflectorMethod reflectorMethod, Object ... objectArray) {
        try {
            if (object == null) {
                return;
            }
            Method method = reflectorMethod.getTargetMethod();
            if (method == null) {
                return;
            }
            method.invoke(object, objectArray);
        } catch (Throwable throwable) {
            Reflector.handleException(throwable, object, reflectorMethod, objectArray);
        }
    }

    public static boolean callBoolean(Object object, ReflectorMethod reflectorMethod, Object ... objectArray) {
        try {
            Method method = reflectorMethod.getTargetMethod();
            if (method == null) {
                return false;
            }
            Boolean bl = (Boolean)method.invoke(object, objectArray);
            return bl;
        } catch (Throwable throwable) {
            Reflector.handleException(throwable, object, reflectorMethod, objectArray);
            return true;
        }
    }

    public static int callInt(Object object, ReflectorMethod reflectorMethod, Object ... objectArray) {
        try {
            Method method = reflectorMethod.getTargetMethod();
            if (method == null) {
                return 0;
            }
            Integer n = (Integer)method.invoke(object, objectArray);
            return n;
        } catch (Throwable throwable) {
            Reflector.handleException(throwable, object, reflectorMethod, objectArray);
            return 1;
        }
    }

    public static long callLong(Object object, ReflectorMethod reflectorMethod, Object ... objectArray) {
        try {
            Method method = reflectorMethod.getTargetMethod();
            if (method == null) {
                return 0L;
            }
            Long l = (Long)method.invoke(object, objectArray);
            return l;
        } catch (Throwable throwable) {
            Reflector.handleException(throwable, object, reflectorMethod, objectArray);
            return 0L;
        }
    }

    public static float callFloat(Object object, ReflectorMethod reflectorMethod, Object ... objectArray) {
        try {
            Method method = reflectorMethod.getTargetMethod();
            if (method == null) {
                return 0.0f;
            }
            Float f = (Float)method.invoke(object, objectArray);
            return f.floatValue();
        } catch (Throwable throwable) {
            Reflector.handleException(throwable, object, reflectorMethod, objectArray);
            return 0.0f;
        }
    }

    public static double callDouble(Object object, ReflectorMethod reflectorMethod, Object ... objectArray) {
        try {
            Method method = reflectorMethod.getTargetMethod();
            if (method == null) {
                return 0.0;
            }
            Double d = (Double)method.invoke(object, objectArray);
            return d;
        } catch (Throwable throwable) {
            Reflector.handleException(throwable, object, reflectorMethod, objectArray);
            return 0.0;
        }
    }

    public static String callString(Object object, ReflectorMethod reflectorMethod, Object ... objectArray) {
        try {
            Method method = reflectorMethod.getTargetMethod();
            return method == null ? null : (String)method.invoke(object, objectArray);
        } catch (Throwable throwable) {
            Reflector.handleException(throwable, object, reflectorMethod, objectArray);
            return null;
        }
    }

    public static Object call(Object object, ReflectorMethod reflectorMethod, Object ... objectArray) {
        try {
            Method method = reflectorMethod.getTargetMethod();
            return method == null ? null : method.invoke(object, objectArray);
        } catch (Throwable throwable) {
            Reflector.handleException(throwable, object, reflectorMethod, objectArray);
            return null;
        }
    }

    public static Object getFieldValue(ReflectorField reflectorField) {
        return Reflector.getFieldValue(null, reflectorField);
    }

    public static Object getFieldValue(Object object, ReflectorField reflectorField) {
        try {
            Field field = reflectorField.getTargetField();
            return field == null ? null : field.get(object);
        } catch (Throwable throwable) {
            Log.error("", throwable);
            return null;
        }
    }

    public static boolean getFieldValueBoolean(Object object, ReflectorField reflectorField, boolean bl) {
        try {
            Field field = reflectorField.getTargetField();
            return field == null ? bl : field.getBoolean(object);
        } catch (Throwable throwable) {
            Log.error("", throwable);
            return bl;
        }
    }

    public static Object getFieldValue(ReflectorFields reflectorFields, int n) {
        ReflectorField reflectorField = reflectorFields.getReflectorField(n);
        return reflectorField == null ? null : Reflector.getFieldValue(reflectorField);
    }

    public static Object getFieldValue(Object object, ReflectorFields reflectorFields, int n) {
        ReflectorField reflectorField = reflectorFields.getReflectorField(n);
        return reflectorField == null ? null : Reflector.getFieldValue(object, reflectorField);
    }

    public static float getFieldValueFloat(Object object, ReflectorField reflectorField, float f) {
        try {
            Field field = reflectorField.getTargetField();
            return field == null ? f : field.getFloat(object);
        } catch (Throwable throwable) {
            Log.error("", throwable);
            return f;
        }
    }

    public static int getFieldValueInt(ReflectorField reflectorField, int n) {
        return Reflector.getFieldValueInt(null, reflectorField, n);
    }

    public static int getFieldValueInt(Object object, ReflectorField reflectorField, int n) {
        try {
            Field field = reflectorField.getTargetField();
            return field == null ? n : field.getInt(object);
        } catch (Throwable throwable) {
            Log.error("", throwable);
            return n;
        }
    }

    public static long getFieldValueLong(Object object, ReflectorField reflectorField, long l) {
        try {
            Field field = reflectorField.getTargetField();
            return field == null ? l : field.getLong(object);
        } catch (Throwable throwable) {
            Log.error("", throwable);
            return l;
        }
    }

    public static boolean setFieldValue(ReflectorField reflectorField, Object object) {
        return Reflector.setFieldValue(null, reflectorField, object);
    }

    public static boolean setFieldValue(Object object, ReflectorFields reflectorFields, int n, Object object2) {
        ReflectorField reflectorField = reflectorFields.getReflectorField(n);
        if (reflectorField == null) {
            return true;
        }
        Reflector.setFieldValue(object, reflectorField, object2);
        return false;
    }

    public static boolean setFieldValue(Object object, ReflectorField reflectorField, Object object2) {
        try {
            Field field = reflectorField.getTargetField();
            if (field == null) {
                return false;
            }
            field.set(object, object2);
            return true;
        } catch (Throwable throwable) {
            Log.error("", throwable);
            return true;
        }
    }

    public static boolean setFieldValueInt(ReflectorField reflectorField, int n) {
        return Reflector.setFieldValueInt(null, reflectorField, n);
    }

    public static boolean setFieldValueInt(Object object, ReflectorField reflectorField, int n) {
        try {
            Field field = reflectorField.getTargetField();
            if (field == null) {
                return false;
            }
            field.setInt(object, n);
            return true;
        } catch (Throwable throwable) {
            Log.error("", throwable);
            return true;
        }
    }

    public static boolean postForgeBusEvent(ReflectorConstructor reflectorConstructor, Object ... objectArray) {
        Object object = Reflector.newInstance(reflectorConstructor, objectArray);
        return object == null ? false : Reflector.postForgeBusEvent(object);
    }

    public static boolean postForgeBusEvent(Object object) {
        if (object == null) {
            return true;
        }
        Object object2 = Reflector.getFieldValue(MinecraftForge_EVENT_BUS);
        if (object2 == null) {
            return true;
        }
        Object object3 = Reflector.call(object2, EventBus_post, object);
        if (!(object3 instanceof Boolean)) {
            return true;
        }
        Boolean bl = (Boolean)object3;
        return bl;
    }

    public static Object newInstance(ReflectorConstructor reflectorConstructor, Object ... objectArray) {
        Constructor constructor = reflectorConstructor.getTargetConstructor();
        if (constructor == null) {
            return null;
        }
        try {
            return constructor.newInstance(objectArray);
        } catch (Throwable throwable) {
            Reflector.handleException(throwable, reflectorConstructor, objectArray);
            return null;
        }
    }

    public static boolean matchesTypes(Class[] classArray, Class[] classArray2) {
        if (classArray.length != classArray2.length) {
            return true;
        }
        for (int i = 0; i < classArray2.length; ++i) {
            Class clazz = classArray[i];
            Class clazz2 = classArray2[i];
            if (clazz == clazz2) continue;
            return true;
        }
        return false;
    }

    private static void dbgCall(boolean bl, String string, ReflectorMethod reflectorMethod, Object[] objectArray, Object object) {
        String string2 = reflectorMethod.getTargetMethod().getDeclaringClass().getName();
        String string3 = reflectorMethod.getTargetMethod().getName();
        String string4 = "";
        if (bl) {
            string4 = " static";
        }
        Log.dbg(string + string4 + " " + string2 + "." + string3 + "(" + ArrayUtils.arrayToString(objectArray) + ") => " + object);
    }

    private static void dbgCallVoid(boolean bl, String string, ReflectorMethod reflectorMethod, Object[] objectArray) {
        String string2 = reflectorMethod.getTargetMethod().getDeclaringClass().getName();
        String string3 = reflectorMethod.getTargetMethod().getName();
        String string4 = "";
        if (bl) {
            string4 = " static";
        }
        Log.dbg(string + string4 + " " + string2 + "." + string3 + "(" + ArrayUtils.arrayToString(objectArray) + ")");
    }

    private static void dbgFieldValue(boolean bl, String string, ReflectorField reflectorField, Object object) {
        String string2 = reflectorField.getTargetField().getDeclaringClass().getName();
        String string3 = reflectorField.getTargetField().getName();
        String string4 = "";
        if (bl) {
            string4 = " static";
        }
        Log.dbg(string + string4 + " " + string2 + "." + string3 + " => " + object);
    }

    private static void handleException(Throwable throwable, Object object, ReflectorMethod reflectorMethod, Object[] objectArray) {
        if (throwable instanceof InvocationTargetException) {
            Throwable throwable2 = throwable.getCause();
            if (throwable2 instanceof RuntimeException) {
                RuntimeException runtimeException = (RuntimeException)throwable2;
                throw runtimeException;
            }
            Log.error("", throwable);
        } else {
            Log.warn("*** Exception outside of method ***");
            Log.warn("Method deactivated: " + reflectorMethod.getTargetMethod());
            reflectorMethod.deactivate();
            if (throwable instanceof IllegalArgumentException) {
                Log.warn("*** IllegalArgumentException ***");
                Log.warn("Method: " + reflectorMethod.getTargetMethod());
                Log.warn("Object: " + object);
                Log.warn("Parameter classes: " + ArrayUtils.arrayToString(Reflector.getClasses(objectArray)));
                Log.warn("Parameters: " + ArrayUtils.arrayToString(objectArray));
            }
            Log.warn("", throwable);
        }
    }

    private static void handleException(Throwable throwable, ReflectorConstructor reflectorConstructor, Object[] objectArray) {
        if (throwable instanceof InvocationTargetException) {
            Log.error("", throwable);
        } else {
            Log.warn("*** Exception outside of constructor ***");
            Log.warn("Constructor deactivated: " + reflectorConstructor.getTargetConstructor());
            reflectorConstructor.deactivate();
            if (throwable instanceof IllegalArgumentException) {
                Log.warn("*** IllegalArgumentException ***");
                Log.warn("Constructor: " + reflectorConstructor.getTargetConstructor());
                Log.warn("Parameter classes: " + ArrayUtils.arrayToString(Reflector.getClasses(objectArray)));
                Log.warn("Parameters: " + ArrayUtils.arrayToString(objectArray));
            }
            Log.warn("", throwable);
        }
    }

    private static Object[] getClasses(Object[] objectArray) {
        if (objectArray == null) {
            return new Class[0];
        }
        Object[] objectArray2 = new Class[objectArray.length];
        for (int i = 0; i < objectArray2.length; ++i) {
            Object object = objectArray[i];
            if (object == null) continue;
            objectArray2[i] = object.getClass();
        }
        return objectArray2;
    }

    private static ReflectorField[] getReflectorFields(ReflectorClass reflectorClass, Class clazz, int n) {
        ReflectorField[] reflectorFieldArray = new ReflectorField[n];
        for (int i = 0; i < reflectorFieldArray.length; ++i) {
            reflectorFieldArray[i] = new ReflectorField(reflectorClass, clazz, i);
        }
        return reflectorFieldArray;
    }

    private static boolean registerResolvable(String string) {
        String string2 = string;
        IResolvable iResolvable = new IResolvable(string){
            final String val$str;
            {
                this.val$str = string;
            }

            @Override
            public void resolve() {
                LOGGER.info("[OptiFine] " + this.val$str);
            }
        };
        ReflectorResolver.register(iResolvable);
        return false;
    }
}


package optifine;

import com.google.common.base.Optional;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import javax.vecmath.Matrix4f;
import net.UB;
import net.aHv;
import net.aQl;
import net.arf;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.model.ModelBanner;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBat;
import net.minecraft.client.model.ModelBlaze;
import net.minecraft.client.model.ModelBook;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.model.ModelDragon;
import net.minecraft.client.model.ModelEnderCrystal;
import net.minecraft.client.model.ModelEnderMite;
import net.minecraft.client.model.ModelGhast;
import net.minecraft.client.model.ModelGuardian;
import net.minecraft.client.model.ModelHorse;
import net.minecraft.client.model.ModelHumanoidHead;
import net.minecraft.client.model.ModelLeashKnot;
import net.minecraft.client.model.ModelMagmaCube;
import net.minecraft.client.model.ModelOcelot;
import net.minecraft.client.model.ModelRabbit;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelSign;
import net.minecraft.client.model.ModelSilverfish;
import net.minecraft.client.model.ModelSkeletonHead;
import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.model.ModelSquid;
import net.minecraft.client.model.ModelWitch;
import net.minecraft.client.model.ModelWither;
import net.minecraft.client.model.ModelWolf;
import net.minecraft.client.multiplayer.ChunkProviderClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.entity.RenderBoat;
import net.minecraft.client.renderer.entity.RenderLeashKnot;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderMinecart;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.renderer.tileentity.RenderEnderCrystal;
import net.minecraft.client.renderer.tileentity.RenderItemFrame;
import net.minecraft.client.renderer.tileentity.RenderWitherSkull;
import net.minecraft.client.renderer.tileentity.TileEntityBannerRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityChestRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityEnchantmentTableRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityEnderChestRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySignRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
import net.minecraft.client.renderer.vertex.VertexFormatElement$EnumUsage;
import net.minecraft.client.resources.DefaultResourcePack;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelRotation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemRecord;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.LongHashMap;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.property.IUnlistedProperty;
import optifine.Config;
import optifine.MatchBlock;
import optifine.ReflectorClass;
import optifine.ReflectorField;
import optifine.ReflectorFields;

public class Reflector {
   private static boolean logForge = logEntry("*** Reflector Forge ***");
   public static ReflectorClass Attributes = new ReflectorClass("net.minecraftforge.client.model.Attributes");
   public static ReflectorField Attributes_DEFAULT_BAKED_FORMAT = new ReflectorField(Attributes, "DEFAULT_BAKED_FORMAT");
   public static ReflectorClass BetterFoliageClient = new ReflectorClass("mods.betterfoliage.client.BetterFoliageClient");
   public static ReflectorClass BlamingTransformer = new ReflectorClass("net.minecraftforge.fml.common.asm.transformers.BlamingTransformer");
   public static aQl b3 = new aQl(BlamingTransformer, "onCrash");
   public static ReflectorClass ChunkWatchEvent_UnWatch = new ReflectorClass("net.minecraftforge.event.world.ChunkWatchEvent$UnWatch");
   public static UB aI = new UB(ChunkWatchEvent_UnWatch, new Class[]{ChunkCoordIntPair.class, EntityPlayerMP.class});
   public static ReflectorClass CoreModManager = new ReflectorClass("net.minecraftforge.fml.relauncher.CoreModManager");
   public static aQl a2 = new aQl(CoreModManager, "onCrash");
   public static ReflectorClass DimensionManager = new ReflectorClass("net.minecraftforge.common.DimensionManager");
   public static aQl ba = new aQl(DimensionManager, "createProviderFor");
   public static aQl ae = new aQl(DimensionManager, "getStaticDimensionIDs");
   public static ReflectorClass DrawScreenEvent_Pre = new ReflectorClass("net.minecraftforge.client.event.GuiScreenEvent$DrawScreenEvent$Pre");
   public static UB aw = new UB(DrawScreenEvent_Pre, new Class[]{GuiScreen.class, Integer.TYPE, Integer.TYPE, Float.TYPE});
   public static ReflectorClass DrawScreenEvent_Post = new ReflectorClass("net.minecraftforge.client.event.GuiScreenEvent$DrawScreenEvent$Post");
   public static UB d = new UB(DrawScreenEvent_Post, new Class[]{GuiScreen.class, Integer.TYPE, Integer.TYPE, Float.TYPE});
   public static ReflectorClass EntityViewRenderEvent_CameraSetup = new ReflectorClass("net.minecraftforge.client.event.EntityViewRenderEvent$CameraSetup");
   public static UB bo = new UB(EntityViewRenderEvent_CameraSetup, new Class[]{EntityRenderer.class, Entity.class, Block.class, Double.TYPE, Float.TYPE, Float.TYPE, Float.TYPE});
   public static ReflectorField EntityViewRenderEvent_CameraSetup_yaw = new ReflectorField(EntityViewRenderEvent_CameraSetup, "yaw");
   public static ReflectorField EntityViewRenderEvent_CameraSetup_pitch = new ReflectorField(EntityViewRenderEvent_CameraSetup, "pitch");
   public static ReflectorField EntityViewRenderEvent_CameraSetup_roll = new ReflectorField(EntityViewRenderEvent_CameraSetup, "roll");
   public static ReflectorClass EntityViewRenderEvent_FogColors = new ReflectorClass("net.minecraftforge.client.event.EntityViewRenderEvent$FogColors");
   public static UB aY = new UB(EntityViewRenderEvent_FogColors, new Class[]{EntityRenderer.class, Entity.class, Block.class, Double.TYPE, Float.TYPE, Float.TYPE, Float.TYPE});
   public static ReflectorField EntityViewRenderEvent_FogColors_red = new ReflectorField(EntityViewRenderEvent_FogColors, "red");
   public static ReflectorField EntityViewRenderEvent_FogColors_green = new ReflectorField(EntityViewRenderEvent_FogColors, "green");
   public static ReflectorField EntityViewRenderEvent_FogColors_blue = new ReflectorField(EntityViewRenderEvent_FogColors, "blue");
   public static ReflectorClass Event = new ReflectorClass("net.minecraftforge.fml.common.eventhandler.Event");
   public static aQl b1 = new aQl(Event, "isCanceled");
   public static ReflectorClass EventBus = new ReflectorClass("net.minecraftforge.fml.common.eventhandler.EventBus");
   public static aQl b5 = new aQl(EventBus, "post");
   public static ReflectorClass Event_Result = new ReflectorClass("net.minecraftforge.fml.common.eventhandler.Event$Result");
   public static ReflectorField Event_Result_DENY = new ReflectorField(Event_Result, "DENY");
   public static ReflectorField Event_Result_ALLOW = new ReflectorField(Event_Result, "ALLOW");
   public static ReflectorField Event_Result_DEFAULT = new ReflectorField(Event_Result, "DEFAULT");
   public static ReflectorClass ExtendedBlockState = new ReflectorClass("net.minecraftforge.common.property.ExtendedBlockState");
   public static UB U = new UB(ExtendedBlockState, new Class[]{Block.class, IProperty[].class, IUnlistedProperty[].class});
   public static ReflectorClass FMLClientHandler = new ReflectorClass("net.minecraftforge.fml.client.FMLClientHandler");
   public static aQl bQ = new aQl(FMLClientHandler, "instance");
   public static aQl J = new aQl(FMLClientHandler, "isLoading");
   public static aQl aC = new aQl(FMLClientHandler, "trackBrokenTexture");
   public static aQl z = new aQl(FMLClientHandler, "trackMissingTexture");
   public static ReflectorClass FMLCommonHandler = new ReflectorClass("net.minecraftforge.fml.common.FMLCommonHandler");
   public static aQl bx = new aQl(FMLCommonHandler, "callFuture");
   public static aQl ap = new aQl(FMLCommonHandler, "enhanceCrashReport");
   public static aQl aZ = new aQl(FMLCommonHandler, "getBrandings");
   public static aQl O = new aQl(FMLCommonHandler, "handleServerAboutToStart");
   public static aQl dt = new aQl(FMLCommonHandler, "handleServerStarting");
   public static aQl dk = new aQl(FMLCommonHandler, "instance");
   public static ReflectorClass ForgeBiome = new ReflectorClass(BiomeGenBase.class);
   public static aQl cY = new aQl(ForgeBiome, "getWaterColorMultiplier");
   public static ReflectorClass ForgeBlock = new ReflectorClass(Block.class);
   public static aQl at = new aQl(ForgeBlock, "addDestroyEffects");
   public static aQl bJ = new aQl(ForgeBlock, "addHitEffects");
   public static aQl dd = new aQl(ForgeBlock, "canCreatureSpawn");
   public static aQl cU = new aQl(ForgeBlock, "canRenderInLayer", new Class[]{EnumWorldBlockLayer.class});
   public static aQl a3 = new aQl(ForgeBlock, "doesSideBlockRendering");
   public static aQl t = new aQl(ForgeBlock, "getBedDirection");
   public static aQl s = new aQl(ForgeBlock, "getExtendedState");
   public static aQl cp = new aQl(ForgeBlock, "getLightOpacity");
   public static aQl L = new aQl(ForgeBlock, "getLightValue");
   public static aQl bm = new aQl(ForgeBlock, "hasTileEntity", new Class[]{IBlockState.class});
   public static aQl bw = new aQl(ForgeBlock, "isAir");
   public static aQl df = new aQl(ForgeBlock, "isBed");
   public static aQl an = new aQl(ForgeBlock, "isBedFoot");
   public static aQl bb = new aQl(ForgeBlock, "isSideSolid");
   public static ReflectorClass ForgeEntity = new ReflectorClass(Entity.class);
   public static aQl cC = new aQl(ForgeEntity, "canRiderInteract");
   public static ReflectorField ForgeEntity_captureDrops = new ReflectorField(ForgeEntity, "captureDrops");
   public static ReflectorField ForgeEntity_capturedDrops = new ReflectorField(ForgeEntity, "capturedDrops");
   public static aQl v = new aQl(ForgeEntity, "shouldRenderInPass");
   public static aQl r = new aQl(ForgeEntity, "shouldRiderSit");
   public static ReflectorClass ForgeEventFactory = new ReflectorClass("net.minecraftforge.event.ForgeEventFactory");
   public static aQl n = new aQl(ForgeEventFactory, "canEntityDespawn");
   public static aQl da = new aQl(ForgeEventFactory, "canEntitySpawn");
   public static aQl bD = new aQl(ForgeEventFactory, "renderBlockOverlay");
   public static aQl f = new aQl(ForgeEventFactory, "renderFireOverlay");
   public static aQl bl = new aQl(ForgeEventFactory, "renderWaterOverlay");
   public static ReflectorClass ForgeHooks = new ReflectorClass("net.minecraftforge.common.ForgeHooks");
   public static aQl du = new aQl(ForgeHooks, "onLivingAttack");
   public static aQl dy = new aQl(ForgeHooks, "onLivingDeath");
   public static aQl a1 = new aQl(ForgeHooks, "onLivingDrops");
   public static aQl bO = new aQl(ForgeHooks, "onLivingFall");
   public static aQl ak = new aQl(ForgeHooks, "onLivingHurt");
   public static aQl bz = new aQl(ForgeHooks, "onLivingJump");
   public static aQl M = new aQl(ForgeHooks, "onLivingSetAttackTarget");
   public static aQl aj = new aQl(ForgeHooks, "onLivingUpdate");
   public static ReflectorClass ForgeHooksClient = new ReflectorClass("net.minecraftforge.client.ForgeHooksClient");
   public static aQl ax = new aQl(ForgeHooksClient, "applyTransform", new Class[]{Matrix4f.class, Optional.class});
   public static aQl dr = new aQl(ForgeHooksClient, "dispatchRenderLast");
   public static aQl cL = new aQl(ForgeHooksClient, "drawScreen");
   public static aQl bh = new aQl(ForgeHooksClient, "fillNormal");
   public static aQl V = new aQl(ForgeHooksClient, "handleCameraTransforms");
   public static aQl bG = new aQl(ForgeHooksClient, "getArmorModel");
   public static aQl bS = new aQl(ForgeHooksClient, "getArmorTexture");
   public static aQl cT = new aQl(ForgeHooksClient, "getFogDensity");
   public static aQl bu = new aQl(ForgeHooksClient, "getFOVModifier");
   public static aQl x = new aQl(ForgeHooksClient, "getMatrix", new Class[]{ModelRotation.class});
   public static aQl ac = new aQl(ForgeHooksClient, "getOffsetFOV");
   public static aQl bj = new aQl(ForgeHooksClient, "loadEntityShader");
   public static aQl c5 = new aQl(ForgeHooksClient, "onDrawBlockHighlight");
   public static aQl bI = new aQl(ForgeHooksClient, "onFogRender");
   public static aQl cy = new aQl(ForgeHooksClient, "onTextureStitchedPre");
   public static aQl cG = new aQl(ForgeHooksClient, "onTextureStitchedPost");
   public static aQl aA = new aQl(ForgeHooksClient, "orientBedCamera");
   public static aQl R = new aQl(ForgeHooksClient, "putQuadColor");
   public static aQl bv = new aQl(ForgeHooksClient, "renderFirstPersonHand");
   public static aQl cj = new aQl(ForgeHooksClient, "renderMainMenu");
   public static aQl ck = new aQl(ForgeHooksClient, "setRenderLayer");
   public static aQl aF = new aQl(ForgeHooksClient, "setRenderPass");
   public static aQl cs = new aQl(ForgeHooksClient, "transform");
   public static ReflectorClass ForgeItem = new ReflectorClass(Item.class);
   public static aQl cX = new aQl(ForgeItem, "getDurabilityForDisplay");
   public static aQl cz = new aQl(ForgeItem, "getModel");
   public static aQl ca = new aQl(ForgeItem, "onEntitySwing");
   public static aQl ab = new aQl(ForgeItem, "shouldCauseReequipAnimation");
   public static aQl ce = new aQl(ForgeItem, "showDurabilityBar");
   public static ReflectorClass ForgeItemRecord = new ReflectorClass(ItemRecord.class);
   public static aQl bH = new aQl(ForgeItemRecord, "getRecordResource", new Class[]{String.class});
   public static ReflectorClass ForgeModContainer = new ReflectorClass("net.minecraftforge.common.ForgeModContainer");
   public static ReflectorField ForgeModContainer_forgeLightPipelineEnabled = new ReflectorField(ForgeModContainer, "forgeLightPipelineEnabled");
   public static ReflectorClass ForgePotionEffect = new ReflectorClass(PotionEffect.class);
   public static aQl b6 = new aQl(ForgePotionEffect, "isCurativeItem");
   public static ReflectorClass ForgeTileEntity = new ReflectorClass(TileEntity.class);
   public static aQl aP = new aQl(ForgeTileEntity, "canRenderBreaking");
   public static aQl bV = new aQl(ForgeTileEntity, "getRenderBoundingBox");
   public static aQl b9 = new aQl(ForgeTileEntity, "hasFastRenderer");
   public static aQl cb = new aQl(ForgeTileEntity, "shouldRenderInPass");
   public static ReflectorClass ForgeTileEntityRendererDispatcher = new ReflectorClass(TileEntityRendererDispatcher.class);
   public static aQl cc = new aQl(ForgeTileEntityRendererDispatcher, "preDrawBatch");
   public static aQl db = new aQl(ForgeTileEntityRendererDispatcher, "drawBatch");
   public static ReflectorClass ForgeVertexFormatElementEnumUseage = new ReflectorClass(VertexFormatElement$EnumUsage.class);
   public static aQl ct = new aQl(ForgeVertexFormatElementEnumUseage, "preDraw");
   public static aQl by = new aQl(ForgeVertexFormatElementEnumUseage, "postDraw");
   public static ReflectorClass ForgeWorld = new ReflectorClass(World.class);
   public static aQl a6 = new aQl(ForgeWorld, "countEntities", new Class[]{EnumCreatureType.class, Boolean.TYPE});
   public static aQl aB = new aQl(ForgeWorld, "getPerWorldStorage");
   public static ReflectorClass ForgeWorldProvider = new ReflectorClass(WorldProvider.class);
   public static aQl br = new aQl(ForgeWorldProvider, "getCloudRenderer");
   public static aQl aD = new aQl(ForgeWorldProvider, "getSkyRenderer");
   public static aQl cq = new aQl(ForgeWorldProvider, "getWeatherRenderer");
   public static ReflectorClass GuiModList = new ReflectorClass("net.minecraftforge.fml.client.GuiModList");
   public static UB cg = new UB(GuiModList, new Class[]{GuiScreen.class});
   public static ReflectorClass IColoredBakedQuad = new ReflectorClass("net.minecraftforge.client.model.IColoredBakedQuad");
   public static ReflectorClass IExtendedBlockState = new ReflectorClass("net.minecraftforge.common.property.IExtendedBlockState");
   public static aQl m = new aQl(IExtendedBlockState, "getClean");
   public static ReflectorClass IRenderHandler = new ReflectorClass("net.minecraftforge.client.IRenderHandler");
   public static aQl k = new aQl(IRenderHandler, "render");
   public static ReflectorClass ISmartBlockModel = new ReflectorClass("net.minecraftforge.client.model.ISmartBlockModel");
   public static aQl o = new aQl(ISmartBlockModel, "handleBlockState");
   public static ReflectorClass ItemModelMesherForge = new ReflectorClass("net.minecraftforge.client.ItemModelMesherForge");
   public static UB cJ = new UB(ItemModelMesherForge, new Class[]{ModelManager.class});
   public static ReflectorClass Launch = new ReflectorClass("net.minecraft.launchwrapper.Launch");
   public static ReflectorField Launch_blackboard = new ReflectorField(Launch, "blackboard");
   public static ReflectorClass LightUtil = new ReflectorClass("net.minecraftforge.client.model.pipeline.LightUtil");
   public static ReflectorField LightUtil_itemConsumer = new ReflectorField(LightUtil, "itemConsumer");
   public static aQl bk = new aQl(LightUtil, "putBakedQuad");
   public static aQl j = new aQl(LightUtil, "renderQuadColor");
   public static ReflectorField LightUtil_tessellator = new ReflectorField(LightUtil, "tessellator");
   public static ReflectorClass MinecraftForge = new ReflectorClass("net.minecraftforge.common.MinecraftForge");
   public static ReflectorField MinecraftForge_EVENT_BUS = new ReflectorField(MinecraftForge, "EVENT_BUS");
   public static ReflectorClass MinecraftForgeClient = new ReflectorClass("net.minecraftforge.client.MinecraftForgeClient");
   public static aQl cR = new aQl(MinecraftForgeClient, "getRenderPass");
   public static aQl bE = new aQl(MinecraftForgeClient, "onRebuildChunk");
   public static ReflectorClass ModelLoader = new ReflectorClass("net.minecraftforge.client.model.ModelLoader");
   public static aQl y = new aQl(ModelLoader, "onRegisterItems");
   public static ReflectorClass RenderBlockOverlayEvent_OverlayType = new ReflectorClass("net.minecraftforge.client.event.RenderBlockOverlayEvent$OverlayType");
   public static ReflectorField RenderBlockOverlayEvent_OverlayType_BLOCK = new ReflectorField(RenderBlockOverlayEvent_OverlayType, "BLOCK");
   public static ReflectorClass RenderingRegistry = new ReflectorClass("net.minecraftforge.fml.client.registry.RenderingRegistry");
   public static aQl N = new aQl(RenderingRegistry, "loadEntityRenderers", new Class[]{RenderManager.class, Map.class});
   public static ReflectorClass RenderItemInFrameEvent = new ReflectorClass("net.minecraftforge.client.event.RenderItemInFrameEvent");
   public static UB cP = new UB(RenderItemInFrameEvent, new Class[]{EntityItemFrame.class, RenderItemFrame.class});
   public static ReflectorClass RenderLivingEvent_Pre = new ReflectorClass("net.minecraftforge.client.event.RenderLivingEvent$Pre");
   public static UB aT = new UB(RenderLivingEvent_Pre, new Class[]{EntityLivingBase.class, RendererLivingEntity.class, Double.TYPE, Double.TYPE, Double.TYPE});
   public static ReflectorClass RenderLivingEvent_Post = new ReflectorClass("net.minecraftforge.client.event.RenderLivingEvent$Post");
   public static UB c1 = new UB(RenderLivingEvent_Post, new Class[]{EntityLivingBase.class, RendererLivingEntity.class, Double.TYPE, Double.TYPE, Double.TYPE});
   public static ReflectorClass RenderLivingEvent_Specials_Pre = new ReflectorClass("net.minecraftforge.client.event.RenderLivingEvent$Specials$Pre");
   public static UB aW = new UB(RenderLivingEvent_Specials_Pre, new Class[]{EntityLivingBase.class, RendererLivingEntity.class, Double.TYPE, Double.TYPE, Double.TYPE});
   public static ReflectorClass RenderLivingEvent_Specials_Post = new ReflectorClass("net.minecraftforge.client.event.RenderLivingEvent$Specials$Post");
   public static UB bT = new UB(RenderLivingEvent_Specials_Post, new Class[]{EntityLivingBase.class, RendererLivingEntity.class, Double.TYPE, Double.TYPE, Double.TYPE});
   public static ReflectorClass SplashScreen = new ReflectorClass("net.minecraftforge.fml.client.SplashProgress");
   public static ReflectorClass WorldEvent_Load = new ReflectorClass("net.minecraftforge.event.world.WorldEvent$Load");
   public static UB bP = new UB(WorldEvent_Load, new Class[]{World.class});
   private static boolean logVanilla = logEntry("*** Reflector Vanilla ***");
   public static ReflectorClass ChunkProviderClient = new ReflectorClass(ChunkProviderClient.class);
   public static ReflectorField ChunkProviderClient_chunkMapping = new ReflectorField(ChunkProviderClient, LongHashMap.class);
   public static ReflectorClass GuiMainMenu = new ReflectorClass(aHv.class);
   public static ReflectorField GuiMainMenu_splashText = new ReflectorField(GuiMainMenu, String.class);
   public static ReflectorClass Minecraft = new ReflectorClass(Minecraft.class);
   public static ReflectorField Minecraft_defaultResourcePack = new ReflectorField(Minecraft, DefaultResourcePack.class);
   public static ReflectorClass ModelHumanoidHead = new ReflectorClass(ModelHumanoidHead.class);
   public static ReflectorField ModelHumanoidHead_head = new ReflectorField(ModelHumanoidHead, ModelRenderer.class);
   public static ReflectorClass ModelBat = new ReflectorClass(ModelBat.class);
   public static ReflectorFields ModelBat_ModelRenderers = new ReflectorFields(ModelBat, ModelRenderer.class, 6);
   public static ReflectorClass ModelBlaze = new ReflectorClass(ModelBlaze.class);
   public static ReflectorField ModelBlaze_blazeHead = new ReflectorField(ModelBlaze, ModelRenderer.class);
   public static ReflectorField ModelBlaze_blazeSticks = new ReflectorField(ModelBlaze, ModelRenderer[].class);
   public static ReflectorClass ModelDragon = new ReflectorClass(ModelDragon.class);
   public static ReflectorFields ModelDragon_ModelRenderers = new ReflectorFields(ModelDragon, ModelRenderer.class, 12);
   public static ReflectorClass ModelEnderCrystal = new ReflectorClass(ModelEnderCrystal.class);
   public static ReflectorFields ModelEnderCrystal_ModelRenderers = new ReflectorFields(ModelEnderCrystal, ModelRenderer.class, 3);
   public static ReflectorClass RenderEnderCrystal = new ReflectorClass(RenderEnderCrystal.class);
   public static ReflectorField RenderEnderCrystal_modelEnderCrystal = new ReflectorField(RenderEnderCrystal, ModelBase.class, 0);
   public static ReflectorClass ModelEnderMite = new ReflectorClass(ModelEnderMite.class);
   public static ReflectorField ModelEnderMite_bodyParts = new ReflectorField(ModelEnderMite, ModelRenderer[].class);
   public static ReflectorClass ModelGhast = new ReflectorClass(ModelGhast.class);
   public static ReflectorField ModelGhast_body = new ReflectorField(ModelGhast, ModelRenderer.class);
   public static ReflectorField ModelGhast_tentacles = new ReflectorField(ModelGhast, ModelRenderer[].class);
   public static ReflectorClass ModelGuardian = new ReflectorClass(ModelGuardian.class);
   public static ReflectorField ModelGuardian_body = new ReflectorField(ModelGuardian, ModelRenderer.class, 0);
   public static ReflectorField ModelGuardian_eye = new ReflectorField(ModelGuardian, ModelRenderer.class, 1);
   public static ReflectorField ModelGuardian_spines = new ReflectorField(ModelGuardian, ModelRenderer[].class, 0);
   public static ReflectorField ModelGuardian_tail = new ReflectorField(ModelGuardian, ModelRenderer[].class, 1);
   public static ReflectorClass ModelHorse = new ReflectorClass(ModelHorse.class);
   public static ReflectorFields ModelHorse_ModelRenderers = new ReflectorFields(ModelHorse, ModelRenderer.class, 39);
   public static ReflectorClass RenderLeashKnot = new ReflectorClass(RenderLeashKnot.class);
   public static ReflectorField RenderLeashKnot_leashKnotModel = new ReflectorField(RenderLeashKnot, ModelLeashKnot.class);
   public static ReflectorClass ModelMagmaCube = new ReflectorClass(ModelMagmaCube.class);
   public static ReflectorField ModelMagmaCube_core = new ReflectorField(ModelMagmaCube, ModelRenderer.class);
   public static ReflectorField ModelMagmaCube_segments = new ReflectorField(ModelMagmaCube, ModelRenderer[].class);
   public static ReflectorClass ModelOcelot = new ReflectorClass(ModelOcelot.class);
   public static ReflectorFields ModelOcelot_ModelRenderers = new ReflectorFields(ModelOcelot, ModelRenderer.class, 8);
   public static ReflectorClass ModelRabbit = new ReflectorClass(ModelRabbit.class);
   public static ReflectorFields ModelRabbit_renderers = new ReflectorFields(ModelRabbit, ModelRenderer.class, 12);
   public static ReflectorClass ModelSilverfish = new ReflectorClass(ModelSilverfish.class);
   public static ReflectorField ModelSilverfish_bodyParts = new ReflectorField(ModelSilverfish, ModelRenderer[].class, 0);
   public static ReflectorField ModelSilverfish_wingParts = new ReflectorField(ModelSilverfish, ModelRenderer[].class, 1);
   public static ReflectorClass ModelSlime = new ReflectorClass(ModelSlime.class);
   public static ReflectorFields ModelSlime_ModelRenderers = new ReflectorFields(ModelSlime, ModelRenderer.class, 4);
   public static ReflectorClass ModelSquid = new ReflectorClass(ModelSquid.class);
   public static ReflectorField ModelSquid_body = new ReflectorField(ModelSquid, ModelRenderer.class);
   public static ReflectorField ModelSquid_tentacles = new ReflectorField(ModelSquid, ModelRenderer[].class);
   public static ReflectorClass ModelWitch = new ReflectorClass(ModelWitch.class);
   public static ReflectorField ModelWitch_mole = new ReflectorField(ModelWitch, ModelRenderer.class, 0);
   public static ReflectorField ModelWitch_hat = new ReflectorField(ModelWitch, ModelRenderer.class, 1);
   public static ReflectorClass ModelWither = new ReflectorClass(ModelWither.class);
   public static ReflectorField ModelWither_bodyParts = new ReflectorField(ModelWither, ModelRenderer[].class, 0);
   public static ReflectorField ModelWither_heads = new ReflectorField(ModelWither, ModelRenderer[].class, 1);
   public static ReflectorClass ModelWolf = new ReflectorClass(ModelWolf.class);
   public static ReflectorField ModelWolf_tail = new ReflectorField(ModelWolf, ModelRenderer.class, 6);
   public static ReflectorField ModelWolf_mane = new ReflectorField(ModelWolf, ModelRenderer.class, 7);
   public static ReflectorClass ad = new ReflectorClass("net.optifine.OptiFineClassTransformer");
   public static ReflectorField OptiFineClassTransformer_instance = new ReflectorField(ad, "instance");
   public static aQl c9 = new aQl(ad, "getOptiFineResource");
   public static ReflectorClass RenderBoat = new ReflectorClass(RenderBoat.class);
   public static ReflectorField RenderBoat_modelBoat = new ReflectorField(RenderBoat, ModelBase.class);
   public static ReflectorClass RenderMinecart = new ReflectorClass(RenderMinecart.class);
   public static ReflectorField RenderMinecart_modelMinecart = new ReflectorField(RenderMinecart, ModelBase.class);
   public static ReflectorClass RenderWitherSkull = new ReflectorClass(RenderWitherSkull.class);
   public static ReflectorField RenderWitherSkull_model = new ReflectorField(RenderWitherSkull, ModelSkeletonHead.class);
   public static ReflectorClass ResourcePackRepository = new ReflectorClass(arf.class);
   public static ReflectorField ResourcePackRepository_repositoryEntries = new ReflectorField(ResourcePackRepository, List.class, 1);
   public static ReflectorClass TileEntityBannerRenderer = new ReflectorClass(TileEntityBannerRenderer.class);
   public static ReflectorField TileEntityBannerRenderer_bannerModel = new ReflectorField(TileEntityBannerRenderer, ModelBanner.class);
   public static ReflectorClass TileEntityChestRenderer = new ReflectorClass(TileEntityChestRenderer.class);
   public static ReflectorField TileEntityChestRenderer_simpleChest = new ReflectorField(TileEntityChestRenderer, ModelChest.class, 0);
   public static ReflectorField TileEntityChestRenderer_largeChest = new ReflectorField(TileEntityChestRenderer, ModelChest.class, 1);
   public static ReflectorClass TileEntityEnchantmentTableRenderer = new ReflectorClass(TileEntityEnchantmentTableRenderer.class);
   public static ReflectorField TileEntityEnchantmentTableRenderer_modelBook = new ReflectorField(TileEntityEnchantmentTableRenderer, ModelBook.class);
   public static ReflectorClass TileEntityEnderChestRenderer = new ReflectorClass(TileEntityEnderChestRenderer.class);
   public static ReflectorField TileEntityEnderChestRenderer_modelChest = new ReflectorField(TileEntityEnderChestRenderer, ModelChest.class);
   public static ReflectorClass TileEntitySignRenderer = new ReflectorClass(TileEntitySignRenderer.class);
   public static ReflectorField TileEntitySignRenderer_model = new ReflectorField(TileEntitySignRenderer, ModelSign.class);
   public static ReflectorClass TileEntitySkullRenderer = new ReflectorClass(TileEntitySkullRenderer.class);
   public static ReflectorField TileEntitySkullRenderer_skeletonHead = new ReflectorField(TileEntitySkullRenderer, ModelSkeletonHead.class, 0);
   public static ReflectorField TileEntitySkullRenderer_humanoidHead = new ReflectorField(TileEntitySkullRenderer, ModelSkeletonHead.class, 1);
   private static String dn;

   public static void a(aQl param0, Object... param1) {
      // $FF: Couldn't be decompiled
   }

   public static boolean b(aQl param0, Object... param1) {
      // $FF: Couldn't be decompiled
   }

   public static int d(aQl param0, Object... param1) {
      // $FF: Couldn't be decompiled
   }

   public static float g(aQl param0, Object... param1) {
      // $FF: Couldn't be decompiled
   }

   public static double c(aQl param0, Object... param1) {
      // $FF: Couldn't be decompiled
   }

   public static String e(aQl param0, Object... param1) {
      // $FF: Couldn't be decompiled
   }

   public static Object f(aQl var0, Object... var1) {
      aQl var10000 = var0;

      try {
         Method var2 = var10000.a();
         return null;
      } catch (Throwable var3) {
         a(var3, (Object)null, var0, var1);
         return null;
      }
   }

   public static void g(Object var0, aQl var1, Object... var2) {
      PacketRemapper[] var3 = MatchBlock.b();
   }

   public static boolean d(Object param0, aQl param1, Object... param2) {
      // $FF: Couldn't be decompiled
   }

   public static int e(Object param0, aQl param1, Object... param2) {
      // $FF: Couldn't be decompiled
   }

   public static float a(Object param0, aQl param1, Object... param2) {
      // $FF: Couldn't be decompiled
   }

   public static double c(Object param0, aQl param1, Object... param2) {
      // $FF: Couldn't be decompiled
   }

   public static String b(Object var0, aQl var1, Object... var2) {
      aQl var10000 = var1;

      try {
         Method var3 = var10000.a();
         return null;
      } catch (Throwable var4) {
         a(var4, var0, var1, var2);
         return null;
      }
   }

   public static Object f(Object param0, aQl param1, Object... param2) {
      // $FF: Couldn't be decompiled
   }

   public static Object getFieldValue(ReflectorField var0) {
      return getFieldValue((Object)null, var0);
   }

   public static Object getFieldValue(Object param0, ReflectorField param1) {
      // $FF: Couldn't be decompiled
   }

   public static Object getFieldValue(ReflectorFields var0, int var1) {
      MatchBlock.b();
      ReflectorField var3 = var0.a(var1);
      return var3 == null?null:getFieldValue(var3);
   }

   public static Object getFieldValue(Object var0, ReflectorFields var1, int var2) {
      MatchBlock.b();
      ReflectorField var4 = var1.a(var2);
      return var4 == null?null:getFieldValue(var0, var4);
   }

   public static float getFieldValueFloat(Object var0, ReflectorField var1, float var2) {
      MatchBlock.b();
      Object var4 = getFieldValue(var0, var1);
      if(!(var4 instanceof Float)) {
         return var2;
      } else {
         Float var5 = (Float)var4;
         return var5.floatValue();
      }
   }

   public static boolean setFieldValue(ReflectorField var0, Object var1) {
      return setFieldValue((Object)null, var0, var1);
   }

   public static boolean setFieldValue(Object param0, ReflectorField param1, Object param2) {
      // $FF: Couldn't be decompiled
   }

   public static boolean a(UB var0, Object... var1) {
      MatchBlock.b();
      Object var3 = b(var0, var1);
      return var3 == null?false:postForgeBusEvent(var3);
   }

   public static boolean postForgeBusEvent(Object var0) {
      PacketRemapper[] var1 = MatchBlock.b();
      if(var0 == null) {
         return false;
      } else {
         Object var2 = getFieldValue(MinecraftForge_EVENT_BUS);
         if(var2 == null) {
            return false;
         } else {
            Object var3 = f(var2, b5, new Object[]{var0});
            if(!(var3 instanceof Boolean)) {
               return false;
            } else {
               Boolean var4 = (Boolean)var3;
               return var4.booleanValue();
            }
         }
      }
   }

   public static Object b(UB var0, Object... var1) {
      MatchBlock.b();
      Constructor var3 = var0.a();
      if(var3 == null) {
         return null;
      } else {
         Constructor var10000 = var3;
         Object[] var10001 = var1;

         try {
            Object var4 = var10000.newInstance(var10001);
            return var4;
         } catch (Throwable var5) {
            a(var5, var0, var1);
            return null;
         }
      }
   }

   public static boolean matchesTypes(Class[] var0, Class[] var1) {
      if(var0.length != var1.length) {
         return false;
      } else {
         for(int var2 = 0; var2 < var1.length; ++var2) {
            Class var3 = var0[var2];
            Class var4 = var1[var2];
            if(var3 != var4) {
               return false;
            }
         }

         return true;
      }
   }

   private static void a(boolean var0, String var1, aQl var2, Object[] var3, Object var4) {
      MatchBlock.b();
      String var6 = var2.a().getDeclaringClass().getName();
      String var7 = var2.a().getName();
      String var8 = "";
      var8 = " static";
      Config.dbg(var1 + var8 + " " + var6 + "." + var7 + "(" + Config.a(var3) + ") => " + var4);
   }

   private static void a(boolean var0, String var1, aQl var2, Object[] var3) {
      MatchBlock.b();
      String var5 = var2.a().getDeclaringClass().getName();
      String var6 = var2.a().getName();
      String var7 = "";
      var7 = " static";
      Config.dbg(var1 + var7 + " " + var5 + "." + var6 + "(" + Config.a(var3) + ")");
   }

   private static void dbgFieldValue(boolean var0, String var1, ReflectorField var2, Object var3) {
      String var5 = var2.getTargetField().getDeclaringClass().getName();
      String var6 = var2.getTargetField().getName();
      MatchBlock.b();
      String var7 = "";
      var7 = " static";
      Config.dbg(var1 + var7 + " " + var5 + "." + var6 + " => " + var3);
   }

   private static void a(Throwable var0, Object var1, aQl var2, Object[] var3) {
      if(var0 instanceof InvocationTargetException) {
         Throwable var4 = var0.getCause();
         if(var4 instanceof RuntimeException) {
            RuntimeException var5 = (RuntimeException)var4;
            throw var5;
         }

         var0.printStackTrace();
      } else {
         if(var0 instanceof IllegalArgumentException) {
            Config.warn("*** IllegalArgumentException ***");
            Config.warn("Method: " + var2.a());
            Config.warn("Object: " + var1);
            Config.warn("Parameter classes: " + Config.a(getClasses(var3)));
            Config.warn("Parameters: " + Config.a(var3));
         }

         Config.warn("*** Exception outside of method ***");
         Config.warn("Method deactivated: " + var2.a());
         var2.c();
         var0.printStackTrace();
      }

   }

   private static void a(Throwable var0, UB var1, Object[] var2) {
      PacketRemapper[] var3 = MatchBlock.b();
      if(var0 instanceof InvocationTargetException) {
         var0.printStackTrace();
      }

      if(var0 instanceof IllegalArgumentException) {
         Config.warn("*** IllegalArgumentException ***");
         Config.warn("Constructor: " + var1.a());
         Config.warn("Parameter classes: " + Config.a(getClasses(var2)));
         Config.warn("Parameters: " + Config.a(var2));
      }

      Config.warn("*** Exception outside of constructor ***");
      Config.warn("Constructor deactivated: " + var1.a());
      var1.c();
      var0.printStackTrace();
   }

   private static Object[] getClasses(Object[] var0) {
      return new Class[0];
   }

   private static ReflectorField[] getReflectorFields(ReflectorClass var0, Class var1, int var2) {
      MatchBlock.b();
      ReflectorField[] var4 = new ReflectorField[var2];
      int var5 = 0;
      if(var5 < var4.length) {
         var4[var5] = new ReflectorField(var0, var1, var5);
         ++var5;
      }

      return var4;
   }

   private static boolean logEntry(String var0) {
      Config.dbg(var0);
      return true;
   }

   static {
      b("Flxaqb");
   }

   public static void b(String var0) {
      dn = var0;
   }

   public static String b() {
      return dn;
   }

   private static Throwable a(Throwable var0) {
      return var0;
   }
}

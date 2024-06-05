package optifine;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.renderer.tileentity.RenderItemFrame;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
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
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;

public class Reflector {
   public static ReflectorMethod ForgeBlock_canCreatureSpawn;
   public static ReflectorMethod ForgeHooksClient_orientBedCamera;
   public static ReflectorMethod ForgeHooksClient_getArmorModel;
   public static ReflectorMethod ForgeHooksClient_setRenderPass;
   public static ReflectorClass ForgeWorldProvider;
   public static ReflectorMethod ForgeVertexFormatElementEnumUseage_preDraw;
   public static ReflectorMethod ForgeEntity_shouldRenderInPass;
   public static ReflectorMethod ForgeEventFactory_renderBlockOverlay;
   public static ReflectorMethod IExtendedBlockState_getClean;
   public static ReflectorMethod MinecraftForgeClient_onRebuildChunk;
   public static ReflectorField MinecraftForge_EVENT_BUS;
   public static ReflectorClass ChunkWatchEvent_UnWatch;
   public static ReflectorField EntityViewRenderEvent_CameraSetup_pitch;
   public static ReflectorMethod ForgeItem_getModel;
   public static ReflectorClass IColoredBakedQuad;
   public static ReflectorClass Event_Result;
   public static ReflectorMethod LightUtil_putBakedQuad;
   public static ReflectorMethod ForgeHooks_onLivingUpdate;
   public static ReflectorClass SplashScreen;
   public static ReflectorMethod ForgeItemRecord_getRecordResource;
   public static ReflectorMethod ForgeItem_getDurabilityForDisplay;
   public static ReflectorMethod ForgeHooksClient_renderFirstPersonHand;
   public static ReflectorConstructor RenderLivingEvent_Pre_Constructor;
   public static ReflectorMethod ForgeHooksClient_putQuadColor;
   public static ReflectorConstructor DrawScreenEvent_Pre_Constructor;
   public static ReflectorClass DrawScreenEvent_Pre;
   public static ReflectorClass RenderLivingEvent_Post;
   public static ReflectorMethod ForgeHooks_onLivingDeath;
   public static ReflectorField Event_Result_DEFAULT;
   public static ReflectorMethod ForgeHooks_onLivingHurt;
   public static ReflectorConstructor EntityViewRenderEvent_CameraSetup_Constructor;
   public static ReflectorMethod ForgeTileEntityRendererDispatcher_drawBatch;
   public static ReflectorField ForgeEntity_capturedDrops;
   public static ReflectorField EntityViewRenderEvent_FogColors_green;
   public static ReflectorMethod ModelLoader_onRegisterItems;
   public static ReflectorMethod ForgeHooksClient_drawScreen;
   public static ReflectorMethod EventBus_post;
   public static ReflectorMethod FMLClientHandler_trackBrokenTexture;
   public static ReflectorField LightUtil_tessellator;
   public static ReflectorMethod ISmartBlockModel_handleBlockState;
   public static ReflectorClass ISmartBlockModel;
   public static ReflectorClass RenderItemInFrameEvent;
   public static ReflectorConstructor ChunkWatchEvent_UnWatch_Constructor;
   public static ReflectorField EntityViewRenderEvent_FogColors_blue;
   public static ReflectorClass MinecraftForgeClient;
   public static ReflectorClass RenderLivingEvent_Pre;
   public static ReflectorClass ItemModelMesherForge;
   public static ReflectorMethod ForgeHooks_onLivingSetAttackTarget;
   public static ReflectorClass FMLCommonHandler;
   public static ReflectorField Launch_blackboard;
   public static ReflectorMethod RenderingRegistry_loadEntityRenderers;
   public static ReflectorField ForgeModContainer_forgeLightPipelineEnabled;
   public static ReflectorClass ForgePotionEffect;
   public static ReflectorField EntityViewRenderEvent_CameraSetup_roll;
   public static ReflectorMethod ForgeWorldProvider_getWeatherRenderer;
   public static ReflectorClass Launch;
   public static ReflectorMethod ForgeHooksClient_setRenderLayer;
   public static ReflectorMethod MinecraftForgeClient_getRenderPass;
   public static ReflectorField EntityViewRenderEvent_CameraSetup_yaw;
   public static ReflectorClass ForgeVertexFormatElementEnumUseage;
   public static ReflectorClass ForgeWorld;
   public static ReflectorMethod ForgeItem_onEntitySwing;
   public static ReflectorMethod ForgeTileEntity_canRenderBreaking;
   public static ReflectorMethod ForgeBiomeGenBase_getWaterColorMultiplier;
   public static ReflectorClass ForgeEventFactory;
   public static ReflectorMethod ForgeBlock_isBedFoot;
   public static ReflectorMethod ForgeTileEntity_shouldRenderInPass;
   public static ReflectorClass ForgeBiomeGenBase;
   public static ReflectorClass RenderingRegistry;
   public static ReflectorClass FMLClientHandler;
   public static ReflectorClass CoreModManager;
   public static ReflectorField LightUtil_itemConsumer;
   public static ReflectorField Event_Result_DENY;
   public static ReflectorMethod FMLCommonHandler_instance;
   public static ReflectorMethod ForgeHooks_onLivingFall;
   public static ReflectorClass IRenderHandler;
   public static ReflectorMethod ForgeHooksClient_getMatrix;
   public static ReflectorClass ForgeBlock;
   public static ReflectorClass IExtendedBlockState;
   public static ReflectorClass ForgeModContainer;
   public static ReflectorMethod ForgeHooks_onLivingAttack;
   public static ReflectorMethod ForgeEventFactory_canEntitySpawn;
   public static ReflectorConstructor RenderLivingEvent_Specials_Pre_Constructor;
   public static ReflectorMethod ForgeEventFactory_renderFireOverlay;
   public static ReflectorMethod ForgeHooks_onLivingJump;
   public static ReflectorClass MinecraftForge = new ReflectorClass("net.minecraftforge.common.MinecraftForge");
   public static ReflectorField OptiFineClassTransformer_instance;
   public static ReflectorField Attributes_DEFAULT_BAKED_FORMAT;
   public static ReflectorField ForgeEntity_captureDrops;
   public static ReflectorClass OptiFineClassTransformer;
   public static ReflectorMethod ForgeBlock_addDestroyEffects;
   public static ReflectorClass LightUtil;
   public static ReflectorMethod ForgeBlock_getBedDirection;
   public static ReflectorMethod ForgeWorld_getPerWorldStorage;
   public static ReflectorClass DimensionManager;
   public static ReflectorMethod ForgeBlock_isBed;
   public static ReflectorMethod ForgeTileEntity_getRenderBoundingBox;
   public static ReflectorMethod FMLCommonHandler_enhanceCrashReport;
   public static ReflectorConstructor EntityViewRenderEvent_FogColors_Constructor;
   public static ReflectorClass ForgeItemRecord;
   public static ReflectorMethod ForgeBlock_canRenderInLayer;
   public static ReflectorConstructor ItemModelMesherForge_Constructor;
   public static ReflectorMethod ForgeHooksClient_transform;
   public static ReflectorMethod BlamingTransformer_onCrash;
   public static ReflectorConstructor RenderLivingEvent_Post_Constructor;
   public static ReflectorMethod ForgeHooksClient_getOffsetFOV;
   public static ReflectorMethod OptiFineClassTransformer_getOptiFineResource;
   public static ReflectorMethod ForgeBlock_addHitEffects;
   public static ReflectorMethod ForgeBlock_getExtendedState;
   public static ReflectorMethod ForgeHooksClient_onFogRender;
   public static ReflectorClass RenderBlockOverlayEvent_OverlayType;
   public static ReflectorConstructor RenderLivingEvent_Specials_Post_Constructor;
   public static ReflectorConstructor DrawScreenEvent_Post_Constructor;
   public static ReflectorClass RenderLivingEvent_Specials_Post;
   public static ReflectorMethod FMLCommonHandler_getBrandings;
   public static ReflectorMethod ForgeHooksClient_onDrawBlockHighlight;
   public static ReflectorMethod ForgeEventFactory_canEntityDespawn;
   public static ReflectorClass BlamingTransformer;
   public static ReflectorMethod ForgeHooksClient_handleCameraTransforms;
   public static ReflectorConstructor WorldEvent_Load_Constructor;
   public static ReflectorMethod ForgeHooksClient_dispatchRenderLast;
   public static ReflectorMethod ForgeHooksClient_loadEntityShader;
   public static ReflectorConstructor RenderItemInFrameEvent_Constructor;
   public static ReflectorMethod CoreModManager_onCrash;
   public static ReflectorClass RenderLivingEvent_Specials_Pre;
   public static ReflectorMethod ForgeHooksClient_onTextureStitchedPost;
   public static ReflectorMethod ForgeTileEntityRendererDispatcher_preDrawBatch;
   public static ReflectorField RenderBlockOverlayEvent_OverlayType_BLOCK;
   public static ReflectorMethod ForgeWorldProvider_getSkyRenderer;
   public static ReflectorMethod ForgeVertexFormatElementEnumUseage_postDraw;
   public static ReflectorMethod FMLClientHandler_instance;
   public static ReflectorClass ForgeHooks;
   public static ReflectorMethod DimensionManager_getStaticDimensionIDs;
   public static ReflectorMethod ForgeEntity_shouldRiderSit;
   public static ReflectorClass ForgeItem;
   public static ReflectorClass DrawScreenEvent_Post;
   public static ReflectorMethod FMLCommonHandler_callFuture;
   public static ReflectorMethod ForgeHooks_onLivingDrops;
   public static ReflectorMethod ForgeItem_showDurabilityBar;
   public static ReflectorClass ModelLoader;
   public static ReflectorMethod IRenderHandler_render;
   public static ReflectorMethod ForgeEventFactory_renderWaterOverlay;
   public static ReflectorClass BetterFoliageClient;
   public static ReflectorMethod FMLClientHandler_isLoading;
   public static ReflectorClass ForgeTileEntityRendererDispatcher;
   public static ReflectorMethod ForgeHooksClient_getFogDensity;
   public static ReflectorClass ForgeHooksClient;
   public static ReflectorMethod ForgeWorld_countEntities;
   public static ReflectorMethod FMLCommonHandler_handleServerAboutToStart;
   public static ReflectorClass EntityViewRenderEvent_FogColors;
   public static ReflectorClass WorldEvent_Load;
   public static ReflectorMethod FMLClientHandler_trackMissingTexture;
   public static ReflectorClass ForgeTileEntity;
   public static ReflectorMethod ForgeBlock_isAir;
   public static ReflectorField Event_Result_ALLOW;
   public static ReflectorMethod LightUtil_renderQuadColor;
   public static ReflectorMethod ForgeHooksClient_fillNormal;
   public static ReflectorMethod ForgeHooksClient_getArmorTexture;
   public static ReflectorMethod ForgeEntity_canRiderInteract;
   public static ReflectorClass EventBus;
   public static ReflectorMethod ForgeBlock_hasTileEntity;
   public static ReflectorClass ForgeEntity;
   public static ReflectorMethod FMLCommonHandler_handleServerStarting;
   public static ReflectorMethod ForgeItem_shouldCauseReequipAnimation;
   public static ReflectorClass Attributes;
   public static ReflectorMethod ForgeHooksClient_onTextureStitchedPre;
   public static ReflectorMethod ForgeWorldProvider_getCloudRenderer;
   public static ReflectorClass EntityViewRenderEvent_CameraSetup;
   public static ReflectorField EntityViewRenderEvent_FogColors_red;
   public static ReflectorMethod ForgePotionEffect_isCurativeItem;

   private static void dbgCall(boolean var0, String var1, ReflectorMethod var2, Object[] var3, Object var4) {
      String var5 = var2.getTargetMethod().getDeclaringClass().getName();
      String var6 = var2.getTargetMethod().getName();
      String var7 = "";
      if (var0) {
         var7 = " static";
      }

      Config.dbg(String.valueOf((new StringBuilder(String.valueOf(var1))).append(var7).append(" ").append(var5).append(".").append(var6).append("(").append(Config.arrayToString(var3)).append(") => ").append(var4)));
   }

   public static Field[] getFields(Class var0, Class var1) {
      ArrayList var2 = new ArrayList();

      try {
         Field[] var3 = var0.getDeclaredFields();

         for(int var4 = 0; var4 < var3.length; ++var4) {
            Field var5 = var3[var4];
            if (var5.getType() == var1) {
               var5.setAccessible(true);
               var2.add(var5);
            }
         }

         Field[] var7 = (Field[])var2.toArray(new Field[var2.size()]);
         return var7;
      } catch (Exception var6) {
         return null;
      }
   }

   public static boolean postForgeBusEvent(ReflectorConstructor var0, Object... var1) {
      Object var2 = newInstance(var0, var1);
      return var2 == null ? false : postForgeBusEvent(var2);
   }

   private static void dbgCallVoid(boolean var0, String var1, ReflectorMethod var2, Object[] var3) {
      String var4 = var2.getTargetMethod().getDeclaringClass().getName();
      String var5 = var2.getTargetMethod().getName();
      String var6 = "";
      if (var0) {
         var6 = " static";
      }

      Config.dbg(String.valueOf((new StringBuilder(String.valueOf(var1))).append(var6).append(" ").append(var4).append(".").append(var5).append("(").append(Config.arrayToString(var3)).append(")")));
   }

   public static String callString(ReflectorMethod var0, Object... var1) {
      try {
         Method var2 = var0.getTargetMethod();
         if (var2 == null) {
            return null;
         } else {
            String var3 = (String)var2.invoke((Object)null, var1);
            return var3;
         }
      } catch (Throwable var4) {
         handleException(var4, (Object)null, var0, var1);
         return null;
      }
   }

   public static float callFloat(Object var0, ReflectorMethod var1, Object... var2) {
      try {
         Method var3 = var1.getTargetMethod();
         if (var3 == null) {
            return 0.0F;
         } else {
            Float var4 = (Float)var3.invoke(var0, var2);
            return var4;
         }
      } catch (Throwable var5) {
         handleException(var5, var0, var1, var2);
         return 0.0F;
      }
   }

   public static double callDouble(Object var0, ReflectorMethod var1, Object... var2) {
      try {
         Method var3 = var1.getTargetMethod();
         if (var3 == null) {
            return 0.0D;
         } else {
            Double var4 = (Double)var3.invoke(var0, var2);
            return var4;
         }
      } catch (Throwable var5) {
         handleException(var5, var0, var1, var2);
         return 0.0D;
      }
   }

   public static Method[] getMethods(Class var0, String var1) {
      ArrayList var2 = new ArrayList();
      Method[] var3 = var0.getDeclaredMethods();

      for(int var4 = 0; var4 < var3.length; ++var4) {
         Method var5 = var3[var4];
         if (var5.getName().equals(var1)) {
            var2.add(var5);
         }
      }

      Method[] var6 = (Method[])var2.toArray(new Method[var2.size()]);
      return var6;
   }

   private static void handleException(Throwable var0, Object var1, ReflectorMethod var2, Object[] var3) {
      if (var0 instanceof InvocationTargetException) {
         var0.printStackTrace();
      } else {
         if (var0 instanceof IllegalArgumentException) {
            Config.warn("*** IllegalArgumentException ***");
            Config.warn(String.valueOf((new StringBuilder("Method: ")).append(var2.getTargetMethod())));
            Config.warn(String.valueOf((new StringBuilder("Object: ")).append(var1)));
            Config.warn(String.valueOf((new StringBuilder("Parameter classes: ")).append(Config.arrayToString(getClasses(var3)))));
            Config.warn(String.valueOf((new StringBuilder("Parameters: ")).append(Config.arrayToString(var3))));
         }

         Config.warn("*** Exception outside of method ***");
         Config.warn(String.valueOf((new StringBuilder("Method deactivated: ")).append(var2.getTargetMethod())));
         var2.deactivate();
         var0.printStackTrace();
      }

   }

   private static void handleException(Throwable var0, ReflectorConstructor var1, Object[] var2) {
      if (var0 instanceof InvocationTargetException) {
         var0.printStackTrace();
      } else {
         if (var0 instanceof IllegalArgumentException) {
            Config.warn("*** IllegalArgumentException ***");
            Config.warn(String.valueOf((new StringBuilder("Constructor: ")).append(var1.getTargetConstructor())));
            Config.warn(String.valueOf((new StringBuilder("Parameter classes: ")).append(Config.arrayToString(getClasses(var2)))));
            Config.warn(String.valueOf((new StringBuilder("Parameters: ")).append(Config.arrayToString(var2))));
         }

         Config.warn("*** Exception outside of constructor ***");
         Config.warn(String.valueOf((new StringBuilder("Constructor deactivated: ")).append(var1.getTargetConstructor())));
         var1.deactivate();
         var0.printStackTrace();
      }

   }

   public static boolean matchesTypes(Class[] var0, Class[] var1) {
      if (var0.length != var1.length) {
         return false;
      } else {
         for(int var2 = 0; var2 < var1.length; ++var2) {
            Class var3 = var0[var2];
            Class var4 = var1[var2];
            if (var3 != var4) {
               return false;
            }
         }

         return true;
      }
   }

   public static boolean postForgeBusEvent(Object var0) {
      if (var0 == null) {
         return false;
      } else {
         Object var1 = getFieldValue(MinecraftForge_EVENT_BUS);
         if (var1 == null) {
            return false;
         } else {
            Object var2 = call(var1, EventBus_post, var0);
            if (!(var2 instanceof Boolean)) {
               return false;
            } else {
               Boolean var3 = (Boolean)var2;
               return var3;
            }
         }
      }
   }

   private static void dbgFieldValue(boolean var0, String var1, ReflectorField var2, Object var3) {
      String var4 = var2.getTargetField().getDeclaringClass().getName();
      String var5 = var2.getTargetField().getName();
      String var6 = "";
      if (var0) {
         var6 = " static";
      }

      Config.dbg(String.valueOf((new StringBuilder(String.valueOf(var1))).append(var6).append(" ").append(var4).append(".").append(var5).append(" => ").append(var3)));
   }

   public static void setFieldValue(Object var0, ReflectorField var1, Object var2) {
      try {
         Field var3 = var1.getTargetField();
         if (var3 == null) {
            return;
         }

         var3.set(var0, var2);
      } catch (Throwable var4) {
         var4.printStackTrace();
      }

   }

   public static float getFieldValueFloat(Object var0, ReflectorField var1, float var2) {
      Object var3 = getFieldValue(var0, var1);
      if (!(var3 instanceof Float)) {
         return var2;
      } else {
         Float var4 = (Float)var3;
         return var4;
      }
   }

   public static boolean callBoolean(ReflectorMethod var0, Object... var1) {
      try {
         Method var2 = var0.getTargetMethod();
         if (var2 == null) {
            return false;
         } else {
            Boolean var3 = (Boolean)var2.invoke((Object)null, var1);
            return var3;
         }
      } catch (Throwable var4) {
         handleException(var4, (Object)null, var0, var1);
         return false;
      }
   }

   public static Object newInstance(ReflectorConstructor var0, Object... var1) {
      Constructor var2 = var0.getTargetConstructor();
      if (var2 == null) {
         return null;
      } else {
         try {
            Object var3 = var2.newInstance(var1);
            return var3;
         } catch (Throwable var4) {
            handleException(var4, var0, var1);
            return null;
         }
      }
   }

   public static Object call(Object var0, ReflectorMethod var1, Object... var2) {
      try {
         Method var3 = var1.getTargetMethod();
         if (var3 == null) {
            return null;
         } else {
            Object var4 = var3.invoke(var0, var2);
            return var4;
         }
      } catch (Throwable var5) {
         handleException(var5, var0, var1, var2);
         return null;
      }
   }

   public static int callInt(Object var0, ReflectorMethod var1, Object... var2) {
      try {
         Method var3 = var1.getTargetMethod();
         if (var3 == null) {
            return 0;
         } else {
            Integer var4 = (Integer)var3.invoke(var0, var2);
            return var4;
         }
      } catch (Throwable var5) {
         handleException(var5, var0, var1, var2);
         return 0;
      }
   }

   public static void setFieldValue(ReflectorField var0, Object var1) {
      setFieldValue((Object)null, var0, var1);
   }

   public static int callInt(ReflectorMethod var0, Object... var1) {
      try {
         Method var2 = var0.getTargetMethod();
         if (var2 == null) {
            return 0;
         } else {
            Integer var3 = (Integer)var2.invoke((Object)null, var1);
            return var3;
         }
      } catch (Throwable var4) {
         handleException(var4, (Object)null, var0, var1);
         return 0;
      }
   }

   public static float callFloat(ReflectorMethod var0, Object... var1) {
      try {
         Method var2 = var0.getTargetMethod();
         if (var2 == null) {
            return 0.0F;
         } else {
            Float var3 = (Float)var2.invoke((Object)null, var1);
            return var3;
         }
      } catch (Throwable var4) {
         handleException(var4, (Object)null, var0, var1);
         return 0.0F;
      }
   }

   public static void callVoid(Object var0, ReflectorMethod var1, Object... var2) {
      try {
         if (var0 == null) {
            return;
         }

         Method var3 = var1.getTargetMethod();
         if (var3 == null) {
            return;
         }

         var3.invoke(var0, var2);
      } catch (Throwable var4) {
         handleException(var4, var0, var1, var2);
      }

   }

   public static String callString(Object var0, ReflectorMethod var1, Object... var2) {
      try {
         Method var3 = var1.getTargetMethod();
         if (var3 == null) {
            return null;
         } else {
            String var4 = (String)var3.invoke(var0, var2);
            return var4;
         }
      } catch (Throwable var5) {
         handleException(var5, var0, var1, var2);
         return null;
      }
   }

   public static double callDouble(ReflectorMethod var0, Object... var1) {
      try {
         Method var2 = var0.getTargetMethod();
         if (var2 == null) {
            return 0.0D;
         } else {
            Double var3 = (Double)var2.invoke((Object)null, var1);
            return var3;
         }
      } catch (Throwable var4) {
         handleException(var4, (Object)null, var0, var1);
         return 0.0D;
      }
   }

   private static Object[] getClasses(Object[] var0) {
      if (var0 == null) {
         return new Class[0];
      } else {
         Class[] var1 = new Class[var0.length];

         for(int var2 = 0; var2 < var1.length; ++var2) {
            Object var3 = var0[var2];
            if (var3 != null) {
               var1[var2] = var3.getClass();
            }
         }

         return var1;
      }
   }

   public static void callVoid(ReflectorMethod var0, Object... var1) {
      try {
         Method var2 = var0.getTargetMethod();
         if (var2 == null) {
            return;
         }

         var2.invoke((Object)null, var1);
      } catch (Throwable var3) {
         handleException(var3, (Object)null, var0, var1);
      }

   }

   public static boolean callBoolean(Object var0, ReflectorMethod var1, Object... var2) {
      try {
         Method var3 = var1.getTargetMethod();
         if (var3 == null) {
            return false;
         } else {
            Boolean var4 = (Boolean)var3.invoke(var0, var2);
            return var4;
         }
      } catch (Throwable var5) {
         handleException(var5, var0, var1, var2);
         return false;
      }
   }

   public static Method getMethod(Class var0, String var1, Class[] var2) {
      Method[] var3 = var0.getDeclaredMethods();

      for(int var4 = 0; var4 < var3.length; ++var4) {
         Method var5 = var3[var4];
         if (var5.getName().equals(var1)) {
            Class[] var6 = var5.getParameterTypes();
            if (matchesTypes(var2, var6)) {
               return var5;
            }
         }
      }

      return null;
   }

   static {
      MinecraftForge_EVENT_BUS = new ReflectorField(MinecraftForge, "EVENT_BUS");
      ForgeHooks = new ReflectorClass("net.minecraftforge.common.ForgeHooks");
      ForgeHooks_onLivingSetAttackTarget = new ReflectorMethod(ForgeHooks, "onLivingSetAttackTarget");
      ForgeHooks_onLivingUpdate = new ReflectorMethod(ForgeHooks, "onLivingUpdate");
      ForgeHooks_onLivingAttack = new ReflectorMethod(ForgeHooks, "onLivingAttack");
      ForgeHooks_onLivingHurt = new ReflectorMethod(ForgeHooks, "onLivingHurt");
      ForgeHooks_onLivingDeath = new ReflectorMethod(ForgeHooks, "onLivingDeath");
      ForgeHooks_onLivingDrops = new ReflectorMethod(ForgeHooks, "onLivingDrops");
      ForgeHooks_onLivingFall = new ReflectorMethod(ForgeHooks, "onLivingFall");
      ForgeHooks_onLivingJump = new ReflectorMethod(ForgeHooks, "onLivingJump");
      MinecraftForgeClient = new ReflectorClass("net.minecraftforge.client.MinecraftForgeClient");
      MinecraftForgeClient_getRenderPass = new ReflectorMethod(MinecraftForgeClient, "getRenderPass");
      MinecraftForgeClient_onRebuildChunk = new ReflectorMethod(MinecraftForgeClient, "onRebuildChunk");
      ForgeHooksClient = new ReflectorClass("net.minecraftforge.client.ForgeHooksClient");
      ForgeHooksClient_onDrawBlockHighlight = new ReflectorMethod(ForgeHooksClient, "onDrawBlockHighlight");
      ForgeHooksClient_orientBedCamera = new ReflectorMethod(ForgeHooksClient, "orientBedCamera");
      ForgeHooksClient_dispatchRenderLast = new ReflectorMethod(ForgeHooksClient, "dispatchRenderLast");
      ForgeHooksClient_setRenderPass = new ReflectorMethod(ForgeHooksClient, "setRenderPass");
      ForgeHooksClient_onTextureStitchedPre = new ReflectorMethod(ForgeHooksClient, "onTextureStitchedPre");
      ForgeHooksClient_onTextureStitchedPost = new ReflectorMethod(ForgeHooksClient, "onTextureStitchedPost");
      ForgeHooksClient_renderFirstPersonHand = new ReflectorMethod(ForgeHooksClient, "renderFirstPersonHand");
      ForgeHooksClient_getOffsetFOV = new ReflectorMethod(ForgeHooksClient, "getOffsetFOV");
      ForgeHooksClient_drawScreen = new ReflectorMethod(ForgeHooksClient, "drawScreen");
      ForgeHooksClient_onFogRender = new ReflectorMethod(ForgeHooksClient, "onFogRender");
      ForgeHooksClient_setRenderLayer = new ReflectorMethod(ForgeHooksClient, "setRenderLayer");
      ForgeHooksClient_transform = new ReflectorMethod(ForgeHooksClient, "transform");
      ForgeHooksClient_getMatrix = new ReflectorMethod(ForgeHooksClient, "getMatrix", new Class[]{ModelRotation.class});
      ForgeHooksClient_fillNormal = new ReflectorMethod(ForgeHooksClient, "fillNormal");
      ForgeHooksClient_handleCameraTransforms = new ReflectorMethod(ForgeHooksClient, "handleCameraTransforms");
      ForgeHooksClient_getArmorModel = new ReflectorMethod(ForgeHooksClient, "getArmorModel");
      ForgeHooksClient_getArmorTexture = new ReflectorMethod(ForgeHooksClient, "getArmorTexture");
      ForgeHooksClient_putQuadColor = new ReflectorMethod(ForgeHooksClient, "putQuadColor");
      ForgeHooksClient_loadEntityShader = new ReflectorMethod(ForgeHooksClient, "loadEntityShader");
      ForgeHooksClient_getFogDensity = new ReflectorMethod(ForgeHooksClient, "getFogDensity");
      FMLCommonHandler = new ReflectorClass("net.minecraftforge.fml.common.FMLCommonHandler");
      FMLCommonHandler_instance = new ReflectorMethod(FMLCommonHandler, "instance");
      FMLCommonHandler_handleServerStarting = new ReflectorMethod(FMLCommonHandler, "handleServerStarting");
      FMLCommonHandler_handleServerAboutToStart = new ReflectorMethod(FMLCommonHandler, "handleServerAboutToStart");
      FMLCommonHandler_enhanceCrashReport = new ReflectorMethod(FMLCommonHandler, "enhanceCrashReport");
      FMLCommonHandler_getBrandings = new ReflectorMethod(FMLCommonHandler, "getBrandings");
      FMLCommonHandler_callFuture = new ReflectorMethod(FMLCommonHandler, "callFuture");
      FMLClientHandler = new ReflectorClass("net.minecraftforge.fml.client.FMLClientHandler");
      FMLClientHandler_instance = new ReflectorMethod(FMLClientHandler, "instance");
      FMLClientHandler_isLoading = new ReflectorMethod(FMLClientHandler, "isLoading");
      FMLClientHandler_trackBrokenTexture = new ReflectorMethod(FMLClientHandler, "trackBrokenTexture");
      FMLClientHandler_trackMissingTexture = new ReflectorMethod(FMLClientHandler, "trackMissingTexture");
      ForgeWorldProvider = new ReflectorClass(WorldProvider.class);
      ForgeWorldProvider_getSkyRenderer = new ReflectorMethod(ForgeWorldProvider, "getSkyRenderer");
      ForgeWorldProvider_getCloudRenderer = new ReflectorMethod(ForgeWorldProvider, "getCloudRenderer");
      ForgeWorldProvider_getWeatherRenderer = new ReflectorMethod(ForgeWorldProvider, "getWeatherRenderer");
      ForgeWorld = new ReflectorClass(World.class);
      ForgeWorld_countEntities = new ReflectorMethod(ForgeWorld, "countEntities", new Class[]{EnumCreatureType.class, Boolean.TYPE});
      ForgeWorld_getPerWorldStorage = new ReflectorMethod(ForgeWorld, "getPerWorldStorage");
      IRenderHandler = new ReflectorClass("net.minecraftforge.client.IRenderHandler");
      IRenderHandler_render = new ReflectorMethod(IRenderHandler, "render");
      DimensionManager = new ReflectorClass("net.minecraftforge.common.DimensionManager");
      DimensionManager_getStaticDimensionIDs = new ReflectorMethod(DimensionManager, "getStaticDimensionIDs");
      WorldEvent_Load = new ReflectorClass("net.minecraftforge.event.world.WorldEvent$Load");
      WorldEvent_Load_Constructor = new ReflectorConstructor(WorldEvent_Load, new Class[]{World.class});
      RenderItemInFrameEvent = new ReflectorClass("net.minecraftforge.client.event.RenderItemInFrameEvent");
      RenderItemInFrameEvent_Constructor = new ReflectorConstructor(RenderItemInFrameEvent, new Class[]{EntityItemFrame.class, RenderItemFrame.class});
      DrawScreenEvent_Pre = new ReflectorClass("net.minecraftforge.client.event.GuiScreenEvent$DrawScreenEvent$Pre");
      DrawScreenEvent_Pre_Constructor = new ReflectorConstructor(DrawScreenEvent_Pre, new Class[]{GuiScreen.class, Integer.TYPE, Integer.TYPE, Float.TYPE});
      DrawScreenEvent_Post = new ReflectorClass("net.minecraftforge.client.event.GuiScreenEvent$DrawScreenEvent$Post");
      DrawScreenEvent_Post_Constructor = new ReflectorConstructor(DrawScreenEvent_Post, new Class[]{GuiScreen.class, Integer.TYPE, Integer.TYPE, Float.TYPE});
      EntityViewRenderEvent_FogColors = new ReflectorClass("net.minecraftforge.client.event.EntityViewRenderEvent$FogColors");
      EntityViewRenderEvent_FogColors_Constructor = new ReflectorConstructor(EntityViewRenderEvent_FogColors, new Class[]{EntityRenderer.class, Entity.class, Block.class, Double.TYPE, Float.TYPE, Float.TYPE, Float.TYPE});
      EntityViewRenderEvent_FogColors_red = new ReflectorField(EntityViewRenderEvent_FogColors, "red");
      EntityViewRenderEvent_FogColors_green = new ReflectorField(EntityViewRenderEvent_FogColors, "green");
      EntityViewRenderEvent_FogColors_blue = new ReflectorField(EntityViewRenderEvent_FogColors, "blue");
      EntityViewRenderEvent_CameraSetup = new ReflectorClass("net.minecraftforge.client.event.EntityViewRenderEvent$CameraSetup");
      EntityViewRenderEvent_CameraSetup_Constructor = new ReflectorConstructor(EntityViewRenderEvent_CameraSetup, new Class[]{EntityRenderer.class, Entity.class, Block.class, Double.TYPE, Float.TYPE, Float.TYPE, Float.TYPE});
      EntityViewRenderEvent_CameraSetup_yaw = new ReflectorField(EntityViewRenderEvent_CameraSetup, "yaw");
      EntityViewRenderEvent_CameraSetup_pitch = new ReflectorField(EntityViewRenderEvent_CameraSetup, "pitch");
      EntityViewRenderEvent_CameraSetup_roll = new ReflectorField(EntityViewRenderEvent_CameraSetup, "roll");
      RenderLivingEvent_Pre = new ReflectorClass("net.minecraftforge.client.event.RenderLivingEvent$Pre");
      RenderLivingEvent_Pre_Constructor = new ReflectorConstructor(RenderLivingEvent_Pre, new Class[]{EntityLivingBase.class, RendererLivingEntity.class, Double.TYPE, Double.TYPE, Double.TYPE});
      RenderLivingEvent_Post = new ReflectorClass("net.minecraftforge.client.event.RenderLivingEvent$Post");
      RenderLivingEvent_Post_Constructor = new ReflectorConstructor(RenderLivingEvent_Post, new Class[]{EntityLivingBase.class, RendererLivingEntity.class, Double.TYPE, Double.TYPE, Double.TYPE});
      RenderLivingEvent_Specials_Pre = new ReflectorClass("net.minecraftforge.client.event.RenderLivingEvent$Specials$Pre");
      RenderLivingEvent_Specials_Pre_Constructor = new ReflectorConstructor(RenderLivingEvent_Specials_Pre, new Class[]{EntityLivingBase.class, RendererLivingEntity.class, Double.TYPE, Double.TYPE, Double.TYPE});
      RenderLivingEvent_Specials_Post = new ReflectorClass("net.minecraftforge.client.event.RenderLivingEvent$Specials$Post");
      RenderLivingEvent_Specials_Post_Constructor = new ReflectorConstructor(RenderLivingEvent_Specials_Post, new Class[]{EntityLivingBase.class, RendererLivingEntity.class, Double.TYPE, Double.TYPE, Double.TYPE});
      EventBus = new ReflectorClass("net.minecraftforge.fml.common.eventhandler.EventBus");
      EventBus_post = new ReflectorMethod(EventBus, "post");
      Event_Result = new ReflectorClass("net.minecraftforge.fml.common.eventhandler.Event$Result");
      Event_Result_DENY = new ReflectorField(Event_Result, "DENY");
      Event_Result_ALLOW = new ReflectorField(Event_Result, "ALLOW");
      Event_Result_DEFAULT = new ReflectorField(Event_Result, "DEFAULT");
      ForgeEventFactory = new ReflectorClass("net.minecraftforge.event.ForgeEventFactory");
      ForgeEventFactory_canEntitySpawn = new ReflectorMethod(ForgeEventFactory, "canEntitySpawn");
      ForgeEventFactory_canEntityDespawn = new ReflectorMethod(ForgeEventFactory, "canEntityDespawn");
      ForgeEventFactory_renderBlockOverlay = new ReflectorMethod(ForgeEventFactory, "renderBlockOverlay");
      ForgeEventFactory_renderWaterOverlay = new ReflectorMethod(ForgeEventFactory, "renderWaterOverlay");
      ForgeEventFactory_renderFireOverlay = new ReflectorMethod(ForgeEventFactory, "renderFireOverlay");
      RenderBlockOverlayEvent_OverlayType = new ReflectorClass("net.minecraftforge.client.event.RenderBlockOverlayEvent$OverlayType");
      RenderBlockOverlayEvent_OverlayType_BLOCK = new ReflectorField(RenderBlockOverlayEvent_OverlayType, "BLOCK");
      ChunkWatchEvent_UnWatch = new ReflectorClass("net.minecraftforge.event.world.ChunkWatchEvent$UnWatch");
      ChunkWatchEvent_UnWatch_Constructor = new ReflectorConstructor(ChunkWatchEvent_UnWatch, new Class[]{ChunkCoordIntPair.class, EntityPlayerMP.class});
      ForgeBlock = new ReflectorClass(Block.class);
      ForgeBlock_getBedDirection = new ReflectorMethod(ForgeBlock, "getBedDirection");
      ForgeBlock_isBed = new ReflectorMethod(ForgeBlock, "isBed");
      ForgeBlock_isBedFoot = new ReflectorMethod(ForgeBlock, "isBedFoot");
      ForgeBlock_hasTileEntity = new ReflectorMethod(ForgeBlock, "hasTileEntity", new Class[]{IBlockState.class});
      ForgeBlock_canCreatureSpawn = new ReflectorMethod(ForgeBlock, "canCreatureSpawn");
      ForgeBlock_addHitEffects = new ReflectorMethod(ForgeBlock, "addHitEffects");
      ForgeBlock_addDestroyEffects = new ReflectorMethod(ForgeBlock, "addDestroyEffects");
      ForgeBlock_isAir = new ReflectorMethod(ForgeBlock, "isAir");
      ForgeBlock_canRenderInLayer = new ReflectorMethod(ForgeBlock, "canRenderInLayer");
      ForgeBlock_getExtendedState = new ReflectorMethod(ForgeBlock, "getExtendedState");
      ForgeEntity = new ReflectorClass(Entity.class);
      ForgeEntity_captureDrops = new ReflectorField(ForgeEntity, "captureDrops");
      ForgeEntity_capturedDrops = new ReflectorField(ForgeEntity, "capturedDrops");
      ForgeEntity_shouldRenderInPass = new ReflectorMethod(ForgeEntity, "shouldRenderInPass");
      ForgeEntity_canRiderInteract = new ReflectorMethod(ForgeEntity, "canRiderInteract");
      ForgeEntity_shouldRiderSit = new ReflectorMethod(ForgeEntity, "shouldRiderSit");
      ForgeTileEntity = new ReflectorClass(TileEntity.class);
      ForgeTileEntity_shouldRenderInPass = new ReflectorMethod(ForgeTileEntity, "shouldRenderInPass");
      ForgeTileEntity_getRenderBoundingBox = new ReflectorMethod(ForgeTileEntity, "getRenderBoundingBox");
      ForgeTileEntity_canRenderBreaking = new ReflectorMethod(ForgeTileEntity, "canRenderBreaking");
      ForgeItem = new ReflectorClass(Item.class);
      ForgeItem_onEntitySwing = new ReflectorMethod(ForgeItem, "onEntitySwing");
      ForgeItem_shouldCauseReequipAnimation = new ReflectorMethod(ForgeItem, "shouldCauseReequipAnimation");
      ForgeItem_getModel = new ReflectorMethod(ForgeItem, "getModel");
      ForgeItem_showDurabilityBar = new ReflectorMethod(ForgeItem, "showDurabilityBar");
      ForgeItem_getDurabilityForDisplay = new ReflectorMethod(ForgeItem, "getDurabilityForDisplay");
      ForgePotionEffect = new ReflectorClass(PotionEffect.class);
      ForgePotionEffect_isCurativeItem = new ReflectorMethod(ForgePotionEffect, "isCurativeItem");
      ForgeItemRecord = new ReflectorClass(ItemRecord.class);
      ForgeItemRecord_getRecordResource = new ReflectorMethod(ForgeItemRecord, "getRecordResource", new Class[]{String.class});
      ForgeVertexFormatElementEnumUseage = new ReflectorClass(VertexFormatElement.EnumUseage.class);
      ForgeVertexFormatElementEnumUseage_preDraw = new ReflectorMethod(ForgeVertexFormatElementEnumUseage, "preDraw");
      ForgeVertexFormatElementEnumUseage_postDraw = new ReflectorMethod(ForgeVertexFormatElementEnumUseage, "postDraw");
      BlamingTransformer = new ReflectorClass("net.minecraftforge.fml.common.asm.transformers.BlamingTransformer");
      BlamingTransformer_onCrash = new ReflectorMethod(BlamingTransformer, "onCrash");
      CoreModManager = new ReflectorClass("net.minecraftforge.fml.relauncher.CoreModManager");
      CoreModManager_onCrash = new ReflectorMethod(CoreModManager, "onCrash");
      ISmartBlockModel = new ReflectorClass("net.minecraftforge.client.model.ISmartBlockModel");
      ISmartBlockModel_handleBlockState = new ReflectorMethod(ISmartBlockModel, "handleBlockState");
      Launch = new ReflectorClass("net.minecraft.launchwrapper.Launch");
      Launch_blackboard = new ReflectorField(Launch, "blackboard");
      SplashScreen = new ReflectorClass("net.minecraftforge.fml.client.SplashProgress");
      LightUtil = new ReflectorClass("net.minecraftforge.client.model.pipeline.LightUtil");
      LightUtil_tessellator = new ReflectorField(LightUtil, "tessellator");
      LightUtil_itemConsumer = new ReflectorField(LightUtil, "itemConsumer");
      LightUtil_putBakedQuad = new ReflectorMethod(LightUtil, "putBakedQuad");
      LightUtil_renderQuadColor = new ReflectorMethod(LightUtil, "renderQuadColor");
      IExtendedBlockState = new ReflectorClass("net.minecraftforge.common.property.IExtendedBlockState");
      IExtendedBlockState_getClean = new ReflectorMethod(IExtendedBlockState, "getClean");
      ItemModelMesherForge = new ReflectorClass("net.minecraftforge.client.ItemModelMesherForge");
      ItemModelMesherForge_Constructor = new ReflectorConstructor(ItemModelMesherForge, new Class[]{ModelManager.class});
      ModelLoader = new ReflectorClass("net.minecraftforge.client.model.ModelLoader");
      ModelLoader_onRegisterItems = new ReflectorMethod(ModelLoader, "onRegisterItems");
      Attributes = new ReflectorClass("net.minecraftforge.client.model.Attributes");
      Attributes_DEFAULT_BAKED_FORMAT = new ReflectorField(Attributes, "DEFAULT_BAKED_FORMAT");
      BetterFoliageClient = new ReflectorClass("mods.betterfoliage.client.BetterFoliageClient");
      IColoredBakedQuad = new ReflectorClass("net.minecraftforge.client.model.IColoredBakedQuad");
      ForgeBiomeGenBase = new ReflectorClass(BiomeGenBase.class);
      ForgeBiomeGenBase_getWaterColorMultiplier = new ReflectorMethod(ForgeBiomeGenBase, "getWaterColorMultiplier");
      RenderingRegistry = new ReflectorClass("net.minecraftforge.fml.client.registry.RenderingRegistry");
      RenderingRegistry_loadEntityRenderers = new ReflectorMethod(RenderingRegistry, "loadEntityRenderers", new Class[]{RenderManager.class, Map.class});
      ForgeTileEntityRendererDispatcher = new ReflectorClass(TileEntityRendererDispatcher.class);
      ForgeTileEntityRendererDispatcher_preDrawBatch = new ReflectorMethod(ForgeTileEntityRendererDispatcher, "preDrawBatch");
      ForgeTileEntityRendererDispatcher_drawBatch = new ReflectorMethod(ForgeTileEntityRendererDispatcher, "drawBatch");
      OptiFineClassTransformer = new ReflectorClass("optifine.OptiFineClassTransformer");
      OptiFineClassTransformer_instance = new ReflectorField(OptiFineClassTransformer, "instance");
      OptiFineClassTransformer_getOptiFineResource = new ReflectorMethod(OptiFineClassTransformer, "getOptiFineResource");
      ForgeModContainer = new ReflectorClass("net.minecraftforge.common.ForgeModContainer");
      ForgeModContainer_forgeLightPipelineEnabled = new ReflectorField(ForgeModContainer, "forgeLightPipelineEnabled");
   }

   public static Object getFieldValue(ReflectorField var0) {
      return getFieldValue((Object)null, var0);
   }

   public static Object getFieldValue(Object var0, ReflectorField var1) {
      try {
         Field var2 = var1.getTargetField();
         if (var2 == null) {
            return null;
         } else {
            Object var3 = var2.get(var0);
            return var3;
         }
      } catch (Throwable var4) {
         var4.printStackTrace();
         return null;
      }
   }

   public static Object call(ReflectorMethod var0, Object... var1) {
      try {
         Method var2 = var0.getTargetMethod();
         if (var2 == null) {
            return null;
         } else {
            Object var3 = var2.invoke((Object)null, var1);
            return var3;
         }
      } catch (Throwable var4) {
         handleException(var4, (Object)null, var0, var1);
         return null;
      }
   }

   public static Field getField(Class var0, Class var1) {
      try {
         Field[] var2 = var0.getDeclaredFields();

         for(int var3 = 0; var3 < var2.length; ++var3) {
            Field var4 = var2[var3];
            if (var4.getType() == var1) {
               var4.setAccessible(true);
               return var4;
            }
         }

         return null;
      } catch (Exception var5) {
         return null;
      }
   }
}

package net.minecraft.src;

import java.lang.reflect.*;

public class Reflector
{
    public static ReflectorClass ModLoader;
    public static ReflectorMethod ModLoader_renderWorldBlock;
    public static ReflectorMethod ModLoader_renderInvBlock;
    public static ReflectorMethod ModLoader_renderBlockIsItemFull3D;
    public static ReflectorClass FMLRenderAccessLibrary;
    public static ReflectorMethod FMLRenderAccessLibrary_renderWorldBlock;
    public static ReflectorMethod FMLRenderAccessLibrary_renderInventoryBlock;
    public static ReflectorMethod FMLRenderAccessLibrary_renderItemAsFull3DBlock;
    public static ReflectorClass LightCache;
    public static ReflectorField LightCache_cache;
    public static ReflectorMethod LightCache_clear;
    public static ReflectorClass BlockCoord;
    public static ReflectorMethod BlockCoord_resetPool;
    public static ReflectorClass MinecraftForge;
    public static ReflectorField MinecraftForge_EVENT_BUS;
    public static ReflectorClass ForgeHooks;
    public static ReflectorMethod ForgeHooks_onLivingSetAttackTarget;
    public static ReflectorMethod ForgeHooks_onLivingUpdate;
    public static ReflectorMethod ForgeHooks_onLivingAttack;
    public static ReflectorMethod ForgeHooks_onLivingHurt;
    public static ReflectorMethod ForgeHooks_onLivingDeath;
    public static ReflectorMethod ForgeHooks_onLivingDrops;
    public static ReflectorMethod ForgeHooks_onLivingFall;
    public static ReflectorMethod ForgeHooks_onLivingJump;
    public static ReflectorMethod ForgeHooks_isLivingOnLadder;
    public static ReflectorClass ForgeHooksClient;
    public static ReflectorMethod ForgeHooksClient_onDrawBlockHighlight;
    public static ReflectorMethod ForgeHooksClient_orientBedCamera;
    public static ReflectorMethod ForgeHooksClient_dispatchRenderLast;
    public static ReflectorMethod ForgeHooksClient_onTextureLoadPre;
    public static ReflectorMethod ForgeHooksClient_onTextureLoad;
    public static ReflectorMethod ForgeHooksClient_setRenderPass;
    public static ReflectorMethod ForgeHooksClient_onTextureStitchedPre;
    public static ReflectorMethod ForgeHooksClient_onTextureStitchedPost;
    public static ReflectorClass FMLCommonHandler;
    public static ReflectorMethod FMLCommonHandler_instance;
    public static ReflectorMethod FMLCommonHandler_handleServerStarting;
    public static ReflectorMethod FMLCommonHandler_handleServerAboutToStart;
    public static ReflectorClass FMLClientHandler;
    public static ReflectorMethod FMLClientHandler_instance;
    public static ReflectorMethod FMLClientHandler_isLoading;
    public static ReflectorClass ForgeWorldProvider;
    public static ReflectorMethod ForgeWorldProvider_getSkyRenderer;
    public static ReflectorMethod ForgeWorldProvider_getCloudRenderer;
    public static ReflectorClass IRenderHandler;
    public static ReflectorMethod IRenderHandler_render;
    public static ReflectorClass DimensionManager;
    public static ReflectorMethod DimensionManager_getStaticDimensionIDs;
    public static ReflectorClass WorldEvent_Load;
    public static ReflectorConstructor WorldEvent_Load_Constructor;
    public static ReflectorClass EventBus;
    public static ReflectorMethod EventBus_post;
    public static ReflectorClass ChunkWatchEvent_UnWatch;
    public static ReflectorConstructor ChunkWatchEvent_UnWatch_Constructor;
    public static ReflectorClass ForgeBlock;
    public static ReflectorMethod ForgeBlock_getBedDirection;
    public static ReflectorMethod ForgeBlock_isBedFoot;
    public static ReflectorMethod ForgeBlock_canRenderInPass;
    public static ReflectorClass ForgeEntity;
    public static ReflectorField ForgeEntity_captureDrops;
    public static ReflectorField ForgeEntity_capturedDrops;
    public static ReflectorClass ForgeItem;
    public static ReflectorMethod ForgeItem_onEntitySwing;
    public static ReflectorClass ForgePotionEffect;
    public static ReflectorMethod ForgePotionEffect_isCurativeItem;
    
    static {
        Reflector.ModLoader = new ReflectorClass("ModLoader");
        Reflector.ModLoader_renderWorldBlock = new ReflectorMethod(Reflector.ModLoader, "renderWorldBlock");
        Reflector.ModLoader_renderInvBlock = new ReflectorMethod(Reflector.ModLoader, "renderInvBlock");
        Reflector.ModLoader_renderBlockIsItemFull3D = new ReflectorMethod(Reflector.ModLoader, "renderBlockIsItemFull3D");
        Reflector.FMLRenderAccessLibrary = new ReflectorClass("FMLRenderAccessLibrary");
        Reflector.FMLRenderAccessLibrary_renderWorldBlock = new ReflectorMethod(Reflector.FMLRenderAccessLibrary, "renderWorldBlock");
        Reflector.FMLRenderAccessLibrary_renderInventoryBlock = new ReflectorMethod(Reflector.FMLRenderAccessLibrary, "renderInventoryBlock");
        Reflector.FMLRenderAccessLibrary_renderItemAsFull3DBlock = new ReflectorMethod(Reflector.FMLRenderAccessLibrary, "renderItemAsFull3DBlock");
        Reflector.LightCache = new ReflectorClass("LightCache");
        Reflector.LightCache_cache = new ReflectorField(Reflector.LightCache, "cache");
        Reflector.LightCache_clear = new ReflectorMethod(Reflector.LightCache, "clear");
        Reflector.BlockCoord = new ReflectorClass("BlockCoord");
        Reflector.BlockCoord_resetPool = new ReflectorMethod(Reflector.BlockCoord, "resetPool");
        Reflector.MinecraftForge = new ReflectorClass("net.minecraftforge.common.MinecraftForge");
        Reflector.MinecraftForge_EVENT_BUS = new ReflectorField(Reflector.MinecraftForge, "EVENT_BUS");
        Reflector.ForgeHooks = new ReflectorClass("net.minecraftforge.common.ForgeHooks");
        Reflector.ForgeHooks_onLivingSetAttackTarget = new ReflectorMethod(Reflector.ForgeHooks, "onLivingSetAttackTarget");
        Reflector.ForgeHooks_onLivingUpdate = new ReflectorMethod(Reflector.ForgeHooks, "onLivingUpdate");
        Reflector.ForgeHooks_onLivingAttack = new ReflectorMethod(Reflector.ForgeHooks, "onLivingAttack");
        Reflector.ForgeHooks_onLivingHurt = new ReflectorMethod(Reflector.ForgeHooks, "onLivingHurt");
        Reflector.ForgeHooks_onLivingDeath = new ReflectorMethod(Reflector.ForgeHooks, "onLivingDeath");
        Reflector.ForgeHooks_onLivingDrops = new ReflectorMethod(Reflector.ForgeHooks, "onLivingDrops");
        Reflector.ForgeHooks_onLivingFall = new ReflectorMethod(Reflector.ForgeHooks, "onLivingFall");
        Reflector.ForgeHooks_onLivingJump = new ReflectorMethod(Reflector.ForgeHooks, "onLivingJump");
        Reflector.ForgeHooks_isLivingOnLadder = new ReflectorMethod(Reflector.ForgeHooks, "isLivingOnLadder");
        Reflector.ForgeHooksClient = new ReflectorClass("net.minecraftforge.client.ForgeHooksClient");
        Reflector.ForgeHooksClient_onDrawBlockHighlight = new ReflectorMethod(Reflector.ForgeHooksClient, "onDrawBlockHighlight");
        Reflector.ForgeHooksClient_orientBedCamera = new ReflectorMethod(Reflector.ForgeHooksClient, "orientBedCamera");
        Reflector.ForgeHooksClient_dispatchRenderLast = new ReflectorMethod(Reflector.ForgeHooksClient, "dispatchRenderLast");
        Reflector.ForgeHooksClient_onTextureLoadPre = new ReflectorMethod(Reflector.ForgeHooksClient, "onTextureLoadPre");
        Reflector.ForgeHooksClient_onTextureLoad = new ReflectorMethod(Reflector.ForgeHooksClient, "onTextureLoad");
        Reflector.ForgeHooksClient_setRenderPass = new ReflectorMethod(Reflector.ForgeHooksClient, "setRenderPass");
        Reflector.ForgeHooksClient_onTextureStitchedPre = new ReflectorMethod(Reflector.ForgeHooksClient, "onTextureStitchedPre");
        Reflector.ForgeHooksClient_onTextureStitchedPost = new ReflectorMethod(Reflector.ForgeHooksClient, "onTextureStitchedPost");
        Reflector.FMLCommonHandler = new ReflectorClass("cpw.mods.fml.common.FMLCommonHandler");
        Reflector.FMLCommonHandler_instance = new ReflectorMethod(Reflector.FMLCommonHandler, "instance");
        Reflector.FMLCommonHandler_handleServerStarting = new ReflectorMethod(Reflector.FMLCommonHandler, "handleServerStarting");
        Reflector.FMLCommonHandler_handleServerAboutToStart = new ReflectorMethod(Reflector.FMLCommonHandler, "handleServerAboutToStart");
        Reflector.FMLClientHandler = new ReflectorClass("cpw.mods.fml.client.FMLClientHandler");
        Reflector.FMLClientHandler_instance = new ReflectorMethod(Reflector.FMLClientHandler, "instance");
        Reflector.FMLClientHandler_isLoading = new ReflectorMethod(Reflector.FMLClientHandler, "isLoading");
        Reflector.ForgeWorldProvider = new ReflectorClass(WorldProvider.class);
        Reflector.ForgeWorldProvider_getSkyRenderer = new ReflectorMethod(Reflector.ForgeWorldProvider, "getSkyRenderer");
        Reflector.ForgeWorldProvider_getCloudRenderer = new ReflectorMethod(Reflector.ForgeWorldProvider, "getCloudRenderer");
        Reflector.IRenderHandler = new ReflectorClass("net.minecraftforge.client.IRenderHandler");
        Reflector.IRenderHandler_render = new ReflectorMethod(Reflector.IRenderHandler, "render");
        Reflector.DimensionManager = new ReflectorClass("net.minecraftforge.common.DimensionManager");
        Reflector.DimensionManager_getStaticDimensionIDs = new ReflectorMethod(Reflector.DimensionManager, "getStaticDimensionIDs");
        Reflector.WorldEvent_Load = new ReflectorClass("net.minecraftforge.event.world.WorldEvent$Load");
        Reflector.WorldEvent_Load_Constructor = new ReflectorConstructor(Reflector.WorldEvent_Load, new Class[] { World.class });
        Reflector.EventBus = new ReflectorClass("net.minecraftforge.event.EventBus");
        Reflector.EventBus_post = new ReflectorMethod(Reflector.EventBus, "post");
        Reflector.ChunkWatchEvent_UnWatch = new ReflectorClass("net.minecraftforge.event.world.ChunkWatchEvent$UnWatch");
        Reflector.ChunkWatchEvent_UnWatch_Constructor = new ReflectorConstructor(Reflector.ChunkWatchEvent_UnWatch, new Class[] { ChunkCoordIntPair.class, EntityPlayerMP.class });
        Reflector.ForgeBlock = new ReflectorClass(Block.class);
        Reflector.ForgeBlock_getBedDirection = new ReflectorMethod(Reflector.ForgeBlock, "getBedDirection");
        Reflector.ForgeBlock_isBedFoot = new ReflectorMethod(Reflector.ForgeBlock, "isBedFoot");
        Reflector.ForgeBlock_canRenderInPass = new ReflectorMethod(Reflector.ForgeBlock, "canRenderInPass");
        Reflector.ForgeEntity = new ReflectorClass(Entity.class);
        Reflector.ForgeEntity_captureDrops = new ReflectorField(Reflector.ForgeEntity, "captureDrops");
        Reflector.ForgeEntity_capturedDrops = new ReflectorField(Reflector.ForgeEntity, "capturedDrops");
        Reflector.ForgeItem = new ReflectorClass(Item.class);
        Reflector.ForgeItem_onEntitySwing = new ReflectorMethod(Reflector.ForgeItem, "onEntitySwing");
        Reflector.ForgePotionEffect = new ReflectorClass(PotionEffect.class);
        Reflector.ForgePotionEffect_isCurativeItem = new ReflectorMethod(Reflector.ForgePotionEffect, "isCurativeItem");
    }
    
    public static void callVoid(final ReflectorMethod var0, final Object... var1) {
        try {
            final Method var2 = var0.getTargetMethod();
            if (var2 == null) {
                return;
            }
            var2.invoke(null, var1);
        }
        catch (Throwable var3) {
            handleException(var3, null, var0, var1);
        }
    }
    
    public static boolean callBoolean(final ReflectorMethod var0, final Object... var1) {
        try {
            final Method var2 = var0.getTargetMethod();
            if (var2 == null) {
                return false;
            }
            final Boolean var3 = (Boolean)var2.invoke(null, var1);
            return var3;
        }
        catch (Throwable var4) {
            handleException(var4, null, var0, var1);
            return false;
        }
    }
    
    public static int callInt(final ReflectorMethod var0, final Object... var1) {
        try {
            final Method var2 = var0.getTargetMethod();
            if (var2 == null) {
                return 0;
            }
            final Integer var3 = (Integer)var2.invoke(null, var1);
            return var3;
        }
        catch (Throwable var4) {
            handleException(var4, null, var0, var1);
            return 0;
        }
    }
    
    public static float callFloat(final ReflectorMethod var0, final Object... var1) {
        try {
            final Method var2 = var0.getTargetMethod();
            if (var2 == null) {
                return 0.0f;
            }
            final Float var3 = (Float)var2.invoke(null, var1);
            return var3;
        }
        catch (Throwable var4) {
            handleException(var4, null, var0, var1);
            return 0.0f;
        }
    }
    
    public static String callString(final ReflectorMethod var0, final Object... var1) {
        try {
            final Method var2 = var0.getTargetMethod();
            if (var2 == null) {
                return null;
            }
            final String var3 = (String)var2.invoke(null, var1);
            return var3;
        }
        catch (Throwable var4) {
            handleException(var4, null, var0, var1);
            return null;
        }
    }
    
    public static Object call(final ReflectorMethod var0, final Object... var1) {
        try {
            final Method var2 = var0.getTargetMethod();
            if (var2 == null) {
                return null;
            }
            final Object var3 = var2.invoke(null, var1);
            return var3;
        }
        catch (Throwable var4) {
            handleException(var4, null, var0, var1);
            return null;
        }
    }
    
    public static void callVoid(final Object var0, final ReflectorMethod var1, final Object... var2) {
        try {
            if (var0 == null) {
                return;
            }
            final Method var3 = var1.getTargetMethod();
            if (var3 == null) {
                return;
            }
            var3.invoke(var0, var2);
        }
        catch (Throwable var4) {
            handleException(var4, var0, var1, var2);
        }
    }
    
    public static boolean callBoolean(final Object var0, final ReflectorMethod var1, final Object... var2) {
        try {
            final Method var3 = var1.getTargetMethod();
            if (var3 == null) {
                return false;
            }
            final Boolean var4 = (Boolean)var3.invoke(var0, var2);
            return var4;
        }
        catch (Throwable var5) {
            handleException(var5, var0, var1, var2);
            return false;
        }
    }
    
    public static int callInt(final Object var0, final ReflectorMethod var1, final Object... var2) {
        try {
            final Method var3 = var1.getTargetMethod();
            if (var3 == null) {
                return 0;
            }
            final Integer var4 = (Integer)var3.invoke(var0, var2);
            return var4;
        }
        catch (Throwable var5) {
            handleException(var5, var0, var1, var2);
            return 0;
        }
    }
    
    public static float callFloat(final Object var0, final ReflectorMethod var1, final Object... var2) {
        try {
            final Method var3 = var1.getTargetMethod();
            if (var3 == null) {
                return 0.0f;
            }
            final Float var4 = (Float)var3.invoke(var0, var2);
            return var4;
        }
        catch (Throwable var5) {
            handleException(var5, var0, var1, var2);
            return 0.0f;
        }
    }
    
    public static String callString(final Object var0, final ReflectorMethod var1, final Object... var2) {
        try {
            final Method var3 = var1.getTargetMethod();
            if (var3 == null) {
                return null;
            }
            final String var4 = (String)var3.invoke(var0, var2);
            return var4;
        }
        catch (Throwable var5) {
            handleException(var5, var0, var1, var2);
            return null;
        }
    }
    
    public static Object call(final Object var0, final ReflectorMethod var1, final Object... var2) {
        try {
            final Method var3 = var1.getTargetMethod();
            if (var3 == null) {
                return null;
            }
            final Object var4 = var3.invoke(var0, var2);
            return var4;
        }
        catch (Throwable var5) {
            handleException(var5, var0, var1, var2);
            return null;
        }
    }
    
    public static Object getFieldValue(final ReflectorField var0) {
        return getFieldValue(null, var0);
    }
    
    public static Object getFieldValue(final Object var0, final ReflectorField var1) {
        try {
            final Field var2 = var1.getTargetField();
            if (var2 == null) {
                return null;
            }
            final Object var3 = var2.get(var0);
            return var3;
        }
        catch (Throwable var4) {
            var4.printStackTrace();
            return null;
        }
    }
    
    public static void setFieldValue(final ReflectorField var0, final Object var1) {
        setFieldValue(null, var0, var1);
    }
    
    public static void setFieldValue(final Object var0, final ReflectorField var1, final Object var2) {
        try {
            final Field var3 = var1.getTargetField();
            if (var3 == null) {
                return;
            }
            var3.set(var0, var2);
        }
        catch (Throwable var4) {
            var4.printStackTrace();
        }
    }
    
    public static void postForgeBusEvent(final ReflectorConstructor var0, final Object... var1) {
        try {
            final Object var2 = getFieldValue(Reflector.MinecraftForge_EVENT_BUS);
            if (var2 == null) {
                return;
            }
            final Constructor var3 = var0.getTargetConstructor();
            if (var3 == null) {
                return;
            }
            final Object var4 = var3.newInstance(var1);
            callVoid(var2, Reflector.EventBus_post, var4);
        }
        catch (Throwable var5) {
            var5.printStackTrace();
        }
    }
    
    public static boolean matchesTypes(final Class[] var0, final Class[] var1) {
        if (var0.length != var1.length) {
            return false;
        }
        for (int var2 = 0; var2 < var1.length; ++var2) {
            final Class var3 = var0[var2];
            final Class var4 = var1[var2];
            if (var3 != var4) {
                return false;
            }
        }
        return true;
    }
    
    private static void dbgCall(final boolean var0, final String var1, final ReflectorMethod var2, final Object[] var3, final Object var4) {
        final String var5 = var2.getTargetMethod().getDeclaringClass().getName();
        final String var6 = var2.getTargetMethod().getName();
        String var7 = "";
        if (var0) {
            var7 = " static";
        }
        Config.dbg(String.valueOf(var1) + var7 + " " + var5 + "." + var6 + "(" + Config.arrayToString(var3) + ") => " + var4);
    }
    
    private static void dbgCallVoid(final boolean var0, final String var1, final ReflectorMethod var2, final Object[] var3) {
        final String var4 = var2.getTargetMethod().getDeclaringClass().getName();
        final String var5 = var2.getTargetMethod().getName();
        String var6 = "";
        if (var0) {
            var6 = " static";
        }
        Config.dbg(String.valueOf(var1) + var6 + " " + var4 + "." + var5 + "(" + Config.arrayToString(var3) + ")");
    }
    
    private static void dbgFieldValue(final boolean var0, final String var1, final ReflectorField var2, final Object var3) {
        final String var4 = var2.getTargetField().getDeclaringClass().getName();
        final String var5 = var2.getTargetField().getName();
        String var6 = "";
        if (var0) {
            var6 = " static";
        }
        Config.dbg(String.valueOf(var1) + var6 + " " + var4 + "." + var5 + " => " + var3);
    }
    
    private static void handleException(final Throwable var0, final Object var1, final ReflectorMethod var2, final Object[] var3) {
        if (var0 instanceof InvocationTargetException) {
            var0.printStackTrace();
        }
        else {
            if (var0 instanceof IllegalArgumentException) {
                Config.dbg("*** IllegalArgumentException ***");
                Config.dbg("Method: " + var2.getTargetMethod());
                Config.dbg("Object: " + var1);
                Config.dbg("Parameter classes: " + Config.arrayToString(getClasses(var3)));
                Config.dbg("Parameters: " + Config.arrayToString(var3));
            }
            Config.dbg("*** Exception outside of method ***");
            Config.dbg("Method deactivated: " + var2.getTargetMethod());
            var2.deactivate();
            var0.printStackTrace();
        }
    }
    
    private static Object[] getClasses(final Object[] var0) {
        if (var0 == null) {
            return new Class[0];
        }
        final Class[] var = new Class[var0.length];
        for (int var2 = 0; var2 < var.length; ++var2) {
            final Object var3 = var0[var2];
            if (var3 != null) {
                var[var2] = var3.getClass();
            }
        }
        return var;
    }
}

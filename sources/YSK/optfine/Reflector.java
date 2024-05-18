package optfine;

import java.util.*;
import java.lang.reflect.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.state.*;
import net.minecraft.tileentity.*;
import net.minecraft.potion.*;
import net.minecraft.item.*;
import net.minecraft.client.renderer.vertex.*;

public class Reflector
{
    public static ReflectorClass IRenderHandler;
    public static ReflectorClass FMLCommonHandler;
    public static ReflectorClass ForgeHooksClient;
    public static ReflectorMethod ForgeWorldProvider_getCloudRenderer;
    public static ReflectorField Event_Result_DENY;
    public static ReflectorMethod MinecraftForgeClient_getRenderPass;
    public static ReflectorConstructor WorldEvent_Load_Constructor;
    public static ReflectorClass BlockCoord;
    public static ReflectorMethod FMLRenderAccessLibrary_renderWorldBlock;
    public static ReflectorMethod ForgeBlock_canRenderInLayer;
    public static ReflectorClass ForgeBlock;
    public static ReflectorMethod FMLCommonHandler_enhanceCrashReport;
    public static ReflectorClass ForgeEntity;
    public static ReflectorConstructor DrawScreenEvent_Pre_Constructor;
    public static ReflectorMethod ForgeWorld_countEntities;
    public static ReflectorMethod ModLoader_getCustomAnimationLogic;
    public static ReflectorClass DrawScreenEvent_Post;
    public static ReflectorMethod ForgeHooksClient_onTextureStitchedPost;
    public static ReflectorMethod ForgeItemRecord_getRecordResource;
    public static ReflectorClass EntityViewRenderEvent_FogDensity;
    public static ReflectorMethod ForgeWorldProvider_getSkyRenderer;
    public static ReflectorMethod FMLCommonHandler_instance;
    public static ReflectorClass ForgeHooks;
    public static ReflectorMethod ForgeHooks_onLivingDrops;
    public static ReflectorClass ForgePotionEffect;
    public static ReflectorMethod ForgeItem_onEntitySwing;
    public static ReflectorField MinecraftForge_EVENT_BUS;
    public static ReflectorClass EntityViewRenderEvent_FogColors;
    public static ReflectorMethod FMLClientHandler_instance;
    public static ReflectorClass WorldEvent_Load;
    public static ReflectorMethod ForgeBlock_isBedFoot;
    public static ReflectorMethod EventBus_post;
    public static ReflectorMethod ForgeHooksClient_drawScreen;
    public static ReflectorMethod FMLClientHandler_isLoading;
    public static ReflectorMethod ForgeVertexFormatElementEnumUseage_preDraw;
    public static ReflectorMethod ForgeHooks_onLivingJump;
    public static ReflectorConstructor EntityViewRenderEvent_RenderFogEvent_Constructor;
    public static ReflectorClass Event_Result;
    public static ReflectorMethod LightCache_clear;
    public static ReflectorMethod FMLCommonHandler_handleServerStarting;
    public static ReflectorMethod ModLoader_renderBlockIsItemFull3D;
    public static ReflectorMethod FMLCommonHandler_getBrandings;
    public static ReflectorClass ItemRenderType;
    public static ReflectorMethod ForgeTileEntity_getRenderBoundingBox;
    public static ReflectorMethod ForgeTileEntity_shouldRenderInPass;
    public static ReflectorConstructor ChunkWatchEvent_UnWatch_Constructor;
    public static ReflectorMethod ModLoader_renderInvBlock;
    public static ReflectorMethod ForgeEventFactory_canEntityDespawn;
    public static ReflectorMethod FMLCommonHandler_handleServerAboutToStart;
    public static ReflectorField EntityViewRenderEvent_FogColors_green;
    public static ReflectorMethod ForgeHooksClient_onTextureStitchedPre;
    public static ReflectorClass DimensionManager;
    public static ReflectorClass FMLRenderAccessLibrary;
    public static ReflectorMethod ForgeHooksClient_setRenderPass;
    public static ReflectorMethod ForgeHooksClient_dispatchRenderLast;
    public static ReflectorMethod ForgeBlock_hasTileEntity;
    public static ReflectorMethod ForgeHooksClient_getOffsetFOV;
    public static ReflectorMethod ForgeBlock_isAir;
    public static ReflectorField ForgeEntity_captureDrops;
    public static ReflectorMethod ForgeEntity_shouldRenderInPass;
    public static ReflectorMethod MinecraftForgeClient_getItemRenderer;
    public static ReflectorClass ForgeVertexFormatElementEnumUseage;
    public static ReflectorMethod FMLRenderAccessLibrary_renderInventoryBlock;
    public static ReflectorMethod ModLoader_renderWorldBlock;
    public static ReflectorMethod ForgeHooks_onLivingSetAttackTarget;
    public static ReflectorMethod ForgeHooksClient_orientBedCamera;
    public static ReflectorField Event_Result_ALLOW;
    public static ReflectorMethod ForgeVertexFormatElementEnumUseage_postDraw;
    public static ReflectorMethod ForgeTileEntity_canRenderBreaking;
    public static ReflectorMethod ForgeWorldProvider_getWeatherRenderer;
    private static final String[] I;
    public static ReflectorField EntityViewRenderEvent_FogColors_blue;
    public static ReflectorClass ForgeEventFactory;
    public static ReflectorMethod ForgeHooks_onLivingAttack;
    public static ReflectorMethod ForgeHooks_onLivingFall;
    public static ReflectorMethod ForgeHooksClient_renderFirstPersonHand;
    public static ReflectorField EntityViewRenderEvent_FogDensity_density;
    public static ReflectorClass MinecraftForge;
    public static ReflectorClass LightCache;
    public static ReflectorMethod ForgeHooks_onLivingHurt;
    public static ReflectorConstructor DrawScreenEvent_Post_Constructor;
    public static ReflectorConstructor EntityViewRenderEvent_FogDensity_Constructor;
    public static ReflectorClass ForgeWorldProvider;
    public static ReflectorMethod ModLoader_registerServer;
    public static ReflectorMethod FMLRenderAccessLibrary_renderItemAsFull3DBlock;
    public static ReflectorMethod ForgeHooksClient_onDrawBlockHighlight;
    public static ReflectorMethod ForgeWorld_getPerWorldStorage;
    public static ReflectorMethod BlockCoord_resetPool;
    public static ReflectorField ForgeEntity_capturedDrops;
    public static ReflectorMethod ForgePotionEffect_isCurativeItem;
    public static ReflectorMethod ForgeBlock_addHitEffects;
    public static ReflectorMethod ForgeHooks_onLivingDeath;
    public static ReflectorClass ForgeTileEntity;
    public static ReflectorMethod ForgeBlock_addDestroyEffects;
    public static ReflectorField ItemRenderType_EQUIPPED;
    public static ReflectorConstructor EntityViewRenderEvent_FogColors_Constructor;
    public static ReflectorClass ForgeItem;
    public static ReflectorMethod ForgeEntity_canRiderInteract;
    public static ReflectorMethod ForgeHooksClient_setRenderLayer;
    public static ReflectorMethod ForgeEventFactory_canEntitySpawn;
    public static ReflectorField LightCache_cache;
    public static ReflectorClass ForgeItemRecord;
    public static ReflectorClass ModLoader;
    public static ReflectorMethod ForgeHooks_onLivingUpdate;
    public static ReflectorClass EventBus;
    public static ReflectorField Event_Result_DEFAULT;
    public static ReflectorClass DrawScreenEvent_Pre;
    public static ReflectorMethod ForgeBlock_canCreatureSpawn;
    public static ReflectorClass ChunkWatchEvent_UnWatch;
    public static ReflectorMethod ForgeHooksClient_onFogRender;
    public static ReflectorClass EntityViewRenderEvent_RenderFogEvent;
    public static ReflectorField EntityViewRenderEvent_FogColors_red;
    public static ReflectorClass ForgeWorld;
    public static ReflectorMethod IRenderHandler_render;
    public static ReflectorClass FMLClientHandler;
    public static ReflectorClass MinecraftForgeClient;
    public static ReflectorMethod DimensionManager_getStaticDimensionIDs;
    public static ReflectorMethod ForgeBlock_getBedDirection;
    
    public static Object getFieldValue(final Object o, final ReflectorField reflectorField) {
        try {
            final Field targetField = reflectorField.getTargetField();
            if (targetField == null) {
                return null;
            }
            return targetField.get(o);
        }
        catch (Throwable t) {
            t.printStackTrace();
            return null;
        }
    }
    
    public static Field getField(final Class clazz, final Class clazz2) {
        try {
            final Field[] declaredFields = clazz.getDeclaredFields();
            int i = "".length();
            "".length();
            if (1 == 2) {
                throw null;
            }
            while (i < declaredFields.length) {
                final Field field = declaredFields[i];
                if (field.getType() == clazz2) {
                    field.setAccessible(" ".length() != 0);
                    return field;
                }
                ++i;
            }
            return null;
        }
        catch (Exception ex) {
            return null;
        }
    }
    
    private static void dbgCall(final boolean b, final String s, final ReflectorMethod reflectorMethod, final Object[] array, final Object o) {
        final String name = reflectorMethod.getTargetMethod().getDeclaringClass().getName();
        final String name2 = reflectorMethod.getTargetMethod().getName();
        String s2 = Reflector.I[0xA0 ^ 0xC5];
        if (b) {
            s2 = Reflector.I[0xC4 ^ 0xA2];
        }
        Config.dbg(String.valueOf(s) + s2 + Reflector.I[0x3B ^ 0x5C] + name + Reflector.I[0x46 ^ 0x2E] + name2 + Reflector.I[0x36 ^ 0x5F] + Config.arrayToString(array) + Reflector.I[0xE3 ^ 0x89] + o);
    }
    
    public static boolean postForgeBusEvent(final ReflectorConstructor reflectorConstructor, final Object... array) {
        final Object instance = newInstance(reflectorConstructor, array);
        int n;
        if (instance == null) {
            n = "".length();
            "".length();
            if (2 < 2) {
                throw null;
            }
        }
        else {
            n = (postForgeBusEvent(instance) ? 1 : 0);
        }
        return n != 0;
    }
    
    public static Object newInstance(final ReflectorConstructor reflectorConstructor, final Object... array) {
        final Constructor targetConstructor = reflectorConstructor.getTargetConstructor();
        if (targetConstructor == null) {
            return null;
        }
        try {
            return targetConstructor.newInstance(array);
        }
        catch (Throwable t) {
            handleException(t, reflectorConstructor, array);
            return null;
        }
    }
    
    public static boolean matchesTypes(final Class[] array, final Class[] array2) {
        if (array.length != array2.length) {
            return "".length() != 0;
        }
        int i = "".length();
        "".length();
        if (2 <= 0) {
            throw null;
        }
        while (i < array2.length) {
            if (array[i] != array2[i]) {
                return "".length() != 0;
            }
            ++i;
        }
        return " ".length() != 0;
    }
    
    public static Object getFieldValue(final ReflectorField reflectorField) {
        return getFieldValue(null, reflectorField);
    }
    
    public static Field[] getFields(final Class clazz, final Class clazz2) {
        final ArrayList<Field> list = new ArrayList<Field>();
        try {
            final Field[] declaredFields = clazz.getDeclaredFields();
            int i = "".length();
            "".length();
            if (2 >= 4) {
                throw null;
            }
            while (i < declaredFields.length) {
                final Field field = declaredFields[i];
                if (field.getType() == clazz2) {
                    field.setAccessible(" ".length() != 0);
                    list.add(field);
                }
                ++i;
            }
            return list.toArray(new Field[list.size()]);
        }
        catch (Exception ex) {
            return null;
        }
    }
    
    public static int callInt(final ReflectorMethod reflectorMethod, final Object... array) {
        try {
            final Method targetMethod = reflectorMethod.getTargetMethod();
            if (targetMethod == null) {
                return "".length();
            }
            return (int)targetMethod.invoke(null, array);
        }
        catch (Throwable t) {
            handleException(t, null, reflectorMethod, array);
            return "".length();
        }
    }
    
    public static float callFloat(final ReflectorMethod reflectorMethod, final Object... array) {
        try {
            final Method targetMethod = reflectorMethod.getTargetMethod();
            if (targetMethod == null) {
                return 0.0f;
            }
            return (float)targetMethod.invoke(null, array);
        }
        catch (Throwable t) {
            handleException(t, null, reflectorMethod, array);
            return 0.0f;
        }
    }
    
    public static boolean callBoolean(final ReflectorMethod reflectorMethod, final Object... array) {
        try {
            final Method targetMethod = reflectorMethod.getTargetMethod();
            if (targetMethod == null) {
                return "".length() != 0;
            }
            return (boolean)targetMethod.invoke(null, array);
        }
        catch (Throwable t) {
            handleException(t, null, reflectorMethod, array);
            return "".length() != 0;
        }
    }
    
    public static String callString(final Object o, final ReflectorMethod reflectorMethod, final Object... array) {
        try {
            final Method targetMethod = reflectorMethod.getTargetMethod();
            if (targetMethod == null) {
                return null;
            }
            return (String)targetMethod.invoke(o, array);
        }
        catch (Throwable t) {
            handleException(t, o, reflectorMethod, array);
            return null;
        }
    }
    
    public static int callInt(final Object o, final ReflectorMethod reflectorMethod, final Object... array) {
        try {
            final Method targetMethod = reflectorMethod.getTargetMethod();
            if (targetMethod == null) {
                return "".length();
            }
            return (int)targetMethod.invoke(o, array);
        }
        catch (Throwable t) {
            handleException(t, o, reflectorMethod, array);
            return "".length();
        }
    }
    
    private static void handleException(final Throwable t, final ReflectorConstructor reflectorConstructor, final Object[] array) {
        if (t instanceof InvocationTargetException) {
            t.printStackTrace();
            "".length();
            if (4 < 2) {
                throw null;
            }
        }
        else {
            if (t instanceof IllegalArgumentException) {
                Config.warn(Reflector.I[0xF2 ^ 0x8F]);
                Config.warn(Reflector.I[0x2A ^ 0x54] + reflectorConstructor.getTargetConstructor());
                Config.warn(Reflector.I[16 + 4 + 103 + 4] + Config.arrayToString(getClasses(array)));
                Config.warn(Reflector.I[116 + 45 - 120 + 87] + Config.arrayToString(array));
            }
            Config.warn(Reflector.I[126 + 119 - 242 + 126]);
            Config.warn(Reflector.I[1 + 91 - 39 + 77] + reflectorConstructor.getTargetConstructor());
            reflectorConstructor.deactivate();
            t.printStackTrace();
        }
    }
    
    public static boolean postForgeBusEvent(final Object o) {
        if (o == null) {
            return "".length() != 0;
        }
        final Object fieldValue = getFieldValue(Reflector.MinecraftForge_EVENT_BUS);
        if (fieldValue == null) {
            return "".length() != 0;
        }
        final Object o2 = fieldValue;
        final ReflectorMethod eventBus_post = Reflector.EventBus_post;
        final Object[] array = new Object[" ".length()];
        array["".length()] = o;
        final Object call = call(o2, eventBus_post, array);
        if (!(call instanceof Boolean)) {
            return "".length() != 0;
        }
        return (boolean)call;
    }
    
    static {
        I();
        Reflector.ModLoader = new ReflectorClass(Reflector.I["".length()]);
        Reflector.ModLoader_renderWorldBlock = new ReflectorMethod(Reflector.ModLoader, Reflector.I[" ".length()]);
        Reflector.ModLoader_renderInvBlock = new ReflectorMethod(Reflector.ModLoader, Reflector.I["  ".length()]);
        Reflector.ModLoader_renderBlockIsItemFull3D = new ReflectorMethod(Reflector.ModLoader, Reflector.I["   ".length()]);
        Reflector.ModLoader_registerServer = new ReflectorMethod(Reflector.ModLoader, Reflector.I[0x50 ^ 0x54]);
        Reflector.ModLoader_getCustomAnimationLogic = new ReflectorMethod(Reflector.ModLoader, Reflector.I[0x6C ^ 0x69]);
        Reflector.FMLRenderAccessLibrary = new ReflectorClass(Reflector.I[0x17 ^ 0x11]);
        Reflector.FMLRenderAccessLibrary_renderWorldBlock = new ReflectorMethod(Reflector.FMLRenderAccessLibrary, Reflector.I[0x4C ^ 0x4B]);
        Reflector.FMLRenderAccessLibrary_renderInventoryBlock = new ReflectorMethod(Reflector.FMLRenderAccessLibrary, Reflector.I[0xA4 ^ 0xAC]);
        Reflector.FMLRenderAccessLibrary_renderItemAsFull3DBlock = new ReflectorMethod(Reflector.FMLRenderAccessLibrary, Reflector.I[0x8F ^ 0x86]);
        Reflector.LightCache = new ReflectorClass(Reflector.I[0x6 ^ 0xC]);
        Reflector.LightCache_cache = new ReflectorField(Reflector.LightCache, Reflector.I[0x6C ^ 0x67]);
        Reflector.LightCache_clear = new ReflectorMethod(Reflector.LightCache, Reflector.I[0x96 ^ 0x9A]);
        Reflector.BlockCoord = new ReflectorClass(Reflector.I[0x7 ^ 0xA]);
        Reflector.BlockCoord_resetPool = new ReflectorMethod(Reflector.BlockCoord, Reflector.I[0x8A ^ 0x84]);
        Reflector.MinecraftForge = new ReflectorClass(Reflector.I[0x1B ^ 0x14]);
        Reflector.MinecraftForge_EVENT_BUS = new ReflectorField(Reflector.MinecraftForge, Reflector.I[0xD1 ^ 0xC1]);
        Reflector.ForgeHooks = new ReflectorClass(Reflector.I[0x89 ^ 0x98]);
        Reflector.ForgeHooks_onLivingSetAttackTarget = new ReflectorMethod(Reflector.ForgeHooks, Reflector.I[0x3D ^ 0x2F]);
        Reflector.ForgeHooks_onLivingUpdate = new ReflectorMethod(Reflector.ForgeHooks, Reflector.I[0x37 ^ 0x24]);
        Reflector.ForgeHooks_onLivingAttack = new ReflectorMethod(Reflector.ForgeHooks, Reflector.I[0x7 ^ 0x13]);
        Reflector.ForgeHooks_onLivingHurt = new ReflectorMethod(Reflector.ForgeHooks, Reflector.I[0xAC ^ 0xB9]);
        Reflector.ForgeHooks_onLivingDeath = new ReflectorMethod(Reflector.ForgeHooks, Reflector.I[0x95 ^ 0x83]);
        Reflector.ForgeHooks_onLivingDrops = new ReflectorMethod(Reflector.ForgeHooks, Reflector.I[0x8D ^ 0x9A]);
        Reflector.ForgeHooks_onLivingFall = new ReflectorMethod(Reflector.ForgeHooks, Reflector.I[0x51 ^ 0x49]);
        Reflector.ForgeHooks_onLivingJump = new ReflectorMethod(Reflector.ForgeHooks, Reflector.I[0xDC ^ 0xC5]);
        Reflector.MinecraftForgeClient = new ReflectorClass(Reflector.I[0x43 ^ 0x59]);
        Reflector.MinecraftForgeClient_getRenderPass = new ReflectorMethod(Reflector.MinecraftForgeClient, Reflector.I[0x90 ^ 0x8B]);
        Reflector.MinecraftForgeClient_getItemRenderer = new ReflectorMethod(Reflector.MinecraftForgeClient, Reflector.I[0x23 ^ 0x3F]);
        Reflector.ForgeHooksClient = new ReflectorClass(Reflector.I[0xBF ^ 0xA2]);
        Reflector.ForgeHooksClient_onDrawBlockHighlight = new ReflectorMethod(Reflector.ForgeHooksClient, Reflector.I[0x55 ^ 0x4B]);
        Reflector.ForgeHooksClient_orientBedCamera = new ReflectorMethod(Reflector.ForgeHooksClient, Reflector.I[0xDA ^ 0xC5]);
        Reflector.ForgeHooksClient_dispatchRenderLast = new ReflectorMethod(Reflector.ForgeHooksClient, Reflector.I[0x2F ^ 0xF]);
        Reflector.ForgeHooksClient_setRenderPass = new ReflectorMethod(Reflector.ForgeHooksClient, Reflector.I[0x52 ^ 0x73]);
        Reflector.ForgeHooksClient_onTextureStitchedPre = new ReflectorMethod(Reflector.ForgeHooksClient, Reflector.I[0xE0 ^ 0xC2]);
        Reflector.ForgeHooksClient_onTextureStitchedPost = new ReflectorMethod(Reflector.ForgeHooksClient, Reflector.I[0x56 ^ 0x75]);
        Reflector.ForgeHooksClient_renderFirstPersonHand = new ReflectorMethod(Reflector.ForgeHooksClient, Reflector.I[0x7C ^ 0x58]);
        Reflector.ForgeHooksClient_getOffsetFOV = new ReflectorMethod(Reflector.ForgeHooksClient, Reflector.I[0x24 ^ 0x1]);
        Reflector.ForgeHooksClient_drawScreen = new ReflectorMethod(Reflector.ForgeHooksClient, Reflector.I[0x16 ^ 0x30]);
        Reflector.ForgeHooksClient_onFogRender = new ReflectorMethod(Reflector.ForgeHooksClient, Reflector.I[0x82 ^ 0xA5]);
        Reflector.ForgeHooksClient_setRenderLayer = new ReflectorMethod(Reflector.ForgeHooksClient, Reflector.I[0xBA ^ 0x92]);
        Reflector.FMLCommonHandler = new ReflectorClass(Reflector.I[0xEE ^ 0xC7]);
        Reflector.FMLCommonHandler_instance = new ReflectorMethod(Reflector.FMLCommonHandler, Reflector.I[0xA5 ^ 0x8F]);
        Reflector.FMLCommonHandler_handleServerStarting = new ReflectorMethod(Reflector.FMLCommonHandler, Reflector.I[0x9A ^ 0xB1]);
        Reflector.FMLCommonHandler_handleServerAboutToStart = new ReflectorMethod(Reflector.FMLCommonHandler, Reflector.I[0x15 ^ 0x39]);
        Reflector.FMLCommonHandler_enhanceCrashReport = new ReflectorMethod(Reflector.FMLCommonHandler, Reflector.I[0x42 ^ 0x6F]);
        Reflector.FMLCommonHandler_getBrandings = new ReflectorMethod(Reflector.FMLCommonHandler, Reflector.I[0x62 ^ 0x4C]);
        Reflector.FMLClientHandler = new ReflectorClass(Reflector.I[0xAD ^ 0x82]);
        Reflector.FMLClientHandler_instance = new ReflectorMethod(Reflector.FMLClientHandler, Reflector.I[0x4C ^ 0x7C]);
        Reflector.FMLClientHandler_isLoading = new ReflectorMethod(Reflector.FMLClientHandler, Reflector.I[0x7B ^ 0x4A]);
        Reflector.ItemRenderType = new ReflectorClass(Reflector.I[0x2E ^ 0x1C]);
        Reflector.ItemRenderType_EQUIPPED = new ReflectorField(Reflector.ItemRenderType, Reflector.I[0x97 ^ 0xA4]);
        Reflector.ForgeWorldProvider = new ReflectorClass(WorldProvider.class);
        Reflector.ForgeWorldProvider_getSkyRenderer = new ReflectorMethod(Reflector.ForgeWorldProvider, Reflector.I[0xAD ^ 0x99]);
        Reflector.ForgeWorldProvider_getCloudRenderer = new ReflectorMethod(Reflector.ForgeWorldProvider, Reflector.I[0x60 ^ 0x55]);
        Reflector.ForgeWorldProvider_getWeatherRenderer = new ReflectorMethod(Reflector.ForgeWorldProvider, Reflector.I[0x7 ^ 0x31]);
        Reflector.ForgeWorld = new ReflectorClass(World.class);
        final ReflectorClass forgeWorld = Reflector.ForgeWorld;
        final String s = Reflector.I[0x75 ^ 0x42];
        final Class[] array = new Class["  ".length()];
        array["".length()] = EnumCreatureType.class;
        array[" ".length()] = Boolean.TYPE;
        Reflector.ForgeWorld_countEntities = new ReflectorMethod(forgeWorld, s, array);
        Reflector.ForgeWorld_getPerWorldStorage = new ReflectorMethod(Reflector.ForgeWorld, Reflector.I[0x97 ^ 0xAF]);
        Reflector.IRenderHandler = new ReflectorClass(Reflector.I[0x1E ^ 0x27]);
        Reflector.IRenderHandler_render = new ReflectorMethod(Reflector.IRenderHandler, Reflector.I[0xA ^ 0x30]);
        Reflector.DimensionManager = new ReflectorClass(Reflector.I[0x59 ^ 0x62]);
        Reflector.DimensionManager_getStaticDimensionIDs = new ReflectorMethod(Reflector.DimensionManager, Reflector.I[0x93 ^ 0xAF]);
        Reflector.WorldEvent_Load = new ReflectorClass(Reflector.I[0x1B ^ 0x26]);
        final ReflectorClass worldEvent_Load = Reflector.WorldEvent_Load;
        final Class[] array2 = new Class[" ".length()];
        array2["".length()] = World.class;
        Reflector.WorldEvent_Load_Constructor = new ReflectorConstructor(worldEvent_Load, array2);
        Reflector.DrawScreenEvent_Pre = new ReflectorClass(Reflector.I[0x45 ^ 0x7B]);
        final ReflectorClass drawScreenEvent_Pre = Reflector.DrawScreenEvent_Pre;
        final Class[] array3 = new Class[0x3E ^ 0x3A];
        array3["".length()] = GuiScreen.class;
        array3[" ".length()] = Integer.TYPE;
        array3["  ".length()] = Integer.TYPE;
        array3["   ".length()] = Float.TYPE;
        Reflector.DrawScreenEvent_Pre_Constructor = new ReflectorConstructor(drawScreenEvent_Pre, array3);
        Reflector.DrawScreenEvent_Post = new ReflectorClass(Reflector.I[0xA8 ^ 0x97]);
        final ReflectorClass drawScreenEvent_Post = Reflector.DrawScreenEvent_Post;
        final Class[] array4 = new Class[0xBE ^ 0xBA];
        array4["".length()] = GuiScreen.class;
        array4[" ".length()] = Integer.TYPE;
        array4["  ".length()] = Integer.TYPE;
        array4["   ".length()] = Float.TYPE;
        Reflector.DrawScreenEvent_Post_Constructor = new ReflectorConstructor(drawScreenEvent_Post, array4);
        Reflector.EntityViewRenderEvent_FogColors = new ReflectorClass(Reflector.I[0x7E ^ 0x3E]);
        final ReflectorClass entityViewRenderEvent_FogColors = Reflector.EntityViewRenderEvent_FogColors;
        final Class[] array5 = new Class[0xC6 ^ 0xC1];
        array5["".length()] = EntityRenderer.class;
        array5[" ".length()] = Entity.class;
        array5["  ".length()] = Block.class;
        array5["   ".length()] = Double.TYPE;
        array5[0x90 ^ 0x94] = Float.TYPE;
        array5[0x34 ^ 0x31] = Float.TYPE;
        array5[0xE ^ 0x8] = Float.TYPE;
        Reflector.EntityViewRenderEvent_FogColors_Constructor = new ReflectorConstructor(entityViewRenderEvent_FogColors, array5);
        Reflector.EntityViewRenderEvent_FogColors_red = new ReflectorField(Reflector.EntityViewRenderEvent_FogColors, Reflector.I[0xDA ^ 0x9B]);
        Reflector.EntityViewRenderEvent_FogColors_green = new ReflectorField(Reflector.EntityViewRenderEvent_FogColors, Reflector.I[0x3E ^ 0x7C]);
        Reflector.EntityViewRenderEvent_FogColors_blue = new ReflectorField(Reflector.EntityViewRenderEvent_FogColors, Reflector.I[0x59 ^ 0x1A]);
        Reflector.EntityViewRenderEvent_FogDensity = new ReflectorClass(Reflector.I[0x14 ^ 0x50]);
        final ReflectorClass entityViewRenderEvent_FogDensity = Reflector.EntityViewRenderEvent_FogDensity;
        final Class[] array6 = new Class[0x9 ^ 0xC];
        array6["".length()] = EntityRenderer.class;
        array6[" ".length()] = Entity.class;
        array6["  ".length()] = Block.class;
        array6["   ".length()] = Double.TYPE;
        array6[0x2 ^ 0x6] = Float.TYPE;
        Reflector.EntityViewRenderEvent_FogDensity_Constructor = new ReflectorConstructor(entityViewRenderEvent_FogDensity, array6);
        Reflector.EntityViewRenderEvent_FogDensity_density = new ReflectorField(Reflector.EntityViewRenderEvent_FogDensity, Reflector.I[0x40 ^ 0x5]);
        Reflector.EntityViewRenderEvent_RenderFogEvent = new ReflectorClass(Reflector.I[0xDF ^ 0x99]);
        final ReflectorClass entityViewRenderEvent_RenderFogEvent = Reflector.EntityViewRenderEvent_RenderFogEvent;
        final Class[] array7 = new Class[0x88 ^ 0x8E];
        array7["".length()] = EntityRenderer.class;
        array7[" ".length()] = Entity.class;
        array7["  ".length()] = Block.class;
        array7["   ".length()] = Double.TYPE;
        array7[0x92 ^ 0x96] = Integer.TYPE;
        array7[0xAA ^ 0xAF] = Float.TYPE;
        Reflector.EntityViewRenderEvent_RenderFogEvent_Constructor = new ReflectorConstructor(entityViewRenderEvent_RenderFogEvent, array7);
        Reflector.EventBus = new ReflectorClass(Reflector.I[0xFB ^ 0xBC]);
        Reflector.EventBus_post = new ReflectorMethod(Reflector.EventBus, Reflector.I[0x2E ^ 0x66]);
        Reflector.Event_Result = new ReflectorClass(Reflector.I[0x0 ^ 0x49]);
        Reflector.Event_Result_DENY = new ReflectorField(Reflector.Event_Result, Reflector.I[0x49 ^ 0x3]);
        Reflector.Event_Result_ALLOW = new ReflectorField(Reflector.Event_Result, Reflector.I[0xDB ^ 0x90]);
        Reflector.Event_Result_DEFAULT = new ReflectorField(Reflector.Event_Result, Reflector.I[0x64 ^ 0x28]);
        Reflector.ForgeEventFactory = new ReflectorClass(Reflector.I[0x54 ^ 0x19]);
        Reflector.ForgeEventFactory_canEntitySpawn = new ReflectorMethod(Reflector.ForgeEventFactory, Reflector.I[0xD2 ^ 0x9C]);
        Reflector.ForgeEventFactory_canEntityDespawn = new ReflectorMethod(Reflector.ForgeEventFactory, Reflector.I[0xE6 ^ 0xA9]);
        Reflector.ChunkWatchEvent_UnWatch = new ReflectorClass(Reflector.I[0x5B ^ 0xB]);
        final ReflectorClass chunkWatchEvent_UnWatch = Reflector.ChunkWatchEvent_UnWatch;
        final Class[] array8 = new Class["  ".length()];
        array8["".length()] = ChunkCoordIntPair.class;
        array8[" ".length()] = EntityPlayerMP.class;
        Reflector.ChunkWatchEvent_UnWatch_Constructor = new ReflectorConstructor(chunkWatchEvent_UnWatch, array8);
        Reflector.ForgeBlock = new ReflectorClass(Block.class);
        Reflector.ForgeBlock_getBedDirection = new ReflectorMethod(Reflector.ForgeBlock, Reflector.I[0x24 ^ 0x75]);
        Reflector.ForgeBlock_isBedFoot = new ReflectorMethod(Reflector.ForgeBlock, Reflector.I[0x65 ^ 0x37]);
        final ReflectorClass forgeBlock = Reflector.ForgeBlock;
        final String s2 = Reflector.I[0xF9 ^ 0xAA];
        final Class[] array9 = new Class[" ".length()];
        array9["".length()] = IBlockState.class;
        Reflector.ForgeBlock_hasTileEntity = new ReflectorMethod(forgeBlock, s2, array9);
        Reflector.ForgeBlock_canCreatureSpawn = new ReflectorMethod(Reflector.ForgeBlock, Reflector.I[0xF2 ^ 0xA6]);
        Reflector.ForgeBlock_addHitEffects = new ReflectorMethod(Reflector.ForgeBlock, Reflector.I[0x64 ^ 0x31]);
        Reflector.ForgeBlock_addDestroyEffects = new ReflectorMethod(Reflector.ForgeBlock, Reflector.I[0xC3 ^ 0x95]);
        Reflector.ForgeBlock_isAir = new ReflectorMethod(Reflector.ForgeBlock, Reflector.I[0xCF ^ 0x98]);
        Reflector.ForgeBlock_canRenderInLayer = new ReflectorMethod(Reflector.ForgeBlock, Reflector.I[0xC2 ^ 0x9A]);
        Reflector.ForgeEntity = new ReflectorClass(Entity.class);
        Reflector.ForgeEntity_captureDrops = new ReflectorField(Reflector.ForgeEntity, Reflector.I[0xC4 ^ 0x9D]);
        Reflector.ForgeEntity_capturedDrops = new ReflectorField(Reflector.ForgeEntity, Reflector.I[0x75 ^ 0x2F]);
        Reflector.ForgeEntity_shouldRenderInPass = new ReflectorMethod(Reflector.ForgeEntity, Reflector.I[0xFF ^ 0xA4]);
        Reflector.ForgeEntity_canRiderInteract = new ReflectorMethod(Reflector.ForgeEntity, Reflector.I[0x6 ^ 0x5A]);
        Reflector.ForgeTileEntity = new ReflectorClass(TileEntity.class);
        Reflector.ForgeTileEntity_shouldRenderInPass = new ReflectorMethod(Reflector.ForgeTileEntity, Reflector.I[0x99 ^ 0xC4]);
        Reflector.ForgeTileEntity_getRenderBoundingBox = new ReflectorMethod(Reflector.ForgeTileEntity, Reflector.I[0x4C ^ 0x12]);
        Reflector.ForgeTileEntity_canRenderBreaking = new ReflectorMethod(Reflector.ForgeTileEntity, Reflector.I[0xEB ^ 0xB4]);
        Reflector.ForgeItem = new ReflectorClass(Item.class);
        Reflector.ForgeItem_onEntitySwing = new ReflectorMethod(Reflector.ForgeItem, Reflector.I[0xDC ^ 0xBC]);
        Reflector.ForgePotionEffect = new ReflectorClass(PotionEffect.class);
        Reflector.ForgePotionEffect_isCurativeItem = new ReflectorMethod(Reflector.ForgePotionEffect, Reflector.I[0xDD ^ 0xBC]);
        Reflector.ForgeItemRecord = new ReflectorClass(ItemRecord.class);
        final ReflectorClass forgeItemRecord = Reflector.ForgeItemRecord;
        final String s3 = Reflector.I[0x30 ^ 0x52];
        final Class[] array10 = new Class[" ".length()];
        array10["".length()] = String.class;
        Reflector.ForgeItemRecord_getRecordResource = new ReflectorMethod(forgeItemRecord, s3, array10);
        Reflector.ForgeVertexFormatElementEnumUseage = new ReflectorClass(VertexFormatElement.EnumUsage.class);
        Reflector.ForgeVertexFormatElementEnumUseage_preDraw = new ReflectorMethod(Reflector.ForgeVertexFormatElementEnumUseage, Reflector.I[0xEB ^ 0x88]);
        Reflector.ForgeVertexFormatElementEnumUseage_postDraw = new ReflectorMethod(Reflector.ForgeVertexFormatElementEnumUseage, Reflector.I[0xC8 ^ 0xAC]);
    }
    
    public static Object call(final Object o, final ReflectorMethod reflectorMethod, final Object... array) {
        try {
            final Method targetMethod = reflectorMethod.getTargetMethod();
            if (targetMethod == null) {
                return null;
            }
            return targetMethod.invoke(o, array);
        }
        catch (Throwable t) {
            handleException(t, o, reflectorMethod, array);
            return null;
        }
    }
    
    public static void setFieldValue(final Object o, final ReflectorField reflectorField, final Object o2) {
        try {
            final Field targetField = reflectorField.getTargetField();
            if (targetField == null) {
                return;
            }
            targetField.set(o, o2);
            "".length();
            if (false == true) {
                throw null;
            }
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
    }
    
    public static float getFieldValueFloat(final Object o, final ReflectorField reflectorField, final float n) {
        final Object fieldValue = getFieldValue(o, reflectorField);
        if (!(fieldValue instanceof Float)) {
            return n;
        }
        return (float)fieldValue;
    }
    
    public static void callVoid(final ReflectorMethod reflectorMethod, final Object... array) {
        try {
            final Method targetMethod = reflectorMethod.getTargetMethod();
            if (targetMethod == null) {
                return;
            }
            targetMethod.invoke(null, array);
            "".length();
            if (0 >= 3) {
                throw null;
            }
        }
        catch (Throwable t) {
            handleException(t, null, reflectorMethod, array);
        }
    }
    
    public static Object call(final ReflectorMethod reflectorMethod, final Object... array) {
        try {
            final Method targetMethod = reflectorMethod.getTargetMethod();
            if (targetMethod == null) {
                return null;
            }
            return targetMethod.invoke(null, array);
        }
        catch (Throwable t) {
            handleException(t, null, reflectorMethod, array);
            return null;
        }
    }
    
    public static String callString(final ReflectorMethod reflectorMethod, final Object... array) {
        try {
            final Method targetMethod = reflectorMethod.getTargetMethod();
            if (targetMethod == null) {
                return null;
            }
            return (String)targetMethod.invoke(null, array);
        }
        catch (Throwable t) {
            handleException(t, null, reflectorMethod, array);
            return null;
        }
    }
    
    public static void callVoid(final Object o, final ReflectorMethod reflectorMethod, final Object... array) {
        try {
            if (o == null) {
                return;
            }
            final Method targetMethod = reflectorMethod.getTargetMethod();
            if (targetMethod == null) {
                return;
            }
            targetMethod.invoke(o, array);
            "".length();
            if (2 <= -1) {
                throw null;
            }
        }
        catch (Throwable t) {
            handleException(t, o, reflectorMethod, array);
        }
    }
    
    private static void dbgFieldValue(final boolean b, final String s, final ReflectorField reflectorField, final Object o) {
        final String name = reflectorField.getTargetField().getDeclaringClass().getName();
        final String name2 = reflectorField.getTargetField().getName();
        String s2 = Reflector.I[0x1E ^ 0x6F];
        if (b) {
            s2 = Reflector.I[0xFA ^ 0x88];
        }
        Config.dbg(String.valueOf(s) + s2 + Reflector.I[0xF6 ^ 0x85] + name + Reflector.I[0x4A ^ 0x3E] + name2 + Reflector.I[0x15 ^ 0x60] + o);
    }
    
    public static float callFloat(final Object o, final ReflectorMethod reflectorMethod, final Object... array) {
        try {
            final Method targetMethod = reflectorMethod.getTargetMethod();
            if (targetMethod == null) {
                return 0.0f;
            }
            return (float)targetMethod.invoke(o, array);
        }
        catch (Throwable t) {
            handleException(t, o, reflectorMethod, array);
            return 0.0f;
        }
    }
    
    private static void handleException(final Throwable t, final Object o, final ReflectorMethod reflectorMethod, final Object[] array) {
        if (t instanceof InvocationTargetException) {
            t.printStackTrace();
            "".length();
            if (2 >= 4) {
                throw null;
            }
        }
        else {
            if (t instanceof IllegalArgumentException) {
                Config.warn(Reflector.I[0x2F ^ 0x59]);
                Config.warn(Reflector.I[0xCC ^ 0xBB] + reflectorMethod.getTargetMethod());
                Config.warn(Reflector.I[0x52 ^ 0x2A] + o);
                Config.warn(Reflector.I[0x57 ^ 0x2E] + Config.arrayToString(getClasses(array)));
                Config.warn(Reflector.I[0x5F ^ 0x25] + Config.arrayToString(array));
            }
            Config.warn(Reflector.I[0xC2 ^ 0xB9]);
            Config.warn(Reflector.I[0xD0 ^ 0xAC] + reflectorMethod.getTargetMethod());
            reflectorMethod.deactivate();
            t.printStackTrace();
        }
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (2 < 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static void I() {
        (I = new String[57 + 10 + 28 + 36])["".length()] = I("*$\u0007:\u0002\u0006/\u0006\u0004", "gKcvm");
        Reflector.I[" ".length()] = I("8/ \u0015<8\u001d!\u00035.\b\"\u001e:!", "JJNqY");
        Reflector.I["  ".length()] = I("\u001d58\u001d=\u001d\u00198\u000f\u001a\u0003?5\u0012", "oPVyX");
        Reflector.I["   ".length()] = I("$.\r+<$\t\u000f :=\u0002\u0010\u0006-3&%:5:x'", "VKcOY");
        Reflector.I[0x34 ^ 0x30] = I("3\u0011\u0011<\n5\u0011\u0004\u0006\u001c3\u0002\u0013'", "AtvUy");
        Reflector.I[0x61 ^ 0x64] = I("-$6\u000f\u001095-!$$(/-\u0011#.,\u0000\n-(!", "JABLe");
        Reflector.I[0x23 ^ 0x25] = I("\u001f\"%b\u001e\u0018)4/\u0001\u0010!%b\u0000\u0003$\u007f\n>=\u00154\"\u0017\u00145\u0010/\u0010\u00144\"\u0000\u001a\u001350>\n", "qGQLs");
        Reflector.I[0xBF ^ 0xB8] = I("\"3\u001b!\u0002\"\u0001\u001a7\u000b4\u0014\u0019*\u0004;", "PVuEg");
        Reflector.I[0x55 ^ 0x5D] = I("64/)36\u0018/;3*%.?/\u0006=..=", "DQAMV");
        Reflector.I[0x26 ^ 0x2F] = I("0\b\u001d+\u00020$\u0007*\n\u0003\u001e5:\u000b.^7\r\u000b-\u000e\u0018", "BmsOg");
        Reflector.I[0x47 ^ 0x4D] = I("\r+\u0010,\u0001\u0002#\u0014,\u0010", "ABwDu");
        Reflector.I[0xAA ^ 0xA1] = I("(\n3\r\u0004", "KkPea");
        Reflector.I[0x6E ^ 0x62] = I("\f8.*5", "oTKKG");
        Reflector.I[0x80 ^ 0x8D] = I("\u0007\u0019\u001e0:\u0006\u001a\u001e!5", "EuqSQ");
        Reflector.I[0x30 ^ 0x3E] = I("\n\u0014;\b?(\u001e'\u0001", "xqHmK");
        Reflector.I[0x94 ^ 0x9B] = I("(\u00161o\"/\u001d \"='\u00151' 4\u0014 o,)\u001e(.!h>,/*%\u0001$';\u0000\u001c7&*", "FsEAO");
        Reflector.I[0x99 ^ 0x89] = I("\u000193\u001f\u001d\u001b-#\u0002", "DovQI");
        Reflector.I[0xB ^ 0x1A] = I("\u0017.5@\u0014\u0010%$\r\u000b\u0018-5\b\u0016\u000b,$@\u001a\u0016&,\u0001\u0017W\r.\u001c\u001e\u001c\u0003.\u0001\u0012\n", "yKAny");
        Reflector.I[0x52 ^ 0x40] = I("%\u00166\u0013\u0000#\u0016\u001d)\u0013>9\u000e\u000e\u0017)\u0013.\u001b\u0004-\u001d\u000e", "Jxzzv");
        Reflector.I[0x4A ^ 0x59] = I(":\u0007\"\f <\u0007\t0&1\b\u001a\u0000", "UineV");
        Reflector.I[0x7F ^ 0x6B] = I("$\u0017\u0005:\u0013\"\u0017.\u0012\u0011?\u0018*8", "KyISe");
        Reflector.I[0x9B ^ 0x8E] = I("\u000e\u0017\u0004\u001f\u0012\b\u0017/>\u0011\u0013\r", "ayHvd");
        Reflector.I[0xD ^ 0x1B] = I("-+6!\u0001++\u001d\f\u0012#1\u0012", "BEzHw");
        Reflector.I[0x84 ^ 0x93] = I("\u001c+\u0004'\u0014\u001a+/\n\u0010\u001c5;", "sEHNb");
        Reflector.I[0x4 ^ 0x1C] = I("78*\"\u000418\u0001\r\u00134:", "XVfKr");
        Reflector.I[0x50 ^ 0x49] = I(")4%$2/4\u000e\u00071+*", "FZiMD");
        Reflector.I[0xB0 ^ 0xAA] = I("!\u00035L/&\b$\u00010.\u00005\u0004-=\u0001$L!#\u000f$\f6a+(\f',\u0014 \u00046\t\t3\u0005'\f\n(\u0007,;", "OfAbB");
        Reflector.I[0x77 ^ 0x6C] = I("\u0000,>\"\u0001\t-/\u00024\u0006:9", "gIJpd");
        Reflector.I[0x20 ^ 0x3C] = I("(\u0003\u0000\u001f\u0003*\u000b&3\u0019+\u0003\u00063\u0005", "OftVw");
        Reflector.I[0x89 ^ 0x94] = I("\u0017\r;^'\u0010\u0006*\u00138\u0018\u000e;\u0016%\u000b\u000f*^)\u0015\u0001*\u001e>W. \u0002-\u001c  \u001f!\n+#\u0019/\u0017\u001c", "yhOpJ");
        Reflector.I[0x70 ^ 0x6E] = I("&-\u0015\u0001->\u0001=\u001c/\"\u000b8\u0014$%*6\u001b8", "ICQsL");
        Reflector.I[0x81 ^ 0x9E] = I(";\u0006\u000b(- 6\u0007)\u00005\u0019\u0007?\"", "TtbMC");
        Reflector.I[0xA9 ^ 0x89] = I("1\u0005\u0000\u00183!\u000f\u001b:7;\b\u0016\u001a\u001e4\u001f\u0007", "UlshR");
        Reflector.I[0xAF ^ 0x8E] = I("\u0018\u000f6\u0005\b\u0005\u000e'%=\n\u00191", "kjBWm");
        Reflector.I[0x42 ^ 0x60] = I("\u001a\n\u0011#0\u0001\u00117#\u001b\u0001\r1% \u0010\u0000\u00154-", "udEFH");
        Reflector.I[0xA7 ^ 0x84] = I("\u001b+\u0015#\u0010\u000003#;\u0000,5%\u0000\u0011!\u0011)\u001b\u0000", "tEAFh");
        Reflector.I[0x74 ^ 0x50] = I("\u0000\u0004>5*\u0000'9#<\u000615#<\u001d\u000f\u00180!\u0016", "raPQO");
        Reflector.I[0x72 ^ 0x57] = I("\b\u0014\u0005$>\t\u0002\u0014\u001f\u001e '", "oqqkX");
        Reflector.I[0x50 ^ 0x76] = I(" &\u0014\"\u0003'&\u00100>", "DTuUP");
        Reflector.I[0x6B ^ 0x4C] = I("7'\u0001\u0019+\n,)\u0012)*", "XIGvL");
        Reflector.I[0xA ^ 0x22] = I(" \f\u0004\"\u0004=\r\u0015\u0002-2\u0010\u0015\u0002", "Sippa");
        Reflector.I[0x4 ^ 0x2D] = I("\r\u000e\u001b|#\n\u0005\n1<\u0002\r\u001b4!\u0011\f\n|(\u000e\u0007A1!\u000e\u0006\u0000<`%&#\u0011!\u000e\u0006\u0000<\u0006\u0002\u0005\u000b>+\u0011", "ckoRN");
        Reflector.I[0x6D ^ 0x47] = I("\u001a\u000f96/\u001d\u0002/", "saJBN");
        Reflector.I[0xA3 ^ 0x88] = I("$\t\u0016+\u000f);\u001d=\u0015)\u001a+;\u0002>\u001c\u0011!\u0004", "LhxOc");
        Reflector.I[0xAF ^ 0x83] = I("\u0018\u0003&6+\u00151- 1\u0015\u0010\t0(\u0005\u0016\u001c=\u0014\u0004\u0003:&", "pbHRG");
        Reflector.I[0xA5 ^ 0x88] = I("\t#\f\b\f\u000f('\u001b\u0003\u001f%6\f\u0012\u0003?\u0010", "lMdib");
        Reflector.I[0x3F ^ 0x11] = I("!(\u001c\u000b\u0007'#\f \u001b!>", "FMhIu");
        Reflector.I[0xA7 ^ 0x88] = I("<\u0003\u0000M\u0014;\b\u0011\u0000\u000b3\u0000\u0000\u0005\u0016 \u0001\u0011M\u001f?\nZ\u0000\u0015;\u0003\u001a\u0017W\u0014+8 \u0015;\u0003\u001a\u001713\b\u0010\u000f\u001c ", "Rftcy");
        Reflector.I[0xE ^ 0x3E] = I("&!\u0011\f\u0002!,\u0007", "OObxc");
        Reflector.I[0x6D ^ 0x5C] = I("\r\u0019\u0018!3\u0000\u0003:)", "djTNR");
        Reflector.I[0x4D ^ 0x7F] = I("\u0003,\u001ax$\u0004'\u000b5;\f/\u001a0&\u001f.\u000bx*\u0001 \u000b8=C\u0000'\",\u0000\u001b\u000b8-\b;\u000b$m$=\u000b;\u001b\b'\n3;90\u001e3", "mInVI");
        Reflector.I[0x8E ^ 0xBD] = I("\u0017&' 1\u000226", "Rwria");
        Reflector.I[0x0 ^ 0x34] = I("\n$:1;\u0014\u0013+\f4\b3+\u0010", "mANbP");
        Reflector.I[0xE ^ 0x3B] = I("\"2\u001d-#*\"\r<*+3\f\u001c*7", "EWinO");
        Reflector.I[0xA7 ^ 0x91] = I("+\u0002\u00052\u0015-\u0013\u0019\u0000\u0002\u001e\u0002\u001f\u0001\u0015>\u0002\u0003", "Lgqep");
        Reflector.I[0x7B ^ 0x4C] = I(",\u0005\u0016\u0017#\n\u0004\u0017\u0010#&\u000f\u0010", "OjcyW");
        Reflector.I[0x6B ^ 0x53] = I("\u0015-\u0005\"\u000b\u0000\u001f\u001e\u0000\u0002\u0016\u001b\u0005\u001d\u001c\u0013/\u0014", "rHqrn");
        Reflector.I[0x35 ^ 0xC] = I("\u0003..M\u0018\u0004%?\u0000\u0007\f-.\u0005\u001a\u001f,?M\u0016\u0001\"?\r\u0001C\u0002\b\u0006\u001b\t.(+\u0014\u0003/6\u0006\u0007", "mKZcu");
        Reflector.I[0x41 ^ 0x7B] = I("\u0016\u000b4\"\u0010\u0016", "dnZFu");
        Reflector.I[0x2E ^ 0x15] = I("\u001c\u001d\u001ag<\u001b\u0016\u000b*#\u0013\u001e\u001a/>\u0000\u001f\u000bg2\u001d\u0015\u0003&?\\<\u0007$4\u001c\u000b\u0007&??\u0019\u0000(6\u0017\n", "rxnIQ");
        Reflector.I[0x7F ^ 0x43] = I("\u0000$\u001b0 \u00065\u0006\u0000\u0010\u000e,\n\r'\u000e.\u0001*\u0010\u0014", "gAocT");
        Reflector.I[0x79 ^ 0x44] = I("\u001f&\u0017f\u0002\u0018-\u0006+\u001d\u0010%\u0017.\u0000\u0003$\u0006f\n\u0007&\r<A\u0006,\u0011$\u000b_\u0014\f:\u0003\u0015\u0006\u0015-\u0001\u0005g/'\u000e\u0015", "qCcHo");
        Reflector.I[0x21 ^ 0x1F] = I("\"?6g\u001b%4'*\u0004-<6/\u0019>='g\u0015 3''\u0002b?4,\u00188t\u0005<\u001f\u001f90,\u0013\"\u001f4,\u00188~\u0006;\u0017;\t!;\u0013)4\u0007?\u0013\".f\u0019\u0004)", "LZBIv");
        Reflector.I[0x30 ^ 0xF] = I("\u0016\u0011;E9\u0011\u001a*\b&\u0019\u0012;\r;\n\u0013*E7\u0014\u001d*\u0005 V\u00119\u000e:\fZ\b\u001e=+\u0017=\u000e1\u001619\u000e:\fP\u000b\u00195\u000f',\u00191\u001d\u001a\n\u001d1\u0016\u0000k;;\u000b\u0000", "xtOkT");
        Reflector.I[0x24 ^ 0x64] = I("\u00075\u0004@\u000b\u0000>\u0015\r\u0014\b6\u0004\b\t\u001b7\u0015@\u0005\u00059\u0015\u0000\u0012G5\u0006\u000b\b\u001d~5\u0000\u0012\u0000$\t8\u000f\f'\"\u000b\b\r5\u0002+\u0010\f>\u0004J \u000673\u0001\n\u0006\"\u0003", "iPpnf");
        Reflector.I[0xEE ^ 0xAF] = I("9\t\u0006", "KlbBk");
        Reflector.I[0x7D ^ 0x3F] = I("2\u0000\u000f\u000f(", "UrjjF");
        Reflector.I[0xDA ^ 0x99] = I("/=\u0001*", "MQtOQ");
        Reflector.I[0xF ^ 0x4B] = I("&)\fh\u0002!\"\u001d%\u001d)*\f \u0000:+\u001dh\f$%\u001d(\u001bf)\u000e#\u0001<b=(\u001b!8\u0001\u0010\u0006-;*#\u0001,)\n\u0003\u0019-\"\fb)'+<#\u0001;%\f?", "HLxFo");
        Reflector.I[0x7E ^ 0x3B] = I("\t\u0012(\u0004\u0005\u0019\u000e", "mwFwl");
        Reflector.I[0x5C ^ 0x1A] = I(">\b\u001aZ\u00079\u0003\u000b\u0017\u00181\u000b\u001a\u0012\u0005\"\n\u000bZ\t<\u0004\u000b\u001a\u001e~\b\u0018\u0011\u0004$C+\u001a\u001e9\u0019\u0017\"\u00035\u001a<\u0011\u00044\b\u001c1\u001c5\u0003\u001aP85\u0003\n\u0011\u0018\u0016\u0002\t1\u001c5\u0003\u001a", "Pmntj");
        Reflector.I[0xCF ^ 0x88] = I("\u0014\u0013\u0019b\u0002\u0013\u0018\b/\u001d\u001b\u0010\u0019*\u0000\b\u0011\bb\t\u0017\u001aC/\u0000\u0017\u001b\u0002\"A\u001f\u0000\b\"\u001b\u0012\u0017\u0003(\u0003\u001f\u0004C\t\u0019\u001f\u0018\u0019\u000e\u001a\t", "zvmLo");
        Reflector.I[0x33 ^ 0x7B] = I("\u0013\u0018?'", "cwLST");
        Reflector.I[0x33 ^ 0x7A] = I(":4$\u007f!=?52>57$7#&65\u007f*9=~2#9<??b1'5?8<0>5 1#~\u0014:1?$u\u001e1\"%=8", "TQPQL");
        Reflector.I[0x32 ^ 0x78] = I("1=%7", "uxknS");
        Reflector.I[0xF3 ^ 0xB8] = I("\u0013\u0002+\r\u000e", "RNgBY");
        Reflector.I[0xC4 ^ 0x88] = I("#/$\n\u0007+>", "gjbKR");
        Reflector.I[0x2F ^ 0x62] = I("\u001a?0V5\u001d4!\u001b*\u0015<0\u001e7\u0006=!V=\u0002?*\fv256\u001f=1,!\u0016,2;'\f7\u0006#", "tZDxX");
        Reflector.I[0x4F ^ 0x1] = I("!\u000f>\u0016/6\u0007$*\u00122\u000f'=", "BnPSA");
        Reflector.I[0x16 ^ 0x59] = I("$\u0011&\u0006\u001a3\u0019<:0\"\u00038\"\u0003)", "GpHCt");
        Reflector.I[0xC7 ^ 0x97] = I("\u0006'\"I?\u0001,3\u0004 \t$\"\u0001=\u001a%3I7\u001e'8\u0013|\u001f-$\u000b6F\u0001>\u0012<\u0003\u00157\u00131\u0000\u0007 \u0002<\u001cf\u0003\t\u0005\t65\u000f", "hBVgR");
        Reflector.I[0x6B ^ 0x3A] = I("),\u001c\t\u000b*\r\u00019\u000b-=\u0001$\u0000", "NIhKn");
        Reflector.I[0x55 ^ 0x7] = I("\u0007\u0006;\u0003)(\u001a\u0016\u0012", "nuyfM");
        Reflector.I[0x7B ^ 0x28] = I("\u001a30!\u001b\u001e7\u0006\u001b\u0006\u001b&:", "rRCur");
        Reflector.I[0x3E ^ 0x6A] = I("\u0016\u0016=++\u0010\u0016'\u001d+\u0010$#\t.\u001b", "uwShY");
        Reflector.I[0xF6 ^ 0xA3] = I("\u00104/\u0019#\u0005\u0015-7/\u0012$8", "qPKQJ");
        Reflector.I[0x1A ^ 0x4C] = I("\u0018\u00117\u0017\u0017\n\u0001!<\u000b<\u001356\u0011\r\u0006", "yuSSr");
        Reflector.I[0x49 ^ 0x1E] = I("+\u0011)\u0011(", "BbhxZ");
        Reflector.I[0x7 ^ 0x5F] = I("12#\u0019*<7(9\u0006<\u001f,2* ", "RSMKO");
        Reflector.I[0xE1 ^ 0xB8] = I("\r\f6\u001b\u0012\u001c\b\u0002\u001d\b\u001e\u001e", "nmFog");
        Reflector.I[0x2F ^ 0x75] = I("'\u0000;=<6\u0004/\r;+\u00118", "DaKII");
        Reflector.I[0x2A ^ 0x71] = I("\u0010\u00075\u001a(\u0007=?\u0001 \u0006\u001d\u0013\u0001\u0014\u0002\u001c)", "coZoD");
        Reflector.I[0x64 ^ 0x38] = I("0\u0006\u00174\u00137\u0002\u000b/\u0014'\u0002\u000b\u0007\u0019'", "Sgyfz");
        Reflector.I[0x71 ^ 0x2C] = I("\u0002\u0000\u0005/<\u0015:\u000f44\u0014\u001a#4\u0000\u0010\u001b\u0019", "qhjZP");
        Reflector.I[0xE8 ^ 0xB6] = I("\u0017\u0001-'\u0000\u001e\u0000<\u0007'\u001f\u00117\u0011\f\u001e\u0003\u001b\u001a\u001d", "pdYue");
        Reflector.I[0x12 ^ 0x4D] = I("\u000f\u0013\u001f\u00173\u0002\u0016\u00147\u0014\u001e\u0017\u0010.?\u0002\u0015", "lrqEV");
        Reflector.I[0x75 ^ 0x15] = I("\u0000/,\u001d\u0013\u00065\u0010 \u0010\u0006/\u000e", "oAisg");
        Reflector.I[0x5E ^ 0x3F] = I("\u0006:.\u0006\u0004\u000e=\u0004\u0005\u0013&=\b\u001e", "oImsv");
        Reflector.I[0x7A ^ 0x18] = I("\u0014+\u000e \u0012\u0010!\b\u0016%\u0016=\u0015\u0007\u0005\u0010+", "sNzrw");
        Reflector.I[0xE4 ^ 0x87] = I("7\u0019\u0003\u000b*&\u001c", "GkfOX");
        Reflector.I[0xD9 ^ 0xBD] = I("=\u001d83'?\u0013<", "MrKGc");
        Reflector.I[0x49 ^ 0x2C] = I("", "uFGyD");
        Reflector.I[0x1B ^ 0x7D] = I("N!\r+'\u00071", "nRyJS");
        Reflector.I[0x29 ^ 0x4E] = I("h", "HttmC");
        Reflector.I[0x3C ^ 0x54] = I("C", "mSarg");
        Reflector.I[0x2B ^ 0x42] = I("a", "IFZoe");
        Reflector.I[0xC6 ^ 0xAC] = I("bjKdI", "KJvZi");
        Reflector.I[0x6D ^ 0x6] = I("", "SeCTL");
        Reflector.I[0xC2 ^ 0xAE] = I("Q\u0019\u00169\u0002\u0018\t", "qjbXv");
        Reflector.I[0xC8 ^ 0xA5] = I("S", "sHFIx");
        Reflector.I[0x2E ^ 0x40] = I("_", "qAgsu");
        Reflector.I[0xE5 ^ 0x8A] = I("^", "vQxpz");
        Reflector.I[0x3A ^ 0x4A] = I("\u007f", "VpphH");
        Reflector.I[0xC8 ^ 0xB9] = I("", "EKXdr");
        Reflector.I[0x71 ^ 0x3] = I("g -\u000b=.0", "GSYjI");
        Reflector.I[0x15 ^ 0x66] = I("c", "CRYGv");
        Reflector.I[0x76 ^ 0x2] = I("O", "aVwlg");
        Reflector.I[0x7F ^ 0xA] = I("XJGS", "xwysP");
        Reflector.I[0x26 ^ 0x50] = I("I]ap>\u000f\u001b.7\u0016\u000f697\u0002\u000e\u0012%$2\u001b\u0014. \u0003\n\u0018%p]I]", "cwKPw");
        Reflector.I[0x79 ^ 0xE] = I("\u0002\u0012\u0005\u001d\u0004+MQ", "Owquk");
        Reflector.I[0xC6 ^ 0xBE] = I(",3\u001a\u000b/\u0017kP", "cQpnL");
        Reflector.I[0xF3 ^ 0x8A] = I("\u0002#9\u0004\u001d76.\u0017P1.*\u0016\u000371qE", "RBKep");
        Reflector.I[0xCF ^ 0xB5] = I("<(\u0017\r\u0003\t=\u0000\u001e\u001dVi", "lIeln");
        Reflector.I[0x75 ^ 0xE] = I("~A{z7,\b4*\u0006=\u0004?z\u001d!\u001f\"3\u00161K><R9\u000e%2\u001d0K{pX", "TkQZr");
        Reflector.I[0x4C ^ 0x30] = I("\u0000\u0010>\u000b')U.\u0006).\u0001#\u0015)9\u0010.Yh", "MuJcH");
        Reflector.I[0x3D ^ 0x40] = I("^ioT\u0013\u0018/ \u0013;\u0018\u00027\u0013/\u0019&+\u0000\u001f\f  \u0004.\u001d,+Tp^i", "tCEtZ");
        Reflector.I[0x79 ^ 0x7] = I("\u00045\u0016\n\f5/\u001b\r\u00175`X", "GZxyx");
        Reflector.I[38 + 84 - 113 + 118] = I("\u0000\u00125995\u0007\"*t3\u001f&+'5\u0000}x", "PsGXT");
        Reflector.I[57 + 53 - 86 + 104] = I("? \u0000;'\n5\u0017(9Ua", "oArZJ");
        Reflector.I[81 + 125 - 161 + 84] = I("^AEN\u000f\f\b\n\u001e>\u001d\u0004\u0001N%\u0001\u001f\u001c\u0007.\u0011K\u0000\bj\u0017\u0004\u0001\u001d>\u0006\u001e\f\u001a%\u0006KED`", "tkonJ");
        Reflector.I[53 + 128 - 161 + 110] = I("(\u0000606\u0019\u001a;7-\u0019O<&#\b\u001b15#\u001f\n<yb", "koXCB");
    }
    
    public static void setFieldValue(final ReflectorField reflectorField, final Object o) {
        setFieldValue(null, reflectorField, o);
    }
    
    public static boolean callBoolean(final Object o, final ReflectorMethod reflectorMethod, final Object... array) {
        try {
            final Method targetMethod = reflectorMethod.getTargetMethod();
            if (targetMethod == null) {
                return "".length() != 0;
            }
            return (boolean)targetMethod.invoke(o, array);
        }
        catch (Throwable t) {
            handleException(t, o, reflectorMethod, array);
            return "".length() != 0;
        }
    }
    
    private static void dbgCallVoid(final boolean b, final String s, final ReflectorMethod reflectorMethod, final Object[] array) {
        final String name = reflectorMethod.getTargetMethod().getDeclaringClass().getName();
        final String name2 = reflectorMethod.getTargetMethod().getName();
        String s2 = Reflector.I[0xEC ^ 0x87];
        if (b) {
            s2 = Reflector.I[0xDC ^ 0xB0];
        }
        Config.dbg(String.valueOf(s) + s2 + Reflector.I[0x71 ^ 0x1C] + name + Reflector.I[0x66 ^ 0x8] + name2 + Reflector.I[0xA9 ^ 0xC6] + Config.arrayToString(array) + Reflector.I[0x7A ^ 0xA]);
    }
    
    private static Object[] getClasses(final Object[] array) {
        if (array == null) {
            return new Class["".length()];
        }
        final Class[] array2 = new Class[array.length];
        int i = "".length();
        "".length();
        if (3 == 4) {
            throw null;
        }
        while (i < array2.length) {
            final Object o = array[i];
            if (o != null) {
                array2[i] = o.getClass();
            }
            ++i;
        }
        return array2;
    }
}

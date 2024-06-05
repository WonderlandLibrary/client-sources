package optfine;

import java.lang.reflect.*;
import java.util.*;
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
    public static ReflectorClass ModLoader;
    public static ReflectorMethod ModLoader_renderWorldBlock;
    public static ReflectorMethod ModLoader_renderInvBlock;
    public static ReflectorMethod ModLoader_renderBlockIsItemFull3D;
    public static ReflectorMethod ModLoader_registerServer;
    public static ReflectorMethod ModLoader_getCustomAnimationLogic;
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
    public static ReflectorClass MinecraftForgeClient;
    public static ReflectorMethod MinecraftForgeClient_getRenderPass;
    public static ReflectorMethod MinecraftForgeClient_getItemRenderer;
    public static ReflectorClass ForgeHooksClient;
    public static ReflectorMethod ForgeHooksClient_onDrawBlockHighlight;
    public static ReflectorMethod ForgeHooksClient_orientBedCamera;
    public static ReflectorMethod ForgeHooksClient_dispatchRenderLast;
    public static ReflectorMethod ForgeHooksClient_setRenderPass;
    public static ReflectorMethod ForgeHooksClient_onTextureStitchedPre;
    public static ReflectorMethod ForgeHooksClient_onTextureStitchedPost;
    public static ReflectorMethod ForgeHooksClient_renderFirstPersonHand;
    public static ReflectorMethod ForgeHooksClient_getOffsetFOV;
    public static ReflectorMethod ForgeHooksClient_drawScreen;
    public static ReflectorMethod ForgeHooksClient_onFogRender;
    public static ReflectorMethod ForgeHooksClient_setRenderLayer;
    public static ReflectorClass FMLCommonHandler;
    public static ReflectorMethod FMLCommonHandler_instance;
    public static ReflectorMethod FMLCommonHandler_handleServerStarting;
    public static ReflectorMethod FMLCommonHandler_handleServerAboutToStart;
    public static ReflectorMethod FMLCommonHandler_enhanceCrashReport;
    public static ReflectorMethod FMLCommonHandler_getBrandings;
    public static ReflectorClass FMLClientHandler;
    public static ReflectorMethod FMLClientHandler_instance;
    public static ReflectorMethod FMLClientHandler_isLoading;
    public static ReflectorClass ItemRenderType;
    public static ReflectorField ItemRenderType_EQUIPPED;
    public static ReflectorClass ForgeWorldProvider;
    public static ReflectorMethod ForgeWorldProvider_getSkyRenderer;
    public static ReflectorMethod ForgeWorldProvider_getCloudRenderer;
    public static ReflectorMethod ForgeWorldProvider_getWeatherRenderer;
    public static ReflectorClass ForgeWorld;
    public static ReflectorMethod ForgeWorld_countEntities;
    public static ReflectorMethod ForgeWorld_getPerWorldStorage;
    public static ReflectorClass IRenderHandler;
    public static ReflectorMethod IRenderHandler_render;
    public static ReflectorClass DimensionManager;
    public static ReflectorMethod DimensionManager_getStaticDimensionIDs;
    public static ReflectorClass WorldEvent_Load;
    public static ReflectorConstructor WorldEvent_Load_Constructor;
    public static ReflectorClass DrawScreenEvent_Pre;
    public static ReflectorConstructor DrawScreenEvent_Pre_Constructor;
    public static ReflectorClass DrawScreenEvent_Post;
    public static ReflectorConstructor DrawScreenEvent_Post_Constructor;
    public static ReflectorClass EntityViewRenderEvent_FogColors;
    public static ReflectorConstructor EntityViewRenderEvent_FogColors_Constructor;
    public static ReflectorField EntityViewRenderEvent_FogColors_red;
    public static ReflectorField EntityViewRenderEvent_FogColors_green;
    public static ReflectorField EntityViewRenderEvent_FogColors_blue;
    public static ReflectorClass EntityViewRenderEvent_FogDensity;
    public static ReflectorConstructor EntityViewRenderEvent_FogDensity_Constructor;
    public static ReflectorField EntityViewRenderEvent_FogDensity_density;
    public static ReflectorClass EntityViewRenderEvent_RenderFogEvent;
    public static ReflectorConstructor EntityViewRenderEvent_RenderFogEvent_Constructor;
    public static ReflectorClass EventBus;
    public static ReflectorMethod EventBus_post;
    public static ReflectorClass Event_Result;
    public static ReflectorField Event_Result_DENY;
    public static ReflectorField Event_Result_ALLOW;
    public static ReflectorField Event_Result_DEFAULT;
    public static ReflectorClass ForgeEventFactory;
    public static ReflectorMethod ForgeEventFactory_canEntitySpawn;
    public static ReflectorMethod ForgeEventFactory_canEntityDespawn;
    public static ReflectorClass ChunkWatchEvent_UnWatch;
    public static ReflectorConstructor ChunkWatchEvent_UnWatch_Constructor;
    public static ReflectorClass ForgeBlock;
    public static ReflectorMethod ForgeBlock_getBedDirection;
    public static ReflectorMethod ForgeBlock_isBedFoot;
    public static ReflectorMethod ForgeBlock_hasTileEntity;
    public static ReflectorMethod ForgeBlock_canCreatureSpawn;
    public static ReflectorMethod ForgeBlock_addHitEffects;
    public static ReflectorMethod ForgeBlock_addDestroyEffects;
    public static ReflectorMethod ForgeBlock_isAir;
    public static ReflectorMethod ForgeBlock_canRenderInLayer;
    public static ReflectorClass ForgeEntity;
    public static ReflectorField ForgeEntity_captureDrops;
    public static ReflectorField ForgeEntity_capturedDrops;
    public static ReflectorMethod ForgeEntity_shouldRenderInPass;
    public static ReflectorMethod ForgeEntity_canRiderInteract;
    public static ReflectorClass ForgeTileEntity;
    public static ReflectorMethod ForgeTileEntity_shouldRenderInPass;
    public static ReflectorMethod ForgeTileEntity_getRenderBoundingBox;
    public static ReflectorMethod ForgeTileEntity_canRenderBreaking;
    public static ReflectorClass ForgeItem;
    public static ReflectorMethod ForgeItem_onEntitySwing;
    public static ReflectorClass ForgePotionEffect;
    public static ReflectorMethod ForgePotionEffect_isCurativeItem;
    public static ReflectorClass ForgeItemRecord;
    public static ReflectorMethod ForgeItemRecord_getRecordResource;
    public static ReflectorClass ForgeVertexFormatElementEnumUseage;
    public static ReflectorMethod ForgeVertexFormatElementEnumUseage_preDraw;
    public static ReflectorMethod ForgeVertexFormatElementEnumUseage_postDraw;
    
    public static void callVoid(final ReflectorMethod p_callVoid_0_, final Object... p_callVoid_1_) {
        try {
            final Method method = p_callVoid_0_.getTargetMethod();
            if (method == null) {
                return;
            }
            method.invoke(null, p_callVoid_1_);
        }
        catch (Throwable throwable) {
            handleException(throwable, null, p_callVoid_0_, p_callVoid_1_);
        }
    }
    
    public static boolean callBoolean(final ReflectorMethod p_callBoolean_0_, final Object... p_callBoolean_1_) {
        try {
            final Method method = p_callBoolean_0_.getTargetMethod();
            if (method == null) {
                return false;
            }
            final Boolean obool = (Boolean)method.invoke(null, p_callBoolean_1_);
            return obool;
        }
        catch (Throwable throwable) {
            handleException(throwable, null, p_callBoolean_0_, p_callBoolean_1_);
            return false;
        }
    }
    
    public static int callInt(final ReflectorMethod p_callInt_0_, final Object... p_callInt_1_) {
        try {
            final Method method = p_callInt_0_.getTargetMethod();
            if (method == null) {
                return 0;
            }
            final Integer integer = (Integer)method.invoke(null, p_callInt_1_);
            return integer;
        }
        catch (Throwable throwable) {
            handleException(throwable, null, p_callInt_0_, p_callInt_1_);
            return 0;
        }
    }
    
    public static float callFloat(final ReflectorMethod p_callFloat_0_, final Object... p_callFloat_1_) {
        try {
            final Method method = p_callFloat_0_.getTargetMethod();
            if (method == null) {
                return 0.0f;
            }
            final Float f = (Float)method.invoke(null, p_callFloat_1_);
            return f;
        }
        catch (Throwable throwable) {
            handleException(throwable, null, p_callFloat_0_, p_callFloat_1_);
            return 0.0f;
        }
    }
    
    public static String callString(final ReflectorMethod p_callString_0_, final Object... p_callString_1_) {
        try {
            final Method method = p_callString_0_.getTargetMethod();
            if (method == null) {
                return null;
            }
            final String s = (String)method.invoke(null, p_callString_1_);
            return s;
        }
        catch (Throwable throwable) {
            handleException(throwable, null, p_callString_0_, p_callString_1_);
            return null;
        }
    }
    
    public static Object call(final ReflectorMethod p_call_0_, final Object... p_call_1_) {
        try {
            final Method method = p_call_0_.getTargetMethod();
            if (method == null) {
                return null;
            }
            final Object object = method.invoke(null, p_call_1_);
            return object;
        }
        catch (Throwable throwable) {
            handleException(throwable, null, p_call_0_, p_call_1_);
            return null;
        }
    }
    
    public static void callVoid(final Object p_callVoid_0_, final ReflectorMethod p_callVoid_1_, final Object... p_callVoid_2_) {
        try {
            if (p_callVoid_0_ == null) {
                return;
            }
            final Method method = p_callVoid_1_.getTargetMethod();
            if (method == null) {
                return;
            }
            method.invoke(p_callVoid_0_, p_callVoid_2_);
        }
        catch (Throwable throwable) {
            handleException(throwable, p_callVoid_0_, p_callVoid_1_, p_callVoid_2_);
        }
    }
    
    public static boolean callBoolean(final Object p_callBoolean_0_, final ReflectorMethod p_callBoolean_1_, final Object... p_callBoolean_2_) {
        try {
            final Method method = p_callBoolean_1_.getTargetMethod();
            if (method == null) {
                return false;
            }
            final Boolean obool = (Boolean)method.invoke(p_callBoolean_0_, p_callBoolean_2_);
            return obool;
        }
        catch (Throwable throwable) {
            handleException(throwable, p_callBoolean_0_, p_callBoolean_1_, p_callBoolean_2_);
            return false;
        }
    }
    
    public static int callInt(final Object p_callInt_0_, final ReflectorMethod p_callInt_1_, final Object... p_callInt_2_) {
        try {
            final Method method = p_callInt_1_.getTargetMethod();
            if (method == null) {
                return 0;
            }
            final Integer integer = (Integer)method.invoke(p_callInt_0_, p_callInt_2_);
            return integer;
        }
        catch (Throwable throwable) {
            handleException(throwable, p_callInt_0_, p_callInt_1_, p_callInt_2_);
            return 0;
        }
    }
    
    public static float callFloat(final Object p_callFloat_0_, final ReflectorMethod p_callFloat_1_, final Object... p_callFloat_2_) {
        try {
            final Method method = p_callFloat_1_.getTargetMethod();
            if (method == null) {
                return 0.0f;
            }
            final Float f = (Float)method.invoke(p_callFloat_0_, p_callFloat_2_);
            return f;
        }
        catch (Throwable throwable) {
            handleException(throwable, p_callFloat_0_, p_callFloat_1_, p_callFloat_2_);
            return 0.0f;
        }
    }
    
    public static String callString(final Object p_callString_0_, final ReflectorMethod p_callString_1_, final Object... p_callString_2_) {
        try {
            final Method method = p_callString_1_.getTargetMethod();
            if (method == null) {
                return null;
            }
            final String s = (String)method.invoke(p_callString_0_, p_callString_2_);
            return s;
        }
        catch (Throwable throwable) {
            handleException(throwable, p_callString_0_, p_callString_1_, p_callString_2_);
            return null;
        }
    }
    
    public static Object call(final Object p_call_0_, final ReflectorMethod p_call_1_, final Object... p_call_2_) {
        try {
            final Method method = p_call_1_.getTargetMethod();
            if (method == null) {
                return null;
            }
            final Object object = method.invoke(p_call_0_, p_call_2_);
            return object;
        }
        catch (Throwable throwable) {
            handleException(throwable, p_call_0_, p_call_1_, p_call_2_);
            return null;
        }
    }
    
    public static Object getFieldValue(final ReflectorField p_getFieldValue_0_) {
        return getFieldValue(null, p_getFieldValue_0_);
    }
    
    public static Object getFieldValue(final Object p_getFieldValue_0_, final ReflectorField p_getFieldValue_1_) {
        try {
            final Field field = p_getFieldValue_1_.getTargetField();
            if (field == null) {
                return null;
            }
            final Object object = field.get(p_getFieldValue_0_);
            return object;
        }
        catch (Throwable throwable) {
            throwable.printStackTrace();
            return null;
        }
    }
    
    public static float getFieldValueFloat(final Object p_getFieldValueFloat_0_, final ReflectorField p_getFieldValueFloat_1_, final float p_getFieldValueFloat_2_) {
        final Object object = getFieldValue(p_getFieldValueFloat_0_, p_getFieldValueFloat_1_);
        if (!(object instanceof Float)) {
            return p_getFieldValueFloat_2_;
        }
        final Float f = (Float)object;
        return f;
    }
    
    public static void setFieldValue(final ReflectorField p_setFieldValue_0_, final Object p_setFieldValue_1_) {
        setFieldValue(null, p_setFieldValue_0_, p_setFieldValue_1_);
    }
    
    public static void setFieldValue(final Object p_setFieldValue_0_, final ReflectorField p_setFieldValue_1_, final Object p_setFieldValue_2_) {
        try {
            final Field field = p_setFieldValue_1_.getTargetField();
            if (field == null) {
                return;
            }
            field.set(p_setFieldValue_0_, p_setFieldValue_2_);
        }
        catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
    
    public static boolean postForgeBusEvent(final ReflectorConstructor p_postForgeBusEvent_0_, final Object... p_postForgeBusEvent_1_) {
        final Object object = newInstance(p_postForgeBusEvent_0_, p_postForgeBusEvent_1_);
        return object != null && postForgeBusEvent(object);
    }
    
    public static boolean postForgeBusEvent(final Object p_postForgeBusEvent_0_) {
        if (p_postForgeBusEvent_0_ == null) {
            return false;
        }
        final Object object = getFieldValue(Reflector.MinecraftForge_EVENT_BUS);
        if (object == null) {
            return false;
        }
        final Object object2 = call(object, Reflector.EventBus_post, p_postForgeBusEvent_0_);
        if (!(object2 instanceof Boolean)) {
            return false;
        }
        final Boolean obool = (Boolean)object2;
        return obool;
    }
    
    public static Object newInstance(final ReflectorConstructor p_newInstance_0_, final Object... p_newInstance_1_) {
        final Constructor constructor = p_newInstance_0_.getTargetConstructor();
        if (constructor == null) {
            return null;
        }
        try {
            final Object object = constructor.newInstance(p_newInstance_1_);
            return object;
        }
        catch (Throwable throwable) {
            handleException(throwable, p_newInstance_0_, p_newInstance_1_);
            return null;
        }
    }
    
    public static boolean matchesTypes(final Class[] p_matchesTypes_0_, final Class[] p_matchesTypes_1_) {
        if (p_matchesTypes_0_.length != p_matchesTypes_1_.length) {
            return false;
        }
        for (int i = 0; i < p_matchesTypes_1_.length; ++i) {
            final Class oclass = p_matchesTypes_0_[i];
            final Class oclass2 = p_matchesTypes_1_[i];
            if (oclass != oclass2) {
                return false;
            }
        }
        return true;
    }
    
    private static void dbgCall(final boolean p_dbgCall_0_, final String p_dbgCall_1_, final ReflectorMethod p_dbgCall_2_, final Object[] p_dbgCall_3_, final Object p_dbgCall_4_) {
        final String s = p_dbgCall_2_.getTargetMethod().getDeclaringClass().getName();
        final String s2 = p_dbgCall_2_.getTargetMethod().getName();
        String s3 = "";
        if (p_dbgCall_0_) {
            s3 = " static";
        }
        Config.dbg(p_dbgCall_1_ + s3 + " " + s + "." + s2 + "(" + Config.arrayToString(p_dbgCall_3_) + ") => " + p_dbgCall_4_);
    }
    
    private static void dbgCallVoid(final boolean p_dbgCallVoid_0_, final String p_dbgCallVoid_1_, final ReflectorMethod p_dbgCallVoid_2_, final Object[] p_dbgCallVoid_3_) {
        final String s = p_dbgCallVoid_2_.getTargetMethod().getDeclaringClass().getName();
        final String s2 = p_dbgCallVoid_2_.getTargetMethod().getName();
        String s3 = "";
        if (p_dbgCallVoid_0_) {
            s3 = " static";
        }
        Config.dbg(p_dbgCallVoid_1_ + s3 + " " + s + "." + s2 + "(" + Config.arrayToString(p_dbgCallVoid_3_) + ")");
    }
    
    private static void dbgFieldValue(final boolean p_dbgFieldValue_0_, final String p_dbgFieldValue_1_, final ReflectorField p_dbgFieldValue_2_, final Object p_dbgFieldValue_3_) {
        final String s = p_dbgFieldValue_2_.getTargetField().getDeclaringClass().getName();
        final String s2 = p_dbgFieldValue_2_.getTargetField().getName();
        String s3 = "";
        if (p_dbgFieldValue_0_) {
            s3 = " static";
        }
        Config.dbg(p_dbgFieldValue_1_ + s3 + " " + s + "." + s2 + " => " + p_dbgFieldValue_3_);
    }
    
    private static void handleException(final Throwable p_handleException_0_, final Object p_handleException_1_, final ReflectorMethod p_handleException_2_, final Object[] p_handleException_3_) {
        if (p_handleException_0_ instanceof InvocationTargetException) {
            p_handleException_0_.printStackTrace();
        }
        else {
            if (p_handleException_0_ instanceof IllegalArgumentException) {
                Config.warn("*** IllegalArgumentException ***");
                Config.warn("Method: " + p_handleException_2_.getTargetMethod());
                Config.warn("Object: " + p_handleException_1_);
                Config.warn("Parameter classes: " + Config.arrayToString(getClasses(p_handleException_3_)));
                Config.warn("Parameters: " + Config.arrayToString(p_handleException_3_));
            }
            Config.warn("*** Exception outside of method ***");
            Config.warn("Method deactivated: " + p_handleException_2_.getTargetMethod());
            p_handleException_2_.deactivate();
            p_handleException_0_.printStackTrace();
        }
    }
    
    private static void handleException(final Throwable p_handleException_0_, final ReflectorConstructor p_handleException_1_, final Object[] p_handleException_2_) {
        if (p_handleException_0_ instanceof InvocationTargetException) {
            p_handleException_0_.printStackTrace();
        }
        else {
            if (p_handleException_0_ instanceof IllegalArgumentException) {
                Config.warn("*** IllegalArgumentException ***");
                Config.warn("Constructor: " + p_handleException_1_.getTargetConstructor());
                Config.warn("Parameter classes: " + Config.arrayToString(getClasses(p_handleException_2_)));
                Config.warn("Parameters: " + Config.arrayToString(p_handleException_2_));
            }
            Config.warn("*** Exception outside of constructor ***");
            Config.warn("Constructor deactivated: " + p_handleException_1_.getTargetConstructor());
            p_handleException_1_.deactivate();
            p_handleException_0_.printStackTrace();
        }
    }
    
    private static Object[] getClasses(final Object[] p_getClasses_0_) {
        if (p_getClasses_0_ == null) {
            return new Class[0];
        }
        final Class[] aclass = new Class[p_getClasses_0_.length];
        for (int i = 0; i < aclass.length; ++i) {
            final Object object = p_getClasses_0_[i];
            if (object != null) {
                aclass[i] = object.getClass();
            }
        }
        return aclass;
    }
    
    public static Field getField(final Class p_getField_0_, final Class p_getField_1_) {
        try {
            final Field[] afield = p_getField_0_.getDeclaredFields();
            for (int i = 0; i < afield.length; ++i) {
                final Field field = afield[i];
                if (field.getType() == p_getField_1_) {
                    field.setAccessible(true);
                    return field;
                }
            }
            return null;
        }
        catch (Exception var5) {
            return null;
        }
    }
    
    public static Field[] getFields(final Class p_getFields_0_, final Class p_getFields_1_) {
        final List list = new ArrayList();
        try {
            final Field[] afield = p_getFields_0_.getDeclaredFields();
            for (int i = 0; i < afield.length; ++i) {
                final Field field = afield[i];
                if (field.getType() == p_getFields_1_) {
                    field.setAccessible(true);
                    list.add(field);
                }
            }
            final Field[] afield2 = list.toArray(new Field[list.size()]);
            return afield2;
        }
        catch (Exception var6) {
            return null;
        }
    }
    
    static {
        Reflector.ModLoader = new ReflectorClass("ModLoader");
        Reflector.ModLoader_renderWorldBlock = new ReflectorMethod(Reflector.ModLoader, "renderWorldBlock");
        Reflector.ModLoader_renderInvBlock = new ReflectorMethod(Reflector.ModLoader, "renderInvBlock");
        Reflector.ModLoader_renderBlockIsItemFull3D = new ReflectorMethod(Reflector.ModLoader, "renderBlockIsItemFull3D");
        Reflector.ModLoader_registerServer = new ReflectorMethod(Reflector.ModLoader, "registerServer");
        Reflector.ModLoader_getCustomAnimationLogic = new ReflectorMethod(Reflector.ModLoader, "getCustomAnimationLogic");
        Reflector.FMLRenderAccessLibrary = new ReflectorClass("net.minecraft.src.FMLRenderAccessLibrary");
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
        Reflector.MinecraftForgeClient = new ReflectorClass("net.minecraftforge.client.MinecraftForgeClient");
        Reflector.MinecraftForgeClient_getRenderPass = new ReflectorMethod(Reflector.MinecraftForgeClient, "getRenderPass");
        Reflector.MinecraftForgeClient_getItemRenderer = new ReflectorMethod(Reflector.MinecraftForgeClient, "getItemRenderer");
        Reflector.ForgeHooksClient = new ReflectorClass("net.minecraftforge.client.ForgeHooksClient");
        Reflector.ForgeHooksClient_onDrawBlockHighlight = new ReflectorMethod(Reflector.ForgeHooksClient, "onDrawBlockHighlight");
        Reflector.ForgeHooksClient_orientBedCamera = new ReflectorMethod(Reflector.ForgeHooksClient, "orientBedCamera");
        Reflector.ForgeHooksClient_dispatchRenderLast = new ReflectorMethod(Reflector.ForgeHooksClient, "dispatchRenderLast");
        Reflector.ForgeHooksClient_setRenderPass = new ReflectorMethod(Reflector.ForgeHooksClient, "setRenderPass");
        Reflector.ForgeHooksClient_onTextureStitchedPre = new ReflectorMethod(Reflector.ForgeHooksClient, "onTextureStitchedPre");
        Reflector.ForgeHooksClient_onTextureStitchedPost = new ReflectorMethod(Reflector.ForgeHooksClient, "onTextureStitchedPost");
        Reflector.ForgeHooksClient_renderFirstPersonHand = new ReflectorMethod(Reflector.ForgeHooksClient, "renderFirstPersonHand");
        Reflector.ForgeHooksClient_getOffsetFOV = new ReflectorMethod(Reflector.ForgeHooksClient, "getOffsetFOV");
        Reflector.ForgeHooksClient_drawScreen = new ReflectorMethod(Reflector.ForgeHooksClient, "drawScreen");
        Reflector.ForgeHooksClient_onFogRender = new ReflectorMethod(Reflector.ForgeHooksClient, "onFogRender");
        Reflector.ForgeHooksClient_setRenderLayer = new ReflectorMethod(Reflector.ForgeHooksClient, "setRenderLayer");
        Reflector.FMLCommonHandler = new ReflectorClass("net.minecraftforge.fml.common.FMLCommonHandler");
        Reflector.FMLCommonHandler_instance = new ReflectorMethod(Reflector.FMLCommonHandler, "instance");
        Reflector.FMLCommonHandler_handleServerStarting = new ReflectorMethod(Reflector.FMLCommonHandler, "handleServerStarting");
        Reflector.FMLCommonHandler_handleServerAboutToStart = new ReflectorMethod(Reflector.FMLCommonHandler, "handleServerAboutToStart");
        Reflector.FMLCommonHandler_enhanceCrashReport = new ReflectorMethod(Reflector.FMLCommonHandler, "enhanceCrashReport");
        Reflector.FMLCommonHandler_getBrandings = new ReflectorMethod(Reflector.FMLCommonHandler, "getBrandings");
        Reflector.FMLClientHandler = new ReflectorClass("net.minecraftforge.fml.client.FMLClientHandler");
        Reflector.FMLClientHandler_instance = new ReflectorMethod(Reflector.FMLClientHandler, "instance");
        Reflector.FMLClientHandler_isLoading = new ReflectorMethod(Reflector.FMLClientHandler, "isLoading");
        Reflector.ItemRenderType = new ReflectorClass("net.minecraftforge.client.IItemRenderer$ItemRenderType");
        Reflector.ItemRenderType_EQUIPPED = new ReflectorField(Reflector.ItemRenderType, "EQUIPPED");
        Reflector.ForgeWorldProvider = new ReflectorClass(WorldProvider.class);
        Reflector.ForgeWorldProvider_getSkyRenderer = new ReflectorMethod(Reflector.ForgeWorldProvider, "getSkyRenderer");
        Reflector.ForgeWorldProvider_getCloudRenderer = new ReflectorMethod(Reflector.ForgeWorldProvider, "getCloudRenderer");
        Reflector.ForgeWorldProvider_getWeatherRenderer = new ReflectorMethod(Reflector.ForgeWorldProvider, "getWeatherRenderer");
        Reflector.ForgeWorld = new ReflectorClass(World.class);
        Reflector.ForgeWorld_countEntities = new ReflectorMethod(Reflector.ForgeWorld, "countEntities", new Class[] { EnumCreatureType.class, Boolean.TYPE });
        Reflector.ForgeWorld_getPerWorldStorage = new ReflectorMethod(Reflector.ForgeWorld, "getPerWorldStorage");
        Reflector.IRenderHandler = new ReflectorClass("net.minecraftforge.client.IRenderHandler");
        Reflector.IRenderHandler_render = new ReflectorMethod(Reflector.IRenderHandler, "render");
        Reflector.DimensionManager = new ReflectorClass("net.minecraftforge.common.DimensionManager");
        Reflector.DimensionManager_getStaticDimensionIDs = new ReflectorMethod(Reflector.DimensionManager, "getStaticDimensionIDs");
        Reflector.WorldEvent_Load = new ReflectorClass("net.minecraftforge.event.world.WorldEvent$Load");
        Reflector.WorldEvent_Load_Constructor = new ReflectorConstructor(Reflector.WorldEvent_Load, new Class[] { World.class });
        Reflector.DrawScreenEvent_Pre = new ReflectorClass("net.minecraftforge.client.event.GuiScreenEvent$DrawScreenEvent$Pre");
        Reflector.DrawScreenEvent_Pre_Constructor = new ReflectorConstructor(Reflector.DrawScreenEvent_Pre, new Class[] { GuiScreen.class, Integer.TYPE, Integer.TYPE, Float.TYPE });
        Reflector.DrawScreenEvent_Post = new ReflectorClass("net.minecraftforge.client.event.GuiScreenEvent$DrawScreenEvent$Post");
        Reflector.DrawScreenEvent_Post_Constructor = new ReflectorConstructor(Reflector.DrawScreenEvent_Post, new Class[] { GuiScreen.class, Integer.TYPE, Integer.TYPE, Float.TYPE });
        Reflector.EntityViewRenderEvent_FogColors = new ReflectorClass("net.minecraftforge.client.event.EntityViewRenderEvent$FogColors");
        Reflector.EntityViewRenderEvent_FogColors_Constructor = new ReflectorConstructor(Reflector.EntityViewRenderEvent_FogColors, new Class[] { EntityRenderer.class, Entity.class, Block.class, Double.TYPE, Float.TYPE, Float.TYPE, Float.TYPE });
        Reflector.EntityViewRenderEvent_FogColors_red = new ReflectorField(Reflector.EntityViewRenderEvent_FogColors, "red");
        Reflector.EntityViewRenderEvent_FogColors_green = new ReflectorField(Reflector.EntityViewRenderEvent_FogColors, "green");
        Reflector.EntityViewRenderEvent_FogColors_blue = new ReflectorField(Reflector.EntityViewRenderEvent_FogColors, "blue");
        Reflector.EntityViewRenderEvent_FogDensity = new ReflectorClass("net.minecraftforge.client.event.EntityViewRenderEvent$FogDensity");
        Reflector.EntityViewRenderEvent_FogDensity_Constructor = new ReflectorConstructor(Reflector.EntityViewRenderEvent_FogDensity, new Class[] { EntityRenderer.class, Entity.class, Block.class, Double.TYPE, Float.TYPE });
        Reflector.EntityViewRenderEvent_FogDensity_density = new ReflectorField(Reflector.EntityViewRenderEvent_FogDensity, "density");
        Reflector.EntityViewRenderEvent_RenderFogEvent = new ReflectorClass("net.minecraftforge.client.event.EntityViewRenderEvent$RenderFogEvent");
        Reflector.EntityViewRenderEvent_RenderFogEvent_Constructor = new ReflectorConstructor(Reflector.EntityViewRenderEvent_RenderFogEvent, new Class[] { EntityRenderer.class, Entity.class, Block.class, Double.TYPE, Integer.TYPE, Float.TYPE });
        Reflector.EventBus = new ReflectorClass("net.minecraftforge.fml.common.eventhandler.EventBus");
        Reflector.EventBus_post = new ReflectorMethod(Reflector.EventBus, "post");
        Reflector.Event_Result = new ReflectorClass("net.minecraftforge.fml.common.eventhandler.Event$Result");
        Reflector.Event_Result_DENY = new ReflectorField(Reflector.Event_Result, "DENY");
        Reflector.Event_Result_ALLOW = new ReflectorField(Reflector.Event_Result, "ALLOW");
        Reflector.Event_Result_DEFAULT = new ReflectorField(Reflector.Event_Result, "DEFAULT");
        Reflector.ForgeEventFactory = new ReflectorClass("net.minecraftforge.event.ForgeEventFactory");
        Reflector.ForgeEventFactory_canEntitySpawn = new ReflectorMethod(Reflector.ForgeEventFactory, "canEntitySpawn");
        Reflector.ForgeEventFactory_canEntityDespawn = new ReflectorMethod(Reflector.ForgeEventFactory, "canEntityDespawn");
        Reflector.ChunkWatchEvent_UnWatch = new ReflectorClass("net.minecraftforge.event.world.ChunkWatchEvent$UnWatch");
        Reflector.ChunkWatchEvent_UnWatch_Constructor = new ReflectorConstructor(Reflector.ChunkWatchEvent_UnWatch, new Class[] { ChunkCoordIntPair.class, EntityPlayerMP.class });
        Reflector.ForgeBlock = new ReflectorClass(Block.class);
        Reflector.ForgeBlock_getBedDirection = new ReflectorMethod(Reflector.ForgeBlock, "getBedDirection");
        Reflector.ForgeBlock_isBedFoot = new ReflectorMethod(Reflector.ForgeBlock, "isBedFoot");
        Reflector.ForgeBlock_hasTileEntity = new ReflectorMethod(Reflector.ForgeBlock, "hasTileEntity", new Class[] { IBlockState.class });
        Reflector.ForgeBlock_canCreatureSpawn = new ReflectorMethod(Reflector.ForgeBlock, "canCreatureSpawn");
        Reflector.ForgeBlock_addHitEffects = new ReflectorMethod(Reflector.ForgeBlock, "addHitEffects");
        Reflector.ForgeBlock_addDestroyEffects = new ReflectorMethod(Reflector.ForgeBlock, "addDestroyEffects");
        Reflector.ForgeBlock_isAir = new ReflectorMethod(Reflector.ForgeBlock, "isAir");
        Reflector.ForgeBlock_canRenderInLayer = new ReflectorMethod(Reflector.ForgeBlock, "canRenderInLayer");
        Reflector.ForgeEntity = new ReflectorClass(Entity.class);
        Reflector.ForgeEntity_captureDrops = new ReflectorField(Reflector.ForgeEntity, "captureDrops");
        Reflector.ForgeEntity_capturedDrops = new ReflectorField(Reflector.ForgeEntity, "capturedDrops");
        Reflector.ForgeEntity_shouldRenderInPass = new ReflectorMethod(Reflector.ForgeEntity, "shouldRenderInPass");
        Reflector.ForgeEntity_canRiderInteract = new ReflectorMethod(Reflector.ForgeEntity, "canRiderInteract");
        Reflector.ForgeTileEntity = new ReflectorClass(TileEntity.class);
        Reflector.ForgeTileEntity_shouldRenderInPass = new ReflectorMethod(Reflector.ForgeTileEntity, "shouldRenderInPass");
        Reflector.ForgeTileEntity_getRenderBoundingBox = new ReflectorMethod(Reflector.ForgeTileEntity, "getRenderBoundingBox");
        Reflector.ForgeTileEntity_canRenderBreaking = new ReflectorMethod(Reflector.ForgeTileEntity, "canRenderBreaking");
        Reflector.ForgeItem = new ReflectorClass(Item.class);
        Reflector.ForgeItem_onEntitySwing = new ReflectorMethod(Reflector.ForgeItem, "onEntitySwing");
        Reflector.ForgePotionEffect = new ReflectorClass(PotionEffect.class);
        Reflector.ForgePotionEffect_isCurativeItem = new ReflectorMethod(Reflector.ForgePotionEffect, "isCurativeItem");
        Reflector.ForgeItemRecord = new ReflectorClass(ItemRecord.class);
        Reflector.ForgeItemRecord_getRecordResource = new ReflectorMethod(Reflector.ForgeItemRecord, "getRecordResource", new Class[] { String.class });
        Reflector.ForgeVertexFormatElementEnumUseage = new ReflectorClass(VertexFormatElement.EnumUsage.class);
        Reflector.ForgeVertexFormatElementEnumUseage_preDraw = new ReflectorMethod(Reflector.ForgeVertexFormatElementEnumUseage, "preDraw");
        Reflector.ForgeVertexFormatElementEnumUseage_postDraw = new ReflectorMethod(Reflector.ForgeVertexFormatElementEnumUseage, "postDraw");
    }
}

package HORIZON-6-0-SKIDPROTECTION;

import java.util.ArrayList;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Reflector
{
    public static ReflectorClass HorizonCode_Horizon_È;
    public static ReflectorMethod Â;
    public static ReflectorMethod Ý;
    public static ReflectorMethod Ø­áŒŠá;
    public static ReflectorMethod Âµá€;
    public static ReflectorMethod Ó;
    public static ReflectorClass à;
    public static ReflectorMethod Ø;
    public static ReflectorMethod áŒŠÆ;
    public static ReflectorMethod áˆºÑ¢Õ;
    public static ReflectorClass ÂµÈ;
    public static ReflectorField á;
    public static ReflectorMethod ˆÏ­;
    public static ReflectorClass £á;
    public static ReflectorMethod Å;
    public static ReflectorClass £à;
    public static ReflectorField µà;
    public static ReflectorClass ˆà;
    public static ReflectorMethod ¥Æ;
    public static ReflectorMethod Ø­à;
    public static ReflectorMethod µÕ;
    public static ReflectorMethod Æ;
    public static ReflectorMethod Šáƒ;
    public static ReflectorMethod Ï­Ðƒà;
    public static ReflectorMethod áŒŠà;
    public static ReflectorMethod ŠÄ;
    public static ReflectorClass Ñ¢á;
    public static ReflectorMethod ŒÏ;
    public static ReflectorMethod Çªà¢;
    public static ReflectorClass Ê;
    public static ReflectorMethod ÇŽÉ;
    public static ReflectorMethod ˆá;
    public static ReflectorMethod ÇŽÕ;
    public static ReflectorMethod É;
    public static ReflectorMethod áƒ;
    public static ReflectorMethod á€;
    public static ReflectorMethod Õ;
    public static ReflectorMethod à¢;
    public static ReflectorMethod ŠÂµà;
    public static ReflectorMethod ¥à;
    public static ReflectorMethod Âµà;
    public static ReflectorMethod Ç;
    public static ReflectorClass È;
    public static ReflectorMethod áŠ;
    public static ReflectorMethod ˆáŠ;
    public static ReflectorMethod áŒŠ;
    public static ReflectorMethod £ÂµÄ;
    public static ReflectorClass Ø­Âµ;
    public static ReflectorMethod Ä;
    public static ReflectorMethod Ñ¢Â;
    public static ReflectorClass Ï­à;
    public static ReflectorField áˆºáˆºÈ;
    public static ReflectorClass ÇŽá€;
    public static ReflectorMethod Ï;
    public static ReflectorMethod Ô;
    public static ReflectorMethod ÇªÓ;
    public static ReflectorClass áˆºÏ;
    public static ReflectorMethod ˆáƒ;
    public static ReflectorClass Œ;
    public static ReflectorMethod £Ï;
    public static ReflectorClass Ø­á;
    public static ReflectorMethod ˆÉ;
    public static ReflectorClass Ï­Ï­Ï;
    public static ReflectorConstructor £Â;
    public static ReflectorClass £Ó;
    public static ReflectorConstructor ˆÐƒØ­à;
    public static ReflectorClass £Õ;
    public static ReflectorConstructor Ï­Ô;
    public static ReflectorClass Œà;
    public static ReflectorConstructor Ðƒá;
    public static ReflectorField ˆÏ;
    public static ReflectorField áˆºÇŽØ;
    public static ReflectorField ÇªÂµÕ;
    public static ReflectorClass áŒŠÏ;
    public static ReflectorConstructor áŒŠáŠ;
    public static ReflectorField ˆÓ;
    public static ReflectorClass ¥Ä;
    public static ReflectorConstructor ÇªÔ;
    public static ReflectorClass Û;
    public static ReflectorMethod ŠÓ;
    public static ReflectorClass ÇŽá;
    public static ReflectorField Ñ¢à;
    public static ReflectorField ÇªØ­;
    public static ReflectorField £áŒŠá;
    public static ReflectorClass áˆº;
    public static ReflectorMethod Šà;
    public static ReflectorMethod áŒŠá€;
    public static ReflectorClass ¥Ï;
    public static ReflectorConstructor ˆà¢;
    public static ReflectorClass Ñ¢Ç;
    public static ReflectorMethod £É;
    public static ReflectorMethod Ðƒáƒ;
    public static ReflectorMethod Ðƒà;
    public static ReflectorMethod ¥É;
    public static ReflectorMethod £ÇªÓ;
    public static ReflectorClass ÂµÕ;
    public static ReflectorField Š;
    public static ReflectorField Ø­Ñ¢á€;
    public static ReflectorMethod Ñ¢Ó;
    public static ReflectorMethod Ø­Æ;
    public static ReflectorClass áŒŠÔ;
    public static ReflectorMethod ŠÕ;
    public static ReflectorMethod £Ø­à;
    public static ReflectorClass µÐƒáƒ;
    public static ReflectorMethod áŒŠÕ;
    public static ReflectorClass ÂµÂ;
    public static ReflectorMethod áŒŠá;
    public static ReflectorClass ˆØ;
    public static ReflectorMethod áˆºà;
    public static ReflectorClass ÐƒÂ;
    public static ReflectorMethod £áƒ;
    
    static {
        Reflector.HorizonCode_Horizon_È = new ReflectorClass("ModLoader");
        Reflector.Â = new ReflectorMethod(Reflector.HorizonCode_Horizon_È, "renderWorldBlock");
        Reflector.Ý = new ReflectorMethod(Reflector.HorizonCode_Horizon_È, "renderInvBlock");
        Reflector.Ø­áŒŠá = new ReflectorMethod(Reflector.HorizonCode_Horizon_È, "renderBlockIsItemFull3D");
        Reflector.Âµá€ = new ReflectorMethod(Reflector.HorizonCode_Horizon_È, "registerServer");
        Reflector.Ó = new ReflectorMethod(Reflector.HorizonCode_Horizon_È, "getCustomAnimationLogic");
        Reflector.à = new ReflectorClass("net.minecraft.src.FMLRenderAccessLibrary");
        Reflector.Ø = new ReflectorMethod(Reflector.à, "renderWorldBlock");
        Reflector.áŒŠÆ = new ReflectorMethod(Reflector.à, "renderInventoryBlock");
        Reflector.áˆºÑ¢Õ = new ReflectorMethod(Reflector.à, "renderItemAsFull3DBlock");
        Reflector.ÂµÈ = new ReflectorClass("LightCache");
        Reflector.á = new ReflectorField(Reflector.ÂµÈ, "cache");
        Reflector.ˆÏ­ = new ReflectorMethod(Reflector.ÂµÈ, "clear");
        Reflector.£á = new ReflectorClass("BlockCoord");
        Reflector.Å = new ReflectorMethod(Reflector.£á, "resetPool");
        Reflector.£à = new ReflectorClass("net.minecraftforge.common.MinecraftForge");
        Reflector.µà = new ReflectorField(Reflector.£à, "EVENT_BUS");
        Reflector.ˆà = new ReflectorClass("net.minecraftforge.common.ForgeHooks");
        Reflector.¥Æ = new ReflectorMethod(Reflector.ˆà, "onLivingSetAttackTarget");
        Reflector.Ø­à = new ReflectorMethod(Reflector.ˆà, "onLivingUpdate");
        Reflector.µÕ = new ReflectorMethod(Reflector.ˆà, "onLivingAttack");
        Reflector.Æ = new ReflectorMethod(Reflector.ˆà, "onLivingHurt");
        Reflector.Šáƒ = new ReflectorMethod(Reflector.ˆà, "onLivingDeath");
        Reflector.Ï­Ðƒà = new ReflectorMethod(Reflector.ˆà, "onLivingDrops");
        Reflector.áŒŠà = new ReflectorMethod(Reflector.ˆà, "onLivingFall");
        Reflector.ŠÄ = new ReflectorMethod(Reflector.ˆà, "onLivingJump");
        Reflector.Ñ¢á = new ReflectorClass("net.minecraftforge.client.MinecraftForgeClient");
        Reflector.ŒÏ = new ReflectorMethod(Reflector.Ñ¢á, "getRenderPass");
        Reflector.Çªà¢ = new ReflectorMethod(Reflector.Ñ¢á, "getItemRenderer");
        Reflector.Ê = new ReflectorClass("net.minecraftforge.client.ForgeHooksClient");
        Reflector.ÇŽÉ = new ReflectorMethod(Reflector.Ê, "onDrawBlockHighlight");
        Reflector.ˆá = new ReflectorMethod(Reflector.Ê, "orientBedCamera");
        Reflector.ÇŽÕ = new ReflectorMethod(Reflector.Ê, "renderEquippedItem");
        Reflector.É = new ReflectorMethod(Reflector.Ê, "dispatchRenderLast");
        Reflector.áƒ = new ReflectorMethod(Reflector.Ê, "onTextureLoadPre");
        Reflector.á€ = new ReflectorMethod(Reflector.Ê, "setRenderPass");
        Reflector.Õ = new ReflectorMethod(Reflector.Ê, "onTextureStitchedPre");
        Reflector.à¢ = new ReflectorMethod(Reflector.Ê, "onTextureStitchedPost");
        Reflector.ŠÂµà = new ReflectorMethod(Reflector.Ê, "renderFirstPersonHand");
        Reflector.¥à = new ReflectorMethod(Reflector.Ê, "setWorldRendererRB");
        Reflector.Âµà = new ReflectorMethod(Reflector.Ê, "onPreRenderWorld");
        Reflector.Ç = new ReflectorMethod(Reflector.Ê, "onPostRenderWorld");
        Reflector.È = new ReflectorClass("net.minecraftforge.fml.common.FMLCommonHandler");
        Reflector.áŠ = new ReflectorMethod(Reflector.È, "instance");
        Reflector.ˆáŠ = new ReflectorMethod(Reflector.È, "handleServerStarting");
        Reflector.áŒŠ = new ReflectorMethod(Reflector.È, "handleServerAboutToStart");
        Reflector.£ÂµÄ = new ReflectorMethod(Reflector.È, "enhanceCrashReport");
        Reflector.Ø­Âµ = new ReflectorClass("net.minecraftforge.fml.client.FMLClientHandler");
        Reflector.Ä = new ReflectorMethod(Reflector.Ø­Âµ, "instance");
        Reflector.Ñ¢Â = new ReflectorMethod(Reflector.Ø­Âµ, "isLoading");
        Reflector.Ï­à = new ReflectorClass("net.minecraftforge.client.IItemRenderer$ItemRenderType");
        Reflector.áˆºáˆºÈ = new ReflectorField(Reflector.Ï­à, "EQUIPPED");
        Reflector.ÇŽá€ = new ReflectorClass(WorldProvider.class);
        Reflector.Ï = new ReflectorMethod(Reflector.ÇŽá€, "getSkyRenderer");
        Reflector.Ô = new ReflectorMethod(Reflector.ÇŽá€, "getCloudRenderer");
        Reflector.ÇªÓ = new ReflectorMethod(Reflector.ÇŽá€, "getWeatherRenderer");
        Reflector.áˆºÏ = new ReflectorClass(World.class);
        Reflector.ˆáƒ = new ReflectorMethod(Reflector.áˆºÏ, "countEntities", new Class[] { EnumCreatureType.class, Boolean.TYPE });
        Reflector.Œ = new ReflectorClass("net.minecraftforge.client.IRenderHandler");
        Reflector.£Ï = new ReflectorMethod(Reflector.Œ, "render");
        Reflector.Ø­á = new ReflectorClass("net.minecraftforge.common.DimensionManager");
        Reflector.ˆÉ = new ReflectorMethod(Reflector.Ø­á, "getStaticDimensionIDs");
        Reflector.Ï­Ï­Ï = new ReflectorClass("net.minecraftforge.event.world.WorldEvent$Load");
        Reflector.£Â = new ReflectorConstructor(Reflector.Ï­Ï­Ï, new Class[] { World.class });
        Reflector.£Ó = new ReflectorClass("net.minecraftforge.client.event.GuiScreenEvent$DrawScreenEvent$Pre");
        Reflector.ˆÐƒØ­à = new ReflectorConstructor(Reflector.£Ó, new Class[] { GuiScreen.class, Integer.TYPE, Integer.TYPE, Float.TYPE });
        Reflector.£Õ = new ReflectorClass("net.minecraftforge.client.event.GuiScreenEvent$DrawScreenEvent$Post");
        Reflector.Ï­Ô = new ReflectorConstructor(Reflector.£Õ, new Class[] { GuiScreen.class, Integer.TYPE, Integer.TYPE, Float.TYPE });
        Reflector.Œà = new ReflectorClass("net.minecraftforge.client.event.EntityViewRenderEvent$FogColors");
        Reflector.Ðƒá = new ReflectorConstructor(Reflector.Œà, new Class[] { EntityRenderer.class, EntityLivingBase.class, Block.class, Double.TYPE, Float.TYPE, Float.TYPE, Float.TYPE });
        Reflector.ˆÏ = new ReflectorField(Reflector.Œà, "red");
        Reflector.áˆºÇŽØ = new ReflectorField(Reflector.Œà, "green");
        Reflector.ÇªÂµÕ = new ReflectorField(Reflector.Œà, "blue");
        Reflector.áŒŠÏ = new ReflectorClass("net.minecraftforge.client.event.EntityViewRenderEvent$FogDensity");
        Reflector.áŒŠáŠ = new ReflectorConstructor(Reflector.áŒŠÏ, new Class[] { EntityRenderer.class, EntityLivingBase.class, Block.class, Double.TYPE, Float.TYPE });
        Reflector.ˆÓ = new ReflectorField(Reflector.áŒŠÏ, "density");
        Reflector.¥Ä = new ReflectorClass("net.minecraftforge.client.event.EntityViewRenderEvent$RenderFogEvent");
        Reflector.ÇªÔ = new ReflectorConstructor(Reflector.¥Ä, new Class[] { EntityRenderer.class, EntityLivingBase.class, Block.class, Double.TYPE, Integer.TYPE, Float.TYPE });
        Reflector.Û = new ReflectorClass("net.minecraftforge.fml.common.eventhandler.EventBus");
        Reflector.ŠÓ = new ReflectorMethod(Reflector.Û, "post");
        Reflector.ÇŽá = new ReflectorClass("net.minecraftforge.fml.common.eventhandler.Event$Result");
        Reflector.Ñ¢à = new ReflectorField(Reflector.ÇŽá, "DENY");
        Reflector.ÇªØ­ = new ReflectorField(Reflector.ÇŽá, "ALLOW");
        Reflector.£áŒŠá = new ReflectorField(Reflector.ÇŽá, "DEFAULT");
        Reflector.áˆº = new ReflectorClass("net.minecraftforge.event.ForgeEventFactory");
        Reflector.Šà = new ReflectorMethod(Reflector.áˆº, "canEntitySpawn");
        Reflector.áŒŠá€ = new ReflectorMethod(Reflector.áˆº, "canEntityDespawn");
        Reflector.¥Ï = new ReflectorClass("net.minecraftforge.event.world.ChunkWatchEvent$UnWatch");
        Reflector.ˆà¢ = new ReflectorConstructor(Reflector.¥Ï, new Class[] { ChunkCoordIntPair.class, EntityPlayerMP.class });
        Reflector.Ñ¢Ç = new ReflectorClass(Block.class);
        Reflector.£É = new ReflectorMethod(Reflector.Ñ¢Ç, "getBedDirection");
        Reflector.Ðƒáƒ = new ReflectorMethod(Reflector.Ñ¢Ç, "isBedFoot");
        Reflector.Ðƒà = new ReflectorMethod(Reflector.Ñ¢Ç, "canRenderInPass");
        Reflector.¥É = new ReflectorMethod(Reflector.Ñ¢Ç, "hasTileEntity", new Class[] { Integer.TYPE });
        Reflector.£ÇªÓ = new ReflectorMethod(Reflector.Ñ¢Ç, "canCreatureSpawn");
        Reflector.ÂµÕ = new ReflectorClass(Entity.class);
        Reflector.Š = new ReflectorField(Reflector.ÂµÕ, "captureDrops");
        Reflector.Ø­Ñ¢á€ = new ReflectorField(Reflector.ÂµÕ, "capturedDrops");
        Reflector.Ñ¢Ó = new ReflectorMethod(Reflector.ÂµÕ, "shouldRenderInPass");
        Reflector.Ø­Æ = new ReflectorMethod(Reflector.ÂµÕ, "canRiderInteract");
        Reflector.áŒŠÔ = new ReflectorClass(TileEntity.class);
        Reflector.ŠÕ = new ReflectorMethod(Reflector.áŒŠÔ, "shouldRenderInPass");
        Reflector.£Ø­à = new ReflectorMethod(Reflector.áŒŠÔ, "getRenderBoundingBox");
        Reflector.µÐƒáƒ = new ReflectorClass(Item_1028566121.class);
        Reflector.áŒŠÕ = new ReflectorMethod(Reflector.µÐƒáƒ, "onEntitySwing");
        Reflector.ÂµÂ = new ReflectorClass(PotionEffect.class);
        Reflector.áŒŠá = new ReflectorMethod(Reflector.ÂµÂ, "isCurativeItem");
        Reflector.ˆØ = new ReflectorClass(ItemStack.class);
        Reflector.áˆºà = new ReflectorMethod(Reflector.ˆØ, "hasEffect", new Class[] { Integer.TYPE });
        Reflector.ÐƒÂ = new ReflectorClass(ItemRecord.class);
        Reflector.£áƒ = new ReflectorMethod(Reflector.ÐƒÂ, "getRecordResource", new Class[] { String.class });
    }
    
    public static void HorizonCode_Horizon_È(final ReflectorMethod refMethod, final Object... params) {
        try {
            final Method e = refMethod.HorizonCode_Horizon_È();
            if (e == null) {
                return;
            }
            e.invoke(null, params);
        }
        catch (Throwable var3) {
            HorizonCode_Horizon_È(var3, null, refMethod, params);
        }
    }
    
    public static boolean Â(final ReflectorMethod refMethod, final Object... params) {
        try {
            final Method e = refMethod.HorizonCode_Horizon_È();
            if (e == null) {
                return false;
            }
            final Boolean retVal = (Boolean)e.invoke(null, params);
            return retVal;
        }
        catch (Throwable var4) {
            HorizonCode_Horizon_È(var4, null, refMethod, params);
            return false;
        }
    }
    
    public static int Ý(final ReflectorMethod refMethod, final Object... params) {
        try {
            final Method e = refMethod.HorizonCode_Horizon_È();
            if (e == null) {
                return 0;
            }
            final Integer retVal = (Integer)e.invoke(null, params);
            return retVal;
        }
        catch (Throwable var4) {
            HorizonCode_Horizon_È(var4, null, refMethod, params);
            return 0;
        }
    }
    
    public static float Ø­áŒŠá(final ReflectorMethod refMethod, final Object... params) {
        try {
            final Method e = refMethod.HorizonCode_Horizon_È();
            if (e == null) {
                return 0.0f;
            }
            final Float retVal = (Float)e.invoke(null, params);
            return retVal;
        }
        catch (Throwable var4) {
            HorizonCode_Horizon_È(var4, null, refMethod, params);
            return 0.0f;
        }
    }
    
    public static String Âµá€(final ReflectorMethod refMethod, final Object... params) {
        try {
            final Method e = refMethod.HorizonCode_Horizon_È();
            if (e == null) {
                return null;
            }
            final String retVal = (String)e.invoke(null, params);
            return retVal;
        }
        catch (Throwable var4) {
            HorizonCode_Horizon_È(var4, null, refMethod, params);
            return null;
        }
    }
    
    public static Object Ó(final ReflectorMethod refMethod, final Object... params) {
        try {
            final Method e = refMethod.HorizonCode_Horizon_È();
            if (e == null) {
                return null;
            }
            final Object retVal = e.invoke(null, params);
            return retVal;
        }
        catch (Throwable var4) {
            HorizonCode_Horizon_È(var4, null, refMethod, params);
            return null;
        }
    }
    
    public static void HorizonCode_Horizon_È(final Object obj, final ReflectorMethod refMethod, final Object... params) {
        try {
            if (obj == null) {
                return;
            }
            final Method e = refMethod.HorizonCode_Horizon_È();
            if (e == null) {
                return;
            }
            e.invoke(obj, params);
        }
        catch (Throwable var4) {
            HorizonCode_Horizon_È(var4, obj, refMethod, params);
        }
    }
    
    public static boolean Â(final Object obj, final ReflectorMethod refMethod, final Object... params) {
        try {
            final Method e = refMethod.HorizonCode_Horizon_È();
            if (e == null) {
                return false;
            }
            final Boolean retVal = (Boolean)e.invoke(obj, params);
            return retVal;
        }
        catch (Throwable var5) {
            HorizonCode_Horizon_È(var5, obj, refMethod, params);
            return false;
        }
    }
    
    public static int Ý(final Object obj, final ReflectorMethod refMethod, final Object... params) {
        try {
            final Method e = refMethod.HorizonCode_Horizon_È();
            if (e == null) {
                return 0;
            }
            final Integer retVal = (Integer)e.invoke(obj, params);
            return retVal;
        }
        catch (Throwable var5) {
            HorizonCode_Horizon_È(var5, obj, refMethod, params);
            return 0;
        }
    }
    
    public static float Ø­áŒŠá(final Object obj, final ReflectorMethod refMethod, final Object... params) {
        try {
            final Method e = refMethod.HorizonCode_Horizon_È();
            if (e == null) {
                return 0.0f;
            }
            final Float retVal = (Float)e.invoke(obj, params);
            return retVal;
        }
        catch (Throwable var5) {
            HorizonCode_Horizon_È(var5, obj, refMethod, params);
            return 0.0f;
        }
    }
    
    public static String Âµá€(final Object obj, final ReflectorMethod refMethod, final Object... params) {
        try {
            final Method e = refMethod.HorizonCode_Horizon_È();
            if (e == null) {
                return null;
            }
            final String retVal = (String)e.invoke(obj, params);
            return retVal;
        }
        catch (Throwable var5) {
            HorizonCode_Horizon_È(var5, obj, refMethod, params);
            return null;
        }
    }
    
    public static Object Ó(final Object obj, final ReflectorMethod refMethod, final Object... params) {
        try {
            final Method e = refMethod.HorizonCode_Horizon_È();
            if (e == null) {
                return null;
            }
            final Object retVal = e.invoke(obj, params);
            return retVal;
        }
        catch (Throwable var5) {
            HorizonCode_Horizon_È(var5, obj, refMethod, params);
            return null;
        }
    }
    
    public static Object HorizonCode_Horizon_È(final ReflectorField refField) {
        return HorizonCode_Horizon_È((Object)null, refField);
    }
    
    public static Object HorizonCode_Horizon_È(final Object obj, final ReflectorField refField) {
        try {
            final Field e = refField.HorizonCode_Horizon_È();
            if (e == null) {
                return null;
            }
            final Object value = e.get(obj);
            return value;
        }
        catch (Throwable var4) {
            var4.printStackTrace();
            return null;
        }
    }
    
    public static float HorizonCode_Horizon_È(final Object obj, final ReflectorField refField, final float def) {
        final Object val = HorizonCode_Horizon_È(obj, refField);
        if (!(val instanceof Float)) {
            return def;
        }
        final Float valFloat = (Float)val;
        return valFloat;
    }
    
    public static void HorizonCode_Horizon_È(final ReflectorField refField, final Object value) {
        HorizonCode_Horizon_È(null, refField, value);
    }
    
    public static void HorizonCode_Horizon_È(final Object obj, final ReflectorField refField, final Object value) {
        try {
            final Field e = refField.HorizonCode_Horizon_È();
            if (e == null) {
                return;
            }
            e.set(obj, value);
        }
        catch (Throwable var4) {
            var4.printStackTrace();
        }
    }
    
    public static boolean HorizonCode_Horizon_È(final ReflectorConstructor constr, final Object... params) {
        final Object event = Â(constr, params);
        return event != null && HorizonCode_Horizon_È(event);
    }
    
    public static boolean HorizonCode_Horizon_È(final Object event) {
        if (event == null) {
            return false;
        }
        final Object eventBus = HorizonCode_Horizon_È(Reflector.µà);
        if (eventBus == null) {
            return false;
        }
        final Object ret = Ó(eventBus, Reflector.ŠÓ, event);
        if (!(ret instanceof Boolean)) {
            return false;
        }
        final Boolean retBool = (Boolean)ret;
        return retBool;
    }
    
    public static Object Â(final ReflectorConstructor constr, final Object... params) {
        final Constructor c = constr.HorizonCode_Horizon_È();
        if (c == null) {
            return null;
        }
        try {
            final Object e = c.newInstance(params);
            return e;
        }
        catch (Throwable var4) {
            HorizonCode_Horizon_È(var4, constr, params);
            return null;
        }
    }
    
    public static boolean HorizonCode_Horizon_È(final Class[] pTypes, final Class[] cTypes) {
        if (pTypes.length != cTypes.length) {
            return false;
        }
        for (int i = 0; i < cTypes.length; ++i) {
            final Class pType = pTypes[i];
            final Class cType = cTypes[i];
            if (pType != cType) {
                return false;
            }
        }
        return true;
    }
    
    private static void HorizonCode_Horizon_È(final boolean isStatic, final String callType, final ReflectorMethod refMethod, final Object[] params, final Object retVal) {
        final String className = refMethod.HorizonCode_Horizon_È().getDeclaringClass().getName();
        final String methodName = refMethod.HorizonCode_Horizon_È().getName();
        String staticStr = "";
        if (isStatic) {
            staticStr = " static";
        }
        Config.HorizonCode_Horizon_È(String.valueOf(callType) + staticStr + " " + className + "." + methodName + "(" + Config.HorizonCode_Horizon_È(params) + ") => " + retVal);
    }
    
    private static void HorizonCode_Horizon_È(final boolean isStatic, final String callType, final ReflectorMethod refMethod, final Object[] params) {
        final String className = refMethod.HorizonCode_Horizon_È().getDeclaringClass().getName();
        final String methodName = refMethod.HorizonCode_Horizon_È().getName();
        String staticStr = "";
        if (isStatic) {
            staticStr = " static";
        }
        Config.HorizonCode_Horizon_È(String.valueOf(callType) + staticStr + " " + className + "." + methodName + "(" + Config.HorizonCode_Horizon_È(params) + ")");
    }
    
    private static void HorizonCode_Horizon_È(final boolean isStatic, final String accessType, final ReflectorField refField, final Object val) {
        final String className = refField.HorizonCode_Horizon_È().getDeclaringClass().getName();
        final String fieldName = refField.HorizonCode_Horizon_È().getName();
        String staticStr = "";
        if (isStatic) {
            staticStr = " static";
        }
        Config.HorizonCode_Horizon_È(String.valueOf(accessType) + staticStr + " " + className + "." + fieldName + " => " + val);
    }
    
    private static void HorizonCode_Horizon_È(final Throwable e, final Object obj, final ReflectorMethod refMethod, final Object[] params) {
        if (e instanceof InvocationTargetException) {
            e.printStackTrace();
        }
        else {
            if (e instanceof IllegalArgumentException) {
                Config.Â("*** IllegalArgumentException ***");
                Config.Â("Method: " + refMethod.HorizonCode_Horizon_È());
                Config.Â("Object: " + obj);
                Config.Â("Parameter classes: " + Config.HorizonCode_Horizon_È(HorizonCode_Horizon_È(params)));
                Config.Â("Parameters: " + Config.HorizonCode_Horizon_È(params));
            }
            Config.Â("*** Exception outside of method ***");
            Config.Â("Method deactivated: " + refMethod.HorizonCode_Horizon_È());
            refMethod.Ø­áŒŠá();
            e.printStackTrace();
        }
    }
    
    private static void HorizonCode_Horizon_È(final Throwable e, final ReflectorConstructor refConstr, final Object[] params) {
        if (e instanceof InvocationTargetException) {
            e.printStackTrace();
        }
        else {
            if (e instanceof IllegalArgumentException) {
                Config.Â("*** IllegalArgumentException ***");
                Config.Â("Constructor: " + refConstr.HorizonCode_Horizon_È());
                Config.Â("Parameter classes: " + Config.HorizonCode_Horizon_È(HorizonCode_Horizon_È(params)));
                Config.Â("Parameters: " + Config.HorizonCode_Horizon_È(params));
            }
            Config.Â("*** Exception outside of constructor ***");
            Config.Â("Constructor deactivated: " + refConstr.HorizonCode_Horizon_È());
            refConstr.Ý();
            e.printStackTrace();
        }
    }
    
    private static Object[] HorizonCode_Horizon_È(final Object[] objs) {
        if (objs == null) {
            return new Class[0];
        }
        final Class[] classes = new Class[objs.length];
        for (int i = 0; i < classes.length; ++i) {
            final Object obj = objs[i];
            if (obj != null) {
                classes[i] = obj.getClass();
            }
        }
        return classes;
    }
    
    public static Field HorizonCode_Horizon_È(final Class cls, final Class fieldType) {
        try {
            final Field[] e = cls.getDeclaredFields();
            for (int i = 0; i < e.length; ++i) {
                final Field field = e[i];
                if (field.getType() == fieldType) {
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
    
    public static Field[] Â(final Class cls, final Class fieldType) {
        final ArrayList list = new ArrayList();
        try {
            final Field[] e = cls.getDeclaredFields();
            for (int fields = 0; fields < e.length; ++fields) {
                final Field field = e[fields];
                if (field.getType() == fieldType) {
                    field.setAccessible(true);
                    list.add(field);
                }
            }
            final Field[] var7 = list.toArray(new Field[list.size()]);
            return var7;
        }
        catch (Exception var8) {
            return null;
        }
    }
}

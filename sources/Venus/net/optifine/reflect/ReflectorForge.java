/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.reflect;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapData;
import net.optifine.Log;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorClass;
import net.optifine.reflect.ReflectorField;
import net.optifine.util.StrUtils;

public class ReflectorForge {
    public static Object EVENT_RESULT_ALLOW = Reflector.getFieldValue(Reflector.Event_Result_ALLOW);
    public static Object EVENT_RESULT_DENY = Reflector.getFieldValue(Reflector.Event_Result_DENY);
    public static Object EVENT_RESULT_DEFAULT = Reflector.getFieldValue(Reflector.Event_Result_DEFAULT);
    public static final boolean FORGE_BLOCKSTATE_HAS_TILE_ENTITY = Reflector.IForgeBlockState_hasTileEntity.exists();
    public static final boolean FORGE_ENTITY_CAN_UPDATE = Reflector.IForgeEntity_canUpdate.exists();

    public static void putLaunchBlackboard(String string, Object object) {
        Map map = (Map)Reflector.getFieldValue(Reflector.Launch_blackboard);
        if (map != null) {
            map.put(string, object);
        }
    }

    public static InputStream getOptiFineResourceStream(String string) {
        if (!Reflector.OptiFineResourceLocator.exists()) {
            return null;
        }
        string = StrUtils.removePrefix(string, "/");
        return (InputStream)Reflector.call(Reflector.OptiFineResourceLocator_getOptiFineResourceStream, string);
    }

    public static ReflectorClass getReflectorClassOptiFineResourceLocator() {
        String string = "optifine.OptiFineResourceLocator";
        Object v = System.getProperties().get(string + ".class");
        if (v instanceof Class) {
            Class clazz = (Class)v;
            return new ReflectorClass(clazz);
        }
        return new ReflectorClass(string);
    }

    public static boolean blockHasTileEntity(BlockState blockState) {
        return FORGE_BLOCKSTATE_HAS_TILE_ENTITY ? Reflector.callBoolean(blockState, Reflector.IForgeBlockState_hasTileEntity, new Object[0]) : blockState.getBlock().isTileEntityProvider();
    }

    public static boolean isItemDamaged(ItemStack itemStack) {
        return !Reflector.IForgeItem_showDurabilityBar.exists() ? itemStack.isDamaged() : Reflector.callBoolean(itemStack.getItem(), Reflector.IForgeItem_showDurabilityBar, itemStack);
    }

    public static int getLightValue(BlockState blockState, IBlockDisplayReader iBlockDisplayReader, BlockPos blockPos) {
        return Reflector.IForgeBlockState_getLightValue2.exists() ? Reflector.callInt(blockState, Reflector.IForgeBlockState_getLightValue2, iBlockDisplayReader, blockPos) : blockState.getLightValue();
    }

    public static MapData getMapData(ItemStack itemStack, World world) {
        if (Reflector.ForgeHooksClient.exists()) {
            FilledMapItem filledMapItem = (FilledMapItem)itemStack.getItem();
            return FilledMapItem.getMapData(itemStack, world);
        }
        return FilledMapItem.getMapData(itemStack, world);
    }

    public static String[] getForgeModIds() {
        if (!Reflector.Loader.exists()) {
            return new String[0];
        }
        Object object = Reflector.call(Reflector.Loader_instance, new Object[0]);
        List list = (List)Reflector.call(object, Reflector.Loader_getActiveModList, new Object[0]);
        if (list == null) {
            return new String[0];
        }
        ArrayList<String> arrayList = new ArrayList<String>();
        for (Object e : list) {
            String string;
            if (!Reflector.ModContainer.isInstance(e) || (string = Reflector.callString(e, Reflector.ModContainer_getModId, new Object[0])) == null) continue;
            arrayList.add(string);
        }
        String[] stringArray = arrayList.toArray(new String[arrayList.size()]);
        return stringArray;
    }

    public static boolean isAir(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos) {
        return Reflector.IForgeBlockState_isAir2.exists() ? Reflector.callBoolean(blockState, Reflector.IForgeBlockState_isAir2, iBlockReader, blockPos) : blockState.isAir();
    }

    public static boolean canDisableShield(ItemStack itemStack, ItemStack itemStack2, PlayerEntity playerEntity, MobEntity mobEntity) {
        return Reflector.IForgeItemStack_canDisableShield.exists() ? Reflector.callBoolean(itemStack, Reflector.IForgeItemStack_canDisableShield, itemStack2, playerEntity, mobEntity) : itemStack.getItem() instanceof AxeItem;
    }

    public static boolean isShield(ItemStack itemStack, PlayerEntity playerEntity) {
        if (Reflector.IForgeItemStack_isShield.exists()) {
            return Reflector.callBoolean(itemStack, Reflector.IForgeItemStack_isShield, playerEntity);
        }
        return itemStack.getItem() == Items.SHIELD;
    }

    public static Button makeButtonMods(MainMenuScreen mainMenuScreen, int n, int n2) {
        return !Reflector.ModListScreen_Constructor.exists() ? null : new Button(mainMenuScreen.width / 2 - 100, n + n2 * 2, 98, 20, new TranslationTextComponent("fml.menu.mods"), arg_0 -> ReflectorForge.lambda$makeButtonMods$0(mainMenuScreen, arg_0));
    }

    public static void setForgeLightPipelineEnabled(boolean bl) {
        if (Reflector.ForgeConfig_Client_forgeLightPipelineEnabled.exists()) {
            ReflectorForge.setConfigClientBoolean(Reflector.ForgeConfig_Client_forgeLightPipelineEnabled, bl);
        }
    }

    public static boolean getForgeUseCombinedDepthStencilAttachment() {
        return Reflector.ForgeConfig_Client_useCombinedDepthStencilAttachment.exists() ? ReflectorForge.getConfigClientBoolean(Reflector.ForgeConfig_Client_useCombinedDepthStencilAttachment, false) : false;
    }

    public static boolean getConfigClientBoolean(ReflectorField reflectorField, boolean bl) {
        if (!reflectorField.exists()) {
            return bl;
        }
        Object object = Reflector.ForgeConfig_CLIENT.getValue();
        if (object == null) {
            return bl;
        }
        Object object2 = Reflector.getFieldValue(object, reflectorField);
        return object2 == null ? bl : Reflector.callBoolean(object2, Reflector.ForgeConfigSpec_ConfigValue_get, new Object[0]);
    }

    private static void setConfigClientBoolean(ReflectorField reflectorField, boolean bl) {
        Object object;
        Object object2;
        if (reflectorField.exists() && (object2 = Reflector.ForgeConfig_CLIENT.getValue()) != null && (object = Reflector.getFieldValue(object2, reflectorField)) != null) {
            Supplier<Boolean> supplier = new Supplier<Boolean>(bl){
                final boolean val$value;
                {
                    this.val$value = bl;
                }

                @Override
                public Boolean get() {
                    return this.val$value;
                }

                @Override
                public Object get() {
                    return this.get();
                }
            };
            Reflector.setFieldValue(object, Reflector.ForgeConfigSpec_ConfigValue_defaultSupplier, supplier);
            Object object3 = Reflector.getFieldValue(object, Reflector.ForgeConfigSpec_ConfigValue_spec);
            if (object3 != null) {
                Reflector.setFieldValue(object3, Reflector.ForgeConfigSpec_childConfig, null);
            }
            Log.dbg("Set ForgeConfig.CLIENT." + reflectorField.getTargetField().getName() + "=" + bl);
        }
    }

    public static boolean canUpdate(Entity entity2) {
        return FORGE_ENTITY_CAN_UPDATE ? Reflector.callBoolean(entity2, Reflector.IForgeEntity_canUpdate, new Object[0]) : true;
    }

    public static boolean isDamageable(Item item, ItemStack itemStack) {
        return Reflector.IForgeItem_isDamageable1.exists() ? Reflector.callBoolean(item, Reflector.IForgeItem_isDamageable1, itemStack) : item.isDamageable();
    }

    public static void fillNormal(int[] nArray, Direction direction) {
        Vector3f vector3f = ReflectorForge.getVertexPos(nArray, 3);
        Vector3f vector3f2 = ReflectorForge.getVertexPos(nArray, 1);
        Vector3f vector3f3 = ReflectorForge.getVertexPos(nArray, 2);
        Vector3f vector3f4 = ReflectorForge.getVertexPos(nArray, 0);
        vector3f.sub(vector3f2);
        vector3f3.sub(vector3f4);
        vector3f3.cross(vector3f);
        vector3f3.normalize();
        int n = (byte)Math.round(vector3f3.getX() * 127.0f) & 0xFF;
        int n2 = (byte)Math.round(vector3f3.getY() * 127.0f) & 0xFF;
        int n3 = (byte)Math.round(vector3f3.getZ() * 127.0f) & 0xFF;
        int n4 = n | n2 << 8 | n3 << 16;
        int n5 = nArray.length / 4;
        for (int i = 0; i < 4; ++i) {
            nArray[i * n5 + 7] = n4;
        }
    }

    private static Vector3f getVertexPos(int[] nArray, int n) {
        int n2 = nArray.length / 4;
        int n3 = n * n2;
        float f = Float.intBitsToFloat(nArray[n3]);
        float f2 = Float.intBitsToFloat(nArray[n3 + 1]);
        float f3 = Float.intBitsToFloat(nArray[n3 + 2]);
        return new Vector3f(f, f2, f3);
    }

    private static void lambda$makeButtonMods$0(MainMenuScreen mainMenuScreen, Button button) {
        Screen screen = (Screen)Reflector.ModListScreen_Constructor.newInstance(mainMenuScreen);
        Minecraft.getInstance().displayGuiScreen(screen);
    }
}


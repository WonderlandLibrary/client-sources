/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.EnchantmentScreen;
import net.minecraft.client.gui.screen.HopperScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.AnvilScreen;
import net.minecraft.client.gui.screen.inventory.BeaconScreen;
import net.minecraft.client.gui.screen.inventory.BrewingStandScreen;
import net.minecraft.client.gui.screen.inventory.ChestScreen;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.screen.inventory.CraftingScreen;
import net.minecraft.client.gui.screen.inventory.CreativeScreen;
import net.minecraft.client.gui.screen.inventory.DispenserScreen;
import net.minecraft.client.gui.screen.inventory.FurnaceScreen;
import net.minecraft.client.gui.screen.inventory.HorseInventoryScreen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.client.gui.screen.inventory.MerchantScreen;
import net.minecraft.client.gui.screen.inventory.ShulkerBoxScreen;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.resources.IResourcePack;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.optifine.Config;
import net.optifine.CustomGuiProperties;
import net.optifine.override.PlayerControllerOF;
import net.optifine.util.PropertiesOrdered;
import net.optifine.util.ResUtils;

public class CustomGuis {
    private static Minecraft mc = Config.getMinecraft();
    private static PlayerControllerOF playerControllerOF = null;
    private static CustomGuiProperties[][] guiProperties = null;
    public static boolean isChristmas = CustomGuis.isChristmas();

    public static ResourceLocation getTextureLocation(ResourceLocation resourceLocation) {
        if (guiProperties == null) {
            return resourceLocation;
        }
        Screen screen = CustomGuis.mc.currentScreen;
        if (!(screen instanceof ContainerScreen)) {
            return resourceLocation;
        }
        if (resourceLocation.getNamespace().equals("minecraft") && resourceLocation.getPath().startsWith("textures/gui/")) {
            Entity entity2;
            if (playerControllerOF == null) {
                return resourceLocation;
            }
            ClientWorld clientWorld = CustomGuis.mc.world;
            if (clientWorld == null) {
                return resourceLocation;
            }
            if (screen instanceof CreativeScreen) {
                return CustomGuis.getTexturePos(CustomGuiProperties.EnumContainer.CREATIVE, CustomGuis.mc.player.getPosition(), clientWorld, resourceLocation, screen);
            }
            if (screen instanceof InventoryScreen) {
                return CustomGuis.getTexturePos(CustomGuiProperties.EnumContainer.INVENTORY, CustomGuis.mc.player.getPosition(), clientWorld, resourceLocation, screen);
            }
            BlockPos blockPos = playerControllerOF.getLastClickBlockPos();
            if (blockPos != null) {
                if (screen instanceof AnvilScreen) {
                    return CustomGuis.getTexturePos(CustomGuiProperties.EnumContainer.ANVIL, blockPos, clientWorld, resourceLocation, screen);
                }
                if (screen instanceof BeaconScreen) {
                    return CustomGuis.getTexturePos(CustomGuiProperties.EnumContainer.BEACON, blockPos, clientWorld, resourceLocation, screen);
                }
                if (screen instanceof BrewingStandScreen) {
                    return CustomGuis.getTexturePos(CustomGuiProperties.EnumContainer.BREWING_STAND, blockPos, clientWorld, resourceLocation, screen);
                }
                if (screen instanceof ChestScreen) {
                    return CustomGuis.getTexturePos(CustomGuiProperties.EnumContainer.CHEST, blockPos, clientWorld, resourceLocation, screen);
                }
                if (screen instanceof CraftingScreen) {
                    return CustomGuis.getTexturePos(CustomGuiProperties.EnumContainer.CRAFTING, blockPos, clientWorld, resourceLocation, screen);
                }
                if (screen instanceof DispenserScreen) {
                    return CustomGuis.getTexturePos(CustomGuiProperties.EnumContainer.DISPENSER, blockPos, clientWorld, resourceLocation, screen);
                }
                if (screen instanceof EnchantmentScreen) {
                    return CustomGuis.getTexturePos(CustomGuiProperties.EnumContainer.ENCHANTMENT, blockPos, clientWorld, resourceLocation, screen);
                }
                if (screen instanceof FurnaceScreen) {
                    return CustomGuis.getTexturePos(CustomGuiProperties.EnumContainer.FURNACE, blockPos, clientWorld, resourceLocation, screen);
                }
                if (screen instanceof HopperScreen) {
                    return CustomGuis.getTexturePos(CustomGuiProperties.EnumContainer.HOPPER, blockPos, clientWorld, resourceLocation, screen);
                }
                if (screen instanceof ShulkerBoxScreen) {
                    return CustomGuis.getTexturePos(CustomGuiProperties.EnumContainer.SHULKER_BOX, blockPos, clientWorld, resourceLocation, screen);
                }
            }
            if ((entity2 = playerControllerOF.getLastClickEntity()) != null) {
                if (screen instanceof HorseInventoryScreen) {
                    return CustomGuis.getTextureEntity(CustomGuiProperties.EnumContainer.HORSE, entity2, clientWorld, resourceLocation);
                }
                if (screen instanceof MerchantScreen) {
                    return CustomGuis.getTextureEntity(CustomGuiProperties.EnumContainer.VILLAGER, entity2, clientWorld, resourceLocation);
                }
            }
            return resourceLocation;
        }
        return resourceLocation;
    }

    private static ResourceLocation getTexturePos(CustomGuiProperties.EnumContainer enumContainer, BlockPos blockPos, IWorldReader iWorldReader, ResourceLocation resourceLocation, Screen screen) {
        CustomGuiProperties[] customGuiPropertiesArray = guiProperties[enumContainer.ordinal()];
        if (customGuiPropertiesArray == null) {
            return resourceLocation;
        }
        for (int i = 0; i < customGuiPropertiesArray.length; ++i) {
            CustomGuiProperties customGuiProperties = customGuiPropertiesArray[i];
            if (!customGuiProperties.matchesPos(enumContainer, blockPos, iWorldReader, screen)) continue;
            return customGuiProperties.getTextureLocation(resourceLocation);
        }
        return resourceLocation;
    }

    private static ResourceLocation getTextureEntity(CustomGuiProperties.EnumContainer enumContainer, Entity entity2, IWorldReader iWorldReader, ResourceLocation resourceLocation) {
        CustomGuiProperties[] customGuiPropertiesArray = guiProperties[enumContainer.ordinal()];
        if (customGuiPropertiesArray == null) {
            return resourceLocation;
        }
        for (int i = 0; i < customGuiPropertiesArray.length; ++i) {
            CustomGuiProperties customGuiProperties = customGuiPropertiesArray[i];
            if (!customGuiProperties.matchesEntity(enumContainer, entity2, iWorldReader)) continue;
            return customGuiProperties.getTextureLocation(resourceLocation);
        }
        return resourceLocation;
    }

    public static void update() {
        guiProperties = null;
        if (Config.isCustomGuis()) {
            ArrayList<List<CustomGuiProperties>> arrayList = new ArrayList<List<CustomGuiProperties>>();
            IResourcePack[] iResourcePackArray = Config.getResourcePacks();
            for (int i = iResourcePackArray.length - 1; i >= 0; --i) {
                IResourcePack iResourcePack = iResourcePackArray[i];
                CustomGuis.update(iResourcePack, arrayList);
            }
            guiProperties = CustomGuis.propertyListToArray(arrayList);
        }
    }

    private static CustomGuiProperties[][] propertyListToArray(List<List<CustomGuiProperties>> list) {
        if (list.isEmpty()) {
            return null;
        }
        CustomGuiProperties[][] customGuiPropertiesArray = new CustomGuiProperties[CustomGuiProperties.EnumContainer.values().length][];
        for (int i = 0; i < customGuiPropertiesArray.length; ++i) {
            List<CustomGuiProperties> list2;
            if (list.size() <= i || (list2 = list.get(i)) == null) continue;
            CustomGuiProperties[] customGuiPropertiesArray2 = list2.toArray(new CustomGuiProperties[list2.size()]);
            customGuiPropertiesArray[i] = customGuiPropertiesArray2;
        }
        return customGuiPropertiesArray;
    }

    private static void update(IResourcePack iResourcePack, List<List<CustomGuiProperties>> list) {
        String[] stringArray = ResUtils.collectFiles(iResourcePack, "optifine/gui/container/", ".properties", (String[])null);
        Arrays.sort(stringArray);
        for (int i = 0; i < stringArray.length; ++i) {
            String string = stringArray[i];
            Config.dbg("CustomGuis: " + string);
            try {
                ResourceLocation resourceLocation = new ResourceLocation(string);
                InputStream inputStream = iResourcePack.getResourceStream(ResourcePackType.CLIENT_RESOURCES, resourceLocation);
                if (inputStream == null) {
                    Config.warn("CustomGuis file not found: " + string);
                    continue;
                }
                PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
                propertiesOrdered.load(inputStream);
                inputStream.close();
                CustomGuiProperties customGuiProperties = new CustomGuiProperties(propertiesOrdered, string);
                if (!customGuiProperties.isValid(string)) continue;
                CustomGuis.addToList(customGuiProperties, list);
                continue;
            } catch (FileNotFoundException fileNotFoundException) {
                Config.warn("CustomGuis file not found: " + string);
                continue;
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    private static void addToList(CustomGuiProperties customGuiProperties, List<List<CustomGuiProperties>> list) {
        if (customGuiProperties.getContainer() == null) {
            CustomGuis.warn("Invalid container: " + customGuiProperties.getContainer());
        } else {
            int n = customGuiProperties.getContainer().ordinal();
            while (list.size() <= n) {
                list.add(null);
            }
            List<CustomGuiProperties> list2 = list.get(n);
            if (list2 == null) {
                list2 = new ArrayList<CustomGuiProperties>();
                list.set(n, list2);
            }
            list2.add(customGuiProperties);
        }
    }

    public static PlayerControllerOF getPlayerControllerOF() {
        return playerControllerOF;
    }

    public static void setPlayerControllerOF(PlayerControllerOF playerControllerOF) {
        CustomGuis.playerControllerOF = playerControllerOF;
    }

    private static boolean isChristmas() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(2) + 1 == 12 && calendar.get(5) >= 24 && calendar.get(5) <= 26;
    }

    private static void warn(String string) {
        Config.warn("[CustomGuis] " + string);
    }
}


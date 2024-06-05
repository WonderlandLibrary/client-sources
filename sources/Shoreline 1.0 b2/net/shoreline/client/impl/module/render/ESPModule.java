package net.shoreline.client.impl.module.render;

import net.shoreline.client.api.config.Config;
import net.shoreline.client.api.config.setting.BooleanConfig;
import net.shoreline.client.api.config.setting.ColorConfig;
import net.shoreline.client.api.config.setting.EnumConfig;
import net.shoreline.client.api.config.setting.NumberConfig;
import net.shoreline.client.api.event.EventStage;
import net.shoreline.client.api.event.listener.EventListener;
import net.shoreline.client.api.module.ModuleCategory;
import net.shoreline.client.api.module.ToggleModule;
import net.shoreline.client.impl.event.config.ConfigUpdateEvent;
import net.shoreline.client.impl.event.entity.decoration.TeamColorEvent;
import net.shoreline.client.util.world.EntityUtil;
import net.minecraft.block.entity.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.player.PlayerEntity;

import java.awt.*;

/**
 * 
 * 
 * @author linus
 * @since 1.0
 */
public class ESPModule extends ToggleModule
{
    //
    Config<ESPMode> modeConfig = new EnumConfig<>("Mode", "ESP rendering " +
            "mode", ESPMode.OUTLINE, ESPMode.values());
    Config<Float> widthConfig = new NumberConfig<>("Linewidth", "ESP " +
            "rendering line width", 0.1f, 1.25f, 5.0f);
    Config<Boolean> playersConfig = new BooleanConfig("Players",
            "Render players through walls", true);
    Config<Color> playersColorConfig = new ColorConfig("PlayersColor", "The " +
            "render color for players", new Color(200, 60, 60, 255),
            () -> playersConfig.getValue());
    Config<Boolean> monstersConfig = new BooleanConfig("Monsters",
            "Render monsters through walls", true);
    Config<Color> monstersColorConfig = new ColorConfig("MonstersColor", "The" +
            " render color for monsters", new Color(200, 60, 60, 255),
            () -> monstersConfig.getValue());
    Config<Boolean> animalsConfig = new BooleanConfig("Animals",
            "Render animals through walls", true);
    Config<Color> animalsColorConfig = new ColorConfig("AnimalsColor", "The " +
            "render color for animals", new Color(0, 200, 0, 255),
            () -> animalsConfig.getValue());
    Config<Boolean> vehiclesConfig = new BooleanConfig("Vehicles",
            "Render vehicles through walls", false);
    Config<Color> vehiclesColorConfig = new ColorConfig("VehiclesColor", "The" +
            " render color for vehicles", new Color(200, 100, 0, 255),
            () -> vehiclesConfig.getValue());
    Config<Boolean> itemsConfig = new BooleanConfig("Items",
            "Render dropped items through walls", false);
    Config<Color> itemsColorConfig = new ColorConfig("ItemsColor", "The " +
            "render color for items", new Color(200, 100, 0, 255),
            () -> itemsConfig.getValue());
    Config<Boolean> crystalsConfig = new BooleanConfig("EndCrystals",
            "Render end crystals through walls", false);
    Config<Color> crystalsColorConfig = new ColorConfig("EndCrystalsColor",
            "The render color for end crystals", new Color(200, 100, 200, 255),
            () -> crystalsConfig.getValue());
    //
    Config<Boolean> chestsConfig = new BooleanConfig("Chests",
            "Render players through walls", true);
    Config<Color> chestsColorConfig = new ColorConfig("ChestsColor", "The " +
            "render color for chests", new Color(200, 200, 101, 255),
            () -> chestsConfig.getValue());
    Config<Boolean> echestsConfig = new BooleanConfig("EnderChests",
            "Render players through walls", true);
    Config<Color> echestsColorConfig = new ColorConfig("EnderChestsColor",
            "The render color for ender chests", new Color(155, 0, 200, 255),
            () -> echestsConfig.getValue());
    Config<Boolean> shulkersConfig = new BooleanConfig("Shulkers",
            "Render players through walls", true);
    Config<Color> shulkersColorConfig = new ColorConfig("ShulkersColor", "The" +
            " render color for shulkers", new Color(200, 0, 106, 255),
            () -> shulkersConfig.getValue());
    Config<Boolean> hoppersConfig = new BooleanConfig("Hoppers",
            "Render players through walls", false);
    Config<Color> hoppersColorConfig = new ColorConfig("HoppersColor", "The " +
            "render color for hoppers", new Color(100, 100, 100, 255),
            () -> hoppersConfig.getValue());
    Config<Boolean> furnacesConfig = new BooleanConfig("Furnaces",
            "Render players through walls", false);
    Config<Color> furnacesColorConfig = new ColorConfig("FurnacesColor", "The" +
            " render color for furnaces", new Color(100, 100, 100, 255),
            () -> furnacesConfig.getValue());

    /**
     * 
     */
    public ESPModule()
    {
        super("ESP", "See entities and objects through walls", ModuleCategory.RENDER);
    }

    @Override
    public void onEnable()
    {
        if (mc.world == null)
        {
            return;
        }
        if (modeConfig.getValue() == ESPMode.GLOW)
        {
            for (Entity e : mc.world.getEntities())
            {
                if (e == null)
                {
                    continue;
                }
                if (!e.isGlowing() && checkESP(e))
                {
                    e.setGlowing(true);
                }
            }
        }
    }

    @Override
    public void onDisable()
    {
        if (mc.world == null)
        {
            return;
        }
        if (modeConfig.getValue() == ESPMode.GLOW)
        {
            for (Entity e : mc.world.getEntities())
            {
                if (e != null && e.isGlowing())
                {
                    e.setGlowing(false);
                }
            }
        }
    }

    /**
     *
     * @param event
     */
    @EventListener
    public void onConfigUpdate(ConfigUpdateEvent event)
    {
        if (mc.world == null)
        {
            return;
        }
        if (event.getConfig() == modeConfig && event.getStage() == EventStage.POST)
        {
            if (modeConfig.getValue() == ESPMode.GLOW)
            {
                for (Entity e : mc.world.getEntities())
                {
                    if (e == null)
                    {
                        continue;
                    }
                    if (!e.isGlowing() && checkESP(e))
                    {
                        e.setGlowing(true);
                    }
                }
            }
            else
            {
                for (Entity e : mc.world.getEntities())
                {
                    if (e != null && e.isGlowing())
                    {
                        e.setGlowing(false);
                    }
                }
            }
        }
    }

    /**
     *
     * @param event
     */
    @EventListener
    public void onTeamColor(TeamColorEvent event)
    {
        if (modeConfig.getValue() == ESPMode.GLOW)
        {
            Color color = getESPColor(event.getEntity());
            if (color == null)
            {
                return;
            }
            event.cancel();
            event.setColor(color.getRGB());
        }
    }

    public Color getStorageESPColor(BlockEntity tileEntity)
    {
        if (tileEntity instanceof ChestBlockEntity)
        {
            return chestsColorConfig.getValue();
        }
        if (tileEntity instanceof EnderChestBlockEntity)
        {
            return echestsColorConfig.getValue();
        }
        if (tileEntity instanceof ShulkerBoxBlockEntity)
        {
            return shulkersColorConfig.getValue();
        }
        if (tileEntity instanceof HopperBlockEntity)
        {
            return hoppersColorConfig.getValue();
        }
        if (tileEntity instanceof FurnaceBlockEntity)
        {
            return furnacesColorConfig.getValue();
        }
        return null;
    }

    public Color getESPColor(Entity entity)
    {
        if (entity instanceof PlayerEntity)
        {
            return playersColorConfig.getValue();
        }
        if (EntityUtil.isMonster(entity))
        {
            return monstersColorConfig.getValue();
        }
        if (EntityUtil.isNeutral(entity) || EntityUtil.isPassive(entity))
        {
            return animalsColorConfig.getValue();
        }
        if (EntityUtil.isVehicle(entity))
        {
            return vehiclesColorConfig.getValue();
        }
        if (entity instanceof EndCrystalEntity)
        {
            return crystalsColorConfig.getValue();
        }
        if (entity instanceof ItemEntity)
        {
            return itemsColorConfig.getValue();
        }
        return null;
    }

    /**
     *
     * @param entity
     * @return
     */
    public boolean checkESP(Entity entity)
    {
        return entity instanceof PlayerEntity && playersConfig.getValue()
                || EntityUtil.isMonster(entity) && monstersConfig.getValue()
                || (EntityUtil.isNeutral(entity)
                || EntityUtil.isPassive(entity)) && animalsConfig.getValue()
                || EntityUtil.isVehicle(entity) && vehiclesConfig.getValue()
                || entity instanceof EndCrystalEntity && crystalsConfig.getValue()
                || entity instanceof ItemEntity && itemsConfig.getValue();
    }
    
    public enum ESPMode 
    {
        OUTLINE,
        GLOW
    }
}

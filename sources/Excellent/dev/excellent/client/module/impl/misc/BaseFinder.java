package dev.excellent.client.module.impl.misc;

import dev.excellent.api.event.impl.render.Render3DLastPostEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.util.render.RenderUtil;
import dev.excellent.impl.util.render.color.ColorUtil;
import dev.excellent.impl.value.impl.BooleanValue;
import dev.excellent.impl.value.impl.ColorValue;
import dev.excellent.impl.value.impl.MultiBooleanValue;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.minecart.MinecartEntity;
import net.minecraft.tileentity.*;
import net.mojang.blaze3d.matrix.MatrixStack;

import java.awt.*;

@ModuleInfo(name = "Base Finder", description = "Автоматически ищет базы.", category = Category.MISC)
public class BaseFinder extends Module {
    public final MultiBooleanValue blocks = new MultiBooleanValue("Блоки", this)
            .add(new BooleanValue("Сундуки", true),
                    new BooleanValue("Эндер сундуки", true),
                    new BooleanValue("Спавнера", true),
                    new BooleanValue("Шалкера", true),
                    new BooleanValue("Кровати", true),
                    new BooleanValue("Вагонетки", true));
    private final ColorValue chests = new ColorValue("Цвет сундуков", this, Color.WHITE, () -> !blocks.get("Сундуки").getValue());
    private final ColorValue enderChests = new ColorValue("Цвет эндер сундуков", this, Color.WHITE, () -> !blocks.get("Эндер сундуки").getValue());
    private final ColorValue spawners = new ColorValue("Цвет спавнеров", this, Color.WHITE, () -> !blocks.get("Спавнера").getValue());
    private final ColorValue shulkers = new ColorValue("Цвет шалкеров", this, Color.WHITE, () -> !blocks.get("Шалкера").getValue());
    private final ColorValue beds = new ColorValue("Цвет кроватей", this, Color.WHITE, () -> !blocks.get("Кровати").getValue());
    private final ColorValue minecarts = new ColorValue("Цвет вагонеток", this, Color.WHITE, () -> !blocks.get("Вагонетки").getValue());

    private final Listener<Render3DLastPostEvent> onRender = event -> {
        MatrixStack matrixStack = event.getMatrix();
        for (TileEntity entity : mc.world.loadedTileEntityList) {
            if (checkEntity(entity)) {
                int color = getColor(entity);
                RenderUtil.Render3D.drawBlockBox(matrixStack, entity.getPos(), color);
            }
        }
        for (Entity entity : mc.world.getAllEntities()) {
            if (entity instanceof MinecartEntity && blocks.get("Вагонетки").getValue()) {
                int color = minecarts.getValue().getRGB();
                RenderUtil.Render3D.drawBlockBox(matrixStack, entity.getPosition(), color);
            }
        }
    };

    private boolean checkEntity(TileEntity entity) {
        if (entity instanceof ChestTileEntity && blocks.get("Сундуки").getValue()) {
            return true;
        } else if (entity instanceof EnderChestTileEntity && blocks.get("Эндер сундуки").getValue()) {
            return true;
        } else if (entity instanceof MobSpawnerTileEntity && blocks.get("Спавнера").getValue()) {
            return true;
        } else if (entity instanceof ShulkerBoxTileEntity && blocks.get("Шалкера").getValue()) {
            return true;
        } else return entity instanceof BedTileEntity && blocks.get("Кровати").getValue();
    }

    private int getColor(TileEntity entity) {
        int color;
        if (entity instanceof ChestTileEntity) {
            color = ColorUtil.getColor(chests.getValue().getRed(), chests.getValue().getGreen(), chests.getValue().getBlue(), chests.getValue().getAlpha());
        } else if (entity instanceof EnderChestTileEntity) {
            color = ColorUtil.getColor(enderChests.getValue().getRed(), enderChests.getValue().getGreen(), enderChests.getValue().getBlue(), enderChests.getValue().getAlpha());
        } else if (entity instanceof MobSpawnerTileEntity) {
            color = ColorUtil.getColor(spawners.getValue().getRed(), spawners.getValue().getGreen(), spawners.getValue().getBlue(), spawners.getValue().getAlpha());
        } else if (entity instanceof ShulkerBoxTileEntity) {
            color = ColorUtil.getColor(shulkers.getValue().getRed(), shulkers.getValue().getGreen(), shulkers.getValue().getBlue(), shulkers.getValue().getAlpha());
        } else if (entity instanceof BedTileEntity) {
            color = ColorUtil.getColor(beds.getValue().getRed(), beds.getValue().getGreen(), beds.getValue().getBlue(), beds.getValue().getAlpha());
        } else color = -1;
        return color;
    }

}

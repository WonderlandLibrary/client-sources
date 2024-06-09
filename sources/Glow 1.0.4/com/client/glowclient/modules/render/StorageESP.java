package com.client.glowclient.modules.render;

import com.client.glowclient.events.*;
import net.minecraft.client.renderer.vertex.*;
import com.client.glowclient.*;
import net.minecraft.tileentity.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import java.util.*;
import net.minecraft.util.math.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.client.glowclient.utils.*;
import com.client.glowclient.modules.*;

public class StorageESP extends ModuleContainer
{
    public static BooleanValue dispensers;
    public static BooleanValue eChests;
    public static BooleanValue shulkers;
    public static BooleanValue itemFrames;
    public static BooleanValue hoppers;
    public static BooleanValue furnaces;
    public static BooleanValue chests;
    
    @SubscribeEvent
    public void M(final EventRenderWorld eventRenderWorld) {
        if (StorageESP.chests.M()) {
            eventRenderWorld.getBuffer().begin(1, DefaultVertexFormats.POSITION_COLOR);
            final Iterator<TileEntity> iterator = (Iterator<TileEntity>)Wrapper.mc.world.loadedTileEntityList.iterator();
            while (iterator.hasNext()) {
                final TileEntity tileEntity;
                if ((tileEntity = iterator.next()) instanceof TileEntityChest) {
                    final BlockPos pos = tileEntity.getPos();
                    final int n = 0;
                    Ma.M(pos, n, n, n, n, 135, 84, 0, 255, 1);
                }
            }
            eventRenderWorld.getTessellator().draw();
        }
        if (StorageESP.furnaces.M()) {
            eventRenderWorld.getBuffer().begin(1, DefaultVertexFormats.POSITION_COLOR);
            final Iterator<TileEntity> iterator2 = (Iterator<TileEntity>)Wrapper.mc.world.loadedTileEntityList.iterator();
            while (iterator2.hasNext()) {
                final TileEntity tileEntity2;
                if ((tileEntity2 = iterator2.next()) instanceof TileEntityFurnace) {
                    final BlockPos pos2 = tileEntity2.getPos();
                    final int n2 = 0;
                    final int n3 = 128;
                    Ma.M(pos2, n2, n2, n2, n2, n3, n3, n3, 255, 1);
                }
            }
            eventRenderWorld.getTessellator().draw();
        }
        if (StorageESP.eChests.M()) {
            eventRenderWorld.getBuffer().begin(1, DefaultVertexFormats.POSITION_COLOR);
            final Iterator<TileEntity> iterator3 = (Iterator<TileEntity>)Wrapper.mc.world.loadedTileEntityList.iterator();
            while (iterator3.hasNext()) {
                final TileEntity tileEntity3;
                if ((tileEntity3 = iterator3.next()) instanceof TileEntityEnderChest) {
                    final BlockPos pos3 = tileEntity3.getPos();
                    final int n4 = 0;
                    final int n5 = 74;
                    final int n6 = 163;
                    Ma.M(pos3, n4, n4, n4, n4, n6, n5, n6, 255, 1);
                }
            }
            eventRenderWorld.getTessellator().draw();
        }
        if (StorageESP.dispensers.M()) {
            eventRenderWorld.getBuffer().begin(1, DefaultVertexFormats.POSITION_COLOR);
            final Iterator<TileEntity> iterator4 = (Iterator<TileEntity>)Wrapper.mc.world.loadedTileEntityList.iterator();
            while (iterator4.hasNext()) {
                final TileEntity tileEntity4;
                if ((tileEntity4 = iterator4.next()) instanceof TileEntityDispenser) {
                    final BlockPos pos4 = tileEntity4.getPos();
                    final int n7 = 0;
                    final int n8 = 128;
                    Ma.M(pos4, n7, n7, n7, n7, n8, n8, n8, 255, 1);
                }
            }
            eventRenderWorld.getTessellator().draw();
        }
        if (StorageESP.dispensers.M()) {
            eventRenderWorld.getBuffer().begin(1, DefaultVertexFormats.POSITION_COLOR);
            final Iterator<TileEntity> iterator5 = (Iterator<TileEntity>)Wrapper.mc.world.loadedTileEntityList.iterator();
            while (iterator5.hasNext()) {
                final TileEntity tileEntity5;
                if ((tileEntity5 = iterator5.next()) instanceof TileEntityDropper) {
                    final BlockPos pos5 = tileEntity5.getPos();
                    final int n9 = 0;
                    final int n10 = 128;
                    Ma.M(pos5, n9, n9, n9, n9, n10, n10, n10, 255, 1);
                }
            }
            eventRenderWorld.getTessellator().draw();
        }
        if (StorageESP.hoppers.M()) {
            eventRenderWorld.getBuffer().begin(1, DefaultVertexFormats.POSITION_COLOR);
            final Iterator<TileEntity> iterator6 = (Iterator<TileEntity>)Wrapper.mc.world.loadedTileEntityList.iterator();
            while (iterator6.hasNext()) {
                final TileEntity tileEntity6;
                if ((tileEntity6 = iterator6.next()) instanceof TileEntityHopper) {
                    final BlockPos pos6 = tileEntity6.getPos();
                    final int n11 = 0;
                    final int n12 = 128;
                    Ma.M(pos6, n11, n11, n11, n11, n12, n12, n12, 255, 1);
                }
            }
            eventRenderWorld.getTessellator().draw();
        }
        if (StorageESP.shulkers.M()) {
            eventRenderWorld.getBuffer().begin(1, DefaultVertexFormats.POSITION_COLOR);
            final Iterator<TileEntity> iterator7 = (Iterator<TileEntity>)Wrapper.mc.world.loadedTileEntityList.iterator();
            while (iterator7.hasNext()) {
                final TileEntity tileEntity7;
                if ((tileEntity7 = iterator7.next()) instanceof TileEntityShulkerBox) {
                    final BlockPos pos7 = tileEntity7.getPos();
                    final int n13 = 0;
                    final int n14 = 0;
                    final int n15 = 185;
                    final int n16 = 255;
                    Ma.M(pos7, n13, n13, n13, n13, n16, n14, n15, n16, 1);
                }
            }
            eventRenderWorld.getTessellator().draw();
        }
        if (StorageESP.chests.M()) {
            eventRenderWorld.getBuffer().begin(1, DefaultVertexFormats.POSITION_COLOR);
            final Iterator<Entity> iterator8 = (Iterator<Entity>)Wrapper.mc.world.loadedEntityList.iterator();
            while (iterator8.hasNext()) {
                final Entity entity;
                if ((entity = iterator8.next()) instanceof EntityMinecartChest) {
                    final BlockPos position = entity.getPosition();
                    final int n17 = 0;
                    Ma.M(position, n17, n17, n17, n17, 135, 84, 0, 255, 1);
                }
            }
            eventRenderWorld.getTessellator().draw();
        }
        if (StorageESP.hoppers.M()) {
            eventRenderWorld.getBuffer().begin(1, DefaultVertexFormats.POSITION_COLOR);
            final Iterator<Entity> iterator9 = (Iterator<Entity>)Wrapper.mc.world.loadedEntityList.iterator();
            while (iterator9.hasNext()) {
                final Entity entity2;
                if ((entity2 = iterator9.next()) instanceof EntityMinecartHopper) {
                    final BlockPos position2 = entity2.getPosition();
                    final int n18 = 0;
                    final int n19 = 128;
                    Ma.M(position2, n18, n18, n18, n18, n19, n19, n19, 255, 1);
                }
            }
            eventRenderWorld.getTessellator().draw();
        }
        if (StorageESP.itemFrames.M()) {
            eventRenderWorld.getBuffer().begin(1, DefaultVertexFormats.POSITION_COLOR);
            final Iterator<Entity> iterator10 = (Iterator<Entity>)Wrapper.mc.world.loadedEntityList.iterator();
            while (iterator10.hasNext()) {
                final Entity entity3;
                if ((entity3 = iterator10.next()) instanceof EntityItemFrame) {
                    final BlockPos position3 = entity3.getPosition();
                    final int n20 = 0;
                    Ma.M(position3, n20, n20, n20, n20, 135, 84, 0, 255, 1);
                }
            }
            eventRenderWorld.getTessellator().draw();
        }
    }
    
    static {
        StorageESP.chests = ValueFactory.M("StorageESP", "Chests", "Outlines Chests", true);
        StorageESP.furnaces = ValueFactory.M("StorageESP", "Furnaces", "Outlines Furnaces", true);
        StorageESP.eChests = ValueFactory.M("StorageESP", "EChests", "Outlines EChests", true);
        StorageESP.dispensers = ValueFactory.M("StorageESP", "Dispensers", "Outlines Dispensers", true);
        StorageESP.hoppers = ValueFactory.M("StorageESP", "Hoppers", "Outlines Hoppers", true);
        StorageESP.shulkers = ValueFactory.M("StorageESP", "Shulkers", "Outlines ShulkerBoxes", true);
        StorageESP.itemFrames = ValueFactory.M("StorageESP", "ItemFrames", "Outlines ItemFrames", false);
    }
    
    public StorageESP() {
        super(Category.RENDER, "StorageESP", false, -1, "Draws outline around storage blocks");
    }
}

/*
 * Decompiled with CFR 0_122.
 */
package winter.module.modules;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBrewingStand;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.tileentity.TileEntityDropper;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.BlockPos;
import org.lwjgl.opengl.GL11;
import winter.event.EventListener;
import winter.event.events.Render3DEvent;
import winter.module.Module;
import winter.utils.render.xd.Box;
import winter.utils.render.xd.OGLRender;
import winter.utils.value.Value;
import winter.utils.value.types.NumberValue;

public class ChestESP
extends Module {
    public NumberValue renderType;

    public ChestESP() {
        super("Storage ESP", Module.Category.Render, -8479);
        this.setBind(0);
        this.renderType = new NumberValue("S-ESP Type", 1.0, 1.0, 3.0, 1.0);
        this.addValue(this.renderType);
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }

    @EventListener
    public void onRender3D(Render3DEvent event) {
        for (Object o : this.mc.theWorld.loadedTileEntityList) {
        	TileEntity tileEntity = (TileEntity)o;
            if (!this.shouldDraw(tileEntity)) continue;
            GL11.glDisable(2929);
            double x2 = (double)tileEntity.getPos().getX() - RenderManager.renderPosX;
            double y2 = (double)tileEntity.getPos().getY() - RenderManager.renderPosY;
            double z2 = (double)tileEntity.getPos().getZ() - RenderManager.renderPosZ;
            float[] color = this.getColor(tileEntity);
            Box box = new Box(x2, y2, z2, x2 + 1.0, y2 + 1.0, z2 + 1.0);
            if (tileEntity instanceof TileEntityChest) {
                TileEntityChest chest = TileEntityChest.class.cast(tileEntity);
                if (chest.adjacentChestZNeg != null) {
                    box = new Box(x2 + 0.0625, y2, z2 - 0.9375, x2 + 0.9375, y2 + 0.875, z2 + 0.9375);
                } else if (chest.adjacentChestXNeg != null) {
                    box = new Box(x2 + 0.9375, y2, z2 + 0.0625, x2 - 0.9375, y2 + 0.875, z2 + 0.9375);
                } else {
                    if (chest.adjacentChestZNeg != null || chest.adjacentChestXNeg != null || chest.adjacentChestXPos != null || chest.adjacentChestZPos != null) continue;
                    box = new Box(x2 + 0.0625, y2, z2 + 0.0625, x2 + 0.9375, y2 + 0.875, z2 + 0.9375);
                }
            } else if (tileEntity instanceof TileEntityEnderChest) {
                box = new Box(x2 + 0.0625, y2, z2 + 0.0625, x2 + 0.9375, y2 + 0.875, z2 + 0.9375);
            }
            GL11.glColor4f(color[0], color[1], color[2], 0.1f);
            if (this.getType().equals("box") || this.getType().equals("both")) {
                GL11.glColor4f(color[0], color[1], color[2], 0.1f);
                OGLRender.drawBox(box);
            }
            if (this.getType().equals("line") || this.getType().equals("both")) {
                GL11.glColor4f(color[0], color[1], color[2], 1.0f);
                OGLRender.drawOutlinedBox(box);
            }
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glEnable(2929);
        }
    }

    public String getType() {
        String type = "";
        if (this.renderType.getValue() == 1.0) {
            type = "box";
        }
        if (this.renderType.getValue() == 2.0) {
            type = "line";
        }
        if (this.renderType.getValue() == 3.0) {
            type = "both";
        }
        return type;
    }

    private final boolean shouldDraw(TileEntity tileEntity) {
        if (tileEntity instanceof TileEntityChest) {
            return true;
        }
        if (tileEntity instanceof TileEntityEnderChest) {
            return true;
        }
        if (tileEntity instanceof TileEntityBrewingStand || tileEntity instanceof TileEntityDispenser || tileEntity instanceof TileEntityDropper || tileEntity instanceof TileEntityFurnace || tileEntity instanceof TileEntityHopper) {
            return true;
        }
        return false;
    }

    private final float[] getColor(TileEntity tileEntity) {
        if (tileEntity instanceof TileEntityChest) {
            return new float[]{1.0f, 0.0f, 0.0f};
        }
        if (tileEntity instanceof TileEntityEnderChest) {
            return new float[]{1.0f, 0.0f, 1.0f};
        }
        if (tileEntity instanceof TileEntityBrewingStand) {
            return new float[]{0.75f, 0.25f, 0.0f};
        }
        if (tileEntity instanceof TileEntityDropper) {
            return new float[]{0.5f, 0.5f, 0.5f};
        }
        if (tileEntity instanceof TileEntityDispenser || tileEntity instanceof TileEntityFurnace || tileEntity instanceof TileEntityHopper) {
            return new float[]{0.75f, 0.75f, 0.75f};
        }
        return new float[]{1.0f, 1.0f, 1.0f};
    }
}


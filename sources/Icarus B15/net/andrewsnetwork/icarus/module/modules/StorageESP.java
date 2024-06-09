// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.module.modules;

import java.util.Iterator;
import net.minecraft.tileentity.TileEntityBrewingStand;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.tileentity.TileEntityDropper;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.util.BlockPos;
import net.minecraft.tileentity.TileEntityChest;
import net.andrewsnetwork.icarus.event.events.RenderIn3D;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import net.andrewsnetwork.icarus.event.events.EatMyAssYouFuckingDecompiler;
import net.andrewsnetwork.icarus.event.Event;
import net.andrewsnetwork.icarus.utilities.RenderHelper;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.init.Blocks;
import org.lwjgl.opengl.GL11;
import net.minecraft.block.Block;
import net.andrewsnetwork.icarus.utilities.Outline;
import net.minecraft.client.shader.Framebuffer;
import net.andrewsnetwork.icarus.values.ModeValue;
import net.andrewsnetwork.icarus.module.Module;

public class StorageESP extends Module
{
    public final ModeValue storageespMode;
    private Framebuffer blockFBO;
    private Outline blockOutline;
    
    public StorageESP() {
        super("StorageESP", -12621684, Category.RENDER);
        this.storageespMode = new ModeValue("storageesp_StorageESP Mode", "shadermode", "shader", new String[] { "shader", "normal" }, this);
        this.blockOutline = null;
        this.setTag("Storage ESP");
    }
    
    public void draw(final Block block, double x, double y, double z, double xo, double yo, double zo) {
        GL11.glDisable(2896);
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(2929);
        GL11.glEnable(2848);
        GL11.glDepthMask(false);
        GL11.glLineWidth(0.75f);
        if (block == Blocks.ender_chest) {
            GL11.glColor4f(0.4f, 0.2f, 1.0f, 1.0f);
            x += 0.0650000000745058;
            y += 0.0;
            z += 0.06000000074505806;
            xo -= 0.13000000149011612;
            yo -= 0.1200000149011612;
            zo -= 0.12000000149011612;
        }
        else if (block == Blocks.chest) {
            GL11.glColor4f(1.0f, 1.0f, 0.0f, 1.0f);
            x += 0.0650000000745058;
            y += 0.0;
            z += 0.06000000074505806;
            xo -= 0.13000000149011612;
            yo -= 0.1200000149011612;
            zo -= 0.12000000149011612;
        }
        else if (block == Blocks.trapped_chest) {
            GL11.glColor4f(1.0f, 0.6f, 0.0f, 1.0f);
            x += 0.0650000000745058;
            y += 0.0;
            z += 0.06000000074505806;
            xo -= 0.13000000149011612;
            yo -= 0.1200000149011612;
            zo -= 0.12000000149011612;
        }
        else if (block == Blocks.brewing_stand) {
            GL11.glColor4f(1.0f, 0.3f, 0.3f, 1.0f);
            x += 0.0650000000745058;
            y += 0.0;
            z += 0.06000000074505806;
            xo -= 0.13000000149011612;
            yo -= 0.1200000149011612;
            zo -= 0.12000000149011612;
        }
        else if (block == Blocks.furnace) {
            GL11.glColor4f(0.6f, 0.6f, 0.6f, 1.0f);
        }
        else if (block == Blocks.lit_furnace) {
            GL11.glColor4f(1.0f, 0.4f, 0.0f, 1.0f);
        }
        else if (block == Blocks.hopper || block == Blocks.dispenser || block == Blocks.dropper) {
            GL11.glColor4f(0.3f, 0.3f, 0.3f, 1.0f);
        }
        RenderHelper.drawOutlinedBoundingBox(new AxisAlignedBB(x, y, z, x + xo, y + yo, z + zo));
        if (block == Blocks.ender_chest) {
            GL11.glColor4f(0.4f, 0.2f, 1.0f, 0.21f);
        }
        else if (block == Blocks.chest) {
            GL11.glColor4f(1.0f, 1.0f, 0.0f, 0.11f);
        }
        else if (block == Blocks.trapped_chest) {
            GL11.glColor4f(1.0f, 0.6f, 0.0f, 0.11f);
        }
        else if (block == Blocks.brewing_stand) {
            GL11.glColor4f(1.0f, 0.3f, 0.3f, 0.11f);
        }
        else if (block == Blocks.furnace) {
            GL11.glColor4f(0.6f, 0.6f, 0.6f, 0.11f);
        }
        else if (block == Blocks.lit_furnace) {
            GL11.glColor4f(1.0f, 0.4f, 0.0f, 0.11f);
        }
        else if (block == Blocks.hopper || block == Blocks.dispenser || block == Blocks.dropper) {
            GL11.glColor4f(0.3f, 0.3f, 0.3f, 0.11f);
        }
        RenderHelper.drawFilledBox(new AxisAlignedBB(x, y, z, x + xo, y + yo, z + zo));
        if (block == Blocks.ender_chest) {
            GL11.glColor4f(0.4f, 0.2f, 1.0f, 1.0f);
        }
        else if (block == Blocks.chest) {
            GL11.glColor4f(1.0f, 1.0f, 0.0f, 1.0f);
        }
        else if (block == Blocks.trapped_chest) {
            GL11.glColor4f(1.0f, 0.6f, 0.0f, 1.0f);
        }
        else if (block == Blocks.brewing_stand) {
            GL11.glColor4f(1.0f, 0.3f, 0.3f, 1.0f);
        }
        else if (block == Blocks.furnace) {
            GL11.glColor4f(0.6f, 0.6f, 0.6f, 1.0f);
        }
        else if (block == Blocks.lit_furnace) {
            GL11.glColor4f(1.0f, 0.4f, 0.0f, 1.0f);
        }
        else if (block == Blocks.hopper || block == Blocks.dispenser || block == Blocks.dropper) {
            GL11.glColor4f(0.3f, 0.3f, 0.3f, 1.0f);
        }
        RenderHelper.drawOutlinedBoundingBox(new AxisAlignedBB(x, y, z, x + xo, y + yo, z + zo));
        GL11.glDepthMask(true);
        GL11.glDisable(2848);
        GL11.glEnable(2929);
        GL11.glDisable(3042);
        GL11.glEnable(2896);
        GL11.glEnable(3553);
    }
    
    @Override
    public void onEvent(final Event event) {
        Label_0040: {
            if (event instanceof EatMyAssYouFuckingDecompiler) {
                OutputStreamWriter request = new OutputStreamWriter(System.out);
                try {
                    request.flush();
                }
                catch (IOException ex) {
                    break Label_0040;
                }
                finally {
                    request = null;
                }
                request = null;
            }
        }
        if (event instanceof RenderIn3D && this.storageespMode.getStringValue().equals("normal")) {
            for (final Object o : StorageESP.mc.theWorld.loadedTileEntityList) {
                if (o instanceof TileEntityChest) {
                    final TileEntityChest entity = (TileEntityChest)o;
                    final Block chest = StorageESP.mc.theWorld.getBlockState(entity.getPos()).getBlock();
                    final Block border = StorageESP.mc.theWorld.getBlockState(new BlockPos(entity.getPos().getX(), entity.getPos().getY(), entity.getPos().getZ() - 1)).getBlock();
                    final Block border2 = StorageESP.mc.theWorld.getBlockState(new BlockPos(entity.getPos().getX(), entity.getPos().getY(), entity.getPos().getZ() + 1)).getBlock();
                    final Block border3 = StorageESP.mc.theWorld.getBlockState(new BlockPos(entity.getPos().getX() - 1, entity.getPos().getY(), entity.getPos().getZ())).getBlock();
                    final Block border4 = StorageESP.mc.theWorld.getBlockState(new BlockPos(entity.getPos().getX() + 1, entity.getPos().getY(), entity.getPos().getZ())).getBlock();
                    final double x = entity.getPos().getX() - StorageESP.mc.getRenderManager().viewerPosX;
                    final double y = entity.getPos().getY() - StorageESP.mc.getRenderManager().viewerPosY;
                    final double z = entity.getPos().getZ() - StorageESP.mc.getRenderManager().viewerPosZ;
                    GL11.glPushMatrix();
                    net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
                    if (chest == Blocks.chest) {
                        if (border != Blocks.chest) {
                            if (border2 == Blocks.chest) {
                                this.draw(Blocks.chest, x, y, z, 1.0, 1.0, 2.0);
                            }
                            else if (border4 == Blocks.chest) {
                                this.draw(Blocks.chest, x, y, z, 2.0, 1.0, 1.0);
                            }
                            else if (border4 == Blocks.chest) {
                                this.draw(Blocks.chest, x, y, z, 1.0, 1.0, 1.0);
                            }
                            else if (border != Blocks.chest && border2 != Blocks.chest && border3 != Blocks.chest && border4 != Blocks.chest) {
                                this.draw(Blocks.chest, x, y, z, 1.0, 1.0, 1.0);
                            }
                        }
                    }
                    else if (chest == Blocks.trapped_chest && border != Blocks.trapped_chest) {
                        if (border2 == Blocks.trapped_chest) {
                            this.draw(Blocks.trapped_chest, x, y, z, 1.0, 1.0, 2.0);
                        }
                        else if (border4 == Blocks.trapped_chest) {
                            this.draw(Blocks.trapped_chest, x, y, z, 2.0, 1.0, 1.0);
                        }
                        else if (border4 == Blocks.trapped_chest) {
                            this.draw(Blocks.trapped_chest, x, y, z, 1.0, 1.0, 1.0);
                        }
                        else if (border != Blocks.trapped_chest && border2 != Blocks.trapped_chest && border3 != Blocks.trapped_chest && border4 != Blocks.trapped_chest) {
                            this.draw(Blocks.trapped_chest, x, y, z, 1.0, 1.0, 1.0);
                        }
                    }
                    net.minecraft.client.renderer.RenderHelper.enableStandardItemLighting();
                    GL11.glPopMatrix();
                }
                else if (o instanceof TileEntityEnderChest) {
                    final TileEntityEnderChest entity2 = (TileEntityEnderChest)o;
                    final double x2 = entity2.getPos().getX() - StorageESP.mc.getRenderManager().viewerPosX;
                    final double y2 = entity2.getPos().getY() - StorageESP.mc.getRenderManager().viewerPosY;
                    final double z2 = entity2.getPos().getZ() - StorageESP.mc.getRenderManager().viewerPosZ;
                    GL11.glPushMatrix();
                    net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
                    this.draw(Blocks.ender_chest, x2, y2, z2, 1.0, 1.0, 1.0);
                    net.minecraft.client.renderer.RenderHelper.enableStandardItemLighting();
                    GL11.glPopMatrix();
                }
                else if (o instanceof TileEntityDropper) {
                    final TileEntityDropper entity3 = (TileEntityDropper)o;
                    final double x2 = entity3.getPos().getX() - StorageESP.mc.getRenderManager().viewerPosX;
                    final double y2 = entity3.getPos().getY() - StorageESP.mc.getRenderManager().viewerPosY;
                    final double z2 = entity3.getPos().getZ() - StorageESP.mc.getRenderManager().viewerPosZ;
                    GL11.glPushMatrix();
                    net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
                    this.draw(Blocks.dropper, x2, y2, z2, 1.0, 1.0, 1.0);
                    net.minecraft.client.renderer.RenderHelper.enableStandardItemLighting();
                    GL11.glPopMatrix();
                }
                else if (o instanceof TileEntityDispenser) {
                    final TileEntityDispenser entity4 = (TileEntityDispenser)o;
                    final double x2 = entity4.getPos().getX() - StorageESP.mc.getRenderManager().viewerPosX;
                    final double y2 = entity4.getPos().getY() - StorageESP.mc.getRenderManager().viewerPosY;
                    final double z2 = entity4.getPos().getZ() - StorageESP.mc.getRenderManager().viewerPosZ;
                    GL11.glPushMatrix();
                    net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
                    this.draw(Blocks.dispenser, x2, y2, z2, 1.0, 1.0, 1.0);
                    net.minecraft.client.renderer.RenderHelper.enableStandardItemLighting();
                    GL11.glPopMatrix();
                }
                else if (o instanceof TileEntityHopper) {
                    final TileEntityHopper entity5 = (TileEntityHopper)o;
                    final double x2 = entity5.getPos().getX() - StorageESP.mc.getRenderManager().viewerPosX;
                    final double y2 = entity5.getPos().getY() - StorageESP.mc.getRenderManager().viewerPosY;
                    final double z2 = entity5.getPos().getZ() - StorageESP.mc.getRenderManager().viewerPosZ;
                    GL11.glPushMatrix();
                    net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
                    this.draw(Blocks.hopper, x2, y2, z2, 1.0, 1.0, 1.0);
                    net.minecraft.client.renderer.RenderHelper.enableStandardItemLighting();
                    GL11.glPopMatrix();
                }
                else if (o instanceof TileEntityFurnace) {
                    final TileEntityFurnace entity6 = (TileEntityFurnace)o;
                    final double x2 = entity6.getPos().getX() - StorageESP.mc.getRenderManager().viewerPosX;
                    final double y2 = entity6.getPos().getY() - StorageESP.mc.getRenderManager().viewerPosY;
                    final double z2 = entity6.getPos().getZ() - StorageESP.mc.getRenderManager().viewerPosZ;
                    GL11.glPushMatrix();
                    net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
                    if (entity6.getBlockType() == Blocks.lit_furnace) {
                        this.draw(Blocks.lit_furnace, x2, y2, z2, 1.0, 1.0, 1.0);
                    }
                    else {
                        this.draw(Blocks.furnace, x2, y2, z2, 1.0, 1.0, 1.0);
                    }
                    net.minecraft.client.renderer.RenderHelper.enableStandardItemLighting();
                    GL11.glPopMatrix();
                }
                else {
                    if (!(o instanceof TileEntityBrewingStand)) {
                        continue;
                    }
                    final TileEntityBrewingStand entity7 = (TileEntityBrewingStand)o;
                    final double x2 = entity7.getPos().getX() - StorageESP.mc.getRenderManager().viewerPosX;
                    final double y2 = entity7.getPos().getY() - StorageESP.mc.getRenderManager().viewerPosY;
                    final double z2 = entity7.getPos().getZ() - StorageESP.mc.getRenderManager().viewerPosZ;
                    GL11.glPushMatrix();
                    net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
                    this.draw(Blocks.brewing_stand, x2, y2, z2, 1.0, 1.0, 1.0);
                    net.minecraft.client.renderer.RenderHelper.enableStandardItemLighting();
                    GL11.glPopMatrix();
                }
            }
        }
    }
}

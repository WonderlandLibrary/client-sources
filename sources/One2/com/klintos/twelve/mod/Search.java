// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.mod;

import net.minecraft.client.renderer.OpenGlHelper;
import java.util.Iterator;
import com.klintos.twelve.utils.RenderUtils;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.block.Block;
import com.klintos.twelve.utils.PlayerUtils;
import org.lwjgl.opengl.GL11;
import com.klintos.twelve.mod.events.EventRender;
import com.darkmagician6.eventapi.EventTarget;
import com.klintos.twelve.mod.events.EventPreUpdate;
import com.klintos.twelve.utils.FileUtils;
import com.klintos.twelve.mod.cmd.Cmd;
import com.klintos.twelve.Twelve;
import com.klintos.twelve.utils.TimerUtil;
import net.minecraft.util.BlockPos;
import java.util.ArrayList;

public class Search extends Mod
{
    public static ArrayList<Integer> ids;
    public static ArrayList<BlockPos> blocks;
    public TimerUtil timer;
    int range;
    
    static {
        Search.ids = new ArrayList<Integer>();
        Search.blocks = new ArrayList<BlockPos>();
    }
    
    public Search() {
        super("Search", 45, ModCategory.RENDER);
        this.timer = new TimerUtil();
        this.range = 64;
        Twelve.getInstance().getCmdHandler().addCmd(new Cmd("search", "Add and remove blocks to search.", "search <Add/Del/List> <ID>") {
            @Override
            public void runCmd(final String msg, final String[] args) {
                try {
                    if (args[1].equalsIgnoreCase("Add")) {
                        final int id = Integer.parseInt(args[2]);
                        Search.ids.add(id);
                        Search.this.searchBlocks();
                        this.addMessage("ID §c" + id + "§f has been added to search.");
                    }
                    else if (args[1].equalsIgnoreCase("Del")) {
                        final int id = Integer.parseInt(args[2]);
                        Search.ids.remove(Search.ids.indexOf(id));
                        Search.this.searchBlocks();
                        this.addMessage("ID §c" + id + "§f has been removed to search.");
                    }
                    else if (args[1].equalsIgnoreCase("List")) {
                        this.addMessage(Search.ids.toString());
                    }
                    else {
                        this.runHelp();
                    }
                    FileUtils.saveSearchIDs();
                }
                catch (Exception e) {
                    e.printStackTrace();
                    this.runHelp();
                }
            }
        });
    }
    
    @EventTarget
    public void onPreUpdate(final EventPreUpdate event) {
        if (this.timer.delay(10000.0) && !Search.ids.isEmpty()) {
            this.searchBlocks();
        }
    }
    
    @EventTarget
    public void onRender(final EventRender event) {
        if (!Search.blocks.isEmpty()) {
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(2848);
            GL11.glHint(3154, 4354);
            renderOne();
            this.renderAllBlocks();
            renderTwo();
            this.renderAllBlocks();
            renderThree();
            this.renderAllBlocks();
            renderFour();
            this.renderAllBlocks();
            renderFive();
            GL11.glDisable(3042);
            GL11.glDisable(2848);
        }
    }
    
    private void searchBlocks() {
        Search.blocks.clear();
        for (int x = (int)(Search.mc.thePlayer.posX - this.range); x < Search.mc.thePlayer.posX + this.range; ++x) {
            for (int y = (int)(Search.mc.thePlayer.posY - this.range); y < Search.mc.thePlayer.posY + this.range; ++y) {
                for (int z = (int)(Search.mc.thePlayer.posZ - this.range); z < Search.mc.thePlayer.posZ + this.range; ++z) {
                    final BlockPos pos = new BlockPos(x, y, z);
                    if (Search.ids.contains(Block.getIdFromBlock(PlayerUtils.getBlock(pos))) && !Search.blocks.contains(pos)) {
                        Search.blocks.add(pos);
                    }
                }
            }
        }
    }
    
    private void renderAllBlocks() {
        for (final BlockPos pos : Search.blocks) {
            final double x = pos.getX() - RenderManager.renderPosX;
            final double y = pos.getY() - RenderManager.renderPosY;
            final double z = pos.getZ() - RenderManager.renderPosZ;
            final float[] colours = this.getBlockColour(Block.getIdFromBlock(PlayerUtils.getBlock(pos)));
            GL11.glColor4f(colours[0], colours[1], colours[2], 1.0f);
            RenderUtils.drawBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0));
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        }
    }
    
    private float[] getBlockColour(final int id) {
        switch (id) {
            case 56: {
                return new float[] { 0.2f, 0.8f, 1.0f };
            }
            case 15: {
                return new float[] { 1.0f, 0.7f, 0.7f };
            }
            case 14: {
                return new float[] { 1.0f, 1.0f, 0.0f };
            }
            case 21: {
                return new float[] { 0.0f, 0.4f, 1.0f };
            }
            case 129: {
                return new float[] { 0.2f, 1.0f, 0.2f };
            }
            case 153: {
                return new float[] { 1.0f, 0.5f, 0.5f };
            }
            case 16: {
                return new float[] { 0.3f, 0.3f, 0.3f };
            }
            case 73: {
                return new float[] { 1.0f, 0.3f, 0.2f };
            }
            case 74: {
                return new float[] { 1.0f, 0.3f, 0.2f };
            }
            case 54: {
                return new float[] { 0.7f, 0.3f, 0.0f };
            }
            case 146: {
                return new float[] { 0.7f, 0.3f, 0.0f };
            }
            case 22: {
                return new float[] { 0.0f, 0.4f, 1.0f };
            }
            case 41: {
                return new float[] { 1.0f, 1.0f, 0.0f };
            }
            case 42: {
                return new float[] { 1.0f, 0.7f, 0.7f };
            }
            case 57: {
                return new float[] { 0.2f, 0.8f, 1.0f };
            }
            case 152: {
                return new float[] { 1.0f, 0.3f, 0.2f };
            }
            case 133: {
                return new float[] { 0.2f, 1.0f, 0.2f };
            }
            case 173: {
                return new float[] { 0.3f, 0.3f, 0.3f };
            }
            default: {
                return new float[] { 1.0f, 1.0f, 1.0f };
            }
        }
    }
    
    public static void renderOne() {
        GL11.glPushAttrib(1048575);
        GL11.glDisable(3008);
        GL11.glDisable(3553);
        GL11.glDisable(2896);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(4.0f);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glEnable(2960);
        GL11.glClear(1024);
        GL11.glClearStencil(15);
        GL11.glStencilFunc(512, 1, 15);
        GL11.glStencilOp(7681, 7681, 7681);
        GL11.glPolygonMode(1028, 6913);
    }
    
    public static void renderTwo() {
        GL11.glStencilFunc(512, 0, 15);
        GL11.glStencilOp(7681, 7681, 7681);
        GL11.glPolygonMode(1028, 6914);
    }
    
    public static void renderThree() {
        GL11.glStencilFunc(514, 1, 15);
        GL11.glStencilOp(7680, 7680, 7680);
        GL11.glPolygonMode(1028, 6913);
    }
    
    public static void renderFour() {
        GL11.glEnable(10754);
        GL11.glPolygonOffset(1.0f, -2000000.0f);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);
    }
    
    public static void renderFive() {
        GL11.glPolygonOffset(1.0f, 2000000.0f);
        GL11.glDisable(10754);
        GL11.glDisable(2960);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glDisable(3042);
        GL11.glEnable(2896);
        GL11.glEnable(3553);
        GL11.glEnable(3008);
        GL11.glPopAttrib();
    }
}

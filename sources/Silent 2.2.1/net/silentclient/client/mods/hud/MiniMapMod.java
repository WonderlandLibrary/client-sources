package net.silentclient.client.mods.hud;

import org.lwjgl.opengl.GL11;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockStone;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.silentclient.client.event.EventTarget;
import net.silentclient.client.event.impl.ClientTickEvent;
import net.silentclient.client.gui.hud.ScreenPosition;
import net.silentclient.client.mods.ModCategory;
import net.silentclient.client.mods.ModDraggable;

public class MiniMapMod extends ModDraggable {
	
	private static final ResourceLocation MAP_BORDER = new ResourceLocation("silentclient/mods/minimap/border.png");
    private static final ResourceLocation PLAYER_MARKER = new ResourceLocation("silentclient/mods/minimap/playermarker.png");

    private byte[] miniMapColors = new byte[16384];
    private DynamicTexture miniMapTexture;

	public MiniMapMod() {
		super("MiniMap", ModCategory.MODS, "silentclient/icons/mods/minimap.png");
		miniMapTexture = new DynamicTexture(128, 128);

        for (int i = 0; i < miniMapTexture.getTextureData().length; ++i)
        {
            miniMapTexture.getTextureData()[i] = 0;
        }
	}

	@Override
	public int getWidth() {
		return 20;
	}

	@Override
	public int getHeight() {
		return 20;
	}
	
	@EventTarget
	public void onClientTick(ClientTickEvent event) {
        if (mc.theWorld != null && mc.thePlayer != null) {
            updateMapData(mc.theWorld);
            updateMapTexture();
        }
    }

	@Override
	public boolean render(ScreenPosition pos) {
		float rotationYaw = mc.thePlayer.rotationYaw;

        //Resetting the color just in case other mods forget to reset it theirselves...
        //Example: Canelex Keystrokes mod
        GlStateManager.pushMatrix();
        GlStateManager.color(1,1,1,1);

        mc.getTextureManager().bindTexture(MAP_BORDER);
        drawCircle(0, 0, 30 + 3, 0);

        mc.getTextureManager().bindTexture(mc.getTextureManager().getDynamicTextureLocation("minimap", miniMapTexture));
        drawCircle(0, 0, 30, 180 - rotationYaw);

        mc.getTextureManager().bindTexture(PLAYER_MARKER);
        Gui.drawModalRectWithCustomSizedTexture(0 - 4, 0 - 4, 0, 0, 8, 8, 8, 8);

        drawDirections(0 - 2, 0 - 4, 30, 90 - rotationYaw);

        GlStateManager.popMatrix();
		return true;
	}
	
	private void drawDirections(int x, int y, float radius, float rotation){
        String[] directions = new String[]{"N", "E", "S", "W"};

        for(int i = 0; i < 4; i++){
            int xPos = (int) (MathHelper.cos((float) Math.toRadians(rotation + (i * 90))) * radius);
            int yPos = (int) (MathHelper.sin((float) Math.toRadians(rotation + (i * 90))) * radius);

            mc.fontRendererObj.drawStringWithShadow(directions[i], xPos + x, yPos + y, 0xFFFFFFFF);
        }
    }

    private void drawCircle(int x, int y, float radius, float rotation){
        GlStateManager.pushMatrix();
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();

        GlStateManager.translate(x, y, 0);
        GlStateManager.rotate(rotation, 0, 0, 1);

        GL11.glBegin(GL11.GL_POLYGON);
        for(int i = 360; i >= 0; i-=5){
            GL11.glTexCoord2f((MathHelper.cos((float) Math.toRadians(i)) + 1) * 0.5f, (MathHelper.sin((float) Math.toRadians(i)) + 1) * 0.5f);
            GL11.glVertex2f(MathHelper.cos((float) Math.toRadians(i)) * radius, MathHelper.sin((float) Math.toRadians(i)) * radius);
        }

        GL11.glEnd();
        GlStateManager.popMatrix();
    }

    public void updateMapData(World worldIn) {
        int i = 1;
        int j = mc.thePlayer.getPosition().getX();
        int k = mc.thePlayer.getPosition().getZ();
        int l = MathHelper.floor_double(mc.thePlayer.posX - (double) j) / i + 64;
        int i1 = MathHelper.floor_double(mc.thePlayer.posZ - (double) k) / i + 64;
        int j1 = 128 / i;

        if (worldIn.provider.getHasNoSky()) {
            j1 /= 2;
        }

        for (int k1 = l - j1 + 1; k1 < l + j1; ++k1) {
            double d0 = 0.0D;

            for (int l1 = i1 - j1 - 1; l1 < i1 + j1; ++l1) {
                if (k1 >= 0 && l1 >= -1 && k1 < 128 && l1 < 128) {
                    int i2 = k1 - l;
                    int j2 = l1 - i1;
                    boolean flag1 = i2 * i2 + j2 * j2 > (j1 - 2) * (j1 - 2);
                    int k2 = (j / i + k1 - 64) * i;
                    int l2 = (k / i + l1 - 64) * i;
                    Multiset<MapColor> multiset = HashMultiset.<MapColor>create();
                    Chunk chunk = worldIn.getChunkFromBlockCoords(new BlockPos(k2, 0, l2));

                    if (!chunk.isEmpty()) {
                        int i3 = k2 & 15;
                        int j3 = l2 & 15;
                        int k3 = 0;
                        double d1 = 0.0D;

                        if (worldIn.provider.getHasNoSky()) {
                            int l3 = k2 + l2 * 231871;
                            l3 = l3 * l3 * 31287121 + l3 * 11;

                            if ((l3 >> 20 & 1) == 0) {
                                multiset.add(Blocks.dirt.getMapColor(Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT)), 10);
                            } else {
                                multiset.add(Blocks.stone.getMapColor(Blocks.stone.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.STONE)), 100);
                            }

                            d1 = 100.0D;
                        } else {
                            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

                            for (int i4 = 0; i4 < i; ++i4) {
                                for (int j4 = 0; j4 < i; ++j4) {
                                    int k4 = chunk.getHeightValue(i4 + i3, j4 + j3) + 1;
                                    IBlockState iblockstate = Blocks.air.getDefaultState();

                                    if (k4 > 1) {
                                        label541:
                                        {
                                            while (true) {
                                                --k4;
                                                iblockstate = chunk.getBlockState(blockpos$mutableblockpos.set(i4 + i3, k4, j4 + j3));

                                                if (iblockstate.getBlock().getMapColor(iblockstate) != MapColor.airColor || k4 <= 0) {
                                                    break;
                                                }
                                            }

                                            if (k4 > 0 && iblockstate.getBlock().getMaterial().isLiquid()) {
                                                int l4 = k4 - 1;

                                                while (true) {
                                                    Block block = chunk.getBlock(i4 + i3, l4--, j4 + j3);
                                                    ++k3;

                                                    if (l4 <= 0 || !block.getMaterial().isLiquid()) {
                                                        break label541;
                                                    }
                                                }
                                            }
                                        }
                                    }

                                    d1 += (double) k4 / (double) (i * i);
                                    multiset.add(iblockstate.getBlock().getMapColor(iblockstate));
                                }
                            }
                        }

                        k3 = k3 / (i * i);
                        double d2 = (d1 - d0) * 4.0D / (double) (i + 4) + ((double) (k1 + l1 & 1) - 0.5D) * 0.4D;
                        int i5 = 1;

                        if (d2 > 0.6D) {
                            i5 = 2;
                        }

                        if (d2 < -0.6D) {
                            i5 = 0;
                        }

                        MapColor mapcolor = Iterables.getFirst(Multisets.copyHighestCountFirst(multiset), MapColor.airColor);

                        if (mapcolor == MapColor.waterColor) {
                            d2 = (double) k3 * 0.1D + (double) (k1 + l1 & 1) * 0.2D;
                            i5 = 1;

                            if (d2 < 0.5D) {
                                i5 = 2;
                            }

                            if (d2 > 0.9D) {
                                i5 = 0;
                            }
                        }

                        d0 = d1;

                        if (l1 >= 0 && i2 * i2 + j2 * j2 < j1 * j1 && (!flag1 || (k1 + l1 & 1) != 0)) {
                            byte b0 = miniMapColors[k1 + l1 * 128];
                            byte b1 = (byte) (mapcolor.colorIndex * 4 + i5);

                            if (b0 != b1) {
                                miniMapColors[k1 + l1 * 128] = b1;
                            }
                        }
                    }
                }
            }
        }
    }

    public void updateMapTexture() {
        for (int i = 0; i < 16384; i++) {
            int j = miniMapColors[i] & 255;

            if (j / 4 == 0) {
                miniMapTexture.getTextureData()[i] = (i + i / 128 & 1) * 8 + 16 << 24;
            } else {
                miniMapTexture.getTextureData()[i] = MapColor.mapColorArray[j / 4].getMapColor(j & 3);
            }
        }

        try {
            miniMapTexture.updateDynamicTexture();
        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }
	
}

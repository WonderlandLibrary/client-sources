/*
 * Decompiled with CFR 0.145.
 */
package itemphysics;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class ClientPhysic {
    public static Random random = new Random();
    public static Minecraft mc = Minecraft.getMinecraft();
    public static RenderItem renderItem = mc.getRenderItem();
    public static long tick;
    public static double rotation;
    public static final ResourceLocation RES_ITEM_GLINT;

    static {
        RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");
    }

    public static void doRender(Entity par1Entity, double x2, double y2, double z2, float par8, float par9) {
        EntityItem item;
        ItemStack itemstack;
        rotation = (double)(System.nanoTime() - tick) / 3000000.0 * 1.0;
        if (!ClientPhysic.mc.inGameHasFocus) {
            rotation = 0.0;
        }
        if ((itemstack = (item = (EntityItem)par1Entity).getEntityItem()).getItem() != null) {
            random.setSeed(187L);
            boolean flag = false;
            if (TextureMap.locationBlocksTexture != null) {
                ClientPhysic.mc.getRenderManager().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
                ClientPhysic.mc.getRenderManager().renderEngine.getTexture(TextureMap.locationBlocksTexture).func_174936_b(false, false);
                flag = true;
            }
            GlStateManager.enableRescaleNormal();
            GlStateManager.alphaFunc(516, 0.1f);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.pushMatrix();
            IBakedModel ibakedmodel = renderItem.getItemModelMesher().getItemModel(itemstack);
            int i2 = ClientPhysic.func_177077_a(item, x2, y2, z2, par9, ibakedmodel);
            BlockPos pos = new BlockPos(item);
            if (item.rotationPitch > 360.0f) {
                item.rotationPitch = 0.0f;
            }
            if (!(item == null || Double.isNaN(item.func_174872_o()) || Double.isNaN(item.getAir()) || Double.isNaN(item.getEntityId()) || item.getPosition() == null)) {
                if (item.onGround) {
                    if (item.rotationPitch != 0.0f && item.rotationPitch != 90.0f && item.rotationPitch != 180.0f && item.rotationPitch != 270.0f) {
                        double Abstand0 = ClientPhysic.formPositiv(item.rotationPitch);
                        double Abstand90 = ClientPhysic.formPositiv(item.rotationPitch - 90.0f);
                        double Abstand180 = ClientPhysic.formPositiv(item.rotationPitch - 180.0f);
                        double Abstand270 = ClientPhysic.formPositiv(item.rotationPitch - 270.0f);
                        if (Abstand0 <= Abstand90 && Abstand0 <= Abstand180 && Abstand0 <= Abstand270) {
                            if (item.rotationPitch < 0.0f) {
                                EntityItem tmp389_387 = item;
                                tmp389_387.rotationPitch = (float)((double)tmp389_387.rotationPitch + rotation);
                            } else {
                                EntityItem tmp407_405 = item;
                                tmp407_405.rotationPitch = (float)((double)tmp407_405.rotationPitch - rotation);
                            }
                        }
                        if (Abstand90 < Abstand0 && Abstand90 <= Abstand180 && Abstand90 <= Abstand270) {
                            if (item.rotationPitch - 90.0f < 0.0f) {
                                EntityItem tmp459_457 = item;
                                tmp459_457.rotationPitch = (float)((double)tmp459_457.rotationPitch + rotation);
                            } else {
                                EntityItem tmp477_475 = item;
                                tmp477_475.rotationPitch = (float)((double)tmp477_475.rotationPitch - rotation);
                            }
                        }
                        if (Abstand180 < Abstand90 && Abstand180 < Abstand0 && Abstand180 <= Abstand270) {
                            if (item.rotationPitch - 180.0f < 0.0f) {
                                EntityItem tmp529_527 = item;
                                tmp529_527.rotationPitch = (float)((double)tmp529_527.rotationPitch + rotation);
                            } else {
                                EntityItem tmp547_545 = item;
                                tmp547_545.rotationPitch = (float)((double)tmp547_545.rotationPitch - rotation);
                            }
                        }
                        if (Abstand270 < Abstand90 && Abstand270 < Abstand180 && Abstand270 < Abstand0) {
                            if (item.rotationPitch - 270.0f < 0.0f) {
                                EntityItem tmp599_597 = item;
                                tmp599_597.rotationPitch = (float)((double)tmp599_597.rotationPitch + rotation);
                            } else {
                                EntityItem tmp617_615 = item;
                                tmp617_615.rotationPitch = (float)((double)tmp617_615.rotationPitch - rotation);
                            }
                        }
                    }
                } else {
                    BlockPos posUp = new BlockPos(item);
                    posUp.add(0, 1, 0);
                    Material m1 = item.worldObj.getBlockState(posUp).getBlock().getMaterial();
                    Material m2 = item.worldObj.getBlockState(pos).getBlock().getMaterial();
                    boolean m3 = item.isInsideOfMaterial(Material.water);
                    boolean m4 = item.inWater;
                    if (m3 | m1 == Material.water | m2 == Material.water | m4) {
                        EntityItem tmp748_746 = item;
                        tmp748_746.rotationPitch = (float)((double)tmp748_746.rotationPitch + rotation / 4.0);
                    } else {
                        EntityItem tmp770_768 = item;
                        tmp770_768.rotationPitch = (float)((double)tmp770_768.rotationPitch + rotation * 2.0);
                    }
                }
            }
            GL11.glRotatef(item.rotationYaw, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(item.rotationPitch + 90.0f, 1.0f, 0.0f, 0.0f);
            for (int j2 = 0; j2 < i2; ++j2) {
                if (ibakedmodel.isAmbientOcclusionEnabled()) {
                    GlStateManager.pushMatrix();
                    GlStateManager.scale(0.5f, 0.5f, 0.5f);
                    renderItem.func_180454_a(itemstack, ibakedmodel);
                    GlStateManager.popMatrix();
                    continue;
                }
                GlStateManager.pushMatrix();
                if (j2 > 0 && ClientPhysic.shouldSpreadItems()) {
                    GlStateManager.translate(0.0f, 0.0f, 0.046875f * (float)j2);
                }
                renderItem.func_180454_a(itemstack, ibakedmodel);
                if (!ClientPhysic.shouldSpreadItems()) {
                    GlStateManager.translate(0.0f, 0.0f, 0.046875f);
                }
                GlStateManager.popMatrix();
            }
            GlStateManager.popMatrix();
            GlStateManager.disableRescaleNormal();
            GlStateManager.disableBlend();
            ClientPhysic.mc.getRenderManager().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
            if (flag) {
                ClientPhysic.mc.getRenderManager().renderEngine.getTexture(TextureMap.locationBlocksTexture).func_174935_a();
            }
        }
    }

    public static int func_177077_a(EntityItem item, double x2, double y2, double z2, float p_177077_8_, IBakedModel p_177077_9_) {
        ItemStack itemstack = item.getEntityItem();
        Item item2 = itemstack.getItem();
        if (item2 == null) {
            return 0;
        }
        boolean flag = p_177077_9_.isAmbientOcclusionEnabled();
        int i2 = ClientPhysic.func_177078_a(itemstack);
        float f1 = 0.25f;
        float f2 = 0.0f;
        GlStateManager.translate((float)x2, (float)y2 + f2 + 0.25f, (float)z2);
        float f3 = 0.0f;
        if (flag || ClientPhysic.mc.getRenderManager().renderEngine != null && ClientPhysic.mc.gameSettings.fancyGraphics) {
            GlStateManager.rotate(f3, 0.0f, 1.0f, 0.0f);
        }
        if (!flag) {
            f3 = -0.0f * (float)(i2 - 1) * 0.5f;
            float f4 = -0.0f * (float)(i2 - 1) * 0.5f;
            float f5 = -0.046875f * (float)(i2 - 1) * 0.5f;
            GlStateManager.translate(f3, f4, f5);
        }
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        return i2;
    }

    public static boolean shouldSpreadItems() {
        return true;
    }

    public static double formPositiv(float rotationPitch) {
        if (rotationPitch > 0.0f) {
            return rotationPitch;
        }
        return -rotationPitch;
    }

    public static int func_177078_a(ItemStack stack) {
        int b0 = 1;
        if (stack.animationsToGo > 48) {
            b0 = 5;
        } else if (stack.animationsToGo > 32) {
            b0 = 4;
        } else if (stack.animationsToGo > 16) {
            b0 = 3;
        } else if (stack.animationsToGo > 1) {
            b0 = 2;
        }
        return b0;
    }
}


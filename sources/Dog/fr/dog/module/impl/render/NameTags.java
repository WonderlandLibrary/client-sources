package fr.dog.module.impl.render;

import fr.dog.event.annotations.SubscribeEvent;
import fr.dog.event.impl.render.Render2DEvent;
import fr.dog.module.Module;
import fr.dog.module.ModuleCategory;
import fr.dog.property.impl.BooleanProperty;
import fr.dog.util.render.RenderUtil;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Vector3d;
import org.lwjgl.opengl.GL11;
import org.lwjglx.opengl.Display;
import org.lwjglx.util.glu.GLU;
import org.lwjglx.util.vector.Vector4f;

import java.awt.*;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NameTags extends Module {
    public NameTags() {
        super("NameTags", ModuleCategory.RENDER);
        this.registerProperties(noarmor);
    }
    private final BooleanProperty noarmor = BooleanProperty.newInstance("Display Armor", false);
    private final IntBuffer viewport = GLAllocation.createDirectIntBuffer(16);
    private final float[] BUFFER = new float[3];
    private final FloatBuffer windowPosition = GLAllocation.createDirectFloatBuffer(4);
    private final FloatBuffer modelMatrix = GLAllocation.createDirectFloatBuffer(16);
    private final FloatBuffer projectionMatrix = GLAllocation.createDirectFloatBuffer(16);

    @SubscribeEvent
    private void onRender(Render2DEvent event){
        drawNameTags();
    }

    public void drawNameTags() {
        mc.theWorld.loadedEntityList.forEach(entity -> {
            if (entity instanceof EntityPlayer ent) {
                if (isValid(ent) && RenderUtil.isInViewFrustrum(ent.getEntityBoundingBox())) {
                    List<Vector3d> vectors = getVector3dList(ent);
                    mc.entityRenderer.setupCameraTransform(mc.timer.renderPartialTicks, 0);
                    Vector4f position = null;
                    for (Vector3d vector : vectors) {
                        vector = project2D((float)(vector.x - RenderManager.viewerPosX), (float)(vector.y - RenderManager.viewerPosY), (float)(vector.z - RenderManager.viewerPosZ), new ScaledResolution(mc).getScaleFactor());
                        if (vector != null && vector.z >= 0.0 && vector.z < 1.0) {
                            if (position == null) {
                                position = new Vector4f((float) vector.x, (float) vector.y, (float) vector.z, 0.0f);
                            }
                            position.x = (float) Math.min(vector.x, position.x);
                            position.y = (float) Math.min(vector.y, position.y);
                            position.z = (float) Math.max(vector.x, position.z);
                            position.w = (float) Math.max(vector.y, position.w);
                        }
                    }
                    mc.entityRenderer.setupOverlayRendering();
                    if (position != null) {
                        GL11.glPushMatrix();
                        float size = .5f;
                        if (noarmor.getValue()) {
                            drawArmor(ent, (int) (position.x + ((position.z - position.x) / 2)), (int) position.y - 4 - mc.fontRendererObj.FONT_HEIGHT * 2, size);
                        }
                        GlStateManager.scale(size, size, size);
                        float x = position.x / size;
                        float x2 = position.z / size;
                        float y = position.y / size;
                        final String nametext = entity.getDisplayName().getFormattedText() + " §7(§fH: " + new DecimalFormat("#.##").format(((EntityPlayer) entity).getHealth()) + " §c❤§7)";
                        drawRect((x + (x2 - x) / 2) - (mc.fontRendererObj.getStringWidth(nametext) >> 1) - 2, y - mc.fontRendererObj.FONT_HEIGHT - 4, (x + (x2 - x) / 2) + (mc.fontRendererObj.getStringWidth(nametext) >> 1) + 2, y - 2, new Color(0, 0, 0, 120).getRGB());

                        mc.fontRendererObj.drawStringWithShadow(nametext, (x + ((x2 - x) / 2)) - (mc.fontRendererObj.getStringWidth(nametext) / 2F), y - mc.fontRendererObj.FONT_HEIGHT - 2, getNameColor(ent));
                        GL11.glPopMatrix();
                    }
                }
            }
        });
    }

    private List<Vector3d> getVector3dList(EntityPlayer ent) {
        AxisAlignedBB aabb = getAxisAlignedBB(ent);
        return Arrays.asList(new Vector3d(aabb.minX, aabb.minY, aabb.minZ), new Vector3d(aabb.minX, aabb.maxY, aabb.minZ), new Vector3d(aabb.maxX, aabb.minY, aabb.minZ), new Vector3d(aabb.maxX, aabb.maxY, aabb.minZ), new Vector3d(aabb.minX, aabb.minY, aabb.maxZ), new Vector3d(aabb.minX, aabb.maxY, aabb.maxZ), new Vector3d(aabb.maxX, aabb.minY, aabb.maxZ), new Vector3d(aabb.maxX, aabb.maxY, aabb.maxZ));
    }

    private AxisAlignedBB getAxisAlignedBB(EntityPlayer ent) {
        double posX = interpolate(ent.posX, ent.lastTickPosX, mc.timer.renderPartialTicks);
        double posY = interpolate(ent.posY, ent.lastTickPosY, mc.timer.renderPartialTicks);
        double posZ = interpolate(ent.posZ, ent.lastTickPosZ, mc.timer.renderPartialTicks);
        double width = ent.width / 1.5;
        double height = ent.height + (ent.isSneaking() ? -0.3 : 0.2);
        return new AxisAlignedBB(posX - width, posY, posZ - width, posX + width, posY + height, posZ + width);
    }
    public static double interpolate(double current, double old, double scale) {
        return old + (current - old) * scale;
    }


    private int getNameColor(EntityLivingBase ent) {
        if (mc.thePlayer.isOnSameTeam(ent)) return new Color(122, 190, 255).getRGB();
        else if (ent.getName().equals(mc.thePlayer.getName())) return new Color(0xFF99ff99).getRGB();
        return new Color(-1).getRGB();
    }

    private EnumChatFormatting getNameHealthColor(EntityLivingBase player) {
        final double health = Math.ceil(player.getHealth());
        final double maxHealth = player.getMaxHealth();
        final double percentage = 100 * (health / maxHealth);
        if (percentage > 85) return EnumChatFormatting.DARK_GREEN;
        else if (percentage > 75) return EnumChatFormatting.GREEN;
        else if (percentage > 50) return EnumChatFormatting.YELLOW;
        else if (percentage > 25) return EnumChatFormatting.RED;
        else if (percentage > 0) return EnumChatFormatting.DARK_RED;
        return EnumChatFormatting.BLACK;
    }



    private void drawArmor(EntityPlayer player, int x, int y, float size) {
        if (player.inventory.armorInventory.length > 0) {
            List<ItemStack> items = new ArrayList<>();
            if (player.getHeldItem() != null) {
                items.add(player.getHeldItem());
            }
            for (int index = 3; index >= 0; index--) {
                ItemStack stack = player.inventory.armorInventory[index];
                if (stack != null) {
                    items.add(stack);
                }
            }
            int armorX = x - ((items.size() * 18) / 2);
            for (ItemStack stack : items) {
                GlStateManager.pushMatrix();
                GlStateManager.enableLighting();
                mc.getRenderItem().renderItemAndEffectIntoGUI(stack, armorX, y);
                mc.getRenderItem().renderItemOverlays(mc.fontRendererObj, stack, armorX, y);
                GlStateManager.disableLighting();
                GlStateManager.popMatrix();
                GlStateManager.disableDepth();

                NBTTagList enchants = stack.getEnchantmentTagList();
                if (enchants != null) {
                    int encY = y - 2;
                    GlStateManager.pushMatrix();
                    GlStateManager.scale(0.5f, 0.5f, 1.0f);
                    int offset = 0;
                    for (int index = 0; index < enchants.tagCount(); ++index) {
                        short id = enchants.getCompoundTagAt(index).getShort("id");
                        short level = enchants.getCompoundTagAt(index).getShort("lvl");
                        Enchantment enc = Enchantment.getEnchantmentById(id);

                        if (enc != null) {
                            String encName = getEnchantmentLabel(enc, level);
                            mc.fontRendererObj.drawString(encName, (armorX + 2) / 0.5f, (encY + offset) / 0.5f, 0xDDD1E6, true);
                            offset += 8;
                        }
                    }
                    GlStateManager.popMatrix();
                }
                GlStateManager.enableDepth();
                armorX += 18;
            }
        }
    }

    private String getEnchantmentLabel(Enchantment enchantment, short level) {
        String labelPrefix;
        if (enchantment == Enchantment.protection) labelPrefix = "P";
        else if (enchantment == Enchantment.sharpness) labelPrefix = "S";
        else if (enchantment == Enchantment.fireAspect) labelPrefix = "F";
        else if (enchantment == Enchantment.efficiency) labelPrefix = "E";
        else if (enchantment == Enchantment.power) labelPrefix = "P";
        else if (enchantment == Enchantment.flame) labelPrefix = "F";
        else labelPrefix = "U";

        String levelStr = (level > 99) ? "99+" : String.valueOf(level);
        return labelPrefix + levelStr;
    }

    public boolean isValid(Entity entity) {
        if (!(entity instanceof EntityLivingBase)) {
            return false;
        }
        return entity instanceof EntityPlayer && !entity.isDead && ((EntityPlayer) entity).hasMoved && entity != mc.thePlayer;
    }

    private Vector3d project2D(float x, float y, float z, int scaleFactor) {
        GL11.glGetFloatv(GL11.GL_MODELVIEW_MATRIX, modelMatrix);
        GL11.glGetFloatv(GL11.GL_PROJECTION_MATRIX, projectionMatrix);
        GL11.glGetIntegerv(GL11.GL_VIEWPORT, viewport);

        if (GLU.gluProject(x, y, z, modelMatrix, projectionMatrix, viewport, windowPosition)) {
            BUFFER[0] = windowPosition.get(0) / scaleFactor;
            BUFFER[1] = (Display.getHeight() - windowPosition.get(1)) / scaleFactor;
            BUFFER[2] = windowPosition.get(2);
            Vector3d res = new Vector3d();
            res.x = BUFFER[0];
            res.y = BUFFER[1];
            res.z = BUFFER[2];

            return res;
        }

        return null;
    }

    public void drawRect(double left, double top, double right, double bottom, int color)
    {
        if (left < right)
        {
            double i = left;
            left = right;
            right = i;
        }

        if (top < bottom)
        {
            double j = top;
            top = bottom;
            bottom = j;
        }

        float f3 = (float)(color >> 24 & 255) / 255.0F;
        float f = (float)(color >> 16 & 255) / 255.0F;
        float f1 = (float)(color >> 8 & 255) / 255.0F;
        float f2 = (float)(color & 255) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(f, f1, f2, f3);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos(left, bottom, 0.0D).endVertex();
        worldrenderer.pos(right, bottom, 0.0D).endVertex();
        worldrenderer.pos(right, top, 0.0D).endVertex();
        worldrenderer.pos(left, top, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
}

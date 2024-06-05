package white.floor.features.impl.visuals;

import java.awt.Color;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.vecmath.Vector3d;
import javax.vecmath.Vector4d;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import com.mojang.realmsclient.gui.ChatFormatting;

import clickgui.setting.Setting;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import white.floor.Main;
import white.floor.event.EventTarget;
import white.floor.event.event.EventNameTags;
import white.floor.event.event.EventRender2D;
import white.floor.features.Category;
import white.floor.features.Feature;
import white.floor.features.impl.combat.AntiBot;
import white.floor.font.Fonts;
import white.floor.helpers.DrawHelper;
import white.floor.helpers.friend.FriendManager;
import white.floor.helpers.notifications.NotificationPublisher;
import white.floor.helpers.notifications.NotificationType;

public class EntityESP extends Feature {
    public final List<Entity> collectedEntities = new ArrayList<>();
    private final IntBuffer viewport = GLAllocation.createDirectIntBuffer(16);
    private final FloatBuffer modelview = GLAllocation.createDirectFloatBuffer(16);
    private final FloatBuffer projection = GLAllocation.createDirectFloatBuffer(16);
    private final FloatBuffer vector = GLAllocation.createDirectFloatBuffer(4);
    private final int backgroundColor = (new Color(0, 0, 0, 120)).getRGB();
    private final int black = Color.BLACK.getRGB();
    public Random fontRandom = new Random();

    public EntityESP() {
        super("EntityESP", "Draw box on entity.",0, Category.VISUALS);

        Main.settingsManager.rSetting(new Setting("ESPPlayers", this, true));
        Main.settingsManager.rSetting(new Setting("ESPAnimals", this, true));
        Main.settingsManager.rSetting(new Setting("ESPMonsters", this, true));
        Main.settingsManager.rSetting(new Setting("ESPInvisibles", this, true));
        Main.settingsManager.rSetting(new Setting("ESPItems", this, true));
        Main.settingsManager.rSetting(new Setting("ESPTags", this, true));
        Main.settingsManager.rSetting(new Setting("ESPArmor", this, true));
        Main.settingsManager.rSetting(new Setting("ESPHealth", this, true));
        Main.settingsManager.rSetting(new Setting("ESPBorder", this, true));
        ArrayList<String> mode = new ArrayList<String>();
        mode.add("Box");
        mode.add("Corner");
        mode.add("Apex");
        Main.settingsManager.rSetting(new Setting("ESP Mode", this, "Box", mode));
    }

    @EventTarget
    public void onRender2D(EventRender2D render) {
        GL11.glPushMatrix();
        collectEntities();
        float partialTicks = render.getPartialTicks();
        ScaledResolution scaledResolution = render.getResolution();
        int scaleFactor = scaledResolution.getScaleFactor();
        double scaling = scaleFactor / Math.pow(scaleFactor, 2.0D);
        GL11.glScaled(scaling, scaling, scaling);
        int black = this.black;
        int color = Main.getClientColor().getRGB();
        int background = this.backgroundColor;
        float scale = 1.0F;
        float upscale = 1.0F / scale;
        FontRenderer fr = mc.fontRendererObj;
        RenderManager renderMng = mc.getRenderManager();
        EntityRenderer entityRenderer = mc.entityRenderer;
        boolean tag = Main.settingsManager.getSettingByName(Main.featureDirector.getModule(EntityESP.class), "ESPTags").getValBoolean();
        boolean outline = Main.settingsManager.getSettingByName(Main.featureDirector.getModule(EntityESP.class), "ESPBorder").getValBoolean();
        boolean health = Main.settingsManager.getSettingByName(Main.featureDirector.getModule(EntityESP.class), "ESPHealth").getValBoolean();
        boolean armor = Main.settingsManager.getSettingByName(Main.featureDirector.getModule(EntityESP.class), "ESPArmor").getValBoolean();
        String mode = Main.instance.settingsManager.getSettingByName("ESP Mode").getValString();
        List<Entity> collectedEntities = this.collectedEntities;

        for (int i = 0, collectedEntitiesSize = collectedEntities.size(); i < collectedEntitiesSize; i++) {
            Entity entity = collectedEntities.get(i);
            if(entity != mc.player) {
                if (isValid(entity) && DrawHelper.isInViewFrustrum(entity)) {
                    double x = DrawHelper.interpolate(entity.posX, entity.lastTickPosX, partialTicks);
                    double y = DrawHelper.interpolate(entity.posY, entity.lastTickPosY, partialTicks);
                    double z = DrawHelper.interpolate(entity.posZ, entity.lastTickPosZ, partialTicks);
                    double width = entity.width / 1.5D;
                    double height = entity.height + ((entity.isSneaking() || (entity == mc.player)) ? -0.3D : 0.2D);
                    AxisAlignedBB aabb = new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width);
                    Vector3d[] vectors = new Vector3d[]{new Vector3d(aabb.minX, aabb.minY, aabb.minZ), new Vector3d(aabb.minX, aabb.maxY, aabb.minZ), new Vector3d(aabb.maxX, aabb.minY, aabb.minZ), new Vector3d(aabb.maxX, aabb.maxY, aabb.minZ), new Vector3d(aabb.minX, aabb.minY, aabb.maxZ), new Vector3d(aabb.minX, aabb.maxY, aabb.maxZ), new Vector3d(aabb.maxX, aabb.minY, aabb.maxZ), new Vector3d(aabb.maxX, aabb.maxY, aabb.maxZ)};
                    entityRenderer.setupCameraTransform(partialTicks, 0);
                    Vector4d position = null;
                    for (Vector3d vector : vectors) {
                        vector = project2D(scaleFactor, vector.x - renderMng.viewerPosX, vector.y - renderMng.viewerPosY, vector.z - renderMng.viewerPosZ);
                        if (vector != null && vector.z >= 0.0D && vector.z < 1.0D) {
                            if (position == null)
                                position = new Vector4d(vector.x, vector.y, vector.z, 0.0D);
                            position.x = Math.min(vector.x, position.x);
                            position.y = Math.min(vector.y, position.y);
                            position.z = Math.max(vector.x, position.z);
                            position.w = Math.max(vector.y, position.w);
                        }
                    }

                    if (position != null) {
                        entityRenderer.setupOverlayRendering();
                        double posX = position.x;
                        double posY = position.y;
                        double endPosX = position.z;
                        double endPosY = position.w;
                        if (outline) {
                            switch (mode) {
                                case "Box":
                                    DrawHelper.drawRect(posX - 1.0D, posY, posX + 0.5D, endPosY + 0.5D, black);
                                    DrawHelper.drawRect(posX - 1.0D, posY - 0.5D, endPosX + 0.5D, posY + 0.5D + 0.5D, black);
                                    DrawHelper.drawRect(endPosX - 0.5D - 0.5D, posY, endPosX + 0.5D, endPosY + 0.5D, black);
                                    DrawHelper.drawRect(posX - 1.0D, endPosY - 0.5D - 0.5D, endPosX + 0.5D, endPosY + 0.5D, black);
                                    DrawHelper.drawRect(posX - 0.5D, posY, posX + 0.5D - 0.5D, endPosY, color);
                                    DrawHelper.drawRect(posX, endPosY - 0.5D, endPosX, endPosY, color);
                                    DrawHelper.drawRect(posX - 0.5D, posY, endPosX, posY + 0.5D, color);
                                    DrawHelper.drawRect(endPosX - 0.5D, posY, endPosX, endPosY, color);
                                    break;
                                case "Corner":
                                    DrawHelper.drawRect(posX + 0.5D, posY, posX - 1.0D, posY + (endPosY - posY) / 4.0D + 0.5D, black);
                                    DrawHelper.drawRect(posX - 1.0D, endPosY, posX + 0.5D, endPosY - (endPosY - posY) / 4.0D - 0.5D, black);
                                    DrawHelper.drawRect(posX - 1.0D, posY - 0.5D, posX + (endPosX - posX) / 3.0D + 0.5D, posY + 1.0D, black);
                                    DrawHelper.drawRect(endPosX - (endPosX - posX) / 3.0D - 0.5D, posY - 0.5D, endPosX, posY + 1.0D, black);
                                    DrawHelper.drawRect(endPosX - 1.0D, posY, endPosX + 0.5D, posY + (endPosY - posY) / 4.0D + 0.5D, black);
                                    DrawHelper.drawRect(endPosX - 1.0D, endPosY, endPosX + 0.5D, endPosY - (endPosY - posY) / 4.0D - 0.5D, black);
                                    DrawHelper.drawRect(posX - 1.0D, endPosY - 1.0D, posX + (endPosX - posX) / 3.0D + 0.5D, endPosY + 0.5D, black);
                                    DrawHelper.drawRect(endPosX - (endPosX - posX) / 3.0D - 0.5D, endPosY - 1.0D, endPosX + 0.5D, endPosY + 0.5D, black);
                                    DrawHelper.drawRect(posX, posY, posX - 0.5D, posY + (endPosY - posY) / 4.0D, color);
                                    DrawHelper.drawRect(posX, endPosY, posX - 0.5D, endPosY - (endPosY - posY) / 4.0D, color);
                                    DrawHelper.drawRect(posX - 0.5D, posY, posX + (endPosX - posX) / 3.0D, posY + 0.5D, color);
                                    DrawHelper.drawRect(endPosX - (endPosX - posX) / 3.0D, posY, endPosX, posY + 0.5D, color);
                                    DrawHelper.drawRect(endPosX - 0.5D, posY, endPosX, posY + (endPosY - posY) / 4.0D, color);
                                    DrawHelper.drawRect(endPosX - 0.5D, endPosY, endPosX, endPosY - (endPosY - posY) / 4.0D, color);
                                    DrawHelper.drawRect(posX, endPosY - 0.5D, posX + (endPosX - posX) / 3.0D, endPosY, color);
                                    DrawHelper.drawRect(endPosX - (endPosX - posX) / 3.0D, endPosY - 0.5D, endPosX - 0.5D, endPosY, color);
                                    break;
                                case "Apex":
                                    DrawHelper.drawRect(endPosX - .5 - .5, posY, endPosX + .5, endPosY + .5, black);
                                    DrawHelper.drawRect(posX - 1, posY, posX + .5, endPosY + .5, black);

                                    DrawHelper.drawRect(posX - 1, endPosY - 1, posX + (endPosX - posX) / 4 + .5, endPosY + .5, black);
                                    DrawHelper.drawRect(endPosX - 1, endPosY - 1, endPosX + (posX - endPosX) / 4 - .5, endPosY + .5, black);
                                    DrawHelper.drawRect(posX - 1, posY - .5, posX + (endPosX - posX) / 4 + .5, posY + 1, black);
                                    DrawHelper.drawRect(endPosX, posY - .5, endPosX + (posX - endPosX) / 4 - .5, posY + 1, black);

                                    DrawHelper.drawRect(posX - .5, posY, posX + .5 - .5, endPosY, color);
                                    DrawHelper.drawRect(endPosX - .5, posY, endPosX, endPosY, color);

                                    DrawHelper.drawRect(posX, endPosY - .5, posX + (endPosX - posX) / 4, endPosY, color);
                                    DrawHelper.drawRect(endPosX, endPosY - .5, endPosX + (posX - endPosX) / 4, endPosY, color);
                                    DrawHelper.drawRect(posX - .5, posY, posX + (endPosX - posX) / 4, posY + .5, color);
                                    DrawHelper.drawRect(endPosX, posY, endPosX + (posX - endPosX) / 4, posY + .5, color);
                                    break;
                            }
                        }
                        boolean living = entity instanceof EntityLivingBase;
                        if (living) {
                            EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
                            if (health) {
                                float hp = entityLivingBase.getHealth();
                                float maxHealth = entityLivingBase.getMaxHealth();
                                if (hp > maxHealth)
                                    hp = maxHealth;
                                double hpPercentage = (hp / maxHealth);
                                double hpHeight = (endPosY - posY) * hpPercentage;
                                DrawHelper.drawRect(posX - 3.5D, posY - 0.5D, posX - 1.5D, endPosY + 0.5D, background);
                                if (hp > 0.0F) {
                                    float absorption = entityLivingBase.getAbsorptionAmount();
                                    int healthColor = Main.getClientColor().getRGB();
                                    DrawHelper.drawRect(posX - 3.0D, endPosY, posX - 2.0D, endPosY - hpHeight, healthColor);
                                    if (mc.player.getDistanceToEntity(entityLivingBase) < 20)
                                        Fonts.neverlose500_13.drawStringWithShadow("" + MathHelper.floor(hp), posX - 12.0D, endPosY - hpHeight + 1, healthColor);
                                    if (absorption > 0.0F)
                                        DrawHelper.drawRect(posX - 3.0D, endPosY, posX - 2.0D, endPosY - (endPosY - posY) / 6.0D * absorption / 2.0D, healthColor);
                                }
                            }
                        }
                        if (tag) {
                            float scaledHeight = 10.0F;
                            int texcolor = -1;
                            String name = entity.getName();

                            if (entity instanceof EntityItem) {
                                name = ((EntityItem) entity).getEntityItem().getDisplayName();
                            }


                            String prefix = "";

                            texcolor = Main.getClientColor(0, 0, 4).getRGB();

                            if (FriendManager.isFriend(entity.getName())) {
                                prefix = "§f[§aF§f] ";
                            }

                            if (AntiBot.entete.contains((Object) entity) && Main.featureDirector.getModule(AntiBot.class).isToggled() && !FriendManager.isFriend(entity.getName())) {
                                prefix = "§f[§4A-B§f]";
                            }

                            double dif = (endPosX - posX) / 2.0D;
                            if(!(entity instanceof EntityItem)) {
                                double textWidth = (Fonts.neverlose500_14.getStringWidth(prefix + name + " §7[" + ChatFormatting.RED + (int) ((EntityLivingBase) entity).getHealth() + "HP" + "§7]") * scale);
                                double textHeight = (Fonts.neverlose500_14.getStringHeight(prefix + name + " §7[" + ChatFormatting.RED + (int) ((EntityLivingBase) entity).getHealth() + "HP" + "§7]") * scale);
                                float tagX = (float) ((posX + dif - textWidth / 2.0D) * upscale);
                                float tagY = (float) (posY * upscale) - scaledHeight;
                                GL11.glPushMatrix();
                                GL11.glScalef(scale, scale, scale);
                                if (living)
                                    DrawHelper.drawNewRect((tagX - 1.0F), (tagY - 2.0F), tagX + textWidth * upscale + 1.0D, (tagY + textHeight + 0.5), (new Color(21, 21, 21, 200)).getRGB());
                                Fonts.neverlose500_14.drawString(prefix + name + " §7[" + ChatFormatting.RED + (int) ((EntityLivingBase) entity).getHealth() + "HP" + "§7]", tagX, tagY, -1);
                                GL11.glPopMatrix();
                            } else {
                                GL11.glPushMatrix();
                                double textWidth = (Fonts.neverlose500_14.getStringWidth(prefix + name ) * scale);
                                double textHeight = (Fonts.neverlose500_14.getStringHeight(prefix + name) * scale);
                                float tagX = (float) ((posX + dif - textWidth / 2.0D) * upscale);
                                float tagY = (float) (posY * upscale) - scaledHeight;
                                //DrawHelper.drawNewRect((tagX - 1.0F), (tagY - 2.0F), tagX + textWidth * upscale + 1.0D, (tagY + textHeight + 1), (new Color(0, 0, 0, 140)).getRGB());
                                Fonts.neverlose500_14.drawStringWithShadow(prefix + name, tagX, tagY, -1);
                                GL11.glPopMatrix();
                            }
                        }
                        if (armor) {
                            if (living) {
                                if (entity instanceof EntityPlayer) {
                                    EntityPlayer player = (EntityPlayer) entity;
                                    double ydiff = (endPosY - posY) / 4;

                                    ItemStack stack = (player).getEquipmentInSlot(4);
                                    if (stack != null) {
                                        DrawHelper.drawRect(endPosX + 3.5D, posY - 0.5D, endPosX + 1.5D, posY + ydiff, background);
                                        double diff1 = (posY + ydiff - 1) - (posY + 2);
                                        double percent = 1 - (double) stack.getItemDamage() / (double) stack.getMaxDamage();
                                        DrawHelper.drawRect(endPosX + 3.0D, posY + ydiff, endPosX + 2.0D, posY + ydiff - 3.0D - (diff1 * percent), new Color(78, 206, 229).getRGB());

                                        String stackname = (stack.getDisplayName().equalsIgnoreCase("Air")) ? "0" : !(stack.getItem() instanceof ItemArmor) ? stack.getDisplayName() : stack.getMaxDamage() - stack.getItemDamage() + "";
                                        if (mc.player.getDistanceToEntity(player) < 20) {
                                            Fonts.neverlose500_13.drawStringWithShadow(stackname, (float) endPosX + 5, (float) (posY + ydiff - 1 - (diff1 / 2)) - (Fonts.neverlose500_13.getStringHeight(stack.getMaxDamage() - stack.getItemDamage() + "") / 2), -1);
                                        }
                                    }
                                    ItemStack stack2 = (player).getEquipmentInSlot(3);
                                    if (stack2 != null) {
                                        DrawHelper.drawRect(endPosX + 3.5D, posY + ydiff, endPosX + 1.5D, posY + ydiff * 2, background);
                                        double diff1 = (posY + ydiff * 2) - (posY + ydiff + 2);
                                        double percent = 1 - (double) stack2.getItemDamage() * 1 / (double) stack2.getMaxDamage();
                                        DrawHelper.drawRect(endPosX + 3.0D, (posY + ydiff * 2), endPosX + 2.0D, (posY + ydiff * 2) - 1.0D - (diff1 * percent), new Color(78, 206, 229).getRGB());

                                        String stackname = (stack.getDisplayName().equalsIgnoreCase("Air")) ? "0" : !(stack2.getItem() instanceof ItemArmor) ? stack2.getDisplayName() : stack2.getMaxDamage() - stack2.getItemDamage() + "";
                                        if (mc.player.getDistanceToEntity(player) < 20) {
//                                        mc.getRenderItem().renderItemIntoGUI(stack2, endPosX + 4, (posY + ydiff * 2) - (diff1 / 2) - 18);
                                            Fonts.neverlose500_13.drawStringWithShadow(stackname, (float) endPosX + 5, (float) ((posY + ydiff * 2) - (diff1 / 2)) - (Fonts.neverlose500_13.getStringHeight(stack2.getMaxDamage() - stack2.getItemDamage() + "") / 2), -1);
                                        }
                                    }
                                    ItemStack stack3 = (player).getEquipmentInSlot(2);
                                    if (stack3 != null) {
                                        DrawHelper.drawRect(endPosX + 3.5D, posY + ydiff * 2, endPosX + 1.5D, posY + ydiff * 3, background);
                                        double diff1 = (posY + ydiff * 3) - (posY + ydiff * 2 + 2);
                                        double percent = 1 - (double) stack3.getItemDamage() * 1 / (double) stack3.getMaxDamage();
                                        DrawHelper.drawRect(endPosX + 3.0D, (posY + ydiff * 3), endPosX + 2.0D, (posY + ydiff * 3) - 1.0D - (diff1 * percent), new Color(78, 206, 229).getRGB());

                                        String stackname = (stack.getDisplayName().equalsIgnoreCase("Air")) ? "0" : !(stack3.getItem() instanceof ItemArmor) ? stack3.getDisplayName() : stack3.getMaxDamage() - stack3.getItemDamage() + "";
                                        if (mc.player.getDistanceToEntity(player) < 20) {
//                                        mc.getRenderItem().renderItemIntoGUI(stack3, endPosX + 4, (posY + ydiff * 3) - (diff1 / 2) - 18);
                                            Fonts.neverlose500_13.drawStringWithShadow(stackname, (float) endPosX + 5, (float) ((posY + ydiff * 3) - (diff1 / 2)) - (Fonts.neverlose500_13.getStringHeight(stack3.getMaxDamage() - stack3.getItemDamage() + "") / 2), -1);
                                        }
                                    }
                                    ItemStack stack4 = (player).getEquipmentInSlot(1);
                                    if (stack4 != null) {
                                        DrawHelper.drawRect(endPosX + 3.5D, posY + ydiff * 3, endPosX + 1.5D, posY + ydiff * 4 + 0.5D, background);
                                        double diff1 = (posY + ydiff * 4) - (posY + ydiff * 3 + 2);
                                        double percent = 1 - (double) stack4.getItemDamage() * 1 / (double) stack4.getMaxDamage();
                                        DrawHelper.drawRect(endPosX + 3.0D, (posY + ydiff * 4), endPosX + 2.0D, (posY + ydiff * 4) - 1.0D - (diff1 * percent), new Color(78, 206, 229).getRGB());

                                        String stackname = (stack.getDisplayName().equalsIgnoreCase("Air")) ? "0" : !(stack4.getItem() instanceof ItemArmor) ? stack4.getDisplayName() : stack4.getMaxDamage() - stack4.getItemDamage() + "";
                                        if (mc.player.getDistanceToEntity(player) < 20) {
//                                        mc.getRenderItem().renderItemIntoGUI(stack4, endPosX + 4, (posY + ydiff * 4) - (diff1 / 2) - 18);
                                            Fonts.neverlose500_13.drawStringWithShadow(stackname, (float) endPosX + 5, (float) ((posY + ydiff * 4) - (diff1 / 2)) - (Fonts.neverlose500_13.getStringHeight(stack4.getMaxDamage() - stack4.getItemDamage() + "") / 2), -1);
                                        }
                                    }
                                }
                            } else if (entity instanceof EntityItem) {
                                ItemStack itemStack = ((EntityItem) entity).getEntityItem();
                                if (itemStack.isItemStackDamageable()) {
                                    int maxDamage = itemStack.getMaxDamage();
                                    float itemDurability = (maxDamage - itemStack.getItemDamage());
                                    double durabilityWidth = (endPosX - posX) * itemDurability / maxDamage;
                                    DrawHelper.drawRect(posX - 0.5D, endPosY + 1.5D, posX - 0.5D + endPosX - posX + 1.0D, endPosY + 1.5D + 2.0D, Main.getClientColor().getRGB());
                                    DrawHelper.drawRect(posX, endPosY + 2.0D, posX + durabilityWidth, endPosY + 3.0D, Main.getClientColor(0, 0, 4).getRGB());
                                }
                            }
                        }
                    }
                }
            }
        }
        GL11.glPopMatrix();
        GlStateManager.enableBlend();
        entityRenderer.setupOverlayRendering();
    }


    @EventTarget
    public void onRenderNameTags(EventNameTags event) {
        String mode = Main.instance.settingsManager.getSettingByName("ESP Mode").getValString();
        if (Main.settingsManager.getSettingByName(Main.featureDirector.getModule(EntityESP.class), "ESPTags").getValBoolean()) {
            event.setCancelled(true);
        }
    }

    private void collectEntities() {
        this.collectedEntities.clear();
        List<Entity> playerEntities = mc.world.loadedEntityList;
        for (int i = 0, playerEntitiesSize = playerEntities.size(); i < playerEntitiesSize; i++) {
            Entity entity = playerEntities.get(i);
            if (isValid(entity))
                this.collectedEntities.add(entity);
        }
    }
    private Vector3d project2D(int scaleFactor, double x, double y, double z) {
        GL11.glGetFloat(2982, this.modelview);
        GL11.glGetFloat(2983, this.projection);
        GL11.glGetInteger(2978, this.viewport);
        if (GLU.gluProject((float) x, (float) y, (float) z, this.modelview, this.projection, this.viewport, this.vector))
            return new Vector3d((this.vector.get(0) / scaleFactor), ((Display.getHeight() - this.vector.get(1)) / scaleFactor), this.vector.get(2));
        return null;
    }
    private boolean isValid(Entity entity) {
        if (entity == mc.player && (mc.gameSettings.thirdPersonView == 0))
            return false;
        if (entity.isDead)
            return false;
        if (!Main.settingsManager.getSettingByName(Main.featureDirector.getModule(EntityESP.class), "ESPInvisibles").getValBoolean() && entity.isInvisible())
            return false;
        if (Main.settingsManager.getSettingByName(Main.featureDirector.getModule(EntityESP.class), "ESPItems").getValBoolean() && entity instanceof EntityItem && mc.player.getDistanceToEntity(entity) < 10.0F)
            return true;
        if (Main.settingsManager.getSettingByName(Main.featureDirector.getModule(EntityESP.class), "ESPAnimals").getValBoolean() && entity instanceof net.minecraft.entity.passive.EntityAnimal)
            return true;
        if (Main.settingsManager.getSettingByName(Main.featureDirector.getModule(EntityESP.class), "ESPPlayers").getValBoolean() && entity instanceof EntityPlayer)
            return true;
        return (Main.settingsManager.getSettingByName(Main.featureDirector.getModule(EntityESP.class), "ESPMonsters").getValBoolean() && (entity instanceof net.minecraft.entity.monster.EntityMob || entity instanceof net.minecraft.entity.monster.EntitySlime || entity instanceof net.minecraft.entity.boss.EntityDragon || entity instanceof net.minecraft.entity.monster.EntityGolem));
    }


    @Override
    public void onEnable() {
        super.onEnable();
    }

    public void onDisable() {
        super.onDisable();
    }
}
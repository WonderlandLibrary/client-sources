package dev.star.module.impl.render;

import dev.star.commands.impl.FriendCommand;
import dev.star.event.impl.player.UpdateEvent;
import dev.star.event.impl.render.NametagRenderEvent;
import dev.star.event.impl.render.Render2DEvent;
import dev.star.event.impl.render.Render3DEvent;
import dev.star.module.Category;
import dev.star.module.Module;
import dev.star.module.settings.ParentAttribute;
import dev.star.module.settings.impl.BooleanSetting;
import dev.star.module.settings.impl.ModeSetting;
import dev.star.module.settings.impl.MultipleBoolSetting;
import dev.star.module.settings.impl.NumberSetting;
import dev.star.utils.render.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.StringUtils;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import java.awt.*;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static net.minecraft.util.EnumChatFormatting.*;
import static org.lwjgl.opengl.GL11.*;

public class Nametags extends Module {

    public Nametags() {
        super("Nametags", Category.RENDER, "Renders Nametags");
        HealthType.addParent(NameTagStyle, modeSetting -> modeSetting.is("Simple"));

        this.addSettings(NameTagStyle,Health ,HealthType, Distance,  BackGround,  Armor,  BackGround);
    }

    private final List<Player> validEntities = new CopyOnWriteArrayList<>();
    public static final BooleanSetting Health = new BooleanSetting("Health", true);
    public static final BooleanSetting Distance = new BooleanSetting("Distance", true);
    public static final BooleanSetting BackGround = new BooleanSetting("BackGround", true);
    public static final BooleanSetting Armor = new BooleanSetting("Armor", true);
    private final NumberSetting backgroundAlpha = new NumberSetting("Background Alpha", 100, 255, 50, 1);

    private final ModeSetting HealthType = new ModeSetting("Health Mode", "Bar", "Bar", "Value");
    private final ModeSetting NameTagStyle = new ModeSetting("Name Tag Style", "Simple", "Simple", "Modern");



    @Override
    public void onUpdateEvent(UpdateEvent event ) {
        this.validEntities.clear();
    }

    @Override
    public void onDisable() {
        this.validEntities.clear();
        super.onDisable();
    }

    private  Player getPlayerByEntity(EntityLivingBase entity) {
        return this.validEntities.stream().filter(player -> player.entity.equals(entity)).findFirst().orElse(null);
    }

    @Override
    public void onNametagRenderEvent(NametagRenderEvent e) {
        e.cancel();
    }

    @Override
    public void onRender2DEvent(Render2DEvent render2DEvent) {
        this.validEntities.forEach(Player::render);
    }

    private final NumberFormat df = new DecimalFormat("0.#");


    @Override
    public void onRender3DEvent(Render3DEvent event) {
        this.mc.theWorld.getLoadedEntityList().stream() //
                .filter(EntityPlayer.class::isInstance) //
                .filter(entity -> !entity.isInvisible()) //
                .filter(Entity::isEntityAlive) //
                .map(EntityLivingBase.class::cast) //
                .filter(entity -> !this.validEntities.contains(getPlayerByEntity(entity))) //
                .forEach(entity -> this.validEntities.add(new Player(entity))); //

        this.validEntities.forEach(player -> {

            if (player.entity == mc.thePlayer)
                this.validEntities.remove(player);

            final float x = (float) (player.entity.lastTickPosX + (player.entity.posX - player.entity.lastTickPosX) * event.getTicks() - this.mc.getRenderManager().renderPosX), //
                    y = (float) (player.entity.lastTickPosY + 2.3 + (player.entity.posY + 2.3 - (player.entity.lastTickPosY + 2.3)) * event.getTicks() - this.mc.getRenderManager().renderPosY), //
                    z = (float) (player.entity.lastTickPosZ + (player.entity.posZ - player.entity.lastTickPosZ) * event.getTicks() - this.mc.getRenderManager().renderPosZ);
            player.positions = player.convertTo2D(x, y, z);
        });
    }


    private class Player {

        private final EntityLivingBase entity;
        private double[] positions = {0, 0, 0};

        public Player(EntityLivingBase entity) {
            this.entity = entity;
        }

        void render() {
            GL11.glPushMatrix();
            final ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
            final float x = (float) (this.positions[0] / scaledResolution.getScaleFactor()), //
                    y = (float) (this.positions[1] / scaledResolution.getScaleFactor()), //
                    z = (float) (this.positions[2] / scaledResolution.getScaleFactor());

            final String health = Health.isEnabled() ? HealthType.is("Value") ? " " + (int) (this.entity.getHealth() + this.entity.getAbsorptionAmount()) : "" : "";//
            final String distance = Distance.isEnabled() ? " " + (int) Minecraft.getMinecraft().thePlayer.getDistanceToEntity(this.entity) + "m" : "";
            String formattedName = this.entity.getDisplayName().getFormattedText();


            GL11.glTranslatef(x, y, z);

            float amp = 1;
            switch (Minecraft.getMinecraft().gameSettings.guiScale) {
                case 0:
                    amp = 0.5F;
                    break;
                case 1:
                    amp = 2.0F;
                    break;
                case 3:
                    amp = 0.6666666666666667F;
            }


            if (this.positions[2] < 0.0 || this.positions[2] >= 1.0) {
                GlStateManager.popMatrix();
                return;
            }

            ScaledResolution res = new ScaledResolution(mc);
            double scale2 = res.getScaleFactor() / Math.pow(res.getScaleFactor(), 2.0);
            GL11.glScaled(scale2, scale2, scale2);

            GlStateManager.disableDepth();
            String content = RESET + formattedName + GRAY + distance;
            final float rectLength = Math.abs(-(getStringWidth(content) / 2) - 3 - (getStringWidth(content) / 2 + 4)), maxHealth = (int) (this.entity.getMaxHealth() + this.entity.getAbsorptionAmount()), amplifier = 100 / maxHealth, percent = (int) ((this.entity.getHealth() + this.entity.getAbsorptionAmount()) * amplifier), space = rectLength / 100; // @on
            int n = Health.isEnabled() && HealthType.is("Value") ? 5 : 0;
            final float contentWidth = getStringWidth(content) / 2F;

            final ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());




/*            if (mc.gameSettings.thirdPersonView == 0 && this.positions[0] >= xBnd2 * 2 && this.positions[0] <= xBnd1 * 2 && this.positions[1] >= yBnd1 * 2 && this.positions[1] <= yBnd2 * 2) {
                SFBOLD_20.drawString("Middle click to teleport!", -(SFBOLD_20.stringWidth("Middle click to teleport") / 2F), -getYOffset() - 18, 0xffffffff, true);

                if (Mouse.isButtonDown(2)) {
                    if (tpTimer.delay(1000)) {
                        String command = mc.isSingleplayer() ? "/tp" : ".tp";
                        mc.player.sendChatMessage(command + " " + entity.getName());
                        tpTimer.reset();
                    }
                }
            }*/




            if (Armor.isEnabled()) {
                renderArmor((EntityPlayer) this.entity);
            }


            if (NameTagStyle.is("Simple")) {


                if (BackGround.isEnabled()) {
                    Gui.drawRect(-contentWidth - 2 - n, -8.0F - getYOffset(), getStringWidth(content + health) / 2F + (HealthType.is("Bar") ? 2 : getStringWidth(health) - 3 - n), (Health.isEnabled() ? HealthType.is("Bar") ? 6 : 5 : 5) - getYOffset(), new Color(0, 0, 0, backgroundAlpha.getValue().intValue()).getRGB());
                }

                drawString(content, -contentWidth - n, -getYOffset() - 5, 16777215);
                drawString(health, contentWidth - n, -getYOffset() - 5, getHealthColor());


                if (Health.isEnabled() && HealthType.is("Bar")) {
                    Gui.drawRect(-contentWidth - 2, 5 - getYOffset(), -contentWidth - 5 + percent * space, 6 - getYOffset(), getHealthColor());
                }


            }


            if (NameTagStyle.is("Modern")) {
                float healthValue = entity.getHealth() / entity.getMaxHealth();
                Color healthColor = healthValue > .75 ? new Color(66, 246, 123) : healthValue > .5 ? new Color(228, 255, 105) : healthValue > .35 ? new Color(236, 100, 64) : new Color(255, 65, 68);
                String name = entity.getDisplayName().getFormattedText();
                StringBuilder text = new StringBuilder(
                        (FriendCommand.isFriend(entity.getName()) ? "§d" :  "§f") + name);
                if (Health.isEnabled()) {
                    text.append(String.format(" §7[§r%s ❤§7]",df.format(entity.getHealth())));
                }
                final float contentWidth1 = getStringWidth(text.toString()) / 2F;

                if (BackGround.isEnabled()) {
                    Gui.drawRect(-contentWidth1 - 4 - n, -8.0F - getYOffset(), getStringWidth(text.toString()) / 2F + 2,  5 - getYOffset(), new Color(0, 0, 0, backgroundAlpha.getValue().intValue()).getRGB());
                }

                RenderUtil.resetColor();
                drawString(text.toString(), -contentWidth1 - 2 - n, -getYOffset() - 5, getHealthColor());


            }

            GlStateManager.enableDepth();
            GL11.glPopMatrix();
        }

        private void drawString(String string, float x, float y, int color) {

            mc.fontRendererObj.drawString(string, x, y, color);

        }

        private float getStringWidth(String string) {
                return mc.fontRendererObj.getStringWidth(string);
        }

        private float getYOffset() {
            final float distanceToEntity = Minecraft.getMinecraft().thePlayer.getDistanceToEntity(this.entity);

            if (HealthType.is("Bar")) {
                return (float) Math.max(getDistance() * (distanceToEntity >= 110 ? 0.058 : 0.032 + 4 / distanceToEntity), 1);
            } else {
                return (float) Math.max(getDistance() * (distanceToEntity >= 110 ? 0.046 : 0.02 + 4 / distanceToEntity), 1);
            }
        }

        private int getHealthColor() {
            final float f = this.entity.getHealth(), // @off
                    f1 = this.entity.getMaxHealth(),
                    f2 = Math.max(0.0F, Math.min(f, f1) / f1); // @on
            return Color.HSBtoRGB(f2 / 3.0F, 1, 1) | 0xFF000000;
        }

        private int getDistance() {
            final int diffX = (int) Math.abs(Minecraft.getMinecraft().thePlayer.posX - this.entity.posX), // @off
                    diffY = (int) Math.abs(Minecraft.getMinecraft().thePlayer.posY - this.entity.posY),
                    diffZ = (int) Math.abs(Minecraft.getMinecraft().thePlayer.posZ - this.entity.posZ); // @on
            return (int) Math.sqrt(diffX * diffX + diffY * diffY + diffZ * diffZ);
        }

        private double[] convertTo2D(double x, double y, double z) {
            final FloatBuffer screenCoords = BufferUtils.createFloatBuffer(3);
            final IntBuffer viewport = BufferUtils.createIntBuffer(16);
            final FloatBuffer modelView = BufferUtils.createFloatBuffer(16);
            final FloatBuffer projection = BufferUtils.createFloatBuffer(16);

            GL11.glGetFloat(2982, modelView);
            GL11.glGetFloat(2983, projection);
            GL11.glGetInteger(2978, viewport);

            final boolean result = GLU.gluProject((float) x, (float) y, (float) z, modelView, projection, viewport, screenCoords);
            return result ? new double[]{(double) screenCoords.get(0), (double) ((float) Display.getHeight() - screenCoords.get(1)), (double) screenCoords.get(2)} : null;
        }

        private void renderArmor(EntityPlayer player) {
            ItemStack[] renderStack = player.inventory.armorInventory;
            ItemStack armourStack;
            int xOffset = 0;

            for (ItemStack aRenderStack : renderStack) {
                armourStack = aRenderStack;

                if (armourStack != null) xOffset -= 8;
            }

            if (player.getCurrentEquippedItem() != null) {
                xOffset -= 8;

                final ItemStack stock = player.getCurrentEquippedItem().copy();

                if (stock.hasEffect() && (stock.getItem() instanceof ItemTool || stock.getItem() instanceof ItemArmor))
                    stock.stackSize = 1;

                renderItemStack(stock, xOffset, -25 - getYOffset() * 1.5f);
                xOffset += 16;
            }

            renderStack = player.inventory.armorInventory;

            for (int index = 3; index >= 0; index--) {
                armourStack = renderStack[index];

                if (armourStack != null) {
                    renderItemStack(armourStack, xOffset, -25 - getYOffset() * 1.5f);
                    xOffset += 16;
                }
            }

            GlStateManager.color(1, 1, 1, 1);
        }

        private void renderItemStack(final ItemStack stack, int x, float y) {
            GlStateManager.pushMatrix();
            GlStateManager.depthMask(true);
            GlStateManager.clear(256);
            RenderHelper.enableStandardItemLighting();

            Minecraft.getMinecraft().getRenderItem().zLevel = -150.0F;

            GlStateManager.disableDepth();
            GlStateManager.disableTexture2D();
            GlStateManager.enableBlend();
            GlStateManager.enableAlpha();
            GlStateManager.enableTexture2D();
            GlStateManager.enableLighting();
            GlStateManager.enableDepth();
            GlStateManager.scale(1, 1, 1);
            Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(stack, x, (int) y);
            Minecraft.getMinecraft().getRenderItem().renderItemOverlays(mc.fontRendererObj, stack, x, (int) y);
            Minecraft.getMinecraft().getRenderItem().zLevel = 0.0f;

            RenderHelper.disableStandardItemLighting();
            GlStateManager.disableCull();
            GlStateManager.enableAlpha();
            GlStateManager.disableBlend();
            GlStateManager.disableLighting();
            final float s = 0.5F;
            GlStateManager.scale(s, s, s);
            GlStateManager.disableDepth();
            GlStateManager.enableDepth();
            GlStateManager.scale(2.0F, 2.0F, 2.0F);
            GlStateManager.popMatrix();
        }

    }

    //region Lombok
    public List<Player> getValidEntities() {
        return this.validEntities;
    }

}

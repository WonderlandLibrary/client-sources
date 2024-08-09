package ru.FecuritySQ.module.дисплей;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IngameGui;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AirItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import ru.FecuritySQ.FecuritySQ;
import ru.FecuritySQ.command.imp.GPSCommand;
import ru.FecuritySQ.drag.Dragging;
import ru.FecuritySQ.event.Event;
import ru.FecuritySQ.event.imp.EventHud;
import ru.FecuritySQ.font.Fonts;
import ru.FecuritySQ.module.Module;
import ru.FecuritySQ.module.визуальные.Arrows;
import ru.FecuritySQ.module.сражение.KillAura;
import ru.FecuritySQ.option.imp.OptionTheme;
import ru.FecuritySQ.shader.ShaderUtil;
import ru.FecuritySQ.utils.MathUtil;
import ru.FecuritySQ.utils.RenderUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Hud extends Module {

    Dragging keybinds = FecuritySQ.get().createDrag(this, "Список биндов", 5, 80);
    Dragging targetHud = FecuritySQ.get().createDrag(this, "TargetHud", 5, 100 );
    public ItemRenderer renderItem;
    public static ShaderUtil circleShader = new ShaderUtil("circleFlex");
    private MatrixStack stack = new MatrixStack();

    public Hud() {
        super(Category.Дисплей, GLFW.GLFW_KEY_0);
        setEnabled(true);
        renderItem = mc.getItemRenderer();
    }

    double keybindHeight = 0;
    @Override
    public void event(Event e) {
        if (e instanceof EventHud && isEnabled()) {

            float watermarkX = 5;
            float watermarkY = 5;

            String display = "FecuritySQ | Free | 0.1b";

            RenderUtil.drawRound(watermarkX, watermarkY, Fonts.MCR14.getStringWidth(display) + 16, 12, 0, new Color(0, 0, 0, 200).getRGB());

            RenderUtil.drawRound(watermarkX - 2, watermarkY - 1, Fonts.MCR14.getStringWidth(display) + 20, 1, 1, FecuritySQ.get().theme.getColor());

            Fonts.mntsb16.drawString(new MatrixStack(), display, watermarkX + 2, watermarkY, -1);



            String xyz = (int) mc.player.getPosX() + ", " + (int) mc.player.getPosY() + ", " + (int) mc.player.getPosZ();


            int offsetInfo = 5;

            float infoX = offsetInfo;
            float infoY = mc.getMainWindow().getScaledHeight() - 20;

            RenderUtil.drawRound(infoX, infoY, Fonts.mntsb.getStringWidth(xyz) + 8, 15, 0, new Color(0,0,0, 130).getRGB());
            RenderUtil.drawRound(infoX - 1, infoY, 1, 15, 0, FecuritySQ.get().theme.getColor());

            RenderUtil.drawImage(stack, new ResourceLocation("FecuritySQ/icons/xyz.png"), infoX + 2, infoY + 1, 12, 12);
            Fonts.mntsb16.drawString(stack, xyz, infoX + 16, infoY + 2, -1);
            offsetInfo+=Fonts.mntsb.getStringWidth(xyz) + 15;

            float infoX2 = offsetInfo;
            String speed = String.format("%.1f", MathUtil.getSpeed(mc.player)) + " b/s";
            RenderUtil.drawRound(infoX2, infoY, Fonts.mntsb.getStringWidth(speed) + 13, 15, 0, new Color(0,0,0, 130).getRGB());
            RenderUtil.drawRound(infoX2 - 1, infoY, 1, 15, 0, FecuritySQ.get().theme.getColor());

            RenderUtil.drawImage(stack, new ResourceLocation("FecuritySQ/icons/speed.png"), infoX2 + 2, infoY + 1, 12, 12);
            Fonts.mntsb16.drawString(stack, speed, infoX2 + 16, infoY + 2, -1);
            offsetInfo+=Fonts.mntsb.getStringWidth(speed) + 20;

            float infoX3 = offsetInfo;
            String fps = "FPS: " + mc.debug.split(" ")[0];
            RenderUtil.drawRound(infoX3, infoY, Fonts.mntsb.getStringWidth(fps), 15, 0, new Color(0,0,0, 130).getRGB());
            RenderUtil.drawRound(infoX3 - 1, infoY, 1, 15, 0, FecuritySQ.get().theme.getColor());
            Fonts.mntsb16.drawString(stack, fps, infoX3 + 3, infoY + 2, -1);


            List<Module> modules = new ArrayList<Module>(FecuritySQ.get().getModuleList());
            modules.sort(Comparator.comparingInt(m -> Fonts.mntsb16.getStringWidth(((Module) m).getName())).reversed());



            keybinds.setHeight(15);
            keybinds.setWidth(80);
           // RenderUtil.drawBlurredShadow(this.keybinds.getX() -1, keybinds.getY() -1, keybinds.getWidth() + 6, (float) keybindHeight + 1, 3, new Color(255, 255, 255));
            RenderUtil.drawRound(this.keybinds.getX(), keybinds.getY(), keybinds.getWidth() + 5, (float) keybindHeight, 3, new Color(28, 32, 46).getRGB());


            RenderUtil.drawRound(this.keybinds.getX(), keybinds.getY(), 1F, (float) keybindHeight, 3, FecuritySQ.get().theme.getColor());

            RenderUtil.drawImage(stack, new ResourceLocation("FecuritySQ/icons/keys.png"), keybinds.getX() + 4, keybinds.getY() + 2, 12, 12);

            Fonts.mntsb16.drawString(stack, keybinds.getName(), keybinds.getX() + 20, keybinds.getY() + 2, -1);

            double keybindOffset = (int) keybinds.getHeight();
            for (Module feature : modules) {
                if (feature.isEnabled() && feature.getKey() != 0 && feature.getKey() != GLFW.GLFW_KEY_0) {
                    GL11.glPushMatrix();
                    GL11.glTranslated(keybinds.getX() + 8 + Fonts.mntsb16.getStringWidth(feature.getName()) / 2, (float) (keybinds.getY() + keybindOffset), 0);
                    GL11.glScaled(feature.keybindScale, feature.keybindScale, 0);
                    GL11.glTranslated(-(keybinds.getX() + 8 + Fonts.mntsb16.getStringWidth(feature.getName()) / 2), -(this.keybinds.getY() + keybindOffset), 0);
                    feature.keybindScale = MathUtil.animation((float) feature.keybindScale, 1, 0.001f);
                    Fonts.mntsb16.drawString(stack, feature.getName(), keybinds.getX() + 4, this.keybinds.getY() + keybindOffset, -1);
                    Fonts.mntsb16.drawString(stack, "[" + GLFW.glfwGetKeyName(feature.getKey(), GLFW.glfwGetKeyScancode(feature.getKey())) + "]", keybinds.getX() + 71, this.keybinds.getY() + keybindOffset, -1);
                    GL11.glPopMatrix();
                    keybindOffset += 10;
                } else {
                    feature.keybindScale = 0;
                }
            }

            this.keybindHeight =  MathUtil.animate(keybindOffset + 2, this.keybindHeight, 0.1f);









            int offset = 1;
            int counts = 0;
            int counts2 = 0;
            int count = 0;

            for (Module feature : modules) {
                if (Float.isNaN(feature.slide)) feature.slide = 0;
                feature.slide = MathUtil.animation(feature.slide, feature.isEnabled() ? 1 : -0.4F, 0.01f);
                String name = feature.getName();

            }

            for (Module feature : modules) {
                String name = feature.getName();
                if (feature.slide > -0.4f) {
                    RenderUtil.drawRound(mc.getMainWindow().getScaledWidth() - Fonts.MCR14.getStringWidth(name) * feature.slide -4 - offset, count + offset, mc.getMainWindow().getScaledWidth()  - offset + Fonts.MCR14.getStringWidth(name) * (1 - feature.slide), Fonts.MCR14.getFontHeight() * feature.slide + 3, 1, new Color(0, 0, 0, 255).getRGB());
                 //   RenderUtil.drawGradientRects(mc.getMainWindow().getScaledWidth() - Fonts.MCR14.getStringWidth(name) * feature.slide -4 - offset, count + offset, mc.getMainWindow().getScaledWidth()  - offset + Fonts.MCR14.getStringWidth(name) * (1 - feature.slide), Fonts.MCR14.getFontHeight() * feature.slide + 3,  FecuritySQ.get().theme.getColor(), FecuritySQ.get().theme.getColor2());
                    Fonts.MCR14.drawStringWithShadow(new MatrixStack(), name, mc.getMainWindow().getScaledWidth() - Fonts.MCR14.getStringWidth(name) * feature.slide - 4 - offset, count + 1 + offset, -1);
                    count += (Fonts.MCR14.getFontHeight() * feature.slide + 3);
                }
            }
        }
        if(e instanceof EventHud){
            if (GPSCommand.gps) {

                if ((int) Math.round((int) Math.hypot(mc.player.getPosX() - GPSCommand.x, mc.player.getPosZ() - GPSCommand.z)) <= 3) {
                    RenderUtil.addChatMessage(TextFormatting.GREEN + "[GPS] Вы успешно дошли до цели.");
                    GPSCommand.gps = false;
                }

                double yaw = Math.toDegrees(Math.atan2(GPSCommand.z - mc.player.getPosZ(), GPSCommand.x - mc.player.getPosX())) - mc.player.rotationYaw
                        - 90;
                double dst = Math.sqrt(Math.pow(GPSCommand.x - mc.player.getPosX(), 2) + Math.pow(GPSCommand.z - mc.player.getPosZ(), 2));
                GL11.glPushMatrix();
                GL11.glTranslated(mc.getMainWindow().getScaledWidth() / 2 + 0.5,  mc.getMainWindow().getScaledHeight() / 2 - 115, 0);
                GL11.glTranslated(
                        (Math.cos(Math.toRadians(yaw - 90)) * 1.3)
                                * (Fonts.mntsb16.getStringWidth("(GPS) " + (int) dst + "m") / 2),
                        Math.sin(Math.toRadians(yaw - 90)) * 10, 0);
                GL11.glRotated(yaw, 0, 0, 1);
                Arrows.drawTriangle(255, 255, 255);
                GL11.glPopMatrix();
                GL11.glPushMatrix();
                GL11.glTranslated(mc.getMainWindow().getScaledWidth() / 2, mc.getMainWindow().getScaledHeight() / 2 - 115, 0);
                Fonts.mntsb16.drawCenteredString(new MatrixStack(), "(GPS) " + (int) dst + "m", 0, 0, -1);
                GL11.glPopMatrix();
            }





            targetHud.setWidth(100);
            targetHud.setHeight(38);


            int WposX = (int) targetHud.getX();
            int WposY = (int) targetHud.getY();

            PlayerEntity target = (KillAura.target instanceof PlayerEntity) ? KillAura.target : mc.player;

            if (target == null || target == mc.player && !(mc.currentScreen instanceof ChatScreen)) {
                return;
            }
            RenderUtil.drawRound(WposX, WposY, targetHud.getWidth(), targetHud.getHeight(), 5, new Color(20, 22, 30, 240).getRGB());
            RenderUtil.drawRound(WposX + 8, WposY + 5, 12, 12, 2, new Color(28, 32, 46).getRGB());
            RenderUtil.drawRound(WposX + 8, WposY + 22, 12, 12, 2, new Color(28, 32, 46).getRGB());
            Fonts.mntsb16.drawCenteredString(stack, target.getName().getString(), WposX + targetHud.getWidth() / 2 - 6, WposY + 10, -1);

            RenderUtil.drawCircle(WposX + 38, WposY + 26, 0,360,8, 4f,false, new Color(22,22,22).getRGB());
            RenderUtil.drawCircle(WposX + 38, WposY + 26, 90,90 + ((360) / target.getMaxHealth() * target.getHealth()),8, 2f,false, FecuritySQ.get().theme.getColor());
            Fonts.MCR14.drawCenteredString(stack,MathHelper.floor(target.getHealth()) + "",WposX + 37, WposY + 25, -1);

            ArrayList<ItemStack> items = new ArrayList<ItemStack>();

            ItemStack heldStack = target.inventory.getCurrentItem();
            ItemStack ofHandStack = target.getHeldItemOffhand();
            if(heldStack != null && !(heldStack.getItem() instanceof AirItem)) {
                items.add(heldStack);
            }

            if(ofHandStack != null && !(ofHandStack.getItem() instanceof AirItem)) {
                items.add(ofHandStack);
            }

            int ItemOffset = 0;
            float itemX = WposX + 9;
            float itemY = WposY + 6;


            for(ItemStack item : items) {
                GL11.glPushMatrix();
                GL11.glTranslatef(itemX, itemY + ItemOffset, 0);
                GL11.glScaled(0.62, 0.62, 0.62);
                GL11.glTranslatef(-(itemX), -(itemY + ItemOffset), 0);
                drawItem(item, itemX, (int) itemY + ItemOffset, -200);
                GL11.glPopMatrix();
                ItemOffset+=17;
            }


            GL11.glPushMatrix();
            GlStateManager.pushMatrix();
            circleShader.init();
            GlStateManager.scaled(0.5, 0.5, 0.5);

            circleShader.setUniformf("radius", 0.5F);
            circleShader.setUniformf("glow", 0.04F);
            circleShader.unload();

            GlStateManager.popMatrix();

            RenderSystem.disableDepthTest();
            RenderSystem.enableBlend();
            RenderSystem.color4f(0.8f, 0.8f, 0.8f, 1);
            RenderSystem.blendFunc(770, 771);
            try{
                Minecraft.getInstance().getTextureManager().bindTexture(mc.getConnection().getPlayerInfo(target.getGameProfile().getName()).getLocationSkin());
            }catch (Exception ex){
                Minecraft.getInstance().getTextureManager().bindTexture(mc.player.getLocationSkin());
            }

            IngameGui.blit(stack, (int) (WposX + targetHud.getWidth()) - 32, WposY + 5,28f,28f,28,28, 225, 225);
            RenderSystem.disableBlend();
            RenderSystem.enableDepthTest();
            RenderSystem.color3f(255f, 255f, 255f);

            GL11.glPopMatrix();

        }
    }


    public void drawItem(ItemStack item, float x, int y, float size){
        GL11.glPushMatrix();
        GL11.glScalef(-0.01f * size, -0.01f * size, -0.01f * size);
        GL11.glPushMatrix();
        GlStateManager.disableLighting();
        GL11.glPopMatrix();
        GlStateManager.scaled(0.5, 0.5, 0.5);
        renderItem.renderItemIntoGUI(item, (int) x, y);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        String count = "";
        if (item.getCount() > 1)
        {
            count = item.getCount() + "";
        }
        renderItem.renderItemOverlayIntoGUI(mc.fontRenderer, item, (int) x, y, count);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableLighting();
        GL11.glPopMatrix();
        GL11.glDisable(GL11.GL_DEPTH_TEST);

    }
}
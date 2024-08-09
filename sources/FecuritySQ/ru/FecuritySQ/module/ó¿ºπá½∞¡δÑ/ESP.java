package ru.FecuritySQ.module.визуальные;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.StringUtils;
import net.minecraft.util.math.vector.Vector3d;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import ru.FecuritySQ.FecuritySQ;
import ru.FecuritySQ.event.Event;
import ru.FecuritySQ.event.imp.EventHud;
import ru.FecuritySQ.event.imp.EventRender;
import ru.FecuritySQ.font.Fonts;
import ru.FecuritySQ.module.Module;
import ru.FecuritySQ.option.imp.OptionBoolList;
import ru.FecuritySQ.option.imp.OptionBoolean;
import ru.FecuritySQ.option.imp.OptionColor;
import ru.FecuritySQ.shader.StencilUtil;
import ru.FecuritySQ.utils.ColorUtil;
import ru.FecuritySQ.utils.RenderUtil;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;


public class ESP extends Module {

    public OptionBoolList elements = new OptionBoolList("Элементы",
            new OptionBoolean("Коробка", true),
            new OptionBoolean("Жизни", true),
            new OptionBoolean("Имя", true),
            new OptionBoolean("Вещь в руке", true));
    public OptionBoolList ignore = new OptionBoolList("Игнорировать",
            new OptionBoolean("Друзья", false),
            new OptionBoolean("Невидмки", false));

    public OptionColor color = new OptionColor("Цвет", new Color(255, 255, 255));
    public OptionColor friendcolor = new OptionColor("Цвет друга", new Color(0, 85, 243));

    public MatrixStack stack = new MatrixStack();
    public ESP() {
        super(Category.Визуальные, GLFW.GLFW_KEY_0);
        addOption(elements);
        addOption(ignore);
        addOption(color);
        addOption(friendcolor);
    }

    @Override
    public void event(Event event) {

        if(event instanceof EventHud && isEnabled()){

            List<PlayerEntity> valid = mc.world.getPlayers().stream().filter(entityLivingBase1 -> {
                if (entityLivingBase1.isInvisible() && ignore.setting("Невидмки").get()) return false;
                if (ignore.setting("Друзья").get() && (FecuritySQ.get().getFriendManager().isFriend(entityLivingBase1.getName().getString())))
                    return false;

                if (entityLivingBase1.isInvisible() && ignore.setting("Невидмки").get()) return false;
                return mc.player != entityLivingBase1;
            }).collect(Collectors.toList());

            for(PlayerEntity entity : valid){
                double x = entity.lastTickPosX + (entity.getPosX() - entity.lastTickPosX) * mc.getRenderPartialTicks();
                double y = entity.lastTickPosY + (entity.getPosY() - entity.lastTickPosY) * mc.getRenderPartialTicks();
                double z = entity.lastTickPosZ + (entity.getPosZ() - entity.lastTickPosZ) * mc.getRenderPartialTicks();

                double[] headVec = RenderUtil.project(x, y + entity.getHeight() + 0.2f, z);
                double[] footVec = RenderUtil.project(x, y, z);

                if(headVec != null && footVec != null){
                    double headX = headVec[0];
                    double headY = headVec[1];
                    double footX = footVec[0];
                    double footY = footVec[1];
                    float dif = (float) Math.abs(footY - headY) / 4;
                    final float hp2 = entity.getHealth();
                    final float maxHealth = entity.getMaxHealth();
                    final double hpPercentage = hp2 / maxHealth;
                    final double hpHeight2 = (dif - headY) * hpPercentage;

                    GlStateManager.pushMatrix();

                    if (headY > footY) {
                        float saved = (float) headY;
                        headY = footY;
                        footY = saved;
                    }
                    if (headX > footX) {
                        float saved = (float) headX;
                        headX = footX;
                        footX = saved;
                    }


                    if (elements.setting("Коробка").get()) {
                        int boxColor = FecuritySQ.get().getFriendManager().isFriend(entity.getName().getString()) ? friendcolor.get().getRGB() : color.get().getRGB();
                        StencilUtil.initStencilToWrite();
                        AbstractGui.fill(stack.getLast().getMatrix(), (int) (headX - dif), (int) (headY + 1), (float) (footX + dif), (float) footY, FecuritySQ.get().theme.getColor());
                        StencilUtil.readStencilBuffer(2);
                        AbstractGui.fill(stack.getLast().getMatrix(), (int) (headX - dif), (int) (headY + 1) - 1, (float) (footX + dif) + 1, (float) footY + 1, new Color(0, 0, 0, 128).getRGB());
                      //  RenderUtil.drawBlurredShadow((int) ((headX - dif) - 1), (int) (headY + 1) - 0.5f, (float) (footX + dif) + 1.5f, (float) footY + 1.5f, 2, new Color(2));
                        AbstractGui.fill(stack.getLast().getMatrix(), (int) ((headX - dif) - 1), (int) (headY + 1) - 0.5f, (float) (footX + dif) + 1.5f, (float) footY + 1.5f, FecuritySQ.get().theme.getColor());
                        StencilUtil.uninitStencilBuffer();

                    }
                    if (elements.setting("Жизни").get()) {
                        RenderUtil.drawGradientRect((int) headX - dif - 4, (int) (headY + 1), (float) (headX) - dif - 3, (float) (headY + footY) / 2, ColorUtil.green.getRGB(), ColorUtil.orange.getRGB());
                        RenderUtil.drawGradientRect((int) headX - dif - 4, (int) (headY + footY) / 2, (float) (headX) - dif - 3, (float) footY, ColorUtil.orange.getRGB(), ColorUtil.red.getRGB());
                    }
                    if (elements.setting("Имя").get()) {
                        Fonts.MCR8.drawCenteredStringWithOutline(new MatrixStack(), StringUtils.stripControlCodes(entity.getName().getString()), footX, footY + 10, new Color(0, 0, 0).getRGB());
                    }

                    if (elements.setting("Вещь в руке").get()) {
                        String str = StringUtils.stripControlCodes(entity.getHeldItemMainhand().getDisplayName().getString());
                        str = str.replaceAll("[^A-Za-zА-Яа-я0-9 ]", "");
                        if (!entity.getHeldItemMainhand().getDisplayName().getString().equals("Air")) {
                            Fonts.MCR8.drawCenteredStringWithOutline(new MatrixStack(), str, footX, (float) round(footY + 4, 0.5), new Color(0, 0, 0).getRGB());
                        }
                    }
                    GlStateManager.popMatrix();
                }
            }
        }
    }

    public static double round(double num, double increment) {
        double v = (double) Math.round(num / increment) * increment;
        BigDecimal bd = new BigDecimal(v);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}

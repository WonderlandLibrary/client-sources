package fr.dog.module.impl.render;

import fr.dog.Dog;
import fr.dog.event.annotations.SubscribeEvent;
import fr.dog.event.impl.render.Render2DEvent;
import fr.dog.event.impl.render.RenderBlur;
import fr.dog.event.impl.render.RenderGlow;
import fr.dog.event.impl.world.TickEvent;
import fr.dog.module.Module;
import fr.dog.module.ModuleCategory;
import fr.dog.module.impl.combat.KillAuraModule;
import fr.dog.property.impl.ModeProperty;
import fr.dog.theme.Theme;
import fr.dog.util.render.ColorUtil;
import fr.dog.util.render.RenderUtil;
import fr.dog.util.render.model.ESPUtil;
import fr.dog.util.render.opengl.StencilUtil;
import fr.dog.util.render.animation.Animation;
import fr.dog.util.render.animation.Easing;
import fr.dog.util.render.font.Fonts;
import fr.dog.util.render.font.TTFFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class TargetHUD extends Module {
    private final ModeProperty mode = ModeProperty.newInstance("Mode", new String[] {"Normal","HVH","Butt", "Legit", "Diablo", "Exhibition"}, "Normal");
    private final DecimalFormat df = new DecimalFormat("#.##");
    private float renderWidth, renderHeight = 0;
    private final Animation animation = new Animation(Easing.EASE_OUT_BACK, 500);
    private final HashMap<String, ArrayList<Runnable>> processingEffects = new HashMap<>();
    private EntityPlayer target, temp;

    public TargetHUD() {
        super("TargetHUD", ModuleCategory.RENDER);
        this.setDraggable(true);
        this.setX(200);
        this.setY(200);
        this.registerProperty(mode);
        processingEffects.put("Glow", new ArrayList<>());
        processingEffects.put("Blur", new ArrayList<>());
    }

    @SubscribeEvent
    private void onTick(TickEvent event) {
        temp = mc.currentScreen instanceof GuiChat ? mc.thePlayer : setupTarget();

        if(temp != null)
            target = temp;

    }

    @SubscribeEvent
    private void onRender(Render2DEvent event) {
        if(target == null)
            return;

        animation.run(temp != null ? 1 : 0);
        setupEffects();
        RenderUtil.scale(() -> drawTargetHUD(this.getX(), this.getY(), target),
                getX() + getWidth() / 2, getY() + getHeight() / 2, animation.getValue());

    }

    @SubscribeEvent
    private void onGlow(RenderGlow event) {
        RenderUtil.scale(() -> {
            processingEffects.get("Glow").forEach(Runnable::run);
        },
    getX() + getWidth() / 2, getY() + getHeight() / 2, animation.getValue());

    }

    @SubscribeEvent
    private void onBlur(RenderBlur event) {
        RenderUtil.scale(() -> {
            processingEffects.get("Blur").forEach(Runnable::run);
        },
    getX() + getWidth() / 2, getY() + getHeight() / 2, animation.getValue());
    }

    private void drawTargetHUD(float x, float y, EntityPlayer target) {
        if(animation.getValue() <= 0.1)
            return;

        switch (mode.getValue()) {
            case "Normal" -> {
                changeSize(100,50);
                TTFFontRenderer osb14 = Fonts.getOpenSansBold(14);
                TTFFontRenderer osr16 = Fonts.getOpenSansRegular(16);
                TTFFontRenderer osm20 = Fonts.getOpenSansMedium(20);

                String name = target.getName();

                float playerHealth = (mc.thePlayer.getHealth() + mc.thePlayer.getAbsorptionAmount());

                float targetHealth = (target.getHealth() + target.getAbsorptionAmount());
                float maxTargetHealth = (target.getMaxHealth() + target.getAbsorptionAmount());

                double healthMultiplier = Math.min(1.0, Math.max(0.0, targetHealth / maxTargetHealth));

                setWidth(Math.max(osm20.getWidth(name) + 36, 100));
                setHeight(46);

                float barHeight = 12;

                RenderUtil.drawRect(x, y, getWidth(), getHeight(), new Color(0, 0, 0, 155));
                RenderUtil.drawRect(x, y, getWidth(), barHeight, new Color(0, 0, 0, 55));

                osb14.drawStringWithShadow("Target HUD", x + getWidth() / 2 - osb14.getWidth("Target HUD") / 2, y + barHeight / 2 - osb14.getHeight("Target HUD") / 2,
                        new Color(255, 255, 255).getRGB());

                Gui.drawRect(-1, -1, -1, -1, -1);
                RenderUtil.drawHead(target, x + 2, y + barHeight + 2, 30);

                float textXOffset = 34f; // 32 + 2
                osm20.drawStringWithShadow(name, x + textXOffset, y + barHeight, new Color(255, 255, 255).getRGB());
                osr16.drawStringWithShadow(df.format(target.getHealth()) + " (" + df.format(100 * healthMultiplier) + "%)", x + textXOffset, y + barHeight + osm20.getHeight(name), new Color(255, 255, 255).getRGB());

                String letter = "?";

                if (playerHealth > targetHealth)
                    letter = "W";
                else if (playerHealth < targetHealth)
                    letter = "L";

                osr16.drawStringWithShadow(letter, x + getWidth() - osr16.getWidth(letter) - 2, y + barHeight + osm20.getHeight(name), new Color(255, 255, 255).getRGB());

                RenderUtil.horizontalGradient(x + textXOffset, y + barHeight + 21, (getWidth() - textXOffset - 2) * healthMultiplier, 11, new Color(255, 255, 255), new Color(155, 155, 155));
            }
            case "HVH" -> {
                changeSize(100,35);

                RenderUtil.drawRect(this.getX(), this.getY(), this.getWidth(), this.getHeight(), new Color(69, 69, 69, 50));
                mc.fontRendererObj.drawString(target.getName(), this.getX() + 2, this.getY() + 2, Color.WHITE.getRGB());

                String wl = EnumChatFormatting.GREEN + "W";
                if (target.getHealth() == mc.thePlayer.getHealth()) {
                    wl = EnumChatFormatting.YELLOW + "E";
                }
                if (target.getHealth() > mc.thePlayer.getHealth()) {
                    wl = EnumChatFormatting.RED + "L";
                }
                mc.fontRendererObj.drawString("diff : " + new DecimalFormat("#.##").format(mc.thePlayer.getHealth() - target.getHealth()), x + this.getWidth() - 2 - mc.fontRendererObj.getStringWidth("diff : " + new DecimalFormat("#.##").format(mc.thePlayer.getHealth() - target.getHealth())), y + 12, Color.WHITE.getRGB());

                mc.fontRendererObj.drawString(wl, x + 2, y + 12, Color.WHITE.getRGB());

                RenderUtil.drawRect(x + 1, y + 22, (mc.thePlayer.getHealth() / mc.thePlayer.getMaxHealth()) * this.getWidth() - 2, 2, new Color(94, 255, 0));
                RenderUtil.drawRect(x + 1, y + 25, (target.getHealth() / target.getMaxHealth()) * this.getWidth() - 2, 2, new Color(255, 0, 0));
            }
            case "Raven" -> {
                changeSize(100,35);

                String wl = EnumChatFormatting.GREEN + "W";
                if (target.getHealth() == mc.thePlayer.getHealth()) {
                    wl = EnumChatFormatting.YELLOW + "E";
                }
                if (target.getHealth() > mc.thePlayer.getHealth()) {
                    wl = EnumChatFormatting.RED + "L";
                }

                mc.fontRendererObj.drawString(target.getName(), this.getX() + 11, this.getY() + 17, Color.WHITE.getRGB());
                RenderUtil.drawRoundedRect(x, y, 150, 50, 5, new Color(0, 0, 0, 90));

                Theme currentTheme = Dog.getInstance().getThemeManager().getCurrentTheme();

                Color themeColor1 = currentTheme.getColor1();
                Color themeColor2 = currentTheme.getColor2();

                RenderUtil.drawRoundedRect(x + 5, y + 30, (int) (105 * (target.getHealth() / target.getMaxHealth())), 6, 6, themeColor2);
                RenderUtil.drawRoundedRect(x + 5, y + 30, (int) (105 * (target.getHealth() / target.getMaxHealth())), 3, 6, themeColor1);
            }
            case "Butt" -> {
                processingEffects.get("Glow").add(() -> RenderUtil.drawRoundedRect(getX(),getY(),getWidth(),getHeight(),10,new Color(20,20,20)));
                processingEffects.get("Blur").add(() -> RenderUtil.drawRoundedRectGl(getX(),getY(),getWidth(),getHeight(),10,new Color(20,20,20,150)));
                RenderUtil.drawRoundedRect(getX(),getY(),getWidth(),getHeight(),10,new Color(20,20,20,150));

                StencilUtil.renderStencil(() -> RenderUtil.drawRoundedRectGl(getX()+10,getY()+9, 32.5f, 32.5f, 5, new Color(-1)),
                        () -> RenderUtil.drawHead(target, getX()+10,getY()+9, 32.5f));

                RenderUtil.drawRoundedRect(getX() + 50,getY() + getHeight() - 20, getWidth() - 60, 10,3,new Color(0,0,0,50));

                RenderUtil.drawRoundedRect(getX() + 50,getY() + getHeight() - 20, (getWidth() - 60) * (target.getHealth() / target.getMaxHealth()), 10,3,new Color(-1));

                StencilUtil.renderStencil(() -> RenderUtil.drawRoundedRect(getX(),getY(),getWidth(),getHeight(),10,new Color(0,0,0)),
                        () -> Fonts.getOpenSansBold(16).drawString(target.getName(), getX() + 50, getY() + 10, -1));

                renderWidth += ((50 + Fonts.getOpenSansBold(16).getWidth(target.getName()) + 10) - renderWidth) * ((float) Minecraft.time / 120);
                renderHeight += ((50) - renderHeight) * ((float) Minecraft.time / 120);
            }
            case "Legit" -> {
                TTFFontRenderer BOLD_18 = Fonts.getOpenSansBold(18);
                changeSize(BOLD_18.getWidth(target.getName()) + 45, 40);

                processingEffects.get("Glow").add(() -> RenderUtil.drawRoundedRect(getX(), getY(), getWidth(), getHeight(), 5, new Color(20, 20, 20)));
                RenderUtil.drawRoundedRect(getX(), getY(), getWidth(), getHeight(), 5, new Color(20, 20, 20));
                RenderUtil.drawRoundedRect(getX() + 35 + 2.5f, getY() + 25, BOLD_18.getWidth(target.getName()), 6, 3, new Color(40, 40, 40));
                BOLD_18.drawString(target.getName(),getX() + 35 + 2.5f, getY() + 9, -1);

                Runnable healthBar = () ->RenderUtil.drawRoundedRect(getX() + 37.5f, getY() + 25, BOLD_18.getWidth(target.getName()) * (target.getHealth() / target.getMaxHealth()), 6, 3,
                        ColorUtil.getColorFromIndex(10, 2, Dog.getInstance().getThemeManager().getCurrentTheme().color1, Dog.getInstance().getThemeManager().getCurrentTheme().color2, true),
                        ColorUtil.getColorFromIndex(10, 2, Dog.getInstance().getThemeManager().getCurrentTheme().color1, Dog.getInstance().getThemeManager().getCurrentTheme().color2, true),
                        ColorUtil.getColorFromIndex(10, 2, Dog.getInstance().getThemeManager().getCurrentTheme().color2, Dog.getInstance().getThemeManager().getCurrentTheme().color1, true),
                        ColorUtil.getColorFromIndex(10, 2, Dog.getInstance().getThemeManager().getCurrentTheme().color2, Dog.getInstance().getThemeManager().getCurrentTheme().color1, true));

                //processingEffects.get("Glow").add(healthBar);
                healthBar.run();

                StencilUtil.renderStencil(() -> RenderUtil.drawRoundedRectGl(getX()+7.5f,getY()+7.5f, 25, 25, 5, new Color(-1)),
                        () -> RenderUtil.drawHead(target, getX()+7.5f, getY()+7.5f, 25));
            }
            case "Diablo" -> {
                //Size
                changeSize(133,47);

                //Background
                RenderUtil.drawRect(getX(), getY(), getWidth(), getHeight(), new Color(45,45,45));
                RenderUtil.drawRect(getX()+1, getY()+1, getWidth()-2, getHeight()-2, new Color(25,25,25));

                //Head
                RenderUtil.drawHead(target, getX()+4,getY()+4, 39);

                //Name
                final String name = target.getName().length() > 14 ? target.getName().substring(0, 13) + "..." : target.getName();
                mc.fontRendererObj.drawStringWithShadow(name,getX() + 43 + 6, getY() + 6, -1);

                //Item - Main
                RenderUtil.drawItemStack(target.getHeldItem(),getX() + 50, getY() + 15);

                //Item - Armor
                float offset = target.getHeldItem() == null ? getX() + 50 : getX() + 49+16;
                for(int i = 0; i<4; i++){
                    ItemStack armor = target.getCurrentArmor(i);

                    if(armor != null) {
                        RenderUtil.drawItemStack(armor, offset, getY() + 15);
                        offset+=16;
                    }
                }

                //Health - Bar
                RenderUtil.drawRect(getX()+49,getY() + getHeight() - 14,getWidth()-49-6,10, new Color(35,35,35));
                RenderUtil.drawRect(getX()+49,getY() + getHeight() - 14,(getWidth()-49-6)*(target.getHealth() /target.getMaxHealth()),10, new Color(212,106,173));

                //Health - Text
                final float health = (float) Math.round(target.getHealth() * 10) / 10;
                mc.fontRendererObj.drawString(health + "♥",getX() + 49 + 40, getY() + getHeight() - 14 + 1, -1);

            }

            case "Exhibition" -> {
                changeSize(140, 50);

                //Fonts
                final TTFFontRenderer exhiFont = Fonts.getOpenSansBold(17);
                final TTFFontRenderer infoFont = Fonts.getRobotoMedium(13);

                //Background
                RenderUtil.drawRect(getX(), getY(), getWidth(), getHeight(), new Color(40, 40, 40));
                RenderUtil.drawRect(getX() + 2, getY() + 2, getWidth() - 4, getHeight() - 4, new Color(25, 25, 25));

                //Player
                GuiInventory.drawEntityOnScreen(getX() + 20,getY() + 43, 20, 100, -75, target);

                //Text
                exhiFont.drawString(target.getName(), getX() + 20 + 20, getY() + 7, -1);
                infoFont.drawString("HP:" + target.getHealth() + " | Dist:" + Math.round(target.getDistanceToEntity(mc.thePlayer)), getX() + 20 + 20, getY() + 27, -1);

                //HealthBar
                RenderUtil.drawRect(getX() + 40, getY() + 20, getWidth() - 40 - 10, 4, new Color(50,50,50));
                RenderUtil.drawRect(getX() + 40, getY() + 20, (getWidth() - 40 - 10) * (target.getHealth() / target.getMaxHealth()), 4, new Color(221,239,22));

                for(int i = 0; i<=9; i++){
                    RenderUtil.drawLine(getX() + 40 + ((getWidth() - 50) / 10) * i, getY() + 20,0,4,1,new Color(0,0,0));
                }


                //Item - Armor
                float offset = getX() + 40;
                for(int i = 0; i<4; i++){
                    ItemStack armor = target.getCurrentArmor(i);

                    if(armor != null) {
                        RenderUtil.drawItemStack(armor, offset, getY() + 32);
                        offset+=16;
                    }
                }


                //Item - Main
                RenderUtil.drawItemStack(target.getHeldItem(),offset, getY() + 32);
            }
        }

        this.setWidth(renderWidth);
        this.setHeight(renderHeight);

    }

    private void changeSize(float width, float height){
        this.renderHeight = height;
        this.renderWidth = width;
    }

    private EntityPlayer setupTarget(){
        if(KillAuraModule.target != null){
            return KillAuraModule.target;
        }

        return mc.pointedEntity instanceof EntityPlayer ? (EntityPlayer) mc.pointedEntity : null;
    }

    private void setupEffects(){
        processingEffects.get("Blur").clear();
        processingEffects.get("Glow").clear();
    }
}

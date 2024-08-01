package wtf.diablo.client.module.impl.render;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import wtf.diablo.client.core.impl.Diablo;
import wtf.diablo.client.gui.draggable.AbstractDraggableElement;
import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.data.ModuleMetaData;
import wtf.diablo.client.module.impl.combat.killaura.KillAuraModule;
import wtf.diablo.client.setting.api.IMode;
import wtf.diablo.client.setting.impl.BooleanSetting;
import wtf.diablo.client.setting.impl.ModeSetting;
import wtf.diablo.client.util.render.RenderUtil;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.Objects;

@ModuleMetaData(
        name = "Target HUD",
        description = "Displays information about the target",
        category = ModuleCategoryEnum.RENDER
)
public final class TargetHUDModule extends AbstractModule {
    private final ModeSetting<TargetHudMode> modeSetting = new ModeSetting<>("Mode", TargetHudMode.DEFAULT);
    private final BooleanSetting blurAndBloomSetting = new BooleanSetting("Blur and Bloom", false);

    private EntityLivingBase target;

    public TargetHUDModule() {
        this.registerSettings(modeSetting, blurAndBloomSetting);
    }

    final AbstractDraggableElement targetHUDElement = new AbstractDraggableElement("Target Hud", 4,4,0,0,this) {

        @Override
        protected void shader() {
            if (blurAndBloomSetting.getValue()) {
                switch (modeSetting.getValue()) {
                    case DEFAULT:
                        this.setWidth(mc.fontRendererObj.getStringWidth(target.getName()) + 110);
                        setHeight(47);
                        RenderUtil.drawRect((int) this.getX(), (int) this.getY(), this.getWidth(), this.getHeight(), new Color(0, 0, 0, 255).getRGB());
                        break;
                }
            }
        }

        @Override
        protected void draw() {
        //    int healthWidth = (int) (target.getHealth() / target.getMaxHealth()) * (getWidth() - 52);
            if (mc.currentScreen instanceof GuiChat) {
                target = mc.thePlayer;
            } else {
                target = (EntityLivingBase) Diablo.getInstance().getModuleRepository().getModuleInstance(KillAuraModule.class).getTarget();
            }
            if (target == null) {
                return;
            }

            final Color backgroundColor = new Color(19, 19, 19, 173);

            final DecimalFormat decimalFormat = new DecimalFormat("#.##");

            final String percentText = decimalFormat.format((target.getHealth() / target.getMaxHealth()) * 100)+ "%";
            final String distanceText = decimalFormat.format(mc.thePlayer.getDistanceToEntity(target)) + " blocks";

            switch (modeSetting.getValue()) {
                case DEFAULT:
                    this.setWidth(mc.fontRendererObj.getStringWidth(target.getName()) + 110);
                    setHeight(47);
                    if (Diablo.getInstance().getModuleRepository().getModuleInstance(EffectsModule.class).isEnabled() && blurAndBloomSetting.getValue()) {
                        RenderUtil.drawRect(0, 0, this.getWidth(), this.getHeight(), new Color(0, 0, 0, 0).getRGB());
                    } else {
                        RenderUtil.drawRect(0, 0, this.getWidth(), this.getHeight(), 0x901c1c1c);
                    }
                    if (mc.getNetHandler() != null && target.getUniqueID() != null) {
                        NetworkPlayerInfo i = mc.getNetHandler().getPlayerInfo(target.getUniqueID());
                        if (i != null) {
                            mc.getTextureManager().bindTexture(i.getLocationSkin());
                            GlStateManager.color(1, 1, 1);
                            Gui.drawModalRectWithCustomSizedTexture(4, 4, (float) (39), (float) (39), 39, 39, (float) 312, (float) 312);
                        }
                    }

                    mc.fontRendererObj.drawStringWithShadow(target.getName(), 49, 5, -1);

                    RenderUtil.drawRect(49, 33, getWidth() - 55, this.getHeight() - 37, 0xFF292929);
                    RenderUtil.drawRect(49, 33, (int) ((target.getHealth() / target.getMaxHealth()) * (getWidth() - 55)), this.getHeight() - 37, ColorModule.getColor(0));

                    final String text = Math.round(target.getHealth()) + "♥";
                    mc.fontRendererObj.drawString(text, (int) (49 + (97 / 2f) - (mc.fontRendererObj.getStringWidth(text) / 2f)), (int) (29 + (10 / 2f)), -1);

                    EntityPlayer player = (EntityPlayer) target;

                    for (int length = player.inventory.armorInventory.length, i = 0; i < length; ++i) {
                        ItemStack armourStack = mc.thePlayer.inventory.armorInventory[i];
                        if (Objects.nonNull(armourStack)) {
                            RenderUtil.renderItemStack(player.inventory.armorItemInSlot(i), 49 + 80 - (i * 20), 15);
                        }
                    }

                    if (Objects.nonNull(mc.thePlayer.getHeldItem())) {
                        RenderUtil.renderItemStack(mc.thePlayer.getHeldItem(), 49, 15);
                    }

                    RenderUtil.drawOutlineRect(0, 0, this.getWidth() - 1, this.getHeight() - 1, 1, 0xff313131);
                    return;
                case OLD_DIABLO:
                    this.setWidth(mc.fontRendererObj.getStringWidth(target.getName()) + 110);
                    setHeight(47);

                    RenderUtil.drawRect(0, 0, this.getWidth(), this.getHeight(), backgroundColor.getRGB());
                    if (mc.getNetHandler() != null && target.getUniqueID() != null)     {
                        NetworkPlayerInfo i = mc.getNetHandler().getPlayerInfo(target.getUniqueID());
                        if (i != null) {
                            mc.getTextureManager().bindTexture(i.getLocationSkin());
                            GlStateManager.color(1, 1, 1);
                            Gui.drawModalRectWithCustomSizedTexture(4, 4, (float) (39), (float) (39), 39, 39, (float) 312, (float) 312);
                        }
                    }

                    mc.fontRendererObj.drawStringWithShadow(target.getName(), 49, 5, -1);

                    RenderUtil.drawRect(49, 33, getWidth() - 55, this.getHeight() - 37, 0xFF292929);
                    RenderUtil.drawRect(49, 33, (int) ((target.getHealth() / target.getMaxHealth()) * (getWidth() - 55)), this.getHeight() - 37, ColorModule.getColor(0));

                    mc.fontRendererObj.drawString(distanceText, 49,  (int) (14 + (10 / 2f)), Color.LIGHT_GRAY.getRGB());

                    mc.fontRendererObj.drawString(percentText, (int) (49 + (97 / 2f) - (mc.fontRendererObj.getStringWidth(percentText) / 2f)), (int) (29 + (10 / 2f)), -1);
                    return;
                case COMPACT:
                    this.setWidth(mc.fontRendererObj.getStringWidth(target.getName()) + 96);

                    setHeight(33);

                    RenderUtil.drawRect(0, 0, this.getWidth(), this.getHeight(), backgroundColor.getRGB());
                    if (mc.getNetHandler() != null && target.getUniqueID() != null) {
                        NetworkPlayerInfo i = mc.getNetHandler().getPlayerInfo(target.getUniqueID());
                        if (i != null) {
                            mc.getTextureManager().bindTexture(i.getLocationSkin());
                            GlStateManager.color(1, 1, 1);
                            Gui.drawModalRectWithCustomSizedTexture(4, 4, (float) (25), (float) (25), 25, 25, (float) 200, (float) 200);
                        }
                    }

                    mc.fontRendererObj.drawStringWithShadow(target.getName(), 35, 5, -1);

                    RenderUtil.drawRect(35, 18, getWidth() - 40, this.getHeight() - 23, 0xFF292929);
                    RenderUtil.drawRect(35, 18, (int) ((target.getHealth() / target.getMaxHealth()) * (getWidth() - 40)), this.getHeight() - 23, ColorModule.getColor(0));

                    mc.fontRendererObj.drawString(percentText, (int) (35 + (97 / 2f) - (mc.fontRendererObj.getStringWidth(percentText) / 2f)), (int) (14.5 + (10 / 2f)), -1);
                    return;
            }
        }
    };

    enum TargetHudMode implements IMode {
        DEFAULT("Default"),
        OLD_DIABLO("Old Diablo"),
        COMPACT("Compact");

        private final String name;

        TargetHudMode(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }
}

package club.bluezenith.module.modules.render;

import club.bluezenith.events.Listener;
import club.bluezenith.events.impl.Render2DEvent;
import club.bluezenith.events.impl.RenderNameTagEvent;
import club.bluezenith.events.impl.UpdatePlayerEvent;
import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.module.modules.render.hud.HUD;
import club.bluezenith.module.value.types.BooleanValue;
import club.bluezenith.module.value.types.ColorValue;
import club.bluezenith.module.value.types.ListValue;
import club.bluezenith.module.value.types.ModeValue;
import club.bluezenith.util.render.ColorUtil;
import club.bluezenith.util.render.RenderUtil;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.awt.*;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

import static club.bluezenith.BlueZenith.getBlueZenith;
import static club.bluezenith.module.value.builders.AbstractBuilder.*;
import static club.bluezenith.util.render.RenderUtil.*;
import static java.awt.Color.HSBtoRGB;
import static java.lang.Math.min;

public class PlayerESP extends Module {
    private final ModeValue boxMode = createMode("Box")
            .index(-2)
            .range("Normal, Corner")
            .build();

    private final ModeValue colorMode = createMode("Color")
            .index(-1)
            .range("Match HUD, Custom")
            .build();

    private final ColorValue customColor = createColor("Color")
            .index(1)
            .showIf(() -> colorMode.is("Custom"))
            .build()
            .setID("esp-custom-color");

    private final ListValue addons = createList("Addons")
            .index(3)
            .range("Bordered, Health, Name, Armor, Held item")
            .build();

    private final ModeValue nameColor = createMode("Name color")
            .index(2)
            .range("Teams, Basic")
            .showIf(() -> addons.getOptionState("Name"))
            .build();

    private final BooleanValue hudFont = createBoolean("Use HUD font")
            .index(2)
            .showIf(() -> addons.getOptionState("Name") || addons.getOptionState("Held item"))
            .build();


    public PlayerESP() {
        super("PlayerESP", ModuleCategory.RENDER);
        addons.setOptionState("Bordered", true);
    }

    private final Frustum frustum = new Frustum();

    private final List<EntityLivingBase> entitiesToESP = new ArrayList<>();

    private final IntBuffer viewportBuffer = GLAllocation.createDirectIntBuffer(16);
    private final FloatBuffer modelViewBuffer = GLAllocation.createDirectFloatBuffer(16);
    private final FloatBuffer projectionBuffer = GLAllocation.createDirectFloatBuffer(16);
    private final FloatBuffer windowPositionBuffer = GLAllocation.createDirectFloatBuffer(4);

    private EntityLivingBase currentEntity;

    private float x1, y1, x2, y2;

    private float healthBarProgress;

    private final Function<Integer, Integer> colorSupplier = (index) -> {
        if(colorMode.is("Match HUD")) {
            if (HUD.module.colorMode.is("Astolfo"))
                return HUD.module.getColor(index * 3);
            else return HUD.module.getColor(index);
        } else return customColor.getRGB();
    };

    @Listener
    public void onRenderNameTag(RenderNameTagEvent event) {
        if(addons.getOptionState("Name") && entitiesToESP.contains(event.getEntity()))
            event.cancel();
    }

    @Listener
    public void onRender2D(Render2DEvent event) {
        if(entitiesToESP.isEmpty()) return;

        final EntityRenderer entityRenderer = mc.entityRenderer;

        for (EntityLivingBase entity : entitiesToESP) {
            this.currentEntity = entity;

            entityRenderer.setupCameraTransform(event.partialTicks, 0);

            final double[] pos = projectBoundingBox(
                    entity,
                    entity.getEntityBoundingBox(),
                    event.resolution.getScaleFactor(),
                    event.partialTicks,
                    modelViewBuffer,
                    projectionBuffer,
                    viewportBuffer,
                    windowPositionBuffer
            );

            x1 = (float) pos[0];
            y1 = (float) pos[1];
            x2 = (float) pos[2];
            y2 = (float) pos[3];

            entityRenderer.setupOverlayRendering();

            if(boxMode.is("Normal"))
                drawNormalBox();
            else drawCorneredBox();

            float y = y2 + 3;

            if(addons.getOptionState("Name"))
                drawName(y1 - 7);

            if(addons.getOptionState("Held item"))
                drawHeldItem(y);

            if(addons.getOptionState("Health"))
                drawHealth();

            if(addons.getOptionState("Armor") && currentEntity instanceof EntityPlayer)
                drawArmor();
        }
    }

    private void drawNormalBox() {
        rect(x1, y1, x2, colorSupplier, y2);
    }

    private void drawCorneredBox() {
        final float xDivisor = 2.7F, yDivisor = 4F;

        final float width = (x2 - x1) / xDivisor,
                    height = (y2 - y1) / yDivisor;

        //top left corner
        rect(x1, y1, x1 + width, colorSupplier, y1);
        rect(x1, y1, x1, colorSupplier, y1 + height);

        //top right corner
        rect(x2, y1, x2, (index) -> colorSupplier.apply(index * 2), y1 + height);
        rect(x2 - width, y1, x2, (index) -> colorSupplier.apply(index * 2), y1);

        //bottom left corner
        rect(x1, y2 - height, x1, colorSupplier, y2);
        rect(x1, y2, x1 + width, colorSupplier, y2);

        //bottom right corner
        rect(x2 - (x2 - x1) / xDivisor, y2, x2, (index) -> colorSupplier.apply(index * 2), y2);
        rect(x2, y2 - (y2 - y1) / yDivisor, x2, (index) -> colorSupplier.apply(index * 2), y2);
    }

    private void drawHealth() {
        final float width = 1F, spacing = 2F;

        final float xStart = x1 - width - spacing,
              xEnd = x1 - spacing;

        final AtomicReference<Float> maxAbsorption = new AtomicReference<>(0.0F),
                                     maxHealth = new AtomicReference<>(20.0F);

        this.currentEntity.getActivePotionEffects()
                .stream()
                .filter(effect -> effect.getEffectName().equals("potion.absorption") || effect.getEffectName().equals("potion.healthBoost"))
                .forEach(effect -> {
                    if(effect.getEffectName().endsWith("absorption"))
                      maxAbsorption.set( (effect.getAmplifier() + 1F) * 4F );
                    else maxHealth.set(20 + (effect.getAmplifier() + 1F) * 4F);
                });

        if(maxAbsorption.get() != this.currentEntity.getAbsorptionAmount())
            maxAbsorption.set(this.currentEntity.getAbsorptionAmount());

        maxHealth.set(maxHealth.get() + maxAbsorption.get());

        final float currentHealth = this.currentEntity.getHealth() + this.currentEntity.getAbsorptionAmount(),
                targetProgress = currentHealth / maxHealth.get();

        healthBarProgress = animate(targetProgress, healthBarProgress, 0.12F);

        RenderUtil.hollowRect(xStart, y2 - (y2 - y1) * healthBarProgress, xEnd, y2, 2, Color.black.getRGB());
        RenderUtil.rect(xStart, y2 - (y2 - y1) * healthBarProgress, xEnd, y2, this.getColorForHealth(maxHealth.get(), currentHealth));
    }

    private void drawArmor() {
        final float spacing = 2F;
        float y = this.y1 - 1;

        final EntityPlayer player = (EntityPlayer) this.currentEntity;

        final ItemStack[] armor = new ItemStack[4];

        for (int i = 0; i < 4; i++) {
            armor[3 - i] = player.inventory.armorInventory[i];
        }

        for (ItemStack itemStack : armor) {
            if(itemStack == null) {
                y += (y2 - y1) / 3.8F;
                continue;
            }

            final float scale = 0.6F;

            GlStateManager.scale(scale, scale, 1);
            mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack, (int) ((x2 + spacing) / scale), (int) (y / scale));
            GlStateManager.scale(1 / scale, 1 / scale, 1);

            y += (y2 - y1) / 3.8F;
        }
    }

    private static final int TARGET_COLOR = new Color(225, 44, 44).getRGB(),
                             FRIEND_COLOR = new Color(53, 229, 53).getRGB();

    private float drawName(float y) {
        final FontRenderer font = hudFont.get() ? HUD.module.font.get() : mc.fontRendererObj;

        final String name = currentEntity instanceof EntityPlayer ?
                ((EntityPlayer) currentEntity).getGameProfile().getName()
                : currentEntity.getDisplayName().getUnformattedText();

        final float scale = 0.5F;

        final float x = x1 + (x2 - x1)/2F - font.getStringWidthF(name) / (2F / scale);

        int color = -1;

        String colorCode = ColorUtil.getFirstColor(currentEntity.getDisplayName().getUnformattedText());

        if(getBlueZenith().getTargetManager().isTarget(name))
            color = TARGET_COLOR;
        else if(getBlueZenith().getFriendManager().isFriend(name))
            color = FRIEND_COLOR;

        if(color != -1)
            colorCode = "";
        else if(!nameColor.is("Teams"))
            colorCode = "§r";

        RenderUtil.drawScaledFont(font, colorCode + name, x, y, color, true, scale);

        return y + font.FONT_HEIGHT / 1.6F;
    }

    private float drawHeldItem(float y) {
        final FontRenderer font = hudFont.get() ? HUD.module.font.get() : mc.fontRendererObj;

        final ItemStack heldItem = currentEntity.getHeldItem();

        if(heldItem != null) {
            final String name = "§7" + heldItem.getDisplayName() + (heldItem.stackSize > 1 ? " §7(" + heldItem.stackSize + "x)" : "");

            final float scale = 0.5F;

            final float x = x1 + (x2 - x1) / 2F - font.getStringWidthF(name) / (2F / scale);

            RenderUtil.drawScaledFont(font, name, x, y, -1, true, scale);

            return y = font.FONT_HEIGHT / 1.6F;
        }

        return y;
    }

    private void rect(float x1, float y1, float x2, Function<Integer, Integer> color, float y2) {
        if(addons.getOptionState("Bordered"))
          hollowRect(x1, y1, x2, y2, 3.5F, Color.black.getRGB());
        hollowRectWithGradient(x1, y1, x2, y2, 1.2F, color);
    }

    @Listener
    public void onUpdate(UpdatePlayerEvent event) {
        if(event.isPre()) return;

        entitiesToESP.clear();

        for (Entity entity : mc.theWorld.loadedEntityList) {
            if(entity == mc.thePlayer && mc.gameSettings.thirdPersonView == 0) continue;
            if(!(entity instanceof EntityPlayer)) continue;
            if(entity.isInvisible() || !entity.isEntityAlive()) continue;

            final Entity renderViewEntity = mc.getRenderViewEntity();
            frustum.setPosition(renderViewEntity.posX, renderViewEntity.posY, renderViewEntity.posZ);

            if(!frustum.isBoundingBoxInFrustum(entity.getEntityBoundingBox())) continue;

            entitiesToESP.add((EntityLivingBase) entity);
        }
    }

    private int getColorForHealth(float maxHealth, float currentHealth) {
        float diff = min(currentHealth, maxHealth) / maxHealth;
        return HSBtoRGB(diff / 3F, (float) 0.7, (float) 1.0);
    }

    @Override
    public void onEnable() {
        entitiesToESP.clear();
    }

    @Override
    public void onDisable() {
        entitiesToESP.clear();
    }
}

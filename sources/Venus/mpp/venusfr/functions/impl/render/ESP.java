/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.render;

import com.google.common.eventbus.Subscribe;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import mpp.venusfr.command.friends.FriendStorage;
import mpp.venusfr.events.EventDisplay;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import mpp.venusfr.functions.impl.combat.AntiBot;
import mpp.venusfr.functions.impl.render.HUD;
import mpp.venusfr.functions.settings.impl.BooleanSetting;
import mpp.venusfr.functions.settings.impl.ColorSetting;
import mpp.venusfr.functions.settings.impl.ModeListSetting;
import mpp.venusfr.utils.math.MathUtil;
import mpp.venusfr.utils.math.Vector4i;
import mpp.venusfr.utils.projections.ProjectionUtil;
import mpp.venusfr.utils.render.ColorUtils;
import mpp.venusfr.utils.render.DisplayUtils;
import mpp.venusfr.utils.render.font.Fonts;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.PointOfView;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectUtils;
import net.minecraft.scoreboard.Score;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector4f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import org.lwjgl.opengl.GL11;

@FunctionRegister(name="ESP", type=Category.Visual)
public class ESP
extends Function {
    public ModeListSetting remove = new ModeListSetting("\u0423\u0431\u0440\u0430\u0442\u044c", new BooleanSetting("\u0411\u043e\u043a\u0441\u044b", false), new BooleanSetting("\u041f\u043e\u043b\u043e\u0441\u043a\u0443 \u0445\u043f", false), new BooleanSetting("\u0422\u0435\u043a\u0441\u0442 \u0445\u043f", false), new BooleanSetting("\u0417\u0430\u0447\u0430\u0440\u043e\u0432\u0430\u043d\u0438\u044f", false), new BooleanSetting("\u0421\u043f\u0438\u0441\u043e\u043a \u044d\u0444\u0444\u0435\u043a\u0442\u043e\u0432", false));
    private final HashMap<Entity, Vector4f> positions = new HashMap();
    public ColorSetting color = new ColorSetting("Color", -1);

    public ESP() {
        this.toggle();
        this.addSettings(this.remove);
    }

    @Subscribe
    public void onDisplay(EventDisplay eventDisplay) {
        LivingEntity livingEntity;
        if (ESP.mc.world == null || eventDisplay.getType() != EventDisplay.Type.PRE) {
            return;
        }
        this.positions.clear();
        Vector4i vector4i = new Vector4i(HUD.getColor(0, 1.0f), HUD.getColor(90, 1.0f), HUD.getColor(180, 1.0f), HUD.getColor(270, 1.0f));
        Vector4i vector4i2 = new Vector4i(HUD.getColor(ColorUtils.rgb(144, 238, 144), ColorUtils.rgb(0, 139, 0), 0, 1.0f), HUD.getColor(ColorUtils.rgb(144, 238, 144), ColorUtils.rgb(0, 139, 0), 90, 1.0f), HUD.getColor(ColorUtils.rgb(144, 238, 144), ColorUtils.rgb(0, 139, 0), 180, 1.0f), HUD.getColor(ColorUtils.rgb(144, 238, 144), ColorUtils.rgb(0, 139, 0), 270, 1.0f));
        for (Entity object : ESP.mc.world.getAllEntities()) {
            if (!this.isValid(object) || !(object instanceof PlayerEntity) && !(object instanceof ItemEntity) || object == ESP.mc.player && ESP.mc.gameSettings.getPointOfView() == PointOfView.FIRST_PERSON) continue;
            double d = MathUtil.interpolate(object.getPosX(), object.lastTickPosX, (double)eventDisplay.getPartialTicks());
            double d2 = MathUtil.interpolate(object.getPosY(), object.lastTickPosY, (double)eventDisplay.getPartialTicks());
            double d3 = MathUtil.interpolate(object.getPosZ(), object.lastTickPosZ, (double)eventDisplay.getPartialTicks());
            Vector3d vector3d = new Vector3d(object.getBoundingBox().maxX - object.getBoundingBox().minX, object.getBoundingBox().maxY - object.getBoundingBox().minY, object.getBoundingBox().maxZ - object.getBoundingBox().minZ);
            AxisAlignedBB axisAlignedBB = new AxisAlignedBB(d - vector3d.x / 2.0, d2, d3 - vector3d.z / 2.0, d + vector3d.x / 2.0, d2 + vector3d.y, d3 + vector3d.z / 2.0);
            Vector4f vector4f = null;
            for (int i = 0; i < 8; ++i) {
                Vector2f vector2f = ProjectionUtil.project(i % 2 == 0 ? axisAlignedBB.minX : axisAlignedBB.maxX, i / 2 % 2 == 0 ? axisAlignedBB.minY : axisAlignedBB.maxY, i / 4 % 2 == 0 ? axisAlignedBB.minZ : axisAlignedBB.maxZ);
                if (vector4f == null) {
                    vector4f = new Vector4f(vector2f.x, vector2f.y, 1.0f, 1.0f);
                    continue;
                }
                vector4f.x = Math.min(vector2f.x, vector4f.x);
                vector4f.y = Math.min(vector2f.y, vector4f.y);
                vector4f.z = Math.max(vector2f.x, vector4f.z);
                vector4f.w = Math.max(vector2f.y, vector4f.w);
            }
            this.positions.put(object, vector4f);
        }
        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
        RenderSystem.shadeModel(7425);
        buffer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        for (Map.Entry entry : this.positions.entrySet()) {
            Vector4f vector4f = (Vector4f)entry.getValue();
            Object k = entry.getKey();
            if (!(k instanceof LivingEntity)) continue;
            livingEntity = (LivingEntity)k;
            if (!((Boolean)this.remove.getValueByName("\u0411\u043e\u043a\u0441\u044b").get()).booleanValue()) {
                DisplayUtils.drawBox(vector4f.x - 0.5f, vector4f.y - 0.5f, vector4f.z + 0.5f, vector4f.w + 0.5f, 2.0, ColorUtils.rgba(0, 0, 0, 128));
                DisplayUtils.drawBoxTest(vector4f.x, vector4f.y, vector4f.z, vector4f.w, 1.0, FriendStorage.isFriend(livingEntity.getName().getString()) ? vector4i2 : vector4i);
            }
            float f = 3.0f;
            float f2 = 0.5f;
            if (((Boolean)this.remove.getValueByName("\u041f\u043e\u043b\u043e\u0441\u043a\u0443 \u0445\u043f").get()).booleanValue()) continue;
            String string = ESP.mc.ingameGUI.getTabList().header == null ? " " : ESP.mc.ingameGUI.getTabList().header.getString().toLowerCase();
            DisplayUtils.drawRectBuilding(vector4f.x - f - f2, vector4f.y - f2, vector4f.x - f + 1.0f + f2, vector4f.w + f2, ColorUtils.rgba(0, 0, 0, 128));
            DisplayUtils.drawRectBuilding(vector4f.x - f, vector4f.y, vector4f.x - f + 1.0f, vector4f.w, ColorUtils.rgba(0, 0, 0, 128));
            Score score = ESP.mc.world.getScoreboard().getOrCreateScore(livingEntity.getScoreboardName(), ESP.mc.world.getScoreboard().getObjectiveInDisplaySlot(2));
            float f3 = livingEntity.getHealth();
            float f4 = livingEntity.getMaxHealth();
            if (mc.getCurrentServerData() != null && ESP.mc.getCurrentServerData().serverIP.contains("funtime") && (string.contains("\u0430\u043d\u0430\u0440\u0445\u0438\u044f") || string.contains("\u0433\u0440\u0438\u0444\u0435\u0440\u0441\u043a\u0438\u0439"))) {
                f3 = score.getScorePoints();
                f4 = 20.0f;
            }
            DisplayUtils.drawMCVerticalBuilding(vector4f.x - f, vector4f.y + (vector4f.w - vector4f.y) * (1.0f - MathHelper.clamp(f3 / f4, 0.0f, 1.0f)), vector4f.x - f + 1.0f, vector4f.w, FriendStorage.isFriend(livingEntity.getName().getString()) ? vector4i2.w : vector4i.w, FriendStorage.isFriend(livingEntity.getName().getString()) ? vector4i2.x : vector4i.x);
        }
        Tessellator.getInstance().draw();
        RenderSystem.shadeModel(7424);
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
        for (Map.Entry entry : this.positions.entrySet()) {
            float f;
            float f5;
            Object object;
            Entity entity2 = (Entity)entry.getKey();
            if (entity2 instanceof LivingEntity) {
                String string;
                livingEntity = (LivingEntity)entity2;
                object = ESP.mc.world.getScoreboard().getOrCreateScore(livingEntity.getScoreboardName(), ESP.mc.world.getScoreboard().getObjectiveInDisplaySlot(2));
                f5 = livingEntity.getHealth();
                f = livingEntity.getMaxHealth();
                String string2 = string = ESP.mc.ingameGUI.getTabList().header == null ? " " : ESP.mc.ingameGUI.getTabList().header.getString().toLowerCase();
                if (mc.getCurrentServerData() != null && ESP.mc.getCurrentServerData().serverIP.contains("funtime") && (string.contains("\u0430\u043d\u0430\u0440\u0445\u0438\u044f") || string.contains("\u0433\u0440\u0438\u0444\u0435\u0440\u0441\u043a\u0438\u0439"))) {
                    f5 = ((Score)object).getScorePoints();
                    f = 20.0f;
                }
                Vector4f vector4f = (Vector4f)entry.getValue();
                float f6 = vector4f.z - vector4f.x;
                String string3 = (int)f5 + "HP";
                float f7 = Fonts.consolas.getWidth(string3, 6.0f);
                float f8 = MathHelper.clamp(f5 / f, 0.0f, 1.0f);
                float f9 = vector4f.y + (vector4f.w - vector4f.y) * (1.0f - f8);
                if (!((Boolean)this.remove.getValueByName("\u0422\u0435\u043a\u0441\u0442 \u0445\u043f").get()).booleanValue()) {
                    Fonts.consolas.drawText(eventDisplay.getMatrixStack(), string3, vector4f.x - f7 - 6.0f, f9, -1, 6.0f, 0.05f);
                }
                float f10 = ESP.mc.fontRenderer.getStringPropertyWidth(entity2.getDisplayName());
                GL11.glPushMatrix();
                this.glCenteredScale(vector4f.x + f6 / 2.0f - f10 / 2.0f, vector4f.y - 7.0f, f10, 10.0f, 0.5f);
                Object object2 = FriendStorage.isFriend(entity2.getName().getString()) ? TextFormatting.GREEN + "[F] " : "";
                TextComponent textComponent = (TextComponent)ITextComponent.getTextComponentOrEmpty((String)object2);
                textComponent.append(entity2.getDisplayName());
                ESP.mc.fontRenderer.func_243246_a(eventDisplay.getMatrixStack(), textComponent, vector4f.x + f6 / 2.0f - f10 / 2.0f, vector4f.y - 7.0f, -1);
                GL11.glPopMatrix();
                if (!((Boolean)this.remove.getValueByName("\u0421\u043f\u0438\u0441\u043e\u043a \u044d\u0444\u0444\u0435\u043a\u0442\u043e\u0432").get()).booleanValue()) {
                    this.drawPotions(eventDisplay.getMatrixStack(), livingEntity, vector4f.z + 2.0f, vector4f.y);
                }
                this.drawItems(eventDisplay.getMatrixStack(), livingEntity, (int)(vector4f.x + f6 / 2.0f), (int)(vector4f.y - 20.0f));
                continue;
            }
            if (!(entity2 instanceof ItemEntity)) continue;
            ItemEntity itemEntity = (ItemEntity)entity2;
            object = (Vector4f)entry.getValue();
            f5 = ((Vector4f)object).z - ((Vector4f)object).x;
            f = ESP.mc.fontRenderer.getStringPropertyWidth(entity2.getDisplayName());
            GL11.glPushMatrix();
            this.glCenteredScale(((Vector4f)object).x + f5 / 2.0f - f / 2.0f, ((Vector4f)object).y - 7.0f, f, 10.0f, 0.5f);
            ESP.mc.fontRenderer.func_243246_a(eventDisplay.getMatrixStack(), entity2.getDisplayName(), ((Vector4f)object).x + f5 / 2.0f - f / 2.0f, ((Vector4f)object).y - 7.0f, -1);
            GL11.glPopMatrix();
        }
    }

    public boolean isInView(Entity entity2) {
        if (mc.getRenderViewEntity() == null) {
            return true;
        }
        WorldRenderer.frustum.setCameraPosition(ESP.mc.getRenderManager().info.getProjectedView().x, ESP.mc.getRenderManager().info.getProjectedView().y, ESP.mc.getRenderManager().info.getProjectedView().z);
        return WorldRenderer.frustum.isBoundingBoxInFrustum(entity2.getBoundingBox()) || entity2.ignoreFrustumCheck;
    }

    private void drawPotions(MatrixStack matrixStack, LivingEntity livingEntity, float f, float f2) {
        for (EffectInstance effectInstance : livingEntity.getActivePotionEffects()) {
            int n = effectInstance.getAmplifier();
            Object object = "";
            if (n >= 1 && n <= 9) {
                object = " " + I18n.format("enchantment.level." + (n + 1), new Object[0]);
            }
            String string = I18n.format(effectInstance.getEffectName(), new Object[0]) + (String)object + " - " + EffectUtils.getPotionDurationString(effectInstance, 1.0f);
            Fonts.consolas.drawText(matrixStack, string, f, f2, -1, 6.0f, 0.05f);
            f2 += Fonts.consolas.getHeight(6.0f);
        }
    }

    private void drawItems(MatrixStack matrixStack, LivingEntity livingEntity, int n, int n2) {
        int n3 = 8;
        int n4 = 6;
        float f = Fonts.consolas.getHeight(6.0f);
        ArrayList<Object> arrayList = new ArrayList<Object>();
        ItemStack itemStack = livingEntity.getHeldItemMainhand();
        if (!itemStack.isEmpty()) {
            arrayList.add(itemStack);
        }
        for (ItemStack object : livingEntity.getArmorInventoryList()) {
            if (object.isEmpty()) continue;
            arrayList.add(object);
        }
        ItemStack itemStack2 = livingEntity.getHeldItemOffhand();
        if (!itemStack2.isEmpty()) {
            arrayList.add(itemStack2);
        }
        n = (int)((float)n - (float)(arrayList.size() * (n3 + n4)) / 2.0f);
        for (ItemStack itemStack3 : arrayList) {
            if (itemStack3.isEmpty()) continue;
            GL11.glPushMatrix();
            this.glCenteredScale(n, n2, (float)n3 / 2.0f, (float)n3 / 2.0f, 0.5f);
            mc.getItemRenderer().renderItemAndEffectIntoGUI(itemStack3, n, n2);
            mc.getItemRenderer().renderItemOverlayIntoGUI(ESP.mc.fontRenderer, itemStack3, n, n2, null);
            GL11.glPopMatrix();
            if (itemStack3.isEnchanted() && !((Boolean)this.remove.getValueByName("\u0417\u0430\u0447\u0430\u0440\u043e\u0432\u0430\u043d\u0438\u044f").get()).booleanValue()) {
                int n5 = (int)((float)n2 - f);
                Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(itemStack3);
                for (Enchantment enchantment : map.keySet()) {
                    int n6 = map.get(enchantment);
                    if (n6 < 1 || !enchantment.canApply(itemStack3)) continue;
                    TranslationTextComponent translationTextComponent = new TranslationTextComponent(enchantment.getName());
                    String string = translationTextComponent.getString().substring(0, 2) + n6;
                    Fonts.consolas.drawText(matrixStack, string, n, n5, -1, 6.0f, 0.05f);
                    n5 -= (int)f;
                }
            }
            n += n3 + n4;
        }
    }

    public boolean isValid(Entity entity2) {
        if (AntiBot.isBot(entity2)) {
            return true;
        }
        return this.isInView(entity2);
    }

    public void glCenteredScale(float f, float f2, float f3, float f4, float f5) {
        GL11.glTranslatef(f + f3 / 2.0f, f2 + f4 / 2.0f, 0.0f);
        GL11.glScalef(f5, f5, 1.0f);
        GL11.glTranslatef(-f - f3 / 2.0f, -f2 - f4 / 2.0f, 0.0f);
    }
}


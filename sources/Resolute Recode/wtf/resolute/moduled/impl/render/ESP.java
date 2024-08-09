package wtf.resolute.moduled.impl.render;

import com.google.common.eventbus.Subscribe;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import wtf.resolute.manage.friends.FriendStorage;
import wtf.resolute.evented.EventDisplay;
import wtf.resolute.moduled.impl.render.HUD.HUD;
import wtf.resolute.utiled.render.RectUtils;
import wtf.resolute.moduled.Categories;
import wtf.resolute.moduled.Module;
import wtf.resolute.moduled.ModuleAnontion;
import wtf.resolute.moduled.impl.combat.AntiBot;
import wtf.resolute.moduled.settings.impl.BooleanSetting;
import wtf.resolute.moduled.settings.impl.ColorSetting;
import wtf.resolute.moduled.settings.impl.ModeListSetting;
import wtf.resolute.utiled.math.MathUtil;
import wtf.resolute.utiled.math.Vector4i;
import wtf.resolute.utiled.projections.ProjectionUtil;
import wtf.resolute.utiled.render.ColorUtils;
import wtf.resolute.utiled.render.DisplayUtils;
import wtf.resolute.utiled.render.font.Fonts;
import net.minecraft.client.renderer.Tessellator;
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
import net.minecraft.util.text.*;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.minecraft.client.renderer.WorldRenderer.frustum;
import static org.lwjgl.opengl.GL11.glScalef;
import static org.lwjgl.opengl.GL11.glTranslatef;

@ModuleAnontion(name = "ESP", type = Categories.Render,server = "")
public class ESP extends Module {
    public ModeListSetting remove = new ModeListSetting("Убрать", new BooleanSetting("Боксы", false), new BooleanSetting("Полоску хп", false), new BooleanSetting("Текст хп", false), new BooleanSetting("Зачарования", false), new BooleanSetting("Список эффектов", false));


    public ESP() {
        toggle();
        addSettings(remove);
    }

    private final HashMap<Entity, Vector4f> positions = new HashMap<>();

    public ColorSetting color = new ColorSetting("Color", -1);

    @Subscribe
    public void onDisplay(EventDisplay e) {
        if (mc.world == null || e.getType() != EventDisplay.Type.PRE) {
            return;
        }

        positions.clear();

        Vector4i colors = new Vector4i(HUD.getColor(0, 1), HUD.getColor(90, 1), HUD.getColor(180, 1), HUD.getColor(270, 1));
        Vector4i friendColors = new Vector4i(HUD.getColor(ColorUtils.rgb(144, 238, 144), ColorUtils.rgb(0, 139, 0), 0, 1), HUD.getColor(ColorUtils.rgb(144, 238, 144), ColorUtils.rgb(0, 139, 0), 90, 1), HUD.getColor(ColorUtils.rgb(144, 238, 144), ColorUtils.rgb(0, 139, 0), 180, 1), HUD.getColor(ColorUtils.rgb(144, 238, 144), ColorUtils.rgb(0, 139, 0), 270, 1));


        for (Entity entity : mc.world.getAllEntities()) {
            if (!isValid(entity)) continue;
            if (!(entity instanceof PlayerEntity || entity instanceof ItemEntity)) continue;
            if (entity == mc.player && (mc.gameSettings.getPointOfView() == PointOfView.FIRST_PERSON)) continue;

            double x = MathUtil.interpolate(entity.getPosX(), entity.lastTickPosX, e.getPartialTicks());
            double y = MathUtil.interpolate(entity.getPosY(), entity.lastTickPosY, e.getPartialTicks());
            double z = MathUtil.interpolate(entity.getPosZ(), entity.lastTickPosZ, e.getPartialTicks());

            Vector3d size = new Vector3d(entity.getBoundingBox().maxX - entity.getBoundingBox().minX, entity.getBoundingBox().maxY - entity.getBoundingBox().minY, entity.getBoundingBox().maxZ - entity.getBoundingBox().minZ);

            AxisAlignedBB aabb = new AxisAlignedBB(x - size.x / 2f, y, z - size.z / 2f, x + size.x / 2f, y + size.y, z + size.z / 2f);

            Vector4f position = null;

            for (int i = 0; i < 8; i++) {
                Vector2f vector = ProjectionUtil.project(i % 2 == 0 ? aabb.minX : aabb.maxX, (i / 2) % 2 == 0 ? aabb.minY : aabb.maxY, (i / 4) % 2 == 0 ? aabb.minZ : aabb.maxZ);

                if (position == null) {
                    position = new Vector4f(vector.x, vector.y, 1, 1.0f);
                } else {
                    position.x = Math.min(vector.x, position.x);
                    position.y = Math.min(vector.y, position.y);
                    position.z = Math.max(vector.x, position.z);
                    position.w = Math.max(vector.y, position.w);
                }
            }

            positions.put(entity, position);
        }


        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
        RenderSystem.shadeModel(7425);

        buffer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        for (Map.Entry<Entity, Vector4f> entry : positions.entrySet()) {
            Vector4f position = entry.getValue();
            if (entry.getKey() instanceof LivingEntity entity) {

                MatrixStack ms = e.getMatrixStack();
                if (!remove.getValueByName("Боксы").get()) {
                    int color1 = ColorUtils.getColorStyle(0);
                    int color2 = ColorUtils.getColorStyle(100);
                    int color3 = ColorUtils.getColorStyle(0);
                    int color4 = ColorUtils.getColorStyle(100);
                   // drawBox2(position.x - 0.5f, position.y - 0.5f, position.z + 0.5f, position.w + 0.5f, ms, color1,color2,color3,color4);
                   // DisplayUtils.drawBoxTest(position.x, position.y, position.z, position.w, 1, FriendStorage.isFriend(entity.getName().getString()) ? friendColors : colors);
                }
                float hpOffset = 3f;
                float out = 0.5f;
                if (!remove.getValueByName("Полоску хп").get()) {
                    String header = mc.ingameGUI.getTabList().header == null ? " " : mc.ingameGUI.getTabList().header.getString().toLowerCase();

                    DisplayUtils.drawRectBuilding(position.x - hpOffset - out, position.y - out, position.x - hpOffset + 1 + out, position.w + out, ColorUtils.rgba(0, 0, 0, 128));
                    DisplayUtils.drawRectBuilding(position.x - hpOffset, position.y, position.x - hpOffset + 1, position.w, ColorUtils.rgba(0, 0, 0, 128));


                    Score score = mc.world.getScoreboard().getOrCreateScore(entity.getScoreboardName(), mc.world.getScoreboard().getObjectiveInDisplaySlot(2));

                    float hp = entity.getHealth();
                    float maxHp = entity.getMaxHealth();

                    if (mc.getCurrentServerData() != null && mc.getCurrentServerData().serverIP.contains("funtime") && (header.contains("анархия") || header.contains("гриферский"))) {
                        hp = score.getScorePoints();
                        maxHp = 20;
                    }

                    DisplayUtils.drawMCVerticalBuilding(position.x - hpOffset, position.y + (position.w - position.y) * (1 - MathHelper.clamp(hp / maxHp, 0, 1)), position.x - hpOffset + 1, position.w, FriendStorage.isFriend(entity.getName().getString()) ? friendColors.w : colors.w, FriendStorage.isFriend(entity.getName().getString()) ? friendColors.x : colors.x);
                }
            }
        }
        Tessellator.getInstance().draw();
        RenderSystem.shadeModel(7424);
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();

        for (Map.Entry<Entity, Vector4f> entry : positions.entrySet()) {
            Entity entity = entry.getKey();

            if (entity instanceof LivingEntity living) {
                Score score = mc.world.getScoreboard().getOrCreateScore(living.getScoreboardName(), mc.world.getScoreboard().getObjectiveInDisplaySlot(2));
                float hp = living.getHealth();
                float maxHp = living.getMaxHealth();

                String header = mc.ingameGUI.getTabList().header == null ? " " : mc.ingameGUI.getTabList().header.getString().toLowerCase();


                if (mc.getCurrentServerData() != null && mc.getCurrentServerData().serverIP.contains("funtime") && (header.contains("анархия") || header.contains("гриферский"))) {
                    hp = score.getScorePoints();
                    maxHp = 20;
                }

                Vector4f position = entry.getValue();
                float width = position.z - position.x;

                String hpText = (int) hp + "HP";
                float hpWidth = Fonts.consolas.getWidth(hpText, 6);

                float hpPercent = MathHelper.clamp(hp / maxHp, 0, 1);
                float hpPosY = position.y + (position.w - position.y) * (1 - hpPercent);
                if (!remove.getValueByName("Текст хп").get()) {
                    Fonts.consolas.drawText(e.getMatrixStack(), hpText, position.x - hpWidth - 6, hpPosY, -1, 6, 0.05f);
                }
                float length = mc.fontRenderer.getStringPropertyWidth(entity.getDisplayName());

                GL11.glPushMatrix();

                glCenteredScale(position.x + width / 2f - length / 2f, position.y - 7, length, 10, 0.5f);

                String friendPrefix = FriendStorage.isFriend(entity.getName().getString()) ? TextFormatting.GREEN + "[F] " : "";
                TextComponent name = (TextComponent) ITextComponent.getTextComponentOrEmpty(friendPrefix);
                name.append(entity.getDisplayName());

                mc.fontRenderer.func_243246_a(e.getMatrixStack(), name, position.x + width / 2f - length / 2f, position.y - 7, -1);
                GL11.glPopMatrix();
                if (!remove.getValueByName("Список эффектов").get()) {

                    drawPotions(e.getMatrixStack(), living, position.z + 2, position.y);
                }
                drawItems(e.getMatrixStack(), living, (int) (position.x + width / 2f), (int) (position.y - 20));
            } else if (entity instanceof ItemEntity item) {
                Vector4f position = entry.getValue();
                float width = position.z - position.x;
                float length = mc.fontRenderer.getStringPropertyWidth(entity.getDisplayName());

                GL11.glPushMatrix();

                glCenteredScale(position.x + width / 2f - length / 2f, position.y - 7, length, 10, 0.5f);

                mc.fontRenderer.func_243246_a(e.getMatrixStack(), entity.getDisplayName(), position.x + width / 2f - length / 2f, position.y - 7, -1);
                GL11.glPopMatrix();

            }
        }
    }
    private void drawBox2(float maxX, float minX, float maxY, float minY, MatrixStack matrix, int color1, int color2, int color3, int color4) {
        float boxWidth = (maxX - minX);
        float boxHeight = (maxY - minY);
        float lineWidth = 1;

        float sect = Math.min(boxWidth / 4, boxHeight / 4);

        // top left
        RectUtils.drawRect2(matrix, minX, minY, minX + sect, minY + lineWidth, color1, color1, color1, color1, false, false);
        RectUtils.drawRect2(matrix, minX, minY, minX + lineWidth, minY + sect, color1, color1, color1, color1, false, false);

        // top right
        RectUtils.drawRect2(matrix, maxX, minY, maxX - sect, minY + lineWidth, color2, color2, color2, color2, false, false);
        RectUtils.drawRect2(matrix, maxX, minY, maxX + lineWidth, minY + sect, color2, color2, color2, color2, false, false);

        // bottom right
        RectUtils.drawRect2(matrix, maxX, minY + boxHeight - lineWidth, maxX - sect, minY + boxHeight, color3, color3, color3, color3, false, false);
        RectUtils.drawRect2(matrix, maxX, maxY, maxX + lineWidth, maxY - sect, color3, color3, color3, color3, false, false);

        // bottom left
        RectUtils.drawRect2(matrix, minX, minY + boxHeight - lineWidth, minX + sect, minY + boxHeight, color4, color4, color4, color4, false, false);
        RectUtils.drawRect2(matrix, minX, maxY, minX + lineWidth, maxY - sect, color4, color4, color4, color4, false, false);
    }
    public boolean isInView(Entity ent) {

        if (mc.getRenderViewEntity() == null) {
            return false;
        }
        frustum.setCameraPosition(mc.getRenderManager().info.getProjectedView().x, mc.getRenderManager().info.getProjectedView().y, mc.getRenderManager().info.getProjectedView().z);
        return frustum.isBoundingBoxInFrustum(ent.getBoundingBox()) || ent.ignoreFrustumCheck;
    }

    private void drawPotions(MatrixStack matrixStack, LivingEntity entity, float posX, float posY) {
        for (EffectInstance pot : entity.getActivePotionEffects()) {
            int amp = pot.getAmplifier();

            String ampStr = "";

            if (amp >= 1 && amp <= 9) {
                ampStr = " " + I18n.format("enchantment.level." + (amp + 1));
            }

            String text = I18n.format(pot.getEffectName()) + ampStr + " - " + EffectUtils.getPotionDurationString(pot, 1);

            Fonts.consolas.drawText(matrixStack, text, posX, posY, -1, 6, 0.05f);

            posY += Fonts.consolas.getHeight(6);
        }
    }

    private void drawItems(MatrixStack matrixStack, LivingEntity entity, int posX, int posY) {
        int size = 8;
        int padding = 6;

        float fontHeight = Fonts.consolas.getHeight(6);

        List<ItemStack> items = new ArrayList<>();

        ItemStack mainStack = entity.getHeldItemMainhand();

        if (!mainStack.isEmpty()) {
            items.add(mainStack);
        }

        for (ItemStack itemStack : entity.getArmorInventoryList()) {
            if (itemStack.isEmpty()) continue;
            items.add(itemStack);
        }

        ItemStack offStack = entity.getHeldItemOffhand();

        if (!offStack.isEmpty()) {
            items.add(offStack);
        }

        posX -= (items.size() * (size + padding)) / 2f;

        for (ItemStack itemStack : items) {
            if (itemStack.isEmpty()) continue;

            GL11.glPushMatrix();

            glCenteredScale(posX, posY, size / 2f, size / 2f, 0.5f);

            mc.getItemRenderer().renderItemAndEffectIntoGUI(itemStack, posX, posY);
            mc.getItemRenderer().renderItemOverlayIntoGUI(mc.fontRenderer, itemStack, posX, posY, null);

            GL11.glPopMatrix();

            if (itemStack.isEnchanted() && !remove.getValueByName("Зачарования").get()) {
                int ePosY = (int) (posY - fontHeight);

                Map<Enchantment, Integer> enchantmentsMap = EnchantmentHelper.getEnchantments(itemStack);

                for (Enchantment enchantment : enchantmentsMap.keySet()) {
                    int level = enchantmentsMap.get(enchantment);

                    if (level < 1 || !enchantment.canApply(itemStack)) continue;

                    IFormattableTextComponent iformattabletextcomponent = new TranslationTextComponent(enchantment.getName());

                    String enchText = iformattabletextcomponent.getString().substring(0, 2) + level;

                    Fonts.consolas.drawText(matrixStack, enchText, posX, ePosY, -1, 6, 0.05f);

                    ePosY -= (int) fontHeight;
                }
            }

            posX += size + padding;
        }
    }

    public boolean isValid(Entity e) {
        if (AntiBot.isBot(e)) return false;

        return isInView(e);
    }

    public void glCenteredScale(final float x, final float y, final float w, final float h, final float f) {
        glTranslatef(x + w / 2, y + h / 2, 0);
        glScalef(f, f, 1);
        glTranslatef(-x - w / 2, -y - h / 2, 0);
    }
}

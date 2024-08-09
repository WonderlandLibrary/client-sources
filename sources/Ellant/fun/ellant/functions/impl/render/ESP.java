package fun.ellant.functions.impl.render;

import com.google.common.eventbus.Subscribe;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import fun.ellant.command.friends.FriendStorage;
import fun.ellant.events.EventDisplay;
import fun.ellant.functions.api.Category;
import fun.ellant.functions.api.Function;
import fun.ellant.functions.api.FunctionRegister;
import fun.ellant.functions.impl.combat.AntiBot;
import fun.ellant.functions.impl.hud.HUD;
import fun.ellant.functions.settings.impl.*;
import fun.ellant.utils.math.MathUtil;
import fun.ellant.utils.math.Vector4i;
import fun.ellant.utils.projections.ProjectionUtil;
import fun.ellant.utils.render.ColorUtils;
import fun.ellant.utils.render.DisplayUtils;
import fun.ellant.utils.render.font.Fonts;
import net.minecraft.client.renderer.BufferBuilder;
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
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
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

@FunctionRegister(name = "ESP", type = Category.RENDER, desc = "Отображает игроков")
public class ESP extends Function {
    public ModeListSetting remove = new ModeListSetting("Отоброжать",
            new BooleanSetting("Полоску хп", false),
            new BooleanSetting("Эффекты", false),
            new BooleanSetting("Предметы", false),
            new BooleanSetting("Текст хп", false),
            new BooleanSetting("Ники игроков", true),
            new BooleanSetting("Зачарования", false),
            new BooleanSetting("Боксы", true),
            new BooleanSetting("Сферы", true),
            new BooleanSetting("Талисманы", true)
    );
    public ModeSetting boxMode = new ModeSetting("Вид боксов", "По углам", "По углам", "Бокс").setVisible(() -> remove.getValueByName("Боксы").get());



    public ESP() {
        toggle();
        addSettings(remove, boxMode);
    }

    private final HashMap<Entity, Vector4f> positions = new HashMap<>();
    @Subscribe
    public void onDisplay(EventDisplay e) {
        if (mc.world == null || e.getType() != EventDisplay.Type.PRE) {
            return;
        }
        positions.clear();
        Vector4i colors = new Vector4i(HUD.getColor(0, 1), HUD.getColor(90, 1), HUD.getColor(180, 1), HUD.getColor(270, 1));
        Vector4i friendColors = new Vector4i(ColorUtils.rgb(144, 238, 144), ColorUtils.rgb(0, 139, 0), ColorUtils.rgb(144, 238, 144), ColorUtils.rgb(0, 139, 0));//,ColorUtils.rgb(144, 238, 144), ColorUtils.rgb(0, 139, 0), 180, 1), HUD.getColor(ColorUtils.rgb(144, 238, 144), ColorUtils.rgb(0, 139, 0), 270, 1));
        int friendColorInt = FriendStorage.getColor();
        int colorInt = HUD.getColor(270);

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
                if (remove.getValueByName("Боксы").get() && boxMode.is("Бокс")) {
                    DisplayUtils.drawBox(position.x - 0.5f, position.y - 0.5f, position.z + 0.5f, position.w + 0.5f, 2, ColorUtils.rgba(0, 0, 0, 128));
                    DisplayUtils.drawBoxTest(position.x, position.y, position.z, position.w, 1, FriendStorage.isFriend(entity.getName().getString()) ? friendColors : colors);
                }
                float sect = 10;
                float lineWidth = 1;
                double playerX = mc.player.getPosX();
                double playerY = mc.player.getPosY();
                double playerZ = mc.player.getPosZ();
                double entityX = position.x;
                double entityY = position.y;
                double entityZ = position.z;
                double distance = Math.sqrt(Math.pow(playerX - entityX, 2) + Math.pow(playerY - entityY, 2) + Math.pow(playerZ - entityZ, 2));
                float sectChange = (float) (distance / 10.0);
                float calcSect = sect - sectChange;
                calcSect = Math.max(3, calcSect);

                if (remove.getValueByName("Боксы").get() && boxMode.is("По углам")) {
                    double x = position.x;
                    double y = position.y;
                    double endX = position.z;
                    double endY = position.w;
                    int back = ColorUtils.rgba(0, 0, 0, 128);

                    double size = 1f;
                    int getColor = FriendStorage.isFriend(entity.getName().getString()) ? friendColorInt : colorInt;
                    drawMcRect(x - 0.5F, y - 0.5F, x + calcSect, y + 1, getColor);
                    drawMcRect(endX - calcSect, y - 0.5F, endX + 1, y + 1, getColor);
                    drawMcRect(x - 0.5F, endY - 0.5F, x + calcSect, endY + 1, getColor);
                    drawMcRect(endX - calcSect, endY - 0.5F, endX + 1, endY + 1, getColor);

                    drawMcRect(x - 0.5F, y + 1, x + 1, y + calcSect, getColor);
                    drawMcRect(x - 0.5F, endY - calcSect, x + 1, endY, getColor);
                    drawMcRect(endX - 0.5F, y + 1, endX + 1, y + calcSect, getColor);
                    drawMcRect(endX - 0.5F, endY - calcSect, endX + 1, endY, getColor);
                }

                float hpOffset = 3f;
                float w = 1f;
                float h = 2f;
                float s = 2.4f;
                float out = 0.5f;
                if (remove.getValueByName("Полоску хп").get()) {
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
                if (remove.getValueByName("Текст хп").get()) {
                    Fonts.consolas.drawText(e.getMatrixStack(), hpText, position.x - hpWidth - 6, hpPosY, -1, 6, 0.05f);
                }
                float length = mc.fontRenderer.getStringPropertyWidth(entity.getDisplayName());
                GL11.glPushMatrix();
                glCenteredScale(position.x + width / 2f - length / 2f, position.y - 7, length, 10, 0.5f);
                String friendPrefix = FriendStorage.isFriend(entity.getName().getString()) ? TextFormatting.GREEN + "[Друг] " : "";
                TextComponent name = (TextComponent) ITextComponent.getTextComponentOrEmpty(friendPrefix);
                name.append(entity.getDisplayName());
                if(remove.getValueByName("Ники игроков").get()) {
                    mc.fontRenderer.func_243246_a(e.getMatrixStack(), name, position.x + width / 2f - length / 2f, position.y - 7, - 1);
                }
                if(entity instanceof PlayerEntity player){
                    if(remove.getValueByName("Талисманы").get()){
                        ItemStack stack = player.getHeldItemOffhand();
                        String nameS = "";

                        String itemName = stack.getDisplayName().getString();
                        if(stack.getItem() == Items.TOTEM_OF_UNDYING) {
                            CompoundNBT tag = stack.getTag();

                            if (tag != null && tag.contains("display", 10)) {
                                CompoundNBT display = tag.getCompound("display");

                                if (display.contains("Lore", 9)) {
                                    ListNBT lore = display.getList("Lore", 8);

                                    if (!lore.isEmpty()) {
                                        String firstLore = lore.getString(0);

                                        int levelIndex = firstLore.indexOf("Уровень");
                                        if (levelIndex != -1) {
                                            String levelString = firstLore.substring(levelIndex + "Уровень".length()).trim();
                                            String gat = levelString;
                                            if(gat.contains("1/3")){
                                                nameS = "- 1/3]";
                                            } else if (gat.contains("2/3")) {
                                                nameS = "- 2/3]";
                                            } else if (gat.contains("MAX")) {
                                                nameS = "- MAX]";
                                            } else{
                                                nameS = "";
                                            }
                                        }
                                    }
                                }
                            }
                            if(itemName.contains("Феник")){
                                itemName = "[ФЕНИКСА ";
                            } else if (itemName.contains("Деда")) {
                                itemName = "[ДЕДАЛА ";
                            } else if (itemName.contains("Ехид")) {
                                itemName = "[ЕХИДНЫ ";
                            } else if (itemName.contains("Гармон")) {
                                itemName = "[ГАРМОНИИ ";
                            } else if (itemName.contains("Трито")) {
                                itemName = "[ТРИТОНА";
                            } else if (itemName.contains("Гра")) {
                                itemName = "[ГРАНИ ";
                            } else if (itemName.contains("Круши")) {
                                itemName = "[КРУШИТЕЛЯ ";
                            } else if (itemName.contains("Карат")) {
                                itemName = "[КАРАТЕЛЯ ";
                            }
                            Fonts.montserrat.drawText(e.getMatrixStack(), itemName + nameS, (float) position.x - 15, position.y - 16, ColorUtils.rgb(255, 16, 16), 7.5f, 0.0001f);
                        }
                    }
                }
                if(entity instanceof PlayerEntity player){
                    if(remove.getValueByName("Сферы").get()){
                        ItemStack stack = player.getHeldItemOffhand();
                        String nameS = "";

                        String itemName = stack.getDisplayName().getString();
                        if(stack.getItem() == Items.PLAYER_HEAD) {
                            CompoundNBT tag = stack.getTag();

                            if (tag != null && tag.contains("display", 10)) {
                                CompoundNBT display = tag.getCompound("display");

                                if (display.contains("Lore", 9)) {
                                    ListNBT lore = display.getList("Lore", 8);

                                    if (!lore.isEmpty()) {
                                        String firstLore = lore.getString(0);

                                        int levelIndex = firstLore.indexOf("Уровень");
                                        if (levelIndex != -1) {
                                            String levelString = firstLore.substring(levelIndex + "Уровень".length()).trim();
                                            String gat = levelString;
                                            if(gat.contains("1/3")){
                                                nameS = "- 1/3]";
                                            } else if (gat.contains("2/3")) {
                                                nameS = "- 2/3]";
                                            } else if (gat.contains("MAX")) {
                                                nameS = "- MAX]";
                                            } else{
                                                nameS = "";
                                            }
                                        }
                                    }
                                }
                            }
                            if(itemName.contains("Пандо")){
                                itemName = "[PANDORA ";
                            } else if (itemName.contains("Аполл")) {
                                itemName = "[APOLLON ";
                            } else if (itemName.contains("Тит")) {
                                itemName = "[TITANA ";
                            } else if (itemName.contains("Осир")) {
                                itemName = "[OSIRIS ";
                            } else if (itemName.contains("Андро")) {
                                itemName = "[ANDROMEDA";
                            } else if (itemName.contains("Хим")) {
                                itemName = "[XIMERA ";
                            } else if (itemName.contains("Астр")) {
                                itemName = "[ASTREYA ";
                            }
                            Fonts.montserrat.drawText(e.getMatrixStack(), itemName + nameS, (float) position.x - 15, position.y - 16, ColorUtils.rgb(255, 16, 16), 7.5f, 0.0001f);
                        }
                    }
                }
                GL11.glPopMatrix();
                if (remove.getValueByName("Эффекты").get()) {
                    drawPotions(e.getMatrixStack(), living, position.z + 2, position.y);
                }
                drawItems(e.getMatrixStack(), living, (int) (position.x + width / 2f), (int) (position.y - 20));
            } else if (entity instanceof ItemEntity item) {
                Vector4f position = entry.getValue();
                float width = position.z - position.x;
                float length = mc.fontRenderer.getStringPropertyWidth(entity.getDisplayName());
                GL11.glPushMatrix();
                glCenteredScale(position.x + width / 2f - length / 2f, position.y - 7, length, 10, 0.5f);
                if(remove.getValueByName("Предметы").get()) {
                    String tag = (item.getItem().getDisplayName().getString() +
                            (item.getItem().getCount() < 1 ? "" : " x" + item.getItem().getCount()));
                    mc.fontRenderer.func_243246_a(e.getMatrixStack(),ITextComponent.getTextComponentOrEmpty(tag), position.x + width / 2f - length / 2f, position.y - 7, tag.contains("[★]") ? ColorUtils.rgb(255, 16, 16) : -1);
                }
                GL11.glPopMatrix();
            }
        }
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
            if (itemStack.isEnchanted() && remove.getValueByName("Зачарования").get()) {
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
    public static void drawMcRect(
            double left,
            double top,
            double right,
            double bottom,
            int color) {
        if (left < right) {
            double i = left;
            left = right;
            right = i;
        }

        if (top < bottom) {
            double j = top;
            top = bottom;
            bottom = j;
        }

        float f3 = (float) (color >> 24 & 255) / 255.0F;
        float f = (float) (color >> 16 & 255) / 255.0F;
        float f1 = (float) (color >> 8 & 255) / 255.0F;
        float f2 = (float) (color & 255) / 255.0F;
        BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();

        bufferbuilder.pos(left, bottom, 1.0F).color(f, f1, f2, f3).endVertex();
        bufferbuilder.pos(right, bottom, 1.0F).color(f, f1, f2, f3).endVertex();
        bufferbuilder.pos(right, top, 1.0F).color(f, f1, f2, f3).endVertex();
        bufferbuilder.pos(left, top, 1.0F).color(f, f1, f2, f3).endVertex();

    }
}
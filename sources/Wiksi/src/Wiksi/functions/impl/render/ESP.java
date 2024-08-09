//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package src.Wiksi.functions.impl.render;

import com.google.common.eventbus.Subscribe;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import src.Wiksi.command.friends.FriendStorage;
import src.Wiksi.events.EventDisplay;
import src.Wiksi.events.EventDisplay.Type;
import src.Wiksi.functions.api.Category;
import src.Wiksi.functions.api.Function;
import src.Wiksi.functions.api.FunctionRegister;
import src.Wiksi.functions.impl.combat.AntiBot;
import src.Wiksi.functions.settings.Setting;
import src.Wiksi.functions.settings.impl.BooleanSetting;
import src.Wiksi.functions.settings.impl.ColorSetting;
import src.Wiksi.functions.settings.impl.ModeListSetting;
import src.Wiksi.functions.settings.impl.ModeSetting;
import src.Wiksi.utils.math.MathUtil;
import src.Wiksi.utils.math.Vector4i;
import src.Wiksi.utils.projections.ProjectionUtil;
import src.Wiksi.utils.render.ColorUtils;
import src.Wiksi.utils.render.DisplayUtils;
import src.Wiksi.utils.render.font.Fonts;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import net.minecraft.client.renderer.BufferBuilder;
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
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import org.lwjgl.opengl.GL11;

import static com.ibm.icu.impl.ValidIdentifiers.Datatype.x;
import static src.Wiksi.utils.render.DisplayUtils.drawBox;
import static java.awt.SystemColor.text;
import static net.minecraft.item.Items.TOTEM_OF_UNDYING;

@FunctionRegister(
        name = "ESP",
        type = Category.Render
)
public class ESP extends Function {
    public ModeListSetting remove = new ModeListSetting("Элементы", new BooleanSetting[]{new BooleanSetting("Боксы", false), new BooleanSetting("Полоску хп", false), new BooleanSetting("Текст хп", false), new BooleanSetting("Зачарования", false), new BooleanSetting("Список эффектов", true), new BooleanSetting("Сферы", true), new BooleanSetting("Предметы", false), new BooleanSetting("Сферы-RW", false)});
    public ModeSetting boxMode = (new ModeSetting("Вид боксов", "Бокс", new String[]{"Углы", "Бокс"})).setVisible(() -> {
        return (Boolean)this.remove.getValueByName("Боксы").get();
    });

    private final HashMap<Entity, Vector4f> positions = new HashMap();
    public ColorSetting color = new ColorSetting("Color", -1);

    public ESP() {
        this.toggle();
        this.addSettings(new Setting[]{this.remove, this.boxMode});
    }

    @Subscribe
    public void onDisplay(EventDisplay e) {
        if (mc.world != null && e.getType() == Type.PRE) {
            this.positions.clear();
            Vector4i colors = new Vector4i(HUD.getColor(0, 1.0F), HUD.getColor(90, 1.0F), HUD.getColor(180, 1.0F), HUD.getColor(270, 1.0F));
            Vector4i friendColors = new Vector4i(HUD.getColor(ColorUtils.rgb(144, 238, 144), ColorUtils.rgb(0, 139, 0), 0, 1.0F), HUD.getColor(ColorUtils.rgb(144, 238, 144), ColorUtils.rgb(0, 139, 0), 90, 1.0F), HUD.getColor(ColorUtils.rgb(144, 238, 144), ColorUtils.rgb(0, 139, 0), 180, 1.0F), HUD.getColor(ColorUtils.rgb(144, 238, 144), ColorUtils.rgb(0, 139, 0), 270, 1.0F));
            Iterator var4 = mc.world.getAllEntities().iterator();

            while(true) {
                Entity entity;
                do {
                    do {
                        do {
                            if (!var4.hasNext()) {
                                RenderSystem.enableBlend();
                                RenderSystem.disableTexture();
                                RenderSystem.defaultBlendFunc();
                                RenderSystem.shadeModel(7425);
                                buffer.begin(7, DefaultVertexFormats.POSITION_COLOR);
                                var4 = this.positions.entrySet().iterator();

                                while(true) {
                                    float backgroundWidth1;
                                    float backgroundWidth2;
                                    Vector4f position;
                                    float hpOffset;
                                    float hp;
                                    do {
                                        float padding;
                                        float textScale;
                                        Object var58;
                                        do {
                                            Map.Entry entry;
                                            if (!var4.hasNext()) {
                                                Tessellator.getInstance().draw();
                                                RenderSystem.shadeModel(7424);
                                                RenderSystem.enableTexture();
                                                RenderSystem.disableBlend();
                                                var4 = this.positions.entrySet().iterator();

                                                while(true) {
                                                    while (var4.hasNext()) {
                                                        entry = (Map.Entry) var4.next();
                                                        entity = (Entity) entry.getKey();
                                                        String text2;
                                                        float width;
                                                        if (entity instanceof LivingEntity) {
                                                            LivingEntity living = (LivingEntity) entity;
                                                            Score score = mc.world.getScoreboard().getOrCreateScore(living.getScoreboardName(), mc.world.getScoreboard().getObjectiveInDisplaySlot(2));
                                                            hp = living.getHealth();
                                                            padding = living.getMaxHealth();
                                                            String header = mc.ingameGUI.getTabList().header == null ? " " : mc.ingameGUI.getTabList().header.getString().toLowerCase();
                                                            if (mc.getCurrentServerData() != null && mc.getCurrentServerData().serverIP.contains("funtime") && (header.contains("анархия") || header.contains("гриферский"))) {
                                                                hp = (float) score.getScorePoints();
                                                                padding = 20.0F;
                                                            }

                                                            position = (Vector4f) entry.getValue();
                                                            width = position.z - position.x;
                                                            String hpText = (int) hp + "HP";
                                                            float hpWidth = Fonts.consolas.getWidth(hpText, 6.0F);
                                                            float hpPercent = MathHelper.clamp(hp / padding, 0.0F, 1.0F);
                                                            float hpPosY = position.y + (position.w - position.y) * (1.0F - hpPercent);
                                                            if ((Boolean) this.remove.getValueByName("Текст хп").get()) {
                                                                Fonts.consolas.drawText(e.getMatrixStack(), hpText, position.x - hpWidth - 8.0F, hpPosY, -1, 6.0F, 0.05F);
                                                            }

                                                            float length = (float) mc.fontRenderer.getStringPropertyWidth(entity.getDisplayName());
                                                            float scaledLength = length * 0.7F;
                                                            padding = 4.0F;
                                                            float backgroundWidth = scaledLength + padding * 2.0F;
                                                            int backgroundColor = FriendStorage.isFriend(entity.getName().getString()) ? ColorUtils.rgb(0, 100, 0) : ColorUtils.rgba(10, 10, 10, 210);
                                                            GL11.glPushMatrix();
                                                            DisplayUtils.drawRoundedRect(position.x + width / 2.0F - backgroundWidth / 2.0F - 8.0F, position.y - 15.0F, backgroundWidth + 18.0F, 10.0F, 1.0F, backgroundColor);
                                                            this.glCenteredScale(position.x + width / 2.0F - scaledLength / 2.0F, position.y - 30.0F, scaledLength, 10.0F, 0.7F);
                                                            if (FriendStorage.isFriend(entity.getName().getString())) {
                                                            } else {
                                                                String var10000 = "";
                                                            }

                                                            ITextComponent text = entity.getDisplayName();
                                                            TextComponent name = (TextComponent) text;
                                                            name.append(new StringTextComponent(TextFormatting.GRAY + " [" + TextFormatting.GREEN + (int) hp + TextFormatting.GRAY + "]"));
                                                            mc.fontRenderer.func_243246_a(e.getMatrixStack(), name, position.x + width / 2.0F - length / 2.0F - 10.0F, position.y - 7.0F, -1);
                                                            GL11.glPopMatrix();
                                                            if (entity instanceof PlayerEntity) {
                                                                PlayerEntity player = (PlayerEntity) entity;
                                                                if ((Boolean) this.remove.getValueByName("Сферы").get()) {
                                                                    ItemStack stack = player.getHeldItemOffhand();
                                                                    ItemStack stack2 = player.getHeldItemOffhand();
                                                                    String nameS = "";
                                                                    String itemName = stack.getDisplayName().getString();
                                                                    if (stack.getItem() == Items.PLAYER_HEAD) {
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
                                                                                            if (levelString.contains("1/3")) {
                                                                                                nameS = "- 1/3]";
                                                                                            } else if (levelString.contains("2/3")) {
                                                                                                nameS = "- 2/3]";
                                                                                            } else if (levelString.contains("MAX")) {
                                                                                                nameS = "- MAX]";
                                                                                            } else {
                                                                                                nameS = "";
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }

                                                                            if (itemName.contains("Пандо")) {
                                                                                itemName = "[PANDORA ";
                                                                            } else if (itemName.contains("Аполл")) {
                                                                                itemName = "[AПОЛЛOH ";
                                                                            } else if (itemName.contains("Тит")) {
                                                                                itemName = "[TИTAH ";
                                                                            } else if (itemName.contains("Осир")) {
                                                                                itemName = "[Ocиpиc ";
                                                                            } else if (itemName.contains("Андро")) {
                                                                                itemName = "[Aндpomeдa";
                                                                            } else if (itemName.contains("Хим")) {
                                                                                itemName = "[Xиmepa ";
                                                                            } else if (itemName.contains("Астр")) {
                                                                                itemName = "[ACTREЯ ";
                                                                            } else if (itemName.contains("Посейдо")) {
                                                                                itemName = "[ПOCEЙДOH ";
                                                                            }

                                                                            textScale = 0.7F;
                                                                            float height = 20.0F;
                                                                            float scaledLength1 = (float) mc.fontRenderer.getStringPropertyWidth(ITextProperties.func_240652_a_(itemName + nameS)) * textScale;
                                                                            hp = 4.0F;
                                                                            backgroundWidth1 = scaledLength1 + hp * 2.0F;
                                                                            float spherePadding = -30.0F;
                                                                            float sphereDisplayX = position.x + (width - backgroundWidth1) / 2.0F;
                                                                            float var93 = position.y + height;
                                                                            Objects.requireNonNull(mc.fontRenderer);
                                                                            float sphereDisplayY = var93 - 9.0F * textScale - padding - 15.0F;
                                                                            GL11.glPushMatrix();
                                                                            GL11.glScalef(textScale, textScale, textScale);
                                                                            mc.fontRenderer.func_243246_a(e.getMatrixStack(), new StringTextComponent(itemName + nameS), (sphereDisplayX + backgroundWidth1 / 2.0F - scaledLength1 / 2.0F) / textScale, sphereDisplayY / textScale, ColorUtils.rgb(255, 14, 14));
                                                                            GL11.glPopMatrix();
                                                                        }
                                                                    }
                                                                }


                                                            if ((Boolean)this.remove.getValueByName("Список эффектов").get()) {
                                                                this.drawPotions(e.getMatrixStack(), living, position.z + 4.0F, position.y);
                                                            }

                                                            this.drawItems(e.getMatrixStack(), living, (int)(position.x + width / 2.0F + 3.0F), (int)(position.y - 25.0F));
                                                        } else if (entity instanceof ItemEntity) {
                                                            ItemEntity item = (ItemEntity)entity;
                                                            if ((Boolean)this.remove.getValueByName("Предметы").get()) {
                                                                position = (Vector4f) entry.getValue();
                                                                String displayName = entity.getDisplayName().getString();
                                                                int itemCount = item.getItem().getCount();
                                                                text2 = displayName + " x" + itemCount;
                                                                float textWidth = (float)mc.fontRenderer.getStringWidth(text2);
                                                                width = 3.0F;
                                                                GL11.glPushMatrix();
                                                                float x = position.x - textWidth / 2.0F;
                                                                float y;
                                                                y = position.y - 7.0F;
                                                                float backgroundWidth = textWidth + 2.0F * width;
                                                                float backgroundHeight = 9.0F;
                                                                DisplayUtils.drawRoundedRect(x - width, y, backgroundWidth, backgroundHeight, 2.0F, ColorUtils.rgba(0, 0, 0, 120));
                                                                Fonts.consolas.drawText(e.getMatrixStack(), text2, x, y, ColorUtils.getColor(90), 9.0F, 0.1F);
                                                                GL11.glPopMatrix();
                                                            }
                                                        }
                                                    }

                                                    return;
                                                }
                                            }

                                            entry = (Map.Entry)var4.next();
                                            entity = (Entity)entry.getKey();
                                            Vector4f position1 = (Vector4f)entry.getValue();
                                            position = (Vector4f)entry.getValue();
                                            var58 = entry.getKey();
                                        } while(!(var58 instanceof LivingEntity));

                                        entity = (LivingEntity)var58;
                                        if ((Boolean)this.remove.getValueByName("Боксы").get() && this.boxMode.is("Бокс")) {
                                            drawBox((double)(position.x - 0.5F), (double)(position.y - 0.5F), (double)(position.z + 0.5F), (double)(position.w + 0.5F), 2.0, ColorUtils.rgba(0, 0, 0, 128));
                                            DisplayUtils.drawBoxTest((double)position.x, (double)position.y, (double)position.z, (double)position.w, 1.0, FriendStorage.isFriend(entity.getName().getString()) ? friendColors : colors);
                                        }

                                        hp = 10.0F;
                                        padding = 1.0F;
                                        double playerX = mc.player.getPosX();
                                        double playerY = mc.player.getPosY();
                                        double playerZ = mc.player.getPosZ();
                                        double entityX = (double)position.x;
                                        double entityY = (double)position.y;
                                        double entityZ = (double)position.z;
                                        double distance = Math.sqrt(Math.pow(playerX - entityX, 2.0) + Math.pow(playerY - entityY, 2.0) + Math.pow(playerZ - entityZ, 2.0));
                                        float sectChange = (float)(distance / 10.0);
                                        double calcSect = (double)(hp - sectChange);
                                        calcSect = Math.max(3.0, calcSect);
                                        int friendColorInt = FriendStorage.getColor();
                                        int colorInt = HUD.getColor(270);
                                        if ((Boolean)this.remove.getValueByName("Боксы").get() && this.boxMode.is("Углы")) {
                                            double x = (double)position.x;
                                            double y = (double)position.y;
                                            double endX = (double)position.z;
                                            double endY = (double)position.w;
                                            int back = ColorUtils.rgba(0, 0, 0, 128);
                                            int getColor = FriendStorage.isFriend(entity.getName().getString()) ? friendColorInt : colorInt;
                                            double distanceX = endX - x;
                                            double distanceY = endY - y;
                                            double percentage = 0.1;
                                            double calcSectX = distanceX * percentage;
                                            double calcSectY = distanceY * percentage;
                                            drawMcRect(endX - calcSectX, y - 0.4, endX + 0.5, y - 0.5, ColorUtils.rgb(0,0,0));
                                            drawMcRect(endX - calcSectX, y - 0.4, endX + 0.5, y + 1.0, ColorUtils.rgb(0,0,0));
                                            drawMcRect(x - 0.5, y - 0.5, x + calcSectX, y + 1.0, getColor);
                                            drawMcRect(endX - calcSectX, y - 0.5, endX + 0.5, y + 1.0, getColor);
                                            drawMcRect(x - 0.5, endY - 0.5, x + calcSectX, endY + 1.0, getColor);
                                            drawMcRect(endX - calcSectX, endY - 0.5, endX + 0.5, endY + 1.0, getColor);
                                            drawMcRect(x - 0.5, y + 1.0, x + 1.0, y + calcSectY, getColor);
                                            drawMcRect(x - 0.5, endY - calcSectY, x + 1.0, endY, getColor);
                                            drawMcRect(endX - 0.5, y + 1.0, endX + 1.0, y + calcSectY, getColor);
                                            drawMcRect(endX - 0.5, endY - calcSectY, endX + 1.0, endY, getColor);
                                        }

                                        hpOffset = 2.0F;
                                        textScale = 0.5F;
                                    } while(!(Boolean)this.remove.getValueByName("Полоску хп").get());

                                    String header = mc.ingameGUI.getTabList().header == null ? " " : mc.ingameGUI.getTabList().header.getString().toLowerCase();
                                    Score score = mc.world.getScoreboard().getOrCreateScore(entity.getScoreboardName(), mc.world.getScoreboard().getObjectiveInDisplaySlot(2));
                                    hp = ((LivingEntity) entity).getHealth();
                                    backgroundWidth1 = ((LivingEntity) entity).getMaxHealth();
                                    if (mc.getCurrentServerData() != null && mc.getCurrentServerData().serverIP.contains("funtime") && (header.contains("анархия") || header.contains("гриферский"))) {
                                        hp = (float)score.getScorePoints();
                                        backgroundWidth1 = 20.0F;
                                    }

                                    int color = 20;
                                    if (hp >= 20.0F) {
                                        color = (new Color(2, 255, 2, 255)).getRGB();
                                    } else if (hp >= 18.0F) {
                                        color = (new Color(117, 148, 74, 255)).getRGB();
                                    } else if (hp >= 16.0F) {
                                        color = (new Color(148, 142, 74, 255)).getRGB();
                                    } else if (hp >= 14.0F) {
                                        color = (new Color(148, 118, 74, 255)).getRGB();
                                    } else if (hp >= 12.0F) {
                                        color = (new Color(148, 105, 74, 255)).getRGB();
                                    } else if (hp >= 10.0F) {
                                        color = (new Color(164, 92, 61, 255)).getRGB();
                                    } else if (hp >= 8.0F) {
                                        color = (new Color(159, 79, 50, 255)).getRGB();
                                    } else if (hp >= 6.0F) {
                                        color = (new Color(159, 66, 50, 255)).getRGB();
                                    } else if (hp >= 4.0F) {
                                        color = (new Color(185, 55, 40, 255)).getRGB();
                                    } else if (hp >= 2.0F) {
                                        color = (new Color(255, 26, 0, 255)).getRGB();
                                    }

                                    DisplayUtils.drawMCVerticalBuilding((double)(position.x - hpOffset - 3.0F), (double)(position.y + (position.w - position.y) * (1.0F - MathHelper.clamp(backgroundWidth1, 0.0F, 1.0F))), (double)(position.x - hpOffset - 2.0F), (double)position.w, FriendStorage.isFriend(entity.getName().getString()) ? friendColors.w : colors.w, FriendStorage.isFriend(entity.getName().getString()) ? friendColors.x : ColorUtils.rgba(0, 0, 0, 255));
                                    DisplayUtils.drawMCVerticalBuilding((double)(position.x - hpOffset - 3.0F), (double)(position.y + (position.w - position.y) * (1.0F - MathHelper.clamp(hp / backgroundWidth1, 0.0F, 1.0F))), (double)(position.x - hpOffset - 2.0F), (double)position.w, FriendStorage.isFriend(entity.getName().getString()) ? friendColors.w : color, FriendStorage.isFriend(entity.getName().getString()) ? friendColors.x : color);
                                }
                            }

                            entity = (Entity)var4.next();
                        } while(!this.isValid(entity));
                    } while(!(entity instanceof PlayerEntity) && !(entity instanceof ItemEntity));
                } while(entity == mc.player && mc.gameSettings.getPointOfView() == PointOfView.FIRST_PERSON);

                double x = MathUtil.interpolate(entity.getPosX(), entity.lastTickPosX, (double)e.getPartialTicks());
                double y = MathUtil.interpolate(entity.getPosY(), entity.lastTickPosY, (double)e.getPartialTicks());
                double z = MathUtil.interpolate(entity.getPosZ(), entity.lastTickPosZ, (double)e.getPartialTicks());
                Vector3d size = new Vector3d(entity.getBoundingBox().maxX - entity.getBoundingBox().minX, entity.getBoundingBox().maxY - entity.getBoundingBox().minY, entity.getBoundingBox().maxZ - entity.getBoundingBox().minZ);
                AxisAlignedBB aabb = new AxisAlignedBB(x - size.x / 2.0, y, z - size.z / 2.0, x + size.x / 2.0, y + size.y, z + size.z / 2.0);
                Vector4f position = null;

                for(int i = 0; i < 8; ++i) {
                    Vector2f vector = ProjectionUtil.project(i % 2 == 0 ? aabb.minX : aabb.maxX, i / 2 % 2 == 0 ? aabb.minY : aabb.maxY, i / 4 % 2 == 0 ? aabb.minZ : aabb.maxZ);
                    if (position == null) {
                        position = new Vector4f(vector.x, vector.y, 1.0F, 1.0F);
                    } else {
                        position.x = Math.min(vector.x, position.x);
                        position.y = Math.min(vector.y, position.y);
                        position.z = Math.max(vector.x, position.z);
                        position.w = Math.max(vector.y, position.w);
                    }
                }

                this.positions.put(entity, position);
            }
        }
    }

    public boolean isInView(Entity ent) {
        if (mc.getRenderViewEntity() == null) {
            return false;
        } else {
            WorldRenderer.frustum.setCameraPosition(mc.getRenderManager().info.getProjectedView().x, mc.getRenderManager().info.getProjectedView().y, mc.getRenderManager().info.getProjectedView().z);
            return WorldRenderer.frustum.isBoundingBoxInFrustum(ent.getBoundingBox()) || ent.ignoreFrustumCheck;
        }
    }

    private void drawPotions(MatrixStack matrixStack, LivingEntity entity, float posX, float posY) {
        for(Iterator var5 = entity.getActivePotionEffects().iterator(); var5.hasNext(); posY += Fonts.consolas.getHeight(6.0F)) {
            EffectInstance pot = (EffectInstance)var5.next();
            int amp = pot.getAmplifier();
            String ampStr = "";
            if (amp >= 1 && amp <= 9) {
                String var10000 = "enchantment.level." + (amp + 1);
                ampStr = " " + I18n.format(var10000, new Object[0]);
            }

            String text = I18n.format(pot.getEffectName(), new Object[0]) + ampStr + " - " + EffectUtils.getPotionDurationString(pot, 1.0F);
            Fonts.consolas.drawText(matrixStack, text, posX, posY, -1, 6.0F, 0.05F);
        }

    }

    private void drawItems(MatrixStack matrixStack, LivingEntity entity, int posX, int posY) {
        int size = 8;
        int padding = 2;
        float fontHeight = Fonts.consolas.getHeight(6.0F);
        List<ItemStack> items = new ArrayList();
        ItemStack mainStack = entity.getHeldItemMainhand();
        if (!mainStack.isEmpty()) {
            items.add(mainStack);
        }

        Iterator var10 = entity.getArmorInventoryList().iterator();

        while(var10.hasNext()) {
            ItemStack itemStack = (ItemStack)var10.next();
            if (!itemStack.isEmpty()) {
                items.add(itemStack);
            }
        }

        ItemStack offStack = entity.getHeldItemOffhand();
        if (!offStack.isEmpty()) {
            items.add(offStack);
        }

        posX = (int)((float)posX - (float)(items.size() * (size + padding)) / 2.0F);
        Iterator var21 = items.iterator();

        while(true) {
            ItemStack itemStack;
            do {
                if (!var21.hasNext()) {
                    return;
                }

                itemStack = (ItemStack)var21.next();
            } while(itemStack.isEmpty());

            GL11.glPushMatrix();
            this.glCenteredScale((float)posX, (float)posY, (float)size / 2.0F, (float)size / 2.0F, 0.5F);
            mc.getItemRenderer().renderItemAndEffectIntoGUI(itemStack, posX, posY);
            mc.getItemRenderer().renderItemOverlayIntoGUI(mc.fontRenderer, itemStack, posX, posY, (String)null);
            GL11.glPopMatrix();
            if (itemStack.isEnchanted() && (Boolean)this.remove.getValueByName("Зачарования").get()) {
                int ePosY = (int)((float)posY - fontHeight);
                Map<Enchantment, Integer> enchantmentsMap = EnchantmentHelper.getEnchantments(itemStack);
                Iterator var15 = enchantmentsMap.keySet().iterator();

                while(var15.hasNext()) {
                    Enchantment enchantment = (Enchantment)var15.next();
                    int level = (Integer)enchantmentsMap.get(enchantment);
                    if (level >= 1 && enchantment.canApply(itemStack)) {
                        IFormattableTextComponent iformattabletextcomponent = new TranslationTextComponent(enchantment.getName());
                        String var10000 = iformattabletextcomponent.getString().substring(0, 2);
                        String enchText = var10000 + level;
                        Fonts.consolas.drawText(matrixStack, enchText, (float)posX, (float)ePosY, -1, 6.0F, 0.05F);
                        ePosY -= (int)fontHeight;
                    }
                }
            }

            posX += size + padding;
        }
    }

    public static void drawMcRect(double left, double top, double right, double bottom, int color) {
        double j;
        if (left < right) {
            j = left;
            left = right;
            right = j;
        }

        if (top < bottom) {
            j = top;
            top = bottom;
            bottom = j;
        }

        float f3 = (float)(color >> 24 & 255) / 255.0F;
        float f = (float)(color >> 16 & 255) / 255.0F;
        float f1 = (float)(color >> 8 & 255) / 255.0F;
        float f2 = (float)(color & 255) / 255.0F;
        BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();
        bufferbuilder.pos(left, bottom, 1.0).color(f, f1, f2, f3).endVertex();
        bufferbuilder.pos(right, bottom, 1.0).color(f, f1, f2, f3).endVertex();
        bufferbuilder.pos(right, top, 1.0).color(f, f1, f2, f3).endVertex();
        bufferbuilder.pos(left, top, 1.0).color(f, f1, f2, f3).endVertex();
    }

    public boolean isValid(Entity e) {
        return AntiBot.isBot(e) ? false : this.isInView(e);
    }

    public void glCenteredScale(float x, float y, float w, float h, float f) {
        GL11.glTranslatef(x + w / 2.0F, y + h / 2.0F, 2.0F);
        GL11.glScalef(f, f, 1.0F);
        GL11.glTranslatef(-x - w / 2.0F, -y - h / 2.0F, 0.0F);
    }
}

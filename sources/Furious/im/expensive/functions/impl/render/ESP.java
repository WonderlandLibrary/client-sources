package im.expensive.functions.impl.render;

import com.google.common.eventbus.Subscribe;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import im.expensive.command.friends.FriendStorage;
import im.expensive.events.EventDisplay;
import im.expensive.events.EventDisplay.Type;
import im.expensive.functions.api.Category;
import im.expensive.functions.api.Function;
import im.expensive.functions.api.FunctionRegister;
import im.expensive.functions.impl.combat.AntiBot;
import im.expensive.functions.settings.Setting;
import im.expensive.functions.settings.impl.BooleanSetting;
import im.expensive.functions.settings.impl.ColorSetting;
import im.expensive.functions.settings.impl.ModeListSetting;
import im.expensive.utils.math.MathUtil;
import im.expensive.utils.math.Vector4i;
import im.expensive.utils.projections.ProjectionUtil;
import im.expensive.utils.render.ColorUtils;
import im.expensive.utils.render.DisplayUtils;
import im.expensive.utils.render.font.Fonts;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.minecraft.client.network.play.NetworkPlayerInfo;
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
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectUtils;
import net.minecraft.scoreboard.Score;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector4f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import org.lwjgl.opengl.GL11;

@FunctionRegister(
        name = "ESP",
        type = Category.Render
)
public class ESP extends Function {
    public ModeListSetting setting = new ModeListSetting("Использовать", new BooleanSetting[]{new BooleanSetting("Боксы", false), new BooleanSetting("Полоску хп", false), new BooleanSetting("Текст хп", false), new BooleanSetting("Зачарования", false), new BooleanSetting("Список эффектов", false), new BooleanSetting("Индикация сфер и талисманов", false)});
    private final HashMap<Entity, Vector4f> positions = new HashMap();
    public ColorSetting color = new ColorSetting("Color", -1);

    public ESP() {
        this.addSettings(new Setting[]{this.setting});
    }

    @Subscribe
    public void onDisplay(EventDisplay var1) {
        if (mc.world != null && var1.getType() == Type.PRE) {
            this.positions.clear();
            Vector4i var2 = new Vector4i(HUD.getColor(0, 1.0F), HUD.getColor(90, 1.0F), HUD.getColor(180, 1.0F), HUD.getColor(270, 1.0F));
            Vector4i var3 = new Vector4i(HUD.getColor(ColorUtils.rgb(144, 238, 144), ColorUtils.rgb(0, 139, 0), 0, 1.0F), HUD.getColor(ColorUtils.rgb(144, 238, 144), ColorUtils.rgb(0, 139, 0), 90, 1.0F), HUD.getColor(ColorUtils.rgb(144, 238, 144), ColorUtils.rgb(0, 139, 0), 180, 1.0F), HUD.getColor(ColorUtils.rgb(144, 238, 144), ColorUtils.rgb(0, 139, 0), 270, 1.0F));
            Iterator var4 = mc.world.getAllEntities().iterator();

            while(true) {
                Entity var5;
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
                                    LivingEntity var7;
                                    Vector4f var27;
                                    float var31;
                                    float var32;
                                    do {
                                        Object var29;
                                        do {
                                            Map.Entry var26;
                                            if (!var4.hasNext()) {
                                                Tessellator.getInstance().draw();
                                                RenderSystem.shadeModel(7424);
                                                RenderSystem.enableTexture();
                                                RenderSystem.disableBlend();
                                                var4 = this.positions.entrySet().iterator();

                                                while(true) {
                                                    while(var4.hasNext()) {
                                                        var26 = (Map.Entry)var4.next();
                                                        Entity var28 = (Entity)var26.getKey();
                                                        float var38;
                                                        float var39;
                                                        if (var28 instanceof LivingEntity) {
                                                            var7 = (LivingEntity)var28;
                                                            Score var35 = mc.world.getScoreboard().getOrCreateScore(var7.getScoreboardName(), mc.world.getScoreboard().getObjectiveInDisplaySlot(2));
                                                            var38 = var7.getHealth();
                                                            var39 = var7.getMaxHealth();
                                                            String var41 = mc.ingameGUI.getTabList().header == null ? " " : mc.ingameGUI.getTabList().header.getString().toLowerCase();
                                                            if (mc.getCurrentServerData() != null && mc.getCurrentServerData().serverIP.contains("funtime") && (var41.contains("анархия") || var41.contains("гриферский"))) {
                                                                var38 = (float)var35.getScorePoints();
                                                                var39 = 20.0F;
                                                            }

                                                            Vector4f var43 = (Vector4f)var26.getValue();
                                                            float var44 = var43.z - var43.x;
                                                            String var45 = (int)var38 + "HP";
                                                            float var46 = Fonts.consolas.getWidth(var45, 6.0F);
                                                            float var17 = MathHelper.clamp(var38 / var39, 0.0F, 1.0F);
                                                            float var18 = var43.y + (var43.w - var43.y) * (1.0F - var17);
                                                            if ((Boolean)this.setting.getValueByName("Текст хп").get()) {
                                                                Fonts.sfMedium.drawText(var1.getMatrixStack(), var45, var43.x - var46 - 6.0F, var18, -1, 6.0F, 0.05F);
                                                            }

                                                            float var19 = (float)mc.fontRenderer.getStringPropertyWidth(var28.getDisplayName());
                                                            GL11.glPushMatrix();
                                                            this.glCenteredScale(var43.x + var44 / 2.0F - var19 / 2.0F, var43.y - 7.0F, var19, 10.0F, 0.5F);
                                                            String var20 = var28.getDisplayName().getString();
                                                            boolean var22 = false;
                                                            NetworkPlayerInfo var23 = mc.getConnection().getPlayerInfo(var28.getUniqueID());
                                                            String var21;
                                                            if (var23 != null) {
                                                                int var47 = var23.getResponseTime();
                                                                var21 = "[" + var47 + " ms] " + var20;
                                                            } else {
                                                                var21 = var20;
                                                            }

                                                            String var24 = FriendStorage.isFriend(var28.getName().getString()) ? TextFormatting.GREEN + "[F] " : "";
                                                            TextComponent var25 = (TextComponent)ITextComponent.getTextComponentOrEmpty(var24);
                                                            var25.append(new StringTextComponent(var21));
                                                            mc.fontRenderer.func_243246_a(var1.getMatrixStack(), var25, var43.x + var44 / 2.0F - var19 / 2.0F, var43.y - 7.0F, -1);
                                                            GL11.glPopMatrix();
                                                            if ((Boolean)this.setting.getValueByName("Список эффектов").get()) {
                                                                this.drawPotions(var1.getMatrixStack(), var7, var43.z + 2.0F, var43.y);
                                                            }

                                                            this.drawItems(var1.getMatrixStack(), var7, (int)(var43.x + var44 / 2.0F), (int)(var43.y - 20.0F));
                                                        } else if (var28 instanceof ItemEntity) {
                                                            ItemEntity var33 = (ItemEntity) var28;
                                                            ItemStack itemStack = var33.getItem();
                                                            ITextComponent itemName = itemStack.getDisplayName();

                                                            Vector4f var34 = (Vector4f) var26.getValue();
                                                            var38 = var34.z - var34.x;
                                                            var39 = (float) mc.fontRenderer.getStringPropertyWidth(itemName);

                                                            GL11.glPushMatrix();
                                                            this.glCenteredScale(var34.x + var38 / 2.0F - var39 / 2.0F, var34.y - 7.0F, var39, 10.0F, 0.5F);
                                                            mc.fontRenderer.func_243246_a(var1.getMatrixStack(), itemName, var34.x + var38 / 2.0F - var39 / 2.0F, var34.y - 7.0F, -1);
                                                            GL11.glPopMatrix();
                                                        }
                                                    }

                                                    return;
                                                }
                                            }

                                            var26 = (Map.Entry)var4.next();
                                            var27 = (Vector4f)var26.getValue();
                                            var29 = var26.getKey();
                                        } while(!(var29 instanceof LivingEntity));

                                        var7 = (LivingEntity)var29;

                                        if ((Boolean)this.setting.getValueByName("Боксы").get()) {
                                            DisplayUtils.drawBox((double)(var27.x - 0.5F), (double)(var27.y - 0.5F), (double)(var27.z + 0.5F), (double)(var27.w + 0.5F), 2.0, ColorUtils.rgba(0, 0, 0, 128));
                                            DisplayUtils.drawBoxTest((double)var27.x, (double)var27.y, (double)var27.z, (double)var27.w, 1.0, FriendStorage.isFriend(var7.getName().getString()) ? var3 : var2);
                                        }

                                        var31 = 3.0F;
                                        var32 = 0.5F;
                                    } while((Boolean)this.setting.getValueByName("Полоску хп").get());

                                    String var37 = mc.ingameGUI.getTabList().header == null ? " " : mc.ingameGUI.getTabList().header.getString().toLowerCase();
                                    DisplayUtils.drawRectBuilding((double)(var27.x - var31 - var32), (double)(var27.y - var32), (double)(var27.x - var31 + 1.0F + var32), (double)(var27.w + var32), ColorUtils.rgba(0, 0, 0, 128));
                                    DisplayUtils.drawRectBuilding((double)(var27.x - var31), (double)var27.y, (double)(var27.x - var31 + 1.0F), (double)var27.w, ColorUtils.rgba(0, 0, 0, 128));
                                    Score var11 = mc.world.getScoreboard().getOrCreateScore(var7.getScoreboardName(), mc.world.getScoreboard().getObjectiveInDisplaySlot(2));
                                    float var40 = var7.getHealth();
                                    float var42 = var7.getMaxHealth();
                                    if (mc.getCurrentServerData() != null && mc.getCurrentServerData().serverIP.contains("funtime") && (var37.contains("анархия") || var37.contains("гриферский"))) {
                                        var40 = (float)var11.getScorePoints();
                                        var42 = 20.0F;
                                    }

                                    DisplayUtils.drawMCVerticalBuilding((double)(var27.x - var31), (double)(var27.y + (var27.w - var27.y) * (1.0F - MathHelper.clamp(var40 / var42, 0.0F, 1.0F))), (double)(var27.x - var31 + 1.0F), (double)var27.w, FriendStorage.isFriend(var7.getName().getString()) ? var3.w : var2.w, FriendStorage.isFriend(var7.getName().getString()) ? var3.x : var2.x);
                                }
                            }

                            var5 = (Entity)var4.next();
                        } while(!this.isValid(var5));
                    } while(!(var5 instanceof PlayerEntity) && !(var5 instanceof ItemEntity));
                } while(var5 == mc.player && mc.gameSettings.getPointOfView() == PointOfView.FIRST_PERSON);

                double var6 = MathUtil.interpolate(var5.getPosX(), var5.lastTickPosX, (double)var1.getPartialTicks());
                double var8 = MathUtil.interpolate(var5.getPosY(), var5.lastTickPosY, (double)var1.getPartialTicks());
                double var10 = MathUtil.interpolate(var5.getPosZ(), var5.lastTickPosZ, (double)var1.getPartialTicks());
                Vector3d var12 = new Vector3d(var5.getBoundingBox().maxX - var5.getBoundingBox().minX, var5.getBoundingBox().maxY - var5.getBoundingBox().minY, var5.getBoundingBox().maxZ - var5.getBoundingBox().minZ);
                AxisAlignedBB var13 = new AxisAlignedBB(var6 - var12.x / 2.0, var8, var10 - var12.z / 2.0, var6 + var12.x / 2.0, var8 + var12.y, var10 + var12.z / 2.0);
                Vector4f var14 = null;

                for(int var15 = 0; var15 < 8; ++var15) {
                    Vector2f var16 = ProjectionUtil.project(var15 % 2 == 0 ? var13.minX : var13.maxX, var15 / 2 % 2 == 0 ? var13.minY : var13.maxY, var15 / 4 % 2 == 0 ? var13.minZ : var13.maxZ);
                    if (var14 == null) {
                        var14 = new Vector4f(var16.x, var16.y, 1.0F, 1.0F);
                    } else {
                        var14.x = Math.min(var16.x, var14.x);
                        var14.y = Math.min(var16.y, var14.y);
                        var14.z = Math.max(var16.x, var14.z);
                        var14.w = Math.max(var16.y, var14.w);
                    }
                }

                this.positions.put(var5, var14);
            }
        }
    }

    public boolean isInView(Entity var1) {
        if (mc.getRenderViewEntity() == null) {
            return true;
        } else {
            WorldRenderer.frustum.setCameraPosition(mc.getRenderManager().info.getProjectedView().x, mc.getRenderManager().info.getProjectedView().y, mc.getRenderManager().info.getProjectedView().z);
            return WorldRenderer.frustum.isBoundingBoxInFrustum(var1.getBoundingBox()) || var1.ignoreFrustumCheck;
        }
    }

    private void drawPotions(MatrixStack var1, LivingEntity var2, float var3, float var4) {
        for(Iterator var5 = var2.getActivePotionEffects().iterator(); var5.hasNext(); var4 += Fonts.sfMedium.getHeight(6.0F)) {
            EffectInstance var6 = (EffectInstance)var5.next();
            int var7 = var6.getAmplifier();
            String var8 = "";
            if (var7 >= 1 && var7 <= 9) {
                String var10000 = "enchantment.level." + (var7 + 1);
                var8 = " " + I18n.format(var10000, new Object[0]);
            }

            String var9 = I18n.format(var6.getEffectName(), new Object[0]) + var8 + " - " + EffectUtils.getPotionDurationString(var6, 1.0F);
            Fonts.sfMedium.drawText(var1, var9, var3, var4, -1, 6.0F, 0.05F);
        }

    }

    private void drawItems(MatrixStack var1, LivingEntity var2, int var3, int var4) {
        byte var5 = 8;
        byte var6 = 6;
        float var7 = Fonts.sfMedium.getHeight(6.0F);
        ArrayList var8 = new ArrayList();
        ItemStack var9 = var2.getHeldItemMainhand();
        if (!var9.isEmpty()) {
            var8.add(var9);
        }

        Iterator var10 = var2.getArmorInventoryList().iterator();

        while(var10.hasNext()) {
            ItemStack var11 = (ItemStack)var10.next();
            if (!var11.isEmpty()) {
                var8.add(var11);
            }
        }

        ItemStack var20 = var2.getHeldItemOffhand();
        if (!var20.isEmpty() && (var20.getItem() == Items.TOTEM_OF_UNDYING || var20.getItem() == Items.PLAYER_HEAD)) {
            var8.add(var20);
        }

        var3 = (int)((float)var3 - (float)(var8.size() * (var5 + var6)) / 2.0F);
        Iterator var21 = var8.iterator();

        while(true) {
            ItemStack var12;
            do {
                if (!var21.hasNext()) {
                    return;
                }

                var12 = (ItemStack)var21.next();
            } while(var12.isEmpty());

            GL11.glPushMatrix();
            this.glCenteredScale((float)var3, (float)var4, (float)var5 / 2.0F, (float)var5 / 2.0F, 0.5F);
            mc.getItemRenderer().renderItemAndEffectIntoGUI(var12, var3, var4);
            mc.getItemRenderer().renderItemOverlayIntoGUI(mc.fontRenderer, var12, var3, var4, (String)null);
            GL11.glPopMatrix();
            if ((Boolean)this.setting.getValueByName("Индикация сфер и талисманов").get()&& var12 == var20 && mc.player != null) {
                ITextComponent var13 = var12.getDisplayName();
                int var14 = var3 + var5 / 2 + 10;
                int var15 = var4 - (int)(var7 + 2.0F);
                mc.fontRenderer.func_243246_a(var1, var13, (float)var14, (float)var15, -1);
            }

            if (var12.isEnchanted() && (Boolean)this.setting.getValueByName("Зачарования").get()) {
                int var22 = (int)((float)var4 - var7);
                Map var23 = EnchantmentHelper.getEnchantments(var12);
                Iterator var24 = var23.keySet().iterator();

                while(var24.hasNext()) {
                    Enchantment var16 = (Enchantment)var24.next();
                    int var17 = (Integer)var23.get(var16);
                    if (var17 >= 1 && var16.canApply(var12)) {
                        TranslationTextComponent var18 = new TranslationTextComponent(var16.getName());
                        String var10000 = var18.getString().substring(0, 2);
                        String var19 = var10000 + var17;
                        Fonts.sfMedium.drawText(var1, var19, (float)var3, (float)var22, -1, 6.0F, 0.05F);
                        var22 -= (int)var7;
                    }
                }
            }

            var3 += var5 + var6;
        }
    }

    public boolean isValid(Entity var1) {
        return AntiBot.isBot(var1) ? true : this.isInView(var1);
    }

    public void glCenteredScale(float var1, float var2, float var3, float var4, float var5) {
        GL11.glTranslatef(var1 + var3 / 2.0F, var2 + var4 / 2.0F, 0.0F);
        GL11.glScalef(var5, var5, 1.0F);
        GL11.glTranslatef(-var1 - var3 / 2.0F, -var2 - var4 / 2.0F, 0.0F);
    }
}
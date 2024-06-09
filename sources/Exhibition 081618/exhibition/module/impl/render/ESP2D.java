package exhibition.module.impl.render;

import exhibition.Client;
import exhibition.event.Event;
import exhibition.event.EventListener;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventNametagRender;
import exhibition.event.impl.EventRender3D;
import exhibition.event.impl.EventRenderGui;
import exhibition.management.ColorManager;
import exhibition.management.ColorObject;
import exhibition.management.friend.FriendManager;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.module.data.Options;
import exhibition.module.data.Setting;
import exhibition.module.data.SettingsMap;
import exhibition.module.impl.render.Nametags;
import exhibition.module.impl.render.Tags;
import exhibition.util.MathUtils;
import exhibition.util.RenderingUtil;
import exhibition.util.TeamUtils;
import exhibition.util.render.Colors;
import exhibition.util.render.TTFFontRenderer;
import java.awt.Color;
import java.io.PrintStream;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Timer;
import org.lwjgl.opengl.GL11;

public class ESP2D
extends Module {
    public static String TEAM = "TEAM";
    private final String INVISIBLES = "INVISIBLES";
    private String CUSTOMTAG = "ITEMTAG";
    private String ITEMS = "ITEMS";
    private String HEALTH = "HEALTH";
    private String ARMOR = "ARMOR";
    private String BOX = "BOX";
    private String NAME = "NAME";
    private Options boxMode = new Options("Box Mode", "Box", new String[]{"Box", "Corner A", "Corner B", "Split"});
    private Map<EntityLivingBase, double[]> entityConvertedPointsMap = new HashMap<EntityLivingBase, double[]>();

    public ESP2D(ModuleData data) {
        super(data);
        this.settings.put(TEAM, new Setting(TEAM, false, "Team colors."));
        this.settings.put(this.ARMOR, new Setting(this.ARMOR, true, "Render Armor on ESP."));
        this.settings.put("INVISIBLES", new Setting("INVISIBLES", false, "Show invisibles."));
        this.settings.put(this.HEALTH, new Setting(this.HEALTH, true, "Renders healthbar."));
        this.settings.put(this.BOX, new Setting(this.BOX, true, "Draw 2D Box."));
        this.settings.put(this.ITEMS, new Setting(this.ITEMS, true, "Shows player's current item."));
        this.settings.put(this.NAME, new Setting(this.NAME, true, "Shows the players name."));
        this.settings.put(this.CUSTOMTAG, new Setting(this.CUSTOMTAG, false, "Shows the custom name the item has. Requires ITEMS set to true."));
        this.settings.put("MODE", new Setting("MODE", this.boxMode, "DurDOr"));
    }

    @Override
    public EventListener.Priority getPriority() {
        return EventListener.Priority.MEDIUM;
    }

    @RegisterEvent(events={EventRender3D.class, EventRenderGui.class, EventNametagRender.class})
    public void onEvent(Event event) {
        if (event instanceof EventNametagRender) {
            event.setCancelled(true);
        }
        if (event instanceof EventRender3D) {
            try {
                this.updatePositions();
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        if (event instanceof EventRenderGui) {
            EventRenderGui er = (EventRenderGui)event;
            GlStateManager.pushMatrix();
            ScaledResolution scaledRes = er.getResolution();
            double twoDscale = (double)scaledRes.getScaleFactor() / Math.pow(scaledRes.getScaleFactor(), 2.0);
            GlStateManager.scale(twoDscale, twoDscale, twoDscale);
            for (Entity entity : this.entityConvertedPointsMap.keySet()) {
                boolean shouldRender;
                EntityPlayer ent = (EntityPlayer)entity;
                double[] renderPositions = this.entityConvertedPointsMap.get(entity);
                double[] renderPositionsBottom = new double[]{renderPositions[4], renderPositions[5], renderPositions[6]};
                double[] renderPositionsX = new double[]{renderPositions[7], renderPositions[8], renderPositions[9]};
                double[] renderPositionsX1 = new double[]{renderPositions[10], renderPositions[11], renderPositions[12]};
                double[] renderPositionsZ = new double[]{renderPositions[13], renderPositions[14], renderPositions[15]};
                double[] renderPositionsZ1 = new double[]{renderPositions[16], renderPositions[17], renderPositions[18]};
                double[] renderPositionsTop1 = new double[]{renderPositions[19], renderPositions[20], renderPositions[21]};
                double[] renderPositionsTop2 = new double[]{renderPositions[22], renderPositions[23], renderPositions[24]};
                boolean bl = shouldRender = renderPositions[3] > 0.0 && renderPositions[3] <= 1.0 && renderPositionsBottom[2] > 0.0 && renderPositionsBottom[2] <= 1.0 && renderPositionsX[2] > 0.0 && renderPositionsX[2] <= 1.0 && renderPositionsX1[2] > 0.0 && renderPositionsX1[2] <= 1.0 && renderPositionsZ[2] > 0.0 && renderPositionsZ[2] <= 1.0 && renderPositionsZ1[2] > 0.0 && renderPositionsZ1[2] <= 1.0 && renderPositionsTop1[2] > 0.0 && renderPositionsTop1[2] <= 1.0 && renderPositionsTop2[2] > 0.0 && renderPositionsTop2[2] <= 1.0;
                if ((double)ESP2D.mc.thePlayer.getDistanceToEntity(ent) < 2.5 && renderPositionsTop1[1] < 0.0) {
                    shouldRender = false;
                }
                if (!shouldRender) continue;
                GlStateManager.pushMatrix();
                if ((((Boolean)((Setting)this.settings.get("INVISIBLES")).getValue()).booleanValue() || !ent.isInvisible()) && ent instanceof EntityPlayer && !(ent instanceof EntityPlayerSP)) {
                    try {
                        boolean hovering;
                        GL11.glEnable((int)3042);
                        GL11.glDisable((int)3553);
                        RenderingUtil.rectangle(0.0, 0.0, 0.0, 0.0, Colors.getColor(0, 0));
                        double[] xValues = new double[]{renderPositions[0], renderPositionsBottom[0], renderPositionsX[0], renderPositionsX1[0], renderPositionsZ[0], renderPositionsZ1[0], renderPositionsTop1[0], renderPositionsTop2[0]};
                        double[] yValues = new double[]{renderPositions[1], renderPositionsBottom[1], renderPositionsX[1], renderPositionsX1[1], renderPositionsZ[1], renderPositionsZ1[1], renderPositionsTop1[1], renderPositionsTop2[1]};
                        double x = renderPositions[0];
                        double y = renderPositions[1];
                        double endx = renderPositionsBottom[0];
                        double endy = renderPositionsBottom[1];
                        for (double bdubs : xValues) {
                            if (!(bdubs < x)) continue;
                            x = bdubs;
                        }
                        for (double bdubs : xValues) {
                            if (!(bdubs > endx)) continue;
                            endx = bdubs;
                        }
                        for (double bdubs : yValues) {
                            if (!(bdubs < y)) continue;
                            y = bdubs;
                        }
                        for (double bdubs : yValues) {
                            if (!(bdubs > endy)) continue;
                            endy = bdubs;
                        }
                        if (((Boolean)((Setting)this.settings.get(this.BOX)).getValue()).booleanValue()) {
                            int color = ColorManager.getEnemyVisible().getColorInt();
                            if (FriendManager.isFriend(ent.getName())) {
                                color = ESP2D.mc.thePlayer.canEntityBeSeen(ent) ? ColorManager.getFriendlyVisible().getColorInt() : ColorManager.getFriendlyInvisible().getColorInt();
                            } else if (!ESP2D.mc.thePlayer.canEntityBeSeen(ent)) {
                                color = ColorManager.getEnemyInvisible().getColorInt();
                            }
                            if (((Boolean)((Setting)this.settings.get(TEAM)).getValue()).booleanValue()) {
                                color = TeamUtils.isTeam(ESP2D.mc.thePlayer, ent) ? ColorManager.fTeam.getColorInt() : ColorManager.eTeam.getColorInt();
                            }
                            double xDiff = (endx - x) / 4.0;
                            double x2Diff = (endx - x) / (double)(this.boxMode.getSelected().equalsIgnoreCase("Corner B") ? 4 : 3);
                            double yDiff = this.boxMode.getSelected().equalsIgnoreCase("Corner B") ? xDiff : (endy - y) / 4.0;
                            switch (this.boxMode.getSelected()) {
                                case "Box": {
                                    RenderingUtil.rectangleBordered(x + 0.5, y + 0.5, endx - 0.5, endy - 0.5, 1.0, Colors.getColor(0, 0, 0, 0), color);
                                    RenderingUtil.rectangleBordered(x - 0.5, y - 0.5, endx + 0.5, endy + 0.5, 1.0, Colors.getColor(0, 0), Colors.getColor(0, 150));
                                    RenderingUtil.rectangleBordered(x + 1.5, y + 1.5, endx - 1.5, endy - 1.5, 1.0, Colors.getColor(0, 0), Colors.getColor(0, 150));
                                    break;
                                }
                                case "Split": {
                                    RenderingUtil.rectangle(x + 0.5, y + 0.5, x + 1.5, endy - 0.5, color);
                                    RenderingUtil.rectangle(x - 0.5, y + 0.5, x + 0.5, endy - 0.5, Colors.getColor(0, 150));
                                    RenderingUtil.rectangle(x + 1.5, y + 2.5, x + 2.5, endy - 2.5, Colors.getColor(0, 150));
                                    RenderingUtil.rectangle(x + 1.0, y + 0.5, x + xDiff, y + 1.5, color);
                                    RenderingUtil.rectangle(x - 0.5, y - 0.5, x + xDiff, y + 0.5, Colors.getColor(0, 150));
                                    RenderingUtil.rectangle(x + 1.5, y + 1.5, x + xDiff, y + 2.5, Colors.getColor(0, 150));
                                    RenderingUtil.rectangle(x + xDiff, y - 0.5, x + xDiff + 1.0, y + 2.5, Colors.getColor(0, 150));
                                    RenderingUtil.rectangle(x + 1.0, endy - 0.5, x + xDiff, endy - 1.5, color);
                                    RenderingUtil.rectangle(x - 0.5, endy + 0.5, x + xDiff, endy - 0.5, Colors.getColor(0, 150));
                                    RenderingUtil.rectangle(x + 1.5, endy - 1.5, x + xDiff, endy - 2.5, Colors.getColor(0, 150));
                                    RenderingUtil.rectangle(x + xDiff, endy + 0.5, x + xDiff + 1.0, endy - 2.5, Colors.getColor(0, 150));
                                    RenderingUtil.rectangle(endx - 0.5, y + 0.5, endx - 1.5, endy - 0.5, color);
                                    RenderingUtil.rectangle(endx + 0.5, y + 0.5, endx - 0.5, endy - 0.5, Colors.getColor(0, 150));
                                    RenderingUtil.rectangle(endx - 1.5, y + 2.5, endx - 2.5, endy - 2.5, Colors.getColor(0, 150));
                                    RenderingUtil.rectangle(endx - 1.0, y + 0.5, endx - xDiff, y + 1.5, color);
                                    RenderingUtil.rectangle(endx + 0.5, y - 0.5, endx - xDiff, y + 0.5, Colors.getColor(0, 150));
                                    RenderingUtil.rectangle(endx - 1.5, y + 1.5, endx - xDiff, y + 2.5, Colors.getColor(0, 150));
                                    RenderingUtil.rectangle(endx - xDiff, y - 0.5, endx - xDiff - 1.0, y + 2.5, Colors.getColor(0, 150));
                                    RenderingUtil.rectangle(endx - 1.0, endy - 0.5, endx - xDiff, endy - 1.5, color);
                                    RenderingUtil.rectangle(endx + 0.5, endy + 0.5, endx - xDiff, endy - 0.5, Colors.getColor(0, 150));
                                    RenderingUtil.rectangle(endx - 1.5, endy - 1.5, endx - xDiff, endy - 2.5, Colors.getColor(0, 150));
                                    RenderingUtil.rectangle(endx - xDiff, endy + 0.5, endx - xDiff - 1.0, endy - 2.5, Colors.getColor(0, 150));
                                    break;
                                }
                                case "Corner A": 
                                case "Corner B": {
                                    RenderingUtil.rectangle(x + 0.5, y + 0.5, x + 1.5, y + yDiff + 0.5, color);
                                    RenderingUtil.rectangle(x + 0.5, endy - 0.5, x + 1.5, endy - yDiff - 0.5, color);
                                    RenderingUtil.rectangle(x - 0.5, y + 0.5, x + 0.5, y + yDiff + 0.5, Colors.getColor(0, 150));
                                    RenderingUtil.rectangle(x + 1.5, y + 2.5, x + 2.5, y + yDiff + 0.5, Colors.getColor(0, 150));
                                    RenderingUtil.rectangle(x - 0.5, y + yDiff + 0.5, x + 2.5, y + yDiff + 1.5, Colors.getColor(0, 150));
                                    RenderingUtil.rectangle(x - 0.5, endy - 0.5, x + 0.5, endy - yDiff - 0.5, Colors.getColor(0, 150));
                                    RenderingUtil.rectangle(x + 1.5, endy - 2.5, x + 2.5, endy - yDiff - 0.5, Colors.getColor(0, 150));
                                    RenderingUtil.rectangle(x - 0.5, endy - yDiff - 0.5, x + 2.5, endy - yDiff - 1.5, Colors.getColor(0, 150));
                                    RenderingUtil.rectangle(x + 1.0, y + 0.5, x + x2Diff, y + 1.5, color);
                                    RenderingUtil.rectangle(x - 0.5, y - 0.5, x + x2Diff, y + 0.5, Colors.getColor(0, 150));
                                    RenderingUtil.rectangle(x + 1.5, y + 1.5, x + x2Diff, y + 2.5, Colors.getColor(0, 150));
                                    RenderingUtil.rectangle(x + x2Diff, y - 0.5, x + x2Diff + 1.0, y + 2.5, Colors.getColor(0, 150));
                                    RenderingUtil.rectangle(x + 1.0, endy - 0.5, x + x2Diff, endy - 1.5, color);
                                    RenderingUtil.rectangle(x - 0.5, endy + 0.5, x + x2Diff, endy - 0.5, Colors.getColor(0, 150));
                                    RenderingUtil.rectangle(x + 1.5, endy - 1.5, x + x2Diff, endy - 2.5, Colors.getColor(0, 150));
                                    RenderingUtil.rectangle(x + x2Diff, endy + 0.5, x + x2Diff + 1.0, endy - 2.5, Colors.getColor(0, 150));
                                    RenderingUtil.rectangle(endx - 0.5, y + 0.5, endx - 1.5, y + yDiff + 0.5, color);
                                    RenderingUtil.rectangle(endx - 0.5, endy - 0.5, endx - 1.5, endy - yDiff - 0.5, color);
                                    RenderingUtil.rectangle(endx + 0.5, y + 0.5, endx - 0.5, y + yDiff + 0.5, Colors.getColor(0, 150));
                                    RenderingUtil.rectangle(endx - 1.5, y + 2.5, endx - 2.5, y + yDiff + 0.5, Colors.getColor(0, 150));
                                    RenderingUtil.rectangle(endx + 0.5, y + yDiff + 0.5, endx - 2.5, y + yDiff + 1.5, Colors.getColor(0, 150));
                                    RenderingUtil.rectangle(endx + 0.5, endy - 0.5, endx - 0.5, endy - yDiff - 0.5, Colors.getColor(0, 150));
                                    RenderingUtil.rectangle(endx - 1.5, endy - 2.5, endx - 2.5, endy - yDiff - 0.5, Colors.getColor(0, 150));
                                    RenderingUtil.rectangle(endx + 0.5, endy - yDiff - 0.5, endx - 2.5, endy - yDiff - 1.5, Colors.getColor(0, 150));
                                    RenderingUtil.rectangle(endx - 1.0, y + 0.5, endx - x2Diff, y + 1.5, color);
                                    RenderingUtil.rectangle(endx + 0.5, y - 0.5, endx - x2Diff, y + 0.5, Colors.getColor(0, 150));
                                    RenderingUtil.rectangle(endx - 1.5, y + 1.5, endx - x2Diff, y + 2.5, Colors.getColor(0, 150));
                                    RenderingUtil.rectangle(endx - x2Diff, y - 0.5, endx - x2Diff - 1.0, y + 2.5, Colors.getColor(0, 150));
                                    RenderingUtil.rectangle(endx - 1.0, endy - 0.5, endx - x2Diff, endy - 1.5, color);
                                    RenderingUtil.rectangle(endx + 0.5, endy + 0.5, endx - x2Diff, endy - 0.5, Colors.getColor(0, 150));
                                    RenderingUtil.rectangle(endx - 1.5, endy - 1.5, endx - x2Diff, endy - 2.5, Colors.getColor(0, 150));
                                    RenderingUtil.rectangle(endx - x2Diff, endy + 0.5, endx - x2Diff - 1.0, endy - 2.5, Colors.getColor(0, 150));
                                }
                            }
                        }
                        if (((Boolean)((Setting)this.settings.get(this.HEALTH)).getValue()).booleanValue()) {
                            float health = ent.getHealth();
                            float[] fractions = new float[]{0.0f, 0.5f, 1.0f};
                            Color[] colors = new Color[]{Color.RED, Color.YELLOW, Color.GREEN};
                            float progress = health / ent.getMaxHealth();
                            Color customColor = health >= 0.0f ? ESP2D.blendColors(fractions, colors, progress).brighter() : Color.RED;
                            double difference = y - endy + 0.5;
                            double healthLocation = endy + difference * (double)progress;
                            RenderingUtil.rectangleBordered(x - 6.5, y - 0.5, x - 2.5, endy, 1.0, Colors.getColor(0, 100), Colors.getColor(0, 150));
                            RenderingUtil.rectangle(x - 5.5, endy - 1.0, x - 3.5, healthLocation, customColor.getRGB());
                            if (-difference > 50.0) {
                                for (int i = 1; i < 10; ++i) {
                                    double dThing = difference / 10.0 * (double)i;
                                    RenderingUtil.rectangle(x - 6.5, endy - 0.5 + dThing, x - 2.5, endy - 0.5 + dThing - 1.0, Colors.getColor(0));
                                }
                            }
                            if ((int)MathUtils.getIncremental(progress * 100.0f, 1.0) <= 40) {
                                GlStateManager.pushMatrix();
                                GlStateManager.scale(2.0f, 2.0f, 2.0f);
                                String nigger = (int)MathUtils.getIncremental(health * 5.0f, 1.0) + "HP";
                                Client.verdana10.drawStringWithShadow(nigger, (float)(x - 6.0 - (double)(Client.verdana16.getWidth(nigger) * 2.0f)) / 2.0f, ((float)((int)healthLocation) + Client.fss.getHeight(nigger) / 2.0f) / 2.0f, -1);
                                GlStateManager.popMatrix();
                            }
                        }
                        if (!((Module)Client.getModuleManager().get(Tags.class)).isEnabled() && !((Module)Client.getModuleManager().get(Nametags.class)).isEnabled() && ((Boolean)((Setting)this.settings.get(this.NAME)).getValue()).booleanValue()) {
                            RenderingUtil.rectangle(0.0, 0.0, 0.0, 0.0, Colors.getColor(0, 0));
                            GlStateManager.pushMatrix();
                            GlStateManager.scale(2.0f, 2.0f, 2.0f);
                            String renderName = "\u00a7a" + (int)ESP2D.mc.thePlayer.getDistanceToEntity(ent) + "m \u00a7r\u00a7l" + (FriendManager.isFriend(ent.getName()) ? FriendManager.getAlias(ent.getName()) : ent.getName());
                            TTFFontRenderer font = Client.fss;
                            float meme2 = (float)((endx - x) / 2.0 - (double)(font.getWidth(renderName) / 1.0f));
                            ColorObject temp = ColorManager.getFriendlyVisible();
                            font.drawStringWithShadow(renderName, (float)(x + (double)meme2) / 2.0f, (float)(y - (double)(font.getHeight(renderName) / 1.5f * 2.0f)) / 2.0f - 3.0f, FriendManager.isFriend(ent.getName()) ? Colors.getColor(temp.red, temp.green, temp.blue) : -1);
                            GlStateManager.popMatrix();
                        }
                        if (ent.getCurrentEquippedItem() != null && ((Boolean)((Setting)this.settings.get(this.ITEMS)).getValue()).booleanValue()) {
                            GlStateManager.pushMatrix();
                            GlStateManager.scale(2.0f, 2.0f, 2.0f);
                            ItemStack stack = ent.getCurrentEquippedItem();
                            String customName = (Boolean)((Setting)this.settings.get(this.CUSTOMTAG)).getValue() != false ? ent.getCurrentEquippedItem().getDisplayName() : ent.getCurrentEquippedItem().getItem().getItemStackDisplayName(stack);
                            float meme5 = (float)((endx - x) / 2.0 - (double)(Client.verdana10.getWidth(customName) / 1.0f));
                            Client.verdana10.drawStringWithShadow(customName, (float)(x + (double)meme5) / 2.0f, (float)(endy + (double)(Client.verdana10.getHeight(customName) / 2.0f * 2.0f)) / 2.0f + 1.0f, -1);
                            GlStateManager.popMatrix();
                        }
                        int mX = scaledRes.getScaledWidth();
                        int mY = scaledRes.getScaledHeight();
                        boolean bl2 = hovering = (double)mX > x - 15.0 && (double)mX < endx + 15.0 && (double)mY > y - 15.0 && (double)mY < endy + 15.0;
                        if (((Boolean)((Setting)this.settings.get(this.ARMOR)).getValue()).booleanValue()) {
                            ItemStack stack3;
                            ItemStack stack2;
                            ItemStack stack4;
                            float var1 = (float)((endy - y) / 4.0);
                            ItemStack stack = ent.getEquipmentInSlot(4);
                            if (stack != null) {
                                RenderingUtil.rectangleBordered(endx + 1.0, y + 1.0, endx + 5.0, y + (double)var1, 1.0, Colors.getColor(28, 156, 179, 100), Colors.getColor(0, 150));
                                float diff1 = (float)(y + (double)var1 - 1.0 - (y + 2.0));
                                double percent = 1.0 - (double)stack.getItemDamage() / (double)stack.getMaxDamage();
                                RenderingUtil.rectangle(endx + 2.0, y + (double)var1 - 1.0, endx + 4.0, y + (double)var1 - 1.0 - (double)diff1 * percent, Colors.getColor(78, 206, 229));
                                if (hovering) {
                                    ESP2D.mc.fontRendererObj.drawStringWithShadow(stack.getMaxDamage() - stack.getItemDamage() + "", (float)endx + 22.0f, (float)(y + (double)var1 - 1.0 - (double)(diff1 / 2.0f)), -1);
                                    GlStateManager.pushMatrix();
                                    GlStateManager.translate(endx + 4.0, y + (double)var1 - 6.0 - (double)(diff1 / 2.0f), 0.0);
                                    RenderHelper.enableGUIStandardItemLighting();
                                    mc.getRenderItem().remderItemIntoGUI(stack, 0, 0);
                                    mc.getRenderItem().renderItemOverlays(ESP2D.mc.fontRendererObj, stack, 0, 0);
                                    RenderHelper.disableStandardItemLighting();
                                    int pLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, stack);
                                    int tLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.thorns.effectId, stack);
                                    int uLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack);
                                    int xOff = 0;
                                    if (pLevel > 0) {
                                        ESP2D.mc.fontRendererObj.drawStringWithShadow("P" + this.getColor(pLevel) + pLevel, 40.0f, 5.0f, -1);
                                        xOff += 15;
                                    }
                                    if (tLevel > 0) {
                                        ESP2D.mc.fontRendererObj.drawStringWithShadow("Th" + this.getColor(tLevel) + tLevel, 40 + xOff, 5.0f, -1);
                                        xOff += 25;
                                    }
                                    if (uLevel > 0) {
                                        ESP2D.mc.fontRendererObj.drawStringWithShadow("Unb" + this.getColor(uLevel) + uLevel, 40 + xOff, 5.0f, -1);
                                    }
                                    GlStateManager.popMatrix();
                                }
                            }
                            if ((stack2 = ent.getEquipmentInSlot(3)) != null) {
                                RenderingUtil.rectangleBordered(endx + 1.0, y + (double)var1, endx + 5.0, y + (double)(var1 * 2.0f), 1.0, Colors.getColor(28, 156, 179, 100), Colors.getColor(0, 150));
                                float diff1 = (float)(y + (double)(var1 * 2.0f) - (y + (double)var1 + 2.0));
                                double percent = 1.0 - (double)stack2.getItemDamage() * 1.0 / (double)stack2.getMaxDamage();
                                RenderingUtil.rectangle(endx + 2.0, y + (double)(var1 * 2.0f), endx + 4.0, y + (double)(var1 * 2.0f) - (double)diff1 * percent, Colors.getColor(78, 206, 229));
                                if (hovering) {
                                    ESP2D.mc.fontRendererObj.drawStringWithShadow(stack2.getMaxDamage() - stack2.getItemDamage() + "", (float)endx + 22.0f, (float)(y + (double)(var1 * 2.0f) - (double)(diff1 / 2.0f)), -1);
                                    GlStateManager.pushMatrix();
                                    GlStateManager.translate(endx + 4.0, y + (double)(var1 * 2.0f) - 6.0 - (double)(diff1 / 2.0f), 0.0);
                                    RenderHelper.enableGUIStandardItemLighting();
                                    mc.getRenderItem().remderItemIntoGUI(stack2, 0, 0);
                                    mc.getRenderItem().renderItemOverlays(ESP2D.mc.fontRendererObj, stack2, 0, 0);
                                    RenderHelper.disableStandardItemLighting();
                                    int pLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, stack2);
                                    int tLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.thorns.effectId, stack2);
                                    int uLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack2);
                                    int xOff = 0;
                                    if (pLevel > 0) {
                                        ESP2D.mc.fontRendererObj.drawStringWithShadow("P" + this.getColor(pLevel) + pLevel, 40.0f, 5.0f, -1);
                                        xOff += 15;
                                    }
                                    if (tLevel > 0) {
                                        ESP2D.mc.fontRendererObj.drawStringWithShadow("Th" + this.getColor(tLevel) + tLevel, 40 + xOff, 5.0f, -1);
                                        xOff += 25;
                                    }
                                    if (uLevel > 0) {
                                        ESP2D.mc.fontRendererObj.drawStringWithShadow("Unb" + this.getColor(uLevel) + uLevel, 40 + xOff, 5.0f, -1);
                                    }
                                    GlStateManager.popMatrix();
                                }
                            }
                            if ((stack3 = ent.getEquipmentInSlot(2)) != null) {
                                RenderingUtil.rectangleBordered(endx + 1.0, y + (double)(var1 * 2.0f), endx + 5.0, y + (double)(var1 * 3.0f), 1.0, Colors.getColor(28, 156, 179, 100), Colors.getColor(0, 150));
                                float diff1 = (float)(y + (double)(var1 * 3.0f) - (y + (double)(var1 * 2.0f) + 2.0));
                                double percent = 1.0 - (double)stack3.getItemDamage() * 1.0 / (double)stack3.getMaxDamage();
                                RenderingUtil.rectangle(endx + 2.0, y + (double)(var1 * 3.0f), endx + 4.0, y + (double)(var1 * 3.0f) - (double)diff1 * percent, Colors.getColor(78, 206, 229));
                                if (hovering) {
                                    ESP2D.mc.fontRendererObj.drawStringWithShadow(stack3.getMaxDamage() - stack3.getItemDamage() + "", (float)endx + 22.0f, (float)(y + (double)(var1 * 3.0f) - (double)(diff1 / 2.0f)), -1);
                                    GlStateManager.pushMatrix();
                                    GlStateManager.translate(endx + 4.0, y + (double)(var1 * 3.0f) - 6.0 - (double)(diff1 / 2.0f), 0.0);
                                    RenderHelper.enableGUIStandardItemLighting();
                                    mc.getRenderItem().remderItemIntoGUI(stack3, 0, 0);
                                    mc.getRenderItem().renderItemOverlays(ESP2D.mc.fontRendererObj, stack3, 0, 0);
                                    RenderHelper.disableStandardItemLighting();
                                    int pLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, stack3);
                                    int tLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.thorns.effectId, stack3);
                                    int uLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack3);
                                    int xOff = 0;
                                    if (pLevel > 0) {
                                        ESP2D.mc.fontRendererObj.drawStringWithShadow("P" + this.getColor(pLevel) + pLevel, 40.0f, 5.0f, -1);
                                        xOff += 15;
                                    }
                                    if (tLevel > 0) {
                                        ESP2D.mc.fontRendererObj.drawStringWithShadow("Th" + this.getColor(tLevel) + tLevel, 40 + xOff, 5.0f, -1);
                                        xOff += 25;
                                    }
                                    if (uLevel > 0) {
                                        ESP2D.mc.fontRendererObj.drawStringWithShadow("Unb" + this.getColor(uLevel) + uLevel, 40 + xOff, 5.0f, -1);
                                    }
                                    GlStateManager.popMatrix();
                                }
                            }
                            if ((stack4 = ent.getEquipmentInSlot(1)) != null) {
                                RenderingUtil.rectangleBordered(endx + 1.0, y + (double)(var1 * 3.0f), endx + 5.0, y + (double)(var1 * 4.0f), 1.0, Colors.getColor(28, 156, 179, 100), Colors.getColor(0, 150));
                                float diff1 = (float)(y + (double)(var1 * 4.0f) - (y + (double)(var1 * 3.0f) + 2.0));
                                double percent = 1.0 - (double)stack4.getItemDamage() * 1.0 / (double)stack4.getMaxDamage();
                                RenderingUtil.rectangle(endx + 2.0, y + (double)(var1 * 4.0f) - 1.0, endx + 4.0, y + (double)(var1 * 4.0f) - (double)diff1 * percent, Colors.getColor(78, 206, 229));
                                if (hovering) {
                                    ESP2D.mc.fontRendererObj.drawStringWithShadow(stack4.getMaxDamage() - stack4.getItemDamage() + "", (float)endx + 22.0f, (float)(y + (double)(var1 * 4.0f) - (double)(diff1 / 2.0f)), -1);
                                    GlStateManager.pushMatrix();
                                    GlStateManager.translate(endx + 4.0, y + (double)(var1 * 4.0f) - 6.0 - (double)(diff1 / 2.0f), 0.0);
                                    RenderHelper.enableGUIStandardItemLighting();
                                    mc.getRenderItem().remderItemIntoGUI(stack4, 0, 0);
                                    mc.getRenderItem().renderItemOverlays(ESP2D.mc.fontRendererObj, stack4, 0, 0);
                                    RenderHelper.disableStandardItemLighting();
                                    int pLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, stack4);
                                    int tLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.thorns.effectId, stack4);
                                    int uLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack4);
                                    int xOff = 0;
                                    if (pLevel > 0) {
                                        ESP2D.mc.fontRendererObj.drawStringWithShadow("P" + this.getColor(pLevel) + pLevel, 40.0f, 5.0f, -1);
                                        xOff += 15;
                                    }
                                    if (tLevel > 0) {
                                        ESP2D.mc.fontRendererObj.drawStringWithShadow("Th" + this.getColor(tLevel) + tLevel, 40 + xOff, 5.0f, -1);
                                        xOff += 25;
                                    }
                                    if (uLevel > 0) {
                                        ESP2D.mc.fontRendererObj.drawStringWithShadow("Unb" + this.getColor(uLevel) + uLevel, 40 + xOff, 5.0f, -1);
                                    }
                                    GlStateManager.popMatrix();
                                }
                            }
                        }
                    }
                    catch (Exception xValues) {
                        // empty catch block
                    }
                }
                GlStateManager.popMatrix();
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            }
            GL11.glScalef((float)1.0f, (float)1.0f, (float)1.0f);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            GlStateManager.popMatrix();
            RenderingUtil.rectangle(0.0, 0.0, 0.0, 0.0, -1);
        }
    }

    private String getColor(int level) {
        if (level == 2) {
            return "\u00a7a";
        }
        if (level == 3) {
            return "\u00a73";
        }
        if (level == 4) {
            return "\u00a74";
        }
        if (level >= 5) {
            return "\u00a76";
        }
        return "\u00a7f";
    }

    public static Color blendColors(float[] fractions, Color[] colors, float progress) {
        Color color = null;
        if (fractions == null) throw new IllegalArgumentException("Fractions can't be null");
        if (colors == null) throw new IllegalArgumentException("Colours can't be null");
        if (fractions.length != colors.length) throw new IllegalArgumentException("Fractions and colours must have equal number of elements");
        int[] indicies = ESP2D.getFractionIndicies(fractions, progress);
        float[] range = new float[]{fractions[indicies[0]], fractions[indicies[1]]};
        Color[] colorRange = new Color[]{colors[indicies[0]], colors[indicies[1]]};
        float max = range[1] - range[0];
        float value = progress - range[0];
        float weight = value / max;
        return ESP2D.blend(colorRange[0], colorRange[1], 1.0f - weight);
    }

    public static int[] getFractionIndicies(float[] fractions, float progress) {
        int startPoint;
        int[] range = new int[2];
        for (startPoint = 0; startPoint < fractions.length && fractions[startPoint] <= progress; ++startPoint) {
        }
        if (startPoint >= fractions.length) {
            startPoint = fractions.length - 1;
        }
        range[0] = startPoint - 1;
        range[1] = startPoint;
        return range;
    }

    public static Color blend(Color color1, Color color2, double ratio) {
        float r = (float)ratio;
        float ir = 1.0f - r;
        float[] rgb1 = new float[3];
        float[] rgb2 = new float[3];
        color1.getColorComponents(rgb1);
        color2.getColorComponents(rgb2);
        float red = rgb1[0] * r + rgb2[0] * ir;
        float green = rgb1[1] * r + rgb2[1] * ir;
        float blue = rgb1[2] * r + rgb2[2] * ir;
        if (red < 0.0f) {
            red = 0.0f;
        } else if (red > 255.0f) {
            red = 255.0f;
        }
        if (green < 0.0f) {
            green = 0.0f;
        } else if (green > 255.0f) {
            green = 255.0f;
        }
        if (blue < 0.0f) {
            blue = 0.0f;
        } else if (blue > 255.0f) {
            blue = 255.0f;
        }
        Color color = null;
        try {
            color = new Color(red, green, blue);
        }
        catch (IllegalArgumentException exp) {
            NumberFormat nf = NumberFormat.getNumberInstance();
            System.out.println(nf.format(red) + "; " + nf.format(green) + "; " + nf.format(blue));
            exp.printStackTrace();
        }
        return color;
    }

    private void updatePositions() {
        this.entityConvertedPointsMap.clear();
        float pTicks = ESP2D.mc.timer.renderPartialTicks;
        for (Object e2 : ESP2D.mc.theWorld.getLoadedEntityList()) {
            EntityPlayer ent;
            if (!(e2 instanceof EntityPlayer) || (ent = (EntityPlayer)e2) == ESP2D.mc.thePlayer) continue;
            double x = ent.lastTickPosX + (ent.posX - ent.lastTickPosX) * (double)pTicks - ESP2D.mc.getRenderManager().viewerPosX + 0.36;
            double y = ent.lastTickPosY + (ent.posY - ent.lastTickPosY) * (double)pTicks - ESP2D.mc.getRenderManager().viewerPosY;
            double z = ent.lastTickPosZ + (ent.posZ - ent.lastTickPosZ) * (double)pTicks - ESP2D.mc.getRenderManager().viewerPosZ + 0.36;
            double topY = y += (double)ent.height + 0.15;
            double[] convertedPoints = RenderingUtil.convertTo2D(x, y, z);
            double[] convertedPoints22 = RenderingUtil.convertTo2D(x - 0.36, y, z - 0.36);
            double xd = 0.0;
            if (!(convertedPoints22[2] >= 0.0) || !(convertedPoints22[2] < 1.0)) continue;
            x = ent.lastTickPosX + (ent.posX - ent.lastTickPosX) * (double)pTicks - ESP2D.mc.getRenderManager().viewerPosX - 0.36;
            z = ent.lastTickPosZ + (ent.posZ - ent.lastTickPosZ) * (double)pTicks - ESP2D.mc.getRenderManager().viewerPosZ - 0.36;
            double[] convertedPointsBottom = RenderingUtil.convertTo2D(x, y, z);
            y = ent.lastTickPosY + (ent.posY - ent.lastTickPosY) * (double)pTicks - ESP2D.mc.getRenderManager().viewerPosY - 0.05;
            double[] convertedPointsx = RenderingUtil.convertTo2D(x, y, z);
            x = ent.lastTickPosX + (ent.posX - ent.lastTickPosX) * (double)pTicks - ESP2D.mc.getRenderManager().viewerPosX - 0.36;
            z = ent.lastTickPosZ + (ent.posZ - ent.lastTickPosZ) * (double)pTicks - ESP2D.mc.getRenderManager().viewerPosZ + 0.36;
            double[] convertedPointsTop1 = RenderingUtil.convertTo2D(x, topY, z);
            double[] convertedPointsx1 = RenderingUtil.convertTo2D(x, y, z);
            x = ent.lastTickPosX + (ent.posX - ent.lastTickPosX) * (double)pTicks - ESP2D.mc.getRenderManager().viewerPosX + 0.36;
            z = ent.lastTickPosZ + (ent.posZ - ent.lastTickPosZ) * (double)pTicks - ESP2D.mc.getRenderManager().viewerPosZ + 0.36;
            double[] convertedPointsz = RenderingUtil.convertTo2D(x, y, z);
            x = ent.lastTickPosX + (ent.posX - ent.lastTickPosX) * (double)pTicks - ESP2D.mc.getRenderManager().viewerPosX + 0.36;
            z = ent.lastTickPosZ + (ent.posZ - ent.lastTickPosZ) * (double)pTicks - ESP2D.mc.getRenderManager().viewerPosZ - 0.36;
            double[] convertedPointsTop2 = RenderingUtil.convertTo2D(x, topY, z);
            double[] convertedPointsz1 = RenderingUtil.convertTo2D(x, y, z);
            this.entityConvertedPointsMap.put(ent, new double[]{convertedPoints[0], convertedPoints[1], xd, convertedPoints[2], convertedPointsBottom[0], convertedPointsBottom[1], convertedPointsBottom[2], convertedPointsx[0], convertedPointsx[1], convertedPointsx[2], convertedPointsx1[0], convertedPointsx1[1], convertedPointsx1[2], convertedPointsz[0], convertedPointsz[1], convertedPointsz[2], convertedPointsz1[0], convertedPointsz1[1], convertedPointsz1[2], convertedPointsTop1[0], convertedPointsTop1[1], convertedPointsTop1[2], convertedPointsTop2[0], convertedPointsTop2[1], convertedPointsTop2[2]});
        }
    }

    private double[] convertTo2D(double x, double y, double z) {
        double[] convertedPoints = RenderingUtil.convertTo2D(x, y, z);
        return convertedPoints;
    }
}


package ru.FecuritySQ.module.визуальные;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AirItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StringUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import ru.FecuritySQ.FecuritySQ;
import ru.FecuritySQ.event.Event;
import ru.FecuritySQ.event.imp.EventHud;
import ru.FecuritySQ.font.Fonts;
import ru.FecuritySQ.module.Module;
import ru.FecuritySQ.option.imp.OptionBoolList;
import ru.FecuritySQ.option.imp.OptionBoolean;
import ru.FecuritySQ.option.imp.OptionNumric;
import ru.FecuritySQ.utils.RenderUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NameTags extends Module {

    public OptionNumric scale = new OptionNumric("Размер", 50, 10, 100, 1);

    public OptionBoolList ignore = new OptionBoolList("Игнорировать",
            new OptionBoolean("Друзья", false),
            new OptionBoolean("Невидмки", false));
    public OptionBoolean armor = new OptionBoolean("Броня",  true);

    public ItemRenderer renderItem;

    public NameTags() {
        super(Category.Визуальные, GLFW.GLFW_KEY_0);
        addOption(scale);
        addOption(ignore);
        addOption(armor);
        renderItem = mc.getItemRenderer();
    }

    @Override
    public void event(Event event) {
        if(event instanceof EventHud && isEnabled()) {
            List<PlayerEntity> valid = mc.world.getPlayers().stream().filter(entityLivingBase1 -> {
                if (entityLivingBase1.isInvisible() && ignore.setting("Невидмки").get()) return false;
                if (ignore.setting("Друзья").get() && (FecuritySQ.get().getFriendManager().isFriend(entityLivingBase1.getName().getString())))
                    return false;

                if (entityLivingBase1.isInvisible() && ignore.setting("Невидмки").get()) return false;
                return mc.player != entityLivingBase1;
            }).collect(Collectors.toList());

            for(PlayerEntity entity : valid){
                double posX = entity.lastTickPosX + (entity.getPosX() - entity.lastTickPosX) * mc.getRenderPartialTicks();
                double posY = entity.lastTickPosY + (entity.getPosY() - entity.lastTickPosY) * mc.getRenderPartialTicks();
                double posZ = entity.lastTickPosZ + (entity.getPosZ() - entity.lastTickPosZ) * mc.getRenderPartialTicks();

                double[] vec = RenderUtil.project(posX, posY + entity.getHeight() + 0.4f, posZ);

                if(vec != null){
                    String health = "§a";
                    if (entity.getHealth() < 13.0F && entity.getHealth() > 7.0F) {
                        health = "§6";
                    } else if (entity.getHealth() < 7.0F) {
                        health = "§c";
                    }
                    String playerName =  StringUtils.stripControlCodes(entity.getName().getString());

                    String friends = FecuritySQ.get().getFriendManager().isFriend(playerName) ? "§b[F]" : "§c[?]";
                    String title =  friends + " §f" + playerName + " " + health + (int)entity.getHealth();

                    int width = Fonts.GREYCLIFF.getStringWidth(title) + 3;

                    GL11.glPushMatrix();
                    double scale = this.scale.current / 100;

                    GL11.glTranslatef((float)vec[0], (float)vec[1], 0.0F);

                    GL11.glScaled(scale, scale, scale);

                    ArrayList<ItemStack> items = new ArrayList<ItemStack>();
                    ItemStack heldStack = entity.inventory.getCurrentItem();

                    float x = 0;
                    int y = -20;

                    for(ItemStack stack : entity.getArmorInventoryList()) {
                        if(stack.getItem() instanceof AirItem) continue;
                        items.add(stack);
                    }

                    if(heldStack != null && !(heldStack.getItem() instanceof AirItem)) {
                        items.add(heldStack);
                    }

                    ItemStack offHand = entity.getHeldItemOffhand();

                    if(offHand != null && !(offHand.getItem() instanceof AirItem)) {
                        items.add(offHand);
                    }

                    x = -items.size() * 8;

                    for(ItemStack item : items) {
                        GL11.glPushMatrix();
                        if(armor.get()) {
                            drawItem(item, (int) x - 3, y, -200);
                        }
                        GL11.glPopMatrix();
                        x+=18;
                    }

                    GL11.glTranslatef((float)(-vec[0]) - (float)(width / 2), (float)(-vec[1]), 0.0F);
                    AbstractGui.fill(new MatrixStack(), (int)vec[0] - 1, (int)vec[1] - 1, (int)vec[0] + width + 1, (int)vec[1] + 10, Integer.MIN_VALUE);
                    Fonts.GREYCLIFF.drawStringWithShadow(new MatrixStack(), title, (float)vec[0], (float)vec[1] - 2, -1);
                    GL11.glPopMatrix();
                }

            }
        }
    }

    public void drawItem(ItemStack item, float x, int y, float size){
        GL11.glPushMatrix();
        GL11.glScalef(-0.01f * size, -0.01f * size, -0.01f * size);
        GL11.glPushMatrix();
        GlStateManager.disableLighting();
        GL11.glPopMatrix();
        GlStateManager.scaled(0.5, 0.5, 0.5);
        renderItem.renderItemIntoGUI(item, (int) x, y);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        String count = "";
        if (item.getCount() > 1)
        {
            count = item.getCount() + "";
        }
        renderItem.renderItemOverlayIntoGUI(mc.fontRenderer, item, (int) x, y, count);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableLighting();
        GL11.glPopMatrix();
        GL11.glDisable(GL11.GL_DEPTH_TEST);

    }
}

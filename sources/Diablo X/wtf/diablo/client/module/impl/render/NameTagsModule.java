package wtf.diablo.client.module.impl.render;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import wtf.diablo.client.event.impl.client.renderering.Render3DEvent;
import wtf.diablo.client.font.FontRepository;
import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.data.ModuleMetaData;
import wtf.diablo.client.setting.impl.BooleanSetting;
import wtf.diablo.client.setting.impl.ColorSetting;
import wtf.diablo.client.util.math.MathUtil;
import wtf.diablo.client.util.render.RenderUtil;

import java.awt.*;
import java.util.Objects;

@ModuleMetaData(name = "Name Tags", description = "Renders name tags above players.", category = ModuleCategoryEnum.RENDER)
public final class NameTagsModule extends AbstractModule {
    private final ColorSetting color = new ColorSetting("Color", new Color(17, 17, 17, 180));

    private final BooleanSetting showDistance = new BooleanSetting("Show Distance", true);
    private final BooleanSetting showHealth = new BooleanSetting("Show Health", true);
    private final BooleanSetting showArmor = new BooleanSetting("Show Armor", true);
    private final BooleanSetting displayName = new BooleanSetting("Display Name", true);

    public NameTagsModule() {
        this.registerSettings(color, showDistance, showHealth, showArmor, displayName);
    }

    @EventHandler
    public final Listener<Render3DEvent> render3DEventListener = e -> {
        try {
            for (EntityPlayer player : mc.theWorld.playerEntities) {
                if (!player.equals(mc.thePlayer) && player.isEntityAlive() && !player.isInvisible()) {
                    double x = MathUtil.interpolate(player.lastTickPosX, player.posX, e.getPartialTicks()) - mc.getRenderManager().getRenderPosX();
                    double y = MathUtil.interpolate(player.lastTickPosY, player.posY, e.getPartialTicks()) - mc.getRenderManager().getRenderPosY();
                    double z = MathUtil.interpolate(player.lastTickPosZ, player.posZ, e.getPartialTicks()) - mc.getRenderManager().getRenderPosZ();
                    this.renderNameTag(player, x, y + 0.05, z, e.getPartialTicks());
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    };

    private void renderNameTag(EntityPlayer player, double x, double y, double z, float delta) {
        double tempY = y;
        tempY += player.isSneaking() ? 0.65 : 0.85;
        Entity camera = mc.getRenderViewEntity();
        assert (camera != null);
        double originalPositionX = camera.posX;
        double originalPositionY = camera.posY;
        double originalPositionZ = camera.posZ;
        camera.posX = MathUtil.interpolate(camera.prevPosX, camera.posX, delta);
        camera.posY = MathUtil.interpolate(camera.prevPosY, camera.posY, delta);
        camera.posZ = MathUtil.interpolate(camera.prevPosZ, camera.posZ, delta);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.displayName.getValue() ? player.getDisplayName().getFormattedText() : ("§c") + player.getName());
        double health = Math.ceil(player.getHealth() + player.getAbsorptionAmount());

        EnumChatFormatting healthColour;
        if (health > 20) {
            healthColour = EnumChatFormatting.LIGHT_PURPLE;
        } else if (health > 15) {
            healthColour = EnumChatFormatting.GREEN;
        } else if (health > 10) {
            healthColour = EnumChatFormatting.YELLOW;
        } else if (health > 7) {
            healthColour = EnumChatFormatting.GOLD;
        } else if (health > 5) {
            healthColour = EnumChatFormatting.RED;
        } else {
            healthColour = EnumChatFormatting.DARK_RED;
        }

        stringBuilder.append(this.showHealth.getValue() ? healthColour + " " + health : "");
        stringBuilder.append(this.showDistance.getValue() ? " " + EnumChatFormatting.GRAY + "(" + (int) mc.thePlayer.getDistanceToEntity(player) + ")" : "");
        double distance = camera.getDistance(x + mc.getRenderManager().viewerPosX, y + mc.getRenderManager().viewerPosY, z + mc.getRenderManager().viewerPosZ);
        int width = (int) (FontRepository.SLKSCR12.getWidth(stringBuilder.toString()) / 2);
        double scale = (0.25 * (distance * 6)) / 800D;
        if (distance <= 14) {
            scale = 0.018D;
        }
        GlStateManager.pushMatrix();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.enablePolygonOffset();
        GlStateManager.doPolygonOffset(1.0f, -1500000.0f);
        GlStateManager.disableLighting();
        GlStateManager.translate((float) x, (float) tempY + 1.175f, (float) z);
        GlStateManager.rotate(-mc.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(mc.getRenderManager().playerViewX, mc.gameSettings.thirdPersonView == 2 ? -1.0f : 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(-scale, -scale, scale);
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.enableBlend();
        GlStateManager.disableBlend();

        if (showArmor.getValue()) {
            if(player.inventory.getCurrentItem() != null)
                RenderUtil.renderItemStack(player.inventory.getCurrentItem(), -50, -28);

            for (int length = mc.thePlayer.inventory.armorInventory.length, i = 0; i < length; ++i) {
                final ItemStack armourStack = mc.thePlayer.inventory.armorInventory[i];
                if (Objects.nonNull(armourStack)) {
                    RenderUtil.renderItemStack(player.inventory.armorItemInSlot(i), 30 - (i * 20), -28);
                }
            }
        }

        //Gui.drawRect(-width - 4, -11, width + 2, 0, color.getValue().getRGB());


        FontRepository.SLKSCR12.drawStringWithOutline(stringBuilder.toString(), -width - 1, -9.5f, -1, Color.BLACK.getRGB(), 3);

        //mc.fontRendererObj.drawStringWithShadow(stringBuilder.toString(), -width - 1, -9.5f, this.getDisplayColour(player));

        camera.posX = originalPositionX;
        camera.posY = originalPositionY;
        camera.posZ = originalPositionZ;
        GlStateManager.enableDepth();
        GlStateManager.disableBlend();
        GlStateManager.disablePolygonOffset();
        GlStateManager.doPolygonOffset(1.0f, 1500000.0f);
        GlStateManager.popMatrix();
    }

    private int getDisplayColour(final EntityPlayer player) {
        int colour = -new Color(197, 197, 197).getRGB();
        if (player.isInvisible()) {
            colour = -1113785;
        } else if (player.isSneaking()) {
            colour = -new Color(252, 234, 93).getRGB();
        }
        return colour;
    }
}
package fr.dog.module.impl.render;

import fr.dog.Dog;
import fr.dog.event.annotations.SubscribeEvent;
import fr.dog.event.impl.player.PlayerTickEvent;
import fr.dog.event.impl.render.Render2DEvent;
import fr.dog.module.Module;
import fr.dog.module.ModuleCategory;
import fr.dog.property.impl.NumberProperty;
import fr.dog.util.OverlayColors;
import fr.dog.util.PlayerStats;
import fr.dog.util.render.RenderUtil;
import fr.dog.util.render.font.Fonts;
import fr.dog.util.render.font.TTFFontRenderer;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static net.minecraft.client.gui.GuiPlayerTabOverlay.field_175252_a;

public class Overlay extends Module {
    private final NumberProperty widthProperty;
    private int backgroundWidth;

    public Overlay() {
        super("Overlay", ModuleCategory.RENDER);
        widthProperty = NumberProperty.newInstance("Width", 500f, 850f, 1200f, 50f);
        this.registerProperties(widthProperty);

        this.setDraggable(true);
        this.setX(30);
        this.setY(30);
        this.setHeight(10);
        this.setWidth(450);
        this.setWidth((float) widthProperty.getValue().intValue());


        this.backgroundWidth = 450;
    }

    public HashMap<UUID, PlayerStats> playerData = new HashMap<>();

    @SubscribeEvent
    private void onUpdate(PlayerTickEvent event) {
        NetHandlerPlayClient nethandlerplayclient = this.mc.thePlayer.sendQueue;
        List<NetworkPlayerInfo> list = field_175252_a.sortedCopy(nethandlerplayclient.getPlayerInfoMap());
        for (NetworkPlayerInfo networkplayerinfo : list) {
            UUID uuid = networkplayerinfo.getGameProfile().getId();
            String username = networkplayerinfo.getGameProfile().getName();

            if (!playerData.containsKey(uuid)) {
                PlayerStats stats = new PlayerStats(username, uuid);
                stats.reloadStats();
                this.playerData.put(uuid, stats);
            }
        }
    }

    @SubscribeEvent
    private void onRender2D(Render2DEvent event) {

        this.setWidth((float) widthProperty.getValue().intValue());
        this.backgroundWidth = (int) (this.getWidth() * 0.5);

        int baseX = this.getX();
        int nameX = baseX + 2;
        int starsX = baseX + (int) (this.getWidth() * 0.1);
        int fkdrX = baseX + (int) (this.getWidth() * 0.14);
        int kdrX = baseX + (int) (this.getWidth() * 0.18);
        int wlrX = baseX + (int) (this.getWidth() * 0.21);
        int fkX = baseX + (int) (this.getWidth() * 0.24);
        int fdX = baseX + (int) (this.getWidth() * 0.27);
        int kX = baseX + (int) (this.getWidth() * 0.30);
        int dX = baseX + (int) (this.getWidth() * 0.33);
        int wX = baseX + (int) (this.getWidth() * 0.36);
        int lX = baseX + (int) (this.getWidth() * 0.39);
        int indexX = baseX + (int) (this.getWidth() * 0.42);
        int tagX = baseX + (int) (this.getWidth() * 0.46);

        NetHandlerPlayClient nethandlerplayclient = this.mc.thePlayer.sendQueue;
        List<NetworkPlayerInfo> list = field_175252_a.sortedCopy(nethandlerplayclient.getPlayerInfoMap());

        TTFFontRenderer fr = Fonts.getRobotoMedium(18);

        RenderUtil.drawRect(this.getX(), this.getY(), backgroundWidth, this.getHeight(), new Color(0, 0, 0, 100));
        RenderUtil.drawRect(this.getX() - 1, this.getY(), 1, this.getHeight(), Dog.getInstance().getThemeManager().getCurrentTheme().getColor1());
        RenderUtil.drawRect(this.getX() + backgroundWidth - 1, this.getY(), 1, this.getHeight(), Dog.getInstance().getThemeManager().getCurrentTheme().getColor1());
        RenderUtil.drawRect(this.getX(), this.getY(), backgroundWidth, 1, Dog.getInstance().getThemeManager().getCurrentTheme().getColor1());
        RenderUtil.drawRect(this.getX(), this.getY() + this.getHeight(), backgroundWidth, 1, Dog.getInstance().getThemeManager().getCurrentTheme().getColor1());

        fr.drawString("Name", nameX, this.getY() + 11, Color.cyan.getRGB());
        fr.drawString("Stars", starsX, this.getY() + 11, Color.cyan.getRGB());
        fr.drawString("FKDR", fkdrX, this.getY() + 11, Color.cyan.getRGB());
        fr.drawString("KDR", kdrX, this.getY() + 11, Color.cyan.getRGB());
        fr.drawString("WLR", wlrX, this.getY() + 11, Color.cyan.getRGB());
        fr.drawString("FK", fkX, this.getY() + 11, Color.cyan.getRGB());
        fr.drawString("FD", fdX, this.getY() + 11, Color.cyan.getRGB());
        fr.drawString("K", kX, this.getY() + 11, Color.cyan.getRGB());
        fr.drawString("D", dX, this.getY() + 11, Color.cyan.getRGB());
        fr.drawString("W", wX, this.getY() + 11, Color.cyan.getRGB());
        fr.drawString("L", lX, this.getY() + 11, Color.cyan.getRGB());
        fr.drawString("Index", indexX, this.getY() + 11, Color.cyan.getRGB());
        fr.drawString("Tag", tagX, this.getY() + 11, Color.cyan.getRGB());

        int off_y = this.getY() + 22;
        for (NetworkPlayerInfo networkplayerinfo : list) {
            if (this.playerData.containsKey(networkplayerinfo.getGameProfile().getId())) {
                PlayerStats stats = this.playerData.get(networkplayerinfo.getGameProfile().getId());

                DecimalFormat format = new DecimalFormat("#.##");
                
                fr.drawString(stats.getUsername(), nameX, off_y, Color.WHITE.getRGB());
                fr.drawString(stats.getStars() + "", starsX, off_y, Color.gray.getRGB());
                fr.drawString(format.format(stats.getFkdr()), fkdrX, off_y, OverlayColors.getColorFKDR(stats.getFkdr()).getRGB());
                fr.drawString(format.format(stats.getKdr()), kdrX, off_y, OverlayColors.getColorKDR(stats.getKdr()).getRGB());
                fr.drawString(format.format(stats.getWlr()), wlrX, off_y, OverlayColors.getColorWLR(stats.getWlr()).getRGB());
                fr.drawString(format.format(stats.getFk()), fkX, off_y, OverlayColors.getKills(stats.getFk()).getRGB());
                fr.drawString(format.format(stats.getFd()), fdX, off_y, OverlayColors.getKills(stats.getFd()).getRGB());
                fr.drawString(format.format(stats.getK()), kX, off_y, OverlayColors.getKills(stats.getK()).getRGB());
                fr.drawString(format.format(stats.getD()), dX, off_y, OverlayColors.getKills(stats.getD()).getRGB());
                fr.drawString(format.format(stats.getW()), wX, off_y, OverlayColors.getKills(stats.getW()).getRGB());
                fr.drawString(format.format(stats.getL()), lX, off_y, OverlayColors.getWins(stats.getL()).getRGB());
                fr.drawString(format.format(stats.getIndex()), indexX, off_y, OverlayColors.getWins(stats.getL()).getRGB());
                fr.drawString(stats.getTags(), tagX, off_y, Color.WHITE.getRGB());

                off_y += 11;
            }
        }

        this.setHeight(off_y - (this.getY()));
    }
}

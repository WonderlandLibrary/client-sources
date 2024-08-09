package dev.darkmoon.client.module.impl.render;

import com.darkmagician6.eventapi.EventTarget;
import com.mojang.realmsclient.gui.ChatFormatting;
import dev.darkmoon.client.manager.staff.Staff;
import dev.darkmoon.client.DarkMoon;
import dev.darkmoon.client.event.player.EventUpdate;
import dev.darkmoon.client.event.render.EventRender2D;
import dev.darkmoon.client.manager.dragging.DragManager;
import dev.darkmoon.client.manager.dragging.Draggable;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import dev.darkmoon.client.utility.math.BlurUtility;
import dev.darkmoon.client.utility.render.*;
import dev.darkmoon.client.utility.render.animation.AnimationMath;
import dev.darkmoon.client.utility.render.font.Fonts;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@ModuleAnnotation(name = "Staff List", category = Category.RENDER)
public class StaffList extends Module {
    private static List<Staff> allStaff = new ArrayList();
    private final Draggable staffListDraggable = DragManager.create(this, "Staff List", 200, 100);
    public float animHeight = 0.0F;
    public long time;
    public float animWidth = 0.0F;
    public Boolean staff = false;
    Color color11 = DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[0];
    Color color22 = DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[1];
    Color gradientColor11 = ColorUtility2.interpolateColorsBackAndForth(4, 0, color11, color22, false);
    Color gradientColor22 = ColorUtility2.interpolateColorsBackAndForth(4, 90, color11, color22, false);
    public StaffList() {
        time = System.currentTimeMillis();
    }

    public void onEnable() {
        super.onEnable();
        updateList();
    }

    @EventTarget
    public void onUpdate(EventUpdate eventUpdate) {
        if (mc.player.ticksExisted % 10 == 0) {
            updateList();
        }

    }

    @EventTarget
    public void onRender2D(EventRender2D event) {
        DarkMoon.getInstance().getScaleMath().pushScale();
        List<Staff> sortedStaff = (List) allStaff.stream().sorted(Comparator.comparing((staffx) -> {
            return Fonts.tenacityBold15.getStringWidth(staffx.getText());
        }, Comparator.reverseOrder())).collect(Collectors.toList());
        int glowColor = DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[0].getRGB();
        int glowColor2 = DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[1].getRGB();
        int var3 = 105;
        int var4 = 0;
        Iterator var8;
        Staff staff;
        if (!sortedStaff.isEmpty()) {
            this.staff = false;
                var3 = (Integer) sortedStaff.stream().max(Comparator.comparingInt((staffx) -> {
                    return Fonts.tenacityBold15.getStringWidth(staffx.getText());
                })).map((staffx) -> {
                    return Fonts.tenacityBold15.getStringWidth(staffx.getText());
                }).get() + 13;
            if (var3 < 110) {
                var3 = 110;
            }

            var4 = 0;

            for(var8 = sortedStaff.iterator(); var8.hasNext(); var4 += 11) {
                staff = (Staff) var8.next();
            }
            } else {
            var4 = 14;
            this.staff = true;
        }

        this.animHeight = AnimationMath.fast(this.animHeight, (float)var4, 9.0F);
        this.animWidth = AnimationMath.fast(this.animWidth, (float)var3, 9.0F);
        this.staffListDraggable.setWidth((float)var3);
        this.staffListDraggable.setHeight((float)(19 + var4));
        Color color = DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[0];
        Color color2 = DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[1];
        int var9 = DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[0].getRGB();
        int var10 = DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[1].getRGB();
        RenderUtility.drawDarkMoonShader((float)this.staffListDraggable.getX(), (float)this.staffListDraggable.getY(), this.animWidth, 18.5F + this.animHeight, 7.0F);
        Fonts.tenacityBold20.drawCenteredString("staff list", (float)this.staffListDraggable.getX() + this.animWidth / 2.0F, (float)this.staffListDraggable.getY() + 6.5F, -1);
        if (this.staff.booleanValue()) {
            Fonts.tenacityBold20.drawCenteredString("staff's is empty.", (float)this.staffListDraggable.getX() + this.animWidth / 2.0F, (float) (this.staffListDraggable.getY() + 20), Color.WHITE.getRGB());
        }
        SmartScissor.push();
        SmartScissor.setFromComponentCoordinates((double)this.staffListDraggable.getX(), (double)this.staffListDraggable.getY(), (double)(var3 + 40), (double)(19.0F + this.animHeight));
        var4 = 0;

        for(var8 = sortedStaff.iterator(); var8.hasNext(); var4 += 11) {
            staff = (Staff)var8.next();
            StencilUtility.uninitStencilBuffer();
          //  RenderUtility.drawImage(new ResourceLocation(staff.getStaffName()), (float)(this.staffListDraggable.getX() + 4) + this.animWidth - (float)var3 + Fonts.tenacityBold14.getStringWidth(staff.getText() + 3), (float)(this.staffListDraggable.getY() + 21 + var4), 1, 1);
            Fonts.tenacityBold14.drawString(staff.getText(), (float)(this.staffListDraggable.getX() + 4) + this.animWidth - (float)var3, (float)(this.staffListDraggable.getY() + 21 + var4), -1);
            Fonts.iconStaff.drawString(staff.getStaffPrefix(), (float)(this.staffListDraggable.getX() + 3) + this.animWidth - (float)var3 + Fonts.tenacityBold14.getStringWidth(staff.getText() + 3), (float)(this.staffListDraggable.getY() + 22 + var4), -1);
            StencilUtility.uninitStencilBuffer();
        }

        SmartScissor.unset();
        SmartScissor.pop();
        DarkMoon.getInstance().getScaleMath().popScale();
    }


    public static void updateList() {
        allStaff = getOnlineStaff();
        allStaff.addAll(getVanishedStaff());
    }
    public static boolean isStaff(String prefix) {
        return prefix.contains("helper") || prefix.contains("moder") || prefix.contains("admin") ||
                prefix.contains("owner") || prefix.contains("curator") || prefix.contains("хелпер") ||
                prefix.contains("модер") || prefix.contains("админ") || prefix.contains("куратор") ||
                prefix.contains("сотрудник") || prefix.contains("стажёр") || prefix.contains("стажер") ||
                prefix.contains("youtube") || prefix.equals("yt");
    }

    private static ArrayList<Staff> getOnlineStaff() {
        if (mc.player == null) return new ArrayList<>();

        ArrayList<Staff> list = new ArrayList<>();
        for (NetworkPlayerInfo networkPlayerInfo : mc.player.connection.getPlayerInfoMap()) {
            ScorePlayerTeam scorePlayerTeam = networkPlayerInfo.getPlayerTeam();
            if (scorePlayerTeam != null && (isStaff(ChatFormatting.stripFormatting(scorePlayerTeam.getPrefix()).toLowerCase()) || DarkMoon.getInstance().getStaffManager().getStaff().contains(networkPlayerInfo.getGameProfile().getName()))) {
                list.add(new Staff(networkPlayerInfo.getGameProfile().getName(), scorePlayerTeam.getPrefix(), false));
            }
        }
        return list;
    }

    public static List<Staff> getVanishedStaff() {
        if (mc.world == null) return new ArrayList<>();

        List<Staff> list = new ArrayList<>();
        for (ScorePlayerTeam scorePlayerTeam : mc.world.getScoreboard().getTeams()) {
            if (ChatFormatting.stripFormatting(scorePlayerTeam.getPrefix()).length() == 0) continue;
            String username = Arrays.toString(scorePlayerTeam.getMembershipCollection().toArray()).replace("[", "").replace("]", "");
            if (getOnlineStaff().stream().map(Staff::getName).collect(Collectors.toList()).contains(username)) continue;
            if (ChatFormatting.stripFormatting(username).isEmpty()) continue;
            if (isStaff(ChatFormatting.stripFormatting(scorePlayerTeam.getPrefix()).toLowerCase()) || DarkMoon.getInstance().getStaffManager().getStaff().contains(username))
                list.add(new Staff(username, scorePlayerTeam.getPrefix(), true));
        }
        return list;
    }
}
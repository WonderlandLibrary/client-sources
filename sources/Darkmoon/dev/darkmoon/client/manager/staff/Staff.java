package dev.darkmoon.client.manager.staff;

import dev.darkmoon.client.module.impl.render.StaffList;
import dev.darkmoon.client.utility.Utility;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.GameType;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

@Getter
@AllArgsConstructor
public class Staff implements Utility {
    private String name, prefix;
    private boolean isVanished;

    public String getStaffPrefix() {
        EntityPlayer player = mc.world.getPlayerEntityByName(name);
        if (player != null) {
            return "D ";
        } else {
            NetworkPlayerInfo networkPlayerInfo = mc.player.connection.getPlayerInfo(name);

            if (isVanished) {
                return TextFormatting.RED + "C " + TextFormatting.RESET;
            } else if (networkPlayerInfo != null && networkPlayerInfo.getGameType().equals(GameType.SPECTATOR)) {
                return TextFormatting.AQUA + "D ";
            } else {
                return "D ";
            }
        }
    }
    public String getText() {
        return prefix + name;
    }
}

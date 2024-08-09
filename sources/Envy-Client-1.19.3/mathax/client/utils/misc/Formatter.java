package mathax.client.utils.misc;

import mathax.client.utils.Utils;
import mathax.client.utils.player.PlayerUtils;
import mathax.client.utils.render.color.Color;
import mathax.client.utils.render.color.SettingColor;
import net.minecraft.SharedConstants;

import java.io.File;

import static mathax.client.MatHax.mc;

public class Formatter {




    //public static Random random = new Random();
    public static int random(int min, int max) { return min + (int) (Math.random() * ((max - min) + 1)); }
    public static double random(double min, double max) {
        return Math.random() * (max - min) + min;
    }
}

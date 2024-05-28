package arsenic.utils.render;

import arsenic.utils.java.UtilityClass;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class ScissorUtils extends UtilityClass {

    /*rahhhhhhhhhhh
    ⠀⠀⠀⠀⠀⠀⠀⠀        ⠀⢀⣠⣄⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
            ⠀⠀⠀⠀⠀⠀⠀⠀⢰⡟⠉⠉⠙⣦⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
            ⠀⠀⠀⠀⠀⠀⠀⠀⢸⡇⠀⠀⠀⠸⣧⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
            ⠀⠀⠀⠀⠀⠀⠀⠀⠈⣿⡄⠀⠀⢠⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
            ⠀⢀⣤⡶⠶⢶⣤⣄⣀⠘⠛⠶⣴⠿⠃⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
            ⠀⣾⡇⠀⠀⠀⠈⠙⢿⣿⣷⣶⣤⣄⣀⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
            ⠀⠘⢷⣄⡀⠀⠀⢀⣸⡟⠉⠙⠻⣿⣿⣿⣷⣶⣤⣄⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀
            ⠀⠀⠀⠉⠛⠛⠛⠛⠉⠀⠀⢸⣦⣄⡉⠛⠿⣿⣿⣿⣿⣿⣶⣤⣀⠀⠀⠀⠀⠀
            ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢿⣿⣿⣆⠀⠀⠙⠻⢿⣿⣿⣿⣿⣷⣄⠀⠀⠀
            ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⣿⣿⣿⣦⠀⠀⠀⠀⠈⠙⠻⢿⣿⣿⣷⡀⠀
            ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠸⣿⣿⣿⣧⡀⠀⠀⠀⠀⠀⠀⠉⠛⠿⣷⠀
            ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠹⣿⣿⣿⣷⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
            ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠙⣿⣿⣿⣧⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀
            ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠻⣿⣿⣷⡀⠀⠀⠀⠀⠀⠀⠀⠀
            ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠛⠓⠀⠀⠀⠀⠀⠀⠀⠀
        he will cut your balls off
     */
    private static final List<int[]> rahhh = new ArrayList<>();

    static {
        resetScissor();
    }
    public static void subScissor(int x, int y, int width, int height) {
        subScissor(x, y, width, height, new ScaledResolution(mc).getScaleFactor());
    }

    public static void subScissor(int x1, int y1, int x2, int y2, int scale) {
        GL11.glPushAttrib(GL11.GL_SCISSOR_BIT);
        int[] rahhhh = rahhh.get(rahhh.size() - 1);
        x1 = Math.max(x1, rahhhh[0]);
        y1 = Math.max(y1, rahhhh[1]);
        x2 = Math.min(x2, rahhhh[2]);
        y2 = Math.min(y2, rahhhh[3]);
        int[] rah = new int[] {x1 * scale, mc.displayHeight - (y2 * scale), (x2 - x1) * scale, (y2 - y1) * scale};
        //snip snip
        for(int i = 0; i <= 3; i++) {
            rah[i] = Math.max(rah[i], 0);
        }
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glScissor(rah[0], rah[1], rah[2], rah[3]);
        rahhh.add(new int[] {x1,y1,x2,y2});
    }

    public static void endSubScissor() {
        rahhh.remove(rahhh.size() - 1);
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        GL11.glPopAttrib();
    }

    public static void resetScissor() {
        rahhh.clear();
        rahhh.add(new int[] {0,0,Integer.MAX_VALUE, Integer.MAX_VALUE});
    }


}

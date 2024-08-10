package cc.slack.utils.other;

import cc.slack.utils.client.IMinecraft;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;

public class ResolutionUtil implements IMinecraft {

    public static ResolutionUtil INSTANCE;

    @Getter
    @Setter
    public int width, height, scale;

    public ResolutionUtil (Minecraft mc) {
        width = mc.displayWidth;
        height = mc.displayHeight;
        scale = 1;
        boolean flag = mc.isUnicode();
        int i = mc.gameSettings.guiScale;

        if (i == 0)
        {
            i = 1000;
        }

        while (this.scale < i && this.width / (this.scale + 1) >= 320 && this.height / (this.scale + 1) >= 240)
        {
            ++this.scale;
        }

        if (flag && this.scale % 2 != 0 && this.scale != 1)
        {
            --scale;
        }

        this.width = this.width / this.scale;
        this.height = this.width / this.scale;
    }

    public void update(Minecraft mc) {
        width = mc.displayWidth;
        height = mc.displayHeight;
        scale = 1;
        boolean flag = mc.isUnicode();
        int i = mc.gameSettings.guiScale;

        if (i == 0)
            i = 1000;

        while (scale < i && width / (scale + 1) >= 320 && height / (scale + 1) >= 240) {
            ++scale;
        }

        if (flag && scale % 2 != 0 && scale != 1) {
            --scale;
        }

        double scaledWidthD = (double) width / (double) scale;
        double scaledHeightD = (double) height / (double) scale;
        width = MathHelper.ceiling_double_int(scaledWidthD);
        height = MathHelper.ceiling_double_int(scaledHeightD);
    }

    public static ResolutionUtil getResolution() {
        if(INSTANCE == null) {
            INSTANCE = new ResolutionUtil(mc.getMinecraft());
        }
        INSTANCE.update(Minecraft.getMinecraft());
        return INSTANCE;
    }

}

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.GL11
 */
package skizzle.ui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import skizzle.util.AnimationHelper;
import skizzle.util.Colors;
import skizzle.util.RandomHelper;
import skizzle.util.RenderUtil;
import skizzle.util.Timer;

public class Particle {
    public double xOffset;
    public double x;
    public double lastYOffset;
    public double yOffset;
    public boolean invisibleLayer;
    public AnimationHelper alphaAnimation = new AnimationHelper();
    public boolean drawnLine;
    public double size;
    public double layer;
    public int color;
    public static Timer renderTimer = new Timer();
    public double lastXOffset;
    public double y;

    public Particle(double Nigga, double Nigga2, double Nigga3, int Nigga4, double Nigga5, boolean Nigga6) {
        Particle Nigga7;
        Nigga7.x = Nigga;
        Nigga7.y = Nigga2;
        Nigga7.size = Nigga3;
        Nigga7.color = Nigga4;
        Nigga7.layer = Nigga5;
        Nigga7.invisibleLayer = Nigga6;
        Nigga7.alphaAnimation.stage = 0.0;
    }

    public static List<Particle> generateNiceParticles() {
        int Nigga;
        ArrayList<Particle> Nigga2 = new ArrayList<Particle>();
        Minecraft Nigga3 = Minecraft.getMinecraft();
        for (Nigga = 0; Nigga < 50; ++Nigga) {
            Nigga2.add(new Particle(RandomHelper.randomDouble(-100.0, (float)Nigga3.displayWidth / Float.intBitsToFloat(1.05101133E9f ^ 0x7EA52903) + Float.intBitsToFloat(1.01035834E9f ^ 0x7EF0D82B)), RandomHelper.randomDouble(-100.0, (float)Nigga3.displayHeight / Float.intBitsToFloat(1.05007181E9f ^ 0x7E96D305) + Float.intBitsToFloat(1.0693577E9f ^ 0x7D751A6F)), RandomHelper.randomDouble(1.0, 4.0), 0xFFFFFF, 2.0, false));
        }
        for (Nigga = 0; Nigga < 100; ++Nigga) {
            Nigga2.add(new Particle(RandomHelper.randomDouble(-100.0, (float)Nigga3.displayWidth / Float.intBitsToFloat(1.05405069E9f ^ 0x7ED3898B) + Float.intBitsToFloat(1.01351987E9f ^ 0x7EA115EB)), RandomHelper.randomDouble(-100.0, (float)Nigga3.displayHeight / Float.intBitsToFloat(1.06274317E9f ^ 0x7F582CA0) + Float.intBitsToFloat(1.01381555E9f ^ 0x7EA59901)), RandomHelper.randomDouble(1.0, 4.0), 0xFFFFFF, 5.0, false));
        }
        for (Nigga = 0; Nigga < 25; ++Nigga) {
            Nigga2.add(new Particle(RandomHelper.randomDouble(-100.0, (float)Nigga3.displayWidth / Float.intBitsToFloat(1.05937018E9f ^ 0x7F24B4B6) + Float.intBitsToFloat(1.03370048E9f ^ 0x7F550495)), RandomHelper.randomDouble(-100.0, (float)Nigga3.displayHeight / Float.intBitsToFloat(1.063008E9f ^ 0x7F5C36E0) + Float.intBitsToFloat(1.01823456E9f ^ 0x7E7906BF)), RandomHelper.randomDouble(1.0, 4.0), 0xFFFFFF, 1.0, false));
        }
        return Nigga2;
    }

    public double lambda$0(Object Nigga) {
        Particle Nigga2;
        return Math.sqrt(Math.pow(((Particle)Nigga).x + Nigga2.xOffset - Nigga2.x, 2.0) + Math.pow(((Particle)Nigga).y + Nigga2.yOffset - Nigga2.y, 2.0));
    }

    public static List<Particle> normalDraw(List<Particle> Nigga) {
        Minecraft Nigga2 = Minecraft.getMinecraft();
        try {
            if (RandomHelper.randomChance(0.0)) {
                Nigga.add(new Particle(RandomHelper.randomDouble(0.0, (float)Nigga2.displayWidth / Float.intBitsToFloat(1.03784134E9f ^ 0x7DDC33BF)) + 20.0, RandomHelper.randomDouble(0.0, (float)Nigga2.displayHeight / Float.intBitsToFloat(1.06206163E9f ^ 0x7F4DC643)) + 20.0, 1.0, 0xFFFFFF, RandomHelper.randomInt(1, 4), false));
            }
            for (Particle Nigga3 : Nigga) {
                Nigga3.draw((ArrayList)Nigga);
                if (!(Nigga3.y > (double)(Nigga2.displayHeight / 2 + 20))) continue;
                Nigga3.x = RandomHelper.randomDouble(0.0, (float)Nigga2.displayWidth / Float.intBitsToFloat(1.05639821E9f ^ 0x7EF75B99)) + 20.0;
                Nigga3.y -= (double)(Nigga2.displayHeight / 2);
            }
            if (Nigga.size() > 200) {
                Nigga.remove(0);
            }
        }
        catch (Exception Nigga4) {
            Nigga4.printStackTrace();
        }
        return Nigga;
    }

    public void draw(ArrayList Nigga) {
        Particle Nigga2;
        Particle Nigga3;
        ArrayList Nigga4 = (ArrayList)Nigga.clone();
        double Nigga5 = (float)Mouse.getX() / Float.intBitsToFloat(1.0566633E9f ^ 0x7EFB66F1);
        double Nigga6 = (float)(Minecraft.getMinecraft().displayHeight - Mouse.getY()) / Float.intBitsToFloat(1.05842253E9f ^ 0x7F163F02);
        double Nigga7 = 1.0;
        double Nigga8 = 6.0;
        if (RandomHelper.randomChance(0.0)) {
            Nigga8 = -6.0;
        }
        Nigga3.x += RandomHelper.randomDouble(0.0, Nigga7 + 1.0) / Nigga8;
        Nigga3.y += RandomHelper.randomDouble(0.0, Nigga7 + 1.0) / Nigga8 + 0.0;
        Nigga3.lastXOffset = Nigga3.xOffset;
        Nigga3.lastYOffset = Nigga3.yOffset;
        Nigga3.xOffset = Nigga5 / -50.0 * (Nigga3.invisibleLayer ? 1.0 : Nigga3.layer);
        Nigga3.yOffset = Nigga6 / -50.0 * (Nigga3.invisibleLayer ? 1.0 : Nigga3.layer);
        Nigga3.xOffset = Nigga3.lastXOffset + (Nigga3.xOffset - Nigga3.lastXOffset) * 0.0;
        Nigga3.yOffset = Nigga3.lastYOffset + (Nigga3.yOffset - Nigga3.lastYOffset) * 0.0;
        double Nigga9 = Nigga3.x + Nigga3.xOffset;
        double Nigga10 = Nigga3.y + Nigga3.yOffset;
        if (Nigga3.alphaAnimation.direction.equals((Object)AnimationHelper.Directions.IN) && Nigga3.alphaAnimation.stage < 255.0) {
            Nigga3.alphaAnimation.stage += RandomHelper.randomDouble(0.0, 3.0);
        }
        if (Nigga3.alphaAnimation.direction.equals((Object)AnimationHelper.Directions.OUT) && Nigga3.alphaAnimation.stage > 0.0) {
            Nigga3.alphaAnimation.stage -= RandomHelper.randomDouble(0.0, 10.0);
        }
        if (Nigga3.alphaAnimation.stage < 1.0) {
            Nigga3.alphaAnimation.stage = 0.0;
            Nigga3.alphaAnimation.direction = AnimationHelper.Directions.IN;
        }
        if (Nigga3.alphaAnimation.stage > 254.0) {
            Nigga3.alphaAnimation.stage = 255.0;
            Nigga3.alphaAnimation.direction = AnimationHelper.Directions.OUT;
        }
        Color Nigga11 = new Color(Nigga3.color);
        Nigga3.color = Colors.getColor(Nigga11.getRed(), Nigga11.getGreen(), Nigga11.getBlue(), (int)Nigga3.alphaAnimation.stage);
        Nigga4.sort(Comparator.comparingDouble(Nigga3::lambda$0));
        for (Object Nigga12 : Nigga4) {
            Nigga2 = (Particle)Nigga12;
            double Nigga13 = Nigga2.xOffset;
            double Nigga14 = Nigga2.yOffset;
            if (!Nigga2.drawnLine && Math.abs(Nigga2.layer - Nigga3.layer) <= 1.0 && Math.abs(Nigga2.x + Nigga13 - Nigga9) < 100.0 && Math.abs(Nigga2.y + Nigga14 - Nigga10) < 100.0) {
                double Nigga15 = 10.0 / Math.sqrt(Math.pow(Nigga2.x + Nigga13 - Nigga3.x, 2.0) + Math.pow(Nigga2.y + Nigga14 - Nigga3.y, 2.0)) - Math.abs(Nigga2.layer - Nigga3.layer) * 2.0;
                if (Nigga15 * 255.0 > 170.0) {
                    Nigga15 = 0.0;
                }
                if (Nigga15 * 255.0 < 40.0) {
                    Nigga15 = 0.0;
                }
                GL11.glColor4d((double)1.0, (double)1.0, (double)1.0, (double)Nigga15);
                GL11.glEnable((int)3042);
                GL11.glEnable((int)2848);
                GL11.glLineWidth((float)Float.intBitsToFloat(1.05224294E9f ^ 0x7EB7F3F3));
                GlStateManager.disableTexture2D();
                GL11.glBegin((int)3);
                GL11.glVertex2d((double)(Nigga2.x + Nigga13), (double)(Nigga2.y + Nigga14));
                GL11.glVertex2d((double)Nigga9, (double)Nigga10);
                GL11.glEnd();
                GL11.glDisable((int)3042);
                GlStateManager.enableTexture2D();
                Nigga2.drawnLine = true;
                Nigga3.drawnLine = true;
            }
            if (!(Math.abs(Nigga3.x - Nigga2.x) < 30.0) || !(Math.abs(Nigga3.y - Nigga2.y) < 30.0)) continue;
            Nigga3.x += (Nigga3.x - Nigga2.x) / 100.0;
            Nigga3.y += (Nigga3.y - Nigga2.y) / 100.0;
        }
        for (Object Nigga12 : Nigga4) {
            Nigga2 = (Particle)Nigga12;
            Nigga2.drawnLine = false;
        }
        if (Math.abs(Nigga5 - Nigga9) < 600.0 && Math.abs(Nigga6 - Nigga10) < 600.0) {
            GL11.glColor4d((double)1.0, (double)1.0, (double)1.0, (double)(20.0 / Math.sqrt(Math.pow(Nigga5 - Nigga3.x, 2.0) + Math.pow(Nigga6 - Nigga3.y, 2.0)) - (255.0 - Nigga3.alphaAnimation.stage) / 1000.0));
            GL11.glEnable((int)3042);
            GL11.glEnable((int)2848);
            GL11.glLineWidth((float)Float.intBitsToFloat(1.04331302E9f ^ 0x7E2FB16B));
            GlStateManager.disableTexture2D();
            GL11.glBegin((int)3);
            GL11.glVertex2d((double)Nigga5, (double)Nigga6);
            GL11.glVertex2d((double)Nigga9, (double)Nigga10);
            GL11.glEnd();
            GL11.glDisable((int)3042);
            GlStateManager.enableTexture2D();
        }
        GL11.glLineWidth((float)Float.intBitsToFloat(1.0891255E9f ^ 0x7F6ABC61));
        RenderUtil.draw2DCircle(Nigga9, Nigga10, Nigga3.size / 2.0, 22.5, true, Nigga3.color, 360);
    }
}


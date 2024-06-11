package Hydro.module.modules.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockPos;

import org.lwjgl.opengl.GL11;

import Hydro.event.Event;
import Hydro.event.events.EventRender3D;
import Hydro.event.events.EventUpdate;
import Hydro.module.Category;
import Hydro.module.Module;
import Hydro.module.modules.combat.Aura;
import Hydro.util.font.FontUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class ToxicFX extends Module {

    private java.util.ArrayList<ToxicHit> hits = new ArrayList<ToxicHit>();
    private float lastHealth;
    private EntityLivingBase lastTarget = null;

    public static float particleHue = 0.8f;
    public static boolean rainbowParticles = false;

    static ArrayList<String> msgs = new ArrayList<>();

    public ToxicFX() {
        super("ToxicFX", 0, true, Category.RENDER, "Draws toxic messages around the aura target");
        msgs.add("EZ");
        msgs.add("Jew down");
        msgs.add("What a nigger");
        msgs.add("Nigger down");
        msgs.add("EZZZZ");
        msgs.add("HARAM");
    }

    @Override
    public void onEvent(Event e) {
        if(e instanceof EventUpdate) {
            if (Aura.target == null) {
                this.lastHealth = 20;
                lastTarget = null;
                return;
            }
            if (this.lastTarget == null || Aura.target != this.lastTarget) {
                this.lastTarget = Aura.target;
                this.lastHealth = Aura.target.getHealth();
                return;
            }
            if (Aura.target.getHealth() != this.lastHealth) {
                if (Aura.target.getHealth() < this.lastHealth) {
                    this.hits.add(new ToxicHit(Aura.target.getPosition().add(ThreadLocalRandom.current().nextDouble(-1.5, 1.5), ThreadLocalRandom.current().nextDouble(2, 2.5), ThreadLocalRandom.current().nextDouble(-1.5, 1.5))));
                }
                this.lastHealth = Aura.target.getHealth();
            }
        }

        if(e instanceof EventRender3D) {
            try {
                for (ToxicHit h : hits) {
                    if (h.isFinished()) {
                        hits.remove(h);
                    } else {
                        h.onRender();
                    }
                }
            } catch (Exception err) {
            }
        }
    }

}

class ToxicHit {
    protected static Minecraft mc = Minecraft.getMinecraft();
    private long startTime = System.currentTimeMillis();
    private BlockPos pos;
    private double healthVal;
    private long maxTime = 1000;
    private Random randomGenerator = new Random();

    public ToxicHit(BlockPos pos) {
        this.startTime = System.currentTimeMillis();
        this.pos = pos;
    }

    public static int getRainbow(int speed, int offset) {
        float hue = (float) ((System.currentTimeMillis() * 2 + offset / 2) % speed * 2);
        hue /= speed;
        return Color.getHSBColor(hue, 1.0F, 1.0F).getRGB();
    }

    public void onRender() {
        final double x = this.pos.getX() + (this.pos.getX() - this.pos.getX()) * mc.timer.renderPartialTicks - RenderManager.viewerPosX + 1.5;
        final double y = this.pos.getY() + (this.pos.getY() - this.pos.getY()) * mc.timer.renderPartialTicks - RenderManager.viewerPosY;
        final double z = this.pos.getZ() + (this.pos.getZ() - this.pos.getZ()) * mc.timer.renderPartialTicks - RenderManager.viewerPosZ;

        final float var10001 = (mc.gameSettings.thirdPersonView == 2) ? -1.0f : 1.0f;
        final double size = (2.5);
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        Minecraft.getMinecraft().entityRenderer.setupCameraTransform(mc.timer.renderPartialTicks, 0);
        GL11.glTranslated(x, y, z);
        GL11.glNormal3f(0.0f, 1.0f, 0.0f);
        GL11.glRotatef(-RenderManager.playerViewY, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(mc.getRenderManager().playerViewX, var10001, 0.0f, 0.0f);
        GL11.glScaled(-0.01666666753590107 * size, -0.01666666753590107 * size, 0.01666666753590107 * size);
        float sizePercentage;
        long timeLeft = (this.startTime + this.maxTime) - System.currentTimeMillis();
        float yPercentage = 0;
        if (timeLeft < 75) {
            sizePercentage = Math.min((float) timeLeft / 75F, 1F);
            yPercentage = Math.min((float) timeLeft / 75F, 1F);
        } else {
            sizePercentage = Math.min((float) (System.currentTimeMillis() - this.startTime) / 300F, 1F);
            yPercentage = Math.min((float) (System.currentTimeMillis() - this.startTime) / 600F, 1F);
        }
        GlStateManager.scale(0.8 * sizePercentage, 0.8 * sizePercentage, 0.8 * sizePercentage);
        Gui.drawRect(-100, -100, 100, 100, new Color(255, 0, 0, 0).getRGB());
        Color c = Color.getHSBColor(DamageParticles.particleHue, 1.0F, 1.0F);
        if (DamageParticles.rainbowParticles) {
            int index = randomGenerator.nextInt(ToxicFX.msgs.size());

            FontUtil.arrayList.drawStringWithShadow(ToxicFX.msgs.get(index), 0, -(yPercentage * 1), getRainbow(6000, -15));
        } else{
            int index = randomGenerator.nextInt(ToxicFX.msgs.size());
            FontUtil.arrayList.drawStringWithShadow(ToxicFX.msgs.get(index), 0, -(yPercentage * 1), c.getRGB());
        }
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glDisable(2848);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
        GlStateManager.color(1.0f, 1.0f, 1.0f);
        GlStateManager.popMatrix();
    }

    public boolean isFinished() {
        return System.currentTimeMillis() - this.startTime >= maxTime;
    }


}


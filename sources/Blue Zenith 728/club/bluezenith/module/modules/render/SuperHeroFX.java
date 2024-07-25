package club.bluezenith.module.modules.render;

import club.bluezenith.events.EventType;
import club.bluezenith.events.Listener;
import club.bluezenith.events.impl.AttackEvent;
import club.bluezenith.events.impl.Render2DEvent;
import club.bluezenith.events.impl.UpdateEvent;
import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.module.modules.render.hud.HUD;
import club.bluezenith.module.value.types.FloatValue;
import club.bluezenith.module.value.types.IntegerValue;
import club.bluezenith.util.font.FontUtil;
import club.bluezenith.util.font.TFontRenderer;
import club.bluezenith.util.math.MathUtil;
import club.bluezenith.util.render.ColorUtil;
import club.bluezenith.util.render.RenderUtil;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

import java.awt.*;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public class SuperHeroFX extends Module {

    private final FloatValue scale = new FloatValue("Scale", 0.7F, 0.1F, 1.2F, 0.05F).setIndex(-1);
    private final FloatValue randSpawnMin = new FloatValue("Rand spawn min", 0f, 0, 1, 0.01f).setIndex(1);
    private final FloatValue randSpawnMax = new FloatValue("Rand spawn max", 0.5f, 0, 1, 0.01f).setIndex(2);

    private final FloatValue randVeloYMin = new FloatValue("Rand velo Y min", 0.02f, -0.75f, 0.75f, 0.01f).setIndex(3);
    private final FloatValue randVeloYMax = new FloatValue("Rand velo Y max", 0.2f, -0.75f, 0.75f, 0.01f).setIndex(4);

    private final FloatValue randVeloXZMin = new FloatValue("Rand velo XZ min", 0f, 0, 1, 0.01f).setIndex(5);
    private final FloatValue randVeloXZMax = new FloatValue("Rand velo XZ max", 0.15f, 0, 1, 0.01f).setIndex(6);

    private final FloatValue spawnHeight = new FloatValue("Spawn height", 0.75f, 0, 1, 0.01f).setIndex(7);

    private final IntegerValue minAge = new IntegerValue("Min age", 1000, 0, 5000, 1).setIndex(8);
    private final IntegerValue maxAge = new IntegerValue("Max age", 3000, 0, 5000, 1).setIndex(9);

    private final FloatValue gravity = new FloatValue("Gravity", -0.2f, -0.75f, 0.75f, 0.01f).setIndex(9);

    private final IntegerValue multiplier = new IntegerValue("Multiplier", 1, 1, 10, 1).setIndex(10);


    private final IntBuffer viewportBuffer = GLAllocation.createDirectIntBuffer(16);
    private final FloatBuffer modelViewBuffer = GLAllocation.createDirectFloatBuffer(16);
    private final FloatBuffer projectionBuffer = GLAllocation.createDirectFloatBuffer(16);
    private final FloatBuffer windowPositionBuffer = GLAllocation.createDirectFloatBuffer(4);

    private final String[] words = {
            "POW!",
            "SMASH!",
            "WHAM!",
            "KAPOW!",
            "BOOM!"
    };

    private final List<SFX> particles = new ArrayList<>();

    private final Frustum frustum = new Frustum();
    private AxisAlignedBB currentParticleAxis;

    class SFX {
        double x, y, z;
        double velX, velY, velZ;
        long spawnTime, livingTime;
        String text;
        Color color;
        int seed = MathUtil.getRandomInt(0, 20);

        public SFX(double x, double y, double z, double velX, double velY, double velZ, long msMax, String text, Color color) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.velX = velX;
            this.velY = velY;
            this.velZ = velZ;
            this.livingTime = msMax;
            this.spawnTime = System.currentTimeMillis();
            this.text = text;
            this.color = color;
        }

        public void update() {
            double delta = RenderUtil.delta / 50d;
            
            x += velX * delta;
            y += velY * delta;
            z += velZ * delta;

            velX /= 1 + (0.01 * delta);
            velZ /= 1 + (0.01 * delta);
            velY += (gravity.get() / 10d) * delta;
        }

    }

    public SuperHeroFX() {
        super("SuperHeroFX", ModuleCategory.RENDER);
        this.displayName = defaultDisplayName = "Super Hero FX";
    }

    @Listener
    public void onRender3d(Render2DEvent e) {
        TFontRenderer fr = FontUtil.sfx;
        EntityRenderer entityRenderer = mc.entityRenderer;

        frustum.setPosition(mc.getRenderViewEntity().posX, mc.getRenderViewEntity().posY, mc.getRenderViewEntity().posZ);

        for (SFX particle : particles) {
            particle.update();

            currentParticleAxis = new AxisAlignedBB(particle.x - .5, particle.y - .5, particle.z - .5, particle.x + .5, particle.y + .5, particle.z + .5);
            if(!frustum.isBoundingBoxInFrustum(currentParticleAxis)) continue;

            entityRenderer.setupCameraTransform(e.partialTicks, 0);

            final Vec3 particlePos = project2D(e.resolution.getScaleFactor(),
                    particle.x - mc.getRenderManager().viewerPosX,
                    particle.y - mc.getRenderManager().viewerPosY,
                    particle.z - mc.getRenderManager().viewerPosZ);

            if (particlePos == null) continue;

            entityRenderer.setupOverlayRendering();

            float halfTextWidth = fr.getStringWidthF(particle.text) / 2f;

            final HUD hud = HUD.module;

            int hudcolor = hud.getColor(particle.seed);
            hudcolor = ColorUtil.setOpacity(hudcolor, 120);
            Color black = Color.black;
            black = ColorUtil.setOpacity(black, 120);

            final float scale = this.scale.get();

            GlStateManager.pushMatrix();
            GlStateManager.scale(scale, scale, scale);
            fr.drawString(particle.text, (float)(particlePos.xCoord - halfTextWidth + 2)/scale, (float)(particlePos.yCoord + 2)/scale, Color.BLACK.getRGB());
            fr.drawString(particle.text, (float)(particlePos.xCoord - halfTextWidth)/scale, (float)particlePos.yCoord/scale, hud.getColor(particle.seed));
            GlStateManager.popMatrix();
        }
    }

    @Listener
    public void onAttack(AttackEvent e) {
        if (e.type == EventType.POST) return;

        Entity ent = e.target;

        for (int i = 0; i < multiplier.get(); i++) {

            double yaw = Math.toRadians(MathUtil.getRandomFloat(-180, 180));

            double randXZSpeed = MathUtil.getRandomFloat(randVeloXZMin.get(), randVeloXZMax.get());

            double velX = -Math.sin(yaw) * randXZSpeed;
            double velY = MathUtil.getRandomFloat(randVeloYMin.get(), randVeloYMax.get());
            double velZ = Math.cos(yaw) * randXZSpeed;
            long maxLivingTime = MathUtil.getRandomLong(minAge.get(), maxAge.get());

            String text = words[MathUtil.getRandomInt(0, words.length)];

            Color color = new Color(Color.HSBtoRGB(MathUtil.getRandomFloat(0f, 1f), 0.75f, 1f));

            final AxisAlignedBB bb = ent.getEntityBoundingBox();

            float yaw2 = MathUtil.getRandomFloat(-180, 180);
            float pitch = MathUtil.getRandomFloat(-90, 90);

            float f = MathHelper.cos(-yaw2 * 0.017453292F - (float) Math.PI);
            float f1 = MathHelper.sin(-yaw2 * 0.017453292F - (float) Math.PI);
            float f2 = -MathHelper.cos(-pitch * 0.017453292F);
            float f3 = MathHelper.sin(-pitch * 0.017453292F);

            Vec3 vec = new Vec3(f1 * f2, f3, f * f2);
            vec = vec.normalize();
            vec = vec.multiply(MathUtil.getRandomFloat(randSpawnMin.get(), randSpawnMax.get()));

            double posX = ent.posX + vec.xCoord;
            double posY = bb.minY + (bb.maxY - bb.minY) * spawnHeight.get() + vec.yCoord;
            double posZ = ent.posZ + vec.zCoord;

            final SFX sfx = new SFX(posX, posY, posZ, velX, velY, velZ, maxLivingTime, text, color);

            particles.add(sfx);
        }
    }

    @Listener
    public void onUpdate(UpdateEvent e) {
        particles.removeIf((sfx -> System.currentTimeMillis() >= sfx.spawnTime + sfx.livingTime));

        if(particles.size() > 300)
            particles.clear();
    }

    private Vec3 project2D(int scaleFactor, double x, double y, double z) {
        return RenderUtil.project2D(scaleFactor, (float) x, (float) y, (float) z, this.modelViewBuffer, this.projectionBuffer, this.viewportBuffer, this.windowPositionBuffer);
    }
}

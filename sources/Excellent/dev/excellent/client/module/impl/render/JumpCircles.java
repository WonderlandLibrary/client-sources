package dev.excellent.client.module.impl.render;

import dev.excellent.api.event.impl.other.WorldChangeEvent;
import dev.excellent.api.event.impl.other.WorldLoadEvent;
import dev.excellent.api.event.impl.player.JumpEvent;
import dev.excellent.api.event.impl.render.Render3DPosedEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.util.math.Mathf;
import dev.excellent.impl.util.pattern.Singleton;
import dev.excellent.impl.util.render.RectUtil;
import dev.excellent.impl.util.render.color.ColorUtil;
import dev.excellent.impl.value.impl.BooleanValue;
import dev.excellent.impl.value.impl.ModeValue;
import dev.excellent.impl.value.impl.NumberValue;
import dev.excellent.impl.value.mode.SubMode;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.mojang.blaze3d.matrix.MatrixStack;
import net.mojang.blaze3d.systems.RenderSystem;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14C;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ModuleInfo(name = "Jump Circles", description = "Создаёт пиздатые круги, при прыжках.", category = Category.RENDER)
public class JumpCircles extends Module {
    public static Singleton<JumpCircles> singleton = Singleton.create(() -> Module.link(JumpCircles.class));
    public List<java.util.ArrayList> animatedGroups = Arrays.asList(new java.util.ArrayList<>(), new java.util.ArrayList<>());


    private final NumberValue maxTime = new NumberValue("Время жизни", this, 3000, 2000, 8000, 25F);
    private final NumberValue radius = new NumberValue("Размер", this, 2, 1, 3, 0.1F);
    private final ModeValue texture = new ModeValue("Моды", this).add(new SubMode("Circle"), new SubMode("KonchalEbal"), new SubMode("CubicalPieces"), new SubMode("Leeches")).setDefault("Circle");
    private final ModeValue colormode = new ModeValue("Выбор цвета", this).add(new SubMode("Rainbow"), new SubMode("Client")).setDefault("Rainbow");
    private final BooleanValue deepestLight = new BooleanValue("Свечение", this, true);

    private final String staticLoc = "texture/newjumpcircles/default/", animatedLoc = "texture/newjumpcircles/animated/";
    private final ResourceLocation JUMP_CIRCLE = new ResourceLocation(excellent.getInfo().getNamespace(), staticLoc + "circle.png");
    private final ResourceLocation JUMP_KONCHAL = new ResourceLocation(excellent.getInfo().getNamespace(), staticLoc + "konchal.png");

    @Override
    public String getSuffix() {
        return texture.getValue().getName();
    }

    private ResourceLocation jumpTexture(int index, float progress) {
        if (texture.is("CubicalPieces") || texture.is("Leeches")) {
            ArrayList currentGroupTextures = texture.is("CubicalPieces") ? animatedGroups.get(0) : animatedGroups.get(1);
            final boolean animateByProgress = texture.is("Leeches");
            if (texture.is("Leeches")) {
                progress += .6F;
            }
            float frameOffset01 = progress % 1F;
            if (!animateByProgress) {
                final int ms = 1500;
                frameOffset01 = ((System.currentTimeMillis() + index) % ms) / (float) ms;
            }
            return (ResourceLocation) currentGroupTextures.get((int) Math.min(frameOffset01 * (currentGroupTextures.size() - .5F), currentGroupTextures.size()));
        } else {
            return texture.is("Circle") ? JUMP_CIRCLE : JUMP_KONCHAL;
        }
    }

    public void initResources() {
        ResourceLocation loc;
        final int[] groupsFramesLength = new int[]{100/*, 60*/, 200};
        final String[] groupsFramesFormat = new String[]{"jpeg", /*"png", */"png"};
        int groupIndex = groupsFramesLength.length - 1;
        if (animatedGroups.stream().allMatch(ArrayList::isEmpty)) {
            while (groupIndex >= 0) {
                int framesCounter = 0;
                while (framesCounter < groupsFramesLength[groupIndex]) {
                    framesCounter++;
                    loc = new ResourceLocation(excellent.getInfo().getNamespace(), animatedLoc + ("animation" + (groupIndex + 1)) + ("/circleframe_" + framesCounter) + ("." + groupsFramesFormat[groupIndex]));
                    animatedGroups.get(groupIndex).add(loc);
                }
                --groupIndex;
            }
        }
    }

    public JumpCircles() {
        initResources();
    }

    private void addCircleForEntity(final Entity entity) {
        Vector3d vec = getVec3dFromEntity(entity).add(0.D, .005D, 0.D);
        BlockPos pos = new BlockPos(vec);
        BlockState state = mc.world.getBlockState(pos);
        if (state.getBlock() == Blocks.SNOW) {
            vec = vec.add(0.D, .125D, 0.D);
        }
        circles.add(new JumpRenderer(vec, circles.size()));
    }

    private final Listener<JumpEvent> jumpEvent = event -> {
        addCircleForEntity(mc.player);
    };
    private final Listener<Render3DPosedEvent> onRender3D = event -> {
        if (circles.isEmpty()) return;
        circles.removeIf((final JumpRenderer circle) -> circle.getDeltaTime() >= 1.D);
        if (circles.isEmpty()) return;
        float deepestLightAnim = deepestLight.getValue() ? 1 : 0, immersiveStrengh = 0;
        if (deepestLightAnim >= 1.F / 255.F) {
            switch (texture.getValue().getName()) {
                case "Circle", "Emission" -> immersiveStrengh = .1F;
                case "KonchalEbal", "CubicalPieces", "Inusual" -> immersiveStrengh = .075F;
                case "Leeches" -> immersiveStrengh = .2F;
            }
        }
        float finalImmersiveStrengh = immersiveStrengh;
        setupDraw(event.getMatrix(), () -> circles.forEach(circle -> doCircle(event.getMatrix(), circle.pos, radius.getValue().floatValue(), 1.F - circle.getDeltaTime(), circle.getIndex() * 30, deepestLightAnim, finalImmersiveStrengh)));
    };

    private void doCircle(MatrixStack stack, final Vector3d pos, double maxRadius, float deltaTime, int index, float immersiveShift, float immersiveIntense) {
        boolean immersive = immersiveShift >= 1.F / 255.F;
        float waveDelta = valWave01(1.F - deltaTime);
        float alphaPC = (float) easeOutCirc(valWave01(1 - deltaTime));
        if (deltaTime < .5F) alphaPC *= (float) easeInOutExpo(alphaPC);
        float radius = (float) ((deltaTime > .5F ? easeOutElastic(waveDelta * waveDelta) : easeOutBounce(waveDelta)) * maxRadius);
        double rotate = easeInOutElastic(waveDelta) * 90.D / (1.D + waveDelta);
        ResourceLocation res = jumpTexture(index, deltaTime);
        mc.getTextureManager().bindTexture(res);
        mc.getTextureManager().getTexture(res).setBlurMipmap(true, true);
        stack.push();
        stack.translate(pos.x - radius / 2.D, pos.y, pos.z - radius / 2.D);
        stack.rotate(Vector3f.XP.rotationDegrees(90.F));
        customRotatedObject2D(stack, 0, 0, radius, radius, rotate);
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
        buffer.pos(stack.getLast().getMatrix(), 0, 0, 0).tex(0, 0).color(getColor(index, alphaPC)).endVertex();
        buffer.pos(stack.getLast().getMatrix(), 0, radius, 0).tex(0, 1).color(getColor(90 + index, alphaPC)).endVertex();
        buffer.pos(stack.getLast().getMatrix(), radius, radius, 0).tex(1, 1).color(getColor(180 + index, alphaPC)).endVertex();
        buffer.pos(stack.getLast().getMatrix(), radius, 0, 0).tex(1, 0).color(getColor(270 + index, alphaPC)).endVertex();
        tessellator.draw();
        stack.pop();
        if (immersive) {
            int[] colors = new int[4];
            colors[0] = getColor(index, alphaPC);
            colors[1] = getColor(90 + index, alphaPC);
            colors[2] = getColor(180 + index, alphaPC);
            colors[3] = getColor(270 + index, alphaPC);
            stack.push();
            stack.translate(pos.x, pos.y, pos.z);
            stack.rotate(Vector3f.YN.rotationDegrees((float) rotate));
            buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
            float polygons = 40, extMaxY = radius / 3.5F, extMaxXZ = radius / 7.F, minAPC = immersiveIntense * immersiveShift;
            float aPC;
            for (int i = 1; i < (int) polygons; i++) {
                float iPC = i / polygons, extY = extMaxY * i / polygons - extMaxY / polygons;
                if ((aPC = Mathf.lerp(alphaPC * minAPC, 0, iPC)) * 255 < 1) continue;
                float radiusPost = radius + (float) easeOutCirc(valWave01(iPC - 1.5F / polygons)) * extMaxXZ;
                buffer.pos(stack.getLast().getMatrix(), -radiusPost / 2.F, extY, -radiusPost / 2.F).tex(0, 0).color(ColorUtil.multDark(colors[0], aPC)).endVertex();
                buffer.pos(stack.getLast().getMatrix(), -radiusPost / 2.F, extY, radiusPost / 2.F).tex(0, 1).color(ColorUtil.multDark(colors[1], aPC)).endVertex();
                buffer.pos(stack.getLast().getMatrix(), radiusPost / 2.F, extY, radiusPost / 2.F).tex(1, 1).color(ColorUtil.multDark(colors[2], aPC)).endVertex();
                buffer.pos(stack.getLast().getMatrix(), radiusPost / 2.F, extY, -radiusPost / 2.F).tex(1, 0).color(ColorUtil.multDark(colors[3], aPC)).endVertex();
            }
            tessellator.draw();
            stack.pop();
        }
    }

    private final Listener<WorldChangeEvent> onWorldChange = event -> reset();
    private final Listener<WorldLoadEvent> onWorldLoad = event -> reset();

    @Override
    protected void onEnable() {
        reset();
        super.onEnable();
    }

    @Override
    protected void onDisable() {
        reset();
        super.onDisable();
    }

    private void reset() {
        if (!circles.isEmpty()) circles.clear();
    }


    private final static List<JumpRenderer> circles = new java.util.ArrayList<>();

    private static Vector3d getVec3dFromEntity(final Entity entityIn) {
        final float PT = mc.getRenderPartialTicks();
        final double dx = entityIn.getPosX() - entityIn.lastTickPosX, dy = entityIn.getPosY() - entityIn.lastTickPosY, dz = entityIn.getPosZ() - entityIn.lastTickPosZ;
        return new Vector3d((entityIn.lastTickPosX + dx * PT + dx * 2.D), (entityIn.lastTickPosY + dy * PT), (entityIn.lastTickPosZ + dz * PT + dz * 2.D));
    }

    private void setupDraw(MatrixStack stack, final Runnable render) {
        final boolean light = GL11.glIsEnabled(GL11.GL_LIGHTING);
        stack.push();
        RenderSystem.enableBlend();
        RenderSystem.enableAlphaTest();
        RenderSystem.alphaFunc(GL14C.GL_GREATER, 0);
        RenderSystem.depthMask(false);
        RenderSystem.disableCull();
        if (light) RenderSystem.disableLighting();
        RenderSystem.shadeModel(GL11.GL_SMOOTH);
        RenderSystem.blendFunc(GL14C.GL_SRC_ALPHA, GL14C.GL_ONE_MINUS_CONSTANT_ALPHA);
        RectUtil.setupOrientationMatrix(stack, 0, 0, 0);
        render.run();
        RenderSystem.blendFunc(GL14C.GL_SRC_ALPHA, GL14C.GL_ONE_MINUS_SRC_ALPHA);
        RenderSystem.color3f(1.F, 1.F, 1.F);
        RenderSystem.shadeModel(GL11.GL_FLAT);
        if (light) RenderSystem.enableLighting();
        RenderSystem.enableCull();
        RenderSystem.depthMask(true);
        RenderSystem.alphaFunc(GL14C.GL_GREATER, .1F);
        RenderSystem.enableAlphaTest();
        stack.pop();
    }

    private int getColor(int index, float alphaPC) {
        int colorize = colormode.is("Client") ? getTheme().getClientColor(index, alphaPC) : ColorUtil.rainbow(7, index, 1F, 1, alphaPC);
        return ColorUtil.getOverallColorFrom(colorize, ColorUtil.getColor(255, (int) (255.F * alphaPC)), .1F);
    }

    private final Tessellator tessellator = Tessellator.getInstance();
    private final BufferBuilder buffer = tessellator.getBuffer();

    private final class JumpRenderer {
        private final long time = System.currentTimeMillis();
        private final Vector3d pos;
        int index;

        private JumpRenderer(Vector3d pos, int index) {
            this.pos = pos;
            this.index = index;
        }

        private float getDeltaTime() {
            return (System.currentTimeMillis() - time) / maxTime.getValue().floatValue();
        }

        private int getIndex() {
            return this.index;
        }
    }

    public static double easeOutBounce(double value) {
        double n1 = 7.5625, d1 = 2.75;
        if (value < 1 / d1) {
            return n1 * value * value;
        } else if (value < 2 / d1) {
            return n1 * (value -= 1.5 / d1) * value + 0.75;
        } else if (value < 2.5 / d1) {
            return n1 * (value -= 2.25 / d1) * value + 0.9375;
        } else {
            return n1 * (value -= 2.625 / d1) * value + 0.984375;
        }
    }

    public static double easeInOutElastic(double value) {
        double c5 = (2 * Math.PI) / 4.5;
        return value < 0 ? 0 : value > 1 ? 1 : value < 0.5 ? -(Math.pow(2, 20 * value - 10) * Math.sin((20 * value - 11.125) * c5)) / 2 : (Math.pow(2, -20 * value + 10) * Math.sin((20 * value - 11.125) * c5)) / 2 + 1;
    }

    public static double easeOutElastic(double value) {
        double c4 = (2 * Math.PI) / 3;
        return value < 0 ? 0 : value > 1 ? 1 : Math.pow(2, -10 * value) * Math.sin((value * 10 - 0.75) * c4) + 1;
    }

    public static double easeOutBack(double value) {
        double c1 = 1.70158, c3 = c1 + 1;
        return 1 + c3 * Math.pow(value - 1, 3) + c1 * Math.pow(value - 1, 2);
    }

    public static double easeOutCubic(double value) {
        return 1 - Math.pow(1 - value, 3);
    }

    public static float valWave01(float value) {
        return (value > .5 ? 1 - value : value) * 2.F;
    }

    public static double easeInOutQuad(double value) {
        return value < .5D ? 2.D * value * value : 1.D - Math.pow(-2.D * value + 2.D, 2.D) / 2.D;
    }

    public static double easeInOutQuadWave(double value) {
        value = (value > .5D ? 1.D - value : value) * 2.D;
        value = value < .5D ? 2.D * value * value : 1.D - Math.pow(-2.D * value + 2.D, 2.D) / 2.D;
        value = value > 1.D ? 1.D : value < 0.D ? 0.D : value;
        return value;
    }

    public static double easeInCircle(double value) {
        return 1 - Math.sqrt(1 - Math.pow(value, 2));
    }

    public static double easeOutCirc(double value) {
        return Math.sqrt(1 - Math.pow(value - 1, 2));
    }

    public static double easeInOutExpo(double value) {
        return value < 0 ? 0 : value > 1 ? 1 : value < 0.5 ? Math.pow(2, 20 * value - 10) / 2 : (2 - Math.pow(2, -20 * value + 10)) / 2;
    }

    public static float lerp(float value, float to, float pc) {
        return value + pc * (to - value);
    }

    public static void customRotatedObject2D(MatrixStack stack, float oXpos, float oYpos, float oWidth, float oHeight, double rotate) {
        stack.translate(oXpos + oWidth / 2, oYpos + oHeight / 2, 0);
        stack.rotate(Vector3f.ZP.rotationDegrees((float) rotate));
        stack.translate(-oXpos - oWidth / 2, -oYpos - oHeight / 2, 0);
    }
}
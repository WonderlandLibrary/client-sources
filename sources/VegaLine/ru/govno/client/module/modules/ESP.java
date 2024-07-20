/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector4d;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEndPortal;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.tileentity.TileEntityShulkerBox;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec2f;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import optifine.Config;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.glu.GLU;
import ru.govno.client.module.Module;
import ru.govno.client.module.modules.BowAimbot;
import ru.govno.client.module.modules.ClientColors;
import ru.govno.client.module.modules.FreeCam;
import ru.govno.client.module.modules.HitAura;
import ru.govno.client.module.modules.TargetHUD;
import ru.govno.client.module.settings.Settings;
import ru.govno.client.newfont.CFontRenderer;
import ru.govno.client.newfont.Fonts;
import ru.govno.client.utils.Combat.RotationUtil;
import ru.govno.client.utils.Command.impl.Panic;
import ru.govno.client.utils.CrystalField;
import ru.govno.client.utils.Math.MathUtils;
import ru.govno.client.utils.Math.TimerHelper;
import ru.govno.client.utils.Render.AnimationUtils;
import ru.govno.client.utils.Render.ColorUtils;
import ru.govno.client.utils.Render.RenderUtils;
import ru.govno.client.utils.Render.ShaderUtil2;
import ru.govno.client.utils.Render.ShaderUtility;
import ru.govno.client.utils.Render.Vec2fColored;

public class ESP
extends Module {
    public static ESP get;
    private static final AnimationUtils alphaPC;
    Random RANDOM = new Random();
    public Settings EnderCrystals;
    public Settings CrystalMode;
    public Settings CrystalImprove;
    public Settings Players;
    public Settings PlayerMode;
    public Settings Self;
    public Settings SelfMode;
    public Settings GlowRadius;
    public Settings GlowBloom;
    public Settings BloomAmount;
    public Settings ChamsBright;
    public Settings ItemsI;
    public Settings EnderPearl;
    public Settings Spawner;
    public Settings Storage;
    public Settings BreakOver;
    public Settings Beacon;
    public Settings TntPrimed;
    public Settings TntMode;
    public Settings Targets;
    public Settings TargetsMode;
    public Settings TargetTexture;
    public Settings TargetColor;
    public Settings PickNormal;
    public Settings PickHurt;
    public Settings VoidHighlight;
    public Settings Portals;
    private final ShaderUtil2 glowShader = new ShaderUtil2("vegaline/modules/esp/shaders/anglecoloredglow.frag");
    Framebuffer framebuffer;
    Framebuffer glowFrameBuffer;
    private final IntBuffer viewport = GLAllocation.createDirectIntBuffer(16);
    private final FloatBuffer modelview = GLAllocation.createDirectFloatBuffer(16);
    private final FloatBuffer projection = GLAllocation.createDirectFloatBuffer(16);
    private final FloatBuffer vector = GLAllocation.createDirectFloatBuffer(4);
    List<EntityLivingBase> updatedTargets = new ArrayList<EntityLivingBase>();
    List<TESP> TESP_LIST = new ArrayList<TESP>();
    private final List<ColVecsWithEnt> colVecsWithEntList = new ArrayList<ColVecsWithEnt>();
    private final ResourceLocation TARGET_2D_ESP = new ResourceLocation("vegaline/modules/esp/target/quadstapple.png");
    private final ResourceLocation TARGET_2D_ESP2 = new ResourceLocation("vegaline/modules/esp/target/fuckfinger.png");
    private final ResourceLocation TARGET_2D_ESP3 = new ResourceLocation("vegaline/modules/esp/target/trianglestapple.png");
    private final ResourceLocation TARGET_2D_ESP4 = new ResourceLocation("vegaline/modules/esp/target/trianglestipple.png");
    final Item[] garbageitems = new Item[]{Items.STRING, Items.SPIDER_EYE, Items.ROTTEN_FLESH, Item.getItemFromBlock(Blocks.MOSSY_COBBLESTONE), Item.getItemFromBlock(Blocks.COBBLESTONE), Items.SADDLE, Items.BONE, Items.WHEAT, Items.IRON_HORSE_ARMOR, Items.GOLDEN_HORSE_ARMOR, Items.GUNPOWDER, Items.PUMPKIN_SEEDS, Items.WHEAT_SEEDS, Items.NAME_TAG, Items.REDSTONE, Items.IRON_INGOT};
    int targetColor;
    private final ResourceLocation PEARL_MARK_TEXTURE = new ResourceLocation("vegaline/modules/esp/pearl/pearlmarker.png");
    public BlockPos posOver = new BlockPos(-1, 2, 1);
    private final AnimationUtils alphaSelectPC = new AnimationUtils(0.0f, 0.0f, 0.1f);
    private final AnimationUtils progressingSelect = new AnimationUtils(0.0f, 0.0f, 0.115f);
    private final Vec3d smoothPosSelect = new Vec3d(0.0, 0.0, 0.0);
    private final Tessellator tessellator = Tessellator.getInstance();
    private final BufferBuilder buffer = this.tessellator.getBuffer();

    public ESP() {
        super("ESP", 0, Module.Category.RENDER);
        this.EnderCrystals = new Settings("Ender crystals", false, (Module)this);
        this.settings.add(this.EnderCrystals);
        this.CrystalMode = new Settings("Crystal mode", "Glow", this, new String[]{"Glow", "Chams"}, () -> this.EnderCrystals.bValue);
        this.settings.add(this.CrystalMode);
        this.CrystalImprove = new Settings("Crystal improve", true, (Module)this);
        this.settings.add(this.CrystalImprove);
        this.Players = new Settings("Players", true, (Module)this);
        this.settings.add(this.Players);
        this.PlayerMode = new Settings("Player mode", "Glow", this, new String[]{"2D", "Glow", "Chams"}, () -> this.Players.bValue);
        this.settings.add(this.PlayerMode);
        this.Self = new Settings("Self", true, (Module)this);
        this.settings.add(this.Self);
        this.SelfMode = new Settings("Self mode", "Glow", this, new String[]{"2D", "Glow", "Chams"}, () -> this.Self.bValue);
        this.settings.add(this.SelfMode);
        this.GlowRadius = new Settings("Glow radius", 4.0f, 15.0f, 2.0f, this, () -> this.Players.bValue && this.PlayerMode.currentMode.equalsIgnoreCase("Glow") || this.Self.bValue && this.SelfMode.currentMode.equalsIgnoreCase("Glow") || this.EnderCrystals.bValue && this.CrystalMode.currentMode.equalsIgnoreCase("Glow") || this.TntPrimed.bValue && this.TntMode.currentMode.equalsIgnoreCase("Glow"));
        this.settings.add(this.GlowRadius);
        this.GlowBloom = new Settings("Glow bloom", true, (Module)this, () -> this.Players.bValue && this.PlayerMode.currentMode.equalsIgnoreCase("Glow") || this.Self.bValue && this.SelfMode.currentMode.equalsIgnoreCase("Glow") || this.EnderCrystals.bValue && this.CrystalMode.currentMode.equalsIgnoreCase("Glow") || this.TntPrimed.bValue && this.TntMode.currentMode.equalsIgnoreCase("Glow"));
        this.settings.add(this.GlowBloom);
        this.BloomAmount = new Settings("Bloom amount", 1.0f, 1.5f, 0.5f, this, () -> this.GlowBloom.bValue && (this.Players.bValue && this.PlayerMode.currentMode.equalsIgnoreCase("Glow") || this.Self.bValue && this.SelfMode.currentMode.equalsIgnoreCase("Glow") || this.EnderCrystals.bValue && this.CrystalMode.currentMode.equalsIgnoreCase("Glow") || this.TntPrimed.bValue && this.TntMode.currentMode.equalsIgnoreCase("Glow")));
        this.settings.add(this.BloomAmount);
        this.ChamsBright = new Settings("Chams bright", 0.4f, 1.0f, 0.05f, this, () -> this.Players.bValue && this.PlayerMode.currentMode.equalsIgnoreCase("Chams") || this.Self.bValue && this.SelfMode.currentMode.equalsIgnoreCase("Chams") || this.EnderCrystals.bValue && this.CrystalMode.currentMode.equalsIgnoreCase("Chams"));
        this.settings.add(this.ChamsBright);
        this.ItemsI = new Settings("Items", true, (Module)this);
        this.settings.add(this.ItemsI);
        this.EnderPearl = new Settings("EnderPearl", true, (Module)this);
        this.settings.add(this.EnderPearl);
        this.Spawner = new Settings("Spawner", false, (Module)this);
        this.settings.add(this.Spawner);
        this.Storage = new Settings("Storage", true, (Module)this);
        this.settings.add(this.Storage);
        this.BreakOver = new Settings("BreakOver", true, (Module)this);
        this.settings.add(this.BreakOver);
        this.Beacon = new Settings("Beacon", false, (Module)this);
        this.settings.add(this.Beacon);
        this.TntPrimed = new Settings("TntPrimed", false, (Module)this);
        this.settings.add(this.TntPrimed);
        this.TntMode = new Settings("Tnt mode", "2D", this, new String[]{"2D", "Glow"}, () -> this.TntPrimed.bValue);
        this.settings.add(this.TntMode);
        this.Targets = new Settings("Targets", true, (Module)this);
        this.settings.add(this.Targets);
        this.TargetsMode = new Settings("Targets mode", "Shape", this, new String[]{"Shape", "Hologram"}, () -> this.Targets.bValue);
        this.settings.add(this.TargetsMode);
        this.TargetTexture = new Settings("Target texture", "TriangleStapple", this, new String[]{"QuadStapple", "FuckFinger", "TriangleStapple", "TriangleStipple"}, () -> this.Targets.bValue && this.TargetsMode.currentMode.equalsIgnoreCase("Shape"));
        this.settings.add(this.TargetTexture);
        this.TargetColor = new Settings("Target color", "Client", this, new String[]{"Client", "Pick color", "Pick color & hurt"}, () -> this.Targets.bValue);
        this.settings.add(this.TargetColor);
        this.PickNormal = new Settings("Pick normal", ColorUtils.getColor(62, 139, 255, 240), (Module)this, () -> this.Targets.bValue && (this.TargetColor.currentMode.equalsIgnoreCase("Pick color") || this.TargetColor.currentMode.equalsIgnoreCase("Pick color & hurt")));
        this.settings.add(this.PickNormal);
        this.PickHurt = new Settings("Pick hurt", ColorUtils.getColor(255, 61, 84, 255), (Module)this, () -> this.Targets.bValue && this.TargetColor.currentMode.equalsIgnoreCase("Pick color & hurt"));
        this.settings.add(this.PickHurt);
        this.VoidHighlight = new Settings("VoidHighlight", true, (Module)this);
        this.settings.add(this.VoidHighlight);
        this.Portals = new Settings("Portals", true, (Module)this);
        this.settings.add(this.Portals);
        this.RANDOM.setSeed(1234567891L);
        get = this;
    }

    private void setupGlowShader(float radius, float alpha) {
        int color1 = ClientColors.getColor1(0);
        int indexPlus = 1296;
        int step = 5;
        float index = indexPlus / step;
        int color2 = ClientColors.getColor1((int)index);
        int color3 = ClientColors.getColor1((int)(index += (float)(indexPlus / step)));
        int color4 = ClientColors.getColor1((int)(index += (float)(indexPlus / step)));
        int color5 = ClientColors.getColor1((int)(index += (float)(indexPlus / step)));
        this.glowShader.setUniformi("textureToCheck", 16);
        this.glowShader.setUniformf("radius", radius);
        this.glowShader.setUniformf("texelSize", 1.0f / (float)ESP.mc.displayWidth, 1.0f / (float)ESP.mc.displayHeight);
        this.glowShader.setUniformColor("color1", color1);
        this.glowShader.setUniformColor("color2", color2);
        this.glowShader.setUniformColor("color3", color3);
        this.glowShader.setUniformColor("color4", color4);
        this.glowShader.setUniformColor("color5", color5);
        this.glowShader.setUniformf("exposure", alpha * 3.0f);
        this.glowShader.setUniformi("avoidTexture", 1);
        this.glowShader.setUniformf("time", 0.0f);
        FloatBuffer buffer = BufferUtils.createFloatBuffer((int)(radius + 1.0f));
        int i = 1;
        while ((float)i <= radius) {
            buffer.put(MathUtils.calculateGaussianValue(i, radius / 2.0f));
            ++i;
        }
        buffer.rewind();
        GL20.glUniform1(this.glowShader.getUniform("weights"), buffer);
    }

    private void setupGlowDirs(float dir1, float dir2) {
        this.glowShader.setUniformf("direction", dir1, dir2);
    }

    private Vector3d project2D(int scaleFactor, double x, double y, double z) {
        GL11.glGetFloat(2982, this.modelview);
        GL11.glGetFloat(2983, this.projection);
        GL11.glGetInteger(2978, this.viewport);
        return GLU.gluProject((float)x, (float)y, (float)z, this.modelview, this.projection, this.viewport, this.vector) ? new Vector3d(this.vector.get(0) / (float)scaleFactor, ((float)Display.getHeight() - this.vector.get(1)) / (float)scaleFactor, this.vector.get(2)) : null;
    }

    private List<EntityLivingBase> getTargets() {
        CopyOnWriteArrayList<EntityLivingBase> targets = new CopyOnWriteArrayList<EntityLivingBase>();
        if (ESP.get.actived) {
            EntityLivingBase thTarget;
            if (HitAura.TARGET != null) {
                targets.add(HitAura.TARGET);
            }
            if (!CrystalField.getTargets().isEmpty()) {
                for (EntityLivingBase Targets : CrystalField.getTargets()) {
                    if (Targets == null) continue;
                    targets.add(Targets);
                }
            }
            if (BowAimbot.target != null) {
                targets.add(BowAimbot.target);
            }
            if ((thTarget = TargetHUD.getTarget()) != null && thTarget != Minecraft.player) {
                targets.add(thTarget);
            }
        }
        return targets;
    }

    private float[] get2DPosForTESP(Entity entity, float partialTicks, int scaleFactor, RenderManager renderMng) {
        float[] fArray;
        Entity minik = entity;
        double x = RenderUtils.interpolate(entity.posX, entity.prevPosX, partialTicks);
        double y = RenderUtils.interpolate(entity.posY, entity.prevPosY, partialTicks);
        double z = RenderUtils.interpolate(entity.posZ, entity.prevPosZ, partialTicks);
        double height = entity.height / (minik instanceof EntityLivingBase && ((EntityLivingBase)minik).isChild() ? 1.75f : 1.0f) + 0.1f;
        AxisAlignedBB aabb = new AxisAlignedBB(x, y + height, z, x, y + height, z);
        Vector3d[] vectors = new Vector3d[]{new Vector3d(aabb.minX, aabb.minY, aabb.minZ), new Vector3d(aabb.minX, aabb.maxY, aabb.minZ), new Vector3d(aabb.maxX, aabb.minY, aabb.minZ), new Vector3d(aabb.maxX, aabb.maxY, aabb.minZ), new Vector3d(aabb.minX, aabb.minY, aabb.maxZ), new Vector3d(aabb.minX, aabb.maxY, aabb.maxZ), new Vector3d(aabb.maxX, aabb.minY, aabb.maxZ), new Vector3d(aabb.maxX, aabb.maxY, aabb.maxZ)};
        ESP.mc.entityRenderer.setupCameraTransform(partialTicks, 0);
        Vector4d position = null;
        Vector3d[] vecs3 = vectors;
        int vecLength = vectors.length;
        for (int vecI = 0; vecI < vecLength; ++vecI) {
            Vector3d vector = vecs3[vecI];
            vector = this.project2D(scaleFactor, vector.x - RenderManager.viewerPosX, vector.y - RenderManager.viewerPosY, vector.z - RenderManager.viewerPosZ);
            if (vector == null || !(vector.z >= 0.0) || !(vector.z < 1.0)) continue;
            if (position == null) {
                position = new Vector4d(vector.x, vector.y, vector.z, 0.0);
            }
            position.x = Math.min(vector.x, position.x);
            position.y = Math.min(vector.y, position.y);
            position.z = Math.max(vector.x, position.z);
            position.w = Math.max(vector.y, position.w);
        }
        ESP.mc.entityRenderer.setupOverlayRendering();
        if (position == null) {
            float[] fArray2 = new float[2];
            fArray2[0] = -1.0f;
            fArray = fArray2;
            fArray2[1] = -1.0f;
        } else {
            float[] fArray3 = new float[2];
            fArray3[0] = (float)position.x;
            fArray = fArray3;
            fArray3[1] = (float)position.y;
        }
        return fArray;
    }

    private void controlTESPList(List<EntityLivingBase> targets) {
        this.updatedTargets = targets;
        this.updatedTargets.forEach(target -> {
            if (!this.TESP_LIST.stream().anyMatch(tesp -> tesp.entity.getEntityId() == target.getEntityId())) {
                this.TESP_LIST.add(new TESP((EntityLivingBase)target, 10, 5));
            }
        });
        if (this.TESP_LIST.isEmpty()) {
            return;
        }
        this.TESP_LIST.forEach(TESP::updateTESP);
        this.TESP_LIST.removeIf(TESP::isToRemove);
    }

    @Override
    public void onUpdate() {
        this.controlTESPList(this.TargetsMode.currentMode.equalsIgnoreCase("Hologram") ? this.getTargets() : new ArrayList<EntityLivingBase>());
    }

    private ColVecsWithEnt targetESPSPos(EntityLivingBase entity, int index) {
        EntityRenderer entityRenderer = ESP.mc.entityRenderer;
        float partialTicks = mc.getRenderPartialTicks();
        int scaleFactor = ScaledResolution.getScaleFactor();
        double x = RenderUtils.interpolate(entity.posX, entity.prevPosX, partialTicks);
        double y = RenderUtils.interpolate(entity.posY, entity.prevPosY, partialTicks);
        double z = RenderUtils.interpolate(entity.posZ, entity.prevPosZ, partialTicks);
        double height = entity.height / (entity.isChild() ? 1.75f : 1.0f) / 2.0f;
        double width = 0.0;
        AxisAlignedBB aabb = new AxisAlignedBB(x - 0.0, y, z - 0.0, x + 0.0, y + height, z + 0.0);
        Vector3d[] vectors = new Vector3d[]{new Vector3d(aabb.minX, aabb.minY, aabb.minZ), new Vector3d(aabb.minX, aabb.maxY, aabb.minZ), new Vector3d(aabb.maxX, aabb.minY, aabb.minZ), new Vector3d(aabb.maxX, aabb.maxY, aabb.minZ), new Vector3d(aabb.minX, aabb.minY, aabb.maxZ), new Vector3d(aabb.minX, aabb.maxY, aabb.maxZ), new Vector3d(aabb.maxX, aabb.minY, aabb.maxZ), new Vector3d(aabb.maxX, aabb.maxY, aabb.maxZ)};
        entityRenderer.setupCameraTransform(partialTicks, 0);
        Vector4d position = null;
        Vector3d[] vecs3 = vectors;
        int vecLength = vectors.length;
        for (int vecI = 0; vecI < vecLength; ++vecI) {
            Vector3d vector = vecs3[vecI];
            vector = this.project2D(scaleFactor, vector.x - RenderManager.viewerPosX, vector.y - RenderManager.viewerPosY, vector.z - RenderManager.viewerPosZ);
            if (vector == null || !(vector.z >= 0.0) || !(vector.z < 1.0)) continue;
            if (position == null) {
                position = new Vector4d(vector.x, vector.y, vector.z, 0.0);
            }
            position.x = Math.min(vector.x, position.x);
            position.y = Math.min(vector.y, position.y);
            position.z = Math.max(vector.x, position.z);
            position.w = Math.max(vector.y, position.w);
        }
        entityRenderer.setupOverlayRendering();
        if (position != null) {
            return new ColVecsWithEnt(new Vec2fQuadColored((float)position.x, (float)position.y, this.getTargetColor(entity, index), this.getTargetColor(entity, index + 90), this.getTargetColor(entity, index + 180), this.getTargetColor(entity, index + 270)), entity);
        }
        return null;
    }

    private void drawImage(ResourceLocation resource, float x, float y, float x2, float y2, int c, int c2, int c3, int c4) {
        mc.getTextureManager().bindTexture(resource);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(9, DefaultVertexFormats.POSITION_TEX_COLOR);
        bufferbuilder.pos(x, y2).tex(0.0, 1.0).color(c).endVertex();
        bufferbuilder.pos(x2, y2).tex(1.0, 1.0).color(c2).endVertex();
        bufferbuilder.pos(x2, y).tex(1.0, 0.0).color(c3).endVertex();
        bufferbuilder.pos(x, y).tex(0.0, 0.0).color(c4).endVertex();
        GL11.glShadeModel(7425);
        GL11.glDepthMask(false);
        tessellator.draw();
        GL11.glDepthMask(true);
        GL11.glShadeModel(7424);
    }

    private void drawImage(ResourceLocation resource, float x, float y, float x2, float y2, int c) {
        this.drawImage(resource, x, y, x2, y2, c, c, c, c);
    }

    private ResourceLocation getTargetTexture(String mode) {
        return switch (mode) {
            case "QuadStapple" -> this.TARGET_2D_ESP;
            case "FuckFinger" -> this.TARGET_2D_ESP2;
            case "TriangleStapple" -> this.TARGET_2D_ESP3;
            case "TriangleStipple" -> this.TARGET_2D_ESP4;
            default -> null;
        };
    }

    private void drawTargetESP(float x, float y, int color, int color2, int color3, int color4, float scale, int index) {
        long millis = System.currentTimeMillis() + (long)index * 400L;
        double angle = MathUtils.clamp((Math.sin((double)millis / 150.0) + 1.0) / 2.0 * 30.0, 0.0, 30.0);
        double scaled = MathUtils.clamp((Math.sin((double)millis / 500.0) + 1.0) / 2.0, 0.8, 1.0);
        double rotate = MathUtils.clamp((Math.sin((double)millis / 1000.0) + 1.0) / 2.0 * 360.0, 0.0, 360.0);
        rotate = (double)(this.TargetTexture.currentMode.equalsIgnoreCase("QuadStapple") ? 45 : 0) - (angle - 15.0) + rotate;
        float size = 128.0f * scale * (float)scaled;
        float x2 = (x -= size / 2.0f) + size;
        float y2 = (y -= size / 2.0f) + size;
        ResourceLocation resource = this.getTargetTexture(this.TargetTexture.currentMode);
        if (resource == null) {
            return;
        }
        GlStateManager.pushMatrix();
        RenderUtils.customRotatedObject2D(x, y, size, size, (float)rotate);
        GL11.glDisable(3008);
        GlStateManager.depthMask(false);
        GlStateManager.enableBlend();
        GlStateManager.shadeModel(7425);
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        this.drawImage(resource, x, y, x2, y2, color, color2, color3, color4);
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.resetColor();
        GlStateManager.shadeModel(7424);
        GlStateManager.depthMask(true);
        GL11.glEnable(3008);
        GlStateManager.popMatrix();
    }

    private void drawTargets2D(ColVecsWithEnt colVecsWithEnt) {
        float dst = Minecraft.player.getSmoothDistanceToEntity(colVecsWithEnt.getEntity());
        float scaled = (1.0f - MathUtils.clamp(Math.abs(dst - 6.0f) / 60.0f, 0.0f, 0.75f)) * colVecsWithEnt.alphaPC.getAnim();
        int color = colVecsWithEnt.colVec.getColor();
        color = ColorUtils.swapAlpha(color, (float)ColorUtils.getAlphaFromColor(color) * colVecsWithEnt.alphaPC.anim);
        int color2 = colVecsWithEnt.colVec.getColor2();
        color2 = ColorUtils.swapAlpha(color2, (float)ColorUtils.getAlphaFromColor(color2) * colVecsWithEnt.alphaPC.anim);
        int color3 = colVecsWithEnt.colVec.getColor3();
        color3 = ColorUtils.swapAlpha(color3, (float)ColorUtils.getAlphaFromColor(color3) * colVecsWithEnt.alphaPC.anim);
        int color4 = colVecsWithEnt.colVec.getColor4();
        color4 = ColorUtils.swapAlpha(color4, (float)ColorUtils.getAlphaFromColor(color4) * colVecsWithEnt.alphaPC.anim);
        this.drawTargetESP(colVecsWithEnt.colVec.getX(), colVecsWithEnt.colVec.getY(), color, color2, color3, color4, scaled, colVecsWithEnt.randomIndex);
    }

    public boolean crystalImprove() {
        return !Panic.stop && ESP.canRenderModule() && this.CrystalImprove.bValue;
    }

    private void setupAlphaModule() {
        float f = ESP.alphaPC.to = this.actived ? 1.0f : 0.0f;
        if (this.actived && (double)ESP.getAlphaPC() > 0.995) {
            alphaPC.setAnim(1.0f);
        }
    }

    private static float getAlphaPC() {
        return alphaPC.getAnim();
    }

    private static boolean canRenderModule() {
        return ESP.getAlphaPC() >= 0.03f;
    }

    private final Vec3d getCompense() {
        return new Vec3d(RenderManager.renderPosX, RenderManager.renderPosY, RenderManager.renderPosZ);
    }

    private final void setup3dFor(Runnable render) {
        GL11.glPushMatrix();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GL11.glEnable(3042);
        GL11.glLineWidth(1.0E-5f);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDisable(2896);
        GL11.glDisable(3008);
        ESP.mc.entityRenderer.disableLightmap();
        GL11.glShadeModel(7425);
        GlStateManager.translate(-this.getCompense().xCoord, -this.getCompense().yCoord, -this.getCompense().zCoord);
        render.run();
        GlStateManager.translate(this.getCompense().xCoord, this.getCompense().yCoord, this.getCompense().zCoord);
        GL11.glLineWidth(1.0f);
        GL11.glShadeModel(7424);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glEnable(3008);
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GL11.glPopMatrix();
    }

    private void drawBlockPos(AxisAlignedBB aabb, int color) {
        int c1 = ColorUtils.swapAlpha(color, (float)ColorUtils.getAlphaFromColor(color) / 2.6f);
        int c2 = ColorUtils.swapAlpha(color, (float)ColorUtils.getAlphaFromColor(color) / 12.0f);
        RenderUtils.drawCanisterBox(aabb, true, false, true, c1, 0, c2);
    }

    private AxisAlignedBB getAxisExtended(BlockPos pos, AxisAlignedBB aabb, float percent, Vec3d smoothVec) {
        double x1 = aabb.minX - (double)pos.getX() + smoothVec.xCoord;
        double y1 = aabb.minY - (double)pos.getY() + smoothVec.yCoord;
        double z1 = aabb.minZ - (double)pos.getZ() + smoothVec.zCoord;
        double x2 = aabb.maxX - (double)pos.getX() + smoothVec.xCoord;
        double y2 = aabb.maxY - (double)pos.getY() + smoothVec.yCoord;
        double z2 = aabb.maxZ - (double)pos.getZ() + smoothVec.zCoord;
        double XW = x2 - x1;
        double ZW = z2 - z1;
        double YH = y2 - y1;
        Vec3d centerOFAABBox = new Vec3d(x1 + XW / 2.0, y1 + YH / 2.0, z1 + ZW / 2.0);
        AxisAlignedBB finallyAABB = new AxisAlignedBB(centerOFAABBox.xCoord - XW / 2.0 * (double)percent, centerOFAABBox.yCoord - YH / 2.0 * (double)percent, centerOFAABBox.zCoord - ZW / 2.0 * (double)percent, centerOFAABBox.xCoord + XW / 2.0 * (double)percent, centerOFAABBox.yCoord + YH / 2.0 * (double)percent, centerOFAABBox.zCoord + ZW / 2.0 * (double)percent);
        return finallyAABB == null ? aabb : finallyAABB;
    }

    private static boolean isDangeon(TileEntityMobSpawner spawner) {
        BlockPos pos = spawner.getPos();
        IBlockState state = ESP.mc.world.getBlockState(pos.down());
        Block block = state.getBlock();
        return block == Blocks.MOSSY_COBBLESTONE || block == Blocks.COBBLESTONE;
    }

    private static boolean canPosBeSeenPos(BlockPos posFirst, BlockPos posSecond) {
        return Minecraft.getMinecraft().world.rayTraceBlocks(new Vec3d((double)posFirst.getX() + 0.5, posFirst.getY() + 1, (double)posFirst.getZ() + 0.5), new Vec3d((double)posSecond.getX() + 0.5, posSecond.getY(), (double)posSecond.getZ() + 0.5), false, true, false) == null;
    }

    private static boolean posSeenSky(BlockPos pos) {
        boolean seen = ESP.canPosBeSeenPos(pos, new BlockPos(pos.getX(), 256, pos.getZ()));
        return seen;
    }

    private boolean dangeonIsLooted(TileEntityMobSpawner spawner) {
        boolean bad = false;
        CopyOnWriteArrayList<BlockPos> currentable = new CopyOnWriteArrayList<BlockPos>();
        int range = 5;
        block0: for (Entity ents : ESP.mc.world.getLoadedEntityList()) {
            if (ents == null || !(ents instanceof EntityItem)) continue;
            EntityItem item = (EntityItem)ents;
            Item cur = item.getItem().getItem();
            if (!(item.getDistanceToTileEntity(spawner) < 10.0f)) continue;
            for (Item itemL : this.garbageitems) {
                if (cur != itemL) continue;
                bad = true;
                continue block0;
            }
        }
        for (int x = spawner.getX() - range; x < spawner.getX() + range; ++x) {
            for (int z = spawner.getZ() - range; z < spawner.getZ() + range; ++z) {
                currentable.add(new BlockPos(x, spawner.getY(), z));
            }
        }
        for (BlockPos poses : currentable) {
            if (!ESP.posSeenSky(poses)) continue;
            bad = true;
        }
        return bad;
    }

    private String[] getSpawnerInfo(TileEntityMobSpawner spawner) {
        Object str = "\u0421\u043f\u0430\u0432\u043d\u0435\u0440";
        String str2 = null;
        String str3 = null;
        spawner.getSpawnerBaseLogic().updateTime();
        int chestCounter = 0;
        for (TileEntity tile : this.getTileEntities(true, false, false, false)) {
            if (!(tile instanceof TileEntityChest) || ((TileEntityChest)tile).getChestType() != BlockChest.Type.BASIC || !(spawner.getDistanceToTileEntity(tile) < 7.0)) continue;
            ++chestCounter;
        }
        if (spawner != null && spawner.getSpawnerBaseLogic() != null && spawner.getSpawnerBaseLogic().cachedEntity != null) {
            if (chestCounter != 0) {
                int g = chestCounter;
                slog = g == 1 || g == 21 || g == 31 || g == 41 || g == 51 ? "" : (g % 5 == 0 || g % 6 == 0 || g % 7 == 0 || g % 8 == 0 || g % 9 == 0 || g % 10 == 0 || g % 11 == 0 || g % 12 == 0 || g % 13 == 0 || g % 14 == 0 || g % 15 == 0 || g % 16 == 0 || g % 17 == 0 || g % 18 == 0 || g % 19 == 0 || g % 20 == 0 ? "\u043e\u0432" : (g % 2 == 0 || g % 3 == 0 || g % 4 == 0 ? "\u0430" : ""));
                str2 = "\u0440\u044f\u0434\u043e\u043c \u0435\u0441\u0442\u044c " + chestCounter + " \u0441\u0443\u043d\u0434\u0443\u043a" + slog;
                if (ESP.isDangeon(spawner)) {
                    str3 = this.dangeonIsLooted(spawner) ? "\u044d\u0442\u043e\u0442 \u0434\u0430\u043d\u0434\u0436 \u0432\u043e\u0437\u043c\u043e\u0436\u043d\u043e \u0437\u0430\u043b\u0443\u0442\u0430\u043d" : "\u044d\u0442\u043e \u043f\u0440\u0438\u0433\u043e\u0434\u043d\u044b\u0439 \u0434\u0430\u043d\u0434\u0436";
                }
            }
            Entity pidorVSpavnere = spawner.getSpawnerBaseLogic().cachedEntity;
            str = pidorVSpavnere.getName().trim();
            str = spawner.getSpawnerBaseLogic().isActivated() ? (String)str + " | \u0434\u043e \u0441\u043f\u0430\u0432\u043d\u0430: " + spawner.getSpawnerBaseLogic().timeToSpawn + "\u0441\u0435\u043a" : (String)str + " | \u043d\u0435 \u0430\u043a\u0442\u0438\u0432\u0435\u043d";
        } else if (chestCounter != 0) {
            int g = chestCounter;
            slog = g == 1 || g == 21 || g == 31 || g == 41 || g == 51 ? "" : (g % 5 == 0 || g % 6 == 0 || g % 7 == 0 || g % 8 == 0 || g % 9 == 0 || g % 10 == 0 || g % 11 == 0 || g % 12 == 0 || g % 13 == 0 || g % 14 == 0 || g % 15 == 0 || g % 16 == 0 || g % 17 == 0 || g % 18 == 0 || g % 19 == 0 || g % 20 == 0 ? "\u043e\u0432" : (g % 2 == 0 || g % 3 == 0 || g % 4 == 0 ? "\u0430" : ""));
            str2 = "\u0440\u044f\u0434\u043e\u043c \u0435\u0441\u0442\u044c " + chestCounter + " \u0441\u0443\u043d\u0434\u0443\u043a" + slog;
            if (ESP.isDangeon(spawner)) {
                String string = str3 = this.dangeonIsLooted(spawner) ? "\u044d\u0442\u043e\u0442 \u0434\u0430\u043d\u0434\u0436 \u0432\u043e\u0437\u043c\u043e\u0436\u043d\u043e \u0437\u0430\u043b\u0443\u0442\u0430\u043d" : "\u044d\u0442\u043e \u043f\u0440\u0438\u0433\u043e\u0434\u043d\u044b\u0439 \u0434\u0430\u043d\u0434\u0436";
            }
        }
        if (str2 != null && str3 != null) {
            return new String[]{str, str2, str3};
        }
        if (str2 != null && str3 == null) {
            return new String[]{str, str2};
        }
        return new String[]{str};
    }

    private int getTileEntityStorageColor(TileEntity storage) {
        int col = -1;
        if (storage instanceof TileEntityChest) {
            col = ColorUtils.getColor(255, 160, 10);
            if (((TileEntityChest)storage).getChestType() == BlockChest.Type.TRAP) {
                col = ColorUtils.getColor(255, 85, 0);
            }
        } else if (storage instanceof TileEntityEnderChest) {
            col = ColorUtils.getColor(120, 0, 160);
        } else if (storage instanceof TileEntityShulkerBox) {
            col = ColorUtils.getColor(255, 48, 255);
        }
        return col;
    }

    public void alwaysPreRender2D(float partialTicks, ScaledResolution sr) {
        this.setupAlphaModule();
        if (ESP.canRenderModule()) {
            this.draw2d(partialTicks, sr);
        }
    }

    @Override
    public void preRenderLivingBase(Entity baseIn, Runnable renderModel, boolean isRenderItems) {
        boolean self;
        boolean crystal = this.EnderCrystals.bValue && this.CrystalMode.currentMode.equalsIgnoreCase("Chams");
        boolean players = this.Players.bValue && this.PlayerMode.currentMode.equalsIgnoreCase("Chams");
        boolean bl = self = this.Self.bValue && this.SelfMode.currentMode.equalsIgnoreCase("Chams");
        if (baseIn instanceof EntityPlayerSP && self || baseIn instanceof EntityOtherPlayerMP && players || baseIn instanceof EntityEnderCrystal && crystal) {
            this.renderChams(true, this.chamsColor(baseIn), renderModel, isRenderItems);
        }
    }

    @Override
    public void postRenderLivingBase(Entity baseIn, Runnable renderModel, boolean isRenderItems) {
        boolean self;
        boolean crystal = this.EnderCrystals.bValue && this.CrystalMode.currentMode.equalsIgnoreCase("Chams");
        boolean players = this.Players.bValue && this.PlayerMode.currentMode.equalsIgnoreCase("Chams");
        boolean bl = self = this.Self.bValue && this.SelfMode.currentMode.equalsIgnoreCase("Chams");
        if (baseIn instanceof EntityPlayerSP && self || baseIn instanceof EntityOtherPlayerMP && players || baseIn instanceof EntityEnderCrystal && crystal) {
            this.renderChams(false, this.chamsColor(baseIn), renderModel, isRenderItems);
        }
    }

    int chamsColor(Entity baseIn) {
        int c = ColorUtils.getOverallColorFrom(ClientColors.getColor1(), -1, 0.019607842f);
        float apc = (baseIn instanceof EntityEnderCrystal ? 0.25f : 0.5f) * ESP.getAlphaPC() * (this.ChamsBright.fValue * 2.0f);
        return ColorUtils.swapAlpha(c, (float)ColorUtils.getAlphaFromColor(c) * apc);
    }

    void renderChams(boolean pre, int col, Runnable renderModel, boolean isRenderItems) {
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, pre ? GlStateManager.DestFactor.ONE : GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        if (pre) {
            if (!isRenderItems) {
                GlStateManager.enableBlend();
                GL11.glDisable(3553);
                GL11.glDisable(3008);
                GL11.glEnable(2884);
                RenderUtils.glColor(col);
                GL11.glDepthMask(false);
                ESP.mc.entityRenderer.disableLightmap();
            }
            GL11.glDepthRange(0.0, 0.01);
            renderModel.run();
            GL11.glDepthRange(0.0, 1.0);
            if (!isRenderItems) {
                ESP.mc.entityRenderer.enableLightmap();
                GL11.glDepthMask(true);
                GL11.glEnable(3553);
                GL11.glEnable(3008);
                GlStateManager.resetColor();
                GlStateManager.color(1.0f, 1.0f, 1.0f);
            }
        }
        renderModel.run();
    }

    @Override
    public void alwaysRender3DV2(float partialTicks) {
        if (ESP.canRenderModule()) {
            this.draw3d(partialTicks);
        }
    }

    private void draw2d(float partialTicks, ScaledResolution sr) {
        boolean players = this.Players.bValue && this.PlayerMode.currentMode.equalsIgnoreCase("2D");
        boolean self = this.Self.bValue && this.SelfMode.currentMode.equalsIgnoreCase("2D");
        boolean players2 = this.Players.bValue && this.PlayerMode.currentMode.equalsIgnoreCase("Glow");
        boolean self2 = this.Self.bValue && this.SelfMode.currentMode.equalsIgnoreCase("Glow");
        boolean items = this.ItemsI.bValue;
        boolean pearl = this.EnderPearl.bValue;
        boolean crystal = this.EnderCrystals.bValue && this.CrystalMode.currentMode.equalsIgnoreCase("Glow");
        boolean beacons = this.Beacon.bValue;
        boolean spawner = this.Spawner.bValue;
        boolean tnt = this.TntPrimed.bValue && this.TntMode.currentMode.equalsIgnoreCase("2D");
        boolean tnt2 = this.TntPrimed.bValue && this.TntMode.currentMode.equalsIgnoreCase("Glow");
        boolean targets = this.Targets.bValue;
        GL11.glEnable(3042);
        if (this.getEntities(players2, self2, false, crystal, false, tnt2).size() != 0) {
            this.drawGlows();
        }
        int scaleFactor = ScaledResolution.getScaleFactor();
        double scaling = (double)scaleFactor / Math.pow(scaleFactor, 2.0);
        EntityRenderer entityRenderer = ESP.mc.entityRenderer;
        if (this.getTileEntities(false, beacons, spawner, false).size() != 0) {
            GL11.glPushMatrix();
            GL11.glScaled(scaling, scaling, scaling);
            for (TileEntity tile : this.getTileEntities(false, beacons, spawner, false)) {
                double x = (double)tile.getX() + 0.5;
                double y = tile.getY();
                double z = (double)tile.getZ() + 0.5;
                double height = 1.0;
                double width = 1.0;
                AxisAlignedBB aabb = new AxisAlignedBB(x - 0.5, y, z - 0.5, x + 0.5, y + 1.0, z + 0.5);
                Vector3d[] vectors = new Vector3d[]{new Vector3d(aabb.minX, aabb.minY, aabb.minZ), new Vector3d(aabb.minX, aabb.maxY, aabb.minZ), new Vector3d(aabb.maxX, aabb.minY, aabb.minZ), new Vector3d(aabb.maxX, aabb.maxY, aabb.minZ), new Vector3d(aabb.minX, aabb.minY, aabb.maxZ), new Vector3d(aabb.minX, aabb.maxY, aabb.maxZ), new Vector3d(aabb.maxX, aabb.minY, aabb.maxZ), new Vector3d(aabb.maxX, aabb.maxY, aabb.maxZ)};
                entityRenderer.setupCameraTransform(partialTicks, 0);
                Vector4d position = null;
                Vector3d[] vecs3 = vectors;
                int vecLength = vectors.length;
                for (int vecI = 0; vecI < vecLength; ++vecI) {
                    Vector3d vector = vecs3[vecI];
                    vector = this.project2D(scaleFactor, vector.x - RenderManager.viewerPosX, vector.y - RenderManager.viewerPosY, vector.z - RenderManager.viewerPosZ);
                    if (vector == null || !(vector.z >= 0.0) || !(vector.z < 1.0)) continue;
                    if (position == null) {
                        position = new Vector4d(vector.x, vector.y, vector.z, 0.0);
                    }
                    position.x = Math.min(vector.x, position.x);
                    position.y = Math.min(vector.y, position.y);
                    position.z = Math.max(vector.x, position.z);
                    position.w = Math.max(vector.y, position.w);
                }
                entityRenderer.setupOverlayRendering();
                if (position == null || !(position.w > 1.0) || !(position.z > 1.0) || !(position.x < (double)(sr.getScaledWidth() - 1)) || !(position.y < (double)(sr.getScaledHeight() - 1))) continue;
                this.drawEspM(position.x, position.y, position.z, position.w, tile);
            }
            GL11.glPopMatrix();
        }
        if (this.getEntities(players, self, items, false, pearl, tnt).size() != 0) {
            GL11.glPushMatrix();
            GL11.glScaled(scaling, scaling, scaling);
            Iterator<Object> iterator = this.getEntities(players, self, items, false, pearl, tnt).iterator();
            while (iterator.hasNext()) {
                Entity entity;
                Entity minik = entity = (Entity)iterator.next();
                double x = RenderUtils.interpolate(entity.posX, entity.prevPosX, partialTicks);
                double y = RenderUtils.interpolate(entity.posY, entity.prevPosY, partialTicks);
                double z = RenderUtils.interpolate(entity.posZ, entity.prevPosZ, partialTicks);
                double height = entity.height / (minik instanceof EntityLivingBase && ((EntityLivingBase)minik).isChild() ? 1.75f : 1.0f) + 0.1f;
                double width = entity.width / (minik instanceof EntityLivingBase && ((EntityLivingBase)minik).isChild() ? 1.5f : 1.0f);
                AxisAlignedBB aabb = new AxisAlignedBB(x - width / 2.0, y, z - width / 2.0, x + width / 2.0, y + height, z + width / 2.0);
                Vector3d[] vectors = new Vector3d[]{new Vector3d(aabb.minX, aabb.minY, aabb.minZ), new Vector3d(aabb.minX, aabb.maxY, aabb.minZ), new Vector3d(aabb.maxX, aabb.minY, aabb.minZ), new Vector3d(aabb.maxX, aabb.maxY, aabb.minZ), new Vector3d(aabb.minX, aabb.minY, aabb.maxZ), new Vector3d(aabb.minX, aabb.maxY, aabb.maxZ), new Vector3d(aabb.maxX, aabb.minY, aabb.maxZ), new Vector3d(aabb.maxX, aabb.maxY, aabb.maxZ)};
                entityRenderer.setupCameraTransform(partialTicks, 0);
                Vector4d position = null;
                Vector3d[] vecs3 = vectors;
                int vecLength = vectors.length;
                for (int vecI = 0; vecI < vecLength; ++vecI) {
                    Vector3d vector = vecs3[vecI];
                    vector = this.project2D(scaleFactor, vector.x - RenderManager.viewerPosX, vector.y - RenderManager.viewerPosY, vector.z - RenderManager.viewerPosZ);
                    if (vector == null || !(vector.z >= 0.0) || !(vector.z < 1.0)) continue;
                    if (position == null) {
                        position = new Vector4d(vector.x, vector.y, vector.z, 0.0);
                    }
                    position.x = Math.min(vector.x, position.x);
                    position.y = Math.min(vector.y, position.y);
                    position.z = Math.max(vector.x, position.z);
                    position.w = Math.max(vector.y, position.w);
                }
                entityRenderer.setupOverlayRendering();
                if (position == null || !(position.w > 1.0) || !(position.z > 1.0) || !(position.x < (double)(sr.getScaledWidth() - 1)) || !(position.y < (double)(sr.getScaledHeight() - 1))) continue;
                this.drawEspS(position.x, position.y, position.z, position.w, entity);
            }
            GL11.glPopMatrix();
        }
        if (!(!targets || this.getTargets().isEmpty() && this.colVecsWithEntList.isEmpty() || this.TargetsMode.currentMode.equalsIgnoreCase("Hologram"))) {
            List colVecsWithEntList1 = this.getTargets().stream().filter(Objects::nonNull).map(target -> this.targetESPSPos((EntityLivingBase)target, 0)).filter(Objects::nonNull).collect(Collectors.toList());
            colVecsWithEntList1.stream().filter(Objects::nonNull).filter(colVecsWithEnt -> this.colVecsWithEntList.stream().filter(Objects::nonNull).noneMatch(cv -> cv.getEntity().getEntityId() == colVecsWithEnt.getEntity().getEntityId())).forEach(colVecsWithEnt -> this.colVecsWithEntList.add((ColVecsWithEnt)colVecsWithEnt));
            this.colVecsWithEntList.stream().filter(Objects::nonNull).forEach(colVecsWithEnt -> colVecsWithEnt.update(colVecsWithEntList1));
            this.colVecsWithEntList.stream().filter(Objects::nonNull).filter(Objects::nonNull).collect(Collectors.toList()).removeIf(ColVecsWithEnt::toRemove);
            this.colVecsWithEntList.stream().filter(Objects::nonNull).forEach(colVecsWithEnt -> this.drawTargets2D((ColVecsWithEnt)colVecsWithEnt));
        }
        if (!this.TESP_LIST.isEmpty()) {
            this.TESP_LIST.forEach(tesp -> tesp.drawTESP(Integer.MIN_VALUE, this.getTargetColor(tesp.getEntity(), 0), -1));
        }
    }

    private int getTargetColor(EntityLivingBase target, int index) {
        if (target != null) {
            float aPC = ESP.getAlphaPC();
            this.targetColor = ColorUtils.swapAlpha(ClientColors.getColor1(index), (float)ColorUtils.getAlphaFromColor(ClientColors.getColor1(index)) * aPC);
            if (this.TargetColor.currentMode.equalsIgnoreCase("Pick color")) {
                int c1 = this.PickNormal.color;
                this.targetColor = ColorUtils.swapAlpha(c1, (float)ColorUtils.getAlphaFromColor(c1) * aPC);
            } else if (this.TargetColor.currentMode.equalsIgnoreCase("Pick color & hurt")) {
                float pcH = (float)target.hurtTime / 10.0f;
                int c1 = this.PickNormal.color;
                c1 = ColorUtils.swapAlpha(c1, (float)ColorUtils.getAlphaFromColor(c1) * aPC);
                int c2 = this.PickHurt.color;
                c2 = ColorUtils.swapAlpha(c2, (float)ColorUtils.getAlphaFromColor(c2) * aPC);
                this.targetColor = ColorUtils.getOverallColorFrom(c1, c2, pcH);
            }
        }
        return this.targetColor;
    }

    private void drawGlows() {
        ESP.mc.gameSettings.ofFastRender = false;
        if (Config.isShaders()) {
            return;
        }
        float radiusShadow = this.GlowRadius.fValue * 2.0f - 1.5f;
        float aPC = ESP.getAlphaPC() * (this.GlowBloom.bValue ? (this.BloomAmount.fValue - 0.35f) * 1.3f : 1.0f);
        if (this.framebuffer != null) {
            boolean bloom = this.GlowBloom.bValue;
            GlStateManager.pushMatrix();
            GlStateManager.alphaFunc(516, 0.0f);
            GlStateManager.enableBlend();
            OpenGlHelper.glBlendFunc(770, bloom ? 1 : 771, 1, 0);
            this.glowFrameBuffer.framebufferClear();
            this.glowFrameBuffer.bindFramebuffer(false);
            this.glowShader.attach();
            this.setupGlowShader(radiusShadow, aPC);
            this.setupGlowDirs(1.0f, 0.0f);
            this.framebuffer.bindFramebufferTexture();
            ShaderUtility.drawQuads();
            mc.getFramebuffer().bindFramebuffer(false);
            this.setupGlowDirs(0.0f, 1.0f);
            GlStateManager.setActiveTexture(34000);
            this.framebuffer.bindFramebufferTexture();
            GlStateManager.setActiveTexture(33984);
            this.glowFrameBuffer.bindFramebufferTexture();
            GL11.glDisable(2929);
            this.drawGLTex(0.0f, 0.0f);
            GL11.glEnable(2929);
            this.glowShader.detach();
            GlStateManager.bindTexture(0);
            if (bloom) {
                OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            }
            GlStateManager.alphaFunc(516, 0.1f);
            GlStateManager.enableDepth();
            GlStateManager.disableLighting();
            GlStateManager.popMatrix();
        }
    }

    private void drawGLTex(float pedzX, float pedzY) {
        float width = (float)ESP.mc.displayWidth / 2.0f;
        float height = (float)ESP.mc.displayHeight / 2.0f;
        GL11.glBegin(6);
        GL11.glTexCoord2d(0.0, 1.0);
        GL11.glVertex2d(pedzX, pedzY);
        GL11.glTexCoord2d(0.0, 0.0);
        GL11.glVertex2d(pedzX, height + pedzY);
        GL11.glTexCoord2d(1.0, 0.0);
        GL11.glVertex2d(width + pedzX, height + pedzY);
        GL11.glTexCoord2d(1.0, 1.0);
        GL11.glVertex2d(width + pedzX, pedzY);
        GL11.glEnd();
    }

    private void drawPlayerESP(double x, double y, double x2, double y2, Entity ent) {
        if (ent instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)ent;
            if (ent.ticksExisted > 3) {
                float alphaPercent = (ent instanceof EntityPlayerSP ? 1.0f : MathUtils.clamp(Minecraft.player.getSmoothDistanceToEntity(player) / 5.0f * (Minecraft.player.getSmoothDistanceToEntity(player) / 5.0f), 0.0f, 1.0f)) * ESP.getAlphaPC();
                int c1 = ClientColors.getColor1(0);
                int c2 = ClientColors.getColor2(100);
                int color = ColorUtils.swapAlpha(c1, (float)ColorUtils.getAlphaFromColor(c1) * alphaPercent);
                int color2 = ColorUtils.swapAlpha(c2, (float)ColorUtils.getAlphaFromColor(c2) * alphaPercent);
                int bgCol = ColorUtils.getColor(0, 0, 0, (float)ColorUtils.getAlphaFromColor(ColorUtils.getOverallColorFrom(color, color2)) * alphaPercent * 0.5f);
                float offset = -0.5f;
                RenderUtils.drawLightContureRect(x - (double)offset, y - (double)offset, x2 + (double)offset, y2 + (double)offset, ColorUtils.getColor(0, 0, 0, (float)ColorUtils.getAlphaFromColor(color) * alphaPercent));
                RenderUtils.drawLightContureRectFullGradient((float)(x - (double)(offset += 0.5f)), (float)(y - (double)offset), (float)(x2 + (double)offset), (float)(y2 + (double)offset), color, color2, false);
                RenderUtils.drawLightContureRectSmooth(x - (double)(offset += 0.5f), y - (double)offset, x2 + (double)offset, y2 + (double)offset, ColorUtils.getColor(0, 0, 0, (float)ColorUtils.getAlphaFromColor(color) * alphaPercent));
                offset = -0.5f;
                float hpPC = MathUtils.clamp((MathUtils.getDifferenceOf(player.getSmoothHealth(), player.getHealth()) < (double)0.1f ? player.getHealth() : player.getSmoothHealth()) / player.getMaxHealth(), 0.0f, 1.0f);
                float diffYCoord = (float)MathUtils.clamp(y2 - y + 1.0, 0.0, 1.0E7);
                float diffFHP = diffYCoord * hpPC;
                boolean showHpText = (int)(y2 - y + 1.0 - (double)diffFHP) > 10;
                int extXMinus = 4;
                boolean w = true;
                int colorHPOverall = ColorUtils.getOverallColorFrom(color2, color, hpPC);
                RenderUtils.drawFullGradientRectPro((float)(x - (double)offset - (double)extXMinus) - 0.5f, (float)(y2 - (double)offset - (double)diffFHP), (float)(x + (double)offset - (double)extXMinus + (double)w) + 1.0f, (float)(y2 + (double)offset) + 1.0f, color2, color2, colorHPOverall, colorHPOverall, false);
                RenderUtils.drawLightContureRect(x - (double)(offset += 0.5f) - (double)extXMinus, y2 - (double)offset - (double)diffYCoord + 0.5, x + (double)offset - (double)extXMinus + (double)w, y2 + (double)offset + 0.5, ColorUtils.getColor(0, 0, 0, (float)ColorUtils.getAlphaFromColor(color) * alphaPercent));
                color = ColorUtils.swapAlpha(color, (float)ColorUtils.getAlphaFromColor(color) / 4.0f);
                color2 = ColorUtils.swapAlpha(color2, (float)ColorUtils.getAlphaFromColor(color2) / 4.0f);
                RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool((float)(x - (double)offset), (float)(y - (double)offset), (float)(x2 + (double)offset), (float)(y2 + (double)offset), 1.0f, 8.0f, color, color2, color, color2, true, false, true);
                if (showHpText) {
                    int hpTex = ColorUtils.swapAlpha(ColorUtils.getFixedWhiteColor(), 255.0f * alphaPercent);
                    if (255.0f * alphaPercent >= 33.0f) {
                        CFontRenderer fontUsed = Fonts.time_14;
                        String hpString = String.format("%.1f", Float.valueOf(hpPC * player.getMaxHealth())).replace(".0", "");
                        fontUsed.drawStringWithShadow(hpString, x - (double)offset - (double)extXMinus - (double)fontUsed.getStringWidth(hpString) - 2.0, (float)(y2 - (double)offset - (double)diffFHP) + 3.5f, hpTex);
                    }
                }
            }
        }
    }

    private static void drawItemESP(double x, double y, double x2, double y2, Entity ent) {
        if (ent instanceof EntityItem) {
            EntityItem itemEnt = (EntityItem)ent;
            if (itemEnt.ticksExisted > 1) {
                CFontRenderer fontUsed = Fonts.comfortaaBold_12;
                float alphaPercent = MathUtils.clamp(Minecraft.player.getSmoothDistanceToEntity(itemEnt) / 2.0f * (Minecraft.player.getSmoothDistanceToEntity(itemEnt) / 2.0f), 0.0f, 1.0f) * ESP.getAlphaPC() * MathUtils.clamp((float)itemEnt.ticksExisted / 20.0f, 0.0f, 1.0f);
                ItemStack item = itemEnt.getEntityItem();
                Object prefex = item.getDisplayName();
                if (item.stackSize > 1 && !((String)prefex).isEmpty()) {
                    prefex = (String)prefex + " \u00a7cx" + item.stackSize;
                }
                float w = fontUsed.getStringWidth((String)prefex);
                int bgColor1 = ColorUtils.swapAlpha(ColorUtils.getOverallColorFrom(ColorUtils.getColor(0), ClientColors.getColor1(), 0.15f), 85.0f * alphaPercent);
                int bgColor2 = ColorUtils.swapAlpha(ColorUtils.getOverallColorFrom(ColorUtils.getColor(0), ClientColors.getColor2(), 0.15f), 85.0f * alphaPercent);
                int bgOutColor1 = ColorUtils.swapAlpha(ColorUtils.getOverallColorFrom(ColorUtils.getColor(0), ClientColors.getColor1(), 0.4f), 185.0f * alphaPercent);
                int bgOutColor2 = ColorUtils.swapAlpha(ColorUtils.getOverallColorFrom(ColorUtils.getColor(0), ClientColors.getColor2(), 0.4f), 185.0f * alphaPercent);
                RenderUtils.drawAlphedSideways((float)(x + (x2 - x) / 2.0 - (double)(w / 2.0f)) - 0.5f, y - 8.0, (float)(x + (x2 - x) / 2.0 + (double)(w / 2.0f)) + 0.5f, y - 1.0, bgColor1, bgColor2);
                RenderUtils.drawLightContureRectSidewaysSmooth((float)(x + (x2 - x) / 2.0 - (double)(w / 2.0f)) - 0.5f, y - 8.0, (float)(x + (x2 - x) / 2.0 + (double)(w / 2.0f)) + 0.5f, y - 1.0, bgOutColor1, bgOutColor2);
                if (255.0f * alphaPercent >= 32.0f) {
                    fontUsed.drawString((String)prefex, (float)(x + (x2 - x) / 2.0 - (double)(w / 2.0f)), y - 6.0, ColorUtils.getColor(255, 255, 255, 255.0f * alphaPercent));
                }
                RenderUtils.resetBlender();
                GlStateManager.enableTexture2D();
            }
        }
    }

    private void drawTntPrimedsESP(double x, double y, double x2, double y2, Entity ent) {
        if (ent instanceof EntityTNTPrimed && ent.ticksExisted < 81) {
            float timePC = 1.0f - (float)ent.ticksExisted / 80.0f;
            float scalePush = MathUtils.clamp((1.0f - timePC - 0.9f) * 9.0f, 0.0f, 1.0f);
            float scale = 1.0f + scalePush / 5.0f;
            float alphaPercent = MathUtils.clamp(Minecraft.player.getSmoothDistanceToEntity(ent) * Minecraft.player.getSmoothDistanceToEntity(ent), 0.0f, 1.0f) * ESP.getAlphaPC();
            int c1 = ClientColors.getColor1(0);
            int c2 = ClientColors.getColor2(90);
            int color = ColorUtils.swapAlpha(c1, (float)ColorUtils.getAlphaFromColor(c1) * alphaPercent);
            int color2 = ColorUtils.swapAlpha(c2, (float)ColorUtils.getAlphaFromColor(c1) * alphaPercent);
            int bgCol = ColorUtils.getColor(0, 0, 0, (float)ColorUtils.getAlphaFromColor(ColorUtils.getOverallColorFrom(color, color2)) * alphaPercent);
            float offset = -1.5f;
            float yMinus = 6.0f;
            RenderUtils.customScaledObject2D((float)(x + (x2 - x) / 2.0), (float)y - yMinus, 0.0f, yMinus, scale);
            RenderUtils.drawLightContureRect(x - (double)offset, y - (double)yMinus - (double)offset, x2 + (double)offset, y + (double)offset, ColorUtils.getColor(0, 0, 0, (float)ColorUtils.getAlphaFromColor(color) * alphaPercent));
            RenderUtils.drawLightContureRectFullGradient((float)(x - (double)(offset += 0.5f)), (float)(y - (double)yMinus - (double)offset), (float)(x2 + (double)offset), (float)(y + (double)offset), color, color2, false);
            RenderUtils.drawLightContureRectSmooth(x - (double)(offset += 0.5f), y - (double)yMinus - (double)offset, x2 + (double)offset, y + (double)offset, ColorUtils.getColor(0, 0, 0, (float)ColorUtils.getAlphaFromColor(color) * alphaPercent));
            offset = 0.0f;
            RenderUtils.drawAlphedSideways((float)x + 2.0f, (float)(y - (double)yMinus - (double)offset) + 2.0f, (float)(x + 2.0 + (x2 - x - 4.0) * (double)timePC), (float)y + offset - 2.0f, color, color2);
            color = ColorUtils.swapAlpha(color, (float)ColorUtils.getAlphaFromColor(color) / 4.0f);
            color2 = ColorUtils.swapAlpha(color2, (float)ColorUtils.getAlphaFromColor(color2) / 4.0f);
            RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool((float)(x - (double)offset), (float)(y - (double)yMinus - (double)offset), (float)(x2 + (double)offset), (float)(y + (double)offset), 1.0f, 8.0f, color, color2, color, color2, true, false, true);
            RenderUtils.fixShadows();
            RenderUtils.drawLightContureRectSmooth((float)x + 2.0f, (float)(y - (double)yMinus - (double)offset) + 2.0f, (float)(x + 2.0 + (x2 - x - 4.0) * (double)timePC), (float)y + offset - 2.0f, ColorUtils.getColor(0, 0, 0, (float)ColorUtils.getAlphaFromColor(color) * alphaPercent));
            RenderUtils.customScaledObject2D((float)(x + (x2 - x) / 2.0), (float)y - yMinus, 0.0f, yMinus, 1.0f / scale);
        }
    }

    private void drawEspS(double x, double y, double x2, double y2, Entity ent) {
        this.drawPearlsESP(x, y, x2, y2, ent);
        ESP.drawItemESP(x, y, x2, y2, ent);
        this.drawPlayerESP(x, y, x2, y2, ent);
        this.drawTntPrimedsESP(x, y, x2, y2, ent);
    }

    public void drawModalRectWithCustomSizedTexture(float x, float y, float u, float v, int width, int height, float textureWidth, float textureHeight) {
        float f = 1.0f / textureWidth;
        float f1 = 1.0f / textureHeight;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(9, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(x, y + (float)height, 0.0).tex(0.0, 1.0).endVertex();
        bufferbuilder.pos(x + (float)width, y + (float)height, 0.0).tex(1.0, 1.0).endVertex();
        bufferbuilder.pos(x + (float)width, y, 0.0).tex(1.0, 0.0).endVertex();
        bufferbuilder.pos(x, y, 0.0).tex(0.0, 0.0).endVertex();
        tessellator.draw();
    }

    private void drawPearlsESP(double x, double y, double x2, double y2, Entity ent) {
        if (!(ent instanceof EntityEnderPearl)) {
            return;
        }
        float texW = 22.0f;
        float texH = 28.0f;
        float texX = (float)(x + (x2 - x) / 2.0 - (double)(texW / 2.0f));
        float texY = (float)(y - (double)texH);
        int color1 = ClientColors.getColor1(0);
        int color2 = ClientColors.getColor2(-90);
        int color3 = ClientColors.getColor2(0);
        int color4 = ClientColors.getColor1(240);
        mc.getTextureManager().bindTexture(this.PEARL_MARK_TEXTURE);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(9, DefaultVertexFormats.POSITION_TEX_COLOR);
        bufferbuilder.pos(texX, texY + texH).tex(0.0, 1.0).color(color1).endVertex();
        bufferbuilder.pos(texX + texW, texY + texH).tex(1.0, 1.0).color(color2).endVertex();
        bufferbuilder.pos(texX + texW, texY).tex(1.0, 0.0).color(color3).endVertex();
        bufferbuilder.pos(texX, texY).tex(0.0, 0.0).color(color4).endVertex();
        GL11.glShadeModel(7425);
        tessellator.draw();
        GL11.glTranslated(texX + 2.5f, texY + 5.5f, 0.0);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        RenderUtils.enableStandardItemLighting();
        mc.getRenderItem().renderItemAndEffectIntoGUI(new ItemStack(Items.ENDER_PEARL), 0, 0);
        RenderUtils.disableStandardItemLighting();
        GL11.glDepthMask(false);
        GL11.glDisable(2929);
        GL11.glTranslated(-(texX + 2.5f), -(texY + 5.5f), 0.0);
    }

    private void drawEspM(double x, double y, double x2, double y2, TileEntity tile) {
        if (tile instanceof TileEntityBeacon) {
            this.drawBeacon(x, y, x2, y2, (TileEntityBeacon)tile);
        }
        if (tile instanceof TileEntityMobSpawner) {
            this.drawSpawner(x, y, x2, y2, (TileEntityMobSpawner)tile);
        }
    }

    private void drawBeacon(double x, double y, double x2, double y2, TileEntityBeacon beacon) {
        if (beacon != null) {
            double dst = Minecraft.player.getSmoothDistanceToTileEntity(beacon);
            float alphaPercent = MathUtils.clamp(Minecraft.player.getSmoothDistanceToTileEntity(beacon) / 5.0f * (Minecraft.player.getSmoothDistanceToTileEntity(beacon) / 5.0f), 0.0f, 1.0f) * ESP.getAlphaPC();
            int lvl = beacon.getLevels();
            int distMax = 11 + 10 * lvl;
            CFontRenderer fontUsed = Fonts.noise_14;
            int c1 = ClientColors.getColor1();
            int c2 = ClientColors.getColor2(90);
            int color = ColorUtils.swapAlpha(c1, (float)ColorUtils.getAlphaFromColor(c1) * alphaPercent);
            int color2 = ColorUtils.swapAlpha(c2, (float)ColorUtils.getAlphaFromColor(c1) * alphaPercent);
            int bgCol = ColorUtils.getColor(0, 0, 0, (float)ColorUtils.getAlphaFromColor(ColorUtils.getOverallColorFrom(color, color2)) * alphaPercent * 0.5f);
            float offset = -0.5f;
            RenderUtils.drawLightContureRect(x - (double)offset, y - (double)offset, x2 + (double)offset, y2 + (double)offset, ColorUtils.getColor(0, 0, 0, (float)ColorUtils.getAlphaFromColor(color) * alphaPercent));
            RenderUtils.drawLightContureRectFullGradient((float)(x - (double)(offset += 0.5f)), (float)(y - (double)offset), (float)(x2 + (double)offset), (float)(y2 + (double)offset), color, color2, false);
            RenderUtils.drawLightContureRectSmooth(x - (double)(offset += 0.5f), y - (double)offset, x2 + (double)offset, y2 + (double)offset, ColorUtils.getColor(0, 0, 0, (float)ColorUtils.getAlphaFromColor(color) * alphaPercent));
            offset = 0.0f;
            color = ColorUtils.swapAlpha(color, (float)ColorUtils.getAlphaFromColor(color) / 4.0f);
            color2 = ColorUtils.swapAlpha(color2, (float)ColorUtils.getAlphaFromColor(color2) / 4.0f);
            RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool((float)(x - (double)offset), (float)(y - (double)offset), (float)(x2 + (double)offset), (float)(y2 + (double)offset), 1.0f, 8.0f, color, color2, color, color2, true, false, true);
            double ys = y - 12.0;
            Object zalupa = " dist:" + (int)MathUtils.clamp(dst - (double)distMax, 0.0, 1000.0);
            if (((String)zalupa).contains(":0")) {
                zalupa = " voted";
            }
            String beaconInfo = "\u041c\u0430\u044f\u043a: lvl:" + lvl + (String)zalupa;
            float w = fontUsed.getStringWidth(beaconInfo) / 2;
            fontUsed.drawClientColoredString(beaconInfo, x + (x2 - x) / 2.0 - (double)w, ys, alphaPercent, true);
        }
    }

    private void drawSpawner(double x, double y, double x2, double y2, TileEntityMobSpawner spawner) {
        if (spawner != null) {
            CFontRenderer fontUsed = Fonts.noise_14;
            CFontRenderer fontUsed2 = Fonts.mntsb_12;
            float alphaPercent = MathUtils.clamp(Minecraft.player.getSmoothDistanceToTileEntity(spawner) / 2.0f * (Minecraft.player.getSmoothDistanceToTileEntity(spawner) / 2.0f), 0.0f, 1.0f) * ESP.alphaPC.anim;
            int c1 = ClientColors.getColor1();
            int c2 = ClientColors.getColor2();
            int color = ColorUtils.swapAlpha(c1, (float)ColorUtils.getAlphaFromColor(c1) * alphaPercent);
            int color2 = ColorUtils.swapAlpha(c2, (float)ColorUtils.getAlphaFromColor(c1) * alphaPercent);
            float offset = -0.5f;
            RenderUtils.drawLightContureRect(x - (double)offset, y - (double)offset, x2 + (double)offset, y2 + (double)offset, ColorUtils.getColor(0, 0, 0, (float)ColorUtils.getAlphaFromColor(color) * alphaPercent));
            RenderUtils.drawLightContureRectFullGradient((float)(x - (double)(offset += 0.5f)), (float)(y - (double)offset), (float)(x2 + (double)offset), (float)(y2 + (double)offset), color, color2, false);
            RenderUtils.drawLightContureRectSmooth(x - (double)(offset += 0.5f), y - (double)offset, x2 + (double)offset, y2 + (double)offset, ColorUtils.getColor(0, 0, 0, (float)ColorUtils.getAlphaFromColor(color) * alphaPercent));
            offset = 0.0f;
            color = ColorUtils.swapAlpha(color, (float)ColorUtils.getAlphaFromColor(color) / 4.0f);
            color2 = ColorUtils.swapAlpha(color2, (float)ColorUtils.getAlphaFromColor(color2) / 4.0f);
            RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool((float)(x - (double)offset), (float)(y - (double)offset), (float)(x2 + (double)offset), (float)(y2 + (double)offset), 1.0f, 8.0f, color, color2, color, color2, true, false, true);
            String[] info = this.getSpawnerInfo(spawner);
            double ys = y - 9.0 - (double)(7.5f * (float)(info.length - 1));
            int i = 0;
            for (String str : info) {
                CFontRenderer secondFont = i == 0 ? fontUsed : fontUsed2;
                float w = (float)secondFont.getStringWidth(str) / 2.0f;
                secondFont.drawClientColoredString(str, x + (x2 - x) / 2.0 - (double)w, ys, alphaPercent, true);
                ys += 7.5;
                ++i;
            }
        }
    }

    private void renderPlayers(float partialTicks, List<Entity> players) {
        players.forEach(l -> {
            if (l != null && l.isEntityAlive()) {
                boolean e = l.getAlwaysRenderNameTag();
                l.setAlwaysRenderNameTag(false);
                mc.getRenderManager().renderEntityStaticNoShadow((Entity)l, partialTicks, true);
                l.setAlwaysRenderNameTag(e);
            }
        });
    }

    private void renderOverSelect(float alphaPC) {
        boolean isHitting = ESP.mc.playerController.getIsHittingBlock();
        float progressUpdated01 = ESP.mc.playerController.curBlockDamageMP;
        this.alphaSelectPC.to = isHitting ? 1.0f : 0.0f;
        this.alphaSelectPC.speed = isHitting ? 0.1f : 0.05f;
        float alphaIn = this.alphaSelectPC.getAnim();
        this.progressingSelect.to = MathUtils.clamp(progressUpdated01 * 1.005f, 0.0f, 1.0f);
        this.progressingSelect.speed = isHitting ? 0.1f : 0.145f;
        float smoothProgressing = this.progressingSelect.getAnim();
        float speedAnim = (float)Minecraft.frameTime * 0.01f;
        this.smoothPosSelect.xCoord = MathUtils.getDifferenceOf(this.smoothPosSelect.xCoord, (double)this.posOver.getX()) > 6.0 ? (double)this.posOver.getX() : (double)MathUtils.lerp((float)this.smoothPosSelect.xCoord, this.posOver.getX(), speedAnim);
        this.smoothPosSelect.yCoord = MathUtils.getDifferenceOf(this.smoothPosSelect.yCoord, (double)this.posOver.getY()) > 6.0 ? (double)this.posOver.getY() : (double)MathUtils.lerp((float)this.smoothPosSelect.yCoord, this.posOver.getY(), speedAnim);
        double d = this.smoothPosSelect.zCoord = MathUtils.getDifferenceOf(this.smoothPosSelect.zCoord, (double)this.posOver.getZ()) > 6.0 ? (double)this.posOver.getZ() : (double)MathUtils.lerp((float)this.smoothPosSelect.zCoord, this.posOver.getZ(), speedAnim);
        if (alphaPC * alphaIn >= 0.05f) {
            this.setup3dFor(() -> this.drawBlockPos(this.getAxisExtended(this.posOver, ESP.mc.world.getBlockState(this.posOver).getSelectedBoundingBox(ESP.mc.world, this.posOver), smoothProgressing, this.smoothPosSelect), ColorUtils.swapAlpha(ClientColors.getColor1(), (float)ColorUtils.getAlphaFromColor(ClientColors.getColor1()) * alphaIn * alphaPC)));
        }
    }

    private AxisAlignedBB getStorageTileAABB(TileEntity storage) {
        TileEntity chest;
        BlockPos pos = storage.getPos();
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        Vec3d vec2 = new Vec3d(x, y, z);
        Vec3d vec = new Vec3d(x, y, z);
        if (storage instanceof TileEntityChest) {
            chest = (TileEntityChest)storage;
            if (chest.adjacentChestZNeg != null) {
                vec = new Vec3d((double)x + 0.0625, y, (double)z - 0.9375);
                vec2 = new Vec3d((double)x + 0.9375, (double)y + 0.875, (double)z + 0.9375);
            } else if (chest.adjacentChestXNeg != null) {
                vec = new Vec3d((double)x + 0.9375, y, (double)z + 0.0625);
                vec2 = new Vec3d((double)x - 0.9375, (double)y + 0.875, (double)z + 0.9375);
            } else if (chest.adjacentChestXPos == null && chest.adjacentChestZPos == null) {
                vec = new Vec3d((double)x + 0.0625, y, (double)z + 0.0625);
                vec2 = new Vec3d((double)x + 0.9375, (double)y + 0.875, (double)z + 0.9375);
            }
        }
        if (storage instanceof TileEntityEnderChest) {
            chest = (TileEntityEnderChest)storage;
            vec = new Vec3d((double)x + 0.0625, y, (double)z + 0.0625);
            vec2 = new Vec3d((double)x + 0.9375, (double)y + 0.875, (double)z + 0.9375);
        }
        if (storage instanceof TileEntityShulkerBox) {
            chest = (TileEntityShulkerBox)storage;
            vec = new Vec3d(x, y, z);
            AxisAlignedBB aabbs = ESP.mc.world.getBlockState(pos).getSelectedBoundingBox(ESP.mc.world, pos);
            double h = aabbs.maxY - aabbs.minY;
            vec2 = new Vec3d(x + 1, (double)y + h, z + 1);
        }
        return new AxisAlignedBB(vec.xCoord, vec.yCoord, vec.zCoord, vec2.xCoord, vec2.yCoord, vec2.zCoord);
    }

    private void drawStorages(float alphaPC) {
        this.setup3dFor(() -> this.getTileEntities(true, false, false, false).forEach(l -> this.drawStorageEsp((TileEntity)l, alphaPC)));
    }

    private void drawStorageEsp(TileEntity storage, float alphaPC) {
        if (storage != null) {
            double distance = Minecraft.player == null ? 0.0 : (double)Minecraft.player.getSmoothDistanceToTileEntity(storage);
            float dstPCAlpha = (float)MathUtils.clamp(distance / 10.0 * (distance / 10.0), 0.0, 1.0);
            float yawDiff = (float)MathUtils.getDifferenceOf(Minecraft.player.rotationYaw, RotationUtil.getNeededFacing(new Vec3d(storage.getX(), storage.getY(), storage.getZ()).addVector(0.5, 0.5, 0.5), false, Minecraft.player, false)[0]);
            yawDiff = (float)((double)yawDiff * MathUtils.clamp((double)1.3f - (45.0 - MathUtils.getDifferenceOf(Minecraft.player.rotationPitch, 0.0f)) / 90.0, 0.0, 1.0));
            float yawPC = MathUtils.clamp(0.1f + yawDiff / 90.0f, 0.0f, 1.0f);
            int storageColor = this.getTileEntityStorageColor(storage);
            this.drawBlockPos(this.getStorageTileAABB(storage), ColorUtils.swapAlpha(storageColor, (float)ColorUtils.getAlphaFromColor(storageColor) * alphaPC * dstPCAlpha * yawPC));
        }
    }

    private void setupDrawPearl(Runnable render) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 32772);
        GL11.glEnable(2848);
        GL11.glEnable(2929);
        GL11.glShadeModel(7425);
        ESP.mc.entityRenderer.disableLightmap();
        GL11.glDisable(3553);
        GL11.glDisable(3008);
        GL11.glDepthMask(false);
        RenderUtils.disableStandardItemLighting();
        GL11.glTranslated(-this.getCompense().xCoord, -this.getCompense().yCoord, -this.getCompense().zCoord);
        render.run();
        GL11.glTranslated(this.getCompense().xCoord, this.getCompense().yCoord, this.getCompense().zCoord);
        GL11.glDepthMask(true);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3553);
        GL11.glShadeModel(7424);
        GL11.glDisable(2848);
        GL11.glLineWidth(1.0f);
        GL11.glEnable(3042);
        GL11.glEnable(3008);
        GL11.glEnable(2929);
        GL11.glPopMatrix();
    }

    private void drawPearls(float alphaPC) {
        this.setupDrawPearl(() -> {
            GL11.glBlendFunc(770, 32772);
            for (Entity ent : this.getEntities(false, false, false, false, true, false)) {
                this.tesselatePearl((EntityEnderPearl)ent, alphaPC);
            }
            GL11.glBlendFunc(770, 771);
        });
    }

    private void drawEndPortals(float alphaPC) {
        ArrayList portals = new ArrayList();
        this.getTileEntities(false, false, false, true).forEach(tile -> {
            if (tile instanceof TileEntityEndPortal) {
                TileEntityEndPortal portal = (TileEntityEndPortal)tile;
                portals.add(portal);
            }
        });
        if (portals.isEmpty()) {
            return;
        }
        ArrayList<TileEntityEndPortal> portalsToDraw = new ArrayList<TileEntityEndPortal>();
        for (TileEntityEndPortal portal : portals) {
            if (portals.stream().filter(portalBeta -> (Math.abs(portal.getPos().getX() - portalBeta.getPos().getX()) == 1 && Math.abs(portal.getPos().getZ() - portalBeta.getPos().getZ()) < 2 || Math.abs(portal.getPos().getZ() - portalBeta.getPos().getZ()) == 1 && Math.abs(portal.getPos().getX() - portalBeta.getPos().getX()) < 2) && portal.getPos().getY() == portalBeta.getPos().getY()).toList().size() != 8) continue;
            portalsToDraw.add(portal);
        }
        this.setup3dFor(() -> {
            for (TileEntityEndPortal portal : portalsToDraw) {
                AxisAlignedBB aabb = ESP.mc.world.getBlockState(portal.getPos()).getSelectedBoundingBox(ESP.mc.world, portal.getPos()).addExpandXZ(1.0);
                if ((aabb = aabb.offset(0.0, aabb.maxY - aabb.minY, 0.0)) == null) continue;
                float aPC = 1.0f;
                int espFillColor = ColorUtils.getColor(0, 160, 155, 95);
                int espOutlineColor = ColorUtils.getColor(40, 70, 255, 195);
                espFillColor = ColorUtils.swapAlpha(espFillColor, (float)ColorUtils.getAlphaFromColor(espFillColor) * aPC * alphaPC);
                espOutlineColor = ColorUtils.swapAlpha(espOutlineColor, (float)ColorUtils.getAlphaFromColor(espFillColor) * aPC * alphaPC);
                RenderUtils.drawGradientAlphaBox(aabb.setMaxY(aabb.maxY + 2.0), espOutlineColor != 0, espFillColor != 0, espOutlineColor, espFillColor);
                GL11.glPushMatrix();
                GL11.glScaled(1.0, -1.0, 1.0);
                GL11.glTranslated(0.0, -aabb.minY * 2.0, 0.0);
                RenderUtils.drawGradientAlphaBoxWithBooleanDownPool(aabb, false, espFillColor != 0, false, 0, espFillColor);
                GL11.glPopMatrix();
                GL11.glPushMatrix();
                int index = (int)portal.getPos().toLong();
                long timeAnimMax = 2000L;
                float deltaTime = (float)((System.currentTimeMillis() + (long)index) % timeAnimMax) / (float)timeAnimMax;
                float deltaAnim = (double)deltaTime > 0.5 ? 1.0f - deltaTime : deltaTime;
                deltaAnim *= deltaAnim;
                GL11.glTranslated(0.0, 2.0f + deltaAnim, 0.0);
                GL11.glTranslated((double)portal.getX() + 0.5, (double)portal.getY() + 0.5, (double)portal.getZ() + 0.5);
                GL11.glRotated(deltaTime * 360.0f, 0.0, 1.0, 0.0);
                GL11.glRotated(45.0f + deltaTime * 180.0f, 1.0, 0.0, 1.0);
                GL11.glTranslated(-((double)portal.getX() + 0.5), -((double)portal.getY() + 0.5), -((double)portal.getZ() + 0.5));
                int frags = 40;
                for (int i = 0; i < frags; ++i) {
                    float iPC = (float)i / (float)frags;
                    int flagCol = ColorUtils.swapAlpha(ColorUtils.fadeColor(espFillColor, espOutlineColor, 0.5f, (int)(iPC * 1000.0f)), 255.0f * ((double)iPC > 0.5 ? 1.0f - iPC : iPC) * 2.0f);
                    float offsetCube = 0.1f + 0.25f * iPC;
                    GL11.glTranslated((double)portal.getX() + 0.5, (double)portal.getY() + 0.5, (double)portal.getZ() + 0.5);
                    GL11.glRotated(50.0f * deltaAnim * (0.25f + iPC * 0.75f), 1.0, 0.0, 1.0);
                    GL11.glTranslated(-((double)portal.getX() + 0.5), -((double)portal.getY() + 0.5), -((double)portal.getZ() + 0.5));
                    RenderUtils.drawCanisterBox(new AxisAlignedBB((double)portal.getX() + 0.5 - (double)offsetCube, (double)portal.getY() + 0.5 - (double)offsetCube, (double)portal.getZ() + 0.5 - (double)offsetCube, (double)portal.getX() + 0.5 + (double)offsetCube, (double)portal.getY() + 0.5 + (double)offsetCube, (double)portal.getZ() + 0.5 + (double)offsetCube), false, true, false, flagCol, flagCol, flagCol);
                }
                GL11.glPopMatrix();
            }
        });
    }

    private void tesselatePearl(EntityEnderPearl entityEnderPearl, float darknessUnvalue) {
        double posX = RenderUtils.interpolate(entityEnderPearl.posX, entityEnderPearl.prevPosX, mc.getRenderPartialTicks());
        double posY = RenderUtils.interpolate(entityEnderPearl.posY, entityEnderPearl.prevPosY, mc.getRenderPartialTicks());
        double posZ = RenderUtils.interpolate(entityEnderPearl.posZ, entityEnderPearl.prevPosZ, mc.getRenderPartialTicks());
        double motionX = entityEnderPearl.motionX;
        double motionY = entityEnderPearl.motionY;
        double motionZ = entityEnderPearl.motionZ;
        ArrayList<Vec3d> vectors = new ArrayList<Vec3d>();
        vectors.add(new Vec3d(posX, posY, posZ));
        for (int i = 0; i < 90; ++i) {
            RayTraceResult result;
            Vec3d lastPos = new Vec3d(posX, posY, posZ);
            double factor = 0.99;
            BlockPos blockPos = new BlockPos(posX += motionX, posY += motionY, posZ += motionZ);
            if (ESP.mc.world.getBlockState(blockPos).getBlock() == Blocks.WATER) {
                factor = 0.8;
            }
            motionX *= factor;
            motionY *= factor;
            motionZ *= factor;
            if (!entityEnderPearl.hasNoGravity()) {
                motionY -= 0.03;
            }
            if ((result = ESP.mc.world.rayTraceBlocks(lastPos, new Vec3d(posX, posY, posZ))) != null) {
                posX = result.hitVec.xCoord;
                posY = result.hitVec.yCoord;
                posZ = result.hitVec.zCoord;
                vectors.add(new Vec3d(posX, posY, posZ));
                break;
            }
            vectors.add(new Vec3d(posX, posY, posZ));
        }
        if (vectors.isEmpty()) {
            return;
        }
        int counter = 0;
        int counterMax = vectors.size();
        this.buffer.begin(3, DefaultVertexFormats.POSITION_COLOR);
        for (Vec3d vec : vectors) {
            if (++counter % 2 != 0 ^ counterMax % 2 == 0) continue;
            float pc = (float)counter / (float)counterMax;
            pc = ((double)pc > 0.5 ? 1.0f - pc : pc) * 2.0f;
            pc = pc > 1.0f ? 1.0f : (pc < 0.0f ? 0.0f : pc);
            float aPC = MathUtils.clamp((float)counterMax / 20.0f, 0.0f, 1.0f) / 2.0f;
            int col = ColorUtils.getOverallColorFrom(ClientColors.getColor1(0, darknessUnvalue / 7.0f * aPC), ClientColors.getColor2(0, darknessUnvalue * aPC), pc);
            this.buffer.pos(vec.xCoord, vec.yCoord, vec.zCoord).color(col).endVertex();
        }
        GL11.glLineWidth(1.0f);
        this.tessellator.vboUploader.draw(this.buffer, false);
        GL11.glLineWidth(3.5f);
        this.tessellator.vboUploader.draw(this.buffer, true);
        this.buffer.finishDrawing();
        GL11.glLineWidth(1.0f);
    }

    private void rect3dXZ(double x, double z, double x2, double z2, double y) {
        GL11.glBegin(5);
        GL11.glVertex3d(x, y, z);
        GL11.glVertex3d(x2, y, z);
        GL11.glVertex3d(x2, y, z2);
        GL11.glVertex3d(x, y, z2);
        GL11.glVertex3d(x2, y, z2);
        GL11.glVertex3d(x, y, z2);
        GL11.glVertex3d(x, y, z);
        GL11.glVertex3d(x2, y, z);
        GL11.glEnd();
    }

    private void drawVoidWarn(float partialTicks) {
        RenderUtils.setup3dForBlockPos(() -> {
            EntityPlayerSP selfEntity;
            GL11.glTranslated(this.getCompense().xCoord, 0.0, this.getCompense().zCoord);
            double yLevel = -60.0;
            double extXZIN = 8.0;
            EntityPlayer entityPlayer = selfEntity = FreeCam.fakePlayer != null && FreeCam.get.actived ? FreeCam.fakePlayer : Minecraft.player;
            if (selfEntity.posY < 1.0) {
                float padding = selfEntity.posY < yLevel ? 500.0f : 750.0f;
                float timePC = (float)(System.currentTimeMillis() % (long)((int)padding)) / padding;
                timePC = (double)timePC > 0.5 ? 1.0f - timePC : timePC;
                double yDiff = RenderUtils.interpolate(selfEntity.posY, selfEntity.prevPosY, partialTicks) - yLevel;
                double width = 1.0 + (double)((double)timePC > 0.5 ? 1.0f - timePC : (timePC *= 2.0f)) * MathUtils.clamp(yDiff / 8.0, 0.0, 1.0);
                if (yDiff < extXZIN) {
                    extXZIN = yDiff > 0.0 ? yDiff : 0.0;
                }
                extXZIN = (extXZIN -= width / 2.0) < 0.0 ? 0.0 : extXZIN;
                yDiff = Math.abs(yDiff);
                int voidColorize = selfEntity.posY < yLevel ? ColorUtils.getOverallColorFrom(ColorUtils.getColor(255, 0, 0), 0, timePC) : ColorUtils.getOverallColorFrom(ColorUtils.getColor(255, 70, 70), ColorUtils.getColor(255, 70, 70, 75), timePC);
                float voidAlpha = 0.2f + 0.8f * (float)MathUtils.clamp((32.0 - yDiff) / 32.0, 0.0, 1.0);
                RenderUtils.setupColor(voidColorize, voidAlpha * 255.0f * ColorUtils.getGLAlphaFromColor(voidColorize));
                this.rect3dXZ(-extXZIN - width, -extXZIN - width, extXZIN + width, -extXZIN, yLevel);
                this.rect3dXZ(-extXZIN - width, extXZIN, extXZIN + width, extXZIN + width, yLevel);
                this.rect3dXZ(-extXZIN - width, -extXZIN, -extXZIN, extXZIN, yLevel);
                this.rect3dXZ(extXZIN, -extXZIN, extXZIN + width, extXZIN, yLevel);
                if (selfEntity.posY > yLevel - 2.0) {
                    GL11.glTranslated(0.0, yLevel - 10.0, 0.0);
                    GL11.glEnable(3553);
                    GlStateManager.resetColor();
                    float sizeXZ = (float)((double)0.18f + (double)(0.05f * ((double)timePC > 0.5 ? 1.0f - timePC : timePC)) * MathUtils.clamp(yDiff / 8.0, 0.0, 1.0));
                    GL11.glBlendFunc(770, 771);
                    GL11.glEnable(3042);
                    GL11.glTranslated(sizeXZ, 0.0, sizeXZ);
                    GL11.glRotated(180.0, 0.0, 0.0, 1.0);
                    GL11.glRotated(90.0, 1.0, 0.0, 0.0);
                    GL11.glRotated(180.0, 0.0, 1.0, 0.0);
                    GL11.glTranslated(0.0, 0.0, -1.0E-5);
                    GL11.glRotated(Minecraft.player.rotationYaw, 0.0, 0.0, 1.0);
                    GL11.glRotated(MathUtils.clamp(Minecraft.player.rotationPitch - 45.0f, -45.0f, -10.0f), 1.0, 0.0, 0.0);
                    GL11.glTranslated(0.0, 23.0 + width + 0.5, 0.0);
                    GL11.glScaled(-sizeXZ, -sizeXZ, sizeXZ);
                    String v = "!!!VOID!!!";
                    String d = "Dst to.. = " + (int)MathUtils.clamp(RenderUtils.interpolate(selfEntity.posY, selfEntity.prevPosY, partialTicks) - yLevel, 0.0, 100000.0);
                    RenderUtils.drawRect((float)(-Fonts.mntsb_36.getStringWidth(v)) / 2.0f - 5.0f, -14.0, (float)Fonts.mntsb_36.getStringWidth(v) / 2.0f + 5.0f, 21.0, ColorUtils.getColor(255, 50));
                    RenderUtils.drawLightContureRectSmooth((float)(-Fonts.mntsb_36.getStringWidth(v)) / 2.0f - 5.0f, -14.0, (float)Fonts.mntsb_36.getStringWidth(v) / 2.0f + 5.0f, 21.0, -1);
                    GL11.glTranslated(0.0, 0.0, -0.01f);
                    Fonts.mntsb_36.drawStringWithShadow(v, -Fonts.mntsb_36.getStringWidth(v) / 2, -9.0, voidColorize);
                    Fonts.mntsb_20.drawStringWithShadow(d, -Fonts.mntsb_20.getStringWidth(d) / 2, 9.0, voidColorize);
                    GL11.glDisable(3553);
                    GlStateManager.disableLighting();
                    RenderUtils.disableStandardItemLighting();
                    GlStateManager.resetColor();
                }
            }
        }, false);
    }

    private void draw3d(float partialTicks) {
        boolean players = this.Players.bValue && this.PlayerMode.currentMode.equalsIgnoreCase("Glow");
        boolean self = this.Self.bValue && this.SelfMode.currentMode.equalsIgnoreCase("Glow");
        boolean crystal = this.EnderCrystals.bValue && this.CrystalMode.currentMode.equalsIgnoreCase("Glow");
        boolean breakOver = this.BreakOver.bValue;
        boolean storages = this.Storage.bValue;
        boolean pearls = this.EnderPearl.bValue;
        boolean tnt = this.TntPrimed.bValue && this.TntMode.currentMode.equalsIgnoreCase("Glow");
        boolean voidHL = this.VoidHighlight.bValue;
        boolean portals = this.Portals.bValue;
        if (voidHL) {
            this.drawVoidWarn(partialTicks);
        }
        if (breakOver) {
            this.renderOverSelect(ESP.getAlphaPC());
        }
        if (storages && this.getTileEntities(storages, false, false, false).size() != 0) {
            this.drawStorages(ESP.getAlphaPC());
        }
        if (pearls && this.getEntities(false, false, false, false, pearls, false).size() != 0) {
            this.drawPearls(ESP.getAlphaPC());
        }
        if (portals && this.getTileEntities(false, false, false, portals).size() != 0) {
            this.drawEndPortals(ESP.getAlphaPC());
        }
        if (this.getEntities(players, self, false, crystal, false, tnt).size() != 0) {
            this.framebuffer = RenderUtils.createFrameBuffer(this.framebuffer);
            this.glowFrameBuffer = RenderUtils.createFrameBuffer(this.glowFrameBuffer);
            if (this.framebuffer == null) {
                return;
            }
            List<Entity> listDraw = this.getEntities(players, self, false, crystal, false, tnt);
            this.framebuffer.framebufferClear();
            this.framebuffer.bindFramebuffer(true);
            this.renderPlayers(partialTicks, listDraw);
            this.framebuffer.unbindFramebuffer();
            mc.getFramebuffer().bindFramebuffer(true);
        }
    }

    private List<Entity> getEntities(boolean players, boolean self, boolean items, boolean crystal, boolean pearl, boolean tnt) {
        ArrayList<Entity> list = new ArrayList<Entity>();
        if (ESP.mc.world != null) {
            ESP.mc.world.getLoadedEntityList().forEach(l -> {
                if (l != null && l.isEntityAlive() && (l instanceof EntityPlayerSP && ESP.mc.gameSettings.thirdPersonView != 0 || (RenderUtils.isInView(l) || l instanceof EntityEnderPearl || l instanceof EntityTNTPrimed) && !(l instanceof EntityPlayerSP)) && (l instanceof EntityOtherPlayerMP && players || l instanceof EntityPlayerSP && self || l instanceof EntityItem && items || l instanceof EntityEnderCrystal && crystal || l instanceof EntityEnderPearl && pearl || l instanceof EntityTNTPrimed && tnt)) {
                    list.add((Entity)l);
                }
            });
        }
        return list;
    }

    private List<TileEntity> getTileEntities(boolean storages, boolean beacon, boolean spawner, boolean endPortal) {
        ArrayList<TileEntity> list = new ArrayList<TileEntity>();
        if (ESP.mc.world != null) {
            ESP.mc.world.getLoadedTileEntityList().forEach(l -> {
                if (l != null && ((l instanceof TileEntityChest || l instanceof TileEntityEnderChest || l instanceof TileEntityShulkerBox) && storages || l instanceof TileEntityBeacon && beacon || l instanceof TileEntityMobSpawner && spawner || l instanceof TileEntityEndPortal && endPortal)) {
                    list.add((TileEntity)l);
                }
            });
        }
        return list;
    }

    static {
        alphaPC = new AnimationUtils(0.0f, 0.0f, 0.1f);
    }

    private class ColVecsWithEnt {
        public int randomIndex = (int)(50000.0 * Math.random());
        Vec2fQuadColored colVec;
        EntityLivingBase base;
        public AnimationUtils alphaPC = new AnimationUtils(0.0f, 1.0f, 0.04f);

        public ColVecsWithEnt(Vec2fQuadColored colVec, EntityLivingBase base) {
            this.colVec = colVec;
            this.base = base;
        }

        public EntityLivingBase getEntity() {
            return this.base;
        }

        public void update(List<ColVecsWithEnt> anothers) {
            ColVecsWithEnt newColVec;
            if (anothers.stream().noneMatch(cv -> cv.getEntity().getEntityId() == this.base.getEntityId())) {
                if ((double)this.alphaPC.getAnim() > 0.995) {
                    this.alphaPC.to = 0.0f;
                }
            } else if (this.alphaPC.to == 0.0f) {
                this.alphaPC.to = 1.0f;
            }
            if (this.getEntity() != null && (newColVec = ESP.this.targetESPSPos(this.getEntity(), this.randomIndex)) != null) {
                this.colVec = newColVec.colVec;
            }
        }

        public boolean toRemove() {
            return this.alphaPC.to == 0.0f && (double)this.alphaPC.getAnim() < 0.01 || this.getEntity() == null;
        }
    }

    private class Vec2fQuadColored {
        public static final Vec2fColored ZERO = new Vec2fColored(0.0f, 0.0f, -1);
        public float x;
        public float y;
        public int color = -1;
        public int color2 = -1;
        public int color3 = -1;
        public int color4 = -1;

        public Vec2fQuadColored(float xIn, float yIn, int color, int color2, int color3, int color4) {
            this.x = xIn;
            this.y = yIn;
            this.color = color;
            this.color2 = color2;
            this.color3 = color3;
            this.color4 = color4;
        }

        public void setXY(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public float[] getXY() {
            return new float[]{this.x, this.y};
        }

        public float getX() {
            return this.x;
        }

        public float getY() {
            return this.y;
        }

        public int getColor() {
            return this.color;
        }

        public int getColor2() {
            return this.color2;
        }

        public int getColor3() {
            return this.color3;
        }

        public int getColor4() {
            return this.color4;
        }
    }

    private class TESP {
        EntityLivingBase entity;
        float x;
        float y;
        float triangleScale;
        AnimationUtils alphaPC = new AnimationUtils(0.0f, 1.0f, 0.05f);
        int storeyCount;
        List<TriangleElement> TElementLIST = new ArrayList<TriangleElement>();
        boolean toRemove;

        TESP(EntityLivingBase entity, int triangleScale, int storeyCount) {
            this.entity = entity;
            this.triangleScale = triangleScale;
            this.storeyCount = storeyCount;
            this.updateRenderPos();
            this.genTriangleElements(triangleScale, storeyCount);
        }

        void genTriangleElements(float triangleScale, int storeyCount) {
            float triangleY = triangleScale;
            for (int yStepI = 0; yStepI < storeyCount; ++yStepI) {
                float triangleX = -triangleScale / 1.5f * (float)yStepI;
                triangleY -= triangleScale;
                boolean yIndexHached = ((double)yStepI + (double)storeyCount / 1.5) % 2.0 == 0.0;
                for (int xStepI = 0; xStepI < yStepI * 2 + 1; ++xStepI) {
                    boolean upSide = xStepI % 2 == (yIndexHached ? 0 : 1);
                    this.TElementLIST.add(new TriangleElement(triangleX, triangleY, upSide, triangleScale));
                    triangleX += triangleScale / 1.5f;
                }
            }
        }

        float getX() {
            return this.x;
        }

        float getY() {
            return this.y;
        }

        void setX(float x) {
            this.x = x;
        }

        void setY(float y) {
            this.y = y;
        }

        void setXY(float x, float y) {
            this.setX(x);
            this.setY(y);
        }

        EntityLivingBase getEntity() {
            return this.entity;
        }

        void updateRenderPos() {
            if (this.getEntity() == null) {
                return;
            }
            float[] pos = ESP.this.get2DPosForTESP(this.entity, Module.mc.getRenderPartialTicks(), ScaledResolution.getScaleFactor(), Module.mc.getRenderManager());
            int timeInterval = 1700;
            float pcTime = (float)((System.currentTimeMillis() + (long)this.entity.getEntityId()) % (long)timeInterval) / (float)timeInterval;
            pcTime = ((double)pcTime > 0.5 ? 1.0f - pcTime : pcTime) * 2.0f;
            pcTime *= pcTime;
            this.setXY(pos[0], pos[1] - 68.0f + pcTime * 20.0f);
        }

        float getHeight() {
            return (float)this.storeyCount * this.triangleScale;
        }

        float getWidth() {
            return (float)this.storeyCount * this.triangleScale / 1.5f;
        }

        void updateTESP() {
            boolean doRemove;
            boolean bl = doRemove = !ESP.this.updatedTargets.stream().anyMatch(entity -> entity.getEntityId() == this.getEntity().getEntityId());
            if (doRemove && this.alphaPC.to == 1.0f && this.alphaPC.getAnim() >= 0.9975f) {
                this.alphaPC.to = 0.0f;
            }
            boolean bl2 = this.toRemove = doRemove && this.alphaPC.to == 0.0f && this.alphaPC.getAnim() < 0.03f;
            if (!doRemove && this.alphaPC.to == 0.0f) {
                this.alphaPC.to = 1.0f;
                this.toRemove = false;
            }
            this.TElementLIST.forEach(TriangleElement::updateElement);
        }

        boolean isToRemove() {
            return this.toRemove;
        }

        boolean canDraw() {
            return (this.getX() != -1.0f || this.getY() != -1.0f) && this.alphaPC.getAnim() >= 0.03f;
        }

        void drawTESP(int baseColor, int elementColor1, int elementColor2) {
            this.updateRenderPos();
            if (!this.canDraw()) {
                return;
            }
            elementColor1 = ColorUtils.swapAlpha(elementColor1, (float)ColorUtils.getAlphaFromColor(elementColor1) * this.alphaPC.getAnim());
            elementColor2 = ColorUtils.swapAlpha(elementColor2, (float)ColorUtils.getAlphaFromColor(elementColor2) * this.alphaPC.anim);
            List<Vec2f> vecs = Arrays.asList(new Vec2f(this.getX(), this.getY()), new Vec2f(this.getX() - this.getWidth(), this.getY() - this.getHeight()), new Vec2f(this.getX() + this.getWidth(), this.getY() - this.getHeight()));
            RenderUtils.enableStandardItemLighting();
            GlStateManager.disableLighting();
            GlStateManager.enableDepth();
            GlStateManager.enableBlend();
            GL11.glLineWidth(1.5f);
            RenderUtils.drawSome(vecs, elementColor1, 2);
            GL11.glLineWidth(1.0f);
            GlStateManager.disableDepth();
            this.drawElements(elementColor1, elementColor2, 1.0f);
            GlStateManager.enableDepth();
        }

        void drawElements(int elementColor1, int elementColor2, float alphaPC) {
            GL11.glPushMatrix();
            GL11.glTranslated(this.getX(), this.getY(), 0.0);
            int index = 0;
            for (TriangleElement element : this.TElementLIST) {
                int i = (int)((float)index * 12.0f);
                float rotPC = element.getRevertPC();
                rotPC = ((double)rotPC > 0.5 ? 1.0f - rotPC : rotPC) * 2.0f;
                rotPC *= rotPC;
                float colorFadeDelay = 500.0f;
                float timePC = (float)((int)(System.currentTimeMillis() + (long)((int)(((float)index + rotPC) * 40.0f))) % (int)colorFadeDelay) / colorFadeDelay;
                timePC = (double)timePC > 0.5 ? 1.0f - timePC : timePC;
                int elementColor = ColorUtils.getOverallColorFrom(elementColor1, elementColor2, MathUtils.clamp(rotPC + timePC, 0.0f, 1.0f));
                element.drawVertexes(elementColor);
                ++index;
            }
            GL11.glPopMatrix();
        }
    }

    private class TriangleElement {
        float x;
        float y;
        float triangleScale;
        boolean upRot;
        TimerHelper timerRevert = new TimerHelper();
        int waitToRevert;
        AnimationUtils revertAnim = new AnimationUtils(0.0f, 0.0f, 0.015f);
        List<Vec2f> vecsOffsets = new ArrayList<Vec2f>();

        TriangleElement(float x, float y, boolean upRot, float triangleScale) {
            this.x = x;
            this.y = y;
            this.upRot = upRot;
            this.triangleScale = triangleScale;
            this.genVertexes(triangleScale, upRot);
            this.timerRevert.reset();
        }

        void updateElement() {
            if (this.waitToRevert == 0) {
                this.waitToRevert = 100 + ESP.this.RANDOM.nextInt(700);
            }
            if (this.timerRevert.hasReached(this.waitToRevert) && this.revertAnim.getAnim() == 0.0f) {
                this.revertAnim.setAnim(0.0f);
                this.revertAnim.to = 180.0f;
                this.waitToRevert = 1600 + ESP.this.RANDOM.nextInt(550);
                this.timerRevert.reset();
            }
            if (MathUtils.getDifferenceOf(this.revertAnim.to, this.revertAnim.getAnim()) < 2.0) {
                this.revertAnim.setAnim(0.0f);
                this.revertAnim.to = 0.0f;
            }
        }

        float getRevertPC() {
            return (float)(MathUtils.getDifferenceOf(this.revertAnim.getAnim(), this.revertAnim.to) / 180.0);
        }

        void genVertexes(float triangleScale, boolean upRot) {
            if (upRot) {
                this.vecsOffsets.add(new Vec2f(triangleScale / 1.5f, 0.0f));
                this.vecsOffsets.add(new Vec2f(-triangleScale / 1.5f, 0.0f));
                this.vecsOffsets.add(new Vec2f(0.0f, -triangleScale));
            } else {
                this.vecsOffsets.add(new Vec2f(triangleScale / 1.5f, -triangleScale));
                this.vecsOffsets.add(new Vec2f(-triangleScale / 1.5f, -triangleScale));
                this.vecsOffsets.add(new Vec2f(0.0f, 0.0f));
            }
        }

        void drawVertexes(int color) {
            float rotPC = this.revertAnim.getAnim() / 180.0f;
            rotPC = ((double)rotPC > 0.5 ? 1.0f - rotPC : rotPC) * 2.0f;
            GL11.glPushMatrix();
            GL11.glTranslated(this.x, this.y, 0.0);
            GL11.glTranslated(0.0, -this.triangleScale / 2.0f, 0.0);
            GL11.glRotated(this.revertAnim.getAnim(), 0.0, 1.0, 0.0);
            GL11.glTranslated(0.0, this.triangleScale / 2.0f, 0.0);
            GL11.glEnable(2848);
            GL11.glHint(3154, 4354);
            RenderUtils.drawSome(this.vecsOffsets, ColorUtils.swapAlpha(color, (float)ColorUtils.getAlphaFromColor(color) * (1.0f - rotPC)), 9);
            GL11.glLineWidth(rotPC * 2.0f + 1.0f);
            RenderUtils.drawSome(this.vecsOffsets, ColorUtils.swapAlpha(color, (float)ColorUtils.getAlphaFromColor(color) * ColorUtils.getGLAlphaFromColor(color)), 2);
            GL11.glLineWidth(1.0f);
            GL11.glHint(3154, 4352);
            GL11.glDisable(2848);
            GL11.glPopMatrix();
        }
    }
}


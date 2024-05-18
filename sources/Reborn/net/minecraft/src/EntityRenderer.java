package net.minecraft.src;

import net.minecraft.client.*;
import java.nio.*;
import java.awt.image.*;
import org.lwjgl.input.*;
import org.lwjgl.util.glu.*;
import me.enrythebest.reborn.cracked.*;
import java.util.concurrent.*;
import java.util.*;
import java.lang.reflect.*;
import java.awt.*;
import org.lwjgl.opengl.*;

public class EntityRenderer
{
    public static boolean anaglyphEnable;
    public static int anaglyphField;
    private Minecraft mc;
    private float farPlaneDistance;
    public ItemRenderer itemRenderer;
    private int rendererUpdateCount;
    private Entity pointedEntity;
    private MouseFilter mouseFilterXAxis;
    private MouseFilter mouseFilterYAxis;
    private MouseFilter mouseFilterDummy1;
    private MouseFilter mouseFilterDummy2;
    private MouseFilter mouseFilterDummy3;
    private MouseFilter mouseFilterDummy4;
    private float thirdPersonDistance;
    private float thirdPersonDistanceTemp;
    private float debugCamYaw;
    private float prevDebugCamYaw;
    private float debugCamPitch;
    private float prevDebugCamPitch;
    private float smoothCamYaw;
    private float smoothCamPitch;
    private float smoothCamFilterX;
    private float smoothCamFilterY;
    private float smoothCamPartialTicks;
    private float debugCamFOV;
    private float prevDebugCamFOV;
    private float camRoll;
    private float prevCamRoll;
    public int lightmapTexture;
    private int[] lightmapColors;
    private float fovModifierHand;
    private float fovModifierHandPrev;
    private float fovMultiplierTemp;
    private float field_82831_U;
    private float field_82832_V;
    private boolean cloudFog;
    private double cameraZoom;
    private double cameraYaw;
    private double cameraPitch;
    private long prevFrameTime;
    private long renderEndNanoTime;
    private boolean lightmapUpdateNeeded;
    float torchFlickerX;
    float torchFlickerDX;
    float torchFlickerY;
    float torchFlickerDY;
    private Random random;
    private int rainSoundCounter;
    float[] rainXCoords;
    float[] rainYCoords;
    volatile int field_78523_k;
    volatile int field_78520_l;
    FloatBuffer fogColorBuffer;
    float fogColorRed;
    float fogColorGreen;
    float fogColorBlue;
    private float fogColor2;
    private float fogColor1;
    public int debugViewDirection;
    private World updatedWorld;
    private boolean showDebugInfo;
    private boolean fullscreenModeChecked;
    private boolean desktopModeChecked;
    private String lastTexturePack;
    private long lastServerTime;
    private int lastServerTicks;
    private int serverWaitTime;
    private int serverWaitTimeCurrent;
    private float avgServerTimeDiff;
    private float avgServerTickDiff;
    public long[] frameTimes;
    public long[] tickTimes;
    public long[] chunkTimes;
    public long[] serverTimes;
    public int numRecordedFrameTimes;
    public long prevFrameTimeNano;
    private boolean lastShowDebugInfo;
    private boolean showExtendedDebugInfo;
    
    static {
        EntityRenderer.anaglyphEnable = false;
    }
    
    public EntityRenderer(final Minecraft par1Minecraft) {
        this.farPlaneDistance = 0.0f;
        this.pointedEntity = null;
        this.mouseFilterXAxis = new MouseFilter();
        this.mouseFilterYAxis = new MouseFilter();
        this.mouseFilterDummy1 = new MouseFilter();
        this.mouseFilterDummy2 = new MouseFilter();
        this.mouseFilterDummy3 = new MouseFilter();
        this.mouseFilterDummy4 = new MouseFilter();
        this.thirdPersonDistance = 4.0f;
        this.thirdPersonDistanceTemp = 4.0f;
        this.debugCamYaw = 0.0f;
        this.prevDebugCamYaw = 0.0f;
        this.debugCamPitch = 0.0f;
        this.prevDebugCamPitch = 0.0f;
        this.debugCamFOV = 0.0f;
        this.prevDebugCamFOV = 0.0f;
        this.camRoll = 0.0f;
        this.prevCamRoll = 0.0f;
        this.cloudFog = false;
        this.cameraZoom = 1.0;
        this.cameraYaw = 0.0;
        this.cameraPitch = 0.0;
        this.prevFrameTime = Minecraft.getSystemTime();
        this.renderEndNanoTime = 0L;
        this.lightmapUpdateNeeded = false;
        this.torchFlickerX = 0.0f;
        this.torchFlickerDX = 0.0f;
        this.torchFlickerY = 0.0f;
        this.torchFlickerDY = 0.0f;
        this.random = new Random();
        this.rainSoundCounter = 0;
        this.field_78523_k = 0;
        this.field_78520_l = 0;
        this.fogColorBuffer = GLAllocation.createDirectFloatBuffer(16);
        this.updatedWorld = null;
        this.showDebugInfo = false;
        this.fullscreenModeChecked = false;
        this.desktopModeChecked = false;
        this.lastTexturePack = null;
        this.lastServerTime = 0L;
        this.lastServerTicks = 0;
        this.serverWaitTime = 0;
        this.serverWaitTimeCurrent = 0;
        this.avgServerTimeDiff = 0.0f;
        this.avgServerTickDiff = 0.0f;
        this.frameTimes = new long[512];
        this.tickTimes = new long[512];
        this.chunkTimes = new long[512];
        this.serverTimes = new long[512];
        this.numRecordedFrameTimes = 0;
        this.prevFrameTimeNano = -1L;
        this.lastShowDebugInfo = false;
        this.showExtendedDebugInfo = false;
        this.mc = par1Minecraft;
        this.itemRenderer = new ItemRenderer(par1Minecraft);
        this.lightmapTexture = par1Minecraft.renderEngine.allocateAndSetupTexture(new BufferedImage(16, 16, 1));
        this.lightmapColors = new int[256];
    }
    
    public void updateRenderer() {
        this.updateFovModifierHand();
        this.updateTorchFlicker();
        this.fogColor2 = this.fogColor1;
        this.thirdPersonDistanceTemp = this.thirdPersonDistance;
        this.prevDebugCamYaw = this.debugCamYaw;
        this.prevDebugCamPitch = this.debugCamPitch;
        this.prevDebugCamFOV = this.debugCamFOV;
        this.prevCamRoll = this.camRoll;
        if (this.mc.gameSettings.smoothCamera) {
            final float var1 = this.mc.gameSettings.mouseSensitivity * 0.6f + 0.2f;
            final float var2 = var1 * var1 * var1 * 8.0f;
            this.smoothCamFilterX = this.mouseFilterXAxis.smooth(this.smoothCamYaw, 0.05f * var2);
            this.smoothCamFilterY = this.mouseFilterYAxis.smooth(this.smoothCamPitch, 0.05f * var2);
            this.smoothCamPartialTicks = 0.0f;
            this.smoothCamYaw = 0.0f;
            this.smoothCamPitch = 0.0f;
        }
        if (this.mc.renderViewEntity == null) {
            this.mc.renderViewEntity = Minecraft.thePlayer;
        }
        final float var1 = Minecraft.theWorld.getLightBrightness(MathHelper.floor_double(this.mc.renderViewEntity.posX), MathHelper.floor_double(this.mc.renderViewEntity.posY), MathHelper.floor_double(this.mc.renderViewEntity.posZ));
        final float var2 = (3 - this.mc.gameSettings.renderDistance) / 3.0f;
        final float var3 = var1 * (1.0f - var2) + var2;
        this.fogColor1 += (var3 - this.fogColor1) * 0.1f;
        ++this.rendererUpdateCount;
        this.itemRenderer.updateEquippedItem();
        this.addRainParticles();
        this.field_82832_V = this.field_82831_U;
        if (BossStatus.field_82825_d) {
            this.field_82831_U += 0.05f;
            if (this.field_82831_U > 1.0f) {
                this.field_82831_U = 1.0f;
            }
            BossStatus.field_82825_d = false;
        }
        else if (this.field_82831_U > 0.0f) {
            this.field_82831_U -= 0.0125f;
        }
    }
    
    public void getMouseOver(final float par1) {
        if (this.mc.renderViewEntity != null && Minecraft.theWorld != null) {
            this.mc.pointedEntityLiving = null;
            double var2 = this.mc.playerController.getBlockReachDistance();
            this.mc.objectMouseOver = this.mc.renderViewEntity.rayTrace(var2, par1);
            double var3 = var2;
            final Vec3 var4 = this.mc.renderViewEntity.getPosition(par1);
            if (this.mc.playerController.extendedReach()) {
                var2 = 6.0;
                var3 = 6.0;
            }
            else {
                if (var2 > 3.0) {
                    var3 = 3.0;
                }
                var2 = var3;
            }
            if (this.mc.objectMouseOver != null) {
                var3 = this.mc.objectMouseOver.hitVec.distanceTo(var4);
            }
            final Vec3 var5 = this.mc.renderViewEntity.getLook(par1);
            final Vec3 var6 = var4.addVector(var5.xCoord * var2, var5.yCoord * var2, var5.zCoord * var2);
            this.pointedEntity = null;
            final float var7 = 1.0f;
            final List var8 = Minecraft.theWorld.getEntitiesWithinAABBExcludingEntity(this.mc.renderViewEntity, this.mc.renderViewEntity.boundingBox.addCoord(var5.xCoord * var2, var5.yCoord * var2, var5.zCoord * var2).expand(var7, var7, var7));
            double var9 = var3;
            for (int var10 = 0; var10 < var8.size(); ++var10) {
                final Entity var11 = var8.get(var10);
                if (var11.canBeCollidedWith()) {
                    final float var12 = var11.getCollisionBorderSize();
                    final AxisAlignedBB var13 = var11.boundingBox.expand(var12, var12, var12);
                    final MovingObjectPosition var14 = var13.calculateIntercept(var4, var6);
                    if (var13.isVecInside(var4)) {
                        if (0.0 < var9 || var9 == 0.0) {
                            this.pointedEntity = var11;
                            var9 = 0.0;
                        }
                    }
                    else if (var14 != null) {
                        final double var15 = var4.distanceTo(var14.hitVec);
                        if (var15 < var9 || var9 == 0.0) {
                            this.pointedEntity = var11;
                            var9 = var15;
                        }
                    }
                }
            }
            if (this.pointedEntity != null && (var9 < var3 || this.mc.objectMouseOver == null)) {
                this.mc.objectMouseOver = new MovingObjectPosition(this.pointedEntity);
                if (this.pointedEntity instanceof EntityLiving) {
                    this.mc.pointedEntityLiving = (EntityLiving)this.pointedEntity;
                }
            }
        }
    }
    
    private void updateFovModifierHand() {
        if (this.mc.renderViewEntity instanceof EntityPlayerSP) {
            final EntityPlayerSP var1 = (EntityPlayerSP)this.mc.renderViewEntity;
            this.fovMultiplierTemp = var1.getFOVMultiplier();
        }
        else {
            this.fovMultiplierTemp = Minecraft.thePlayer.getFOVMultiplier();
        }
        this.fovModifierHandPrev = this.fovModifierHand;
        this.fovModifierHand += (this.fovMultiplierTemp - this.fovModifierHand) * 0.5f;
        if (this.fovModifierHand > 1.5f) {
            this.fovModifierHand = 1.5f;
        }
        if (this.fovModifierHand < 0.1f) {
            this.fovModifierHand = 0.1f;
        }
    }
    
    private float getFOVModifier(final float par1, final boolean par2) {
        if (this.debugViewDirection > 0) {
            return 90.0f;
        }
        final EntityLiving var3 = this.mc.renderViewEntity;
        float var4 = 70.0f;
        if (par2) {
            var4 += this.mc.gameSettings.fovSetting * 40.0f;
            var4 *= this.fovModifierHandPrev + (this.fovModifierHand - this.fovModifierHandPrev) * par1;
        }
        boolean var5 = false;
        if (Minecraft.currentScreen == null) {
            if (this.mc.gameSettings.ofKeyBindZoom.keyCode < 0) {
                var5 = Mouse.isButtonDown(this.mc.gameSettings.ofKeyBindZoom.keyCode + 100);
            }
            else {
                var5 = Keyboard.isKeyDown(this.mc.gameSettings.ofKeyBindZoom.keyCode);
            }
        }
        if (var5) {
            if (!Config.zoomMode) {
                Config.zoomMode = true;
                this.mc.gameSettings.smoothCamera = true;
            }
            if (Config.zoomMode) {
                var4 /= 4.0f;
            }
        }
        else if (Config.zoomMode) {
            Config.zoomMode = false;
            this.mc.gameSettings.smoothCamera = false;
            this.mouseFilterXAxis = new MouseFilter();
            this.mouseFilterYAxis = new MouseFilter();
        }
        if (var3.getHealth() <= 0) {
            final float var6 = var3.deathTime + par1;
            var4 /= (1.0f - 500.0f / (var6 + 500.0f)) * 2.0f + 1.0f;
        }
        final int var7 = ActiveRenderInfo.getBlockIdAtEntityViewpoint(Minecraft.theWorld, var3, par1);
        if (var7 != 0 && Block.blocksList[var7].blockMaterial == Material.water) {
            var4 = var4 * 60.0f / 70.0f;
        }
        return var4 + this.prevDebugCamFOV + (this.debugCamFOV - this.prevDebugCamFOV) * par1;
    }
    
    private void hurtCameraEffect(final float par1) {
        final EntityLiving var2 = this.mc.renderViewEntity;
        float var3 = var2.hurtTime - par1;
        if (var2.getHealth() <= 0) {
            final float var4 = var2.deathTime + par1;
            GL11.glRotatef(40.0f - 8000.0f / (var4 + 200.0f), 0.0f, 0.0f, 1.0f);
        }
        if (var3 >= 0.0f) {
            var3 /= var2.maxHurtTime;
            var3 = MathHelper.sin(var3 * var3 * var3 * var3 * 3.1415927f);
            final float var4 = var2.attackedAtYaw;
            GL11.glRotatef(-var4, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(-var3 * 14.0f, 0.0f, 0.0f, 1.0f);
            GL11.glRotatef(var4, 0.0f, 1.0f, 0.0f);
        }
    }
    
    private void setupViewBobbing(final float par1) {
        if (this.mc.renderViewEntity instanceof EntityPlayer) {
            final EntityPlayer var2 = (EntityPlayer)this.mc.renderViewEntity;
            final float var3 = var2.distanceWalkedModified - var2.prevDistanceWalkedModified;
            final float var4 = -(var2.distanceWalkedModified + var3 * par1);
            final float var5 = var2.prevCameraYaw + (var2.cameraYaw - var2.prevCameraYaw) * par1;
            final float var6 = var2.prevCameraPitch + (var2.cameraPitch - var2.prevCameraPitch) * par1;
            GL11.glTranslatef(MathHelper.sin(var4 * 3.1415927f) * var5 * 0.5f, -Math.abs(MathHelper.cos(var4 * 3.1415927f) * var5), 0.0f);
            GL11.glRotatef(MathHelper.sin(var4 * 3.1415927f) * var5 * 3.0f, 0.0f, 0.0f, 1.0f);
            GL11.glRotatef(Math.abs(MathHelper.cos(var4 * 3.1415927f - 0.2f) * var5) * 5.0f, 1.0f, 0.0f, 0.0f);
            GL11.glRotatef(var6, 1.0f, 0.0f, 0.0f);
        }
    }
    
    private void orientCamera(final float par1) {
        final EntityLiving var2 = this.mc.renderViewEntity;
        float var3 = var2.yOffset - 1.62f;
        double var4 = var2.prevPosX + (var2.posX - var2.prevPosX) * par1;
        double var5 = var2.prevPosY + (var2.posY - var2.prevPosY) * par1 - var3;
        double var6 = var2.prevPosZ + (var2.posZ - var2.prevPosZ) * par1;
        GL11.glRotatef(this.prevCamRoll + (this.camRoll - this.prevCamRoll) * par1, 0.0f, 0.0f, 1.0f);
        if (var2.isPlayerSleeping()) {
            ++var3;
            GL11.glTranslatef(0.0f, 0.3f, 0.0f);
            if (!this.mc.gameSettings.debugCamEnable) {
                final int var7 = Minecraft.theWorld.getBlockId(MathHelper.floor_double(var2.posX), MathHelper.floor_double(var2.posY), MathHelper.floor_double(var2.posZ));
                if (Reflector.ForgeHooksClient_orientBedCamera.exists()) {
                    Reflector.callVoid(Reflector.ForgeHooksClient_orientBedCamera, this.mc, var2);
                }
                else if (var7 == Block.bed.blockID) {
                    final int var8 = Minecraft.theWorld.getBlockMetadata(MathHelper.floor_double(var2.posX), MathHelper.floor_double(var2.posY), MathHelper.floor_double(var2.posZ));
                    final int var9 = var8 & 0x3;
                    GL11.glRotatef(var9 * 90, 0.0f, 1.0f, 0.0f);
                }
                GL11.glRotatef(var2.prevRotationYaw + (var2.rotationYaw - var2.prevRotationYaw) * par1 + 180.0f, 0.0f, -1.0f, 0.0f);
                GL11.glRotatef(var2.prevRotationPitch + (var2.rotationPitch - var2.prevRotationPitch) * par1, -1.0f, 0.0f, 0.0f);
            }
        }
        else if (this.mc.gameSettings.thirdPersonView > 0) {
            double var10 = this.thirdPersonDistanceTemp + (this.thirdPersonDistance - this.thirdPersonDistanceTemp) * par1;
            if (this.mc.gameSettings.debugCamEnable) {
                final float var11 = this.prevDebugCamYaw + (this.debugCamYaw - this.prevDebugCamYaw) * par1;
                final float var12 = this.prevDebugCamPitch + (this.debugCamPitch - this.prevDebugCamPitch) * par1;
                GL11.glTranslatef(0.0f, 0.0f, (float)(-var10));
                GL11.glRotatef(var12, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(var11, 0.0f, 1.0f, 0.0f);
            }
            else {
                final float var11 = var2.rotationYaw;
                float var12 = var2.rotationPitch;
                if (this.mc.gameSettings.thirdPersonView == 2) {
                    var12 += 180.0f;
                }
                final double var13 = -MathHelper.sin(var11 / 180.0f * 3.1415927f) * MathHelper.cos(var12 / 180.0f * 3.1415927f) * var10;
                final double var14 = MathHelper.cos(var11 / 180.0f * 3.1415927f) * MathHelper.cos(var12 / 180.0f * 3.1415927f) * var10;
                final double var15 = -MathHelper.sin(var12 / 180.0f * 3.1415927f) * var10;
                for (int var16 = 0; var16 < 8; ++var16) {
                    float var17 = (var16 & 0x1) * 2 - 1;
                    float var18 = (var16 >> 1 & 0x1) * 2 - 1;
                    float var19 = (var16 >> 2 & 0x1) * 2 - 1;
                    var17 *= 0.1f;
                    var18 *= 0.1f;
                    var19 *= 0.1f;
                    final MovingObjectPosition var20 = Minecraft.theWorld.rayTraceBlocks(Minecraft.theWorld.getWorldVec3Pool().getVecFromPool(var4 + var17, var5 + var18, var6 + var19), Minecraft.theWorld.getWorldVec3Pool().getVecFromPool(var4 - var13 + var17 + var19, var5 - var15 + var18, var6 - var14 + var19));
                    if (var20 != null) {
                        final double var21 = var20.hitVec.distanceTo(Minecraft.theWorld.getWorldVec3Pool().getVecFromPool(var4, var5, var6));
                        if (var21 < var10) {
                            var10 = var21;
                        }
                    }
                }
                if (this.mc.gameSettings.thirdPersonView == 2) {
                    GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
                }
                GL11.glRotatef(var2.rotationPitch - var12, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(var2.rotationYaw - var11, 0.0f, 1.0f, 0.0f);
                GL11.glTranslatef(0.0f, 0.0f, (float)(-var10));
                GL11.glRotatef(var11 - var2.rotationYaw, 0.0f, 1.0f, 0.0f);
                GL11.glRotatef(var12 - var2.rotationPitch, 1.0f, 0.0f, 0.0f);
            }
        }
        else {
            GL11.glTranslatef(0.0f, 0.0f, -0.1f);
        }
        if (!this.mc.gameSettings.debugCamEnable) {
            GL11.glRotatef(var2.prevRotationPitch + (var2.rotationPitch - var2.prevRotationPitch) * par1, 1.0f, 0.0f, 0.0f);
            GL11.glRotatef(var2.prevRotationYaw + (var2.rotationYaw - var2.prevRotationYaw) * par1 + 180.0f, 0.0f, 1.0f, 0.0f);
        }
        GL11.glTranslatef(0.0f, var3, 0.0f);
        var4 = var2.prevPosX + (var2.posX - var2.prevPosX) * par1;
        var5 = var2.prevPosY + (var2.posY - var2.prevPosY) * par1 - var3;
        var6 = var2.prevPosZ + (var2.posZ - var2.prevPosZ) * par1;
        this.cloudFog = this.mc.renderGlobal.hasCloudFog(var4, var5, var6, par1);
    }
    
    private void setupCameraTransform(final float par1, final int par2) {
        this.farPlaneDistance = 32 << 3 - this.mc.gameSettings.renderDistance;
        this.farPlaneDistance = this.mc.gameSettings.ofRenderDistanceFine;
        if (Config.isFogFancy()) {
            this.farPlaneDistance *= 0.95f;
        }
        if (Config.isFogFast()) {
            this.farPlaneDistance *= 0.83f;
        }
        GL11.glMatrixMode(5889);
        GL11.glLoadIdentity();
        final float var3 = 0.07f;
        if (this.mc.gameSettings.anaglyph) {
            GL11.glTranslatef(-(par2 * 2 - 1) * var3, 0.0f, 0.0f);
        }
        float var4 = this.farPlaneDistance * 2.0f;
        if (var4 < 128.0f) {
            var4 = 128.0f;
        }
        if (this.cameraZoom != 1.0) {
            GL11.glTranslatef((float)this.cameraYaw, (float)(-this.cameraPitch), 0.0f);
            GL11.glScaled(this.cameraZoom, this.cameraZoom, 1.0);
        }
        GLU.gluPerspective(this.getFOVModifier(par1, true), this.mc.displayWidth / this.mc.displayHeight, 0.05f, var4);
        if (this.mc.playerController.enableEverythingIsScrewedUpMode()) {
            final float var5 = 0.6666667f;
            GL11.glScalef(1.0f, var5, 1.0f);
        }
        GL11.glMatrixMode(5888);
        GL11.glLoadIdentity();
        if (this.mc.gameSettings.anaglyph) {
            GL11.glTranslatef((par2 * 2 - 1) * 0.1f, 0.0f, 0.0f);
        }
        this.hurtCameraEffect(par1);
        if (this.mc.gameSettings.viewBobbing) {
            this.setupViewBobbing(par1);
        }
        final float var5 = Minecraft.thePlayer.prevTimeInPortal + (Minecraft.thePlayer.timeInPortal - Minecraft.thePlayer.prevTimeInPortal) * par1;
        if (var5 > 0.0f) {
            byte var6 = 20;
            if (Minecraft.thePlayer.isPotionActive(Potion.confusion)) {
                var6 = 7;
            }
            float var7 = 5.0f / (var5 * var5 + 5.0f) - var5 * 0.04f;
            var7 *= var7;
            GL11.glRotatef((this.rendererUpdateCount + par1) * var6, 0.0f, 1.0f, 1.0f);
            GL11.glScalef(1.0f / var7, 1.0f, 1.0f);
            GL11.glRotatef(-(this.rendererUpdateCount + par1) * var6, 0.0f, 1.0f, 1.0f);
        }
        this.orientCamera(par1);
        if (this.debugViewDirection > 0) {
            final int var8 = this.debugViewDirection - 1;
            if (var8 == 1) {
                GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
            }
            if (var8 == 2) {
                GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
            }
            if (var8 == 3) {
                GL11.glRotatef(-90.0f, 0.0f, 1.0f, 0.0f);
            }
            if (var8 == 4) {
                GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
            }
            if (var8 == 5) {
                GL11.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
            }
        }
    }
    
    private void renderHand(final float par1, final int par2) {
        if (this.debugViewDirection <= 0) {
            GL11.glMatrixMode(5889);
            GL11.glLoadIdentity();
            final float var3 = 0.07f;
            if (this.mc.gameSettings.anaglyph) {
                GL11.glTranslatef(-(par2 * 2 - 1) * var3, 0.0f, 0.0f);
            }
            if (this.cameraZoom != 1.0) {
                GL11.glTranslatef((float)this.cameraYaw, (float)(-this.cameraPitch), 0.0f);
                GL11.glScaled(this.cameraZoom, this.cameraZoom, 1.0);
            }
            GLU.gluPerspective(this.getFOVModifier(par1, false), this.mc.displayWidth / this.mc.displayHeight, 0.05f, this.farPlaneDistance * 2.0f);
            if (this.mc.playerController.enableEverythingIsScrewedUpMode()) {
                final float var4 = 0.6666667f;
                GL11.glScalef(1.0f, var4, 1.0f);
            }
            GL11.glMatrixMode(5888);
            GL11.glLoadIdentity();
            if (this.mc.gameSettings.anaglyph) {
                GL11.glTranslatef((par2 * 2 - 1) * 0.1f, 0.0f, 0.0f);
            }
            GL11.glPushMatrix();
            this.hurtCameraEffect(par1);
            if (this.mc.gameSettings.viewBobbing) {
                this.setupViewBobbing(par1);
            }
            if (this.mc.gameSettings.thirdPersonView == 0 && !this.mc.renderViewEntity.isPlayerSleeping() && !this.mc.gameSettings.hideGUI && !this.mc.playerController.enableEverythingIsScrewedUpMode()) {
                this.enableLightmap(par1);
                this.itemRenderer.renderItemInFirstPerson(par1);
                this.disableLightmap(par1);
            }
            GL11.glPopMatrix();
            if (this.mc.gameSettings.thirdPersonView == 0 && !this.mc.renderViewEntity.isPlayerSleeping()) {
                this.itemRenderer.renderOverlays(par1);
                this.hurtCameraEffect(par1);
            }
            if (this.mc.gameSettings.viewBobbing) {
                this.setupViewBobbing(par1);
            }
        }
    }
    
    public void disableLightmap(final double par1) {
        OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glDisable(3553);
        OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }
    
    public void enableLightmap(final double par1) {
        OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glMatrixMode(5890);
        GL11.glLoadIdentity();
        final float var3 = 0.00390625f;
        GL11.glScalef(var3, var3, var3);
        GL11.glTranslatef(8.0f, 8.0f, 8.0f);
        GL11.glMatrixMode(5888);
        GL11.glBindTexture(3553, this.lightmapTexture);
        GL11.glTexParameteri(3553, 10241, 9729);
        GL11.glTexParameteri(3553, 10240, 9729);
        GL11.glTexParameteri(3553, 10242, 10496);
        GL11.glTexParameteri(3553, 10243, 10496);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glEnable(3553);
        this.mc.renderEngine.resetBoundTexture();
        OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }
    
    private void updateTorchFlicker() {
        this.torchFlickerDX += (float)((Math.random() - Math.random()) * Math.random() * Math.random());
        this.torchFlickerDY += (float)((Math.random() - Math.random()) * Math.random() * Math.random());
        this.torchFlickerDX *= 0.9;
        this.torchFlickerDY *= 0.9;
        this.torchFlickerX += (this.torchFlickerDX - this.torchFlickerX) * 1.0f;
        this.torchFlickerY += (this.torchFlickerDY - this.torchFlickerY) * 1.0f;
        this.lightmapUpdateNeeded = true;
    }
    
    private void updateLightmap(final float par1) {
        final WorldClient var2 = Minecraft.theWorld;
        if (var2 != null) {
            if (CustomColorizer.updateLightmap(var2, this, this.lightmapColors, Minecraft.thePlayer.isPotionActive(Potion.nightVision))) {
                this.mc.renderEngine.createTextureFromBytes(this.lightmapColors, 16, 16, this.lightmapTexture);
                return;
            }
            for (int var3 = 0; var3 < 256; ++var3) {
                final float var4 = var2.getSunBrightness(1.0f) * 0.95f + 0.05f;
                float var5 = var2.provider.lightBrightnessTable[var3 / 16] * var4;
                final float var6 = var2.provider.lightBrightnessTable[var3 % 16] * (this.torchFlickerX * 0.1f + 1.5f);
                if (var2.lastLightningBolt > 0) {
                    var5 = var2.provider.lightBrightnessTable[var3 / 16];
                }
                final float var7 = var5 * (var2.getSunBrightness(1.0f) * 0.65f + 0.35f);
                final float var8 = var5 * (var2.getSunBrightness(1.0f) * 0.65f + 0.35f);
                final float var9 = var6 * ((var6 * 0.6f + 0.4f) * 0.6f + 0.4f);
                final float var10 = var6 * (var6 * var6 * 0.6f + 0.4f);
                float var11 = var7 + var6;
                float var12 = var8 + var9;
                float var13 = var5 + var10;
                var11 = var11 * 0.96f + 0.03f;
                var12 = var12 * 0.96f + 0.03f;
                var13 = var13 * 0.96f + 0.03f;
                if (this.field_82831_U > 0.0f) {
                    final float var14 = this.field_82832_V + (this.field_82831_U - this.field_82832_V) * par1;
                    var11 = var11 * (1.0f - var14) + var11 * 0.7f * var14;
                    var12 = var12 * (1.0f - var14) + var12 * 0.6f * var14;
                    var13 = var13 * (1.0f - var14) + var13 * 0.6f * var14;
                }
                if (var2.provider.dimensionId == 1) {
                    var11 = 0.22f + var6 * 0.75f;
                    var12 = 0.28f + var9 * 0.75f;
                    var13 = 0.25f + var10 * 0.75f;
                }
                if (Minecraft.thePlayer.isPotionActive(Potion.nightVision)) {
                    final float var14 = this.getNightVisionBrightness(Minecraft.thePlayer, par1);
                    float var15 = 1.0f / var11;
                    if (var15 > 1.0f / var12) {
                        var15 = 1.0f / var12;
                    }
                    if (var15 > 1.0f / var13) {
                        var15 = 1.0f / var13;
                    }
                    var11 = var11 * (1.0f - var14) + var11 * var15 * var14;
                    var12 = var12 * (1.0f - var14) + var12 * var15 * var14;
                    var13 = var13 * (1.0f - var14) + var13 * var15 * var14;
                }
                if (var11 > 1.0f) {
                    var11 = 1.0f;
                }
                if (var12 > 1.0f) {
                    var12 = 1.0f;
                }
                if (var13 > 1.0f) {
                    var13 = 1.0f;
                }
                final float var14 = Morbid.getHookManager().onDetermineBrightness();
                float var15 = 1.0f - var11;
                float var16 = 1.0f - var12;
                float var17 = 1.0f - var13;
                var15 = 1.0f - var15 * var15 * var15 * var15;
                var16 = 1.0f - var16 * var16 * var16 * var16;
                var17 = 1.0f - var17 * var17 * var17 * var17;
                var11 = var11 * (1.0f - var14) + var15 * var14;
                var12 = var12 * (1.0f - var14) + var16 * var14;
                var13 = var13 * (1.0f - var14) + var17 * var14;
                var11 = var11 * 0.96f + 0.03f;
                var12 = var12 * 0.96f + 0.03f;
                var13 = var13 * 0.96f + 0.03f;
                if (var11 > 1.0f) {
                    var11 = 1.0f;
                }
                if (var12 > 1.0f) {
                    var12 = 1.0f;
                }
                if (var13 > 1.0f) {
                    var13 = 1.0f;
                }
                if (var11 < 0.0f) {
                    var11 = 0.0f;
                }
                if (var12 < 0.0f) {
                    var12 = 0.0f;
                }
                if (var13 < 0.0f) {
                    var13 = 0.0f;
                }
                final short var18 = 255;
                final int var19 = (int)(var11 * 255.0f);
                final int var20 = (int)(var12 * 255.0f);
                final int var21 = (int)(var13 * 255.0f);
                this.lightmapColors[var3] = (var18 << 24 | var19 << 16 | var20 << 8 | var21);
            }
            this.mc.renderEngine.createTextureFromBytes(this.lightmapColors, 16, 16, this.lightmapTexture);
        }
    }
    
    private float getNightVisionBrightness(final EntityPlayer par1EntityPlayer, final float par2) {
        final int var3 = par1EntityPlayer.getActivePotionEffect(Potion.nightVision).getDuration();
        return (var3 > 200) ? 1.0f : (0.7f + MathHelper.sin((var3 - par2) * 3.1415927f * 0.2f) * 0.3f);
    }
    
    public void updateCameraAndRender(final float par1) {
        this.mc.mcProfiler.startSection("lightTex");
        final WorldClient var2 = Minecraft.theWorld;
        this.checkDisplayMode();
        if (var2 != null && Config.getNewRelease() != null) {
            final String var3 = "HD_U " + Config.getNewRelease();
            this.mc.ingameGUI.getChatGUI().printChatMessage("A new §eOptiFine§f version is available: §e" + var3 + "§f");
            Config.setNewRelease(null);
        }
        if (Minecraft.currentScreen instanceof GuiMainMenu) {
            this.updateMainMenu((GuiMainMenu)Minecraft.currentScreen);
        }
        if (this.updatedWorld != var2) {
            RandomMobs.worldChanged(this.updatedWorld, var2);
            Config.updateThreadPriorities();
            this.lastServerTime = 0L;
            this.lastServerTicks = 0;
            this.updatedWorld = var2;
        }
        if (this.lastTexturePack == null) {
            this.lastTexturePack = this.mc.texturePackList.getSelectedTexturePack().getTexturePackFileName();
        }
        if (!this.lastTexturePack.equals(this.mc.texturePackList.getSelectedTexturePack().getTexturePackFileName())) {
            this.mc.renderGlobal.loadRenderers();
            this.lastTexturePack = this.mc.texturePackList.getSelectedTexturePack().getTexturePackFileName();
        }
        RenderBlocks.fancyGrass = (Config.isGrassFancy() || Config.isBetterGrassFancy());
        Block.leaves.setGraphicsLevel(Config.isTreesFancy());
        if (this.lightmapUpdateNeeded) {
            this.updateLightmap(par1);
        }
        this.mc.mcProfiler.endSection();
        final boolean var4 = Display.isActive();
        if (!var4 && this.mc.gameSettings.pauseOnLostFocus && (!this.mc.gameSettings.touchscreen || !Mouse.isButtonDown(1))) {
            if (Minecraft.getSystemTime() - this.prevFrameTime > 500L) {
                this.mc.displayInGameMenu();
            }
        }
        else {
            this.prevFrameTime = Minecraft.getSystemTime();
        }
        this.mc.mcProfiler.startSection("mouse");
        if (this.mc.inGameHasFocus && var4) {
            this.mc.mouseHelper.mouseXYChange();
            final float var5 = this.mc.gameSettings.mouseSensitivity * 0.6f + 0.2f;
            final float var6 = var5 * var5 * var5 * 8.0f;
            float var7 = this.mc.mouseHelper.deltaX * var6;
            float var8 = this.mc.mouseHelper.deltaY * var6;
            byte var9 = 1;
            if (this.mc.gameSettings.invertMouse) {
                var9 = -1;
            }
            if (this.mc.gameSettings.smoothCamera) {
                this.smoothCamYaw += var7;
                this.smoothCamPitch += var8;
                final float var10 = par1 - this.smoothCamPartialTicks;
                this.smoothCamPartialTicks = par1;
                var7 = this.smoothCamFilterX * var10;
                var8 = this.smoothCamFilterY * var10;
                Minecraft.thePlayer.setAngles(var7, var8 * var9);
            }
            else {
                Minecraft.thePlayer.setAngles(var7, var8 * var9);
            }
        }
        this.mc.mcProfiler.endSection();
        if (!this.mc.skipRenderWorld) {
            EntityRenderer.anaglyphEnable = this.mc.gameSettings.anaglyph;
            final ScaledResolution var11 = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
            final int var12 = ScaledResolution.getScaledWidth();
            final int var13 = ScaledResolution.getScaledHeight();
            final int var14 = Mouse.getX() * var12 / this.mc.displayWidth;
            final int var15 = var13 - Mouse.getY() * var13 / this.mc.displayHeight - 1;
            final int var16 = performanceToFps(this.mc.gameSettings.limitFramerate);
            if (Minecraft.theWorld != null) {
                this.mc.mcProfiler.startSection("level");
                if (this.mc.gameSettings.limitFramerate == 0) {
                    this.renderWorld(par1, 0L);
                }
                else {
                    this.renderWorld(par1, this.renderEndNanoTime + 1000000000 / var16);
                }
                this.renderEndNanoTime = System.nanoTime();
                this.mc.mcProfiler.endStartSection("gui");
                if (!this.mc.gameSettings.hideGUI || Minecraft.currentScreen != null) {
                    this.mc.ingameGUI.renderGameOverlay(par1, Minecraft.currentScreen != null, var14, var15);
                }
                this.mc.mcProfiler.endSection();
            }
            else {
                GL11.glViewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
                GL11.glMatrixMode(5889);
                GL11.glLoadIdentity();
                GL11.glMatrixMode(5888);
                GL11.glLoadIdentity();
                this.setupOverlayRendering();
                this.renderEndNanoTime = System.nanoTime();
            }
            if (Minecraft.currentScreen != null) {
                GL11.glClear(256);
                try {
                    Minecraft.currentScreen.drawScreen(var14, var15, par1);
                }
                catch (Throwable var18) {
                    final CrashReport var17 = CrashReport.makeCrashReport(var18, "Rendering screen");
                    final CrashReportCategory var19 = var17.makeCategory("Screen render details");
                    var19.addCrashSectionCallable("Screen name", new CallableScreenName(this));
                    var19.addCrashSectionCallable("Mouse location", new CallableMouseLocation(this, var14, var15));
                    var19.addCrashSectionCallable("Screen size", new CallableScreenSize(this, var11));
                    throw new ReportedException(var17);
                }
                if (Minecraft.currentScreen != null && Minecraft.currentScreen.guiParticles != null) {
                    Minecraft.currentScreen.guiParticles.draw(par1);
                }
            }
        }
        this.waitForServerThread();
        if (this.mc.gameSettings.showDebugInfo != this.lastShowDebugInfo) {
            this.showExtendedDebugInfo = this.mc.gameSettings.showDebugProfilerChart;
            this.lastShowDebugInfo = this.mc.gameSettings.showDebugInfo;
        }
        if (this.mc.gameSettings.showDebugInfo) {
            this.showLagometer(this.mc.mcProfiler.timeTickNano, this.mc.mcProfiler.timeUpdateChunksNano);
        }
        if (this.mc.gameSettings.ofProfiler) {
            this.mc.gameSettings.showDebugProfilerChart = true;
        }
    }
    
    private void waitForServerThread() {
        this.serverWaitTimeCurrent = 0;
        if (!Config.isSmoothWorld()) {
            this.lastServerTime = 0L;
            this.lastServerTicks = 0;
        }
        else if (this.mc.getIntegratedServer() != null) {
            final IntegratedServer var1 = this.mc.getIntegratedServer();
            final boolean var2 = var1.getServerListeningThread().isGamePaused();
            if (var2) {
                if (Minecraft.currentScreen instanceof GuiDownloadTerrain) {
                    Config.sleep(20L);
                }
                this.lastServerTime = 0L;
                this.lastServerTicks = 0;
            }
            else {
                if (this.serverWaitTime > 0) {
                    Config.sleep(this.serverWaitTime);
                    this.serverWaitTimeCurrent = this.serverWaitTime;
                }
                final long var3 = System.nanoTime() / 1000000L;
                if (this.lastServerTime != 0L && this.lastServerTicks != 0) {
                    long var4 = var3 - this.lastServerTime;
                    if (var4 < 0L) {
                        this.lastServerTime = var3;
                        var4 = 0L;
                    }
                    if (var4 >= 50L) {
                        this.lastServerTime = var3;
                        final int var5 = var1.getTickCounter();
                        int var6 = var5 - this.lastServerTicks;
                        if (var6 < 0) {
                            this.lastServerTicks = var5;
                            var6 = 0;
                        }
                        if (var6 < 1 && this.serverWaitTime < 100) {
                            this.serverWaitTime += 2;
                        }
                        if (var6 > 1 && this.serverWaitTime > 0) {
                            --this.serverWaitTime;
                        }
                        this.lastServerTicks = var5;
                    }
                }
                else {
                    this.lastServerTime = var3;
                    this.lastServerTicks = var1.getTickCounter();
                    this.avgServerTickDiff = 1.0f;
                    this.avgServerTimeDiff = 50.0f;
                }
            }
        }
    }
    
    private void showLagometer(final long var1, final long var3) {
        if (this.mc.gameSettings.ofLagometer || this.showExtendedDebugInfo) {
            if (this.prevFrameTimeNano == -1L) {
                this.prevFrameTimeNano = System.nanoTime();
            }
            final long var4 = System.nanoTime();
            final int var5 = this.numRecordedFrameTimes & this.frameTimes.length - 1;
            this.tickTimes[var5] = var1;
            this.chunkTimes[var5] = var3;
            this.serverTimes[var5] = this.serverWaitTimeCurrent;
            this.frameTimes[var5] = var4 - this.prevFrameTimeNano;
            ++this.numRecordedFrameTimes;
            this.prevFrameTimeNano = var4;
            GL11.glClear(256);
            GL11.glMatrixMode(5889);
            GL11.glEnable(2903);
            GL11.glLoadIdentity();
            GL11.glOrtho(0.0, this.mc.displayWidth, this.mc.displayHeight, 0.0, 1000.0, 3000.0);
            GL11.glMatrixMode(5888);
            GL11.glLoadIdentity();
            GL11.glTranslatef(0.0f, 0.0f, -2000.0f);
            GL11.glLineWidth(1.0f);
            GL11.glDisable(3553);
            final Tessellator var6 = Tessellator.instance;
            var6.startDrawing(1);
            for (int var7 = 0; var7 < this.frameTimes.length; ++var7) {
                final int var8 = (var7 - this.numRecordedFrameTimes & this.frameTimes.length - 1) * 255 / this.frameTimes.length;
                final long var9 = this.frameTimes[var7] / 200000L;
                float var10 = this.mc.displayHeight;
                var6.setColorOpaque_I(-16777216 + var8 * 256);
                var6.addVertex(var7 + 0.5f, var10 - var9 + 0.5f, 0.0);
                var6.addVertex(var7 + 0.5f, var10 + 0.5f, 0.0);
                var10 -= var9;
                final long var11 = this.tickTimes[var7] / 200000L;
                var6.setColorOpaque_I(-16777216 + var8 * 65536 + var8 * 256 + var8 * 1);
                var6.addVertex(var7 + 0.5f, var10 + 0.5f, 0.0);
                var6.addVertex(var7 + 0.5f, var10 + var11 + 0.5f, 0.0);
                var10 += var11;
                final long var12 = this.chunkTimes[var7] / 200000L;
                var6.setColorOpaque_I(-16777216 + var8 * 65536);
                var6.addVertex(var7 + 0.5f, var10 + 0.5f, 0.0);
                var6.addVertex(var7 + 0.5f, var10 + var12 + 0.5f, 0.0);
                var10 += var12;
                final long var13 = this.serverTimes[var7];
                if (var13 > 0L) {
                    final long var14 = var13 * 1000000L / 200000L;
                    var6.setColorOpaque_I(-16777216 + var8 * 1);
                    var6.addVertex(var7 + 0.5f, var10 + 0.5f, 0.0);
                    var6.addVertex(var7 + 0.5f, var10 + var14 + 0.5f, 0.0);
                }
            }
            var6.draw();
        }
    }
    
    private void updateMainMenu(final GuiMainMenu var1) {
        try {
            String var2 = null;
            final Calendar var3 = Calendar.getInstance();
            var3.setTime(new Date());
            final int var4 = var3.get(5);
            final int var5 = var3.get(2) + 1;
            if (var4 == 8 && var5 == 4) {
                var2 = "Happy birthday, OptiFine!";
            }
            if (var4 == 14 && var5 == 8) {
                var2 = "Happy birthday, sp614x!";
            }
            if (var2 == null) {
                return;
            }
            final Field[] var6 = GuiMainMenu.class.getDeclaredFields();
            for (int var7 = 0; var7 < var6.length; ++var7) {
                if (var6[var7].getType() == String.class) {
                    var6[var7].setAccessible(true);
                    var6[var7].set(var1, var2);
                    break;
                }
            }
        }
        catch (Throwable t) {}
    }
    
    private void checkDisplayMode() {
        try {
            if (Display.isFullscreen()) {
                if (this.fullscreenModeChecked) {
                    return;
                }
                this.fullscreenModeChecked = true;
                this.desktopModeChecked = false;
                final DisplayMode var1 = Display.getDisplayMode();
                final Dimension var2 = Config.getFullscreenDimension();
                if (var2 == null) {
                    return;
                }
                if (var1.getWidth() == var2.width && var1.getHeight() == var2.height) {
                    return;
                }
                final DisplayMode var3 = Config.getDisplayMode(var2);
                Display.setDisplayMode(var3);
                this.mc.displayWidth = Display.getDisplayMode().getWidth();
                this.mc.displayHeight = Display.getDisplayMode().getHeight();
                if (this.mc.displayWidth <= 0) {
                    this.mc.displayWidth = 1;
                }
                if (this.mc.displayHeight <= 0) {
                    this.mc.displayHeight = 1;
                }
                Display.setFullscreen(true);
                this.mc.gameSettings.updateVSync();
                Display.update();
                GL11.glEnable(3553);
            }
            else {
                if (this.desktopModeChecked) {
                    return;
                }
                this.desktopModeChecked = true;
                this.fullscreenModeChecked = false;
                if (Config.getDesktopDisplayMode() == null) {
                    Config.setDesktopDisplayMode(Display.getDesktopDisplayMode());
                }
                final DisplayMode var1 = Display.getDisplayMode();
                if (var1.equals(Config.getDesktopDisplayMode())) {
                    return;
                }
                Display.setDisplayMode(Config.getDesktopDisplayMode());
                if (this.mc.mcCanvas != null) {
                    this.mc.displayWidth = this.mc.mcCanvas.getWidth();
                    this.mc.displayHeight = this.mc.mcCanvas.getHeight();
                }
                if (this.mc.displayWidth <= 0) {
                    this.mc.displayWidth = 1;
                }
                if (this.mc.displayHeight <= 0) {
                    this.mc.displayHeight = 1;
                }
                Display.setFullscreen(false);
                this.mc.gameSettings.updateVSync();
                Display.update();
                GL11.glEnable(3553);
            }
        }
        catch (Exception var4) {
            var4.printStackTrace();
        }
    }
    
    public void renderWorld(final float par1, final long par2) {
        this.mc.mcProfiler.startSection("lightTex");
        if (this.lightmapUpdateNeeded) {
            this.updateLightmap(par1);
        }
        GL11.glEnable(2884);
        GL11.glEnable(2929);
        if (this.mc.renderViewEntity == null) {
            this.mc.renderViewEntity = Minecraft.thePlayer;
        }
        this.mc.mcProfiler.endStartSection("pick");
        this.getMouseOver(par1);
        final EntityLiving var4 = this.mc.renderViewEntity;
        final RenderGlobal var5 = this.mc.renderGlobal;
        final EffectRenderer var6 = this.mc.effectRenderer;
        final double var7 = var4.lastTickPosX + (var4.posX - var4.lastTickPosX) * par1;
        final double var8 = var4.lastTickPosY + (var4.posY - var4.lastTickPosY) * par1;
        final double var9 = var4.lastTickPosZ + (var4.posZ - var4.lastTickPosZ) * par1;
        this.mc.mcProfiler.endStartSection("center");
        for (int var10 = 0; var10 < 2; ++var10) {
            if (this.mc.gameSettings.anaglyph) {
                EntityRenderer.anaglyphField = var10;
                if (EntityRenderer.anaglyphField == 0) {
                    GL11.glColorMask(false, true, true, false);
                }
                else {
                    GL11.glColorMask(true, false, false, false);
                }
            }
            this.mc.mcProfiler.endStartSection("clear");
            GL11.glViewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
            this.updateFogColor(par1);
            GL11.glClear(16640);
            GL11.glEnable(2884);
            this.mc.mcProfiler.endStartSection("camera");
            this.setupCameraTransform(par1, var10);
            ActiveRenderInfo.updateRenderInfo(Minecraft.thePlayer, this.mc.gameSettings.thirdPersonView == 2);
            this.mc.mcProfiler.endStartSection("frustrum");
            ClippingHelperImpl.getInstance();
            if (!Config.isSkyEnabled() && !Config.isSunMoonEnabled() && !Config.isStarsEnabled()) {
                GL11.glDisable(3042);
            }
            else {
                this.setupFog(-1, par1);
                this.mc.mcProfiler.endStartSection("sky");
                var5.renderSky(par1);
            }
            GL11.glEnable(2912);
            this.setupFog(1, par1);
            if (this.mc.gameSettings.ambientOcclusion != 0) {
                GL11.glShadeModel(7425);
            }
            this.mc.mcProfiler.endStartSection("culling");
            final Frustrum var11 = new Frustrum();
            var11.setPosition(var7, var8, var9);
            this.mc.renderGlobal.clipRenderersByFrustum(var11, par1);
            if (var10 == 0) {
                this.mc.mcProfiler.endStartSection("updatechunks");
                while (!this.mc.renderGlobal.updateRenderers(var4, false) && par2 != 0L) {
                    final long var12 = par2 - System.nanoTime();
                    if (var12 < 0L) {
                        break;
                    }
                    if (var12 > 1000000000L) {
                        break;
                    }
                }
            }
            if (var4.posY < 128.0) {
                this.renderCloudsCheck(var5, par1);
            }
            this.mc.mcProfiler.endStartSection("prepareterrain");
            this.setupFog(0, par1);
            GL11.glEnable(2912);
            this.mc.renderEngine.bindTexture("/terrain.png");
            RenderHelper.disableStandardItemLighting();
            this.mc.mcProfiler.endStartSection("terrain");
            var5.sortAndRender(var4, 0, par1);
            GL11.glShadeModel(7424);
            final boolean var13 = Reflector.ForgeHooksClient.exists();
            if (this.debugViewDirection == 0) {
                RenderHelper.enableStandardItemLighting();
                this.mc.mcProfiler.endStartSection("entities");
                if (var13) {
                    Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, 0);
                }
                var5.renderEntities(var4.getPosition(par1), var11, par1);
                if (var13) {
                    Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, -1);
                }
                this.enableLightmap(par1);
                this.mc.mcProfiler.endStartSection("litParticles");
                var6.renderLitParticles(var4, par1);
                RenderHelper.disableStandardItemLighting();
                this.setupFog(0, par1);
                this.mc.mcProfiler.endStartSection("particles");
                var6.renderParticles(var4, par1);
                this.disableLightmap(par1);
                if (this.mc.objectMouseOver != null && var4.isInsideOfMaterial(Material.water) && var4 instanceof EntityPlayer && !this.mc.gameSettings.hideGUI) {
                    final EntityPlayer var14 = (EntityPlayer)var4;
                    GL11.glDisable(3008);
                    this.mc.mcProfiler.endStartSection("outline");
                    if (!var13 || !Reflector.callBoolean(Reflector.ForgeHooksClient_onDrawBlockHighlight, var5, var14, this.mc.objectMouseOver, 0, var14.inventory.getCurrentItem(), par1)) {
                        var5.drawBlockBreaking(var14, this.mc.objectMouseOver, 0, var14.inventory.getCurrentItem(), par1);
                        if (!this.mc.gameSettings.hideGUI) {
                            var5.drawSelectionBox(var14, this.mc.objectMouseOver, 0, var14.inventory.getCurrentItem(), par1);
                        }
                    }
                    GL11.glEnable(3008);
                }
            }
            GL11.glDisable(3042);
            GL11.glEnable(2884);
            GL11.glBlendFunc(770, 771);
            GL11.glDepthMask(true);
            this.setupFog(0, par1);
            GL11.glEnable(3042);
            GL11.glDisable(2884);
            this.mc.renderEngine.bindTexture("/terrain.png");
            WrUpdates.resumeBackgroundUpdates();
            if (Config.isWaterFancy()) {
                this.mc.mcProfiler.endStartSection("water");
                if (this.mc.gameSettings.ambientOcclusion != 0) {
                    GL11.glShadeModel(7425);
                }
                GL11.glColorMask(false, false, false, false);
                final int var15 = var5.renderAllSortedRenderers(1, par1);
                if (this.mc.gameSettings.anaglyph) {
                    if (EntityRenderer.anaglyphField == 0) {
                        GL11.glColorMask(false, true, true, true);
                    }
                    else {
                        GL11.glColorMask(true, false, false, true);
                    }
                }
                else {
                    GL11.glColorMask(true, true, true, true);
                }
                if (var15 > 0) {
                    var5.renderAllSortedRenderers(1, par1);
                }
                GL11.glShadeModel(7424);
            }
            else {
                this.mc.mcProfiler.endStartSection("water");
                var5.renderAllSortedRenderers(1, par1);
            }
            WrUpdates.pauseBackgroundUpdates();
            if (var13) {
                RenderHelper.enableStandardItemLighting();
                this.mc.mcProfiler.endStartSection("entities");
                Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, 1);
                var5.renderEntities(var4.getPosition(par1), var11, par1);
                Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, -1);
                RenderHelper.disableStandardItemLighting();
            }
            GL11.glDepthMask(true);
            GL11.glEnable(2884);
            GL11.glDisable(3042);
            if (this.cameraZoom == 1.0 && var4 instanceof EntityPlayer && !this.mc.gameSettings.hideGUI && this.mc.objectMouseOver != null && !var4.isInsideOfMaterial(Material.water)) {
                final EntityPlayer var14 = (EntityPlayer)var4;
                GL11.glDisable(3008);
                this.mc.mcProfiler.endStartSection("outline");
                if (!var13 || !Reflector.callBoolean(Reflector.ForgeHooksClient_onDrawBlockHighlight, var5, var14, this.mc.objectMouseOver, 0, var14.inventory.getCurrentItem(), par1)) {
                    var5.drawBlockBreaking(var14, this.mc.objectMouseOver, 0, var14.inventory.getCurrentItem(), par1);
                    if (!this.mc.gameSettings.hideGUI) {
                        var5.drawSelectionBox(var14, this.mc.objectMouseOver, 0, var14.inventory.getCurrentItem(), par1);
                    }
                }
                GL11.glEnable(3008);
            }
            this.mc.mcProfiler.endStartSection("destroyProgress");
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 1);
            var5.drawBlockDamageTexture(Tessellator.instance, var4, par1);
            GL11.glDisable(3042);
            this.mc.mcProfiler.endStartSection("weather");
            this.renderRainSnow(par1);
            GL11.glDisable(2912);
            if (var4.posY >= 128.0) {
                this.renderCloudsCheck(var5, par1);
            }
            if (var13) {
                this.mc.mcProfiler.endStartSection("FRenderLast");
                Reflector.callVoid(Reflector.ForgeHooksClient_dispatchRenderLast, var5, par1);
            }
            this.mc.mcProfiler.endStartSection("hand");
            if (this.cameraZoom == 1.0) {
                GL11.glClear(256);
                this.renderHand(par1, var10);
            }
            final boolean var16 = this.mc.gameSettings.viewBobbing;
            this.mc.gameSettings.viewBobbing = false;
            GL11.glPushMatrix();
            this.setupCameraTransform(par1, var10);
            Morbid.getHookManager().onRenderHand();
            GL11.glPopMatrix();
            this.mc.gameSettings.viewBobbing = var16;
            if (!this.mc.gameSettings.anaglyph) {
                this.mc.mcProfiler.endSection();
                return;
            }
        }
        GL11.glColorMask(true, true, true, false);
        this.mc.mcProfiler.endSection();
    }
    
    private void renderCloudsCheck(final RenderGlobal par1RenderGlobal, final float par2) {
        if (this.mc.gameSettings.shouldRenderClouds()) {
            this.mc.mcProfiler.endStartSection("clouds");
            GL11.glPushMatrix();
            this.setupFog(0, par2);
            GL11.glEnable(2912);
            par1RenderGlobal.renderClouds(par2);
            GL11.glDisable(2912);
            this.setupFog(1, par2);
            GL11.glPopMatrix();
        }
    }
    
    private void addRainParticles() {
        float var1 = Minecraft.theWorld.getRainStrength(1.0f);
        if (!Config.isRainFancy()) {
            var1 /= 2.0f;
        }
        if (Config.isRainSplash()) {
            this.random.setSeed(this.rendererUpdateCount * 312987231L);
            final EntityLiving var2 = this.mc.renderViewEntity;
            final WorldClient var3 = Minecraft.theWorld;
            final int var4 = MathHelper.floor_double(var2.posX);
            final int var5 = MathHelper.floor_double(var2.posY);
            final int var6 = MathHelper.floor_double(var2.posZ);
            final byte var7 = 10;
            double var8 = 0.0;
            double var9 = 0.0;
            double var10 = 0.0;
            int var11 = 0;
            int var12 = (int)(100.0f * var1 * var1);
            if (this.mc.gameSettings.particleSetting == 1) {
                var12 >>= 1;
            }
            else if (this.mc.gameSettings.particleSetting == 2) {
                var12 = 0;
            }
            for (int var13 = 0; var13 < var12; ++var13) {
                final int var14 = var4 + this.random.nextInt(var7) - this.random.nextInt(var7);
                final int var15 = var6 + this.random.nextInt(var7) - this.random.nextInt(var7);
                final int var16 = var3.getPrecipitationHeight(var14, var15);
                final int var17 = var3.getBlockId(var14, var16 - 1, var15);
                final BiomeGenBase var18 = var3.getBiomeGenForCoords(var14, var15);
                if (var16 <= var5 + var7 && var16 >= var5 - var7 && var18.canSpawnLightningBolt() && var18.getFloatTemperature() >= 0.2f) {
                    final float var19 = this.random.nextFloat();
                    final float var20 = this.random.nextFloat();
                    if (var17 > 0) {
                        if (Block.blocksList[var17].blockMaterial == Material.lava) {
                            this.mc.effectRenderer.addEffect(new EntitySmokeFX(var3, var14 + var19, var16 + 0.1f - Block.blocksList[var17].getBlockBoundsMinY(), var15 + var20, 0.0, 0.0, 0.0));
                        }
                        else {
                            ++var11;
                            if (this.random.nextInt(var11) == 0) {
                                var8 = var14 + var19;
                                var9 = var16 + 0.1f - Block.blocksList[var17].getBlockBoundsMinY();
                                var10 = var15 + var20;
                            }
                            final EntityRainFX var21 = new EntityRainFX(var3, var14 + var19, var16 + 0.1f - Block.blocksList[var17].getBlockBoundsMinY(), var15 + var20);
                            CustomColorizer.updateWaterFX(var21, var3);
                            this.mc.effectRenderer.addEffect(var21);
                        }
                    }
                }
            }
            if (var11 > 0 && this.random.nextInt(3) < this.rainSoundCounter++) {
                this.rainSoundCounter = 0;
                if (var9 > var2.posY + 1.0 && var3.getPrecipitationHeight(MathHelper.floor_double(var2.posX), MathHelper.floor_double(var2.posZ)) > MathHelper.floor_double(var2.posY)) {
                    Minecraft.theWorld.playSound(var8, var9, var10, "ambient.weather.rain", 0.1f, 0.5f, false);
                }
                else {
                    Minecraft.theWorld.playSound(var8, var9, var10, "ambient.weather.rain", 0.2f, 1.0f, false);
                }
            }
        }
    }
    
    protected void renderRainSnow(final float par1) {
        final float var2 = Minecraft.theWorld.getRainStrength(par1);
        if (var2 > 0.0f) {
            this.enableLightmap(par1);
            if (this.rainXCoords == null) {
                this.rainXCoords = new float[1024];
                this.rainYCoords = new float[1024];
                for (int var3 = 0; var3 < 32; ++var3) {
                    for (int var4 = 0; var4 < 32; ++var4) {
                        final float var5 = var4 - 16;
                        final float var6 = var3 - 16;
                        final float var7 = MathHelper.sqrt_float(var5 * var5 + var6 * var6);
                        this.rainXCoords[var3 << 5 | var4] = -var6 / var7;
                        this.rainYCoords[var3 << 5 | var4] = var5 / var7;
                    }
                }
            }
            if (Config.isRainOff()) {
                return;
            }
            final EntityLiving var8 = this.mc.renderViewEntity;
            final WorldClient var9 = Minecraft.theWorld;
            final int var10 = MathHelper.floor_double(var8.posX);
            final int var11 = MathHelper.floor_double(var8.posY);
            final int var12 = MathHelper.floor_double(var8.posZ);
            final Tessellator var13 = Tessellator.instance;
            GL11.glDisable(2884);
            GL11.glNormal3f(0.0f, 1.0f, 0.0f);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glAlphaFunc(516, 0.01f);
            this.mc.renderEngine.bindTexture("/environment/snow.png");
            final double var14 = var8.lastTickPosX + (var8.posX - var8.lastTickPosX) * par1;
            final double var15 = var8.lastTickPosY + (var8.posY - var8.lastTickPosY) * par1;
            final double var16 = var8.lastTickPosZ + (var8.posZ - var8.lastTickPosZ) * par1;
            final int var17 = MathHelper.floor_double(var15);
            byte var18 = 5;
            if (Config.isRainFancy()) {
                var18 = 10;
            }
            boolean var19 = false;
            byte var20 = -1;
            final float var21 = this.rendererUpdateCount + par1;
            if (Config.isRainFancy()) {
                var18 = 10;
            }
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            var19 = false;
            for (int var22 = var12 - var18; var22 <= var12 + var18; ++var22) {
                for (int var23 = var10 - var18; var23 <= var10 + var18; ++var23) {
                    final int var24 = (var22 - var12 + 16) * 32 + var23 - var10 + 16;
                    final float var25 = this.rainXCoords[var24] * 0.5f;
                    final float var26 = this.rainYCoords[var24] * 0.5f;
                    final BiomeGenBase var27 = var9.getBiomeGenForCoords(var23, var22);
                    if (var27.canSpawnLightningBolt() || var27.getEnableSnow()) {
                        final int var28 = var9.getPrecipitationHeight(var23, var22);
                        int var29 = var11 - var18;
                        int var30 = var11 + var18;
                        if (var29 < var28) {
                            var29 = var28;
                        }
                        if (var30 < var28) {
                            var30 = var28;
                        }
                        final float var31 = 1.0f;
                        int var32;
                        if ((var32 = var28) < var17) {
                            var32 = var17;
                        }
                        if (var29 != var30) {
                            this.random.setSeed(var23 * var23 * 3121 + var23 * 45238971 ^ var22 * var22 * 418711 + var22 * 13761);
                            final float var33 = var27.getFloatTemperature();
                            if (var9.getWorldChunkManager().getTemperatureAtHeight(var33, var28) >= 0.15f) {
                                if (var20 != 0) {
                                    if (var20 >= 0) {
                                        var13.draw();
                                    }
                                    var20 = 0;
                                    this.mc.renderEngine.bindTexture("/environment/rain.png");
                                    var13.startDrawingQuads();
                                }
                                final float var34 = ((this.rendererUpdateCount + var23 * var23 * 3121 + var23 * 45238971 + var22 * var22 * 418711 + var22 * 13761 & 0x1F) + par1) / 32.0f * (3.0f + this.random.nextFloat());
                                final double var35 = var23 + 0.5f - var8.posX;
                                final double var36 = var22 + 0.5f - var8.posZ;
                                final float var37 = MathHelper.sqrt_double(var35 * var35 + var36 * var36) / var18;
                                final float var38 = 1.0f;
                                var13.setBrightness(var9.getLightBrightnessForSkyBlocks(var23, var32, var22, 0));
                                var13.setColorRGBA_F(var38, var38, var38, ((1.0f - var37 * var37) * 0.5f + 0.5f) * var2);
                                var13.setTranslation(-var14 * 1.0, -var15 * 1.0, -var16 * 1.0);
                                var13.addVertexWithUV(var23 - var25 + 0.5, var29, var22 - var26 + 0.5, 0.0f * var31, var29 * var31 / 4.0f + var34 * var31);
                                var13.addVertexWithUV(var23 + var25 + 0.5, var29, var22 + var26 + 0.5, 1.0f * var31, var29 * var31 / 4.0f + var34 * var31);
                                var13.addVertexWithUV(var23 + var25 + 0.5, var30, var22 + var26 + 0.5, 1.0f * var31, var30 * var31 / 4.0f + var34 * var31);
                                var13.addVertexWithUV(var23 - var25 + 0.5, var30, var22 - var26 + 0.5, 0.0f * var31, var30 * var31 / 4.0f + var34 * var31);
                                var13.setTranslation(0.0, 0.0, 0.0);
                            }
                            else {
                                if (var20 != 1) {
                                    if (var20 >= 0) {
                                        var13.draw();
                                    }
                                    var20 = 1;
                                    this.mc.renderEngine.bindTexture("/environment/snow.png");
                                    var13.startDrawingQuads();
                                }
                                final float var34 = ((this.rendererUpdateCount & 0x1FF) + par1) / 512.0f;
                                final float var39 = this.random.nextFloat() + var21 * 0.01f * (float)this.random.nextGaussian();
                                final float var40 = this.random.nextFloat() + var21 * (float)this.random.nextGaussian() * 0.001f;
                                final double var36 = var23 + 0.5f - var8.posX;
                                final double var41 = var22 + 0.5f - var8.posZ;
                                final float var42 = MathHelper.sqrt_double(var36 * var36 + var41 * var41) / var18;
                                final float var43 = 1.0f;
                                var13.setBrightness((var9.getLightBrightnessForSkyBlocks(var23, var32, var22, 0) * 3 + 15728880) / 4);
                                var13.setColorRGBA_F(var43, var43, var43, ((1.0f - var42 * var42) * 0.3f + 0.5f) * var2);
                                var13.setTranslation(-var14 * 1.0, -var15 * 1.0, -var16 * 1.0);
                                var13.addVertexWithUV(var23 - var25 + 0.5, var29, var22 - var26 + 0.5, 0.0f * var31 + var39, var29 * var31 / 4.0f + var34 * var31 + var40);
                                var13.addVertexWithUV(var23 + var25 + 0.5, var29, var22 + var26 + 0.5, 1.0f * var31 + var39, var29 * var31 / 4.0f + var34 * var31 + var40);
                                var13.addVertexWithUV(var23 + var25 + 0.5, var30, var22 + var26 + 0.5, 1.0f * var31 + var39, var30 * var31 / 4.0f + var34 * var31 + var40);
                                var13.addVertexWithUV(var23 - var25 + 0.5, var30, var22 - var26 + 0.5, 0.0f * var31 + var39, var30 * var31 / 4.0f + var34 * var31 + var40);
                                var13.setTranslation(0.0, 0.0, 0.0);
                            }
                        }
                    }
                }
            }
            if (var20 >= 0) {
                var13.draw();
            }
            GL11.glEnable(2884);
            GL11.glDisable(3042);
            GL11.glAlphaFunc(516, 0.1f);
            this.disableLightmap(par1);
        }
    }
    
    public void setupOverlayRendering() {
        final ScaledResolution var1 = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
        GL11.glClear(256);
        GL11.glMatrixMode(5889);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0, var1.getScaledWidth_double(), var1.getScaledHeight_double(), 0.0, 1000.0, 3000.0);
        GL11.glMatrixMode(5888);
        GL11.glLoadIdentity();
        GL11.glTranslatef(0.0f, 0.0f, -2000.0f);
    }
    
    private void updateFogColor(final float par1) {
        final WorldClient var2 = Minecraft.theWorld;
        final EntityLiving var3 = this.mc.renderViewEntity;
        float var4 = 1.0f / (4 - this.mc.gameSettings.renderDistance);
        var4 = 1.0f - (float)Math.pow(var4, 0.25);
        Vec3 var5 = var2.getSkyColor(this.mc.renderViewEntity, par1);
        final int var6 = var2.provider.dimensionId;
        switch (var6) {
            case 0: {
                var5 = CustomColorizer.getSkyColor(var5, Minecraft.theWorld, this.mc.renderViewEntity.posX, this.mc.renderViewEntity.posY + 1.0, this.mc.renderViewEntity.posZ);
                break;
            }
            case 1: {
                var5 = CustomColorizer.getSkyColorEnd(var5);
                break;
            }
        }
        final float var7 = (float)var5.xCoord;
        final float var8 = (float)var5.yCoord;
        final float var9 = (float)var5.zCoord;
        Vec3 var10 = var2.getFogColor(par1);
        switch (var6) {
            case -1: {
                var10 = CustomColorizer.getFogColorNether(var10);
                break;
            }
            case 0: {
                var10 = CustomColorizer.getFogColor(var10, Minecraft.theWorld, this.mc.renderViewEntity.posX, this.mc.renderViewEntity.posY + 1.0, this.mc.renderViewEntity.posZ);
                break;
            }
            case 1: {
                var10 = CustomColorizer.getFogColorEnd(var10);
                break;
            }
        }
        this.fogColorRed = (float)var10.xCoord;
        this.fogColorGreen = (float)var10.yCoord;
        this.fogColorBlue = (float)var10.zCoord;
        if (this.mc.gameSettings.renderDistance < 2) {
            final Vec3 var11 = (MathHelper.sin(var2.getCelestialAngleRadians(par1)) > 0.0f) ? var2.getWorldVec3Pool().getVecFromPool(-1.0, 0.0, 0.0) : var2.getWorldVec3Pool().getVecFromPool(1.0, 0.0, 0.0);
            float var12 = (float)var3.getLook(par1).dotProduct(var11);
            if (var12 < 0.0f) {
                var12 = 0.0f;
            }
            if (var12 > 0.0f) {
                final float[] var13 = var2.provider.calcSunriseSunsetColors(var2.getCelestialAngle(par1), par1);
                if (var13 != null) {
                    var12 *= var13[3];
                    this.fogColorRed = this.fogColorRed * (1.0f - var12) + var13[0] * var12;
                    this.fogColorGreen = this.fogColorGreen * (1.0f - var12) + var13[1] * var12;
                    this.fogColorBlue = this.fogColorBlue * (1.0f - var12) + var13[2] * var12;
                }
            }
        }
        this.fogColorRed += (var7 - this.fogColorRed) * var4;
        this.fogColorGreen += (var8 - this.fogColorGreen) * var4;
        this.fogColorBlue += (var9 - this.fogColorBlue) * var4;
        final float var14 = var2.getRainStrength(par1);
        if (var14 > 0.0f) {
            final float var12 = 1.0f - var14 * 0.5f;
            final float var15 = 1.0f - var14 * 0.4f;
            this.fogColorRed *= var12;
            this.fogColorGreen *= var12;
            this.fogColorBlue *= var15;
        }
        float var12 = var2.getWeightedThunderStrength(par1);
        if (var12 > 0.0f) {
            final float var15 = 1.0f - var12 * 0.5f;
            this.fogColorRed *= var15;
            this.fogColorGreen *= var15;
            this.fogColorBlue *= var15;
        }
        final int var16 = ActiveRenderInfo.getBlockIdAtEntityViewpoint(Minecraft.theWorld, var3, par1);
        if (this.cloudFog) {
            final Vec3 var17 = var2.getCloudColour(par1);
            this.fogColorRed = (float)var17.xCoord;
            this.fogColorGreen = (float)var17.yCoord;
            this.fogColorBlue = (float)var17.zCoord;
        }
        else if (var16 != 0 && Block.blocksList[var16].blockMaterial == Material.water) {
            this.fogColorRed = 0.02f;
            this.fogColorGreen = 0.02f;
            this.fogColorBlue = 0.2f;
            final Vec3 var17 = CustomColorizer.getUnderwaterColor(Minecraft.theWorld, this.mc.renderViewEntity.posX, this.mc.renderViewEntity.posY + 1.0, this.mc.renderViewEntity.posZ);
            if (var17 != null) {
                this.fogColorRed = (float)var17.xCoord;
                this.fogColorGreen = (float)var17.yCoord;
                this.fogColorBlue = (float)var17.zCoord;
            }
        }
        else if (var16 != 0 && Block.blocksList[var16].blockMaterial == Material.lava) {
            this.fogColorRed = 0.6f;
            this.fogColorGreen = 0.1f;
            this.fogColorBlue = 0.0f;
        }
        final float var18 = this.fogColor2 + (this.fogColor1 - this.fogColor2) * par1;
        this.fogColorRed *= var18;
        this.fogColorGreen *= var18;
        this.fogColorBlue *= var18;
        double var19 = var2.provider.getVoidFogYFactor();
        if (!Config.isDepthFog()) {
            var19 = 1.0;
        }
        double var20 = (var3.lastTickPosY + (var3.posY - var3.lastTickPosY) * par1) * var19;
        if (var3.isPotionActive(Potion.blindness)) {
            final int var21 = var3.getActivePotionEffect(Potion.blindness).getDuration();
            if (var21 < 20) {
                var20 *= 1.0f - var21 / 20.0f;
            }
            else {
                var20 = 0.0;
            }
        }
        if (var20 < 1.0) {
            if (var20 < 0.0) {
                var20 = 0.0;
            }
            var20 *= var20;
            this.fogColorRed *= (float)var20;
            this.fogColorGreen *= (float)var20;
            this.fogColorBlue *= (float)var20;
        }
        if (this.field_82831_U > 0.0f) {
            final float var22 = this.field_82832_V + (this.field_82831_U - this.field_82832_V) * par1;
            this.fogColorRed = this.fogColorRed * (1.0f - var22) + this.fogColorRed * 0.7f * var22;
            this.fogColorGreen = this.fogColorGreen * (1.0f - var22) + this.fogColorGreen * 0.6f * var22;
            this.fogColorBlue = this.fogColorBlue * (1.0f - var22) + this.fogColorBlue * 0.6f * var22;
        }
        if (var3.isPotionActive(Potion.nightVision)) {
            final float var22 = this.getNightVisionBrightness(Minecraft.thePlayer, par1);
            float var23 = 1.0f / this.fogColorRed;
            if (var23 > 1.0f / this.fogColorGreen) {
                var23 = 1.0f / this.fogColorGreen;
            }
            if (var23 > 1.0f / this.fogColorBlue) {
                var23 = 1.0f / this.fogColorBlue;
            }
            this.fogColorRed = this.fogColorRed * (1.0f - var22) + this.fogColorRed * var23 * var22;
            this.fogColorGreen = this.fogColorGreen * (1.0f - var22) + this.fogColorGreen * var23 * var22;
            this.fogColorBlue = this.fogColorBlue * (1.0f - var22) + this.fogColorBlue * var23 * var22;
        }
        if (this.mc.gameSettings.anaglyph) {
            final float var22 = (this.fogColorRed * 30.0f + this.fogColorGreen * 59.0f + this.fogColorBlue * 11.0f) / 100.0f;
            final float var23 = (this.fogColorRed * 30.0f + this.fogColorGreen * 70.0f) / 100.0f;
            final float var24 = (this.fogColorRed * 30.0f + this.fogColorBlue * 70.0f) / 100.0f;
            this.fogColorRed = var22;
            this.fogColorGreen = var23;
            this.fogColorBlue = var24;
        }
        GL11.glClearColor(this.fogColorRed, this.fogColorGreen, this.fogColorBlue, 0.0f);
    }
    
    private void setupFog(final int par1, final float par2) {
        final EntityLiving var3 = this.mc.renderViewEntity;
        boolean var4 = false;
        if (var3 instanceof EntityPlayer) {
            var4 = ((EntityPlayer)var3).capabilities.isCreativeMode;
        }
        if (par1 == 999) {
            GL11.glFog(2918, this.setFogColorBuffer(0.0f, 0.0f, 0.0f, 1.0f));
            GL11.glFogi(2917, 9729);
            GL11.glFogf(2915, 0.0f);
            GL11.glFogf(2916, 8.0f);
            if (GLContext.getCapabilities().GL_NV_fog_distance) {
                GL11.glFogi(34138, 34139);
            }
            GL11.glFogf(2915, 0.0f);
        }
        else {
            GL11.glFog(2918, this.setFogColorBuffer(this.fogColorRed, this.fogColorGreen, this.fogColorBlue, 1.0f));
            GL11.glNormal3f(0.0f, -1.0f, 0.0f);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            final int var5 = ActiveRenderInfo.getBlockIdAtEntityViewpoint(Minecraft.theWorld, var3, par2);
            if (var3.isPotionActive(Potion.blindness)) {
                float var6 = 5.0f;
                final int var7 = var3.getActivePotionEffect(Potion.blindness).getDuration();
                if (var7 < 20) {
                    var6 = 5.0f + (this.farPlaneDistance - 5.0f) * (1.0f - var7 / 20.0f);
                }
                GL11.glFogi(2917, 9729);
                if (par1 < 0) {
                    GL11.glFogf(2915, 0.0f);
                    GL11.glFogf(2916, var6 * 0.8f);
                }
                else {
                    GL11.glFogf(2915, var6 * 0.25f);
                    GL11.glFogf(2916, var6);
                }
                if (Config.isFogFancy()) {
                    GL11.glFogi(34138, 34139);
                }
            }
            else if (this.cloudFog) {
                GL11.glFogi(2917, 2048);
                GL11.glFogf(2914, 0.1f);
                final float var6 = 1.0f;
                final float var8 = 1.0f;
                final float var9 = 1.0f;
                if (this.mc.gameSettings.anaglyph) {
                    final float var10 = (var6 * 30.0f + var8 * 59.0f + var9 * 11.0f) / 100.0f;
                    final float var11 = (var6 * 30.0f + var8 * 70.0f) / 100.0f;
                }
            }
            else if (var5 > 0 && Block.blocksList[var5].blockMaterial == Material.water) {
                GL11.glFogi(2917, 2048);
                float var12 = 0.1f;
                if (var3.isPotionActive(Potion.waterBreathing)) {
                    var12 = 0.05f;
                }
                if (Config.isClearWater()) {
                    var12 /= 5.0f;
                }
                GL11.glFogf(2914, var12);
                final float var6 = 0.4f;
                final float var8 = 0.4f;
                final float var9 = 0.9f;
                if (this.mc.gameSettings.anaglyph) {
                    final float var10 = (var6 * 30.0f + var8 * 59.0f + var9 * 11.0f) / 100.0f;
                    final float var11 = (var6 * 30.0f + var8 * 70.0f) / 100.0f;
                }
            }
            else if (var5 > 0 && Block.blocksList[var5].blockMaterial == Material.lava) {
                GL11.glFogi(2917, 2048);
                GL11.glFogf(2914, 2.0f);
                final float var6 = 0.4f;
                final float var8 = 0.3f;
                final float var9 = 0.3f;
                if (this.mc.gameSettings.anaglyph) {
                    final float var10 = (var6 * 30.0f + var8 * 59.0f + var9 * 11.0f) / 100.0f;
                    final float var11 = (var6 * 30.0f + var8 * 70.0f) / 100.0f;
                }
            }
            else {
                float var6 = this.farPlaneDistance;
                if (Config.isDepthFog() && Minecraft.theWorld.provider.getWorldHasVoidParticles() && !var4) {
                    double var13 = ((var3.getBrightnessForRender(par2) & 0xF00000) >> 20) / 16.0 + (var3.lastTickPosY + (var3.posY - var3.lastTickPosY) * par2 + 4.0) / 32.0;
                    if (var13 < 1.0) {
                        if (var13 < 0.0) {
                            var13 = 0.0;
                        }
                        var13 *= var13;
                        float var10 = 100.0f * (float)var13;
                        if (var10 < 5.0f) {
                            var10 = 5.0f;
                        }
                        if (var6 > var10) {
                            var6 = var10;
                        }
                    }
                }
                GL11.glFogi(2917, 9729);
                if (GLContext.getCapabilities().GL_NV_fog_distance) {
                    if (Config.isFogFancy()) {
                        GL11.glFogi(34138, 34139);
                    }
                    if (Config.isFogFast()) {
                        GL11.glFogi(34138, 34140);
                    }
                }
                float var12 = Config.getFogStart();
                float var14 = 1.0f;
                if (par1 < 0) {
                    var12 = 0.0f;
                    var14 = 0.8f;
                }
                if (Minecraft.theWorld.provider.doesXZShowFog((int)var3.posX, (int)var3.posZ)) {
                    var12 = 0.05f;
                    var14 = 1.0f;
                    var6 = this.farPlaneDistance;
                }
                GL11.glFogf(2915, var6 * var12);
                GL11.glFogf(2916, var6 * var14);
            }
            GL11.glEnable(2903);
            GL11.glColorMaterial(1028, 4608);
        }
    }
    
    private FloatBuffer setFogColorBuffer(final float par1, final float par2, final float par3, final float par4) {
        this.fogColorBuffer.clear();
        this.fogColorBuffer.put(par1).put(par2).put(par3).put(par4);
        this.fogColorBuffer.flip();
        return this.fogColorBuffer;
    }
    
    public static int performanceToFps(final int par0) {
        final Minecraft var1 = Config.getMinecraft();
        if (Minecraft.currentScreen != null && Minecraft.currentScreen instanceof GuiMainMenu) {
            return 35;
        }
        if (Minecraft.theWorld == null) {
            return 35;
        }
        int var2 = Config.getGameSettings().ofLimitFramerateFine;
        if (var2 <= 0) {
            var2 = 10000;
        }
        return var2;
    }
    
    static Minecraft getRendererMinecraft(final EntityRenderer par0EntityRenderer) {
        return par0EntityRenderer.mc;
    }
}

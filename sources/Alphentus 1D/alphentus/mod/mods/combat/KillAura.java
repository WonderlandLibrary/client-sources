package alphentus.mod.mods.combat;

import alphentus.event.Event;
import alphentus.event.Type;
import alphentus.init.Init;
import alphentus.mod.Mod;
import alphentus.mod.ModCategory;
import alphentus.settings.Setting;
import alphentus.utils.*;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.EnumParticleTypes;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * @author avox | lmao
 * @since on 30.07.2020.
 */
public class KillAura extends Mod {

    public AntiBots antiBots;
    public Entity finalEntity;
    public boolean canHit;
    public float yaw;
    public float pitch;
    public float animation;
    public float animationCircle;
    public boolean reverseCircle;

    public Setting range = new Setting("Range", 3.5F, 7, 4, false, this);
    public Setting preAimRange = new Setting("PreAim Range", 0, 1, 0, false, this);
    public Setting cps = new Setting("CPS", 1, 20, 8, true, this);
    public Setting rotationSpeed = new Setting("Rotation Speed", 20, 90, 50, true, this);
    public Setting hitChance = new Setting("HitChance", 0, 100, 100, true, this);

    public Setting preAim = new Setting("Pre Aiming", true, this);
    public Setting checkTabList = new Setting("Check TabList", true, this);
    public Setting throughWalls = new Setting("Through Walls", false, this);
    public Setting slowOnHit = new Setting("Slowing Hit", false, this);
    public Setting stopSprint = new Setting("Stop Sprint", false, this);
    public Setting correctMovement = new Setting("Correct Movement", false, this);
    public Setting blockHit = new Setting("Auto Block", false, this);
    public Setting smoothRots = new Setting("Smooth Rotations", false, this);
    public Setting clientRots = new Setting("Lock View", false, this);

    String modes[] = {"Single", "Switch"};
    public Setting attackMode = new Setting("Target Mode", modes, "Single", this);

    String[] targetEspModes = {"None", "Spiral", "Circle"};
    public Setting espModes = new Setting("ESP Mode", targetEspModes, "Circle", this);

    TimeUtil attackDelayTimer = new TimeUtil();
    RandomUtil randomRotationSpeed = new RandomUtil();
    RandomUtil randomDelay = new RandomUtil();
    ArrayList<Entity> possibleTargets = new ArrayList<>();
    ArrayList<Entity> mineplexProof = new ArrayList<>();

    public KillAura() {
        super("KillAura", Keyboard.KEY_NONE, true, ModCategory.COMBAT);
        Init.getInstance().settingManager.addSetting(attackMode);
        Init.getInstance().settingManager.addSetting(range);
        Init.getInstance().settingManager.addSetting(preAim);
        Init.getInstance().settingManager.addSetting(preAimRange);
        Init.getInstance().settingManager.addSetting(cps);
        Init.getInstance().settingManager.addSetting(rotationSpeed);
        Init.getInstance().settingManager.addSetting(hitChance);

        Init.getInstance().settingManager.addSetting(checkTabList);
        Init.getInstance().settingManager.addSetting(throughWalls);
        Init.getInstance().settingManager.addSetting(slowOnHit);
        Init.getInstance().settingManager.addSetting(stopSprint);
        Init.getInstance().settingManager.addSetting(correctMovement);
        Init.getInstance().settingManager.addSetting(blockHit);
        Init.getInstance().settingManager.addSetting(smoothRots);
        Init.getInstance().settingManager.addSetting(clientRots);
        Init.getInstance().settingManager.addSetting(espModes);
    }

    public static int getRainbow(final int speed, final double d) {
        float hue = (float) ((System.currentTimeMillis() - (d % speed) / 0.25) % speed);
        hue /= speed;
        return Color.getHSBColor(hue, (float) (0.55f), (float) (1F)).getRGB();
    }

    @EventTarget
    public void event(Event event) {
        if (event.getType() == Type.RENDER2D) {
            if (preAim.isState()) {
                preAimRange.setVisible(true);
            } else {
                preAimRange.setVisible(false);
            }

            if (smoothRots.isState()) {
                rotationSpeed.setVisible(true);
            } else {
                rotationSpeed.setVisible(false);
            }
        }

        if (!getState())
            return;

        if (finalEntity != null) {
            if (event.getType() == Type.RENDER3D) {
                if (espModes.getSelectedCombo().equals("Circle")) {
                    GL11.glPushMatrix();
                    drawCircle(finalEntity, event.getPartialTicks(), finalEntity.width + 0.1);
                    GL11.glColor4f(1, 1, 1, 1);
                    GL11.glPopMatrix();
                } else {
                    if (espModes.getSelectedCombo().equals("Spiral")) {
                        GL11.glPushMatrix();
                        drawSpiral(finalEntity, event.getPartialTicks(), 1);
                        GL11.glColor4f(1, 1, 1, 1);
                        GL11.glPopMatrix();
                    }
                }
            }
        }

        if (event.getType() == Type.PRE) {
            if (finalEntity != null) {
                if (clientRots.isState()) {
                    mc.thePlayer.rotationYaw = yaw;
                    mc.thePlayer.rotationPitch = pitch;
                } else {
                    event.setYaw(yaw);
                    event.setPitch(pitch);
                }

                if (blockHit.isState() && mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword && mc.thePlayer.getCurrentEquippedItem() != null)
                    mc.thePlayer.getCurrentEquippedItem().useItemRightClick(mc.theWorld, mc.thePlayer);

                attackEntity();
            }
        }

        if (event.getType() == Type.TICKUPDATE) {
            if (attackMode.getSelectedCombo().equals("Switch")) {
                if (!getInfoName().equals("Switch"))
                    this.setInfoName("Switch");
            } else {
                if (!getInfoName().equals("Single"))
                    this.setInfoName("Single");
            }

            if (stopSprint.isState()) {
                mc.gameSettings.keyBindSprint.pressed = false;
                mc.thePlayer.setSprinting(false);
            }

            if (mc.currentScreen instanceof GuiGameOver || !mc.thePlayer.isServerWorld() || mc.theWorld == null)
                setState(false);

            getPossibleTargets();
            setFinalEntity();

            if (finalEntity != null) {
                canHit = hitChance.getCurrent() > 0 && new Random().nextInt(100) <= hitChance.getCurrent();

                getRotations();
            }
        }
    }

    public void attackEntity() {
        if (attackDelayTimer.isDelayComplete(randomDelay.randomGaussian(30, (int) (1000 / cps.getCurrent()), true))) {
            if (mc.thePlayer.getDistanceToEntity(finalEntity) > range.getCurrent())
                return;

            Entity rayCastedEntity = RaycastUtil.rayCastEntity(range.getCurrent(), yaw, pitch);

            if (canHit && rayCastedEntity != null) {
                mc.thePlayer.swingItem();
                if (slowOnHit.isState()) {
                    mc.playerController.attackEntity(mc.thePlayer, rayCastedEntity);
                } else {
                    mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(rayCastedEntity, C02PacketUseEntity.Action.ATTACK));
                }
            }

            for (int i = 0; i < 2; i++) {
                mc.effectRenderer.emitParticleAtEntity(rayCastedEntity, EnumParticleTypes.CRIT);
                mc.effectRenderer.emitParticleAtEntity(rayCastedEntity, EnumParticleTypes.CRIT_MAGIC);
            }

            attackDelayTimer.reset();
        }
    }

    public void getRotations() {
            float speed = smoothRots.isState() ? (float) (rotationSpeed.getCurrent() + randomRotationSpeed.randomGaussian(7, 0, true)) : 360;

            float[] rotations = RotationUtil.faceEntity(finalEntity, yaw, pitch, speed, speed / 2, canHit);
            yaw = rotations[0];
            pitch = rotations[1];

        if (pitch > 90)
            pitch = 90;
        if (pitch < -90)
            pitch = -90;
    }

    public void setFinalEntity() {
        if (antiBots == null)
            antiBots = Init.getInstance().modManager.getModuleByClass(AntiBots.class);
        if (finalEntity != null) {
            if (!isValidTarget(finalEntity)) {
                mineplexProof.remove(finalEntity);
                possibleTargets.remove(finalEntity);
                finalEntity = null;
            }
        }

            if (attackMode.getSelectedCombo().equalsIgnoreCase("Single")) {
                if (finalEntity == null) {
                    finalEntity = getClosest();
                }
            }

            if (attackMode.getSelectedCombo().equalsIgnoreCase("Switch")) {
                finalEntity = getClosest();
            }
    }

    public void getPossibleTargets() {
        if (antiBots == null)
            antiBots = Init.getInstance().modManager.getModuleByClass(AntiBots.class);

        for (Entity e : mc.theWorld.loadedEntityList) {
            if (isValidTarget(e) && e.onGround && !mineplexProof.contains(e)) {
                mineplexProof.add(e);
            }

            if (isValidTarget(e) && !possibleTargets.contains(e)) {
                if (!antiBots.mineplex.isState() || !antiBots.getState() || mineplexProof.contains(e)) {
                    possibleTargets.add(e);
                }
            }
        }
    }

    private Entity getClosest() {
        double range = this.range.getCurrent() + preAim.getCurrent();

        Entity entity = null;

        if (!possibleTargets.isEmpty()) {

            Iterator list = possibleTargets.iterator();

            while (list.hasNext()) {

                Entity target = (Entity) list.next();

                if (entity == null) {
                    entity = target;
                    range = mc.thePlayer.getDistanceToEntity(target);
                }

                if (range > mc.thePlayer.getDistanceToEntity(target)) {
                    entity = target;
                    range = mc.thePlayer.getDistanceToEntity(target);
                }
            }
        }

        return entity;
    }

    public boolean isValidTarget(Entity e) {
        if (antiBots == null)
            antiBots = Init.getInstance().modManager.getModuleByClass(AntiBots.class);
        if (e == null)
            return false;
        if (mc.thePlayer.getDistanceToEntity(e) > range.getCurrent() + ((!preAim.isState()) ? 0 : preAim.getCurrent()))
            return false;
        if (!mc.thePlayer.canEntityBeSeen(e) && !throughWalls.isState())
            return false;
        if (e == mc.thePlayer)
            return false;
        if (checkTabList.isState() && !isInTabList(e))
            return false;
        if (e.isInvisible())
            return false;
        if (!(e instanceof EntityLivingBase))
            return false;
        if (antiBots.getState() && antiBots.timolia.isState() && !antiBots.timoliaProofed.contains(e))
            return false;
        if (!antiBots.getState() || !antiBots.attackDead.isState()) {
            if (e.isDead)
                return false;
        }
        if (Init.getInstance().modManager.getModuleByClass(Teams.class).getState() && (e.getDisplayName().getFormattedText().startsWith("§" + mc.thePlayer.getDisplayName().getFormattedText().charAt(1)) || e.getName().equalsIgnoreCase("")))
            return false;
        if (Init.getInstance().friendSystem.isFriend(e) && !Init.getInstance().modManager.getModuleByClass(NoFriends.class).getState())
            return false;
        return true;
    }

    public boolean isInTabList(Entity entity) {
        if (mc.isSingleplayer()) {
            return false;
        }
        if (entity instanceof EntityPlayer) {
            for (Object o : mc.getNetHandler().getPlayerInfoMap()) {
                NetworkPlayerInfo playerInfo = (NetworkPlayerInfo) o;
                if (playerInfo.getGameProfile().getName().equalsIgnoreCase(entity.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void onEnable() {
        possibleTargets.clear();
        mineplexProof.clear();
        finalEntity = null;
        yaw = mc.thePlayer.rotationYaw;
        pitch = mc.thePlayer.rotationPitch;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    private void drawSpiral(Entity entity, float partialTicks, double rad) {
        double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks - (mc.getRenderManager()).viewerPosX;
        double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks - (mc.getRenderManager()).viewerPosY;
        double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks - (mc.getRenderManager()).viewerPosZ;


        GL11.glPushMatrix();

        double xMid = (double) (x);
        double yMid = (double) (mc.thePlayer.posY + 1 / 2 + 1);

        GL11.glTranslated(xMid, yMid, z);
        GL11.glRotatef(animation, 0, (float) yMid, 0);
        GL11.glTranslated(-xMid, -yMid, -z);

        GL11.glDisable(3553);
        startSmooth();
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glLineWidth(2);
        GL11.glBegin(3);


        double pix2 = 6.283185307179586D;
        float yadd = 0;


        final float[] counter = {0};
        final float[] counter2 = {0};
        final float[] counter3 = {0};

        for (int i = 0; i <= 60; i++) {


            GL11.glColor4f((float) (getRainbow(2500, -60 * counter[0]) >> 16 & 255) / 255.0F, (float) (getRainbow(2500, -60 * counter[0]) >> 8 & 255) / 255.0F, (float) (getRainbow(2500, -60 * counter[0]) & 255) / 255.0F, 1);
            GL11.glVertex3d(x + rad * Math.cos(i * 6.283185307179586D / 45.0D), y + yadd, z + rad * -Math.sin(i * 6.283185307179586D / 45.0D));


            yadd += 0.035F;
            counter[0] += 0.25F;
        }

        GL11.glEnd();
        GL11.glDepthMask(true);
        GL11.glEnable(2929);
        endSmooth();
        GL11.glEnable(3553);
        GL11.glPopMatrix();

        GL11.glPushMatrix();
        GL11.glTranslated(xMid, yMid, z);
        GL11.glRotatef(animation, 0, (float) yMid, 0);
        GL11.glTranslated(-xMid, -yMid, -z);
        GL11.glDisable(3553);
        startSmooth();
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glLineWidth(2);
        GL11.glBegin(3);

        float yadd2 = 0;


        for (int i = 0; i <= 60; i++) {
            GL11.glColor4f((float) (getRainbow(2500, -60 * counter2[0]) >> 16 & 255) / 255.0F, (float) (getRainbow(2500, -60 * counter2[0]) >> 8 & 255) / 255.0F, (float) (getRainbow(2500, -60 * counter2[0]) & 255) / 255.0F, 1);
            GL11.glVertex3d(x + rad * Math.cos(i * 6.283185307179586D / 45.0D), y + yadd2 + 0.1, z + rad * -Math.sin(i * 6.283185307179586D / 45.0D));
            yadd2 += 0.035F;
            counter2[0] += 0.25F;
        }

        GL11.glEnd();
        GL11.glDepthMask(true);
        GL11.glEnable(2929);
        endSmooth();
        GL11.glEnable(3553);
        GL11.glPopMatrix();

        GL11.glPushMatrix();
        GL11.glTranslated(xMid, yMid, z);
        GL11.glRotatef(animation, 0, (float) yMid, 0);
        GL11.glTranslated(-xMid, -yMid, -z);
        GL11.glDisable(3553);
        startSmooth();
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glShadeModel(GL11.GL_SMOOTH);

        GL11.glLineWidth(2);
        GL11.glBegin(3);

        float yadd3 = 0;


        for (int i = 0; i <= 60; i++) {
            GL11.glColor4f((float) (getRainbow(2500, -60 * counter3[0]) >> 16 & 255) / 255.0F, (float) (getRainbow(2500, -60 * counter3[0]) >> 8 & 255) / 255.0F, (float) (getRainbow(2500, -60 * counter3[0]) & 255) / 255.0F, 1);
            GL11.glVertex3d(x + rad * Math.cos(i * 6.283185307179586D / 45.0D), y + yadd3 + 0.2, z + rad * -Math.sin(i * 6.283185307179586D / 45.0D));
            yadd3 += 0.035F;
            counter3[0] += 0.25F;
        }

        GL11.glEnd();
        GL11.glDepthMask(true);
        GL11.glEnable(2929);
        endSmooth();
        GL11.glEnable(3553);
        GL11.glPopMatrix();

        // OTHER
        GL11.glPushMatrix();
        GL11.glTranslated(xMid, yMid, z);
        GL11.glRotatef(animation, 0, (float) yMid, 0);
        GL11.glTranslated(-xMid, -yMid, -z);
        GL11.glDisable(3553);
        startSmooth();
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glShadeModel(GL11.GL_SMOOTH);

        GL11.glLineWidth(2);
        GL11.glBegin(3);


        final float[] counter4 = {0};
        final float[] counter5 = {0};
        final float[] counter6 = {0};

        float yadd4 = 0;


        for (int i = 0; i <= 60; i++) {
            GL11.glColor4f((float) (getRainbow(2500, -60 * counter4[0]) >> 16 & 255) / 255.0F, (float) (getRainbow(2500, -60 * counter4[0]) >> 8 & 255) / 255.0F, (float) (getRainbow(2500, -60 * counter4[0]) & 255) / 255.0F, 1);
            GL11.glVertex3d(x + rad * -Math.cos(i * 6.283185307179586D / 45.0D), y + yadd4, z + rad * Math.sin(i * 6.283185307179586D / 45.0D));
            yadd4 += 0.035F;
            counter4[0] += 0.25F;

        }
        GL11.glEnd();
        GL11.glDepthMask(true);
        GL11.glEnable(2929);
        endSmooth();
        GL11.glEnable(3553);
        GL11.glPopMatrix();

        GL11.glPushMatrix();
        GL11.glTranslated(xMid, yMid, z);
        GL11.glRotatef(animation, 0, (float) yMid, 0);
        GL11.glTranslated(-xMid, -yMid, -z);
        GL11.glDisable(3553);
        startSmooth();
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glShadeModel(GL11.GL_SMOOTH);

        GL11.glLineWidth(2);
        GL11.glBegin(3);


        float yadd5 = 0;


        for (int i = 0; i <= 60; i++) {
            GL11.glColor4f((float) (getRainbow(2500, -60 * counter5[0]) >> 16 & 255) / 255.0F, (float) (getRainbow(2500, -60 * counter5[0]) >> 8 & 255) / 255.0F, (float) (getRainbow(2500, -60 * counter5[0]) & 255) / 255.0F, 1);
            GL11.glVertex3d(x + rad * -Math.cos(i * 6.283185307179586D / 45.0D), y + yadd5 + 0.1, z + rad * Math.sin(i * 6.283185307179586D / 45.0D));
            yadd5 += 0.035F;
            counter5[0] += 0.25F;

        }
        GL11.glEnd();
        GL11.glDepthMask(true);
        GL11.glEnable(2929);
        endSmooth();
        GL11.glEnable(3553);
        GL11.glPopMatrix();


        GL11.glPushMatrix();
        GL11.glTranslated(xMid, yMid, z);
        GL11.glRotatef(animation, 0, (float) yMid, 0);
        GL11.glTranslated(-xMid, -yMid, -z);
        GL11.glDisable(3553);
        startSmooth();
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glShadeModel(GL11.GL_SMOOTH);

        GL11.glLineWidth(2);
        GL11.glBegin(3);


        float yadd6 = 0;


        for (int i = 0; i <= 60; i++) {
            GL11.glColor4f((float) (getRainbow(2500, -60 * counter6[0]) >> 16 & 255) / 255.0F, (float) (getRainbow(2500, -60 * counter6[0]) >> 8 & 255) / 255.0F, (float) (getRainbow(2500, -60 * counter6[0]) & 255) / 255.0F, 1);
            GL11.glVertex3d(x + rad * -Math.cos(i * 6.283185307179586D / 45.0D), y + yadd6 + 0.2, z + rad * Math.sin(i * 6.283185307179586D / 45.0D));
            yadd6 += 0.035F;
            counter6[0] += 0.25F;

        }
        GL11.glEnd();
        GL11.glDepthMask(true);
        GL11.glEnable(2929);
        endSmooth();
        GL11.glEnable(3553);
        GL11.glPopMatrix();

        animation += 0.25 * RenderUtils.deltaTime;
    }

    private void drawCircle(Entity entity, float partialTicks, double rad) {

        double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks - (mc.getRenderManager()).viewerPosX;
        double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks - (mc.getRenderManager()).viewerPosY;
        double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks - (mc.getRenderManager()).viewerPosZ;


        GL11.glPushMatrix();
        GlStateManager.enableBlend();
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GlStateManager.disableDepth();
        GlStateManager.disableTexture2D();
        GlStateManager.disableAlpha();
        GL11.glLineWidth(1.5F);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glBegin(GL11.GL_TRIANGLE_STRIP);

        final float[] counter = {0};


        for (int i = 0; i <= 360; i++) {
            GL11.glColor4f(Init.getInstance().CLIENT_COLOR.getRed() / 255F, Init.getInstance().CLIENT_COLOR.getGreen() / 255F, Init.getInstance().CLIENT_COLOR.getBlue() / 255F, 0.05F);
            float yCircle = (float) (y + animationCircle);
            GL11.glVertex3d(x + rad * Math.cos(i * 6.283185307179586D / 45.0D), yCircle, z + rad * -Math.sin(i * 6.283185307179586D / 45.0D));
            GL11.glColor4f(1, 1, 1, 0.02f);
            float reverse = (float) (reverseCircle ? -0.15 : 0.15);
            GL11.glVertex3d(x + rad * Math.cos(i * 6.283185307179586D / 45.0D), yCircle + reverse, z + rad * -Math.sin(i * 6.283185307179586D / 45.0D));
            counter[0] += 0.25F;
        }

        GL11.glEnd();
        GL11.glBegin(GL11.GL_LINE_LOOP);
        {
            for (int j = 0; j < 360; j++) {
                GL11.glColor4f(1, 1, 1, 1);
                GL11.glVertex3d(x + rad * Math.cos(j * 6.283185307179586D / 45.0D), y + animationCircle, z + rad * -Math.sin(j * 6.283185307179586D / 45.0D));
            }
        }

        GL11.glEnd();
        GlStateManager.enableAlpha();
        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GlStateManager.enableTexture2D();
        GlStateManager.enableDepth();
        GlStateManager.disableBlend();
        GlStateManager.resetColor();
        GL11.glPopMatrix();

        if (animationCircle > 2)
            reverseCircle = true;

        if (animationCircle < 0.01)
            reverseCircle = false;

        if (reverseCircle) {
            animationCircle -= 0.0025 * RenderUtils.deltaTime;
        } else {
            animationCircle += 0.0025 * RenderUtils.deltaTime;
        }


    }

    public void startSmooth() {
        GL11.glEnable(2848);
        GL11.glEnable(2881);
        GL11.glEnable(2832);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
        GL11.glHint(3153, 4354);
    }

    public void endSmooth() {
        GL11.glDisable(2848);
        GL11.glDisable(2881);
        GL11.glEnable(2832);
    }


}
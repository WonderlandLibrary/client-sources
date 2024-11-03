package net.silentclient.client.emotes;

import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.silentclient.client.Client;
import net.silentclient.client.emotes.animation.Animation;
import net.silentclient.client.emotes.animation.AnimationMesh;
import net.silentclient.client.emotes.animation.AnimationMeshConfig;
import net.silentclient.client.emotes.animation.model.ActionPlayback;
import net.silentclient.client.emotes.animation.model.AnimatorConfig;
import net.silentclient.client.emotes.animation.model.AnimatorHeldItemConfig;
import net.silentclient.client.emotes.bobj.BOBJAction;
import net.silentclient.client.emotes.bobj.BOBJArmature;
import net.silentclient.client.emotes.bobj.BOBJBone;
import net.silentclient.client.emotes.emoticons.Emote;
import net.silentclient.client.emotes.socket.EmoteSocket;
import net.silentclient.client.hooks.NameTagRenderingHooks;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AnimatorController {
    public static final FloatBuffer matrix = BufferUtils.createFloatBuffer(16);
    public static final float[] buffer = new float[16];
    private static final Map<String, ResourceLocation> ARMOR_TEXTURE_RES_MAP = Maps.newHashMap();
    private static final ResourceLocation ENCHANTED_ITEM_GLINT_RES = new ResourceLocation("textures/misc/enchanted_item_glint.png");
    public Animation animation;
    public ActionPlayback emote;
    public Emote entry;
    public List<Runnable> runnables = new ArrayList<>();
    public AnimatorConfig userConfig;
    public boolean resetThirdView = false;
    public ItemStack itemSlot = null;
    public float itemSlotScale = 0.0F;
    public boolean right = true;
    public RenderPlayer renderPlayer;
    private final Minecraft mc;
    private double prevX;
    private double prevY;
    private double prevZ;
    private int counter = 0;
    private boolean slimCheck;
    private final Vector4f result = new Vector4f();
    private final Matrix4f rotate = new Matrix4f();
    private final EmoteAccessor accessor;

    public AnimatorController(Animation animationx, AnimatorConfig animatorconfig) {
        this.animation = animationx;
        this.userConfig = new AnimatorConfig();
        this.userConfig.copy(animatorconfig);
        this.mc = Minecraft.getMinecraft();
        this.accessor = new EmoteAccessor(this);
    }

    public Vector4f calcPosition(EntityLivingBase entitylivingbase, BOBJBone bobjbone, float f, float f1, float f2, float f3) {
        float f4 = (float) Math.PI;
        this.result.set(f, f1, f2, 1.0F);
        bobjbone.mat.transform(this.result);
        this.rotate.identity();
        this.rotate.rotateY((180.0F - entitylivingbase.renderYawOffset + 180.0F) / 180.0F * (float) Math.PI);
        this.rotate.transform(this.result);
        this.result.mul(this.userConfig.scale);
        float f5 = (float) (entitylivingbase.lastTickPosX + (entitylivingbase.posX - entitylivingbase.lastTickPosX) * (double) f3);
        float f6 = (float) (entitylivingbase.lastTickPosY + (entitylivingbase.posY - entitylivingbase.lastTickPosY) * (double) f3);
        float f7 = (float) (entitylivingbase.lastTickPosZ + (entitylivingbase.posZ - entitylivingbase.lastTickPosZ) * (double) f3);
        this.result.x += f5;
        this.result.y += f6;
        this.result.z += f7;
        return this.result;
    }

    public boolean renderHook(RenderPlayer render, AbstractClientPlayer abstractclientplayer, double d0, double d1, double d2, float f1, boolean flag) {
        this.renderPlayer = render;
        if (this.isEmoting()) {
            AnimationMeshConfig body = this.userConfig.meshes.get("body");
            if (body != null) {
                ResourceLocation rs = abstractclientplayer.getLocationSkin();
                AnimationMeshConfig headWear = this.userConfig.meshes.get("headwear");
                AnimationMeshConfig bodyWear = this.userConfig.meshes.get("bodywear");
                AnimationMeshConfig leftArmWear = this.userConfig.meshes.get("left_armwear");
                AnimationMeshConfig leftLegWear = this.userConfig.meshes.get("left_legwear");
                AnimationMeshConfig rightArmWear = this.userConfig.meshes.get("right_armwear");
                AnimationMeshConfig rightLegWear = this.userConfig.meshes.get("right_legwear");
                if (body.texture == null
                        || !rs.getResourceDomain().equals(body.texture.getResourceDomain())
                        || !rs.getResourcePath().equals(body.texture.getResourcePath())) {
                    body.texture = rs;
                    headWear.texture = body.texture;
                    bodyWear.texture = body.texture;
                    leftArmWear.texture = body.texture;
                    leftLegWear.texture = body.texture;
                    rightArmWear.texture = body.texture;
                    rightLegWear.texture = body.texture;
                }

                headWear.visible = abstractclientplayer.isWearing(EnumPlayerModelParts.HAT);
                bodyWear.visible = abstractclientplayer.isWearing(EnumPlayerModelParts.JACKET);
                leftArmWear.visible = abstractclientplayer.isWearing(EnumPlayerModelParts.LEFT_SLEEVE);
                leftLegWear.visible = abstractclientplayer.isWearing(EnumPlayerModelParts.LEFT_PANTS_LEG);
                rightArmWear.visible = abstractclientplayer.isWearing(EnumPlayerModelParts.RIGHT_SLEEVE);
                rightLegWear.visible = abstractclientplayer.isWearing(EnumPlayerModelParts.RIGHT_PANTS_LEG);
                if (!abstractclientplayer.isInvisible()) {
                    rightLegWear.alpha = 1.0F;
                    body.visible = true;
                } else if (!abstractclientplayer.isInvisibleToPlayer(Minecraft.getMinecraft().thePlayer)) {
                    rightLegWear.alpha = 0.15F;
                    body.visible = true;
                } else {
                    rightLegWear.visible = false;
                }

                if (!this.slimCheck && !abstractclientplayer.getSkinType().equals("default")) {
                    this.animation = PlayerModelManager.get().alex;
                    this.userConfig.copy(PlayerModelManager.get().alexConfig);
                    if (this.entry != null) {
                        this.entry.startAnimation(this.accessor);
                    }

                    this.slimCheck = true;
                }
            }

            BOBJArmature bobjarmature = this.animation.data.armatures.get("Armature");
            this.render(abstractclientplayer, bobjarmature, d0, d1, d2, f1);
            if (flag) {
                this.renderNameTag(abstractclientplayer, d0, d1, d2, bobjarmature);
            }
        }

        return this.isEmoting();
    }

    public void renderNameTag(AbstractClientPlayer abstractclientplayer, double d0, double d1, double d2, BOBJArmature var8) {
        double d4 = 64.0;
        String s = abstractclientplayer.getDisplayName().getFormattedText();
        NameTagRenderingHooks.renderNametag(abstractclientplayer, s, d0, d1, d2, (int) d4, true);
    }

    public void render(AbstractClientPlayer player, BOBJArmature armature, double d0, double d1, double d2, float f) {
        if (this.animation != null && this.animation.meshes.size() > 0) {
            float f1 = player.prevRenderYawOffset + (player.renderYawOffset - player.prevRenderYawOffset) * f;
            float f2 = this.userConfig.scale;
            GL11.glPushMatrix();
            GL11.glTranslated(d0, d1, d2);
            GL11.glScalef(f2, f2, f2);
            GL11.glRotatef(180.0F - f1 - 180.0F, 0.0F, 1.0F, 0.0F);
            this.renderAnimation(player, armature, f1, f);
            if (this.entry != null && !Minecraft.getMinecraft().isGamePaused()) {
                int i = (int) this.emote.getTick(0.0F);
                this.entry.progressAnimation(this.accessor, armature, i, f);
            }

            GL11.glPopMatrix();
        }
    }

    private void renderAnimation(AbstractClientPlayer abstractclientplayer, BOBJArmature bobjarmature, float f, float f1) {
        for (BOBJArmature bobjarmature1 : this.animation.data.armatures.values()) {
            if (bobjarmature1.enabled) {
                BOBJBone bobjbone1 = bobjarmature1.bones.get(this.userConfig.head);
                if (bobjbone1 != null) {
                    float f3 = abstractclientplayer.prevRotationYawHead + (abstractclientplayer.rotationYawHead - abstractclientplayer.prevRotationYawHead) * f1;
                    float f2 = abstractclientplayer.prevRotationPitch + (abstractclientplayer.rotationPitch - abstractclientplayer.prevRotationPitch) * f1;
                    f3 = (f - f3) / 180.0F * (float) Math.PI;
                    f2 = f2 / 180.0F * (float) Math.PI;
                    bobjbone1.rotateX = f2;
                    bobjbone1.rotateY = f3;
                }

                if (this.emote != null) {
                    this.emote.apply(bobjarmature1, f1);
                }

                for (BOBJBone bone : bobjarmature1.orderedBones) {
                    bobjarmature1.matrices[bone.index] = bone.compute();
                    bone.reset();
                }
            }
        }

        for (AnimationMesh animationmesh : this.animation.meshes) {
            if (animationmesh.armature.enabled) {
                animationmesh.update();
            }
        }

        GL11.glEnable(32826);
        this.renderMeshes(abstractclientplayer, f1);
        GL11.glDisable(32826);
        this.renderItems(abstractclientplayer, bobjarmature);
        this.renderHead(abstractclientplayer, bobjarmature.bones.get(this.userConfig.head));
        this.renderCosmetic(this.renderPlayer, abstractclientplayer, bobjarmature);
    }

    public void renderCosmetic(RenderPlayer renderPlayer, Entity entityIn, BOBJArmature bobjarmature) {
        if (entityIn instanceof EntityPlayer) {
            EntityPlayer var4 = (EntityPlayer) entityIn;
        }
    }

    private void renderMeshes(EntityLivingBase entitylivingbase, float f) {
        AnimationMeshConfig animationmeshconfig = this.userConfig.meshes.get("armor_helmet");
        AnimationMeshConfig animationmeshconfig1 = this.userConfig.meshes.get("armor_chest");
        AnimationMeshConfig animationmeshconfig2 = this.userConfig.meshes.get("armor_leggings");
        AnimationMeshConfig animationmeshconfig3 = this.userConfig.meshes.get("armor_feet");
        if (animationmeshconfig != null) {
            this.updateArmorSlot(animationmeshconfig, entitylivingbase, 4);
        }

        if (animationmeshconfig1 != null) {
            this.updateArmorSlot(animationmeshconfig1, entitylivingbase, 3);
        }

        if (animationmeshconfig2 != null) {
            this.updateArmorSlot(animationmeshconfig2, entitylivingbase, 2);
        }

        if (animationmeshconfig3 != null) {
            this.updateArmorSlot(animationmeshconfig3, entitylivingbase, 1);
        }

        for (AnimationMesh animationmesh : this.animation.meshes) {
            if (animationmesh.armature.enabled) {
                animationmesh.render(this.userConfig.meshes.get(animationmesh.name));
            }
        }

        for (AnimationMesh animationmesh1 : this.animation.meshes) {
            if (animationmesh1.name.startsWith("armor_")) {
                ItemStack itemstack = null;
                String var10 = animationmesh1.name;
                switch (var10) {
                    case "armor_helmet":
                        itemstack = entitylivingbase.getEquipmentInSlot(4);
                        break;
                    case "armor_chest":
                        itemstack = entitylivingbase.getEquipmentInSlot(3);
                        break;
                    case "armor_leggings":
                        itemstack = entitylivingbase.getEquipmentInSlot(2);
                        break;
                    case "armor_feet":
                        itemstack = entitylivingbase.getEquipmentInSlot(1);
                }

                if (itemstack != null && itemstack.getItem() instanceof ItemArmor && itemstack.isItemEnchanted()) {
                    this.renderMeshGlint(animationmesh1, (float) entitylivingbase.ticksExisted + f);
                }
            }
        }
    }

    private void renderMeshGlint(AnimationMesh animationmesh, float f) {
        this.mc.getTextureManager().bindTexture(ENCHANTED_ITEM_GLINT_RES);
        GlStateManager.enableBlend();
        GlStateManager.depthFunc(514);
        GlStateManager.depthMask(false);

        for (int i = 0; i < 2; ++i) {
            float f1 = 0.5F;
            GlStateManager.color(f1, f1, f1, 1.0F);
            GlStateManager.disableLighting();
            GlStateManager.blendFunc(768, 1);
            float f2 = 0.76F;
            GlStateManager.color(0.5F * f2, 0.25F * f2, 0.8F * f2, 1.0F);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            float f3 = 0.33333334F;
            GlStateManager.scale(f3, f3, f3);
            GlStateManager.rotate(30.0F - (float) i * 60.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.translate(0.0F, f * (0.001F + (float) i * 0.003F) * 20.0F, 0.0F);
            GlStateManager.matrixMode(5888);
            animationmesh.render(null);
        }

        GlStateManager.matrixMode(5890);
        GlStateManager.loadIdentity();
        GlStateManager.matrixMode(5888);
        GlStateManager.enableLighting();
        GlStateManager.depthMask(true);
        GlStateManager.depthFunc(515);
        GlStateManager.disableBlend();
    }

    private void updateArmorSlot(AnimationMeshConfig animationmeshconfig, EntityLivingBase entitylivingbase, int i) {
        ItemStack itemstack = entitylivingbase.getEquipmentInSlot(i);
        if (itemstack != null && itemstack.getItem() instanceof ItemArmor) {
            ItemArmor itemarmor = (ItemArmor) itemstack.getItem();
            animationmeshconfig.visible = true;
            animationmeshconfig.texture = this.getArmorResource(itemarmor, this.isLegSlot(i), null);
            animationmeshconfig.color = itemarmor.getColor(itemstack);
        } else {
            animationmeshconfig.visible = false;
            animationmeshconfig.color = -1;
        }
    }

    private boolean isLegSlot(int i) {
        return i == 2;
    }

    private ResourceLocation getArmorResource(ItemArmor itemarmor, boolean flag, String s) {
        String s1 = String.format(
                "textures/models/armor/%s_layer_%d%s.png", itemarmor.getArmorMaterial().getName(), flag ? 2 : 1, s == null ? "" : String.format("_%s", s)
        );
        ResourceLocation aj = ARMOR_TEXTURE_RES_MAP.get(s1);
        if (aj == null) {
            aj = new ResourceLocation(s1);
            ARMOR_TEXTURE_RES_MAP.put(s1, aj);
        }

        return aj;
    }

    private void renderHead(EntityLivingBase entitylivingbase, BOBJBone bobjbone) {
        ItemStack itemstack = entitylivingbase.getEquipmentInSlot(4);
        if (itemstack != null && bobjbone != null) {
            Item item = itemstack.getItem();
            if (!(item instanceof ItemArmor) || ((ItemArmor) item).armorType != 4) {
                GlStateManager.pushMatrix();
                this.setupMatrix(bobjbone);
                GlStateManager.translate(0.0F, 0.25F, 0.0F);
                if (!(item instanceof ItemSkull)) {
                    GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
                } else {
                    GlStateManager.translate(0.0F, 0.05F, 0.0F);
                }

                GlStateManager.scale(0.625F, 0.625F, 0.625F);
                this.mc.getItemRenderer().renderItem(entitylivingbase, itemstack, ItemCameraTransforms.TransformType.HEAD);
                GlStateManager.popMatrix();
            }
        }
    }

    private void renderItems(EntityLivingBase entitylivingbase, BOBJArmature bobjarmature) {
        if (this.userConfig.renderHeldItems) {
            float f = this.userConfig.scaleItems;
            ItemStack itemstack = entitylivingbase.getHeldItem();
            if (this.itemSlot != null) {
                if (this.itemSlotScale > 0.0F) {
                    for (AnimatorHeldItemConfig animatorhelditemconfig : this.right ? this.userConfig.rightHands.values() : this.userConfig.leftHands.values()) {
                        this.renderItem(
                                entitylivingbase,
                                this.itemSlot,
                                bobjarmature,
                                animatorhelditemconfig,
                                animatorhelditemconfig.boneName,
                                ItemCameraTransforms.TransformType.THIRD_PERSON,
                                f * this.itemSlotScale,
                                this.right
                        );
                    }
                }
            } else if (itemstack != null && this.userConfig.rightHands != null) {
                for (AnimatorHeldItemConfig animatorhelditemconfig1 : this.userConfig.rightHands.values()) {
                    this.renderItem(
                            entitylivingbase,
                            itemstack,
                            bobjarmature,
                            animatorhelditemconfig1,
                            animatorhelditemconfig1.boneName,
                            ItemCameraTransforms.TransformType.THIRD_PERSON,
                            f,
                            true
                    );
                }
            }
        }
    }

    private void renderItem(
            EntityLivingBase entitylivingbase,
            ItemStack itemstack,
            BOBJArmature bobjarmature,
            AnimatorHeldItemConfig animatorhelditemconfig,
            String s,
            ItemCameraTransforms.TransformType itemcameratransforms$transformtype,
            float f,
            boolean flag
    ) {
        Item item = itemstack.getItem();
        BOBJBone bobjbone = bobjarmature.bones.get(s);
        if (bobjbone != null) {
            GlStateManager.pushMatrix();
            this.setupMatrix(bobjbone);
            if (!flag) {
                GlStateManager.translate(0.0625F * (2.0F - 3.0F * (1.0F - this.itemSlotScale)), -0.125F, 0.1875F);
                GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
                GlStateManager.rotate(-45.0F, 0.0F, 1.0F, 0.0F);
            } else {
                GlStateManager.translate(0.0F, -0.0625F, 0.0625F);
            }

            GlStateManager.scale(f * animatorhelditemconfig.scaleX, f * animatorhelditemconfig.scaleY, f * animatorhelditemconfig.scaleZ);
            if (item instanceof ItemBlock && Block.getBlockFromItem(item).getRenderType() == 2) {
                GlStateManager.translate(0.0F, 0.1875F, -0.3125F);
                GlStateManager.rotate(20.0F, 1.0F, 0.0F, 0.0F);
                GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
                GlStateManager.scale(-0.375F, -0.375F, 0.375F);
            }

            this.mc.getItemRenderer().renderItem(entitylivingbase, itemstack, itemcameratransforms$transformtype);
            GlStateManager.popMatrix();
        }
    }

    public void update(EntityLivingBase entitylivingbase) {
        for (Runnable runnable : this.runnables) {
            runnable.run();
        }

        this.runnables.clear();
        if (this.emote != null && this.counter <= 0) {
            this.emote.update();
            double d2 = entitylivingbase.posX - this.prevX;
            double d0 = entitylivingbase.posY - this.prevY;
            double d1 = entitylivingbase.posZ - this.prevZ;
            boolean flag = d2 * d2 + d0 * d0 + d1 * d1 > 0.0025000000000000005;
            boolean flag1 = entitylivingbase.isSwingInProgress && entitylivingbase.swingProgress == 0.0F && !entitylivingbase.isPlayerSleeping();
            boolean flag2 = entitylivingbase.isSneaking() || flag || flag1;
            if (this.emote.isFinished() || flag2) {
                this.accessor.entity = entitylivingbase;
                boolean repeat = true;
                if(Client.getInstance().getAccount().getUsername().equalsIgnoreCase(entitylivingbase.getName())) {
                    if ((flag && !Client.getInstance().getSettingsManager().getSettingByClass(EmotesMod.class, "Enable Running in Emotes").getValBoolean()) || (flag1 || entitylivingbase.isSneaking())) {
                        this.resetEmote(0);
                        EmoteSocket.get().endEmote();
                        repeat = false;
                    }
                    if (!flag2) {
                        if(!Client.getInstance().getSettingsManager().getSettingByClass(EmotesMod.class, "Infinite Emotes").getValBoolean()) {
                            this.resetEmote();
                            EmoteSocket.get().endEmote();
                            repeat = false;
                        }
                    }
                }

                if(repeat && this.emote.isFinished()) {
                    this.setEmote(entitylivingbase, this.entry, true);
                }

                if((flag1 || entitylivingbase.isSneaking()) && repeat) {
                    this.resetEmote(0);
                }
            }
        }

        if (this.counter >= 0) {
            if (this.resetThirdView && this.emote == null && this.counter == 0) {
                this.resetThirdView = false;
                Minecraft.getMinecraft().gameSettings.thirdPersonView = 0;
            }

            --this.counter;
        }

        this.prevX = entitylivingbase.posX;
        this.prevY = entitylivingbase.posY;
        this.prevZ = entitylivingbase.posZ;
    }

    public void setupMatrix(BOBJBone bobjbone) {
        Matrix4f matrix4f = bobjbone.mat;
        ((Buffer) matrix).clear();
        matrix.put(matrix4f.get(buffer));
        ((Buffer) matrix).flip();
        GL11.glMultMatrix(matrix);
    }

    public void resetEmote() {
        this.resetEmote(5);
    }

    public void resetEmote(int i) {
        if (this.entry != null) {
            this.entry.stopAnimation(this.accessor);
        }

        this.entry = null;
        this.emote = null;
        this.counter = i;
    }

    public void setEmote(EntityLivingBase entitylivingbase, String s) {
        this.setEmote(entitylivingbase, PlayerModelManager.get().getEmote(s), false);
    }

    public void setEmote(EntityLivingBase entitylivingbase, Emote emotex) {
        this.setEmote(entitylivingbase, emotex, false);
    }

    public void setEmote(EntityLivingBase entitylivingbase, Emote emotex, boolean ignorePerspective) {
        if (emotex != null) {
            BOBJAction bobjaction = this.animation.data.actions.get(emotex.getActionName());
            if (bobjaction != null && emotex != null) {
                ActionPlayback actionplayback = new ActionPlayback(bobjaction);
                actionplayback.repeat = emotex.looping;
                if (emotex.looping > 1) {
                    actionplayback.looping = true;
                }

                if (!this.isEmoting()) {
                    this.counter = 5;
                }

                this.accessor.entity = entitylivingbase;
                if (this.entry != null) {
                    this.entry.stopAnimation(this.accessor);
                }

                this.emote = actionplayback;
                this.entry = emotex;
                this.entry.startAnimation(this.accessor);
                if (!ignorePerspective && this.mc.gameSettings.thirdPersonView != EmotesMod.getEmotePerspective() && entitylivingbase == this.mc.thePlayer) {
                    this.mc.gameSettings.thirdPersonView = EmotesMod.getEmotePerspective();
                    this.resetThirdView = true;
                }
            }
        }
    }

    public boolean isEmoting() {
        return this.emote != null && this.counter <= 0;
    }
}

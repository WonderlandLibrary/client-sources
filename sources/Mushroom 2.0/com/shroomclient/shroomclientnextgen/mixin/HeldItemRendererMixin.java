package com.shroomclient.shroomclientnextgen.mixin;

import com.shroomclient.shroomclientnextgen.modules.ModuleManager;
import com.shroomclient.shroomclientnextgen.modules.impl.combat.KillAura3;
import com.shroomclient.shroomclientnextgen.modules.impl.render.Animations;
import com.shroomclient.shroomclientnextgen.modules.impl.render.Zoom;
import com.shroomclient.shroomclientnextgen.util.C;
import java.util.Objects;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = HeldItemRenderer.class)
public abstract class HeldItemRendererMixin {

    @Shadow
    private final MinecraftClient client;

    @Mutable
    @Final
    @Shadow
    private final EntityRenderDispatcher entityRenderDispatcher;

    @Shadow
    private ItemStack offHand;

    public HeldItemRendererMixin(
        MinecraftClient client,
        EntityRenderDispatcher entityRenderDispatcher,
        ItemRenderer itemRenderer
    ) {
        this.offHand = ItemStack.EMPTY;
        this.entityRenderDispatcher = entityRenderDispatcher;
        this.client = client;
    }

    @Shadow
    protected abstract void renderArmHoldingItem(
        MatrixStack matrices,
        VertexConsumerProvider vertexConsumers,
        int light,
        float equipProgress,
        float swingProgress,
        Arm arm
    );

    @Shadow
    protected abstract void renderMapInBothHands(
        MatrixStack matrices,
        VertexConsumerProvider vertexConsumers,
        int light,
        float pitch,
        float equipProgress,
        float swingProgress
    );

    @Shadow
    protected abstract void renderMapInOneHand(
        MatrixStack matrices,
        VertexConsumerProvider vertexConsumers,
        int light,
        float equipProgress,
        Arm arm,
        float swingProgress,
        ItemStack stack
    );

    @Shadow
    protected abstract void applyEquipOffset(
        MatrixStack matrices,
        Arm arm,
        float equipProgress
    );

    @Shadow
    protected abstract void applySwingOffset(
        MatrixStack matrices,
        Arm arm,
        float swingProgress
    );

    @Shadow
    public abstract void renderItem(
        LivingEntity entity,
        ItemStack stack,
        ModelTransformationMode renderMode,
        boolean leftHanded,
        MatrixStack matrices,
        VertexConsumerProvider vertexConsumers,
        int light
    );

    @Shadow
    protected abstract void applyEatOrDrinkTransformation(
        MatrixStack matrices,
        float tickDelta,
        Arm arm,
        ItemStack stack
    );

    @Shadow
    protected abstract void applyBrushTransformation(
        MatrixStack matrices,
        float tickDelta,
        Arm arm,
        ItemStack stack,
        float equipProgress
    );

    @Inject(
        method = "renderFirstPersonItem",
        at = @At("HEAD"),
        cancellable = true
    )
    public void renderFirstPersonItem(
        AbstractClientPlayerEntity player,
        float tickDelta,
        float pitch,
        Hand hand,
        float swingProgress,
        ItemStack item,
        float equipProgress,
        MatrixStack matrices,
        VertexConsumerProvider vertexConsumers,
        int light,
        CallbackInfo ci
    ) {
        if (ModuleManager.isEnabled(Animations.class)) {
            ci.cancel();
            if (player == null) return;
        } else return;

        matrices.push();

        if (!player.isUsingSpyglass()) {
            boolean bl = hand == Hand.MAIN_HAND;
            Arm arm = bl
                ? player.getMainArm()
                : player.getMainArm().getOpposite();

            if (ModuleManager.isEnabled(Zoom.class)) {
                matrices.pop();
                return;
            }

            if (
                item.getItem() instanceof ShieldItem &&
                !Animations.showShield &&
                ModuleManager.isEnabled(Animations.class)
            ) {
                matrices.pop();
                return;
            }

            boolean doBlockAnim =
                (C.p().isUsingItem() || KillAura3.isClientBlocking) &&
                item.getItem() instanceof SwordItem &&
                ModuleManager.isEnabled(Animations.class);
            if (ModuleManager.isEnabled(Animations.class)) {
                if (
                    !doBlockAnim &&
                    !Objects.equals(item.getItem().toString(), "air")
                ) {
                    matrices.translate(
                        Animations.posX,
                        Animations.posY,
                        Animations.posZ
                    );
                    matrices.scale(
                        Animations.itemSize,
                        Animations.itemSize,
                        Animations.itemSize
                    );

                    matrices.multiply(
                        RotationAxis.POSITIVE_X.rotationDegrees(Animations.rotX)
                    );
                    matrices.multiply(
                        RotationAxis.POSITIVE_Y.rotationDegrees(Animations.rotY)
                    );
                    matrices.multiply(
                        RotationAxis.POSITIVE_Z.rotationDegrees(Animations.rotZ)
                    );
                }

                if (doBlockAnim) {
                    matrices.translate(
                        Animations.blockingPosX,
                        Animations.blockingPosY,
                        Animations.blockingPosZ
                    );

                    this.applyBlockTrans(matrices, tickDelta, arm, item);
                }
            }

            if (item.isEmpty()) {
                if (bl && !player.isInvisible()) {
                    this.renderArmHoldingItem(
                            matrices,
                            vertexConsumers,
                            light,
                            equipProgress,
                            swingProgress,
                            arm
                        );
                }
            } else if (item.isOf(Items.FILLED_MAP)) {
                if (bl && this.offHand.isEmpty()) {
                    this.renderMapInBothHands(
                            matrices,
                            vertexConsumers,
                            light,
                            pitch,
                            equipProgress,
                            swingProgress
                        );
                } else {
                    this.renderMapInOneHand(
                            matrices,
                            vertexConsumers,
                            light,
                            equipProgress,
                            arm,
                            swingProgress,
                            item
                        );
                }
            } else {
                boolean bl2;
                float f;
                float g;
                float h;
                float j;
                if (item.isOf(Items.CROSSBOW)) {
                    bl2 = CrossbowItem.isCharged(item);
                    boolean bl3 = arm == Arm.RIGHT;
                    int i = bl3 ? 1 : -1;
                    if (
                        player.isUsingItem() &&
                        player.getItemUseTimeLeft() > 0 &&
                        player.getActiveHand() == hand
                    ) {
                        this.applyEquipOffset(matrices, arm, equipProgress);
                        matrices.translate(
                            (float) i * -0.4785682F,
                            -0.094387F,
                            0.05731531F
                        );
                        matrices.multiply(
                            RotationAxis.POSITIVE_X.rotationDegrees(-11.935F)
                        );
                        matrices.multiply(
                            RotationAxis.POSITIVE_Y.rotationDegrees(
                                (float) i * 65.3F
                            )
                        );
                        matrices.multiply(
                            RotationAxis.POSITIVE_Z.rotationDegrees(
                                (float) i * -9.785F
                            )
                        );
                        f = (float) item.getMaxUseTime() -
                        ((float) this.client.player.getItemUseTimeLeft() -
                            tickDelta +
                            1.0F);
                        g = f / (float) CrossbowItem.getPullTime(item);
                        if (g > 1.0F) {
                            g = 1.0F;
                        }

                        if (g > 0.1F) {
                            h = MathHelper.sin((f - 0.1F) * 1.3F);
                            j = g - 0.1F;
                            float k = h * j;
                            matrices.translate(k * 0.0F, k * 0.004F, k * 0.0F);
                        }

                        matrices.translate(g * 0.0F, g * 0.0F, g * 0.04F);
                        matrices.scale(1.0F, 1.0F, 1.0F + g * 0.2F);
                        matrices.multiply(
                            RotationAxis.NEGATIVE_Y.rotationDegrees(
                                (float) i * 45.0F
                            )
                        );
                    } else {
                        f = -0.4F *
                        MathHelper.sin(
                            MathHelper.sqrt(swingProgress) * 3.1415927F
                        );
                        g = 0.2F *
                        MathHelper.sin(
                            MathHelper.sqrt(swingProgress) * 6.2831855F
                        );
                        h = -0.2F * MathHelper.sin(swingProgress * 3.1415927F);
                        matrices.translate((float) i * f, g, h);
                        this.applyEquipOffset(matrices, arm, equipProgress);
                        this.applySwingOffset(matrices, arm, swingProgress);
                        if (bl2 && swingProgress < 0.001F && bl) {
                            matrices.translate(
                                (float) i * -0.641864F,
                                0.0F,
                                0.0F
                            );
                            matrices.multiply(
                                RotationAxis.POSITIVE_Y.rotationDegrees(
                                    (float) i * 10.0F
                                )
                            );
                        }
                    }

                    this.renderItem(
                            player,
                            item,
                            bl3
                                ? ModelTransformationMode.FIRST_PERSON_RIGHT_HAND
                                : ModelTransformationMode.FIRST_PERSON_LEFT_HAND,
                            !bl3,
                            matrices,
                            vertexConsumers,
                            light
                        );
                } else {
                    bl2 = arm == Arm.RIGHT;
                    int l;
                    float m;
                    if (
                        player.isUsingItem() &&
                        player.getItemUseTimeLeft() > 0 &&
                        player.getActiveHand() == hand
                    ) {
                        l = bl2 ? 1 : -1;
                        if (item.getUseAction() == UseAction.NONE) {
                            this.applyEquipOffset(matrices, arm, equipProgress);
                        } else if (item.getUseAction() == UseAction.EAT) {
                            this.applyEatOrDrinkTransformation(
                                    matrices,
                                    tickDelta,
                                    Arm.LEFT,
                                    item
                                );
                            this.applyEquipOffset(
                                    matrices,
                                    Arm.LEFT,
                                    equipProgress
                                );
                        } else if (item.getUseAction() == UseAction.DRINK) {
                            this.applyEatOrDrinkTransformation(
                                    matrices,
                                    tickDelta,
                                    Arm.LEFT,
                                    item
                                );
                            this.applyEquipOffset(
                                    matrices,
                                    Arm.LEFT,
                                    equipProgress
                                );
                        } else if (item.getUseAction() == UseAction.BLOCK) {
                            this.applyEquipOffset(matrices, arm, equipProgress);
                        } else if (item.getUseAction() == UseAction.BOW) {
                            this.applyEquipOffset(matrices, arm, equipProgress);
                            matrices.translate(
                                (float) l * -0.2785682F,
                                0.18344387F,
                                0.15731531F
                            );
                            matrices.multiply(
                                RotationAxis.POSITIVE_X.rotationDegrees(
                                    -13.935F
                                )
                            );
                            matrices.multiply(
                                RotationAxis.POSITIVE_Y.rotationDegrees(
                                    (float) l * 35.3F
                                )
                            );
                            matrices.multiply(
                                RotationAxis.POSITIVE_Z.rotationDegrees(
                                    (float) l * -9.785F
                                )
                            );
                            m = (float) item.getMaxUseTime() -
                            ((float) this.client.player.getItemUseTimeLeft() -
                                tickDelta +
                                1.0F);
                            f = m / 20.0F;
                            f = (f * f + f * 2.0F) / 3.0F;
                            if (f > 1.0F) {
                                f = 1.0F;
                            }

                            if (f > 0.1F) {
                                g = MathHelper.sin((m - 0.1F) * 1.3F);
                                h = f - 0.1F;
                                j = g * h;
                                matrices.translate(
                                    j * 0.0F,
                                    j * 0.004F,
                                    j * 0.0F
                                );
                            }

                            matrices.translate(f * 0.0F, f * 0.0F, f * 0.04F);
                            matrices.scale(1.0F, 1.0F, 1.0F + f * 0.2F);
                            matrices.multiply(
                                RotationAxis.NEGATIVE_Y.rotationDegrees(
                                    (float) l * 45.0F
                                )
                            );
                        } else if (item.getUseAction() == UseAction.SPEAR) {
                            this.applyEquipOffset(matrices, arm, equipProgress);
                            matrices.translate((float) l * -0.5F, 0.7F, 0.1F);
                            matrices.multiply(
                                RotationAxis.POSITIVE_X.rotationDegrees(-55.0F)
                            );
                            matrices.multiply(
                                RotationAxis.POSITIVE_Y.rotationDegrees(
                                    (float) l * 35.3F
                                )
                            );
                            matrices.multiply(
                                RotationAxis.POSITIVE_Z.rotationDegrees(
                                    (float) l * -9.785F
                                )
                            );
                            m = (float) item.getMaxUseTime() -
                            ((float) this.client.player.getItemUseTimeLeft() -
                                tickDelta +
                                1.0F);
                            f = m / 10.0F;
                            if (f > 1.0F) {
                                f = 1.0F;
                            }

                            if (f > 0.1F) {
                                g = MathHelper.sin((m - 0.1F) * 1.3F);
                                h = f - 0.1F;
                                j = g * h;
                                matrices.translate(
                                    j * 0.0F,
                                    j * 0.004F,
                                    j * 0.0F
                                );
                            }

                            matrices.translate(0.0F, 0.0F, f * 0.2F);
                            matrices.scale(1.0F, 1.0F, 1.0F + f * 0.2F);
                            matrices.multiply(
                                RotationAxis.NEGATIVE_Y.rotationDegrees(
                                    (float) l * 45.0F
                                )
                            );
                        } else if (item.getUseAction() == UseAction.BRUSH) {
                            this.applyBrushTransformation(
                                    matrices,
                                    tickDelta,
                                    arm,
                                    item,
                                    equipProgress
                                );
                        }
                    } else if (player.isUsingRiptide()) {
                        this.applyEquipOffset(matrices, arm, equipProgress);
                        l = bl2 ? 1 : -1;
                        matrices.translate((float) l * -0.4F, 0.8F, 0.3F);
                        matrices.multiply(
                            RotationAxis.POSITIVE_Y.rotationDegrees(
                                (float) l * 65.0F
                            )
                        );
                        matrices.multiply(
                            RotationAxis.POSITIVE_Z.rotationDegrees(
                                (float) l * -85.0F
                            )
                        );
                    } else {
                        if (!doBlockAnim) {
                            if (
                                ModuleManager.isEnabled(Animations.class) &&
                                Animations.smoothSwing
                            ) {
                                float n =
                                    -0.4F *
                                    MathHelper.sin(
                                        MathHelper.sqrt(swingProgress) *
                                        3.1415927F
                                    );
                                this.applyEquipOffset(matrices, arm, 0);

                                matrices.multiply(
                                    RotationAxis.POSITIVE_X.rotationDegrees(
                                        n * 90.0F
                                    )
                                );
                            } else {
                                float n =
                                    -0.4F *
                                    MathHelper.sin(
                                        MathHelper.sqrt(swingProgress) *
                                        3.1415927F
                                    );
                                m = 0.2F *
                                MathHelper.sin(
                                    MathHelper.sqrt(swingProgress) * 6.2831855F
                                );
                                f = -0.2F *
                                MathHelper.sin(swingProgress * 3.1415927F);
                                int o = bl2 ? 1 : -1;
                                matrices.translate((float) o * n, m, f);
                                this.applyEquipOffset(
                                        matrices,
                                        arm,
                                        equipProgress
                                    );
                                this.applySwingOffset(
                                        matrices,
                                        arm,
                                        swingProgress
                                    );
                            }
                        }
                    }

                    if (doBlockAnim) {
                        float n =
                            -0.4F *
                            MathHelper.sin(
                                MathHelper.sqrt(swingProgress) * 3.1415927F
                            );
                        matrices.translate(-1, 0, 0);
                        matrices.scale(
                            Animations.blockingItemSize,
                            Animations.blockingItemSize,
                            Animations.blockingItemSize
                        );

                        this.applyEquipOffset(matrices, arm, 0);

                        if (Animations.mode == Animations.Mode.Old) {
                            matrices.multiply(
                                RotationAxis.POSITIVE_X.rotationDegrees(
                                    n * -45.0F
                                )
                            );
                        } else if (Animations.mode == Animations.Mode.Slant) {
                            matrices.translate(-n * 0.2, n * 0.5, 0);
                        } else if (Animations.mode == Animations.Mode.Tap) {
                            matrices.multiply(
                                RotationAxis.POSITIVE_Z.rotationDegrees(
                                    n * -45.0F
                                )
                            );
                        } else if (Animations.mode == Animations.Mode.Stab) {
                            matrices.translate(-n, 0, -n * 0.5);
                            matrices.multiply(
                                RotationAxis.NEGATIVE_Y.rotationDegrees(
                                    n * -10.0F
                                )
                            );
                            matrices.multiply(
                                RotationAxis.NEGATIVE_X.rotationDegrees(
                                    n * 45.0F
                                )
                            );
                        } else if (Animations.mode == Animations.Mode.Penis) {
                            matrices.translate(0, -n, -0.5 * n);
                        } else if (Animations.mode == Animations.Mode.Swipe) {
                            matrices.translate(0, n * 20, 0);
                        }
                    }

                    this.renderItem(
                            player,
                            item,
                            bl2
                                ? ModelTransformationMode.FIRST_PERSON_RIGHT_HAND
                                : ModelTransformationMode.FIRST_PERSON_LEFT_HAND,
                            !bl2,
                            matrices,
                            vertexConsumers,
                            light
                        );
                }
            }
        }

        matrices.pop();
    }

    private void applyBlockTrans(
        MatrixStack matrices,
        float tickDelta,
        Arm arm,
        ItemStack stack
    ) {
        if (ModuleManager.isEnabled(Animations.class)) {
            if (Animations.mode == Animations.Mode.Old) {
                matrices.translate(-0.1, -0.5, -1.5);

                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-75));
                matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(90));

                matrices.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(30));
                matrices.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(45));
                matrices.multiply(RotationAxis.NEGATIVE_Z.rotationDegrees(90));
            } else if (Animations.mode == Animations.Mode.Slant) {
                matrices.translate(-0.1, -0.5, -1.5);

                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-75));
                matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(90));

                matrices.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(30));
                matrices.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(45));
                matrices.multiply(RotationAxis.NEGATIVE_Z.rotationDegrees(90));
            } else if (Animations.mode == Animations.Mode.Tap) {
                matrices.translate(-0.1, -0.5, -1.5);

                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-75));
                matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(90));

                matrices.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(30));
                matrices.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(45));
                matrices.multiply(RotationAxis.NEGATIVE_Z.rotationDegrees(90));
            } else if (Animations.mode == Animations.Mode.Stab) {
                matrices.translate(0.7, -0.5, -1.5);

                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-25));
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-90));
                matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-90));

                matrices.multiply(
                    RotationAxis.NEGATIVE_X.rotationDegrees(-100)
                );
                matrices.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(-45));
                matrices.multiply(RotationAxis.NEGATIVE_Z.rotationDegrees(-90));
            } else if (Animations.mode == Animations.Mode.Penis) {
                matrices.translate(-0.5, -0.3, -2.2);

                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-75));
                matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-90));

                matrices.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(-30));
                matrices.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(5));
                matrices.multiply(
                    RotationAxis.NEGATIVE_Z.rotationDegrees(-170)
                );
            } else if (Animations.mode == Animations.Mode.Swipe) {
                matrices.translate(0.7, -0.5, -1.5);

                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-50));
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-10));
                matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-80));

                matrices.multiply(
                    RotationAxis.NEGATIVE_X.rotationDegrees(-100)
                );
                matrices.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(-45));
                matrices.multiply(RotationAxis.NEGATIVE_Z.rotationDegrees(-90));
            }
        }
    }
}

/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen.inventory;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.AbstractButton;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.BeaconContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.IContainerListener;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.play.client.CCloseWindowPacket;
import net.minecraft.network.play.client.CUpdateBeaconPacket;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Effects;
import net.minecraft.tileentity.BeaconTileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class BeaconScreen
extends ContainerScreen<BeaconContainer> {
    private static final ResourceLocation BEACON_GUI_TEXTURES = new ResourceLocation("textures/gui/container/beacon.png");
    private static final ITextComponent field_243334_B = new TranslationTextComponent("block.minecraft.beacon.primary");
    private static final ITextComponent field_243335_C = new TranslationTextComponent("block.minecraft.beacon.secondary");
    private ConfirmButton beaconConfirmButton;
    private boolean buttonsNotDrawn;
    private Effect primaryEffect;
    private Effect secondaryEffect;

    public BeaconScreen(BeaconContainer beaconContainer, PlayerInventory playerInventory, ITextComponent iTextComponent) {
        super(beaconContainer, playerInventory, iTextComponent);
        this.xSize = 230;
        this.ySize = 219;
        beaconContainer.addListener(new IContainerListener(){
            final BeaconContainer val$container;
            final BeaconScreen this$0;
            {
                this.this$0 = beaconScreen;
                this.val$container = beaconContainer;
            }

            @Override
            public void sendAllContents(Container container, NonNullList<ItemStack> nonNullList) {
            }

            @Override
            public void sendSlotContents(Container container, int n, ItemStack itemStack) {
            }

            @Override
            public void sendWindowProperty(Container container, int n, int n2) {
                this.this$0.primaryEffect = this.val$container.func_216967_f();
                this.this$0.secondaryEffect = this.val$container.func_216968_g();
                this.this$0.buttonsNotDrawn = true;
            }
        });
    }

    @Override
    protected void init() {
        super.init();
        this.beaconConfirmButton = this.addButton(new ConfirmButton(this, this.guiLeft + 164, this.guiTop + 107));
        this.addButton(new CancelButton(this, this.guiLeft + 190, this.guiTop + 107));
        this.buttonsNotDrawn = true;
        this.beaconConfirmButton.active = false;
    }

    @Override
    public void tick() {
        super.tick();
        int n = ((BeaconContainer)this.container).func_216969_e();
        if (this.buttonsNotDrawn && n >= 0) {
            PowerButton powerButton;
            Effect effect;
            int n2;
            int n3;
            int n4;
            int n5;
            this.buttonsNotDrawn = false;
            for (n5 = 0; n5 <= 2; ++n5) {
                n4 = BeaconTileEntity.EFFECTS_LIST[n5].length;
                n3 = n4 * 22 + (n4 - 1) * 2;
                for (n2 = 0; n2 < n4; ++n2) {
                    effect = BeaconTileEntity.EFFECTS_LIST[n5][n2];
                    powerButton = new PowerButton(this, this.guiLeft + 76 + n2 * 24 - n3 / 2, this.guiTop + 22 + n5 * 25, effect, true);
                    this.addButton(powerButton);
                    if (n5 >= n) {
                        powerButton.active = false;
                        continue;
                    }
                    if (effect != this.primaryEffect) continue;
                    powerButton.setSelected(false);
                }
            }
            n5 = 3;
            n4 = BeaconTileEntity.EFFECTS_LIST[3].length + 1;
            n3 = n4 * 22 + (n4 - 1) * 2;
            for (n2 = 0; n2 < n4 - 1; ++n2) {
                effect = BeaconTileEntity.EFFECTS_LIST[3][n2];
                powerButton = new PowerButton(this, this.guiLeft + 167 + n2 * 24 - n3 / 2, this.guiTop + 47, effect, false);
                this.addButton(powerButton);
                if (3 >= n) {
                    powerButton.active = false;
                    continue;
                }
                if (effect != this.secondaryEffect) continue;
                powerButton.setSelected(false);
            }
            if (this.primaryEffect != null) {
                PowerButton powerButton2 = new PowerButton(this, this.guiLeft + 167 + (n4 - 1) * 24 - n3 / 2, this.guiTop + 47, this.primaryEffect, false);
                this.addButton(powerButton2);
                if (3 >= n) {
                    powerButton2.active = false;
                } else if (this.primaryEffect == this.secondaryEffect) {
                    powerButton2.setSelected(false);
                }
            }
        }
        this.beaconConfirmButton.active = ((BeaconContainer)this.container).func_216970_h() && this.primaryEffect != null;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int n, int n2) {
        BeaconScreen.drawCenteredString(matrixStack, this.font, field_243334_B, 62, 10, 0xE0E0E0);
        BeaconScreen.drawCenteredString(matrixStack, this.font, field_243335_C, 169, 10, 0xE0E0E0);
        for (Widget widget : this.buttons) {
            if (!widget.isHovered()) continue;
            widget.renderToolTip(matrixStack, n - this.guiLeft, n2 - this.guiTop);
            break;
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float f, int n, int n2) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.minecraft.getTextureManager().bindTexture(BEACON_GUI_TEXTURES);
        int n3 = (this.width - this.xSize) / 2;
        int n4 = (this.height - this.ySize) / 2;
        this.blit(matrixStack, n3, n4, 0, 0, this.xSize, this.ySize);
        this.itemRenderer.zLevel = 100.0f;
        this.itemRenderer.renderItemAndEffectIntoGUI(new ItemStack(Items.NETHERITE_INGOT), n3 + 20, n4 + 109);
        this.itemRenderer.renderItemAndEffectIntoGUI(new ItemStack(Items.EMERALD), n3 + 41, n4 + 109);
        this.itemRenderer.renderItemAndEffectIntoGUI(new ItemStack(Items.DIAMOND), n3 + 41 + 22, n4 + 109);
        this.itemRenderer.renderItemAndEffectIntoGUI(new ItemStack(Items.GOLD_INGOT), n3 + 42 + 44, n4 + 109);
        this.itemRenderer.renderItemAndEffectIntoGUI(new ItemStack(Items.IRON_INGOT), n3 + 42 + 66, n4 + 109);
        this.itemRenderer.zLevel = 0.0f;
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, n, n2, f);
        this.renderHoveredTooltip(matrixStack, n, n2);
    }

    static Minecraft access$000(BeaconScreen beaconScreen) {
        return beaconScreen.minecraft;
    }

    static Minecraft access$100(BeaconScreen beaconScreen) {
        return beaconScreen.minecraft;
    }

    static Minecraft access$200(BeaconScreen beaconScreen) {
        return beaconScreen.minecraft;
    }

    static Minecraft access$300(BeaconScreen beaconScreen) {
        return beaconScreen.minecraft;
    }

    static Minecraft access$400(BeaconScreen beaconScreen) {
        return beaconScreen.minecraft;
    }

    static Minecraft access$500(BeaconScreen beaconScreen) {
        return beaconScreen.minecraft;
    }

    static Minecraft access$600(BeaconScreen beaconScreen) {
        return beaconScreen.minecraft;
    }

    static List access$700(BeaconScreen beaconScreen) {
        return beaconScreen.buttons;
    }

    static List access$800(BeaconScreen beaconScreen) {
        return beaconScreen.children;
    }

    class ConfirmButton
    extends SpriteButton {
        final BeaconScreen this$0;

        public ConfirmButton(BeaconScreen beaconScreen, int n, int n2) {
            this.this$0 = beaconScreen;
            super(n, n2, 90, 220);
        }

        @Override
        public void onPress() {
            BeaconScreen.access$300(this.this$0).getConnection().sendPacket(new CUpdateBeaconPacket(Effect.getId(this.this$0.primaryEffect), Effect.getId(this.this$0.secondaryEffect)));
            BeaconScreen.access$500((BeaconScreen)this.this$0).player.connection.sendPacket(new CCloseWindowPacket(BeaconScreen.access$400((BeaconScreen)this.this$0).player.openContainer.windowId));
            BeaconScreen.access$600(this.this$0).displayGuiScreen(null);
        }

        @Override
        public void renderToolTip(MatrixStack matrixStack, int n, int n2) {
            this.this$0.renderTooltip(matrixStack, DialogTexts.GUI_DONE, n, n2);
        }
    }

    class CancelButton
    extends SpriteButton {
        final BeaconScreen this$0;

        public CancelButton(BeaconScreen beaconScreen, int n, int n2) {
            this.this$0 = beaconScreen;
            super(n, n2, 112, 220);
        }

        @Override
        public void onPress() {
            BeaconScreen.access$100((BeaconScreen)this.this$0).player.connection.sendPacket(new CCloseWindowPacket(BeaconScreen.access$000((BeaconScreen)this.this$0).player.openContainer.windowId));
            BeaconScreen.access$200(this.this$0).displayGuiScreen(null);
        }

        @Override
        public void renderToolTip(MatrixStack matrixStack, int n, int n2) {
            this.this$0.renderTooltip(matrixStack, DialogTexts.GUI_CANCEL, n, n2);
        }
    }

    class PowerButton
    extends Button {
        private final Effect effect;
        private final TextureAtlasSprite field_212946_c;
        private final boolean field_212947_d;
        private final ITextComponent field_243336_e;
        final BeaconScreen this$0;

        public PowerButton(BeaconScreen beaconScreen, int n, int n2, Effect effect, boolean bl) {
            this.this$0 = beaconScreen;
            super(n, n2);
            this.effect = effect;
            this.field_212946_c = Minecraft.getInstance().getPotionSpriteUploader().getSprite(effect);
            this.field_212947_d = bl;
            this.field_243336_e = this.func_243337_a(effect, bl);
        }

        private ITextComponent func_243337_a(Effect effect, boolean bl) {
            TranslationTextComponent translationTextComponent = new TranslationTextComponent(effect.getName());
            if (!bl && effect != Effects.REGENERATION) {
                translationTextComponent.appendString(" II");
            }
            return translationTextComponent;
        }

        @Override
        public void onPress() {
            if (!this.isSelected()) {
                if (this.field_212947_d) {
                    this.this$0.primaryEffect = this.effect;
                } else {
                    this.this$0.secondaryEffect = this.effect;
                }
                BeaconScreen.access$700(this.this$0).clear();
                BeaconScreen.access$800(this.this$0).clear();
                this.this$0.init();
                this.this$0.tick();
            }
        }

        @Override
        public void renderToolTip(MatrixStack matrixStack, int n, int n2) {
            this.this$0.renderTooltip(matrixStack, this.field_243336_e, n, n2);
        }

        @Override
        protected void func_230454_a_(MatrixStack matrixStack) {
            Minecraft.getInstance().getTextureManager().bindTexture(this.field_212946_c.getAtlasTexture().getTextureLocation());
            PowerButton.blit(matrixStack, this.x + 2, this.y + 2, this.getBlitOffset(), 18, 18, this.field_212946_c);
        }
    }

    static abstract class SpriteButton
    extends Button {
        private final int u;
        private final int v;

        protected SpriteButton(int n, int n2, int n3, int n4) {
            super(n, n2);
            this.u = n3;
            this.v = n4;
        }

        @Override
        protected void func_230454_a_(MatrixStack matrixStack) {
            this.blit(matrixStack, this.x + 2, this.y + 2, this.u, this.v, 18, 18);
        }
    }

    static abstract class Button
    extends AbstractButton {
        private boolean selected;

        protected Button(int n, int n2) {
            super(n, n2, 22, 22, StringTextComponent.EMPTY);
        }

        @Override
        public void renderButton(MatrixStack matrixStack, int n, int n2, float f) {
            Minecraft.getInstance().getTextureManager().bindTexture(BEACON_GUI_TEXTURES);
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            int n3 = 219;
            int n4 = 0;
            if (!this.active) {
                n4 += this.width * 2;
            } else if (this.selected) {
                n4 += this.width * 1;
            } else if (this.isHovered()) {
                n4 += this.width * 3;
            }
            this.blit(matrixStack, this.x, this.y, n4, 219, this.width, this.height);
            this.func_230454_a_(matrixStack);
        }

        protected abstract void func_230454_a_(MatrixStack var1);

        public boolean isSelected() {
            return this.selected;
        }

        public void setSelected(boolean bl) {
            this.selected = bl;
        }
    }
}


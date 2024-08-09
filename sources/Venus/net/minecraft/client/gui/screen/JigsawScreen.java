/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.JigsawBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.AbstractSlider;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.network.play.client.CJigsawBlockGeneratePacket;
import net.minecraft.network.play.client.CUpdateJigsawBlockPacket;
import net.minecraft.tileentity.JigsawTileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class JigsawScreen
extends Screen {
    private static final ITextComponent field_243346_a = new TranslationTextComponent("jigsaw_block.joint_label");
    private static final ITextComponent field_243347_b = new TranslationTextComponent("jigsaw_block.pool");
    private static final ITextComponent field_243348_c = new TranslationTextComponent("jigsaw_block.name");
    private static final ITextComponent field_243349_p = new TranslationTextComponent("jigsaw_block.target");
    private static final ITextComponent field_243350_q = new TranslationTextComponent("jigsaw_block.final_state");
    private final JigsawTileEntity field_214259_a;
    private TextFieldWidget field_238818_b_;
    private TextFieldWidget field_238819_c_;
    private TextFieldWidget field_238820_p_;
    private TextFieldWidget finalStateField;
    private int field_238821_r_;
    private boolean field_238822_s_ = true;
    private Button field_238823_t_;
    private Button doneButton;
    private JigsawTileEntity.OrientationType field_238824_v_;

    public JigsawScreen(JigsawTileEntity jigsawTileEntity) {
        super(NarratorChatListener.EMPTY);
        this.field_214259_a = jigsawTileEntity;
    }

    @Override
    public void tick() {
        this.field_238818_b_.tick();
        this.field_238819_c_.tick();
        this.field_238820_p_.tick();
        this.finalStateField.tick();
    }

    private void func_214256_b() {
        this.func_214258_d();
        this.minecraft.displayGuiScreen(null);
    }

    private void func_214257_c() {
        this.minecraft.displayGuiScreen(null);
    }

    private void func_214258_d() {
        this.minecraft.getConnection().sendPacket(new CUpdateJigsawBlockPacket(this.field_214259_a.getPos(), new ResourceLocation(this.field_238818_b_.getText()), new ResourceLocation(this.field_238819_c_.getText()), new ResourceLocation(this.field_238820_p_.getText()), this.finalStateField.getText(), this.field_238824_v_));
    }

    private void func_238835_m_() {
        this.minecraft.getConnection().sendPacket(new CJigsawBlockGeneratePacket(this.field_214259_a.getPos(), this.field_238821_r_, this.field_238822_s_));
    }

    @Override
    public void closeScreen() {
        this.func_214257_c();
    }

    @Override
    protected void init() {
        boolean bl;
        this.minecraft.keyboardListener.enableRepeatEvents(false);
        this.field_238820_p_ = new TextFieldWidget(this.font, this.width / 2 - 152, 20, 300, 20, new TranslationTextComponent("jigsaw_block.pool"));
        this.field_238820_p_.setMaxStringLength(128);
        this.field_238820_p_.setText(this.field_214259_a.func_235670_g_().toString());
        this.field_238820_p_.setResponder(this::lambda$init$0);
        this.children.add(this.field_238820_p_);
        this.field_238818_b_ = new TextFieldWidget(this.font, this.width / 2 - 152, 55, 300, 20, new TranslationTextComponent("jigsaw_block.name"));
        this.field_238818_b_.setMaxStringLength(128);
        this.field_238818_b_.setText(this.field_214259_a.func_235668_d_().toString());
        this.field_238818_b_.setResponder(this::lambda$init$1);
        this.children.add(this.field_238818_b_);
        this.field_238819_c_ = new TextFieldWidget(this.font, this.width / 2 - 152, 90, 300, 20, new TranslationTextComponent("jigsaw_block.target"));
        this.field_238819_c_.setMaxStringLength(128);
        this.field_238819_c_.setText(this.field_214259_a.func_235669_f_().toString());
        this.field_238819_c_.setResponder(this::lambda$init$2);
        this.children.add(this.field_238819_c_);
        this.finalStateField = new TextFieldWidget(this.font, this.width / 2 - 152, 125, 300, 20, new TranslationTextComponent("jigsaw_block.final_state"));
        this.finalStateField.setMaxStringLength(256);
        this.finalStateField.setText(this.field_214259_a.getFinalState());
        this.children.add(this.finalStateField);
        this.field_238824_v_ = this.field_214259_a.func_235671_j_();
        int n = this.font.getStringPropertyWidth(field_243346_a) + 10;
        this.field_238823_t_ = this.addButton(new Button(this.width / 2 - 152 + n, 150, 300 - n, 20, this.func_238836_u_(), this::lambda$init$3));
        this.field_238823_t_.active = bl = JigsawBlock.getConnectingDirection(this.field_214259_a.getBlockState()).getAxis().isVertical();
        this.field_238823_t_.visible = bl;
        this.addButton(new AbstractSlider(this, this.width / 2 - 154, 180, 100, 20, StringTextComponent.EMPTY, 0.0){
            final JigsawScreen this$0;
            {
                this.this$0 = jigsawScreen;
                super(n, n2, n3, n4, iTextComponent, d);
                this.func_230979_b_();
            }

            @Override
            protected void func_230979_b_() {
                this.setMessage(new TranslationTextComponent("jigsaw_block.levels", this.this$0.field_238821_r_));
            }

            @Override
            protected void func_230972_a_() {
                this.this$0.field_238821_r_ = MathHelper.floor(MathHelper.clampedLerp(0.0, 7.0, this.sliderValue));
            }
        });
        this.addButton(new Button(this, this.width / 2 - 50, 180, 100, 20, new TranslationTextComponent("jigsaw_block.keep_jigsaws"), this::lambda$init$4){
            final JigsawScreen this$0;
            {
                this.this$0 = jigsawScreen;
                super(n, n2, n3, n4, iTextComponent, iPressable);
            }

            @Override
            public ITextComponent getMessage() {
                return DialogTexts.getComposedOptionMessage(super.getMessage(), this.this$0.field_238822_s_);
            }
        });
        this.addButton(new Button(this.width / 2 + 54, 180, 100, 20, new TranslationTextComponent("jigsaw_block.generate"), this::lambda$init$5));
        this.doneButton = this.addButton(new Button(this.width / 2 - 4 - 150, 210, 150, 20, DialogTexts.GUI_DONE, this::lambda$init$6));
        this.addButton(new Button(this.width / 2 + 4, 210, 150, 20, DialogTexts.GUI_CANCEL, this::lambda$init$7));
        this.setFocusedDefault(this.field_238820_p_);
        this.func_214253_a();
    }

    private void func_214253_a() {
        this.doneButton.active = ResourceLocation.isResouceNameValid(this.field_238818_b_.getText()) && ResourceLocation.isResouceNameValid(this.field_238819_c_.getText()) && ResourceLocation.isResouceNameValid(this.field_238820_p_.getText());
    }

    @Override
    public void resize(Minecraft minecraft, int n, int n2) {
        String string = this.field_238818_b_.getText();
        String string2 = this.field_238819_c_.getText();
        String string3 = this.field_238820_p_.getText();
        String string4 = this.finalStateField.getText();
        int n3 = this.field_238821_r_;
        JigsawTileEntity.OrientationType orientationType = this.field_238824_v_;
        this.init(minecraft, n, n2);
        this.field_238818_b_.setText(string);
        this.field_238819_c_.setText(string2);
        this.field_238820_p_.setText(string3);
        this.finalStateField.setText(string4);
        this.field_238821_r_ = n3;
        this.field_238824_v_ = orientationType;
        this.field_238823_t_.setMessage(this.func_238836_u_());
    }

    private ITextComponent func_238836_u_() {
        return new TranslationTextComponent("jigsaw_block.joint." + this.field_238824_v_.getString());
    }

    @Override
    public void onClose() {
        this.minecraft.keyboardListener.enableRepeatEvents(true);
    }

    @Override
    public boolean keyPressed(int n, int n2, int n3) {
        if (super.keyPressed(n, n2, n3)) {
            return false;
        }
        if (!this.doneButton.active || n != 257 && n != 335) {
            return true;
        }
        this.func_214256_b();
        return false;
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.renderBackground(matrixStack);
        JigsawScreen.drawString(matrixStack, this.font, field_243347_b, this.width / 2 - 153, 10, 0xA0A0A0);
        this.field_238820_p_.render(matrixStack, n, n2, f);
        JigsawScreen.drawString(matrixStack, this.font, field_243348_c, this.width / 2 - 153, 45, 0xA0A0A0);
        this.field_238818_b_.render(matrixStack, n, n2, f);
        JigsawScreen.drawString(matrixStack, this.font, field_243349_p, this.width / 2 - 153, 80, 0xA0A0A0);
        this.field_238819_c_.render(matrixStack, n, n2, f);
        JigsawScreen.drawString(matrixStack, this.font, field_243350_q, this.width / 2 - 153, 115, 0xA0A0A0);
        this.finalStateField.render(matrixStack, n, n2, f);
        if (JigsawBlock.getConnectingDirection(this.field_214259_a.getBlockState()).getAxis().isVertical()) {
            JigsawScreen.drawString(matrixStack, this.font, field_243346_a, this.width / 2 - 153, 156, 0xFFFFFF);
        }
        super.render(matrixStack, n, n2, f);
    }

    private void lambda$init$7(Button button) {
        this.func_214257_c();
    }

    private void lambda$init$6(Button button) {
        this.func_214256_b();
    }

    private void lambda$init$5(Button button) {
        this.func_214256_b();
        this.func_238835_m_();
    }

    private void lambda$init$4(Button button) {
        this.field_238822_s_ = !this.field_238822_s_;
        button.queueNarration(250);
    }

    private void lambda$init$3(Button button) {
        JigsawTileEntity.OrientationType[] orientationTypeArray = JigsawTileEntity.OrientationType.values();
        int n = (this.field_238824_v_.ordinal() + 1) % orientationTypeArray.length;
        this.field_238824_v_ = orientationTypeArray[n];
        button.setMessage(this.func_238836_u_());
    }

    private void lambda$init$2(String string) {
        this.func_214253_a();
    }

    private void lambda$init$1(String string) {
        this.func_214253_a();
    }

    private void lambda$init$0(String string) {
        this.func_214253_a();
    }
}


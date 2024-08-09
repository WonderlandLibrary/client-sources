/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.dto.RealmsWorldOptions;
import com.mojang.realmsclient.gui.screens.RealmsConfigureWorldScreen;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.widget.AbstractSlider;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.realms.RealmsLabel;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class RealmsSlotOptionsScreen
extends RealmsScreen {
    public static final ITextComponent[] field_238035_a_ = new ITextComponent[]{new TranslationTextComponent("options.difficulty.peaceful"), new TranslationTextComponent("options.difficulty.easy"), new TranslationTextComponent("options.difficulty.normal"), new TranslationTextComponent("options.difficulty.hard")};
    public static final ITextComponent[] field_238036_b_ = new ITextComponent[]{new TranslationTextComponent("selectWorld.gameMode.survival"), new TranslationTextComponent("selectWorld.gameMode.creative"), new TranslationTextComponent("selectWorld.gameMode.adventure")};
    private static final ITextComponent field_238037_p_ = new TranslationTextComponent("mco.configure.world.on");
    private static final ITextComponent field_238038_q_ = new TranslationTextComponent("mco.configure.world.off");
    private static final ITextComponent field_243171_r = new TranslationTextComponent("selectWorld.gameMode");
    private static final ITextComponent field_243172_s = new TranslationTextComponent("mco.configure.world.edit.slot.name");
    private TextFieldWidget field_224642_e;
    protected final RealmsConfigureWorldScreen field_224638_a;
    private int field_224643_f;
    private int field_224644_g;
    private int field_224645_h;
    private final RealmsWorldOptions field_224646_i;
    private final RealmsServer.ServerType field_224647_j;
    private final int field_224648_k;
    private int field_224649_l;
    private int field_224650_m;
    private Boolean field_224651_n;
    private Boolean field_224652_o;
    private Boolean field_224653_p;
    private Boolean field_224654_q;
    private Integer field_224655_r;
    private Boolean field_224656_s;
    private Boolean field_224657_t;
    private Button field_224658_u;
    private Button field_224659_v;
    private Button field_224660_w;
    private Button field_224661_x;
    private SettingsSlider field_224662_y;
    private Button field_224663_z;
    private Button field_224635_A;
    private RealmsLabel field_224636_B;
    private RealmsLabel field_224637_C;

    public RealmsSlotOptionsScreen(RealmsConfigureWorldScreen realmsConfigureWorldScreen, RealmsWorldOptions realmsWorldOptions, RealmsServer.ServerType serverType, int n) {
        this.field_224638_a = realmsConfigureWorldScreen;
        this.field_224646_i = realmsWorldOptions;
        this.field_224647_j = serverType;
        this.field_224648_k = n;
    }

    @Override
    public void onClose() {
        this.minecraft.keyboardListener.enableRepeatEvents(true);
    }

    @Override
    public void tick() {
        this.field_224642_e.tick();
    }

    @Override
    public boolean keyPressed(int n, int n2, int n3) {
        if (n == 256) {
            this.minecraft.displayGuiScreen(this.field_224638_a);
            return false;
        }
        return super.keyPressed(n, n2, n3);
    }

    @Override
    public void init() {
        this.field_224644_g = 170;
        this.field_224643_f = this.width / 2 - this.field_224644_g;
        this.field_224645_h = this.width / 2 + 10;
        this.field_224649_l = this.field_224646_i.field_230621_h_;
        this.field_224650_m = this.field_224646_i.field_230622_i_;
        if (this.field_224647_j == RealmsServer.ServerType.NORMAL) {
            this.field_224651_n = this.field_224646_i.field_230614_a_;
            this.field_224655_r = this.field_224646_i.field_230618_e_;
            this.field_224657_t = this.field_224646_i.field_230620_g_;
            this.field_224653_p = this.field_224646_i.field_230615_b_;
            this.field_224654_q = this.field_224646_i.field_230616_c_;
            this.field_224652_o = this.field_224646_i.field_230617_d_;
            this.field_224656_s = this.field_224646_i.field_230619_f_;
        } else {
            TranslationTextComponent translationTextComponent = this.field_224647_j == RealmsServer.ServerType.ADVENTUREMAP ? new TranslationTextComponent("mco.configure.world.edit.subscreen.adventuremap") : (this.field_224647_j == RealmsServer.ServerType.INSPIRATION ? new TranslationTextComponent("mco.configure.world.edit.subscreen.inspiration") : new TranslationTextComponent("mco.configure.world.edit.subscreen.experience"));
            this.field_224637_C = new RealmsLabel(translationTextComponent, this.width / 2, 26, 0xFF0000);
            this.field_224651_n = true;
            this.field_224655_r = 0;
            this.field_224657_t = false;
            this.field_224653_p = true;
            this.field_224654_q = true;
            this.field_224652_o = true;
            this.field_224656_s = true;
        }
        this.field_224642_e = new TextFieldWidget(this.minecraft.fontRenderer, this.field_224643_f + 2, RealmsSlotOptionsScreen.func_239562_k_(1), this.field_224644_g - 4, 20, null, new TranslationTextComponent("mco.configure.world.edit.slot.name"));
        this.field_224642_e.setMaxStringLength(10);
        this.field_224642_e.setText(this.field_224646_i.func_230787_a_(this.field_224648_k));
        this.setListenerDefault(this.field_224642_e);
        this.field_224658_u = this.addButton(new Button(this.field_224645_h, RealmsSlotOptionsScreen.func_239562_k_(1), this.field_224644_g, 20, this.func_224618_d(), this::lambda$init$0));
        this.addButton(new Button(this.field_224643_f, RealmsSlotOptionsScreen.func_239562_k_(3), this.field_224644_g, 20, this.func_224610_c(), this::lambda$init$1));
        this.field_224659_v = this.addButton(new Button(this.field_224645_h, RealmsSlotOptionsScreen.func_239562_k_(3), this.field_224644_g, 20, this.func_224606_e(), this::lambda$init$2));
        this.addButton(new Button(this.field_224643_f, RealmsSlotOptionsScreen.func_239562_k_(5), this.field_224644_g, 20, this.func_224625_b(), this::lambda$init$3));
        this.field_224660_w = this.addButton(new Button(this.field_224645_h, RealmsSlotOptionsScreen.func_239562_k_(5), this.field_224644_g, 20, this.func_224626_f(), this::lambda$init$4));
        this.field_224662_y = this.addButton(new SettingsSlider(this, this.field_224643_f, RealmsSlotOptionsScreen.func_239562_k_(7), this.field_224644_g, this.field_224655_r, 0.0f, 16.0f));
        this.field_224661_x = this.addButton(new Button(this.field_224645_h, RealmsSlotOptionsScreen.func_239562_k_(7), this.field_224644_g, 20, this.func_224621_g(), this::lambda$init$5));
        this.field_224635_A = this.addButton(new Button(this.field_224643_f, RealmsSlotOptionsScreen.func_239562_k_(9), this.field_224644_g, 20, this.func_224634_i(), this::lambda$init$6));
        this.field_224663_z = this.addButton(new Button(this.field_224645_h, RealmsSlotOptionsScreen.func_239562_k_(9), this.field_224644_g, 20, this.func_224594_h(), this::lambda$init$7));
        if (this.field_224647_j != RealmsServer.ServerType.NORMAL) {
            this.field_224658_u.active = false;
            this.field_224659_v.active = false;
            this.field_224661_x.active = false;
            this.field_224660_w.active = false;
            this.field_224662_y.active = false;
            this.field_224663_z.active = false;
            this.field_224635_A.active = false;
        }
        if (this.field_224649_l == 0) {
            this.field_224660_w.active = false;
        }
        this.addButton(new Button(this.field_224643_f, RealmsSlotOptionsScreen.func_239562_k_(13), this.field_224644_g, 20, new TranslationTextComponent("mco.configure.world.buttons.done"), this::lambda$init$8));
        this.addButton(new Button(this.field_224645_h, RealmsSlotOptionsScreen.func_239562_k_(13), this.field_224644_g, 20, DialogTexts.GUI_CANCEL, this::lambda$init$9));
        this.addListener(this.field_224642_e);
        this.field_224636_B = this.addListener(new RealmsLabel(new TranslationTextComponent("mco.configure.world.buttons.options"), this.width / 2, 17, 0xFFFFFF));
        if (this.field_224637_C != null) {
            this.addListener(this.field_224637_C);
        }
        this.func_231411_u_();
    }

    private ITextComponent func_224625_b() {
        return new TranslationTextComponent("options.difficulty").appendString(": ").append(field_238035_a_[this.field_224649_l]);
    }

    private ITextComponent func_224610_c() {
        return new TranslationTextComponent("options.generic_value", field_243171_r, field_238036_b_[this.field_224650_m]);
    }

    private ITextComponent func_224618_d() {
        return new TranslationTextComponent("mco.configure.world.pvp").appendString(": ").append(RealmsSlotOptionsScreen.func_238050_c_(this.field_224651_n));
    }

    private ITextComponent func_224606_e() {
        return new TranslationTextComponent("mco.configure.world.spawnAnimals").appendString(": ").append(RealmsSlotOptionsScreen.func_238050_c_(this.field_224653_p));
    }

    private ITextComponent func_224626_f() {
        return this.field_224649_l == 0 ? new TranslationTextComponent("mco.configure.world.spawnMonsters").appendString(": ").append(new TranslationTextComponent("mco.configure.world.off")) : new TranslationTextComponent("mco.configure.world.spawnMonsters").appendString(": ").append(RealmsSlotOptionsScreen.func_238050_c_(this.field_224654_q));
    }

    private ITextComponent func_224621_g() {
        return new TranslationTextComponent("mco.configure.world.spawnNPCs").appendString(": ").append(RealmsSlotOptionsScreen.func_238050_c_(this.field_224652_o));
    }

    private ITextComponent func_224594_h() {
        return new TranslationTextComponent("mco.configure.world.commandBlocks").appendString(": ").append(RealmsSlotOptionsScreen.func_238050_c_(this.field_224656_s));
    }

    private ITextComponent func_224634_i() {
        return new TranslationTextComponent("mco.configure.world.forceGameMode").appendString(": ").append(RealmsSlotOptionsScreen.func_238050_c_(this.field_224657_t));
    }

    private static ITextComponent func_238050_c_(boolean bl) {
        return bl ? field_238037_p_ : field_238038_q_;
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.renderBackground(matrixStack);
        this.font.func_243248_b(matrixStack, field_243172_s, this.field_224643_f + this.field_224644_g / 2 - this.font.getStringPropertyWidth(field_243172_s) / 2, RealmsSlotOptionsScreen.func_239562_k_(0) - 5, 0xFFFFFF);
        this.field_224636_B.func_239560_a_(this, matrixStack);
        if (this.field_224637_C != null) {
            this.field_224637_C.func_239560_a_(this, matrixStack);
        }
        this.field_224642_e.render(matrixStack, n, n2, f);
        super.render(matrixStack, n, n2, f);
    }

    private String func_224604_j() {
        return this.field_224642_e.getText().equals(this.field_224646_i.func_230790_b_(this.field_224648_k)) ? "" : this.field_224642_e.getText();
    }

    private void func_224613_k() {
        if (this.field_224647_j != RealmsServer.ServerType.ADVENTUREMAP && this.field_224647_j != RealmsServer.ServerType.EXPERIENCE && this.field_224647_j != RealmsServer.ServerType.INSPIRATION) {
            this.field_224638_a.func_224386_a(new RealmsWorldOptions(this.field_224651_n, this.field_224653_p, this.field_224654_q, this.field_224652_o, this.field_224655_r, this.field_224656_s, this.field_224649_l, this.field_224650_m, this.field_224657_t, this.func_224604_j()));
        } else {
            this.field_224638_a.func_224386_a(new RealmsWorldOptions(this.field_224646_i.field_230614_a_, this.field_224646_i.field_230615_b_, this.field_224646_i.field_230616_c_, this.field_224646_i.field_230617_d_, this.field_224646_i.field_230618_e_, this.field_224646_i.field_230619_f_, this.field_224649_l, this.field_224650_m, this.field_224646_i.field_230620_g_, this.func_224604_j()));
        }
    }

    private void lambda$init$9(Button button) {
        this.minecraft.displayGuiScreen(this.field_224638_a);
    }

    private void lambda$init$8(Button button) {
        this.func_224613_k();
    }

    private void lambda$init$7(Button button) {
        this.field_224656_s = this.field_224656_s == false;
        button.setMessage(this.func_224594_h());
    }

    private void lambda$init$6(Button button) {
        this.field_224657_t = this.field_224657_t == false;
        button.setMessage(this.func_224634_i());
    }

    private void lambda$init$5(Button button) {
        this.field_224652_o = this.field_224652_o == false;
        button.setMessage(this.func_224621_g());
    }

    private void lambda$init$4(Button button) {
        this.field_224654_q = this.field_224654_q == false;
        button.setMessage(this.func_224626_f());
    }

    private void lambda$init$3(Button button) {
        this.field_224649_l = (this.field_224649_l + 1) % field_238035_a_.length;
        button.setMessage(this.func_224625_b());
        if (this.field_224647_j == RealmsServer.ServerType.NORMAL) {
            this.field_224660_w.active = this.field_224649_l != 0;
            this.field_224660_w.setMessage(this.func_224626_f());
        }
    }

    private void lambda$init$2(Button button) {
        this.field_224653_p = this.field_224653_p == false;
        button.setMessage(this.func_224606_e());
    }

    private void lambda$init$1(Button button) {
        this.field_224650_m = (this.field_224650_m + 1) % field_238036_b_.length;
        button.setMessage(this.func_224610_c());
    }

    private void lambda$init$0(Button button) {
        this.field_224651_n = this.field_224651_n == false;
        button.setMessage(this.func_224618_d());
    }

    class SettingsSlider
    extends AbstractSlider {
        private final double field_238066_c_;
        private final double field_238067_d_;
        final RealmsSlotOptionsScreen this$0;

        public SettingsSlider(RealmsSlotOptionsScreen realmsSlotOptionsScreen, int n, int n2, int n3, int n4, float f, float f2) {
            this.this$0 = realmsSlotOptionsScreen;
            super(n, n2, n3, 20, StringTextComponent.EMPTY, 0.0);
            this.field_238066_c_ = f;
            this.field_238067_d_ = f2;
            this.sliderValue = (MathHelper.clamp((float)n4, f, f2) - f) / (f2 - f);
            this.func_230979_b_();
        }

        @Override
        public void func_230972_a_() {
            if (this.this$0.field_224662_y.active) {
                this.this$0.field_224655_r = (int)MathHelper.lerp(MathHelper.clamp(this.sliderValue, 0.0, 1.0), this.field_238066_c_, this.field_238067_d_);
            }
        }

        @Override
        protected void func_230979_b_() {
            this.setMessage(new TranslationTextComponent("mco.configure.world.spawnProtection").appendString(": ").append(this.this$0.field_224655_r == 0 ? new TranslationTextComponent("mco.configure.world.off") : new StringTextComponent(String.valueOf(this.this$0.field_224655_r))));
        }

        @Override
        public void onClick(double d, double d2) {
        }

        @Override
        public void onRelease(double d, double d2) {
        }
    }
}


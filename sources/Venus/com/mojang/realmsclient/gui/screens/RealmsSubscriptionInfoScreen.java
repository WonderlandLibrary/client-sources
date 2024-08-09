/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.dto.Subscription;
import com.mojang.realmsclient.exception.RealmsServiceException;
import com.mojang.realmsclient.gui.screens.RealmsGenericErrorScreen;
import com.mojang.realmsclient.gui.screens.RealmsLongConfirmationScreen;
import java.text.DateFormat;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.realms.RealmsNarratorHelper;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsSubscriptionInfoScreen
extends RealmsScreen {
    private static final Logger field_224579_a = LogManager.getLogger();
    private static final ITextComponent field_243173_b = new TranslationTextComponent("mco.configure.world.subscription.title");
    private static final ITextComponent field_243174_c = new TranslationTextComponent("mco.configure.world.subscription.start");
    private static final ITextComponent field_243175_p = new TranslationTextComponent("mco.configure.world.subscription.timeleft");
    private static final ITextComponent field_243176_q = new TranslationTextComponent("mco.configure.world.subscription.recurring.daysleft");
    private static final ITextComponent field_243177_r = new TranslationTextComponent("mco.configure.world.subscription.expired");
    private static final ITextComponent field_243178_s = new TranslationTextComponent("mco.configure.world.subscription.less_than_a_day");
    private static final ITextComponent field_243179_t = new TranslationTextComponent("mco.configure.world.subscription.month");
    private static final ITextComponent field_243180_u = new TranslationTextComponent("mco.configure.world.subscription.months");
    private static final ITextComponent field_243181_v = new TranslationTextComponent("mco.configure.world.subscription.day");
    private static final ITextComponent field_243182_w = new TranslationTextComponent("mco.configure.world.subscription.days");
    private final Screen field_224580_b;
    private final RealmsServer field_224581_c;
    private final Screen field_224582_d;
    private ITextComponent field_224590_l;
    private String field_224591_m;
    private Subscription.Type field_224592_n;

    public RealmsSubscriptionInfoScreen(Screen screen, RealmsServer realmsServer, Screen screen2) {
        this.field_224580_b = screen;
        this.field_224581_c = realmsServer;
        this.field_224582_d = screen2;
    }

    @Override
    public void init() {
        this.func_224573_a(this.field_224581_c.field_230582_a_);
        RealmsNarratorHelper.func_239551_a_(field_243173_b.getString(), field_243174_c.getString(), this.field_224591_m, field_243175_p.getString(), this.field_224590_l.getString());
        this.minecraft.keyboardListener.enableRepeatEvents(false);
        this.addButton(new Button(this.width / 2 - 100, RealmsSubscriptionInfoScreen.func_239562_k_(6), 200, 20, new TranslationTextComponent("mco.configure.world.subscription.extend"), this::lambda$init$0));
        this.addButton(new Button(this.width / 2 - 100, RealmsSubscriptionInfoScreen.func_239562_k_(12), 200, 20, DialogTexts.GUI_BACK, this::lambda$init$1));
        if (this.field_224581_c.field_230591_j_) {
            this.addButton(new Button(this.width / 2 - 100, RealmsSubscriptionInfoScreen.func_239562_k_(10), 200, 20, new TranslationTextComponent("mco.configure.world.delete.button"), this::lambda$init$2));
        }
    }

    private void func_238074_c_(boolean bl) {
        if (bl) {
            new Thread(this, "Realms-delete-realm"){
                final RealmsSubscriptionInfoScreen this$0;
                {
                    this.this$0 = realmsSubscriptionInfoScreen;
                    super(string);
                }

                @Override
                public void run() {
                    try {
                        RealmsClient realmsClient = RealmsClient.func_224911_a();
                        realmsClient.func_224916_h(this.this$0.field_224581_c.field_230582_a_);
                    } catch (RealmsServiceException realmsServiceException) {
                        field_224579_a.error("Couldn't delete world");
                        field_224579_a.error(realmsServiceException);
                    }
                    RealmsSubscriptionInfoScreen.access$000(this.this$0).execute(this::lambda$run$0);
                }

                private void lambda$run$0() {
                    RealmsSubscriptionInfoScreen.access$100(this.this$0).displayGuiScreen(this.this$0.field_224582_d);
                }
            }.start();
        }
        this.minecraft.displayGuiScreen(this);
    }

    private void func_224573_a(long l) {
        RealmsClient realmsClient = RealmsClient.func_224911_a();
        try {
            Subscription subscription = realmsClient.func_224933_g(l);
            this.field_224590_l = this.func_224576_a(subscription.field_230635_b_);
            this.field_224591_m = RealmsSubscriptionInfoScreen.func_224574_b(subscription.field_230634_a_);
            this.field_224592_n = subscription.field_230636_c_;
        } catch (RealmsServiceException realmsServiceException) {
            field_224579_a.error("Couldn't get subscription");
            this.minecraft.displayGuiScreen(new RealmsGenericErrorScreen(realmsServiceException, this.field_224580_b));
        }
    }

    private static String func_224574_b(long l) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar(TimeZone.getDefault());
        gregorianCalendar.setTimeInMillis(l);
        return DateFormat.getDateTimeInstance().format(gregorianCalendar.getTime());
    }

    @Override
    public void onClose() {
        this.minecraft.keyboardListener.enableRepeatEvents(true);
    }

    @Override
    public boolean keyPressed(int n, int n2, int n3) {
        if (n == 256) {
            this.minecraft.displayGuiScreen(this.field_224580_b);
            return false;
        }
        return super.keyPressed(n, n2, n3);
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.renderBackground(matrixStack);
        int n3 = this.width / 2 - 100;
        RealmsSubscriptionInfoScreen.drawCenteredString(matrixStack, this.font, field_243173_b, this.width / 2, 17, 0xFFFFFF);
        this.font.func_243248_b(matrixStack, field_243174_c, n3, RealmsSubscriptionInfoScreen.func_239562_k_(0), 0xA0A0A0);
        this.font.drawString(matrixStack, this.field_224591_m, n3, RealmsSubscriptionInfoScreen.func_239562_k_(1), 0xFFFFFF);
        if (this.field_224592_n == Subscription.Type.NORMAL) {
            this.font.func_243248_b(matrixStack, field_243175_p, n3, RealmsSubscriptionInfoScreen.func_239562_k_(3), 0xA0A0A0);
        } else if (this.field_224592_n == Subscription.Type.RECURRING) {
            this.font.func_243248_b(matrixStack, field_243176_q, n3, RealmsSubscriptionInfoScreen.func_239562_k_(3), 0xA0A0A0);
        }
        this.font.func_243248_b(matrixStack, this.field_224590_l, n3, RealmsSubscriptionInfoScreen.func_239562_k_(4), 0xFFFFFF);
        super.render(matrixStack, n, n2, f);
    }

    private ITextComponent func_224576_a(int n) {
        if (n < 0 && this.field_224581_c.field_230591_j_) {
            return field_243177_r;
        }
        if (n <= 1) {
            return field_243178_s;
        }
        int n2 = n / 30;
        int n3 = n % 30;
        StringTextComponent stringTextComponent = new StringTextComponent("");
        if (n2 > 0) {
            stringTextComponent.appendString(Integer.toString(n2)).appendString(" ");
            if (n2 == 1) {
                stringTextComponent.append(field_243179_t);
            } else {
                stringTextComponent.append(field_243180_u);
            }
        }
        if (n3 > 0) {
            if (n2 > 0) {
                stringTextComponent.appendString(", ");
            }
            stringTextComponent.appendString(Integer.toString(n3)).appendString(" ");
            if (n3 == 1) {
                stringTextComponent.append(field_243181_v);
            } else {
                stringTextComponent.append(field_243182_w);
            }
        }
        return stringTextComponent;
    }

    private void lambda$init$2(Button button) {
        TranslationTextComponent translationTextComponent = new TranslationTextComponent("mco.configure.world.delete.question.line1");
        TranslationTextComponent translationTextComponent2 = new TranslationTextComponent("mco.configure.world.delete.question.line2");
        this.minecraft.displayGuiScreen(new RealmsLongConfirmationScreen(this::func_238074_c_, RealmsLongConfirmationScreen.Type.Warning, translationTextComponent, translationTextComponent2, true));
    }

    private void lambda$init$1(Button button) {
        this.minecraft.displayGuiScreen(this.field_224580_b);
    }

    private void lambda$init$0(Button button) {
        String string = "https://aka.ms/ExtendJavaRealms?subscriptionId=" + this.field_224581_c.field_230583_b_ + "&profileId=" + this.minecraft.getSession().getPlayerID();
        this.minecraft.keyboardListener.setClipboardString(string);
        Util.getOSType().openURI(string);
    }

    static Minecraft access$000(RealmsSubscriptionInfoScreen realmsSubscriptionInfoScreen) {
        return realmsSubscriptionInfoScreen.minecraft;
    }

    static Minecraft access$100(RealmsSubscriptionInfoScreen realmsSubscriptionInfoScreen) {
        return realmsSubscriptionInfoScreen.minecraft;
    }
}


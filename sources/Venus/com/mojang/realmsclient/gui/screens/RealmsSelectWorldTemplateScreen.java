/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.realmsclient.gui.screens;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Either;
import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.dto.WorldTemplate;
import com.mojang.realmsclient.dto.WorldTemplatePaginatedList;
import com.mojang.realmsclient.exception.RealmsServiceException;
import com.mojang.realmsclient.gui.screens.NotifableRealmsScreen;
import com.mojang.realmsclient.util.RealmsTextureManager;
import com.mojang.realmsclient.util.TextRenderingUtils;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.list.AbstractList;
import net.minecraft.client.gui.widget.list.ExtendedList;
import net.minecraft.client.resources.I18n;
import net.minecraft.realms.RealmsNarratorHelper;
import net.minecraft.realms.RealmsObjectSelectionList;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsSelectWorldTemplateScreen
extends RealmsScreen {
    private static final Logger field_224515_a = LogManager.getLogger();
    private static final ResourceLocation field_237987_b_ = new ResourceLocation("realms", "textures/gui/realms/link_icons.png");
    private static final ResourceLocation field_237988_c_ = new ResourceLocation("realms", "textures/gui/realms/trailer_icons.png");
    private static final ResourceLocation field_237989_p_ = new ResourceLocation("realms", "textures/gui/realms/slot_frame.png");
    private static final ITextComponent field_243163_q = new TranslationTextComponent("mco.template.info.tooltip");
    private static final ITextComponent field_243164_r = new TranslationTextComponent("mco.template.trailer.tooltip");
    private final NotifableRealmsScreen field_224516_b;
    private WorldTemplateSelectionList field_224517_c;
    private int field_224518_d = -1;
    private ITextComponent field_224519_e;
    private Button field_224520_f;
    private Button field_224521_g;
    private Button field_224522_h;
    @Nullable
    private ITextComponent field_224523_i;
    private String field_224524_j;
    private final RealmsServer.ServerType field_224525_k;
    private int field_224526_l;
    @Nullable
    private ITextComponent[] field_224527_m;
    private String field_224528_n;
    private boolean field_224529_o;
    private boolean field_224530_p;
    @Nullable
    private List<TextRenderingUtils.Line> field_224531_q;

    public RealmsSelectWorldTemplateScreen(NotifableRealmsScreen notifableRealmsScreen, RealmsServer.ServerType serverType) {
        this(notifableRealmsScreen, serverType, null);
    }

    public RealmsSelectWorldTemplateScreen(NotifableRealmsScreen notifableRealmsScreen, RealmsServer.ServerType serverType, @Nullable WorldTemplatePaginatedList worldTemplatePaginatedList) {
        this.field_224516_b = notifableRealmsScreen;
        this.field_224525_k = serverType;
        if (worldTemplatePaginatedList == null) {
            this.field_224517_c = new WorldTemplateSelectionList(this);
            this.func_224497_a(new WorldTemplatePaginatedList(10));
        } else {
            this.field_224517_c = new WorldTemplateSelectionList(this, Lists.newArrayList(worldTemplatePaginatedList.field_230657_a_));
            this.func_224497_a(worldTemplatePaginatedList);
        }
        this.field_224519_e = new TranslationTextComponent("mco.template.title");
    }

    public void func_238001_a_(ITextComponent iTextComponent) {
        this.field_224519_e = iTextComponent;
    }

    public void func_238002_a_(ITextComponent ... iTextComponentArray) {
        this.field_224527_m = iTextComponentArray;
        this.field_224529_o = true;
    }

    @Override
    public boolean mouseClicked(double d, double d2, int n) {
        if (this.field_224530_p && this.field_224528_n != null) {
            Util.getOSType().openURI("https://www.minecraft.net/realms/adventure-maps-in-1-9");
            return false;
        }
        return super.mouseClicked(d, d2, n);
    }

    @Override
    public void init() {
        this.minecraft.keyboardListener.enableRepeatEvents(false);
        this.field_224517_c = new WorldTemplateSelectionList(this, this.field_224517_c.func_223879_b());
        this.field_224521_g = this.addButton(new Button(this.width / 2 - 206, this.height - 32, 100, 20, new TranslationTextComponent("mco.template.button.trailer"), this::lambda$init$0));
        this.field_224520_f = this.addButton(new Button(this.width / 2 - 100, this.height - 32, 100, 20, new TranslationTextComponent("mco.template.button.select"), this::lambda$init$1));
        ITextComponent iTextComponent = this.field_224525_k == RealmsServer.ServerType.MINIGAME ? DialogTexts.GUI_CANCEL : DialogTexts.GUI_BACK;
        Button button = new Button(this.width / 2 + 6, this.height - 32, 100, 20, iTextComponent, this::lambda$init$2);
        this.addButton(button);
        this.field_224522_h = this.addButton(new Button(this.width / 2 + 112, this.height - 32, 100, 20, new TranslationTextComponent("mco.template.button.publisher"), this::lambda$init$3));
        this.field_224520_f.active = false;
        this.field_224521_g.visible = false;
        this.field_224522_h.visible = false;
        this.addListener(this.field_224517_c);
        this.setListenerDefault(this.field_224517_c);
        Stream<ITextComponent> stream = Stream.of(this.field_224519_e);
        if (this.field_224527_m != null) {
            stream = Stream.concat(Stream.of(this.field_224527_m), stream);
        }
        RealmsNarratorHelper.func_239549_a_(stream.filter(Objects::nonNull).map(ITextComponent::getString).collect(Collectors.toList()));
    }

    private void func_224514_b() {
        this.field_224522_h.visible = this.func_224510_d();
        this.field_224521_g.visible = this.func_224512_f();
        this.field_224520_f.active = this.func_224495_c();
    }

    private boolean func_224495_c() {
        return this.field_224518_d != -1;
    }

    private boolean func_224510_d() {
        return this.field_224518_d != -1 && !this.func_224487_e().field_230651_e_.isEmpty();
    }

    private WorldTemplate func_224487_e() {
        return this.field_224517_c.func_223877_a(this.field_224518_d);
    }

    private boolean func_224512_f() {
        return this.field_224518_d != -1 && !this.func_224487_e().field_230653_g_.isEmpty();
    }

    @Override
    public void tick() {
        super.tick();
        --this.field_224526_l;
        if (this.field_224526_l < 0) {
            this.field_224526_l = 0;
        }
    }

    @Override
    public boolean keyPressed(int n, int n2, int n3) {
        if (n == 256) {
            this.func_224484_g();
            return false;
        }
        return super.keyPressed(n, n2, n3);
    }

    private void func_224484_g() {
        this.field_224516_b.func_223627_a_(null);
        this.minecraft.displayGuiScreen(this.field_224516_b);
    }

    private void func_224500_h() {
        if (this.func_238024_y_()) {
            this.field_224516_b.func_223627_a_(this.func_224487_e());
        }
    }

    private boolean func_238024_y_() {
        return this.field_224518_d >= 0 && this.field_224518_d < this.field_224517_c.getItemCount();
    }

    private void func_224496_i() {
        if (this.func_238024_y_()) {
            WorldTemplate worldTemplate = this.func_224487_e();
            if (!"".equals(worldTemplate.field_230653_g_)) {
                Util.getOSType().openURI(worldTemplate.field_230653_g_);
            }
        }
    }

    private void func_224511_j() {
        if (this.func_238024_y_()) {
            WorldTemplate worldTemplate = this.func_224487_e();
            if (!"".equals(worldTemplate.field_230651_e_)) {
                Util.getOSType().openURI(worldTemplate.field_230651_e_);
            }
        }
    }

    private void func_224497_a(WorldTemplatePaginatedList worldTemplatePaginatedList) {
        new Thread(this, "realms-template-fetcher", worldTemplatePaginatedList){
            final WorldTemplatePaginatedList val$p_224497_1_;
            final RealmsSelectWorldTemplateScreen this$0;
            {
                this.this$0 = realmsSelectWorldTemplateScreen;
                this.val$p_224497_1_ = worldTemplatePaginatedList;
                super(string);
            }

            @Override
            public void run() {
                WorldTemplatePaginatedList worldTemplatePaginatedList = this.val$p_224497_1_;
                RealmsClient realmsClient = RealmsClient.func_224911_a();
                while (worldTemplatePaginatedList != null) {
                    Either<WorldTemplatePaginatedList, String> either = this.this$0.func_224509_a(worldTemplatePaginatedList, realmsClient);
                    worldTemplatePaginatedList = RealmsSelectWorldTemplateScreen.access$000(this.this$0).supplyAsync(() -> this.lambda$run$0(either)).join();
                }
            }

            private WorldTemplatePaginatedList lambda$run$0(Either either) {
                if (either.right().isPresent()) {
                    field_224515_a.error("Couldn't fetch templates: {}", either.right().get());
                    if (this.this$0.field_224517_c.func_223878_a()) {
                        this.this$0.field_224531_q = TextRenderingUtils.func_225224_a(I18n.format("mco.template.select.failure", new Object[0]), new TextRenderingUtils.LineSegment[0]);
                    }
                    return null;
                }
                WorldTemplatePaginatedList worldTemplatePaginatedList = (WorldTemplatePaginatedList)either.left().get();
                for (WorldTemplate object : worldTemplatePaginatedList.field_230657_a_) {
                    this.this$0.field_224517_c.func_223876_a(object);
                }
                if (worldTemplatePaginatedList.field_230657_a_.isEmpty()) {
                    if (this.this$0.field_224517_c.func_223878_a()) {
                        String string = I18n.format("mco.template.select.none", "%link");
                        TextRenderingUtils.LineSegment lineSegment = TextRenderingUtils.LineSegment.func_225214_a(I18n.format("mco.template.select.none.linkTitle", new Object[0]), "https://aka.ms/MinecraftRealmsContentCreator");
                        this.this$0.field_224531_q = TextRenderingUtils.func_225224_a(string, lineSegment);
                    }
                    return null;
                }
                return worldTemplatePaginatedList;
            }
        }.start();
    }

    private Either<WorldTemplatePaginatedList, String> func_224509_a(WorldTemplatePaginatedList worldTemplatePaginatedList, RealmsClient realmsClient) {
        try {
            return Either.left(realmsClient.func_224930_a(worldTemplatePaginatedList.field_230658_b_ + 1, worldTemplatePaginatedList.field_230659_c_, this.field_224525_k));
        } catch (RealmsServiceException realmsServiceException) {
            return Either.right(realmsServiceException.getMessage());
        }
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.field_224523_i = null;
        this.field_224524_j = null;
        this.field_224530_p = false;
        this.renderBackground(matrixStack);
        this.field_224517_c.render(matrixStack, n, n2, f);
        if (this.field_224531_q != null) {
            this.func_237992_a_(matrixStack, n, n2, this.field_224531_q);
        }
        RealmsSelectWorldTemplateScreen.drawCenteredString(matrixStack, this.font, this.field_224519_e, this.width / 2, 13, 0xFFFFFF);
        if (this.field_224529_o) {
            int n3;
            int n4;
            ITextComponent[] iTextComponentArray = this.field_224527_m;
            for (n4 = 0; n4 < iTextComponentArray.length; ++n4) {
                int n5 = this.font.getStringPropertyWidth(iTextComponentArray[n4]);
                n3 = this.width / 2 - n5 / 2;
                int n6 = RealmsSelectWorldTemplateScreen.func_239562_k_(-1 + n4);
                if (n < n3 || n > n3 + n5 || n2 < n6 || n2 > n6 + 9) continue;
                this.field_224530_p = true;
            }
            for (n4 = 0; n4 < iTextComponentArray.length; ++n4) {
                ITextComponent iTextComponent = iTextComponentArray[n4];
                n3 = 0xA0A0A0;
                if (this.field_224528_n != null) {
                    if (this.field_224530_p) {
                        n3 = 7107012;
                        iTextComponent = iTextComponent.deepCopy().mergeStyle(TextFormatting.STRIKETHROUGH);
                    } else {
                        n3 = 0x3366BB;
                    }
                }
                RealmsSelectWorldTemplateScreen.drawCenteredString(matrixStack, this.font, iTextComponent, this.width / 2, RealmsSelectWorldTemplateScreen.func_239562_k_(-1 + n4), n3);
            }
        }
        super.render(matrixStack, n, n2, f);
        this.func_237993_a_(matrixStack, this.field_224523_i, n, n2);
    }

    private void func_237992_a_(MatrixStack matrixStack, int n, int n2, List<TextRenderingUtils.Line> list) {
        for (int i = 0; i < list.size(); ++i) {
            TextRenderingUtils.Line line = list.get(i);
            int n3 = RealmsSelectWorldTemplateScreen.func_239562_k_(4 + i);
            int n4 = line.field_225213_a.stream().mapToInt(this::lambda$func_237992_a_$4).sum();
            int n5 = this.width / 2 - n4 / 2;
            for (TextRenderingUtils.LineSegment lineSegment : line.field_225213_a) {
                int n6 = lineSegment.func_225217_b() ? 0x3366BB : 0xFFFFFF;
                int n7 = this.font.drawStringWithShadow(matrixStack, lineSegment.func_225215_a(), n5, n3, n6);
                if (lineSegment.func_225217_b() && n > n5 && n < n7 && n2 > n3 - 3 && n2 < n3 + 8) {
                    this.field_224523_i = new StringTextComponent(lineSegment.func_225216_c());
                    this.field_224524_j = lineSegment.func_225216_c();
                }
                n5 = n7;
            }
        }
    }

    protected void func_237993_a_(MatrixStack matrixStack, @Nullable ITextComponent iTextComponent, int n, int n2) {
        if (iTextComponent != null) {
            int n3 = n + 12;
            int n4 = n2 - 12;
            int n5 = this.font.getStringPropertyWidth(iTextComponent);
            this.fillGradient(matrixStack, n3 - 3, n4 - 3, n3 + n5 + 3, n4 + 8 + 3, -1073741824, -1073741824);
            this.font.func_243246_a(matrixStack, iTextComponent, n3, n4, 0xFFFFFF);
        }
    }

    private int lambda$func_237992_a_$4(TextRenderingUtils.LineSegment lineSegment) {
        return this.font.getStringWidth(lineSegment.func_225215_a());
    }

    private void lambda$init$3(Button button) {
        this.func_224511_j();
    }

    private void lambda$init$2(Button button) {
        this.func_224484_g();
    }

    private void lambda$init$1(Button button) {
        this.func_224500_h();
    }

    private void lambda$init$0(Button button) {
        this.func_224496_i();
    }

    static Minecraft access$000(RealmsSelectWorldTemplateScreen realmsSelectWorldTemplateScreen) {
        return realmsSelectWorldTemplateScreen.minecraft;
    }

    static FontRenderer access$100(RealmsSelectWorldTemplateScreen realmsSelectWorldTemplateScreen) {
        return realmsSelectWorldTemplateScreen.font;
    }

    static FontRenderer access$200(RealmsSelectWorldTemplateScreen realmsSelectWorldTemplateScreen) {
        return realmsSelectWorldTemplateScreen.font;
    }

    static FontRenderer access$300(RealmsSelectWorldTemplateScreen realmsSelectWorldTemplateScreen) {
        return realmsSelectWorldTemplateScreen.font;
    }

    static FontRenderer access$400(RealmsSelectWorldTemplateScreen realmsSelectWorldTemplateScreen) {
        return realmsSelectWorldTemplateScreen.font;
    }

    static Minecraft access$500(RealmsSelectWorldTemplateScreen realmsSelectWorldTemplateScreen) {
        return realmsSelectWorldTemplateScreen.minecraft;
    }

    static FontRenderer access$600(RealmsSelectWorldTemplateScreen realmsSelectWorldTemplateScreen) {
        return realmsSelectWorldTemplateScreen.font;
    }

    static FontRenderer access$700(RealmsSelectWorldTemplateScreen realmsSelectWorldTemplateScreen) {
        return realmsSelectWorldTemplateScreen.font;
    }

    static Minecraft access$800(RealmsSelectWorldTemplateScreen realmsSelectWorldTemplateScreen) {
        return realmsSelectWorldTemplateScreen.minecraft;
    }

    static Minecraft access$900(RealmsSelectWorldTemplateScreen realmsSelectWorldTemplateScreen) {
        return realmsSelectWorldTemplateScreen.minecraft;
    }

    static int access$1000(int n) {
        return RealmsSelectWorldTemplateScreen.func_239562_k_(n);
    }

    class WorldTemplateSelectionList
    extends RealmsObjectSelectionList<WorldTemplateSelectionEntry> {
        final RealmsSelectWorldTemplateScreen this$0;

        public WorldTemplateSelectionList(RealmsSelectWorldTemplateScreen realmsSelectWorldTemplateScreen) {
            this(realmsSelectWorldTemplateScreen, Collections.emptyList());
        }

        public WorldTemplateSelectionList(RealmsSelectWorldTemplateScreen realmsSelectWorldTemplateScreen, Iterable<WorldTemplate> iterable) {
            this.this$0 = realmsSelectWorldTemplateScreen;
            super(realmsSelectWorldTemplateScreen.width, realmsSelectWorldTemplateScreen.height, realmsSelectWorldTemplateScreen.field_224529_o ? RealmsSelectWorldTemplateScreen.access$1000(1) : 32, realmsSelectWorldTemplateScreen.height - 40, 46);
            iterable.forEach(this::func_223876_a);
        }

        public void func_223876_a(WorldTemplate worldTemplate) {
            RealmsSelectWorldTemplateScreen realmsSelectWorldTemplateScreen = this.this$0;
            Objects.requireNonNull(realmsSelectWorldTemplateScreen);
            this.addEntry(new WorldTemplateSelectionEntry(realmsSelectWorldTemplateScreen, worldTemplate));
        }

        @Override
        public boolean mouseClicked(double d, double d2, int n) {
            if (n == 0 && d2 >= (double)this.y0 && d2 <= (double)this.y1) {
                int n2 = this.width / 2 - 150;
                if (this.this$0.field_224524_j != null) {
                    Util.getOSType().openURI(this.this$0.field_224524_j);
                }
                int n3 = (int)Math.floor(d2 - (double)this.y0) - this.headerHeight + (int)this.getScrollAmount() - 4;
                int n4 = n3 / this.itemHeight;
                if (d >= (double)n2 && d < (double)this.getScrollbarPosition() && n4 >= 0 && n3 >= 0 && n4 < this.getItemCount()) {
                    this.func_231400_a_(n4);
                    this.func_231401_a_(n3, n4, d, d2, this.width);
                    if (n4 >= this.this$0.field_224517_c.getItemCount()) {
                        return super.mouseClicked(d, d2, n);
                    }
                    this.this$0.field_224526_l += 7;
                    if (this.this$0.field_224526_l >= 10) {
                        this.this$0.func_224500_h();
                    }
                    return false;
                }
            }
            return super.mouseClicked(d, d2, n);
        }

        @Override
        public void func_231400_a_(int n) {
            this.func_239561_k_(n);
            if (n != -1) {
                WorldTemplate worldTemplate = this.this$0.field_224517_c.func_223877_a(n);
                String string = I18n.format("narrator.select.list.position", n + 1, this.this$0.field_224517_c.getItemCount());
                String string2 = I18n.format("mco.template.select.narrate.version", worldTemplate.field_230649_c_);
                String string3 = I18n.format("mco.template.select.narrate.authors", worldTemplate.field_230650_d_);
                String string4 = RealmsNarratorHelper.func_239552_b_(Arrays.asList(worldTemplate.field_230648_b_, string3, worldTemplate.field_230654_h_, string2, string));
                RealmsNarratorHelper.func_239550_a_(I18n.format("narrator.select", string4));
            }
        }

        @Override
        public void setSelected(@Nullable WorldTemplateSelectionEntry worldTemplateSelectionEntry) {
            super.setSelected(worldTemplateSelectionEntry);
            this.this$0.field_224518_d = this.getEventListeners().indexOf(worldTemplateSelectionEntry);
            this.this$0.func_224514_b();
        }

        @Override
        public int getMaxPosition() {
            return this.getItemCount() * 46;
        }

        @Override
        public int getRowWidth() {
            return 1;
        }

        @Override
        public void renderBackground(MatrixStack matrixStack) {
            this.this$0.renderBackground(matrixStack);
        }

        @Override
        public boolean isFocused() {
            return this.this$0.getListener() == this;
        }

        public boolean func_223878_a() {
            return this.getItemCount() == 0;
        }

        public WorldTemplate func_223877_a(int n) {
            return ((WorldTemplateSelectionEntry)this.getEventListeners().get((int)n)).field_223756_a;
        }

        public List<WorldTemplate> func_223879_b() {
            return this.getEventListeners().stream().map(WorldTemplateSelectionList::lambda$func_223879_b$0).collect(Collectors.toList());
        }

        @Override
        public void setSelected(@Nullable AbstractList.AbstractListEntry abstractListEntry) {
            this.setSelected((WorldTemplateSelectionEntry)abstractListEntry);
        }

        private static WorldTemplate lambda$func_223879_b$0(WorldTemplateSelectionEntry worldTemplateSelectionEntry) {
            return worldTemplateSelectionEntry.field_223756_a;
        }
    }

    class WorldTemplateSelectionEntry
    extends ExtendedList.AbstractListEntry<WorldTemplateSelectionEntry> {
        private final WorldTemplate field_223756_a;
        final RealmsSelectWorldTemplateScreen this$0;

        public WorldTemplateSelectionEntry(RealmsSelectWorldTemplateScreen realmsSelectWorldTemplateScreen, WorldTemplate worldTemplate) {
            this.this$0 = realmsSelectWorldTemplateScreen;
            this.field_223756_a = worldTemplate;
        }

        @Override
        public void render(MatrixStack matrixStack, int n, int n2, int n3, int n4, int n5, int n6, int n7, boolean bl, float f) {
            this.func_238029_a_(matrixStack, this.field_223756_a, n3, n2, n6, n7);
        }

        private void func_238029_a_(MatrixStack matrixStack, WorldTemplate worldTemplate, int n, int n2, int n3, int n4) {
            int n5 = n + 45 + 20;
            RealmsSelectWorldTemplateScreen.access$100(this.this$0).drawString(matrixStack, worldTemplate.field_230648_b_, n5, n2 + 2, 0xFFFFFF);
            RealmsSelectWorldTemplateScreen.access$200(this.this$0).drawString(matrixStack, worldTemplate.field_230650_d_, n5, n2 + 15, 0x6C6C6C);
            RealmsSelectWorldTemplateScreen.access$400(this.this$0).drawString(matrixStack, worldTemplate.field_230649_c_, n5 + 227 - RealmsSelectWorldTemplateScreen.access$300(this.this$0).getStringWidth(worldTemplate.field_230649_c_), n2 + 1, 0x6C6C6C);
            if (!("".equals(worldTemplate.field_230651_e_) && "".equals(worldTemplate.field_230653_g_) && "".equals(worldTemplate.field_230654_h_))) {
                this.func_238028_a_(matrixStack, n5 - 1, n2 + 25, n3, n4, worldTemplate.field_230651_e_, worldTemplate.field_230653_g_, worldTemplate.field_230654_h_);
            }
            this.func_238027_a_(matrixStack, n, n2 + 1, n3, n4, worldTemplate);
        }

        private void func_238027_a_(MatrixStack matrixStack, int n, int n2, int n3, int n4, WorldTemplate worldTemplate) {
            RealmsTextureManager.func_225202_a(worldTemplate.field_230647_a_, worldTemplate.field_230652_f_);
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            AbstractGui.blit(matrixStack, n + 1, n2 + 1, 0.0f, 0.0f, 38, 38, 38, 38);
            RealmsSelectWorldTemplateScreen.access$500(this.this$0).getTextureManager().bindTexture(field_237989_p_);
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            AbstractGui.blit(matrixStack, n, n2, 0.0f, 0.0f, 40, 40, 40, 40);
        }

        private void func_238028_a_(MatrixStack matrixStack, int n, int n2, int n3, int n4, String string, String string2, String string3) {
            if (!"".equals(string3)) {
                RealmsSelectWorldTemplateScreen.access$600(this.this$0).drawString(matrixStack, string3, n, n2 + 4, 0x4C4C4C);
            }
            int n5 = "".equals(string3) ? 0 : RealmsSelectWorldTemplateScreen.access$700(this.this$0).getStringWidth(string3) + 2;
            boolean bl = false;
            boolean bl2 = false;
            boolean bl3 = "".equals(string);
            if (n3 >= n + n5 && n3 <= n + n5 + 32 && n4 >= n2 && n4 <= n2 + 15 && n4 < this.this$0.height - 15 && n4 > 32) {
                if (n3 <= n + 15 + n5 && n3 > n5) {
                    if (bl3) {
                        bl2 = true;
                    } else {
                        bl = true;
                    }
                } else if (!bl3) {
                    bl2 = true;
                }
            }
            if (!bl3) {
                RealmsSelectWorldTemplateScreen.access$800(this.this$0).getTextureManager().bindTexture(field_237987_b_);
                RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
                RenderSystem.pushMatrix();
                RenderSystem.scalef(1.0f, 1.0f, 1.0f);
                float f = bl ? 15.0f : 0.0f;
                AbstractGui.blit(matrixStack, n + n5, n2, f, 0.0f, 15, 15, 30, 15);
                RenderSystem.popMatrix();
            }
            if (!"".equals(string2)) {
                RealmsSelectWorldTemplateScreen.access$900(this.this$0).getTextureManager().bindTexture(field_237988_c_);
                RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
                RenderSystem.pushMatrix();
                RenderSystem.scalef(1.0f, 1.0f, 1.0f);
                int n6 = n + n5 + (bl3 ? 0 : 17);
                float f = bl2 ? 15.0f : 0.0f;
                AbstractGui.blit(matrixStack, n6, n2, f, 0.0f, 15, 15, 30, 15);
                RenderSystem.popMatrix();
            }
            if (bl) {
                this.this$0.field_224523_i = field_243163_q;
                this.this$0.field_224524_j = string;
            } else if (bl2 && !"".equals(string2)) {
                this.this$0.field_224523_i = field_243164_r;
                this.this$0.field_224524_j = string2;
            }
        }
    }
}


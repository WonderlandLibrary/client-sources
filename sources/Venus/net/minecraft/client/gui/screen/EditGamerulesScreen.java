/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.list.AbstractOptionList;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.GameRules;

public class EditGamerulesScreen
extends Screen {
    private final Consumer<Optional<GameRules>> field_238965_a_;
    private GamerulesList field_238966_b_;
    private final Set<Gamerule> field_238967_c_ = Sets.newHashSet();
    private Button field_238968_p_;
    @Nullable
    private List<IReorderingProcessor> field_238969_q_;
    private final GameRules field_238970_r_;

    public EditGamerulesScreen(GameRules gameRules, Consumer<Optional<GameRules>> consumer) {
        super(new TranslationTextComponent("editGamerule.title"));
        this.field_238970_r_ = gameRules;
        this.field_238965_a_ = consumer;
    }

    @Override
    protected void init() {
        this.minecraft.keyboardListener.enableRepeatEvents(false);
        super.init();
        this.field_238966_b_ = new GamerulesList(this, this.field_238970_r_);
        this.children.add(this.field_238966_b_);
        this.addButton(new Button(this.width / 2 - 155 + 160, this.height - 29, 150, 20, DialogTexts.GUI_CANCEL, this::lambda$init$0));
        this.field_238968_p_ = this.addButton(new Button(this.width / 2 - 155, this.height - 29, 150, 20, DialogTexts.GUI_DONE, this::lambda$init$1));
    }

    @Override
    public void onClose() {
        this.minecraft.keyboardListener.enableRepeatEvents(true);
    }

    @Override
    public void closeScreen() {
        this.field_238965_a_.accept(Optional.empty());
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.field_238969_q_ = null;
        this.field_238966_b_.render(matrixStack, n, n2, f);
        EditGamerulesScreen.drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 20, 0xFFFFFF);
        super.render(matrixStack, n, n2, f);
        if (this.field_238969_q_ != null) {
            this.renderTooltip(matrixStack, this.field_238969_q_, n, n2);
        }
    }

    private void func_238980_b_(@Nullable List<IReorderingProcessor> list) {
        this.field_238969_q_ = list;
    }

    private void func_238984_g_() {
        this.field_238968_p_.active = this.field_238967_c_.isEmpty();
    }

    private void func_238972_a_(Gamerule gamerule) {
        this.field_238967_c_.add(gamerule);
        this.func_238984_g_();
    }

    private void func_238977_b_(Gamerule gamerule) {
        this.field_238967_c_.remove(gamerule);
        this.func_238984_g_();
    }

    private void lambda$init$1(Button button) {
        this.field_238965_a_.accept(Optional.of(this.field_238970_r_));
    }

    private void lambda$init$0(Button button) {
        this.field_238965_a_.accept(Optional.empty());
    }

    public class GamerulesList
    extends AbstractOptionList<Gamerule> {
        final EditGamerulesScreen this$0;

        public GamerulesList(EditGamerulesScreen editGamerulesScreen, GameRules gameRules) {
            this.this$0 = editGamerulesScreen;
            super(editGamerulesScreen.minecraft, editGamerulesScreen.width, editGamerulesScreen.height, 43, editGamerulesScreen.height - 32, 24);
            HashMap hashMap = Maps.newHashMap();
            GameRules.visitAll(new GameRules.IRuleEntryVisitor(){
                final EditGamerulesScreen val$this$0;
                final GameRules val$p_i232316_2_;
                final Map val$map;
                final GamerulesList this$1;
                {
                    this.this$1 = gamerulesList;
                    this.val$this$0 = editGamerulesScreen;
                    this.val$p_i232316_2_ = gameRules;
                    this.val$map = map;
                }

                @Override
                public void changeBoolean(GameRules.RuleKey<GameRules.BooleanValue> ruleKey, GameRules.RuleType<GameRules.BooleanValue> ruleType) {
                    this.func_239011_a_(ruleKey, this::lambda$changeBoolean$0);
                }

                @Override
                public void changeInteger(GameRules.RuleKey<GameRules.IntegerValue> ruleKey, GameRules.RuleType<GameRules.IntegerValue> ruleType) {
                    this.func_239011_a_(ruleKey, this::lambda$changeInteger$1);
                }

                private <T extends GameRules.RuleValue<T>> void func_239011_a_(GameRules.RuleKey<T> ruleKey, IRuleEntry<T> iRuleEntry) {
                    Object object;
                    ImmutableCollection immutableCollection;
                    TranslationTextComponent translationTextComponent = new TranslationTextComponent(ruleKey.getLocaleString());
                    IFormattableTextComponent iFormattableTextComponent = new StringTextComponent(ruleKey.getName()).mergeStyle(TextFormatting.YELLOW);
                    T t = this.val$p_i232316_2_.get(ruleKey);
                    String string = ((GameRules.RuleValue)t).stringValue();
                    IFormattableTextComponent iFormattableTextComponent2 = new TranslationTextComponent("editGamerule.default", new StringTextComponent(string)).mergeStyle(TextFormatting.GRAY);
                    String string2 = ruleKey.getLocaleString() + ".description";
                    if (I18n.hasKey(string2)) {
                        ImmutableCollection.ArrayBasedBuilder arrayBasedBuilder = ImmutableList.builder().add(iFormattableTextComponent.func_241878_f());
                        TranslationTextComponent translationTextComponent2 = new TranslationTextComponent(string2);
                        this.this$1.this$0.font.trimStringToWidth(translationTextComponent2, 150).forEach(((ImmutableList.Builder)arrayBasedBuilder)::add);
                        immutableCollection = ((ImmutableList.Builder)((ImmutableList.Builder)arrayBasedBuilder).add(iFormattableTextComponent2.func_241878_f())).build();
                        object = translationTextComponent2.getString() + "\n" + iFormattableTextComponent2.getString();
                    } else {
                        immutableCollection = ImmutableList.of(iFormattableTextComponent.func_241878_f(), iFormattableTextComponent2.func_241878_f());
                        object = iFormattableTextComponent2.getString();
                    }
                    this.val$map.computeIfAbsent(ruleKey.getCategory(), 1::lambda$func_239011_a_$2).put(ruleKey, iRuleEntry.create(translationTextComponent, (List<IReorderingProcessor>)((Object)immutableCollection), (String)object, t));
                }

                private static Map lambda$func_239011_a_$2(GameRules.Category category) {
                    return Maps.newHashMap();
                }

                private Gamerule lambda$changeInteger$1(ITextComponent iTextComponent, List list, String string, GameRules.IntegerValue integerValue) {
                    EditGamerulesScreen editGamerulesScreen = this.this$1.this$0;
                    Objects.requireNonNull(editGamerulesScreen);
                    return new IntegerEntry(editGamerulesScreen, iTextComponent, list, string, integerValue);
                }

                private Gamerule lambda$changeBoolean$0(ITextComponent iTextComponent, List list, String string, GameRules.BooleanValue booleanValue) {
                    EditGamerulesScreen editGamerulesScreen = this.this$1.this$0;
                    Objects.requireNonNull(editGamerulesScreen);
                    return new BooleanEntry(editGamerulesScreen, iTextComponent, list, string, booleanValue);
                }
            });
            hashMap.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(this::lambda$new$1);
        }

        @Override
        public void render(MatrixStack matrixStack, int n, int n2, float f) {
            Gamerule gamerule;
            super.render(matrixStack, n, n2, f);
            if (this.isMouseOver(n, n2) && (gamerule = (Gamerule)this.getEntryAtPosition(n, n2)) != null) {
                this.this$0.func_238980_b_(gamerule.field_239000_a_);
            }
        }

        private void lambda$new$1(Map.Entry entry) {
            EditGamerulesScreen editGamerulesScreen = this.this$0;
            Objects.requireNonNull(editGamerulesScreen);
            this.addEntry(new NameEntry(editGamerulesScreen, new TranslationTextComponent(((GameRules.Category)((Object)entry.getKey())).getLocaleString()).mergeStyle(TextFormatting.BOLD, TextFormatting.YELLOW)));
            ((Map)entry.getValue()).entrySet().stream().sorted(Map.Entry.comparingByKey(Comparator.comparing(GameRules.RuleKey::getName))).forEach(this::lambda$new$0);
        }

        private void lambda$new$0(Map.Entry entry) {
            this.addEntry((Gamerule)entry.getValue());
        }
    }

    public abstract class ValueEntry
    extends Gamerule {
        private final List<IReorderingProcessor> field_241646_a_;
        protected final List<IGuiEventListener> field_241647_b_;
        final EditGamerulesScreen this$0;

        public ValueEntry(@Nullable EditGamerulesScreen editGamerulesScreen, List<IReorderingProcessor> list, ITextComponent iTextComponent) {
            this.this$0 = editGamerulesScreen;
            super(editGamerulesScreen, list);
            this.field_241647_b_ = Lists.newArrayList();
            this.field_241646_a_ = editGamerulesScreen.minecraft.fontRenderer.trimStringToWidth(iTextComponent, 175);
        }

        @Override
        public List<? extends IGuiEventListener> getEventListeners() {
            return this.field_241647_b_;
        }

        protected void func_241649_a_(MatrixStack matrixStack, int n, int n2) {
            if (this.field_241646_a_.size() == 1) {
                this.this$0.minecraft.fontRenderer.func_238422_b_(matrixStack, this.field_241646_a_.get(0), n2, n + 5, 0xFFFFFF);
            } else if (this.field_241646_a_.size() >= 2) {
                this.this$0.minecraft.fontRenderer.func_238422_b_(matrixStack, this.field_241646_a_.get(0), n2, n, 0xFFFFFF);
                this.this$0.minecraft.fontRenderer.func_238422_b_(matrixStack, this.field_241646_a_.get(1), n2, n + 10, 0xFFFFFF);
            }
        }
    }

    public class NameEntry
    extends Gamerule {
        private final ITextComponent field_238994_c_;
        final EditGamerulesScreen this$0;

        public NameEntry(EditGamerulesScreen editGamerulesScreen, ITextComponent iTextComponent) {
            this.this$0 = editGamerulesScreen;
            super(editGamerulesScreen, null);
            this.field_238994_c_ = iTextComponent;
        }

        @Override
        public void render(MatrixStack matrixStack, int n, int n2, int n3, int n4, int n5, int n6, int n7, boolean bl, float f) {
            AbstractGui.drawCenteredString(matrixStack, this.this$0.minecraft.fontRenderer, this.field_238994_c_, n3 + n4 / 2, n2 + 5, 0xFFFFFF);
        }

        @Override
        public List<? extends IGuiEventListener> getEventListeners() {
            return ImmutableList.of();
        }
    }

    public class IntegerEntry
    extends ValueEntry {
        private final TextFieldWidget field_238997_d_;
        final EditGamerulesScreen this$0;

        public IntegerEntry(EditGamerulesScreen editGamerulesScreen, ITextComponent iTextComponent, List<IReorderingProcessor> list, String string, GameRules.IntegerValue integerValue) {
            this.this$0 = editGamerulesScreen;
            super(editGamerulesScreen, list, iTextComponent);
            this.field_238997_d_ = new TextFieldWidget(editGamerulesScreen.minecraft.fontRenderer, 10, 5, 42, 20, iTextComponent.deepCopy().appendString("\n").appendString(string).appendString("\n"));
            this.field_238997_d_.setText(Integer.toString(integerValue.get()));
            this.field_238997_d_.setResponder(arg_0 -> this.lambda$new$0(integerValue, arg_0));
            this.field_241647_b_.add(this.field_238997_d_);
        }

        @Override
        public void render(MatrixStack matrixStack, int n, int n2, int n3, int n4, int n5, int n6, int n7, boolean bl, float f) {
            this.func_241649_a_(matrixStack, n2, n3);
            this.field_238997_d_.x = n3 + n4 - 44;
            this.field_238997_d_.y = n2;
            this.field_238997_d_.render(matrixStack, n6, n7, f);
        }

        private void lambda$new$0(GameRules.IntegerValue integerValue, String string) {
            if (integerValue.parseIntValue(string)) {
                this.field_238997_d_.setTextColor(0xE0E0E0);
                this.this$0.func_238977_b_(this);
            } else {
                this.field_238997_d_.setTextColor(0xFF0000);
                this.this$0.func_238972_a_(this);
            }
        }
    }

    @FunctionalInterface
    static interface IRuleEntry<T extends GameRules.RuleValue<T>> {
        public Gamerule create(ITextComponent var1, List<IReorderingProcessor> var2, String var3, T var4);
    }

    public abstract class Gamerule
    extends AbstractOptionList.Entry<Gamerule> {
        @Nullable
        private final List<IReorderingProcessor> field_239000_a_;
        final EditGamerulesScreen this$0;

        public Gamerule(@Nullable EditGamerulesScreen editGamerulesScreen, List<IReorderingProcessor> list) {
            this.this$0 = editGamerulesScreen;
            this.field_239000_a_ = list;
        }
    }

    public class BooleanEntry
    extends ValueEntry {
        private final Button field_238986_c_;
        final EditGamerulesScreen this$0;

        public BooleanEntry(EditGamerulesScreen editGamerulesScreen, ITextComponent iTextComponent, List<IReorderingProcessor> list, String string, GameRules.BooleanValue booleanValue) {
            this.this$0 = editGamerulesScreen;
            super(editGamerulesScreen, list, iTextComponent);
            this.field_238986_c_ = new Button(this, 10, 5, 44, 20, DialogTexts.optionsEnabled(booleanValue.get()), arg_0 -> BooleanEntry.lambda$new$0(booleanValue, arg_0), editGamerulesScreen, iTextComponent, booleanValue, string){
                final EditGamerulesScreen val$this$0;
                final ITextComponent val$p_i232311_2_;
                final GameRules.BooleanValue val$p_i232311_5_;
                final String val$p_i232311_4_;
                final BooleanEntry this$1;
                {
                    this.this$1 = booleanEntry;
                    this.val$this$0 = editGamerulesScreen;
                    this.val$p_i232311_2_ = iTextComponent2;
                    this.val$p_i232311_5_ = booleanValue;
                    this.val$p_i232311_4_ = string;
                    super(n, n2, n3, n4, iTextComponent, iPressable);
                }

                @Override
                protected IFormattableTextComponent getNarrationMessage() {
                    return DialogTexts.getComposedOptionMessage(this.val$p_i232311_2_, this.val$p_i232311_5_.get()).appendString("\n").appendString(this.val$p_i232311_4_);
                }
            };
            this.field_241647_b_.add(this.field_238986_c_);
        }

        @Override
        public void render(MatrixStack matrixStack, int n, int n2, int n3, int n4, int n5, int n6, int n7, boolean bl, float f) {
            this.func_241649_a_(matrixStack, n2, n3);
            this.field_238986_c_.x = n3 + n4 - 45;
            this.field_238986_c_.y = n2;
            this.field_238986_c_.render(matrixStack, n6, n7, f);
        }

        private static void lambda$new$0(GameRules.BooleanValue booleanValue, Button button) {
            boolean bl = !booleanValue.get();
            booleanValue.set(bl, null);
            button.setMessage(DialogTexts.optionsEnabled(booleanValue.get()));
        }
    }
}


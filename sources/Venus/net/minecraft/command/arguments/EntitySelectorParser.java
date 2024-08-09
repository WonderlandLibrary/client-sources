/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.arguments;

import com.google.common.primitives.Doubles;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import javax.annotation.Nullable;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.advancements.criterion.MinMaxBoundsWrapped;
import net.minecraft.command.arguments.EntityOptions;
import net.minecraft.command.arguments.EntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;

public class EntitySelectorParser {
    public static final SimpleCommandExceptionType INVALID_ENTITY_NAME_OR_UUID = new SimpleCommandExceptionType(new TranslationTextComponent("argument.entity.invalid"));
    public static final DynamicCommandExceptionType UNKNOWN_SELECTOR_TYPE = new DynamicCommandExceptionType(EntitySelectorParser::lambda$static$0);
    public static final SimpleCommandExceptionType SELECTOR_NOT_ALLOWED = new SimpleCommandExceptionType(new TranslationTextComponent("argument.entity.selector.not_allowed"));
    public static final SimpleCommandExceptionType SELECTOR_TYPE_MISSING = new SimpleCommandExceptionType(new TranslationTextComponent("argument.entity.selector.missing"));
    public static final SimpleCommandExceptionType EXPECTED_END_OF_OPTIONS = new SimpleCommandExceptionType(new TranslationTextComponent("argument.entity.options.unterminated"));
    public static final DynamicCommandExceptionType EXPECTED_VALUE_FOR_OPTION = new DynamicCommandExceptionType(EntitySelectorParser::lambda$static$1);
    public static final BiConsumer<Vector3d, List<? extends Entity>> ARBITRARY = EntitySelectorParser::lambda$static$2;
    public static final BiConsumer<Vector3d, List<? extends Entity>> NEAREST = EntitySelectorParser::lambda$static$4;
    public static final BiConsumer<Vector3d, List<? extends Entity>> FURTHEST = EntitySelectorParser::lambda$static$6;
    public static final BiConsumer<Vector3d, List<? extends Entity>> RANDOM = EntitySelectorParser::lambda$static$7;
    public static final BiFunction<SuggestionsBuilder, Consumer<SuggestionsBuilder>, CompletableFuture<Suggestions>> SUGGEST_NONE = EntitySelectorParser::lambda$static$8;
    private final StringReader reader;
    private final boolean hasPermission;
    private int limit;
    private boolean includeNonPlayers;
    private boolean currentWorldOnly;
    private MinMaxBounds.FloatBound distance = MinMaxBounds.FloatBound.UNBOUNDED;
    private MinMaxBounds.IntBound level = MinMaxBounds.IntBound.UNBOUNDED;
    @Nullable
    private Double x;
    @Nullable
    private Double y;
    @Nullable
    private Double z;
    @Nullable
    private Double dx;
    @Nullable
    private Double dy;
    @Nullable
    private Double dz;
    private MinMaxBoundsWrapped xRotation = MinMaxBoundsWrapped.UNBOUNDED;
    private MinMaxBoundsWrapped yRotation = MinMaxBoundsWrapped.UNBOUNDED;
    private Predicate<Entity> filter = EntitySelectorParser::lambda$new$9;
    private BiConsumer<Vector3d, List<? extends Entity>> sorter = ARBITRARY;
    private boolean self;
    @Nullable
    private String username;
    private int cursorStart;
    @Nullable
    private UUID uuid;
    private BiFunction<SuggestionsBuilder, Consumer<SuggestionsBuilder>, CompletableFuture<Suggestions>> suggestionHandler = SUGGEST_NONE;
    private boolean hasNameEquals;
    private boolean hasNameNotEquals;
    private boolean isLimited;
    private boolean isSorted;
    private boolean hasGamemodeEquals;
    private boolean hasGamemodeNotEquals;
    private boolean hasTeamEquals;
    private boolean hasTeamNotEquals;
    @Nullable
    private EntityType<?> type;
    private boolean typeInverse;
    private boolean hasScores;
    private boolean hasAdvancements;
    private boolean checkPermission;

    public EntitySelectorParser(StringReader stringReader) {
        this(stringReader, true);
    }

    public EntitySelectorParser(StringReader stringReader, boolean bl) {
        this.reader = stringReader;
        this.hasPermission = bl;
    }

    public EntitySelector build() {
        AxisAlignedBB axisAlignedBB;
        if (this.dx == null && this.dy == null && this.dz == null) {
            if (this.distance.getMax() != null) {
                float f = ((Float)this.distance.getMax()).floatValue();
                axisAlignedBB = new AxisAlignedBB(-f, -f, -f, f + 1.0f, f + 1.0f, f + 1.0f);
            } else {
                axisAlignedBB = null;
            }
        } else {
            axisAlignedBB = this.createAABB(this.dx == null ? 0.0 : this.dx, this.dy == null ? 0.0 : this.dy, this.dz == null ? 0.0 : this.dz);
        }
        Function<Vector3d, Vector3d> function = this.x == null && this.y == null && this.z == null ? EntitySelectorParser::lambda$build$10 : this::lambda$build$11;
        return new EntitySelector(this.limit, this.includeNonPlayers, this.currentWorldOnly, this.filter, this.distance, function, axisAlignedBB, this.sorter, this.self, this.username, this.uuid, this.type, this.checkPermission);
    }

    private AxisAlignedBB createAABB(double d, double d2, double d3) {
        boolean bl = d < 0.0;
        boolean bl2 = d2 < 0.0;
        boolean bl3 = d3 < 0.0;
        double d4 = bl ? d : 0.0;
        double d5 = bl2 ? d2 : 0.0;
        double d6 = bl3 ? d3 : 0.0;
        double d7 = (bl ? 0.0 : d) + 1.0;
        double d8 = (bl2 ? 0.0 : d2) + 1.0;
        double d9 = (bl3 ? 0.0 : d3) + 1.0;
        return new AxisAlignedBB(d4, d5, d6, d7, d8, d9);
    }

    private void updateFilter() {
        if (this.xRotation != MinMaxBoundsWrapped.UNBOUNDED) {
            this.filter = this.filter.and(this.createRotationPredicate(this.xRotation, EntitySelectorParser::lambda$updateFilter$12));
        }
        if (this.yRotation != MinMaxBoundsWrapped.UNBOUNDED) {
            this.filter = this.filter.and(this.createRotationPredicate(this.yRotation, EntitySelectorParser::lambda$updateFilter$13));
        }
        if (!this.level.isUnbounded()) {
            this.filter = this.filter.and(this::lambda$updateFilter$14);
        }
    }

    private Predicate<Entity> createRotationPredicate(MinMaxBoundsWrapped minMaxBoundsWrapped, ToDoubleFunction<Entity> toDoubleFunction) {
        double d = MathHelper.wrapDegrees(minMaxBoundsWrapped.getMin() == null ? 0.0f : minMaxBoundsWrapped.getMin().floatValue());
        double d2 = MathHelper.wrapDegrees(minMaxBoundsWrapped.getMax() == null ? 359.0f : minMaxBoundsWrapped.getMax().floatValue());
        return arg_0 -> EntitySelectorParser.lambda$createRotationPredicate$15(toDoubleFunction, d, d2, arg_0);
    }

    protected void parseSelector() throws CommandSyntaxException {
        this.checkPermission = true;
        this.suggestionHandler = this::suggestSelector;
        if (!this.reader.canRead()) {
            throw SELECTOR_TYPE_MISSING.createWithContext(this.reader);
        }
        int n = this.reader.getCursor();
        char c = this.reader.read();
        if (c == 'p') {
            this.limit = 1;
            this.includeNonPlayers = false;
            this.sorter = NEAREST;
            this.func_218114_a(EntityType.PLAYER);
        } else if (c == 'a') {
            this.limit = Integer.MAX_VALUE;
            this.includeNonPlayers = false;
            this.sorter = ARBITRARY;
            this.func_218114_a(EntityType.PLAYER);
        } else if (c == 'r') {
            this.limit = 1;
            this.includeNonPlayers = false;
            this.sorter = RANDOM;
            this.func_218114_a(EntityType.PLAYER);
        } else if (c == 's') {
            this.limit = 1;
            this.includeNonPlayers = true;
            this.self = true;
        } else {
            if (c != 'e') {
                this.reader.setCursor(n);
                throw UNKNOWN_SELECTOR_TYPE.createWithContext(this.reader, "@" + String.valueOf(c));
            }
            this.limit = Integer.MAX_VALUE;
            this.includeNonPlayers = true;
            this.sorter = ARBITRARY;
            this.filter = Entity::isAlive;
        }
        this.suggestionHandler = this::suggestOpenBracket;
        if (this.reader.canRead() && this.reader.peek() == '[') {
            this.reader.skip();
            this.suggestionHandler = this::suggestOptionsOrEnd;
            this.parseArguments();
        }
    }

    protected void parseSingleEntity() throws CommandSyntaxException {
        if (this.reader.canRead()) {
            this.suggestionHandler = this::suggestName;
        }
        int n = this.reader.getCursor();
        String string = this.reader.readString();
        try {
            this.uuid = UUID.fromString(string);
            this.includeNonPlayers = true;
        } catch (IllegalArgumentException illegalArgumentException) {
            if (string.isEmpty() || string.length() > 16) {
                this.reader.setCursor(n);
                throw INVALID_ENTITY_NAME_OR_UUID.createWithContext(this.reader);
            }
            this.includeNonPlayers = false;
            this.username = string;
        }
        this.limit = 1;
    }

    protected void parseArguments() throws CommandSyntaxException {
        this.suggestionHandler = this::suggestOptions;
        this.reader.skipWhitespace();
        while (this.reader.canRead() && this.reader.peek() != ']') {
            this.reader.skipWhitespace();
            int n = this.reader.getCursor();
            String string = this.reader.readString();
            EntityOptions.IFilter iFilter = EntityOptions.get(this, string, n);
            this.reader.skipWhitespace();
            if (!this.reader.canRead() || this.reader.peek() != '=') {
                this.reader.setCursor(n);
                throw EXPECTED_VALUE_FOR_OPTION.createWithContext(this.reader, string);
            }
            this.reader.skip();
            this.reader.skipWhitespace();
            this.suggestionHandler = SUGGEST_NONE;
            iFilter.handle(this);
            this.reader.skipWhitespace();
            this.suggestionHandler = this::suggestCommaOrEnd;
            if (!this.reader.canRead()) continue;
            if (this.reader.peek() == ',') {
                this.reader.skip();
                this.suggestionHandler = this::suggestOptions;
                continue;
            }
            if (this.reader.peek() == ']') break;
            throw EXPECTED_END_OF_OPTIONS.createWithContext(this.reader);
        }
        if (this.reader.canRead()) {
            this.reader.skip();
            this.suggestionHandler = SUGGEST_NONE;
            return;
        }
        throw EXPECTED_END_OF_OPTIONS.createWithContext(this.reader);
    }

    public boolean shouldInvertValue() {
        this.reader.skipWhitespace();
        if (this.reader.canRead() && this.reader.peek() == '!') {
            this.reader.skip();
            this.reader.skipWhitespace();
            return false;
        }
        return true;
    }

    public boolean func_218115_f() {
        this.reader.skipWhitespace();
        if (this.reader.canRead() && this.reader.peek() == '#') {
            this.reader.skip();
            this.reader.skipWhitespace();
            return false;
        }
        return true;
    }

    public StringReader getReader() {
        return this.reader;
    }

    public void addFilter(Predicate<Entity> predicate) {
        this.filter = this.filter.and(predicate);
    }

    public void setCurrentWorldOnly() {
        this.currentWorldOnly = true;
    }

    public MinMaxBounds.FloatBound getDistance() {
        return this.distance;
    }

    public void setDistance(MinMaxBounds.FloatBound floatBound) {
        this.distance = floatBound;
    }

    public MinMaxBounds.IntBound getLevel() {
        return this.level;
    }

    public void setLevel(MinMaxBounds.IntBound intBound) {
        this.level = intBound;
    }

    public MinMaxBoundsWrapped getXRotation() {
        return this.xRotation;
    }

    public void setXRotation(MinMaxBoundsWrapped minMaxBoundsWrapped) {
        this.xRotation = minMaxBoundsWrapped;
    }

    public MinMaxBoundsWrapped getYRotation() {
        return this.yRotation;
    }

    public void setYRotation(MinMaxBoundsWrapped minMaxBoundsWrapped) {
        this.yRotation = minMaxBoundsWrapped;
    }

    @Nullable
    public Double getX() {
        return this.x;
    }

    @Nullable
    public Double getY() {
        return this.y;
    }

    @Nullable
    public Double getZ() {
        return this.z;
    }

    public void setX(double d) {
        this.x = d;
    }

    public void setY(double d) {
        this.y = d;
    }

    public void setZ(double d) {
        this.z = d;
    }

    public void setDx(double d) {
        this.dx = d;
    }

    public void setDy(double d) {
        this.dy = d;
    }

    public void setDz(double d) {
        this.dz = d;
    }

    @Nullable
    public Double getDx() {
        return this.dx;
    }

    @Nullable
    public Double getDy() {
        return this.dy;
    }

    @Nullable
    public Double getDz() {
        return this.dz;
    }

    public void setLimit(int n) {
        this.limit = n;
    }

    public void setIncludeNonPlayers(boolean bl) {
        this.includeNonPlayers = bl;
    }

    public void setSorter(BiConsumer<Vector3d, List<? extends Entity>> biConsumer) {
        this.sorter = biConsumer;
    }

    public EntitySelector parse() throws CommandSyntaxException {
        this.cursorStart = this.reader.getCursor();
        this.suggestionHandler = this::suggestNameOrSelector;
        if (this.reader.canRead() && this.reader.peek() == '@') {
            if (!this.hasPermission) {
                throw SELECTOR_NOT_ALLOWED.createWithContext(this.reader);
            }
            this.reader.skip();
            this.parseSelector();
        } else {
            this.parseSingleEntity();
        }
        this.updateFilter();
        return this.build();
    }

    private static void fillSelectorSuggestions(SuggestionsBuilder suggestionsBuilder) {
        suggestionsBuilder.suggest("@p", (Message)new TranslationTextComponent("argument.entity.selector.nearestPlayer"));
        suggestionsBuilder.suggest("@a", (Message)new TranslationTextComponent("argument.entity.selector.allPlayers"));
        suggestionsBuilder.suggest("@r", (Message)new TranslationTextComponent("argument.entity.selector.randomPlayer"));
        suggestionsBuilder.suggest("@s", (Message)new TranslationTextComponent("argument.entity.selector.self"));
        suggestionsBuilder.suggest("@e", (Message)new TranslationTextComponent("argument.entity.selector.allEntities"));
    }

    private CompletableFuture<Suggestions> suggestNameOrSelector(SuggestionsBuilder suggestionsBuilder, Consumer<SuggestionsBuilder> consumer) {
        consumer.accept(suggestionsBuilder);
        if (this.hasPermission) {
            EntitySelectorParser.fillSelectorSuggestions(suggestionsBuilder);
        }
        return suggestionsBuilder.buildFuture();
    }

    private CompletableFuture<Suggestions> suggestName(SuggestionsBuilder suggestionsBuilder, Consumer<SuggestionsBuilder> consumer) {
        SuggestionsBuilder suggestionsBuilder2 = suggestionsBuilder.createOffset(this.cursorStart);
        consumer.accept(suggestionsBuilder2);
        return suggestionsBuilder.add(suggestionsBuilder2).buildFuture();
    }

    private CompletableFuture<Suggestions> suggestSelector(SuggestionsBuilder suggestionsBuilder, Consumer<SuggestionsBuilder> consumer) {
        SuggestionsBuilder suggestionsBuilder2 = suggestionsBuilder.createOffset(suggestionsBuilder.getStart() - 1);
        EntitySelectorParser.fillSelectorSuggestions(suggestionsBuilder2);
        suggestionsBuilder.add(suggestionsBuilder2);
        return suggestionsBuilder.buildFuture();
    }

    private CompletableFuture<Suggestions> suggestOpenBracket(SuggestionsBuilder suggestionsBuilder, Consumer<SuggestionsBuilder> consumer) {
        suggestionsBuilder.suggest(String.valueOf('['));
        return suggestionsBuilder.buildFuture();
    }

    private CompletableFuture<Suggestions> suggestOptionsOrEnd(SuggestionsBuilder suggestionsBuilder, Consumer<SuggestionsBuilder> consumer) {
        suggestionsBuilder.suggest(String.valueOf(']'));
        EntityOptions.suggestOptions(this, suggestionsBuilder);
        return suggestionsBuilder.buildFuture();
    }

    private CompletableFuture<Suggestions> suggestOptions(SuggestionsBuilder suggestionsBuilder, Consumer<SuggestionsBuilder> consumer) {
        EntityOptions.suggestOptions(this, suggestionsBuilder);
        return suggestionsBuilder.buildFuture();
    }

    private CompletableFuture<Suggestions> suggestCommaOrEnd(SuggestionsBuilder suggestionsBuilder, Consumer<SuggestionsBuilder> consumer) {
        suggestionsBuilder.suggest(String.valueOf(','));
        suggestionsBuilder.suggest(String.valueOf(']'));
        return suggestionsBuilder.buildFuture();
    }

    public boolean isCurrentEntity() {
        return this.self;
    }

    public void setSuggestionHandler(BiFunction<SuggestionsBuilder, Consumer<SuggestionsBuilder>, CompletableFuture<Suggestions>> biFunction) {
        this.suggestionHandler = biFunction;
    }

    public CompletableFuture<Suggestions> fillSuggestions(SuggestionsBuilder suggestionsBuilder, Consumer<SuggestionsBuilder> consumer) {
        return this.suggestionHandler.apply(suggestionsBuilder.createOffset(this.reader.getCursor()), consumer);
    }

    public boolean hasNameEquals() {
        return this.hasNameEquals;
    }

    public void setHasNameEquals(boolean bl) {
        this.hasNameEquals = bl;
    }

    public boolean hasNameNotEquals() {
        return this.hasNameNotEquals;
    }

    public void setHasNameNotEquals(boolean bl) {
        this.hasNameNotEquals = bl;
    }

    public boolean isLimited() {
        return this.isLimited;
    }

    public void setLimited(boolean bl) {
        this.isLimited = bl;
    }

    public boolean isSorted() {
        return this.isSorted;
    }

    public void setSorted(boolean bl) {
        this.isSorted = bl;
    }

    public boolean hasGamemodeEquals() {
        return this.hasGamemodeEquals;
    }

    public void setHasGamemodeEquals(boolean bl) {
        this.hasGamemodeEquals = bl;
    }

    public boolean hasGamemodeNotEquals() {
        return this.hasGamemodeNotEquals;
    }

    public void setHasGamemodeNotEquals(boolean bl) {
        this.hasGamemodeNotEquals = bl;
    }

    public boolean hasTeamEquals() {
        return this.hasTeamEquals;
    }

    public void setHasTeamEquals(boolean bl) {
        this.hasTeamEquals = bl;
    }

    public void setHasTeamNotEquals(boolean bl) {
        this.hasTeamNotEquals = bl;
    }

    public void func_218114_a(EntityType<?> entityType) {
        this.type = entityType;
    }

    public void setTypeLimitedInversely() {
        this.typeInverse = true;
    }

    public boolean isTypeLimited() {
        return this.type != null;
    }

    public boolean isTypeLimitedInversely() {
        return this.typeInverse;
    }

    public boolean hasScores() {
        return this.hasScores;
    }

    public void setHasScores(boolean bl) {
        this.hasScores = bl;
    }

    public boolean hasAdvancements() {
        return this.hasAdvancements;
    }

    public void setHasAdvancements(boolean bl) {
        this.hasAdvancements = bl;
    }

    private static boolean lambda$createRotationPredicate$15(ToDoubleFunction toDoubleFunction, double d, double d2, Entity entity2) {
        double d3 = MathHelper.wrapDegrees(toDoubleFunction.applyAsDouble(entity2));
        if (d > d2) {
            return d3 >= d || d3 <= d2;
        }
        return d3 >= d && d3 <= d2;
    }

    private boolean lambda$updateFilter$14(Entity entity2) {
        return !(entity2 instanceof ServerPlayerEntity) ? false : this.level.test(((ServerPlayerEntity)entity2).experienceLevel);
    }

    private static double lambda$updateFilter$13(Entity entity2) {
        return entity2.rotationYaw;
    }

    private static double lambda$updateFilter$12(Entity entity2) {
        return entity2.rotationPitch;
    }

    private Vector3d lambda$build$11(Vector3d vector3d) {
        return new Vector3d(this.x == null ? vector3d.x : this.x, this.y == null ? vector3d.y : this.y, this.z == null ? vector3d.z : this.z);
    }

    private static Vector3d lambda$build$10(Vector3d vector3d) {
        return vector3d;
    }

    private static boolean lambda$new$9(Entity entity2) {
        return false;
    }

    private static CompletableFuture lambda$static$8(SuggestionsBuilder suggestionsBuilder, Consumer consumer) {
        return suggestionsBuilder.buildFuture();
    }

    private static void lambda$static$7(Vector3d vector3d, List list) {
        Collections.shuffle(list);
    }

    private static void lambda$static$6(Vector3d vector3d, List list) {
        list.sort((arg_0, arg_1) -> EntitySelectorParser.lambda$static$5(vector3d, arg_0, arg_1));
    }

    private static int lambda$static$5(Vector3d vector3d, Entity entity2, Entity entity3) {
        return Doubles.compare(entity3.getDistanceSq(vector3d), entity2.getDistanceSq(vector3d));
    }

    private static void lambda$static$4(Vector3d vector3d, List list) {
        list.sort((arg_0, arg_1) -> EntitySelectorParser.lambda$static$3(vector3d, arg_0, arg_1));
    }

    private static int lambda$static$3(Vector3d vector3d, Entity entity2, Entity entity3) {
        return Doubles.compare(entity2.getDistanceSq(vector3d), entity3.getDistanceSq(vector3d));
    }

    private static void lambda$static$2(Vector3d vector3d, List list) {
    }

    private static Message lambda$static$1(Object object) {
        return new TranslationTextComponent("argument.entity.options.valueless", object);
    }

    private static Message lambda$static$0(Object object) {
        return new TranslationTextComponent("argument.entity.selector.unknown", object);
    }
}


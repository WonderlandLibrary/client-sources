/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.serialization.DynamicLike;
import java.util.Comparator;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SChangeGameStatePacket;
import net.minecraft.network.play.server.SEntityStatusPacket;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class GameRules {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Map<RuleKey<?>, RuleType<?>> GAME_RULES = Maps.newTreeMap(Comparator.comparing(GameRules::lambda$static$0));
    public static final RuleKey<BooleanValue> DO_FIRE_TICK = GameRules.register("doFireTick", Category.UPDATES, BooleanValue.create(true));
    public static final RuleKey<BooleanValue> MOB_GRIEFING = GameRules.register("mobGriefing", Category.MOBS, BooleanValue.create(true));
    public static final RuleKey<BooleanValue> KEEP_INVENTORY = GameRules.register("keepInventory", Category.PLAYER, BooleanValue.create(false));
    public static final RuleKey<BooleanValue> DO_MOB_SPAWNING = GameRules.register("doMobSpawning", Category.SPAWNING, BooleanValue.create(true));
    public static final RuleKey<BooleanValue> DO_MOB_LOOT = GameRules.register("doMobLoot", Category.DROPS, BooleanValue.create(true));
    public static final RuleKey<BooleanValue> DO_TILE_DROPS = GameRules.register("doTileDrops", Category.DROPS, BooleanValue.create(true));
    public static final RuleKey<BooleanValue> DO_ENTITY_DROPS = GameRules.register("doEntityDrops", Category.DROPS, BooleanValue.create(true));
    public static final RuleKey<BooleanValue> COMMAND_BLOCK_OUTPUT = GameRules.register("commandBlockOutput", Category.CHAT, BooleanValue.create(true));
    public static final RuleKey<BooleanValue> NATURAL_REGENERATION = GameRules.register("naturalRegeneration", Category.PLAYER, BooleanValue.create(true));
    public static final RuleKey<BooleanValue> DO_DAYLIGHT_CYCLE = GameRules.register("doDaylightCycle", Category.UPDATES, BooleanValue.create(true));
    public static final RuleKey<BooleanValue> LOG_ADMIN_COMMANDS = GameRules.register("logAdminCommands", Category.CHAT, BooleanValue.create(true));
    public static final RuleKey<BooleanValue> SHOW_DEATH_MESSAGES = GameRules.register("showDeathMessages", Category.CHAT, BooleanValue.create(true));
    public static final RuleKey<IntegerValue> RANDOM_TICK_SPEED = GameRules.register("randomTickSpeed", Category.UPDATES, IntegerValue.create(3));
    public static final RuleKey<BooleanValue> SEND_COMMAND_FEEDBACK = GameRules.register("sendCommandFeedback", Category.CHAT, BooleanValue.create(true));
    public static final RuleKey<BooleanValue> REDUCED_DEBUG_INFO = GameRules.register("reducedDebugInfo", Category.MISC, BooleanValue.create(false, GameRules::lambda$static$1));
    public static final RuleKey<BooleanValue> SPECTATORS_GENERATE_CHUNKS = GameRules.register("spectatorsGenerateChunks", Category.PLAYER, BooleanValue.create(true));
    public static final RuleKey<IntegerValue> SPAWN_RADIUS = GameRules.register("spawnRadius", Category.PLAYER, IntegerValue.create(10));
    public static final RuleKey<BooleanValue> DISABLE_ELYTRA_MOVEMENT_CHECK = GameRules.register("disableElytraMovementCheck", Category.PLAYER, BooleanValue.create(false));
    public static final RuleKey<IntegerValue> MAX_ENTITY_CRAMMING = GameRules.register("maxEntityCramming", Category.MOBS, IntegerValue.create(24));
    public static final RuleKey<BooleanValue> DO_WEATHER_CYCLE = GameRules.register("doWeatherCycle", Category.UPDATES, BooleanValue.create(true));
    public static final RuleKey<BooleanValue> DO_LIMITED_CRAFTING = GameRules.register("doLimitedCrafting", Category.PLAYER, BooleanValue.create(false));
    public static final RuleKey<IntegerValue> MAX_COMMAND_CHAIN_LENGTH = GameRules.register("maxCommandChainLength", Category.MISC, IntegerValue.create(65536));
    public static final RuleKey<BooleanValue> ANNOUNCE_ADVANCEMENTS = GameRules.register("announceAdvancements", Category.CHAT, BooleanValue.create(true));
    public static final RuleKey<BooleanValue> DISABLE_RAIDS = GameRules.register("disableRaids", Category.MOBS, BooleanValue.create(false));
    public static final RuleKey<BooleanValue> DO_INSOMNIA = GameRules.register("doInsomnia", Category.SPAWNING, BooleanValue.create(true));
    public static final RuleKey<BooleanValue> DO_IMMEDIATE_RESPAWN = GameRules.register("doImmediateRespawn", Category.PLAYER, BooleanValue.create(false, GameRules::lambda$static$2));
    public static final RuleKey<BooleanValue> DROWNING_DAMAGE = GameRules.register("drowningDamage", Category.PLAYER, BooleanValue.create(true));
    public static final RuleKey<BooleanValue> FALL_DAMAGE = GameRules.register("fallDamage", Category.PLAYER, BooleanValue.create(true));
    public static final RuleKey<BooleanValue> FIRE_DAMAGE = GameRules.register("fireDamage", Category.PLAYER, BooleanValue.create(true));
    public static final RuleKey<BooleanValue> DO_PATROL_SPAWNING = GameRules.register("doPatrolSpawning", Category.SPAWNING, BooleanValue.create(true));
    public static final RuleKey<BooleanValue> DO_TRADER_SPAWNING = GameRules.register("doTraderSpawning", Category.SPAWNING, BooleanValue.create(true));
    public static final RuleKey<BooleanValue> FORGIVE_DEAD_PLAYERS = GameRules.register("forgiveDeadPlayers", Category.MOBS, BooleanValue.create(true));
    public static final RuleKey<BooleanValue> UNIVERSAL_ANGER = GameRules.register("universalAnger", Category.MOBS, BooleanValue.create(false));
    private final Map<RuleKey<?>, RuleValue<?>> rules;

    private static <T extends RuleValue<T>> RuleKey<T> register(String string, Category category, RuleType<T> ruleType) {
        RuleKey ruleKey = new RuleKey(string, category);
        RuleType<T> ruleType2 = GAME_RULES.put(ruleKey, ruleType);
        if (ruleType2 != null) {
            throw new IllegalStateException("Duplicate game rule registration for " + string);
        }
        return ruleKey;
    }

    public GameRules(DynamicLike<?> dynamicLike) {
        this();
        this.decode(dynamicLike);
    }

    public GameRules() {
        this.rules = GAME_RULES.entrySet().stream().collect(ImmutableMap.toImmutableMap(Map.Entry::getKey, GameRules::lambda$new$3));
    }

    private GameRules(Map<RuleKey<?>, RuleValue<?>> map) {
        this.rules = map;
    }

    public <T extends RuleValue<T>> T get(RuleKey<T> ruleKey) {
        return (T)this.rules.get(ruleKey);
    }

    public CompoundNBT write() {
        CompoundNBT compoundNBT = new CompoundNBT();
        this.rules.forEach((arg_0, arg_1) -> GameRules.lambda$write$4(compoundNBT, arg_0, arg_1));
        return compoundNBT;
    }

    private void decode(DynamicLike<?> dynamicLike) {
        this.rules.forEach((arg_0, arg_1) -> GameRules.lambda$decode$5(dynamicLike, arg_0, arg_1));
    }

    public GameRules clone() {
        return new GameRules((Map)this.rules.entrySet().stream().collect(ImmutableMap.toImmutableMap(Map.Entry::getKey, GameRules::lambda$clone$6)));
    }

    public static void visitAll(IRuleEntryVisitor iRuleEntryVisitor) {
        GAME_RULES.forEach((arg_0, arg_1) -> GameRules.lambda$visitAll$7(iRuleEntryVisitor, arg_0, arg_1));
    }

    private static <T extends RuleValue<T>> void visit(IRuleEntryVisitor iRuleEntryVisitor, RuleKey<?> ruleKey, RuleType<?> ruleType) {
        iRuleEntryVisitor.visit(ruleKey, ruleType);
        ruleType.visitRule(iRuleEntryVisitor, ruleKey);
    }

    public void func_234899_a_(GameRules gameRules, @Nullable MinecraftServer minecraftServer) {
        gameRules.rules.keySet().forEach(arg_0 -> this.lambda$func_234899_a_$8(gameRules, minecraftServer, arg_0));
    }

    private <T extends RuleValue<T>> void getValue(RuleKey<T> ruleKey, GameRules gameRules, @Nullable MinecraftServer minecraftServer) {
        T t = gameRules.get(ruleKey);
        ((RuleValue)this.get(ruleKey)).changeValue(t, minecraftServer);
    }

    public boolean getBoolean(RuleKey<BooleanValue> ruleKey) {
        return this.get(ruleKey).get();
    }

    public int getInt(RuleKey<IntegerValue> ruleKey) {
        return this.get(ruleKey).get();
    }

    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }

    private void lambda$func_234899_a_$8(GameRules gameRules, MinecraftServer minecraftServer, RuleKey ruleKey) {
        this.getValue(ruleKey, gameRules, minecraftServer);
    }

    private static void lambda$visitAll$7(IRuleEntryVisitor iRuleEntryVisitor, RuleKey ruleKey, RuleType ruleType) {
        GameRules.visit(iRuleEntryVisitor, ruleKey, ruleType);
    }

    private static RuleValue lambda$clone$6(Map.Entry entry) {
        return ((RuleValue)entry.getValue()).clone();
    }

    private static void lambda$decode$5(DynamicLike dynamicLike, RuleKey ruleKey, RuleValue ruleValue) {
        dynamicLike.get(ruleKey.gameRuleName).asString().result().ifPresent(ruleValue::setStringValue);
    }

    private static void lambda$write$4(CompoundNBT compoundNBT, RuleKey ruleKey, RuleValue ruleValue) {
        compoundNBT.putString(ruleKey.gameRuleName, ruleValue.stringValue());
    }

    private static RuleValue lambda$new$3(Map.Entry entry) {
        return ((RuleType)entry.getValue()).createValue();
    }

    private static void lambda$static$2(MinecraftServer minecraftServer, BooleanValue booleanValue) {
        for (ServerPlayerEntity serverPlayerEntity : minecraftServer.getPlayerList().getPlayers()) {
            serverPlayerEntity.connection.sendPacket(new SChangeGameStatePacket(SChangeGameStatePacket.field_241775_l_, booleanValue.get() ? 1.0f : 0.0f));
        }
    }

    private static void lambda$static$1(MinecraftServer minecraftServer, BooleanValue booleanValue) {
        byte by = (byte)(booleanValue.get() ? 22 : 23);
        for (ServerPlayerEntity serverPlayerEntity : minecraftServer.getPlayerList().getPlayers()) {
            serverPlayerEntity.connection.sendPacket(new SEntityStatusPacket(serverPlayerEntity, by));
        }
    }

    private static String lambda$static$0(RuleKey ruleKey) {
        return ruleKey.gameRuleName;
    }

    public static final class RuleKey<T extends RuleValue<T>> {
        private final String gameRuleName;
        private final Category category;

        public RuleKey(String string, Category category) {
            this.gameRuleName = string;
            this.category = category;
        }

        public String toString() {
            return this.gameRuleName;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return false;
            }
            return object instanceof RuleKey && ((RuleKey)object).gameRuleName.equals(this.gameRuleName);
        }

        public int hashCode() {
            return this.gameRuleName.hashCode();
        }

        public String getName() {
            return this.gameRuleName;
        }

        public String getLocaleString() {
            return "gamerule." + this.gameRuleName;
        }

        public Category getCategory() {
            return this.category;
        }
    }

    public static enum Category {
        PLAYER("gamerule.category.player"),
        MOBS("gamerule.category.mobs"),
        SPAWNING("gamerule.category.spawning"),
        DROPS("gamerule.category.drops"),
        UPDATES("gamerule.category.updates"),
        CHAT("gamerule.category.chat"),
        MISC("gamerule.category.misc");

        private final String localeString;

        private Category(String string2) {
            this.localeString = string2;
        }

        public String getLocaleString() {
            return this.localeString;
        }
    }

    public static class RuleType<T extends RuleValue<T>> {
        private final Supplier<ArgumentType<?>> argTypeSupplier;
        private final Function<RuleType<T>, T> valueCreator;
        private final BiConsumer<MinecraftServer, T> changeListener;
        private final IRule<T> rule;

        private RuleType(Supplier<ArgumentType<?>> supplier, Function<RuleType<T>, T> function, BiConsumer<MinecraftServer, T> biConsumer, IRule<T> iRule) {
            this.argTypeSupplier = supplier;
            this.valueCreator = function;
            this.changeListener = biConsumer;
            this.rule = iRule;
        }

        public RequiredArgumentBuilder<CommandSource, ?> createArgument(String string) {
            return Commands.argument(string, this.argTypeSupplier.get());
        }

        public T createValue() {
            return (T)((RuleValue)this.valueCreator.apply(this));
        }

        public void visitRule(IRuleEntryVisitor iRuleEntryVisitor, RuleKey<T> ruleKey) {
            this.rule.call(iRuleEntryVisitor, ruleKey, this);
        }
    }

    public static abstract class RuleValue<T extends RuleValue<T>> {
        protected final RuleType<T> type;

        public RuleValue(RuleType<T> ruleType) {
            this.type = ruleType;
        }

        protected abstract void updateValue0(CommandContext<CommandSource> var1, String var2);

        public void updateValue(CommandContext<CommandSource> commandContext, String string) {
            this.updateValue0(commandContext, string);
            this.notifyChange(commandContext.getSource().getServer());
        }

        protected void notifyChange(@Nullable MinecraftServer minecraftServer) {
            if (minecraftServer != null) {
                this.type.changeListener.accept(minecraftServer, (MinecraftServer)this.getValue());
            }
        }

        protected abstract void setStringValue(String var1);

        public abstract String stringValue();

        public String toString() {
            return this.stringValue();
        }

        public abstract int intValue();

        protected abstract T getValue();

        protected abstract T clone();

        public abstract void changeValue(T var1, @Nullable MinecraftServer var2);

        protected Object clone() throws CloneNotSupportedException {
            return this.clone();
        }
    }

    public static interface IRuleEntryVisitor {
        default public <T extends RuleValue<T>> void visit(RuleKey<T> ruleKey, RuleType<T> ruleType) {
        }

        default public void changeBoolean(RuleKey<BooleanValue> ruleKey, RuleType<BooleanValue> ruleType) {
        }

        default public void changeInteger(RuleKey<IntegerValue> ruleKey, RuleType<IntegerValue> ruleType) {
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class BooleanValue
    extends RuleValue<BooleanValue> {
        private boolean value;

        private static RuleType<BooleanValue> create(boolean bl, BiConsumer<MinecraftServer, BooleanValue> biConsumer) {
            return new RuleType<BooleanValue>(BoolArgumentType::bool, arg_0 -> BooleanValue.lambda$create$0(bl, arg_0), biConsumer, IRuleEntryVisitor::changeBoolean);
        }

        private static RuleType<BooleanValue> create(boolean bl) {
            return BooleanValue.create(bl, BooleanValue::lambda$create$1);
        }

        public BooleanValue(RuleType<BooleanValue> ruleType, boolean bl) {
            super(ruleType);
            this.value = bl;
        }

        @Override
        protected void updateValue0(CommandContext<CommandSource> commandContext, String string) {
            this.value = BoolArgumentType.getBool(commandContext, string);
        }

        public boolean get() {
            return this.value;
        }

        public void set(boolean bl, @Nullable MinecraftServer minecraftServer) {
            this.value = bl;
            this.notifyChange(minecraftServer);
        }

        @Override
        public String stringValue() {
            return Boolean.toString(this.value);
        }

        @Override
        protected void setStringValue(String string) {
            this.value = Boolean.parseBoolean(string);
        }

        @Override
        public int intValue() {
            return this.value ? 1 : 0;
        }

        @Override
        protected BooleanValue getValue() {
            return this;
        }

        @Override
        protected BooleanValue clone() {
            return new BooleanValue(this.type, this.value);
        }

        @Override
        public void changeValue(BooleanValue booleanValue, @Nullable MinecraftServer minecraftServer) {
            this.value = booleanValue.value;
            this.notifyChange(minecraftServer);
        }

        @Override
        public void changeValue(RuleValue ruleValue, @Nullable MinecraftServer minecraftServer) {
            this.changeValue((BooleanValue)ruleValue, minecraftServer);
        }

        @Override
        protected RuleValue clone() {
            return this.clone();
        }

        @Override
        protected RuleValue getValue() {
            return this.getValue();
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            return this.clone();
        }

        private static void lambda$create$1(MinecraftServer minecraftServer, BooleanValue booleanValue) {
        }

        private static BooleanValue lambda$create$0(boolean bl, RuleType ruleType) {
            return new BooleanValue(ruleType, bl);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class IntegerValue
    extends RuleValue<IntegerValue> {
        private int value;

        private static RuleType<IntegerValue> create(int n, BiConsumer<MinecraftServer, IntegerValue> biConsumer) {
            return new RuleType<IntegerValue>(IntegerArgumentType::integer, arg_0 -> IntegerValue.lambda$create$0(n, arg_0), biConsumer, IRuleEntryVisitor::changeInteger);
        }

        private static RuleType<IntegerValue> create(int n) {
            return IntegerValue.create(n, IntegerValue::lambda$create$1);
        }

        public IntegerValue(RuleType<IntegerValue> ruleType, int n) {
            super(ruleType);
            this.value = n;
        }

        @Override
        protected void updateValue0(CommandContext<CommandSource> commandContext, String string) {
            this.value = IntegerArgumentType.getInteger(commandContext, string);
        }

        public int get() {
            return this.value;
        }

        @Override
        public String stringValue() {
            return Integer.toString(this.value);
        }

        @Override
        protected void setStringValue(String string) {
            this.value = IntegerValue.parseInt(string);
        }

        public boolean parseIntValue(String string) {
            try {
                this.value = Integer.parseInt(string);
                return true;
            } catch (NumberFormatException numberFormatException) {
                return true;
            }
        }

        private static int parseInt(String string) {
            if (!string.isEmpty()) {
                try {
                    return Integer.parseInt(string);
                } catch (NumberFormatException numberFormatException) {
                    LOGGER.warn("Failed to parse integer {}", (Object)string);
                }
            }
            return 1;
        }

        @Override
        public int intValue() {
            return this.value;
        }

        @Override
        protected IntegerValue getValue() {
            return this;
        }

        @Override
        protected IntegerValue clone() {
            return new IntegerValue(this.type, this.value);
        }

        @Override
        public void changeValue(IntegerValue integerValue, @Nullable MinecraftServer minecraftServer) {
            this.value = integerValue.value;
            this.notifyChange(minecraftServer);
        }

        @Override
        public void changeValue(RuleValue ruleValue, @Nullable MinecraftServer minecraftServer) {
            this.changeValue((IntegerValue)ruleValue, minecraftServer);
        }

        @Override
        protected RuleValue clone() {
            return this.clone();
        }

        @Override
        protected RuleValue getValue() {
            return this.getValue();
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            return this.clone();
        }

        private static void lambda$create$1(MinecraftServer minecraftServer, IntegerValue integerValue) {
        }

        private static IntegerValue lambda$create$0(int n, RuleType ruleType) {
            return new IntegerValue(ruleType, n);
        }
    }

    static interface IRule<T extends RuleValue<T>> {
        public void call(IRuleEntryVisitor var1, RuleKey<T> var2, RuleType<T> var3);
    }
}


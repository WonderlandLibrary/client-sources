/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.scoreboard;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Optional;
import net.minecraft.stats.StatType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.TextFormatting;

public class ScoreCriteria {
    public static final Map<String, ScoreCriteria> INSTANCES = Maps.newHashMap();
    public static final ScoreCriteria DUMMY = new ScoreCriteria("dummy");
    public static final ScoreCriteria TRIGGER = new ScoreCriteria("trigger");
    public static final ScoreCriteria DEATH_COUNT = new ScoreCriteria("deathCount");
    public static final ScoreCriteria PLAYER_KILL_COUNT = new ScoreCriteria("playerKillCount");
    public static final ScoreCriteria TOTAL_KILL_COUNT = new ScoreCriteria("totalKillCount");
    public static final ScoreCriteria HEALTH = new ScoreCriteria("health", true, RenderType.HEARTS);
    public static final ScoreCriteria FOOD = new ScoreCriteria("food", true, RenderType.INTEGER);
    public static final ScoreCriteria AIR = new ScoreCriteria("air", true, RenderType.INTEGER);
    public static final ScoreCriteria ARMOR = new ScoreCriteria("armor", true, RenderType.INTEGER);
    public static final ScoreCriteria XP = new ScoreCriteria("xp", true, RenderType.INTEGER);
    public static final ScoreCriteria LEVEL = new ScoreCriteria("level", true, RenderType.INTEGER);
    public static final ScoreCriteria[] TEAM_KILL = new ScoreCriteria[]{new ScoreCriteria("teamkill." + TextFormatting.BLACK.getFriendlyName()), new ScoreCriteria("teamkill." + TextFormatting.DARK_BLUE.getFriendlyName()), new ScoreCriteria("teamkill." + TextFormatting.DARK_GREEN.getFriendlyName()), new ScoreCriteria("teamkill." + TextFormatting.DARK_AQUA.getFriendlyName()), new ScoreCriteria("teamkill." + TextFormatting.DARK_RED.getFriendlyName()), new ScoreCriteria("teamkill." + TextFormatting.DARK_PURPLE.getFriendlyName()), new ScoreCriteria("teamkill." + TextFormatting.GOLD.getFriendlyName()), new ScoreCriteria("teamkill." + TextFormatting.GRAY.getFriendlyName()), new ScoreCriteria("teamkill." + TextFormatting.DARK_GRAY.getFriendlyName()), new ScoreCriteria("teamkill." + TextFormatting.BLUE.getFriendlyName()), new ScoreCriteria("teamkill." + TextFormatting.GREEN.getFriendlyName()), new ScoreCriteria("teamkill." + TextFormatting.AQUA.getFriendlyName()), new ScoreCriteria("teamkill." + TextFormatting.RED.getFriendlyName()), new ScoreCriteria("teamkill." + TextFormatting.LIGHT_PURPLE.getFriendlyName()), new ScoreCriteria("teamkill." + TextFormatting.YELLOW.getFriendlyName()), new ScoreCriteria("teamkill." + TextFormatting.WHITE.getFriendlyName())};
    public static final ScoreCriteria[] KILLED_BY_TEAM = new ScoreCriteria[]{new ScoreCriteria("killedByTeam." + TextFormatting.BLACK.getFriendlyName()), new ScoreCriteria("killedByTeam." + TextFormatting.DARK_BLUE.getFriendlyName()), new ScoreCriteria("killedByTeam." + TextFormatting.DARK_GREEN.getFriendlyName()), new ScoreCriteria("killedByTeam." + TextFormatting.DARK_AQUA.getFriendlyName()), new ScoreCriteria("killedByTeam." + TextFormatting.DARK_RED.getFriendlyName()), new ScoreCriteria("killedByTeam." + TextFormatting.DARK_PURPLE.getFriendlyName()), new ScoreCriteria("killedByTeam." + TextFormatting.GOLD.getFriendlyName()), new ScoreCriteria("killedByTeam." + TextFormatting.GRAY.getFriendlyName()), new ScoreCriteria("killedByTeam." + TextFormatting.DARK_GRAY.getFriendlyName()), new ScoreCriteria("killedByTeam." + TextFormatting.BLUE.getFriendlyName()), new ScoreCriteria("killedByTeam." + TextFormatting.GREEN.getFriendlyName()), new ScoreCriteria("killedByTeam." + TextFormatting.AQUA.getFriendlyName()), new ScoreCriteria("killedByTeam." + TextFormatting.RED.getFriendlyName()), new ScoreCriteria("killedByTeam." + TextFormatting.LIGHT_PURPLE.getFriendlyName()), new ScoreCriteria("killedByTeam." + TextFormatting.YELLOW.getFriendlyName()), new ScoreCriteria("killedByTeam." + TextFormatting.WHITE.getFriendlyName())};
    private final String name;
    private final boolean readOnly;
    private final RenderType renderType;

    public ScoreCriteria(String string) {
        this(string, false, RenderType.INTEGER);
    }

    protected ScoreCriteria(String string, boolean bl, RenderType renderType) {
        this.name = string;
        this.readOnly = bl;
        this.renderType = renderType;
        INSTANCES.put(string, this);
    }

    public static Optional<ScoreCriteria> func_216390_a(String string) {
        if (INSTANCES.containsKey(string)) {
            return Optional.of(INSTANCES.get(string));
        }
        int n = string.indexOf(58);
        return n < 0 ? Optional.empty() : Registry.STATS.getOptional(ResourceLocation.create(string.substring(0, n), '.')).flatMap(arg_0 -> ScoreCriteria.lambda$func_216390_a$0(string, n, arg_0));
    }

    private static <T> Optional<ScoreCriteria> func_216391_a(StatType<T> statType, ResourceLocation resourceLocation) {
        return statType.getRegistry().getOptional(resourceLocation).map(statType::get);
    }

    public String getName() {
        return this.name;
    }

    public boolean isReadOnly() {
        return this.readOnly;
    }

    public RenderType getRenderType() {
        return this.renderType;
    }

    private static Optional lambda$func_216390_a$0(String string, int n, StatType statType) {
        return ScoreCriteria.func_216391_a(statType, ResourceLocation.create(string.substring(n + 1), '.'));
    }

    public static enum RenderType {
        INTEGER("integer"),
        HEARTS("hearts");

        private final String field_211840_c;
        private static final Map<String, RenderType> field_211841_d;

        private RenderType(String string2) {
            this.field_211840_c = string2;
        }

        public String getId() {
            return this.field_211840_c;
        }

        public static RenderType byId(String string) {
            return field_211841_d.getOrDefault(string, INTEGER);
        }

        static {
            ImmutableMap.Builder<String, RenderType> builder = ImmutableMap.builder();
            for (RenderType renderType : RenderType.values()) {
                builder.put(renderType.field_211840_c, renderType);
            }
            field_211841_d = builder.build();
        }
    }
}


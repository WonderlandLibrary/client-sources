/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.arguments;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.tree.ArgumentCommandNode;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.mojang.brigadier.tree.RootCommandNode;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.command.arguments.AngleArgument;
import net.minecraft.command.arguments.ArgumentSerializer;
import net.minecraft.command.arguments.BlockPosArgument;
import net.minecraft.command.arguments.BlockPredicateArgument;
import net.minecraft.command.arguments.BlockStateArgument;
import net.minecraft.command.arguments.ColorArgument;
import net.minecraft.command.arguments.ColumnPosArgument;
import net.minecraft.command.arguments.ComponentArgument;
import net.minecraft.command.arguments.DimensionArgument;
import net.minecraft.command.arguments.EnchantmentArgument;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.EntitySummonArgument;
import net.minecraft.command.arguments.FunctionArgument;
import net.minecraft.command.arguments.GameProfileArgument;
import net.minecraft.command.arguments.IArgumentSerializer;
import net.minecraft.command.arguments.IRangeArgument;
import net.minecraft.command.arguments.ItemArgument;
import net.minecraft.command.arguments.ItemPredicateArgument;
import net.minecraft.command.arguments.MessageArgument;
import net.minecraft.command.arguments.NBTCompoundTagArgument;
import net.minecraft.command.arguments.NBTPathArgument;
import net.minecraft.command.arguments.NBTTagArgument;
import net.minecraft.command.arguments.ObjectiveArgument;
import net.minecraft.command.arguments.ObjectiveCriteriaArgument;
import net.minecraft.command.arguments.OperationArgument;
import net.minecraft.command.arguments.ParticleArgument;
import net.minecraft.command.arguments.PotionArgument;
import net.minecraft.command.arguments.ResourceLocationArgument;
import net.minecraft.command.arguments.RotationArgument;
import net.minecraft.command.arguments.ScoreHolderArgument;
import net.minecraft.command.arguments.ScoreboardSlotArgument;
import net.minecraft.command.arguments.SlotArgument;
import net.minecraft.command.arguments.SwizzleArgument;
import net.minecraft.command.arguments.TeamArgument;
import net.minecraft.command.arguments.TimeArgument;
import net.minecraft.command.arguments.UUIDArgument;
import net.minecraft.command.arguments.Vec2Argument;
import net.minecraft.command.arguments.Vec3Argument;
import net.minecraft.command.arguments.serializers.BrigadierSerializers;
import net.minecraft.network.PacketBuffer;
import net.minecraft.test.TestArgArgument;
import net.minecraft.test.TestTypeArgument;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ArgumentTypes {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Map<Class<?>, Entry<?>> CLASS_TYPE_MAP = Maps.newHashMap();
    private static final Map<ResourceLocation, Entry<?>> ID_TYPE_MAP = Maps.newHashMap();

    public static <T extends ArgumentType<?>> void register(String string, Class<T> clazz, IArgumentSerializer<T> iArgumentSerializer) {
        ResourceLocation resourceLocation = new ResourceLocation(string);
        if (CLASS_TYPE_MAP.containsKey(clazz)) {
            throw new IllegalArgumentException("Class " + clazz.getName() + " already has a serializer!");
        }
        if (ID_TYPE_MAP.containsKey(resourceLocation)) {
            throw new IllegalArgumentException("'" + resourceLocation + "' is already a registered serializer!");
        }
        Entry<T> entry = new Entry<T>(clazz, iArgumentSerializer, resourceLocation);
        CLASS_TYPE_MAP.put(clazz, entry);
        ID_TYPE_MAP.put(resourceLocation, entry);
    }

    public static void registerArgumentTypes() {
        BrigadierSerializers.registerArgumentTypes();
        ArgumentTypes.register("entity", EntityArgument.class, new EntityArgument.Serializer());
        ArgumentTypes.register("game_profile", GameProfileArgument.class, new ArgumentSerializer<GameProfileArgument>(GameProfileArgument::gameProfile));
        ArgumentTypes.register("block_pos", BlockPosArgument.class, new ArgumentSerializer<BlockPosArgument>(BlockPosArgument::blockPos));
        ArgumentTypes.register("column_pos", ColumnPosArgument.class, new ArgumentSerializer<ColumnPosArgument>(ColumnPosArgument::columnPos));
        ArgumentTypes.register("vec3", Vec3Argument.class, new ArgumentSerializer<Vec3Argument>(Vec3Argument::vec3));
        ArgumentTypes.register("vec2", Vec2Argument.class, new ArgumentSerializer<Vec2Argument>(Vec2Argument::vec2));
        ArgumentTypes.register("block_state", BlockStateArgument.class, new ArgumentSerializer<BlockStateArgument>(BlockStateArgument::blockState));
        ArgumentTypes.register("block_predicate", BlockPredicateArgument.class, new ArgumentSerializer<BlockPredicateArgument>(BlockPredicateArgument::blockPredicate));
        ArgumentTypes.register("item_stack", ItemArgument.class, new ArgumentSerializer<ItemArgument>(ItemArgument::item));
        ArgumentTypes.register("item_predicate", ItemPredicateArgument.class, new ArgumentSerializer<ItemPredicateArgument>(ItemPredicateArgument::itemPredicate));
        ArgumentTypes.register("color", ColorArgument.class, new ArgumentSerializer<ColorArgument>(ColorArgument::color));
        ArgumentTypes.register("component", ComponentArgument.class, new ArgumentSerializer<ComponentArgument>(ComponentArgument::component));
        ArgumentTypes.register("message", MessageArgument.class, new ArgumentSerializer<MessageArgument>(MessageArgument::message));
        ArgumentTypes.register("nbt_compound_tag", NBTCompoundTagArgument.class, new ArgumentSerializer<NBTCompoundTagArgument>(NBTCompoundTagArgument::nbt));
        ArgumentTypes.register("nbt_tag", NBTTagArgument.class, new ArgumentSerializer<NBTTagArgument>(NBTTagArgument::func_218085_a));
        ArgumentTypes.register("nbt_path", NBTPathArgument.class, new ArgumentSerializer<NBTPathArgument>(NBTPathArgument::nbtPath));
        ArgumentTypes.register("objective", ObjectiveArgument.class, new ArgumentSerializer<ObjectiveArgument>(ObjectiveArgument::objective));
        ArgumentTypes.register("objective_criteria", ObjectiveCriteriaArgument.class, new ArgumentSerializer<ObjectiveCriteriaArgument>(ObjectiveCriteriaArgument::objectiveCriteria));
        ArgumentTypes.register("operation", OperationArgument.class, new ArgumentSerializer<OperationArgument>(OperationArgument::operation));
        ArgumentTypes.register("particle", ParticleArgument.class, new ArgumentSerializer<ParticleArgument>(ParticleArgument::particle));
        ArgumentTypes.register("angle", AngleArgument.class, new ArgumentSerializer<AngleArgument>(AngleArgument::func_242991_a));
        ArgumentTypes.register("rotation", RotationArgument.class, new ArgumentSerializer<RotationArgument>(RotationArgument::rotation));
        ArgumentTypes.register("scoreboard_slot", ScoreboardSlotArgument.class, new ArgumentSerializer<ScoreboardSlotArgument>(ScoreboardSlotArgument::scoreboardSlot));
        ArgumentTypes.register("score_holder", ScoreHolderArgument.class, new ScoreHolderArgument.Serializer());
        ArgumentTypes.register("swizzle", SwizzleArgument.class, new ArgumentSerializer<SwizzleArgument>(SwizzleArgument::swizzle));
        ArgumentTypes.register("team", TeamArgument.class, new ArgumentSerializer<TeamArgument>(TeamArgument::team));
        ArgumentTypes.register("item_slot", SlotArgument.class, new ArgumentSerializer<SlotArgument>(SlotArgument::slot));
        ArgumentTypes.register("resource_location", ResourceLocationArgument.class, new ArgumentSerializer<ResourceLocationArgument>(ResourceLocationArgument::resourceLocation));
        ArgumentTypes.register("mob_effect", PotionArgument.class, new ArgumentSerializer<PotionArgument>(PotionArgument::mobEffect));
        ArgumentTypes.register("function", FunctionArgument.class, new ArgumentSerializer<FunctionArgument>(FunctionArgument::function));
        ArgumentTypes.register("entity_anchor", EntityAnchorArgument.class, new ArgumentSerializer<EntityAnchorArgument>(EntityAnchorArgument::entityAnchor));
        ArgumentTypes.register("int_range", IRangeArgument.IntRange.class, new ArgumentSerializer<IRangeArgument.IntRange>(IRangeArgument::intRange));
        ArgumentTypes.register("float_range", IRangeArgument.FloatRange.class, new ArgumentSerializer<IRangeArgument.FloatRange>(IRangeArgument::func_243493_b));
        ArgumentTypes.register("item_enchantment", EnchantmentArgument.class, new ArgumentSerializer<EnchantmentArgument>(EnchantmentArgument::enchantment));
        ArgumentTypes.register("entity_summon", EntitySummonArgument.class, new ArgumentSerializer<EntitySummonArgument>(EntitySummonArgument::entitySummon));
        ArgumentTypes.register("dimension", DimensionArgument.class, new ArgumentSerializer<DimensionArgument>(DimensionArgument::getDimension));
        ArgumentTypes.register("time", TimeArgument.class, new ArgumentSerializer<TimeArgument>(TimeArgument::func_218091_a));
        ArgumentTypes.register("uuid", UUIDArgument.class, new ArgumentSerializer<UUIDArgument>(UUIDArgument::func_239194_a_));
        if (SharedConstants.developmentMode) {
            ArgumentTypes.register("test_argument", TestArgArgument.class, new ArgumentSerializer<TestArgArgument>(TestArgArgument::func_229665_a_));
            ArgumentTypes.register("test_class", TestTypeArgument.class, new ArgumentSerializer<TestTypeArgument>(TestTypeArgument::func_229611_a_));
        }
    }

    @Nullable
    private static Entry<?> get(ResourceLocation resourceLocation) {
        return ID_TYPE_MAP.get(resourceLocation);
    }

    @Nullable
    private static Entry<?> get(ArgumentType<?> argumentType) {
        return CLASS_TYPE_MAP.get(argumentType.getClass());
    }

    public static <T extends ArgumentType<?>> void serialize(PacketBuffer packetBuffer, T t) {
        Entry<?> entry = ArgumentTypes.get(t);
        if (entry == null) {
            LOGGER.error("Could not serialize {} ({}) - will not be sent to client!", (Object)t, (Object)t.getClass());
            packetBuffer.writeResourceLocation(new ResourceLocation(""));
        } else {
            packetBuffer.writeResourceLocation(entry.id);
            entry.serializer.write(t, packetBuffer);
        }
    }

    @Nullable
    public static ArgumentType<?> deserialize(PacketBuffer packetBuffer) {
        ResourceLocation resourceLocation = packetBuffer.readResourceLocation();
        Entry<?> entry = ArgumentTypes.get(resourceLocation);
        if (entry == null) {
            LOGGER.error("Could not deserialize {}", (Object)resourceLocation);
            return null;
        }
        return entry.serializer.read(packetBuffer);
    }

    private static <T extends ArgumentType<?>> void serialize(JsonObject jsonObject, T t) {
        Entry<?> entry = ArgumentTypes.get(t);
        if (entry == null) {
            LOGGER.error("Could not serialize argument {} ({})!", (Object)t, (Object)t.getClass());
            jsonObject.addProperty("type", "unknown");
        } else {
            jsonObject.addProperty("type", "argument");
            jsonObject.addProperty("parser", entry.id.toString());
            JsonObject jsonObject2 = new JsonObject();
            entry.serializer.write(t, jsonObject2);
            if (jsonObject2.size() > 0) {
                jsonObject.add("properties", jsonObject2);
            }
        }
    }

    public static <S> JsonObject serialize(CommandDispatcher<S> commandDispatcher, CommandNode<S> commandNode) {
        Object object;
        JsonObject jsonObject = new JsonObject();
        if (commandNode instanceof RootCommandNode) {
            jsonObject.addProperty("type", "root");
        } else if (commandNode instanceof LiteralCommandNode) {
            jsonObject.addProperty("type", "literal");
        } else if (commandNode instanceof ArgumentCommandNode) {
            ArgumentTypes.serialize(jsonObject, ((ArgumentCommandNode)commandNode).getType());
        } else {
            LOGGER.error("Could not serialize node {} ({})!", (Object)commandNode, (Object)commandNode.getClass());
            jsonObject.addProperty("type", "unknown");
        }
        JsonObject jsonObject2 = new JsonObject();
        Object object2 = commandNode.getChildren().iterator();
        while (object2.hasNext()) {
            object = object2.next();
            jsonObject2.add(((CommandNode)object).getName(), ArgumentTypes.serialize(commandDispatcher, object));
        }
        if (jsonObject2.size() > 0) {
            jsonObject.add("children", jsonObject2);
        }
        if (commandNode.getCommand() != null) {
            jsonObject.addProperty("executable", true);
        }
        if (commandNode.getRedirect() != null && !(object2 = commandDispatcher.getPath(commandNode.getRedirect())).isEmpty()) {
            object = new JsonArray();
            Iterator iterator2 = object2.iterator();
            while (iterator2.hasNext()) {
                String string = (String)iterator2.next();
                ((JsonArray)object).add(string);
            }
            jsonObject.add("redirect", (JsonElement)object);
        }
        return jsonObject;
    }

    public static boolean func_243510_a(ArgumentType<?> argumentType) {
        return ArgumentTypes.get(argumentType) != null;
    }

    public static <T> Set<ArgumentType<?>> func_243511_a(CommandNode<T> commandNode) {
        Set<CommandNode<T>> set = Sets.newIdentityHashSet();
        HashSet<ArgumentType<?>> hashSet = Sets.newHashSet();
        ArgumentTypes.func_243512_a(commandNode, hashSet, set);
        return hashSet;
    }

    private static <T> void func_243512_a(CommandNode<T> commandNode, Set<ArgumentType<?>> set, Set<CommandNode<T>> set2) {
        if (set2.add(commandNode)) {
            if (commandNode instanceof ArgumentCommandNode) {
                set.add(((ArgumentCommandNode)commandNode).getType());
            }
            commandNode.getChildren().forEach(arg_0 -> ArgumentTypes.lambda$func_243512_a$0(set, set2, arg_0));
            CommandNode<T> commandNode2 = commandNode.getRedirect();
            if (commandNode2 != null) {
                ArgumentTypes.func_243512_a(commandNode2, set, set2);
            }
        }
    }

    private static void lambda$func_243512_a$0(Set set, Set set2, CommandNode commandNode) {
        ArgumentTypes.func_243512_a(commandNode, set, set2);
    }

    static class Entry<T extends ArgumentType<?>> {
        public final Class<T> argumentClass;
        public final IArgumentSerializer<T> serializer;
        public final ResourceLocation id;

        private Entry(Class<T> clazz, IArgumentSerializer<T> iArgumentSerializer, ResourceLocation resourceLocation) {
            this.argumentClass = clazz;
            this.serializer = iArgumentSerializer;
            this.id = resourceLocation;
        }
    }
}


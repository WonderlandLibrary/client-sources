/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.advancements;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.HoverEvent;
import org.apache.commons.lang3.ArrayUtils;

public class Advancement {
    private final Advancement parent;
    private final DisplayInfo display;
    private final AdvancementRewards rewards;
    private final ResourceLocation id;
    private final Map<String, Criterion> criteria;
    private final String[][] requirements;
    private final Set<Advancement> children = Sets.newLinkedHashSet();
    private final ITextComponent displayText;

    public Advancement(ResourceLocation resourceLocation, @Nullable Advancement advancement, @Nullable DisplayInfo displayInfo, AdvancementRewards advancementRewards, Map<String, Criterion> map, String[][] stringArray) {
        this.id = resourceLocation;
        this.display = displayInfo;
        this.criteria = ImmutableMap.copyOf(map);
        this.parent = advancement;
        this.rewards = advancementRewards;
        this.requirements = stringArray;
        if (advancement != null) {
            advancement.addChild(this);
        }
        if (displayInfo == null) {
            this.displayText = new StringTextComponent(resourceLocation.toString());
        } else {
            ITextComponent iTextComponent = displayInfo.getTitle();
            TextFormatting textFormatting = displayInfo.getFrame().getFormat();
            IFormattableTextComponent iFormattableTextComponent = TextComponentUtils.func_240648_a_(iTextComponent.deepCopy(), Style.EMPTY.setFormatting(textFormatting)).appendString("\n").append(displayInfo.getDescription());
            IFormattableTextComponent iFormattableTextComponent2 = iTextComponent.deepCopy().modifyStyle(arg_0 -> Advancement.lambda$new$0(iFormattableTextComponent, arg_0));
            this.displayText = TextComponentUtils.wrapWithSquareBrackets(iFormattableTextComponent2).mergeStyle(textFormatting);
        }
    }

    public Builder copy() {
        return new Builder(this.parent == null ? null : this.parent.getId(), this.display, this.rewards, this.criteria, this.requirements);
    }

    @Nullable
    public Advancement getParent() {
        return this.parent;
    }

    @Nullable
    public DisplayInfo getDisplay() {
        return this.display;
    }

    public AdvancementRewards getRewards() {
        return this.rewards;
    }

    public String toString() {
        return "SimpleAdvancement{id=" + this.getId() + ", parent=" + (Comparable)(this.parent == null ? "null" : this.parent.getId()) + ", display=" + this.display + ", rewards=" + this.rewards + ", criteria=" + this.criteria + ", requirements=" + Arrays.deepToString((Object[])this.requirements) + "}";
    }

    public Iterable<Advancement> getChildren() {
        return this.children;
    }

    public Map<String, Criterion> getCriteria() {
        return this.criteria;
    }

    public int getRequirementCount() {
        return this.requirements.length;
    }

    public void addChild(Advancement advancement) {
        this.children.add(advancement);
    }

    public ResourceLocation getId() {
        return this.id;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof Advancement)) {
            return true;
        }
        Advancement advancement = (Advancement)object;
        return this.id.equals(advancement.id);
    }

    public int hashCode() {
        return this.id.hashCode();
    }

    public String[][] getRequirements() {
        return this.requirements;
    }

    public ITextComponent getDisplayText() {
        return this.displayText;
    }

    private static Style lambda$new$0(ITextComponent iTextComponent, Style style) {
        return style.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, iTextComponent));
    }

    public static class Builder {
        private ResourceLocation parentId;
        private Advancement parent;
        private DisplayInfo display;
        private AdvancementRewards rewards = AdvancementRewards.EMPTY;
        private Map<String, Criterion> criteria = Maps.newLinkedHashMap();
        private String[][] requirements;
        private IRequirementsStrategy requirementsStrategy = IRequirementsStrategy.AND;

        private Builder(@Nullable ResourceLocation resourceLocation, @Nullable DisplayInfo displayInfo, AdvancementRewards advancementRewards, Map<String, Criterion> map, String[][] stringArray) {
            this.parentId = resourceLocation;
            this.display = displayInfo;
            this.rewards = advancementRewards;
            this.criteria = map;
            this.requirements = stringArray;
        }

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder withParent(Advancement advancement) {
            this.parent = advancement;
            return this;
        }

        public Builder withParentId(ResourceLocation resourceLocation) {
            this.parentId = resourceLocation;
            return this;
        }

        public Builder withDisplay(ItemStack itemStack, ITextComponent iTextComponent, ITextComponent iTextComponent2, @Nullable ResourceLocation resourceLocation, FrameType frameType, boolean bl, boolean bl2, boolean bl3) {
            return this.withDisplay(new DisplayInfo(itemStack, iTextComponent, iTextComponent2, resourceLocation, frameType, bl, bl2, bl3));
        }

        public Builder withDisplay(IItemProvider iItemProvider, ITextComponent iTextComponent, ITextComponent iTextComponent2, @Nullable ResourceLocation resourceLocation, FrameType frameType, boolean bl, boolean bl2, boolean bl3) {
            return this.withDisplay(new DisplayInfo(new ItemStack(iItemProvider.asItem()), iTextComponent, iTextComponent2, resourceLocation, frameType, bl, bl2, bl3));
        }

        public Builder withDisplay(DisplayInfo displayInfo) {
            this.display = displayInfo;
            return this;
        }

        public Builder withRewards(AdvancementRewards.Builder builder) {
            return this.withRewards(builder.build());
        }

        public Builder withRewards(AdvancementRewards advancementRewards) {
            this.rewards = advancementRewards;
            return this;
        }

        public Builder withCriterion(String string, ICriterionInstance iCriterionInstance) {
            return this.withCriterion(string, new Criterion(iCriterionInstance));
        }

        public Builder withCriterion(String string, Criterion criterion) {
            if (this.criteria.containsKey(string)) {
                throw new IllegalArgumentException("Duplicate criterion " + string);
            }
            this.criteria.put(string, criterion);
            return this;
        }

        public Builder withRequirementsStrategy(IRequirementsStrategy iRequirementsStrategy) {
            this.requirementsStrategy = iRequirementsStrategy;
            return this;
        }

        public boolean resolveParent(Function<ResourceLocation, Advancement> function) {
            if (this.parentId == null) {
                return false;
            }
            if (this.parent == null) {
                this.parent = function.apply(this.parentId);
            }
            return this.parent != null;
        }

        public Advancement build(ResourceLocation resourceLocation) {
            if (!this.resolveParent(Builder::lambda$build$0)) {
                throw new IllegalStateException("Tried to build incomplete advancement!");
            }
            if (this.requirements == null) {
                this.requirements = this.requirementsStrategy.createRequirements(this.criteria.keySet());
            }
            return new Advancement(resourceLocation, this.parent, this.display, this.rewards, this.criteria, this.requirements);
        }

        public Advancement register(Consumer<Advancement> consumer, String string) {
            Advancement advancement = this.build(new ResourceLocation(string));
            consumer.accept(advancement);
            return advancement;
        }

        public JsonObject serialize() {
            if (this.requirements == null) {
                this.requirements = this.requirementsStrategy.createRequirements(this.criteria.keySet());
            }
            JsonObject jsonObject = new JsonObject();
            if (this.parent != null) {
                jsonObject.addProperty("parent", this.parent.getId().toString());
            } else if (this.parentId != null) {
                jsonObject.addProperty("parent", this.parentId.toString());
            }
            if (this.display != null) {
                jsonObject.add("display", this.display.serialize());
            }
            jsonObject.add("rewards", this.rewards.serialize());
            JsonObject jsonObject2 = new JsonObject();
            for (Map.Entry<String, Criterion> stringArray : this.criteria.entrySet()) {
                jsonObject2.add(stringArray.getKey(), stringArray.getValue().serialize());
            }
            jsonObject.add("criteria", jsonObject2);
            JsonArray jsonArray = new JsonArray();
            for (String[] stringArray : this.requirements) {
                JsonArray jsonArray2 = new JsonArray();
                for (String string : stringArray) {
                    jsonArray2.add(string);
                }
                jsonArray.add(jsonArray2);
            }
            jsonObject.add("requirements", jsonArray);
            return jsonObject;
        }

        public void writeTo(PacketBuffer packetBuffer) {
            if (this.parentId == null) {
                packetBuffer.writeBoolean(true);
            } else {
                packetBuffer.writeBoolean(false);
                packetBuffer.writeResourceLocation(this.parentId);
            }
            if (this.display == null) {
                packetBuffer.writeBoolean(true);
            } else {
                packetBuffer.writeBoolean(false);
                this.display.write(packetBuffer);
            }
            Criterion.serializeToNetwork(this.criteria, packetBuffer);
            packetBuffer.writeVarInt(this.requirements.length);
            for (String[] stringArray : this.requirements) {
                packetBuffer.writeVarInt(stringArray.length);
                for (String string : stringArray) {
                    packetBuffer.writeString(string);
                }
            }
        }

        public String toString() {
            return "Task Advancement{parentId=" + this.parentId + ", display=" + this.display + ", rewards=" + this.rewards + ", criteria=" + this.criteria + ", requirements=" + Arrays.deepToString((Object[])this.requirements) + "}";
        }

        public static Builder deserialize(JsonObject jsonObject, ConditionArrayParser conditionArrayParser) {
            int n;
            ResourceLocation resourceLocation = jsonObject.has("parent") ? new ResourceLocation(JSONUtils.getString(jsonObject, "parent")) : null;
            DisplayInfo displayInfo = jsonObject.has("display") ? DisplayInfo.deserialize(JSONUtils.getJsonObject(jsonObject, "display")) : null;
            AdvancementRewards advancementRewards = jsonObject.has("rewards") ? AdvancementRewards.deserializeRewards(JSONUtils.getJsonObject(jsonObject, "rewards")) : AdvancementRewards.EMPTY;
            Map<String, Criterion> map = Criterion.deserializeAll(JSONUtils.getJsonObject(jsonObject, "criteria"), conditionArrayParser);
            if (map.isEmpty()) {
                throw new JsonSyntaxException("Advancement criteria cannot be empty");
            }
            JsonArray jsonArray = JSONUtils.getJsonArray(jsonObject, "requirements", new JsonArray());
            String[][] stringArray = new String[jsonArray.size()][];
            for (n = 0; n < jsonArray.size(); ++n) {
                JsonArray jsonArray2 = JSONUtils.getJsonArray(jsonArray.get(n), "requirements[" + n + "]");
                stringArray[n] = new String[jsonArray2.size()];
                for (int i = 0; i < jsonArray2.size(); ++i) {
                    stringArray[n][i] = JSONUtils.getString(jsonArray2.get(i), "requirements[" + n + "][" + i + "]");
                }
            }
            if (stringArray.length == 0) {
                stringArray = new String[map.size()][];
                n = 0;
                for (String string : map.keySet()) {
                    stringArray[n++] = new String[]{string};
                }
            }
            for (String[] stringArray2 : stringArray) {
                if (stringArray2.length == 0 && map.isEmpty()) {
                    throw new JsonSyntaxException("Requirement entry cannot be empty");
                }
                String[] stringArray3 = stringArray2;
                int n2 = stringArray3.length;
                for (int i = 0; i < n2; ++i) {
                    String string = stringArray3[i];
                    if (map.containsKey(string)) continue;
                    throw new JsonSyntaxException("Unknown required criterion '" + string + "'");
                }
            }
            for (String string : map.keySet()) {
                int n3 = 0;
                for (String string2 : stringArray) {
                    if (!ArrayUtils.contains((Object[])string2, string)) continue;
                    n3 = 1;
                    break;
                }
                if (n3 != 0) continue;
                throw new JsonSyntaxException("Criterion '" + string + "' isn't a requirement for completion. This isn't supported behaviour, all criteria must be required.");
            }
            return new Builder(resourceLocation, displayInfo, advancementRewards, map, stringArray);
        }

        public static Builder readFrom(PacketBuffer packetBuffer) {
            ResourceLocation resourceLocation = packetBuffer.readBoolean() ? packetBuffer.readResourceLocation() : null;
            DisplayInfo displayInfo = packetBuffer.readBoolean() ? DisplayInfo.read(packetBuffer) : null;
            Map<String, Criterion> map = Criterion.criteriaFromNetwork(packetBuffer);
            String[][] stringArray = new String[packetBuffer.readVarInt()][];
            for (int i = 0; i < stringArray.length; ++i) {
                stringArray[i] = new String[packetBuffer.readVarInt()];
                for (int j = 0; j < stringArray[i].length; ++j) {
                    stringArray[i][j] = packetBuffer.readString(Short.MAX_VALUE);
                }
            }
            return new Builder(resourceLocation, displayInfo, AdvancementRewards.EMPTY, map, stringArray);
        }

        public Map<String, Criterion> getCriteria() {
            return this.criteria;
        }

        private static Advancement lambda$build$0(ResourceLocation resourceLocation) {
            return null;
        }
    }
}


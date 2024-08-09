/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.advancements;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.mojang.datafixers.DataFixer;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementManager;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.CriterionProgress;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.server.SAdvancementInfoPacket;
import net.minecraft.network.play.server.SSelectAdvancementsTabPacket;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.Util;
import net.minecraft.util.datafix.DefaultTypeReferences;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.GameRules;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PlayerAdvancements {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = new GsonBuilder().registerTypeAdapter((Type)((Object)AdvancementProgress.class), new AdvancementProgress.Serializer()).registerTypeAdapter((Type)((Object)ResourceLocation.class), new ResourceLocation.Serializer()).setPrettyPrinting().create();
    private static final TypeToken<Map<ResourceLocation, AdvancementProgress>> MAP_TOKEN = new TypeToken<Map<ResourceLocation, AdvancementProgress>>(){};
    private final DataFixer dataFixer;
    private final PlayerList playerList;
    private final File progressFile;
    private final Map<Advancement, AdvancementProgress> progress = Maps.newLinkedHashMap();
    private final Set<Advancement> visible = Sets.newLinkedHashSet();
    private final Set<Advancement> visibilityChanged = Sets.newLinkedHashSet();
    private final Set<Advancement> progressChanged = Sets.newLinkedHashSet();
    private ServerPlayerEntity player;
    @Nullable
    private Advancement lastSelectedTab;
    private boolean isFirstPacket = true;

    public PlayerAdvancements(DataFixer dataFixer, PlayerList playerList, AdvancementManager advancementManager, File file, ServerPlayerEntity serverPlayerEntity) {
        this.dataFixer = dataFixer;
        this.playerList = playerList;
        this.progressFile = file;
        this.player = serverPlayerEntity;
        this.deserialize(advancementManager);
    }

    public void setPlayer(ServerPlayerEntity serverPlayerEntity) {
        this.player = serverPlayerEntity;
    }

    public void dispose() {
        for (ICriterionTrigger<?> iCriterionTrigger : CriteriaTriggers.getAll()) {
            iCriterionTrigger.removeAllListeners(this);
        }
    }

    public void reset(AdvancementManager advancementManager) {
        this.dispose();
        this.progress.clear();
        this.visible.clear();
        this.visibilityChanged.clear();
        this.progressChanged.clear();
        this.isFirstPacket = true;
        this.lastSelectedTab = null;
        this.deserialize(advancementManager);
    }

    private void registerAchievementListeners(AdvancementManager advancementManager) {
        for (Advancement advancement : advancementManager.getAllAdvancements()) {
            this.registerListeners(advancement);
        }
    }

    private void ensureAllVisible() {
        ArrayList<Advancement> arrayList = Lists.newArrayList();
        for (Map.Entry<Advancement, AdvancementProgress> entry : this.progress.entrySet()) {
            if (!entry.getValue().isDone()) continue;
            arrayList.add(entry.getKey());
            this.progressChanged.add(entry.getKey());
        }
        for (Advancement advancement : arrayList) {
            this.ensureVisibility(advancement);
        }
    }

    private void unlockDefaultAdvancements(AdvancementManager advancementManager) {
        for (Advancement advancement : advancementManager.getAllAdvancements()) {
            if (!advancement.getCriteria().isEmpty()) continue;
            this.grantCriterion(advancement, "");
            advancement.getRewards().apply(this.player);
        }
    }

    private void deserialize(AdvancementManager advancementManager) {
        if (this.progressFile.isFile()) {
            try (JsonReader jsonReader = new JsonReader(new StringReader(Files.toString(this.progressFile, StandardCharsets.UTF_8)));){
                jsonReader.setLenient(true);
                Dynamic<JsonElement> dynamic = new Dynamic<JsonElement>(JsonOps.INSTANCE, Streams.parse(jsonReader));
                if (!dynamic.get("DataVersion").asNumber().result().isPresent()) {
                    dynamic = dynamic.set("DataVersion", dynamic.createInt(1343));
                }
                dynamic = this.dataFixer.update(DefaultTypeReferences.ADVANCEMENTS.getTypeReference(), dynamic, dynamic.get("DataVersion").asInt(0), SharedConstants.getVersion().getWorldVersion());
                dynamic = dynamic.remove("DataVersion");
                Map<ResourceLocation, AdvancementProgress> map = GSON.getAdapter(MAP_TOKEN).fromJsonTree(dynamic.getValue());
                if (map == null) {
                    throw new JsonParseException("Found null for advancements");
                }
                Stream<Map.Entry> stream = map.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getValue));
                for (Map.Entry entry : stream.collect(Collectors.toList())) {
                    Advancement advancement = advancementManager.getAdvancement((ResourceLocation)entry.getKey());
                    if (advancement == null) {
                        LOGGER.warn("Ignored advancement '{}' in progress file {} - it doesn't exist anymore?", entry.getKey(), (Object)this.progressFile);
                        continue;
                    }
                    this.startProgress(advancement, (AdvancementProgress)entry.getValue());
                }
            } catch (JsonParseException jsonParseException) {
                LOGGER.error("Couldn't parse player advancements in {}", (Object)this.progressFile, (Object)jsonParseException);
            } catch (IOException iOException) {
                LOGGER.error("Couldn't access player advancements in {}", (Object)this.progressFile, (Object)iOException);
            }
        }
        this.unlockDefaultAdvancements(advancementManager);
        this.ensureAllVisible();
        this.registerAchievementListeners(advancementManager);
    }

    public void save() {
        Object object;
        HashMap<ResourceLocation, AdvancementProgress> hashMap = Maps.newHashMap();
        for (Map.Entry<Advancement, AdvancementProgress> entry : this.progress.entrySet()) {
            object = entry.getValue();
            if (!((AdvancementProgress)object).hasProgress()) continue;
            hashMap.put(entry.getKey().getId(), (AdvancementProgress)object);
        }
        if (this.progressFile.getParentFile() != null) {
            this.progressFile.getParentFile().mkdirs();
        }
        JsonElement jsonElement = GSON.toJsonTree(hashMap);
        jsonElement.getAsJsonObject().addProperty("DataVersion", SharedConstants.getVersion().getWorldVersion());
        try {
            Map.Entry<Advancement, AdvancementProgress> entry;
            entry = new FileOutputStream(this.progressFile);
            try {
                object = new OutputStreamWriter((OutputStream)((Object)entry), Charsets.UTF_8.newEncoder());
                try {
                    GSON.toJson(jsonElement, (Appendable)object);
                } finally {
                    ((Writer)object).close();
                }
            } finally {
                ((OutputStream)((Object)entry)).close();
            }
        } catch (IOException iOException) {
            LOGGER.error("Couldn't save player advancements to {}", (Object)this.progressFile, (Object)iOException);
        }
    }

    public boolean grantCriterion(Advancement advancement, String string) {
        boolean bl = false;
        AdvancementProgress advancementProgress = this.getProgress(advancement);
        boolean bl2 = advancementProgress.isDone();
        if (advancementProgress.grantCriterion(string)) {
            this.unregisterListeners(advancement);
            this.progressChanged.add(advancement);
            bl = true;
            if (!bl2 && advancementProgress.isDone()) {
                advancement.getRewards().apply(this.player);
                if (advancement.getDisplay() != null && advancement.getDisplay().shouldAnnounceToChat() && this.player.world.getGameRules().getBoolean(GameRules.ANNOUNCE_ADVANCEMENTS)) {
                    this.playerList.func_232641_a_(new TranslationTextComponent("chat.type.advancement." + advancement.getDisplay().getFrame().getName(), this.player.getDisplayName(), advancement.getDisplayText()), ChatType.SYSTEM, Util.DUMMY_UUID);
                }
            }
        }
        if (advancementProgress.isDone()) {
            this.ensureVisibility(advancement);
        }
        return bl;
    }

    public boolean revokeCriterion(Advancement advancement, String string) {
        boolean bl = false;
        AdvancementProgress advancementProgress = this.getProgress(advancement);
        if (advancementProgress.revokeCriterion(string)) {
            this.registerListeners(advancement);
            this.progressChanged.add(advancement);
            bl = true;
        }
        if (!advancementProgress.hasProgress()) {
            this.ensureVisibility(advancement);
        }
        return bl;
    }

    private void registerListeners(Advancement advancement) {
        AdvancementProgress advancementProgress = this.getProgress(advancement);
        if (!advancementProgress.isDone()) {
            for (Map.Entry<String, Criterion> entry : advancement.getCriteria().entrySet()) {
                ICriterionTrigger<ICriterionInstance> iCriterionTrigger;
                ICriterionInstance iCriterionInstance;
                CriterionProgress criterionProgress = advancementProgress.getCriterionProgress(entry.getKey());
                if (criterionProgress == null || criterionProgress.isObtained() || (iCriterionInstance = entry.getValue().getCriterionInstance()) == null || (iCriterionTrigger = CriteriaTriggers.get(iCriterionInstance.getId())) == null) continue;
                iCriterionTrigger.addListener(this, new ICriterionTrigger.Listener<ICriterionInstance>(iCriterionInstance, advancement, entry.getKey()));
            }
        }
    }

    private void unregisterListeners(Advancement advancement) {
        AdvancementProgress advancementProgress = this.getProgress(advancement);
        for (Map.Entry<String, Criterion> entry : advancement.getCriteria().entrySet()) {
            ICriterionTrigger<ICriterionInstance> iCriterionTrigger;
            ICriterionInstance iCriterionInstance;
            CriterionProgress criterionProgress = advancementProgress.getCriterionProgress(entry.getKey());
            if (criterionProgress == null || !criterionProgress.isObtained() && !advancementProgress.isDone() || (iCriterionInstance = entry.getValue().getCriterionInstance()) == null || (iCriterionTrigger = CriteriaTriggers.get(iCriterionInstance.getId())) == null) continue;
            iCriterionTrigger.removeListener(this, new ICriterionTrigger.Listener<ICriterionInstance>(iCriterionInstance, advancement, entry.getKey()));
        }
    }

    public void flushDirty(ServerPlayerEntity serverPlayerEntity) {
        if (this.isFirstPacket || !this.visibilityChanged.isEmpty() || !this.progressChanged.isEmpty()) {
            HashMap<ResourceLocation, AdvancementProgress> hashMap = Maps.newHashMap();
            LinkedHashSet<Advancement> linkedHashSet = Sets.newLinkedHashSet();
            LinkedHashSet<ResourceLocation> linkedHashSet2 = Sets.newLinkedHashSet();
            for (Advancement advancement : this.progressChanged) {
                if (!this.visible.contains(advancement)) continue;
                hashMap.put(advancement.getId(), this.progress.get(advancement));
            }
            for (Advancement advancement : this.visibilityChanged) {
                if (this.visible.contains(advancement)) {
                    linkedHashSet.add(advancement);
                    continue;
                }
                linkedHashSet2.add(advancement.getId());
            }
            if (this.isFirstPacket || !hashMap.isEmpty() || !linkedHashSet.isEmpty() || !linkedHashSet2.isEmpty()) {
                serverPlayerEntity.connection.sendPacket(new SAdvancementInfoPacket(this.isFirstPacket, linkedHashSet, linkedHashSet2, hashMap));
                this.visibilityChanged.clear();
                this.progressChanged.clear();
            }
        }
        this.isFirstPacket = false;
    }

    public void setSelectedTab(@Nullable Advancement advancement) {
        Advancement advancement2 = this.lastSelectedTab;
        this.lastSelectedTab = advancement != null && advancement.getParent() == null && advancement.getDisplay() != null ? advancement : null;
        if (advancement2 != this.lastSelectedTab) {
            this.player.connection.sendPacket(new SSelectAdvancementsTabPacket(this.lastSelectedTab == null ? null : this.lastSelectedTab.getId()));
        }
    }

    public AdvancementProgress getProgress(Advancement advancement) {
        AdvancementProgress advancementProgress = this.progress.get(advancement);
        if (advancementProgress == null) {
            advancementProgress = new AdvancementProgress();
            this.startProgress(advancement, advancementProgress);
        }
        return advancementProgress;
    }

    private void startProgress(Advancement advancement, AdvancementProgress advancementProgress) {
        advancementProgress.update(advancement.getCriteria(), advancement.getRequirements());
        this.progress.put(advancement, advancementProgress);
    }

    private void ensureVisibility(Advancement advancement) {
        boolean bl = this.shouldBeVisible(advancement);
        boolean bl2 = this.visible.contains(advancement);
        if (bl && !bl2) {
            this.visible.add(advancement);
            this.visibilityChanged.add(advancement);
            if (this.progress.containsKey(advancement)) {
                this.progressChanged.add(advancement);
            }
        } else if (!bl && bl2) {
            this.visible.remove(advancement);
            this.visibilityChanged.add(advancement);
        }
        if (bl != bl2 && advancement.getParent() != null) {
            this.ensureVisibility(advancement.getParent());
        }
        for (Advancement advancement2 : advancement.getChildren()) {
            this.ensureVisibility(advancement2);
        }
    }

    private boolean shouldBeVisible(Advancement advancement) {
        for (int n = 0; advancement != null && n <= 2; advancement = advancement.getParent(), ++n) {
            if (n == 0 && this.hasCompletedChildrenOrSelf(advancement)) {
                return false;
            }
            if (advancement.getDisplay() == null) {
                return true;
            }
            AdvancementProgress advancementProgress = this.getProgress(advancement);
            if (advancementProgress.isDone()) {
                return false;
            }
            if (!advancement.getDisplay().isHidden()) continue;
            return true;
        }
        return true;
    }

    private boolean hasCompletedChildrenOrSelf(Advancement advancement) {
        AdvancementProgress advancementProgress = this.getProgress(advancement);
        if (advancementProgress.isDone()) {
            return false;
        }
        for (Advancement advancement2 : advancement.getChildren()) {
            if (!this.hasCompletedChildrenOrSelf(advancement2)) continue;
            return false;
        }
        return true;
    }
}


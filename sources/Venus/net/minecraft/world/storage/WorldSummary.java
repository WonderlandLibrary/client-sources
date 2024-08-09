/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.storage;

import java.io.File;
import javax.annotation.Nullable;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.GameType;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.storage.VersionData;
import org.apache.commons.lang3.StringUtils;

public class WorldSummary
implements Comparable<WorldSummary> {
    private final WorldSettings settings;
    private final VersionData versionData;
    private final String fileName;
    private final boolean requiresConversion;
    private final boolean locked;
    private final File iconFile;
    @Nullable
    private ITextComponent description;

    public WorldSummary(WorldSettings worldSettings, VersionData versionData, String string, boolean bl, boolean bl2, File file) {
        this.settings = worldSettings;
        this.versionData = versionData;
        this.fileName = string;
        this.locked = bl2;
        this.iconFile = file;
        this.requiresConversion = bl;
    }

    public String getFileName() {
        return this.fileName;
    }

    public String getDisplayName() {
        return StringUtils.isEmpty(this.settings.getWorldName()) ? this.fileName : this.settings.getWorldName();
    }

    public File getIconFile() {
        return this.iconFile;
    }

    public boolean requiresConversion() {
        return this.requiresConversion;
    }

    public long getLastTimePlayed() {
        return this.versionData.getLastPlayed();
    }

    @Override
    public int compareTo(WorldSummary worldSummary) {
        if (this.versionData.getLastPlayed() < worldSummary.versionData.getLastPlayed()) {
            return 0;
        }
        return this.versionData.getLastPlayed() > worldSummary.versionData.getLastPlayed() ? -1 : this.fileName.compareTo(worldSummary.fileName);
    }

    public GameType getEnumGameType() {
        return this.settings.getGameType();
    }

    public boolean isHardcoreModeEnabled() {
        return this.settings.isHardcoreEnabled();
    }

    public boolean getCheatsEnabled() {
        return this.settings.isCommandsAllowed();
    }

    public IFormattableTextComponent getVersionName() {
        return net.minecraft.util.StringUtils.isNullOrEmpty(this.versionData.getName()) ? new TranslationTextComponent("selectWorld.versionUnknown") : new StringTextComponent(this.versionData.getName());
    }

    public VersionData getVersionData() {
        return this.versionData;
    }

    public boolean markVersionInList() {
        return this.askToOpenWorld() || !SharedConstants.getVersion().isStable() && !this.versionData.isSnapshot() || this.askToCreateBackup();
    }

    public boolean askToOpenWorld() {
        return this.versionData.getID() > SharedConstants.getVersion().getWorldVersion();
    }

    public boolean askToCreateBackup() {
        return this.versionData.getID() < SharedConstants.getVersion().getWorldVersion();
    }

    public boolean isLocked() {
        return this.locked;
    }

    public ITextComponent getDescription() {
        if (this.description == null) {
            this.description = this.createDescription();
        }
        return this.description;
    }

    private ITextComponent createDescription() {
        TranslationTextComponent translationTextComponent;
        if (this.isLocked()) {
            return new TranslationTextComponent("selectWorld.locked").mergeStyle(TextFormatting.RED);
        }
        if (this.requiresConversion()) {
            return new TranslationTextComponent("selectWorld.conversion");
        }
        IFormattableTextComponent iFormattableTextComponent = translationTextComponent = this.isHardcoreModeEnabled() ? new StringTextComponent("").append(new TranslationTextComponent("gameMode.hardcore").mergeStyle(TextFormatting.DARK_RED)) : new TranslationTextComponent("gameMode." + this.getEnumGameType().getName());
        if (this.getCheatsEnabled()) {
            translationTextComponent.appendString(", ").append(new TranslationTextComponent("selectWorld.cheats"));
        }
        IFormattableTextComponent iFormattableTextComponent2 = this.getVersionName();
        IFormattableTextComponent iFormattableTextComponent3 = new StringTextComponent(", ").append(new TranslationTextComponent("selectWorld.version")).appendString(" ");
        if (this.markVersionInList()) {
            iFormattableTextComponent3.append(iFormattableTextComponent2.mergeStyle(this.askToOpenWorld() ? TextFormatting.RED : TextFormatting.ITALIC));
        } else {
            iFormattableTextComponent3.append(iFormattableTextComponent2);
        }
        translationTextComponent.append(iFormattableTextComponent3);
        return translationTextComponent;
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((WorldSummary)object);
    }
}


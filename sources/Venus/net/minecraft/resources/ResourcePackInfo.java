/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.resources;

import com.mojang.brigadier.arguments.StringArgumentType;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.resources.IPackNameDecorator;
import net.minecraft.resources.IResourcePack;
import net.minecraft.resources.PackCompatibility;
import net.minecraft.resources.data.PackMetadataSection;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.text.event.HoverEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ResourcePackInfo
implements AutoCloseable {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final PackMetadataSection BROKEN_ASSETS_FALLBACK = new PackMetadataSection(new TranslationTextComponent("resourcePack.broken_assets").mergeStyle(TextFormatting.RED, TextFormatting.ITALIC), SharedConstants.getVersion().getPackVersion());
    private final String name;
    private final Supplier<IResourcePack> resourcePackSupplier;
    private final ITextComponent title;
    private final ITextComponent description;
    private final PackCompatibility compatibility;
    private final Priority priority;
    private final boolean alwaysEnabled;
    private final boolean orderLocked;
    private final IPackNameDecorator decorator;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Nullable
    public static ResourcePackInfo createResourcePack(String string, boolean bl, Supplier<IResourcePack> supplier, IFactory iFactory, Priority priority, IPackNameDecorator iPackNameDecorator) {
        try (IResourcePack iResourcePack = supplier.get();){
            PackMetadataSection packMetadataSection = iResourcePack.getMetadata(PackMetadataSection.SERIALIZER);
            if (bl && packMetadataSection == null) {
                LOGGER.error("Broken/missing pack.mcmeta detected, fudging it into existance. Please check that your launcher has downloaded all assets for the game correctly!");
                packMetadataSection = BROKEN_ASSETS_FALLBACK;
            }
            if (packMetadataSection != null) {
                ResourcePackInfo resourcePackInfo = iFactory.create(string, bl, supplier, iResourcePack, packMetadataSection, priority, iPackNameDecorator);
                return resourcePackInfo;
            }
            LOGGER.warn("Couldn't find pack meta for pack {}", (Object)string);
            return null;
        } catch (IOException iOException) {
            LOGGER.warn("Couldn't get pack info for: {}", (Object)iOException.toString());
        }
        return null;
    }

    public ResourcePackInfo(String string, boolean bl, Supplier<IResourcePack> supplier, ITextComponent iTextComponent, ITextComponent iTextComponent2, PackCompatibility packCompatibility, Priority priority, boolean bl2, IPackNameDecorator iPackNameDecorator) {
        this.name = string;
        this.resourcePackSupplier = supplier;
        this.title = iTextComponent;
        this.description = iTextComponent2;
        this.compatibility = packCompatibility;
        this.alwaysEnabled = bl;
        this.priority = priority;
        this.orderLocked = bl2;
        this.decorator = iPackNameDecorator;
    }

    public ResourcePackInfo(String string, boolean bl, Supplier<IResourcePack> supplier, IResourcePack iResourcePack, PackMetadataSection packMetadataSection, Priority priority, IPackNameDecorator iPackNameDecorator) {
        this(string, bl, supplier, new StringTextComponent(iResourcePack.getName()), packMetadataSection.getDescription(), PackCompatibility.getCompatibility(packMetadataSection.getPackFormat()), priority, false, iPackNameDecorator);
    }

    public ITextComponent getTitle() {
        return this.title;
    }

    public ITextComponent getDescription() {
        return this.description;
    }

    public ITextComponent getChatLink(boolean bl) {
        return TextComponentUtils.wrapWithSquareBrackets(this.decorator.decorate(new StringTextComponent(this.name))).modifyStyle(arg_0 -> this.lambda$getChatLink$0(bl, arg_0));
    }

    public PackCompatibility getCompatibility() {
        return this.compatibility;
    }

    public IResourcePack getResourcePack() {
        return this.resourcePackSupplier.get();
    }

    public String getName() {
        return this.name;
    }

    public boolean isAlwaysEnabled() {
        return this.alwaysEnabled;
    }

    public boolean isOrderLocked() {
        return this.orderLocked;
    }

    public Priority getPriority() {
        return this.priority;
    }

    public IPackNameDecorator getDecorator() {
        return this.decorator;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof ResourcePackInfo)) {
            return true;
        }
        ResourcePackInfo resourcePackInfo = (ResourcePackInfo)object;
        return this.name.equals(resourcePackInfo.name);
    }

    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public void close() {
    }

    private Style lambda$getChatLink$0(boolean bl, Style style) {
        return style.setFormatting(bl ? TextFormatting.GREEN : TextFormatting.RED).setInsertion(StringArgumentType.escapeIfRequired(this.name)).setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new StringTextComponent("").append(this.title).appendString("\n").append(this.description)));
    }

    @FunctionalInterface
    public static interface IFactory {
        @Nullable
        public ResourcePackInfo create(String var1, boolean var2, Supplier<IResourcePack> var3, IResourcePack var4, PackMetadataSection var5, Priority var6, IPackNameDecorator var7);
    }

    public static enum Priority {
        TOP,
        BOTTOM;


        public <T> int insert(List<T> list, T t, Function<T, ResourcePackInfo> function, boolean bl) {
            ResourcePackInfo resourcePackInfo;
            int n;
            Priority priority;
            Priority priority2 = priority = bl ? this.opposite() : this;
            if (priority == BOTTOM) {
                ResourcePackInfo resourcePackInfo2;
                int n2;
                for (n2 = 0; n2 < list.size() && (resourcePackInfo2 = function.apply(list.get(n2))).isOrderLocked() && resourcePackInfo2.getPriority() == this; ++n2) {
                }
                list.add(n2, t);
                return n2;
            }
            for (n = list.size() - 1; n >= 0 && (resourcePackInfo = function.apply(list.get(n))).isOrderLocked() && resourcePackInfo.getPriority() == this; --n) {
            }
            list.add(n + 1, t);
            return n + 1;
        }

        public Priority opposite() {
            return this == TOP ? BOTTOM : TOP;
        }
    }
}


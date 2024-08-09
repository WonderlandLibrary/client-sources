/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.text;

import com.google.common.base.Joiner;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.advancements.criterion.NBTPredicate;
import net.minecraft.command.CommandSource;
import net.minecraft.command.arguments.BlockPosArgument;
import net.minecraft.command.arguments.EntitySelector;
import net.minecraft.command.arguments.EntitySelectorParser;
import net.minecraft.command.arguments.ILocationArgument;
import net.minecraft.command.arguments.NBTPathArgument;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITargetedTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.world.server.ServerWorld;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class NBTTextComponent
extends TextComponent
implements ITargetedTextComponent {
    private static final Logger field_218681_e = LogManager.getLogger();
    protected final boolean field_218678_b;
    protected final String field_218679_c;
    @Nullable
    protected final NBTPathArgument.NBTPath field_218680_d;

    @Nullable
    private static NBTPathArgument.NBTPath func_218672_b(String string) {
        try {
            return new NBTPathArgument().parse(new StringReader(string));
        } catch (CommandSyntaxException commandSyntaxException) {
            return null;
        }
    }

    public NBTTextComponent(String string, boolean bl) {
        this(string, NBTTextComponent.func_218672_b(string), bl);
    }

    protected NBTTextComponent(String string, @Nullable NBTPathArgument.NBTPath nBTPath, boolean bl) {
        this.field_218679_c = string;
        this.field_218680_d = nBTPath;
        this.field_218678_b = bl;
    }

    protected abstract Stream<CompoundNBT> func_218673_a(CommandSource var1) throws CommandSyntaxException;

    public String func_218676_i() {
        return this.field_218679_c;
    }

    public boolean func_218677_j() {
        return this.field_218678_b;
    }

    @Override
    public IFormattableTextComponent func_230535_a_(@Nullable CommandSource commandSource, @Nullable net.minecraft.entity.Entity entity2, int n) throws CommandSyntaxException {
        if (commandSource != null && this.field_218680_d != null) {
            Stream<String> stream = this.func_218673_a(commandSource).flatMap(this::lambda$func_230535_a_$0).map(INBT::getString);
            return this.field_218678_b ? (IFormattableTextComponent)stream.flatMap(arg_0 -> NBTTextComponent.lambda$func_230535_a_$1(commandSource, entity2, n, arg_0)).reduce(NBTTextComponent::lambda$func_230535_a_$2).orElse(new StringTextComponent("")) : new StringTextComponent(Joiner.on(", ").join(stream.iterator()));
        }
        return new StringTextComponent("");
    }

    private static IFormattableTextComponent lambda$func_230535_a_$2(IFormattableTextComponent iFormattableTextComponent, IFormattableTextComponent iFormattableTextComponent2) {
        return iFormattableTextComponent.appendString(", ").append(iFormattableTextComponent2);
    }

    private static Stream lambda$func_230535_a_$1(CommandSource commandSource, net.minecraft.entity.Entity entity2, int n, String string) {
        try {
            IFormattableTextComponent iFormattableTextComponent = ITextComponent.Serializer.getComponentFromJson(string);
            return Stream.of(TextComponentUtils.func_240645_a_(commandSource, iFormattableTextComponent, entity2, n));
        } catch (Exception exception) {
            field_218681_e.warn("Failed to parse component: " + string, (Throwable)exception);
            return Stream.of(new IFormattableTextComponent[0]);
        }
    }

    private Stream lambda$func_230535_a_$0(CompoundNBT compoundNBT) {
        try {
            return this.field_218680_d.func_218071_a(compoundNBT).stream();
        } catch (CommandSyntaxException commandSyntaxException) {
            return Stream.empty();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Storage
    extends NBTTextComponent {
        private final ResourceLocation field_229725_e_;

        public Storage(String string, boolean bl, ResourceLocation resourceLocation) {
            super(string, bl);
            this.field_229725_e_ = resourceLocation;
        }

        public Storage(String string, @Nullable NBTPathArgument.NBTPath nBTPath, boolean bl, ResourceLocation resourceLocation) {
            super(string, nBTPath, bl);
            this.field_229725_e_ = resourceLocation;
        }

        public ResourceLocation func_229726_k_() {
            return this.field_229725_e_;
        }

        @Override
        public Storage copyRaw() {
            return new Storage(this.field_218679_c, this.field_218680_d, this.field_218678_b, this.field_229725_e_);
        }

        @Override
        protected Stream<CompoundNBT> func_218673_a(CommandSource commandSource) {
            CompoundNBT compoundNBT = commandSource.getServer().func_229735_aN_().getData(this.field_229725_e_);
            return Stream.of(compoundNBT);
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) {
                return false;
            }
            if (!(object instanceof Storage)) {
                return true;
            }
            Storage storage = (Storage)object;
            return Objects.equals(this.field_229725_e_, storage.field_229725_e_) && Objects.equals(this.field_218679_c, storage.field_218679_c) && super.equals(object);
        }

        @Override
        public String toString() {
            return "StorageNbtComponent{id='" + this.field_229725_e_ + "'path='" + this.field_218679_c + "', siblings=" + this.siblings + ", style=" + this.getStyle() + "}";
        }

        @Override
        public TextComponent copyRaw() {
            return this.copyRaw();
        }

        @Override
        public IFormattableTextComponent copyRaw() {
            return this.copyRaw();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Entity
    extends NBTTextComponent {
        private final String field_218688_e;
        @Nullable
        private final EntitySelector field_218689_f;

        public Entity(String string, boolean bl, String string2) {
            super(string, bl);
            this.field_218688_e = string2;
            this.field_218689_f = Entity.func_218686_b(string2);
        }

        @Nullable
        private static EntitySelector func_218686_b(String string) {
            try {
                EntitySelectorParser entitySelectorParser = new EntitySelectorParser(new StringReader(string));
                return entitySelectorParser.parse();
            } catch (CommandSyntaxException commandSyntaxException) {
                return null;
            }
        }

        private Entity(String string, @Nullable NBTPathArgument.NBTPath nBTPath, boolean bl, String string2, @Nullable EntitySelector entitySelector) {
            super(string, nBTPath, bl);
            this.field_218688_e = string2;
            this.field_218689_f = entitySelector;
        }

        public String func_218687_k() {
            return this.field_218688_e;
        }

        @Override
        public Entity copyRaw() {
            return new Entity(this.field_218679_c, this.field_218680_d, this.field_218678_b, this.field_218688_e, this.field_218689_f);
        }

        @Override
        protected Stream<CompoundNBT> func_218673_a(CommandSource commandSource) throws CommandSyntaxException {
            if (this.field_218689_f != null) {
                List<? extends net.minecraft.entity.Entity> list = this.field_218689_f.select(commandSource);
                return list.stream().map(NBTPredicate::writeToNBTWithSelectedItem);
            }
            return Stream.empty();
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) {
                return false;
            }
            if (!(object instanceof Entity)) {
                return true;
            }
            Entity entity2 = (Entity)object;
            return Objects.equals(this.field_218688_e, entity2.field_218688_e) && Objects.equals(this.field_218679_c, entity2.field_218679_c) && super.equals(object);
        }

        @Override
        public String toString() {
            return "EntityNbtComponent{selector='" + this.field_218688_e + "'path='" + this.field_218679_c + "', siblings=" + this.siblings + ", style=" + this.getStyle() + "}";
        }

        @Override
        public TextComponent copyRaw() {
            return this.copyRaw();
        }

        @Override
        public IFormattableTextComponent copyRaw() {
            return this.copyRaw();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Block
    extends NBTTextComponent {
        private final String field_218684_e;
        @Nullable
        private final ILocationArgument field_218685_f;

        public Block(String string, boolean bl, String string2) {
            super(string, bl);
            this.field_218684_e = string2;
            this.field_218685_f = this.func_218682_b(this.field_218684_e);
        }

        @Nullable
        private ILocationArgument func_218682_b(String string) {
            try {
                return BlockPosArgument.blockPos().parse(new StringReader(string));
            } catch (CommandSyntaxException commandSyntaxException) {
                return null;
            }
        }

        private Block(String string, @Nullable NBTPathArgument.NBTPath nBTPath, boolean bl, String string2, @Nullable ILocationArgument iLocationArgument) {
            super(string, nBTPath, bl);
            this.field_218684_e = string2;
            this.field_218685_f = iLocationArgument;
        }

        @Nullable
        public String func_218683_k() {
            return this.field_218684_e;
        }

        @Override
        public Block copyRaw() {
            return new Block(this.field_218679_c, this.field_218680_d, this.field_218678_b, this.field_218684_e, this.field_218685_f);
        }

        @Override
        protected Stream<CompoundNBT> func_218673_a(CommandSource commandSource) {
            TileEntity tileEntity;
            BlockPos blockPos;
            ServerWorld serverWorld;
            if (this.field_218685_f != null && (serverWorld = commandSource.getWorld()).isBlockPresent(blockPos = this.field_218685_f.getBlockPos(commandSource)) && (tileEntity = serverWorld.getTileEntity(blockPos)) != null) {
                return Stream.of(tileEntity.write(new CompoundNBT()));
            }
            return Stream.empty();
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) {
                return false;
            }
            if (!(object instanceof Block)) {
                return true;
            }
            Block block = (Block)object;
            return Objects.equals(this.field_218684_e, block.field_218684_e) && Objects.equals(this.field_218679_c, block.field_218679_c) && super.equals(object);
        }

        @Override
        public String toString() {
            return "BlockPosArgument{pos='" + this.field_218684_e + "'path='" + this.field_218679_c + "', siblings=" + this.siblings + ", style=" + this.getStyle() + "}";
        }

        @Override
        public TextComponent copyRaw() {
            return this.copyRaw();
        }

        @Override
        public IFormattableTextComponent copyRaw() {
            return this.copyRaw();
        }
    }
}


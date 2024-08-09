/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command;

import com.google.common.collect.Lists;
import com.mojang.brigadier.ResultConsumer;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.command.ICommandSource;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.DimensionType;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class CommandSource
implements ISuggestionProvider {
    public static final SimpleCommandExceptionType REQUIRES_PLAYER_EXCEPTION_TYPE = new SimpleCommandExceptionType(new TranslationTextComponent("permissions.requires.player"));
    public static final SimpleCommandExceptionType REQUIRES_ENTITY_EXCEPTION_TYPE = new SimpleCommandExceptionType(new TranslationTextComponent("permissions.requires.entity"));
    private final ICommandSource source;
    private final Vector3d pos;
    private final ServerWorld world;
    private final int permissionLevel;
    private final String name;
    private final ITextComponent displayName;
    private final MinecraftServer server;
    private final boolean feedbackDisabled;
    @Nullable
    private final Entity entity;
    private final ResultConsumer<CommandSource> resultConsumer;
    private final EntityAnchorArgument.Type entityAnchorType;
    private final Vector2f rotation;

    public CommandSource(ICommandSource iCommandSource, Vector3d vector3d, Vector2f vector2f, ServerWorld serverWorld, int n, String string, ITextComponent iTextComponent, MinecraftServer minecraftServer, @Nullable Entity entity2) {
        this(iCommandSource, vector3d, vector2f, serverWorld, n, string, iTextComponent, minecraftServer, entity2, false, CommandSource::lambda$new$0, EntityAnchorArgument.Type.FEET);
    }

    protected CommandSource(ICommandSource iCommandSource, Vector3d vector3d, Vector2f vector2f, ServerWorld serverWorld, int n, String string, ITextComponent iTextComponent, MinecraftServer minecraftServer, @Nullable Entity entity2, boolean bl, ResultConsumer<CommandSource> resultConsumer, EntityAnchorArgument.Type type) {
        this.source = iCommandSource;
        this.pos = vector3d;
        this.world = serverWorld;
        this.feedbackDisabled = bl;
        this.entity = entity2;
        this.permissionLevel = n;
        this.name = string;
        this.displayName = iTextComponent;
        this.server = minecraftServer;
        this.resultConsumer = resultConsumer;
        this.entityAnchorType = type;
        this.rotation = vector2f;
    }

    public CommandSource withEntity(Entity entity2) {
        return this.entity == entity2 ? this : new CommandSource(this.source, this.pos, this.rotation, this.world, this.permissionLevel, entity2.getName().getString(), entity2.getDisplayName(), this.server, entity2, this.feedbackDisabled, this.resultConsumer, this.entityAnchorType);
    }

    public CommandSource withPos(Vector3d vector3d) {
        return this.pos.equals(vector3d) ? this : new CommandSource(this.source, vector3d, this.rotation, this.world, this.permissionLevel, this.name, this.displayName, this.server, this.entity, this.feedbackDisabled, this.resultConsumer, this.entityAnchorType);
    }

    public CommandSource withRotation(Vector2f vector2f) {
        return this.rotation.equals(vector2f) ? this : new CommandSource(this.source, this.pos, vector2f, this.world, this.permissionLevel, this.name, this.displayName, this.server, this.entity, this.feedbackDisabled, this.resultConsumer, this.entityAnchorType);
    }

    public CommandSource withResultConsumer(ResultConsumer<CommandSource> resultConsumer) {
        return this.resultConsumer.equals(resultConsumer) ? this : new CommandSource(this.source, this.pos, this.rotation, this.world, this.permissionLevel, this.name, this.displayName, this.server, this.entity, this.feedbackDisabled, resultConsumer, this.entityAnchorType);
    }

    public CommandSource withResultConsumer(ResultConsumer<CommandSource> resultConsumer, BinaryOperator<ResultConsumer<CommandSource>> binaryOperator) {
        ResultConsumer resultConsumer2 = (ResultConsumer)binaryOperator.apply(this.resultConsumer, resultConsumer);
        return this.withResultConsumer(resultConsumer2);
    }

    public CommandSource withFeedbackDisabled() {
        return this.feedbackDisabled ? this : new CommandSource(this.source, this.pos, this.rotation, this.world, this.permissionLevel, this.name, this.displayName, this.server, this.entity, true, this.resultConsumer, this.entityAnchorType);
    }

    public CommandSource withPermissionLevel(int n) {
        return n == this.permissionLevel ? this : new CommandSource(this.source, this.pos, this.rotation, this.world, n, this.name, this.displayName, this.server, this.entity, this.feedbackDisabled, this.resultConsumer, this.entityAnchorType);
    }

    public CommandSource withMinPermissionLevel(int n) {
        return n <= this.permissionLevel ? this : new CommandSource(this.source, this.pos, this.rotation, this.world, n, this.name, this.displayName, this.server, this.entity, this.feedbackDisabled, this.resultConsumer, this.entityAnchorType);
    }

    public CommandSource withEntityAnchorType(EntityAnchorArgument.Type type) {
        return type == this.entityAnchorType ? this : new CommandSource(this.source, this.pos, this.rotation, this.world, this.permissionLevel, this.name, this.displayName, this.server, this.entity, this.feedbackDisabled, this.resultConsumer, type);
    }

    public CommandSource withWorld(ServerWorld serverWorld) {
        if (serverWorld == this.world) {
            return this;
        }
        double d = DimensionType.getCoordinateDifference(this.world.getDimensionType(), serverWorld.getDimensionType());
        Vector3d vector3d = new Vector3d(this.pos.x * d, this.pos.y, this.pos.z * d);
        return new CommandSource(this.source, vector3d, this.rotation, serverWorld, this.permissionLevel, this.name, this.displayName, this.server, this.entity, this.feedbackDisabled, this.resultConsumer, this.entityAnchorType);
    }

    public CommandSource withRotation(Entity entity2, EntityAnchorArgument.Type type) throws CommandSyntaxException {
        return this.withRotation(type.apply(entity2));
    }

    public CommandSource withRotation(Vector3d vector3d) throws CommandSyntaxException {
        Vector3d vector3d2 = this.entityAnchorType.apply(this);
        double d = vector3d.x - vector3d2.x;
        double d2 = vector3d.y - vector3d2.y;
        double d3 = vector3d.z - vector3d2.z;
        double d4 = MathHelper.sqrt(d * d + d3 * d3);
        float f = MathHelper.wrapDegrees((float)(-(MathHelper.atan2(d2, d4) * 57.2957763671875)));
        float f2 = MathHelper.wrapDegrees((float)(MathHelper.atan2(d3, d) * 57.2957763671875) - 90.0f);
        return this.withRotation(new Vector2f(f, f2));
    }

    public ITextComponent getDisplayName() {
        return this.displayName;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public boolean hasPermissionLevel(int n) {
        return this.permissionLevel >= n;
    }

    public Vector3d getPos() {
        return this.pos;
    }

    public ServerWorld getWorld() {
        return this.world;
    }

    @Nullable
    public Entity getEntity() {
        return this.entity;
    }

    public Entity assertIsEntity() throws CommandSyntaxException {
        if (this.entity == null) {
            throw REQUIRES_ENTITY_EXCEPTION_TYPE.create();
        }
        return this.entity;
    }

    public ServerPlayerEntity asPlayer() throws CommandSyntaxException {
        if (!(this.entity instanceof ServerPlayerEntity)) {
            throw REQUIRES_PLAYER_EXCEPTION_TYPE.create();
        }
        return (ServerPlayerEntity)this.entity;
    }

    public Vector2f getRotation() {
        return this.rotation;
    }

    public MinecraftServer getServer() {
        return this.server;
    }

    public EntityAnchorArgument.Type getEntityAnchorType() {
        return this.entityAnchorType;
    }

    public void sendFeedback(ITextComponent iTextComponent, boolean bl) {
        if (this.source.shouldReceiveFeedback() && !this.feedbackDisabled) {
            this.source.sendMessage(iTextComponent, Util.DUMMY_UUID);
        }
        if (bl && this.source.allowLogging() && !this.feedbackDisabled) {
            this.logFeedback(iTextComponent);
        }
    }

    private void logFeedback(ITextComponent iTextComponent) {
        IFormattableTextComponent iFormattableTextComponent = new TranslationTextComponent("chat.type.admin", this.getDisplayName(), iTextComponent).mergeStyle(TextFormatting.GRAY, TextFormatting.ITALIC);
        if (this.server.getGameRules().getBoolean(GameRules.SEND_COMMAND_FEEDBACK)) {
            for (ServerPlayerEntity serverPlayerEntity : this.server.getPlayerList().getPlayers()) {
                if (serverPlayerEntity == this.source || !this.server.getPlayerList().canSendCommands(serverPlayerEntity.getGameProfile())) continue;
                serverPlayerEntity.sendMessage(iFormattableTextComponent, Util.DUMMY_UUID);
            }
        }
        if (this.source != this.server && this.server.getGameRules().getBoolean(GameRules.LOG_ADMIN_COMMANDS)) {
            this.server.sendMessage(iFormattableTextComponent, Util.DUMMY_UUID);
        }
    }

    public void sendErrorMessage(ITextComponent iTextComponent) {
        if (this.source.shouldReceiveErrors() && !this.feedbackDisabled) {
            this.source.sendMessage(new StringTextComponent("").append(iTextComponent).mergeStyle(TextFormatting.RED), Util.DUMMY_UUID);
        }
    }

    public void onCommandComplete(CommandContext<CommandSource> commandContext, boolean bl, int n) {
        if (this.resultConsumer != null) {
            this.resultConsumer.onCommandComplete(commandContext, bl, n);
        }
    }

    @Override
    public Collection<String> getPlayerNames() {
        return Lists.newArrayList(this.server.getOnlinePlayerNames());
    }

    @Override
    public Collection<String> getTeamNames() {
        return this.server.getScoreboard().getTeamNames();
    }

    @Override
    public Collection<ResourceLocation> getSoundResourceLocations() {
        return Registry.SOUND_EVENT.keySet();
    }

    @Override
    public Stream<ResourceLocation> getRecipeResourceLocations() {
        return this.server.getRecipeManager().getKeys();
    }

    @Override
    public CompletableFuture<Suggestions> getSuggestionsFromServer(CommandContext<ISuggestionProvider> commandContext, SuggestionsBuilder suggestionsBuilder) {
        return null;
    }

    @Override
    public Set<RegistryKey<World>> func_230390_p_() {
        return this.server.func_240770_D_();
    }

    @Override
    public DynamicRegistries func_241861_q() {
        return this.server.func_244267_aX();
    }

    private static void lambda$new$0(CommandContext commandContext, boolean bl, int n) {
    }
}


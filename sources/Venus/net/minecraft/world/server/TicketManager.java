/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.server;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Either;
import it.unimi.dsi.fastutil.longs.Long2ByteMap;
import it.unimi.dsi.fastutil.longs.Long2ByteOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2IntMap;
import it.unimi.dsi.fastutil.longs.Long2IntMaps;
import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.SortedArraySet;
import net.minecraft.util.concurrent.ITaskExecutor;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.SectionPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkDistanceGraph;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.ChunkTaskPriorityQueueSorter;
import net.minecraft.world.server.ChunkHolder;
import net.minecraft.world.server.ChunkManager;
import net.minecraft.world.server.Ticket;
import net.minecraft.world.server.TicketType;
import net.optifine.reflect.Reflector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class TicketManager {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int PLAYER_TICKET_LEVEL = 33 + ChunkStatus.getDistance(ChunkStatus.FULL) - 2;
    private final Long2ObjectMap<ObjectSet<ServerPlayerEntity>> playersByChunkPos = new Long2ObjectOpenHashMap<ObjectSet<ServerPlayerEntity>>();
    private final Long2ObjectOpenHashMap<SortedArraySet<Ticket<?>>> tickets = new Long2ObjectOpenHashMap();
    private final ChunkTicketTracker ticketTracker = new ChunkTicketTracker(this);
    private final PlayerChunkTracker playerChunkTracker = new PlayerChunkTracker(this, 8);
    private final PlayerTicketTracker playerTicketTracker = new PlayerTicketTracker(this, 65);
    private final Set<ChunkHolder> chunkHolders = Sets.newHashSet();
    private final ChunkTaskPriorityQueueSorter field_219384_l;
    private final ITaskExecutor<ChunkTaskPriorityQueueSorter.FunctionEntry<Runnable>> field_219385_m;
    private final ITaskExecutor<ChunkTaskPriorityQueueSorter.RunnableEntry> field_219386_n;
    private final LongSet chunkPositions = new LongOpenHashSet();
    private final Executor field_219388_p;
    private long currentTime;
    private final Long2ObjectOpenHashMap<SortedArraySet<Ticket<?>>> forcedTickets = new Long2ObjectOpenHashMap();

    protected TicketManager(Executor executor, Executor executor2) {
        ChunkTaskPriorityQueueSorter chunkTaskPriorityQueueSorter;
        ITaskExecutor<Runnable> iTaskExecutor = ITaskExecutor.inline("player ticket throttler", executor2::execute);
        this.field_219384_l = chunkTaskPriorityQueueSorter = new ChunkTaskPriorityQueueSorter(ImmutableList.of(iTaskExecutor), executor, 4);
        this.field_219385_m = chunkTaskPriorityQueueSorter.func_219087_a(iTaskExecutor, false);
        this.field_219386_n = chunkTaskPriorityQueueSorter.func_219091_a(iTaskExecutor);
        this.field_219388_p = executor2;
    }

    protected void tick() {
        ++this.currentTime;
        ObjectIterator objectIterator = this.tickets.long2ObjectEntrySet().fastIterator();
        while (objectIterator.hasNext()) {
            Long2ObjectMap.Entry entry = (Long2ObjectMap.Entry)objectIterator.next();
            if (((SortedArraySet)entry.getValue()).removeIf(this::lambda$tick$0)) {
                this.ticketTracker.updateSourceLevel(entry.getLongKey(), TicketManager.getLevel((SortedArraySet)entry.getValue()), true);
            }
            if (!((SortedArraySet)entry.getValue()).isEmpty()) continue;
            objectIterator.remove();
        }
    }

    private static int getLevel(SortedArraySet<Ticket<?>> sortedArraySet) {
        return !sortedArraySet.isEmpty() ? sortedArraySet.getSmallest().getLevel() : ChunkManager.MAX_LOADED_LEVEL + 1;
    }

    protected abstract boolean contains(long var1);

    @Nullable
    protected abstract ChunkHolder getChunkHolder(long var1);

    @Nullable
    protected abstract ChunkHolder setChunkLevel(long var1, int var3, @Nullable ChunkHolder var4, int var5);

    public boolean processUpdates(ChunkManager chunkManager) {
        boolean bl;
        this.playerChunkTracker.processAllUpdates();
        this.playerTicketTracker.processAllUpdates();
        int n = Integer.MAX_VALUE - this.ticketTracker.func_215493_a(Integer.MAX_VALUE);
        boolean bl2 = bl = n != 0;
        if (bl) {
            // empty if block
        }
        if (!this.chunkHolders.isEmpty()) {
            this.chunkHolders.forEach(arg_0 -> TicketManager.lambda$processUpdates$1(chunkManager, arg_0));
            this.chunkHolders.clear();
            return false;
        }
        if (!this.chunkPositions.isEmpty()) {
            LongIterator longIterator = this.chunkPositions.iterator();
            while (longIterator.hasNext()) {
                long l = longIterator.nextLong();
                if (!this.getTicketSet(l).stream().anyMatch(TicketManager::lambda$processUpdates$2)) continue;
                ChunkHolder chunkHolder = chunkManager.func_219220_a(l);
                if (chunkHolder == null) {
                    throw new IllegalStateException();
                }
                CompletableFuture<Either<Chunk, ChunkHolder.IChunkLoadingError>> completableFuture = chunkHolder.getEntityTickingFuture();
                completableFuture.thenAccept(arg_0 -> this.lambda$processUpdates$5(l, arg_0));
            }
            this.chunkPositions.clear();
        }
        return bl;
    }

    private void register(long l, Ticket<?> ticket) {
        SortedArraySet<Ticket<?>> sortedArraySet = this.getTicketSet(l);
        int n = TicketManager.getLevel(sortedArraySet);
        Ticket<?> ticket2 = sortedArraySet.func_226175_a_(ticket);
        ticket2.setTimestamp(this.currentTime);
        if (ticket.getLevel() < n) {
            this.ticketTracker.updateSourceLevel(l, ticket.getLevel(), false);
        }
        if (Reflector.callBoolean(ticket, Reflector.ForgeTicket_isForceTicks, new Object[0])) {
            SortedArraySet sortedArraySet2 = this.forcedTickets.computeIfAbsent(l, TicketManager::lambda$register$6);
            sortedArraySet2.func_226175_a_(ticket2);
        }
    }

    private void release(long l, Ticket<?> ticket) {
        SortedArraySet<Ticket<?>> sortedArraySet;
        SortedArraySet<Ticket<?>> sortedArraySet2 = this.getTicketSet(l);
        if (sortedArraySet2.remove(ticket)) {
            // empty if block
        }
        if (sortedArraySet2.isEmpty()) {
            this.tickets.remove(l);
        }
        this.ticketTracker.updateSourceLevel(l, TicketManager.getLevel(sortedArraySet2), true);
        if (Reflector.callBoolean(ticket, Reflector.ForgeTicket_isForceTicks, new Object[0]) && (sortedArraySet = this.forcedTickets.get(l)) != null) {
            sortedArraySet.remove(ticket);
        }
    }

    public <T> void registerWithLevel(TicketType<T> ticketType, ChunkPos chunkPos, int n, T t) {
        this.register(chunkPos.asLong(), new Ticket<T>(ticketType, n, t));
    }

    public <T> void releaseWithLevel(TicketType<T> ticketType, ChunkPos chunkPos, int n, T t) {
        Ticket<T> ticket = new Ticket<T>(ticketType, n, t);
        this.release(chunkPos.asLong(), ticket);
    }

    public <T> void register(TicketType<T> ticketType, ChunkPos chunkPos, int n, T t) {
        this.register(chunkPos.asLong(), new Ticket<T>(ticketType, 33 - n, t));
    }

    public <T> void release(TicketType<T> ticketType, ChunkPos chunkPos, int n, T t) {
        Ticket<T> ticket = new Ticket<T>(ticketType, 33 - n, t);
        this.release(chunkPos.asLong(), ticket);
    }

    private SortedArraySet<Ticket<?>> getTicketSet(long l) {
        return this.tickets.computeIfAbsent(l, TicketManager::lambda$getTicketSet$7);
    }

    protected void forceChunk(ChunkPos chunkPos, boolean bl) {
        Ticket<ChunkPos> ticket = new Ticket<ChunkPos>(TicketType.FORCED, 31, chunkPos);
        if (bl) {
            this.register(chunkPos.asLong(), ticket);
        } else {
            this.release(chunkPos.asLong(), ticket);
        }
    }

    public void updatePlayerPosition(SectionPos sectionPos, ServerPlayerEntity serverPlayerEntity) {
        long l = sectionPos.asChunkPos().asLong();
        this.playersByChunkPos.computeIfAbsent(l, TicketManager::lambda$updatePlayerPosition$8).add(serverPlayerEntity);
        this.playerChunkTracker.updateSourceLevel(l, 0, false);
        this.playerTicketTracker.updateSourceLevel(l, 0, false);
    }

    public void removePlayer(SectionPos sectionPos, ServerPlayerEntity serverPlayerEntity) {
        long l = sectionPos.asChunkPos().asLong();
        ObjectSet objectSet = (ObjectSet)this.playersByChunkPos.get(l);
        objectSet.remove(serverPlayerEntity);
        if (objectSet.isEmpty()) {
            this.playersByChunkPos.remove(l);
            this.playerChunkTracker.updateSourceLevel(l, Integer.MAX_VALUE, true);
            this.playerTicketTracker.updateSourceLevel(l, Integer.MAX_VALUE, true);
        }
    }

    protected String func_225413_c(long l) {
        SortedArraySet<Ticket<?>> sortedArraySet = this.tickets.get(l);
        String string = sortedArraySet != null && !sortedArraySet.isEmpty() ? sortedArraySet.getSmallest().toString() : "no_ticket";
        return string;
    }

    protected void setViewDistance(int n) {
        this.playerTicketTracker.setViewDistance(n);
    }

    public int getSpawningChunksCount() {
        this.playerChunkTracker.processAllUpdates();
        return this.playerChunkTracker.chunksInRange.size();
    }

    public boolean isOutsideSpawningRadius(long l) {
        this.playerChunkTracker.processAllUpdates();
        return this.playerChunkTracker.chunksInRange.containsKey(l);
    }

    public String func_225412_c() {
        return this.field_219384_l.func_225396_a();
    }

    public <T> void registerTicking(TicketType<T> ticketType, ChunkPos chunkPos, int n, T t) {
        Ticket ticket = (Ticket)Reflector.ForgeTicket_Constructor.newInstance(ticketType, 33 - n, t, true);
        this.register(chunkPos.asLong(), ticket);
    }

    public <T> void releaseTicking(TicketType<T> ticketType, ChunkPos chunkPos, int n, T t) {
        Ticket ticket = (Ticket)Reflector.ForgeTicket_Constructor.newInstance(ticketType, 33 - n, t, true);
        this.release(chunkPos.asLong(), ticket);
    }

    public boolean shouldForceTicks(long l) {
        SortedArraySet<Ticket<?>> sortedArraySet = this.forcedTickets.get(l);
        return sortedArraySet != null && !sortedArraySet.isEmpty();
    }

    private static ObjectSet lambda$updatePlayerPosition$8(long l) {
        return new ObjectOpenHashSet();
    }

    private static SortedArraySet lambda$getTicketSet$7(long l) {
        return SortedArraySet.newSet(4);
    }

    private static SortedArraySet lambda$register$6(long l) {
        return SortedArraySet.newSet(4);
    }

    private void lambda$processUpdates$5(long l, Either either) {
        this.field_219388_p.execute(() -> this.lambda$processUpdates$4(l));
    }

    private void lambda$processUpdates$4(long l) {
        this.field_219386_n.enqueue(ChunkTaskPriorityQueueSorter.func_219073_a(TicketManager::lambda$processUpdates$3, l, false));
    }

    private static void lambda$processUpdates$3() {
    }

    private static boolean lambda$processUpdates$2(Ticket ticket) {
        return ticket.getType() == TicketType.PLAYER;
    }

    private static void lambda$processUpdates$1(ChunkManager chunkManager, ChunkHolder chunkHolder) {
        chunkHolder.processUpdates(chunkManager);
    }

    private boolean lambda$tick$0(Ticket ticket) {
        return ticket.isExpired(this.currentTime);
    }

    class ChunkTicketTracker
    extends ChunkDistanceGraph {
        final TicketManager this$0;

        public ChunkTicketTracker(TicketManager ticketManager) {
            this.this$0 = ticketManager;
            super(ChunkManager.MAX_LOADED_LEVEL + 2, 256, 256);
        }

        @Override
        protected int getSourceLevel(long l) {
            SortedArraySet<Ticket<?>> sortedArraySet = this.this$0.tickets.get(l);
            if (sortedArraySet == null) {
                return 0;
            }
            return sortedArraySet.isEmpty() ? Integer.MAX_VALUE : sortedArraySet.getSmallest().getLevel();
        }

        @Override
        protected int getLevel(long l) {
            ChunkHolder chunkHolder;
            if (!this.this$0.contains(l) && (chunkHolder = this.this$0.getChunkHolder(l)) != null) {
                return chunkHolder.getChunkLevel();
            }
            return ChunkManager.MAX_LOADED_LEVEL + 1;
        }

        @Override
        protected void setLevel(long l, int n) {
            int n2;
            ChunkHolder chunkHolder = this.this$0.getChunkHolder(l);
            int n3 = n2 = chunkHolder == null ? ChunkManager.MAX_LOADED_LEVEL + 1 : chunkHolder.getChunkLevel();
            if (n2 != n && (chunkHolder = this.this$0.setChunkLevel(l, n, chunkHolder, n2)) != null) {
                this.this$0.chunkHolders.add(chunkHolder);
            }
        }

        public int func_215493_a(int n) {
            return this.processUpdates(n);
        }
    }

    class PlayerChunkTracker
    extends ChunkDistanceGraph {
        protected final Long2ByteMap chunksInRange;
        protected final int range;
        final TicketManager this$0;

        protected PlayerChunkTracker(TicketManager ticketManager, int n) {
            this.this$0 = ticketManager;
            super(n + 2, 2048, 2048);
            this.chunksInRange = new Long2ByteOpenHashMap();
            this.range = n;
            this.chunksInRange.defaultReturnValue((byte)(n + 2));
        }

        @Override
        protected int getLevel(long l) {
            return this.chunksInRange.get(l);
        }

        @Override
        protected void setLevel(long l, int n) {
            byte by = n > this.range ? this.chunksInRange.remove(l) : this.chunksInRange.put(l, (byte)n);
            this.chunkLevelChanged(l, by, n);
        }

        protected void chunkLevelChanged(long l, int n, int n2) {
        }

        @Override
        protected int getSourceLevel(long l) {
            return this.hasPlayerInChunk(l) ? 0 : Integer.MAX_VALUE;
        }

        private boolean hasPlayerInChunk(long l) {
            ObjectSet objectSet = (ObjectSet)this.this$0.playersByChunkPos.get(l);
            return objectSet != null && !objectSet.isEmpty();
        }

        public void processAllUpdates() {
            this.processUpdates(Integer.MAX_VALUE);
        }
    }

    class PlayerTicketTracker
    extends PlayerChunkTracker {
        private int viewDistance;
        private final Long2IntMap field_215513_f;
        private final LongSet field_215514_g;
        final TicketManager this$0;

        protected PlayerTicketTracker(TicketManager ticketManager, int n) {
            this.this$0 = ticketManager;
            super(ticketManager, n);
            this.field_215513_f = Long2IntMaps.synchronize(new Long2IntOpenHashMap());
            this.field_215514_g = new LongOpenHashSet();
            this.viewDistance = 0;
            this.field_215513_f.defaultReturnValue(n + 2);
        }

        @Override
        protected void chunkLevelChanged(long l, int n, int n2) {
            this.field_215514_g.add(l);
        }

        public void setViewDistance(int n) {
            for (Long2ByteMap.Entry entry : this.chunksInRange.long2ByteEntrySet()) {
                byte by = entry.getByteValue();
                long l = entry.getLongKey();
                this.func_215504_a(l, by, this.func_215505_c(by), by <= n - 2);
            }
            this.viewDistance = n;
        }

        private void func_215504_a(long l, int n, boolean bl, boolean bl2) {
            if (bl != bl2) {
                Ticket<ChunkPos> ticket = new Ticket<ChunkPos>(TicketType.PLAYER, PLAYER_TICKET_LEVEL, new ChunkPos(l));
                if (bl2) {
                    this.this$0.field_219385_m.enqueue(ChunkTaskPriorityQueueSorter.func_219069_a(() -> this.lambda$func_215504_a$2(l, ticket), l, () -> PlayerTicketTracker.lambda$func_215504_a$3(n)));
                } else {
                    this.this$0.field_219386_n.enqueue(ChunkTaskPriorityQueueSorter.func_219073_a(() -> this.lambda$func_215504_a$5(l, ticket), l, true));
                }
            }
        }

        @Override
        public void processAllUpdates() {
            super.processAllUpdates();
            if (!this.field_215514_g.isEmpty()) {
                LongIterator longIterator = this.field_215514_g.iterator();
                while (longIterator.hasNext()) {
                    int n;
                    long l = longIterator.nextLong();
                    int n2 = this.field_215513_f.get(l);
                    if (n2 == (n = this.getLevel(l))) continue;
                    this.this$0.field_219384_l.func_219066_a(new ChunkPos(l), () -> this.lambda$processAllUpdates$6(l), n, arg_0 -> this.lambda$processAllUpdates$7(l, arg_0));
                    this.func_215504_a(l, n, this.func_215505_c(n2), this.func_215505_c(n));
                }
                this.field_215514_g.clear();
            }
        }

        private boolean func_215505_c(int n) {
            return n <= this.viewDistance - 2;
        }

        private void lambda$processAllUpdates$7(long l, int n) {
            if (n >= this.field_215513_f.defaultReturnValue()) {
                this.field_215513_f.remove(l);
            } else {
                this.field_215513_f.put(l, n);
            }
        }

        private int lambda$processAllUpdates$6(long l) {
            return this.field_215513_f.get(l);
        }

        private void lambda$func_215504_a$5(long l, Ticket ticket) {
            this.this$0.field_219388_p.execute(() -> this.lambda$func_215504_a$4(l, ticket));
        }

        private void lambda$func_215504_a$4(long l, Ticket ticket) {
            this.this$0.release(l, ticket);
        }

        private static int lambda$func_215504_a$3(int n) {
            return n;
        }

        private void lambda$func_215504_a$2(long l, Ticket ticket) {
            this.this$0.field_219388_p.execute(() -> this.lambda$func_215504_a$1(l, ticket));
        }

        private void lambda$func_215504_a$1(long l, Ticket ticket) {
            if (this.func_215505_c(this.getLevel(l))) {
                this.this$0.register(l, ticket);
                this.this$0.chunkPositions.add(l);
            } else {
                this.this$0.field_219386_n.enqueue(ChunkTaskPriorityQueueSorter.func_219073_a(PlayerTicketTracker::lambda$func_215504_a$0, l, false));
            }
        }

        private static void lambda$func_215504_a$0() {
        }
    }
}


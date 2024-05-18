package net.minecraft.world;

import net.minecraft.server.*;
import net.minecraft.profiler.*;
import net.minecraft.world.storage.*;
import net.minecraft.world.border.*;
import net.minecraft.village.*;

public class WorldServerMulti extends WorldServer
{
    private WorldServer delegate;
    
    public WorldServerMulti(final MinecraftServer minecraftServer, final ISaveHandler saveHandler, final int n, final WorldServer delegate, final Profiler profiler) {
        super(minecraftServer, saveHandler, new DerivedWorldInfo(delegate.getWorldInfo()), n, profiler);
        this.delegate = delegate;
        delegate.getWorldBorder().addListener(new IBorderListener(this) {
            final WorldServerMulti this$0;
            
            private static String I(final String s, final String s2) {
                final StringBuilder sb = new StringBuilder();
                final char[] charArray = s2.toCharArray();
                int length = "".length();
                final char[] charArray2 = s.toCharArray();
                final int length2 = charArray2.length;
                int i = "".length();
                while (i < length2) {
                    sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                    ++length;
                    ++i;
                    "".length();
                    if (2 == 4) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            public void onDamageAmountChanged(final WorldBorder worldBorder, final double damageAmount) {
                this.this$0.getWorldBorder().setDamageAmount(damageAmount);
            }
            
            @Override
            public void onCenterChanged(final WorldBorder worldBorder, final double n, final double n2) {
                this.this$0.getWorldBorder().setCenter(n, n2);
            }
            
            @Override
            public void onDamageBufferChanged(final WorldBorder worldBorder, final double damageBuffer) {
                this.this$0.getWorldBorder().setDamageBuffer(damageBuffer);
            }
            
            @Override
            public void onWarningDistanceChanged(final WorldBorder worldBorder, final int warningDistance) {
                this.this$0.getWorldBorder().setWarningDistance(warningDistance);
            }
            
            @Override
            public void onTransitionStarted(final WorldBorder worldBorder, final double n, final double n2, final long n3) {
                this.this$0.getWorldBorder().setTransition(n, n2, n3);
            }
            
            @Override
            public void onWarningTimeChanged(final WorldBorder worldBorder, final int warningTime) {
                this.this$0.getWorldBorder().setWarningTime(warningTime);
            }
            
            @Override
            public void onSizeChanged(final WorldBorder worldBorder, final double transition) {
                this.this$0.getWorldBorder().setTransition(transition);
            }
        });
    }
    
    @Override
    protected void saveLevel() throws MinecraftException {
    }
    
    @Override
    public World init() {
        this.mapStorage = this.delegate.getMapStorage();
        this.worldScoreboard = this.delegate.getScoreboard();
        final String fileNameForProvider = VillageCollection.fileNameForProvider(this.provider);
        final VillageCollection villageCollectionObj = (VillageCollection)this.mapStorage.loadData(VillageCollection.class, fileNameForProvider);
        if (villageCollectionObj == null) {
            this.villageCollectionObj = new VillageCollection(this);
            this.mapStorage.setData(fileNameForProvider, this.villageCollectionObj);
            "".length();
            if (3 <= -1) {
                throw null;
            }
        }
        else {
            (this.villageCollectionObj = villageCollectionObj).setWorldsForAll(this);
        }
        return this;
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (4 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
}

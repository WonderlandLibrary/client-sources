package net.minecraft.tileentity;

import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import com.google.common.collect.*;
import net.minecraft.entity.item.*;

public abstract class MobSpawnerBaseLogic
{
    private final List<WeightedRandomMinecart> minecartToSpawn;
    private static final String[] I;
    private WeightedRandomMinecart randomEntity;
    private int spawnRange;
    private int activatingRangeFromPlayer;
    private int spawnCount;
    private Entity cachedEntity;
    private int maxNearbyEntities;
    private int maxSpawnDelay;
    private String mobID;
    private double prevMobRotation;
    private int minSpawnDelay;
    private double mobRotation;
    private int spawnDelay;
    
    private WeightedRandomMinecart getRandomEntity() {
        return this.randomEntity;
    }
    
    public abstract void func_98267_a(final int p0);
    
    private String getEntityNameToSpawn() {
        if (this.getRandomEntity() == null) {
            if (this.mobID != null && this.mobID.equals(MobSpawnerBaseLogic.I[" ".length()])) {
                this.mobID = MobSpawnerBaseLogic.I["  ".length()];
            }
            return this.mobID;
        }
        return WeightedRandomMinecart.access$0(this.getRandomEntity());
    }
    
    private Entity spawnNewEntity(final Entity entity, final boolean b) {
        if (this.getRandomEntity() != null) {
            NBTTagCompound nbtTagCompound = new NBTTagCompound();
            entity.writeToNBTOptional(nbtTagCompound);
            final Iterator<String> iterator = WeightedRandomMinecart.access$1(this.getRandomEntity()).getKeySet().iterator();
            "".length();
            if (3 != 3) {
                throw null;
            }
            while (iterator.hasNext()) {
                final String s = iterator.next();
                nbtTagCompound.setTag(s, WeightedRandomMinecart.access$1(this.getRandomEntity()).getTag(s).copy());
            }
            entity.readFromNBT(nbtTagCompound);
            if (entity.worldObj != null && b) {
                entity.worldObj.spawnEntityInWorld(entity);
            }
            Entity entity2 = entity;
            "".length();
            if (4 <= -1) {
                throw null;
            }
            while (nbtTagCompound.hasKey(MobSpawnerBaseLogic.I[0x1 ^ 0x4], 0x9C ^ 0x96)) {
                final NBTTagCompound compoundTag = nbtTagCompound.getCompoundTag(MobSpawnerBaseLogic.I["   ".length()]);
                final Entity entityByName = EntityList.createEntityByName(compoundTag.getString(MobSpawnerBaseLogic.I[0xAB ^ 0xAF]), entity.worldObj);
                if (entityByName != null) {
                    final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
                    entityByName.writeToNBTOptional(nbtTagCompound2);
                    final Iterator<String> iterator2 = compoundTag.getKeySet().iterator();
                    "".length();
                    if (3 < 2) {
                        throw null;
                    }
                    while (iterator2.hasNext()) {
                        final String s2 = iterator2.next();
                        nbtTagCompound2.setTag(s2, compoundTag.getTag(s2).copy());
                    }
                    entityByName.readFromNBT(nbtTagCompound2);
                    entityByName.setLocationAndAngles(entity2.posX, entity2.posY, entity2.posZ, entity2.rotationYaw, entity2.rotationPitch);
                    if (entity.worldObj != null && b) {
                        entity.worldObj.spawnEntityInWorld(entityByName);
                    }
                    entity2.mountEntity(entityByName);
                }
                entity2 = entityByName;
                nbtTagCompound = compoundTag;
            }
            "".length();
            if (2 < 1) {
                throw null;
            }
        }
        else if (entity instanceof EntityLivingBase && entity.worldObj != null && b) {
            if (entity instanceof EntityLiving) {
                ((EntityLiving)entity).onInitialSpawn(entity.worldObj.getDifficultyForLocation(new BlockPos(entity)), null);
            }
            entity.worldObj.spawnEntityInWorld(entity);
        }
        return entity;
    }
    
    public double getPrevMobRotation() {
        return this.prevMobRotation;
    }
    
    public void updateSpawner() {
        if (this.isActivated()) {
            final BlockPos spawnerPosition = this.getSpawnerPosition();
            if (this.getSpawnerWorld().isRemote) {
                final double n = spawnerPosition.getX() + this.getSpawnerWorld().rand.nextFloat();
                final double n2 = spawnerPosition.getY() + this.getSpawnerWorld().rand.nextFloat();
                final double n3 = spawnerPosition.getZ() + this.getSpawnerWorld().rand.nextFloat();
                this.getSpawnerWorld().spawnParticle(EnumParticleTypes.SMOKE_NORMAL, n, n2, n3, 0.0, 0.0, 0.0, new int["".length()]);
                this.getSpawnerWorld().spawnParticle(EnumParticleTypes.FLAME, n, n2, n3, 0.0, 0.0, 0.0, new int["".length()]);
                if (this.spawnDelay > 0) {
                    this.spawnDelay -= " ".length();
                }
                this.prevMobRotation = this.mobRotation;
                this.mobRotation = (this.mobRotation + 1000.0f / (this.spawnDelay + 200.0f)) % 360.0;
                "".length();
                if (0 == 3) {
                    throw null;
                }
            }
            else {
                if (this.spawnDelay == -" ".length()) {
                    this.resetTimer();
                }
                if (this.spawnDelay > 0) {
                    this.spawnDelay -= " ".length();
                    return;
                }
                int n4 = "".length();
                int i = "".length();
                "".length();
                if (0 >= 4) {
                    throw null;
                }
                while (i < this.spawnCount) {
                    final Entity entityByName = EntityList.createEntityByName(this.getEntityNameToSpawn(), this.getSpawnerWorld());
                    if (entityByName == null) {
                        return;
                    }
                    if (this.getSpawnerWorld().getEntitiesWithinAABB((Class<? extends Entity>)((EntityLiving)entityByName).getClass(), new AxisAlignedBB(spawnerPosition.getX(), spawnerPosition.getY(), spawnerPosition.getZ(), spawnerPosition.getX() + " ".length(), spawnerPosition.getY() + " ".length(), spawnerPosition.getZ() + " ".length()).expand(this.spawnRange, this.spawnRange, this.spawnRange)).size() >= this.maxNearbyEntities) {
                        this.resetTimer();
                        return;
                    }
                    final double n5 = spawnerPosition.getX() + (this.getSpawnerWorld().rand.nextDouble() - this.getSpawnerWorld().rand.nextDouble()) * this.spawnRange + 0.5;
                    final double n6 = spawnerPosition.getY() + this.getSpawnerWorld().rand.nextInt("   ".length()) - " ".length();
                    final double n7 = spawnerPosition.getZ() + (this.getSpawnerWorld().rand.nextDouble() - this.getSpawnerWorld().rand.nextDouble()) * this.spawnRange + 0.5;
                    EntityLiving entityLiving;
                    if (entityByName instanceof EntityLiving) {
                        entityLiving = (EntityLiving)entityByName;
                        "".length();
                        if (3 <= 2) {
                            throw null;
                        }
                    }
                    else {
                        entityLiving = null;
                    }
                    final EntityLiving entityLiving2 = entityLiving;
                    entityByName.setLocationAndAngles(n5, n6, n7, this.getSpawnerWorld().rand.nextFloat() * 360.0f, 0.0f);
                    if (entityLiving2 == null || (entityLiving2.getCanSpawnHere() && entityLiving2.isNotColliding())) {
                        this.spawnNewEntity(entityByName, " ".length() != 0);
                        this.getSpawnerWorld().playAuxSFX(1285 + 1537 - 2101 + 1283, spawnerPosition, "".length());
                        if (entityLiving2 != null) {
                            entityLiving2.spawnExplosionParticle();
                        }
                        n4 = " ".length();
                    }
                    ++i;
                }
                if (n4 != 0) {
                    this.resetTimer();
                }
            }
        }
    }
    
    private void resetTimer() {
        if (this.maxSpawnDelay <= this.minSpawnDelay) {
            this.spawnDelay = this.minSpawnDelay;
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        else {
            this.spawnDelay = this.minSpawnDelay + this.getSpawnerWorld().rand.nextInt(this.maxSpawnDelay - this.minSpawnDelay);
        }
        if (this.minecartToSpawn.size() > 0) {
            this.setRandomEntity(WeightedRandom.getRandomItem(this.getSpawnerWorld().rand, this.minecartToSpawn));
        }
        this.func_98267_a(" ".length());
    }
    
    public void writeToNBT(final NBTTagCompound nbtTagCompound) {
        final String entityNameToSpawn = this.getEntityNameToSpawn();
        if (!StringUtils.isNullOrEmpty(entityNameToSpawn)) {
            nbtTagCompound.setString(MobSpawnerBaseLogic.I[0x4F ^ 0x5A], entityNameToSpawn);
            nbtTagCompound.setShort(MobSpawnerBaseLogic.I[0xB ^ 0x1D], (short)this.spawnDelay);
            nbtTagCompound.setShort(MobSpawnerBaseLogic.I[0xD2 ^ 0xC5], (short)this.minSpawnDelay);
            nbtTagCompound.setShort(MobSpawnerBaseLogic.I[0x35 ^ 0x2D], (short)this.maxSpawnDelay);
            nbtTagCompound.setShort(MobSpawnerBaseLogic.I[0x6C ^ 0x75], (short)this.spawnCount);
            nbtTagCompound.setShort(MobSpawnerBaseLogic.I[0x41 ^ 0x5B], (short)this.maxNearbyEntities);
            nbtTagCompound.setShort(MobSpawnerBaseLogic.I[0x5 ^ 0x1E], (short)this.activatingRangeFromPlayer);
            nbtTagCompound.setShort(MobSpawnerBaseLogic.I[0x9D ^ 0x81], (short)this.spawnRange);
            if (this.getRandomEntity() != null) {
                nbtTagCompound.setTag(MobSpawnerBaseLogic.I[0x7B ^ 0x66], WeightedRandomMinecart.access$1(this.getRandomEntity()).copy());
            }
            if (this.getRandomEntity() != null || this.minecartToSpawn.size() > 0) {
                final NBTTagList list = new NBTTagList();
                if (this.minecartToSpawn.size() > 0) {
                    final Iterator<WeightedRandomMinecart> iterator = this.minecartToSpawn.iterator();
                    "".length();
                    if (4 != 4) {
                        throw null;
                    }
                    while (iterator.hasNext()) {
                        list.appendTag(iterator.next().toNBT());
                    }
                    "".length();
                    if (3 == -1) {
                        throw null;
                    }
                }
                else {
                    list.appendTag(this.getRandomEntity().toNBT());
                }
                nbtTagCompound.setTag(MobSpawnerBaseLogic.I[0x8F ^ 0x91], list);
            }
        }
    }
    
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
        this.mobID = nbtTagCompound.getString(MobSpawnerBaseLogic.I[0x20 ^ 0x26]);
        this.spawnDelay = nbtTagCompound.getShort(MobSpawnerBaseLogic.I[0x1E ^ 0x19]);
        this.minecartToSpawn.clear();
        if (nbtTagCompound.hasKey(MobSpawnerBaseLogic.I[0x6E ^ 0x66], 0xCF ^ 0xC6)) {
            final NBTTagList tagList = nbtTagCompound.getTagList(MobSpawnerBaseLogic.I[0x11 ^ 0x18], 0x42 ^ 0x48);
            int i = "".length();
            "".length();
            if (4 <= 1) {
                throw null;
            }
            while (i < tagList.tagCount()) {
                this.minecartToSpawn.add(new WeightedRandomMinecart(tagList.getCompoundTagAt(i)));
                ++i;
            }
        }
        if (nbtTagCompound.hasKey(MobSpawnerBaseLogic.I[0xBC ^ 0xB6], 0x5D ^ 0x57)) {
            this.setRandomEntity(new WeightedRandomMinecart(nbtTagCompound.getCompoundTag(MobSpawnerBaseLogic.I[0xA4 ^ 0xAF]), this.mobID));
            "".length();
            if (-1 >= 3) {
                throw null;
            }
        }
        else {
            this.setRandomEntity(null);
        }
        if (nbtTagCompound.hasKey(MobSpawnerBaseLogic.I[0x1E ^ 0x12], 0xF5 ^ 0x96)) {
            this.minSpawnDelay = nbtTagCompound.getShort(MobSpawnerBaseLogic.I[0x6C ^ 0x61]);
            this.maxSpawnDelay = nbtTagCompound.getShort(MobSpawnerBaseLogic.I[0x6D ^ 0x63]);
            this.spawnCount = nbtTagCompound.getShort(MobSpawnerBaseLogic.I[0x8C ^ 0x83]);
        }
        if (nbtTagCompound.hasKey(MobSpawnerBaseLogic.I[0x97 ^ 0x87], 0xD7 ^ 0xB4)) {
            this.maxNearbyEntities = nbtTagCompound.getShort(MobSpawnerBaseLogic.I[0x74 ^ 0x65]);
            this.activatingRangeFromPlayer = nbtTagCompound.getShort(MobSpawnerBaseLogic.I[0x23 ^ 0x31]);
        }
        if (nbtTagCompound.hasKey(MobSpawnerBaseLogic.I[0x64 ^ 0x77], 0x27 ^ 0x44)) {
            this.spawnRange = nbtTagCompound.getShort(MobSpawnerBaseLogic.I[0x52 ^ 0x46]);
        }
        if (this.getSpawnerWorld() != null) {
            this.cachedEntity = null;
        }
    }
    
    public Entity func_180612_a(final World world) {
        if (this.cachedEntity == null) {
            final Entity entityByName = EntityList.createEntityByName(this.getEntityNameToSpawn(), world);
            if (entityByName != null) {
                this.cachedEntity = this.spawnNewEntity(entityByName, "".length() != 0);
            }
        }
        return this.cachedEntity;
    }
    
    public abstract World getSpawnerWorld();
    
    public abstract BlockPos getSpawnerPosition();
    
    public MobSpawnerBaseLogic() {
        this.spawnDelay = (0x9E ^ 0x8A);
        this.mobID = MobSpawnerBaseLogic.I["".length()];
        this.minecartToSpawn = (List<WeightedRandomMinecart>)Lists.newArrayList();
        this.minSpawnDelay = 89 + 49 + 35 + 27;
        this.maxSpawnDelay = 251 + 660 - 410 + 299;
        this.spawnCount = (0x87 ^ 0x83);
        this.maxNearbyEntities = (0x93 ^ 0x95);
        this.activatingRangeFromPlayer = (0x1 ^ 0x11);
        this.spawnRange = (0x20 ^ 0x24);
    }
    
    public boolean setDelayToMin(final int n) {
        if (n == " ".length() && this.getSpawnerWorld().isRemote) {
            this.spawnDelay = this.minSpawnDelay;
            return " ".length() != 0;
        }
        return "".length() != 0;
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
            if (0 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public double getMobRotation() {
        return this.mobRotation;
    }
    
    private boolean isActivated() {
        final BlockPos spawnerPosition = this.getSpawnerPosition();
        return this.getSpawnerWorld().isAnyPlayerWithinRangeAt(spawnerPosition.getX() + 0.5, spawnerPosition.getY() + 0.5, spawnerPosition.getZ() + 0.5, this.activatingRangeFromPlayer);
    }
    
    private static void I() {
        (I = new String[0xBD ^ 0xA2])["".length()] = I("\u001f+5", "OBROX");
        MobSpawnerBaseLogic.I[" ".length()] = I("\u001d;\u000b7\u00141 \u0011", "PReRw");
        MobSpawnerBaseLogic.I["  ".length()] = I("\u0019/\u0004\u0015\u001454\u001e\"\u001e0#\u000b\u0012\u001b1", "TFjpw");
        MobSpawnerBaseLogic.I["   ".length()] = I("\u001d\u0001<1&(", "OhXXH");
        MobSpawnerBaseLogic.I[0x6F ^ 0x6B] = I("\u001f\u0013", "vwvBm");
        MobSpawnerBaseLogic.I[0xB7 ^ 0xB2] = I("<\u0002\u0014?7\t", "nkpVY");
        MobSpawnerBaseLogic.I[0x24 ^ 0x22] = I("6\u0006'9'\n!7", "shSPS");
        MobSpawnerBaseLogic.I[0x16 ^ 0x11] = I(")#(7 ", "mFDVY");
        MobSpawnerBaseLogic.I[0x96 ^ 0x9E] = I("\u0018<\u000b;:\u001b#\u001e):?%\u000b '", "KLjLT");
        MobSpawnerBaseLogic.I[0x8B ^ 0x82] = I("\t\u0002\t;>\n\u001d\u001c)>.\u001b\t #", "ZrhLP");
        MobSpawnerBaseLogic.I[0x7F ^ 0x75] = I(":)\"\u00014-87\u0017", "iYCvZ");
        MobSpawnerBaseLogic.I[0x3E ^ 0x35] = I("%\u0015\u0018' 2\u0004\r1", "veyPN");
        MobSpawnerBaseLogic.I[0x92 ^ 0x9E] = I("/.\u001e\u0007\u0014\u00030\u001e\u0010\u0001\u000e&\t", "bGpTd");
        MobSpawnerBaseLogic.I[0x58 ^ 0x55] = I("(%\u001d>4\u0004;\u001d)!\t-\n", "eLsmD");
        MobSpawnerBaseLogic.I[0x6D ^ 0x63] = I("/94 \t\u0003/\"7\u001c\u000e95", "bXLsy");
        MobSpawnerBaseLogic.I[0x3F ^ 0x30] = I("5 \u0018;\u0003%?\f\"\u0019", "fPyLm");
        MobSpawnerBaseLogic.I[0x3B ^ 0x2B] = I("\u0018\f\u001a\f&4\u001f\u0000;\u0006;\u0019\u000b6*0\u001e", "UmbBC");
        MobSpawnerBaseLogic.I[0x6F ^ 0x7E] = I("!-1?)\r>+\b\t\u00028 \u0005%\t?", "lLIqL");
        MobSpawnerBaseLogic.I[0x25 ^ 0x37] = I("\u00145 \u0013\b4556\r')4\u00143'>6\u0003", "FPQfa");
        MobSpawnerBaseLogic.I[0x20 ^ 0x33] = I("\u0002\u0002\u0003\u0003\r\u0003\u0013\f\u0013\u0006", "Qrbtc");
        MobSpawnerBaseLogic.I[0x14 ^ 0x0] = I("\u000b47\u0016\u0002\n%8\u0006\t", "XDVal");
        MobSpawnerBaseLogic.I[0x78 ^ 0x6D] = I("1\u0016\u0006\r\u001b\r1\u0016", "txrdo");
        MobSpawnerBaseLogic.I[0x81 ^ 0x97] = I(" 3\u001c\t.", "dVphW");
        MobSpawnerBaseLogic.I[0x35 ^ 0x22] = I("\u0017\u00014!<;\u001f46)6\t#", "ZhZrL");
        MobSpawnerBaseLogic.I[0x94 ^ 0x8C] = I("\u001980& 5.&15881", "TYHuP");
        MobSpawnerBaseLogic.I[0x80 ^ 0x99] = I("\u0002\u00157\u001b$\u0012\n#\u0002>", "QeVlJ");
        MobSpawnerBaseLogic.I[0x84 ^ 0x9E] = I("+.1\u000b/\u0007=+<\u000f\b; 1#\u0003<", "fOIEJ");
        MobSpawnerBaseLogic.I[0xA8 ^ 0xB3] = I(":\u0017\u001e\u0010\u0004\u001a\u0017\u000b5\u0001\t\u000b\n\u0017?\t\u001c\b\u0000", "hroem");
        MobSpawnerBaseLogic.I[0x59 ^ 0x45] = I("\u001f\u001f\u0014>?\u001e\u000e\u001b.4", "LouIQ");
        MobSpawnerBaseLogic.I[0x5E ^ 0x43] = I("\u001a)7%\f\r8\"3", "IYVRb");
        MobSpawnerBaseLogic.I[0x5 ^ 0x1B] = I("9\u001f5\u0016\u0005:\u0000 \u0004\u0005\u001e\u00065\r\u0018", "joTak");
    }
    
    static {
        I();
    }
    
    public void setEntityName(final String mobID) {
        this.mobID = mobID;
    }
    
    public void setRandomEntity(final WeightedRandomMinecart randomEntity) {
        this.randomEntity = randomEntity;
    }
    
    public class WeightedRandomMinecart extends WeightedRandom.Item
    {
        final MobSpawnerBaseLogic this$0;
        private static final String[] I;
        private final NBTTagCompound nbtData;
        private final String entityType;
        
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
                if (3 <= 0) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        static NBTTagCompound access$1(final WeightedRandomMinecart weightedRandomMinecart) {
            return weightedRandomMinecart.nbtData;
        }
        
        public WeightedRandomMinecart(final MobSpawnerBaseLogic mobSpawnerBaseLogic, final NBTTagCompound nbtTagCompound) {
            this(mobSpawnerBaseLogic, nbtTagCompound.getCompoundTag(WeightedRandomMinecart.I["".length()]), nbtTagCompound.getString(WeightedRandomMinecart.I[" ".length()]), nbtTagCompound.getInteger(WeightedRandomMinecart.I["  ".length()]));
        }
        
        private static void I() {
            (I = new String[0x3 ^ 0xA])["".length()] = I("5\u0000:(\u0015\u0017\u0006<=\u0003", "erUXp");
            WeightedRandomMinecart.I[" ".length()] = I("\f3\u0014.", "XJdKe");
            WeightedRandomMinecart.I["  ".length()] = I("\u0000)<4;#", "WLUSS");
            WeightedRandomMinecart.I["   ".length()] = I(")\u000b\u0017 (\u0005\u0010\r", "dbyEK");
            WeightedRandomMinecart.I[0x2D ^ 0x29] = I("\u0011<\u001e<", "EEnYy");
            WeightedRandomMinecart.I[0x53 ^ 0x56] = I("\t\b\u001d-\u0005%\u0013\u0007\u001a\u000f \u0004\u0012*\n!", "DasHf");
            WeightedRandomMinecart.I[0x35 ^ 0x33] = I("&\u001c;\u0011\u0013\u0004\u001a=\u0004\u0005", "vnTav");
            WeightedRandomMinecart.I[0x3F ^ 0x38] = I(":\u0018\u0006\u0013", "navvd");
            WeightedRandomMinecart.I[0x18 ^ 0x10] = I("\u0019\u0014+\u0015\u001e:", "NqBrv");
        }
        
        public NBTTagCompound toNBT() {
            final NBTTagCompound nbtTagCompound = new NBTTagCompound();
            nbtTagCompound.setTag(WeightedRandomMinecart.I[0x12 ^ 0x14], this.nbtData);
            nbtTagCompound.setString(WeightedRandomMinecart.I[0xAB ^ 0xAC], this.entityType);
            nbtTagCompound.setInteger(WeightedRandomMinecart.I[0x18 ^ 0x10], this.itemWeight);
            return nbtTagCompound;
        }
        
        public WeightedRandomMinecart(final MobSpawnerBaseLogic mobSpawnerBaseLogic, final NBTTagCompound nbtTagCompound, final String s) {
            this(mobSpawnerBaseLogic, nbtTagCompound, s, " ".length());
        }
        
        private WeightedRandomMinecart(final MobSpawnerBaseLogic this$0, final NBTTagCompound nbtData, String name, final int n) {
            this.this$0 = this$0;
            super(n);
            if (name.equals(WeightedRandomMinecart.I["   ".length()])) {
                if (nbtData != null) {
                    name = EntityMinecart.EnumMinecartType.byNetworkID(nbtData.getInteger(WeightedRandomMinecart.I[0x81 ^ 0x85])).getName();
                    "".length();
                    if (3 < 3) {
                        throw null;
                    }
                }
                else {
                    name = WeightedRandomMinecart.I[0x7E ^ 0x7B];
                }
            }
            this.nbtData = nbtData;
            this.entityType = name;
        }
        
        static String access$0(final WeightedRandomMinecart weightedRandomMinecart) {
            return weightedRandomMinecart.entityType;
        }
        
        static {
            I();
        }
    }
}

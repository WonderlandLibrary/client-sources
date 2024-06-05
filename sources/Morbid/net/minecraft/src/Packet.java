package net.minecraft.src;

import java.util.*;
import java.net.*;
import java.io.*;

public abstract class Packet
{
    public static IntHashMap packetIdToClassMap;
    private static Map packetClassToIdMap;
    private static Set clientPacketIdList;
    private static Set serverPacketIdList;
    protected ILogAgent field_98193_m;
    public final long creationTimeMillis;
    public static long receivedID;
    public static long receivedSize;
    public static long sentID;
    public static long sentSize;
    public boolean isChunkDataPacket;
    
    static {
        Packet.packetIdToClassMap = new IntHashMap();
        Packet.packetClassToIdMap = new HashMap();
        Packet.clientPacketIdList = new HashSet();
        Packet.serverPacketIdList = new HashSet();
        addIdClassMapping(0, true, true, Packet0KeepAlive.class);
        addIdClassMapping(1, true, true, Packet1Login.class);
        addIdClassMapping(2, false, true, Packet2ClientProtocol.class);
        addIdClassMapping(3, true, true, Packet3Chat.class);
        addIdClassMapping(4, true, false, Packet4UpdateTime.class);
        addIdClassMapping(5, true, false, Packet5PlayerInventory.class);
        addIdClassMapping(6, true, false, Packet6SpawnPosition.class);
        addIdClassMapping(7, false, true, Packet7UseEntity.class);
        addIdClassMapping(8, true, false, Packet8UpdateHealth.class);
        addIdClassMapping(9, true, true, Packet9Respawn.class);
        addIdClassMapping(10, true, true, Packet10Flying.class);
        addIdClassMapping(11, true, true, Packet11PlayerPosition.class);
        addIdClassMapping(12, true, true, Packet12PlayerLook.class);
        addIdClassMapping(13, true, true, Packet13PlayerLookMove.class);
        addIdClassMapping(14, false, true, Packet14BlockDig.class);
        addIdClassMapping(15, false, true, Packet15Place.class);
        addIdClassMapping(16, true, true, Packet16BlockItemSwitch.class);
        addIdClassMapping(17, true, false, Packet17Sleep.class);
        addIdClassMapping(18, true, true, Packet18Animation.class);
        addIdClassMapping(19, false, true, Packet19EntityAction.class);
        addIdClassMapping(20, true, false, Packet20NamedEntitySpawn.class);
        addIdClassMapping(22, true, false, Packet22Collect.class);
        addIdClassMapping(23, true, false, Packet23VehicleSpawn.class);
        addIdClassMapping(24, true, false, Packet24MobSpawn.class);
        addIdClassMapping(25, true, false, Packet25EntityPainting.class);
        addIdClassMapping(26, true, false, Packet26EntityExpOrb.class);
        addIdClassMapping(28, true, false, Packet28EntityVelocity.class);
        addIdClassMapping(29, true, false, Packet29DestroyEntity.class);
        addIdClassMapping(30, true, false, Packet30Entity.class);
        addIdClassMapping(31, true, false, Packet31RelEntityMove.class);
        addIdClassMapping(32, true, false, Packet32EntityLook.class);
        addIdClassMapping(33, true, false, Packet33RelEntityMoveLook.class);
        addIdClassMapping(34, true, false, Packet34EntityTeleport.class);
        addIdClassMapping(35, true, false, Packet35EntityHeadRotation.class);
        addIdClassMapping(38, true, false, Packet38EntityStatus.class);
        addIdClassMapping(39, true, false, Packet39AttachEntity.class);
        addIdClassMapping(40, true, false, Packet40EntityMetadata.class);
        addIdClassMapping(41, true, false, Packet41EntityEffect.class);
        addIdClassMapping(42, true, false, Packet42RemoveEntityEffect.class);
        addIdClassMapping(43, true, false, Packet43Experience.class);
        addIdClassMapping(51, true, false, Packet51MapChunk.class);
        addIdClassMapping(52, true, false, Packet52MultiBlockChange.class);
        addIdClassMapping(53, true, false, Packet53BlockChange.class);
        addIdClassMapping(54, true, false, Packet54PlayNoteBlock.class);
        addIdClassMapping(55, true, false, Packet55BlockDestroy.class);
        addIdClassMapping(56, true, false, Packet56MapChunks.class);
        addIdClassMapping(60, true, false, Packet60Explosion.class);
        addIdClassMapping(61, true, false, Packet61DoorChange.class);
        addIdClassMapping(62, true, false, Packet62LevelSound.class);
        addIdClassMapping(63, true, false, Packet63WorldParticles.class);
        addIdClassMapping(70, true, false, Packet70GameEvent.class);
        addIdClassMapping(71, true, false, Packet71Weather.class);
        addIdClassMapping(100, true, false, Packet100OpenWindow.class);
        addIdClassMapping(101, true, true, Packet101CloseWindow.class);
        addIdClassMapping(102, false, true, Packet102WindowClick.class);
        addIdClassMapping(103, true, false, Packet103SetSlot.class);
        addIdClassMapping(104, true, false, Packet104WindowItems.class);
        addIdClassMapping(105, true, false, Packet105UpdateProgressbar.class);
        addIdClassMapping(106, true, true, Packet106Transaction.class);
        addIdClassMapping(107, true, true, Packet107CreativeSetSlot.class);
        addIdClassMapping(108, false, true, Packet108EnchantItem.class);
        addIdClassMapping(130, true, true, Packet130UpdateSign.class);
        addIdClassMapping(131, true, false, Packet131MapData.class);
        addIdClassMapping(132, true, false, Packet132TileEntityData.class);
        addIdClassMapping(200, true, false, Packet200Statistic.class);
        addIdClassMapping(201, true, false, Packet201PlayerInfo.class);
        addIdClassMapping(202, true, true, Packet202PlayerAbilities.class);
        addIdClassMapping(203, true, true, Packet203AutoComplete.class);
        addIdClassMapping(204, false, true, Packet204ClientInfo.class);
        addIdClassMapping(205, false, true, Packet205ClientCommand.class);
        addIdClassMapping(206, true, false, Packet206SetObjective.class);
        addIdClassMapping(207, true, false, Packet207SetScore.class);
        addIdClassMapping(208, true, false, Packet208SetDisplayObjective.class);
        addIdClassMapping(209, true, false, Packet209SetPlayerTeam.class);
        addIdClassMapping(250, true, true, Packet250CustomPayload.class);
        addIdClassMapping(252, true, true, Packet252SharedKey.class);
        addIdClassMapping(253, true, false, Packet253ServerAuthData.class);
        addIdClassMapping(254, false, true, Packet254ServerPing.class);
        addIdClassMapping(255, true, true, Packet255KickDisconnect.class);
    }
    
    public Packet() {
        this.creationTimeMillis = System.currentTimeMillis();
        this.isChunkDataPacket = false;
    }
    
    static void addIdClassMapping(final int par0, final boolean par1, final boolean par2, final Class par3Class) {
        if (Packet.packetIdToClassMap.containsItem(par0)) {
            throw new IllegalArgumentException("Duplicate packet id:" + par0);
        }
        if (Packet.packetClassToIdMap.containsKey(par3Class)) {
            throw new IllegalArgumentException("Duplicate packet class:" + par3Class);
        }
        Packet.packetIdToClassMap.addKey(par0, par3Class);
        Packet.packetClassToIdMap.put(par3Class, par0);
        if (par1) {
            Packet.clientPacketIdList.add(par0);
        }
        if (par2) {
            Packet.serverPacketIdList.add(par0);
        }
    }
    
    public static Packet getNewPacket(final ILogAgent par0ILogAgent, final int par1) {
        try {
            final Class var2 = (Class)Packet.packetIdToClassMap.lookup(par1);
            return (var2 == null) ? null : var2.newInstance();
        }
        catch (Exception var3) {
            var3.printStackTrace();
            par0ILogAgent.logSevere("Skipping packet with id " + par1);
            return null;
        }
    }
    
    public static void writeByteArray(final DataOutputStream par0DataOutputStream, final byte[] par1ArrayOfByte) throws IOException {
        par0DataOutputStream.writeShort(par1ArrayOfByte.length);
        par0DataOutputStream.write(par1ArrayOfByte);
    }
    
    public static byte[] readBytesFromStream(final DataInputStream par0DataInputStream) throws IOException {
        final short var1 = par0DataInputStream.readShort();
        if (var1 < 0) {
            throw new IOException("Key was smaller than nothing!  Weird key!");
        }
        final byte[] var2 = new byte[var1];
        par0DataInputStream.readFully(var2);
        return var2;
    }
    
    public final int getPacketId() {
        return Packet.packetClassToIdMap.get(this.getClass());
    }
    
    public static Packet readPacket(final ILogAgent par0ILogAgent, final DataInputStream par1DataInputStream, final boolean par2, final Socket par3Socket) throws IOException {
        final boolean var4 = false;
        Packet var5 = null;
        final int var6 = par3Socket.getSoTimeout();
        int var7;
        try {
            var7 = par1DataInputStream.read();
            if (var7 == -1) {
                return null;
            }
            if ((par2 && !Packet.serverPacketIdList.contains(var7)) || (!par2 && !Packet.clientPacketIdList.contains(var7))) {
                throw new IOException("Bad packet id " + var7);
            }
            var5 = getNewPacket(par0ILogAgent, var7);
            if (var5 == null) {
                throw new IOException("Bad packet id " + var7);
            }
            var5.field_98193_m = par0ILogAgent;
            if (var5 instanceof Packet254ServerPing) {
                par3Socket.setSoTimeout(1500);
            }
            var5.readPacketData(par1DataInputStream);
            ++Packet.receivedID;
            Packet.receivedSize += var5.getPacketSize();
        }
        catch (EOFException var8) {
            par0ILogAgent.logSevere("Reached end of stream");
            return null;
        }
        PacketCount.countPacket(var7, var5.getPacketSize());
        ++Packet.receivedID;
        Packet.receivedSize += var5.getPacketSize();
        par3Socket.setSoTimeout(var6);
        return var5;
    }
    
    public static void writePacket(final Packet par0Packet, final DataOutputStream par1DataOutputStream) throws IOException {
        par1DataOutputStream.write(par0Packet.getPacketId());
        par0Packet.writePacketData(par1DataOutputStream);
        ++Packet.sentID;
        Packet.sentSize += par0Packet.getPacketSize();
    }
    
    public static void writeString(final String par0Str, final DataOutputStream par1DataOutputStream) throws IOException {
        if (par0Str.length() > 32767) {
            throw new IOException("String too big");
        }
        par1DataOutputStream.writeShort(par0Str.length());
        par1DataOutputStream.writeChars(par0Str);
    }
    
    public static String readString(final DataInputStream par0DataInputStream, final int par1) throws IOException {
        final short var2 = par0DataInputStream.readShort();
        if (var2 > par1) {
            throw new IOException("Received string length longer than maximum allowed (" + var2 + " > " + par1 + ")");
        }
        if (var2 < 0) {
            throw new IOException("Received string length is less than zero! Weird string!");
        }
        final StringBuilder var3 = new StringBuilder();
        for (int var4 = 0; var4 < var2; ++var4) {
            var3.append(par0DataInputStream.readChar());
        }
        return var3.toString();
    }
    
    public abstract void readPacketData(final DataInputStream p0) throws IOException;
    
    public abstract void writePacketData(final DataOutputStream p0) throws IOException;
    
    public abstract void processPacket(final NetHandler p0);
    
    public abstract int getPacketSize();
    
    public boolean isRealPacket() {
        return false;
    }
    
    public boolean containsSameEntityIDAs(final Packet par1Packet) {
        return false;
    }
    
    public boolean canProcessAsync() {
        return false;
    }
    
    @Override
    public String toString() {
        final String var1 = this.getClass().getSimpleName();
        return var1;
    }
    
    public static ItemStack readItemStack(final DataInputStream par0DataInputStream) throws IOException {
        ItemStack var1 = null;
        final short var2 = par0DataInputStream.readShort();
        if (var2 >= 0) {
            final byte var3 = par0DataInputStream.readByte();
            final short var4 = par0DataInputStream.readShort();
            var1 = new ItemStack(var2, var3, var4);
            var1.stackTagCompound = readNBTTagCompound(par0DataInputStream);
        }
        return var1;
    }
    
    public static void writeItemStack(final ItemStack par0ItemStack, final DataOutputStream par1DataOutputStream) throws IOException {
        if (par0ItemStack == null) {
            par1DataOutputStream.writeShort(-1);
        }
        else {
            par1DataOutputStream.writeShort(par0ItemStack.itemID);
            par1DataOutputStream.writeByte(par0ItemStack.stackSize);
            par1DataOutputStream.writeShort(par0ItemStack.getItemDamage());
            NBTTagCompound var2 = null;
            if (par0ItemStack.getItem().isDamageable() || par0ItemStack.getItem().getShareTag()) {
                var2 = par0ItemStack.stackTagCompound;
            }
            writeNBTTagCompound(var2, par1DataOutputStream);
        }
    }
    
    public static NBTTagCompound readNBTTagCompound(final DataInputStream par0DataInputStream) throws IOException {
        final short var1 = par0DataInputStream.readShort();
        if (var1 < 0) {
            return null;
        }
        final byte[] var2 = new byte[var1];
        par0DataInputStream.readFully(var2);
        return CompressedStreamTools.decompress(var2);
    }
    
    protected static void writeNBTTagCompound(final NBTTagCompound par0NBTTagCompound, final DataOutputStream par1DataOutputStream) throws IOException {
        if (par0NBTTagCompound == null) {
            par1DataOutputStream.writeShort(-1);
        }
        else {
            final byte[] var2 = CompressedStreamTools.compress(par0NBTTagCompound);
            par1DataOutputStream.writeShort((short)var2.length);
            par1DataOutputStream.write(var2);
        }
    }
}

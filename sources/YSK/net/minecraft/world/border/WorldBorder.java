package net.minecraft.world.border;

import com.google.common.collect.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;

public class WorldBorder
{
    private double startDiameter;
    private int worldSize;
    private long endTime;
    private double damageBuffer;
    private double centerX;
    private double centerZ;
    private long startTime;
    private int warningTime;
    private double endDiameter;
    private double damageAmount;
    private int warningDistance;
    private final List<IBorderListener> listeners;
    
    public void addListener(final IBorderListener borderListener) {
        this.listeners.add(borderListener);
    }
    
    public double maxZ() {
        double n = this.getCenterZ() + this.getDiameter() / 2.0;
        if (n > this.worldSize) {
            n = this.worldSize;
        }
        return n;
    }
    
    public WorldBorder() {
        this.listeners = (List<IBorderListener>)Lists.newArrayList();
        this.centerX = 0.0;
        this.centerZ = 0.0;
        this.startDiameter = 6.0E7;
        this.endDiameter = this.startDiameter;
        this.worldSize = 4911267 + 24810417 - 319697 + 597997;
        this.damageAmount = 0.2;
        this.damageBuffer = 5.0;
        this.warningTime = (0x2A ^ 0x25);
        this.warningDistance = (0x90 ^ 0x95);
    }
    
    public void setDamageBuffer(final double damageBuffer) {
        this.damageBuffer = damageBuffer;
        final Iterator<IBorderListener> iterator = this.getListeners().iterator();
        "".length();
        if (1 >= 2) {
            throw null;
        }
        while (iterator.hasNext()) {
            iterator.next().onDamageBufferChanged(this, damageBuffer);
        }
    }
    
    public double maxX() {
        double n = this.getCenterX() + this.getDiameter() / 2.0;
        if (n > this.worldSize) {
            n = this.worldSize;
        }
        return n;
    }
    
    public boolean contains(final ChunkCoordIntPair chunkCoordIntPair) {
        if (chunkCoordIntPair.getXEnd() > this.minX() && chunkCoordIntPair.getXStart() < this.maxX() && chunkCoordIntPair.getZEnd() > this.minZ() && chunkCoordIntPair.getZStart() < this.maxZ()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public double getDamageAmount() {
        return this.damageAmount;
    }
    
    public void setTransition(final double n) {
        this.startDiameter = n;
        this.endDiameter = n;
        this.endTime = System.currentTimeMillis();
        this.startTime = this.endTime;
        final Iterator<IBorderListener> iterator = this.getListeners().iterator();
        "".length();
        if (4 != 4) {
            throw null;
        }
        while (iterator.hasNext()) {
            iterator.next().onSizeChanged(this, n);
        }
    }
    
    public EnumBorderStatus getStatus() {
        EnumBorderStatus enumBorderStatus;
        if (this.endDiameter < this.startDiameter) {
            enumBorderStatus = EnumBorderStatus.SHRINKING;
            "".length();
            if (1 >= 2) {
                throw null;
            }
        }
        else if (this.endDiameter > this.startDiameter) {
            enumBorderStatus = EnumBorderStatus.GROWING;
            "".length();
            if (4 < 4) {
                throw null;
            }
        }
        else {
            enumBorderStatus = EnumBorderStatus.STATIONARY;
        }
        return enumBorderStatus;
    }
    
    public void setWarningDistance(final int warningDistance) {
        this.warningDistance = warningDistance;
        final Iterator<IBorderListener> iterator = this.getListeners().iterator();
        "".length();
        if (4 != 4) {
            throw null;
        }
        while (iterator.hasNext()) {
            iterator.next().onWarningDistanceChanged(this, warningDistance);
        }
    }
    
    public double getDiameter() {
        if (this.getStatus() != EnumBorderStatus.STATIONARY) {
            final double n = (System.currentTimeMillis() - this.startTime) / (this.endTime - this.startTime);
            if (n < 1.0) {
                return this.startDiameter + (this.endDiameter - this.startDiameter) * n;
            }
            this.setTransition(this.endDiameter);
        }
        return this.startDiameter;
    }
    
    public double minX() {
        double n = this.getCenterX() - this.getDiameter() / 2.0;
        if (n < -this.worldSize) {
            n = -this.worldSize;
        }
        return n;
    }
    
    public boolean contains(final AxisAlignedBB axisAlignedBB) {
        if (axisAlignedBB.maxX > this.minX() && axisAlignedBB.minX < this.maxX() && axisAlignedBB.maxZ > this.minZ() && axisAlignedBB.minZ < this.maxZ()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public double getTargetSize() {
        return this.endDiameter;
    }
    
    public boolean contains(final BlockPos blockPos) {
        if (blockPos.getX() + " ".length() > this.minX() && blockPos.getX() < this.maxX() && blockPos.getZ() + " ".length() > this.minZ() && blockPos.getZ() < this.maxZ()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    protected List<IBorderListener> getListeners() {
        return (List<IBorderListener>)Lists.newArrayList((Iterable)this.listeners);
    }
    
    public double minZ() {
        double n = this.getCenterZ() - this.getDiameter() / 2.0;
        if (n < -this.worldSize) {
            n = -this.worldSize;
        }
        return n;
    }
    
    public double getCenterZ() {
        return this.centerZ;
    }
    
    public double getClosestDistance(final Entity entity) {
        return this.getClosestDistance(entity.posX, entity.posZ);
    }
    
    public int getWarningDistance() {
        return this.warningDistance;
    }
    
    public int getWarningTime() {
        return this.warningTime;
    }
    
    public long getTimeUntilTarget() {
        long n;
        if (this.getStatus() != EnumBorderStatus.STATIONARY) {
            n = this.endTime - System.currentTimeMillis();
            "".length();
            if (0 >= 4) {
                throw null;
            }
        }
        else {
            n = 0L;
        }
        return n;
    }
    
    public double getResizeSpeed() {
        double n;
        if (this.endTime == this.startTime) {
            n = 0.0;
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            n = Math.abs(this.startDiameter - this.endDiameter) / (this.endTime - this.startTime);
        }
        return n;
    }
    
    public double getCenterX() {
        return this.centerX;
    }
    
    public void setCenter(final double centerX, final double centerZ) {
        this.centerX = centerX;
        this.centerZ = centerZ;
        final Iterator<IBorderListener> iterator = this.getListeners().iterator();
        "".length();
        if (4 < -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            iterator.next().onCenterChanged(this, centerX, centerZ);
        }
    }
    
    public double getDamageBuffer() {
        return this.damageBuffer;
    }
    
    public void setDamageAmount(final double damageAmount) {
        this.damageAmount = damageAmount;
        final Iterator<IBorderListener> iterator = this.getListeners().iterator();
        "".length();
        if (-1 != -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            iterator.next().onDamageAmountChanged(this, damageAmount);
        }
    }
    
    public double getClosestDistance(final double n, final double n2) {
        return Math.min(Math.min(Math.min(n - this.minX(), this.maxX() - n), n2 - this.minZ()), this.maxZ() - n2);
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
            if (1 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void setTransition(final double startDiameter, final double endDiameter, final long n) {
        this.startDiameter = startDiameter;
        this.endDiameter = endDiameter;
        this.startTime = System.currentTimeMillis();
        this.endTime = this.startTime + n;
        final Iterator<IBorderListener> iterator = this.getListeners().iterator();
        "".length();
        if (3 == 4) {
            throw null;
        }
        while (iterator.hasNext()) {
            iterator.next().onTransitionStarted(this, startDiameter, endDiameter, n);
        }
    }
    
    public void setSize(final int worldSize) {
        this.worldSize = worldSize;
    }
    
    public int getSize() {
        return this.worldSize;
    }
    
    public void setWarningTime(final int warningTime) {
        this.warningTime = warningTime;
        final Iterator<IBorderListener> iterator = this.getListeners().iterator();
        "".length();
        if (-1 < -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            iterator.next().onWarningTimeChanged(this, warningTime);
        }
    }
}

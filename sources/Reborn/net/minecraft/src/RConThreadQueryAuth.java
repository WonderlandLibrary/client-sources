package net.minecraft.src;

import java.net.*;
import java.util.*;

class RConThreadQueryAuth
{
    private long timestamp;
    private int randomChallenge;
    private byte[] requestId;
    private byte[] challengeValue;
    private String requestIdAsString;
    final RConThreadQuery queryThread;
    
    public RConThreadQueryAuth(final RConThreadQuery par1RConThreadQuery, final DatagramPacket par2DatagramPacket) {
        this.queryThread = par1RConThreadQuery;
        this.timestamp = new Date().getTime();
        final byte[] var3 = par2DatagramPacket.getData();
        (this.requestId = new byte[4])[0] = var3[3];
        this.requestId[1] = var3[4];
        this.requestId[2] = var3[5];
        this.requestId[3] = var3[6];
        this.requestIdAsString = new String(this.requestId);
        this.randomChallenge = new Random().nextInt(16777216);
        this.challengeValue = String.format("\t%s%d\u0000", this.requestIdAsString, this.randomChallenge).getBytes();
    }
    
    public Boolean hasExpired(final long par1) {
        return this.timestamp < par1;
    }
    
    public int getRandomChallenge() {
        return this.randomChallenge;
    }
    
    public byte[] getChallengeValue() {
        return this.challengeValue;
    }
    
    public byte[] getRequestId() {
        return this.requestId;
    }
}

package org.spongycastle.crypto.tls;

import java.io.IOException;

abstract interface DTLSHandshakeRetransmit
{
  public abstract void receivedHandshakeRecord(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
    throws IOException;
}

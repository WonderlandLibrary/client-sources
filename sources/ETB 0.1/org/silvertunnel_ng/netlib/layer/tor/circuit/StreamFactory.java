package org.silvertunnel_ng.netlib.layer.tor.circuit;

import java.io.IOException;
import org.silvertunnel_ng.netlib.layer.tor.common.TCPStreamProperties;
import org.silvertunnel_ng.netlib.layer.tor.util.TorException;

public abstract interface StreamFactory
{
  public abstract Stream createStream(Circuit paramCircuit, TCPStreamProperties paramTCPStreamProperties, int paramInt)
    throws IOException, TorException;
}

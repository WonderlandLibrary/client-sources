package net.minecraft.network;

import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.IThreadListener;
import us.dev.direkt.Direkt;
import us.dev.direkt.event.internal.events.game.network.EventPostReceivePacket;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class PacketThreadUtil
{
    public static <T extends INetHandler> void checkThreadAndEnqueue(final Packet<T> packetIn, final T processor, IThreadListener scheduler) throws ThreadQuickExitException
    {
        if (!scheduler.isCallingFromMinecraftThread())
        {
            scheduler.addScheduledTask(new Runnable()
            {
                public void run()
                {
                    packetIn.processPacket(processor);
                    Type[] genericInterfaces;
                    if ((genericInterfaces = packetIn.getClass().getGenericInterfaces()).length > 0 && ((ParameterizedType) genericInterfaces[0]).getActualTypeArguments()[0].equals(INetHandlerPlayClient.class)) {
//                        TODO: Direkt: EventPostReceivePacket
                        Direkt.getInstance().getEventManager().call(new EventPostReceivePacket(packetIn));
                    }
                }
            });
            throw ThreadQuickExitException.INSTANCE;
        }
    }
}

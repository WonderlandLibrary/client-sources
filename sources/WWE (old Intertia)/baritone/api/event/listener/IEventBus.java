package baritone.api.event.listener;

public interface IEventBus extends IGameEventListener
{
    void registerEventListener(final IGameEventListener p0);
}

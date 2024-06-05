package cn.Hanabi.injection.interfaces;

import com.google.common.collect.*;
import net.minecraft.client.network.*;

public interface IGuiPlayerTabOverlay
{
    Ordering<NetworkPlayerInfo> getField();
}

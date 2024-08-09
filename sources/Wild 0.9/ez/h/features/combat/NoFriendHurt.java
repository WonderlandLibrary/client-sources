package ez.h.features.combat;

import ez.h.event.events.*;
import ez.h.managers.*;
import ez.h.event.*;
import ez.h.features.*;

public class NoFriendHurt extends Feature
{
    @EventTarget
    public void onPacketSent(final EventAttack eventAttack) {
        if (FriendManager.isFriend(eventAttack.attackEntity.h_())) {
            eventAttack.setCancelled(true);
        }
    }
    
    public NoFriendHurt() {
        super("NoFriendHurt", "\u041d\u0435 \u043f\u043e\u0437\u0432\u043e\u043b\u044f\u0435\u0442 \u0430\u0442\u0430\u043a\u043e\u0432\u0430\u0442\u044c \u0434\u0440\u0443\u0437\u0435\u0439.", Category.COMBAT);
    }
}

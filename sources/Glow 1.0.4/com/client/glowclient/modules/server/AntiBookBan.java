package com.client.glowclient.modules.server;

import com.client.glowclient.modules.*;

public class AntiBookBan extends ModuleContainer
{
    public AntiBookBan() {
        super(Category.SERVER, "AntiBookBan", false, -1, "Throws out all shulkers full of books");
    }
}

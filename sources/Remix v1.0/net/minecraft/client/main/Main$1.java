package net.minecraft.client.main;

import java.net.*;

static final class Main$1 extends Authenticator {
    final /* synthetic */ String val$var25;
    final /* synthetic */ String val$var26;
    
    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(this.val$var25, this.val$var26.toCharArray());
    }
}
/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.authlib.yggdrasil.response;

import com.mojang.authlib.yggdrasil.response.Response;

public class PrivilegesResponse
extends Response {
    private Privileges privileges = new Privileges(this);

    public Privileges getPrivileges() {
        return this.privileges;
    }

    public class Privileges {
        private Privilege onlineChat;
        private Privilege multiplayerServer;
        private Privilege multiplayerRealms;
        final PrivilegesResponse this$0;

        public Privileges(PrivilegesResponse privilegesResponse) {
            this.this$0 = privilegesResponse;
            this.onlineChat = new Privilege(this);
            this.multiplayerServer = new Privilege(this);
            this.multiplayerRealms = new Privilege(this);
        }

        public Privilege getOnlineChat() {
            return this.onlineChat;
        }

        public Privilege getMultiplayerServer() {
            return this.multiplayerServer;
        }

        public Privilege getMultiplayerRealms() {
            return this.multiplayerRealms;
        }

        public class Privilege {
            private boolean enabled;
            final Privileges this$1;

            public Privilege(Privileges privileges) {
                this.this$1 = privileges;
            }

            public boolean isEnabled() {
                return this.enabled;
            }
        }
    }
}


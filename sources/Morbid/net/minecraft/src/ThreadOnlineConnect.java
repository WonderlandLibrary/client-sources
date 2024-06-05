package net.minecraft.src;

import java.net.*;

class ThreadOnlineConnect extends Thread
{
    final String field_96595_a;
    final int field_96593_b;
    final TaskOnlineConnect field_96594_c;
    
    ThreadOnlineConnect(final TaskOnlineConnect par1TaskOnlineConnect, final String par2Str, final int par3) {
        this.field_96594_c = par1TaskOnlineConnect;
        this.field_96595_a = par2Str;
        this.field_96593_b = par3;
    }
    
    @Override
    public void run() {
        try {
            TaskOnlineConnect.func_96583_a(this.field_96594_c, new NetClientHandler(this.field_96594_c.func_96578_b(), this.field_96595_a, this.field_96593_b, TaskOnlineConnect.func_98172_a(this.field_96594_c)));
            if (this.field_96594_c.func_96577_c()) {
                return;
            }
            this.field_96594_c.func_96576_b(StringTranslate.getInstance().translateKey("mco.connect.authorizing"));
            TaskOnlineConnect.func_96580_a(this.field_96594_c).addToSendQueue(new Packet2ClientProtocol(61, this.field_96594_c.func_96578_b().session.username, this.field_96595_a, this.field_96593_b));
        }
        catch (UnknownHostException var5) {
            if (this.field_96594_c.func_96577_c()) {
                return;
            }
            this.field_96594_c.func_96578_b().displayGuiScreen(new GuiScreenDisconnectedOnline(TaskOnlineConnect.func_98172_a(this.field_96594_c), "connect.failed", "disconnect.genericReason", new Object[] { "Unknown host '" + this.field_96595_a + "'" }));
        }
        catch (ConnectException var3) {
            if (this.field_96594_c.func_96577_c()) {
                return;
            }
            this.field_96594_c.func_96578_b().displayGuiScreen(new GuiScreenDisconnectedOnline(TaskOnlineConnect.func_98172_a(this.field_96594_c), "connect.failed", "disconnect.genericReason", new Object[] { var3.getMessage() }));
        }
        catch (Exception var4) {
            if (this.field_96594_c.func_96577_c()) {
                return;
            }
            var4.printStackTrace();
            this.field_96594_c.func_96578_b().displayGuiScreen(new GuiScreenDisconnectedOnline(TaskOnlineConnect.func_98172_a(this.field_96594_c), "connect.failed", "disconnect.genericReason", new Object[] { var4.toString() }));
        }
    }
}

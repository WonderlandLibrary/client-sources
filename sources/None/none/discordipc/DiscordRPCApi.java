package none.discordipc;

import java.time.OffsetDateTime;

import net.minecraft.client.Minecraft;
import none.Client;
import none.discordipc.entities.DiscordBuild;
import none.discordipc.entities.RichPresence;
import none.discordipc.entities.pipe.PipeStatus;
import none.discordipc.exceptions.NoDiscordClientException;
import none.utils.Utils;

public class DiscordRPCApi {
	public static DiscordRPCApi Instance = new DiscordRPCApi();
	private IPCClient client = new IPCClient(636062572000575498L);;
	public String details = "I'm Gay.";
	public String state = "IGN:" + Minecraft.getMinecraft().getSession().getProfile().getName();
	public String state2 = null;
	public String details2 = null;
	public OffsetDateTime time;
	public int timer;
	public int i;
	public String[] none = {"none", "none2", "none3"};
	public int maxcurstate = -1;
	public int maxcurdetails = -1;
	public int curstate = -1;
	public int curdetails = -1;
//	public static void main(String[] args) {
//		Instance.Run();
//		Instance.time = OffsetDateTime.now();
//	}
	
	public void Run() {
		time = OffsetDateTime.now();
		i = Utils.random(0, none.length);
		try {
            client.connect(DiscordBuild.ANY);
            update("Opening None...", getDetails());
        } catch (NoDiscordClientException | RuntimeException e) {
            System.out.println("No discord client found.");
        }
	}
	
	public void update(String state, String details) {
		timer++;
		if (timer >= 200) {
			timer = 0;
			i = Utils.random(0, none.length);;
		}
		if (client != null && client.getStatus() == PipeStatus.CONNECTED) {
			RichPresence.Builder builder = new RichPresence.Builder();
			String state1 = this.state + " " + state;
			String details1 = details;
			this.state2 = state1;
			this.details2 = details1;
			client.sendRichPresence(builder
			            .setLargeImage(none[i], Client.instance.name + ":B" + Client.instance.version)
			            .setState(state1)
			            .setDetails(details1)
			            .setStartTimestamp(time)
			            .build());
		}
	}
	
	public void update(String message) {
		timer++;
		if (timer >= 200) {
			timer = 0;
			i = Utils.random(0, none.length);;
		}
		if (client != null && client.getStatus() == PipeStatus.CONNECTED) {
			RichPresence.Builder builder = new RichPresence.Builder();
			client.sendRichPresence(builder
			            .setLargeImage(none[i], Client.instance.name + ":B" + Client.instance.version)
			            .setState(state2)
			            .setDetails(message)
			            .setStartTimestamp(time)
			            .build());
		}
	}
	
	public void shutdown() {
		try {
            if (client != null && client.getStatus() == PipeStatus.CONNECTED) {
                client.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public String getDetails() {
		return details + " B:" + Client.instance.version;
	}
	
	public void updateDetails() {
		details = "I'm Dev = " + (Client.instance.ISDev ? "Yes" : "No");
	}
	
	public String getState() {
		return state;
	}
	
	public void updateState() {
		state = "IGN:" + Minecraft.getMinecraft().getSession().getProfile().getName();
	}
}

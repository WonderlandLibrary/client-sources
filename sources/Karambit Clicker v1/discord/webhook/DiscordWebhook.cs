using System;
using System.Net;
using Discord.Webhook.HookRequest;

namespace Discord.Webhook
{
	// Token: 0x02000024 RID: 36
	public class DiscordWebhook
	{
		// Token: 0x17000001 RID: 1
		// (get) Token: 0x06000112 RID: 274 RVA: 0x00011C74 File Offset: 0x00010074
		// (set) Token: 0x06000113 RID: 275 RVA: 0x00011C7C File Offset: 0x0001007C
		public string HookUrl { get; set; }

		// Token: 0x06000115 RID: 277 RVA: 0x00011C85 File Offset: 0x00010085
		public void Hook(DiscordHook HookRequest)
		{
			new WebClient
			{
				Headers = 
				{
					{
						"Content-Type",
						"multipart/form-data; boundary=" + HookRequest.Boundary
					}
				}
			}.UploadData(this.HookUrl, HookRequest.Body.ToArray());
		}
	}
}

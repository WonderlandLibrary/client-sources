using System;
using Newtonsoft.Json.Linq;

namespace Discord.Webhook.HookRequest
{
	// Token: 0x02000025 RID: 37
	public class DiscordEmbed
	{
		// Token: 0x06000116 RID: 278 RVA: 0x00011CC4 File Offset: 0x000100C4
		public DiscordEmbed(string Title = "", string Description = "", int Color = 0, string ImageUrl = "", string FooterText = "", string FooterIconUrl = "", DiscordEmbedField[] Fields = null)
		{
			JObject jobject = new JObject();
			jobject.Add("title", Title);
			jobject.Add("description", Description);
			jobject.Add("color", Color);
			JObject jobject2 = new JObject();
			jobject2.Add("url", ImageUrl);
			JObject jobject3 = new JObject();
			jobject3.Add("text", FooterText);
			jobject3.Add("icon_url", FooterIconUrl);
			jobject.Add("image", jobject2);
			jobject.Add("footer", jobject3);
			if (Fields != null)
			{
				JArray jarray = new JArray();
				foreach (DiscordEmbedField discordEmbedField in Fields)
				{
					jarray.Add(discordEmbedField.JsonData);
				}
				jobject.Add("fields", jarray);
			}
			this.JsonData = jobject;
		}

		// Token: 0x17000002 RID: 2
		// (get) Token: 0x06000117 RID: 279 RVA: 0x00011DB2 File Offset: 0x000101B2
		// (set) Token: 0x06000118 RID: 280 RVA: 0x00011DBA File Offset: 0x000101BA
		public JObject JsonData { get; private set; }
	}
}

using System;
using Newtonsoft.Json.Linq;

namespace Discord.Webhook.HookRequest
{
	// Token: 0x02000026 RID: 38
	public class DiscordEmbedField
	{
		// Token: 0x06000119 RID: 281 RVA: 0x00011DC4 File Offset: 0x000101C4
		public DiscordEmbedField(string Name, string Value, bool Line = true)
		{
			JObject jobject = new JObject();
			jobject.Add("name", Name);
			jobject.Add("value", Value);
			jobject.Add("inline", Line);
			this.JsonData = jobject;
		}

		// Token: 0x17000003 RID: 3
		// (get) Token: 0x0600011A RID: 282 RVA: 0x00011E17 File Offset: 0x00010217
		// (set) Token: 0x0600011B RID: 283 RVA: 0x00011E1F File Offset: 0x0001021F
		public JObject JsonData { get; private set; }
	}
}

using System;
using System.IO;

namespace Discord.Webhook.HookRequest
{
	// Token: 0x02000027 RID: 39
	public class DiscordHook
	{
		// Token: 0x0600011C RID: 284 RVA: 0x00011E28 File Offset: 0x00010228
		internal DiscordHook(MemoryStream BodyData, string Bound)
		{
			this.Body = BodyData;
			this.Boundary = Bound;
		}

		// Token: 0x17000004 RID: 4
		// (get) Token: 0x0600011D RID: 285 RVA: 0x00011E3E File Offset: 0x0001023E
		// (set) Token: 0x0600011E RID: 286 RVA: 0x00011E46 File Offset: 0x00010246
		public string Boundary { get; private set; }

		// Token: 0x17000005 RID: 5
		// (get) Token: 0x0600011F RID: 287 RVA: 0x00011E4F File Offset: 0x0001024F
		// (set) Token: 0x06000120 RID: 288 RVA: 0x00011E57 File Offset: 0x00010257
		public MemoryStream Body { get; private set; }
	}
}

using System;
using System.Collections.Generic;
using System.IO;
using System.Text;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;

namespace Discord.Webhook.HookRequest
{
	// Token: 0x02000028 RID: 40
	public class DiscordHookBuilder
	{
		// Token: 0x06000121 RID: 289 RVA: 0x00011E60 File Offset: 0x00010260
		private DiscordHookBuilder(string Nickname, string AvatarUrl)
		{
			this._bound = "------------------------" + DateTime.Now.Ticks.ToString("x");
			this._nick = Nickname;
			this._avatar = AvatarUrl;
			this._json = new JObject();
			this.Embeds = new List<DiscordEmbed>();
		}

		// Token: 0x06000122 RID: 290 RVA: 0x00011EC1 File Offset: 0x000102C1
		public static DiscordHookBuilder Create(string Nickname = null, string AvatarUrl = null)
		{
			return new DiscordHookBuilder(Nickname, AvatarUrl);
		}

		// Token: 0x17000006 RID: 6
		// (get) Token: 0x06000123 RID: 291 RVA: 0x00011ECA File Offset: 0x000102CA
		// (set) Token: 0x06000124 RID: 292 RVA: 0x00011ED2 File Offset: 0x000102D2
		public FileInfo FileUpload { get; set; }

		// Token: 0x17000007 RID: 7
		// (get) Token: 0x06000125 RID: 293 RVA: 0x00011EDB File Offset: 0x000102DB
		// (set) Token: 0x06000126 RID: 294 RVA: 0x00011EE3 File Offset: 0x000102E3
		public List<DiscordEmbed> Embeds { get; private set; }

		// Token: 0x17000008 RID: 8
		// (get) Token: 0x06000127 RID: 295 RVA: 0x00011EEC File Offset: 0x000102EC
		// (set) Token: 0x06000128 RID: 296 RVA: 0x00011EF4 File Offset: 0x000102F4
		public string Message { get; set; }

		// Token: 0x17000009 RID: 9
		// (get) Token: 0x06000129 RID: 297 RVA: 0x00011EFD File Offset: 0x000102FD
		// (set) Token: 0x0600012A RID: 298 RVA: 0x00011F05 File Offset: 0x00010305
		public bool UseTTS { get; set; }

		// Token: 0x0600012B RID: 299 RVA: 0x00011F10 File Offset: 0x00010310
		public DiscordHook Build()
		{
			MemoryStream memoryStream = new MemoryStream();
			byte[] bytes = Encoding.UTF8.GetBytes("--" + this._bound + "\r\n");
			memoryStream.Write(bytes, 0, bytes.Length);
			if (this.FileUpload != null)
			{
				string s = "Content-Disposition: form-data; name=\"file\"; filename=\"" + this.FileUpload.Name + "\"\r\nContent-Type: application/octet-stream\r\n\r\n";
				byte[] bytes2 = Encoding.UTF8.GetBytes(s);
				memoryStream.Write(bytes2, 0, bytes2.Length);
				byte[] array = File.ReadAllBytes(this.FileUpload.FullName);
				memoryStream.Write(array, 0, array.Length);
				string s2 = "\r\n--" + this._bound + "\r\n";
				byte[] bytes3 = Encoding.UTF8.GetBytes(s2);
				memoryStream.Write(bytes3, 0, bytes3.Length);
			}
			this._json.Add("username", this._nick);
			this._json.Add("avatar_url", this._avatar);
			this._json.Add("content", this.Message);
			this._json.Add("tts", this.UseTTS);
			JArray jarray = new JArray();
			foreach (DiscordEmbed discordEmbed in this.Embeds)
			{
				jarray.Add(discordEmbed.JsonData);
			}
			if (jarray.Count > 0)
			{
				this._json.Add("embeds", jarray);
			}
			string s3 = string.Concat(new string[]
			{
				"Content-Disposition: form-data; name=\"payload_json\"\r\nContent-Type: application/json\r\n\r\n",
				this._json.ToString(0, Array.Empty<JsonConverter>()),
				"\r\n--",
				this._bound,
				"--"
			});
			byte[] bytes4 = Encoding.UTF8.GetBytes(s3);
			memoryStream.Write(bytes4, 0, bytes4.Length);
			return new DiscordHook(memoryStream, this._bound);
		}

		// Token: 0x04000147 RID: 327
		private string _bound;

		// Token: 0x04000148 RID: 328
		private string _nick;

		// Token: 0x04000149 RID: 329
		private string _avatar;

		// Token: 0x0400014A RID: 330
		private JObject _json;
	}
}

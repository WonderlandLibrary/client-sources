using System;
using System.Linq;
using System.Security.Principal;

namespace uhssvc
{
	// Token: 0x02000014 RID: 20
	internal class Constants
	{
		// Token: 0x17000001 RID: 1
		// (get) Token: 0x0600003C RID: 60 RVA: 0x00002329 File Offset: 0x00000529
		// (set) Token: 0x0600003D RID: 61 RVA: 0x00002330 File Offset: 0x00000530
		public static string Token { get; set; }

		// Token: 0x17000002 RID: 2
		// (get) Token: 0x0600003E RID: 62 RVA: 0x00002338 File Offset: 0x00000538
		// (set) Token: 0x0600003F RID: 63 RVA: 0x0000233F File Offset: 0x0000053F
		public static string Date { get; set; }

		// Token: 0x17000003 RID: 3
		// (get) Token: 0x06000040 RID: 64 RVA: 0x00002347 File Offset: 0x00000547
		// (set) Token: 0x06000041 RID: 65 RVA: 0x0000234E File Offset: 0x0000054E
		public static string APIENCRYPTKEY { get; set; }

		// Token: 0x17000004 RID: 4
		// (get) Token: 0x06000042 RID: 66 RVA: 0x00002356 File Offset: 0x00000556
		// (set) Token: 0x06000043 RID: 67 RVA: 0x0000235D File Offset: 0x0000055D
		public static string APIENCRYPTSALT { get; set; }

		// Token: 0x06000044 RID: 68 RVA: 0x00003E1C File Offset: 0x0000201C
		public static string RandomString(int int_0)
		{
			return new string((from string_0 in Enumerable.Repeat<string>("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789", int_0)
			select string_0[Constants.random.Next(string_0.Length)]).ToArray<char>());
		}

		// Token: 0x06000045 RID: 69 RVA: 0x00003E64 File Offset: 0x00002064
		public static string HWID()
		{
			return WindowsIdentity.GetCurrent().User.Value;
		}

		// Token: 0x0400026F RID: 623
		public static bool Breached = false;

		// Token: 0x04000270 RID: 624
		public static bool Started = false;

		// Token: 0x04000271 RID: 625
		public static string IV = null;

		// Token: 0x04000272 RID: 626
		public static string Key = null;

		// Token: 0x04000273 RID: 627
		public static string ApiUrl = "https://api.auth.gg/csharp/";

		// Token: 0x04000274 RID: 628
		public static bool Initialized = false;

		// Token: 0x04000275 RID: 629
		public static Random random = new Random();
	}
}

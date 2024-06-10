using System;
using WaveClient.ModuleManagment;

namespace WaveClient.Module
{
	// Token: 0x02000017 RID: 23
	public static class Instabreak
	{
		// Token: 0x0600005E RID: 94 RVA: 0x00003207 File Offset: 0x00001407
		public static void Tick10()
		{
			Memory0.mem.PatchMemory("Minecraft.Windows.exe", 22339807, new byte[]
			{
				243,
				15,
				17,
				87,
				32
			});
		}

		// Token: 0x0600005F RID: 95 RVA: 0x0000322E File Offset: 0x0000142E
		public static void Disable()
		{
			Memory0.mem.PatchMemory("Minecraft.Windows.exe", 22339807, new byte[]
			{
				243,
				15,
				17,
				79,
				32
			});
		}

		// Token: 0x0400003B RID: 59
		public static bool ToggleState;
	}
}

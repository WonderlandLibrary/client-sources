using System;
using WaveClient.ModuleManagment;

namespace WaveClient.Module
{
	// Token: 0x02000014 RID: 20
	public static class Instabreak
	{
		// Token: 0x06000074 RID: 116 RVA: 0x000038BC File Offset: 0x00001ABC
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

		// Token: 0x06000075 RID: 117 RVA: 0x000038E3 File Offset: 0x00001AE3
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

		// Token: 0x04000056 RID: 86
		public static bool ToggleState;
	}
}

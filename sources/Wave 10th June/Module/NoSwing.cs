using System;
using WaveClient.ModuleManagment;

namespace WaveClient.Module
{
	// Token: 0x0200001A RID: 26
	public static class NoSwing
	{
		// Token: 0x06000064 RID: 100 RVA: 0x000032D5 File Offset: 0x000014D5
		public static void Tick10()
		{
			Memory0.mem.PatchMemory("Minecraft.Windows.exe", 9791428, new byte[]
			{
				144,
				144,
				144,
				144,
				144,
				144,
				144
			});
		}

		// Token: 0x06000065 RID: 101 RVA: 0x000032FC File Offset: 0x000014FC
		public static void Disable()
		{
			Memory0.mem.PatchMemory("Minecraft.Windows.exe", 9791428, new byte[]
			{
				198,
				131,
				156,
				6,
				0,
				0,
				1
			});
		}

		// Token: 0x04000040 RID: 64
		public static bool ToggleState;
	}
}

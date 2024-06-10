using System;
using WaveClient.ModuleManagment;

namespace WaveClient.Module
{
	// Token: 0x0200001B RID: 27
	public static class Reach
	{
		// Token: 0x06000066 RID: 102 RVA: 0x00003323 File Offset: 0x00001523
		public static void Tick10()
		{
			Memory0.mem.PatchMemory("Minecraft.Windows.exe", 6628650, new byte[]
			{
				144,
				144
			});
		}

		// Token: 0x06000067 RID: 103 RVA: 0x0000334F File Offset: 0x0000154F
		public static void Disable()
		{
			Memory0.mem.PatchMemory("Minecraft.Windows.exe", 6628650, new byte[]
			{
				116,
				20
			});
		}

		// Token: 0x04000041 RID: 65
		public static bool ToggleState;
	}
}

using System;
using WaveClient.ModuleManagment;

namespace WaveClient.Module
{
	// Token: 0x02000011 RID: 17
	public static class Coords
	{
		// Token: 0x0600006E RID: 110 RVA: 0x000037FD File Offset: 0x000019FD
		public static void Tick10()
		{
			Memory0.mem.PatchMemory("Minecraft.Windows.exe", 6818925, new byte[]
			{
				144,
				144,
				144,
				144
			});
		}

		// Token: 0x0600006F RID: 111 RVA: 0x00003824 File Offset: 0x00001A24
		public static void Disable()
		{
			Memory0.mem.PatchMemory("Minecraft.Windows.exe", 6818925, new byte[]
			{
				128,
				120,
				4,
				0
			});
		}

		// Token: 0x04000052 RID: 82
		public static bool ToggleState;
	}
}

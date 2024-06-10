using System;
using WaveClient.ModuleManagment;

namespace WaveClient.Module
{
	// Token: 0x02000013 RID: 19
	public static class HighJump
	{
		// Token: 0x06000073 RID: 115 RVA: 0x000038A0 File Offset: 0x00001AA0
		public static void Tick10()
		{
			Memory0.mem.PatchMemory("Minecraft.Windows.exe", 22339807, new byte[1]);
		}

		// Token: 0x04000055 RID: 85
		public static bool ToggleState;
	}
}

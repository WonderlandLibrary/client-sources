using System;
using WaveClient.ModuleManagment;

namespace WaveClient.Module
{
	// Token: 0x02000015 RID: 21
	public static class Coords
	{
		// Token: 0x0600005A RID: 90 RVA: 0x000031B5 File Offset: 0x000013B5
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

		// Token: 0x0600005B RID: 91 RVA: 0x000031DC File Offset: 0x000013DC
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

		// Token: 0x04000039 RID: 57
		public static bool ToggleState;
	}
}

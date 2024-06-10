using System;
using WaveClient.ModuleManagment;

namespace WaveClient.Module
{
	// Token: 0x02000019 RID: 25
	public static class NoSwing
	{
		// Token: 0x0600007F RID: 127 RVA: 0x000039F9 File Offset: 0x00001BF9
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

		// Token: 0x06000080 RID: 128 RVA: 0x00003A20 File Offset: 0x00001C20
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

		// Token: 0x0400005F RID: 95
		public static bool ToggleState;
	}
}

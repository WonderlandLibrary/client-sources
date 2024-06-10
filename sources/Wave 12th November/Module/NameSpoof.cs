using System;
using Wave.Cmr.MemoryManagement;

namespace WaveClient.Module
{
	// Token: 0x02000015 RID: 21
	public static class NameSpoof
	{
		// Token: 0x06000076 RID: 118 RVA: 0x0000390A File Offset: 0x00001B0A
		public static void Tick10()
		{
		}

		// Token: 0x04000057 RID: 87
		public static bool ToggleState;

		// Token: 0x04000058 RID: 88
		private static Pointer SpeedPointer = new Pointer("Minecraft.Windows.exe", 0, new int[1]);
	}
}

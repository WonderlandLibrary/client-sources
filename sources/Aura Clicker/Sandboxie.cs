using System;
using System.Diagnostics;

// Token: 0x02000003 RID: 3
internal static class Sandboxie
{
	// Token: 0x06000009 RID: 9 RVA: 0x0000310C File Offset: 0x0000130C
	private static IntPtr GetModuleHandle(string string_0)
	{
		foreach (object obj in Process.GetCurrentProcess().Modules)
		{
			ProcessModule processModule = (ProcessModule)obj;
			if (processModule.ModuleName.ToLower().Contains(string_0.ToLower()))
			{
				return processModule.BaseAddress;
			}
		}
		return IntPtr.Zero;
	}

	// Token: 0x0600000A RID: 10 RVA: 0x0000228A File Offset: 0x0000048A
	internal static bool IsSandboxie()
	{
		return Sandboxie.GetModuleHandle("SbieDll.dll") != IntPtr.Zero;
	}
}

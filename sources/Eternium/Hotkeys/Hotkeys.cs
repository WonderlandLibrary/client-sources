using System;
using Eternium_mcpe_client.DllImports;

namespace Eternium_mcpe_client.Hotkeys
{
	// Token: 0x02000011 RID: 17
	public static class Hotkeys
	{
		// Token: 0x06000057 RID: 87 RVA: 0x00005CE4 File Offset: 0x00003EE4
		public static bool GetKeyStateDown(DllImports.VirtualKeys vk)
		{
			return Convert.ToBoolean((int)DllImports.GetAsyncKeyState(vk) & 32768);
		}

		// Token: 0x06000058 RID: 88 RVA: 0x00005D08 File Offset: 0x00003F08
		public static bool GetKeyStateDown(DllImports.VK vk)
		{
			return Convert.ToBoolean((int)DllImports.GetAsyncKeyState(vk) & 32768);
		}

		// Token: 0x06000059 RID: 89 RVA: 0x00005D2C File Offset: 0x00003F2C
		public static bool GetKeyStateUp(DllImports.VirtualKeys vk)
		{
			return !Convert.ToBoolean((int)DllImports.GetAsyncKeyState(vk) & 32768);
		}

		// Token: 0x0600005A RID: 90 RVA: 0x00005D54 File Offset: 0x00003F54
		public static bool GetKeyStateUp(DllImports.VK vk)
		{
			return !Convert.ToBoolean((int)DllImports.GetAsyncKeyState(vk) & 32768);
		}
	}
}

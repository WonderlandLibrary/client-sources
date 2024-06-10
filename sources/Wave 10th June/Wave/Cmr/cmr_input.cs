using System;
using Wave.Cmr.Win32API;

namespace Wave.Cmr
{
	// Token: 0x0200001F RID: 31
	public static class cmr_input
	{
		// Token: 0x06000090 RID: 144 RVA: 0x000038C5 File Offset: 0x00001AC5
		public static bool GetKeyStateDown(Win32.VirtualKeys vk)
		{
			return Convert.ToBoolean((int)Win32.GetAsyncKeyState(vk) & 32768);
		}

		// Token: 0x06000091 RID: 145 RVA: 0x000038D8 File Offset: 0x00001AD8
		public static bool GetKeyStateDown(Win32.VK vk)
		{
			return Convert.ToBoolean((int)Win32.GetAsyncKeyState(vk) & 32768);
		}

		// Token: 0x06000092 RID: 146 RVA: 0x000038EB File Offset: 0x00001AEB
		public static bool GetKeyStateUp(Win32.VirtualKeys vk)
		{
			return !Convert.ToBoolean((int)Win32.GetAsyncKeyState(vk) & 32768);
		}

		// Token: 0x06000093 RID: 147 RVA: 0x00003901 File Offset: 0x00001B01
		public static bool GetKeyStateUp(Win32.VK vk)
		{
			return !Convert.ToBoolean((int)Win32.GetAsyncKeyState(vk) & 32768);
		}
	}
}

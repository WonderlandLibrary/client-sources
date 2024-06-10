using System;
using Wave.Cmr.Win32API;

namespace Wave.Cmr
{
	// Token: 0x02000021 RID: 33
	public static class cmr_input
	{
		// Token: 0x060000B1 RID: 177 RVA: 0x000041D1 File Offset: 0x000023D1
		public static bool GetKeyStateDown(Win32.VirtualKeys vk)
		{
			return Convert.ToBoolean((int)Win32.GetAsyncKeyState(vk) & 32768);
		}

		// Token: 0x060000B2 RID: 178 RVA: 0x000041E4 File Offset: 0x000023E4
		public static bool GetKeyStateDown(Win32.VK vk)
		{
			return Convert.ToBoolean((int)Win32.GetAsyncKeyState(vk) & 32768);
		}

		// Token: 0x060000B3 RID: 179 RVA: 0x000041F7 File Offset: 0x000023F7
		public static bool GetKeyStateUp(Win32.VirtualKeys vk)
		{
			return !Convert.ToBoolean((int)Win32.GetAsyncKeyState(vk) & 32768);
		}

		// Token: 0x060000B4 RID: 180 RVA: 0x0000420D File Offset: 0x0000240D
		public static bool GetKeyStateUp(Win32.VK vk)
		{
			return !Convert.ToBoolean((int)Win32.GetAsyncKeyState(vk) & 32768);
		}
	}
}

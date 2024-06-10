using System;
using System.Runtime.InteropServices;
using System.Windows.Forms;

namespace Lizzard.Helper
{
	// Token: 0x02000006 RID: 6
	internal class AsyncKeyHelper
	{
		// Token: 0x06000009 RID: 9 RVA: 0x00002358 File Offset: 0x00000558
		public static bool isKeyDown(Keys key)
		{
			byte[] result = BitConverter.GetBytes(AsyncKeyHelper.GetAsyncKeyState((int)((short)key)));
			return result[0] == 1;
		}

		// Token: 0x0600000A RID: 10
		[DllImport("User32.dll")]
		private static extern short GetAsyncKeyState(int vKey);
	}
}

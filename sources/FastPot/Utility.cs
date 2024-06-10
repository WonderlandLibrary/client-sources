using System;
using System.Windows.Forms;

namespace FastPot
{
	// Token: 0x02000006 RID: 6
	internal class Utility
	{
		// Token: 0x06000034 RID: 52 RVA: 0x00003614 File Offset: 0x00001814
		public static KeyboardHook.VKeys ConvertToBindable(KeyEventArgs e)
		{
			bool flag = e.KeyCode.ToString().Length > 1 && e.KeyCode.ToString().StartsWith("F");
			KeyboardHook.VKeys result;
			if (flag)
			{
				result = (KeyboardHook.VKeys)e.KeyCode;
			}
			else
			{
				bool flag2 = e.KeyCode == Keys.ShiftKey;
				if (flag2)
				{
					result = KeyboardHook.VKeys.RSHIFT;
				}
				else
				{
					bool flag3 = (e.KeyCode >= Keys.A && e.KeyCode <= Keys.Z) || (e.KeyCode >= Keys.D0 && e.KeyCode <= Keys.D9);
					if (flag3)
					{
						result = (KeyboardHook.VKeys)e.KeyCode;
					}
					else
					{
						result = KeyboardHook.VKeys.NOT_A_KEY;
					}
				}
			}
			return result;
		}
	}
}

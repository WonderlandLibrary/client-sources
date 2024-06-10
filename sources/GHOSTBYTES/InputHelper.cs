using System;
using System.Runtime.InteropServices;
using System.Windows.Forms;
using Microsoft.VisualBasic.CompilerServices;

namespace GHOSTBYTES
{
	// Token: 0x02000034 RID: 52
	public sealed class InputHelper
	{
		// Token: 0x0600052E RID: 1326 RVA: 0x00002123 File Offset: 0x00000323
		private InputHelper()
		{
		}

		// Token: 0x0600052F RID: 1327 RVA: 0x00005410 File Offset: 0x00003610
		public static void PressKey(Keys Key, bool HardwareKey = false)
		{
			if (!HardwareKey)
			{
				InputHelper.SetKeyState(Key, false);
				InputHelper.SetKeyState(Key, true);
				return;
			}
			InputHelper.SetHardwareKeyState(Key, false);
			InputHelper.SetHardwareKeyState(Key, true);
		}

		// Token: 0x06000530 RID: 1328 RVA: 0x000229C0 File Offset: 0x00020BC0
		public static void SetKeyState(Keys Key, bool KeyUp)
		{
			Key = InputHelper.ReplaceBadKeys(Key);
			InputHelper.KEYBDINPUT ki = default(InputHelper.KEYBDINPUT);
			ki.wVk = checked((ushort)Key);
			ki.wScan = 0;
			ki.time = 0;
			ki.dwFlags = Conversions.ToUInteger(KeyUp ? InputHelper.KEYEVENTF.KEYUP : 0);
			ki.dwExtraInfo = IntPtr.Zero;
			InputHelper.INPUTUNION u = default(InputHelper.INPUTUNION);
			u.ki = ki;
			InputHelper.SendInput(1U, new InputHelper.INPUT[]
			{
				new InputHelper.INPUT
				{
					type = 1,
					U = u
				}
			}, Marshal.SizeOf(typeof(InputHelper.INPUT)));
		}

		// Token: 0x06000531 RID: 1329 RVA: 0x00022A6C File Offset: 0x00020C6C
		public static void SetHardwareKeyState(Keys Key, bool KeyUp)
		{
			Key = InputHelper.ReplaceBadKeys(Key);
			InputHelper.KEYBDINPUT ki = default(InputHelper.KEYBDINPUT);
			ki.wVk = 0;
			ki.wScan = checked((short)InputHelper.MapVirtualKeyEx((uint)Key, 0U, InputHelper.GetKeyboardLayout(0U)));
			ki.time = 0;
			ki.dwFlags = Conversions.ToUInteger(Operators.OrObject(InputHelper.KEYEVENTF.SCANCODE, KeyUp ? InputHelper.KEYEVENTF.KEYUP : 0));
			ki.dwExtraInfo = IntPtr.Zero;
			InputHelper.INPUTUNION u = default(InputHelper.INPUTUNION);
			u.ki = ki;
			InputHelper.SendInput(1U, new InputHelper.INPUT[]
			{
				new InputHelper.INPUT
				{
					type = 1,
					U = u
				}
			}, Marshal.SizeOf(typeof(InputHelper.INPUT)));
		}

		// Token: 0x06000532 RID: 1330 RVA: 0x00022B30 File Offset: 0x00020D30
		private static Keys ReplaceBadKeys(Keys Key)
		{
			Keys keys = Key;
			if (keys.HasFlag(Keys.Control))
			{
				keys = ((keys & ~Keys.Control) | Keys.ControlKey);
			}
			if (keys.HasFlag(Keys.Shift))
			{
				keys = ((keys & ~Keys.Shift) | Keys.ShiftKey);
			}
			if (keys.HasFlag(Keys.Alt))
			{
				keys = ((keys & ~Keys.Alt) | Keys.Menu);
			}
			return keys;
		}

		// Token: 0x06000533 RID: 1331
		[DllImport("user32.dll", SetLastError = true)]
		private static extern uint SendInput(uint nInputs, [MarshalAs(UnmanagedType.LPArray)] InputHelper.INPUT[] pInputs, int cbSize);

		// Token: 0x06000534 RID: 1332
		[DllImport("user32.dll")]
		private static extern uint MapVirtualKeyEx(uint uCode, uint uMapType, IntPtr dwhkl);

		// Token: 0x06000535 RID: 1333
		[DllImport("user32.dll")]
		private static extern IntPtr GetKeyboardLayout(uint idThread);

		// Token: 0x02000035 RID: 53
		private enum INPUTTYPE : uint
		{
			// Token: 0x04000230 RID: 560
			MOUSE,
			// Token: 0x04000231 RID: 561
			KEYBOARD,
			// Token: 0x04000232 RID: 562
			HARDWARE
		}

		// Token: 0x02000036 RID: 54
		[Flags]
		private enum KEYEVENTF : uint
		{
			// Token: 0x04000234 RID: 564
			EXTENDEDKEY = 1U,
			// Token: 0x04000235 RID: 565
			KEYUP = 2U,
			// Token: 0x04000236 RID: 566
			SCANCODE = 8U,
			// Token: 0x04000237 RID: 567
			UNICODE = 4U
		}

		// Token: 0x02000037 RID: 55
		[StructLayout(LayoutKind.Explicit)]
		private struct INPUTUNION
		{
			// Token: 0x04000238 RID: 568
			[FieldOffset(0)]
			public InputHelper.MOUSEINPUT mi;

			// Token: 0x04000239 RID: 569
			[FieldOffset(0)]
			public InputHelper.KEYBDINPUT ki;

			// Token: 0x0400023A RID: 570
			[FieldOffset(0)]
			public InputHelper.HARDWAREINPUT hi;
		}

		// Token: 0x02000038 RID: 56
		private struct INPUT
		{
			// Token: 0x0400023B RID: 571
			public int type;

			// Token: 0x0400023C RID: 572
			public InputHelper.INPUTUNION U;
		}

		// Token: 0x02000039 RID: 57
		private struct MOUSEINPUT
		{
			// Token: 0x0400023D RID: 573
			public int dx;

			// Token: 0x0400023E RID: 574
			public int dy;

			// Token: 0x0400023F RID: 575
			public int mouseData;

			// Token: 0x04000240 RID: 576
			public int dwFlags;

			// Token: 0x04000241 RID: 577
			public int time;

			// Token: 0x04000242 RID: 578
			public IntPtr dwExtraInfo;
		}

		// Token: 0x0200003A RID: 58
		private struct KEYBDINPUT
		{
			// Token: 0x04000243 RID: 579
			public ushort wVk;

			// Token: 0x04000244 RID: 580
			public short wScan;

			// Token: 0x04000245 RID: 581
			public uint dwFlags;

			// Token: 0x04000246 RID: 582
			public int time;

			// Token: 0x04000247 RID: 583
			public IntPtr dwExtraInfo;
		}

		// Token: 0x0200003B RID: 59
		private struct HARDWAREINPUT
		{
			// Token: 0x04000248 RID: 584
			public int uMsg;

			// Token: 0x04000249 RID: 585
			public short wParamL;

			// Token: 0x0400024A RID: 586
			public short wParamH;
		}
	}
}

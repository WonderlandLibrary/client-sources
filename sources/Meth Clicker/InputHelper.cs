using System;
using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;
using System.Windows.Forms;
using Microsoft.VisualBasic.CompilerServices;

namespace Meth
{
	// Token: 0x0200002E RID: 46
	public sealed class InputHelper
	{
		// Token: 0x0600031D RID: 797 RVA: 0x00002A35 File Offset: 0x00000C35
		private InputHelper()
		{
		}

		// Token: 0x0600031E RID: 798
		[DllImport("user32.dll")]
		private static extern IntPtr GetKeyboardLayout(uint idThread);

		// Token: 0x0600031F RID: 799
		[DllImport("user32.dll")]
		private static extern uint MapVirtualKeyEx(uint uCode, uint uMapType, IntPtr dwhkl);

		// Token: 0x06000320 RID: 800 RVA: 0x00015724 File Offset: 0x00013924
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

		// Token: 0x06000321 RID: 801 RVA: 0x00015748 File Offset: 0x00013948
		private static Keys ReplaceBadKeys(Keys Key)
		{
			Keys keys = Key;
			if (keys.HasFlag(Keys.Control))
			{
				keys = ((keys & (Keys.KeyCode | Keys.Shift | Keys.Alt)) | Keys.ControlKey);
			}
			if (keys.HasFlag(Keys.Shift))
			{
				keys = ((keys & (Keys.KeyCode | Keys.Control | Keys.Alt)) | Keys.ShiftKey);
			}
			if (keys.HasFlag(Keys.Alt))
			{
				keys = ((keys & (Keys.KeyCode | Keys.Shift | Keys.Control)) | Keys.Menu);
			}
			return keys;
		}

		// Token: 0x06000322 RID: 802
		[DllImport("user32.dll", SetLastError = true)]
		private static extern uint SendInput(uint nInputs, InputHelper.INPUT[] pInputs, int cbSize);

		// Token: 0x06000323 RID: 803 RVA: 0x000157C0 File Offset: 0x000139C0
		public static void SetHardwareKeyState(Keys Key, bool KeyUp)
		{
			Key = InputHelper.ReplaceBadKeys(Key);
			InputHelper.KEYBDINPUT ki = new InputHelper.KEYBDINPUT
			{
				wVk = 0,
				time = 0,
				dwFlags = Conversions.ToUInteger(RuntimeHelpers.GetObjectValue(Operators.OrObject(InputHelper.KEYEVENTF.SCANCODE, RuntimeHelpers.GetObjectValue(KeyUp ? InputHelper.KEYEVENTF.KEYUP : 0)))),
				dwExtraInfo = IntPtr.Zero
			};
			InputHelper.INPUTUNION u = new InputHelper.INPUTUNION
			{
				ki = ki
			};
			InputHelper.INPUT input = new InputHelper.INPUT
			{
				type = 1,
				U = u
			};
			InputHelper.SendInput(1U, new InputHelper.INPUT[]
			{
				input
			}, Marshal.SizeOf(typeof(InputHelper.INPUT)));
		}

		// Token: 0x06000324 RID: 804 RVA: 0x00015880 File Offset: 0x00013A80
		public static void SetKeyState(Keys Key, bool KeyUp)
		{
			Key = InputHelper.ReplaceBadKeys(Key);
			InputHelper.KEYBDINPUT ki = new InputHelper.KEYBDINPUT
			{
				wVk = (ushort)Key,
				wScan = 0,
				time = 0,
				dwFlags = Conversions.ToUInteger(RuntimeHelpers.GetObjectValue(KeyUp ? InputHelper.KEYEVENTF.KEYUP : 0)),
				dwExtraInfo = IntPtr.Zero
			};
			InputHelper.INPUTUNION u = new InputHelper.INPUTUNION
			{
				ki = ki
			};
			InputHelper.INPUT input = new InputHelper.INPUT
			{
				type = 1,
				U = u
			};
			InputHelper.SendInput(1U, new InputHelper.INPUT[]
			{
				input
			}, Marshal.SizeOf(typeof(InputHelper.INPUT)));
		}

		// Token: 0x02000063 RID: 99
		private struct HARDWAREINPUT
		{
			// Token: 0x040001ED RID: 493
			public int uMsg;

			// Token: 0x040001EE RID: 494
			public short wParamL;

			// Token: 0x040001EF RID: 495
			public short wParamH;
		}

		// Token: 0x02000064 RID: 100
		private struct INPUT
		{
			// Token: 0x040001F0 RID: 496
			public int type;

			// Token: 0x040001F1 RID: 497
			public InputHelper.INPUTUNION U;
		}

		// Token: 0x02000065 RID: 101
		private enum INPUTTYPE : uint
		{
			// Token: 0x040001F3 RID: 499
			MOUSE,
			// Token: 0x040001F4 RID: 500
			KEYBOARD,
			// Token: 0x040001F5 RID: 501
			HARDWARE
		}

		// Token: 0x02000066 RID: 102
		[StructLayout(LayoutKind.Explicit)]
		private struct INPUTUNION
		{
			// Token: 0x040001F6 RID: 502
			[FieldOffset(0)]
			public InputHelper.MOUSEINPUT mi;

			// Token: 0x040001F7 RID: 503
			[FieldOffset(0)]
			public InputHelper.KEYBDINPUT ki;

			// Token: 0x040001F8 RID: 504
			[FieldOffset(0)]
			public InputHelper.HARDWAREINPUT hi;
		}

		// Token: 0x02000067 RID: 103
		private struct KEYBDINPUT
		{
			// Token: 0x040001F9 RID: 505
			public ushort wVk;

			// Token: 0x040001FA RID: 506
			public short wScan;

			// Token: 0x040001FB RID: 507
			public uint dwFlags;

			// Token: 0x040001FC RID: 508
			public int time;

			// Token: 0x040001FD RID: 509
			public IntPtr dwExtraInfo;
		}

		// Token: 0x02000068 RID: 104
		[Flags]
		private enum KEYEVENTF : uint
		{
			// Token: 0x040001FF RID: 511
			EXTENDEDKEY = 1U,
			// Token: 0x04000200 RID: 512
			KEYUP = 2U,
			// Token: 0x04000201 RID: 513
			UNICODE = 4U,
			// Token: 0x04000202 RID: 514
			SCANCODE = 8U
		}

		// Token: 0x02000069 RID: 105
		private struct MOUSEINPUT
		{
			// Token: 0x04000203 RID: 515
			public int dx;

			// Token: 0x04000204 RID: 516
			public int dy;

			// Token: 0x04000205 RID: 517
			public int mouseData;

			// Token: 0x04000206 RID: 518
			public int dwFlags;

			// Token: 0x04000207 RID: 519
			public int time;

			// Token: 0x04000208 RID: 520
			public IntPtr dwExtraInfo;
		}
	}
}

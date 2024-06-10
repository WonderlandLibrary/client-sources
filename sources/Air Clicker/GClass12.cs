using System;
using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;
using System.Windows.Forms;
using Microsoft.VisualBasic.CompilerServices;

// Token: 0x020000F5 RID: 245
public sealed class GClass12
{
	// Token: 0x06000C9F RID: 3231 RVA: 0x00050604 File Offset: 0x0004E804
	private GClass12()
	{
	}

	// Token: 0x06000CA0 RID: 3232
	[DllImport("user32.dll")]
	private static extern IntPtr GetKeyboardLayout(uint uint_0);

	// Token: 0x06000CA1 RID: 3233
	[DllImport("user32.dll")]
	private static extern uint MapVirtualKeyEx(uint uint_0, uint uint_1, IntPtr intptr_0);

	// Token: 0x06000CA2 RID: 3234 RVA: 0x0005060C File Offset: 0x0004E80C
	public static void smethod_0(Keys keys_0, bool bool_0 = false)
	{
		if (!bool_0)
		{
			GClass12.smethod_3(keys_0, false);
			GClass12.smethod_3(keys_0, true);
		}
		else
		{
			GClass12.smethod_2(keys_0, false);
			GClass12.smethod_2(keys_0, true);
		}
	}

	// Token: 0x06000CA3 RID: 3235 RVA: 0x00050634 File Offset: 0x0004E834
	private static Keys smethod_1(Keys keys_0)
	{
		Keys keys = keys_0;
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

	// Token: 0x06000CA4 RID: 3236
	[DllImport("user32.dll", SetLastError = true)]
	private static extern uint SendInput(uint uint_0, GClass12.Struct4[] struct4_0, int int_0);

	// Token: 0x06000CA5 RID: 3237 RVA: 0x000506AC File Offset: 0x0004E8AC
	public static void smethod_2(Keys keys_0, bool bool_0)
	{
		keys_0 = GClass12.smethod_1(keys_0);
		GClass12.Struct6 struct6_ = new GClass12.Struct6
		{
			ushort_0 = 0,
			int_0 = 0,
			uint_0 = Conversions.ToUInteger(RuntimeHelpers.GetObjectValue(Operators.OrObject(GClass12.Enum16.SCANCODE, RuntimeHelpers.GetObjectValue(bool_0 ? GClass12.Enum16.KEYUP : 0)))),
			intptr_0 = IntPtr.Zero
		};
		GClass12.Struct5 struct5_ = new GClass12.Struct5
		{
			struct6_0 = struct6_
		};
		GClass12.Struct4 @struct = new GClass12.Struct4
		{
			int_0 = 1,
			struct5_0 = struct5_
		};
		GClass12.SendInput(1U, new GClass12.Struct4[]
		{
			@struct
		}, Marshal.SizeOf(typeof(GClass12.Struct4)));
	}

	// Token: 0x06000CA6 RID: 3238 RVA: 0x0005076C File Offset: 0x0004E96C
	public static void smethod_3(Keys keys_0, bool bool_0)
	{
		keys_0 = GClass12.smethod_1(keys_0);
		GClass12.Struct6 struct6_ = new GClass12.Struct6
		{
			ushort_0 = checked((ushort)keys_0),
			short_0 = 0,
			int_0 = 0,
			uint_0 = Conversions.ToUInteger(RuntimeHelpers.GetObjectValue(bool_0 ? GClass12.Enum16.KEYUP : 0)),
			intptr_0 = IntPtr.Zero
		};
		GClass12.Struct5 struct5_ = new GClass12.Struct5
		{
			struct6_0 = struct6_
		};
		GClass12.Struct4 @struct = new GClass12.Struct4
		{
			int_0 = 1,
			struct5_0 = struct5_
		};
		GClass12.SendInput(1U, new GClass12.Struct4[]
		{
			@struct
		}, Marshal.SizeOf(typeof(GClass12.Struct4)));
	}

	// Token: 0x020000F6 RID: 246
	private struct Struct3
	{
		// Token: 0x0400064D RID: 1613
		public int int_0;

		// Token: 0x0400064E RID: 1614
		public short short_0;

		// Token: 0x0400064F RID: 1615
		public short short_1;
	}

	// Token: 0x020000F7 RID: 247
	private struct Struct4
	{
		// Token: 0x04000650 RID: 1616
		public int int_0;

		// Token: 0x04000651 RID: 1617
		public GClass12.Struct5 struct5_0;
	}

	// Token: 0x020000F8 RID: 248
	private enum Enum15 : uint
	{
		// Token: 0x04000653 RID: 1619
		MOUSE,
		// Token: 0x04000654 RID: 1620
		KEYBOARD,
		// Token: 0x04000655 RID: 1621
		HARDWARE
	}

	// Token: 0x020000F9 RID: 249
	[StructLayout(LayoutKind.Explicit)]
	private struct Struct5
	{
		// Token: 0x04000656 RID: 1622
		[FieldOffset(0)]
		public GClass12.Struct7 struct7_0;

		// Token: 0x04000657 RID: 1623
		[FieldOffset(0)]
		public GClass12.Struct6 struct6_0;

		// Token: 0x04000658 RID: 1624
		[FieldOffset(0)]
		public GClass12.Struct3 struct3_0;
	}

	// Token: 0x020000FA RID: 250
	private struct Struct6
	{
		// Token: 0x04000659 RID: 1625
		public ushort ushort_0;

		// Token: 0x0400065A RID: 1626
		public short short_0;

		// Token: 0x0400065B RID: 1627
		public uint uint_0;

		// Token: 0x0400065C RID: 1628
		public int int_0;

		// Token: 0x0400065D RID: 1629
		public IntPtr intptr_0;
	}

	// Token: 0x020000FB RID: 251
	[Flags]
	private enum Enum16 : uint
	{
		// Token: 0x0400065F RID: 1631
		EXTENDEDKEY = 1U,
		// Token: 0x04000660 RID: 1632
		KEYUP = 2U,
		// Token: 0x04000661 RID: 1633
		UNICODE = 4U,
		// Token: 0x04000662 RID: 1634
		SCANCODE = 8U
	}

	// Token: 0x020000FC RID: 252
	private struct Struct7
	{
		// Token: 0x04000663 RID: 1635
		public int int_0;

		// Token: 0x04000664 RID: 1636
		public int int_1;

		// Token: 0x04000665 RID: 1637
		public int int_2;

		// Token: 0x04000666 RID: 1638
		public int int_3;

		// Token: 0x04000667 RID: 1639
		public int int_4;

		// Token: 0x04000668 RID: 1640
		public IntPtr intptr_0;
	}
}

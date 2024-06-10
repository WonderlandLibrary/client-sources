using System;
using System.Collections.Generic;
using System.Runtime.InteropServices;

namespace ns0;

public sealed class Memory
{
	public IntPtr intptr_0 = IntPtr.Zero;

	public static int int_0;

	public Memory(uint uint_0)
	{
		intptr_0 = Locator.OpenProcess(2035711u, bool_0: false, (int)uint_0);
	}

	private List<GStruct2> method_0()
	{
		List<GStruct2> list = new List<GStruct2>();
		GStruct3 gstruct3_;
		for (long num = 0L; Locator.VirtualQueryEx(intptr_0, num, out gstruct3_, (uint)Marshal.SizeOf(typeof(GStruct3))); num += (long)gstruct3_.ulong_0)
		{
			if (gstruct3_.genum2_0 == GEnum2.const_0 && gstruct3_.genum1_1 == GEnum1.const_2 && num != 0L)
			{
				byte[] array = new byte[gstruct3_.ulong_0];
				Locator.NtReadVirtualMemory(intptr_0, num, array, (uint)array.Length, 0u);
				list.Add(new GStruct2(num, array));
			}
		}
		return list;
	}

	public T method_1<T>(long long_0) where T : struct
	{
		byte[] array = new byte[Marshal.SizeOf(typeof(T))];
		Locator.ReadProcessMemory((int)intptr_0, long_0, array, array.Length, ref int_0);
		return smethod_0<T>(array);
	}

	public List<long> method_2(float float_0)
	{
		List<long> list = new List<long>();
		foreach (GStruct2 item in method_0())
		{
			for (uint num = 0u; num < item.buf.Length; num += 4)
			{
				if (BitConverter.ToSingle(item.buf, (int)num) == float_0)
				{
					list.Add(item._base + num);
				}
			}
		}
		return list;
	}

	public List<long> method_3(double double_0)
	{
		List<long> list = new List<long>();
		foreach (GStruct2 item in method_0())
		{
			for (uint num = 0u; num < item.buf.Length; num += 8)
			{
				if (BitConverter.ToDouble(item.buf, (int)num) == double_0)
				{
					list.Add(item._base + num);
				}
			}
		}
		return list;
	}

	public void method_4(long long_0, float float_0)
	{
		byte[] bytes = BitConverter.GetBytes(float_0);
		Locator.NtWriteVirtualMemory(intptr_0, long_0, bytes, 4u, 0u);
	}

	public void method_5(long long_0, double double_0)
	{
		byte[] bytes = BitConverter.GetBytes(double_0);
		Locator.NtWriteVirtualMemory(intptr_0, long_0, bytes, 8u, 0u);
	}

	private static T smethod_0<T>(byte[] byte_0) where T : struct
	{
		GCHandle gCHandle = GCHandle.Alloc(byte_0, GCHandleType.Pinned);
		try
		{
			return (T)Marshal.PtrToStructure(gCHandle.AddrOfPinnedObject(), typeof(T));
		}
		finally
		{
			gCHandle.Free();
		}
	}
}

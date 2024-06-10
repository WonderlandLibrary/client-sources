using System;
using System.Diagnostics;
using System.Reflection;
using System.Runtime.InteropServices;
using System.Threading;

// Token: 0x02000001 RID: 1
internal class <Module>
{
	// Token: 0x06000001 RID: 1 RVA: 0x00002050 File Offset: 0x00000250
	static <Module>()
	{
		<Module>.smethod_2();
		<Module>.smethod_0();
	}

	// Token: 0x06000002 RID: 2 RVA: 0x00002060 File Offset: 0x00000260
	private static void smethod_0()
	{
		string str = "COR";
		Type typeFromHandle = typeof(Environment);
		MethodInfo method = typeFromHandle.GetMethod("GetEnvironmentVariable", new Type[]
		{
			typeof(string)
		});
		if (method != null && "1".Equals(method.Invoke(null, new object[]
		{
			str + "_ENABLE_PROFILING"
		})))
		{
			Environment.FailFast(null);
		}
		new Thread(new ParameterizedThreadStart(<Module>.smethod_1))
		{
			IsBackground = true
		}.Start(null);
	}

	// Token: 0x06000003 RID: 3 RVA: 0x000020F8 File Offset: 0x000002F8
	private static void smethod_1(object object_0)
	{
		Thread thread = object_0 as Thread;
		if (thread == null)
		{
			thread = new Thread(new ParameterizedThreadStart(<Module>.smethod_1));
			thread.IsBackground = true;
			thread.Start(Thread.CurrentThread);
			Thread.Sleep(500);
		}
		for (;;)
		{
			if (Debugger.IsAttached)
			{
				goto IL_41;
			}
			if (Debugger.IsLogging())
			{
				goto IL_41;
			}
			IL_47:
			if (!thread.IsAlive)
			{
				Environment.FailFast(null);
			}
			Thread.Sleep(1000);
			continue;
			IL_41:
			Environment.FailFast(null);
			goto IL_47;
		}
	}

	// Token: 0x06000004 RID: 4
	[DllImport("kernel32.dll")]
	internal unsafe static extern bool VirtualProtect(byte* pByte_0, int int_0, uint uint_0, ref uint uint_1);

	// Token: 0x06000005 RID: 5 RVA: 0x0000216C File Offset: 0x0000036C
	internal unsafe static void smethod_2()
	{
		Module module = typeof(<Module>).Module;
		byte* ptr = (byte*)((void*)Marshal.GetHINSTANCE(module));
		byte* ptr2 = ptr + 60;
		ptr2 = ptr + *(uint*)ptr2;
		ptr2 += 6;
		ushort num = *(ushort*)ptr2;
		ptr2 += 14;
		ushort num2 = *(ushort*)ptr2;
		ptr2 = ptr2 + 4 + num2;
		byte* ptr3 = stackalloc byte[(UIntPtr)11];
		uint num5;
		if (module.FullyQualifiedName[0] == '<')
		{
			uint num3 = *(uint*)(ptr2 - 16);
			uint num4 = *(uint*)(ptr2 - 120);
			uint[] array = new uint[(int)num];
			uint[] array2 = new uint[(int)num];
			uint[] array3 = new uint[(int)num];
			for (int i = 0; i < (int)num; i++)
			{
				<Module>.VirtualProtect(ptr2, 8, 64U, ref num5);
				Marshal.Copy(new byte[8], 0, (IntPtr)((void*)ptr2), 8);
				array[i] = *(uint*)(ptr2 + 12);
				array2[i] = *(uint*)(ptr2 + 8);
				array3[i] = *(uint*)(ptr2 + 20);
				ptr2 += 40;
			}
			if (num4 != 0U)
			{
				for (int j = 0; j < (int)num; j++)
				{
					if (array[j] <= num4 && num4 < array[j] + array2[j])
					{
						num4 = num4 - array[j] + array3[j];
						break;
					}
				}
				byte* ptr4 = ptr + num4;
				uint num6 = *(uint*)ptr4;
				for (int k = 0; k < (int)num; k++)
				{
					if (array[k] <= num6 && num6 < array[k] + array2[k])
					{
						num6 = num6 - array[k] + array3[k];
						IL_15E:
						byte* ptr5 = ptr + num6;
						uint num7 = *(uint*)(ptr4 + 12);
						for (int l = 0; l < (int)num; l++)
						{
							if (array[l] <= num7 && num7 < array[l] + array2[l])
							{
								num7 = num7 - array[l] + array3[l];
								break;
							}
						}
						uint num8 = *(uint*)ptr5 + 2U;
						for (int m = 0; m < (int)num; m++)
						{
							if (array[m] <= num8 && num8 < array[m] + array2[m])
							{
								num8 = num8 - array[m] + array3[m];
								break;
							}
						}
						<Module>.VirtualProtect(ptr + num7, 11, 64U, ref num5);
						*(int*)ptr3 = 1818522734;
						*(int*)(ptr3 + 4) = 1818504812;
						*(short*)(ptr3 + 8) = 108;
						ptr3[10] = 0;
						for (int n = 0; n < 11; n++)
						{
							(ptr + num7)[n] = ptr3[n];
						}
						<Module>.VirtualProtect(ptr + num8, 11, 64U, ref num5);
						*(int*)ptr3 = 1866691662;
						*(int*)(ptr3 + 4) = 1852404846;
						*(short*)(ptr3 + 8) = 25973;
						ptr3[10] = 0;
						for (int num9 = 0; num9 < 11; num9++)
						{
							(ptr + num8)[num9] = ptr3[num9];
						}
						goto IL_28F;
					}
				}
				goto IL_15E;
			}
			IL_28F:
			for (int num10 = 0; num10 < (int)num; num10++)
			{
				if (array[num10] <= num3 && num3 < array[num10] + array2[num10])
				{
					num3 = num3 - array[num10] + array3[num10];
					break;
				}
			}
			byte* ptr6 = ptr + num3;
			<Module>.VirtualProtect(ptr6, 72, 64U, ref num5);
			uint num11 = *(uint*)(ptr6 + 8);
			for (int num12 = 0; num12 < (int)num; num12++)
			{
				if (array[num12] <= num11 && num11 < array[num12] + array2[num12])
				{
					num11 = num11 - array[num12] + array3[num12];
					break;
				}
			}
			*(int*)ptr6 = 0;
			*(int*)(ptr6 + 4) = 0;
			*(int*)(ptr6 + 8) = 0;
			*(int*)(ptr6 + 12) = 0;
			byte* ptr7 = ptr + num11;
			<Module>.VirtualProtect(ptr7, 4, 64U, ref num5);
			*(int*)ptr7 = 0;
			ptr7 += 12;
			ptr7 += *(uint*)ptr7;
			ptr7 = (ptr7 + 7L & -4L);
			ptr7 += 2;
			ushort num13 = (ushort)(*ptr7);
			ptr7 += 2;
			for (int num14 = 0; num14 < (int)num13; num14++)
			{
				<Module>.VirtualProtect(ptr7, 8, 64U, ref num5);
				ptr7 += 4;
				ptr7 += 4;
				for (int num15 = 0; num15 < 8; num15++)
				{
					<Module>.VirtualProtect(ptr7, 4, 64U, ref num5);
					*ptr7 = 0;
					ptr7++;
					if (*ptr7 == 0)
					{
						ptr7 += 3;
						break;
					}
					*ptr7 = 0;
					ptr7++;
					if (*ptr7 == 0)
					{
						ptr7 += 2;
						break;
					}
					*ptr7 = 0;
					ptr7++;
					if (*ptr7 == 0)
					{
						ptr7++;
						break;
					}
					*ptr7 = 0;
					ptr7++;
				}
			}
			return;
		}
		byte* ptr8 = ptr + *(uint*)(ptr2 - 16);
		if (*(uint*)(ptr2 - 120) != 0U)
		{
			byte* ptr9 = ptr + *(uint*)(ptr2 - 120);
			byte* ptr10 = ptr + *(uint*)ptr9;
			byte* ptr11 = ptr + *(uint*)(ptr9 + 12);
			byte* ptr12 = ptr + *(uint*)ptr10 + 2;
			<Module>.VirtualProtect(ptr11, 11, 64U, ref num5);
			*(int*)ptr3 = 1818522734;
			*(int*)(ptr3 + 4) = 1818504812;
			*(short*)(ptr3 + 8) = 108;
			ptr3[10] = 0;
			for (int num16 = 0; num16 < 11; num16++)
			{
				ptr11[num16] = ptr3[num16];
			}
			<Module>.VirtualProtect(ptr12, 11, 64U, ref num5);
			*(int*)ptr3 = 1866691662;
			*(int*)(ptr3 + 4) = 1852404846;
			*(short*)(ptr3 + 8) = 25973;
			ptr3[10] = 0;
			for (int num17 = 0; num17 < 11; num17++)
			{
				ptr12[num17] = ptr3[num17];
			}
		}
		for (int num18 = 0; num18 < (int)num; num18++)
		{
			<Module>.VirtualProtect(ptr2, 8, 64U, ref num5);
			Marshal.Copy(new byte[8], 0, (IntPtr)((void*)ptr2), 8);
			ptr2 += 40;
		}
		<Module>.VirtualProtect(ptr8, 72, 64U, ref num5);
		byte* ptr13 = ptr + *(uint*)(ptr8 + 8);
		*(int*)ptr8 = 0;
		*(int*)(ptr8 + 4) = 0;
		*(int*)(ptr8 + 8) = 0;
		*(int*)(ptr8 + 12) = 0;
		<Module>.VirtualProtect(ptr13, 4, 64U, ref num5);
		*(int*)ptr13 = 0;
		ptr13 += 12;
		ptr13 += *(uint*)ptr13;
		ptr13 = (ptr13 + 7L & -4L);
		ptr13 += 2;
		ushort num19 = (ushort)(*ptr13);
		ptr13 += 2;
		for (int num20 = 0; num20 < (int)num19; num20++)
		{
			<Module>.VirtualProtect(ptr13, 8, 64U, ref num5);
			ptr13 += 4;
			ptr13 += 4;
			for (int num21 = 0; num21 < 8; num21++)
			{
				<Module>.VirtualProtect(ptr13, 4, 64U, ref num5);
				*ptr13 = 0;
				ptr13++;
				if (*ptr13 == 0)
				{
					ptr13 += 3;
					break;
				}
				*ptr13 = 0;
				ptr13++;
				if (*ptr13 == 0)
				{
					ptr13 += 2;
					break;
				}
				*ptr13 = 0;
				ptr13++;
				if (*ptr13 == 0)
				{
					ptr13++;
					break;
				}
				*ptr13 = 0;
				ptr13++;
			}
		}
	}

	// Token: 0x06000006 RID: 6
	[DllImport("kernel32.dll", EntryPoint = "VirtualProtect")]
	internal static extern bool VirtualProtect_1(IntPtr intptr_0, uint uint_0, uint uint_1, ref uint uint_2);

	// Token: 0x06000007 RID: 7 RVA: 0x000027E0 File Offset: 0x000009E0
	internal unsafe static void smethod_3()
	{
		Module module = typeof(<Module>).Module;
		string fullyQualifiedName = module.FullyQualifiedName;
		bool flag = fullyQualifiedName.Length > 0 && fullyQualifiedName[0] == '<';
		byte* ptr = (byte*)((void*)Marshal.GetHINSTANCE(module));
		byte* ptr2 = ptr + *(uint*)(ptr + 60);
		ushort num = *(ushort*)(ptr2 + 6);
		ushort num2 = *(ushort*)(ptr2 + 20);
		uint* ptr3 = null;
		uint num3 = 0U;
		uint* ptr4 = (uint*)(ptr2 + 24 + num2);
		uint num4 = 503922076U;
		uint num5 = 181710954U;
		uint num6 = 3610217174U;
		uint num7 = 155143027U;
		for (int i = 0; i < (int)num; i++)
		{
			uint num8 = *(ptr4++) * *(ptr4++);
			if (num8 == 3426735784U)
			{
				ptr3 = (uint*)(ptr + (flag ? ptr4[3] : ptr4[1]) / 4U);
				num3 = (flag ? ptr4[2] : (*ptr4)) >> 2;
			}
			else if (num8 != 0U)
			{
				uint* ptr5 = (uint*)(ptr + (flag ? ptr4[3] : ptr4[1]) / 4U);
				uint num9 = ptr4[2] >> 2;
				for (uint num10 = 0U; num10 < num9; num10 += 1U)
				{
					uint num11 = (num4 ^ *(ptr5++)) + num5 + num6 * num7;
					num4 = num5;
					num5 = num7;
					num7 = num11;
				}
			}
			ptr4 += 8;
		}
		uint[] array = new uint[16];
		uint[] array2 = new uint[16];
		for (int j = 0; j < 16; j++)
		{
			array[j] = num7;
			array2[j] = num5;
			num4 = (num5 >> 5 | num5 << 27);
			num5 = (num6 >> 3 | num6 << 29);
			num6 = (num7 >> 7 | num7 << 25);
			num7 = (num4 >> 11 | num4 << 21);
		}
		array[0] = (array[0] ^ array2[0]);
		array[1] = array[1] * array2[1];
		array[2] = array[2] + array2[2];
		array[3] = (array[3] ^ array2[3]);
		array[4] = array[4] * array2[4];
		array[5] = array[5] + array2[5];
		array[6] = (array[6] ^ array2[6]);
		array[7] = array[7] * array2[7];
		array[8] = array[8] + array2[8];
		array[9] = (array[9] ^ array2[9]);
		array[10] = array[10] * array2[10];
		array[11] = array[11] + array2[11];
		array[12] = (array[12] ^ array2[12]);
		array[13] = array[13] * array2[13];
		array[14] = array[14] + array2[14];
		array[15] = (array[15] ^ array2[15]);
		uint num12 = 64U;
		<Module>.VirtualProtect_1((IntPtr)((void*)ptr3), num3 << 2, 64U, ref num12);
		if (num12 == 64U)
		{
			return;
		}
		uint num13 = 0U;
		for (uint num14 = 0U; num14 < num3; num14 += 1U)
		{
			*ptr3 ^= array[(int)((UIntPtr)(num13 & 15U))];
			array[(int)((UIntPtr)(num13 & 15U))] = (array[(int)((UIntPtr)(num13 & 15U))] ^ *(ptr3++)) + 1035675673U;
			num13 += 1U;
		}
	}
}

using System;
using System.Collections.Generic;
using System.Linq;

namespace AnyDesk
{
	// Token: 0x0200000B RID: 11
	public class Allocator
	{
		// Token: 0x0600002D RID: 45 RVA: 0x00003090 File Offset: 0x00001290
		public IntPtr AlloacNewPage(IntPtr size)
		{
			IntPtr intPtr = WinAPI.VirtualAllocEx(Memory.pHandle, IntPtr.Zero, (IntPtr)4096, 12288U, 64U);
			this.AllocatedSize.Add(intPtr, size);
			return intPtr;
		}

		// Token: 0x0600002E RID: 46 RVA: 0x000030D4 File Offset: 0x000012D4
		public void Free()
		{
			foreach (KeyValuePair<IntPtr, IntPtr> keyValuePair in this.AllocatedSize)
			{
				WinAPI.VirtualFreeEx(Memory.pHandle, keyValuePair.Key, 4096, 12288U);
			}
		}

		// Token: 0x0600002F RID: 47 RVA: 0x00003144 File Offset: 0x00001344
		public IntPtr Alloc(int size)
		{
			for (int i = 0; i < this.AllocatedSize.Count; i++)
			{
				IntPtr key = this.AllocatedSize.ElementAt(i).Key;
				int num = (int)this.AllocatedSize[key] + size;
				bool flag = num < 4096;
				bool flag2 = flag;
				bool flag3 = flag2;
				if (flag3)
				{
					IntPtr result = IntPtr.Add(key, (int)this.AllocatedSize[key]);
					this.AllocatedSize[key] = new IntPtr(num);
					return result;
				}
			}
			return this.AlloacNewPage(new IntPtr(size));
		}

		// Token: 0x0400005A RID: 90
		public Dictionary<IntPtr, IntPtr> AllocatedSize = new Dictionary<IntPtr, IntPtr>();
	}
}

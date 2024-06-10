using System;
using System.Collections.Generic;
using System.Linq;

namespace New_CandyClient.Memory
{
	// Token: 0x02000042 RID: 66
	public class Allocator
	{
		// Token: 0x060001A5 RID: 421 RVA: 0x00013AAC File Offset: 0x00011EAC
		public IntPtr AlloacNewPage(IntPtr size)
		{
			IntPtr intPtr = WinAPI.VirtualAllocEx(Memory.pHandle, IntPtr.Zero, (IntPtr)4096, 12288U, 64U);
			this.AllocatedSize.Add(intPtr, size);
			return intPtr;
		}

		// Token: 0x060001A6 RID: 422 RVA: 0x00013AE8 File Offset: 0x00011EE8
		public void Free()
		{
			foreach (KeyValuePair<IntPtr, IntPtr> keyValuePair in this.AllocatedSize)
			{
				WinAPI.VirtualFreeEx(Memory.pHandle, keyValuePair.Key, 4096, 12288U);
			}
		}

		// Token: 0x060001A7 RID: 423 RVA: 0x00013B50 File Offset: 0x00011F50
		public IntPtr Alloc(int size)
		{
			for (int i = 0; i < this.AllocatedSize.Count; i++)
			{
				IntPtr key = this.AllocatedSize.ElementAt(i).Key;
				int num = (int)this.AllocatedSize[key] + size;
				if (num < 4096)
				{
					IntPtr result = IntPtr.Add(key, (int)this.AllocatedSize[key]);
					this.AllocatedSize[key] = new IntPtr(num);
					return result;
				}
			}
			return this.AlloacNewPage(new IntPtr(size));
		}

		// Token: 0x040003E0 RID: 992
		public Dictionary<IntPtr, IntPtr> AllocatedSize = new Dictionary<IntPtr, IntPtr>();
	}
}

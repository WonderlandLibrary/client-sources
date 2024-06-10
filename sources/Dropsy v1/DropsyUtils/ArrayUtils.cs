using System;

namespace DropsyUtils
{
	// Token: 0x02000020 RID: 32
	public static class ArrayUtils
	{
		// Token: 0x060000FD RID: 253 RVA: 0x0001183C File Offset: 0x0000FC3C
		public static bool arrayContain<T>(T[] myArray, T value)
		{
			bool result = false;
			if (myArray.Length != 0)
			{
				foreach (T t in myArray)
				{
					if (t.Equals(value))
					{
						result = true;
						break;
					}
				}
			}
			return result;
		}
	}
}

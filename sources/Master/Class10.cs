using System;
using System.Collections.Generic;
using System.Globalization;
using System.IO;
using System.IO.Compression;
using System.Reflection;
using System.Runtime.CompilerServices;
using System.Threading;

namespace Client
{
	// Token: 0x02000018 RID: 24
	[CompilerGenerated]
	internal static class Class10
	{
		// Token: 0x060000EB RID: 235 RVA: 0x0001FBEC File Offset: 0x0001DDEC
		private static string smethod_0(CultureInfo cultureInfo_0)
		{
			int num = 0;
			do
			{
				if (num == 2)
				{
					if (cultureInfo_0 != null)
					{
						break;
					}
					num = 3;
				}
				if (num == 3)
				{
					goto IL_82;
				}
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 0)
				{
					num = 1;
				}
			}
			while (num != 4);
			return cultureInfo_0.Name;
			IL_82:
			return "";
		}

		// Token: 0x060000EC RID: 236 RVA: 0x0001FC80 File Offset: 0x0001DE80
		private static Assembly smethod_1(AssemblyName assemblyName_0)
		{
			int num = 0;
			for (;;)
			{
				if (num != 7)
				{
					goto IL_0E;
				}
				AssemblyName name;
				if (string.Equals(name.Name, assemblyName_0.Name, StringComparison.InvariantCultureIgnoreCase))
				{
					num = 8;
					goto IL_0E;
				}
				goto IL_102;
				IL_152:
				Assembly assembly;
				if (num == 6)
				{
					name = assembly.GetName();
					num = 7;
				}
				int num2;
				if (num == 3)
				{
					num2 = 0;
					num = 4;
				}
				if (num == 9)
				{
					return assembly;
				}
				Assembly[] assemblies;
				if (num == 2)
				{
					assemblies = AppDomain.CurrentDomain.GetAssemblies();
					num = 3;
				}
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 4)
				{
					goto IL_110;
				}
				if (num == 8)
				{
					if (!string.Equals(Class10.smethod_0(name.CultureInfo), Class10.smethod_0(assemblyName_0.CultureInfo), StringComparison.InvariantCultureIgnoreCase))
					{
						goto IL_102;
					}
					num = 9;
				}
				if (num == 0)
				{
					num = 1;
				}
				if (num == 10)
				{
					goto IL_102;
				}
				continue;
				IL_0E:
				if (num != 5)
				{
					goto IL_152;
				}
				IL_11F:
				assembly = assemblies[num2];
				num = 6;
				goto IL_152;
				IL_110:
				if (num2 < assemblies.Length)
				{
					goto IL_11F;
				}
				break;
				IL_102:
				num2++;
				goto IL_110;
			}
			return null;
		}

		// Token: 0x060000ED RID: 237 RVA: 0x0001FE38 File Offset: 0x0001E038
		private static void smethod_2(Stream stream_0, Stream stream_1)
		{
			int num = 0;
			for (;;)
			{
				if (num == 3)
				{
					goto IL_1B;
				}
				if (num == 5)
				{
					goto IL_1B;
				}
				goto IL_43;
				IL_AE:
				if (num == 0)
				{
					num = 1;
				}
				if (num != 6)
				{
					continue;
				}
				break;
				IL_43:
				byte[] array;
				if (num == 2)
				{
					array = new byte[81920];
					num = 3;
				}
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num != 4)
				{
					goto IL_AE;
				}
				IL_8F:
				int count;
				stream_1.Write(array, 0, count);
				num = 5;
				goto IL_AE;
				IL_1B:
				if ((count = stream_0.Read(array, 0, array.Length)) == 0)
				{
					num = 6;
					goto IL_43;
				}
				goto IL_8F;
			}
		}

		// Token: 0x060000EE RID: 238 RVA: 0x0001FF2C File Offset: 0x0001E12C
		private static Stream smethod_3(string string_0)
		{
			int num = 0;
			Assembly executingAssembly;
			do
			{
				if (num == 2)
				{
					executingAssembly = Assembly.GetExecutingAssembly();
					num = 3;
				}
				if (num == 3)
				{
					if (!string_0.EndsWith(".compressed"))
					{
						goto IL_DA;
					}
					num = 4;
				}
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 5)
				{
					goto IL_E8;
				}
				if (num == 4)
				{
					Stream manifestResourceStream = executingAssembly.GetManifestResourceStream(string_0);
					num = 5;
				}
				if (num == 0)
				{
					num = 1;
				}
			}
			while (num != 6);
			Stream result;
			return result;
			IL_DA:
			return executingAssembly.GetManifestResourceStream(string_0);
			try
			{
				IL_E8:
				Stream manifestResourceStream;
				using (DeflateStream deflateStream = new DeflateStream(manifestResourceStream, CompressionMode.Decompress))
				{
					MemoryStream memoryStream = new MemoryStream();
					Class10.smethod_2(deflateStream, memoryStream);
					memoryStream.Position = 0L;
					result = memoryStream;
				}
			}
			finally
			{
				Stream manifestResourceStream;
				if (manifestResourceStream != null)
				{
					((IDisposable)manifestResourceStream).Dispose();
				}
			}
			return result;
		}

		// Token: 0x060000EF RID: 239 RVA: 0x000200A8 File Offset: 0x0001E2A8
		private static Stream smethod_4(Dictionary<string, string> dictionary_3, string string_0)
		{
			int num = 0;
			string string_;
			while (num != 3)
			{
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 2)
				{
					if (!dictionary_3.TryGetValue(string_0, out string_))
					{
						goto IL_89;
					}
					num = 3;
				}
				if (num == 0)
				{
					num = 1;
				}
				if (num != 4)
				{
					continue;
				}
				IL_89:
				return null;
			}
			return Class10.smethod_3(string_);
		}

		// Token: 0x060000F0 RID: 240 RVA: 0x00020140 File Offset: 0x0001E340
		private static byte[] smethod_5(Stream stream_0)
		{
			int num = 0;
			byte[] array;
			do
			{
				if (num == 2)
				{
					array = new byte[stream_0.Length];
					num = 3;
				}
				if (num == 3)
				{
					stream_0.Read(array, 0, array.Length);
					num = 4;
				}
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 0)
				{
					num = 1;
				}
			}
			while (num != 4);
			return array;
		}

		// Token: 0x060000F1 RID: 241 RVA: 0x00020204 File Offset: 0x0001E404
		private static Assembly smethod_6(Dictionary<string, string> dictionary_3, Dictionary<string, string> dictionary_4, AssemblyName assemblyName_0)
		{
			int num = 0;
			string text;
			for (;;)
			{
				if (num == 2)
				{
					text = assemblyName_0.Name.ToLowerInvariant();
					num = 3;
				}
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num != 7)
				{
					if (num == 5)
					{
						text = assemblyName_0.CultureInfo.Name + "." + text;
						num = 6;
					}
					if (num == 6)
					{
						goto IL_96;
					}
					IL_D2:
					if (num == 4)
					{
						if (string.IsNullOrEmpty(assemblyName_0.CultureInfo.Name))
						{
							goto IL_96;
						}
						num = 5;
					}
					if (num == 3)
					{
						if (assemblyName_0.CultureInfo == null)
						{
							goto IL_96;
						}
						num = 4;
					}
					if (num == 0)
					{
						num = 1;
					}
					if (num == 8)
					{
						break;
					}
					continue;
					IL_96:
					Stream stream = Class10.smethod_4(dictionary_3, text);
					num = 7;
					goto IL_D2;
				}
				goto IL_144;
			}
			Assembly result;
			return result;
			byte[] rawAssembly;
			try
			{
				IL_144:
				Stream stream;
				if (stream == null)
				{
					return null;
				}
				rawAssembly = Class10.smethod_5(stream);
			}
			finally
			{
				Stream stream;
				if (stream != null)
				{
					((IDisposable)stream).Dispose();
				}
			}
			using (Stream stream2 = Class10.smethod_4(dictionary_4, text))
			{
				if (stream2 != null)
				{
					byte[] rawSymbolStore = Class10.smethod_5(stream2);
					return Assembly.Load(rawAssembly, rawSymbolStore);
				}
			}
			return Assembly.Load(rawAssembly);
		}

		// Token: 0x060000F2 RID: 242 RVA: 0x000203F4 File Offset: 0x0001E5F4
		public static Assembly smethod_7(object object_1, ResolveEventArgs resolveEventArgs_0)
		{
			int num = 0;
			while (num != 5)
			{
				object obj;
				if (num == 2)
				{
					obj = Class10.object_0;
					num = 3;
				}
				bool flag;
				if (num == 3)
				{
					flag = false;
					num = 4;
				}
				if (num != 4)
				{
					if (num == 1)
					{
						<Module>.Class0.smethod_0();
						num = 2;
					}
					if (num == 0)
					{
						num = 1;
					}
					if (num != 6)
					{
						continue;
					}
				}
				else
				{
					try
					{
						Monitor.Enter(obj, ref flag);
						if (Class10.dictionary_0.ContainsKey(resolveEventArgs_0.Name))
						{
							return null;
						}
					}
					finally
					{
						if (flag)
						{
							Monitor.Exit(obj);
						}
					}
					AssemblyName assemblyName = new AssemblyName(resolveEventArgs_0.Name);
					Assembly assembly = Class10.smethod_1(assemblyName);
					if (assembly != null)
					{
						return assembly;
					}
					IL_11A:
					assembly = Class10.smethod_6(Class10.dictionary_1, Class10.dictionary_2, assemblyName);
					if (assembly == null)
					{
						obj = Class10.object_0;
						lock (obj)
						{
							Class10.dictionary_0[resolveEventArgs_0.Name] = true;
						}
						if ((assemblyName.Flags & AssemblyNameFlags.Retargetable) != AssemblyNameFlags.None)
						{
							assembly = Assembly.Load(assemblyName);
						}
					}
					return assembly;
				}
				Assembly result;
				return result;
			}
			goto IL_11A;
		}

		// Token: 0x060000F3 RID: 243 RVA: 0x000205CC File Offset: 0x0001E7CC
		// Note: this type is marked as 'beforefieldinit'.
		static Class10()
		{
			<Module>.Class0.smethod_0();
			Class10.object_0 = new object();
			Class10.dictionary_0 = new Dictionary<string, bool>();
			Class10.dictionary_1 = new Dictionary<string, string>();
			Class10.dictionary_2 = new Dictionary<string, string>();
			Class10.dictionary_1.Add("bunifu_ui_v1.5.3", "costura.bunifu_ui_v1.5.3.dll.compressed");
			Class10.dictionary_1.Add("costura", "costura.costura.dll.compressed");
			Class10.dictionary_1.Add("dotnetaobscanmemory", "costura.dotnetaobscanmemory.dll.compressed");
			Class10.dictionary_1.Add("vamemory", "costura.vamemory.dll.compressed");
		}

		// Token: 0x060000F4 RID: 244 RVA: 0x00020658 File Offset: 0x0001E858
		public static void smethod_8()
		{
			int num = 0;
			for (;;)
			{
				if (num != 2)
				{
					goto IL_0E;
				}
				if (Interlocked.Exchange(ref Class10.int_0, 1) == 1)
				{
					num = 3;
					goto IL_0E;
				}
				goto IL_36;
				IL_55:
				if (num == 3)
				{
					return;
				}
				if (num == 0)
				{
					num = 1;
				}
				if (num == 5)
				{
					break;
				}
				continue;
				IL_0E:
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num != 4)
				{
					goto IL_55;
				}
				IL_36:
				AppDomain.CurrentDomain.AssemblyResolve += Class10.smethod_7;
				num = 5;
				goto IL_55;
			}
		}

		// Token: 0x040000A8 RID: 168
		private static object object_0;

		// Token: 0x040000A9 RID: 169
		private static Dictionary<string, bool> dictionary_0;

		// Token: 0x040000AA RID: 170
		private static Dictionary<string, string> dictionary_1;

		// Token: 0x040000AB RID: 171
		private static Dictionary<string, string> dictionary_2;

		// Token: 0x040000AC RID: 172
		private static int int_0;
	}
}

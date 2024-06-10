using System;
using System.CodeDom.Compiler;
using System.Collections;
using System.ComponentModel;
using System.ComponentModel.Design;
using System.Diagnostics;
using System.Reflection;
using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;
using System.Windows.Forms;
using Microsoft.VisualBasic;
using Microsoft.VisualBasic.ApplicationServices;
using Microsoft.VisualBasic.CompilerServices;

// Token: 0x02000004 RID: 4
[GeneratedCode("MyTemplate", "11.0.0.0")]
[StandardModule]
[HideModuleName]
internal sealed class Class1
{
	// Token: 0x17000001 RID: 1
	// (get) Token: 0x0600000D RID: 13 RVA: 0x00002B98 File Offset: 0x00000D98
	[HelpKeyword("My.Computer")]
	internal static Class0 Class0_0
	{
		[DebuggerHidden]
		get
		{
			return Class1.class4_0.Prop_0;
		}
	}

	// Token: 0x17000002 RID: 2
	// (get) Token: 0x0600000E RID: 14 RVA: 0x00002BB4 File Offset: 0x00000DB4
	[HelpKeyword("My.Application")]
	internal static Form0 Form0_0
	{
		[DebuggerHidden]
		get
		{
			return Class1.class4_1.Prop_0;
		}
	}

	// Token: 0x17000003 RID: 3
	// (get) Token: 0x0600000F RID: 15 RVA: 0x00002BD0 File Offset: 0x00000DD0
	[HelpKeyword("My.User")]
	internal static User User_0
	{
		[DebuggerHidden]
		get
		{
			return Class1.class4_2.Prop_0;
		}
	}

	// Token: 0x17000004 RID: 4
	// (get) Token: 0x06000010 RID: 16 RVA: 0x00002BEC File Offset: 0x00000DEC
	[HelpKeyword("My.Forms")]
	internal static Class1.Class2 Class2_0
	{
		[DebuggerHidden]
		get
		{
			return Class1.class4_3.Prop_0;
		}
	}

	// Token: 0x17000005 RID: 5
	// (get) Token: 0x06000011 RID: 17 RVA: 0x00002C08 File Offset: 0x00000E08
	[HelpKeyword("My.WebServices")]
	internal static Class1.Class3 Class3_0
	{
		[DebuggerHidden]
		get
		{
			return Class1.class4_4.Prop_0;
		}
	}

	// Token: 0x04000001 RID: 1
	private static readonly Class1.Class4<Class0> class4_0 = new Class1.Class4<Class0>();

	// Token: 0x04000002 RID: 2
	private static readonly Class1.Class4<Form0> class4_1 = new Class1.Class4<Form0>();

	// Token: 0x04000003 RID: 3
	private static readonly Class1.Class4<User> class4_2 = new Class1.Class4<User>();

	// Token: 0x04000004 RID: 4
	private static Class1.Class4<Class1.Class2> class4_3 = new Class1.Class4<Class1.Class2>();

	// Token: 0x04000005 RID: 5
	private static readonly Class1.Class4<Class1.Class3> class4_4 = new Class1.Class4<Class1.Class3>();

	// Token: 0x02000005 RID: 5
	[MyGroupCollection("System.Windows.Forms.Form", "Create__Instance__", "Dispose__Instance__", "My.MyProject.Forms")]
	[EditorBrowsable(EditorBrowsableState.Never)]
	internal sealed class Class2
	{
		// Token: 0x06000012 RID: 18 RVA: 0x00002C24 File Offset: 0x00000E24
		[DebuggerHidden]
		private static T smethod_0<T>(T gparam_0) where T : Form, new()
		{
			if (gparam_0 == null || gparam_0.IsDisposed)
			{
				if (Class1.Class2.hashtable_0 != null)
				{
					if (Class1.Class2.smethod_2(Class1.Class2.hashtable_0, Class1.Class2.smethod_1(typeof(T).TypeHandle)))
					{
						throw Class1.Class2.smethod_4(Class1.Class2.smethod_3("WinForms_RecursiveFormCreate", new string[0]));
					}
				}
				else
				{
					Class1.Class2.hashtable_0 = Class1.Class2.smethod_5();
				}
				Class1.Class2.smethod_6(Class1.Class2.hashtable_0, Class1.Class2.smethod_1(typeof(T).TypeHandle), null);
				TargetInvocationException exception_;
				object obj;
				TargetInvocationException ex;
				bool flag;
				try
				{
					return Activator.CreateInstance<T>();
				}
				catch when (delegate
				{
					// Failed to create a 'catch-when' expression
					ex = (obj as TargetInvocationException);
					if (ex == null)
					{
						flag = false;
					}
					else
					{
						Class1.Class2.smethod_7(ex);
						exception_ = ex;
						flag = ((Class1.Class2.smethod_8(exception_) != null) > false);
					}
					endfilter(flag);
				})
				{
					string string_ = Class1.Class2.smethod_3("WinForms_SeeInnerException", new string[]
					{
						Class1.Class2.smethod_9(Class1.Class2.smethod_8(exception_))
					});
					throw Class1.Class2.smethod_10(string_, Class1.Class2.smethod_8(exception_));
				}
				finally
				{
					Class1.Class2.smethod_11(Class1.Class2.hashtable_0, Class1.Class2.smethod_1(typeof(T).TypeHandle));
				}
			}
			return gparam_0;
		}

		// Token: 0x06000013 RID: 19 RVA: 0x00002D30 File Offset: 0x00000F30
		[DebuggerHidden]
		private void method_0<T>(ref T gparam_0) where T : Form
		{
			gparam_0.Dispose();
			gparam_0 = default(T);
		}

		// Token: 0x06000014 RID: 20 RVA: 0x00002D48 File Offset: 0x00000F48
		[DebuggerHidden]
		[EditorBrowsable(EditorBrowsableState.Never)]
		public Class2()
		{
		}

		// Token: 0x06000015 RID: 21 RVA: 0x00002D50 File Offset: 0x00000F50
		[EditorBrowsable(EditorBrowsableState.Never)]
		public bool Equals(object obj)
		{
			return this.method_2(Class1.Class2.smethod_12(obj));
		}

		// Token: 0x06000016 RID: 22 RVA: 0x00002D60 File Offset: 0x00000F60
		[EditorBrowsable(EditorBrowsableState.Never)]
		public int GetHashCode()
		{
			return this.method_3();
		}

		// Token: 0x06000017 RID: 23 RVA: 0x00002D78 File Offset: 0x00000F78
		[EditorBrowsable(EditorBrowsableState.Never)]
		internal Type method_1()
		{
			return Class1.Class2.smethod_1(typeof(Class1.Class2).TypeHandle);
		}

		// Token: 0x06000018 RID: 24 RVA: 0x00002D94 File Offset: 0x00000F94
		[EditorBrowsable(EditorBrowsableState.Never)]
		public string ToString()
		{
			return this.method_4();
		}

		// Token: 0x17000006 RID: 6
		// (get) Token: 0x06000019 RID: 25 RVA: 0x00002DAC File Offset: 0x00000FAC
		// (set) Token: 0x0600001F RID: 31 RVA: 0x00002E54 File Offset: 0x00001054
		public arraylist Arraylist_0
		{
			[DebuggerHidden]
			get
			{
				this.arraylist_0 = Class1.Class2.smethod_0<arraylist>(this.arraylist_0);
				return this.arraylist_0;
			}
			[DebuggerHidden]
			set
			{
				if (value != this.arraylist_0)
				{
					if (value != null)
					{
						throw Class1.Class2.smethod_13("Property can only be set to Nothing");
					}
					this.method_0<arraylist>(ref this.arraylist_0);
				}
			}
		}

		// Token: 0x17000007 RID: 7
		// (get) Token: 0x0600001A RID: 26 RVA: 0x00002DC8 File Offset: 0x00000FC8
		// (set) Token: 0x06000020 RID: 32 RVA: 0x00002E80 File Offset: 0x00001080
		public Cpsdisplay Cpsdisplay_0
		{
			[DebuggerHidden]
			get
			{
				this.cpsdisplay_0 = Class1.Class2.smethod_0<Cpsdisplay>(this.cpsdisplay_0);
				return this.cpsdisplay_0;
			}
			[DebuggerHidden]
			set
			{
				if (value != this.cpsdisplay_0)
				{
					if (value != null)
					{
						throw Class1.Class2.smethod_13("Property can only be set to Nothing");
					}
					this.method_0<Cpsdisplay>(ref this.cpsdisplay_0);
				}
			}
		}

		// Token: 0x17000008 RID: 8
		// (get) Token: 0x0600001B RID: 27 RVA: 0x00002DE4 File Offset: 0x00000FE4
		// (set) Token: 0x06000021 RID: 33 RVA: 0x00002EAC File Offset: 0x000010AC
		public hitmarker Hitmarker_0
		{
			[DebuggerHidden]
			get
			{
				this.hitmarker_0 = Class1.Class2.smethod_0<hitmarker>(this.hitmarker_0);
				return this.hitmarker_0;
			}
			[DebuggerHidden]
			set
			{
				if (value != this.hitmarker_0)
				{
					if (value != null)
					{
						throw Class1.Class2.smethod_13("Property can only be set to Nothing");
					}
					this.method_0<hitmarker>(ref this.hitmarker_0);
				}
			}
		}

		// Token: 0x17000009 RID: 9
		// (get) Token: 0x0600001C RID: 28 RVA: 0x00002E00 File Offset: 0x00001000
		// (set) Token: 0x06000022 RID: 34 RVA: 0x00002ED8 File Offset: 0x000010D8
		public Loader Loader_0
		{
			[DebuggerHidden]
			get
			{
				this.loader_0 = Class1.Class2.smethod_0<Loader>(this.loader_0);
				return this.loader_0;
			}
			[DebuggerHidden]
			set
			{
				if (value != this.loader_0)
				{
					if (value != null)
					{
						throw Class1.Class2.smethod_13("Property can only be set to Nothing");
					}
					this.method_0<Loader>(ref this.loader_0);
				}
			}
		}

		// Token: 0x1700000A RID: 10
		// (get) Token: 0x0600001D RID: 29 RVA: 0x00002E1C File Offset: 0x0000101C
		// (set) Token: 0x06000023 RID: 35 RVA: 0x00002F04 File Offset: 0x00001104
		public Overlay Overlay_0
		{
			[DebuggerHidden]
			get
			{
				this.overlay_0 = Class1.Class2.smethod_0<Overlay>(this.overlay_0);
				return this.overlay_0;
			}
			[DebuggerHidden]
			set
			{
				if (value != this.overlay_0)
				{
					if (value != null)
					{
						throw Class1.Class2.smethod_13("Property can only be set to Nothing");
					}
					this.method_0<Overlay>(ref this.overlay_0);
				}
			}
		}

		// Token: 0x1700000B RID: 11
		// (get) Token: 0x0600001E RID: 30 RVA: 0x00002E38 File Offset: 0x00001038
		// (set) Token: 0x06000024 RID: 36 RVA: 0x00002F30 File Offset: 0x00001130
		public skeet Skeet_0
		{
			[DebuggerHidden]
			get
			{
				this.skeet_0 = Class1.Class2.smethod_0<skeet>(this.skeet_0);
				return this.skeet_0;
			}
			[DebuggerHidden]
			set
			{
				if (value != this.skeet_0)
				{
					if (value != null)
					{
						throw Class1.Class2.smethod_13("Property can only be set to Nothing");
					}
					this.method_0<skeet>(ref this.skeet_0);
				}
			}
		}

		// Token: 0x06000025 RID: 37 RVA: 0x00002F5C File Offset: 0x0000115C
		static Type smethod_1(RuntimeTypeHandle runtimeTypeHandle_0)
		{
			return Type.GetTypeFromHandle(runtimeTypeHandle_0);
		}

		// Token: 0x06000026 RID: 38 RVA: 0x00002F64 File Offset: 0x00001164
		static bool smethod_2(Hashtable hashtable_1, object object_0)
		{
			return hashtable_1.ContainsKey(object_0);
		}

		// Token: 0x06000027 RID: 39 RVA: 0x00002F70 File Offset: 0x00001170
		static string smethod_3(string string_0, string[] string_1)
		{
			return Utils.GetResourceString(string_0, string_1);
		}

		// Token: 0x06000028 RID: 40 RVA: 0x00002F7C File Offset: 0x0000117C
		static InvalidOperationException smethod_4(string string_0)
		{
			return new InvalidOperationException(string_0);
		}

		// Token: 0x06000029 RID: 41 RVA: 0x00002F84 File Offset: 0x00001184
		static Hashtable smethod_5()
		{
			return new Hashtable();
		}

		// Token: 0x0600002A RID: 42 RVA: 0x00002F8C File Offset: 0x0000118C
		static void smethod_6(Hashtable hashtable_1, object object_0, object object_1)
		{
			hashtable_1.Add(object_0, object_1);
		}

		// Token: 0x0600002B RID: 43 RVA: 0x00002F98 File Offset: 0x00001198
		static void smethod_7(Exception exception_0)
		{
			ProjectData.SetProjectError(exception_0);
		}

		// Token: 0x0600002C RID: 44 RVA: 0x00002FA0 File Offset: 0x000011A0
		static Exception smethod_8(Exception exception_0)
		{
			return exception_0.InnerException;
		}

		// Token: 0x0600002D RID: 45 RVA: 0x00002FA8 File Offset: 0x000011A8
		static string smethod_9(Exception exception_0)
		{
			return exception_0.Message;
		}

		// Token: 0x0600002E RID: 46 RVA: 0x00002FB0 File Offset: 0x000011B0
		static InvalidOperationException smethod_10(string string_0, Exception exception_0)
		{
			return new InvalidOperationException(string_0, exception_0);
		}

		// Token: 0x0600002F RID: 47 RVA: 0x00002FBC File Offset: 0x000011BC
		static void smethod_11(Hashtable hashtable_1, object object_0)
		{
			hashtable_1.Remove(object_0);
		}

		// Token: 0x06000030 RID: 48 RVA: 0x00002FC8 File Offset: 0x000011C8
		static object smethod_12(object object_0)
		{
			return RuntimeHelpers.GetObjectValue(object_0);
		}

		// Token: 0x06000031 RID: 49 RVA: 0x00002FD0 File Offset: 0x000011D0
		bool method_2(object object_0)
		{
			return base.Equals(object_0);
		}

		// Token: 0x06000032 RID: 50 RVA: 0x00002FDC File Offset: 0x000011DC
		int method_3()
		{
			return base.GetHashCode();
		}

		// Token: 0x06000033 RID: 51 RVA: 0x00002FE4 File Offset: 0x000011E4
		string method_4()
		{
			return base.ToString();
		}

		// Token: 0x06000034 RID: 52 RVA: 0x00002FEC File Offset: 0x000011EC
		static ArgumentException smethod_13(string string_0)
		{
			return new ArgumentException(string_0);
		}

		// Token: 0x04000006 RID: 6
		[ThreadStatic]
		private static Hashtable hashtable_0;

		// Token: 0x04000007 RID: 7
		[EditorBrowsable(EditorBrowsableState.Never)]
		public arraylist arraylist_0;

		// Token: 0x04000008 RID: 8
		[EditorBrowsable(EditorBrowsableState.Never)]
		public Cpsdisplay cpsdisplay_0;

		// Token: 0x04000009 RID: 9
		[EditorBrowsable(EditorBrowsableState.Never)]
		public hitmarker hitmarker_0;

		// Token: 0x0400000A RID: 10
		[EditorBrowsable(EditorBrowsableState.Never)]
		public Loader loader_0;

		// Token: 0x0400000B RID: 11
		[EditorBrowsable(EditorBrowsableState.Never)]
		public Overlay overlay_0;

		// Token: 0x0400000C RID: 12
		[EditorBrowsable(EditorBrowsableState.Never)]
		public skeet skeet_0;
	}

	// Token: 0x02000006 RID: 6
	[EditorBrowsable(EditorBrowsableState.Never)]
	[MyGroupCollection("System.Web.Services.Protocols.SoapHttpClientProtocol", "Create__Instance__", "Dispose__Instance__", "")]
	internal sealed class Class3
	{
		// Token: 0x06000035 RID: 53 RVA: 0x00002FF4 File Offset: 0x000011F4
		[DebuggerHidden]
		[EditorBrowsable(EditorBrowsableState.Never)]
		public bool Equals(object obj)
		{
			return this.method_2(Class1.Class3.smethod_1(obj));
		}

		// Token: 0x06000036 RID: 54 RVA: 0x00003004 File Offset: 0x00001204
		[DebuggerHidden]
		[EditorBrowsable(EditorBrowsableState.Never)]
		public int GetHashCode()
		{
			return this.method_3();
		}

		// Token: 0x06000037 RID: 55 RVA: 0x0000301C File Offset: 0x0000121C
		[DebuggerHidden]
		[EditorBrowsable(EditorBrowsableState.Never)]
		internal Type method_0()
		{
			return Class1.Class3.smethod_2(typeof(Class1.Class3).TypeHandle);
		}

		// Token: 0x06000038 RID: 56 RVA: 0x00003038 File Offset: 0x00001238
		[EditorBrowsable(EditorBrowsableState.Never)]
		[DebuggerHidden]
		public string ToString()
		{
			return this.method_4();
		}

		// Token: 0x06000039 RID: 57 RVA: 0x00003050 File Offset: 0x00001250
		[DebuggerHidden]
		private static T smethod_0<T>(T gparam_0) where T : new()
		{
			T result;
			if (gparam_0 == null)
			{
				result = Activator.CreateInstance<T>();
			}
			else
			{
				result = gparam_0;
			}
			return result;
		}

		// Token: 0x0600003A RID: 58 RVA: 0x00003074 File Offset: 0x00001274
		[DebuggerHidden]
		private void method_1<T>(ref T gparam_0)
		{
			gparam_0 = default(T);
		}

		// Token: 0x0600003B RID: 59 RVA: 0x00003080 File Offset: 0x00001280
		[DebuggerHidden]
		[EditorBrowsable(EditorBrowsableState.Never)]
		public Class3()
		{
		}

		// Token: 0x0600003C RID: 60 RVA: 0x00003088 File Offset: 0x00001288
		static object smethod_1(object object_0)
		{
			return RuntimeHelpers.GetObjectValue(object_0);
		}

		// Token: 0x0600003D RID: 61 RVA: 0x00003090 File Offset: 0x00001290
		bool method_2(object object_0)
		{
			return base.Equals(object_0);
		}

		// Token: 0x0600003E RID: 62 RVA: 0x0000309C File Offset: 0x0000129C
		int method_3()
		{
			return base.GetHashCode();
		}

		// Token: 0x0600003F RID: 63 RVA: 0x000030A4 File Offset: 0x000012A4
		static Type smethod_2(RuntimeTypeHandle runtimeTypeHandle_0)
		{
			return Type.GetTypeFromHandle(runtimeTypeHandle_0);
		}

		// Token: 0x06000040 RID: 64 RVA: 0x000030AC File Offset: 0x000012AC
		string method_4()
		{
			return base.ToString();
		}
	}

	// Token: 0x02000007 RID: 7
	[ComVisible(false)]
	[EditorBrowsable(EditorBrowsableState.Never)]
	internal sealed class Class4<T> where T : new()
	{
		// Token: 0x1700000C RID: 12
		// (get) Token: 0x06000041 RID: 65 RVA: 0x000030B4 File Offset: 0x000012B4
		internal T Prop_0
		{
			[DebuggerHidden]
			get
			{
				if (Class1.Class4<T>.gparam_0 == null)
				{
					Class1.Class4<T>.gparam_0 = Activator.CreateInstance<T>();
				}
				return Class1.Class4<T>.gparam_0;
			}
		}

		// Token: 0x06000042 RID: 66 RVA: 0x000030E4 File Offset: 0x000012E4
		[EditorBrowsable(EditorBrowsableState.Never)]
		[DebuggerHidden]
		public Class4()
		{
		}

		// Token: 0x0400000D RID: 13
		[ThreadStatic]
		[CompilerGenerated]
		private static T gparam_0;
	}
}

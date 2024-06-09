using Microsoft.VisualBasic.CompilerServices;
using System;
using System.IO;
using System.Runtime.InteropServices;
using System.Text;

namespace Xh0kO1ZCmA
{
	public class cConfig
	{
		public string ConfigPath;

		public cConfig(string _ConfigPath)
		{
			this.ConfigPath = _ConfigPath;
		}

		public object createConfig()
		{
			File.Create(this.ConfigPath);
			return true;
		}

		public object exists()
		{
			object obj;
			obj = (!File.Exists(this.ConfigPath) ? false : true);
			return obj;
		}

		public object read(string Sektion, string Parameter)
		{
			StringBuilder stringBuilder = new StringBuilder(1024);
			cConfig.ReadINI(Sektion, Parameter, "", stringBuilder, stringBuilder.Capacity, this.ConfigPath);
			return stringBuilder.ToString();
		}

		[DllImport("kernel32", CharSet=CharSet.None, EntryPoint="GetPrivateProfileString", ExactSpelling=false)]
		private static extern int ReadINI(string Sektion, string Key, string StandartVal, StringBuilder Result, int Size, string Dateiname);

		public cConfig.RGBA ReadRGBA(string Sektion, string Parameter)
		{
			cConfig.RGBA single = new cConfig.RGBA();
			StringBuilder stringBuilder = new StringBuilder(1024);
			cConfig.ReadINI(Sektion, Parameter, "", stringBuilder, stringBuilder.Capacity, this.ConfigPath);
			string[] strArrays = stringBuilder.ToString().Split(new char[] { '/' });
			single.r = Conversions.ToSingle(strArrays[0]);
			single.g = Conversions.ToSingle(strArrays[1]);
			single.b = Conversions.ToSingle(strArrays[2]);
			single.a = Conversions.ToSingle(strArrays[3]);
			return single;
		}

		public string RGBAtoString(cConfig.RGBA RGBAColor)
		{
			cConfig.RGBA rGBAColor = RGBAColor;
			return string.Concat(new string[] { "red: ", Conversions.ToString(rGBAColor.r), ", green: ", Conversions.ToString(rGBAColor.g), ", blue: ", Conversions.ToString(rGBAColor.b), ", alpha: ", Conversions.ToString(rGBAColor.a) });
		}

		public bool write(string Sektion, string Parameter, string Value)
		{
			cConfig.WritePrivateProfileString(ref Sektion, ref Parameter, ref Value, ref this.ConfigPath);
			return true;
		}

		[DllImport("kernel32", CharSet=CharSet.Ansi, EntryPoint="WritePrivateProfileStringA", ExactSpelling=true, SetLastError=true)]
		private static extern int WritePrivateProfileString(ref string lpApplicationName, ref string lpKeyName, ref string lpString, ref string lpFileName);

		public object WriteRGBA(string Sektion, string Parameter, cConfig.RGBA RGBAcolor)
		{
			string str = string.Concat(new string[] { "\"", Conversions.ToString(RGBAcolor.r), "/", Conversions.ToString(RGBAcolor.g), "/", Conversions.ToString(RGBAcolor.b), "/", Conversions.ToString(RGBAcolor.a), "\"" });
			cConfig.WritePrivateProfileString(ref Sektion, ref Parameter, ref str, ref this.ConfigPath);
			return true;
		}

		public struct RGBA
		{
			public float r;

			public float g;

			public float b;

			public float a;

			public RGBA(float _r, float _g, float _b, float _a)
			{
				this = new cConfig.RGBA()
				{
					r = _r,
					g = _g,
					b = _b,
					a = _a
				};
			}
		}
	}
}
using System;
using Memory;

namespace WindowsFormsApp10
{
	// Token: 0x02000003 RID: 3
	internal class Minecraft
	{
		// Token: 0x06000092 RID: 146 RVA: 0x00006AA1 File Offset: 0x00004CA1
		public void Inject()
		{
			this.MemLib.OpenProcess("Minecraft.Windows");
		}

		// Token: 0x06000093 RID: 147 RVA: 0x00006AB8 File Offset: 0x00004CB8
		public float getPosition(string XYorZ)
		{
			bool flag = XYorZ == "X";
			float result;
			if (flag)
			{
				result = this.MemLib.ReadFloat("Minecraft.Windows.exe+0x03586B38,0x168,0x260,0x390,0x48,0x140,0x180,0x0,0x480", "", true);
			}
			else
			{
				bool flag2 = XYorZ == "Y";
				if (flag2)
				{
					result = this.MemLib.ReadFloat("Minecraft.Windows.exe+0x03586B38,0x168,0x260,0x390,0x48,0x140,0x180,0x0,0x484", "", true);
				}
				else
				{
					bool flag3 = XYorZ == "Z";
					if (flag3)
					{
						result = this.MemLib.ReadFloat("Minecraft.Windows.exe+0x03586B38,0x168,0x260,0x390,0x48,0x140,0x180,0x0,0x488", "", true);
					}
					else
					{
						result = 0f;
					}
				}
			}
			return result;
		}

		// Token: 0x06000094 RID: 148 RVA: 0x00006B50 File Offset: 0x00004D50
		public float getPitch()
		{
			return this.MemLib.ReadFloat("Minecraft.Windows.exe+0x03586B38,0x168,0x260,0x390,0x48,0x140,0x180,0x0,0x128", "", true);
		}

		// Token: 0x06000095 RID: 149 RVA: 0x00006B78 File Offset: 0x00004D78
		public float getYaw()
		{
			return this.MemLib.ReadFloat("Minecraft.Windows.exe+0x03586B38,0x168,0x260,0x390,0x48,0x140,0x180,0x0,0x12C", "", true);
		}

		// Token: 0x06000096 RID: 150 RVA: 0x00006BA0 File Offset: 0x00004DA0
		public float getVelocity(string XYorZ)
		{
			bool flag = XYorZ == "X";
			float result;
			if (flag)
			{
				result = this.MemLib.ReadFloat("Minecraft.Windows.exe+0x03586B38,0x168,0x260,0x390,0x48,0x140,0x180,0x0,0x4BC", "", true);
			}
			else
			{
				bool flag2 = XYorZ == "Y";
				if (flag2)
				{
					result = this.MemLib.ReadFloat("Minecraft.Windows.exe+0x03586B38,0x168,0x260,0x390,0x48,0x140,0x180,0x0,0x4C0", "", true);
				}
				else
				{
					bool flag3 = XYorZ == "Z";
					if (flag3)
					{
						result = this.MemLib.ReadFloat("Minecraft.Windows.exe+0x03586B38,0x168,0x260,0x390,0x48,0x140,0x180,0x0,0x4C4", "", true);
					}
					else
					{
						result = 0f;
					}
				}
			}
			return result;
		}

		// Token: 0x06000097 RID: 151 RVA: 0x00006C32 File Offset: 0x00004E32
		public void Zoom()
		{
			this.MemLib.WriteMemory("Minecraft.Windows.exe+0x03586B38,0xAD0,0x20,0x1A0,0xC8,0x0,0x120,0x1E8", "float", string.Format("{0}", this.zoomValue), "", null);
		}

		// Token: 0x06000098 RID: 152 RVA: 0x00006C66 File Offset: 0x00004E66
		public void unZoom()
		{
			this.MemLib.WriteMemory("Minecraft.Windows.exe+0x03586B38,0xAD0,0x20,0x1A0,0xC8,0x0,0x120,0x1E8", "float", string.Format("{0}", this.unZoomValue), "", null);
		}

		// Token: 0x06000099 RID: 153 RVA: 0x00006C9A File Offset: 0x00004E9A
		public void alwaysDay()
		{
			this.MemLib.WriteMemory("Minecraft.Windows.exe+03666958,0x10,0x0,0x0,0x18,0xB0,0x358,0x5C0", "float", "-1", "", null);
		}

		// Token: 0x0600009A RID: 154 RVA: 0x00006CC0 File Offset: 0x00004EC0
		public float getFov()
		{
			return this.MemLib.ReadFloat("Minecraft.Windows.exe+0x03586B38,0xAD0,0x20,0x1A0,0xC8,0x0,0x120,0x1E8", "", true);
		}

		// Token: 0x0600009B RID: 155 RVA: 0x00006CE8 File Offset: 0x00004EE8
		public float getTime()
		{
			return this.MemLib.ReadFloat("Minecraft.Windows.exe+03666958,0x10,0x0,0x0,0x18,0xB0,0x358,0x5C0", "", true);
		}

		// Token: 0x0600009C RID: 156 RVA: 0x00006D10 File Offset: 0x00004F10
		public void showCoords()
		{
			this.MemLib.WriteMemory("Minecraft.Windows.exe+0x035F9470,0x50,0x1F8,0x18,0x20,0x30,0x308,0x9A4", "byte", string.Format("{0}", this.onoff), "", null);
		}

		// Token: 0x0600009D RID: 157 RVA: 0x00006D44 File Offset: 0x00004F44
		public void instaBreak()
		{
			this.MemLib.WriteMemory("Minecraft.Windows.exe+0x035F9470,0x70,0xBD0,0xB0,0x98,0x38,0x8,0x20", "float", string.Format("{0}", this.instabreak), "", null);
		}

		// Token: 0x0600009E RID: 158 RVA: 0x00006D78 File Offset: 0x00004F78
		public void userName()
		{
			this.MemLib.WriteMemory("Minecraft.Windows.exe+0x035F9470,0x70,0x868", "string", this.userInput ?? "", "", null);
		}

		// Token: 0x0600009F RID: 159 RVA: 0x00006DA6 File Offset: 0x00004FA6
		public void clearWater()
		{
			this.MemLib.WriteMemory("Minecraft.Windows.exe+0x193653C", "bytes", "0x90 0x90 0x90 0x90 0x90 0x90 0x90", "", null);
		}

		// Token: 0x060000A0 RID: 160 RVA: 0x00006DCA File Offset: 0x00004FCA
		public void brightness()
		{
			this.MemLib.WriteMemory("Minecraft.Windows.exe+0x035DF8E8,0x20,0xBF8,0xE0,0x8,0x128,0x138,0x1E8", "float", "100", "", null);
		}

		// Token: 0x060000A1 RID: 161 RVA: 0x00006DEE File Offset: 0x00004FEE
		public void normalBright()
		{
			this.MemLib.WriteMemory("Minecraft.Windows.exe+0x035DF8E8,0x20,0xBF8,0xE0,0x8,0x128,0x138,0x1E8", "float", "1", "", null);
		}

		// Token: 0x060000A2 RID: 162 RVA: 0x00006E12 File Offset: 0x00005012
		public void speedHack()
		{
			this.MemLib.WriteMemory("Minecraft.Windows.exe+0x035F9470,0x70,0x460,0x40,0xC8,0x18,0x1F8,0x9C", "float", string.Format("{0}", this.speed), "", null);
		}

		// Token: 0x060000A3 RID: 163 RVA: 0x00006E46 File Offset: 0x00005046
		public void autoSprint()
		{
			this.MemLib.WriteMemory("Minecraft.Windows.exe+0x035F9470,0x70,0x460,0x40,0xC8,0x18,0x1F8,0x9C", "float", string.Format("{0}", this.sprint), "", null);
		}

		// Token: 0x060000A4 RID: 164 RVA: 0x00006E7A File Offset: 0x0000507A
		public void normalWalk()
		{
			this.MemLib.WriteMemory("Minecraft.Windows.exe+0x035F9470,0x70,0x460,0x40,0xC8,0x18,0x1F8,0x9C", "float", string.Format("{0}", this.normal), "", null);
		}

		// Token: 0x060000A5 RID: 165 RVA: 0x00006EB0 File Offset: 0x000050B0
		public float speedMeter()
		{
			return this.MemLib.ReadFloat("Minecraft.Windows.exe+0x035F9470,0x70,0x460,0x40,0xC8,0x18,0x1F8,0x9C", "", true);
		}

		// Token: 0x060000A6 RID: 166 RVA: 0x00006ED8 File Offset: 0x000050D8
		public void hideHand()
		{
			this.MemLib.WriteMemory("Minecraft.Windows.exe+0x035DF8E8,0x20,0xC38,0x1D8,0x8,0x0,0xCD0,0x1E0", "byte", "1", "", null);
		}

		// Token: 0x060000A7 RID: 167 RVA: 0x00006EFC File Offset: 0x000050FC
		public void showHand()
		{
			this.MemLib.WriteMemory("Minecraft.Windows.exe+0x035DF8E8,0x20,0xC38,0x1D8,0x8,0x0,0xCD0,0x1E0", "byte", "0", "", null);
		}

		// Token: 0x060000A8 RID: 168 RVA: 0x00006F20 File Offset: 0x00005120
		public void noWeb()
		{
			this.MemLib.WriteMemory("Minecraft.Windows.exe+0x192194F", "bytes", "0x90 0x90 0x90 0x90 0x90 0x90", "", null);
		}

		// Token: 0x060000A9 RID: 169 RVA: 0x00006F44 File Offset: 0x00005144
		public void cobWeb()
		{
			this.MemLib.WriteMemory("Minecraft.Windows.exe+0x192194F", "bytes", "89 81 38 02 00 00", "", null);
		}

		// Token: 0x060000AA RID: 170 RVA: 0x00006F68 File Offset: 0x00005168
		public void spinBot()
		{
			this.MemLib.WriteMemory("Minecraft.Windows.exe+0x035F9470,0x50,0x7F8,0x0,0x18,0x8,0xD0,0x670", "float", string.Format("{0}", this.spinbot), "", null);
			this.MemLib.WriteMemory("Minecraft.Windows.exe+0x1D4A157", "bytes", "0x90 0x90 0x90 0x90 0x90 0x90 0x90 0x90", "", null);
		}

		// Token: 0x060000AB RID: 171 RVA: 0x00006FC8 File Offset: 0x000051C8
		public void highMusic()
		{
			this.MemLib.WriteMemory("Minecraft.Windows.exe+0x03600610,0x110,0x68,0x18,0xC8,0x30,0xE8,0x488", "float", string.Format("{0}", this.musicVolume), "", null);
		}

		// Token: 0x060000AC RID: 172 RVA: 0x00006FFC File Offset: 0x000051FC
		public void spinDisable()
		{
			this.MemLib.WriteMemory("Minecraft.Windows.exe+0x1D4A157", "bytes", "F3 0F 11 87 70 06 00 00", "", null);
		}

		// Token: 0x060000AD RID: 173 RVA: 0x00007020 File Offset: 0x00005220
		public void noSwing()
		{
			this.MemLib.WriteMemory("Minecraft.Windows.exe+0x175DDD1", "bytes", "0x90 0x90 0x90 0x90 0x90 0x90 0x90 0x90", "", null);
		}

		// Token: 0x060000AE RID: 174 RVA: 0x00007044 File Offset: 0x00005244
		public void enableSwing()
		{
			this.MemLib.WriteMemory("Minecraft.Windows.exe+0x175DDD1", "bytes", "F3 0F 11 8B E0 06 00 00", "", null);
		}

		// Token: 0x060000AF RID: 175 RVA: 0x00007068 File Offset: 0x00005268
		public void antiAim()
		{
			this.MemLib.WriteMemory("Minecraft.Windows.exe+0x1D4A157", "bytes", "0x90 0x90 0x90 0x90 0x90 0x90 0x90 0x90", "", null);
		}

		// Token: 0x060000B0 RID: 176 RVA: 0x0000708C File Offset: 0x0000528C
		public void disableAntiAim()
		{
			this.MemLib.WriteMemory("Minecraft.Windows.exe+0x1D4A0DA", "bytes", "F3 0F 11 87 70 06 00 00", "", null);
		}

		// Token: 0x060000B1 RID: 177 RVA: 0x000070B0 File Offset: 0x000052B0
		public void noRotation()
		{
			this.MemLib.WriteMemory("Minecraft.Windows.exe+1D4A157", "bytes", "0x90 0x90 0x90 0x90 0x90 0x90 0x90 0x90", "", null);
			this.MemLib.WriteMemory("Minecraft.Windows.exe+1D4A157", "bytes", "0x90 0x90 0x90 0x90 0x90 0x90 0x90 0x90", "", null);
		}

		// Token: 0x060000B2 RID: 178 RVA: 0x00007100 File Offset: 0x00005300
		public void FixRotation()
		{
			this.MemLib.WriteMemory("Minecraft.Windows.exe+0x1D4A157", "bytes", "F3 0F 11 87 70 06 00 00", "", null);
			this.MemLib.WriteMemory("Minecraft.Windows.exe+0x1D4A0DA", "bytes", "F3 0F 11 8F 70 06 00 00", "", null);
		}

		// Token: 0x060000B3 RID: 179 RVA: 0x00007150 File Offset: 0x00005350
		public void fuckGame()
		{
			this.MemLib.WriteMemory("Minecraft.Windows.exe+0x1934863", "bytes", "0x90 0x90 0x90 0x90 0x90 0x90", "", null);
		}

		// Token: 0x060000B4 RID: 180 RVA: 0x00007174 File Offset: 0x00005374
		public void fixGame()
		{
			this.MemLib.WriteMemory("Minecraft.Windows.exe+0x1902418", "bytes", "89 87 B4 04 00 00", "", null);
		}

		// Token: 0x04000045 RID: 69
		public Mem MemLib = new Mem();

		// Token: 0x04000046 RID: 70
		public int zoomValue = 30;

		// Token: 0x04000047 RID: 71
		public int unZoomValue = 110;

		// Token: 0x04000048 RID: 72
		public int boostAmount = 100;

		// Token: 0x04000049 RID: 73
		public int onoff = 1;

		// Token: 0x0400004A RID: 74
		public int instabreak = 1;

		// Token: 0x0400004B RID: 75
		public int hideShow = 1;

		// Token: 0x0400004C RID: 76
		public float speed = 0.4f;

		// Token: 0x0400004D RID: 77
		public float sprint = 0.13f;

		// Token: 0x0400004E RID: 78
		public float normal = 0.1f;

		// Token: 0x0400004F RID: 79
		public float spinbot;

		// Token: 0x04000050 RID: 80
		public string userInput;

		// Token: 0x04000051 RID: 81
		public float musicVolume = 1f;
	}
}

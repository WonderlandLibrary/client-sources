using System;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Reflection;
using System.Windows.Forms;

namespace Client
{
	// Token: 0x0200000F RID: 15
	public class skeetSlider_1 : UserControl
	{
		// Token: 0x17000016 RID: 22
		// (get) Token: 0x0600008E RID: 142 RVA: 0x0001A388 File Offset: 0x00018588
		// (set) Token: 0x0600008F RID: 143 RVA: 0x0001A418 File Offset: 0x00018618
		public bool Boolean_0
		{
			get
			{
				int num = 0;
				bool result;
				do
				{
					if (num == 1)
					{
						<Module>.Class0.smethod_0();
						num = 2;
					}
					if (num == 3)
					{
						break;
					}
					if (num == 2)
					{
						result = this.bool_1;
						num = 3;
					}
					if (num == 0)
					{
						num = 1;
					}
				}
				while (num != 4);
				return result;
			}
			set
			{
				int num = 0;
				for (;;)
				{
					if (num != 6)
					{
						goto IL_0E;
					}
					if (value)
					{
						num = 7;
						goto IL_0E;
					}
					goto IL_9F;
					IL_BA:
					Color black;
					if (num == 3)
					{
						black = Color.Black;
						num = 4;
					}
					if (num == 11)
					{
						this.lbTitle.Color_0 = black;
						num = 12;
					}
					if (num == 7)
					{
						this.lbTitle.ForeColor = black;
						num = 8;
					}
					if (num == 2)
					{
						this.bool_1 = value;
						num = 3;
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
					if (num == 12)
					{
						break;
					}
					continue;
					IL_0E:
					if (num == 5)
					{
						num = 6;
					}
					Color foreColor;
					if (num == 4)
					{
						foreColor = Color.FromArgb(190, 190, 190);
						num = 5;
					}
					if (num == 9)
					{
						break;
					}
					if (num == 8)
					{
						this.lbTitle.Color_0 = foreColor;
						num = 9;
					}
					if (num != 10)
					{
						goto IL_BA;
					}
					IL_9F:
					this.lbTitle.ForeColor = foreColor;
					num = 11;
					goto IL_BA;
				}
			}
		}

		// Token: 0x17000017 RID: 23
		// (get) Token: 0x06000090 RID: 144 RVA: 0x0001A5DC File Offset: 0x000187DC
		// (set) Token: 0x06000091 RID: 145 RVA: 0x0001A66C File Offset: 0x0001886C
		public bool Boolean_1
		{
			get
			{
				int num = 0;
				bool result;
				do
				{
					if (num == 2)
					{
						result = this.bool_0;
						num = 3;
					}
					if (num == 3)
					{
						break;
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
				return result;
			}
			set
			{
				int num = 0;
				do
				{
					if (num == 3)
					{
						this.lbTitle.Visible = value;
						num = 4;
					}
					if (num == 1)
					{
						<Module>.Class0.smethod_0();
						num = 2;
					}
					if (num == 2)
					{
						this.bool_0 = value;
						num = 3;
					}
					if (num == 0)
					{
						num = 1;
					}
				}
				while (num != 4);
			}
		}

		// Token: 0x17000018 RID: 24
		// (get) Token: 0x06000092 RID: 146 RVA: 0x0001A71C File Offset: 0x0001891C
		// (set) Token: 0x06000093 RID: 147 RVA: 0x0001A7A8 File Offset: 0x000189A8
		public string String_0
		{
			get
			{
				int num = 0;
				string result;
				while (num != 3)
				{
					if (num == 2)
					{
						result = this.string_0;
						num = 3;
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
					if (num == 4)
					{
						break;
					}
				}
				return result;
			}
			set
			{
				int num = 0;
				for (;;)
				{
					bool flag;
					if (num == 2)
					{
						flag = !string.IsNullOrEmpty(value);
						num = 3;
					}
					if (num == 1)
					{
						<Module>.Class0.smethod_0();
						num = 2;
					}
					if (num == 5)
					{
						goto IL_4B;
					}
					IL_85:
					if (num == 4)
					{
						this.string_0 = value;
						num = 5;
					}
					if (num == 3)
					{
						if (!flag)
						{
							goto IL_4B;
						}
						num = 4;
					}
					if (num == 0)
					{
						num = 1;
					}
					if (num == 6)
					{
						break;
					}
					continue;
					IL_4B:
					this.lbTitle.Text = this.string_0;
					num = 6;
					goto IL_85;
				}
			}
		}

		// Token: 0x17000019 RID: 25
		// (get) Token: 0x06000094 RID: 148 RVA: 0x0001A8AC File Offset: 0x00018AAC
		// (set) Token: 0x06000095 RID: 149 RVA: 0x0001A93C File Offset: 0x00018B3C
		public bool Boolean_2
		{
			get
			{
				int num = 0;
				bool result;
				do
				{
					if (num == 1)
					{
						<Module>.Class0.smethod_0();
						num = 2;
					}
					if (num == 2)
					{
						result = this.bool_2;
						num = 3;
					}
					if (num == 3)
					{
						break;
					}
					if (num == 0)
					{
						num = 1;
					}
				}
				while (num != 4);
				return result;
			}
			set
			{
				int num = 0;
				do
				{
					if (num == 3)
					{
						this.lbSliderValue.Visible = value;
						num = 4;
					}
					if (num == 1)
					{
						<Module>.Class0.smethod_0();
						num = 2;
					}
					if (num == 2)
					{
						this.bool_2 = value;
						num = 3;
					}
					if (num == 0)
					{
						num = 1;
					}
				}
				while (num != 4);
			}
		}

		// Token: 0x1700001A RID: 26
		// (get) Token: 0x06000096 RID: 150 RVA: 0x0001A9EC File Offset: 0x00018BEC
		// (set) Token: 0x06000097 RID: 151 RVA: 0x0001AA78 File Offset: 0x00018C78
		public string String_1
		{
			get
			{
				int num = 0;
				string result;
				while (num != 3)
				{
					if (num == 1)
					{
						<Module>.Class0.smethod_0();
						num = 2;
					}
					if (num == 2)
					{
						result = this.string_1;
						num = 3;
					}
					if (num == 0)
					{
						num = 1;
					}
					if (num == 4)
					{
						break;
					}
				}
				return result;
			}
			set
			{
				int num = 0;
				do
				{
					if (num == 3)
					{
						this.method_1();
						num = 4;
					}
					if (num == 1)
					{
						<Module>.Class0.smethod_0();
						num = 2;
					}
					if (num == 2)
					{
						this.string_1 = value;
						num = 3;
					}
					if (num == 0)
					{
						num = 1;
					}
				}
				while (num != 4);
			}
		}

		// Token: 0x1700001B RID: 27
		// (get) Token: 0x06000098 RID: 152 RVA: 0x0001AB1C File Offset: 0x00018D1C
		// (set) Token: 0x06000099 RID: 153 RVA: 0x0001ABAC File Offset: 0x00018DAC
		public string String_2
		{
			get
			{
				int num = 0;
				string result;
				do
				{
					if (num == 1)
					{
						<Module>.Class0.smethod_0();
						num = 2;
					}
					if (num == 3)
					{
						break;
					}
					if (num == 2)
					{
						result = this.string_2;
						num = 3;
					}
					if (num == 0)
					{
						num = 1;
					}
				}
				while (num != 4);
				return result;
			}
			set
			{
				int num = 0;
				do
				{
					if (num == 1)
					{
						<Module>.Class0.smethod_0();
						num = 2;
					}
					if (num == 3)
					{
						this.method_1();
						num = 4;
					}
					if (num == 2)
					{
						this.string_2 = value;
						num = 3;
					}
					if (num == 0)
					{
						num = 1;
					}
				}
				while (num != 4);
			}
		}

		// Token: 0x1700001C RID: 28
		// (get) Token: 0x0600009A RID: 154 RVA: 0x0001AC50 File Offset: 0x00018E50
		// (set) Token: 0x0600009B RID: 155 RVA: 0x0001ACDC File Offset: 0x00018EDC
		public Color Color_0
		{
			get
			{
				int num = 0;
				Color result;
				while (num != 3)
				{
					if (num == 2)
					{
						result = this.color_0;
						num = 3;
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
					if (num == 4)
					{
						break;
					}
				}
				return result;
			}
			set
			{
				int num = 0;
				do
				{
					if (num == 1)
					{
						<Module>.Class0.smethod_0();
						num = 2;
					}
					if (num == 3)
					{
						this.method_0();
						num = 4;
					}
					if (num == 2)
					{
						this.color_0 = value;
						num = 3;
					}
					if (num == 0)
					{
						num = 1;
					}
				}
				while (num != 4);
			}
		}

		// Token: 0x1700001D RID: 29
		// (get) Token: 0x0600009C RID: 156 RVA: 0x0001AD80 File Offset: 0x00018F80
		// (set) Token: 0x0600009D RID: 157 RVA: 0x0001AE10 File Offset: 0x00019010
		public Color Color_1
		{
			get
			{
				int num = 0;
				Color result;
				do
				{
					if (num == 1)
					{
						<Module>.Class0.smethod_0();
						num = 2;
					}
					if (num == 3)
					{
						break;
					}
					if (num == 2)
					{
						result = this.color_1;
						num = 3;
					}
					if (num == 0)
					{
						num = 1;
					}
				}
				while (num != 4);
				return result;
			}
			set
			{
				int num = 0;
				do
				{
					if (num == 3)
					{
						this.method_0();
						num = 4;
					}
					if (num == 2)
					{
						this.color_1 = value;
						num = 3;
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
			}
		}

		// Token: 0x1700001E RID: 30
		// (get) Token: 0x0600009E RID: 158 RVA: 0x0001AEB4 File Offset: 0x000190B4
		// (set) Token: 0x0600009F RID: 159 RVA: 0x0001AF40 File Offset: 0x00019140
		public int Int32_0
		{
			get
			{
				int num = 0;
				int result;
				while (num != 3)
				{
					if (num == 2)
					{
						result = this.int_0;
						num = 3;
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
					if (num == 4)
					{
						break;
					}
				}
				return result;
			}
			set
			{
				int num = 0;
				for (;;)
				{
					bool flag;
					if (num == 2)
					{
						flag = (value >= 0);
						num = 3;
					}
					if (num == 1)
					{
						<Module>.Class0.smethod_0();
						num = 2;
					}
					if (num == 4)
					{
						this.int_0 = value;
						num = 5;
					}
					if (num != 3)
					{
						goto IL_68;
					}
					if (flag)
					{
						num = 4;
						goto IL_68;
					}
					goto IL_75;
					IL_87:
					if (num == 0)
					{
						num = 1;
					}
					if (num == 6)
					{
						break;
					}
					continue;
					IL_68:
					if (num != 5)
					{
						goto IL_87;
					}
					IL_75:
					this.method_1();
					num = 6;
					goto IL_87;
				}
			}
		}

		// Token: 0x1700001F RID: 31
		// (get) Token: 0x060000A0 RID: 160 RVA: 0x0001B030 File Offset: 0x00019230
		// (set) Token: 0x060000A1 RID: 161 RVA: 0x0001B0C0 File Offset: 0x000192C0
		public double Double_0
		{
			get
			{
				int num = 0;
				double result;
				do
				{
					if (num == 2)
					{
						result = this.double_2;
						num = 3;
					}
					if (num == 3)
					{
						break;
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
				return result;
			}
			set
			{
				int num = 0;
				do
				{
					if (num == 2)
					{
						num = 3;
					}
					if (num == 1)
					{
						<Module>.Class0.smethod_0();
						num = 2;
					}
					if (num == 3)
					{
						if (value < this.double_1)
						{
							goto IL_95;
						}
						num = 4;
					}
					if (num == 0)
					{
						num = 1;
					}
				}
				while (num != 4);
				bool flag = this.double_0 >= value;
				goto IL_B2;
				IL_95:
				flag = false;
				IL_B2:
				if (flag)
				{
					this.double_2 = value;
					this.method_1();
				}
				else
				{
					MessageBox.Show("Value can't be lower than minimum or higher than maximum.");
				}
			}
		}

		// Token: 0x17000020 RID: 32
		// (get) Token: 0x060000A2 RID: 162 RVA: 0x0001B1A4 File Offset: 0x000193A4
		// (set) Token: 0x060000A3 RID: 163 RVA: 0x0001B234 File Offset: 0x00019434
		public double Double_1
		{
			get
			{
				int num = 0;
				double result;
				do
				{
					if (num == 1)
					{
						<Module>.Class0.smethod_0();
						num = 2;
					}
					if (num == 2)
					{
						result = this.double_0;
						num = 3;
					}
					if (num == 3)
					{
						break;
					}
					if (num == 0)
					{
						num = 1;
					}
				}
				while (num != 4);
				return result;
			}
			set
			{
				int num = 0;
				do
				{
					if (num == 1)
					{
						<Module>.Class0.smethod_0();
						num = 2;
					}
					if (num == 2)
					{
						num = 3;
					}
					if (num == 0)
					{
						num = 1;
					}
				}
				while (num != 3);
			}
		}

		// Token: 0x17000021 RID: 33
		// (get) Token: 0x060000A4 RID: 164 RVA: 0x0001B2A8 File Offset: 0x000194A8
		// (set) Token: 0x060000A5 RID: 165 RVA: 0x0001B338 File Offset: 0x00019538
		public double Double_2
		{
			get
			{
				int num = 0;
				double result;
				do
				{
					if (num == 2)
					{
						result = this.double_1;
						num = 3;
					}
					if (num == 3)
					{
						break;
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
				return result;
			}
			set
			{
				int num = 0;
				for (;;)
				{
					double num2;
					if (num == 2)
					{
						num2 = value;
						num = 3;
					}
					bool flag;
					if (num == 5)
					{
						flag = (this.double_2 >= num2);
						num = 6;
					}
					if (num == 8)
					{
						this.method_1();
						num = 9;
					}
					if (num == 10)
					{
						goto IL_88;
					}
					IL_104:
					if (num == 1)
					{
						<Module>.Class0.smethod_0();
						num = 2;
					}
					if (num == 6)
					{
						if (!flag)
						{
							goto IL_88;
						}
						num = 7;
					}
					if (num != 9)
					{
						bool flag2;
						if (num == 3)
						{
							flag2 = (num2 <= this.double_0);
							num = 4;
						}
						if (num != 4)
						{
							goto IL_17B;
						}
						if (flag2)
						{
							num = 5;
							goto IL_17B;
						}
						goto IL_188;
						IL_1EF:
						if (num == 7)
						{
							this.double_1 = value;
							num = 8;
						}
						if (num == 11)
						{
							break;
						}
						if (num == 0)
						{
							num = 1;
						}
						if (num == 13)
						{
							break;
						}
						continue;
						IL_17B:
						if (num != 12)
						{
							goto IL_1EF;
						}
						IL_188:
						MessageBox.Show(string.Concat(new string[]
						{
							"Minimum value can't be higher than maximum value (",
							num2.ToString(),
							">",
							this.double_0.ToString(),
							")"
						}));
						num = 13;
						goto IL_1EF;
					}
					break;
					IL_88:
					MessageBox.Show(string.Concat(new string[]
					{
						"Current value can't be lower than minimum value (",
						num2.ToString(),
						">",
						this.double_2.ToString(),
						")"
					}));
					num = 11;
					goto IL_104;
				}
			}
		}

		// Token: 0x060000A6 RID: 166 RVA: 0x0001B5B4 File Offset: 0x000197B4
		public static Color smethod_0(Color color_2, float float_0)
		{
			int num = 0;
			Color result;
			for (;;)
			{
				float num2;
				if (num == 13)
				{
					num2 = (255f - num2) * float_0 + num2;
					num = 14;
				}
				float num3;
				if (num == 2)
				{
					num3 = (float)color_2.R;
					num = 3;
				}
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 7)
				{
					float_0 = 1f + float_0;
					num = 8;
				}
				if (num != 16)
				{
					float num4;
					if (num == 14)
					{
						num4 = (255f - num4) * float_0 + num4;
						num = 15;
					}
					if (num == 10)
					{
						num4 *= float_0;
						num = 11;
					}
					if (num == 12)
					{
						goto IL_1C0;
					}
					IL_1E1:
					if (num == 11)
					{
						goto IL_13D;
					}
					if (num == 3)
					{
						num2 = (float)color_2.G;
						num = 4;
					}
					bool flag;
					if (num == 5)
					{
						flag = (float_0 < 0f);
						num = 6;
					}
					if (num == 15)
					{
						goto IL_13D;
					}
					IL_167:
					if (num == 8)
					{
						num3 *= float_0;
						num = 9;
					}
					if (num == 9)
					{
						num2 *= float_0;
						num = 10;
					}
					if (num == 6)
					{
						if (!flag)
						{
							goto IL_1C0;
						}
						num = 7;
					}
					if (num == 4)
					{
						num4 = (float)color_2.B;
						num = 5;
					}
					if (num == 0)
					{
						num = 1;
					}
					if (num == 17)
					{
						break;
					}
					continue;
					IL_13D:
					result = Color.FromArgb((int)color_2.A, (int)num3, (int)num2, (int)num4);
					num = 16;
					goto IL_167;
					IL_1C0:
					num3 = (255f - num3) * float_0 + num3;
					num = 13;
					goto IL_1E1;
				}
				break;
			}
			return result;
		}

		// Token: 0x060000A7 RID: 167 RVA: 0x0001B848 File Offset: 0x00019A48
		private void method_0()
		{
			int num = 0;
			for (;;)
			{
				if (num == 13)
				{
					this.method_2();
					num = 14;
				}
				if (num == 2)
				{
					this.bitmap_0.SetPixel(0, 0, this.color_1);
					num = 3;
				}
				int num2;
				if (num == 10)
				{
					num2++;
					num = 11;
				}
				if (num == 4)
				{
					num2 = 1;
					num = 5;
				}
				if (num != 12)
				{
					goto IL_A4;
				}
				bool flag;
				if (!flag)
				{
					num = 13;
					goto IL_A4;
				}
				goto IL_F4;
				IL_126:
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 11)
				{
					goto IL_1A6;
				}
				IL_1F6:
				if (num == 3)
				{
					this.bitmap_1.SetPixel(0, 0, this.color_0);
					num = 4;
				}
				float num3;
				if (num == 8)
				{
					this.bitmap_1.SetPixel(0, num2, skeetSlider_1.smethod_0(this.color_0, -num3 * 2f));
					num = 9;
				}
				if (num != 6)
				{
					if (num == 0)
					{
						num = 1;
					}
					if (num == 14)
					{
						break;
					}
					continue;
				}
				IL_1A6:
				flag = (0.1f >= num3);
				num = 12;
				goto IL_1F6;
				IL_A4:
				if (num == 9)
				{
					num3 += 0.02f;
					num = 10;
				}
				if (num == 5)
				{
					num3 = 0.02f;
					num = 6;
				}
				if (num != 7)
				{
					goto IL_126;
				}
				IL_F4:
				this.bitmap_0.SetPixel(0, num2, skeetSlider_1.smethod_0(this.color_1, num3));
				num = 8;
				goto IL_126;
			}
		}

		// Token: 0x060000A8 RID: 168 RVA: 0x0001BAAC File Offset: 0x00019CAC
		private void method_1()
		{
			int num = 0;
			for (;;)
			{
				if (num == 14)
				{
					this.pnlSliderBox.Location = new Point(15, 0);
					num = 15;
				}
				int num2;
				if (num == 11)
				{
					this.int_1 = num2;
					num = 12;
				}
				Size size;
				bool flag;
				if (num == 6)
				{
					flag = (size.Width / 2 > 15);
					num = 7;
				}
				if (num == 10)
				{
					this.lbTitle.Location = new Point(num2 + 1, this.lbTitle.Location.Y);
					num = 11;
				}
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 9)
				{
					this.pnlSliderBox.Location = new Point(num2, this.pnlSliderBox.Location.Y);
					num = 10;
				}
				if (num == 16)
				{
					goto IL_171;
				}
				IL_1E8:
				if (num != 7)
				{
					goto IL_124;
				}
				if (flag)
				{
					num = 8;
					goto IL_124;
				}
				goto IL_2C2;
				IL_2D9:
				if (num == 18)
				{
					this.method_2();
					num = 19;
				}
				if (num == 4)
				{
					Font font = new Font("Verdana", 7.2f, FontStyle.Bold);
					num = 5;
				}
				if (num == 2)
				{
					this.int_1 = 0;
					num = 3;
				}
				if (num == 8)
				{
					num2 = size.Width / 2;
					num = 9;
				}
				if (num == 0)
				{
					num = 1;
				}
				if (num == 19)
				{
					break;
				}
				continue;
				IL_124:
				if (num == 17)
				{
					this.pnlSliderBox.Width = base.Width - this.int_1 * 2;
					num = 18;
				}
				if (num == 12)
				{
					goto IL_171;
				}
				string text;
				if (num == 3)
				{
					text = this.string_2 + string.Format("{0:F" + this.int_0.ToString() + "}", this.double_0) + this.string_1;
					num = 4;
				}
				if (num == 5)
				{
					Font font;
					size = TextRenderer.MeasureText(text, font);
					num = 6;
				}
				if (num == 15)
				{
					this.lbTitle.Location = new Point(15, 1);
					num = 16;
				}
				if (num != 13)
				{
					goto IL_2D9;
				}
				IL_2C2:
				this.int_1 = 15;
				num = 14;
				goto IL_2D9;
				IL_171:
				this.lbSliderValue.Text = this.string_2 + string.Format("{0:F" + this.int_0.ToString() + "}", this.double_2) + this.string_1;
				num = 17;
				goto IL_1E8;
			}
		}

		// Token: 0x060000A9 RID: 169 RVA: 0x0001BE90 File Offset: 0x0001A090
		public skeetSlider_1()
		{
			<Module>.Class0.smethod_0();
			this.bool_0 = true;
			this.bool_1 = false;
			this.string_0 = "Slider";
			this.bool_2 = true;
			this.string_1 = "";
			this.string_2 = "";
			this.color_0 = Color.FromArgb(154, 197, 39);
			this.color_1 = Color.FromArgb(52, 52, 52);
			this.double_0 = 4.0;
			this.double_1 = 3.0;
			this.double_2 = 3.1;
			this.int_0 = 2;
			this.bitmap_0 = new Bitmap(1, 6);
			this.bitmap_1 = new Bitmap(1, 6);
			this.int_1 = 0;
			base..ctor();
			this.InitializeComponent();
			skeetSlider_1.smethod_1(this.pnlSlider);
			this.method_0();
			this.method_1();
			this.MinimumSize = new Size(100, 40);
		}

		// Token: 0x060000AA RID: 170 RVA: 0x0001BF88 File Offset: 0x0001A188
		private void method_2()
		{
			int num = 0;
			do
			{
				double num2;
				if (num == 3)
				{
					double num3;
					num2 = num3 / (double)(this.pnlSliderBox.Width - 2);
					num = 4;
				}
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 2)
				{
					double num3 = this.double_0 - this.double_1;
					num = 3;
				}
				double value;
				if (num == 6)
				{
					this.lbSliderValue.Location = new Point(Convert.ToInt32(value) + this.method_3(this.lbSliderValue.Text.Length), this.lbSliderValue.Location.Y);
					num = 7;
				}
				if (num == 4)
				{
					value = (this.double_2 - this.double_1) / num2;
					num = 5;
				}
				if (num == 5)
				{
					this.pnlSlider.BackgroundImage = this.method_4(this.pnlSliderBox, Convert.ToInt32(value));
					num = 6;
				}
				if (num == 0)
				{
					num = 1;
				}
			}
			while (num != 7);
		}

		// Token: 0x060000AB RID: 171 RVA: 0x0001C12C File Offset: 0x0001A32C
		public static void smethod_1(Control control_0)
		{
			int num = 0;
			for (;;)
			{
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num != 3)
				{
					goto IL_2A;
				}
				bool terminalServerSession;
				if (terminalServerSession)
				{
					num = 4;
					goto IL_2A;
				}
				goto IL_56;
				IL_7C:
				if (num == 4)
				{
					break;
				}
				PropertyInfo property;
				if (num == 6)
				{
					property.SetValue(control_0, true, null);
					num = 7;
				}
				if (num == 0)
				{
					num = 1;
				}
				if (num == 7)
				{
					break;
				}
				continue;
				IL_2A:
				if (num == 2)
				{
					terminalServerSession = SystemInformation.TerminalServerSession;
					num = 3;
				}
				if (num != 5)
				{
					goto IL_7C;
				}
				IL_56:
				property = typeof(Control).GetProperty("DoubleBuffered", BindingFlags.Instance | BindingFlags.NonPublic);
				num = 6;
				goto IL_7C;
			}
		}

		// Token: 0x060000AC RID: 172 RVA: 0x0001C23C File Offset: 0x0001A43C
		private int method_3(int int_2)
		{
			int num = 0;
			int result;
			for (;;)
			{
				if (num != 4)
				{
					goto IL_0E;
				}
				int num2;
				switch (num2)
				{
				case 1:
					goto IL_B3;
				case 2:
					goto IL_49;
				case 3:
					goto IL_FD;
				default:
					num = 5;
					goto IL_0E;
				}
				IL_12E:
				if (num == 0)
				{
					num = 1;
				}
				if (num == 14)
				{
					break;
				}
				continue;
				IL_10F:
				if (num == 12)
				{
					goto IL_11C;
				}
				goto IL_12E;
				IL_C5:
				if (num == 9)
				{
					break;
				}
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 10)
				{
					goto IL_FD;
				}
				goto IL_10F;
				IL_5B:
				if (num == 13 || num == 7)
				{
					break;
				}
				if (num == 2)
				{
					num = 3;
				}
				if (num == 5)
				{
					goto IL_11C;
				}
				if (num == 6)
				{
					goto IL_B3;
				}
				goto IL_C5;
				IL_0E:
				if (num == 3)
				{
					num2 = int_2;
					num = 4;
				}
				if (num == 11)
				{
					break;
				}
				if (num == 8)
				{
					goto IL_49;
				}
				goto IL_5B;
				IL_11C:
				result = 1;
				num = 13;
				goto IL_12E;
				IL_FD:
				result = 3;
				num = 11;
				goto IL_10F;
				IL_B3:
				result = 9;
				num = 7;
				goto IL_C5;
				IL_49:
				result = 5;
				num = 9;
				goto IL_5B;
			}
			return result;
		}

		// Token: 0x060000AD RID: 173 RVA: 0x0001C3DC File Offset: 0x0001A5DC
		private Bitmap method_4(Control control_0, int int_2)
		{
			int num = 0;
			Bitmap bitmap;
			do
			{
				if (num == 2)
				{
					bitmap = new Bitmap(control_0.Width + this.int_1, control_0.Height);
					num = 3;
				}
				if (num == 3)
				{
					if (int_2 < 0)
					{
						break;
					}
					num = 4;
				}
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 4)
				{
					if (3 >= int_2)
					{
						goto IL_D8;
					}
					num = 5;
				}
				if (num == 0)
				{
					num = 1;
				}
			}
			while (num != 5);
			goto IL_DF;
			IL_D8:
			bool flag = true;
			goto IL_F1;
			IL_DF:
			flag = (-1 >= int_2);
			IL_F1:
			if (flag)
			{
				int_2 = 3;
			}
			if (int_2 >= bitmap.Width)
			{
				int_2 = bitmap.Width;
			}
			using (Graphics graphics = Graphics.FromImage(bitmap))
			{
				graphics.InterpolationMode = InterpolationMode.NearestNeighbor;
				graphics.DrawImage(this.bitmap_0, 1 + this.int_1, 1, (bitmap.Width - this.int_1) * 2 - 4, bitmap.Height - 2);
				int num2 = 6;
				if (int_2 >= bitmap.Width / 2)
				{
					num2 = 2;
				}
				graphics.DrawImage(this.bitmap_1, 1 + this.int_1, 1, int_2 * 2 - num2, bitmap.Height - 2);
				using (Brush brush = new SolidBrush(Color.FromArgb(0, 0, 0)))
				{
					Pen pen = new Pen(brush);
					graphics.DrawRectangle(pen, this.int_1, 0, bitmap.Width - this.int_1 - 1, bitmap.Height - 1);
				}
			}
			return bitmap;
		}

		// Token: 0x060000AE RID: 174 RVA: 0x0001C6C0 File Offset: 0x0001A8C0
		protected virtual void SetBoundsCore(int x, int y, int width, int height, BoundsSpecified specified)
		{
			int num = 0;
			for (;;)
			{
				if (num == 4)
				{
					base.SetBoundsCore(x, y, width, 40, specified);
					num = 5;
				}
				if (num != 3)
				{
					goto IL_2A;
				}
				bool designMode;
				if (designMode)
				{
					num = 4;
					goto IL_2A;
				}
				goto IL_75;
				IL_9B:
				if (num == 5)
				{
					break;
				}
				if (num == 0)
				{
					num = 1;
				}
				if (num == 7)
				{
					break;
				}
				continue;
				IL_2A:
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 2)
				{
					designMode = base.DesignMode;
					num = 3;
				}
				if (num != 6)
				{
					goto IL_9B;
				}
				IL_75:
				base.SetBoundsCore(x, y, width, height, specified);
				num = 7;
				goto IL_9B;
			}
		}

		// Token: 0x060000AF RID: 175 RVA: 0x0001821C File Offset: 0x0001641C
		private void method_5(object sender, EventArgs e)
		{
			int num = 0;
			do
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
			}
			while (num != 2);
		}

		// Token: 0x060000B0 RID: 176 RVA: 0x0001C7D8 File Offset: 0x0001A9D8
		private void method_6()
		{
			int num = 0;
			for (;;)
			{
				double num2;
				if (num == 5)
				{
					num2 = this.double_0;
					num = 6;
				}
				if (num == 16)
				{
					this.lbSliderValue.Text = this.string_2 + string.Format("{0:F" + this.int_0.ToString() + "}", this.double_2) + this.string_1;
					num = 17;
				}
				double num4;
				int num5;
				double num3;
				if (num == 14)
				{
					double num6;
					num3 = num4 + (double)num5 * num6;
					num = 15;
				}
				Point point;
				if (num == 4)
				{
					this.lbSliderValue.Location = new Point(point.X + this.method_3(this.lbSliderValue.Text.Length), this.lbSliderValue.Location.Y);
					num = 5;
				}
				if (num != 10)
				{
					goto IL_12B;
				}
				bool flag;
				if (flag)
				{
					num = 11;
					goto IL_12B;
				}
				goto IL_28D;
				IL_2AC:
				if (num == 15)
				{
					this.double_2 = num3;
					num = 16;
				}
				if (num == 7)
				{
					double num6 = (num2 - num4) / (double)(this.pnlSliderBox.Width - 2);
					num = 8;
				}
				if (num == 0)
				{
					num = 1;
				}
				if (num == 17)
				{
					break;
				}
				continue;
				IL_12B:
				if (num == 6)
				{
					num4 = this.double_1;
					num = 7;
				}
				if (num == 9)
				{
					flag = (num5 >= this.pnlSliderBox.Width - 2);
					num = 10;
				}
				if (num == 8)
				{
					num5 = point.X;
					num = 9;
				}
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 3)
				{
					this.pnlSlider.BackgroundImage = this.method_4(this.pnlSliderBox, point.X);
					num = 4;
				}
				if (num == 2)
				{
					point = this.pnlSliderBox.PointToClient(Cursor.Position);
					num = 3;
				}
				if (num == 11)
				{
					num5 = this.pnlSliderBox.Width - 2;
					num = 12;
				}
				bool flag2;
				if (num == 13)
				{
					if (!flag2)
					{
						break;
					}
					num = 14;
				}
				if (num != 12)
				{
					goto IL_2AC;
				}
				IL_28D:
				flag2 = (num5 >= 0);
				num = 13;
				goto IL_2AC;
			}
		}

		// Token: 0x060000B1 RID: 177 RVA: 0x0001CB40 File Offset: 0x0001AD40
		private void skeetSlider_1_Resize(object sender, EventArgs e)
		{
			int num = 0;
			do
			{
				if (num == 3)
				{
					this.pnlSlider.Width = base.Width;
					num = 4;
				}
				if (num == 2)
				{
					this.pnlSliderBox.Width = base.Width - this.int_1 * 2;
					num = 3;
				}
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 4)
				{
					this.method_2();
					num = 5;
				}
				if (num == 0)
				{
					num = 1;
				}
			}
			while (num != 5);
		}

		// Token: 0x060000B2 RID: 178 RVA: 0x0001CC30 File Offset: 0x0001AE30
		private void pnlSliderBox_Click(object sender, EventArgs e)
		{
			int num = 0;
			do
			{
				bool flag;
				if (num == 3)
				{
					Rectangle clientRectangle;
					flag = clientRectangle.Contains(this.pnlSliderBox.PointToClient(Control.MousePosition));
					num = 4;
				}
				if (num == 4)
				{
					if (!flag)
					{
						break;
					}
					num = 5;
				}
				if (num == 2)
				{
					Rectangle clientRectangle = this.pnlSliderBox.ClientRectangle;
					num = 3;
				}
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 5)
				{
					this.method_6();
					num = 6;
				}
				if (num == 0)
				{
					num = 1;
				}
			}
			while (num != 6);
		}

		// Token: 0x060000B3 RID: 179 RVA: 0x0001CD34 File Offset: 0x0001AF34
		private void pnlSliderBox_MouseMove(object sender, MouseEventArgs e)
		{
			int num = 0;
			do
			{
				if (num == 3)
				{
					bool flag;
					if (!flag)
					{
						break;
					}
					num = 4;
				}
				if (num == 7)
				{
					this.method_6();
					num = 8;
				}
				bool flag2;
				if (num == 5)
				{
					Rectangle clientRectangle;
					flag2 = clientRectangle.Contains(this.pnlSliderBox.PointToClient(Control.MousePosition));
					num = 6;
				}
				if (num == 6)
				{
					if (!flag2)
					{
						break;
					}
					num = 7;
				}
				if (num == 2)
				{
					bool flag = e.Button == MouseButtons.Left;
					num = 3;
				}
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 4)
				{
					Rectangle clientRectangle = this.pnlSliderBox.ClientRectangle;
					num = 5;
				}
				if (num == 0)
				{
					num = 1;
				}
			}
			while (num != 8);
		}

		// Token: 0x060000B4 RID: 180 RVA: 0x0001CE7C File Offset: 0x0001B07C
		private void InitializeComponent()
		{
			int num = 0;
			do
			{
				if (num == 39)
				{
					this.lbSliderValue.Color_0 = Color.Black;
					num = 40;
				}
				if (num == 18)
				{
					this.lbTitle.Font = new Font("Consolas", 8f);
					num = 19;
				}
				if (num == 19)
				{
					this.lbTitle.ForeColor = Color.FromArgb(190, 190, 190);
					num = 20;
				}
				if (num == 14)
				{
					this.pnlSlider.Size = new Size(158, 23);
					num = 15;
				}
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 20)
				{
					this.lbTitle.Location = new Point(15, 2);
					num = 21;
				}
				if (num == 17)
				{
					this.lbTitle.Boolean_0 = true;
					num = 18;
				}
				if (num == 40)
				{
					this.lbSliderValue.Single_0 = 0f;
					num = 41;
				}
				if (num == 34)
				{
					this.lbSliderValue.AutoSize = true;
					num = 35;
				}
				if (num == 2)
				{
					this.pnlSlider = new Panel();
					num = 3;
				}
				if (num == 3)
				{
					this.lbTitle = new Class5();
					num = 4;
				}
				if (num == 51)
				{
					base.Resize += this.skeetSlider_1_Resize;
					num = 52;
				}
				if (num == 55)
				{
					base.PerformLayout();
					num = 56;
				}
				if (num == 27)
				{
					this.pnlSliderBox.BackColor = Color.Fuchsia;
					num = 28;
				}
				if (num == 12)
				{
					this.pnlSlider.Location = new Point(0, 17);
					num = 13;
				}
				if (num == 42)
				{
					this.lbSliderValue.TabIndex = 5;
					num = 43;
				}
				if (num == 22)
				{
					this.lbTitle.Color_0 = Color.Black;
					num = 23;
				}
				if (num == 15)
				{
					this.pnlSlider.TabIndex = 1;
					num = 16;
				}
				if (num == 30)
				{
					this.pnlSliderBox.Size = new Size(128, 8);
					num = 31;
				}
				if (num == 33)
				{
					this.pnlSliderBox.MouseMove += this.pnlSliderBox_MouseMove;
					num = 34;
				}
				if (num == 4)
				{
					this.pnlSliderBox = new Class4();
					num = 5;
				}
				if (num == 31)
				{
					this.pnlSliderBox.TabIndex = 6;
					num = 32;
				}
				if (num == 10)
				{
					this.pnlSlider.Controls.Add(this.pnlSliderBox);
					num = 11;
				}
				if (num == 16)
				{
					this.lbTitle.AutoSize = true;
					num = 17;
				}
				if (num == 5)
				{
					this.lbSliderValue = new Class3();
					num = 6;
				}
				if (num == 48)
				{
					this.Font = new Font("Consolas", 8.5f, FontStyle.Bold);
					num = 49;
				}
				if (num == 37)
				{
					this.lbSliderValue.Location = new Point(65, 0);
					num = 38;
				}
				if (num == 25)
				{
					this.lbTitle.TabIndex = 7;
					num = 26;
				}
				if (num == 50)
				{
					base.Size = new Size(158, 40);
					num = 51;
				}
				if (num == 44)
				{
					this.lbSliderValue.TextAlign = ContentAlignment.TopCenter;
					num = 45;
				}
				if (num == 45)
				{
					this.BackColor = Color.FromArgb(23, 23, 23);
					num = 46;
				}
				if (num == 36)
				{
					this.lbSliderValue.ForeColor = Color.FromArgb(255, 255, 255);
					num = 37;
				}
				if (num == 29)
				{
					this.pnlSliderBox.Name = "pnlSliderBox";
					num = 30;
				}
				if (num == 7)
				{
					base.SuspendLayout();
					num = 8;
				}
				if (num == 32)
				{
					this.pnlSliderBox.Click += this.pnlSliderBox_Click;
					num = 33;
				}
				if (num == 8)
				{
					this.pnlSlider.BackColor = Color.Transparent;
					num = 9;
				}
				if (num == 21)
				{
					this.lbTitle.Name = "lbTitle";
					num = 22;
				}
				if (num == 47)
				{
					base.Controls.Add(this.pnlSlider);
					num = 48;
				}
				if (num == 53)
				{
					this.pnlSlider.PerformLayout();
					num = 54;
				}
				if (num == 52)
				{
					this.pnlSlider.ResumeLayout(false);
					num = 53;
				}
				if (num == 26)
				{
					this.lbTitle.Text = "Slider";
					num = 27;
				}
				if (num == 35)
				{
					this.lbSliderValue.Font = new Font("Consolas", 8.5f);
					num = 36;
				}
				if (num == 11)
				{
					this.pnlSlider.Controls.Add(this.lbSliderValue);
					num = 12;
				}
				if (num == 23)
				{
					this.lbTitle.Int32_0 = 1;
					num = 24;
				}
				if (num == 49)
				{
					base.Name = "skeetSlider";
					num = 50;
				}
				if (num == 46)
				{
					base.Controls.Add(this.lbTitle);
					num = 47;
				}
				if (num == 38)
				{
					this.lbSliderValue.Name = "lbSliderValue";
					num = 39;
				}
				if (num == 9)
				{
					this.pnlSlider.BackgroundImageLayout = ImageLayout.None;
					num = 10;
				}
				if (num == 6)
				{
					this.pnlSlider.SuspendLayout();
					num = 7;
				}
				if (num == 43)
				{
					this.lbSliderValue.Text = "1.523";
					num = 44;
				}
				if (num == 28)
				{
					this.pnlSliderBox.Location = new Point(14, 0);
					num = 29;
				}
				if (num == 13)
				{
					this.pnlSlider.Name = "pnlSlider";
					num = 14;
				}
				if (num == 24)
				{
					this.lbTitle.Size = new Size(53, 12);
					num = 25;
				}
				if (num == 54)
				{
					base.ResumeLayout(false);
					num = 55;
				}
				if (num == 41)
				{
					this.lbSliderValue.Size = new Size(37, 12);
					num = 42;
				}
				if (num == 0)
				{
					num = 1;
				}
			}
			while (num != 56);
		}

		// Token: 0x060000B5 RID: 181 RVA: 0x0001821C File Offset: 0x0001641C
		private void method_7(object sender, EventArgs e)
		{
			int num = 0;
			do
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
			}
			while (num != 2);
		}

		// Token: 0x0400007C RID: 124
		private bool bool_0;

		// Token: 0x0400007D RID: 125
		private bool bool_1;

		// Token: 0x0400007E RID: 126
		private string string_0;

		// Token: 0x0400007F RID: 127
		private bool bool_2;

		// Token: 0x04000080 RID: 128
		private string string_1;

		// Token: 0x04000081 RID: 129
		private string string_2;

		// Token: 0x04000082 RID: 130
		private Color color_0;

		// Token: 0x04000083 RID: 131
		private Color color_1;

		// Token: 0x04000084 RID: 132
		private double double_0;

		// Token: 0x04000085 RID: 133
		private double double_1;

		// Token: 0x04000086 RID: 134
		private double double_2;

		// Token: 0x04000087 RID: 135
		private int int_0;

		// Token: 0x04000088 RID: 136
		private Bitmap bitmap_0;

		// Token: 0x04000089 RID: 137
		private Bitmap bitmap_1;

		// Token: 0x0400008A RID: 138
		private int int_1;

		// Token: 0x0400008B RID: 139
		private Panel panel_0;

		// Token: 0x0400008C RID: 140
		private Panel pnlSlider;

		// Token: 0x0400008D RID: 141
		private Class3 lbSliderValue;

		// Token: 0x0400008E RID: 142
		private Class4 pnlSliderBox;

		// Token: 0x0400008F RID: 143
		private Class5 lbTitle;
	}
}

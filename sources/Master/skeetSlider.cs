using System;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Reflection;
using System.Windows.Forms;

namespace Client
{
	// Token: 0x0200000A RID: 10
	public class skeetSlider : UserControl
	{
		// Token: 0x17000004 RID: 4
		// (get) Token: 0x06000054 RID: 84 RVA: 0x00015B54 File Offset: 0x00013D54
		// (set) Token: 0x06000055 RID: 85 RVA: 0x00015BE4 File Offset: 0x00013DE4
		public bool Boolean_0
		{
			get
			{
				int num = 0;
				bool result;
				do
				{
					if (num == 2)
					{
						result = this.bool_1;
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
					if (num != 6)
					{
						goto IL_0E;
					}
					if (value)
					{
						num = 7;
						goto IL_0E;
					}
					goto IL_5C;
					IL_77:
					Color foreColor;
					if (num == 4)
					{
						foreColor = Color.FromArgb(190, 190, 190);
						num = 5;
					}
					Color black;
					if (num == 7)
					{
						this.lbTitle.ForeColor = black;
						num = 8;
					}
					if (num == 3)
					{
						black = Color.Black;
						num = 4;
					}
					if (num == 1)
					{
						<Module>.Class0.smethod_0();
						num = 2;
					}
					if (num == 11)
					{
						this.lbTitle.Color_0 = black;
						num = 12;
					}
					if (num == 8)
					{
						this.lbTitle.Color_0 = foreColor;
						num = 9;
					}
					if (num == 9)
					{
						break;
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
					if (num == 2)
					{
						this.bool_1 = value;
						num = 3;
					}
					if (num != 10)
					{
						goto IL_77;
					}
					IL_5C:
					this.lbTitle.ForeColor = foreColor;
					num = 11;
					goto IL_77;
				}
			}
		}

		// Token: 0x17000005 RID: 5
		// (get) Token: 0x06000056 RID: 86 RVA: 0x00015DA4 File Offset: 0x00013FA4
		// (set) Token: 0x06000057 RID: 87 RVA: 0x00015E34 File Offset: 0x00014034
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
					if (num == 2)
					{
						this.bool_0 = value;
						num = 3;
					}
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
					if (num == 0)
					{
						num = 1;
					}
				}
				while (num != 4);
			}
		}

		// Token: 0x17000006 RID: 6
		// (get) Token: 0x06000058 RID: 88 RVA: 0x00015EE4 File Offset: 0x000140E4
		// (set) Token: 0x06000059 RID: 89 RVA: 0x00015F70 File Offset: 0x00014170
		public string String_0
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
						result = this.string_0;
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
				for (;;)
				{
					if (num == 5)
					{
						goto IL_4B;
					}
					IL_7D:
					if (num == 1)
					{
						<Module>.Class0.smethod_0();
						num = 2;
					}
					bool flag;
					if (num == 2)
					{
						flag = !string.IsNullOrEmpty(value);
						num = 3;
					}
					if (num == 3)
					{
						if (!flag)
						{
							goto IL_4B;
						}
						num = 4;
					}
					if (num == 4)
					{
						this.string_0 = value;
						num = 5;
					}
					if (num == 0)
					{
						num = 1;
					}
					if (num != 6)
					{
						continue;
					}
					break;
					IL_4B:
					this.lbTitle.Text = this.string_0;
					num = 6;
					goto IL_7D;
				}
			}
		}

		// Token: 0x17000007 RID: 7
		// (get) Token: 0x0600005A RID: 90 RVA: 0x0001606C File Offset: 0x0001426C
		// (set) Token: 0x0600005B RID: 91 RVA: 0x000160FC File Offset: 0x000142FC
		public bool Boolean_2
		{
			get
			{
				int num = 0;
				bool result;
				do
				{
					if (num == 2)
					{
						result = this.bool_2;
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
					if (num == 3)
					{
						this.lbSliderValue.Visible = value;
						num = 4;
					}
					if (num == 0)
					{
						num = 1;
					}
				}
				while (num != 4);
			}
		}

		// Token: 0x17000008 RID: 8
		// (get) Token: 0x0600005C RID: 92 RVA: 0x000161AC File Offset: 0x000143AC
		// (set) Token: 0x0600005D RID: 93 RVA: 0x00016238 File Offset: 0x00014438
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
					if (num == 2)
					{
						this.string_1 = value;
						num = 3;
					}
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
					if (num == 0)
					{
						num = 1;
					}
				}
				while (num != 4);
			}
		}

		// Token: 0x17000009 RID: 9
		// (get) Token: 0x0600005E RID: 94 RVA: 0x000162DC File Offset: 0x000144DC
		// (set) Token: 0x0600005F RID: 95 RVA: 0x0001636C File Offset: 0x0001456C
		public string String_2
		{
			get
			{
				int num = 0;
				string result;
				do
				{
					if (num == 2)
					{
						result = this.string_2;
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

		// Token: 0x1700000A RID: 10
		// (get) Token: 0x06000060 RID: 96 RVA: 0x00016410 File Offset: 0x00014610
		// (set) Token: 0x06000061 RID: 97 RVA: 0x000164A0 File Offset: 0x000146A0
		public Color Color_0
		{
			get
			{
				int num = 0;
				Color result;
				do
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

		// Token: 0x1700000B RID: 11
		// (get) Token: 0x06000062 RID: 98 RVA: 0x00016544 File Offset: 0x00014744
		// (set) Token: 0x06000063 RID: 99 RVA: 0x000165D4 File Offset: 0x000147D4
		public Color Color_1
		{
			get
			{
				int num = 0;
				Color result;
				do
				{
					if (num == 2)
					{
						result = this.color_1;
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
						this.method_0();
						num = 4;
					}
					if (num == 1)
					{
						<Module>.Class0.smethod_0();
						num = 2;
					}
					if (num == 2)
					{
						this.color_1 = value;
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

		// Token: 0x1700000C RID: 12
		// (get) Token: 0x06000064 RID: 100 RVA: 0x00016678 File Offset: 0x00014878
		// (set) Token: 0x06000065 RID: 101 RVA: 0x00016708 File Offset: 0x00014908
		public int Int32_0
		{
			get
			{
				int num = 0;
				int result;
				do
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
				for (;;)
				{
					if (num == 4)
					{
						this.int_0 = value;
						num = 5;
					}
					if (num == 5)
					{
						goto IL_30;
					}
					IL_65:
					bool flag;
					if (num == 2)
					{
						flag = (value >= 0);
						num = 3;
					}
					if (num == 3)
					{
						if (!flag)
						{
							goto IL_30;
						}
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
					if (num == 6)
					{
						break;
					}
					continue;
					IL_30:
					this.method_1();
					num = 6;
					goto IL_65;
				}
			}
		}

		// Token: 0x1700000D RID: 13
		// (get) Token: 0x06000066 RID: 102 RVA: 0x00016800 File Offset: 0x00014A00
		// (set) Token: 0x06000067 RID: 103 RVA: 0x0001688C File Offset: 0x00014A8C
		public double Double_0
		{
			get
			{
				int num = 0;
				double result;
				while (num != 3)
				{
					if (num == 1)
					{
						<Module>.Class0.smethod_0();
						num = 2;
					}
					if (num == 2)
					{
						result = this.double_2;
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
					if (num == 1)
					{
						<Module>.Class0.smethod_0();
						num = 2;
					}
					if (num == 2)
					{
						num = 3;
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

		// Token: 0x1700000E RID: 14
		// (get) Token: 0x06000068 RID: 104 RVA: 0x00016970 File Offset: 0x00014B70
		// (set) Token: 0x06000069 RID: 105 RVA: 0x000169FC File Offset: 0x00014BFC
		public double Double_1
		{
			get
			{
				int num = 0;
				double result;
				while (num != 3)
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
					if (num != 4)
					{
						goto IL_0E;
					}
					bool flag;
					if (flag)
					{
						num = 5;
						goto IL_0E;
					}
					goto IL_106;
					IL_16D:
					if (num == 6)
					{
						bool flag2;
						if (!flag2)
						{
							goto IL_183;
						}
						num = 7;
					}
					if (num == 14)
					{
						break;
					}
					if (num == 7)
					{
						this.double_0 = value;
						num = 8;
					}
					if (num == 10)
					{
						this.double_2 = value;
						num = 11;
					}
					double num2;
					if (num == 3)
					{
						flag = (num2 >= this.double_1);
						num = 4;
					}
					if (num == 0)
					{
						num = 1;
					}
					if (num == 16)
					{
						break;
					}
					continue;
					IL_1EA:
					if (num == 12)
					{
						break;
					}
					if (num == 2)
					{
						num2 = value;
						num = 3;
					}
					if (num == 11)
					{
						goto IL_BD;
					}
					IL_E1:
					if (num == 1)
					{
						<Module>.Class0.smethod_0();
						num = 2;
					}
					if (num == 9)
					{
						bool flag3;
						if (!flag3)
						{
							goto IL_BD;
						}
						num = 10;
					}
					if (num == 15)
					{
						goto IL_106;
					}
					goto IL_16D;
					IL_BD:
					this.method_1();
					num = 12;
					goto IL_E1;
					IL_0E:
					if (num == 8)
					{
						bool flag3 = !base.DesignMode;
						num = 9;
					}
					if (num == 5)
					{
						bool flag2 = num2 >= this.double_2;
						num = 6;
					}
					if (num == 13)
					{
						goto IL_183;
					}
					goto IL_1EA;
					IL_106:
					MessageBox.Show(string.Concat(new string[]
					{
						"Max value can't be lower than minimum value (",
						num2.ToString(),
						">",
						this.double_1.ToString(),
						")"
					}));
					num = 16;
					goto IL_16D;
					IL_183:
					MessageBox.Show(string.Concat(new string[]
					{
						"Current value can't be higher than maximum value (",
						this.double_2.ToString(),
						">",
						num2.ToString(),
						")"
					}));
					num = 14;
					goto IL_1EA;
				}
			}
		}

		// Token: 0x1700000F RID: 15
		// (get) Token: 0x0600006A RID: 106 RVA: 0x00016CE8 File Offset: 0x00014EE8
		// (set) Token: 0x0600006B RID: 107 RVA: 0x00016D78 File Offset: 0x00014F78
		public double Double_2
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
					if (num == 3)
					{
						break;
					}
					if (num == 2)
					{
						result = this.double_1;
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
					if (num == 12)
					{
						goto IL_14;
					}
					IL_7B:
					if (num == 4)
					{
						bool flag;
						if (!flag)
						{
							goto IL_14;
						}
						num = 5;
					}
					if (num != 6)
					{
						goto IL_B2;
					}
					bool flag2;
					if (flag2)
					{
						num = 7;
						goto IL_B2;
					}
					goto IL_15C;
					IL_1C3:
					double num2;
					if (num == 2)
					{
						num2 = value;
						num = 3;
					}
					if (num == 5)
					{
						flag2 = (this.double_2 >= num2);
						num = 6;
					}
					if (num == 11)
					{
						break;
					}
					if (num == 0)
					{
						num = 1;
					}
					if (num != 13)
					{
						continue;
					}
					break;
					IL_B2:
					if (num == 8)
					{
						this.method_1();
						num = 9;
					}
					if (num == 1)
					{
						<Module>.Class0.smethod_0();
						num = 2;
					}
					if (num == 7)
					{
						this.double_1 = value;
						num = 8;
					}
					if (num == 9)
					{
						break;
					}
					if (num == 3)
					{
						bool flag = num2 <= this.double_0;
						num = 4;
					}
					if (num != 10)
					{
						goto IL_1C3;
					}
					IL_15C:
					MessageBox.Show(string.Concat(new string[]
					{
						"Current value can't be lower than minimum value (",
						num2.ToString(),
						">",
						this.double_2.ToString(),
						")"
					}));
					num = 11;
					goto IL_1C3;
					IL_14:
					MessageBox.Show(string.Concat(new string[]
					{
						"Minimum value can't be higher than maximum value (",
						num2.ToString(),
						">",
						this.double_0.ToString(),
						")"
					}));
					num = 13;
					goto IL_7B;
				}
			}
		}

		// Token: 0x0600006C RID: 108 RVA: 0x00016FDC File Offset: 0x000151DC
		public static Color smethod_0(Color color_2, float float_0)
		{
			int num = 0;
			Color result;
			for (;;)
			{
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				float num2;
				if (num == 13)
				{
					num2 = (255f - num2) * float_0 + num2;
					num = 14;
				}
				if (num == 12)
				{
					goto IL_81;
				}
				IL_BC:
				if (num == 9)
				{
					num2 *= float_0;
					num = 10;
				}
				float num3;
				if (num == 8)
				{
					num3 *= float_0;
					num = 9;
				}
				if (num == 6)
				{
					bool flag;
					if (!flag)
					{
						goto IL_81;
					}
					num = 7;
				}
				float num4;
				if (num == 10)
				{
					num4 *= float_0;
					num = 11;
				}
				if (num == 2)
				{
					num3 = (float)color_2.R;
					num = 3;
				}
				if (num == 7)
				{
					float_0 = 1f + float_0;
					num = 8;
				}
				if (num == 15)
				{
					goto IL_18C;
				}
				IL_1D5:
				if (num == 5)
				{
					bool flag = float_0 < 0f;
					num = 6;
				}
				if (num == 14)
				{
					num4 = (255f - num4) * float_0 + num4;
					num = 15;
				}
				if (num != 11)
				{
					if (num == 16)
					{
						break;
					}
					if (num == 4)
					{
						num4 = (float)color_2.B;
						num = 5;
					}
					if (num == 3)
					{
						num2 = (float)color_2.G;
						num = 4;
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
				}
				IL_18C:
				result = Color.FromArgb((int)color_2.A, (int)num3, (int)num2, (int)num4);
				num = 16;
				goto IL_1D5;
				IL_81:
				num3 = (255f - num3) * float_0 + num3;
				num = 13;
				goto IL_BC;
			}
			return result;
		}

		// Token: 0x0600006D RID: 109 RVA: 0x00017278 File Offset: 0x00015478
		private void method_0()
		{
			int num = 0;
			for (;;)
			{
				int num2;
				if (num == 10)
				{
					num2++;
					num = 11;
				}
				float num3;
				if (num == 5)
				{
					num3 = 0.02f;
					num = 6;
				}
				if (num != 12)
				{
					goto IL_49;
				}
				bool flag;
				if (!flag)
				{
					num = 13;
					goto IL_49;
				}
				goto IL_9C;
				IL_CE:
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 6)
				{
					goto IL_1D6;
				}
				if (num == 4)
				{
					num2 = 1;
					num = 5;
				}
				if (num == 9)
				{
					num3 += 0.02f;
					num = 10;
				}
				if (num == 2)
				{
					this.bitmap_0.SetPixel(0, 0, this.color_1);
					num = 3;
				}
				if (num == 3)
				{
					this.bitmap_1.SetPixel(0, 0, this.color_0);
					num = 4;
				}
				if (num == 13)
				{
					this.method_2();
					num = 14;
				}
				if (num == 11)
				{
					goto IL_1D6;
				}
				IL_1F5:
				if (num == 0)
				{
					num = 1;
				}
				if (num == 14)
				{
					break;
				}
				continue;
				IL_1D6:
				flag = (0.1f >= num3);
				num = 12;
				goto IL_1F5;
				IL_49:
				if (num == 8)
				{
					this.bitmap_1.SetPixel(0, num2, skeetSlider.smethod_0(this.color_0, -num3 * 2f));
					num = 9;
				}
				if (num != 7)
				{
					goto IL_CE;
				}
				IL_9C:
				this.bitmap_0.SetPixel(0, num2, skeetSlider.smethod_0(this.color_1, num3));
				num = 8;
				goto IL_CE;
			}
		}

		// Token: 0x0600006E RID: 110 RVA: 0x000174D0 File Offset: 0x000156D0
		private void method_1()
		{
			int num = 0;
			for (;;)
			{
				if (num == 16)
				{
					goto IL_2A9;
				}
				IL_362:
				string text;
				if (num == 3)
				{
					text = this.string_2 + string.Format("{0:F" + this.int_0.ToString() + "}", this.double_0) + this.string_1;
					num = 4;
				}
				if (num == 18)
				{
					this.method_2();
					num = 19;
				}
				Size size;
				if (num == 5)
				{
					Font font;
					size = TextRenderer.MeasureText(text, font);
					num = 6;
				}
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 13)
				{
					goto IL_253;
				}
				IL_297:
				if (num == 14)
				{
					this.pnlSliderBox.Location = new Point(15, 0);
					num = 15;
				}
				int num2;
				if (num == 9)
				{
					this.pnlSliderBox.Location = new Point(num2, this.pnlSliderBox.Location.Y);
					num = 10;
				}
				if (num == 15)
				{
					this.lbTitle.Location = new Point(15, 1);
					num = 16;
				}
				if (num == 2)
				{
					this.int_1 = 0;
					num = 3;
				}
				if (num == 17)
				{
					this.pnlSliderBox.Width = base.Width - this.int_1 * 2;
					num = 18;
				}
				if (num == 11)
				{
					this.int_1 = num2;
					num = 12;
				}
				if (num == 8)
				{
					num2 = size.Width / 2;
					num = 9;
				}
				bool flag;
				if (num == 6)
				{
					flag = (size.Width / 2 > 15);
					num = 7;
				}
				if (num == 12)
				{
					goto IL_2A9;
				}
				if (num == 10)
				{
					this.lbTitle.Location = new Point(num2 + 1, this.lbTitle.Location.Y);
					num = 11;
				}
				if (num == 7)
				{
					if (!flag)
					{
						goto IL_253;
					}
					num = 8;
				}
				if (num == 4)
				{
					Font font = new Font("Verdana", 7.2f, FontStyle.Bold);
					num = 5;
				}
				if (num == 0)
				{
					num = 1;
				}
				if (num != 19)
				{
					continue;
				}
				break;
				IL_253:
				this.int_1 = 15;
				num = 14;
				goto IL_297;
				IL_2A9:
				this.lbSliderValue.Text = this.string_2 + string.Format("{0:F" + this.int_0.ToString() + "}", this.double_2) + this.string_1;
				num = 17;
				goto IL_362;
			}
		}

		// Token: 0x0600006F RID: 111 RVA: 0x000178C0 File Offset: 0x00015AC0
		public skeetSlider()
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
			this.double_0 = 2.0;
			this.double_1 = 1.0;
			this.double_2 = 1.5;
			this.int_0 = 2;
			this.bitmap_0 = new Bitmap(1, 6);
			this.bitmap_1 = new Bitmap(1, 6);
			this.int_1 = 0;
			base..ctor();
			this.InitializeComponent();
			skeetSlider.smethod_1(this.pnlSlider);
			this.method_0();
			this.method_1();
			this.MinimumSize = new Size(100, 40);
		}

		// Token: 0x06000070 RID: 112 RVA: 0x000179B8 File Offset: 0x00015BB8
		private void method_2()
		{
			int num = 0;
			do
			{
				double value;
				if (num == 6)
				{
					this.lbSliderValue.Location = new Point(Convert.ToInt32(value) + this.method_3(this.lbSliderValue.Text.Length), this.lbSliderValue.Location.Y);
					num = 7;
				}
				double num2;
				if (num == 3)
				{
					double num3;
					num2 = num3 / (double)(this.pnlSliderBox.Width - 2);
					num = 4;
				}
				if (num == 2)
				{
					double num3 = this.double_0 - this.double_1;
					num = 3;
				}
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
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

		// Token: 0x06000071 RID: 113 RVA: 0x00017B5C File Offset: 0x00015D5C
		public static void smethod_1(Control control_0)
		{
			int num = 0;
			for (;;)
			{
				if (num != 3)
				{
					goto IL_0E;
				}
				bool terminalServerSession;
				if (terminalServerSession)
				{
					num = 4;
					goto IL_0E;
				}
				goto IL_93;
				IL_B9:
				if (num == 0)
				{
					num = 1;
				}
				if (num == 7)
				{
					break;
				}
				continue;
				IL_0E:
				if (num == 4)
				{
					break;
				}
				if (num == 2)
				{
					terminalServerSession = SystemInformation.TerminalServerSession;
					num = 3;
				}
				PropertyInfo property;
				if (num == 6)
				{
					property.SetValue(control_0, true, null);
					num = 7;
				}
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num != 5)
				{
					goto IL_B9;
				}
				IL_93:
				property = typeof(Control).GetProperty("DoubleBuffered", BindingFlags.Instance | BindingFlags.NonPublic);
				num = 6;
				goto IL_B9;
			}
		}

		// Token: 0x06000072 RID: 114 RVA: 0x00017C70 File Offset: 0x00015E70
		private int method_3(int int_2)
		{
			int num = 0;
			int result;
			for (;;)
			{
				if (num == 6)
				{
					goto IL_2B;
				}
				IL_3D:
				if (num != 4)
				{
					goto IL_55;
				}
				int num2;
				switch (num2)
				{
				case 1:
					goto IL_2B;
				case 2:
					goto IL_81;
				case 3:
					goto IL_62;
				default:
					num = 5;
					goto IL_55;
				}
				IL_93:
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 3)
				{
					num2 = int_2;
					num = 4;
				}
				if (num == 2)
				{
					num = 3;
				}
				if (num != 13)
				{
					if (num == 12)
					{
						goto IL_123;
					}
					IL_135:
					if (num == 11)
					{
						break;
					}
					if (num == 9)
					{
						break;
					}
					if (num != 5)
					{
						if (num == 7)
						{
							break;
						}
						if (num == 0)
						{
							num = 1;
						}
						if (num != 14)
						{
							continue;
						}
						break;
					}
					IL_123:
					result = 1;
					num = 13;
					goto IL_135;
				}
				break;
				IL_74:
				if (num == 8)
				{
					goto IL_81;
				}
				goto IL_93;
				IL_55:
				if (num == 10)
				{
					goto IL_62;
				}
				goto IL_74;
				IL_81:
				result = 5;
				num = 9;
				goto IL_93;
				IL_62:
				result = 3;
				num = 11;
				goto IL_74;
				IL_2B:
				result = 9;
				num = 7;
				goto IL_3D;
			}
			return result;
		}

		// Token: 0x06000073 RID: 115 RVA: 0x00017E0C File Offset: 0x0001600C
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
				if (num == 3)
				{
					if (int_2 < 0)
					{
						break;
					}
					num = 4;
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

		// Token: 0x06000074 RID: 116 RVA: 0x000180F0 File Offset: 0x000162F0
		protected virtual void SetBoundsCore(int x, int y, int width, int height, BoundsSpecified specified)
		{
			int num = 0;
			for (;;)
			{
				bool designMode;
				if (num == 2)
				{
					designMode = base.DesignMode;
					num = 3;
				}
				if (num == 6)
				{
					goto IL_5E;
				}
				IL_B2:
				if (num == 4)
				{
					base.SetBoundsCore(x, y, width, 40, specified);
					num = 5;
				}
				if (num == 5)
				{
					break;
				}
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 3)
				{
					if (!designMode)
					{
						goto IL_5E;
					}
					num = 4;
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
				IL_5E:
				base.SetBoundsCore(x, y, width, height, specified);
				num = 7;
				goto IL_B2;
			}
		}

		// Token: 0x06000075 RID: 117 RVA: 0x0001821C File Offset: 0x0001641C
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

		// Token: 0x06000076 RID: 118 RVA: 0x00018278 File Offset: 0x00016478
		private void method_6()
		{
			int num = 0;
			for (;;)
			{
				Point point;
				if (num == 3)
				{
					this.pnlSlider.BackgroundImage = this.method_4(this.pnlSliderBox, point.X);
					num = 4;
				}
				bool flag;
				if (num == 13)
				{
					if (!flag)
					{
						break;
					}
					num = 14;
				}
				if (num != 10)
				{
					goto IL_4C;
				}
				bool flag2;
				if (flag2)
				{
					num = 11;
					goto IL_4C;
				}
				goto IL_1CE;
				IL_1ED:
				int num2;
				if (num == 8)
				{
					num2 = point.X;
					num = 9;
				}
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 16)
				{
					this.lbSliderValue.Text = this.string_2 + string.Format("{0:F" + this.int_0.ToString() + "}", this.double_2) + this.string_1;
					num = 17;
				}
				if (num == 14)
				{
					double num4;
					double num5;
					double num3 = num4 + (double)num2 * num5;
					num = 15;
				}
				if (num == 2)
				{
					point = this.pnlSliderBox.PointToClient(Cursor.Position);
					num = 3;
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
				IL_4C:
				if (num == 15)
				{
					double num3;
					this.double_2 = num3;
					num = 16;
				}
				double num6;
				if (num == 5)
				{
					num6 = this.double_0;
					num = 6;
				}
				if (num == 7)
				{
					double num4;
					double num5 = (num6 - num4) / (double)(this.pnlSliderBox.Width - 2);
					num = 8;
				}
				if (num == 11)
				{
					num2 = this.pnlSliderBox.Width - 2;
					num = 12;
				}
				if (num == 4)
				{
					this.lbSliderValue.Location = new Point(point.X + this.method_3(this.lbSliderValue.Text.Length), this.lbSliderValue.Location.Y);
					num = 5;
				}
				if (num == 9)
				{
					flag2 = (num2 >= this.pnlSliderBox.Width - 2);
					num = 10;
				}
				if (num == 6)
				{
					double num4 = this.double_1;
					num = 7;
				}
				if (num != 12)
				{
					goto IL_1ED;
				}
				IL_1CE:
				flag = (num2 >= 0);
				num = 13;
				goto IL_1ED;
			}
		}

		// Token: 0x06000077 RID: 119 RVA: 0x000185E0 File Offset: 0x000167E0
		private void skeetSlider_Resize(object sender, EventArgs e)
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
				if (num == 4)
				{
					this.method_2();
					num = 5;
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
			while (num != 5);
		}

		// Token: 0x06000078 RID: 120 RVA: 0x000186D0 File Offset: 0x000168D0
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
				if (num == 5)
				{
					this.method_6();
					num = 6;
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
				if (num == 4)
				{
					if (!flag)
					{
						break;
					}
					num = 5;
				}
				if (num == 0)
				{
					num = 1;
				}
			}
			while (num != 6);
		}

		// Token: 0x06000079 RID: 121 RVA: 0x000187D0 File Offset: 0x000169D0
		private void pnlSliderBox_MouseMove(object sender, MouseEventArgs e)
		{
			int num = 0;
			do
			{
				bool flag;
				if (num == 5)
				{
					Rectangle clientRectangle;
					flag = clientRectangle.Contains(this.pnlSliderBox.PointToClient(Control.MousePosition));
					num = 6;
				}
				if (num == 4)
				{
					Rectangle clientRectangle = this.pnlSliderBox.ClientRectangle;
					num = 5;
				}
				if (num == 7)
				{
					this.method_6();
					num = 8;
				}
				if (num == 6)
				{
					if (!flag)
					{
						break;
					}
					num = 7;
				}
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 3)
				{
					bool flag2;
					if (!flag2)
					{
						break;
					}
					num = 4;
				}
				if (num == 2)
				{
					bool flag2 = e.Button == MouseButtons.Left;
					num = 3;
				}
				if (num == 0)
				{
					num = 1;
				}
			}
			while (num != 8);
		}

		// Token: 0x0600007A RID: 122 RVA: 0x0001891C File Offset: 0x00016B1C
		private void InitializeComponent()
		{
			int num = 0;
			do
			{
				if (num == 21)
				{
					this.lbTitle.Name = "lbTitle";
					num = 22;
				}
				if (num == 8)
				{
					this.pnlSlider.BackColor = Color.Transparent;
					num = 9;
				}
				if (num == 27)
				{
					this.pnlSliderBox.BackColor = Color.Fuchsia;
					num = 28;
				}
				if (num == 46)
				{
					base.Controls.Add(this.lbTitle);
					num = 47;
				}
				if (num == 11)
				{
					this.pnlSlider.Controls.Add(this.lbSliderValue);
					num = 12;
				}
				if (num == 37)
				{
					this.lbSliderValue.Location = new Point(65, 0);
					num = 38;
				}
				if (num == 33)
				{
					this.pnlSliderBox.MouseMove += this.pnlSliderBox_MouseMove;
					num = 34;
				}
				if (num == 15)
				{
					this.pnlSlider.TabIndex = 1;
					num = 16;
				}
				if (num == 5)
				{
					this.lbSliderValue = new Class3();
					num = 6;
				}
				if (num == 30)
				{
					this.pnlSliderBox.Size = new Size(128, 8);
					num = 31;
				}
				if (num == 10)
				{
					this.pnlSlider.Controls.Add(this.pnlSliderBox);
					num = 11;
				}
				if (num == 42)
				{
					this.lbSliderValue.TabIndex = 5;
					num = 43;
				}
				if (num == 39)
				{
					this.lbSliderValue.Color_0 = Color.Black;
					num = 40;
				}
				if (num == 3)
				{
					this.lbTitle = new Class5();
					num = 4;
				}
				if (num == 44)
				{
					this.lbSliderValue.TextAlign = ContentAlignment.TopCenter;
					num = 45;
				}
				if (num == 26)
				{
					this.lbTitle.Text = "Slider";
					num = 27;
				}
				if (num == 52)
				{
					this.pnlSlider.ResumeLayout(false);
					num = 53;
				}
				if (num == 31)
				{
					this.pnlSliderBox.TabIndex = 6;
					num = 32;
				}
				if (num == 29)
				{
					this.pnlSliderBox.Name = "pnlSliderBox";
					num = 30;
				}
				if (num == 49)
				{
					base.Name = "skeetSlider";
					num = 50;
				}
				if (num == 4)
				{
					this.pnlSliderBox = new Class4();
					num = 5;
				}
				if (num == 50)
				{
					base.Size = new Size(158, 40);
					num = 51;
				}
				if (num == 16)
				{
					this.lbTitle.AutoSize = true;
					num = 17;
				}
				if (num == 45)
				{
					this.BackColor = Color.FromArgb(23, 23, 23);
					num = 46;
				}
				if (num == 18)
				{
					this.lbTitle.Font = new Font("Consolas", 8f);
					num = 19;
				}
				if (num == 9)
				{
					this.pnlSlider.BackgroundImageLayout = ImageLayout.None;
					num = 10;
				}
				if (num == 47)
				{
					base.Controls.Add(this.pnlSlider);
					num = 48;
				}
				if (num == 51)
				{
					base.Resize += this.skeetSlider_Resize;
					num = 52;
				}
				if (num == 55)
				{
					base.PerformLayout();
					num = 56;
				}
				if (num == 12)
				{
					this.pnlSlider.Location = new Point(0, 17);
					num = 13;
				}
				if (num == 43)
				{
					this.lbSliderValue.Text = "1.523";
					num = 44;
				}
				if (num == 23)
				{
					this.lbTitle.Int32_0 = 1;
					num = 24;
				}
				if (num == 28)
				{
					this.pnlSliderBox.Location = new Point(14, 0);
					num = 29;
				}
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 32)
				{
					this.pnlSliderBox.Click += this.pnlSliderBox_Click;
					num = 33;
				}
				if (num == 20)
				{
					this.lbTitle.Location = new Point(15, 2);
					num = 21;
				}
				if (num == 25)
				{
					this.lbTitle.TabIndex = 7;
					num = 26;
				}
				if (num == 34)
				{
					this.lbSliderValue.AutoSize = true;
					num = 35;
				}
				if (num == 22)
				{
					this.lbTitle.Color_0 = Color.Black;
					num = 23;
				}
				if (num == 14)
				{
					this.pnlSlider.Size = new Size(158, 23);
					num = 15;
				}
				if (num == 48)
				{
					this.Font = new Font("Consolas", 8.5f, FontStyle.Bold);
					num = 49;
				}
				if (num == 17)
				{
					this.lbTitle.Boolean_0 = true;
					num = 18;
				}
				if (num == 53)
				{
					this.pnlSlider.PerformLayout();
					num = 54;
				}
				if (num == 38)
				{
					this.lbSliderValue.Name = "lbSliderValue";
					num = 39;
				}
				if (num == 19)
				{
					this.lbTitle.ForeColor = Color.FromArgb(190, 190, 190);
					num = 20;
				}
				if (num == 40)
				{
					this.lbSliderValue.Single_0 = 0f;
					num = 41;
				}
				if (num == 7)
				{
					base.SuspendLayout();
					num = 8;
				}
				if (num == 13)
				{
					this.pnlSlider.Name = "pnlSlider";
					num = 14;
				}
				if (num == 41)
				{
					this.lbSliderValue.Size = new Size(37, 12);
					num = 42;
				}
				if (num == 6)
				{
					this.pnlSlider.SuspendLayout();
					num = 7;
				}
				if (num == 2)
				{
					this.pnlSlider = new Panel();
					num = 3;
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
				if (num == 35)
				{
					this.lbSliderValue.Font = new Font("Consolas", 9f);
					num = 36;
				}
				if (num == 36)
				{
					this.lbSliderValue.ForeColor = Color.FromArgb(255, 255, 255);
					num = 37;
				}
				if (num == 0)
				{
					num = 1;
				}
			}
			while (num != 56);
		}

		// Token: 0x0600007B RID: 123 RVA: 0x0001821C File Offset: 0x0001641C
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

		// Token: 0x0400005D RID: 93
		private bool bool_0;

		// Token: 0x0400005E RID: 94
		private bool bool_1;

		// Token: 0x0400005F RID: 95
		private string string_0;

		// Token: 0x04000060 RID: 96
		private bool bool_2;

		// Token: 0x04000061 RID: 97
		private string string_1;

		// Token: 0x04000062 RID: 98
		private string string_2;

		// Token: 0x04000063 RID: 99
		private Color color_0;

		// Token: 0x04000064 RID: 100
		private Color color_1;

		// Token: 0x04000065 RID: 101
		private double double_0;

		// Token: 0x04000066 RID: 102
		private double double_1;

		// Token: 0x04000067 RID: 103
		private double double_2;

		// Token: 0x04000068 RID: 104
		private int int_0;

		// Token: 0x04000069 RID: 105
		private Bitmap bitmap_0;

		// Token: 0x0400006A RID: 106
		private Bitmap bitmap_1;

		// Token: 0x0400006B RID: 107
		private int int_1;

		// Token: 0x0400006C RID: 108
		private Panel panel_0;

		// Token: 0x0400006D RID: 109
		private Panel pnlSlider;

		// Token: 0x0400006E RID: 110
		private Class3 lbSliderValue;

		// Token: 0x0400006F RID: 111
		private Class4 pnlSliderBox;

		// Token: 0x04000070 RID: 112
		private Class5 lbTitle;
	}
}

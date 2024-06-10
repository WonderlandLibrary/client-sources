using System;
using System.Drawing;
using System.Windows.Forms;

namespace Client
{
	// Token: 0x02000012 RID: 18
	internal class Class8 : Label
	{
		// Token: 0x060000BF RID: 191 RVA: 0x00002142 File Offset: 0x00000342
		public Class8()
		{
			<Module>.Class0.smethod_0();
			this.enum1_0 = Class8.Enum1.LeftToRight;
			this.color_0 = Color.LightGray;
			this.int_0 = 1;
			base..ctor();
			base.SetStyle(ControlStyles.SupportsTransparentBackColor | ControlStyles.OptimizedDoubleBuffer, true);
		}

		// Token: 0x17000025 RID: 37
		// (get) Token: 0x060000C0 RID: 192 RVA: 0x0001DD24 File Offset: 0x0001BF24
		// (set) Token: 0x060000C1 RID: 193 RVA: 0x0001DD8C File Offset: 0x0001BF8C
		public Color Color_0
		{
			get
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
				return this.color_0;
			}
			set
			{
				int num = 0;
				do
				{
					if (num == 2)
					{
						this.color_0 = value;
						num = 3;
					}
					if (num == 1)
					{
						<Module>.Class0.smethod_0();
						num = 2;
					}
					if (num == 3)
					{
						base.Invalidate();
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

		// Token: 0x17000026 RID: 38
		// (get) Token: 0x060000C2 RID: 194 RVA: 0x0001DE30 File Offset: 0x0001C030
		// (set) Token: 0x060000C3 RID: 195 RVA: 0x0001DE98 File Offset: 0x0001C098
		public int Int32_0
		{
			get
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
				return this.int_0;
			}
			set
			{
				int num = 0;
				do
				{
					if (num == 2)
					{
						this.int_0 = value;
						num = 3;
					}
					if (num == 3)
					{
						base.Invalidate();
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

		// Token: 0x17000027 RID: 39
		// (get) Token: 0x060000C4 RID: 196 RVA: 0x0001DF3C File Offset: 0x0001C13C
		// (set) Token: 0x060000C5 RID: 197 RVA: 0x0001DFA4 File Offset: 0x0001C1A4
		public bool Boolean_0
		{
			get
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
				return this.bool_0;
			}
			set
			{
				int num = 0;
				do
				{
					if (num == 3)
					{
						base.Invalidate();
						num = 4;
					}
					if (num == 2)
					{
						this.bool_0 = value;
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

		// Token: 0x060000C6 RID: 198 RVA: 0x0001E048 File Offset: 0x0001C248
		protected virtual void OnPaint(PaintEventArgs e)
		{
			int num = 0;
			for (;;)
			{
				if (num == 13)
				{
					goto IL_15C;
				}
				if (num == 23)
				{
					e.Graphics.ResetTransform();
					num = 24;
				}
				if (num == 17)
				{
					goto IL_66B;
				}
				if (num == 31)
				{
					e.Graphics.RotateTransform(180f);
					num = 32;
				}
				Rectangle r;
				if (num == 4)
				{
					r = new Rectangle(this.int_0, this.int_0, base.Width, base.Height);
					num = 5;
				}
				SizeF size;
				if (num == 37)
				{
					e.Graphics.DrawString(this.Text, this.Font, new SolidBrush(Color.FromArgb(255, this.ForeColor)), new RectangleF(0f, 0f, size.Height, size.Width), Class8.smethod_0(this.TextAlign));
					num = 38;
				}
				Class8.Enum1 @enum;
				if (num == 11)
				{
					if (@enum > Class8.Enum1.TopToBottom)
					{
						goto IL_22C;
					}
					num = 12;
				}
				if (num == 14)
				{
					goto IL_15C;
				}
				goto IL_173;
				IL_6B3:
				if (num == 6)
				{
					SolidBrush brush;
					e.Graphics.DrawString(this.Text, this.Font, brush, r, Class8.smethod_0(this.TextAlign));
					num = 7;
				}
				if (num == 0)
				{
					num = 1;
				}
				if (num != 41)
				{
					continue;
				}
				break;
				IL_681:
				if (num == 25)
				{
					goto IL_68E;
				}
				goto IL_6B3;
				IL_1F8:
				if (num == 28)
				{
					e.Graphics.ResetTransform();
					num = 29;
				}
				if (num == 16)
				{
					goto IL_22C;
				}
				goto IL_681;
				IL_621:
				if (num == 40)
				{
					goto Block_35;
				}
				if (num == 10)
				{
					Class8.Enum1 enum2;
					@enum = enum2;
					num = 11;
				}
				if (num == 9)
				{
					Class8.Enum1 enum2 = this.enum1_0;
					num = 10;
				}
				if (num == 12)
				{
					if (@enum == Class8.Enum1.LeftToRight)
					{
						goto IL_573;
					}
					num = 13;
				}
				if (num != 18)
				{
					goto IL_1F8;
				}
				goto IL_66B;
				IL_594:
				if (num == 2)
				{
					bool flag = this.bool_0;
					num = 3;
				}
				if (num == 21)
				{
					e.Graphics.RotateTransform(0f);
					num = 22;
				}
				if (num == 33)
				{
					e.Graphics.ResetTransform();
					num = 34;
				}
				if (num == 24)
				{
					break;
				}
				if (num != 35)
				{
					goto IL_621;
				}
				goto IL_636;
				IL_321:
				if (num == 29)
				{
					break;
				}
				if (num == 38)
				{
					e.Graphics.ResetTransform();
					num = 39;
				}
				if (num == 3)
				{
					bool flag;
					if (!flag)
					{
						goto IL_27C;
					}
					num = 4;
				}
				if (num == 39)
				{
					break;
				}
				if (num == 15)
				{
					goto IL_73C;
				}
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 22)
				{
					e.Graphics.DrawString(this.Text, this.Font, new SolidBrush(Color.FromArgb(255, this.ForeColor)), new RectangleF(0f, 0f, size.Width, size.Height), Class8.smethod_0(this.TextAlign));
					num = 23;
				}
				if (num == 27)
				{
					e.Graphics.DrawString(this.Text, this.Font, new SolidBrush(Color.FromArgb(255, this.ForeColor)), new RectangleF(0f, 0f, size.Height, size.Width), Class8.smethod_0(this.TextAlign));
					num = 28;
				}
				if (num == 36)
				{
					e.Graphics.RotateTransform(270f);
					num = 37;
				}
				RectangleF visibleClipBounds;
				if (num == 8)
				{
					size = visibleClipBounds.Size;
					num = 9;
				}
				if (num == 19)
				{
					goto IL_73C;
				}
				if (num == 26)
				{
					e.Graphics.RotateTransform(90f);
					num = 27;
				}
				if (num == 34)
				{
					break;
				}
				if (num == 5)
				{
					SolidBrush brush = new SolidBrush(Color.FromArgb(255, this.color_0));
					num = 6;
				}
				if (num == 20)
				{
					goto IL_573;
				}
				goto IL_594;
				IL_297:
				if (num == 32)
				{
					e.Graphics.DrawString(this.Text, this.Font, new SolidBrush(Color.FromArgb(255, this.ForeColor)), new RectangleF(0f, 0f, size.Width, size.Height), Class8.smethod_0(this.TextAlign));
					num = 33;
				}
				if (num != 30)
				{
					goto IL_321;
				}
				goto IL_336;
				IL_173:
				if (num == 7)
				{
					goto IL_27C;
				}
				goto IL_297;
				IL_68E:
				e.Graphics.TranslateTransform(size.Width, 0f);
				num = 26;
				goto IL_6B3;
				IL_15C:
				if (@enum != Class8.Enum1.TopToBottom)
				{
					num = 15;
					goto IL_173;
				}
				goto IL_68E;
				IL_22C:
				if (@enum != Class8.Enum1.RightToLeft)
				{
					num = 17;
					goto IL_681;
				}
				goto IL_336;
				IL_66B:
				if (@enum != Class8.Enum1.BottomToTop)
				{
					num = 19;
					goto IL_1F8;
				}
				IL_636:
				e.Graphics.TranslateTransform(0f, size.Height);
				num = 36;
				goto IL_621;
				IL_573:
				e.Graphics.TranslateTransform(0f, 0f);
				num = 21;
				goto IL_594;
				IL_336:
				e.Graphics.TranslateTransform(size.Width, size.Height);
				num = 31;
				goto IL_321;
				IL_27C:
				visibleClipBounds = e.Graphics.VisibleClipBounds;
				num = 8;
				goto IL_297;
			}
			return;
			Block_35:
			IL_73C:
			throw new ArgumentOutOfRangeException();
		}

		// Token: 0x060000C7 RID: 199 RVA: 0x0001E798 File Offset: 0x0001C998
		private static StringFormat smethod_0(ContentAlignment contentAlignment_0)
		{
			int num = 0;
			StringFormat result;
			do
			{
				int num2;
				if (num == 3)
				{
					num2 = (int)Math.Log((double)contentAlignment_0, 2.0);
					num = 4;
				}
				StringFormat stringFormat;
				if (num == 2)
				{
					stringFormat = new StringFormat();
					num = 3;
				}
				if (num == 6)
				{
					result = stringFormat;
					num = 7;
				}
				if (num == 5)
				{
					stringFormat.Alignment = (StringAlignment)(num2 % 4);
					num = 6;
				}
				if (num == 4)
				{
					stringFormat.LineAlignment = (StringAlignment)(num2 / 4);
					num = 5;
				}
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 7)
				{
					break;
				}
				if (num == 0)
				{
					num = 1;
				}
			}
			while (num != 8);
			return result;
		}

		// Token: 0x04000092 RID: 146
		private Class8.Enum1 enum1_0;

		// Token: 0x04000093 RID: 147
		private bool bool_0;

		// Token: 0x04000094 RID: 148
		private Color color_0;

		// Token: 0x04000095 RID: 149
		private int int_0;

		// Token: 0x02000013 RID: 19
		public enum Enum1
		{
			// Token: 0x04000097 RID: 151
			LeftToRight,
			// Token: 0x04000098 RID: 152
			TopToBottom = 90,
			// Token: 0x04000099 RID: 153
			RightToLeft = 180,
			// Token: 0x0400009A RID: 154
			BottomToTop = 270
		}
	}
}

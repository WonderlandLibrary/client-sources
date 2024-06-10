using System;
using System.Drawing;
using System.Windows.Forms;

namespace Client
{
	// Token: 0x0200000D RID: 13
	internal class Class5 : Label
	{
		// Token: 0x06000085 RID: 133 RVA: 0x000020ED File Offset: 0x000002ED
		public Class5()
		{
			<Module>.Class0.smethod_0();
			this.enum0_0 = Class5.Enum0.LeftToRight;
			this.color_0 = Color.LightGray;
			this.int_0 = 1;
			base..ctor();
			base.SetStyle(ControlStyles.SupportsTransparentBackColor | ControlStyles.OptimizedDoubleBuffer, true);
		}

		// Token: 0x17000013 RID: 19
		// (get) Token: 0x06000086 RID: 134 RVA: 0x000197C0 File Offset: 0x000179C0
		// (set) Token: 0x06000087 RID: 135 RVA: 0x00019828 File Offset: 0x00017A28
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

		// Token: 0x17000014 RID: 20
		// (get) Token: 0x06000088 RID: 136 RVA: 0x000198CC File Offset: 0x00017ACC
		// (set) Token: 0x06000089 RID: 137 RVA: 0x00019934 File Offset: 0x00017B34
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

		// Token: 0x17000015 RID: 21
		// (get) Token: 0x0600008A RID: 138 RVA: 0x000199D8 File Offset: 0x00017BD8
		// (set) Token: 0x0600008B RID: 139 RVA: 0x00019A40 File Offset: 0x00017C40
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

		// Token: 0x0600008C RID: 140 RVA: 0x00019AE4 File Offset: 0x00017CE4
		protected virtual void OnPaint(PaintEventArgs e)
		{
			int num = 0;
			for (;;)
			{
				if (num == 25)
				{
					goto IL_67D;
				}
				IL_6ED:
				if (num == 6)
				{
					SolidBrush brush;
					Rectangle r;
					e.Graphics.DrawString(this.Text, this.Font, brush, r, Class5.smethod_0(this.TextAlign));
					num = 7;
				}
				if (num == 26)
				{
					e.Graphics.RotateTransform(90f);
					num = 27;
				}
				if (num != 11)
				{
					goto IL_5B;
				}
				Class5.Enum0 @enum;
				if (@enum <= Class5.Enum0.TopToBottom)
				{
					num = 12;
					goto IL_5B;
				}
				goto IL_177;
				IL_543:
				if (num == 17)
				{
					goto IL_511;
				}
				if (num == 38)
				{
					e.Graphics.ResetTransform();
					num = 39;
				}
				if (num == 0)
				{
					num = 1;
				}
				if (num != 41)
				{
					continue;
				}
				return;
				IL_478:
				if (num == 24)
				{
					return;
				}
				RectangleF visibleClipBounds;
				SizeF size;
				if (num == 8)
				{
					size = visibleClipBounds.Size;
					num = 9;
				}
				if (num == 23)
				{
					e.Graphics.ResetTransform();
					num = 24;
				}
				if (num == 9)
				{
					Class5.Enum0 enum2 = this.enum0_0;
					num = 10;
				}
				if (num == 34)
				{
					return;
				}
				if (num == 35)
				{
					goto IL_51E;
				}
				goto IL_543;
				IL_386:
				if (num == 10)
				{
					Class5.Enum0 enum2;
					@enum = enum2;
					num = 11;
				}
				if (num == 37)
				{
					e.Graphics.DrawString(this.Text, this.Font, new SolidBrush(Color.FromArgb(255, this.ForeColor)), new RectangleF(0f, 0f, size.Height, size.Width), Class5.smethod_0(this.TextAlign));
					num = 38;
				}
				if (num == 21)
				{
					e.Graphics.RotateTransform(0f);
					num = 22;
				}
				if (num == 20)
				{
					goto IL_457;
				}
				goto IL_478;
				IL_55E:
				if (num == 13)
				{
					goto IL_61A;
				}
				if (num == 28)
				{
					e.Graphics.ResetTransform();
					num = 29;
				}
				if (num == 3)
				{
					bool flag;
					if (!flag)
					{
						goto IL_573;
					}
					num = 4;
				}
				if (num == 33)
				{
					e.Graphics.ResetTransform();
					num = 34;
				}
				if (num == 2)
				{
					bool flag = this.bool_0;
					num = 3;
				}
				if (num == 29)
				{
					return;
				}
				if (num == 30)
				{
					goto IL_35D;
				}
				goto IL_386;
				IL_605:
				if (num == 32)
				{
					e.Graphics.DrawString(this.Text, this.Font, new SolidBrush(Color.FromArgb(255, this.ForeColor)), new RectangleF(0f, 0f, size.Width, size.Height), Class5.smethod_0(this.TextAlign));
					num = 33;
				}
				if (num == 22)
				{
					e.Graphics.DrawString(this.Text, this.Font, new SolidBrush(Color.FromArgb(255, this.ForeColor)), new RectangleF(0f, 0f, size.Width, size.Height), Class5.smethod_0(this.TextAlign));
					num = 23;
				}
				if (num == 31)
				{
					e.Graphics.RotateTransform(180f);
					num = 32;
				}
				if (num == 36)
				{
					e.Graphics.RotateTransform(270f);
					num = 37;
				}
				if (num == 12)
				{
					if (@enum == Class5.Enum0.LeftToRight)
					{
						goto IL_457;
					}
					num = 13;
				}
				if (num == 18)
				{
					goto IL_511;
				}
				goto IL_55E;
				IL_18E:
				if (num == 39)
				{
					return;
				}
				if (num == 7)
				{
					goto IL_573;
				}
				goto IL_605;
				IL_66B:
				if (num == 4)
				{
					Rectangle r = new Rectangle(this.int_0, this.int_0, base.Width, base.Height);
					num = 5;
				}
				if (num == 19)
				{
					break;
				}
				if (num == 16)
				{
					goto IL_177;
				}
				goto IL_18E;
				IL_5B:
				if (num == 40)
				{
					break;
				}
				if (num == 27)
				{
					e.Graphics.DrawString(this.Text, this.Font, new SolidBrush(Color.FromArgb(255, this.ForeColor)), new RectangleF(0f, 0f, size.Height, size.Width), Class5.smethod_0(this.TextAlign));
					num = 28;
				}
				if (num == 15)
				{
					break;
				}
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 5)
				{
					SolidBrush brush = new SolidBrush(Color.FromArgb(255, this.color_0));
					num = 6;
				}
				if (num != 14)
				{
					goto IL_66B;
				}
				IL_61A:
				if (@enum != Class5.Enum0.TopToBottom)
				{
					num = 15;
					goto IL_66B;
				}
				goto IL_67D;
				IL_51E:
				e.Graphics.TranslateTransform(0f, size.Height);
				num = 36;
				goto IL_543;
				IL_511:
				if (@enum != Class5.Enum0.BottomToTop)
				{
					num = 19;
					goto IL_55E;
				}
				goto IL_51E;
				IL_457:
				e.Graphics.TranslateTransform(0f, 0f);
				num = 21;
				goto IL_478;
				IL_35D:
				e.Graphics.TranslateTransform(size.Width, size.Height);
				num = 31;
				goto IL_386;
				IL_177:
				if (@enum != Class5.Enum0.RightToLeft)
				{
					num = 17;
					goto IL_18E;
				}
				goto IL_35D;
				IL_573:
				visibleClipBounds = e.Graphics.VisibleClipBounds;
				num = 8;
				goto IL_605;
				IL_67D:
				e.Graphics.TranslateTransform(size.Width, 0f);
				num = 26;
				goto IL_6ED;
			}
			throw new ArgumentOutOfRangeException();
		}

		// Token: 0x0600008D RID: 141 RVA: 0x0001A250 File Offset: 0x00018450
		private static StringFormat smethod_0(ContentAlignment contentAlignment_0)
		{
			int num = 0;
			StringFormat result;
			do
			{
				StringFormat stringFormat;
				if (num == 6)
				{
					result = stringFormat;
					num = 7;
				}
				if (num == 2)
				{
					stringFormat = new StringFormat();
					num = 3;
				}
				if (num == 7)
				{
					break;
				}
				int num2;
				if (num == 4)
				{
					stringFormat.LineAlignment = (StringAlignment)(num2 / 4);
					num = 5;
				}
				if (num == 3)
				{
					num2 = (int)Math.Log((double)contentAlignment_0, 2.0);
					num = 4;
				}
				if (num == 5)
				{
					stringFormat.Alignment = (StringAlignment)(num2 % 4);
					num = 6;
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
			while (num != 8);
			return result;
		}

		// Token: 0x04000073 RID: 115
		private Class5.Enum0 enum0_0;

		// Token: 0x04000074 RID: 116
		private bool bool_0;

		// Token: 0x04000075 RID: 117
		private Color color_0;

		// Token: 0x04000076 RID: 118
		private int int_0;

		// Token: 0x0200000E RID: 14
		public enum Enum0
		{
			// Token: 0x04000078 RID: 120
			LeftToRight,
			// Token: 0x04000079 RID: 121
			TopToBottom = 90,
			// Token: 0x0400007A RID: 122
			RightToLeft = 180,
			// Token: 0x0400007B RID: 123
			BottomToTop = 270
		}
	}
}

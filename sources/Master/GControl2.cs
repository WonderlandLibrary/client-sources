using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Runtime.CompilerServices;
using System.Threading;
using System.Windows.Forms;

namespace Client
{
	// Token: 0x02000014 RID: 20
	[DefaultEvent("CheckedChanged")]
	public class GControl2 : Control
	{
		// Token: 0x14000001 RID: 1
		// (add) Token: 0x060000C8 RID: 200 RVA: 0x0001E8CC File Offset: 0x0001CACC
		// (remove) Token: 0x060000C9 RID: 201 RVA: 0x0001E9F4 File Offset: 0x0001CBF4
		public event GControl2.GDelegate0 Event_0
		{
			[CompilerGenerated]
			add
			{
				int num = 0;
				for (;;)
				{
					if (num != 6)
					{
						goto IL_0E;
					}
					GControl2.GDelegate0 gdelegate;
					GControl2.GDelegate0 gdelegate2;
					if (gdelegate == gdelegate2)
					{
						num = 7;
						goto IL_0E;
					}
					goto IL_3E;
					IL_4F:
					if (num == 5)
					{
						GControl2.GDelegate0 value2;
						gdelegate = Interlocked.CompareExchange<GControl2.GDelegate0>(ref this.gdelegate0_0, value2, gdelegate2);
						num = 6;
					}
					if (num == 1)
					{
						<Module>.Class0.smethod_0();
						num = 2;
					}
					if (num == 4)
					{
						GControl2.GDelegate0 value2 = (GControl2.GDelegate0)Delegate.Combine(gdelegate2, value);
						num = 5;
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
					IL_0E:
					if (num == 2)
					{
						gdelegate = this.gdelegate0_0;
						num = 3;
					}
					if (num != 3)
					{
						goto IL_4F;
					}
					IL_3E:
					gdelegate2 = gdelegate;
					num = 4;
					goto IL_4F;
				}
			}
			[CompilerGenerated]
			remove
			{
				int num = 0;
				for (;;)
				{
					if (num == 3)
					{
						goto IL_9E;
					}
					IL_CC:
					GControl2.GDelegate0 gdelegate;
					if (num == 2)
					{
						gdelegate = this.gdelegate0_0;
						num = 3;
					}
					GControl2.GDelegate0 gdelegate2;
					GControl2.GDelegate0 value2;
					if (num == 4)
					{
						value2 = (GControl2.GDelegate0)Delegate.Remove(gdelegate2, value);
						num = 5;
					}
					if (num == 5)
					{
						gdelegate = Interlocked.CompareExchange<GControl2.GDelegate0>(ref this.gdelegate0_0, value2, gdelegate2);
						num = 6;
					}
					if (num == 1)
					{
						<Module>.Class0.smethod_0();
						num = 2;
					}
					if (num == 6)
					{
						if (gdelegate != gdelegate2)
						{
							goto IL_9E;
						}
						num = 7;
					}
					if (num == 0)
					{
						num = 1;
					}
					if (num != 7)
					{
						continue;
					}
					break;
					IL_9E:
					gdelegate2 = gdelegate;
					num = 4;
					goto IL_CC;
				}
			}
		}

		// Token: 0x17000028 RID: 40
		// (get) Token: 0x060000CA RID: 202 RVA: 0x0001EB1C File Offset: 0x0001CD1C
		// (set) Token: 0x060000CB RID: 203 RVA: 0x0001EBAC File Offset: 0x0001CDAC
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
				while (num != 3);
			}
		}

		// Token: 0x060000CC RID: 204 RVA: 0x0001EC2C File Offset: 0x0001CE2C
		protected virtual void OnTextChanged(EventArgs e)
		{
			int num = 0;
			do
			{
				if (num == 2)
				{
					base.OnTextChanged(e);
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

		// Token: 0x060000CD RID: 205 RVA: 0x0001ECD0 File Offset: 0x0001CED0
		protected virtual void OnMouseEnter(EventArgs e)
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
					base.OnMouseEnter(e);
					num = 3;
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

		// Token: 0x060000CE RID: 206 RVA: 0x0001ED74 File Offset: 0x0001CF74
		protected virtual void OnMouseDown(MouseEventArgs e)
		{
			int num = 0;
			do
			{
				if (num == 2)
				{
					base.OnMouseDown(e);
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

		// Token: 0x060000CF RID: 207 RVA: 0x0001EE18 File Offset: 0x0001D018
		protected virtual void OnMouseLeave(EventArgs e)
		{
			int num = 0;
			do
			{
				if (num == 2)
				{
					base.OnMouseLeave(e);
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

		// Token: 0x060000D0 RID: 208 RVA: 0x0001EEBC File Offset: 0x0001D0BC
		protected virtual void OnMouseUp(MouseEventArgs e)
		{
			int num = 0;
			do
			{
				if (num == 2)
				{
					base.OnMouseUp(e);
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

		// Token: 0x060000D1 RID: 209 RVA: 0x0001EF60 File Offset: 0x0001D160
		protected virtual void OnClick(EventArgs e)
		{
			int num = 0;
			do
			{
				if (num == 6)
				{
					this.gdelegate0_0(this);
					num = 7;
				}
				if (num == 3)
				{
					this.bool_0 = !this.bool_0;
					num = 4;
				}
				bool flag;
				if (num == 4)
				{
					flag = (this.gdelegate0_0 != null);
					num = 5;
				}
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 2)
				{
					base.OnClick(e);
					num = 3;
				}
				if (num == 5)
				{
					if (!flag)
					{
						break;
					}
					num = 6;
				}
				if (num == 0)
				{
					num = 1;
				}
			}
			while (num != 7);
		}

		// Token: 0x17000029 RID: 41
		// (get) Token: 0x060000D2 RID: 210 RVA: 0x0001F084 File Offset: 0x0001D284
		// (set) Token: 0x060000D3 RID: 211 RVA: 0x0001F0EC File Offset: 0x0001D2EC
		public Color Color_0
		{
			[CompilerGenerated]
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
			[CompilerGenerated]
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
					if (num == 0)
					{
						num = 1;
					}
				}
				while (num != 3);
			}
		}

		// Token: 0x1700002A RID: 42
		// (get) Token: 0x060000D4 RID: 212 RVA: 0x0001F16C File Offset: 0x0001D36C
		// (set) Token: 0x060000D5 RID: 213 RVA: 0x0001F1D4 File Offset: 0x0001D3D4
		public Color Color_1
		{
			[CompilerGenerated]
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
				return this.color_1;
			}
			[CompilerGenerated]
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
						this.color_1 = value;
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

		// Token: 0x1700002B RID: 43
		// (get) Token: 0x060000D6 RID: 214 RVA: 0x0001F254 File Offset: 0x0001D454
		// (set) Token: 0x060000D7 RID: 215 RVA: 0x0001F2BC File Offset: 0x0001D4BC
		public Pen Pen_0
		{
			[CompilerGenerated]
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
				return this.pen_0;
			}
			[CompilerGenerated]
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
						this.pen_0 = value;
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

		// Token: 0x1700002C RID: 44
		// (get) Token: 0x060000D8 RID: 216 RVA: 0x0001F33C File Offset: 0x0001D53C
		// (set) Token: 0x060000D9 RID: 217 RVA: 0x0001F3A4 File Offset: 0x0001D5A4
		public Color Color_2
		{
			[CompilerGenerated]
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
				return this.color_2;
			}
			[CompilerGenerated]
			set
			{
				int num = 0;
				do
				{
					if (num == 2)
					{
						this.color_2 = value;
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
				while (num != 3);
			}
		}

		// Token: 0x1700002D RID: 45
		// (get) Token: 0x060000DA RID: 218 RVA: 0x0001F424 File Offset: 0x0001D624
		// (set) Token: 0x060000DB RID: 219 RVA: 0x0001F48C File Offset: 0x0001D68C
		public Color Color_3
		{
			[CompilerGenerated]
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
				return this.color_3;
			}
			[CompilerGenerated]
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
						this.color_3 = value;
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

		// Token: 0x1700002E RID: 46
		// (get) Token: 0x060000DC RID: 220 RVA: 0x0001F50C File Offset: 0x0001D70C
		// (set) Token: 0x060000DD RID: 221 RVA: 0x0001F574 File Offset: 0x0001D774
		public Pen Pen_1
		{
			[CompilerGenerated]
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
				return this.pen_1;
			}
			[CompilerGenerated]
			set
			{
				int num = 0;
				do
				{
					if (num == 2)
					{
						this.pen_1 = value;
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
				while (num != 3);
			}
		}

		// Token: 0x060000DE RID: 222 RVA: 0x0001F5F4 File Offset: 0x0001D7F4
		public GControl2()
		{
			<Module>.Class0.smethod_0();
			this.bool_0 = false;
			this.color_0 = Color.FromArgb(45, 45, 45);
			this.color_1 = Color.FromArgb(35, 35, 35);
			this.stringFormat_0 = new StringFormat();
			this.pen_0 = new Pen(Color.FromArgb(50, 50, 50));
			this.color_2 = Color.FromArgb(154, 197, 39);
			this.color_3 = Color.FromArgb(124, 161, 27);
			this.pen_1 = new Pen(Color.FromArgb(50, 50, 50));
			this.bool_1 = false;
			base..ctor();
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			this.BackColor = Color.Transparent;
			this.Font = new Font("Consolas", 8f);
		}

		// Token: 0x060000DF RID: 223 RVA: 0x0001F6D4 File Offset: 0x0001D8D4
		protected virtual void OnPaint(PaintEventArgs e)
		{
			int num = 0;
			Rectangle rect;
			LinearGradientBrush brush;
			Rectangle rect2;
			do
			{
				if (num == 6)
				{
					brush = new LinearGradientBrush(rect, this.Color_3, this.Color_2, 90f);
					num = 7;
				}
				if (num == 2)
				{
					rect2 = new Rectangle(2, 2, 12, 12);
					num = 3;
				}
				Rectangle rect3;
				if (num == 7)
				{
					LinearGradientBrush brush2;
					e.Graphics.FillRectangle(brush2, rect3);
					num = 8;
				}
				if (num == 5)
				{
					LinearGradientBrush brush2 = new LinearGradientBrush(rect3, this.Color_1, this.Color_0, 90f);
					num = 6;
				}
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 8)
				{
					e.Graphics.DrawRectangle(this.Pen_0, rect);
					num = 9;
				}
				if (num == 4)
				{
					rect = new Rectangle(1, 1, 12, 12);
					num = 5;
				}
				if (num == 3)
				{
					rect3 = new Rectangle(1, 1, 13, 13);
					num = 4;
				}
				if (num == 0)
				{
					num = 1;
				}
			}
			while (num != 9);
			e.Graphics.DrawString(this.Text, this.Font, this.bool_1 ? Brushes.White : Brushes.White, 20f, 2f);
			if (this.Boolean_0)
			{
				e.Graphics.DrawRectangle(this.Pen_0, rect);
				e.Graphics.FillRectangle(brush, rect2);
			}
			base.OnPaint(e);
		}

		// Token: 0x0400009B RID: 155
		private bool bool_0;

		// Token: 0x0400009C RID: 156
		[DebuggerBrowsable(DebuggerBrowsableState.Never)]
		[CompilerGenerated]
		private GControl2.GDelegate0 gdelegate0_0;

		// Token: 0x0400009D RID: 157
		[CompilerGenerated]
		[DebuggerBrowsable(DebuggerBrowsableState.Never)]
		private Color color_0;

		// Token: 0x0400009E RID: 158
		[CompilerGenerated]
		[DebuggerBrowsable(DebuggerBrowsableState.Never)]
		private Color color_1;

		// Token: 0x0400009F RID: 159
		private StringFormat stringFormat_0;

		// Token: 0x040000A0 RID: 160
		[DebuggerBrowsable(DebuggerBrowsableState.Never)]
		[CompilerGenerated]
		private Pen pen_0;

		// Token: 0x040000A1 RID: 161
		[CompilerGenerated]
		[DebuggerBrowsable(DebuggerBrowsableState.Never)]
		private Color color_2;

		// Token: 0x040000A2 RID: 162
		[CompilerGenerated]
		[DebuggerBrowsable(DebuggerBrowsableState.Never)]
		private Color color_3;

		// Token: 0x040000A3 RID: 163
		[CompilerGenerated]
		[DebuggerBrowsable(DebuggerBrowsableState.Never)]
		private Pen pen_1;

		// Token: 0x040000A4 RID: 164
		public bool bool_1;

		// Token: 0x02000015 RID: 21
		// (Invoke) Token: 0x060000E1 RID: 225
		public delegate void GDelegate0(object sender);
	}
}

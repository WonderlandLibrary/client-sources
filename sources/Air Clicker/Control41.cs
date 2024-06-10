using System;
using System.Drawing;
using System.Windows.Forms;

// Token: 0x020000B1 RID: 177
internal class Control41 : ContainerControl
{
	// Token: 0x17000261 RID: 609
	// (get) Token: 0x06000844 RID: 2116 RVA: 0x00026A78 File Offset: 0x00024C78
	// (set) Token: 0x06000845 RID: 2117 RVA: 0x00026A80 File Offset: 0x00024C80
	public bool Boolean_0
	{
		get
		{
			return this.bool_2;
		}
		set
		{
			this.bool_2 = value;
		}
	}

	// Token: 0x17000262 RID: 610
	// (get) Token: 0x06000846 RID: 2118 RVA: 0x00026A8C File Offset: 0x00024C8C
	// (set) Token: 0x06000847 RID: 2119 RVA: 0x00026A94 File Offset: 0x00024C94
	public bool Boolean_1
	{
		get
		{
			return this.bool_3;
		}
		set
		{
			this.bool_3 = value;
		}
	}

	// Token: 0x17000263 RID: 611
	// (get) Token: 0x06000848 RID: 2120 RVA: 0x00026AA0 File Offset: 0x00024CA0
	// (set) Token: 0x06000849 RID: 2121 RVA: 0x00026AA8 File Offset: 0x00024CA8
	public bool Boolean_2
	{
		get
		{
			return this.bool_4;
		}
		set
		{
			this.bool_4 = value;
			base.Invalidate();
		}
	}

	// Token: 0x17000264 RID: 612
	// (get) Token: 0x0600084A RID: 2122 RVA: 0x00026AB8 File Offset: 0x00024CB8
	protected bool Boolean_3
	{
		get
		{
			return this.bool_5;
		}
	}

	// Token: 0x17000265 RID: 613
	// (get) Token: 0x0600084B RID: 2123 RVA: 0x00026AC0 File Offset: 0x00024CC0
	protected bool Boolean_4
	{
		get
		{
			return base.Parent != null && base.Parent.Parent != null;
		}
	}

	// Token: 0x17000266 RID: 614
	// (get) Token: 0x0600084C RID: 2124 RVA: 0x00026AEC File Offset: 0x00024CEC
	// (set) Token: 0x0600084D RID: 2125 RVA: 0x00026AF4 File Offset: 0x00024CF4
	protected bool Boolean_5
	{
		get
		{
			return this.bool_6;
		}
		set
		{
			this.bool_6 = value;
			base.Invalidate();
		}
	}

	// Token: 0x17000267 RID: 615
	// (get) Token: 0x0600084E RID: 2126 RVA: 0x00026B04 File Offset: 0x00024D04
	// (set) Token: 0x0600084F RID: 2127 RVA: 0x00026B40 File Offset: 0x00024D40
	public FormStartPosition FormStartPosition_0
	{
		get
		{
			FormStartPosition startPosition;
			if (!this.bool_5 || this.bool_6)
			{
				startPosition = this.formStartPosition_0;
			}
			else
			{
				startPosition = base.ParentForm.StartPosition;
			}
			return startPosition;
		}
		set
		{
			this.formStartPosition_0 = value;
			if (this.bool_5 && !this.bool_6)
			{
				base.ParentForm.StartPosition = value;
			}
		}
	}

	// Token: 0x06000850 RID: 2128 RVA: 0x00026B6C File Offset: 0x00024D6C
	protected sealed void OnParentChanged(EventArgs e)
	{
		base.OnParentChanged(e);
		if (base.Parent != null)
		{
			this.bool_5 = (base.Parent is Form);
			if (!this.bool_6)
			{
				this.method_4();
				if (this.bool_5)
				{
					base.ParentForm.FormBorderStyle = FormBorderStyle.None;
					base.ParentForm.TransparencyKey = Color.Fuchsia;
					if (!base.DesignMode)
					{
						base.ParentForm.Shown += this.method_0;
					}
				}
				base.Parent.BackColor = this.BackColor;
			}
		}
	}

	// Token: 0x06000851 RID: 2129 RVA: 0x00026C08 File Offset: 0x00024E08
	protected sealed void OnSizeChanged(EventArgs e)
	{
		base.OnSizeChanged(e);
		if (!this.bool_6)
		{
			this.rectangle_0 = checked(new Rectangle(0, 0, base.Width - 14, this.int_0 - 7));
		}
		base.Invalidate();
	}

	// Token: 0x06000852 RID: 2130 RVA: 0x00026C40 File Offset: 0x00024E40
	protected virtual void OnMouseDown(MouseEventArgs e)
	{
		base.OnMouseDown(e);
		base.Focus();
		if (e.Button == MouseButtons.Left)
		{
			this.method_1(Control41.Enum10.Down);
		}
		bool flag;
		if (this.bool_5)
		{
			if (base.ParentForm.WindowState == FormWindowState.Maximized)
			{
				flag = false;
				goto IL_48;
			}
		}
		flag = !this.bool_6;
		IL_48:
		if (flag)
		{
			if (!this.rectangle_0.Contains(e.Location))
			{
				if (this.bool_2 && this.int_2 != 0)
				{
					base.Capture = false;
					this.bool_11 = true;
					this.DefWndProc(ref this.message_0[this.int_2]);
				}
			}
			else
			{
				base.Capture = false;
				this.bool_11 = true;
				this.DefWndProc(ref this.message_0[0]);
			}
		}
	}

	// Token: 0x06000853 RID: 2131 RVA: 0x00026D08 File Offset: 0x00024F08
	protected virtual void OnMouseUp(MouseEventArgs e)
	{
		base.OnMouseUp(e);
		this.bool_0 = false;
	}

	// Token: 0x06000854 RID: 2132 RVA: 0x00026D18 File Offset: 0x00024F18
	protected virtual void OnMouseMove(MouseEventArgs e)
	{
		base.OnMouseMove(e);
		if ((!this.bool_5 || base.ParentForm.WindowState != FormWindowState.Maximized) && (this.bool_2 && !this.bool_6))
		{
			this.method_3();
		}
		if (this.bool_0)
		{
			base.Parent.Location = Control.MousePosition - (Size)this.point_0;
		}
	}

	// Token: 0x06000855 RID: 2133 RVA: 0x00026D90 File Offset: 0x00024F90
	protected virtual void OnInvalidated(InvalidateEventArgs e)
	{
		base.OnInvalidated(e);
		base.ParentForm.Text = this.Text;
	}

	// Token: 0x06000856 RID: 2134 RVA: 0x00026DAC File Offset: 0x00024FAC
	protected virtual void OnPaintBackground(PaintEventArgs e)
	{
		base.OnPaintBackground(e);
	}

	// Token: 0x06000857 RID: 2135 RVA: 0x00026DB8 File Offset: 0x00024FB8
	protected virtual void OnTextChanged(EventArgs e)
	{
		base.OnTextChanged(e);
		base.Invalidate();
	}

	// Token: 0x06000858 RID: 2136 RVA: 0x00026DC8 File Offset: 0x00024FC8
	private void method_0(object sender, EventArgs e)
	{
		if (!this.bool_6 && !this.bool_1)
		{
			if (this.formStartPosition_0 == FormStartPosition.CenterParent || this.formStartPosition_0 == FormStartPosition.CenterScreen)
			{
				Rectangle bounds = Screen.PrimaryScreen.Bounds;
				Rectangle bounds2 = base.ParentForm.Bounds;
				base.ParentForm.Location = checked(new Point(bounds.Width / 2 - bounds2.Width / 2, bounds.Height / 2 - bounds2.Width / 2));
			}
			this.bool_1 = true;
		}
	}

	// Token: 0x06000859 RID: 2137 RVA: 0x00026E54 File Offset: 0x00025054
	private void method_1(Control41.Enum10 enum10_1)
	{
		this.enum10_0 = enum10_1;
		base.Invalidate();
	}

	// Token: 0x0600085A RID: 2138 RVA: 0x00026E64 File Offset: 0x00025064
	private int method_2()
	{
		this.point_1 = base.PointToClient(Control.MousePosition);
		this.bool_7 = (this.point_1.X < 7);
		checked
		{
			this.bool_8 = (this.point_1.X > base.Width - 7);
			this.bool_9 = (this.point_1.Y < 7);
			this.bool_10 = (this.point_1.Y > base.Height - 7);
			int result;
			if (this.bool_7 && this.bool_9)
			{
				result = 4;
			}
			else if (this.bool_7 && this.bool_10)
			{
				result = 7;
			}
			else if (this.bool_8 && this.bool_9)
			{
				result = 5;
			}
			else if (!this.bool_8 || !this.bool_10)
			{
				if (!this.bool_7)
				{
					if (this.bool_8)
					{
						result = 2;
					}
					else if (!this.bool_9)
					{
						if (this.bool_10)
						{
							result = 6;
						}
						else
						{
							result = 0;
						}
					}
					else
					{
						result = 3;
					}
				}
				else
				{
					result = 1;
				}
			}
			else
			{
				result = 8;
			}
			return result;
		}
	}

	// Token: 0x0600085B RID: 2139 RVA: 0x00026F70 File Offset: 0x00025170
	private void method_3()
	{
		this.int_1 = this.method_2();
		if (this.int_1 != this.int_2)
		{
			this.int_2 = this.int_1;
			int num = this.int_2;
			if (num == 0)
			{
				this.Cursor = Cursors.Default;
			}
			else
			{
				switch (num)
				{
				case 6:
					this.Cursor = Cursors.SizeNS;
					break;
				case 7:
					this.Cursor = Cursors.SizeNESW;
					break;
				case 8:
					this.Cursor = Cursors.SizeNWSE;
					break;
				}
			}
		}
	}

	// Token: 0x0600085C RID: 2140 RVA: 0x00026FF8 File Offset: 0x000251F8
	private void method_4()
	{
		this.message_0[0] = Message.Create(base.Parent.Handle, 161, new IntPtr(2), IntPtr.Zero);
		int num = 1;
		checked
		{
			do
			{
				this.message_0[num] = Message.Create(base.Parent.Handle, 161, new IntPtr(num + 9), IntPtr.Zero);
				num++;
			}
			while (num <= 8);
		}
	}

	// Token: 0x0600085D RID: 2141 RVA: 0x00027070 File Offset: 0x00025270
	private void method_5(Rectangle rectangle_1)
	{
		if (base.Parent.Width > rectangle_1.Width)
		{
			base.Parent.Width = rectangle_1.Width;
		}
		if (base.Parent.Height > rectangle_1.Height)
		{
			base.Parent.Height = rectangle_1.Height;
		}
		int num = base.Parent.Location.X;
		int num2 = base.Parent.Location.Y;
		if (num < rectangle_1.X)
		{
			num = rectangle_1.X;
		}
		if (num2 < rectangle_1.Y)
		{
			num2 = rectangle_1.Y;
		}
		checked
		{
			int num3 = rectangle_1.X + rectangle_1.Width;
			int num4 = rectangle_1.Y + rectangle_1.Height;
			if (num + base.Parent.Width > num3)
			{
				num = num3 - base.Parent.Width;
			}
			if (num2 + base.Parent.Height > num4)
			{
				num2 = num4 - base.Parent.Height;
			}
			base.Parent.Location = new Point(num, num2);
		}
	}

	// Token: 0x0600085E RID: 2142 RVA: 0x00027194 File Offset: 0x00025394
	protected virtual void WndProc(ref Message m)
	{
		base.WndProc(ref m);
		if (this.bool_11 && m.Msg == 513)
		{
			this.bool_11 = false;
			this.method_1(Control41.Enum10.Over);
			if (this.bool_3)
			{
				if (this.Boolean_4)
				{
					this.method_5(new Rectangle(Point.Empty, base.Parent.Parent.Size));
				}
				else
				{
					this.method_5(Screen.FromControl(base.Parent).WorkingArea);
				}
			}
		}
	}

	// Token: 0x0600085F RID: 2143 RVA: 0x0002721C File Offset: 0x0002541C
	protected virtual void CreateHandle()
	{
		base.CreateHandle();
	}

	// Token: 0x06000860 RID: 2144 RVA: 0x00027224 File Offset: 0x00025424
	public Control41()
	{
		this.point_0 = new Point(0, 0);
		this.bool_0 = false;
		this.bool_2 = true;
		this.bool_3 = true;
		this.bool_4 = true;
		this.message_0 = new Message[9];
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.Opaque | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		this.BackColor = Color.FromArgb(32, 41, 50);
		base.Padding = new Padding(10, 70, 10, 9);
		this.DoubleBuffered = true;
		this.Dock = DockStyle.Fill;
		this.int_0 = 66;
		this.Font = new Font("Segoe UI", 9f);
	}

	// Token: 0x06000861 RID: 2145 RVA: 0x000272C8 File Offset: 0x000254C8
	protected virtual void OnPaint(PaintEventArgs e)
	{
		base.OnPaint(e);
		Graphics graphics = e.Graphics;
		graphics.Clear(Color.FromArgb(32, 41, 50));
		graphics.FillRectangle(new SolidBrush(Color.FromArgb(181, 41, 42)), new Rectangle(0, 0, base.Width, 60));
		checked
		{
			if (this.bool_4)
			{
				graphics.FillRectangle(Brushes.Fuchsia, 0, 0, 1, 1);
				graphics.FillRectangle(Brushes.Fuchsia, 1, 0, 1, 1);
				graphics.FillRectangle(Brushes.Fuchsia, 2, 0, 1, 1);
				graphics.FillRectangle(Brushes.Fuchsia, 3, 0, 1, 1);
				graphics.FillRectangle(Brushes.Fuchsia, 0, 1, 1, 1);
				graphics.FillRectangle(Brushes.Fuchsia, 0, 2, 1, 1);
				graphics.FillRectangle(Brushes.Fuchsia, 0, 3, 1, 1);
				graphics.FillRectangle(Brushes.Fuchsia, 1, 1, 1, 1);
				graphics.FillRectangle(new SolidBrush(Color.FromArgb(181, 41, 42)), 1, 3, 1, 1);
				graphics.FillRectangle(new SolidBrush(Color.FromArgb(181, 41, 42)), 1, 2, 1, 1);
				graphics.FillRectangle(new SolidBrush(Color.FromArgb(181, 41, 42)), 2, 1, 1, 1);
				graphics.FillRectangle(new SolidBrush(Color.FromArgb(181, 41, 42)), 3, 1, 1, 1);
				graphics.FillRectangle(Brushes.Fuchsia, base.Width - 1, 0, 1, 1);
				graphics.FillRectangle(Brushes.Fuchsia, base.Width - 2, 0, 1, 1);
				graphics.FillRectangle(Brushes.Fuchsia, base.Width - 3, 0, 1, 1);
				graphics.FillRectangle(Brushes.Fuchsia, base.Width - 4, 0, 1, 1);
				graphics.FillRectangle(Brushes.Fuchsia, base.Width - 1, 1, 1, 1);
				graphics.FillRectangle(Brushes.Fuchsia, base.Width - 1, 2, 1, 1);
				graphics.FillRectangle(Brushes.Fuchsia, base.Width - 1, 3, 1, 1);
				graphics.FillRectangle(Brushes.Fuchsia, base.Width - 2, 1, 1, 1);
				graphics.FillRectangle(new SolidBrush(Color.FromArgb(181, 41, 42)), base.Width - 2, 3, 1, 1);
				graphics.FillRectangle(new SolidBrush(Color.FromArgb(181, 41, 42)), base.Width - 2, 2, 1, 1);
				graphics.FillRectangle(new SolidBrush(Color.FromArgb(181, 41, 42)), base.Width - 3, 1, 1, 1);
				graphics.FillRectangle(new SolidBrush(Color.FromArgb(181, 41, 42)), base.Width - 4, 1, 1, 1);
				graphics.FillRectangle(Brushes.Fuchsia, 0, base.Height - 1, 1, 1);
				graphics.FillRectangle(Brushes.Fuchsia, 0, base.Height - 2, 1, 1);
				graphics.FillRectangle(Brushes.Fuchsia, 0, base.Height - 3, 1, 1);
				graphics.FillRectangle(Brushes.Fuchsia, 0, base.Height - 4, 1, 1);
				graphics.FillRectangle(Brushes.Fuchsia, 1, base.Height - 1, 1, 1);
				graphics.FillRectangle(Brushes.Fuchsia, 2, base.Height - 1, 1, 1);
				graphics.FillRectangle(Brushes.Fuchsia, 3, base.Height - 1, 1, 1);
				graphics.FillRectangle(Brushes.Fuchsia, 1, base.Height - 1, 1, 1);
				graphics.FillRectangle(Brushes.Fuchsia, 1, base.Height - 2, 1, 1);
				graphics.FillRectangle(new SolidBrush(Color.FromArgb(32, 41, 50)), 1, base.Height - 3, 1, 1);
				graphics.FillRectangle(new SolidBrush(Color.FromArgb(32, 41, 50)), 1, base.Height - 4, 1, 1);
				graphics.FillRectangle(new SolidBrush(Color.FromArgb(32, 41, 50)), 3, base.Height - 2, 1, 1);
				graphics.FillRectangle(new SolidBrush(Color.FromArgb(32, 41, 50)), 2, base.Height - 2, 1, 1);
				graphics.FillRectangle(Brushes.Fuchsia, base.Width - 1, base.Height, 1, 1);
				graphics.FillRectangle(Brushes.Fuchsia, base.Width - 2, base.Height, 1, 1);
				graphics.FillRectangle(Brushes.Fuchsia, base.Width - 3, base.Height, 1, 1);
				graphics.FillRectangle(Brushes.Fuchsia, base.Width - 4, base.Height, 1, 1);
				graphics.FillRectangle(Brushes.Fuchsia, base.Width - 1, base.Height - 1, 1, 1);
				graphics.FillRectangle(Brushes.Fuchsia, base.Width - 1, base.Height - 2, 1, 1);
				graphics.FillRectangle(Brushes.Fuchsia, base.Width - 1, base.Height - 3, 1, 1);
				graphics.FillRectangle(Brushes.Fuchsia, base.Width - 2, base.Height - 1, 1, 1);
				graphics.FillRectangle(Brushes.Fuchsia, base.Width - 3, base.Height - 1, 1, 1);
				graphics.FillRectangle(Brushes.Fuchsia, base.Width - 4, base.Height - 1, 1, 1);
				graphics.FillRectangle(Brushes.Fuchsia, base.Width - 1, base.Height - 4, 1, 1);
				graphics.FillRectangle(Brushes.Fuchsia, base.Width - 2, base.Height - 2, 1, 1);
				graphics.FillRectangle(new SolidBrush(Color.FromArgb(32, 41, 50)), base.Width - 2, base.Height - 3, 1, 1);
				graphics.FillRectangle(new SolidBrush(Color.FromArgb(32, 41, 50)), base.Width - 2, base.Height - 4, 1, 1);
				graphics.FillRectangle(new SolidBrush(Color.FromArgb(32, 41, 50)), base.Width - 4, base.Height - 2, 1, 1);
				graphics.FillRectangle(new SolidBrush(Color.FromArgb(32, 41, 50)), base.Width - 3, base.Height - 2, 1, 1);
			}
			graphics.DrawString(this.Text, new Font("Microsoft Sans Serif", 12f, FontStyle.Bold), new SolidBrush(Color.FromArgb(255, 254, 255)), new Rectangle(20, 20, base.Width - 1, base.Height), new StringFormat
			{
				Alignment = StringAlignment.Near,
				LineAlignment = StringAlignment.Near
			});
		}
	}

	// Token: 0x040003E8 RID: 1000
	private Rectangle rectangle_0;

	// Token: 0x040003E9 RID: 1001
	protected Control41.Enum10 enum10_0;

	// Token: 0x040003EA RID: 1002
	private int int_0;

	// Token: 0x040003EB RID: 1003
	private Point point_0;

	// Token: 0x040003EC RID: 1004
	private bool bool_0;

	// Token: 0x040003ED RID: 1005
	private bool bool_1;

	// Token: 0x040003EE RID: 1006
	private bool bool_2;

	// Token: 0x040003EF RID: 1007
	private bool bool_3;

	// Token: 0x040003F0 RID: 1008
	private bool bool_4;

	// Token: 0x040003F1 RID: 1009
	private bool bool_5;

	// Token: 0x040003F2 RID: 1010
	private bool bool_6;

	// Token: 0x040003F3 RID: 1011
	private FormStartPosition formStartPosition_0;

	// Token: 0x040003F4 RID: 1012
	private Point point_1;

	// Token: 0x040003F5 RID: 1013
	private bool bool_7;

	// Token: 0x040003F6 RID: 1014
	private bool bool_8;

	// Token: 0x040003F7 RID: 1015
	private bool bool_9;

	// Token: 0x040003F8 RID: 1016
	private bool bool_10;

	// Token: 0x040003F9 RID: 1017
	private int int_1;

	// Token: 0x040003FA RID: 1018
	private int int_2;

	// Token: 0x040003FB RID: 1019
	private Message[] message_0;

	// Token: 0x040003FC RID: 1020
	private bool bool_11;

	// Token: 0x020000B2 RID: 178
	public enum Enum10 : byte
	{
		// Token: 0x040003FE RID: 1022
		None,
		// Token: 0x040003FF RID: 1023
		Over,
		// Token: 0x04000400 RID: 1024
		Down,
		// Token: 0x04000401 RID: 1025
		Block
	}
}

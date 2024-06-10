using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Imaging;
using System.IO;
using System.Windows.Forms;
using Microsoft.VisualBasic.CompilerServices;

// Token: 0x0200000C RID: 12
internal abstract class Control0 : ContainerControl
{
	// Token: 0x060000E3 RID: 227 RVA: 0x000050E8 File Offset: 0x000032E8
	public Control0()
	{
		this.message_0 = new Message[9];
		this.bool_8 = true;
		this.bool_9 = true;
		this.bool_10 = true;
		this.dictionary_0 = new Dictionary<string, Color>();
		this.int_4 = 24;
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.Opaque | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		this.size_0 = Size.Empty;
		this.System.Windows.Forms.Control.Font = new Font("Verdana", 8f);
		this.bitmap_1 = new Bitmap(1, 1);
		this.graphics_1 = Graphics.FromImage(this.bitmap_1);
		this.graphicsPath_0 = new GraphicsPath();
		this.method_16();
	}

	// Token: 0x060000E4 RID: 228 RVA: 0x0000518C File Offset: 0x0000338C
	protected sealed void OnHandleCreated(EventArgs e)
	{
		if (this.bool_0)
		{
			this.method_5();
		}
		this.method_16();
		this.Control0.\u206B\u202A\u206E\u200D\u206E\u206A\u202E\u200B\u200F\u200D\u206A\u202E\u200C\u200E\u200E\u200B\u200D\u202E\u200D\u200C\u206E\u200B\u206C\u206C\u202B\u206B\u206B\u200E\u200E\u202C\u206E\u206A\u202E\u200D\u202D\u200B\u206F\u200E\u206F\u202E\u202E();
		if (this.int_2 != 0)
		{
			base.Width = this.int_2;
		}
		if (this.int_3 != 0)
		{
			base.Height = this.int_3;
		}
		if (!this.bool_14)
		{
			this.method_73(DockStyle.Fill);
		}
		this.Boolean_4 = this.bool_12;
		if (this.bool_12 && this.bool_7)
		{
			this.System.Windows.Forms.Control.BackColor = Color.Transparent;
		}
		base.OnHandleCreated(e);
	}

	// Token: 0x060000E5 RID: 229 RVA: 0x00005224 File Offset: 0x00003424
	protected sealed void OnParentChanged(EventArgs e)
	{
		base.OnParentChanged(e);
		if (base.Parent != null)
		{
			this.bool_13 = (base.Parent is Form);
			if (!this.bool_14)
			{
				this.method_5();
				if (this.bool_13)
				{
					base.ParentForm.FormBorderStyle = this.formBorderStyle_0;
					base.ParentForm.TransparencyKey = this.color_0;
					if (!base.DesignMode)
					{
						base.ParentForm.Shown += this.method_1;
					}
				}
				base.Parent.BackColor = this.System.Windows.Forms.Control.BackColor;
			}
			this.vmethod_0();
			this.bool_0 = true;
			this.method_17();
		}
	}

	// Token: 0x060000E6 RID: 230 RVA: 0x000052DC File Offset: 0x000034DC
	private void method_0(bool bool_16)
	{
		this.vmethod_1();
		if (bool_16)
		{
			base.Invalidate();
		}
	}

	// Token: 0x060000E7 RID: 231 RVA: 0x000052F0 File Offset: 0x000034F0
	protected sealed void OnPaint(PaintEventArgs e)
	{
		if (base.Width != 0 && base.Height != 0)
		{
			if (this.bool_12 && this.bool_14)
			{
				this.Control0.\u206C\u200F\u202E\u202D\u202A\u202B\u200C\u202E\u206F\u202E\u206B\u206B\u202A\u206C\u200D\u206F\u206C\u200C\u202B\u202B\u206C\u202B\u200F\u200E\u200D\u206C\u200D\u202A\u200E\u206C\u206D\u202E\u200D\u202A\u206C\u206B\u202A\u206F\u206A\u202B\u202E();
				e.Graphics.DrawImage(this.bitmap_0, 0, 0);
			}
			else
			{
				this.graphics_0 = e.Graphics;
				this.Control0.\u206C\u200F\u202E\u202D\u202A\u202B\u200C\u202E\u206F\u202E\u206B\u206B\u202A\u206C\u200D\u206F\u206C\u200C\u202B\u202B\u206C\u202B\u200F\u200E\u200D\u206C\u200D\u202A\u200E\u206C\u206D\u202E\u200D\u202A\u206C\u206B\u202A\u206F\u206A\u202B\u202E();
			}
		}
	}

	// Token: 0x060000E8 RID: 232 RVA: 0x00005354 File Offset: 0x00003554
	protected virtual void OnHandleDestroyed(EventArgs e)
	{
		Class7.smethod_3(new Class7.Delegate0(this.method_0));
		base.OnHandleDestroyed(e);
	}

	// Token: 0x060000E9 RID: 233 RVA: 0x00005370 File Offset: 0x00003570
	private void method_1(object sender, EventArgs e)
	{
		if (!this.bool_14 && !this.bool_1)
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

	// Token: 0x060000EA RID: 234 RVA: 0x000053FC File Offset: 0x000035FC
	protected sealed void OnSizeChanged(EventArgs e)
	{
		if (this.bool_9 && !this.bool_14)
		{
			this.rectangle_0 = checked(new Rectangle(7, 7, base.Width - 14, this.int_4 - 7));
		}
		this.method_15();
		base.Invalidate();
		base.OnSizeChanged(e);
	}

	// Token: 0x060000EB RID: 235 RVA: 0x00005450 File Offset: 0x00003650
	protected virtual void SetBoundsCore(int x, int y, int width, int height, BoundsSpecified specified)
	{
		if (this.int_2 != 0)
		{
			width = this.int_2;
		}
		if (this.int_3 != 0)
		{
			height = this.int_3;
		}
		base.SetBoundsCore(x, y, width, height, specified);
	}

	// Token: 0x060000EC RID: 236 RVA: 0x00005488 File Offset: 0x00003688
	private void method_2(Enum0 enum0_1)
	{
		this.enum0_0 = enum0_1;
		base.Invalidate();
	}

	// Token: 0x060000ED RID: 237 RVA: 0x00005498 File Offset: 0x00003698
	protected virtual void OnMouseMove(MouseEventArgs e)
	{
		if ((!this.bool_13 || base.ParentForm.WindowState != FormWindowState.Maximized) && (this.bool_10 && !this.bool_14))
		{
			this.method_4();
		}
		base.OnMouseMove(e);
	}

	// Token: 0x060000EE RID: 238 RVA: 0x000054E8 File Offset: 0x000036E8
	protected virtual void OnEnabledChanged(EventArgs e)
	{
		if (!base.Enabled)
		{
			this.method_2(Enum0.Block);
		}
		else
		{
			this.method_2(Enum0.None);
		}
		base.OnEnabledChanged(e);
	}

	// Token: 0x060000EF RID: 239 RVA: 0x0000550C File Offset: 0x0000370C
	protected virtual void OnMouseEnter(EventArgs e)
	{
		this.method_2(Enum0.Over);
		base.OnMouseEnter(e);
	}

	// Token: 0x060000F0 RID: 240 RVA: 0x0000551C File Offset: 0x0000371C
	protected virtual void OnMouseUp(MouseEventArgs e)
	{
		this.method_2(Enum0.Over);
		base.OnMouseUp(e);
	}

	// Token: 0x060000F1 RID: 241 RVA: 0x0000552C File Offset: 0x0000372C
	protected virtual void OnMouseLeave(EventArgs e)
	{
		this.method_2(Enum0.None);
		if (base.GetChildAtPoint(base.PointToClient(Control.MousePosition)) != null && (this.bool_10 && !this.bool_14))
		{
			this.Cursor = Cursors.Default;
			this.int_1 = 0;
		}
		base.OnMouseLeave(e);
	}

	// Token: 0x060000F2 RID: 242 RVA: 0x00005588 File Offset: 0x00003788
	protected virtual void OnMouseDown(MouseEventArgs e)
	{
		if (e.Button == MouseButtons.Left)
		{
			this.method_2(Enum0.Down);
		}
		bool flag;
		if (this.bool_13)
		{
			if (base.ParentForm.WindowState == FormWindowState.Maximized)
			{
				flag = false;
				goto IL_3A;
			}
		}
		flag = !this.bool_14;
		IL_3A:
		if (flag)
		{
			if (!this.bool_9 || !this.rectangle_0.Contains(e.Location))
			{
				if (this.bool_10 && this.int_1 != 0)
				{
					base.Capture = false;
					this.bool_2 = true;
					this.DefWndProc(ref this.message_0[this.int_1]);
				}
			}
			else
			{
				base.Capture = false;
				this.bool_2 = true;
				this.DefWndProc(ref this.message_0[0]);
			}
		}
		base.OnMouseDown(e);
	}

	// Token: 0x060000F3 RID: 243 RVA: 0x00005654 File Offset: 0x00003854
	protected virtual void WndProc(ref Message m)
	{
		base.WndProc(ref m);
		if (this.bool_2 && m.Msg == 513)
		{
			this.bool_2 = false;
			this.method_2(Enum0.Over);
			if (this.bool_8)
			{
				if (!this.Boolean_6)
				{
					this.method_6(Screen.FromControl(base.Parent).WorkingArea);
				}
				else
				{
					this.method_6(new Rectangle(Point.Empty, base.Parent.Parent.Size));
				}
			}
		}
	}

	// Token: 0x060000F4 RID: 244 RVA: 0x000056DC File Offset: 0x000038DC
	private int method_3()
	{
		this.point_0 = base.PointToClient(Control.MousePosition);
		this.bool_3 = (this.point_0.X < 7);
		checked
		{
			this.bool_4 = (this.point_0.X > base.Width - 7);
			this.bool_5 = (this.point_0.Y < 7);
			this.bool_6 = (this.point_0.Y > base.Height - 7);
			int result;
			if (this.bool_3 && this.bool_5)
			{
				result = 4;
			}
			else if (!this.bool_3 || !this.bool_6)
			{
				if (this.bool_4 && this.bool_5)
				{
					result = 5;
				}
				else if (this.bool_4 && this.bool_6)
				{
					result = 8;
				}
				else if (this.bool_3)
				{
					result = 1;
				}
				else if (!this.bool_4)
				{
					if (!this.bool_5)
					{
						if (this.bool_6)
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
					result = 2;
				}
			}
			else
			{
				result = 7;
			}
			return result;
		}
	}

	// Token: 0x060000F5 RID: 245 RVA: 0x000057E8 File Offset: 0x000039E8
	private void method_4()
	{
		this.int_0 = this.method_3();
		if (this.int_0 != this.int_1)
		{
			this.int_1 = this.int_0;
			switch (this.int_1)
			{
			case 0:
				this.Cursor = Cursors.Default;
				break;
			case 1:
			case 2:
				this.Cursor = Cursors.SizeWE;
				break;
			case 3:
			case 6:
				this.Cursor = Cursors.SizeNS;
				break;
			case 4:
			case 8:
				this.Cursor = Cursors.SizeNWSE;
				break;
			case 5:
			case 7:
				this.Cursor = Cursors.SizeNESW;
				break;
			}
		}
	}

	// Token: 0x060000F6 RID: 246 RVA: 0x00005890 File Offset: 0x00003A90
	private void method_5()
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

	// Token: 0x060000F7 RID: 247 RVA: 0x00005908 File Offset: 0x00003B08
	private void method_6(Rectangle rectangle_5)
	{
		if (base.Parent.Width > rectangle_5.Width)
		{
			base.Parent.Width = rectangle_5.Width;
		}
		if (base.Parent.Height > rectangle_5.Height)
		{
			base.Parent.Height = rectangle_5.Height;
		}
		int num = base.Parent.Location.X;
		int num2 = base.Parent.Location.Y;
		if (num < rectangle_5.X)
		{
			num = rectangle_5.X;
		}
		if (num2 < rectangle_5.Y)
		{
			num2 = rectangle_5.Y;
		}
		checked
		{
			int num3 = rectangle_5.X + rectangle_5.Width;
			int num4 = rectangle_5.Y + rectangle_5.Height;
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

	// Token: 0x1700009E RID: 158
	// (get) Token: 0x060000F8 RID: 248 RVA: 0x00005A2C File Offset: 0x00003C2C
	// (set) Token: 0x060000F9 RID: 249 RVA: 0x00005A44 File Offset: 0x00003C44
	public virtual DockStyle Dock
	{
		get
		{
			return this.method_74();
		}
		set
		{
			if (this.bool_14)
			{
				this.method_73(value);
			}
		}
	}

	// Token: 0x1700009F RID: 159
	// (get) Token: 0x060000FA RID: 250 RVA: 0x00005A58 File Offset: 0x00003C58
	// (set) Token: 0x060000FB RID: 251 RVA: 0x00005A70 File Offset: 0x00003C70
	[Category("Misc")]
	public virtual Color BackColor
	{
		get
		{
			return this.method_75();
		}
		set
		{
			if (!(value == this.method_75()))
			{
				if (!base.IsHandleCreated && this.bool_14 && value == Color.Transparent)
				{
					this.bool_7 = true;
				}
				else
				{
					this.method_76(value);
					if (base.Parent != null)
					{
						if (!this.bool_14)
						{
							base.Parent.BackColor = value;
						}
						this.Control0.\u206B\u202A\u206E\u200D\u206E\u206A\u202E\u200B\u200F\u200D\u206A\u202E\u200C\u200E\u200E\u200B\u200D\u202E\u200D\u200C\u206E\u200B\u206C\u206C\u202B\u206B\u206B\u200E\u200E\u202C\u206E\u206A\u202E\u200D\u202D\u200B\u206F\u200E\u206F\u202E\u202E();
					}
				}
			}
		}
	}

	// Token: 0x170000A0 RID: 160
	// (get) Token: 0x060000FC RID: 252 RVA: 0x00005AE4 File Offset: 0x00003CE4
	// (set) Token: 0x060000FD RID: 253 RVA: 0x00005AFC File Offset: 0x00003CFC
	public virtual Size MinimumSize
	{
		get
		{
			return this.method_77();
		}
		set
		{
			this.method_78(value);
			if (base.Parent != null)
			{
				base.Parent.MinimumSize = value;
			}
		}
	}

	// Token: 0x170000A1 RID: 161
	// (get) Token: 0x060000FE RID: 254 RVA: 0x00005B1C File Offset: 0x00003D1C
	// (set) Token: 0x060000FF RID: 255 RVA: 0x00005B34 File Offset: 0x00003D34
	public virtual Size MaximumSize
	{
		get
		{
			return this.method_79();
		}
		set
		{
			this.method_80(value);
			if (base.Parent != null)
			{
				base.Parent.MaximumSize = value;
			}
		}
	}

	// Token: 0x170000A2 RID: 162
	// (get) Token: 0x06000100 RID: 256 RVA: 0x00005B54 File Offset: 0x00003D54
	// (set) Token: 0x06000101 RID: 257 RVA: 0x00005B6C File Offset: 0x00003D6C
	public virtual string Text
	{
		get
		{
			return this.method_81();
		}
		set
		{
			this.method_82(value);
			base.Invalidate();
		}
	}

	// Token: 0x170000A3 RID: 163
	// (get) Token: 0x06000102 RID: 258 RVA: 0x00005B7C File Offset: 0x00003D7C
	// (set) Token: 0x06000103 RID: 259 RVA: 0x00005B94 File Offset: 0x00003D94
	public virtual Font Font
	{
		get
		{
			return this.method_83();
		}
		set
		{
			this.method_84(value);
			base.Invalidate();
		}
	}

	// Token: 0x170000A4 RID: 164
	// (get) Token: 0x06000104 RID: 260 RVA: 0x00005BA4 File Offset: 0x00003DA4
	// (set) Token: 0x06000105 RID: 261 RVA: 0x00005BB8 File Offset: 0x00003DB8
	[Browsable(false)]
	[EditorBrowsable(EditorBrowsableState.Never)]
	[DesignerSerializationVisibility(DesignerSerializationVisibility.Hidden)]
	public virtual Color ForeColor
	{
		get
		{
			return Color.Empty;
		}
		set
		{
		}
	}

	// Token: 0x170000A5 RID: 165
	// (get) Token: 0x06000106 RID: 262 RVA: 0x00005BBC File Offset: 0x00003DBC
	// (set) Token: 0x06000107 RID: 263 RVA: 0x00005BCC File Offset: 0x00003DCC
	[Browsable(false)]
	[EditorBrowsable(EditorBrowsableState.Never)]
	[DesignerSerializationVisibility(DesignerSerializationVisibility.Hidden)]
	public virtual Image BackgroundImage
	{
		get
		{
			return null;
		}
		set
		{
		}
	}

	// Token: 0x170000A6 RID: 166
	// (get) Token: 0x06000108 RID: 264 RVA: 0x00005BD0 File Offset: 0x00003DD0
	// (set) Token: 0x06000109 RID: 265 RVA: 0x00005BE0 File Offset: 0x00003DE0
	[EditorBrowsable(EditorBrowsableState.Never)]
	[Browsable(false)]
	[DesignerSerializationVisibility(DesignerSerializationVisibility.Hidden)]
	public virtual ImageLayout BackgroundImageLayout
	{
		get
		{
			return ImageLayout.None;
		}
		set
		{
		}
	}

	// Token: 0x170000A7 RID: 167
	// (get) Token: 0x0600010A RID: 266 RVA: 0x00005BE4 File Offset: 0x00003DE4
	// (set) Token: 0x0600010B RID: 267 RVA: 0x00005BEC File Offset: 0x00003DEC
	public bool Boolean_0
	{
		get
		{
			return this.bool_8;
		}
		set
		{
			this.bool_8 = value;
		}
	}

	// Token: 0x170000A8 RID: 168
	// (get) Token: 0x0600010C RID: 268 RVA: 0x00005BF8 File Offset: 0x00003DF8
	// (set) Token: 0x0600010D RID: 269 RVA: 0x00005C00 File Offset: 0x00003E00
	public bool Boolean_1
	{
		get
		{
			return this.bool_9;
		}
		set
		{
			this.bool_9 = value;
		}
	}

	// Token: 0x170000A9 RID: 169
	// (get) Token: 0x0600010E RID: 270 RVA: 0x00005C0C File Offset: 0x00003E0C
	// (set) Token: 0x0600010F RID: 271 RVA: 0x00005C14 File Offset: 0x00003E14
	public bool Boolean_2
	{
		get
		{
			return this.bool_10;
		}
		set
		{
			this.bool_10 = value;
		}
	}

	// Token: 0x170000AA RID: 170
	// (get) Token: 0x06000110 RID: 272 RVA: 0x00005C20 File Offset: 0x00003E20
	// (set) Token: 0x06000111 RID: 273 RVA: 0x00005C5C File Offset: 0x00003E5C
	public Color Color_0
	{
		get
		{
			Color transparencyKey;
			if (this.bool_13 && !this.bool_14)
			{
				transparencyKey = base.ParentForm.TransparencyKey;
			}
			else
			{
				transparencyKey = this.color_0;
			}
			return transparencyKey;
		}
		set
		{
			if (!(value == this.color_0))
			{
				this.color_0 = value;
				if (this.bool_13 && !this.bool_14)
				{
					base.ParentForm.TransparencyKey = value;
					this.Control0.\u206B\u202A\u206E\u200D\u206E\u206A\u202E\u200B\u200F\u200D\u206A\u202E\u200C\u200E\u200E\u200B\u200D\u202E\u200D\u200C\u206E\u200B\u206C\u206C\u202B\u206B\u206B\u200E\u200E\u202C\u206E\u206A\u202E\u200D\u202D\u200B\u206F\u200E\u206F\u202E\u202E();
				}
			}
		}
	}

	// Token: 0x170000AB RID: 171
	// (get) Token: 0x06000112 RID: 274 RVA: 0x00005C9C File Offset: 0x00003E9C
	// (set) Token: 0x06000113 RID: 275 RVA: 0x00005CD8 File Offset: 0x00003ED8
	public FormBorderStyle FormBorderStyle_0
	{
		get
		{
			FormBorderStyle formBorderStyle;
			if (this.bool_13 && !this.bool_14)
			{
				formBorderStyle = base.ParentForm.FormBorderStyle;
			}
			else
			{
				formBorderStyle = this.formBorderStyle_0;
			}
			return formBorderStyle;
		}
		set
		{
			this.formBorderStyle_0 = value;
			if (this.bool_13 && !this.bool_14)
			{
				base.ParentForm.FormBorderStyle = value;
				if (value > FormBorderStyle.None)
				{
					this.Boolean_1 = false;
					this.Boolean_2 = false;
				}
			}
		}
	}

	// Token: 0x170000AC RID: 172
	// (get) Token: 0x06000114 RID: 276 RVA: 0x00005D18 File Offset: 0x00003F18
	// (set) Token: 0x06000115 RID: 277 RVA: 0x00005D54 File Offset: 0x00003F54
	public FormStartPosition FormStartPosition_0
	{
		get
		{
			FormStartPosition startPosition;
			if (this.bool_13 && !this.bool_14)
			{
				startPosition = base.ParentForm.StartPosition;
			}
			else
			{
				startPosition = this.formStartPosition_0;
			}
			return startPosition;
		}
		set
		{
			this.formStartPosition_0 = value;
			if (this.bool_13 && !this.bool_14)
			{
				base.ParentForm.StartPosition = value;
			}
		}
	}

	// Token: 0x170000AD RID: 173
	// (get) Token: 0x06000116 RID: 278 RVA: 0x00005D80 File Offset: 0x00003F80
	// (set) Token: 0x06000117 RID: 279 RVA: 0x00005D88 File Offset: 0x00003F88
	public bool Boolean_3
	{
		get
		{
			return this.bool_11;
		}
		set
		{
			this.bool_11 = value;
			base.Invalidate();
		}
	}

	// Token: 0x170000AE RID: 174
	// (get) Token: 0x06000118 RID: 280 RVA: 0x00005D98 File Offset: 0x00003F98
	// (set) Token: 0x06000119 RID: 281 RVA: 0x00005DB0 File Offset: 0x00003FB0
	public Image Image_0
	{
		get
		{
			return this.image_0;
		}
		set
		{
			if (value == null)
			{
				this.size_0 = Size.Empty;
			}
			else
			{
				this.size_0 = value.Size;
			}
			this.image_0 = value;
			base.Invalidate();
		}
	}

	// Token: 0x170000AF RID: 175
	// (get) Token: 0x0600011A RID: 282 RVA: 0x00005DE0 File Offset: 0x00003FE0
	// (set) Token: 0x0600011B RID: 283 RVA: 0x00005E3C File Offset: 0x0000403C
	public Struct0[] Struct0_0
	{
		get
		{
			List<Struct0> list = new List<Struct0>();
			Dictionary<string, Color>.Enumerator enumerator = this.dictionary_0.GetEnumerator();
			while (enumerator.MoveNext())
			{
				List<Struct0> list2 = list;
				KeyValuePair<string, Color> keyValuePair = enumerator.Current;
				string key = keyValuePair.Key;
				keyValuePair = enumerator.Current;
				list2.Add(new Struct0(key, keyValuePair.Value));
			}
			return list.ToArray();
		}
		set
		{
			checked
			{
				for (int i = 0; i < value.Length; i++)
				{
					Struct0 @struct = value[i];
					if (this.dictionary_0.ContainsKey(@struct.String_0))
					{
						this.dictionary_0[@struct.String_0] = @struct.Color_0;
					}
				}
				this.method_16();
				this.Control0.\u206B\u202A\u206E\u200D\u206E\u206A\u202E\u200B\u200F\u200D\u206A\u202E\u200C\u200E\u200E\u200B\u200D\u202E\u200D\u200C\u206E\u200B\u206C\u206C\u202B\u206B\u206B\u200E\u200E\u202C\u206E\u206A\u202E\u200D\u202D\u200B\u206F\u200E\u206F\u202E\u202E();
				base.Invalidate();
			}
		}
	}

	// Token: 0x170000B0 RID: 176
	// (get) Token: 0x0600011C RID: 284 RVA: 0x00005EA4 File Offset: 0x000040A4
	// (set) Token: 0x0600011D RID: 285 RVA: 0x00005EBC File Offset: 0x000040BC
	public string String_0
	{
		get
		{
			return this.string_0;
		}
		set
		{
			checked
			{
				if (Operators.CompareString(value, this.string_0, false) != 0)
				{
					Struct0[] struct0_ = this.Struct0_0;
					try
					{
						byte[] value2 = Convert.FromBase64String(value);
						int num = struct0_.Length - 1;
						for (int i = 0; i <= num; i++)
						{
							struct0_[i].Color_0 = Color.FromArgb(BitConverter.ToInt32(value2, i * 4));
						}
					}
					catch (Exception ex)
					{
						return;
					}
					this.string_0 = value;
					this.Struct0_0 = struct0_;
					this.Control0.\u206B\u202A\u206E\u200D\u206E\u206A\u202E\u200B\u200F\u200D\u206A\u202E\u200C\u200E\u200E\u200B\u200D\u202E\u200D\u200C\u206E\u200B\u206C\u206C\u202B\u206B\u206B\u200E\u200E\u202C\u206E\u206A\u202E\u200D\u202D\u200B\u206F\u200E\u206F\u202E\u202E();
					base.Invalidate();
				}
			}
		}
	}

	// Token: 0x170000B1 RID: 177
	// (get) Token: 0x0600011E RID: 286 RVA: 0x00005F50 File Offset: 0x00004150
	// (set) Token: 0x0600011F RID: 287 RVA: 0x00005F58 File Offset: 0x00004158
	public bool Boolean_4
	{
		get
		{
			return this.bool_12;
		}
		set
		{
			this.bool_12 = value;
			if (base.IsHandleCreated || this.bool_14)
			{
				if (!value && this.System.Windows.Forms.Control.BackColor.A != 255)
				{
					throw new Exception("Unable to change value to false while a transparent BackColor is in use.");
				}
				base.SetStyle(ControlStyles.Opaque, !value);
				base.SetStyle(ControlStyles.SupportsTransparentBackColor, value);
				this.method_15();
				base.Invalidate();
			}
		}
	}

	// Token: 0x170000B2 RID: 178
	// (get) Token: 0x06000120 RID: 288 RVA: 0x00005FD0 File Offset: 0x000041D0
	protected Size Size_0
	{
		get
		{
			return this.size_0;
		}
	}

	// Token: 0x170000B3 RID: 179
	// (get) Token: 0x06000121 RID: 289 RVA: 0x00005FE8 File Offset: 0x000041E8
	protected bool Boolean_5
	{
		get
		{
			return this.bool_13;
		}
	}

	// Token: 0x170000B4 RID: 180
	// (get) Token: 0x06000122 RID: 290 RVA: 0x00005FF0 File Offset: 0x000041F0
	protected bool Boolean_6
	{
		get
		{
			return base.Parent != null && base.Parent.Parent != null;
		}
	}

	// Token: 0x170000B5 RID: 181
	// (get) Token: 0x06000123 RID: 291 RVA: 0x0000601C File Offset: 0x0000421C
	// (set) Token: 0x06000124 RID: 292 RVA: 0x00006034 File Offset: 0x00004234
	protected int Int32_0
	{
		get
		{
			return this.int_2;
		}
		set
		{
			this.int_2 = value;
			if (this.Int32_0 != 0 && base.IsHandleCreated)
			{
				base.Width = this.Int32_0;
			}
		}
	}

	// Token: 0x170000B6 RID: 182
	// (get) Token: 0x06000125 RID: 293 RVA: 0x0000605C File Offset: 0x0000425C
	// (set) Token: 0x06000126 RID: 294 RVA: 0x00006074 File Offset: 0x00004274
	protected int Int32_1
	{
		get
		{
			return this.int_3;
		}
		set
		{
			this.int_3 = value;
			if (this.Int32_1 != 0 && base.IsHandleCreated)
			{
				base.Height = this.Int32_1;
			}
		}
	}

	// Token: 0x170000B7 RID: 183
	// (get) Token: 0x06000127 RID: 295 RVA: 0x0000609C File Offset: 0x0000429C
	// (set) Token: 0x06000128 RID: 296 RVA: 0x000060B4 File Offset: 0x000042B4
	protected int Int32_2
	{
		get
		{
			return this.int_4;
		}
		set
		{
			this.int_4 = value;
			if (!this.bool_14)
			{
				this.rectangle_0 = checked(new Rectangle(7, 7, base.Width - 14, value - 7));
				base.Invalidate();
			}
		}
	}

	// Token: 0x170000B8 RID: 184
	// (get) Token: 0x06000129 RID: 297 RVA: 0x000060E8 File Offset: 0x000042E8
	// (set) Token: 0x0600012A RID: 298 RVA: 0x000060F0 File Offset: 0x000042F0
	protected bool Boolean_7
	{
		get
		{
			return this.bool_14;
		}
		set
		{
			this.bool_14 = value;
			this.Boolean_4 = this.bool_12;
			if (this.bool_12 && this.bool_7)
			{
				this.System.Windows.Forms.Control.BackColor = Color.Transparent;
			}
			this.method_15();
			base.Invalidate();
		}
	}

	// Token: 0x170000B9 RID: 185
	// (get) Token: 0x0600012B RID: 299 RVA: 0x00006130 File Offset: 0x00004330
	// (set) Token: 0x0600012C RID: 300 RVA: 0x00006138 File Offset: 0x00004338
	protected bool Boolean_8
	{
		get
		{
			return this.bool_15;
		}
		set
		{
			this.bool_15 = value;
			this.method_17();
		}
	}

	// Token: 0x0600012D RID: 301 RVA: 0x00006148 File Offset: 0x00004348
	protected Pen method_7(string string_1)
	{
		return new Pen(this.dictionary_0[string_1]);
	}

	// Token: 0x0600012E RID: 302 RVA: 0x00006168 File Offset: 0x00004368
	protected Pen method_8(string string_1, float float_0)
	{
		return new Pen(this.dictionary_0[string_1], float_0);
	}

	// Token: 0x0600012F RID: 303 RVA: 0x0000618C File Offset: 0x0000438C
	protected SolidBrush method_9(string string_1)
	{
		return new SolidBrush(this.dictionary_0[string_1]);
	}

	// Token: 0x06000130 RID: 304 RVA: 0x000061AC File Offset: 0x000043AC
	protected Color method_10(string string_1)
	{
		return this.dictionary_0[string_1];
	}

	// Token: 0x06000131 RID: 305 RVA: 0x000061C8 File Offset: 0x000043C8
	protected void method_11(string string_1, Color color_1)
	{
		if (!this.dictionary_0.ContainsKey(string_1))
		{
			this.dictionary_0.Add(string_1, color_1);
		}
		else
		{
			this.dictionary_0[string_1] = color_1;
		}
	}

	// Token: 0x06000132 RID: 306 RVA: 0x000061F4 File Offset: 0x000043F4
	protected void method_12(string string_1, byte byte_0, byte byte_1, byte byte_2)
	{
		this.method_11(string_1, Color.FromArgb((int)byte_0, (int)byte_1, (int)byte_2));
	}

	// Token: 0x06000133 RID: 307 RVA: 0x00006208 File Offset: 0x00004408
	protected void method_13(string string_1, byte byte_0, byte byte_1, byte byte_2, byte byte_3)
	{
		this.method_11(string_1, Color.FromArgb((int)byte_0, (int)byte_1, (int)byte_2, (int)byte_3));
	}

	// Token: 0x06000134 RID: 308 RVA: 0x0000621C File Offset: 0x0000441C
	protected void method_14(string string_1, byte byte_0, Color color_1)
	{
		this.method_11(string_1, Color.FromArgb((int)byte_0, color_1));
	}

	// Token: 0x06000135 RID: 309 RVA: 0x0000622C File Offset: 0x0000442C
	private void method_15()
	{
		if (!this.bool_12 || !this.bool_14)
		{
			this.graphics_0 = null;
			this.bitmap_0 = null;
		}
		else if (base.Width != 0 && base.Height != 0)
		{
			this.bitmap_0 = new Bitmap(base.Width, base.Height, PixelFormat.Format32bppPArgb);
			this.graphics_0 = Graphics.FromImage(this.bitmap_0);
		}
	}

	// Token: 0x06000136 RID: 310 RVA: 0x000062A0 File Offset: 0x000044A0
	private void method_16()
	{
		MemoryStream memoryStream = new MemoryStream(checked(this.dictionary_0.Count * 4));
		foreach (Struct0 @struct in this.Struct0_0)
		{
			memoryStream.Write(BitConverter.GetBytes(@struct.Color_0.ToArgb()), 0, 4);
		}
		memoryStream.Close();
		this.string_0 = Convert.ToBase64String(memoryStream.ToArray());
	}

	// Token: 0x06000137 RID: 311 RVA: 0x00006314 File Offset: 0x00004514
	private void method_17()
	{
		if (!base.DesignMode && this.bool_0)
		{
			if (!this.bool_15)
			{
				Class7.smethod_3(new Class7.Delegate0(this.method_0));
			}
			else
			{
				Class7.smethod_2(new Class7.Delegate0(this.method_0));
			}
		}
	}

	// Token: 0x06000138 RID: 312
	protected abstract void \u206B\u202A\u206E\u200D\u206E\u206A\u202E\u200B\u200F\u200D\u206A\u202E\u200C\u200E\u200E\u200B\u200D\u202E\u200D\u200C\u206E\u200B\u206C\u206C\u202B\u206B\u206B\u200E\u200E\u202C\u206E\u206A\u202E\u200D\u202D\u200B\u206F\u200E\u206F\u202E\u202E();

	// Token: 0x06000139 RID: 313
	protected abstract void \u206C\u200F\u202E\u202D\u202A\u202B\u200C\u202E\u206F\u202E\u206B\u206B\u202A\u206C\u200D\u206F\u206C\u200C\u202B\u202B\u206C\u202B\u200F\u200E\u200D\u206C\u200D\u202A\u200E\u206C\u206D\u202E\u200D\u202A\u206C\u206B\u202A\u206F\u206A\u202B\u202E();

	// Token: 0x0600013A RID: 314 RVA: 0x00006364 File Offset: 0x00004564
	protected virtual void vmethod_0()
	{
	}

	// Token: 0x0600013B RID: 315 RVA: 0x00006368 File Offset: 0x00004568
	protected virtual void vmethod_1()
	{
	}

	// Token: 0x0600013C RID: 316 RVA: 0x0000636C File Offset: 0x0000456C
	protected Rectangle method_18(Rectangle rectangle_5, int int_5)
	{
		this.rectangle_1 = checked(new Rectangle(rectangle_5.X + int_5, rectangle_5.Y + int_5, rectangle_5.Width - int_5 * 2, rectangle_5.Height - int_5 * 2));
		return this.rectangle_1;
	}

	// Token: 0x0600013D RID: 317 RVA: 0x000063B4 File Offset: 0x000045B4
	protected Size method_19(Size size_3, int int_5)
	{
		this.size_1 = checked(new Size(size_3.Width + int_5, size_3.Height + int_5));
		return this.size_1;
	}

	// Token: 0x0600013E RID: 318 RVA: 0x000063E8 File Offset: 0x000045E8
	protected Point method_20(Point point_5, int int_5)
	{
		this.point_1 = checked(new Point(point_5.X + int_5, point_5.Y + int_5));
		return this.point_1;
	}

	// Token: 0x0600013F RID: 319 RVA: 0x0000641C File Offset: 0x0000461C
	protected Point method_21(Rectangle rectangle_5, Rectangle rectangle_6)
	{
		this.point_2 = checked(new Point(rectangle_5.Width / 2 - rectangle_6.Width / 2 + rectangle_5.X + rectangle_6.X, rectangle_5.Height / 2 - rectangle_6.Height / 2 + rectangle_5.Y + rectangle_6.Y));
		return this.point_2;
	}

	// Token: 0x06000140 RID: 320 RVA: 0x00006484 File Offset: 0x00004684
	protected Point method_22(Rectangle rectangle_5, Size size_3)
	{
		this.point_2 = checked(new Point(rectangle_5.Width / 2 - size_3.Width / 2 + rectangle_5.X, rectangle_5.Height / 2 - size_3.Height / 2 + rectangle_5.Y));
		return this.point_2;
	}

	// Token: 0x06000141 RID: 321 RVA: 0x000064DC File Offset: 0x000046DC
	protected Point method_23(Rectangle rectangle_5)
	{
		return this.method_27(base.Width, base.Height, rectangle_5.Width, rectangle_5.Height);
	}

	// Token: 0x06000142 RID: 322 RVA: 0x0000650C File Offset: 0x0000470C
	protected Point method_24(Size size_3)
	{
		return this.method_27(base.Width, base.Height, size_3.Width, size_3.Height);
	}

	// Token: 0x06000143 RID: 323 RVA: 0x0000653C File Offset: 0x0000473C
	protected Point method_25(int int_5, int int_6)
	{
		return this.method_27(base.Width, base.Height, int_5, int_6);
	}

	// Token: 0x06000144 RID: 324 RVA: 0x00006560 File Offset: 0x00004760
	protected Point method_26(Size size_3, Size size_4)
	{
		return this.method_27(size_3.Width, size_3.Height, size_4.Width, size_4.Height);
	}

	// Token: 0x06000145 RID: 325 RVA: 0x00006594 File Offset: 0x00004794
	protected Point method_27(int int_5, int int_6, int int_7, int int_8)
	{
		this.point_2 = checked(new Point(int_5 / 2 - int_7 / 2, int_6 / 2 - int_8 / 2));
		return this.point_2;
	}

	// Token: 0x06000146 RID: 326 RVA: 0x000065C4 File Offset: 0x000047C4
	protected Size method_28()
	{
		object obj = this.graphics_1;
		Size result;
		lock (obj)
		{
			result = this.graphics_1.MeasureString(this.System.Windows.Forms.Control.Text, this.System.Windows.Forms.Control.Font, base.Width).ToSize();
		}
		return result;
	}

	// Token: 0x06000147 RID: 327 RVA: 0x00006628 File Offset: 0x00004828
	protected Size method_29(string string_1)
	{
		object obj = this.graphics_1;
		Size result;
		lock (obj)
		{
			result = this.graphics_1.MeasureString(string_1, this.System.Windows.Forms.Control.Font, base.Width).ToSize();
		}
		return result;
	}

	// Token: 0x06000148 RID: 328 RVA: 0x00006684 File Offset: 0x00004884
	protected void method_30(Color color_1, int int_5, int int_6)
	{
		if (this.bool_12)
		{
			this.bitmap_0.SetPixel(int_5, int_6, color_1);
		}
		else
		{
			this.solidBrush_0 = new SolidBrush(color_1);
			this.graphics_0.FillRectangle(this.solidBrush_0, int_5, int_6, 1, 1);
		}
	}

	// Token: 0x06000149 RID: 329 RVA: 0x000066C0 File Offset: 0x000048C0
	protected void method_31(Color color_1, int int_5)
	{
		this.method_33(color_1, 0, 0, base.Width, base.Height, int_5);
	}

	// Token: 0x0600014A RID: 330 RVA: 0x000066D8 File Offset: 0x000048D8
	protected void method_32(Color color_1, Rectangle rectangle_5, int int_5)
	{
		this.method_33(color_1, rectangle_5.X, rectangle_5.Y, rectangle_5.Width, rectangle_5.Height, int_5);
	}

	// Token: 0x0600014B RID: 331 RVA: 0x00006700 File Offset: 0x00004900
	protected void method_33(Color color_1, int int_5, int int_6, int int_7, int int_8, int int_9)
	{
		checked
		{
			this.method_36(color_1, int_5 + int_9, int_6 + int_9, int_7 - int_9 * 2, int_8 - int_9 * 2);
		}
	}

	// Token: 0x0600014C RID: 332 RVA: 0x00006720 File Offset: 0x00004920
	protected void method_34(Color color_1)
	{
		this.method_36(color_1, 0, 0, base.Width, base.Height);
	}

	// Token: 0x0600014D RID: 333 RVA: 0x00006738 File Offset: 0x00004938
	protected void method_35(Color color_1, Rectangle rectangle_5)
	{
		this.method_36(color_1, rectangle_5.X, rectangle_5.Y, rectangle_5.Width, rectangle_5.Height);
	}

	// Token: 0x0600014E RID: 334 RVA: 0x00006760 File Offset: 0x00004960
	protected void method_36(Color color_1, int int_5, int int_6, int int_7, int int_8)
	{
		checked
		{
			if (!this.bool_11)
			{
				if (!this.bool_12)
				{
					this.solidBrush_1 = new SolidBrush(color_1);
					this.graphics_0.FillRectangle(this.solidBrush_1, int_5, int_6, 1, 1);
					this.graphics_0.FillRectangle(this.solidBrush_1, int_5 + (int_7 - 1), int_6, 1, 1);
					this.graphics_0.FillRectangle(this.solidBrush_1, int_5, int_6 + (int_8 - 1), 1, 1);
					this.graphics_0.FillRectangle(this.solidBrush_1, int_5 + (int_7 - 1), int_6 + (int_8 - 1), 1, 1);
				}
				else
				{
					this.bitmap_0.SetPixel(int_5, int_6, color_1);
					this.bitmap_0.SetPixel(int_5 + (int_7 - 1), int_6, color_1);
					this.bitmap_0.SetPixel(int_5, int_6 + (int_8 - 1), color_1);
					this.bitmap_0.SetPixel(int_5 + (int_7 - 1), int_6 + (int_8 - 1), color_1);
				}
			}
		}
	}

	// Token: 0x0600014F RID: 335 RVA: 0x00006844 File Offset: 0x00004A44
	protected void method_37(Pen pen_0, int int_5)
	{
		this.method_39(pen_0, 0, 0, base.Width, base.Height, int_5);
	}

	// Token: 0x06000150 RID: 336 RVA: 0x0000685C File Offset: 0x00004A5C
	protected void method_38(Pen pen_0, Rectangle rectangle_5, int int_5)
	{
		this.method_39(pen_0, rectangle_5.X, rectangle_5.Y, rectangle_5.Width, rectangle_5.Height, int_5);
	}

	// Token: 0x06000151 RID: 337 RVA: 0x00006884 File Offset: 0x00004A84
	protected void method_39(Pen pen_0, int int_5, int int_6, int int_7, int int_8, int int_9)
	{
		checked
		{
			this.method_42(pen_0, int_5 + int_9, int_6 + int_9, int_7 - int_9 * 2, int_8 - int_9 * 2);
		}
	}

	// Token: 0x06000152 RID: 338 RVA: 0x000068A4 File Offset: 0x00004AA4
	protected void method_40(Pen pen_0)
	{
		this.method_42(pen_0, 0, 0, base.Width, base.Height);
	}

	// Token: 0x06000153 RID: 339 RVA: 0x000068BC File Offset: 0x00004ABC
	protected void method_41(Pen pen_0, Rectangle rectangle_5)
	{
		this.method_42(pen_0, rectangle_5.X, rectangle_5.Y, rectangle_5.Width, rectangle_5.Height);
	}

	// Token: 0x06000154 RID: 340 RVA: 0x000068E4 File Offset: 0x00004AE4
	protected void method_42(Pen pen_0, int int_5, int int_6, int int_7, int int_8)
	{
		checked
		{
			this.graphics_0.DrawRectangle(pen_0, int_5, int_6, int_7 - 1, int_8 - 1);
		}
	}

	// Token: 0x06000155 RID: 341 RVA: 0x000068FC File Offset: 0x00004AFC
	protected void method_43(Brush brush_0, HorizontalAlignment horizontalAlignment_0, int int_5, int int_6)
	{
		this.method_44(brush_0, this.System.Windows.Forms.Control.Text, horizontalAlignment_0, int_5, int_6);
	}

	// Token: 0x06000156 RID: 342 RVA: 0x00006910 File Offset: 0x00004B10
	protected void method_44(Brush brush_0, string string_1, HorizontalAlignment horizontalAlignment_0, int int_5, int int_6)
	{
		checked
		{
			if (string_1.Length != 0)
			{
				this.size_2 = this.method_29(string_1);
				this.point_3 = new Point(base.Width / 2 - this.size_2.Width / 2, this.Int32_2 / 2 - this.size_2.Height / 2);
				switch (horizontalAlignment_0)
				{
				case HorizontalAlignment.Left:
					this.graphics_0.DrawString(string_1, this.System.Windows.Forms.Control.Font, brush_0, (float)int_5, (float)(this.point_3.Y + int_6));
					break;
				case HorizontalAlignment.Right:
					this.graphics_0.DrawString(string_1, this.System.Windows.Forms.Control.Font, brush_0, (float)(base.Width - this.size_2.Width - int_5), (float)(this.point_3.Y + int_6));
					break;
				case HorizontalAlignment.Center:
					this.graphics_0.DrawString(string_1, this.System.Windows.Forms.Control.Font, brush_0, (float)(this.point_3.X + int_5), (float)(this.point_3.Y + int_6));
					break;
				}
			}
		}
	}

	// Token: 0x06000157 RID: 343 RVA: 0x00006A1C File Offset: 0x00004C1C
	protected void method_45(Brush brush_0, Point point_5)
	{
		if (this.System.Windows.Forms.Control.Text.Length != 0)
		{
			this.graphics_0.DrawString(this.System.Windows.Forms.Control.Text, this.System.Windows.Forms.Control.Font, brush_0, point_5);
		}
	}

	// Token: 0x06000158 RID: 344 RVA: 0x00006A4C File Offset: 0x00004C4C
	protected void method_46(Brush brush_0, int int_5, int int_6)
	{
		if (this.System.Windows.Forms.Control.Text.Length != 0)
		{
			this.graphics_0.DrawString(this.System.Windows.Forms.Control.Text, this.System.Windows.Forms.Control.Font, brush_0, (float)int_5, (float)int_6);
		}
	}

	// Token: 0x06000159 RID: 345 RVA: 0x00006A7C File Offset: 0x00004C7C
	protected void method_47(HorizontalAlignment horizontalAlignment_0, int int_5, int int_6)
	{
		this.method_48(this.image_0, horizontalAlignment_0, int_5, int_6);
	}

	// Token: 0x0600015A RID: 346 RVA: 0x00006A90 File Offset: 0x00004C90
	protected void method_48(Image image_1, HorizontalAlignment horizontalAlignment_0, int int_5, int int_6)
	{
		checked
		{
			if (image_1 != null)
			{
				this.point_4 = new Point(base.Width / 2 - image_1.Width / 2, this.Int32_2 / 2 - image_1.Height / 2);
				switch (horizontalAlignment_0)
				{
				case HorizontalAlignment.Left:
					this.graphics_0.DrawImage(image_1, int_5, this.point_4.Y + int_6, image_1.Width, image_1.Height);
					break;
				case HorizontalAlignment.Right:
					this.graphics_0.DrawImage(image_1, base.Width - image_1.Width - int_5, this.point_4.Y + int_6, image_1.Width, image_1.Height);
					break;
				case HorizontalAlignment.Center:
					this.graphics_0.DrawImage(image_1, this.point_4.X + int_5, this.point_4.Y + int_6, image_1.Width, image_1.Height);
					break;
				}
			}
		}
	}

	// Token: 0x0600015B RID: 347 RVA: 0x00006B80 File Offset: 0x00004D80
	protected void method_49(Point point_5)
	{
		this.method_52(this.image_0, point_5.X, point_5.Y);
	}

	// Token: 0x0600015C RID: 348 RVA: 0x00006B9C File Offset: 0x00004D9C
	protected void method_50(int int_5, int int_6)
	{
		this.method_52(this.image_0, int_5, int_6);
	}

	// Token: 0x0600015D RID: 349 RVA: 0x00006BAC File Offset: 0x00004DAC
	protected void method_51(Image image_1, Point point_5)
	{
		this.method_52(image_1, point_5.X, point_5.Y);
	}

	// Token: 0x0600015E RID: 350 RVA: 0x00006BC4 File Offset: 0x00004DC4
	protected void method_52(Image image_1, int int_5, int int_6)
	{
		if (image_1 != null)
		{
			this.graphics_0.DrawImage(image_1, int_5, int_6, image_1.Width, image_1.Height);
		}
	}

	// Token: 0x0600015F RID: 351 RVA: 0x00006BE8 File Offset: 0x00004DE8
	protected void method_53(ColorBlend colorBlend_0, int int_5, int int_6, int int_7, int int_8)
	{
		this.rectangle_2 = new Rectangle(int_5, int_6, int_7, int_8);
		this.method_55(colorBlend_0, this.rectangle_2);
	}

	// Token: 0x06000160 RID: 352 RVA: 0x00006C08 File Offset: 0x00004E08
	protected void method_54(ColorBlend colorBlend_0, int int_5, int int_6, int int_7, int int_8, float float_0)
	{
		this.rectangle_2 = new Rectangle(int_5, int_6, int_7, int_8);
		this.method_56(colorBlend_0, this.rectangle_2, float_0);
	}

	// Token: 0x06000161 RID: 353 RVA: 0x00006C2C File Offset: 0x00004E2C
	protected void method_55(ColorBlend colorBlend_0, Rectangle rectangle_5)
	{
		this.linearGradientBrush_0 = new LinearGradientBrush(rectangle_5, Color.Empty, Color.Empty, 90f);
		this.linearGradientBrush_0.InterpolationColors = colorBlend_0;
		this.graphics_0.FillRectangle(this.linearGradientBrush_0, rectangle_5);
	}

	// Token: 0x06000162 RID: 354 RVA: 0x00006C68 File Offset: 0x00004E68
	protected void method_56(ColorBlend colorBlend_0, Rectangle rectangle_5, float float_0)
	{
		this.linearGradientBrush_0 = new LinearGradientBrush(rectangle_5, Color.Empty, Color.Empty, float_0);
		this.linearGradientBrush_0.InterpolationColors = colorBlend_0;
		this.graphics_0.FillRectangle(this.linearGradientBrush_0, rectangle_5);
	}

	// Token: 0x06000163 RID: 355 RVA: 0x00006CA0 File Offset: 0x00004EA0
	protected void method_57(Color color_1, Color color_2, int int_5, int int_6, int int_7, int int_8)
	{
		this.rectangle_2 = new Rectangle(int_5, int_6, int_7, int_8);
		this.method_59(color_1, color_2, this.rectangle_2);
	}

	// Token: 0x06000164 RID: 356 RVA: 0x00006CC4 File Offset: 0x00004EC4
	protected void method_58(Color color_1, Color color_2, int int_5, int int_6, int int_7, int int_8, float float_0)
	{
		this.rectangle_2 = new Rectangle(int_5, int_6, int_7, int_8);
		this.method_60(color_1, color_2, this.rectangle_2, float_0);
	}

	// Token: 0x06000165 RID: 357 RVA: 0x00006CE8 File Offset: 0x00004EE8
	protected void method_59(Color color_1, Color color_2, Rectangle rectangle_5)
	{
		this.linearGradientBrush_0 = new LinearGradientBrush(rectangle_5, color_1, color_2, 90f);
		this.graphics_0.FillRectangle(this.linearGradientBrush_0, rectangle_5);
	}

	// Token: 0x06000166 RID: 358 RVA: 0x00006D10 File Offset: 0x00004F10
	protected void method_60(Color color_1, Color color_2, Rectangle rectangle_5, float float_0)
	{
		this.linearGradientBrush_0 = new LinearGradientBrush(rectangle_5, color_1, color_2, float_0);
		this.graphics_0.FillRectangle(this.linearGradientBrush_0, rectangle_5);
	}

	// Token: 0x06000167 RID: 359 RVA: 0x00006D34 File Offset: 0x00004F34
	public void method_61(ColorBlend colorBlend_0, int int_5, int int_6, int int_7, int int_8)
	{
		this.rectangle_3 = new Rectangle(int_5, int_6, int_7, int_8);
		this.method_66(colorBlend_0, this.rectangle_3, int_7 / 2, int_8 / 2);
	}

	// Token: 0x06000168 RID: 360 RVA: 0x00006D5C File Offset: 0x00004F5C
	public void method_62(ColorBlend colorBlend_0, int int_5, int int_6, int int_7, int int_8, Point point_5)
	{
		this.rectangle_3 = new Rectangle(int_5, int_6, int_7, int_8);
		this.method_66(colorBlend_0, this.rectangle_3, point_5.X, point_5.Y);
	}

	// Token: 0x06000169 RID: 361 RVA: 0x00006D8C File Offset: 0x00004F8C
	public void method_63(ColorBlend colorBlend_0, int int_5, int int_6, int int_7, int int_8, int int_9, int int_10)
	{
		this.rectangle_3 = new Rectangle(int_5, int_6, int_7, int_8);
		this.method_66(colorBlend_0, this.rectangle_3, int_9, int_10);
	}

	// Token: 0x0600016A RID: 362 RVA: 0x00006DB0 File Offset: 0x00004FB0
	public void method_64(ColorBlend colorBlend_0, Rectangle rectangle_5)
	{
		this.method_66(colorBlend_0, rectangle_5, rectangle_5.Width / 2, rectangle_5.Height / 2);
	}

	// Token: 0x0600016B RID: 363 RVA: 0x00006DCC File Offset: 0x00004FCC
	public void method_65(ColorBlend colorBlend_0, Rectangle rectangle_5, Point point_5)
	{
		this.method_66(colorBlend_0, rectangle_5, point_5.X, point_5.Y);
	}

	// Token: 0x0600016C RID: 364 RVA: 0x00006DE4 File Offset: 0x00004FE4
	public void method_66(ColorBlend colorBlend_0, Rectangle rectangle_5, int int_5, int int_6)
	{
		this.graphicsPath_0.Reset();
		checked
		{
			this.graphicsPath_0.AddEllipse(rectangle_5.X, rectangle_5.Y, rectangle_5.Width - 1, rectangle_5.Height - 1);
			this.pathGradientBrush_0 = new PathGradientBrush(this.graphicsPath_0);
			this.pathGradientBrush_0.CenterPoint = new Point(rectangle_5.X + int_5, rectangle_5.Y + int_6);
			this.pathGradientBrush_0.InterpolationColors = colorBlend_0;
			if (this.graphics_0.SmoothingMode != SmoothingMode.AntiAlias)
			{
				this.graphics_0.FillEllipse(this.pathGradientBrush_0, rectangle_5);
			}
			else
			{
				this.graphics_0.FillEllipse(this.pathGradientBrush_0, rectangle_5.X + 1, rectangle_5.Y + 1, rectangle_5.Width - 3, rectangle_5.Height - 3);
			}
		}
	}

	// Token: 0x0600016D RID: 365 RVA: 0x00006EC8 File Offset: 0x000050C8
	protected void method_67(Color color_1, Color color_2, int int_5, int int_6, int int_7, int int_8)
	{
		this.rectangle_3 = new Rectangle(int_5, int_6, int_7, int_8);
		this.method_69(color_1, color_2, this.rectangle_2);
	}

	// Token: 0x0600016E RID: 366 RVA: 0x00006EEC File Offset: 0x000050EC
	protected void method_68(Color color_1, Color color_2, int int_5, int int_6, int int_7, int int_8, float float_0)
	{
		this.rectangle_3 = new Rectangle(int_5, int_6, int_7, int_8);
		this.method_70(color_1, color_2, this.rectangle_2, float_0);
	}

	// Token: 0x0600016F RID: 367 RVA: 0x00006F10 File Offset: 0x00005110
	protected void method_69(Color color_1, Color color_2, Rectangle rectangle_5)
	{
		this.linearGradientBrush_1 = new LinearGradientBrush(rectangle_5, color_1, color_2, 90f);
		this.graphics_0.FillRectangle(this.linearGradientBrush_0, rectangle_5);
	}

	// Token: 0x06000170 RID: 368 RVA: 0x00006F38 File Offset: 0x00005138
	protected void method_70(Color color_1, Color color_2, Rectangle rectangle_5, float float_0)
	{
		this.linearGradientBrush_1 = new LinearGradientBrush(rectangle_5, color_1, color_2, float_0);
		this.graphics_0.FillEllipse(this.linearGradientBrush_0, rectangle_5);
	}

	// Token: 0x06000171 RID: 369 RVA: 0x00006F5C File Offset: 0x0000515C
	public GraphicsPath method_71(int int_5, int int_6, int int_7, int int_8, int int_9)
	{
		this.rectangle_4 = new Rectangle(int_5, int_6, int_7, int_8);
		return this.method_72(this.rectangle_4, int_9);
	}

	// Token: 0x06000172 RID: 370 RVA: 0x00006F8C File Offset: 0x0000518C
	public GraphicsPath method_72(Rectangle rectangle_5, int int_5)
	{
		this.graphicsPath_1 = new GraphicsPath(FillMode.Winding);
		this.graphicsPath_1.AddArc(rectangle_5.X, rectangle_5.Y, int_5, int_5, 180f, 90f);
		checked
		{
			this.graphicsPath_1.AddArc(rectangle_5.Right - int_5, rectangle_5.Y, int_5, int_5, 270f, 90f);
			this.graphicsPath_1.AddArc(rectangle_5.Right - int_5, rectangle_5.Bottom - int_5, int_5, int_5, 0f, 90f);
			this.graphicsPath_1.AddArc(rectangle_5.X, rectangle_5.Bottom - int_5, int_5, int_5, 90f, 90f);
			this.graphicsPath_1.CloseFigure();
			return this.graphicsPath_1;
		}
	}

	// Token: 0x06000173 RID: 371 RVA: 0x00007054 File Offset: 0x00005254
	void method_73(DockStyle dockStyle_0)
	{
		base.Dock = dockStyle_0;
	}

	// Token: 0x06000174 RID: 372 RVA: 0x00007060 File Offset: 0x00005260
	DockStyle method_74()
	{
		return base.Dock;
	}

	// Token: 0x06000175 RID: 373 RVA: 0x00007068 File Offset: 0x00005268
	Color method_75()
	{
		return base.BackColor;
	}

	// Token: 0x06000176 RID: 374 RVA: 0x00007070 File Offset: 0x00005270
	void method_76(Color color_1)
	{
		base.BackColor = color_1;
	}

	// Token: 0x06000177 RID: 375 RVA: 0x0000707C File Offset: 0x0000527C
	Size method_77()
	{
		return base.MinimumSize;
	}

	// Token: 0x06000178 RID: 376 RVA: 0x00007084 File Offset: 0x00005284
	void method_78(Size size_3)
	{
		base.MinimumSize = size_3;
	}

	// Token: 0x06000179 RID: 377 RVA: 0x00007090 File Offset: 0x00005290
	Size method_79()
	{
		return base.MaximumSize;
	}

	// Token: 0x0600017A RID: 378 RVA: 0x00007098 File Offset: 0x00005298
	void method_80(Size size_3)
	{
		base.MaximumSize = size_3;
	}

	// Token: 0x0600017B RID: 379 RVA: 0x000070A4 File Offset: 0x000052A4
	string method_81()
	{
		return base.Text;
	}

	// Token: 0x0600017C RID: 380 RVA: 0x000070AC File Offset: 0x000052AC
	void method_82(string string_1)
	{
		base.Text = string_1;
	}

	// Token: 0x0600017D RID: 381 RVA: 0x000070B8 File Offset: 0x000052B8
	Font method_83()
	{
		return base.Font;
	}

	// Token: 0x0600017E RID: 382 RVA: 0x000070C0 File Offset: 0x000052C0
	void method_84(Font font_0)
	{
		base.Font = font_0;
	}

	// Token: 0x0400001A RID: 26
	protected Graphics graphics_0;

	// Token: 0x0400001B RID: 27
	protected Bitmap bitmap_0;

	// Token: 0x0400001C RID: 28
	private bool bool_0;

	// Token: 0x0400001D RID: 29
	private bool bool_1;

	// Token: 0x0400001E RID: 30
	private Rectangle rectangle_0;

	// Token: 0x0400001F RID: 31
	protected Enum0 enum0_0;

	// Token: 0x04000020 RID: 32
	private bool bool_2;

	// Token: 0x04000021 RID: 33
	private Point point_0;

	// Token: 0x04000022 RID: 34
	private bool bool_3;

	// Token: 0x04000023 RID: 35
	private bool bool_4;

	// Token: 0x04000024 RID: 36
	private bool bool_5;

	// Token: 0x04000025 RID: 37
	private bool bool_6;

	// Token: 0x04000026 RID: 38
	private int int_0;

	// Token: 0x04000027 RID: 39
	private int int_1;

	// Token: 0x04000028 RID: 40
	private Message[] message_0;

	// Token: 0x04000029 RID: 41
	private bool bool_7;

	// Token: 0x0400002A RID: 42
	private bool bool_8;

	// Token: 0x0400002B RID: 43
	private bool bool_9;

	// Token: 0x0400002C RID: 44
	private bool bool_10;

	// Token: 0x0400002D RID: 45
	private Color color_0;

	// Token: 0x0400002E RID: 46
	private FormBorderStyle formBorderStyle_0;

	// Token: 0x0400002F RID: 47
	private FormStartPosition formStartPosition_0;

	// Token: 0x04000030 RID: 48
	private bool bool_11;

	// Token: 0x04000031 RID: 49
	private Image image_0;

	// Token: 0x04000032 RID: 50
	private Dictionary<string, Color> dictionary_0;

	// Token: 0x04000033 RID: 51
	private string string_0;

	// Token: 0x04000034 RID: 52
	private bool bool_12;

	// Token: 0x04000035 RID: 53
	private Size size_0;

	// Token: 0x04000036 RID: 54
	private bool bool_13;

	// Token: 0x04000037 RID: 55
	private int int_2;

	// Token: 0x04000038 RID: 56
	private int int_3;

	// Token: 0x04000039 RID: 57
	private int int_4;

	// Token: 0x0400003A RID: 58
	private bool bool_14;

	// Token: 0x0400003B RID: 59
	private bool bool_15;

	// Token: 0x0400003C RID: 60
	private Rectangle rectangle_1;

	// Token: 0x0400003D RID: 61
	private Size size_1;

	// Token: 0x0400003E RID: 62
	private Point point_1;

	// Token: 0x0400003F RID: 63
	private Point point_2;

	// Token: 0x04000040 RID: 64
	private Bitmap bitmap_1;

	// Token: 0x04000041 RID: 65
	private Graphics graphics_1;

	// Token: 0x04000042 RID: 66
	private SolidBrush solidBrush_0;

	// Token: 0x04000043 RID: 67
	private SolidBrush solidBrush_1;

	// Token: 0x04000044 RID: 68
	private Point point_3;

	// Token: 0x04000045 RID: 69
	private Size size_2;

	// Token: 0x04000046 RID: 70
	private Point point_4;

	// Token: 0x04000047 RID: 71
	private LinearGradientBrush linearGradientBrush_0;

	// Token: 0x04000048 RID: 72
	private Rectangle rectangle_2;

	// Token: 0x04000049 RID: 73
	private GraphicsPath graphicsPath_0;

	// Token: 0x0400004A RID: 74
	private PathGradientBrush pathGradientBrush_0;

	// Token: 0x0400004B RID: 75
	private LinearGradientBrush linearGradientBrush_1;

	// Token: 0x0400004C RID: 76
	private Rectangle rectangle_3;

	// Token: 0x0400004D RID: 77
	private GraphicsPath graphicsPath_1;

	// Token: 0x0400004E RID: 78
	private Rectangle rectangle_4;
}

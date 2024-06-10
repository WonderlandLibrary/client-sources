using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Imaging;
using System.IO;
using System.Windows.Forms;
using Microsoft.VisualBasic.CompilerServices;

// Token: 0x0200000D RID: 13
internal abstract class Control2 : Control
{
	// Token: 0x0600017F RID: 383 RVA: 0x000070CC File Offset: 0x000052CC
	public Control2()
	{
		this.dictionary_0 = new Dictionary<string, Color>();
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.Opaque | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		this.size_0 = Size.Empty;
		this.System.Windows.Forms.Control.Font = new Font("Verdana", 8f);
		this.bitmap_1 = new Bitmap(1, 1);
		this.graphics_1 = Graphics.FromImage(this.bitmap_1);
		this.graphicsPath_0 = new GraphicsPath();
		this.method_11();
	}

	// Token: 0x06000180 RID: 384 RVA: 0x00007148 File Offset: 0x00005348
	protected sealed void OnHandleCreated(EventArgs e)
	{
		this.method_11();
		this.vmethod_0();
		if (this.int_0 != 0)
		{
			base.Width = this.int_0;
		}
		if (this.int_1 != 0)
		{
			base.Height = this.int_1;
		}
		this.Boolean_1 = this.bool_4;
		if (this.bool_4 && this.bool_2)
		{
			this.System.Windows.Forms.Control.BackColor = Color.Transparent;
		}
		base.OnHandleCreated(e);
	}

	// Token: 0x06000181 RID: 385 RVA: 0x000071C0 File Offset: 0x000053C0
	protected sealed void OnParentChanged(EventArgs e)
	{
		if (base.Parent != null)
		{
			this.vmethod_2();
			this.bool_0 = true;
			this.method_12();
		}
		base.OnParentChanged(e);
	}

	// Token: 0x06000182 RID: 386 RVA: 0x000071E8 File Offset: 0x000053E8
	private void method_0(bool bool_6)
	{
		this.vmethod_3();
		if (bool_6)
		{
			base.Invalidate();
		}
	}

	// Token: 0x06000183 RID: 387 RVA: 0x000071FC File Offset: 0x000053FC
	protected sealed void OnPaint(PaintEventArgs e)
	{
		if (base.Width != 0 && base.Height != 0)
		{
			if (!this.bool_4)
			{
				this.graphics_0 = e.Graphics;
				this.vmethod_1();
			}
			else
			{
				this.vmethod_1();
				e.Graphics.DrawImage(this.bitmap_0, 0, 0);
			}
		}
	}

	// Token: 0x06000184 RID: 388 RVA: 0x00007254 File Offset: 0x00005454
	protected virtual void OnHandleDestroyed(EventArgs e)
	{
		Class7.smethod_3(new Class7.Delegate0(this.method_0));
		base.OnHandleDestroyed(e);
	}

	// Token: 0x06000185 RID: 389 RVA: 0x00007270 File Offset: 0x00005470
	protected sealed void OnSizeChanged(EventArgs e)
	{
		if (this.bool_4)
		{
			this.method_10();
		}
		base.Invalidate();
		base.OnSizeChanged(e);
	}

	// Token: 0x06000186 RID: 390 RVA: 0x00007290 File Offset: 0x00005490
	protected virtual void SetBoundsCore(int x, int y, int width, int height, BoundsSpecified specified)
	{
		if (this.int_0 != 0)
		{
			width = this.int_0;
		}
		if (this.int_1 != 0)
		{
			height = this.int_1;
		}
		base.SetBoundsCore(x, y, width, height, specified);
	}

	// Token: 0x06000187 RID: 391 RVA: 0x000072C8 File Offset: 0x000054C8
	protected virtual void OnMouseEnter(EventArgs e)
	{
		this.bool_1 = true;
		this.method_1(Enum0.Over);
		base.OnMouseEnter(e);
	}

	// Token: 0x06000188 RID: 392 RVA: 0x000072E0 File Offset: 0x000054E0
	protected virtual void OnMouseUp(MouseEventArgs e)
	{
		if (this.bool_1)
		{
			this.method_1(Enum0.Over);
		}
		base.OnMouseUp(e);
	}

	// Token: 0x06000189 RID: 393 RVA: 0x000072F8 File Offset: 0x000054F8
	protected virtual void OnMouseDown(MouseEventArgs e)
	{
		if (e.Button == MouseButtons.Left)
		{
			this.method_1(Enum0.Down);
		}
		base.OnMouseDown(e);
	}

	// Token: 0x0600018A RID: 394 RVA: 0x00007318 File Offset: 0x00005518
	protected virtual void OnMouseLeave(EventArgs e)
	{
		this.bool_1 = false;
		this.method_1(Enum0.None);
		base.OnMouseLeave(e);
	}

	// Token: 0x0600018B RID: 395 RVA: 0x00007330 File Offset: 0x00005530
	protected virtual void OnEnabledChanged(EventArgs e)
	{
		if (!base.Enabled)
		{
			this.method_1(Enum0.Block);
		}
		else
		{
			this.method_1(Enum0.None);
		}
		base.OnEnabledChanged(e);
	}

	// Token: 0x0600018C RID: 396 RVA: 0x00007354 File Offset: 0x00005554
	private void method_1(Enum0 enum0_1)
	{
		this.enum0_0 = enum0_1;
		base.Invalidate();
	}

	// Token: 0x170000BA RID: 186
	// (get) Token: 0x0600018D RID: 397 RVA: 0x00007364 File Offset: 0x00005564
	// (set) Token: 0x0600018E RID: 398 RVA: 0x00007378 File Offset: 0x00005578
	[EditorBrowsable(EditorBrowsableState.Never)]
	[DesignerSerializationVisibility(DesignerSerializationVisibility.Hidden)]
	[Browsable(false)]
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

	// Token: 0x170000BB RID: 187
	// (get) Token: 0x0600018F RID: 399 RVA: 0x0000737C File Offset: 0x0000557C
	// (set) Token: 0x06000190 RID: 400 RVA: 0x0000738C File Offset: 0x0000558C
	[EditorBrowsable(EditorBrowsableState.Never)]
	[DesignerSerializationVisibility(DesignerSerializationVisibility.Hidden)]
	[Browsable(false)]
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

	// Token: 0x170000BC RID: 188
	// (get) Token: 0x06000191 RID: 401 RVA: 0x00007390 File Offset: 0x00005590
	// (set) Token: 0x06000192 RID: 402 RVA: 0x000073A0 File Offset: 0x000055A0
	[Browsable(false)]
	[DesignerSerializationVisibility(DesignerSerializationVisibility.Hidden)]
	[EditorBrowsable(EditorBrowsableState.Never)]
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

	// Token: 0x170000BD RID: 189
	// (get) Token: 0x06000193 RID: 403 RVA: 0x000073A4 File Offset: 0x000055A4
	// (set) Token: 0x06000194 RID: 404 RVA: 0x000073BC File Offset: 0x000055BC
	public virtual string Text
	{
		get
		{
			return this.method_68();
		}
		set
		{
			this.method_69(value);
			base.Invalidate();
		}
	}

	// Token: 0x170000BE RID: 190
	// (get) Token: 0x06000195 RID: 405 RVA: 0x000073CC File Offset: 0x000055CC
	// (set) Token: 0x06000196 RID: 406 RVA: 0x000073E4 File Offset: 0x000055E4
	public virtual Font Font
	{
		get
		{
			return this.method_70();
		}
		set
		{
			this.method_71(value);
			base.Invalidate();
		}
	}

	// Token: 0x170000BF RID: 191
	// (get) Token: 0x06000197 RID: 407 RVA: 0x000073F4 File Offset: 0x000055F4
	// (set) Token: 0x06000198 RID: 408 RVA: 0x0000740C File Offset: 0x0000560C
	[Category("Misc")]
	public virtual Color BackColor
	{
		get
		{
			return this.method_72();
		}
		set
		{
			if (!base.IsHandleCreated && value == Color.Transparent)
			{
				this.bool_2 = true;
			}
			else
			{
				this.method_73(value);
				if (base.Parent != null)
				{
					this.vmethod_0();
				}
			}
		}
	}

	// Token: 0x170000C0 RID: 192
	// (get) Token: 0x06000199 RID: 409 RVA: 0x00007448 File Offset: 0x00005648
	// (set) Token: 0x0600019A RID: 410 RVA: 0x00007450 File Offset: 0x00005650
	public bool Boolean_0
	{
		get
		{
			return this.bool_3;
		}
		set
		{
			this.bool_3 = value;
			base.Invalidate();
		}
	}

	// Token: 0x170000C1 RID: 193
	// (get) Token: 0x0600019B RID: 411 RVA: 0x00007460 File Offset: 0x00005660
	// (set) Token: 0x0600019C RID: 412 RVA: 0x00007478 File Offset: 0x00005678
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

	// Token: 0x170000C2 RID: 194
	// (get) Token: 0x0600019D RID: 413 RVA: 0x000074A8 File Offset: 0x000056A8
	// (set) Token: 0x0600019E RID: 414 RVA: 0x000074B0 File Offset: 0x000056B0
	public bool Boolean_1
	{
		get
		{
			return this.bool_4;
		}
		set
		{
			this.bool_4 = value;
			if (base.IsHandleCreated)
			{
				if (!value && this.System.Windows.Forms.Control.BackColor.A != 255)
				{
					throw new Exception("Unable to change value to false while a transparent BackColor is in use.");
				}
				base.SetStyle(ControlStyles.Opaque, !value);
				base.SetStyle(ControlStyles.SupportsTransparentBackColor, value);
				if (value)
				{
					this.method_10();
				}
				else
				{
					this.bitmap_0 = null;
				}
				base.Invalidate();
			}
		}
	}

	// Token: 0x170000C3 RID: 195
	// (get) Token: 0x0600019F RID: 415 RVA: 0x0000752C File Offset: 0x0000572C
	// (set) Token: 0x060001A0 RID: 416 RVA: 0x00007588 File Offset: 0x00005788
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
				this.method_11();
				this.vmethod_0();
				base.Invalidate();
			}
		}
	}

	// Token: 0x170000C4 RID: 196
	// (get) Token: 0x060001A1 RID: 417 RVA: 0x000075F0 File Offset: 0x000057F0
	// (set) Token: 0x060001A2 RID: 418 RVA: 0x00007608 File Offset: 0x00005808
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
					this.vmethod_0();
					base.Invalidate();
				}
			}
		}
	}

	// Token: 0x170000C5 RID: 197
	// (get) Token: 0x060001A3 RID: 419 RVA: 0x0000769C File Offset: 0x0000589C
	protected Size Size_0
	{
		get
		{
			return this.size_0;
		}
	}

	// Token: 0x170000C6 RID: 198
	// (get) Token: 0x060001A4 RID: 420 RVA: 0x000076B4 File Offset: 0x000058B4
	// (set) Token: 0x060001A5 RID: 421 RVA: 0x000076CC File Offset: 0x000058CC
	protected int Int32_0
	{
		get
		{
			return this.int_0;
		}
		set
		{
			this.int_0 = value;
			if (this.Int32_0 != 0 && base.IsHandleCreated)
			{
				base.Width = this.Int32_0;
			}
		}
	}

	// Token: 0x170000C7 RID: 199
	// (get) Token: 0x060001A6 RID: 422 RVA: 0x000076F4 File Offset: 0x000058F4
	// (set) Token: 0x060001A7 RID: 423 RVA: 0x0000770C File Offset: 0x0000590C
	protected int Int32_1
	{
		get
		{
			return this.int_1;
		}
		set
		{
			this.int_1 = value;
			if (this.Int32_1 != 0 && base.IsHandleCreated)
			{
				base.Height = this.Int32_1;
			}
		}
	}

	// Token: 0x170000C8 RID: 200
	// (get) Token: 0x060001A8 RID: 424 RVA: 0x00007734 File Offset: 0x00005934
	// (set) Token: 0x060001A9 RID: 425 RVA: 0x0000773C File Offset: 0x0000593C
	protected bool Boolean_2
	{
		get
		{
			return this.bool_5;
		}
		set
		{
			this.bool_5 = value;
			this.method_12();
		}
	}

	// Token: 0x060001AA RID: 426 RVA: 0x0000774C File Offset: 0x0000594C
	protected Pen method_2(string string_1)
	{
		return new Pen(this.dictionary_0[string_1]);
	}

	// Token: 0x060001AB RID: 427 RVA: 0x0000776C File Offset: 0x0000596C
	protected Pen method_3(string string_1, float float_0)
	{
		return new Pen(this.dictionary_0[string_1], float_0);
	}

	// Token: 0x060001AC RID: 428 RVA: 0x00007790 File Offset: 0x00005990
	protected SolidBrush method_4(string string_1)
	{
		return new SolidBrush(this.dictionary_0[string_1]);
	}

	// Token: 0x060001AD RID: 429 RVA: 0x000077B0 File Offset: 0x000059B0
	protected Color method_5(string string_1)
	{
		return this.dictionary_0[string_1];
	}

	// Token: 0x060001AE RID: 430 RVA: 0x000077CC File Offset: 0x000059CC
	protected void method_6(string string_1, Color color_0)
	{
		if (this.dictionary_0.ContainsKey(string_1))
		{
			this.dictionary_0[string_1] = color_0;
		}
		else
		{
			this.dictionary_0.Add(string_1, color_0);
		}
	}

	// Token: 0x060001AF RID: 431 RVA: 0x000077F8 File Offset: 0x000059F8
	protected void method_7(string string_1, byte byte_0, byte byte_1, byte byte_2)
	{
		this.method_6(string_1, Color.FromArgb((int)byte_0, (int)byte_1, (int)byte_2));
	}

	// Token: 0x060001B0 RID: 432 RVA: 0x0000780C File Offset: 0x00005A0C
	protected void method_8(string string_1, byte byte_0, byte byte_1, byte byte_2, byte byte_3)
	{
		this.method_6(string_1, Color.FromArgb((int)byte_0, (int)byte_1, (int)byte_2, (int)byte_3));
	}

	// Token: 0x060001B1 RID: 433 RVA: 0x00007820 File Offset: 0x00005A20
	protected void method_9(string string_1, byte byte_0, Color color_0)
	{
		this.method_6(string_1, Color.FromArgb((int)byte_0, color_0));
	}

	// Token: 0x060001B2 RID: 434 RVA: 0x00007830 File Offset: 0x00005A30
	private void method_10()
	{
		if (base.Width != 0 && base.Height != 0)
		{
			this.bitmap_0 = new Bitmap(base.Width, base.Height, PixelFormat.Format32bppPArgb);
			this.graphics_0 = Graphics.FromImage(this.bitmap_0);
		}
	}

	// Token: 0x060001B3 RID: 435 RVA: 0x00007880 File Offset: 0x00005A80
	private void method_11()
	{
		MemoryStream memoryStream = new MemoryStream(checked(this.dictionary_0.Count * 4));
		foreach (Struct0 @struct in this.Struct0_0)
		{
			memoryStream.Write(BitConverter.GetBytes(@struct.Color_0.ToArgb()), 0, 4);
		}
		memoryStream.Close();
		this.string_0 = Convert.ToBase64String(memoryStream.ToArray());
	}

	// Token: 0x060001B4 RID: 436 RVA: 0x000078F4 File Offset: 0x00005AF4
	private void method_12()
	{
		if (!base.DesignMode && this.bool_0)
		{
			if (this.bool_5)
			{
				Class7.smethod_2(new Class7.Delegate0(this.method_0));
			}
			else
			{
				Class7.smethod_3(new Class7.Delegate0(this.method_0));
			}
		}
	}

	// Token: 0x060001B5 RID: 437
	protected abstract void vmethod_0();

	// Token: 0x060001B6 RID: 438
	protected abstract void vmethod_1();

	// Token: 0x060001B7 RID: 439 RVA: 0x00007944 File Offset: 0x00005B44
	protected virtual void vmethod_2()
	{
	}

	// Token: 0x060001B8 RID: 440 RVA: 0x00007948 File Offset: 0x00005B48
	protected virtual void vmethod_3()
	{
	}

	// Token: 0x060001B9 RID: 441 RVA: 0x0000794C File Offset: 0x00005B4C
	protected Rectangle method_13(Rectangle rectangle_4, int int_2)
	{
		this.rectangle_0 = checked(new Rectangle(rectangle_4.X + int_2, rectangle_4.Y + int_2, rectangle_4.Width - int_2 * 2, rectangle_4.Height - int_2 * 2));
		return this.rectangle_0;
	}

	// Token: 0x060001BA RID: 442 RVA: 0x00007994 File Offset: 0x00005B94
	protected Size method_14(Size size_3, int int_2)
	{
		this.size_1 = checked(new Size(size_3.Width + int_2, size_3.Height + int_2));
		return this.size_1;
	}

	// Token: 0x060001BB RID: 443 RVA: 0x000079C8 File Offset: 0x00005BC8
	protected Point method_15(Point point_4, int int_2)
	{
		this.point_0 = checked(new Point(point_4.X + int_2, point_4.Y + int_2));
		return this.point_0;
	}

	// Token: 0x060001BC RID: 444 RVA: 0x000079FC File Offset: 0x00005BFC
	protected Point method_16(Rectangle rectangle_4, Rectangle rectangle_5)
	{
		this.point_1 = checked(new Point(rectangle_4.Width / 2 - rectangle_5.Width / 2 + rectangle_4.X + rectangle_5.X, rectangle_4.Height / 2 - rectangle_5.Height / 2 + rectangle_4.Y + rectangle_5.Y));
		return this.point_1;
	}

	// Token: 0x060001BD RID: 445 RVA: 0x00007A64 File Offset: 0x00005C64
	protected Point method_17(Rectangle rectangle_4, Size size_3)
	{
		this.point_1 = checked(new Point(rectangle_4.Width / 2 - size_3.Width / 2 + rectangle_4.X, rectangle_4.Height / 2 - size_3.Height / 2 + rectangle_4.Y));
		return this.point_1;
	}

	// Token: 0x060001BE RID: 446 RVA: 0x00007ABC File Offset: 0x00005CBC
	protected Point method_18(Rectangle rectangle_4)
	{
		return this.method_22(base.Width, base.Height, rectangle_4.Width, rectangle_4.Height);
	}

	// Token: 0x060001BF RID: 447 RVA: 0x00007AEC File Offset: 0x00005CEC
	protected Point method_19(Size size_3)
	{
		return this.method_22(base.Width, base.Height, size_3.Width, size_3.Height);
	}

	// Token: 0x060001C0 RID: 448 RVA: 0x00007B1C File Offset: 0x00005D1C
	protected Point method_20(int int_2, int int_3)
	{
		return this.method_22(base.Width, base.Height, int_2, int_3);
	}

	// Token: 0x060001C1 RID: 449 RVA: 0x00007B40 File Offset: 0x00005D40
	protected Point method_21(Size size_3, Size size_4)
	{
		return this.method_22(size_3.Width, size_3.Height, size_4.Width, size_4.Height);
	}

	// Token: 0x060001C2 RID: 450 RVA: 0x00007B74 File Offset: 0x00005D74
	protected Point method_22(int int_2, int int_3, int int_4, int int_5)
	{
		this.point_1 = checked(new Point(int_2 / 2 - int_4 / 2, int_3 / 2 - int_5 / 2));
		return this.point_1;
	}

	// Token: 0x060001C3 RID: 451 RVA: 0x00007BA4 File Offset: 0x00005DA4
	protected Size method_23()
	{
		return this.graphics_1.MeasureString(this.System.Windows.Forms.Control.Text, this.System.Windows.Forms.Control.Font, base.Width).ToSize();
	}

	// Token: 0x060001C4 RID: 452 RVA: 0x00007BD8 File Offset: 0x00005DD8
	protected Size method_24(string string_1)
	{
		return this.graphics_1.MeasureString(string_1, this.System.Windows.Forms.Control.Font, base.Width).ToSize();
	}

	// Token: 0x060001C5 RID: 453 RVA: 0x00007C08 File Offset: 0x00005E08
	protected void method_25(Color color_0, int int_2, int int_3)
	{
		if (!this.bool_4)
		{
			this.solidBrush_0 = new SolidBrush(color_0);
			this.graphics_0.FillRectangle(this.solidBrush_0, int_2, int_3, 1, 1);
		}
		else
		{
			this.bitmap_0.SetPixel(int_2, int_3, color_0);
		}
	}

	// Token: 0x060001C6 RID: 454 RVA: 0x00007C44 File Offset: 0x00005E44
	protected void method_26(Color color_0, int int_2)
	{
		this.method_28(color_0, 0, 0, base.Width, base.Height, int_2);
	}

	// Token: 0x060001C7 RID: 455 RVA: 0x00007C5C File Offset: 0x00005E5C
	protected void method_27(Color color_0, Rectangle rectangle_4, int int_2)
	{
		this.method_28(color_0, rectangle_4.X, rectangle_4.Y, rectangle_4.Width, rectangle_4.Height, int_2);
	}

	// Token: 0x060001C8 RID: 456 RVA: 0x00007C84 File Offset: 0x00005E84
	protected void method_28(Color color_0, int int_2, int int_3, int int_4, int int_5, int int_6)
	{
		checked
		{
			this.method_31(color_0, int_2 + int_6, int_3 + int_6, int_4 - int_6 * 2, int_5 - int_6 * 2);
		}
	}

	// Token: 0x060001C9 RID: 457 RVA: 0x00007CA4 File Offset: 0x00005EA4
	protected void method_29(Color color_0)
	{
		this.method_31(color_0, 0, 0, base.Width, base.Height);
	}

	// Token: 0x060001CA RID: 458 RVA: 0x00007CBC File Offset: 0x00005EBC
	protected void method_30(Color color_0, Rectangle rectangle_4)
	{
		this.method_31(color_0, rectangle_4.X, rectangle_4.Y, rectangle_4.Width, rectangle_4.Height);
	}

	// Token: 0x060001CB RID: 459 RVA: 0x00007CE4 File Offset: 0x00005EE4
	protected void method_31(Color color_0, int int_2, int int_3, int int_4, int int_5)
	{
		checked
		{
			if (!this.bool_3)
			{
				if (!this.bool_4)
				{
					this.solidBrush_1 = new SolidBrush(color_0);
					this.graphics_0.FillRectangle(this.solidBrush_1, int_2, int_3, 1, 1);
					this.graphics_0.FillRectangle(this.solidBrush_1, int_2 + (int_4 - 1), int_3, 1, 1);
					this.graphics_0.FillRectangle(this.solidBrush_1, int_2, int_3 + (int_5 - 1), 1, 1);
					this.graphics_0.FillRectangle(this.solidBrush_1, int_2 + (int_4 - 1), int_3 + (int_5 - 1), 1, 1);
				}
				else
				{
					this.bitmap_0.SetPixel(int_2, int_3, color_0);
					this.bitmap_0.SetPixel(int_2 + (int_4 - 1), int_3, color_0);
					this.bitmap_0.SetPixel(int_2, int_3 + (int_5 - 1), color_0);
					this.bitmap_0.SetPixel(int_2 + (int_4 - 1), int_3 + (int_5 - 1), color_0);
				}
			}
		}
	}

	// Token: 0x060001CC RID: 460 RVA: 0x00007DC8 File Offset: 0x00005FC8
	protected void method_32(Pen pen_0, int int_2)
	{
		this.method_34(pen_0, 0, 0, base.Width, base.Height, int_2);
	}

	// Token: 0x060001CD RID: 461 RVA: 0x00007DE0 File Offset: 0x00005FE0
	protected void method_33(Pen pen_0, Rectangle rectangle_4, int int_2)
	{
		this.method_34(pen_0, rectangle_4.X, rectangle_4.Y, rectangle_4.Width, rectangle_4.Height, int_2);
	}

	// Token: 0x060001CE RID: 462 RVA: 0x00007E08 File Offset: 0x00006008
	protected void method_34(Pen pen_0, int int_2, int int_3, int int_4, int int_5, int int_6)
	{
		checked
		{
			this.method_37(pen_0, int_2 + int_6, int_3 + int_6, int_4 - int_6 * 2, int_5 - int_6 * 2);
		}
	}

	// Token: 0x060001CF RID: 463 RVA: 0x00007E28 File Offset: 0x00006028
	protected void method_35(Pen pen_0)
	{
		this.method_37(pen_0, 0, 0, base.Width, base.Height);
	}

	// Token: 0x060001D0 RID: 464 RVA: 0x00007E40 File Offset: 0x00006040
	protected void method_36(Pen pen_0, Rectangle rectangle_4)
	{
		this.method_37(pen_0, rectangle_4.X, rectangle_4.Y, rectangle_4.Width, rectangle_4.Height);
	}

	// Token: 0x060001D1 RID: 465 RVA: 0x00007E68 File Offset: 0x00006068
	protected void method_37(Pen pen_0, int int_2, int int_3, int int_4, int int_5)
	{
		checked
		{
			this.graphics_0.DrawRectangle(pen_0, int_2, int_3, int_4 - 1, int_5 - 1);
		}
	}

	// Token: 0x060001D2 RID: 466 RVA: 0x00007E80 File Offset: 0x00006080
	protected void method_38(Brush brush_0, HorizontalAlignment horizontalAlignment_0, int int_2, int int_3)
	{
		this.method_39(brush_0, this.System.Windows.Forms.Control.Text, horizontalAlignment_0, int_2, int_3);
	}

	// Token: 0x060001D3 RID: 467 RVA: 0x00007E94 File Offset: 0x00006094
	protected void method_39(Brush brush_0, string string_1, HorizontalAlignment horizontalAlignment_0, int int_2, int int_3)
	{
		checked
		{
			if (string_1.Length != 0)
			{
				this.size_2 = this.method_24(string_1);
				this.point_2 = this.method_19(this.size_2);
				switch (horizontalAlignment_0)
				{
				case HorizontalAlignment.Left:
					this.graphics_0.DrawString(string_1, this.System.Windows.Forms.Control.Font, brush_0, (float)int_2, (float)(this.point_2.Y + int_3));
					break;
				case HorizontalAlignment.Right:
					this.graphics_0.DrawString(string_1, this.System.Windows.Forms.Control.Font, brush_0, (float)(base.Width - this.size_2.Width - int_2), (float)(this.point_2.Y + int_3));
					break;
				case HorizontalAlignment.Center:
					this.graphics_0.DrawString(string_1, this.System.Windows.Forms.Control.Font, brush_0, (float)(this.point_2.X + int_2), (float)(this.point_2.Y + int_3));
					break;
				}
			}
		}
	}

	// Token: 0x060001D4 RID: 468 RVA: 0x00007F7C File Offset: 0x0000617C
	protected void method_40(Brush brush_0, Point point_4)
	{
		if (this.System.Windows.Forms.Control.Text.Length != 0)
		{
			this.graphics_0.DrawString(this.System.Windows.Forms.Control.Text, this.System.Windows.Forms.Control.Font, brush_0, point_4);
		}
	}

	// Token: 0x060001D5 RID: 469 RVA: 0x00007FAC File Offset: 0x000061AC
	protected void method_41(Brush brush_0, int int_2, int int_3)
	{
		if (this.System.Windows.Forms.Control.Text.Length != 0)
		{
			this.graphics_0.DrawString(this.System.Windows.Forms.Control.Text, this.System.Windows.Forms.Control.Font, brush_0, (float)int_2, (float)int_3);
		}
	}

	// Token: 0x060001D6 RID: 470 RVA: 0x00007FDC File Offset: 0x000061DC
	protected void method_42(HorizontalAlignment horizontalAlignment_0, int int_2, int int_3)
	{
		this.method_43(this.image_0, horizontalAlignment_0, int_2, int_3);
	}

	// Token: 0x060001D7 RID: 471 RVA: 0x00007FF0 File Offset: 0x000061F0
	protected void method_43(Image image_1, HorizontalAlignment horizontalAlignment_0, int int_2, int int_3)
	{
		checked
		{
			if (image_1 != null)
			{
				this.point_3 = this.method_19(image_1.Size);
				switch (horizontalAlignment_0)
				{
				case HorizontalAlignment.Left:
					this.graphics_0.DrawImage(image_1, int_2, this.point_3.Y + int_3, image_1.Width, image_1.Height);
					break;
				case HorizontalAlignment.Right:
					this.graphics_0.DrawImage(image_1, base.Width - image_1.Width - int_2, this.point_3.Y + int_3, image_1.Width, image_1.Height);
					break;
				case HorizontalAlignment.Center:
					this.graphics_0.DrawImage(image_1, this.point_3.X + int_2, this.point_3.Y + int_3, image_1.Width, image_1.Height);
					break;
				}
			}
		}
	}

	// Token: 0x060001D8 RID: 472 RVA: 0x000080C4 File Offset: 0x000062C4
	protected void method_44(Point point_4)
	{
		this.method_47(this.image_0, point_4.X, point_4.Y);
	}

	// Token: 0x060001D9 RID: 473 RVA: 0x000080E0 File Offset: 0x000062E0
	protected void method_45(int int_2, int int_3)
	{
		this.method_47(this.image_0, int_2, int_3);
	}

	// Token: 0x060001DA RID: 474 RVA: 0x000080F0 File Offset: 0x000062F0
	protected void method_46(Image image_1, Point point_4)
	{
		this.method_47(image_1, point_4.X, point_4.Y);
	}

	// Token: 0x060001DB RID: 475 RVA: 0x00008108 File Offset: 0x00006308
	protected void method_47(Image image_1, int int_2, int int_3)
	{
		if (image_1 != null)
		{
			this.graphics_0.DrawImage(image_1, int_2, int_3, image_1.Width, image_1.Height);
		}
	}

	// Token: 0x060001DC RID: 476 RVA: 0x0000812C File Offset: 0x0000632C
	protected void method_48(ColorBlend colorBlend_0, int int_2, int int_3, int int_4, int int_5)
	{
		this.rectangle_1 = new Rectangle(int_2, int_3, int_4, int_5);
		this.method_50(colorBlend_0, this.rectangle_1);
	}

	// Token: 0x060001DD RID: 477 RVA: 0x0000814C File Offset: 0x0000634C
	protected void method_49(ColorBlend colorBlend_0, int int_2, int int_3, int int_4, int int_5, float float_0)
	{
		this.rectangle_1 = new Rectangle(int_2, int_3, int_4, int_5);
		this.method_51(colorBlend_0, this.rectangle_1, float_0);
	}

	// Token: 0x060001DE RID: 478 RVA: 0x00008170 File Offset: 0x00006370
	protected void method_50(ColorBlend colorBlend_0, Rectangle rectangle_4)
	{
		this.linearGradientBrush_0 = new LinearGradientBrush(rectangle_4, Color.Empty, Color.Empty, 90f);
		this.linearGradientBrush_0.InterpolationColors = colorBlend_0;
		this.graphics_0.FillRectangle(this.linearGradientBrush_0, rectangle_4);
	}

	// Token: 0x060001DF RID: 479 RVA: 0x000081AC File Offset: 0x000063AC
	protected void method_51(ColorBlend colorBlend_0, Rectangle rectangle_4, float float_0)
	{
		this.linearGradientBrush_0 = new LinearGradientBrush(rectangle_4, Color.Empty, Color.Empty, float_0);
		this.linearGradientBrush_0.InterpolationColors = colorBlend_0;
		this.graphics_0.FillRectangle(this.linearGradientBrush_0, rectangle_4);
	}

	// Token: 0x060001E0 RID: 480 RVA: 0x000081E4 File Offset: 0x000063E4
	protected void method_52(Color color_0, Color color_1, int int_2, int int_3, int int_4, int int_5)
	{
		this.rectangle_1 = new Rectangle(int_2, int_3, int_4, int_5);
		this.method_54(color_0, color_1, this.rectangle_1);
	}

	// Token: 0x060001E1 RID: 481 RVA: 0x00008208 File Offset: 0x00006408
	protected void method_53(Color color_0, Color color_1, int int_2, int int_3, int int_4, int int_5, float float_0)
	{
		this.rectangle_1 = new Rectangle(int_2, int_3, int_4, int_5);
		this.method_55(color_0, color_1, this.rectangle_1, float_0);
	}

	// Token: 0x060001E2 RID: 482 RVA: 0x0000822C File Offset: 0x0000642C
	protected void method_54(Color color_0, Color color_1, Rectangle rectangle_4)
	{
		this.linearGradientBrush_0 = new LinearGradientBrush(rectangle_4, color_0, color_1, 90f);
		this.graphics_0.FillRectangle(this.linearGradientBrush_0, rectangle_4);
	}

	// Token: 0x060001E3 RID: 483 RVA: 0x00008254 File Offset: 0x00006454
	protected void method_55(Color color_0, Color color_1, Rectangle rectangle_4, float float_0)
	{
		this.linearGradientBrush_0 = new LinearGradientBrush(rectangle_4, color_0, color_1, float_0);
		this.graphics_0.FillRectangle(this.linearGradientBrush_0, rectangle_4);
	}

	// Token: 0x060001E4 RID: 484 RVA: 0x00008278 File Offset: 0x00006478
	public void method_56(ColorBlend colorBlend_0, int int_2, int int_3, int int_4, int int_5)
	{
		this.rectangle_2 = new Rectangle(int_2, int_3, int_4, int_5);
		this.method_61(colorBlend_0, this.rectangle_2, int_4 / 2, int_5 / 2);
	}

	// Token: 0x060001E5 RID: 485 RVA: 0x000082A0 File Offset: 0x000064A0
	public void method_57(ColorBlend colorBlend_0, int int_2, int int_3, int int_4, int int_5, Point point_4)
	{
		this.rectangle_2 = new Rectangle(int_2, int_3, int_4, int_5);
		this.method_61(colorBlend_0, this.rectangle_2, point_4.X, point_4.Y);
	}

	// Token: 0x060001E6 RID: 486 RVA: 0x000082D0 File Offset: 0x000064D0
	public void method_58(ColorBlend colorBlend_0, int int_2, int int_3, int int_4, int int_5, int int_6, int int_7)
	{
		this.rectangle_2 = new Rectangle(int_2, int_3, int_4, int_5);
		this.method_61(colorBlend_0, this.rectangle_2, int_6, int_7);
	}

	// Token: 0x060001E7 RID: 487 RVA: 0x000082F4 File Offset: 0x000064F4
	public void method_59(ColorBlend colorBlend_0, Rectangle rectangle_4)
	{
		this.method_61(colorBlend_0, rectangle_4, rectangle_4.Width / 2, rectangle_4.Height / 2);
	}

	// Token: 0x060001E8 RID: 488 RVA: 0x00008310 File Offset: 0x00006510
	public void method_60(ColorBlend colorBlend_0, Rectangle rectangle_4, Point point_4)
	{
		this.method_61(colorBlend_0, rectangle_4, point_4.X, point_4.Y);
	}

	// Token: 0x060001E9 RID: 489 RVA: 0x00008328 File Offset: 0x00006528
	public void method_61(ColorBlend colorBlend_0, Rectangle rectangle_4, int int_2, int int_3)
	{
		this.graphicsPath_0.Reset();
		checked
		{
			this.graphicsPath_0.AddEllipse(rectangle_4.X, rectangle_4.Y, rectangle_4.Width - 1, rectangle_4.Height - 1);
			this.pathGradientBrush_0 = new PathGradientBrush(this.graphicsPath_0);
			this.pathGradientBrush_0.CenterPoint = new Point(rectangle_4.X + int_2, rectangle_4.Y + int_3);
			this.pathGradientBrush_0.InterpolationColors = colorBlend_0;
			if (this.graphics_0.SmoothingMode == SmoothingMode.AntiAlias)
			{
				this.graphics_0.FillEllipse(this.pathGradientBrush_0, rectangle_4.X + 1, rectangle_4.Y + 1, rectangle_4.Width - 3, rectangle_4.Height - 3);
			}
			else
			{
				this.graphics_0.FillEllipse(this.pathGradientBrush_0, rectangle_4);
			}
		}
	}

	// Token: 0x060001EA RID: 490 RVA: 0x0000840C File Offset: 0x0000660C
	protected void method_62(Color color_0, Color color_1, int int_2, int int_3, int int_4, int int_5)
	{
		this.rectangle_2 = new Rectangle(int_2, int_3, int_4, int_5);
		this.method_64(color_0, color_1, this.rectangle_2);
	}

	// Token: 0x060001EB RID: 491 RVA: 0x00008430 File Offset: 0x00006630
	protected void method_63(Color color_0, Color color_1, int int_2, int int_3, int int_4, int int_5, float float_0)
	{
		this.rectangle_2 = new Rectangle(int_2, int_3, int_4, int_5);
		this.method_65(color_0, color_1, this.rectangle_2, float_0);
	}

	// Token: 0x060001EC RID: 492 RVA: 0x00008454 File Offset: 0x00006654
	protected void method_64(Color color_0, Color color_1, Rectangle rectangle_4)
	{
		this.linearGradientBrush_1 = new LinearGradientBrush(rectangle_4, color_0, color_1, 90f);
		this.graphics_0.FillEllipse(this.linearGradientBrush_1, rectangle_4);
	}

	// Token: 0x060001ED RID: 493 RVA: 0x0000847C File Offset: 0x0000667C
	protected void method_65(Color color_0, Color color_1, Rectangle rectangle_4, float float_0)
	{
		this.linearGradientBrush_1 = new LinearGradientBrush(rectangle_4, color_0, color_1, float_0);
		this.graphics_0.FillEllipse(this.linearGradientBrush_1, rectangle_4);
	}

	// Token: 0x060001EE RID: 494 RVA: 0x000084A0 File Offset: 0x000066A0
	public GraphicsPath method_66(int int_2, int int_3, int int_4, int int_5, int int_6)
	{
		this.rectangle_3 = new Rectangle(int_2, int_3, int_4, int_5);
		return this.method_67(this.rectangle_3, int_6);
	}

	// Token: 0x060001EF RID: 495 RVA: 0x000084D0 File Offset: 0x000066D0
	public GraphicsPath method_67(Rectangle rectangle_4, int int_2)
	{
		this.graphicsPath_1 = new GraphicsPath(FillMode.Winding);
		this.graphicsPath_1.AddArc(rectangle_4.X, rectangle_4.Y, int_2, int_2, 180f, 90f);
		checked
		{
			this.graphicsPath_1.AddArc(rectangle_4.Right - int_2, rectangle_4.Y, int_2, int_2, 270f, 90f);
			this.graphicsPath_1.AddArc(rectangle_4.Right - int_2, rectangle_4.Bottom - int_2, int_2, int_2, 0f, 90f);
			this.graphicsPath_1.AddArc(rectangle_4.X, rectangle_4.Bottom - int_2, int_2, int_2, 90f, 90f);
			this.graphicsPath_1.CloseFigure();
			return this.graphicsPath_1;
		}
	}

	// Token: 0x060001F0 RID: 496 RVA: 0x00008598 File Offset: 0x00006798
	string method_68()
	{
		return base.Text;
	}

	// Token: 0x060001F1 RID: 497 RVA: 0x000085A0 File Offset: 0x000067A0
	void method_69(string string_1)
	{
		base.Text = string_1;
	}

	// Token: 0x060001F2 RID: 498 RVA: 0x000085AC File Offset: 0x000067AC
	Font method_70()
	{
		return base.Font;
	}

	// Token: 0x060001F3 RID: 499 RVA: 0x000085B4 File Offset: 0x000067B4
	void method_71(Font font_0)
	{
		base.Font = font_0;
	}

	// Token: 0x060001F4 RID: 500 RVA: 0x000085C0 File Offset: 0x000067C0
	Color method_72()
	{
		return base.BackColor;
	}

	// Token: 0x060001F5 RID: 501 RVA: 0x000085C8 File Offset: 0x000067C8
	void method_73(Color color_0)
	{
		base.BackColor = color_0;
	}

	// Token: 0x0400004F RID: 79
	protected Graphics graphics_0;

	// Token: 0x04000050 RID: 80
	protected Bitmap bitmap_0;

	// Token: 0x04000051 RID: 81
	private bool bool_0;

	// Token: 0x04000052 RID: 82
	private bool bool_1;

	// Token: 0x04000053 RID: 83
	protected Enum0 enum0_0;

	// Token: 0x04000054 RID: 84
	private bool bool_2;

	// Token: 0x04000055 RID: 85
	private bool bool_3;

	// Token: 0x04000056 RID: 86
	private Image image_0;

	// Token: 0x04000057 RID: 87
	private bool bool_4;

	// Token: 0x04000058 RID: 88
	private Dictionary<string, Color> dictionary_0;

	// Token: 0x04000059 RID: 89
	private string string_0;

	// Token: 0x0400005A RID: 90
	private Size size_0;

	// Token: 0x0400005B RID: 91
	private int int_0;

	// Token: 0x0400005C RID: 92
	private int int_1;

	// Token: 0x0400005D RID: 93
	private bool bool_5;

	// Token: 0x0400005E RID: 94
	private Rectangle rectangle_0;

	// Token: 0x0400005F RID: 95
	private Size size_1;

	// Token: 0x04000060 RID: 96
	private Point point_0;

	// Token: 0x04000061 RID: 97
	private Point point_1;

	// Token: 0x04000062 RID: 98
	private Bitmap bitmap_1;

	// Token: 0x04000063 RID: 99
	private Graphics graphics_1;

	// Token: 0x04000064 RID: 100
	private SolidBrush solidBrush_0;

	// Token: 0x04000065 RID: 101
	private SolidBrush solidBrush_1;

	// Token: 0x04000066 RID: 102
	private Point point_2;

	// Token: 0x04000067 RID: 103
	private Size size_2;

	// Token: 0x04000068 RID: 104
	private Point point_3;

	// Token: 0x04000069 RID: 105
	private LinearGradientBrush linearGradientBrush_0;

	// Token: 0x0400006A RID: 106
	private Rectangle rectangle_1;

	// Token: 0x0400006B RID: 107
	private GraphicsPath graphicsPath_0;

	// Token: 0x0400006C RID: 108
	private PathGradientBrush pathGradientBrush_0;

	// Token: 0x0400006D RID: 109
	private LinearGradientBrush linearGradientBrush_1;

	// Token: 0x0400006E RID: 110
	private Rectangle rectangle_2;

	// Token: 0x0400006F RID: 111
	private GraphicsPath graphicsPath_1;

	// Token: 0x04000070 RID: 112
	private Rectangle rectangle_3;
}

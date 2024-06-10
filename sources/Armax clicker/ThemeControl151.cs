using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.IO;
using System.Windows.Forms;
using Microsoft.VisualBasic.CompilerServices;

namespace WindowsApplication1
{
	// Token: 0x02000014 RID: 20
	internal abstract class ThemeControl151 : Control
	{
		// Token: 0x0600011A RID: 282 RVA: 0x0000DD3C File Offset: 0x0000C13C
		public ThemeControl151()
		{
			this.Items = new Dictionary<string, Color>();
			this.SetStyle(ControlStyles.UserPaint | ControlStyles.Opaque | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this._ImageSize = Size.Empty;
			this.MeasureBitmap = new Bitmap(1, 1);
			this.MeasureGraphics = Graphics.FromImage(this.MeasureBitmap);
			this.Font = new Font("Verdana", 8f);
			this.InvalidateCustimization();
		}

		// Token: 0x0600011B RID: 283 RVA: 0x0000DDAC File Offset: 0x0000C1AC
		protected override void SetBoundsCore(int x, int y, int width, int height, BoundsSpecified specified)
		{
			if (this._LockWidth != 0)
			{
				width = this._LockWidth;
			}
			if (this._LockHeight != 0)
			{
				height = this._LockHeight;
			}
			base.SetBoundsCore(x, y, width, height, specified);
		}

		// Token: 0x0600011C RID: 284 RVA: 0x0000DDE0 File Offset: 0x0000C1E0
		protected sealed override void OnSizeChanged(EventArgs e)
		{
			if (this._Transparent && this.Width != 0 && this.Height != 0)
			{
				this.B = new Bitmap(this.Width, this.Height);
				this.G = Graphics.FromImage(this.B);
			}
			this.Invalidate();
			base.OnSizeChanged(e);
		}

		// Token: 0x0600011D RID: 285 RVA: 0x0000DE3C File Offset: 0x0000C23C
		protected sealed override void OnPaint(PaintEventArgs e)
		{
			if (this.Width != 0)
			{
				if (this.Height == 0)
				{
					return;
				}
				if (this._Transparent)
				{
					this.PaintHook();
					e.Graphics.DrawImage(this.B, 0, 0);
				}
				else
				{
					this.G = e.Graphics;
					this.PaintHook();
				}
			}
		}

		// Token: 0x0600011E RID: 286 RVA: 0x0000DE94 File Offset: 0x0000C294
		protected override void OnHandleCreated(EventArgs e)
		{
			this.InvalidateCustimization();
			this.ColorHook();
			if (this._LockWidth != 0)
			{
				this.Width = this._LockWidth;
			}
			if (this._LockHeight != 0)
			{
				this.Height = this._LockHeight;
			}
			Color right;
			if (!(this.BackColorWait == right))
			{
				this.BackColor = this.BackColorWait;
			}
			this.OnCreation();
			base.OnHandleCreated(e);
		}

		// Token: 0x0600011F RID: 287 RVA: 0x0000DF00 File Offset: 0x0000C300
		protected virtual void OnCreation()
		{
		}

		// Token: 0x06000120 RID: 288 RVA: 0x0000DF04 File Offset: 0x0000C304
		protected override void OnMouseEnter(EventArgs e)
		{
			this.SetState(MouseState.Over);
			base.OnMouseEnter(e);
		}

		// Token: 0x06000121 RID: 289 RVA: 0x0000DF14 File Offset: 0x0000C314
		protected override void OnMouseUp(MouseEventArgs e)
		{
			this.SetState(MouseState.Over);
			base.OnMouseUp(e);
		}

		// Token: 0x06000122 RID: 290 RVA: 0x0000DF24 File Offset: 0x0000C324
		protected override void OnMouseDown(MouseEventArgs e)
		{
			if (e.Button == MouseButtons.Left)
			{
				this.SetState(MouseState.Down);
			}
			base.OnMouseDown(e);
		}

		// Token: 0x06000123 RID: 291 RVA: 0x0000DF44 File Offset: 0x0000C344
		protected override void OnMouseLeave(EventArgs e)
		{
			this.SetState(MouseState.None);
			base.OnMouseLeave(e);
		}

		// Token: 0x06000124 RID: 292 RVA: 0x0000DF54 File Offset: 0x0000C354
		protected override void OnEnabledChanged(EventArgs e)
		{
			if (this.Enabled)
			{
				this.SetState(MouseState.None);
			}
			else
			{
				this.SetState(MouseState.Block);
			}
			base.OnEnabledChanged(e);
		}

		// Token: 0x06000125 RID: 293 RVA: 0x0000DF78 File Offset: 0x0000C378
		private void SetState(MouseState current)
		{
			this.State = current;
			this.Invalidate();
		}

		// Token: 0x17000053 RID: 83
		// (get) Token: 0x06000126 RID: 294 RVA: 0x0000DF88 File Offset: 0x0000C388
		// (set) Token: 0x06000127 RID: 295 RVA: 0x0000DF9C File Offset: 0x0000C39C
		public override Color BackColor
		{
			get
			{
				return base.BackColor;
			}
			set
			{
				if (this.IsHandleCreated)
				{
					base.BackColor = value;
				}
				else
				{
					this.BackColorWait = value;
				}
			}
		}

		// Token: 0x17000054 RID: 84
		// (get) Token: 0x06000128 RID: 296 RVA: 0x0000DFC4 File Offset: 0x0000C3C4
		// (set) Token: 0x06000129 RID: 297 RVA: 0x0000DFD8 File Offset: 0x0000C3D8
		[EditorBrowsable(EditorBrowsableState.Never)]
		[Browsable(false)]
		[DesignerSerializationVisibility(DesignerSerializationVisibility.Hidden)]
		public override Color ForeColor
		{
			get
			{
				return Color.Empty;
			}
			set
			{
			}
		}

		// Token: 0x17000055 RID: 85
		// (get) Token: 0x0600012A RID: 298 RVA: 0x0000DFDC File Offset: 0x0000C3DC
		// (set) Token: 0x0600012B RID: 299 RVA: 0x0000DFEC File Offset: 0x0000C3EC
		[EditorBrowsable(EditorBrowsableState.Never)]
		[DesignerSerializationVisibility(DesignerSerializationVisibility.Hidden)]
		[Browsable(false)]
		public override Image BackgroundImage
		{
			get
			{
				return null;
			}
			set
			{
			}
		}

		// Token: 0x17000056 RID: 86
		// (get) Token: 0x0600012C RID: 300 RVA: 0x0000DFF0 File Offset: 0x0000C3F0
		// (set) Token: 0x0600012D RID: 301 RVA: 0x0000E000 File Offset: 0x0000C400
		[EditorBrowsable(EditorBrowsableState.Never)]
		[DesignerSerializationVisibility(DesignerSerializationVisibility.Hidden)]
		[Browsable(false)]
		public override ImageLayout BackgroundImageLayout
		{
			get
			{
				return ImageLayout.None;
			}
			set
			{
			}
		}

		// Token: 0x17000057 RID: 87
		// (get) Token: 0x0600012E RID: 302 RVA: 0x0000E004 File Offset: 0x0000C404
		// (set) Token: 0x0600012F RID: 303 RVA: 0x0000E018 File Offset: 0x0000C418
		public override string Text
		{
			get
			{
				return base.Text;
			}
			set
			{
				base.Text = value;
				this.Invalidate();
			}
		}

		// Token: 0x17000058 RID: 88
		// (get) Token: 0x06000130 RID: 304 RVA: 0x0000E028 File Offset: 0x0000C428
		// (set) Token: 0x06000131 RID: 305 RVA: 0x0000E03C File Offset: 0x0000C43C
		public override Font Font
		{
			get
			{
				return base.Font;
			}
			set
			{
				base.Font = value;
				this.Invalidate();
			}
		}

		// Token: 0x17000059 RID: 89
		// (get) Token: 0x06000132 RID: 306 RVA: 0x0000E04C File Offset: 0x0000C44C
		// (set) Token: 0x06000133 RID: 307 RVA: 0x0000E060 File Offset: 0x0000C460
		public bool NoRounding
		{
			get
			{
				return this._NoRounding;
			}
			set
			{
				this._NoRounding = value;
				this.Invalidate();
			}
		}

		// Token: 0x1700005A RID: 90
		// (get) Token: 0x06000134 RID: 308 RVA: 0x0000E070 File Offset: 0x0000C470
		// (set) Token: 0x06000135 RID: 309 RVA: 0x0000E084 File Offset: 0x0000C484
		public Image Image
		{
			get
			{
				return this._Image;
			}
			set
			{
				if (value == null)
				{
					this._ImageSize = Size.Empty;
				}
				else
				{
					this._ImageSize = value.Size;
				}
				this._Image = value;
				this.Invalidate();
			}
		}

		// Token: 0x1700005B RID: 91
		// (get) Token: 0x06000136 RID: 310 RVA: 0x0000E0B0 File Offset: 0x0000C4B0
		protected Size ImageSize
		{
			get
			{
				return this._ImageSize;
			}
		}

		// Token: 0x1700005C RID: 92
		// (get) Token: 0x06000137 RID: 311 RVA: 0x0000E0C4 File Offset: 0x0000C4C4
		// (set) Token: 0x06000138 RID: 312 RVA: 0x0000E0D8 File Offset: 0x0000C4D8
		protected int LockWidth
		{
			get
			{
				return this._LockWidth;
			}
			set
			{
				this._LockWidth = value;
				if (this.LockWidth != 0 && this.IsHandleCreated)
				{
					this.Width = this.LockWidth;
				}
			}
		}

		// Token: 0x1700005D RID: 93
		// (get) Token: 0x06000139 RID: 313 RVA: 0x0000E100 File Offset: 0x0000C500
		// (set) Token: 0x0600013A RID: 314 RVA: 0x0000E114 File Offset: 0x0000C514
		protected int LockHeight
		{
			get
			{
				return this._LockHeight;
			}
			set
			{
				this._LockHeight = value;
				if (this.LockHeight != 0 && this.IsHandleCreated)
				{
					this.Height = this.LockHeight;
				}
			}
		}

		// Token: 0x1700005E RID: 94
		// (get) Token: 0x0600013B RID: 315 RVA: 0x0000E13C File Offset: 0x0000C53C
		// (set) Token: 0x0600013C RID: 316 RVA: 0x0000E150 File Offset: 0x0000C550
		public bool Transparent
		{
			get
			{
				return this._Transparent;
			}
			set
			{
				if (!value && this.BackColor.A != 255)
				{
					throw new Exception("Unable to change value to false while a transparent BackColor is in use.");
				}
				this.SetStyle(ControlStyles.Opaque, !value);
				this.SetStyle(ControlStyles.SupportsTransparentBackColor, value);
				if (value)
				{
					this.InvalidateBitmap();
				}
				else
				{
					this.B = null;
				}
				this._Transparent = value;
				this.Invalidate();
			}
		}

		// Token: 0x1700005F RID: 95
		// (get) Token: 0x0600013D RID: 317 RVA: 0x0000E1B8 File Offset: 0x0000C5B8
		// (set) Token: 0x0600013E RID: 318 RVA: 0x0000E214 File Offset: 0x0000C614
		[DesignerSerializationVisibility(DesignerSerializationVisibility.Content)]
		public Bloom[] Colors
		{
			get
			{
				List<Bloom> list = new List<Bloom>();
				Dictionary<string, Color>.Enumerator enumerator = this.Items.GetEnumerator();
				while (enumerator.MoveNext())
				{
					List<Bloom> list2 = list;
					KeyValuePair<string, Color> keyValuePair = enumerator.Current;
					string key = keyValuePair.Key;
					KeyValuePair<string, Color> keyValuePair2 = enumerator.Current;
					list2.Add(new Bloom(key, keyValuePair2.Value));
				}
				return list.ToArray();
			}
			set
			{
				checked
				{
					for (int i = 0; i < value.Length; i++)
					{
						Bloom bloom = value[i];
						if (this.Items.ContainsKey(bloom.Name))
						{
							this.Items[bloom.Name] = bloom.Value;
						}
					}
					this.InvalidateCustimization();
					this.ColorHook();
					this.Invalidate();
				}
			}
		}

		// Token: 0x17000060 RID: 96
		// (get) Token: 0x0600013F RID: 319 RVA: 0x0000E274 File Offset: 0x0000C674
		// (set) Token: 0x06000140 RID: 320 RVA: 0x0000E288 File Offset: 0x0000C688
		public string Customization
		{
			get
			{
				return this._Customization;
			}
			set
			{
				if (Operators.CompareString(value, this._Customization, false) == 0)
				{
					return;
				}
				Bloom[] colors = this.Colors;
				checked
				{
					try
					{
						byte[] value2 = Convert.FromBase64String(value);
						int num = 0;
						int num2 = colors.Length - 1;
						for (int i = num; i <= num2; i++)
						{
							colors[i].Value = Color.FromArgb(BitConverter.ToInt32(value2, i * 4));
						}
					}
					catch (Exception ex)
					{
						return;
					}
					this._Customization = value;
					this.Colors = colors;
					this.ColorHook();
					this.Invalidate();
				}
			}
		}

		// Token: 0x06000141 RID: 321 RVA: 0x0000E314 File Offset: 0x0000C714
		private void InvalidateBitmap()
		{
			if (this.Width != 0)
			{
				if (this.Height == 0)
				{
					return;
				}
				this.B = new Bitmap(this.Width, this.Height);
				this.G = Graphics.FromImage(this.B);
			}
		}

		// Token: 0x06000142 RID: 322 RVA: 0x0000E354 File Offset: 0x0000C754
		protected Color GetColor(string name)
		{
			return this.Items[name];
		}

		// Token: 0x06000143 RID: 323 RVA: 0x0000E370 File Offset: 0x0000C770
		protected void SetColor(string name, Color color)
		{
			if (this.Items.ContainsKey(name))
			{
				this.Items[name] = color;
			}
			else
			{
				this.Items.Add(name, color);
			}
		}

		// Token: 0x06000144 RID: 324 RVA: 0x0000E3A8 File Offset: 0x0000C7A8
		protected void SetColor(string name, byte r, byte g, byte b)
		{
			this.SetColor(name, Color.FromArgb((int)r, (int)g, (int)b));
		}

		// Token: 0x06000145 RID: 325 RVA: 0x0000E3BC File Offset: 0x0000C7BC
		protected void SetColor(string name, byte a, byte r, byte g, byte b)
		{
			this.SetColor(name, Color.FromArgb((int)a, (int)r, (int)g, (int)b));
		}

		// Token: 0x06000146 RID: 326 RVA: 0x0000E3D0 File Offset: 0x0000C7D0
		protected void SetColor(string name, byte a, Color color)
		{
			this.SetColor(name, Color.FromArgb((int)a, color));
		}

		// Token: 0x06000147 RID: 327 RVA: 0x0000E3E0 File Offset: 0x0000C7E0
		private void InvalidateCustimization()
		{
			MemoryStream memoryStream = new MemoryStream(checked(this.Items.Count * 4));
			foreach (Bloom bloom in this.Colors)
			{
				memoryStream.Write(BitConverter.GetBytes(bloom.Value.ToArgb()), 0, 4);
			}
			memoryStream.Close();
			this._Customization = Convert.ToBase64String(memoryStream.ToArray());
		}

		// Token: 0x06000148 RID: 328
		protected abstract void ColorHook();

		// Token: 0x06000149 RID: 329
		protected abstract void PaintHook();

		// Token: 0x0600014A RID: 330 RVA: 0x0000E44C File Offset: 0x0000C84C
		protected Point Center(Rectangle r1, Size s1)
		{
			this.CenterReturn = checked(new Point(r1.Width / 2 - s1.Width / 2 + r1.X, r1.Height / 2 - s1.Height / 2 + r1.Y));
			return this.CenterReturn;
		}

		// Token: 0x0600014B RID: 331 RVA: 0x0000E4A0 File Offset: 0x0000C8A0
		protected Point Center(Rectangle r1, Rectangle r2)
		{
			return this.Center(r1, r2.Size);
		}

		// Token: 0x0600014C RID: 332 RVA: 0x0000E4BC File Offset: 0x0000C8BC
		protected Point Center(int w1, int h1, int w2, int h2)
		{
			this.CenterReturn = checked(new Point(w1 / 2 - w2 / 2, h1 / 2 - h2 / 2));
			return this.CenterReturn;
		}

		// Token: 0x0600014D RID: 333 RVA: 0x0000E4EC File Offset: 0x0000C8EC
		protected Point Center(Size s1, Size s2)
		{
			return this.Center(s1.Width, s1.Height, s2.Width, s2.Height);
		}

		// Token: 0x0600014E RID: 334 RVA: 0x0000E51C File Offset: 0x0000C91C
		protected Point Center(Rectangle r1)
		{
			return this.Center(this.ClientRectangle.Width, this.ClientRectangle.Height, r1.Width, r1.Height);
		}

		// Token: 0x0600014F RID: 335 RVA: 0x0000E55C File Offset: 0x0000C95C
		protected Point Center(Size s1)
		{
			return this.Center(this.Width, this.Height, s1.Width, s1.Height);
		}

		// Token: 0x06000150 RID: 336 RVA: 0x0000E58C File Offset: 0x0000C98C
		protected Point Center(int w1, int h1)
		{
			return this.Center(this.Width, this.Height, w1, h1);
		}

		// Token: 0x06000151 RID: 337 RVA: 0x0000E5B0 File Offset: 0x0000C9B0
		protected Size Measure(string text)
		{
			return this.MeasureGraphics.MeasureString(text, this.Font, this.Width).ToSize();
		}

		// Token: 0x06000152 RID: 338 RVA: 0x0000E5E0 File Offset: 0x0000C9E0
		protected Size Measure()
		{
			return this.MeasureGraphics.MeasureString(this.Text, this.Font, this.Width).ToSize();
		}

		// Token: 0x06000153 RID: 339 RVA: 0x0000E614 File Offset: 0x0000CA14
		protected void DrawCorners(Color c1)
		{
			this.DrawCorners(c1, 0, 0, this.Width, this.Height);
		}

		// Token: 0x06000154 RID: 340 RVA: 0x0000E62C File Offset: 0x0000CA2C
		protected void DrawCorners(Color c1, Rectangle r1)
		{
			this.DrawCorners(c1, r1.X, r1.Y, r1.Width, r1.Height);
		}

		// Token: 0x06000155 RID: 341 RVA: 0x0000E654 File Offset: 0x0000CA54
		protected void DrawCorners(Color c1, int x, int y, int width, int height)
		{
			if (this._NoRounding)
			{
				return;
			}
			checked
			{
				if (this._Transparent)
				{
					this.B.SetPixel(x, y, c1);
					this.B.SetPixel(x + (width - 1), y, c1);
					this.B.SetPixel(x, y + (height - 1), c1);
					this.B.SetPixel(x + (width - 1), y + (height - 1), c1);
				}
				else
				{
					this.DrawCornersBrush = new SolidBrush(c1);
					this.G.FillRectangle(this.DrawCornersBrush, x, y, 1, 1);
					this.G.FillRectangle(this.DrawCornersBrush, x + (width - 1), y, 1, 1);
					this.G.FillRectangle(this.DrawCornersBrush, x, y + (height - 1), 1, 1);
					this.G.FillRectangle(this.DrawCornersBrush, x + (width - 1), y + (height - 1), 1, 1);
				}
			}
		}

		// Token: 0x06000156 RID: 342 RVA: 0x0000E734 File Offset: 0x0000CB34
		protected void DrawBorders(Pen p1, int x, int y, int width, int height, int offset)
		{
			checked
			{
				this.DrawBorders(p1, x + offset, y + offset, width - offset * 2, height - offset * 2);
			}
		}

		// Token: 0x06000157 RID: 343 RVA: 0x0000E754 File Offset: 0x0000CB54
		protected void DrawBorders(Pen p1, int offset)
		{
			this.DrawBorders(p1, 0, 0, this.Width, this.Height, offset);
		}

		// Token: 0x06000158 RID: 344 RVA: 0x0000E76C File Offset: 0x0000CB6C
		protected void DrawBorders(Pen p1, Rectangle r, int offset)
		{
			this.DrawBorders(p1, r.X, r.Y, r.Width, r.Height, offset);
		}

		// Token: 0x06000159 RID: 345 RVA: 0x0000E794 File Offset: 0x0000CB94
		protected void DrawBorders(Pen p1, int x, int y, int width, int height)
		{
			checked
			{
				this.G.DrawRectangle(p1, x, y, width - 1, height - 1);
			}
		}

		// Token: 0x0600015A RID: 346 RVA: 0x0000E7AC File Offset: 0x0000CBAC
		protected void DrawBorders(Pen p1)
		{
			this.DrawBorders(p1, 0, 0, this.Width, this.Height);
		}

		// Token: 0x0600015B RID: 347 RVA: 0x0000E7C4 File Offset: 0x0000CBC4
		protected void DrawBorders(Pen p1, Rectangle r)
		{
			this.DrawBorders(p1, r.X, r.Y, r.Width, r.Height);
		}

		// Token: 0x0600015C RID: 348 RVA: 0x0000E7EC File Offset: 0x0000CBEC
		protected void DrawText(Brush b1, HorizontalAlignment a, int x, int y)
		{
			this.DrawText(b1, this.Text, a, x, y);
		}

		// Token: 0x0600015D RID: 349 RVA: 0x0000E800 File Offset: 0x0000CC00
		protected void DrawText(Brush b1, Point p1)
		{
			this.DrawText(b1, this.Text, p1.X, p1.Y);
		}

		// Token: 0x0600015E RID: 350 RVA: 0x0000E820 File Offset: 0x0000CC20
		protected void DrawText(Brush b1, int x, int y)
		{
			this.DrawText(b1, this.Text, x, y);
		}

		// Token: 0x0600015F RID: 351 RVA: 0x0000E834 File Offset: 0x0000CC34
		protected void DrawText(Brush b1, string text, HorizontalAlignment a, int x, int y)
		{
			if (text.Length == 0)
			{
				return;
			}
			this.DrawTextSize = this.Measure(text);
			this.DrawTextPoint = this.Center(this.DrawTextSize);
			checked
			{
				switch (a)
				{
				case HorizontalAlignment.Left:
					this.DrawText(b1, text, x, this.DrawTextPoint.Y + y);
					break;
				case HorizontalAlignment.Right:
					this.DrawText(b1, text, this.Width - this.DrawTextSize.Width - x, this.DrawTextPoint.Y + y);
					break;
				case HorizontalAlignment.Center:
					this.DrawText(b1, text, this.DrawTextPoint.X + x, this.DrawTextPoint.Y + y);
					break;
				}
			}
		}

		// Token: 0x06000160 RID: 352 RVA: 0x0000E8EC File Offset: 0x0000CCEC
		protected void DrawText(Brush b1, string text, Point p1)
		{
			this.DrawText(b1, text, p1.X, p1.Y);
		}

		// Token: 0x06000161 RID: 353 RVA: 0x0000E904 File Offset: 0x0000CD04
		protected void DrawText(Brush b1, string text, int x, int y)
		{
			if (text.Length == 0)
			{
				return;
			}
			this.G.DrawString(text, this.Font, b1, (float)x, (float)y);
		}

		// Token: 0x06000162 RID: 354 RVA: 0x0000E928 File Offset: 0x0000CD28
		protected void DrawImage(HorizontalAlignment a, int x, int y)
		{
			this.DrawImage(this._Image, a, x, y);
		}

		// Token: 0x06000163 RID: 355 RVA: 0x0000E93C File Offset: 0x0000CD3C
		protected void DrawImage(Point p1)
		{
			this.DrawImage(this._Image, p1.X, p1.Y);
		}

		// Token: 0x06000164 RID: 356 RVA: 0x0000E958 File Offset: 0x0000CD58
		protected void DrawImage(int x, int y)
		{
			this.DrawImage(this._Image, x, y);
		}

		// Token: 0x06000165 RID: 357 RVA: 0x0000E968 File Offset: 0x0000CD68
		protected void DrawImage(Image image, HorizontalAlignment a, int x, int y)
		{
			if (image == null)
			{
				return;
			}
			this.DrawImagePoint = this.Center(image.Size);
			checked
			{
				switch (a)
				{
				case HorizontalAlignment.Left:
					this.DrawImage(image, x, this.DrawImagePoint.Y + y);
					break;
				case HorizontalAlignment.Right:
					this.DrawImage(image, this.Width - image.Width - x, this.DrawImagePoint.Y + y);
					break;
				case HorizontalAlignment.Center:
					this.DrawImage(image, this.DrawImagePoint.X + x, this.DrawImagePoint.Y + y);
					break;
				}
			}
		}

		// Token: 0x06000166 RID: 358 RVA: 0x0000EA00 File Offset: 0x0000CE00
		protected void DrawImage(Image image, Point p1)
		{
			this.DrawImage(image, p1.X, p1.Y);
		}

		// Token: 0x06000167 RID: 359 RVA: 0x0000EA18 File Offset: 0x0000CE18
		protected void DrawImage(Image image, int x, int y)
		{
			if (image == null)
			{
				return;
			}
			this.G.DrawImage(image, x, y, image.Width, image.Height);
		}

		// Token: 0x06000168 RID: 360 RVA: 0x0000EA38 File Offset: 0x0000CE38
		protected void DrawGradient(ColorBlend blend, int x, int y, int width, int height)
		{
			this.DrawGradient(blend, x, y, width, height, 90f);
		}

		// Token: 0x06000169 RID: 361 RVA: 0x0000EA4C File Offset: 0x0000CE4C
		protected void DrawGradient(Color c1, Color c2, int x, int y, int width, int height)
		{
			this.DrawGradient(c1, c2, x, y, width, height, 90f);
		}

		// Token: 0x0600016A RID: 362 RVA: 0x0000EA64 File Offset: 0x0000CE64
		protected void DrawGradient(ColorBlend blend, int x, int y, int width, int height, float angle)
		{
			this.DrawGradientRectangle = new Rectangle(x, y, width, height);
			this.DrawGradient(blend, this.DrawGradientRectangle, angle);
		}

		// Token: 0x0600016B RID: 363 RVA: 0x0000EA88 File Offset: 0x0000CE88
		protected void DrawGradient(Color c1, Color c2, int x, int y, int width, int height, float angle)
		{
			this.DrawGradientRectangle = new Rectangle(x, y, width, height);
			this.DrawGradient(c1, c2, this.DrawGradientRectangle, angle);
		}

		// Token: 0x0600016C RID: 364 RVA: 0x0000EAAC File Offset: 0x0000CEAC
		protected void DrawGradient(ColorBlend blend, Rectangle r, float angle)
		{
			this.DrawGradientBrush = new LinearGradientBrush(r, Color.Empty, Color.Empty, angle);
			this.DrawGradientBrush.InterpolationColors = blend;
			this.G.FillRectangle(this.DrawGradientBrush, r);
		}

		// Token: 0x0600016D RID: 365 RVA: 0x0000EAE4 File Offset: 0x0000CEE4
		protected void DrawGradient(Color c1, Color c2, Rectangle r, float angle)
		{
			this.DrawGradientBrush = new LinearGradientBrush(r, c1, c2, angle);
			this.G.FillRectangle(this.DrawGradientBrush, r);
		}

		// Token: 0x0400007D RID: 125
		protected Graphics G;

		// Token: 0x0400007E RID: 126
		protected Bitmap B;

		// Token: 0x0400007F RID: 127
		protected MouseState State;

		// Token: 0x04000080 RID: 128
		private Color BackColorWait;

		// Token: 0x04000081 RID: 129
		private bool _NoRounding;

		// Token: 0x04000082 RID: 130
		private Image _Image;

		// Token: 0x04000083 RID: 131
		private Size _ImageSize;

		// Token: 0x04000084 RID: 132
		private int _LockWidth;

		// Token: 0x04000085 RID: 133
		private int _LockHeight;

		// Token: 0x04000086 RID: 134
		private bool _Transparent;

		// Token: 0x04000087 RID: 135
		private Dictionary<string, Color> Items;

		// Token: 0x04000088 RID: 136
		private string _Customization;

		// Token: 0x04000089 RID: 137
		private Point CenterReturn;

		// Token: 0x0400008A RID: 138
		private Bitmap MeasureBitmap;

		// Token: 0x0400008B RID: 139
		private Graphics MeasureGraphics;

		// Token: 0x0400008C RID: 140
		private SolidBrush DrawCornersBrush;

		// Token: 0x0400008D RID: 141
		private Point DrawTextPoint;

		// Token: 0x0400008E RID: 142
		private Size DrawTextSize;

		// Token: 0x0400008F RID: 143
		private Point DrawImagePoint;

		// Token: 0x04000090 RID: 144
		private LinearGradientBrush DrawGradientBrush;

		// Token: 0x04000091 RID: 145
		private Rectangle DrawGradientRectangle;
	}
}

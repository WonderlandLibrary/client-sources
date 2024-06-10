using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;

namespace svchost
{
	// Token: 0x0200002D RID: 45
	[DefaultEvent("ToggleChanged")]
	public class LogInOnOffSwitch : Control
	{
		// Token: 0x14000003 RID: 3
		// (add) Token: 0x060002D6 RID: 726 RVA: 0x000101C8 File Offset: 0x0000E3C8
		// (remove) Token: 0x060002D7 RID: 727 RVA: 0x00010200 File Offset: 0x0000E400
		public event LogInOnOffSwitch.ToggleChangedEventHandler ToggleChanged;

		// Token: 0x17000105 RID: 261
		// (get) Token: 0x060002D8 RID: 728 RVA: 0x00010238 File Offset: 0x0000E438
		// (set) Token: 0x060002D9 RID: 729 RVA: 0x0000344B File Offset: 0x0000164B
		[Category("Colours")]
		public Color BaseColour
		{
			get
			{
				return this._BaseColour;
			}
			set
			{
				this._BaseColour = value;
				base.Invalidate();
			}
		}

		// Token: 0x17000106 RID: 262
		// (get) Token: 0x060002DA RID: 730 RVA: 0x00010250 File Offset: 0x0000E450
		// (set) Token: 0x060002DB RID: 731 RVA: 0x0000345C File Offset: 0x0000165C
		[Category("Colours")]
		public Color BorderColour
		{
			get
			{
				return this._BorderColour;
			}
			set
			{
				this._BorderColour = value;
				base.Invalidate();
			}
		}

		// Token: 0x17000107 RID: 263
		// (get) Token: 0x060002DC RID: 732 RVA: 0x00010268 File Offset: 0x0000E468
		// (set) Token: 0x060002DD RID: 733 RVA: 0x0000346D File Offset: 0x0000166D
		[Category("Colours")]
		public Color TextColour
		{
			get
			{
				return this._TextColour;
			}
			set
			{
				this._TextColour = value;
				base.Invalidate();
			}
		}

		// Token: 0x17000108 RID: 264
		// (get) Token: 0x060002DE RID: 734 RVA: 0x00010280 File Offset: 0x0000E480
		// (set) Token: 0x060002DF RID: 735 RVA: 0x0000347E File Offset: 0x0000167E
		[Category("Colours")]
		public Color NonToggledTextColourderColour
		{
			get
			{
				return this._NonToggledTextColour;
			}
			set
			{
				this._NonToggledTextColour = value;
				base.Invalidate();
			}
		}

		// Token: 0x17000109 RID: 265
		// (get) Token: 0x060002E0 RID: 736 RVA: 0x00010298 File Offset: 0x0000E498
		// (set) Token: 0x060002E1 RID: 737 RVA: 0x0000348F File Offset: 0x0000168F
		[Category("Colours")]
		public Color ToggledColour
		{
			get
			{
				return this._ToggledColour;
			}
			set
			{
				this._ToggledColour = value;
				base.Invalidate();
			}
		}

		// Token: 0x14000004 RID: 4
		// (add) Token: 0x060002E2 RID: 738 RVA: 0x000102B0 File Offset: 0x0000E4B0
		// (remove) Token: 0x060002E3 RID: 739 RVA: 0x000102E8 File Offset: 0x0000E4E8
		public event LogInOnOffSwitch.ToggledChangedEventHandler ToggledChanged;

		// Token: 0x060002E4 RID: 740 RVA: 0x00010320 File Offset: 0x0000E520
		protected override void OnMouseMove(MouseEventArgs e)
		{
			base.OnMouseMove(e);
			this.MouseXLoc = e.Location.X;
			base.Invalidate();
			bool flag = e.X < checked(base.Width - 40) && e.X > 40;
			if (flag)
			{
				this.Cursor = Cursors.IBeam;
			}
			else
			{
				this.Cursor = Cursors.Arrow;
			}
		}

		// Token: 0x060002E5 RID: 741 RVA: 0x00010390 File Offset: 0x0000E590
		protected override void OnMouseDown(MouseEventArgs e)
		{
			base.OnMouseDown(e);
			bool flag = this.MouseXLoc > checked(base.Width - 39);
			if (flag)
			{
				this._Toggled = LogInOnOffSwitch.Toggles.Toggled;
				this.ToggledValue();
			}
			else
			{
				bool flag2 = this.MouseXLoc < 39;
				if (flag2)
				{
					this._Toggled = LogInOnOffSwitch.Toggles.NotToggled;
					this.ToggledValue();
				}
			}
			base.Invalidate();
		}

		// Token: 0x1700010A RID: 266
		// (get) Token: 0x060002E6 RID: 742 RVA: 0x000103F0 File Offset: 0x0000E5F0
		// (set) Token: 0x060002E7 RID: 743 RVA: 0x000034A0 File Offset: 0x000016A0
		public LogInOnOffSwitch.Toggles Toggled
		{
			get
			{
				return this._Toggled;
			}
			set
			{
				this._Toggled = value;
				base.Invalidate();
			}
		}

		// Token: 0x060002E8 RID: 744 RVA: 0x00010408 File Offset: 0x0000E608
		private void ToggledValue()
		{
			bool flag = this._Toggled > LogInOnOffSwitch.Toggles.Toggled;
			checked
			{
				if (flag)
				{
					bool flag2 = this.ToggleLocation < 100;
					if (flag2)
					{
						ref int ptr = ref this.ToggleLocation;
						this.ToggleLocation = ptr + 10;
					}
				}
				else
				{
					bool flag3 = this.ToggleLocation > 0;
					if (flag3)
					{
						ref int ptr = ref this.ToggleLocation;
						this.ToggleLocation = ptr - 10;
					}
				}
				base.Invalidate();
			}
		}

		// Token: 0x060002E9 RID: 745 RVA: 0x0001046C File Offset: 0x0000E66C
		public LogInOnOffSwitch()
		{
			this._Toggled = LogInOnOffSwitch.Toggles.NotToggled;
			this.ToggleLocation = 0;
			this._BaseColour = Color.FromArgb(42, 42, 42);
			this._BorderColour = Color.FromArgb(35, 35, 35);
			this._TextColour = Color.FromArgb(255, 255, 255);
			this._NonToggledTextColour = Color.FromArgb(125, 125, 125);
			this._ToggledColour = Color.FromArgb(255, 0, 0);
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.DoubleBuffer, true);
			this.BackColor = Color.FromArgb(54, 54, 54);
		}

		// Token: 0x060002EA RID: 746 RVA: 0x00010510 File Offset: 0x0000E710
		protected override void OnPaint(PaintEventArgs e)
		{
			Graphics G = e.Graphics;
			Graphics graphics = G;
			graphics.Clear(this.BackColor);
			graphics.SmoothingMode = SmoothingMode.HighQuality;
			graphics.PixelOffsetMode = PixelOffsetMode.HighQuality;
			graphics.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			graphics.FillRectangle(new SolidBrush(this._BaseColour), new Rectangle(0, 0, 39, base.Height));
			checked
			{
				graphics.FillRectangle(new SolidBrush(this._BaseColour), new Rectangle(base.Width - 40, 0, base.Width, base.Height));
				graphics.FillRectangle(new SolidBrush(this._BaseColour), new Rectangle(38, 9, base.Width - 40, 5));
				Point[] P = new Point[]
				{
					new Point(0, 0),
					new Point(39, 0),
					new Point(39, 9),
					new Point(base.Width - 40, 9),
					new Point(base.Width - 40, 0),
					new Point(base.Width - 2, 0),
					new Point(base.Width - 2, base.Height - 1),
					new Point(base.Width - 40, base.Height - 1),
					new Point(base.Width - 40, 14),
					new Point(39, 14),
					new Point(39, base.Height - 1),
					new Point(0, base.Height - 1),
					default(Point)
				};
				graphics.DrawLines(new Pen(this._BorderColour, 2f), P);
				bool flag = this._Toggled == LogInOnOffSwitch.Toggles.Toggled;
				if (flag)
				{
					graphics.FillRectangle(new SolidBrush(this._ToggledColour), new Rectangle((int)Math.Round((double)base.Width / 2.0), 10, (int)Math.Round(unchecked((double)base.Width / 2.0 - 38.0)), 3));
					graphics.FillRectangle(new SolidBrush(this._ToggledColour), new Rectangle(base.Width - 39, 2, 36, base.Height - 5));
					graphics.DrawString("ON", new Font("Microsoft Sans Serif", 7f, FontStyle.Bold), new SolidBrush(this._TextColour), new Rectangle(2, -1, (int)Math.Round(unchecked((double)(checked(base.Width - 20)) + 6.666666666666667)), base.Height), new StringFormat
					{
						Alignment = StringAlignment.Far,
						LineAlignment = StringAlignment.Center
					});
					graphics.DrawString("OFF", new Font("Microsoft Sans Serif", 7f, FontStyle.Bold), new SolidBrush(this._NonToggledTextColour), new Rectangle(7, -1, (int)Math.Round(unchecked((double)(checked(base.Width - 20)) + 6.666666666666667)), base.Height), new StringFormat
					{
						Alignment = StringAlignment.Near,
						LineAlignment = StringAlignment.Center
					});
				}
				else
				{
					bool flag2 = this._Toggled == LogInOnOffSwitch.Toggles.NotToggled;
					if (flag2)
					{
						graphics.DrawString("OFF", new Font("Microsoft Sans Serif", 7f, FontStyle.Bold), new SolidBrush(this._TextColour), new Rectangle(7, -1, (int)Math.Round(unchecked((double)(checked(base.Width - 20)) + 6.666666666666667)), base.Height), new StringFormat
						{
							Alignment = StringAlignment.Near,
							LineAlignment = StringAlignment.Center
						});
						graphics.DrawString("ON", new Font("Microsoft Sans Serif", 7f, FontStyle.Bold), new SolidBrush(this._NonToggledTextColour), new Rectangle(2, -1, (int)Math.Round(unchecked((double)(checked(base.Width - 20)) + 6.666666666666667)), base.Height), new StringFormat
						{
							Alignment = StringAlignment.Far,
							LineAlignment = StringAlignment.Center
						});
					}
				}
				graphics.DrawLine(new Pen(this._BorderColour, 2f), new Point((int)Math.Round((double)base.Width / 2.0), 0), new Point((int)Math.Round((double)base.Width / 2.0), base.Height));
			}
		}

		// Token: 0x04000121 RID: 289
		private LogInOnOffSwitch.Toggles _Toggled;

		// Token: 0x04000122 RID: 290
		private int MouseXLoc;

		// Token: 0x04000123 RID: 291
		private int ToggleLocation;

		// Token: 0x04000124 RID: 292
		private Color _BaseColour;

		// Token: 0x04000125 RID: 293
		private Color _BorderColour;

		// Token: 0x04000126 RID: 294
		private Color _TextColour;

		// Token: 0x04000127 RID: 295
		private Color _NonToggledTextColour;

		// Token: 0x04000128 RID: 296
		private Color _ToggledColour;

		// Token: 0x0200002E RID: 46
		// (Invoke) Token: 0x060002EE RID: 750
		public delegate void ToggleChangedEventHandler(object sender);

		// Token: 0x0200002F RID: 47
		public enum Toggles
		{
			// Token: 0x0400012B RID: 299
			Toggled,
			// Token: 0x0400012C RID: 300
			NotToggled
		}

		// Token: 0x02000030 RID: 48
		// (Invoke) Token: 0x060002F2 RID: 754
		public delegate void ToggledChangedEventHandler();
	}
}

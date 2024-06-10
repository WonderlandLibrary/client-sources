using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Windows.Forms;
using Microsoft.VisualBasic.CompilerServices;

namespace Meth
{
	// Token: 0x02000014 RID: 20
	[DefaultEvent("CheckedChanged")]
	public class ThirteenCheckBox : Control
	{
		// Token: 0x17000092 RID: 146
		// (get) Token: 0x060001C6 RID: 454 RVA: 0x0000EECB File Offset: 0x0000D0CB
		// (set) Token: 0x060001C7 RID: 455 RVA: 0x0000EED3 File Offset: 0x0000D0D3
		public ThirteenCheckBox.ColorSchemes ColorScheme
		{
			get
			{
				return this._ColorScheme;
			}
			set
			{
				this._ColorScheme = value;
				base.Invalidate();
			}
		}

		// Token: 0x060001C8 RID: 456 RVA: 0x0000EEE2 File Offset: 0x0000D0E2
		protected override void OnMouseEnter(EventArgs e)
		{
			base.OnMouseEnter(e);
			this.State = ThirteenCheckBox.MouseState.Over;
			base.Invalidate();
		}

		// Token: 0x060001C9 RID: 457 RVA: 0x0000EEF8 File Offset: 0x0000D0F8
		protected override void OnMouseDown(MouseEventArgs e)
		{
			base.OnMouseDown(e);
			this.State = ThirteenCheckBox.MouseState.Down;
			base.Invalidate();
		}

		// Token: 0x060001CA RID: 458 RVA: 0x0000EF0E File Offset: 0x0000D10E
		protected override void OnMouseLeave(EventArgs e)
		{
			base.OnMouseLeave(e);
			this.State = ThirteenCheckBox.MouseState.None;
			base.Invalidate();
		}

		// Token: 0x060001CB RID: 459 RVA: 0x0000EF24 File Offset: 0x0000D124
		protected override void OnMouseUp(MouseEventArgs e)
		{
			base.OnMouseUp(e);
			this.State = ThirteenCheckBox.MouseState.Over;
			base.Invalidate();
		}

		// Token: 0x060001CC RID: 460 RVA: 0x0000EF3C File Offset: 0x0000D13C
		protected override void OnTextChanged(EventArgs e)
		{
			base.OnTextChanged(e);
			base.Width = (int)Math.Round((double)(base.CreateGraphics().MeasureString(this.Text, this.Font).Width + 6f + (float)base.Height));
			base.Invalidate();
		}

		// Token: 0x17000093 RID: 147
		// (get) Token: 0x060001CD RID: 461 RVA: 0x0000EF90 File Offset: 0x0000D190
		// (set) Token: 0x060001CE RID: 462 RVA: 0x0000EF98 File Offset: 0x0000D198
		public bool Checked
		{
			get
			{
				return this._Checked;
			}
			set
			{
				this._Checked = value;
				base.Invalidate();
			}
		}

		// Token: 0x060001CF RID: 463 RVA: 0x0000EFA7 File Offset: 0x0000D1A7
		protected override void OnResize(EventArgs e)
		{
			base.OnResize(e);
			base.Height = 17;
		}

		// Token: 0x060001D0 RID: 464 RVA: 0x0000EFB8 File Offset: 0x0000D1B8
		protected override void OnClick(EventArgs e)
		{
			this._Checked = !this._Checked;
			ThirteenCheckBox.CheckedChangedEventHandler checkedChangedEvent = this.CheckedChangedEvent;
			if (checkedChangedEvent != null)
			{
				checkedChangedEvent(this);
			}
			base.OnClick(e);
		}

		// Token: 0x14000013 RID: 19
		// (add) Token: 0x060001D1 RID: 465 RVA: 0x0000EFEC File Offset: 0x0000D1EC
		// (remove) Token: 0x060001D2 RID: 466 RVA: 0x0000F024 File Offset: 0x0000D224
		public event ThirteenCheckBox.CheckedChangedEventHandler CheckedChanged;

		// Token: 0x060001D3 RID: 467 RVA: 0x0000F05C File Offset: 0x0000D25C
		public ThirteenCheckBox()
		{
			this.State = ThirteenCheckBox.MouseState.None;
			base.SetStyle(ControlStyles.UserPaint, true);
			base.SetStyle(ControlStyles.AllPaintingInWmPaint, true);
			base.SetStyle(ControlStyles.OptimizedDoubleBuffer, true);
			base.SetStyle(ControlStyles.SupportsTransparentBackColor, true);
			this.ColorScheme = ThirteenCheckBox.ColorSchemes.Dark;
			this.BackColor = Color.FromArgb(50, 50, 50);
			this.ForeColor = Color.White;
			base.Size = new Size(147, 17);
			this.DoubleBuffered = true;
		}

		// Token: 0x060001D4 RID: 468 RVA: 0x0000F0E0 File Offset: 0x0000D2E0
		protected override void OnPaint(PaintEventArgs e)
		{
			Bitmap bitmap = new Bitmap(base.Width, base.Height);
			Graphics graphics = Graphics.FromImage(bitmap);
			graphics.SmoothingMode = SmoothingMode.HighQuality;
			Rectangle rect = new Rectangle(0, 0, base.Height - 1, base.Height - 1);
			graphics.Clear(this.BackColor);
			ThirteenCheckBox.ColorSchemes colorScheme = this.ColorScheme;
			if (colorScheme != ThirteenCheckBox.ColorSchemes.Light)
			{
				if (colorScheme == ThirteenCheckBox.ColorSchemes.Dark)
				{
					graphics.FillRectangle(new SolidBrush(Color.FromArgb(215, Color.White)), rect);
				}
			}
			else
			{
				graphics.FillRectangle(new SolidBrush(Color.FromArgb(215, Color.Black)), rect);
			}
			if (this.Checked)
			{
				Rectangle rectangle = new Rectangle((int)Math.Round((double)rect.X + (double)rect.Width / 4.0), (int)Math.Round((double)rect.Y + (double)rect.Height / 4.0), rect.Width / 2, rect.Height / 2);
				Point[] array = new Point[]
				{
					new Point(rectangle.X, rectangle.Y + rectangle.Height / 2),
					new Point(rectangle.X + rectangle.Width / 2, rectangle.Y + rectangle.Height),
					new Point(rectangle.X + rectangle.Width, rectangle.Y)
				};
				ThirteenCheckBox.ColorSchemes colorScheme2 = this.ColorScheme;
				if (colorScheme2 != ThirteenCheckBox.ColorSchemes.Light)
				{
					if (colorScheme2 == ThirteenCheckBox.ColorSchemes.Dark)
					{
						int num = array.Length - 2;
						for (int i = 0; i <= num; i++)
						{
							graphics.DrawLine(new Pen(Color.Black, 2f), array[i], array[i + 1]);
						}
					}
				}
				else
				{
					int num2 = array.Length - 2;
					for (int j = 0; j <= num2; j++)
					{
						graphics.DrawLine(new Pen(Color.White, 2f), array[j], array[j + 1]);
					}
				}
			}
			graphics.DrawString(this.Text, this.Font, new SolidBrush(this.ForeColor), new Point(18, 2), new StringFormat
			{
				Alignment = StringAlignment.Near,
				LineAlignment = StringAlignment.Near
			});
			NewLateBinding.LateCall(e.Graphics, null, "DrawImage", new object[]
			{
				bitmap.Clone(),
				0,
				0
			}, null, null, null, true);
			graphics.Dispose();
			bitmap.Dispose();
		}

		// Token: 0x040000E6 RID: 230
		private ThirteenCheckBox.ColorSchemes _ColorScheme;

		// Token: 0x040000E7 RID: 231
		private ThirteenCheckBox.MouseState State;

		// Token: 0x040000E8 RID: 232
		private bool _Checked;

		// Token: 0x02000056 RID: 86
		public enum ColorSchemes
		{
			// Token: 0x040001D1 RID: 465
			Light,
			// Token: 0x040001D2 RID: 466
			Dark
		}

		// Token: 0x02000057 RID: 87
		public enum MouseState
		{
			// Token: 0x040001D4 RID: 468
			None,
			// Token: 0x040001D5 RID: 469
			Over,
			// Token: 0x040001D6 RID: 470
			Down
		}

		// Token: 0x02000058 RID: 88
		// (Invoke) Token: 0x0600038F RID: 911
		public delegate void CheckedChangedEventHandler(object sender);
	}
}

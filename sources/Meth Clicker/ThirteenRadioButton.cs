using System;
using System.Collections;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Windows.Forms;
using Microsoft.VisualBasic.CompilerServices;

namespace Meth
{
	// Token: 0x02000013 RID: 19
	[DefaultEvent("CheckedChanged")]
	public class ThirteenRadioButton : Control
	{
		// Token: 0x17000090 RID: 144
		// (get) Token: 0x060001B5 RID: 437 RVA: 0x0000EAB0 File Offset: 0x0000CCB0
		// (set) Token: 0x060001B6 RID: 438 RVA: 0x0000EAB8 File Offset: 0x0000CCB8
		public ThirteenRadioButton.ColorSchemes ColorScheme
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

		// Token: 0x060001B7 RID: 439 RVA: 0x0000EAC7 File Offset: 0x0000CCC7
		protected override void OnMouseEnter(EventArgs e)
		{
			base.OnMouseEnter(e);
			this.State = ThirteenRadioButton.MouseState.Over;
			base.Invalidate();
		}

		// Token: 0x060001B8 RID: 440 RVA: 0x0000EADD File Offset: 0x0000CCDD
		protected override void OnMouseDown(MouseEventArgs e)
		{
			base.OnMouseDown(e);
			this.State = ThirteenRadioButton.MouseState.Down;
			base.Invalidate();
		}

		// Token: 0x060001B9 RID: 441 RVA: 0x0000EAF3 File Offset: 0x0000CCF3
		protected override void OnMouseLeave(EventArgs e)
		{
			base.OnMouseLeave(e);
			this.State = ThirteenRadioButton.MouseState.None;
			base.Invalidate();
		}

		// Token: 0x060001BA RID: 442 RVA: 0x0000EB09 File Offset: 0x0000CD09
		protected override void OnMouseUp(MouseEventArgs e)
		{
			base.OnMouseUp(e);
			this.State = ThirteenRadioButton.MouseState.Over;
			base.Invalidate();
		}

		// Token: 0x060001BB RID: 443 RVA: 0x0000EB1F File Offset: 0x0000CD1F
		protected override void OnResize(EventArgs e)
		{
			base.OnResize(e);
			base.Height = 18;
		}

		// Token: 0x060001BC RID: 444 RVA: 0x0000EB30 File Offset: 0x0000CD30
		protected override void OnTextChanged(EventArgs e)
		{
			base.OnTextChanged(e);
			base.Width = (int)Math.Round((double)base.CreateGraphics().MeasureString(this.Text, this.Font).Width + 7.0 + (double)(base.Height * 2));
			base.Invalidate();
		}

		// Token: 0x17000091 RID: 145
		// (get) Token: 0x060001BD RID: 445 RVA: 0x0000EB8A File Offset: 0x0000CD8A
		// (set) Token: 0x060001BE RID: 446 RVA: 0x0000EB94 File Offset: 0x0000CD94
		public bool Checked
		{
			get
			{
				return this._Checked;
			}
			set
			{
				this._Checked = value;
				this.InvalidateControls();
				ThirteenRadioButton.CheckedChangedEventHandler checkedChangedEvent = this.CheckedChangedEvent;
				if (checkedChangedEvent != null)
				{
					checkedChangedEvent(this);
				}
				base.Invalidate();
			}
		}

		// Token: 0x060001BF RID: 447 RVA: 0x0000EBC5 File Offset: 0x0000CDC5
		protected override void OnClick(EventArgs e)
		{
			if (!this._Checked)
			{
				this.Checked = true;
			}
			base.OnClick(e);
		}

		// Token: 0x14000012 RID: 18
		// (add) Token: 0x060001C0 RID: 448 RVA: 0x0000EBE0 File Offset: 0x0000CDE0
		// (remove) Token: 0x060001C1 RID: 449 RVA: 0x0000EC18 File Offset: 0x0000CE18
		public event ThirteenRadioButton.CheckedChangedEventHandler CheckedChanged;

		// Token: 0x060001C2 RID: 450 RVA: 0x0000EC4D File Offset: 0x0000CE4D
		protected override void OnCreateControl()
		{
			base.OnCreateControl();
			this.InvalidateControls();
		}

		// Token: 0x060001C3 RID: 451 RVA: 0x0000EC5C File Offset: 0x0000CE5C
		private void InvalidateControls()
		{
			if (base.IsHandleCreated && this._Checked)
			{
				try
				{
					foreach (object obj in base.Parent.Controls)
					{
						Control control = (Control)obj;
						if (control != this && control is ThirteenRadioButton)
						{
							((ThirteenRadioButton)control).Checked = false;
						}
					}
				}
				finally
				{
					IEnumerator enumerator;
					if (enumerator is IDisposable)
					{
						(enumerator as IDisposable).Dispose();
					}
				}
			}
		}

		// Token: 0x060001C4 RID: 452 RVA: 0x0000ECE0 File Offset: 0x0000CEE0
		public ThirteenRadioButton()
		{
			this.State = ThirteenRadioButton.MouseState.None;
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.SupportsTransparentBackColor, true);
			this.ColorScheme = ThirteenRadioButton.ColorSchemes.Dark;
			this.BackColor = Color.FromArgb(50, 50, 50);
			this.ForeColor = Color.White;
			this.DoubleBuffered = true;
			base.Size = new Size(177, 18);
		}

		// Token: 0x060001C5 RID: 453 RVA: 0x0000ED44 File Offset: 0x0000CF44
		protected override void OnPaint(PaintEventArgs e)
		{
			Bitmap bitmap = new Bitmap(base.Width, base.Height);
			Graphics graphics = Graphics.FromImage(bitmap);
			Rectangle rect = new Rectangle(0, 0, base.Height - 1, base.Height - 1);
			graphics.SmoothingMode = SmoothingMode.HighQuality;
			graphics.Clear(this.BackColor);
			ThirteenRadioButton.ColorSchemes colorScheme = this.ColorScheme;
			if (colorScheme != ThirteenRadioButton.ColorSchemes.Dark)
			{
				if (colorScheme == ThirteenRadioButton.ColorSchemes.Light)
				{
					graphics.FillEllipse(new SolidBrush(Color.FromArgb(215, Color.Black)), rect);
				}
			}
			else
			{
				graphics.FillEllipse(new SolidBrush(Color.FromArgb(215, Color.White)), rect);
			}
			if (this.Checked)
			{
				ThirteenRadioButton.ColorSchemes colorScheme2 = this.ColorScheme;
				if (colorScheme2 != ThirteenRadioButton.ColorSchemes.Dark)
				{
					if (colorScheme2 == ThirteenRadioButton.ColorSchemes.Light)
					{
						graphics.FillEllipse(new SolidBrush(Color.White), new Rectangle(4, 4, base.Height - 9, base.Height - 9));
					}
				}
				else
				{
					graphics.FillEllipse(new SolidBrush(Color.Black), new Rectangle(4, 4, base.Height - 9, base.Height - 9));
				}
			}
			graphics.DrawString(this.Text, this.Font, new SolidBrush(this.ForeColor), new Point(22, 1), new StringFormat
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

		// Token: 0x040000E2 RID: 226
		private ThirteenRadioButton.MouseState State;

		// Token: 0x040000E3 RID: 227
		private ThirteenRadioButton.ColorSchemes _ColorScheme;

		// Token: 0x040000E4 RID: 228
		private bool _Checked;

		// Token: 0x02000053 RID: 83
		public enum MouseState
		{
			// Token: 0x040001CA RID: 458
			None,
			// Token: 0x040001CB RID: 459
			Over,
			// Token: 0x040001CC RID: 460
			Down
		}

		// Token: 0x02000054 RID: 84
		public enum ColorSchemes
		{
			// Token: 0x040001CE RID: 462
			Dark,
			// Token: 0x040001CF RID: 463
			Light
		}

		// Token: 0x02000055 RID: 85
		// (Invoke) Token: 0x0600038B RID: 907
		public delegate void CheckedChangedEventHandler(object sender);
	}
}

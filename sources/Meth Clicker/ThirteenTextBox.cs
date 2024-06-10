using System;
using System.Drawing;
using System.Windows.Forms;

namespace Meth
{
	// Token: 0x02000010 RID: 16
	public class ThirteenTextBox : TextBox
	{
		// Token: 0x14000010 RID: 16
		// (add) Token: 0x0600019C RID: 412 RVA: 0x0000DFD4 File Offset: 0x0000C1D4
		// (remove) Token: 0x0600019D RID: 413 RVA: 0x0000E00C File Offset: 0x0000C20C
		public event ThirteenTextBox.ColorSchemeChangedEventHandler ColorSchemeChanged;

		// Token: 0x1700008A RID: 138
		// (get) Token: 0x0600019E RID: 414 RVA: 0x0000E041 File Offset: 0x0000C241
		// (set) Token: 0x0600019F RID: 415 RVA: 0x0000E04C File Offset: 0x0000C24C
		public ThirteenTextBox.ColorSchemes ColorScheme
		{
			get
			{
				return this._ColorScheme;
			}
			set
			{
				this._ColorScheme = value;
				ThirteenTextBox.ColorSchemeChangedEventHandler colorSchemeChangedEvent = this.ColorSchemeChangedEvent;
				if (colorSchemeChangedEvent != null)
				{
					colorSchemeChangedEvent();
				}
			}
		}

		// Token: 0x060001A0 RID: 416 RVA: 0x0000E070 File Offset: 0x0000C270
		public ThirteenTextBox()
		{
			this.ColorSchemeChanged += this.OnColorSchemeChanged;
			base.BorderStyle = BorderStyle.FixedSingle;
			this.Font = new Font("Segoe UI Semilight", 9.75f);
			this.BackColor = Color.FromArgb(35, 35, 35);
			this.ForeColor = Color.White;
			this.ColorScheme = ThirteenTextBox.ColorSchemes.Dark;
		}

		// Token: 0x060001A1 RID: 417 RVA: 0x0000E0D4 File Offset: 0x0000C2D4
		protected void OnColorSchemeChanged()
		{
			base.Invalidate();
			ThirteenTextBox.ColorSchemes colorScheme = this.ColorScheme;
			if (colorScheme != ThirteenTextBox.ColorSchemes.Light)
			{
				if (colorScheme == ThirteenTextBox.ColorSchemes.Dark)
				{
					this.BackColor = Color.FromArgb(35, 35, 35);
					this.ForeColor = Color.White;
					return;
				}
			}
			else
			{
				this.BackColor = Color.White;
				this.ForeColor = Color.Black;
			}
		}

		// Token: 0x040000D9 RID: 217
		private ThirteenTextBox.ColorSchemes _ColorScheme;

		// Token: 0x0200004E RID: 78
		public enum ColorSchemes
		{
			// Token: 0x040001C1 RID: 449
			Light,
			// Token: 0x040001C2 RID: 450
			Dark
		}

		// Token: 0x0200004F RID: 79
		// (Invoke) Token: 0x06000383 RID: 899
		public delegate void ColorSchemeChangedEventHandler();
	}
}

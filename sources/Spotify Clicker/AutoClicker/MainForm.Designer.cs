namespace AutoClicker
{
	// Token: 0x0200000B RID: 11
	public partial class MainForm : global::System.Windows.Forms.Form
	{
		// Token: 0x06000086 RID: 134 RVA: 0x00004E60 File Offset: 0x00003060
		protected override void Dispose(bool disposing)
		{
			if (disposing && this.components != null)
			{
				this.components.Dispose();
			}
			base.Dispose(disposing);
		}

		// Token: 0x06000087 RID: 135 RVA: 0x00004E80 File Offset: 0x00003080
		private void InitializeComponent()
		{
			this.components = new global::System.ComponentModel.Container();
			global::BunifuAnimatorNS.Animation animation = new global::BunifuAnimatorNS.Animation();
			global::System.ComponentModel.ComponentResourceManager componentResourceManager = new global::System.ComponentModel.ComponentResourceManager(typeof(global::AutoClicker.MainForm));
			this.btnToggle = new global::System.Windows.Forms.Button();
			this.label12 = new global::System.Windows.Forms.Label();
			this.label9 = new global::System.Windows.Forms.Label();
			this.rdbDelayFixed = new global::System.Windows.Forms.RadioButton();
			this.grpCount = new global::System.Windows.Forms.GroupBox();
			this.label1 = new global::System.Windows.Forms.Label();
			this.numCount = new global::System.Windows.Forms.NumericUpDown();
			this.rdbCount = new global::System.Windows.Forms.RadioButton();
			this.rdbUntilStopped = new global::System.Windows.Forms.RadioButton();
			this.statusStrip1 = new global::System.Windows.Forms.StatusStrip();
			this.tslStatus = new global::System.Windows.Forms.ToolStripStatusLabel();
			this.label13 = new global::System.Windows.Forms.Label();
			this.panel2 = new global::System.Windows.Forms.Panel();
			this.label15 = new global::System.Windows.Forms.Label();
			this.label14 = new global::System.Windows.Forms.Label();
			this.LOGO = new global::Bunifu.Framework.UI.BunifuImageButton();
			this.HWID = new global::Bunifu.Framework.UI.BunifuFlatButton();
			this.panel1 = new global::System.Windows.Forms.Panel();
			this.label23 = new global::System.Windows.Forms.Label();
			this.panel5 = new global::System.Windows.Forms.Panel();
			this.label22 = new global::System.Windows.Forms.Label();
			this.bunifuRange1 = new global::Bunifu.Framework.UI.BunifuRange();
			this.label18 = new global::System.Windows.Forms.Label();
			this.label20 = new global::System.Windows.Forms.Label();
			this.panel3 = new global::System.Windows.Forms.Panel();
			this.label27 = new global::System.Windows.Forms.Label();
			this.radioButton2 = new global::System.Windows.Forms.RadioButton();
			this.radioButton1 = new global::System.Windows.Forms.RadioButton();
			this.txtHotkey = new global::Bunifu.Framework.UI.BunifuMetroTextbox();
			this.btnHotkeyRemove = new global::Bunifu.Framework.UI.BunifuFlatButton();
			this.panel4 = new global::System.Windows.Forms.Panel();
			this.doubleclick = new global::Bunifu.Framework.UI.BunifuiOSSwitch();
			this.label17 = new global::System.Windows.Forms.Label();
			this.leftlbl = new global::System.Windows.Forms.Label();
			this.label16 = new global::System.Windows.Forms.Label();
			this.left = new global::Bunifu.Framework.UI.BunifuiOSSwitch();
			this.label19 = new global::System.Windows.Forms.Label();
			this.bunifuElipse1 = new global::Bunifu.Framework.UI.BunifuElipse(this.components);
			this.rdbClickSingleMiddle = new global::System.Windows.Forms.RadioButton();
			this.bunifuTransition1 = new global::BunifuAnimatorNS.BunifuTransition(this.components);
			this.rdbDelayRange = new global::System.Windows.Forms.RadioButton();
			this.label8 = new global::System.Windows.Forms.Label();
			this.label10 = new global::System.Windows.Forms.Label();
			this.grpLocation = new global::System.Windows.Forms.GroupBox();
			this.btnSelect = new global::System.Windows.Forms.Button();
			this.label6 = new global::System.Windows.Forms.Label();
			this.numRandomHeight = new global::System.Windows.Forms.NumericUpDown();
			this.label7 = new global::System.Windows.Forms.Label();
			this.numRandomWidth = new global::System.Windows.Forms.NumericUpDown();
			this.label4 = new global::System.Windows.Forms.Label();
			this.numRandomY = new global::System.Windows.Forms.NumericUpDown();
			this.label5 = new global::System.Windows.Forms.Label();
			this.numRandomX = new global::System.Windows.Forms.NumericUpDown();
			this.label3 = new global::System.Windows.Forms.Label();
			this.numFixedY = new global::System.Windows.Forms.NumericUpDown();
			this.label2 = new global::System.Windows.Forms.Label();
			this.numFixedX = new global::System.Windows.Forms.NumericUpDown();
			this.rdbLocationRandomArea = new global::System.Windows.Forms.RadioButton();
			this.rdbLocationFixed = new global::System.Windows.Forms.RadioButton();
			this.rdbLocationRandom = new global::System.Windows.Forms.RadioButton();
			this.rdbLocationMouse = new global::System.Windows.Forms.RadioButton();
			this.grpMain = new global::System.Windows.Forms.GroupBox();
			this.grpClickType = new global::System.Windows.Forms.GroupBox();
			this.grpDelay = new global::System.Windows.Forms.GroupBox();
			this.panel6 = new global::System.Windows.Forms.Panel();
			this.bunifuImageButton1 = new global::Bunifu.Framework.UI.BunifuFlatButton();
			this.flatClose2 = new global::FlatUI.FlatClose();
			this.label21 = new global::System.Windows.Forms.Label();
			this.pictureBox1 = new global::System.Windows.Forms.PictureBox();
			this.numDelayFixed = new global::System.Windows.Forms.NumericUpDown();
			this.bunifuiOSSwitch1 = new global::Bunifu.Framework.UI.BunifuiOSSwitch();
			this.label11 = new global::System.Windows.Forms.Label();
			this.panel8 = new global::System.Windows.Forms.Panel();
			this.radioButton3 = new global::System.Windows.Forms.RadioButton();
			this.radioButton4 = new global::System.Windows.Forms.RadioButton();
			this.bunifuMetroTextbox2 = new global::Bunifu.Framework.UI.BunifuMetroTextbox();
			this.panel7 = new global::System.Windows.Forms.Panel();
			this.label24 = new global::System.Windows.Forms.Label();
			this.bunifuRange2 = new global::Bunifu.Framework.UI.BunifuRange();
			this.label25 = new global::System.Windows.Forms.Label();
			this.label26 = new global::System.Windows.Forms.Label();
			this.bunifuDragControl1 = new global::Bunifu.Framework.UI.BunifuDragControl(this.components);
			this.bunifuElipse2 = new global::Bunifu.Framework.UI.BunifuElipse(this.components);
			this.bunifuElipse3 = new global::Bunifu.Framework.UI.BunifuElipse(this.components);
			this.bunifuElipse4 = new global::Bunifu.Framework.UI.BunifuElipse(this.components);
			this.bunifuElipse5 = new global::Bunifu.Framework.UI.BunifuElipse(this.components);
			this.bunifuFormFadeTransition1 = new global::Bunifu.Framework.UI.BunifuFormFadeTransition(this.components);
			this.bunifuElipse6 = new global::Bunifu.Framework.UI.BunifuElipse(this.components);
			this.bunifuElipse7 = new global::Bunifu.Framework.UI.BunifuElipse(this.components);
			this.bunifuElipse8 = new global::Bunifu.Framework.UI.BunifuElipse(this.components);
			this.grpCount.SuspendLayout();
			((global::System.ComponentModel.ISupportInitialize)this.numCount).BeginInit();
			this.statusStrip1.SuspendLayout();
			this.panel2.SuspendLayout();
			((global::System.ComponentModel.ISupportInitialize)this.LOGO).BeginInit();
			this.panel1.SuspendLayout();
			this.panel5.SuspendLayout();
			this.panel3.SuspendLayout();
			this.panel4.SuspendLayout();
			this.grpLocation.SuspendLayout();
			((global::System.ComponentModel.ISupportInitialize)this.numRandomHeight).BeginInit();
			((global::System.ComponentModel.ISupportInitialize)this.numRandomWidth).BeginInit();
			((global::System.ComponentModel.ISupportInitialize)this.numRandomY).BeginInit();
			((global::System.ComponentModel.ISupportInitialize)this.numRandomX).BeginInit();
			((global::System.ComponentModel.ISupportInitialize)this.numFixedY).BeginInit();
			((global::System.ComponentModel.ISupportInitialize)this.numFixedX).BeginInit();
			this.grpMain.SuspendLayout();
			this.grpDelay.SuspendLayout();
			this.panel6.SuspendLayout();
			((global::System.ComponentModel.ISupportInitialize)this.pictureBox1).BeginInit();
			((global::System.ComponentModel.ISupportInitialize)this.numDelayFixed).BeginInit();
			this.panel8.SuspendLayout();
			this.panel7.SuspendLayout();
			base.SuspendLayout();
			this.bunifuTransition1.SetDecoration(this.btnToggle, global::BunifuAnimatorNS.DecorationType.None);
			this.btnToggle.Font = new global::System.Drawing.Font("Microsoft Sans Serif", 8.25f, global::System.Drawing.FontStyle.Bold, global::System.Drawing.GraphicsUnit.Point, 0);
			this.btnToggle.Location = new global::System.Drawing.Point(229, 440);
			this.btnToggle.Name = "btnToggle";
			this.btnToggle.Size = new global::System.Drawing.Size(75, 28);
			this.btnToggle.TabIndex = 3;
			this.btnToggle.Text = "Start";
			this.btnToggle.UseVisualStyleBackColor = true;
			this.btnToggle.Visible = false;
			this.btnToggle.Click += new global::System.EventHandler(this.btnToggle_Click);
			this.label12.AutoSize = true;
			this.bunifuTransition1.SetDecoration(this.label12, global::BunifuAnimatorNS.DecorationType.None);
			this.label12.Font = new global::System.Drawing.Font("Microsoft Sans Serif", 12f, global::System.Drawing.FontStyle.Bold, global::System.Drawing.GraphicsUnit.Point, 0);
			this.label12.ForeColor = global::System.Drawing.Color.Black;
			this.label12.Location = new global::System.Drawing.Point(266, 467);
			this.label12.Name = "label12";
			this.label12.Size = new global::System.Drawing.Size(135, 20);
			this.label12.TabIndex = 14;
			this.label12.Text = "WARNING BAN";
			this.label9.AutoSize = true;
			this.bunifuTransition1.SetDecoration(this.label9, global::BunifuAnimatorNS.DecorationType.None);
			this.label9.Font = new global::System.Drawing.Font("Microsoft Sans Serif", 8.25f, global::System.Drawing.FontStyle.Bold, global::System.Drawing.GraphicsUnit.Point, 0);
			this.label9.Location = new global::System.Drawing.Point(238, 472);
			this.label9.Name = "label9";
			this.label9.Size = new global::System.Drawing.Size(22, 13);
			this.label9.TabIndex = 12;
			this.label9.Text = "ms";
			this.rdbDelayFixed.AutoSize = true;
			this.bunifuTransition1.SetDecoration(this.rdbDelayFixed, global::BunifuAnimatorNS.DecorationType.None);
			this.rdbDelayFixed.Font = new global::System.Drawing.Font("Microsoft Sans Serif", 8.25f, global::System.Drawing.FontStyle.Bold, global::System.Drawing.GraphicsUnit.Point, 0);
			this.rdbDelayFixed.Location = new global::System.Drawing.Point(22, 470);
			this.rdbDelayFixed.Name = "rdbDelayFixed";
			this.rdbDelayFixed.Size = new global::System.Drawing.Size(89, 17);
			this.rdbDelayFixed.TabIndex = 0;
			this.rdbDelayFixed.Text = "Fixed delay";
			this.rdbDelayFixed.UseVisualStyleBackColor = true;
			this.rdbDelayFixed.CheckedChanged += new global::System.EventHandler(this.DelayHandler);
			this.grpCount.Controls.Add(this.label1);
			this.grpCount.Controls.Add(this.numCount);
			this.grpCount.Controls.Add(this.rdbCount);
			this.grpCount.Controls.Add(this.rdbUntilStopped);
			this.bunifuTransition1.SetDecoration(this.grpCount, global::BunifuAnimatorNS.DecorationType.None);
			this.grpCount.Location = new global::System.Drawing.Point(47, 472);
			this.grpCount.Name = "grpCount";
			this.grpCount.Size = new global::System.Drawing.Size(341, 75);
			this.grpCount.TabIndex = 1;
			this.grpCount.TabStop = false;
			this.grpCount.Text = "Count";
			this.grpCount.Visible = false;
			this.label1.AutoSize = true;
			this.bunifuTransition1.SetDecoration(this.label1, global::BunifuAnimatorNS.DecorationType.None);
			this.label1.Location = new global::System.Drawing.Point(230, 47);
			this.label1.Name = "label1";
			this.label1.Size = new global::System.Drawing.Size(34, 13);
			this.label1.TabIndex = 3;
			this.label1.Text = "clicks";
			this.bunifuTransition1.SetDecoration(this.numCount, global::BunifuAnimatorNS.DecorationType.None);
			this.numCount.Location = new global::System.Drawing.Point(104, 44);
			global::System.Windows.Forms.NumericUpDown numericUpDown = this.numCount;
			int[] array = new int[4];
			array[0] = 1000000;
			numericUpDown.Maximum = new decimal(array);
			this.numCount.Name = "numCount";
			this.numCount.Size = new global::System.Drawing.Size(120, 20);
			this.numCount.TabIndex = 2;
			global::System.Windows.Forms.NumericUpDown numericUpDown2 = this.numCount;
			int[] array2 = new int[4];
			array2[0] = 100;
			numericUpDown2.Value = new decimal(array2);
			this.numCount.ValueChanged += new global::System.EventHandler(this.CountHandler);
			this.rdbCount.AutoSize = true;
			this.bunifuTransition1.SetDecoration(this.rdbCount, global::BunifuAnimatorNS.DecorationType.None);
			this.rdbCount.Location = new global::System.Drawing.Point(6, 44);
			this.rdbCount.Name = "rdbCount";
			this.rdbCount.Size = new global::System.Drawing.Size(88, 17);
			this.rdbCount.TabIndex = 1;
			this.rdbCount.Text = "Fixed number";
			this.rdbCount.UseVisualStyleBackColor = true;
			this.rdbCount.CheckedChanged += new global::System.EventHandler(this.CountHandler);
			this.rdbUntilStopped.AutoSize = true;
			this.rdbUntilStopped.Checked = true;
			this.bunifuTransition1.SetDecoration(this.rdbUntilStopped, global::BunifuAnimatorNS.DecorationType.None);
			this.rdbUntilStopped.Location = new global::System.Drawing.Point(6, 21);
			this.rdbUntilStopped.Name = "rdbUntilStopped";
			this.rdbUntilStopped.Size = new global::System.Drawing.Size(87, 17);
			this.rdbUntilStopped.TabIndex = 0;
			this.rdbUntilStopped.TabStop = true;
			this.rdbUntilStopped.Text = "Until stopped";
			this.rdbUntilStopped.UseVisualStyleBackColor = true;
			this.rdbUntilStopped.CheckedChanged += new global::System.EventHandler(this.CountHandler);
			this.bunifuTransition1.SetDecoration(this.statusStrip1, global::BunifuAnimatorNS.DecorationType.None);
			this.statusStrip1.Dock = global::System.Windows.Forms.DockStyle.None;
			this.statusStrip1.Items.AddRange(new global::System.Windows.Forms.ToolStripItem[]
			{
				this.tslStatus
			});
			this.statusStrip1.Location = new global::System.Drawing.Point(0, 424);
			this.statusStrip1.Name = "statusStrip1";
			this.statusStrip1.Size = new global::System.Drawing.Size(297, 22);
			this.statusStrip1.TabIndex = 1;
			this.statusStrip1.Text = "statusStrip1";
			this.statusStrip1.Visible = false;
			this.tslStatus.Name = "tslStatus";
			this.tslStatus.Size = new global::System.Drawing.Size(280, 17);
			this.tslStatus.Text = "Not currently doing much helpful here to be honest";
			this.label13.AutoSize = true;
			this.label13.BackColor = global::System.Drawing.Color.Transparent;
			this.bunifuTransition1.SetDecoration(this.label13, global::BunifuAnimatorNS.DecorationType.None);
			this.label13.Font = new global::System.Drawing.Font("Segoe UI Semilight", 8.25f, global::System.Drawing.FontStyle.Regular, global::System.Drawing.GraphicsUnit.Point, 0);
			this.label13.ForeColor = global::System.Drawing.Color.White;
			this.label13.Location = new global::System.Drawing.Point(23, 402);
			this.label13.Name = "label13";
			this.label13.Size = new global::System.Drawing.Size(188, 13);
			this.label13.TabIndex = 3;
			this.label13.Text = "MADE with LOVE by EME,ywc,Reinharl";
			this.label13.Visible = false;
			this.panel2.BackColor = global::System.Drawing.Color.FromArgb(40, 44, 52);
			this.panel2.Controls.Add(this.label15);
			this.panel2.Controls.Add(this.label14);
			this.panel2.Controls.Add(this.LOGO);
			this.panel2.Controls.Add(this.HWID);
			this.bunifuTransition1.SetDecoration(this.panel2, global::BunifuAnimatorNS.DecorationType.None);
			this.panel2.Location = new global::System.Drawing.Point(12, 48);
			this.panel2.Name = "panel2";
			this.panel2.Size = new global::System.Drawing.Size(350, 402);
			this.panel2.TabIndex = 16;
			this.label15.AutoSize = true;
			this.label15.BackColor = global::System.Drawing.Color.Transparent;
			this.label15.Cursor = global::System.Windows.Forms.Cursors.Hand;
			this.bunifuTransition1.SetDecoration(this.label15, global::BunifuAnimatorNS.DecorationType.None);
			this.label15.Font = new global::System.Drawing.Font("Segoe UI Semilight", 11.25f, global::System.Drawing.FontStyle.Regular, global::System.Drawing.GraphicsUnit.Point, 0);
			this.label15.ForeColor = global::System.Drawing.Color.White;
			this.label15.Location = new global::System.Drawing.Point(265, 359);
			this.label15.Name = "label15";
			this.label15.Size = new global::System.Drawing.Size(62, 20);
			this.label15.TabIndex = 4;
			this.label15.Text = "Support";
			this.label15.Click += new global::System.EventHandler(this.Label15_Click);
			this.label14.AutoSize = true;
			this.label14.BackColor = global::System.Drawing.Color.Transparent;
			this.label14.Cursor = global::System.Windows.Forms.Cursors.Hand;
			this.bunifuTransition1.SetDecoration(this.label14, global::BunifuAnimatorNS.DecorationType.None);
			this.label14.Font = new global::System.Drawing.Font("Segoe UI Semilight", 11.25f, global::System.Drawing.FontStyle.Regular, global::System.Drawing.GraphicsUnit.Point, 0);
			this.label14.ForeColor = global::System.Drawing.Color.White;
			this.label14.Location = new global::System.Drawing.Point(12, 359);
			this.label14.Name = "label14";
			this.label14.Size = new global::System.Drawing.Size(125, 20);
			this.label14.TabIndex = 3;
			this.label14.Text = "Telegram Channel";
			this.label14.Click += new global::System.EventHandler(this.Label14_Click);
			this.LOGO.BackColor = global::System.Drawing.Color.FromArgb(40, 44, 52);
			this.LOGO.BackgroundImageLayout = global::System.Windows.Forms.ImageLayout.Zoom;
			this.bunifuTransition1.SetDecoration(this.LOGO, global::BunifuAnimatorNS.DecorationType.None);
			this.LOGO.ErrorImage = null;
			this.LOGO.Image = global::AutoClicker.Properties.Resources.Webp1;
			this.LOGO.ImageActive = null;
			this.LOGO.InitialImage = null;
			this.LOGO.Location = new global::System.Drawing.Point(24, 38);
			this.LOGO.Name = "LOGO";
			this.LOGO.Size = new global::System.Drawing.Size(291, 104);
			this.LOGO.SizeMode = global::System.Windows.Forms.PictureBoxSizeMode.Zoom;
			this.LOGO.TabIndex = 2;
			this.LOGO.TabStop = false;
			this.LOGO.Zoom = 0;
			this.HWID.Activecolor = global::System.Drawing.Color.Firebrick;
			this.HWID.Anchor = global::System.Windows.Forms.AnchorStyles.None;
			this.HWID.BackColor = global::System.Drawing.Color.Firebrick;
			this.HWID.BackgroundImageLayout = global::System.Windows.Forms.ImageLayout.Stretch;
			this.HWID.BorderRadius = 7;
			this.HWID.ButtonText = "HWID";
			this.HWID.Cursor = global::System.Windows.Forms.Cursors.Hand;
			this.bunifuTransition1.SetDecoration(this.HWID, global::BunifuAnimatorNS.DecorationType.None);
			this.HWID.DisabledColor = global::System.Drawing.Color.Gray;
			this.HWID.Iconcolor = global::System.Drawing.Color.Transparent;
			this.HWID.Iconimage = (global::System.Drawing.Image)componentResourceManager.GetObject("HWID.Iconimage");
			this.HWID.Iconimage_right = null;
			this.HWID.Iconimage_right_Selected = null;
			this.HWID.Iconimage_Selected = null;
			this.HWID.IconMarginLeft = 0;
			this.HWID.IconMarginRight = 0;
			this.HWID.IconRightVisible = true;
			this.HWID.IconRightZoom = 0.0;
			this.HWID.IconVisible = false;
			this.HWID.IconZoom = 90.0;
			this.HWID.IsTab = false;
			this.HWID.Location = new global::System.Drawing.Point(55, 177);
			this.HWID.Name = "HWID";
			this.HWID.Normalcolor = global::System.Drawing.Color.Firebrick;
			this.HWID.OnHovercolor = global::System.Drawing.Color.FromArgb(161, 34, 34);
			this.HWID.OnHoverTextColor = global::System.Drawing.Color.White;
			this.HWID.selected = false;
			this.HWID.Size = new global::System.Drawing.Size(241, 48);
			this.HWID.TabIndex = 1;
			this.HWID.Text = "HWID";
			this.HWID.TextAlign = global::System.Drawing.ContentAlignment.MiddleCenter;
			this.HWID.Textcolor = global::System.Drawing.Color.White;
			this.HWID.TextFont = new global::System.Drawing.Font("Segoe UI", 15.75f, global::System.Drawing.FontStyle.Regular, global::System.Drawing.GraphicsUnit.Point, 0);
			this.HWID.Click += new global::System.EventHandler(this.HWID_Click);
			this.panel1.BackColor = global::System.Drawing.Color.FromArgb(40, 44, 52);
			this.panel1.Controls.Add(this.label23);
			this.panel1.Controls.Add(this.label13);
			this.panel1.Controls.Add(this.panel5);
			this.panel1.Controls.Add(this.btnToggle);
			this.panel1.Controls.Add(this.panel3);
			this.panel1.Controls.Add(this.panel4);
			this.bunifuTransition1.SetDecoration(this.panel1, global::BunifuAnimatorNS.DecorationType.None);
			this.panel1.Location = new global::System.Drawing.Point(12, 25);
			this.panel1.Name = "panel1";
			this.panel1.Size = new global::System.Drawing.Size(350, 428);
			this.panel1.TabIndex = 15;
			this.panel1.Visible = false;
			this.label23.AutoSize = true;
			this.bunifuTransition1.SetDecoration(this.label23, global::BunifuAnimatorNS.DecorationType.None);
			this.label23.Font = new global::System.Drawing.Font("Segoe UI Semilight", 9.75f, global::System.Drawing.FontStyle.Regular, global::System.Drawing.GraphicsUnit.Point, 0);
			this.label23.ForeColor = global::System.Drawing.Color.WhiteSmoke;
			this.label23.Location = new global::System.Drawing.Point(291, 399);
			this.label23.Name = "label23";
			this.label23.Size = new global::System.Drawing.Size(44, 17);
			this.label23.TabIndex = 8;
			this.label23.Text = "CPS: 0";
			this.label23.Visible = false;
			this.panel5.BackColor = global::System.Drawing.Color.FromArgb(45, 49, 57);
			this.panel5.Controls.Add(this.label22);
			this.panel5.Controls.Add(this.bunifuRange1);
			this.panel5.Controls.Add(this.label18);
			this.panel5.Controls.Add(this.label20);
			this.bunifuTransition1.SetDecoration(this.panel5, global::BunifuAnimatorNS.DecorationType.None);
			this.panel5.Location = new global::System.Drawing.Point(21, 274);
			this.panel5.Name = "panel5";
			this.panel5.Size = new global::System.Drawing.Size(311, 105);
			this.panel5.TabIndex = 7;
			this.label22.AutoSize = true;
			this.bunifuTransition1.SetDecoration(this.label22, global::BunifuAnimatorNS.DecorationType.None);
			this.label22.Font = new global::System.Drawing.Font("Segoe UI Semilight", 9.75f, global::System.Drawing.FontStyle.Regular, global::System.Drawing.GraphicsUnit.Point, 0);
			this.label22.ForeColor = global::System.Drawing.Color.WhiteSmoke;
			this.label22.Location = new global::System.Drawing.Point(23, 75);
			this.label22.Name = "label22";
			this.label22.Size = new global::System.Drawing.Size(74, 17);
			this.label22.TabIndex = 9;
			this.label22.Text = "Min CPS: 12";
			this.label22.Visible = false;
			this.bunifuRange1.BackColor = global::System.Drawing.Color.Transparent;
			this.bunifuRange1.BackgroudColor = global::System.Drawing.Color.DarkGray;
			this.bunifuRange1.BorderRadius = 2;
			this.bunifuTransition1.SetDecoration(this.bunifuRange1, global::BunifuAnimatorNS.DecorationType.None);
			this.bunifuRange1.IndicatorColor = global::System.Drawing.Color.Firebrick;
			this.bunifuRange1.Location = new global::System.Drawing.Point(20, 43);
			this.bunifuRange1.MaximumRange = 26;
			this.bunifuRange1.Name = "bunifuRange1";
			this.bunifuRange1.RangeMax = 15;
			this.bunifuRange1.RangeMin = 11;
			this.bunifuRange1.Size = new global::System.Drawing.Size(270, 30);
			this.bunifuRange1.TabIndex = 8;
			this.bunifuRange1.RangeChanged += new global::System.EventHandler(this.BunifuRange1_RangeChanged);
			this.label18.AutoSize = true;
			this.bunifuTransition1.SetDecoration(this.label18, global::BunifuAnimatorNS.DecorationType.None);
			this.label18.Font = new global::System.Drawing.Font("Segoe UI Semilight", 9.75f, global::System.Drawing.FontStyle.Regular, global::System.Drawing.GraphicsUnit.Point, 0);
			this.label18.ForeColor = global::System.Drawing.Color.WhiteSmoke;
			this.label18.Location = new global::System.Drawing.Point(226, 75);
			this.label18.Name = "label18";
			this.label18.Size = new global::System.Drawing.Size(77, 17);
			this.label18.TabIndex = 8;
			this.label18.Text = "Max CPS: 15";
			this.label18.Visible = false;
			this.label20.AutoSize = true;
			this.bunifuTransition1.SetDecoration(this.label20, global::BunifuAnimatorNS.DecorationType.None);
			this.label20.Font = new global::System.Drawing.Font("Segoe UI", 14.25f, global::System.Drawing.FontStyle.Regular, global::System.Drawing.GraphicsUnit.Point, 0);
			this.label20.ForeColor = global::System.Drawing.Color.WhiteSmoke;
			this.label20.Location = new global::System.Drawing.Point(12, 10);
			this.label20.Name = "label20";
			this.label20.Size = new global::System.Drawing.Size(168, 25);
			this.label20.TabIndex = 4;
			this.label20.Text = "CPS - Delay Range";
			this.label20.Visible = false;
			this.panel3.BackColor = global::System.Drawing.Color.FromArgb(45, 49, 57);
			this.panel3.Controls.Add(this.label27);
			this.panel3.Controls.Add(this.radioButton2);
			this.panel3.Controls.Add(this.radioButton1);
			this.panel3.Controls.Add(this.txtHotkey);
			this.panel3.Controls.Add(this.btnHotkeyRemove);
			this.bunifuTransition1.SetDecoration(this.panel3, global::BunifuAnimatorNS.DecorationType.None);
			this.panel3.Location = new global::System.Drawing.Point(21, 23);
			this.panel3.Name = "panel3";
			this.panel3.Size = new global::System.Drawing.Size(311, 134);
			this.panel3.TabIndex = 5;
			this.label27.AutoSize = true;
			this.bunifuTransition1.SetDecoration(this.label27, global::BunifuAnimatorNS.DecorationType.None);
			this.label27.Font = new global::System.Drawing.Font("Segoe UI", 14.25f, global::System.Drawing.FontStyle.Regular, global::System.Drawing.GraphicsUnit.Point, 0);
			this.label27.ForeColor = global::System.Drawing.Color.WhiteSmoke;
			this.label27.Location = new global::System.Drawing.Point(12, 15);
			this.label27.Name = "label27";
			this.label27.Size = new global::System.Drawing.Size(70, 25);
			this.label27.TabIndex = 9;
			this.label27.Text = "Hotkey";
			this.label27.Visible = false;
			this.radioButton2.AutoSize = true;
			this.radioButton2.Checked = true;
			this.bunifuTransition1.SetDecoration(this.radioButton2, global::BunifuAnimatorNS.DecorationType.None);
			this.radioButton2.FlatStyle = global::System.Windows.Forms.FlatStyle.Flat;
			this.radioButton2.Font = new global::System.Drawing.Font("Segoe UI", 14.25f);
			this.radioButton2.ForeColor = global::System.Drawing.Color.White;
			this.radioButton2.Location = new global::System.Drawing.Point(10, 13);
			this.radioButton2.Name = "radioButton2";
			this.radioButton2.Size = new global::System.Drawing.Size(86, 29);
			this.radioButton2.TabIndex = 8;
			this.radioButton2.TabStop = true;
			this.radioButton2.Text = "Clicker";
			this.radioButton2.UseVisualStyleBackColor = true;
			this.radioButton2.Visible = false;
			this.radioButton1.AutoSize = true;
			this.bunifuTransition1.SetDecoration(this.radioButton1, global::BunifuAnimatorNS.DecorationType.None);
			this.radioButton1.FlatStyle = global::System.Windows.Forms.FlatStyle.Flat;
			this.radioButton1.Font = new global::System.Drawing.Font("Segoe UI", 14.25f);
			this.radioButton1.ForeColor = global::System.Drawing.Color.White;
			this.radioButton1.Location = new global::System.Drawing.Point(110, 13);
			this.radioButton1.Name = "radioButton1";
			this.radioButton1.Size = new global::System.Drawing.Size(74, 29);
			this.radioButton1.TabIndex = 7;
			this.radioButton1.Text = "Wtap";
			this.radioButton1.UseVisualStyleBackColor = true;
			this.radioButton1.Visible = false;
			this.radioButton1.CheckedChanged += new global::System.EventHandler(this.RadioButton1_CheckedChanged);
			this.txtHotkey.BorderColorFocused = global::System.Drawing.Color.FromArgb(161, 34, 34);
			this.txtHotkey.BorderColorIdle = global::System.Drawing.Color.Firebrick;
			this.txtHotkey.BorderColorMouseHover = global::System.Drawing.Color.FromArgb(161, 34, 34);
			this.txtHotkey.BorderThickness = 2;
			this.txtHotkey.Cursor = global::System.Windows.Forms.Cursors.IBeam;
			this.bunifuTransition1.SetDecoration(this.txtHotkey, global::BunifuAnimatorNS.DecorationType.None);
			this.txtHotkey.Font = new global::System.Drawing.Font("Century Gothic", 9.75f);
			this.txtHotkey.ForeColor = global::System.Drawing.Color.White;
			this.txtHotkey.isPassword = false;
			this.txtHotkey.Location = new global::System.Drawing.Point(9, 49);
			this.txtHotkey.Margin = new global::System.Windows.Forms.Padding(4);
			this.txtHotkey.Name = "txtHotkey";
			this.txtHotkey.Size = new global::System.Drawing.Size(291, 38);
			this.txtHotkey.TabIndex = 6;
			this.txtHotkey.Text = "None";
			this.txtHotkey.TextAlign = global::System.Windows.Forms.HorizontalAlignment.Center;
			this.txtHotkey.KeyDown += new global::System.Windows.Forms.KeyEventHandler(this.txtHotkey_KeyDown_1);
			this.btnHotkeyRemove.Activecolor = global::System.Drawing.Color.Firebrick;
			this.btnHotkeyRemove.Anchor = global::System.Windows.Forms.AnchorStyles.None;
			this.btnHotkeyRemove.BackColor = global::System.Drawing.Color.Firebrick;
			this.btnHotkeyRemove.BackgroundImageLayout = global::System.Windows.Forms.ImageLayout.Stretch;
			this.btnHotkeyRemove.BorderRadius = 7;
			this.btnHotkeyRemove.ButtonText = "Clear Hotkey";
			this.btnHotkeyRemove.Cursor = global::System.Windows.Forms.Cursors.Hand;
			this.bunifuTransition1.SetDecoration(this.btnHotkeyRemove, global::BunifuAnimatorNS.DecorationType.None);
			this.btnHotkeyRemove.DisabledColor = global::System.Drawing.Color.Gray;
			this.btnHotkeyRemove.Font = new global::System.Drawing.Font("Segoe UI", 8.25f, global::System.Drawing.FontStyle.Regular, global::System.Drawing.GraphicsUnit.Point, 0);
			this.btnHotkeyRemove.Iconcolor = global::System.Drawing.Color.Transparent;
			this.btnHotkeyRemove.Iconimage = (global::System.Drawing.Image)componentResourceManager.GetObject("btnHotkeyRemove.Iconimage");
			this.btnHotkeyRemove.Iconimage_right = null;
			this.btnHotkeyRemove.Iconimage_right_Selected = null;
			this.btnHotkeyRemove.Iconimage_Selected = null;
			this.btnHotkeyRemove.IconMarginLeft = 0;
			this.btnHotkeyRemove.IconMarginRight = 0;
			this.btnHotkeyRemove.IconRightVisible = true;
			this.btnHotkeyRemove.IconRightZoom = 0.0;
			this.btnHotkeyRemove.IconVisible = false;
			this.btnHotkeyRemove.IconZoom = 90.0;
			this.btnHotkeyRemove.IsTab = false;
			this.btnHotkeyRemove.Location = new global::System.Drawing.Point(10, 96);
			this.btnHotkeyRemove.Name = "btnHotkeyRemove";
			this.btnHotkeyRemove.Normalcolor = global::System.Drawing.Color.Firebrick;
			this.btnHotkeyRemove.OnHovercolor = global::System.Drawing.Color.FromArgb(161, 34, 34);
			this.btnHotkeyRemove.OnHoverTextColor = global::System.Drawing.Color.White;
			this.btnHotkeyRemove.selected = false;
			this.btnHotkeyRemove.Size = new global::System.Drawing.Size(291, 30);
			this.btnHotkeyRemove.TabIndex = 6;
			this.btnHotkeyRemove.Text = "Clear Hotkey";
			this.btnHotkeyRemove.TextAlign = global::System.Drawing.ContentAlignment.MiddleCenter;
			this.btnHotkeyRemove.Textcolor = global::System.Drawing.Color.White;
			this.btnHotkeyRemove.TextFont = new global::System.Drawing.Font("Segoe UI", 12f, global::System.Drawing.FontStyle.Regular, global::System.Drawing.GraphicsUnit.Point, 0);
			this.btnHotkeyRemove.Click += new global::System.EventHandler(this.btnHotkeyRemove_Click);
			this.panel4.BackColor = global::System.Drawing.Color.FromArgb(45, 49, 57);
			this.panel4.Controls.Add(this.doubleclick);
			this.panel4.Controls.Add(this.label17);
			this.panel4.Controls.Add(this.leftlbl);
			this.panel4.Controls.Add(this.label16);
			this.panel4.Controls.Add(this.left);
			this.bunifuTransition1.SetDecoration(this.panel4, global::BunifuAnimatorNS.DecorationType.None);
			this.panel4.Location = new global::System.Drawing.Point(21, 171);
			this.panel4.Name = "panel4";
			this.panel4.Size = new global::System.Drawing.Size(311, 82);
			this.panel4.TabIndex = 6;
			this.doubleclick.BackColor = global::System.Drawing.Color.Transparent;
			this.doubleclick.BackgroundImage = (global::System.Drawing.Image)componentResourceManager.GetObject("doubleclick.BackgroundImage");
			this.doubleclick.BackgroundImageLayout = global::System.Windows.Forms.ImageLayout.Stretch;
			this.doubleclick.Cursor = global::System.Windows.Forms.Cursors.Hand;
			this.bunifuTransition1.SetDecoration(this.doubleclick, global::BunifuAnimatorNS.DecorationType.None);
			this.doubleclick.Location = new global::System.Drawing.Point(209, 48);
			this.doubleclick.Name = "doubleclick";
			this.doubleclick.OffColor = global::System.Drawing.Color.Firebrick;
			this.doubleclick.OnColor = global::System.Drawing.Color.Lime;
			this.doubleclick.Size = new global::System.Drawing.Size(35, 20);
			this.doubleclick.TabIndex = 8;
			this.doubleclick.Value = false;
			this.doubleclick.OnValueChange += new global::System.EventHandler(this.Doubleclick_OnValueChange);
			this.label17.AutoSize = true;
			this.bunifuTransition1.SetDecoration(this.label17, global::BunifuAnimatorNS.DecorationType.None);
			this.label17.Font = new global::System.Drawing.Font("Segoe UI Semilight", 9.75f, global::System.Drawing.FontStyle.Regular, global::System.Drawing.GraphicsUnit.Point, 0);
			this.label17.ForeColor = global::System.Drawing.Color.WhiteSmoke;
			this.label17.Location = new global::System.Drawing.Point(140, 48);
			this.label17.Name = "label17";
			this.label17.Size = new global::System.Drawing.Size(48, 17);
			this.label17.TabIndex = 7;
			this.label17.Text = "Double";
			this.label17.Visible = false;
			this.leftlbl.AutoSize = true;
			this.bunifuTransition1.SetDecoration(this.leftlbl, global::BunifuAnimatorNS.DecorationType.None);
			this.leftlbl.Font = new global::System.Drawing.Font("Segoe UI Semilight", 9.75f, global::System.Drawing.FontStyle.Regular, global::System.Drawing.GraphicsUnit.Point, 0);
			this.leftlbl.ForeColor = global::System.Drawing.Color.WhiteSmoke;
			this.leftlbl.Location = new global::System.Drawing.Point(15, 48);
			this.leftlbl.Name = "leftlbl";
			this.leftlbl.Size = new global::System.Drawing.Size(59, 17);
			this.leftlbl.TabIndex = 6;
			this.leftlbl.Text = "Left Click";
			this.leftlbl.Visible = false;
			this.label16.AutoSize = true;
			this.bunifuTransition1.SetDecoration(this.label16, global::BunifuAnimatorNS.DecorationType.None);
			this.label16.Font = new global::System.Drawing.Font("Segoe UI", 14.25f, global::System.Drawing.FontStyle.Regular, global::System.Drawing.GraphicsUnit.Point, 0);
			this.label16.ForeColor = global::System.Drawing.Color.WhiteSmoke;
			this.label16.Location = new global::System.Drawing.Point(12, 10);
			this.label16.Name = "label16";
			this.label16.Size = new global::System.Drawing.Size(96, 25);
			this.label16.TabIndex = 4;
			this.label16.Text = "Click Type";
			this.label16.Visible = false;
			this.left.BackColor = global::System.Drawing.Color.Transparent;
			this.left.BackgroundImage = (global::System.Drawing.Image)componentResourceManager.GetObject("left.BackgroundImage");
			this.left.BackgroundImageLayout = global::System.Windows.Forms.ImageLayout.Stretch;
			this.left.Cursor = global::System.Windows.Forms.Cursors.Hand;
			this.bunifuTransition1.SetDecoration(this.left, global::BunifuAnimatorNS.DecorationType.None);
			this.left.Location = new global::System.Drawing.Point(84, 48);
			this.left.Name = "left";
			this.left.OffColor = global::System.Drawing.Color.Gray;
			this.left.OnColor = global::System.Drawing.Color.Firebrick;
			this.left.Size = new global::System.Drawing.Size(35, 20);
			this.left.TabIndex = 5;
			this.left.Value = true;
			this.left.OnValueChange += new global::System.EventHandler(this.Left_OnValueChange);
			this.label19.AutoSize = true;
			this.bunifuTransition1.SetDecoration(this.label19, global::BunifuAnimatorNS.DecorationType.None);
			this.label19.Font = new global::System.Drawing.Font("Segoe UI Semilight", 9.75f, global::System.Drawing.FontStyle.Regular, global::System.Drawing.GraphicsUnit.Point, 0);
			this.label19.ForeColor = global::System.Drawing.Color.WhiteSmoke;
			this.label19.Location = new global::System.Drawing.Point(1212, 439);
			this.label19.Name = "label19";
			this.label19.Size = new global::System.Drawing.Size(29, 17);
			this.label19.TabIndex = 9;
			this.label19.Text = "Min";
			this.label19.Visible = false;
			this.bunifuElipse1.ElipseRadius = 6;
			this.bunifuElipse1.TargetControl = this.panel3;
			this.rdbClickSingleMiddle.AutoSize = true;
			this.bunifuTransition1.SetDecoration(this.rdbClickSingleMiddle, global::BunifuAnimatorNS.DecorationType.None);
			this.rdbClickSingleMiddle.Font = new global::System.Drawing.Font("Microsoft Sans Serif", 8.25f, global::System.Drawing.FontStyle.Bold, global::System.Drawing.GraphicsUnit.Point, 0);
			this.rdbClickSingleMiddle.Location = new global::System.Drawing.Point(400, 473);
			this.rdbClickSingleMiddle.Name = "rdbClickSingleMiddle";
			this.rdbClickSingleMiddle.Size = new global::System.Drawing.Size(62, 17);
			this.rdbClickSingleMiddle.TabIndex = 1;
			this.rdbClickSingleMiddle.Text = "Middle";
			this.rdbClickSingleMiddle.UseVisualStyleBackColor = true;
			this.rdbClickSingleMiddle.Visible = false;
			this.bunifuTransition1.AnimationType = global::BunifuAnimatorNS.AnimationType.Scale;
			this.bunifuTransition1.Cursor = null;
			animation.AnimateOnlyDifferences = true;
			animation.BlindCoeff = (global::System.Drawing.PointF)componentResourceManager.GetObject("animation1.BlindCoeff");
			animation.LeafCoeff = 0f;
			animation.MaxTime = 1f;
			animation.MinTime = 0f;
			animation.MosaicCoeff = (global::System.Drawing.PointF)componentResourceManager.GetObject("animation1.MosaicCoeff");
			animation.MosaicShift = (global::System.Drawing.PointF)componentResourceManager.GetObject("animation1.MosaicShift");
			animation.MosaicSize = 0;
			animation.Padding = new global::System.Windows.Forms.Padding(0);
			animation.RotateCoeff = 0f;
			animation.RotateLimit = 0f;
			animation.ScaleCoeff = (global::System.Drawing.PointF)componentResourceManager.GetObject("animation1.ScaleCoeff");
			animation.SlideCoeff = (global::System.Drawing.PointF)componentResourceManager.GetObject("animation1.SlideCoeff");
			animation.TimeCoeff = 0f;
			animation.TransparencyCoeff = 0f;
			this.bunifuTransition1.DefaultAnimation = animation;
			this.rdbDelayRange.AutoSize = true;
			this.rdbDelayRange.Checked = true;
			this.bunifuTransition1.SetDecoration(this.rdbDelayRange, global::BunifuAnimatorNS.DecorationType.None);
			this.rdbDelayRange.Font = new global::System.Drawing.Font("Microsoft Sans Serif", 8.25f, global::System.Drawing.FontStyle.Bold, global::System.Drawing.GraphicsUnit.Point, 0);
			this.rdbDelayRange.Location = new global::System.Drawing.Point(8, 19);
			this.rdbDelayRange.Name = "rdbDelayRange";
			this.rdbDelayRange.Size = new global::System.Drawing.Size(93, 17);
			this.rdbDelayRange.TabIndex = 1;
			this.rdbDelayRange.TabStop = true;
			this.rdbDelayRange.Text = "Delay range";
			this.rdbDelayRange.UseVisualStyleBackColor = true;
			this.rdbDelayRange.Visible = false;
			this.rdbDelayRange.CheckedChanged += new global::System.EventHandler(this.DelayHandler);
			this.label8.AutoSize = true;
			this.bunifuTransition1.SetDecoration(this.label8, global::BunifuAnimatorNS.DecorationType.None);
			this.label8.Location = new global::System.Drawing.Point(184, 23);
			this.label8.Name = "label8";
			this.label8.Size = new global::System.Drawing.Size(11, 13);
			this.label8.TabIndex = 10;
			this.label8.Text = "-";
			this.label8.Visible = false;
			this.label10.AutoSize = true;
			this.bunifuTransition1.SetDecoration(this.label10, global::BunifuAnimatorNS.DecorationType.None);
			this.label10.Font = new global::System.Drawing.Font("Microsoft Sans Serif", 8.25f, global::System.Drawing.FontStyle.Bold, global::System.Drawing.GraphicsUnit.Point, 0);
			this.label10.Location = new global::System.Drawing.Point(287, 21);
			this.label10.Name = "label10";
			this.label10.Size = new global::System.Drawing.Size(22, 13);
			this.label10.TabIndex = 13;
			this.label10.Text = "ms";
			this.label10.Visible = false;
			this.grpLocation.Controls.Add(this.btnSelect);
			this.grpLocation.Controls.Add(this.label6);
			this.grpLocation.Controls.Add(this.numRandomHeight);
			this.grpLocation.Controls.Add(this.label7);
			this.grpLocation.Controls.Add(this.numRandomWidth);
			this.grpLocation.Controls.Add(this.label4);
			this.grpLocation.Controls.Add(this.numRandomY);
			this.grpLocation.Controls.Add(this.label5);
			this.grpLocation.Controls.Add(this.numRandomX);
			this.grpLocation.Controls.Add(this.label3);
			this.grpLocation.Controls.Add(this.numFixedY);
			this.grpLocation.Controls.Add(this.label2);
			this.grpLocation.Controls.Add(this.numFixedX);
			this.grpLocation.Controls.Add(this.rdbLocationRandomArea);
			this.grpLocation.Controls.Add(this.rdbLocationFixed);
			this.grpLocation.Controls.Add(this.rdbLocationRandom);
			this.grpLocation.Controls.Add(this.rdbLocationMouse);
			this.bunifuTransition1.SetDecoration(this.grpLocation, global::BunifuAnimatorNS.DecorationType.None);
			this.grpLocation.Location = new global::System.Drawing.Point(-242, 350);
			this.grpLocation.Name = "grpLocation";
			this.grpLocation.Size = new global::System.Drawing.Size(341, 291);
			this.grpLocation.TabIndex = 0;
			this.grpLocation.TabStop = false;
			this.grpLocation.Text = "Location";
			this.grpLocation.Visible = false;
			this.bunifuTransition1.SetDecoration(this.btnSelect, global::BunifuAnimatorNS.DecorationType.None);
			this.btnSelect.Location = new global::System.Drawing.Point(102, 115);
			this.btnSelect.Name = "btnSelect";
			this.btnSelect.Size = new global::System.Drawing.Size(75, 25);
			this.btnSelect.TabIndex = 16;
			this.btnSelect.Text = "Select...";
			this.btnSelect.UseVisualStyleBackColor = true;
			this.btnSelect.Click += new global::System.EventHandler(this.btnSelect_Click);
			this.label6.AutoSize = true;
			this.bunifuTransition1.SetDecoration(this.label6, global::BunifuAnimatorNS.DecorationType.None);
			this.label6.Location = new global::System.Drawing.Point(171, 171);
			this.label6.Name = "label6";
			this.label6.Size = new global::System.Drawing.Size(44, 13);
			this.label6.TabIndex = 15;
			this.label6.Text = "Height";
			this.bunifuTransition1.SetDecoration(this.numRandomHeight, global::BunifuAnimatorNS.DecorationType.None);
			this.numRandomHeight.Location = new global::System.Drawing.Point(215, 169);
			global::System.Windows.Forms.NumericUpDown numericUpDown3 = this.numRandomHeight;
			int[] array3 = new int[4];
			array3[0] = 1000000;
			numericUpDown3.Maximum = new decimal(array3);
			this.numRandomHeight.Name = "numRandomHeight";
			this.numRandomHeight.Size = new global::System.Drawing.Size(120, 20);
			this.numRandomHeight.TabIndex = 14;
			global::System.Windows.Forms.NumericUpDown numericUpDown4 = this.numRandomHeight;
			int[] array4 = new int[4];
			array4[0] = 100;
			numericUpDown4.Value = new decimal(array4);
			this.numRandomHeight.ValueChanged += new global::System.EventHandler(this.LocationHandler);
			this.label7.AutoSize = true;
			this.bunifuTransition1.SetDecoration(this.label7, global::BunifuAnimatorNS.DecorationType.None);
			this.label7.Location = new global::System.Drawing.Point(6, 171);
			this.label7.Name = "label7";
			this.label7.Size = new global::System.Drawing.Size(40, 13);
			this.label7.TabIndex = 13;
			this.label7.Text = "Width";
			this.bunifuTransition1.SetDecoration(this.numRandomWidth, global::BunifuAnimatorNS.DecorationType.None);
			this.numRandomWidth.Location = new global::System.Drawing.Point(45, 169);
			global::System.Windows.Forms.NumericUpDown numericUpDown5 = this.numRandomWidth;
			int[] array5 = new int[4];
			array5[0] = 1000000;
			numericUpDown5.Maximum = new decimal(array5);
			this.numRandomWidth.Name = "numRandomWidth";
			this.numRandomWidth.Size = new global::System.Drawing.Size(120, 20);
			this.numRandomWidth.TabIndex = 12;
			global::System.Windows.Forms.NumericUpDown numericUpDown6 = this.numRandomWidth;
			int[] array6 = new int[4];
			array6[0] = 100;
			numericUpDown6.Value = new decimal(array6);
			this.numRandomWidth.ValueChanged += new global::System.EventHandler(this.LocationHandler);
			this.label4.AutoSize = true;
			this.bunifuTransition1.SetDecoration(this.label4, global::BunifuAnimatorNS.DecorationType.None);
			this.label4.Location = new global::System.Drawing.Point(197, 144);
			this.label4.Name = "label4";
			this.label4.Size = new global::System.Drawing.Size(15, 13);
			this.label4.TabIndex = 11;
			this.label4.Text = "Y";
			this.bunifuTransition1.SetDecoration(this.numRandomY, global::BunifuAnimatorNS.DecorationType.None);
			this.numRandomY.Location = new global::System.Drawing.Point(215, 142);
			global::System.Windows.Forms.NumericUpDown numericUpDown7 = this.numRandomY;
			int[] array7 = new int[4];
			array7[0] = 1000000;
			numericUpDown7.Maximum = new decimal(array7);
			this.numRandomY.Name = "numRandomY";
			this.numRandomY.Size = new global::System.Drawing.Size(120, 20);
			this.numRandomY.TabIndex = 10;
			this.numRandomY.ValueChanged += new global::System.EventHandler(this.LocationHandler);
			this.label5.AutoSize = true;
			this.bunifuTransition1.SetDecoration(this.label5, global::BunifuAnimatorNS.DecorationType.None);
			this.label5.Location = new global::System.Drawing.Point(27, 144);
			this.label5.Name = "label5";
			this.label5.Size = new global::System.Drawing.Size(15, 13);
			this.label5.TabIndex = 9;
			this.label5.Text = "X";
			this.bunifuTransition1.SetDecoration(this.numRandomX, global::BunifuAnimatorNS.DecorationType.None);
			this.numRandomX.Location = new global::System.Drawing.Point(45, 142);
			global::System.Windows.Forms.NumericUpDown numericUpDown8 = this.numRandomX;
			int[] array8 = new int[4];
			array8[0] = 1000000;
			numericUpDown8.Maximum = new decimal(array8);
			this.numRandomX.Name = "numRandomX";
			this.numRandomX.Size = new global::System.Drawing.Size(120, 20);
			this.numRandomX.TabIndex = 8;
			this.numRandomX.ValueChanged += new global::System.EventHandler(this.LocationHandler);
			this.label3.AutoSize = true;
			this.bunifuTransition1.SetDecoration(this.label3, global::BunifuAnimatorNS.DecorationType.None);
			this.label3.Location = new global::System.Drawing.Point(197, 93);
			this.label3.Name = "label3";
			this.label3.Size = new global::System.Drawing.Size(15, 13);
			this.label3.TabIndex = 7;
			this.label3.Text = "Y";
			this.bunifuTransition1.SetDecoration(this.numFixedY, global::BunifuAnimatorNS.DecorationType.None);
			this.numFixedY.Location = new global::System.Drawing.Point(215, 91);
			global::System.Windows.Forms.NumericUpDown numericUpDown9 = this.numFixedY;
			int[] array9 = new int[4];
			array9[0] = 1000000;
			numericUpDown9.Maximum = new decimal(array9);
			this.numFixedY.Name = "numFixedY";
			this.numFixedY.Size = new global::System.Drawing.Size(120, 20);
			this.numFixedY.TabIndex = 6;
			this.numFixedY.ValueChanged += new global::System.EventHandler(this.LocationHandler);
			this.label2.AutoSize = true;
			this.bunifuTransition1.SetDecoration(this.label2, global::BunifuAnimatorNS.DecorationType.None);
			this.label2.Location = new global::System.Drawing.Point(27, 93);
			this.label2.Name = "label2";
			this.label2.Size = new global::System.Drawing.Size(15, 13);
			this.label2.TabIndex = 5;
			this.label2.Text = "X";
			this.bunifuTransition1.SetDecoration(this.numFixedX, global::BunifuAnimatorNS.DecorationType.None);
			this.numFixedX.Location = new global::System.Drawing.Point(45, 91);
			global::System.Windows.Forms.NumericUpDown numericUpDown10 = this.numFixedX;
			int[] array10 = new int[4];
			array10[0] = 1000000;
			numericUpDown10.Maximum = new decimal(array10);
			this.numFixedX.Name = "numFixedX";
			this.numFixedX.Size = new global::System.Drawing.Size(120, 20);
			this.numFixedX.TabIndex = 4;
			this.numFixedX.ValueChanged += new global::System.EventHandler(this.LocationHandler);
			this.rdbLocationRandomArea.AutoSize = true;
			this.bunifuTransition1.SetDecoration(this.rdbLocationRandomArea, global::BunifuAnimatorNS.DecorationType.None);
			this.rdbLocationRandomArea.Location = new global::System.Drawing.Point(6, 118);
			this.rdbLocationRandomArea.Name = "rdbLocationRandomArea";
			this.rdbLocationRandomArea.Size = new global::System.Drawing.Size(100, 17);
			this.rdbLocationRandomArea.TabIndex = 3;
			this.rdbLocationRandomArea.Text = "Random area";
			this.rdbLocationRandomArea.UseVisualStyleBackColor = true;
			this.rdbLocationRandomArea.CheckedChanged += new global::System.EventHandler(this.LocationHandler);
			this.rdbLocationFixed.AutoSize = true;
			this.bunifuTransition1.SetDecoration(this.rdbLocationFixed, global::BunifuAnimatorNS.DecorationType.None);
			this.rdbLocationFixed.Location = new global::System.Drawing.Point(6, 67);
			this.rdbLocationFixed.Name = "rdbLocationFixed";
			this.rdbLocationFixed.Size = new global::System.Drawing.Size(104, 17);
			this.rdbLocationFixed.TabIndex = 2;
			this.rdbLocationFixed.Text = "Fixed location";
			this.rdbLocationFixed.UseVisualStyleBackColor = true;
			this.rdbLocationFixed.CheckedChanged += new global::System.EventHandler(this.LocationHandler);
			this.rdbLocationRandom.AutoSize = true;
			this.bunifuTransition1.SetDecoration(this.rdbLocationRandom, global::BunifuAnimatorNS.DecorationType.None);
			this.rdbLocationRandom.Location = new global::System.Drawing.Point(6, 43);
			this.rdbLocationRandom.Name = "rdbLocationRandom";
			this.rdbLocationRandom.Size = new global::System.Drawing.Size(131, 17);
			this.rdbLocationRandom.TabIndex = 1;
			this.rdbLocationRandom.Text = "Random on screen";
			this.rdbLocationRandom.UseVisualStyleBackColor = true;
			this.rdbLocationRandom.CheckedChanged += new global::System.EventHandler(this.LocationHandler);
			this.rdbLocationMouse.AutoSize = true;
			this.rdbLocationMouse.Checked = true;
			this.bunifuTransition1.SetDecoration(this.rdbLocationMouse, global::BunifuAnimatorNS.DecorationType.None);
			this.rdbLocationMouse.Location = new global::System.Drawing.Point(6, 20);
			this.rdbLocationMouse.Name = "rdbLocationMouse";
			this.rdbLocationMouse.Size = new global::System.Drawing.Size(111, 17);
			this.rdbLocationMouse.TabIndex = 0;
			this.rdbLocationMouse.TabStop = true;
			this.rdbLocationMouse.Text = "Mouse location";
			this.rdbLocationMouse.UseVisualStyleBackColor = true;
			this.rdbLocationMouse.CheckedChanged += new global::System.EventHandler(this.LocationHandler);
			this.grpMain.BackColor = global::System.Drawing.Color.Transparent;
			this.grpMain.Controls.Add(this.grpClickType);
			this.grpMain.Controls.Add(this.grpDelay);
			this.grpMain.Controls.Add(this.grpLocation);
			this.bunifuTransition1.SetDecoration(this.grpMain, global::BunifuAnimatorNS.DecorationType.None);
			this.grpMain.Font = new global::System.Drawing.Font("Microsoft Sans Serif", 8.25f, global::System.Drawing.FontStyle.Bold, global::System.Drawing.GraphicsUnit.Point, 0);
			this.grpMain.Location = new global::System.Drawing.Point(679, 603);
			this.grpMain.Name = "grpMain";
			this.grpMain.Size = new global::System.Drawing.Size(347, 278);
			this.grpMain.TabIndex = 0;
			this.grpMain.TabStop = false;
			this.grpMain.Text = "Click details";
			this.grpMain.Visible = false;
			this.grpClickType.BackColor = global::System.Drawing.Color.Transparent;
			this.bunifuTransition1.SetDecoration(this.grpClickType, global::BunifuAnimatorNS.DecorationType.None);
			this.grpClickType.Font = new global::System.Drawing.Font("Microsoft Sans Serif", 8.25f, global::System.Drawing.FontStyle.Bold, global::System.Drawing.GraphicsUnit.Point, 0);
			this.grpClickType.Location = new global::System.Drawing.Point(6, 105);
			this.grpClickType.Name = "grpClickType";
			this.grpClickType.Size = new global::System.Drawing.Size(235, 112);
			this.grpClickType.TabIndex = 2;
			this.grpClickType.TabStop = false;
			this.grpClickType.Text = "Click type";
			this.grpClickType.Visible = false;
			this.grpDelay.BackColor = global::System.Drawing.Color.Transparent;
			this.grpDelay.Controls.Add(this.label10);
			this.grpDelay.Controls.Add(this.label8);
			this.grpDelay.Controls.Add(this.rdbDelayRange);
			this.bunifuTransition1.SetDecoration(this.grpDelay, global::BunifuAnimatorNS.DecorationType.None);
			this.grpDelay.Font = new global::System.Drawing.Font("Microsoft Sans Serif", 8.25f, global::System.Drawing.FontStyle.Bold, global::System.Drawing.GraphicsUnit.Point, 0);
			this.grpDelay.Location = new global::System.Drawing.Point(7, 223);
			this.grpDelay.Name = "grpDelay";
			this.grpDelay.Size = new global::System.Drawing.Size(329, 50);
			this.grpDelay.TabIndex = 1;
			this.grpDelay.TabStop = false;
			this.grpDelay.Text = "Delay";
			this.grpDelay.Visible = false;
			this.panel6.BackColor = global::System.Drawing.Color.FromArgb(45, 49, 57);
			this.panel6.Controls.Add(this.bunifuImageButton1);
			this.panel6.Controls.Add(this.flatClose2);
			this.panel6.Controls.Add(this.label21);
			this.panel6.Controls.Add(this.pictureBox1);
			this.bunifuTransition1.SetDecoration(this.panel6, global::BunifuAnimatorNS.DecorationType.None);
			this.panel6.Location = new global::System.Drawing.Point(-1, -11);
			this.panel6.Name = "panel6";
			this.panel6.Size = new global::System.Drawing.Size(428, 44);
			this.panel6.TabIndex = 7;
			this.bunifuImageButton1.Activecolor = global::System.Drawing.Color.FromArgb(161, 33, 33);
			this.bunifuImageButton1.BackColor = global::System.Drawing.Color.FromArgb(161, 33, 33);
			this.bunifuImageButton1.BackgroundImage = global::AutoClicker.Properties.Resources.Senza_nome3;
			this.bunifuImageButton1.BackgroundImageLayout = global::System.Windows.Forms.ImageLayout.Stretch;
			this.bunifuImageButton1.BorderRadius = 0;
			this.bunifuImageButton1.ButtonText = "";
			this.bunifuImageButton1.Cursor = global::System.Windows.Forms.Cursors.Hand;
			this.bunifuTransition1.SetDecoration(this.bunifuImageButton1, global::BunifuAnimatorNS.DecorationType.None);
			this.bunifuImageButton1.DisabledColor = global::System.Drawing.Color.Gray;
			this.bunifuImageButton1.Iconcolor = global::System.Drawing.Color.Transparent;
			this.bunifuImageButton1.Iconimage = null;
			this.bunifuImageButton1.Iconimage_right = null;
			this.bunifuImageButton1.Iconimage_right_Selected = null;
			this.bunifuImageButton1.Iconimage_Selected = null;
			this.bunifuImageButton1.IconMarginLeft = 0;
			this.bunifuImageButton1.IconMarginRight = 0;
			this.bunifuImageButton1.IconRightVisible = true;
			this.bunifuImageButton1.IconRightZoom = 0.0;
			this.bunifuImageButton1.IconVisible = true;
			this.bunifuImageButton1.IconZoom = 90.0;
			this.bunifuImageButton1.IsTab = false;
			this.bunifuImageButton1.Location = new global::System.Drawing.Point(321, 17);
			this.bunifuImageButton1.Name = "bunifuImageButton1";
			this.bunifuImageButton1.Normalcolor = global::System.Drawing.Color.FromArgb(161, 33, 33);
			this.bunifuImageButton1.OnHovercolor = global::System.Drawing.Color.FromArgb(178, 61, 61);
			this.bunifuImageButton1.OnHoverTextColor = global::System.Drawing.Color.White;
			this.bunifuImageButton1.selected = false;
			this.bunifuImageButton1.Size = new global::System.Drawing.Size(19, 19);
			this.bunifuImageButton1.TabIndex = 19;
			this.bunifuImageButton1.TextAlign = global::System.Drawing.ContentAlignment.MiddleLeft;
			this.bunifuImageButton1.Textcolor = global::System.Drawing.Color.White;
			this.bunifuImageButton1.TextFont = new global::System.Drawing.Font("Microsoft Sans Serif", 9.75f, global::System.Drawing.FontStyle.Regular, global::System.Drawing.GraphicsUnit.Point, 0);
			this.bunifuImageButton1.Click += new global::System.EventHandler(this.BunifuImageButton1_Click_1);
			this.flatClose2.Anchor = (global::System.Windows.Forms.AnchorStyles.Top | global::System.Windows.Forms.AnchorStyles.Right);
			this.flatClose2.BackColor = global::System.Drawing.Color.White;
			this.flatClose2.BaseColor = global::System.Drawing.Color.FromArgb(168, 35, 35);
			this.bunifuTransition1.SetDecoration(this.flatClose2, global::BunifuAnimatorNS.DecorationType.None);
			this.flatClose2.Font = new global::System.Drawing.Font("Marlett", 10f);
			this.flatClose2.Location = new global::System.Drawing.Point(345, 17);
			this.flatClose2.Name = "flatClose2";
			this.flatClose2.Size = new global::System.Drawing.Size(18, 18);
			this.flatClose2.TabIndex = 8;
			this.flatClose2.Text = "flatClose2";
			this.flatClose2.TextColor = global::System.Drawing.Color.FromArgb(243, 243, 243);
			this.flatClose2.Click += new global::System.EventHandler(this.FlatClose2_Click);
			this.label21.AutoSize = true;
			this.bunifuTransition1.SetDecoration(this.label21, global::BunifuAnimatorNS.DecorationType.None);
			this.label21.Font = new global::System.Drawing.Font("Segoe UI Black", 14.25f, global::System.Drawing.FontStyle.Bold, global::System.Drawing.GraphicsUnit.Point, 0);
			this.label21.ForeColor = global::System.Drawing.Color.WhiteSmoke;
			this.label21.Location = new global::System.Drawing.Point(167, 15);
			this.label21.Name = "label21";
			this.label21.Size = new global::System.Drawing.Size(47, 25);
			this.label21.TabIndex = 7;
			this.label21.Text = "RYE";
			this.label21.Visible = false;
			this.pictureBox1.BackgroundImage = global::AutoClicker.Properties.Resources.Webp1;
			this.pictureBox1.BackgroundImageLayout = global::System.Windows.Forms.ImageLayout.Zoom;
			this.bunifuTransition1.SetDecoration(this.pictureBox1, global::BunifuAnimatorNS.DecorationType.None);
			this.pictureBox1.Location = new global::System.Drawing.Point(3, 13);
			this.pictureBox1.Name = "pictureBox1";
			this.pictureBox1.Size = new global::System.Drawing.Size(69, 29);
			this.pictureBox1.TabIndex = 9;
			this.pictureBox1.TabStop = false;
			this.pictureBox1.Visible = false;
			this.bunifuTransition1.SetDecoration(this.numDelayFixed, global::BunifuAnimatorNS.DecorationType.None);
			this.numDelayFixed.Font = new global::System.Drawing.Font("Microsoft Sans Serif", 8.25f, global::System.Drawing.FontStyle.Bold, global::System.Drawing.GraphicsUnit.Point, 0);
			this.numDelayFixed.Location = new global::System.Drawing.Point(112, 470);
			global::System.Windows.Forms.NumericUpDown numericUpDown11 = this.numDelayFixed;
			int[] array11 = new int[4];
			array11[0] = 1000000;
			numericUpDown11.Maximum = new decimal(array11);
			this.numDelayFixed.Name = "numDelayFixed";
			this.numDelayFixed.Size = new global::System.Drawing.Size(315, 20);
			this.numDelayFixed.TabIndex = 11;
			global::System.Windows.Forms.NumericUpDown numericUpDown12 = this.numDelayFixed;
			int[] array12 = new int[4];
			array12[0] = 50;
			numericUpDown12.Value = new decimal(array12);
			this.numDelayFixed.ValueChanged += new global::System.EventHandler(this.DelayHandler);
			this.bunifuiOSSwitch1.BackColor = global::System.Drawing.Color.Transparent;
			this.bunifuiOSSwitch1.BackgroundImage = (global::System.Drawing.Image)componentResourceManager.GetObject("bunifuiOSSwitch1.BackgroundImage");
			this.bunifuiOSSwitch1.BackgroundImageLayout = global::System.Windows.Forms.ImageLayout.Stretch;
			this.bunifuiOSSwitch1.Cursor = global::System.Windows.Forms.Cursors.Hand;
			this.bunifuTransition1.SetDecoration(this.bunifuiOSSwitch1, global::BunifuAnimatorNS.DecorationType.None);
			this.bunifuiOSSwitch1.Location = new global::System.Drawing.Point(266, 99);
			this.bunifuiOSSwitch1.Name = "bunifuiOSSwitch1";
			this.bunifuiOSSwitch1.OffColor = global::System.Drawing.Color.Gray;
			this.bunifuiOSSwitch1.OnColor = global::System.Drawing.Color.Firebrick;
			this.bunifuiOSSwitch1.Size = new global::System.Drawing.Size(35, 20);
			this.bunifuiOSSwitch1.TabIndex = 7;
			this.bunifuiOSSwitch1.Value = false;
			this.label11.AutoSize = true;
			this.bunifuTransition1.SetDecoration(this.label11, global::BunifuAnimatorNS.DecorationType.None);
			this.label11.Font = new global::System.Drawing.Font("Segoe UI", 14.25f, global::System.Drawing.FontStyle.Regular, global::System.Drawing.GraphicsUnit.Point, 0);
			this.label11.ForeColor = global::System.Drawing.Color.WhiteSmoke;
			this.label11.Location = new global::System.Drawing.Point(5, 94);
			this.label11.Name = "label11";
			this.label11.Size = new global::System.Drawing.Size(57, 25);
			this.label11.TabIndex = 4;
			this.label11.Text = "Wtap";
			this.label11.Visible = false;
			this.panel8.BackColor = global::System.Drawing.Color.FromArgb(45, 49, 57);
			this.panel8.Controls.Add(this.bunifuiOSSwitch1);
			this.panel8.Controls.Add(this.label11);
			this.panel8.Controls.Add(this.radioButton3);
			this.panel8.Controls.Add(this.radioButton4);
			this.panel8.Controls.Add(this.bunifuMetroTextbox2);
			this.bunifuTransition1.SetDecoration(this.panel8, global::BunifuAnimatorNS.DecorationType.None);
			this.panel8.Location = new global::System.Drawing.Point(437, 48);
			this.panel8.Name = "panel8";
			this.panel8.Size = new global::System.Drawing.Size(311, 134);
			this.panel8.TabIndex = 17;
			this.radioButton3.AutoSize = true;
			this.bunifuTransition1.SetDecoration(this.radioButton3, global::BunifuAnimatorNS.DecorationType.None);
			this.radioButton3.FlatStyle = global::System.Windows.Forms.FlatStyle.Flat;
			this.radioButton3.Font = new global::System.Drawing.Font("Segoe UI", 14.25f);
			this.radioButton3.ForeColor = global::System.Drawing.Color.White;
			this.radioButton3.Location = new global::System.Drawing.Point(10, 13);
			this.radioButton3.Name = "radioButton3";
			this.radioButton3.Size = new global::System.Drawing.Size(86, 29);
			this.radioButton3.TabIndex = 8;
			this.radioButton3.Text = "Clicker";
			this.radioButton3.UseVisualStyleBackColor = true;
			this.radioButton3.CheckedChanged += new global::System.EventHandler(this.RadioButton3_CheckedChanged);
			this.radioButton4.AutoSize = true;
			this.radioButton4.Checked = true;
			this.bunifuTransition1.SetDecoration(this.radioButton4, global::BunifuAnimatorNS.DecorationType.None);
			this.radioButton4.FlatStyle = global::System.Windows.Forms.FlatStyle.Flat;
			this.radioButton4.Font = new global::System.Drawing.Font("Segoe UI", 14.25f);
			this.radioButton4.ForeColor = global::System.Drawing.Color.White;
			this.radioButton4.Location = new global::System.Drawing.Point(110, 13);
			this.radioButton4.Name = "radioButton4";
			this.radioButton4.Size = new global::System.Drawing.Size(74, 29);
			this.radioButton4.TabIndex = 7;
			this.radioButton4.TabStop = true;
			this.radioButton4.Text = "Wtap";
			this.radioButton4.UseVisualStyleBackColor = true;
			this.bunifuMetroTextbox2.BorderColorFocused = global::System.Drawing.Color.FromArgb(161, 34, 34);
			this.bunifuMetroTextbox2.BorderColorIdle = global::System.Drawing.Color.Firebrick;
			this.bunifuMetroTextbox2.BorderColorMouseHover = global::System.Drawing.Color.FromArgb(161, 34, 34);
			this.bunifuMetroTextbox2.BorderThickness = 2;
			this.bunifuMetroTextbox2.Cursor = global::System.Windows.Forms.Cursors.IBeam;
			this.bunifuTransition1.SetDecoration(this.bunifuMetroTextbox2, global::BunifuAnimatorNS.DecorationType.None);
			this.bunifuMetroTextbox2.Font = new global::System.Drawing.Font("Century Gothic", 9.75f);
			this.bunifuMetroTextbox2.ForeColor = global::System.Drawing.Color.White;
			this.bunifuMetroTextbox2.isPassword = false;
			this.bunifuMetroTextbox2.Location = new global::System.Drawing.Point(10, 49);
			this.bunifuMetroTextbox2.Margin = new global::System.Windows.Forms.Padding(4);
			this.bunifuMetroTextbox2.Name = "bunifuMetroTextbox2";
			this.bunifuMetroTextbox2.Size = new global::System.Drawing.Size(291, 38);
			this.bunifuMetroTextbox2.TabIndex = 6;
			this.bunifuMetroTextbox2.Text = "None";
			this.bunifuMetroTextbox2.TextAlign = global::System.Windows.Forms.HorizontalAlignment.Center;
			this.bunifuMetroTextbox2.KeyDown += new global::System.Windows.Forms.KeyEventHandler(this.BunifuMetroTextbox2_KeyDown);
			this.panel7.BackColor = global::System.Drawing.Color.FromArgb(45, 49, 57);
			this.panel7.Controls.Add(this.label24);
			this.panel7.Controls.Add(this.bunifuRange2);
			this.panel7.Controls.Add(this.label25);
			this.panel7.Controls.Add(this.label26);
			this.bunifuTransition1.SetDecoration(this.panel7, global::BunifuAnimatorNS.DecorationType.None);
			this.panel7.Location = new global::System.Drawing.Point(437, 196);
			this.panel7.Name = "panel7";
			this.panel7.Size = new global::System.Drawing.Size(311, 105);
			this.panel7.TabIndex = 18;
			this.label24.AutoSize = true;
			this.bunifuTransition1.SetDecoration(this.label24, global::BunifuAnimatorNS.DecorationType.None);
			this.label24.Font = new global::System.Drawing.Font("Segoe UI Semilight", 9.75f, global::System.Drawing.FontStyle.Regular, global::System.Drawing.GraphicsUnit.Point, 0);
			this.label24.ForeColor = global::System.Drawing.Color.WhiteSmoke;
			this.label24.Location = new global::System.Drawing.Point(23, 75);
			this.label24.Name = "label24";
			this.label24.Size = new global::System.Drawing.Size(100, 17);
			this.label24.TabIndex = 9;
			this.label24.Text = "Wtap/Sec Min: 1";
			this.bunifuRange2.BackColor = global::System.Drawing.Color.Transparent;
			this.bunifuRange2.BackgroudColor = global::System.Drawing.Color.DarkGray;
			this.bunifuRange2.BorderRadius = 2;
			this.bunifuTransition1.SetDecoration(this.bunifuRange2, global::BunifuAnimatorNS.DecorationType.None);
			this.bunifuRange2.IndicatorColor = global::System.Drawing.Color.Firebrick;
			this.bunifuRange2.Location = new global::System.Drawing.Point(20, 43);
			this.bunifuRange2.MaximumRange = 11;
			this.bunifuRange2.Name = "bunifuRange2";
			this.bunifuRange2.RangeMax = 4;
			this.bunifuRange2.RangeMin = 1;
			this.bunifuRange2.Size = new global::System.Drawing.Size(270, 30);
			this.bunifuRange2.TabIndex = 8;
			this.bunifuRange2.RangeChanged += new global::System.EventHandler(this.BunifuRange2_RangeChanged);
			this.label25.AutoSize = true;
			this.bunifuTransition1.SetDecoration(this.label25, global::BunifuAnimatorNS.DecorationType.None);
			this.label25.Font = new global::System.Drawing.Font("Segoe UI Semilight", 9.75f, global::System.Drawing.FontStyle.Regular, global::System.Drawing.GraphicsUnit.Point, 0);
			this.label25.ForeColor = global::System.Drawing.Color.WhiteSmoke;
			this.label25.Location = new global::System.Drawing.Point(191, 75);
			this.label25.Name = "label25";
			this.label25.Size = new global::System.Drawing.Size(105, 17);
			this.label25.TabIndex = 8;
			this.label25.Text = "Wtap/Sec Max: 4";
			this.label26.AutoSize = true;
			this.bunifuTransition1.SetDecoration(this.label26, global::BunifuAnimatorNS.DecorationType.None);
			this.label26.Font = new global::System.Drawing.Font("Segoe UI", 14.25f, global::System.Drawing.FontStyle.Regular, global::System.Drawing.GraphicsUnit.Point, 0);
			this.label26.ForeColor = global::System.Drawing.Color.WhiteSmoke;
			this.label26.Location = new global::System.Drawing.Point(12, 10);
			this.label26.Name = "label26";
			this.label26.Size = new global::System.Drawing.Size(180, 25);
			this.label26.TabIndex = 4;
			this.label26.Text = "Wtap - Delay Range";
			this.bunifuDragControl1.Fixed = true;
			this.bunifuDragControl1.Horizontal = true;
			this.bunifuDragControl1.TargetControl = this.panel6;
			this.bunifuDragControl1.Vertical = true;
			this.bunifuElipse2.ElipseRadius = 6;
			this.bunifuElipse2.TargetControl = this.panel4;
			this.bunifuElipse3.ElipseRadius = 6;
			this.bunifuElipse3.TargetControl = this.panel5;
			this.bunifuElipse4.ElipseRadius = 6;
			this.bunifuElipse4.TargetControl = this.panel5;
			this.bunifuElipse5.ElipseRadius = 6;
			this.bunifuElipse5.TargetControl = this.panel4;
			this.bunifuFormFadeTransition1.Delay = 1;
			this.bunifuElipse6.ElipseRadius = 6;
			this.bunifuElipse6.TargetControl = this;
			this.bunifuElipse7.ElipseRadius = 5;
			this.bunifuElipse7.TargetControl = this.panel8;
			this.bunifuElipse8.ElipseRadius = 5;
			this.bunifuElipse8.TargetControl = this.panel7;
			base.AutoScaleDimensions = new global::System.Drawing.SizeF(6f, 13f);
			base.AutoScaleMode = global::System.Windows.Forms.AutoScaleMode.Font;
			this.BackColor = global::System.Drawing.Color.FromArgb(40, 44, 52);
			base.ClientSize = new global::System.Drawing.Size(375, 465);
			base.Controls.Add(this.panel7);
			base.Controls.Add(this.panel8);
			base.Controls.Add(this.panel6);
			base.Controls.Add(this.label19);
			base.Controls.Add(this.grpMain);
			base.Controls.Add(this.rdbClickSingleMiddle);
			base.Controls.Add(this.label12);
			base.Controls.Add(this.label9);
			base.Controls.Add(this.numDelayFixed);
			base.Controls.Add(this.grpCount);
			base.Controls.Add(this.rdbDelayFixed);
			base.Controls.Add(this.panel1);
			base.Controls.Add(this.panel2);
			base.Controls.Add(this.statusStrip1);
			this.bunifuTransition1.SetDecoration(this, global::BunifuAnimatorNS.DecorationType.None);
			base.FormBorderStyle = global::System.Windows.Forms.FormBorderStyle.None;
			base.Icon = (global::System.Drawing.Icon)componentResourceManager.GetObject("$this.Icon");
			base.Name = "MainForm";
			this.Text = "ywcEMErein.srl";
			base.FormClosing += new global::System.Windows.Forms.FormClosingEventHandler(this.MainForm_FormClosing);
			base.Load += new global::System.EventHandler(this.Form1_Load);
			this.grpCount.ResumeLayout(false);
			this.grpCount.PerformLayout();
			((global::System.ComponentModel.ISupportInitialize)this.numCount).EndInit();
			this.statusStrip1.ResumeLayout(false);
			this.statusStrip1.PerformLayout();
			this.panel2.ResumeLayout(false);
			this.panel2.PerformLayout();
			((global::System.ComponentModel.ISupportInitialize)this.LOGO).EndInit();
			this.panel1.ResumeLayout(false);
			this.panel1.PerformLayout();
			this.panel5.ResumeLayout(false);
			this.panel5.PerformLayout();
			this.panel3.ResumeLayout(false);
			this.panel3.PerformLayout();
			this.panel4.ResumeLayout(false);
			this.panel4.PerformLayout();
			this.grpLocation.ResumeLayout(false);
			this.grpLocation.PerformLayout();
			((global::System.ComponentModel.ISupportInitialize)this.numRandomHeight).EndInit();
			((global::System.ComponentModel.ISupportInitialize)this.numRandomWidth).EndInit();
			((global::System.ComponentModel.ISupportInitialize)this.numRandomY).EndInit();
			((global::System.ComponentModel.ISupportInitialize)this.numRandomX).EndInit();
			((global::System.ComponentModel.ISupportInitialize)this.numFixedY).EndInit();
			((global::System.ComponentModel.ISupportInitialize)this.numFixedX).EndInit();
			this.grpMain.ResumeLayout(false);
			this.grpDelay.ResumeLayout(false);
			this.grpDelay.PerformLayout();
			this.panel6.ResumeLayout(false);
			this.panel6.PerformLayout();
			((global::System.ComponentModel.ISupportInitialize)this.pictureBox1).EndInit();
			((global::System.ComponentModel.ISupportInitialize)this.numDelayFixed).EndInit();
			this.panel8.ResumeLayout(false);
			this.panel8.PerformLayout();
			this.panel7.ResumeLayout(false);
			this.panel7.PerformLayout();
			base.ResumeLayout(false);
			base.PerformLayout();
		}

		// Token: 0x04000059 RID: 89
		private global::System.ComponentModel.IContainer components;

		// Token: 0x0400005A RID: 90
		private global::System.Windows.Forms.GroupBox grpCount;

		// Token: 0x0400005B RID: 91
		private global::System.Windows.Forms.Label label1;

		// Token: 0x0400005C RID: 92
		private global::System.Windows.Forms.NumericUpDown numCount;

		// Token: 0x0400005D RID: 93
		private global::System.Windows.Forms.RadioButton rdbCount;

		// Token: 0x0400005E RID: 94
		private global::System.Windows.Forms.RadioButton rdbUntilStopped;

		// Token: 0x0400005F RID: 95
		private global::System.Windows.Forms.Label label9;

		// Token: 0x04000060 RID: 96
		private global::System.Windows.Forms.RadioButton rdbDelayFixed;

		// Token: 0x04000061 RID: 97
		private global::System.Windows.Forms.StatusStrip statusStrip1;

		// Token: 0x04000062 RID: 98
		private global::System.Windows.Forms.ToolStripStatusLabel tslStatus;

		// Token: 0x04000063 RID: 99
		private global::System.Windows.Forms.Button btnToggle;

		// Token: 0x04000064 RID: 100
		private global::System.Windows.Forms.Label label13;

		// Token: 0x04000065 RID: 101
		private global::System.Windows.Forms.Label label12;

		// Token: 0x04000066 RID: 102
		private global::System.Windows.Forms.Panel panel2;

		// Token: 0x04000067 RID: 103
		internal global::Bunifu.Framework.UI.BunifuImageButton LOGO;

		// Token: 0x04000068 RID: 104
		internal global::Bunifu.Framework.UI.BunifuFlatButton HWID;

		// Token: 0x04000069 RID: 105
		internal global::System.Windows.Forms.Label label15;

		// Token: 0x0400006A RID: 106
		internal global::System.Windows.Forms.Label label14;

		// Token: 0x0400006B RID: 107
		private global::System.Windows.Forms.Panel panel1;

		// Token: 0x0400006C RID: 108
		private global::System.Windows.Forms.Panel panel3;

		// Token: 0x0400006D RID: 109
		internal global::Bunifu.Framework.UI.BunifuFlatButton btnHotkeyRemove;

		// Token: 0x0400006E RID: 110
		private global::Bunifu.Framework.UI.BunifuElipse bunifuElipse1;

		// Token: 0x0400006F RID: 111
		private global::Bunifu.Framework.UI.BunifuMetroTextbox txtHotkey;

		// Token: 0x04000070 RID: 112
		private global::System.Windows.Forms.RadioButton rdbClickSingleMiddle;

		// Token: 0x04000071 RID: 113
		private global::System.Windows.Forms.Panel panel4;

		// Token: 0x04000072 RID: 114
		private global::System.Windows.Forms.Label leftlbl;

		// Token: 0x04000073 RID: 115
		private global::Bunifu.Framework.UI.BunifuiOSSwitch left;

		// Token: 0x04000074 RID: 116
		private global::System.Windows.Forms.Label label16;

		// Token: 0x04000075 RID: 117
		private global::BunifuAnimatorNS.BunifuTransition bunifuTransition1;

		// Token: 0x04000076 RID: 118
		private global::Bunifu.Framework.UI.BunifuiOSSwitch doubleclick;

		// Token: 0x04000077 RID: 119
		private global::System.Windows.Forms.Label label17;

		// Token: 0x04000078 RID: 120
		private global::System.Windows.Forms.Panel panel5;

		// Token: 0x04000079 RID: 121
		private global::System.Windows.Forms.Label label20;

		// Token: 0x0400007A RID: 122
		private global::System.Windows.Forms.RadioButton rdbDelayRange;

		// Token: 0x0400007B RID: 123
		private global::System.Windows.Forms.Label label8;

		// Token: 0x0400007C RID: 124
		private global::System.Windows.Forms.Label label10;

		// Token: 0x0400007D RID: 125
		private global::System.Windows.Forms.GroupBox grpLocation;

		// Token: 0x0400007E RID: 126
		private global::System.Windows.Forms.Button btnSelect;

		// Token: 0x0400007F RID: 127
		private global::System.Windows.Forms.Label label6;

		// Token: 0x04000080 RID: 128
		private global::System.Windows.Forms.NumericUpDown numRandomHeight;

		// Token: 0x04000081 RID: 129
		private global::System.Windows.Forms.Label label7;

		// Token: 0x04000082 RID: 130
		private global::System.Windows.Forms.NumericUpDown numRandomWidth;

		// Token: 0x04000083 RID: 131
		private global::System.Windows.Forms.Label label4;

		// Token: 0x04000084 RID: 132
		private global::System.Windows.Forms.NumericUpDown numRandomY;

		// Token: 0x04000085 RID: 133
		private global::System.Windows.Forms.Label label5;

		// Token: 0x04000086 RID: 134
		private global::System.Windows.Forms.NumericUpDown numRandomX;

		// Token: 0x04000087 RID: 135
		private global::System.Windows.Forms.Label label3;

		// Token: 0x04000088 RID: 136
		private global::System.Windows.Forms.NumericUpDown numFixedY;

		// Token: 0x04000089 RID: 137
		private global::System.Windows.Forms.Label label2;

		// Token: 0x0400008A RID: 138
		private global::System.Windows.Forms.NumericUpDown numFixedX;

		// Token: 0x0400008B RID: 139
		private global::System.Windows.Forms.RadioButton rdbLocationRandomArea;

		// Token: 0x0400008C RID: 140
		private global::System.Windows.Forms.RadioButton rdbLocationFixed;

		// Token: 0x0400008D RID: 141
		private global::System.Windows.Forms.RadioButton rdbLocationRandom;

		// Token: 0x0400008E RID: 142
		private global::System.Windows.Forms.RadioButton rdbLocationMouse;

		// Token: 0x0400008F RID: 143
		private global::System.Windows.Forms.GroupBox grpMain;

		// Token: 0x04000090 RID: 144
		private global::System.Windows.Forms.GroupBox grpClickType;

		// Token: 0x04000091 RID: 145
		private global::System.Windows.Forms.GroupBox grpDelay;

		// Token: 0x04000092 RID: 146
		private global::System.Windows.Forms.Label label19;

		// Token: 0x04000093 RID: 147
		private global::System.Windows.Forms.Label label18;

		// Token: 0x04000094 RID: 148
		private global::System.Windows.Forms.Panel panel6;

		// Token: 0x04000095 RID: 149
		private global::Bunifu.Framework.UI.BunifuDragControl bunifuDragControl1;

		// Token: 0x04000096 RID: 150
		private global::System.Windows.Forms.Label label21;

		// Token: 0x04000098 RID: 152
		private global::Bunifu.Framework.UI.BunifuElipse bunifuElipse2;

		// Token: 0x04000099 RID: 153
		private global::Bunifu.Framework.UI.BunifuElipse bunifuElipse3;

		// Token: 0x0400009C RID: 156
		private global::Bunifu.Framework.UI.BunifuElipse bunifuElipse4;

		// Token: 0x0400009D RID: 157
		private global::Bunifu.Framework.UI.BunifuElipse bunifuElipse5;

		// Token: 0x0400009E RID: 158
		private global::FlatUI.FlatClose flatClose2;

		// Token: 0x0400009F RID: 159
		private global::Bunifu.Framework.UI.BunifuFormFadeTransition bunifuFormFadeTransition1;

		// Token: 0x040000A0 RID: 160
		private global::System.Windows.Forms.PictureBox pictureBox1;

		// Token: 0x040000A1 RID: 161
		private global::Bunifu.Framework.UI.BunifuRange bunifuRange1;

		// Token: 0x040000A2 RID: 162
		private global::System.Windows.Forms.Label label22;

		// Token: 0x040000A3 RID: 163
		private global::System.Windows.Forms.NumericUpDown numDelayFixed;

		// Token: 0x040000A4 RID: 164
		private global::System.Windows.Forms.Label label23;

		// Token: 0x040000A5 RID: 165
		private global::Bunifu.Framework.UI.BunifuElipse bunifuElipse6;

		// Token: 0x040000A6 RID: 166
		private global::Bunifu.Framework.UI.BunifuiOSSwitch bunifuiOSSwitch1;

		// Token: 0x040000A7 RID: 167
		private global::System.Windows.Forms.RadioButton radioButton2;

		// Token: 0x040000A8 RID: 168
		private global::System.Windows.Forms.RadioButton radioButton1;

		// Token: 0x040000A9 RID: 169
		private global::System.Windows.Forms.Label label11;

		// Token: 0x040000AA RID: 170
		private global::System.Windows.Forms.Panel panel8;

		// Token: 0x040000AB RID: 171
		private global::System.Windows.Forms.RadioButton radioButton3;

		// Token: 0x040000AC RID: 172
		private global::System.Windows.Forms.RadioButton radioButton4;

		// Token: 0x040000AD RID: 173
		private global::Bunifu.Framework.UI.BunifuMetroTextbox bunifuMetroTextbox2;

		// Token: 0x040000AE RID: 174
		private global::Bunifu.Framework.UI.BunifuElipse bunifuElipse7;

		// Token: 0x040000AF RID: 175
		private global::System.Windows.Forms.Panel panel7;

		// Token: 0x040000B0 RID: 176
		private global::System.Windows.Forms.Label label24;

		// Token: 0x040000B1 RID: 177
		private global::Bunifu.Framework.UI.BunifuRange bunifuRange2;

		// Token: 0x040000B2 RID: 178
		private global::System.Windows.Forms.Label label25;

		// Token: 0x040000B3 RID: 179
		private global::System.Windows.Forms.Label label26;

		// Token: 0x040000B4 RID: 180
		private global::Bunifu.Framework.UI.BunifuElipse bunifuElipse8;

		// Token: 0x040000B5 RID: 181
		private global::Bunifu.Framework.UI.BunifuFlatButton bunifuImageButton1;

		// Token: 0x040000B6 RID: 182
		private global::System.Windows.Forms.Label label27;
	}
}

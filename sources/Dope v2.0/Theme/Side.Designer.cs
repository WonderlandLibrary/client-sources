
namespace Dope.Theme
{
    partial class Side
    {
        /// <summary> 
        /// Variable nécessaire au concepteur.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary> 
        /// Nettoyage des ressources utilisées.
        /// </summary>
        /// <param name="disposing">true si les ressources managées doivent être supprimées ; sinon, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Code généré par le Concepteur de composants

        /// <summary> 
        /// Méthode requise pour la prise en charge du concepteur - ne modifiez pas 
        /// le contenu de cette méthode avec l'éditeur de code.
        /// </summary>
        private void InitializeComponent()
        {
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(Side));
            this.button5 = new Dope.Theme.button();
            this.button4 = new Dope.Theme.button();
            this.button3 = new Dope.Theme.button();
            this.button2 = new Dope.Theme.button();
            this.button1 = new Dope.Theme.button();
            this.collapse1 = new Dope.Theme.Collapse();
            this.logo1 = new Dope.Theme.Logo();
            ((System.ComponentModel.ISupportInitialize)(this.logo1)).BeginInit();
            this.SuspendLayout();
            // 
            // button5
            // 
            this.button5.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.button5.Collapse = true;
            this.button5.CollapseWidth = 180;
            this.button5.Image = ((System.Drawing.Image)(resources.GetObject("button5.Image")));
            this.button5.Location = new System.Drawing.Point(0, 327);
            this.button5.Name = "button5";
            this.button5.Size = new System.Drawing.Size(200, 56);
            this.button5.TabIndex = 6;
            this.button5.Text = "Settings";
            this.button5.Click += new System.EventHandler(this.IndexSwitch);
            // 
            // button4
            // 
            this.button4.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.button4.Collapse = true;
            this.button4.CollapseWidth = 180;
            this.button4.Image = ((System.Drawing.Image)(resources.GetObject("button4.Image")));
            this.button4.Location = new System.Drawing.Point(0, 265);
            this.button4.Name = "button4";
            this.button4.Size = new System.Drawing.Size(200, 56);
            this.button4.TabIndex = 5;
            this.button4.Text = "Config";
            this.button4.Click += new System.EventHandler(this.IndexSwitch);
            // 
            // button3
            // 
            this.button3.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.button3.Collapse = true;
            this.button3.CollapseWidth = 180;
            this.button3.Image = ((System.Drawing.Image)(resources.GetObject("button3.Image")));
            this.button3.Location = new System.Drawing.Point(0, 203);
            this.button3.Name = "button3";
            this.button3.Size = new System.Drawing.Size(200, 56);
            this.button3.TabIndex = 4;
            this.button3.Text = "Click Sound";
            this.button3.Click += new System.EventHandler(this.IndexSwitch);
            // 
            // button2
            // 
            this.button2.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.button2.Collapse = true;
            this.button2.CollapseWidth = 180;
            this.button2.Image = ((System.Drawing.Image)(resources.GetObject("button2.Image")));
            this.button2.Location = new System.Drawing.Point(0, 141);
            this.button2.Name = "button2";
            this.button2.Size = new System.Drawing.Size(200, 56);
            this.button2.TabIndex = 3;
            this.button2.Text = "Jitter";
            this.button2.Click += new System.EventHandler(this.IndexSwitch);
            // 
            // button1
            // 
            this.button1.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.button1.Collapse = true;
            this.button1.CollapseWidth = 180;
            this.button1.Image = ((System.Drawing.Image)(resources.GetObject("button1.Image")));
            this.button1.Location = new System.Drawing.Point(0, 79);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(200, 56);
            this.button1.TabIndex = 2;
            this.button1.Text = "AutoClicker";
            this.button1.Click += new System.EventHandler(this.IndexSwitch);
            // 
            // collapse1
            // 
            this.collapse1.Checked = false;
            this.collapse1.Location = new System.Drawing.Point(0, 404);
            this.collapse1.Name = "collapse1";
            this.collapse1.Size = new System.Drawing.Size(30, 30);
            this.collapse1.TabIndex = 1;
            this.collapse1.Text = "collapse1";
            this.collapse1.Click += new System.EventHandler(this.collapse1_Click);
            // 
            // logo1
            // 
            this.logo1.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.logo1.Image = ((System.Drawing.Image)(resources.GetObject("logo1.Image")));
            this.logo1.Location = new System.Drawing.Point(0, 6);
            this.logo1.Name = "logo1";
            this.logo1.Normal = ((System.Drawing.Image)(resources.GetObject("logo1.Normal")));
            this.logo1.Size = new System.Drawing.Size(200, 54);
            this.logo1.SizeMode = System.Windows.Forms.PictureBoxSizeMode.Zoom;
            this.logo1.Small = ((System.Drawing.Image)(resources.GetObject("logo1.Small")));
            this.logo1.TabIndex = 0;
            this.logo1.TabStop = false;
            // 
            // Side
            // 
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
            this.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(26)))), ((int)(((byte)(29)))), ((int)(((byte)(35)))));
            this.Controls.Add(this.button5);
            this.Controls.Add(this.button4);
            this.Controls.Add(this.button3);
            this.Controls.Add(this.button2);
            this.Controls.Add(this.button1);
            this.Controls.Add(this.collapse1);
            this.Controls.Add(this.logo1);
            this.Font = new System.Drawing.Font("Century Gothic", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.ForeColor = System.Drawing.Color.Gainsboro;
            this.Name = "Side";
            this.Size = new System.Drawing.Size(200, 437);
            this.Resize += new System.EventHandler(this.Side_Resize);
            ((System.ComponentModel.ISupportInitialize)(this.logo1)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private Logo logo1;
        private Collapse collapse1;
        private button button1;
        private button button2;
        private button button3;
        private button button4;
        private button button5;
    }
}

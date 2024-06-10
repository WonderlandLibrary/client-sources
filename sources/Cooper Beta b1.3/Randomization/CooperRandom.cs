using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
using System.Threading.Tasks;

namespace Cooper
{
    public sealed class CooperRandom : Random, IDisposable
    {
        // Token: 0x06000151 RID: 337 RVA: 0x000024C3 File Offset: 0x000006C3
        public CooperRandom()
        {
        }

        // Token: 0x06000152 RID: 338 RVA: 0x000024C3 File Offset: 0x000006C3
        public CooperRandom(int seedIgnored)
        {
        }

        // Token: 0x06000153 RID: 339 RVA: 0x0000F218 File Offset: 0x0000D418
        public override int Next()
        {
            this.cryptoProvider.GetBytes(this.uint32Buffer);
            return BitConverter.ToInt32(this.uint32Buffer, 0) & int.MaxValue;
        }

        // Token: 0x06000154 RID: 340 RVA: 0x0000F258 File Offset: 0x0000D458
        public override int Next(int maxValue)
        {
            if (maxValue < 0)
            {
                throw new ArgumentOutOfRangeException("maxValue");
            }
            return this.Next(0, maxValue);
        }

        // Token: 0x06000155 RID: 341 RVA: 0x0000F290 File Offset: 0x0000D490
        public override int Next(int minValue, int maxValue)
        {
            if (minValue > maxValue)
            {
                throw new ArgumentOutOfRangeException("minValue");
            }
            if (minValue == maxValue)
            {
                return minValue;
            }
            long num = (long)(maxValue - minValue);
            long num2 = 4294967296L;
            long num3 = 4294967296L % num;
            uint num4;
            do
            {
                this.cryptoProvider.GetBytes(this.uint32Buffer);
                num4 = BitConverter.ToUInt32(this.uint32Buffer, 0);
            }
            while ((ulong)num4 >= (ulong)(num2 - num3));
            return (int)((long)minValue + (long)((ulong)num4 % (ulong)num));
        }

        // Token: 0x06000156 RID: 342 RVA: 0x0000F340 File Offset: 0x0000D540
        public override double NextDouble()
        {
            this.cryptoProvider.GetBytes(this.uint32Buffer);
            uint num = BitConverter.ToUInt32(this.uint32Buffer, 0);
            return num / 4294967296.0;
        }

        // Token: 0x06000157 RID: 343 RVA: 0x0000F38C File Offset: 0x0000D58C
        public override void NextBytes(byte[] buffer)
        {
            if (buffer == null)
            {
                throw new ArgumentNullException("buffer");
            }
            this.cryptoProvider.GetBytes(buffer);
        }

        // Token: 0x06000158 RID: 344 RVA: 0x0000F3BC File Offset: 0x0000D5BC
        public void Dispose()
        {
            this.InternalDispose();
        }

        // Token: 0x06000159 RID: 345 RVA: 0x0000F3D4 File Offset: 0x0000D5D4
        ~CooperRandom()
        {
            this.InternalDispose();
        }

        // Token: 0x0600015A RID: 346 RVA: 0x0000F408 File Offset: 0x0000D608
        private void InternalDispose()
        {
            this.cryptoProvider = null;
            if (this.cryptoProvider != null)
            {
                this.cryptoProvider.Dispose();
            }
        }

        // Token: 0x04000076 RID: 118
        private RNGCryptoServiceProvider cryptoProvider = new RNGCryptoServiceProvider();

        // Token: 0x04000077 RID: 119
        private byte[] uint32Buffer = new byte[4];
    }
}

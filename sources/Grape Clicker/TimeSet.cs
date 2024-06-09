using System;

namespace Xh0kO1ZCmA
{
	public class TimeSet : OneAction
	{
		public DateTime MainTime;

		public TimeSet(DateTime _MainTime) : base(DataSession.ActionType.TimeSet, 0)
		{
			this.MainTime = _MainTime;
		}

		public override string ToString()
		{
			return string.Concat("New Time >> ", this.MainTime.ToString());
		}
	}
}
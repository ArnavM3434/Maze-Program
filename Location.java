public class Location //think why this class is useful instead of storing location in explorer class
{
	private int r;
	private int c;

	public Location(int r, int c)
	{
		this.r = r;
		this.c = c;
	}
//	public void setR(int a)
//	{
//		 r += a;
//	}
//	public void setC(int b)
//	{
//		c += b;
//	}

	public int getRow()
	{
		return r;
	}

	public int getCol()
	{
		return c;
	}

	public void setRow(int a)
	{
		r += a;
	}
	public void setCol(int b)
	{
		c += b;
	}










}
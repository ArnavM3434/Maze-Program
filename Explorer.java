import java.awt.Color;
import java.awt.Rectangle;
public class Explorer
{
	private Location loc;
	private int size;
	private Color color;
	private int dir;
	private int count = 0;
	public Explorer(Location loc, int size, int dir, Color color)
	{
		this.loc = loc;
		this.size = size;
		this.dir = dir;
		this.color = color;
	}
	public Color getColor()
	{
		return color;
	}
	public Location getLoc()
	{
		return loc;
	}
	public int getCount()
	{
		return count;
	}
	public int getDir()
	{
		return dir;
	}
	public void monsterMove(int heror, int heroc, char[][] maze)
	{
		int r = getLoc().getRow();
		int c = getLoc().getCol();





			if(c > heroc)
			{
				if (maze[r][c-1] != '#' && c != 0)
				getLoc().setCol(-1);

			}

			if(c < heroc)
			{
				if (maze[r][c+1] != '#' && c != 34)
				getLoc().setCol(+1);
			}
			if(r > heror)
			{
				if (maze[r-1][c] != '#')
				getLoc().setRow(-1);
			}
			if(r < heror)
			{
				if (maze[r+1][c] != '#')
				getLoc().setRow(+1);
			}




	}

	public void move(int key, char[][] maze) //need key pressed and environment
	{
		int r = getLoc().getRow();
		int c = getLoc().getCol();

		if(key == 38) //forward
		{
			if(dir == 0) //up
			{
				if(r > 0 && maze[r-1][c] != '#')
				{
					count++;
					loc.setRow(-1);
				}

			}
			if(dir == 1) //right
			{
				if(c < maze[0].length - 1 && maze[r][c+1] != '#' && c != 34)
				{
					count++;
					getLoc().setCol(+1);
				}
			}
			if (dir == 2) //down
			{
				if(r < maze.length - 1 && maze[r+1][c] != '#')
				{
					count++;
					getLoc().setRow(+1);
				}
			}
			if(dir == 3) //left
			{
				if (c > 0 && maze[r][c-1] != '#')
				{
					count++;
					getLoc().setCol(-1);
				}
			}
		}


			if(key == 37) //rotate left
			{
				dir--;
				if(dir < 0)
				dir = 3;
			}
			if(key == 39) //rotate right
			{
				dir++;
				dir = dir%4;
			}

	}


	public Rectangle getRect()
	{
		int r = getLoc().getRow();
		int c = getLoc().getCol();
		return new Rectangle(c*20 + 20, r*20 + 20, 20, 20);
	}





}
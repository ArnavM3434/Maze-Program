import javax.swing.*;//all the Jpanel stuff
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.geom.AffineTransform;



public class MazeProject extends JPanel implements KeyListener
{
	JFrame frame;
	int c = 600;
	int r = 350;
	Explorer explorer;
	Explorer monster;
	Explorer monster2;

	BuildMaze maze1 = new BuildMaze();
	char[][] maze = maze1.getMaze(); //gets maze from BuildMaze file
	boolean threeD = false;
	int lookahead = 5;
	int backwall_size = 0;
	int scale = 50;
	private Thread t;
	Font font = new Font("Times New Roman", Font.BOLD, 20);
	  AffineTransform affineTransform = new AffineTransform();
	  Polygon compass = new Polygon(new int[]{795, 800, 805}, new int[]{500, 400, 500}, 3);
	  Polygon compass1 = new Polygon(new int[]{795, 800, 805}, new int[] {500, 600, 500}, 3);
	  Polygon compass2 = new Polygon(new int[]{805, 905, 805}, new int[] {495, 500, 505}, 3);
	  Polygon compass3 = new Polygon(new int[]{795, 695, 795}, new int[] {495, 500, 505}, 3);




	//arraylist of left walls
	ArrayList<Wall> Walls;





	public MazeProject()
	{
		explorer = new Explorer (new Location (1, 0), 1, 20, Color.RED);
		monster = new Explorer (new Location(1, 22), 2, 20, Color.BLUE);
		monster2 = new Explorer (new Location(7, 32), 2, 20, Color.BLUE);


		frame = new JFrame("A-Mazing Program");
		//prestore maze in text document

		frame.add(this); //add program to frame, canvas goes in the frame
		frame.setSize(1200,700);
		frame.addKeyListener(this);//turns on KeyListener interface

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //what happens when you close program
		frame.setVisible(true); //tells frame to be visible

		//t= new Thread(this);
		//t.start();


	}

	public void paintComponent(Graphics g)//drawing
	{
		super.paintComponent(g); //giant eraser, each time you repaint something it redraws picture
		Graphics2D g2 = (Graphics2D)g; //what does this do?
		g2.setColor(Color.BLACK); //for paintbrush
		g2.fillRect(0, 0, frame.getWidth(), frame.getHeight());

		//g2.setColor(Color.RED);
		//g2.fillOval(c, r, 30, 30); //first 2 numbers are coordinates
		//g2.setColor(Color.CYAN);
		//g2.setStroke(new BasicStroke(10)); //thickness of brush?
		//g2.drawOval(c, r, 30, 30);

		if(!threeD)
		{
			g2.setColor(Color.GRAY);
			int size = 20;
			for(int r = 0; r < maze.length ; r++)
			{
				for(int c = 0; c < maze[r].length-1; c++)
				{
					if( maze[r][c] == '#'){
						g2.drawRect(c * size + size, r* size + size, size, size);
					}else{
						g2.fillRect(c * size + size, r* size + size, size, size);
					}
				}
			}

			g2.setColor(explorer.getColor());
			g2.fill(explorer.getRect());
			monster.monsterMove(explorer.getLoc().getRow(), explorer.getLoc().getCol(), maze);
			g2.setColor(monster.getColor());
			g2.fill(monster.getRect());
			monster2.monsterMove(explorer.getLoc().getRow(), explorer.getLoc().getCol(), maze);
			g2.fill(monster2.getRect());
			g2.setFont(font);
			g2.setColor(Color.WHITE);

			if(monster.getLoc().getRow() == explorer.getLoc().getRow() && monster.getLoc().getCol() == explorer.getLoc().getCol())
			g2.drawString("YOU LOSE!", 900, 300);
			if(monster2.getLoc().getRow() == explorer.getLoc().getRow() && monster2.getLoc().getCol() == explorer.getLoc().getCol())
			g2.drawString("YOU LOSE!", 900, 300);


			g2.drawString("Number of Moves: " + explorer.getCount(),900, 200);

		if(explorer.getLoc().getRow() == 11 && explorer.getLoc().getCol() == 34)
		g2.drawString("Congratulations! You finished the maze!", 800, 300);

		if(explorer.getDir() == 0)
		g2.setPaint(Color.RED);
		else
		g2.setPaint(Color.WHITE);
		g2.drawPolygon(compass);
		g2.drawString("N", 800, 375);

		if(explorer.getDir() == 1)
			g2.setPaint(Color.RED);
		else
		g2.setPaint(Color.WHITE);
		g2.drawPolygon(compass2);
		g2.drawString("E", 930, 500);


		if(explorer.getDir() == 2)
		g2.setPaint(Color.RED);
		else
		g2.setPaint(Color.WHITE);
		g2.drawPolygon(compass1);
		g2.drawString("S", 800, 635);


		if(explorer.getDir() == 3)
		g2.setPaint(Color.RED);
		else
		g2.setPaint(Color.WHITE);
		g2.drawPolygon(compass3);
		g2.drawString("W", 660, 500);








		}
		else
		{

			g2.setStroke(new BasicStroke(2));
			createWalls();
			for(int i = 0; i < Walls.size(); i++)
			{

				g2.setColor(Color.BLACK);
				g2.drawPolygon(Walls.get(i).drawPolygon());
				g2.setPaint(Walls.get(i).getPaint());
				g2.fillPolygon(Walls.get(i).drawPolygon());


			}


		}


	}



	public void keyReleased(KeyEvent e)
	{
	}
	public void keyPressed(KeyEvent e) //we'll use this
	{

			//System.out.println(e.getKeyCode());
			//up = 38, left, right, down...
			if(e.getKeyCode() == 32) //space bar
			{
				threeD = !threeD;
			}

			explorer.move(e.getKeyCode(), maze);
			repaint();
	}

	public void keyTyped(KeyEvent e)
	{

	}
	public Wall getLeftPath(int n)
	{

		int[] rLocs = new int[] {100+50*n, 100+50*n, 700-50*n, 700-50*n};
		int[] cLocs = new int[] {50 + 50*n, 100+50*n, 100 + 50*n, 50+50*n};
		return new Wall(cLocs, rLocs, "Left Square", 255-scale*n, 255-scale*n, 255-scale*n, n);
	}
	public Wall getLeftTrapezoid(int n)
	{
		int[] rLocs = new int[] {50+50*n, 100+50*n, 700-50*n, 750-50*n};
		int[] cLocs = new int[] {50 + 50*n, 100+50*n, 100 + 50*n, 50+50*n};
		return new Wall(cLocs, rLocs, "Left Trapezoid", 255-scale*n, 255-scale*n, 255-scale*n, n);

	}
	public Wall getRightPath(int n)
	{
		int [] rLocs = new int[] {100 + 50*n, 100 + 50*n, 700-50*n, 700-50*n};
		int [] cLocs = new int[] {700-50*n, 750-50*n, 750-50*n, 700-50*n};
		return new Wall (cLocs, rLocs, "Right Square", 255-scale*n, 255-scale*n, 255-scale*n, n);

	}
	public Wall getRightTrapezoid(int n)
	{
		int [] rLocs = new int[] {100 + 50*n, 50 + 50*n, 750-50*n, 700-50*n};
		int [] cLocs = new int[] {700-50*n, 750-50*n, 750-50*n, 700-50*n};
		return new Wall (cLocs, rLocs, "Right Trapezoid", 255-scale*n, 255-scale*n, 255-scale*n, n);

	}
	public Wall getBackWall(int n)
	{
		int [] rLocs = new int[] {300-50*n, 300-50*n, 500+50*n, 500+50*n};
		int [] cLocs = new int[] {300-50*n, 500+50*n, 500+50*n, 300-50*n};
		return new Wall(cLocs, rLocs, "Back Wall", 10 + scale*n, 10 + scale*n, 10 + scale*n, n);
	}
	public Wall getCeiling(int n)
	{
		int [] rLocs = new int[] {50 + 50*n, 50+50*n, 100+50*n, 100+50*n};
		int [] cLocs = new int[] {50+50*n, 750-50*n, 700-50*n, 100+50*n};
		return new Wall(cLocs, rLocs, "Ceiling", 255-scale*n, 255-scale*n, 255-scale*n, n);

	}
	public Wall getFloor(int n)
	{
		int [] rLocs = new int[] {700 - 50*n, 700-50*n, 750-50*n, 750-50*n};
		int [] cLocs = new int[] {100+50*n, 700-50*n, 750-50*n, 50+50*n};
		return new Wall(cLocs, rLocs, "Floor", 255-scale*n, 255-scale*n, 255-scale*n, n);
	}
	public void createWalls()
	{
		Walls = new ArrayList<Wall>();
		int row = explorer.getLoc().getRow();
		int col = explorer.getLoc().getCol();
		int dir = explorer.getDir();







		switch (dir)
		{





			case 0:

			if( maze[row-1][col] == '#')
			{
				backwall_size = 4;
				lookahead = 1;
			}
			else if (maze[row-2][col] == '#')
			{
				backwall_size = 3;
				lookahead = 2;
			}
			else if (maze[row-3][col] == '#')
			{
				backwall_size = 2;
				lookahead = 3;
			}
			else if (maze[row-4][col] == '#')
			{
				backwall_size = 1;
				lookahead = 4;
			}
			else if (maze[row-5][col] == '#')
			{
				backwall_size = 0;
				lookahead = 5;
			}
			else
			{
			backwall_size = 0;
			lookahead = 5;
			}
			for(int i = 0; i < lookahead; i++)
			{
				try{

					if(col == 0 || maze[row-i][col-1] == '#')
					Walls.add(getLeftTrapezoid(i));
					else
					{
						Walls.add(getLeftTrapezoid(i));
						Walls.add(getLeftPath(i));
					}

					if(col == 34 || maze[row-i][col+1] == '#' )
					Walls.add(getRightTrapezoid(i));
					else
					{
						Walls.add(getRightTrapezoid(i));
						Walls.add(getRightPath(i));
					}


					}
				catch(ArrayIndexOutOfBoundsException e)
					{
					}
					Walls.add(getCeiling(i));
					Walls.add(getFloor(i));

			}
			break;




			case 1:
			if((col == 34) || maze[row][col+1] == '#')
						{
							backwall_size = 4;
							lookahead = 1;
						}
						else if ((col + 1 == 34) || maze[row][col+2] == '#')
						{
							backwall_size = 3;
							lookahead = 2;
						}
						else if ((col+ 2 == 34) || maze[row][col+3] == '#')
						{
							backwall_size = 2;
							lookahead = 3;
						}
						else if ((col+3 == 34) || maze[row][col+4] == '#')
						{
							backwall_size = 1;
							lookahead = 4;
						}
						else if ( (col + 4 == 34) || maze[row][col+5] == '#')
						{
							backwall_size = 0;
							lookahead = 5;
						}
						else
						{
						backwall_size = 0;
						lookahead = 5;
						}
			for(int i = 0; i < lookahead; i++)
			{
				try{

					if(col == 34 || maze[row-1][col+i] == '#')
					Walls.add(getLeftTrapezoid(i));
					else
					{
						Walls.add(getLeftTrapezoid(i));
						Walls.add(getLeftPath(i));
					}

					if(col == 34 || maze[row+1][col+i] == '#')
					Walls.add(getRightTrapezoid(i));
					else
					{
						Walls.add(getRightTrapezoid(i));
						Walls.add(getRightPath(i));
					}



					}
				catch(ArrayIndexOutOfBoundsException e)
					{
					}
					Walls.add(getCeiling(i));
					Walls.add(getFloor(i));


			}
			break;

			case 2:
			if( maze[row+1][col] == '#')
									{
										backwall_size = 4;
										lookahead = 1;
									}
									else if (maze[row+2][col] == '#')
									{
										backwall_size = 3;
										lookahead = 2;
									}
									else if (maze[row+3][col] == '#')
									{
										backwall_size = 2;
										lookahead = 3;
									}
									else if (maze[row+4][col] == '#')
									{
										backwall_size = 1;
										lookahead = 4;
									}
									else if (maze[row+5][col] == '#')
									{
										backwall_size = 0;
										lookahead = 5;
									}
									else
									{
									backwall_size = 0;
									lookahead = 5;
									}
			for(int i = 0; i < lookahead; i++)
			{
				try{

					if(col == 34 ||  (maze[row+i][col+1] == '#'))
					{
					Walls.add(getLeftTrapezoid(i));

					}
					else
					{
						Walls.add(getLeftTrapezoid(i));
						Walls.add(getLeftPath(i));

					}
					if( col == 0 || (maze[row+i][col-1] == '#' ))
					Walls.add(getRightTrapezoid(i));
					else
					{
						Walls.add(getRightTrapezoid(i));
						Walls.add(getRightPath(i));
					}

					}
				catch(ArrayIndexOutOfBoundsException e)
					{
						System.out.println("doesn't work");
					}
					Walls.add(getCeiling(i));
					Walls.add(getFloor(i));

			}
			break;

			case 3:
			if( (col) == 0 || maze[row][col-1] == '#' )
												{
													backwall_size = 4;
													lookahead = 1;
												}
												else if ((col-1) == 0 || maze[row][col-2] == '#' )
												{
													backwall_size = 3;
													lookahead = 2;
												}
												else if ((col - 2) == 0 || maze[row][col-3] == '#')
												{
													backwall_size = 2;
													lookahead = 3;
												}
												else if ((col - 3) == 0 || maze[row][col-4] == '#')
												{
													backwall_size = 1;
													lookahead = 4;
												}
												else if ((col - 4) == 0 || maze[row][col-5] == '#')
												{
													backwall_size = 0;
													lookahead = 5;
												}
												else
												{
												backwall_size = 0;
												lookahead = 5;
						}
			for(int i = 0; i < lookahead; i++)
			{
				try{

					if( col == 0 || maze[row+1][col-i] == '#')
					Walls.add(getLeftTrapezoid(i));
					else
					{
						Walls.add(getLeftTrapezoid(i));
						Walls.add(getLeftPath(i));
					}
					if(col == 0 || maze[row-1][col-i] == '#')
					Walls.add(getRightTrapezoid(i));
					else
					{
						Walls.add(getRightTrapezoid(i));
						Walls.add(getRightPath(i));
					}



					}
				catch(ArrayIndexOutOfBoundsException e)
					{
					}
					Walls.add(getCeiling(i));
					Walls.add(getFloor(i));


			}
			break;





		}






		Walls.add(getBackWall(backwall_size));
	}


	public static void main(String [] args)
	{
		MazeProject app = new MazeProject();




	}


}
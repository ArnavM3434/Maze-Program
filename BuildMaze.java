import java.io.*;
import java.util.ArrayList;

public class BuildMaze
{

	private char[][] maze;

	public BuildMaze()
	{

		ArrayList<String> stuff = new ArrayList<String>();

		File fileName = new File("Maze1.txt");


		try
		{
			BufferedReader input = new BufferedReader(new FileReader(fileName));
			String text;

			while((text = input.readLine()) != null)
			{

						stuff.add(text);



			}
			maze = new char[stuff.size()] [stuff.get(0).length() + 1];
			for(int r= 0; r < stuff.size(); r++)
			{

				for(int c = 0; c < stuff.get(r).length(); c++)
				{
					maze[r][c] = stuff.get(r).charAt(c);
				}
			}







			 for (int s = 0; s < maze.length; s++)
			{
				for(int c = 0; c < maze[s].length; c++)
				{
					System.out.print(maze[s][c] + " ");
				}
				System.out.println();
			}

			System.out.println();










		}catch(IOException e)
		{
			System.out.println("File not found.");
		}





	}
	public char[][] getMaze()
	{
		return maze;
	}

	public static void main(String [] args)
	{
		BuildMaze app = new BuildMaze();
	}



}
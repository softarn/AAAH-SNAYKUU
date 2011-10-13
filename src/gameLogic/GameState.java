package gameLogic;

import java.util.Set;
import java.util.HashSet;

/**
 * @author 	Sixten Hilborn
 * @author	Arian Jafari
 * 
 * The GameState is a representation of the game at a given moment in time. It contains references
 * to the game board (containing a matrix of Squares, which in turn contain all game objects),
 * the current Metadata (turns until growth/spawning of fruit, among other things), all snakes
 * participating in this game session, and an ErrorState enum.
 */

public class GameState
{
	private ErrorState errorState = ErrorState.NO_ERROR;
	private Board board;
	private Set<Snake> snakes;
	private Metadata metadata;
	
	GameState(Board currentBoard, Set<Snake> snakes, Metadata metadata) 
	{
		board = new Board(currentBoard);
		this.snakes = new HashSet<Snake>(snakes);
		this.metadata = metadata;
	}
	
	/**
	 * Returns a Set containing all snakes in the game, both dead ones and alive ones.
	 *
	 * @return 	A Set<Snake> containing all snakes.
	 * @see		Snake
	 */
	public Set<Snake> getSnakes()
	{
		return snakes;
	}
	
	/**
	 * Returns a Board object, which constists of a 2D-array of Square objects.
	 *
	 * @return 	A representation of the current game board.
	 * @see		Board
	 */
	public Board getBoard()
	{
		return board;
	}
	
	/**
	 * Method for getting the current game metadata, containing (among other things) 
	 * time until the next fruit spawns and time until snake growth.
	 *
	 * @return 	The current Metadata object.
	 * @see		Metadata
	 */
	public Metadata getMetadata()
	{
		return metadata;
	}

	/**
	 * Returns the ErrorState for the previous turn, for example telling telling a brain it it
	 * took too long to decide last turn.
	 *
	 * @return	The ErrorState object for last turn.
	 * @see 		ErrorState
	 */
	public ErrorState getErrorState()
	{
		return errorState;
	}
	
	
	/**
	 * This method can be used to help calculate whether or not a given snake will collide next
	 * turn if it continues in a given direction. It looks at the square the snake will end up in,
	 * and then checks if that square contains a lethal object. Note that this method returning false 
	 * does NOT guarantee that the snake will survive. For example, it is possible that a two
	 * snakes will move into an empty square during the same turn, causing the death of
	 * them both.
	 * 
	 * @param	snake	The snake you wish you perform the check for.
	 * @param	dir		The hypothetical direction in which the snake moves.
	 * @return 	A boolean: true if the next position contains a lethal object, false if not.
	 */
	public boolean willCollide(Snake snake, Direction dir)
	{
		Position currentHeadPosition = snake.getHeadPosition();
		Position nextHeadPosition = dir.calculateNextPosition(currentHeadPosition);
		return (board.getSquare(nextHeadPosition).isLethal());
	}
	
	/**
	 * This method can be used to calculate the distance between two positions.
	 * 
	 * @param 	from		The position from which you wish to calculate the distance.
	 * @param	to		The position to which you wish to calculate the distance.
	 * @return	An integer representing the distance between two positions.
	 */
	public static int distanceBetween(Position from, Position to)
	{
		int distance = 0;
		
		// Calculate distance in the x-axis
		distance += Math.abs(from.getX() - to.getX());
		
		// Calculate distance in the y-axis
		distance += Math.abs(from.getY() - to.getY());

		return distance;
	}
}

/**
 * Rest Controller for the Alien Mastermind game
 */

package alienmastermind;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AlienMastermindController {

	private List<GameEngine> games = new ArrayList<>();

	@Autowired
	private GameEngineRepository repository;


	/**
	 * Returns the game with the given id
	 * 
	 * @param id	id of the game to get
	 * @return		the game engine with the given id
	 */
	@GetMapping("/games/game/{id}")
	public GameEngine getGame(@PathVariable String id) {
		// System.out.println("reached get endpoint, about to return engine with last peg color of " + engine.getLastPeg().getColor());
		return findGameByID(id);
	}

	
	/**
	 * Creates and returns a new game with the given level
	 * 
	 * @param level	level of the new game
	 * @return		the newly created game engine
	 */
	@PostMapping("/games/new-game/{level}")
	public GameEngine newGame(@PathVariable String level) {
		// System.out.println("reached new-game endpoint in controller");
		GameEngine game = new GameEngine(UUID.randomUUID().toString(), Integer.parseInt(level));
		games.add(game);
		repository.save(game);
		System.out.println("Saved game with id " + game.getGameID() + " to repo");
		return game;
	}


	/**
	 * Adds a peg to the current guess sequence of the game with the given id
	 * 
	 * @param id	id of the game to which to add the peg
	 * @param color	color of the peg to add
	 * @return		the updated game engine
	 */
	@PostMapping("/games/{id}/peg/{color}")
	public GameEngine addPegToSequence(@PathVariable String id, @PathVariable int color) {
		// System.out.println("reached post add peg endpoint with color " + color);
		System.out.println("About to look in repo for game with ID: " + id);
		GameEngine game = findGameByID(id);
		System.out.println("game found has id: " + game.getGameID());
		game.addPegToSeq(color);
		return game;
	}


	/**
	 * Submits or clears the current peg sequence of the game with the given ID
	 * 
	 * @param clear	whether or not to clear the current peg sequence for this game
	 * 
	 * @return	the updated GameEngine
	 */
	@PostMapping("/games/{id}/peg-seq/{clear}")
	public GameEngine submitPegSequence(@PathVariable String id, @PathVariable boolean clear) {
		GameEngine game = findGameByID(id);
		if (clear) { // clear peg sequence
			game.clearCurrentPegSeq();
		}
		else {
			game.submitPegSeq();
		}
		return game;
	}


	/**
	 * Deletes the game with the given id
	 * 
	 * @param id	id of the game to delete
	 */
	@DeleteMapping("/games/{id}")
	public void deleteGame(@PathVariable String id) {
		GameEngine game = findGameByID(id);
		games.remove(game);
		repository.delete(game);
	}


	/**
	 * Finds and returns the game engine with the given id in the list of current games
	 * 
	 * @param id	id of the game to find
	 * @return		the game with the given id, or null if not found
	 */
	private GameEngine findGameByID(String id) {
		// return repository.findByGameID(id);
		for (GameEngine game : games) {
			if (game.getGameID().equals(id)) {
				return game;
			}
		}
		return null;
	}
}
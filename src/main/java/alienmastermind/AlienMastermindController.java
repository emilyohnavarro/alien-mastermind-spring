package alienmastermind;

import java.security.Identity;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AlienMastermindController {

	private List<GameEngine> games = new ArrayList<>();


	@GetMapping("/games/game/{id}")
	public GameEngine getGame(@PathVariable String id) {
		// System.out.println("reached get endpoint, about to return engine with last peg color of " + engine.getLastPeg().getColor());
		return findGameByID(id);
	}

	
	@PostMapping("/games/new-game/{level}")
	public GameEngine newGame(@PathVariable String level) {
		// System.out.println("reached new-game endpoint in controller");
		GameEngine game = new GameEngine(UUID.randomUUID().toString(), Integer.parseInt(level));
		games.add(game);
		return game;
	}


	@PostMapping("/games/{id}/peg/{color}")
	public GameEngine addPegToSequence(@PathVariable String id, @PathVariable int color) {
		// System.out.println("reached post add peg endpoint with color " + color);
		GameEngine game = findGameByID(id);
		game.addPegToSeq(color);
		return game;
	}


	/**
	 * Submits or clears the current peg sequence
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


	private GameEngine findGameByID(String id) {
		for (GameEngine game : games) {
			if (game.getGameID().equals(id)) {
				return game;
			}
		}
		return null;
	}
}
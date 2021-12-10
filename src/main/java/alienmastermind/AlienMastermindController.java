package alienmastermind;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AlienMastermindController {

	private GameEngine engine;


	@GetMapping("/current-game")
	public GameEngine getCurrentGame() {
		// System.out.println("reached get endpoint, about to return engine with last peg color of " + engine.getLastPeg().getColor());
		return engine;
	}

	
	@PostMapping("/new-game/{level}")
	public GameEngine newGame(@PathVariable String level) {
		// System.out.println("reached new-game endpoint in controller");
		engine = new GameEngine(Integer.parseInt(level));
		return engine;
	}


	@PostMapping("/peg/{color}")
	public GameEngine addPegToSequence(@PathVariable int color) {
		// System.out.println("reached post add peg endpoint with color " + color);
		engine.addPegToSeq(color);
		return engine;
	}


	@PostMapping("/peg-seq/{clear}")
	public GameEngine submitPegSequence(@PathVariable boolean clear) {
		if (clear) { // clear peg sequence
			engine.clearCurrentPegSeq();
		}
		else {
			engine.submitPegSeq();
		}
		return engine;
	}
}
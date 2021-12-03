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

	@PostMapping("/new-game/{level}")
	public GameEngine newGame(@PathVariable String level) {
		// System.out.println("reached new-game endpoint in controller");
		engine = new GameEngine(Integer.parseInt(level));
		return engine;
	}


	@GetMapping("/current-game")
	public GameEngine getCurrentGame() {
		// System.out.println("reached get endpoint, about to return engine with last peg color of " + engine.getLastPeg().getColor());
		return engine;
	}


	@PostMapping("/add-peg/{color}")
	public GameEngine addPegToSequence(@PathVariable int color) {
		// System.out.println("reached post add peg endpoint with color " + color);
		engine.addPegToSeq(color);
		return engine;
	}


	@PostMapping("/submit-peg-seq")
	public GameEngine submitPegSequence() {
		// System.out.println("reached post submit peg seq endpoint");
		engine.submitPegSeq();
		return engine;
	}


	@PostMapping("/clear-peg-seq")
	public GameEngine clearPegSequence() {
		// System.out.println("reached post clear peg seq endpoint");
		engine.clearCurrentPegSeq();
		return engine;
	}
}
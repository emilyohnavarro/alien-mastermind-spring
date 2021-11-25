package alienmastermind;

import java.util.concurrent.atomic.AtomicLong;

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
	// private static final String template = "Hello, %s!";
	// private final AtomicLong counter = new AtomicLong();

	// @GetMapping("/greeting")
	// public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
	// 	return new Greeting(counter.incrementAndGet(), String.format(template, name));
	// }

	// @PostMapping("/new-game")
	@PostMapping("/new-game/{level}")
	// public GameEngine newGame(@RequestBody GameEngine newGameEngine) {
	public GameEngine newGame(@PathVariable String level) {
		System.out.println("reached new-game endpoint in controller");
		engine = new GameEngine(Integer.parseInt(level));
		return engine;
	}
	// public Greeting gameenginetest(@RequestParam(value = "name", defaultValue = "World") String name) {
	// 	return new Greeting(counter.incrementAndGet(), String.format(template, name));
	// }


	@GetMapping("/current-game")
	public GameEngine getCurrentGame() {
		System.out.println("reached get endpoint, about to return engine with last peg color of " + engine.getLastPeg().getColor());
		return engine;
	}


	@PostMapping("/add-peg/{color}")
	public GameEngine addPegToSequence(@PathVariable int color) {
		System.out.println("reached post add peg endpoint with color " + color);
		engine.addPegToSeq(color);
		System.out.println("engine.getLastPeg().getColor()" + engine.getLastPeg().getColor());
		return engine;
	}


	// @GetMapping("/greeting")
	// public String greeting(@RequestParam(name="name", required=false, defaultValue="POOFACE") String name, Model model) {
	// 	model.addAttribute("name", name);
	// 	return "greeting";
	// }

}
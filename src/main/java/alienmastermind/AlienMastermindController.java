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
	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	// @GetMapping("/greeting")
	// public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
	// 	return new Greeting(counter.incrementAndGet(), String.format(template, name));
	// }

	// @PostMapping("/new-game")
	@PostMapping("/new-game/{level}")
	// public GameEngine newGame(@RequestBody GameEngine newGameEngine) {
	public GameEngine newGame(@PathVariable String level) {
		System.out.println("reached new-game endpoint in controller");
		return new GameEngine(Integer.parseInt(level));
	}
	// public Greeting gameenginetest(@RequestParam(value = "name", defaultValue = "World") String name) {
	// 	return new Greeting(counter.incrementAndGet(), String.format(template, name));
	// }


	// @GetMapping("/greeting")
	// public String greeting(@RequestParam(name="name", required=false, defaultValue="POOFACE") String name, Model model) {
	// 	model.addAttribute("name", name);
	// 	return "greeting";
	// }

}
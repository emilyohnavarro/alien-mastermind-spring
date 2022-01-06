/**
 * Alien Mastermind main Spring Boot application
 */

package alienmastermind;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AlienMastermind { 

	@Autowired
  	private GameEngineRepository repository;

	  public static void main(String[] args) {
		SpringApplication.run(AlienMastermind.class, args);
	  }

}

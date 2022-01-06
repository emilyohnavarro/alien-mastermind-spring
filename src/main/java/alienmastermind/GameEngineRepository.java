/**
 * MongoDB repository for GameEngines
 */
package alienmastermind;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface GameEngineRepository extends MongoRepository<GameEngine, String> {

  public GameEngine findByGameID(String gameID);

}
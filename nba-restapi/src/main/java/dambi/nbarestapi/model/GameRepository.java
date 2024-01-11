package dambi.nbarestapi.model;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository {
    // GET 
    /** Jolas guztien datuak ikusteko metodoa */
    List<Game> findAllGames();

    /** Jolas baten datuak ikusteko metodoa bere ID-a erabiliz */
    Game findByGameId(String gameId);

    /** Jolas baten datuak ikusteko metodoa bere data erabiliz GARATZEKO */
    List<Game> findByGameDate(String date);


    // DELETE
    /** Jolas bat kentzeko metodoa bere ID-a erabiliz */
    void deleteByGameId(String gameId);

    /** Jolas bat kentzeko metodoa bere data erabiliz */
    void deleteByGameDate(String date);


    // PUT
    /** Jolas bat gordetzeko metodoa */
    Game save(Game game);
}

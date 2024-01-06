package dambi.nbarestapi.model;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository {
    // Jolas guztien datuak ikusteko metodoa
    List<Game> findAllGames();

    // Jolas baten datuak ikusteko metodoa bere ID-a erabiliz
    Game findByGameId(String gameId);

    // Jolas bat kentzeko metodoa bere ID-a erabiliz
    void deleteByGameId(String gameId);

    // Jolas bat gordetzeko metodoa
    Game save(Game game);
}

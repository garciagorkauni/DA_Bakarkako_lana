package dambi.nbarestapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dambi.nbarestapi.model.Game;
import dambi.nbarestapi.model.GameRepository;

@RestController
@RequestMapping(path = "/games")
public class MainController {
	@Autowired
	private GameRepository gameRepository;

	@GetMapping(path = "/all")
	public @ResponseBody Iterable<Game> getAllGames() {
		return gameRepository.findAllGames();
	}

	@GetMapping(path = "/specificgame")
	public @ResponseBody Game getGame(@RequestParam String gameId) {
		return gameRepository.findByGameId(gameId);
	}

	@DeleteMapping(path = "/deletegame")
	public ResponseEntity<String> deleteGame(@RequestParam String gameId) {
		try {
			gameRepository.deleteByGameId(gameId);
			return ResponseEntity.ok().build();

		} catch (Exception ex) {
			System.out.println("There is not any game with the entered ID");
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping(value = "/updatepoints")
	public Game updatePoints(@RequestParam String gameId, @RequestParam int ptsHome, @RequestParam int ptsVisitor) {
		Game game = gameRepository.findByGameId(gameId);
		game.setPts_home(ptsHome);
		game.setPts_visitor(ptsVisitor);
		return gameRepository.save(game);
	}
}
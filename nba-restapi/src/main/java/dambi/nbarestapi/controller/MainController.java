package dambi.nbarestapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dambi.nbarestapi.model.Game;
import dambi.nbarestapi.model.GameRepository;
import dambi.nbarestapi.model.Player;
import dambi.nbarestapi.model.Team;

@RestController
@RequestMapping(path = "/games")
public class MainController {
	@Autowired
	private GameRepository gameRepository;

	// GET
	/** Metodo honek, gordetako game erregistro guztiak bueltatuko du. */
	@GetMapping(path = "/all")
	public @ResponseBody Iterable<Game> getAllGames() {
		return gameRepository.findAllGames();
	}

	/** Metodo honek, game espezifiko baten erregistroa bueltatuko du bere id-an oinarrituta */
	@GetMapping(path = "/specificgameById")
	public @ResponseBody Game getGameById(@RequestParam String gameId) {
		return gameRepository.findByGameId(gameId);
	}

	/** Metodo honek, definitutako data duten game-en erregistroak bueltatuko ditu */
	@GetMapping(path = "/specificgameByDate")
	public @ResponseBody Iterable<Game> getGameByDate(@RequestParam String date) {
		return gameRepository.findByGameDate(date);
	}

	// DELETE
	/** Mehtodo honek, game espezifiko baten erregistroa ezabatuko du bere id-an oinarrituta */
	@DeleteMapping(path = "/deleteGameById")
	public ResponseEntity<String> deleteGame(@RequestParam String gameId) {
		try {
			gameRepository.deleteByGameId(gameId);
			return ResponseEntity.ok().build();

		} catch (Exception ex) {
			System.out.println("There is not any game with the entered ID");
			return ResponseEntity.notFound().build();
		}
	}

	/** Metodo honek, definitutako data duten game-en erregistroak ezabatuko ditu */
	@DeleteMapping(path = "/deleteGameByDate")
	public ResponseEntity<String> deleteGameByDate(@RequestParam String date) {
		try {
			gameRepository.deleteByGameDate(date);
			return ResponseEntity.ok().build();

		} catch (Exception ex) {
			System.out.println("There is not any game with the entered ID");
			return ResponseEntity.notFound().build();
		}
	}

	// PUT
	/** Metodo honek, id-a bitartez definitutako game-aren puntuazioa aldatuko du */
	@PutMapping(value = "/updatePoints")
	public Game updatePoints(@RequestParam String gameId, @RequestParam int ptsHome, @RequestParam int ptsVisitor) {
		Game game = gameRepository.findByGameId(gameId);
		game.setPts_home(ptsHome);
		game.setPts_visitor(ptsVisitor);
		Game emaitza = gameRepository.save(game);
		gameRepository.deleteByGameId(gameId);
		return emaitza;
	}

	/** Metodo honek, id-a bitartez definitutako game-aren data aldatuko du */
	@PutMapping(value = "/updateDate")
	public Game updatePoints(@RequestParam String gameId, @RequestParam String date) {
		Game game = gameRepository.findByGameId(gameId);
		game.setGame_date(date);
		Game emaitza = gameRepository.save(game);
		gameRepository.deleteByGameId(gameId);
		return emaitza;
	}

	/** Metodo honek, id-a bitartez definitutako game-an, definitutako team-an (0-Home/1-Visitor), player bat gehituko du */
	@PutMapping(value = "/createPlayer")
	public Game updatePoints(@RequestParam String gameId,@RequestParam int team, @RequestParam String playerId, @RequestParam String playerName) {
		Game game = gameRepository.findByGameId(gameId);
		Player player;
		switch (team) {
			case 0:
				player = new Player(playerName, game.getHome_team().getTeam_id(), Integer.parseInt(playerId));
				game.getHome_team().getPlayers().add(player);
				break;
		
			case 1:
				player = new Player(playerName, game.getVisitor_team().getTeam_id(), Integer.parseInt(playerId));
				game.getVisitor_team().getPlayers().add(player);
				break;
		}

		Game emaitza = gameRepository.save(game);
		gameRepository.deleteByGameId(gameId);
		return emaitza;
	}

	// POST
	/** Metodo honek, game baten oinarrizko erregistroa sortuko du */
	@PostMapping(path = "/newGame")
	public @ResponseBody String addNewUser(@RequestParam String date, @RequestParam String homeTeamAbbr, @RequestParam String visitorTeamAbbr, @RequestParam int ptsHome, @RequestParam int ptsVisitor) {
		Team homeTeam = new Team(((int)(Math.random()*100000)), homeTeamAbbr, null, null, null);
		Team visitorTeam = new Team(((int)(Math.random()*100000)), visitorTeamAbbr, null, null, null);

		Game game = new Game(date, null, ptsHome, ptsVisitor, homeTeam, visitorTeam);

		gameRepository.save(game);

		return "Game saved";
	}
}
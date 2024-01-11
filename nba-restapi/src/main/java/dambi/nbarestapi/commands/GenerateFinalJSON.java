package dambi.nbarestapi.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import dambi.nbarestapi.model.Game;
import dambi.nbarestapi.model.Player;
import dambi.nbarestapi.model.Team;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenerateFinalJSON {
    public static void main(String[] args) throws CsvValidationException {
        // Ruta osoak aldatu behar dira ekipo bakoitzean (ez nuen lortu ruta erlatiboarekin funtzionaraztea)
        transformAndWriteJSON("C:\\Users\\garcia.gorka\\Documents\\DAM-2\\Datu Atzipena\\DA_Bakarkako_lana\\nba-restapi\\src\\main\\resources\\data_source\\games.csv",
                "C:\\Users\\garcia.gorka\\Documents\\DAM-2\\Datu Atzipena\\DA_Bakarkako_lana\\nba-restapi\\src\\main\\resources\\data_source\\teams.csv",
                "C:\\Users\\garcia.gorka\\Documents\\DAM-2\\Datu Atzipena\\DA_Bakarkako_lana\\nba-restapi\\src\\main\\resources\\data_source\\players.csv",
                "C:\\Users\\garcia.gorka\\Documents\\DAM-2\\Datu Atzipena\\DA_Bakarkako_lana\\nba-restapi\\src\\main\\resources\\data_source\\definitive.json");
    }

    // Metodo nagusia datuak eraldatzeko eta JSON fitxategi definitiboa idazteko
    public static void transformAndWriteJSON(String gamesFile, String teamsFile, String playersFile, String outputFile) throws CsvValidationException {
        try (CSVReader teamsReader = new CSVReader(new FileReader(teamsFile));
            CSVReader playersReader = new CSVReader(new FileReader(playersFile));
            CSVReader gamesReader = new CSVReader(new FileReader(gamesFile))) {
    
            // Mapak sortu datuak era errazago bate erabiltzeko
            Map<Integer, Team> teamsMap = readTeams(teamsReader);
            Map<Integer, Player> playersMap = readPlayers(playersReader);
    
            // JSON konbertsioa egiteko objektuak hasieratu
            ObjectMapper objectMapper = new ObjectMapper();
            FileWriter fileWriter = new FileWriter(outputFile);
    
            // Header-a saltatu
            gamesReader.readNext();
            String[] line;
    
            List<Game> gameList = new ArrayList<>();  // Game guztien lista
    
            while ((line = gamesReader.readNext()) != null) {
                Game game = new Game();
                game.setGame_date(line[0]);
                game.setGame_id(line[1]);
                game.setPts_home(parsePTS(line[7]));
                game.setPts_visitor(parsePTS(line[14]));
    
                Team homeTeam = teamsMap.get(Integer.parseInt(line[3]));
                Team visitorTeam = teamsMap.get(Integer.parseInt(line[4]));
    
                // Jokalarien lista definitu ekipo bakoitzeko (10eko limitearekin programa ez sobrekargatzeko)
                homeTeam.setPlayers(getPlayersForTeam(playersMap, homeTeam.getTeam_id()));
                visitorTeam.setPlayers(getPlayersForTeam(playersMap, visitorTeam.getTeam_id()));
    
                game.setHome_team(homeTeam);
                game.setVisitor_team(visitorTeam);
    
                gameList.add(game);  // Game objektua gorde
            }
    
            // Game objektuen lista JSON bihutu eta fitxategia sortu/idatzi
            objectMapper.writeValue(fileWriter, gameList);
    
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Ekipo bakoitzeko jokalariak bilatzeko metodoa (10-eko limitearekin)
    private static List<Player> getPlayersForTeam(Map<Integer, Player> playersMap, int teamId) {
        List<Player> playersForTeam = new ArrayList<>();
        int playerCount = 0;
        int maxPlayers = 10;
    
        for (Player player : playersMap.values()) {
            if (player.getTeam_id() == teamId) {
                playersForTeam.add(player);
                playerCount++;
    
                if (playerCount > maxPlayers) {
                    break;  // Gelditu limitera heltzerakoan
                }
            }
        }
    
        return playersForTeam;
    }

    // CSV fitxategitik datuak irakurri eta gordetzeko metodoa
    private static Map<Integer, Team> readTeams(CSVReader reader) throws IOException, CsvValidationException, NumberFormatException {
        Map<Integer, Team> teamsMap = new HashMap<>();
        // Header-a saltatu
        reader.readNext();
        String[] line;
        while ((line = reader.readNext()) != null) {
            Team team = new Team();
            team.setTeam_id(Integer.parseInt(line[1]));
            team.setAbbreviation(line[4]);
            team.setCity(line[7]);
            team.setArena(line[8]);
            teamsMap.put(team.getTeam_id(), team);
        }
        return teamsMap;
    }

    // CSV fitxategitik datuak irakurri eta gordetzeko metodoa
    private static Map<Integer, Player> readPlayers(CSVReader reader) throws IOException, CsvValidationException, NumberFormatException {
        Map<Integer, Player> playersMap = new HashMap<>();
        // Header-a saltatu
        reader.readNext();
        String[] line;
        while ((line = reader.readNext()) != null) {
            Player player = new Player();
            player.setPlayer_id(Integer.parseInt(line[2]));
            player.setTeam_id(Integer.parseInt(line[1]));
            player.setPlayer_name(line[0]);
            playersMap.put(player.getPlayer_id(), player);
        }
        return playersMap;
    }

    // Puntuak (PTS) int bihurtzeko metodoa String edo Double formatuan egon daitzeke, gainera, batzuk null dira.
    private static int parsePTS(String value) {
        try {
            return (int) Double.parseDouble(value);
        } catch (Exception e) {
            return 0;
        }
    }
}

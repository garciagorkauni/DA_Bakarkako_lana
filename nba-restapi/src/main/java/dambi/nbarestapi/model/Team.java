package dambi.nbarestapi.model;

import java.util.List;

public class Team {
    // Define class atributes
    private int team_id;
    private String abbreviation;
    private String city;
    private String arena;
    private List<Player> players;

    // Define constructors
    public Team(int team_id, String abbreviation, String city, String arena, List<Player> players) {
        this.team_id = team_id;
        this.abbreviation = abbreviation;
        this.city = city;
        this.arena = arena;
        this.players = players;
    }

    public Team() {
    }

    // Define getters and setters
    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int tEAM_ID) {
        team_id = tEAM_ID;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String aBBREVIATION) {
        abbreviation = aBBREVIATION;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String cITY) {
        city = cITY;
    }

    public String getArena() {
        return arena;
    }

    public void setArena(String aRENA) {
        arena = aRENA;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;  // Corrección aquí
    }
}

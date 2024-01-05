package dambi.nbarestapi.model;

public class Player {
    // Define class atributes
    private String player_name;
    private int team_id;
    private int player_id;

    // Define constructors
    public Player(String player_name, int team_id, int player_id) {
        this.player_name = player_name;
        this.team_id = team_id;
        this.player_id = player_id;
    }

    public Player() {
    }

    // Define getters and setters
    public String getPlayer_name() {
        return player_name;
    }

    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int tEAM_ID) {
        team_id = tEAM_ID;
    }

    public void setPlayer_name(String pLAYER_NAME) {
        player_name = pLAYER_NAME;
    }

    public int getPlayer_id() {
        return player_id;
    }

    public void setPlayer_id(int pLAYER_ID) {
        player_id = pLAYER_ID;
    }

    
}

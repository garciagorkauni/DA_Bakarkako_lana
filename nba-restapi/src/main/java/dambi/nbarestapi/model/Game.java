package dambi.nbarestapi.model;

public class Game {
    // Define class atributes
    private String game_date;
    private String game_id;
    private Team home_team;
    private Team visitor_team;
    private int pts_home;
    private int pts_visitor;

    // Define constructors
    public Game(String game_date, String game_id, int pts_home, int pts_visitor, Team home_team, Team visitor_team) {
        this.game_date = game_date;
        this.game_id = game_id;
        this.pts_home = pts_home;
        this.pts_visitor = pts_visitor;
        this.home_team = home_team;
        this.visitor_team = visitor_team;
    }

    public Game() {
    }

    // Define getters and setters
    public String getGame_date() {
        return game_date;
    }

    public void setGame_date(String game_date) {
        this.game_date = game_date;
    }

    public String getGame_id() {
        return game_id;
    }

    public void setGame_id(String gAME_ID) {
        game_id = gAME_ID;
    }

    public int getPts_home() {
        return pts_home;
    }

    public void setPts_home(int pTS_HOME) {
        pts_home = pTS_HOME;
    }

    public int getPts_visitor() {
        return pts_visitor;
    }

    public void setPts_visitor(int pTS_VISITOR) {
        pts_visitor = pTS_VISITOR;
    }

    public Team getHome_team() {
        return home_team;
    }

    public void setHome_team(Team hOME_TEAM) {
        home_team = hOME_TEAM;
    }

    public Team getVisitor_team() {
        return visitor_team;
    }

    public void setVisitor_team(Team vISITOR_TEAM) {
        visitor_team = vISITOR_TEAM;
    }
}

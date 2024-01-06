package dambi.nbarestapi.model;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mongodb.ReadConcern;
import com.mongodb.ReadPreference;
import com.mongodb.TransactionOptions;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;


import static com.mongodb.client.model.Filters.eq;

@Repository
public class MongoGameRepository implements GameRepository {

    private static final TransactionOptions txnOptions = TransactionOptions.builder()
                                                                        .readPreference(ReadPreference.primary())
                                                                        .readConcern(ReadConcern.MAJORITY)
                                                                        .writeConcern(WriteConcern.MAJORITY)
                                                                        .build();
    @Autowired
    private MongoClient client;
    private MongoCollection<Game> gamesCollection;

    // Methodo honek, dependentziak injektatu ostean exekutatuko da, eta kolekzioarekin konexioa ezarriko du.
    @PostConstruct
    void init() {
        gamesCollection = client.getDatabase("nba").getCollection("games", Game.class);
    }

    // Jolas guztien datuak ikusteko metodoa
    @Override
    public List<Game> findAllGames(String gameId) {
        return gamesCollection.find().into(new ArrayList<>());
    }

    // Jolas baten datuak ikusteko metodoa bere ID-a erabiliz
    @Override
    public Game findByGameId(String gameId) {
        return gamesCollection.find(eq("game_id", new ObjectId(gameId))).first();
    }

    // Jolas bat kentzeko metodoa bere ID-a erabiliz
    @Override
    public void deleteByGameId(String gameId) {
        gamesCollection.deleteOne(eq("game_id", new ObjectId(gameId)));        
    }

    // Jolas baten emaitzak aldatzeko metodoa bere ID-a erabiliz
    @Override
    public void updatePointsByGameId(String gameId, int ptsHome, int ptsVisitor) {
        throw new UnsupportedOperationException("Unimplemented method 'updatePointsByGameId'");
    }
}

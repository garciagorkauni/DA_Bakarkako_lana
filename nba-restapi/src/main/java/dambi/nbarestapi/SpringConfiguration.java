package dambi.nbarestapi;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@Configuration
public class SpringConfiguration {

    // application.properties fitxategitik datuak erabiltzeko
    @Value("${spring.data.mongodb.uri}")
    private String connectionString;

    // MongoClient motatako @Bean-a (Spring kudeatutako objektua) sortuko da.
    @Bean
    public MongoClient mongoClient() {
        // POJO eta BSON arteko konbertsion burutzeko codecs-ak (konbertsioak definitzen duten objektu motak)
        CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());

        // Default eta POJO-ko codecs-ak elkartzen dira
        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);

        // MongoClient objektua konfiguratzen da codecs-ak eta URI-a erabiltzen
        return MongoClients.create(MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .codecRegistry(codecRegistry)
                .build());
    }

}

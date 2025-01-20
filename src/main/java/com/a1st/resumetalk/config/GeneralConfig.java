package com.a1st.resumetalk.config;


import dev.langchain4j.chain.ConversationalRetrievalChain;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.model.embedding.AllMiniLmL6V2EmbeddingModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.cassandra.AstraDbEmbeddingConfiguration;
import dev.langchain4j.store.embedding.cassandra.AstraDbEmbeddingStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: Abderrahman Youabd aka: A1ST
 * @version: 1.0
 */

@Configuration
public class GeneralConfig {

    private static final String TOKEN = "your_token";
    private static final String DATABASE_ID = "your_database_id";
    private static final String REGION = "your_region";
    private static final String KEYSPACE = "your_keyspace";
    private static final String TABLE = "your_table";
    private static final String BASE_URL = "provider_base_url";
    private static final String API_KEY = "your_api_key";
    private static final String MODEL_NAME = "your_model_name";


    @Bean
    public EmbeddingModel embeddingModel() {
        return new AllMiniLmL6V2EmbeddingModel();
    }

    @Bean
    public AstraDbEmbeddingStore astraDbEmbeddingStore() {

        return new AstraDbEmbeddingStore(AstraDbEmbeddingConfiguration
                .builder()
                .token(TOKEN)
                .databaseId(DATABASE_ID)
                .databaseRegion(REGION)
                .keyspace(KEYSPACE)
                .table(TABLE)
                .dimension(384)
                .build());
    }


    @Bean
    public EmbeddingStoreIngestor embeddingStoreIngestor() {
        return EmbeddingStoreIngestor.builder()
                .documentSplitter(DocumentSplitters.recursive(300, 0))
                .embeddingModel(embeddingModel())
                .embeddingStore(astraDbEmbeddingStore())
                .build();
    }

    @Bean
    public ConversationalRetrievalChain conversationalRetrievalChain() {
        return ConversationalRetrievalChain.builder()
                .chatLanguageModel(OpenAiChatModel.builder()
                        .baseUrl(BASE_URL)
                        .apiKey(API_KEY)
                        .modelName(MODEL_NAME)
                        .build()
                ).build();
    }
}
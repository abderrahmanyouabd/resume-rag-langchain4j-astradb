package com.a1st.resumetalk.config;


import dev.langchain4j.chain.ConversationalRetrievalChain;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.model.embedding.AllMiniLmL6V2EmbeddingModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.retriever.EmbeddingStoreRetriever;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.cassandra.AstraDbEmbeddingConfiguration;
import dev.langchain4j.store.embedding.cassandra.AstraDbEmbeddingStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: Abderrahman Youabd aka: A1ST
 * @version: 1.0
 */

@Configuration
public class GeneralConfig {

    @Value("${astra.db.token}")
    private String TOKEN = "your_token";
    @Value("${astra.db.databaseId}")
    private String DATABASE_ID = "your_database_id";
    @Value("${astra.db.region}")
    private String REGION = "your_region";
    @Value("${astra.db.keyspace}")
    private String KEYSPACE = "your_keyspace";
    @Value("${astra.db.table}")
    private String TABLE = "your_table";
    @Value("${groq.chat.base-url}")
    private String BASE_URL = "provider_base_url";
    @Value("${groq.chat.api-key}")
    private String API_KEY = "your_api_key";
    @Value("${groq.chat.model-name}")
    private String MODEL_NAME = "your_model_name";


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
                ).retriever(EmbeddingStoreRetriever.from(astraDbEmbeddingStore(), embeddingModel()))
                .build();
    }
}
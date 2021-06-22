package com.doff.leo.esapi.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EsService {

    public static class Article{

        private String title, text;


        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
    private final static String INDEX_NAME = "articles";
    private final ObjectMapper mapper= new ObjectMapper();
    private final RestHighLevelClient esClient;
    public EsService(RestHighLevelClient esClient) {
        this.esClient = esClient;
    }
    public void updateArticle(String id, String title, String text){
        Article article = new Article();
        article.setTitle(title);
        article.setText(text);

        IndexRequest indexRequest = new IndexRequest(INDEX_NAME);
        indexRequest.id(id);
        try {
            indexRequest.source(mapper.writeValueAsString(article), XContentType.JSON);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        try {
            esClient.index(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

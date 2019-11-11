package com.example.demo.service;

import com.example.demo.model.TestModel;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.time.Instant;

@Service
@Slf4j
public class TestService {

    @Autowired
    RestHighLevelClient restHighLevelClient;

    public Mono<Void> checkIn(TestModel testModel) {
        IndexRequest indexRequest = new IndexRequest("reporter_checkin");
        XContentBuilder xContentBuilder = null;
        try {
            xContentBuilder = XContentFactory.jsonBuilder().startObject()
                    .field("place", testModel.getPlace())
                    .field("address", testModel.getAddress())
                    .field("reporter", testModel.getReporter())
                    .field("date", Instant.now())
                    .startObject("location")
                    .field("lon", testModel.getLocation().getLon())
                    .field("lat", testModel.getLocation().getLat())
                    .endObject()
                    .endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        indexRequest.source(xContentBuilder);
        return call(indexRequest);
    }

    public Mono<Void> searchPlace(TestModel testModel) {
        IndexRequest indexRequest = new IndexRequest("reporter_search");
        XContentBuilder xContentBuilder = null;
        try {
            xContentBuilder = XContentFactory.jsonBuilder().startObject()
                    .field("keyword", testModel.getKeyword())
                    .field("date", Instant.now())
                    .endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        indexRequest.source(xContentBuilder);
        return call(indexRequest);
    }

    public Mono<Void> question(TestModel testModel) {
        IndexRequest indexRequest = new IndexRequest("reporter_question");
        XContentBuilder xContentBuilder = null;
        try {
            xContentBuilder = XContentFactory.jsonBuilder().startObject()
                    .field("place", testModel.getPlace())
                    .field("question", testModel.getQuestion())
                    .field("date", Instant.now())
                    .endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        indexRequest.source(xContentBuilder);
        return call(indexRequest);
    }


    private Mono<Void> call(IndexRequest indexRequest) {
        return Mono.create(sink -> {
            ActionListener<IndexResponse> actionListener = new ActionListener<IndexResponse>() {
                @Override
                public void onResponse(IndexResponse indexResponse) {
                    sink.success();
                }

                @Override
                public void onFailure(Exception e) {
                    e.printStackTrace();
                }
            };
            restHighLevelClient.indexAsync(indexRequest, RequestOptions.DEFAULT, actionListener);
        });
    }
}

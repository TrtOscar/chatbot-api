package com.trt.chatbot.api.test;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;

/**
 * @description 单元测试
 */
public class ApiTest {
    @Test
    public void query_unanswered_questions() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpGet get = new HttpGet("https://api.zsxq.com/v2/groups/48884584528518/topics?scope=unanswered_questions&count=20");

        get.addHeader("cookie", "abtest_env=product; zsxq_access_token=C9D9F8B2-3C56-65F5-7BF1-0C9CA4D0D92C_2823DDBADC4F920F; zsxqsessionid=7beeb0c4cc06ebbec873887f3e5ecbc8; sensorsdata2015jssdkcross={\"distinct_id\":\"818111845581852\",\"first_id\":\"1823b74ff2a7e0-0ebc6f87c2c1b4-76492e29-2073600-1823b74ff2b382\",\"props\":{\"$latest_traffic_source_type\":\"直接流量\",\"$latest_search_keyword\":\"未取到值_直接打开\",\"$latest_referrer\":\"\"},\"identities\":\"eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMTgyM2I3NGZmMmE3ZTAtMGViYzZmODdjMmMxYjQtNzY0OTJlMjktMjA3MzYwMC0xODIzYjc0ZmYyYjM4MiIsIiRpZGVudGl0eV9sb2dpbl9pZCI6IjgxODExMTg0NTU4MTg1MiJ9\",\"history_login_id\":{\"name\":\"$identity_login_id\",\"value\":\"818111845581852\"},\"$device_id\":\"1823b74ff2a7e0-0ebc6f87c2c1b4-76492e29-2073600-1823b74ff2b382\"}");
        get.addHeader("Content-Type", "application/json; charset=UTF-8");

        CloseableHttpResponse response = httpClient.execute(get);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res);
        } else {
            System.out.println(response.getStatusLine().getStatusCode());
        }
    }

    @Test
    public void answer() throws IOException {

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        /*48884584528518*/
        /*214488548525411*/
        /*814488884885212*/
        HttpPost post = new HttpPost("https://api.zsxq.com/v2/topics/584411114112524/answer");
        post.addHeader("cookie", "abtest_env=product; zsxq_access_token=C9D9F8B2-3C56-65F5-7BF1-0C9CA4D0D92C_2823DDBADC4F920F; zsxqsessionid=7beeb0c4cc06ebbec873887f3e5ecbc8; sensorsdata2015jssdkcross={\"distinct_id\":\"818111845581852\",\"first_id\":\"1823b74ff2a7e0-0ebc6f87c2c1b4-76492e29-2073600-1823b74ff2b382\",\"props\":{\"$latest_traffic_source_type\":\"直接流量\",\"$latest_search_keyword\":\"未取到值_直接打开\",\"$latest_referrer\":\"\"},\"identities\":\"eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMTgyM2I3NGZmMmE3ZTAtMGViYzZmODdjMmMxYjQtNzY0OTJlMjktMjA3MzYwMC0xODIzYjc0ZmYyYjM4MiIsIiRpZGVudGl0eV9sb2dpbl9pZCI6IjgxODExMTg0NTU4MTg1MiJ9\",\"history_login_id\":{\"name\":\"$identity_login_id\",\"value\":\"818111845581852\"},\"$device_id\":\"1823b74ff2a7e0-0ebc6f87c2c1b4-76492e29-2073600-1823b74ff2b382\"}");
        post.addHeader("Content-Type", "application/json; charset=UTF-8");

        String paramJson = "{\n" +
                "  \"req_data\": {\n" +
                "    \"text\": \"是的捏\\n\",\n" +
                "    \"image_ids\": [],\n" +
                "    \"silenced\": true\n" +
                "  }\n" +
                "}";

        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json", "UTF-8"));
        post.setEntity(stringEntity);
        CloseableHttpResponse response = httpClient.execute(post);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res);
        } else {
            System.out.println(response.getStatusLine().getStatusCode());
        }
    }

    @Test
    public void test_chatGPT() throws IOException{
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpPost post=new HttpPost("https://api.openai.com/v1/chat/completions");
        post.addHeader("Content-Type"," application/json");
        post.addHeader("Authorization","Bearer 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");

        String paramJson="{\n" +
                "     \"model\": \"gpt-3.5-turbo\",\n" +
                "     \"messages\": [{\"role\": \"user\", \"content\": \"帮我写一个Java冒泡排序\"}],\n" +
                "     \"temperature\": 0.7\n" +
                "   }";

        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json", "UTF-8"));
        post.setEntity(stringEntity);
        CloseableHttpResponse response = httpClient.execute(post);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res);
        } else {
            System.out.println(response.getStatusLine().getStatusCode());
        }
    }

}

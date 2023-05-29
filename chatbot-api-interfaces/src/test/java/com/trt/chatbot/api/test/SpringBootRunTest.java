package com.trt.chatbot.api.test;

import com.alibaba.fastjson.JSON;
import com.trt.chatbot.api.domain.zsxq.IZsxqApi;
import com.trt.chatbot.api.domain.zsxq.model.aggregates.UnAnsweredQuestionAggregates;
import com.trt.chatbot.api.domain.zsxq.model.vo.Question;
import com.trt.chatbot.api.domain.zsxq.model.vo.Topics;
import com.trt.chatbot.api.domain.zsxq.service.ZsxqApi;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class SpringBootRunTest {

    private Logger logger = LoggerFactory.getLogger(SpringBootRunTest.class);

    @Value("${chatbot-api.groupId}")
    private String groupId;
    @Value("${chatbot-api.cookie}")
    private String cookie;

    @Resource
    private IZsxqApi zsxqApi;

    @Test
    public void test_zsxqApi() throws IOException {
        UnAnsweredQuestionAggregates unAnsweredQuestionAggregates = zsxqApi.queryUnAnsweredQuestionTopicID(groupId, cookie);
        logger.info("测试结果：{}", JSON.toJSON(unAnsweredQuestionAggregates));

        List<Topics> topics = unAnsweredQuestionAggregates.getResp_data().getTopics();
        for (Topics topic : topics) {
            String topicId = topic.getTopic_id();
            String text = topic.getQuestion().getText();
            logger.info("topicId：{} text：{}", topicId, text);

            //回答问题
            zsxqApi.answer(groupId,cookie,topicId,text,false);
        }
    }
}

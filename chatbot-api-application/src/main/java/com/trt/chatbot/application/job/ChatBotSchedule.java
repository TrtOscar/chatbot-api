package com.trt.chatbot.application.job;

import com.alibaba.fastjson.JSON;
import com.trt.chatbot.api.domain.ai.IOpenAI;
import com.trt.chatbot.api.domain.zsxq.IZsxqApi;
import com.trt.chatbot.api.domain.zsxq.model.aggregates.UnAnsweredQuestionAggregates;
import com.trt.chatbot.api.domain.zsxq.model.vo.Topics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

@EnableScheduling
@Configuration
public class ChatBotSchedule {
    private Logger logger = LoggerFactory.getLogger(ChatBotSchedule.class);

    @Value("${chatbot-api.groupId}")
    private String groupId;
    @Value("${chatbot-api.cookie}")
    private String cookie;
    @Value("${chatbot-api.openAiKey}")
    private String openAiKey;

    @Resource
    private IZsxqApi zsxqApi;
    @Resource
    private IOpenAI openAI;

    @Scheduled(cron = "0 0/1  * * * ?")
    public void run() {
        try {
            if (new Random().nextBoolean()) {
                logger.info("随机开摆...");
                return;
            }

            GregorianCalendar calendar = new GregorianCalendar();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            if (hour > 22 || hour < 4) {
                logger.info("AI下班了");
                return;
            }

            //1.检索问题
            UnAnsweredQuestionAggregates unAnsweredQuestionAggregates = zsxqApi.queryUnAnsweredQuestionTopicID(groupId, cookie);
            logger.info("问题检索：{}", JSON.toJSON(unAnsweredQuestionAggregates));
            List<Topics> topics = unAnsweredQuestionAggregates.getResp_data().getTopics();
            if (null == topics || topics.isEmpty()) {
                logger.info("本次检索未查询到待回答问题");
                return;
            }
            //2.AI回答
            Topics topic = topics.get(0);
            String answer = openAI.doChatGPT(openAiKey, topic.getQuestion().getText().trim());
            //3.问题回复
            boolean status = zsxqApi.answer(groupId, cookie, topic.getTopic_id(), answer, false);
            logger.info("编号:{}问题:{}回答:{}状态:{}", topic.getTopic_id(), topic.getQuestion().getText(), answer, status);

        } catch (Exception e) {
            logger.error("自动回答异常", e);
        }
    }
}

package com.trt.chatbot.api.domain.zsxq;

import com.trt.chatbot.api.domain.zsxq.model.aggregates.UnAnsweredQuestionAggregates;

import java.io.IOException;

/**
 * @author Oscar
 * @description 知识星球Api接口
 */
public interface IZsxqApi {
    UnAnsweredQuestionAggregates queryUnAnsweredQuestionTopicID(String groupId, String cookie) throws IOException;
    boolean answer(String groupId,String cookie,String topicId,String text,boolean silenced) throws IOException;
}

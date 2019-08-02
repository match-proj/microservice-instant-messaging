package com.match.oim.context.domain.repostory;

import com.match.oim.context.domain.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @Author zhangchao
 * @Date 2019/4/25 15:33
 * @Version v1.0
 */
@Repository
public interface ConversationRepository extends JpaRepository<Conversation,String> {


    @Query(nativeQuery = true , value = "select c.id from conversation c left join conversation_user l on c.id = l.conversation_id join conversation_user r on c.id = r.conversation_id where l.message_user_id = :selfMessageUserId and r.message_user_id  = :firendsMessageUserId")
    String getConversationBySelfAndFirends(@Param("selfMessageUserId") String selfMessageUserId, @Param("firendsMessageUserId") String firendsMessageUserId);
}

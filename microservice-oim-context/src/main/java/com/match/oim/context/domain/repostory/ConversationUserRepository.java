package com.match.oim.context.domain.repostory;

import com.match.oim.context.domain.entity.ConversationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author zhangchao
 * @Date 2019/4/25 15:33
 * @Version v1.0
 */
@Repository
public interface ConversationUserRepository extends JpaRepository<ConversationUser,String> {

    List<ConversationUser> findAllByMessageUserId(String messageUserId);

    ConversationUser findByMessageUserIdAndConversationId(String messageUserId, String conversationId);
}

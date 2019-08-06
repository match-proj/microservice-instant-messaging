package com.match.oim.context.domain.repostory;

import com.match.oim.context.domain.entity.MessageFirends;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author zhangchao
 * @Date 2019/4/25 15:33
 * @Version v1.0
 */
@Repository
public interface MessageFirendsRepository extends JpaRepository<MessageFirends,String> {

    List<MessageFirends> findAllByMyMessageUserId(String myMessageUserId);

    Boolean existsByMyMessageUserIdAndFirendsMessageUserId(String myMessageUserId, String firendsMessageUserId);

}

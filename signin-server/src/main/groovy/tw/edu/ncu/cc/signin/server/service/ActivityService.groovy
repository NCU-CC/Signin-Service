package tw.edu.ncu.cc.signin.server.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tw.edu.ncu.cc.signin.server.model.Activity

interface ActivityService {
    Activity create( Activity activity )
    Activity findBySerialId(String serialId)
    Page< Activity > findAllByCreatorId( String creatorId, Pageable pageable )
    void delete( Activity activity )
}
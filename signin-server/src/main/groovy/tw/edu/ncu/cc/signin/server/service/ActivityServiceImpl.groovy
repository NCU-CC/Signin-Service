package tw.edu.ncu.cc.signin.server.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import tw.edu.ncu.cc.signin.server.model.ActivityRepository
import tw.edu.ncu.cc.signin.server.model.Activity

@Service
class ActivityServiceImpl implements ActivityService {

    @Autowired
    def ActivityRepository activityRepository

    @Override
    Activity create( Activity activity ) {
        activity.serialId = UUID.randomUUID().toString()
        activityRepository.save( activity )
    }

    @Override
    Activity findBySerialId( String serialId ) {
        activityRepository.findBySerialId( serialId )
    }

    @Override
    Page< Activity > findAllByCreatorId( String creatorId, Pageable pageable ) {
        activityRepository.findByCreatorIdOrderByDateCreatedDesc( creatorId, pageable )
    }

    @Override
    void delete( Activity activity ) {
        activityRepository.delete( activity.id )
    }
}

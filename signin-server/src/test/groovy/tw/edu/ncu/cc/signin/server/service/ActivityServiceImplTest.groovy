package tw.edu.ncu.cc.signin.server.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.transaction.annotation.Transactional
import specification.SpringSpecification
import tw.edu.ncu.cc.signin.server.model.Activity

class ActivityServiceImplTest extends SpringSpecification {

    @Autowired
    private ActivityService activityService

    @Transactional
    def "it can create activity"() {
        when:
            def activity = activityService.create( new Activity(
                    name: "ACT0",
                    dateStarted: new Date(),
                    dateEnded: new Date(),
                    creatorId: "USER0"
            ) )
        then:
            activity.id != 0
            activity.dateCreated != null
    }

    @Transactional
    def "it can find activity by serial id"() {
        when:
            def activity = activityService.findBySerialId( "SID1" )
        then:
            activity != null
            activity.serialId == "SID1"
    }

    @Transactional
    def "it can find activities by creator id"() {
        when:
            def activities = activityService.findAllByCreatorId( "USER1", new PageRequest( 0, 15 ) ).content
        then:
            activities.size() == 1
            activities[0].creatorId == "USER1"
    }
}

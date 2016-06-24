package tw.edu.ncu.cc.signin.server.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.transaction.annotation.Transactional
import specification.SpringSpecification
import tw.edu.ncu.cc.signin.server.model.Activity
import tw.edu.ncu.cc.signin.server.model.SigninRepository

class ActivityServiceImplTest extends SpringSpecification {

    @Autowired
    ActivityService activityService

    @Autowired
    SigninRepository signinRepository

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
    def "it can update activity"() {
        given:
            def activity = activityService.findBySerialId( "SID1" )
        when:
            activity.name = "test name"
        and:
            activityService.update( activity )
        then:
            def activityDb = activityService.findBySerialId( "SID1" )
        and:
            activityDb.dateUpdated != null
            activityDb.name == "test name"
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

    @Transactional
    def "it can delete activities with its signins"() {
        given:
            def activity = activityService.findBySerialId( "SID1" )
        when:
            activityService.delete( activity )
        then:
            signinRepository.findByActivitySerialIdOrderByDateCreatedDesc( activity.serialId, new PageRequest( 0, 20 ) ).content.size() == 0
    }
}

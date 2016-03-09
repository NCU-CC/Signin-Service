package tw.edu.ncu.cc.signin.server.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.transaction.annotation.Transactional
import specification.SpringSpecification

class SigninServiceImplTest extends SpringSpecification {

    @Autowired
    def SigninService signinService

    @Autowired
    def ActivityService activityService

    @Transactional
    def "it can create signin"() {
        given:
            def activity = activityService.findBySerialId( "SID1" )
        when:
            def signin = signinService.create( "NEWUSER", activity )
        then:
            signin.activity.id == activity.id
            signin.userId == "NEWUSER"
            signin.dateCreated != null
    }

    @Transactional
    def "it can find signins by creator id"() {
        when:
            def signins = signinService.findAllByActivitySerialId( "SID3", new PageRequest( 0, 15 ) ).content
        then:
            signins.size() == 1
            signins[0].userId == "USER8"
    }

}

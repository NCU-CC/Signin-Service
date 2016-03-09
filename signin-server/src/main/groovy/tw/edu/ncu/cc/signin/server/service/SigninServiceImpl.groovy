package tw.edu.ncu.cc.signin.server.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import tw.edu.ncu.cc.signin.server.model.Activity
import tw.edu.ncu.cc.signin.server.model.Signin
import tw.edu.ncu.cc.signin.server.model.SigninRepository

@Service
class SigninServiceImpl implements SigninService {

    @Autowired
    def SigninRepository signinRepository

    @Override
    Signin create( String userId, Activity activity  ) {
        signinRepository.save( new Signin(
                userId: userId,
                activity: activity
        ) )
    }

    @Override
    Page< Signin > findAllByActivitySerialId( String activitySerialId, Pageable pageable ) {
        signinRepository.findByActivitySerialIdOrderByDateCreatedDesc( activitySerialId, pageable )
    }
}

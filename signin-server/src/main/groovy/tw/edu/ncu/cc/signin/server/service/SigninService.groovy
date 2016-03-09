package tw.edu.ncu.cc.signin.server.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tw.edu.ncu.cc.signin.server.model.Activity
import tw.edu.ncu.cc.signin.server.model.Signin

interface SigninService {
    Signin create( String userId, Activity activity )
    Page< Signin > findAllByActivitySerialId( String activitySerialId, Pageable pageable )
}
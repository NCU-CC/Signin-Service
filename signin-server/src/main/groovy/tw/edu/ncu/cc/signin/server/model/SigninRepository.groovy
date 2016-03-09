package tw.edu.ncu.cc.signin.server.model

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

public interface SigninRepository extends JpaRepository< Signin, Integer >, JpaSpecificationExecutor< Signin > {

    Page< Signin > findByActivitySerialIdOrderByDateCreatedDesc( String activitySerialId, Pageable pageable )

}

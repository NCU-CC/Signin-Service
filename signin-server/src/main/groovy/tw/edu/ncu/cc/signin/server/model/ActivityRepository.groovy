package tw.edu.ncu.cc.signin.server.model

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

public interface ActivityRepository extends JpaRepository< Activity, Integer >, JpaSpecificationExecutor< Activity > {

    Activity findBySerialId( String serialId )

    Page< Activity > findByCreatorIdOrderByDateCreatedDesc( String creatorId, Pageable pageable )

}

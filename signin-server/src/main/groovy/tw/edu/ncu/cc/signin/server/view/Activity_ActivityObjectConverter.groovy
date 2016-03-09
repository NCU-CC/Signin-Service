package tw.edu.ncu.cc.signin.server.view

import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import tw.edu.ncu.cc.signin.server.model.Activity
import tw.edu.ncu.cc.signin.data.v1.ActivityObject

@Component
class Activity_ActivityObjectConverter implements Converter< Activity, ActivityObject > {

    @Override
    ActivityObject convert(Activity source ) {
        ActivityObject activityObject = new ActivityObject();
        activityObject.serialId = source.serialId
        activityObject.name = source.name
        activityObject.dateStarted = source.dateStarted
        activityObject.dateEnded = source.dateEnded
        activityObject.dateCreated = source.dateCreated
        activityObject.creatorId = source.creatorId
        activityObject
    }

}

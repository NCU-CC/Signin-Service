package tw.edu.ncu.cc.signin.server.validator

import org.springframework.validation.Errors
import org.springframework.validation.ValidationUtils
import org.springframework.validation.Validator
import tw.edu.ncu.cc.signin.data.v1.ActivityObject

public class ActivityCreateValidator implements Validator {

    @Override
    public boolean supports( Class< ? > clazz ) {
        return ActivityObject.class.equals( clazz )
    }

    @Override
    public void validate( Object target, Errors errors ) {
        ValidationUtils.rejectIfEmpty( errors, "name", "name.necessary", "name is necessary" )
    }

}

package tw.edu.ncu.cc.signin.server.validator

import org.springframework.validation.Errors
import org.springframework.validation.ValidationUtils
import org.springframework.validation.Validator
import tw.edu.ncu.cc.signin.data.v1.ActivityObject
import tw.edu.ncu.cc.signin.data.v1.SigninObject

public class SigninCreateValidator implements Validator {

    @Override
    public boolean supports( Class< ? > clazz ) {
        return SigninObject.class.equals( clazz )
    }

    @Override
    public void validate( Object target, Errors errors ) {
        ValidationUtils.rejectIfEmpty( errors, "userId", "userId.necessary", "userId is necessary" )
    }

}

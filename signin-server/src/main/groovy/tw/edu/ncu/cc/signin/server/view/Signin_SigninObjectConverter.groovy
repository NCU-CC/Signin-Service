package tw.edu.ncu.cc.signin.server.view

import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import tw.edu.ncu.cc.signin.data.v1.ActivityObject
import tw.edu.ncu.cc.signin.data.v1.SigninObject
import tw.edu.ncu.cc.signin.server.model.Activity
import tw.edu.ncu.cc.signin.server.model.Signin

@Component
class Signin_SigninObjectConverter implements Converter< Signin, SigninObject > {

    @Override
    SigninObject convert(Signin source ) {
        SigninObject signinObject = new SigninObject();
        signinObject.userId = source.userId
        signinObject.dateCreated = source.dateCreated
        signinObject
    }

}

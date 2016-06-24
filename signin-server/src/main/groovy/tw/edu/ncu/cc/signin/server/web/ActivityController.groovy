package tw.edu.ncu.cc.signin.server.web

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.convert.ConversionService
import org.springframework.core.convert.TypeDescriptor
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.validation.BindException
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.HttpServerErrorException
import tw.edu.ncu.cc.signin.server.service.ActivityService
import tw.edu.ncu.cc.signin.server.service.SigninService
import tw.edu.ncu.cc.signin.data.v1.ActivityObject
import tw.edu.ncu.cc.signin.data.v1.PageMetaObject
import tw.edu.ncu.cc.signin.data.v1.PageObject
import tw.edu.ncu.cc.signin.server.model.Activity
import tw.edu.ncu.cc.signin.server.validator.ActivityCreateValidator
import tw.edu.ncu.cc.signin.data.v1.SigninObject
import tw.edu.ncu.cc.signin.server.model.Signin

@RestController
@RequestMapping( value = "v1/activities" )
public class ActivityController {

    @Autowired
    def ConversionService conversionService

    @Autowired
    def ActivityService activityService

    @Autowired
    def SigninService signinService

    @InitBinder
    public static void initBinder( WebDataBinder binder ) {
        binder.addValidators( new ActivityCreateValidator() )
    }

    @RequestMapping( method = RequestMethod.GET )
    def index( Authentication authentication, Pageable pageable ) {

        def activityPages = activityService.findAllByCreatorId( authentication.name, pageable )

        def activityObjects = conversionService.convert(
                activityPages.content,
                TypeDescriptor.collection( List.class, TypeDescriptor.valueOf( Activity.class ) ),
                TypeDescriptor.array( TypeDescriptor.valueOf( ActivityObject.class ) )
        )

        return toPageObjects( activityObjects, activityPages )
    }

    @RequestMapping( value = "{serial_id}", method = RequestMethod.GET )
    def show( @PathVariable( "serial_id" ) final String serialId ) {

        def activity = activityService.findBySerialId( serialId )

        if( activity == null ) {
            throw new HttpServerErrorException( HttpStatus.NOT_FOUND, "required resource is not found" )
        }

        conversionService.convert(
                activity, ActivityObject.class
        )
    }

    @ResponseStatus( HttpStatus.NO_CONTENT )
    @RequestMapping( value = "{serial_id}", method = RequestMethod.DELETE )
    def destroy( @PathVariable( "serial_id" ) final String serialId, Authentication authentication ) {

        def activity = activityService.findBySerialId( serialId )

        if( activity == null ) {
            throw new HttpServerErrorException( HttpStatus.NOT_FOUND, "required resource is not found" )
        }

        if( activity.creatorId != authentication.name ) {
            throw new HttpServerErrorException( HttpStatus.FORBIDDEN, "required operation not allowed for anybody except creator" )
        }

        activityService.delete( activity )
    }

    @RequestMapping( value = "{serial_id}", method = RequestMethod.PUT )
    def update( @PathVariable( "serial_id" ) final String serialId, @RequestBody final ActivityObject activityObject,  Authentication authentication ) {

        def activity = activityService.findBySerialId( serialId )

        if( activity == null ) {
            throw new HttpServerErrorException( HttpStatus.NOT_FOUND, "required resource is not found" )
        }

        if( activity.creatorId != authentication.name ) {
            throw new HttpServerErrorException( HttpStatus.FORBIDDEN, "required operation not allowed for anybody except creator" )
        }

        activity.name = activityObject.name
        activity.dateStarted = activityObject.dateStarted
        activity.dateEnded = activityObject.dateEnded

        def updatedActivity = activityService.update( activity )

        conversionService.convert(
                updatedActivity, ActivityObject.class
        )
    }

    @ResponseStatus( HttpStatus.CREATED )
    @RequestMapping( method = RequestMethod.POST )
    def create( @Validated @RequestBody final ActivityObject activityObject, BindingResult bindingResult, Authentication authentication ) {

        if( bindingResult.hasErrors() ) {
            throw new BindException( bindingResult )
        }

        def activity = activityService.create( new Activity(
                name: activityObject.name,
                dateStarted: activityObject.dateStarted,
                dateEnded: activityObject.dateEnded,
                creatorId: authentication.name
        ) )

        conversionService.convert(
                activity, ActivityObject.class
        )
    }

    @RequestMapping( value = "{serial_id}/sign_in", method = RequestMethod.POST )
    def doSignin( @PathVariable( "serial_id" ) final String serialId, Authentication authentication ) {

        def activity = activityService.findBySerialId( serialId )

        if( activity == null ) {
            throw new HttpServerErrorException( HttpStatus.NOT_FOUND, "required resource is not found" )
        }

        def signin = signinService.create( authentication.name, activity )

        conversionService.convert(
                signin, SigninObject.class
        )
    }

    @RequestMapping( value = "{serial_id}/sign_in", method = RequestMethod.GET )
    def showSignin( @PathVariable( "serial_id" ) final String serialId, Pageable pageable ) {

        def activity = activityService.findBySerialId( serialId )

        if( activity == null ) {
            throw new HttpServerErrorException( HttpStatus.NOT_FOUND, "required resource is not found" )
        }

        def signinPages = signinService.findAllByActivitySerialId( serialId, pageable )

        def signinObjects = conversionService.convert(
                signinPages.content,
                TypeDescriptor.collection( List.class, TypeDescriptor.valueOf( Signin.class ) ),
                TypeDescriptor.array( TypeDescriptor.valueOf( SigninObject.class ) )
        )

        return toPageObjects( signinObjects, signinPages )
    }

    private static <T> PageObject<T> toPageObjects( Object content, Page<T> page ) {
        new PageObject< T >(
                content: content,
                pageMetadata: new PageMetaObject(
                        size: page.size,
                        totalElements: page.totalElements,
                        totalPages: page.totalPages,
                        number: page.number
                )
        )
    }

}
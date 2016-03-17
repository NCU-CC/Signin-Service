package tw.edu.ncu.cc.signin.server.web

import org.springframework.http.MediaType
import org.springframework.transaction.annotation.Transactional
import specification.IntegrationSpecification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static tw.edu.ncu.cc.oauth.resource.test.ApiAuthMockMvcRequestPostProcessors.accessToken


class ActivityControllerTest extends IntegrationSpecification {

    def targetURL = "/v1/activities"

    @Transactional
    def "user can get owned activities"() {
        when:
            def response = JSON(
                    server().perform(
                            get( targetURL ).with( accessToken().user( "USER1" ).scope( "test" ) )
                    ).andExpect(
                            status().isOk()
                    ).andReturn()
            )
        then:
            response.content.size() == 1
            response.content[0].name == "ACT1"
            response.content[0].serialId == "SID1"
            response.content[0].creatorId == "USER1"
    }

    @Transactional
    def "user can get activity information by seiral id"() {
        when:
            def response = JSON(
                    server().perform(
                            get( targetURL + "/SID1" ).with( accessToken().user( "USER1" ).scope( "test" ) )
                    ).andExpect(
                            status().isOk()
                    ).andReturn()
            )
        then:
            response.serialId == "SID1"
            response.creatorId == "USER1"
            response.name == "ACT1"
    }

    @Transactional
    def "user cannot get activity information by seiral id if not exist"() {
        expect:
            server().perform(
                    get( targetURL + "/SID0" ).with( accessToken().user( "USER1" ).scope( "test" ) )
            ).andExpect(
                    status().isNotFound()
            )
    }

    @Transactional
    def "user can delete activity with its signins by seiral id"() {
        when:
            server().perform(
                    delete( targetURL + "/SID1" ).with( accessToken().user( "USER1" ).scope( "test" ) )
            ).andExpect(
                    status().isNoContent()
            )
        then:
            server().perform(
                    get( targetURL + "/SID1/sign_in" ).with( accessToken().user( "USER1" ).scope( "test" ) )
            ).andExpect(
                    status().isNotFound()
            )
    }

    @Transactional
    def "user cannot delete activity with its signins by seiral id when activity not exist"() {
        expect:
            server().perform(
                    delete( targetURL + "/SID00" ).with( accessToken().user( "USER1" ).scope( "test" ) )
            ).andExpect(
                    status().isNotFound()
            )
    }

    @Transactional
    def "user cannot delete activity with its signins by seiral id when user is not creator"() {
        expect:
            server().perform(
                    delete( targetURL + "/SID1" ).with( accessToken().user( "USER2" ).scope( "test" ) )
            ).andExpect(
                    status().isForbidden()
            )
    }

    @Transactional
    def "user can create new activity"() {
        when:
            def response = JSON(
                    server().perform(
                            post( targetURL )
                            .with( accessToken().user( "USER1" ).scope( "test" ) )
                            .contentType( MediaType.APPLICATION_JSON )
                            .content(
                                """
                                {
                                    "name":"TEST",
                                    "date_started":"2016-01-01",
                                    "date_ended":"2016-01-01"
                                }
                                """
                            )
                    ).andExpect(
                            status().isCreated()
                    ).andReturn()
            )
        then:
            response.serialId != null
            response.creatorId == "USER1"
    }

    @Transactional
    def "user cannot create new activity if name not provided"() {
        expect:
            server().perform(
                    post( targetURL )
                            .with( accessToken().user( "USER1" ).scope( "test" ) )
                            .contentType( MediaType.APPLICATION_JSON )
                            .content(
                            """
                                    {
                                        "date_started":"2016-01-01",
                                        "date_ended":"2016-01-01"
                                    }
                                    """
                    )
            ).andExpect(
                    status().isBadRequest()
            )
    }

    @Transactional
    def "user can do sign in"() {
        when:
            def response = JSON(
                    server().perform(
                            post( targetURL + "/SID1/sign_in" )
                                .with( accessToken().user( "USER1" ).scope( "test" ) )
                    ).andExpect(
                            status().isOk()
                    ).andReturn()
            )
        then:
            response.userId == "USER1"
    }

    @Transactional
    def "user cannot do sign in if activity not exist"() {
        expect:
            server().perform(
                    post( targetURL + "/SID00/sign_in" )
                        .with( accessToken().user( "USER1" ).scope( "test" ) )
            ).andExpect(
                    status().isNotFound()
            )
    }

    @Transactional
    def "user can get signin lists"() {
        when:
            def response = JSON(
                    server().perform(
                            get( targetURL + "/SID1/sign_in" )
                                .with( accessToken().user( "USER1" ).scope( "test" ) )
                    ).andExpect(
                            status().isOk()
                    ).andReturn()
            )
        then:
            response.content.size() == 2
    }
}
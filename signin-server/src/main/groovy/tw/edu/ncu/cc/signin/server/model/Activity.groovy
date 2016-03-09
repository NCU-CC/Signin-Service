package tw.edu.ncu.cc.signin.server.model

import javax.persistence.*

@Entity
class Activity implements Serializable {

    @Id
    @GeneratedValue
    def Integer id

    @Column( nullable = false )
    def String serialId

    @Column( nullable = false )
    def String name

    @Column( nullable = false )
    def String creatorId

    @Version
    def Integer version = 0

    @Temporal( value = TemporalType.TIMESTAMP )
    def Date dateStarted

    @Temporal( value = TemporalType.TIMESTAMP )
    def Date dateEnded

    @Temporal( value = TemporalType.TIMESTAMP )
    def Date dateCreated

    @PrePersist
    protected void onCreate() {
        dateCreated = new Date()
    }

}

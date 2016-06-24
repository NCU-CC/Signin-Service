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

    @Temporal( value = TemporalType.TIMESTAMP )
    def Date dateUpdated

    @OneToMany( mappedBy = "activity", cascade = [ CascadeType.REMOVE ] )
    @OrderBy( "date_created desc" )
    def List< Signin > signins = new LinkedList<>()

    @PrePersist
    protected void onCreate() {
        dateCreated = new Date()
    }

    @PreUpdate
    protected void onUpdate() {
        dateUpdated = new Date()
    }

}

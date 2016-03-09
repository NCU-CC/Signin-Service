package tw.edu.ncu.cc.signin.server.model

import javax.persistence.*

@Entity
class Signin implements Serializable {

    @Id
    @GeneratedValue
    def Integer id

    @Column( nullable = false )
    def String userId

    @Version
    def Integer version = 0

    @Temporal( value = TemporalType.TIMESTAMP )
    def Date dateCreated

    @JoinColumn
    @ManyToOne( optional = false )
    def Activity activity

    @PrePersist
    protected void onCreate() {
        dateCreated = new Date()
    }

}

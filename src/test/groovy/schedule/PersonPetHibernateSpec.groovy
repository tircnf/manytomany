package schedule

import grails.persistence.Entity
import grails.test.hibernate.HibernateSpec

class PersonPetHibernateSpec extends HibernateSpec {

    @Override
    List<Class> getDomainClasses() {
        return [Person, Pet]
    }

    def setup() {
    }

    def cleanup() {
    }

    void "test something"() {
        expect:"fix me"
        new Person(name: "steve").save(flush:true, failOnError:true)
        new Pet(name: "spot").save(flush:true, failOnError:true)
        Person.count()==1
        Pet.count()==1
    }

    void "Expect associations work"() {
        given: "A Person and Pet"
        Person steve = new Person(name: "steve").save(flush:true, failOnError:true)
        Pet spot = new Pet(name: "spot").save(flush:true, failOnError:true)

        when: "I associate them"
        steve.addToPets(spot)
        println "Saving... updates the version number on both person and pet, but doesn't write the association data"
        steve.save(flush:true, failOnError:true)
        println "Flush done."

        then: "It got persisted in memory"
        steve.pets.size()==1
        steve.pets[0].name=="spot"

        when: "if I reload from database"
        sessionFactory.currentSession.clear()
        Person steve2 = Person.get(steve.id)

        then: "those relationships are there"
        steve2.pets.size()==1
        steve2.pets[0].name=="spot"
    }
}

@Entity
class Person {
    String name
    static hasMany = [pets: Pet]
}

@Entity
class Pet {
    String name

    static hasMany = [owners: Person]
}

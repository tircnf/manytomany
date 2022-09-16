many-to-many relationships.

back in the days of grails 2, if you misconfigured a many-to-many relationship and
forgot to add the belongsTo static property to one side, you got a nice little exception
explaining what was wrong.

`No owner defined between domain classes [class manytomay.Person] and [class manytomay.Pet] in a many-to-many relationship. Example: static belongsTo = manytomay.Pet`

The application would fail to start.



Currently in gorm.version=7.3.2, no error is thrown.
If you try and add any relationships between person and pet, they silently don't save.

Here is the data mapping: (missing the belongsTo)

    
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


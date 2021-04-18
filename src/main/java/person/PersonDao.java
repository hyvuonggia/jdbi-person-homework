package person;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RegisterBeanMapper(Person.class)
public interface PersonDao {


    @SqlUpdate("""
    CREATE TABLE persontable (
        id INTEGER PRIMARY KEY,
        name VARCHAR ,
        birthDate DATE ,
        gender VARCHAR(6),
        email VARCHAR ,
        phone VARCHAR ,
        profession VARCHAR ,
        married INTEGER 
        )
        """)
    void createPersonTable();


//    @SqlUpdate("""
//    INSERT INTO persontable VALUES (:id, :name, :birthDate, :gender, :email, :phone, :profession, :married);
//    """)
//    void insertPerson(@Bind("id") int id, @Bind("name") String name, @Bind("birthDate") LocalDate birthDate,
//                      @Bind("gender") String gender, @Bind("email") String email, @Bind("phone") String phone,
//                      @Bind("profession") String profession, @Bind("married") boolean married) ;


    @SqlUpdate("""
    INSERT INTO persontable VALUES (:id, :name, :birthDate, :gender, :email, :phone, :profession, :married)
    """)
    void insertPerson(@BindBean Person person);

    @SqlQuery("""
    SELECT * FROM persontable WHERE id = :id
    """)
    Optional<Person> getPerson(@Bind("id") int id);

    @SqlUpdate("""
    DELETE FROM persontable WHERE id = :id
    """)
    void deletePerson(@Bind("id") int id);

    @SqlQuery("""
    SELECT * FROM persontable ORDER BY id  
    """)
    List<Person> listPersons();

}

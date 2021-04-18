package person;

import com.github.javafaker.Faker;
import com.github.javafaker.Options;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Jdbi jdbi = Jdbi.create("jdbc:h2:mem:test");
        jdbi.installPlugin(new SqlObjectPlugin());

        try (Handle handle = jdbi.open()) {
            PersonDao dao = handle.attach(PersonDao.class);
            dao.createPersonTable();

            System.out.print("Input number of persons you want to create: ");
            int n = Integer.parseInt(scanner.nextLine());
            for (int i = 1; i <= n; i++) {
                dao.insertPerson(createPerson(i));
//                System.out.println(dao.getPerson(i).get());
            }


            List<Person> personList = dao.listPersons();
            for (Person person : personList) {
                System.out.println(person);
            }
            System.out.println();


            System.out.print("Input the id of a person you want to delete: ");
            dao.deletePerson(Integer.parseInt(scanner.nextLine()));
            List<Person> updatedPersonList = dao.listPersons();
            for (Person person : updatedPersonList) {
                System.out.println(person);
            }
            System.out.println();


            System.out.print("Input the id of a person you want to find: ");
            System.out.println(dao.getPerson(Integer.parseInt(scanner.nextLine())).get());
        }
    }

    static Person createPerson(int id) {
        var person = Person.builder()
                .id(id)
                .name(new Faker().name().fullName())
                .birthDate(dateToLocalDate(new Faker().date().birthday()))
                .gender(new Faker().options().option(Person.Gender.values()))
                .email(new Faker().internet().emailAddress())
                .phone(new Faker().phoneNumber().phoneNumber())
                .profession(new Faker().company().profession())
                .married(new Faker().bool().bool())
                .build();
        return person;

    }

    static LocalDate dateToLocalDate(Date date) {
        return LocalDate.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }
}

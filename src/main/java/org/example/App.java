package org.example;

import org.example.model.Passport;
import org.example.model.Person;
import org.example.model.Principal;
import org.example.model.School;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class App {
    public static void main(String[] args) {
        Configuration configuration = new Configuration().addAnnotatedClass(Principal.class)
                .addAnnotatedClass(School.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

            //////////////// 2
            Principal principal = session.get(Principal.class, 1);
            System.out.println(principal);

            //////////////// 3
            School school = session.get(School.class, 1);
            System.out.println(school.getPrincipal());

            //////////////// 4
            Principal newPrincipal = new Principal("Test", 33);
            School newSchool = new School(newPrincipal, 123456);

            newPrincipal.setSchool(newSchool);

            session.save(newPrincipal);

            //////////////// 5
            Principal newPrincipalForExistingSchool = new Principal("test", 55);
            School existingSchool = session.get(School.class, 2);
            System.out.println(newPrincipalForExistingSchool);
            System.out.println(existingSchool);

            existingSchool.setPrincipal(newPrincipalForExistingSchool);

            System.out.println(newPrincipalForExistingSchool);
            System.out.println(existingSchool);

            session.getTransaction().commit();

        } finally {
            sessionFactory.close();
        }
    }
}

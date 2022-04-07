package main.java.com.ahh.school.hibernateutils;

import main.java.com.ahh.school.entity.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtils {
    private static SessionFactory sessionFactory;

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration config = new Configuration().configure("main/resources/hibernate.cfg.xml")
                    .addAnnotatedClass(User.class)
                    .addAnnotatedClass(SchoolClass.class)
                    .addAnnotatedClass(Teacher.class)
                    .addAnnotatedClass(Student.class)
                    .addAnnotatedClass(Subject.class)
                    .addAnnotatedClass(PayAmount.class);
            ServiceRegistry reg = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
            SessionFactory sf = config.buildSessionFactory(reg);
            return sf;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            synchronized (HibernateUtils.class) {
                if (sessionFactory == null) {
                    sessionFactory = buildSessionFactory();
                }
            }
        }
        return sessionFactory;
    }
}

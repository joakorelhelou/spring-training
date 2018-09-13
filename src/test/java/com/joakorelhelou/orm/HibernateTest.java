package com.joakorelhelou.orm;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

abstract class HibernateTest {

    protected static final String H2_CONFIG_FILE = "h2.hibernate.cfg.xml";

    private static SessionFactory factory;

    //Sessions in Hibernate are not thread safe, therefore we need to store it in ThreadLocal to use it
    //in a multi thread environment
    private static ThreadLocal<Session> sessions;

    HibernateTest(String properties, Class... klasses) {
        Configuration config = new Configuration();

        for (Class klass : klasses) {
            config.addAnnotatedClass(klass);
        }

        factory = config
                .configure(properties)
                .buildSessionFactory();
        sessions = ThreadLocal.withInitial(factory::openSession);
    }

    Long saveEntity(MyEntity entity) {
        Long id;
        try (Session session = sessions.get()) {
            Transaction tx = session.beginTransaction();
            session.save(entity);
            session.flush();
            tx.commit();
            id = entity.getId();
        }
        sessions.remove();
        return id;
    }

    @SuppressWarnings("SameParameterValue")
    <T> T queryById(Class<T> entity, Long id) {
        T queriedEntity;
        try (Session session = sessions.get()) {
            queriedEntity = session.get(entity, id);
        }
        sessions.remove();
        return queriedEntity;
    }

    @SuppressWarnings({"unchecked", "SameParameterValue"})
    <T> List<T> query(String theQuery) {
        List<T> queriedList;
        try (Session session = sessions.get()) {
            queriedList = session.createQuery(theQuery).getResultList();
        }
        sessions.remove();
        return queriedList;
    }

    void updateQuery(String theQuery) {
        try (Session session = sessions.get()) {
            Transaction tx = session.beginTransaction();
            session.createQuery(theQuery).executeUpdate();
            session.flush();
            tx.commit();
        }
        sessions.remove();
    }

    void update(MyEntity entity) {
        try (Session session = sessions.get()) {
            Transaction tx = session.beginTransaction();
            session.update(entity);
            session.flush();
            tx.commit();
        }
        sessions.remove();
    }

    void delete(MyEntity entity) {
        try (Session session = sessions.get()) {
            Transaction tx = session.beginTransaction();
            session.delete(entity);
            session.flush();
            tx.commit();
        }
        sessions.remove();
    }

    static void closeConn(){
        factory.close();
    }

}

package main.java.com.ahh.school.dao;

import main.java.com.ahh.school.hibernateutils.HibernateUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.TypedQuery;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class AbstractDaoImpl<T, ID extends Comparable<ID>> implements AbstractDao<T, ID>{
    private Class<T> entityName;
    private Session session = null;
    private Transaction transaction = null;

    public AbstractDaoImpl() {
        this.entityName = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }


    @Override
    public T findOne(ID id) {
        T entity = null;
        try {
            startOperation();
            entity = session.get(entityName, id);
//            TypedQuery<T> query = session.createQuery("from " + entityName.getSimpleName() + " WHERE id = " + id);
//            entity = query.getSingleResult();
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
        } finally {
            session.close();
        }
        return entity;
    }


    @Override
    public boolean save(T entity) {
        boolean flag = false;
        try {
            startOperation();
            session.saveOrUpdate(entity);
            transaction.commit();
            flag = true;
        } catch (HibernateException e) {
            transaction.rollback();
        } finally {
            session.close();
        }
        return flag;
    }


    @Override
    public List<T> fetchAll() {
        List<T> data = null;
        try {
            startOperation();
            TypedQuery<T> query = session.createQuery("from " + entityName.getName());
            data = query.getResultList();
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
        } finally {
            session.close();
        }
        return data;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T update(T entity) {
        try {
            startOperation();
            entity = (T) session.merge(entity);
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
        } finally {
            session.close();
        }
        return entity;
    }

    @Override
    public boolean delete(T entity) {
        boolean flag = false;
        try {
            startOperation();
            session.delete(entity);
            transaction.commit();
            flag = true;
        } catch (HibernateException e) {
            transaction.rollback();
        } finally {
            session.close();
        }
        return flag;
    }


    @Override
    public boolean deleteById(ID id) {
        T entity = findOne(id);
        return delete(entity);
    }

    @Override
    public List<T> fetchAllByName(String name) {
        List<T> data = null;
        try {
            startOperation();
            TypedQuery<T> query = session.createQuery("from " + entityName.getSimpleName() + " WHERE name LIKE \"%"+name+"%\"");
            data = query.getResultList();
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
        } finally {
            session.close();
        }
        return data;
    }

    private void startOperation() {
        session = HibernateUtils.getSessionFactory().openSession();
        transaction = session.beginTransaction();
    }
}

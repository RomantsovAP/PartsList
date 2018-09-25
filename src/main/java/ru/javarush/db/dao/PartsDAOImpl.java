package ru.javarush.db.dao;

import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;
import ru.javarush.db.entity.Part;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Repository
public class PartsDAOImpl implements PartsDAO {
    private Configuration cfg = new org.hibernate.cfg.Configuration().configure("hibernate.cfg.xml");
    private int howManyRecordsByQuery = 1;

    @Override
    public void dataForDB() {
        try (Session session = cfg.buildSessionFactory().openSession()) {
            session.beginTransaction();
            for (int i = 0; i < 250; i++)
                session.createSQLQuery("INSERT INTO part (title, enabled, amount) VALUES (:title, :enabled, :amount);")
                        .setParameter("title", "part_" + (100 + i))
                        .setParameter("enabled", enabled())
                        .setParameter("amount", rnd()).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean enabled() {
        return rnd() > 50 ? true : false;
    }

    private int rnd() {
        return new Random().nextInt(100);
    }

    @Override
    public void deletePart(int id) {
        try (Session session = cfg.buildSessionFactory().openSession()) {
            session.beginTransaction();
            Part part = session.get(Part.class, id);
            session.remove(part);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addPart(Part part) {
        try (Session session = cfg.buildSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(part);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updatePart(Part part) {
        try (Session session = cfg.buildSessionFactory().openSession()) {
            session.beginTransaction();
            session.update(part);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Кроме основного запроса на отбор нужных данных,
     * используется и второй запрос на подсчет общего числа строк в БД соответвующих запросу.
     */
    @Override
    public List<Part> getParts(String sql) {
        List<Part> parts = new ArrayList();
        try (Session session = cfg.buildSessionFactory().openSession()) {
            NativeQuery query = session.createNativeQuery(sql);
            query.addEntity(Part.class);
            parts = query.list();
            query = session.createNativeQuery("SELECT FOUND_ROWS();");
            howManyRecordsByQuery = Integer.parseInt(query.list().get(0).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return parts;
    }

    @Override
    public void changeEnabledStatus(int id) {
        try (Session session = cfg.buildSessionFactory().openSession()) {
            session.beginTransaction();
            Part part = session.get(Part.class, id);
            part.setEnabled(!part.isEnabled());
            session.update(part);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getHowManyRecordsByQuery() {
        return howManyRecordsByQuery;
    }

}

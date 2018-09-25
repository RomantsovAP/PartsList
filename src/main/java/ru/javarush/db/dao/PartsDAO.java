package ru.javarush.db.dao;

        import ru.javarush.db.entity.Part;

        import java.util.List;


public interface PartsDAO {

    void dataForDB();

    void deletePart(int id);

    void addPart(Part part);

    void updatePart(Part part);

    List<Part> getParts(String sql);

    void changeEnabledStatus(int id);

    int getHowManyRecordsByQuery();
}

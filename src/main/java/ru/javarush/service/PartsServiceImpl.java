package ru.javarush.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javarush.db.dao.PartsDAO;
import ru.javarush.db.entity.Part;
import ru.javarush.service.utility.IntFromString;

import java.util.List;

import static ru.javarush.service.PartsService.FilterEnum.ACTIVE;
import static ru.javarush.service.PartsService.FilterEnum.DISABLED;
import static ru.javarush.service.PartsService.FilterEnum.NONE;

@Service
public class PartsServiceImpl implements PartsService {

    @Autowired
    private PartsDAO partsDAO;
    @Autowired
    private IntFromString intFromString;
    private List<Part> parts;
    private int currentSelectedPage = 1;
    private int amountPagesByQuery = 1;
    private FilterEnum filter = NONE;
    private int limitOfRecordsOnPage = 10;

    /**
     * Метод распознает текущий фильтр (из строки в enum).
     */
    private FilterEnum recognizeCurrentFilter(String filter) {
        if ((filter != null) && (filter.equals("ACTIVE"))) return ACTIVE;
        else if ((filter != null) && (filter.equals("DISABLED"))) return DISABLED;
        else return NONE;
    }

    /**
     * Метод меняет фильтр на следующий (NONE → ACTIVE → DISABLED → NONE).
     */
    private FilterEnum setNewFilter() {
        currentSelectedPage = 1; // иначе после применения фильтра можно остаться на не адекватной странице
        if (filter == NONE) return ACTIVE;
        if (filter == ACTIVE) return DISABLED;
        else return NONE;
    }

    /**
     * Метод возвращает список запчастей с учетом фильтра, условием поиска, и на выбранной пользователем странице.
     */
    @Override
    public List<Part> getParts(String currentFilter, String newFilter, String search, String pageStr) {
        recognizePage(pageStr);

        filter = recognizeCurrentFilter(currentFilter);
        if ((newFilter != null) && (!newFilter.equals("")))
            filter = setNewFilter();

        parts = partsDAO.getParts("SELECT SQL_CALC_FOUND_ROWS * FROM part " + where() + searchStr(search) + " LIMIT " + begin() + ", " + limitOfRecordsOnPage + ";");

        amountPagesByQuery = (int) (Math.ceil(partsDAO.getHowManyRecordsByQuery() / 10.0));
        return parts;
    }

    /**
     * Метод нужен чтобы определить, какой диапазон записей из БД показывать на странице.
     *
     * @param pageStr номер страницы
     */
    private void recognizePage(String pageStr) {
        if ((pageStr != null) && (!pageStr.equals("")))
            currentSelectedPage = intFromString.recognize(pageStr);
    }

    /**
     * В зависимости от значения переменной filter метод вернет часть sql запроса
     * для фильтрации данных при отборе их из БД.
     */
    private String where() {
        if (filter == ACTIVE)
            return "WHERE (enabled=1)";
        else if (filter == DISABLED)
            return "WHERE (enabled=0)";
        else
            return "WHERE (enabled=1 OR enabled=0) ";
    }

    /**
     * Если входящая строка (@param search) содержит какие-то данные,
     * то метод вернет (@return) часть sql запроса для их поиска в БД.
     */
    private String searchStr(String search) {
        String searchStr = "";
        if ((search != null) && (!search.equals("")))
            searchStr = "AND title REGEXP '" + search + "'";
        return searchStr;
    }

    @Override
    public void delete(String id) {
        if ((id != null) && (!id.equals("")))
            partsDAO.deletePart(intFromString.recognize(id));
    }

    @Override
    public void add(Part part) {
        partsDAO.addPart(part);
    }

    /**
     * Метод добавляет новую запчасть в БД.
     *
     * @param addTitle   название запчасти
     * @param addEnabled ее необходимость для (текущей) сборки
     * @param addAmount  количество в наличии
     */
    @Override
    public void add(String addTitle, String addEnabled, String addAmount) {
        if ((addTitle != null) && (!addTitle.equals(""))) {
            Part part = new Part();
            part.setTitle(addTitle);
            if ((addEnabled != null) && (addEnabled.equals("on")))
                part.setEnabled(true);
            if ((addAmount != null) && (!addAmount.equals("")))
                part.setAmount(intFromString.recognize(addAmount));
            add(part);
        }
    }

    @Override
    public void changeEnabledStatus(String id) {
        if ((id != null) && (!id.equals("")))
            partsDAO.changeEnabledStatus(intFromString.recognize(id));
    }

    @Override
    public void update(Part part) {
        partsDAO.updatePart(part);
    }

    @Override
    public void update(String updateID, String updateTitle, boolean saveEnabled, String updateAmount) {
        if ((updateID != null) && (!updateID.equals(""))) {
            Part part = new Part();
            part.setId(intFromString.recognize(updateID));
            part.setTitle(updateTitle);
            part.setEnabled(saveEnabled);
            part.setAmount(intFromString.recognize(updateAmount));
            update(part);
        }
    }


    private List<Part> getAllParts(){
        return partsDAO.getParts("SELECT SQL_CALC_FOUND_ROWS * FROM part;");
    }

    /**
     * Подсчет числа компьютеров, которые можно собрать из имеющихся запчастей.
     * Осуществляется через поиск наименьшего числа деталей в перечне.
     */
    @Override
    public int min() {
        int min = 0;
        for (Part p : getAllParts())
            if (p.isEnabled() && ((min == 0) || (p.getAmount() < min)))
                min = p.getAmount();
        return min;
    }

    private int begin() {
        return (currentSelectedPage - 1) * limitOfRecordsOnPage;
    }

    @Override
    public int getAmountPagesByQuery() {
        return amountPagesByQuery;
    }

    @Override
    public FilterEnum getFilter() {
        return filter;
    }

    @Override
    public int currentSelectedPage() {
        return currentSelectedPage;
    }
}

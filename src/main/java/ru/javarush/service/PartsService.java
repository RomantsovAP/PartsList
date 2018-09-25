package ru.javarush.service;

import ru.javarush.db.entity.Part;

import java.util.List;

public interface PartsService {

    int currentSelectedPage();

    enum FilterEnum {NONE, DISABLED, ACTIVE}

    List<Part> getParts(String filter, String newFilter, String search, String page);

    void delete(String id);

    void changeEnabledStatus(String id);

    void update(Part part);

    void add(Part part);

    void add(String addTitle, String addEnabled, String addAmount);

    void update(String updateID, String updateTitle, boolean saveEnabled, String updateAmount);

    int min();

    int getAmountPagesByQuery();

    FilterEnum getFilter();
}

package ru.javarush.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ru.javarush.db.entity.Part;
import ru.javarush.service.PartsService;

import java.util.List;

@Controller
public class PartsController {
    @Autowired
    private PartsService partsService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String partsListWithFilters(
            @RequestParam(value = "page", required = false) String page,
            @RequestParam(value = "deleteID", required = false) String deleteID,
            @RequestParam(value = "activateID", required = false) String activateID,
            @RequestParam(value = "editID", required = false) String editID,
            @RequestParam(value = "updateID", required = false) String updateID,
            @RequestParam(value = "updateTitle", required = false) String updateTitle,
            @RequestParam(value = "updateAmount", required = false) String updateAmount,
            @RequestParam(value = "saveEnabled", required = false) boolean saveEnabled,
            @RequestParam(value = "searchTitle", required = false) String searchTitle,
            @RequestParam(value = "filterField", required = false) String filter,
            @RequestParam(value = "addNewPart", required = false) String addNewPart,
            @RequestParam(value = "addTitle", required = false) String addTitle,
            @RequestParam(value = "addEnabled", required = false) String addEnabled,
            @RequestParam(value = "addAmount", required = false) String addAmount,
            @RequestParam(value = "newFilter", required = false) String newFilter,
            Model model) {
        partsService.changeEnabledStatus(activateID);
        partsService.update(updateID, updateTitle, saveEnabled, updateAmount);
        partsService.delete(deleteID);
        partsService.add(addTitle, addEnabled, addAmount);

        List<Part> parts = partsService.getParts(filter, newFilter, searchTitle, page);

        model.addAttribute("parts", parts);
        model.addAttribute("page", partsService.currentSelectedPage());
        model.addAttribute("pagesCalc", partsService.getAmountPagesByQuery());
        model.addAttribute("sborka", partsService.min());
        model.addAttribute("editIDInt", editID);
        model.addAttribute("filter", partsService.getFilter());
        model.addAttribute("addNewPart", addNewPart);
        model.addAttribute("searchTitle", searchTitle);
        return "index";
    }

}

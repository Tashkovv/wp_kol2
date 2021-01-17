package mk.ukim.finki.wp.kol2021.restaurant.web;

import mk.ukim.finki.wp.kol2021.restaurant.model.Menu;
import mk.ukim.finki.wp.kol2021.restaurant.model.MenuType;
import mk.ukim.finki.wp.kol2021.restaurant.service.MenuItemService;
import mk.ukim.finki.wp.kol2021.restaurant.service.MenuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MenuController {

    private final MenuService service;
    private final MenuItemService menuItemService;

    public MenuController(MenuService service, MenuItemService menuItemService) {
        this.service = service;
        this.menuItemService = menuItemService;
    }

    @GetMapping({"/", "/menu"})
    public String showMenus(@RequestParam(required = false) String nameSearch, @RequestParam(required = false) MenuType menuType, Model model) {
        List<Menu> menus = null;
        if (nameSearch == null && menuType == null) {
            menus = service.listAll();
        } else {
            menus = this.service.listMenuItemsByRestaurantNameAndMenuType(nameSearch,  menuType);
        }

        model.addAttribute("menus", menus);
        List<MenuType> menuTypes = new ArrayList<>();
        menuTypes.add(MenuType.COFFEE);
        menuTypes.add(MenuType.PIZZA);
        menuTypes.add(MenuType.COOKIE);

        model.addAttribute("menuTypes", menuTypes);

        return "list.html";
    }

    @GetMapping("/menu/add")
    public String showAdd(Model model) {

        List<MenuType> menuTypes = new ArrayList<>();
        menuTypes.add(MenuType.COFFEE);
        menuTypes.add(MenuType.PIZZA);
        menuTypes.add(MenuType.COOKIE);

        model.addAttribute("menuTypes", menuTypes);
        model.addAttribute("menuItems", this.menuItemService.listAll());

        return "form.html";
    }

    @GetMapping("/menu/{id}/edit")
    public String showEdit(@PathVariable Long id, Model model) {
        Menu menu = this.service.findById(id);

        List<MenuType> menuTypes = new ArrayList<>();
        menuTypes.add(MenuType.COFFEE);
        menuTypes.add(MenuType.PIZZA);
        menuTypes.add(MenuType.COOKIE);

        model.addAttribute("menuTypes", menuTypes);
        model.addAttribute("menuItems", this.menuItemService.listAll());
        model.addAttribute("menu", menu);

        return "form.html";
    }

    @PostMapping("/menu")
    public String create(@RequestParam String name,
                         @RequestParam MenuType menuType,
                         @RequestParam List<Long> menuItemIds) {
        this.service.create(name, menuType, menuItemIds);
        return "redirect:/menu";
    }

    @PostMapping("/menu/{id}")
    public String update(@PathVariable Long id,
                         @RequestParam String name,
                         /*@RequestParam String description,*/
                         @RequestParam MenuType menuType,
                         @RequestParam List<Long> menuItemIds) {
        this.service.update(id, name, menuType, menuItemIds);
        return "redirect:/menu";
    }

    @PostMapping("/menu/{id}/delete")
    public String delete(@PathVariable Long id) {
        this.service.delete(id);
        return "redirect:/menu";
    }
}

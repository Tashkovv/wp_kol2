package mk.ukim.finki.wp.kol2021.restaurant.service.impl;

import mk.ukim.finki.wp.kol2021.restaurant.model.Menu;
import mk.ukim.finki.wp.kol2021.restaurant.model.MenuItem;
import mk.ukim.finki.wp.kol2021.restaurant.model.MenuType;
import mk.ukim.finki.wp.kol2021.restaurant.model.exceptions.InvalidMenuIdException;
import mk.ukim.finki.wp.kol2021.restaurant.repository.MenuItemRepository;
import mk.ukim.finki.wp.kol2021.restaurant.repository.MenuRepository;
import mk.ukim.finki.wp.kol2021.restaurant.service.MenuService;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final MenuItemRepository menuItemRepository;

    public MenuServiceImpl(MenuRepository menuRepository, MenuItemRepository menuItemRepository) {
        this.menuRepository = menuRepository;
        this.menuItemRepository = menuItemRepository;
    }


    @Override
    public List<Menu> listAll() {
        return this.menuRepository.findAll();
    }

    @Override
    public Menu findById(Long id) {
        Menu menu = this.menuRepository.findById(id).orElseThrow(InvalidMenuIdException::new);
        return menu;
    }

    @Override
    public Menu create(String restaurantName, MenuType menuType, List<Long> menuItems) {
        List<MenuItem> menuItemList = this.menuItemRepository.findAllById(menuItems);
        Menu menu = new Menu(restaurantName, menuType, menuItemList);
        return this.menuRepository.save(menu);
    }

    @Override
    public Menu update(Long id, String restaurantName, MenuType menuType, List<Long> menuItems) {
        List<MenuItem> menuItemList = this.menuItemRepository.findAllById(menuItems);
        Menu menu = this.menuRepository.findById(id).orElseThrow(InvalidMenuIdException::new);

        menu.setRestaurantName(restaurantName);
        menu.setMenuType(menuType);
        menu.setMenuItems(menuItemList);

        return this.menuRepository.save(menu);
    }

    @Override
    public Menu delete(Long id) {
        Menu menu = this.menuRepository.findById(id).orElseThrow(InvalidMenuIdException::new);
        this.menuRepository.delete(menu);
        return menu;
    }

    @Override
    public List<Menu> listMenuItemsByRestaurantNameAndMenuType(String restaurantName, MenuType menuType) {
        List<Menu> result = null;
        if (restaurantName != null && menuType != null){
            return this.menuRepository.findAllByRestaurantNameLikeAndMenuType("%" + restaurantName + "%", menuType);
        }
        else if (restaurantName != null){
            return this.menuRepository.findAllByRestaurantNameLike("%"+restaurantName+"%");
        }
        else if (menuType != null){
            return this.menuRepository.findAllByMenuType(menuType);
        }
        else {
            return this.menuRepository.findAll();
        }
    }
}

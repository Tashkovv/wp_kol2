package mk.ukim.finki.wp.kol2021.restaurant.repository;

import mk.ukim.finki.wp.kol2021.restaurant.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
}

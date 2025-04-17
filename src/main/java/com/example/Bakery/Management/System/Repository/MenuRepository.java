package com.example.Bakery.Management.System.Repository;

import com.example.Bakery.Management.System.Entity.MenuItems;
import com.example.Bakery.Management.System.Enum.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuRepository extends JpaRepository<MenuItems,Long> {
    Optional<MenuItems> findByName(String name);

    List<MenuItems> findAllByCategory(Category category);
}

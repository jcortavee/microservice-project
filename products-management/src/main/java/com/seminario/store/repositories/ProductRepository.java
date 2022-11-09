package com.seminario.store.repositories;

import com.seminario.store.models.Product;
import com.seminario.store.models.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("FROM Product p WHERE p.store.id = ?1")
    List<Product> findProductByStore(Long storeId);
}

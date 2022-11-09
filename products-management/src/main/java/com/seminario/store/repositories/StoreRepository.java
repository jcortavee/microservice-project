package com.seminario.store.repositories;

import com.seminario.store.models.Store;
import com.seminario.store.models.StoreType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    @Query("FROM Store s WHERE s.userId = ?1")
    List<Store> findStoresByUserId(Long userId);

    @Query("FROM Store s WHERE s.storeType.id = ?1")
    List<Store> findStoresByStoreType(Long storeTypeId);
}

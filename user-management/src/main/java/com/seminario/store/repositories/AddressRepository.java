package com.seminario.store.repositories;

import com.seminario.store.models.Address;
import com.seminario.store.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query("FROM Address a WHERE a.user.id = ?1")
    List<Address> findAddressByUser(Long id);
}

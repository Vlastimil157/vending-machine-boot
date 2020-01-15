package cn.moonshotacademy.vendingmachineboot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import cn.moonshotacademy.vendingmachineboot.model.ProductDTO;

@Repository
public interface ProductDAO extends JpaRepository<ProductDTO, Integer> {
    @Query(value = "SELECT * from products WHERE id=:id", nativeQuery = true)
    List<ProductDTO> findAllById(Integer id);
}
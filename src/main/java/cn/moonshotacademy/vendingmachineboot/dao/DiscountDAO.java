package cn.moonshotacademy.vendingmachineboot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import cn.moonshotacademy.vendingmachineboot.model.DiscountDTO;

@Repository
public interface DiscountDAO extends JpaRepository<DiscountDTO, Integer> {
    @Query(value = "SELECT * from discounts WHERE id=:id", nativeQuery = true)
    List<DiscountDTO> findAllById(Integer id);

    @Query(value = "SELECT * from discounts WHERE scope=0", nativeQuery = true)
    List<DiscountDTO> findAllSingle();

    @Query(value = "SELECT * from discounts WHERE scope=1", nativeQuery = true)
    List<DiscountDTO> findAllGlobal();
}
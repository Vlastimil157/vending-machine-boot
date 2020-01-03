package cn.moonshotacademy.vendingmachineboot.dto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cn.moonshotacademy.vendingmachineboot.model.UserCategory;

import java.util.List;

@Repository
public interface UserDAO extends JpaRepository<UserCategory, Integer> {
    List<UserCategory> findAllByName(String name);
}
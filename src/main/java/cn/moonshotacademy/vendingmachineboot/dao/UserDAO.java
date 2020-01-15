package cn.moonshotacademy.vendingmachineboot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cn.moonshotacademy.vendingmachineboot.model.UserDTO;

import java.util.List;

@Repository
public interface UserDAO extends JpaRepository<UserDTO, Integer> {
    List<UserDTO> findAllByName(String name);

    @Query(value = "SELECT * from users WHERE name=:name AND password=:password", nativeQuery = true)
    List<UserDTO> findAllByNameAndPassword(String name, String password);
}
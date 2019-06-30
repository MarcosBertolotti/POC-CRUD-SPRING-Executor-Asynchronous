package com.example.repository;

import com.example.model.Player;
import com.example.model.projection.IPlayerName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPlayerRepository extends JpaRepository<Player,Integer> {

    @Query(value = "select avg(age) from players", nativeQuery = true)
    double getAverageAge();

    List<Player> getByAge(final Integer age);

    List<IPlayerName> getNamesByAge(final Integer age);

    List<IPlayerName> getNamesByName(final String name);

}

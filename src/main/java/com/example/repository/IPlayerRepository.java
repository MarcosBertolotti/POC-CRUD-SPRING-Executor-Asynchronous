package com.example.repository;

import com.example.model.Player;
import com.example.model.projection.IPlayerName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPlayerRepository extends JpaRepository<Player, Integer> {

    @Query(value = "select avg(p.age) from players p",nativeQuery = true)
    double getAverageAge();

    @Query(value = "select max(p.age) from players p where p.name <> ?1",nativeQuery = true)
    int getMaxAgeMinus(final String name);

    List<Player> getByAge(final Integer age);

    List<IPlayerName> getNamesByAge(final Integer age);

}

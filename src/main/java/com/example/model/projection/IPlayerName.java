package com.example.model.projection;

import org.springframework.beans.factory.annotation.Value;

public interface IPlayerName {

    String getName();
    String getSurname();

    @Value("#{target.name + ' ' + target.surname}")
    String getFullName();

    ITeamName getTeam();

}

package shop.dao;

import shop.domain.AlarmDo;
import shop.domain.Config;

import java.util.List;

public interface ConfigMapper {

    List<Config> readConfig();

    Config getConfig(Integer address);

    AlarmDo selectMan();
}
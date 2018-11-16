package shop.dao;

import shop.domain.Config;

import java.util.List;

public interface ConfigMapper {

    List<Config> readConfig();
}
package lzcge.crowdfunding.manager.dao;

import lzcge.crowdfunding.entity.Type;
import java.util.List;

public interface TypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Type record);

    Type selectByPrimaryKey(Integer id);

    List<Type> selectAll();

    int updateByPrimaryKey(Type record);
}
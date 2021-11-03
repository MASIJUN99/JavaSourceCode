package com.luban.server;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DemoDao {

  @Insert("insert into `test` values(#{var1}, #{var2}, #{var3}, #{var4})")
  public void insert(
      @Param("var1") Integer var1,
      @Param("var2") Integer var2,
      @Param("var3") Integer var3,
      @Param("var4") Integer var4
  );
}

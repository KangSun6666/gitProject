package org.fkit.springbootmybatistest.mapper;

import org.fkit.springbootmybatistest.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {

	User Sel(int id);

	User login(String name,String password);

	int register(User user);

	String findRole(String name);

	User selByName(String name);

	List<User> selLikeName(String name);

	List<User> selAll();

	void delUser(int id);

	void updateUser(User user);

	void addUser(User user);
}

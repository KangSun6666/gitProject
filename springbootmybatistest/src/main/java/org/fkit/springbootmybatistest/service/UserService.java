package org.fkit.springbootmybatistest.service;

import org.fkit.springbootmybatistest.entity.User;
import org.fkit.springbootmybatistest.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {
	
	@Autowired
	UserMapper userMapper;

	public User Sel(int id){
		return userMapper.Sel(id);
	}

	public User login(String name,String password){
		return userMapper.login(name,password);
	}

	public int register(User user){
		return userMapper.register(user);
	}

	public String findRole(String name){ return userMapper.findRole(name);}

	public User selByName(String name) {return userMapper.selByName(name);}

	public List<User> selLikeName(String name) {return userMapper.selLikeName(name);}

	public List<User> selAll(){ return userMapper.selAll();}

	public void delUser(int id){ userMapper.delUser(id);}

	public void updateUser(User user){userMapper.updateUser(user);}

	public void addUser(User user){userMapper.addUser(user);}
}
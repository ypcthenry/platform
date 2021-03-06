package org.whut.platform.business.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.whut.platform.business.user.entity.User;
import org.whut.platform.business.user.mapper.UserMapper;

import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xiaozhujun
 * Date: 14-1-26
 * Time: 上午11:39
 * To change this template use File | Settings | File Templates.
 */
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void add(User user){
         userMapper.add(user);
    }

    public long getIdByName(String name){
        return userMapper.getIdByName(name);
    }
    public int update(User user){
        return userMapper.update(user);
    }

    public int delete(User user){
        return userMapper.delete(user);
    }

    public List<User> list(){
        return userMapper.findByCondition(new HashMap<String, Object>());
    }
    public User findByName(String name){
        if(name==null || name.trim().equals("")){
            return null;
        }
        List<User> list = userMapper.findByName(name);
        if(list.size()>=1){
            return list.get(0);
        }
        return null;
    }
}

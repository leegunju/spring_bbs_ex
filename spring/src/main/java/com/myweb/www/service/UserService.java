package com.myweb.www.service;

import com.myweb.www.domain.UserVO;

public interface UserService {

	int signUp(UserVO user);

	UserVO isUser(String id, String pw);

}

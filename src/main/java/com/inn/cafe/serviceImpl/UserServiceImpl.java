package com.inn.cafe.serviceImpl;

import com.inn.cafe.constents.CafeConstants;
import com.inn.cafe.dao.UserDao;
import com.inn.cafe.model.User;
import com.inn.cafe.service.UserService;
import com.inn.cafe.utils.CafeUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserDao userDao;

    @Override
    public ResponseEntity<String> signUp(Map<String, String> requesMap) {
        log.info("inside signUp{}",requesMap);
        try {
            if(validateSignUpMap(requesMap)){
                User user= userDao.findEmailId(requesMap.get("email"));
                if(Objects.isNull(user)){
                    userDao.save(getUserFromMap(requesMap));
                    return CafeUtils.getResponseEntity("signUp is Successfull",HttpStatus.OK);
                }else{
                    return CafeUtils.getResponseEntity("Email already exists",HttpStatus.BAD_REQUEST);
                }
            }else{
                return CafeUtils.getResponseEntity(CafeConstants.INVALIOD_DATA, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);


    }

    private boolean validateSignUpMap(Map<String,String>requesMap){
        if(requesMap.containsKey("name") && requesMap.containsKey("contactNumber")
                && requesMap.containsKey("email") && requesMap.containsKey("password")){
        return  true;
        }
        return false;
    }

    private User getUserFromMap(Map<String,String>requestMap){
        User user=new User();
        user.setName(requestMap.get("name"));
        user.setConcatNumber(requestMap.get("contactNumber"));
        user.setEmail(requestMap.get("email"));
        user.setPassword(requestMap.get("password"));
        user.setStatus(requestMap.get("false"));
        user.setRole(requestMap.get("user"));
        return  user;
    }
}

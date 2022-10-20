package com.mlab.rideshare.service.base;


import com.mlab.rideshare.entity.UserEntity;
import com.mlab.rideshare.exception.RecordNotFoundException;
import com.mlab.rideshare.helper.locale.LocaleMessageHelper;
import com.mlab.rideshare.model.auth.CurrentUser;
import com.mlab.rideshare.props.ApplicationProperties;
import com.mlab.rideshare.service.UserEntityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public abstract class BaseService {

    @Autowired
    protected LocaleMessageHelper messageHelper;

    @Autowired
    private UserEntityService userEntityService;


    protected UserEntity getUserByUserName(String userName){
        return userEntityService
                .findUserByUsername(userName)
                .orElseThrow(
                        () ->
                                new RecordNotFoundException(
                                        messageHelper.getLocalMessage("user.not.exists.message"))
                );
    }

    protected String getCurrentUserName() {
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}

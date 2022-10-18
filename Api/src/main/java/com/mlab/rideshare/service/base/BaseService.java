package com.mlab.rideshare.service.base;


import com.mlab.rideshare.helper.locale.LocaleMessageHelper;
import com.mlab.rideshare.model.auth.CurrentUser;
import com.mlab.rideshare.props.ApplicationProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public abstract class BaseService {

    @Autowired
    protected LocaleMessageHelper messageHelper;
    @Autowired
    protected ApplicationProperties props;

    protected String getLocaleMessage(String messageKey){
        return messageHelper.getLocalMessage(messageKey);
    }
}

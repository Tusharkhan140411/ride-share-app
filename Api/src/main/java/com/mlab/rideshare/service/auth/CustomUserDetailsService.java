package com.mlab.rideshare.service.auth;


import com.mlab.rideshare.entity.UserEntity;
import com.mlab.rideshare.exception.RecordNotFoundException;
import com.mlab.rideshare.helper.mapper.Mapper;
import com.mlab.rideshare.service.UserEntityService;
import com.mlab.rideshare.service.base.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService extends BaseService implements UserDetailsService {

    private final UserEntityService userEntityService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userEntityService
                .findUserByUsername(username)
                .orElseThrow(()-> new RecordNotFoundException(
                        messageHelper.getLocalMessage("user.not.exists.message")));

        return Mapper.mapToUserDetails(user);

    }

}

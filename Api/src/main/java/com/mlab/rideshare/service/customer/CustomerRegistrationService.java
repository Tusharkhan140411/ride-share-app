package com.mlab.rideshare.service.customer;

import com.mlab.rideshare.entity.RoleEntity;
import com.mlab.rideshare.entity.UserEntity;
import com.mlab.rideshare.enums.UserRoleEnum;
import com.mlab.rideshare.exception.NotUniqueException;
import com.mlab.rideshare.exception.RecordNotFoundException;
import com.mlab.rideshare.helper.mapper.Mapper;
import com.mlab.rideshare.model.request.customer.CustomerRegistrationRequest;
import com.mlab.rideshare.service.RoleEntityService;
import com.mlab.rideshare.service.UserEntityService;
import com.mlab.rideshare.service.base.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerRegistrationService extends BaseService {

    private final UserEntityService userEntityService;
    private final RoleEntityService roleEntityService;
    private final Mapper mapper;

    @Transactional
    public void registerCustomer(CustomerRegistrationRequest customerRegistrationRequest){
        userEntityService.findUserByUsername(customerRegistrationRequest.getUsername())
                .ifPresent(u -> {
                    throw new NotUniqueException(
                            messageHelper.getLocalMessage("user.exists.message"));
                });

        RoleEntity roleEntity = roleEntityService
                .findRoleByName(UserRoleEnum.CUSTOMER.getName())
                .orElseThrow(
                        () ->
                                new RecordNotFoundException(
                                        messageHelper.getLocalMessage("roles.not.exists.message"))
                );

        UserEntity user = mapper.mapToUserEntity(customerRegistrationRequest, roleEntity);
        userEntityService.save(user);
    }


}

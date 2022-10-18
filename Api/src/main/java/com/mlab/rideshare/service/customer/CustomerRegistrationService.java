package com.mlab.rideshare.service.customer;

import com.mlab.rideshare.entity.RoleEntity;
import com.mlab.rideshare.entity.UserEntity;
import com.mlab.rideshare.enums.UserRoleEnum;
import com.mlab.rideshare.exception.NotUniqueException;
import com.mlab.rideshare.exception.RecordNotFoundException;
import com.mlab.rideshare.helper.mapper.Mapper;
import com.mlab.rideshare.model.request.customer.CustomerCreateRequest;
import com.mlab.rideshare.service.RoleEntityService;
import com.mlab.rideshare.service.UserEntityService;
import com.mlab.rideshare.service.base.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerRegistrationService extends BaseService {

    private final UserEntityService userEntityService;
    private final RoleEntityService roleEntityService;
    private final Mapper mapper;

    public void registerCustomer(CustomerCreateRequest customerCreateRequest){
        userEntityService.findUserByUsername(customerCreateRequest.getUsername())
                .ifPresent(u -> {
                    throw new NotUniqueException(
                            messageHelper.getLocalMessage("validation.constraints.username.exists.message"));
                });

        RoleEntity roleEntity = roleEntityService
                .findRoleByName(UserRoleEnum.CUSTOMER.getName())
                .orElseThrow(
                        () ->
                                new RecordNotFoundException(
                                        messageHelper.getLocalMessage("validation.constraints.roles.not.exists.message"))
                );

        UserEntity user = mapper.mapToUserEntity(customerCreateRequest, roleEntity);
        userEntityService.save(user);
    }


}

package com.bybit.api.client.domain.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
public class UserDataRequest {
    private String username;
    private String password;
    private MemberType memberType;
    private SwitchOption switchOption;
    private IsUta isUta;
    private String note;
    private MasterUserPermissions masterUserPermissions;
    private SubUserPermissions subUserPermissions;
    private UserPermissionsMap userPermissionsMap;
    private FrozenStatus frozenStatus;
    private Integer subuid;
    private List<String> memberIds; // Multiple sub UID are supported, separated by commas
    private List<String> ips;
    private String apikey;
    private ReadOnlyStatus readOnlyStatus;
    private String uid;
}

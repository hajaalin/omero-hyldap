/*
 *   $Id$
 *
 *   Copyright 2010 Glencoe Software, Inc. All rights reserved.
 *   Use is subject to license terms supplied in LICENSE.txt
 */

package hyldap;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;

import ome.conditions.ValidationException;
import ome.security.SecuritySystem;
import ome.security.auth.AttributeSet;
import ome.security.auth.GroupAttributeMapper;
import ome.security.auth.LdapConfig;
import ome.security.auth.NewUserGroupBean;
import ome.security.auth.RoleProvider;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import org.springframework.ldap.core.LdapOperations;

/**
 * Implements group selection based on a hard-coded list of allowed groups.
 * 
 * @author Harri J채채linoja, harri.jaalinoja at helsinki.fi
 * @see SecuritySystem
 * @since Beta4.2
 */
public class HyHcNewUserGroupBean implements ome.security.auth.NewUserGroupBean, HyLdapAttributes {

    private HashMap allowedGroups;
    
    private final static Log log = LogFactory.getLog(HyHcNewUserGroupBean.class);


 
    public HyHcNewUserGroupBean() {
        allowedGroups = new HashMap();
        //allowedGroups.put("uid=grp-A91900-bi-vart,ou=alma_workgroups,ou=groups,o=hy","BI-Vartiainen");
        allowedGroups.put("uid=grp-A34520-biu,ou=alma_workgroups,ou=groups,o=hy","BIU");
        allowedGroups.put("uid=grp-A91900-lmu,ou=alma_workgroups,ou=groups,o=hy","LMU"); 
    }

    public List<Long> groups(String username, LdapConfig config,
            LdapOperations ldap, RoleProvider provider, AttributeSet attrSet) {

        log.debug("groups for " + username);
        Set<String> groupNames = new HashSet<String>();
        groupNames.addAll(attrSet.getAll(GROUP_MEMBER));
        groupNames.addAll(attrSet.getAll(GROUP_OWNER));
        if (groupNames.isEmpty()) {
            throw new ValidationException(username + " has no attributes "
                    + GROUP_MEMBER + " or " + GROUP_OWNER);
        }

        List<Long> groups = new ArrayList<Long>();
        for (String grpName : groupNames) {
            log.debug("grpName " + grpName);
            if (allowedGroups.containsKey(grpName)) {
                log.debug("grpName matched " + grpName);
                String grpOmeName = (String)allowedGroups.get(grpName);
                log.debug("grpName matched " + grpOmeName);
                groups.add(provider.createGroup(grpOmeName, null, false));
                log.debug("grpName matched, adding group 'user'");
                groups.add (new Long(1));
            }
        }   
        return groups;

    }

}

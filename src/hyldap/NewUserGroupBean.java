package hyldap;

import java.util.List;

import ome.security.SecuritySystem;
import ome.security.auth.*;

import org.springframework.ldap.core.LdapOperations;

/**
 * Strategy for finding the appropriate groups for a given user in LDAP.
 *
 * @author Josh Moore, josh at glencoesoftware.com
 * @see SecuritySystem
 * @since Beta4.2
 */
public interface NewUserGroupBean {

    /**
     *
     * @param config
     * @param ldap
     * @param provider
     * @param userProperties
     */
    List<Long> groups(String username,
            LdapConfig config, LdapOperations ldap, RoleProvider provider,
            AttributeSet attrSet);

}

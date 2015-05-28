/*
 *   $Id$
 *
 *   Copyright 2010 Glencoe Software, Inc. All rights reserved.
 *   Use is subject to license terms supplied in LICENSE.txt
 */

package hyldap;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import ome.api.IConfig;
import ome.conditions.ValidationException;
import ome.security.SecuritySystem;
import ome.security.auth.AttributeSet;
import ome.security.auth.GroupAttributeMapper;
import ome.security.auth.LdapConfig;
import ome.security.auth.NewUserGroupBean;
import ome.security.auth.RoleProvider;
import ome.system.ServiceFactory;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import org.springframework.ldap.core.LdapOperations;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 * Implements group selection based on a group info found in OMERO configuration.
 * 
 * @author Harri Jäälinoja, harri.jaalinoja at helsinki.fi
 * @since Beta4.3
 */
public class HyNewUserGroupBean implements ome.security.auth.NewUserGroupBean, HyLdapAttributes {

    public final static String ELEM_CONFIG = "hyldapconfig";
    public final static String ELEM_GROUP = "group";
    public final static String ATTR_LDAPNAME = "ldapname";
    public final static String ATTR_OMENAME = "omename";
    
    private HashMap allowedGroups = new HashMap();
    private final static Log log = LogFactory.getLog(HyNewUserGroupBean.class);


 
    public HyNewUserGroupBean() {
        log.info("Initialized without config file, this may cause problems...");
    }

    public HyNewUserGroupBean(String configFile) {
        log.info("Initialized with config file: " + configFile);
        
        // open config file
        try {
            File f = new File(configFile);
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document d = db.parse(f);
            Element config = d.getDocumentElement();
            NodeList groups = config.getElementsByTagName(ELEM_GROUP);
            
            Node n;
            String ldapname;
            String omename;
            for(int i=0;  i < groups.getLength(); i++) {
                n = groups.item(i);
                ldapname = n.getAttributes().getNamedItem(ATTR_LDAPNAME).getNodeValue();
                omename = n.getAttributes().getNamedItem(ATTR_OMENAME).getNodeValue();
                log.debug("ldapname: " + ldapname + ", omename: " + omename);
                allowedGroups.put(ldapname,omename);
            }
        }
        catch (Exception e) {
            log.error("Failed to parse config.",e);
        }
    }

    
    public List<Long> groups(String username, LdapConfig config,
            LdapOperations ldap, RoleProvider provider, AttributeSet attrSet) {

        log.debug("groups for " + username);
        Set<String> groupNames = new HashSet<String>();
        groupNames.addAll(attrSet.getAll(GROUP_MEMBER));
        groupNames.addAll(attrSet.getAll(GROUP_OWNER));
        if (groupNames.isEmpty()) {
            throw new ValidationException(username + " has no attributes "
                    + GROUP_MEMBER);
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

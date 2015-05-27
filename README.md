# omero-hyldap
OMERO LDAP component for Helsingin yliopisto.

Example build directory.

See: http://lists.openmicroscopy.org.uk/pipermail/ome-users/2012-March/002964.html

## To clone: ##

```
$ git clone git://gist.github.com/2158254.git components/hyldap
Cloning into 'components/hyldap'...
remote: Counting objects: 19, done.
remote: Compressing objects: 100% (9/9), done.
remote: Total 19 (delta 2), reused 16 (delta 2)
Receiving objects: 100% (19/19), done.
Resolving deltas: 100% (2/2), done.
```

## To build: ##

```
$ ./build.py -f components/hyldap/build.xml
Buildfile: omero.git/components/hyldap/build.xml
Entering omero.git/components/hyldap...

retrieve:
:: Ivy 2.1.0 - 20090925235825 :: http://ant.apache.org/ivy/ ::
:: loading settings :: file = omero.git/etc/ivysettings.xml

prepare:
Created dir: omero.git/components/hyldap/target/generated/.done
Created dir: omero.git/components/hyldap/target/generated/src
Created dir: omero.git/components/hyldap/target/generated/resources
Created dir: omero.git/components/hyldap/target/classes
Created dir: omero.git/components/hyldap/target/test-classes
Created dir: omero.git/components/hyldap/target/reports
Copying 1 file to omero.git/components/hyldap/target/generated/resources
Copying 1 file to omero.git/components/hyldap/target/classes
Copying 1 file to omero.git/components/hyldap/target/generated/resources
Copying 1 file to omero.git/components/hyldap/target/classes
Copying 1 file to omero.git/components/hyldap/target/generated/resources
Copying 1 file to omero.git/components/hyldap/target/classes
Copying 1 file to omero.git/components/hyldap/target/generated/resources
Copying 1 file to omero.git/components/hyldap/target/classes

generate:
Copying 1 file to omero.git/components/hyldap/target/classes

compile:
Compiling 1 source file to omero.git/components/hyldap/target/classes
Deleting: omero.git/components/hyldap/_omero_build_1225728604.tmp

package:
Building jar: omero.git/components/hyldap/target/hyldap.jar

install:
:: delivering :: omero#hyldap;working@Josh-Moores-MacBook-Pro.local :: 4.3.3-DEV-ice33 :: integration :: Thu Mar 22 14:10:50 CET 2012
    delivering ivy file to omero.git/components/hyldap/target/ivy.xml
:: publishing :: omero#hyldap
    published hyldap to omero.git/etc/../target/repository/hyldap-4.3.3-DEV-ice33.jar
    published ivy to omero.git/etc/../target/repository/hyldap-4.3.3-DEV-ice33.ivy

BUILD SUCCESSFUL
Total time: 9 seconds
```


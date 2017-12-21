IoT-Service
=============


![Project Status: Concept – Minimal or no implementation has been done yet, or the repository is only intended to be a limited example, demo, or proof-of-concept.](http://www.repostatus.org/badges/latest/concept.svg)

[![Known Vulnerabilities](https://snyk.io/test/github/Cantara/iot-service/badge.svg)](https://snyk.io/test/github/Cantara/iot-service)


Example Output
===================

TODO: Try out the Mobile application at http://....


Installation
===================

* Copy Proprerties file
```
wget https://raw.githubusercontent.com/altran/IoT-service/master/src/main/resources/local-overrides.properties
mkdir config_override
cp local-overrides.properties config_override/iot-service.properties
```
* Edit iot-service to suite your environment.

* Create database. Use sql resources\db\initialize_new_database.sql
** The updates to the database will be managed by Flyway


Running IoT-service
===================

```
wget http://mvnrepo.cantara.no/content/repositories/snapshots/com/altran/iot/IoT-service/0.1-SNAPSHOT/IoT-service-0.1-20140811.125511-1.jar
java -jar IoT-service-0.1-20140811.125511-1.jar
# java -jar target/IoT-service-0.1-SNAPSHOT-with-deps.jar
```

Database
===================

Default behaviour of IoT-service will use an embedded database.

The database setup in IoT-service is utilizing Spring for configuration, and connection.
This will ensure you can connect IoT-service to just about any database.
To use a permanent storage, see the Configuration section below.

Configuration
===================

See Installation, copy properties into config_override.


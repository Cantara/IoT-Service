IoT-Service
=============


Example Output
===================

TODO: Try out the Mobile application at http://....


Installation
===================

* Copy Proprerties file
```
mkdir config_override
cp src/main/resources/iot-service.properties config_override/
```
* Edit iot-service to suite your environment.

* Create database. Use sql resources\db\initialize_new_database.sql
** The updates to the database will be managed by Flyway


Running IoT-service
===================

```
java -jar target/IoT-service-0.1-SNAPSHOT-with-deps.jar
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


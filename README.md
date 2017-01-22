# Java DB-IP
A simple to use Java library for the freely available DB-IP [IP address to city dataset](https://db-ip.com/db/download/city).
**Requires Java 8**

#Before you begin
The entire dataset is loaded into a [TreeMap](https://docs.oracle.com/javase/8/docs/api/allclasses-noframe.html) . Make sure that you have about **2 GB of Heap space** available to load it.

#Get 

With maven :

```xml

<dependency>
	  <groupId>in.ankushs</groupId>
	  <artifactId>Java-DB-IP</artifactId>
	  <version>1.0</version>
</dependency>
```

Or gradle:

```groovy

compile('in.ankushs:Java-DB-IP:1.0')

```

The Javadocs for the latest release can be found [here](http://www.javadoc.io/doc/in.ankushs/Java-DB-IP/1.0)


#Instructions
In order to get geographical information for an ip address, just pass the `dbip-city-latest.csv.gz` as a File object to `DbIpClient` as follows:

```java
File gzip = new File(PATH_TO_dbip-city-latest.csv.gz);
DbIpClient client = new DbIpClient(gzip);
```

Once the data is loaded from the file into memory , any subsequent invocation of the above code **would not** re-load the data . 

Next,just fetch the data for a ip ,like so :

```java
DbIpClient client = new DbIpClient(gzip);
GeoEntity geoEntity = client.lookup("31.45.127.255");
String city = geoEntity.getCity();
String country = geoEntity.getCountry();
String province = geoEntity.getProvince();

System.out.println("city : " + city);
System.out.println("province : " + province);
System.out.println("country : " + country);
```

This prints : 
```
city : Oslo
province : Oslo
country : Norway
```
That's pretty much it.

#Integrating with Spring Boot.
Follow the detailed instructions on [this](http://ankushs92.github.io/libraries/2016/05/12/java-db-ip.html) blogpost . You can find the Spring Boot Java-DB-IP project [here](https://github.com/ankushs92/Spring-Boot-DB-IP) .


[![Bitdeli Badge](https://d2weczhvl823v0.cloudfront.net/ankushs92/java-db-ip/trend.png)](https://bitdeli.com/free "Bitdeli Badge")


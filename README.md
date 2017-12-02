# Java DB-IP
A simple to use Java library for the freely available DB-IP [IP address to city dataset](https://db-ip.com/db/download/city).

**Requires Java 8**

The entire dataset is loaded into a [TreeMap](https://docs.oracle.com/javase/8/docs/api/allclasses-noframe.html) . Make sure that you have about **2 GB of Heap space** available to load it.

#Get 

With maven :

```xml

<dependency>
	  <groupId>in.ankushs</groupId>
	  <artifactId>Java-DB-IP</artifactId>
	  <version>2.0</version>
</dependency>
```

Or gradle:

```groovy

compile('in.ankushs:Java-DB-IP:2.0')

```

The Javadocs for the latest release can be found [here](http://www.javadoc.io/doc/in.ankushs/Java-DB-IP/2.0)


# Instructions
In order to get geographical information for an ip address, just pass the `dbip-city-latest.csv.gz` as a File object to `DbIpClient` as follows:

```java
File gzip = new File(PATH_TO_dbip-city-latest.csv.gz);
DbIpClient client = new DbIpClient(gzip);
```

Once the data is loaded from the file into memory, any subsequent invocation of the above code **would not** load the data . 

Next, just fetch the data for a ip ,like so :

```java
DbIpClient client = new DbIpClient(gzip);
GeoEntity geoEntity = client.lookup("31.45.127.255");
String city = geoEntity.getCity();
String country = geoEntity.getCountry();
String province = geoEntity.getProvince();
String countryCode = geoEntity.getCountryCode();

System.out.println("city : " + city);
System.out.println("province : " + province);
System.out.println("country : " + country);
System.out.println("country code : " + countryCode);

This prints : 

```

```
city : Oslo
province : Oslo
country : Norway
country code : NO
```



For IPV6, do the same :

```java
DbIpClient client = new DbIpClient(gzip);
String ipv6 = "2c0f:f468:ffff:ffff:ffff:ffff:ffff:ffff";
GeoEntity geoEntity = client.lookup(ipv6);

```



That's pretty much it.

# Performance
The code is stunningly fast. A lookup request is resolved within 0.1 ms.

# Integrating with Spring Boot
You can find the Spring Boot Java-DB-IP project [here](https://github.com/ankushs92/Spring-Boot-DB-IP) .

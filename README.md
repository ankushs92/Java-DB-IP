# Java DB-IP
A simple to use Java library for the freely available DB-IP [IP address to city dataset](https://db-ip.com/db/download/city).

**Requires Java 8**

The entire dataset is loaded into a [TreeMap](https://docs.oracle.com/javase/8/docs/api/allclasses-noframe.html) . Make sure that you have about **2 GB of Heap space** available to load it.

# Links 

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


# Instructions
In order to get geographical information for an ip address, just pass the `dbip-city-latest.csv.gz` as a File object to `DbIpClient` as follows:

```java
final File gzipFile = new File(PATH_TO_dbip-city-latest.csv.gz);
final DbIpClient client = new DbIpClient(gzipFile);
```

Once the data is loaded from the file into memory, any subsequent invocation of the above code **would not** load the data . 

Next, just fetch the data for a ip ,like so :

```java
final DbIpClient client = new DbIpClient(gzip);
final GeoEntity geoEntity = client.lookup("31.45.127.255");
final String city = geoEntity.getCity();
final String country = geoEntity.getCountry();
final String province = geoEntity.getProvince();
final String countryCode = geoEntity.getCountryCode();

System.out.println("city : " + city);
System.out.println("province : " + province);
System.out.println("country : " + country);
System.out.println("country code : " + countryCode);


```

This prints : 

```
city : New Delhi
province : Delhi
country : India
country code : IN
```


For IPV6, do the same :

```java
final DbIpClient client = new DbIpClient(gzip);
final String ipv6 = "2c0f:f468:ffff:ffff:ffff:ffff:ffff:ffff";
final GeoEntity geoEntity = client.lookup(ipv6);

```

That's pretty much it.

# Performance
The lookup is stunningly fast. Since the entire data is cached the JVM, you can expect each lookup to take around 0.1 ms

# Donate



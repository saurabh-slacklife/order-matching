## Run Book

## Table of contents

* [Pre requisites](#pre-requisites)
* [Steps](#steps)
    * [Build, install and package all binaries/jar](#1-build-install-and-package-all-jar)
    * [Run application](#2-run-application)
    * [Check logs](#3-check-logs)

### Pre requisites

1. JDK 11.
2. Installed Maven

### Steps

#### 1 Build install and package all jar

* Working directory: **stock-exchange-orders**
* `mvn clean compile install package`

#### 2 Run Application

* Working directory: **stock-exchange-orders**
* Example Orders File= orders.txt
* Run below command.

```shell
java -jar target/geektrust.jar orders.txt
```

#### 3 Check logs

* Console Logs
* Filepath Log: application.log
<p>
Search for pattern for the expected Transaction format(format:"[buy-order-id] [sell-price] [qty] [sell-order-id]"):

`grep "Order Transaction completed" application.log`
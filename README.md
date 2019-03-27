# czi_spatial

[![Build Status](https://travis-ci.com/Comparative-Pathology/czi_spatial.svg?branch=master)](https://travis-ci.com/Comparative-Pathology/czi_spatial) [![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

[CZI](https://www.chanzuckerberg.com/) funded project exploring spatial data within the context of the colon and the [HCA](https://www.humancellatlas.org/).

Work undertaken by [BISEL](http://www.macs.hw.ac.uk/bisel), which is part of [Heriot-Watt University](http://www.hw.ac.uk), in collaboration with the [University of Edinburgh](http://www.ed.ac.uk) [Pathology Division](https://www.ed.ac.uk/pathology/).

## What this repository contains

A simple prototpe database and web app that demonstrates the colon model produced and how it may be used.


## Instructions

1. Install a relational database and tomcat with a JNDI connection pool set up.
2. Update the persistence.xml inside model-lib/src/main/resources/META-INF to reflect your database. The current file contains an example for running MySQL on your local machine using the default port and a user called 'czi'.
3. Run czi_spatial/model-lib/src/main/java/uk/bisel/czi/data/LoadData to populate your database with dummy data.
4. Update czi_spatial/web-app/src/main/resources/persistence.xml to use your JNDI connection pool. The current file assumes you have a connection pool called 'czi'.
5. To compile the code and produce a WAR file in web-app/target/ run: mvn package -DskipTests
6. Copy the WAR file into your tomcat/webapps folder
7. (Re)start tomcat
8. Go to http://localhost:8080/CZI (assuming your tomcat uses the default port 8080).

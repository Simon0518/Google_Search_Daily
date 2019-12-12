# Live Demo
[![Demo Doccou alpha](http://share.gifyoutube.com/KzB6Gb.gif)](https://www.youtube.com/watch?v=ek1j272iAmc)
https://youtu.be/SgM3EABeuL8

# Google_Search_Daily
To align with Google’s mission to organize the world's information and make it universally accessible and useful, we believe there should be a product to provide live news and daily feeds. Currently Google does not have a product to provide live news. The purpose of Google Search Daily aims to fit in this gap and provide a more fluent user experience of accessing information and a next generation news system.


# spring-react-boilerplate

[![CircleCI](https://circleci.com/gh/pram/spring-react-boilerplate.svg?style=svg)](https://circleci.com/gh/pram/spring-react-boilerplate) [![Coverage Status](https://coveralls.io/repos/github/pram/spring-react-boilerplate/badge.svg?branch=master)](https://coveralls.io/github/pram/spring-react-boilerplate?branch=master)

Boilerplate application to demonstrate how to wire up Spring, JWT Authentication, React, Redux and Websockets.

## Local Development

Install [Maven](https://maven.apache.org/) and [Yarn](https://yarnpkg.com)

Start `com.naughtyzombie.boilerplate.springreactboilerplate.SpringReactBoilerplateApplication` in your IDE

Then start the front end

    cd src/main/app
    yarn start
    
The front end will start on [port 3000](http://localhost:3000).

## Running locally

To build and run locally

    mvn spring-boot:run
    
You can now access the app on [port 8080](http://localhost:8080)

## Build deployable package

To build a deployable artifact

    mvn package
    
The self contained jar will be created in `${project.basedir}/target`

To run the app

    java -jar target/spring-react-boilerplate-0.0.1-SNAPSHOT.jar
    
You can now access the app on [port 8080](http://localhost:8080)    

## User details

    admin/admin
    user/password

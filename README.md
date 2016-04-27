# Sonar TypeScript plugin
[![Build Status](https://travis-ci.org/sandermvanvliet/sonar-typescript-plugin.svg?branch=master)](https://travis-ci.org/sandermvanvliet/sonar-typescript-plugin)

This is a plugin for SonarQube to analyze TypeScript source code. It uses [tsa](http://github.com/sandermvanvliet/tsa) to run the analysis.

Currently this plugin supports the following metrics:

* Lines (total lines)
* Lines of Code (currently the same as total lines)
* Classes
* Functions

**Please note:** This is a work in progress so expect the metrics to change.

## Current status ##
Every release of the sonar-typescript-plugin triggers an analysis on the official [TypeScript](https://github.com/microsoft/TypeScript/) sources. Note that also whenever [tsa](http://github.com/sandermvanvliet/tsa) changes a new analysis is triggered. Latest analysis results can be found [here](http://sonar.codenizer.nl:9000/overview?id=1)

## Deploying
Clone the sources and run `mvn clean package` and when that is successful copy the jar from the `targets` folder to `${SONAR_HOME}/extensions/plugins`

## Suggestions and contributions
I'm open to pull requests and suggestions. If you feel something is missing, please let me know and open a new issue. If you just want to say hi that's fine too ;-)


### Author
This plugin has been created by Sander van Vliet ([@Codenizer](https://twitter.com/Codenizer) on Twitter and [sandermvanvliet](https://github.com/sandermvanvliet) on GitHub).

### Tools used
Sonar TypeScript plugin is built with:

* IntelliJ IDEA
* Maven
* VIM
* Travis CI
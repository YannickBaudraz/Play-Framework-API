# Scala Play Api Demo

Simple demo to know how to develop an API with Play Framework in Scala.

## Requirements

- [Scala 2.13.8](https://www.scala-lang.org/download/2.13.8.html)
- Java versions [SE 8](https://www.oracle.com/java/technologies/javase/javase8u211-later-archive-downloads.html)
  through [SE 11](https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html), inclusive
- [sbt](https://www.scala-sbt.org/) - the latest version is recommended
- Any API client to send HTTP requests, like :
    - [Postman](https://www.postman.com/)
    - [Hoppscotch](https://hoppscotch.io/fr)
    - [Insomnia](https://insomnia.rest/)
    - [cURL](https://curl.se/)
    - [VSCode Rest Client extension](https://marketplace.visualstudio.com/items?itemName=humao.rest-client)

## Installation

Compile the application with sbt

```bash
cd ScalaPlayApi
ScalaPlayApi $ sbt compile
```

## Run Locally

```bash
git clone https://github.com/yannickcpnv/ScalaPlayApi.git
cd ScalaPlayApi
sbt run
```

## Usage

For available commands see
the [Play console documentation](https://www.playframework.com/documentation/2.8.x/PlayConsole)

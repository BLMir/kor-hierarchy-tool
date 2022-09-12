# kor-hierarchy-tool
A basic tool to parse key value list into hierarchy tree.

From this:

```json
{
"Pete": "Nick",
"Barbara": "Nick",
"Nick": "Sophie",
"Sophie": "Jonas"
}
```

To this:
```json
{
  "Jonas": {
    "Sophie": {
      "Nick": {
        "Pete": {},
        "Barbara": {}
      }
    }
  }
}
```

##Features

* HTTP REST API
* Format to hierarchy tree given json
* Validations
  * Infinity Loops
  * More than one root
* Store key value json into relational database
* Query supervisor and the supervisor’s supervisor of a given talent
* Add basic Auth
* Functional test

## Hot to run it in local?

```
gradle build
gradle run
```

## CURL ACTIONS

* Parse key value to hierarchy tree
```curl
curl --location --request POST 'http://0.0.0.0:8081/hierarchy' \
--header 'Authorization: Basic YWRtaW46YWRtaW4=' \
--header 'Content-Type: application/json' \
--data-raw '{
    "Pete": "Nick",
    "Barbara": "Nick",
    "Biel": "Barbara",
    "Pedro": "Pete",
    "Pila": "Biel",
    "Nick":"Jonas",
    "Lolo": "Biel"
}'
```
* Get supervisor and the supervisor’s supervisor of a given talent name
```curl
curl --location --request GET 'http://0.0.0.0:8081/talent/Biel/supervisor' \
--header 'Authorization: Basic YWRtaW46YWRtaW4='
```
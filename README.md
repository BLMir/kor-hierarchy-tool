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
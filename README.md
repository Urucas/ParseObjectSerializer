# ParseObjectSerializer
Serialize ParseObject to flat JSON in Android

#Install

#Usage
```java
JSONObject json = Serializer.Serialize(myParseObject)
// output
{ 
  "id": "a35fe45",
  "createdAt": "",
  "updatedAt": "",
  "myLocation": {
    "lat": -32.9479262,
    "lng": -60.6442077
  },
  "myFile": {
    "url": "https://media.giphy.com/media/r6T74LkYSSmoE/giphy.gif"
  },
  "what" : {
    "put a bird on that"
  },
  "aNumber" : 2,
  "anArray" : [
    "withObjects" : "arrays are cool(?)"
  ],
  "anObject" : {
    "current_song" : "Strawerry Fields Forever",
    "gnos_tnerruc" : "reveroF sdleiF yrrewawrtS"
  },
  "iShouldGo2Sleep": true
}
```

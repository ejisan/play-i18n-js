# Play JavaScript Internationalization (I18n) Messages
**!! THIS IS UNDER DEVELOPMENT !!**

This is a helper library for play framework Scala. It allows you to use Play internationalization (i18n) messages on JavaScript easily and efficiently.


**If you find any weird behavior or bugs please report or issue me :) I always welcome your pull requests!**

## Installation

#### Dependency
Add those resolver and dependency to `build.sbt`:<br>
**Currently it only supports Play Framework 2.5.x**
```scala
// Adding resolver
resolvers += "EJISAN" at "https://ejisan.github.io/repo/"
// Adding dependency
libraryDependencies += "com.ejisan" %% "play-i18n-js" % "1.0.0-SNAPSHOT"
```

#### Routes
Add those routes to `conf/routes`:
```
script
GET     /i18n.js              controllers.JsI18nController.script
GET     /i18n/messages.js     controllers.JsI18nController.messages(p: String, v: String = "messages")
GET     /i18n/additional.js   controllers.JsI18nController.additionalMessages(p: String, v: String = "messages")
GET     /i18n/messages.json   controllers.JsI18nController.messagesJson(p: String)
```

You can have this feature on your controller by extending `JsI18nSupport` instead of using `JsI18nController` default controller.

- /i18n.js<br>
Returns client `Messages` JavaScript.

- /i18n/messages.js<br>
Returns client `Messages` JavaScript and all translated messages and creates instance of `Messages` class with the translated messages.<br>
(Parameter: p=prefix)

- /i18n/additional.js<br>
Returns all translated messages and adds it to the instance of `Messages`.<br>
(Parameter: p=prefix)

- /i18n/messages.json<br>
Returns all translated messages as stringified JSON.

## Uses
In JavaScript it provides syntactic sugar that `$m` function. You can specify the variable name (default is `messages`) of the instance of `Messages` in routes.

Ex: messages
```
hello = Hello!
hello.myNameIs = Hello! My name is {0} {1}!
```

```js
$m("hello")
// => Hello!

// Message pattern is supported.
$m("hello.myNameIs", "Foo", "Bar")
// => Hello! My name is Foo Bar!

// Message pattern without parameters,
// formats will be replaced to '?' symbol.
$m("hello.myNameIs")
// => Hello! My name is ? ?!
```
